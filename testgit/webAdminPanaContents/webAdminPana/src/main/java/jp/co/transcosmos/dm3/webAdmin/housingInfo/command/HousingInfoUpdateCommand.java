package jp.co.transcosmos.dm3.webAdmin.housingInfo.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.building.BuildingPartThumbnailProxy;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.AddressMst;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.building.form.PanaBuildingForm;
import jp.co.transcosmos.dm3.corePana.model.building.form.PanaBuildingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.building.form.PanaBuildingStationInfoForm;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingInfoForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.util.PanaCommonUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;
/**
 * ������{���ύX�������
 * <p>
 * �y�V�K�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B</li>
 * <li>�o���f�[�V����������I�������ꍇ�A�X�e�[�^�X��V�K�o�^����B</li>
 * </ul>
 * <br/>
 * �y�X�V�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B</li>
 * <li>�o���f�[�V����������I�������ꍇ�A�X�e�[�^�X�����X�V����B</li>
 * <li>�����A�X�V�Ώۃf�[�^�����݂��Ȃ��ꍇ�A�X�V�������p���ł��Ȃ��̂ŊY��������ʂ�\������B</li>
 * </ul>
 * <br/>
 * �y���A���� View ���z<br/>
 * <ul>
 * <li>success</li>:����I���i���_�C���N�g�y�[�W�j
 * <li>notFound</li>:�Y���f�[�^�����݂��Ȃ��ꍇ�i�X�V�����̏ꍇ�j
 * <li>comp</li>:������ʕ\��
 * </ul>
 * <p>
 *
 * <pre>
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * fan			2015.04.06	�V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class HousingInfoUpdateCommand implements Command {

	/** �Z��f�f��񃁃��e�i���X���s�� Model �I�u�W�F�N�g */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/**
	 * �Z��f�f��񃁃��e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param housingManager
	 *            �Z��f�f��񃁃��e�i���X�� model �I�u�W�F�N�g
	 */
	public void setPanaHousingManager(PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
	}

	/** �}�X�^��񃁃��e�i���X���s�� Model �I�u�W�F�N�g */
	private PanaCommonManage panamCommonManager;

	/**
	 * �}�X�^��񃁃��e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param panaCommonManager
	 *            �}�X�^��񃁃��e�i���X�� model �I�u�W�F�N�g
	 */
	public void setPanamCommonManager(PanaCommonManage panamCommonManager) {
		this.panamCommonManager = panamCommonManager;
	}

	private BuildingPartThumbnailProxy buildingManager;


	public void setBuildingManager(BuildingPartThumbnailProxy buildingManager) {
		this.buildingManager = buildingManager;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);

		// ���N�G�X�g�p�����[�^���i�[���� form �I�u�W�F�N�g�𐶐�����B
		PanaHousingInfoForm form = factory.createPanaHousingInfoForm(request);
		PanaHousingSearchForm searchForm = factory.createPanaHousingSearchForm(request);
		model.put("searchForm", searchForm);

		// ���O�C�����[�U�[�̏����擾����B�@�i�^�C���X�^���v�̍X�V�p�j
		AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request)
				.getLoginUserInfo(request, response);

		// ���[�UID���擾
		String userId = String.valueOf(loginUser.getUserId().toString());

		String command = form.getCommand();
		String sysHousingCd = form.getSysHousingCd();
		if (command != null && command.equals("redirect")) {
			model.put("sysHousingCd", sysHousingCd);
			return new ModelAndView("comp", model);
		}

		// ������{���e�[�u���̓Ǎ�
		Housing housingresults = this.panaHousingManager.searchHousingPk(form
				.getSysHousingCd(),true);

		JoinResult buildingInfoResults = housingresults.getBuilding().getBuildingInfo();
		String housingKindCd = "";
		if(buildingInfoResults !=null){
			//�������
			housingKindCd = ((BuildingInfo)buildingInfoResults.getItems().get("buildingInfo")).getHousingKindCd();
		}

		// �o���f�[�V��������
		Validateable validateableForm = (Validateable) form;
		// �G���[���b�Z�[�W�p�̃��X�g���쐬
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();

		if(!housingKindCd.equals(form.getHousingKindCd())){
            ValidationFailure vf = new ValidationFailure(
                    "housingKindCdError", "housingInfo.input.housingKindCd", "", null);
            errors.add(vf);
	        // �G���[����
			// �G���[�I�u�W�F�N�g�ƁA�t�H�[���I�u�W�F�N�g��ModelAndView �ɓn���Ă���
			model.put("errors", errors);
			// ���X�g���擾����
	         getMstList(model ,  form);
			model.put("housingInfoForm", form);
			return new ModelAndView("validationError", model);
		}

		// �o���f�[�V���������s
		if (!validateableForm.validate(errors)) {
			// �G���[����
			// �G���[�I�u�W�F�N�g�ƁA�t�H�[���I�u�W�F�N�g��ModelAndView �ɓn���Ă���
			model.put("errors", errors);
			// ���X�g���擾����
            getMstList(model ,  form);
			model.put("housingInfoForm", form);
			return new ModelAndView("validationError", model);
		}
		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		PanaHousingFormFactory panaHousingFormFactory = new PanaHousingFormFactory();

		 // ������{�����擾����B
        HousingInfo housingInfo = ((HousingInfo) housingresults.getHousingInfo().getItems().get("housingInfo"));
		// PanaHousingForm��ݒ肷��
		PanaHousingForm panaHousingForm = panaHousingFormFactory.createPanaHousingForm();
		PanaCommonUtil.copyProperties(panaHousingForm, housingInfo);
		panaHousingForm.setSysHousingCd(form.getSysHousingCd());
		panaHousingForm.setSysBuildingCd(form.getSysBuildingCd());
		panaHousingForm.setHousingCd(form.getHousingCd());
		panaHousingForm.setDisplayHousingName(form.getDisplayHousingName());
		panaHousingForm.setPrice(form.getPrice());
		panaHousingForm.setLayoutCd(form.getLayoutCd());
		panaHousingForm.setLandArea(form.getLandArea());
		panaHousingForm.setLandAreaMemo(form.getLandAreaMemo());
		panaHousingForm.setPersonalArea(form.getPersonalArea());
		panaHousingForm.setPersonalAreaMemo(form.getPersonalAreaMemo());
		if(form.getTimeFromBusStop() !=null && form.getTimeFromBusStop().length>0){
			String wkminWalkingTime = form.getTimeFromBusStop()[0];

			for(int i=0;i<form.getTimeFromBusStop().length;i++){
				if(form.getTimeFromBusStop()[i] != ""){
					if(Integer.valueOf(wkminWalkingTime==""?"0":wkminWalkingTime) > Integer.valueOf(form.getTimeFromBusStop()[i])){
						wkminWalkingTime = form.getTimeFromBusStop()[i];
					}
				}
			}
			panaHousingForm.setMinWalkingTime(wkminWalkingTime);
		}

		//�u������{���v�e�[�u���X�V���A�u���t�H�[�������i�i�ŏ��j�v�A�u���t�H�[�������i�i�ő�j�v���X�V����B���t�H�[���v���������݂��Ȃ��ꍇ�A��ʍ��ڢ���i��̒l��ݒ肷��
		//�@�@���t�H�[�������i�i�ŏ��j����ʍ��ځu���i�v�{�u���t�H�[���v�����v�e�[�u���̍ŏ��́u���t�H�[�����i�v
		//�@�@���t�H�[�������i�i�ő�j����ʍ��ځu���i�v�{�u���t�H�[���v�����v�e�[�u���̍ő�́u���t�H�[�����i�v
