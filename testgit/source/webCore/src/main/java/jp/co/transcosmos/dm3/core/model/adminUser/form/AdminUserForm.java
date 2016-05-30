package jp.co.transcosmos.dm3.core.model.adminUser.form;

import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserRoleInterface;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.validation.CompareValidation;
import jp.co.transcosmos.dm3.core.validation.PasswordValidation;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.UpdateExpression;
import jp.co.transcosmos.dm3.dao.UpdateValue;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.EncodingUtils;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.AlphanumericMarkOnlyValidation;
import jp.co.transcosmos.dm3.validation.AsciiOnlyValidation;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.EmailRFCValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.MinLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;
import jp.co.transcosmos.dm3.validation.ZenkakuOnlyValidation;

/**
 * �Ǘ����[�U�[�����e�i���X�̓��̓p�����[�^����p�t�H�[��.
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.28	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���� Form �̃o���f�[�V�����́A�g�p���郂�[�h�i�ǉ����� or �X�V�����j�ňقȂ�̂ŁA
 * �t���[�����[�N���񋟂��� Validateable �C���^�[�t�F�[�X�͎������Ă��Ȃ��B<br/>
 * �o���f�[�V�������s���̃p�����[�^���ʏ�ƈقȂ�̂Œ��ӂ��鎖�B<br/>
 *
 */
public class AdminUserForm {

	// command ���N�G�X�g�p�����[�^�́AURL �}�b�s���O�Œ�`����Ă���AnamedCommands �̐���ɂ��g�p�����B
	// �Ǘ����[�U�[�̃����e�i���X�@�\�ł́A�ȉ��̒l���ݒ肳���ꍇ������B
	//
	// list : �Ǘ����[�U�[������ʂŎ��ۂɌ������s���ꍇ�Ɏw�肷��B�@�i���w��̏ꍇ�A������ʂ������\���B�j
	// back : ���͊m�F��ʂ�����͉�ʂ֕��A�����ꍇ�B�@�i�w�肳�ꂽ�ꍇ�A�p�����[�^�̒l�������l�Ƃ��ē��͉�ʂɕ\������B�j

	/** command �p�����[�^ */
	private String command;

	/** ���[��ID �iAdminUserRoleInterface�@�C���^�[�t�F�[�X�� getRoleId() �ɑΉ��j */
	private String roleId;

	/** �����Ώۃ��[�U�[ID (LoginUser �C���^�[�t�F�[�X�� getUserId() �ɑΉ�) */
	private String userId;

	/** ���O�C��ID (LoginUser �C���^�[�t�F�[�X�� getLoginId() �ɑΉ�) */
	private String loginId;
	/** �p�X���[�h  �iAdminUserRoleInterface�@�C���^�[�t�F�[�X�� getPassword() �ɑΉ��j  */
	private String password;
	/** �p�X���[�h�m�F  */
	private String passwordChk;
	/** ���[�U�[�� �iAdminUserInterface �C���^�[�t�F�[�X�� getUserName() �ɑΉ��j */
	private String userName;
	/** ���[���A�h���X �iAdminUserInterface �C���^�[�t�F�[�X�� getEmail() �ɑΉ��j */
	private String email;
	/** ���l �iAdminUserInterface �C���^�[�t�F�[�X�� getNote() �ɑΉ��j */
	private String note;

	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;
	/** ���ʃp�����[�^�I�u�W�F�N�g */
	protected CommonParameters commonParameters;
	/** �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B */
	protected LengthValidationUtils lengthUtils;



