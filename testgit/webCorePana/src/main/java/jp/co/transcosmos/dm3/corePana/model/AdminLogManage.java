package jp.co.transcosmos.dm3.corePana.model;

import jp.co.transcosmos.dm3.corePana.model.adminLog.form.AdminLogSearchForm;

/**
 * Interface that provides common services of admin log function
 * <p>
 * search corresponding admin log base on search conditions<br/>
 * <p>
 *
 * <pre>
 * íSìñé“        èCê≥ì˙       èCê≥ì‡óe
 * ------------ ----------- -----------------------------------------------------
 * Duong.Nguyen 2015.08.24  Create this file, add searchAdminLog function
 * </pre>
 *
 */
public interface AdminLogManage {

    /**
     * Find record of admin log which are made when users export data from the
     * system <br/>
     * Values of {@link AdminLogSearchForm} parameters, that are passed in
     * arguments, include search criteria<br/>
     * And searching results are also stored in the Form object<br/>
     * 
     * @param searchForm
     *            contain input search criteria and is used to store searching
     *            result
     * @return number of found records
     * @throws Exception
     */
    public int searchAdminLog(AdminLogSearchForm searchForm) throws Exception;

}
