package jp.co.transcosmos.dm3.login.command.v3;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.command.Command;


/**
 * <pre>
 * Ver 3 �F�ؑΉ��p���O�C�������\���R�}���h�N���X
 * ���O�C����ʂ������\������ꍇ�Ɏg�p����R�}���h�N���X�B
 * LoginCheckFilter �� �ʏ� Filter �Ƃ��Ďg�p����ꍇ�A���O�C����ʂ̃��_�C���N�g��Ƃ���
 * �g�p����B
 *
 * �S����            �C����               �C�����e
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.05.07  �V�K�쐬
 *
 * </pre>
*/
public class InitLoginCommand  implements Command {

	// ���_�C���N�g�� URL �p�����[�^��
	protected String redirectURLParameter = "redirectURL";

	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("init", "redirectURL", getRedirectURL(request));
	}
	
	
    /**
     * ���_�C���N�g��URL�̎擾<br/>
     * ���N�G�X�g�E�p�����[�^����A���_�C���N�g�� URL ���擾����B
     * <br/>
     * @param request HTTP���N�G�X�g
     * @return ���_�C���N�g�� URL
     */
    protected String getRedirectURL(HttpServletRequest request) {
        return request.getParameter(this.redirectURLParameter);
    }

}
