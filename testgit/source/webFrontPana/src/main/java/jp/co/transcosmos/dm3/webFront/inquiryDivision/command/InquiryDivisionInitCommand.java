package jp.co.transcosmos.dm3.webFront.inquiryDivision.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.login.command.v3.LoginCommand;

import org.springframework.web.servlet.ModelAndView;

/**
 * �����̂��₢���킹�������
 *
 * <pre>
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �s�����C    2015.04.23   �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class InquiryDivisionInitCommand extends LoginCommand {

	/**
	 * �������N�G�X�g���͉�ʕ\������<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g
	 * @param response
	 *            HTTP ���X�|���X
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		model.put("sysHousingCd", request.getParameter("sysHousingCd"));


		return new ModelAndView("success", model);
	}


}
