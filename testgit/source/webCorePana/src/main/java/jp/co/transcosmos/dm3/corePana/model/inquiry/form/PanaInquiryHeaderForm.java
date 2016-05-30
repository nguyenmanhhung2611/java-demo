package jp.co.transcosmos.dm3.corePana.model.inquiry.form;

import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryHeaderForm;
import jp.co.transcosmos.dm3.core.vo.InquiryDtlInfo;
import jp.co.transcosmos.dm3.core.vo.InquiryHeader;

/**
 * �⍇���̓��̓p�����[�^����p�t�H�[�� �i�w�b�_���p�j.
 * <p>
 * <pre>
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �`����		2015.03.18	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class PanaInquiryHeaderForm extends InquiryHeaderForm {

	/** �X�֔ԍ� */
	private String zip;
	/** �s���{��CD */
	private String prefCd;
	/** �s�撬���Ԓn */
	private String address;
	/** ������ */
	private String addressOther;
	/** FAX�ԍ� */
	private String fax;



	/**
	 * @return zip
	 */
	public String getZip() {
		return zip;
	}
	/**
	 * @param zip �Z�b�g���� zip
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
	 * @param prefCd �Z�b�g���� prefCd
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
	 * @param address �Z�b�g���� address
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
	 * @param addressOther �Z�b�g���� addressOther
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
	 * @param fax �Z�b�g���� fax
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}



	/**
	 * �����œn���ꂽ���⍇���w�b�_���̃o���[�I�u�W�F�N�g�Ƀt�H�[���̒l��ݒ肷��B<br/>
	 * �X�V�����ł��g�p���鎖���l�����A�^�C���X�^���v���Ɋւ��ẮA�X�V���A�X�V�҂̂ݐݒ肷��B<br/>
	 * <br/>
	 * @param inquiryHeader �l��ݒ肷�邨�⍇���w�b�_���̃o���[�I�u�W�F�N�g
	 *
	 */
	@Override
	public void copyToInquiryHeader(InquiryHeader inquiryHeader, String editUserId) {

		super.copyToInquiryHeader(inquiryHeader, editUserId);

		// �X�֔ԍ���ݒ�
		((jp.co.transcosmos.dm3.corePana.vo.InquiryHeader)inquiryHeader).setZip(this.zip);

		// �s���{��CD��ݒ�
		((jp.co.transcosmos.dm3.corePana.vo.InquiryHeader)inquiryHeader).setPrefCd(this.prefCd);

		// �s�撬���Ԓn��ݒ�
		((jp.co.transcosmos.dm3.corePana.vo.InquiryHeader)inquiryHeader).setAddress(this.address);

		// ��������ݒ�
		((jp.co.transcosmos.dm3.corePana.vo.InquiryHeader)inquiryHeader).setAddressOther(this.addressOther);

		// FAX��ݒ�
		((jp.co.transcosmos.dm3.corePana.vo.InquiryHeader)inquiryHeader).setFax(this.fax);

	}

	/**
	 * �⍇�����e��ʏ��̃o���[�I�u�W�F�N�g���쐬����B<br/>
	 * <br/>
	 * @param inquiryDtlInfos�@
	 * @return �⍇�����e��ʏ��o���[�I�u�W�F�N�g�̔z��
	 */
	@Override
	public void copyToInquiryDtlInfo(InquiryDtlInfo[] inquiryDtlInfos){

		if (inquiryDtlInfos == null) return;
		if (this.getInquiryDtlType() == null || this.getInquiryDtlType().length == 0) return;

		inquiryDtlInfos[0].setInquiryDtlType(this.getInquiryDtlType()[0]);
	}

}
