package jp.co.transcosmos.dm3.webFront.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.core.util.RecentlyCookieUtils;
import jp.co.transcosmos.dm3.corePana.vo.RecentlyInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.servlet.BaseFilter;

/**
 * �ŋߌ��������̌�����ݒ肷��t�B���^�[����<br/>
 * <br/>
 * �ŋߌ��������̌�����cookie�����݂��Ȃ��ꍇ�ADB���猏�����擾���A�ŋߌ��������̌�����cookie��ݒ肷��<br/>
 * <br/>
 * <pre>
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.05.13	�V�K�쐬
 * </pre>
 */
public class RecentlyFilter extends BaseFilter {
	
	/** �ŋߌ����������p DAO */
	private DAO<RecentlyInfo> recentlyInfoDAO;
	
	/**
	 * �ŋߌ����������p DAO��ݒ肷��B<br/>
	 * <br/>
	 * @param recentlyInfoDAO �ŋߌ����������p DAO
	 */
	public void setRecentlyInfoDAO(DAO<RecentlyInfo> recentlyInfoDAO) {
		this.recentlyInfoDAO = recentlyInfoDAO;
	}

	/**
	 * �t�B���^�[�̃��C������<br/>
	 * �ŋߌ��������̌�����cookie�����݂��Ȃ��ꍇ�ADB���猏�����擾���A�ŋߌ��������̌�����cookie��ݒ肷��<br/>
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
		// �ŋߌ��������̌������擾
		String recentlyCnt = null;
		String cookieRecentlyCnt = RecentlyCookieUtils.getInstance(request).getRecentlyCount(request);
		if (cookieRecentlyCnt == null) {
			// �ŋߌ��������̌�����cookie�����݂��Ȃ��ꍇ
			// ���[�U�[ID���擾����
			String userId = (String) FrontLoginUserUtils.getInstance(request)
					.getAnonLoginUserInfo(request, response).getUserId();

			// �ŋߌ��������̌������擾����
			DAOCriteria criteria = new DAOCriteria();
			criteria.addWhereClause("userId", userId);
			List<RecentlyInfo> recentlyInfos = this.recentlyInfoDAO.selectByFilter(criteria);
			int numRows = 0;
			if (recentlyInfos != null) {
				numRows = recentlyInfos.size();
			}
			recentlyCnt = String.valueOf(numRows);
		}
		
		if (!StringUtils.isEmpty(recentlyCnt)) {
			// �ŋߌ��������̌�����cookie��ݒ肷��
			RecentlyCookieUtils.getInstance(request).setRecentlyCount(request,
					response, Integer.valueOf(recentlyCnt));
			// ���C�ɓ��茏������ʂɕԋp
			request.setAttribute(RecentlyCookieUtils.HISTORY_COUNT_KEY, recentlyCnt);
		} else {
			// �ŋߌ��������̌�������ʂɕԋp
			request.setAttribute(RecentlyCookieUtils.HISTORY_COUNT_KEY, cookieRecentlyCnt);
		}

		// ���̏����� Chain ����B
		chain.doFilter(request, response);
		
	}

}
