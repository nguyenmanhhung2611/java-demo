package jp.co.transcosmos.dm3.core.model.inquiry;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.vo.InquiryHousing;


/**
 * �����⍇�����.
 * <p>
 * <pre>
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.04.02	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class HousingInquiry implements InquiryInterface {

	/** �⍇���w�b�_��� */
	private InquiryHeaderInfo inquiryHeaderInfo;
	/** �����⍇����� */
	private List<InquiryHousing> inquiryHousings;
	/** ������� */
	private List<Housing> housings;



	/**
	 * �⍇���w�b�_�����擾����B<br/>
	 * <br/>
	 * @return �⍇���w�b�_���
	 */
	@Override
	public InquiryHeaderInfo getInquiryHeaderInfo() {
		return this.inquiryHeaderInfo;
	}

	/**
	 * �⍇���w�b�_����ݒ肷��B<br/>
	 * <br/>
	 * @param inquiryHeaderInfo �⍇���w�b�_���
	 */
	public void setInquiryHeaderInfo(InquiryHeaderInfo inquiryHeaderInfo) {
		this.inquiryHeaderInfo = inquiryHeaderInfo;
	}
	
	/**
	 * �����⍇�������擾����B<br/>
	 * <br/>
	 * @return �����⍇�����
	 */
	public List<InquiryHousing> getInquiryHousings() {
		return inquiryHousings;
	}

	/**
	 * �����⍇������ݒ肷��B<br/>
	 * <br/>
	 * @param inquiryHousings �����⍇�����
	 */
	public void setInquiryHousings(List<InquiryHousing> inquiryHousings) {
		this.inquiryHousings = inquiryHousings;
	}

	/**
	 * ���������擾����B<br/>
	 * <br/>
	 * @return �������
	 */
	public List<Housing> getHousings() {
		return housings;
	}

	/**
	 * ��������ݒ肷��B<br/>
	 * <br/>
	 * @param housings �������
	 */
	public void setHousings(List<Housing> housings) {
		this.housings = housings;
	}

}
