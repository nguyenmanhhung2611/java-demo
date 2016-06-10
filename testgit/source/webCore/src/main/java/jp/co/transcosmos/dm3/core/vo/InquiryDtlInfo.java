package jp.co.transcosmos.dm3.core.vo;


/**
 * ���⍇�����e��ʏ��.
 * <p>
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 * <p>
 *
 * <pre>
 * �S����        �C����        �C�����e
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.03.10     �V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �ʃJ�X�^�}�C�Y�Ōp�������\��������̂ŁA���ڃC���X�^���X�𐶐����Ȃ����B<br/>
 * �C���X�^���X���擾����ꍇ�́AValueObjectFactory ����擾���鎖�B<br/>
 *
 */
public class InquiryDtlInfo {

	/** ���⍇��ID */
	private String inquiryId;
	/** ���⍇�����e���CD */
	private String inquiryDtlType;

	/**
	 * ���⍇��ID ���擾����B<br/>
	 * <br/>
	 *
	 * @return ���⍇��ID
	 */
	public String getInquiryId() {
		return inquiryId;
	}

	/**
	 * ���⍇��ID ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param inquiryId
	 */
	public void setInquiryId(String inquiryId) {
		this.inquiryId = inquiryId;
	}

	/**
	 * ���⍇�����e���CD ���擾����B<br/>
	 * <br/>
	 *
	 * @return ���⍇�����e���CD
	 */
	public String getInquiryDtlType() {
		return inquiryDtlType;
	}

	/**
	 * ���⍇�����e���CD ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param inquiryDtlType
	 */
	public void setInquiryDtlType(String inquiryDtlType) {
		this.inquiryDtlType = inquiryDtlType;
	}
}
