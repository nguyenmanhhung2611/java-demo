package jp.co.transcosmos.dm3.core.model.inquiry.form;

/**
 * お問合せフォーム用インターフェース.
 * <p>
 * 各お問合せ登録で使用するフォームクラスは、このインターフェースを実装する事。<br/>
 * <p>
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.04.02	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public interface InquiryForm {

	/**
	 * お問合せヘッダ登録用のフォームオブジェクトを取得する。<br/>
	 * <br/>
	 * @return お問合せヘッダ登録用のフォームオブジェクト
	 */
	public InquiryHeaderForm getInquiryHeaderForm();
	
}
