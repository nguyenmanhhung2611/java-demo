package jp.co.transcosmos.dm3.webFront.assessment.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.assessment.form.AssessmentFormFactory;
import jp.co.transcosmos.dm3.corePana.model.assessment.form.AssessmentInputForm;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryFormFactory;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * ����\����ʂ̐V�K�o�^
 *
 * �y���A���� View ���z
 *	�E"success" : ����I��
 *	�E"input"   : �o���f�[�V�����G���[�ɂ��ē���
 *
 * �S����	   �C����	  �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �`����	   2015.04.29  �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class AssessmentConfirmCommand implements Command {

	/** ���ʏ���p Model �I�u�W�F�N�g */
	private PanaCommonManage panamCommonManager;

	/**
	 * ���ʏ���p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param PanaCommonManage ���ʏ���p Model �I�u�W�F�N�g
	 */
	public void setPanamCommonManager(PanaCommonManage panamCommonManager) {
		this.panamCommonManager = panamCommonManager;
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

		// �v�]�E����i�\���p�j��ݒ�
		inputForm.setRequestTextHyoji(PanaStringUtils.encodeHtml(inputForm.getRequestText()));
		// Form �l�̎����ϊ�������B
		inputForm.setAutoChange();

		// view ���̏����l��ݒ�
		String viewName = "success";

		// �o���f�[�V���������s
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
		if (!inputForm.validate(errors)){

			// �o���f�[�V�����G���[���́A�G���[���� model �ɐݒ肵�A���͉�ʂ�\������B
			model.put("errors", errors);

			// �s���{��List�̒l�� view �w�֓n���p�����[�^�Ƃ��Đݒ肵�Ă���B
			model.put("prefMst", this.panamCommonManager.getPrefMstList());

			viewName = "input";
		}

		// �s���{�������擾���ݒ肷��B
		inputForm.setPrefName(this.panamCommonManager.getPrefName(inputForm.getPrefCd()));
		if (!StringValidateUtil.isEmpty(inputForm.getUserPrefCd())) {
			inputForm.setUserPrefName(this.panamCommonManager.getPrefName(inputForm.getUserPrefCd()));
		}

		return new ModelAndView(viewName, model);

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
