package jp.co.transcosmos.dm3.corePana.model.housing.dao.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.vo.AdminLoginInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingLandmark;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.EquipMst;
import jp.co.transcosmos.dm3.core.vo.HousingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.RrMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousing;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.dao.PanaSearchHousingDAO;
import jp.co.transcosmos.dm3.corePana.model.housing.dao.PanaSearchHousingRowMapper;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.util.PanaCalcUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.HousingInspection;
import jp.co.transcosmos.dm3.corePana.vo.HousingStatusInfo;
import jp.co.transcosmos.dm3.corePana.vo.MemberInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformChart;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.FragmentResultSetExtractor;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.util.StringUtils;

public class PanaSearchHousingDAOImpl implements PanaSearchHousingDAO {

	// テーブル物理名
	/** 物件基本情報テーブル名 */
	protected static final String TABLE_HOUSING_NAME = "housing";
	/** リフォームプランテーブル名 */
	protected static final String TABLE_REFORM_NAME = "reform";
	/** 物件基本情報テーブル名 */
	protected static final String TABLE_HOUSING_INFO_NAME = "housing_info";
	/** 物件ステータス情報テーブル名 */
	protected static final String TABLE_HOUSING_STATUS_INFO_NAME = "housing_status_info";
	/** 建物基本情報テーブル名 */
	protected static final String TABLE_BUILDING_INFO_NAME = "building_info";
	/** 建物詳細情報テーブル名 */
	protected static final String TABLE_BUILDING_DTL_INFO_NAME = "building_dtl_info";
	/** 建物最寄駅情報テーブル名 */
	protected static final String TABLE_BUILDING_STATION_NAME = "building_station_info";
	/** 都道府県マスタテーブル名 */
	protected static final String TABLE_PREF_MST_NAME = "pref_mst";
	/** 物件詳細情報テーブル名 */
	protected static final String TABLE_HOUSING_DTL_INFO_NAME = "housing_dtl_info";
	/** マイページ会員情報テーブル名 */
	protected static final String TABLE_MEMBER_INFO_NAME = "member_info";
	/** 管理者ログインＩＤ情報テーブル名 */
	protected static final String TABLE_ADMIN_LOGIN_INFO_NAME = "admin_login_info";
	/** 物件拡張属性情報テーブル名 */
	protected static final String TABLE_HOUSING_EXT_INFO_NAME = "housing_ext_info";
	/** 物件設備情報テーブル名 */
	protected static final String TABLE_HOUSING_EQUIP_INFO_NAME = "housing_equip_info";
	/** 物件インスペクションテーブル名 */
	protected static final String TABLE_HOUSING_INSPECTION_NAME = "housing_inspection";
	/** 建物ランドマーク情報テーブル名 */
	protected static final String TABLE_BUILDING_LANDMARK_NAME = "building_landmark";
	/** リフォームプランテーブル名 */
	protected static final String TABLE_REFORM_PLAN_NAME = "reform_plan";
	/** リフォーム詳細情報テーブル名 */
	protected static final String TABLE_REFORM_DTL_NAME = "reform_dtl";
	/** リフォーム・レーダーチャートテーブル名 */
	protected static final String TABLE_REFORM_CHART_NAME = "reform_chart";

	// エイリアス
	/** 物件基本情報エイリアス */
	protected static final String TABLE_HOUSING_INFO_ALIAS = "housingInfo";
	/** 物件ステータス情報エイリアス */
	protected static final String TABLE_HOUSING_STATUS_INFO_ALIAS = "housingStatusInfo";
	/** 建物基本情報エイリアス */
	protected static final String TABLE_BUILDING_INFO_ALIAS = "buildingInfo";
	/** 建物詳細情報エイリアス */
	protected static final String TABLE_BUILDING_DTL_INFO_ALIAS = "buildingDtlInfo";
	/** 建物最寄駅情報エイリアス */
	protected static final String TABLE_BUILDING_STATION_ALIAS = "buildingStationInfo";
	/** 都道府県マスタエイリアス */
	protected static final String TABLE_PREF_MST_ALIAS = "prefMst";
	/** 物件詳細情報エイリアス */
	protected static final String TABLE_HOUSING_DTL_INFO_ALIAS = "housingDtlInfo";
	/** マイページ会員情報エイリアス */
	protected static final String TABLE_MEMBER_INFO_ALIAS = "memberInfo";
	/** 管理者ログインＩＤ情報エイリアス */
	protected static final String TABLE_ADMIN_LOGIN_INFO1_ALIAS = "adminLoginInfo1";
	/** 管理者ログインＩＤ情報エイリアス */
	protected static final String TABLE_ADMIN_LOGIN_INFO2_ALIAS = "adminLoginInfo2";
	
	protected static final String TABLE_REFORM_PLAN_ALIAS = "reformPlan";

	/** CSV出力の名 */
	protected static final String[] CSV_OUT_NAME = {"housing","reform"};

	// ソート条件
	/** 物件番号（ソート条件） */
	public static final String SORT_SYS_HOUSING_CD_ASC = "0";

	/** 賃料/価格が安い順（ソート条件） */
	public static final String SORT_PRICE_ASC = "1";
	/** 賃料/価格が高い順（ソート条件） */
	public static final String SORT_PRICE_DESC = "2";
	/** 物件登録日（新着順）（ソート条件） */
	public static final String SORT_UPD_DATA_ASC = "3";
	/** 物件登録日（新着順）（ソート条件） */
	public static final String SORT_UPD_DATA_DESC = "4";
	/** 築年が古い順（ソート条件） */
	public static final String SORT_BUILD_DATE_DESC = "5";
	/** 築年が新しい順（ソート条件） */
	public static final String SORT_BUILD_DATE_ASC = "6";
	/** 駅からの距離（ソート条件） */
	public static final String SORT_WALK_TIME_ASC = "7";
	/** 駅からの距離（ソート条件） */
	public static final String SORT_WALK_TIME_DESC = "8";

	private static final String _CHARACTER_ENCODING = "UTF-8";

	private static final String _CONTENT_TYPE = "application/octet-stream";

	private static final String _HEADER1 = "Content-Disposition";
	private static final String _HEADER2 = "attachment ;filename=";
	private static final int _BYTE_SIZE = 1024;
	// log
	private static final Log log = LogFactory
			.getLog(PanaSearchHousingDAOImpl.class);

	/** データソース */
	private DataSource dataSource;
	/** 物件検索結果を格納する為のMapper */
	private PanaSearchHousingRowMapper panaSearchHousingRowMapper;
	/** 共通コード変換処理 */
	private CodeLookupManager codeLookupManager;

	private DAO<AdminLoginInfo> adminLoginInfoDAO;

	/**
	 * @param adminLoginInfoDAO セットする adminLoginInfoDAO
	 */
	public void setAdminLoginInfoDAO(DAO<AdminLoginInfo> adminLoginInfoDAO) {
		this.adminLoginInfoDAO = adminLoginInfoDAO;
	}

	// →駅検索にて使用する為にOR_PART_SRCH_LISTに直接宣言しない
	/** 駅徒歩5分 or 駅徒歩10分 or 駅徒歩15分 or 駅徒歩20分 or バス徒歩5分 */
	private Map<String, String> orPartSrchWarktime = new HashMap<String, String>();

	/**
	 * 駅徒歩5分 or 駅徒歩10分 or 駅徒歩15分 or 駅徒歩20分 or バス徒歩5分を設定する。<br/>
	 * <br/>
	 *
	 * @param orPartSrchWarktime
	 *            駅徒歩5分 or 駅徒歩10分 or 駅徒歩15分 or 駅徒歩20分 or バス徒歩5分
	 */
	public void setOrPartSrchWarktime(Map<String, String> orPartSrchWarktime) {
		this.orPartSrchWarktime = orPartSrchWarktime;
	}

	/**
	 * データソースを設定する。<br/>
	 * <br/>
	 *
	 * @param dataSource
	 *            データソース
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * 物件検索結果を格納する為のMapperを設定する。<br/>
	 * <br/>
	 *
	 * @param panaSearchHousingRowMapper
	 *            物件検索結果を格納する為のMapper
	 */
	public void setPanaSearchHousingRowMapper(
			PanaSearchHousingRowMapper panaSearchHousingRowMapper) {
		this.panaSearchHousingRowMapper = panaSearchHousingRowMapper;
	}

	/**
	 * 共通コード変換処理を設定する。<br/>
	 * <br/>
	 *
	 * @param codeLookupManager
	 *            共通コード変換処理
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * 物件情報（一部、建物情報）を検索し、結果リストを復帰する。（一覧用）<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、物件情報を検索する。<br/>
	 * 検索結果は List<Housing> オブジェクトに格納され、取得したList<Housing>を戻り値として復帰する。<br/>
	 * <br/>
	 *
	 * @param searchForm
	 *            検索条件の格納オブジェクト
	 * @return List<Housing>
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Housing> panaSearchHousing(PanaHousingSearchForm searchForm) {

		JdbcTemplate template = new JdbcTemplate(dataSource);

		StringBuffer sbSql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		// 検索SQLを生成
		createSql(searchForm, sbSql, params);

		log.debug("SQL : " + sbSql.toString());
		List<Housing> housings = (List<Housing>) template.query(sbSql.toString(), (Object[]) params.toArray(),
				new FragmentResultSetExtractor<Object>(this.panaSearchHousingRowMapper, searchForm.getStartIndex(),
				searchForm.getEndIndex()));
		return housings;
	}

	/**
	 * 物件情報（CSV出力情報）を検索し、結果リストを復帰する。（CSV出力用）<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、物件情報を検索する。<br/>
	 * CSV出力データを生成し、CSVファイルを出力する。<br/>
	 * <br/>
	 *
	 * @param searchForm
	 *            検索条件の格納オブジェクト
	 * @throws IOException
	 *
	 */
	@Override
	public void panaSearchHousing(PanaHousingSearchForm searchForm, HttpServletResponse response,
			PanaHousingPartThumbnailProxy panaHousingManager, PanaCommonManage panamCommonManager) throws IOException {

		JdbcTemplate template = new JdbcTemplate(dataSource);

		StringBuffer sbSql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		// 検索SQLを生成
		createSql(searchForm, sbSql, params);

		log.debug("SQL : " + sbSql.toString());

		List<PanaHousing> panaHousings = new ArrayList<PanaHousing>();
		List<AdminLoginInfo> adminLoginInfo_InUsers = new ArrayList<AdminLoginInfo>();
		List<MemberInfo> memberInfos = new ArrayList<MemberInfo>();
		List<EquipMst> equipMstList = new ArrayList<EquipMst>();
		template.query(sbSql.toString(), params.toArray(), new HousingListRowCallbackHandler(panaHousingManager,
				memberInfos, adminLoginInfo_InUsers,panaHousings));
		try {
			equipMstList = panamCommonManager.getEquipMstList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		response.setCharacterEncoding(_CHARACTER_ENCODING);
		response.setContentType(_CONTENT_TYPE);
		response.setHeader(_HEADER1, _HEADER2 + getZipFileName());
		ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
		byte[] bom = {(byte)0xef, (byte)0xbb, (byte)0xbf};
		for (String str : CSV_OUT_NAME) {
			String csvFileName = str + ".csv";
			zos.putNextEntry(new ZipEntry(csvFileName));
			zos.write(bom);
			writeCSVHeader(str, zos, equipMstList);
			for (int i = 0; i< panaHousings.size(); i++) {
				writeCSVContent(str,panaHousings.get(i),zos,memberInfos.get(i),adminLoginInfo_InUsers.get(i),equipMstList);
			}
		}
		zos.flush();
		zos.close();
	}

	private void zipWriter(String str, ZipOutputStream zos) throws IOException {
		byte[] bs= new byte[_BYTE_SIZE];
		int value = -1;
		ByteArrayInputStream bais = new ByteArrayInputStream(str.getBytes(_CHARACTER_ENCODING));
		BufferedInputStream bis = new BufferedInputStream(bais,_BYTE_SIZE);
		while ((value = bis.read(bs, 0, bs.length)) != -1) {
			zos.write(bs, 0, value);
		}
		bis.close();
		bais.close();
	}

	/**
	 * CSVファイル名を取得する。<br/>
	 * <br/>
	 *
	 * @return String CSVファイル名
	 */
	private String getZipFileName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dt = sdf.format(new Date());

		return "bukken_" + dt + ".zip";
	}

