/**
 * 
 */
package jp.co.trancosmos.dm3.corePana.model.news.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.model.housing.form.HousingFormFactory;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingRequestForm;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author hiennt
 *
 */
public class NewsFormFactory {
	/** Form �𐶐����� Factory �� Bean ID */

	/** Form �𐶐����� Factory �� Bean ID */
	protected static String FACTORY_BEAN_ID = "newFormFactory";
	
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

	public static NewsFormFactory getInstance(HttpServletRequest request) {

		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (NewsFormFactory)springContext.getBean(NewsFormFactory.FACTORY_BEAN_ID);
	}
	
	public NewsSearchForm createNewsSearchForm(){
		return new NewsSearchForm(this.lengthUtils);
	}

	public NewsSearchForm createNewSearchForm(HttpServletRequest request){
		NewsSearchForm form = createNewsSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}
	
	public NewsRequestForm createNewsRequestForm(HttpServletRequest request){
		return new NewsRequestForm(this.lengthUtils, this.codeLookupManager);
	}

	public NewsRequestForm createNewForm(HttpServletRequest request){
		NewsRequestForm form = createNewsRequestForm(request);
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}
	
	
}
