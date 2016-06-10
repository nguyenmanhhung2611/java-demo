package jp.co.transcosmos.dm3.corePana.model.assessment.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 査定メンテナンス用 Form の Factory クラス.
 * <p>
 * getInstance() か、Spring からインスタンスを取得して使用する事。
 * <p>
 * <pre>
 * 担当者	   修正日	  修正内容
 * ------------ ----------- -----------------------------------------------------
 * チョ夢		2015.04.28	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * Factory のインスタンスを直接生成しない事。　必ずgetInstance() で取得する事。
 *
 */
public class AssessmentFormFactory {

	/** Form を生成する Factory の Bean ID */
	protected static String FACTORY_BEAN_ID = "assessmentFormFactory";

	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;

	/** 共通パラメータオブジェクト */
	protected CommonParameters commonParameters;

	/** レングスバリデーション用ユーティリティ */
	protected LengthValidationUtils lengthUtils;



	/**
	 * 共通コード変換処理を設定する。<br/>
	 * <br/>
	 * @param codeLookupManager 共通コード変換処理
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * 共通パラメータオブジェクトを設定する。<br/>
	 * <br/>
	 * @param commonParameters 共通パラメータオブジェクト
	 */
	public void setCommonParameters(CommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * レングスバリデーション用ユーティリティを設定する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーション用ユーティリティ
	 */
	public void setLengthUtils(LengthValidationUtils lengthUtils) {
		this.lengthUtils = lengthUtils;
	}


	/**
	 * AssessmentFormFactory のインスタンスを取得する。<br/>
	 * Spring のコンテキストから、 assessmentFormFactory で定義された AssessmentFormFactory の
	 * インスタンスを取得する。<br/>
	 * 取得されるインスタンスは、assessmentFormFactory を継承した拡張クラスが復帰される場合もある。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return AssessmentFormFactory、または継承して拡張したクラスのインスタンス
	 */
	public static AssessmentFormFactory getInstance(HttpServletRequest request) {
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request
				.getServletContext());
		return (AssessmentFormFactory) springContext.getBean(AssessmentFormFactory.FACTORY_BEAN_ID);
	}

	/**
	 *査定のお申し込みの入力内容受取り用空の AssessmentInputForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の AssessmentInputForm インスタンス
	 */
	public AssessmentInputForm createAssessmentInputForm() {
		return new AssessmentInputForm(this.lengthUtils, this.commonParameters, this.codeLookupManager);
	}

	public AssessmentInputForm createAssessmentInputForm(HttpServletRequest request) {
		AssessmentInputForm form = createAssessmentInputForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}
}