	@SuppressWarnings("unchecked")
	private void writeCSVContent(String str, PanaHousing panaHousing, ZipOutputStream zos,
			MemberInfo memberInfo, AdminLoginInfo adminLoginInfo_InUser, List<EquipMst> equipMsts) throws IOException {
		StringBuilder sb = new StringBuilder();
		HousingInfo housingInfo = (HousingInfo)panaHousing.getHousingInfo().getItems().get("housingInfo");
		switch (str) {
			case TABLE_HOUSING_NAME:
				AdminLoginInfo adminLoginInfo_UpUser = panaHousing.getHousingInfoUpdUser();
				BuildingInfo buildingInfo = (BuildingInfo)panaHousing.getBuilding().getBuildingInfo().getItems().get("buildingInfo");
				PrefMst prefMst = (PrefMst)panaHousing.getBuilding().getBuildingInfo().getItems().get("prefMst");
				HousingDtlInfo housingDtlInfo = (HousingDtlInfo)panaHousing.getHousingInfo().getItems().get("housingDtlInfo");
				// 物件拡張属性情報の取得
				Map<String, Map<String, String>> housingExtInfos = panaHousing.getHousingExtInfos();
				// 最寄り駅情報の取得
				List<JoinResult> joinResultList = panaHousing.getBuilding().getBuildingStationInfoList();
				BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo)panaHousing.getBuilding().getBuildingInfo().getItems().get("buildingDtlInfo");
				Map<String, EquipMst> equipMstMap = panaHousing.getHousingEquipInfos();
				// 建物ランドマーク情報の取得
				List<BuildingLandmark> buildingLandmarkList = panaHousing.getBuilding().getBuildingLandmarkList();
				// 物件インスペクションの取得
				List<HousingInspection> housingInspectionList = panaHousing.getHousingInspections();
				HousingStatusInfo housingStatusInfo = (HousingStatusInfo)panaHousing.getHousingInfo().getItems().get("housingStatusInfo");
				AdminLoginInfo adminLoginInfo_UpUser_Id = panaHousing.getHousingInfoUpdUser();
				if(housingInfo != null){
					sb.append("\"" + PanaStringUtils.toString(housingInfo.getHousingCd()) + "\",");//物件番号
					sb.append("\"" + PanaStringUtils.toString(housingInfo.getDisplayHousingName()) + "\",");//物件名
					if (buildingInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(lookupValueWithDefault("buildingInfo_housingKindCd", buildingInfo.getHousingKindCd())) + "\",");//物件種別
					}else{
						sb.append("\"\",");
					}
					if (housingDtlInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(housingDtlInfo.getDtlComment()) + "\",");//物件コメント
					}else{
						sb.append("\"\",");
					}
					if (!housingExtInfos.isEmpty()) {
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "housingDetail","vendorComment")) + "\",");//売主コメント
					}else{
						sb.append("\"\",");
					}
					if (buildingInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(buildingInfo.getZip()) + "\",");//郵便番号（物件）
					}else{
						sb.append("\"\",");
					}
					if (prefMst != null) {
						sb.append("\"" + PanaStringUtils.toString(prefMst.getPrefName()) + "\",");//住所1(物件)
					}else {
						sb.append("\"\",");
					}
					if (buildingInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(buildingInfo.getAddressName()) + PanaStringUtils.toString(buildingInfo.getAddressOther1()) + PanaStringUtils.toString(buildingInfo.getAddressOther2()) + "\",");//住所2（物件）
					}else {
						sb.append("\"\",");
					}
					if (!joinResultList.isEmpty()) {
						// 沿線１～３、駅１～３、バス１～３、徒歩１～３
						List<String[]> strList = getRouteStationBusName(joinResultList);
						for (int i = 0; i < strList.size(); i++) {
							sb.append("\"" + PanaStringUtils.toString(strList.get(i)[0]) + "\",");// 沿線
							sb.append("\"" + PanaStringUtils.toString(strList.get(i)[1]) + "\",");// 駅
							sb.append("\"" + PanaStringUtils.toString(strList.get(i)[2]) + "\",");// バス
							sb.append("\"" + PanaStringUtils.toString(strList.get(i)[3]) + "\",");// 徒歩
						}
					}else {
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
					}
					sb.append("\"" + PanaStringUtils.toString(format(housingInfo.getPrice(),"円")) + "\",");//物件価格
					sb.append("\"" + PanaStringUtils.toString(lookupValueWithDefault("layoutCd",housingInfo.getLayoutCd())) + "\",");//間取
					if(buildingDtlInfo != null){
						sb.append("\"" + PanaStringUtils.toString(format(buildingDtlInfo.getBuildingArea(), "㎡")) + "\",");//建物面積
					}else {
						sb.append("\"\",");
					}
					if(buildingDtlInfo != null){
						sb.append("\"" + PanaStringUtils.toString(buildingDtlInfo.getBuildingAreaMemo()) + "\",");//建物面積_補足
					}else {
						sb.append("\"\",");
					}
					sb.append("\"" + PanaStringUtils.toString(format(housingInfo.getLandArea(), "㎡")) + "\",");//土地面積
					sb.append("\"" + PanaStringUtils.toString(housingInfo.getLandAreaMemo()) + "\",");//土地面積_補足
					sb.append("\"" + PanaStringUtils.toString(format(housingInfo.getPersonalArea(),"㎡")) + "\",");//専有面積
					sb.append("\"" + PanaStringUtils.toString(housingInfo.getPersonalAreaMemo()) + "\",");//専有面積_補足
					if (buildingInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(format(buildingInfo.getCompDate(),"YYYY年MM月")) + "\",");//築年月
					}else {
						sb.append("\"\",");
					}
					if (housingDtlInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(lookupValueWithDefault("landRight",housingDtlInfo.getLandRight())) + "\",");//土地権利
					}else {
						sb.append("\"\",");
					}
					if (!housingExtInfos.isEmpty()) {
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "housingDetail","struct")) + "\",");//建物構造
					}else {
						sb.append("\"\",");
					}
					if (housingDtlInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(housingDtlInfo.getContactRoad()) + "\",");//接道状況
					}else {
						sb.append("\"\",");
					}
					if (housingDtlInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(housingDtlInfo.getContactRoadDir()) + "\",");//接道方向/幅員
					}else {
						sb.append("\"\",");
					}
					if (housingDtlInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(housingDtlInfo.getPrivateRoad()) + "\",");//私道負担
					}else {
						sb.append("\"\",");
					}
					sb.append("\"" + PanaStringUtils.toString(housingInfo.getDisplayParkingInfo()) + "\",");//駐車場
					if (housingDtlInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(lookupValueWithDefault("usedAreaCd",housingDtlInfo.getUsedAreaCd())) + "\",");//用途地域
					}else {
						sb.append("\"\",");
					}
					if(buildingDtlInfo != null){
						sb.append("\"" + PanaStringUtils.toString(buildingDtlInfo.getCoverageMemo()) + "\",");//建ぺい率
					}else {
						sb.append("\"\",");
					}
					if(buildingDtlInfo != null){
						sb.append("\"" + PanaStringUtils.toString(buildingDtlInfo.getBuildingRateMemo()) + "\",");//容積率
					}else {
						sb.append("\"\",");
					}
					if (!housingExtInfos.isEmpty()) {
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "housingDetail","status")) + "\",");//現況
					}else {
						sb.append("\"\",");
					}
					if (housingDtlInfo != null) {
						String moveinTiming = "";
						if (PanaCommonConstant.MOVEIN_TIMING.equals(housingDtlInfo.getMoveinTiming())) {
							moveinTiming = "即時";
						}
						sb.append("\"" + PanaStringUtils.toString(moveinTiming) + "\",");//引渡時期
					}else {
						sb.append("\"\",");
					}
					if (housingDtlInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(housingDtlInfo.getMoveinNote()) + "\",");//引渡時期コメント
					}else {
						sb.append("\"\",");
					}
					if (housingDtlInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(lookupValueWithDefault("transactTypeDiv", housingDtlInfo.getTransactTypeDiv())) + "\",");//取引形態
					}else {
						sb.append("\"\",");
					}
					if (housingDtlInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(housingDtlInfo.getSpecialInstruction()) + "\",");//特記事項
					}else {
						sb.append("\"\",");
					}
