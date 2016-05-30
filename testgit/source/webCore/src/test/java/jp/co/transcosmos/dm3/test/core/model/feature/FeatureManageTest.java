package jp.co.transcosmos.dm3.test.core.model.feature;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.FeatureManage;
import jp.co.transcosmos.dm3.core.model.feature.form.FeatureFormFactory;
import jp.co.transcosmos.dm3.core.model.feature.form.FeatureSearchForm;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.FeatureGroupInfo;
import jp.co.transcosmos.dm3.core.vo.FeatureGroupPage;
import jp.co.transcosmos.dm3.core.vo.FeaturePageInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingPartInfo;
import jp.co.transcosmos.dm3.core.vo.HousingStatusInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 特集情報 model のテストケース
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
public class FeatureManageTest {

	@Autowired
	private FeatureManage featureManage;
	@Autowired
	private FeatureFormFactory featureFormFactory;

	@Autowired
	private DAO<FeatureGroupInfo> featureGroupInfoDAO;
	@Autowired
	private DAO<FeaturePageInfo> featurePageInfoDAO;
	@Autowired
	private DAO<FeatureGroupPage> featureGroupPageDAO;

	@Autowired
	private DAO<HousingInfo> housingInfoDAO;
	@Autowired
	private DAO<HousingPartInfo> housingPartInfoDAO;
	@Autowired
	private DAO<HousingStatusInfo> housingStatusInfoDAO;
	@Autowired
	private DAO<BuildingInfo> buildingInfoDAO;
	@Autowired
	private DAO<BuildingDtlInfo> buildingDtlInfoDAO;
	@Autowired
	private DAO<BuildingStationInfo> buildingStationInfoDAO;

	// 前処理
	@Before
	public void init() {
		// 関連するデータの全件削除
		DAOCriteria criteria = new DAOCriteria();
		this.featureGroupInfoDAO.deleteByFilter(criteria);
		this.featurePageInfoDAO.deleteByFilter(criteria);
		this.featureGroupPageDAO.deleteByFilter(criteria);

		this.housingStatusInfoDAO.deleteByFilter(criteria);
		this.housingPartInfoDAO.deleteByFilter(criteria);
		this.housingInfoDAO.deleteByFilter(criteria);
		this.buildingStationInfoDAO.deleteByFilter(criteria);
		this.buildingDtlInfoDAO.deleteByFilter(criteria);
		this.buildingInfoDAO.deleteByFilter(criteria);

		initTestData();
	}

