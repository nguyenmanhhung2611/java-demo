package jp.co.transcosmos.dm3.adminCore.building.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.BuildingManage;
import jp.co.transcosmos.dm3.core.model.building.Building;
import jp.co.transcosmos.dm3.core.model.building.BuildingManageImpl;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingFormFactory;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingSearchForm;
import jp.co.transcosmos.dm3.core.vo.AddressMst;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * ���������͉��
 * 
 * �y�V�K�o�^�̏ꍇ�z
 *     �E���N�G�X�g�p�����[�^�i������ʂœ��͂������������A�\���y�[�W�ʒu���j�̎���݂̂��s���B
 *     
 * �y�X�V�o�^�̏ꍇ�z
 *     �E���N�G�X�g�p�����[�^�i������ʂœ��͂������������A�\���y�[�W�ʒu���j�̎����s���B
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
 * I.Shu		2015.03.02	�V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class BuildingInputCommand implements Command {

	/** ���������e�i���X���s�� Model �I�u�W�F�N�g */
	protected BuildingManage buildingManager;
	
	/** �������[�h (insert = �V�K�o�^�����A update=�X�V����)*/
	protected String mode;
	
	/** �s���{���}�X�^�擾�p DAO */
	protected DAO<PrefMst> prefMstDAO;
	
	/** �s�撬���}�X�^�擾�p DAO */
	protected DAO<AddressMst> addressMstDAO;
	
	/**
	 * ������񃁃��e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>Building
	 * @param buildingManager ������񃁃��e�i���X�� model �I�u�W�F�N�g
	 */
	public void setBuildingManager(BuildingManage buildingManager) {
		this.buildingManager = buildingManager;
	}

	/**
	 * �������[�h��ݒ肷��<br/>
	 * <br/>
	 * @param mode "insert" = �V�K�o�^�����A"update" = �X�V����
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	/**
	 * �s���{���}�X�^�pDAO��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @return �s�撬���}�X�^�p DAO
	 */
	public void setPrefMstDAO(DAO<PrefMst> prefMstDAO) {
		this.prefMstDAO = prefMstDAO;
	}

	/**
	 * �s�撬���}�X�^�pDAO��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @return �s�撬���}�X�^�p DAO
	 */
	public void setAddressMstDAO(DAO<AddressMst> addressMstDAO) {
		this.addressMstDAO = addressMstDAO;
	}

	
	/**
	 * ���������͉�ʕ\������<br>
	 * <br>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	*/
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
        Map<String, Object> model = createModel(request);
        BuildingForm inputForm = (BuildingForm) model.get("inputForm"); 
        BuildingSearchForm searchForm = (BuildingSearchForm) model.get("searchForm"); 

		// �s���{���}�X�^���擾����
		List<PrefMst> prefMstList = this.prefMstDAO.selectByFilter(null);
		model.put("prefMstList", prefMstList);

		
		// �X�V�����̏ꍇ�A�����ΏۂƂȂ��L�[�l���p�����[�^�œn����Ă��Ȃ��ꍇ�A��O���X���[����B
		if ("update".equals(this.mode) && StringValidateUtil.isEmpty(searchForm.getSysBuildingCd())){
			throw new RuntimeException ("pk value is null.");
		}

		// command �p�����[�^�� "back" �̏ꍇ�A���͊m�F��ʂ���̕��A�Ȃ̂ŁA�c�a���珉���l���擾���Ȃ��B
		// �i���N�G�X�g�p�����[�^����擾�����l���g�p����B�j
		String command = inputForm.getCommand();
		
		if (command != null && "back".equals(command)) {
			// �s�撬���}�X�^���擾����
			model.put("addressMstList", getAddressMstList(inputForm.getPrefCd()));
			return new ModelAndView("success", model);
		}

        // �V�K�o�^���[�h�ŏ�����ʕ\���̏ꍇ�A�f�[�^�̎擾�͍s��Ȃ��B
        if ("insert".equals(this.mode)) {
        	return new ModelAndView("success", model);
        }
        
        // �V�X�e�������ԍ�
        String sysBuildingCd = searchForm.getSysBuildingCd();
        
        // �w�肳�ꂽsysBuildingCd�ɊY�����錚���̏����擾����B
        // ���������擾
        Building building = this.buildingManager.searchBuildingPk(sysBuildingCd);
		// ������{���̐ݒ�
		inputForm.setDefaultData(
				(BuildingInfo) building.getBuildingInfo().getItems()
						.get(BuildingManageImpl.BUILDING_INFO_ALIA),
				(PrefMst) building.getBuildingInfo().getItems()
						.get(BuildingManageImpl.PREF_MST_ALIA));
		// �s�撬���}�X�^���擾����
		model.put("addressMstList", getAddressMstList(inputForm.getPrefCd()));
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

		BuildingForm inputForm = factory.createBuildingForm(request);
		String command = inputForm.getCommand();
		
		if (command != null && "back".equals(command)){
			model.put("inputForm", inputForm);
		} else {
			model.put("inputForm", factory.createBuildingForm());
		}

		return model;

	}
	
	/**
	 * �s���{��CD�Ŏs�撬�����X�g���擾����B<br/>
	 * <br/>
	 * @param prefCd �s���{��CD
	 * @return �s�撬�����X�g�𕜋A����B
	 */
	protected List<AddressMst> getAddressMstList(String prefCd) {
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("prefCd", prefCd);
		List<AddressMst> addressMstList = this.addressMstDAO.selectByFilter(criteria);
		return addressMstList;
		
	}

}
