package jp.co.transcosmos.dm3.webAdmin.inquiryStatus.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.adminCore.inquiry.command.InquiryStatusConfirmCommand;
import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.InquiryManage;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.inquiry.InquiryInterface;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.model.inquiry.InquiryInfo;
import jp.co.transcosmos.dm3.corePana.model.inquiry.PanaHousingInquiryManageImpl;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryStatusForm;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHeader;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * ���⍇�������͊m�F���
 * ���N�G�X�g�p�����[�^�œn���ꂽ���⍇�����̃o���f�[�V�������s���A�m�F��ʂ�\������B
 * �����o���f�[�V�����G���[�����������ꍇ�͓��͉�ʂƂ��ĕ\������B
 *
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *    �E"input" : �o���f�[�V�����G���[�ɂ��ē���
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �s�����C		2015.04.09	�V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
*/
public class PanaInquiryStatusConfirmCommand extends InquiryStatusConfirmCommand implements Command {

	/** �����⍇�������e�i���X���s�� Model �I�u�W�F�N�g */
	private PanaHousingInquiryManageImpl panaInquiryManager;
	/** ����⍇�������e�i���X���s�� Model �I�u�W�F�N�g */
	private InquiryManage assessmentInquiryManager;
	/** �ėp�⍇�������e�i���X���s�� Model �I�u�W�F�N�g */
	private InquiryManage generalInquiryManager;

	/**
	 * �����⍇�������e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param panaInquiryManager
	 *            �����⍇�������e�i���X�� model �I�u�W�F�N�g
	 */
	public void setPanaInquiryManager(
			PanaHousingInquiryManageImpl panaInquiryManager) {
		this.panaInquiryManager = panaInquiryManager;
	}

	/**
	 * ����⍇�������e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param assessmentInquiryManager
	 *            ����⍇�������e�i���X�� model �I�u�W�F�N�g
	 */
	public void setAssessmentInquiryManager(InquiryManage assessmentInquiryManager) {
		this.assessmentInquiryManager = assessmentInquiryManager;
	}

	/**
	 * �ėp�⍇�������e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param generalInquiryManager
	 *            �ėp�⍇�������e�i���X�� model �I�u�W�F�N�g
	 */
	public void setGeneralInquiryManager(InquiryManage generalInquiryManager) {
		this.generalInquiryManager = generalInquiryManager;
	}

	/**
	 * ���⍇�������͊m�F��ʕ\������<br>
	 * <br>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	*/
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = super.createModel(request);
		PanaInquiryStatusForm inputForm = (PanaInquiryStatusForm) model.get("inputForm");

		// ���O�`�F�b�N
		 String inquiryType = check(inputForm);

		ModelAndView modelAndView = super.handleRequest(request, response);
		model = modelAndView.getModel();

        // �⍇���I�u�W�F�N�g���쐬���āA�⍇�������擾����B
 		InquiryInfo inquiryInfo = getInquiryInfo(inputForm, inquiryType);

 		// �Y������f�[�^�����݂��Ȃ��ꍇ�A�Y��������ʂ�\������B
 		if (inquiryInfo == null) {
 			return new ModelAndView("notFound", model);
 		}

 		// model�ɂ��⍇�������i�[����
 		putModel(inquiryInfo, model, inquiryType, inputForm);

 		// �Ή����e��ݒ�
 		inputForm.setShowAnswerText(PanaStringUtils.encodeHtml(inputForm.getAnswerText()));
 		model.put("inputForm", inputForm);

        return modelAndView;
	}

	/**
	 * model �I�u�W�F�N�g���쐬���A���N�G�X�g�p�����[�^���i�[���� form �I�u�W�F�N�g���i�[����B<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            inputForm
	 * @throws Exception
	 */
	protected String check(PanaInquiryStatusForm inputForm) throws Exception {

		// �����ΏۂƂȂ��L�[�l���p�����[�^�œn����Ă��Ȃ��ꍇ�A��O���X���[����B
		if (StringValidateUtil.isEmpty(inputForm.getInquiryId())) {
			throw new RuntimeException("���⍇��ID���w�肳��Ă��܂���.");
		}

		// ���O�`�F�b�N:���⍇��ID�iinquiryId�j�ɂ��A���⍇���w�b�_�e�[�u���̓Ǎ�
		InquiryHeader inquiryHeader = panaInquiryManager.searchInquiryHeaderPk(inputForm.getInquiryId());

		// �f�[�^�̑��݂��Ȃ��ꍇ�B
		if (inquiryHeader.getInquiryId() == null) {
			throw new NotFoundException();
		}

		return inquiryHeader.getInquiryType();
	}

	/**
	 * ���⍇�������擾����B<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            inputForm
	 * @throws Exception
	 */
	protected InquiryInfo getInquiryInfo(PanaInquiryStatusForm inputForm, String inquiryType) throws Exception {

		// �⍇���I�u�W�F�N�g���쐬����
		InquiryInfo inquiryInfo = new InquiryInfo();

		if (PanaCommonConstant.INQUIRY_TYPE_HOUSING.equals(inquiryType)) {
			// �����⍇�������擾����
			InquiryInterface inquiryHousing = this.panaInquiryManager.searchHousingInquiryPk(inputForm.getInquiryId());

			return (InquiryInfo)inquiryHousing;

		} else if (PanaCommonConstant.INQUIRY_TYPE_GENERAL.equals(inquiryType)) {
			// �ėp�⍇�������擾����
			InquiryInterface	inquiryGeneral = generalInquiryManager.searchInquiryPk(inputForm.getInquiryId());

			return (InquiryInfo)inquiryGeneral;

		} else if (PanaCommonConstant.INQUIRY_TYPE_ASSESSMENT.equals(inquiryType)) {
			// ����⍇�������擾����
			InquiryInterface	inquiryAssessment = assessmentInquiryManager.searchInquiryPk(inputForm.getInquiryId());

			return (InquiryInfo)inquiryAssessment;

		}

		return inquiryInfo;
	}

	/**
	 * �擾�������⍇������model �I�u�W�F�N�g�Ɋi�[����B<br/>
	 * <br/>
	 *
	 * @param model
	 *            model �I�u�W�F�N�g
	 * @param inquiryInfo
	 *            ���⍇�����
	 */
	protected void putModel(InquiryInfo inquiryInfo, Map<String, Object> model, String inquiryType, PanaInquiryStatusForm inputForm) {

		if (inquiryType.equalsIgnoreCase(PanaCommonConstant.INQUIRY_TYPE_HOUSING)) {

			// �擾���������⍇������model �֊i�[����B
			inputForm.setHousingData(inquiryInfo, model);

		} else if (inquiryType.equalsIgnoreCase(PanaCommonConstant.INQUIRY_TYPE_GENERAL)) {

			// �擾�����ėp�⍇������model �֊i�[����B
			inputForm.setGeneralData(inquiryInfo, model);

		} else if (inquiryType.equalsIgnoreCase(PanaCommonConstant.INQUIRY_TYPE_ASSESSMENT)) {

			// �擾�����������model �֊i�[����B
			inputForm.setAssessmentData(inquiryInfo, model);
		}
	}
}
