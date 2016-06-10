package jp.co.transcosmos.dm3.webAdmin.housingStatus.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.BuildingManage;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.ReformManage;
import jp.co.transcosmos.dm3.corePana.model.building.form.PanaBuildingForm;
import jp.co.transcosmos.dm3.corePana.model.building.form.PanaBuildingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousing;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingBrowse;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingForm;
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
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * �X�e�[�^�X�ύX�������
 * <p>
 * �y�V�K�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B</li>
 * <li>�o���f�[�V����������I�������ꍇ�A�X�e�[�^�X��V�K�o�^����B</li>
 * <li>�܂��A���J�敪������l�̏ꍇ�A�X�e�[�^�X���J�敪�����V�K�o�^����B</li>
 * </ul>
 * <br/>
 * �y�X�V�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B</li>
 * <li>�o���f�[�V����������I�������ꍇ�A�X�e�[�^�X�����X�V����B</li>
 * <li>�����A�X�V�Ώۃf�[�^�����݂��Ȃ��ꍇ�A�X�V�������p���ł��Ȃ��̂ŊY��������ʂ�\������B</li>
 * </ul>
 * <br/>
 * �y���A���� View ���z<br/>
 * <ul>
 * <li>success</li>:����I���i���_�C���N�g�y�[�W�j
 * <li>input</li>:�o���f�[�V�����G���[�ɂ��ē���
 * <li>notFound</li>:�Y���f�[�^�����݂��Ȃ��ꍇ�i�X�V�����̏ꍇ�j
 * <li>comp</li>:������ʕ\��
 * </ul>
 * <p>
 *
 * <pre>
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * Liu.yandong 2015.03.11	�V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class HousingStatusCompCommand implements Command {

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

	private BuildingManage buildingManager;
	/** �������[�h (insert = �V�K�o�^�����A update=�X�V����) */
	private String mode;

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
	 * �X�e�[�^�X�����e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param buildingManager
	 *            �X�e�[�^�X�����e�i���X�� model �I�u�W�F�N�g
	 */
	public void setBuildingManager(BuildingManage buildingManage) {
		this.buildingManager = buildingManage;
	}

	/**
	 * �������[�h��ݒ肷��<br/>
	 * <br/>
	 *
	 * @param mode
	 *            "insert" = �V�K�o�^�����A"update" = �X�V����
	 */
	public void setMode(String mode) {
		this.mode = mode;
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
	 * �������̒ǉ��A�ύX����<br>
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

		// ���O�C�����[�U�[�̏����擾����B�@�i�^�C���X�^���v�̍X�V�p�j
		AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request)
				.getLoginUserInfo(request, response);

		// ���O�C�����[�U�[�̃��[���������擾����B�@�i�^�C���X�^���v�̍X�V�p�j
		UserRoleSet userRoleSet = AdminLoginUserUtils.getInstance(request)
				.getLoginUserRole(request, response);


		// ������ʂŃ����[�h�����ꍇ�A�X�V�������Ӑ}�������s������肪��������B
		// ���̖�����������ׁAview ���� "success"�@���w�肷��Ǝ������_�C���N�g��ʂ��\�������B
		// ���̃��_�C���N�g��ʂ́Acommand �p�����[�^�� "redirect"�@�ɐݒ肵�Ċ�����ʂփ��N�G�X�g��
		// ���M����B
		// ����āAcommand = "redirect" �̏ꍇ�́A�c�a�X�V�͍s�킸�A������ʂ�\������B
		String command = form.getCommand();
		if (command != null
				&& ("iRedirect".equals(command) || "uRedirect".equals(command))) {

			if ("iRedirect".equals(command)) {
				form.setCommand("insert");
			} else if ("uRedirect".equals(command)) {
				form.setCommand("update");
			}
			form.setSysHousingCd(form.getHousingCd());
			return new ModelAndView("comp", model);
		}

		// �o���f�[�V�����`�F�b�N
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
		if ("insert".equals(form.getCommand())) {
			if (!form.validateIns(errors, userRoleSet.hasRole("admin"))) {
				model.put("errors", errors);
				model.put("inputForm", form);
				return new ModelAndView("insErr", model);
			}
		} else {
			// ���O�`�F�b�N
			PanaHousing housing = (PanaHousing) this.panaHousingManager
					.searchHousingPk(form.getSysHousingCd(), true);
			if (housing == null) {
				throw new NotFoundException();
			}

			JoinResult housingInfo = housing.getHousingInfo();
			HousingStatusInfo housingStatusInfo = (HousingStatusInfo) housingInfo
					.getItems().get("housingStatusInfo");

			if (!form.validateUpd(errors, userRoleSet.hasRole("admin"),
					housingStatusInfo)) {
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
				housingBrowse.setPanaHousingBrowse(model, userRole,
						this.commonParameters, fileUtil);

				model.put("housingBrowse", housingBrowse);
				model.put("errors", errors);
				model.put("inputForm", form);

				return new ModelAndView("updErr", model);
			}
		}

		// �e�폈�������s
		execute(request, response, form, loginUser);

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
	 * �����̐U�蕪���Ǝ��s���s���B<br/>
	 * <br/>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g
	 * @param inputForm
	 *            ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
	 * @param loginUser
	 *            �^�C���X�^���v�X�V���Ɏg�p���郍�O�C�����[�U�[���
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException
	 *                �X�V�Ώۂ����݂��Ȃ��ꍇ
	 */
	protected void execute(HttpServletRequest request,
			HttpServletResponse response, PanaHousingStatusForm inputForm,
			AdminUserInterface loginUser) throws Exception, NotFoundException {

		if ("insert".equals(this.mode)) {
			insert(request, inputForm, loginUser);
		} else if ("update".equals(this.mode)) {
			panaHousingManager.updateHousingStatus(inputForm,
					(String) loginUser.getUserId());

		} else {
			// �z�肵�Ă��Ȃ��������[�h�̏ꍇ�A��O���X���[����B
			throw new RuntimeException("�z��O�̏������[�h�ł�.");
		}

	}

	/**
	 * �V�K�o�^����<br/>
	 * �����œn���ꂽ���e�ŕ����������L�̃e�[�u���ɒǉ�����B<br/>
	 * �E�����X�e�[�^�X���<br/>
	 * �E������{���<br/>
	 * �E������{���<br/>
	 * <br/>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g
	 * @param inputForm
	 *            ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
	 * @param loginUser
	 *            �^�C���X�^���v�X�V���Ɏg�p���郍�O�C�����[�U�[���
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	protected void insert(HttpServletRequest request,
			PanaHousingStatusForm inputForm, AdminUserInterface loginUser)
			throws Exception {

		// ������{���̓o�^
		String sysBuildingCd = addPanaBuildingInfo(request, inputForm,
				loginUser);

		// ������{���̓o�^
		addPanaHousingInfo(request, inputForm, loginUser, sysBuildingCd);

		// �����X�e�[�^�X���̓o�^
		addPanaHousingStatus(request, inputForm, loginUser);

	}

	/**
	 * ������{���o�^����<br/>
	 * <br/>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g
	 * @param inputForm
	 *            ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
	 * @param loginUser
	 *            �^�C���X�^���v�X�V���Ɏg�p���郍�O�C�����[�U�[���
	 * @return sysBuildingCd �V�X�e������CD
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	private String addPanaBuildingInfo(HttpServletRequest request,
			PanaHousingStatusForm inputForm, AdminUserInterface loginUser)
			throws Exception {

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		PanaBuildingFormFactory factory = PanaBuildingFormFactory
				.getInstance(request);
		// ���N�G�X�g�p�����[�^���i�[���� form �I�u�W�F�N�g�𐶐�����B
		PanaBuildingForm form = factory.createPanaBuildingForm(request);

		// �������CD
		form.setHousingKindCd(inputForm.getHousingKindCd());

		String sysBuildingCd = this.buildingManager.addBuilding(form,
				(String) loginUser.getUserId());

		return sysBuildingCd;
	}

	/**
	 * ������{���o�^����<br/>
	 * <br/>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g
	 * @param inputForm
	 *            ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
	 * @param loginUser
	 *            �^�C���X�^���v�X�V���Ɏg�p���郍�O�C�����[�U�[���
	 * @param sysBuildingCd
	 *            �V�X�e������CD
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	private void addPanaHousingInfo(HttpServletRequest request,
			PanaHousingStatusForm inputForm, AdminUserInterface loginUser,
			String sysBuildingCd) throws Exception {

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		PanaHousingFormFactory factory = PanaHousingFormFactory
				.getInstance(request);
		// ���N�G�X�g�p�����[�^���i�[���� form �I�u�W�F�N�g�𐶐�����B
		PanaHousingForm housingForm = factory.createPanaHousingForm(request);

		// �V�X�e������CD
		housingForm.setSysBuildingCd(sysBuildingCd);
		// �\���p������
		housingForm.setDisplayHousingName(inputForm.getDisplayHousingName());
		// �������N�G�X�g�����Ώۃt���O
		housingForm.setRequestFlg("1");

		// �f�[�^��ǉ�
		String sysHousingCd = this.panaHousingManager.addHousing(housingForm,
				(String) loginUser.getUserId());

		// �f�[�^���C��
		housingForm.setSysHousingCd(sysHousingCd);
		housingForm.setHousingCd(sysHousingCd);
		this.panaHousingManager.updateHousing(housingForm,
				(String) loginUser.getUserId());

		inputForm.setSysHousingCd(sysHousingCd);
	}

	/**
	 * �����X�e�[�^�X���o�^����<br/>
	 * <br/>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g
	 * @param inputForm
	 *            ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
	 * @param loginUser
	 *            �^�C���X�^���v�X�V���Ɏg�p���郍�O�C�����[�U�[���
	 * @return sysBuildingCd �V�X�e������CD
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	private void addPanaHousingStatus(HttpServletRequest request,
			PanaHousingStatusForm inputForm, AdminUserInterface loginUser)
			throws Exception {

		// �����X�e�[�^�X���̓o�^
		this.panaHousingManager.addHousingStatus(inputForm);
	}

	/**
	 * model �I�u�W�F�N�g���쐬���A���N�G�X�g�p�����[�^���i�[���� form �I�u�W�F�N�g���i�[����B<br/>
	 * <br/>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^��ݒ肵�� Form ���i�[���� model �I�u�W�F�N�g�𕜋A����B
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<>();

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		PanaHousingFormFactory factory = PanaHousingFormFactory
				.getInstance(request);
		PanaHousingStatusForm form = factory
				.createPanaHousingStatusForm(request);

		model.put("inputForm", form);

		// ���������A����сA��ʃR���g���[���p�����[�^���擾����B
		PanaHousingSearchForm searchForm = factory
				.createPanaHousingSearchForm(request);
		model.put("searchForm", searchForm);

		return model;

	}

}
