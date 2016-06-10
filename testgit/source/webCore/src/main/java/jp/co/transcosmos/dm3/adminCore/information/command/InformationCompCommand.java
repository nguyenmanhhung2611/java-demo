package jp.co.transcosmos.dm3.adminCore.information.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.adminCore.mypage.command.MypageCompCommand;
import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.InformationManage;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.information.form.InformationForm;
import jp.co.transcosmos.dm3.core.model.information.form.InformationFormFactory;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.mail.ReplacingMail;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

/**
 * ���m�点���̒ǉ��A�ύX�A�폜����.
 * <p>
 * �y�V�K�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B</li>
 * <li>�o���f�[�V����������I�������ꍇ�A���m�点����V�K�o�^����B</li>
 * <li>�܂��A���J�悪����l�̏ꍇ�A���m�点���J������V�K�o�^����B</li>
 * </ul>
 * <br/>
 * �y�X�V�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B</li>
 * <li>�o���f�[�V����������I�������ꍇ�A���m�点�����X�V����B</li>
 * <li>���m�点���J����͈�x�폜���A�ύX��̌��J�悪����l�ł���΁A���m�点���J������V�K�o�^����B</li>
 * <li>�����A�X�V�Ώۃf�[�^�����݂��Ȃ��ꍇ�A�X�V�������p���ł��Ȃ��̂ŊY��������ʂ�\������B</li>
 * </ul>
 * <br/>
 * �y�폜�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B�i��L�[�l�̂݁j</li>
 * <li>�o���f�[�V����������I�������ꍇ�A���m�点���A���m�点���J������폜����B</li>
 * </ul>
 * <br/>
 * �y���A���� View ���z<br/>
 * <ul>
 * <li>success</li>:����I���i���_�C���N�g�y�[�W�j
 * <li>input</li>:�o���f�[�V�����G���[�ɂ��ē���
 * <li>notFound</li>:�Y���f�[�^�����݂��Ȃ��ꍇ�i�X�V�����̏ꍇ�j
 * <li>comp</li>:������ʕ\��
 * </ul>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.10	�V�K�쐬
 * H.Mizuno		2015.02.27	�C���^�[�t�F�[�X�̉���ɂƂ��Ȃ��S�̍\����ύX
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class InformationCompCommand implements Command  {

	private static final Log log = LogFactory.getLog(MypageCompCommand.class);

	/** ���m�点�����e�i���X���s�� Model �I�u�W�F�N�g */
	protected InformationManage informationManager;

	/** �������[�h (insert = �V�K�o�^�����A update=�X�V�����Adelete=�폜����)*/
	protected String mode;

	/** ��L�[�ƂȂ� UserId �̕K�{�`�F�b�N���s���ꍇ�Atrue ��ݒ肷��B �i�f�t�H���g true�j�@*/
	protected boolean useUserIdValidation = true;

	/** Form �̃o���f�[�V���������s����ꍇ�Atrue ��ݒ肷��B�@�i�f�t�H���g true�j */
	protected boolean useValidation = true;

	/** ���m�点�ʒm���[���e���v���[�g */
	protected ReplacingMail sendInformationTemplate;

	/**
	 * ���m�点�ʒm���[���e���v���[�g��ݒ肷��B<br/>
	 * ���ݒ�̏ꍇ�A���[�����M���s��Ȃ��B<br/>
	 * <br/>
	 * @param sendInformationTemplate ���m�点�ʒm���[���e���v���[�g
	 */
	public void setSendInformationTemplate(ReplacingMail sendInformationTemplate) {
		this.sendInformationTemplate = sendInformationTemplate;
	}

	/**
	 * ���m�点�����e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param informationManager ���m�点�����e�i���X�� model �I�u�W�F�N�g
	 */
	public void setInformationManager(InformationManage informationManager) {
		this.informationManager = informationManager;
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
	 * ���m�点���̒ǉ��A�ύX�A�폜����<br>
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
        InformationForm inputForm = (InformationForm) model.get("inputForm");


        // ���O�C�����[�U�[�̏����擾����B�@�i�^�C���X�^���v�̍X�V�p�j
        AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);


        // ������ʂŃ����[�h�����ꍇ�A�X�V�������Ӑ}�������s������肪��������B
        // ���̖�����������ׁAview ���� "success"�@���w�肷��Ǝ������_�C���N�g��ʂ��\�������B
        // ���̃��_�C���N�g��ʂ́Acommand �p�����[�^�� "redirect"�@�ɐݒ肵�Ċ�����ʂփ��N�G�X�g��
        // ���M����B
        // ����āAcommand = "redirect" �̏ꍇ�́A�c�a�X�V�͍s�킸�A������ʂ�\������B
        String command = inputForm.getCommand();
        if (command != null && "redirect".equals(command)){
        	return new ModelAndView("comp" , model);
        }


        // ��L�[�l�̃o�����[�^�`�F�b�N���K�{�̏ꍇ�A�`�F�b�N���s���B
        // �V�K�o�^���ȊO�͏����Ώۂ̎�L�[�l���p�����[�^�œn����Ă��Ȃ��ꍇ�A��O���X���[����B
        if (this.useUserIdValidation){
        	if (StringValidateUtil.isEmpty(inputForm.getInformationNo())) throw new RuntimeException ("pk value is null.");
        }


        // note
        // ���������p�����[�^�̈Ӑ}�I������ɑ΂���o���f�[�V�����͂��̃^�C�~���O�ł͍s��Ȃ��B
        // �o�^������̌�����ʂł̃o���f�[�V�����Ɉς˂�B


		// �o���f�[�V�����̎��s���[�h���L���̏ꍇ�A�o���f�[�V���������s����B
        // �폜�����A���b�N���������̏ꍇ�̓��O�C��ID �ȊO�̃p�����[�^�͕s�v�Ȃ̂ŁAspring �����疳�������Ă���B
        if (this.useValidation){
        	List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        	if (!inputForm.validate(errors)){

        		// �o���f�[�V�����G���[���́A�G���[���� model �ɐݒ肵�A���͉�ʂ�\������B
        		model.put("errors", errors);
        		return new ModelAndView("input" , model);
        	}
        }


        // �e�폈�������s
        try {
        	execute(model, inputForm, loginUser);

        } catch (NotFoundException e) {
            // ���O�C��ID �����݂��Ȃ��ꍇ�́A�Y���Ȃ���ʂ�
        	return new ModelAndView("notFound", model);

        }

		// �p�X���[�h�̒ʒm���[�����M
		sendInformationMail(request, inputForm);

		return new ModelAndView("success" , model);
	}

	/**
	 * ���m�点�̒ʒm���[���𑗐M����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param inputForm ���͒l
	 */
	protected void sendInformationMail(HttpServletRequest request,
			InformationForm inputForm) {

		// �폜����ꍇ ���A���M���Ȃ�
		if (!"delete".equals(inputForm.getCommand())
				&& "1".equals(inputForm.getMailFlg())) {
			// ���[���e���v���[�g���ݒ肳��Ă��Ȃ��ꍇ�̓��[���̒ʒm���s��Ȃ��B
			if (this.sendInformationTemplate == null) {
				log.warn("sendInformationTemplate is null.");
				return;
			}

			// ���[���e���v���[�g�Ŏg�p����p�����[�^��ݒ肷��B
			this.sendInformationTemplate.setParameter("inputForm", inputForm);
			//
			// ���[�����M
			this.sendInformationTemplate.send();
		}
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
		InformationFormFactory factory = InformationFormFactory.getInstance(request);

		model.put("searchForm", factory.createInformationSearchForm(request));
		model.put("inputForm", factory.createInformationForm(request));

		return model;

	}



	/**
	 * �����̐U�蕪���Ǝ��s���s���B<br/>
	 * <br/>
 	 * @param model View �w�ֈ����n�� model �I�u�W�F�N�g
	 * @param inputForm ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
	 * @param loginUser �^�C���X�^���v�X�V���Ɏg�p���郍�O�C�����[�U�[���
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException �X�V�Ώۂ����݂��Ȃ��ꍇ
	 */
	protected void execute(Map<String, Object> model, InformationForm inputForm, AdminUserInterface loginUser)
			throws Exception, NotFoundException {

		if (this.mode.equals("insert")){
			insert(model, inputForm, loginUser);
        } else if (this.mode.equals("update")) {
			update(model, inputForm, loginUser);
        } else if (this.mode.equals("delete")) {
			delete(model, inputForm, loginUser);
        } else {
        	// �z�肵�Ă��Ȃ��������[�h�̏ꍇ�A��O���X���[����B
        	throw new RuntimeException ("execute mode bad setting.");
        }

	}



	/**
	 * �V�K�o�^����<br/>
	 * �����œn���ꂽ���e�ł��m�点����ǉ�����B<br/>
	 * <br/>
 	 * @param model View �w�ֈ����n�� model �I�u�W�F�N�g
	 * @param inputForm ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
	 * @param loginUser �^�C���X�^���v�X�V���Ɏg�p���郍�O�C�����[�U�[���
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	protected void insert(Map<String, Object> model, InformationForm inputForm, AdminUserInterface loginUser)
			throws Exception {

		// �V�K�o�^����
    	String informationNo = this.informationManager.addInformation(inputForm, (String)loginUser.getUserId());
    	inputForm.setInformationNo(informationNo);
	}



	/**
	 * �X�V�o�^����<br/>
	 * �����œn���ꂽ���e�ł��m�点�����X�V����B<br/>
	 * <br/>
 	 * @param model View �w�ֈ����n�� model �I�u�W�F�N�g
	 * @param inputForm ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
	 * @param loginUser �^�C���X�^���v�X�V���Ɏg�p���郍�O�C�����[�U�[���
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException �X�V�Ώۂ����݂��Ȃ��ꍇ
	 */
	protected void update(Map<String, Object> model, InformationForm inputForm, AdminUserInterface loginUser)
			throws Exception, NotFoundException {

		// �X�V����
    	this.informationManager.updateInformation(inputForm, (String)loginUser.getUserId());
	}



	/**
	 * �폜����<br/>
	 * �����œn���ꂽ���e�ł��m�点�����폜����B<br/>
	 * <br/>
 	 * @param model View �w�ֈ����n�� model �I�u�W�F�N�g
	 * @param inputForm ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
	 * @param loginUser �^�C���X�^���v�X�V���Ɏg�p���郍�O�C�����[�U�[���
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	protected void delete(Map<String, Object> model, InformationForm inputForm, AdminUserInterface loginUser)
			throws Exception {

		// �폜����
    	this.informationManager.delInformation(inputForm);
	}

}
