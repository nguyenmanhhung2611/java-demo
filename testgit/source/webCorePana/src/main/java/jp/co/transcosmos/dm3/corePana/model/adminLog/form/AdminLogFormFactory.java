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
 * 担当者        修正日       修正内容
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

    /** Form を生成する Factory の Bean ID */
    protected static final String FACTORY_BEAN_ID = "adminLogFormFactory";

    /** 共通コード変換処理 */
    protected CodeLookupManager codeLookupManager;

    /** 共通パラメータオブジェクト */
    protected CommonParameters commonParameters;

    /** レ ン グ ス バ リ デ ー シ ョ ン 用 ユ ー テ ィ リ テ ィ */
    protected LengthValidationUtils lengthUtils;

    /**
     * 共通コード変換処理を設定する。<br/>
     * <br/>
     * 
     * @param codeLookupManager
     *            共通コード変換処理
     */
    public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
        this.codeLookupManager = codeLookupManager;
    }

    /**
     * 共通パラメータオブジェクトを設定する。<br/>
     * <br/>
     * 
     * @param commonParameters
     *            共通パラメータオブジェクト
     */
    public void setCommonParameters(CommonParameters commonParameters) {
        this.commonParameters = commonParameters;
    }

    /**
     * レングスバリデーション用ユーティリティを設定する。<br/>
     * <br/>
     * 
     * @param lengthUtils
     *            レングスバリデーション用ユーティリティ
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
     * @return AdminLogFormFactory、Or by inheriting instance of a class that
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
