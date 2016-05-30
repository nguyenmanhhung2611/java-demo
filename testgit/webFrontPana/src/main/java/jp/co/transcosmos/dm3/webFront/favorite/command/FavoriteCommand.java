package jp.co.transcosmos.dm3.webFront.favorite.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.util.FavoriteCookieUtils;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.favorite.PanaFavoriteManageImpl;
import jp.co.transcosmos.dm3.corePana.vo.HousingStatusInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * ���C�ɓ���o�^
 * ���C�ɓ���o�^��ʂ�\������B
 *
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *
 * �S����       �C����     �C�����e
 * ------------ ----------- -----------------------------------------------------
 * gao.long     2015.04.24  �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class FavoriteCommand implements Command {

	/** �������p Model �I�u�W�F�N�g */
	private PanaFavoriteManageImpl panaFavoriteManager;

	/** �����X�e�[�^�X���pDAO **/
	private DAO<HousingStatusInfo> housingStatusInfoDAO;

	/** ���ʃR�[�h�ϊ����� */
	private CodeLookupManager codeLookupManager;

	/** ���ʃp�����[�^�I�u�W�F�N�g */
	private PanaCommonParameters commonParameters;

	/**
	 * �������p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param PanaFavoriteManageImpl
	 *            �������p Model �I�u�W�F�N�g
	 */
	public void setPanaFavoriteManager(PanaFavoriteManageImpl panaFavoriteManager) {
		this.panaFavoriteManager = panaFavoriteManager;
	}

	/**
	 * �����X�e�[�^�X���pDAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param housingInfoDAO �����X�e�[�^�X���p DAO
	 */
	public void setHousingStatusInfoDAO(DAO<HousingStatusInfo> housingStatusInfoDAO) {
		this.housingStatusInfoDAO = housingStatusInfoDAO;
	}

	/**
	 * ���ʃR�[�h�ϊ��I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param codeLookupManager
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * ���ʃp�����[�^�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param commonParameters ���ʃp�����[�^�I�u�W�F�N�g
	 */
	public void setCommonParameters(PanaCommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * �����ꗗ��ʕ\������<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g
	 * @param response
	 *            HTTP ���X�|���X
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// ���O�C�����[�U�[�̏����擾
		MypageUserInterface loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);

		// ���[�UID���擾
		String userId = null;

		// ����t���O
		boolean memberFlg = false;

		if (loginUser != null) {
			// ���[�UID
			userId = (String) loginUser.getUserId();

			// ����t���O
			memberFlg = true;
		}

		// �����ԍ����擾
		String sysHousingCd = request.getParameter("sysHousingCd");

		// �����X�e�[�^�X���
		HousingStatusInfo housingStatusInfo = housingStatusInfoDAO.selectByPK(sysHousingCd);

		// ����J���ǂ������f����B
		boolean hiddenFlg = housingStatusInfo != null && PanaCommonConstant.HIDDEN_FLG_PUBLIC.equals(housingStatusInfo.getHiddenFlg());

		// ���b�Z�[�W
		String messageId = null;

		// ��������J�̏ꍇ
		if (memberFlg && hiddenFlg) {

			// ���C�ɓ��蕨���e�[�u�����猏�����擾����B
			int favoriteInfoCnt = this.panaFavoriteManager.getFavoriteInfoCnt(userId);

			// �I���������������C�ɓ�����e�[�u���ɓo�^����B
			messageId = this.panaFavoriteManager.addPanaFavorite(userId, sysHousingCd);

			if ("0".equals(messageId)) {
				// �o�^�����A���C�ɓ���̌�����ێ�����Cookie�̒l + 1
				FavoriteCookieUtils.getInstance(request).setFavoriteCount(request, response, favoriteInfoCnt + 1);
			}

		} else {

			// ���b�Z�[�W
			messageId = "3";
		}

		// �擾�����f�[�^�������_�����O�w�֓n��
		Map<String, String> map = new HashMap<String, String>();

		map.put("alertMessage", getMessage(messageId));

		return new ModelAndView("success", map);
	}

	/**
	 * �G���[���b�Z�[�W���擾����B<br/>
	 * <br/>
	 * @param messageId ���b�Z�[�WID
	 * @return ���b�Z�[�W
	 */
	private String getMessage(String messageId) {
		String key = null;
		switch (messageId) {
		case "0":
			key = "favoriteAddSuccess";
			break;
		case "1":
			key = "favoriteAddOver";
			break;
		case "2":
			key = "favoriteAddAlready";
			break;
		default:
			key = "favoriteAddError";
			break;
		}
		return this.codeLookupManager.lookupValue("errorTemplates", key).replace("[#fieldName]", String.valueOf(this.commonParameters.getMaxFavoriteInfoCnt()));
	}
}
