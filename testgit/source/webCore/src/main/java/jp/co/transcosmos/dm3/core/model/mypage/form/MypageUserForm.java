package jp.co.transcosmos.dm3.core.model.mypage.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.validation.CompareValidation;
import jp.co.transcosmos.dm3.core.validation.PasswordValidation;
import jp.co.transcosmos.dm3.core.validation.ZenkakuKanaValidator;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.EncodingUtils;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.AlphanumericMarkOnlyValidation;
import jp.co.transcosmos.dm3.validation.EmailRFCValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.MinLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;
import jp.co.transcosmos.dm3.validation.ZenkakuOnlyValidation;


/**
 * �}�C�y�[�W��������e�i���X�̓��̓p�����[�^����p�t�H�[��.
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.05	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���� Form �̃o���f�[�V�����́A�g�p���郂�[�h�i�ǉ����� or �X�V�����j�ňقȂ�̂ŁA
 * �t���[�����[�N���񋟂��� Validateable �C���^�[�t�F�[�X�͎������Ă��Ȃ��B<br/>
 * �o���f�[�V�������s���̃p�����[�^���ʏ�ƈقȂ�̂Œ��ӂ��鎖�B<br/>
 * 
 */
public class MypageUserForm {

	// command ���N�G�X�g�p�����[�^�́AURL �}�b�s���O�Œ�`����Ă���AnamedCommands �̐���ɂ��g�p�����B
	// �Ǘ����[�U�[�̃����e�i���X�@�\�ł́A�ȉ��̒l���ݒ肳���ꍇ������B
	//
	// list : �Ǘ����[�U�[������ʂŎ��ۂɌ������s���ꍇ�Ɏw�肷��B�@�i���w��̏ꍇ�A������ʂ������\���B�j
	// back : ���͊m�F��ʂ�����͉�ʂ֕��A�����ꍇ�B�@�i�w�肳�ꂽ�ꍇ�A�p�����[�^�̒l�������l�Ƃ��ē��͉�ʂɕ\������B�j

	/** command �p�����[�^ */
	private String command;
	
	// note
	// userId �� Form �ł͎����Ȃ����B
	// ����́A�t�����g���̏ꍇ����g�p�����ꍇ�A���N�G�X�g�p�����[�^�Ƃ��� userId ���������郊�X�N���������ׂ̔z���B
	
	/** ���[���A�h���X (LoginUser �C���^�[�t�F�[�X�� getLoginId() �ɑΉ�) */
	private String email;
	/** �����(���j */
	private String memberLname;
	/** �����(���j */
	private String memberFname;
	/** ������E�J�i(���j */
	private String memberLnameKana;
	/** ������E�J�i(���j */
	private String memberFnameKana;
	/** �p�X���[�h  */
	private String password;
	/** �p�X���[�h�m�F  */
	private String passwordChk;

