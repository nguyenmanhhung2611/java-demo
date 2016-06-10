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

    
    // 2013.04.23 H.Mizuno ���� URL �}�b�s���O�Ή� Start
    // ���� URL �}�b�s���O��L���ɂ���ꍇ�́A���̃v���p�e�B�� true �ɐݒ肷��B
    private boolean autoMapping = false;
    
    public void setAutoMapping(boolean autoMapping) {
		this.autoMapping = autoMapping;
	}
    // 2013.04.23 H.Mizuno ���� URL �}�b�s���O�Ή� End
    
    
    // 1013.5.17 H.Mizuno �g�[�N���`�F�b�N�Ή� (CSRF �Ή��j start
    // �g�[�N���`�F�b�N��L���ɂ���ꍇ�́A���̃v���p�e�B�� true �ɐݒ肷��B
    private boolean useTokenCheck = false;
    
	public void setUseTokenCheck(boolean useTokenCheck) {
		this.useTokenCheck = useTokenCheck;
	}

	// �g�[�N�����i���N�G�X�g�p�����[�^�A�Z�b�V�������ʁj
	private String tokenName = "dm3token";
	
	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}

	
	// �g�[�N���`�F�b�N������������A�g�[�N�����폜����ꍇ�Atrue ��ݒ肷��B
	// �f�t�H���g�ł�false�i�폜���Ȃ��j�B
	// �Œ�g�[�N���̏ꍇ�� false �̂܂܎g�p���鎖�B
	// �Œ�g�[�N���ɂ��`�F�b�N���A�����I�ɃZ�b�V����������폜���������� true �ɐݒ肷��B
	private boolean removeAfterToken = false;
	
	public void setRemoveAfterToken(boolean removeAfterToken) {
		this.removeAfterToken = removeAfterToken;
	}
	
	
	// 1013.5.17 H.Mizuno �g�[�N���`�F�b�N�Ή� (CSRF �Ή��j end
    
    
    
	public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {        
        FilterCommandChain chain = (FilterCommandChain) request.getAttribute(
                FilterCommandChain.REQUEST_ATTRIBUTE_NAME); 
        if (chain != null) {
            // 1013.5.17 H.Mizuno �g�[�N���`�F�b�N�Ή� (CSRF �Ή��j start
        	if (isInvalidToken(chain, request, response)) return null;
            // 1013.5.17 H.Mizuno �g�[�N���`�F�b�N�Ή� (CSRF �Ή��j end

        	request.removeAttribute(FilterCommandChain.REQUEST_ATTRIBUTE_NAME);
            chain.doFilter(request, response);
            return chain.getResult(request);
        }
        
        // Build chain manually
        chain = commandURLMapper.findCommandAndFilters(request, true);
        if (chain != null) {
            // 1013.5.17 H.Mizuno �g�[�N���`�F�b�N�Ή� (CSRF �Ή��j start
        	if (isInvalidToken(chain, request, response)) return null;
            // 1013.5.17 H.Mizuno �g�[�N���`�F�b�N�Ή� (CSRF �Ή��j end

        	chain.doFilter(request, response);
            return chain.getResult(request);
        }
        
        // 2013.04.23 H.Mizuno ���� URL �}�b�s���O�Ή� Start 
        // URL �}�b�s���O��`����Y�� URL ���擾�ł��Ȃ��ꍇ�AURL ���玩���}�b�s���O�����s����B
        if (autoMapping){
            chain = commandURLMapper.findCommandFromAnnotation(request);
            if (chain != null) {
                // 1013.5.17 H.Mizuno �g�[�N���`�F�b�N�Ή� (CSRF �Ή��j start
            	if (isInvalidToken(chain, request, response)) return null;
                // 1013.5.17 H.Mizuno �g�[�N���`�F�b�N�Ή� (CSRF �Ή��j end

            	chain.doFilter(request, response);
                return chain.getResult(request);
            }
        }
        // 2013.04.23 H.Mizuno ���� URL �}�b�s���O�Ή� End 
        
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


	
	// 1013.5.17 H.Mizuno �g�[�N���`�F�b�N�Ή� (CSRF �Ή��j start
	private boolean isInvalidToken(FilterCommandChain chain,
					HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean isError = false;
		
		// �g�[�N���`�F�b�N������������Ă���ꍇ�A��ɂn�j��Ԃ�
		if (this.useTokenCheck == false) return false;


		// �g�[�N���`�F�b�N�̃A�m�e�[�V���������݂���ꍇ�A�`�F�b�N�����s����B
		UseTokenCheck annotation = chain.getUseTokenCheckAnnotation();
		if (annotation != null){

			// �g�[�N���̏ƍ��`�F�b�N
			isError = GenerateTokenId.hasError(request, this.tokenName);

			// �G���[�̏ꍇ�A�A�m�e�[�V�����Ŏw�肳�ꂽ URL �փ��_�C���N�g����
			if (isError){
				log.warn("token error(annotation check). token is " + request.getParameter(this.tokenName));

	            response.sendRedirect(ServletUtils.fixPartialURL(
	                    request.getContextPath() + (String)annotation.value(), request));
			}

			if (removeAfterToken){
				// �`�F�b�N������I�������ꍇ�A�g�[�N�����Z�b�V��������폜����B
				GenerateTokenId.removeId(request, this.tokenName);
			}
		
		}

		return isError;
	}
	// 1013.5.17 H.Mizuno �g�[�N���`�F�b�N�Ή� (CSRF �Ή��j end
}
