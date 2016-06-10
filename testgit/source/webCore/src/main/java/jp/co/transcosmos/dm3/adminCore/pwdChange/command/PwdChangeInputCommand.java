package jp.co.transcosmos.dm3.adminCore.pwdChange.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;

/**
 * �Ǘ����[�U�[�p�X���[�h�ύX���͉��.
 * <p>
 * �Z�b�V�������烍�O�C�������擾���A�p�X���[�h�̍ŏI�X�V������ʏo�͂���B<br/>
 * <br/>
 * �y���A���� View ���z<br/>
 * <ul>
 * <li>success<li>:���͉�ʕ\��
 * </ul>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.23	Shamaison ���Q�l�ɐV�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class PwdChangeInputCommand implements Command {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<>();


		// ���O�C�������Z�b�V��������擾
		AdminUserInterface userInfo = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);

		// �p�X���[�h�̍ŏI�X�V�����擾
		model.put("pwdChangeDate", userInfo.getLastPasswdChange());

		return new ModelAndView("success", model);
	}

}
