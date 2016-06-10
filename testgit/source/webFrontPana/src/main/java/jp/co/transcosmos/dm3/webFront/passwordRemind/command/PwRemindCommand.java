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
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserFormFactory;
import jp.co.transcosmos.dm3.core.model.mypage.form.RemindForm;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.passwordRemind.form.PasswordFormFactory;
import jp.co.transcosmos.dm3.corePana.model.passwordRemind.form.PasswordRemindForm;
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
public class PwRemindCommand implements Command {

	public static final String NEWPW_URL = "/account/pw/new/input/";

	/** �}�C�y�[�W���[�U�[�̏����Ǘ����� Model �I�u�W�F�N�g */
	private MypageUserManage mypageUserManage;

	/** �X�V�ʒm���[���e���v���[�g */
	private ReplacingMail sendPasswordRemind;

    /** ���ʃp�����[�^�I�u�W�F�N�g */
    private PanaCommonParameters commonParameters;

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
	public void setSendPasswordRemind(
			ReplacingMail sendPasswordRemind) {
		this.sendPasswordRemind = sendPasswordRemind;
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
		PasswordRemindForm pwRemindForm = factory
				.createPasswordRemindForm(request);

		MypageUserFormFactory mpfactory = MypageUserFormFactory
				.getInstance(request);

		RemindForm remindForm = mpfactory.createRemindForm(request);

		remindForm.setEmail(pwRemindForm.getMailAddress());

		// �o���f�[�V���������s
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
		if (!pwRemindForm.validate(errors)) {

			// �o���f�[�V�����G���[����
			model.put("errors", errors);

			model.put("pwRemindForm", pwRemindForm);

			return new ModelAndView("validFail", model);
		}

		pwRemindForm.setEmail(pwRemindForm.getMailAddress());

		// �Y�����[���̃��[�U���݃`�F�b�N
		boolean emailExitsFlg = this.mypageUserManage.isFreeLoginId(pwRemindForm,"");

		if(emailExitsFlg){

			ValidationFailure vf = new ValidationFailure(
	                    "emailExitsError", "", "", null);
            errors.add(vf);
	        // �G���[����
			// �G���[�I�u�W�F�N�g�ƁA�t�H�[���I�u�W�F�N�g��ModelAndView �ɓn���Ă���
			model.put("errors", errors);
			// ���X�g���擾����
			model.put("pwRemindForm", pwRemindForm);
			return new ModelAndView("validFail", model);
		}

		// �����F�؎��ɕ����o����Ă��郆�[�U�[ID
		String entryUserId = FrontLoginUserUtils.getInstance(request).getAnonLoginUserInfo(request, response).getUserId().toString();

		// �p�X���[�h�ύX�̓o�^�������s���B
		String returnId = mypageUserManage.addPasswordChangeRequest(remindForm, entryUserId);

		String HttpPath = this.commonParameters.getSiteURL();

		String url = HttpPath + NEWPW_URL + returnId;

		// ���[���e���v���[�g�Ŏg�p����p�����[�^��ݒ肷��B
		this.sendPasswordRemind.setParameter("inputForm", pwRemindForm);
		this.sendPasswordRemind.setParameter("mailUrl", url);
		this.sendPasswordRemind.setParameter("commonParameters", CommonParameters.getInstance(request));

		// ���[�����M
		this.sendPasswordRemind.send();

		model.put("pwRemindForm", pwRemindForm);

		return new ModelAndView("success", model);
	}

}
