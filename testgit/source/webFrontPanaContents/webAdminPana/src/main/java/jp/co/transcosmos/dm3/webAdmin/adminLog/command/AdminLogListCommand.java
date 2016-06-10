package jp.co.transcosmos.dm3.webAdmin.adminLog.command; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.corePana.model.AdminLogManage;
import jp.co.transcosmos.dm3.corePana.model.adminLog.form.AdminLogFormFactory;
import jp.co.transcosmos.dm3.corePana.model.adminLog.form.AdminLogSearchForm;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * <Pre>
 * Management tool of admin log and output csv file.
 * Find the admin log base on the input search conditions .
 * If there is a problem with the input of the search condition, the search process is not performed.
 * 
 * The [View name to return]
 *   .Success": Output csv file.
 * 
 * File's modification logs
 * ------------ ----------- -----------------------------------------------------
 * Duong.Nguyen 2015.08.24  Add admin log search function
 * Vinh.Ly      2015.08.24  Add validation of search form.
 * 
 * Notes
 *
 * </ Pre>
 *
 */
public class AdminLogListCommand implements Command {

    private AdminLogManage adminLogManager;
    private String mode;

    /**
     * Renovation information input screen<br>
     * <br>
     *
     * @param request HTTP request
     * @param response HTTP response
     */
    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // read and create a map model that includes parameters from request
        Map<String, Object> model = createModel(request);
        AdminLogSearchForm searchForm = (AdminLogSearchForm) model
                .get("searchForm");

        // create new list of errors that is used during business processing
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        if (!searchForm.validate(errors)) {
            // put errors found to model map
            model.put("errors", errors);
            return new ModelAndView("validFail", model);
        }
        if ("csv".equals(mode)) {
            searchForm.setRowsPerPage(Integer.MAX_VALUE);
            int counter = adminLogManager.searchAdminLog(searchForm);
            if (counter > 0) {
                return new ModelAndView("success", model);
            } else {
                model.put("hitcont", counter);
            }

        }

        return new ModelAndView("default", model);
    }

    /**
     * We want to create a Form object from the request parameters. <br/>
     * The resulting Form object is returned and stored in a Map. <br/>
     * Key = form class name (without package), Value = form object <br/>
     *
     * @param request HTTP request
     * @return Map object parameter storing form object set
     */
    protected Map<String, Object> createModel(HttpServletRequest request) {

        Map<String, Object> model = new HashMap<String, Object>();

        // Create a Form object is to get the request parameters.
        AdminLogFormFactory factory = AdminLogFormFactory.getInstance(request);
        AdminLogSearchForm searchForm = factory
                .createAdminLogSearchForm(request);

        model.put("searchForm", searchForm);

        return model;
    }

    /**
     * @param adminLogManage the adminLogManage to set
     */
    public void setAdminLogManager(AdminLogManage adminLogManage) {
        this.adminLogManager = adminLogManage;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

}
