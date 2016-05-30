package jp.co.trancosmos.dm3.corePana.model.news.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.model.news.form.NewsForm;
import jp.co.transcosmos.dm3.core.model.news.form.NewsFormFactory;
import jp.co.transcosmos.dm3.core.model.news.form.NewsSearchForm;
import jp.co.transcosmos.dm3.form.FormPopulator;

/**
 * <pre>
 * ���m�点�����E�ꗗ�p Form �� Factory �N���X
 * getInstance() ���ASpring ����C���X�^���X���擾���Ďg�p���鎖�B
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * zhang		2015.04.21	�V�K�쐬
 *
 * ���ӎ���
 * Factory �̃C���X�^���X�𒼐ڐ������Ȃ����B�@�K��getInstance() �Ŏ擾���鎖�B
 *
 * </pre>
 */
public class PanaNewsFormFactory extends NewsFormFactory {

	
	/**
	 * ���m�点�̌������ʁA����ь����������i�[������ AdminUserSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� AdminUserSearchForm �C���X�^���X
	 */
	public NewsSearchForm createInformationSearchForm(){
		return new PanaNewsSearchForm(lengthUtils);
	}

	/**
	 * ���m�点�̌������ʁA����ь����������i�[���� InformationSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * InformationSearchForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� InformationSearchForm �C���X�^���X
	 */
	public NewsSearchForm createInformationSearchForm(HttpServletRequest request){
		PanaNewsSearchForm form = (PanaNewsSearchForm)createInformationSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * ���m�点�X�V���̓��͒l���i�[������ InformationForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� InformationForm �̃C���X�^���X
	 */
	public NewsForm createNewsForm(){
		return new PanaNewsForm(lengthUtils, codeLookupManager);
	}

	/**
	 * ���m�点�X�V���̓��͒l���i�[������ InformationForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� InformationForm �̃C���X�^���X
	 */
	
	
	public NewsForm createNewsForm(HttpServletRequest request){
		PanaNewsForm form = (PanaNewsForm)createNewsForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

}
