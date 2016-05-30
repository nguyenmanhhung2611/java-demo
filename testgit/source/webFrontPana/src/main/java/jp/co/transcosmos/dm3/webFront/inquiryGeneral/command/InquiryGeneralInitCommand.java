package jp.co.transcosmos.dm3.webFront.inquiryGeneral.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryFormFactory;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryGeneralForm;
import jp.co.transcosmos.dm3.corePana.vo.MemberInfo;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.login.LoginUser;

import org.springframework.web.servlet.ModelAndView;

/**
 * �ėp���₢���킹���͉��
 *
 * <pre>
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * qiao.meng	 2015.04.28    �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class InquiryGeneralInitCommand implements Command {

	/** �������[�h (init = ��ʏ������AeditBack = �ĕҏW) */
	private String mode;

	/** �}�C�y�[�W���[�U�[�̏����Ǘ����� Model �I�u�W�F�N�g */
	private MypageUserManage mypageUserManage;

	/**
	 * @param mode �Z�b�g���� mode
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @param mypageUserManage �Z�b�g���� mypageUserManage
	 */
	public void setMypageUserManage(MypageUserManage mypageUserManage) {
		this.mypageUserManage = mypageUserManage;
	}

	/**
	 * �ėp���₢���킹���͉�ʕ\������<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g
	 * @param response
	 *            HTTP ���X�|���X
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = createModel(request);
		PanaInquiryGeneralForm inputForm = (PanaInquiryGeneralForm)model.get("inputForm");

		// command �p�����[�^�� "back" �̏ꍇ�A���͊m�F��ʂ���̕��A�Ȃ̂ŁA�c�a���珉���l���擾���Ȃ��B
		// �i���N�G�X�g�p�����[�^����擾�����l���g�p����B�j
		String command = inputForm.getCommand();
		if (command != null && "back".equals(command)) {

			return new ModelAndView("success", model);
		}

		// �����\���̏ꍇ
		if ("init".equals(this.mode)) {
			String[] inquiryDtlType = {"001"};
			inputForm.getInquiryHeaderForm().setInquiryDtlType(inquiryDtlType);

			// ���O�C�����[�U�[�̏����擾
			LoginUser loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);

			JoinResult userInfo = null;
			if (loginUser != null) {
				userInfo = this.mypageUserManage.searchMyPageUserPk(String.valueOf(loginUser.getUserId()));
			}

			// �}�C�y�[�W��������擾
			if (userInfo != null){
				MemberInfo memberInfo = (MemberInfo)userInfo.getItems().get("memberInfo");
				if (memberInfo != null) {
					// �����O_��
					inputForm.getInquiryHeaderForm().setLname(memberInfo.getMemberLname());
					// �����O_��
					inputForm.getInquiryHeaderForm().setFname(memberInfo.getMemberFname());
					// �����O�i�t���K�i�j_��
					inputForm.getInquiryHeaderForm().setLnameKana(memberInfo.getMemberLnameKana());
					// �����O�i�t���K�i�j_��
					inputForm.getInquiryHeaderForm().setFnameKana(memberInfo.getMemberFnameKana());
					// ���[���A�h���X
					inputForm.getInquiryHeaderForm().setEmail(memberInfo.getEmail());
					// �d�b�ԍ�
					inputForm.getInquiryHeaderForm().setTel(memberInfo.getTel());
				}
			}
		}

		model.put("inputForm", inputForm);

		// �擾�����f�[�^�������_�����O�w�֓n��
		return new ModelAndView("success", model);
	}



	/**
	 * ���N�G�X�g�p�����[�^���� Form �I�u�W�F�N�g���쐬����B<br/>
	 * �������� Form �I�u�W�F�N�g�� Map �Ɋi�[���ĕ��A����B<br/>
	 * key = �t�H�[���N���X���i�p�b�P�[�W�Ȃ��j�AValue = �t�H�[���I�u�W�F�N�g <br/>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^���ݒ肳�ꂽ�t�H�[���I�u�W�F�N�g���i�[���� Map �I�u�W�F�N�g
	 */
	private Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<>();
        // ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		PanaInquiryFormFactory factory = PanaInquiryFormFactory.getInstance(request);
		PanaInquiryFormFactory headerFactory = PanaInquiryFormFactory.getInstance(request);

		PanaInquiryGeneralForm inputForm = factory.createPanaInquiryGeneralForm(request);
		inputForm.setCommonInquiryForm(headerFactory.createInquiryHeaderForm(request));

		model.put("inputForm", inputForm);

		return model;

	}
}
