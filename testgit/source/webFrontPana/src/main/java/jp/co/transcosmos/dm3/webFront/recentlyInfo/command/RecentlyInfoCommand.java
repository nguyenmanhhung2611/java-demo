package jp.co.transcosmos.dm3.webFront.recentlyInfo.command;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.core.util.RecentlyCookieUtils;
import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.RrMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.RecentlyInfoManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousing;
import jp.co.transcosmos.dm3.corePana.util.PanaCalcUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * �ŋߌ��������ꗗ
 * �ŋߌ��������ꗗ��ʂ�\������B
 *
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *
 * �S����       �C����     �C�����e
 * ------------ ----------- -----------------------------------------------------
 * tang.tianyun 2015.05.07  �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class RecentlyInfoCommand implements Command {

    /** ���ʃp�����[�^�I�u�W�F�N�g */
    private PanaCommonParameters commonParameters;

	/** �ŋߌ������������Ǘ����� Model �I�u�W�F�N�g */
	private RecentlyInfoManage recentlyInfoManage;

	/** �����摜���DAO */
	private DAO<HousingImageInfo> HousingImageInfoDAO;

	/** Panasonic�p�t�@�C�������֘A����Util */
	private PanaFileUtil fileUtil;

    /**
     * ���ʃp�����[�^�I�u�W�F�N�g��ݒ肷��B<br/>
     * <br/>
     * @param commonParameters ���ʃp�����[�^�I�u�W�F�N�g
     */
    public void setCommonParameters(PanaCommonParameters commonParameters) {
        this.commonParameters = commonParameters;
    }

	/**
	 * �ŋߌ����������p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param recentlyInfoManage �ŋߌ����������p Model �I�u�W�F�N�g
	 */
	public void setRecentlyInfoManage(RecentlyInfoManage recentlyInfoManage) {
		this.recentlyInfoManage = recentlyInfoManage;
	}

	/**
	 * �����摜���DAO��ݒ肷��B<br/>
	 * <br/>
	 * @param housingImageInfoDAO �����摜���DAO
	 */
	public void setHousingImageInfoDAO(DAO<HousingImageInfo> housingImageInfoDAO) {
		HousingImageInfoDAO = housingImageInfoDAO;
	}

	/**
	 *Panasonic�p�t�@�C�������֘A����Util��ݒ肷��B<br/>
	 * <br/>
	 * @param fileUtil Panasonic�p�t�@�C�������֘A����Util
	 */
	public void setFileUtil(PanaFileUtil fileUtil) {
		this.fileUtil = fileUtil;
	}


	/**
	 * �ŋߌ��������ꗗ��ʕ\������<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g
	 * @param response
	 *            HTTP ���X�|���X
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		// �\�[�g����ݒ肷��B
		String keyOrderType = request.getParameter("keyOrderType");
		String sortOrder1Label = request.getParameter("sortOrder1Label");
		String sortOrder2Label = request.getParameter("sortOrder2Label");
		String sortOrder3Label = request.getParameter("sortOrder3Label");
		String sortOrder4Label = request.getParameter("sortOrder4Label");
		String orderBy = "";
		boolean ascending = true;

		if ("1".equals(keyOrderType)) {
			orderBy = "1";
			ascending = true;
		} else if (StringValidateUtil.isEmpty(keyOrderType) || "2".equals(keyOrderType)){
			orderBy = "1";
			ascending = false;
		}else if ("3".equals(keyOrderType)) {
			orderBy = "2";
			ascending = true;
		} else if ("4".equals(keyOrderType)) {
			orderBy = "2";
			ascending = false;
		} else if ("5".equals(keyOrderType)) {
			orderBy = "3";
			ascending = false;
		} else if ("6".equals(keyOrderType)) {
			orderBy = "3";
			ascending = true;
		} else if ("7".equals(keyOrderType)) {
			orderBy = "4";
			ascending = true;
		} else if ("8".equals(keyOrderType)) {
			orderBy = "4";
			ascending = false;
		}

		// ���O�C�����[�U�[�̏����擾
		LoginUser loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);

		// ���O�C��Flg ��ݒ�
		String loginFlg;
		if (loginUser != null) {
			loginFlg = "0";
			model.put("loginFlg", loginFlg);
		// loginUser��NULL�̏ꍇ�A�񃍃O�C����ԂƂȂ�B
		} else {
			loginUser = FrontLoginUserUtils.getInstance(request).getAnonLoginUserInfo(request, response);
			loginFlg = "1";
			model.put("loginFlg", loginFlg);
		}
		// ���[�U�[ID���擾
		String userId = (String) loginUser.getUserId();

		// �ŋߌ������������폜
		this.recentlyInfoManage.delRecentlyInfo(userId);

		// �ŋߌ��������ꗗ����
		List<PanaHousing> recentlyInfoList = this.recentlyInfoManage.getRecentlyInfoListMap(userId, orderBy, ascending);

		// 1�T�Ԉȓ��v�Z���
		List<String> dateList = new ArrayList<String>();
		// ������{���
		List<BuildingInfo> buildingList = new ArrayList<BuildingInfo>();
		// ������{���
		List<HousingInfo> housingList = new ArrayList<HousingInfo>();
		// �����摜��񃊃X�g
		List<String> searchHousingImgList = new ArrayList<String>();
		// ���ݒn���X�g
		List<String> addressList = new ArrayList<String>();
		// �A�N�Z�X���X�g
		List<List<String>> nearStationLists = new ArrayList<List<String>>();
		// ��L�ʐσ��X�g
		List<String> personalAreaList = new ArrayList<String>();
		// ��L�ʐ� �؃��X�g
		List<String> personalAreaSquareList = new ArrayList<String>();
		// �����ʐσ��X�g
		List<String> buildingAreaList = new ArrayList<String>();
		// �����ʐ� �؃��X�g
		List<String> buildingAreaSquareList = new ArrayList<String>();
		// �y�n�ʐσ��X�g
		List<String> landAreaList = new ArrayList<String>();
		// �y�n�ʐ� �؃��X�g
		List<String> landAreaSquareList = new ArrayList<String>();
		// �z�N�����X�g
		List<String> compDateList = new ArrayList<String>();
		// �K���A���݊K���X�g
		List<String> totalFloorList = new ArrayList<String>();
		List<String> floorNoList = new ArrayList<String>();

		if (recentlyInfoList != null && recentlyInfoList.size() > 0) {

			// �ŋߌ��������ꗗ��ʕ\������
			int cnt = this.commonParameters.getMaxRecentlyInfoCnt();
			int i = 0;

			for (PanaHousing housing : recentlyInfoList) {

				if (housing != null) {

					// ������{�����擾����B
					HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");
					// ������{�����擾����B
					BuildingInfo buildingInfo = (BuildingInfo) housing.getBuilding().getBuildingInfo().getItems().get("buildingInfo");
					// �����Ŋ��w�����擾����B
					List<JoinResult> buildingStationInfoList = housing.getBuilding().getBuildingStationInfoList();
					// �����ڍ׏����擾
					BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo)housing.getBuilding().getBuildingInfo().getItems().get("buildingDtlInfo");
					// �s���{���}�X�^���擾
					PrefMst prefMst = (PrefMst)housing.getBuilding().getBuildingInfo().getItems().get("prefMst");


					// 1�T�Ԉȓ��i�V�X�e�����t - �ŏI�X�V��<= 7���j�ɒǉ����ꂽ�����ɑ΂��ĐV���A�C�R�����o���B
					// �ŏI�X�V�����擾����B
					Date updDate = housingInfo.getUpdDate();
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


					// �����摜����ݒ肷��B
					String pathName = "";
					// �{������������A���A�\�����A�摜�^�C�v�A�}�Ԃŏ����\�[�g���1�Ԗڂ̉摜��\��
					DAOCriteria criteria = new DAOCriteria();
					criteria.addWhereClause("sysHousingCd", housingInfo.getSysHousingCd());
					criteria.addWhereClause("roleId", null, DAOCriteria.IS_NULL, true);
					if(loginFlg == "1"){
						criteria.addWhereClause("roleId", PanaCommonConstant.ROLE_ID_PUBLIC);
					}
					criteria.addOrderByClause("sortOrder");
					criteria.addOrderByClause("imageType");
					criteria.addOrderByClause("divNo");
					List<HousingImageInfo> housingImageInfoList = this.HousingImageInfoDAO.selectByFilter(criteria);
					if (housingImageInfoList.size() > 0) {
						jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo housingImageInfo = (jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo)housingImageInfoList.get(0);
						if (PanaCommonConstant.ROLE_ID_PUBLIC.equals(housingImageInfo.getRoleId())) {
							// �{���������S���̏ꍇ
							pathName = this.fileUtil.getHousFileOpenUrl(housingImageInfo.getPathName(), housingImageInfo.getFileName(), this.commonParameters.getHousingListImageSize());
						} else if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(housingImageInfo.getRoleId())) {
							// �{������������݂̂̏ꍇ
							pathName = this.fileUtil.getHousFileMemberUrl(housingImageInfo.getPathName(), housingImageInfo.getFileName(), this.commonParameters.getHousingListImageSize());
						}
					}
					searchHousingImgList.add(pathName);


					// ���ݒn��ݒ肷��B
					StringBuffer address = new StringBuffer();
					if (!StringValidateUtil.isEmpty(buildingInfo.getPrefCd())) {
						if (prefMst.getPrefName() != null) {
							address.append(prefMst.getPrefName()).append(" ");
							address.append(buildingInfo.getAddressName() == null?"":buildingInfo.getAddressName()).append(" ");
							address.append(buildingInfo.getAddressOther1() == null?"":buildingInfo.getAddressOther1()).append(" ");
							address.append(buildingInfo.getAddressOther2() == null?"":buildingInfo.getAddressOther2());
						}
					}
					// ���ݒn���X�g
					addressList.add(address.toString());


					// �A�N�Z�X��ݒ肷��B
					// �����Ŋ��w���List���擾
					 BuildingStationInfo buildingStationInfo = new BuildingStationInfo();
		     		 RrMst rrMst = new RrMst();
					 RouteMst routeMst = new RouteMst();
					 StationMst stationMst = new StationMst();
					 StringBuffer nearStation = new StringBuffer();
					 List<String> nearStationList = new ArrayList<String>();
					 for (int j = 0; j < buildingStationInfoList.size(); j++) {

						 // �����Ŋ��w���̎擾
						 buildingStationInfo = new BuildingStationInfo();
						 buildingStationInfo =  (BuildingStationInfo)buildingStationInfoList.get(j).getItems().get("buildingStationInfo");
		    			 // �S����Ѓ}�X�^�̎擾
		    			 rrMst = new RrMst();
		    			 rrMst = (RrMst)buildingStationInfoList.get(j).getItems().get("rrMst");
						 // �H���}�X�^�̎擾
						 routeMst = new RouteMst();
						 routeMst = (RouteMst)buildingStationInfoList.get(j).getItems().get("routeMst");
						 // �w�}�X�^�̎擾
						 stationMst = new StationMst();
						 stationMst = (StationMst)buildingStationInfoList.get(j).getItems().get("stationMst");

						 // ��\�H����
						 nearStation = new StringBuffer();
		    			 if (!StringValidateUtil.isEmpty(rrMst.getRrName())) {
		    				 nearStation.append(rrMst.getRrName());
		    			 }
						 if (!StringValidateUtil.isEmpty(routeMst.getRouteName())) {
							 nearStation.append(routeMst.getRouteName()).append(" ");
						 } else {
							 if (!StringValidateUtil.isEmpty(buildingStationInfo.getDefaultRouteName())) {
								 nearStation.append(buildingStationInfo.getDefaultRouteName()).append(" ");
							 }
						 }
						 // �w��
						 if (!StringValidateUtil.isEmpty(stationMst.getStationName())) {
							 nearStation.append(stationMst.getStationName()).append("�w ");
						 } else {
							 if (!StringValidateUtil.isEmpty(buildingStationInfo.getStationName())) {
								 nearStation.append(buildingStationInfo.getStationName()).append("�w ");
							 }
						 }
						 // �o�X��Ж�
						 if (!StringValidateUtil.isEmpty(buildingStationInfo.getBusCompany())) {
							 nearStation.append(buildingStationInfo.getBusCompany()).append(" ");
						 }
						 // �o�X�₩��̓k������
						 if (buildingStationInfo.getTimeFromBusStop() != null) {
							 nearStation.append("�k��").append(String.valueOf(buildingStationInfo.getTimeFromBusStop())).append("��");
						 }
						 nearStationList.add(nearStation.toString());
					 }
					// �A�N�Z�X���X�g
					nearStationLists.add(nearStationList);


					// �z�N��
					String compDate = "";
					if (buildingInfo != null && buildingInfo.getCompDate() != null) {
						compDate = new SimpleDateFormat("yyyy�NM���z").format(buildingInfo.getCompDate());
					}
					compDateList.add(compDate);


					// �������CD�𔻒f
					String personalArea = "";
					String personalAreaSquare = "";
					String buildingArea = "";
					String buildingAreaSquare = "";
					String landArea = "";
					String landAreaSquare = "";
					// �K��
					String totalFloor = "";
					// ���݊K
					String floorNo = "";
					if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(buildingInfo.getHousingKindCd())) {
						if (housingInfo.getPersonalArea() != null) {
							// ��L�ʐ�
							personalArea = housingInfo.getPersonalArea().toString();
							// ��L�ʐ� ��
							personalAreaSquare = ((housingInfo.getPersonalArea() == null) ? "" : "�i��" + PanaCalcUtil.calcTsubo(housingInfo.getPersonalArea()).toString() + "�؁j") +
										(housingInfo.getPersonalAreaMemo() == null? "" : housingInfo.getPersonalAreaMemo()) ;
						}
						if (buildingInfo != null) {
							// �K��
							totalFloor = (buildingInfo.getTotalFloors() == null) ? "" : buildingInfo.getTotalFloors() + "�K��";
							// ���݊K
							floorNo = (housingInfo.getFloorNo() == null) ? "" : housingInfo.getFloorNo() + "�K";
						}
					} else {
						if (buildingDtlInfo != null && buildingDtlInfo.getBuildingArea() != null) {
							// �����ʐ�
							buildingArea = buildingDtlInfo.getBuildingArea().toString();
							// �����ʐρ@��
							buildingAreaSquare = ((buildingDtlInfo.getBuildingArea() == null) ? "" : "�i��" + PanaCalcUtil.calcTsubo(buildingDtlInfo.getBuildingArea()).toString() + "�؁j") +
										(buildingDtlInfo.getBuildingAreaMemo() == null? "" : buildingDtlInfo.getBuildingAreaMemo()) ;
						}
						if (housingInfo.getLandArea() != null) {
							// �y�n�ʐ�
							landArea = housingInfo.getLandArea().toString();
							// �y�n�ʐρ@��
							landAreaSquare = ((housingInfo.getLandArea() == null) ? "" : "�i��" + PanaCalcUtil.calcTsubo(housingInfo.getLandArea()).toString() + "�؁j") +
										(housingInfo.getLandAreaMemo() == null? "" : housingInfo.getLandAreaMemo()) ;
						}
					}
					// ��L�ʐ�
					personalAreaList.add(personalArea);
					// ��L�ʐ� ��
					personalAreaSquareList.add(personalAreaSquare);
					// �K���^���݊K
					totalFloorList.add(totalFloor);
					floorNoList.add(floorNo);
					// �����ʐ�
					buildingAreaList.add(buildingArea);
					// �����ʐ� ��
					buildingAreaSquareList.add(buildingAreaSquare);
					// �y�n�ʐ�
					landAreaList.add(landArea);
					// �y�n�ʐ� ��
					landAreaSquareList.add(landAreaSquare);
					// ������{���
					buildingList.add(buildingInfo);
					// ������{���
					housingList.add(housingInfo);

					i++;

					if (i >= cnt) {
						break;
					}
				}
			}
		}

		// �ŋߌ��������ꗗ���X�gSize
		model.put("recentlyInfoListSize", recentlyInfoList.size());
		// �ŋߌ��������ꗗ���X�gSize
		model.put("recentlyInfoList", recentlyInfoList);
		// 1�T�Ԉȓ��v�Z���
		model.put("dateList", dateList);
		// �����摜���
		model.put("searchHousingImgList", searchHousingImgList);
		// ���ݒn���
		model.put("addressList", addressList);
		// �A�N�Z�X���
		model.put("nearStationLists", nearStationLists);
		// �z�N��
		model.put("compDateList", compDateList);
		// ��L�ʐ�
		model.put("personalAreaList", personalAreaList);
		// ��L�ʐ� ��
		model.put("personalAreaSquareList", personalAreaSquareList);
		// �����ʐ�
		model.put("buildingAreaList", buildingAreaList);
		// �����ʐ� ��
		model.put("buildingAreaSquareList", buildingAreaSquareList);
		// �y�n�ʐ�
		model.put("landAreaList", landAreaList);
		// �y�n�ʐ� ��
		model.put("landAreaSquareList", landAreaSquareList);
		// �K���A���݊K
		model.put("totalFloorList", totalFloorList);
		model.put("floorNoList", floorNoList);
		// ������{���
		model.put("buildingList", buildingList);
		// ������{���
		model.put("housingList", housingList);
		// ���בւ�
		model.put("keyOrderType", keyOrderType);
		model.put("sortOrder1Label", sortOrder1Label == null? "�ŋߌ������t ��" : sortOrder1Label);
		model.put("sortOrder2Label", sortOrder2Label == null? "�������i ��" : sortOrder2Label);
		model.put("sortOrder3Label", sortOrder3Label == null? "�z�N�� ��" : sortOrder3Label);
		model.put("sortOrder4Label", sortOrder4Label == null? "�w����̋��� ��" : sortOrder4Label);

		// Cookie��ݒ肷��
		RecentlyCookieUtils.getInstance(request).setRecentlyCount(request, response, recentlyInfoList.size());
		request.setAttribute(RecentlyCookieUtils.HISTORY_COUNT_KEY, recentlyInfoList.size());

		return new ModelAndView("success", model);
	}
}
