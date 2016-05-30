/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.servlet;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.csv.CsvConfig;
import jp.co.transcosmos.dm3.csv.SimpleCSVModel;
import jp.co.transcosmos.dm3.report.ReplacingBIRTReport;
import jp.co.transcosmos.dm3.report.ReplacingJasperReport;
import jp.co.transcosmos.dm3.utils.ServletUtils;
import jp.co.transcosmos.dm3.view.CSVView;
import jp.co.transcosmos.dm3.view.ClientSideRedirectView;
import jp.co.transcosmos.dm3.view.ExcelView;
import jp.co.transcosmos.dm3.view.JSONView;
import jp.co.transcosmos.dm3.view.PDFResponseView;
import jp.co.transcosmos.dm3.view.ServerSideRedirectView;
import jp.co.transcosmos.dm3.view.SimpleSpreadsheetModel;
import jp.co.transcosmos.dm3.view.VelocityView;
import jp.co.transcosmos.dm3.view.XMLResponseView;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

/**
 * Internal object used by the framework for rewriting of the view name prefixes (e.g.
 * redirect:, pdf:, etc) into view classes in the jp.co.transcosmos.dm3.view package.
 * You shouldn't need to edit this directly.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: ModelAndViewRewriteSupport.java,v 1.6 2012/08/01 09:28:36 tanaka Exp $
 */
public class ModelAndViewRewriteSupport implements BeanFactoryAware, ServletContextAware {    
    private static final Log log = LogFactory.getLog(ModelAndViewRewriteSupport.class); 
     
    private BeanFactory beanFactory;
    private ServletContext servletContext;
    private String additionalModelEntriesName = "additionalModelEntries";

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public void setServletContext(ServletContext pServletContext) {
        this.servletContext = pServletContext;
    }


    
	/**
     * Rewrites the modelAndView we returned from the command into the wrapping from the 
     * struts-esque url mapping section
     */
    protected ModelAndView resolveView(HttpServletRequest request, HttpServletResponse response, 
            ModelAndView mav, URLCommandViewMapping mapping) {
        
        // Add missing model parameters
        if (mav != null) {
            addMissingParams(mav.getModel(), request, response);
        }
        
        // don't touch anything with a view already set or if we don't have a mapping
        if ((mav != null) && (mav.getView() != null)) {
            log.info("Forwarding to explicit view");
            return mav; 
        } else if (mapping == null) {
            log.info("View unchanged: mapping was null");
            return mav;
        } else if (mav == null) {
            // Assign default view if one exists and we don't have one set
            if (mapping.getDefaultViewName() != null) {
                log.info("Returning default view: " + mapping.getDefaultViewName());
                return new ModelAndView(mapping.getDefaultViewName());
            } else {
                log.info("Null view returned");
                return null;
            }
        } else if (mav.getViewName() == null) {
            // Assign default view if one exists and we don't have one set
            if (mapping.getDefaultViewName() != null) {
                log.info("Returning default view (with model): " + mapping.getDefaultViewName());
                mav.setViewName(mapping.getDefaultViewName());
                return mav;
            } else {
                log.info("Null view returned");
                return null;
            }
        } else {
            // We must have returned a mav with a view name to get here
            String replace = null;
            if (mapping.getNamedViews() != null) {
                synchronized (mapping.getNamedViews()) {
                    replace = mapping.getNamedViews().get(mav.getViewName());
                }
            }
            if (replace == null) {
                replace = mapping.getDefaultViewName();
            }
            log.info("Returning named view " + mav.getViewName() + "=" + replace);
            if (replace == null) {
                mav = null;
            } else {
                mav.setViewName(replace);
            }
            return mav;
        }
    }
    
