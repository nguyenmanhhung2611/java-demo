package jp.co.transcosmos.dm3.core.model.information.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.form.PagingListForm;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.AlphanumericOnlyValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.ValidDateValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * <pre>
 * ���m�点���̌����p�����[�^�A����сA��ʃR���g���[���p�����[�^����p�t�H�[��
 * ���������ƂȂ郊�N�G�X�g�p�����[�^�̎擾��A�c�a�����I�u�W�F�N�g�̐������s���B
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.05	�V�K�쐬
 * H.Mizuno		2015.02.26	�p�b�P�[�W�ړ��A�R���X�g���N�^�[�̉B��
 *
 * ���ӎ���
 *
 * </pre>
 */
public class InformationSearchForm extends PagingListForm<JoinResult> implements Validateable {
	// command ���N�G�X�g�p�����[�^�́AURL �}�b�s���O�Œ�`����Ă���AnamedCommands �̐���ɂ��g�p�����B
	// ���m�点���̌����@�\�ł́A�ȉ��̒l���ݒ肳���ꍇ������B
	//
	// list : ���m�点�����E�ꗗ��ʂŎ��ۂɌ������s���ꍇ�Ɏw�肷��B�@�i���w��̏ꍇ�A������ʂ������\���B�j
	// back : ���͊m�F��ʂ�����͉�ʂ֕��A�����ꍇ�B�@�i�w�肳�ꂽ�ꍇ�A�p�����[�^�̒l�������l�Ƃ��ē��͉�ʂɕ\������B�j

	/** ������ʂ� command �p�����[�^ */
	private String searchCommand;
	/** ���m�点�ԍ� �i���������j */
	private String keyInformationNo;
	/** �^�C�g�� �i���������j*/
	private String keyTitle;
	/** �o�^��from �i���������j*/
	private String keyInsDateFrom;
	/** �o�^��to �i���������j */
	private String keyInsDateTo;
	/** �Ώۉ���i���������j */
	private String keyUserId;
	/** ���m�点�ԍ� */
	private String informationNo;

	/** �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B */
	protected LengthValidationUtils lengthUtils;

	
	
	/**
	 * �f�t�H���g�R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	protected InformationSearchForm(){
		super();
	}

	

	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
	 */
	protected InformationSearchForm(LengthValidationUtils lengthUtils){
		super();
		this.lengthUtils = lengthUtils;
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

        // ���m�点�ԍ����̓`�F�b�N
        validKeyInformationNo(errors);

        // �^�C�g�����̓`�F�b�N
        validKeyTitle(errors);

        // �o�^��From���̓`�F�b�N
        validKeyInsDateFrom(errors);

        // �o�^��To���̓`�F�b�N
        validKeyInsDateTo(errors);

        // �Ώۉ�����̓`�F�b�N
        validKeyUserId(errors);

        return (startSize == errors.size());
	}

	/**
	 * ���m�点�ԍ� �o���f�[�V����<br/>
	 * �E�p�����`�F�b�N
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validKeyInformationNo(List<ValidationFailure> errors) {
        // ���m�点�ԍ����̓`�F�b�N
        ValidationChain valInformationNo = new ValidationChain("information.search.keyInformationNo",this.keyInformationNo);
		// �p�����`�F�b�N
        valInformationNo.addValidation(new AlphanumericOnlyValidation());
        // �����`�F�b�N
        valInformationNo.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("information.search.keyInformationNo", 13)));
        valInformationNo.validate(errors);
	}

	/**
	 * �^�C�g�� �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validKeyTitle(List<ValidationFailure> errors) {
        // �^�C�g�����̓`�F�b�N
        ValidationChain valTitle = new ValidationChain("information.search.keyTitle", this.keyTitle);
        // �����`�F�b�N
        valTitle.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("information.search.keyTitle", 200)));
        valTitle.validate(errors);
	}

	/**
	 * �o�^��From �o���f�[�V����<br/>
	 * �E���t�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validKeyInsDateFrom(List<ValidationFailure> errors) {
        // �o�^��From���̓`�F�b�N
        ValidationChain valInsDateFrom = new ValidationChain("information.search.keyInsDateFrom", this.keyInsDateFrom);
        // ���t�����`�F�b�N
        valInsDateFrom.addValidation(new ValidDateValidation("yyyy/MM/dd"));
        valInsDateFrom.validate(errors);
	}

	/**
	 * �o�^��To �o���f�[�V����<br/>
	 * �E���t�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validKeyInsDateTo(List<ValidationFailure> errors) {
        // �o�^��To���̓`�F�b�N
        ValidationChain valInsDateTo = new ValidationChain("information.search.keyInsDateTo", this.keyInsDateTo);
		// ���t�����`�F�b�N
        valInsDateTo.addValidation(new ValidDateValidation("yyyy/MM/dd"));
        valInsDateTo.validate(errors);
	}

	/**
	 * �Ώۉ�� �o���f�[�V����<br/>
	 * �E�p�����`�F�b�N
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validKeyUserId(List<ValidationFailure> errors) {
	    // �Ώۉ�����̓`�F�b�N
        ValidationChain valUserId = new ValidationChain("information.search.keyUserId", this.keyUserId);
		// �p�����`�F�b�N
        valUserId.addValidation(new AlphanumericOnlyValidation());
        // �����`�F�b�N
        valUserId.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("information.search.keyUserId", 20)));
        valUserId.validate(errors);
	}


	/**
	 * ������ʂ� command���擾����B<br/>
	 * <br/>
	 * @param searchCommand ������ʂ� command
	 */
	public String getSearchCommand() {
		return searchCommand;
	}

	/**
	 * ������ʂ� command��ݒ肷��B<br/>
	 * <br/>
	 * @return ������ʂ� command
	 */
	public void setSearchCommand(String searchCommand) {
		this.searchCommand = searchCommand;
	}

	/**
	 * ���m�点�ԍ� �i���������j���擾����B<br/>
	 * <br/>
	 * @param keyInformationNo ���m�点�ԍ� �i���������j
	 */
	public String getKeyInformationNo() {
		return keyInformationNo;
	}

	/**
	 * ���m�点�ԍ� �i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @return ���m�点�ԍ� �i���������j
	 */
	public void setKeyInformationNo(String informationNo) {
		this.keyInformationNo = informationNo;
	}

	/**
	 * �^�C�g�� �i���������j���擾����B<br/>
	 * <br/>
	 * @param keyTitle �^�C�g�� �i���������j
	 */
	public String getKeyTitle() {
		return keyTitle;
	}

	/**
	 * �^�C�g�� �i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @return �^�C�g�� �i���������j
	 */
	public void setKeyTitle(String title) {
		this.keyTitle = title;
	}

	/**
	 * �o�^��from �i���������j���擾����B<br/>
	 * <br/>
	 * @param keyInsDateFrom �o�^��from �i���������j
	 */
	public String getKeyInsDateFrom() {
		return keyInsDateFrom;
	}

	/**
	 * �o�^��from �i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @return �o�^��from �i���������j
	 */
	public void setKeyInsDateFrom(String insDateFrom) {
		this.keyInsDateFrom = insDateFrom;
	}

	/**
	 * �o�^��to �i���������j���擾����B<br/>
	 * <br/>
	 * @param keyInsDateTo �o�^��to �i���������j
	 */
	public String getKeyInsDateTo() {
		return keyInsDateTo;
	}

	/**
	 * �o�^��to �i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @return �o�^��to �i���������j
	 */
	public void setKeyInsDateTo(String insDateTo) {
		this.keyInsDateTo = insDateTo;
	}

	/**
	 * �Ώۉ���i���������j���擾����B<br/>
	 * <br/>
	 * @param keyUserId �Ώۉ���i���������j
	 */
	public String getKeyUserId() {
		return keyUserId;
	}

	/**
	 * �Ώۉ���i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @return �Ώۉ���i���������j
	 */
	public void setKeyUserId(String userId) {
		this.keyUserId = userId;
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


		// ���m�点�ԍ��̌�����������
		if (!StringValidateUtil.isEmpty(this.keyInformationNo)) {
			criteria.addWhereClause("informationNo", this.keyInformationNo);
		}

		
		// �^�C�g���̌�������������
		if (!StringValidateUtil.isEmpty(this.keyTitle)) {
			criteria.addWhereClause("title", "%" + this.keyTitle + "%",
					DAOCriteria.LIKE_CASE_INSENSITIVE);
		}
		
		// �o�^���̌�����������
		if (!StringValidateUtil.isEmpty(this.keyInsDateFrom)) {
			criteria.addWhereClause("insDate", this.keyInsDateFrom + " 00:00:00",
					DAOCriteria.GREATER_THAN_EQUALS);
		}
		if (!StringValidateUtil.isEmpty(this.keyInsDateTo)) {
			criteria.addWhereClause("insDate", this.keyInsDateTo + " 23:59:59",
					DAOCriteria.LESS_THAN_EQUALS);
		}

		// �Ώۉ���̌�������������
		if (!StringValidateUtil.isEmpty(this.keyUserId)) {
			criteria.addWhereClause("userId", "%" + this.keyUserId + "%",
					DAOCriteria.LIKE_CASE_INSENSITIVE);
		}
		
        return criteria;

	}
	

	
	/**
	 * ���m�点�ԍ����擾����B<br/>
	 * <br/>
	 * @param informationNo ���m�点�ԍ�
	 */
	public String getInformationNo() {
		return informationNo;
	}

	/**
	 * ���m�点�ԍ���ݒ肷��B<br/>
	 * <br/>
	 * @return ���m�点�ԍ�
	 */
	public void setInformationNo(String informationNo) {
		this.informationNo = informationNo;
	}
}
