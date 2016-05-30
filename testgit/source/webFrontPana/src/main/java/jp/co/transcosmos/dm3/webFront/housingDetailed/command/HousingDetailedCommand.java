package jp.co.transcosmos.dm3.webFront.housingDetailed.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.core.util.RecentlyCookieUtils;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.RecentlyInfoManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousing;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingDetailed;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingInspection;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * �����ڍ׉��
 * ���N�G�X�g�p�����[�^�œn���ꂽ�����ڍׂ̃o���f�[�V�������s���A�����ڍ׉�ʂ�\������B
 *
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *
 * �S����       �C����     �C�����e
 * ------------ ----------- -----------------------------------------------------
 * gao.long     2015.04.13  �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class HousingDetailedCommand implements Command {

	/** �������[�h (detail = �����P�́Areform = ���t�H�[���v��������) */
	private String mode;

	/** �������p Model �I�u�W�F�N�g */
	private PanaHousingPartThumbnailProxy panaHousingManage;

	/** �ŋߌ������������Ǘ����� Model �I�u�W�F�N�g */
	private RecentlyInfoManage recentlyInfoManage;

	/** ���ʃp�����[�^�I�u�W�F�N�g */
	private PanaCommonParameters commonParameters;

	/** ���ʃR�[�h�ϊ����� */
	private CodeLookupManager codeLookupManager;

	/** Panasonic�p�t�@�C�������֘A����Util */
	private PanaFileUtil panaFileUtil;

	/** Panasonic�p���ʏ��擾 */
	private PanaCommonManage panamCommonManager;

	/**
	 * �������[�h��ݒ肷��<br/>
	 * <br/>
	 *
	 * @param mode "detail" = �����P�� "reform" = ���t�H�[���v��������
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * �������p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param PanaHousingManage �������p Model �I�u�W�F�N�g
	 */
	public void setPanaHousingManage(PanaHousingPartThumbnailProxy panaHousingManage) {
		this.panaHousingManage = panaHousingManage;
	}

	/**
	 * �ŋߌ����������p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param RecentlyInfoManage �ŋߌ����������p Model �I�u�W�F�N�g
	 */
	public void setRecentlyInfoManage(RecentlyInfoManage recentlyInfoManage) {
		this.recentlyInfoManage = recentlyInfoManage;
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
	 * ���ʃR�[�h�ϊ��I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param codeLookupManager
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * Panasonic�p�t�@�C�������֘A����Util�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param panaFileUtil
	 */
	public void setPanaFileUtil(PanaFileUtil panaFileUtil) {
		this.panaFileUtil = panaFileUtil;
	}

	/**
	 * Panasonic�p���ʏ��擾�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param panamCommonManager
	 */
	public void setPanamCommonManager(PanaCommonManage panamCommonManager) {
		this.panamCommonManager = panamCommonManager;
	}

	/**
	 * �����ڍ׉�ʕ\������<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g
	 * @param response
	 *            HTTP ���X�|���X
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
		Map<String, Object> model = createModel(request);
		PanaHousingDetailed outPutForm = (PanaHousingDetailed) model.get("outPutForm");

		// �������[�h��ݒ肷��B
		outPutForm.setMode(this.mode);

		// �V�X�e������CD
		String sysHousingCd = request.getParameter("sysHousingCd");

		// ���t�H�[��CD
		String sysReformCd = request.getParameter("sysReformCd");

		if (!StringUtils.isEmpty(sysHousingCd)) {

			outPutForm.setSysHousingCd(sysHousingCd);

		} else {

			// �V�X�e������CD��null
			throw new RuntimeException("�V�X�e������CD���w�肳��Ă��܂���.");
		}

		if ("reform".equals(this.mode)) {

			if (!StringUtils.isEmpty(sysReformCd)) {

				outPutForm.setReformCd(sysReformCd);

			} else {

				// ���t�H�[��CD��null
				throw new RuntimeException("�V�X�e�����t�H�[��CD���w�肳��Ă��܂���.");

			}
		}

		// �{�y�[�WURL
		outPutForm.setCurrentUrl(request.getRequestURI());

		// �������s���s���B
		try {
			execute(outPutForm, request, response);

		} catch (NotFoundException e) {
			return new ModelAndView("404");
		}

		model.put("outPutForm", outPutForm);

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

		Map<String, Object> model = new HashMap<>();

		PanaHousingDetailed requestForm = new PanaHousingDetailed(this.codeLookupManager, this.commonParameters, this.panaFileUtil);

		model.put("outPutForm", requestForm);

		return model;
	}

	/**
	 * �������s���s���B<br/>
	 * <br/>
	 *
	 * @param outPutForm
	 *            ���͒l���i�[���ꂽ outPutForm �I�u�W�F�N�g
	 * @param response
	 * @param request
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException
	 *                �X�V�Ώۂ����݂��Ȃ��ꍇ
	 */
	@SuppressWarnings("unchecked")
	private void execute(PanaHousingDetailed outPutForm, HttpServletRequest request, HttpServletResponse response) throws Exception, NotFoundException {

		// �v���r���[�t���O
		outPutForm.setPreviewFlg(false);

		// ���O�C�����[�U�[�̏����擾
		MypageUserInterface mypageUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);

		String userId = null;

		// ���O�C�����[�U�[�̏��擾�����ꍇ
		if (mypageUser != null) {
			// ���[�UID���擾
			userId = (String) mypageUser.getUserId();

			// ����t���O
			outPutForm.setMemberFlg(true);

		} else {

			// �������O�C�������擾
			LoginUser loginUser = FrontLoginUserUtils.getInstance(request).getAnonLoginUserInfo(request, response);

			if (loginUser != null) {
				// ���[�UID���擾
				userId = (String) loginUser.getUserId();

				// ����t���O
				outPutForm.setMemberFlg(false);
			}
		}

		// �����ڍ�Map
		Map<String, Object> detailedMap = new HashMap<String, Object>();

		// ���������擾����B
		PanaHousing housing = (PanaHousing) this.panaHousingManage.searchHousingPk(outPutForm.getSysHousingCd());

		if (housing == null) {

			// ������񂪖���
			throw new NotFoundException();
		}

		// �ŋߌ���������񌋉ʃ��X�g���擾����B
		List<PanaHousing> recentlyInfoList = this.recentlyInfoManage.searchRecentlyInfo(userId);

		// ���t�H�[���֘A�����擾����B
		List<Map<String, Object>> reforms = housing.getReforms();

		// ���t�H�[���v������񃊃X�g
		List<ReformPlan> reformPlanList = new ArrayList<ReformPlan>();

		// ���t�H�[���v�������
		ReformPlan reformPlan = null;

		// ���t�H�[���ڍ׏�񃊃X�g
		List<ReformDtl> reformDtlList = null;

		// ���t�H�[���摜��񃊃X�g
		List<ReformImg> reformImgList = null;

		// ���t�H�[���֘A�����J��Ԃ��A���t�H�[��CD�𔻒f����B
		for (Map<String, Object> reform : reforms) {

			// ���t�H�[���v�������
			ReformPlan temReformPlan = (ReformPlan) reform.get("reformPlan");

			// ���t�H�[��CD���\���p�̃��t�H�[��CD�̏ꍇ
			if (outPutForm.getReformCd() != null && outPutForm.getReformCd().equals(temReformPlan.getSysReformCd())) {

				// ���t�H�[���v�������擾����B
				reformPlan = (ReformPlan) reform.get("reformPlan");

				// ���t�H�[���ڍ׏�񃊃X�g�擾����B
				reformDtlList = (List<ReformDtl>) reform.get("dtlList");

				// ���t�H�[���摜��񃊃X�g�擾����B
				reformImgList = (List<ReformImg>) reform.get("imgList");
			}

			// ���t�H�[���v���������擾����B
			reformPlanList.add(temReformPlan);
		}

		if ("reform".equals(this.mode) && reformPlan == null) {

			// ���t�H�[���v������񂪖���
			throw new NotFoundException();
		}

		// �����C���X�y�N�V�������X�g�擾����B
		List<HousingInspection> housingInspectionList = housing.getHousingInspections();

		// �s���{�����X�g���擾����B
		List<PrefMst> prefMstList = this.panamCommonManager.getPrefMstList();

		// ���������v�b�g����B
		detailedMap.put("housing", housing);

		// ���t�H�[���v������񃊃X�g���v�b�g����B
		detailedMap.put("reformPlanList", reformPlanList);

		// ���t�H�[���v���������v�b�g����B
		detailedMap.put("reformPlan", reformPlan);

		// ���t�H�[���ڍ׏�񃊃X�g���v�b�g����B
		detailedMap.put("reformDtlList", reformDtlList);

		// ���t�H�[���摜��񃊃X�g���v�b�g����B
		detailedMap.put("reformImgList", reformImgList);

		// �����C���X�y�N�V�������X�g���v�b�g����B
		detailedMap.put("housingInspectionList", housingInspectionList);

		// �s���{�����X�g���v�b�g����B
		detailedMap.put("prefMstList", prefMstList);

		// �ŋߌ���������񌋉ʃ��X�g���v�b�g����B
		detailedMap.put("recentlyInfoList", recentlyInfoList);

		// Form���Z�b�g����B
		outPutForm.setDefaultData(detailedMap);

		// ��ʂ̕\���t���O��ݒ肷��B
		outPutForm.setDisplayFlg();

		// �ŋߌ��������e�[�u���Ƀf�[�^��o�^
		Map<String, Object> paramMap = new HashMap<String, Object>();

		// �ŋߌ����������̃V�X�e������CD
		paramMap.put("sysHousingCd", outPutForm.getSysHousingCd());

		// ���[�U�[ID
		paramMap.put("userId", userId);

		// �ŋߌ��������e�[�u�����猏�����擾����B
		int recentlyInfoCnt = this.recentlyInfoManage.getRecentlyInfoCnt(userId);

		// �ŋߌ��������e�[�u���Ƀf�[�^��o�^
		int ret = this.recentlyInfoManage.addRecentlyInfo(paramMap, userId);

		// �ǉ������ꍇ
		if (ret == 1) {

			// �o�^�����A�ŋߌ��������̌�����ێ�����Cookie�̒l + 1
			RecentlyCookieUtils.getInstance(request).setRecentlyCount(request, response, recentlyInfoCnt + 1);
		}
	}
}
