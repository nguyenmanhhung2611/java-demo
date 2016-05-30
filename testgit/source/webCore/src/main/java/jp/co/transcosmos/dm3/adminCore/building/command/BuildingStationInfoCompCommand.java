package jp.co.transcosmos.dm3.adminCore.building.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.BuildingManage;
import jp.co.transcosmos.dm3.core.model.building.dao.RouteMstListDAO;
import jp.co.transcosmos.dm3.core.model.building.dao.StationMstListDAO;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingFormFactory;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingStationInfoForm;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;


/**
 * �Ŋ��w���̕ύX����.
 * <p>
 * �y�X�V�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B</li>
 * <li>�Ŋ��w���͈�x�폜���A�ύX��̍Ŋ��w����V�K�o�^����B</li>
 * <li>�����A�X�V�Ώۃf�[�^�����݂��Ȃ��ꍇ�A�X�V�������p���ł��Ȃ��̂ŊY��������ʂ�\������B</li>
 * </ul>
 * <br/>
 * �y���A���� View ���z<br/>
 * <ul>
 * <li>success</li>:����I���i���_�C���N�g�y�[�W�j
 * <li>input</li>:�o���f�[�V�����G���[�ɂ��ē���
 * <li>notFound</li>:�Y���f�[�^�����݂��Ȃ��ꍇ�i�X�V�����̏ꍇ�j
 * <li>comp</li>:������ʕ\��
 * </ul>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.03.10	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class BuildingStationInfoCompCommand implements Command  {

	/** ���������e�i���X���s�� Model �I�u�W�F�N�g */
	protected BuildingManage buildingManager;

	/** �������[�h (insert = �V�K�o�^�����A update=�X�V�����Adelete=�폜����)*/
	protected String mode;

	/** Form �̃o���f�[�V���������s����ꍇ�Atrue ��ݒ肷��B�@�i�f�t�H���g true�j */
	protected boolean useValidation = true;

	/** �H���}�X�^�擾�p DAO */
	protected RouteMstListDAO routeMstListDAO;
	
	/** �w���}�X�^�擾�p DAO */
	protected StationMstListDAO stationMstListDAO;
	
	/**
	 * �H���}�X�^�pDAO��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @return �H���}�X�^�p DAO
	 */
	public void setRouteMstListDAO(RouteMstListDAO routeMstListDAO) {
		this.routeMstListDAO = routeMstListDAO;
	}

	/**
	 * �w���}�X�^�pDAO��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @return �w���}�X�^�p DAO
	 */
	public void setStationMstListDAO(StationMstListDAO stationMstListDAO) {
		this.stationMstListDAO = stationMstListDAO;
	}
	
	
	/**
	 * ���������e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param buildingManager ���������e�i���X�� model �I�u�W�F�N�g
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
	 * Form �̃o���f�[�V���������s����ꍇ�Atrue ��ݒ肷��B�@�i�f�t�H���g true�j<br/>
	 * <br/>
	 * @param useValidation true �̏ꍇ�AForm �̃o���f�[�V���������s
	 */
	public void setUseValidation(boolean useValidation) {
		this.useValidation = useValidation;
	}

	/**
	 * �Ŋ��w���̕ύX����<br>
	 * <br>
	 * @param request �N���C�A���g�����Http���N�G�X�g�B
	 * @param response �N���C�A���g�ɕԂ�Http���X�|���X�B
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
        Map<String, Object> model = createModel(request);
        BuildingStationInfoForm inputForm = (BuildingStationInfoForm) model.get("inputForm");
        
        // ������ʂŃ����[�h�����ꍇ�A�X�V�������Ӑ}�������s������肪��������B
        // ���̖�����������ׁAview ���� "success"�@���w�肷��Ǝ������_�C���N�g��ʂ��\�������B
        // ���̃��_�C���N�g��ʂ́Acommand �p�����[�^�� "redirect"�@�ɐݒ肵�Ċ�����ʂփ��N�G�X�g��
        // ���M����B
        // ����āAcommand = "redirect" �̏ꍇ�́A�c�a�X�V�͍s�킸�A������ʂ�\������B
        String command = inputForm.getCommand();
        if (command != null && "redirect".equals(command)){
        	return new ModelAndView("comp" , model);
        }

		// �o���f�[�V�����̎��s���[�h���L���̏ꍇ�A�o���f�[�V���������s����B
        // �폜�����A���b�N���������̏ꍇ�̓��O�C��ID �ȊO�̃p�����[�^�͕s�v�Ȃ̂ŁAspring �����疳�������Ă���B
        if (this.useValidation){
        	List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        	if (!inputForm.validate(errors)){
        		// �H���}�X�^���擾����
        		Object[] params = new Object[] {inputForm.getPrefCd()};
        		List<RouteMst> routeMstList = this.routeMstListDAO.listRouteMst(params);
        		model.put("routeMstList", routeMstList);
    			// �w�}�X�^���擾����
    			String[] routeCd = inputForm.getDefaultRouteCd();
    			if (routeCd != null) {
    				List<List<StationMst>> stationMstList = new ArrayList<List<StationMst>>();
    				for (int i = 0; i < routeCd.length; i++) {
    					if (StringValidateUtil.isEmpty(routeCd[i])) {
    						stationMstList.add(null);
    					} else {
    						Object[] paramsStation = new Object[] {inputForm.getPrefCd(), routeCd[i]};
    						stationMstList.add(getStationMstList(paramsStation));
    					}
    				}
    				model.put("stationMstList", stationMstList);
    			}
        		// �o���f�[�V�����G���[���́A�G���[���� model �ɐݒ肵�A���͉�ʂ�\������B
        		model.put("errors", errors);
        		return new ModelAndView("input" , model);
        	}
        }
        
        // �e�폈�������s
        try {
        	execute(inputForm);

        } catch (NotFoundException e) {
            // �X�V�Ώۂ����݂��Ȃ��ꍇ�́A�Y���Ȃ���ʂ�
        	return new ModelAndView("notFound", model);

        }

		return new ModelAndView("success" , model);
        
	}

	/**
	 * model �I�u�W�F�N�g���쐬���A���N�G�X�g�p�����[�^���i�[���� form �I�u�W�F�N�g���i�[����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^��ݒ肵�� Form ���i�[���� model �I�u�W�F�N�g�𕜋A����B
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<>();

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		BuildingFormFactory factory = BuildingFormFactory.getInstance(request);

		model.put("searchForm", factory.createBuildingSearchForm(request));
		model.put("inputForm", factory.createBuildingStationInfoForm(request));

		return model;

	}
	
	/**
	 * �����̐U�蕪���Ǝ��s���s���B<br/>
	 * <br/>
 	 * @param model View �w�ֈ����n�� model �I�u�W�F�N�g
	 * @param inputForm ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException �X�V�Ώۂ����݂��Ȃ��ꍇ
	 */
	protected void execute(BuildingStationInfoForm inputForm)
			throws Exception, NotFoundException {

		if (this.mode.equals("update")){
			// �X�V
	    	this.buildingManager.updateBuildingStationInfo(inputForm);
        }  else {
        	// �z�肵�Ă��Ȃ��������[�h�̏ꍇ�A��O���X���[����B
        	throw new RuntimeException ("execute mode bad setting.");
        }

	}
	
	/**
	 * ���HCD�ŉw���X�g���擾����B<br/>
	 * <br/>
	 * @param routeCd ���HCD
	 * @return �w���X�g�𕜋A����B
	 */
	protected List<StationMst> getStationMstList(Object[] params) {
		List<StationMst> stationMstList = this.stationMstListDAO.listStationMst(params);
		return stationMstList;
	}
	
}
