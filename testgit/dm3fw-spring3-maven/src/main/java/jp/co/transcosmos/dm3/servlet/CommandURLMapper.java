/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.servlet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.command.SimpleForwardCommand;
import jp.co.transcosmos.dm3.servlet.annotation.DefaultViewName;
import jp.co.transcosmos.dm3.servlet.annotation.NamedView;
import jp.co.transcosmos.dm3.utils.ServletUtils;

import org.springframework.stereotype.Controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.web.context.ServletContextAware;

/**
 * Maps a request URL to a set of filter Command instances and a main Command. 
 * <p>
 * The details of mapping are reverse-injected, that is the mapping data objects include
 * a reference to this instance in their config, and use that to actively seek this object
 * out and declare their configurations. The reason for this is that supporting multiple 
 * spring config files involves this object not having any knowledge of the existence
 * of the mapping data objects.
 * <p>
 * A single instance of this class should be mounted in the spring-servlet.xml file, and
 * referred to by each of the URLCommandViewMappingList objects and the CommandDelegatingController
 * instance used.
 * 
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: CommandURLMapper.java,v 1.5 2012/08/01 09:28:36 tanaka Exp $
 */
public class CommandURLMapper implements BeanFactoryAware, ServletContextAware {
    private static final Log log = LogFactory.getLog(CommandURLMapper.class);

    private final static String WILDCARD_MARKER = "###";
    
    private String commandParameter = "command";
    private String welcomePage = "index.html";
    private String modelAndViewAttribute = "dm3fw-spring2.modelAndViewAttribute"; // rarely used name is better
    private BeanFactory beanFactory;
    private ServletContext servletContext;
    private Map<String,URLCommandViewMapping> exactMainMappings = new Hashtable<String,URLCommandViewMapping>();
    private List<URLCommandViewMapping> patternMainMappings = new ArrayList<URLCommandViewMapping>();
    private List<URLCommandViewMapping> filterMappings = new ArrayList<URLCommandViewMapping>();
    private boolean rejectUnknownNamedCommand = false;

// 2013.04.23 H.Mizuno 自動 URL マッピング対応 Start
    private String jspRootPath = "/WEB-INF/jsp/";
// 2013.04.23 H.Mizuno 自動 URL マッピング対応 End
    
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
    
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public void setCommandParameter(String commandParameter) {
        this.commandParameter = commandParameter;
    }

    public void setModelAndViewAttribute(String pModelAndViewAttribute) {
        this.modelAndViewAttribute = pModelAndViewAttribute;
    }

    public void setWelcomePage(String pWelcomePage) {
        this.welcomePage = pWelcomePage;
    }
    
    public void setRejectUnknownNamedCommand(boolean rejectUnknownNamedCommand) {
    	this.rejectUnknownNamedCommand = rejectUnknownNamedCommand;
    }    

    public void registerMapping(URLCommandViewMapping mapping) {
        mapping.validate();
        if (mapping.isFilter()) {
            // If it's a filter, add to the filter mappings
            this.filterMappings.add(mapping);
            log.debug("Mapped filter pattern: " + mapping);
        } else {
            String urlPatterns[] = mapping.getUrlPatterns();
            if (urlPatterns == null) {
                log.warn("WARNING: mapping for command " + mapping + 
                        " ignored because it has no url pattern");
            } else {
                boolean allExact = true;
                for (int n = 0; n < urlPatterns.length; n++) {
                    if (urlPatterns[n].indexOf(WILDCARD_MARKER) == -1) {
                        // If it's an exact match, add to the exact main map (one per method)
                        String methods[] = mapping.getSupportedMethods();
                        for (int k = 0; k < methods.length; k++) {
                            this.exactMainMappings.put(getExactMappingKey(
                                    urlPatterns[n], methods[k]), mapping);
                            log.debug("Mapped exact pattern " + methods[k] + ":" + urlPatterns[n]);
                        }
                    } else {
                        allExact = false;
                    }
                }
                
                if (!allExact) {
                    // Otherwise add to pattern main list
                    this.patternMainMappings.add(mapping);
                    log.debug("Mapped command pattern: " + mapping);
                }
            }
        }
    }
    
    private static String getExactMappingKey(String url, String method) {
        return url + "_METHOD:" + method;
    }
    
    public FilterCommandChain findCommandAndFilters(HttpServletRequest request, boolean useCommandProxy) {
        return findCommandAndFilters(ServletUtils.getDecodedUrlPath(request), request.getMethod(), 
                getCommandParameterFromRequest(request), useCommandProxy);
    }
    
