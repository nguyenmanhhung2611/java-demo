package jp.co.transcosmos.dm3.test.core.model.favorite;

import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.FavoriteManage;
import jp.co.transcosmos.dm3.core.model.favorite.form.FavoriteFormFactory;
import jp.co.transcosmos.dm3.core.model.favorite.form.FavoriteSearchForm;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.FavoriteInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.UserInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * お気に入り情報 model のテストケース
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")

public class FavoriteManageTest {

	@Autowired
	private DAO<FavoriteInfo> favoriteInfoDAO;

	@Autowired
	private DAO<UserInfo> userInfoDAO;

	@Autowired
	private DAO<BuildingInfo> buildingInfoDAO;

	@Autowired
	private DAO<HousingInfo> housingInfoDAO;

	@Autowired
	private FavoriteManage favoriteManage;

	@Autowired
	private FavoriteFormFactory favoriteFormFactory;

	// 前処理
	@Before
	public void init() {
		// 関連するデータの全件削除
		DAOCriteria criteria = new DAOCriteria();
		this.favoriteInfoDAO.deleteByFilter(criteria);
		this.userInfoDAO.deleteByFilter(criteria);
		this.buildingInfoDAO.deleteByFilter(criteria);
		this.housingInfoDAO.deleteByFilter(criteria);

		initTestData();
	}

	// テストデータ作成
	private void initTestData() {

		UserInfo userInfo;
		BuildingInfo buildingInfo;
		HousingInfo housingInfo;

		// ------ 登録データ------

		// ユーザID情報
		userInfo = new UserInfo();
		userInfo.setUserId("UID00001");
		userInfoDAO.insert(new UserInfo[] { userInfo });

		userInfo = new UserInfo();
		userInfo.setUserId("UID00002");
		userInfoDAO.insert(new UserInfo[] { userInfo });

		userInfo = new UserInfo();
		userInfo.setUserId("UID00003");
		userInfoDAO.insert(new UserInfo[] { userInfo });

		// 建物基本情報
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("BULD00001");
		buildingInfo.setDisplayBuildingName("建物名１");
		buildingInfo.setPrefCd("13");
		this.buildingInfoDAO.insert(new BuildingInfo[] { buildingInfo });

		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("BULD00002");
		buildingInfo.setDisplayBuildingName("建物名２");
		buildingInfo.setPrefCd("13");
		this.buildingInfoDAO.insert(new BuildingInfo[] { buildingInfo });

		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("BULD00003");
		buildingInfo.setDisplayBuildingName("建物名３");
		buildingInfo.setPrefCd("13");
		this.buildingInfoDAO.insert(new BuildingInfo[] { buildingInfo });

		// 物件基本情報
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("HOU00001");
		housingInfo.setSysBuildingCd("BULD00001");
		housingInfo.setDisplayHousingName("物件名１");
		this.housingInfoDAO.insert(new HousingInfo[] { housingInfo });

		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("HOU00002");
		housingInfo.setSysBuildingCd("BULD00002");
		housingInfo.setDisplayHousingName("物件名２");
		this.housingInfoDAO.insert(new HousingInfo[] { housingInfo });

		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("HOU00003");
		housingInfo.setSysBuildingCd("BULD00003");
		housingInfo.setDisplayHousingName("物件名３");
		this.housingInfoDAO.insert(new HousingInfo[] { housingInfo });

	}

	/**
	 * お気に入り情報の登録処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>パラメタが正しく設定されていない場合、お気に入り情報は登録せず、正常終了する事</li>
	 *     <li>存在しないユーザIDでは登録が行われず、正常終了する事</li>
	 *     <li>存在しないシステム物件IDでは登録が行われず、正常終了する事</li>
	 * </ul>
	 */
	@Test
	public void addFavoriteParamCheckTest() throws Exception {

		DAOCriteria criteria = null;
		List<FavoriteInfo> favoriteInfos = null;

		// テストコード実行（ユーザIDが空）
		this.favoriteManage.addFavorite("", "HOU00001");

		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "");
		criteria.addWhereClause("sysHousingCd", "");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("パラメタが正しく設定されていない場合、お気に入り情報は登録せず、正常終了する事", 0, favoriteInfos.size());