	private void initTestData() {

		FeatureGroupInfo featureGroupInfo = new FeatureGroupInfo();
		FeaturePageInfo featurePageInfo = new FeaturePageInfo();
		FeatureGroupPage featureGroupPage = new FeatureGroupPage();

		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		calendar.clear();

		// ------ 登録データ------

		// 【FGI00001】
		featureGroupInfo.setFeatureGroupId("FGI00001");
		featureGroupInfo.setFeatureGroupName("特集グループ名０１");
		featureGroupInfoDAO.insert(new FeatureGroupInfo[] { featureGroupInfo });

		featurePageInfo.setFeaturePageId("FPI00001");
		featurePageInfo.setFeaturePageName("特集ページ名称０１");
		featurePageInfo.setFeatureComment("特集コメント０１");
		featurePageInfo.setQueryStrings("prefCd=13&addressCd=13101");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfo.setPathName("ImagePath");
		featurePageInfo.setHwFlg("2");
		calendar.setTime(now);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		featurePageInfo.setDisplayStartDate(calendar.getTime());
		calendar.setTime(now);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		featurePageInfo.setDisplayEndDate(calendar.getTime());
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		featureGroupPage.setFeatureGroupId("FGI00001");
		featureGroupPage.setFeaturePageId("FPI00001");
		featureGroupPageDAO.insert(new FeatureGroupPage[] { featureGroupPage });

		// 【FGI00002】
		featureGroupInfo.setFeatureGroupId("FGI00002");
		featureGroupInfo.setFeatureGroupName("特集グループ名０２");
		featureGroupInfoDAO.insert(new FeatureGroupInfo[] { featureGroupInfo });

		featurePageInfo.setFeaturePageId("FPI00021");
		featurePageInfo.setFeaturePageName("特集ページ名称２１");
		featurePageInfo.setQueryStrings("prefCd=13&addressCd=13101");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		featurePageInfo.setFeaturePageId("FPI00022");
		featurePageInfo.setFeaturePageName("特集ページ名称２２");
		featurePageInfo.setQueryStrings("prefCd=13&addressCd=13101");
		featurePageInfo.setDisplayFlg("0");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		featurePageInfo.setFeaturePageId("FPI00023");
		featurePageInfo.setFeaturePageName("特集ページ名称２３");
		featurePageInfo.setQueryStrings("prefCd=13&addressCd=13101");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		featureGroupPage.setFeatureGroupId("FGI00002");
		featureGroupPage.setFeaturePageId("FPI00023");
		featureGroupPage.setSortOrder(1);
		featureGroupPageDAO.insert(new FeatureGroupPage[] { featureGroupPage });

		featureGroupPage.setFeatureGroupId("FGI00002");
		featureGroupPage.setFeaturePageId("FPI00022");
		featureGroupPage.setSortOrder(2);
		featureGroupPageDAO.insert(new FeatureGroupPage[] { featureGroupPage });

		featureGroupPage.setFeatureGroupId("FGI00002");
		featureGroupPage.setFeaturePageId("FPI00021");
		featureGroupPage.setSortOrder(3);
		featureGroupPageDAO.insert(new FeatureGroupPage[] { featureGroupPage });

		// 物件検索結果対象となる物件情報関連
		BuildingInfo buildingInfo;
		BuildingDtlInfo buildingDtlInfo;
		HousingInfo housingInfo;
		HousingPartInfo housingPartInfo;
		HousingStatusInfo housingStatusInfo;
		BuildingStationInfo buildingStationInfo;

		// 建物基本情報
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00001");
		buildingInfo.setBuildingCd("BLD00001");
		buildingInfo.setHousingKindCd("001");
		buildingInfo.setDisplayBuildingName("建物名０１");
		buildingInfo.setPrefCd("01");
		buildingInfo.setAddressCd("01101");
		this.buildingInfoDAO.insert(new BuildingInfo[] { buildingInfo });

		// 建物詳細情報
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("SBLD00001");
		buildingDtlInfo.setBuildingArea(new BigDecimal("50"));
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] { buildingDtlInfo });

		// 物件基本情報
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00001");
		housingInfo.setHousingCd("HOU00001");
		housingInfo.setDisplayHousingName("物件名０１");
		housingInfo.setSysBuildingCd("SBLD00001");
		housingInfo.setPrice(100000L);
		housingInfo.setLayoutCd("003");
		housingInfo.setLandArea(new BigDecimal("50"));
		housingInfo.setPersonalArea(new BigDecimal("60"));
		housingInfo.setMoveinFlg("0");
		this.housingInfoDAO.insert(new HousingInfo[] { housingInfo });

		// 物件こだわり条件情報
		housingPartInfo = new HousingPartInfo();
		housingPartInfo.setSysHousingCd("SHOU00001");
		housingPartInfo.setPartSrchCd("REI");
		this.housingPartInfoDAO.insert(new HousingPartInfo[] { housingPartInfo });

		// 物件ステータス情報
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SHOU00001");
		housingStatusInfo.setHiddenFlg("0");
		this.housingStatusInfoDAO.insert(new HousingStatusInfo[] { housingStatusInfo });

		// 建物最寄駅情報
		buildingStationInfo = new BuildingStationInfo();
		buildingStationInfo.setSysBuildingCd("SBLD00001");
		buildingStationInfo.setDivNo(001);
		buildingStationInfo.setDefaultRouteCd("J002003");
		buildingStationInfo.setStationCd("21137");
		this.buildingStationInfoDAO.insert(new BuildingStationInfo[] { buildingStationInfo });

		// テスト用の検索データに該当しないはずの情報

		// 建物基本情報
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00002");
		buildingInfo.setBuildingCd("BLD00002");
		buildingInfo.setHousingKindCd("000");
		buildingInfo.setDisplayBuildingName("０");
		buildingInfo.setPrefCd("13");
		buildingInfo.setAddressCd("13101");
		this.buildingInfoDAO.insert(new BuildingInfo[] { buildingInfo });

		// 建物詳細情報
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("SBLD00002");
		buildingDtlInfo.setBuildingArea(new BigDecimal("10"));
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] { buildingDtlInfo });

		// 物件基本情報
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00002");
		housingInfo.setHousingCd("HOU00002");
		housingInfo.setDisplayHousingName("０");
		housingInfo.setSysBuildingCd("SBLD00002");
		housingInfo.setPrice(100L);
		housingInfo.setLayoutCd("001");
		housingInfo.setLandArea(new BigDecimal("10"));
		housingInfo.setPersonalArea(new BigDecimal("10"));
		housingInfo.setMoveinFlg("0");
		housingInfo.setIconCd("S05");
		this.housingInfoDAO.insert(new HousingInfo[] { housingInfo });

		// 物件こだわり条件情報
		housingPartInfo = new HousingPartInfo();
		housingPartInfo.setSysHousingCd("SHOU00002");
		housingPartInfo.setPartSrchCd("S10");
		this.housingPartInfoDAO.insert(new HousingPartInfo[] { housingPartInfo });

		// 物件ステータス情報
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SHOU00002");
		housingStatusInfo.setHiddenFlg("0");
		this.housingStatusInfoDAO.insert(new HousingStatusInfo[] { housingStatusInfo });

		// 建物最寄駅情報
		buildingStationInfo = new BuildingStationInfo();
		buildingStationInfo.setSysBuildingCd("SBLD00002");
		buildingStationInfo.setDivNo(001);
		buildingStationInfo.setDefaultRouteCd("J002083");
		buildingStationInfo.setStationCd("22895");
		this.buildingStationInfoDAO.insert(new BuildingStationInfo[] { buildingStationInfo });

		// 【FPI003XX】

		// 検索条件に利用されないパラメタ
		featurePageInfo.setFeaturePageId("FPI00300");
		featurePageInfo.setFeaturePageName("特集ページ名称３００");
		featurePageInfo.setQueryStrings("test=HOU00001");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		// 物件番号
		featurePageInfo.setFeaturePageId("FPI00301");
		featurePageInfo.setFeaturePageName("特集ページ名称３０１");
		featurePageInfo.setQueryStrings("housingCd=HOU00001");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		// 物件名称
		featurePageInfo.setFeaturePageId("FPI00302");
		featurePageInfo.setFeaturePageName("特集ページ名称３０２");
		featurePageInfo.setQueryStrings("displayHousingName=物件名");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		// 都道府県CD
		featurePageInfo.setFeaturePageId("FPI00303");
		featurePageInfo.setFeaturePageName("特集ページ名称３０３");
		featurePageInfo.setQueryStrings("prefCd=01");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		// 市区町村CD
		featurePageInfo.setFeaturePageId("FPI00304");
		featurePageInfo.setFeaturePageName("特集ページ名称３０４");
		featurePageInfo.setQueryStrings("addressCd=01101");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		// 路線CD
		featurePageInfo.setFeaturePageId("FPI00305");
		featurePageInfo.setFeaturePageName("特集ページ名称３０５");
		featurePageInfo.setQueryStrings("routeCd=J002003");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		// 駅CD
		featurePageInfo.setFeaturePageId("FPI00306");
		featurePageInfo.setFeaturePageName("特集ページ名称３０６");
		featurePageInfo.setQueryStrings("routeCd=J002003&stationCd=21137");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		// 土地面積・下限、上限
		featurePageInfo.setFeaturePageId("FPI00307");
		featurePageInfo.setFeaturePageName("特集ページ名称３０７");
		featurePageInfo.setQueryStrings("landAreaLower=50&landAreaUpper=60");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