    public FilterCommandChain findCommandAndFilters(String decodedUrlPathWithoutQueryString, 
            String method, String commandParameter, boolean useCommandProxy) {
        log.info("Looking up command and filter chain for url: " + decodedUrlPathWithoutQueryString + 
                " (method=" + method + ", suggested command=" + commandParameter + ")");
        
        List<String[]> matchedParameters = new ArrayList<String[]>();
        
        // Look for the longest matching main
        String key = getExactMappingKey(decodedUrlPathWithoutQueryString, method);
        URLCommandViewMapping mainMatch = (URLCommandViewMapping) this.exactMainMappings.get(key);
        if (mainMatch == null) {
            mainMatch = patternMatch(decodedUrlPathWithoutQueryString, method, matchedParameters);
        } else {
            log.debug("Matched exact path command: " + decodedUrlPathWithoutQueryString + " exactly");
        }
        
        if (mainMatch != null) {
            
            String mainParams[][] = null;
            if (!matchedParameters.isEmpty()) {
                mainParams = (String [][]) matchedParameters.toArray(
                        new String[matchedParameters.size()][2]);
            }
            Command main = lookupCommand(mainMatch, commandParameter);
            if (useCommandProxy && (mainParams != null)) {
                log.debug("Wrapping command in proxy to supply extra parameters");
                main = new PatternMatchedCommandProxy(main, mainParams);
            }
            
            // Look for matching filters
            List<Filter> filters = new ArrayList<Filter>();
            List<URLCommandViewMapping> filterMappings = new ArrayList<URLCommandViewMapping>();
            filterMatch(decodedUrlPathWithoutQueryString, filters, filterMappings, method, 
                    commandParameter, matchedParameters, this.modelAndViewAttribute);
            
            // Create a filterCommandChain instance
            FilterCommandChain chain = new FilterCommandChain(
                    filters.toArray(new Filter[filters.size()]), 
                    filterMappings.toArray(new URLCommandViewMapping[filterMappings.size()]), 
                    main, mainMatch, mainParams, 
                    this.modelAndViewAttribute, this.beanFactory, this.servletContext);
            return chain;
        } else {
            return null;
        }
    }
    

    
    // 2013.04.23 H.Mizuno 自動 URL マッピング Start

