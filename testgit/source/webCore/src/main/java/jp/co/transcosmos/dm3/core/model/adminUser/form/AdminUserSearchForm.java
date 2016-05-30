package jp.co.transcosmos.dm3.core.model.adminUser.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.form.PagingListForm;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * �Ǘ����[�U�[�����e�i���X�̌����p�����[�^�A����сA��ʃR���g���[���p�����[�^����p�t�H�[��.
 * <p>
 * ���������ƂȂ郊�N�G�X�g�p�����[�^�̎擾��A�c�a�����I�u�W�F�N�g�̐������s���B
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.28	Shamaison ���Q�l�ɐV�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class AdminUserSearchForm extends PagingListForm<JoinResult> implements Validateable {

	// command ���N�G�X�g�p�����[�^�́AURL �}�b�s���O�Œ�`����Ă���AnamedCommands �̐���ɂ��g�p�����B
	// �Ǘ����[�U�[�̃����e�i���X�@�\�ł́A�ȉ��̒l���ݒ肳���ꍇ������B
	//
	// list : �Ǘ����[�U�[������ʂŎ��ۂɌ������s���ꍇ�Ɏw�肷��B�@�i���w��̏ꍇ�A������ʂ������\���B�j
	// back : ���͊m�F��ʂ�����͉�ʂ֕��A�����ꍇ�B�@�i�w�肳�ꂽ�ꍇ�A�p�����[�^�̒l�������l�Ƃ��ē��͉�ʂɕ\������B�j

	/** ������ʂ� command �p�����[�^ */
	private String searchCommand;

	/** ���O�C��ID �i���������j */
	private String keyLoginId;
	/** ���[�U�[�� �i���������j*/
	private String keyUserName;
	/** ���[���A�h���X �i���������j*/
	private String keyEmail;
	/** ���� �i���������j */
	private String keyRoleId;
	/** �����Ώۃ��[�U�[ID (LoginUser �C���^�[�t�F�[�X�� getUserId() �����A�����L�[�̒l) */
	private String userId;

	/** �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B */
	protected LengthValidationUtils lengthUtils;
	
	

	/**
	 * �f�t�H���g�R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	protected AdminUserSearchForm(){
		super();
	}



	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
	 */
	protected AdminUserSearchForm(LengthValidationUtils lengthUtils){
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

        // ���O�C��ID���̓`�F�b�N
        validKeyLoginId(errors);
        // ���[�U�����̓`�F�b�N
        validKeyUserName(errors);
        // ���[���A�h���X���̓`�F�b�N
        validKeyEmail(errors);
        // �������̓`�F�b�N
        validKeyRoleId(errors);

        return (startSize == errors.size());
	}

	/**
	 * ���O�C��ID �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validKeyLoginId(List<ValidationFailure> errors) {
		String label = "user.search.keyLoginId";
        ValidationChain valid = new ValidationChain(label,this.keyLoginId);

        // �����`�F�b�N
        valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 16)));

        valid.validate(errors);
	}
	
	/**
	 * ���[�U�� �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validKeyUserName(List<ValidationFailure> errors){
		String label = "user.search.keyUserName";
	    ValidationChain valid = new ValidationChain(label, this.keyUserName);

	    // �����`�F�b�N
	    valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 40)));

	    valid.validate(errors);
	}

	/**
	 * ���[���A�h���X �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validKeyEmail(List<ValidationFailure> errors){
		String label = "user.search.keyEmail";
	    ValidationChain valid = new ValidationChain(label, this.keyEmail);

	    // �����`�F�b�N
	    valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 255)));

	    valid.validate(errors);
	}

	/**
	 * ���� �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validKeyRoleId(List<ValidationFailure> errors){
		String label = "user.search.keyRoleId";
	    ValidationChain valid = new ValidationChain(label, this.keyRoleId);

	    // �����`�F�b�N
	    valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 20)));

	    valid.validate(errors);
	}

	
	
	/**
	 * ���������I�u�W�F�N�g���쐬����B<br/>
	 * PagingListForm �Ɏ�������Ă���A�y�[�W�����p�̌������������������g�����A�󂯎�������N�G�X�g
	 * �p�����[�^�ɂ�錟�������𐶐�����B<br/>
	 * <br/>
	 * @return ���������I�u�W�F�N�g
	 */
	@Override
	public DAOCriteria buildCriteria(){

		// �����p�I�u�W�F�N�g�̐���
		// �y�[�W�������s���ꍇ�APagingListForm�@�C���^�[�t�F�[�X���������� Form ����
		// DAOCriteria �𐶐�����K�v������B ����āA���g�� buildCriteria() ��
		// �g�p���Č��������𐶐����Ă���B
		return buildCriteria(super.buildCriteria());
		
	}

	/**
	 * ���������I�u�W�F�N�g���쐬����B<br/>
	 * �����Ŏ󂯎���������I�u�W�F�N�g�ɑ΂��āA�󂯎�������N�G�X�g�p�����[�^���猟�������𐶐�����
	 * �ݒ肷��B<br/>
	 * <br/>
	 * ���̃��\�b�h���ł́ADB �̕����t�B�[���h�����w�肵�Ă���R�[�h�����݂���B<br/>
	 * ���ׁ̈A�A�Ǘ����[�U�[���̃e�[�u���Ƃ��āAAdminLoginInfo �ȊO���g�p�����ꍇ�A���̃��\�b�h��
	 * �I�[�o�[���C�h����K�v������B<br/>
	 * <br/>
	 * @return ���������I�u�W�F�N�g
	 */
	protected DAOCriteria buildCriteria(DAOCriteria criteria){
	
		// ���O�C��ID�̌�����������
		if (!StringValidateUtil.isEmpty(this.keyLoginId)) {
			criteria.addWhereClause("loginId", this.keyLoginId);
		}

		// ���[�U�[���̌�������������
		if (!StringValidateUtil.isEmpty(this.keyUserName)) {
			criteria.addWhereClause("userName", "%" + this.keyUserName + "%", DAOCriteria.LIKE);
		}

		// ���[���A�h���X�̌�������������
		if (!StringValidateUtil.isEmpty(this.keyEmail)) {
			criteria.addWhereClause("email", "%" + this.keyEmail + "%", DAOCriteria.LIKE);
		}

		// �����̌�������������
		if (!StringValidateUtil.isEmpty(this.keyRoleId)) {
			criteria.addWhereClause("roleId", this.keyRoleId);
		}

        return criteria;
	}

	/**
	 * �Ǘ����[�U�[���̎�L�[�l�ƂȂ錟�������𐶐�����B<br/>
	 * <br/>
	 * ���̃��\�b�h���ł́ADB �̕����t�B�[���h�����w�肵�Ă���R�[�h�����݂���B<br/>
	 * ���ׁ̈A�A�Ǘ����[�U�[���̃e�[�u���Ƃ��āAAdminLoginInfo �ȊO���g�p�����ꍇ�A���̃��\�b�h��
	 * �I�[�o�[���C�h����K�v������B<br/>
	 * <br/>
	 * @return ��L�[�ƂȂ錟�������I�u�W�F�N�g
	 */
	public DAOCriteria buildPkCriteria(){
		// ���������I�u�W�F�N�g���擾
		DAOCriteria criteria = new DAOCriteria();

		// ���[�U�[�Ǘ������Ă���e�[�u���̎�L�[�̌��������𐶐�����B 
		criteria.addWhereClause("adminUserId", this.userId);

		return criteria;
	}
	
	
	
	/**
	 * ������ʂŎg�p���Ă��� command �p�����[�^�̒l���擾����B<br/>
	 * ���̃p�����[�^�l�́A������ʂɕ��A����ہAcommand �p�����[�^�Ƃ��ēn���K�v������B<br/>
	 * <br/>
	 * @return command ����p�p�����[�^
	 */
	public String getSearchCommand() {
		return this.searchCommand;
	}

	/**
	 * ������ʂŎg�p���Ă��� command �p�����[�^�̒l��ݒ肷��B<br/>
	 * ���̃p�����[�^�l�́A������ʂɕ��A����ہAcommand �p�����[�^�Ƃ��ēn���K�v������B<br/>
	 * <br/>
	 * @param searchCommand ����p�p�����[�^
	 */
	public void setSearchCommand(String searchCommand) {
		this.searchCommand = searchCommand;
	}

	/**
	 * �����ΏۂƂȂ郍�O�C��ID (LoginUser �C���^�[�t�F�[�X�� getUserId() �����A�����L�[�̒l)���擾����B<br/>
	 * <br/>
	 * @return �����ΏۂƂȂ郍�O�C��ID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * �����ΏۂƂȂ郍�O�C��ID (LoginUser �C���^�[�t�F�[�X�� getUserId() �����A�����L�[�̒l)��ݒ肷��B<br/>
	 * <br/>
	 * @param userId �����ΏۂƂȂ郍�O�C��ID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * ���O�C���h�c�i���������j���擾����B<br/>
	 * <br/>
	 * @return ���O�C���h�c�i���������j
	 */
	public String getKeyLoginId() {
		return this.keyLoginId;
	}

	/**
	 * ���O�C���h�c�i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @param keyLoginId ���O�C���h�c�i���������j
	 */
	public void setKeyLoginId(String keyLoginId) {
		this.keyLoginId = keyLoginId;
	}

	/**
	 * ���[�U�[���i���������j���擾����B<br/>
	 * <br/>
	 * @return ���[�U�[���i���������j
	 */
	public String getKeyUserName() {
		return this.keyUserName;
	}

	/**
	 * ���[�U�[���i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @param keyUserName ���[�U�[���i���������j
	 */
	public void setKeyUserName(String keyUserName) {
		this.keyUserName = keyUserName;
	}
	
	/**
	 * ���[���A�h���X�i���������j���擾����B<br/>
	 * <br/>
	 * @return ���[���A�h���X�i���������j
	 */
	public String getKeyEmail() {
		return this.keyEmail;
	}

	/**
	 * ���[���A�h���X�i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @param keyEmail ���[���A�h���X�i���������j
	 */
	public void setKeyEmail(String keyEmail) {
		this.keyEmail = keyEmail;
	}

	/**
	 * ���[���h�c�i���������j���擾����B<br/>
	 * <br/>
	 * @return ���[��ID
	 */
	public String getKeyRoleId() {
		return this.keyRoleId;
	}

	/**
	 * ���[��ID�i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @param keyRoleId ���[��ID
	 */
	public void setKeyRoleId(String keyRoleId) {
		this.keyRoleId = keyRoleId;
	}
}