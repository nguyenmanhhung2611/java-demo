package jp.co.transcosmos.dm3.webAdmin.housingInfo.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * �H���̌���
 * �H�������擾
 *
 * �y���A���� View ���z
 *    �E"success" : ������������I��
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * fan			2015.04.06	�V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class RouteMstListCommand implements Command {

	/** �}�X�^��񃁃��e�i���X���s�� Model �I�u�W�F�N�g */
	private PanaCommonManage panamCommonManager;

	/**
	 * �}�X�^��񃁃��e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param panaCommonManager
	 *            �}�X�^��񃁃��e�i���X�� model �I�u�W�F�N�g
	 */
	public void setPanamCommonManager(PanaCommonManage panamCommonManager) {
		this.panamCommonManager = panamCommonManager;
	}

	/**
	 * �H���}�X�^�̌�������<br>
	 * �H���}�X�^���̃��N�G�X�g���������Ƃ��ɌĂяo�����B <br>
	 *
	 * @param request
	 *            �N���C�A���g�����Http���N�G�X�g�B
	 * @param response
	 *            �N���C�A���g�ɕԂ�Http���X�|���X�B
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// �����p�����[�^��M
        String prefCd = request.getParameter("prefCd");

		// �H���}�X�^���擾����
		List<RouteMst> routeMstList = panamCommonManager.getPrefCdToRouteMstList(prefCd);
		return new ModelAndView("success", "routeMstList", routeMstList);
	}
}
