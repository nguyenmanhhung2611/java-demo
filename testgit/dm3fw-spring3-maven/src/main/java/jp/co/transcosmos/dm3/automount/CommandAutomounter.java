/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.automount;

import java.lang.reflect.Method;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.utils.ReflectionUtils;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * Automount command classes detected in the specified package. 
 * This is intended to be used on the Command package.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: CommandAutomounter.java,v 1.4 2007/05/31 10:42:10 rick Exp $
 */
public class CommandAutomounter extends Automounter {
    
    /**
     * By default we want this automounter to search for Command subclasses only, 
     * so presets the assignableClassName to be the Command class.
     */
    public CommandAutomounter() {
        setAssignableClassName(Command.class.getName());
    }
    
    protected void assignProperties(ConfigurableListableBeanFactory beanFactory, 
            MutablePropertyValues props, String beanName, Class<?> detectedClass) {
        // Iterate over the detected command class, and look for any beans in the
        // factory that match the attribute setterName
        Class<?> thisClass = detectedClass;
        while ((thisClass != null) && !thisClass.equals(Object.class)) {
            Method methods[] = thisClass.getDeclaredMethods();
            for (int n = 0; n < methods.length; n++) {
                if (methods[n].getName().startsWith("set") && 
                        (methods[n].getParameterTypes().length == 1)) {
                    // Get the arg name+type, and look for a bean that matches
                    String dependencyName = ReflectionUtils.lowerCaseFirstChar(
                            methods[n].getName().substring(3));
                    if (beanFactory.containsBean(dependencyName)) {                    
                        props.addPropertyValue(dependencyName, 
                                beanFactory.getBean(dependencyName));
                    }
                }
            }
            thisClass = thisClass.getSuperclass();
        }
    }
    
    protected String getBeanName(ConfigurableListableBeanFactory beanFactory, Class<?> candidate) {
        return super.getBeanName(beanFactory, candidate);        
    }
}
