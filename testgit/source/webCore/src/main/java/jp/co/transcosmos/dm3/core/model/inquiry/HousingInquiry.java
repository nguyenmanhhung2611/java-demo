package jp.co.transcosmos.dm3.core.model.inquiry;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.vo.InquiryHousing;


/**
 * 物件問合せ情報.
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
public class HousingInquiry implements InquiryInterface {

	/** 問合せヘッダ情報 */
	private InquiryHeaderInfo inquiryHeaderInfo;
	/** 物件問合せ情報 */
	private List<InquiryHousing> inquiryHousings;
	/** 物件情報 */
	private List<Housing> housings;



	/**
	 * 問合せヘッダ情報を取得する。<br/>
	 * <br/>
	 * @return 問合せヘッダ情報
	 */
	@Override
	public InquiryHeaderInfo getInquiryHeaderInfo() {
		return this.inquiryHeaderInfo;
	}

	/**
	 * 問合せヘッダ情報を設定する。<br/>
	 * <br/>
	 * @param inquiryHeaderInfo 問合せヘッダ情報
	 */
	public void setInquiryHeaderInfo(InquiryHeaderInfo inquiryHeaderInfo) {
		this.inquiryHeaderInfo = inquiryHeaderInfo;
	}
	
	/**
	 * 物件問合せ情報を取得する。<br/>
	 * <br/>
	 * @return 物件問合せ情報
	 */
	public List<InquiryHousing> getInquiryHousings() {
		return inquiryHousings;
	}

	/**
	 * 物件問合せ情報を設定する。<br/>
	 * <br/>
	 * @param inquiryHousings 物件問合せ情報
	 */
	public void setInquiryHousings(List<InquiryHousing> inquiryHousings) {
		this.inquiryHousings = inquiryHousings;
	}

	/**
	 * 物件情報を取得する。<br/>
	 * <br/>
	 * @return 物件情報
	 */
	public List<Housing> getHousings() {
		return housings;
	}

	/**
	 * 物件情報を設定する。<br/>
	 * <br/>
	 * @param housings 物件情報
	 */
	public void setHousings(List<Housing> housings) {
		this.housings = housings;
	}

}
