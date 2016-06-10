/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.form;

/**
 * Implement this interface on your Form objects to add support for population 
 * filter chains.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: FormPopulationFilterable.java,v 1.3 2007/05/31 10:18:26 rick Exp $
 */
public interface FormPopulationFilterable {
    /**
     * Implement this method to instantiate the FormPopulationFilters and add them
     * to the chain for application to form fields.
     */
    public void initializeFormPopulationFilters(FormPopulationFilterChain chain);
    
    /**
     * Implement this method to initialize the form with default values that will be
     * overridden by the request parameters if the field names match.
     */
    public void initializeFormWithDefaults();
}
