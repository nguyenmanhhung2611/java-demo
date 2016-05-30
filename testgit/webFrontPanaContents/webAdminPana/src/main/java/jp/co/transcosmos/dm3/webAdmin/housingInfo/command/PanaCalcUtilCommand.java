package jp.co.transcosmos.dm3.webAdmin.housingInfo.command;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.corePana.util.PanaCalcUtil;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * �،v�Z���擾
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
public class PanaCalcUtilCommand implements Command {


	/**
	 * �،v�Z�̏���<br>
	 * �،v�Z�̃��N�G�X�g���������Ƃ��ɌĂяo�����B <br>
	 *
	 * @param request
	 *            �N���C�A���g�����Http���N�G�X�g�B
	 * @param response
	 *            �N���C�A���g�ɕԂ�Http���X�|���X�B
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String wkarea = request.getParameter("area");
		String areaCon = "";
		if(!StringValidateUtil.isEmpty(wkarea)){
			try{
				areaCon =String.valueOf(PanaCalcUtil.calcTsubo(BigDecimal.valueOf(Double.valueOf(wkarea))));
			}catch (Exception e){
				areaCon=null;
			}
		}
		Map<String,String> map = null;
	    map = new HashMap<String,String>();
	    map.put("areaCon", areaCon);


		return new ModelAndView("success", "areaCon", map);
	}
}
