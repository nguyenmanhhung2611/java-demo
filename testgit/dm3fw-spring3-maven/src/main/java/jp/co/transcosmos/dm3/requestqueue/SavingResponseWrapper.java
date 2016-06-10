/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.requestqueue;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * Copies anything written to this request into the "saved" valueobject in
 * the constructor. The "saved" object can then be used by dumping it's contents into 
 * another response to simulate a "response caching" behaviour.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: SavingResponseWrapper.java,v 1.2 2007/05/31 07:54:21 rick Exp $
 */
public class SavingResponseWrapper extends HttpServletResponseWrapper {

    private SavedResponseData saved;
    private SavingOutputStream wrapperStream;
    private SavingOutputWriter wrapperWriter;
    
    public SavingResponseWrapper(HttpServletResponse response, SavedResponseData saved) {
        super(response);
        this.saved = saved;
    }

    public void addCookie(Cookie cookie) {
        super.addCookie(cookie);
        this.saved.addCookie(cookie);
    }

    public void addDateHeader(String name, long value) {
        super.addDateHeader(name, value);
        this.saved.addDateHeader(name, value);
    }

    public void addHeader(String name, String value) {
        super.addHeader(name, value);
        this.saved.addHeader(name, value);
    }

    public void addIntHeader(String name, int value) {
        super.addIntHeader(name, value);
        this.saved.addIntHeader(name, value);
    }

    public void sendError(int name, String msg) throws IOException {
        super.sendError(name, msg);
        this.saved.sendError(name, msg);
    }

    public void sendError(int status) throws IOException {
        super.sendError(status);
        this.saved.sendError(status);
    }

    public void sendRedirect(String url) throws IOException {
        super.sendRedirect(url);
        this.saved.sendRedirect(url);
    }
    
    public void setDateHeader(String name, long value) {        
        super.setDateHeader(name, value);      
        this.saved.setDateHeader(name, value);
    }
    
    public void setHeader(String name, String value) {        
        super.setHeader(name, value);     
        this.saved.setHeader(name, value);
    }
    
    public void setIntHeader(String name, int value) {        
        super.setIntHeader(name, value);    
        this.saved.setIntHeader(name, value);
    }
    
    public void setStatus(int status) {        
        super.setStatus(status);
        this.saved.setStatus(status);
    }
    
    public void flushBuffer() throws IOException {        
        super.flushBuffer();
        if (this.wrapperStream != null) {
            this.wrapperStream.flush();
        } else if (this.wrapperWriter != null) {
            this.wrapperWriter.flush();
        }
    }

    public ServletOutputStream getOutputStream() throws IOException {
        if (this.wrapperWriter != null) {
            throw new IllegalStateException("Called getOutputStream() on a response after getWriter()");
        }
        if (this.wrapperStream == null) {
            this.wrapperStream = new SavingOutputStream(super.getOutputStream(), 
                    this.saved.getSavingOutputStream());
        }
        return this.wrapperStream;
    }

    public PrintWriter getWriter() throws IOException {
        if (this.wrapperStream != null) {
            throw new IllegalStateException("Called getWriter() on a response after getOutputStream()");
        }
        if (this.wrapperWriter == null) {
            this.wrapperWriter = new SavingOutputWriter(super.getWriter(),
                    new PrintWriter(new OutputStreamWriter(this.saved.getSavingOutputStream(), 
                            super.getCharacterEncoding()), true));
        }
        return this.wrapperWriter;
    }

    public void reset() {
        super.reset();
        this.saved.reset();
    }

    public void resetBuffer() {
        super.resetBuffer();
        this.saved.resetBuffer();
    }

    public void setBufferSize(int size) {
        super.setBufferSize(size);
        super.setBufferSize(super.getBufferSize());
    }

    public void setCharacterEncoding(String encoding) {
        super.setCharacterEncoding(encoding);
        this.saved.setCharacterEncoding(super.getCharacterEncoding());
    }

    public void setContentLength(int length) {
        super.setContentLength(length);
        this.saved.setContentLength(length);
    }

    public void setContentType(String type) {
        super.setContentType(type);
        this.saved.setContentType(super.getContentType());
    }

    public void setLocale(Locale locale) {
        super.setLocale(locale);
        this.saved.setLocale(super.getLocale());
    }

    /**
     * @deprecated
     */
    public String encodeRedirectUrl(String pArg0) {
        // TODO Auto-generated method stub
        return super.encodeRedirectUrl(pArg0);
    }

    /**
     * @deprecated
     */
    public String encodeUrl(String pArg0) {
        // TODO Auto-generated method stub
        return super.encodeUrl(pArg0);
    }

    /**
     * @deprecated
     */
    public void setStatus(int pArg0, String pArg1) {        
        super.setStatus(pArg0, pArg1);      
        this.saved.setStatus(pArg0);
    }
}
