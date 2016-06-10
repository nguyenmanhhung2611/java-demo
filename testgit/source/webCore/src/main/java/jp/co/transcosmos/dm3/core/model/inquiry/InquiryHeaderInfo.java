package jp.co.transcosmos.dm3.core.model.inquiry;

import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.vo.InquiryDtlInfo;
import jp.co.transcosmos.dm3.core.vo.InquiryHeader;


/**
 * 問合せヘッダ情報.
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
public class InquiryHeaderInfo {
	
	// TODO inquiryDtlInfo のプロパティは、inquiry_dtl_type を Key とした Map に変える可能性あり。
	
	/** 問合せヘッダ情報 */
	private InquiryHeader inquiryHeader;
	/** 問合せ内容種別情報 */
	private InquiryDtlInfo[] inquiryDtlInfos;
	/** マイページ会員情報 */
	private MypageUserInterface mypageUser;

	
	/**
	 * 問合せヘッダ情報を取得する。<br/>
	 * <br/>
	 * @return 問合せヘッダ情報
	 */
	public InquiryHeader getInquiryHeader() {
		return inquiryHeader;
	}

	/**
	 * 問合せヘッダ情報を設定する。<br/>
	 * <br/>
	 * @param inquiryHeader 問合せヘッダ情報
	 */
	public void setInquiryHeader(InquiryHeader inquiryHeader) {
		this.inquiryHeader = inquiryHeader;
	}

	/**
	 * 問合せ内容種別情報を取得する。<br/>
	 * <br/>
	 * @return 問合せ内容種別情報
	 */
	public InquiryDtlInfo[] getInquiryDtlInfos() {
		return inquiryDtlInfos;
	}

	/**
	 * 問合せ内容種別情報を設定する。<br/>
	 * <br/>
	 * @param inquiryDtlInfos 問合せ内容種別情報
	 */
	public void setInquiryDtlInfos(InquiryDtlInfo[] inquiryDtlInfos) {
		this.inquiryDtlInfos = inquiryDtlInfos;
	}

	/**
	 * マイページ会員情報を取得する。<br/>
	 * <br/>
	 * @return マイページ会員情報
	 */
	public MypageUserInterface getMypageUser() {
		return mypageUser;
	}

	/**
	 * マイページ会員情報を設定する。<br/>
	 * <br/>
	 * @param mypageUser マイページ会員情報
	 */
	public void setMypageUser(MypageUserInterface mypageUser) {
		this.mypageUser = mypageUser;
	}
}
