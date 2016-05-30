package jp.co.transcosmos.dm3.core.model.housingRequest.form;

import javax.servlet.http.HttpServletRequest;
import jp.co.transcosmos.dm3.form.FormPopulator;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * <pre>
 * �������N�G�X�g�p Form �� Factory �N���X
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
public class RequestSearchFormFactory {

	/** Form �𐶐����� Factory �� Bean ID */
	protected static String FACTORY_BEAN_ID = "requestSearchFormFactory";



	/**
	 * RequestSearchFormFactory �̃C���X�^���X���擾����B<br/>
	 * Spring �̃R���e�L�X�g����A requestSearchFormFactory �Œ�`���ꂽ RequestSearchFormFactory ��
	 * �C���X�^���X���擾����B<br/>
	 * �擾�����C���X�^���X�́ARequestSearchFormFactory ���p�������g���N���X�����A�����ꍇ������B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return RequestSearchFormFactory�A�܂��͌p�����Ċg�������N���X�̃C���X�^���X
	 */
	public static RequestSearchFormFactory getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (RequestSearchFormFactory)springContext.getBean(RequestSearchFormFactory.FACTORY_BEAN_ID);
	}



	/**
	 * �������N�G�X�g�ɊY�����镨������������p�����[�^�������� RequestSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� RequestSearchForm �C���X�^���X 
	 */
	public RequestSearchForm createRequestSearchForm(){
		return new RequestSearchForm();
	}



	/**
	 * �������N�G�X�g�ɊY�����镨������������p�����[�^���i�[���� RequestSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * RequestSearchForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� RequestSearchForm �C���X�^���X 
	 */
	public RequestSearchForm createRequestSearchForm(HttpServletRequest request){
		RequestSearchForm form = createRequestSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}

}
