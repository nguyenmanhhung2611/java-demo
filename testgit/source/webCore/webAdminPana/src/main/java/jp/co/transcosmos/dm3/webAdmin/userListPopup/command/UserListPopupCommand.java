package jp.co.transcosmos.dm3.webAdmin.userListPopup.command;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.adminCore.mypage.command.MypageListCommand;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserSearchForm;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * ������̌����E�ꗗ
 *
 * �y���A���� View ���z
 *    �E"success" : ������������I��
 *
 * �S����         �C����      �C�����e
 * -------------- ----------- -----------------------------------------------------
 * tang.tianyun	  2015.04.13	�V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class UserListPopupCommand extends MypageListCommand {

	/** ���ʏ���p Model �I�u�W�F�N�g */
	private PanaCommonManage panamCommonManager;

	/**
	 * ���ʏ���p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param PanaCommonManage ���ʏ���p Model �I�u�W�F�N�g
	 */
	public void setPanamCommonManager(PanaCommonManage panamCommonManager) {
		this.panamCommonManager = panamCommonManager;
	}

	/** �y�[�W�̕\���� */
    private int visibleNavigationPageCount = 10;

    /**
     * �y�[�W�̕\������ݒ肷��B<br>
     * @param visibleNavigationPageCount �y�[�W��
     */
    public void setVisibleNavigationPageCount(int visibleNavigationPageCount) {
        this.visibleNavigationPageCount = visibleNavigationPageCount;
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
        // model �I�u�W�F�N�g���擾����B
        ModelAndView modelAndView = super.handleRequest(request, response);
        Map<String, Object> model = modelAndView.getModel();
        // �s���{���}�X�^���擾����
        List<PrefMst> prefMstList = this.panamCommonManager.getPrefMstList();
 		model.put("prefMstList", prefMstList);

 	    // ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
 		MypageUserSearchForm searchForm = (MypageUserSearchForm) modelAndView.getModel()
				.get("searchForm");
 		// �y�[�W����ݒ肷��B
        searchForm.setVisibleNavigationPageCount(this.visibleNavigationPageCount);
		return modelAndView;

    }
}
