package jp.co.transcosmos.dm3.adminCore.userManage.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.AdminUserManage;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserFormFactory;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserSearchForm;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.dao.JoinResult;

import org.springframework.web.servlet.ModelAndView;

/**
 * �Ǘ��҃��[�U�ڍ׉�ʁ@�i�폜�����̊m�F��ʌ��p�j.
 * <p>
 * ���N�G�X�g�p�����[�^�iuserId�j�œn���ꂽ�l�ɊY������Ǘ����[�U�[���A�Ǘ����[�U�[���[������
 * �擾����ʕ\������B<br/>
 * <br/>
 * �y���A���� View ���z<br/>
 * <ul>
 * <li>success<li>:������������I��
 * </ul>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.29	Shamaison ���Q�l�ɐV�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class UserDetailCommand implements Command {

	/** �Ǘ����[�U�[�����e�i���X���s�� Model �I�u�W�F�N�g */
	protected AdminUserManage userManager;

	/**
	 * �Ǘ����[�U�[�����e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param userManager �Ǘ����[�U�[�����e�i���X�� model �I�u�W�F�N�g
	 */
	public void setUserManager(AdminUserManage userManager) {
		this.userManager = userManager;
	}

	
	
	/**
	 * �Ǘ����[�U�ڍו\������<br>
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
        AdminUserSearchForm searchForm = (AdminUserSearchForm) model.get("searchForm");
        

        // note
        // ���������p�����[�^�̈Ӑ}�I������ɑ΂���o���f�[�V�����͂��̃^�C�~���O�ł͍s��Ȃ��B
        // �o�^������̌�����ʂł̃o���f�[�V�����Ɉς˂�B


        // �w�肳�ꂽ���[�U�[ID �ɊY������Ǘ����[�U�[�̏����擾����B
        JoinResult adminUser = this.userManager.searchAdminUserPk(searchForm);

        // �Y������f�[�^�����݂��Ȃ��ꍇ
        // �i�Ⴆ�΁A�Ǘ����[�U�[�̌�����ɕʂ̃Z�b�V��������擾�Ώۃ��[�U�[���폜�����ꍇ�j�A
        // �����Ώۃf�[�^�����̗�O���X���[����B
        if (adminUser == null) {
        	throw new NotFoundException();
        }

        // �擾�ł����ꍇ�� model �ɐݒ肷��B
		model.put("adminUser", adminUser);


		// �擾�������[�U�[���̃��b�N�󋵂� model �ɐݒ肷��B
		String alias = CommonParameters.getInstance(request).getAdminUserDbAlias();
		AdminUserInterface userInfo = (AdminUserInterface) adminUser.getItems().get(alias);
		model.put("lockStatus", this.userManager.isLocked(userInfo));


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
		AdminUserFormFactory factory = AdminUserFormFactory.getInstance(request);
		model.put("searchForm", factory.createUserSearchForm(request));

		return model;
	}

}
