/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.automount;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.ServletContext;

import jp.co.transcosmos.dm3.utils.ReflectionUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.web.context.ServletContextAware;

/**
 * Automount all the classes that 
 * 
 * 1) implement a given interface 
 * 2) are members of a given package, and 
 * 3) have a zero-arg constructor
 * 
 * as Spring managed beans using a naming convention defined in attributes.
 */
public class Automounter implements BeanFactoryPostProcessor, ServletContextAware {
    private static final Log log = LogFactory.getLog(Automounter.class);

    private List<String> scanPackages; 
    private List<String> classPath;
    private ServletContext servletContext;
    private Class<?> assignableClass;
    private String scope;
    private boolean lazyInit;
    
    public void setClassPath(List<String> pClassPath) {
        this.classPath = pClassPath;
    }

    public void setScanPackages(List<String> pScanPackages) {
        this.scanPackages = pScanPackages;
    }

    public void setServletContext(ServletContext pServletContext) {
        this.servletContext = pServletContext;
    }

    public void setLazyInit(boolean pLazyInit) {
        this.lazyInit = pLazyInit;
    }

    public void setScope(String pScope) {
        this.scope = pScope;
    }

    public void setAssignableClassName(String assignableClassName) {
        try {
            this.assignableClass = Class.forName(assignableClassName);
        } catch (ClassNotFoundException err) {
            throw new RuntimeException("Error in Automounter - unknown class: " + 
                    assignableClassName, err);
        }
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) 
            throws BeansException {
        if (this.scanPackages == null) {
            log.info("Automounter skipped - no scanPackages defined");
            return;
        }
        log.info("Automounter executing ... interrogating classpath to find classes to mount");
        
        String allClasspathEntryNames[] = null;
        try {
            allClasspathEntryNames = getAllClasspathEntryNames();
        } catch (IOException err) {
            throw new RuntimeException("Error interrogating from classpath", err);
        }
        Arrays.sort(allClasspathEntryNames);
        
        for (Iterator<String> i = this.scanPackages.iterator(); i.hasNext(); ) {
            String scanPackage = i.next();
            
            // Advance to first field that might match our search path
            int pos = Arrays.binarySearch(allClasspathEntryNames, scanPackage);
            if (pos < 0) {
                pos = (-1 * pos) - 1;
            }
            
            int mounted = 0;
            while (allClasspathEntryNames[pos].startsWith(scanPackage)) {
                // Try to load class - if a valid class, load and mount as a reflectingDAO
                try {
                    Class<?> candidate = Class.forName(allClasspathEntryNames[pos]);
                    int modifiers = candidate.getModifiers();
                    if (Modifier.isAbstract(modifiers) || 
                            !Modifier.isPublic(modifiers) || 
                            Modifier.isInterface(modifiers)) {
                        log.debug("Skipping non-instantiable class " + candidate.getName());
                    } else if ((this.assignableClass != null) && 
                                !this.assignableClass.isAssignableFrom(candidate)) {
                        log.debug("Skipping class " + candidate.getName() + " that doesn't " +
                                "extend/implement " + this.assignableClass.getName());
                    } else {
                        // Check for empty constructor
                        Constructor<?> candidateConstructor = candidate.getConstructor(new Class[0]);
                        if (candidateConstructor == null) {
                            log.debug("Skipping class without zero-arg constructor " + 
                                    candidate.getName());
                        } else {
                            String beanName = getBeanName(beanFactory, candidate);
                            
                            // Check for existing bean with this name
                            if (!beanFactory.containsBean(beanName)) {
                                mountBean(beanFactory, beanName, candidate);                                
                                mounted++;
                            } else {
                                log.info("Skipping auto-mount for dao " + beanName + 
                                        " - existing bean found with that name");
                            }
                            
                        }
                    }
                } catch (Throwable err) {
                    log.debug("Skipping class due to error: " + allClasspathEntryNames[pos], err);
                }
                pos++;
            }
            log.info("Mounted " + mounted + " instances from package: " + scanPackage);
        }
    }
    
    protected String getBeanName(ConfigurableListableBeanFactory beanFactory, Class<?> candidate) {
        return ReflectionUtils.lowerCaseFirstChar(
                ReflectionUtils.getClassNameWithoutPackage(candidate));        
    }
    
