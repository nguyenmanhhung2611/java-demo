package jp.co.transcosmos.dm3.adminCore.mypage.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.exception.DuplicateException;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserForm;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserFormFactory;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserSearchForm;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.mail.ReplacingMail;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

/**
 * �}�C�y�[�W����̒ǉ��A�ύX�A�폜�A����уA�J�E���g���b�N�̉�������.
 * <p>
 * �y�V�K�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B</li>
 * <li>�o���f�[�V����������I�������ꍇ�A���[�U�[�h�c���A�}�C�y�[�W�������V�K�o�^����B</li>
 * <li>�����A�V�K�o�^���Ƀ��[���A�h���X�ň�Ӑ���G���[�����������ꍇ�̓o���f�[�V�����G���[�Ƃ��Ĉ����B</li>
 * <li>�o�^������A�o�^�������[���A�h���X���Ƀp�X���[�h�̒ʒm���[���𑗐M����B</li>
 * </ul>
 * <br/>
 * �y�X�V�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B</li>
 * <li>�o���f�[�V����������I�������ꍇ�A�}�C�y�[�W��������X�V����B</li>
 * <li>�����A�X�V���Ƀ��[���A�h���X�i���O�C��ID�j �ň�Ӑ���G���[�����������ꍇ�̓o���f�[�V�����G���[�Ƃ��Ĉ����B</li>
 * <li>�����A�X�V�Ώۃf�[�^�����݂��Ȃ��ꍇ�A�X�V�������p���ł��Ȃ��̂ŊY��������ʂ�\������B</li>
 * <li>�o�^������A�p�X���[�h�̕ύX���������ꍇ�A�o�^�������[���A�h���X���Ƀp�X���[�h�̒ʒm���[���𑗐M����B</li>
 * </ul>
 * <br/>
 * �y�폜�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B�i��L�[�l�̂݁j</li>
 * <li>�o���f�[�V����������I�������ꍇ�A�}�C�y�[�W��������폜����B</li>
 * </ul>
 * <br/>
 * �y���A���� View ���z<br/>
 * <ul>
 * <li>success</li>:����I���i���_�C���N�g�y�[�W�j
 * <li>input</li>:�o���f�[�V�����G���[�ɂ��ē���
 * <li>comp</li>:������ʕ\��
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
public class MypageCompCommand implements Command  {

	private static final Log log = LogFactory.getLog(MypageCompCommand.class);
	
	/** �}�C�y�[�W��������e�i���X���s�� Model �I�u�W�F�N�g */
	protected MypageUserManage userManager;

	/** �������[�h (insert=�V�K�o�^�����A update=�X�V�����Adelete=�폜����)*/
	protected String mode;

	/** ��L�[�ƂȂ� UserId �̕K�{�`�F�b�N���s���ꍇ�Atrue ��ݒ肷��B �i�f�t�H���g true�j�@*/
	protected boolean useUserIdValidation = true;

	/** Form �̃o���f�[�V���������s����ꍇ�Atrue ��ݒ肷��B�@�i�f�t�H���g true�j */
	protected boolean useValidation = true;

	/** �p�X���[�h�ʒm���[���e���v���[�g */
	protected ReplacingMail sendMypagePasswordTemplate;



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
	 * ��L�[�ƂȂ� UserId �̕K�{�`�F�b�N�����s����ꍇ�Atrue ��ݒ肷��B�@�i�f�t�H���g true�j<br/>
	 * <br/>
	 * @param useUserIdValidation true �̏ꍇ�AUserId �̕K�{�`�F�b�N�����s
	 */
	public void setUseUserIdValidation(boolean useUserIdValidation) {
		this.useUserIdValidation = useUserIdValidation;
	}

	/**
	 * Form �̃o���f�[�V���������s����ꍇ�Atrue ��ݒ肷��B�@�i�f�t�H���g true�j<br/>
	 * <br/>
	 * @param useValidation true �̏ꍇ�AForm �̃o���f�[�V���������s
	 */
	public void setUseValidation(boolean useValidation) {
		this.useValidation = useValidation;
	}

	/**
	 * �p�X���[�h�ʒm���[���e���v���[�g��ݒ肷��B<br/>
	 * ���ݒ�̏ꍇ�A���[�����M���s��Ȃ��B<br/>
	 * <br/>
	 * @param sendMypagePasswordTemplate �p�X���[�h�ʒm���[���e���v���[�g
	 */
	public void setSendMypagePasswordTemplate(ReplacingMail sendMypagePasswordTemplate) {
		this.sendMypagePasswordTemplate = sendMypagePasswordTemplate;
	}

	
	
	/**
	 * �Ǘ����[�U�̒ǉ��E�ύX�E�폜����<br>
	 * DI �R���e�i����ݒ肳�ꂽ�v���p�e�B�l�imode�AuseUserIdValidation�AuseValidation�j
	 * �̐ݒ�ɏ]���Ǘ����[�U�[����ǉ��E�ύX�E�폜����B<br/>
	 * <br>
	 * @param request �N���C�A���g�����Http���N�G�X�g�B
	 * @param response �N���C�A���g�ɕԂ�Http���X�|���X�B
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
				throws Exception {

		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
		Map<String, Object> model = createModel(request);
		MypageUserForm inputForm = (MypageUserForm) model.get("inputForm");
		MypageUserSearchForm searchForm = (MypageUserSearchForm) model.get("searchForm");


        // ���O�C�����[�U�[�̏����擾����B�@�i�^�C���X�^���v�̍X�V�p�j
		AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);


        // ������ʂŃ����[�h�����ꍇ�A�X�V�������Ӑ}�������s������肪��������B
        // ���̖�����������ׁAview ���� "success"�@���w�肷��Ǝ������_�C���N�g��ʂ��\�������B
        // ���̃��_�C���N�g��ʂ́Acommand �p�����[�^�� "redirect"�@�ɐݒ肵�Ċ�����ʂփ��N�G�X�g��
        // ���M����B
        // ����āAcommand = "redirect" �̏ꍇ�́A�c�a�X�V�͍s�킸�A������ʂ�\������B
        String command = inputForm.getCommand();
        if (command != null && command.equals("redirect")){
        	return new ModelAndView("comp" , model);
        }


        // ��L�[�l�̃o�����[�^�`�F�b�N���K�{�̏ꍇ�A�`�F�b�N���s���B
        // �V�K�o�^���ȊO�͏����Ώۂ̎�L�[�l���p�����[�^�œn����Ă��Ȃ��ꍇ�A��O���X���[����B
        if (this.useUserIdValidation){
        	if (StringValidateUtil.isEmpty(searchForm.getUserId())) throw new RuntimeException ("pk value is null.");
        }


        // note
        // ���������p�����[�^�̈Ӑ}�I������ɑ΂���o���f�[�V�����͂��̃^�C�~���O�ł͍s��Ȃ��B
        // �o�^������̌�����ʂł̃o���f�[�V�����Ɉς˂�B


		// �o���f�[�V�����̎��s���[�h���L���̏ꍇ�A�o���f�[�V���������s����B
        // �폜�����A���b�N���������̏ꍇ�̓��O�C��ID �ȊO�̃p�����[�^�͕s�v�Ȃ̂ŁAspring �����疳�������Ă���B
        if (this.useValidation){
        	List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        	if (!inputForm.validate(errors, this.mode)){

        		// �o���f�[�V�����G���[���́A�G���[���� model �ɐݒ肵�A���͉�ʂ�\������B
        		model.put("errors", errors);
        		return new ModelAndView("input" , model);
        	}

        	// ���[���A�h���X�i���O�C��ID�j �̏d���`�F�b�N�́A����������ōs���̂ŁA�ʏ�̃o���f�[�V�����Ƃ��Ă͎��{���Ȃ��B
        }


        // �e�폈�������s
        try {
            execute(model, inputForm, searchForm, loginUser);
        	
        } catch (DuplicateException e1) {
            // ���[���A�h���X�i���O�C��ID�j ���d�����Ă���ꍇ�A�ē��͂ցB
        	List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        	errors.add(new ValidationFailure("duplicate", "mypage.input.email", inputForm.getEmail(), null));
    		model.put("errors", errors);

        	return new ModelAndView("input", model);

        } catch (NotFoundException e2) {

        	// �Y������f�[�^�����݂��Ȃ��ꍇ
            // �i�Ⴆ�΁A�Ǘ����[�U�[�̌�����ɕʂ̃Z�b�V��������擾�Ώۃ��[�U�[���폜�����ꍇ�j�A
            // �����Ώۃf�[�^�����̗�O���X���[����B
           	throw new NotFoundException();

        }

        // �p�X���[�h�̒ʒm���[�����M
        sendPwdMail(request, inputForm);

        
		return new ModelAndView("success" , model);
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
		model.put("inputForm", factory.createMypageUserForm(request));

		return model;

	}



	/**
	 * �����̐U�蕪���Ǝ��s���s���B<br/>
	 * <br/>
	 * @param model View �w�ֈ����n�� model �I�u�W�F�N�g
	 * @param inputForm ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
	 * @param searchForm ���������A����эX�V�E�폜�Ώ� userId ���i�[���ꂽ Form �I�u�W�F�N�g
	 * @param editUser �^�C���X�^���v�X�V���Ɏg�p���郍�O�C�����[�U�[���
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception DuplicateException �o�^���郍�O�C��ID ���d�������ꍇ
	 * @exception NotFoundException �X�V�Ώۂ����݂��Ȃ��ꍇ
	 * @exception RuntimeException mode ���w�肳��Ă��Ȃ��ꍇ�B�@�iDI �R���e�i�̐ݒ�~�X�j
	 */
	protected void execute(Map<String, Object> model, MypageUserForm inputForm,
						   MypageUserSearchForm searchForm, AdminUserInterface editUser)
			throws Exception, DuplicateException, RuntimeException {

		if (this.mode.equals("insert")){
			insert(model, inputForm, editUser);
        } else if (this.mode.equals("update")) {
			update(model, inputForm, (String)searchForm.getUserId(), editUser);
        } else if (this.mode.equals("delete")) {
			delete(model, (String)searchForm.getUserId(), editUser);
        } else {
        	// �z�肵�Ă��Ȃ��������[�h�̏ꍇ�A��O���X���[����B
        	throw new RuntimeException ("execute mode bad setting.");
        }

	}



	/**
	 * �V�K�o�^����<br/>
	 * �����œn���ꂽ���e�Ń��[�U�[ID���A�}�C�y�[�W�������ǉ�����B<br/>
	 * <br/>
	 * @param model View �w�ֈ����n�� model �I�u�W�F�N�g
	 * @param inputForm ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
	 * @param editUser �^�C���X�^���v�X�V���Ɏg�p���郍�O�C�����[�U�[���
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception DuplicateException �o�^���郍�O�C��ID ���d�������ꍇ
	 */
	protected void insert(Map<String, Object> model, MypageUserForm inputForm, AdminUserInterface editUser)
			throws Exception, DuplicateException {

		// ���[�U�[ID ���̓o�^
		LoginUser loginUser = this.userManager.addLoginID((String)editUser.getUserId());

    	// �擾�������[�U�[ID �Ń}�C�y�[�W������̓o�^
    	this.userManager.addMyPageUser(inputForm, (String)loginUser.getUserId(), (String)editUser.getUserId());

	}



	/**
	 * �X�V�o�^����<br/>
	 * �����œn���ꂽ���e�ŊǗ����[�U�[�̏����X�V����B<br/>
	 * <br/>
	 * @param model View �w�ֈ����n�� model �I�u�W�F�N�g
	 * @param inputForm ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
	 * @param updUserId �X�V�ΏۂƂȂ�}�C�y�[�W����� userId
	 * @param loginUser �^�C���X�^���v�X�V���Ɏg�p���郍�O�C�����[�U�[���
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception DuplicateException �o�^���郍�O�C��ID ���d�������ꍇ
	 * @exception NotFoundException �X�V�Ώۂ����݂��Ȃ��ꍇ
	 */
	protected void update(Map<String, Object> model, MypageUserForm inputForm, String updUserId, AdminUserInterface loginUser)
			throws Exception, DuplicateException, NotFoundException {

		// �X�V����
    	this.userManager.updateMyPageUser(inputForm, updUserId, (String)loginUser.getUserId());

	}



	/**
	 * �폜����<br/>
	 * �����œn���ꂽ���e�Ń}�C�y�[�W��������폜����B<br/>
	 * <br/>
	 * @param model View �w�ֈ����n�� model �I�u�W�F�N�g
	 * @param delUserId �폜�ΏۂƂȂ�}�C�y�[�W����� userId
	 * @param loginUser �^�C���X�^���v�X�V���Ɏg�p���郍�O�C�����[�U�[���
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	protected void delete(Map<String, Object> model, String delUserId, AdminUserInterface loginUser)
			throws Exception {

		// �폜����
		this.userManager.delMyPageUser(delUserId);

	}

	
	
	/**
	 * �p�X���[�h�̒ʒm���[���𑗐M����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param inputForm ���͒l
	 */
	protected void sendPwdMail(HttpServletRequest request, MypageUserForm inputForm){

		// ���[���e���v���[�g���ݒ肳��Ă��Ȃ��ꍇ�̓��[���̒ʒm���s��Ȃ��B
		if (this.sendMypagePasswordTemplate == null) {
			log.warn("sendMypagePasswordTemplate is null.");
			return;
		}

		// �V�K�o�^���܂��̓p�X���[�h�ύX�����������ꍇ�Ƀ��[���𑗐M����B
		// ��L������p�X���[�h�̓��̗͂L���ōs���B�@�i���͒l�����ۂɕύX���ꂽ���܂ł̓`�F�b�N���Ȃ��B�j
		if (StringValidateUtil.isEmpty(inputForm.getPassword())){
			return;
		}

		// ���[���e���v���[�g�Ŏg�p����p�����[�^��ݒ肷��B
		this.sendMypagePasswordTemplate.setParameter("inputForm", inputForm);
		this.sendMypagePasswordTemplate.setParameter("commonParameters", CommonParameters.getInstance(request));

		// ���[�����M
		this.sendMypagePasswordTemplate.send();
	}

}
