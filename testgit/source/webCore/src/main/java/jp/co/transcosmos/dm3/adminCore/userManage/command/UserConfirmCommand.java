package jp.co.transcosmos.dm3.adminCore.userManage.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.AdminUserManage;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserForm;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserFormFactory;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserSearchForm;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * �Ǘ��҃��[�U���͊m�F���.
 * <p>
 * ���N�G�X�g�p�����[�^�œn���ꂽ�Ǘ����[�U�[���̃o���f�[�V�������s���A�m�F��ʂ�\������B<br/>
 * �����o���f�[�V�����G���[�����������ꍇ�͓��͉�ʂƂ��ĕ\������B<br/>
 * <br/>
 * �y���A���� View ���z<br/>
 * <ul>
 * <li>success<li>:����I��
 * <li>input<li>:�o���f�[�V�����G���[�ɂ��ē���
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
public class UserConfirmCommand implements Command {

	/** �Ǘ����[�U�[�����e�i���X���s�� Model �I�u�W�F�N�g */
	protected AdminUserManage userManager;

	/** �������[�h (insert = �V�K�o�^�����A update=�X�V����)*/
	protected String mode;


	
	/**
	 * �Ǘ����[�U�[�����e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param userManager �Ǘ����[�U�[�����e�i���X�� model �I�u�W�F�N�g
	 */
	public void setUserManager(AdminUserManage userManager) {
		this.userManager = userManager;
	}

	/**
	 * �������[�h��ݒ肷��<br/>
	 * <br/>
	 * @param mode "insert" = �V�K�o�^�����A"update" = �X�V����
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	
	
	/**
	 * �Ǘ����[�U���͊m�F��ʕ\������<br>
	 * <br>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	*/
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
        Map<String, Object> model = createModel(request);
        AdminUserForm inputForm = (AdminUserForm) model.get("inputForm");
        AdminUserSearchForm searchForm = (AdminUserSearchForm) model.get("searchForm");


        // ���b�N�X�e�[�^�X���f�t�H���g�Ƃ��� false �i�ʏ�j��ݒ肵�Ă���
		model.put("lockStatus", false);
        
        
        // �X�V���[�h�̏ꍇ�A�X�V�Ώۂ̎�L�[�l���p�����[�^�œn����Ă��Ȃ��ꍇ�A��O���X���[����B
        if (this.mode.equals("update")){
        	if (StringValidateUtil.isEmpty(inputForm.getUserId())) throw new RuntimeException ("pk value is null.");


            // �Ǘ����[�U�[�����擾
            JoinResult adminUser = this.userManager.searchAdminUserPk(searchForm);

            // �Y������f�[�^�����݂��Ȃ��ꍇ
            // �i�Ⴆ�΁A�Ǘ����[�U�[�̌�����ɕʂ̃Z�b�V��������擾�Ώۃ��[�U�[���폜�����ꍇ�j�A
            // �����Ώۃf�[�^�����̗�O���X���[����B
            if (adminUser == null) {
            	throw new NotFoundException();
            }

            
    		// �擾�������[�U�[���̃��b�N�󋵂� model �ɐݒ肷��B
    		String alias = CommonParameters.getInstance(request).getAdminUserDbAlias();
    		AdminUserInterface userInfo = (AdminUserInterface) adminUser.getItems().get(alias);
    		model.put("lockStatus", this.userManager.isLocked(userInfo));
        }


        // note
        // ���������p�����[�^�̈Ӑ}�I������ɑ΂���o���f�[�V�����͂��̃^�C�~���O�ł͍s��Ȃ��B
        // �o�^������̌�����ʂł̃o���f�[�V�����Ɉς˂�B


        // view ���̏����l��ݒ�
        String viewName = "success"; 
        
		// �o���f�[�V���������s
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        if (!inputForm.validate(errors, this.mode)){
        	
        	// �o���f�[�V�����G���[���́A�G���[���� model �ɐݒ肵�A���͉�ʂ�\������B
        	model.put("errors", errors);
        	viewName = "input";
        } else if (!localValidation(inputForm, searchForm, errors)){

        	// Form �̃o���f�[�V����������I�������ꍇ�͌ŗL�̃o���f�[�V���������{����B
        	model.put("errors", errors);
        	viewName = "input";
        }
        
        return new ModelAndView(viewName, model);
	}



	/**
	 * model �I�u�W�F�N�g���쐬���A���N�G�X�g�p�����[�^���i�[���� form �I�u�W�F�N�g���i�[����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^��ݒ肵�� Form ���i�[���� model �I�u�W�F�N�g�𕜋A����B
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<>();

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		AdminUserFormFactory factory = AdminUserFormFactory.getInstance(request);

		model.put("searchForm", factory.createUserSearchForm(request));
		model.put("inputForm", factory.createAdminUserForm(request));

		return model;

	}



	/**
	 * Form �ł͎������Ȃ��A�c�a���g�p�����o���f�[�V�������s��<br/>
	 * <br/>
	 * @param errors
	 * 
	 * @return ���펞 true�A�G���[�� false
	 * @throws Exception 
	 */
	protected boolean localValidation(AdminUserForm inputForm, AdminUserSearchForm searchForm, List<ValidationFailure> errors)
			throws Exception{

		int startSize = errors.size();

		// ���͂������O�C��ID �����p�\�����`�F�b�N����B
		if (!this.userManager.isFreeLoginId(inputForm)){
			errors.add(new ValidationFailure("duplicate", "user.input.loginId", inputForm.getLoginId(), null));
		}

		return (startSize == errors.size());
	}
	
}
