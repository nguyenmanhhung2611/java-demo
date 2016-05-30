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

// 2013.04.23 H.Mizuno ���� URL �}�b�s���O�Ή� Start
    private String jspRootPath = "/WEB-INF/jsp/";
// 2013.04.23 H.Mizuno ���� URL �}�b�s���O�Ή� End
    
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
    

    
    // 2013.04.23 H.Mizuno ���� URL �}�b�s���O Start

	/**
     * �A�m�e�[�V�����ɂ�� Command �N���X�̎����}�b�s���O����<br/>
     * ���N�G�X�g URL ���� Command �N���X�� Bean ID �𐶐����Ď����I�� URL �}�b�s���O����B<br />
     *    �E/dir1/dir2/cmd.do �̏ꍇ�Adir1.dir2.cmd �� Bean ID �Ƃ���B<br />
     *    �E�t�H���_���Ƀs���I�h���܂܂�Ă��鎖���t���[�����[�N�͑z�肵�Ă��Ȃ��̂Œ��ӂ���B<br />
     *    �E�����}�b�s���O�Ώۂ� Command �N���X�́AController �A�m�e�[�V������ێ�����N���X��ΏۂƂ���B<br />
     * NamedCommands �ɊY������@�\���g�p����ꍇ�́ABean ID �ŁA# + �R�}���h�����g�p����B<br />
     *    �E/dir1/dir2/cmd.do �ŁA���N�G�X�g�p�����[�^ command=exec �̏ꍇ�Adir1.dir2.cmd#exec ��<br />
     *     �}�b�s���O�����B<br />
     *    �E�Y������}�b�s���O�����t����Ȃ��ꍇ�Acommand �p�����[�^�����O���� Command �N���X�Ƀ}�b�v����B<br />
     *     �iDefaultCommand �ւ̃}�b�s���O�j
     * DefaultViewName �́A�A�m�e�[�V�����Ŏw��\�B�@���ݒ�̏ꍇ�AURL ���� JSP �t�@�C������肷��B<br />
     *    �E/dir1/dir2/cmd.do �̏ꍇ�A/WEB-INF/jsp/dir1/dir2/cmd.jsp �������l�ƂȂ�B<br />
     * NamedViews �̐ݒ���s���ꍇ�́ANamedView �A�m�e�[�V�����Őݒ肷��B
     *    �E@NamedViews("key;View��")�̍\���Őݒ肷��B<br />
     *     �����ݒ肷��ꍇ�́A{"", "", ""} �̗l�ɃA�m�e�[�V�����Œ�`����B<br />�@
     * <br/>
     * @param pRequest HTTP ���N�G�X�g
     * @return FilterCommandChain�@�I�u�W�F�N�g�B�@�Y������R�}���h�N���X�������ꍇ�A null
     */
    public FilterCommandChain findCommandFromAnnotation(HttpServletRequest pRequest){

    	// URL ����R�}���h���� BeanID �𐶐�����B
    	String decodeUrl = ServletUtils.getDecodedUrlPath(pRequest);
    	
    	// �g���q��������������B�@�t�H���_���ł̃s���I�h�͍l�����Ȃ��B
    	String url = decodeUrl.split("\\.")[0];

    	// �擪�̃X���b�V�����폜���A�X���b�V�����s���I�h�ɕϊ����ăR�}���h�� Bean ID �Ƃ���B
    	if (url.startsWith("/")) url = url.substring(1);
    	String commandName = url.replaceAll("/", ".");

    	// ����ɁAcommand �p�����[�^������ꍇ�A"#�p�����[�^�l" ��t������B
    	String cmdParam = getCommandParameterFromRequest(pRequest);
    	if (cmdParam != null) commandName = commandName + "#" + cmdParam;
    	log.debug("create bean id : " + commandName);

    	// URL �Ő������ꂽ Bean ID ���� Command �I�u�W�F�N�g���擾����B
		Command main = this.getAutoMappingCommand(commandName);
		if (main == null) {
			// �f�t�H���g�R�}���h���̂����t����Ȃ��ꍇ�Anull �𕜋A
			if (cmdParam == null) return null;
			
			// command �p�����[�^������ꍇ�Acommand �p�����[�^�����Ń}�b�s���O����B
			// �iDefaultCommand �ւ̃}�b�s���O�����j
	    	commandName = commandName.split("#")[0];
	    	log.debug("create bean id (retry): " + commandName);
	    	
	    	main = this.getAutoMappingCommand(commandName);
	    	if (main == null) return null;
		}

    	// URLCommandViewMapping �𐶐�����B
    	URLCommandViewMapping mainMatch = new URLCommandViewMapping();
    	mainMatch.setDefaultCommandName(commandName);

    	// DefaultViewName �́A�A�m�e�[�V��������擾����B
    	// �A�m�e�[�V���������ݒ�̏ꍇ�AURL + .jsp �� DefaultView �Ƃ���B
    	DefaultViewName defaultViewAnno = main.getClass().getAnnotation(DefaultViewName.class);
    	String defaultViewName = null;
    	if (defaultViewAnno != null) defaultViewName = defaultViewAnno.value();

    	if (defaultViewName == null || defaultViewName.length() == 0)
    		defaultViewName = this.jspRootPath + url + ".jsp";

    	log.debug("defautlView is '" + defaultViewName + "'");
    	mainMatch.setDefaultViewName(defaultViewName);

    	// NamedView ���A�A�m�e�[�V��������擾����B
    	NamedView namedViewAnno = main.getClass().getAnnotation(NamedView.class);
    	if (namedViewAnno != null) {
    		Map<String, String> map = new HashMap<String, String>();
    		for (String namedValue : namedViewAnno.value()){
    			String[] str = namedValue.split(";");
    			map.put(str[0], str[1]);
    		}
    		mainMatch.setNamedViews(map);
    	}


        // �Y������t�B���^�[�̎擾
        List<Filter> filters = new ArrayList<Filter>();
        List<URLCommandViewMapping> filterMappings = new ArrayList<URLCommandViewMapping>();
        List<String[]> matchedParameters = new ArrayList<String[]>();
        filterMatch(decodeUrl, filters, filterMappings, pRequest.getMethod(), 
        		cmdParam, matchedParameters, this.modelAndViewAttribute);

    	
    	// filterCommandChain �C���X�^���X�̍쐬
    	// �ŏI�I�ɂ́AURL �}�b�s���O�̃��X�g�ɖ߂��K�v������̂��A�I�[�o�[�w�b�h�𑪒肵�ĕ��j������B
        FilterCommandChain chain = new FilterCommandChain(
        		filters.toArray(new Filter[filters.size()]), 
                filterMappings.toArray(new URLCommandViewMapping[filterMappings.size()]), 
                main, mainMatch, null, 
                this.modelAndViewAttribute, this.beanFactory, this.servletContext);
        return chain;
        
    }


    
    /**
     * ���� URL �}�b�s���O�ΏۂƂȂ�R�}���h�N���X���擾<br/>
     * �w�肳�ꂽ Bean ID ���g�p���ăR�}���h�I�u�W�F�N�g���擾����B<br />
     * �擾�����I�u�W�F�N�g�� Command �C���^�[�t�F�[�X���������Ă��Ȃ��ꍇ�A�Y���Ȃ��Ƃ���B<br />
     * �܂��A�擾�����R�}���h�N���X�� Controller �A�m�e�[�V������ێ����Ă��Ȃ��ꍇ���Y�������Ƃ���B<br />
     * <br/>
     * @param pCommandName �擾����R�}���h�N���X�� Bean ID�B�@�Y�������̏ꍇ�Anull �𕜋A�B
     * @return Command �N���X
     */
    protected Command getAutoMappingCommand(String pCommandName){
		Command main;
    	try {
    		main = (Command)this.beanFactory.getBean(pCommandName);
    		// Filter Command �Ƃ̋����������ׁA�����}�b�s���O�̑ΏۃR�}���h�́A
    		// Controller �A�m�e�[�V�����������Ă��鎖�Ƃ���B
    		if (main.getClass().getAnnotation(Controller.class) == null){
        		log.debug("bean does not have Controller annotation");
            	return null;
    		}
    	} catch (NoSuchBeanDefinitionException e){
        	// �Y������ Bean �����t����Ȃ������ꍇ
    		log.debug("bean id is not found");
        	return null;
    	} catch (ClassCastException e){
        	// Command �ȊO�� Bean ���擾�����ꍇ 
    		log.debug("bean does not have Command Interface");
        	return null;
    	}
    	return main;
    }
    // 2013.04.23 H.Mizuno ���� URL �}�b�s���O End
    
    
    
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

            // �S�Ă� URL �p�^�[���ƃ}�b�`�����A�ŏ�ʂ� URL �p�^�[������ʂƔ��肳�ꂽ�ꍇ�A
            // �z��̃I�[�o�[�t���[���N���������C���B�@2012.04.16 H.Mizuno
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
// 2015.07.07 H.Mizuno FindBugs �x���Ή� start
//                String candidateSegment = (thisCandidateSegmentEnd < 0 ?
//                        candidatePattern.substring(thisCandidateSegmentStart) :
//                            candidatePattern.substring(thisCandidateSegmentStart, thisCandidateSegmentEnd));
                String candidateSegment = candidatePattern.substring(thisCandidateSegmentStart, thisCandidateSegmentEnd);
