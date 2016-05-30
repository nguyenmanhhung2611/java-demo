package jp.co.transcosmos.dm3.adminCore.mypage.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserFormFactory;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserSearchForm;
import jp.co.transcosmos.dm3.dao.JoinResult;


/**
 * �}�C�y�[�W����ڍ׉�ʁ@�i�폜�����̊m�F��ʌ��p�j.
 * <p>
 * ���N�G�X�g�p�����[�^�iuserId�j�œn���ꂽ�l�ɊY������}�C�y�[�W��������擾����ʕ\������B
 * <br/>
 * �y���A���� View ���z<br/>
 * <ul>
 * <li>success<li>:������������I��
 * </ul>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.23	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class MypageDetailCommand implements Command {

	/** �}�C�y�[�W��������e�i���X���s�� Model �I�u�W�F�N�g */
	private MypageUserManage userManager;

	/**
	 * �}�C�y�[�W��������e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param userManager �}�C�y�[�W��������e�i���X�� model �I�u�W�F�N�g
	 */
	public void setUserManager(MypageUserManage userManager) {
		this.userManager = userManager;
	}



	/**
	 * �}�C�y�[�W����ڍו\������<br>
	 * <br>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	 * @return ModelAndView�@�̃C���X�^���X
	*/
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
        Map<String, Object> model = createModel(request);
        MypageUserSearchForm searchForm = (MypageUserSearchForm) model.get("searchForm");

		
        // note
        // ���������p�����[�^�̈Ӑ}�I������ɑ΂���o���f�[�V�����͂��̃^�C�~���O�ł͍s��Ȃ��B
        // �o�^������̌�����ʂł̃o���f�[�V�����Ɉς˂�B

		
        // �w�肳�ꂽ���[�U�[ID �ɊY������}�C�y�[�W����̏����擾����B
        JoinResult mypageUser = this.userManager.searchMyPageUserPk(searchForm.getUserId());

        // �Y������f�[�^�����݂��Ȃ��ꍇ
        // �i�Ⴆ�΁A�Ǘ����[�U�[�̌�����ɕʂ̃Z�b�V��������擾�Ώۃ��[�U�[���폜�����ꍇ�j�A
        // �����Ώۃf�[�^�����̗�O���X���[����B
        if (mypageUser == null) {
        	throw new NotFoundException();
        }

        // �擾�ł����ꍇ�� model �ɐݒ肷��B
		model.put("mypageUser", mypageUser);

		return new ModelAndView("success", model);

	}

	
	
	/**
	 * model �I�u�W�F�N�g���쐬���A���N�G�X�g�p�����[�^���i�[���� form �I�u�W�F�N�g���i�[����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^��ݒ肵�� Form ���i�[���� model �I�u�W�F�N�g�𕜋A����B
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		MypageUserFormFactory factory = MypageUserFormFactory.getInstance(request);
		model.put("searchForm", factory.createMypageUserSearchForm(request));

		return model;
	}

}
