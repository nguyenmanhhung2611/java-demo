package jp.co.transcosmos.dm3.webAdmin.news.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.NewsManage;
import jp.co.transcosmos.dm3.core.model.news.form.NewsForm;
import jp.co.transcosmos.dm3.core.model.news.form.NewsFormFactory;
import jp.co.transcosmos.dm3.core.model.news.form.NewsSearchForm;
import jp.co.transcosmos.dm3.core.vo.News;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.web.servlet.ModelAndView;

/**
 *
 * ------------ ----------- -----------------------------------------------------
 * @author hiennt
 *
 * </pre>
 */
public class DelInputCommand implements Command {

	/** ���m�点�����e�i���X���s�� Model �I�u�W�F�N�g */
	protected NewsManage newsManager;;
	
	/** �������[�h (insert = �V�K�o�^�����A update=�X�V����)*/
	protected String mode;
	
	
	/**
	 * ���m�点�����e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param newsManager ���m�点�����e�i���X�� model �I�u�W�F�N�g
	 */
	public void setNewsManager(NewsManage newsManager) {
		this.newsManager = newsManager;
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
	 * ���m�点�����͉�ʕ\������<br>
	 * <br>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	*/
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
        Map<String, Object> model = createModel(request);
        NewsForm inputForm = (NewsForm) model.get("inputForm"); 
        NewsSearchForm searchForm = (NewsSearchForm) model.get("searchForm"); 

		// �X�V�����̏ꍇ�A�����ΏۂƂȂ��L�[�l���p�����[�^�œn����Ă��Ȃ��ꍇ�A��O���X���[����B
		if ("delete".equals(this.mode) && StringValidateUtil.isEmpty(searchForm.getNewsId())){
			throw new RuntimeException ("pk value is null.");
		}

        // �V�K�o�^���[�h�ŏ�����ʕ\���̏ꍇ�A�f�[�^�̎擾�͍s��Ȃ��B
        if ("insert".equals(this.mode)) {
        	return new ModelAndView("success", model);
        }
        
       // ���m�点�����擾
        News news = this.newsManager.searchTopNewsPk(searchForm.getNewsId());
        
        // �Y������f�[�^�����݂��Ȃ��ꍇ�A�Y��������ʂ�\������B
        if (news == null) {
        	return new ModelAndView("notFound", model);
        }
        
		// command �p�����[�^�� "back" �̏ꍇ�A���͊m�F��ʂ���̕��A�Ȃ̂ŁA�c�a���珉���l���擾���Ȃ��B
		// �i���N�G�X�g�p�����[�^����擾�����l���g�p����B�j
		String command = inputForm.getCommand();
		if (command != null && "back".equals(command)) {
			
			return new ModelAndView("success", model);
		}
		
		inputForm.setDefaultData(news);
		
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
		NewsFormFactory factory = NewsFormFactory.getInstance(request);

		// ���������A����сA��ʃR���g���[���p�����[�^���擾����B
		NewsSearchForm searchForm = factory.createInformationSearchForm(request);
		model.put("searchForm", searchForm);

		
		// ���̓t�H�[���̎󂯎��́Acommand �p�����[�^�̒l�ɂ���ĈقȂ�B
		// command �p�����[�^���n����Ȃ��ꍇ�A���͉�ʂ̏����\���Ƃ݂Ȃ��A��̃t�H�[���𕜋A����B
		// command �p�����[�^�� "back" ���n���ꂽ�ꍇ�A���͊m�F��ʂ���̕��A���Ӗ�����B
		// ���̏ꍇ�̓��N�G�X�g�p�����[�^���󂯎��A���͉�ʂ֏����\������B
		// �����A"back" �ȊO�̒l���n���ꂽ�ꍇ�Acommand �p�����[�^���n����Ȃ��ꍇ�Ɠ����Ɉ����B

		NewsForm inputForm = factory.createInformationForm(request);
		String command = inputForm.getCommand();
		
		if ("back".equals(command)){
			// �߂�{�^���̏ꍇ�́A���N�G�X�g�p�����[�^�̒l��ݒ肵�� Form �𕜋A����B
			model.put("inputForm", inputForm);
		} 
		model.put("inputForm", inputForm);
		return model;

	}

}
