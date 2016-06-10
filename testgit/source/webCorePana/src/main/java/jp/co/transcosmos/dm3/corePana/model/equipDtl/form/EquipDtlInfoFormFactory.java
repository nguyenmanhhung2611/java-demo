package jp.co.transcosmos.dm3.corePana.model.equipDtl.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.form.FormPopulator;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * <pre>
 * 設備情報用 Form の Factory クラス
 * getInstance() か、Spring からインスタンスを取得して使用する事。
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * 郭中レイ		2015.4.14	新規作成
 *
 * 注意事項
 * Factory のインスタンスを直接生成しない事。　必ずgetInstance() で取得する事。
 *
 * </pre>
 */
public class EquipDtlInfoFormFactory {
	/** Form を生成する Factory の Bean ID */
	protected static String FACTORY_BEAN_ID = "equipDtlInfoFormFactory";

	/**
	 * EquipDtlInfoFormFactory のインスタンスを取得する。<br/>
	 * Spring のコンテキストから、 equipDtlInfoFormFactory で定義された EquipDtlInfoFormFactory の
	 * インスタンスを取得する。<br/>
	 * 取得されるインスタンスは、EquipDtlInfoFormFactory を継承した拡張クラスが復帰される場合もある。<br/>
	 * <br/>
	 *
	 * @param request HTTP リクエスト
	 * @return EquipDtlInfoFormFactory、または継承して拡張したクラスのインスタンス
	 */
	public static EquipDtlInfoFormFactory getInstance(HttpServletRequest request) {
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request
				.getServletContext());
		return (EquipDtlInfoFormFactory) springContext.getBean(EquipDtlInfoFormFactory.FACTORY_BEAN_ID);
	}

	/**
	 * 設備情報を格納する空の EquipDtlInfoForm のインスタンスを生成する。<br/>
	 * <br/>
	 *
	 * @return 空の EquipDtlInfoForm インスタンス
	 */
	public EquipDtlInfoForm createEquipDtlInfoForm() {
		return new EquipDtlInfoForm();
	}

	/**
	 * 設備情報を格納した EquipDtlInfoForm のインスタンスを生成する。<br/>
	 * EquipDtlInfoFormFactory には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 *
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した EquipDtlInfoForm インスタンス
	 */
	public EquipDtlInfoForm createEquipDtlInfoForm(HttpServletRequest request) {
		EquipDtlInfoForm form = createEquipDtlInfoForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}
}
