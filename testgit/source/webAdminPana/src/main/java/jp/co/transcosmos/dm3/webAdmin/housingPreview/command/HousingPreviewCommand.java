package jp.co.transcosmos.dm3.webAdmin.housingPreview.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.building.form.PanaBuildingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousing;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingDetailed;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPreview;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformFormFactory;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingInspection;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * �����v���r���[���
 * ���N�G�X�g�p�����[�^�œn���ꂽ�����ڍׂ̃o���f�[�V�������s���A�����v���r���[��ʂ�\������B
 *
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *
 * �S����       �C����     �C�����e
 * ------------ ----------- -----------------------------------------------------
 * Y.Zhang     2015.05.06  �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class HousingPreviewCommand implements Command {

	/** �������[�h (detail = �����P�́Areform = ���t�H�[���v��������) */
	private String mode;

	/** �������p Model �I�u�W�F�N�g */
	private PanaHousingPartThumbnailProxy panaHousingManage;

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
	 * �����v���r���[��ʕ\������<br>
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
		PanaHousingPreview outPutForm = (PanaHousingPreview) model.get("outPutForm");

		// �V�X�e������CD
		String sysHousingCd = request.getParameter("sysHousingCd");

		// ���t�H�[��CD
		String sysReformCd = request.getParameter("sysReformCd");

		// �J�ڌ����Id���擾
		String prePageId = request.getParameter("pageId");

		// �������[�h��ݒ肷��B
		if ("reformPlan".equals(prePageId) || "reformDtl".equals(prePageId) || "reformImg".equals(prePageId)) {
			this.mode = "reform";
		}

		// �������[�h��ݒ肷��B
		outPutForm.setMode(this.mode);

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

				if (!"reformPlan".equals(prePageId)) {

					// ���t�H�[��CD��null
					throw new RuntimeException("�V�X�e�����t�H�[��CD���w�肳��Ă��܂���.");
				}

			}
		}

		// �������s���s���B
		execute(outPutForm, request, response);

		// �v���r���[���e��ݒ肷��
		setPreviewData(prePageId, request, outPutForm);

		model.put("outPutForm", outPutForm);

		return new ModelAndView("success", model);
	}

	/**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� Form �փv���r���[���e��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param prePage �J�ڌ����
	 * @param request HTTP ���N�G�X�g
	 * @param outPutForm �o�͗pForm �I�u�W�F�N�g
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	private void setPreviewData(String prePageId,
			HttpServletRequest request,
			PanaHousingPreview outPutForm) throws Exception {

		if (StringUtils.isEmpty(prePageId)) {
			throw new RuntimeException("�J�ڌ���ʂ��w�肳��Ă��܂���B");
		}

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		PanaHousingFormFactory housingFormFactory = PanaHousingFormFactory.getInstance(request);

		PanaBuildingFormFactory buildingFormFactory = PanaBuildingFormFactory.getInstance(request);

		ReformFormFactory reformFormFactory = ReformFormFactory.getInstance(request);

		// ������{���̏ꍇ
		if ("housingInfo".equals(prePageId)) {
			outPutForm.setHousingInfoPreviewData(housingFormFactory.createPanaHousingInfoForm(request), outPutForm);
		}

		// �����ڍ׏��̏ꍇ
		if ("housingDtl".equals(prePageId)) {
			outPutForm.setHousingDtlPreviewData(housingFormFactory.createPanaHousingDtlInfoForm(request), outPutForm);
		}

		// ���������̏ꍇ
		if ("housingSpecialty".equals(prePageId)) {
			outPutForm.setHousingSpecialtyPreviewData(housingFormFactory.createPanaHousingSpecialtyForm(request), outPutForm);
		}

		// �������߃|�C���g���̏ꍇ
		if ("recommendPoint".equals(prePageId)) {
			outPutForm.setRecommendPointPreviewData(housingFormFactory.createRecommendPointForm(request), outPutForm);
		}

		// �Z��f�f���̏ꍇ
		if ("housingInspection".equals(prePageId)) {
			outPutForm.setHousingInspectionPreviewData(housingFormFactory.createPanaHousingInspectionForm(request), outPutForm);
		}

		// ���t�H�[�����̏ꍇ
		if ("reformPlan".equals(prePageId)) {
			outPutForm.setReformPlanPreviewData(reformFormFactory.createRefromInfoForm(request), outPutForm);
		}

		// ���t�H�[���ڍ׏��̏ꍇ
		if ("reformDtl".equals(prePageId)) {
			outPutForm.setReformDtlPreviewData(reformFormFactory.createReformDtlForm(request), outPutForm);
		}

		// ���t�H�[���摜���̏ꍇ
		if ("reformImg".equals(prePageId)) {
			outPutForm.setReformImgPreviewData(reformFormFactory.createRefromImgForm(request), outPutForm);
		}

		// �����摜���̏ꍇ
		if ("housingImage".equals(prePageId)) {
			outPutForm.setHousingImagePreviewData(housingFormFactory.createPanaHousingImageInfoForm(request), outPutForm);
		}

		// �n����̏ꍇ
		if ("housingLandmark".equals(prePageId)) {
			outPutForm.setHousingLandmarkPreviewData(buildingFormFactory.createBuildingLandmarkForm(request), outPutForm);
		}

		// �v���r���[�̐V���t���O�ƍX�V����ݒ肷��B
		outPutForm.setFreshAndUpdDate(outPutForm);

		// ��ʂ̕\���t���O��ݒ肷��B
		outPutForm.setDisplayFlg();

		// meta����ݒ肷��B
		outPutForm.setMeta();

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

		PanaHousingPreview requestForm = new PanaHousingPreview(this.codeLookupManager, this.commonParameters, this.panaFileUtil);

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
		outPutForm.setPreviewFlg(true);

		// ���O�C�����[�U�[�̏����擾
		AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);

		// ���O�C�����[�U�[�̏��擾�����ꍇ
		if (loginUser != null) {

			// ����t���O
			outPutForm.setMemberFlg(true);

		} else {
			// ����t���O
			outPutForm.setMemberFlg(false);
		}

		// �����ڍ�Map
		Map<String, Object> detailedMap = new HashMap<String, Object>();

		// ���������擾����B
		PanaHousing housing = (PanaHousing) this.panaHousingManage.searchHousingPk(outPutForm.getSysHousingCd(), true);

		if (housing == null) {

			// ������񂪖���
			throw new NotFoundException();
		}

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

		if ("reform".equals(this.mode) && !"reformPlan".equals(request.getParameter("pageId")) && reformPlan == null) {

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

		// Form���Z�b�g����B
		outPutForm.setDefaultData(detailedMap);
	}
}
