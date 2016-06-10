/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.token.GenerateTokenId;
import jp.co.transcosmos.dm3.token.annotation.UseTokenCheck;
import jp.co.transcosmos.dm3.utils.ServletUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Functions as an adapter between the Spring MVC Controller API and the
 * DM3-Spring2 Command API pattern, delegating the actual resolution of which 
 * command instances to run to the CommandURLMapper class.
 * <p>
 * This class should be mounted in the spring-servlet.xml file as follows:
 * <p><code>
 * &lt;bean id="commandDelegatingController" class="jp.co.transcosmos.dm3.servlet.CommandDelegatingController"&gt;<br/>
 * &#0160;&#0160;&lt;property name="commandURLMapper" ref="commandURLMapper"/&gt;<br/>
 * &lt;/bean&gt;<br/>
 * &lt;bean id="urlMapping" class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping"&gt;<br/>
 * &#0160;&#0160;&lt;property name="mappings"&gt;<br/>
 * &#0160;&#0160;&#0160;&#0160;&lt;props&gt;&lt;prop key="/*"&gt;commandDelegatingController&lt;/prop&gt;&lt;/props&gt;<br/>
 * &#0160;&#0160;&lt;/property&gt;<br/>
 * &#0160;&#0160;&lt;property name="interceptors" ref="rollbackOnExceptionInterceptor"/&gt;<br/>
 * &lt;/bean&gt;  <br/>
 * &lt;bean id="rollbackOnExceptionInterceptor" class="jp.co.transcosmos.dm3.transaction.RollbackOnExceptionInterceptor"/&gt;<br/>
 * </code>
 * <p>
 * This will 
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: CommandDelegatingController.java,v 1.2 2007/05/31 07:44:43 rick Exp $
 */
public class CommandDelegatingController implements Controller {

	private static final Log log = LogFactory.getLog(CommandDelegatingController.class);
	
    private CommandURLMapper commandURLMapper;
    
    public CommandURLMapper getCommandURLMapper() {
        return commandURLMapper;
    }
    public void setCommandURLMapper(CommandURLMapper urlMapper) {
        this.commandURLMapper = urlMapper;
    }

    
    // 2013.04.23 H.Mizuno 自動 URL マッピング対応 Start
    // 自動 URL マッピングを有効にする場合は、このプロパティを true に設定する。
    private boolean autoMapping = false;
    
    public void setAutoMapping(boolean autoMapping) {
		this.autoMapping = autoMapping;
	}
    // 2013.04.23 H.Mizuno 自動 URL マッピング対応 End
    
    
    // 1013.5.17 H.Mizuno トークンチェック対応 (CSRF 対応） start
    // トークンチェックを有効にする場合は、このプロパティを true に設定する。
    private boolean useTokenCheck = false;
    
	public void setUseTokenCheck(boolean useTokenCheck) {
		this.useTokenCheck = useTokenCheck;
	}

	// トークン名（リクエストパラメータ、セッション共通）
	private String tokenName = "dm3token";
	
	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}

	
	// トークンチェックが成功した後、トークンを削除する場合、true を設定する。
	// デフォルトではfalse（削除しない）。
	// 固定トークンの場合は false のまま使用する事。
	// 固定トークンによるチェック時、自動的にセッション内から削除したい時に true に設定する。
	private boolean removeAfterToken = false;
	
	public void setRemoveAfterToken(boolean removeAfterToken) {
		this.removeAfterToken = removeAfterToken;
	}
	
	
	// 1013.5.17 H.Mizuno トークンチェック対応 (CSRF 対応） end
    
    
    
	public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {        
        FilterCommandChain chain = (FilterCommandChain) request.getAttribute(
                FilterCommandChain.REQUEST_ATTRIBUTE_NAME); 
        if (chain != null) {
            // 1013.5.17 H.Mizuno トークンチェック対応 (CSRF 対応） start
        	if (isInvalidToken(chain, request, response)) return null;
            // 1013.5.17 H.Mizuno トークンチェック対応 (CSRF 対応） end

        	request.removeAttribute(FilterCommandChain.REQUEST_ATTRIBUTE_NAME);
            chain.doFilter(request, response);
            return chain.getResult(request);
        }
        
        // Build chain manually
        chain = commandURLMapper.findCommandAndFilters(request, true);
        if (chain != null) {
            // 1013.5.17 H.Mizuno トークンチェック対応 (CSRF 対応） start
        	if (isInvalidToken(chain, request, response)) return null;
            // 1013.5.17 H.Mizuno トークンチェック対応 (CSRF 対応） end

        	chain.doFilter(request, response);
            return chain.getResult(request);
        }
        
        // 2013.04.23 H.Mizuno 自動 URL マッピング対応 Start 
        // URL マッピング定義から該当 URL が取得できない場合、URL から自動マッピングを実行する。
        if (autoMapping){
            chain = commandURLMapper.findCommandFromAnnotation(request);
            if (chain != null) {
                // 1013.5.17 H.Mizuno トークンチェック対応 (CSRF 対応） start
            	if (isInvalidToken(chain, request, response)) return null;
                // 1013.5.17 H.Mizuno トークンチェック対応 (CSRF 対応） end

            	chain.doFilter(request, response);
                return chain.getResult(request);
            }
        }
        // 2013.04.23 H.Mizuno 自動 URL マッピング対応 End 
        
        // No chain available, check for a directory index
        String welcomePage = commandURLMapper.findWelcomePageURI(request);
        if (welcomePage != null) {
            response.sendRedirect(ServletUtils.fixPartialURL(
                    request.getContextPath() + welcomePage, request));
            return null;
        }
        
        // If no options left, return 404
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found: url=" +
                ServletUtils.getDecodedUrlPath(request) + 
                " (method=" + request.getMethod() + ")");
        return null;
    }


	
	// 1013.5.17 H.Mizuno トークンチェック対応 (CSRF 対応） start
	private boolean isInvalidToken(FilterCommandChain chain,
					HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean isError = false;
		
		// トークンチェックが無効化されている場合、常にＯＫを返す
		if (this.useTokenCheck == false) return false;


		// トークンチェックのアノテーションが存在する場合、チェックを実行する。
		UseTokenCheck annotation = chain.getUseTokenCheckAnnotation();
		if (annotation != null){

			// トークンの照合チェック
			isError = GenerateTokenId.hasError(request, this.tokenName);

			// エラーの場合、アノテーションで指定された URL へリダイレクトする
			if (isError){
				log.warn("token error(annotation check). token is " + request.getParameter(this.tokenName));

	            response.sendRedirect(ServletUtils.fixPartialURL(
	                    request.getContextPath() + (String)annotation.value(), request));
			}

			if (removeAfterToken){
				// チェックが正常終了した場合、トークンをセッションから削除する。
				GenerateTokenId.removeId(request, this.tokenName);
			}
		
		}

		return isError;
	}
	// 1013.5.17 H.Mizuno トークンチェック対応 (CSRF 対応） end
}
