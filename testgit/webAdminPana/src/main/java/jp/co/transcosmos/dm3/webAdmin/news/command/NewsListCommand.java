package jp.co.transcosmos.dm3.webAdmin.news.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.NewsManage;
import jp.co.transcosmos.dm3.core.model.news.form.NewsForm;
import jp.co.transcosmos.dm3.core.model.news.form.NewsFormFactory;
import jp.co.transcosmos.dm3.core.model.news.form.NewsSearchForm;
import jp.co.transcosmos.dm3.utils.CommonLogging;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * ------------ ----------- -----------------------------------------------------
 * @author hiennt
 *
 * </pre>
 */
public class NewsListCommand implements Command {

	private String mode;

	/** ���m�点�����e�i���X���s�� Model �I�u�W�F�N�g */
	
	private NewsManage newsManager;
	
	public void setNewsManager(NewsManage newsManager) {
		this.newsManager = newsManager;
	}

	/** �P�y�[�W�̕\������ */
	private int rowsPerPage;

    /** �F�ؗp���M���O�N���X */

	/** �y�[�W�̕\���� */
    private int visibleNavigationPageCount;

	/**
	 * �������[�h��ݒ肷��<br/>
	 * <br/>
	 * @param mode
	 *            search = ���������A csv = CSV�o�͏����A delete = �폜����
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * 1�y�[�W������\������ݒ肷��B<br>
	 *
	 * @param rowsPerPage
	 *            1�y�[�W������\����
	 */
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

    /**
     * �y�[�W�̕\������ݒ肷��B<br>
     * @param visibleNavigationPageCount �y�[�W��
     */
    public void setVisibleNavigationPageCount(int visibleNavigationPageCount) {
        this.visibleNavigationPageCount = visibleNavigationPageCount;
    }

	/**
	 * ���m�点��񃊃N�G�X�g����<br>
	 * ���m�点���̃��N�G�X�g���������Ƃ��ɌĂяo�����B <br>
	 *
	 * @param request
	 *            �N���C�A���g�����Http���N�G�X�g�B
	 * @param response
	 *            �N���C�A���g�ɕԂ�Http���X�|���X�B
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
        Map<String, Object> model = createModel(request);
        
        NewsSearchForm form = (NewsSearchForm) model.get("searchForm");
        NewsForm inputForm = (NewsForm) model.get("inputForm");
        
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        
        if (!form.validate(errors)) {
			model.put("errors", errors);
			return new ModelAndView("validFail", model);
        }

        if ("delete".equals(this.mode)) { 
        	this.newsManager.delNews(inputForm);
        }
        
        model.put("hitcont", this.newsManager.searchAdminNews(form));
		model.put("command", "list");

		return new ModelAndView("success", model);
	}

	/**
	 * ���N�G�X�g�p�����[�^���� Form �I�u�W�F�N�g���쐬����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^���ݒ肳�ꂽ�t�H�[���I�u�W�F�N�g
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		NewsFormFactory factory = NewsFormFactory.getInstance(request);
		NewsSearchForm searchForm = factory.createInformationSearchForm(request);
		NewsForm inputForm = factory.createInformationForm(request);

		//set row in paging
		searchForm.setRowsPerPage(this.rowsPerPage);
		
        searchForm.setVisibleNavigationPageCount(this.visibleNavigationPageCount);
        model.put("searchForm", searchForm);
        model.put("inputForm", inputForm);

		return model;

	}
}
