package jp.co.transcosmos.dm3.frontCore.mypage.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserForm;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserFormFactory;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * �}�C�y�[�W������͊m�F���.
 * <p>
 * ���N�G�X�g�p�����[�^�œn���ꂽ�}�C�y�[�W������̃o���f�[�V�������s���A�m�F��ʂ�\������B<br>
 * �����o���f�[�V�����G���[�����������ꍇ�͓��͉�ʂƂ��ĕ\������B<br>
 * <br>
 * �y���A���� View ���z<br>
 * <ul>
 * <li>success<li>:����I��
 * <li>input<li>:�o���f�[�V�����G���[�ɂ��ē���
 * </ul>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * Y.Cho		2015.04.16	�V�K�쐬
 * H.Mizuno		2015.07.07	�}�C�y�[�W���O�C�����ɐV���ȉ����V�K�o�^����ƁA���g�̃��[���A�h���X��
 * 							�o���f�[�V������ʉ߂�������C��
 * </pre>
 * <p>
 * ���ӎ���<br>
 * 
 */
public class MemberConfirmCommand implements Command {

	/** �}�C�y�[�W��������e�i���X���s�� Model �I�u�W�F�N�g */
	protected MypageUserManage userManager;

	/** �������[�h (insert = �V�K�o�^�����A update=�X�V����)*/
	protected String mode;


	
	/**
	 * �}�C�y�[�W��������e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br>
	 * <br>
	 * @param userManager �}�C�y�[�W��������e�i���X�� model �I�u�W�F�N�g
	 */
	public void setUserManager(MypageUserManage userManager) {
		this.userManager = userManager;
	}

	/**
	 * �������[�h��ݒ肷��<br>
	 * <br>
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

        
		// note
		// ���̃^�C�~���O�Ń}�C�y�[�W�F�؏����擾���Ă����ׁA�A�����ĉ���o�^���s���ƁA��ɓo�^�������[���A�h���X
		// ���o���f�[�V������ʉ߂��Ă����B
		// �΍�Ƃ��āA�X�V�������̂݃}�C�y�[�W���[�U�[���擾����l�ɏ�����ύX�����B

        // ���O�C�����[�U�[���
		MypageUserInterface loginUser = null;

        // �X�V���[�h�̏ꍇ�A���O�C�����[�U�[�����擾����B�@�����擾�ł��Ȃ��ꍇ�͗�O���X���[����B
		// �F�؃t�B���^�[�̑Ώۉ�ʂȂ̂ŁA���O�C�����[�U�[��񂪎擾�o���Ȃ��̂̓Z�b�V�����^�C���A�E�g�ȊO��
		// �������Ȃ��B
        if (this.mode.equals("update")) {
    		loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);
    		if (loginUser == null) throw new RuntimeException ("not loggined.");
        }


        // view ���̏����l��ݒ�
        String viewName = "success"; 
        
		// �o���f�[�V���������s
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        if (!inputForm.validate(errors, this.mode)){
        	
        	// �o���f�[�V�����G���[���́A�G���[���� model �ɐݒ肵�A���͉�ʂ�\������B
        	model.put("errors", errors);
        	viewName = "input";
        } else if (!localValidation(inputForm, loginUser, errors)){

        	// Form �̃o���f�[�V����������I�������ꍇ�͌ŗL�̃o���f�[�V���������{����B
        	model.put("errors", errors);
        	viewName = "input";
        }
        
        return new ModelAndView(viewName, model);
	}



	/**
	 * model �I�u�W�F�N�g���쐬���A���N�G�X�g�p�����[�^���i�[���� form �I�u�W�F�N�g���i�[����B<br>
	 * <br>
	 * @param request HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^��ݒ肵�� Form ���i�[���� model �I�u�W�F�N�g�𕜋A����B
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<>();

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		MypageUserFormFactory factory = MypageUserFormFactory.getInstance(request);

		model.put("inputForm", factory.createMypageUserForm(request));

		return model;

	}

	
	
	/**
	 * Form �ł͎������Ȃ��A�c�a���g�p�����o���f�[�V�������s��<br>
	 * <br>
	 * @param inputForm �}�C�y�[�W��������e�i���X�̓��̓p�����[�^����p�t�H�[��.
	 * @param loginUser ���O�C�����[�U�[���
	 * @param errors �G���[���
	 * @return ���펞 true�A�G���[�� false
	 * @throws Exception �Ϗ���N���X���X���[�����O 
	 */
	protected boolean localValidation(MypageUserForm inputForm, MypageUserInterface loginUser, List<ValidationFailure> errors)
			throws Exception {

		int startSize = errors.size();

		// ���[�U�[�h�c
		// ���O�C�����[�U�[���́A���g�̃��[���A�h���X���d�����ăG���[�ƂȂ鎖�������ׁA�X�V�������̂ݐݒ肳��Ă���B
		// isFreeLoginId() ���\�b�h�́A���[�U�[�h�c���w�肳��Ă���ꍇ�́A���̂h�c�ɊY�����郁�[���A�h���X�����O����
		// �d���`�F�b�N���s���B
		String userId = null;
		if (loginUser != null) {
			userId = (String) loginUser.getUserId();
		}

		// ���͂������[���A�h���X�����p�\�����`�F�b�N����B
		if (!this.userManager.isFreeLoginId(inputForm, userId)){
			errors.add(new ValidationFailure("duplicate", "mypage.input.email", inputForm.getEmail(), null));
		}

		return (startSize == errors.size());
	}


}
