package jp.co.transcosmos.dm3.adminCore.mypage.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserForm;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserFormFactory;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserSearchForm;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * �}�C�y�[�W������͊m�F���.
 * <p>
 * ���N�G�X�g�p�����[�^�œn���ꂽ�}�C�y�[�W������̃o���f�[�V�������s���A�m�F��ʂ�\������B<br/>
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
 * H.Mizuno		2015.03.05	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class MypageConfirmCommand implements Command {

	/** �}�C�y�[�W��������e�i���X���s�� Model �I�u�W�F�N�g */
	protected MypageUserManage userManager;

	/** �������[�h (insert = �V�K�o�^�����A update=�X�V����)*/
	protected String mode;


	
	/**
	 * �}�C�y�[�W��������e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param userManager �}�C�y�[�W��������e�i���X�� model �I�u�W�F�N�g
	 */
	public void setUserManager(MypageUserManage userManager) {
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
	 * �}�C�y�[�W��������͊m�F��ʕ\������<br>
	 * <br>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	*/
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
        Map<String, Object> model = createModel(request);
        MypageUserForm inputForm = (MypageUserForm) model.get("inputForm");
        MypageUserSearchForm searchForm = (MypageUserSearchForm) model.get("searchForm");


        // note
        // �X�V���̃L�[�́AMypageUserForm �ł͎󂯎��Ȃ��B
        // MypageUserForm �� userId ���`����ƁA���N�G�X�g�p�����[�^�̉�₂Ń��[�U�[ID �����������\��������ׁB


        // �X�V���[�h�̏ꍇ�A�X�V�Ώۂ̎�L�[�l���p�����[�^�œn����Ă��Ȃ��ꍇ�A��O���X���[����B
        if (this.mode.equals("update")){
        	if (StringValidateUtil.isEmpty(searchForm.getUserId())) throw new RuntimeException ("pk value is null.");
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
		MypageUserFormFactory factory = MypageUserFormFactory.getInstance(request);

		model.put("searchForm", factory.createMypageUserSearchForm(request));
		model.put("inputForm", factory.createMypageUserForm(request));

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
	protected boolean localValidation(MypageUserForm inputForm, MypageUserSearchForm searchForm, List<ValidationFailure> errors)
			throws Exception{

		int startSize = errors.size();

		// ���͂������[���A�h���X�����p�\�����`�F�b�N����B
		if (!this.userManager.isFreeLoginId(inputForm, searchForm.getUserId())){
			errors.add(new ValidationFailure("duplicate", "mypage.input.email", inputForm.getEmail(), null));
		}

		return (startSize == errors.size());
	}

}
