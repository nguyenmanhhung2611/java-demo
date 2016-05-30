package jp.co.transcosmos.dm3.core.vo;


/**
 * お問合せ内容種別情報.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 *
 * <pre>
 * 担当者        修正日        修正内容
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.03.10     新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 *
 */
public class InquiryDtlInfo {

	/** お問合せID */
	private String inquiryId;
	/** お問合せ内容種別CD */
	private String inquiryDtlType;

	/**
	 * お問合せID を取得する。<br/>
	 * <br/>
	 *
	 * @return お問合せID
	 */
	public String getInquiryId() {
		return inquiryId;
	}

	/**
	 * お問合せID を設定する。<br/>
	 * <br/>
	 *
	 * @param inquiryId
	 */
	public void setInquiryId(String inquiryId) {
		this.inquiryId = inquiryId;
	}

	/**
	 * お問合せ内容種別CD を取得する。<br/>
	 * <br/>
	 *
	 * @return お問合せ内容種別CD
	 */
	public String getInquiryDtlType() {
		return inquiryDtlType;
	}

	/**
	 * お問合せ内容種別CD を設定する。<br/>
	 * <br/>
	 *
	 * @param inquiryDtlType
	 */
	public void setInquiryDtlType(String inquiryDtlType) {
		this.inquiryDtlType = inquiryDtlType;
	}
}
