package jp.co.transcosmos.dm3.corePana.model.adminLog;

import java.util.List;

import jp.co.transcosmos.dm3.corePana.model.AdminLogManage;
import jp.co.transcosmos.dm3.corePana.model.adminLog.form.AdminLogSearchForm;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.dao.NotEnoughRowsException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Model class that manages admin log
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
public class AdminLogManageImpl implements AdminLogManage {

    private static final Log LOG = LogFactory.getLog(AdminLogManageImpl.class);

    /** DAO instance to acquire information of admin log */
    private DAO<JoinResult> adminLogListDAO;

    /*
     * (non-Javadoc)
     * 
     * @see
     * jp.co.transcosmos.dm3.corePana.model.AdminLogManage#searchAdminLog(jp
     * .co.transcosmos.dm3.corePana.model.adminLog.form.PanaAdminLogSearchForm)
     */
    @Override
    public int searchAdminLog(AdminLogSearchForm searchForm) throws Exception {
        DAOCriteria searchCriteria = searchForm.buildCriteria();
        List<JoinResult> adminLogList;
        try {
            adminLogList = adminLogListDAO.selectByFilter(searchCriteria);

        } catch (NotEnoughRowsException err) {
            LOG.warn("rewind to the first page", err);
            int pageNo = (err.getMaxRowCount() - 1) / searchForm.getRowsPerPage() + 1;
            searchForm.setSelectedPage(pageNo);
            searchCriteria = searchForm.buildCriteria();
            adminLogList = adminLogListDAO.selectByFilter(searchCriteria);
        }

        searchForm.setRows(adminLogList);
        return adminLogList.size();
    }

    /**
     * @param adminLogListDAO
     *            the adminLogListDAO to set
     */
    public void setAdminLogListDAO(DAO<JoinResult> adminLogListDAO) {
        this.adminLogListDAO = adminLogListDAO;
    }
}
