package jp.co.transcosmos.dm3.webFront.housingListMation.command;

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
import jp.co.transcosmos.dm3.core.model.building.Building;
import jp.co.transcosmos.dm3.core.model.building.BuildingPartThumbnailProxy;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.HousingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.PartSrchMst;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.RrMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.ReformManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.util.PanaCalcUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * �����ꗗ���
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
public class HousingListMatinoInitCommand implements Command {

	/** �f�B�t�H���g���i�\�[�g�����j */
	public static final String SORT_DEFAULT = "4";

	/** ���t�H�[����񃁃��e�i���X���s�� Model �I�u�W�F�N�g */
	private ReformManage reformManager;

	/** �P�y�[�W�̕\������ */
	private int rowsPerPage = 15;

	/** �t�@�C�������N���X */
	private PanaFileUtil panaFileUtil;

	/** ������� */
	private String housingKindCd = "";

	/** �������p Model �I�u�W�F�N�g */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/** ���� Model �I�u�W�F�N�g */
	private PanaCommonManage panaCommonManage;

    /** ���ʃp�����[�^�I�u�W�F�N�g */
    private PanaCommonParameters commonParameters;

	/** ���ʃR�[�h�I�u�W�F�N�g */
	protected CodeLookupManager codeLookupManager;

	/** �������p Model �I�u�W�F�N�g */
	private BuildingPartThumbnailProxy buildingManager;

