package jp.co.transcosmos.dm3.test.core.model.housingRequest;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.adminCore.request.form.HousingRequestForm;
import jp.co.transcosmos.dm3.core.model.HousingRequestManage;
import jp.co.transcosmos.dm3.core.model.exception.MaxEntryOverException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.housingRequest.HousingRequest;
import jp.co.transcosmos.dm3.core.model.housingRequest.form.RequestSearchForm;
import jp.co.transcosmos.dm3.core.model.housingRequest.form.RequestSearchFormFactory;
import jp.co.transcosmos.dm3.core.vo.AddressMst;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingPartInfo;
import jp.co.transcosmos.dm3.core.vo.HousingReqKind;
import jp.co.transcosmos.dm3.core.vo.HousingReqLayout;
import jp.co.transcosmos.dm3.core.vo.HousingReqPart;
import jp.co.transcosmos.dm3.core.vo.HousingReqRoute;
import jp.co.transcosmos.dm3.core.vo.HousingReqStation;
import jp.co.transcosmos.dm3.core.vo.HousingRequestArea;
import jp.co.transcosmos.dm3.core.vo.HousingRequestInfo;
import jp.co.transcosmos.dm3.core.vo.MemberInfo;
import jp.co.transcosmos.dm3.core.vo.PartSrchMst;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
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
 * 物件リクエスト情報 model のテストケース
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")

public class HousingRequestManageTest {

	@Autowired
	private HousingRequestManage housingRequestManage;
	@Autowired
	private RequestSearchFormFactory requestSearchFormFactory;

	@Autowired
	private DAO<UserInfo> userInfoDAO;
	@Autowired
	private DAO<MemberInfo> memberInfoDAO;

	@Autowired
	private DAO<HousingRequestInfo> housingRequestInfoDAO;
	@Autowired
	private DAO<HousingRequestArea> housingRequestAreaDAO;
	@Autowired
	private DAO<HousingReqRoute> housingReqRouteDAO;
	@Autowired
	private DAO<HousingReqStation> housingReqStationDAO;
	@Autowired
	private DAO<HousingReqKind> housingReqKindDAO;
	@Autowired
	private DAO<HousingReqLayout> housingReqLayoutDAO;
	@Autowired
	private DAO<HousingReqPart> housingReqPartDAO;

	@Autowired
	private DAO<AddressMst> addressMstDAO;
	@Autowired
	private DAO<RouteMst> routeMstDAO;
	@Autowired
	private DAO<StationMst> stationMstDAO;
	@Autowired
	private DAO<JoinResult> stationRouteRelationDAO;
	@Autowired
	private DAO<PartSrchMst> partSrchMstDAO;

	@Autowired
	private DAO<BuildingInfo> buildingInfoDAO;
	@Autowired
	private DAO<HousingInfo> housingInfoDAO;
	@Autowired
	private DAO<BuildingStationInfo> buildingStationInfoDAO;
	@Autowired
	private DAO<HousingPartInfo> housingPartInfoDAO;

	// 前処理
	@Before
	public void init() {
		// 関連するデータの全件削除
		DAOCriteria criteria = new DAOCriteria();
		this.userInfoDAO.deleteByFilter(criteria);
		this.memberInfoDAO.deleteByFilter(criteria);

		this.housingRequestInfoDAO.deleteByFilter(criteria);
		this.housingRequestAreaDAO.deleteByFilter(criteria);
		this.housingReqRouteDAO.deleteByFilter(criteria);
		this.housingReqStationDAO.deleteByFilter(criteria);
		this.housingReqKindDAO.deleteByFilter(criteria);
		this.housingReqLayoutDAO.deleteByFilter(criteria);
		this.housingReqLayoutDAO.deleteByFilter(criteria);
		this.housingReqPartDAO.deleteByFilter(criteria);

		this.housingInfoDAO.deleteByFilter(criteria);
		this.buildingInfoDAO.deleteByFilter(criteria);
		this.buildingStationInfoDAO.deleteByFilter(criteria);
		this.housingPartInfoDAO.deleteByFilter(criteria);

		initTestData();
	}

	// テストデータ作成
	private void initTestData() {

		UserInfo userInfo;
		MemberInfo memberInfo;

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

		userInfo = new UserInfo();
		userInfo.setUserId("UID00004");
		userInfoDAO.insert(new UserInfo[] { userInfo });

		userInfo = new UserInfo();
		userInfo.setUserId("UID00005");
		userInfoDAO.insert(new UserInfo[] { userInfo });

		userInfo = new UserInfo();
		userInfo.setUserId("UID00006");
		userInfoDAO.insert(new UserInfo[] { userInfo });

		userInfo = new UserInfo();
		userInfo.setUserId("UID00007");
		userInfoDAO.insert(new UserInfo[] { userInfo });

		userInfo = new UserInfo();
		userInfo.setUserId("UID00008");
		userInfoDAO.insert(new UserInfo[] { userInfo });

		userInfo = new UserInfo();
		userInfo.setUserId("UID00009");
		userInfoDAO.insert(new UserInfo[] { userInfo });

		userInfo = new UserInfo();
		userInfo.setUserId("UID00010");
		userInfoDAO.insert(new UserInfo[] { userInfo });

		userInfo = new UserInfo();
		userInfo.setUserId("UID00011");
		userInfoDAO.insert(new UserInfo[] { userInfo });

		// マイページ会員情報
		memberInfo = new MemberInfo();
		memberInfo.setUserId("UID00001");
		memberInfo.setEmail("UID00001@mail.co.jp");
		memberInfoDAO.insert(new MemberInfo[] { memberInfo });

		memberInfo = new MemberInfo();
		memberInfo.setUserId("UID00002");
		memberInfo.setEmail("UID00002@mail.co.jp");
		memberInfoDAO.insert(new MemberInfo[] { memberInfo });

		memberInfo = new MemberInfo();
		memberInfo.setUserId("UID00003");
		memberInfo.setEmail("UID00003@mail.co.jp");
		memberInfoDAO.insert(new MemberInfo[] { memberInfo });

		memberInfo = new MemberInfo();
		memberInfo.setUserId("UID00004");
		memberInfo.setEmail("UID00004@mail.co.jp");
		memberInfoDAO.insert(new MemberInfo[] { memberInfo });

		memberInfo = new MemberInfo();
		memberInfo.setUserId("UID00005");
		memberInfo.setEmail("UID00005@mail.co.jp");
		memberInfoDAO.insert(new MemberInfo[] { memberInfo });

		memberInfo = new MemberInfo();
		memberInfo.setUserId("UID00006");
		memberInfo.setEmail("UID00006@mail.co.jp");
		memberInfoDAO.insert(new MemberInfo[] { memberInfo });

		memberInfo = new MemberInfo();
		memberInfo.setUserId("UID00007");
		memberInfo.setEmail("UID00007@mail.co.jp");
		memberInfoDAO.insert(new MemberInfo[] { memberInfo });

		memberInfo = new MemberInfo();
		memberInfo.setUserId("UID00008");
		memberInfo.setEmail("UID00008@mail.co.jp");
		memberInfoDAO.insert(new MemberInfo[] { memberInfo });

		memberInfo = new MemberInfo();
		memberInfo.setUserId("UID00009");
		memberInfo.setEmail("UID00009@mail.co.jp");
		memberInfoDAO.insert(new MemberInfo[] { memberInfo });

		memberInfo = new MemberInfo();
		memberInfo.setUserId("UID00010");
		memberInfo.setEmail("UID00010@mail.co.jp");
		memberInfoDAO.insert(new MemberInfo[] { memberInfo });

		memberInfo = new MemberInfo();
		memberInfo.setUserId("UID00011");
		memberInfo.setEmail("UID00011@mail.co.jp");
		memberInfoDAO.insert(new MemberInfo[] { memberInfo });

	}

	/**
	 * 物件リクエスト情報の登録処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>パラメタが正しく設定されていない場合、物件リクエスト情報は登録せず、正常終了する事</li>
	 *     <li>物件リクエスト情報のレコードが作られる事</li>
	 * </ul>
	 */
	@Test
	public void addRequestParamCheckTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingRequestInfo> housingRequestInfos;

		// テストケース実施前確認
		criteria = new DAOCriteria();
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("【テストケース実施前確認】物件リクエスト情報が0件である事。", 0, housingRequestInfos.size());

		HousingRequestForm inputForm;

		// ユーザIDが空の場合、レコードが作られない事
		inputForm = new HousingRequestForm();
		housingRequestManage.addRequest("", inputForm);
		criteria = new DAOCriteria();
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("パラメタが正しく設定されていない場合、物件リクエスト情報は登録せず、正常終了する事", 0, housingRequestInfos.size());

		// ユーザIDがnullの場合、レコードが作られない事
		inputForm = new HousingRequestForm();
		housingRequestManage.addRequest(null, inputForm);
		criteria = new DAOCriteria();
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("パラメタが正しく設定されていない場合、物件リクエスト情報は登録せず、正常終了する事", 0, housingRequestInfos.size());

