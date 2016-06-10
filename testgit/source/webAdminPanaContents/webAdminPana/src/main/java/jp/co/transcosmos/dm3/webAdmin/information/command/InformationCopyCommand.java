package jp.co.transcosmos.dm3.webAdmin.information.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.adminCore.information.command.InformationInputCommand;
import jp.co.transcosmos.dm3.core.model.information.form.InformationForm;
import jp.co.transcosmos.dm3.core.vo.Information;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * ���m�点�R�s�[���
 *
 * �����̂��m�点����A�����擾���A�V�������m�点���쐬���邱�ƁB
 *
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * zh.xiaoting  2015.04.24	�V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class InformationCopyCommand extends InformationInputCommand {

	/**
	 * ���t�H�[�������͉�ʏ���<br>
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

		ModelAndView modelAndView = super.handleRequest(request, response);
		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		InformationForm inputForm = (InformationForm) modelAndView.getModel()
				.get("inputForm");
		// ���m�点�ԍ����󔒂ɐݒ�
		inputForm.setInformationNo("");

		// �o�^�������󔒂ɐݒ�
		Information information = (Information) modelAndView.getModel().get("informationInsDate");
		information.setInsDate(null);

		return modelAndView;
	}

}
