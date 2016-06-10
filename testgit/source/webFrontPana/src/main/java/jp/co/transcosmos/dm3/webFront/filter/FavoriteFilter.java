package jp.co.transcosmos.dm3.webFront.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.core.model.FavoriteManage;
import jp.co.transcosmos.dm3.core.model.information.InformationManageImpl;
import jp.co.transcosmos.dm3.core.util.FavoriteCookieUtils;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.servlet.BaseFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

/**
 * ���C�ɓ��茏����ݒ肷��t�B���^�[����<br/>
 * <br/>
 * ���C�ɓ��茏����cookie�����݂��Ȃ��ꍇ�ADB���猏�����擾���A���C�ɓ��茏����cookie��ݒ肷��<br/>
 * <br/>
 * <pre>
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.05.13	�V�K�쐬
 *
 * </pre>
 */
public class FavoriteFilter extends BaseFilter {

	private static final Log log = LogFactory.getLog(InformationManageImpl.class);
	
	/** ���C�ɓ��胁���e�i���X���s�� Model �I�u�W�F�N�g */
	private FavoriteManage favoriteManager;
	
	/**
	 * ���C�ɓ��胁���e�i���X���s�� Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param favoriteManager ���C�ɓ��胁���e�i���X���s�� Model �I�u�W�F�N�g
	 */
	public void setFavoriteManager(FavoriteManage favoriteManager) {
		this.favoriteManager = favoriteManager;
	}
	
	/**
	 * �t�B���^�[�̃��C������<br/>
	 * ���C�ɓ��茏����cookie�����݂��Ȃ��ꍇ�ADB���猏�����擾���A���C�ɓ��茏����cookie��ݒ肷��<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	 * @param chain �`�F�[����I�u�W�F�N�g
	 * @exception IOException
	 * @exception ServletException
	 */
	@Override
	protected void filterAction(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// ���C�ɓ���̓o�^�����̕\��
		String favoriteCnt = null;
		String cookieFavoriteCnt = (FavoriteCookieUtils.getInstance(request).getFavoriteCount(request));
		if (cookieFavoriteCnt == null) {
			// ���C�ɓ��茏����cookie�����݂��Ȃ��ꍇ
			// ���O�C���ς݂̃��[�U�����擾����
			LoginUser loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);
			
			if (loginUser != null) {
				// ���O�C���ς݂̏ꍇ�A���[�U�[ID���擾����
				String userId =(String) loginUser.getUserId();
				try {
					favoriteCnt = String.valueOf(favoriteManager.getFavoriteCnt(userId));
				} catch (Exception e) {
					log.warn("Failed To Set favoriteCount");
				}
			}
		}
		if (!StringUtils.isEmpty(favoriteCnt)) {
			// ���C�ɓ��茏����cookie��ݒ肷��
			FavoriteCookieUtils.getInstance(request).setFavoriteCount(
					request, response, Integer.valueOf(favoriteCnt));
			// ���C�ɓ��茏������ʂɕԋp
			request.setAttribute(FavoriteCookieUtils.FAVORITE_COUNT_KEY, favoriteCnt);
		} else {
			request.setAttribute(FavoriteCookieUtils.FAVORITE_COUNT_KEY, cookieFavoriteCnt);
		}

		// ���C�ɓ��茏����cookie�����݂���ꍇ�A�������Ȃ�
		// ���̏����� Chain ����B
		chain.doFilter(request, response);
	}
	

}
