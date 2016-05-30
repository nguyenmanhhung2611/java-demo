package jp.co.transcosmos.dm3.webAdmin.inquiryStatus.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.InquiryManage;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.inquiry.InquiryInterface;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.model.inquiry.InquiryInfo;
import jp.co.transcosmos.dm3.corePana.model.inquiry.PanaHousingInquiryManageImpl;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryFormFactory;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquirySearchForm;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryStatusForm;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHeader;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * ���⍇�������͉��
 *
 * �E���N�G�X�g�p�����[�^�i������ʂœ��͂������������A�\���y�[�W�ʒu���j�̎����s���B
 * �E���N�G�X�g�p�����[�^�iinquiryId�j�ɊY�����邨�⍇�����A�}�C�y�[�W��������擾���A���
 *      �\������B
 *
 * �y���N�G�X�g�p�����[�^�icommand�j �� "back"�̏ꍇ�z
 *     �E���N�G�X�g�p�����[�^�œn���ꂽ���͒l����͉�ʂɕ\������B�@�i���͊m�F��ʂ��畜�A�����P�[�X�B�j
 *
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *    �E"notFound" : �Y���f�[�^�����݂��Ȃ��ꍇ
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �s�����C		2015.04.08	�V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class InquiryStatusInputCommand implements Command {

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
	 * ���⍇�������͉�ʕ\������<br>
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
		PanaInquiryStatusForm inputForm = (PanaInquiryStatusForm) model.get("inputForm");

		// ���O�`�F�b�N
		 String inquiryType = check(inputForm);

		// �⍇���I�u�W�F�N�g���쐬���āA�⍇�������擾����B
		InquiryInfo inquiryInfo = getInquiryInfo(inputForm, inquiryType);

		// �Y������f�[�^�����݂��Ȃ��ꍇ�A�Y��������ʂ�\������B
		if (inquiryInfo == null) {
			return new ModelAndView("notFound", model);
		}

		// model�ɂ��⍇�������i�[����
		putModel(inquiryInfo, model, inquiryType, inputForm);

		// command �p�����[�^�� "back" �̏ꍇ�A���͊m�F��ʂ���̕��A�Ȃ̂ŁA�c�a���珉���l���擾���Ȃ��B
		// �i���N�G�X�g�p�����[�^����擾�����l���g�p����B�j
		String command = inputForm.getCommand();
		if (command != null && "back".equals(command)) {

			return new ModelAndView("success", model);
		}

		// �擾�����l����͗p Form �֊i�[����B �i���͒l�̏����l�p�j
		inputForm.setDefaultData(inquiryInfo, inquiryType);

		return new ModelAndView("success", model);
	}

	/**
	 * ���O�`�F�b�N:���⍇��ID�iinquiryId�j�ɂ��A���⍇���w�b�_�e�[�u���̓Ǎ�����B<br/>
	 * <br/>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^��ݒ肵�� Form ���i�[���� model �I�u�W�F�N�g�𕜋A����B
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		PanaInquiryFormFactory factory = PanaInquiryFormFactory.getInstance(request);
		PanaInquiryStatusForm inputForm = factory.createPanaInquiryStatusForm(request);
		PanaInquirySearchForm searchForm = factory.createPanaInquirySearchForm(request);

		model.put("inputForm", inputForm);
		model.put("searchForm", searchForm);

		return model;
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
