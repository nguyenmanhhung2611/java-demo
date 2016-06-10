package jp.co.transcosmos.dm3.core.model.inquiry.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.form.PagingListForm;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.EmailRFCValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NumberValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;


/**
 * ���⍇�����i�w�b�_�j�̌����p�����[�^�A����сA��ʃR���g���[���p�����[�^����p�t�H�[��.
 * ���������̎擾��A�c�a�����I�u�W�F�N�g�̐������s���B<br/>
 * <p>
 * <pre>
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.18	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class InquirySearchForm extends PagingListForm<JoinResult> implements Validateable {
	
	/** ������ʂ� command */
	private String searchCommand;
	/** ���⍇��ID */
	private String inquiryId;
	/** �����i���j�@�i���������j */
	private String keyLname;
	/** �����i���j�@�i���������j */
	private String keyFname;
	/** �����E�J�i�i�Z�C�j�@�i���������j */
	private String keyLnameKana;
	/** �����E�J�i�i���C�j�@�i���������j */
	private String keyFnameKana;
	/** ���[���A�h���X�@�i���������j */
	private String keyEmail;
	/** �d�b�ԍ��@�i���������j */
	private String keyTel;
	/** �Ή��X�e�[�^�X�@�i���������j */
	private String keyAnswerStatus;
	
	/** �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B */
	protected LengthValidationUtils lengthUtils;
	
	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;


	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	protected InquirySearchForm(){
		super();
	}
	
	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
	 * @param codeLookupManager ���ʃR�[�h�ϊ�����
	 */
	protected InquirySearchForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager){
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
	}
	
	/**
	 * ���⍇��ID���擾����B<br/>
	 * <br/>
	 * @return ���⍇��ID
	 */
	public String getInquiryId() {
		return inquiryId;
	}


	/**
	 * ���⍇��ID��ݒ肷��B<br/>
	 * <br/>
	 * @param inquiryId ���⍇��ID
	 */
	public void setInquiryId(String inquiryId) {
		this.inquiryId = inquiryId;
	}
	
	/**
	 * ������ʂ� command���擾����B<br/>
	 * <br/>
	 * @return ������ʂ� command
	 */
	public String getSearchCommand() {
		return searchCommand;
	}

	/**
	 * ������ʂ� command��ݒ肷��B<br/>
	 * <br/>
	 * @param searchCommand ������ʂ� command
	 */
	public void setSearchCommand(String searchCommand) {
		this.searchCommand = searchCommand;
	}

	/**
	 * �����i���j�@�i���������j���擾����B<br/>
	 * <br/>
	 * @return �����i���j�@�i���������j
	 */
	public String getKeyLname() {
		return keyLname;
	}

	/**
	 * �����i���j�@�i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @param keyLname �����i���j�@�i���������j
	 */
	public void setKeyLname(String keyLname) {
		this.keyLname = keyLname;
	}
	
	/**
	 * �����i���j�@�i���������j���擾����B<br/>
	 * <br/>
	 * @return�@�����i���j�@�i���������j
	 */
	public String getKeyFname() {
		return keyFname;
	}

	/**
	 * �����i���j�@�i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @param keyFname �����i���j�@�i���������j
	 */
	public void setKeyFname(String keyFname) {
		this.keyFname = keyFname;
	}

	/**
	 * �����E�J�i�i�Z�C�j�@�i���������j���擾����B<br/>
	 * <br/>
	 * @return �����E�J�i�i�Z�C�j�@�i���������j
	 */
	public String getKeyLnameKana() {
		return keyLnameKana;
	}

	/**
	 * �����E�J�i�i�Z�C�j�@�i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @param keyLnameKana �����E�J�i�i�Z�C�j�@�i���������j
	 */
	public void setKeyLnameKana(String keyLnameKana) {
		this.keyLnameKana = keyLnameKana;
	}
	
	/**
	 * �����E�J�i�i���C�j�@�i���������j���擾����B<br/>
	 * <br/>
	 * @return �����E�J�i�i���C�j�@�i���������j
	 */
	public String getKeyFnameKana() {
		return keyFnameKana;
	}

	/**
	 * �����E�J�i�i���C�j�@�i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @param keyFnameKana
	 */
	public void setKeyFnameKana(String keyFnameKana) {
		this.keyFnameKana = keyFnameKana;
	}

	/**
	 * ���[���A�h���X�@�i���������j���擾����B<br/>
	 * <br/>
	 * @return ���[���A�h���X�@�i���������j
	 */
	public String getKeyEmail() {
		return keyEmail;
	}

	/**
	 * ���[���A�h���X�@�i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @param keyEmail ���[���A�h���X�@�i���������j
	 */
	public void setKeyEmail(String keyEmail) {
		this.keyEmail = keyEmail;
	}

	/**
	 * �d�b�ԍ��@�i���������j���擾����B<br/>
	 * <br/>
	 * @return �d�b�ԍ��@�i���������j
	 */
	public String getKeyTel() {
		return keyTel;
	}

	/**
	 * �d�b�ԍ��@�i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @param keyTel �d�b�ԍ��@�i���������j
	 */
	public void setKeyTel(String keyTel) {
		this.keyTel = keyTel;
	}

	/**
	 * �Ή��X�e�[�^�X�@�i���������j���擾����B<br/>
	 * <br/>
	 * @return �Ή��X�e�[�^�X�@�i���������j
	 */
	public String getKeyAnswerStatus() {
		return keyAnswerStatus;
	}
	
	/**
	 * �Ή��X�e�[�^�X�@�i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @param keyAnswerStatus�@�Ή��X�e�[�^�X�@�i���������j
	 */
	public void setKeyAnswerStatus(String keyAnswerStatus) {
		this.keyAnswerStatus = keyAnswerStatus;
	}
	
	/**
	 * ���������I�u�W�F�N�g���쐬����B<br/>
	 * PagingListForm �Ɏ�������Ă���A�y�[�W�����p�̌������������������g�����A�󂯎�������N�G�X�g
	 * �p�����[�^�ɂ�錟�������𐶐�����B<br/>
	 * <br/>
	 * ���̃��\�b�h���ł́ADB �̕����t�B�[���h�����w�肵�Ă���R�[�h�����݂���B<br/>
	 * ���ׁ̈A�A�Ǘ����[�U�[���̃e�[�u���Ƃ��āAInformation �ȊO���g�p�����ꍇ�A���̃��\�b�h��
	 * �I�[�o�[���C�h����K�v������B<br/>
	 * <br/>
	 * @return ���������I�u�W�F�N�g
	 */
	@Override
	public DAOCriteria buildCriteria(){

		// �����p�I�u�W�F�N�g�̐���
		// �y�[�W�������s���ꍇ�APagingListForm�@�C���^�[�t�F�[�X���������� Form ����
		// DAOCriteria �𐶐�����K�v������B ����āA���g�� buildCriteria() ��
		// �g�p���Č��������𐶐����Ă���B
		DAOCriteria criteria = super.buildCriteria();
		
		// �����i���j�̌�������������
		if (!StringValidateUtil.isEmpty(this.keyLname)) {
			criteria.addWhereClause("lname", "%" + this.keyLname + "%",
					DAOCriteria.LIKE_CASE_INSENSITIVE);
		}
		
		// �����i���j�̌�������������
		if (!StringValidateUtil.isEmpty(this.keyFname)) {
			criteria.addWhereClause("fname", "%" + this.keyFname + "%",
					DAOCriteria.LIKE_CASE_INSENSITIVE);
		}
		
		// �����E�J�i(��)�̌�������������
		if (!StringValidateUtil.isEmpty(this.keyLnameKana)) {
			criteria.addWhereClause("lnameKana", "%" + this.keyLnameKana + "%",
					DAOCriteria.LIKE_CASE_INSENSITIVE);
		}
		
		// �����E�J�i(��)�̌�������������
		if (!StringValidateUtil.isEmpty(this.keyFnameKana)) {
			criteria.addWhereClause("fnameKana", "%" + this.keyFnameKana + "%",
					DAOCriteria.LIKE_CASE_INSENSITIVE);
		}

		// ���[���A�h���X�̌�������������
		if (!StringValidateUtil.isEmpty(this.keyEmail)) {
			criteria.addWhereClause("email", this.keyEmail);
		}
		
		// �d�b�ԍ��̌�������������
		if (!StringValidateUtil.isEmpty(this.keyTel)) {
			criteria.addWhereClause("tel", this.keyTel);
		}
		
		// �Ή��X�e�[�^�X�̌�������������
		if (!StringValidateUtil.isEmpty(this.keyAnswerStatus)) {
			criteria.addWhereClause("answerStatus", this.keyAnswerStatus);
		}
		
        return criteria;

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

        // �����i���j���̓`�F�b�N
        validKeyLname(errors);

        // �����i���j���̓`�F�b�N
        validKeyFname(errors);

        // �����E�J�i�i�Z�C�j���̓`�F�b�N
        validKeyLnameKana(errors);

        // �����E�J�i�i���C�j���̓`�F�b�N
        validKeyFnameKana(errors);

        // ���[���A�h���X���̓`�F�b�N
        validKeyEmail(errors);
        
        // �d�b�ԍ����̓`�F�b�N
        validKeyTel(errors);
        
        // �Ή��X�e�[�^�X���̓`�F�b�N
        validKeyAnswerStatus(errors);

        return (startSize == errors.size());
	}
	
	/**
	 * �����i���j �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validKeyLname(List<ValidationFailure> errors) {
        ValidationChain valLname = new ValidationChain("inquiry.search.keyLname",this.keyLname);
        // �����`�F�b�N
        valLname.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("inquiry.search.keyLname", 30)));
        valLname.validate(errors);
	}
	
	/**
	 * �����i���j �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validKeyFname(List<ValidationFailure> errors) {
        ValidationChain valFname = new ValidationChain("inquiry.search.keyFname",this.keyFname);
        // �����`�F�b�N
        valFname.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("inquiry.search.keyFname", 30)));
        valFname.validate(errors);
	}
	
	/**
	 * �����E�J�i�i�Z�C�j �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validKeyLnameKana(List<ValidationFailure> errors) {
        ValidationChain valLnameKana = new ValidationChain("inquiry.search.keyLnameKana",this.keyLnameKana);
        // �����`�F�b�N
        valLnameKana.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("inquiry.search.keyLnameKana", 30)));
        valLnameKana.validate(errors);
	}
	
	/**
	 * �����E�J�i�i���C�j �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validKeyFnameKana(List<ValidationFailure> errors) {
        ValidationChain valFnameKana = new ValidationChain("inquiry.search.keyFnameKana",this.keyFnameKana);
        // �����`�F�b�N
        valFnameKana.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("inquiry.search.keyFnameKana", 30)));
        valFnameKana.validate(errors);
	}
	
	/**
	 * ���[���A�h���X �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * �E���[���A�h���X�̏����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validKeyEmail(List<ValidationFailure> errors) {
        ValidationChain valEmail = new ValidationChain("inquiry.search.keyEmail",this.keyEmail);
        // �����`�F�b�N
        valEmail.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("inquiry.search.keyEmail", 255)));
		// ���[���A�h���X�̏����`�F�b�N
        valEmail.addValidation(new EmailRFCValidation());
        valEmail.validate(errors);
	}
	
	/**
	 * �d�b�ԍ� �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * �E���l�`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validKeyTel(List<ValidationFailure> errors) {
        ValidationChain valTel = new ValidationChain("inquiry.search.keyTel",this.keyTel);
        // �����`�F�b�N
        valTel.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("inquiry.search.keyTel", 13)));
		// ���l�`�F�b�N
        valTel.addValidation(new NumberValidation());
        valTel.validate(errors);
	}
	
	/**
	 * �Ή��X�e�[�^�X �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * �E�p�^�[���`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validKeyAnswerStatus(List<ValidationFailure> errors) {
        ValidationChain valAnswerStatus = new ValidationChain("inquiry.search.keyAnswerStatus",this.keyAnswerStatus);
        // �����`�F�b�N
        valAnswerStatus.addValidation(
        		new MaxLengthValidation(this.lengthUtils.getLength("inquiry.search.keyAnswerStatus", 1)));
        // �p�^�[���`�F�b�N
        valAnswerStatus.addValidation(new CodeLookupValidation(this.codeLookupManager,"inquiry_answerStatus"));
        valAnswerStatus.validate(errors);
	}


}