	/** ���ʃp�����[�^�I�u�W�F�N�g */
	protected CommonParameters commonParameters;
	/** �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B */
	protected LengthValidationUtils lengthUtils;
	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;


	
	/**
	 * �f�t�H���g�R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 * 
	 */
	protected MypageUserForm(){
		super();
	}

	
	
	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
	 * @param commonParameters ���ʃp�����[�^�I�u�W�F�N�g
	 * @param codeLookupManager ���ʃR�[�h�ϊ�����
	 * 
	 */
	protected MypageUserForm(LengthValidationUtils lengthUtils,
							 CommonParameters commonParameters,
							 CodeLookupManager codeLookupManager){
		super();
		this.lengthUtils = lengthUtils;
		this.commonParameters = commonParameters;
		this.codeLookupManager = codeLookupManager;
	}

	
	
	/**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� Form �֏����l��ݒ肷��B<br/>
	 * <br/>
	 * @param mypageUser MypageUserInterface�@�����������A�}�C�y�[�W������p�o���[�I�u�W�F�N�g
	 */
	public void setDefaultData(MypageUserInterface mypageUser){

		// �o���[�I�u�W�F�N�g�Ɋi�[����Ă���p�X���[�h�̓n�b�V���l�Ȃ̂� Form �ɂ͐ݒ肵�Ȃ��B
		// �p�X���[�h�̓��͂͐V�K�o�^���̂ݕK�{�ŁA�X�V�������͔C�ӂ̓��͂ƂȂ�B
		// �X�V�����Ńp�X���[�h�̓��͂��s���Ȃ��ꍇ�A�p�X���[�h�̍X�V�͍s��Ȃ��B

		// ���[���A�h���X
		this.email = mypageUser.getEmail();
		// �����(���j
		this.memberLname = mypageUser.getMemberLname();
		// �����(���j
		this.memberFname = mypageUser.getMemberFname();
		// ������E�J�i(���j
		this.memberLnameKana = mypageUser.getMemberLnameKana();
		// ������E�J�i(���j
		this.memberFnameKana = mypageUser.getMemberFnameKana();

	}


	
	/**
	 * �����œn���ꂽ�}�C�y�[�W����̃o���[�I�u�W�F�N�g�Ƀt�H�[���̒l��ݒ肷��B<br/>
	 * �X�V�����ł��g�p���鎖���l�����A�^�C���X�^���v���Ɋւ��ẮA�X�V���A�X�V�҂̂ݐݒ肷��B<br/>
	 * <br/>
	 * @return MypageUserInterface �����������}�C�y�[�W����I�u�W�F�N�g
	 */
	public void copyToMemberInfo(MypageUserInterface mypageUser){

		// ������i���j ��ݒ�
		mypageUser.setMemberLname(this.memberLname);
		// ������i���j
		mypageUser.setMemberFname(this.memberFname);
		// ������E�J�i�i���j
		mypageUser.setMemberLnameKana(this.memberLnameKana);
		// ������E�J�i�i���j
		mypageUser.setMemberFnameKana(this.memberFnameKana);
		// ���[���A�h���X
		mypageUser.setEmail(this.email);

		// �p�X���[�h ��ݒ�
		// �X�V�����̏ꍇ�A�p�X���[�h�͕ύX����ꍇ�݂̂̓��͂ƂȂ�B�@����āA�p�X���[�h�̏ꍇ�͓��͂��������ꍇ�̂�
		// �l��ݒ肷��B
		// �i�p�X���[�h�̓��͂������ꍇ�́A�����ɐݒ肳��Ă���l�����̂܂܎g�p����ƌ������B�j
		if (!StringValidateUtil.isEmpty(this.password)){
			mypageUser.setPassword(EncodingUtils.md5Encode(this.password));
		}
	}


	
	/**
	 * �}�C�y�[�W������̎�L�[�l�ƂȂ錟�������𐶐�����B<br/>
	 * <br/>
	 * ���̃��\�b�h���ł́ADB �̕����t�B�[���h�����w�肵�Ă���R�[�h�����݂���B<br/>
	 * ���ׁ̈A�A�}�C�y�[�W������̃e�[�u���Ƃ��āAMemberInfo �ȊO���g�p�����ꍇ�A���̃��\�b�h��
	 * �I�[�o�[���C�h����K�v������B<br/>
	 * <br/>
	 * @param userId �X�V�ΏۂƂȂ��L�[�l
	 * 
	 * @return ��L�[�ƂȂ錟�������I�u�W�F�N�g
	 */
	public DAOCriteria buildPkCriteria(String userId){
		// ���������I�u�W�F�N�g���擾
		DAOCriteria criteria = new DAOCriteria();

		// ���[�U�[�Ǘ������Ă���e�[�u���̎�L�[�̌��������𐶐�����B 
		criteria.addWhereClause("userId", userId);

		return criteria;
	}


	
	/**
	 * �w�肵�����[���A�h���X�i���O�C��ID�j�����݂��邩���`�F�b�N����ׂ̌��������𐶐�����B<br/>
	 * <br/>
	 * �E�����Ń��[�U�[ID �����w��̎��́A�V�K�o�^����z�肵�A���[���A�h���X�����݂��邩��������������𐶐�����B<br/>
	 * �E�����Ń��[�U�[ID ���w�莞���ꂽ���́A�ύX����z�肵�A�����ȊO�Ń��[���A�h���X�����݂��邩��������������𐶐�����B<br/>
	 * <br/>
	 * @return ���[���A�h���X�i���O�C��ID�j�̑��݂��`�F�b�N���錟�������I�u�W�F�N�g
	 */
	public DAOCriteria buildFreeLoginIdCriteria(String userId){
		// ���������I�u�W�F�N�g���擾
		DAOCriteria criteria = new DAOCriteria();

		criteria.addWhereClause("email", this.email);
		
		// ���[�U�[ID ���w�肳��Ă���ꍇ�A�����ȊO�Ŏg�p����Ă��邩���`�F�b�N����B
		if (!StringValidateUtil.isEmpty(userId)){
			criteria.addWhereClause("userId", userId, DAOCriteria.EQUALS, true);
		}

		return criteria;
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

        // ���[���A�h���X���̓`�F�b�N
        validEmail(errors);
        // �p�X���[�h�̓��̓`�F�b�N
        validPassword(errors, mode);
        // �p�X���[�h�m�F�̓��̓`�F�b�N
        validRePassword(errors);
        // ������i���j���̓`�F�b�N
        validMemberLname(errors);
        // ������i���j���̓`�F�b�N
        validMemberFname(errors);
        // ������E�J�i�i���j���̓`�F�b�N
        validMemberLnameKana(errors);
        // ������E�J�i�i���j���̓`�F�b�N
        validMemberFnameKana(errors);

        return (startSize == errors.size());

	}

	/**
	 * ���[���A�h���X �o���f�[�V����<br/>
	 * <ul>
	 * <li>�K�{�`�F�b�N</li>
	 * <li>�����`�F�b�N</li>
	 * <li>���[���A�h���X�̏����`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validEmail(List<ValidationFailure> errors) {
		String label = "mypage.input.email";
        ValidationChain valUpdateMail = new ValidationChain(label, this.email);

        // �K�{�`�F�b�N
        valUpdateMail.addValidation(new NullOrEmptyCheckValidation());
        // �����`�F�b�N
        valUpdateMail.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 255)));
        // ���[���A�h���X�̏����`�F�b�N
        valUpdateMail.addValidation(new EmailRFCValidation());

        valUpdateMail.validate(errors);
	}

	/**
	 * �p�X���[�h �o���f�[�V����<br/>
	 * <ul>
	 * <li>�K�{�`�F�b�N</li>
	 * <li>�ŏ������`�F�b�N</li>
	 * <li>�ő包���`�F�b�N</li>
	 * <li>�V�p�X���[�h�̔��p�p���L�������`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @param mode �������[�h ("insert" or "update")
	 */
	protected void validPassword(List<ValidationFailure> errors, String mode) {
		String label = "mypage.input.password";
        ValidationChain valPassword = new ValidationChain(label, this.password);

        if (mode.equals("insert")) {
        	// �V�K�o�^���͕K�{�`�F�b�N���s���B
        	// �X�V�o�^���̃p�X���[�h���͔͂C�ӂƂȂ�A���͂��ꂽ�ꍇ�̂݃p�X���[�h���X�V����B
        	valPassword.addValidation(new NullOrEmptyCheckValidation());						// �K�{�`�F�b�N
        }

        // �p�X���[�h�̓��͂��������ꍇ�A�p�X���[�h�̃o���f�[�V�������s��
        if (!StringValidateUtil.isEmpty(this.password)){

        	// �ŏ������`�F�b�N
        	valPassword.addValidation(new MinLengthValidation(this.commonParameters.getMinMypagePwdLength()));

        	// �ő包���`�F�b�N
        	valPassword.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 16)));

        	// �V�p�X���[�h�̔��p�p���L�������`�F�b�N
        	// ���p�\�ȋL�������́A���ʃp�����[�^�I�u�W�F�N�g����擾����B
        	valPassword.addValidation(new AlphanumericMarkOnlyValidation(this.commonParameters.getMypagePwdUseMarks()));

        	// �p�X���[�h���x�`�F�b�N�̃o���f�[�V�����I�u�W�F�N�g���擾�ł����ꍇ�A�p�X���[�h���x�̃o���f�[�V���������s����B
            Validation pwdValidation = createPwdValidation();
        	if (pwdValidation != null){
        		valPassword.addValidation(pwdValidation);
        	}
        }
        valPassword.validate(errors);
	}

	/**
	 * �p�X���[�h�m�F �o���f�[�V����<br/>
	 * �p�X���[�h�̓��͂��������ꍇ�̂݁A�ȉ��̃o���f�[�V���������{����<br/>
	 * <ul>
	 * <li>�K�{�`�F�b�N</li>
	 * <li>�p�X���[�h�ƁA�m�F���͂̏ƍ��`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validRePassword(List<ValidationFailure> errors) {

		// �p�X���[�h�̓��͂��������ꍇ�A�p�X���[�h�m�F�̃o���f�[�V�������s��
        if (!StringValidateUtil.isEmpty(this.password)){

        	String label = "mypage.input.rePassword";
            ValidationChain valNewRePwd = new ValidationChain(label, this.passwordChk);

            // �K�{�`�F�b�N
            valNewRePwd.addValidation(new NullOrEmptyCheckValidation());

            // �p�X���[�h�ƁA�m�F���͂̏ƍ��`�F�b�N
            String cmplabel = this.codeLookupManager.lookupValue("errorLabels", "mypage.input.password");
            valNewRePwd.addValidation(new CompareValidation(this.password, cmplabel));

            valNewRePwd.validate(errors);
        }
	}

	/**
	 * ������i���j �o���f�[�V����<br/>
	 * <ul>
	 * <li>�K�{�`�F�b�N</li>
	 * <li>�S�p�`�F�b�N</li>
	 * <li>�����`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validMemberLname(List<ValidationFailure> errors) {
		String label = "mypage.input.memberLname";
        ValidationChain valLName = new ValidationChain(label, this.memberLname);

        // �K�{�`�F�b�N
        valLName.addValidation(new NullOrEmptyCheckValidation());
		// �S�p�`�F�b�N
        valLName.addValidation(new ZenkakuOnlyValidation());
        // �����`�F�b�N
        valLName.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 40)));

        valLName.validate(errors);
	}

	/**
	 * ������i���j �o���f�[�V����<br/>
	 * <ul>
	 * <li>�K�{�`�F�b�N</li>
	 * <li>�S�p�`�F�b�N</li>
	 * <li>�����`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validMemberFname(List<ValidationFailure> errors) {
		String label = "mypage.input.memberFname";
        ValidationChain valFName = new ValidationChain(label, this.memberFname);

        // �K�{�`�F�b�N
        valFName.addValidation(new NullOrEmptyCheckValidation());
		// �S�p�`�F�b�N
        valFName.addValidation(new ZenkakuOnlyValidation());
        // �����`�F�b�N
        valFName.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 40)));

        valFName.validate(errors);
	}

	/**
	 * ������E�J�i�i���j�o���f�[�V����<br/>
	 * <ul>
	 * <li>�K�{�`�F�b�N</li>
	 * <li>�S�p�J�^�J�i�`�F�b�N</li>
	 * <li>�����`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validMemberLnameKana(List<ValidationFailure> errors) {
		String label = "mypage.input.memberLnameKana";
        ValidationChain valLNameKana = new ValidationChain(label, this.memberLnameKana);

        // �K�{�`�F�b�N
        valLNameKana.addValidation(new NullOrEmptyCheckValidation());
		// �S�p�J�^�J�i�`�F�b�N
        valLNameKana.addValidation(new ZenkakuKanaValidator());
        // �����`�F�b�N
        valLNameKana.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 40)));

        valLNameKana.validate(errors);
	}

	/**
	 * ������E�J�i�i���j �o���f�[�V����<br/>
	 * <ul>
	 * <li>�K�{�`�F�b�N</li>
	 * <li>�S�p�J�^�J�i�`�F�b�N</li>
	 * <li>�����`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validMemberFnameKana(List<ValidationFailure> errors) {
		String label = "mypage.input.memberFnameKana";
        ValidationChain valFNameKana = new ValidationChain(label, this.memberFnameKana);

        // �K�{�`�F�b�N
        valFNameKana.addValidation(new NullOrEmptyCheckValidation());
		// �S�p�J�^�J�i�`�F�b�N
        valFNameKana.addValidation(new ZenkakuKanaValidator());
        // �����`�F�b�N
        valFNameKana.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 40)));

        valFNameKana.validate(errors);
	}



	/**
	 * �p�X���[�h���x�`�F�b�N�o���f�[�V�����̃C���X�^���X�𐶐�����B<br/>
	 * �p�X���[�h���x�`�F�b�N���s��Ȃ��ꍇ�A���̃��\�b�h�̖߂�l�� null �ɂȂ�l�ɃI�[�o�[���C�h���鎖�B<br/>
	 * <br/>
	 * @return PasswordValidation �̃C���X�^���X
	 */
	protected Validation createPwdValidation(){
		return new PasswordValidation(this.commonParameters.getMypagePwdMastMarks());
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
	 * ���[���A�h���X���擾����B�@���[���A�h���X�̓��O�C��ID �Ƃ��Ďg�p�����B<br/>
	 * <br/>
	 * @return ���[���A�h���X
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * ���[���A�h���X��ݒ肷��B�@���[���A�h���X�̓��O�C��ID �Ƃ��Ďg�p�����B<br/>
	 * <br/>
	 * @param email ���[���A�h���X
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * ������i���j���擾����B<br/>
	 * <br/>
	 * @return ������i���j
	 */
	public String getMemberLname() {
		return memberLname;
	}

	/**
	 * ������i���j��ݒ肷��B<br/>
	 * @param memberLname ������i���j
	 */
	public void setMemberLname(String memberLname) {
		this.memberLname = memberLname;
	}

	/**
	 * �����(���j���擾����B<br/>
	 * <br/>
	 * @return �����(���j
	 */
	public String getMemberFname() {
		return memberFname;
	}

	/**
	 * �����(���j��ݒ肷��B<br/>
	 * <br/>
	 * @param memberFname �����(���j
	 */
	public void setMemberFname(String memberFname) {
		this.memberFname = memberFname;
	}

	/**
	 * ������E�J�i(���j���擾����B<br/>
	 * <br/>
	 * @return ������E�J�i(���j
	 */
	public String getMemberLnameKana() {
		return memberLnameKana;
	}

	/**
	 * ������E�J�i(���j��ݒ肷��B<br/>
	 * <br/>
	 * @param memberLnameKana
	 */
	public void setMemberLnameKana(String memberLnameKana) {
		this.memberLnameKana = memberLnameKana;
	}

	/**
	 * ������E�J�i(���j���擾����B<br/>
	 * <br/>
	 * @return ������E�J�i(���j
	 */
	public String getMemberFnameKana() {
		return memberFnameKana;
	}

	/**
	 * ������E�J�i(���j��ݒ肷��B<br/>
	 * <br/>
	 * @param memberFnameKana������E�J�i(���j
	 */
	public void setMemberFnameKana(String memberFnameKana) {
		this.memberFnameKana = memberFnameKana;
	}

	/**
	 * �p�X���[�h���擾����B<br/>
	 * <br/>
	 * @return �p�X���[�h
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * �p�X���[�h��ݒ肷��B<br/>
	 * <br/>
	 * @param password �p�X���[�h
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
		return passwordChk;
	}

	/**
	 * �p�X���[�h�m�F��ݒ肷��B<br/>
	 * <br/>
	 * @param passwordChk �p�X���[�h�m�F
	 */
	public void setPasswordChk(String passwordChk) {
		this.passwordChk = passwordChk;
	}

}
