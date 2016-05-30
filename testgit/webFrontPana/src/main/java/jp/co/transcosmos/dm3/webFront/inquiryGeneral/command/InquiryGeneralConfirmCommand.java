package jp.co.transcosmos.dm3.webFront.inquiryGeneral.command;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryFormFactory;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryGeneralForm;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * �ėp���⍇���m�F���
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
public class InquiryGeneralConfirmCommand implements Command {

	/**
	 * �ėp���⍇���m�F��ʕ\������<br>
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

		// view ���̏����l��ݒ�
		String viewName = "success";

		// �o���f�[�V���������s
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
		if (!inputForm.validate(errors)) {
			// �o���f�[�V�����G���[����
			model.put("errors", errors);
			model.put("errorsSingle", errors);
			viewName = "input";
		}

		if ("success".equals(viewName)) {
	        if (PanaCommonConstant.INQUIRY_DTL_TYPE_SEMINAR.equals(inputForm.getInquiryHeaderForm().getInquiryDtlType()[0])){
	    		DateFormat format1 =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    		DateFormat format =  new SimpleDateFormat("M���@d���@H:mm");
	    		inputForm.setEventDatetimeWithFormat(format.format(format1.parse(inputForm.getEventDatetime())));
	        }
    		inputForm.setInquiryText1(PanaStringUtils.encodeHtml(inputForm.getInquiryHeaderForm().getInquiryText()));
		}

		model.put("inputForm", inputForm);

		// �擾�����f�[�^�������_�����O�w�֓n��
		return new ModelAndView(viewName, model);
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
