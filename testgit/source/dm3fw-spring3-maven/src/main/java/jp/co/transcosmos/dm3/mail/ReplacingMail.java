/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.mail;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.springframework.web.context.ServletContextAware;

import com.ozacc.mail.Mail;
import com.ozacc.mail.SendMail;
import com.ozacc.mail.impl.MimeMessageBuilder;

/**
 * Holds the variables that need replacing in a mail template before sending to 
 * the system administrator. This class should ideally be mounted as a "prototype"
 * scope object in spring, and called by the error servlet when we want to send an
 * error mail.
 * <p>
 * It is NOT THREADSAFE.
 * <p>
 * See the mail sending tutorial for more information: (dm3fw-spring2-blog/docs/mailsending.html)
 *
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: ReplacingMail.java,v 1.4 2008/09/18 02:04:31 abe Exp $
 */
public class ReplacingMail implements ServletContextAware {
    private static final Log log = LogFactory.getLog(ReplacingMail.class);

    private ServletContext context; //used to specify webroot-relative paths in spring
    private SendMail ozaccMailSender;
    private Properties mailProperties;
    private boolean velocityReplaceOverrides = true;
    
    private List<String> userAgent;
    
    private List<String> toList;
    private List<String> ccList;
    private List<String> bccList;
    private String fromAddress;
    private String subject;
    private String bodyText;
    private String bodyHtml;
    private String returnPath;
    private String replyTo;
    private String errorTo;
    private String xMailer;
    
    private String mailTemplateXml;
    private boolean mailSessionDebug;
    private Map<String,Object> parameters = new HashMap<String,Object>();

    private Map<String,byte[]> files = new HashMap<String,byte[]>();

// 2014.06.27 H.Mizuno �Y�t�t�@�C�����ݒ�ł��Ȃ������C���B�@start
// �o�C�g�z��Ƃ͕ʂɁAFile �I�u�W�F�N�g�p��ǉ�
    private Map<String,File> diskFiles = new HashMap<String,File>();
// 2014.06.27 H.Mizuno �Y�t�t�@�C�����ݒ�ł��Ȃ������C���B�@end


// 2014.03.12 H.Mizuno RFC �񏀋����[���A�h���X�ւ̑Ή� start 
    // RFC �񏀋��̃��[���A�h���X�ւ̑Ή���L������ꍇ�́A���̃v���p�e�B�� true �ɕύX����B
    private boolean useChgRfcAddress = false;
// 2014.03.12 H.Mizuno RFC �񏀋����[���A�h���X�ւ̑Ή� end 


    public List<String> getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(List<String> userAgent) {
		this.userAgent = userAgent;
	}

	public void setToList(List<String> pToList) {
        this.toList = pToList;
    }

    public void setCcList(List<String> pCcList) {
        this.ccList = pCcList;
    }

    public void setBccList(List<String> pBccList) {
        this.bccList = pBccList;
    }

    public void setFromAddress(String pFromAddress) {
        this.fromAddress = pFromAddress;
    }

    public void setSubject(String pSubject) {
        this.subject = pSubject;
    }

    public void setBodyText(String pBodyText) {
        this.bodyText = pBodyText;
    }

    public void setBodyHtml(String pBodyHtml) {
        this.bodyHtml = pBodyHtml;
    }
    
    public void setOzaccMailSender(SendMail pOzaccMailSender) {
        this.ozaccMailSender = pOzaccMailSender;
    }

    public SendMail getOzaccMailSender() {
        return this.ozaccMailSender;
    }

    public Properties getMailProperties() {
        return this.mailProperties;
    }

    public void setMailProperties(Properties pMailProperties) {
        this.mailProperties = pMailProperties;
    }

    public boolean isVelocityReplaceOverrides() {
        return this.velocityReplaceOverrides;
    }

    public void setVelocityReplaceOverrides(boolean pVelocityReplace) {
        this.velocityReplaceOverrides = pVelocityReplace;
    }

    public void setMailSessionDebug(boolean pMailSessionDebug) {
        this.mailSessionDebug = pMailSessionDebug;
    }

    public void setErrorTo(String pErrorTo) {
        this.errorTo = pErrorTo;
    }

    public void setReplyTo(String pReplyTo) {
        this.replyTo = pReplyTo;
    }

    public void setReturnPath(String pReturnPath) {
        this.returnPath = pReturnPath;
    }

    public void setXMailer(String pMailer) {
        this.xMailer = pMailer;
    }

// 2014.03.12 H.Mizuno RFC �񏀋����[���A�h���X�ւ̑Ή� start 
    public boolean isUseChgRfcAddress() {
		return useChgRfcAddress;
	}

