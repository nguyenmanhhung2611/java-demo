package jp.co.transcosmos.dm3.core.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

/**
 * レングスバリデーションのユーティリティクラス.
 * <p>
 * カスタマイズされたレングス長の値を取得する。<br/>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.27	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class LengthValidationUtils {

	/** このユーティリテ の Bean ID */
	protected static String FACTORY_BEAN_ID = "lengthValidationUtils";

	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;

	/** カスタマイズされた文字列長を管理する codelookup 名 */
	protected String lookupName = "inputLength";
	
	
	
	/**
	 * 共通コード変換処理を設定する。<br/>
	 * <br/>
	 * @param codeLookupManager 共通コード変換処理
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * カスタマイズされた文字列長を管理する codelookup 名を変更する場合、このプロパティで設定する。<br/>
	 * （デフォルト値は inputLength）<br/>
	 * <br/>
	 * @param lookupName カスタマイズされた文字列長を管理する codelookup 名
	 */
	public void setLookupName(String lookupName) {
		this.lookupName = lookupName;
	}



	/**
	 * LengthValidationUtils のインスタンスを取得する。<br/>
	 * Spring のコンテキストから、 lengthValidationUtils で定義された LengthValidationUtils の
	 * インスタンスを取得する。<br/>
	 * 取得されるインスタンスは、LengthValidationUtils を継承した拡張クラスが復帰される場合もある。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return LengthValidationUtils、または継承して拡張したクラスのインスタンス
	 */
	public static LengthValidationUtils getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (LengthValidationUtils)springContext.getBean(LengthValidationUtils.FACTORY_BEAN_ID);
	}


	
	/**
	 * カスタマイズされたレングス長を取得する。<br/>
	 * label には、errorMessage.xml の errorLabels、errorCustom で使用されている各入力項目
	 * 毎の Key 値を設定する。<br/>
	 * もし、lookupName　プロパティで指定されている codelookup 定義に、label　に該当する値が存在する
	 * 場合、そちらの値を復帰する。<br/>
	 * 該当する値が存在しない場合は defaultValue の値を復帰する。<br/>
	 * lookupName　プロパティの初期値は、「inputLength」が設定されており、入力文字列長のカスタマイズを
	 * 行う場合は、customeLengthValidation.xml　を修正する。<br/>
	 * <br/>
	 * 
	 * @param label 入力欄のラベル名
	 * @param defaultValue デフォルトとなる文字列長
	 * @return
	 */
	public Integer getLength(String label, Integer defaultValue) {

		String value = this.codeLookupManager.lookupValue(this.lookupName, label);
		if (value == null) return defaultValue;

		return Integer.valueOf(value);
	}
	
	
	
	
}
