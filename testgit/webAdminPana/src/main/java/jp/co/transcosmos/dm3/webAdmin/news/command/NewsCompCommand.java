package jp.co.transcosmos.dm3.webAdmin.news.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.adminCore.mypage.command.MypageCompCommand;
import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.NewsManage;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.news.form.NewsForm;
import jp.co.transcosmos.dm3.core.model.news.form.NewsFormFactory;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * @author hiennt
 */
public class NewsCompCommand implements Command  {

	private static final Log log = LogFactory.getLog(MypageCompCommand.class);

	/** ���m�点�����e�i���X���s�� Model �I�u�W�F�N�g */
	protected NewsManage newsManager;

	/** �������[�h (insert = �V�K�o�^�����A update=�X�V�����Adelete=�폜����)*/
	protected String mode;

	/** ��L�[�ƂȂ� UserId �̕K�{�`�F�b�N���s���ꍇ�Atrue ��ݒ肷��B �i�f�t�H���g true�j�@*/
	protected boolean useUserIdValidation = true;

	/** Form �̃o���f�[�V���������s����ꍇ�Atrue ��ݒ肷��B�@�i�f�t�H���g true�j */
	protected boolean useValidation = true;

	

	/**
	 * �������[�h��ݒ肷��<br/>
	 * <br/>
	 * @param mode "insert" = �V�K�o�^�����A"update" = �X�V����
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	public NewsManage getNewsManager() {
		return newsManager;
	}

	public void setNewsManager(NewsManage newsManager) {
		this.newsManager = newsManager;
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
        NewsForm inputForm = (NewsForm) model.get("inputForm");


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
        	if (StringValidateUtil.isEmpty(inputForm.getNewsId())) throw new RuntimeException ("pk value is null.");
        }


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

		return new ModelAndView("success" , model);
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
		NewsFormFactory factory = NewsFormFactory.getInstance(request);

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
	protected void execute(Map<String, Object> model, NewsForm inputForm, AdminUserInterface loginUser)
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
	protected void insert(Map<String, Object> model, NewsForm inputForm, AdminUserInterface loginUser)
			throws Exception {

		// �V�K�o�^����
    	String newsId = this.newsManager.addNews(inputForm, (String)loginUser.getUserId());
    	inputForm.setNewsId(newsId);
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
	protected void update(Map<String, Object> model, NewsForm inputForm, AdminUserInterface loginUser)
			throws Exception, NotFoundException {

		// �X�V����
    	this.newsManager.updateNews(inputForm, (String)loginUser.getUserId());
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
	protected void delete(Map<String, Object> model, NewsForm inputForm, AdminUserInterface loginUser)
			throws Exception {

		// �폜����
    	this.newsManager.delNews(inputForm);
	}

}
