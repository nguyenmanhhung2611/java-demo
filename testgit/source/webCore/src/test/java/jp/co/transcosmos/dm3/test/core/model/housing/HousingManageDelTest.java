package jp.co.transcosmos.dm3.test.core.model.housing;

import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.HousingManage;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingFormFactory;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.HousingEquipInfo;
import jp.co.transcosmos.dm3.core.vo.HousingExtInfo;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingPartInfo;
import jp.co.transcosmos.dm3.core.vo.HousingStatusInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.utils.DateUtils;
import jp.co.transcosmos.dm3.utils.StringUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 物件情報 model 削除系処理のテスト
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class HousingManageDelTest {

	@Autowired
	private DAO<HousingInfo> housingInfoDAO;
	@Autowired
	private DAO<HousingDtlInfo> housingDtlInfoDAO;
	@Autowired
	private DAO<HousingStatusInfo> housingStatusInfoDAO;
	@Autowired
	private DAO<HousingImageInfo> housingImageInfoDAO;
	@Autowired
	private DAO<BuildingInfo> buildingInfoDAO;
	@Autowired
	private DAO<HousingEquipInfo> housingEquipInfoDAO;
	@Autowired
	private DAO<HousingExtInfo> housingExtInfoDAO;
	@Autowired
	private DAO<HousingPartInfo> housingPartInfoDAO;

	@Autowired
	private HousingFormFactory formFactory;

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
		
		// 建物基本情報１
		BuildingInfo buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SYSBLD0001");
		buildingInfo.setDisplayBuildingName("建物名１");
		buildingInfo.setPrefCd("13");

		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		
		// 建物基本情報２
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SYSBLD0002");
		buildingInfo.setDisplayBuildingName("建物名２");
		buildingInfo.setPrefCd("14");

		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

	}



	/**
	 * 物件情報の削除
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>削除対象となる物件基本情報が削除されている事</li>
	 *     <li>削除対象となる物件詳細情報が削除されている事</li>
	 *     <li>削除対象となる物件ステータス情報が削除されている事</li>
	 *     <li>削除対象となる物件こだわり条件情報が削除されている事</li>
	 *     <li>削除対象となる物件設備情報が削除されている事</li>
	 *     <li>削除対象となる物件拡張情報が削除されている事</li>
	 *     <li>削除対象となる物件画像情報が削除されている事</li>
	 * </ul>
	 */
	@Test
	public void delHousingTest() throws Exception {
		
		// テストデータ作成
		initData();

		// フォーム作成
		HousingForm form = this.formFactory.createHousingForm();
		form.setSysHousingCd("SYSHOU00001");

		// テスト対象メソッド実行
		this.housingManage.delHousingInfo(form, "dummy");
		

		// テスト結果確認
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00001");
		
		// 削除対象データが存在しない事
		Assert.assertEquals("該当する物件基本情報が削除されている事", 0, this.housingInfoDAO.selectByFilter(criteria).size());
		Assert.assertEquals("該当する物件詳細情報が削除されている事", 0, this.housingDtlInfoDAO.selectByFilter(criteria).size());
		Assert.assertEquals("該当する物件ステータス情報が削除されている事", 0, this.housingStatusInfoDAO.selectByFilter(criteria).size());
		Assert.assertEquals("該当する物件こだわり情報が削除されている事", 0, this.housingPartInfoDAO.selectByFilter(criteria).size());
		Assert.assertEquals("該当する物件設備情報が削除されている事", 0, this.housingEquipInfoDAO.selectByFilter(criteria).size());
		Assert.assertEquals("該当する物件拡張属性情報が削除されている事", 0, this.housingExtInfoDAO.selectByFilter(criteria).size());
		Assert.assertEquals("該当する物件画像情報が削除されている事", 0, this.housingImageInfoDAO.selectByFilter(criteria).size());

		
		// 削除対象外データが存在する事
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00002");
		
		Assert.assertEquals("対象外の物件基本情報が削除されていない事", 1, this.housingInfoDAO.selectByFilter(criteria).size());
		Assert.assertEquals("対象外の物件詳細情報が削除されていない事", 1, this.housingDtlInfoDAO.selectByFilter(criteria).size());
		Assert.assertEquals("対象外の物件ステータス情報が削除されていない事", 1, this.housingStatusInfoDAO.selectByFilter(criteria).size());
		Assert.assertEquals("対象外の物件こだわり情報が削除されていない事", 2, this.housingPartInfoDAO.selectByFilter(criteria).size());
		Assert.assertEquals("対象外の物件設備情報が削除されていない事", 2, this.housingEquipInfoDAO.selectByFilter(criteria).size());
		Assert.assertEquals("対象外の物件拡張属性情報が削除されていない事", 3, this.housingExtInfoDAO.selectByFilter(criteria).size());
		Assert.assertEquals("対象外の物件画像情報が削除されていない事", 2, this.housingImageInfoDAO.selectByFilter(criteria).size());
		
	}
	
	

	/**
	 * 物件画像情報の削除
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>削除フラグが設定されている物件画像情報が削除されている事</li>
	 *     <li>削除フラグが設定されていない物件画像情報が削除されていない事</li>
	 *     <li>枝番が再構築されている事</li>
	 *     <li>関連する物件基本情報の更新日、更新者が更新されている事。</li>
	 *     <li>関連しない物件基本情報の更新日、更新者が更新されていない事。</li>
	 * </ul>
	 */
	@Test
	public void delHousingImgTest() throws Exception {
		
		// テストデータ作成
		initData();
		

		// テスト対象メソッド実行
		this.housingManage.delHousingImg("SYSHOU00001", "00", 2, "editUserId1");


		// 実行結果確認　（削除対象システム物件CD）
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00001");
		criteria.addOrderByClause("imageType");
		criteria.addOrderByClause("divNo");

		List<HousingImageInfo> list = this.housingImageInfoDAO.selectByFilter(criteria);

		Assert.assertEquals("１件削除されている事", 1, list.size());
		Assert.assertEquals("削除されたのは指定された枝番のデータである事", "FILE00001_01", list.get(0).getFileName());


		// 実行結果確認　（削除対象外のシステム物件CD）
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00002");

		list = this.housingImageInfoDAO.selectByFilter(criteria);
		
		Assert.assertEquals("削除されていない事", 2, list.size());


	
		// 物件基本情報のタイムスタンプ確認（削除対象）
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00001");
		String day10 = StringUtils.dateToString(DateUtils.addDays(new Date(), -10));
		String date = StringUtils.dateToString(housingInfo.getInsDate());
		Assert.assertEquals("登録日が更新されていない事", day10, date);
		Assert.assertEquals("登録者が更新されていない事", "dummInsID1", housingInfo.getInsUserId());
		
		String now = StringUtils.dateToString(new Date());
		date = StringUtils.dateToString(housingInfo.getUpdDate());
		Assert.assertEquals("更新日が更新されている事", now, date);
		Assert.assertEquals("更新者が更新されている事", "editUserId1", housingInfo.getUpdUserId());
		
		
		// 物件基本情報のタイムスタンプ確認（削除対象外）
		housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00002");
		date = StringUtils.dateToString(housingInfo.getInsDate());
		Assert.assertEquals("登録日が更新されていない事", day10, date);
		Assert.assertEquals("登録者が更新されていない事", "dummInsID2", housingInfo.getInsUserId());
		
		Assert.assertNull("更新日が更新されていない事", housingInfo.getUpdDate());
		Assert.assertNull("更新者が更新されていない事", housingInfo.getUpdUserId());

	}
	

	
	/**
	 * 物件画像情報の削除 （メイン画像フラグの更新確認）
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>削除対象がメイン画像だった場合、別の画像にメイン画像フラグが設定されている事</li>
	 * </ul>
	 */
	@Test
	public void delHousingImgMainFlgTest() throws Exception {
		
		// テストデータ作成
		initData();
		

		// テスト対象メソッド実行
		this.housingManage.delHousingImg("SYSHOU00001", "00", 1, "editUserId1");


		// 実行結果確認　（削除対象システム物件CD）
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00001");
		criteria.addWhereClause("imageType", "00");

		List<HousingImageInfo> list = this.housingImageInfoDAO.selectByFilter(criteria);

		Assert.assertEquals("１件削除されている事", 1, list.size());
		Assert.assertEquals("削除されたのは指定された枝番のデータである事", "FILE00001_02", list.get(0).getFileName());
		Assert.assertEquals("２番目の枝番データがメイン画像に設定されている事", "1", list.get(0).getMainImageFlg());
		Assert.assertEquals("枝番が一つ前の値に更新されている事", Integer.valueOf(1), list.get(0).getDivNo());

	}

	
	
	// テストデータ登録
	private void initData() {

		HousingInfo housingInfo;
		HousingDtlInfo housingDtlInfo;
		HousingStatusInfo housingStatusInfo;
		HousingImageInfo[] housingImageInfo = new HousingImageInfo[2];
		HousingEquipInfo[] housingEquipInfo = new HousingEquipInfo[2];
		HousingExtInfo[] housingExtInfo = new HousingExtInfo[3];
		HousingPartInfo[] housingPartInfo = new HousingPartInfo[2]; 

		
		
		//　物件基本情報（更新対象）
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SYSHOU00001");
		housingInfo.setSysBuildingCd("SYSBLD0001");
		housingInfo.setDisplayHousingName("OLD物件名１");
		Date date = new Date();
		housingInfo.setInsDate(DateUtils.addDays(date, -10));
		housingInfo.setInsUserId("dummInsID1");

		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});
		
		// 物件詳細情報
		housingDtlInfo = new HousingDtlInfo();
		housingDtlInfo.setSysHousingCd("SYSHOU00001");

		this.housingDtlInfoDAO.insert(new HousingDtlInfo[]{housingDtlInfo});

		// 物件ステータス情報
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SYSHOU00001");
		housingStatusInfo.setHiddenFlg("0");

		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// 物件画像情報　（２件）
		housingImageInfo[0] = new HousingImageInfo();
		housingImageInfo[0].setSysHousingCd("SYSHOU00001");
		housingImageInfo[0].setImageType("00");
		housingImageInfo[0].setDivNo(1);
		housingImageInfo[0].setMainImageFlg("1");
		housingImageInfo[0].setPathName("PATH00001_01");
		housingImageInfo[0].setFileName("FILE00001_01");

		housingImageInfo[1] = new HousingImageInfo();
		housingImageInfo[1].setSysHousingCd("SYSHOU00001");
		housingImageInfo[1].setImageType("00");
		housingImageInfo[1].setDivNo(2);
		housingImageInfo[1].setMainImageFlg("0");
		housingImageInfo[1].setPathName("PATH00001_02");
		housingImageInfo[1].setFileName("FILE00001_02");

		this.housingImageInfoDAO.insert(housingImageInfo);

		// 物件設備情報
		housingEquipInfo[0] = new HousingEquipInfo();
		housingEquipInfo[0].setSysHousingCd("SYSHOU00001");
		housingEquipInfo[0].setEquipCd("000001");

		housingEquipInfo[1] = new HousingEquipInfo();
		housingEquipInfo[1].setSysHousingCd("SYSHOU00001");
		housingEquipInfo[1].setEquipCd("000002");
				
		this.housingEquipInfoDAO.insert(housingEquipInfo);;

		// 物件拡張属性情報
		housingExtInfo[0] = new HousingExtInfo();
		housingExtInfo[0].setSysHousingCd("SYSHOU00001");
		housingExtInfo[0].setCategory("CATE0001");
		housingExtInfo[0].setKeyName("KEY0001");
		housingExtInfo[0].setDataValue("VALUE00001");

		housingExtInfo[1] = new HousingExtInfo();
		housingExtInfo[1].setSysHousingCd("SYSHOU00001");
		housingExtInfo[1].setCategory("CATE0001");
		housingExtInfo[1].setKeyName("KEY0002");
		housingExtInfo[1].setDataValue("VALUE00002");

		housingExtInfo[2] = new HousingExtInfo();
		housingExtInfo[2].setSysHousingCd("SYSHOU00001");
		housingExtInfo[2].setCategory("CATE0002");
		housingExtInfo[2].setKeyName("KEY0001");
		housingExtInfo[2].setDataValue("VALUE00003");

		this.housingExtInfoDAO.insert(housingExtInfo);

		// 物件こだわり条件情報
		housingPartInfo[0] = new HousingPartInfo();
		housingPartInfo[0].setSysHousingCd("SYSHOU00001");
		housingPartInfo[0].setPartSrchCd("B05");

		housingPartInfo[1] = new HousingPartInfo();
		housingPartInfo[1].setSysHousingCd("SYSHOU00001");
		housingPartInfo[1].setPartSrchCd("B10");

		this.housingPartInfoDAO.insert(housingPartInfo);
		
		
		
		//　物件基本情報（更新対象外）
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SYSHOU00002");
		housingInfo.setSysBuildingCd("SYSBLD0001");
		housingInfo.setDisplayHousingName("OLD物件名２");
		housingInfo.setInsDate(DateUtils.addDays(date, -10));
		housingInfo.setInsUserId("dummInsID2");

		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// 物件詳細情報
		housingDtlInfo = new HousingDtlInfo();
		housingDtlInfo.setSysHousingCd("SYSHOU00002");
		
		this.housingDtlInfoDAO.insert(new HousingDtlInfo[]{housingDtlInfo});
		
		// 物件ステータス情報
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SYSHOU00002");
		housingStatusInfo.setHiddenFlg("0");

		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// 物件画像情報　（２件）
		housingImageInfo[0] = new HousingImageInfo();
		housingImageInfo[0].setSysHousingCd("SYSHOU00002");
		housingImageInfo[0].setImageType("00");
		housingImageInfo[0].setDivNo(1);
		housingImageInfo[0].setMainImageFlg("1");
		housingImageInfo[0].setPathName("PATH00002_01");
		housingImageInfo[0].setFileName("FILE00002_01");

		housingImageInfo[1] = new HousingImageInfo();
		housingImageInfo[1].setSysHousingCd("SYSHOU00002");
		housingImageInfo[1].setImageType("00");
		housingImageInfo[1].setDivNo(2);
		housingImageInfo[1].setMainImageFlg("0");
		housingImageInfo[1].setPathName("PATH00002_02");
		housingImageInfo[1].setFileName("FILE00002_02");

		this.housingImageInfoDAO.insert(housingImageInfo);

		// 物件設備情報
		housingEquipInfo[0] = new HousingEquipInfo();
		housingEquipInfo[0].setSysHousingCd("SYSHOU00002");
		housingEquipInfo[0].setEquipCd("000001");

		housingEquipInfo[1] = new HousingEquipInfo();
		housingEquipInfo[1].setSysHousingCd("SYSHOU00002");
		housingEquipInfo[1].setEquipCd("000002");
				
		this.housingEquipInfoDAO.insert(housingEquipInfo);;

		// 物件拡張属性情報
		housingExtInfo[0] = new HousingExtInfo();
		housingExtInfo[0].setSysHousingCd("SYSHOU00002");
		housingExtInfo[0].setCategory("CATE0001");
		housingExtInfo[0].setKeyName("KEY0001");
		housingExtInfo[0].setDataValue("VALUE10001");

		housingExtInfo[1] = new HousingExtInfo();
		housingExtInfo[1].setSysHousingCd("SYSHOU00002");
		housingExtInfo[1].setCategory("CATE0001");
		housingExtInfo[1].setKeyName("KEY0002");
		housingExtInfo[1].setDataValue("VALUE10002");

		housingExtInfo[2] = new HousingExtInfo();
		housingExtInfo[2].setSysHousingCd("SYSHOU00002");
		housingExtInfo[2].setCategory("CATE0002");
		housingExtInfo[2].setKeyName("KEY0001");
		housingExtInfo[2].setDataValue("VALUE10003");

		this.housingExtInfoDAO.insert(housingExtInfo);

		// 物件こだわり条件情報
		housingPartInfo[0] = new HousingPartInfo();
		housingPartInfo[0].setSysHousingCd("SYSHOU00002");
		housingPartInfo[0].setPartSrchCd("FRE");

		housingPartInfo[1] = new HousingPartInfo();
		housingPartInfo[1].setSysHousingCd("SYSHOU00002");
		housingPartInfo[1].setPartSrchCd("REI");

		this.housingPartInfoDAO.insert(housingPartInfo);

	}
}
