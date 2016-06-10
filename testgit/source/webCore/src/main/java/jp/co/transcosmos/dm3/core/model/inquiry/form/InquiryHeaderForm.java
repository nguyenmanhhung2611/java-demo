package jp.co.transcosmos.dm3.core.model.inquiry.form;

import java.util.Date;

import jp.co.transcosmos.dm3.core.vo.InquiryDtlInfo;
import jp.co.transcosmos.dm3.core.vo.InquiryHeader;

/**
 * 問合せの入力パラメータ受取り用フォーム （ヘッダ部用）.
 * <p>
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.18	新規作成
 * H.Mizuno		2015.04.30	お問合せ内容種別複数件対応
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class InquiryHeaderForm {

	/** 氏名(姓) */
	private String lname;
	/** 氏名(名) */
	private String fname;
	/** 氏名・カナ(姓) */
	private String lnameKana; 
	/** 氏名・カナ(名) */
	private String fnameKana;
	/** メールアドレス */
	private String email;
	/** 電話番号 */
	private String tel; 
	/** お問合せ内容 */
	private String inquiryText;
	/** お問合せ内容種別CD */
	private String[] inquiryDtlType;

	
	
	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	protected InquiryHeaderForm() {
		super();
	}



	/**
	 * 氏名(姓)を取得する。<br/>
	 * <br/>
	 * @return 氏名(姓)
	 */
	public String getLname() {
		return lname;
	}

	/**
	 * 氏名(姓)を設定する。<br/>
	 * <br/>
	 * @param lname 氏名(姓)
	 */
	public void setLname(String lname) {
		this.lname = lname;
	}

	/**
	 * 氏名(名)を取得する。<br/>
	 * <br/>
	 * @return 氏名(名)
	 */
	public String getFname() {
		return fname;
	}

	/**
	 * 氏名(名)を設定する。<br/>
	 * <br/>
	 * @param fname 氏名(名)
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}

	/**
	 * 氏名・カナ(姓)を取得する。<br/>
	 * <br/>
	 * @return 氏名・カナ(姓)
	 */
	public String getLnameKana() {
		return lnameKana;
	}

	/**
	 * 氏名・カナ(姓)を設定する。<br/>
	 * <br/>
	 * @param lnameKana　氏名・カナ(姓)
	 */
	public void setLnameKana(String lnameKana) {
		this.lnameKana = lnameKana;
	}
	
	/**
	 * 氏名・カナ(名)を取得する。<br/>
	 * <br/>
	 * @return 氏名・カナ(名)
	 */
	public String getFnameKana() {
		return fnameKana;
	}

	/**
	 * 氏名・カナ(名)を設定する。<br/>
	 * <br/>
	 * @param fnameKana　氏名・カナ(名)
	 */
	public void setFnameKana(String fnameKana) {
		this.fnameKana = fnameKana;
	}
	
	/**
	 * メールアドレスを取得する。<br/>
	 * <br/>
	 * @return メールアドレス
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * メールアドレスを設定する。<br/>
	 * <br/>
	 * @param email メールアドレス
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 電話番号を取得する。<br/>
	 * <br/>
	 * @return 電話番号
	 */
	public String getTel() {
		return tel;
	}
	
	/**
	 * 電話番号を設定する。<br/>
	 * <br/>
	 * @param tel 電話番号
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * お問合せ内容を取得する。<br/>
	 * <br/>
	 * @return お問合せ内容
	 */
	public String getInquiryText() {
		return inquiryText;
	}
	
	/**
	 * お問合せ内容を設定する。<br/>
	 * <br/>
	 * @param inquiryText お問合せ内容
	 */
	public void setInquiryText(String inquiryText) {
		this.inquiryText = inquiryText;
	}
	
	/**
	 * お問合せ内容種別CD を取得する。<br/>
	 * <br/>
	 *
	 * @return お問合せ内容種別CD
	 */
	public String[] getInquiryDtlType() {
		return inquiryDtlType;
	}

	/**
	 * お問合せ内容種別CD を設定する。<br/>
	 * <br/>
	 *
	 * @param inquiryDtlType
	 */
	public void setInquiryDtlType(String[] inquiryDtlType) {
		this.inquiryDtlType = inquiryDtlType;
	}
	
	/**
	 * 引数で渡されたお知らせ情報のバリーオブジェクトにフォームの値を設定する。<br/>
	 * 更新処理でも使用する事を考慮し、タイムスタンプ情報に関しては、更新日、更新者のみ設定する。<br/>
	 * <br/>
	 * @param inquiryHeader 値を設定するお問合せヘッダ情報のバリーオブジェクト
	 * 
	 */
	public void copyToInquiryHeader(InquiryHeader inquiryHeader, String editUserId) {
		
		// 氏名(姓)を設定
		inquiryHeader.setLname(this.lname);
		
		// 氏名(名)を設定
		inquiryHeader.setFname(this.fname);
		
		// 氏名・カナ(姓)を設定
		inquiryHeader.setLnameKana(this.lnameKana);
		
		// 氏名・カナ(名)を設定
		inquiryHeader.setFnameKana(this.fnameKana);
		
		// メールアドレスを設定
		inquiryHeader.setEmail(this.email);
		
		// 電話番号を設定
		inquiryHeader.setTel(this.tel);
		
		// お問合せ内容を設定
		inquiryHeader.setInquiryText(this.inquiryText);

		// 更新日付を設定
		inquiryHeader.setUpdDate(new Date());;

		// 更新担当者を設定
		inquiryHeader.setUpdUserId(editUserId);

	}

	/**
	 * 問合せ内容種別情報のバリーオブジェクトを作成する。<br/>
	 * <br/>
	 * @param inquiryDtlInfos　
	 * @return 問合せ内容種別情報バリーオブジェクトの配列
	 */
	public void copyToInquiryDtlInfo(InquiryDtlInfo[] inquiryDtlInfos){

		// note
		// 現状の管理画面では、検索時に複数のお問合せ内容種別情報に対応していない。
		// パラメータ改竄等で複数のお問合せ内容種別を登録されると誤動作する可能性があるので、
		// バリデーションで対応するか、下記コードでこのメソッドをオーバーライドする事。
		//
		// inquiryDtlInfos[0].setInquiryDtlType(this.inquiryDtlType[0]);
		//


// 2015.04.30 H.Mizuno お問合せ内容種別複数件対応 start		
//		inquiryDtlInfos[0].setInquiryDtlType(this.inquiryDtlType);
		if (inquiryDtlInfos == null) return;

		for (int i = 0; i<this.inquiryDtlType.length; ++i){
			inquiryDtlInfos[i].setInquiryDtlType(this.inquiryDtlType[i]);
		}
// 2015.04.30 H.Mizuno お問合せ種別複数件対応 end		

	}

}
