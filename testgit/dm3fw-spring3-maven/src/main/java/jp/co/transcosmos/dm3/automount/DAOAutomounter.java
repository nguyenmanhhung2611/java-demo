/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.automount;

import javax.sql.DataSource;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * Automount DAOs. This is intended to be used on the Valueobject package, and mounts 
 * a ReflectingDAO instance for each valueobject class detected. The name of the DAO
 * is taken from the valueobject class name (without package), and is recorded in the 
 * boot-time log so the bean id can be easily determined.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: DAOAutomounter.java,v 1.3 2007/05/31 10:42:10 rick Exp $
 */
public class DAOAutomounter extends Automounter {

    private DataSource dataSource;
    private String daoClassName = "jp.co.transcosmos.dm3.dao.ReflectingDAO";
    
    public void setDataSource(DataSource pDataSource) {
        this.dataSource = pDataSource;
    }

    public void setDaoClassName(String pDaoClassName) {
        this.daoClassName = pDaoClassName;
    }
    
    protected void assignProperties(ConfigurableListableBeanFactory beanFactory, 
            MutablePropertyValues props, String beanName, Class<?> detectedClass) {
        props.addPropertyValue("valueObjectClassName", detectedClass.getName());
        props.addPropertyValue("dataSource", this.dataSource);        
    }

    protected String getAutomountedClassName(Class<?> detectedClass, String beanName) {
        return this.daoClassName;
    }
    
    protected String getBeanName(ConfigurableListableBeanFactory beanFactory, Class<?> candidate) {
        return super.getBeanName(beanFactory, candidate) + "DAO";        
    }
}
