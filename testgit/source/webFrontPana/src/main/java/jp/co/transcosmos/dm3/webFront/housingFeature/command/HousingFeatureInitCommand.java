package jp.co.transcosmos.dm3.webFront.housingFeature.command;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.FeatureManage;
import jp.co.transcosmos.dm3.core.model.building.Building;
import jp.co.transcosmos.dm3.core.model.building.BuildingPartThumbnailProxy;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.FeaturePageInfo;
import jp.co.transcosmos.dm3.core.vo.HousingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.RrMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.ReformManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaFeatureSearchForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.util.PanaCalcUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * ���W�e���v���[�g���
 *
 * <pre>
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 *   ��		  2015.04.10    �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class HousingFeatureInitCommand implements Command {

	/** ���W�y�[�W��� */
	private DAO<FeaturePageInfo> featurePageInfoDAO;

	/** �P�y�[�W�̕\������ */
	private int rowsPerPage = 2;

    /** ���ʃp�����[�^�I�u�W�F�N�g */
    private PanaCommonParameters commonParameters;

	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;

	/** ���ʏ��擾���� Model �I�u�W�F�N�g */
	private PanaCommonManage panaCommonManage;

	/** ���W�̏����Ǘ����� Model �I�u�W�F�N�g */
	private FeatureManage featureManage;

	/** ������񃁃��e�i���X�p Model �I�u�W�F�N�g */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/** ���t�H�[����񃁃��e�i���X���s�� Model �I�u�W�F�N�g */
	private ReformManage reformManager;

	/** ���ʃp�����[�^�I�u�W�F�N�g */
	private BuildingPartThumbnailProxy buildingManager;

	/**�ꗗ��ʂ̕\���y�[�W�� */
	private int visibleNavigationPageCount = 5;

	/** �t�@�C�������N���X */
	private PanaFileUtil panaFileUtil;

	/**
	 * �t�@�C�������N���X��ݒ肷��B<br>
	 *
	 * @param panaFileUtil �t�@�C�������N���X
	 */
	public void setPanaFileUtil(PanaFileUtil panaFileUtil) {
		this.panaFileUtil = panaFileUtil;
	}

	/**
	 * �ꗗ��ʂ̕\���y�[�W����ݒ肷��B<br>
	 *
	 * @param rowsPerPage
	 *            �ꗗ��ʂ̕\���y�[�W��
	 */
	public void setVisibleNavigationPageCount(int visibleNavigationPageCount) {
		this.visibleNavigationPageCount = visibleNavigationPageCount;
	}

    /**
     * ���W�y�[�W����ݒ肷��B<br/>
     * <br/>
     * @param featurePageInfoDAO ���W�y�[�W���
     */
	public void setFeaturePageInfoDAO(DAO<FeaturePageInfo> featurePageInfoDAO) {
		this.featurePageInfoDAO = featurePageInfoDAO;
	}

    /**
     * ���ʃp�����[�^�I�u�W�F�N�g��ݒ肷��B<br/>
     * <br/>
     * @param commonParameters ���ʃp�����[�^�I�u�W�F�N�g
     */
    public void setCommonParameters(PanaCommonParameters commonParameters) {
        this.commonParameters = commonParameters;
    }

	/**
	 * ���W�̏����Ǘ����� Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param featureManage
	 *            ���W�̏����Ǘ����� Model �I�u�W�F�N�g
	 */
	public void setFeatureManage(FeatureManage featureManage) {
		this.featureManage = featureManage;
	}

	/**
     * ���ʃp�����[�^�I�u�W�F�N�g��ݒ肷��B<br/>
     * <br/>
     * @param buildingManager ���ʃp�����[�^�I�u�W�F�N�g
     */
	public void setBuildingManager(BuildingPartThumbnailProxy buildingManager) {
		this.buildingManager = buildingManager;
	}

	/**
	 * ���t�H�[���v�����p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param reformManager
	 *            ���t�H�[���v�����p Model �I�u�W�F�N�g
	 */
	public void setReformManager(ReformManage reformManager) {
		this.reformManager = reformManager;
	}

    /**
     * ���ʏ��擾���� Model��ݒ肷��B<br/>
     * <br/>
     * @param PanaCommonManage ���ʏ��擾���� Model �I�u�W�F�N�g
     */
	public void setPanaCommonManage(PanaCommonManage panaCommonManager) {
		this.panaCommonManage = panaCommonManager;
	}

	/**
	 * ������񃁃��e�i���X�p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param panaHousingManager
	 *            ������񃁃��e�i���X�p Model �I�u�W�F�N�g
	 */
	public void setPanaHousingManager(
			PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
	}

	/**
	 * 1�y�[�W������\������ݒ肷��B<br>
	 *
	 * @param rowsPerPage
	 *            1�y�[�W������\����
	 */
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	/**
	 * ���ʃR�[�h�ϊ��I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param codeLookupManager
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * �����ꗗ��ʕ\������<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g
	 * @param response
	 *            HTTP ���X�|���X
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		PanaHousingFormFactory factory = PanaHousingFormFactory
				.getInstance(request);

		// �y�[�W�����p�̃t�H�[���I�u�W�F�N�g���쐬
		PanaFeatureSearchForm housingFeatureForm = factory
				.createFeatureSearchForm(request);

		// ���O�C�����[�U�[�̏����擾
		MypageUserInterface loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);

		// ���[�UID���擾
		int loginFlg = 1;
		if (loginUser != null) {
			loginFlg = 0;
		}else{
			loginFlg = 1;
		}
		model.put("loginFlg", loginFlg);

		housingFeatureForm.setRowsPerPage(this.rowsPerPage);

		housingFeatureForm.setVisibleNavigationPageCount(this.visibleNavigationPageCount);

		model.put("housingFeatureForm", housingFeatureForm);

		// URL����A���W�y�[�WID���擾����B
		String featurePageId = request.getParameter("featurePageId");

		if(StringUtils.isEmpty(featurePageId)){
			throw new RuntimeException("���W�y�[�WID���w�肳��Ă��܂���.");
		}
		// ���W�y�[�WID��ݒ肷��B
		housingFeatureForm.setFeaturePageId(featurePageId);

		housingFeatureForm.setDefaultData();

		FeaturePageInfo featurePageInfo = featurePageInfoDAO.selectByPK(featurePageId);

		model.put("featurePageInfo", featurePageInfo);

		String description = "";
		Iterator<String> descriptionIte = this.codeLookupManager.getKeysByLookup("feature_description");

		while (descriptionIte.hasNext()) {

			String descriptionCd = descriptionIte.next();
			if(descriptionCd.equals(featurePageId)){
				description = this.codeLookupManager.lookupValue("feature_description", descriptionCd);
			}
		}
		model.put("description", description);

		// ���W�ɊY�����镨���̏����擾����B
		int listSize = this.featureManage.searchHousing(housingFeatureForm);
		model.put("listSize", listSize);

		// ������{���
		List<BuildingInfo> buildingList = new ArrayList<BuildingInfo>();

		// ������{���
		List<HousingInfo> housingList = new ArrayList<HousingInfo>();

		// �����ڍ׏��
		List<HousingDtlInfo> housingDtlList = new ArrayList<HousingDtlInfo>();

		// �����ڍ׏��
		List<BuildingDtlInfo> buildingDtlList = new ArrayList<BuildingDtlInfo>();

		// �����摜��񃊃X�g
		List<List<String>> searchHousingImgList = new ArrayList<List<String>>();

		// ��L�ʐϒؐ�
		List<BigDecimal> personalTubosuuList = new ArrayList<BigDecimal>();

		// �����ʐϒؐ�
		List<BigDecimal> houseAreaList = new ArrayList<BigDecimal>();

		// �y�n�ʐϒؐ�
		List<BigDecimal> landAreaList = new ArrayList<BigDecimal>();

		// �z�N��
		List<String> compDateList = new ArrayList<String>();

		// ���t�H�[���v����
		List<List<ReformPlan>> reformPlanList = new ArrayList<List<ReformPlan>>();

		// �����Ŋ��w���
		List<List<BuildingStationInfo>> buildingStationInfoList = new ArrayList<List<BuildingStationInfo>>();

		// �S����Ѓ}�X�^
		List<List<RrMst>> rrMstInfoList = new ArrayList<List<RrMst>>();

		// �H���}�X�^
		List<List<RouteMst>> routeMstInfoList = new ArrayList<List<RouteMst>>();

		// �w���}�X�^
		List<List<StationMst>> stationMstInfoList = new ArrayList<List<StationMst>>();

		// �������߂̃|�C���g
		List<List<String>> iconInfoList = new ArrayList<List<String>>();

		// �V��
		List<String> dateList = new ArrayList<String>();

		// �s���{����
		List<String> prefNameList = new ArrayList<String>();

		// ���y�[�W�̕\���f�[�^��ݒ肷��B
		int selectedPage = housingFeatureForm.getSelectedPage();

		int pageListSize = 0;

		if (listSize - ((selectedPage - 1) * this.rowsPerPage) < this.rowsPerPage) {
			pageListSize = listSize - ((selectedPage - 1) * this.rowsPerPage);
		} else {
			pageListSize = this.rowsPerPage;
		}

		for (int count = 0; count < pageListSize; count++) {
			// �V�X�e������CD���擾����B
			String sysHousingCd = ((HousingInfo) housingFeatureForm
					.getVisibleRows().get(count).getHousingInfo().getItems()
					.get("housingInfo")).getSysHousingCd();

			// �V�X�e������CD���擾����B
			String sysBuildingCd = ((BuildingInfo) housingFeatureForm
					.getVisibleRows().get(count).getBuilding()
					.getBuildingInfo().getItems().get("buildingInfo"))
					.getSysBuildingCd();

			// �ŏI�X�V�����擾����B
			Date updDate = ((HousingInfo) housingFeatureForm
					.getVisibleRows().get(count).getHousingInfo().getItems()
					.get("housingInfo")).getUpdDate();

			// �V�X�e������
			Date now = new Date();

			Calendar cal = Calendar.getInstance();

			cal.setTime(now);
			double nowTime = cal.getTimeInMillis();
			double updTime = 0;
			if (updDate != null) {
				cal.setTime(updDate);
				updTime = cal.getTimeInMillis();
			} else {
				cal.setTime(now);
				updTime = cal.getTimeInMillis();
			}

			// 1�T�Ԉȓ��i�V�X�e�����t - �ŏI�X�V��<= 7���j�v�Z
			double betweenDays = (nowTime - updTime) / (1000 * 3600 * 24);

			String dateFlg = "0";
			// �����ɑ΂��ĐV���A�C�R�����o���B
			if (betweenDays <= 7) {
				dateFlg = "1";
			}

			dateList.add(dateFlg);
			// ������{���
			BuildingInfo buildingInfo = (BuildingInfo) housingFeatureForm
					.getVisibleRows().get(count).getBuilding()
					.getBuildingInfo().getItems().get("buildingInfo");

			// ������{���
			HousingInfo housingInfo = (HousingInfo) housingFeatureForm
					.getVisibleRows().get(count).getHousingInfo().getItems()
					.get("housingInfo");

			// �����ڍ׏��
			HousingDtlInfo housingDtlInfo = (HousingDtlInfo) housingFeatureForm
					.getVisibleRows().get(count).getHousingInfo().getItems()
					.get("housingDtlInfo");

			// �����ڍ׏��
			BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) housingFeatureForm
					.getVisibleRows().get(count).getBuilding().getBuildingInfo().getItems()
					.get("buildingDtlInfo");

			// �������߂̃|�C���g
			List<String> iconInfo = new ArrayList<String>();
			if (!StringUtils.isEmpty(housingInfo.getIconCd())) {
				String[] iconCount = housingInfo.getIconCd().split(",");
				for (int i = 0; i < iconCount.length; i++) {
					iconInfo.add(iconCount[i]);
				}
			}

			// �������߂̃|�C���g
			iconInfoList.add(iconInfo);

			// ��L�ʐϒؐ��v�Z
			BigDecimal personalArea = housingInfo.getPersonalArea();

			if (personalArea != null || "".equals(personalArea)) {
				BigDecimal personalTubosuu = PanaCalcUtil
						.calcTsubo(personalArea);
				personalTubosuuList.add(personalTubosuu);
			} else {
				personalTubosuuList.add(null);
			}

			// �����ʐϒؐ��v�Z
			BigDecimal houseArea = buildingDtlInfo.getBuildingArea();

			if (houseArea != null || "".equals(houseArea)) {
				BigDecimal houseAreaValue = PanaCalcUtil
						.calcTsubo(houseArea);
				houseAreaList.add(houseAreaValue);
			} else {
				houseAreaList.add(null);
			}

			// �y�n�ʐϒؐ��v�Z
			BigDecimal landArea = housingInfo.getLandArea();

			if (landArea != null || "".equals(landArea)) {
				BigDecimal landAreaValue = PanaCalcUtil
						.calcTsubo(landArea);
				landAreaList.add(landAreaValue);
			} else {
				landAreaList.add(null);
			}

			// �z�N��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String compDate = null;
			if (buildingInfo.getCompDate() != null) {
				compDate = String
						.valueOf(sdf.format(buildingInfo.getCompDate()));
			}

			compDateList.add(compDate);

			if(!StringUtils.isEmpty(housingInfo.getReformComment())){
				housingInfo.setReformComment(PanaStringUtils.encodeHtml(housingInfo.getReformComment().trim()));
			}

			if(!StringUtils.isEmpty(housingInfo.getBasicComment())){
				housingInfo.setBasicComment(PanaStringUtils.encodeHtml(housingInfo.getBasicComment().trim()));
			}

			if(!StringUtils.isEmpty(housingDtlInfo.getDtlComment())){
				housingDtlInfo.setDtlComment(PanaStringUtils.encodeHtml(housingDtlInfo.getDtlComment().trim()));
			}

			// �s���{����
			String prefName = this.panaCommonManage.getPrefName(buildingInfo.getPrefCd());
			prefNameList.add(prefName);

			// ������{���
			buildingList.add(buildingInfo);
			// ������{���
			housingList.add(housingInfo);
			// �����ڍ׏��
			housingDtlList.add(housingDtlInfo);
			// �����ڍ׏��
			buildingDtlList.add(buildingDtlInfo);


			// �u�V�X�e������CD�v�ɂ��A�����Ŋ��w���
			Building buildingResultList = this.buildingManager.searchBuildingPk(sysBuildingCd);
			List<JoinResult> srchStation = (List<JoinResult>) buildingResultList.getBuildingStationInfoList();

			// �����Ŋ��w���
			List<BuildingStationInfo> buildingStationInfo = new ArrayList<BuildingStationInfo>();

			// �S����Ѓ}�X�^
			List<RrMst> rrMstInfo = new ArrayList<RrMst>();

			// �H���}�X�^
			List<RouteMst> routeMstInfo = new ArrayList<RouteMst>();

			// �w���}�X�^
			List<StationMst> stationMstInfo = new ArrayList<StationMst>();

			for (int i = 0; i < srchStation.size(); i++) {

				// �����Ŋ��w���
				BuildingStationInfo buildingStation = (BuildingStationInfo) srchStation
						.get(i).getItems().get("buildingStationInfo");
				buildingStationInfo.add(buildingStation);

				// �S����Ѓ}�X�^
				RrMst rrMst = (RrMst) srchStation.get(i)
						.getItems().get("rrMst");
				rrMstInfo.add(rrMst);

				// �H���}�X�^
				RouteMst routeMst = (RouteMst) srchStation.get(i).getItems()
						.get("routeMst");
				routeMstInfo.add(routeMst);

				// �w���}�X�^
				StationMst stationMst = (StationMst) srchStation.get(i)
						.getItems().get("stationMst");
				stationMstInfo.add(stationMst);

			}

			// �����Ŋ��w���
			if (buildingStationInfo.size() > 0) {
				buildingStationInfoList.add(buildingStationInfo);
			} else {
				buildingStationInfoList.add(null);
			}

			// �S����Ѓ}�X�^
			if (rrMstInfo.size() > 0) {
				rrMstInfoList.add(rrMstInfo);
			} else {
				rrMstInfoList.add(null);
			}

			// �H���}�X�^
			if (routeMstInfo.size() > 0) {
				routeMstInfoList.add(routeMstInfo);
			} else {
				routeMstInfoList.add(null);
			}

			// �w���}�X�^
			if (stationMstInfo.size() > 0) {
				stationMstInfoList.add(stationMstInfo);
			} else {
				stationMstInfoList.add(null);
			}

			// �u�V�X�e������CD�v�ɂ��A���t�H�[���v�������̎擾
			List<ReformPlan> searchReformPlan = this.reformManager.searchReformPlan(sysHousingCd);
			if (searchReformPlan.size() > 0) {
				reformPlanList.add(searchReformPlan);
			} else {
				reformPlanList.add(null);
			}

			// �u�V�X�e������CD�v�ɂ��A�����摜���̎擾
			Housing housingresults = this.panaHousingManager.searchHousingPk(
					sysHousingCd, true);

			List<jp.co.transcosmos.dm3.core.vo.HousingImageInfo> searchHousingImg = housingresults.getHousingImageInfos();

			List<String> searchHousingImgInfo = setUrlList(searchHousingImg,model, loginFlg);

			if (searchHousingImgInfo.size() > 0) {

				searchHousingImgList.add(searchHousingImgInfo);
			} else {
				searchHousingImgList.add(null);
			}
		}

		// �V��
		model.put("dateList", dateList);

		// �������߂̃|�C���g
		model.put("iconInfoList", iconInfoList);

		// �����摜���
		model.put("searchHousingImgList", searchHousingImgList);

		// �����Ŋ��w���
		model.put("buildingStationInfoList", buildingStationInfoList);

		// �S����Ѓ}�X�^
		model.put("rrMstInfoList", rrMstInfoList);

		// �H���}�X�^
		model.put("routeMstInfoList", routeMstInfoList);

		// �w���}�X�^
		model.put("stationMstInfoList", stationMstInfoList);

		// ���t�H�[���v����
		model.put("reformPlanList", reformPlanList);

		// �z�N��
		model.put("compDateList", compDateList);

		// ��L�ʐϒؐ�
		model.put("personalTubosuuList", personalTubosuuList);

		// �����ʐϒؐ�
		model.put("houseAreaList", houseAreaList);

		// �y�n�ʐϒؐ�
		model.put("landAreaList", landAreaList);

		// ������{���
		model.put("buildingList", buildingList);

		// ������{���
		model.put("housingList", housingList);

		// �����ڍ׏��
		model.put("housingDtlList", housingDtlList);

		// �����ڍ׏��
		model.put("buildingDtlList", buildingDtlList);

		// �s���{����
		model.put("prefNameList", prefNameList);

		// �擾�����f�[�^�������_�����O�w�֓n��
		return new ModelAndView("success", model);
	}

	/**
     * �摜�\���p��ݒ肷�鏈���B <br>
     *
     * @param housingImageInfoList
     * @param model
     *
     * @return
     */
    private List<String> setUrlList(List<jp.co.transcosmos.dm3.core.vo.HousingImageInfo> housingImageInfoList,Map<String, Object> model, int loginFlg) {

    	List<String> urlInfoList = new ArrayList<String>();

		for (jp.co.transcosmos.dm3.core.vo.HousingImageInfo coreHi : housingImageInfoList) {
			jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo hi = (jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo) coreHi;

			String urlInfo = null;

			// �{������������݂̂̏ꍇ
			String pathName = hi.getPathName();
			String fileName = hi.getFileName();
			String size = this.commonParameters.getHousingListImageSize();

			if (loginFlg == 0) {
				// �{������������݂̂̏ꍇ
				if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(hi.getRoleId())) {
					urlInfo = panaFileUtil.getHousFileMemberUrl(pathName, fileName, size);
					urlInfoList.add(urlInfo);
					break;
				} else {
					// �{���������S���̏ꍇ
					urlInfo = panaFileUtil.getHousFileOpenUrl(pathName, fileName, size);
					urlInfoList.add(urlInfo);
					break;
				}
			} else {
				// �{���������S���̏ꍇ
				if (!PanaCommonConstant.ROLE_ID_PRIVATE.equals(hi.getRoleId())) {
					urlInfo = panaFileUtil.getHousFileOpenUrl(pathName, fileName, size);
					urlInfoList.add(urlInfo);
					break;
				}
			}

		}

 		 // �����摜��񃊃X�g
 		 return urlInfoList;
    }

}