//		List<ReformPlan> reformPlanList = this.reformManager.searchReformPlan(form.getSysHousingCd());
//		if(reformPlanList!=null){
//			Long wkPriceFullMin = reformPlanList.get(0).getPlanPrice();
//			Long wkPriceFullMax = reformPlanList.get(0).getPlanPrice();
//			for(int i=0;i<reformPlanList.size();i++){
//				if(wkPriceFullMin > reformPlanList.get(i).getPlanPrice()){
//					wkPriceFullMin = reformPlanList.get(i).getPlanPrice();
//				}
//				if(wkPriceFullMax < reformPlanList.get(i).getPlanPrice()){
//					wkPriceFullMax = reformPlanList.get(i).getPlanPrice();
//				}
//			}
//			panaHousingForm.setPriceFullMin(String.valueOf(wkPriceFullMin));
//			panaHousingForm.setPriceFullMax(String.valueOf(wkPriceFullMax));
//		}
//		else{
//			panaHousingForm.setPriceFullMin(form.getPrice());
//			panaHousingForm.setPriceFullMax(form.getPrice());
//		}

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		PanaBuildingFormFactory panaBuildingFormFactory = new PanaBuildingFormFactory();

		// ������{�����擾����B
		BuildingInfo buildingInfo = (BuildingInfo)housingresults.getBuilding().getBuildingInfo().getItems().get("buildingInfo");
		// PanaBuildingForm��ݒ肷��
		PanaBuildingForm panaBuildingForm = panaBuildingFormFactory.createPanaBuildingForm();
		PanaCommonUtil.copyProperties(panaBuildingForm, buildingInfo);
		panaBuildingForm.setSysBuildingCd(form.getSysBuildingCd());
		panaBuildingForm.setBuildingCd(form.getSysBuildingCd());
		panaBuildingForm.setHousingKindCd(form.getHousingKindCd());
		panaBuildingForm.setCompDate(form.getCompDate());
		panaBuildingForm.setZip(form.getZip());
		panaBuildingForm.setPrefCd(form.getPrefCd());
		panaBuildingForm.setPrefName(form.getPrefName());
		panaBuildingForm.setAddressCd(form.getAddressCd());
		panaBuildingForm.setAddressName(form.getAddressName());
		panaBuildingForm.setAddressOther1(form.getAddressOther1());
		panaBuildingForm.setAddressOther2(form.getAddressOther2());

		// PanaBuildingStationInfoForm��ݒ肷��
		PanaBuildingStationInfoForm panaBuildingStationInfoForm = panaBuildingFormFactory.createPanaBuildingStationInfoForm();
		panaBuildingStationInfoForm.setSysBuildingCd(form.getSysBuildingCd());
		panaBuildingStationInfoForm.setDefaultRouteCd(form.getDefaultRouteCd()==null?new String[1]:form.getDefaultRouteCd());

		String[] routeName = form.getRouteName();
		if(form.getRouteName() != null){
			for(int i=0;i<form.getRouteName().length;i++){
				String wkRouteName = panamCommonManager.getRouteName(form.getDefaultRouteCd()[i]);
				if(!StringValidateUtil.isEmpty(wkRouteName)){
					routeName[i]=wkRouteName;
				}
			}
		}

		panaBuildingStationInfoForm.setRouteName(routeName);

