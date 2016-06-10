package jp.co.transcosmos.dm3.core.model.news.form;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

/**
 * <pre>
 * ���m�点�����E�ꗗ�p Form �� Factory �N���X
 * getInstance() ���ASpring ����C���X�^���X���擾���Ďg�p���鎖�B
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 *@author hiennt
 *
 * </pre>
 */
public class NewsFormFactory {

	/** Form �𐶐����� Factory �� Bean ID */
	protected static String FACTORY_BEAN_ID = "newsFormFactory";
	
	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;
	
	/** �����O�X�o���f�[�V�����p���[�e�B���e�B */
    protected LengthValidationUtils lengthUtils;


	/**
	 * ���ʃR�[�h�ϊ��I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param codeLookupManager
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}
	
	/**
	 * �����O�X�o���f�[�V�����p���[�e�B���e�B��ݒ肷��B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����p���[�e�B���e�B
	 */
	public void setLengthUtils(LengthValidationUtils lengthUtils) {
		this.lengthUtils = lengthUtils;
	}


	
	/**
	 * SearchFormFactory �̃C���X�^���X���擾����B<br/>
	 * Spring �̃R���e�L�X�g����A SearchFormFactory �Œ�`���ꂽ InformationFormFactory ��
	 * �C���X�^���X���擾����B<br/>
	 * �擾�����C���X�^���X�́ASearchFormFactory ���p�������g���N���X�����A�����ꍇ������B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return AdminUserFormFactory�A�܂��͌p�����Ċg�������N���X�̃C���X�^���X
	 */
	public static NewsFormFactory getInstance(HttpServletRequest request) {

		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (NewsFormFactory)springContext.getBean(NewsFormFactory.FACTORY_BEAN_ID);
	}
	
	

	/**
	 * ���m�点�̌������ʁA����ь����������i�[������ AdminUserSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� AdminUserSearchForm �C���X�^���X 
	 */
	public NewsSearchForm createInformationSearchForm(){
		return new NewsSearchForm(this.lengthUtils);
	}


	
	/**
	 * ���m�点�̌������ʁA����ь����������i�[���� InformationSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * NewsSearchForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� InformationSearchForm �C���X�^���X 
	 */
	public NewsSearchForm createInformationSearchForm(HttpServletRequest request){
		NewsSearchForm form = createInformationSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}

	
	
	/**
	 * ���m�点�X�V���̓��͒l���i�[������ NewsForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� NewsForm �̃C���X�^���X
	 */
	public NewsForm createInformationForm(){
		return new NewsForm(this.lengthUtils, this.codeLookupManager);
	}

	
	/**
	 * ���m�点�X�V���̓��͒l���i�[������ NewsForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� NewsForm �̃C���X�^���X
	 */
	public NewsForm createInformationForm(HttpServletRequest request){
		NewsForm form = createInformationForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}
	
}
