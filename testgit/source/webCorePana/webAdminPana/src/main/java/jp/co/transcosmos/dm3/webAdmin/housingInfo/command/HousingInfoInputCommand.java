package jp.co.transcosmos.dm3.webAdmin.housingInfo.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.vo.AddressMst;
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
 * ������{���ҏW��ʃR�}���h�N���X
 * ������{���ҏW��ʂ�\������ۂɌĂяo�����R�}���h�B
 *
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * fan			2015.04.06	�V�K�쐬
 *
 * </pre>
 */
public class HousingInfoInputCommand implements Command {
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

		// ���O�`�F�b�N:������{���e�[�u���̓Ǎ�
		Housing housingresults = this.panaHousingManager.searchHousingPk(form
				.getSysHousingCd(),true);

		if (housingresults == null) {
			// �f�[�^�̑��݂��Ȃ��ꍇ,���b�Z�[�W�F"�o0�p������񂪑��݂��Ȃ�"�\��
			throw new NotFoundException();
		}

		JoinResult housingInfo = housingresults.getHousingInfo();
		JoinResult buildingInfo = housingresults.getBuilding().getBuildingInfo();
		List<JoinResult> buildingStationInfoList = housingresults.getBuilding().getBuildingStationInfoList();

		// ���͊m�F��ʂ́u�߂�v�{�^������Ԃ��ꍇ
		if ("back".equals(form.getCommand())) {
			// ��ʕ\���p�A�b�v���[�h�摜�ҏW���̍Đݒ�
			// ���X�g���擾����
			getMstList(model ,  form);
			model.put("housingInfoForm", form);
			form.setCommand(form.getComflg());

			// �擾�����f�[�^�������_�����O�w�֓n��
			return new ModelAndView("success", model);
		}

		// ���͊m�F��ʂ́u�Z�������v�{�^���̏ꍇ
		if("address".equals(form.getCommand())){

			// �o���f�[�V��������
			Validateable validateableForm = (Validateable) form;
			// �G���[���b�Z�[�W�p�̃��X�g���쐬
			List<ValidationFailure> errors = new ArrayList<ValidationFailure>();

			// �o���f�[�V���������s
			if (!validateableForm.validate(errors)) {
				// �G���[����
				// �G���[�I�u�W�F�N�g�ƁA�t�H�[���I�u�W�F�N�g��ModelAndView �ɓn���Ă���
				model.put("errors", errors);
				// ���X�g���擾����
	            getMstList(model ,  form);
				model.put("housingInfoForm", form);

				// �擾�����f�[�^�������_�����O�w�֓n��
				return new ModelAndView("validationError", model);
			}

			// �s�撬���}�X�^���擾����
			String zip = form.getZip();

			String[] zipMst = panamCommonManager.getZipToAddress(zip);
			// �X�֔ԍ��̑΂���Z�����Ȃ��ꍇ
			if(zipMst == null || zipMst[0] != "0"){

				ValidationFailure vf = new ValidationFailure(
	                    "housingInfoZipInput", "", "", null);
	            errors.add(vf);
	            model.put("errors", errors);
	            // ���X�g���擾����
	            getMstList(model ,  form);
				model.put("housingInfoForm", form);
				form.setCommand(form.getComflg());


				// �擾�����f�[�^�������_�����O�w�֓n��
				return new ModelAndView("validationError", model);
			}
			String prefCd = form.getPrefCd();

			form.setPrefCd(zipMst[1]);
			form.setAddressCd(zipMst[2]);

			//�s���{��CD
			if(!prefCd.equals(form.getPrefCd())){
				form.setDefaultRouteCd(null);
				form.setRouteName(null);
				form.setStationCd(null);
				form.setStationName(null);
			}
			// ���X�g���擾����
 			getMstList(model ,  form);
			model.put("housingInfoForm", form);
			form.setCommand(form.getComflg());

			// �擾�����f�[�^�������_�����O�w�֓n��
			return new ModelAndView("success", model);
		}

		form.setDefaultData(housingInfo, buildingInfo,buildingStationInfoList);

		// Model�ݒ�
		String wkModel;
		if (housingresults.getBuilding() == null) {
			wkModel = "insert";
		} else {
			wkModel = "update";
		}
		form.setCommand(wkModel);
		form.setComflg(wkModel);

		// ���X�g���擾����
		getMstList(model ,  form);
		model.put("housingInfoForm", form);
		// �擾�����f�[�^�������_�����O�w�֓n��
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
