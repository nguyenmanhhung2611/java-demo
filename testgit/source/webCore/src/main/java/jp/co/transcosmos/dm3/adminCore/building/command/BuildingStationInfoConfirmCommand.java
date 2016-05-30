package jp.co.transcosmos.dm3.adminCore.building.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.building.dao.RouteMstListDAO;
import jp.co.transcosmos.dm3.core.model.building.dao.StationMstListDAO;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingFormFactory;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingStationInfoForm;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * �Ŋ��w�����͊m�F���
 * ���N�G�X�g�p�����[�^�œn���ꂽ�Ǘ����[�U�[���̃o���f�[�V�������s���A�m�F��ʂ�\������B
 * �����o���f�[�V�����G���[�����������ꍇ�͓��͉�ʂƂ��ĕ\������B
 * 
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *    �E"input" : �o���f�[�V�����G���[�ɂ��ē���
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.03.10	�V�K�쐬
 *
 * ���ӎ���
 * 
 * </pre>
 */
public class BuildingStationInfoConfirmCommand implements Command {

	/** �������[�h (insert = �V�K�o�^�����A update=�X�V����)*/
	protected String mode;
	
	/** �H���}�X�^�擾�p DAO */
	protected RouteMstListDAO routeMstListDAO;
	
	/** �w���}�X�^�擾�p DAO */
	protected StationMstListDAO stationMstListDAO;
	
	/**
	 * �������[�h��ݒ肷��<br/>
	 * <br/>
	 * @param mode "insert" = �V�K�o�^�����A"update" = �X�V����
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

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
	 * ���������͊m�F��ʕ\������<br>
	 * <br>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	*/
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

 		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
 		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
         Map<String, Object> model = createModel(request);
         BuildingStationInfoForm inputForm = (BuildingStationInfoForm) model.get("inputForm"); 
         
         
        // �X�V���[�h�̏ꍇ�A�X�V�Ώۂ̎�L�[�l���p�����[�^�œn����Ă��Ȃ��ꍇ�A��O���X���[����B
        if ("update".equals(this.mode)){
        	if (StringValidateUtil.isEmpty(inputForm.getSysBuildingCd())) throw new RuntimeException ("pk value is null.");
        }

        // view ���̏����l��ݒ�
        String viewName = "success"; 
        
		// �o���f�[�V���������s
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
        	viewName = "input";
        }

        return new ModelAndView(viewName, model);
	}

	/**
	 * ���N�G�X�g�p�����[�^���� Form �I�u�W�F�N�g���쐬����B<br/>
	 * �������� Form �I�u�W�F�N�g�� Map �Ɋi�[���ĕ��A����B<br/>
	 * key = �t�H�[���N���X���i�p�b�P�[�W�Ȃ��j�AValue = �t�H�[���I�u�W�F�N�g
	 * <br/>
	 * @param request HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^���ݒ肳�ꂽ�t�H�[���I�u�W�F�N�g���i�[���� Map �I�u�W�F�N�g
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
