package jp.co.transcosmos.dm3.core.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.utils.ServletUtils;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * ���C�ɓ���pcookie����.
 * <p>
 * ���C�ɓ��茏���̎擾<br/>
 * ���C�ɓ��茏����cookie�ɐݒ肷�邱��<br/>
 * ���C�ɓ��茏���v���X�P<br/>
 * ���C�ɓ��茏���}�C�i�X�P<br/>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.05.07	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class FavoriteCookieUtils {
	
	/** ���̃��[�e�B���e �� Bean ID */
	protected static String FACTORY_BEAN_ID = "favoriteCookieUtils";
	
	/**
	 * ���C�ɓ��茏���p Cookie �� Key ��<br/>
	 * Mypage �Ƀ��O�C���ς̏ꍇ�A���� Key ���� Cookie �ɏ������ށB<br/>
	 * Mypage ���烍�O�A�E�g�����ꍇ�A���� Key ���� Cookie ����폜����B<br/>
	 */
	public static final String FAVORITE_COUNT_KEY = "favoriteCount";

	/** ���ʃp�����[�^�I�u�W�F�N�g */
	protected CommonParameters commonParameters;
	
	/**
	 * ���ʃp�����[�^�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param commonParameters
	 */
	public void setCommonParameters(CommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}
	
	/**
	 * FavoriteCookieUtils �̃C���X�^���X���擾����B<br/>
	 * Spring �̃R���e�L�X�g����A favoriteCookieUtils �Œ�`���ꂽ FavoriteCookieUtils ��
	 * �C���X�^���X���擾����B<br/>
	 * �擾�����C���X�^���X�́AFavoriteCookieUtils ���p�������g���N���X�����A�����ꍇ������B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return favoriteCookieUtils�A�܂��͌p�����Ċg�������N���X�̃C���X�^���X
	 */
	public static FavoriteCookieUtils getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (FavoriteCookieUtils)springContext.getBean(FavoriteCookieUtils.FACTORY_BEAN_ID);
	}
	
	/**
	 * ���C�ɓ���o�^������cookie�Ɏ擾����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 */
	public String getFavoriteCount(HttpServletRequest request){
		// ���C�ɓ���o�^������cookie�Ɏ擾����
		return  ServletUtils.findFirstCookieValueByName(request, FAVORITE_COUNT_KEY);
	}

	/**
	 * ���C�ɓ���o�^������cookie�ɐݒ肷��B<br/>
	 * count������cookie�ɐݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	 * @param count ���C�ɓ��茏��
	 */
	public void setFavoriteCount(HttpServletRequest request, HttpServletResponse response, int count){
		// count������cookie�ɐݒ肷��
    	ServletUtils.addCookie(response, request.getContextPath(), FAVORITE_COUNT_KEY, 
    			String.valueOf(count), this.commonParameters.getCookieDomain());
	}

	/**
	 * cookie�擾���邨�C�ɓ��茏�����v���X1�ɂ���B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	 */
	public void favoriteCountUp(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// cookie���炨�C�ɓ��茏�����擾����
		String favoriteCount = getFavoriteCount(request);
		int count = 0;
		if (favoriteCount != null) {
			count = Integer.valueOf(favoriteCount);
		}
		// count����+1��cookie�ɐݒ肷��
		setFavoriteCount(request, response, count + 1);
	}
	
	/**
	 * cookie�擾���邨�C�ɓ��茏�����}�C�i�X1�ɂ���B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	 */
	public void favoriteCountDown(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// cookie���炨�C�ɓ��茏�����擾����
		String favoriteCount = getFavoriteCount(request);
		int count = 0;
		if (favoriteCount != null && Integer.valueOf(favoriteCount) > 0) {
			count = Integer.valueOf(favoriteCount) - 1;
		}
		// count����-1��cookie�ɐݒ肷��
		setFavoriteCount(request, response, count);
	}
	
	/**
	 * ���C�ɓ��茏��cookie���폜����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	 */
	public void delFavoriteCount(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// ���C�ɓ��茏��cookie���폜����
		ServletUtils.delCookie(response, request.getContextPath(),
				FAVORITE_COUNT_KEY, this.commonParameters.getCookieDomain());
	}
	
}
