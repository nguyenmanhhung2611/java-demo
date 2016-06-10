/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.view;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.utils.ServletUtils;
import jp.co.transcosmos.dm3.utils.SimpleExpressionLanguage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.View;

/**
 * A view that sends an HTTP 302 response "client-redirect" to the browser. This can be
 * used directly (through the new ModelAndView(view) call) or by using the "redirect:"
 * prefix in the url-mapping files.
 * <br>
 * This is used instead of the Spring internal client redirect view because the Spring one 
 * tries to append model and view info to the query string even when we don't want it to.
 */
public class ClientSideRedirectView implements View, ServletContextAware {
    private static final Log log = LogFactory.getLog(ClientSideRedirectView.class);

    private String url;
    private ServletContext context;
// 2013.11.20 H.Mizuno リダイレクト時のレスポンスコード指定対応 start
    // ステータスコード　（デフォルト = 302　は、sendRedirect() と同じ設定。）
    private int statusCode = 302;

    public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
// 2013.11.20 H.Mizuno リダイレクト時のレスポンスコード指定対応 end


	public ClientSideRedirectView(String url) {
        this.url = url;
    }
    
    public ClientSideRedirectView(String url, ServletContext context) {
        this(url);
        this.context = context;
    }

// 2013.11.20 H.Mizuno リダイレクト時のレスポンスコード指定対応 start
	public ClientSideRedirectView(String url, int statusCode) {
		this(url);
		this.statusCode = statusCode;
    }
// 2013.11.20 H.Mizuno リダイレクト時のレスポンスコード指定対応 end

	
    public ServletContext getServletContext() {
        return this.context;
    }
    public void setServletContext(ServletContext pContext) {
        this.context = pContext;
    }

    /**
     * Irrelevant because we send a redirect.
     */
    public String getContentType() {
        return null;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void render(Map model, HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
        if (this.url == null) {
            throw new RuntimeException("No url configured");
        }
        
        if (response.getCharacterEncoding() == null) {
            response.setCharacterEncoding(request.getCharacterEncoding());
        }
        
        String url = ServletUtils.fixPartialURL(
                SimpleExpressionLanguage.evaluateWebExpressionPatternString(
                        this.url, request, response, getServletContext(), model, 
                        true, response.getCharacterEncoding(), ""), request);
        log.info("Client side redirecting to: " + url);

        
// 2013.11.20 H.Mizuno ステータスコードを設定可能に変更 start
//      response.sendRedirect(url);

        // 一応、300番台以外の値が設定された場合は、302 を設定する。
        if (statusCode < 300 || statusCode > 399 ) {
        	this.statusCode = 302;
        }

        response.setStatus(this.statusCode);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Location", url);
// 2013.11.20 H.Mizuno ステータスコードを設定可能に変更 end
    }
}