	/**
     * アノテーションによる Command クラスの自動マッピング処理<br/>
     * リクエスト URL から Command クラスの Bean ID を生成して自動的に URL マッピングする。<br />
     *    ・/dir1/dir2/cmd.do の場合、dir1.dir2.cmd を Bean ID とする。<br />
     *    ・フォルダ名にピリオドが含まれている事をフレームワークは想定していないので注意する。<br />
     *    ・自動マッピング対象の Command クラスは、Controller アノテーションを保持するクラスを対象とする。<br />
     * NamedCommands に該当する機能を使用する場合は、Bean ID で、# + コマンド名を使用する。<br />
     *    ・/dir1/dir2/cmd.do で、リクエストパラメータ command=exec の場合、dir1.dir2.cmd#exec に<br />
     *     マッピングされる。<br />
     *    ・該当するマッピングが見付からない場合、command パラメータを除外した Command クラスにマップする。<br />
     *     （DefaultCommand へのマッピング）
     * DefaultViewName は、アノテーションで指定可能。　未設定の場合、URL から JSP ファイルを特定する。<br />
     *    ・/dir1/dir2/cmd.do の場合、/WEB-INF/jsp/dir1/dir2/cmd.jsp が初期値となる。<br />
     * NamedViews の設定を行う場合は、NamedView アノテーションで設定する。
     *    ・@NamedViews("key;View名")の構造で設定する。<br />
     *     複数設定する場合は、{"", "", ""} の様にアノテーションで定義する。<br />　
     * <br/>
     * @param pRequest HTTP リクエスト
     * @return FilterCommandChain　オブジェクト。　該当するコマンドクラスが無い場合、 null
     */
    public FilterCommandChain findCommandFromAnnotation(HttpServletRequest pRequest){

    	// URL からコマンド名の BeanID を生成する。
    	String decodeUrl = ServletUtils.getDecodedUrlPath(pRequest);
    	
    	// 拡張子部分を除去する。　フォルダ名でのピリオドは考慮しない。
    	String url = decodeUrl.split("\\.")[0];

    	// 先頭のスラッシュを削除し、スラッシュをピリオドに変換してコマンドの Bean ID とする。
    	if (url.startsWith("/")) url = url.substring(1);
    	String commandName = url.replaceAll("/", ".");

    	// さらに、command パラメータがある場合、"#パラメータ値" を付加する。
    	String cmdParam = getCommandParameterFromRequest(pRequest);
    	if (cmdParam != null) commandName = commandName + "#" + cmdParam;
    	log.debug("create bean id : " + commandName);

    	// URL で生成された Bean ID から Command オブジェクトを取得する。
		Command main = this.getAutoMappingCommand(commandName);
		if (main == null) {
			// デフォルトコマンド自体が見付からない場合、null を復帰
			if (cmdParam == null) return null;
			
			// command パラメータがある場合、command パラメータ無しでマッピングする。
			// （DefaultCommand へのマッピング処理）
	    	commandName = commandName.split("#")[0];
	    	log.debug("create bean id (retry): " + commandName);
	    	
	    	main = this.getAutoMappingCommand(commandName);
	    	if (main == null) return null;
		}

    	// URLCommandViewMapping を生成する。
    	URLCommandViewMapping mainMatch = new URLCommandViewMapping();
    	mainMatch.setDefaultCommandName(commandName);

    	// DefaultViewName は、アノテーションから取得する。
    	// アノテーションが未設定の場合、URL + .jsp を DefaultView とする。
    	DefaultViewName defaultViewAnno = main.getClass().getAnnotation(DefaultViewName.class);
    	String defaultViewName = null;
    	if (defaultViewAnno != null) defaultViewName = defaultViewAnno.value();

    	if (defaultViewName == null || defaultViewName.length() == 0)
    		defaultViewName = this.jspRootPath + url + ".jsp";

    	log.debug("defautlView is '" + defaultViewName + "'");
    	mainMatch.setDefaultViewName(defaultViewName);

    	// NamedView も、アノテーションから取得する。
    	NamedView namedViewAnno = main.getClass().getAnnotation(NamedView.class);
    	if (namedViewAnno != null) {
    		Map<String, String> map = new HashMap<String, String>();
    		for (String namedValue : namedViewAnno.value()){
    			String[] str = namedValue.split(";");
    			map.put(str[0], str[1]);
    		}
    		mainMatch.setNamedViews(map);
    	}


        // 該当するフィルターの取得
        List<Filter> filters = new ArrayList<Filter>();
        List<URLCommandViewMapping> filterMappings = new ArrayList<URLCommandViewMapping>();
        List<String[]> matchedParameters = new ArrayList<String[]>();
        filterMatch(decodeUrl, filters, filterMappings, pRequest.getMethod(), 
        		cmdParam, matchedParameters, this.modelAndViewAttribute);

    	
    	// filterCommandChain インスタンスの作成
    	// 最終的には、URL マッピングのリストに戻す必要があるのか、オーバーヘッドを測定して方針を決定。
        FilterCommandChain chain = new FilterCommandChain(
        		filters.toArray(new Filter[filters.size()]), 
                filterMappings.toArray(new URLCommandViewMapping[filterMappings.size()]), 
                main, mainMatch, null, 
                this.modelAndViewAttribute, this.beanFactory, this.servletContext);
        return chain;
        
    }


    
    /**
     * 自動 URL マッピング対象となるコマンドクラスを取得<br/>
     * 指定された Bean ID を使用してコマンドオブジェクトを取得する。<br />
     * 取得したオブジェクトが Command インターフェースを所持していない場合、該当なしとする。<br />
     * また、取得したコマンドクラスが Controller アノテーションを保持していない場合も該当無しとする。<br />
     * <br/>
     * @param pCommandName 取得するコマンドクラスの Bean ID。　該当無しの場合、null を復帰。
     * @return Command クラス
     */
    protected Command getAutoMappingCommand(String pCommandName){
		Command main;
    	try {
    		main = (Command)this.beanFactory.getBean(pCommandName);
    		// Filter Command との競合を避ける為、自動マッピングの対象コマンドは、
    		// Controller アノテーションを持っている事とする。
    		if (main.getClass().getAnnotation(Controller.class) == null){
        		log.debug("bean does not have Controller annotation");
            	return null;
    		}
    	} catch (NoSuchBeanDefinitionException e){
        	// 該当する Bean が見付からなかった場合
    		log.debug("bean id is not found");
        	return null;
    	} catch (ClassCastException e){
        	// Command 以外の Bean を取得した場合 
    		log.debug("bean does not have Command Interface");
        	return null;
    	}
    	return main;
    }
    // 2013.04.23 H.Mizuno 自動 URL マッピング End
    
    
    
