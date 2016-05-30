package jp.co.transcosmos.dm3.core.model.inquiry.form;

import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.inquiry.InquiryInterface;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.InquiryHeader;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;


/**
 * �⍇���X�e�[�^�X�̓��̓p�����[�^����p�t�H�[��.
 * <p>
 * <pre>
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * Y.Chou		2015.04.03	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class InquiryStatusForm implements Validateable {
	
	/** command �p�����[�^ */
	private String command;
	/** ���⍇��ID */
	private String inquiryId;
	/** ���⍇���敪 */
	private String inquiryType;
	/** �Ή��X�e�[�^�X */
	private String answerStatus;
	/** �Ή����e */
	private String answerText;
	
	/** �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B */
	protected LengthValidationUtils lengthUtils;
	
	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;
	
	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	protected InquiryStatusForm(){
		super();
	}
	
	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
	 * @param codeLookupManager ���ʃR�[�h�ϊ�����
	 */
	protected InquiryStatusForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager){
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
	}
	
	/**
	 * command �p�����[�^��ݒ肷��B<br/>
	 * <br/>
	 * @param command command �p�����[�^
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * command �p�����[�^���擾����B<br/>
	 * <br/>
	 * @return command �p�����[�^
	 */
	public void setCommand(String command) {
		this.command = command;
	}
	
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
	 * ���⍇���敪 ���擾����B<br/>
	 * <br/>
	 *
	 * @return ���⍇���敪
	 */
	public String getInquiryType() {
		return inquiryType;
	}

	/**
	 * ���⍇���敪 ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param inquiryType
	 */
	public void setInquiryType(String inquiryType) {
		this.inquiryType = inquiryType;
	}
	
	/**
	 * �Ή��X�e�[�^�X ���擾����B<br/>
	 * <br/>
	 *
	 * @return �Ή��X�e�[�^�X
	 */
	public String getAnswerStatus() {
		return answerStatus;
	}

	/**
	 * �Ή��X�e�[�^�X ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param answerStatus
	 */
	public void setAnswerStatus(String answerStatus) {
		this.answerStatus = answerStatus;
	}

	/**
	 * �Ή����e ���擾����B<br/>
	 * <br/>
	 *
	 * @return �Ή����e
	 */
	public String getAnswerText() {
		return answerText;
	}

	/**
	 * �Ή����e ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param answerText
	 */
	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}
	
	/**
	 * �����œn���ꂽ���m�点���̃o���[�I�u�W�F�N�g�Ƀt�H�[���̒l��ݒ肷��B<br/>
	 * �X�V�����ł��g�p���鎖���l�����A�^�C���X�^���v���Ɋւ��ẮA�X�V���A�X�V�҂̂ݐݒ肷��B<br/>
	 * <br/>
	 * @param inquiryHeader �l��ݒ肷�邨�⍇���w�b�_���̃o���[�I�u�W�F�N�g
	 * 
	 */
	public void copyToInquiryHeader(InquiryHeader inquiryHeader, String editUserId) {
		
		// �Ή��X�e�[�^�X��ݒ�
		inquiryHeader.setAnswerStatus(this.answerStatus);
		
		// �Ή����e��ݒ�
		inquiryHeader.setAnswerText(this.answerText);

		// �X�V���t��ݒ�
		inquiryHeader.setUpdDate(new Date());;

		// �X�V�S���҂�ݒ�
		inquiryHeader.setUpdUserId(editUserId);

	}
	
	/**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� Form �֏����l��ݒ肷��B<br/>
	 * <br/>
	 * @param inquiry InquiryInterface�@�����������⍇�����Ǘ��p�o���[�I�u�W�F�N�g
	 */
	public void setDefaultData(InquiryInterface inquiry) {

		// �o���[�I�u�W�F�N�g�Ɋi�[����Ă���p�X���[�h�̓n�b�V���l�Ȃ̂� Form �ɂ͐ݒ肵�Ȃ��B
		// �p�X���[�h�̓��͂͐V�K�o�^���̂ݕK�{�ŁA�X�V�������͔C�ӂ̓��͂ƂȂ�B
		// �X�V�����Ńp�X���[�h�̓��͂��s���Ȃ��ꍇ�A�p�X���[�h�̍X�V�͍s��Ȃ��B

		// �Ή��X�e�[�^�X ��ݒ�
		this.answerStatus = inquiry.getInquiryHeaderInfo().getInquiryHeader().getAnswerStatus();
		// �Ή����e ��ݒ�
		this.answerText = inquiry.getInquiryHeaderInfo().getInquiryHeader().getAnswerText();
		
	}
	
	/**
	 * �o���f�[�V��������<br/>
	 * ���N�G�X�g�p�����[�^�̃o���f�[�V�������s��<br/>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @return ���펞 true�A�G���[�� false
	 */
	@Override
	public boolean validate(List<ValidationFailure> errors) {
        int startSize = errors.size();
        
        // �X�e�[�^�X���̓`�F�b�N
        validAnswerStatus(errors);
        
        // �Ή����e���̓`�F�b�N
        validAnswerText(errors);

        return (startSize == errors.size());
	}
	
	/**
	 * �Ή��X�e�[�^�X �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * �E�p�^�[���`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validAnswerStatus(List<ValidationFailure> errors) {
        ValidationChain valAnswerStatus = new ValidationChain("inquiry.input.answerStatus",this.answerStatus);
        // �����`�F�b�N
        valAnswerStatus.addValidation(
        		new MaxLengthValidation(this.lengthUtils.getLength("inquiry.input.answerStatus", 1)));
        // �p�^�[���`�F�b�N
        valAnswerStatus.addValidation(new CodeLookupValidation(this.codeLookupManager,"inquiry_answerStatus"));
        valAnswerStatus.validate(errors);
	}
	
	/**
	 * �Ή����e  �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validAnswerText(List<ValidationFailure> errors) {
        ValidationChain valAnswerText = new ValidationChain("inquiry.input.answerText",this.answerText);
        // �����`�F�b�N
        valAnswerText.addValidation(
        		new MaxLengthValidation(this.lengthUtils.getLength("inquiry.input.answerText", 1000)));
        valAnswerText.validate(errors);
	}

}
