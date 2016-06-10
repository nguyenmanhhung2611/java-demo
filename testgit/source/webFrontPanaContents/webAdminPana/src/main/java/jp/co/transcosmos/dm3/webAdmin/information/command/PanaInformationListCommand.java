package jp.co.transcosmos.dm3.webAdmin.information.command;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.InformationManage;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.information.InformationManageImpl;
import jp.co.transcosmos.dm3.core.model.information.form.InformationForm;
import jp.co.transcosmos.dm3.core.model.information.form.InformationFormFactory;
import jp.co.transcosmos.dm3.core.model.information.form.InformationSearchForm;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.utils.CommonLogging;
import jp.co.transcosmos.dm3.validation.ValidationFailure;
import jp.co.transcosmos.dm3.webAdmin.utils.AdminLogging;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * ���m�点�̌����E�ꗗ
 * ���͂��ꂽ�������������ɂ��m�点�����������A�ꗗ�\������B
 * ���������̓��͂ɖ�肪����ꍇ�A���������͍s��Ȃ��B
 *
 * �y���A���� View ���z
 *    �E"success" : ������������I��
 *    �E"validFail" : �o���f�[�V�����G���[
 *
 * �S����        �C����       �C�����e
 * ------------ ----------- -----------------------------------------------------
 * zh.xiaoting  2015.04.24  �V�K�쐬
 * Duong.Nguyen 2015.08.26  Change simple log by admin log for exporting csv file
 *
 * ���ӎ���
 *
 * </pre>
 */
public class PanaInformationListCommand implements Command {

	/** �������[�h (search = ���������A csv = CSV�o�͏����A delete = �폜����) */
	private String mode;

	/** ���m�点�����e�i���X���s�� Model �I�u�W�F�N�g */
	private InformationManage informationManager;

	/** �P�y�[�W�̕\������ */
	private int rowsPerPage = 50;

    /** �F�ؗp���M���O�N���X */
    private CommonLogging authLogging;

	/** �y�[�W�̕\���� */
    private int visibleNavigationPageCount = 10;

	/**
	 * �������[�h��ݒ肷��<br/>
	 * <br/>
	 * @param mode
	 *            search = ���������A csv = CSV�o�͏����A delete = �폜����
	 */
	public void setMode(String mode) {
		this.mode = mode;
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
	 * �F�ؗp���M���O�N���X Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param authLogging �F�ؗp���M���O�N���X Model �I�u�W�F�N�g
	 */
    public void setAuthLogging(CommonLogging authLogging) {
        this.authLogging = authLogging;
    }

    /**
     * �y�[�W�̕\������ݒ肷��B<br>
     * @param visibleNavigationPageCount �y�[�W��
     */
    public void setVisibleNavigationPageCount(int visibleNavigationPageCount) {
        this.visibleNavigationPageCount = visibleNavigationPageCount;
    }

	/**
	 * ���m�点��񃊃N�G�X�g����<br>
	 * ���m�点���̃��N�G�X�g���������Ƃ��ɌĂяo�����B <br>
	 *
	 * @param request
	 *            �N���C�A���g�����Http���N�G�X�g�B
	 * @param response
	 *            �N���C�A���g�ɕԂ�Http���X�|���X�B
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// �����p�����[�^��M
        Map<String, Object> model = createModel(request);
        InformationSearchForm form = (InformationSearchForm) model.get("searchForm");
        InformationForm inputForm = (InformationForm) model.get("inputForm");

        // �o���f�[�V���������s
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();

        if (!form.validate(errors)) {
        	// �o���f�[�V�����G���[����
			model.put("errors", errors);
			return new ModelAndView("validFail", model);
        }

        if ("delete".equals(this.mode)) {
        	// ���m�点���f�[�^���폜��������
        	this.informationManager.delInformation(inputForm);
        }

        // CSV���O�o��
        if ("csv".equals(this.mode)) {
            // Prepare data for admin log
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String dt = sdf.format(new Date());
            String csvName = "oshirase_" + dt + ".csv";
            AdminUserInterface adminUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);
            String loginID = adminUser.getLoginId();
            String adminUserId = String.valueOf(adminUser.getUserId());

            try {
                informationManager.searchAdminInformation(form);
                ((AdminLogging) this.authLogging).write("[" + loginID + "]�@�i" + csvName + "�j�o�͐���", adminUserId,
                        PanaCommonConstant.ADMIN_LOG_FC_INFO_LIST);
            } catch (Exception ex) {
                ((AdminLogging) this.authLogging).write("[" + loginID + "]�@�i" + csvName + "�j�o�͎��s", adminUserId,
                        PanaCommonConstant.ADMIN_LOG_FC_INFO_LIST);
                throw ex;
            }
        } else {
            // �������s
            // searchInformation() �́A�p�����[�^�œn���ꂽ form �̓��e�ł��m�点�����������A
            // �����������ʂ� form �Ɋi�[����B
            // ���̃��\�b�h�̖߂�l�͊Y�������Ȃ̂ŁA���̒l�� view �w�֓n���p�����[�^�Ƃ��Đݒ肵�Ă���B
            model.put("hitcont", this.informationManager.searchAdminInformation(form));
        }


		// �o���f�[�V������ʉ߂��A�����������s��ꂽ�ꍇ�AForm �� command �p�����[�^�� list ��ݒ�
		// ����B�@���̃p�����[�^�l�́A�ڍ׉�ʂ�ύX��ʂ� searchCommand �p�����[�^�Ƃ��Ĉ����p����A
		// �Ăь�����ʂ֕��A����ہAcommand �p�����[�^�Ƃ��ēn�����B
		model.put("command", "list");

		return new ModelAndView("success", model);
	}

	/**
	 * ���N�G�X�g�p�����[�^���� Form �I�u�W�F�N�g���쐬����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^���ݒ肳�ꂽ�t�H�[���I�u�W�F�N�g
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		InformationFormFactory factory = InformationFormFactory.getInstance(request);
		InformationSearchForm searchForm = factory.createInformationSearchForm(request);
		InformationForm inputForm = factory.createInformationForm(request);

        // �y�[�W���̕\�������� From �ɐݒ肷��B
        // ���̒l�́A�t���[�����[�N�̃y�[�W�������g�p����B
		searchForm.setRowsPerPage(this.rowsPerPage);
		// �y�[�W����ݒ肷��B
        searchForm.setVisibleNavigationPageCount(this.visibleNavigationPageCount);
        model.put("searchForm", searchForm);
        model.put("inputForm", inputForm);

		return model;

	}

	/**
	 * ���m�点�����e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param informationManager ���m�点�����e�i���X�� model �I�u�W�F�N�g
	 */
	public void setInformationManager(InformationManageImpl informationManager) {
		this.informationManager = informationManager;
	}

}
