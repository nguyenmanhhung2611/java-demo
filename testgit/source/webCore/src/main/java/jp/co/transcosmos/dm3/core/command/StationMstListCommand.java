package jp.co.transcosmos.dm3.core.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.building.dao.StationMstListDAO;
import jp.co.transcosmos.dm3.core.vo.StationMst;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * �w�}�X�^�̌���
 * �w�����擾
 * 
 * �y���A���� View ���z
 *    �E"success" : ������������I��
 * 
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.03.10	�V�K�쐬
 * 
 * ���ӎ���
 * 
 * </pre>
 */
public class StationMstListCommand implements Command {
	
	/** �w�}�X�^�擾�p DAO */
	protected StationMstListDAO stationMstListDAO;

	/**
	 * �w�}�X�^�擾�p DAO��ݒ肷��B<br/>
	 * <br/>
	 * @param stationMstListDAO �w�}�X�^�擾�p DAO
	 */
	public void setStationMstListDAO(StationMstListDAO stationMstListDAO) {
		this.stationMstListDAO = stationMstListDAO;
	}
	
	
	/**
	 * �w�}�X�^�̌�������<br>
	 * �w���̃��N�G�X�g���������Ƃ��ɌĂяo�����B <br>
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
        String routeCd = request.getParameter("routeCd");
        
		// �s�撬���}�X�^���擾����
        Object[] params = new Object[] {prefCd, routeCd};
		List<StationMst> stationMstList = this.stationMstListDAO.listStationMst(params);

		return new ModelAndView("success", "stationMstList", stationMstList);
	}


}