//		panaBuildingStationInfoForm.setRouteName(form.getRouteName());
		panaBuildingStationInfoForm.setStationCd(form.getStationCd());
		panaBuildingStationInfoForm.setStationName(form.getStationName());
		panaBuildingStationInfoForm.setBusCompany(form.getBusCompany());
		panaBuildingStationInfoForm.setTimeFromBusStop(form.getTimeFromBusStop());
		// ������{���e�[�u����update����
		this.panaHousingManager.updateHousing(panaHousingForm , userId);
		// ������{���e�[�u����update����
		this.buildingManager.updateBuildingInfo(panaBuildingForm, userId);
		// �����Ŋ��w���e�[�u����update����
		this.buildingManager.updateBuildingStationInfo(panaBuildingStationInfoForm);


		// �������[�h�yhidden�z = insert�̏ꍇ
		if("insert".equals(command)){
			// �t�H�[���̓��͒l���o���[�I�u�W�F�N�g�ɐݒ肷��B
            this.panaHousingManager.updateBuildingDtlInfo(form,userId);
		}
		// �������[�h�yhidden�z = update�̏ꍇ�A
		if("update".equals(command)){
			// �t�H�[���̓��͒l���o���[�I�u�W�F�N�g�ɐݒ肷��B
			this.panaHousingManager.updateBuildingDtlInfo(form,userId);
		}

		model.put("housingInfoForm", form);
		return new ModelAndView("success", model);

	}
	/**
	 * �s���{���A�s�撬���A�H���A�w���X�g���擾����B<br/>
	 * <br/>
	 * @param model modelD
	 * @param form form
	 */
	protected void getMstList(Map<String, Object> model , PanaHousingInfoForm form)  throws Exception{

		//����CD
		String[] defaultRouteCd = new String[3];
		//������
		String[] routeName = new String[3];
		//�wCD
		String[] stationCd = new String[3];
		//�w��
		String[] stationName = new String[3];
		if(form.getDefaultRouteCd() != null){
			for(int i=0;i<form.getDefaultRouteCd().length;i++){
				defaultRouteCd[i]=form.getDefaultRouteCd()[i];
				routeName[i]=form.getRouteName()[i];
				stationCd[i]=form.getStationCd()[i];
				stationName[i]=form.getStationName()[i];
			}
		}

		form.setOldRouteName(routeName);
		form.setOldStationName(stationName);
		form.setOldDefaultRouteCd(defaultRouteCd);
		form.setOldStationCd(stationCd);

		List<PrefMst> prefMstList = panamCommonManager.getPrefMstList();
		if(prefMstList!=null&&prefMstList.size()>0){
			// �s���{���}�X�^���擾����
			model.put("prefMstList", prefMstList);
		}

		String[] oldRouteName = new String[3];
		String[] oldRouteCd = new String[3];
		for(int i=0;i<form.getOldDefaultRouteCd().length;i++){
			oldRouteCd[i]=form.getOldDefaultRouteCd()[i];
			oldRouteName[i]=form.getOldRouteName()[i];
		}

		if (form.getPrefCd() != null) {
			List<AddressMst> addressMstList= panamCommonManager.getPrefCdToAddressMstList(form.getPrefCd());
			List<RouteMst> routeMstList = panamCommonManager.getPrefCdToRouteMstList(form.getPrefCd());

            // �s�撬���}�X�^���擾����
    		model.put("addressMstList", addressMstList);
    		// �H���}�X�^���擾����
    		model.put("routeMstList", routeMstList);

    		// �H��CD �ɊY������f�[�^�������ꍇ�ɑ�\�H�������g�p
    		if(form.getOldDefaultRouteCd() != null){
    			for(int j=0;j<form.getOldDefaultRouteCd().length;j++){
        			for(int i=0;i<routeMstList.size();i++){
        				if(routeMstList.get(i).getRouteCd().equals(form.getOldDefaultRouteCd()[j]) ){
        					oldRouteName[j] = "";
        					oldRouteCd[j] = "";
            			}
        			}
        		}
    		}
		}

		String[] oldStationName = new String[form.getOldStationCd().length];
		String[] oldStationCd = new String[form.getOldStationCd().length];
		for(int i=0;i<form.getOldDefaultRouteCd().length;i++){
			oldStationName[i]=form.getOldStationName()[i];
			oldStationCd[i]=form.getOldStationCd()[i];
		}

		// �w�}�X�^���擾����
		String[] routeCd = form.getDefaultRouteCd();
		if (routeCd != null) {
			List<List<StationMst>> stationMstList = new ArrayList<List<StationMst>>();
			for (int i = 0; i < routeCd.length; i++) {
				if (StringValidateUtil.isEmpty(routeCd[i])) {
					stationMstList.add(null);
				} else {
					stationMstList.add(panamCommonManager.getRouteCdToStationMstList(routeCd[i]));
				}
			}
			model.put("stationMstList", stationMstList);

			// �wCD �ɊY������f�[�^�������ꍇ�ɉw�����g�p
    		for(int j=0;j<form.getOldStationCd().length;j++){
    			List<StationMst> stationMst = panamCommonManager.getRouteCdToStationMstList(form.getOldDefaultRouteCd()[j]);
    			for(int jj=0;jj<stationMst.size();jj++){
    				if(stationMst.get(jj).getStationCd().equals(form.getOldStationCd()[j]) ){
    					oldStationName[j] = "";
    					oldStationCd[j] = "";
        			}
    			}
    		}
		}
		form.setOldStationName(oldStationName);
		form.setOldStationCd(oldStationCd);
		form.setOldRouteName(oldRouteName);
		form.setOldDefaultRouteCd(oldRouteCd);
	}
}