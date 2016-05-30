package jp.co.transcosmos.dm3.adminCore.pwdChange.command;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.AdminUserManage;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserFormFactory;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserSearchForm;
import jp.co.transcosmos.dm3.core.model.adminUser.form.PwdChangeForm;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.core.validation.CompareValidation;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.EncodingUtils;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * �Ǘ����[�U�[�p�X���[�h�ύX����.
 * <p>
 * ���O�C�����[�U�[���g�̃p�X���[�h��ύX����B<br/>
 * <br/>
 * �y���A���� View ���z<br/>
 * <ul>
 * <li>success</li>:������ʕ\��
 * <li>input</li>:���͉�ʕ\��
 * </ul>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.04	Shamaison ���Q�l�ɐV�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class PwdChangeCompCommand implements Command {

	/** �Ǘ����[�U�[�����e�i���X���s�� Model �I�u�W�F�N�g */
	protected AdminUserManage userManager;

	/** �t�H�[���̃t�@�N�g���[�I�u�W�F�N�g */
	protected AdminUserFormFactory formFactory;

	/** ���ʃp�����[�^�I�u�W�F�N�g */
    protected CommonParameters commonParameters;

	/** ���ʃR�[�h�ϊ����� */
    protected CodeLookupManager codeLookupManager;
	


	/**
	 * �Ǘ����[�U�[�����e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param userManager �Ǘ����[�U�[�����e�i���X�� model �I�u�W�F�N�g
	 */
	public void setUserManager(AdminUserManage userManager) {
		this.userManager = userManager;
	}



	/**
	 * �Ǘ����[�U�p�X���[�h�̕ύX����<br>
	 * <br>
	 * @param request �N���C�A���g�����Http���N�G�X�g�B
	 * @param response �N���C�A���g�ɕԂ�Http���X�|���X�B
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> model = new HashMap<>();

		// Form �̃t�@�N�g���[���擾
		this.formFactory = AdminUserFormFactory.getInstance(request);

		// ���ʃp�����[�^�I�u�W�F�N�g���擾
	    this.commonParameters = CommonParameters.getInstance(request);

	    // ���ʃR�[�h�ϊ��������擾
	    this.codeLookupManager = (CodeLookupManager) request.getAttribute("codeLookupManager");


		// ���N�G�X�g�p�����[�^�̎擾
		PwdChangeForm inputform = this.formFactory.createPwdChangeForm(request);
		model.put("inputform", inputform);


		// ���O�C�������Z�b�V��������擾
		AdminUserInterface userInfo = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);

		// �p�X���[�h�̍ŏI�X�V�����擾
		model.put("pwdChangeDate", userInfo.getLastPasswdChange());


		// �o���f�[�V�����̎��s
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        if (!inputform.validate(errors)){

        	// �o���f�[�V�����G���[���́A�G���[���� model �ɐݒ肵�A���͉�ʂ�\������B
        	model.put("errors", errors);
        	return new ModelAndView("input", model);
        }


        // ���p�X���[�h�̏ƍ����s��
        if (!oldPasswordCheck(userInfo, inputform, errors)) {

        	// ���p�X���[�h�̓��͊Ԉ᎞�́A�G���[���� model �ɐݒ肵�A���͉�ʂ�\������B
        	model.put("errors", errors);
        	return new ModelAndView("input", model);

        }

        // �p�X���[�h���X�V����B
        updatePassword(userInfo, inputform);

        // note
        // PasswordExpireFilter�@�́A�����؂��F�������ꍇ�ApasswordExpireFailed ��
        // �A�g���r���[�g���ŃZ�b�V�����Ƀt���O��ݒ肷��B
        // ���̉�ʂ́A���̃t���O�����āA�����؂�ł��鎖��`���郁�b�Z�[�W��\�����Ă���B
        // ���̃t���O�́A�t�B���^�[�̑Ώۉ�ʂɑJ�ڂ���΃��Z�b�g����邪�A�t�B���^�[�̑ΏۊO��J�ڂ���
        // ����Ԃ̓��Z�b�g����Ȃ��B
        // ����āA�p�X���[�h�̕ύX���s��ꂽ�ꍇ�͖����I�ɃZ�b�V��������t���O���폜����B
        request.getSession().removeAttribute("passwordExpireFailed");


        return new ModelAndView("success", model);
	}



	/**
	 * ���p�X���[�h�̑Ó������`�F�b�N����B<br/>
	 * �Z�b�V��������擾�������O�C�����[�U�[��� �̃��[�U�[ID ���L�[�Ƃ��āA���݂̃��O�C���p�X���[�h���擾����B<br/>
	 * �p�X���[�h�̒l�̓n�b�V�����ꂽ�l�Ŋi�[����Ă���ׁA���͂����l���n�b�V�����Ă����r����B<br/>
	 * <br/>
	 * ���A�P�[�X�����A�Z�b�V�����Ɋi�[���ꂽ���[�U�[��� DB �ɑ��݂��Ȃ��\��������B�@���̏ꍇ�A�����̌p����
	 * ����ȈׁA�V�X�e���G���[�Ƃ���B<br/>
	 * <br/>
	 * @param userInfo ���O�C�����[�U�[���
	 * @param inputform ��ʓ��͂����l �i���p�X���[�h�̒l�j
	 * @param errors �G���[���b�Z�[�W�p���X�g�I�u�W�F�N�g
	 * 
	 * @return true = ����Afalse = �G���[
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	protected boolean oldPasswordCheck(AdminUserInterface userInfo, PwdChangeForm inputform, List<ValidationFailure> errors)
			throws Exception {

		// ���݂̃p�X���[�h���擾����B
        AdminUserSearchForm searchForm = this.formFactory.createUserSearchForm();
        searchForm.setUserId((String)userInfo.getUserId());
        JoinResult result = this.userManager.searchAdminUserPk(searchForm);
        if (result == null){
        	// ���O�C�����ɊǗ����[�U�[�����e�i���X�ŃA�J�E���g���폜����ƁA���̏�ԂɂȂ�\��������B
        	// �A�J�E���g�����݂��Ȃ��ꍇ�A�ǂ����鎖���o���Ȃ��̂ŁA���̂܂܃V�X�e���G���[�ɂ���B
        	throw new RuntimeException ("admin user is deleted.");
        }

        AdminUserInterface adminUser = (AdminUserInterface)result.getItems().get(this.commonParameters.getAdminUserDbAlias());

        // �p�X���[�h�̓n�b�V�����ꂽ�l�Ŋi�[����Ă���̂ŁA�n�b�V�������l�ŏƍ�����B
        ValidationChain oldPwd = new ValidationChain("user.pwd.oldPassword", EncodingUtils.md5Encode(inputform.getOldPassword()));

        // ���b�Z�[�W�Ŏg�p���郉�x�����擾
        String label = this.codeLookupManager.lookupValue("errorCustom", "db_old_value");
        
        // ���p�X���[�h�̏ƍ�
        oldPwd.addValidation(new CompareValidation(adminUser.getPassword(), label));
        
        return oldPwd.validate(errors);

	}

	

	/**
	 * ���O�C�����[�U�[�̃p�X���[�h��ύX����B<br/>
	 * <br/>
	 * @param userInfo ���O�C�����[�U�[���
	 * @param inputform ��ʓ��͂����l �i���p�X���[�h�̒l�j
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException �X�V�Ώۂ����݂��Ȃ��ꍇ
	 */
	protected void updatePassword(AdminUserInterface userInfo, PwdChangeForm inputform)
			throws Exception, NotFoundException {

		// �Z�b�V��������擾�������O�C����񂩂烆�[�U�[�h�c���擾
		String userId = (String)userInfo.getUserId();

		// �p�X���[�h�̕ύX����
		// �p�X���[�h�ύX�ōX�V�Ώۃ��R�[�h�����݂��Ȃ��ꍇ�A�������p�������鎖���̂�����Ȃ̂�
		// ���̂܂܁@NotFoundException�@��O����ʂփX���[����B
        this.userManager.changePassword(inputform, userId, userId);


        // �p�X���[�h�̕ύX�����������̂ŁA�Z�b�V�����Ɋi�[����Ă��郍�O�C�����̃p�X���[�h�ŏI�X�V����
        // ���݂̃V�X�e�����t�ɕύX����B

        // note
        // ���̕��@���ƁADB �Ɏ��ۂɍX�V�����l�ƌ덷�������邪�A���p�I�ɂ͖��Ȃ��͈͂Ȃ̂ŁA
        // �C���^�[�t�F�[�X��ύX�����ɂ��̕��@�Ŏ������Ă���B
        userInfo.setLastPasswdChange(new Date());

	}

}
