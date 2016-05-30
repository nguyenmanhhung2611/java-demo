/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.command;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.utils.SimpleExpressionLanguage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

/**
 * Serves static content from a defined prefix path. Acts as a proxy for some static path.
 * <p>
 * The filePrefixPath init-param is used to specify the static content document 
 * root on the application server filesystem. The trimUriPrefix init-param is used
 * to make a prefix on the URI be removed before adding to the static root. (e.g. 
 *  /abc/def/123.txt might have /abc trimmed to make the static path &lt;docroot&gt;/def/123.txt)
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: StaticResourceCommand.java,v 1.4 2012/08/01 09:28:36 tanaka Exp $
 */
public class StaticResourceCommand implements Command, ServletContextAware {
    private static final Log log = LogFactory.getLog(StaticResourceCommand.class);

    private String filePrefixPath;
    private String trimUriPrefix;
    private ServletContext servletContext;
    
    public void setFilePrefixPath(String pFilePrefixPath) {
        this.filePrefixPath = pFilePrefixPath;
    }

    public void setTrimUriPrefix(String pTrimUriPrefix) {
        this.trimUriPrefix = pTrimUriPrefix;
    }

    public void setServletContext(ServletContext pServletContext) {
        this.servletContext = pServletContext;
    }

    public ModelAndView handleRequest(HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
        if (this.filePrefixPath == null) {
            // otherwise 404
            response.sendError(HttpServletResponse.SC_NOT_FOUND, 
                    "No prefix path defined for URI: " + request.getRequestURI());
            return null;
        } else {
            String prefixPath = SimpleExpressionLanguage.evaluateWebExpressionPatternString(
                    this.filePrefixPath, request, response, this.servletContext, 
                    null, false, request.getCharacterEncoding(), "");
            
            String path = (request.getServletPath() != null ? request.getServletPath() : "") + 
                          (request.getPathInfo() != null ? request.getPathInfo() : "");
            if ((this.trimUriPrefix != null) && 
                    path.toLowerCase().startsWith(this.trimUriPrefix.toLowerCase())) {
                path = path.substring(this.trimUriPrefix.length());
            }
            log.info("Using prefix: " + prefixPath + ", uri=" + path);
            
            File prefix = new File(prefixPath);
            File file = prefix;
            if (path.startsWith("/")) {
                file = new File(prefix, path.substring(1));
            } else if (path.length() > 0) {
                file = new File(prefix, path);
            }
            
            if (file.getCanonicalPath().toLowerCase().startsWith(
                    prefix.getCanonicalPath().toLowerCase())) {
                View view = new StaticResourceView(file, this.servletContext);
                return new ModelAndView(view);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
                        "Illegal URI: " + request.getRequestURI());
                return null;
            }
        }
    }
    
    public class StaticResourceView implements View {
        
        private File file;
        private ServletContext context;
        
        public StaticResourceView(File file, ServletContext context) {
            this.file = file;
            this.context = context;
        }

        public String getContentType() {
            return this.context.getMimeType(this.file.getName());
        }

        public void render(Map model, HttpServletRequest request, 
                HttpServletResponse response) throws Exception {
            InputStream in = null;
            if (!this.file.isFile()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found: " + 
                        this.file.getPath());
            } else try {
                String contentType = getContentType();
                log.info("Serving static file: " + this.file.getPath() + " contentType=" + contentType);
                response.setContentType(contentType);
                response.setContentLength((int) this.file.length());
                in = new FileInputStream(this.file);
                byte buffer[] = new byte[response.getBufferSize()];
                int read = 0;
                OutputStream out = response.getOutputStream();
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }
                response.flushBuffer();
            } finally {
                if (in != null) {
                    try {in.close();} catch (IOException err) {}
                }
            }            
        }
    }
}
