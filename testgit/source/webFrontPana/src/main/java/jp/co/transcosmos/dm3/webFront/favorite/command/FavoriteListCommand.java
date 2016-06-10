package jp.co.transcosmos.dm3.webFront.favorite.command;

import java.net.URLEncoder;
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
import jp.co.transcosmos.dm3.core.model.favorite.form.FavoriteFormFactory;
import jp.co.transcosmos.dm3.core.model.favorite.form.FavoriteSearchForm;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.util.FavoriteCookieUtils;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.FavoriteInfo;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.RrMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.favorite.PanaFavoriteManageImpl;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.util.PanaCalcUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * ���C�ɓ��蕨���ꗗ
 * ���C�ɓ��蕨���ꗗ��\������B
 *
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *
 * �S����       �C����     �C�����e
 * ------------ ----------- -----------------------------------------------------
 * tan.tianyun   2015.05.06  �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class FavoriteListCommand implements Command {

	/** ���C�ɓ�����p Model �I�u�W�F�N�g */
	private PanaFavoriteManageImpl panaFavoriteManager;

    /** ���ʃp�����[�^�I�u�W�F�N�g */
    private PanaCommonParameters commonParameters;


	/** �������p Model �I�u�W�F�N�g */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/** �����摜���DAO */
	private DAO<HousingImageInfo> housingImageInfoDAO;

	/** ���� Model �I�u�W�F�N�g */
	private PanaCommonManage panaCommonManager;

	/** Panasonic�p�t�@�C�������֘A����Util */
	private PanaFileUtil panaFileUtil;

    /** �P�y�[�W�̕\������ */
    private int rowsPerPage = 15;

	/**
	 * �������p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param PanaFavoriteManageImpl
	 *            �������p Model �I�u�W�F�N�g
	 */
	public void setPanaFavoriteManager(PanaFavoriteManageImpl panaFavoriteManager) {
		this.panaFavoriteManager = panaFavoriteManager;
	}

	/**
	 * @param panaHousingManager �Z�b�g���� panaHousingManager
	 */
	public void setPanaHousingManager(
			PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
	}

	/**
	 * @param commonParameters �Z�b�g���� commonParameters
	 */
	public void setCommonParameters(PanaCommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}


	/**
	 * @param housingImageInfoDAO �Z�b�g���� housingImageInfoDAO
	 */
	public void setHousingImageInfoDAO(DAO<HousingImageInfo> housingImageInfoDAO) {
		this.housingImageInfoDAO = housingImageInfoDAO;
	}

	/**
	 * @param panaCommonManager �Z�b�g���� panaCommonManager
	 */
	public void setPanaCommonManager(PanaCommonManage panaCommonManager) {
		this.panaCommonManager = panaCommonManager;
	}

	/**
	 * @param rowsPerPage �Z�b�g���� rowsPerPage
	 */
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	/**
	 * @param panaFileUtil �Z�b�g���� panaFileUtil
	 */
	public void setPanaFileUtil(PanaFileUtil panaFileUtil) {
		this.panaFileUtil = panaFileUtil;
	}

	/**
	 * ���C�ɓ��蕨���ꗗ��ʕ\������<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g
	 * @param response
	 *            HTTP ���X�|���X
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ���C�ɓ���폜�G���[�̏ꍇ�Aruntime�G���[
		String error = request.getParameter("error");
		if ("RuntimeError".equals(error)) {
			throw new RuntimeException("�V�X�e������CD�܂��̓��[�U�[ID���w�肳��Ă��܂���.");
		}

		Map<String, Object> model = new HashMap<>();
		FavoriteFormFactory factory = FavoriteFormFactory.getInstance(request);

		// �y�[�W�����p�̃t�H�[���I�u�W�F�N�g���쐬
		FavoriteSearchForm form = factory.createFavoriteSearchForm(request);

        // �P�y�[�W�ɕ\������s����ݒ肷��B
		form.setRowsPerPage(this.rowsPerPage);

		String keyOrderType = request.getParameter("keyOrderType");
		String sortOrder1Label = request.getParameter("sortOrder1Label");
		String sortOrder2Label = request.getParameter("sortOrder2Label");
		String sortOrder3Label = request.getParameter("sortOrder3Label");
		String sortOrder4Label = request.getParameter("sortOrder4Label");
		String orderBy = "";
		boolean ascending = true;

		if (StringValidateUtil.isEmpty(keyOrderType)) {
			orderBy = "1";
			ascending = false;
		} else if ("1".equals(keyOrderType)) {
			orderBy = "1";
			ascending = true;
		} else if ("2".equals(keyOrderType)) {
			orderBy = "1";
			ascending = false;
		} else if ("3".equals(keyOrderType)) {
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
		MypageUserInterface loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);

		// ���[�UID���擾
		String userId = String.valueOf(loginUser.getUserId());

		if(userId == null) {
			throw new RuntimeException("���[�U�[ID���w�肳��Ă��܂���.");
		}
		// ���C�ɓ��蕨���ꗗ����
		int publicListSize = this.panaFavoriteManager.searchPublicFavorite(form, userId, orderBy, ascending);

		// ���C�ɓ���e�[�u������u�폜�A����J�v�̕������폜����
		List<FavoriteInfo> listPrivate =  this.panaFavoriteManager.searchPrivateFavorite(userId).get("private");
		for (FavoriteInfo f : listPrivate) {
			this.panaFavoriteManager.delFavorite(f.getUserId(), f.getSysHousingCd());
		}

		/** ������{��񃊃X�g */
		List<HousingInfo> housingInfoList = new ArrayList<HousingInfo>();

		/** ������{��񃊃X�g */
		List<BuildingInfo> buildingInfoList = new ArrayList<BuildingInfo>();

		/** �����ڍ׏�񃊃X�g */
		List<BuildingDtlInfo> buildingDtlInfoList = new ArrayList<BuildingDtlInfo>();

		/** �V���A�C�R�����X�g */
		List<String> dateList = new ArrayList<String>();

		/** �����摜��񃊃X�g */
		List<String> housingImgList = new ArrayList<String>();

		/** ���ݒn���X�g */
		List<String> prefAddressList = new ArrayList<String>();

		/** �����Ŋ��w��� */
		List<List<String>> buildingStationList = new ArrayList<List<String>>();

		/** �z�N����� */
		List<String> compDateList = new ArrayList<String>();;

		/** ��L�ʐ� �؃��X�g */
		List<String> personalAreaSquareList = new ArrayList<String>();

		/** �y�n�ʐ� �؃��X�g */
		List<String> landAreaSquareList = new ArrayList<String>();

		/** �����ʐ� �؃��X�g */
		List<String> buildingAreaSquareList = new ArrayList<String>();

		/** �K�����X�g */
		List<String> totalFloorList = new ArrayList<String>();

		/** ���݊K���X�g */
		List<String> floorNoList = new ArrayList<String>();

		/** URL Encode���X�g */
		List<String> encodeHousingNameList = new ArrayList<String>();

		for (int count = 0; count < form.getVisibleRows().size(); count++) {
			FavoriteInfo favoriteInfo = (FavoriteInfo) form.getVisibleRows().get(count).getItems().get("favoriteInfo");
			String sysHousingCd = favoriteInfo.getSysHousingCd();
			Housing housing = this.panaHousingManager.searchHousingPk(sysHousingCd, true);

	        // ������{�����擾����B
	        HousingInfo housingInfo = ((HousingInfo) housing.getHousingInfo().getItems().get("housingInfo"));
	        encodeHousingNameList.add(URLEncoder.encode(housingInfo.getDisplayHousingName(), "UTF-8"));

			// ������{�����擾����B
			BuildingInfo buildingInfo = (BuildingInfo)housing.getBuilding().getBuildingInfo().getItems().get("buildingInfo");

			// �����ڍ׏����擾����B
			BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo)housing.getBuilding().getBuildingInfo().getItems().get("buildingDtlInfo");

			housingInfoList.add(housingInfo);
			buildingInfoList.add(buildingInfo);
			buildingDtlInfoList.add(buildingDtlInfo);

			/** �V���A�C�R���ݒ� */
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


			/** �����摜���ݒ� */
			String pathName = "";
			// �{������������A���A�\�����A�摜�^�C�v�A�}�Ԃŏ����\�[�g���1�Ԗڂ̉摜��\��
			DAOCriteria criteria = new DAOCriteria();
			criteria.addWhereClause("sysHousingCd", sysHousingCd);
			criteria.addWhereClause("roleId", null, DAOCriteria.IS_NULL, true);
			criteria.addOrderByClause("sortOrder");
			criteria.addOrderByClause("imageType");
			criteria.addOrderByClause("divNo");
			List<HousingImageInfo> housingImageInfoList = this.housingImageInfoDAO.selectByFilter(criteria);
			if (housingImageInfoList.size() > 0) {
				jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo housingImageInfo = (jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo)housingImageInfoList.get(0);
				if (PanaCommonConstant.ROLE_ID_PUBLIC.equals(housingImageInfo.getRoleId())) {
					// �{���������S���̏ꍇ
					pathName = this.panaFileUtil.getHousFileOpenUrl(housingImageInfoList.get(0).getPathName(), housingImageInfoList.get(0).getFileName(), this.commonParameters.getHousingListImageSize());
				} else if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(housingImageInfo.getRoleId())) {
					// �{������������݂̂̏ꍇ
					pathName = this.panaFileUtil.getHousFileMemberUrl(housingImageInfoList.get(0).getPathName(), housingImageInfoList.get(0).getFileName(), this.commonParameters.getHousingListImageSize());
				}
			}
             housingImgList.add(pathName);

             /** ���ݒn�ݒ� */
     		StringBuffer prefAddressName = new StringBuffer();
     		if (!StringValidateUtil.isEmpty(buildingInfo.getPrefCd())) {
     			prefAddressName.append(this.panaCommonManager.getPrefName(buildingInfo.getPrefCd())).append(" ");
     			prefAddressName.append(buildingInfo.getAddressName() == null?"":buildingInfo.getAddressName()).append(" ");
     			prefAddressName.append(buildingInfo.getAddressOther1() == null?"":buildingInfo.getAddressOther1()).append(" ");
     			prefAddressName.append(buildingInfo.getAddressOther2() == null?"":buildingInfo.getAddressOther2());
     		}
             prefAddressList.add(prefAddressName.toString());


             /** �A�N�Z�X��ݒ肷��  */
     		// �����Ŋ��w���List���擾
     		 List<JoinResult> buildingStationInfoList = housing.getBuilding().getBuildingStationInfoList();
     		 BuildingStationInfo buildingStationInfo = new BuildingStationInfo();
     		 RrMst rrMst = new RrMst();
     		 RouteMst routeMst = new RouteMst();
     		 StationMst stationMst = new StationMst();
     		 StringBuffer nearStation = new StringBuffer();
     		 List<String> nearStationList = new ArrayList<String>();
    		 for (int i = 0; i < buildingStationInfoList.size(); i++) {

    			 // �����Ŋ��w���̎擾
    			 buildingStationInfo = new BuildingStationInfo();
    			 buildingStationInfo =  (BuildingStationInfo)buildingStationInfoList.get(i).getItems().get("buildingStationInfo");
    			 // �S����Ѓ}�X�^�̎擾
    			 rrMst = new RrMst();
    			 rrMst = (RrMst)buildingStationInfoList.get(i).getItems().get("rrMst");
    			 // �H���}�X�^�̎擾
    			 routeMst = new RouteMst();
    			 routeMst = (RouteMst)buildingStationInfoList.get(i).getItems().get("routeMst");
    			 // �w�}�X�^�̎擾
    			 stationMst = new StationMst();
    			 stationMst = (StationMst)buildingStationInfoList.get(i).getItems().get("stationMst");

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
    		 buildingStationList.add(nearStationList);

			/** �z�N����ݒ肷��  */
    		String compDate = "";
			if (buildingInfo != null && buildingInfo.getCompDate() != null) {
				compDate = new SimpleDateFormat("yyyy�NM���z").format(buildingInfo.getCompDate());
			}
			compDateList.add(compDate);

			/** ��L�ʐρ^�����ʐρ^�y�n�ʐρA�K���^���݊K��ݒ肷��  */
			// ��L�ʐ� ��
			String personalAreaSquare = "";
			// �y�n�ʐ� ��
			String landAreaSquare = "";
			// �����ʐ� ��
			String buildingAreaSquare = "";
			// �K��
			String totalFloor = "";
			// ���݊K
			String floorNo = "";


			// �������CD�𔻒f
			if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(buildingInfo.getHousingKindCd())) {
				if (housingInfo.getPersonalArea() != null) {
					// ��L�ʐ� ��
					personalAreaSquare = ((housingInfo.getPersonalArea() == null) ? "" : "�i��" + PanaCalcUtil.calcTsubo(housingInfo.getPersonalArea()).toString() + "�؁j") +
								(housingInfo.getPersonalAreaMemo() == null ? "" : housingInfo.getPersonalAreaMemo());
				}
				if (buildingInfo != null) {
					// �K��
					totalFloor = (buildingInfo.getTotalFloors() == null) ? "" : buildingInfo.getTotalFloors() + "�K��";
					// ���݊K
					floorNo = (housingInfo.getFloorNo() == null) ? "" : housingInfo.getFloorNo() + "�K";
				}
			} else if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(buildingInfo.getHousingKindCd()) ||
					PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(buildingInfo.getHousingKindCd())) {
				if (buildingDtlInfo != null && buildingDtlInfo.getBuildingArea() != null) {
					// �����ʐ� ��
					buildingAreaSquare = ((buildingDtlInfo.getBuildingArea() == null) ? "" : "�i��" + PanaCalcUtil.calcTsubo(buildingDtlInfo.getBuildingArea()).toString() + "�؁j") +
								(buildingDtlInfo.getBuildingAreaMemo() == null? "" : buildingDtlInfo.getBuildingAreaMemo()) ;
				}
				if (housingInfo.getLandArea() != null) {
					// �y�n�ʐρ@��
					landAreaSquare = ((housingInfo.getLandArea() == null) ? "" : "�i��" + PanaCalcUtil.calcTsubo(housingInfo.getLandArea()).toString() + "�؁j") +
								(housingInfo.getLandAreaMemo() == null? "" : housingInfo.getLandAreaMemo()) ;
				}
			}
			personalAreaSquareList.add(personalAreaSquare);
			landAreaSquareList.add(landAreaSquare);
			buildingAreaSquareList.add(buildingAreaSquare);
			totalFloorList.add(totalFloor);
			floorNoList.add(floorNo);
		}

		model.put("housingInfoList", housingInfoList);
		model.put("buildingInfoList", buildingInfoList);
		model.put("buildingDtlInfoList", buildingDtlInfoList);
		model.put("dateList", dateList);
		model.put("housingImgList", housingImgList);
		model.put("prefAddressList", prefAddressList);
		model.put("buildingStationList", buildingStationList);
		model.put("compDateList", compDateList);
		model.put("personalAreaSquareList", personalAreaSquareList);
		model.put("landAreaSquareList", landAreaSquareList);
		model.put("buildingAreaSquareList", buildingAreaSquareList);
		model.put("totalFloorList", totalFloorList);
		model.put("floorNoList", floorNoList);
		model.put("keyOrderType", keyOrderType);
		model.put("sortOrder1Label", sortOrder1Label == null? "���C�ɓ���o�^�� ��" : sortOrder1Label);
		model.put("sortOrder2Label", sortOrder2Label == null? "�������i ��" : sortOrder2Label);
		model.put("sortOrder3Label", sortOrder3Label == null? "�z�N�� ��" : sortOrder3Label);
		model.put("sortOrder4Label", sortOrder4Label == null? "�w����̋��� ��" : sortOrder4Label);
		model.put("privateFavoriteHousingList", listPrivate);
		model.put("publicFavoriteHousingListForm", form);
		model.put("encodeHousingNameList", encodeHousingNameList);

		// Cookie��ݒ肷��
		FavoriteCookieUtils.getInstance(request).setFavoriteCount(request, response, publicListSize);
		request.setAttribute(FavoriteCookieUtils.FAVORITE_COUNT_KEY, publicListSize);

		return new ModelAndView("success", model);
	}

}
