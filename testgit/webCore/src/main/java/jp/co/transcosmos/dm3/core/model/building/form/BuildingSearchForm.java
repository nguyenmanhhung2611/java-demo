package jp.co.transcosmos.dm3.core.model.building.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.form.PagingListForm;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.AlphanumericOnlyValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * <pre>
 * �������̌����p�����[�^�A����сA��ʃR���g���[���p�����[�^����p�t�H�[��
 * ���������ƂȂ郊�N�G�X�g�p�����[�^�̎擾��A�c�a�����I�u�W�F�N�g�̐������s���B
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.27	�V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class BuildingSearchForm extends PagingListForm<JoinResult> implements Validateable {

	// command ���N�G�X�g�p�����[�^�́AURL �}�b�s���O�Œ�`����Ă���AnamedCommands �̐���ɂ��g�p�����B
	// �������̌����@�\�ł́A�ȉ��̒l���ݒ肳���ꍇ������B
	//
	// list : ������񌟍��E�ꗗ��ʂŎ��ۂɌ������s���ꍇ�Ɏw�肷��B�@�i���w��̏ꍇ�A������ʂ������\���B�j
	// back : ���͊m�F��ʂ�����͉�ʂ֕��A�����ꍇ�B�@�i�w�肳�ꂽ�ꍇ�A�p�����[�^�̒l�������l�Ƃ��ē��͉�ʂɕ\������B�j
	
	/** ������ʂ� command �p�����[�^ */
	private String searchCommand;
	/** �����ԍ� �i���������j */
	private String keyBuildingCd;
	/** �\���p������ �i���������j */
	private String keyDisplayBuildingName;
	/** ���ݒn�E�s���{��CD */
	private String keyPrefCd;
	/** �V�X�e������CD */
	private String sysBuildingCd;
	
	/** �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B */
	protected LengthValidationUtils lengthUtils;


	
	/**
	 * �f�t�H���g�R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	protected BuildingSearchForm(){
		super();
	}

	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
	 */
	protected BuildingSearchForm(LengthValidationUtils lengthUtils){
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
        // �����ԍ����̓`�F�b�N
        validKeyBuildingCd(errors);
        // �\���p���������̓`�F�b�N
        validKeyDisplayBuildingName(errors);
        
        return (startSize == errors.size());
	}

	/**
	 * �����ԍ� �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validKeyBuildingCd(List<ValidationFailure> errors) {
        // �����ԍ����̓`�F�b�N
        ValidationChain valid = new ValidationChain("building.search.keyBuildingCd",this.keyBuildingCd);
        // �����`�F�b�N
        int len = this.lengthUtils.getLength("building.search.keyBuildingCd", 20);
        valid.addValidation(new MaxLengthValidation(len));
        // ���p�p���`�F�b�N
        valid.addValidation(new AlphanumericOnlyValidation());
        valid.validate(errors);

	}
	
	/**
	 * �\���p������ �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validKeyDisplayBuildingName(List<ValidationFailure> errors) {
		// �\���p���������̓`�F�b�N
		ValidationChain valid = new ValidationChain(
				"building.search.keyDisplayBuildingName",
				this.keyDisplayBuildingName);
		// �����`�F�b�N
		int len = this.lengthUtils.getLength(
				"building.search.keyDisplayBuildingName", 40);
		valid.addValidation(new MaxLengthValidation(len));
		valid.validate(errors);

	}
	
	/**
	 * ������ʂ� command �p�����[�^���擾����B<br/>
	 * <br/>
	 * @return ������ʂ� command �p�����[�^
	 */
	public String getSearchCommand() {
		return searchCommand;
	}

	/**
	 * ������ʂ� command �p�����[�^��ݒ肷��B<br/>
	 * <br/>
	 * @param searchCommand ������ʂ� command �p�����[�^
	 */
	public void setSearchCommand(String searchCommand) {
		this.searchCommand = searchCommand;
	}

	/**
	 * �����ԍ� �i���������j���擾����B<br/>
	 * <br/>
	 * @return �����ԍ� �i���������j
	 */
	public String getKeyBuildingCd() {
		return keyBuildingCd;
	}

	/**
	 * �����ԍ� �i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @param keyBuildingCd �����ԍ� �i���������j
	 */
	public void setKeyBuildingCd(String keyBuildingCd) {
		this.keyBuildingCd = keyBuildingCd;
	}

	/**
	 * ���擾����B<br/>
	 * <br/>
	 * @return 
	 */
	public String getKeyDisplayBuildingName() {
		return keyDisplayBuildingName;
	}

	/**
	 * �\���p������ �i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @param keyDisplayBuildingName �\���p������ �i���������j
	 */
	public void setKeyDisplayBuildingName(String keyDisplayBuildingName) {
		this.keyDisplayBuildingName = keyDisplayBuildingName;
	}

	/**
	 * �V�X�e������CD���擾����B<br/>
	 * <br/>
	 * @return �V�X�e������CD
	 */
	public String getSysBuildingCd() {
		return sysBuildingCd;
	}

	/**
	 * �V�X�e������CD��ݒ肷��B<br/>
	 * <br/>
	 * @param sysBuildingCd �V�X�e������CD
	 */
	public void setSysBuildingCd(String sysBuildingCd) {
		this.sysBuildingCd = sysBuildingCd;
	}

	/**
	 * ���ݒn�E�s���{��CD���擾����B<br/>
	 * <br/>
	 * @return ���ݒn�E�s���{��CD
	 */
	public String getKeyPrefCd() {
		return keyPrefCd;
	}

	/**
	 * ���ݒn�E�s���{��CD��ݒ肷��B<br/>
	 * <br/>
	 * @param keyPrefCd ���ݒn�E�s���{��CD
	 */
	public void setKeyPrefCd(String keyPrefCd) {
		this.keyPrefCd = keyPrefCd;
	}
	
	/**
	 * ���������I�u�W�F�N�g���쐬����B<br/>
	 * PagingListForm �Ɏ�������Ă���A�y�[�W�����p�̌������������������g�����A�󂯎�������N�G�X�g
	 * �p�����[�^�ɂ�錟�������𐶐�����B<br/>
	 * <br/>
	 * ���̃��\�b�h���ł́ADB �̕����t�B�[���h�����w�肵�Ă���R�[�h�����݂���B<br/>
	 * ���ׁ̈A�A�������̃e�[�u���Ƃ��āABuildingInfo �ȊO���g�p�����ꍇ�A���̃��\�b�h��
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


		// �����ԍ��̌�����������
		if (!StringValidateUtil.isEmpty(this.keyBuildingCd)) {
			criteria.addWhereClause("buildingCd", this.keyBuildingCd);
		}

		
		// �\���p�������̌�������������
		if (!StringValidateUtil.isEmpty(this.keyDisplayBuildingName)) {
			criteria.addWhereClause("displayBuildingName", "%" + this.keyDisplayBuildingName + "%",
					DAOCriteria.LIKE_CASE_INSENSITIVE);
		}

		// ���ݒn�E�s���{���̌�������������
		if (!StringValidateUtil.isEmpty(this.keyPrefCd)) {
			criteria.addWhereClause("prefCd", this.keyPrefCd);
		}
		
        return criteria;

	}
	

}
