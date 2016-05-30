package jp.co.transcosmos.dm3.webFront.favorite.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.model.favorite.PanaFavoriteManageImpl;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * ���C�ɓ��蕨���폜
 *
 *
 * �S����       �C����     �C�����e
 * ------------ ----------- -----------------------------------------------------
 * tan.tianyun   2015.05.06  �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class FavoriteDeleteCommand implements Command {

	/** ���C�ɓ�����p Model �I�u�W�F�N�g */
	private PanaFavoriteManageImpl panaFavoriteManager;

	/**
	 * ���C�ɓ�����p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param PanaFavoriteManageImpl
	 *            ���C�ɓ�����p Model �I�u�W�F�N�g
	 */
	public void setPanaFavoriteManager(PanaFavoriteManageImpl panaFavoriteManager) {
		this.panaFavoriteManager = panaFavoriteManager;
	}

	/**
	 * ���C�ɓ��蕨���폜<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g
	 * @param response
	 *            HTTP ���X�|���X
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sysHousingCd = request.getParameter("sysHousingCd");

		// ���O�C�����[�U�[�̏����擾
		MypageUserInterface loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);

		// ���[�UID���擾
		String userId = String.valueOf(loginUser.getUserId());

		if(StringValidateUtil.isEmpty(sysHousingCd) || StringValidateUtil.isEmpty(userId)) {
			throw new RuntimeException("�V�X�e������CD�܂��̓��[�U�[ID���w�肳��Ă��܂���.");
		}

		// ���C�ɓ��蕨���폜
		this.panaFavoriteManager.delFavorite(userId, sysHousingCd);

		return new ModelAndView("success", "delete", "ok");
	}

}
