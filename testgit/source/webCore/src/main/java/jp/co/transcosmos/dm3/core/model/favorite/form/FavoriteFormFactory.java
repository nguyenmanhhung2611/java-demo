package jp.co.transcosmos.dm3.core.model.favorite.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.form.FormPopulator;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * <pre>
 * ���C�ɓ����񌟍��p Form �� Factory �N���X
 * getInstance() ���ASpring ����C���X�^���X���擾���Ďg�p���鎖�B
 * 
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * T.Nakamura	2015.03.23	�V�K�쐬
 * 
 * ���ӎ���
 * Factory �̃C���X�^���X�𒼐ڐ������Ȃ����B�@�K��getInstance() �Ŏ擾���鎖�B
 * 
 * </pre>
 */
public class FavoriteFormFactory {

	/** Form �𐶐����� Factory �� Bean ID */
	protected static String FACTORY_BEAN_ID = "favoriteFormFactory";

	/**
	 * FavoriteFormFactory �̃C���X�^���X���擾����B<br/>
	 * Spring �̃R���e�L�X�g����A favoriteFormFactory �Œ�`���ꂽ FavoriteFormFactory ��
	 * �C���X�^���X���擾����B<br/>
	 * �擾�����C���X�^���X�́AFavoriteFormFactory ���p�������g���N���X�����A�����ꍇ������B<br/>
	 * <br/>
	 * 
	 * @param request HTTP ���N�G�X�g
	 * @return FavoriteFormFactory�A�܂��͌p�����Ċg�������N���X�̃C���X�^���X
	 */
	public static FavoriteFormFactory getInstance(HttpServletRequest request) {
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (FavoriteFormFactory) springContext.getBean(FavoriteFormFactory.FACTORY_BEAN_ID);
	}

	/**
	 * ���C�ɓ�����̌����������i�[������ FavoriteSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * 
	 * @return ��� FavoriteSearchForm �C���X�^���X
	 */
	public FavoriteSearchForm createFavoriteSearchForm() {
		return new FavoriteSearchForm();
	}

	/**
	 * ���C�ɓ�����̌����������i�[���� FavoriteSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * FavoriteSearchForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� FavoriteSearchForm �C���X�^���X
	 */
	public FavoriteSearchForm createFavoriteSearchForm(HttpServletRequest request) {
		FavoriteSearchForm form = createFavoriteSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

}
