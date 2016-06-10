package jp.co.transcosmos.dm3.corePana.model.inquiry;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.inquiry.HousingInquiry;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHousing;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHousingQuestion;
import jp.co.transcosmos.dm3.dao.JoinResult;

/**
 * 問合せ情報.
 * <p>
 *
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * 郭中レイ		2015.04.16	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class InquiryInfo extends HousingInquiry {

	/** 査定問合情報 （お問合せヘッダ＋査定情報＋問合せアンケート＋都道府県マスタ） */
	private List<JoinResult> assessmentInquiry;
	/** 汎用問合せ情報 （お問合せヘッダ＋お問合せ内容種別情報＋汎用問合せ＋問合せアンケート） */
	private List<JoinResult> generalInquiry;
	/** 物件問合せ情報 */
	private InquiryHousing inquiryHousing;
	/** 問合せアンケート */
	private InquiryHousingQuestion[] inquiryHousingQuestion;
	/** 都道府県マスタ */
	private PrefMst prefMst;

	/**
	 * 査定問合情報を取得する。<br/>
	 * <br/>
	 *
	 * @return 査定問合情報
	 */
	public List<JoinResult> getAssessmentInquiry() {
		return assessmentInquiry;
	}

	/**
	 * 査定問合情報を設定する。<br/>
	 * <br/>
	 *
	 * @param assessmentInquiry
	 */
	public void setAssessmentInquiry(List<JoinResult> assessmentInquiry) {
		this.assessmentInquiry = assessmentInquiry;
	}

	/**
	 * 汎用問合せ情報を取得する。<br/>
	 * <br/>
	 *
	 * @return 汎用問合せ情報
	 */
	public List<JoinResult> getGeneralInquiry() {
		return generalInquiry;
	}

	/**
	 * 汎用問合せ情報を設定する。<br/>
	 * <br/>
	 *
	 * @param generalInquiry
	 */
	public void setGeneralInquiry(List<JoinResult> generalInquiry) {
		this.generalInquiry = generalInquiry;
	}

	/**
	 * 物件問合せ情報を取得する。<br/>
	 * <br/>
	 *
	 * @return 物件問合せ情報
	 */
	public InquiryHousing getInquiryHousing() {
		return inquiryHousing;
	}

	/**
	 * 物件問合せ情報を設定する。<br/>
	 * <br/>
	 *
	 * @param inquiryHousing
	 */
	public void setInquiryHousing(InquiryHousing inquiryHousing) {
		this.inquiryHousing = inquiryHousing;
	}

	/**
	 * 問合せアンケートを取得する。<br/>
	 * <br/>
	 *
	 * @return 問合せアンケート
	 */
	public InquiryHousingQuestion[] getInquiryHousingQuestion() {
		return inquiryHousingQuestion;
	}

	/**
	 * 問合せアンケートを設定する。<br/>
	 * <br/>
	 *
	 * @param inquiryHousingQuestion
	 */
	public void setInquiryHousingQuestion(
			InquiryHousingQuestion[] inquiryHousingQuestion) {
		this.inquiryHousingQuestion = inquiryHousingQuestion;
	}

	/**
	 * 都道府県マスタを取得する。<br/>
	 * <br/>
	 *
	 * @return 都道府県マスタ
	 */
	public PrefMst getPrefMst() {
		return prefMst;
	}

	/**
	 * 都道府県マスタを設定する。<br/>
	 * <br/>
	 *
	 * @param prefMst
	 */
	public void setPrefMst(PrefMst prefMst) {
		this.prefMst = prefMst;
	}
}
