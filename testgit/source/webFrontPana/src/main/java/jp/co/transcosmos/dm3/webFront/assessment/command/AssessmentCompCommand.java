package jp.co.transcosmos.dm3.webFront.assessment.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.assessment.form.AssessmentFormFactory;
import jp.co.transcosmos.dm3.corePana.model.assessment.form.AssessmentInputForm;
import jp.co.transcosmos.dm3.corePana.model.inquiry.AssessmentInquiryManageImpl;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryFormFactory;
import jp.co.transcosmos.dm3.corePana.util.AffiliateUtil;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.mail.ReplacingMail;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * ����\���m�F��ʂ���o�^
 *
 * �y���A���� View ���z
 *	�E"success" : ����I��
 *	�E"input"   : �o���f�[�V�����G���[�ɂ��ē���
 *
 * �S����                  �C����                         �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �`����                     2015.04.29   �V�K�쐬
 * Thi Tran   2015.10.16    Add 'janetUrl' attribute to model
 *
 * ���ӎ���
 *
 * </pre>
 */
public class AssessmentCompCommand implements Command {

	/** ���ʏ���p Model �I�u�W�F�N�g */
	private PanaCommonManage panamCommonManager;

	/** �����񃁃��e�i���X���s�� Model �I�u�W�F�N�g */
	private AssessmentInquiryManageImpl assessmentInquiryManager;

	/** �p�X���[�h�ʒm���[���e���v���[�g */
	private ReplacingMail sendAssessmentInquiryTemplate;

	/**
	 * ���ʏ���p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param PanaCommonManage ���ʏ���p Model �I�u�W�F�N�g
	 */
	public void setPanamCommonManager(PanaCommonManage panamCommonManager) {
		this.panamCommonManager = panamCommonManager;
	}

	/**
	 * @param assessmentInquiryManage �Z�b�g���� assessmentInquiryManager
	 */
	public void setAssessmentInquiryManager(
			AssessmentInquiryManageImpl assessmentInquiryManager) {
		this.assessmentInquiryManager = assessmentInquiryManager;
	}

	/**
	 * ����̂��\�����ݎ�̃��[���e���v���[�g��ݒ肷��B<br/>
	 * ���ݒ�̏ꍇ�A���[�����M���s��Ȃ��B<br/>
	 * <br/>
	 * @param sendAssessmentMailTemplate ����̂��\�����ݎ�̃��[���e���v���[�g
	 */
	public void setSendAssessmentInquiryTemplate(ReplacingMail sendAssessmentInquiryTemplate) {
		this.sendAssessmentInquiryTemplate = sendAssessmentInquiryTemplate;
	}



	/**
	 * ����\���m�F��ʕ\������<br>
	 * <br>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	*/
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
		Map<String, Object> model = createModel(request);
		AssessmentInputForm inputForm = (AssessmentInputForm) model.get("inputForm");

		// ������ʂŃ����[�h�����ꍇ�A�X�V�������Ӑ}�������s������肪��������B
		// ���̖�����������ׁAview ���� "success"�@���w�肷��Ǝ������_�C���N�g��ʂ��\�������B
		// ���̃��_�C���N�g��ʂ́Acommand �p�����[�^�� "redirect"�@�ɐݒ肵�Ċ�����ʂփ��N�G�X�g��
		// ���M����B
		// ����āAcommand = "redirect" �̏ꍇ�́A�c�a�X�V�͍s�킸�A������ʂ�\������B
		String command = inputForm.getCommand();
		if (command != null && command.equals("redirect")){
			return new ModelAndView("comp" , model);
		}

		// Form �l�̎����ϊ�������B
		inputForm.setAutoChange();

		// �o���f�[�V���������s
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
		if (!inputForm.validate(errors)){

			// �o���f�[�V�����G���[���́A�G���[���� model �ɐݒ肵�A���͉�ʂ�\������B
			model.put("errors", errors);

			// �s���{��List�̒l�� view �w�֓n���p�����[�^�Ƃ��Đݒ肵�Ă���B
			model.put("prefMst", this.panamCommonManager.getPrefMstList());

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

		// �������o�^����B
		String inquiryId = this.assessmentInquiryManager.addInquiry(inputForm, mypageUserId, editUserId);

		// ���[�����M
		this.sendAssessmentInquiryTemplate.setParameter("inputForm", inputForm);
		this.sendAssessmentInquiryTemplate.setParameter("inquiryId", inquiryId);
		this.sendAssessmentInquiryTemplate.setParameter("commonParameters", CommonParameters.getInstance(request));
		this.sendAssessmentInquiryTemplate.send();

        // Get janet url
        PanaCommonParameters commonParamerers = PanaCommonParameters.getInstance(request);
        model.put("janetUrl", AffiliateUtil.getJanetURL(commonParamerers.getJanetUrl(),
                commonParamerers.getInquiryIdPrefix() + inquiryId, commonParamerers.getJanetAssessmentSid()));
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


		// �A���\�Ȏ��ԑт̍č쐬
		String reqParam = request.getParameter("contactTime");
		if (!StringValidateUtil.isEmpty(reqParam)) {
			String[] contactTime = reqParam.split(",");
			inputForm.setContactTime(contactTime);
		}
		// �A���P�[�g�񓚊i�[�̍č쐬
		reqParam = request.getParameter("ansCd");
		if (!StringValidateUtil.isEmpty(reqParam)) {
			String[] ansCd = reqParam.split(",");
			inputForm.setAnsCd(ansCd);
		}

		model.put("inputForm", inputForm);

		return model;

	}

}
