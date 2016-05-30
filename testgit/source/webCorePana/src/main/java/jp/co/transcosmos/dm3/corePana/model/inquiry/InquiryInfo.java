package jp.co.transcosmos.dm3.corePana.model.inquiry;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.inquiry.HousingInquiry;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHousing;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHousingQuestion;
import jp.co.transcosmos.dm3.dao.JoinResult;

/**
 * �⍇�����.
 * <p>
 *
 * <pre>
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �s�����C		2015.04.16	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class InquiryInfo extends HousingInquiry {

	/** ����⍇��� �i���⍇���w�b�_�{������{�⍇���A���P�[�g�{�s���{���}�X�^�j */
	private List<JoinResult> assessmentInquiry;
	/** �ėp�⍇����� �i���⍇���w�b�_�{���⍇�����e��ʏ��{�ėp�⍇���{�⍇���A���P�[�g�j */
	private List<JoinResult> generalInquiry;
	/** �����⍇����� */
	private InquiryHousing inquiryHousing;
	/** �⍇���A���P�[�g */
	private InquiryHousingQuestion[] inquiryHousingQuestion;
	/** �s���{���}�X�^ */
	private PrefMst prefMst;

	/**
	 * ����⍇�����擾����B<br/>
	 * <br/>
	 *
	 * @return ����⍇���
	 */
	public List<JoinResult> getAssessmentInquiry() {
		return assessmentInquiry;
	}

	/**
	 * ����⍇����ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param assessmentInquiry
	 */
	public void setAssessmentInquiry(List<JoinResult> assessmentInquiry) {
		this.assessmentInquiry = assessmentInquiry;
	}

	/**
	 * �ėp�⍇�������擾����B<br/>
	 * <br/>
	 *
	 * @return �ėp�⍇�����
	 */
	public List<JoinResult> getGeneralInquiry() {
		return generalInquiry;
	}

	/**
	 * �ėp�⍇������ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param generalInquiry
	 */
	public void setGeneralInquiry(List<JoinResult> generalInquiry) {
		this.generalInquiry = generalInquiry;
	}

	/**
	 * �����⍇�������擾����B<br/>
	 * <br/>
	 *
	 * @return �����⍇�����
	 */
	public InquiryHousing getInquiryHousing() {
		return inquiryHousing;
	}

	/**
	 * �����⍇������ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param inquiryHousing
	 */
	public void setInquiryHousing(InquiryHousing inquiryHousing) {
		this.inquiryHousing = inquiryHousing;
	}

	/**
	 * �⍇���A���P�[�g���擾����B<br/>
	 * <br/>
	 *
	 * @return �⍇���A���P�[�g
	 */
	public InquiryHousingQuestion[] getInquiryHousingQuestion() {
		return inquiryHousingQuestion;
	}

	/**
	 * �⍇���A���P�[�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param inquiryHousingQuestion
	 */
	public void setInquiryHousingQuestion(
			InquiryHousingQuestion[] inquiryHousingQuestion) {
		this.inquiryHousingQuestion = inquiryHousingQuestion;
	}

	/**
	 * �s���{���}�X�^���擾����B<br/>
	 * <br/>
	 *
	 * @return �s���{���}�X�^
	 */
	public PrefMst getPrefMst() {
		return prefMst;
	}

	/**
	 * �s���{���}�X�^��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param prefMst
	 */
	public void setPrefMst(PrefMst prefMst) {
		this.prefMst = prefMst;
	}
}