    /**
     * Tries to find a welcome page, after confirming that this uri is in fact in the
     * parent of an existing mapping path. 
     */
    public String findWelcomePageURI(HttpServletRequest request) {
        if (this.welcomePage != null) {
            String folderURI = request.getRequestURI();
            if (!folderURI.endsWith("/")) {
                folderURI = folderURI + "/";
            }
            
            // Check exact matches for parentage
            String keys[] = this.exactMainMappings.keySet().toArray(new String[this.exactMainMappings.size()]);
            Arrays.sort(keys);
            int pos = Arrays.binarySearch(keys, folderURI);
            if (pos < 0) {
                pos = (-1 * pos) - 1; // if exact not found, use insertion point
            }

            // 全ての URL パターンとマッチせず、最上位の URL パターンより上位と判定された場合、
            // 配列のオーバーフローを起こす問題を修正。　2012.04.16 H.Mizuno
            if (keys.length < pos){
            	if (keys[pos].startsWith(folderURI)) {
            		log.info("Forwarding unmapped URI " + request.getRequestURI() + 
            				" to welcome page: " + folderURI + this.welcomePage);
            		return folderURI + this.welcomePage;
            	}
            }
            
            // Check pattern matches for parentage
            for (Iterator<URLCommandViewMapping> i = this.patternMainMappings.iterator(); i.hasNext(); ) {
            	URLCommandViewMapping mapping = i.next();
                String patterns[] = mapping.getUrlPatterns();
                for (int n = 0; n < patterns.length; n++) {
                    if (mapping.isRegardOrder() && 
                            treewalkPatternOrdered(patterns[n], folderURI, null, true, false, 
                                    mapping.isRequireAllPatterns())) {
                        log.info("Forwarding unmapped URI " + request.getRequestURI() + 
                                " to welcome page: " + folderURI + this.welcomePage);
                        return folderURI + this.welcomePage;
                    } else if (!mapping.isRegardOrder() && 
                            treewalkPatternUnordered(patterns[n], folderURI, null, false,
                                    mapping.isRequireAllPatterns())) {
                        log.info("Forwarding unmapped URI " + request.getRequestURI() + 
                                " to welcome page: " + folderURI + this.welcomePage);
                        return folderURI + this.welcomePage;
                    }
                }   
            }
        }
        return null;
    }
    
    /**
     * Walks through a list of mappings, and matches the first match it finds, or returns all it 
     * finds that match (based on value of stopAfterOne). 
     */
    protected void filterMatch(String urlPath, List<Filter> outFilters, 
            List<URLCommandViewMapping> outFilterMappings, String method, 
            String commandParameter, List<String[]> matchedParameterHelper,
            String returnResultAttribute) {
        
        // Note iterators are threadsafe, so we can navigate this list without synchronization 
        // as long as we don't update it
        for (Iterator<URLCommandViewMapping> i = this.filterMappings.iterator(); i.hasNext(); ) {
            URLCommandViewMapping mapping = i.next();
            
            if (!mapping.supportsMethod(method)) {
                continue;
            } else {
                String patterns[] = mapping.getUrlPatterns();
                boolean matched = false;
                for (int n = 0; n < patterns.length && !matched; n++) {
                    matchedParameterHelper.clear();
                    
                    if (mapping.isRegardOrder() && 
                            treewalkPatternOrdered(patterns[n], urlPath, matchedParameterHelper, false, false, 
                                    mapping.isRequireAllPatterns()) &&
                            !exceptionPatternsMatch(mapping, urlPath)) {
                        matched = true;
                    } else if (!mapping.isRegardOrder() && 
                            treewalkPatternUnordered(patterns[n], urlPath, matchedParameterHelper, false,
                                    mapping.isRequireAllPatterns()) &&
                            !exceptionPatternsMatch(mapping, urlPath)) {
                        log.debug("Matched main pattern: " + patterns[n] + " to url: " + urlPath);
                        matched = true;
                    }
                    
                    if (matched) {                        
                        // Found a match: build the command proxy with the extra args added
                        Filter matchedFilter = lookupFilter(mapping, commandParameter, 
                                matchedParameterHelper.toArray(new String[matchedParameterHelper.size()][2]),
                                returnResultAttribute);
                        
                        if (matchedFilter != null) {
                            log.debug("Matched filter pattern: " + patterns[n] + " to url: " + urlPath);
                            if (matchedFilter instanceof BeanFactoryAware) {
                                ((BeanFactoryAware) matchedFilter).setBeanFactory(this.beanFactory);
                            }
                            if (outFilters != null) {
                                outFilters.add(matchedFilter);
                            }
                            if (outFilterMappings != null) {
                                outFilterMappings.add(mapping);
                            }
                            matchedParameterHelper.clear();
                        } else {
                            matched = false; // not found because filter was null
                        }                            
                    }
                }
            }
        }
    }
    
