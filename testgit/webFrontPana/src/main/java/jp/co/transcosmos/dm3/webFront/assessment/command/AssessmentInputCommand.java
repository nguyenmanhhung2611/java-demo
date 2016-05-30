package jp.co.transcosmos.dm3.webFront.assessment.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.assessment.form.AssessmentFormFactory;
import jp.co.transcosmos.dm3.corePana.model.assessment.form.AssessmentInputForm;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryFormFactory;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * ����\����ʂ̏�����
 * �s���{�������擾�B
 *
 * �y���A���� View ���z
 *	�E"success" : ����I��
 *
 * �S����	   �C����	  �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �`����	   2015.04.27  �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class AssessmentInputCommand implements Command {

	/** ���ʏ���p Model �I�u�W�F�N�g */
	private PanaCommonManage panamCommonManager;

	/** �}�C�y�[�W���[�U�[�̏����Ǘ����� Model �I�u�W�F�N�g */
	private MypageUserManage mypageUserManage;

	/**
	 * ���ʏ���p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param PanaCommonManage ���ʏ���p Model �I�u�W�F�N�g
	 */
	public void setPanamCommonManager(PanaCommonManage panamCommonManager) {
		this.panamCommonManager = panamCommonManager;
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
	 * ����\����ʂ̏������\������<br>
	 * <br>
	 *
	 * @param request
	 *			�N���C�A���g�����Http���N�G�X�g�B
	 * @param response
	 *			�N���C�A���g�ɕԂ�Http���X�|���X�B
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
		Map<String, Object> model = createModel(request);
		AssessmentInputForm inputForm = (AssessmentInputForm) model.get("inputForm");

		String command = inputForm.getCommand();
		if (StringValidateUtil.isEmpty(command)) {

			// �u���p�����Ɠ����v�̏�����
			model.put("sameWithHousingFG", true);

			// �}�C�y�[�W��������擾
			LoginUser loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);
			JoinResult userInfo = null;
			if (loginUser != null) {
				userInfo = mypageUserManage.searchMyPageUserPk((String)loginUser.getUserId());
			}

			// Form �֏����l��ݒ肷��B
			inputForm.setDefaultData(userInfo);

		}

		// �s���{��List�̒l�� view �w�֓n���p�����[�^�Ƃ��Đݒ肵�Ă���B
		model.put("prefMst", this.panamCommonManager.getPrefMstList());

		model.put("inputForm", inputForm);

		return new ModelAndView("success", model);
	}

	/**
	 * model �I�u�W�F�N�g���쐬���A���N�G�X�g�p�����[�^���i�[���� form �I�u�W�F�N�g���i�[����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^��ݒ肵�� Form ���i�[���� model �I�u�W�F�N�g�𕜋A����B
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<>();

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		AssessmentFormFactory factory = AssessmentFormFactory.getInstance(request);
		PanaInquiryFormFactory headerFactory = PanaInquiryFormFactory.getInstance(request);

		AssessmentInputForm inputForm = factory.createAssessmentInputForm(request);
		inputForm.setCommonInquiryForm(headerFactory.createInquiryHeaderForm(request));

		model.put("inputForm", inputForm);

		return model;

	}
}
