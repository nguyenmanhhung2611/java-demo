package jp.co.transcosmos.dm3.webFront.inquiryGeneral.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.model.inquiry.GeneralInquiryManageImpl;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryFormFactory;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryGeneralForm;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.mail.ReplacingMail;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * �ėp���⍇���������
 *
 * <pre>
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * qiao.meng	 2015.04.28   �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class InquiryGeneralCompCommand implements Command {

	/** �ėp�⍇�������e�i���X���s�� Model �I�u�W�F�N�g */
	private GeneralInquiryManageImpl generalInquiryManager;

	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;

	/** �V�K�ʒm���[���e���v���[�g */
	private ReplacingMail sendInquiryGeneralMail;



	/**
	 * @param generalInquiryManager �Z�b�g���� generalInquiryManager
	 */
	public void setGeneralInquiryManager(
			GeneralInquiryManageImpl generalInquiryManager) {
		this.generalInquiryManager = generalInquiryManager;
	}


	/**
	 * ���ʃR�[�h�ϊ��I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param codeLookupManager
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}


	/**
	 * @param sendInquiryGeneralMail �Z�b�g���� sendInquiryGeneralMail
	 */
	public void setSendInquiryGeneralMail(ReplacingMail sendInquiryGeneralMail) {
		this.sendInquiryGeneralMail = sendInquiryGeneralMail;
	}

	/**
	 * �ėp���⍇��������ʕ\������<br>
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

		String command = inputForm.getCommand();
		if (command !=null && "redirect".equals(command)){
        	return new ModelAndView("comp" , model);
		}
		// �o���f�[�V���������s
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
		if (!inputForm.validate(errors)) {

			// �o���f�[�V�����G���[����
			model.put("errors", errors);
			model.put("errorsSingle", errors);

			model.put("inputForm", inputForm);

			return new ModelAndView("input", model);
		}

		// ���O�C�����[�U�[�̏����擾
		String mypageUserId = null;
		LoginUser loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);
		if (loginUser == null) {
			loginUser = FrontLoginUserUtils.getInstance(request).getAnonLoginUserInfo(request, response);
		} else {
			// �}�C�y�[�W�̃��[�U�[ID���擾
			mypageUserId = (String)loginUser.getUserId();
		}
 		// ���[�UID�i�X�V���p�j���擾
 		String editUserId = (String)loginUser.getUserId();

		// �ėp���₢���킹��DB�o�^����
		this.generalInquiryManager.addInquiry(inputForm, mypageUserId, editUserId);

		// ���[���e���v���[�g�Ŏg�p����p�����[�^��ݒ肷��B
		this.sendInquiryGeneralMail.setParameter("inputForm",inputForm);
		this.sendInquiryGeneralMail.setParameter("commonParameters",CommonParameters.getInstance(request));

		// ���[�����M
		this.sendInquiryGeneralMail.send();

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
