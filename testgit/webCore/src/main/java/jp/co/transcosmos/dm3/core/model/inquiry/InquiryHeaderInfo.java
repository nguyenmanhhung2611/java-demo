package jp.co.transcosmos.dm3.core.model.inquiry;

import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.vo.InquiryDtlInfo;
import jp.co.transcosmos.dm3.core.vo.InquiryHeader;


/**
 * �⍇���w�b�_���.
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
public class InquiryHeaderInfo {
	
	// TODO inquiryDtlInfo �̃v���p�e�B�́Ainquiry_dtl_type �� Key �Ƃ��� Map �ɕς���\������B
	
	/** �⍇���w�b�_��� */
	private InquiryHeader inquiryHeader;
	/** �⍇�����e��ʏ�� */
	private InquiryDtlInfo[] inquiryDtlInfos;
	/** �}�C�y�[�W������ */
	private MypageUserInterface mypageUser;

	
	/**
	 * �⍇���w�b�_�����擾����B<br/>
	 * <br/>
	 * @return �⍇���w�b�_���
	 */
	public InquiryHeader getInquiryHeader() {
		return inquiryHeader;
	}

	/**
	 * �⍇���w�b�_����ݒ肷��B<br/>
	 * <br/>
	 * @param inquiryHeader �⍇���w�b�_���
	 */
	public void setInquiryHeader(InquiryHeader inquiryHeader) {
		this.inquiryHeader = inquiryHeader;
	}

	/**
	 * �⍇�����e��ʏ����擾����B<br/>
	 * <br/>
	 * @return �⍇�����e��ʏ��
	 */
	public InquiryDtlInfo[] getInquiryDtlInfos() {
		return inquiryDtlInfos;
	}

	/**
	 * �⍇�����e��ʏ���ݒ肷��B<br/>
	 * <br/>
	 * @param inquiryDtlInfos �⍇�����e��ʏ��
	 */
	public void setInquiryDtlInfos(InquiryDtlInfo[] inquiryDtlInfos) {
		this.inquiryDtlInfos = inquiryDtlInfos;
	}

	/**
	 * �}�C�y�[�W��������擾����B<br/>
	 * <br/>
	 * @return �}�C�y�[�W������
	 */
	public MypageUserInterface getMypageUser() {
		return mypageUser;
	}

	/**
	 * �}�C�y�[�W�������ݒ肷��B<br/>
	 * <br/>
	 * @param mypageUser �}�C�y�[�W������
	 */
	public void setMypageUser(MypageUserInterface mypageUser) {
		this.mypageUser = mypageUser;
	}
}
