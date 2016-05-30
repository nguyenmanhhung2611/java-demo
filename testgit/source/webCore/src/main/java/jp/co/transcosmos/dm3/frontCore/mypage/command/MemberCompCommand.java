package jp.co.transcosmos.dm3.frontCore.mypage.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.adminCore.mypage.command.MypageCompCommand;
import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.model.exception.DuplicateException;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserForm;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserFormFactory;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.MemberInfo;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.mail.ReplacingMail;
import jp.co.transcosmos.dm3.utils.EncodingUtils;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

/**
 * �}�C�y�[�W����̒ǉ��A�ύX�A����уA�J�E���g���b�N�̉�������.
 * <p>
 * �y�V�K�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B</li>
 * <li>�o���f�[�V����������I�������ꍇ�A���[�U�[�h�c���A�}�C�y�[�W�������V�K�o�^����B</li>
 * <li>�����A�V�K�o�^���Ƀ��[���A�h���X�ň�Ӑ���G���[�����������ꍇ�̓o���f�[�V�����G���[�Ƃ��Ĉ����B</li>
 * <li>�o�^������A�o�^�������[���A�h���X���Ƀp�X���[�h�̒ʒm���[���𑗐M����B</li>
 * </ul>
 * <br/>
 * �y�X�V�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B</li>
 * <li>�o���f�[�V����������I�������ꍇ�A�}�C�y�[�W��������X�V����B</li>
 * <li>�����A�X�V���Ƀ��[���A�h���X�i���O�C��ID�j �ň�Ӑ���G���[�����������ꍇ�̓o���f�[�V�����G���[�Ƃ��Ĉ����B</li>
 * <li>�����A�X�V�Ώۃf�[�^�����݂��Ȃ��ꍇ�A�X�V�������p���ł��Ȃ��̂ŊY��������ʂ�\������B</li>
 * <li>�o�^������A�p�X���[�h�̕ύX���������ꍇ�A�o�^�������[���A�h���X���Ƀp�X���[�h�̒ʒm���[���𑗐M����B</li>
 * </ul>
 * <br/>
 * �y���A���� View ���z<br/>
 * <ul>
 * <li>success</li>:����I���i���_�C���N�g�y�[�W�j
 * <li>input</li>:�o���f�[�V�����G���[�ɂ��ē���
 * </ul>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * Y.Cho		2015.04.16	�V�K�쐬
 * H.Mizuno		2015.07.07	�X�V�������̎�L�[�l�擾����ɖ�肪�������̂Ń`�F�b�N���@��ύX�B
 * 							�Z�b�V�����̃��[�U�[�����X�V���鏈����ǉ�
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class MemberCompCommand implements Command {

	private static final Log log = LogFactory.getLog(MypageCompCommand.class);

	/** �}�C�y�[�W��������e�i���X���s�� Model �I�u�W�F�N�g */
	protected MypageUserManage userManager;

	/** �������[�h (insert=�V�K�o�^�����A update=�X�V����)*/
	protected String mode;

	/** ��L�[�ƂȂ� UserId �̕K�{�`�F�b�N���s���ꍇ�Atrue ��ݒ肷��B �i�f�t�H���g true�j�@*/
	protected boolean useUserIdValidation = true;

	/** Form �̃o���f�[�V���������s����ꍇ�Atrue ��ݒ肷��B�@�i�f�t�H���g true�j */
	protected boolean useValidation = true;

	/** �p�X���[�h�ʒm���[���e���v���[�g */
	protected ReplacingMail sendMypagePasswordTemplate;



	/**
	 * �}�C�y�[�W��������e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param userManager �}�C�y�[�W��������e�i���X�� model �I�u�W�F�N�g
	 */
	public void setUserManager(MypageUserManage userManager) {
		this.userManager = userManager;
	}

	/**
	 * �������[�h��ݒ肷��<br/>
	 * <br/>
	 * @param mode "insert" = �V�K�o�^�����A"update" = �X�V����
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * ��L�[�ƂȂ� UserId �̕K�{�`�F�b�N�����s����ꍇ�Atrue ��ݒ肷��B�@�i�f�t�H���g true�j<br/>
	 * <br/>
	 * @param useUserIdValidation true �̏ꍇ�AUserId �̕K�{�`�F�b�N�����s
	 */
	public void setUseUserIdValidation(boolean useUserIdValidation) {
		this.useUserIdValidation = useUserIdValidation;
	}

	/**
	 * Form �̃o���f�[�V���������s����ꍇ�Atrue ��ݒ肷��B�@�i�f�t�H���g true�j<br/>
	 * <br/>
	 * @param useValidation true �̏ꍇ�AForm �̃o���f�[�V���������s
	 */
	public void setUseValidation(boolean useValidation) {
		this.useValidation = useValidation;
	}

	/**
	 * �p�X���[�h�ʒm���[���e���v���[�g��ݒ肷��B<br/>
	 * ���ݒ�̏ꍇ�A���[�����M���s��Ȃ��B<br/>
	 * <br/>
	 * @param sendMypagePasswordTemplate �p�X���[�h�ʒm���[���e���v���[�g
	 */
	public void setSendMypagePasswordTemplate(ReplacingMail sendMypagePasswordTemplate) {
		this.sendMypagePasswordTemplate = sendMypagePasswordTemplate;
	}



	/**
	 * �Ǘ����[�U�̒ǉ��E�ύX����<br>
	 * DI �R���e�i����ݒ肳�ꂽ�v���p�e�B�l�imode�AuseUserIdValidation�AuseValidation�j
	 * �̐ݒ�ɏ]���Ǘ����[�U�[����ǉ��E�ύX�E�폜����B<br/>
	 * <br>
	 * @param request �N���C�A���g�����Http���N�G�X�g�B
	 * @param response �N���C�A���g�ɕԂ�Http���X�|���X�B
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
				throws Exception {

		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
		Map<String, Object> model = createModel(request);
		MypageUserForm inputForm = (MypageUserForm) model.get("inputForm");


		// ���O�C�����[�U�[�̏����擾����B
		// �A�����ĉ���o�^���s���ꍇ�A�}�C�y�[�W�Ƀ��O�C���ςȂ̂ŁAgetAnonLoginUserInfo() �́A
		// �������[�U�[�ł͂Ȃ��A���݃��O�C�����Ă���}�C�y�[�W����̏��𕜋A����̂ŁAloginUser�@
		// �̈����ɂ͒��ӂ��鎖�B
		LoginUser loginUser = null;
		if (mode != null && mode.equals("insert")) {
			loginUser = FrontLoginUserUtils.getInstance(request).getAnonLoginUserInfo(
					request, response);
		} else {
			loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(
					request, response);
		}


        // ��L�[�l�̃o�����[�^�`�F�b�N���K�{�̏ꍇ�A���O�C�����[�U�[��񂩂�擾����B
        // �V�K�o�^���ȊO�̓��O�C�����[�U�[��񂪎擾�o���Ă��Ȃ��ꍇ�̓G���[�Ƃ���B
        if (this.useUserIdValidation) {
        	if (loginUser == null) throw new RuntimeException ("not loggined.");
        }


		// �o���f�[�V�����̎��s���[�h���L���̏ꍇ�A�o���f�[�V���������s����B
        // �폜�����A���b�N���������̏ꍇ�̓��O�C��ID �ȊO�̃p�����[�^�͕s�v�Ȃ̂ŁAspring �����疳�������Ă���B
        if (this.useValidation) {
        	List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        	if (!inputForm.validate(errors, this.mode)) {

        		// �o���f�[�V�����G���[���́A�G���[���� model �ɐݒ肵�A���͉�ʂ�\������B
        		model.put("errors", errors);
        		return new ModelAndView("input" , model);
        	}

        	// ���[���A�h���X�i���O�C��ID�j �̏d���`�F�b�N�́A����������ōs���̂ŁA�ʏ�̃o���f�[�V�����Ƃ��Ă͎��{���Ȃ��B
        }


        // �e�폈�������s
        try {
            execute(model, inputForm, loginUser);

        } catch (DuplicateException e1) {
            // ���[���A�h���X�i���O�C��ID�j ���d�����Ă���ꍇ�A�ē��͂ցB
        	List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        	errors.add(new ValidationFailure("duplicate", "mypage.input.email", inputForm.getEmail(), null));
    		model.put("errors", errors);

        	return new ModelAndView("input", model);

        } catch (NotFoundException e2) {

        	// �Y������f�[�^�����݂��Ȃ��ꍇ
            // �i�Ⴆ�΁A�Ǘ����[�U�[�̌�����ɕʂ̃Z�b�V��������擾�Ώۃ��[�U�[���폜�����ꍇ�j�A
            // �����Ώۃf�[�^�����̗�O���X���[����B
           	throw new NotFoundException();

        }

        // �p�X���[�h�̒ʒm���[�����M
        sendPwdMail(request, inputForm);


		return new ModelAndView("success" , model);
	}



	/**
	 * model �I�u�W�F�N�g���쐬���A���N�G�X�g�p�����[�^���i�[���� form �I�u�W�F�N�g���i�[����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^��ݒ肵�� Form ���i�[���� model �I�u�W�F�N�g�𕜋A����B
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		MypageUserFormFactory factory = MypageUserFormFactory.getInstance(request);

		model.put("inputForm", factory.createMypageUserForm(request));

		return model;

	}



	/**
	 * �����̐U�蕪���Ǝ��s���s���B<br/>
	 * <br/>
	 * @param model View �w�ֈ����n�� model �I�u�W�F�N�g
	 * @param inputForm ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
	 * @param editUser ���O�C�����[�U�[���
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception DuplicateException �o�^���郍�O�C��ID ���d�������ꍇ
	 * @exception NotFoundException �X�V�Ώۂ����݂��Ȃ��ꍇ
	 * @exception RuntimeException mode ���w�肳��Ă��Ȃ��ꍇ�B�@�iDI �R���e�i�̐ݒ�~�X�j
	 */
	protected void execute(Map<String, Object> model, MypageUserForm inputForm, LoginUser editUser)
			throws Exception, DuplicateException, RuntimeException {

		if (this.mode.equals("insert")) {
			insert(model, inputForm, editUser);
        } else if (this.mode.equals("update")) {
			update(model, inputForm, (MemberInfo)editUser);
        } else {
        	// �z�肵�Ă��Ȃ��������[�h�̏ꍇ�A��O���X���[����B
        	throw new RuntimeException ("execute mode bad setting.");
        }
	}



	/**
	 * �V�K�o�^����<br/>
	 * �����œn���ꂽ���e�Ń��[�U�[ID���A�}�C�y�[�W�������ǉ�����B<br/>
	 * <br/>
	 * @param model View �w�ֈ����n�� model �I�u�W�F�N�g
	 * @param inputForm ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
	 * @param editUser �V�K�o�^�Ɏg�p���铽�����[�U�[�i�������͉�����j
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception DuplicateException �o�^���郍�O�C��ID ���d�������ꍇ
	 */
	protected void insert(Map<String, Object> model, MypageUserForm inputForm, LoginUser editUser)
			throws Exception, DuplicateException {

		String addUserId = (String) editUser.getUserId(); // �o�^�p���[�U�[ID

		// note
		// �A�����ĉ������o�^�����ꍇ�A�������́A�}�C�y�[�W�Ƀ��O�C���ςŉ���o�^�����ꍇ�A���O�C�����Ă���
		// �}�C�y�[�W������� userId ���n����Ă���B
		// ���ׁ̈A���̃��[�U�[�h�c�Ƀ}�C�y�[�W�����񂪓o�^����Ă��邩���`�F�b�N���A�����o�^����Ă���ꍇ��
		//�@�V���ȃ��[�U�[�h�c���̔Ԃ��Ă������o�^���s���B

    	// ���O�C�����[�U�[ID�ŉ�������擾����B
		JoinResult userInfo = this.userManager.searchMyPageUserPk(addUserId);

        // �Y����������񂪑��݂���ꍇ�́A�V���ȃ��[�U�[�h�c�𔭍s���A���̃��[�U�[�h�c��
		// �֘A�t�����ă}�C�y�[�W�������o�^����B
		if (userInfo != null) {
			LoginUser loginUser = this.userManager.addLoginID(null);
			addUserId = (String)loginUser.getUserId();
		}

    	// �}�C�y�[�W������̓o�^
    	this.userManager.addMyPageUser(inputForm, addUserId, addUserId);

	}



	/**
	 * �X�V�o�^����<br/>
	 * �����œn���ꂽ���e�ŊǗ����[�U�[�̏����X�V����B<br/>
	 * <br/>
	 * @param model View �w�ֈ����n�� model �I�u�W�F�N�g
	 * @param inputForm ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
	 * @param editUser �X�V�ΏۂƂȂ�}�C�y�[�W������
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception DuplicateException �o�^���郍�O�C��ID ���d�������ꍇ
	 * @exception NotFoundException �X�V�Ώۂ����݂��Ȃ��ꍇ
	 */
	protected void update(Map<String, Object> model, MypageUserForm inputForm, MemberInfo editUser)
			throws Exception, DuplicateException, NotFoundException {

		String userId = (String) editUser.getUserId();
		
		// �X�V����
    	this.userManager.updateMyPageUser(inputForm, userId, userId);

    	// �Z�b�V�����Ɋi�[����Ă�A���O�C�����[�U�[����ύX����B
    	updateSessionUserId(inputForm, editUser);
	}



	/**
	 * �p�X���[�h�̒ʒm���[���𑗐M����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param inputForm ���͒l
	 */
	protected void sendPwdMail(HttpServletRequest request, MypageUserForm inputForm){

		// ���[���e���v���[�g���ݒ肳��Ă��Ȃ��ꍇ�̓��[���̒ʒm���s��Ȃ��B
		if (this.sendMypagePasswordTemplate == null) {
			log.warn("sendMypagePasswordTemplate is null.");
			return;
		}

		// �V�K�o�^���܂��̓p�X���[�h�ύX�����������ꍇ�Ƀ��[���𑗐M����B
		// ��L������p�X���[�h�̓��̗͂L���ōs���B�@�i���͒l�����ۂɕύX���ꂽ���܂ł̓`�F�b�N���Ȃ��B�j
		if (StringValidateUtil.isEmpty(inputForm.getPassword())){
			return;
		}

		// ���[���e���v���[�g�Ŏg�p����p�����[�^��ݒ肷��B
		this.sendMypagePasswordTemplate.setParameter("inputForm", inputForm);
		this.sendMypagePasswordTemplate.setParameter("commonParameters", CommonParameters.getInstance(request));

		// ���[�����M
		this.sendMypagePasswordTemplate.send();
	}

	
	
	/**
	 * �Z�b�V�����Ɋi�[����Ă��郍�O�C�������X�V����B<br>
	 * <br>
	 * @param inputForm ���͂��ꂽ������
	 * @param sessionUser �Z�b�V��������擾����������
	 */
	protected void updateSessionUserId(MypageUserForm inputForm, MemberInfo sessionUser){

		sessionUser.setEmail(inputForm.getEmail());							// ���[���A�h���X
		sessionUser.setMemberLname(inputForm.getMemberLname());				// ������i���j
		sessionUser.setMemberFname(inputForm.getMemberFname());				// ������i���j
		sessionUser.setMemberLnameKana(inputForm.getMemberLnameKana());		// ������E�J�i�i���j 
		sessionUser.setMemberFnameKana(inputForm.getMemberFnameKana());		// ������E�J�i�i���j

		// �p�X���[�h�́A���͂��������ꍇ�̂ݕύX
		if (!StringValidateUtil.isEmpty(inputForm.getPassword())){
			// �p�X���[�h���Í������Ċi�[����B
			sessionUser.setPassword(EncodingUtils.md5Encode(inputForm.getPassword()));
		}
		
		// �ŏI�X�V���͕ύX���Ă��Ȃ����A�Q���������x���Ȃ̂ŁA�����ɍX�V�͍s��Ȃ��B
	}
}
