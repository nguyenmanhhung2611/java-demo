package jp.co.transcosmos.dm3.webFront.mypage.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.member.form.MemberFormFactory;
import jp.co.transcosmos.dm3.corePana.model.member.form.MemberInfoForm;

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
 * zhang          2015.04.29	�V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class MemberConfirmCommand extends jp.co.transcosmos.dm3.frontCore.mypage.command.MemberConfirmCommand {

	/** ���ʏ���p Model �I�u�W�F�N�g */
	private PanaCommonManage panamCommonManager;
	/** ������p Model(Core) �I�u�W�F�N�g */

	/**
	 * @param panamCommonManager �Z�b�g���� panamCommonManager
	 */
	public void setPanamCommonManager(PanaCommonManage panamCommonManager) {
		this.panamCommonManager = panamCommonManager;
	}


	/**
	 * ������ҏW��ʕ\������<br>
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

		if ("success".equals(modelAndView.getViewName())) {
			inputForm.setPrefName(this.panamCommonManager.getPrefName(inputForm.getPrefCd()));
			inputForm.setHopePrefName(this.panamCommonManager.getPrefName(inputForm.getHopePrefCd()));
		}
		// �s���{���}�X�^���擾����
 		List<PrefMst> prefMstList = this.panamCommonManager.getPrefMstList();
		model.put("prefMstList", prefMstList);

		model.put("inputForm", inputForm);

		return  modelAndView;
	}

	/**
	 * model �I�u�W�F�N�g���쐬���A���N�G�X�g�p�����[�^���i�[���� form �I�u�W�F�N�g���i�[����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^��ݒ肵�� Form ���i�[���� model �I�u�W�F�N�g�𕜋A����B
	 */
	@Override
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<>();

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		MemberFormFactory factory = MemberFormFactory.getInstance(request);

		model.put("inputForm", factory.createMypageUserForm(request));

		return model;

	}


}