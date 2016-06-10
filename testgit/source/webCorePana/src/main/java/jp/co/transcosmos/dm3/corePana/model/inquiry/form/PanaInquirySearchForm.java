package jp.co.transcosmos.dm3.corePana.model.inquiry.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.inquiry.form.InquirySearchForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.corePana.validation.DateFromToValidation;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.AlphanumericOnlyValidation;
import jp.co.transcosmos.dm3.validation.AsciiOnlyValidation;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.ValidDateValidation;
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
 * �s�����C		2015.03.18	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class PanaInquirySearchForm extends InquirySearchForm implements Validateable {

	/** ������ʂ� command */
	private String command;
	/** �����ԍ��@�i���������j */
	private String keyHousingCd;
	/** �������@�i���������j */
	private String keyDisplayHousingName;
	/** ����ԍ��@�i���������j */
	private String keyUserId;
	/** �⍇�����J�n���@�i���������j */
	private String keyInquiryDateStart;
	/** �⍇�����I�����@�i���������j */
	private String keyInquiryDateEnd;
	/** �⍇��ʁ@�i���������j */
	private String[] keyInquiryType;
	/** ���⍇�����e��ʁ@�i���������j */
	private String[] keyInquiryDtlType;
	/** ���⍇��ID_�폜�p */
	private String keyInquiryId;

	/** �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B */
	protected LengthValidationUtils lengthUtils;

	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;


	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	protected PanaInquirySearchForm(){
		super();
	}

	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
	 * @param codeLookupManager ���ʃR�[�h�ϊ�����
	 */
	protected PanaInquirySearchForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager){
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * @return command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @param command �Z�b�g���� command
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * @return keyHousingCd
	 */
	public String getKeyHousingCd() {
		return keyHousingCd;
	}

	/**
	 * @param keyHousingCd �Z�b�g���� keyHousingCd
	 */
	public void setKeyHousingCd(String keyHousingCd) {
		this.keyHousingCd = keyHousingCd;
	}

	/**
	 * @return keyDisplayHousingName
	 */
	public String getKeyDisplayHousingName() {
		return keyDisplayHousingName;
	}

	/**
	 * @param keyDisplayHousingName �Z�b�g���� keyDisplayHousingName
	 */
	public void setKeyDisplayHousingName(String keyDisplayHousingName) {
		this.keyDisplayHousingName = keyDisplayHousingName;
	}

	/**
	 * @return keyUserId
	 */
	public String getKeyUserId() {
		return keyUserId;
	}

	/**
	 * @param keyUserId �Z�b�g���� keyUserId
	 */
	public void setKeyUserId(String keyUserId) {
		this.keyUserId = keyUserId;
	}

	/**
	 * @return keyInquiryDateStart
	 */
	public String getKeyInquiryDateStart() {
		return keyInquiryDateStart;
	}

	/**
	 * @param keyInquiryDateStart �Z�b�g���� keyInquiryDateStart
	 */
	public void setKeyInquiryDateStart(String keyInquiryDateStart) {
		this.keyInquiryDateStart = keyInquiryDateStart;
	}

	/**
	 * @return keyInquiryDateEnd
	 */
	public String getKeyInquiryDateEnd() {
		return keyInquiryDateEnd;
	}

	/**
	 * @param keyInquiryDateEnd �Z�b�g���� keyInquiryDateEnd
	 */
	public void setKeyInquiryDateEnd(String keyInquiryDateEnd) {
		this.keyInquiryDateEnd = keyInquiryDateEnd;
	}

	/**
	 * @return keyInquiryType
	 */
	public String[] getKeyInquiryType() {
		return keyInquiryType;
	}

	/**
	 * @param keyInquiryType �Z�b�g���� keyInquiryType
	 */
	public void setKeyInquiryType(String[] keyInquiryType) {
		this.keyInquiryType = keyInquiryType;
	}

	/**
	 * @return keyInquiryDtlType
	 */
	public String[] getKeyInquiryDtlType() {
		return keyInquiryDtlType;
	}

	/**
	 * @param keyInquiryDtlType �Z�b�g���� keyInquiryDtlType
	 */
	public void setKeyInquiryDtlType(String[] keyInquiryDtlType) {
		this.keyInquiryDtlType = keyInquiryDtlType;
	}

	/**
	 * @return keyInquiryId
	 */
	public String getKeyInquiryId() {
		return keyInquiryId;
	}

	/**
	 * @param keyInquiryId �Z�b�g���� keyInquiryId
	 */
	public void setKeyInquiryId(String keyInquiryId) {
		this.keyInquiryId = keyInquiryId;
	}

	/**
     * ���������I�u�W�F�N�g���쐬����B<br/>
     * PagingListForm �Ɏ�������Ă���A�y�[�W�����p�̌������������������g�����A�󂯎�������N�G�X�g
     * �p�����[�^�ɂ�錟�������𐶐�����B<br/>
     * <br/>
     * @return ���������I�u�W�F�N�g
     */
    @Override
    public DAOCriteria buildCriteria() {

        // �����p�I�u�W�F�N�g�̐���
        // �y�[�W�������s���ꍇ�APagingListForm�@�C���^�[�t�F�[�X���������� Form ����
        // DAOCriteria �𐶐�����K�v������B ����āA���g�� buildCriteria() ��
        // �g�p���Č��������𐶐����Ă���B
        return buildCriteria(super.buildCriteria());

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
	public DAOCriteria buildCriteria(DAOCriteria criteria){

		// �����ԍ��̌�������������
		if (!StringValidateUtil.isEmpty(this.keyHousingCd)) {
			criteria.addWhereClause("housingCd", this.keyHousingCd);
		}

		// �������̌�������������
		if (!StringValidateUtil.isEmpty(this.keyDisplayHousingName)) {
			criteria.addWhereClause("displayHousingName", "%" + this.keyDisplayHousingName + "%",
					DAOCriteria.LIKE_CASE_INSENSITIVE);
		}

		// ����ԍ��̌�������������
		if (!StringValidateUtil.isEmpty(this.keyUserId)) {
			criteria.addWhereClause("userId", this.keyUserId);
		}

		// ���[���A�h���X�̌�������������
		if (!StringValidateUtil.isEmpty(this.getKeyEmail())) {
			criteria.addWhereClause("email", this.getKeyEmail());
		}

		// �⍇�����J�n���̌�������������
        if (!StringValidateUtil.isEmpty(this.keyInquiryDateStart)) {
            criteria.addWhereClause("inquiryDate", this.keyInquiryDateStart + " 00:00:00", DAOCriteria.GREATER_THAN_EQUALS);
        }

        // �⍇�����J�n���̌�������������
        if (!StringValidateUtil.isEmpty(this.keyInquiryDateStart)) {
            criteria.addWhereClause("inquiryDate", this.keyInquiryDateStart + " 00:00:00", DAOCriteria.LESS_THAN_EQUALS);
        }

		// �Ή��X�e�[�^�X�̌�������������
		if (!StringValidateUtil.isEmpty(this.getKeyAnswerStatus())) {
			criteria.addWhereClause("answerStatus", this.getKeyAnswerStatus());
		}

		// �⍇��ʂ̌�������������

		// ���⍇�����e��ʂ̌�������������

		// �����⍇�ԍ��̌�������������
		if (!StringValidateUtil.isEmpty(this.getInquiryId())) {
			criteria.addWhereClause("inquiryId", this.getInquiryId());
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

        // �����ԍ����̓`�F�b�N
        validHousingCd(errors);

        // ���������̓`�F�b�N
        validDisplayHousingName(errors);

        // ����ԍ����̓`�F�b�N
        validUserId(errors);

        // ���[���A�h���X���̓`�F�b�N
        validKeyEmail(errors);

        // �⍇�����J�n�����̓`�F�b�N
        validInquiryDateStart(errors);

        // �⍇�����I�������̓`�F�b�N
        validInquiryDateEnd(errors);

        // �⍇��ʓ��̓`�F�b�N
        validInquiryType(errors);

        // �⍇�ԍ����̓`�F�b�N
        validInquiryId(errors);

        // �Ή��X�e�[�^�X���̓`�F�b�N
        validKeyAnswerStatus(errors);

        // �⍇�����J�n�� < �⍇�����I�����`�F�b�N
        validInquiryDateCom(errors);

        // �����ԍ��A�������A�⍇��ʂ̓��̓`�F�b�N
        validInquiryTypeJoin(errors);

        return (startSize == errors.size());
	}

	/**
	 * �����ԍ��o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * �E���p�p�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validHousingCd(List<ValidationFailure> errors) {
        ValidationChain valHousingCd = new ValidationChain("inquiryList.search.housingCd",this.keyHousingCd);
        // �����`�F�b�N
        valHousingCd.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("inquiryList.search.housingCd", 10)));
        // ���p�p�����`�F�b�N
        valHousingCd.addValidation(new AlphanumericOnlyValidation());
        valHousingCd.validate(errors);
	}

	/**
	 * �������o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validDisplayHousingName(List<ValidationFailure> errors) {
        ValidationChain valDisplayHousingName = new ValidationChain("inquiryList.search.displayHousingName",this.keyDisplayHousingName);
        // �����`�F�b�N
        valDisplayHousingName.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("inquiryList.search.displayHousingName", 25)));
        valDisplayHousingName.validate(errors);
	}

	/**
	 * ����ԍ� �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validUserId(List<ValidationFailure> errors) {
        ValidationChain valUserId = new ValidationChain("inquiryList.search.userId",this.keyUserId);
        // �����`�F�b�N
        valUserId.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("inquiryList.search.userId", 20)));
        // ���p�p�����`�F�b�N
        valUserId.addValidation(new AlphanumericOnlyValidation());
        valUserId.validate(errors);
	}

	/**
	 * ���[���A�h���X �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * �E���[���A�h���X�̏����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validKeyEmail(List<ValidationFailure> errors) {
        ValidationChain valEmail = new ValidationChain("inquiryList.search.keyEmail",this.getKeyEmail());
        // �����`�F�b�N
        valEmail.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("inquiryList.search.keyEmail", 255)));
        // ���p�����`�F�b�N
        valEmail.addValidation(new AsciiOnlyValidation());
        valEmail.validate(errors);
	}

	/**
	 * �⍇�����J�n�� �o���f�[�V����<br/>
	 * �E���t�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validInquiryDateStart(List<ValidationFailure> errors) {
        ValidationChain valInquiryDateStart = new ValidationChain("inquiryList.search.inquiryDateStart",this.keyInquiryDateStart);
        // ���t�����`�F�b�N
        valInquiryDateStart.addValidation(new ValidDateValidation("yyyy/MM/dd"));
        valInquiryDateStart.validate(errors);
	}

	/**
	 *  �⍇�����I���� �o���f�[�V����<br/>
	 * �E���t�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validInquiryDateEnd(List<ValidationFailure> errors) {
        ValidationChain valInquiryDateEnd = new ValidationChain("inquiryList.search.inquiryDateEnd",this.keyInquiryDateEnd);
        // ���t�����`�F�b�N
        valInquiryDateEnd.addValidation(new ValidDateValidation("yyyy/MM/dd"));
        valInquiryDateEnd.validate(errors);
	}

	/**
	 *  �⍇��� �o���f�[�V����<br/>
	 * �E�p�^�[���`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validInquiryType(List<ValidationFailure> errors) {
		// �⍇���
		String[] inquiryType = this.keyInquiryType;

		if(null != inquiryType) {
			for(String str : inquiryType) {
		        ValidationChain valInquiryType = new ValidationChain("inquiryList.search.inquiryType", str);
		        // �p�^�[���`�F�b�N
		        valInquiryType.addValidation(new CodeLookupValidation(this.codeLookupManager, "inquiry_type"));
		        valInquiryType.validate(errors);
			}
		}

		// ���⍇�����e���
		String[] inquiryDtlType = this.keyInquiryDtlType;

		if(null != inquiryDtlType) {
			for(String str : inquiryDtlType) {
		        ValidationChain valInquiryDtlType = new ValidationChain("inquiryList.search.inquiryDtlType", str);
		        // �p�^�[���`�F�b�N
		        valInquiryDtlType.addValidation(new CodeLookupValidation(this.codeLookupManager, "inquiry_dtl_type"));
		        valInquiryDtlType.validate(errors);
			}
		}
	}

	/**
	 *  �����⍇�ԍ� �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validInquiryId(List<ValidationFailure> errors) {
        ValidationChain valInquiryId = new ValidationChain("inquiryList.search.inquiryId",this.keyInquiryId);
        // �����`�F�b�N
        valInquiryId.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("inquiryList.search.inquiryId", 13)));
        // ���p�p�����`�F�b�N
        valInquiryId.addValidation(new AlphanumericOnlyValidation());
        valInquiryId.validate(errors);
	}

	/**
	 * �Ή��X�e�[�^�X �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * �E�p�^�[���`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validKeyAnswerStatus(List<ValidationFailure> errors) {
        ValidationChain valAnswerStatus = new ValidationChain("inquiryList.search.keyAnswerStatus",this.getKeyAnswerStatus());
        // �����`�F�b�N
        valAnswerStatus.addValidation(
        		new MaxLengthValidation(this.lengthUtils.getLength("inquiryList.search.keyAnswerStatus", 1)));
        // �p�^�[���`�F�b�N
        valAnswerStatus.addValidation(new CodeLookupValidation(this.codeLookupManager,"inquiry_answerStatus"));
        valAnswerStatus.validate(errors);
	}

    /**
     * �⍇�����J�n�� < �⍇�����I���� �o���f�[�V����<br/>
     * �E���t��r�`�F�b�N
     * <br/>
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
    protected void validInquiryDateCom(List<ValidationFailure> errors) {
        ValidationChain valid = new ValidationChain("inquiryList.search.inquiryDateStart", this.keyInquiryDateStart);
        // ���t��r�`�F�b�N
        valid.addValidation(new DateFromToValidation("yyyy/MM/dd", "�⍇�����I����", this.keyInquiryDateEnd));
        valid.validate(errors);
    }

    /**
     * �����ԍ��A�������A�⍇��� �o���f�[�V����<br/>
     * �E�����ԍ��A�������A�⍇��ʂ̓��̓`�F�b�N
     * <br/>
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
    protected void validInquiryTypeJoin(List<ValidationFailure> errors) {
    	// �����ԍ����͕���������͂����ꍇ
    	if(!StringValidateUtil.isEmpty(this.keyHousingCd) || !StringValidateUtil.isEmpty(this.keyDisplayHousingName)) {
    		// �⍇��ʂ�"����⍇��"����"�ėp�⍇��"��I�������ꍇ
    		if(null != this.keyInquiryType) {
	    		for(String inquiryType : this.keyInquiryType) {
		    		if("01".equals(inquiryType) || "02".equals(inquiryType)) {
			            // ���t��r�`�F�b�N
			    		String param[] = new String[] { "�����⍇��" };
		                ValidationFailure vf = new ValidationFailure(
		                        "inquiryTypeJoinError", "�����ԍ�", "������", param);
		                errors.add(vf);
		                break;
		    		}
	    		}
	    	}
    	}
    }

}
