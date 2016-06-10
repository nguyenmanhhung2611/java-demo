package jp.co.transcosmos.dm3.webFront.mypage.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserForm;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.member.PanaMypageUserManageImpl;
import jp.co.transcosmos.dm3.corePana.model.member.form.MemberFormFactory;
import jp.co.transcosmos.dm3.corePana.model.member.form.MemberInfoForm;
import jp.co.transcosmos.dm3.corePana.vo.MemberQuestion;

import org.springframework.web.servlet.ModelAndView;

/**
 * �}�C�y�[�W��������͉��.
 * <p>
 * �y�V�K�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���͉�ʂ�\������B</li>
 * </ul>
 * <br/>
 * �y�X�V�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^�ő��M���ꂽ�l������B�i��L�[�l�̂݁j</li>
 * <li>���N�G�X�g�p�����[�^�iuserId�j�ɊY������}�C�y�[�W��������擾����B</li>
 * <li>�擾�����l����͉�ʂɕ\������B</li>
 * </ul>
 * <br/>
 * �y���N�G�X�g�p�����[�^�icommand�j �� "back"�̏ꍇ�z�i�m�F��ʂ���̕��A�̏ꍇ�j<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^�ő��M���ꂽ�l������B</li>
 * <li>�󂯎�����l����͉�ʂɕ\������B</li>
 * </ul>
 * <br/>
 * �y���A���� View ���z<br/>
 * <ul>
 * <li>success<li>:������������I��
 * </ul>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * Zhang		2015.04.29	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class MemberInputCommand extends jp.co.transcosmos.dm3.frontCore.mypage.command.MemberInputCommand implements Command {

	/** ���ʏ���p Model �I�u�W�F�N�g */
	private PanaCommonManage panamCommonManager;

	/**
	 * ���ʏ���p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param PanaCommonManage ���ʏ���p Model �I�u�W�F�N�g
	 */
	public void setPanamCommonManager(PanaCommonManage panamCommonManager) {
		this.panamCommonManager = panamCommonManager;
	}

	/**
	 * ��������͉�ʕ\������<br>
	 * <br>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	*/
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

        // model �I�u�W�F�N�g���擾����B
        ModelAndView modelAndView = super.handleRequest(request, response);
        Map<String, Object> model = modelAndView.getModel();

        // form�擾
		MemberInfoForm inputForm = (MemberInfoForm) model.get("inputForm");

		// ���O�C�����[�U�[�̏����擾����B
		MypageUserInterface loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(
				request, response);

		String command = inputForm.getCommand();

		// �������[�h�𔻒f����B
		if ("insert".equals(this.mode) && !"back".equals(command)) {
			// ���W�I�{�^��������
			inputForm.setRefCd(request.getParameter("refCd"));
			// ���[���z�M�̏����l�ݒ�
			inputForm.setMailSendFlg(PanaCommonConstant.SEND_FLG_1);
		}

		if ("update".equals(this.mode) && !"back".equals(command)){

			// �}�C�y�[�W�A���P�[�g���擾���s���B
			execute(inputForm, loginUser.getUpdUserId());
			inputForm.setPassword(null);
			inputForm.setPasswordChk(null);

		}

		// �s���{���}�X�^���擾����
 		List<PrefMst> prefMstList = this.panamCommonManager.getPrefMstList();
		model.put("prefMstList", prefMstList);

		return new ModelAndView("success", model);
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


		// ���̓t�H�[���̎󂯎��́Acommand �p�����[�^�̒l�ɂ���ĈقȂ�B
		// command �p�����[�^���n����Ȃ��ꍇ�A���͉�ʂ̏����\���Ƃ݂Ȃ��A��̃t�H�[���𕜋A����B
		// command �p�����[�^�� "back" ���n���ꂽ�ꍇ�A���͊m�F��ʂ���̕��A���Ӗ�����B
		// ���̏ꍇ�̓��N�G�X�g�p�����[�^���󂯎��A���͉�ʂ֏����\������B
		// �����A"back" �ȊO�̒l���n���ꂽ�ꍇ�Acommand �p�����[�^���n����Ȃ��ꍇ�Ɠ����Ɉ����B

		MypageUserForm inputForm = factory.createMypageUserForm(request);
		String command = inputForm.getCommand();

		if (command != null && command.equals("back")){
			model.put("inputForm", inputForm);
		} else {
			model.put("inputForm", factory.createMypageUserForm());
		}

		return model;

	}

	/**
	 * �}�C�y�[�W�A���P�[�g���擾���s���B<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException
	 *                �X�V�Ώۂ����݂��Ȃ��ꍇ
	 */
	private void execute(MemberInfoForm inputForm, String userId) throws Exception, NotFoundException  {

		// �}�C�y�[�W�A���P�[�g���擾
		List<MemberQuestion> memberQuestionList =
				((PanaMypageUserManageImpl) this.userManager).searchMemberQuestionPk(userId);

		// Form���Z�b�g����
		inputForm.setMemberQuestion(memberQuestionList);
	}

}
