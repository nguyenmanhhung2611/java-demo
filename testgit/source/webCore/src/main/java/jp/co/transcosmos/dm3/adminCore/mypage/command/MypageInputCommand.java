package jp.co.transcosmos.dm3.adminCore.mypage.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserForm;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserFormFactory;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserSearchForm;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.web.servlet.ModelAndView;

/**
 * �}�C�y�[�W��������͉��.
 * <p>
 * �y�V�K�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^�ő��M���ꂽ�l������B�i����������A�ꗗ�̕\���y�[�W�ʒu�̂݁j</li>
 * <li>�󂯎�����l����͉�ʂɕ\������B</li>
 * </ul>
 * <br/>
 * �y�X�V�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^�ő��M���ꂽ�l������B�i����������A�ꗗ�̕\���y�[�W�ʒu�A����ю�L�[�l�̂݁j</li>
 * <li>���N�G�X�g�p�����[�^�iuserId�j�ɊY������}�C�y�[�W��������擾����B</li>
 * <li>�擾�����l����͉�ʂɕ\������B</li>
 * </ul>
 * <br/>
 * �y���N�G�X�g�p�����[�^�icommand�j �� "back"�̏ꍇ�z�i�m�F��ʂ���̕��A�̏ꍇ�j<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^�ő��M���ꂽ�l������B</li>
 * <li>�󂯎�����l����͉�ʂɕ\������B</li>
 * </ul>
 * <br/>
 * �y���A���� View ���z<br/>
 * <ul>
 * <li>success<li>:������������I��
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
public class MypageInputCommand implements Command {

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
	 * �Ǘ����[�U���͉�ʕ\������<br>
	 * <br>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	*/
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
        Map<String, Object> model = createModel(request);
        MypageUserForm inputForm = (MypageUserForm) model.get("inputForm");
        MypageUserSearchForm searchForm = (MypageUserSearchForm) model.get("searchForm");

        
		// �X�V�����̏ꍇ�A�����ΏۂƂȂ��L�[�l���p�����[�^�œn����Ă��Ȃ��ꍇ�A��O���X���[����B
		if (this.mode.equals("update") && StringValidateUtil.isEmpty(searchForm.getUserId())){
			throw new RuntimeException ("pk value is null.");
		}


		// command �p�����[�^�� "back" �̏ꍇ�A���͊m�F��ʂ���̕��A�Ȃ̂ŁA�c�a���珉���l���擾���Ȃ��B
		// �i���N�G�X�g�p�����[�^����擾�����l���g�p����B�j
		String command = inputForm.getCommand();
		if (command != null && command.equals("back")) return new ModelAndView("success", model);


        // �V�K�o�^���[�h�ŏ�����ʕ\���̏ꍇ�A�f�[�^�̎擾�͍s��Ȃ��B
        if (this.mode.equals("insert")) return new ModelAndView("success", model);
        
        
        // note
        // ���������p�����[�^�̈Ӑ}�I������ɑ΂���o���f�[�V�����͂��̃^�C�~���O�ł͍s��Ȃ��B
        // �o�^������̌�����ʂł̃o���f�[�V�����Ɉς˂�B


        // �}�C�y�[�W��������擾
        JoinResult mypageUser = this.userManager.searchMyPageUserPk(searchForm.getUserId());

        // �Y������f�[�^�����݂��Ȃ��ꍇ
        // �i�Ⴆ�΁A�}�C�y�[�W����̌�����ɕʂ̃Z�b�V��������擾�Ώۃ��[�U�[���폜�����ꍇ�j�A
        // �����Ώۃf�[�^�����̗�O���X���[����B
        if (mypageUser == null) {
        	throw new NotFoundException();
        }


        // �擾�����l����͗p Form �֊i�[����B
        // ���̒l�͓��̓t�B�[���h�̏����l�Ƃ��ĕ\�������B
        CommonParameters commonParameters = CommonParameters.getInstance(request);

		inputForm.setDefaultData(
				(MypageUserInterface) mypageUser.getItems().get(commonParameters.getMemberDbAlias()));

		return new ModelAndView("success", model);
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

		// ���������A����сA��ʃR���g���[���p�����[�^���擾����B
		MypageUserSearchForm searchForm = factory.createMypageUserSearchForm(request);
		model.put("searchForm", searchForm);

		
		// ���̓t�H�[���̎󂯎��́Acommand �p�����[�^�̒l�ɂ���ĈقȂ�B
		// command �p�����[�^���n����Ȃ��ꍇ�A���͉�ʂ̏����\���Ƃ݂Ȃ��A��̃t�H�[���𕜋A����B
		// command �p�����[�^�� "back" ���n���ꂽ�ꍇ�A���͊m�F��ʂ���̕��A���Ӗ�����B
		// ���̏ꍇ�̓��N�G�X�g�p�����[�^���󂯎��A���͉�ʂ֏����\������B
		// �����A"back" �ȊO�̒l���n���ꂽ�ꍇ�Acommand �p�����[�^���n����Ȃ��ꍇ�Ɠ����Ɉ����B

		MypageUserForm inputForm = factory.createMypageUserForm(request);
		String command = inputForm.getCommand();
		
		if (command != null && command.equals("back")){
			model.put("inputForm", inputForm);
		} else {
			model.put("inputForm", factory.createMypageUserForm());
		}

		return model;

	}
	
}