	public void setUseChgRfcAddress(boolean useChgRfcAddress) {
		this.useChgRfcAddress = useChgRfcAddress;
	}
// 2014.03.12 H.Mizuno RFC �񏀋����[���A�h���X�ւ̑Ή� end 


// 2014.06.27 H.Mizuno �t�@�C�����Y�t�o���Ȃ������C�� start
// �������Ă��� setter ��ǉ����A�V���ɒǉ����� File �p�� setter ���ǉ�
	public void setFiles(Map<String, byte[]> files) {
		this.files = files;
	}

	public void setDiskFiles(Map<String, File> diskFiles) {
		this.diskFiles = diskFiles;
	}
// 2014.06.27 H.Mizuno �t�@�C�����Y�t�o���Ȃ������C�� end

	
	protected Mail buildMail(Map<String,String> extraHeaders) {
        if (extraHeaders != null) {
            extraHeaders.clear();
        } else {
            extraHeaders = new HashMap<String,String>();
        }

        VelocityEngine engine = null;
        VelocityContext context = new VelocityContext(this.parameters);
        context.put("now", new Date());
        
        Mail mail = null;
        if (this.mailTemplateXml != null) {
            File location = null;
            if (this.context != null) {
                location = new File(this.context.getRealPath("/"), this.mailTemplateXml);
            } else {
                location = new File(this.mailTemplateXml);
            }
            engine = initEngine();
            mail = new MailBuilder(extraHeaders, location.getAbsolutePath(), engine).buildMail(
                    location, context);
        } else {
            mail = new Mail();
        }
        
        if (isVelocityReplaceOverrides()) {
            try {
                if (this.fromAddress != null) {
                    if (engine == null) {
                        engine = initEngine();
                    }
                    mail.setFrom(transformToAddress(
                            replace(engine, context, "from", this.fromAddress), 
                            mail.getCharset()));
                }        
                if (this.returnPath != null) {
                    if (engine == null) {
                        engine = initEngine();
                    }
                    mail.setReturnPath(transformToAddress(
                            replace(engine, context, "returnPath", this.returnPath), 
                            mail.getCharset()));
                }       
                if (this.replyTo != null) {
                    if (engine == null) {
                        engine = initEngine();
                    }
                    mail.setReplyTo(transformToAddress(
                            replace(engine, context, "replyTo", this.replyTo), 
                            mail.getCharset()));
                }
                if (this.toList != null) {
                    if (engine == null) {
                        engine = initEngine();
                    }
                    for (Iterator<String> i = this.toList.iterator(); i.hasNext(); ) {
                        mail.addTo(transformToAddress(
                                replace(engine, context, "to", i.next()), 
                                mail.getCharset()));
                    }
                }
                if (this.ccList != null) {
                    if (engine == null) {
                        engine = initEngine();
                    }
                    for (Iterator<String> i = this.ccList.iterator(); i.hasNext(); ) {
                        mail.addCc(transformToAddress(
                                replace(engine, context, "cc", i.next()), 
                                mail.getCharset()));
                    }
                }
                if (this.bccList != null) {
                    if (engine == null) {
                        engine = initEngine();
                    }
                    for (Iterator<String> i = this.bccList.iterator(); i.hasNext(); ) {
                        mail.addBcc(transformToAddress(
                                replace(engine, context, "bcc", i.next()), 
                                mail.getCharset()));
                    }
                }
                if (this.files != null) {
                    for (Iterator<String> i = this.files.keySet().iterator(); i.hasNext(); ) {
                        String name = i.next();
                        mail.addFile(this.files.get(name), name);
                    }
                }
// 2014.06.27 H.Mizuno ���[�����Y�t�ł��Ȃ������C�� start                
                if (this.diskFiles != null) {
                	for (Map.Entry<String, File> entry : this.diskFiles.entrySet()){
                		mail.addFile(entry.getValue(), entry.getKey());
                	}
                }
// 2014.06.27 H.Mizuno ���[�����Y�t�ł��Ȃ������C�� end                
                if (this.errorTo != null) {
                    if (engine == null) {
                        engine = initEngine();
                    }
                    extraHeaders.put("Error-to", replace(engine, context, "errorTo", this.errorTo));
                }  
                if (this.xMailer != null) {
                    if (engine == null) {
                        engine = initEngine();
                    }
                    extraHeaders.put("X-Mailer", replace(engine, context, "xMailer", this.xMailer));
                }

                if (this.bodyText != null) {
                    if (engine == null) {
                        engine = initEngine();
                    }
                    mail.setText(replace(engine, context, "bodyText", this.bodyText));
                }
                if (this.bodyHtml != null) {
                    if (engine == null) {
                        engine = initEngine();
                    }
                    mail.setHtmlText(replace(engine, context, "bodyHtml", this.bodyHtml));
                }
                if (this.subject != null) {
                    if (engine == null) {
                        engine = initEngine();
                    }
                    mail.setSubject(replace(engine, context, "subject", this.subject));
                }
            } catch (RuntimeException err) {
                throw err;
            } catch (Exception err) {
                throw new RuntimeException("Error rendering mail template with velocity", err);
            }
        } else {
            // Set manually
            if (this.fromAddress != null) {
                mail.setFrom(transformToAddress(this.fromAddress, mail.getCharset()));
            }
            if (this.returnPath != null) {
                mail.setReturnPath(transformToAddress(this.returnPath, mail.getCharset()));
            }
            if (this.replyTo != null) {
                mail.setReplyTo(transformToAddress(this.replyTo, mail.getCharset()));
            }
            if (this.toList != null) {
                for (Iterator<String> i = this.toList.iterator(); i.hasNext(); ) {
                    mail.addTo(transformToAddress(i.next(), mail.getCharset()));
                }
            }
            if (this.ccList != null) {
                for (Iterator<String> i = this.ccList.iterator(); i.hasNext(); ) {
                    mail.addCc(transformToAddress(i.next(), mail.getCharset()));
                }
            }
            if (this.bccList != null) {
                for (Iterator<String> i = this.bccList.iterator(); i.hasNext(); ) {
                    mail.addBcc(transformToAddress(i.next(), mail.getCharset()));
                }
            }
            if (this.files != null) {
                for (Iterator<String> i = this.files.keySet().iterator(); i.hasNext(); ) {
                    String name = i.next();
                    mail.addFile(this.files.get(name), name);
                }
            }
            if (this.errorTo != null) {
                extraHeaders.put("Error-to", this.errorTo);
            }  
            if (this.xMailer != null) {
                extraHeaders.put("X-Mailer", this.xMailer);
            }

            if (this.bodyText != null) {
                mail.setText(this.bodyText);
            }
            if (this.bodyHtml != null) {
                mail.setHtmlText(this.bodyHtml);
            }
            if (this.subject != null) {
                mail.setSubject(this.subject);
            }            
        }
        if (mail.getText() != null) {
            mail.setText(mail.getText() + "\n");
        }
        
        return mail;
    }
    
