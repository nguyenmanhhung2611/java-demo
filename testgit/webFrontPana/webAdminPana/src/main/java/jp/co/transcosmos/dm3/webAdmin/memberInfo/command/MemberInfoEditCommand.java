package jp.co.transcosmos.dm3.webAdmin.memberInfo.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.adminCore.mypage.command.MypageInputCommand;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.member.PanaMypageUserManageImpl;
import jp.co.transcosmos.dm3.corePana.model.member.form.MemberInfoForm;
import jp.co.transcosmos.dm3.corePana.model.member.form.MemberSearchForm;
import jp.co.transcosmos.dm3.corePana.vo.MemberQuestion;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * ������ҏW���
 * ���N�G�X�g�p�����[�^�œn���ꂽ������̃o���f�[�V�������s���A�ҏW��ʂ�\������B
 * �����o���f�[�V�����G���[�����������ꍇ�͓��͉�ʂƂ��ĕ\������B
 *
 * �y���A���� View ���z
 *    �E"success" : ������������I��
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
public class MemberInfoEditCommand extends MypageInputCommand {

	/** ���ʏ���p Model �I�u�W�F�N�g */
	private PanaCommonManage panamCommonManager;

	/** ������p Model �I�u�W�F�N�g */
	private PanaMypageUserManageImpl mypageUserManager;


	/**
	 * ���ʏ���p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param PanaCommonManage ���ʏ���p Model �I�u�W�F�N�g
	 */
	public void setPanamCommonManager(PanaCommonManage panamCommonManager) {
		this.panamCommonManager = panamCommonManager;
	}

	/**
	 * @param mypageUserManager �Z�b�g���� mypageUserManager
	 */
	public void setMypageUserManager(PanaMypageUserManageImpl mypageUserManager) {
		this.mypageUserManager = mypageUserManager;
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

        // form�擾
		MemberInfoForm inputForm = (MemberInfoForm) model.get("inputForm");
		MemberSearchForm searchForm = (MemberSearchForm) model.get("searchForm");

		String command = inputForm.getCommand();

		// �������[�h�𔻒f����B
		if ("insert".equals(this.mode) && !"back".equals(command)) {
				// ���W�I�{�^��������
				inputForm.setMailSendFlg("1");
				inputForm.setEntryRoute("001");
				inputForm.setLockFlg("0");
		}
		if ("update".equals(this.mode) && !"back".equals(command)){
				// �}�C�y�[�W�A���P�[�g���擾���s���B
				execute(inputForm, searchForm);
				inputForm.setPassword(null);
				inputForm.setPasswordChk(null);

		}
		if ("getAddress".equals(this.mode)) {
			// �Z����������
			// �o���f�[�V���������s
			List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
			if (!inputForm.validate(errors, this.mode)) {
				// �o���f�[�V�����G���[���́A�G���[���� model �ɐݒ肵�A���͉�ʂ�\������B
				model.put("errors", errors);
			} else {
				// �s�撬���}�X�^���擾����
				String[] zipMst = panamCommonManager.getZipToAddress(inputForm.getZip());
				// �X�֔ԍ��̑΂���Z�����Ȃ��ꍇ
				if(zipMst == null || zipMst[0] != "0"){
					ValidationFailure vf = new ValidationFailure(
							"housingInfoZipInput","", "",  null);
		            errors.add(vf);
		            model.put("errors", errors);
				}
				inputForm.setPrefCd(zipMst[1]);
				inputForm.setAddress(panamCommonManager.getAddressName(zipMst[2]));
			}
		}

		// �s���{���}�X�^���擾����
 		List<PrefMst> prefMstList = this.panamCommonManager.getPrefMstList();
		model.put("prefMstList", prefMstList);


		return modelAndView;
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
	private void execute(MemberInfoForm inputForm, MemberSearchForm searchForm) throws Exception, NotFoundException  {

		// �}�C�y�[�W�A���P�[�g���擾
		List<MemberQuestion> memberQuestionList = this.mypageUserManager.searchMemberQuestionPk(searchForm.getUserId());

		// Form���Z�b�g����
		inputForm.setMemberQuestion(memberQuestionList);
	}

}