//					if (housingDtlInfo != null) {
//						sb.append("\"" + (housingDtlInfo.getUpkeepCorp()) + "\",");//備考
//					}else {
//						sb.append("\"\",");
//					}
					if (buildingInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(format(buildingInfo.getTotalFloors(), "階建て")) + "\",");//建物階数
					}else {
						sb.append("\"\",");
					}
					sb.append("\"" + PanaStringUtils.toString(format(housingInfo.getFloorNo(),"階")) + "\",");//所在階数
					sb.append("\"" + PanaStringUtils.toString(housingInfo.getFloorNoNote()) + "\",");// 所在階数コメント
					if (!housingExtInfos.isEmpty()) {
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "housingDetail","totalHouseCnt")) + "\",");//総戸数
					}else {
						sb.append("\"\",");
					}
					if (!housingExtInfos.isEmpty()) {
						sb.append("\"" + PanaStringUtils.toString(lookupValueWithDefault("scaleDataValue",getHousingExtInfos(housingExtInfos, "housingDetail","scale"))) + "\",");//規模
					}else {
						sb.append("\"\",");
					}
					if (!housingExtInfos.isEmpty()) {
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "housingDetail","direction")) + "\",");//向き(日当たり・採光）
					}else {
						sb.append("\"\",");
					}
					sb.append("\"" + PanaStringUtils.toString(format(housingInfo.getUpkeep(),"月円")) + "\",");//管理費
					sb.append("\"" + PanaStringUtils.toString(format(housingInfo.getMenteFee(),"月円")) + "\",");//修繕積立金
					if (housingDtlInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(housingDtlInfo.getUpkeepCorp()) + "\",");//管理会社
					}else {
						sb.append("\"\",");
					}
					if (housingDtlInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(housingDtlInfo.getUpkeepType()) + "\",");//管理形態・方式
					}else {
						sb.append("\"\",");
					}
					if (housingDtlInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(format(housingDtlInfo.getBalconyArea(),"㎡")) + "\",");//バルコニー面積
					}else {
						sb.append("\"\",");
					}
					if (housingDtlInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(lookupValueWithDefault("insurExist",housingDtlInfo.getInsurExist())) + "\",");//瑕疵保険
					}else {
						sb.append("\"\",");
					}
					if (!housingExtInfos.isEmpty()) {
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "housingDetail","staffName")) + "\",");//担当者名
					}else {
						sb.append("\"\",");
					}
					if (!housingExtInfos.isEmpty()) {
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "housingDetail","companyName")) + "\",");//会社名
					}else {
						sb.append("\"\",");
					}
					if (!housingExtInfos.isEmpty()) {
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "housingDetail","branchName")) + "\",");//支店名
					}else {
						sb.append("\"\",");
					}
					if (!housingExtInfos.isEmpty()) {
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "housingDetail","licenseNo")) + "\",");//免許番号
					}else {
						sb.append("\"\",");
					}
					sb.append("\"" + PanaStringUtils.toString(housingInfo.getBasicComment()) + "\",");//担当者コメント
					if (!housingExtInfos.isEmpty()) {
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "housingDetail","infrastructure")) + "\",");//インフラ
					}else {
						sb.append("\"\",");
					}
					// 設備名称(二世帯向け、床暖房、リビングダイニング15畳以上、フローリング、室、ウォークインクローゼット、ロフト、
					// 浴室換気乾燥機、TVモニタ付きインターフォン、ウッドデッキ、ペット可、宅配ボックス、角部屋、角地)
					if(!equipMstMap.isEmpty()){
						for (int i = 0; i<equipMsts.size(); i++) {
							if(equipMstMap.containsKey(equipMsts.get(i).getEquipCd())){
								if (i == equipMsts.size() - 1) {
									sb.append("\"" +"あり"+"\",");
								} else {
									sb.append("\"" +"あり"+"\",");
								}
							} else {
								if (i == equipMsts.size() - 1) {
									sb.append("\"" +"なし"+"\",");
								} else {
									sb.append("\"" +"なし"+"\",");
								}
							}
						}
					}else{
						for (int i=0; i<equipMsts.size(); i++) {
							if (i == equipMsts.size() - 1) {
								sb.append("\"" +"なし"+"\",");
							} else {
								sb.append("\"" +"なし"+"\",");
							}
						}
					}
					if (!housingExtInfos.isEmpty()) {
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo01")) + "\",");//管理者用設備情報01
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo02")) + "\",");//管理者用設備情報02
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo03")) + "\",");//管理者用設備情報03
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo04")) + "\",");//管理者用設備情報04
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo05")) + "\",");//管理者用設備情報05
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo06")) + "\",");//管理者用設備情報06
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo07")) + "\",");//管理者用設備情報07
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo08")) + "\",");//管理者用設備情報08
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo09")) + "\",");//管理者用設備情報09
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo10")) + "\",");//管理者用設備情報10
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo11")) + "\",");//管理者用設備情報11
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo12")) + "\",");//管理者用設備情報12
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo13")) + "\",");//管理者用設備情報13
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo14")) + "\",");//管理者用設備情報14
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo15")) + "\",");//管理者用設備情報15
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo16")) + "\",");//管理者用設備情報16
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo17")) + "\",");//管理者用設備情報17
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo18")) + "\",");//管理者用設備情報18
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo19")) + "\",");//管理者用設備情報19
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo20")) + "\",");//管理者用設備情報20
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo21")) + "\",");//管理者用設備情報21
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo22")) + "\",");//管理者用設備情報22
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo23")) + "\",");//管理者用設備情報23
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo24")) + "\",");//管理者用設備情報24
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo25")) + "\",");//管理者用設備情報25
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo26")) + "\",");//管理者用設備情報26
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo27")) + "\",");//管理者用設備情報27
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo28")) + "\",");//管理者用設備情報28
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo29")) + "\",");//管理者用設備情報29
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo30")) + "\",");//管理者用設備情報30
					}else {
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
					}
					// 公園	病院	保育園/幼稚園	小学校	中学校	スーパー	コンビニ	その他施設
					if (!buildingLandmarkList.isEmpty()) {
						// 建物ランドマーク情報の取得
						Iterator<String> codeLookup_Keys_Type = this.codeLookupManager.getKeysByLookup("buildingLandmark_landmarkType");
						while (codeLookup_Keys_Type.hasNext()) {
							String codeLookup_Key_Type = (String) codeLookup_Keys_Type.next();
							for (int i = 0; i < buildingLandmarkList.size(); i++) {
								BuildingLandmark bl = buildingLandmarkList.get(i);
								BigDecimal distanceFromLandmark = null;
								if (bl.getDistanceFromLandmark() != null) {
									distanceFromLandmark = PanaCalcUtil.calcLandMarkTime(bl.getDistanceFromLandmark());
								}
								if (codeLookup_Key_Type.matches(bl.getLandmarkType())) {
									if(codeLookup_Keys_Type.hasNext()){
										if (bl.getLandmarkName() != null) {
											if (bl.getDistanceFromLandmark() != null) {
												sb.append("\"" + PanaStringUtils.toString(bl.getLandmarkName() + "：徒歩" + distanceFromLandmark + "分(" + bl.getDistanceFromLandmark() + "m)") + "\",");
											} else {
												sb.append("\"" + PanaStringUtils.toString(bl.getLandmarkName()) + "\",");
											}
										} else {
											sb.append("\"\",");
										}
									} else {
										if (bl.getLandmarkName() != null) {
											if (bl.getDistanceFromLandmark() != null) {
												sb.append("\"" + PanaStringUtils.toString(bl.getLandmarkName() + "：徒歩" + distanceFromLandmark + "分(" + bl.getDistanceFromLandmark() + "m)") + "\",");
											} else {
												sb.append("\"" + PanaStringUtils.toString(bl.getLandmarkName()) + "\",");
											}
										} else {
											sb.append("\"\",");
										}
									}
									break;
								}
								if (i == buildingLandmarkList.size() - 1) {
									if(codeLookup_Keys_Type.hasNext()){
										sb.append("\"\",");
									} else {
										sb.append("\"\",");
									}
								}
							}
						}
					}else{
						// 公園	病院	保育園/幼稚園	小学校	中学校	スーパー	コンビニ	その他施設
						Iterator<String> codeLookup_Keys_Type = this.codeLookupManager.getKeysByLookup("buildingLandmark_landmarkType");
						while (codeLookup_Keys_Type.hasNext()) {
							String codeLookup_Key_Type = (String) codeLookup_Keys_Type.next();
							if (codeLookup_Keys_Type.hasNext()) {
								sb.append("\"\",");
							} else {
								sb.append("\"\",");
							}
						}
					}
					if (!housingExtInfos.isEmpty()) {
						sb.append("\"" + PanaStringUtils.toString(lookupValueWithDefault("housingInspection",getHousingExtInfos(housingExtInfos, "housingInspection","inspectionExist"))) + "\",");//住宅診断実施有無
					}else {
						sb.append("\"\",");
					}
					if(!housingInspectionList.isEmpty()){
						List<String[]> iList = getHousingInspection(housingInspectionList);
						for (int i = 0; i < iList.size(); i++) {
							if (i == 7) {
								sb.append("\"" + PanaStringUtils.toString(iList.get(i)[0]) + "\",");// レーダーチャート数値（評価基準）
								sb.append("\"" + PanaStringUtils.toString(iList.get(i)[1]) + "\",");// レーダーチャート数値（確認範囲）
							} else {
								sb.append("\"" + PanaStringUtils.toString(iList.get(i)[0]) + "\",");// レーダーチャート数値（評価基準）
								sb.append("\"" + PanaStringUtils.toString(iList.get(i)[1]) + "\",");// レーダーチャート数値（確認範囲）
							}
						}
					}else{
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
					}
					sb.append("\"" + PanaStringUtils.toString(housingInfo.getReformComment()) + "\",");//リフォームプラン準備中文言
					// アイコン情報の処理
					// 住宅診断済,新築保証承継可,瑕疵保険可,リフォーム保険可,SumStock,一体型ローン可
					Iterator<String> codeLookup_Keys = this.codeLookupManager.getKeysByLookup("recommend_point_icon");
					String[] iconCdArr = null;
					if (!StringValidateUtil.isEmpty(housingInfo.getIconCd())) {
						iconCdArr = housingInfo.getIconCd().split(",");
					}
					while (codeLookup_Keys.hasNext()) {
						String codeLookup_Key = (String) codeLookup_Keys.next();
						if (iconCdArr != null) {
							for (int i = 0; i < iconCdArr.length; i++) {
								if(codeLookup_Key.matches(iconCdArr[i])){
									sb.append("\"" + PanaStringUtils.toString("あり") + "\",");
									break;
								}
								if(i == iconCdArr.length - 1){
									sb.append("\"" + PanaStringUtils.toString("なし") + "\",");
								}
							}
						} else {
							sb.append("\"" + PanaStringUtils.toString("なし") + "\",");
						}
					}
					if (housingStatusInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(lookupValueWithDefault("hiddenFlg",housingStatusInfo.getHiddenFlg())) + "\",");//公開区分
					}else {
						sb.append("\"\",");
					}
					if (housingStatusInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(lookupValueWithDefault("statusCd",housingStatusInfo.getStatusCd())) + "\",");//ステータス
					}else {
						sb.append("\"\",");
					}
					if (housingStatusInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(housingStatusInfo.getUserId()) + "\",");//会員番号
					}else {
						sb.append("\"\",");
					}
					if (memberInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(memberInfo.getMemberLname())+"　"+ PanaStringUtils.toString(memberInfo.getMemberFname()) + "\",");//氏名
					}else {
						sb.append("\"\",");
					}
					if (memberInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(memberInfo.getTel()) + "\",");// 電話番号
					}else {
						sb.append("\"\",");
					}
					if (memberInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(memberInfo.getEmail()) + "\",");// メールアドレス
					}else {
						sb.append("\"\",");
					}
					if (housingStatusInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(housingStatusInfo.getNote()) + "\",");//備考（管理者用）
					}else {
						sb.append("\"\",");
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					if(housingInfo.getInsDate() != null){
						sb.append("\"" + PanaStringUtils.toString(sdf.format(housingInfo.getInsDate())) + "\",");//新規登録日時
					}else {
						sb.append("\"\",");//新規登録日時
					}
					if (adminLoginInfo_InUser != null) {
						sb.append("\"" + PanaStringUtils.toString(adminLoginInfo_InUser.getUserName()) + "\",");//新規登録者
					} else {
						sb.append("\"\",");//新規登録者
					}
					if(housingInfo.getUpdDate() != null){
						sb.append("\"" + PanaStringUtils.toString(sdf.format(housingInfo.getUpdDate())) + "\",");//最終更新日時
					}else {
						sb.append("\"\",");//最終更新日時
					}
					if (adminLoginInfo_UpUser_Id != null) {
						sb.append("\"" + PanaStringUtils.toString(adminLoginInfo_UpUser_Id.getAdminUserId()) + "\",");// 最終更新者（ID）
					}
					if (adminLoginInfo_UpUser != null) {
						sb.append("\"" + PanaStringUtils.toString(adminLoginInfo_UpUser.getUserName()) + "\",");//最終更新者
					} else {
						sb.append("\"\"");//最終更新者
					}
				}
				sb.append("\r\n");
				break;
			case TABLE_REFORM_NAME:
				// リフォームプラン情報の取得
				List<Map<String, Object>> reformList0 = new ArrayList<Map<String, Object>>();
				reformList0 = panaHousing.getReforms();
				// リフォームプラン
				List<ReformPlan>  reformPlanResult = new ArrayList<ReformPlan>();
				// リフォーム詳細情報
				List<List<ReformDtl>> reformDtlResult = new ArrayList<List<ReformDtl>>();
				// リフォーム・レーダーチャート
				List<List<ReformChart>> chartResult = new ArrayList<List<ReformChart>>();

				for (Map<String, Object> reformMap : reformList0) {
					// リフォームプラン
					ReformPlan reformPlan = (ReformPlan)reformMap.get("reformPlan");
					reformPlanResult.add(reformPlan);
					// リフォーム詳細情報
					List<ReformDtl> reformDtlList = (List<ReformDtl>) reformMap.get("dtlList");
					reformDtlResult.add(reformDtlList);
					// リフォーム・レーダーチャート
					List<ReformChart> reformChartList = (List<ReformChart>) reformMap.get("chartList");
					chartResult.add(reformChartList);
				}
				if (!reformPlanResult.isEmpty()) {
					// リフォームプラン情報に出力の項目を設定する。
					String planPrice = "";// 価格
					String housingPrice = format(housingInfo.getPrice(),"万円");
					for (ReformPlan rp : reformPlanResult) {
						sb.append("\"" + PanaStringUtils.toString(housingInfo.getHousingCd()) + "\",");//物件番号
						sb.append("\"" + PanaStringUtils.toString(housingInfo.getDisplayHousingName()) + "\",");//物件名
						sb.append("\"" + PanaStringUtils.toString(rp.getPlanName()) + "\",");//リフォームプラン名
						if (housingInfo.getPrice() == null && rp.getPlanPrice() != null) {
							planPrice = "物件価格"+ "0万円" + " + リフォーム価格" + format(rp.getPlanPrice(), "万円") + " = " + format(rp.getPlanPrice(),"万円");// 価格
						} else if (housingInfo.getPrice() != null && rp.getPlanPrice() == null) {
							planPrice = "物件価格"+ housingPrice + " + リフォーム価格" + "0万円" + " = " + housingPrice;// 価格
						} else if (housingInfo.getPrice() != null && rp.getPlanPrice() != null) {
							Long[] LArrPrice = new Long[]{housingInfo.getPrice(),rp.getPlanPrice()};
							planPrice = "物件価格"+ housingPrice + " + リフォーム価格" + format(rp.getPlanPrice(), "万円") + " = "
									+ format(LArrPrice, "万円+");// 価格
						}
						sb.append("\"" + PanaStringUtils.toString(planPrice) + "\",");//価格
						sb.append("\"" + PanaStringUtils.toString(lookupValueWithDefault("hiddenFlg", rp.getHiddenFlg())) + "\",");//公開区分
						if (!reformDtlResult.isEmpty()) {
							sb.append("\"");
							for (List<ReformDtl> rdList : reformDtlResult) {
								for (ReformDtl rd : rdList) {
									if (rd.getSysReformCd().equals(rp.getSysReformCd())) {
										sb.append(PanaStringUtils.toString("「"+rd.getImgName() +":"+ format(rd.getReformPrice(), "万円")+"」"));// リフォーム詳細（内容）：フォーム詳細（価格）
									}
								}
							}
							sb.append("\",");
						}
						sb.append("\"" + PanaStringUtils.toString(rp.getSalesPoint()) + "\",");//セールスポイント（コンセプト）
						sb.append("\"" + PanaStringUtils.toString(rp.getConstructionPeriod()) + "\",");//工期
						sb.append("\"" + PanaStringUtils.toString(rp.getNote()) + "\",");//備考

						if (!chartResult.isEmpty()) {
							String[] chartResultArr = new String[]{"","","","","","","",""};
							for (List<ReformChart> rcList : chartResult) {
								if (!rcList.isEmpty()) {
									for (ReformChart rc : rcList) {
 										if (rc.getSysReformCd().equals(rp.getSysReformCd())) {
											if (rc.getChartValue() != null) {
												chartResultArr[Integer.valueOf(rc.getChartKey())-1] = lookupValueWithDefault("inspectionLabel",String.valueOf(rc.getChartValue()));
											}
										}
									}
								}
							}
							sb.append("\"" + PanaStringUtils.toString(chartResultArr[0]) + "\",");// レーダーチャート数値１（リフォーム実施後）
							sb.append("\"" + PanaStringUtils.toString(chartResultArr[1]) + "\",");// レーダーチャート数値２（リフォーム実施後）
							sb.append("\"" + PanaStringUtils.toString(chartResultArr[2]) + "\",");// レーダーチャート数値３（リフォーム実施後）
							sb.append("\"" + PanaStringUtils.toString(chartResultArr[3]) + "\",");// レーダーチャート数値４（リフォーム実施後）
							sb.append("\"" + PanaStringUtils.toString(chartResultArr[4]) + "\",");// レーダーチャート数値５（リフォーム実施後）
							sb.append("\"" + PanaStringUtils.toString(chartResultArr[5]) + "\",");// レーダーチャート数値６（リフォーム実施後）
							sb.append("\"" + PanaStringUtils.toString(chartResultArr[6]) + "\",");// レーダーチャート数値７（リフォーム実施後）
							sb.append("\"" + PanaStringUtils.toString(chartResultArr[7]) + "\",");// レーダーチャート数値８（リフォーム実施後）
						}
						// 項目追加
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						if(rp.getInsDate() != null){
							sb.append("\"" + PanaStringUtils.toString(sdf.format(rp.getInsDate())) + "\",");//新規登録日時
						}else {
							sb.append("\"\",");//新規登録日時
						}
						if (this.adminLoginInfoDAO.selectByPK(rp.getInsUserId()) != null) {
							sb.append("\"" + PanaStringUtils.toString(this.adminLoginInfoDAO.selectByPK(rp.getInsUserId()).getUserName()) + "\",");//新規登録者
						}else{
							sb.append("\"\",");//新規登録者
						}
						if(rp.getUpdDate() != null){
							sb.append("\"" + PanaStringUtils.toString(sdf.format(rp.getUpdDate())) + "\",");//最終更新日時
						}else {
							sb.append("\"\",");//最終更新日時
						}
						sb.append("\"" + PanaStringUtils.toString(rp.getUpdUserId()) + "\",");// 最終更新者（ID）
						if (this.adminLoginInfoDAO.selectByPK(rp.getUpdUserId()) != null) {
							sb.append("\"" + PanaStringUtils.toString(this.adminLoginInfoDAO.selectByPK(rp.getUpdUserId()).getUserName()) + "\"");//最終更新者
						}else{
							sb.append("\"\"");//最終更新者
						}
						sb.append("\r\n");
					}
				};
				break;

		default:
			break;
		}
		zipWriter(sb.toString(), zos);
	}

	private String lookupValueWithDefault(String lookupName, String key) {
		if (StringValidateUtil.isEmpty(key)) {
			return "";
		}
		String value = this.codeLookupManager.lookupValue(lookupName, key);
		if (value == null) {
			return "";
		}
		return value;
	}

	private String format(Object ob, String addCode) {
		String str = "";
		if (ob != null) {
			switch (addCode) {
				case "円":
					NumberFormat numberFormat = NumberFormat.getIntegerInstance(Locale.JAPANESE);
					str = numberFormat.format(ob) + addCode;
					break;
				case "万円":
					long LRemainder = ((Long)ob).longValue() % 10000;
					long tempValue = 0L;
					if(LRemainder != 0){
						tempValue = ((Long)ob).longValue() / 10000 + 1;
					} else {
						tempValue = ((Long)ob).longValue() / 10000;
					}
					NumberFormat numberFormat2 = NumberFormat.getIntegerInstance(Locale.JAPANESE);
					str = numberFormat2.format(tempValue) + addCode;
					break;
				case "万円+":
					Long[] lArr = ((Long[])ob);
					long LastValue = 0L;
					for (long lArrValue: lArr) {
						long LRemainderVal = lArrValue % 10000;
						long tempVal = 0L;
						if(LRemainderVal != 0){
							tempVal = lArrValue / 10000 + 1;
						} else {
							tempVal = lArrValue / 10000;
						}
						LastValue = LastValue + tempVal;
					}
					NumberFormat numberFormat3 = NumberFormat.getIntegerInstance(Locale.JAPANESE);
					str = numberFormat3.format(LastValue) + "万円";
					break;
				case "㎡":
					str = ob.toString() + addCode;
					break;
				case "階建て":
					str = ob.toString() + addCode;
					break;
				case "階":
					str = ob.toString() + addCode;
					break;
				case "月円":
					NumberFormat numberFormat1 = NumberFormat.getIntegerInstance(Locale.JAPANESE);
					str = "月" + numberFormat1.format(ob) + "円";
					break;
				case "YYYY/MM/dd/HH/MM/SS":
					SimpleDateFormat sdf = new SimpleDateFormat(addCode);
					str = sdf.format(ob);
					break;
				case "YYYY年MM月":
					SimpleDateFormat sdf1 = new SimpleDateFormat(addCode);
					str = sdf1.format(ob);
					break;
				default:
					break;
			}
		}
		return str;
	}

	private String getHousingExtInfos(
			Map<String, Map<String, String>> housingExtInfos,
			String keyName, String key) {
		String str = "";
		if (housingExtInfos.size() != 0) {
			Map<String, String> extInfokeyAddValue = housingExtInfos
					.get(keyName);
			if (extInfokeyAddValue != null) {
				if (extInfokeyAddValue.get(key) != null) {
					str = extInfokeyAddValue.get(key);
				}
			}
		}
		return str;
	}

	private List<String[]> getRouteStationBusName(List<JoinResult> jrList) {
		List<String[]> strList = new ArrayList<String[]>();
		for (int i = 0; i < jrList.size(); i++) {
			JoinResult jr = jrList.get(i);
			BuildingStationInfo buildingStationInfo = (BuildingStationInfo) jr
					.getItems().get("buildingStationInfo");
			RouteMst routeMst = (RouteMst) jr.getItems().get("routeMst");
			StationMst stationMst = (StationMst) jr.getItems().get(
					"stationMst");
			// 鉄道会社マストの取得
			RrMst rrMst = (RrMst) jr.getItems().get("rrMst");

			String[] strArr = new String[] { "-", "-", "-", "-" };
			if (StringValidateUtil.isEmpty(routeMst.getRouteName())) {
				if (!StringValidateUtil.isEmpty(buildingStationInfo
						.getDefaultRouteName())) {
					strArr[0] = buildingStationInfo.getDefaultRouteName();// 沿線
				}
			} else {
				strArr[0] = rrMst.getRrName()+routeMst.getRouteNameFull();// 沿線
			}
			if (StringValidateUtil.isEmpty(stationMst.getStationName())) {
				if (!StringValidateUtil.isEmpty(buildingStationInfo.getStationName())) {
					strArr[1] = buildingStationInfo.getStationName()+"駅";// 駅
				}
			} else {
				if(!StringValidateUtil.isEmpty(stationMst.getStationName())){
					strArr[1] = stationMst.getStationName()+"駅";// 駅
				}
			}
			if (!StringValidateUtil.isEmpty(buildingStationInfo.getBusCompany())) {
				strArr[2] = buildingStationInfo.getBusCompany();// バス
			}
			if (buildingStationInfo.getTimeFromBusStop() != null) {
				strArr[3] = String.valueOf(buildingStationInfo.getTimeFromBusStop())+"分";// 徒歩
			}
			strList.add(strArr);
		}
		int size = strList.size();
		if (size < 3) {
			String[] strArr1 = new String[] { "-", "-", "-", "-" };
			for (int j = 0; j < 3 - size; j++) {
				strList.add(strArr1);
			}
		}
		return strList;
	}

	private List<String[]> getHousingInspection(List<HousingInspection> housingInspectionList) {
		List<String[]> strList = new ArrayList<String[]>();
		for (int i = 0; i < housingInspectionList.size(); i++) {
			HousingInspection housingInspection = housingInspectionList.get(i);
			String[] strArr = new String[2];
			strArr[0] = lookupValueWithDefault("inspectionResult",String.valueOf(housingInspection.getInspectionValue()));
			strArr[1] = lookupValueWithDefault("inspectionLabel",String.valueOf(housingInspection.getInspectionTrust()));
			strList.add(strArr);
		}
		int size = strList.size();
		if (size < 8) {
			String[] strArr1 = new String[]{"",""};
			for (int j = 0; j < 8 - size; j++) {
				strList.add(strArr1);
			}
		}
		return strList;
	}

	/**
	 * CSVのheaderデータを生成する。<br/>
	 *
	 * @param tableName
	 * @throws IOException
	 */
	private void writeCSVHeader(String tableName, ZipOutputStream zos, List<EquipMst> equipMsts) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("物件番号" + ",");
		sb.append("物件名" + ",");
		switch (tableName) {
			case TABLE_HOUSING_NAME:
				sb.append("物件種別" + ",");
				sb.append("物件コメント" + ",");
				sb.append("売主コメント" + ",");
				sb.append("郵便番号（物件）" + ",");
				sb.append("住所1(物件) " + ",");
				sb.append("住所2（物件）" + ",");
				sb.append("沿線1" + ",");
				sb.append("駅1" + ",");
				sb.append("バス1" + ",");
				sb.append("徒歩1" + ",");
				sb.append("沿線2" + ",");
				sb.append("駅2" + ",");
				sb.append("バス2" + ",");
				sb.append("徒歩2" + ",");
				sb.append("沿線3" + ",");
				sb.append("駅3" + ",");
				sb.append("バス3" + ",");
				sb.append("徒歩3" + ",");
				sb.append("物件価格" + ",");
				sb.append("間取" + ",");
				sb.append("建物面積" + ",");
				sb.append("建物面積_補足" + ",");
				sb.append("土地面積" + ",");
				sb.append("土地面積_補足" + ",");
				sb.append("専有面積" + ",");
				sb.append("専有面積_補足" + ",");
				sb.append("築年月" + ",");
				sb.append("土地権利" + ",");
				sb.append("建物構造" + ",");
				sb.append("接道状況" + ",");
				sb.append("接道方向/幅員" + ",");
				sb.append("私道負担" + ",");
				sb.append("駐車場" + ",");
				sb.append("用途地域" + ",");
				sb.append("建ぺい率" + ",");
				sb.append("容積率" + ",");
				sb.append("現況" + ",");
				sb.append("引渡時期" + ",");
				sb.append("引渡時期コメント" + ",");
				sb.append("取引形態" + ",");
				sb.append("特記事項" + ",");
//				sb.append("備考" + ",");
				sb.append("建物階数" + ",");
				sb.append("所在階数" + ",");
				sb.append("所在階数コメント" + ",");
				sb.append("総戸数" + ",");
				sb.append("規模" + ",");
				sb.append("向き(日当たり・採光）" + ",");
				sb.append("管理費" + ",");
				sb.append("修繕積立金" + ",");
				sb.append("管理会社" + ",");
				sb.append("管理形態・方式" + ",");
				sb.append("バルコニー面積" + ",");
				sb.append("瑕疵保険" + ",");
				sb.append("担当者名" + ",");
				sb.append("会社名" + ",");
				sb.append("支店名" + ",");
				sb.append("免許番号" + ",");
				sb.append("担当者コメント" + ",");
				sb.append("インフラ" + ",");
				// 二世帯向け	床暖房	リビングダイニング15畳以上	フローリング	和室	ウォークインクローゼット	ロフト	浴室換気乾燥機	TVモニタ付きインターフォン	ウッドデッキ	ペット可	宅配ボックス	角部屋、角地
				for (int i=0; i<equipMsts.size(); i++) {
					if (i == equipMsts.size() - 1) {
						sb.append(equipMsts.get(i).getEquipName() + ",");
					} else {
						sb.append(equipMsts.get(i).getEquipName() + ",");
					}
				}
				sb.append("管理者用設備情報01" + ",");
				sb.append("管理者用設備情報02" + ",");
				sb.append("管理者用設備情報03" + ",");
				sb.append("管理者用設備情報04" + ",");
				sb.append("管理者用設備情報05" + ",");
				sb.append("管理者用設備情報06" + ",");
				sb.append("管理者用設備情報07" + ",");
				sb.append("管理者用設備情報08" + ",");
				sb.append("管理者用設備情報09" + ",");
				sb.append("管理者用設備情報10" + ",");
				sb.append("管理者用設備情報11" + ",");
				sb.append("管理者用設備情報12" + ",");
				sb.append("管理者用設備情報13" + ",");
				sb.append("管理者用設備情報14" + ",");
				sb.append("管理者用設備情報15" + ",");
				sb.append("管理者用設備情報16" + ",");
				sb.append("管理者用設備情報17" + ",");
				sb.append("管理者用設備情報18" + ",");
				sb.append("管理者用設備情報19" + ",");
				sb.append("管理者用設備情報20" + ",");
				sb.append("管理者用設備情報21" + ",");
				sb.append("管理者用設備情報22" + ",");
				sb.append("管理者用設備情報23" + ",");
				sb.append("管理者用設備情報24" + ",");
				sb.append("管理者用設備情報25" + ",");
				sb.append("管理者用設備情報26" + ",");
				sb.append("管理者用設備情報27" + ",");
				sb.append("管理者用設備情報28" + ",");
				sb.append("管理者用設備情報29" + ",");
				sb.append("管理者用設備情報30" + ",");
				// 公園	病院	保育園/幼稚園	小学校	中学校	スーパー	コンビニ	その他施設
				Iterator<String> codeLookup_Keys_Type = this.codeLookupManager.getKeysByLookup("buildingLandmark_landmarkType");
				while (codeLookup_Keys_Type.hasNext()) {
					String codeLookup_Key_Type = (String) codeLookup_Keys_Type.next();
					if (codeLookup_Keys_Type.hasNext()) {
						sb.append(lookupValueWithDefault("buildingLandmark_landmarkType", codeLookup_Key_Type) + ",");
					} else {
						sb.append(lookupValueWithDefault("buildingLandmark_landmarkType", codeLookup_Key_Type) + ",");
					}
				}
				sb.append("住宅診断実施有無" + ",");
				sb.append("レーダーチャート数値１（評価基準）" + ",");
				sb.append("レーダーチャート数値１（確認範囲）" + ",");
				sb.append("レーダーチャート数値２（評価基準）" + ",");
				sb.append("レーダーチャート数値２（確認範囲）" + ",");
				sb.append("レーダーチャート数値３（評価基準）" + ",");
				sb.append("レーダーチャート数値３（確認範囲）" + ",");
				sb.append("レーダーチャート数値４（評価基準）" + ",");
				sb.append("レーダーチャート数値４（確認範囲）" + ",");
				sb.append("レーダーチャート数値５（評価基準）" + ",");
				sb.append("レーダーチャート数値５（確認範囲）" + ",");
				sb.append("レーダーチャート数値６（評価基準）" + ",");
				sb.append("レーダーチャート数値６（確認範囲）" + ",");
				sb.append("レーダーチャート数値７（評価基準）" + ",");
				sb.append("レーダーチャート数値７（確認範囲）" + ",");
				sb.append("レーダーチャート数値８（評価基準）" + ",");
				sb.append("レーダーチャート数値８（確認範囲）" + ",");
				sb.append("リフォームプラン準備中文言" + ",");
				// 住宅診断済	瑕疵保険可能	リフォーム保険可	保障継承付 	SumStock	一体型ローン可
				Iterator<String> codeLookup_Keys = this.codeLookupManager.getKeysByLookup("recommend_point_icon");
				while (codeLookup_Keys.hasNext()) {
					String codeLookup_Key = (String) codeLookup_Keys.next();
					sb.append(lookupValueWithDefault("recommend_point_icon", codeLookup_Key) + ",");
				}
				sb.append("公開区分" + ",");
				sb.append("ステータス" + ",");
				sb.append("会員番号" + ",");
				sb.append("氏名" + ",");
				sb.append("電話番号" + ",");
				sb.append("メールアドレス" + ",");
				sb.append("備考（管理者用）" + ",");
				sb.append("新規登録日時" + ",");
				sb.append("新規登録者" + ",");
				sb.append("最終更新日時" + ",");
				sb.append("最終更新者（ID）" + ",");
				sb.append("最終更新者");

				break;
			case TABLE_REFORM_NAME:
				sb.append("リフォームプラン名" + ",");
				sb.append("価格" + ",");
				sb.append("公開区分" + ",");
				sb.append("リフォーム詳細「内容:価格」1～N" + ",");
				sb.append("セールスポイント（コンセプト）" + ",");
				sb.append("工期" + ",");
				sb.append("備考" + ",");
				sb.append("レーダーチャート数値１（リフォーム実施後）" + ",");
				sb.append("レーダーチャート数値２（リフォーム実施後）" + ",");
				sb.append("レーダーチャート数値３（リフォーム実施後）" + ",");
				sb.append("レーダーチャート数値４（リフォーム実施後）" + ",");
				sb.append("レーダーチャート数値５（リフォーム実施後）" + ",");
				sb.append("レーダーチャート数値６（リフォーム実施後）" + ",");
				sb.append("レーダーチャート数値７（リフォーム実施後）" + ",");
				sb.append("レーダーチャート数値８（リフォーム実施後）" + ",");
				// 項目追加
				sb.append("新規登録日時" + ",");
				sb.append("新規登録者" + ",");
				sb.append("最終更新日時" + ",");
				sb.append("最終更新者（ID）" + ",");
				sb.append("最終更新者");
				break;
			default :
				break;
		}
		sb.append("\r\n");
		zipWriter(sb.toString(), zos);
	}

	class HousingListRowCallbackHandler implements RowCallbackHandler {
		PanaHousingPartThumbnailProxy panaHousingManager;
		List<PanaHousing> panaHousings;
		List<AdminLoginInfo> adminLoginInfo_InUsers;
		List<MemberInfo> memberInfos;

		public HousingListRowCallbackHandler(PanaHousingPartThumbnailProxy panaHousingManager, List<MemberInfo> memberInfos,
				List<AdminLoginInfo> adminLoginInfo_InUsers, List<PanaHousing> panaHousings) {
			this.panaHousingManager = panaHousingManager;
			this.panaHousings = panaHousings;
			this.adminLoginInfo_InUsers = adminLoginInfo_InUsers;
			this.memberInfos = memberInfos;
		}

		@Override
		public void processRow(ResultSet rs) throws SQLException {
			try {
				PanaHousing panaHousing = (PanaHousing) this.panaHousingManager.searchHousingPk(rs.getString("housingInfo_sysHousingCd"), true);

				this.panaHousings.add(panaHousing);

				// 管理者ログインＩＤ情報
				AdminLoginInfo adminLoginInfo_InUser = new AdminLoginInfo();
				// 管理者ユーザーID
				adminLoginInfo_InUser.setAdminUserId(rs.getString("adminLoginInfo1_adminUserId"));
				// ユーザー名称
				adminLoginInfo_InUser.setUserName(rs.getString("adminLoginInfo1_userName"));

				this.adminLoginInfo_InUsers.add(adminLoginInfo_InUser);

				// マイページ会員情報
				MemberInfo memberInfo = new MemberInfo();
				// 会員名（姓）
				memberInfo.setMemberLname(rs.getString("memberInfo_memberLname"));
				// 会員名（名）
				memberInfo.setMemberFname(rs.getString("memberInfo_memberFname"));
				// メールアドレス
				memberInfo.setEmail(rs.getString("memberInfo_email"));
				// 電話番号
				memberInfo.setTel(rs.getString("memberInfo_TEL"));

				this.memberInfos.add(memberInfo);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 物件検索のSQL文を生成する。<br/>
	 * <br/>
	 *
	 * @param sbSql
	 *            生成したSQL文を追加するバッファ
	 * @param params
	 *            SQLで使用するバインドパラメータのリストオブジェクト
	 * @param searchForm
	 *            検索条件の格納オブジェクト
	 */
	protected void createSql(PanaHousingSearchForm searchForm, StringBuffer sbSql, List<Object> params) {
		createSelect(sbSql, params);
		createFrom(sbSql, params);
		createWhere(searchForm, sbSql, params);
		createOrderBy(sbSql, searchForm.getKeyOrderType());
	}

	/**
	 * Select句文字列を生成する<br/>
	 * 検索結果として必要な項目が増える場合は、修正を行うこと<br/>
	 * （同時にRowMapperクラスの修正も必要）<b/>
	 * 何れもRowMapperクラスで参照するキーとなることから、別名定義してアクセスしやすくすること。
	 * ・テーブル名とカラム名のキャメルケースを「_」（アンダーバー）で結合<br/>
	 * ・集合関数については関数の名称を含めて命名（例：maxImageCnt）<br/>
	 * <br/>
	 *
	 * @param sbSql
	 *            生成したSQL文を追加するバッファ
	 * @param params
	 *            SQLで使用するバインドパラメータのリストオブジェクト
	 */
	protected void createSelect(StringBuffer sbSql, List<Object> params) {

		StringBuffer sbSelect = new StringBuffer();

		sbSelect.append(" SELECT distinct");
		// 物件情報
		// システム物件CD
		sbSelect.append(" " + TABLE_HOUSING_INFO_ALIAS + ".sys_housing_cd AS " + TABLE_HOUSING_INFO_ALIAS + "_sysHousingCd");
		// 物件番号
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".housing_cd AS " + TABLE_HOUSING_INFO_ALIAS + "_housingCd");
		// 表示用物件名
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".display_housing_name AS " + TABLE_HOUSING_INFO_ALIAS + "_displayHousingName");
		// 表示用物件名ふりがな
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".display_housing_name_kana AS " + TABLE_HOUSING_INFO_ALIAS + "_displayHousingNameKana");
		// 部屋番号
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".room_no AS " + TABLE_HOUSING_INFO_ALIAS + "_roomNo");
		// システム建物CD
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".sys_building_cd AS " + TABLE_HOUSING_INFO_ALIAS + "_sysBuildingCd");
		// 賃料/価格
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".price AS " + TABLE_HOUSING_INFO_ALIAS + "_price");
		// リフォーム込価格（最小）
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".price_full_min AS " + TABLE_HOUSING_INFO_ALIAS + "_priceFullMin");
		// リフォーム込価格（最大）
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".price_full_max AS " + TABLE_HOUSING_INFO_ALIAS + "_priceFullMax");
		// 管理費
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".upkeep AS " + TABLE_HOUSING_INFO_ALIAS + "_upkeep");
		// 共益費
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".common_area_fee AS " + TABLE_HOUSING_INFO_ALIAS + "_commonAreaFee");
		// 修繕積立費
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".mente_fee AS " + TABLE_HOUSING_INFO_ALIAS + "_menteFee");
		// 敷金
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".sec_deposit AS " + TABLE_HOUSING_INFO_ALIAS + "_secDeposit");
		// 敷金単位
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".sec_deposit_crs AS " + TABLE_HOUSING_INFO_ALIAS + "_secDepositCrs");
		// 保証金
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".bond_chrg AS " + TABLE_HOUSING_INFO_ALIAS + "_bondChrg");
		// 保証金単位
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".bond_chrg_crs AS " + TABLE_HOUSING_INFO_ALIAS + "_bondChrgCrs");
		// 敷引礼金区分
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".deposit_div AS " + TABLE_HOUSING_INFO_ALIAS + "_depositDiv");
		// 敷引礼金額
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".deposit AS " + TABLE_HOUSING_INFO_ALIAS + "_deposit");
		// 敷引礼金単位
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".deposit_crs AS " + TABLE_HOUSING_INFO_ALIAS + "_depositCrs");
		// 間取CD
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".layout_cd AS " + TABLE_HOUSING_INFO_ALIAS + "_layoutCd");
		// 間取詳細コメント
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".layout_comment AS " + TABLE_HOUSING_INFO_ALIAS + "_layoutComment");
		// 物件の階数
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".floor_no AS " + TABLE_HOUSING_INFO_ALIAS + "_floorNo");
		// 物件の階数コメント
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".floor_no_note AS " + TABLE_HOUSING_INFO_ALIAS + "_floorNoNote");
		// 土地面積
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".land_area AS " + TABLE_HOUSING_INFO_ALIAS + "_landArea");
		// 土地面積_補足
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".land_area_memo AS " + TABLE_HOUSING_INFO_ALIAS + "_landAreaMemo");
		// 専有面積
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".personal_area AS " + TABLE_HOUSING_INFO_ALIAS + "_personalArea");
		// 専有面積_補足
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".personal_area_memo AS " + TABLE_HOUSING_INFO_ALIAS + "_personalAreaMemo");
		// 入居状態フラグ
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".movein_flg AS " + TABLE_HOUSING_INFO_ALIAS + "_moveinFlg");
		// 駐車場の状況
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".parking_situation AS " + TABLE_HOUSING_INFO_ALIAS + "_parkingSituation");
		// 駐車場空の有無
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".parking_emp_exist AS " + TABLE_HOUSING_INFO_ALIAS + "_parkingEmpExist");
		// 表示用駐車場情報
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".display_parking_info AS " + TABLE_HOUSING_INFO_ALIAS + "_displayParkingInfo");
		// 窓の向き
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".window_direction AS " + TABLE_HOUSING_INFO_ALIAS + "_windowDirection");
		// アイコン情報
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".icon_cd AS " + TABLE_HOUSING_INFO_ALIAS + "_iconCd");
		// 基本情報コメント
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".basic_comment AS " + TABLE_HOUSING_INFO_ALIAS + "_basicComment");
		// リフォーム準備中コメント
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".reform_comment AS " + TABLE_HOUSING_INFO_ALIAS + "_reformComment");
		// 最小徒歩時間
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".min_walking_time AS " + TABLE_HOUSING_INFO_ALIAS + "_minWalkingTime");
		// 登録日
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".ins_date AS " + TABLE_HOUSING_INFO_ALIAS + "_insDate");
		// 登録者
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".ins_user_id AS " + TABLE_HOUSING_INFO_ALIAS + "_insUserId");
		// 最終更新日
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".upd_date AS " + TABLE_HOUSING_INFO_ALIAS + "_updDate");
		// 最終更新者
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".upd_user_id AS " + TABLE_HOUSING_INFO_ALIAS + "_updUserId");

		// 物件詳細情報
		// システム物件CD
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".sys_housing_cd AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_sysHousingCd");
		// 用途地域CD
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".used_area_cd AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_usedAreaCd");
		// 取引形態区分
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".transact_type_div AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_transactTypeDiv");
		// 表示用契約期間
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".display_contract_term AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_displayContractTerm");
		// 土地権利
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".land_right AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_landRight");
		// 入居可能時期フラグ
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".movein_timing AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_moveinTiming");
		// 入居可能時期
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".movein_timing_day AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_moveinTimingDay");
		// 入居可能時期コメント
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".movein_note AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_moveinNote");
		// 表示用入居可能時期
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".display_movein_timing AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_displayMoveinTiming");
		// 表示用入居諸条件
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".display_movein_proviso AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_displayMoveinProviso");
		// 管理形態・方式
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".upkeep_type AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_upkeepType");
		// 管理会社
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".upkeep_corp AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_upkeepCorp");
		// 更新料
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".renew_chrg AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_renewChrg");
		// 更新料単位
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".renew_chrg_crs AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_renewChrgCrs");
		// 更新料名
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".renew_chrg_name AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_renewChrgName");
		// 更新手数料
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".renew_due AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_renewDue");
		// 更新手数料単位
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".renew_due_crs AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_renewDueCrs");
		// 仲介手数料
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".brokerage_chrg AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_brokerageChrg");
		// 仲介手数料単位
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".brokerage_chrg_crs AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_brokerageChrgCrs");
		// 鍵交換料
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".change_key_chrg AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_changeKeyChrg");
		// 損保有無
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".insur_exist AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_insurExist");
		// 損保料金
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".insur_chrg AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_insurChrg");
		// 損保年数
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".insur_term AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_insurTerm");
		// 損保認定ランク
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".insur_lank AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_insurLank");
		// 諸費用
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".other_chrg AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_otherChrg");
		// 接道状況
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".contact_road AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_contactRoad");
		// 接道方向/幅員
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".contact_road_dir AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_contactRoadDir");
		// 私道負担
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".private_road AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_privateRoad");
		// バルコニー面積
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".balcony_area AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_balconyArea");
		// 特記事項
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".special_instruction AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_specialInstruction");
		// 詳細コメント
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".dtl_comment AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_dtlComment");

		// 物件ステータス情報
		// システム物件CD
		sbSelect.append(", " + TABLE_HOUSING_STATUS_INFO_ALIAS + ".sys_housing_cd AS " + TABLE_HOUSING_STATUS_INFO_ALIAS + "_sysHousingCd");
		// 非公開フラグ
		sbSelect.append(", " + TABLE_HOUSING_STATUS_INFO_ALIAS + ".hidden_flg AS " + TABLE_HOUSING_STATUS_INFO_ALIAS + "_hiddenFlg");
		// ステータスCD
		sbSelect.append(", " + TABLE_HOUSING_STATUS_INFO_ALIAS + ".status_cd AS " + TABLE_HOUSING_STATUS_INFO_ALIAS + "_statusCd");
		// ユーザーID
		sbSelect.append(", " + TABLE_HOUSING_STATUS_INFO_ALIAS + ".user_id AS " + TABLE_HOUSING_STATUS_INFO_ALIAS + "_userId");
		// 備考
		sbSelect.append(", " + TABLE_HOUSING_STATUS_INFO_ALIAS + ".note AS " + TABLE_HOUSING_STATUS_INFO_ALIAS + "_note");

		// マイページ会員情報
		// ユーザーID
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".user_id AS " + TABLE_MEMBER_INFO_ALIAS + "_userId");
		// 会員名（姓）
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".member_lname AS " + TABLE_MEMBER_INFO_ALIAS + "_memberLname");
		// 会員名（名）
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".member_fname AS " + TABLE_MEMBER_INFO_ALIAS + "_memberFname");
		// 会員名・カナ（姓）
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".member_lname_kana AS " + TABLE_MEMBER_INFO_ALIAS + "_memberLnameKana");
		// 会員名・カナ（名）
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".member_fname_kana AS " + TABLE_MEMBER_INFO_ALIAS + "_memberFnameKana");
		// メールアドレス
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".email AS " + TABLE_MEMBER_INFO_ALIAS + "_email");
		// 自動ログイン用トークン
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".cookie_login_token AS " + TABLE_MEMBER_INFO_ALIAS + "_cookieLoginToken");
		// 住所・郵便番号
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".zip AS " + TABLE_MEMBER_INFO_ALIAS + "_zip");
		// 住所・都道府県CD
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".pref_cd AS " + TABLE_MEMBER_INFO_ALIAS + "_pref_cd");
		// 住所・区町村番地
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".address AS " + TABLE_MEMBER_INFO_ALIAS + "_address");
		// 住所・建物名
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".address_other AS " + TABLE_MEMBER_INFO_ALIAS + "_address_other");
		// 電話番号
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".TEL AS " + TABLE_MEMBER_INFO_ALIAS + "_TEL");
		// FAX
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".FAX AS " + TABLE_MEMBER_INFO_ALIAS + "_FAX");
		// パスワード
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".password AS " + TABLE_MEMBER_INFO_ALIAS + "_password");
		// メール配信希望
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".mail_send_flg AS " + TABLE_MEMBER_INFO_ALIAS + "_mailSendFlg");
		// 居住状態
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".resident_flg AS " + TABLE_MEMBER_INFO_ALIAS + "_residentFlg");
		// 希望地域・都道府県
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".hope_pref_cd AS " + TABLE_MEMBER_INFO_ALIAS + "_hopePrefCd");
		// 希望地域・市区町村
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".hope_address AS " + TABLE_MEMBER_INFO_ALIAS + "_hopeAddress");
		// 登録経路
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".entry_route AS " + TABLE_MEMBER_INFO_ALIAS + "_entryRoute");
		// プロモCD
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".promo_cd AS " + TABLE_MEMBER_INFO_ALIAS + "_promoCd");
		// 流入元CD
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".ref_cd AS " + TABLE_MEMBER_INFO_ALIAS + "_refCd");
		// ログイン失敗回数
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".fail_cnt AS " + TABLE_MEMBER_INFO_ALIAS + "_failCnt");
		// 最終ログイン失敗日
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".last_fail_date AS " + TABLE_MEMBER_INFO_ALIAS + "_lastFailDate");
		// ロックフラグ
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".lock_flg AS " + TABLE_MEMBER_INFO_ALIAS + "_lockFlg");
		// 前回ログイン日
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".pre_login AS " + TABLE_MEMBER_INFO_ALIAS + "_preLogin");
		// 最終ログイン日
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".last_login AS " + TABLE_MEMBER_INFO_ALIAS + "_lastLogin");
		// 登録日
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".ins_date AS " + TABLE_MEMBER_INFO_ALIAS + "_insDate");
		// 登録者
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".ins_user_id AS " + TABLE_MEMBER_INFO_ALIAS + "_insUserId");
		// 最終更新日
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".upd_date AS " + TABLE_MEMBER_INFO_ALIAS + "_updDate");
		// 最終更新者
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".upd_user_id AS " + TABLE_MEMBER_INFO_ALIAS + "_updUserId");

		// 建物基本情報
		// システム建物CD
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".sys_building_cd AS " + TABLE_BUILDING_INFO_ALIAS + "_sysBuildingCd");
		// 建物番号
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".building_cd AS " + TABLE_BUILDING_INFO_ALIAS + "_buildingCd");
		// 物件種類CD
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".housing_kind_cd AS " + TABLE_BUILDING_INFO_ALIAS + "_housingKindCd");
		// 建物構造CD
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".struct_cd AS " + TABLE_BUILDING_INFO_ALIAS + "_structCd");
		// 表示用建物名
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".display_building_name AS " + TABLE_BUILDING_INFO_ALIAS + "_displayBuildingName");
		// 表示用建物名ふりがな
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".display_building_name_kana AS " + TABLE_BUILDING_INFO_ALIAS + "_displayBuildingNameKana");
		// 所在地・郵便番号
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".zip AS " + TABLE_BUILDING_INFO_ALIAS + "_zip");
		// 所在地・都道府県CD
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".pref_cd AS " + TABLE_BUILDING_INFO_ALIAS + "_prefCd");
		// 所在地・市区町村CD
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".address_cd AS " + TABLE_BUILDING_INFO_ALIAS + "_addressCd");
		// 所在地・市区町村名
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".address_name AS " + TABLE_BUILDING_INFO_ALIAS + "_addressName");
		// 所在地・町名番地
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".address_other1 AS " + TABLE_BUILDING_INFO_ALIAS + "_addressOther1");
		// 所在地・建物名その他
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".address_other2 AS " + TABLE_BUILDING_INFO_ALIAS + "_addressOther2");
		// 竣工年月
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".comp_date AS " + TABLE_BUILDING_INFO_ALIAS + "_compDate");
		// 竣工旬
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".comp_ten_days AS " + TABLE_BUILDING_INFO_ALIAS + "_compTenDays");
		// 総階数
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".total_floors AS " + TABLE_BUILDING_INFO_ALIAS + "_totalFloors");
		// 登録日
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".ins_date AS " + TABLE_BUILDING_INFO_ALIAS + "_insDate");
		// 登録者
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".ins_user_id AS " + TABLE_BUILDING_INFO_ALIAS + "_insUserId");
		// 最終更新日
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".upd_date AS " + TABLE_BUILDING_INFO_ALIAS + "_updDate");
		// 最終更新者
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".upd_user_id AS " + TABLE_BUILDING_INFO_ALIAS + "_updUserId");

		// 建物詳細情報
		// システム建物CD
		sbSelect.append(", " + TABLE_BUILDING_DTL_INFO_ALIAS + ".sys_building_cd AS " + TABLE_BUILDING_DTL_INFO_ALIAS + "_sysBuildingCd");
		// 建物面積
		sbSelect.append(", " + TABLE_BUILDING_DTL_INFO_ALIAS + ".building_area AS " + TABLE_BUILDING_DTL_INFO_ALIAS + "_buildingArea");
		// 建物面積_補足
		sbSelect.append(", " + TABLE_BUILDING_DTL_INFO_ALIAS + ".building_area_memo AS " + TABLE_BUILDING_DTL_INFO_ALIAS + "_buildingAreaMemo");
		// 建ぺい率
		sbSelect.append(", " + TABLE_BUILDING_DTL_INFO_ALIAS + ".coverage AS " + TABLE_BUILDING_DTL_INFO_ALIAS + "_coverage");
		// 建ぺい率_補足
		sbSelect.append(", " + TABLE_BUILDING_DTL_INFO_ALIAS + ".coverage_memo AS " + TABLE_BUILDING_DTL_INFO_ALIAS + "_coverageMemo");
		// 容積率
		sbSelect.append(", " + TABLE_BUILDING_DTL_INFO_ALIAS + ".building_rate AS " + TABLE_BUILDING_DTL_INFO_ALIAS + "_buildingRate");
		// 容積率_補足
		sbSelect.append(", " + TABLE_BUILDING_DTL_INFO_ALIAS + ".building_rate_memo AS " + TABLE_BUILDING_DTL_INFO_ALIAS + "_buildingRateMemo");
		// 総戸数
		sbSelect.append(", " + TABLE_BUILDING_DTL_INFO_ALIAS + ".total_house_cnt AS " + TABLE_BUILDING_DTL_INFO_ALIAS + "_totalHouseCnt");
		// 賃貸戸数
		sbSelect.append(", " + TABLE_BUILDING_DTL_INFO_ALIAS + ".lease_house_cnt AS " + TABLE_BUILDING_DTL_INFO_ALIAS + "_leaseHouseCnt");
		// 建物緯度
		sbSelect.append(", " + TABLE_BUILDING_DTL_INFO_ALIAS + ".building_latitude AS " + TABLE_BUILDING_DTL_INFO_ALIAS + "_buildingLatitude");
		// 建物経度
		sbSelect.append(", " + TABLE_BUILDING_DTL_INFO_ALIAS + ".building_longitude AS " + TABLE_BUILDING_DTL_INFO_ALIAS + "_buildingLongitude");

		// 都道府県マスタ
		// 都道府県CD
		sbSelect.append(", " + TABLE_PREF_MST_ALIAS + ".pref_cd AS " + TABLE_PREF_MST_ALIAS + "_prefCd");
		// 都道府県名
		sbSelect.append(", " + TABLE_PREF_MST_ALIAS + ".pref_name AS " + TABLE_PREF_MST_ALIAS + "_prefName");
		// 都道府県名ローマ字
		sbSelect.append(", " + TABLE_PREF_MST_ALIAS + ".r_pref_name AS " + TABLE_PREF_MST_ALIAS + "_rPrefName");
		// 地域CD
		sbSelect.append(", " + TABLE_PREF_MST_ALIAS + ".area_cd AS " + TABLE_PREF_MST_ALIAS + "_areaCd");
		// 表示順
		sbSelect.append(", " + TABLE_PREF_MST_ALIAS + ".sort_order AS " + TABLE_PREF_MST_ALIAS + "_sortOrder");
		// 登録日
		sbSelect.append(", " + TABLE_PREF_MST_ALIAS + ".ins_date AS " + TABLE_PREF_MST_ALIAS + "_insDate");
		// 登録者
		sbSelect.append(", " + TABLE_PREF_MST_ALIAS + ".ins_user_id AS " + TABLE_PREF_MST_ALIAS + "_insUserId");
		// 最終更新日
		sbSelect.append(", " + TABLE_PREF_MST_ALIAS + ".upd_date AS " + TABLE_PREF_MST_ALIAS + "_updDate");
		// 最終更新者
		sbSelect.append(", " + TABLE_PREF_MST_ALIAS + ".upd_user_id AS " + TABLE_PREF_MST_ALIAS + "_updUserId");

		// 管理者ログインＩＤ情報
		// 管理者ユーザーID
		sbSelect.append(", " + TABLE_ADMIN_LOGIN_INFO1_ALIAS+ ".admin_user_id AS " + TABLE_ADMIN_LOGIN_INFO1_ALIAS + "_adminUserId");
		// ユーザー名称
		sbSelect.append(", " + TABLE_ADMIN_LOGIN_INFO1_ALIAS + ".user_name AS " + TABLE_ADMIN_LOGIN_INFO1_ALIAS + "_userName");

		// 管理者ログインＩＤ情報
		// 管理者ユーザーID
		sbSelect.append(", " + TABLE_ADMIN_LOGIN_INFO2_ALIAS + ".admin_user_id AS " + TABLE_ADMIN_LOGIN_INFO2_ALIAS + "_adminUserId");
		// ユーザー名称
		sbSelect.append(", " + TABLE_ADMIN_LOGIN_INFO2_ALIAS + ".user_name AS " + TABLE_ADMIN_LOGIN_INFO2_ALIAS + "_userName");

		sbSql.append(sbSelect.toString());

	}

	/**
	 * From句の文字列を生成する<br/>
	 * 使用するテーブル名、別名は定数を定義して記述すること<br/>
	 * （コーディング簡素化、タイプミス防止）<br/>
	 * <br/>
	 *
	 * @param sbSql
	 *            生成したSQL文を追加するバッファ
	 * @param params
	 *            SQLで使用するバインドパラメータのリストオブジェクト
	 */
	protected void createFrom(StringBuffer sbSql, List<Object> params) {

		StringBuffer sbFrom = new StringBuffer();

		sbFrom.append(" FROM ");
		sbFrom.append(" " + TABLE_HOUSING_INFO_NAME + " " + TABLE_HOUSING_INFO_ALIAS);
		sbFrom.append(" LEFT OUTER JOIN " + TABLE_HOUSING_STATUS_INFO_NAME + " " + TABLE_HOUSING_STATUS_INFO_ALIAS + " ON " + TABLE_HOUSING_INFO_ALIAS + ".sys_housing_cd = " + TABLE_HOUSING_STATUS_INFO_ALIAS + ".sys_housing_cd");
		sbFrom.append(" INNER JOIN " + TABLE_BUILDING_INFO_NAME + " " + TABLE_BUILDING_INFO_ALIAS + " ON " + TABLE_HOUSING_INFO_ALIAS + ".sys_building_cd = " + TABLE_BUILDING_INFO_ALIAS + ".sys_building_cd");
		sbFrom.append(" LEFT OUTER JOIN " + TABLE_BUILDING_DTL_INFO_NAME + " " + TABLE_BUILDING_DTL_INFO_ALIAS + " ON " + TABLE_HOUSING_INFO_ALIAS + ".sys_building_cd = " + TABLE_BUILDING_DTL_INFO_ALIAS + ".sys_building_cd");
		sbFrom.append(" LEFT OUTER JOIN " + TABLE_PREF_MST_NAME + " " + TABLE_PREF_MST_ALIAS + " ON " + TABLE_BUILDING_INFO_ALIAS + ".pref_cd = " + TABLE_PREF_MST_ALIAS + ".pref_cd");
		sbFrom.append(" LEFT OUTER JOIN " + TABLE_HOUSING_DTL_INFO_NAME + " " + TABLE_HOUSING_DTL_INFO_ALIAS + " ON " + TABLE_HOUSING_INFO_ALIAS + ".sys_housing_cd = " + TABLE_HOUSING_DTL_INFO_ALIAS + ".sys_housing_cd");
		sbFrom.append(" LEFT OUTER JOIN " + TABLE_MEMBER_INFO_NAME + " " + TABLE_MEMBER_INFO_ALIAS + " ON " + TABLE_HOUSING_STATUS_INFO_ALIAS + ".user_id = " + TABLE_MEMBER_INFO_ALIAS + ".user_id");
		sbFrom.append(" LEFT OUTER JOIN " + TABLE_ADMIN_LOGIN_INFO_NAME + " " + TABLE_ADMIN_LOGIN_INFO1_ALIAS + " ON " + TABLE_HOUSING_INFO_ALIAS + ".ins_user_id = " + TABLE_ADMIN_LOGIN_INFO1_ALIAS + ".admin_user_id");
		sbFrom.append(" LEFT OUTER JOIN " + TABLE_ADMIN_LOGIN_INFO_NAME + " " + TABLE_ADMIN_LOGIN_INFO2_ALIAS + " ON " + TABLE_HOUSING_INFO_ALIAS + ".upd_user_id = " + TABLE_ADMIN_LOGIN_INFO2_ALIAS + ".admin_user_id");
//		sbFrom.append(" LEFT OUTER JOIN " + TABLE_REFORM_PLAN_NAME + " " + TABLE_REFORM_PLAN_ALIAS + " ON " + TABLE_HOUSING_INFO_ALIAS + ".sys_housing_cd = " + TABLE_REFORM_PLAN_ALIAS + ".sys_housing_cd");

		sbSql.append(sbFrom.toString());

	}

	/**
	 * Where句の文字列を生成する<br/>
	 * 検索条件ごとに、「AND～」となる文字列を生成するメソッドを呼び出している<br/>
	 * <br/>
	 *
	 * @param sbSql
	 *            生成したSQL文を追加するバッファ
	 * @param params
	 *            SQLで使用するバインドパラメータのリストオブジェクト
	 * @param searchForm
	 *            検索条件の格納オブジェクト
	 */
	protected void createWhere(PanaHousingSearchForm searchForm,
			StringBuffer sbSql, List<Object> params) {

		StringBuffer sbWhere = new StringBuffer();

		// 物件番号
		createWhereAndHousingCd(sbWhere, params, searchForm.getKeyHousingCd());
		// 物件名
		createWhereAndDisplayHousingName(sbWhere, params,
				searchForm.getKeyDisplayHousingName());
		// 都道府県CD
		createWhereAndPrefCd(sbWhere, params, searchForm.getKeyPrefCd());

		// 登録開始日の検索条件文字列を追加する
		createWhereAndInsDateStart(sbWhere, params,
				((PanaHousingSearchForm) searchForm).getKeyInsDateStart());
		// 登録終了日の検索条件文字列を追加する
		createWhereAndInsDateEnd(sbWhere, params,
				((PanaHousingSearchForm) searchForm).getKeyInsDateEnd());
		// 更新日時の検索条件文字列を追加する
		createWhereAndUpdDate(sbWhere, params,
				((PanaHousingSearchForm) searchForm).getKeyUpdDate());
		// 会員番号の検索条件文字列を追加する
		createWhereAndUserId(sbWhere, params,
				((PanaHousingSearchForm) searchForm).getKeyUserId());
		// 公開区分の検索条件文字列を追加する
		createWhereAndHiddenFlg(sbWhere, params,
				((PanaHousingSearchForm) searchForm).getKeyHiddenFlg());
		// ステータスの検索条件文字列を追加する
		createWhereAndStatusCd(sbWhere, params,
				((PanaHousingSearchForm) searchForm).getKeyStatusCd());

		// 物件種類
		createWhereAndHousingKindCd(sbWhere, params,
				searchForm.getKeyHousingKindCd());
		// 市区町村CD
		createWhereAndAddressCd(sbWhere, params, searchForm.getKeyAddressCd());
		// 沿線CD、駅CD
		createWhereAndRouteCdStationCd(sbWhere, params,
				searchForm.getKeyRouteCd(), searchForm.getKeyStationCd(),
				searchForm.getKeyPartSrchCd());
		// 土地面積
		createWhereAndLandAreaLower(sbWhere, params,
				searchForm.getKeyLandAreaLower());
		createWhereAndLandAreaUpper(sbWhere, params,
				searchForm.getKeyLandAreaUpper());
		// 専有面積
		createWhereAndPersonalAreaLower(sbWhere, params,
				searchForm.getKeyPersonalAreaLower());
		createWhereAndPersonalAreaUpper(sbWhere, params,
				searchForm.getKeyPersonalAreaUpper());
		// 間取りCD
		createWhereAndLayoutCd(sbWhere, params, searchForm.getKeyLayoutCd());
		// 建物面積
		createWhereAndBuildingAreaLower(sbWhere, params,
				searchForm.getKeyBuildingAreaLower());
		createWhereAndBuildingAreaUpper(sbWhere, params,
				searchForm.getKeyBuildingAreaUpper());

		PanaHousingSearchForm searchFormChild = (PanaHousingSearchForm) searchForm;

		// 「リフォーム価格込みで検索する」の状態の判断フラグ
		boolean priceFlg = false;

		if ("on".equals(searchFormChild.getReformPriceCheck())) {
			priceFlg = true;
		}

		// 予算
		createWhereAndPrice(sbWhere, params, searchForm.getKeyPriceLower(),
				searchForm.getKeyPriceUpper(), priceFlg);

		// 築年月
		createWhereAndCompDate(sbWhere, params,
				searchFormChild.getKeyCompDate());

		// おすすめのポイント
		createWhereAndIconCd(sbWhere, params, searchFormChild.getKeyIconCd());

		// 引渡時期
		createWhereAndMovein(sbWhere, params, searchFormChild.getMoveinTiming());

		// こだわり条件
		createWhereAndPartSrchCd(sbWhere, params, searchForm);
//		createWhereAndReformType(sbWhere, params, searchForm.getKeyReformType());

		// 最初の「AND」を削除して「 WHERE 」を付ける
		if (sbWhere.length() > 0) {
			String strAnd = "AND";
			sbWhere.delete(sbWhere.indexOf(strAnd), sbWhere.indexOf(strAnd)
					+ strAnd.length());
			sbWhere.insert(0, " WHERE ");
		}

		sbSql.append(sbWhere.toString());

	}

