package jp.co.transcosmos.dm3.test.core.model.housing;

import java.math.BigDecimal;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.HousingManage;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingDtlForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingEquipForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingFormFactory;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.HousingEquipInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingPartInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.testUtil.mock.MockPartDataCreator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * 物件情報 model こだわり条件作成、サムネイル作成 Proxy のテストケース
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class HousingPartThumbnailProxyTest {

	@Qualifier("housingManager")
	@Autowired
	private HousingManage housingManage;

	@Autowired
	private DAO<HousingInfo> housingInfoDAO;
	@Autowired
	private DAO<HousingDtlInfo> housingDtlInfoDAO;
	@Autowired
	private DAO<HousingPartInfo> housingPartInfoDAO;
	@Autowired
	private DAO<HousingEquipInfo> housingEquipInfoDAO;
	@Autowired
	private DAO<BuildingInfo> buildingInfoDAO;

	@Autowired
	private HousingFormFactory formFactory;

	// テストコード用の、こだわり条件を作成する mock クラス
	@Qualifier("mockPartCreator")
	@Autowired
	private MockPartDataCreator mockPartDataCreator;

	
	
	// 前処理
	@Before
	public void init(){
		// 物件情報全件削除
		DAOCriteria criteria = new DAOCriteria();
		this.housingPartInfoDAO.deleteByFilter(criteria);
		this.housingInfoDAO.deleteByFilter(criteria);
		this.buildingInfoDAO.deleteByFilter(criteria);

		initTestData();
	}



	/**
	 * 物件新規登録時のこだわり条件作成処理のテスト
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>物件基本情報が登録されている事</li>
	 *     <li>物件情報作成時にこだわり条件が作成されている事</li>
	 * </ul>
	 */
	@Test
	public void addHousingMakeTest() throws Exception{

		// こだわり条件を作成する mock に、テスト対象メソッドを実行対象として設定する。
		this.mockPartDataCreator.setExecuteMethod("addHousing");
		

		HousingForm inputForm = this.formFactory.createHousingForm();
		inputForm.setDisplayHousingName("HNAME0003");					// 表示用物件名
		inputForm.setSysBuildingCd("SBLD00001");						// システム建物CD

		// テスト対象メソッド実行
		String id = this.housingManage.addHousing(inputForm, "editUserId1");


		// DBの整合性制約により、物件基本情報が登録されていないとこだわり条件が登録できないので
		// 物件基本情報登録の明示的なチェックは行わない。
		
		// 実行結果の取得
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", id);

		List<HousingPartInfo> list = this.housingPartInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("こだわり条件が作成されている事", list.get(0).getPartSrchCd(), "S20");
	}

	
	
	/**
	 * 物件新規登録時のこだわり条件作成スキップ処理のテスト
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>物件情報作成時にこだわり条件が作成されていない事</li>
	 * </ul>
	 */
	@Test
	public void addHousingSkipTest() throws Exception{

		// こだわり条件を作成する mock に、テスト対象メソッドを実行対象として設定しない。
		this.mockPartDataCreator.setExecuteMethod("addHousing_");
		

		HousingForm inputForm = this.formFactory.createHousingForm();
		inputForm.setDisplayHousingName("HNAME0003");					// 表示用物件名
		inputForm.setSysBuildingCd("SBLD00001");						// システム建物CD

		// テスト対象メソッド実行
		String id = this.housingManage.addHousing(inputForm, "editUserId1");


		// 実行結果の取得
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", id);

		List<HousingInfo> infoList = this.housingInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("委譲先のメソッドが実行されている事", 1, infoList.size());

		List<HousingPartInfo> partList = this.housingPartInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("こだわり条件が作成されていない事", 0, partList.size());
	}


	
	/**
	 * 物件基本情報更新時のこだわり条件作成処理のテスト
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>委譲先（物件基本情報の更新処理）が実行されている事</li>
	 *     <li>物件基本情報更新時にこだわり条件が作成されている事</li>
	 * </ul>
	 */
	@Test
	public void updateHousingMakeTest() throws Exception{

		// こだわり条件を作成する mock に、テスト対象メソッドを実行対象として設定する。
		this.mockPartDataCreator.setExecuteMethod("updateHousing");
		

		// 物件名を更新
		HousingForm inputForm = this.formFactory.createHousingForm();
		inputForm.setSysHousingCd("SHOU00001");							// システム物件CD
		inputForm.setDisplayHousingName("HNAME1001");					// 表示用物件名
		inputForm.setSysBuildingCd("SBLD00001");						// システム建物CD

		// テスト対象メソッド実行
		this.housingManage.updateHousing(inputForm, "editUserId1");


		// 実行結果の取得
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", inputForm.getSysHousingCd());

		
		List<HousingInfo> infoList = this.housingInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("委譲先のメソッドが実行されている事", infoList.get(0).getDisplayHousingName(), "HNAME1001");

		List<HousingPartInfo> partList = this.housingPartInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("こだわり条件が作成されている事", partList.get(0).getPartSrchCd(), "S20");
	}

	
	
	/**
	 * 物件基本情報更新時のこだわり条件作成スキップ処理のテスト
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>物件基本情報更新時にこだわり条件が作成されていない事</li>
	 * </ul>
	 */
	@Test
	public void updateHousingSkipTest() throws Exception{

		// こだわり条件を作成する mock に、テスト対象メソッドを実行対象として設定しない。
		this.mockPartDataCreator.setExecuteMethod("updateHousing_");


		// 物件名を更新
		HousingForm inputForm = this.formFactory.createHousingForm();
		inputForm.setSysHousingCd("SHOU00001");							// システム物件CD
		inputForm.setDisplayHousingName("HNAME1001");					// 表示用物件名
		inputForm.setSysBuildingCd("SBLD00001");						// システム建物CD

		// テスト対象メソッド実行
		this.housingManage.updateHousing(inputForm, "editUserId1");


		// 実行結果の取得
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", inputForm.getSysHousingCd());

		
		List<HousingInfo> infoList = this.housingInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("委譲先のメソッドが実行されている事", infoList.get(0).getDisplayHousingName(), "HNAME1001");

		List<HousingPartInfo> partList = this.housingPartInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("こだわり条件が作成されていない事", 0, partList.size());
	}



	/**
	 * 物件情報削除時のこだわり条件作成処理のテスト
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>委譲先（物件基本情報の削除処理）が実行されている事</li>
	 * </ul>
	 */
	@Test
	public void delHousingInfoTest() throws Exception{

		// 削除時は、こだわり条件が作成できないので、委譲先が実行されている事のみチェックする。
		this.mockPartDataCreator.setExecuteMethod("delHousingInfo");
		
		HousingForm inputForm = this.formFactory.createHousingForm();
		inputForm.setSysHousingCd("SHOU00001");							// システム物件CD

		// テスト対象メソッド実行
		this.housingManage.delHousingInfo(inputForm, "editUserId1");


		// 実行結果の取得
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", inputForm.getSysHousingCd());

		
		List<HousingInfo> infoList = this.housingInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("委譲先のメソッドが実行されている事", 0, infoList.size());

	}



	/**
	 * 物件詳細情報更新時のこだわり条件作成処理のテスト
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>委譲先（物件詳細情報の更新処理）が実行されている事</li>
	 *     <li>物件詳細情報更新時にこだわり条件が作成されている事</li>
	 * </ul>
	 */
	@Test
	public void updateHousingDtlMakeTest() throws Exception{

		// こだわり条件を作成する mock に、テスト対象メソッドを実行対象として設定する。
		this.mockPartDataCreator.setExecuteMethod("updateHousingDtl");
		

		// 物件名を更新
		HousingDtlForm inputForm = this.formFactory.createHousingDtlForm();
		inputForm.setSysHousingCd("SHOU00001");							// システム物件CD
		inputForm.setRenewDue("100.5");

		// テスト対象メソッド実行
		this.housingManage.updateHousingDtl(inputForm, "editUserId1");


		// 実行結果の取得
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", inputForm.getSysHousingCd());

		
		List<HousingDtlInfo> dtlList = this.housingDtlInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("委譲先のメソッドが実行されている事", dtlList.get(0).getRenewDue(), new BigDecimal("100.50"));

		List<HousingPartInfo> partList = this.housingPartInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("こだわり条件が作成されている事", partList.get(0).getPartSrchCd(), "S20");
	}

	
	
	
	/**
	 * 物件詳細情報更新時のこだわり条件作成スキップ処理のテスト
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>物件詳細情報更新時にこだわり条件が作成されていない事</li>
	 * </ul>
	 */
	@Test
	public void updateHousingDtlSkipTest() throws Exception{

		// こだわり条件を作成する mock に、テスト対象メソッドを実行対象として設定しない。
		this.mockPartDataCreator.setExecuteMethod("updateHousingDtl_");


		HousingDtlForm inputForm = this.formFactory.createHousingDtlForm();
		inputForm.setSysHousingCd("SHOU00001");							// システム物件CD
		inputForm.setRenewDue("100.5");

		// テスト対象メソッド実行
		this.housingManage.updateHousingDtl(inputForm, "editUserId1");


		// 実行結果の取得
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", inputForm.getSysHousingCd());

		
		List<HousingDtlInfo> dtlList = this.housingDtlInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("委譲先のメソッドが実行されている事", dtlList.get(0).getRenewDue(), new BigDecimal("100.50"));

		List<HousingPartInfo> partList = this.housingPartInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("こだわり条件が作成されていない事", 0, partList.size());
	}



	/**
	 * 物件設備情報更新時のこだわり条件作成処理のテスト
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>委譲先（物件設備情報の更新処理）が実行されている事</li>
	 *     <li>物件設備情報更新時にこだわり条件が作成されている事</li>
	 * </ul>
	 */
	@Test
	public void updateHousingEquipMakeTest() throws Exception{

		// こだわり条件を作成する mock に、テスト対象メソッドを実行対象として設定する。
		this.mockPartDataCreator.setExecuteMethod("updateHousingEquip");


		HousingEquipForm inputForm = this.formFactory.createHousingEquipForm();
		inputForm.setSysHousingCd("SHOU00001");							// システム物件CD
		inputForm.setEquipCd(new String[]{"000001"});					// 設備CD


		// テスト対象メソッド実行
		this.housingManage.updateHousingEquip(inputForm, "editUserId1");


		// 実行結果の取得
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", inputForm.getSysHousingCd());


		List<HousingEquipInfo> equList = this.housingEquipInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("委譲先のメソッドが実行されている事", equList.get(0).getEquipCd(), "000001");

		criteria.addWhereClause("partSrchCd", "S20");
		List<HousingPartInfo> partList = this.housingPartInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("こだわり条件が作成されている事", 1, partList.size());
	}
	
	
	
	/**
	 * 物件設備情報更新時のこだわり条件作成スキップ処理のテスト
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>物件詳細情報更新時にこだわり条件が作成されていない事</li>
	 * </ul>
	 */
	@Test
	public void updateHousingEquipSkipTest() throws Exception{

		// こだわり条件を作成する mock に、テスト対象メソッドを実行対象として設定しない。
		this.mockPartDataCreator.setExecuteMethod("updateHousingEquip_");


		HousingEquipForm inputForm = this.formFactory.createHousingEquipForm();
		inputForm.setSysHousingCd("SHOU00001");							// システム物件CD
		inputForm.setEquipCd(new String[]{"000001"});					// 設備CD


		// テスト対象メソッド実行
		this.housingManage.updateHousingEquip(inputForm, "editUserId1");


		// 実行結果の取得
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", inputForm.getSysHousingCd());


		List<HousingEquipInfo> equList = this.housingEquipInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("委譲先のメソッドが実行されている事", equList.get(0).getEquipCd(), "000001");

		criteria.addWhereClause("partSrchCd", "S20");
		List<HousingPartInfo> partList = this.housingPartInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("こだわり条件が作成されていない事", 0, partList.size());
	}
	
	
	
	
	

	// テストデータ作成
	private void initTestData(){

		BuildingInfo buildingInfo;
		HousingInfo housingInfo;


		// 建物基本情報（更新対象）
		buildingInfo = new BuildingInfo();

		buildingInfo.setSysBuildingCd("SBLD00001");
		buildingInfo.setDisplayBuildingName("建物名０１");
		buildingInfo.setPrefCd("01");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// 物件基本情報
		housingInfo = new HousingInfo();

		housingInfo.setSysHousingCd("SHOU00001");
		housingInfo.setSysBuildingCd("SBLD00001");
		housingInfo.setDisplayHousingName("物件名０１");
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});
		


		// 建物基本情報（更新対象外）
		buildingInfo = new BuildingInfo();

		buildingInfo.setSysBuildingCd("SBLD00002");
		buildingInfo.setDisplayBuildingName("建物名０２");
		buildingInfo.setPrefCd("02");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});
		
		// 物件基本情報
		housingInfo = new HousingInfo();

		housingInfo.setSysHousingCd("SHOU00002");
		housingInfo.setSysBuildingCd("SBLD00002");
		housingInfo.setDisplayHousingName("物件名０２");
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		
	}
	
}