    protected void mountBean(ConfigurableListableBeanFactory beanFactory, String beanName,
            Class<?> detectedClass) {
        
        if (beanFactory instanceof BeanDefinitionRegistry) {
            log.info("Auto-mounting bean " + beanName + " for class: " + detectedClass.getName());
            
            MutablePropertyValues props = new MutablePropertyValues();
            assignProperties(beanFactory, props, beanName, detectedClass);
            
            RootBeanDefinition beanDefinition = new RootBeanDefinition();
            beanDefinition.setLazyInit(this.lazyInit);
            if (this.scope != null) {
                beanDefinition.setScope(this.scope);
            }
            beanDefinition.setLazyInit(this.lazyInit);
            beanDefinition.setBeanClassName(getAutomountedClassName(detectedClass, beanName));
            beanDefinition.setPropertyValues(props);
            
            ((BeanDefinitionRegistry) beanFactory).registerBeanDefinition(
                    beanName, beanDefinition);        
        } else if ((this.scope == null) || (this.scope.equals(RootBeanDefinition.SCOPE_SINGLETON))) {
            log.info("Auto-mounting bean " + beanName + " for class: " + detectedClass.getName());
            
            Object instance = null;
            try {
                instance = detectedClass.newInstance();
            } catch (IllegalAccessException err) {
                throw new RuntimeException("Error creating auto-mounted bean instance: " + 
                        beanName, err);
            } catch (InstantiationException err) {
                throw new RuntimeException("Error creating auto-mounted bean instance: " + 
                        beanName, err);
            }

            if (instance != null) {
                // properties
                MutablePropertyValues props = new MutablePropertyValues();
                assignProperties(beanFactory, props, beanName, detectedClass);
                PropertyValue[] vals = props.getPropertyValues();
                for (int n = 0; n < vals.length; n++) {
                    try {
                        ReflectionUtils.setFieldValueBySetter(instance, 
                                vals[n].getName(), vals[n].getValue());
                    } catch (Throwable err) {
                        log.error("Error assigning " + beanName + 
                                " property: " + vals[n].getName() + 
                                " = " + vals[n].getValue(), err);
                    }
                }
                beanFactory.registerSingleton(beanName, instance);
            }
        } else {
            throw new RuntimeException("Can only auto-mount singletons for " +
                    "non-BeanDefinitionRegistry implementation Contexts");
        }
    }

    protected String getAutomountedClassName(Class<?> detectedClass, String beanName) {
        return detectedClass.getName();
    }

    protected void assignProperties(ConfigurableListableBeanFactory beanFactory, 
            MutablePropertyValues props, String beanName, Class<?> detectedClass) {}    
    
    public List<String> getClassPath() {
        List<String> classPath = this.classPath;
        if ((classPath == null) && (this.servletContext != null)) {
            // Try to guess at classpath from webapp locations
            classPath = new ArrayList<String>();
            String realClassFolder = this.servletContext.getRealPath("/WEB-INF/classes");
            String realLibFolder = this.servletContext.getRealPath("/WEB-INF/lib");
            if (realClassFolder != null) {
                classPath.add(realClassFolder);
            }
            if (realLibFolder != null) {
                classPath.add("jardir:" + realLibFolder);
            }
        }
        return classPath;
    }

    /**
     * Returns an array containing all the classnames in the classpath
     */
    public String[] getAllClasspathEntryNames() throws IOException {
        List<String> classPath = getClassPath();
        Set<String> classEntries = new HashSet<String>();
        if (classPath != null) {
            for (Iterator<String> i = classPath.iterator(); i.hasNext(); ) {
                String path = i.next();
                
                // Check the prefix
                if (path.startsWith("jardir:")) {
                    File parent = new File(path.substring(7));
                    File jars[] = parent.listFiles();
                    if (jars != null) {
                        for (int n = 0; n < jars.length; n++) {
                            readJARContents(jars[n], classEntries);
                        }
                    }
                } else if (path.startsWith("jar:")) {
                    readJARContents(new File(path.substring(4)), classEntries);
                } else {
                    readPathElement(new File(path), classEntries, "");
                }
            }
        }
        return classEntries.toArray(new String[classEntries.size()]);
    }
    
    private void readPathElement(File parentDir, Set<String> out, String prefix) {
        File children[] = parentDir.listFiles();
        if (children != null) {
            for (int n = 0; n < children.length; n++) {
                if (children[n].isDirectory()) {
                    readPathElement(children[n], out, prefix + children[n].getName() + "/");
                } else {
                    String name = children[n].getName();
                    if (name.endsWith(".class")) {
                        out.add(prefix.replace('/', '.') + name.substring(0, name.length() - 6));
                    } else {
                        out.add(prefix + name);
                    }
                }
            }
        }
    }
    
    private void readJARContents(File jar, Set<String> out) throws IOException {
        if (jar.getName().endsWith(".jar") || jar.getName().endsWith(".zip")) {
            ZipFile zip = new ZipFile(jar);
            for (Enumeration<?> e = zip.entries(); e.hasMoreElements(); ) {
                ZipEntry candidate = (ZipEntry) e.nextElement();
                if (!candidate.isDirectory()) {
                    String name = candidate.getName();
                    if (name.endsWith(".class")) {
                        out.add(name.substring(0, name.length() - 6).replace('/', '.'));
                    } else {
                        out.add(name);
                    }
                }
            }
            zip.close();
        }
    }
}
