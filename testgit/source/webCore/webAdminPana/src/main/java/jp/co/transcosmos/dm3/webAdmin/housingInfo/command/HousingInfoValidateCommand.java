package jp.co.transcosmos.dm3.webAdmin.housingInfo.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.vo.AddressMst;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingInfoForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;
/**
 * <pre>
 * ������{�����͊m�F���
 * ���N�G�X�g�p�����[�^�œn���ꂽ���t�H�[�����̃o���f�[�V�������s���A�m�F��ʂ�\������B
 * �����o���f�[�V�����G���[�����������ꍇ�͓��͉�ʂƂ��ĕ\������B
 *
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
public class HousingInfoValidateCommand implements Command {
	/** Housing�����e�i���X���s�� Model �I�u�W�F�N�g */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/**
	 * Housing�����e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param panaHousingManager
	 *            Housing�����e�i���X�� model �I�u�W�F�N�g
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

		// ������{���e�[�u���̓Ǎ�
		Housing housingresults = this.panaHousingManager.searchHousingPk(form
				.getSysHousingCd(),true);

		JoinResult buildingInfo = housingresults.getBuilding().getBuildingInfo();
		String housingKindCd = "";
		if(buildingInfo !=null){
			//�������
			housingKindCd = ((BuildingInfo)buildingInfo.getItems().get("buildingInfo")).getHousingKindCd();
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

		// �Z��
		String address = "";
		if(!StringValidateUtil.isEmpty(form.getZip())){
			address = address+"��"+form.getZip()+" ";
		}
		address = address+form.getPrefName()+form.getAddressName()+form.getAddressOther1()+form.getAddressOther2();
		model.put("address", address);

		// �z�N
		String compDate = form.getCompDate();
		String year ="";
		String month ="";
		if(!StringValidateUtil.isEmpty(compDate)){
			year = compDate.substring(0, 4);
			month = compDate.substring(4, 6);
		}
		model.put("year", year);
		model.put("month", month);

		if(form.getStationName() != null){
			String[] stationName = new String[form.getStationName().length];
			for(int i=0;i<form.getStationName().length;i++){
				if(!StringValidateUtil.isEmpty(form.getStationName()[i])){
					stationName[i]=form.getStationName()[i]+"�w";
				}
			}
			model.put("stationName", stationName);
		}

		// �X�֔ԍ�
		String zip = form.getZip();
		String zipBef ="";
		String zipAft ="";
		if(!StringValidateUtil.isEmpty(zip)){
			zipBef = zip.substring(0, 3);
			zipAft = zip.substring(3, 7);
		}
		model.put("zipBef", zipBef);
		model.put("zipAft", zipAft);

		if(form.getDefaultRouteCd() !=null){
			String[] routeName = new String[form.getDefaultRouteCd().length];
			for(int i=0;i<form.getDefaultRouteCd().length;i++){
				String wkRouteName = panamCommonManager.getRouteNameRr(form.getDefaultRouteCd()[i]);
				if(!StringValidateUtil.isEmpty(wkRouteName)){
					routeName[i]=wkRouteName;
				}
				else{
					routeName[i]=form.getRouteName()[i];
				}
			}
			form.setRouteNameRr(routeName);
		}

		model.put("housingInfoForm", form);
		return new ModelAndView("success", model);
	}
	/**
	 * ���X�g���擾����B<br/>
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
