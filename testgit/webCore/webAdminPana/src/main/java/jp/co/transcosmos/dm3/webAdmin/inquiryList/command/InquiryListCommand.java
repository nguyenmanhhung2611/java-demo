package jp.co.transcosmos.dm3.webAdmin.inquiryList.command;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.AdminLoginInfo;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.model.inquiry.PanaHousingInquiryManageImpl;
import jp.co.transcosmos.dm3.corePana.model.inquiry.dao.HousingInquiryDAO;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquirySearchForm;
import jp.co.transcosmos.dm3.utils.CommonLogging;
import jp.co.transcosmos.dm3.validation.ValidationFailure;
import jp.co.transcosmos.dm3.webAdmin.utils.AdminLogging;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * ���⍇���̌����E�ꗗ
 * ���͂��ꂽ�������������ɂ��⍇�������������A�ꗗ�\������B
 * ���������̓��͂ɖ�肪����ꍇ�A���������͍s��Ȃ��B
 *
 * �y���A���� View ���z
 *    �E"success" : ������������I��
 *    �E"validFail" : �o���f�[�V�����G���[
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �s�����C		2015.04.07	�V�K�쐬
 * Vinh.Ly		2015.08.20  Change the log output csv
 *
 * ���ӎ���
 *
 * </pre>
 */
public class InquiryListCommand extends jp.co.transcosmos.dm3.adminCore.inquiry.command.InquiryListCommand {
	/** �������[�h (search = ���������A csv = CSV�o�͏����A delete = �폜����) */
	private String mode;

	/** ���⍇�������e�i���X���s�� Model �I�u�W�F�N�g */
	private PanaHousingInquiryManageImpl panaInquiryManager;

	/** �⍇��񃁃��e�i���X�� model */
	protected HousingInquiryDAO housingInquiryDAO;

    /** �F�ؗp���M���O�N���X */
    private CommonLogging authLogging;

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
	 * �F�ؗp���M���O�N���X Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param authLogging �F�ؗp���M���O�N���X Model �I�u�W�F�N�g
	 */
    public void setAuthLogging(CommonLogging authLogging) {
        this.authLogging = authLogging;
    }

	/**
	 * �����⍇�����e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param inquiryManager �����⍇�����e�i���X�� model �I�u�W�F�N�g
	 */
	public void setPanaInquiryManager(PanaHousingInquiryManageImpl inquiryManager) {
		super.setInquiryManager(inquiryManager);
		this.panaInquiryManager = (PanaHousingInquiryManageImpl)inquiryManager;
	}

	/**
	 * �⍇��񃁃��e�i���X�� model��ݒ肷��B<br/>
	 * <br/>
	 * @param housingInquiryDAO �Z�b�g���� housingInquiryDAO
	 */
	public void setHousingInquiryDAO(HousingInquiryDAO housingInquiryDAO) {
		this.housingInquiryDAO = housingInquiryDAO;
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
	 * �����⍇����񃊃N�G�X�g����<br>
	 * �����⍇�����̃��N�G�X�g���������Ƃ��ɌĂяo�����B <br>
	 *
	 * @param request
	 *            �N���C�A���g�����Http���N�G�X�g�B
	 * @param response
	 *            �N���C�A���g�ɕԂ�Http���X�|���X�B
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// �����p�����[�^��M
        Map<String, Object> model = createModel(request);
        PanaInquirySearchForm searchForm = (PanaInquirySearchForm) model.get("searchForm");
        ModelAndView modelAndView = new ModelAndView();

		searchForm.setVisibleNavigationPageCount(this.visibleNavigationPageCount);

        // �o���f�[�V���������s
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();

        if (!searchForm.validate(errors)) {
        	// �o���f�[�V�����G���[����
			model.put("errors", errors);
			return new ModelAndView("validFail", model);
        }

		if ("search".equals(this.mode)) {
	        // �������s
	        // searchInquiry() �́A�p�����[�^�œn���ꂽ form �̓��e�ŕ����⍇�������������A
	        // �����������ʂ� form �Ɋi�[����B
	        // ���̃��\�b�h�̖߂�l�͊Y�������Ȃ̂ŁA���̒l�� view �w�֓n���p�����[�^�Ƃ��Đݒ肵�Ă���B
			modelAndView = super.handleRequest(request, response);


			// �o���f�[�V������ʉ߂��A�����������s��ꂽ�ꍇ�AForm �� command �p�����[�^�� list ��ݒ�
			// ����B�@���̃p�����[�^�l�́A�ڍ׉�ʂ�ύX��ʂ� searchCommand �p�����[�^�Ƃ��Ĉ����p����A
			// �Ăь�����ʂ֕��A����ہAcommand �p�����[�^�Ƃ��ēn�����B
			model.put("command", "list");
		} else if ("csv".equals(this.mode)) {
            // Log�o�͎��s
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String dt = sdf.format(new Date());
            String csvName = "toiawase_" + dt + ".csv";
            AdminUserInterface adminLoginInfo = (AdminLoginInfo) AdminLoginUserUtils.getInstance(request)
                    .getLoginUserInfo(request, response);
            String adminUserId = adminLoginInfo.getUserId().toString();
            String loginId = adminLoginInfo.getLoginId().toString();

            try {
                // �������s
                // �����������ʂ� form �Ɋi�[����B
                // ���̃��\�b�h�̖߂�l�͊Y�������Ȃ̂ŁA���̒l�� view �w�֓n���p�����[�^�Ƃ��Đݒ肵�Ă���B
                // CSV�o�͎��s
                this.panaInquiryManager.searchCsvHousing(searchForm, response);
                // Log�o�͐���
                ((AdminLogging) this.authLogging).write("[" + loginId + "]�@�i" + csvName + "�j�o�͐���", adminUserId,
                        PanaCommonConstant.ADMIN_LOG_FC_INQUIRY_LIST);
            } catch (Exception e) {
                // Log�o�͎��s
                ((AdminLogging) this.authLogging).write("[" + loginId + "]�@�i" + csvName + "�j�o�͎��s", adminUserId,
                        PanaCommonConstant.ADMIN_LOG_FC_INQUIRY_LIST);
                throw e;
            }

            return null;
		} else if ("delete".equals(this.mode)) {

			// �������f�[�^���폜��������
			this.panaInquiryManager.delInquiryAll(searchForm.getInquiryId());

			// �������s
			// �����������ʂ� form �Ɋi�[����B
			// ���̃��\�b�h�̖߂�l�͊Y�������Ȃ̂ŁA���̒l�� view �w�֓n���p�����[�^�Ƃ��Đݒ肵�Ă���B
			model.put("hitcont",
					this.panaInquiryManager.searchInquiry(searchForm));
			modelAndView = new ModelAndView("success", model);
		}

		return modelAndView;
	}

}
