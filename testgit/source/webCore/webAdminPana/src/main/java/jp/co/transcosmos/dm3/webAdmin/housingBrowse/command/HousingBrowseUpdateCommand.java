package jp.co.transcosmos.dm3.webAdmin.housingBrowse.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.ReformManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousing;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingBrowse;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingStatusForm;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingStatusInfo;
import jp.co.transcosmos.dm3.corePana.vo.MemberInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.login.UserRoleSet;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * �����{�����
 *
 * �y�����ꗗ�z
 *  �����ꗗ��ʂɁA�u�ҏW�v�{�^���������ɕ����{���@�i�ҏW�j��ʂ�\������
 *
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * Zhang.		2015.03.11	�V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class HousingBrowseUpdateCommand implements Command {

	/** ������񃁃��e�i���X�p Model */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/** �}�C�y�[�W���[�U�[�̏��Ǘ��p Model */
	private MypageUserManage mypageUserManager;

	/** ���t�H�[�������Ǘ��p Model */
	private ReformManage reformManager;

	/** ���ʃp�����[�^�I�u�W�F�N�g */
	private PanaCommonParameters commonParameters;

	/** ���ʃR�[�h�ϊ����� */
	private CodeLookupManager codeLookupManager;

    /** Panasonic�p�t�@�C�������֘A���� */
    private PanaFileUtil fileUtil;

	/**
	 * ������񃁃��e�i���X�p Model�icore�j ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param panaHousingManager
	 *            ������񃁃��e�i���X�p Model�icore�j
	 */
	public void setPanaHousingManager(
			PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
	}

	/**
	 * �}�C�y�[�W���[�U�[�̏��Ǘ��p Model�icore�j ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param mypageUserManager
	 *            �}�C�y�[�W���[�U�[�̏��Ǘ��p Model�icore�j
	 */
	public void setMypageUserManager(MypageUserManage mypageUserManager) {
		this.mypageUserManager = mypageUserManager;
	}

	/**
	 * ���t�H�[�������Ǘ��p Model�icore�j ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param reformManager
	 *            ���t�H�[�������Ǘ��p Model�icore�j
	 */
	public void setReformManager(ReformManage reformManager) {
		this.reformManager = reformManager;
	}

	/**
	 * ���ʃp�����[�^�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param commonParameters
	 *            ���ʃp�����[�^�I�u�W�F�N�g
	 */
	public void setCommonParameters(PanaCommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
     * Panasonic�p�t�@�C�������֘A���ʂ�ݒ肷��B<br/>
     * <br/>
     * @param fileUtil Panasonic�p�t�@�C�������֘A����
     */
    public void setFileUtil(PanaFileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

	/**
	 * �����{���i�ҏW�j��ʏ���<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g
	 * @param response
	 *            HTTP ���X�|���X
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
		Map<String, Object> model = createModel(request);
		PanaHousingStatusForm form = (PanaHousingStatusForm) model
				.get("inputForm");

		// �����ڍ׏��A�Z��f�f���A�����摜��񂩂�߂�ꍇ�Asubmit����form��
		// multipart/form-data�Ȃ̂ŁAform��sysHousingCd���擾�ł��Ȃ��Ȃ�B
		// �ł�����Aurl����sysHousingCd���擾����B
		String sysHousingCd = "";
		if (form.getSysHousingCd() == null) {
			sysHousingCd = request.getParameter("sysHousingCd");
			form.setSysHousingCd(sysHousingCd);
		} else {
			sysHousingCd = form.getSysHousingCd();
		}
		// ���O�`�F�b�N
		PanaHousing housing = (PanaHousing) this.panaHousingManager
				.searchHousingPk(sysHousingCd, true);
		if (housing == null) {
			throw new NotFoundException();
		}

		// ���[�U�[���擾
		UserRoleSet userRole = AdminLoginUserUtils.getInstance(request)
				.getLoginUserRole(request, response);

		// �}�C�y�[�W������̎擾
		MemberInfo memberInfo = getMemberInfo(housing);

		// ���t�H�[���v�����̎擾
		List<ReformPlan> reformPlanReslutList = this.reformManager
				.searchReformPlan(form.getSysHousingCd(), true);
		List<ReformPlan> reformPlanList = new ArrayList<ReformPlan>();

		for (int i = 0; i < 5; i++) {
			if (i >= reformPlanReslutList.size()
					|| reformPlanReslutList.get(i) == null) {
				reformPlanList.add(new ReformPlan());
			} else {
				reformPlanList.add(reformPlanReslutList.get(i));
			}
		}

		// ��ʍ��ڂ̊i�[
		model.put("housing", housing);
		model.put("memberInfo", memberInfo);
		model.put("reformPlanList", reformPlanList);
		PanaHousingBrowse housingBrowse = new PanaHousingBrowse(
				this.codeLookupManager);
		housingBrowse.setPanaHousingBrowse(model, userRole, commonParameters, fileUtil);

		if (!"uBack".equals(form.getCommand())) {
			form.setDefaultData(housing, userRole, memberInfo);
		}
		model.put("housingBrowse", housingBrowse);

		return new ModelAndView("success", model);
	}

	/**
	 * �}�C�y�[�W������̎擾<br>
	 * <br>
	 *
	 * @param housing
	 *            �l�̐ݒ��ƂȂ� Housing �I�u�W�F�N�g
	 *
	 * @return �擾�����}�C�y�[�W������
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	private MemberInfo getMemberInfo(Housing housing) throws Exception {

		MemberInfo memberInfo = new MemberInfo();

		// �����X�e�[�^�X���̃��[�U�[ID�̎擾
		JoinResult housingResult = housing.getHousingInfo();
		HousingStatusInfo housingStatusInfo = (HousingStatusInfo) housingResult
				.getItems().get("housingStatusInfo");
		String userId = housingStatusInfo.getUserId();

		// �}�C�y�[�W������̎擾
		JoinResult mypageResult = this.mypageUserManager
				.searchMyPageUserPk(userId);
		if (mypageResult != null) {
			memberInfo = (MemberInfo) mypageResult.getItems().get("memberInfo");
		}

		return memberInfo;
	}

	/**
	 * model �I�u�W�F�N�g���쐬����B<br/>
	 * <br/>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g
	 * @return �p�����[�^��ݒ肵�� Form ���i�[���� model �I�u�W�F�N�g�𕜋A����B
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		PanaHousingFormFactory factory = PanaHousingFormFactory
				.getInstance(request);
		// ���������A����сA��ʃR���g���[���p�����[�^���擾����B
		PanaHousingSearchForm searchForm = factory
				.createPanaHousingSearchForm(request);
		model.put("searchForm", searchForm);
		PanaHousingStatusForm requestForm = factory
				.createPanaHousingStatusForm(request);
		model.put("inputForm", requestForm);


		return model;
	}

}
