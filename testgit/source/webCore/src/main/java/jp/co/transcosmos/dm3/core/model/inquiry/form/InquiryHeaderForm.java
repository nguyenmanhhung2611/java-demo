package jp.co.transcosmos.dm3.core.model.inquiry.form;

import java.util.Date;

import jp.co.transcosmos.dm3.core.vo.InquiryDtlInfo;
import jp.co.transcosmos.dm3.core.vo.InquiryHeader;

/**
 * �⍇���̓��̓p�����[�^����p�t�H�[�� �i�w�b�_���p�j.
 * <p>
 * <pre>
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.18	�V�K�쐬
 * H.Mizuno		2015.04.30	���⍇�����e��ʕ������Ή�
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class InquiryHeaderForm {

	/** ����(��) */
	private String lname;
	/** ����(��) */
	private String fname;
	/** �����E�J�i(��) */
	private String lnameKana; 
	/** �����E�J�i(��) */
	private String fnameKana;
	/** ���[���A�h���X */
	private String email;
	/** �d�b�ԍ� */
	private String tel; 
	/** ���⍇�����e */
	private String inquiryText;
	/** ���⍇�����e���CD */
	private String[] inquiryDtlType;

	
	
	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	protected InquiryHeaderForm() {
		super();
	}



	/**
	 * ����(��)���擾����B<br/>
	 * <br/>
	 * @return ����(��)
	 */
	public String getLname() {
		return lname;
	}

	/**
	 * ����(��)��ݒ肷��B<br/>
	 * <br/>
	 * @param lname ����(��)
	 */
	public void setLname(String lname) {
		this.lname = lname;
	}

	/**
	 * ����(��)���擾����B<br/>
	 * <br/>
	 * @return ����(��)
	 */
	public String getFname() {
		return fname;
	}

	/**
	 * ����(��)��ݒ肷��B<br/>
	 * <br/>
	 * @param fname ����(��)
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}

	/**
	 * �����E�J�i(��)���擾����B<br/>
	 * <br/>
	 * @return �����E�J�i(��)
	 */
	public String getLnameKana() {
		return lnameKana;
	}

	/**
	 * �����E�J�i(��)��ݒ肷��B<br/>
	 * <br/>
	 * @param lnameKana�@�����E�J�i(��)
	 */
	public void setLnameKana(String lnameKana) {
		this.lnameKana = lnameKana;
	}
	
	/**
	 * �����E�J�i(��)���擾����B<br/>
	 * <br/>
	 * @return �����E�J�i(��)
	 */
	public String getFnameKana() {
		return fnameKana;
	}

	/**
	 * �����E�J�i(��)��ݒ肷��B<br/>
	 * <br/>
	 * @param fnameKana�@�����E�J�i(��)
	 */
	public void setFnameKana(String fnameKana) {
		this.fnameKana = fnameKana;
	}
	
	/**
	 * ���[���A�h���X���擾����B<br/>
	 * <br/>
	 * @return ���[���A�h���X
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * ���[���A�h���X��ݒ肷��B<br/>
	 * <br/>
	 * @param email ���[���A�h���X
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * �d�b�ԍ����擾����B<br/>
	 * <br/>
	 * @return �d�b�ԍ�
	 */
	public String getTel() {
		return tel;
	}
	
	/**
	 * �d�b�ԍ���ݒ肷��B<br/>
	 * <br/>
	 * @param tel �d�b�ԍ�
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * ���⍇�����e���擾����B<br/>
	 * <br/>
	 * @return ���⍇�����e
	 */
	public String getInquiryText() {
		return inquiryText;
	}
	
	/**
	 * ���⍇�����e��ݒ肷��B<br/>
	 * <br/>
	 * @param inquiryText ���⍇�����e
	 */
	public void setInquiryText(String inquiryText) {
		this.inquiryText = inquiryText;
	}
	
	/**
	 * ���⍇�����e���CD ���擾����B<br/>
	 * <br/>
	 *
	 * @return ���⍇�����e���CD
	 */
	public String[] getInquiryDtlType() {
		return inquiryDtlType;
	}

	/**
	 * ���⍇�����e���CD ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param inquiryDtlType
	 */
	public void setInquiryDtlType(String[] inquiryDtlType) {
		this.inquiryDtlType = inquiryDtlType;
	}
	
	/**
	 * �����œn���ꂽ���m�点���̃o���[�I�u�W�F�N�g�Ƀt�H�[���̒l��ݒ肷��B<br/>
	 * �X�V�����ł��g�p���鎖���l�����A�^�C���X�^���v���Ɋւ��ẮA�X�V���A�X�V�҂̂ݐݒ肷��B<br/>
	 * <br/>
	 * @param inquiryHeader �l��ݒ肷�邨�⍇���w�b�_���̃o���[�I�u�W�F�N�g
	 * 
	 */
	public void copyToInquiryHeader(InquiryHeader inquiryHeader, String editUserId) {
		
		// ����(��)��ݒ�
		inquiryHeader.setLname(this.lname);
		
		// ����(��)��ݒ�
		inquiryHeader.setFname(this.fname);
		
		// �����E�J�i(��)��ݒ�
		inquiryHeader.setLnameKana(this.lnameKana);
		
		// �����E�J�i(��)��ݒ�
		inquiryHeader.setFnameKana(this.fnameKana);
		
		// ���[���A�h���X��ݒ�
		inquiryHeader.setEmail(this.email);
		
		// �d�b�ԍ���ݒ�
		inquiryHeader.setTel(this.tel);
		
		// ���⍇�����e��ݒ�
		inquiryHeader.setInquiryText(this.inquiryText);

		// �X�V���t��ݒ�
		inquiryHeader.setUpdDate(new Date());;

		// �X�V�S���҂�ݒ�
		inquiryHeader.setUpdUserId(editUserId);

	}

	/**
	 * �⍇�����e��ʏ��̃o���[�I�u�W�F�N�g���쐬����B<br/>
	 * <br/>
	 * @param inquiryDtlInfos�@
	 * @return �⍇�����e��ʏ��o���[�I�u�W�F�N�g�̔z��
	 */
	public void copyToInquiryDtlInfo(InquiryDtlInfo[] inquiryDtlInfos){

		// note
		// ����̊Ǘ���ʂł́A�������ɕ����̂��⍇�����e��ʏ��ɑΉ����Ă��Ȃ��B
		// �p�����[�^��ₓ��ŕ����̂��⍇�����e��ʂ�o�^�����ƌ듮�삷��\��������̂ŁA
		// �o���f�[�V�����őΉ����邩�A���L�R�[�h�ł��̃��\�b�h���I�[�o�[���C�h���鎖�B
		//
		// inquiryDtlInfos[0].setInquiryDtlType(this.inquiryDtlType[0]);
		//


// 2015.04.30 H.Mizuno ���⍇�����e��ʕ������Ή� start		
//		inquiryDtlInfos[0].setInquiryDtlType(this.inquiryDtlType);
		if (inquiryDtlInfos == null) return;

		for (int i = 0; i<this.inquiryDtlType.length; ++i){
			inquiryDtlInfos[i].setInquiryDtlType(this.inquiryDtlType[i]);
		}
// 2015.04.30 H.Mizuno ���⍇����ʕ������Ή� end		

	}

}