    /**
     * Walks through a list of mappings, and matches the first match it finds, or returns all it 
     * finds that match (based on value of stopAfterOne). 
     */
    protected URLCommandViewMapping patternMatch(String urlPath, String method, 
            List<String[]> matchedParameters) {
        // Note iterators are threadsafe, so we can navigate this list without synchronization 
        // as long as we don't update it
        for (Iterator<URLCommandViewMapping> i = this.patternMainMappings.iterator(); i.hasNext(); ) {
            URLCommandViewMapping mapping = (URLCommandViewMapping) i.next();
            
            if (!mapping.supportsMethod(method)) {
                continue;
            } else {
                String patterns[] = mapping.getUrlPatterns();
                for (int n = 0; n < patterns.length; n++) {
                    matchedParameters.clear();
                    if (mapping.isRegardOrder() && 
                            treewalkPatternOrdered(patterns[n], urlPath, matchedParameters, false, false, 
                                    mapping.isRequireAllPatterns()) &&
                            !exceptionPatternsMatch(mapping, urlPath)) {
                        log.debug("Matched main pattern: " + patterns[n] + " to url: " + urlPath);
                        return mapping;
                    } else if (!mapping.isRegardOrder() && 
                            treewalkPatternUnordered(patterns[n], urlPath, matchedParameters, false,
                                    mapping.isRequireAllPatterns()) &&
                            !exceptionPatternsMatch(mapping, urlPath)) {
                        log.debug("Matched main pattern: " + patterns[n] + " to url: " + urlPath);
                        return mapping;
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * Does the pattern match one of the known exceptions ? Return true if any of the exception
     * patterns match
     */
    protected boolean exceptionPatternsMatch(URLCommandViewMapping mapping, String urlPath) {
        if ((mapping != null) && (mapping.getExceptionUrlPatterns() != null)) {
            String patterns[] = mapping.getExceptionUrlPatterns();
            for (int n = 0; n < patterns.length; n++) {
                if (mapping.isRegardOrder() && 
                        treewalkPatternOrdered(patterns[n], urlPath, 
                                null, false, false, mapping.isRequireAllPatterns())) {
                    return true;
                } else if (!mapping.isRegardOrder() && 
                        treewalkPatternUnordered(patterns[n], urlPath, 
                                null, false, mapping.isRequireAllPatterns())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Fast (original) pattern matching technique. This relies on all elements
     * of the pattern being in order - any variations and the match aborts with
     * false as the return. A match must go all the way to the end to succeed,
     * and any non-pattern components must match (always required).
     * 
     * NOTE: requires leading slash and will trim trailing slashes on both paths
     */
    protected boolean treewalkPatternOrdered(String mappingPattern,
            String candidatePattern, List<String[]> matchedParameters,
            boolean parentOfMappingAllowed, boolean childOfMappingAllowed,
            boolean requireAllPatterns) {

        // trim trailing slashes
        if (mappingPattern.endsWith("/")) {
            mappingPattern = mappingPattern.substring(0, mappingPattern.length() - 1);
        }
        if (candidatePattern.endsWith("/")) {
            candidatePattern = candidatePattern.substring(0, candidatePattern.length() - 1);
        }
        
        boolean emptyMapping = mappingPattern.equals("");
        boolean emptyCandidate = candidatePattern.equals("");
        int mappingSlashPos = emptyMapping ? -1 : mappingPattern.indexOf('/', 1);
        int candidateSlashPos = emptyCandidate ? -1 : candidatePattern.indexOf('/', 1);

        // If they are not both negative or both non-negative, no-match
        if ((!emptyMapping && emptyCandidate) || 
                ((mappingSlashPos != -1) && (candidateSlashPos == -1))) {
            if (parentOfMappingAllowed) {
                return true;
            } else if (!requireAllPatterns && 
                    mappingPattern.substring(0, mappingSlashPos).indexOf(WILDCARD_MARKER) != -1) {
                return treewalkPatternOrdered(mappingPattern.substring(mappingSlashPos + 1), candidatePattern,
                        matchedParameters, parentOfMappingAllowed,
                        childOfMappingAllowed, requireAllPatterns);
            } else {
                return false;
            }
        } else if (emptyMapping && !emptyCandidate) {
            return childOfMappingAllowed;
        } else if ((mappingSlashPos == -1) && (candidateSlashPos != -1)) {
            String nameValuePair[] = tokenMatch(mappingPattern, 
                    candidatePattern.substring(0, candidateSlashPos));
            if (nameValuePair == null) {
                // if requireAllPatterns=false and this is a pattern we
                // succeeded, otherwise failed
                return (childOfMappingAllowed && !requireAllPatterns && 
                        mappingPattern.indexOf(WILDCARD_MARKER) != -1);
            }
            if ((nameValuePair.length == 2) && (matchedParameters != null)) {
                matchedParameters.add(nameValuePair);
            }
            return childOfMappingAllowed;
        }

        // if they are both negative, match on remainders
        else if ((mappingSlashPos == -1) && (candidateSlashPos == -1)) {
            String nameValuePair[] = tokenMatch(mappingPattern, candidatePattern);
            if (nameValuePair == null) {
                // if requireAllPatterns=false and this is a pattern we
                // succeeded, otherwise failed
                return (!requireAllPatterns && mappingPattern.indexOf(WILDCARD_MARKER) != -1);
            }
            if ((nameValuePair.length == 2) && (matchedParameters != null)) {
                matchedParameters.add(nameValuePair);
            }
            return true;
        }
        // Match this token and AND with the next match in the path
        else {
            String thisMappingSection = mappingPattern.substring(0,
                    mappingSlashPos);
            String nameValuePair[] = tokenMatch(thisMappingSection,
                    candidatePattern.substring(0, candidateSlashPos));
            if (nameValuePair == null) {
                // if requireAllPatterns=false and this is a pattern try next
                // step down with
                // same candidate, otherwise failed
                if (!requireAllPatterns && thisMappingSection.indexOf(WILDCARD_MARKER) != -1) {
                    return treewalkPatternOrdered(mappingPattern.substring(mappingSlashPos), 
                            candidatePattern, matchedParameters, parentOfMappingAllowed,
                            childOfMappingAllowed, requireAllPatterns);
                } else {
                    return false;
                }
            }
            if ((nameValuePair.length == 2) && (matchedParameters != null)) {
                matchedParameters.add(nameValuePair);
            }
            return treewalkPatternOrdered(mappingPattern.substring(mappingSlashPos + 1), 
                    candidatePattern.substring(candidateSlashPos + 1), matchedParameters,
                    parentOfMappingAllowed, childOfMappingAllowed, requireAllPatterns);
        }
    }

    /**
     * Slower pattern matching technique. This can't rely on the order being fixed - 
     * so all segments much be searched exhaustively. Any non-pattern components must 
     * match (not-optional).
     * 
     * NOTE: requires leading slash and will trim trailing slashes on both paths
     */
    protected boolean treewalkPatternUnordered(String mappingPattern,
            String candidatePattern, List<String[]> matchedParameters,
            boolean childOfMappingAllowed, boolean requireAllPatterns) {
        
        // trim trailing slashes
        if (mappingPattern.endsWith("/")) {
            mappingPattern = mappingPattern.substring(0, mappingPattern.length() - 1);
        }
        if (candidatePattern.endsWith("/")) {
            candidatePattern = candidatePattern.substring(0, candidatePattern.length() - 1);
        }

        // Get the first mapping token
        boolean emptyMapping = mappingPattern.equals("");
        int mappingSlashPos = emptyMapping ? -1 : mappingPattern.indexOf('/', 1);

//        if (mappingSlashPos == 0) {
//            if (candidatePattern.indexOf('/') == 0) {
//                return treewalkPatternUnordered(mappingPattern.substring(1),
//                        candidatePattern.substring(1), matchedParameters,
//                        childOfMappingAllowed, requireAllPatterns);
//            } else if (childOfMappingAllowed && candidatePattern.equals("")){
//                return true;
//            }
//        }
//        else 
        if (mappingSlashPos == -1) {
            int thisCandidateSegmentStart = 0;
            int thisCandidateSegmentEnd = candidatePattern.indexOf('/', thisCandidateSegmentStart + 1);
            while ((thisCandidateSegmentStart < candidatePattern.length()) && 
                    (thisCandidateSegmentEnd >= 0)) {
// 2015.07.07 H.Mizuno FindBugs 警告対応 start
//                String candidateSegment = (thisCandidateSegmentEnd < 0 ?
//                        candidatePattern.substring(thisCandidateSegmentStart) :
//                            candidatePattern.substring(thisCandidateSegmentStart, thisCandidateSegmentEnd));
                String candidateSegment = candidatePattern.substring(thisCandidateSegmentStart, thisCandidateSegmentEnd);
// 2015.07.07 H.Mizuno FindBugs 警告対応 end

                // Try a token match on the segment
                String nameValuePair[] = tokenMatch(mappingPattern, candidateSegment);
                
                // If found, subtract that segment from the candidate and the
                // mappingPattern, then recurse
                if (nameValuePair != null) {
                    if ((nameValuePair.length == 2) && (matchedParameters != null)) {
                        matchedParameters.add(nameValuePair);
                    }
                    return true;
                }
                
                // next segment
// 2015.07.07 H.Mizuno FindBugs 警告対応 start
//                if (thisCandidateSegmentEnd >= 0) {
// 2015.07.07 H.Mizuno FindBugs 警告対応 end
                    thisCandidateSegmentStart = thisCandidateSegmentEnd;
                    thisCandidateSegmentEnd = candidatePattern.indexOf('/', thisCandidateSegmentStart + 1);
// 2015.07.07 H.Mizuno FindBugs 警告対応 start
//                } else {
//                    thisCandidateSegmentStart = candidatePattern.length();
//                }
// 2015.07.07 H.Mizuno FindBugs 警告対応 end
            }
            // Last segment of the candidate
            if (thisCandidateSegmentStart < candidatePattern.length()) {
                String nameValuePair[] = tokenMatch(mappingPattern, candidatePattern.substring(thisCandidateSegmentStart));
                
                // If found, subtract that segment from the candidate and the
                // mappingPattern, then recurse
                if (nameValuePair != null) {
                    if ((nameValuePair.length == 2) && (matchedParameters != null)) {
                        matchedParameters.add(nameValuePair);
                    }
                    return true;
                } else if (!childOfMappingAllowed) {
                    return false;
                }
            }
            // Return true if this is an optional token
            return (!requireAllPatterns && mappingPattern.indexOf(WILDCARD_MARKER) != -1);
        }
        
        String thisMappingSection = mappingPattern.substring(0, mappingSlashPos);

        // Find in the candidate
        int thisCandidateSegmentStart = 0;
        int thisCandidateSegmentEnd = candidatePattern.indexOf('/', thisCandidateSegmentStart + 1);
        while ((thisCandidateSegmentStart < candidatePattern.length()) && 
                (thisCandidateSegmentEnd >= 0)) {
// 2015.07.07 H.Mizuno FindBugs 警告対応 start
//            String candidateSegment = (thisCandidateSegmentEnd < 0 ?
//                    candidatePattern.substring(thisCandidateSegmentStart) :
//                        candidatePattern.substring(thisCandidateSegmentStart, thisCandidateSegmentEnd));
            String candidateSegment = candidatePattern.substring(thisCandidateSegmentStart, thisCandidateSegmentEnd);
// 2015.07.07 H.Mizuno FindBugs 警告対応 end
            
            // Try a token match on the segment
            String nameValuePair[] = tokenMatch(thisMappingSection, candidateSegment);
            
            // If found, subtract that segment from the candidate and the
            // mappingPattern, then recurse
            if (nameValuePair != null) {
                if ((nameValuePair.length == 2) && (matchedParameters != null)) {
                    matchedParameters.add(nameValuePair);
                }
                return treewalkPatternUnordered(mappingPattern.substring(mappingSlashPos),
                        candidatePattern.substring(0, thisCandidateSegmentStart)
                                + candidatePattern.substring(thisCandidateSegmentEnd),
                        matchedParameters, childOfMappingAllowed, requireAllPatterns);
            }
            
            // next segment
// 2015.07.07 H.Mizuno FindBugs 警告対応 start
//            if (thisCandidateSegmentEnd >= 0) {
// 2015.07.07 H.Mizuno FindBugs 警告対応 end
                thisCandidateSegmentStart = thisCandidateSegmentEnd;
                thisCandidateSegmentEnd = candidatePattern.indexOf('/', thisCandidateSegmentStart + 1);
// 2015.07.07 H.Mizuno FindBugs 警告対応 start
//            } else {
//                thisCandidateSegmentStart = candidatePattern.length();
//            }
// 2015.07.07 H.Mizuno FindBugs 警告対応 end
        }
        // Last segment of the candidate
        if (thisCandidateSegmentStart < candidatePattern.length()) {
            String nameValuePair[] = tokenMatch(thisMappingSection, candidatePattern.substring(thisCandidateSegmentStart));
            
            // If found, subtract that segment from the candidate and the
            // mappingPattern, then recurse
            if (nameValuePair != null) {
                if ((nameValuePair.length == 2) && (matchedParameters != null)) {
                    matchedParameters.add(nameValuePair);
                }
                return treewalkPatternUnordered(mappingPattern.substring(mappingSlashPos),
                        candidatePattern.substring(0, thisCandidateSegmentStart),
                        matchedParameters, childOfMappingAllowed, requireAllPatterns);
            } else if (!childOfMappingAllowed) {
                return false;
            }
        }

        // If requireAllPatterns=false and mapping segment has pattern, subtract
        // that segment from the mappingPattern and recurse
        if (!requireAllPatterns && thisMappingSection.indexOf(WILDCARD_MARKER) != -1) {
            return treewalkPatternUnordered(mappingPattern.substring(mappingSlashPos + 1), 
                    candidatePattern, matchedParameters, childOfMappingAllowed, requireAllPatterns);
        } else {
            // else exit false
            return false;
        }
    }
    
    /**
     * Match a single directory/token in the path. If patternsRequired = true, set 
     */
    protected String[] tokenMatch(String mappingToken, String candidateToken) {
        if (mappingToken.startsWith("/") && candidateToken.startsWith("/")) {
            mappingToken = mappingToken.substring(1);
            candidateToken = candidateToken.substring(1);
        }
        
        int startWildcardPos = mappingToken.indexOf(WILDCARD_MARKER);
        if (startWildcardPos == -1) {
            if (mappingToken.equals(candidateToken)) {
                return new String[0]; // empty array denotes successful no-patterns-necessary match
            } else {
                return null; // null means non-match
            }
        } else {            
        	// pattern detected in the mapping: use startsWith or endsWith
            int endWildcardPos = mappingToken.indexOf(WILDCARD_MARKER, 
                    startWildcardPos + WILDCARD_MARKER.length());
            if (endWildcardPos == -1) {
                throw new RuntimeException("Bad pattern mapping: " + mappingToken);
            }
            String wildcardName = mappingToken.substring(
                    startWildcardPos + WILDCARD_MARKER.length(), endWildcardPos); 
            
            if (startWildcardPos == 0) {
                // all wildcard (no-static) case
                if (endWildcardPos == mappingToken.length() - WILDCARD_MARKER.length()) {
                    return new String[] {wildcardName, candidateToken};
                }
                // "*static" case
                String endStatic = mappingToken.substring(endWildcardPos + WILDCARD_MARKER.length());
                if (candidateToken.endsWith(endStatic)) {
                    return new String[] {wildcardName, 
                            candidateToken.substring(0, candidateToken.length() - endStatic.length())};
                } else {
                    return null;
                }
            } else {
                // confirm the "static*" beginning matches
                if (!candidateToken.startsWith(mappingToken.substring(0, startWildcardPos))) {
                    return null;
                }
                
                // "static*static" case
                String endStatic = mappingToken.substring(endWildcardPos + WILDCARD_MARKER.length());
                if (candidateToken.endsWith(endStatic)) {
                    return new String[] {wildcardName, 
                            candidateToken.substring(startWildcardPos, 
                                    candidateToken.length() - endStatic.length())};
                } else {
                    return null;
                }
            }
        }
    }
    
    public String getSelectedCommandName(URLCommandViewMapping mapping, String commandParameter) {
        String selectedCommand = null;
        if ((mapping.getNamedCommands() != null) && (commandParameter != null)) {
            synchronized (mapping.getNamedCommands()) {
                selectedCommand = (String) mapping.getNamedCommands().get(commandParameter);
            }
            if (this.rejectUnknownNamedCommand && (selectedCommand == null)) {
            	throw new RuntimeException("Unknown command parameter: " + commandParameter);
            }
        }
        if (selectedCommand == null) {
            selectedCommand = mapping.getDefaultCommandName();
        }
        return selectedCommand;
    }
    
    /**
     * Looks up a command object from the Spring container
     */
    protected Command lookupCommand(URLCommandViewMapping mapping, String commandParameter) {
        String commandName = getSelectedCommandName(mapping, commandParameter);
            
        // Try to handle the special "forward:" prefix for commands
        if (commandName != null) {
            if (commandName.startsWith("forward:")) {
                SimpleForwardCommand command = new SimpleForwardCommand();
                command.setToPage(commandName.substring("forward:".length()));
                return command;
            } else {
                return (Command) this.beanFactory.getBean(commandName);
            }
        }
        return null;
    }
    
    /**
     * Looks up a command object from the Spring container
     */
    protected Filter lookupFilter(URLCommandViewMapping mapping, String commandParameter,
            String wrapperParams[][], String returnValueParameter) {
        String filterCommandName = getSelectedCommandName(mapping, commandParameter);
            
        // Try to handle the special "forward:" prefix for commands
        if (filterCommandName != null) {
            Object bean = this.beanFactory.getBean(filterCommandName);
            if (bean instanceof Command) {
                Command command = (Command) bean;
                if ((wrapperParams != null) && (wrapperParams.length > 0)) {
                    command = new PatternMatchedCommandProxy(command, wrapperParams);
                }
                return new CommandAdapterFilter(command, mapping, returnValueParameter);
            } else if (bean instanceof Filter) {
                Filter filter = (Filter) bean;
                if ((wrapperParams != null) && (wrapperParams.length > 0)) {
                    filter = new PatternMatchedFilterProxy(filter, wrapperParams);
                }
                return filter;
            } else {
                throw new RuntimeException();
            }
        }
        return null;
    }
    
    protected String getCommandParameterFromRequest(HttpServletRequest request) {
        String method = request.getMethod();
        String contentType = request.getContentType();

        // If method is GET, HEAD or url-encoded-body-POST, call getParameter()
        if (method.equals("GET") || method.equals("HEAD") || 
                (method.equals("POST") && (contentType != null) && 
                        contentType.equals("application/x-www-form-urlencoded"))) {
            return request.getParameter(this.commandParameter);
        } else {
            // Else scan the query string
            String queryString = request.getQueryString();
            if (queryString != null) {
                int pos = queryString.indexOf(this.commandParameter + "=");
                if (pos != -1) {
                    int endPos = queryString.indexOf('&', 
                            pos + this.commandParameter.length() + 1);
                    String encoded = queryString.substring(
                            pos + this.commandParameter.length() + 1, 
                            endPos == -1 ? queryString.length() : endPos);
                    return ServletUtils.decodeURLToken(encoded, request.getCharacterEncoding());
                }
            }
        }
        return null;
    }
    
    public static boolean hasWildcardOnlyToken(String pattern) {
        int slashPos = pattern.indexOf('/');
        if (slashPos == -1) {
            return pattern.startsWith(WILDCARD_MARKER) && pattern.endsWith(WILDCARD_MARKER);
        }
        
        String token = pattern.substring(0, slashPos);
        if (token.startsWith(WILDCARD_MARKER) && token.endsWith(WILDCARD_MARKER)) {
            return true;
        } else {
            return hasWildcardOnlyToken(pattern.substring(slashPos + 1));
        }
    }
}
