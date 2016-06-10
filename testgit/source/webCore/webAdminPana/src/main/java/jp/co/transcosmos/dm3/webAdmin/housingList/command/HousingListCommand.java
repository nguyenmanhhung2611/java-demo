package jp.co.transcosmos.dm3.webAdmin.housingList.command;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.AdminLoginInfo;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.utils.CommonLogging;
import jp.co.transcosmos.dm3.validation.ValidationFailure;
import jp.co.transcosmos.dm3.webAdmin.utils.AdminLogging;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * �����ꗗ���
 * ���N�G�X�g�p�����[�^�œn���ꂽ�����ꗗ�̃o���f�[�V�������s���A�����ꗗ��\������B
 * �����o���f�[�V�����G���[�����������ꍇ�͓��͉�ʂƂ��ĕ\������B
 *
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *    �E"validFail" : �ُ�I��
 *
 * �S����        �C����       �C�����e
 * ------------ ----------- -----------------------------------------------------
 * gao.long     2015.03.17  �V�K�쐬
 * Duong.Nguyen 2015.08.26  Change simple log by admin log for exporting csv file
 *
 * ���ӎ���
 *
 * </pre>
 */
public class HousingListCommand implements Command {

	/** �������[�h (search = ���������A csv = CSV�o�͏����A delete = �폜����) */
	private String mode;

	/** �������p Model �I�u�W�F�N�g */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/** ���ʏ���p Model �I�u�W�F�N�g */
	private PanaCommonManage panamCommonManager;

    /** �F�ؗp���M���O�N���X */
    private CommonLogging authLogging;


    /**
     * �F�ؗp���M���O�N���X Model �I�u�W�F�N�g��ݒ肷��B<br/>
     * <br/>
     * 
     * @param authLogging
     *            �F�ؗp���M���O�N���X Model �I�u�W�F�N�g
     */
    public void setAuthLogging(CommonLogging authLogging) {
        this.authLogging = authLogging;
    }

	/** �P�y�[�W�̕\������ */
	private int rowsPerPage = 50;

	/**�ꗗ��ʂ̕\���y�[�W�� */
	private int visibleNavigationPageCount = 10;
	/**
	 * �������[�h��ݒ肷��<br/>
	 * <br/>
	 *
	 * @param mode
	 *            search = ���������A csv = CSV�o�͏����A delete = �폜����
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * �������p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param PanaHousingPartThumbnailProxy �������p Model �I�u�W�F�N�g
	 */
    public void setPanaHousingManager(PanaHousingPartThumbnailProxy panaHousingManager) {
        this.panaHousingManager = panaHousingManager;
    }

	/**
	 * ���ʏ���p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param PanaCommonManage
	 *            ���ʏ���p Model �I�u�W�F�N�g
	 */
	public void setPanamCommonManager(PanaCommonManage panamCommonManager) {
		this.panamCommonManager = panamCommonManager;
	}

	/**
	 * 1�y�[�W������\������ݒ肷��B<br>
	 *
	 * @param rowsPerPage
	 *            1�y�[�W������\����
	 */
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}
	/**
	 * �ꗗ��ʂ̕\���y�[�W����ݒ肷��B<br>
	 *
	 * @param rowsPerPage
	 *            �ꗗ��ʂ̕\���y�[�W��
	 */
	public void setVisibleNavigationPageCount(int visibleNavigationPageCount) {
		this.visibleNavigationPageCount = visibleNavigationPageCount;
	}

	/**
	 * �����ꗗ�\������<br>
	 * <br>
	 *
	 * @param request
	 *            �N���C�A���g�����Http���N�G�X�g�B
	 * @param response
	 *            �N���C�A���g�ɕԂ�Http���X�|���X�B
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		Map<String, Object> model = createModel(request);
		PanaHousingSearchForm searchForm = (PanaHousingSearchForm) model.get("searchForm");

		// ��ʍ��ڂ̏�����
		searchForm.setDefaultData(this.panamCommonManager.getPrefMstList(),model, false);

		// �o���f�[�V���������s
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();

		if (!searchForm.validate(errors)) {
			// �o���f�[�V�����G���[����
			model.put("errors", errors);
			return new ModelAndView("validFail", model);
		}

		if ("search".equals(this.mode)) {

			// �������s
			// �����������ʂ� form �Ɋi�[����B
			// ���̃��\�b�h�̖߂�l�͊Y�������Ȃ̂ŁA���̒l�� view �w�֓n���p�����[�^�Ƃ��Đݒ肵�Ă���B
			model.put("hitcont", this.panaHousingManager.searchHousing(searchForm));
		} else if ("csv".equals(this.mode)) {
            // Log�o�͎��s
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String dt = sdf.format(new Date());
            String csvName = "bukken_" + dt + ".zip";
            AdminUserInterface adminLoginInfo = (AdminLoginInfo) AdminLoginUserUtils.getInstance(request)
                    .getLoginUserInfo(request, response);
            String adminUserId = adminLoginInfo.getUserId().toString();
            String loginId = adminLoginInfo.getLoginId().toString();

            try {
                // CSV�o�͎��s
                panaHousingManager.searchCsvHousing(searchForm, response, this.panaHousingManager,
                        this.panamCommonManager);
                ((AdminLogging) this.authLogging).write("[" + loginId + "]�@�i" + csvName + "�j�o�͐���", adminUserId,
                        PanaCommonConstant.ADMIN_LOG_FC_HOUSING_LIST);
            } catch (Exception ex) {
                ((AdminLogging) this.authLogging).write("[" + loginId + "]�@�i" + csvName + "�j�o�͎��s", adminUserId,
                        PanaCommonConstant.ADMIN_LOG_FC_HOUSING_LIST);
                throw ex;
            }

            return null;
		} else if ("delete".equals(this.mode)) {
			PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);
			PanaHousingForm housingForm = factory.createPanaHousingForm();
			housingForm.setSysHousingCd(searchForm.getSysHousingCd());
			// �������f�[�^���폜��������
			this.panaHousingManager.delHousingInfo(housingForm, AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response).getUserId().toString());

			// �������s
			// �����������ʂ� form �Ɋi�[����B
			// ���̃��\�b�h�̖߂�l�͊Y�������Ȃ̂ŁA���̒l�� view �w�֓n���p�����[�^�Ƃ��Đݒ肵�Ă���B
			model.put("hitcont", this.panaHousingManager.searchHousing(searchForm));
		}

		// �o���f�[�V������ʉ߂��A�����������s��ꂽ�ꍇ�AForm �� command �p�����[�^�� list ��ݒ�
		// ����B�@���̃p�����[�^�l�́A�ڍ׉�ʂ�ύX��ʂ� searchCommand �p�����[�^�Ƃ��Ĉ����p����A
		// �Ăь�����ʂ֕��A����ہAcommand �p�����[�^�Ƃ��ēn�����B
		// �i���̐ݒ�ɂ��A�����ςł���΁A�ڍ׉�ʂ���߂������Ɍ����ςŉ�ʂ��\�������B�j
		model.put("command", "list");

		return new ModelAndView("success", model);
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
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);
		PanaHousingSearchForm searchForm = factory.createPanaHousingSearchForm(request);

		// �P�y�[�W�ɕ\������s����ݒ肷��B
		searchForm.setRowsPerPage(this.rowsPerPage);
		searchForm.setVisibleNavigationPageCount(this.visibleNavigationPageCount);

		model.put("searchForm", searchForm);

		return model;
	}
}