	/**�ꗗ��ʂ̕\���y�[�W�� */
	private int visibleNavigationPageCount = 5;

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
     * ���ʃp�����[�^�I�u�W�F�N�g��ݒ肷��B<br/>
     * <br/>
     * @param commonParameters ���ʃp�����[�^�I�u�W�F�N�g
     */
    public void setCommonParameters(PanaCommonParameters commonParameters) {
        this.commonParameters = commonParameters;
    }

    /**
     * �������p�I�u�W�F�N�g��ݒ肷��B<br/>
     * <br/>
     * @param buildingManager �������p�I�u�W�F�N�g
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
	 * �������p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param panaHousingManager
	 *            �������p Model �I�u�W�F�N�g
	 */
	public void setPanaHousingManager(
			PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
	}

    /**
     * ���� Model�I�u�W�F�N�g��ݒ肷��B<br/>
     * <br/>
     * @param PanaCommonManage ���� Model�I�u�W�F�N�g
     */
	public void setPanaCommonManage(PanaCommonManage panaCommonManager) {
		this.panaCommonManage = panaCommonManager;
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
	 * ������ނ�ݒ肷��B<br>
	 *
	 * @param housingKindCd
	 *            �������
	 */
	public void setHousingKindCd(String housingKindCd) {
		this.housingKindCd = housingKindCd;
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
		PanaHousingSearchForm housingListMationForm = factory
				.createPanaHousingSearchForm();

		FormPopulator.populateFormBeanFromRequest(request,
				housingListMationForm);

		housingListMationForm.setVisibleNavigationPageCount(this.visibleNavigationPageCount);

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

		String clearFlg = housingListMationForm.getClearFlg();

		// URL����A�����������擾����B
		String prefCd = request.getParameter("prefCd");

		if(StringUtils.isEmpty(prefCd)){
			throw new RuntimeException("�s���{��CD���w�肳��Ă��܂���.");
		}

		model.put("prefCd", prefCd);

		String addressCd = request.getParameter("addressCd");
		String routeCd = request.getParameter("routeCd");
		String stationCd = request.getParameter("stationCd");

		// URL���ŁA�擾�̏������t�H�[���ɐݒ肷��B
		housingListMationForm.setRowsPerPage(this.rowsPerPage);
		housingListMationForm.setKeyHiddenFlg(PanaCommonConstant.HIDDEN_FLG_PUBLIC);
		housingListMationForm.setKeyPrefCd(prefCd);
		housingListMationForm.setHousingKindCd(this.housingKindCd);
		housingListMationForm.setKeyHousingKindCd(this.housingKindCd);
		housingListMationForm.setKeyAddressCd(addressCd);
		housingListMationForm.setKeyRouteCd(routeCd);
		housingListMationForm.setKeyStationCd(stationCd);

		// �ˌ��ƃ}���V������J�ڎ��A�����������N���A�B
		if ("1".equals(clearFlg)) {

			housingListMationForm = factory
					.createPanaHousingSearchForm();

			housingListMationForm.setKeyHousingKindCd(this.housingKindCd);
			housingListMationForm.setHousingKindCd(this.housingKindCd);
			housingListMationForm.setKeyPrefCd(prefCd);

		}

		// �f�B�t�H���g�̃\�[�g����ݒ肷��B
		if (StringUtils.isEmpty(housingListMationForm
				.getSortUpdDateValue())) {
			housingListMationForm.setSortUpdDateValue("3");
			housingListMationForm.setSortPriceValue("1");
			housingListMationForm.setSortBuildDateValue("6");
			housingListMationForm.setSortWalkTimeValue("7");

			housingListMationForm.setKeyOrderType(SORT_DEFAULT);
		}

		// �\�Z
		Iterator<String> priceIte = this.codeLookupManager
				.getKeysByLookup("price");

		while (priceIte.hasNext()) {

			String price = priceIte.next();
			if (price.equals(housingListMationForm.getPriceLower())) {
				// ����/���i�E�����i���������j
				if (!StringUtils.isEmpty(housingListMationForm
						.getPriceLower())) {

					String temp = this.codeLookupManager.lookupValue(
							"price", price);
					housingListMationForm.setKeyPriceLower(Long.valueOf(temp + "0000"));

				}
			}

			if (price.equals(housingListMationForm.getPriceUpper())) {
				// ����/���i�E����i���������j
				if (!StringUtils.isEmpty(housingListMationForm
						.getPriceUpper())) {

					String temp = this.codeLookupManager.lookupValue(
							"price", price);

					housingListMationForm.setKeyPriceUpper(Long
							.valueOf(temp + "0000"));

				}
			}
		}

		// ��L�ʐ�
		Iterator<String> personalAreaIte = this.codeLookupManager
				.getKeysByLookup("area");

		while (personalAreaIte.hasNext()) {

			String personalArea = personalAreaIte.next();
			if (personalArea.equals(housingListMationForm
					.getPersonalAreaLower())) {
				// ��L�ʐρE�����i���������j
				if (!StringUtils.isEmpty(housingListMationForm
						.getPersonalAreaLower())) {

					String temp = this.codeLookupManager.lookupValue(
							"area", personalArea);
					housingListMationForm.setKeyPersonalAreaLower(BigDecimal
							.valueOf(Double.valueOf(temp)));

				}
			}

			if (personalArea.equals(housingListMationForm
					.getPersonalAreaUpper())) {
				// ��L�ʐρE�����i���������j
				if (!StringUtils.isEmpty(housingListMationForm
						.getPersonalAreaUpper())) {

					String temp = this.codeLookupManager.lookupValue(
							"area", personalArea);
					housingListMationForm.setKeyPersonalAreaUpper(BigDecimal
							.valueOf(Double.valueOf(temp)));

				}
			}

		}

		// �����ʐ�
		Iterator<String> buildingAreaIte = this.codeLookupManager
				.getKeysByLookup("area");

		while (buildingAreaIte.hasNext()) {

			String buildingArea = buildingAreaIte.next();
			if (buildingArea.equals(housingListMationForm
					.getBuildingAreaLower())) {
				// �����ʐρE�����i���������j
				if (!StringUtils.isEmpty(housingListMationForm
						.getBuildingAreaLower())) {

					String temp = this.codeLookupManager.lookupValue(
							"area", buildingArea);
					housingListMationForm.setKeyBuildingAreaLower(BigDecimal
							.valueOf(Double.valueOf(temp)));

				}
			}

			if (buildingArea.equals(housingListMationForm
					.getBuildingAreaUpper())) {
				// �����ʐρE�����i���������j
				if (!StringUtils.isEmpty(housingListMationForm
						.getBuildingAreaUpper())) {

					String temp = this.codeLookupManager.lookupValue(
							"area", buildingArea);
					housingListMationForm.setKeyBuildingAreaUpper(BigDecimal
							.valueOf(Double.valueOf(temp)));

				}
			}

		}

		// �y�n�ʐ�
		Iterator<String> landAreaIte = this.codeLookupManager
				.getKeysByLookup("area");

		while (landAreaIte.hasNext()) {

			String landArea = landAreaIte.next();
			if (landArea.equals(housingListMationForm.getLandAreaLower())) {
				// �����ʐρE�����i���������j
				if (!StringUtils.isEmpty(housingListMationForm
						.getLandAreaLower())) {
					String temp = this.codeLookupManager.lookupValue(
							"area", landArea);
					housingListMationForm.setKeyLandAreaLower(BigDecimal
							.valueOf(Double.valueOf(temp)));
				}
			}

			if (landArea.equals(housingListMationForm.getLandAreaUpper())) {
				// �����ʐρE�����i���������j
				if (!StringUtils.isEmpty(housingListMationForm
						.getLandAreaUpper())) {

					String temp = this.codeLookupManager.lookupValue(
							"area", landArea);
					housingListMationForm.setKeyLandAreaUpper(BigDecimal
							.valueOf(Double.valueOf(temp)));

				}
			}

		}

		// �Ԏ��
		StringBuffer layoutCd = new StringBuffer();
		if (housingListMationForm.getLayoutCd() != null) {
			for (int i = 0; i < housingListMationForm.getLayoutCd().length; i++) {
				layoutCd.append(housingListMationForm.getLayoutCd()[i] + ",");
			}
			housingListMationForm.setKeyLayoutCd(layoutCd.toString());
		}

		// �������߂̃|�C���g
		StringBuffer iconCd = new StringBuffer();
		if (housingListMationForm.getIconCd() != null) {
			for (int i = 0; i < housingListMationForm.getIconCd().length; i++) {
				iconCd.append(housingListMationForm.getIconCd()[i] + ",");
			}
			housingListMationForm.setKeyIconCd(iconCd.toString());
		}

		// ������ނ����f���ɐݒ肷��B
		model.put("housingKindCd", housingListMationForm.getHousingKindCd());

		// �s���{���}�X�^�̎擾
		String prefName = this.panaCommonManage.getPrefName(prefCd);

		// �s���{���������f���ɐݒ肷��B
		model.put("prefName", prefName);

		// �L�[���[�h�Ɛ����̒l��ݒ肷��B
		String keyWrods = "";
		String description = "";
		if(PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(housingKindCd)){
			keyWrods = "�p�i�\�j�b�N," + this.commonParameters.getPanasonicSiteEnglish() + "," + this.commonParameters.getPanasonicSiteJapan() + ",Re2,���[�E�X�N�G�A," + prefName + ",���Ã}���V����,���Ã}���V�����w��,���t�H�[��";
			description = prefName + "�̒��Ã}���V�����w���̓p�i�\�j�b�N��" + this.commonParameters.getPanasonicSiteEnglish() + "(" + this.commonParameters.getPanasonicSiteJapan() + ")�ւ����k�������B���t�H�[���v�����ƒ��Ã}���V�����Љ�������X�g�b�v�ł���Ă������܂��B������񂲊�]�ɂ��킹�����t�H�[���������k�����󂯂��܂��B";
		}

		if(PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(housingKindCd)){
			keyWrods = "�p�i�\�j�b�N," + this.commonParameters.getPanasonicSiteEnglish() + "," + this.commonParameters.getPanasonicSiteJapan() + ",Re2,���[�E�X�N�G�A," + prefName + ",���Ìˌ�,���È�ˌ���,���Ìˌ��w��,���t�H�[��";
			description = prefName + "�̒��Ìˌ��w���̓p�i�\�j�b�N��" + this.commonParameters.getPanasonicSiteEnglish() + "(" + this.commonParameters.getPanasonicSiteJapan() + ")�ւ����k�������B���t�H�[���v�����ƒ��Ìˌ��Љ�������X�g�b�v�ł���Ă������܂��B������񂲊�]�ɂ��킹�����t�H�[���������k�����󂯂��܂��B";
		}

		if(PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(housingKindCd)){
			keyWrods = "�p�i�\�j�b�N," + this.commonParameters.getPanasonicSiteEnglish() + "," + this.commonParameters.getPanasonicSiteJapan() + ",Re2,���[�E�X�N�G�A," + prefName + ",�y�n,�y�n�w��";
			description = prefName + "�̓y�n�w���̓p�i�\�j�b�N��" + this.commonParameters.getPanasonicSiteEnglish() + "(" + this.commonParameters.getPanasonicSiteJapan() + ")�ւ����k�������B�p�i�z�[���̐V�z�Z��Ƃ��킹�Ă���Ă��������܂��B";
		}

		housingListMationForm.setKeywords(keyWrods);
		housingListMationForm.setDescription(description);
		// �s���{��CD�ɂ��A�������̎擾
		int listSize = this.panaHousingManager
				.searchHousing(housingListMationForm);

		// ������{���
		List<BuildingInfo> buildingList = new ArrayList<BuildingInfo>();

		// �����ڍ׏��
		List<BuildingDtlInfo> buildingDtlList = new ArrayList<BuildingDtlInfo>();

		// ������{���
		List<HousingInfo> housingList = new ArrayList<HousingInfo>();

		// �����ڍ׏��
		List<HousingDtlInfo> housingDtlList = new ArrayList<HousingDtlInfo>();

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

		List<String> dateList = new ArrayList<String>();

		// ���y�[�W�̕\���f�[�^��ݒ肷��B
		int selectedPage = housingListMationForm.getSelectedPage();

		int pageListSize = 0;

		if (listSize - ((selectedPage - 1) * this.rowsPerPage) < this.rowsPerPage) {
			pageListSize = listSize - ((selectedPage - 1) * this.rowsPerPage);
		} else {
			pageListSize = this.rowsPerPage;
		}

		for (int count = 0; count < pageListSize; count++) {

			// �V�X�e������CD���擾����B
			String sysHousingCd = ((HousingInfo) housingListMationForm
					.getVisibleRows().get(count).getHousingInfo().getItems()
					.get("housingInfo")).getSysHousingCd();

			// �V�X�e������CD���擾����B
			String sysBuildingCd = ((BuildingInfo) housingListMationForm
					.getVisibleRows().get(count).getBuilding()
					.getBuildingInfo().getItems().get("buildingInfo"))
					.getSysBuildingCd();

			// �ŏI�X�V�����擾����B
			Date updDate = ((HousingInfo) housingListMationForm
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
			BuildingInfo buildingInfo = (BuildingInfo) housingListMationForm
					.getVisibleRows().get(count).getBuilding()
					.getBuildingInfo().getItems().get("buildingInfo");

			// ������{���
			HousingInfo housingInfo = (HousingInfo) housingListMationForm
					.getVisibleRows().get(count).getHousingInfo().getItems()
					.get("housingInfo");

			// �����ڍ׏��
			BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) housingListMationForm
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

			// �����ڍ׏��
			HousingDtlInfo housingDtlInfo = (HousingDtlInfo) housingListMationForm
					.getVisibleRows().get(count).getHousingInfo().getItems()
					.get("housingDtlInfo");

			if(!StringUtils.isEmpty(housingInfo.getReformComment())){
				housingInfo.setReformComment(PanaStringUtils.encodeHtml(housingInfo.getReformComment().trim()));
			}

			if(!StringUtils.isEmpty(housingInfo.getBasicComment())){
				housingInfo.setBasicComment(PanaStringUtils.encodeHtml(housingInfo.getBasicComment().trim()));
			}

			if(!StringUtils.isEmpty(housingDtlInfo.getDtlComment())){
				housingDtlInfo.setDtlComment(PanaStringUtils.encodeHtml(housingDtlInfo.getDtlComment().trim()));
			}

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
			List<ReformPlan> searchReformPlan = this.reformManager
					.searchReformPlan(sysHousingCd);
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

		// �����ڍ׏��
		model.put("buildingDtlList", buildingDtlList);

		// ������{���
		model.put("housingList", housingList);

		// �����ڍ׏��
		model.put("housingDtlList", housingDtlList);

		// ������񌏐�
		model.put("housingDtlListSize", listSize);

		model.put("housingListMationForm", housingListMationForm);

		// �����������}�X�^�̓Ǎ��A���ׂẴf�[�^���擾����B
		List<JoinResult> searchPartSrchMstList = this.panaHousingManager
				.searchPartSrchMst(housingListMationForm.getHousingKindCd());
		List<PartSrchMst> searchPartSrchMst = new ArrayList<PartSrchMst>();
		for (int i = 0; i < searchPartSrchMstList.size(); i++) {
			PartSrchMst searchPartSrchMstInfo = (PartSrchMst) searchPartSrchMstList
					.get(i).getItems().get("partSrchMst");
			searchPartSrchMst.add(searchPartSrchMstInfo);
		}
		model.put("searchPartSrchMst", searchPartSrchMst);

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
