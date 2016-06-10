package jp.co.transcosmos.dm3.corePana.util;

import java.net.URI;
import java.net.URLEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.transcosmos.dm3.corePana.model.exception.AffiliateException;

/**
 * <pre>
 * The util helps to generate affiliate url
 * 
 * íSìñé“                          èCê≥ì˙                         èCê≥ì‡óe
 * ----------------------------------------------------------------------------
 * Thi Tran     2015.10.15     êVãKçÏê¨
 * 
 * </pre>
*/
public class AffiliateUtil {
    
    private static final Log log = LogFactory.getLog(AffiliateUtil.class);
     /**
	 * The function returns janet url for affiliation.
	 * It's janet_url/value/sid
	 * @param host
	 * @param value
	 * @param sid
	 * @return
     * @throws AffiliateException 
	 */
    public static String getJanetURL(String host, String value, String sid) throws AffiliateException {
        try {
            String url = host + "/" + URLEncoder.encode(value, "UTF-8") + "/" + URLEncoder.encode(sid, "UTF-8");
            return new URI(url).toASCIIString();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AffiliateException("Error while generating janet url", e);
        }
    }
}
