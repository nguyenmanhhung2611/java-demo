package jp.co.transcosmos.dm3.core.model.housing.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingSearchForm;
import jp.co.transcosmos.dm3.dao.FragmentResultSetExtractor;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class SearchHousingDAOImpl implements SearchHousingDAO {

	// テーブル物理名、エイリアス
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

	// ソート条件
	/** 物件番号（ソート条件） */
	public static final String SORT_SYS_HOUSING_CD_ASC = "0";
	/** 賃料/価格が安い順（ソート条件） */
	public static final String SORT_PRICE_ASC = "1";
	/** 賃料/価格が高い順（ソート条件） */
	public static final String SORT_PRICE_DESC = "2";
	/** 専有面積が狭い順（ソート条件） */
	public static final String SORT_PERSONAL_AREA_ASC = "3";
	/** 専有面積が広い順（ソート条件） */
	public static final String SORT_PERSONAL_AREA_DESC = "4";
	/** 築年が古い順（ソート条件） */
	public static final String SORT_BUILD_DATE_DESC = "5";
	/** 築年が新しい順（ソート条件） */
	public static final String SORT_BUILD_DATE_ASC = "6";

	private static final Log log = LogFactory.getLog(SearchHousingDAOImpl.class);

	/** データソース */
	protected DataSource dataSource;

	/** 物件検索結果を格納する為のMapper */
	protected SearchHousingRowMapper searchHousingRowMapper;

	// 以下、applicationContextに定義
	// 
	// OR条件で検索したいこだわり条件をMapで保持する
	// 　key：こだわり条件CD、原則キーの存在有無が検索条件処理となる
	// 　value：検索処理に使用する値を保持できる
	// 複数のOR検索対象をListで保持する

	/** OR条件で検索するこだわり条件CDのグルーピング定義 */
	private List<Map<String, String>> orPartSrchList =  new ArrayList<Map<String, String>>();

	// →駅検索にて使用する為にOR_PART_SRCH_LISTに直接宣言しない
	/** 駅徒歩5分 or 駅徒歩10分 or 駅徒歩15分 or 駅徒歩20分 or バス徒歩5分 */
	private Map<String, String> orPartSrchWarktime = new HashMap<String, String>();

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setSearchHousingRowMapper(SearchHousingRowMapper searchHousingRowMapper) {
		this.searchHousingRowMapper = searchHousingRowMapper;
	}

	public void setOrPartSrchList(List<Map<String, String>> orPartSrchList) {
		this.orPartSrchList = orPartSrchList;
	}

	public void setOrPartSrchWarktime(Map<String, String> orPartSrchWarktime) {
		this.orPartSrchWarktime = orPartSrchWarktime;
	}

	/**
	 * 物件情報（一部、建物情報）を検索し、結果リストを復帰する。（一覧用）<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、物件情報を検索する。<br/>
	 * 検索結果は Form オブジェクトに格納され、取得した該当件数を戻り値として復帰する。<br/>
	 * <br/>
	 * このメソッドを使用した場合、暗黙の抽出条件（例えば、非公開物件の除外など）が適用される。<br/>
	 * よって、フロント側は基本的にこのメソッドを使用する事。<br/>
	 * <br/>
	 * 
	 * @param searchForm 検索条件、および、検索結果の格納オブジェクト
	 * @param full false の場合、公開対象外を除外する。　true の場合は除外しない
	 * 
	 * @return 該当件数
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	@Override
	public List<Housing> searchHousing(HousingSearchForm searchForm) {
		return searchHousing(searchForm, false);
	}

	/**
	 * 物件情報（一部、建物情報）を検索し、結果リストを復帰する。（一覧用）<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、物件情報を検索する。<br/>
	 * 検索結果は Form オブジェクトに格納され、取得した該当件数を戻り値として復帰する。<br/>
	 * <br/>
	 * 
	 * @param searchForm 検索条件、および、検索結果の格納オブジェクト
	 * @param full false の場合、公開対象外を除外する。　true の場合は除外しない
	 * 
	 * @return 該当件数
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Housing> searchHousing(HousingSearchForm searchForm, boolean full) {

		JdbcTemplate template = new JdbcTemplate(dataSource);

		StringBuffer sbSql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		// 検索SQLを生成
		createSql(searchForm, sbSql, params, full);

		log.debug("SQL : " + sbSql.toString());

		return (List<Housing>) template.query(sbSql.toString(), (Object[]) params.toArray(),
				new FragmentResultSetExtractor<Object>(this.searchHousingRowMapper, searchForm.getStartIndex(),
						searchForm.getEndIndex()));
	}

	/**
	 * 物件検索のSQL文を生成する。<br/>
	 * <br/>
	 * 
	 * @param sbSql 生成したSQL文を追加するバッファ
	 * @param params SQLで使用するバインドパラメータのリストオブジェクト
	 * @param full falseの場合、公開対象外を除外する。trueの場合は除外しない
	 */
	protected void createSql(HousingSearchForm searchForm, StringBuffer sbSql, List<Object> params, boolean full) {

		createSelect(sbSql, params, full);
		createFrom(sbSql, params, full);
		createWhere(searchForm, sbSql, params, full);
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
	 * @param sbSql 生成したSQL文を追加するバッファ
	 * @param params SQLで使用するバインドパラメータのリストオブジェクト
	 * @param full falseの場合、公開対象外を除外する。trueの場合は除外しない
	 */
	protected void createSelect(StringBuffer sbSql, List<Object> params, boolean full) {

		StringBuffer sbSelect = new StringBuffer();

		sbSelect.append(" SELECT ");

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
		// 登録日
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".ins_date AS " + TABLE_HOUSING_INFO_ALIAS + "_insDate");
		// 登録者
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".ins_user_id AS " + TABLE_HOUSING_INFO_ALIAS + "_insUserId");
		// 最終更新日
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".upd_date AS " + TABLE_HOUSING_INFO_ALIAS + "_updDate");
		// 最終更新者
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".upd_user_id AS " + TABLE_HOUSING_INFO_ALIAS + "_updUserId");

		// 物件ステータス情報
		// システム物件CD
		sbSelect.append(", " + TABLE_HOUSING_STATUS_INFO_ALIAS + ".sys_housing_cd AS " + TABLE_HOUSING_STATUS_INFO_ALIAS + "_sysHousingCd");
		// 非公開フラグ
		sbSelect.append(", " + TABLE_HOUSING_STATUS_INFO_ALIAS + ".hidden_flg AS " + TABLE_HOUSING_STATUS_INFO_ALIAS + "_hiddenFlg");

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

		// Select句の拡張処理
		createSelectExtra(sbSelect, params, full);

		sbSql.append(sbSelect.toString());

	}

	/**
	 * Select句生成の拡張処理<br/>
	 * 継承先でSelect項目を追加する場合、このメソッドをオーバーライドする<br/>
	 * 
	 * @param sbWhere
	 * @param params
	 * @param searchForm
	 */
	protected void createSelectExtra(StringBuffer sbSelect, List<Object> params, boolean full) {

		// 継承先でSelect項目を追加する場合、このメソッドをオーバーライドする

	}

	/**
	 * From句の文字列を生成する<br/>
	 * 使用するテーブル名、別名は定数を定義して記述すること<br/>
	 * （コーディング簡素化、タイプミス防止）<br/>
	 * <br/>
	 * 
	 * @param sbSql 生成したSQL文を追加するバッファ
	 * @param params SQLで使用するバインドパラメータのリストオブジェクト
	 * @param full falseの場合、公開対象外を除外する。trueの場合は除外しない
	 */
	protected void createFrom(StringBuffer sbSql, List<Object> params, boolean full) {

		StringBuffer sbFrom = new StringBuffer();

		sbFrom.append(" FROM ");
		sbFrom.append(" " + TABLE_HOUSING_INFO_NAME + " " + TABLE_HOUSING_INFO_ALIAS);
		sbFrom.append(" LEFT OUTER JOIN " + TABLE_HOUSING_STATUS_INFO_NAME + " " + TABLE_HOUSING_STATUS_INFO_ALIAS + " ON " + TABLE_HOUSING_INFO_ALIAS + ".sys_housing_cd = " + TABLE_HOUSING_STATUS_INFO_ALIAS + ".sys_housing_cd");
		sbFrom.append(" INNER JOIN " + TABLE_BUILDING_INFO_NAME + " " + TABLE_BUILDING_INFO_ALIAS + " ON " + TABLE_HOUSING_INFO_ALIAS + ".sys_building_cd = " + TABLE_BUILDING_INFO_ALIAS + ".sys_building_cd");
		sbFrom.append(" LEFT OUTER JOIN " + TABLE_BUILDING_DTL_INFO_NAME + " " + TABLE_BUILDING_DTL_INFO_ALIAS + " ON " + TABLE_HOUSING_INFO_ALIAS + ".sys_building_cd = " + TABLE_BUILDING_DTL_INFO_ALIAS + ".sys_building_cd");

		createFromExtra(sbFrom, params, full);

		sbSql.append(sbFrom.toString());

	}

	/**
	 * From句生成の拡張処理<br/>
	 * 継承先で結合テーブルを追加する場合、このメソッドをオーバーライドする<br/>
	 * 生成する文字列はJOIN句からとなるようにすること<br/>
	 * 
	 * @param sbWhere
	 * @param params
	 * @param searchForm
	 */
	protected void createFromExtra(StringBuffer sbFrom, List<Object> params, boolean full) {

		// 継承先で結合テーブルを追加する場合、このメソッドをオーバーライドする

	}

	/**
	 * Where句の文字列を生成する<br/>
	 * 検索条件ごとに、「AND～」となる文字列を生成するメソッドを呼び出している<br/>
	 * <br/>
	 * 
	 * @param sbSql 生成したSQL文を追加するバッファ
	 * @param params SQLで使用するバインドパラメータのリストオブジェクト
	 * @param full falseの場合、公開対象外を除外する。trueの場合は除外しない
	 */
	protected void createWhere(HousingSearchForm searchForm, StringBuffer sbSql, List<Object> params, boolean full) {

		StringBuffer sbWhere = new StringBuffer();

		// 非公開フラグのチェック
		createWhereAndFull(sbWhere, params, full);

		// 物件番号
		createWhereAndHousingCd(sbWhere, params, searchForm.getKeyHousingCd());
		// 物件名
		createWhereAndDisplayHousingName(sbWhere, params, searchForm.getKeyDisplayHousingName());

		// 都道府県CD
		createWhereAndPrefCd(sbWhere, params, searchForm.getKeyPrefCd());
		// 市区町村CD
		createWhereAndAddressCd(sbWhere, params, searchForm.getKeyAddressCd());
		// 沿線CD、駅CD
		createWhereAndRouteCdStationCd(sbWhere, params, searchForm.getKeyRouteCd(), searchForm.getKeyStationCd(), searchForm.getKeyPartSrchCd());

		// 家賃
		createWhereAndPriceLower(sbWhere, params, searchForm.getKeyPriceLower());
		createWhereAndPriceUpper(sbWhere, params, searchForm.getKeyPriceUpper());

		// 土地面積
		createWhereAndLandAreaLower(sbWhere, params, searchForm.getKeyLandAreaLower());
		createWhereAndLandAreaUpper(sbWhere, params, searchForm.getKeyLandAreaUpper());
		// 専有面積
		createWhereAndPersonalAreaLower(sbWhere, params, searchForm.getKeyPersonalAreaLower());
		createWhereAndPersonalAreaUpper(sbWhere, params, searchForm.getKeyPersonalAreaUpper());

		// 物件種類CD
		createWhereAndHousingKindCd(sbWhere, params, searchForm.getKeyHousingKindCd());
		// 間取りCD
		createWhereAndLayoutCd(sbWhere, params, searchForm.getKeyLayoutCd());

		// 建物面積
		createWhereAndBuildingAreaLower(sbWhere, params, searchForm.getKeyBuildingAreaLower());
		createWhereAndBuildingAreaUpper(sbWhere, params, searchForm.getKeyBuildingAreaUpper());

		// こだわり条件
		createWhereAndPartSrchCd(sbWhere, params, searchForm.getKeyPartSrchCd());

		// 検索条件の拡張
		createWhereExtra(sbWhere, params, searchForm, full);

		// 最初の「AND」を削除して「 WHERE 」を付ける
		if (sbWhere.length() > 0) {
			String strAnd = "AND";
			sbWhere.delete(sbWhere.indexOf(strAnd), sbWhere.indexOf(strAnd) + strAnd.length());
			sbWhere.insert(0, " WHERE ");
		}
		
		sbSql.append(sbWhere.toString());

	}

	/**
	 * Where句生成の拡張処理<br/>
	 * 継承先で固有の検索条件を追加する場合、このメソッドをオーバーライドする<br/>
	 * 生成する文字列は「AND～」となるようにすること<br/>
	 * 
	 * @param sbWhere
	 * @param params
	 * @param searchForm
	 * @param full
	 */
	protected void createWhereExtra(StringBuffer sbWhere, List<Object> params, HousingSearchForm searchForm, boolean full) {

		// 継承先で固有の検索条件を追加する場合、このメソッドをオーバーライドする。

	}

	/**
	 * 非公開フラグの検索条件文字列を追加する<br/>
	 * <br/>
	 * 
	 * @param sbWhere 生成したSQL文を追加するバッファ
	 * @param params SQLで使用するバインドパラメータのリストオブジェクト
	 * @param full falseの場合、公開対象外を除外する。trueの場合は除外しない
	 */
	protected void createWhereAndFull(StringBuffer sbWhere, List<Object> params, boolean full) {

		if (!full) {
			sbWhere.append(" AND (" + TABLE_HOUSING_STATUS_INFO_ALIAS + ".hidden_flg IS NULL OR " + TABLE_HOUSING_STATUS_INFO_ALIAS + ".hidden_flg IS FALSE ) ");
		}

	}

	/**
	 * 物件番号の検索条件文字列を追加する<br/>
	 * <br/>
	 * 
	 * @param sbWhere 生成したSQL文を追加するバッファ
	 * @param params SQLで使用するバインドパラメータのリストオブジェクト
	 * @param csHousingCd 検索対象とする物件番号（カンマ区切りで複数の受け取りを想定）
	 */
	protected void createWhereAndHousingCd(StringBuffer sbWhere, List<Object> params, String csHousingCd) {

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
				sqlIn.delete(sqlIn.indexOf(strComma), sqlIn.indexOf(strComma) + strComma.length());
				sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS + ".housing_cd IN (" + sqlIn.toString() + ")");
			}
		}

	}

	/**
	 * 表示用物件名の検索条件文字列を追加する<br/>
	 * <br/>
	 * 
	 * @param sbWhere 生成したSQL文を追加するバッファ
	 * @param params SQLで使用するバインドパラメータのリストオブジェクト
	 * @param displayHousingName 検索対象とする表示用物件名（部分一致）
	 */
	protected void createWhereAndDisplayHousingName(StringBuffer sbWhere, List<Object> params, String displayHousingName) {

		if (!StringValidateUtil.isEmpty(displayHousingName)) {
			sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS + ".display_housing_name LIKE ? ");
			params.add("%" + displayHousingName + "%");
		}

	}

	/**
	 * 都道府県CDの検索条件文字列を追加する<br/>
	 * <br/>
	 * 
	 * @param sbWhere 生成したSQL文を追加するバッファ
	 * @param params SQLで使用するバインドパラメータのリストオブジェクト
	 * @param csPrefCd 検索対象とする都道府県CDのパラメタ（カンマ区切りで複数の受け取りを想定）
	 */
	protected void createWhereAndPrefCd(StringBuffer sbWhere, List<Object> params, String csPrefCd) {

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
				sqlIn.delete(sqlIn.indexOf(strComma), sqlIn.indexOf(strComma) + strComma.length());
				sbWhere.append(" AND " + TABLE_BUILDING_INFO_ALIAS + ".pref_cd IN (" + sqlIn.toString() + ")");
			}
		}

	}

	/**
	 * 市区町村CDの検索条件文字列を追加する<br/>
	 * <br/>
	 * 
	 * @param sbWhere 生成したSQL文を追加するバッファ
	 * @param params SQLで使用するバインドパラメータのリストオブジェクト
	 * @param csAaddressCd 検索対象とする市区町村CDのパラメタ（カンマ区切りで複数の受け取りを想定）
	 */
	protected void createWhereAndAddressCd(StringBuffer sbWhere, List<Object> params, String csAaddressCd) {

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
				sqlIn.delete(sqlIn.indexOf(strComma), sqlIn.indexOf(strComma) + strComma.length());
				sbWhere.append(" AND " + TABLE_BUILDING_INFO_ALIAS + ".address_cd IN (" + sqlIn.toString() + ")");
			}
		}

	}

	/**
	 * 路線CDと駅CDの検索条件文字列を追加する<br/>
	 * 駅CDの検索は路線CDが同時に設定されている必要がある。<br/>
	 * また、こだわり条件に駅徒歩時間、バス徒歩時間が含まれる場合、それを考慮する</br> <br/>
	 * 
	 * @param sbWhere 生成したSQL文を追加するバッファ
	 * @param params SQLで使用するバインドパラメータのリストオブジェクト
	 * @param csRouteCd 検索対象とする路線CDのパラメタ（カンマ区切りで複数の受け取りを想定）
	 * @param csStationCd 検索対象とする駅CDのパラメタ（カンマ区切りで複数の受け取りを想定）
	 * @param csPartSrchCd 検索対象とするこだわり条件CDのパラメタ（カンマ区切りで複数の受け取りを想定）
	 */
	protected void createWhereAndRouteCdStationCd(StringBuffer sbWhere, List<Object> params, String csRouteCd,
			String csStationCd, String csPartSrchCd) {

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
				sbWhere.append(" INNER JOIN station_route_info sri ON bsi.station_cd = sri.station_cd ");
				sbWhere.append(" INNER JOIN station_mst sm ON sri.station_cd = sm.station_cd ");
				sbWhere.append(" INNER JOIN route_mst rm ON sri.route_cd = rm.route_cd ");
				sbWhere.append(" WHERE rm.route_cd IN (" + sqlRouteIn.toString() + ") ");

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
						sqlStationIn.delete(sqlStationIn.indexOf(strComma), sqlStationIn.indexOf(strComma) + strComma.length());
						sbWhere.append(" AND sm.station_cd IN (" + sqlStationIn.toString() + ")");
					}

				}

				// こだわり条件(徒歩時間)を指定された場合、ここで絞り込み　---------------------------
				if (!StringValidateUtil.isEmpty(csPartSrchCd)) {

					String[] arrPartSrchCd = csPartSrchCd.split(",");
					StringBuffer sqlPartWhere = new StringBuffer();

					for (int i = 0; i < arrPartSrchCd.length; i++) {
						if (arrPartSrchCd[i].trim().length() > 0) {
							// 徒歩時間 OR検索
							if (orPartSrchWarktime.containsKey(arrPartSrchCd[i])) {
								if (arrPartSrchCd[i].startsWith("B")) {
									// バス徒歩時間
									sqlPartWhere.append(" OR bsi.time_from_bus_stop <= " + orPartSrchWarktime.get(arrPartSrchCd[i]));
								} else if (arrPartSrchCd[i].startsWith("S")) {
									// 駅徒歩時間
									sqlPartWhere.append(" OR bsi.time_from_station <= " + orPartSrchWarktime.get(arrPartSrchCd[i]));
								}
							}
						}
					}

					if (sqlPartWhere.length() > 0) {
						// 最初の「OR」を削除
						String strOr = "OR";
						sqlPartWhere.delete(sqlPartWhere.indexOf(strOr), sqlPartWhere.indexOf(strOr) + strOr.length());
						// ANDで接続
						sbWhere.append(" AND (" + sqlPartWhere.toString() + ")");
					}
				}
				// -----------------------------------------------------------------------------------

				sbWhere.append(" AND bsi.sys_building_cd = " + TABLE_BUILDING_INFO_ALIAS + ".sys_building_cd)");
			}
		}

	}

	/**
	 * 賃料/価格・下限の検索条件文字列を追加する<br/>
	 * <br/>
	 * 
	 * @param sbWhere 生成したSQL文を追加するバッファ
	 * @param params SQLで使用するバインドパラメータのリストオブジェクト
	 * @param priceLower 検索対象とする賃料/価格・下限のパラメタ
	 */
	protected void createWhereAndPriceLower(StringBuffer sbWhere, List<Object> params, Long priceLower) {

		if (priceLower != null) {
			sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS + ".price >= ? ");
			params.add(priceLower);
		}

	}

	/**
	 * 賃料/価格・上限の検索条件文字列を追加する<br/>
	 * <br/>
	 * 
	 * @param sbWhere 生成したSQL文を追加するバッファ
	 * @param params SQLで使用するバインドパラメータのリストオブジェクト
	 * @param priceUpper 検索対象とする賃料/価格・上限のパラメタ
	 */
	protected void createWhereAndPriceUpper(StringBuffer sbWhere, List<Object> params, Long priceUpper) {

		if (priceUpper != null) {
			sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS + ".price <= ? ");
			params.add(priceUpper);
		}

	}

	/**
	 * 土地面積・下限の検索条件文字列を追加する<br/>
	 * <br/>
	 * 
	 * @param sbWhere 生成したSQL文を追加するバッファ
	 * @param params SQLで使用するバインドパラメータのリストオブジェクト
	 * @param landAreaLower 検索対象とする土地面積・下限のパラメタ
	 */
	protected void createWhereAndLandAreaLower(StringBuffer sbWhere, List<Object> params, BigDecimal landAreaLower) {

		if (landAreaLower != null) {
			sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS + ".land_area >= ? ");
			params.add(landAreaLower);
		}

	}

	/**
	 * 土地面積・上限の検索条件文字列を追加する<br/>
	 * <br/>
	 * 
	 * @param sbWhere 生成したSQL文を追加するバッファ
	 * @param params SQLで使用するバインドパラメータのリストオブジェクト
	 * @param landAreaUpper 検索対象とする土地面積・上限のパラメタ
	 */
	protected void createWhereAndLandAreaUpper(StringBuffer sbWhere, List<Object> params, BigDecimal landAreaUpper) {

		if (landAreaUpper != null) {
			sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS + ".land_area <= ? ");
			params.add(landAreaUpper);
		}

	}

	/**
	 * 専有面積・下限の検索条件文字列を追加する<br/>
	 * <br/>
	 * 
	 * @param sbWhere 生成したSQL文を追加するバッファ
	 * @param params SQLで使用するバインドパラメータのリストオブジェクト
	 * @param personalAreaLower 検索対象とする専有面積・下限のパラメタ
	 */
	protected void createWhereAndPersonalAreaLower(StringBuffer sbWhere, List<Object> params, BigDecimal personalAreaLower) {

		if (personalAreaLower != null) {
			sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS + ".personal_area >= ? ");
			params.add(personalAreaLower);
		}

	}

	/**
	 * 専有面積・上限の検索条件文字列を追加する<br/>
	 * <br/>
	 * 
	 * @param sbWhere 生成したSQL文を追加するバッファ
	 * @param params SQLで使用するバインドパラメータのリストオブジェクト
	 * @param personalAreaUpper 検索対象とする専有面積・上限のパラメタ
	 */
	protected void createWhereAndPersonalAreaUpper(StringBuffer sbWhere, List<Object> params, BigDecimal personalAreaUpper) {

		if (personalAreaUpper != null) {
			sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS + ".personal_area <= ? ");
			params.add(personalAreaUpper);
		}

	}

	/**
	 * 物件種類CDの検索条件文字列を追加する<br/>
	 * <br/>
	 * 
	 * @param sbWhere 生成したSQL文を追加するバッファ
	 * @param params SQLで使用するバインドパラメータのリストオブジェクト
	 * @param csHousingKindCd 検索対象とする物件種類CDのパラメタ（カンマ区切りで複数の受け取りを想定）
	 */
	protected void createWhereAndHousingKindCd(StringBuffer sbWhere, List<Object> params, String csHousingKindCd) {

		if (!StringValidateUtil.isEmpty(csHousingKindCd)) {

			StringBuffer sqlIn = new StringBuffer();
			String[] arrHousingKindCd = csHousingKindCd.split(",");
			for (int i = 0; i < arrHousingKindCd.length; i++) {
				if (arrHousingKindCd[i].trim().length() > 0) {
					sqlIn.append(",? ");
					params.add(arrHousingKindCd[i]);
				}
			}
			if (sqlIn.length() > 0) {
				// 最初のカンマを削除する
				String strComma = ",";
				sqlIn.delete(sqlIn.indexOf(strComma), sqlIn.indexOf(strComma) + strComma.length());
				sbWhere.append(" AND " + TABLE_BUILDING_INFO_ALIAS + ".housing_kind_cd IN (" + sqlIn.toString() + ")");
			}
		}

	}

	/**
	 * 間取りCDの検索条件文字列を追加する<br/>
	 * <br/>
	 * 
	 * @param sbWhere 生成したSQL文を追加するバッファ
	 * @param params SQLで使用するバインドパラメータのリストオブジェクト
	 * @param csLayoutCd 検索対象とする間取りCDのパラメタ（カンマ区切りで複数の受け取りを想定）
	 */
	protected void createWhereAndLayoutCd(StringBuffer sbWhere, List<Object> params, String csLayoutCd) {

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
				sqlIn.delete(sqlIn.indexOf(strComma), sqlIn.indexOf(strComma) + strComma.length());
				sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS + ".layout_cd IN (" + sqlIn.toString() + ")");
			}
		}

	}

	/**
	 * 建物面積・下限の検索条件文字列を追加する<br/>
	 * <br/>
	 * 
	 * @param sbWhere 生成したSQL文を追加するバッファ
	 * @param params SQLで使用するバインドパラメータのリストオブジェクト
	 * @param buildingAreaLower 検索対象とする建物面積・下限のパラメタ
	 */
	protected void createWhereAndBuildingAreaLower(StringBuffer sbWhere, List<Object> params, BigDecimal buildingAreaLower) {

		if (buildingAreaLower != null) {
			sbWhere.append(" AND " + TABLE_BUILDING_DTL_INFO_ALIAS + ".building_area >= ? ");
			params.add(buildingAreaLower);
		}

	}

	/**
	 * 建物面積・上限の検索条件文字列を追加する<br/>
	 * <br/>
	 * 
	 * @param sbWhere 生成したSQL文を追加するバッファ
	 * @param params SQLで使用するバインドパラメータのリストオブジェクト
	 * @param buildingAreaUpper 検索対象とする建物面積・上限のパラメタ
	 */
	protected void createWhereAndBuildingAreaUpper(StringBuffer sbWhere, List<Object> params, BigDecimal buildingAreaUpper) {

		if (buildingAreaUpper != null) {
			sbWhere.append(" AND " + TABLE_BUILDING_DTL_INFO_ALIAS + ".building_area <= ? ");
			params.add(buildingAreaUpper);
		}

	}

	/**
	 * こだわり条件CDの検索条件文字列を追加する<br/>
	 * <br/>
	 * orPartSrchListでグルーピングされたこだわり条件CDごと、<br/>
	 * 又は定義に含まれないこだわり条件CDで、EXISTS条件を生成する。<br/>
	 * <br/>
	 * 例）<br/>
	 * 　　指定されたこだわり条件CD<br/>
	 * 　　→[S05,S10,NEW,ANT,PAD]<br/>
	 * 　　orPartSrchListの定義<br/>
	 * 　　→[{S05=5, S10=10, S15=15, S20=20, B05=5, B10=10}, {FRE=FRE, REI=REI}, {NEW=NEW, C05=C05}]<br/>
	 * <br/>
	 * 　　生成されるEXISTS条件は以下の通り<br/>
	 * <br/>
	 * 　　  AND EXISTS ( SELECT 1 <br/>
	 * 　　    FROM housing_part_info hpi<br/> 
	 * 　　    WHERE<br/>
	 * 　　      housingInfo.sys_housing_cd = hpi.sys_housing_cd<br/> 
	 * 　　      AND hpi.part_srch_cd = 'ANT'<br/>
	 * 　　  )<br/> 
	 * 　　  AND EXISTS ( SELECT 1<br/> 
	 * 　　    FROM housing_part_info hpi<br/> 
	 * 　　    WHERE<br/>
	 * 　　      housingInfo.sys_housing_cd = hpi.sys_housing_cd<br/> 
	 * 　　      AND hpi.part_srch_cd = 'PAD'<br/>
	 * 　　  )<br/> 
	 * 　　  AND EXISTS ( SELECT 1<br/> 
	 * 　　    FROM housing_part_info hpi<br/> 
	 * 　　    WHERE<br/>
	 * 　　      housingInfo.sys_housing_cd = hpi.sys_housing_cd<br/> 
	 * 　　      AND (hpi.part_srch_cd = 'S05' OR hpi.part_srch_cd = 'S10' )<br/>
	 * 　　  )<br/> 
	 * 　　  AND EXISTS ( SELECT 1<br/> 
	 * 　　    FROM housing_part_info hpi<br/> 
	 * 　　    WHERE<br/>
	 * 　　      housingInfo.sys_housing_cd = hpi.sys_housing_cd<br/> 
	 * 　　      AND (hpi.part_srch_cd = 'NEW' )<br/>
	 * 　　  )<br/> 
	 * <br/>
	 * 
	 * @param sbWhere 生成したSQL文を追加するバッファ
	 * @param params SQLで使用するバインドパラメータのリストオブジェクト
	 * @param csPartSrchCd 検索対象とするこだわり条件CDのパラメタ
	 */
	protected void createWhereAndPartSrchCd(StringBuffer sbWhere, List<Object> params, String csPartSrchCd) {

		if (!StringValidateUtil.isEmpty(csPartSrchCd)) {

			String[] arrPartSrchCd = csPartSrchCd.split(",");

			List<StringBuffer> sqlPartWhereList = new ArrayList<StringBuffer>(orPartSrchList.size());
			List<StringBuffer> orPartSrchCdList = new ArrayList<StringBuffer>(orPartSrchList.size());
			for (int i = 0; i < orPartSrchList.size(); i++) {
				sqlPartWhereList.add(new StringBuffer());
				orPartSrchCdList.add(new StringBuffer());
			}

			for (int i = 0; i < arrPartSrchCd.length; i++) {

				boolean add = true;

				if (arrPartSrchCd[i].trim().length() > 0) {

					if (orPartSrchList.size() > 0) {
						for (int j = 0; j < orPartSrchList.size(); j++) {
							Map<String, String> orPartSrhCdMap = orPartSrchList.get(j);
							if (orPartSrhCdMap.containsKey(arrPartSrchCd[i])) {
								StringBuffer sqlPartWhere = sqlPartWhereList.get(j);
								StringBuffer orPartSrchCd = orPartSrchCdList.get(j);

								sqlPartWhere.append(" OR hpi.part_srch_cd = ? ");
								orPartSrchCd.append("," + arrPartSrchCd[i]);

								add = false;
								break;
							}
						}
					}
					if (add) {
						sbWhere.append(" AND EXISTS(");
						sbWhere.append(" SELECT 1 FROM housing_part_info hpi");
						sbWhere.append(" WHERE " + TABLE_HOUSING_INFO_ALIAS + ".sys_housing_cd = hpi.sys_housing_cd");
						sbWhere.append(" AND hpi.part_srch_cd = ? )");
						params.add(arrPartSrchCd[i]);
					}
				}
			}

			for (int i = 0; i < orPartSrchList.size(); i++) {
				StringBuffer sqlPartWhere = sqlPartWhereList.get(i);
				StringBuffer orPartSrchCd = orPartSrchCdList.get(i);

				if (sqlPartWhere.length() > 0) {
					// 最初のORを削除する
					String strOr = "OR";
					sqlPartWhere.delete(sqlPartWhere.indexOf(strOr), sqlPartWhere.indexOf(strOr) + strOr.length());
					// 最初のカンマを削除する
					String strComma = ",";
					orPartSrchCd.delete(orPartSrchCd.indexOf(strComma), orPartSrchCd.indexOf(strComma) + strComma.length());
					// ANDで接続
					sbWhere.append(" AND EXISTS(");
					sbWhere.append(" SELECT 1 FROM housing_part_info hpi");
					sbWhere.append(" WHERE " + TABLE_HOUSING_INFO_ALIAS + ".sys_housing_cd = hpi.sys_housing_cd");
					sbWhere.append(" AND (" + sqlPartWhere.toString() + "))");

					String[] tmpPartSrchCd = orPartSrchCd.toString().split(",");
					for (int j = 0; j < tmpPartSrchCd.length; j++) {
						params.add(tmpPartSrchCd[j]);
					}
				}

			}

		}
	}

	/**
	 * Order By句を生成する<br/>
	 * 
	 * @param sbSql 生成したSQL文を追加するバッファ
	 * @param keyOrder 検索キー
	 */
	protected void createOrderBy(StringBuffer sbSql, String keyOrder) {

		if (!StringValidateUtil.isEmpty(keyOrder)) {

			StringBuffer sbOrderBy = new StringBuffer();

			if (keyOrder.equals(SORT_SYS_HOUSING_CD_ASC)) {
				// システム物件CDの昇順
				sbOrderBy.append(", " + TABLE_HOUSING_INFO_ALIAS + "_sysHousingCd ASC");

			} else if (keyOrder.equals(SORT_PRICE_ASC)) {
				// 賃料/価格の安い順
				sbOrderBy.append(", " + TABLE_HOUSING_INFO_ALIAS + "_price ASC");

			} else if (keyOrder.equals(SORT_PRICE_DESC)) {
				// 賃料/価格の高い順
				sbOrderBy.append(", " + TABLE_HOUSING_INFO_ALIAS + "_price DESC");

			} else if (keyOrder.equals(SORT_PERSONAL_AREA_ASC)) {
				// 専有面積の狭い順
				sbOrderBy.append(", " + TABLE_HOUSING_INFO_ALIAS + "_personalArea ASC");

			} else if (keyOrder.equals(SORT_PERSONAL_AREA_DESC)) {
				// 専有面積の広い順
				sbOrderBy.append(", " + TABLE_HOUSING_INFO_ALIAS + "_personalArea DESC");

			} else if (keyOrder.equals(SORT_BUILD_DATE_DESC)) {
				// 築年の古い順
				sbOrderBy.append(", " + TABLE_BUILDING_INFO_ALIAS + "_compDate ASC");

			} else if (keyOrder.equals(SORT_BUILD_DATE_ASC)) {
				// 築年の若い順
				sbOrderBy.append(", " + TABLE_BUILDING_INFO_ALIAS + "_compDate DESC");

			}

			// ソート条件の拡張
			createOrderByExtra(sbOrderBy, keyOrder);

			// 最初のカンマを削除して「 ORDER BY 」を付ける
			if (sbOrderBy.length() > 0) {
				String strComma = ",";
				sbOrderBy.delete(sbOrderBy.indexOf(strComma), sbOrderBy.indexOf(strComma) + strComma.length());
				sbOrderBy.insert(0, " ORDER BY ");

				sbSql.append(sbOrderBy.toString());
			}
		}

	}

	/**
	 * OrderBy句生成の拡張処理<br/>
	 * 継承先で固有の検索条件を追加する場合、このメソッドをオーバーライドする<br/>
	 * 生成する文字列は「, 」から始まるようにすること<br/>
	 * 
	 * @param sbOrderBy 生成したSQL文を追加するバッファ
	 * @param keyOrder 検索キー
	 */
	protected void createOrderByExtra(StringBuffer sbOrderBy, String keyOrder) {

		// 継承先で固有の検索条件を追加する場合、このメソッドをオーバーライドする。

	}

}
