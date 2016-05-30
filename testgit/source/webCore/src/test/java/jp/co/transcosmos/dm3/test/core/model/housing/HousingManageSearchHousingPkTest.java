package jp.co.transcosmos.dm3.test.core.model.housing;

import java.util.Map;
import java.util.Map.Entry;

import jp.co.transcosmos.dm3.core.model.HousingManage;
import jp.co.transcosmos.dm3.core.model.building.Building;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingLandmark;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.EquipMst;
import jp.co.transcosmos.dm3.core.vo.HousingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.HousingEquipInfo;
import jp.co.transcosmos.dm3.core.vo.HousingExtInfo;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingPartInfo;
import jp.co.transcosmos.dm3.core.vo.HousingStatusInfo;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * 物件情報 model 主キーによる物件情報取得処理のテスト
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class HousingManageSearchHousingPkTest {

	@Autowired
	private DAO<BuildingInfo> buildingInfoDAO;
	@Autowired
	private DAO<BuildingDtlInfo> buildingDtlInfoDAO;
	@Autowired
	private DAO<BuildingStationInfo> buildingStationInfoDAO;
	@Autowired
	private DAO<BuildingLandmark> buildingLandmarkDAO;

	@Autowired
	private DAO<HousingInfo> housingInfoDAO;
	@Autowired
	private DAO<HousingDtlInfo> housingDtlInfoDAO;
	@Autowired
	private DAO<HousingStatusInfo> housingStatusInfoDAO;
	@Autowired
	private DAO<HousingImageInfo> housingImageInfoDAO;
	@Autowired
	private DAO<HousingEquipInfo> housingEquipInfoDAO;
	@Autowired
	private DAO<HousingExtInfo> housingExtInfoDAO;
	@Autowired
	private DAO<HousingPartInfo> housingPartInfoDAO;

	@Qualifier("housingManager")
	@Autowired
	private HousingManage housingManage;

	
	
	// 前処理
	@Before
	public void init(){
		// 物件情報全件削除
		DAOCriteria criteria = new DAOCriteria();
		this.housingInfoDAO.deleteByFilter(criteria);
		this.housingStatusInfoDAO.deleteByFilter(criteria);
		this.buildingInfoDAO.deleteByFilter(criteria);

		initTestData();
	}



	/**
	 * 物件情報の取得
	 * ・建物基本情報、物件基本情報のみ
	 * ・引数 full = true （非公開情報も取得）
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>最低限の構成データ（建物基本情報と、物件基本情報のみ）でも正しく値が取得できる事</li>
	 * </ul>
	 */
	@Test
	public void getPkMinDataTest() throws Exception {

		// 最小構成のテストなので、建物基本情報、物件基本情報以外を削除する。
		DAOCriteria criteria = new DAOCriteria();
		this.buildingDtlInfoDAO.deleteByFilter(criteria);
		this.buildingStationInfoDAO.deleteByFilter(criteria);
		this.buildingLandmarkDAO.deleteByFilter(criteria);

		this.housingDtlInfoDAO.deleteByFilter(criteria);
		this.housingStatusInfoDAO.deleteByFilter(criteria);
		this.housingImageInfoDAO.deleteByFilter(criteria);
		this.housingEquipInfoDAO.deleteByFilter(criteria);
		this.housingExtInfoDAO.deleteByFilter(criteria);


		// テストメソッドを実行
		Housing housing = this.housingManage.searchHousingPk("HOU00001", true);
		Assert.assertNotNull("対象データが取得できている事", housing);

		// 取得データの確認
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("建物基本情報のシステム建物CDが正しい事", buildingInfo.getSysBuildingCd(), "BULD00001");

		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo)building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertNull("建物詳細情報のシステム建物CD が null である事", buildingDtlInfo.getSysBuildingCd());

		Assert.assertEquals("建物ランドマーク情報の件数が 0 である事", 0, building.getBuildingLandmarkList().size());
		Assert.assertEquals("建物最寄り駅情報の件数が 0 である事", 0, building.getBuildingStationInfoList().size());


		HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件基本情報のシステム物件CDが正しい事", housingInfo.getSysHousingCd(), "HOU00001");

		HousingDtlInfo housingDtlInfo = (HousingDtlInfo)housing.getHousingInfo().getItems().get("housingDtlInfo");
		Assert.assertNull("物件詳細情報のシステム物件CD が null である事", housingDtlInfo.getSysHousingCd());

		HousingStatusInfo housingStatuslInfo = (HousingStatusInfo) housing.getHousingInfo().getItems().get("housingStatusInfo");
		Assert.assertNull("物件ステータス情報のシステム物件CD が null ある事", housingStatuslInfo.getSysHousingCd());

		Assert.assertEquals("物件画像情報の件数が 0 である事", 0, housing.getHousingImageInfos().size());
		Assert.assertEquals("物件設備情報の件数が 0 である事", 0, housing.getHousingEquipInfos().size());
		Assert.assertEquals("物件拡張属性情報の件数が 0 である事", 0, housing.getHousingExtInfos().size());

	}


	
	/**
	 * 物件情報の取得<br/>
	 * ・全ての依存テーブル
	 * ・引数 full = true （非公開情報も取得）
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>指定したシステム部件CD に該当する建物基本情報が取得できている事</li>
	 *     <li>指定したシステム部件CD に該当する建物詳細情報が取得できている事</li>
	 *     <li>指定したシステム部件CD に該当する物件基本情報が取得できている事</li>
	 *     <li>指定したシステム部件CD に該当する物件詳細情報が取得できている事</li>
	 *     <li>指定したシステム部件CD に該当する物件ステータス情報が取得できている事</li>
	 * </ul>
	 */
	@Test
	public void confMainDataFullTest() throws Exception {

		// テストメソッドを実行
		Housing housing = this.housingManage.searchHousingPk("HOU00001", true);
		Assert.assertNotNull("対象データが取得できている事", housing);

		
		// 取得データの確認
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("建物基本情報のシステム建物CDが正しい事", buildingInfo.getSysBuildingCd(), "BULD00001");

		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo)building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("建物詳細情報のシステム建物CDが正しい事", buildingDtlInfo.getSysBuildingCd(), "BULD00001");

		Assert.assertEquals("建物ランドマーク情報の件数が正しい事", 2, building.getBuildingLandmarkList().size());
		for (BuildingLandmark landmark : building.getBuildingLandmarkList()){
		
			Assert.assertEquals("建物ランドマーク情報のシステム建物CD が正しい事", "BULD00001", landmark.getSysBuildingCd());
			
			// 取得データが正しい事をランドマーク名でチェック
			if (landmark.getLandmarkName().equals("LANDNM0001_1")) continue;
			if (landmark.getLandmarkName().equals("LANDNM0001_2")) continue;
			Assert.fail("ランドマーク名が正しくない　" + landmark.getLandmarkName());
		}

		Assert.assertEquals("建物最寄り駅情報の件数が正しい事", 2, building.getBuildingStationInfoList().size());
		for (JoinResult result : building.getBuildingStationInfoList()){
			BuildingStationInfo buildingStationInfo = (BuildingStationInfo) result.getItems().get("buildingStationInfo");
			StationMst stationMst = (StationMst) result.getItems().get("stationMst");

			Assert.assertEquals("建物最寄り駅情報のシステム建物CD が正しい事", "BULD00001", buildingStationInfo.getSysBuildingCd());

			// 取得データが正しい事を駅CD、駅名でチェック
			if (buildingStationInfo.getStationCd().equals("29726") && stationMst.getStationName().equals("奇跡の一本松")) continue;
			if (buildingStationInfo.getStationCd().equals("29695") && stationMst.getStationName().equals("泰澄の里")) continue;

			Assert.fail("最寄り駅情報が正しくない　" + buildingStationInfo.getStationCd() + "," + stationMst.getStationName());
		}


		HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件基本情報のシステム物件CDが正しい事", housingInfo.getSysHousingCd(), "HOU00001");

		HousingDtlInfo housingDtlInfo = (HousingDtlInfo) housing.getHousingInfo().getItems().get("housingDtlInfo");
		Assert.assertEquals("物件詳細情報のシステム物件CDが正しい事", housingDtlInfo.getSysHousingCd(), "HOU00001");

		HousingStatusInfo housingStatusInfo = (HousingStatusInfo) housing.getHousingInfo().getItems().get("housingStatusInfo");
		Assert.assertEquals("物件ステータス情報のシステム物件CDが正しい事", housingStatusInfo.getSysHousingCd(), "HOU00001");

		Assert.assertEquals("物件画像情報の件数が正しい事", 2, housing.getHousingImageInfos().size());
		for (HousingImageInfo img : housing.getHousingImageInfos()){
			
			Assert.assertEquals("物件画像情報のシステム物件CD が正しい事", "HOU00001", img.getSysHousingCd());
			
			// 取得データが正しい事を画像パスでチェック
			if (img.getPathName().equals("PATH00001_01")) continue;
			if (img.getPathName().equals("PATH00001_02")) continue;
			
			Assert.fail("物件画像情報が正しくない　" + img.getPathName());
		}

		Assert.assertEquals("物件設備情報の件数が正しい事", 2, housing.getHousingEquipInfos().size());
		EquipMst equip = housing.getHousingEquipInfos().get("000001");
		Assert.assertEquals("取得した物件設備の設備名が正しい事", "２４Ｈ換気システム", equip.getEquipName());
		equip = housing.getHousingEquipInfos().get("000002");
		Assert.assertEquals("取得した物件設備の設備名が正しい事", "玄関鍵（デジタルロック）", equip.getEquipName());

		// 物件拡張属性の確認
		Map<String, Map<String, String>> categoryMaps = housing.getHousingExtInfos();
		for (Entry<String, Map<String, String>> e : categoryMaps.entrySet()){
			String categoryName = e.getKey();
			Map<String, String> keyMap = e.getValue();

			if (categoryName.equals("CATE0001")) {
				if (keyMap.size() != 2) {
					Assert.fail("取得したカテゴリに該当する key の数が正しくない " + categoryName + "," + keyMap.size());
				}

				Assert.assertEquals("key に該当する値が正しい事", "VALUE00001", keyMap.get("KEY0001"));
				Assert.assertEquals("key に該当する値が正しい事", "VALUE00002", keyMap.get("KEY0002"));

			} else if (categoryName.equals("CATE0002")) {	
				if (keyMap.size() != 1) {
					Assert.fail("取得したカテゴリに該当する key の数が正しくない " + categoryName + "," + keyMap.size());
				}
				Assert.assertEquals("key に該当する値が正しい事", "VALUE00003", keyMap.get("KEY0001"));

			} else {
				Assert.fail("取得したカテゴリ名が正しくない " + categoryName);
			}
		}
	}



	/**
	 * 引数 full による非公開部件の取得制御テスト<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>full = false で物件ステータス情報が存在しない場合、取得される事</li>
	 *     <li>full = true で物件ステータス情報が存在しない場合、取得される事</li>
	 *     <li>full = false で物件ステータス情報が存在し、非公開フラグが 0 の場合、取得される事</li>
	 *     <li>full = true で物件ステータス情報が存在し、非公開フラグが 0 の場合、取得される事</li>
	 *     <li>full = false で物件ステータス情報が存在し、非公開フラグが 1 の場合、取得されない事</li>
	 *     <li>full = true で物件ステータス情報が存在し、非公開フラグが 1 の場合、取得される事</li>
	 * </ul>
	 */
	@Test
	public void getPkStatusTest() throws Exception {
		

		// full = false でステータス情報が無い場合、取得される事
		Housing housing = this.housingManage.searchHousingPk("HOU00004", false);
		HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");
		String sysHousingCd = housingInfo.getSysHousingCd();
		Assert.assertEquals("full = false で物件ステータス情報が無い場合、取得出来ている事。", sysHousingCd, "HOU00004");

		
		// full = true でステータス情報が無い場合、取得される事
		housing = this.housingManage.searchHousingPk("HOU00004", true);
		housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");
		sysHousingCd = housingInfo.getSysHousingCd();
		Assert.assertEquals("full = true で物件ステータス情報が無い場合、取得出来ている事。", sysHousingCd, "HOU00004");


		// full = false でステータス情報が公開の場合、取得される事
		housing = this.housingManage.searchHousingPk("HOU00001", false);
		housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");
		sysHousingCd = housingInfo.getSysHousingCd();
		Assert.assertEquals("物件ステータス情報が公開の場合、取得出来ている事。", sysHousingCd, "HOU00001");

		
		// full = true でステータス情報が公開の場合、取得される事
		housing = this.housingManage.searchHousingPk("HOU00001", true);
		housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");
		sysHousingCd = housingInfo.getSysHousingCd();
		Assert.assertEquals("物件ステータス情報が公開の場合、取得出来ている事。", sysHousingCd, "HOU00001");


		// full = false ステータス情報が非公開の場合、取得されない事
		housing = this.housingManage.searchHousingPk("HOU00005", false);
		Assert.assertNull("物件ステータス情報が非公開の場合、取得対象外である事。", housing);

		
		// full = true でステータス情報が非公開の場合、取得される事
		housing = this.housingManage.searchHousingPk("HOU00005", true);
		housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");
		sysHousingCd = housingInfo.getSysHousingCd();
		Assert.assertEquals("物件ステータス情報が公開の場合、取得出来ている事。", sysHousingCd, "HOU00005");

	}

	


	
	/**
	 * 該当するデータが存在しない場合
	 * ・引数 full = true （非公開情報も取得）
	 * ・存在しないシステム物件番号を指定
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>null を復帰する事。</li>
	 * </ul>
	 */
	@Test
	public void getPkNotHousingCdTest() throws Exception {

		// テストメソッドを実行
		Housing housing = this.housingManage.searchHousingPk("HOU10001", true);
		Assert.assertNull("戻り値が null である事", housing);

	}



	// テストデータ作成
	private void initTestData(){

		BuildingInfo buildingInfo;
		BuildingDtlInfo buildingDtlInfo;
		BuildingStationInfo[] buildingStationInfo = new BuildingStationInfo[2];
		BuildingLandmark[] buildingLandmark = new BuildingLandmark[2];
		
		HousingInfo housingInfo;
		HousingDtlInfo housingDtlInfo;
		HousingStatusInfo housingStatusInfo;
		HousingImageInfo[] housingImageInfo = new HousingImageInfo[2];
		HousingEquipInfo[] housingEquipInfo = new HousingEquipInfo[2];
		HousingExtInfo[] housingExtInfo = new HousingExtInfo[3];

		
		// ------ 取得対象データ ------
		// 建物基本情報
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("BULD00001");
		buildingInfo.setDisplayBuildingName("建物名１");
		buildingInfo.setPrefCd("13");

		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// 建物詳細情報
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("BULD00001");

		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] {buildingDtlInfo});

		// 建物最寄り駅情報　（2件）
		buildingStationInfo[0] = new BuildingStationInfo();
		buildingStationInfo[0].setSysBuildingCd("BULD00001");
		buildingStationInfo[0].setDivNo(1);
		buildingStationInfo[0].setStationCd("29726");	// 奇跡の一本松

		buildingStationInfo[1] = new BuildingStationInfo();
		buildingStationInfo[1].setSysBuildingCd("BULD00001");
		buildingStationInfo[1].setDivNo(2);
		buildingStationInfo[1].setStationCd("29695");	// 泰澄の里

		this.buildingStationInfoDAO.insert(buildingStationInfo);;

		// 建物ランドマーク情報　（２件）
		buildingLandmark[0] = new BuildingLandmark();
		buildingLandmark[0].setSysBuildingCd("BULD00001");
		buildingLandmark[0].setDivNo(1);
		buildingLandmark[0].setWayFromLandmark("1");
		buildingLandmark[0].setLandmarkName("LANDNM0001_1");

		buildingLandmark[1] = new BuildingLandmark();
		buildingLandmark[1].setSysBuildingCd("BULD00001");
		buildingLandmark[1].setDivNo(2);
		buildingLandmark[1].setWayFromLandmark("1");
		buildingLandmark[1].setLandmarkName("LANDNM0001_2");

		this.buildingLandmarkDAO.insert(buildingLandmark);

		//　物件基本情報
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("HOU00001");
		housingInfo.setSysBuildingCd("BULD00001");
		housingInfo.setDisplayHousingName("物件名１");

		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// 物件詳細情報
		housingDtlInfo = new HousingDtlInfo();
		housingDtlInfo.setSysHousingCd("HOU00001");

		this.housingDtlInfoDAO.insert(new HousingDtlInfo[]{housingDtlInfo});

		// 物件ステータス情報
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("HOU00001");
		housingStatusInfo.setHiddenFlg("0");

		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// 物件画像情報　（２件）
		housingImageInfo[0] = new HousingImageInfo();
		housingImageInfo[0].setSysHousingCd("HOU00001");
		housingImageInfo[0].setImageType("00");
		housingImageInfo[0].setDivNo(1);
		housingImageInfo[0].setPathName("PATH00001_01");
		housingImageInfo[0].setFileName("FILE00001_01");

		housingImageInfo[1] = new HousingImageInfo();
		housingImageInfo[1].setSysHousingCd("HOU00001");
		housingImageInfo[1].setImageType("00");
		housingImageInfo[1].setDivNo(2);
		housingImageInfo[1].setPathName("PATH00001_02");
		housingImageInfo[1].setFileName("FILE00001_02");

		this.housingImageInfoDAO.insert(housingImageInfo);

		// 物件設備情報
		housingEquipInfo[0] = new HousingEquipInfo();
		housingEquipInfo[0].setSysHousingCd("HOU00001");
		housingEquipInfo[0].setEquipCd("000001");

		housingEquipInfo[1] = new HousingEquipInfo();
		housingEquipInfo[1].setSysHousingCd("HOU00001");
		housingEquipInfo[1].setEquipCd("000002");
				
		this.housingEquipInfoDAO.insert(housingEquipInfo);;

		// 物件拡張属性情報
		housingExtInfo[0] = new HousingExtInfo();
		housingExtInfo[0].setSysHousingCd("HOU00001");
		housingExtInfo[0].setCategory("CATE0001");
		housingExtInfo[0].setKeyName("KEY0001");
		housingExtInfo[0].setDataValue("VALUE00001");

		housingExtInfo[1] = new HousingExtInfo();
		housingExtInfo[1].setSysHousingCd("HOU00001");
		housingExtInfo[1].setCategory("CATE0001");
		housingExtInfo[1].setKeyName("KEY0002");
		housingExtInfo[1].setDataValue("VALUE00002");

		housingExtInfo[2] = new HousingExtInfo();
		housingExtInfo[2].setSysHousingCd("HOU00001");
		housingExtInfo[2].setCategory("CATE0002");
		housingExtInfo[2].setKeyName("KEY0001");
		housingExtInfo[2].setDataValue("VALUE00003");

		this.housingExtInfoDAO.insert(housingExtInfo);



		// ------ 取得対象外データ（同一建物で別物件）------
		//　物件基本情報
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("HOU00002");
		housingInfo.setSysBuildingCd("BULD00001");
		housingInfo.setDisplayHousingName("物件名２");
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// 物件詳細情報
		housingDtlInfo = new HousingDtlInfo();
		housingDtlInfo.setSysHousingCd("HOU00002");

		this.housingDtlInfoDAO.insert(new HousingDtlInfo[]{housingDtlInfo});

		// 物件ステータス情報
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("HOU00002");
		housingStatusInfo.setHiddenFlg("0");

		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// 物件画像情報　（２件）
		housingImageInfo[0] = new HousingImageInfo();
		housingImageInfo[0].setSysHousingCd("HOU00002");
		housingImageInfo[0].setImageType("00");
		housingImageInfo[0].setDivNo(1);
		housingImageInfo[0].setPathName("PATH00002_01");
		housingImageInfo[0].setFileName("FILE00002_01");

		housingImageInfo[1] = new HousingImageInfo();
		housingImageInfo[1].setSysHousingCd("HOU00002");
		housingImageInfo[1].setImageType("00");
		housingImageInfo[1].setDivNo(2);
		housingImageInfo[1].setPathName("PATH00002_02");
		housingImageInfo[1].setFileName("FILE00002_02");

		this.housingImageInfoDAO.insert(housingImageInfo);

		// 物件設備情報
		housingEquipInfo[0] = new HousingEquipInfo();
		housingEquipInfo[0].setSysHousingCd("HOU00002");
		housingEquipInfo[0].setEquipCd("000003");

		housingEquipInfo[1] = new HousingEquipInfo();
		housingEquipInfo[1].setSysHousingCd("HOU00002");
		housingEquipInfo[1].setEquipCd("000004");
				
		this.housingEquipInfoDAO.insert(housingEquipInfo);;

		// 物件拡張属性情報
		housingExtInfo[0] = new HousingExtInfo();
		housingExtInfo[0].setSysHousingCd("HOU00002");
		housingExtInfo[0].setCategory("CATE0001");
		housingExtInfo[0].setKeyName("KEY0001");
		housingExtInfo[0].setDataValue("VALUE00021");

		housingExtInfo[1] = new HousingExtInfo();
		housingExtInfo[1].setSysHousingCd("HOU00002");
		housingExtInfo[1].setCategory("CATE0001");
		housingExtInfo[1].setKeyName("KEY0002");
		housingExtInfo[1].setDataValue("VALUE00022");

		housingExtInfo[2] = new HousingExtInfo();
		housingExtInfo[2].setSysHousingCd("HOU00002");
		housingExtInfo[2].setCategory("CATE0002");
		housingExtInfo[2].setKeyName("KEY0001");
		housingExtInfo[2].setDataValue("VALUE00023");

		this.housingExtInfoDAO.insert(housingExtInfo);


		
		// ------ 取得対象外データ（建物CD違い）------
		// 建物基本情報
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("BULD00003");
		buildingInfo.setDisplayBuildingName("建物名３");
		buildingInfo.setPrefCd("14");

		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// 建物詳細情報
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("BULD00003");
		
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] {buildingDtlInfo});

		// 建物最寄り駅情報　（2件）
		buildingStationInfo[0] = new BuildingStationInfo();
		buildingStationInfo[0].setSysBuildingCd("BULD00003");
		buildingStationInfo[0].setDivNo(1);
		buildingStationInfo[0].setStationCd("29696");	// 清明

		buildingStationInfo[1] = new BuildingStationInfo();
		buildingStationInfo[1].setSysBuildingCd("BULD00003");
		buildingStationInfo[1].setDivNo(2);
		buildingStationInfo[1].setStationCd("29705");	// ベイサイドアリーナ

		this.buildingStationInfoDAO.insert(buildingStationInfo);;

		// 建物ランドマーク情報　（２件）
		buildingLandmark[0] = new BuildingLandmark();
		buildingLandmark[0].setSysBuildingCd("BULD00003");
		buildingLandmark[0].setDivNo(1);
		buildingLandmark[0].setWayFromLandmark("1");
		buildingLandmark[0].setLandmarkName("LANDNM0003_1");

		buildingLandmark[1] = new BuildingLandmark();
		buildingLandmark[1].setSysBuildingCd("BULD00003");
		buildingLandmark[1].setDivNo(2);
		buildingLandmark[1].setWayFromLandmark("1");
		buildingLandmark[1].setLandmarkName("LANDNM0003_2");

		this.buildingLandmarkDAO.insert(buildingLandmark);

		//　物件基本情報
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("HOU00003");
		housingInfo.setSysBuildingCd("BULD00003");
		housingInfo.setDisplayHousingName("物件名３");
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// 物件詳細情報
		housingDtlInfo = new HousingDtlInfo();
		housingDtlInfo.setSysHousingCd("HOU00003");

		this.housingDtlInfoDAO.insert(new HousingDtlInfo[]{housingDtlInfo});

		// 物件ステータス情報
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("HOU00003");
		housingStatusInfo.setHiddenFlg("0");

		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// 物件画像情報　（２件）
		housingImageInfo[0] = new HousingImageInfo();
		housingImageInfo[0].setSysHousingCd("HOU00003");
		housingImageInfo[0].setImageType("00");
		housingImageInfo[0].setDivNo(1);
		housingImageInfo[0].setPathName("PATH00003_01");
		housingImageInfo[0].setFileName("FILE00003_01");

		housingImageInfo[1] = new HousingImageInfo();
		housingImageInfo[1].setSysHousingCd("HOU00003");
		housingImageInfo[1].setImageType("00");
		housingImageInfo[1].setDivNo(2);
		housingImageInfo[1].setPathName("PATH00003_02");
		housingImageInfo[1].setFileName("FILE00003_02");

		this.housingImageInfoDAO.insert(housingImageInfo);

		// 物件設備情報
		housingEquipInfo[0] = new HousingEquipInfo();
		housingEquipInfo[0].setSysHousingCd("HOU00003");
		housingEquipInfo[0].setEquipCd("000005");

		housingEquipInfo[1] = new HousingEquipInfo();
		housingEquipInfo[1].setSysHousingCd("HOU00003");
		housingEquipInfo[1].setEquipCd("000006");
				
		this.housingEquipInfoDAO.insert(housingEquipInfo);;

		// 物件拡張属性情報
		housingExtInfo[0] = new HousingExtInfo();
		housingExtInfo[0].setSysHousingCd("HOU00003");
		housingExtInfo[0].setCategory("CATE0001");
		housingExtInfo[0].setKeyName("KEY0001");
		housingExtInfo[0].setDataValue("VALUE00031");

		housingExtInfo[1] = new HousingExtInfo();
		housingExtInfo[1].setSysHousingCd("HOU00003");
		housingExtInfo[1].setCategory("CATE0001");
		housingExtInfo[1].setKeyName("KEY0002");
		housingExtInfo[1].setDataValue("VALUE00032");

		housingExtInfo[2] = new HousingExtInfo();
		housingExtInfo[2].setSysHousingCd("HOU00003");
		housingExtInfo[2].setCategory("CATE0002");
		housingExtInfo[2].setKeyName("KEY0001");
		housingExtInfo[2].setDataValue("VALUE00033");

		this.housingExtInfoDAO.insert(housingExtInfo);

		
		
		// ------ 取得対象データ（ステータス情報なしによる公開） ------
		//　物件基本情報
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("HOU00004");
		housingInfo.setSysBuildingCd("BULD00001");
		housingInfo.setDisplayHousingName("物件名４");

		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// 物件画像情報　（２件）
		housingImageInfo[0] = new HousingImageInfo();
		housingImageInfo[0].setSysHousingCd("HOU00004");
		housingImageInfo[0].setImageType("00");
		housingImageInfo[0].setDivNo(1);
		housingImageInfo[0].setPathName("PATH00004_01");
		housingImageInfo[0].setFileName("FILE00004_01");

		housingImageInfo[1] = new HousingImageInfo();
		housingImageInfo[1].setSysHousingCd("HOU00004");
		housingImageInfo[1].setImageType("00");
		housingImageInfo[1].setDivNo(2);
		housingImageInfo[1].setPathName("PATH00004_02");
		housingImageInfo[1].setFileName("FILE00004_02");

		this.housingImageInfoDAO.insert(housingImageInfo);

		// 物件設備情報
		housingEquipInfo[0] = new HousingEquipInfo();
		housingEquipInfo[0].setSysHousingCd("HOU00004");
		housingEquipInfo[0].setEquipCd("000007");

		housingEquipInfo[1] = new HousingEquipInfo();
		housingEquipInfo[1].setSysHousingCd("HOU00004");
		housingEquipInfo[1].setEquipCd("000008");
				
		this.housingEquipInfoDAO.insert(housingEquipInfo);;
		
		// 物件拡張属性情報
		housingExtInfo[0] = new HousingExtInfo();
		housingExtInfo[0].setSysHousingCd("HOU00004");
		housingExtInfo[0].setCategory("CATE0001");
		housingExtInfo[0].setKeyName("KEY0001");
		housingExtInfo[0].setDataValue("VALUE00041");

		housingExtInfo[1] = new HousingExtInfo();
		housingExtInfo[1].setSysHousingCd("HOU00004");
		housingExtInfo[1].setCategory("CATE0001");
		housingExtInfo[1].setKeyName("KEY0002");
		housingExtInfo[1].setDataValue("VALUE00042");

		housingExtInfo[2] = new HousingExtInfo();
		housingExtInfo[2].setSysHousingCd("HOU00004");
		housingExtInfo[2].setCategory("CATE0002");
		housingExtInfo[2].setKeyName("KEY0001");
		housingExtInfo[2].setDataValue("VALUE00043");

		this.housingExtInfoDAO.insert(housingExtInfo);

		
		
		// ------ 取得対象データ（非公開） ------
		//　物件基本情報
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("HOU00005");
		housingInfo.setSysBuildingCd("BULD00001");
		housingInfo.setDisplayHousingName("物件名５");

		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// 物件ステータス情報
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("HOU00005");
		housingStatusInfo.setHiddenFlg("1");

		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// 物件画像情報　（２件）
		housingImageInfo[0] = new HousingImageInfo();
		housingImageInfo[0].setSysHousingCd("HOU00005");
		housingImageInfo[0].setImageType("00");
		housingImageInfo[0].setDivNo(1);
		housingImageInfo[0].setPathName("PATH00005_01");
		housingImageInfo[0].setFileName("FILE00005_01");

		housingImageInfo[1] = new HousingImageInfo();
		housingImageInfo[1].setSysHousingCd("HOU00005");
		housingImageInfo[1].setImageType("00");
		housingImageInfo[1].setDivNo(2);
		housingImageInfo[1].setPathName("PATH00005_02");
		housingImageInfo[1].setFileName("FILE00005_02");

		this.housingImageInfoDAO.insert(housingImageInfo);

		// 物件設備情報
		housingEquipInfo[0] = new HousingEquipInfo();
		housingEquipInfo[0].setSysHousingCd("HOU00005");
		housingEquipInfo[0].setEquipCd("000001");

		housingEquipInfo[1] = new HousingEquipInfo();
		housingEquipInfo[1].setSysHousingCd("HOU00005");
		housingEquipInfo[1].setEquipCd("000002");
				
		this.housingEquipInfoDAO.insert(housingEquipInfo);;

		// 物件拡張属性情報
		housingExtInfo[0] = new HousingExtInfo();
		housingExtInfo[0].setSysHousingCd("HOU00005");
		housingExtInfo[0].setCategory("CATE0001");
		housingExtInfo[0].setKeyName("KEY0001");
		housingExtInfo[0].setDataValue("VALUE00051");

		housingExtInfo[1] = new HousingExtInfo();
		housingExtInfo[1].setSysHousingCd("HOU00005");
		housingExtInfo[1].setCategory("CATE0001");
		housingExtInfo[1].setKeyName("KEY0002");
		housingExtInfo[1].setDataValue("VALUE00052");

		housingExtInfo[2] = new HousingExtInfo();
		housingExtInfo[2].setSysHousingCd("HOU00005");
		housingExtInfo[2].setCategory("CATE0002");
		housingExtInfo[2].setKeyName("KEY0001");
		housingExtInfo[2].setDataValue("VALUE00053");

		this.housingExtInfoDAO.insert(housingExtInfo);

	}

}
