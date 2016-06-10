package jp.co.transcosmos.dm3.corePana.model.inquiry.form;

import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryHeaderForm;
import jp.co.transcosmos.dm3.core.vo.InquiryDtlInfo;
import jp.co.transcosmos.dm3.core.vo.InquiryHeader;

/**
 * 問合せの入力パラメータ受取り用フォーム （ヘッダ部用）.
 * <p>
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * チョ夢		2015.03.18	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class PanaInquiryHeaderForm extends InquiryHeaderForm {

	/** 郵便番号 */
	private String zip;
	/** 都道府県CD */
	private String prefCd;
	/** 市区町村番地 */
	private String address;
	/** 建物名 */
	private String addressOther;
	/** FAX番号 */
	private String fax;



	/**
	 * @return zip
	 */
	public String getZip() {
		return zip;
	}
	/**
	 * @param zip セットする zip
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	/**
	 * @return prefCd
	 */
	public String getPrefCd() {
		return prefCd;
	}
	/**
	 * @param prefCd セットする prefCd
	 */
	public void setPrefCd(String prefCd) {
		this.prefCd = prefCd;
	}
	/**
	 * @return address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address セットする address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return addressOther
	 */
	public String getAddressOther() {
		return addressOther;
	}
	/**
	 * @param addressOther セットする addressOther
	 */
	public void setAddressOther(String addressOther) {
		this.addressOther = addressOther;
	}
	/**
	 * @return fax
	 */
	public String getFax() {
		return fax;
	}
	/**
	 * @param fax セットする fax
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}



	/**
	 * 引数で渡されたお問合せヘッダ情報のバリーオブジェクトにフォームの値を設定する。<br/>
	 * 更新処理でも使用する事を考慮し、タイムスタンプ情報に関しては、更新日、更新者のみ設定する。<br/>
	 * <br/>
	 * @param inquiryHeader 値を設定するお問合せヘッダ情報のバリーオブジェクト
	 *
	 */
	@Override
	public void copyToInquiryHeader(InquiryHeader inquiryHeader, String editUserId) {

		super.copyToInquiryHeader(inquiryHeader, editUserId);

		// 郵便番号を設定
		((jp.co.transcosmos.dm3.corePana.vo.InquiryHeader)inquiryHeader).setZip(this.zip);

		// 都道府県CDを設定
		((jp.co.transcosmos.dm3.corePana.vo.InquiryHeader)inquiryHeader).setPrefCd(this.prefCd);

		// 市区町村番地を設定
		((jp.co.transcosmos.dm3.corePana.vo.InquiryHeader)inquiryHeader).setAddress(this.address);

		// 建物名を設定
		((jp.co.transcosmos.dm3.corePana.vo.InquiryHeader)inquiryHeader).setAddressOther(this.addressOther);

		// FAXを設定
		((jp.co.transcosmos.dm3.corePana.vo.InquiryHeader)inquiryHeader).setFax(this.fax);

	}

	/**
	 * 問合せ内容種別情報のバリーオブジェクトを作成する。<br/>
	 * <br/>
	 * @param inquiryDtlInfos　
	 * @return 問合せ内容種別情報バリーオブジェクトの配列
	 */
	@Override
	public void copyToInquiryDtlInfo(InquiryDtlInfo[] inquiryDtlInfos){

		if (inquiryDtlInfos == null) return;
		if (this.getInquiryDtlType() == null || this.getInquiryDtlType().length == 0) return;

		inquiryDtlInfos[0].setInquiryDtlType(this.getInquiryDtlType()[0]);
	}

}
