/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.error;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.mail.ReplacingMail;
import jp.co.transcosmos.dm3.utils.ServletUtils;

/**
 * An extension to the standard ReplacingMail template object, adding extra error
 * related attributes to make rendering simpler. This normally is only used internally
 * by the ErrorServlet.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: ErrorMail.java,v 1.2 2007/05/31 10:31:51 rick Exp $
 */
public class ErrorMail extends ReplacingMail {

	private HttpServletRequest request;
	private String errorId;
	private Throwable error;
		
	public Throwable getError() {
		return error;
	}

	public void setError(Throwable error) {
		this.error = error;
	}

	public String getErrorId() {
		return errorId;
	}

	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public static void setExtraParams(ReplacingMail mail, HttpServletRequest request, String errorId, Throwable error) {
		if (request != null) {
			mail.setParameter("request", request);
		}
		if (errorId != null) {
			mail.setParameter("errorId", errorId);
		}
		mail.setParameter("localhost", ServletUtils.getLocalhost());
        
        if (error != null) {
            // Stacktrace
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            error.printStackTrace(pw);

            mail.setParameter("stacktrace", sw.toString());
            mail.setParameter("error", error);
        }
	}

	public void send() {
		setExtraParams(this, this.request, this.errorId, this.error);
		super.send();
	}

	public void sendThreaded() {
		setExtraParams(this, this.request, this.errorId, this.error);
		super.sendThreaded();
	}
}
