package jp.co.transcosmos.dm3.corePana.model.adminLog.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Factory class of AdminLog for maintenance Form.
 * <p>
 * getInstance() Or that you use to get the instance from Spring.
 * <p>
 * 
 * <pre>
 * �S����        �C����       �C�����e
 * ------------ ----------- -----------------------------------------------------
 * Vinh.Ly      2015.08.24  Create New
 * </pre>
 * <p>
 * Precautions <br/>
 * Do not create an instance of the Factory direct. Be sure to get in the
 * getInstance().
 *
 */
public class AdminLogFormFactory {

    /** Form �𐶐����� Factory �� Bean ID */
    protected static final String FACTORY_BEAN_ID = "adminLogFormFactory";

    /** ���ʃR�[�h�ϊ����� */
    protected CodeLookupManager codeLookupManager;

    /** ���ʃp�����[�^�I�u�W�F�N�g */
    protected CommonParameters commonParameters;

    /** �� �� �O �X �o �� �f �[ �V �� �� �p �� �[ �e �B �� �e �B */
    protected LengthValidationUtils lengthUtils;

    /**
     * ���ʃR�[�h�ϊ�������ݒ肷��B<br/>
     * <br/>
     * 
     * @param codeLookupManager
     *            ���ʃR�[�h�ϊ�����
     */
    public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
        this.codeLookupManager = codeLookupManager;
    }

    /**
     * ���ʃp�����[�^�I�u�W�F�N�g��ݒ肷��B<br/>
     * <br/>
     * 
     * @param commonParameters
     *            ���ʃp�����[�^�I�u�W�F�N�g
     */
    public void setCommonParameters(CommonParameters commonParameters) {
        this.commonParameters = commonParameters;
    }

    /**
     * �����O�X�o���f�[�V�����p���[�e�B���e�B��ݒ肷��B<br/>
     * <br/>
     * 
     * @param lengthUtils
     *            �����O�X�o���f�[�V�����p���[�e�B���e�B
     */
    public void setLengthUtils(LengthValidationUtils lengthUtils) {
        this.lengthUtils = lengthUtils;
    }

    /**
     * AdminLogSearchForm get the instance.<br/>
     * From Spring of context, AdminLogSearchForm In it has been defined
     * AdminLogFormFactory of get an instance.<br/>
     * Instance that is acquired AdminLogSearchForm Sometimes extension class
     * that inherits from is restored <br/>
     * <br/>
     * 
     * @param request
     *            HTTP Request
     * @return AdminLogFormFactory�AOr by inheriting instance of a class that
     *         extends
     */
    public static final AdminLogFormFactory getInstance(HttpServletRequest request) {
        WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request
                .getServletContext());
        return (AdminLogFormFactory) springContext.getBean(AdminLogFormFactory.FACTORY_BEAN_ID);
    }

    /**
     * It generates an empty instance of AdminLogSearchForm to store the input
     * value of the search adminLog .<br/>
     * <br/>
     * 
     * @return AdminLogSearchForm instance of empty
     */
    public AdminLogSearchForm createAdminLogSearchForm() {
        return new AdminLogSearchForm(this.lengthUtils, this.commonParameters, this.codeLookupManager);
    }

    /**
     * It creates an instance of AdminLogSearchForm that stores the search
     * condition of the property list. <br/>
     * The AdminLogSearchForm, set the value corresponding to the request
     * parameters. <br/>
     * <br/>
     * 
     * @param request
     *            HTTP Request
     * @return AdminLogSearchForm instance that set the request parameter
     */
    public AdminLogSearchForm createAdminLogSearchForm(HttpServletRequest request) {
        AdminLogSearchForm form = createAdminLogSearchForm();
        FormPopulator.populateFormBeanFromRequest(request, form);
        return form;
    }

}
