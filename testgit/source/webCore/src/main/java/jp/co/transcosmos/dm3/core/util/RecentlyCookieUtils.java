package jp.co.transcosmos.dm3.core.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.utils.ServletUtils;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * �ŋߌ��������pcookie����.
 * <p>
 * �ŋߌ��������̃V�X�e������ID�̎擾<br/>
 * �ŋߌ��������̃V�X�e������ID��ݒ肷��<br/>
 * �ŋߌ��������̌����v���X�P<br/>
 * �ŋߌ��������̌�����ݒ肷��<br/>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.05.08	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class RecentlyCookieUtils {

	/** ���̃��[�e�B���e �� Bean ID */
	protected static String FACTORY_BEAN_ID = "recentlyCookieUtils";
	
	/**
	 * �ŋߌ��������̌����ێ��p Cookie �� Key ��<br/>
	 */
	public static final String HISTORY_COUNT_KEY = "historyCount";
	
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
	 * RecentlyCookieUtils �̃C���X�^���X���擾����B<br/>
	 * Spring �̃R���e�L�X�g����A recentlyCookieUtils �Œ�`���ꂽ RecentlyCookieUtils ��
	 * �C���X�^���X���擾����B<br/>
	 * �擾�����C���X�^���X�́ARecentlyCookieUtils ���p�������g���N���X�����A�����ꍇ������B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return recentlyCookieUtils�A�܂��͌p�����Ċg�������N���X�̃C���X�^���X
	 */
	public static RecentlyCookieUtils getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (RecentlyCookieUtils)springContext.getBean(RecentlyCookieUtils.FACTORY_BEAN_ID);
	}
	
	/**
	 * �ŋߌ��������̌�����cookie�ɐݒ肷��B<br/>
	 * count������cookie�ɐݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	 * @param count ���C�ɓ��茏��
	 */
	public void setRecentlyCount(HttpServletRequest request, HttpServletResponse response, int count){
		// count������cookie�ɐݒ肷��
    	ServletUtils.addCookie(response, request.getContextPath(), HISTORY_COUNT_KEY, 
    			String.valueOf(count), this.commonParameters.getCookieDomain());
	}
	
	/**
	 * �ŋߌ��������̌�����cookie����擾����B<br/>
	 * count�������擾����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 */
	public String getRecentlyCount(HttpServletRequest request){
		// count�������擾����
		return  ServletUtils.findFirstCookieValueByName(request, HISTORY_COUNT_KEY);
	}
	
	/**
	 * �ŋߌ��������̌���cookie���폜����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	 */
	public void delFavoriteCount(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// �ŋߌ��������̌���cookie���폜����
		ServletUtils.delCookie(response, request.getContextPath(),
				HISTORY_COUNT_KEY, this.commonParameters.getCookieDomain());
	}
	
}