//	/**
//	 * 
//	 * @param sbWhere
//	 * @param params
//	 * @param reformType
//	 */
//	protected void createWhereAndReformType(StringBuffer sbWhere,
//            List<Object> params, String reformType) {
//	    if (!StringValidateUtil.isEmpty(reformType)) {
//            sbWhere.append(" AND " + TABLE_REFORM_PLAN_ALIAS
//                    + ".type = ? ");
//            params.add(reformType);
//        }
//	}
	/**
	 * 物件番号の検索条件文字列を追加する<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            生成したSQL文を追加するバッファ
	 * @param params
	 *            SQLで使用するバインドパラメータのリストオブジェクト
	 * @param csHousingCd
	 *            検索対象とする物件番号（カンマ区切りで複数の受け取りを想定）
	 */
	protected void createWhereAndHousingCd(StringBuffer sbWhere,
			List<Object> params, String csHousingCd) {

		if (!StringValidateUtil.isEmpty(csHousingCd)) {

			StringBuffer sqlIn = new StringBuffer();
			String[] arrHousingCd = csHousingCd.split(",");
			for (int i = 0; i < arrHousingCd.length; i++) {
				if (arrHousingCd[i].trim().length() > 0) {
					sqlIn.append(",? ");
					params.add(arrHousingCd[i]);
				}
			}
			if (sqlIn.length() > 0) {
				// 最初のカンマを削除する
				String strComma = ",";
				sqlIn.delete(sqlIn.indexOf(strComma), sqlIn.indexOf(strComma)
						+ strComma.length());
				sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
						+ ".housing_cd IN (" + sqlIn.toString() + ")");
			}
		}

	}

	/**
	 * 表示用物件名の検索条件文字列を追加する<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            生成したSQL文を追加するバッファ
	 * @param params
	 *            SQLで使用するバインドパラメータのリストオブジェクト
	 * @param displayHousingName
	 *            検索対象とする表示用物件名（部分一致）
	 */
	protected void createWhereAndDisplayHousingName(StringBuffer sbWhere,
			List<Object> params, String displayHousingName) {

		if (!StringValidateUtil.isEmpty(displayHousingName)) {
			sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
					+ ".display_housing_name LIKE ? ");
			params.add("%" + displayHousingName + "%");
		}

	}

	/**
	 * 都道府県CDの検索条件文字列を追加する<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            生成したSQL文を追加するバッファ
	 * @param params
	 *            SQLで使用するバインドパラメータのリストオブジェクト
	 * @param csPrefCd
	 *            検索対象とする都道府県CDのパラメタ（カンマ区切りで複数の受け取りを想定）
	 */
	protected void createWhereAndPrefCd(StringBuffer sbWhere,
			List<Object> params, String csPrefCd) {

		if (!StringValidateUtil.isEmpty(csPrefCd)) {

			StringBuffer sqlIn = new StringBuffer();
			String[] arrPrefCd = csPrefCd.split(",");
			for (int i = 0; i < arrPrefCd.length; i++) {
				if (arrPrefCd[i].trim().length() > 0) {
					sqlIn.append(",? ");
					params.add(arrPrefCd[i]);
				}
			}
			if (sqlIn.length() > 0) {
				// 最初のカンマを削除する
				String strComma = ",";
				sqlIn.delete(sqlIn.indexOf(strComma), sqlIn.indexOf(strComma)
						+ strComma.length());
				sbWhere.append(" AND " + TABLE_BUILDING_INFO_ALIAS
						+ ".pref_cd IN (" + sqlIn.toString() + ")");
			}
		}

	}

	/**
	 * 登録開始日の検索条件文字列を追加する<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            生成したSQL文を追加するバッファ
	 * @param params
	 *            SQLで使用するバインドパラメータのリストオブジェクト
	 * @param csInsDateStart
	 *            検索対象とする登録開始日
	 */
	protected void createWhereAndInsDateStart(StringBuffer sbWhere,
			List<Object> params, String csInsDateStart) {

		if (!StringValidateUtil.isEmpty(csInsDateStart)) {
			sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
					+ ".ins_date >= ? ");
			params.add(csInsDateStart + " 00:00:00");
		}

	}

	/**
	 * 登録終了日の検索条件文字列を追加する<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            生成したSQL文を追加するバッファ
	 * @param params
	 *            SQLで使用するバインドパラメータのリストオブジェクト
	 * @param csInsDateEnd
	 *            検索対象とする登録終了日
	 */
	protected void createWhereAndInsDateEnd(StringBuffer sbWhere,
			List<Object> params, String csInsDateEnd) {

		if (!StringValidateUtil.isEmpty(csInsDateEnd)) {
			sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
					+ ".ins_date <= ? ");
			params.add(csInsDateEnd + " 23:59:59");
		}
	}

	/**
	 * 更新日時の検索条件文字列を追加する<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            生成したSQL文を追加するバッファ
	 * @param params
	 *            SQLで使用するバインドパラメータのリストオブジェクト
	 * @param csUpdDate
	 *            検索対象とする更新日時
	 */
	protected void createWhereAndUpdDate(StringBuffer sbWhere,
			List<Object> params, String csUpdDate) {

		if (!StringValidateUtil.isEmpty(csUpdDate)) {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy/MM/dd HH:mm:ss");
			if ("02".equals(csUpdDate)) {
				sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
						+ ".upd_date <= ? ");
				params.add(format.format(cal.getTime()));
				cal.add(Calendar.DATE, -7);
				sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
						+ ".upd_date >= ? ");
				params.add(format.format(cal.getTime()));
			} else if ("03".equals(csUpdDate)) {
				cal.add(Calendar.DATE, -7);
				sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
						+ ".upd_date <= ? ");
				params.add(format.format(cal.getTime()));
				cal.add(Calendar.DATE, -7);
				sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
						+ ".upd_date >= ? ");
				params.add(format.format(cal.getTime()));
			} else if ("04".equals(csUpdDate)) {
				cal.add(Calendar.DATE, -14);
				sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
						+ ".upd_date <= ? ");
				params.add(format.format(cal.getTime()));
			}
		}
	}

	/**
	 * 会員番号の検索条件文字列を追加する<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            生成したSQL文を追加するバッファ
	 * @param params
	 *            SQLで使用するバインドパラメータのリストオブジェクト
	 * @param csUserId
	 *            検索対象とする会員番号
	 */
	protected void createWhereAndUserId(StringBuffer sbWhere,
			List<Object> params, String csUserId) {

		if (!StringValidateUtil.isEmpty(csUserId)) {
			sbWhere.append(" AND " + TABLE_HOUSING_STATUS_INFO_ALIAS
					+ ".user_id LIKE ? ");
			params.add("%" + csUserId + "%");
		}
	}

	/**
	 * 公開区分の検索条件文字列を追加する<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            生成したSQL文を追加するバッファ
	 * @param params
	 *            SQLで使用するバインドパラメータのリストオブジェクト
	 * @param csHiddenFlg
	 *            検索対象とする公開区分
	 */
	protected void createWhereAndHiddenFlg(StringBuffer sbWhere,
			List<Object> params, String csHiddenFlg) {

		if (!StringValidateUtil.isEmpty(csHiddenFlg)) {
			sbWhere.append(" AND " + TABLE_HOUSING_STATUS_INFO_ALIAS
					+ ".hidden_flg = ? ");
			params.add(csHiddenFlg);
		}
	}

	/**
	 * ステータスの検索条件文字列を追加する<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            生成したSQL文を追加するバッファ
	 * @param params
	 *            SQLで使用するバインドパラメータのリストオブジェクト
	 * @param csStatusCd
	 *            検索対象とするステータス
	 */
	protected void createWhereAndStatusCd(StringBuffer sbWhere,
			List<Object> params, String csStatusCd) {

		if (!StringValidateUtil.isEmpty(csStatusCd)) {
			sbWhere.append(" AND " + TABLE_HOUSING_STATUS_INFO_ALIAS
					+ ".status_cd = ? ");
			params.add(csStatusCd);
		}
	}

	/**
	 * 市区町村CDの検索条件文字列を追加する<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            生成したSQL文を追加するバッファ
	 * @param params
	 *            SQLで使用するバインドパラメータのリストオブジェクト
	 * @param csAaddressCd
	 *            検索対象とする市区町村CDのパラメタ（カンマ区切りで複数の受け取りを想定）
	 */
	protected void createWhereAndAddressCd(StringBuffer sbWhere,
			List<Object> params, String csAaddressCd) {

		if (!StringValidateUtil.isEmpty(csAaddressCd)) {

			StringBuffer sqlIn = new StringBuffer();
			String[] addressCds = csAaddressCd.split(",");
			for (int i = 0; i < addressCds.length; i++) {
				if (addressCds[i].trim().length() > 0) {
					sqlIn.append(",? ");
					params.add(addressCds[i]);
				}
			}
			if (sqlIn.length() > 0) {
				// 最初のカンマを削除する
				String strComma = ",";
				sqlIn.delete(sqlIn.indexOf(strComma), sqlIn.indexOf(strComma)
						+ strComma.length());
				sbWhere.append(" AND " + TABLE_BUILDING_INFO_ALIAS
						+ ".address_cd IN (" + sqlIn.toString() + ")");
			}
		}

	}

	/**
	 * 路線CDと駅CDの検索条件文字列を追加する<br/>
	 * 駅CDの検索は路線CDが同時に設定されている必要がある。<br/>
	 * また、こだわり条件に駅徒歩時間、バス徒歩時間が含まれる場合、それを考慮する</br> <br/>
	 *
	 * @param sbWhere
	 *            生成したSQL文を追加するバッファ
	 * @param params
	 *            SQLで使用するバインドパラメータのリストオブジェクト
	 * @param csRouteCd
	 *            検索対象とする路線CDのパラメタ（カンマ区切りで複数の受け取りを想定）
	 * @param csStationCd
	 *            検索対象とする駅CDのパラメタ（カンマ区切りで複数の受け取りを想定）
	 * @param csPartSrchCd
	 *            検索対象とするこだわり条件CDのパラメタ（カンマ区切りで複数の受け取りを想定）
	 */
	protected void createWhereAndRouteCdStationCd(StringBuffer sbWhere,
			List<Object> params, String csRouteCd, String csStationCd,
			String csPartSrchCd) {

		if (!StringValidateUtil.isEmpty(csRouteCd)) {

			// 路線CD
			List<Object> pramRoute = new ArrayList<>();
			StringBuffer sqlRouteIn = new StringBuffer();
			String[] arrRouteCd = csRouteCd.split(",");
			for (int i = 0; i < arrRouteCd.length; i++) {
				if (arrRouteCd[i].trim().length() > 0) {
					sqlRouteIn.append(",? ");
					pramRoute.add(arrRouteCd[i]);
				}
			}
			params.addAll(pramRoute);

			if (sqlRouteIn.length() > 0) {

				sqlRouteIn.delete(0, 1);
				sbWhere.append(" AND EXISTS( ");
				sbWhere.append(" SELECT 1 FROM building_station_info bsi");
				sbWhere.append(" INNER JOIN station_route_info sri ON bsi.station_cd = sri.station_cd and bsi.default_route_cd = sri.route_cd ");
				sbWhere.append(" INNER JOIN station_mst sm ON sri.station_cd = sm.station_cd ");
				sbWhere.append(" INNER JOIN route_mst rm ON sri.route_cd = rm.route_cd ");
				sbWhere.append(" WHERE rm.route_cd IN ("
						+ sqlRouteIn.toString() + ") ");

				if (!StringValidateUtil.isEmpty(csStationCd)) {

					// 駅CD（沿線CDの条件があることが前提となる）
					List<Object> pramStation = new ArrayList<>();
					StringBuffer sqlStationIn = new StringBuffer();
					String[] arrStationCd = csStationCd.split(",");
					for (int i = 0; i < arrStationCd.length; i++) {
						if (arrStationCd[i].trim().length() > 0) {
							sqlStationIn.append(",? ");
							pramStation.add(arrStationCd[i]);
						}
					}
					params.addAll(pramStation);
					if (sqlStationIn.length() > 0) {
						// 最初のカンマを削除する
						String strComma = ",";
						sqlStationIn.delete(
								sqlStationIn.indexOf(strComma),
								sqlStationIn.indexOf(strComma)
										+ strComma.length());
						sbWhere.append(" AND sm.station_cd IN ("
								+ sqlStationIn.toString() + ")");
					}

				}

				// こだわり条件(徒歩時間)を指定された場合、ここで絞り込み　---------------------------
				if (!StringValidateUtil.isEmpty(csPartSrchCd)) {

					String[] arrPartSrchCd = csPartSrchCd.split(",");
					StringBuffer sqlPartWhere = new StringBuffer();

					for (int i = 0; i < arrPartSrchCd.length; i++) {
						if (arrPartSrchCd[i].trim().length() > 0) {
							// 徒歩時間 OR検索
							if (orPartSrchWarktime
									.containsKey(arrPartSrchCd[i])) {
								if (arrPartSrchCd[i].startsWith("B")) {
									// バス徒歩時間
									sqlPartWhere
											.append(" OR bsi.time_from_bus_stop <= "
													+ orPartSrchWarktime
															.get(arrPartSrchCd[i]));
								} else if (arrPartSrchCd[i].startsWith("S")) {
									// 駅徒歩時間
									sqlPartWhere
											.append(" OR bsi.time_from_station <= "
													+ orPartSrchWarktime
															.get(arrPartSrchCd[i]));
								}
							}
						}
					}

					if (sqlPartWhere.length() > 0) {
						// 最初の「OR」を削除
						String strOr = "OR";
						sqlPartWhere.delete(sqlPartWhere.indexOf(strOr),
								sqlPartWhere.indexOf(strOr) + strOr.length());
						// ANDで接続
						sbWhere.append(" AND (" + sqlPartWhere.toString() + ")");
					}
				}
				// -----------------------------------------------------------------------------------

				sbWhere.append(" AND bsi.sys_building_cd = "
						+ TABLE_BUILDING_INFO_ALIAS + ".sys_building_cd)");
			}
		}

	}

	/**
	 * 土地面積・下限の検索条件文字列を追加する<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            生成したSQL文を追加するバッファ
	 * @param params
	 *            SQLで使用するバインドパラメータのリストオブジェクト
	 * @param landAreaLower
	 *            検索対象とする土地面積・下限のパラメタ
	 */
	protected void createWhereAndLandAreaLower(StringBuffer sbWhere,
			List<Object> params, BigDecimal landAreaLower) {

		if (landAreaLower != null) {
			sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
					+ ".land_area >= ? ");
			params.add(landAreaLower);
		}

	}

	/**
	 * 土地面積・上限の検索条件文字列を追加する<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            生成したSQL文を追加するバッファ
	 * @param params
	 *            SQLで使用するバインドパラメータのリストオブジェクト
	 * @param landAreaUpper
	 *            検索対象とする土地面積・上限のパラメタ
	 */
	protected void createWhereAndLandAreaUpper(StringBuffer sbWhere,
			List<Object> params, BigDecimal landAreaUpper) {

		if (landAreaUpper != null) {
			sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
					+ ".land_area <= ? ");
			params.add(landAreaUpper);
		}

	}

	/**
	 * 専有面積・下限の検索条件文字列を追加する<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            生成したSQL文を追加するバッファ
	 * @param params
	 *            SQLで使用するバインドパラメータのリストオブジェクト
	 * @param personalAreaLower
	 *            検索対象とする専有面積・下限のパラメタ
	 */
	protected void createWhereAndPersonalAreaLower(StringBuffer sbWhere,
			List<Object> params, BigDecimal personalAreaLower) {

		if (personalAreaLower != null) {
			sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
					+ ".personal_area >= ? ");
			params.add(personalAreaLower);
		}

	}

	/**
	 * 専有面積・上限の検索条件文字列を追加する<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            生成したSQL文を追加するバッファ
	 * @param params
	 *            SQLで使用するバインドパラメータのリストオブジェクト
	 * @param personalAreaUpper
	 *            検索対象とする専有面積・上限のパラメタ
	 */
	protected void createWhereAndPersonalAreaUpper(StringBuffer sbWhere,
			List<Object> params, BigDecimal personalAreaUpper) {

		if (personalAreaUpper != null) {
			sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
					+ ".personal_area <= ? ");
			params.add(personalAreaUpper);
		}

	}

	/**
	 * 間取りCDの検索条件文字列を追加する<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            生成したSQL文を追加するバッファ
	 * @param params
	 *            SQLで使用するバインドパラメータのリストオブジェクト
	 * @param csLayoutCd
	 *            検索対象とする間取りCDのパラメタ（カンマ区切りで複数の受け取りを想定）
	 */
	protected void createWhereAndLayoutCd(StringBuffer sbWhere,
			List<Object> params, String csLayoutCd) {

		if (!StringValidateUtil.isEmpty(csLayoutCd)) {

			StringBuffer sqlIn = new StringBuffer();
			String[] arrLayoutCd = csLayoutCd.split(",");
			for (int i = 0; i < arrLayoutCd.length; i++) {
				if (arrLayoutCd[i].trim().length() > 0) {
					sqlIn.append(",? ");
					params.add(arrLayoutCd[i]);
				}
			}
			if (sqlIn.length() > 0) {
				// 最初のカンマを削除する
				String strComma = ",";
				sqlIn.delete(sqlIn.indexOf(strComma), sqlIn.indexOf(strComma)
						+ strComma.length());
				sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
						+ ".layout_cd IN (" + sqlIn.toString() + ")");
			}
		}

	}

	/**
	 * 建物面積・下限の検索条件文字列を追加する<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            生成したSQL文を追加するバッファ
	 * @param params
	 *            SQLで使用するバインドパラメータのリストオブジェクト
	 * @param buildingAreaLower
	 *            検索対象とする建物面積・下限のパラメタ
	 */
	protected void createWhereAndBuildingAreaLower(StringBuffer sbWhere,
			List<Object> params, BigDecimal buildingAreaLower) {

		if (buildingAreaLower != null) {
			sbWhere.append(" AND " + TABLE_BUILDING_DTL_INFO_ALIAS
					+ ".building_area >= ? ");
			params.add(buildingAreaLower);
		}

	}

	/**
	 * 建物面積・上限の検索条件文字列を追加する<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            生成したSQL文を追加するバッファ
	 * @param params
	 *            SQLで使用するバインドパラメータのリストオブジェクト
	 * @param buildingAreaUpper
	 *            検索対象とする建物面積・上限のパラメタ
	 */
	protected void createWhereAndBuildingAreaUpper(StringBuffer sbWhere,
			List<Object> params, BigDecimal buildingAreaUpper) {

		if (buildingAreaUpper != null) {
			sbWhere.append(" AND " + TABLE_BUILDING_DTL_INFO_ALIAS
					+ ".building_area <= ? ");
			params.add(buildingAreaUpper);
		}

	}

	/**
	 * こだわり条件CDの検索条件文字列を追加する<br/>
	 * <br/>
	 * orPartSrchListでグルーピングされたこだわり条件CDごと、<br/>
	 * 又は定義に含まれないこだわり条件CDで、EXISTS条件を生成する。<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            生成したSQL文を追加するバッファ
	 * @param params
	 *            SQLで使用するバインドパラメータのリストオブジェクト
	 * @param searchForm
	 *            検索対象とする条件のパラメタ
	 */
	protected void createWhereAndPartSrchCd(StringBuffer sbWhere,
			List<Object> params, PanaHousingSearchForm searchForm) {

		// 駅徒歩
		if (!StringValidateUtil.isEmpty(searchForm.getPartSrchCdWalkArray()) && !"999".equals(searchForm.getPartSrchCdWalkArray())) {
			String partSrchCdWalkArray = "";
			if(!"999".equals(searchForm.getPartSrchCdWalkArray())){
				partSrchCdWalkArray = searchForm.getPartSrchCdWalkArray();
			}
			sbWhere.append(" AND EXISTS(SELECT 1 FROM housing_part_info "
					+ " WHERE housingInfo.sys_housing_cd = housing_part_info.sys_housing_cd "
					+ " AND housing_part_info.part_srch_cd = ?) ");

			params.add(partSrchCdWalkArray);

		}
		// 物件画像情報、物件特長
		if (!StringValidateUtil.isEmpty(searchForm.getPartSrchCdArray())) {

			String[] partSrchCd = searchForm.getPartSrchCdArray().split(",");

			for (int i = 0; i < partSrchCd.length; i++) {
				sbWhere.append(" AND EXISTS(SELECT 1 FROM housing_part_info "
						+ " WHERE housingInfo.sys_housing_cd = housing_part_info.sys_housing_cd "
						+ " AND housing_part_info.part_srch_cd = ?) ");

				params.add(partSrchCd[i]);
			}
		}
		// 所在階数
		if (!StringValidateUtil.isEmpty(searchForm.getPartSrchCdFloorArray())) {

			String[] partSrchCdFloor = searchForm.getPartSrchCdFloorArray().split(
					",");

			for (int i = 0; i < partSrchCdFloor.length; i++) {
				if(i==0){
					sbWhere.append(" AND ( ");
				}else{
					sbWhere.append(" OR ");
				}

				sbWhere.append(" EXISTS(SELECT 1 FROM housing_part_info "
						+ " WHERE housingInfo.sys_housing_cd = housing_part_info.sys_housing_cd "
						+ " AND housing_part_info.part_srch_cd = ?) ");

				params.add(partSrchCdFloor[i]);
			}
			sbWhere.append(" ) ");
		}

	}

	/**
	 * 物件種類の検索条件文字列を追加する<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            生成したSQL文を追加するバッファ
	 * @param params
	 *            SQLで使用するバインドパラメータのリストオブジェクト
	 * @param csHousingKindCd
	 *            検索対象とする物件種類
	 */
	protected void createWhereAndHousingKindCd(StringBuffer sbWhere,
			List<Object> params, String csHousingKindCd) {

		if (!StringValidateUtil.isEmpty(csHousingKindCd)) {
			sbWhere.append(" AND " + TABLE_BUILDING_INFO_ALIAS
					+ ".housing_kind_cd = ? ");
			params.add(csHousingKindCd);

		}
	}

	/**
	 * 賃料/価格の検索条件文字列を追加する<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            生成したSQL文を追加するバッファ
	 * @param params
	 *            SQLで使用するバインドパラメータのリストオブジェクト
	 * @param priceLower
	 *            検索対象とする賃料/価格・下限のパラメタ
	 * @param priceUpper
	 *            検索対象とする賃料/価格・上限のパラメタ
	 */
	protected void createWhereAndPrice(StringBuffer sbWhere,
			List<Object> params, Long priceLower, Long priceUpper,
			boolean priceFlg) {

		// 「リフォーム価格込みで検索する」をチェックオフの場合
		if (!priceFlg) {
			if (priceLower != null) {
				sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
						+ ".price >= ? ");
				params.add(priceLower);
			}

			if (priceUpper != null) {
				sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
						+ ".price <= ? ");
				params.add(priceUpper);
			}

			// 「リフォーム価格込みで検索する」をチェックオンの場合
		} else {

			sbWhere.append(" AND (EXISTS(SELECT 1 FROM reform_plan  WHERE " + TABLE_HOUSING_INFO_ALIAS + ".sys_housing_cd = reform_plan.sys_housing_cd  AND reform_plan.hidden_flg = '0' ");

			if (priceLower != null) {
				sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
						+ ".price + reform_plan.plan_price  >= ? ");
				params.add(priceLower);
			}

			if (priceUpper != null) {
				sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
						+ ".price + reform_plan.plan_price <= ? ");
				params.add(priceUpper);
			}

			sbWhere.append(") OR (NOT EXISTS(SELECT 1 FROM reform_plan  WHERE " + TABLE_HOUSING_INFO_ALIAS + ".sys_housing_cd = reform_plan.sys_housing_cd  AND reform_plan.hidden_flg = '0') ");

			if (priceLower != null) {
				sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
						+ ".price  >= ? ");
				params.add(priceLower);
			}

			if (priceUpper != null) {
				sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
						+ ".price <= ? ");
				params.add(priceUpper);
			}

			sbWhere.append(" ))");

		}

	}

	/**
	 * 築年月の検索条件文字列を追加する<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            生成したSQL文を追加するバッファ
	 * @param params
	 *            SQLで使用するバインドパラメータのリストオブジェクト
	 * @param compDate
	 *            検索対象とする築年月のパラメタ
	 */
	protected void createWhereAndCompDate(StringBuffer sbWhere,
			List<Object> params, String compDate) {

		if (!StringUtils.isEmpty(compDate) && !"999".equals(compDate)) {
			sbWhere.append(" AND PERIOD_DIFF(EXTRACT(YEAR_MONTH FROM CURDATE()),EXTRACT(YEAR_MONTH FROM "
					+ TABLE_BUILDING_INFO_ALIAS + ".comp_date)) <= ? * 12");
			params.add(compDate);
		}
	}

	/**
	 * おすすめのポイントの検索条件文字列を追加する<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            生成したSQL文を追加するバッファ
	 * @param params
	 *            SQLで使用するバインドパラメータのリストオブジェクト
	 * @param iconCd
	 *            検索対象とするおすすめのポイントのパラメタ
	 */
	protected void createWhereAndIconCd(StringBuffer sbWhere,
			List<Object> params, String iconCd) {

		// 物件画像情報、物件特長
		if(!StringUtils.isEmpty(iconCd)){

			String[] arrIconCd = iconCd.split(",");

			if (iconCd != null && arrIconCd.length > 0) {

				for (int i = 0; i < arrIconCd.length; i++) {
					sbWhere.append(" AND EXISTS(SELECT 1 FROM housing_part_info "
							+ " WHERE housingInfo.sys_housing_cd = housing_part_info.sys_housing_cd "
							+ " AND housing_part_info.part_srch_cd = ?) ");

					params.add(arrIconCd[i]);
				}
			}
		}

	}

	/**
	 * 引渡時期の検索条件文字列を追加する<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            生成したSQL文を追加するバッファ
	 * @param params
	 *            SQLで使用するバインドパラメータのリストオブジェクト
	 * @param iconCd
	 *            検索対象と引渡時期のパラメタ
	 */
	protected void createWhereAndMovein(StringBuffer sbWhere,
			List<Object> params, String movein) {

		if (!StringUtils.isEmpty(movein)) {
			sbWhere.append(" AND housingDtlInfo.movein_timing = ?");
			params.add(movein);
		}
	}

	/**
	 * Order By句を生成する<br/>
	 *
	 * @param sbSql
	 *            生成したSQL文を追加するバッファ
	 * @param keyOrder
	 *            検索キー
	 */
	protected void createOrderBy(StringBuffer sbSql, String keyOrder) {

		if (!StringValidateUtil.isEmpty(keyOrder)) {

			StringBuffer sbOrderBy = new StringBuffer();

			if (keyOrder.equals(SORT_SYS_HOUSING_CD_ASC)) {
				// _housingCd
				sbOrderBy.append(", " + TABLE_HOUSING_INFO_ALIAS
						+ "_housingCd DESC");
			} else if (keyOrder.equals(SORT_PRICE_ASC)) {
				// 物件価格▲
				sbOrderBy
						.append(", " + TABLE_HOUSING_INFO_ALIAS + "_price ASC");

			} else if (keyOrder.equals(SORT_PRICE_DESC)) {
				// 物件価格▼
				sbOrderBy.append(", " + TABLE_HOUSING_INFO_ALIAS
						+ "_price DESC");

			} else if (keyOrder.equals(SORT_UPD_DATA_ASC)) {
				// 物件登録日（新着順）▲
				sbOrderBy.append(", " + TABLE_HOUSING_INFO_ALIAS
						+ "_updDate ASC");

			} else if (keyOrder.equals(SORT_UPD_DATA_DESC)) {
				// 物件登録日（新着順）▼
				sbOrderBy.append(", " + TABLE_HOUSING_INFO_ALIAS
						+ "_updDate DESC");

			} else if (keyOrder.equals(SORT_BUILD_DATE_DESC)) {
				// 築年数▲
				sbOrderBy.append(", " + TABLE_BUILDING_INFO_ALIAS
						+ "_compDate ASC");

			} else if (keyOrder.equals(SORT_BUILD_DATE_ASC)) {
				// 築年数▼
				sbOrderBy.append(", " + TABLE_BUILDING_INFO_ALIAS
						+ "_compDate DESC");

			} else if (keyOrder.equals(SORT_WALK_TIME_ASC)) {
				// 駅からの距離▲
				sbOrderBy.append(", " + TABLE_HOUSING_INFO_ALIAS
						+ "_minWalkingTime ASC");

			} else if (keyOrder.equals(SORT_WALK_TIME_DESC)) {
				// 駅からの距離▼
				sbOrderBy.append(", " + TABLE_HOUSING_INFO_ALIAS
						+ "_minWalkingTime DESC");

			}

			// 最初のカンマを削除して「 ORDER BY 」を付ける
			if (sbOrderBy.length() > 0) {
				String strComma = ",";
				sbOrderBy.delete(sbOrderBy.indexOf(strComma),
						sbOrderBy.indexOf(strComma) + strComma.length());
				sbOrderBy.insert(0, " ORDER BY ");

				sbSql.append(sbOrderBy.toString());
			}
		}

	}
}
