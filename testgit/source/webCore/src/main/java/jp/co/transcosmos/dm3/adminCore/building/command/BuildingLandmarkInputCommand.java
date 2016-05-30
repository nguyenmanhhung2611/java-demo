package jp.co.transcosmos.dm3.adminCore.building.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.BuildingManage;
import jp.co.transcosmos.dm3.core.model.building.Building;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingFormFactory;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingLandmarkForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingSearchForm;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * �����n������͉��
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
 *    �E"notFound" : �Y���f�[�^�����݂��Ȃ��ꍇ
 *     
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.03.17	�V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class BuildingLandmarkInputCommand implements Command {
	
	/** �������[�h (insert = �V�K�o�^�����A update=�X�V����)*/
	protected String mode;
	
	/** ������񃁃��e�i���X���s�� Model �I�u�W�F�N�g */
	protected BuildingManage buildingManager;

	/**
	 * �������[�h��ݒ肷��<br/>
	 * <br/>
	 * @param mode "insert" = �V�K�o�^�����A"update" = �X�V����
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	/**
	 * ������񃁃��e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param userManager ���������e�i���X�� model �I�u�W�F�N�g
	 */
	public void setBuildingManager(BuildingManage buildingManager) {
		this.buildingManager = buildingManager;
	}
	
	/**
	 * �n������͉�ʕ\������<br>
	 * <br>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	*/
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
        Map<String, Object> model = createModel(request);
        BuildingLandmarkForm inputForm = (BuildingLandmarkForm) model.get("inputForm"); 
        BuildingSearchForm searchForm = (BuildingSearchForm) model.get("searchForm"); 

        // �V�X�e�������ԍ�
        String sysBuildingCd = searchForm.getSysBuildingCd();
        
		// �X�V�����̏ꍇ�A�����ΏۂƂȂ��L�[�l���p�����[�^�œn����Ă��Ȃ��ꍇ�A��O���X���[����B
		if ("update".equals(this.mode) && StringValidateUtil.isEmpty(sysBuildingCd)){
			throw new RuntimeException ("pk value is null.");
		}
		
		// command �p�����[�^�� "back" �̏ꍇ�A���͊m�F��ʂ���̕��A�Ȃ̂ŁA�c�a���珉���l���擾���Ȃ��B
		// �i���N�G�X�g�p�����[�^����擾�����l���g�p����B�j
		String command = inputForm.getCommand();
		
		if (command != null && "back".equals(command)) {
			return new ModelAndView("success", model);
		}

        // �w�肳�ꂽsysBuildingCd�ɊY�����錚���̏����擾����B
        // ���������擾
        Building building = this.buildingManager.searchBuildingPk(sysBuildingCd);
        // �n����̐ݒ�
        inputForm.setDefaultData(building);

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

		// ���������A����сA��ʃR���g���[���p�����[�^���擾����B
		BuildingSearchForm searchForm = factory.createBuildingSearchForm(request);
		model.put("searchForm", searchForm);	

		
		// ���̓t�H�[���̎󂯎��́Acommand �p�����[�^�̒l�ɂ���ĈقȂ�B
		// command �p�����[�^���n����Ȃ��ꍇ�A���͉�ʂ̏����\���Ƃ݂Ȃ��A��̃t�H�[���𕜋A����B
		// command �p�����[�^�� "back" ���n���ꂽ�ꍇ�A���͊m�F��ʂ���̕��A���Ӗ�����B
		// ���̏ꍇ�̓��N�G�X�g�p�����[�^���󂯎��A���͉�ʂ֏����\������B
		// �����A"back" �ȊO�̒l���n���ꂽ�ꍇ�Acommand �p�����[�^���n����Ȃ��ꍇ�Ɠ����Ɉ����B

		BuildingLandmarkForm inputForm = factory.createBuildingLandmarkForm(request);
		String command = inputForm.getCommand();
		
		if (command != null && ("back".equals(command) || "update".equals(command))){
			model.put("inputForm", inputForm);
		} else {
			model.put("inputForm", factory.createBuildingStationInfoForm());
		}

		return model;

	}
}
