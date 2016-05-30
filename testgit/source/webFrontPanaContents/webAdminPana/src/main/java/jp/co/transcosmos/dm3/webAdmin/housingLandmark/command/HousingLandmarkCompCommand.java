package jp.co.transcosmos.dm3.webAdmin.housingLandmark.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.adminCore.building.command.BuildingLandmarkCompCommand;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * �n������͉��
 *
 *
 * �y�X�V�o�^�̏ꍇ�z
 *     �E���N�G�X�g�p�����[�^�̎���݂̂��s���B
 *
 * �y���N�G�X�g�p�����[�^�icommand�j �� "back"�̏ꍇ�z
 *     �E���N�G�X�g�p�����[�^�œn���ꂽ���͒l����͉�ʂɕ\������B�@�i���͊m�F��ʂ��畜�A�����P�[�X�B�j
 *
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * liu.yandong		2015.03.17	�V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class HousingLandmarkCompCommand extends BuildingLandmarkCompCommand {

	/** �������p Model �I�u�W�F�N�g */
	private PanaHousingPartThumbnailProxy panaHousingManager;


	/**
	 * @param panaHousingManager �Z�b�g���� panaHousingManager
	 */
	public void setPanaHousingManager(
			PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
	}


	/**
	 * �n������͉�ʕ\������<br>
	 * <br>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	*/
    @Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	String sysHousingCd = request.getParameter("sysHousingCd");

		// ���������擾����B
		Housing housing = this.panaHousingManager.searchHousingPk(sysHousingCd, true);

		// �f�[�^�̑��݂��Ȃ��ꍇ�B
		if (housing == null) {
			throw new NotFoundException();
		}
        // model �I�u�W�F�N�g���擾����B
        ModelAndView modelAndView = super.handleRequest(request, response);

		// ���O�C�����[�U�[�̏����擾����B�@�i�^�C���X�^���v�̍X�V�p�j
		AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);
		// ������{���̃^�C���X�^���v�����X�V����
        this.panaHousingManager.updateEditTimestamp(sysHousingCd, String.valueOf(loginUser.getUserId()));

		return modelAndView;
	}

}
