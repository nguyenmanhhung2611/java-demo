/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.mail;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.ozacc.mail.Mail;
import com.ozacc.mail.impl.DTDEntityResolver;
import com.ozacc.mail.impl.XMLVelocityMailBuilderImpl;

/**
 * Extends upon the Ozacc internal velocity mail builder to add support for the missing
 * properties, such as Error-to and X-mailer headers.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: MailBuilder.java,v 1.3 2007/05/31 08:37:33 rick Exp $
 */
public class MailBuilder extends XMLVelocityMailBuilderImpl {
    private static final Log log = LogFactory.getLog(MailBuilder.class);

    private Map<String,String> extraHeaders;
    private String systemId;
    private VelocityEngine engine;
    
    public MailBuilder(Map<String,String> extraHeaders, String systemId, 
            VelocityEngine engine) {
        super();
        this.extraHeaders = extraHeaders;
        this.systemId = systemId;
        this.engine = engine;
    }

// 2015.06.15 ozacc-mail Ver 1.2.X ‘Î‰ž start
//    protected Mail buildMail(Document doc) {
//    	Mail mail = super.buildMail(doc);
//    	Element root = doc.getDocumentElement();
    protected Mail buildMail(Element root) {
    	Mail mail = super.buildMail(root);
// 2015.06.15 ozacc-mail Ver 1.2.X ‘Î‰ž end

        setErrorTo(root, extraHeaders);
        setXMailer(root, extraHeaders);
        return mail;
    }
    
    protected void setErrorTo(Element root, Map<String,String> extraHeaders) {
        NodeList nodes = root.getElementsByTagName("errorTo");
        Element errorTo = (Element)nodes.item(0);
        if (errorTo != null && errorTo.getAttribute("email").length() > 0) {
            extraHeaders.put("Error-to", errorTo.getAttribute("email"));
        }
    }
    
    protected void setXMailer(Element root, Map<String,String> extraHeaders) {
        NodeList nodes = root.getElementsByTagName("xMailer");
        Element xMailer = (Element)nodes.item(0);
        if (xMailer == null) {
            return;
        }
        String body = xMailer.getFirstChild().getNodeValue();
        extraHeaders.put("X-Mailer", body.trim());
    }

    /**
     * Overrides the ozacc parser, because it forces DTD validation, we don't want that,
     */
    @Override
    protected Mail build(String templateXmlText, VelocityContext context) 
            throws TransformerFactoryConfigurationError, Exception, 
            ParseErrorException, MethodInvocationException, 
            ResourceNotFoundException, IOException {
        if (log.isDebugEnabled()) {
            log.debug("Source XML Mail Data\n" + templateXmlText);
        }
        
        StringWriter w = new StringWriter();
        this.engine.evaluate(context, w, "XML Mail Data", templateXmlText);
        StringReader reader = new StringReader(w.toString());
        
        DocumentBuilder db = createDocumentBuilder();
        InputSource source = new InputSource(reader);
        source.setSystemId(systemId); // this is the only line different to the base class
        Document newDoc = db.parse(source);

// 2015.06.15 ozacc-mail Ver 1.2.X ‘Î‰ž start
    	Element element = newDoc.getDocumentElement();
// 2015.06.15 ozacc-mail Ver 1.2.X ‘Î‰ž end

        if (log.isDebugEnabled()) {
// 2015.06.15 ozacc-mail Ver 1.2.X ‘Î‰ž start
//            String newXmlContent = convertDocumentIntoString(newDoc);
            String newXmlContent = convertDocumentIntoString(element);
// 2015.06.15 ozacc-mail Ver 1.2.X ‘Î‰ž end
            log.debug("VelocityContext-merged XML Mail Data\n" + newXmlContent);
        }
// 2015.06.15 ozacc-mail Ver 1.2.X ‘Î‰ž start
//        return buildMail(newDoc);
        return buildMail(element);
// 2015.06.15 ozacc-mail Ver 1.2.X ‘Î‰ž end
    }

    /**
     * Overrides the ozacc parser, because it forces DTD validation, we don't want that
     */
    protected Properties getOutputProperties() {
        Properties p = new Properties();
        p.put(OutputKeys.ENCODING, charset);
        return p;
    }
    
    /**
     * Overrides the ozacc parser, because it forces DTD validation, we don't want that
     */
    @Override
    @SuppressWarnings("unchecked")
    protected DocumentBuilder createDocumentBuilder(boolean ignoreComment)
            throws FactoryConfigurationError {
        Boolean dbKey = Boolean.valueOf(ignoreComment);
        DocumentBuilder db = (DocumentBuilder)documentBuilderCache.get(dbKey);
        if (db == null) {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setIgnoringComments(ignoreComment);
            dbf.setCoalescing(ignoreComment);
            dbf.setIgnoringElementContentWhitespace(true);
            dbf.setValidating(false); // different from super-class
            try {
                db = dbf.newDocumentBuilder();
                db.setEntityResolver(new DTDEntityResolver());
                documentBuilderCache.put(dbKey, db);
            } catch (ParserConfigurationException e) {
                // never be thrown
                throw new RuntimeException(e);
            }
        }
        return db;
    }
}
