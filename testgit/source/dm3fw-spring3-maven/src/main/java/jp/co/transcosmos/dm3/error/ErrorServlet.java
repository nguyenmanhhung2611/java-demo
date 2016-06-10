/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.error;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.mail.ReplacingMail;
import jp.co.transcosmos.dm3.utils.SimpleExpressionLanguage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.WebApplicationContext;

import com.ozacc.mail.SendMail;

/**
 * Creates an error mail, and sends it via a Spring Ozacc mailer if one 
 * is available. If none found or there was an error accessing it, sends manually.
 * <p>
 * After the mail is sent, redirects to the defined error page.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: ErrorServlet.java,v 1.4 2008/09/18 02:04:31 abe Exp $
 */
public class ErrorServlet extends HttpServlet {
    private static final Log log = LogFactory.getLog(ErrorServlet.class);
    
    private static final long serialVersionUID = 454257932009610473L;
    private static final String ERROR_ID_ATTRIBUTE = "ErrorServlet.ErrorID";
    
    private String errorPage;
    private String mailSenderBeanName;
    private String mailTemplateBeanName;
    private String mailTemplateXMLFile;
    private String mailServerAddress;
    private String errorIdPattern = "ERR_###now###";
    
// 2014.03.11 H.Mizuno �G���[�T�[�u���b�g �v���p�e�B�ǉ� start
    // web.xml ���� init-param �ł��̃I�v�V������ݒ肷��ƁA�w�肳�ꂽ bean ���̃I�u�W�F�N�g�� spring
    // �� applicationContext ����擾����B�@�擾�o�����ꍇ�A�擾�����l�����N�G�X�g�X�R�[�v�֊i�[����B
    private String optionBeans;
// 2014.03.11 H.Mizuno �G���[�T�[�u���b�g �v���p�e�B�ǉ� end
    
    public void init() throws ServletException {
        super.init();
        this.errorPage = getServletConfig().getInitParameter("errorPage");
        this.mailSenderBeanName = getServletConfig().getInitParameter("mailSenderBeanName");
        this.mailTemplateBeanName = getServletConfig().getInitParameter("mailTemplateBeanName");
        this.mailTemplateXMLFile = getServletConfig().getInitParameter("mailTemplateXMLFile");
        this.mailServerAddress = getServletConfig().getInitParameter("mailServerAddress");
        this.errorIdPattern = getServletConfig().getInitParameter("errorIdPattern");
// 2014.03.11 H.Mizuno �G���[�T�[�u���b�g �v���p�e�B�ǉ� start
        this.optionBeans = getServletConfig().getInitParameter("optionBeans");
// 2014.03.11 H.Mizuno �G���[�T�[�u���b�g �v���p�e�B�ǉ� end
        
        // Defaults
        if (this.mailSenderBeanName == null) {
            this.mailSenderBeanName = "sendMail";
        }
        if (this.mailTemplateBeanName == null) {
            this.mailTemplateBeanName = "errorMailTemplate";
        }
        if (this.mailServerAddress == null) {
            this.mailServerAddress = "localhost";
        }
        if (this.errorIdPattern == null) {
            this.errorIdPattern = "ERR_###now###";
        }
    }