		// テストコード実行（ユーザIDがnull）
		this.favoriteManage.addFavorite(null, "HOU00001");

		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", null);
		criteria.addWhereClause("sysHousingCd", "");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("パラメタが正しく設定されていない場合、お気に入り情報は登録せず、正常終了する事", 0, favoriteInfos.size());

		// テストコード実行（システム物件CDが空）
		this.favoriteManage.addFavorite("UID00001", "");

		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00001");
		criteria.addWhereClause("sysHousingCd", "");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("パラメタが正しく設定されていない場合、お気に入り情報は登録せず、正常終了する事", 0, favoriteInfos.size());

		// テストコード実行（システム物件CDがnull）
		this.favoriteManage.addFavorite("UID00001", null);

		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00001");
		criteria.addWhereClause("sysHousingCd", null);
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("パラメタが正しく設定されていない場合、お気に入り情報は登録せず、正常終了する事", 0, favoriteInfos.size());

		// テストコード実行（存在しないユーザID）
		this.favoriteManage.addFavorite("UID00000", "HOU00001");

		// テスト結果確認
		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00000");
		criteria.addWhereClause("sysHousingCd", "HOU00001");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("存在しないユーザIDで登録されない事。", 0, favoriteInfos.size());

		// テストコード実行（存在しないシステム物件CD）
		this.favoriteManage.addFavorite("UID00001", "HOU00000");

