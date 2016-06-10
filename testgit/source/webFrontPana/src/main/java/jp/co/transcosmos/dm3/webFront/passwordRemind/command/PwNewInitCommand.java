package jp.co.transcosmos.dm3.webFront.passwordRemind.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.corePana.model.member.PanaMypageUserManageImpl;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * �p�X���[�h���}�C���_�[
 * �p�X���[�h���}�C���_�[��ʂ�\������B
 *
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *
 * �S����       �C����     �C�����e
 * ------------ ----------- -----------------------------------------------------
 * ��     2015.04.24  �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class PwNewInitCommand implements Command {

	/** ������p Model �I�u�W�F�N�g */
	private PanaMypageUserManageImpl panaMypageUserManager;

	/**
	 * @param mypageUserManager �Z�b�g���� mypageUserManager
	 */
	public void setPanaMypageUserManager(PanaMypageUserManageImpl panaMypageUserManager) {
		this.panaMypageUserManager = panaMypageUserManager;
	}

	/**
	 * �p�X���[�h���}�C���_�[��ʕ\������<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g
	 * @param response
	 *            HTTP ���X�|���X
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// �擾�����f�[�^�������_�����O�w�֓n��
		Map<String, Object> model = new HashMap<String, Object>();

		String remindId = request.getParameter("remindId");

		// �L�������`�F�b�N
		Boolean dateFlg = false;
		try{
			dateFlg = this.panaMypageUserManager.dateCheck(remindId);
		}catch(NotFoundException e){
			return new ModelAndView("404", model);
		}

		if(!dateFlg){

			return new ModelAndView("error", model);
		}

		model.put("remindId", remindId);
		return new ModelAndView("success", model);
	}

}