    public void destroy() {
        this.errorPage = null;
        this.mailSenderBeanName = "sendMail";
        this.mailTemplateBeanName = "errorMailTemplate";
        this.mailTemplateXMLFile = "/WEB-INF/errorMail.xml";
        this.mailServerAddress = "localhost";
        this.errorIdPattern = "ERR_###now###";
        super.destroy();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doAction(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doAction(request, response);
    }

    protected void doAction(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String errorId = (String) request.getAttribute(ERROR_ID_ATTRIBUTE);
        if (errorId == null) {
            errorId = generateErrorId(this.errorIdPattern, request, response);
            request.setAttribute(ERROR_ID_ATTRIBUTE, errorId);
        }
        log.error("ErrorID:" + errorId);
        
// 2014.03.11 H.Mizuno �G���[�T�[�u���b�g �v���p�e�B�ǉ� start
        optionPropertySetting(request);
// 2014.03.11 H.Mizuno �G���[�T�[�u���b�g �v���p�e�B�ǉ� end
        
        // Build the mail
        sendMail(request, errorId, (Throwable) request.getAttribute(
                "javax.servlet.error.exception"));
        
        if (this.errorPage == null) {
            throw new ServletException("The error servlet redirect page was undefined");
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(this.errorPage);
        if (dispatcher == null) {
            throw new ServletException("The error servlet redirect page was " +
                    "unavailable: errorPage" + this.errorPage);
        }
        dispatcher.forward(request, response);
    }
    
    protected String generateErrorId(String errorIdPattern, HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {
        return SimpleExpressionLanguage.evaluateWebExpressionPatternString(
                this.errorIdPattern, request, response, getServletContext(), null, 
                false, response.getCharacterEncoding(), "");
    }
    
    protected void sendMail(HttpServletRequest request, String errorId, Throwable error)
            throws ServletException, IOException {

        ReplacingMail mail = null;
        
        BeanFactory beanFactory = getSpringContainer();
        if (beanFactory != null) {            
            // Try to use ozacc to send the mail
            if (this.mailTemplateBeanName != null) {
                try {
                    mail = (ReplacingMail) beanFactory.getBean(this.mailTemplateBeanName);
                } catch (Throwable err) {
                    log.error("Error getting mail template from spring: " + this.mailTemplateBeanName);
                    mail = null;
                }
            }
            
            //2008.03.21 N.Abe Add Start -----------
            if(mail != null){
            	List<String> listUserAgent = mail.getUserAgent();
            	String userAgent = request.getHeader("User-agent");
            	if(listUserAgent != null && userAgent != null){
                    for (Iterator<String> i = listUserAgent.iterator(); i.hasNext(); ) {
                    	String xmlUserAgent = i.next().toLowerCase();
                    	if(userAgent.toLowerCase().indexOf(xmlUserAgent) != -1){
                    		log.debug("Not Send ErrorMail: User-agent=" + userAgent);
                    		return;
                    	}
                    }            	
            	}
            }
            //2008.03.21 N.Abe Add End -----------

            if ((mail == null) && (this.mailTemplateXMLFile != null)) {
                mail = new ReplacingMail();
                mail.setServletContext(getServletContext());
                mail.setMailTemplateXml(this.mailTemplateXMLFile);
            }
            // Set conn details from spring if not supplied
            if ((mail != null) && (mail.getOzaccMailSender() == null) && 
                    (mail.getMailProperties() == null)) {
                if (this.mailSenderBeanName != null) {
                    mail.setOzaccMailSender((SendMail) 
                            beanFactory.getBean(this.mailSenderBeanName));
                } else {
                    mail.setMailProperties(buildMailProperties());
                }                
            }
        }
        // If we are here, assume spring is unavailable - try to send manually
        if (mail == null) {
            if (this.mailTemplateXMLFile == null) {
                log.warn("Mail template not available from spring, not configured");
                return;
            }
            mail = new ReplacingMail();
            mail.setMailProperties(buildMailProperties());
            mail.setServletContext(getServletContext());
            mail.setMailTemplateXml(this.mailTemplateXMLFile);
        }
        ErrorMail.setExtraParams(mail, request, errorId, error);
        mail.sendThreaded();
    }
    
    protected Properties buildMailProperties() {
        if (this.mailServerAddress != null) {
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", this.mailServerAddress);
            return props;
        }
        return null;
    }
    
    protected BeanFactory getSpringContainer() {
        return (BeanFactory) getServletContext().getAttribute(
                WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);        
    }


    protected void optionPropertySetting(HttpServletRequest request) {
        
    	// "optionBeans" �p�����[�^���w�肳��Ă��Ȃ��ꍇ�͉������Ȃ��B
    	if (this.optionBeans == null || this.optionBeans.length() == 0) return;

    	// Spring �� bean �t�@�N�g���[���擾����B�@�擾�ł��Ȃ��ꍇ�͉������Ȃ��B
        BeanFactory beanFactory = getSpringContainer();
        if (beanFactory == null) return;        

    	// "optionBeans" �p�����[�^���w�肳��Ă���ꍇ�A"," �ŕ������� BeanID �Ƃ���
    	String beanIds[] = this.optionBeans.split(",");
    	for (String beanId : beanIds){

            try {
            	// Spring �� applicationContext ����I�u�W�F�N�g���擾����B
            	// �擾�ł����ꍇ�AbeanId ���A�g���r���[�g���Ƃ��ă��N�G�X�g�X�R�[�v�Ɋi�[����B
            	// ����āA���̋@�\���g�p����ꍇ�̓A�g���r���[�g���̏d���ɒ��ӂ��鎖�B
                Object obj = beanFactory.getBean(beanId);
                request.setAttribute(beanId, obj);
            } catch (Throwable err) {
                log.error("Error getting ErrorServlet option bean : " + beanId);
            }

    	}
    }

}
