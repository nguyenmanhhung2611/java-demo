package jp.co.transcosmos.dm3.corePana.model.passwordRemind.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class PasswordFormFactory {

	/** Form を生成する Factory の Bean ID */
	protected static String FACTORY_BEAN_ID = "passwordFormFactory";

	/** レングスバリデーション用ユーティリティ */
    protected LengthValidationUtils lengthUtils;

	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;

	/** 共通パラメータオブジェクト */
    protected CommonParameters commonParameters;

	/**
	 * レングスバリデーション用ユーティリティを設定する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーション用ユーティリティ
	 */
	public void setLengthUtils(LengthValidationUtils lengthUtils) {
		this.lengthUtils = lengthUtils;
	}

	/**
	 * 共通コード変換オブジェクトを設定する。<br/>
	 * <br/>
	 * @param codeLookupManager
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	public void setCommonParameters(CommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
     * PanaMemberFormFactory のインスタンスを取得する。<br/>
     * Spring のコンテキストから、 PanaMemberFormFactory で定義された PanaMemberFormFactory の
     * インスタンスを取得する。<br/>
     * 取得されるインスタンスは、PanaMemberFormFactory を継承した拡張クラスが復帰される場合もある。<br/>
     * <br/>
     * @param request HTTP リクエスト
     * @return PanaMemberFormFactory、または継承して拡張したクラスのインスタンス
     */
    public static PasswordFormFactory getInstance(HttpServletRequest request) {
    	WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (PasswordFormFactory)springContext.getBean(PasswordFormFactory.FACTORY_BEAN_ID);
    }

    /**
     * 会員情報の MemberInfoForm のインスタンスを生成する。<br/>
     * <br/>
     * @return 空の MemberInfoForm インスタンス
     */
    public PasswordRemindForm createPasswordRemindForm() {
        return new PasswordRemindForm(this.lengthUtils);
    }

    /**
     * 会員情報の検索結果、および検索条件を格納する MemberSearchForm のインスタンスを生成する。<br/>
     * MemberSearchForm には、リクエストパラメータに該当する値を設定する。<br/>
     * <br/>
     * @param request HTTP リクエスト
     * @return リクエストパラメータを設定した MemberSearchForm インスタンス
     */
    public PasswordRemindForm createPasswordRemindForm(HttpServletRequest request) {
    	PasswordRemindForm form = createPasswordRemindForm();
        FormPopulator.populateFormBeanFromRequest(request, form);
        return form;
    }

    /**
     * 会員情報の MemberInfoForm のインスタンスを生成する。<br/>
     * <br/>
     * @return 空の MemberInfoForm インスタンス
     */
    public PasswordChangeForm createPasswordChangeForm() {
        return new PasswordChangeForm(lengthUtils, commonParameters, codeLookupManager);
    }

    /**
     * 会員情報の検索結果、および検索条件を格納する MemberSearchForm のインスタンスを生成する。<br/>
     * MemberSearchForm には、リクエストパラメータに該当する値を設定する。<br/>
     * <br/>
     * @param request HTTP リクエスト
     * @return リクエストパラメータを設定した MemberSearchForm インスタンス
     */
    public PasswordChangeForm createPasswordChangeForm(HttpServletRequest request) {
    	PasswordChangeForm form = createPasswordChangeForm();
        FormPopulator.populateFormBeanFromRequest(request, form);
        return form;
    }
}