	private VelocityEngine initEngine() {
        try {
            VelocityEngine engine = new VelocityEngine();
            engine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
                    "org.apache.velocity.runtime.log.SimpleLog4JLogSystem" );
            engine.setProperty("runtime.log.logsystem.log4j.category", 
                    ReplacingMail.class.getName() + ".velocity");
            engine.init();        
            return engine;
        } catch (Exception err) {
            throw new RuntimeException("Error initializing velocity", err);
        }
    }
    
    private static String replace(VelocityEngine engine, VelocityContext context, 
            String logTag, String pattern) throws Exception {
        if ((pattern == null) || pattern.equals("")) {
            return "";
        }
        StringWriter out = new StringWriter();
        if (!engine.evaluate(context, out, logTag, new StringReader(pattern))) {
            throw new RuntimeException("Errors rendering the mail " + 
                    logTag + " with velocity - see log");
        } else {
            return out.toString();
        }
    }
//
//    private static void replaceInternetAddress(InternetAddress internetAddress,
//            VelocityContext context, VelocityEngine engine, String logTag, String encoding) 
//            throws Exception {
//        if (internetAddress == null) {
//            return;
//        }
//        internetAddress.setAddress(replace(engine, context, logTag, 
//                internetAddress.getAddress()));
//        if (internetAddress.getPersonal() != null) {
//            internetAddress.setPersonal(replace(engine, context, logTag, 
//                    internetAddress.getPersonal()), encoding);
//        }
//    }
    
    public void setServletContext(ServletContext context) {
        this.context = context;
    }
    
    private InternetAddress transformToAddress(String address, String encoding) {
        try {
            int openPos = address.indexOf('<');
            if (openPos == -1) {
// 2014.03.12 H.Mizuno RFC �񏀋����[���A�h���X�ւ̑Ή� start
//                return new InternetAddress(address.trim());
              return new InternetAddress(chgRfcAddress(address.trim()));
// 2014.03.12 H.Mizuno RFC �񏀋����[���A�h���X�ւ̑Ή� end
            }
            int closePos = address.indexOf('>', openPos);
            if (closePos == -1) {
// 2014.03.12 H.Mizuno RFC �񏀋����[���A�h���X�ւ̑Ή� start
//                return new InternetAddress(address.substring(openPos + 1).trim(),
//                        address.substring(0, openPos).trim(), encoding);
                return new InternetAddress(chgRfcAddress(address.substring(openPos + 1).trim()),
                        address.substring(0, openPos).trim(), encoding);
// 2014.03.12 H.Mizuno RFC �񏀋����[���A�h���X�ւ̑Ή� end
            } else {
// 2014.03.12 H.Mizuno RFC �񏀋����[���A�h���X�ւ̑Ή� start
//                return new InternetAddress(address.substring(openPos + 1, closePos).trim(),
//                        address.substring(0, openPos).trim(), encoding);
                return new InternetAddress(chgRfcAddress(address.substring(openPos + 1, closePos).trim()),
                        address.substring(0, openPos).trim(), encoding);
// 2014.03.12 H.Mizuno RFC �񏀋����[���A�h���X�ւ̑Ή� end
            }
        } catch (UnsupportedEncodingException err) {
            throw new IllegalArgumentException("Error parsing address string");
        } catch (AddressException err) {
            throw new IllegalArgumentException("Error parsing address string");
        }
    }
    
    
