package jp.co.transcosmos.dm3.core.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.vo.AddressMst;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * �s�撬���̌���
 * �s�撬�������擾
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
public class AddressMstListCommand implements Command {
	
	/** �s�撬���}�X�^�擾�p DAO */
	protected DAO<AddressMst> addressMstDAO;
	
	/** �G���A��\�������O����ꍇ�A1�ɐݒ肷��@�i�f�t�H���g 0�j */
	protected String areaNotDsp = "0";
	
	/**
	 * �s�撬���}�X�^�pDAO��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @return �s�撬���}�X�^�p DAO
	 */
	public void setAddressMstDAO(DAO<AddressMst> addressMstDAO) {
		this.addressMstDAO = addressMstDAO;
	}
	
	/**
	 * ��ݒ肷��B<br/>
	 * <br/>
	 * @param areaNotDsp 
	 */
	public void setAreaNotDsp(String areaNotDsp) {
		this.areaNotDsp = areaNotDsp;
	}
	
	/**
	 * �s�撬���̌�������<br>
	 * �s�撬�����̃��N�G�X�g���������Ƃ��ɌĂяo�����B <br>
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
        
		// �s�撬���}�X�^���擾����
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("prefCd", prefCd);
		if ("1".equals(this.areaNotDsp)) {
			criteria.addWhereClause("areNotDsp", this.areaNotDsp);
		}
		List<AddressMst> addressMstList = this.addressMstDAO.selectByFilter(criteria);
				
		return new ModelAndView("success", "addressMstList", addressMstList);
	}


}
