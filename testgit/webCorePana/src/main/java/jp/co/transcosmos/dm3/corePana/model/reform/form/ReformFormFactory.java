package jp.co.transcosmos.dm3.corePana.model.reform.form;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 管理ユーザーメンテナンス用 Form の Factory クラス.
 * <p>
 * getInstance() か、Spring からインスタンスを取得して使用する事。
 * <p>
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.28	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * Factory のインスタンスを直接生成しない事。　必ずgetInstance() で取得する事。
 *
 */
public class ReformFormFactory {

    /** Form を生成する Factory の Bean ID */
    protected static String FACTORY_BEAN_ID = "reformFormFactory";

    /** 共通コード変換処理 */
    protected CodeLookupManager codeLookupManager;

    /**
     * 共通コード変換オブジェクトを設定する。<br/>
     * <br/>
     * @param codeLookupManager
     */
    public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
        this.codeLookupManager = codeLookupManager;
    }

	/** 共通パラメータオブジェクト */
	private PanaCommonParameters commonParameters;

	/**
	 * 共通パラメータオブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param commonParameters
	 *            共通パラメータオブジェクト
	 */
	public void setCommonParameters(PanaCommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}
    /**
     * AdminUserFormFactory のインスタンスを取得する。<br/>
     * Spring のコンテキストから、 adminUserFormFactory で定義された AdminUserFormFactory の
     * インスタンスを取得する。<br/>
     * 取得されるインスタンスは、adminUserFormFactory を継承した拡張クラスが復帰される場合もある。<br/>
     * <br/>
     * @param request HTTP リクエスト
     * @return AdminUserFormFactory、または継承して拡張したクラスのインスタンス
     */
    public static ReformFormFactory getInstance(HttpServletRequest request) {
        WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request
                .getServletContext());
        return (ReformFormFactory) springContext.getBean(ReformFormFactory.FACTORY_BEAN_ID);
    }

    /**
     * 管理ユーザーの検索結果、および検索条件を格納する空の AdminUserSearchForm のインスタンスを生成する。<br/>
     * <br/>
     * @return 空の AdminUserSearchForm インスタンス
     */
    public ReformImgForm createRefromImgForm() {
        return new ReformImgForm(this.codeLookupManager);
    }

    public ReformImgForm createRefromImgForm(HttpServletRequest request) {
        ReformImgForm form = createRefromImgForm();
        FormPopulator.populateFormBeanFromRequest(request, form);
        return form;
    }

	/**
	 * 画像情報の検索結果、および検索条件を格納するフォームのインスタンス配列を生成する。<br/>
	 * forms には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定したフォームインスタンス配列
	 */
	public Object[] createRefromImgForms(HttpServletRequest request) {
		Object[] forms = new Object[2];
		forms[0] = createRefromImgForm();
		PanaHousingFormFactory housingFactory = PanaHousingFormFactory.getInstance(request);
		forms[1] = housingFactory.createPanaHousingSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, forms, PanaCommonConstant.maxFileSize, new File(commonParameters.getUploadWorkPath()));
		return forms;
	}

    /**
     * 管理ユーザーの検索結果、および検索条件を格納する空の AdminUserSearchForm のインスタンスを生成する。<br/>
     * <br/>
     * @return 空の AdminUserSearchForm インスタンス
     */
    public ReformDtlForm createReformDtlForm() {
        return new ReformDtlForm(this.codeLookupManager);
    }

    public ReformDtlForm createReformDtlForm(HttpServletRequest request) {
        ReformDtlForm form = createReformDtlForm();
        FormPopulator.populateFormBeanFromRequest(request, form);
        return form;
    }

	/**
	 * 画像情報の検索結果、および検索条件を格納するフォームのインスタンス配列を生成する。<br/>
	 * forms には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定したフォームインスタンス配列
	 */
	public Object[] createReformDtlForms(HttpServletRequest request) {
		Object[] forms = new Object[2];
		forms[0] = createReformDtlForm();
		PanaHousingFormFactory housingFactory = PanaHousingFormFactory.getInstance(request);
		forms[1] = housingFactory.createPanaHousingSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, forms, PanaCommonConstant.maxFileSize, new File(commonParameters.getUploadWorkPath()));
		return forms;
	}

    /**
     * 管理ユーザーの検索結果、および検索条件を格納する空の ReformInfoForm のインスタンスを生成する。<br/>
     * <br/>
     * @return 空の ReformInfoForm インスタンス
     */
    public ReformInfoForm createReformInfoForm() {
        return new ReformInfoForm(this.codeLookupManager);
    }

    public ReformInfoForm createRefromInfoForm(HttpServletRequest request) {
    	ReformInfoForm form = createReformInfoForm();
        FormPopulator.populateFormBeanFromRequest(request, form);
        return form;
    }


	/**
	 * 画像情報の検索結果、および検索条件を格納するフォームのインスタンス配列を生成する。<br/>
	 * forms には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定したフォームインスタンス配列
	 */
	public Object[] createRefromInfoFormAndSearchForm(HttpServletRequest request) {
		Object[] forms = new Object[2];
		forms[0] = createReformInfoForm();
		PanaHousingFormFactory housingFactory = PanaHousingFormFactory.getInstance(request);
		forms[1] = housingFactory.createPanaHousingSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, forms, PanaCommonConstant.maxFileSize, new File(commonParameters.getUploadWorkPath()));
		return forms;
	}
}