// 2015.07.07 H.Mizuno FindBugs �x���Ή� end

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
// 2015.07.07 H.Mizuno FindBugs �x���Ή� start
//                if (thisCandidateSegmentEnd >= 0) {
// 2015.07.07 H.Mizuno FindBugs �x���Ή� end
                    thisCandidateSegmentStart = thisCandidateSegmentEnd;
                    thisCandidateSegmentEnd = candidatePattern.indexOf('/', thisCandidateSegmentStart + 1);
// 2015.07.07 H.Mizuno FindBugs �x���Ή� start
//                } else {
//                    thisCandidateSegmentStart = candidatePattern.length();
//                }
// 2015.07.07 H.Mizuno FindBugs �x���Ή� end
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
// 2015.07.07 H.Mizuno FindBugs �x���Ή� start
//            String candidateSegment = (thisCandidateSegmentEnd < 0 ?
//                    candidatePattern.substring(thisCandidateSegmentStart) :
//                        candidatePattern.substring(thisCandidateSegmentStart, thisCandidateSegmentEnd));
            String candidateSegment = candidatePattern.substring(thisCandidateSegmentStart, thisCandidateSegmentEnd);
// 2015.07.07 H.Mizuno FindBugs �x���Ή� end
            
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
// 2015.07.07 H.Mizuno FindBugs �x���Ή� start
//            if (thisCandidateSegmentEnd >= 0) {
// 2015.07.07 H.Mizuno FindBugs �x���Ή� end
                thisCandidateSegmentStart = thisCandidateSegmentEnd;
                thisCandidateSegmentEnd = candidatePattern.indexOf('/', thisCandidateSegmentStart + 1);
// 2015.07.07 H.Mizuno FindBugs �x���Ή� start
//            } else {
//                thisCandidateSegmentStart = candidatePattern.length();
//            }
// 2015.07.07 H.Mizuno FindBugs �x���Ή� end
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
