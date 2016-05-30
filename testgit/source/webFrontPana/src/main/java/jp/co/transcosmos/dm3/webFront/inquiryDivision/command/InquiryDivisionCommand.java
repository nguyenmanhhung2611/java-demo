package jp.co.transcosmos.dm3.webFront.inquiryDivision.command;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.corePana.model.inquiry.PanaHousingInquiryManageImpl;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.command.v3.LoginCommand;
import jp.co.transcosmos.dm3.login.v3.LockException;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.mail.ReplacingMail;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * �����̂��₢���킹�������
 *
 * <pre>
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �s�����C    2015.04.23   �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class InquiryDivisionCommand extends LoginCommand {

	/** �����⍇�������e�i���X���s�� Model �I�u�W�F�N�g */
	private PanaHousingInquiryManageImpl panaInquiryManager;

	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;

	/** �V�K�ʒm���[���e���v���[�g */
	private ReplacingMail sendInquiryHousingMail;
	/**
	 * �����⍇�������e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param panaInquiryManager
	 *            �����⍇�������e�i���X�� model �I�u�W�F�N�g
	 */
	public void setPanaInquiryManager(PanaHousingInquiryManageImpl panaInquiryManager) {
		this.panaInquiryManager = panaInquiryManager;
	}

	/**
	 * ���ʃR�[�h�ϊ��I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param codeLookupManager
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * @param sendInquiryHousingMail
	 *            �Z�b�g���� sendInquiryHousingMail
	 */
	public void setSendInquiryHousingMail(ReplacingMail sendInquiryHousingMail) {
		this.sendInquiryHousingMail = sendInquiryHousingMail;
	}

	/**
	 * �������N�G�X�g���͉�ʕ\������<br>
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

		FormPopulator.populateFormBeanFromRequest(request, this.loginForm);

		request.setAttribute("sysHousingCd", request.getParameter("sysHousingCd"));

		List<ValidationFailure> errors = new ArrayList();
		if (!this.loginForm.validate(errors)) {
			return loginFailure(request, response, null, this.loginForm, errors);
		}
		LoginUser user = null;
		try {
			user = this.authentication.login(request, response, this.loginForm);
		} catch (LockException e) {
			errors.add(new ValidationFailure("userlock", "logincheck", null,
					null));
			return loginFailure(request, response, user, this.loginForm, errors);
		}
		if (user == null) {
			errors.add(new ValidationFailure("notfound", "logincheck", null,
					null));
			return loginFailure(request, response, user, this.loginForm, errors);
		}
		return loginSuccess(request, response, user, this.loginForm);
	}

}