		// ユーザIDが存在しない場合、レコードが作られない事
		inputForm = new HousingRequestForm();
		housingRequestManage.addRequest("UID00000", inputForm);
		criteria = new DAOCriteria();
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("パラメタが正しく設定されていない場合、物件リクエスト情報は登録せず、正常終了する事", 0, housingRequestInfos.size());

	}

	/**
	 * 物件リクエスト情報の登録処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>物件リクエスト情報が正しく登録される事</li>
	 * </ul>
	 */
	@Test
	public void addRequestTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingRequestInfo> housingRequestInfos;

		HousingRequestForm inputForm;

		inputForm = new HousingRequestForm();
		inputForm.setRequestName("リクエスト名称");
		inputForm.setPriceLower(10000L);
		inputForm.setPriceUpper(20000L);
		inputForm.setPersonalAreaLower(new BigDecimal("20.00"));
		inputForm.setPersonalAreaUpper(new BigDecimal("50.00"));
		housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00001");
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 

		Assert.assertEquals("物件リクエストが正しく登録される事", 1, housingRequestInfos.size());
		HousingRequestInfo housingRequestInfo = housingRequestInfos.get(0);
		Assert.assertEquals("物件リクエスト情報のユーザIDが正しく登録されている事。", "UID00001", housingRequestInfo.getUserId());
		Assert.assertEquals("物件リクエスト情報のリクエスト名称が正しく登録されている事。", "リクエスト名称", housingRequestInfo.getRequestName());
		Assert.assertEquals("物件リクエスト情報の賃料/価格・下限が正しく登録されている事。", 10000L, housingRequestInfo.getPriceLower().longValue());
		Assert.assertEquals("物件リクエスト情報の賃料/価格・上限が正しく登録されている事。", 20000L, housingRequestInfo.getPriceUpper().longValue());
		Assert.assertTrue("物件リクエスト情報の専有面積・下限が正しく登録されている事。", new BigDecimal("20.00").compareTo(housingRequestInfo.getPersonalAreaLower()) == 0);
		Assert.assertTrue("物件リクエスト情報の専有面積・上限が正しく登録されている事。", new BigDecimal("50.00").compareTo(housingRequestInfo.getPersonalAreaUpper()) == 0);
		Assert.assertEquals("物件リクエスト情報の登録者が正しく登録されている事。", "UID00001", housingRequestInfo.getInsUserId());
		Assert.assertEquals("物件リクエスト情報の最終更新者が正しく登録されている事。", "UID00001", housingRequestInfo.getUpdUserId());

	}

	/**
	 * 物件リクエスト情報の登録処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>物件リクエスト情報の上限件数が正しく動作する事</li>
	 * </ul>
	 */
	@Test
	public void addRequestMaxCntTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingRequestInfo> housingRequestInfos;

		HousingRequestForm inputForm;

		inputForm = new HousingRequestForm();
		inputForm.setRequestName("リクエスト名称１");
		housingRequestManage.addRequest("UID00001", inputForm);

		inputForm = new HousingRequestForm();
		inputForm.setRequestName("リクエスト名称２");
		housingRequestManage.addRequest("UID00001", inputForm);

		inputForm = new HousingRequestForm();
		inputForm.setRequestName("リクエスト名称３");
		housingRequestManage.addRequest("UID00001", inputForm);

		inputForm = new HousingRequestForm();
		inputForm.setRequestName("リクエスト名称４");
		housingRequestManage.addRequest("UID00001", inputForm);

		inputForm = new HousingRequestForm();
		inputForm.setRequestName("リクエスト名称５");
		housingRequestManage.addRequest("UID00001", inputForm);

		try {
			inputForm = new HousingRequestForm();
			inputForm.setRequestName("リクエスト名称６");
			housingRequestManage.addRequest("UID00001", inputForm);
		} catch (MaxEntryOverException e) {
			Assert.assertTrue("上限件数に達している場合、MaxEntryOverExceptionがthrowされること", e instanceof MaxEntryOverException);
		}

		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00001");
		criteria.addOrderByClause("housingRequestId");
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエストが正しく登録される事", 5, housingRequestInfos.size());
		Assert.assertEquals("物件リクエスト情報のリクエスト名称が正しく登録されている事。", "リクエスト名称１", housingRequestInfos.get(0).getRequestName());
		Assert.assertEquals("物件リクエスト情報のリクエスト名称が正しく登録されている事。", "リクエスト名称２", housingRequestInfos.get(1).getRequestName());
		Assert.assertEquals("物件リクエスト情報のリクエスト名称が正しく登録されている事。", "リクエスト名称３", housingRequestInfos.get(2).getRequestName());
		Assert.assertEquals("物件リクエスト情報のリクエスト名称が正しく登録されている事。", "リクエスト名称４", housingRequestInfos.get(3).getRequestName());
		Assert.assertEquals("物件リクエスト情報のリクエスト名称が正しく登録されている事。", "リクエスト名称５", housingRequestInfos.get(4).getRequestName());

	}

	/**
	 * 物件リクエストエリア情報の登録処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>都道府県CDが空となっていた場合、レコードが作られない事</li>
	 *     <li>都道府県CDのみの物件リクエスト情報が作られる事</li>
	 * </ul>
	 */
	@Test
	public void addRequestPrefTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingRequestArea> housingRequestAreas;

		// テストケース実施前確認
		criteria = new DAOCriteria();
		housingRequestAreas = this.housingRequestAreaDAO.selectByFilter(criteria); 
		Assert.assertEquals("【テストケース実施前確認】物件リクエストエリア情報が0件である事。", 0, housingRequestAreas.size());

		HousingRequestForm inputForm;
		String requestId;

		// 都道府県CDが空となっていた場合、レコードが作られない事
		inputForm = new HousingRequestForm();
		inputForm.setPrefCd("");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		housingRequestAreas = this.housingRequestAreaDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエストエリア情報が0件である事。", 0, housingRequestAreas.size());

		// 都道府県CDのレコードが作られる事
		inputForm = new HousingRequestForm();
		inputForm.setPrefCd("13");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		criteria.addOrderByClause("prefCd");
		housingRequestAreas = this.housingRequestAreaDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエストエリア情報の件数が正しい事。", 1, housingRequestAreas.size());
		Assert.assertEquals("物件リクエストエリア情報の都道府県CDが正しく登録されている事。", "13", housingRequestAreas.get(0).getPrefCd());

		// 都道府県CDのレコードが複数作られる事
		inputForm = new HousingRequestForm();
		inputForm.setPrefCd("13,14");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		criteria.addOrderByClause("prefCd");
		housingRequestAreas = this.housingRequestAreaDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエストエリア情報の件数が正しい。", 2, housingRequestAreas.size());
		Assert.assertEquals("物件リクエストエリア情報の都道府県CDが正しく登録されている事。", "13", housingRequestAreas.get(0).getPrefCd());
		Assert.assertEquals("物件リクエストエリア情報の都道府県CDが正しく登録されている事。", "14", housingRequestAreas.get(1).getPrefCd());

	}

	/**
	 * 物件リクエストエリア情報の登録処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>市区町村CDが空となっていた場合レコードが作られない事</li>
	 *     <li>都道府県CDが無く、市区町村CDだけでレコードが作られない事</li>
	 *     <li>都道府県CDと、市区町村CDでレコードが作られる事</li>
	 * </ul>
	 */
	@Test
	public void addRequestAddressTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingRequestArea> housingRequestAreas;

		// テストケース実施前確認
		criteria = new DAOCriteria();
		housingRequestAreas = this.housingRequestAreaDAO.selectByFilter(criteria); 
		Assert.assertEquals("【テストケース実施前確認】物件リクエストエリア情報が0件である事。", 0, housingRequestAreas.size());

		HousingRequestForm inputForm;
		String requestId;

		// 市区町村CDが空となっていた場合レコードが作られない事
		inputForm = new HousingRequestForm();
		inputForm.setAddressCd("");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		housingRequestAreas = this.housingRequestAreaDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエストエリア情報が0件である事。", 0, housingRequestAreas.size());

		// 都道府県CDが無く、市区町村CDだけでレコードが作られない事
		inputForm = new HousingRequestForm();
		inputForm.setAddressCd("13101");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		housingRequestAreas = this.housingRequestAreaDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエストエリア情報が0件である事。", 0, housingRequestAreas.size());

		// 都道府県CDと、市区町村CDでレコードが作られる事
		inputForm = new HousingRequestForm();
		inputForm.setPrefCd("13");
		inputForm.setAddressCd("13101");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		housingRequestAreas = this.housingRequestAreaDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエストエリア情報の件数が正しい事。", 1, housingRequestAreas.size());
		Assert.assertEquals("物件リクエストエリア情報の都道府県CDが正しく登録されている事。", "13", housingRequestAreas.get(0).getPrefCd());
		Assert.assertEquals("物件リクエストエリア情報の市区町村CDが正しく登録されている事。", "13101", housingRequestAreas.get(0).getAddressCd());

		// 都道府県CDと、市区町村CDで複数レコードが作られる事
		inputForm = new HousingRequestForm();
		inputForm.setPrefCd("13");
		inputForm.setAddressCd("13101,13102");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		criteria.addOrderByClause("prefCd");
		criteria.addOrderByClause("addressCd");
		housingRequestAreas = this.housingRequestAreaDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエストエリア情報の件数が正しい事。", 2, housingRequestAreas.size());
		Assert.assertEquals("物件リクエストエリア情報の都道府県CDが正しく登録されている事。", "13", housingRequestAreas.get(0).getPrefCd());
		Assert.assertEquals("物件リクエストエリア情報の市区町村CDが正しく登録されている事。", "13101", housingRequestAreas.get(0).getAddressCd());
		Assert.assertEquals("物件リクエストエリア情報の都道府県CDが正しく登録されている事。", "13", housingRequestAreas.get(1).getPrefCd());
		Assert.assertEquals("物件リクエストエリア情報の市区町村CDが正しく登録されている事。", "13102", housingRequestAreas.get(1).getAddressCd());

		// 都道府県CDと、市区町村CDで複数レコードが作られる事
		inputForm = new HousingRequestForm();
		inputForm.setPrefCd("13,14");
		inputForm.setAddressCd("13101,13102,14101");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		criteria.addOrderByClause("prefCd");
		criteria.addOrderByClause("addressCd");
		housingRequestAreas = this.housingRequestAreaDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエストエリア情報の件数が正しい事。", 3, housingRequestAreas.size());
		Assert.assertEquals("物件リクエストエリア情報の都道府県CDが正しく登録されている事。", "13", housingRequestAreas.get(0).getPrefCd());
		Assert.assertEquals("物件リクエストエリア情報の市区町村CDが正しく登録されている事。", "13101", housingRequestAreas.get(0).getAddressCd());
		Assert.assertEquals("物件リクエストエリア情報の都道府県CDが正しく登録されている事。", "13", housingRequestAreas.get(1).getPrefCd());
		Assert.assertEquals("物件リクエストエリア情報の市区町村CDが正しく登録されている事。", "13102", housingRequestAreas.get(1).getAddressCd());
		Assert.assertEquals("物件リクエストエリア情報の都道府県CDが正しく登録されている事。", "14", housingRequestAreas.get(2).getPrefCd());
		Assert.assertEquals("物件リクエストエリア情報の市区町村CDが正しく登録されている事。", "14101", housingRequestAreas.get(2).getAddressCd());

	}

	/**
	 * 物件リクエスト沿線情報の登録処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>路線CDがが空となっていた場合レコードが作られない事</li>
	 *     <li>物件リクエスト沿線情報が作られる事</li>
	 * </ul>
	 */
	@Test
	public void addRequestRouteTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingReqRoute> housingReqRoutes;

		// テストケース実施前確認
		criteria = new DAOCriteria();
		housingReqRoutes = this.housingReqRouteDAO.selectByFilter(criteria); 
		Assert.assertEquals("【テストケース実施前確認】物件リクエスト路線情報が0件である事。", 0, housingReqRoutes.size());

		HousingRequestForm inputForm;
		String requestId;

		// 路線CDが空でレコードが作られない事
		inputForm = new HousingRequestForm();
		inputForm.setRouteCd("");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		housingReqRoutes = this.housingReqRouteDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエスト路線情報が0件である事。", 0, housingReqRoutes.size());

		// 路線CDのレコードが作られる事
		inputForm = new HousingRequestForm();
		inputForm.setRouteCd("P116005");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		housingReqRoutes = this.housingReqRouteDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエスト路線情報の件数が正しい事。", 1, housingReqRoutes.size());
		Assert.assertEquals("物件リクエスト路線情報の路線CDが正しく登録されている事。", "P116005",  housingReqRoutes.get(0).getRouteCd());

		// 路線CDの複数レコードが作られる事
		inputForm = new HousingRequestForm();
		inputForm.setRouteCd("P116005,P116006");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		criteria.addOrderByClause("routeCd");
		housingReqRoutes = this.housingReqRouteDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエスト路線情報の件数が正しい事。", 2, housingReqRoutes.size());
		Assert.assertEquals("物件リクエスト路線情報の路線CDが正しく登録されている事。", "P116005",  housingReqRoutes.get(0).getRouteCd());
		Assert.assertEquals("物件リクエスト路線情報の路線CDが正しく登録されている事。", "P116006",  housingReqRoutes.get(1).getRouteCd());

	}

	/**
	 * 物件リクエスト最寄駅情報の登録処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>駅CDが空となっていた場合レコードが作られない事</li>
	 *     <li>路線CDが無く、駅CDだけでレコードが作られない事</li>
	 *     <li>物件リクエスト最寄駅情報が作られる事</li>
	 * </ul>
	 */
	@Test
	public void addRequestStationTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingReqStation> housingReqStations;

		// テストケース実施前確認
		criteria = new DAOCriteria();
		housingReqStations = this.housingReqStationDAO.selectByFilter(criteria); 
		Assert.assertEquals("【テストケース実施前確認】物件リクエスト最寄駅情報が0件である事。", 0, housingReqStations.size());

		HousingRequestForm inputForm;
		String requestId;

		// 駅CDが空でレコードが作られない事
		inputForm = new HousingRequestForm();
		inputForm.setStationCd("");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		housingReqStations = this.housingReqStationDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエスト最寄駅情報が0件である事。", 0, housingReqStations.size());

		// 路線CDがなく、駅CDだけでレコードが作られない事
		inputForm = new HousingRequestForm();
		inputForm.setStationCd("22849");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		housingReqStations = this.housingReqStationDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエスト最寄駅情報が0件である事。", 0, housingReqStations.size());

		// 路線CDと、駅CDでレコードが作られる事
		inputForm = new HousingRequestForm();
		inputForm.setRouteCd("P116005");
		inputForm.setStationCd("22849");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		housingReqStations = this.housingReqStationDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエスト最寄駅情報の件数が正しい事。", 1, housingReqStations.size());
		Assert.assertEquals("物件リクエスト最寄駅情報の駅CDが正しい事。", "22849", housingReqStations.get(0).getStationCd());

		// 路線CDと、駅CDで複数のレコードが作られる事
		inputForm = new HousingRequestForm();
		inputForm.setRouteCd("P116005");
		inputForm.setStationCd("22849,22583");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		criteria.addOrderByClause("stationCd");
		housingReqStations = this.housingReqStationDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエスト最寄駅情報の件数が正しい事。", 2, housingReqStations.size());
		Assert.assertEquals("物件リクエスト最寄駅情報の駅CDが正しい事。", "22583", housingReqStations.get(0).getStationCd());
		Assert.assertEquals("物件リクエスト最寄駅情報の駅CDが正しい事。", "22849", housingReqStations.get(1).getStationCd());

		// 路線CDと、駅CDで複数のレコードが作られる事
		inputForm = new HousingRequestForm();
		inputForm.setRouteCd("P116005,P116006");
		inputForm.setStationCd("22849,22583,22627");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		criteria.addOrderByClause("stationCd");
		housingReqStations = this.housingReqStationDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエスト最寄駅情報の件数が正しい事。", 3, housingReqStations.size());
		Assert.assertEquals("物件リクエスト最寄駅情報の駅CDが正しい事。", "22583", housingReqStations.get(0).getStationCd());
		Assert.assertEquals("物件リクエスト最寄駅情報の駅CDが正しい事。", "22627", housingReqStations.get(1).getStationCd());
		Assert.assertEquals("物件リクエスト最寄駅情報の駅CDが正しい事。", "22849", housingReqStations.get(2).getStationCd());

	}

	/**
	 * 物件リクエスト物件種別情報の登録処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>物件種別CDが空となっていた場合、レコードが作られない事</li>
	 *     <li>物件リクエスト物件種別情報が作られる事</li>
	 * </ul>
	 */
	@Test
	public void addRequestHousingKindTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingReqKind> housingReqKinds;

		// テストケース実施前確認
		criteria = new DAOCriteria();
		housingReqKinds = this.housingReqKindDAO.selectByFilter(criteria); 
		Assert.assertEquals("【テストケース実施前確認】物件リクエスト物件種別情報が0件である事。", 0, housingReqKinds.size());

		HousingRequestForm inputForm;
		String requestId;

		// 物件種類CDが空でレコードが作られない事
		inputForm = new HousingRequestForm();
		inputForm.setHousingKindCd("");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		housingReqKinds = this.housingReqKindDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエスト物件種別情報が0件である事。", 0, housingReqKinds.size());

		// 物件種類CDのレコードが作られる事
		inputForm.setHousingKindCd("001");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		housingReqKinds = this.housingReqKindDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエスト物件種別情報の件数が正しい事。", 1, housingReqKinds.size());
		Assert.assertEquals("物件リクエスト物件種別情報の物件種別CDが正しい事。", "001", housingReqKinds.get(0).getHousingKindCd());

		// 物件種類CDの複数レコードが作られる事
		inputForm.setHousingKindCd("001,002");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		criteria.addOrderByClause("housingKindCd");
		housingReqKinds = this.housingReqKindDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエスト物件種別情報の件数が正しい事。", 2, housingReqKinds.size());
		Assert.assertEquals("物件リクエスト物件種別情報の物件種別CDが正しい事。", "001", housingReqKinds.get(0).getHousingKindCd());
		Assert.assertEquals("物件リクエスト物件種別情報の物件種別CDが正しい事。", "002", housingReqKinds.get(1).getHousingKindCd());

	}

	/**
	 * 物件リクエスト間取情報の登録処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>間取CDが空となっていた場合、レコードが作られない事</li>
	 *     <li>物件リクエスト間取情報が作られる事</li>
	 * </ul>
	 */
	@Test
	public void addRequestHousingLayoutTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingReqLayout> housingReqLayouts;

		// テストケース実施前確認
		criteria = new DAOCriteria();
		housingReqLayouts = this.housingReqLayoutDAO.selectByFilter(criteria); 
		Assert.assertEquals("【テストケース実施前確認】物件リクエスト間取情報が0件である事。", 0, housingReqLayouts.size());

		HousingRequestForm inputForm;
		String requestId;

		// 間取りCDが空でレコードが作られない事
		inputForm = new HousingRequestForm();
		inputForm.setLayoutCd("");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		housingReqLayouts = this.housingReqLayoutDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエスト間取情報が0件である事。", 0, housingReqLayouts.size());

		// 間取りCDのレコードが作られる事
		inputForm.setLayoutCd("001");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		housingReqLayouts = this.housingReqLayoutDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエスト間取情報の件数が正しい事。", 1, housingReqLayouts.size());
		Assert.assertEquals("物件リクエスト間取情報の間取CDが正しい事。", "001", housingReqLayouts.get(0).getLayoutCd());

		// 間取りCDの複数レコードが作られる事
		inputForm.setLayoutCd("001,002");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		criteria.addOrderByClause("layoutCd");
		housingReqLayouts = this.housingReqLayoutDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエスト間取情報の件数が正しい事。", 2, housingReqLayouts.size());
		Assert.assertEquals("物件リクエスト間取情報の間取CDが正しい事。", "001", housingReqLayouts.get(0).getLayoutCd());
		Assert.assertEquals("物件リクエスト間取情報の間取CDが正しい事。", "002", housingReqLayouts.get(1).getLayoutCd());

	}

	/**
	 * 物件リクエストこだわり条件情報の登録処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>こだわり条件CDが空となっていた場合、レコードが作られない事</li>
	 *     <li>物件リクエストこだわり条件情報が作られる事</li>
	 * </ul>
	 */
	@Test
	public void addRequestHousingPartSrchTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingReqPart> housingReqParts;

		// テストケース実施前確認
		criteria = new DAOCriteria();
		housingReqParts = this.housingReqPartDAO.selectByFilter(criteria); 
		Assert.assertEquals("【テストケース実施前確認】物件リクエストこだわり条件情報が0件である事。", 0, housingReqParts.size());

		HousingRequestForm inputForm;
		String requestId;

		// こだわり条件CDが空でレコードが作られない事
		inputForm = new HousingRequestForm();
		inputForm.setPartSrchCd("");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		housingReqParts = this.housingReqPartDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエストこだわり条件情報が0件である事。", 0, housingReqParts.size());

		// こだわり条件CDのレコードが作られる事
		inputForm.setPartSrchCd("FRE");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		housingReqParts = this.housingReqPartDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエストこだわり条件情報が正しい件数である事。", 1, housingReqParts.size());
		Assert.assertEquals("物件リクエストこだわり条件情報のこだわりCDが正しい事。", "FRE", housingReqParts.get(0).getPartSrchCd());

		// こだわり条件CDの複数レコードが作られる事
		inputForm.setPartSrchCd("FRE,REI");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		criteria.addOrderByClause("partSrchCd");
		housingReqParts = this.housingReqPartDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエストこだわり条件情報が正しい件数である事。", 2, housingReqParts.size());
		Assert.assertEquals("物件リクエストこだわり条件情報のこだわりCDが正しい事。", "FRE", housingReqParts.get(0).getPartSrchCd());
		Assert.assertEquals("物件リクエストこだわり条件情報のこだわりCDが正しい事。", "REI", housingReqParts.get(1).getPartSrchCd());

	}

	/**
	 * 物件リクエスト情報の更新処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>パラメタが正しく設定されていない場合、物件リクエスト情報は更新せず、正常終了する事</li>
	 *     <li>物件リクエスト情報のレコードが作られる事</li>
	 * </ul>
	 */
	@Test
	public void updateRequestParamCheckTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingRequestInfo> housingRequestInfos;

		HousingRequestForm inputForm;

		Date beforeDate;
		Date afterDate;

		inputForm = new HousingRequestForm();
		inputForm.setRequestName("リクエスト名称");
		inputForm.setPriceLower(10000L);
		inputForm.setPriceUpper(20000L);
		inputForm.setPersonalAreaLower(new BigDecimal("20.00"));
		inputForm.setPersonalAreaUpper(new BigDecimal("50.00"));
		inputForm.setPrefCd("01");
		inputForm.setAddressCd("01101");
		inputForm.setRouteCd("P116001");
		inputForm.setStationCd("22495");
		inputForm.setHousingKindCd("001");
		inputForm.setLayoutCd("002");
		inputForm.setPartSrchCd("FRE");
		housingRequestManage.addRequest("UID00001", inputForm);

		// テストケース実施前確認
		criteria = new DAOCriteria();
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("【テストケース実施前確認】物件リクエスト情報が1件である事。", 1, housingRequestInfos.size());
		beforeDate = housingRequestInfos.get(0).getUpdDate();

		// ユーザIDが空の場合、レコードが更新されない事
		inputForm = new HousingRequestForm();
		housingRequestManage.updateRequest("", inputForm);
		criteria = new DAOCriteria();
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = housingRequestInfos.get(0).getUpdDate();
		Assert.assertTrue("パラメタが正しく設定されていない場合、物件リクエスト情報は更新せず、正常終了する事", beforeDate.compareTo(afterDate) == 0);

		// ユーザIDがnullの場合、レコードが更新されない事
		inputForm = new HousingRequestForm();
		housingRequestManage.updateRequest(null, inputForm);
		criteria = new DAOCriteria();
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = housingRequestInfos.get(0).getUpdDate();
		Assert.assertTrue("パラメタが正しく設定されていない場合、物件リクエスト情報は更新せず、正常終了する事", beforeDate.compareTo(afterDate) == 0);

		// ユーザIDが存在しない場合、レコードが更新されない事
		inputForm = new HousingRequestForm();
		housingRequestManage.updateRequest("UID00000", inputForm);
		criteria = new DAOCriteria();
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = housingRequestInfos.get(0).getUpdDate();
		Assert.assertTrue("パラメタが正しく設定されていない場合、物件リクエスト情報は更新せず、正常終了する事", beforeDate.compareTo(afterDate) == 0);

	}

	/**
	 * 物件リクエスト情報の更新処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>物件リクエスト情報のレコードが正しく更新されている事</li>
	 * </ul>
	 */
	@Test
	public void updateRequestTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingRequestInfo> requestInfos;

		HousingRequestForm inputForm;

		String housingRequestId;
		Date beforeDate;
		Date afterDate;

		inputForm = new HousingRequestForm();
		inputForm.setRequestName("リクエスト名称");
		inputForm.setPriceLower(10000L);
		inputForm.setPriceUpper(20000L);
		inputForm.setPersonalAreaLower(new BigDecimal("20.00"));
		inputForm.setPersonalAreaUpper(new BigDecimal("50.00"));
		inputForm.setPrefCd("01");
		inputForm.setAddressCd("01101");
		inputForm.setRouteCd("P116001");
		inputForm.setStationCd("22495");
		inputForm.setHousingKindCd("001");
		inputForm.setLayoutCd("002");
		inputForm.setPartSrchCd("FRE");
		housingRequestId =  housingRequestManage.addRequest("UID00001", inputForm);

		// テストケース実施前確認
		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("【テストケース実施前確認】物件リクエスト情報が1件である事。", 1, requestInfos.size());
		beforeDate = requestInfos.get(0).getUpdDate();

		// ※更新時刻が同じにならないように一時停止
		Thread.sleep(1000);

		// リクエスト名称
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setRequestName("リクエスト名称１");
		housingRequestManage.updateRequest("UID00001", inputForm);
		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("物件リクエスト情報が正しく更新されている事", beforeDate.compareTo(afterDate) < 0);
		Assert.assertEquals("物件リクエスト情報が正しく更新されている事", "リクエスト名称１", requestInfos.get(0).getRequestName());

		// ※更新時刻が同じにならないように一時停止
		Thread.sleep(1000);

		// 賃料/価格・下限
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setPriceLower(100001L);
		housingRequestManage.updateRequest("UID00001", inputForm);
		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("物件リクエスト情報が正しく更新されている事", beforeDate.compareTo(afterDate) < 0);
		Assert.assertEquals("物件リクエスト情報が正しく更新されている事", 100001L, requestInfos.get(0).getPriceLower().longValue());

		// ※更新時刻が同じにならないように一時停止
		Thread.sleep(1000);

		// 賃料/価格・上限
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setPriceUpper(200001L);
		housingRequestManage.updateRequest("UID00001", inputForm);
		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("物件リクエスト情報が正しく更新されている事", beforeDate.compareTo(afterDate) < 0);
		Assert.assertEquals("物件リクエスト情報が正しく更新されている事", 200001L, requestInfos.get(0).getPriceUpper().longValue());

		// ※更新時刻が同じにならないように一時停止
		Thread.sleep(1000);

		// 専有面積・下限
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setPersonalAreaLower(new BigDecimal("21"));
		housingRequestManage.updateRequest("UID00001", inputForm);
		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("物件リクエスト情報が正しく更新されている事", beforeDate.compareTo(afterDate) < 0);
		Assert.assertTrue("物件リクエスト情報が正しく更新されている事", new BigDecimal("21.00").compareTo(requestInfos.get(0).getPersonalAreaLower()) == 0);

		// ※更新時刻が同じにならないように一時停止
		Thread.sleep(1000);

		// 専有面積・上限
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setPersonalAreaUpper(new BigDecimal("51"));
		housingRequestManage.updateRequest("UID00001", inputForm);
		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("物件リクエスト情報が正しく更新されている事", beforeDate.compareTo(afterDate) < 0);
		Assert.assertTrue("物件リクエスト情報が正しく更新されている事", new BigDecimal("51.00").compareTo(requestInfos.get(0).getPersonalAreaUpper()) == 0);

	}

	/**
	 * 物件リクエスト情報の更新処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>物件リクエストエリア情報のレコードが正しく更新されている事</li>
	 * </ul>
	 */
	@Test
	public void updateRequestAreaTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingRequestInfo> requestInfos;
		List<HousingRequestArea> requestAreas;

		HousingRequestForm inputForm;

		String housingRequestId;
		Date beforeDate;
		Date afterDate;

		inputForm = new HousingRequestForm();
		inputForm.setRequestName("リクエスト名称");
		inputForm.setPriceLower(10000L);
		inputForm.setPriceUpper(20000L);
		inputForm.setPersonalAreaLower(new BigDecimal("20.00"));
		inputForm.setPersonalAreaUpper(new BigDecimal("50.00"));
		inputForm.setPrefCd("01");
		inputForm.setAddressCd("01101");
		inputForm.setRouteCd("P116001");
		inputForm.setStationCd("22495");
		inputForm.setHousingKindCd("001");
		inputForm.setLayoutCd("002");
		inputForm.setPartSrchCd("FRE");
		housingRequestId =  housingRequestManage.addRequest("UID00001", inputForm);

		// テストケース実施前確認
		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("【テストケース実施前確認】物件リクエスト情報が1件である事。", 1, requestInfos.size());
		beforeDate = requestInfos.get(0).getUpdDate();

		// ※更新時刻が同じにならないように一時停止
		Thread.sleep(1000);

		// 都道府県CD（追加）
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setPrefCd("01,02,03");
		housingRequestManage.updateRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("物件リクエスト情報が正しく更新されている事", beforeDate.compareTo(afterDate) < 0);
		criteria.addOrderByClause("prefCd");
		requestAreas = this.housingRequestAreaDAO.selectByFilter(criteria);
		Assert.assertEquals("物件リクエストエリア情報が正しく更新されている事", 3, requestAreas.size());
		Assert.assertEquals("物件リクエストエリア情報が正しく更新されている事", "01", requestAreas.get(0).getPrefCd());
		Assert.assertEquals("物件リクエストエリア情報が正しく更新されている事", "02", requestAreas.get(1).getPrefCd());
		Assert.assertEquals("物件リクエストエリア情報が正しく更新されている事", "03", requestAreas.get(2).getPrefCd());

		// ※更新時刻が同じにならないように一時停止
		Thread.sleep(1000);

		// 都道府県CD（削除）
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setPrefCd("02,03");
		housingRequestManage.updateRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("物件リクエスト情報が正しく更新されている事", beforeDate.compareTo(afterDate) < 0);
		criteria.addOrderByClause("prefCd");
		requestAreas = this.housingRequestAreaDAO.selectByFilter(criteria);
		Assert.assertEquals("物件リクエストエリア情報が正しく更新されている事", 2, requestAreas.size());
		Assert.assertEquals("物件リクエストエリア情報が正しく更新されている事", "02", requestAreas.get(0).getPrefCd());
		Assert.assertEquals("物件リクエストエリア情報が正しく更新されている事", "03", requestAreas.get(1).getPrefCd());

		// 市区町村CD（追加）
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setAddressCd("02201,03201,03202");
		housingRequestManage.updateRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("物件リクエスト情報が正しく更新されている事", beforeDate.compareTo(afterDate) < 0);
		criteria.addOrderByClause("addressCd");
		requestAreas = this.housingRequestAreaDAO.selectByFilter(criteria);
		Assert.assertEquals("物件リクエストエリア情報が正しく更新されている事", 3, requestAreas.size());
		Assert.assertEquals("物件リクエストエリア情報が正しく更新されている事", "02201", requestAreas.get(0).getAddressCd());
		Assert.assertEquals("物件リクエストエリア情報が正しく更新されている事", "03201", requestAreas.get(1).getAddressCd());
		Assert.assertEquals("物件リクエストエリア情報が正しく更新されている事", "03202", requestAreas.get(2).getAddressCd());

		// ※更新時刻が同じにならないように一時停止
		Thread.sleep(1000);

		// 都道府県CD（削除）
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setAddressCd("02201,03201");
		housingRequestManage.updateRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("物件リクエスト情報が正しく更新されている事", beforeDate.compareTo(afterDate) < 0);
		criteria.addOrderByClause("addressCd");
		requestAreas = this.housingRequestAreaDAO.selectByFilter(criteria);
		Assert.assertEquals("物件リクエストエリア情報が正しく更新されている事", 2, requestAreas.size());
		Assert.assertEquals("物件リクエストエリア情報が正しく更新されている事", "02201", requestAreas.get(0).getAddressCd());
		Assert.assertEquals("物件リクエストエリア情報が正しく更新されている事", "03201", requestAreas.get(1).getAddressCd());

	}

	/**
	 * 物件リクエスト情報の更新処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>物件リクエスト路線情報のレコードが正しく更新されている事</li>
	 * </ul>
	 */
	@Test
	public void updateRequesRouteTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingRequestInfo> requestInfos;
		List<HousingReqRoute> requestRoutes;

		HousingRequestForm inputForm;

		String housingRequestId;
		Date beforeDate;
		Date afterDate;

		inputForm = new HousingRequestForm();
		inputForm.setRequestName("リクエスト名称");
		inputForm.setPriceLower(10000L);
		inputForm.setPriceUpper(20000L);
		inputForm.setPersonalAreaLower(new BigDecimal("20.00"));
		inputForm.setPersonalAreaUpper(new BigDecimal("50.00"));
		inputForm.setPrefCd("01");
		inputForm.setAddressCd("01101");
		inputForm.setRouteCd("P116001");
		inputForm.setStationCd("");
		inputForm.setHousingKindCd("001");
		inputForm.setLayoutCd("002");
		inputForm.setPartSrchCd("FRE");
		housingRequestId =  housingRequestManage.addRequest("UID00001", inputForm);

		// テストケース実施前確認
		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("【テストケース実施前確認】物件リクエスト情報が1件である事。", 1, requestInfos.size());
		beforeDate = requestInfos.get(0).getUpdDate();

		// ※更新時刻が同じにならないように一時停止
		Thread.sleep(1000);

		// 路線CD（追加）
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setRouteCd("P116001,P116002,P116003");
		housingRequestManage.updateRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("物件リクエスト情報が正しく更新されている事", beforeDate.compareTo(afterDate) < 0);
		criteria.addOrderByClause("routeCd");
		requestRoutes = this.housingReqRouteDAO.selectByFilter(criteria);
		Assert.assertEquals("物件リクエスト路線情報が正しく更新されている事", 3, requestRoutes.size());
		Assert.assertEquals("物件リクエスト路線情報が正しく更新されている事", "P116001", requestRoutes.get(0).getRouteCd());
		Assert.assertEquals("物件リクエスト路線情報が正しく更新されている事", "P116002", requestRoutes.get(1).getRouteCd());
		Assert.assertEquals("物件リクエスト路線情報が正しく更新されている事", "P116003", requestRoutes.get(2).getRouteCd());

		// ※更新時刻が同じにならないように一時停止
		Thread.sleep(1000);

		// 路線CD（削除）
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setRouteCd("P116002,P116003");
		housingRequestManage.updateRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("物件リクエスト情報が正しく更新されている事", beforeDate.compareTo(afterDate) < 0);
		criteria.addOrderByClause("routeCd");
		requestRoutes = this.housingReqRouteDAO.selectByFilter(criteria);
		Assert.assertEquals("物件リクエスト路線情報が正しく更新されている事", 2, requestRoutes.size());
		Assert.assertEquals("物件リクエスト路線情報が正しく更新されている事", "P116002", requestRoutes.get(0).getRouteCd());
		Assert.assertEquals("物件リクエスト路線情報が正しく更新されている事", "P116003", requestRoutes.get(1).getRouteCd());

	}

	/**
	 * 物件リクエスト情報の更新処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>物件リクエスト最寄駅情報のレコードが正しく更新されている事</li>
	 * </ul>
	 */
	@Test
	public void updateRequesStationTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingRequestInfo> requestInfos;
		List<HousingReqStation> requestStations;

		HousingRequestForm inputForm;

		String housingRequestId;
		Date beforeDate;
		Date afterDate;

		inputForm = new HousingRequestForm();
		inputForm.setRequestName("リクエスト名称");
		inputForm.setPriceLower(10000L);
		inputForm.setPriceUpper(20000L);
		inputForm.setPersonalAreaLower(new BigDecimal("20.00"));
		inputForm.setPersonalAreaUpper(new BigDecimal("50.00"));
		inputForm.setPrefCd("01");
		inputForm.setAddressCd("01101");
		inputForm.setRouteCd("P116001");
		inputForm.setStationCd("22495");
		inputForm.setHousingKindCd("001");
		inputForm.setLayoutCd("002");
		inputForm.setPartSrchCd("FRE");
		housingRequestId =  housingRequestManage.addRequest("UID00001", inputForm);

		// テストケース実施前確認
		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("【テストケース実施前確認】物件リクエスト情報が1件である事。", 1, requestInfos.size());
		beforeDate = requestInfos.get(0).getUpdDate();

		// ※更新時刻が同じにならないように一時停止
		Thread.sleep(1000);

		// 路線CD（追加）
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setStationCd("22495,22811,22528");
		housingRequestManage.updateRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("物件リクエスト情報が正しく更新されている事", beforeDate.compareTo(afterDate) < 0);
		criteria.addOrderByClause("stationCd");
		requestStations = this.housingReqStationDAO.selectByFilter(criteria);
		Assert.assertEquals("物件リクエスト最寄駅情報が正しく更新されている事", 3, requestStations.size());
		Assert.assertEquals("物件リクエスト最寄駅情報が正しく更新されている事", "22495", requestStations.get(0).getStationCd());
		Assert.assertEquals("物件リクエスト最寄駅情報が正しく更新されている事", "22528", requestStations.get(1).getStationCd());
		Assert.assertEquals("物件リクエスト最寄駅情報が正しく更新されている事", "22811", requestStations.get(2).getStationCd());

		// ※更新時刻が同じにならないように一時停止
		Thread.sleep(1000);

		// 路線CD（削除）
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setStationCd("22811,22528");
		housingRequestManage.updateRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("物件リクエスト情報が正しく更新されている事", beforeDate.compareTo(afterDate) < 0);
		criteria.addOrderByClause("stationCd");
		requestStations = this.housingReqStationDAO.selectByFilter(criteria);
		Assert.assertEquals("物件リクエスト最寄駅情報が正しく更新されている事", 2, requestStations.size());
		Assert.assertEquals("物件リクエスト最寄駅情報が正しく更新されている事", "22528", requestStations.get(0).getStationCd());
		Assert.assertEquals("物件リクエスト最寄駅情報が正しく更新されている事", "22811", requestStations.get(1).getStationCd());

	}

	/**
	 * 物件リクエスト情報の更新処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>物件リクエスト物件種類情報のレコードが正しく更新されている事</li>
	 * </ul>
	 */
	@Test
	public void updateRequesHousingKindTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingRequestInfo> requestInfos;
		List<HousingReqKind> requestKinds;

		HousingRequestForm inputForm;

		String housingRequestId;
		Date beforeDate;
		Date afterDate;

		inputForm = new HousingRequestForm();
		inputForm.setRequestName("リクエスト名称");
		inputForm.setPriceLower(10000L);
		inputForm.setPriceUpper(20000L);
		inputForm.setPersonalAreaLower(new BigDecimal("20.00"));
		inputForm.setPersonalAreaUpper(new BigDecimal("50.00"));
		inputForm.setPrefCd("01");
		inputForm.setAddressCd("01101");
		inputForm.setRouteCd("P116001");
		inputForm.setStationCd("22495");
		inputForm.setHousingKindCd("001");
		inputForm.setLayoutCd("002");
		inputForm.setPartSrchCd("FRE");
		housingRequestId =  housingRequestManage.addRequest("UID00001", inputForm);

		// テストケース実施前確認
		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("【テストケース実施前確認】物件リクエスト情報が1件である事。", 1, requestInfos.size());
		beforeDate = requestInfos.get(0).getUpdDate();

		// ※更新時刻が同じにならないように一時停止
		Thread.sleep(1000);

		// 物件種類CD（追加）
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setHousingKindCd("001,002,003");
		housingRequestManage.updateRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("物件リクエスト情報が正しく更新されている事", beforeDate.compareTo(afterDate) < 0);
		criteria.addOrderByClause("housingKindCd");
		requestKinds = this.housingReqKindDAO.selectByFilter(criteria);
		Assert.assertEquals("物件リクエスト物件種類情報が正しく更新されている事", 3, requestKinds.size());
		Assert.assertEquals("物件リクエスト物件種類情報が正しく更新されている事", "001", requestKinds.get(0).getHousingKindCd());
		Assert.assertEquals("物件リクエスト物件種類情報が正しく更新されている事", "002", requestKinds.get(1).getHousingKindCd());
		Assert.assertEquals("物件リクエスト物件種類情報が正しく更新されている事", "003", requestKinds.get(2).getHousingKindCd());

		// ※更新時刻が同じにならないように一時停止
		Thread.sleep(1000);

		// 物件種類CD（削除）
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setHousingKindCd("002,003");
		housingRequestManage.updateRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("物件リクエスト情報が正しく更新されている事", beforeDate.compareTo(afterDate) < 0);
		criteria.addOrderByClause("housingKindCd");
		requestKinds = this.housingReqKindDAO.selectByFilter(criteria);
		Assert.assertEquals("物件リクエスト物件種類情報が正しく更新されている事", 2, requestKinds.size());
		Assert.assertEquals("物件リクエスト物件種類情報が正しく更新されている事", "002", requestKinds.get(0).getHousingKindCd());
		Assert.assertEquals("物件リクエスト物件種類情報が正しく更新されている事", "003", requestKinds.get(1).getHousingKindCd());

	}

	/**
	 * 物件リクエスト情報の更新処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>物件リクエスト間取情報のレコードが正しく更新されている事</li>
	 * </ul>
	 */
	@Test
	public void updateRequesLayoutTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingRequestInfo> requestInfos;
		List<HousingReqLayout> requestLayouts;

		HousingRequestForm inputForm;

		String housingRequestId;
		Date beforeDate;
		Date afterDate;

		inputForm = new HousingRequestForm();
		inputForm.setRequestName("リクエスト名称");
		inputForm.setPriceLower(10000L);
		inputForm.setPriceUpper(20000L);
		inputForm.setPersonalAreaLower(new BigDecimal("20.00"));
		inputForm.setPersonalAreaUpper(new BigDecimal("50.00"));
		inputForm.setPrefCd("01");
		inputForm.setAddressCd("01101");
		inputForm.setRouteCd("P116001");
		inputForm.setStationCd("22495");
		inputForm.setHousingKindCd("001");
		inputForm.setLayoutCd("002");
		inputForm.setPartSrchCd("FRE");
		housingRequestId =  housingRequestManage.addRequest("UID00001", inputForm);

		// テストケース実施前確認
		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("【テストケース実施前確認】物件リクエスト情報が1件である事。", 1, requestInfos.size());
		beforeDate = requestInfos.get(0).getUpdDate();

		// ※更新時刻が同じにならないように一時停止
		Thread.sleep(1000);

		// 間取CD（追加）
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setLayoutCd("002,003,004");
		housingRequestManage.updateRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("物件リクエスト情報が正しく更新されている事", beforeDate.compareTo(afterDate) < 0);
		criteria.addOrderByClause("layoutCd");
		requestLayouts = this.housingReqLayoutDAO.selectByFilter(criteria);
		Assert.assertEquals("物件リクエスト間取情報が正しく更新されている事", 3, requestLayouts.size());
		Assert.assertEquals("物件リクエスト間取情報が正しく更新されている事", "002", requestLayouts.get(0).getLayoutCd());
		Assert.assertEquals("物件リクエスト間取情報が正しく更新されている事", "003", requestLayouts.get(1).getLayoutCd());
		Assert.assertEquals("物件リクエスト間取情報が正しく更新されている事", "004", requestLayouts.get(2).getLayoutCd());

		// ※更新時刻が同じにならないように一時停止
		Thread.sleep(1000);

		// 間取CD（削除）
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setLayoutCd("003,004");
		housingRequestManage.updateRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("物件リクエスト情報が正しく更新されている事", beforeDate.compareTo(afterDate) < 0);
		criteria.addOrderByClause("layoutCd");
		requestLayouts = this.housingReqLayoutDAO.selectByFilter(criteria);
		Assert.assertEquals("物件リクエスト間取情報が正しく更新されている事", 2, requestLayouts.size());
		Assert.assertEquals("物件リクエスト間取情報が正しく更新されている事", "003", requestLayouts.get(0).getLayoutCd());
		Assert.assertEquals("物件リクエスト間取情報が正しく更新されている事", "004", requestLayouts.get(1).getLayoutCd());

	}

	/**
	 * 物件リクエスト情報の更新処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>物件リクエストこだわり条件情報のレコードが正しく更新されている事</li>
	 * </ul>
	 */
	@Test
	public void updateRequesPartSrchTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingRequestInfo> requestInfos;
		List<HousingReqPart> requestPartSrchs;

		HousingRequestForm inputForm;

		String housingRequestId;
		Date beforeDate;
		Date afterDate;

		inputForm = new HousingRequestForm();
		inputForm.setRequestName("リクエスト名称");
		inputForm.setPriceLower(10000L);
		inputForm.setPriceUpper(20000L);
		inputForm.setPersonalAreaLower(new BigDecimal("20.00"));
		inputForm.setPersonalAreaUpper(new BigDecimal("50.00"));
		inputForm.setPrefCd("01");
		inputForm.setAddressCd("01101");
		inputForm.setRouteCd("P116001");
		inputForm.setStationCd("22495");
		inputForm.setHousingKindCd("001");
		inputForm.setLayoutCd("002");
		inputForm.setPartSrchCd("FRE");
		housingRequestId =  housingRequestManage.addRequest("UID00001", inputForm);

		// テストケース実施前確認
		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("【テストケース実施前確認】物件リクエスト情報が1件である事。", 1, requestInfos.size());
		beforeDate = requestInfos.get(0).getUpdDate();

		// ※更新時刻が同じにならないように一時停止
		Thread.sleep(1000);

		// こだわり条件CD（追加）
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setPartSrchCd("FRE,REI,S10");
		housingRequestManage.updateRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("物件リクエスト情報が正しく更新されている事", beforeDate.compareTo(afterDate) < 0);
		criteria.addOrderByClause("partSrchCd");
		requestPartSrchs = this.housingReqPartDAO.selectByFilter(criteria);
		Assert.assertEquals("物件リクエストこだわり条件情報が正しく更新されている事", 3, requestPartSrchs.size());
		Assert.assertEquals("物件リクエストこだわり条件情報が正しく更新されている事", "FRE", requestPartSrchs.get(0).getPartSrchCd());
		Assert.assertEquals("物件リクエストこだわり条件情報が正しく更新されている事", "REI", requestPartSrchs.get(1).getPartSrchCd());
		Assert.assertEquals("物件リクエストこだわり条件情報が正しく更新されている事", "S10", requestPartSrchs.get(2).getPartSrchCd());

		// ※更新時刻が同じにならないように一時停止
		Thread.sleep(1000);

		// こだわり条件CD（削除）
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setPartSrchCd("REI,S10");
		housingRequestManage.updateRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("物件リクエスト情報が正しく更新されている事", beforeDate.compareTo(afterDate) < 0);
		criteria.addOrderByClause("partSrchCd");
		requestPartSrchs = this.housingReqPartDAO.selectByFilter(criteria);
		Assert.assertEquals("物件リクエストこだわり条件情報が正しく更新されている事", 2, requestPartSrchs.size());
		Assert.assertEquals("物件リクエストこだわり条件情報が正しく更新されている事", "REI", requestPartSrchs.get(0).getPartSrchCd());
		Assert.assertEquals("物件リクエストこだわり条件情報が正しく更新されている事", "S10", requestPartSrchs.get(1).getPartSrchCd());

	}

	/**
	 * 物件リクエストの削除処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>パラメタが正しく設定されていない場合、物件リクエストが削除されず、正常終了する事</li>
	 * </ul>
	 */
	@Test
	public void delRequestParamCheckTest() throws Exception {

		DAOCriteria criteria;
		List<HousingRequestInfo> housingRequestInfos;

		// 削除対象データの登録
		HousingRequestInfo housingRequestInfo = new HousingRequestInfo();
		housingRequestInfo.setUserId("UID00001");
		housingRequestInfo.setRequestName("リクエスト１");
		housingRequestInfoDAO.insert(new HousingRequestInfo[] { housingRequestInfo });

		// テストケース実施前確認
		criteria = new DAOCriteria();
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("【テストケース実施前確認】物件リクエスト情報が1件である事。", 1, housingRequestInfos.size());

		HousingRequestForm inputForm;

		// 物件リクエストIDがnull
		inputForm = new HousingRequestForm();
		inputForm.setHousingRequestId(null);
		housingRequestManage.delRequest("UDI00001", inputForm);
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエスト情報が1件である事。", 1, housingRequestInfos.size());

		// 物件リクエストIDが空
		inputForm = new HousingRequestForm();
		inputForm.setHousingRequestId("");
		housingRequestManage.delRequest("UDI00001", inputForm);
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエスト情報が1件である事。", 1, housingRequestInfos.size());

		// ユーザIDがnull
		inputForm = new HousingRequestForm();
		inputForm.setHousingRequestId(housingRequestInfo.getHousingRequestId());
		housingRequestManage.delRequest(null, inputForm);
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエスト情報が1件である事。", 1, housingRequestInfos.size());

		// ユーザIDが空
		inputForm = new HousingRequestForm();
		inputForm.setHousingRequestId(housingRequestInfo.getHousingRequestId());
		housingRequestManage.delRequest("", inputForm);
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエスト情報が1件である事。", 1, housingRequestInfos.size());

	}

	/**
	 * 物件リクエストの削除処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>こだわり条件CDが空となっていた場合、レコードが作られない事</li>
	 *     <li>物件リクエストこだわり条件情報が作られる事</li>
	 * </ul>
	 */
	@Test
	public void delRequestTest() throws Exception {

		DAOCriteria criteria;
		List<HousingRequestInfo> housingRequestInfos;
		List<HousingRequestArea> housingRequestAreas;
		List<HousingReqRoute> housingReqRoutes;
		List<HousingReqStation> housingReqStations;
		List<HousingReqKind> housingReqKinds;
		List<HousingReqLayout> housingReqLayouts;
		List<HousingReqPart> housingReqParts;

		// 削除対象データの登録
		HousingRequestInfo housingRequestInfo = new HousingRequestInfo();
		housingRequestInfo.setUserId("UID00001");
		housingRequestInfo.setRequestName("リクエスト１");
		housingRequestInfoDAO.insert(new HousingRequestInfo[] { housingRequestInfo });

		HousingRequestArea housingRequestArea = new HousingRequestArea();
		housingRequestArea.setHousingRequestId(housingRequestInfo.getHousingRequestId());
		housingRequestArea.setPrefCd("13");
		housingRequestAreaDAO.insert(new HousingRequestArea[] { housingRequestArea });

		housingRequestArea = new HousingRequestArea();
		housingRequestArea.setHousingRequestId(housingRequestInfo.getHousingRequestId());
		housingRequestArea.setPrefCd("14");
		housingRequestArea.setAddressCd("15");
		housingRequestAreaDAO.insert(new HousingRequestArea[] { housingRequestArea });

		HousingReqRoute housingReqRoute = new HousingReqRoute();
		housingReqRoute.setHousingRequestId(housingRequestInfo.getHousingRequestId());
		housingReqRoute.setRouteCd("P116005");
		housingReqRouteDAO.insert(new HousingReqRoute[] { housingReqRoute });

		housingReqRoute = new HousingReqRoute();
		housingReqRoute.setHousingRequestId(housingRequestInfo.getHousingRequestId());
		housingReqRoute.setRouteCd("P116006");
		housingReqRouteDAO.insert(new HousingReqRoute[] { housingReqRoute });

		HousingReqStation housingReqStation = new HousingReqStation();
		housingReqStation.setHousingRequestId(housingRequestInfo.getHousingRequestId());
		housingReqStation.setStationCd("22849");
		housingReqStationDAO.insert(new HousingReqStation[] { housingReqStation });

		housingReqStation = new HousingReqStation();
		housingReqStation.setHousingRequestId(housingRequestInfo.getHousingRequestId());
		housingReqStation.setStationCd("22583");
		housingReqStationDAO.insert(new HousingReqStation[] { housingReqStation });

		HousingReqKind housingReqKind = new HousingReqKind();
		housingReqKind.setHousingRequestId(housingRequestInfo.getHousingRequestId());
		housingReqKind.setHousingKindCd("001");
		housingReqKindDAO.insert(new HousingReqKind[] { housingReqKind });

		housingReqKind = new HousingReqKind();
		housingReqKind.setHousingRequestId(housingRequestInfo.getHousingRequestId());
		housingReqKind.setHousingKindCd("002");
		housingReqKindDAO.insert(new HousingReqKind[] { housingReqKind });

		HousingReqLayout housingReqLayout = new HousingReqLayout();
		housingReqLayout.setHousingRequestId(housingRequestInfo.getHousingRequestId());
		housingReqLayout.setLayoutCd("001");
		housingReqLayoutDAO.insert(new HousingReqLayout[] { housingReqLayout });

		housingReqLayout = new HousingReqLayout();
		housingReqLayout.setHousingRequestId(housingRequestInfo.getHousingRequestId());
		housingReqLayout.setLayoutCd("002");
		housingReqLayoutDAO.insert(new HousingReqLayout[] { housingReqLayout });

		HousingReqPart housingReqPart = new HousingReqPart();
		housingReqPart.setHousingRequestId(housingRequestInfo.getHousingRequestId());
		housingReqPart.setPartSrchCd("FRE");
		housingReqPartDAO.insert(new HousingReqPart[] { housingReqPart });

		housingReqPart = new HousingReqPart();
		housingReqPart.setHousingRequestId(housingRequestInfo.getHousingRequestId());
		housingReqPart.setPartSrchCd("REI");
		housingReqPartDAO.insert(new HousingReqPart[] { housingReqPart });

		criteria = new DAOCriteria();
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("【テストケース実施前確認】物件リクエスト情報が1件である事。", 1, housingRequestInfos.size());
		housingRequestAreas = this.housingRequestAreaDAO.selectByFilter(criteria); 
		Assert.assertEquals("【テストケース実施前確認】物件リクエストエリア情報が2件である事。", 2, housingRequestAreas.size());
		housingReqRoutes = this.housingReqRouteDAO.selectByFilter(criteria); 
		Assert.assertEquals("【テストケース実施前確認】物件リクエスト路線情報が2件である事。", 2, housingReqRoutes.size());
		housingReqStations = this.housingReqStationDAO.selectByFilter(criteria); 
		Assert.assertEquals("【テストケース実施前確認】物件リクエスト最寄駅情報が2件である事。", 2, housingReqStations.size());
		housingReqKinds = this.housingReqKindDAO.selectByFilter(criteria); 
		Assert.assertEquals("【テストケース実施前確認】物件リクエスト物件種別情報が2件である事。", 2, housingReqKinds.size());
		housingReqLayouts = this.housingReqLayoutDAO.selectByFilter(criteria); 
		Assert.assertEquals("【テストケース実施前確認】物件リクエスト間取情報が2件である事。", 2, housingReqLayouts.size());
		housingReqParts = this.housingReqPartDAO.selectByFilter(criteria); 
		Assert.assertEquals("【テストケース実施前確認】物件リクエストこだわり条件情報が2件である事。", 2, housingReqParts.size());

		HousingRequestForm inputForm;

		inputForm = new HousingRequestForm();
		inputForm.setHousingRequestId(housingRequestInfo.getHousingRequestId());
		housingRequestManage.delRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", housingRequestInfo.getHousingRequestId());
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエスト情報が0件である事。", 0, housingRequestInfos.size());
		housingRequestAreas = this.housingRequestAreaDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエストエリア情報が0件である事。", 0, housingRequestAreas.size());
		housingReqRoutes = this.housingReqRouteDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエスト路線情報が0件である事。", 0, housingReqRoutes.size());
		housingReqStations = this.housingReqStationDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエスト最寄駅情報が0件である事。", 0, housingReqStations.size());
		housingReqKinds = this.housingReqKindDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエスト物件種別情報が0件である事。", 0, housingReqKinds.size());
		housingReqLayouts = this.housingReqLayoutDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエスト間取情報が0件である事。", 0, housingReqLayouts.size());
		housingReqParts = this.housingReqPartDAO.selectByFilter(criteria); 
		Assert.assertEquals("物件リクエストこだわり条件情報が0件である事。", 0, housingReqParts.size());

	}

	/**
	 * 物件リクエストの検索処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>パラメタが正しく設定されていない場合、検索結果は0件で正常終了する事</li>
	 * </ul>
	 */
	@Test
	public void searchRequestParamCheckTest() throws Exception {

		DAOCriteria criteria;
		List<HousingRequestInfo> housingRequestInfos;

		// 検索対象データの登録
		HousingRequestInfo housingRequestInfo = new HousingRequestInfo();
		housingRequestInfo.setUserId("UID00001");
		housingRequestInfo.setRequestName("リクエスト１");
		housingRequestInfoDAO.insert(new HousingRequestInfo[] { housingRequestInfo });

		// テストケース実施前確認
		criteria = new DAOCriteria();
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("【テストケース実施前確認】物件リクエスト情報が1件である事。", 1, housingRequestInfos.size());

		List<HousingRequest> requests;

		// ユーザIDがnull
		requests = housingRequestManage.searchRequest(null);
		Assert.assertEquals("検索結果が0件である事。", 0, requests.size());

		// ユーザIDが空
		requests = housingRequestManage.searchRequest("");
		Assert.assertEquals("検索結果が0件である事。", 0, requests.size());

	}

	/**
	 * 物件リクエストの検索処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>物件リクエストが正しく検索される事</li>
	 * </ul>
	 */
	@Test
	public void searchRequestTest() throws Exception {

		DAOCriteria criteria;
		List<HousingRequestInfo> housingRequestInfos;

		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, -10);
		Date insDate = calendar.getTime();

		// 検索対象データの登録
		HousingRequestInfo housingRequestInfo = new HousingRequestInfo();
		housingRequestInfo.setUserId("UID00001");
		housingRequestInfo.setRequestName("リクエスト１１");
		housingRequestInfo.setInsDate(insDate);
		calendar.setTime(now);
		calendar.add(Calendar.DAY_OF_MONTH, -3);
		housingRequestInfo.setUpdDate(calendar.getTime());
		housingRequestInfoDAO.insert(new HousingRequestInfo[] { housingRequestInfo });

		housingRequestInfo = new HousingRequestInfo();
		housingRequestInfo.setUserId("UID00001");
		housingRequestInfo.setRequestName("リクエスト１２");
		housingRequestInfo.setInsDate(insDate);
		calendar.setTime(now);
		calendar.add(Calendar.DAY_OF_MONTH, -2);
		housingRequestInfo.setUpdDate(calendar.getTime());
		housingRequestInfoDAO.insert(new HousingRequestInfo[] { housingRequestInfo });

		housingRequestInfo = new HousingRequestInfo();
		housingRequestInfo.setUserId("UID00002");
		housingRequestInfo.setRequestName("リクエスト２１");
		housingRequestInfo.setInsDate(insDate);
		calendar.setTime(now);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		housingRequestInfo.setUpdDate(calendar.getTime());
		housingRequestInfoDAO.insert(new HousingRequestInfo[] { housingRequestInfo });

		// テストケース実施前確認
		criteria = new DAOCriteria();
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("【テストケース実施前確認】物件リクエスト情報が3件である事。", 3, housingRequestInfos.size());

		List<HousingRequest> requests;

		// 正しく検索結果が取得されること
		requests = housingRequestManage.searchRequest("UID00001");
		Assert.assertEquals("検索結果件数が正しい事。", 2, requests.size());
		Assert.assertTrue("検索結果の並び順が正しい事。", requests.get(0).getHousingRequestInfo().getUpdDate().compareTo(requests.get(1).getHousingRequestInfo().getUpdDate()) > 0);
		Assert.assertEquals("検索結果の物件リクエスト情報が正しい事。", "リクエスト１２", requests.get(0).getHousingRequestInfo().getRequestName());
		Assert.assertEquals("検索結果の物件リクエスト情報が正しい事。", "リクエスト１１", requests.get(1).getHousingRequestInfo().getRequestName());

	}

	/**
	 * 物件リクエストの件数取得処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>パラメタが正しく設定されていない場合、検索結果は0件で正常終了する事</li>
	 * </ul>
	 */
	@Test
	public void searchRequestCntParamCheckTest() throws Exception {

		DAOCriteria criteria;
		List<HousingRequestInfo> housingRequestInfos;
		int cnt = 1;

		// 検索対象データの登録
		HousingRequestInfo housingRequestInfo = new HousingRequestInfo();
		housingRequestInfo.setUserId("UID00001");
		housingRequestInfo.setRequestName("リクエスト１");
		housingRequestInfoDAO.insert(new HousingRequestInfo[] { housingRequestInfo });

		// テストケース実施前確認
		criteria = new DAOCriteria();
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("【テストケース実施前確認】物件リクエスト情報が1件である事。", 1, housingRequestInfos.size());

		// ユーザIDがnull
		cnt = housingRequestManage.searchRequestCnt(null);
		Assert.assertEquals("検索結果が0件である事。", 0, cnt);

		// ユーザIDが空
		cnt = housingRequestManage.searchRequestCnt("");
		Assert.assertEquals("検索結果が0件である事。", 0, cnt);

	}

	/**
	 * 物件リクエストの件数取得処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>物件リクエストの件数が正しく取得される事</li>
	 * </ul>
	 */
	@Test
	public void searchRequestCntTest() throws Exception {

		DAOCriteria criteria;
		List<HousingRequestInfo> housingRequestInfos;

		// 検索対象データの登録
		HousingRequestInfo housingRequestInfo = new HousingRequestInfo();
		housingRequestInfo.setUserId("UID00001");
		housingRequestInfo.setRequestName("リクエスト１１");
		housingRequestInfoDAO.insert(new HousingRequestInfo[] { housingRequestInfo });

		housingRequestInfo = new HousingRequestInfo();
		housingRequestInfo.setUserId("UID00001");
		housingRequestInfo.setRequestName("リクエスト１２");
		housingRequestInfoDAO.insert(new HousingRequestInfo[] { housingRequestInfo });

		housingRequestInfo = new HousingRequestInfo();
		housingRequestInfo.setUserId("UID00002");
		housingRequestInfo.setRequestName("リクエスト２１");
		housingRequestInfoDAO.insert(new HousingRequestInfo[] { housingRequestInfo });

		// テストケース実施前確認
		criteria = new DAOCriteria();
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("【テストケース実施前確認】物件リクエスト情報が3件である事。", 3, housingRequestInfos.size());

		int cnt = 0;

		// 正しく検索結果が取得されること
		cnt = housingRequestManage.searchRequestCnt("UID00001");
		Assert.assertEquals("検索結果件数が正しい事。", 2, cnt);

	}

	/**
	 * 物件リクエストからの物件検索処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>物件リクエストの条件に合致した物件情報が検索される事</li>
	 * </ul>
	 */
	@Test
	public void searchHousingTest() throws Exception {

		// 検索対象とする物件情報を生成
		makeTestHousingData();

		HousingRequestForm inputForm;
		String housingRequestId;

		RequestSearchForm searchForm;
		int cnt = 0;
		HousingInfo housingInfo;

		// 賃料/価格・下限
		inputForm = new HousingRequestForm();
		inputForm.setRequestName("リクエスト名称");
		inputForm.setPriceLower(500000L);
		housingRequestId = housingRequestManage.addRequest("UID00001", inputForm);

		searchForm = requestSearchFormFactory.createRequestSearchForm();
		searchForm.setHousingRequestId(housingRequestId);
		cnt = housingRequestManage.searchHousing("UID00001", searchForm);
		Assert.assertEquals("物件リクエスト検索結果となる物件件数が正しいこと。", 1, cnt);
		housingInfo = (HousingInfo) searchForm.getRows().get(0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件リクエスト検索結果となる物件が正しいこと。", "SHOU00005", housingInfo.getSysHousingCd() );

		// 賃料/価格・上限
		inputForm = new HousingRequestForm();
		inputForm.setRequestName("リクエスト名称");
		inputForm.setPriceUpper(100000L);
		housingRequestId = housingRequestManage.addRequest("UID00002", inputForm);

		searchForm = requestSearchFormFactory.createRequestSearchForm();
		searchForm.setHousingRequestId(housingRequestId);
		cnt = housingRequestManage.searchHousing("UID00002", searchForm);
		Assert.assertEquals("物件リクエスト検索結果となる物件件数が正しいこと。", 1, cnt);
		housingInfo = (HousingInfo) searchForm.getRows().get(0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件リクエスト検索結果となる物件が正しいこと。", "SHOU00001", housingInfo.getSysHousingCd() );

		// 専有面積・下限
		inputForm = new HousingRequestForm();
		inputForm.setRequestName("リクエスト名称");
		inputForm.setPersonalAreaLower(new BigDecimal("100"));
		housingRequestId = housingRequestManage.addRequest("UID00003", inputForm);

		searchForm = requestSearchFormFactory.createRequestSearchForm();
		searchForm.setHousingRequestId(housingRequestId);
		cnt = housingRequestManage.searchHousing("UID00003", searchForm);
		Assert.assertEquals("物件リクエスト検索結果となる物件件数が正しいこと。", 1, cnt);
		housingInfo = (HousingInfo) searchForm.getRows().get(0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件リクエスト検索結果となる物件が正しいこと。", "SHOU00005", housingInfo.getSysHousingCd() );

		// 専有面積・上限
		inputForm = new HousingRequestForm();
		inputForm.setRequestName("リクエスト名称");
		inputForm.setPersonalAreaUpper(new BigDecimal("60"));
		housingRequestId = housingRequestManage.addRequest("UID00004", inputForm);

		searchForm = requestSearchFormFactory.createRequestSearchForm();
		searchForm.setHousingRequestId(housingRequestId);
		cnt = housingRequestManage.searchHousing("UID00004", searchForm);
		Assert.assertEquals("物件リクエスト検索結果となる物件件数が正しいこと。", 1, cnt);
		housingInfo = (HousingInfo) searchForm.getRows().get(0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件リクエスト検索結果となる物件が正しいこと。", "SHOU00001", housingInfo.getSysHousingCd() );

		// 都道府県CD
		inputForm = new HousingRequestForm();
		inputForm.setRequestName("リクエスト名称");
		inputForm.setPrefCd("01");
		housingRequestId = housingRequestManage.addRequest("UID00005", inputForm);

		searchForm = requestSearchFormFactory.createRequestSearchForm();
		searchForm.setHousingRequestId(housingRequestId);
		cnt = housingRequestManage.searchHousing("UID00005", searchForm);
		Assert.assertEquals("物件リクエスト検索結果となる物件件数が正しいこと。", 1, cnt);
		housingInfo = (HousingInfo) searchForm.getRows().get(0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件リクエスト検索結果となる物件が正しいこと。", "SHOU00001", housingInfo.getSysHousingCd() );

		// 市区町村CD
		inputForm = new HousingRequestForm();
		inputForm.setRequestName("リクエスト名称");
		inputForm.setPrefCd("02");
		inputForm.setAddressCd("02101");
		housingRequestId = housingRequestManage.addRequest("UID00006", inputForm);

		searchForm = requestSearchFormFactory.createRequestSearchForm();
		searchForm.setHousingRequestId(housingRequestId);
		cnt = housingRequestManage.searchHousing("UID00006", searchForm);
		Assert.assertEquals("物件リクエスト検索結果となる物件件数が正しいこと。", 1, cnt);
		housingInfo = (HousingInfo) searchForm.getRows().get(0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件リクエスト検索結果となる物件が正しいこと。", "SHOU00002", housingInfo.getSysHousingCd() );

		// 路線CD
		inputForm = new HousingRequestForm();
		inputForm.setRequestName("リクエスト名称");
		inputForm.setRouteCd("P116003");
		housingRequestId = housingRequestManage.addRequest("UID00007", inputForm);

		searchForm = requestSearchFormFactory.createRequestSearchForm();
		searchForm.setHousingRequestId(housingRequestId);
		cnt = housingRequestManage.searchHousing("UID00007", searchForm);
		Assert.assertEquals("物件リクエスト検索結果となる物件件数が正しいこと。", 1, cnt);
		housingInfo = (HousingInfo) searchForm.getRows().get(0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件リクエスト検索結果となる物件が正しいこと。", "SHOU00003", housingInfo.getSysHousingCd() );

		// 駅CD
		inputForm = new HousingRequestForm();
		inputForm.setRequestName("リクエスト名称");
		inputForm.setRouteCd("P116004");
		inputForm.setStationCd("22630");
		housingRequestId = housingRequestManage.addRequest("UID00008", inputForm);

		searchForm = requestSearchFormFactory.createRequestSearchForm();
		searchForm.setHousingRequestId(housingRequestId);
		cnt = housingRequestManage.searchHousing("UID00008", searchForm);
		Assert.assertEquals("物件リクエスト検索結果となる物件件数が正しいこと。", 1, cnt);
		housingInfo = (HousingInfo) searchForm.getRows().get(0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件リクエスト検索結果となる物件が正しいこと。", "SHOU00004", housingInfo.getSysHousingCd() );

		// 物件種類CD
		inputForm = new HousingRequestForm();
		inputForm.setRequestName("リクエスト名称");
		inputForm.setHousingKindCd("001");
		housingRequestId = housingRequestManage.addRequest("UID00009", inputForm);

		searchForm = requestSearchFormFactory.createRequestSearchForm();
		searchForm.setHousingRequestId(housingRequestId);
		cnt = housingRequestManage.searchHousing("UID00009", searchForm);
		Assert.assertEquals("物件リクエスト検索結果となる物件件数が正しいこと。", 1, cnt);
		housingInfo = (HousingInfo) searchForm.getRows().get(0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件リクエスト検索結果となる物件が正しいこと。", "SHOU00001", housingInfo.getSysHousingCd() );

		// 間取りCD
		inputForm = new HousingRequestForm();
		inputForm.setRequestName("リクエスト名称");
		inputForm.setLayoutCd("001");
		housingRequestId = housingRequestManage.addRequest("UID00010", inputForm);

		searchForm = requestSearchFormFactory.createRequestSearchForm();
		searchForm.setHousingRequestId(housingRequestId);
		cnt = housingRequestManage.searchHousing("UID00010", searchForm);
		Assert.assertEquals("物件リクエスト検索結果となる物件件数が正しいこと。", 1, cnt);
		housingInfo = (HousingInfo) searchForm.getRows().get(0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件リクエスト検索結果となる物件が正しいこと。", "SHOU00001", housingInfo.getSysHousingCd() );

		// こだわり条件CD
		inputForm = new HousingRequestForm();
		inputForm.setRequestName("リクエスト名称");
		inputForm.setPartSrchCd("FRE");
		housingRequestId = housingRequestManage.addRequest("UID00011", inputForm);

		searchForm = requestSearchFormFactory.createRequestSearchForm();
		searchForm.setHousingRequestId(housingRequestId);
		cnt = housingRequestManage.searchHousing("UID00011", searchForm);
		Assert.assertEquals("物件リクエスト検索結果となる物件件数が正しいこと。", 1, cnt);
		housingInfo = (HousingInfo) searchForm.getRows().get(0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件リクエスト検索結果となる物件が正しいこと。", "SHOU00002", housingInfo.getSysHousingCd() );

	}

	/**
	 * 物件検索用のデータを作成
	 */
	private void makeTestHousingData() {

		BuildingInfo buildingInfo;
		HousingInfo housingInfo;
		HousingPartInfo housingPartInfo;
		BuildingStationInfo buildingStationInfo;

		Calendar calendar = Calendar.getInstance();
		calendar.clear();

		// ------ 取得対象データ ------

		// ------ 【001】
		// 建物基本情報
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00001");
		buildingInfo.setBuildingCd("BLD00001");
		buildingInfo.setDisplayBuildingName("建物名０１");
		buildingInfo.setHousingKindCd("001");
		buildingInfo.setPrefCd("01");
		buildingInfo.setAddressCd("01101");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// 物件基本情報
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00001");
		housingInfo.setHousingCd("HOU00001");
		housingInfo.setDisplayHousingName("物件名０１");
		housingInfo.setSysBuildingCd("SBLD00001");
		housingInfo.setPrice(100000L);
		housingInfo.setLayoutCd("001");
		housingInfo.setPersonalArea(new BigDecimal("60"));
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// 建物最寄駅情報
		buildingStationInfo = new BuildingStationInfo();
		buildingStationInfo.setSysBuildingCd("SBLD00001");
		buildingStationInfo.setDivNo(000);
		buildingStationInfo.setDefaultRouteCd("P116001");
		buildingStationInfo.setStationCd("22495");
		this.buildingStationInfoDAO.insert(new BuildingStationInfo[]{buildingStationInfo});

		// 物件こだわり条件情報
		housingPartInfo = new HousingPartInfo();
		housingPartInfo.setSysHousingCd("SHOU00001");
		housingPartInfo.setPartSrchCd("REI");
		this.housingPartInfoDAO.insert(new HousingPartInfo[] { housingPartInfo });

		// ------ 【002】

		// 建物基本情報
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00002");
		buildingInfo.setBuildingCd("BLD00002");
		buildingInfo.setDisplayBuildingName("建物名０２");
		buildingInfo.setHousingKindCd("002");
		buildingInfo.setPrefCd("02");
		buildingInfo.setAddressCd("02101");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// 物件基本情報
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00002");
		housingInfo.setHousingCd("HOU00002");
		housingInfo.setDisplayHousingName("物件名０２");
		housingInfo.setSysBuildingCd("SBLD00002");
		housingInfo.setPrice(200000L);
		housingInfo.setLayoutCd("002");
		housingInfo.setPersonalArea(new BigDecimal("70"));
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// 建物最寄駅情報
		buildingStationInfo = new BuildingStationInfo();
		buildingStationInfo.setSysBuildingCd("SBLD00002");
		buildingStationInfo.setDivNo(000);
		buildingStationInfo.setDefaultRouteCd("P116002");
		buildingStationInfo.setStationCd("22513");
		this.buildingStationInfoDAO.insert(new BuildingStationInfo[]{buildingStationInfo});

		// 物件こだわり条件情報
		housingPartInfo = new HousingPartInfo();
		housingPartInfo.setSysHousingCd("SHOU00002");
		housingPartInfo.setPartSrchCd("FRE");
		this.housingPartInfoDAO.insert(new HousingPartInfo[] { housingPartInfo });

		// ------ 【003】

		// 建物基本情報
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00003");
		buildingInfo.setBuildingCd("BLD00003");
		buildingInfo.setDisplayBuildingName("建物名０３");
		buildingInfo.setHousingKindCd("003");
		buildingInfo.setPrefCd("03");
		buildingInfo.setAddressCd("03101");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// 物件基本情報
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00003");
		housingInfo.setHousingCd("HOU00003");
		housingInfo.setDisplayHousingName("物件名０３");
		housingInfo.setSysBuildingCd("SBLD00003");
		housingInfo.setPrice(300000L);
		housingInfo.setLayoutCd("003");
		housingInfo.setPersonalArea(new BigDecimal("80"));
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// 建物最寄駅情報
		buildingStationInfo = new BuildingStationInfo();
		buildingStationInfo.setSysBuildingCd("SBLD00003");
		buildingStationInfo.setDivNo(000);
		buildingStationInfo.setDefaultRouteCd("P116003");
		buildingStationInfo.setStationCd("22850");
		this.buildingStationInfoDAO.insert(new BuildingStationInfo[]{buildingStationInfo});

		// 物件こだわり条件情報
		housingPartInfo = new HousingPartInfo();
		housingPartInfo.setSysHousingCd("SHOU00003");
		housingPartInfo.setPartSrchCd("B05");
		this.housingPartInfoDAO.insert(new HousingPartInfo[] { housingPartInfo });

		// ------ 【004】

		// 建物基本情報
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00004");
		buildingInfo.setBuildingCd("BLD00004");
		buildingInfo.setDisplayBuildingName("建物名０４");
		buildingInfo.setHousingKindCd("004");
		buildingInfo.setPrefCd("04");
		buildingInfo.setAddressCd("04101");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// 物件基本情報
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00004");
		housingInfo.setHousingCd("HOU00004");
		housingInfo.setDisplayHousingName("物件名０４");
		housingInfo.setSysBuildingCd("SBLD00004");
		housingInfo.setPrice(400000L);
		housingInfo.setLayoutCd("004");
		housingInfo.setPersonalArea(new BigDecimal("90"));
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// 建物最寄駅情報
		buildingStationInfo = new BuildingStationInfo();
		buildingStationInfo.setSysBuildingCd("SBLD00004");
		buildingStationInfo.setDivNo(000);
		buildingStationInfo.setDefaultRouteCd("P116004");
		buildingStationInfo.setStationCd("22630");
		this.buildingStationInfoDAO.insert(new BuildingStationInfo[]{buildingStationInfo});

		// 物件こだわり条件情報
		housingPartInfo = new HousingPartInfo();
		housingPartInfo.setSysHousingCd("SHOU00004");
		housingPartInfo.setPartSrchCd("S05");
		this.housingPartInfoDAO.insert(new HousingPartInfo[] { housingPartInfo });

		// ------ 【005】

		// 建物基本情報
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00005");
		buildingInfo.setBuildingCd("BLD00005");
		buildingInfo.setDisplayBuildingName("建物名０５");
		buildingInfo.setHousingKindCd("005");
		buildingInfo.setPrefCd("05");
		buildingInfo.setAddressCd("05101");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// 物件基本情報
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00005");
		housingInfo.setHousingCd("HOU00005");
		housingInfo.setDisplayHousingName("物件名０１");
		housingInfo.setSysBuildingCd("SBLD00005");
		housingInfo.setPrice(500000L);
		housingInfo.setLayoutCd("005");
		housingInfo.setPersonalArea(new BigDecimal("100"));
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// 建物最寄駅情報
		buildingStationInfo = new BuildingStationInfo();
		buildingStationInfo.setSysBuildingCd("SBLD00005");
		buildingStationInfo.setDivNo(000);
		buildingStationInfo.setDefaultRouteCd("P116005");
		buildingStationInfo.setStationCd("22849");
		this.buildingStationInfoDAO.insert(new BuildingStationInfo[]{buildingStationInfo});

		// 物件こだわり条件情報
		housingPartInfo = new HousingPartInfo();
		housingPartInfo.setSysHousingCd("SHOU00005");
		housingPartInfo.setPartSrchCd("S10");
		this.housingPartInfoDAO.insert(new HousingPartInfo[] { housingPartInfo });

	}

	/**
	 * 物件リクエストからの物件検索処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>ページング関連のプロパティ値に合致した物件情報が正しく取得される事</li>
	 * </ul>
	 */
	@Test
	public void searchHousingPagingTest() throws Exception {

		makeTestHousingData();

		HousingRequestForm inputForm;
		String housingRequestId;

		RequestSearchForm searchForm;
		int cnt = 0;
		HousingInfo housingInfo;

		int actualRow =0;

		inputForm = new HousingRequestForm();
		inputForm.setRequestName("リクエスト名称");
		housingRequestId = housingRequestManage.addRequest("UID00001", inputForm);

		searchForm = requestSearchFormFactory.createRequestSearchForm();
		searchForm.setRowsPerPage(2);
		searchForm.setSelectedPage(1);
		searchForm.setHousingRequestId(housingRequestId);

		// 1ページ目
		cnt = housingRequestManage.searchHousing("UID00001", searchForm);
		Assert.assertEquals("物件リクエスト検索結果となる物件件数が正しいこと。", 5, cnt);
		// 実態のサイズを取得する
		actualRow = 0;
		for (Housing housing : searchForm.getRows()) {
			actualRow++;
		}
		int pageRowIndex = searchForm.getRowsPerPage() * searchForm.getSelectedPage() - searchForm.getRowsPerPage();
		Assert.assertEquals("ページング関連のプロパティ値に合致した物件情報が正しく取得される事", 2, actualRow);
		housingInfo = (HousingInfo) searchForm.getRows().get(pageRowIndex + 0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("ページング関連のプロパティ値に合致した物件情報が正しく取得される事", "SHOU00001", housingInfo.getSysHousingCd() );

		searchForm = requestSearchFormFactory.createRequestSearchForm();
		searchForm.setRowsPerPage(2);
		searchForm.setSelectedPage(2);
		searchForm.setHousingRequestId(housingRequestId);

		// 2ページ目
		cnt = housingRequestManage.searchHousing("UID00001", searchForm);
		Assert.assertEquals("物件リクエスト検索結果となる物件件数が正しいこと。", 5, cnt);
		// 実態のサイズを取得する
		actualRow = 0;
		for (Housing housing : searchForm.getRows()) {
			actualRow++;
		}
		pageRowIndex = searchForm.getRowsPerPage() * searchForm.getSelectedPage() - searchForm.getRowsPerPage();
		Assert.assertEquals("ページング関連のプロパティ値に合致した物件情報が正しく取得される事", 2, actualRow);
		housingInfo = (HousingInfo) searchForm.getRows().get(pageRowIndex + 0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("ページング関連のプロパティ値に合致した物件情報が正しく取得される事", "SHOU00003", housingInfo.getSysHousingCd() );


	}

}
