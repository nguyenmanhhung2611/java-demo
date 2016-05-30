package jp.co.transcosmos.dm3.adminCore.mypage.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserFormFactory;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserSearchForm;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * �}�C�y�[�W����������.
 * <p>
 * ���͂��ꂽ�������������Ƀ}�C�y�[�W������������A�ꗗ�\������B<br/>
 * ���������̓��͂ɖ�肪����ꍇ�A���������͍s��Ȃ��B<br/>
 * <br/>
 * �y���A���� View ���z<br/>
 * <ul>
 * <li>success<li>:������������I��
 * <li>validFail<li>:�o���f�[�V�����G���[
 * </ul>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.04	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class MypageListCommand implements Command {

	/** �}�C�y�[�W��������e�i���X���s�� Model �I�u�W�F�N�g */
	protected MypageUserManage userManager;

	/** �P�y�[�W�̕\������ */
	protected int rowsPerPage = 20;

	
	
	/**
	 * �}�C�y�[�W��������e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param userManager �}�C�y�[�W��������e�i���X�� model �I�u�W�F�N�g
	 */
	public void setUserManager(MypageUserManage userManager) {
		this.userManager = userManager;
	}

	/**
	 * 1�y�[�W������\������ݒ肷��B<br>
	 * @param rowsPerPage 1�y�[�W������\����
	 */
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}



	/**
	 * �}�C�y�[�W����������N�G�X�g����<br>
	 * �}�C�y�[�W��������̃��N�G�X�g���������Ƃ��ɌĂяo�����B
	 * <br>
	 * @param request �N���C�A���g�����Http���N�G�X�g�B
	 * @param response �N���C�A���g�ɕԂ�Http���X�|���X�B
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
        Map<String, Object> model = createModel(request);
        MypageUserSearchForm searchForm = (MypageUserSearchForm) model.get("searchForm");


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
		model.put("hitcont", this.userManager.searchMyPageUser(searchForm));


		// �o���f�[�V������ʉ߂��A�����������s��ꂽ�ꍇ�AForm �� command �p�����[�^�� list ��ݒ�
		// ����B�@���̃p�����[�^�l�́A�ڍ׉�ʂ�ύX��ʂ� searchCommand �p�����[�^�Ƃ��Ĉ����p����A
		// �Ăь�����ʂ֕��A����ہAcommand �p�����[�^�Ƃ��ēn�����B
		// �i���̐ݒ�ɂ��A�����ςł���΁A�ڍ׉�ʂ���߂������Ɍ����ςŉ�ʂ��\�������B�j
		model.put("command", "list");

        return new ModelAndView("success", model);
	}



	/**
	 * ���N�G�X�g�p�����[�^���� Form �I�u�W�F�N�g���쐬����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^���ݒ肳�ꂽ�t�H�[���I�u�W�F�N�g
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		MypageUserFormFactory factory = MypageUserFormFactory.getInstance(request);
		MypageUserSearchForm searchForm = factory.createMypageUserSearchForm(request); 

		// �P�y�[�W�ɕ\������s����ݒ肷��B
		searchForm.setRowsPerPage(this.rowsPerPage);

		model.put("searchForm", searchForm);
		
		return model;
	}

}
