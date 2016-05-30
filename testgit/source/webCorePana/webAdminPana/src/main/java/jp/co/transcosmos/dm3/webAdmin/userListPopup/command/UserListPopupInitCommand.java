package jp.co.transcosmos.dm3.webAdmin.userListPopup.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * ������̌����E�ꗗ������
 * �s���{�������擾
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
public class UserListPopupInitCommand implements Command {

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

	/**
	 * �����񃊃N�G�X�g����<br>
	 * ������̃��N�G�X�g���������Ƃ��ɌĂяo�����B <br>
	 *
	 * @param request
	 *            �N���C�A���g�����Http���N�G�X�g�B
	 * @param response
	 *            �N���C�A���g�ɕԂ�Http���X�|���X�B
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// �����p�����[�^��M
        Map<String, Object> model = new HashMap<String, Object>();

		// �s���{���}�X�^���擾����
 		List<PrefMst> prefMstList = this.panamCommonManager.getPrefMstList();
 		model.put("prefMstList", prefMstList);

		return new ModelAndView("success", model);
	}

}