		// テスト結果確認
		criteria.addWhereClause("userId", "UID00001");
		criteria.addWhereClause("sysHousingCd", "HOU00000");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("存在しないシステム物件CDで登録されない事。", 0, favoriteInfos.size());

	}

	/**
	 * お気に入り情報の登録処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>お気に入り情報が正しく登録される事</li>
	 *     <li>お気に入り情報が正しく更新される事</li>
	 * </ul>
	 */
	@Test
	public void addFavoriteTest() throws Exception {

		DAOCriteria criteria = null;
		List<FavoriteInfo> favoriteInfos = null;

		// テストケース実施前確認
		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00001");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("【テストケース実施前確認】お気に入りの登録件数が0件である事。", 0, favoriteInfos.size());

		// テストコード実行（お気に入り情報の新規登録）
		this.favoriteManage.addFavorite("UID00001", "HOU00001");

		// テスト結果確認
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("お気に入り情報が正しく登録されている事。", 1, favoriteInfos.size());
		Assert.assertEquals("お気に入り情報のユーザIDが正しく登録されている事。", "UID00001", favoriteInfos.get(0).getUserId());
		Assert.assertEquals("お気に入り情報のシステム物件CDが正しく登録されている事。", "HOU00001", favoriteInfos.get(0).getSysHousingCd());
		Assert.assertEquals("お気に入り情報の表示用物件名が正しく登録されている事。", "物件名１", favoriteInfos.get(0).getDisplayHousingName());

		// 更新テスト用に時刻取得
		Date insDate1 = favoriteInfos.get(0).getInsDate();
		Date updData1 = favoriteInfos.get(0).getUpdDate();

		// 更新テスト用に物件情報を更新
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK("HOU00001");
		housingInfo.setDisplayHousingName("物件１－１");
		this.housingInfoDAO.update(new HousingInfo[] { housingInfo });

		// ※続くテストにて更新時刻が同じになってしまわないように一時停止
		Thread.sleep(1000);

		// テストコード実行（お気に入り情報の既存データ更新）
		this.favoriteManage.addFavorite("UID00001", "HOU00001");

		// テスト結果確認
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("お気に入り情報が正しく登録されている事。", 1, favoriteInfos.size());
		Assert.assertEquals("お気に入り情報のユーザIDが正しく登録されている事。", "UID00001", favoriteInfos.get(0).getUserId());
		Assert.assertEquals("お気に入り情報のシステム物件CDが正しく登録されている事。", "HOU00001", favoriteInfos.get(0).getSysHousingCd());
		Assert.assertEquals("お気に入り情報の表示用物件名が正しく登録されている事。", "物件１－１", favoriteInfos.get(0).getDisplayHousingName());

		Date insDate2 = favoriteInfos.get(0).getInsDate();
		Date updData2 = favoriteInfos.get(0).getUpdDate();
		Assert.assertTrue("レコードが更新されている事。", insDate2.compareTo(insDate1) == 0);
		Assert.assertTrue("レコードが更新されている事。", updData2.compareTo(updData1) > 0);

	}

	/**
	 * お気に入り情報の削除処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>パラメタが正しく設定されていない場合、お気に入り情報は削除せず、正常終了する事</li>
	 *     <li>存在しないユーザIDでは削除が行われず、正常終了する事</li>
	 *     <li>存在しないシステム物件IDでは削除が行われず、正常終了する事</li>
	 * </ul>
	 */
	@Test
	public void delFavoriteParamCheckTest() throws Exception {

		DAOCriteria criteria = null;
		List<FavoriteInfo> favoriteInfos = null;
		FavoriteInfo favoriteInfo = null;

		// テスト用のお気に入り情報登録
		favoriteInfo = new FavoriteInfo();
		favoriteInfo.setUserId("UID00001");
		favoriteInfo.setSysHousingCd("HOU00001");
		favoriteInfo.setDisplayHousingName("物件名１");
		favoriteInfo.setInsUserId("UID00001");
		favoriteInfo.setUpdUserId("UID00001");
		Date date = new Date();
		favoriteInfo.setInsDate(date);
		favoriteInfo.setUpdDate(date);
		this.favoriteInfoDAO.insert(new FavoriteInfo[] { favoriteInfo} );

		// テストコード実行（ユーザIDが空）
		this.favoriteManage.delFavorite("", "HOU00001");

		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00001");
		criteria.addWhereClause("sysHousingCd", "HOU00001");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("パラメタが正しく設定されていない場合、お気に入り情報は削除せず、正常終了する事", 1, favoriteInfos.size());

		// テストコード実行（ユーザIDがnull）
		this.favoriteManage.delFavorite(null, "HOU00001");

		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00001");
		criteria.addWhereClause("sysHousingCd", "HOU00001");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("パラメタが正しく設定されていない場合、お気に入り情報は削除せず、正常終了する事", 1, favoriteInfos.size());

		// テストコード実行（システム物件CDが空）
		this.favoriteManage.delFavorite("UID00001", "");

		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00001");
		criteria.addWhereClause("sysHousingCd", "HOU00001");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("パラメタが正しく設定されていない場合、お気に入り情報は削除せず、正常終了する事", 1, favoriteInfos.size());

		// テストコード実行（システム物件CDがnull）
		this.favoriteManage.delFavorite("UID00001", null);

		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00001");
		criteria.addWhereClause("sysHousingCd", "HOU00001");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("パラメタが正しく設定されていない場合、お気に入り情報は削除せず、正常終了する事", 1, favoriteInfos.size());

		// テストコード実行（存在しないユーザID）
		this.favoriteManage.delFavorite("UID00000", "HOU00001");

		// テスト結果確認
		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00001");
		criteria.addWhereClause("sysHousingCd", "HOU00001");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("存在しないユーザIDで削除されない事。", 1, favoriteInfos.size());

		// テストコード実行（存在しないシステム物件CD）
		this.favoriteManage.delFavorite("UID00001", "HOU00000");

		// テスト結果確認
		criteria.addWhereClause("userId", "UID00001");
		criteria.addWhereClause("sysHousingCd", "HOU00001");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("存在しないシステム物件CDで削除されない事。", 1, favoriteInfos.size());

	}

	/**
	 * お気に入り情報の削除処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>お気に入り情報が正しく削除される事</li>
	 * </ul>
	 */
	@Test
	public void delFavoriteTest() throws Exception {

		DAOCriteria criteria = null;
		List<FavoriteInfo> favoriteInfos = null;
		FavoriteInfo favoriteInfo = null;

		// テスト用のお気に入り情報登録
		favoriteInfo = new FavoriteInfo();
		favoriteInfo.setUserId("UID00001");
		favoriteInfo.setSysHousingCd("HOU00001");
		favoriteInfo.setDisplayHousingName("物件名１");
		favoriteInfo.setInsUserId("UID00001");
		favoriteInfo.setUpdUserId("UID00001");
		Date date = new Date();
		favoriteInfo.setInsDate(date);
		favoriteInfo.setUpdDate(date);
		this.favoriteInfoDAO.insert(new FavoriteInfo[] { favoriteInfo} );

		// テストケース実施前確認
		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00001");
		criteria.addWhereClause("sysHousingCd", "HOU00001");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("【テストケース実施前確認】お気に入りの登録件数が1件である事。", 1, favoriteInfos.size());

		// テストコード実行
		this.favoriteManage.delFavorite("UID00001", "HOU00001");

		// テスト結果確認
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("お気に入り情報が正しく削除されている事。", 0, favoriteInfos.size());

	}

	/**
	 * お気に入り情報の件数取得処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>パラメタが正しく設定されていない場合も正常終了する事</li>
	 *     <li>ユーザIDが存在しない場合も正常終了する事</li>
	 * </ul>
	 */
	@Test
	public void getFavoriteCntParamCheckTest() throws Exception {

		int cnt = 0;

		// テストコード実行（ユーザIDが空）
		cnt = this.favoriteManage.getFavoriteCnt("");

		// テスト結果確認
		Assert.assertEquals("パラメタが正しく設定されていない場合も正常終了する事。", 0, cnt);

		// テストコード実行（ユーザIDがNull）
		cnt = this.favoriteManage.getFavoriteCnt(null);

		// テスト結果確認
		Assert.assertEquals("パラメタが正しく設定されていない場合も正常終了する事。", 0, cnt);

		// テストコード実行（存在しないユーザID）
		cnt = this.favoriteManage.getFavoriteCnt("UID00000");

		// テスト結果確認
		Assert.assertEquals("ユーザIDが存在しない場合も正常終了する事。", 0, cnt);

	}

	/**
	 * お気に入り情報の件数取得処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>お気に入り情報の件数が正しく取得できる事</li>
	 * </ul>
	 */
	@Test
	public void getFavoriteCntTest() throws Exception {

		DAOCriteria criteria = null;
		List<FavoriteInfo> favoriteInfos = null;
		FavoriteInfo favoriteInfo = null;
		int cnt = 0;

		// テスト用のお気に入り情報登録
		favoriteInfo = new FavoriteInfo();
		favoriteInfo.setUserId("UID00001");
		favoriteInfo.setSysHousingCd("HOU00001");
		favoriteInfo.setDisplayHousingName("物件名１");
		favoriteInfo.setInsUserId("UID00001");
		favoriteInfo.setUpdUserId("UID00001");
		Date date = new Date();
		favoriteInfo.setInsDate(date);
		favoriteInfo.setUpdDate(date);
		this.favoriteInfoDAO.insert(new FavoriteInfo[] { favoriteInfo });

		// テストケース実施前確認
		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00001");
		criteria.addWhereClause("sysHousingCd", "HOU00001");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("【テストケース実施前確認】お気に入りの登録件数が1件である事。", 1, favoriteInfos.size());

		// テストコード実行（お気に入り情報有り）
		cnt = this.favoriteManage.getFavoriteCnt("UID00001");

		// テスト結果確認
		Assert.assertEquals("お気に入り情報の件数が正しく取得できる事。", 1, cnt);

		// テストコード実行（お気に入り情報無し）
		cnt = this.favoriteManage.getFavoriteCnt("UID00002");

		// テスト結果確認
		Assert.assertEquals("お気に入り情報の件数が正しく取得できる事。", 0, cnt);

	}

	/**
	 * お気に入り情報の検索処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>パラメタが正しく設定されていない場合も正常終了する事</li>
	 *     <li>ユーザIDが存在しない場合も正常終了する事</li>
	 * </ul>
	 */
	@Test
	public void searchFavoriteParamCheckTest() throws Exception {

		FavoriteSearchForm searchForm = null;
		int cnt = 0;

		// テストコード実行（ユーザIDが空）
		searchForm = this.favoriteFormFactory.createFavoriteSearchForm();
		cnt = this.favoriteManage.searchFavorite("", searchForm);
		Assert.assertEquals("パラメタが正しく設定されていない場合も正常終了する事。", 0, cnt);

		// テストコード実行（ユーザIDがnull）
		searchForm = this.favoriteFormFactory.createFavoriteSearchForm();
		cnt = this.favoriteManage.searchFavorite(null, searchForm);
		Assert.assertEquals("パラメタが正しく設定されていない場合も正常終了する事。", 0, cnt);

		// テストコード実行（FavoriteSearchFormがnull）
		cnt = this.favoriteManage.searchFavorite("UID00001", null);
		Assert.assertEquals("パラメタが正しく設定されていない場合も正常終了する事。", 0, cnt);

		// テストコード実行（ユーザIDが存在しない）
		searchForm = this.favoriteFormFactory.createFavoriteSearchForm();
		cnt = this.favoriteManage.searchFavorite("UID00000", searchForm);
		Assert.assertEquals("ユーザIDが存在しない場合も正常終了する事。", 0, cnt);

	}

	/**
	 * お気に入り情報の検索処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>物件情報の存在しないお気に入り情報は削除されて検索される事</li>
	 * </ul>
	 */
	@Test
	public void searchFavoriteTest1() throws Exception {

		DAOCriteria criteria = null;
		List<FavoriteInfo> favoriteInfos = null;
		FavoriteInfo favoriteInfo = null;
		HousingInfo housingInfo = null;
		Date date = null;
		int cnt = 0;
		List<JoinResult> results = null;

		// テスト用のお気に入り情報登録
		favoriteInfo = new FavoriteInfo();
		favoriteInfo.setUserId("UID00001");
		favoriteInfo.setSysHousingCd("HOU00000");
		favoriteInfo.setDisplayHousingName("物件名０");
		favoriteInfo.setInsUserId("UID00001");
		favoriteInfo.setUpdUserId("UID00001");
		date = new Date();
		favoriteInfo.setInsDate(date);
		favoriteInfo.setUpdDate(date);
		this.favoriteInfoDAO.insert(new FavoriteInfo[] { favoriteInfo });

		favoriteInfo = new FavoriteInfo();
		favoriteInfo.setUserId("UID00001");
		favoriteInfo.setSysHousingCd("HOU00001");
		favoriteInfo.setDisplayHousingName("物件名１");
		favoriteInfo.setInsUserId("UID00001");
		favoriteInfo.setUpdUserId("UID00001");
		date = new Date();
		favoriteInfo.setInsDate(date);
		favoriteInfo.setUpdDate(date);
		this.favoriteInfoDAO.insert(new FavoriteInfo[] { favoriteInfo });

		// テストケース実施前確認
		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00001");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("【事前確認】テスト実施前、お気に入りの登録件数が2件である事。", 2, favoriteInfos.size());

		// テストコード実行
		FavoriteSearchForm searchForm = this.favoriteFormFactory.createFavoriteSearchForm();
		cnt = this.favoriteManage.searchFavorite("UID00001", searchForm);

		// テスト結果確認
		Assert.assertEquals("お気に入り情報が正しく取得されている事。", 1, cnt);

		results = searchForm.getRows();
		favoriteInfo = (FavoriteInfo) results.get(0).getItems().get("favoriteInfo");
		housingInfo = (HousingInfo) results.get(0).getItems().get("housingInfo");

		Assert.assertEquals("お気に入り情報のユーザIDが正しい事。", "UID00001", favoriteInfo.getUserId());
		Assert.assertEquals("お気に入り情報のシステム物件CDが正しい事。", "HOU00001", favoriteInfo.getSysHousingCd());
		Assert.assertEquals("お気に入り情報の表示用物件名が正しい事。", "物件名１", favoriteInfo.getDisplayHousingName());
		Assert.assertEquals("物件基本情報のシステム物件CDが正しい事。", "HOU00001", housingInfo.getSysHousingCd());
		Assert.assertEquals("物件基本情報の表示用物件名が正しい事。", "物件名１", housingInfo.getDisplayHousingName());

	}

	/**
	 * お気に入り情報の検索処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>お気に入り情報が正しく検索される事</li>
	 *     <li>取得されたお気に入り情報がユーザIDの昇順、最終更新日の降順で並んでいる事</li>
	 * </ul>
	 */
	@Test
	public void searchFavoriteTest2() throws Exception {

		DAOCriteria criteria = null;
		List<FavoriteInfo> favoriteInfos = null;
		FavoriteInfo favoriteInfo = null;
		HousingInfo housingInfo = null;
		Date date = null;
		FavoriteSearchForm searchForm = null;
		int cnt = 0;
		List<JoinResult> results = null;

		// テスト用のお気に入り情報登録
		favoriteInfo = new FavoriteInfo();
		favoriteInfo.setUserId("UID00001");
		favoriteInfo.setSysHousingCd("HOU00003");
		favoriteInfo.setDisplayHousingName("物件名３");
		favoriteInfo.setInsUserId("UID00001");
		favoriteInfo.setUpdUserId("UID00001");
		date = new Date();
		favoriteInfo.setInsDate(date);
		favoriteInfo.setUpdDate(date);
		this.favoriteInfoDAO.insert(new FavoriteInfo[] { favoriteInfo });

		favoriteInfo = new FavoriteInfo();
		favoriteInfo.setUserId("UID00001");
		favoriteInfo.setSysHousingCd("HOU00001");
		favoriteInfo.setDisplayHousingName("物件名１");
		favoriteInfo.setInsUserId("UID00001");
		favoriteInfo.setUpdUserId("UID00001");
		date = new Date();
		favoriteInfo.setInsDate(date);
		favoriteInfo.setUpdDate(date);
		this.favoriteInfoDAO.insert(new FavoriteInfo[] { favoriteInfo });

		favoriteInfo = new FavoriteInfo();
		favoriteInfo.setUserId("UID00001");
		favoriteInfo.setSysHousingCd("HOU00002");
		favoriteInfo.setDisplayHousingName("物件名２");
		favoriteInfo.setInsUserId("UID00001");
		favoriteInfo.setUpdUserId("UID00001");
		date = new Date();
		favoriteInfo.setInsDate(date);
		favoriteInfo.setUpdDate(date);
		this.favoriteInfoDAO.insert(new FavoriteInfo[] { favoriteInfo });

		// テストケース実施前確認
		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00001");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("【テストケース実施前確認】お気に入りの登録件数が2件である事。", 3, favoriteInfos.size());

		// テストコード実行
		searchForm = this.favoriteFormFactory.createFavoriteSearchForm();
		cnt = this.favoriteManage.searchFavorite("UID00001", searchForm);

		// テスト結果確認
		Assert.assertEquals("お気に入り情報が正しく取得されている事。", 3, cnt);

		results = searchForm.getRows();
		favoriteInfo = (FavoriteInfo) results.get(0).getItems().get("favoriteInfo");
		housingInfo = (HousingInfo) results.get(0).getItems().get("housingInfo");

		Assert.assertEquals("お気に入り情報のユーザIDが正しい事。", "UID00001", favoriteInfo.getUserId());
		Assert.assertEquals("お気に入り情報のシステム物件CDが正しい事。", "HOU00001", favoriteInfo.getSysHousingCd());
		Assert.assertEquals("お気に入り情報の表示用物件名が正しい事。", "物件名１", favoriteInfo.getDisplayHousingName());
		Assert.assertEquals("物件基本情報のシステム物件CDが正しい事。", "HOU00001", housingInfo.getSysHousingCd());
		Assert.assertEquals("物件基本情報の表示用物件名が正しい事。", "物件名１", housingInfo.getDisplayHousingName());

		favoriteInfo = (FavoriteInfo) results.get(1).getItems().get("favoriteInfo");
		housingInfo = (HousingInfo) results.get(1).getItems().get("housingInfo");

		Assert.assertEquals("お気に入り情報のユーザIDが正しい事。", "UID00001", favoriteInfo.getUserId());
		Assert.assertEquals("お気に入り情報のシステム物件CDが正しい事。", "HOU00002", favoriteInfo.getSysHousingCd());
		Assert.assertEquals("お気に入り情報の表示用物件名が正しい事。", "物件名２", favoriteInfo.getDisplayHousingName());
		Assert.assertEquals("物件基本情報のシステム物件CDが正しい事。", "HOU00002", housingInfo.getSysHousingCd());
		Assert.assertEquals("物件基本情報の表示用物件名が正しい事。", "物件名２", housingInfo.getDisplayHousingName());

		favoriteInfo = (FavoriteInfo) results.get(2).getItems().get("favoriteInfo");
		housingInfo = (HousingInfo) results.get(2).getItems().get("housingInfo");

		Assert.assertEquals("お気に入り情報のユーザIDが正しい事。", "UID00001", favoriteInfo.getUserId());
		Assert.assertEquals("お気に入り情報のシステム物件CDが正しい事。", "HOU00003", favoriteInfo.getSysHousingCd());
		Assert.assertEquals("お気に入り情報の表示用物件名が正しい事。", "物件名３", favoriteInfo.getDisplayHousingName());
		Assert.assertEquals("物件基本情報のシステム物件CDが正しい事。", "HOU00003", housingInfo.getSysHousingCd());
		Assert.assertEquals("物件基本情報の表示用物件名が正しい事。", "物件名３", housingInfo.getDisplayHousingName());

		// テストコード実行
		searchForm = this.favoriteFormFactory.createFavoriteSearchForm();
		searchForm.setSysHousingCd("HOU00002");
		cnt = this.favoriteManage.searchFavorite("UID00001", searchForm);

		// テスト結果確認
		Assert.assertEquals("お気に入り情報が正しく取得されている事。", 1, cnt);

		results = searchForm.getRows();
		favoriteInfo = (FavoriteInfo) results.get(0).getItems().get("favoriteInfo");
		housingInfo = (HousingInfo) results.get(0).getItems().get("housingInfo");

		Assert.assertEquals("お気に入り情報のユーザIDが正しい事。", "UID00001", favoriteInfo.getUserId());
		Assert.assertEquals("お気に入り情報のシステム物件CDが正しい事。", "HOU00002", favoriteInfo.getSysHousingCd());
		Assert.assertEquals("お気に入り情報の表示用物件名が正しい事。", "物件名２", favoriteInfo.getDisplayHousingName());
		Assert.assertEquals("物件基本情報のシステム物件CDが正しい事。", "HOU00002", housingInfo.getSysHousingCd());
		Assert.assertEquals("物件基本情報の表示用物件名が正しい事。", "物件名２", housingInfo.getDisplayHousingName());

	}

	/**
	 * お気に入り情報の検索処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>物件情報の存在しないお気に入り情報は削除されて検索される事</li>
	 * </ul>
	 */
	@Test
	public void searchFavoriteTest3() throws Exception {

		// buildCriteriaを使用したことによってページ表示件数外の
		// お気に入り情報が正しく削除されていないことがあったため、テストケース追加

		DAOCriteria criteria = null;
		List<FavoriteInfo> favoriteInfos = null;
		FavoriteInfo favoriteInfo = null;
		HousingInfo housingInfo = null;
		Date date = null;
		int cnt = 0;
		List<JoinResult> results = null;

		// テスト用のお気に入り情報登録
		favoriteInfo = new FavoriteInfo();
		favoriteInfo.setUserId("UID00001");
		favoriteInfo.setSysHousingCd("HOU00000");
		favoriteInfo.setDisplayHousingName("物件名０");
		favoriteInfo.setInsUserId("UID00001");
		favoriteInfo.setUpdUserId("UID00001");
		date = new Date();
		favoriteInfo.setInsDate(date);
		favoriteInfo.setUpdDate(date);
		this.favoriteInfoDAO.insert(new FavoriteInfo[] { favoriteInfo });

		// テスト用のお気に入り情報登録
		favoriteInfo = new FavoriteInfo();
		favoriteInfo.setUserId("UID00001");
		favoriteInfo.setSysHousingCd("HOU09999");
		favoriteInfo.setDisplayHousingName("物件名９９９９");
		favoriteInfo.setInsUserId("UID00001");
		favoriteInfo.setUpdUserId("UID00001");
		date = new Date();
		favoriteInfo.setInsDate(date);
		favoriteInfo.setUpdDate(date);
		this.favoriteInfoDAO.insert(new FavoriteInfo[] { favoriteInfo });

		favoriteInfo = new FavoriteInfo();
		favoriteInfo.setUserId("UID00001");
		favoriteInfo.setSysHousingCd("HOU00001");
		favoriteInfo.setDisplayHousingName("物件名１");
		favoriteInfo.setInsUserId("UID00001");
		favoriteInfo.setUpdUserId("UID00001");
		date = new Date();
		favoriteInfo.setInsDate(date);
		favoriteInfo.setUpdDate(date);
		this.favoriteInfoDAO.insert(new FavoriteInfo[] { favoriteInfo });

		// テストケース実施前確認
		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00001");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("【事前確認】テスト実施前、お気に入りの登録件数が3件である事。", 3, favoriteInfos.size());

		// テストコード実行
		FavoriteSearchForm searchForm = this.favoriteFormFactory.createFavoriteSearchForm();
		searchForm.setRowsPerPage(1);
		cnt = this.favoriteManage.searchFavorite("UID00001", searchForm);

		// テスト結果確認
		Assert.assertEquals("お気に入り情報が正しく取得されている事。", 1, cnt);

		results = searchForm.getRows();
		favoriteInfo = (FavoriteInfo) results.get(0).getItems().get("favoriteInfo");
		housingInfo = (HousingInfo) results.get(0).getItems().get("housingInfo");

		Assert.assertEquals("お気に入り情報のユーザIDが正しい事。", "UID00001", favoriteInfo.getUserId());
		Assert.assertEquals("お気に入り情報のシステム物件CDが正しい事。", "HOU00001", favoriteInfo.getSysHousingCd());
		Assert.assertEquals("お気に入り情報の表示用物件名が正しい事。", "物件名１", favoriteInfo.getDisplayHousingName());
		Assert.assertEquals("物件基本情報のシステム物件CDが正しい事。", "HOU00001", housingInfo.getSysHousingCd());
		Assert.assertEquals("物件基本情報の表示用物件名が正しい事。", "物件名１", housingInfo.getDisplayHousingName());

	}

}
