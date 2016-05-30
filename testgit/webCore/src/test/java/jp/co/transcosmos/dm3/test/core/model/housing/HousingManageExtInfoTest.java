package jp.co.transcosmos.dm3.test.core.model.housing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.model.HousingManage;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingExtInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * 物件情報 model 物件拡張属性情報のテストケース
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class HousingManageExtInfoTest {

	@Autowired
	private DAO<BuildingInfo> buildingInfoDAO;

	@Autowired
	private DAO<HousingInfo> housingInfoDAO;
	
	@Autowired
	private DAO<HousingExtInfo> housingExtInfoDAO;
	
	@Qualifier("housingManager")
	@Autowired
	private HousingManage housingManage;
	
	
	
	// テスト対象建物、物件の主キー
	Object buildingId[];
	Object housingId[];
	
	
	
	// 初期化処理
	@Before
	public void init() {
		// 物件情報全件削除
		DAOCriteria criteria = new DAOCriteria();
		this.housingInfoDAO.deleteByFilter(criteria);
		this.buildingInfoDAO.deleteByFilter(criteria);

		
		// テストデータの主キーを２個取得
		buildingId = this.buildingInfoDAO.allocatePrimaryKeyIds(2);
		housingId = this.housingInfoDAO.allocatePrimaryKeyIds(2);

		// note
		// core に対するテストコードなので、 VO を直接生成している。
		// テストコード以外では、この様な処理は行わない事。

		BuildingInfo buildingInfo = new BuildingInfo();

		// ダミー建物情報作成１件目
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd((String)buildingId[0]);
		buildingInfo.setDisplayBuildingName("ダミー１");
		buildingInfo.setPrefCd("13");
		this.buildingInfoDAO.insert(new BuildingInfo[]{buildingInfo});

		// ダミー建物情報作成２件目
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd((String)buildingId[1]);
		buildingInfo.setDisplayBuildingName("ダミー２");
		buildingInfo.setPrefCd("14");
		this.buildingInfoDAO.insert(new BuildingInfo[]{buildingInfo});

		
		HousingInfo housingInfo = new HousingInfo();
		
		// ダミー物件情報作成１件目
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd((String)housingId[0]);
		housingInfo.setSysBuildingCd((String)buildingId[0]);
		housingInfo.setDisplayHousingName("ダミー１");
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// ダミー物件情報作成２件目
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd((String)housingId[1]);
		housingInfo.setSysBuildingCd((String)buildingId[1]);
		housingInfo.setDisplayHousingName("ダミー２");
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

	}
	
	

	/**
	 * Key 単位の削除処理のテスト<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>指定された、システム物件CD、カテゴリ、Key のレコードが削除される事。</li>
	 *     <li>key 違いのレコードが削除されない事。</li>
	 *     <li>カテゴリ 違いのレコードが削除されない事。</li>
	 *     <li>システム物件CD 違いのレコードが削除されない事。</li>
	 *     <li>更新対象物件基本情報の更新日、更新者が変更されている事</li>
	 *     <li>更新対外の象物件基本情報の更新日、更新者が変更されていない事</li>
	 * </ul>
	 */
	@Test
	public void delExtInfoKeyLevelTest() throws Exception {
		
		// ダミーデータ作成
		HousingExtInfo housingExtInfo;
		
		// １件目（削除対象）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});
		
		// ２件目（削除対象外 key 違い）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key2");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// ３件目（削除対象外 category 違い）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category2");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// ４件目（削除対象外 sysHousingCd 違い）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[1]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		
		// テストデータチェック
		List<HousingExtInfo> list = this.housingExtInfoDAO.selectByFilter(null);
		Assert.assertEquals("テストデータの登録確認", 4, list.size());

		
		// テストコード実行
		this.housingManage.delExtInfo((String)housingId[0], "category1", "key1", "dummyID");

		
		// 実行結果チェック
		list = this.housingExtInfoDAO.selectByFilter(null);
		Assert.assertEquals("件数の確認", 3, list.size());

		// 削除対象のデータが存在しない事をチェック
		for (HousingExtInfo extInfo : list) {
			if (housingId[0].equals(extInfo.getSysHousingCd()) &&
				"category1".equals(extInfo.getCategory()) &&
				"key1".equals(extInfo.getKeyName())){
				Assert.fail("削除対象レコードが削除されていない");
			}
		}

		// 物件基本情報のタイムスタンプを確認
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK(housingId[0]);

		// タイムスタンプの方は自動チェックが難しいので、自動テスト対象外
		Assert.assertEquals("更新者が正しい事", "dummyID", housingInfo.getUpdUserId());
		Assert.assertNull("新規登録者が更新されていない事", housingInfo.getInsUserId());
		Assert.assertNull("新規登録日が更新されていない事", housingInfo.getInsDate());
		

		// 更新対象外レコードのタイムスタンプをチェック
		housingInfo = this.housingInfoDAO.selectByPK(housingId[1]);

		Assert.assertNull("更新対象外の新規登録者が更新されていない事", housingInfo.getInsUserId());
		Assert.assertNull("更新対象外の新規登録日が更新されていない事", housingInfo.getInsDate());
		Assert.assertNull("更新対象外の更新者が更新されていない事", housingInfo.getUpdUserId());
		Assert.assertNull("更新対象外の更新日が更新されていない事", housingInfo.getUpdDate());
		
	}
	
	
	
	/**
	 * Key 単位の削除処理のテスト（該当親レコードが存在しない場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>NotFoundException がスローされない事</li>
	 * </ul>
	 */
	@Test
	public void delExtInfoKeyLevelFailTest() throws Exception {

		// テストコード実行（ありえない親キーを指定）
		this.housingManage.delExtInfo("0000", "category1", "key1", "dummyID");

	}



	/**
	 * Key 単位の更新処理のテスト（該当レコードが存在しない場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>null 値が登録できる事</li>
	 *     <li>通常の文字列が登録できる事</li>
	 * </ul>
	 */
	@Test
	public void updExtInfoKeyLevelTest1() throws NotFoundException, Exception{

		// テストコード実行
		this.housingManage.updExtInfo((String)housingId[0], "category1", "key1", null, "dummyID");


		// 結果の確認
		List<HousingExtInfo> list = this.housingExtInfoDAO.selectByFilter(null);

		HousingExtInfo info = list.get(0);
		Assert.assertEquals("システム物件CD が正しい事", (String)housingId[0], info.getSysHousingCd());
		Assert.assertEquals("カテゴリが正しい事", "category1", info.getCategory());
		Assert.assertEquals("Key が正しい事", "key1", info.getKeyName());
		Assert.assertNull("value が null である事", info.getDataValue());

		
		// 物件拡張情報全件削除
		DAOCriteria criteria = new DAOCriteria();
		this.housingExtInfoDAO.deleteByFilter(criteria);

		
		// テストコード実行
		this.housingManage.updExtInfo((String)housingId[0], "category1", "key1", "あいうえお", "dummyID");
		
		// 結果の確認
		list = this.housingExtInfoDAO.selectByFilter(null);

		info = list.get(0);
		Assert.assertEquals("システム物件CD が正しい事", (String)housingId[0], info.getSysHousingCd());
		Assert.assertEquals("カテゴリが正しい事", "category1", info.getCategory());
		Assert.assertEquals("Key が正しい事", "key1", info.getKeyName());
		Assert.assertEquals("value が正しい事", "あいうえお", info.getDataValue());

	}



	/**
	 * Key 単位の更新処理のテスト（該当レコードが存在する場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>更新対象の値が null に更新できる事</li>
	 *     <li>更新対象の値が通常文字列に更新できる事</li>
	 * </ul>
	 */
	@Test
	public void updExtInfoKeyLevelTest2() throws NotFoundException, Exception{

		// ダミーデータ作成
		HousingExtInfo housingExtInfo;
		
		// １件目（更新対象）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});
		
		// ２件目（更新対象外 key 違い）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key2");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// ３件目（更新対象外 category 違い）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category2");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// ４件目（更新対象外 sysHousingCd 違い）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[1]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		
		// テストコード実行
		this.housingManage.updExtInfo((String)housingId[0], "category1", "key1", "かきくけこ", "dummyID");


		// 結果の確認
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key1");
		criteria.addWhereClause("dataValue", "かきくけこ");

		List<HousingExtInfo> list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("更新対象の value が正しい事", 1, list.size());

		
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key2");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertNull("更新対象外が更新されていない事", list.get(0).getDataValue());
		

		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category2");
		criteria.addWhereClause("keyName", "key1");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertNull("更新対象外が更新されていない事", list.get(0).getDataValue());

		
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[1]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key1");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertNull("更新対象外が更新されていない事", list.get(0).getDataValue());

	}



	/**
	 * カテゴリ単位の削除処理のテスト<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>指定された、システム物件CD、カテゴリのレコードが削除される事。</li>
	 *     <li>カテゴリ 違いのレコードが削除されない事。</li>
	 *     <li>システム物件CD 違いのレコードが削除されない事。</li>
	 * </ul>
	 */
	@Test
	public void delExtInfoCategoryLevelTest() throws Exception {
		
		// ダミーデータ作成
		HousingExtInfo housingExtInfo;
		
		// １件目（削除対象）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});
		
		// ２件目（削除対象 key 違い）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key2");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// ３件目（削除対象外 category 違い）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category2");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// ４件目（削除対象外 sysHousingCd 違い）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[1]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		
		// テストデータチェック
		List<HousingExtInfo> list = this.housingExtInfoDAO.selectByFilter(null);
		Assert.assertEquals("テストデータの登録確認", 4, list.size());

		
		// テストコード実行
		this.housingManage.delExtInfo((String)housingId[0], "category1", "dummyID");


		// 実行結果チェック
		list = this.housingExtInfoDAO.selectByFilter(null);
		Assert.assertEquals("件数の確認", 2, list.size());

		// 削除対象のデータが存在しない事をチェック
		for (HousingExtInfo extInfo : list) {
			if (housingId[0].equals(extInfo.getSysHousingCd()) &&
				"category1".equals(extInfo.getCategory())){
				Assert.fail("削除対象レコードが削除されていない");
			}
		}

	}
	
	
	
	/**
	 * カテゴリ単位の更新処理のテスト（該当レコードが存在する場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>対象のカテゴリ配下が入れ替わる事</li>
	 *     <li>対象外のカテゴリ配下が入れ替わらない事</li>
	 * </ul>
	 */
	@Test
	public void updExtInfoCategoryLevelTest1() throws NotFoundException, Exception{

		// ダミーデータ作成
		HousingExtInfo housingExtInfo;
		
		// １件目（更新対象）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});
		
		// ２件目（更新対象 key 違い）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key2");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// ３件目（更新対象外 category 違い）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category2");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// ４件目（更新対象外 sysHousingCd 違い）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[1]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		
		// テストコード実行
		Map<String, String> testMap = new HashMap<>();
		testMap.put("key11", "あいうえお");
		testMap.put("key12", "かきくけこ");

		this.housingManage.updExtInfo((String)housingId[0], "category1", testMap, "dummyID");


		// 結果の確認
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key1");

		List<HousingExtInfo> list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("更新前のデータが存在しない事", 0, list.size());

		
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key2");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("更新前のデータが存在しない事", 0, list.size());
		

		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key11");
		criteria.addWhereClause("dataValue", "あいうえお");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("更新後のデータが存在する事", 1, list.size());
		

		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key12");
		criteria.addWhereClause("dataValue", "かきくけこ");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("更新後のデータが存在する事", 1, list.size());

		
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category2");
		criteria.addWhereClause("keyName", "key1");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertNull("更新対象外が更新されていない事", list.get(0).getDataValue());

		
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[1]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key1");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertNull("更新対象外が更新されていない事", list.get(0).getDataValue());

	}


	
	
	/**
	 * カテゴリ単位の更新処理のテスト（該当レコードが存在する場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>Map が空の場合、対象のカテゴリ配下が削除される事</li>
	 *     <li>対象外のカテゴリ配下が入れ替わらない事</li>
	 * </ul>
	 */
	@Test
	public void updExtInfoCategoryLevelTest2() throws NotFoundException, Exception{

		// ダミーデータ作成
		HousingExtInfo housingExtInfo;
		
		// １件目（更新対象）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});
		
		// ２件目（更新対象 key 違い）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key2");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// ３件目（更新対象外 category 違い）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category2");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// ４件目（更新対象外 sysHousingCd 違い）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[1]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		
		// テストコード実行（マップ内が空）
		Map<String, String> testMap = new HashMap<>();

		this.housingManage.updExtInfo((String)housingId[0], "category1", testMap, "dummyID");


		// 結果の確認
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key1");

		List<HousingExtInfo> list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("更新前のデータが存在しない事", 0, list.size());

		
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key2");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("更新前のデータが存在しない事", 0, list.size());
		

		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category2");
		criteria.addWhereClause("keyName", "key1");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertNull("更新対象外が更新されていない事", list.get(0).getDataValue());

		
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[1]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key1");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertNull("更新対象外が更新されていない事", list.get(0).getDataValue());

	}

	
	
	/**
	 * カテゴリ単位の更新処理のテスト（該当レコードが存在する場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>Map が null 場合、対象のカテゴリ配下が削除される事</li>
	 *     <li>対象外のカテゴリ配下が入れ替わらない事</li>
	 * </ul>
	 */
	@Test
	public void updExtInfoCategoryLevelTest3() throws NotFoundException, Exception{

		// ダミーデータ作成
		HousingExtInfo housingExtInfo;
		
		// １件目（更新対象）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});
		
		// ２件目（更新対象 key 違い）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key2");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// ３件目（更新対象外 category 違い）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category2");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// ４件目（更新対象外 sysHousingCd 違い）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[1]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		
		// テストコード実行（マップ内がnull）
		this.housingManage.updExtInfo((String)housingId[0], "category1", null, "dummyID");


		// 結果の確認
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key1");

		List<HousingExtInfo> list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("更新前のデータが存在しない事", 0, list.size());

		
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key2");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("更新前のデータが存在しない事", 0, list.size());
		

		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category2");
		criteria.addWhereClause("keyName", "key1");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertNull("更新対象外が更新されていない事", list.get(0).getDataValue());

		
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[1]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key1");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertNull("更新対象外が更新されていない事", list.get(0).getDataValue());

	}

	
	
	/**
	 * システム物件CD単位の削除処理のテスト<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>指定された、システム物件CDのレコードが削除される事。</li>
	 *     <li>システム物件CD 違いのレコードが削除されない事。</li>
	 * </ul>
	 */
	@Test
	public void delExtInfoHCDLevelTest() throws Exception {
		
		// ダミーデータ作成
		HousingExtInfo housingExtInfo;
		
		// １件目（削除対象）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});
		
		// ２件目（削除対象 key 違い）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key2");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// ３件目（削除対象 category 違い）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category2");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// ４件目（削除対象外 sysHousingCd 違い）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[1]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		
		// テストデータチェック
		List<HousingExtInfo> list = this.housingExtInfoDAO.selectByFilter(null);
		Assert.assertEquals("テストデータの登録確認", 4, list.size());

		
		// テストコード実行
		this.housingManage.delExtInfo((String)housingId[0], "dummyID");


		// 実行結果チェック
		list = this.housingExtInfoDAO.selectByFilter(null);
		Assert.assertEquals("件数の確認", 1, list.size());

		// 削除対象のデータが存在しない事をチェック
		for (HousingExtInfo extInfo : list) {
			if (housingId[0].equals(extInfo.getSysHousingCd()) &&
				"category1".equals(extInfo.getCategory())){
				Assert.fail("削除対象レコードが削除されていない");
			}
		}

	}

	
	
	/**
	 * システム物件CD単位の更新処理のテスト（該当レコードが存在する場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>対象のシステム物件CD配下が入れ替わる事</li>
	 *     <li>対象外のシステム物件CD配下が入れ替わらない事</li>
	 * </ul>
	 */
	@Test
	public void updExtInfoHCDLevelTest1() throws NotFoundException, Exception{

		// ダミーデータ作成
		HousingExtInfo housingExtInfo;
		
		// １件目（更新対象）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});
		
		// ２件目（更新対象 key 違い）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key2");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// ３件目（更新対象 category 違い）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category2");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// ４件目（更新対象外 sysHousingCd 違い）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[1]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		
		// テストコード実行
		Map<String, String> category11Map = new HashMap<>();
		category11Map.put("key111", "あいうえお");

		Map<String, String> category21Map = new HashMap<>();
		category21Map.put("key211", "さしすせそ");
		category21Map.put("key212", "たちつてと");
		
		
		Map<String, Map<String, String>> rootMap = new HashMap<>();
		rootMap.put("category11", category11Map);
		rootMap.put("category21", category21Map);


		this.housingManage.updExtInfo((String)housingId[0], rootMap, "dummyID");


		// 結果の確認
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key1");

		List<HousingExtInfo> list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("更新前のデータが存在しない事", 0, list.size());

		
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key2");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("更新前のデータが存在しない事", 0, list.size());
		

		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category2");
		criteria.addWhereClause("keyName", "key1");

		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("更新前のデータが存在しない事", 0, list.size());


		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category11");
		criteria.addWhereClause("keyName", "key111");
		criteria.addWhereClause("dataValue", "あいうえお");

		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("更新後のデータが存在する事", 1, list.size());
		

		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category21");
		criteria.addWhereClause("keyName", "key211");
		criteria.addWhereClause("dataValue", "さしすせそ");

		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("更新後のデータが存在する事", 1, list.size());


		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category21");
		criteria.addWhereClause("keyName", "key212");
		criteria.addWhereClause("dataValue", "たちつてと");

		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("更新後のデータが存在する事", 1, list.size());

		
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[1]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key1");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertNull("更新対象外が更新されていない事", list.get(0).getDataValue());

	}


	
	/**
	 * システム物件CD単位の更新処理のテスト（該当レコードが存在する場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>map が空の場合、対象のシステム物件CD配下が削除される事</li>
	 *     <li>対象外のシステム物件CD配下が入れ替わらない事</li>
	 * </ul>
	 */
	@Test
	public void updExtInfoHCDLevelTest2() throws NotFoundException, Exception{

		// ダミーデータ作成
		HousingExtInfo housingExtInfo;
		
		// １件目（更新対象）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});
		
		// ２件目（更新対象 key 違い）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key2");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// ３件目（更新対象 category 違い）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category2");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// ４件目（更新対象外 sysHousingCd 違い）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[1]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		
		// テストコード実行
		Map<String, Map<String, String>> rootMap = new HashMap<>();


		this.housingManage.updExtInfo((String)housingId[0], rootMap, "dummyID");


		// 結果の確認
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key1");

		List<HousingExtInfo> list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("更新前のデータが存在しない事", 0, list.size());

		
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key2");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("更新前のデータが存在しない事", 0, list.size());
		

		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category2");
		criteria.addWhereClause("keyName", "key1");

		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("更新前のデータが存在しない事", 0, list.size());


		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[1]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key1");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertNull("更新対象外が更新されていない事", list.get(0).getDataValue());

	}

	
	
	/**
	 * システム物件CD単位の更新処理のテスト（該当レコードが存在する場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>map が null の場合、対象のシステム物件CD配下が削除される事</li>
	 *     <li>対象外のシステム物件CD配下が入れ替わらない事</li>
	 * </ul>
	 */
	@Test
	public void updExtInfoHCDLevelTest3() throws NotFoundException, Exception{

		// ダミーデータ作成
		HousingExtInfo housingExtInfo;
		
		// １件目（更新対象）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});
		
		// ２件目（更新対象 key 違い）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key2");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// ３件目（更新対象 category 違い）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category2");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// ４件目（更新対象外 sysHousingCd 違い）
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[1]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		
		// テストコード実行
		this.housingManage.updExtInfo((String)housingId[0], null, "dummyID");


		// 結果の確認
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key1");

		List<HousingExtInfo> list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("更新前のデータが存在しない事", 0, list.size());

		
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key2");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("更新前のデータが存在しない事", 0, list.size());
		

		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category2");
		criteria.addWhereClause("keyName", "key1");

		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("更新前のデータが存在しない事", 0, list.size());


		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[1]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key1");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertNull("更新対象外が更新されていない事", list.get(0).getDataValue());

	}



	/**
	 * システム物件CD単位の更新処理のテスト（親レコードなし）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>NotFoundException がスローされる事</li>
	 * </ul>
	 */
	@Test(expected=NotFoundException.class)
	public void updExtInfoFailTest() throws NotFoundException, Exception{

		 Map<String, Map<String, String>> cateMap = new HashMap<>();
		 
		 Map<String, String> keyMap = new HashMap<>();
		 keyMap.put("key", "value");
		 cateMap.put("category", keyMap);
		 
		
		// 存在しないシステム物件CD でテストコード実行
		this.housingManage.updExtInfo("XXXXXXXXXX", cateMap, "dummyID");
		
	}
}
