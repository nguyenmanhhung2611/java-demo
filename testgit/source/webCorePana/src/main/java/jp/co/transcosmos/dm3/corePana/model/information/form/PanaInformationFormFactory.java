package jp.co.transcosmos.dm3.corePana.model.information.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.model.information.form.InformationForm;
import jp.co.transcosmos.dm3.core.model.information.form.InformationFormFactory;
import jp.co.transcosmos.dm3.core.model.information.form.InformationSearchForm;
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
public class PanaInformationFormFactory extends InformationFormFactory {

	/**
	 * ���m�点�̌������ʁA����ь����������i�[������ AdminUserSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� AdminUserSearchForm �C���X�^���X
	 */
	@Override
	public InformationSearchForm createInformationSearchForm(){
		return new PanaInformationSearchForm(lengthUtils);
	}

	/**
	 * ���m�点�̌������ʁA����ь����������i�[���� InformationSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * InformationSearchForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� InformationSearchForm �C���X�^���X
	 */
	@Override
	public InformationSearchForm createInformationSearchForm(HttpServletRequest request){
		PanaInformationSearchForm form = (PanaInformationSearchForm)createInformationSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * ���m�点�X�V���̓��͒l���i�[������ InformationForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� InformationForm �̃C���X�^���X
	 */
	@Override
	public InformationForm createInformationForm(){
		return new PanaInformationForm(lengthUtils, codeLookupManager);
	}

	/**
	 * ���m�点�X�V���̓��͒l���i�[������ InformationForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� InformationForm �̃C���X�^���X
	 */
	@Override
	public InformationForm createInformationForm(HttpServletRequest request){
		PanaInformationForm form = (PanaInformationForm)createInformationForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

}
