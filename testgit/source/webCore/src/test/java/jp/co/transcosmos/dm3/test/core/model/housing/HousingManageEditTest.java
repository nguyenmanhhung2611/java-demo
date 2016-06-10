package jp.co.transcosmos.dm3.test.core.model.housing;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.HousingManage;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingDtlForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingEquipForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingFormFactory;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingImgForm;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.HousingEquipInfo;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingStatusInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.utils.DateUtils;
import jp.co.transcosmos.dm3.utils.StringUtils;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 物件情報 model 更新系処理のテスト
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class HousingManageEditTest {

	@Autowired
	private DAO<BuildingInfo> buildingInfoDAO;
	@Autowired
	private DAO<HousingInfo> housingInfoDAO;
	@Autowired
	private DAO<HousingDtlInfo> housingDtlInfoDAO;
	@Autowired
	private DAO<HousingStatusInfo> housingStatusInfoDAO;
	@Autowired
	private DAO<HousingEquipInfo> housingEquipInfoDAO;
	@Autowired
	private DAO<HousingImageInfo> housingImageInfoDAO;

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
	 * 物件基本情報の新規登録（全フィールド入力時）
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>メソッドの戻り値が採番されたシステム物件CD である事</li>
	 *     <li>登録値が入力値と一致する事</li>
	 * </ul>
	 */
	@Test
	public void addHousingFullTest() throws Exception {

		// 入力フォームを生成して初期値を設定
		HousingForm inputForm = this.formFactory.createHousingForm();

		inputForm.setHousingCd("HOU0001");								// 物件番号
		inputForm.setDisplayHousingName("HNAME0001");					// 表示用物件名
		inputForm.setDisplayHousingNameKana("HNAME0001K");				// 表示用物件名（カナ）
		inputForm.setRoomNo("R001");									// 部屋番号
		inputForm.setSysBuildingCd("SYSBLD0001");						// システム建物CD
		inputForm.setPrice("100000");									// 賃料・価格
		inputForm.setUpkeep("10000");									// 管理費
		inputForm.setCommonAreaFee("20000");							// 共益費
		inputForm.setMenteFee("30000");									// 修繕積立費
		inputForm.setSecDeposit("40000.01");							// 敷金
		inputForm.setSecDepositCrs("S");								// 敷金単位
		inputForm.setBondChrg("60000.01");								// 保証金
		inputForm.setBondChrgCrs("B");									// 保証金単位
		inputForm.setDepositDiv("ZZ");									// 敷引礼金区分
		inputForm.setDeposit("90000.01");								// 敷引礼金額
		inputForm.setDepositCrs("D");									// 敷引礼金単位
		inputForm.setLayoutCd("L01");									// 間取CD
		inputForm.setLayoutComment("LAYOUT_NOTE1");						// 間取詳細コメント
		inputForm.setFloorNo("110");									// 物件の階数
		inputForm.setFloorNoNote("FLOOR0001");							// 物件の階数コメント
		inputForm.setLandArea("111.01"); 								// 土地面積
		inputForm.setLandAreaMemo("LAND_NOTE1");						// 土地面積_補足
		inputForm.setPersonalArea("112.01");							// 専有面積
		inputForm.setPersonalAreaMemo("PER_NOTE1");						// 専有面積_補足
		inputForm.setMoveinFlg("9");									// 入居状態フラグ
		inputForm.setParkingSituation("YY"); 							// 駐車場の状況
		inputForm.setParkingEmpExist("N");								// 駐車場空の有無
		inputForm.setDisplayParkingInfo("PARKING_001");					// 表示用駐車場情報
		inputForm.setWindowDirection("S");								// 窓の向き
		inputForm.setBasicComment("BASIC001");							// 基本情報コメント

		// テストクラス実行
		String id = this.housingManage.addHousing(inputForm, "addUserId1");
		
		// データのチェック
		Assert.assertNotNull("システム物件CD が採番されている事", id);

		// 物件基本情報のチェック
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK(id);

		// システム物件CD
		Assert.assertEquals("システム物件CD が採番された値と一致する事。", id, housingInfo.getSysHousingCd());
		// 物件番号
		Assert.assertEquals("物件番号が入力された値と一致する事。", "HOU0001", housingInfo.getHousingCd());
		// 表示用物件名
		Assert.assertEquals("表示用物件名が入力された値と一致する事。", "HNAME0001", housingInfo.getDisplayHousingName());
		// 表示用物件名（カナ）
		Assert.assertEquals("表示用物件名（カナ）が入力された値と一致する事。", "HNAME0001K", housingInfo.getDisplayHousingNameKana());
		// 部屋番号
		Assert.assertEquals("部屋番号が入力された値と一致する事。", "R001", housingInfo.getRoomNo());
		// システム建物CD
		Assert.assertEquals("システム建物CDが入力された値と一致する事。", "SYSBLD0001", housingInfo.getSysBuildingCd());
		// 賃料・価格
		Assert.assertEquals("賃料・価格が入力された値と一致する事。", Long.valueOf("100000"), housingInfo.getPrice());
		// 管理費
		Assert.assertEquals("管理費が入力された値と一致する事。", Long.valueOf("10000"), housingInfo.getUpkeep());
		// 共益費
		Assert.assertEquals("共益費が入力された値と一致する事。", Long.valueOf("20000"), housingInfo.getCommonAreaFee());
		// 修繕積立費
		Assert.assertEquals("修繕積立費が入力された値と一致する事。", Long.valueOf("30000"), housingInfo.getMenteFee());
		// 敷金
		Assert.assertEquals("敷金が入力された値と一致する事。", new BigDecimal("40000.01"), housingInfo.getSecDeposit());
		// 敷金単位
		Assert.assertEquals("敷金単位が入力された値と一致する事。", "S", housingInfo.getSecDepositCrs());
		// 保証金
		Assert.assertEquals("保証金が入力された値と一致する事。", new BigDecimal("60000.01"), housingInfo.getBondChrg());
		// 保証金単位
		Assert.assertEquals("保証金単位が入力された値と一致する事。", "B", housingInfo.getBondChrgCrs());
		// 敷引礼金区分
		Assert.assertEquals("敷引礼金区分が入力された値と一致する事。", "ZZ", housingInfo.getDepositDiv());
		// 敷引礼金額
		Assert.assertEquals("敷引礼金額が入力された値と一致する事。", new BigDecimal("90000.01"), housingInfo.getDeposit());
		// 敷引礼金単位
		Assert.assertEquals("敷引礼金単位が入力された値と一致する事。", "D", housingInfo.getDepositCrs());
		// 間取CD
		Assert.assertEquals("間取CDが入力された値と一致する事。", "L01", housingInfo.getLayoutCd());
		// 間取詳細コメント
		Assert.assertEquals("間取詳細コメントが入力された値と一致する事。", "LAYOUT_NOTE1", housingInfo.getLayoutComment());
		// 物件の階数
		Assert.assertEquals("物件の階数コメントが入力された値と一致する事。", Integer.valueOf("110"), housingInfo.getFloorNo());
		// 物件の階数コメント
		Assert.assertEquals("物件の階数コメントが入力された値と一致する事。", "FLOOR0001", housingInfo.getFloorNoNote());
		// 土地面積
		Assert.assertEquals("土地面積が入力された値と一致する事。", new BigDecimal("111.01"), housingInfo.getLandArea());
		// 土地面積_補足
		Assert.assertEquals("土地面積_補足が入力された値と一致する事。", "LAND_NOTE1", housingInfo.getLandAreaMemo());
		// 専有面積
		Assert.assertEquals("専有面積が入力された値と一致する事。", new BigDecimal("112.01"), housingInfo.getPersonalArea());
		// 専有面積_補足
		Assert.assertEquals("専有面積_補足が入力された値と一致する事。", "PER_NOTE1", housingInfo.getPersonalAreaMemo());
		// 入居状態フラグ
		Assert.assertEquals("入居状態フラグが入力された値と一致する事。", "9", housingInfo.getMoveinFlg());
		// 駐車場の状況
		Assert.assertEquals("駐車場の状況が入力された値と一致する事。", "YY", housingInfo.getParkingSituation());
		// 駐車場空の有無
		Assert.assertEquals("駐車場空の有無が入力された値と一致する事。", "N", housingInfo.getParkingEmpExist());
		// 表示用駐車場情報
		Assert.assertEquals("表示用駐車場情報が入力された値と一致する事。", "PARKING_001", housingInfo.getDisplayParkingInfo());
		// 窓の向き
		Assert.assertEquals("窓の向きが入力された値と一致する事。", "S", housingInfo.getWindowDirection());
		// 基本情報コメント
		Assert.assertEquals("基本情報コメントが入力された値と一致する事。", "BASIC001", housingInfo.getBasicComment());
		
		// アイコン情報　は、設備登録側で更新されるので、このタイミングでは null が登録される。
		Assert.assertNull("アイコン情報が null である事", housingInfo.getIconCd());

		// 登録日（日付部のみ）
		String now = StringUtils.dateToString(new Date());
		String date = StringUtils.dateToString(housingInfo.getInsDate());
		Assert.assertEquals("登録日がシステム日付と一致する事。", now, date);

		// 登録者
		Assert.assertEquals("登録者が引数で渡した値と一致する事。", "addUserId1", housingInfo.getInsUserId());

		// 最終更新日（日付部のみ）
		date = StringUtils.dateToString(housingInfo.getUpdDate());
		Assert.assertEquals("最終更新日がシステム日付と一致する事。", now, date);

		// 最終更新者
		Assert.assertEquals("最終更新者が引数で渡した値と一致する事。", "addUserId1", housingInfo.getUpdUserId());

	}
	
	
	
	/**
	 * 物件基本情報の新規登録（システム物件CDを指定した場合）
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>システム物件CD をフォームに設定しても値が自動採番される事</li>
	 * </ul>
	 */
	@Test
	public void addHousingBadCdTest() throws Exception{
		
		// 入力フォームを生成して初期値を設定
		HousingForm inputForm = this.formFactory.createHousingForm();

		// 登録時にシステム物件CD の値は使用されない。
		// テスト用にわざと値を設定しておく。
		inputForm.setSysHousingCd("DUMMY_SYS_CD");
		inputForm.setDisplayHousingName("HNAME0001");					// 表示用物件名
		inputForm.setSysBuildingCd("SYSBLD0001");						// システム建物CD


		// テストクラス実行
		String id = this.housingManage.addHousing(inputForm, "addUserId1");
		
		// データのチェック
		Assert.assertNotNull("システム物件CD が採番されている事", id);

		// 物件基本情報のチェック
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK(id);

		// システム物件CD
		Assert.assertEquals("システム物件CD が採番された値と一致する事。", id, housingInfo.getSysHousingCd());
		Assert.assertNotEquals("ダミーで設定したシステム物件CD とは値が異なる事", inputForm.getSysHousingCd(), housingInfo.getSysHousingCd());

	}
	
	
	
	/**
	 * 物件基本情報の新規登録（親となる建物CD が存在しない場合）
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>NotFoundException がスローされる事</li>
	 * </ul>
	 */
	@Test(expected=NotFoundException.class)
	public void addHousingFailTest() throws Exception {

		// 入力フォームを生成して初期値を設定
		HousingForm inputForm = this.formFactory.createHousingForm();

		// 存在しないシステム建物CD を設定
		inputForm.setSysBuildingCd("SYSBLD2001");

		inputForm.setDisplayHousingName("HNAME0001");					// 表示用物件名

		// テストクラス実行
		this.housingManage.addHousing(inputForm, "addUserId1");

	}
	
	
	/**
	 * 物件基本情報の新規登録（最低限の入力値）
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>メソッドの戻り値が採番されたシステム物件CD である事</li>
	 *     <li>登録値が入力値と一致する事</li>
	 * </ul>
	 */
	@Test
	public void addHousingMinTest() throws Exception {

		// 入力フォームを生成して初期値を設定
		HousingForm inputForm = this.formFactory.createHousingForm();

		// 登録時にシステム物件CD の値は使用されない。
		// テスト用にわざと値を設定しておく。
		inputForm.setSysHousingCd("DUMMY_SYS_CD");

		inputForm.setSysBuildingCd("SYSBLD0001");						// システム建物CD
		inputForm.setDisplayHousingName("HNAME0001");					// 表示用物件名

		// テストクラス実行
		String id = this.housingManage.addHousing(inputForm, "addUserId1");
		
		// データのチェック
		Assert.assertNotNull("システム物件CD が採番されている事", id);

		// 物件基本情報のチェック
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK(id);

		// システム物件CD
		Assert.assertEquals("システム物件CD が採番された値と一致する事。", id, housingInfo.getSysHousingCd());
		// 物件番号
		Assert.assertNull("物件番号が Null である事。", housingInfo.getHousingCd());
		// 表示用物件名
		Assert.assertEquals("表示用物件名が入力された値と一致する事。", "HNAME0001", housingInfo.getDisplayHousingName());
		// 表示用物件名（カナ）
		Assert.assertNull("表示用物件名（カナ）が Null である事。", housingInfo.getDisplayHousingNameKana());
		// 部屋番号
		Assert.assertNull("部屋番号が Null である事。", housingInfo.getRoomNo());
		// システム建物CD
		Assert.assertEquals("システム建物CDが入力された値と一致する事。", "SYSBLD0001", housingInfo.getSysBuildingCd());
		// 賃料・価格
		Assert.assertNull("賃料・価格が Null である事。", housingInfo.getPrice());
		// 管理費
		Assert.assertNull("管理費が Null である事。", housingInfo.getUpkeep());
		// 共益費
		Assert.assertNull("共益費が Null である事。", housingInfo.getCommonAreaFee());
		// 修繕積立費
		Assert.assertNull("修繕積立費が Null である事。", housingInfo.getMenteFee());
		// 敷金
		Assert.assertNull("敷金が Null である事。", housingInfo.getSecDeposit());
		// 敷金単位
		Assert.assertNull("敷金単位が Null である事。", housingInfo.getSecDepositCrs());
		// 保証金
		Assert.assertNull("保証金が Null である事。", housingInfo.getBondChrg());
		// 保証金単位
		Assert.assertNull("保証金単位が Null である事。", housingInfo.getBondChrgCrs());
		// 敷引礼金区分
		Assert.assertNull("敷引礼金区分が Null である事。", housingInfo.getDepositDiv());
		// 敷引礼金額
		Assert.assertNull("敷引礼金額が Null である事。", housingInfo.getDeposit());
		// 敷引礼金単位
		Assert.assertNull("敷引礼金単位が Null である事。", housingInfo.getDepositCrs());
		// 間取CD
		Assert.assertNull("間取CD が Null である事。", housingInfo.getLayoutCd());
		// 間取詳細コメント
		Assert.assertNull("間取詳細コメントが Null である事。", housingInfo.getLayoutComment());
		// 物件の階数
		Assert.assertNull("物件の階数コメントが Null である事。", housingInfo.getFloorNo());
		// 物件の階数コメント
		Assert.assertNull("物件の階数コメントが Null である事。", housingInfo.getFloorNoNote());
		// 土地面積
		Assert.assertNull("土地面積が Null である事。", housingInfo.getLandArea());
		// 土地面積_補足
		Assert.assertNull("土地面積_補足が Null である事。", housingInfo.getLandAreaMemo());
		// 専有面積
		Assert.assertNull("専有面積が Null である事。", housingInfo.getPersonalArea());
		// 専有面積_補足
		Assert.assertNull("専有面積_補足が Null である事。", housingInfo.getPersonalAreaMemo());
		// 入居状態フラグ
		Assert.assertNull("入居状態フラグが Null である事。", housingInfo.getMoveinFlg());
		// 駐車場の状況
		Assert.assertNull("駐車場の状況が Null である事。", housingInfo.getParkingSituation());
		// 駐車場空の有無
		Assert.assertNull("駐車場空の有無が Null である事。", housingInfo.getParkingEmpExist());
		// 表示用駐車場情報
		Assert.assertNull("表示用駐車場情報が Null である事。", housingInfo.getDisplayParkingInfo());
		// 窓の向き
		Assert.assertNull("窓の向きが Null である事。", housingInfo.getWindowDirection());
		// 基本情報コメント
		Assert.assertNull("基本情報コメントが Null である事。", housingInfo.getBasicComment());
		
		// アイコン情報　は、設備登録側で更新されるので、このタイミングでは null が登録される。
		Assert.assertNull("アイコン情報が null である事", housingInfo.getIconCd());

		// 登録日（日付部のみ）
		String now = StringUtils.dateToString(new Date());
		String date = StringUtils.dateToString(housingInfo.getInsDate());
		Assert.assertEquals("登録日がシステム日付と一致する事。", now, date);

		// 登録者
		Assert.assertEquals("登録者が引数で渡した値と一致する事。", "addUserId1", housingInfo.getInsUserId());

		// 最終更新日（日付部のみ）
		date = StringUtils.dateToString(housingInfo.getUpdDate());
		Assert.assertEquals("最終更新日がシステム日付と一致する事。", now, date);

		// 最終更新者
		Assert.assertEquals("最終更新者が引数で渡した値と一致する事。", "addUserId1", housingInfo.getUpdUserId());

	}



	/**
	 * 物件基本情報の更新
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>指定した物件基本情報が入力した値で更新される事</li>
	 *     <li>アイコン情報、登録日、登録者が更新されない事</li>
	 *     <li>指定していない物件基本情報が更新されていない事</li>
	 * </ul>
	 */
	@Test
	public void updateHousingTest() throws Exception {
		
		// テストデータを初期登録する。
		initData();


		// 入力フォームを生成して初期値を設定
		HousingForm inputForm = this.formFactory.createHousingForm();

		inputForm.setSysHousingCd("SYSHOU00001");						// 更新対象システム物件CD

		inputForm.setHousingCd("HOU0001");								// 物件番号
		inputForm.setDisplayHousingName("HNAME0001");					// 表示用物件名
		inputForm.setDisplayHousingNameKana("HNAME0001K");				// 表示用物件名（カナ）
		inputForm.setRoomNo("R001");									// 部屋番号
		inputForm.setSysBuildingCd("SYSBLD0002");						// システム建物CD
		inputForm.setPrice("100000");									// 賃料・価格
		inputForm.setUpkeep("10000");									// 管理費
		inputForm.setCommonAreaFee("20000");							// 共益費
		inputForm.setMenteFee("30000");									// 修繕積立費
		inputForm.setSecDeposit("40000.01");							// 敷金
		inputForm.setSecDepositCrs("S");								// 敷金単位
		inputForm.setBondChrg("60000.01");								// 保証金
		inputForm.setBondChrgCrs("B");									// 保証金単位
		inputForm.setDepositDiv("ZZ");									// 敷引礼金区分
		inputForm.setDeposit("90000.01");								// 敷引礼金額
		inputForm.setDepositCrs("D");									// 敷引礼金単位
		inputForm.setLayoutCd("L01");									// 間取CD
		inputForm.setLayoutComment("LAYOUT_NOTE1");						// 間取詳細コメント
		inputForm.setFloorNo("110");									// 物件の階数
		inputForm.setFloorNoNote("FLOOR0001");							// 物件の階数コメント
		inputForm.setLandArea("111.01"); 								// 土地面積
		inputForm.setLandAreaMemo("LAND_NOTE1");						// 土地面積_補足
		inputForm.setPersonalArea("112.01");							// 専有面積
		inputForm.setPersonalAreaMemo("PER_NOTE1");						// 専有面積_補足
		inputForm.setMoveinFlg("9");									// 入居状態フラグ
		inputForm.setParkingSituation("YY"); 							// 駐車場の状況
		inputForm.setParkingEmpExist("N");								// 駐車場空の有無
		inputForm.setDisplayParkingInfo("PARKING_001");					// 表示用駐車場情報
		inputForm.setWindowDirection("S");								// 窓の向き
		inputForm.setBasicComment("BASIC001");							// 基本情報コメント

		// テストクラス実行
		this.housingManage.updateHousing(inputForm, "updUserId1");

		
		// 物件基本情報のチェック
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00001");

		// システム物件CD
		Assert.assertEquals("システム物件CD が採番された値と一致する事。", "SYSHOU00001", housingInfo.getSysHousingCd());
		// 物件番号
		Assert.assertEquals("物件番号が入力された値と一致する事。", "HOU0001", housingInfo.getHousingCd());
		// 表示用物件名
		Assert.assertEquals("表示用物件名が入力された値と一致する事。", "HNAME0001", housingInfo.getDisplayHousingName());
		// 表示用物件名（カナ）
		Assert.assertEquals("表示用物件名（カナ）が入力された値と一致する事。", "HNAME0001K", housingInfo.getDisplayHousingNameKana());
		// 部屋番号
		Assert.assertEquals("部屋番号が入力された値と一致する事。", "R001", housingInfo.getRoomNo());
		// システム建物CD
		Assert.assertEquals("システム建物CDが入力された値と一致する事。", "SYSBLD0002", housingInfo.getSysBuildingCd());
		// 賃料・価格
		Assert.assertEquals("賃料・価格が入力された値と一致する事。", Long.valueOf("100000"), housingInfo.getPrice());
		// 管理費
		Assert.assertEquals("管理費が入力された値と一致する事。", Long.valueOf("10000"), housingInfo.getUpkeep());
		// 共益費
		Assert.assertEquals("共益費が入力された値と一致する事。", Long.valueOf("20000"), housingInfo.getCommonAreaFee());
		// 修繕積立費
		Assert.assertEquals("修繕積立費が入力された値と一致する事。", Long.valueOf("30000"), housingInfo.getMenteFee());
		// 敷金
		Assert.assertEquals("敷金が入力された値と一致する事。", new BigDecimal("40000.01"), housingInfo.getSecDeposit());
		// 敷金単位
		Assert.assertEquals("敷金単位が入力された値と一致する事。", "S", housingInfo.getSecDepositCrs());
		// 保証金
		Assert.assertEquals("保証金が入力された値と一致する事。", new BigDecimal("60000.01"), housingInfo.getBondChrg());
		// 保証金単位
		Assert.assertEquals("保証金単位が入力された値と一致する事。", "B", housingInfo.getBondChrgCrs());
		// 敷引礼金区分
		Assert.assertEquals("敷引礼金区分が入力された値と一致する事。", "ZZ", housingInfo.getDepositDiv());
		// 敷引礼金額
		Assert.assertEquals("敷引礼金額が入力された値と一致する事。", new BigDecimal("90000.01"), housingInfo.getDeposit());
		// 敷引礼金単位
		Assert.assertEquals("敷引礼金単位が入力された値と一致する事。", "D", housingInfo.getDepositCrs());
		// 間取CD
		Assert.assertEquals("間取CDが入力された値と一致する事。", "L01", housingInfo.getLayoutCd());
		// 間取詳細コメント
		Assert.assertEquals("間取詳細コメントが入力された値と一致する事。", "LAYOUT_NOTE1", housingInfo.getLayoutComment());
		// 物件の階数
		Assert.assertEquals("物件の階数コメントが入力された値と一致する事。", Integer.valueOf("110"), housingInfo.getFloorNo());
		// 物件の階数コメント
		Assert.assertEquals("物件の階数コメントが入力された値と一致する事。", "FLOOR0001", housingInfo.getFloorNoNote());
		// 土地面積
		Assert.assertEquals("土地面積が入力された値と一致する事。", new BigDecimal("111.01"), housingInfo.getLandArea());
		// 土地面積_補足
		Assert.assertEquals("土地面積_補足が入力された値と一致する事。", "LAND_NOTE1", housingInfo.getLandAreaMemo());
		// 専有面積
		Assert.assertEquals("専有面積が入力された値と一致する事。", new BigDecimal("112.01"), housingInfo.getPersonalArea());
		// 専有面積_補足
		Assert.assertEquals("専有面積_補足が入力された値と一致する事。", "PER_NOTE1", housingInfo.getPersonalAreaMemo());
		// 入居状態フラグ
		Assert.assertEquals("入居状態フラグが入力された値と一致する事。", "9", housingInfo.getMoveinFlg());
		// 駐車場の状況
		Assert.assertEquals("駐車場の状況が入力された値と一致する事。", "YY", housingInfo.getParkingSituation());
		// 駐車場空の有無
		Assert.assertEquals("駐車場空の有無が入力された値と一致する事。", "N", housingInfo.getParkingEmpExist());
		// 表示用駐車場情報
		Assert.assertEquals("表示用駐車場情報が入力された値と一致する事。", "PARKING_001", housingInfo.getDisplayParkingInfo());
		// 窓の向き
		Assert.assertEquals("窓の向きが入力された値と一致する事。", "S", housingInfo.getWindowDirection());
		// 基本情報コメント
		Assert.assertEquals("基本情報コメントが入力された値と一致する事。", "BASIC001", housingInfo.getBasicComment());
		
		// アイコン情報　は、物件基本情報更新では処理対象外なので、元の値である事をチェックする。
		Assert.assertEquals("アイコン情報が元の値である事", "dummyIcon1", housingInfo.getIconCd());

		// 登録日（日付部のみ）
		String now10 = StringUtils.dateToString(DateUtils.addDays(new Date(), -10));
		String date = StringUtils.dateToString(housingInfo.getInsDate());
		Assert.assertEquals("登録日が更新されていない事。", now10, date);

		// 登録者
		Assert.assertEquals("登録者が更新されていない事", "dummInsID1", housingInfo.getInsUserId());

		// 最終更新日（日付部のみ）
		String now = StringUtils.dateToString(new Date());
		date = StringUtils.dateToString(housingInfo.getUpdDate());
		Assert.assertEquals("最終更新日がシステム日付と一致する事。", now, date);

		// 最終更新者
		Assert.assertEquals("最終更新者が引数で渡した値と一致する事。", "updUserId1", housingInfo.getUpdUserId());

		
		// 更新対象外レコードが更新されていない事をチェックする。
		housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00002");
		Assert.assertEquals("更新対象外レコードが更新されていない事", "OLD物件名２", housingInfo.getDisplayHousingName());
		
	}

	
	
	/**
	 * 物件基本情報の更新（null 値へ更新）
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>null 値へ UPDATE できる事</li>
	 * </ul>
	 */
	@Test
	public void updateHousingToNullTest() throws Exception {
		
		// updateHousingTest　を使用して、全フィールドに値を設定する。
		updateHousingTest();
		
		// 入力フォームを生成して最低限の値のみ設定する。
		HousingForm inputForm = this.formFactory.createHousingForm();

		inputForm.setSysHousingCd("SYSHOU00001");						// 更新対象システム物件CD
		inputForm.setSysBuildingCd("SYSBLD0002");						// システム建物CD
		inputForm.setDisplayHousingName("HNAME0001");					// 表示用物件名

		
		// テストクラス実行
		this.housingManage.updateHousing(inputForm, "updUserId1");

		
		// 物件基本情報のチェック
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00001");

		
		// 物件番号
		Assert.assertNull("物件番号が null である事。", housingInfo.getHousingCd());
		// 表示用物件名（カナ）
		Assert.assertNull("表示用物件名（カナ）が null である事。", housingInfo.getDisplayHousingNameKana());
		// 部屋番号
		Assert.assertNull("部屋番号が null である事。", housingInfo.getRoomNo());
		// 賃料・価格
		Assert.assertNull("賃料・価格が null である事。", housingInfo.getPrice());
		// 管理費
		Assert.assertNull("管理費が null である事。", housingInfo.getUpkeep());
		// 共益費
		Assert.assertNull("共益費が null である事。", housingInfo.getCommonAreaFee());
		// 修繕積立費
		Assert.assertNull("修繕積立費が null である事。", housingInfo.getMenteFee());
		// 敷金
		Assert.assertNull("敷金が null である事。", housingInfo.getSecDeposit());
		// 敷金単位
		Assert.assertNull("敷金単位が null である事。", housingInfo.getSecDepositCrs());
		// 保証金
		Assert.assertNull("保証金が null である事。", housingInfo.getBondChrg());
		// 保証金単位
		Assert.assertNull("保証金単位が null である事。", housingInfo.getBondChrgCrs());
		// 敷引礼金区分
		Assert.assertNull("敷引礼金区分が null である事。", housingInfo.getDepositDiv());
		// 敷引礼金額
		Assert.assertNull("敷引礼金額が null である事。", housingInfo.getDeposit());
		// 敷引礼金単位
		Assert.assertNull("敷引礼金単位が null である事。", housingInfo.getDepositCrs());
		// 間取CD
		Assert.assertNull("間取CDが null である事。", housingInfo.getLayoutCd());
		// 間取詳細コメント
		Assert.assertNull("間取詳細コメントが null である事。", housingInfo.getLayoutComment());
		// 物件の階数
		Assert.assertNull("物件の階数コメントが null である事。", housingInfo.getFloorNo());
		// 物件の階数コメント
		Assert.assertNull("物件の階数コメントが null である事。", housingInfo.getFloorNoNote());
		// 土地面積
		Assert.assertNull("土地面積が null である事。", housingInfo.getLandArea());
		// 土地面積_補足
		Assert.assertNull("土地面積_補足が null である事。", housingInfo.getLandAreaMemo());
		// 専有面積
		Assert.assertNull("専有面積が null である事。", housingInfo.getPersonalArea());
		// 専有面積_補足
		Assert.assertNull("専有面積_補足が null である事。", housingInfo.getPersonalAreaMemo());
		// 入居状態フラグ
		Assert.assertNull("入居状態フラグが null である事。", housingInfo.getMoveinFlg());
		// 駐車場の状況
		Assert.assertNull("駐車場の状況が null である事。", housingInfo.getParkingSituation());
		// 駐車場空の有無
		Assert.assertNull("駐車場空の有無が null である事。", housingInfo.getParkingEmpExist());
		// 表示用駐車場情報
		Assert.assertNull("表示用駐車場情報が null である事。", housingInfo.getDisplayParkingInfo());
		// 窓の向き
		Assert.assertNull("窓の向きが null である事。", housingInfo.getWindowDirection());
		// 基本情報コメント
		Assert.assertNull("基本情報コメントが null である事。", housingInfo.getBasicComment());
		
		// アイコン情報　は、物件基本情報更新では処理対象外なので、元の値である事をチェックする。
		Assert.assertEquals("アイコン情報が元の値である事", "dummyIcon1", housingInfo.getIconCd());

		
		
	}
	
	
	/**
	 * 物件基本情報の更新 （更新対象が存在しない場合）
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>NotFoundException がスローされる事</li>
	 * </ul>
	 */
	@Test(expected=NotFoundException.class)
	public void updateHousingFailTest1() throws Exception {

		// テストデータを初期登録する。
		initData();


		// 入力フォームを生成して初期値を設定
		HousingForm inputForm = this.formFactory.createHousingForm();

		// 存在しないシステム物件CD を設定
		inputForm.setSysHousingCd("SYSHOU10001");
		
		// テストクラス実行
		this.housingManage.updateHousing(inputForm, "updUserId1");
	}
	

	
	/**
	 * 物件基本情報の更新 （システム建物CD が存在しない場合）
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>NotFoundException がスローされる事</li>
	 * </ul>
	 */
	@Test(expected=NotFoundException.class)
	public void updateHousingFailTest2() throws Exception {

		// テストデータを初期登録する。
		initData();


		// 入力フォームを生成して初期値を設定
		HousingForm inputForm = this.formFactory.createHousingForm();

		// システム物件CD を設定
		inputForm.setSysHousingCd("SYSHOU00001");

		// 存在しないシステム建物CD を設定
		inputForm.setSysBuildingCd("SYSBLD2002");
		
		// テストクラス実行
		this.housingManage.updateHousing(inputForm, "updUserId1");
	}
	
	
	
	
	/**
	 * 物件詳情報の更新 （更新対象となる物件詳細情報が存在しない場合で、フル入力）
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>物件詳細情報が新たなレコードとして追加される事</li>
	 *     <li>物件基本情報の更新日、更新者のみが更新されている事</li>
	 * </ul>
	 */
	@Test
	public void updateHousingDtlNonFullDataTest() throws Exception {

		// テストデータを初期登録する。
		initData();

		// 入力フォームの作成
		HousingDtlForm inputForm = initInputDtlForm("SYSHOU00001");

		// テストクラス実行
		this.housingManage.updateHousingDtl(inputForm, "updUserId1");

		// 物件詳細情報のチェック
		chkHousingDtl("SYSHOU00001");
		

		// 物件基本情報の更新日付チェック
		chkHousingTimestamp("SYSHOU00001", "updUserId1");
		
	}

	

	/**
	 * 物件詳情報の更新 （更新対象となる物件詳細情報が存在しない場合で、最小入力）
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>物件詳細情報が新たなレコードとして追加される事</li>
	 *     <li>物件基本情報の更新日、更新者のみが更新されている事</li>
	 * </ul>
	 */
	@Test
	public void updateHousingDtlNonMinDataTest() throws Exception {

		// テストデータを初期登録する。
		initData();

		// 入力フォームの作成
		HousingDtlForm inputForm = this.formFactory.createHousingDtlForm();
		
		// 最低限の値のみ入力
		inputForm.setSysHousingCd("SYSHOU00001");

		// テストクラス実行
		this.housingManage.updateHousingDtl(inputForm, "updUserId1");

		// 物件詳細情報のチェック
		HousingDtlInfo housingDtlInfo = this.housingDtlInfoDAO.selectByPK("SYSHOU00001");

		Assert.assertNull("用途地域CDの登録値が null である事", housingDtlInfo.getUsedAreaCd());
		Assert.assertNull("取引形態区分が null である事", housingDtlInfo.getTransactTypeDiv());
		Assert.assertNull("表示用契約期間が null である事", housingDtlInfo.getDisplayContractTerm());
		Assert.assertNull("土地権利が null である事", housingDtlInfo.getLandRight());
		Assert.assertNull("入居可能時期フラグが null である事", housingDtlInfo.getMoveinTiming());
		Assert.assertNull("入居可能時期が null である事", housingDtlInfo.getMoveinTimingDay());
		Assert.assertNull("入居可能時期コメントが null である事", housingDtlInfo.getMoveinNote());
		Assert.assertNull("表示用入居可能時期が null である事", housingDtlInfo.getDisplayMoveinTiming());
		Assert.assertNull("表示用入居諸条件が null である事", housingDtlInfo.getDisplayMoveinProviso());
		Assert.assertNull("管理形態・方式が null である事", housingDtlInfo.getUpkeepType());
		Assert.assertNull("管理会社が null である事", housingDtlInfo.getUpkeepCorp());
		Assert.assertNull("更新料が null である事", housingDtlInfo.getRenewChrg());
		Assert.assertNull("更新料単位が null である事", housingDtlInfo.getRenewChrgCrs());
		Assert.assertNull("更新料名が null である事", housingDtlInfo.getRenewChrgName());
		Assert.assertNull("更新手数料が null である事", housingDtlInfo.getRenewDue());
		Assert.assertNull("更新手数料単位が null である事", housingDtlInfo.getRenewDueCrs());
		Assert.assertNull("仲介手数料が null である事", housingDtlInfo.getBrokerageChrg());
		Assert.assertNull("仲介手数料単位が null である事", housingDtlInfo.getBrokerageChrgCrs());
		Assert.assertNull("鍵交換料が null である事", housingDtlInfo.getChangeKeyChrg());
		Assert.assertNull("損保有無が null である事", housingDtlInfo.getInsurExist());
		Assert.assertNull("損保料金が null である事", housingDtlInfo.getInsurChrg());
		Assert.assertNull("損保年数が null である事", housingDtlInfo.getInsurTerm());
		Assert.assertNull("損保認定ランクが null である事", housingDtlInfo.getInsurLank());
		Assert.assertNull("諸費用が null である事", housingDtlInfo.getOtherChrg());
		Assert.assertNull("接道状況が null である事", housingDtlInfo.getContactRoad());
		Assert.assertNull("接道方向/幅員が null である事", housingDtlInfo.getContactRoadDir());
		Assert.assertNull("私道負担が null である事", housingDtlInfo.getPrivateRoad());
		Assert.assertNull("バルコニー面積が null である事", housingDtlInfo.getBalconyArea());
		Assert.assertNull("特記事項が null である事", housingDtlInfo.getSpecialInstruction());
		Assert.assertNull("詳細コメントが null である事", housingDtlInfo.getDtlComment());

		// 物件基本情報の更新日付チェック
		chkHousingTimestamp("SYSHOU00001", "updUserId1");

	}



	/**
	 * 物件詳情報の更新 （更新対象となる物件詳細情報が存在する場合）
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>物件詳細情報が入力値で更新されている事</li>
	 *     <li>物件基本情報の更新日、更新者のみが更新されている事</li>
	 * </ul>
	 */
	@Test
	public void updateHousingDtlFullDataTest() throws Exception {

		// テストデータを初期登録する。
		initData();

		// 入力フォームの作成
		HousingDtlForm inputForm = this.formFactory.createHousingDtlForm();
		
		// 最低限の値のみ入力
		inputForm.setSysHousingCd("SYSHOU00001");

		// テストクラス実行
		this.housingManage.updateHousingDtl(inputForm, "updUserId1");

		// テストデータの存在チェック
		HousingDtlInfo housingDtlInfo = this.housingDtlInfoDAO.selectByPK("SYSHOU00001");
		Assert.assertNotNull("登録したテストデータが存在する事", housingDtlInfo);
		
		
		// 入力フォームの作成
		inputForm = initInputDtlForm("SYSHOU00001");

		// テストクラス実行
		this.housingManage.updateHousingDtl(inputForm, "updUserId2");

		// 物件詳細情報のチェック
		chkHousingDtl("SYSHOU00001");

		// 物件基本情報の更新日付チェック
		chkHousingTimestamp("SYSHOU00001", "updUserId2");

	}


	
	/**
	 * 物件詳情報の更新 （更新対象となる物件詳細情報をnullに更新）
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>物件詳細情報が入力値で更新されている事</li>
	 *     <li>物件基本情報の更新日、更新者のみが更新されている事</li>
	 * </ul>
	 */
	@Test
	public void updateHousingDtlToNullTest() throws Exception {

		// updateHousingDtlNonFullDataTest を使用して、全フィールドに値を設定する。
		updateHousingDtlNonFullDataTest();

		
		// 入力フォームの作成
		HousingDtlForm inputForm = this.formFactory.createHousingDtlForm();
		
		// 最低限の値のみ入力
		inputForm.setSysHousingCd("SYSHOU00001");


		// テストクラス実行
		this.housingManage.updateHousingDtl(inputForm, "updUserId2");
		
		
		// 物件詳細情報のチェック
		HousingDtlInfo housingDtlInfo = this.housingDtlInfoDAO.selectByPK("SYSHOU00001");

		Assert.assertNull("用途地域CDの登録値が null である事", housingDtlInfo.getUsedAreaCd());
		Assert.assertNull("取引形態区分が null である事", housingDtlInfo.getTransactTypeDiv());
		Assert.assertNull("表示用契約期間が null である事", housingDtlInfo.getDisplayContractTerm());
		Assert.assertNull("土地権利が null である事", housingDtlInfo.getLandRight());
		Assert.assertNull("入居可能時期フラグが null である事", housingDtlInfo.getMoveinTiming());
		Assert.assertNull("入居可能時期が null である事", housingDtlInfo.getMoveinTimingDay());
		Assert.assertNull("入居可能時期コメントが null である事", housingDtlInfo.getMoveinNote());
		Assert.assertNull("表示用入居可能時期が null である事", housingDtlInfo.getDisplayMoveinTiming());
		Assert.assertNull("表示用入居諸条件が null である事", housingDtlInfo.getDisplayMoveinProviso());
		Assert.assertNull("管理形態・方式が null である事", housingDtlInfo.getUpkeepType());
		Assert.assertNull("管理会社が null である事", housingDtlInfo.getUpkeepCorp());
		Assert.assertNull("更新料が null である事", housingDtlInfo.getRenewChrg());
		Assert.assertNull("更新料単位が null である事", housingDtlInfo.getRenewChrgCrs());
		Assert.assertNull("更新料名が null である事", housingDtlInfo.getRenewChrgName());
		Assert.assertNull("更新手数料が null である事", housingDtlInfo.getRenewDue());
		Assert.assertNull("更新手数料単位が null である事", housingDtlInfo.getRenewDueCrs());
		Assert.assertNull("仲介手数料が null である事", housingDtlInfo.getBrokerageChrg());
		Assert.assertNull("仲介手数料単位が null である事", housingDtlInfo.getBrokerageChrgCrs());
		Assert.assertNull("鍵交換料が null である事", housingDtlInfo.getChangeKeyChrg());
		Assert.assertNull("損保有無が null である事", housingDtlInfo.getInsurExist());
		Assert.assertNull("損保料金が null である事", housingDtlInfo.getInsurChrg());
		Assert.assertNull("損保年数が null である事", housingDtlInfo.getInsurTerm());
		Assert.assertNull("損保認定ランクが null である事", housingDtlInfo.getInsurLank());
		Assert.assertNull("諸費用が null である事", housingDtlInfo.getOtherChrg());
		Assert.assertNull("接道状況が null である事", housingDtlInfo.getContactRoad());
		Assert.assertNull("接道方向/幅員が null である事", housingDtlInfo.getContactRoadDir());
		Assert.assertNull("私道負担が null である事", housingDtlInfo.getPrivateRoad());
		Assert.assertNull("バルコニー面積が null である事", housingDtlInfo.getBalconyArea());
		Assert.assertNull("特記事項が null である事", housingDtlInfo.getSpecialInstruction());
		Assert.assertNull("詳細コメントが null である事", housingDtlInfo.getDtlComment());

	}
	
	
	
	
	
	
	
	
	
	

	/**
	 * 物件詳情報の更新 （親となる物件基本情報が存在しない場合）
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>NotFoundException がスローされる事</li>
	 * </ul>
	 */
	@Test(expected=NotFoundException.class)
	public void updateHousingDtlFailTest() throws Exception {

		// テストデータの作成

		// 入力フォームの作成
		HousingDtlForm inputForm = this.formFactory.createHousingDtlForm();
		
		// 最低限の値のみ入力
		inputForm.setSysHousingCd("SYSHOU00001");

		// テストクラス実行
		this.housingManage.updateHousingDtl(inputForm, "updUserId1");

	}

	
	/**
	 * 設備情報を登録する。　（５件の配列情報、既存データなし）
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>登録された設備CD の件数が正しい事</li>
	 *     <li>登録された設備CD が正しい事</li>
	 *     <li>指定対象外の設備CD が削除されていない事</li>
	 *     <li>更新対象物件基本情報のアイコン情報が入力値で更新されている事</li>
	 *     <li>更新対象以外の物件基本情報のアイコン情報が更新されていない事</li>
	 *     <li>更新対象物件基本情報の更新日、更新者のみが更新されている事</li>
	 *     <li>更新対象以外の物件基本情報の更新日、更新者のみが更新されていない事</li>
	 * </ul>
	 */
	@Test
	public void updateHousingEquipTest1() throws Exception {
		
		// テストデータを初期登録する。
		initData();

		// 誤ったデータ削除の確認用に別のシステム物件CD で設備情報を登録しておく
		HousingEquipInfo equipInfo = new HousingEquipInfo();
		equipInfo.setSysHousingCd("SYSHOU00002");
		equipInfo.setEquipCd("000001");
		this.housingEquipInfoDAO.insert(new HousingEquipInfo[] {equipInfo});
		
		
		
		// フォームを作成して入力値を設定する。
		HousingEquipForm inputForm = this.formFactory.createHousingEquipForm();
		inputForm.setSysHousingCd("SYSHOU00001");

		String equipCd[] = new String[5];
		equipCd[0] = "000001";
		equipCd[1] = "000002";
		equipCd[2] = "000003";
		equipCd[3] = "000004";
		equipCd[4] = "000005";
		inputForm.setEquipCd(equipCd);

		// テストメソッド実行
		this.housingManage.updateHousingEquip(inputForm, "editUserId2");


		// 実行結果の確認（更新対象の設備情報）
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00001");

		List<HousingEquipInfo> list = this.housingEquipInfoDAO.selectByFilter(criteria);
		
		Assert.assertEquals("登録件数が正しい事", 5, list.size());
		
		for (HousingEquipInfo equip : list) {
			if (!equip.getEquipCd().equals("000001") && !equip.getEquipCd().equals("000002") && 
				!equip.getEquipCd().equals("000003") && !equip.getEquipCd().equals("000004") &&
				!equip.getEquipCd().equals("000005")) {
				
				Assert.fail("登録された設備CD が正しい事:" + equip.getEquipCd());
			}
		}

		
		// 実行結果の確認（更新対象外の設備情報）
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00002");
		
		list = this.housingEquipInfoDAO.selectByFilter(criteria);

		Assert.assertEquals("登録件数が正しい事", 1, list.size());
		
		for (HousingEquipInfo equip : list) {
			if (!equip.getEquipCd().equals("000001")) {
				Assert.fail("登録された設備CD が正しい事:" + equip.getEquipCd());
			}
		}

		
		// 更新対象物件基本情報のアイコン情報を確認する。
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00001");
		Assert.assertEquals("更新対象のアイコン情報が更新されている事", "000001,000002,000003,000004,000005",  housingInfo.getIconCd());
		

		// 更新対象外物件基本情報のアイコン情報を確認する。
		housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00002");
		Assert.assertEquals("更新対象外のアイコン情報が更新されていない事", "dummyIcon2", housingInfo.getIconCd());

		
		// 物件基本情報の更新日付チェック
		chkHousingTimestamp("SYSHOU00001", "editUserId2");

	}
	

	
	/**
	 * 設備情報を登録する。　（５件の配列情報、既存データあり）
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>登録された設備CD の件数が正しい事</li>
	 *     <li>登録された設備CD が正しい事</li>
	 *     <li>登録前の設備CD が削除されている事</li>
	 *     <li>指定対象外の設備CD が削除されていない事</li>
	 *     <li>更新対象物件基本情報のアイコン情報が入力値で更新されている事</li>
	 *     <li>更新対象以外の物件基本情報のアイコン情報が更新されていない事</li>
	 *     <li>更新対象物件基本情報の更新日、更新者のみが更新されている事</li>
	 *     <li>更新対象以外の物件基本情報の更新日、更新者のみが更新されていない事</li>
	 * </ul>
	 */
	@Test
	public void updateHousingEquipTest2() throws Exception {
		
		// テストデータを初期登録する。
		initData();

		// 更新前となる設備情報の登録
		HousingEquipInfo equipInfo = new HousingEquipInfo();
		equipInfo.setSysHousingCd("SYSHOU00001");
		equipInfo.setEquipCd("000006");
		this.housingEquipInfoDAO.insert(new HousingEquipInfo[] {equipInfo});


		// 誤ったデータ削除の確認用に別のシステム物件CD で設備情報を登録しておく
		equipInfo = new HousingEquipInfo();
		equipInfo.setSysHousingCd("SYSHOU00002");
		equipInfo.setEquipCd("000001");
		this.housingEquipInfoDAO.insert(new HousingEquipInfo[] {equipInfo});
		
		
		
		// フォームを作成して入力値を設定する。
		HousingEquipForm inputForm = this.formFactory.createHousingEquipForm();
		inputForm.setSysHousingCd("SYSHOU00001");

		String equipCd[] = new String[5];
		equipCd[0] = "000001";
		equipCd[1] = "000002";
		equipCd[2] = "000003";
		equipCd[3] = "000004";
		equipCd[4] = "000005";
		inputForm.setEquipCd(equipCd);

		// テストメソッド実行
		this.housingManage.updateHousingEquip(inputForm, "editUserId2");


		// 実行結果の確認（更新対象の設備情報）
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00001");

		List<HousingEquipInfo> list = this.housingEquipInfoDAO.selectByFilter(criteria);
		
		Assert.assertEquals("登録件数が正しい事", 5, list.size());
		
		for (HousingEquipInfo equip : list) {
			if (!equip.getEquipCd().equals("000001") && !equip.getEquipCd().equals("000002") && 
				!equip.getEquipCd().equals("000003") && !equip.getEquipCd().equals("000004") &&
				!equip.getEquipCd().equals("000005")) {
				
				Assert.fail("登録された設備CD が正しい事:" + equip.getEquipCd());
			}
		}

		
		// 実行結果の確認（更新対象外の設備情報）
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00002");
		
		list = this.housingEquipInfoDAO.selectByFilter(criteria);

		Assert.assertEquals("登録件数が正しい事", 1, list.size());
		
		for (HousingEquipInfo equip : list) {
			if (!equip.getEquipCd().equals("000001")) {
				Assert.fail("登録された設備CD が正しい事:" + equip.getEquipCd());
			}
		}

		
		// 更新対象物件基本情報のアイコン情報を確認する。
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00001");
		Assert.assertEquals("更新対象のアイコン情報が更新されている事", "000001,000002,000003,000004,000005",  housingInfo.getIconCd());
		

		// 更新対象外物件基本情報のアイコン情報を確認する。
		housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00002");
		Assert.assertEquals("更新対象外のアイコン情報が更新されていない事", "dummyIcon2", housingInfo.getIconCd());
		
		
		// 物件基本情報の更新日付チェック
		chkHousingTimestamp("SYSHOU00001", "editUserId2");

	}


	
	/**
	 * 設備情報を登録する。　（３件の配列情報、ブランク、null を含む。　既存データなし）
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>登録された設備CD の件数が正しい事</li>
	 *     <li>登録された設備CD が正しい事</li>
	 *     <li>指定対象外の設備CD が削除されていない事</li>
	 *     <li>更新対象物件基本情報のアイコン情報が入力値で更新されている事</li>
	 *     <li>更新対象以外の物件基本情報のアイコン情報が更新されていない事</li>
	 *     <li>更新対象物件基本情報の更新日、更新者のみが更新されている事</li>
	 *     <li>更新対象以外の物件基本情報の更新日、更新者のみが更新されていない事</li>
	 * </ul>
	 */
	@Test
	public void updateHousingEquipTest3() throws Exception {
		
		// テストデータを初期登録する。
		initData();

		// フォームを作成して入力値を設定する。
		HousingEquipForm inputForm = this.formFactory.createHousingEquipForm();
		inputForm.setSysHousingCd("SYSHOU00001");

		String equipCd[] = new String[5];
		equipCd[0] = "000001";
		equipCd[1] = "000002";
		equipCd[2] = null;
		equipCd[3] = "000004";
		equipCd[4] = "";
		inputForm.setEquipCd(equipCd);

		// テストメソッド実行
		this.housingManage.updateHousingEquip(inputForm, "editUserId2");


		// 実行結果の確認（更新対象の設備情報）
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00001");

		List<HousingEquipInfo> list = this.housingEquipInfoDAO.selectByFilter(criteria);
		
		Assert.assertEquals("登録件数が正しい事", 3, list.size());
		
		for (HousingEquipInfo equip : list) {
			if (!equip.getEquipCd().equals("000001") && !equip.getEquipCd().equals("000002") && 
				!equip.getEquipCd().equals("000004")) {

				Assert.fail("登録された設備CD が正しい事:" + equip.getEquipCd());
			}
		}
		
		
		// 更新対象物件基本情報のアイコン情報を確認する。
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00001");
		Assert.assertEquals("更新対象のアイコン情報が更新されている事", "000001,000002,000004",  housingInfo.getIconCd());
		

		// 更新対象外物件基本情報のアイコン情報を確認する。
		housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00002");
		Assert.assertEquals("更新対象外のアイコン情報が更新されていない事", "dummyIcon2", housingInfo.getIconCd());

		
		// 物件基本情報の更新日付チェック
		chkHousingTimestamp("SYSHOU00001", "editUserId2");

	}


	
	/**
	 * 設備情報を登録する。　（5件の空文字列配列情報）
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>既存の設備CD が削除されている事</li>
	 *     <li>指定対象外の設備CD が削除されていない事</li>
	 *     <li>更新対象物件基本情報のアイコン情報が null で更新されている事</li>
	 *     <li>更新対象以外の物件基本情報のアイコン情報が更新されていない事</li>
	 *     <li>更新対象物件基本情報の更新日、更新者のみが更新されている事</li>
	 *     <li>更新対象以外の物件基本情報の更新日、更新者のみが更新されていない事</li>
	 * </ul>
	 */
	@Test
	public void updateHousingEquipTest4() throws Exception {
		
		// テストデータを初期登録する。
		initData();

		// 更新前となる設備情報の登録
		HousingEquipInfo equipInfo = new HousingEquipInfo();
		equipInfo.setSysHousingCd("SYSHOU00001");
		equipInfo.setEquipCd("000006");
		this.housingEquipInfoDAO.insert(new HousingEquipInfo[] {equipInfo});

		
		// フォームを作成して入力値を設定する。
		HousingEquipForm inputForm = this.formFactory.createHousingEquipForm();
		inputForm.setSysHousingCd("SYSHOU00001");

		String equipCd[] = new String[5];
		equipCd[0] = "";
		equipCd[1] = "";
		equipCd[2] = "";
		equipCd[3] = "";
		equipCd[4] = "";
		inputForm.setEquipCd(equipCd);

		// テストメソッド実行
		this.housingManage.updateHousingEquip(inputForm, "editUserId2");


		// 実行結果の確認（更新対象の設備情報）
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00001");

		List<HousingEquipInfo> list = this.housingEquipInfoDAO.selectByFilter(criteria);
		
		Assert.assertEquals("登録件数が正しい事", 0, list.size());
		
		
		// 更新対象物件基本情報のアイコン情報を確認する。
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00001");
		Assert.assertNull("更新対象のアイコン情報が null である事", housingInfo.getIconCd());
		

		// 更新対象外物件基本情報のアイコン情報を確認する。
		housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00002");
		Assert.assertEquals("更新対象外のアイコン情報が更新されていない事", "dummyIcon2", housingInfo.getIconCd());

		
		// 物件基本情報の更新日付チェック
		chkHousingTimestamp("SYSHOU00001", "editUserId2");

	}


	
	/**
	 * 設備情報を登録する。　（配列自体が null）
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>既存の設備CD が削除されている事</li>
	 *     <li>指定対象外の設備CD が削除されていない事</li>
	 *     <li>更新対象物件基本情報のアイコン情報が null で更新されている事</li>
	 *     <li>更新対象以外の物件基本情報のアイコン情報が更新されていない事</li>
	 *     <li>更新対象物件基本情報の更新日、更新者のみが更新されている事</li>
	 *     <li>更新対象以外の物件基本情報の更新日、更新者のみが更新されていない事</li>
	 * </ul>
	 */
	@Test
	public void updateHousingEquipTest5() throws Exception {
		
		// テストデータを初期登録する。
		initData();

		// 更新前となる設備情報の登録
		HousingEquipInfo equipInfo = new HousingEquipInfo();
		equipInfo.setSysHousingCd("SYSHOU00001");
		equipInfo.setEquipCd("000006");
		this.housingEquipInfoDAO.insert(new HousingEquipInfo[] {equipInfo});

		
		// フォームを作成して入力値を設定する。
		HousingEquipForm inputForm = this.formFactory.createHousingEquipForm();
		inputForm.setSysHousingCd("SYSHOU00001");
		inputForm.setEquipCd(null);

		// テストメソッド実行
		this.housingManage.updateHousingEquip(inputForm, "editUserId2");


		// 実行結果の確認（更新対象の設備情報）
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00001");

		List<HousingEquipInfo> list = this.housingEquipInfoDAO.selectByFilter(criteria);
		
		Assert.assertEquals("登録件数が正しい事", 0, list.size());
		
		
		// 更新対象物件基本情報のアイコン情報を確認する。
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00001");
		Assert.assertNull("更新対象のアイコン情報が null である事", housingInfo.getIconCd());
		

		// 更新対象外物件基本情報のアイコン情報を確認する。
		housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00002");
		Assert.assertEquals("更新対象外のアイコン情報が更新されていない事", "dummyIcon2", housingInfo.getIconCd());

		
		// 物件基本情報の更新日付チェック
		chkHousingTimestamp("SYSHOU00001", "editUserId2");

	}


	
	/**
	 * 設備情報を登録する。　（親レコードなし）
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>NotFoundException が発生</li>
	 * </ul>
	 */
	@Test(expected=NotFoundException.class)
	public void updateHousingEquipFailTest() throws Exception {
		
		// フォームを作成して入力値を設定する。
		HousingEquipForm inputForm = this.formFactory.createHousingEquipForm();
		inputForm.setSysHousingCd("SYSHOU00001");
		inputForm.setEquipCd(new String[]{"000001"});

		// テストメソッド実行
		this.housingManage.updateHousingEquip(inputForm, "editUserId2");

	}


	
	/**
	 * 物件画像情報追加処理（フル入力ケース、３件中、１件は空白行、既存データなし、物件情報にパス関連データなし。）
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>登録件数が正しい事。　（空白行を除いて登録されている事。）</li>
	 *     <li>パスに必要な建物情報が無い場合、オール 9 が使用されている事</li>
	 *     <li>入力した値で登録されている事</li>
	 *     <li>枝番が正しく採番されている事</li>
	 *     <li>メイン画像フラグが正しく設定されている事</li>
	 *     <li>縦長・横長フラグが正しく設定されている事</li>
	 *     <li>物件基本情報の更新者、更新日が更新されている事</li>
	 * </ul>
	 */
	@Test
	public void addHousingImgFullTest() throws Exception {

		// テストデータ作成
		initData();
		
		// テスト画像ファイル配置
		initImgFile();
		
		// フォームの作成
		HousingImgForm inputForm = this.formFactory.createHousingImgForm();

		inputForm.setSysHousingCd("SYSHOU00001");				// システム物件CD
		inputForm.setTempDate("20150101");						// 仮フォルダ名　（日付部分）
		
		String[] imageType = new String[3];						// 画像タイプ
		imageType[0] = "00";
		imageType[1] = "";
		imageType[2] = "01";
		inputForm.setImageType(imageType);

		String[] sortOrder = new String[3];						// 表示順 
		sortOrder[0] = "1";
		sortOrder[1] = "";
		sortOrder[2] = "2";
		inputForm.setSortOrder(sortOrder);

		String[] fileName = new String[3];						// ファイル名
		fileName[0] = "0000000001.jpg";
		fileName[1] = "";
		fileName[2] = "0000000002.jpg";
		inputForm.setFileName(fileName);

		String[] caption = new String[3];						// キャプション
		caption[0] = "キャプション１";
		caption[1] = "";
		caption[2] = "キャプション２";
		inputForm.setCaption(caption);

		String[] imgComment = new String[3];						// コメント
		imgComment[0] = "こめんと１";
		imgComment[1] = "";
		imgComment[2] = "こめんと２";
		inputForm.setImgComment(imgComment);
		
		
		// テストメソッド実行
		this.housingManage.addHousingImg(inputForm, "editUserId2");
		

		// 実行結果確認
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00001");
		criteria.addOrderByClause("imageType");
		criteria.addOrderByClause("divNo");
		List<HousingImageInfo> imgList = this.housingImageInfoDAO.selectByFilter(criteria);
		
		Assert.assertEquals("登録件数が正しし事", 2, imgList.size());
		
		Assert.assertEquals("画像タイプが正しい事", imageType[0], imgList.get(0).getImageType());
		Assert.assertEquals("画像タイプが正しい事", imageType[2], imgList.get(1).getImageType());
		
		Assert.assertEquals("枝番が正しい事", Integer.valueOf(1), imgList.get(0).getDivNo());
		Assert.assertEquals("枝番が正しい事", Integer.valueOf(1), imgList.get(1).getDivNo());

		Assert.assertEquals("表示順が正しい事", Integer.valueOf(sortOrder[0]), imgList.get(0).getSortOrder());
		Assert.assertEquals("表示順が正しい事", Integer.valueOf(sortOrder[2]), imgList.get(1).getSortOrder());

		Assert.assertEquals("パスが正しい事", "999/13/99999/SYSHOU00001/", imgList.get(0).getPathName());
		Assert.assertEquals("パスが正しい事", "999/13/99999/SYSHOU00001/", imgList.get(1).getPathName());

		Assert.assertEquals("ファイル名が正しい事", fileName[0], imgList.get(0).getFileName());
		Assert.assertEquals("ファイル名が正しい事", fileName[2], imgList.get(1).getFileName());

		Assert.assertEquals("メイン画像フラグが正しい事", "1", imgList.get(0).getMainImageFlg());
		Assert.assertEquals("メイン画像フラグが正しい事", "1", imgList.get(1).getMainImageFlg());

		Assert.assertEquals("キャプションが正しい事", caption[0], imgList.get(0).getCaption());
		Assert.assertEquals("キャプションが正しい事", caption[2], imgList.get(1).getCaption());

		Assert.assertEquals("コメントが正しい事", imgComment[0], imgList.get(0).getImgComment());
		Assert.assertEquals("コメントが正しい事", imgComment[2], imgList.get(1).getImgComment());

		Assert.assertEquals("縦長・横長フラグが正しい事", "1", imgList.get(0).getHwFlg());
		Assert.assertEquals("縦長・横長フラグが正しい事", "0", imgList.get(1).getHwFlg());

		
		// 物件基本情報のタイムタンプ確認
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00001");
		
		// 登録日（日付部のみ）
		String now10 = StringUtils.dateToString(DateUtils.addDays(new Date(), -10));
		String date = StringUtils.dateToString(housingInfo.getInsDate());
		Assert.assertEquals("登録日が更新されていない事。", now10, date);

		// 登録者
		Assert.assertEquals("登録者が更新されていない事", "dummInsID1", housingInfo.getInsUserId());

		// 最終更新日（日付部のみ）
		String now = StringUtils.dateToString(new Date());
		date = StringUtils.dateToString(housingInfo.getUpdDate());
		Assert.assertEquals("最終更新日がシステム日付と一致する事。", now, date);

		// 最終更新者
		Assert.assertEquals("最終更新者が引数で渡した値と一致する事。", "editUserId2", housingInfo.getUpdUserId());

	}
	
	
	
	/**
	 * 物件画像情報追加処理（フル入力ケース、３件中、１件は空白行、既存データあり、パスに必要なデータあり）
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>登録件数が正しい事。　（空白行を除いて登録されている事。）</li>
	 *     <li>パスに必要な建物情報が使用されている事</li>
	 *     <li>既存データが存在する場合でも枝番、メイン画像フラグが正しく設定される事</li>
	 * </ul>
	 */
	@Test
	public void addHousingImgFullTest2() throws Exception {

		// テストデータ作成
		initData();
		
		// テスト画像ファイル配置
		initImgFile();
		
		// 物件種別、市区町村CD 設定
		BuildingInfo buildingInfo = this.buildingInfoDAO.selectByPK("SYSBLD0001");
		buildingInfo.setHousingKindCd("123");
		buildingInfo.setAddressCd("12345");
		this.buildingInfoDAO.update(new BuildingInfo[]{buildingInfo});


		// フォームの作成
		HousingImgForm inputForm = this.formFactory.createHousingImgForm();

		inputForm.setSysHousingCd("SYSHOU00001");				// システム物件CD
		inputForm.setTempDate("20150101");						// 仮フォルダ名　（日付部分）
		
		String[] imageType = new String[3];						// 画像タイプ
		imageType[0] = "00";
		imageType[1] = "";
		imageType[2] = "01";
		inputForm.setImageType(imageType);

		String[] sortOrder = new String[3];						// 表示順 
		sortOrder[0] = "1";
		sortOrder[1] = "";
		sortOrder[2] = "2";
		inputForm.setSortOrder(sortOrder);

		String[] fileName = new String[3];						// ファイル名
		fileName[0] = "0000000001.jpg";
		fileName[1] = "";
		fileName[2] = "0000000002.jpg";
		inputForm.setFileName(fileName);

		String[] caption = new String[3];						// キャプション
		caption[0] = "キャプション１";
		caption[1] = "";
		caption[2] = "キャプション２";
		inputForm.setCaption(caption);

		String[] imgComment = new String[3];						// コメント
		imgComment[0] = "こめんと１";
		imgComment[1] = "";
		imgComment[2] = "こめんと２";
		inputForm.setImgComment(imgComment);
		
		
		// テストメソッド実行
		this.housingManage.addHousingImg(inputForm, "editUserId1");
		

		// 実行結果確認
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00001");
		criteria.addOrderByClause("imageType");
		criteria.addOrderByClause("divNo");
		List<HousingImageInfo> imgList = this.housingImageInfoDAO.selectByFilter(criteria);
		
		Assert.assertEquals("登録件数が正しし事", 2, imgList.size());
		
		Assert.assertEquals("パスが正しい事", "123/13/12345/SYSHOU00001/", imgList.get(0).getPathName());
		Assert.assertEquals("パスが正しい事", "123/13/12345/SYSHOU00001/", imgList.get(1).getPathName());

		
		// ２回目のデータ準備
		String[] imageType2 = new String[3];						// 画像タイプ
		imageType2[0] = "01";
		imageType2[1] = "";
		imageType2[2] = "01";
		inputForm.setImageType(imageType2);

		String[] sortOrder2 = new String[3];						// 表示順 
		sortOrder2[0] = "3";
		sortOrder2[1] = "";
		sortOrder2[2] = "4";
		inputForm.setSortOrder(sortOrder2);

		String[] fileName2 = new String[3];						// ファイル名
		fileName2[0] = "0000000003.jpg";
		fileName2[1] = "";
		fileName2[2] = "0000000004.jpg";
		inputForm.setFileName(fileName2);

		String[] caption2 = new String[3];						// キャプション
		caption2[0] = "キャプション３";
		caption2[1] = "";
		caption2[2] = "キャプション４";
		inputForm.setCaption(caption2);

		String[] imgComment2 = new String[3];						// コメント
		imgComment2[0] = "こめんと３";
		imgComment2[1] = "";
		imgComment2[2] = "こめんと４";
		inputForm.setImgComment(imgComment2);

		// テストメソッド実行
		this.housingManage.addHousingImg(inputForm, "editUserId2");
		
		
		// 実行結果確認
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00001");
		criteria.addOrderByClause("imageType");
		criteria.addOrderByClause("divNo");
		imgList = this.housingImageInfoDAO.selectByFilter(criteria);
		
		Assert.assertEquals("登録件数が正しし事", 4, imgList.size());
		
		Assert.assertEquals("画像タイプが正しい事", imageType[0], imgList.get(0).getImageType());
		Assert.assertEquals("画像タイプが正しい事", imageType[2], imgList.get(1).getImageType());
		Assert.assertEquals("画像タイプが正しい事", imageType2[0], imgList.get(2).getImageType());
		Assert.assertEquals("画像タイプが正しい事", imageType2[2], imgList.get(3).getImageType());
		
		Assert.assertEquals("枝番が正しい事", Integer.valueOf(1), imgList.get(0).getDivNo());
		Assert.assertEquals("枝番が正しい事", Integer.valueOf(1), imgList.get(1).getDivNo());
		Assert.assertEquals("枝番が正しい事", Integer.valueOf(2), imgList.get(2).getDivNo());
		Assert.assertEquals("枝番が正しい事", Integer.valueOf(3), imgList.get(3).getDivNo());

		Assert.assertEquals("表示順が正しい事", Integer.valueOf(sortOrder[0]), imgList.get(0).getSortOrder());
		Assert.assertEquals("表示順が正しい事", Integer.valueOf(sortOrder[2]), imgList.get(1).getSortOrder());
		Assert.assertEquals("表示順が正しい事", Integer.valueOf(sortOrder2[0]), imgList.get(2).getSortOrder());
		Assert.assertEquals("表示順が正しい事", Integer.valueOf(sortOrder2[2]), imgList.get(3).getSortOrder());

		Assert.assertEquals("パスが正しい事", "123/13/12345/SYSHOU00001/", imgList.get(0).getPathName());
		Assert.assertEquals("パスが正しい事", "123/13/12345/SYSHOU00001/", imgList.get(1).getPathName());
		Assert.assertEquals("パスが正しい事", "123/13/12345/SYSHOU00001/", imgList.get(2).getPathName());
		Assert.assertEquals("パスが正しい事", "123/13/12345/SYSHOU00001/", imgList.get(3).getPathName());

		Assert.assertEquals("ファイル名が正しい事", fileName[0], imgList.get(0).getFileName());
		Assert.assertEquals("ファイル名が正しい事", fileName[2], imgList.get(1).getFileName());
		Assert.assertEquals("ファイル名が正しい事", fileName2[0], imgList.get(2).getFileName());
		Assert.assertEquals("ファイル名が正しい事", fileName2[2], imgList.get(3).getFileName());

		Assert.assertEquals("メイン画像フラグが正しい事", "1", imgList.get(0).getMainImageFlg());
		Assert.assertEquals("メイン画像フラグが正しい事", "1", imgList.get(1).getMainImageFlg());
		Assert.assertEquals("メイン画像フラグが正しい事", "0", imgList.get(2).getMainImageFlg());
		Assert.assertEquals("メイン画像フラグが正しい事", "0", imgList.get(3).getMainImageFlg());

		Assert.assertEquals("キャプションが正しい事", caption[0], imgList.get(0).getCaption());
		Assert.assertEquals("キャプションが正しい事", caption[2], imgList.get(1).getCaption());
		Assert.assertEquals("キャプションが正しい事", caption2[0], imgList.get(2).getCaption());
		Assert.assertEquals("キャプションが正しい事", caption2[2], imgList.get(3).getCaption());

		Assert.assertEquals("コメントが正しい事", imgComment[0], imgList.get(0).getImgComment());
		Assert.assertEquals("コメントが正しい事", imgComment[2], imgList.get(1).getImgComment());
		Assert.assertEquals("コメントが正しい事", imgComment2[0], imgList.get(2).getImgComment());
		Assert.assertEquals("コメントが正しい事", imgComment2[2], imgList.get(3).getImgComment());

		Assert.assertEquals("縦長・横長フラグが正しい事", "1", imgList.get(0).getHwFlg());
		Assert.assertEquals("縦長・横長フラグが正しい事", "0", imgList.get(1).getHwFlg());
		Assert.assertEquals("縦長・横長フラグが正しい事", "1", imgList.get(2).getHwFlg());
		Assert.assertEquals("縦長・横長フラグが正しい事", "0", imgList.get(3).getHwFlg());
		
	}
	
	
	
	/**
	 * 物件画像情報追加処理（最小限の入力テスト）
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>登録件数が正しい事。　（空白行を除いて登録されている事。）</li>
	 *     <li>入力値で正しく登録されている事</li>
	 * </ul>
	 */
	@Test
	public void addHousingImgMinTest() throws Exception {

		// テストデータ作成
		initData();
		
		// テスト画像ファイル配置
		initImgFile();

		// フォームの作成
		HousingImgForm inputForm = this.formFactory.createHousingImgForm();

		inputForm.setSysHousingCd("SYSHOU00001");				// システム物件CD
		inputForm.setTempDate("20150101");						// 仮フォルダ名　（日付部分）
		
		String[] imageType = new String[3];						// 画像タイプ
		imageType[0] = "00";
		imageType[1] = "";
		imageType[2] = "";
		inputForm.setImageType(imageType);

		String[] sortOrder = new String[3];						// 表示順 
		sortOrder[0] = "";
		sortOrder[1] = "";
		sortOrder[2] = "";
		inputForm.setSortOrder(sortOrder);

		String[] fileName = new String[3];						// ファイル名
		fileName[0] = "0000000001.jpg";
		fileName[1] = "";
		fileName[2] = "";
		inputForm.setFileName(fileName);

		String[] caption = new String[3];						// キャプション
		caption[0] = "";
		caption[1] = "";
		caption[2] = "";
		inputForm.setCaption(caption);

		String[] imgComment = new String[3];						// コメント
		imgComment[0] = "";
		imgComment[1] = "";
		imgComment[2] = "";
		inputForm.setImgComment(imgComment);
		
		
		// テストメソッド実行
		this.housingManage.addHousingImg(inputForm, "editUserId1");

		// 実行結果確認
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00001");
		criteria.addOrderByClause("imageType");
		criteria.addOrderByClause("divNo");
		List<HousingImageInfo> imgList = this.housingImageInfoDAO.selectByFilter(criteria);
		
		Assert.assertEquals("登録件数が正しし事", 1, imgList.size());
		
		Assert.assertEquals("画像タイプが正しい事", imageType[0], imgList.get(0).getImageType());
		Assert.assertEquals("枝番が正しい事", Integer.valueOf(1), imgList.get(0).getDivNo());
		Assert.assertNull("表示順が null である事", imgList.get(0).getSortOrder());
		Assert.assertEquals("パスが正しい事", "999/13/99999/SYSHOU00001/", imgList.get(0).getPathName());
		Assert.assertEquals("ファイル名が正しい事", fileName[0], imgList.get(0).getFileName());
		Assert.assertEquals("メイン画像フラグが正しい事", "1", imgList.get(0).getMainImageFlg());
		Assert.assertNull("キャプションが null である事", imgList.get(0).getCaption());
		Assert.assertNull("コメントが null である事", imgList.get(0).getImgComment());
		Assert.assertEquals("縦長・横長フラグが正しい事", "1", imgList.get(0).getHwFlg());

	}


	
	/**
	 * 物件画像情報追加処理（親データなし）
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>NotFoundException がスローされる事</li>
	 * </ul>
	 */
	@Test(expected=NotFoundException.class)
	public void addHousingImgFailTest() throws Exception {

		// テスト画像ファイル配置
		initImgFile();

		// フォームの作成
		HousingImgForm inputForm = this.formFactory.createHousingImgForm();

		inputForm.setSysHousingCd("SYSHOU00001");				// システム物件CD
		inputForm.setTempDate("20150101");						// 仮フォルダ名　（日付部分）
		
		String[] imageType = new String[3];						// 画像タイプ
		imageType[0] = "00";
		imageType[1] = "";
		imageType[2] = "";
		inputForm.setImageType(imageType);

		String[] sortOrder = new String[3];						// 表示順 
		sortOrder[0] = "";
		sortOrder[1] = "";
		sortOrder[2] = "";
		inputForm.setSortOrder(sortOrder);

		String[] fileName = new String[3];						// ファイル名
		fileName[0] = "0000000001.jpg";
		fileName[1] = "";
		fileName[2] = "";
		inputForm.setFileName(fileName);

		String[] caption = new String[3];						// キャプション
		caption[0] = "";
		caption[1] = "";
		caption[2] = "";
		inputForm.setCaption(caption);

		String[] imgComment = new String[3];						// コメント
		imgComment[0] = "";
		imgComment[1] = "";
		imgComment[2] = "";
		inputForm.setImgComment(imgComment);
		
		
		// テストメソッド実行
		this.housingManage.addHousingImg(inputForm, "editUserId1");

	}

	
	/**
	 * 物件画像情報更新処理
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>削除フラグが指定されている画像情報が削除されている事</li>
	 *     <li>主キー値（画像タイプ）が変更可能である事</li>
	 *     <li>枝番が再構築されている事</li>
	 * </ul>
	 */
	@Test
	public void updHousingImgTest() throws Exception {

		// テスト用物件画像情報の登録
		initImgData();

		// 入力フォームを作成
		HousingImgForm form = this.formFactory.createHousingImgForm();

		// 画像１を削除
		// 画像３を別の画像タイプへ変更
		// 画像４の各フィールドを更新 （ファイル名等、一部の値は更新対象外）

		form.setSysHousingCd("SYSHOU00001");								// システム物件CD

		form.setImageType(new String[]{"00","00","02","01","01"});			// 画像タイプ
		form.setDivNo(new String[]{"1","2","1","2","3"});					// 枝番
		form.setSortOrder(new String[]{"1","2","1","3","4"});				// 表示順
		// ファイル名
		form.setFileName(new String[]{"FNAME0001","FNAME0002","FNAME0003","FNAME0004","FNAME0005"});
		// キャプション
		form.setCaption(new String[]{"CAP00001","CAP00002","CAP00003","CAP10004","CAP00005"});
		// コメント
		form.setImgComment(new String[]{"COM00001","COM00002","COM00003","COM10004","COM00005"});
		// 削除フラグ
		form.setDelFlg(new String[]{"1","0","0","0","0"});
		// 旧画像タイプ
		form.setOldImageType(new String[]{"00","00","01","01","01"});		// 画像タイプ
		
		
		// テストメソッドを実行
		this.housingManage.updHousingImg(form, "editUserId2");


		// 実行結果をチェック
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00001");
		
		List<HousingImageInfo> imgList = this.housingImageInfoDAO.selectByFilter(criteria);
		
		Assert.assertEquals("取得件数が正しい事", 4, imgList.size());
		
		
		for (HousingImageInfo imgInfo : imgList){

			if (imgInfo.getFileName().equals("FNAME0002")){
				Assert.assertEquals("画像タイプが正しい値である事", "00", imgInfo.getImageType());
				Assert.assertEquals("枝番が正しい値に採番しなおされている事", Integer.valueOf(1), imgInfo.getDivNo());
				Assert.assertEquals("表示順が正しい値である事", Integer.valueOf(2), imgInfo.getSortOrder());
				Assert.assertEquals("パス名が正しい値である事", "PATH00002", imgInfo.getPathName());
				Assert.assertEquals("メイン画像フラグが正しい値である事", "1", imgInfo.getMainImageFlg());
				Assert.assertEquals("キャプションが正しい値である事", "CAP00002", imgInfo.getCaption());
				Assert.assertEquals("コメントが正しい値である事", "COM00002", imgInfo.getImgComment());
				Assert.assertEquals("縦長・横長フラグが正しい値である事", "B", imgInfo.getHwFlg());

			} else if (imgInfo.getFileName().equals("FNAME0003")) {
				Assert.assertEquals("画像タイプが正しい値である事", "02", imgInfo.getImageType());
				Assert.assertEquals("枝番が正しい値に採番しなおされている事", Integer.valueOf(1), imgInfo.getDivNo());
				Assert.assertEquals("表示順が正しい値である事", Integer.valueOf(1), imgInfo.getSortOrder());
				Assert.assertEquals("パス名が正しい値である事", "PATH00003", imgInfo.getPathName());
				Assert.assertEquals("メイン画像フラグが正しい値である事", "1", imgInfo.getMainImageFlg());
				Assert.assertEquals("キャプションが正しい値である事", "CAP00003", imgInfo.getCaption());
				Assert.assertEquals("コメントが正しい値である事", "COM00003", imgInfo.getImgComment());
				Assert.assertEquals("縦長・横長フラグが正しい値である事", "C", imgInfo.getHwFlg());

			} else if (imgInfo.getFileName().equals("FNAME0004")) {
				Assert.assertEquals("画像タイプが正しい値である事", "01", imgInfo.getImageType());
				Assert.assertEquals("枝番が正しい値に採番しなおされている事", Integer.valueOf(1), imgInfo.getDivNo());
				Assert.assertEquals("表示順が正しい値である事", Integer.valueOf(3), imgInfo.getSortOrder());
				Assert.assertEquals("パス名が正しい値である事", "PATH00004", imgInfo.getPathName());
				Assert.assertEquals("メイン画像フラグが正しい値である事", "1", imgInfo.getMainImageFlg());
				Assert.assertEquals("キャプションが正しい値である事", "CAP10004", imgInfo.getCaption());
				Assert.assertEquals("コメントが正しい値である事", "COM10004", imgInfo.getImgComment());
				Assert.assertEquals("縦長・横長フラグが正しい値である事", "D", imgInfo.getHwFlg());

			} else if (imgInfo.getFileName().equals("FNAME0005")) {
				Assert.assertEquals("画像タイプが正しい値である事", "01", imgInfo.getImageType());
				Assert.assertEquals("枝番が正しい値に採番しなおされている事", Integer.valueOf(2), imgInfo.getDivNo());
				Assert.assertEquals("表示順が正しい値である事", Integer.valueOf(4), imgInfo.getSortOrder());
				Assert.assertEquals("パス名が正しい値である事", "PATH00005", imgInfo.getPathName());
				Assert.assertEquals("メイン画像フラグが正しい値である事", "0", imgInfo.getMainImageFlg());
				Assert.assertEquals("キャプションが正しい値である事", "CAP00005", imgInfo.getCaption());
				Assert.assertEquals("コメントが正しい値である事", "COM00005", imgInfo.getImgComment());
				Assert.assertEquals("縦長・横長フラグが正しい値である事", "E", imgInfo.getHwFlg());

			} else {
				Assert.fail("誤ったレコードが削除されている : " + imgInfo.getFileName());
			}
		}
		
	}

	
	
	/**
	 * 物件画像情報更新処理（表示順入れ替えによる主キー重複対応の確認）
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>同一システム物件番号、画像タイプで表示順を入れ替えても枝番の重複でエラーにならない事</li>
	 *     <li>他のシステム物件番号のデータが影響を受けていない事</li>
	 * </ul>
	 */
	@Test
	public void updHousingImgTest2() throws Exception {

		// テスト用物件画像情報の登録
		initImgData();

		// 更新対象データのパス名の期待値　（変更されていない事。　ただし、３番目と４番目の順番が入れ替わる。）
		String[] updTargetPath = {"PATH00001", "PATH00002", "PATH00004", "PATH00003", "PATH00005"};
		// 更新対象データのファイル名の期待値　（変更されていない事。　ただし、３番目と４番目の順番が入れ替わる。）
		String[] updTargetFile = {"FNAME0001", "FNAME0002", "FNAME0004", "FNAME0003", "FNAME0005"};
		// 更新対象データのキャプションの期待値　（入力値で変更されている事。　ただし、３番目と４番目の順番が入れ替わる。）
		String[] updTargetCap = {"CAP00001", "CAP00002", "CAP10004", "CAP10003", "CAP00005"};
		// 更新対象データのコメントの期待値　（入力値で変更されている事。　ただし、３番目と４番目の順番が入れ替わる。）
		String[] updTargetCom = {"COM00001", "COM00002", "COM10004", "COM10003", "COM00005"};
		// 更新対象データの縦長・横長フラグの期待値　（変更されていない事。　ただし、３番目と４番目の順番が入れ替わる。）
		String[] updHwFlg = {"A", "B", "D", "C", "E"};
		// 更新対象データのメイン画像フラグの期待値
		String[] updMainFlg = {"1", "0", "1", "0", "0"};
		
		
		// 入力フォームを作成
		HousingImgForm form = this.formFactory.createHousingImgForm();

		// 画像３と４の表示順を入れ替え
		// 他のフィールドもちゃんと更新されている事を確認する為、キャプション、コメントの値を変更

		form.setSysHousingCd("SYSHOU00001");								// システム物件CD

		form.setImageType(new String[]{"00","00","01","01","01"});			// 画像タイプ
		form.setDivNo(new String[]{"1","2","1","2","3"});					// 枝番
		form.setSortOrder(new String[]{"1","2","2","1","3"});				// 表示順
		// ファイル名
		form.setFileName(new String[]{"FNAME0001","FNAME0002","FNAME0003","FNAME0004","FNAME0005"});
		// キャプション
		form.setCaption(new String[]{"CAP00001","CAP00002","CAP10003","CAP10004","CAP00005"});
		// コメント
		form.setImgComment(new String[]{"COM00001","COM00002","COM10003","COM10004","COM00005"});
		// 削除フラグ
		form.setDelFlg(new String[]{"0","0","0","0","0"});
		// 旧画像タイプ
		form.setOldImageType(new String[]{"00","00","01","01","01"});		// 画像タイプ

	
		// テストメソッドを実行
		this.housingManage.updHousingImg(form, "editUserId2");

		
		// 更新結果データを取得
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00001");
		criteria.addOrderByClause("imageType");
		criteria.addOrderByClause("divNo");

		// 更新対象データの確認
		List<HousingImageInfo> list = this.housingImageInfoDAO.selectByFilter(criteria);
		int idx = 0;
		for (HousingImageInfo info : list){

			Assert.assertEquals("画像タイプが登録した値と一致する事(" + idx + ")", form.getImageType()[idx], info.getImageType());
			Assert.assertEquals("枝番が想定値である事(" + idx + ")", Integer.valueOf(form.getDivNo()[idx]), info.getDivNo());

			// 枝番順でソートして値を取得しているので、順番が逆転している。　このケースの場合、表示順は枝番と同じ値になるので、枝番と比較する。
			Assert.assertEquals("表示順が登録した値と一致する事(" + idx + ")", Integer.valueOf(form.getDivNo()[idx]), info.getSortOrder());
			Assert.assertEquals("メイン画像フラグが想定値である事(" + idx + ")", updMainFlg[idx], info.getMainImageFlg());

			Assert.assertEquals("パス名が変化していない事(" + idx + ")", updTargetPath[idx], info.getPathName());
			Assert.assertEquals("ファイル名が変化していない事(" + idx + ")", updTargetFile[idx], info.getFileName());
			Assert.assertEquals("キャプションが登録した値と一致する事(" + idx + ")", updTargetCap[idx], info.getCaption());
			Assert.assertEquals("コメントが登録した値と一致する事(" + idx + ")", updTargetCom[idx], info.getImgComment());
			Assert.assertEquals("縦長・横長フラグが変化していない事(" + idx + ")", updHwFlg[idx], info.getHwFlg());

			++idx;
		}

		
		// 更新対象外データの確認
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00002");

		list = this.housingImageInfoDAO.selectByFilter(criteria);

		Assert.assertEquals("更新対象外データの画像タイプが更新されていない事", "00", list.get(0).getImageType());
		Assert.assertEquals("更新対象外データの枝番が更新されていない事", Integer.valueOf(1), list.get(0).getDivNo());
		Assert.assertEquals("更新対象外データの表示順が更新されていない事", Integer.valueOf(1), list.get(0).getSortOrder());
		Assert.assertEquals("更新対象外データのメイン画像フラグが更新されていない事", "1", list.get(0).getMainImageFlg());
		Assert.assertEquals("更新対象外データのパス名が更新されていない事", "PATH00006", list.get(0).getPathName());
		Assert.assertEquals("更新対象外データのファイル名が更新されていない事", "FNAME0006", list.get(0).getFileName());
		Assert.assertEquals("更新対象外データのキャプションが更新されていない事", "CAP00006", list.get(0).getCaption());
		Assert.assertEquals("更新対象外データのコメントが更新されていない事", "COM00006", list.get(0).getImgComment());
		Assert.assertEquals("更新対象外データの縦長・横長フラグが更新されていない事", "F", list.get(0).getHwFlg());

	}
	
	
	


	// テストデータ登録
	private void initData() {
		
		HousingInfo housingInfo;

		//　物件基本情報（更新対象）
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SYSHOU00001");
		housingInfo.setSysBuildingCd("SYSBLD0001");
		housingInfo.setDisplayHousingName("OLD物件名１");
		housingInfo.setIconCd("dummyIcon1");			// 更新対象外フィールドなので、値を設定
		Date date = new Date();
		housingInfo.setInsDate(DateUtils.addDays(date, -10));
		housingInfo.setInsUserId("dummInsID1");

		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});


		//　物件基本情報（更新対象外）
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SYSHOU00002");
		housingInfo.setSysBuildingCd("SYSBLD0001");
		housingInfo.setDisplayHousingName("OLD物件名２");
		housingInfo.setIconCd("dummyIcon2");
		housingInfo.setInsDate(DateUtils.addDays(date, -10));
		housingInfo.setInsUserId("dummInsID2");

		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

	}

	
	// 物件詳細のテスト用入力フォーム作成
	private HousingDtlForm initInputDtlForm(String sysHousingCd){
		
		// 入力フォームを生成して初期値を設定
		HousingDtlForm inputForm = this.formFactory.createHousingDtlForm();

		inputForm.setSysHousingCd(sysHousingCd);					// システム物件CD
		inputForm.setUsedAreaCd("Y01");								// 用途地域CD
		inputForm.setTransactTypeDiv("T01");						// 取引形態区分
		inputForm.setDisplayContractTerm("表示用契約期間１");			// 表示用契約期間
		inputForm.setLandRight("土地権利１");							// 土地権利
		inputForm.setMoveinTiming("01");							// 入居可能時期フラグ

		String date = StringUtils.dateToString(DateUtils.addDays(new Date(), 10));
		inputForm.setMoveinTimingDay(date);							// 入居可能時期

		inputForm.setMoveinNote("入居可能時期コメント１");				// 入居可能時期コメント
		inputForm.setDisplayMoveinTiming("表示用入居可能時期１");		// 表示用入居可能時期
		inputForm.setDisplayMoveinProviso("表示用入居諸条件１");		// 表示用入居諸条件
		inputForm.setUpkeepType("管理形態・方式１");					// 管理形態・方式
		inputForm.setUpkeepCorp("管理会社１");						// 管理会社
		inputForm.setRenewChrg("1000.01");							// 更新料
		inputForm.setRenewChrgCrs("R1");							// 更新料単位
		inputForm.setRenewChrgName("更新料名１");						// 更新料名
		inputForm.setRenewDue("2000.01");							// 更新手数料
		inputForm.setRenewDueCrs("D1");								// 更新手数料単位
		inputForm.setBrokerageChrg("3000.01");						// 仲介手数料
		inputForm.setBrokerageChrgCrs("B1");						// 仲介手数料単位
		inputForm.setChangeKeyChrg("4000.01");						// 鍵交換料
		inputForm.setInsurExist("B");								// 損保有無
		inputForm.setInsurChrg("5000");								// 損保料金
		inputForm.setInsurTerm("200");								// 損保年数
		inputForm.setInsurLank("L01");								// 損保認定ランク
		inputForm.setOtherChrg("6000");								// 諸費用
		inputForm.setContactRoad("接道状況１");						// 接道状況
		inputForm.setContactRoadDir("接道方向/幅員１");				// 接道方向/幅員
		inputForm.setPrivateRoad("私道負担１");						// 私道負担
		inputForm.setBalconyArea("7000.01");						// バルコニー面積
		inputForm.setSpecialInstruction("特記事項１");				// 特記事項
		inputForm.setDtlComment("詳細コメント１");						// 詳細コメント 

		return inputForm;
	}



	// 物件詳細情報の登録情報チェック
	private void chkHousingDtl(String sysHousingCd){

		HousingDtlInfo housingDtlInfo = this.housingDtlInfoDAO.selectByPK(sysHousingCd);

		Assert.assertEquals("用途地域CDの登録値が正しい事", "Y01", housingDtlInfo.getUsedAreaCd());
		Assert.assertEquals("取引形態区分の登録値が正しい事", "T01", housingDtlInfo.getTransactTypeDiv());
		Assert.assertEquals("表示用契約期間の登録値が正しい事", "表示用契約期間１", housingDtlInfo.getDisplayContractTerm());
		Assert.assertEquals("土地権利の登録値が正しい事", "土地権利１", housingDtlInfo.getLandRight());
		Assert.assertEquals("入居可能時期フラグが正しい事", "01", housingDtlInfo.getMoveinTiming());

		String date10 = StringUtils.dateToString(DateUtils.addDays(new Date(), 10));
		String date = StringUtils.dateToString(housingDtlInfo.getMoveinTimingDay());
		Assert.assertEquals("入居可能時期が正しい事", date10, date);
		
		Assert.assertEquals("入居可能時期コメントが正しい事", "入居可能時期コメント１", housingDtlInfo.getMoveinNote());
		Assert.assertEquals("表示用入居可能時期が正しい事", "表示用入居可能時期１", housingDtlInfo.getDisplayMoveinTiming());
		Assert.assertEquals("表示用入居諸条件が正しい事", "表示用入居諸条件１", housingDtlInfo.getDisplayMoveinProviso());
		Assert.assertEquals("管理形態・方式が正しい事", "管理形態・方式１", housingDtlInfo.getUpkeepType());
		Assert.assertEquals("管理会社が正しい事", "管理会社１", housingDtlInfo.getUpkeepCorp());
		Assert.assertEquals("更新料が正しい事", new BigDecimal("1000.01"), housingDtlInfo.getRenewChrg());
		Assert.assertEquals("更新料単位が正しい事", "R1", housingDtlInfo.getRenewChrgCrs());
		Assert.assertEquals("更新料名が正しい事", "更新料名１", housingDtlInfo.getRenewChrgName());
		Assert.assertEquals("更新手数料が正しい事", new BigDecimal("2000.01"), housingDtlInfo.getRenewDue());
		Assert.assertEquals("更新手数料単位が正しい事", "D1", housingDtlInfo.getRenewDueCrs());
		Assert.assertEquals("仲介手数料が正しい事", new BigDecimal("3000.01"), housingDtlInfo.getBrokerageChrg());
		Assert.assertEquals("仲介手数料単位が正しい事", "B1", housingDtlInfo.getBrokerageChrgCrs());
		Assert.assertEquals("鍵交換料が正しい事", new BigDecimal("4000.01"), housingDtlInfo.getChangeKeyChrg());
		Assert.assertEquals("損保有無が正しい事", "B", housingDtlInfo.getInsurExist());
		Assert.assertEquals("損保料金が正しい事", Long.valueOf("5000"), housingDtlInfo.getInsurChrg());
		Assert.assertEquals("損保年数が正しい事", Integer.valueOf(200), housingDtlInfo.getInsurTerm());
		Assert.assertEquals("損保認定ランクが正しい事", "L01", housingDtlInfo.getInsurLank());
		Assert.assertEquals("諸費用が正しい事", Long.valueOf(6000), housingDtlInfo.getOtherChrg());
		Assert.assertEquals("接道状況が正しい事", "接道状況１", housingDtlInfo.getContactRoad());
		Assert.assertEquals("接道方向/幅員が正しい事", "接道方向/幅員１", housingDtlInfo.getContactRoadDir());
		Assert.assertEquals("私道負担が正しい事", "私道負担１", housingDtlInfo.getPrivateRoad());
		Assert.assertEquals("バルコニー面積が正しい事", new BigDecimal("7000.01"), housingDtlInfo.getBalconyArea());
		Assert.assertEquals("特記事項が正しい事", "特記事項１", housingDtlInfo.getSpecialInstruction());
		Assert.assertEquals("詳細コメントが正しい事", "詳細コメント１", housingDtlInfo.getDtlComment());

	}

	// 物件基本情報の更新日付チェック
	private void chkHousingTimestamp(String sysHousingCd, String editUserId){
		
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK(sysHousingCd);

		// 登録日が変更されていないこと
		String date10 = StringUtils.dateToString(DateUtils.addDays(new Date(), -10));

		String date = StringUtils.dateToString(housingInfo.getInsDate());
		Assert.assertEquals("登録日が更新されていない事", date10, date);
		Assert.assertEquals("登録者が更新されていない事", "dummInsID1", housingInfo.getInsUserId());

		String now = StringUtils.dateToString(new Date());
		date = StringUtils.dateToString(housingInfo.getUpdDate());
		Assert.assertEquals("更新日が更新されている事", now, date);
		Assert.assertEquals("更新者が更新されている事", editUserId, housingInfo.getUpdUserId());
	}



	private void initImgData(){

		// テスト対象となる物件基本情報を作成する。
		initData();

		HousingImageInfo housingImageInfo = new HousingImageInfo();

		// 物件画像１
		housingImageInfo.setSysHousingCd("SYSHOU00001");				// システム物件CD
		housingImageInfo.setImageType("00");							// 画像タイプ
		housingImageInfo.setDivNo(1);									// 枝番
		housingImageInfo.setSortOrder(1);								// 表示順
		housingImageInfo.setPathName("PATH00001");						// パス名
		housingImageInfo.setFileName("FNAME0001");						// ファイル名
		housingImageInfo.setMainImageFlg("1");							// メイン画像フラグ
		housingImageInfo.setCaption("CAP00001");						// キャプション
		housingImageInfo.setImgComment("COM00001");						// コメント
		housingImageInfo.setHwFlg("A");									// 縦長・横長フラグ

		this.housingImageInfoDAO.insert(new HousingImageInfo[]{housingImageInfo});


		// 物件画像２
		housingImageInfo.setSysHousingCd("SYSHOU00001");				// システム物件CD
		housingImageInfo.setImageType("00");							// 画像タイプ
		housingImageInfo.setDivNo(2);									// 枝番
		housingImageInfo.setSortOrder(2);								// 表示順
		housingImageInfo.setPathName("PATH00002");						// パス名
		housingImageInfo.setFileName("FNAME0002");						// ファイル名
		housingImageInfo.setMainImageFlg("0");							// メイン画像フラグ
		housingImageInfo.setCaption("CAP00002");						// キャプション
		housingImageInfo.setImgComment("COM00002");						// コメント
		housingImageInfo.setHwFlg("B");									// 縦長・横長フラグ

		this.housingImageInfoDAO.insert(new HousingImageInfo[]{housingImageInfo});
		

		// 物件画像３
		housingImageInfo.setSysHousingCd("SYSHOU00001");				// システム物件CD
		housingImageInfo.setImageType("01");							// 画像タイプ
		housingImageInfo.setDivNo(1);									// 枝番
		housingImageInfo.setSortOrder(1);								// 表示順
		housingImageInfo.setPathName("PATH00003");						// パス名
		housingImageInfo.setFileName("FNAME0003");						// ファイル名
		housingImageInfo.setMainImageFlg("1");							// メイン画像フラグ
		housingImageInfo.setCaption("CAP00003");						// キャプション
		housingImageInfo.setImgComment("COM00003");						// コメント
		housingImageInfo.setHwFlg("C");									// 縦長・横長フラグ

		this.housingImageInfoDAO.insert(new HousingImageInfo[]{housingImageInfo});

		
		// 物件画像４
		housingImageInfo.setSysHousingCd("SYSHOU00001");				// システム物件CD
		housingImageInfo.setImageType("01");							// 画像タイプ
		housingImageInfo.setDivNo(2);									// 枝番
		housingImageInfo.setSortOrder(2);								// 表示順
		housingImageInfo.setPathName("PATH00004");						// パス名
		housingImageInfo.setFileName("FNAME0004");						// ファイル名
		housingImageInfo.setMainImageFlg("0");							// メイン画像フラグ
		housingImageInfo.setCaption("CAP00004");						// キャプション
		housingImageInfo.setImgComment("COM00004");						// コメント
		housingImageInfo.setHwFlg("D");									// 縦長・横長フラグ

		this.housingImageInfoDAO.insert(new HousingImageInfo[]{housingImageInfo});

		
		// 物件画像５
		housingImageInfo.setSysHousingCd("SYSHOU00001");				// システム物件CD
		housingImageInfo.setImageType("01");							// 画像タイプ
		housingImageInfo.setDivNo(3);									// 枝番
		housingImageInfo.setSortOrder(3);								// 表示順
		housingImageInfo.setPathName("PATH00005");						// パス名
		housingImageInfo.setFileName("FNAME0005");						// ファイル名
		housingImageInfo.setMainImageFlg("0");							// メイン画像フラグ
		housingImageInfo.setCaption("CAP00005");						// キャプション
		housingImageInfo.setImgComment("COM00005");						// コメント
		housingImageInfo.setHwFlg("E");									// 縦長・横長フラグ

		this.housingImageInfoDAO.insert(new HousingImageInfo[]{housingImageInfo});


		// 物件画像６（更新対象外物件）
		housingImageInfo.setSysHousingCd("SYSHOU00002");				// システム物件CD
		housingImageInfo.setImageType("00");							// 画像タイプ
		housingImageInfo.setDivNo(1);									// 枝番
		housingImageInfo.setSortOrder(1);								// 表示順
		housingImageInfo.setPathName("PATH00006");						// パス名
		housingImageInfo.setFileName("FNAME0006");						// ファイル名
		housingImageInfo.setMainImageFlg("1");							// メイン画像フラグ
		housingImageInfo.setCaption("CAP00006");						// キャプション
		housingImageInfo.setImgComment("COM00006");						// コメント
		housingImageInfo.setHwFlg("F");									// 縦長・横長フラグ

		this.housingImageInfoDAO.insert(new HousingImageInfo[]{housingImageInfo});

	}
	
	
	
	private void initImgFile() throws Exception{
		// 実行時のルートディレクトリ
		String root = (String) System.getProperties().get("user.dir");
		
		
		String tempRootDir = root + "/junitTest/upload/temp/";
		String testDataDir = root + "/junitTest/upload/testData/";
		
		// 仮フォルダ内を削除する。
		FileUtils.cleanDirectory(new File(tempRootDir));

		// 日付毎の仮フォルダを作成する
		FileUtils.forceMkdir(new File(tempRootDir + "20150101"));

		// 仮フォルダ内にテストデータを配置する。
		FileUtils.copyDirectory(new File(testDataDir), new File(tempRootDir + "20150101"));

	}
}
