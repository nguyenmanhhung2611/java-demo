package jp.co.transcosmos.dm3.core.model.inquiry.form;

import jp.co.transcosmos.dm3.core.vo.InquiryHousing;

/**
 * 物件問合せの入力パラメータ受取り用フォーム.
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
public class HousingInquiryForm implements InquiryForm {

	/** 問合せヘッダの入力フォーム */
	private InquiryHeaderForm commonInquiryForm;



	// TODO
	// 単一行のリクエストでも問題無いか評価する事。
	/**
	 * システム物件CD<br/>
	 * 複数物件を対応しないが、インターフェースとしては配列としておく。<br/>
	 */
	private String[] sysHousingCd;



	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	protected HousingInquiryForm(){
		super();
	}



	/**
	 * 共通部（ヘッダ部）の入力情報を取得する。<br/>
	 * <br/>
	 * @return commonInquiryForm 共通部（ヘッダ部）
	 */
	public String[] getSysHousingCd() {
		return sysHousingCd;
	}

	/**
	 * 共通部（ヘッダ部）の入力情報を設定する。<br/>
	 * <br/>
	 * @param sysHousingCd 共通部（ヘッダ部）の入力情報
	 */
	public void setSysHousingCd(String[] sysHousingCd) {
		this.sysHousingCd = sysHousingCd;
	}

	/**
	 * 問合せヘッダの入力フォームを取得する。<br/>
	 * <br/>
	 * @return  問合せヘッダの入力フォーム
	 */
	@Override
	public InquiryHeaderForm getInquiryHeaderForm() {
		return this.commonInquiryForm;
	}

	/**
	 * 問合せヘッダの入力フォームを設定する。<br/>
	 * <br/>
	 * @param commonInquiryForm 問合せヘッダの入力フォーム
	 */
	public void setCommonInquiryForm(InquiryHeaderForm commonInquiryForm) {
		this.commonInquiryForm = commonInquiryForm;
	}

	/**
	 * 引数で渡された物件問合せ情報のバリーオブジェクトにフォームの値を設定する。<br/>
	 * 引数で渡された idx に該当する行の Form 値を設定する。<br/>
	 * 物件問合せ情報の更新は、主キー値の更新が必要な為、通常の update() では更新できない。<br/>
	 * このメソッドは、基本的に新規登録時の使用を想定している。<br/>
	 * <br/>
	 * @param inquiryHousing 物件問合せ情報
	 */
	public void copyToInquiryHousing(InquiryHousing inquiryHousing) {

		// システム物件CD
		inquiryHousing.setSysHousingCd(this.sysHousingCd[0]);

	}

}
