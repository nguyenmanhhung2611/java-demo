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

// 2014.06.27 H.Mizuno 添付ファイルが設定できない問題を修正。　start
// バイト配列とは別に、File オブジェクト用を追加
    private Map<String,File> diskFiles = new HashMap<String,File>();
// 2014.06.27 H.Mizuno 添付ファイルが設定できない問題を修正。　end


// 2014.03.12 H.Mizuno RFC 非準拠メールアドレスへの対応 start 
    // RFC 非準拠のメールアドレスへの対応を有効する場合は、このプロパティを true に変更する。
    private boolean useChgRfcAddress = false;
// 2014.03.12 H.Mizuno RFC 非準拠メールアドレスへの対応 end 


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

// 2014.03.12 H.Mizuno RFC 非準拠メールアドレスへの対応 start 
    public boolean isUseChgRfcAddress() {
		return useChgRfcAddress;
	}

	public void setUseChgRfcAddress(boolean useChgRfcAddress) {
		this.useChgRfcAddress = useChgRfcAddress;
	}
// 2014.03.12 H.Mizuno RFC 非準拠メールアドレスへの対応 end 


// 2014.06.27 H.Mizuno ファイルが添付出来ない問題を修正 start
// 欠損していた setter を追加し、新たに追加した File 用の setter も追加
	public void setFiles(Map<String, byte[]> files) {
		this.files = files;
	}

	public void setDiskFiles(Map<String, File> diskFiles) {
		this.diskFiles = diskFiles;
	}
// 2014.06.27 H.Mizuno ファイルが添付出来ない問題を修正 end

	
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
// 2014.06.27 H.Mizuno メールが添付できない問題を修正 start                
                if (this.diskFiles != null) {
                	for (Map.Entry<String, File> entry : this.diskFiles.entrySet()){
                		mail.addFile(entry.getValue(), entry.getKey());
                	}
                }
// 2014.06.27 H.Mizuno メールが添付できない問題を修正 end                
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
// 2014.03.12 H.Mizuno RFC 非準拠メールアドレスへの対応 start
//                return new InternetAddress(address.trim());
              return new InternetAddress(chgRfcAddress(address.trim()));
// 2014.03.12 H.Mizuno RFC 非準拠メールアドレスへの対応 end
            }
            int closePos = address.indexOf('>', openPos);
            if (closePos == -1) {
// 2014.03.12 H.Mizuno RFC 非準拠メールアドレスへの対応 start
//                return new InternetAddress(address.substring(openPos + 1).trim(),
//                        address.substring(0, openPos).trim(), encoding);
                return new InternetAddress(chgRfcAddress(address.substring(openPos + 1).trim()),
                        address.substring(0, openPos).trim(), encoding);
// 2014.03.12 H.Mizuno RFC 非準拠メールアドレスへの対応 end
            } else {
// 2014.03.12 H.Mizuno RFC 非準拠メールアドレスへの対応 start
//                return new InternetAddress(address.substring(openPos + 1, closePos).trim(),
//                        address.substring(0, openPos).trim(), encoding);
                return new InternetAddress(chgRfcAddress(address.substring(openPos + 1, closePos).trim()),
                        address.substring(0, openPos).trim(), encoding);
// 2014.03.12 H.Mizuno RFC 非準拠メールアドレスへの対応 end
            }
        } catch (UnsupportedEncodingException err) {
            throw new IllegalArgumentException("Error parsing address string");
        } catch (AddressException err) {
            throw new IllegalArgumentException("Error parsing address string");
        }
    }
    
    
// 2014.03.12 H.Mizuno RFC 非準拠メールアドレスへの対応 start

    // RFC に準拠しない携帯メールアドレスへの対応。
    // RFC に準拠しない携帯メールアドレスの場合、ユーザー部をダブルコードで囲む置換を行う。
    // 置換する条件は、下記の通り。
    //   ・ユーザー部で連続したピリオドが使用されている場合　（例：「hoge..hoge@trans-cosmos.co.jp」）
    //   ・ユーザー部の先頭と最後でピリオドを使用している場合　（例：「hoge.@trans-cosmos.co.jp」）
    //    ※ユーザー部の先頭と末尾の記号文字は基本的にNGだが、現時点ではピリオドのみ対応。）
    // 変換例：hoge..hoge@trans-cosmos.co.jp　=> "hoge..hoge"@trans-cosmos.co.jp
    // 
    // 注）この置換を行ってもメールが届かない場合があるので注意する事。
    //    また、メールアドレスの必要なバリデーションは先に行っている事を前提とする。
    // 注）この関数は、「XXXXX<hoge@trans-cosmos.co.jo>」のようなメールアドレスの引渡しを前提としてい
    //    ない。　（呼び出し元である、InternetAddress() 側で分割されている事を前提としている為。）
    private String chgRfcAddress(String address){

    	// 機能が無効な場合はなにもしない
    	if (!useChgRfcAddress) return address;
    	log.info("use chgRfcAddress()");

    	// パラメータが空の場合は何もしない
    	if (address == null || address.length() == 0 ) return address;


    	// メールアドレスを、アットマーク記号で分割する
    	String addressParts[] = address.split("@");

    	// 分割した結果が２個以外の場合は何もしない　（アットマークが無いか、複数存在するケース
    	// 注） 「hoge@hoge@trans-cosmos.co.jp」の場合でも、 「"hoge@hoge"@trans-cosmos.co.jp」
    	//    のように加工すれば対応可能になるが、誤送信のリスクもあるので、フレームワーク側では
    	//    対応しない。　（必要であれば、呼び出し元で対応する。）
    	if (addressParts.length != 2) return address;


    	// ユーザー部の先頭と末尾がダブルコートの場合は何もしない 　（既に変換済のケース）
    	if (addressParts[0].startsWith("\"") && addressParts[0].endsWith("\"")) return address;

    	// ユーザー部の先頭もしくは、末尾がピリオドの場合、または、連続するピリオドが使用されている場合、ユーザー部をダブルコードで囲む
    	if (addressParts[0].startsWith(".") || addressParts[0].endsWith(".") || addressParts[0].indexOf("..") > -1) {
        	String retAddress = "\"" + addressParts[0] + "\"@" + addressParts[1]; 

        	log.info("mail address change: " + address + " to " + retAddress);
        	return retAddress;
    	}

    	// 該当しない場合はそのまま復帰。
    	return address;
    }
// 2014.03.12 H.Mizuno RFC 非準拠メールアドレスへの対応 end
    
    
    public void setMailTemplateXml(String location) {
        this.mailTemplateXml = location;
    }
    
    public void setParameter(String key, Object value) {
        this.parameters.put(key, value);
    }
    
// 2014.10.23 H.Mizuno パラメータを取得する機能を追加 start    
    public Object getParameter(String key) {
        return this.parameters.get(key);
    }
// 2014.10.23 H.Mizuno パラメータを取得する機能を追加 end    

    
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