//		// 土地面積・上限
//		featurePageInfo.setFeaturePageId("FPI00308");
//		featurePageInfo.setFeaturePageName("特集ページ名称３０８");
//		featurePageInfo.setQueryStrings("landAreaUpper=50");
//		featurePageInfo.setDisplayFlg("1");
//		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		// 専有面積・下限、上限
		featurePageInfo.setFeaturePageId("FPI00309");
		featurePageInfo.setFeaturePageName("特集ページ名称３０９");
		featurePageInfo.setQueryStrings("personalAreaLower=60&personalAreaUpper=60");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

//		// 専有面積・上限
//		featurePageInfo.setFeaturePageId("FPI00310");
//		featurePageInfo.setFeaturePageName("特集ページ名称３１０");
//		featurePageInfo.setQueryStrings("personalAreaUpper=60");
//		featurePageInfo.setDisplayFlg("1");
//		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		// 物件種類CD
		featurePageInfo.setFeaturePageId("FPI00311");
		featurePageInfo.setFeaturePageName("特集ページ名称３１１");
		featurePageInfo.setQueryStrings("housingKindCd=001");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		// 間取CD
		featurePageInfo.setFeaturePageId("FPI00312");
		featurePageInfo.setFeaturePageName("特集ページ名称３１２");
		featurePageInfo.setQueryStrings("layoutCd=003");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		// 専有面積・下限、上限
		featurePageInfo.setFeaturePageId("FPI00313");
		featurePageInfo.setFeaturePageName("特集ページ名称３１３");
		featurePageInfo.setQueryStrings("buildingAreaLower=50&buildingAreaUpper=60");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

