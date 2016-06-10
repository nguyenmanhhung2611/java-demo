package jp.co.transcosmos.dm3.adminCore.building.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.BuildingManage;
import jp.co.transcosmos.dm3.core.model.building.Building;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingFormFactory;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingSearchForm;

import org.springframework.web.servlet.ModelAndView;

/**
 * �����{�����.
 * <p>
 * ���N�G�X�g�p�����[�^�isysBuildingCd�j�œn���ꂽ�l�ɊY�����錚������
 * �擾����ʕ\������B<br/>
 * <br/>
 * �y���A���� View ���z<br/>
 * <ul>
 * <li>success<li>:������������I��
 * <li>notFound<li>:�Y���f�[�^�����݂��Ȃ��ꍇ
 * </ul>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.03.06	�ɐV�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class BuildingDetailCommand implements Command {
	/** ������񃁃��e�i���X���s�� Model �I�u�W�F�N�g */
	protected BuildingManage buildingManager;

	/**
	 * ������񃁃��e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param userManager ���������e�i���X�� model �I�u�W�F�N�g
	 */
	public void setBuildingManager(BuildingManage buildingManager) {
		this.buildingManager = buildingManager;
	}
	
	/**
	 * �����{���\������<br>
	 * <br>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	 * @return ModelAndView�@�̃C���X�^���X
	*/
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
        Map<String, Object> model = createModel(request);
        BuildingSearchForm searchForm = (BuildingSearchForm) model.get("searchForm"); 
        
        // �V�X�e�������ԍ�
        String sysBuildingCd = searchForm.getSysBuildingCd();
        
        // �w�肳�ꂽsysBuildingCd�ɊY�����錚���̏����擾����B
        // ���������擾
        Building building = this.buildingManager.searchBuildingPk(sysBuildingCd);
        // ������{���̐ݒ�
        model.put("building", building.getBuildingInfo());
        // �Ŋ��w���̐ݒ�
        model.put("buildingStationList", building.getBuildingStationInfoList());
        // ���������h�}�[�N���̐ݒ�
        model.put("buildingLandmarkList", building.getBuildingLandmarkList());
        
		return new ModelAndView("success", model);
	}

	/**
	 * model �I�u�W�F�N�g���쐬���A���N�G�X�g�p�����[�^���i�[���� form �I�u�W�F�N�g���i�[����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^��ݒ肵�� Form ���i�[���� model �I�u�W�F�N�g�𕜋A����B
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		BuildingFormFactory factory = BuildingFormFactory.getInstance(request);
		model.put("searchForm", factory.createBuildingSearchForm(request));

		return model;
	}
}
