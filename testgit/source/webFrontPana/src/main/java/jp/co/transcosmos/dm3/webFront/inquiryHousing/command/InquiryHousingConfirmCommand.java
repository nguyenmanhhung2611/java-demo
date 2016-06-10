package jp.co.transcosmos.dm3.webFront.inquiryHousing.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryHeaderForm;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryFormFactory;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryHeaderForm;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryHousingForm;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * �����̂��₢���킹�m�F���
 *
 * <pre>
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �s�����C    2015.04.23   �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class InquiryHousingConfirmCommand implements Command {

	/** ���ʏ���p Model �I�u�W�F�N�g */
	private PanaCommonManage panaCommonManage;

	/** ���������s�� Model �I�u�W�F�N�g */
	private PanaHousingPartThumbnailProxy panaHousingManager;

    /** ���ʃp�����[�^�I�u�W�F�N�g */
    private PanaCommonParameters commonParameters;

	/** �����摜���DAO */
	private DAO<HousingImageInfo> HousingImageInfoDAO;

	/** Panasonic�p�t�@�C�������֘A����Util */
	private PanaFileUtil fileUtil;

	/**
	 * ���ʏ���p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param PanaCommonManage ���ʏ���p Model �I�u�W�F�N�g
	 */
	public void setPanaCommonManage(PanaCommonManage panaCommonManage) {
		this.panaCommonManage = panaCommonManage;
	}

	/**
	 * ���������s�� Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param panaHousingManager ���������s�� Model �I�u�W�F�N�g
	 */
	public void setPanaHousingManager(PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
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
	 * �����摜���DAO��ݒ肷��B<br/>
	 * <br/>
	 * @param housingImageInfoDAO �����摜���DAO
	 */
	public void setHousingImageInfoDAO(DAO<HousingImageInfo> housingImageInfoDAO) {
		HousingImageInfoDAO = housingImageInfoDAO;
	}

	/**
	 *Panasonic�p�t�@�C�������֘A����Util��ݒ肷��B<br/>
	 * <br/>
	 * @param fileUtil Panasonic�p�t�@�C�������֘A����Util
	 */
	public void setFileUtil(PanaFileUtil fileUtil) {
		this.fileUtil = fileUtil;
	}

	/**
	 * �����̂��₢���킹���͉�ʕ\������<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g
	 * @param response
	 *            HTTP ���X�|���X
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
		Map<String, Object> model = createModel(request);
		PanaInquiryHousingForm inputForm = (PanaInquiryHousingForm) model.get("inputForm");

		// �����ΏۂƂȂ��L�[�l���p�����[�^�œn����Ă��Ȃ��ꍇ�A��O���X���[����B
		if (inputForm.getSysHousingCd() == null || inputForm.getSysHousingCd()[0] == "") {
			throw new RuntimeException("�V�X�e������CD���w�肳��Ă��܂���.");
		}

		// ���O�C�����[�U�[�̏����擾
		LoginUser loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);

		// loginUser��NULL�̏ꍇ�A�񃍃O�C����ԂƂȂ�B
		String logFlg = "0";
		if(loginUser == null){
			loginUser = FrontLoginUserUtils.getInstance(request).getAnonLoginUserInfo(request, response);
			logFlg = "1";
		}

		// �����̂��₢���킹���̎擾
		Housing housing = panaHousingManager.searchHousingPk(inputForm.getSysHousingCd()[0]);
		// �f�[�^�̑��݂��Ȃ��ꍇ�B
		if (housing == null) {
			return new ModelAndView("404", model);
		}
		// �{������������A���A�\�����A�摜�^�C�v�A�}�Ԃŏ����\�[�g��̉摜���擾
		List<HousingImageInfo> housingImageInfoList = getHousingImageInfoList(housing, logFlg);
		// Form �֏����l��ݒ肷��B
		if (housing != null) {
			inputForm.setDefaultDataHousing(housing, commonParameters, housingImageInfoList, model, fileUtil);
		}
		model.put("inputForm", inputForm);

		// �o���f�[�V���������s
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
		if (!inputForm.validate(errors)) {

			// �s���{�����X�g�̐ݒ�
			List<PrefMst> prefMstList = this.panaCommonManage.getPrefMstList();
			model.put("prefMstList", prefMstList);

			// �o���f�[�V�����G���[����
			model.put("errors", errors);

			return new ModelAndView("input", model);
		}

		// ���₢���킹���e�ڍ� ��ݒ�
		inputForm.setShowInquiryText(PanaStringUtils.encodeHtml(inputForm.getInquiryHeaderForm().getInquiryText()));
		// �s���{�������擾���ݒ肷��B
        inputForm.setPrefName(this.panaCommonManage.getPrefName(((PanaInquiryHeaderForm)inputForm.getInquiryHeaderForm()).getPrefCd()));

		model.put("inputForm", inputForm);

		// �擾�����f�[�^�������_�����O�w�֓n��
		return new ModelAndView("success", model);
	}

	/**
	 * ���N�G�X�g�p�����[�^���� outPutForm �I�u�W�F�N�g���쐬����B<br/>
	 * �������� outPutForm �I�u�W�F�N�g�� Map �Ɋi�[���ĕ��A����B<br/>
	 * key = �t�H�[���N���X���i�p�b�P�[�W�Ȃ��j�AValue = �t�H�[���I�u�W�F�N�g <br/>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^���ݒ肳�ꂽ�t�H�[���I�u�W�F�N�g���i�[���� Map �I�u�W�F�N�g
	 */
	private Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		// �y�[�W�����p�̃t�H�[���I�u�W�F�N�g���쐬
		PanaInquiryFormFactory factory = PanaInquiryFormFactory.getInstance(request);

		PanaInquiryHousingForm inputForm = factory.createPanaInquiryHousingForm(request);
		InquiryHeaderForm inquiryHeaderForm = factory.createInquiryHeaderForm(request);
		inputForm.setCommonInquiryForm(inquiryHeaderForm);

		model.put("inputForm", inputForm);

		return model;
	}

	/**
	 *�{������������A���A�\�����A�摜�^�C�v�A�}�Ԃŏ����\�[�g��̉摜���擾����B<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            inputForm
	 * @throws Exception
	 */
	private List<HousingImageInfo> getHousingImageInfoList(Housing housing, String logFlg) {

		// �{������������A���A�\�����A�摜�^�C�v�A�}�Ԃŏ����\�[�g���1�Ԗڂ̉摜��\��
		// ������{�����擾����B
		HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", housingInfo.getSysHousingCd());
		criteria.addWhereClause("roleId", null, DAOCriteria.IS_NULL, true);
		if(logFlg == "1"){
			criteria.addWhereClause("roleId", PanaCommonConstant.ROLE_ID_PUBLIC);
		}
		criteria.addOrderByClause("sortOrder");
		criteria.addOrderByClause("imageType");
		criteria.addOrderByClause("divNo");

		List<HousingImageInfo> housingImageInfoList = this.HousingImageInfoDAO.selectByFilter(criteria);

		return housingImageInfoList;
	}
}
