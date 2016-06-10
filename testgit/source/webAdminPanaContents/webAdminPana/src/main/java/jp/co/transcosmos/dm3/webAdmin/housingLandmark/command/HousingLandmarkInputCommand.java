package jp.co.transcosmos.dm3.webAdmin.housingLandmark.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.adminCore.building.command.BuildingLandmarkInputCommand;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.corePana.model.building.form.PanaBuildingLandmarkForm;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;

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
public class HousingLandmarkInputCommand extends BuildingLandmarkInputCommand {

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
        // model �I�u�W�F�N�g���擾����B
        ModelAndView modelAndView = super.handleRequest(request, response);
        Map<String, Object> model = modelAndView.getModel();

        // Form�擾
        PanaBuildingLandmarkForm inputForm = (PanaBuildingLandmarkForm) model.get("inputForm");

		// ���������擾����B
		Housing housing = this.panaHousingManager.searchHousingPk(inputForm.getSysHousingCd(), true);

		// �f�[�^�̑��݂��Ȃ��ꍇ�B
		if (housing == null) {
			throw new NotFoundException();
		}

        // ������{�����擾����B
        HousingInfo housingInfo = ((HousingInfo) housing.getHousingInfo().getItems().get("housingInfo"));

        // �V�X�e������CD�A�������̂�ݒ�
        inputForm.setHousingCd(housingInfo.getHousingCd());
        inputForm.setDisplayHousingName(housingInfo.getDisplayHousingName());

		return modelAndView;
	}

}
