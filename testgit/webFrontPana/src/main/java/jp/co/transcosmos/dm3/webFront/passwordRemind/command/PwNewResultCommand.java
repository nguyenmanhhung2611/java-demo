package jp.co.transcosmos.dm3.webFront.passwordRemind.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.member.PanaMypageUserManageImpl;
import jp.co.transcosmos.dm3.corePana.model.passwordRemind.form.PasswordChangeForm;
import jp.co.transcosmos.dm3.corePana.model.passwordRemind.form.PasswordFormFactory;
import jp.co.transcosmos.dm3.mail.ReplacingMail;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * �p�X���[�h���}�C���_�[
 * �p�X���[�h���}�C���_�[��ʂ�\������B
 *
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *
 * �S����       �C����     �C�����e
 * ------------ ----------- -----------------------------------------------------
 * ��     2015.04.24  �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class PwNewResultCommand implements Command {

	public static final String RESULT_URL = "/mypage/login/";

	/** �}�C�y�[�W���[�U�[�̏����Ǘ����� Model �I�u�W�F�N�g */
	private MypageUserManage mypageUserManage;

	/** �X�V�ʒm���[���e���v���[�g */
	private ReplacingMail sendPasswordResult;

    /** ���ʃp�����[�^�I�u�W�F�N�g */
    private PanaCommonParameters commonParameters;

    /** ������p Model �I�u�W�F�N�g */
	private PanaMypageUserManageImpl panaMypageUserManager;

	/**
	 * @param mypageUserManager �Z�b�g���� mypageUserManager
	 */
	public void setPanaMypageUserManager(PanaMypageUserManageImpl panaMypageUserManager) {
		this.panaMypageUserManager = panaMypageUserManager;
	}

    /**
     * ���ʃp�����[�^�I�u�W�F�N�g��ݒ肷��B<br/>
     * <br/>
     * @param commonParameters ���ʃp�����[�^�I�u�W�F�N�g
     */
    public void setCommonParameters(PanaCommonParameters commonParameters) {
        this.commonParameters = commonParameters;
    }

	/**
	 * @param sendUpdateTemplate �Z�b�g���� sendUpdateTemplate
	 */
	public void setSendPasswordResult(
			ReplacingMail sendPasswordResult) {
		this.sendPasswordResult = sendPasswordResult;
	}

	/**
	 * �}�C�y�[�W���[�U�[�̏����Ǘ����� Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param MypageUserManage �}�C�y�[�W���[�U�[�̏����Ǘ����� Model �I�u�W�F�N�g
	 */
	public void setMypageUserManage(MypageUserManage mypageUserManage) {
		this.mypageUserManage = mypageUserManage;
	}

	/**
	 * �p�X���[�h���}�C���_�[��ʕ\������<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g
	 * @param response
	 *            HTTP ���X�|���X
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// �擾�����f�[�^�������_�����O�w�֓n��
		Map<String, Object> model = new HashMap<String, Object>();

		PasswordFormFactory factory = PasswordFormFactory
				.getInstance(request);

		// �y�[�W�����p�̃t�H�[���I�u�W�F�N�g���쐬
		PasswordChangeForm pwdChangeForm = factory.createPasswordChangeForm(request);

		// �o���f�[�V���������s
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
		if (!pwdChangeForm.validate(errors)) {

			// �o���f�[�V�����G���[����
			model.put("errors", errors);

			model.put("remindId", pwdChangeForm.getRemindId());

			model.put("email", pwdChangeForm.getEmail());

			model.put("pwdChangeForm", pwdChangeForm);

			return new ModelAndView("validFail", model);
		}

		// �����F�؎��ɕ����o����Ă��郆�[�U�[ID
		String entryUserId = FrontLoginUserUtils.getInstance(request).getAnonLoginUserInfo(request, response).getUserId().toString();

		// �p�X���[�h�̕ύX�������s���B
		try{
			mypageUserManage.changePassword(pwdChangeForm, entryUserId);
		}catch(NotFoundException e){
			return new ModelAndView("404", model);
		}

		String email = this.panaMypageUserManager.getResultMail(pwdChangeForm.getRemindId());

		pwdChangeForm.setEmail(email);

		String HttpPath = this.commonParameters.getSiteURL();

		String mypageURL = HttpPath + RESULT_URL;

		// ���[���e���v���[�g�Ŏg�p����p�����[�^��ݒ肷��B
		this.sendPasswordResult.setParameter("inputForm", pwdChangeForm);
		this.sendPasswordResult.setParameter("mypageURL", mypageURL);
		this.sendPasswordResult.setParameter("commonParameters", CommonParameters.getInstance(request));

		// ���[�����M
		this.sendPasswordResult.send();

		model.put("pwdChangeForm", pwdChangeForm);

		return new ModelAndView("success", model);
	}

}
