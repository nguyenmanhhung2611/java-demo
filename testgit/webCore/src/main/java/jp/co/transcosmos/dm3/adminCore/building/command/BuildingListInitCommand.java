package jp.co.transcosmos.dm3.adminCore.building.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.dao.DAO;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * �������̌����E�ꗗ������
 * �s���{�������擾
 * 
 * �y���A���� View ���z
 *    �E"success" : ������������I��
 * 
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.03.03	�V�K�쐬
 * 
 * ���ӎ���
 * 
 * </pre>
 */
public class BuildingListInitCommand implements Command {
	
	/** �s���{���}�X�^�擾�p DAO */
	protected DAO<PrefMst> prefMstDAO;
	
	/**
	 * �s���{���}�X�^�pDAO��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @return �s�撬���}�X�^�p DAO
	 */
	public void setPrefMstDAO(DAO<PrefMst> prefMstDAO) {
		this.prefMstDAO = prefMstDAO;
	}
	
	/**
	 * ������񃊃N�G�X�g����<br>
	 * �������̃��N�G�X�g���������Ƃ��ɌĂяo�����B <br>
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
		List<PrefMst> prefMstList = this.prefMstDAO.selectByFilter(null);
		model.put("prefMstList", prefMstList);
		return new ModelAndView("success", model);
	}

}
