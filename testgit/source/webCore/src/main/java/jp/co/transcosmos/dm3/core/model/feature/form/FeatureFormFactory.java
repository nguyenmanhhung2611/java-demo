package jp.co.transcosmos.dm3.core.model.feature.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.form.FormPopulator;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * <pre>
 * ���W�p Form �� Factory �N���X
 * getInstance() ���ASpring ����C���X�^���X���擾���Ďg�p���鎖�B
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.11	�V�K�쐬
 *
 * ���ӎ���
 * Factory �̃C���X�^���X�𒼐ڐ������Ȃ����B�@�K��getInstance() �Ŏ擾���鎖�B
 *
 * </pre>
 */
public class FeatureFormFactory {

	/** Form �𐶐����� Factory �� Bean ID */
	protected static String FACTORY_BEAN_ID = "featureFormFactory";



	/**
	 * FeatureFormFactory �̃C���X�^���X���擾����B<br/>
	 * Spring �̃R���e�L�X�g����A featureFormFactory �Œ�`���ꂽ FeatureFormFactory ��
	 * �C���X�^���X���擾����B<br/>
	 * �擾�����C���X�^���X�́AFeatureFormFactory ���p�������g���N���X�����A�����ꍇ������B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return FeatureFormFactory�A�܂��͌p�����Ċg�������N���X�̃C���X�^���X
	 */
	public static FeatureFormFactory getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (FeatureFormFactory)springContext.getBean(FeatureFormFactory.FACTORY_BEAN_ID);
	}

	
	
	/**
	 * ���W�ɊY�����镨���̌����������i�[������ FeatureSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� FeatureSearchForm �C���X�^���X 
	 */
	public FeatureSearchForm createFeatureSearchForm(){
		return new FeatureSearchForm();
	}

	
	
	/**
	 * ���W�ɊY�����镨���̌����������i�[���� FeatureSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * FeatureSearchForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� FeatureSearchForm �C���X�^���X 
	 */
	public FeatureSearchForm createFeatureSearchForm(HttpServletRequest request){
		FeatureSearchForm form = createFeatureSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}

}
