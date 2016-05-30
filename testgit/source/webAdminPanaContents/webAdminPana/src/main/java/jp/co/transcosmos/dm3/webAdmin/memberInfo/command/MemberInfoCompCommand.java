package jp.co.transcosmos.dm3.webAdmin.memberInfo.command;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.adminCore.mypage.command.MypageCompCommand;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserForm;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.member.form.MemberInfoForm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * ������m�F���
 * ���N�G�X�g�p�����[�^�œn���ꂽ������̃o���f�[�V�������s���A�m�F��ʂ�\������B
 * �����o���f�[�V�����G���[�����������ꍇ�͓��͉�ʂƂ��ĕ\������B
 *
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *    �E"input" : �o���f�[�V�����G���[�ɂ��ē���
 *
 * �S����         �C����      �C�����e
 * -------------- ----------- -----------------------------------------------------
 * tang.tianyun	  2015.04.13	�V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class MemberInfoCompCommand extends MypageCompCommand {

	private static final Log log = LogFactory.getLog(MemberInfoCompCommand.class);

	/** ���ʏ���p Model �I�u�W�F�N�g */
	private PanaCommonManage panamCommonManager;

	/**
	 * @param panamCommonManager �Z�b�g���� panamCommonManager
	 */
	public void setPanamCommonManager(PanaCommonManage panamCommonManager) {
		this.panamCommonManager = panamCommonManager;
	}

	/**
	 * �����񊮗���ʕ\������<br>
	 *
	 * @param request
	 *            �N���C�A���g�����Http���N�G�X�g�B
	 * @param response
	 *            �N���C�A���g�ɕԂ�Http���X�|���X�B
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
        // model �I�u�W�F�N�g���擾����B
        ModelAndView modelAndView = super.handleRequest(request, response);
        Map<String, Object> model = modelAndView.getModel();

		MemberInfoForm inputForm = (MemberInfoForm) model.get("inputForm");

        String command = inputForm.getCommand();
        if (command != null && command.equals("redirect")){
        	return new ModelAndView("comp" , model);
        }


		if ("input".equals(modelAndView.getViewName())) {
			// �s���{���}�X�^���擾����
	 		List<PrefMst> prefMstList = this.panamCommonManager.getPrefMstList();
			model.put("prefMstList", prefMstList);
		}
		return modelAndView;
	}


	/**
	 * �p�X���[�h�̒ʒm���[���𑗐M����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param inputForm ���͒l
	 */
	@Override
	protected void sendPwdMail(HttpServletRequest request, MypageUserForm inputForm){

		// ���[���e���v���[�g���ݒ肳��Ă��Ȃ��ꍇ�̓��[���̒ʒm���s��Ȃ��B
		if (this.sendMypagePasswordTemplate == null) {
			log.warn("sendMypagePasswordTemplate is null.");
			return;
		}

		// �V�K�o�^�����[���𑗐M����B
		if (!"insert".equals(this.mode)){
			return;
		}

		// ���[���e���v���[�g�Ŏg�p����p�����[�^��ݒ肷��B
		this.sendMypagePasswordTemplate.setParameter("inputForm", inputForm);
		this.sendMypagePasswordTemplate.setParameter("commonParameters", CommonParameters.getInstance(request));

		// ���[�����M
		this.sendMypagePasswordTemplate.send();
	}
}