// 2014.03.12 H.Mizuno RFC �񏀋����[���A�h���X�ւ̑Ή� start

    // RFC �ɏ������Ȃ��g�у��[���A�h���X�ւ̑Ή��B
    // RFC �ɏ������Ȃ��g�у��[���A�h���X�̏ꍇ�A���[�U�[�����_�u���R�[�h�ň͂ޒu�����s���B
    // �u����������́A���L�̒ʂ�B
    //   �E���[�U�[���ŘA�������s���I�h���g�p����Ă���ꍇ�@�i��F�uhoge..hoge@trans-cosmos.co.jp�v�j
    //   �E���[�U�[���̐擪�ƍŌ�Ńs���I�h���g�p���Ă���ꍇ�@�i��F�uhoge.@trans-cosmos.co.jp�v�j
    //    �����[�U�[���̐擪�Ɩ����̋L�������͊�{�I��NG�����A�����_�ł̓s���I�h�̂ݑΉ��B�j
    // �ϊ���Fhoge..hoge@trans-cosmos.co.jp�@=> "hoge..hoge"@trans-cosmos.co.jp
    // 
    // ���j���̒u�����s���Ă����[�����͂��Ȃ��ꍇ������̂Œ��ӂ��鎖�B
    //    �܂��A���[���A�h���X�̕K�v�ȃo���f�[�V�����͐�ɍs���Ă��鎖��O��Ƃ���B
    // ���j���̊֐��́A�uXXXXX<hoge@trans-cosmos.co.jo>�v�̂悤�ȃ��[���A�h���X�̈��n����O��Ƃ��Ă�
    //    �Ȃ��B�@�i�Ăяo�����ł���AInternetAddress() ���ŕ�������Ă��鎖��O��Ƃ��Ă���ׁB�j
    private String chgRfcAddress(String address){

    	// �@�\�������ȏꍇ�͂Ȃɂ����Ȃ�
    	if (!useChgRfcAddress) return address;
    	log.info("use chgRfcAddress()");

    	// �p�����[�^����̏ꍇ�͉������Ȃ�
    	if (address == null || address.length() == 0 ) return address;


    	// ���[���A�h���X���A�A�b�g�}�[�N�L���ŕ�������
    	String addressParts[] = address.split("@");

    	// �����������ʂ��Q�ȊO�̏ꍇ�͉������Ȃ��@�i�A�b�g�}�[�N���������A�������݂���P�[�X
    	// ���j �uhoge@hoge@trans-cosmos.co.jp�v�̏ꍇ�ł��A �u"hoge@hoge"@trans-cosmos.co.jp�v
    	//    �̂悤�ɉ��H����ΑΉ��\�ɂȂ邪�A�둗�M�̃��X�N������̂ŁA�t���[�����[�N���ł�
    	//    �Ή����Ȃ��B�@�i�K�v�ł���΁A�Ăяo�����őΉ�����B�j
    	if (addressParts.length != 2) return address;


    	// ���[�U�[���̐擪�Ɩ������_�u���R�[�g�̏ꍇ�͉������Ȃ� �@�i���ɕϊ��ς̃P�[�X�j
    	if (addressParts[0].startsWith("\"") && addressParts[0].endsWith("\"")) return address;

    	// ���[�U�[���̐擪�������́A�������s���I�h�̏ꍇ�A�܂��́A�A������s���I�h���g�p����Ă���ꍇ�A���[�U�[�����_�u���R�[�h�ň͂�
    	if (addressParts[0].startsWith(".") || addressParts[0].endsWith(".") || addressParts[0].indexOf("..") > -1) {
        	String retAddress = "\"" + addressParts[0] + "\"@" + addressParts[1]; 

        	log.info("mail address change: " + address + " to " + retAddress);
        	return retAddress;
    	}

    	// �Y�����Ȃ��ꍇ�͂��̂܂ܕ��A�B
    	return address;
    }
