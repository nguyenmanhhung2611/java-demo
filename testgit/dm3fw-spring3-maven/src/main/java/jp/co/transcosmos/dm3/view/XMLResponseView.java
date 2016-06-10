/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.view;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import jp.co.transcosmos.dm3.utils.ReflectionUtils;

import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.View;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Provides an XML serialization of the contents of the model, using java reflection
 * to represent the valueobject structure.
 * <p>
 * This view is used when the url-mapping view definition is prefixed with "xml:" or 
 * "xsl:". The "xml:" prefix implies that we want the XML rendered as is, and the 
 * "xsl:" prefix allows transformation with an XSL stylesheet, so arbitrary XML output
 * is possible. 
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: XMLResponseView.java,v 1.3 2012/08/01 09:28:36 tanaka Exp $
 */
public class XMLResponseView implements View, ServletContextAware {
    
    private String encoding;
    private String parentTagName;
    private String xslSheetId;
    private ServletContext context;

    public XMLResponseView(String parentTagName) {
        this.parentTagName = parentTagName;
    }
    public XMLResponseView(String parentTagName, String encoding) {
        this(parentTagName);
        this.encoding = encoding;
    }
    public XMLResponseView(String parentTagName, String encoding, String xslSheetId) {
        this(parentTagName);
        this.encoding = encoding;
        this.xslSheetId = xslSheetId;
    }
    
    public String getContentType() {
        return "text/xml";
    }
    
    public ServletContext getServletContext() {
        return this.context;
    }
    public void setServletContext(ServletContext pContext) {
        this.context = pContext;
    }
    
    public void render(Map model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
        if (this.encoding != null) {
            response.setCharacterEncoding(this.encoding);
        } else if (response.getCharacterEncoding() == null) {
            response.setCharacterEncoding(request.getCharacterEncoding());
        }
        response.setContentType(getContentType());
        
        Transformer tf = null;
        if (this.xslSheetId != null) {
            String xslPath = getServletContext().getRealPath(this.xslSheetId);
            tf = TransformerFactory.newInstance().newTransformer(new StreamSource(xslPath));
        } else {
            tf = TransformerFactory.newInstance().newTransformer();
        }
        tf.setOutputProperty(OutputKeys.ENCODING, response.getCharacterEncoding());
        StringWriter out = new StringWriter();
        tf.transform(buildDOM(model), new StreamResult(out));
        
        byte xmlResponse[] = out.toString().getBytes(response.getCharacterEncoding());
        response.setContentLength(xmlResponse.length);
        response.getOutputStream().write(xmlResponse);
        response.getOutputStream().flush();
    }
    
    protected Source buildDOM(Map<?,?> model) throws ParserConfigurationException {
        // Build DOM
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        Document doc = builderFactory.newDocumentBuilder().newDocument();
        Element rootElm = doc.createElement(xmlSafeTagName(this.parentTagName));
        doc.appendChild(rootElm);

        Object keys[] = model.keySet().toArray();
        Arrays.sort(keys);
        for (int n = 0; n < keys.length; n++) {
            Object value = model.get(keys[n]);
            if (value != null) {
                Element elm = doc.createElement(xmlSafeTagName(keys[n].toString()));
                writeNode(doc, elm, value);
                rootElm.appendChild(elm);
            }
        }
        return new DOMSource(doc);
    }
    
    protected void writeNode(Document doc, Element parent, Object input) {
        if (input == null) {
            return;
        } else if (input instanceof Map) {
            Map<?,?> map = (Map<?,?>) input;
            Object keys[] = map.keySet().toArray();
            Arrays.sort(keys);
            for (int n = 0; n < keys.length; n++) {
                Object value = map.get(keys[n]);
                if (value != null) {
                    Element elm = doc.createElement(xmlSafeTagName(
                            lowerFirstChar(ReflectionUtils.getClassNameWithoutPackage(
                                    value.getClass()))));
                    Attr attKey = doc.createAttribute("key");
                    attKey.setNodeValue(keys[n].toString());
                    writeNode(doc, elm, value);
                    parent.appendChild(elm);
                }
            }
        } else if (input instanceof Collection) {
            Collection<?> collection = (Collection<?>) input;
            int index = 0;
            for (Iterator<?> i = collection.iterator(); i.hasNext(); index++) {
                Object value = i.next();
                if (value != null) {
                    Element elm = doc.createElement(xmlSafeTagName(
                            lowerFirstChar(ReflectionUtils.getClassNameWithoutPackage(
                                    value.getClass()))));
                    Attr attKey = doc.createAttribute("index");
                    attKey.setNodeValue("" + index);
                    writeNode(doc, elm, value);
                    parent.appendChild(elm);
                }
            }
        } else if (input.getClass().getName().startsWith("java.")) {
            parent.appendChild(doc.createTextNode(xmlSafeTagContent(input + "")));
        } else {
            String attNames[] = ReflectionUtils.getAllFieldNamesByGetters(input.getClass());
            if ((attNames != null) && (attNames.length > 0)) {
                for (int n = 0; n < attNames.length; n++) {
                    try {
                        Object value = ReflectionUtils.getFieldValueByGetter(input, attNames[n]);
                        if (value != null) {
                            Element elm = doc.createElement(attNames[n]);
                            writeNode(doc, elm, value);
                            parent.appendChild(elm);
                        }
                    } catch (Throwable err) {
                        throw new RuntimeException("Error reflecting to get names for object: " +
                                attNames[n] + " = " + input, err);
                    }
                }
            } else {
                parent.appendChild(doc.createTextNode(xmlSafeTagContent(input + "")));
            }
        }
    }
    
    protected static String lowerFirstChar(String input) {
        return input.substring(0, 1).toLowerCase() + input.substring(1);
    }
    
    protected String xmlSafeTagName(String name) {
        return name.replaceAll(XML_ILLEGAL_TAG_NAME, "");
    }
    private static final String XML_ILLEGAL_TAG_NAME = 
        "[:@/$()\\x00-\\x1F\\x26\\x27\\x3C\\x3E\\x22]";
    
    protected String xmlSafeTagContent(String name) {
        return name.replaceAll(XML_ILLEGAL_TAG_CONTENT, "");
    }
    private static final String XML_ILLEGAL_TAG_CONTENT = "[\\x00-\\x1F&&[^\\t\\n\\r]]";
}