    /**
     * Does a last minute conversion on the view name, so that if we request a view
     * with a name starting with "redirect:" we use our internal redirector, not Spring's
     */
    protected void replaceViewIfPrefixed(ModelAndView mav) {
        if (mav != null) {
            String viewName = mav.getViewName();
            if (viewName != null) {
                if (viewName.startsWith("redirect:")) {
                    log.info("Replacing view with client redirect to: " + viewName.substring(9));
                    mav.setViewName(null);
                    mav.setView(new ClientSideRedirectView(viewName.substring(9)));
// 2013.11.20 H.Mizuno リダイレクト時のステータスコード設定に対応 start
                } else if (viewName.startsWith("redirect_")) {
                	String[] wkArray1 = viewName.split(":");
                	String[] wkArray2 = wkArray1[0].split("_");
                	log.info("Replacing view with client redirect to: " + viewName.substring(wkArray1[0].length()+1) + " status:" + wkArray2[1]);
                    mav.setViewName(null);
                    mav.setView(new ClientSideRedirectView(viewName.substring(wkArray1[0].length()+1), Integer.valueOf(wkArray2[1])));
// 2013.11.20 H.Mizuno リダイレクト時のステータスコード設定に対応 end
                } else if (viewName.startsWith("forward:")) {
                    log.info("Replacing view with server-side redirect to: " + viewName.substring(8));
                    mav.setViewName(null);
                    mav.setView(new ServerSideRedirectView(viewName.substring(8)));
                } else if (viewName.startsWith("dispatch:")) {
                    log.info("Replacing view with server-side redirect to: " + viewName.substring(9));
                    mav.setViewName(null);
                    mav.setView(new ServerSideRedirectView(viewName.substring(9)));
                } else if (viewName.startsWith("error:")) {
                    log.info("Replacing view with error view: " + viewName.substring(6));
                    mav.setViewName(null);
                    mav.setView(new ServerSideRedirectView(viewName.substring(6)));
                } else if (viewName.startsWith("xml:")) {
                    log.info("Replacing view with XML response: parentElement=" + 
                            viewName.substring(4));
                    mav.setViewName(null);
                    mav.setView(new XMLResponseView(viewName.substring(4)));
                } else if (viewName.startsWith("xsl:")) {
                    log.info("Replacing view with XSL transform: sheet=" + 
                            viewName.substring(4));
                    mav.setViewName(null);
                    mav.setView(new XMLResponseView("modelAndView", null, 
                            viewName.substring(4)));
                } else if (viewName.startsWith("csv:")) {
                    log.info("Replacing view with CSV download: filename=" + 
                            viewName.substring(4) + ".csv");
                    mav.setViewName(null);
                    mav.setView(new CSVView(new SimpleSpreadsheetModel(viewName.substring(4))));
// 2013.04.19 H.Mizuno ヘッダ付き CSV ダウンロード Start
                } else if (viewName.startsWith("csvh:")) {
                    mav.setViewName(null);
                    CsvConfig config = (CsvConfig)this.beanFactory.getBean(viewName.substring(5));
                    if (config.getDelimiter() != null && config.getDelimiter().length() > 0) {
                        mav.setView(new CSVView(new SimpleCSVModel(config), config.getDelimiter(), "\"", ".csv"));
                    } else {
                        mav.setView(new CSVView(new SimpleCSVModel(config)));
                    }
// 2013.04.19 H.Mizuno ヘッダ付き CSV ダウンロード End
                } else if (viewName.startsWith("tsv:")) {
                    log.info("Replacing view with TSV download: filename=" + 
                            viewName.substring(4) + ".tsv");
                    mav.setViewName(null);
                    mav.setView(new CSVView(new SimpleSpreadsheetModel(
                            viewName.substring(4)), "\t", null, ".tsv"));
                } else if (viewName.startsWith("excel:")) {
                    log.info("Replacing view with Excel download: filename=" + 
                            viewName.substring(6) + ".xls");
                    mav.setViewName(null);
                    mav.setView(new ExcelView(new SimpleSpreadsheetModel(viewName.substring(6))));
                } else if (viewName.startsWith("pdf:")) {
                    Object report = null;
                    if (this.beanFactory != null) {
                        report = this.beanFactory.getBean(viewName.substring(4));
                    }
                    if (report != null) {
                        log.info("Replacing view with PDF view: template=" + viewName.substring(4));
                        if (report instanceof ReplacingJasperReport) {
                            mav.setViewName(null);
                            mav.setView(new PDFResponseView((ReplacingJasperReport) report, 
                                    viewName.substring(4) + ".pdf", false));
                        } else if (report instanceof ReplacingBIRTReport) {
                            mav.setViewName(null);
                            mav.setView(new PDFResponseView((ReplacingBIRTReport) report, 
                                    viewName.substring(4) + ".pdf", false));
                        }
                    }
                } else if (viewName.startsWith("pdfinline:")) {
                    Object report = null;
                    if (this.beanFactory != null) {
                        report = this.beanFactory.getBean(viewName.substring(10));
                    }
                    if (report != null) {
                        log.info("Replacing view with PDF view: template=" + viewName.substring(10));
                        if (report instanceof ReplacingJasperReport) {
                            mav.setViewName(null);
                            mav.setView(new PDFResponseView((ReplacingJasperReport) report, 
                                    viewName.substring(10) + ".pdf", true));
                        } else if (report instanceof ReplacingBIRTReport) {
                            mav.setViewName(null);
                            mav.setView(new PDFResponseView((ReplacingBIRTReport) report, 
                                    viewName.substring(10) + ".pdf", true));
                        }
                    }
                } else if (viewName.equals("json")) {
                    log.info("Replacing view with JSON response");
                    mav.setViewName(null);
                    mav.setView(new JSONView());
                } else if (viewName.startsWith("jsonp:")) {
                    String callback = viewName.substring(6);
                    log.info("Replacing view with JSONP response (callbackParameter=" + callback + ")");
                    mav.setViewName(null);
                    mav.setView(new JSONView(callback));
// 2013.04.04 H.Mizuno Velocity View 対応 Start
                } else if (viewName.startsWith("vm:")) {
                    mav.setViewName(null);
                    String[] strArry = viewName.substring(3).split(":");
                    if (strArry.length >= 2){
                        log.info("Replacing view with Velocity response (template=" + strArry[0] + ", property=" + strArry[1] + ")");
                        mav.setView(new VelocityView(strArry[0] + ".vm", strArry[1]));
                    } else {
                        log.info("Replacing view with Velocity response (template=" + strArry[0] + ")");
                        mav.setView(new VelocityView(strArry[0] + ".vm"));
                    }
// 2013.04.04 H.Mizuno Velocity View 対応 End
                }
                
                if ((mav.getView() != null) && 
                        (mav.getView() instanceof ServletContextAware)) {
                    if (this.servletContext != null) {
                        ((ServletContextAware) mav.getView()).setServletContext(this.servletContext);
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    protected void addMissingParams(Map<String,Object> model, HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> additionalModelEntries = null;
        if ((this.beanFactory != null) && (this.additionalModelEntriesName != null)) {
            try {
                additionalModelEntries = (Map<String,Object>) this.beanFactory.getBean(
                        this.additionalModelEntriesName);
            } catch (Throwable err) {
                log.debug("Couldn't find additional model entries bean: " + 
                        this.additionalModelEntriesName, err);
            }
        }
        if (!model.containsKey("httpsUrl")) {
            if ((additionalModelEntries != null) && additionalModelEntries.containsKey("httpsUrl")) {
                model.put("httpsUrl", additionalModelEntries.get("httpsUrl"));
            } else {
                model.put("httpsUrl", ServletUtils.writeBaseURL(request, true));
            }
        }
        if (!model.containsKey("httpUrl")) {
            if ((additionalModelEntries != null) && additionalModelEntries.containsKey("httpUrl")) {
                model.put("httpUrl", additionalModelEntries.get("httpUrl"));
            } else {
                model.put("httpUrl", ServletUtils.writeBaseURL(request, true));
            }
        }
        if (additionalModelEntries != null) {
            for (Iterator<String> i = additionalModelEntries.keySet().iterator(); i.hasNext(); ) {
                String key = i.next();
                Object value = additionalModelEntries.get(key);
                if (!key.equals("httpsUrl") && !key.equals("httpUrl") && (value != null) && 
                        !model.containsKey(key)) {
                    model.put(key, value);
                }
            }
        }
    }
}