//		// 専有面積・上限
//		featurePageInfo.setFeaturePageId("FPI00314");
//		featurePageInfo.setFeaturePageName("特集ページ名称３１４");
//		featurePageInfo.setQueryStrings("buildingAreaUpper=50");
//		featurePageInfo.setDisplayFlg("1");
//		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		// こだわり条件CD
		featurePageInfo.setFeaturePageId("FPI00315");
		featurePageInfo.setFeaturePageName("特集ページ名称３１５");
		featurePageInfo.setQueryStrings("partSrchCd=REI");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		// ソート条件
		featurePageInfo.setFeaturePageId("FPI00316");
		featurePageInfo.setFeaturePageName("特集ページ名称３１６");
		featurePageInfo.setQueryStrings("orderType=1");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

	}

	/**
	 * 特集ページ情報の検索処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>パラメタが正しく設定されていない場合、特集情報は取得せず、正常終了する事</li>
	 * </ul>
	 */
	@Test
	public void searchFeatureParamCheckTest() throws Exception {

		List<FeaturePageInfo> featurePageInfos;

		featurePageInfos = featureManage.searchFeature(null);

		Assert.assertEquals("パラメタが正しく設定されていない場合、特集情報は取得せずに、正常終了する事", 0, featurePageInfos.size());

		featurePageInfos = featureManage.searchFeature("");

		Assert.assertEquals("パラメタが正しく設定されていない場合、特集情報は取得せずに、正常終了する事", 0, featurePageInfos.size());

	}

	/**
	 * 特集ページ情報の検索処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>特集情報が正しく取得されている事</li>
	 *     <li>特集情報が正しく取得されている事（表示フラグ）</li>
	 *     <li>特集情報が正しく取得されている事（表示順）</li>
	 * </ul>
	 */
	@Test
	public void searchFeatureTest() throws Exception {

		Date now = new Date();
		List<FeaturePageInfo> featurePageInfos;
		FeaturePageInfo featurePageInfo;

		// 特集情報が正しく取得されている事
		featurePageInfos = featureManage.searchFeature("FGI00001");

		Assert.assertEquals("特集情報が正しく件数取得されている事", 1, featurePageInfos.size());

		featurePageInfo = featurePageInfos.get(0);
		Assert.assertEquals("特集ページ情報の特集ページIDが正しく取得されている事", "FPI00001", featurePageInfo.getFeaturePageId());
		Assert.assertEquals("特集ページ情報の特集ページ名が正しく取得されている事", "特集ページ名称０１", featurePageInfo.getFeaturePageName());
		Assert.assertEquals("特集ページ情報のコメントが正しく取得されている事", "特集コメント０１", featurePageInfo.getFeatureComment());
		Assert.assertEquals("特集ページ情報のクエリー文字列が正しく取得されている事", "prefCd=13&addressCd=13101", featurePageInfo.getQueryStrings());
		Assert.assertEquals("特集ページ情報の表示フラグが正しく取得されている事", "1", featurePageInfo.getDisplayFlg());
		Assert.assertEquals("特集ページ情報の画像パス名が正しく取得されている事", "ImagePath", featurePageInfo.getPathName());
		Assert.assertEquals("特集ページ情報の縦長・横長フラグが正しく取得されている事", "2", featurePageInfo.getHwFlg());
		Assert.assertTrue("特集ページ情報の表示開始日が正しく取得されている事", now.compareTo(featurePageInfo.getDisplayStartDate()) >= 0);
		Assert.assertTrue("特集ページ情報の表示終了日が正しく取得されている事", now.compareTo(featurePageInfo.getDisplayEndDate()) <= 0);

		// 特集情報が正しく取得されている事（表示フラグ）
		// 特集情報が正しく取得されている事（表示順）
		featurePageInfos = featureManage.searchFeature("FGI00002");

		Assert.assertEquals("特集情報が正しく件数取得されている事", 2, featurePageInfos.size());
		featurePageInfo = featurePageInfos.get(0);
		Assert.assertEquals("特集ページ情報の特集ページIDが正しく取得されている事", "FPI00023", featurePageInfo.getFeaturePageId());
		featurePageInfo = featurePageInfos.get(1);
		Assert.assertEquals("特集ページ情報の特集ページIDが正しく取得されている事", "FPI00021", featurePageInfo.getFeaturePageId());

	}

	/**
	 * 特集ページ情報による物件検索処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>パラメタが正しく設定されていない場合、物件情報は取得せず、正常終了する事</li>
	 * </ul>
	 */
	@Test
	public void searchHousingParamCheckTest() throws Exception {

		int cnt = 1;
		FeatureSearchForm searchForm;

		// searchForm が null
		cnt = featureManage.searchHousing(null);

		// featurePageId が null
		Assert.assertEquals("パラメタが正しく設定されていない場合、物件情報は取得せずに、正常終了する事", 0, cnt);

		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId(null);
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("パラメタが正しく設定されていない場合、物件情報は取得せずに、正常終了する事", 0, cnt);

		// featurePageId が 空
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId("");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("パラメタが正しく設定されていない場合、物件情報は取得せずに、正常終了する事", 0, cnt);

	}

	/**
	 * 特集ページ情報による物件検索処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>物件情報が正しく検索される事</li>
	 * </ul>
	 */
	@Test
	public void searchHousingTest() throws Exception {

		int cnt = 0;
		FeatureSearchForm searchForm;

		// 物件情報が正しく検索される事（検索処理に利用されないパラメタ）
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId("FPI00300");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("物件情報が正しく取得される事", 2, cnt);

		// 物件情報が正しく検索される事（物件番号）
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId("FPI00301");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("物件情報が正しく取得される事", 1, cnt);

		// 物件情報が正しく検索される事（物件名称）
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId("FPI00302");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("物件情報が正しく取得される事", 1, cnt);

		// 物件情報が正しく検索される事（都道府県CD）
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId("FPI00303");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("物件情報が正しく取得される事", 1, cnt);

		// 物件情報が正しく検索される事（市区町村CD）
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId("FPI00304");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("物件情報が正しく取得される事", 1, cnt);

		// 物件情報が正しく検索される事（路線CD）
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId("FPI00305");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("物件情報が正しく取得される事", 1, cnt);

		// 物件情報が正しく検索される事（駅CD）
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId("FPI00306");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("物件情報が正しく取得される事", 1, cnt);

		// 物件情報が正しく検索される事（土地面積・下限、上限）
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId("FPI00307");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("物件情報が正しく取得される事", 1, cnt);

		// 物件情報が正しく検索される事（専有面積・下限、上限）
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId("FPI00309");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("物件情報が正しく取得される事", 1, cnt);

		// 物件情報が正しく検索される事（物件種類CD）
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId("FPI00311");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("物件情報が正しく取得される事", 1, cnt);

		// 物件情報が正しく検索される事（間取CD）
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId("FPI00312");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("物件情報が正しく取得される事", 1, cnt);

		// 物件情報が正しく検索される事（専有面積・下限、上限）
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId("FPI00313");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("物件情報が正しく取得される事", 1, cnt);

		// 物件情報が正しく検索される事（こだわり条件CD）
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId("FPI00315");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("物件情報が正しく取得される事", 1, cnt);

		// 物件情報が正しく検索される事（ソート条件）
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId("FPI00316");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("物件情報が正しく取得される事", 2, cnt);
		HousingInfo housingInfo;
		housingInfo = (HousingInfo) searchForm.getRows().get(0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件情報が正しく取得される事", "SHOU00002", housingInfo.getSysHousingCd());
		housingInfo = (HousingInfo) searchForm.getRows().get(1).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件情報が正しく取得される事", "SHOU00001", housingInfo.getSysHousingCd());

	}

	/**
	 * 特集ページ情報による物件検索処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>ページング関連のプロパティ値に合致した物件情報が正しく取得される事</li>
	 * </ul>
	 */
	@Test
	public void searchHousingPagingTest() throws Exception {

		int cnt = 0;
		int actualRow =0;
		FeatureSearchForm searchForm;

		HousingInfo housingInfo;

		// 1ページ目
		// FeatureSearchForm に設定されたページング関連のプロパティ値に合致した物件情報が取得できていること
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setRowsPerPage(1);
		searchForm.setSelectedPage(1);
		searchForm.setFeaturePageId("FPI00316");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("物件情報が正しく取得される事", 2, cnt);
		// 実態のサイズを取得する
		actualRow = 0;
		for (Housing housing : searchForm.getRows()) {
			actualRow++;
		}
		int pageRowIndex = searchForm.getRowsPerPage() * searchForm.getSelectedPage() - searchForm.getRowsPerPage();
		Assert.assertEquals("ページング関連のプロパティ値に合致した物件情報が正しく取得される事", 1, actualRow);
		housingInfo = (HousingInfo) searchForm.getRows().get(pageRowIndex + 0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("ページング関連のプロパティ値に合致した物件情報が正しく取得される事", "SHOU00002", housingInfo.getSysHousingCd());

		// 2ページ目
		// FeatureSearchForm に設定されたページング関連のプロパティ値に合致した物件情報が取得できていること
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setRowsPerPage(1);
		searchForm.setSelectedPage(2);
		searchForm.setFeaturePageId("FPI00316");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("物件情報が正しく取得される事", 2, cnt);
		// 実態のサイズを取得する
		actualRow = 0;
		for (Housing housing : searchForm.getRows()) {
			actualRow++;
		}
		pageRowIndex = searchForm.getRowsPerPage() * searchForm.getSelectedPage() - searchForm.getRowsPerPage();
		Assert.assertEquals("ページング関連のプロパティ値に合致した物件情報が正しく取得される事", 1, actualRow);
		housingInfo = (HousingInfo) searchForm.getRows().get(pageRowIndex + 0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("ページング関連のプロパティ値に合致した物件情報が正しく取得される事", "SHOU00001", housingInfo.getSysHousingCd());

	}

}