	/**
	 * �f�t�H���g�R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	protected AdminUserForm(){
		super();
	}



	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
	 * @param�@codeLookupManager�@���ʃR�[�h�ϊ�����
	 * @param commonParameters ���ʃp�����[�^�I�u�W�F�N�g
	 */
	protected AdminUserForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager, CommonParameters commonParameters){
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
		this.commonParameters = commonParameters;
	}



	/**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� Form �֏����l��ݒ肷��B<br/>
	 * <br/>
	 * @param adminUser AdminUserInterface�@�����������A���[�U�[���Ǘ��p�o���[�I�u�W�F�N�g
	 * @param adminRole AdminUserRoleInterface �������������[�U�[���[���Ǘ��p�o���[�I�u�W�F�N�g
	 */
	public void setDefaultData(AdminUserInterface adminUser, AdminUserRoleInterface adminRole){

		// �o���[�I�u�W�F�N�g�Ɋi�[����Ă���p�X���[�h�̓n�b�V���l�Ȃ̂� Form �ɂ͐ݒ肵�Ȃ��B
		// �p�X���[�h�̓��͂͐V�K�o�^���̂ݕK�{�ŁA�X�V�������͔C�ӂ̓��͂ƂȂ�B
		// �X�V�����Ńp�X���[�h�̓��͂��s���Ȃ��ꍇ�A�p�X���[�h�̍X�V�͍s��Ȃ��B

		// ���[�U�[ID ��ݒ�
		this.userId = (String) adminUser.getUserId();
		// ���O�C��ID ��ݒ�
		this.loginId = adminUser.getLoginId();
		// ���[�U�[���̂�ݒ�
		this.userName = adminUser.getUserName();
		// ���[���A�h���X��ݒ�
		this.email = adminUser.getEmail();
		// ���l
		this.note = adminUser.getNote();
		// �����i���[��ID�j
		this.roleId = adminRole.getRoleId();

	}



	/**
	 * �����œn���ꂽ�Ǘ����[�U�[�̃o���[�I�u�W�F�N�g�Ƀt�H�[���̒l��ݒ肷��B<br/>
	 * �X�V�����ł��g�p���鎖���l�����A�^�C���X�^���v���Ɋւ��ẮA�X�V���A�X�V�҂̂ݐݒ肷��B<br/>
	 * <br/>
	 * @return AdminUserInterface �����������Ǘ����[�U�[�I�u�W�F�N�g
	 */
	public void copyToAdminUserInfo(AdminUserInterface adminUser){

		// �V�K�o�^���� DAO ���Ŏ����̔Ԃ���̂ŁA���[�U�[ID �͐ݒ肵�Ȃ��B
		
		// ���O�C��ID ��ݒ�
		adminUser.setLoginId(this.loginId);

		// �p�X���[�h ��ݒ�
		// �X�V�����̏ꍇ�A�p�X���[�h�͕ύX����ꍇ�݂̂̓��͂ƂȂ�B�@����āA�p�X���[�h�̏ꍇ�͓��͂��������ꍇ�̂�
		// �l��ݒ肷��B
		// �i�p�X���[�h�̓��͂������ꍇ�́A�����ɐݒ肳��Ă���l�����̂܂܎g�p����ƌ������B�j
		if (!StringValidateUtil.isEmpty(this.password)){
			adminUser.setPassword(EncodingUtils.md5Encode(this.password));

			// �p�X���[�h�L��������ݒ�
			adminUser.setLastPasswdChange(new Date());
		}

		// ���[�U�[����ݒ�
		adminUser.setUserName(this.userName);

		// ���[���A�h���X��ݒ�
		adminUser.setEmail(this.email);

		// ���l��ݒ�
		adminUser.setNote(this.note);

	}



	/**
	 * �����œn���ꂽ�Ǘ��҃��[��ID �̔z��ɁA�t�H�[���̏���ݒ肷��B<br/>
	 * <br/>
	 * @return ���[�����o���[�I�u�W�F�N�g
	 */
	public void copyToAdminRoleInfo(AdminUserRoleInterface adminRole){

		adminRole.setUserId(this.userId);
		adminRole.setRoleId(this.roleId);

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
	 * �w�肵�����O�C��ID �����݂��邩���`�F�b�N����ׂ̌��������𐶐�����B<br/>
	 * <br/>
	 * �EForm ���Ń��[�U�[ID ���w�莞�́A�V�K�o�^����z�肵�A���O�C��ID �����݂��邩��������������𐶐�����B<br/>
	 * �EForm ���Ń��[�U�[ID �w�莞�́A�ύX����z�肵�A�����ȊO�Ń��O�C��ID �����݂��邩��������������𐶐�����B<br/>
	 * <br/>
	 * @return ���O�C��ID �̑��݂��`�F�b�N���錟�������I�u�W�F�N�g
	 */
	public DAOCriteria buildFreeLoginIdCriteria(){
		// ���������I�u�W�F�N�g���擾
		DAOCriteria criteria = new DAOCriteria();

		criteria.addWhereClause("loginId", this.loginId);
		
		// ���[�U�[ID ���w�肳��Ă���ꍇ�A�����ȊO�Ŏg�p����Ă��邩���`�F�b�N����B
		if (!StringValidateUtil.isEmpty(this.userId)){
			criteria.addWhereClause("adminUserId", this.userId, DAOCriteria.EQUALS, true);
		}

		return criteria;
	}



	/**
	 * �Ǘ����[�U�[���̍X�V�^�C���X�^���v�p UpdateExpression �𐶐�����B<br/>
	 * <br/>
	 * �Ǘ����[�U�[���̃e�[�u���Ƃ��āAAdminLoginInfo �ȊO���g�p�����ꍇ�A���̃��\�b�h��
	 * �I�[�o�[���C�h����K�v������B<br/>
	 * <br/>
	 * @return �X�V�^�C���X�^���v UPDATE �p�@UpdateExpression
	 */
	public UpdateExpression[] buildTimestampUpdateExpression(String editUserId){

        // �V�X�e�����t
        Date sysDate = new Date();

        // ���̃��\�b�h�̏ꍇ�A�o���[�I�u�W�F�N�g���󂯎���Ă��Ȃ��̂ŁAAdminLoginInfo ���g�p�����
        // ����̂����f���ł��Ȃ��B�@�ʃe�[�u�����g�p����ꍇ�͕K���I�[�o�[���C�h���鎖�B
        return new UpdateExpression[] {new UpdateValue("updUserId", editUserId),
        							   new UpdateValue("updDate", sysDate)};
	}



	/**
	 * �o���f�[�V��������<br/>
	 * ���N�G�X�g�p�����[�^�̃o���f�[�V�������s��<br/>
	 * <br/>
	 * ���� Form �N���X�̃o���f�[�V�������\�b�h�́AValidateable �C���^�[�t�F�[�X�̎����ł͖����̂ŁA
	 * �o���f�[�V�������s���̈������قȂ�̂ŁA���� Form ���܂Ƃ߂ăo���f�[�V��������ꍇ�Ȃǂ͒���
	 * ���鎖�B<br/>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @param mode �������[�h ("insert" or "update")
	 * @return ���펞 true�A�G���[�� false
	 */
	public boolean validate(List<ValidationFailure> errors, String mode) {

		int startSize = errors.size();

		// �����`�F�b�N
		validRoleId(errors);
		// ���O�C���h�c���̓`�F�b�N
		validLoginId(errors);
		// �p�X���[�h�̓��̓`�F�b�N
		validPassword(errors, mode);
		// �p�X���[�h�m�F�`�F�b�N
		validComparePassword(errors);
		// ���[�U�����̓`�F�b�N
		validUserName(errors);
		// ���[���A�h���X���̓`�F�b�N
		validEmail(errors);
		// ���l�`�F�b�N
		validNote(errors);

		return (startSize == errors.size());

	}

	/**
	 * ���� �o���f�[�V����<br/>
	 * <ul>
	 *   <li>�K�{�`�F�b�N</li>
	 *   <li>�p�^�[���`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validRoleId(List<ValidationFailure> errors){
		String label = "user.input.roleId";
		ValidationChain valid = new ValidationChain(label, this.roleId);

		// �K�{�`�F�b�N
		valid.addValidation(new NullOrEmptyCheckValidation());
		// �p�^�[���`�F�b�N
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "roleId"));

		valid.validate(errors);
	}

	/**
	 * ���O�C���h�c �o���f�[�V����<br/>
	 * <ul>
	 *   <li>�K�{�`�F�b�N</li>
	 *   <li>�ŏ������`�F�b�N</li>
	 *   <li>�ő包���`�F�b�N</li>
	 *   <li>���p�p���L���`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validLoginId(List<ValidationFailure> errors){
		String label = "user.input.loginId";
		ValidationChain valid = new ValidationChain(label, this.loginId);

		// �K�{�`�F�b�N
		valid.addValidation(new NullOrEmptyCheckValidation());
		// �ŏ������`�F�b�N
		valid.addValidation(new MinLengthValidation(this.commonParameters.getMinAdminPwdLength()));
		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 16)));
		// ���p�p���L���`�F�b�N
		valid.addValidation(new AsciiOnlyValidation());
		
		valid.validate(errors);
	}

	/**
	 * �p�X���[�h �o���f�[�V����<br/>
	 * <ul>
	 *   <li>�K�{�`�F�b�N �i�V�K�o�^���j</li>
	 *   <li>�ŏ������`�F�b�N</li>
	 *   <li>�ő包���`�F�b�N</li>
	 *   <li>���p�p���L���`�F�b�N</li>
	 *   <li>�p�X���[�h���x�`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @param mode �������[�h ("insert" or "update")
	 */
	protected void validPassword(List<ValidationFailure> errors,String mode){
		String label = "user.input.password";
		ValidationChain valid = new ValidationChain(label, this.password);

		if (mode.equals("insert")) {
			// �V�K�o�^���͕K�{�`�F�b�N���s���B
			// �X�V�o�^���̃p�X���[�h���͔͂C�ӂƂȂ�A���͂��ꂽ�ꍇ�̂݃p�X���[�h���X�V����B

			// �K�{�`�F�b�N
			valid.addValidation(new NullOrEmptyCheckValidation());
		}

		// �p�X���[�h�̓��͂��������ꍇ�A�p�X���[�h�̃o���f�[�V�������s��
		if (!StringValidateUtil.isEmpty(this.password)){

			// �ŏ������`�F�b�N
			valid.addValidation(new MinLengthValidation(this.commonParameters.getMinAdminPwdLength()));

			// �ő包���`�F�b�N
			valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 16)));

			// �V�p�X���[�h�̔��p�p���L�������`�F�b�N
			// ���p�\�ȋL�������́A���ʃp�����[�^�I�u�W�F�N�g����擾����B
			valid.addValidation(new AlphanumericMarkOnlyValidation(this.commonParameters.getAdminPwdUseMarks()));

			// �p�X���[�h���x�`�F�b�N�̃o���f�[�V�����I�u�W�F�N�g���擾�ł����ꍇ�A�p�X���[�h���x�̃o���f�[�V���������s����B
			Validation pwdValidation = createPwdValidation();
			if (pwdValidation != null){
				valid.addValidation(pwdValidation);
			}
		}
		valid.validate(errors);
	}

	/**
	 * �p�X���[�h�m�F �o���f�[�V����<br/>
	 * <ul>
	 *   <li>�K�{�`�F�b�N</li>
	 *   <li>�p�X���[�h�ƁA�m�F���͂̏ƍ��`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validComparePassword(List<ValidationFailure> errors){
		// �p�X���[�h�̓��͂��������ꍇ�A�p�X���[�h�m�F�̃o���f�[�V�������s��
		if (!StringValidateUtil.isEmpty(this.password)){

			String label = "user.input.rePassword";
			ValidationChain valid = new ValidationChain(label, this.passwordChk);
			// �K�{�`�F�b�N
			valid.addValidation(new NullOrEmptyCheckValidation());

			// �p�X���[�h�ƁA�m�F���͂̏ƍ��`�F�b�N
	        // ��r�Ώۃt�B�[���h�����擾
	        String cmplabel = this.codeLookupManager.lookupValue("errorLabels", "user.input.password");
			valid.addValidation(new CompareValidation(this.password, cmplabel));

			valid.validate(errors);
		}
	}

	/**
	 * ���[�U�� �o���f�[�V����<br/>
	 * <ul>
	 *   <li>�K�{�`�F�b�N</li>
	 *   <li>�S�p�`�F�b�N</li>
	 *   <li>�����`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validUserName(List<ValidationFailure> errors){
		String label = "user.input.adminUserName";
		ValidationChain valid = new ValidationChain(label, this.userName);

		// �K�{�`�F�b�N
		valid.addValidation(new NullOrEmptyCheckValidation());
		// �S�p�`�F�b�N
		valid.addValidation(new ZenkakuOnlyValidation());
		// �����`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 40)));

		valid.validate(errors);
	}

	/**
	 * ���[���A�h���X �o���f�[�V����<br/>
	 * <ul>
	 *   <li>�����`�F�b�N</li>
	 *   <li>���[���A�h���X�̏����`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validEmail(List<ValidationFailure> errors){
		String label = "user.input.email";
		ValidationChain valid = new ValidationChain(label, this.email);

		// �K�{�`�F�b�N
		valid.addValidation(new NullOrEmptyCheckValidation());
		// �����`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 255)));
		// ���[���A�h���X�̏����`�F�b�N
		valid.addValidation(new EmailRFCValidation());

		valid.validate(errors);
	}

	/**
	 * ���l �o���f�[�V����<br/>
	 * <ul>
	 *   <li>�����`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validNote(List<ValidationFailure> errors){
		String label = "user.input.note";
		ValidationChain valid = new ValidationChain(label, this.note);

		// �����`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 200)));

		valid.validate(errors);
	}

	

	/**
	 * �p�X���[�h���x�`�F�b�N�o���f�[�V�����̃C���X�^���X�𐶐�����B<br/>
	 * �p�X���[�h���x�`�F�b�N���s��Ȃ��ꍇ�A���̃��\�b�h�̖߂�l�� null �ɂȂ�l�ɃI�[�o�[���C�h���鎖�B<br/>
	 * <br/>
	 * @return�@PasswordValidation�@�̃C���X�^���X
	 */
	protected Validation createPwdValidation(){
		return new PasswordValidation(this.commonParameters.getAdminPwdMastMarks());
	}

	

	/**
	 * command �p�����[�^���擾����B<br/>
	 * command �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� command �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
	 * <br/>
	 * @return command �p�����[�^
	 */
	public String getCommand() {
		return this.command;
	}

	/**
	 * command �p�����[�^��ݒ肷��B<br/>
	 * command �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� command �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
	 * <br/>
	 * @param command command �p�����[�^
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * ���[��ID ���擾����B<br/>
	 * <br/>
	 * @return ���[��ID
	 */
	public String getRoleId() {
		return this.roleId;
	}

	/**
	 * ���[��ID ��ݒ肷��B<br/>
	 * <br/>
	 * @param roleId ���[��ID
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * �����ΏۂƂȂ郆�[�U�[ID�i��L�[�l�j���擾����B<br/>
	 * <br/>
	 * @return �����ΏۂƂȂ郆�[�U�[ID
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * �����ΏۂƂȂ郆�[�U�[ID�i��L�[�l�j��ݒ肷��B<br/>
	 * <br/>
	 * @param userId �����ΏۂƂȂ郆�[�U�[ID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * ���O�C��ID���擾����B<br/>
	 * <br/>
	 * @return ���O�C��ID
	 */
	public String getLoginId() {
		return this.loginId;
	}

	/**
	 * ���O�C��ID��ݒ肷��B<br/>
	 * <br/>
	 * @param loginId ���O�C��ID
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	/**
	 * �p�X���[�h���擾����B<br/>
	 * <br/>
	 * @return �p�X���[�h�i�n�b�V�������O�̓��͒l�j
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * �p�X���[�h��ݒ肷��B<br/>
	 * <br/>
	 * @param password �p�X���[�h�i�n�b�V�������O�̓��͒l�j
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * �p�X���[�h�m�F���擾����B<br/>
	 * <br/>
	 * @return �p�X���[�h�m�F
	 */
	public String getPasswordChk() {
		return this.passwordChk;
	}

	/**
	 * �p�X���[�h�m�F��ݒ肷��B<br/>
	 * <br/>
	 * @param passwordChk �p�X���[�h�m�F
	 */
	public void setPasswordChk(String passwordChk) {
		this.passwordChk = passwordChk;
	}

	/**
	 * ���[�U�[�����擾����B<br/>
	 * <br/>
	 * @return ���[�U�[��
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * ���[�U�[����ݒ肷��B<br/>
	 * <br/>
	 * @param adminUserName ���[�U�[��
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * ���[���A�h���X���擾����B<br/>
	 * <br/>
	 * @return ���[���A�h���X
	 */
	public String getEmail() {
		return this.email;
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
	 * ���l���擾����B<br/>
	 * <br/>
	 * @return ���l
	 */
	public String getNote() {
		return this.note;
	}

	/**
	 * ���l��ݒ肷��B<br/>
	 * <br/>
	 * @param note ���l
	 */
	public void setNote(String note) {
		this.note = note;
	}
}
