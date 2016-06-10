package jp.co.transcosmos.dm3.core.model.inquiry;


/**
 * 問合せ情報インターフェース.
 * 各種問合せ情報はこのインターｇフェースを実装する事。<br/>
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
public interface InquiryInterface {

	public InquiryHeaderInfo getInquiryHeaderInfo();
	
}
