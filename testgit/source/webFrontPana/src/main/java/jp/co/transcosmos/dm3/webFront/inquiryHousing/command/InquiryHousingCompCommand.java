package jp.co.transcosmos.dm3.webFront.inquiryHousing.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryHeaderForm;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.inquiry.PanaHousingInquiryManageImpl;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryFormFactory;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryHousingForm;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.mail.ReplacingMail;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * �����̂��₢���킹�������
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
public class InquiryHousingCompCommand implements Command {

	/** ���ʏ���p Model �I�u�W�F�N�g */
	private PanaCommonManage panaCommonManage;

	/** �����⍇�������e�i���X���s�� Model �I�u�W�F�N�g */
	private PanaHousingInquiryManageImpl panaInquiryManager;

	/** ���������s�� Model �I�u�W�F�N�g */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;

	/** �V�K�ʒm���[���e���v���[�g */
	private ReplacingMail sendInquiryHousingMail;

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
	 * �����⍇�������e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param panaInquiryManager
	 *            �����⍇�������e�i���X�� model �I�u�W�F�N�g
	 */
	public void setPanaInquiryManager(PanaHousingInquiryManageImpl panaInquiryManager) {
		this.panaInquiryManager = panaInquiryManager;
	}

	/**
	 * ���������s�� Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param panaHousingManager
	 */
	public void setPanaHousingManager(PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
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
	 * @param sendInquiryHousingMail
	 *            �Z�b�g���� sendInquiryHousingMail
	 */
	public void setSendInquiryHousingMail(ReplacingMail sendInquiryHousingMail) {
		this.sendInquiryHousingMail = sendInquiryHousingMail;
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
	 * �������N�G�X�g���͉�ʕ\������<br>
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

		String command = inputForm.getCommand();
		if (command !=null && "redirect".equals(command)){
        	return new ModelAndView("comp" , model);
		}

		// ���O�C�����[�U�[�̏����擾
		LoginUser loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);
		String mypageUserId = null;
		String logFlg = "0";
		// loginUser��NULL�̏ꍇ�A�񃍃O�C����ԂƂȂ�B
		if (loginUser == null) {
			loginUser = FrontLoginUserUtils.getInstance(request).getAnonLoginUserInfo(request, response);
			logFlg = "1";
		} else {
			mypageUserId = (String)loginUser.getUserId();
		}
		// ���[�UID���擾
		String editUserId = (String)loginUser.getUserId();

		// �o���f�[�V���������s
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
		if (!inputForm.validate(errors)) {

			// �o���f�[�V�����G���[����
			model.put("errors", errors);

			if(errors != null){
				// �s���{�����X�g�̐ݒ�
				List<PrefMst> prefMstList = this.panaCommonManage.getPrefMstList();
				model.put("prefMstList", prefMstList);

				// �����̂��₢���킹���̎擾
				Housing housing = panaHousingManager.searchHousingPk(inputForm.getSysHousingCd()[0]);
				// �f�[�^�̑��݂��Ȃ��ꍇ�B
				if (housing == null) {
					return new ModelAndView("404", model);
				}

				// �{������������A���A�\�����A�摜�^�C�v�A�}�Ԃŏ����\�[�g��̉摜���擾
				List<HousingImageInfo> housingImageInfoList = getHousingImageInfoList(housing, logFlg);

				// Form �֏����l��ݒ肷��B
				if ( housing != null ) {
					inputForm.setDefaultDataHousing(housing, commonParameters, housingImageInfoList, model, fileUtil);
				}
				model.put("inputForm", inputForm);
			}

			return new ModelAndView("input", model);
		}

		// �������₢���킹��DB�o�^����
		String inquiryId = this.panaInquiryManager.addInquiry(inputForm, mypageUserId, editUserId);

		//���₢���킹�ԍ���ݒ�
		inputForm.setInquiryId(inquiryId);
		//�����ԍ���ݒ�
		inputForm.setHousingCd(inputForm.getSysHousingCd()[0]);

		// ���[���e���v���[�g�Ŏg�p����p�����[�^��ݒ肷��B
		this.sendInquiryHousingMail.setParameter("inputForm",inputForm);
		this.sendInquiryHousingMail.setParameter("commonParameters",CommonParameters.getInstance(request));

		// ���[�����M
		this.sendInquiryHousingMail.send();

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