// 2014.03.12 H.Mizuno RFC �񏀋����[���A�h���X�ւ̑Ή� end
    
    
    public void setMailTemplateXml(String location) {
        this.mailTemplateXml = location;
    }
    
    public void setParameter(String key, Object value) {
        this.parameters.put(key, value);
    }
    
// 2014.10.23 H.Mizuno �p�����[�^���擾����@�\��ǉ� start    
    public Object getParameter(String key) {
        return this.parameters.get(key);
    }
// 2014.10.23 H.Mizuno �p�����[�^���擾����@�\��ǉ� end    

    
    public void send() {
        // Transfer to ozacc mail, then thread off
        Map<String,String> extraHeaders = new HashMap<String,String>();
        Mail mail = buildMail(extraHeaders);
        try {
            new MailSendingRunnable(mail, this.ozaccMailSender, 
                    this.mailProperties, extraHeaders).send();
        } catch (UnsupportedEncodingException err) {
            throw new RuntimeException("Error sending mail", err);
        } catch (MessagingException err) {
            throw new RuntimeException("Error sending mail", err);
        }
    }
    
    public void sendThreaded() {
        // Transfer to ozacc mail, then thread off
        Map<String,String> extraHeaders = new HashMap<String,String>();
        Mail mail = buildMail(extraHeaders);
        Thread thread = new Thread(new MailSendingRunnable(mail, 
                this.ozaccMailSender, this.mailProperties, extraHeaders));
        thread.setDaemon(true);
        thread.start();        
    }
    
    /**
     * This runnable allows us to thread off the sending via javamail/ozacc, so we
     * don't block while it's talking to the smtp server
     */
    class MailSendingRunnable implements Runnable {
        private Mail mail;
        private SendMail sendMail;
        private Properties javaMailProperties;
        private Map<String,String> extraHeaders;
        
        MailSendingRunnable(Mail mail, SendMail sendMail, 
                Properties javaMailProperties, Map<String,String> extraHeaders) {
            this.mail = mail;
            this.sendMail = sendMail;
            this.javaMailProperties = javaMailProperties;
            this.extraHeaders = extraHeaders;
        }
        
        public void run() {
            try {
                send();
            } catch (Throwable err) {
                log.error("Error sending mail", err);
            }
        }
        
        public void send() throws MessagingException, UnsupportedEncodingException {

            if (this.sendMail != null) {
                log.info("Sending via Ozacc mail: " + this.mail);

                // This method for getting a session is not good, but it is the same 
                // thing the ozacc component does internally ... shoganai ...
                MimeMessage mimeMessage = new MimeMessage(
                        Session.getInstance(new Properties()));
                MimeMessageBuilder builder = new MimeMessageBuilder(mimeMessage);
                builder.buildMimeMessage(this.mail);
                addExtraHeaders(mimeMessage);
                this.sendMail.send(mimeMessage);
            } else {
                // This is intended as a fallback for the case where no ozacc 
                // mail sender is available
                Properties mailProps = (this.javaMailProperties == null ? new Properties() : 
                    this.javaMailProperties);
                log.info("Sending via direct javamail connection: " + this.mail + 
                        ", mailProps=" + mailProps);
                
                Session mailSession = Session.getInstance(mailProps);
                mailSession.setDebug(mailSessionDebug);
                
                MimeMessage mimeMessage = new MimeMessage(mailSession);
                MimeMessageBuilder builder = new MimeMessageBuilder(mimeMessage);
                builder.buildMimeMessage(mail);
                addExtraHeaders(mimeMessage);
                Transport.send(mimeMessage);
            }            
        }
        
        private void addExtraHeaders(MimeMessage mimeMessage) throws MessagingException {
            for (Iterator<String> i = this.extraHeaders.keySet().iterator(); i.hasNext(); ) {
                String key = i.next();
                mimeMessage.addHeader(key, this.extraHeaders.get(key));
            }
        }
    }
}
