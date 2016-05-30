package jp.co.transcosmos.dm3.webFront.mypage.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserForm;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.member.form.MemberFormFactory;
import jp.co.transcosmos.dm3.corePana.util.AffiliateUtil;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.login.LoginUser;

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
 * zhang    	  2015.05.04	�V�K�쐬
 * Thi Tran       2015.10.16    Add 'janetUrl' attribute to model
 * Thi Tran       2015.10.22    Move 'janetUrl' to complete page
 * ���ӎ���
 *
 * </pre>
 */
public class MemberCompCommand extends jp.co.transcosmos.dm3.frontCore.mypage.command.MemberCompCommand {

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

		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
		Map<String, Object> model = createModel(request);
		MypageUserForm inputForm = (MypageUserForm) model.get("inputForm");

        // ������ʂŃ����[�h�����ꍇ�A�X�V�������Ӑ}�������s������肪��������B
        // ���̖�����������ׁAview ���� "success"�@���w�肷��Ǝ������_�C���N�g��ʂ��\�������B
        // ���̃��_�C���N�g��ʂ́Acommand �p�����[�^�� "redirect"�@�ɐݒ肵�Ċ�����ʂփ��N�G�X�g��
        // ���M����B
        // ����āAcommand = "redirect" �̏ꍇ�́A�c�a�X�V�͍s�킸�A������ʂ�\������B
        String command = inputForm.getCommand();
        if (command != null && "redirect".equals(command)){
        	return new ModelAndView("comp" , model);
        }
        
        // Display complete page with affiliate url if mode = comp
        if("comp".equals(this.mode)){
            // Get janet url
            LoginUser loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);
            PanaCommonParameters commonParamerers = PanaCommonParameters.getInstance(request);
            model.put("janetUrl", AffiliateUtil.getJanetURL(commonParamerers.getJanetUrl(),
                    loginUser.getUserId().toString(), commonParamerers.getJanetMemberSid()));
            return new ModelAndView("comp" , model);
        }
        // model �I�u�W�F�N�g���擾����B
        ModelAndView modelAndView = super.handleRequest(request, response);
        model = modelAndView.getModel();

        if ("input".equals(modelAndView.getViewName())) {
            // �s���{���}�X�^���擾����
            List<PrefMst> prefMstList = this.panamCommonManager.getPrefMstList();
            model.put("prefMstList", prefMstList);
        }
        // ���O�C�����̃Z�b�V�����i�[����
        setLoggedInUser(request, response);

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
		if ("insert".equals(this.mode)) {
			super.sendPwdMail(request, inputForm);
		}
	}

	/**
	 * model �I�u�W�F�N�g���쐬���A���N�G�X�g�p�����[�^���i�[���� form �I�u�W�F�N�g���i�[����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^��ݒ肵�� Form ���i�[���� model �I�u�W�F�N�g�𕜋A����B
	 */
	@Override
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		MemberFormFactory factory = MemberFormFactory.getInstance(request);

		model.put("inputForm", factory.createMypageUserForm(request));

		return model;

	}

	/**
	 * ���O�C�����̃Z�b�V�����i�[����<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param response �N���C�A���g�ɕԂ�Http���X�|���X�B
	 */
	private void setLoggedInUser(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// �X�V���[�h�̏ꍇ�A���O�C�������Z�b�V�����Ɋi�[
		if ("update".equals(this.mode)) {
			// ���O�C�����[�U�[�̋������擾����
			MypageUserInterface oldLoginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(
					request, response);

			// �}�C�y�[�W��������擾
	        JoinResult mypageUser = this.userManager.searchMyPageUserPk((String)oldLoginUser.getUserId());

	        CommonParameters commonParameters = CommonParameters.getInstance(request);

	        MypageUserInterface newLoginUser =
	        		(MypageUserInterface) mypageUser.getItems().get(commonParameters.getMemberDbAlias());

	        HttpSession session = request.getSession();
			session.setAttribute("loggedInUser", newLoginUser);
		}
	}
}