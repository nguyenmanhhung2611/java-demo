package jp.co.transcosmos.dm3.core.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.core.util.CreatePassword;
import jp.co.transcosmos.dm3.command.Command;


/**
 * �����_���p�X���[�h�̐�������.
 * <p>
 * �y���A���� View ���z<br/>
 * <ul>
 * <li>success</li>:����I���i���������p�X���[�h�� JSON ���X�|���X�j
 * </ul>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.27	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class RandomPwdCommand implements Command {

	/** �p�X���[�h�����N���X */
	protected CreatePassword createPassword;



	/**
	 * �p�X���[�h�����N���X��ݒ肷��B<br/>
	 * <br/>
	 * @param createPassword �p�X���[�h�����N���X
	 */
	public void setCreatePassword(CreatePassword createPassword) {
		this.createPassword = createPassword;
	}



	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// �p�X���[�h�𐶐�����B
		return new ModelAndView("success", "password", this.createPassword.getPassword());
	}

}
