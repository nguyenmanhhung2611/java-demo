package jp.co.transcosmos.dm3.webAdmin.memberInfo.command;

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
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.favorite.PanaFavoriteManageImpl;
import jp.co.transcosmos.dm3.corePana.model.member.PanaMypageUserManageImpl;
import jp.co.transcosmos.dm3.corePana.model.member.form.MemberFormFactory;
import jp.co.transcosmos.dm3.corePana.model.member.form.MemberSearchForm;
import jp.co.transcosmos.dm3.utils.CommonLogging;
import jp.co.transcosmos.dm3.validation.ValidationFailure;
import jp.co.transcosmos.dm3.webAdmin.utils.AdminLogging;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * ������̌����E�ꗗ
 *
 * �y���A���� View ���z
 *    �E"success" : ������������I��
 *
 * �S����          �C����       �C�����e
 * -------------- ----------- -----------------------------------------------------
 * tang.tianyun   2015.04.13  �V�K�쐬
 * Vinh.Ly        2015.08.26  Change the log output csv
 *
 * ���ӎ���
 *
 * </pre>
 */
public class MemberInfoListCommand implements Command {

	/** �������[�h (search = ���������A csv = CSV�o�͏����A delete = �폜����) */
	private String mode;

	/** ������p Model �I�u�W�F�N�g */
    protected PanaMypageUserManageImpl memberManager;

	/** ���ʏ���p Model �I�u�W�F�N�g */
	private PanaCommonManage panamCommonManager;

	/** ���C�ɓ������p Model �I�u�W�F�N�g */
	private PanaFavoriteManageImpl panaFavoriteManager;

    /** �P�y�[�W�̕\������ */
    private int rowsPerPage = 50;

    /** �y�[�W�̕\���� */
    private int visibleNavigationPageCount = 10;

    /** �F�ؗp���M���O�N���X */
    private CommonLogging authLogging;

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
	 * �������p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param memberManager ���ʏ���p Model �I�u�W�F�N�g
	 */
	public void setMemberManager(PanaMypageUserManageImpl memberManager) {
		this.memberManager = memberManager;
	}

	/**
	 * ���ʏ���p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param PanaCommonManage ���ʏ���p Model �I�u�W�F�N�g
	 */
	public void setPanamCommonManager(PanaCommonManage panamCommonManager) {
		this.panamCommonManager = panamCommonManager;
	}

	/**
	 * ���C�ɓ������p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param memberManager ���ʏ���p Model �I�u�W�F�N�g
	 */
	public void setPanaFavoriteManager(PanaFavoriteManageImpl panaFavoriteManager) {
		this.panaFavoriteManager = panaFavoriteManager;
	}

    /**
     * 1�y�[�W������\������ݒ肷��B<br>
     * @param rowsPerPage 1�y�[�W������\����
     */
    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    /**
     * �y�[�W�̕\������ݒ肷��B<br>
     * @param visibleNavigationPageCount �y�[�W��
     */
    public void setVisibleNavigationPageCount(int visibleNavigationPageCount) {
        this.visibleNavigationPageCount = visibleNavigationPageCount;
    }

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

    /**
	 * ����ꗗ�\������<br>
	 * <br>
	 *
	 * @param request
	 *            �N���C�A���g�����Http���N�G�X�g�B
	 * @param response
	 *            �N���C�A���g�ɕԂ�Http���X�|���X�B
	 */
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
        Map<String, Object> model = createModel(request);
        MemberSearchForm searchForm = (MemberSearchForm) model.get("searchForm");

        if (!"csv".equals(this.mode)) {
        	// �s���{���}�X�^���擾����
     	    List<PrefMst> prefMstList = this.panamCommonManager.getPrefMstList();
     		model.put("prefMstList", prefMstList);
        }

        // �o���f�[�V���������s
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();

        if (!searchForm.validate(errors)) {
            // �o���f�[�V�����G���[����
            model.put("errors", errors);
            return new ModelAndView("validFail", model);
        }

        if ("delete".equals(this.mode)) {
        	// ������f�[�^���폜��������
        	this.memberManager.delMyPageUser(searchForm.getUserId());
        	// ���C�ɓ�������폜����
        	this.panaFavoriteManager.delFavorite(searchForm.getUserId());
        }

        // CSV���O�o��
        if ("csv".equals(this.mode)) {
            // Log�o�͎��s
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String dt = sdf.format(new Date());
            String csvName = "kaiin_" + dt + ".csv";
            AdminUserInterface adminLoginInfo = (AdminLoginInfo) AdminLoginUserUtils.getInstance(request)
                    .getLoginUserInfo(request, response);
            String adminUserId = adminLoginInfo.getUserId().toString();
            String loginId = adminLoginInfo.getLoginId().toString();
            try {
                // �������s
                // searchMyPageUser() �́A�p�����[�^�œn���ꂽ form �̓��e�ŉ�����������A
                // �����������ʂ� form �Ɋi�[����B
                this.memberManager.searchMyPageUser(searchForm);
                // Log�o�͐���
                ((AdminLogging) this.authLogging).write("[" + loginId + "]�@�i" + csvName + "�j�o�͐���", adminUserId,
                        PanaCommonConstant.ADMIN_LOG_FC_MEMBER_LIST);
            } catch (Exception e) {
                // Log�o�͎��s
                ((AdminLogging) this.authLogging).write("[" + loginId + "]�@�i" + csvName + "�j�o�͎��s", adminUserId,
                        PanaCommonConstant.ADMIN_LOG_FC_MEMBER_LIST);
                throw e;
            }

        } else {
            // �������s
            // searchMyPageUser() �́A�p�����[�^�œn���ꂽ form �̓��e�ŉ�����������A
            // �����������ʂ� form �Ɋi�[����B
            // ���̃��\�b�h�̖߂�l�͊Y�������Ȃ̂ŁA���̒l�� view �w�֓n���p�����[�^�Ƃ��Đݒ肵�Ă���B
            model.put("hitcont", this.memberManager.searchMyPageUser(searchForm));
        }

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
        MemberFormFactory factory = MemberFormFactory.getInstance(request);
        MemberSearchForm searchForm = factory.createMypageUserSearchForm(request);

        // �P�y�[�W�ɕ\������s����ݒ肷��B
        searchForm.setRowsPerPage(this.rowsPerPage);
        // �y�[�W����ݒ肷��B
        searchForm.setVisibleNavigationPageCount(this.visibleNavigationPageCount);
        // �o���f�[�V������ʉ߂��A�����������s��ꂽ�ꍇ�AForm �� searchCommand �p�����[�^�� list ��ݒ�
        // ����B�@���̃p�����[�^�l�́A�ڍ׉�ʂ�ύX��ʂ� searchCommand �p�����[�^�Ƃ��Ĉ����p����A
        // �Ăь�����ʂ֕��A����ہAsearchCommand �p�����[�^�Ƃ��ēn�����B
        // �i���̐ݒ�ɂ��A�����ςł���΁A�ҏW��ʂ���߂������Ɍ����ςŉ�ʂ��\�������B�j
        searchForm.setSearchCommand("list");

        model.put("searchForm", searchForm);

        return model;
    }

}
