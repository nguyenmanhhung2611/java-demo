package jp.co.transcosmos.dm3.webAdmin.userManage.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.adminCore.userManage.command.UserCompCommand;
import jp.co.transcosmos.dm3.corePana.model.adminUser.form.PanaAdminUserSearchForm;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * �Ǘ��҃��[�U�̍폜�A�Č��������B
 * <p>
 * ���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B�i��L�[�l�̂݁j<br/>
 * �o���f�[�V����������I�������ꍇ�A�Ǘ����[�U�[���A�Ǘ����[�U�[���������폜����B<br/>
 * ���͂��ꂽ�������������ɊǗ��҃��[�U�[���Č������A�ꗗ�\������B<br/>
 * <br/>
 * �y���A���� View ���z<br/>
 * <ul>
 * <li>success</li>:����I��
 * <li>validFail<li>:�o���f�[�V�����G���[
 * </ul>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * �`����		2015/04/21	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class PanaUserCompCommand extends UserCompCommand  {

	/** �P�y�[�W�̕\������ */
	private int rowsPerPage = 50;



	/**
	 * 1�y�[�W������\������ݒ肷��B<br>
	 * @param rowsPerPage 1�y�[�W������\����
	 */
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}



	/**
	 * �Ǘ����[�U�̍폜�A�Č�������<br>
	 * DI �R���e�i����ݒ肳�ꂽ�v���p�e�B�l�imode�AuseUserIdValidation�AuseValidation�j
	 * �̐ݒ�ɏ]���Ǘ����[�U�[�����폜�A�Č�������B<br/>
	 * <br>
	 * @param request �N���C�A���g�����Http���N�G�X�g�B
	 * @param response �N���C�A���g�ɕԂ�Http���X�|���X�B
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
				throws Exception {

		ModelAndView modelAndView = super.handleRequest(request, response);

		// ���ꂩ��Č��������s����B
		Map<String, Object> model = modelAndView.getModel();
		PanaAdminUserSearchForm searchForm = (PanaAdminUserSearchForm) model.get("searchForm");

		// �P�y�[�W�ɕ\������s����ݒ肷��B
		searchForm.setRowsPerPage(this.rowsPerPage);

        // �o���f�[�V���������s
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();

        if (!searchForm.validate(errors)) {
        	// �o���f�[�V�����G���[����
			model.put("errors", errors);
			return new ModelAndView("validFail", model);
        }

		// �������s
		// searchAdminUser() �́A�p�����[�^�œn���ꂽ form �̓��e�ŊǗ����[�U�[���������A
		// �����������ʂ� form �Ɋi�[����B
		// ���̃��\�b�h�̖߂�l�͊Y�������Ȃ̂ŁA���̒l�� view �w�֓n���p�����[�^�Ƃ��Đݒ肵�Ă���B
		model.put("hitcont", this.userManager.searchAdminUser(searchForm));


		// �o���f�[�V������ʉ߂��A�����������s��ꂽ�ꍇ�AForm �� command �p�����[�^�� list ��ݒ�
		// ����B�@���̃p�����[�^�l�́A�ڍ׉�ʂ�ύX��ʂ� searchCommand �p�����[�^�Ƃ��Ĉ����p����A
		// �Ăь�����ʂ֕��A����ہAcommand �p�����[�^�Ƃ��ēn�����B
		// �i���̐ݒ�ɂ��A�����ςł���΁A�ڍ׉�ʂ���߂������Ɍ����ςŉ�ʂ��\�������B�j
		model.put("command", "list");

		return modelAndView;
	}
}
