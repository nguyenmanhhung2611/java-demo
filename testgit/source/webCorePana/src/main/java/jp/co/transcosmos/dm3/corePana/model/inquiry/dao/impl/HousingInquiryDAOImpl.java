package jp.co.transcosmos.dm3.corePana.model.inquiry.dao.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import jp.co.transcosmos.dm3.core.model.InquiryManage;
import jp.co.transcosmos.dm3.core.model.building.Building;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.inquiry.HousingInquiry;
import jp.co.transcosmos.dm3.core.model.inquiry.InquiryHeaderInfo;
import jp.co.transcosmos.dm3.core.vo.AdminLoginInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.InquiryDtlInfo;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.model.housing.dao.impl.PanaSearchHousingDAOImpl;
import jp.co.transcosmos.dm3.corePana.model.inquiry.AssessmentInquiryManageImpl;
import jp.co.transcosmos.dm3.corePana.model.inquiry.GeneralInquiryManageImpl;
import jp.co.transcosmos.dm3.corePana.model.inquiry.InquiryInfo;
import jp.co.transcosmos.dm3.corePana.model.inquiry.PanaHousingInquiryManageImpl;
import jp.co.transcosmos.dm3.corePana.model.inquiry.dao.HousingInquiryDAO;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquirySearchForm;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.InquiryAssessment;
import jp.co.transcosmos.dm3.corePana.vo.InquiryGeneral;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHeader;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHousing;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.FragmentResultSetExtractor;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

/**
 * 物件問合せ model の実装クラス.
 * <p>
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * 郭中レイ		2015.04.02	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class HousingInquiryDAOImpl implements HousingInquiryDAO {

	/** 問合せヘッダ情報用DAO */
	private DAO<AdminLoginInfo> adminLoginInfoDAO;

	// テーブル物理名
	/** 物件基本情報テーブル名 */
	protected static final String TABLE_HOUSING_INFO_NAME = "housing_info";

	// エイリアス
	/** 物件基本情報エイリアス */
	protected static final String TABLE_HOUSING_INFO_ALIAS = "housingInfo";

	private static final String _CHARACTER_ENCODING = "UTF-8";

	private static final String _CONTENT_TYPE = "application/octet-stream";

	private static final String _HEADER1 = "Content-Disposition";

	private static final String _HEADER2 = "attachment ;filename=";

	// log
	private static final Log log = LogFactory.getLog(HousingInquiryDAOImpl.class);


	/** データソース */
	private DataSource dataSource;

	/** 共通コード変換処理 */
	private CodeLookupManager codeLookupManager;

	// 以下、applicationContextに定義
	//
	// OR条件で検索したいこだわり条件をMapで保持する
	// 　key：こだわり条件CD、原則キーの存在有無が検索条件処理となる
	// 　value：検索処理に使用する値を保持できる
	// 複数のOR検索対象をListで保持する

	/**
	 * @param adminLoginInfoDAO セットする adminLoginInfoDAO
	 */
	public void setAdminLoginInfoDAO(DAO<AdminLoginInfo> adminLoginInfoDAO) {
		this.adminLoginInfoDAO = adminLoginInfoDAO;
	}


	/** OR条件で検索するこだわり条件CDのグルーピング定義 */
	private List<Map<String, String>> orPartSrchList =  new ArrayList<Map<String, String>>();

	/**
     * データソースを設定する。<br/>
     * <br/>
     * @param dataSource データソース
     */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
     * OR条件で検索するこだわり条件CDのグルーピング定義を設定する。<br/>
     * <br/>
     * @param orPartSrchList OR条件で検索するこだわり条件CDのグルーピング定義
     */
	public void setOrPartSrchList(List<Map<String, String>> orPartSrchList) {
		this.orPartSrchList = orPartSrchList;
	}

	/**
     * 共通コード変換処理を設定する。<br/>
     * <br/>
     * @param codeLookupManager 共通コード変換処理
     */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * 問合一覧情報を検索し、結果リストを復帰する。（一覧用）<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、物件情報を検索する。<br/>
	 * 検索結果は List<Inquiry> オブジェクトに格納され、取得したList<Inquiry>を戻り値として復帰する。<br/>
	 * <br/>
	 *
	 * @param searchForm
	 *            検索条件の格納オブジェクト
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JoinResult> housingInquirySearch(PanaInquirySearchForm searchForm) {
		JdbcTemplate template = new JdbcTemplate(dataSource);

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		// 検索SQLを生成
		createSql(searchForm, sql, params);

		log.debug("SQL : " + sql.toString());
		System.out.println("SQL : " + sql.toString());
		List<JoinResult> housingInquiry = (List<JoinResult>) template.query(sql.toString(), (Object[]) params.toArray(),
				new FragmentResultSetExtractor<Object>(new InquiryRowMapper(), searchForm
								.getStartIndex(), searchForm.getEndIndex()));
		return housingInquiry;
	}

	class InquiryRowMapper implements RowMapper<JoinResult> {
		@Override
		public JoinResult mapRow(ResultSet result, int rowNum)
				throws SQLException {
			Map<String, Object> inquiry = new HashMap<String, Object>();
			inquiry.put("inquiryId",result.getString("inquiryHeader_inquiryId"));
			inquiry.put("inquiryType",result.getString("inquiryHeader_inquiryType"));
			inquiry.put("displayHousingName",result.getString("housingInfo_displayHousingName"));
			inquiry.put("inquiryDate",result.getTimestamp("inquiryHeader_inquiryDate"));
			inquiry.put("answerStatus",result.getString("inquiryHeader_answerStatus"));

			Map<String, Object> items = new HashMap<String, Object>();
			items.put("inquiry",inquiry);

			JoinResult joinResult = new JoinResult(items);
			return joinResult;
		}
	}

	/**
	 * 問合一覧情報を検索し、結果リストを復帰する。（一覧用）<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、物件情報を検索する。<br/>
	 * 検索結果は List<Housing> オブジェクトに格納され、取得したList<Housing>を戻り値として復帰する。<br/>
	 * <br/>
	 *
	 * @param searchForm
	 *            検索条件の格納オブジェクト
	 * @param response
	 *            クライアントに返すHttpレスポンス。
	 * @param inquiryManage
	 *            物件問合せ model
	 * @param generalInquiryManage
	 *            汎用問合せ model
	 * @param assessmentInquiryManage
	 *            査定問合せ model
	 * @throws IOException
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	@Override
	public void housingInquirySearch(PanaInquirySearchForm searchForm,
			HttpServletResponse response,InquiryManage inquiryManage,InquiryManage generalInquiryManage,
			InquiryManage assessmentInquiryManage) throws IOException {
		JdbcTemplate template = new JdbcTemplate(dataSource);

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		// 検索SQLを生成
		createSql(searchForm, sql, params);

		log.debug("SQL : " + sql.toString());

		// ファイル名の設定
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dataTime = sdf.format(new Date());
		String fileName = "toiawase_" + dataTime + ".csv";

		response.setCharacterEncoding(_CHARACTER_ENCODING);
		response.setContentType(_CONTENT_TYPE);
		response.setHeader(_HEADER1, _HEADER2 + fileName);

		PrintWriter pw = response.getWriter();
		byte[] bom = {(byte)0xef, (byte)0xbb, (byte)0xbf};
		pw.write(new String(bom));

		this.writeCSVHeader(pw);

		template.query(sql.toString(), params.toArray(), new InquiryListRowCallbackHandler(response, inquiryManage, generalInquiryManage, assessmentInquiryManage, this.codeLookupManager, this.adminLoginInfoDAO, pw));
	}


	/**
	 * CSVのheaderデータを生成する。<br/>
	 *
	 * @param pw
	 */
	private void writeCSVHeader(PrintWriter pw) {
		pw.write("問合番号" + ",");
		pw.write("問い合わせ種別" + ",");
		pw.write("会員番号" + ",");
		pw.write("お名前(姓)" + ",");
		pw.write("お名前（名）" + ",");
		pw.write("フリガナ(セイ)" + ",");
		pw.write("フリガナ（メイ）" + ",");
		pw.write("メールアドレス" + ",");
		pw.write("電話番号" + ",");
		pw.write("FAX番号" + ",");
		pw.write("お問合せ日時" + ",");
		pw.write("お問合せ種別" + ",");
		pw.write("お問合せ内容" + ",");
		pw.write("セミナー名" + ",");
		pw.write("日時" + ",");
		pw.write("ステータス" + ",");
		pw.write("郵便番号" + ",");
		pw.write("住所1" + ",");
		pw.write("住所2" + ",");
		pw.write("ご希望の連絡方法" + ",");
		pw.write("連絡可能な時間帯" + ",");
		pw.write("物件番号" + ",");
		pw.write("物件名" + ",");
		pw.write("物件種別" + ",");
		pw.write("物件価格" + ",");
		pw.write("築年月" + ",");
		pw.write("間取" + ",");
		pw.write("郵便番号（物件）" + ",");
		pw.write("住所1(物件) " + ",");
		pw.write("住所2（物件）" + ",");
		pw.write("沿線1" + ",");
		pw.write("駅1" + ",");
		pw.write("バス1" + ",");
		pw.write("徒歩1" + ",");
		pw.write("沿線2" + ",");
		pw.write("駅2" + ",");
		pw.write("バス2" + ",");
		pw.write("徒歩2" + ",");
		pw.write("沿線3" + ",");
		pw.write("駅3" + ",");
		pw.write("バス3" + ",");
		pw.write("徒歩3" + ",");
		pw.write("建物面積（物件）" + ",");
		pw.write("土地面積（物件）" + ",");
		pw.write("専有面積（物件）" + ",");
		pw.write("売却物件の種別" + ",");
		pw.write("間取" + ",");
		pw.write("売却物件住所1 " + ",");
		pw.write("売却物件住所2" + ",");
		pw.write("建物面積（売却）" + ",");
		pw.write("土地面積（売却）" + ",");
		pw.write("専有面積（売却）" + ",");
		pw.write("築年数" + ",");
		pw.write("現況" + ",");
		pw.write("売却予定時期" + ",");
		pw.write("買い替え有無" + ",");
		pw.write("備考" + ",");
		pw.write("最終更新日" + ",");
		pw.write("最終更新者");
		pw.write("\r\n");
	}

	class InquiryListRowCallbackHandler implements RowCallbackHandler {
		HttpServletResponse response;
		PanaHousingInquiryManageImpl housingInquiryManage;
		GeneralInquiryManageImpl generalInquiryManage;
		AssessmentInquiryManageImpl assessmentInquiryManage;
		CodeLookupManager codeLookupManager;
		DAO<AdminLoginInfo> adminLoginInfoDAO;
		PrintWriter pw;

		public InquiryListRowCallbackHandler(HttpServletResponse response,InquiryManage housingInquiryManage,
				InquiryManage generalInquiryManage, InquiryManage assessmentInquiryManage,
				CodeLookupManager codeLookupManager, DAO<AdminLoginInfo> adminLoginInfoDAO,
				PrintWriter pw) {
			this.response = response;
			this.housingInquiryManage = (PanaHousingInquiryManageImpl)housingInquiryManage;
			this.generalInquiryManage = (GeneralInquiryManageImpl)generalInquiryManage;
			this.assessmentInquiryManage = (AssessmentInquiryManageImpl)assessmentInquiryManage;
			this.codeLookupManager = codeLookupManager;
			this.adminLoginInfoDAO = adminLoginInfoDAO;
			this.pw = pw;
		}

		@Override
		public void processRow(ResultSet rs) throws SQLException {

			try {
				PrintWriter pw = response.getWriter();
				pw = this.pw;

				this.writeCSVContent(pw, rs);
				pw.flush();

			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

		}

		private void writeCSVContent(PrintWriter pw, ResultSet rs)
				throws SQLException {
			// 問合せ情報
			InquiryInfo inquiryInfo= new InquiryInfo();
			// 物件問合せ情報取得
			HousingInquiry housingInquiry = new HousingInquiry();
			// 物件問合せ情報
			InquiryHousing inquiryHousing = new InquiryHousing();
			// 汎用問合せ情報
			InquiryInfo generalinquiryInfo = new InquiryInfo();
			// 査定問合せ情報
			InquiryInfo assessmentinquiryInfo = new InquiryInfo();
			PrefMst inquiryInfoPrefMst = new PrefMst();

			try {
				//if(PanaCommonConstant.INQUIRY_TYPE_HOUSING.equals(rs.getString("inquiryHeader_inquiryType"))){
				inquiryInfo = this.housingInquiryManage.searchHousingInquiryPk(rs.getString("inquiryHeader_inquiryId"));
				//}

				if(PanaCommonConstant.INQUIRY_TYPE_GENERAL.equals(rs.getString("inquiryHeader_inquiryType"))) {
					generalinquiryInfo = this.generalInquiryManage.searchInquiryPk(rs.getString("inquiryHeader_inquiryId"));
				}

				if(PanaCommonConstant.INQUIRY_TYPE_ASSESSMENT.equals(rs.getString("inquiryHeader_inquiryType"))) {
					assessmentinquiryInfo = this.assessmentInquiryManage.searchInquiryPk(rs.getString("inquiryHeader_inquiryId"));
				}
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}


			housingInquiry = (HousingInquiry)inquiryInfo;

			// 物件問合せ情報
			if(null != inquiryInfo.getInquiryHousing()) {
				inquiryHousing = inquiryInfo.getInquiryHousing();
			}
			if(null != inquiryInfo.getPrefMst()) {
				inquiryInfoPrefMst = inquiryInfo.getPrefMst();
			}

			// 物件基本情報 （物件基本情報＋物件詳細情報＋物件ステータス情報）
			Housing housings = null;
			if (housingInquiry.getHousings().size() > 0) {
				housings = housingInquiry.getHousings().get(0);
			}

			JoinResult housing = null;
			if(null != housings) {
				housing = housings.getHousingInfo();
			}

			// 物件基本情報
			HousingInfo housingInfo = new HousingInfo();
			if(null != housing) {
				housingInfo = (HousingInfo)housing.getItems().get("housingInfo");
			}

			// 建物基本情報
			Building building = null;
			JoinResult builds = null;
			BuildingInfo buildingInfo = new BuildingInfo();
			if(null != housings) {
				building =  housings.getBuilding();
				builds = building.getBuildingInfo();
				buildingInfo = (BuildingInfo)builds.getItems().get("buildingInfo");
			}

			// 建物詳細情報
			BuildingDtlInfo buildingDtlInfo = new BuildingDtlInfo();
			if(null != builds) {
				buildingDtlInfo = (BuildingDtlInfo)builds.getItems().get("buildingDtlInfo");
			}

			// 都道府県マスタクラス
			PrefMst prefMst = new PrefMst();
			if(null != builds) {
				prefMst = (PrefMst)builds.getItems().get("prefMst");
			}

			// 建物最寄り駅情報クラス
			BuildingStationInfo buildingStationInfo1 = new BuildingStationInfo();
			BuildingStationInfo buildingStationInfo2 = new BuildingStationInfo();
			BuildingStationInfo buildingStationInfo3 = new BuildingStationInfo();
			// 路線マスタクラス
			RouteMst routeMst1 = new RouteMst();
			RouteMst routeMst2 = new RouteMst();
			RouteMst routeMst3 = new RouteMst();
			// 駅名マスタクラス
			StationMst stationMst1 = new StationMst();
			StationMst stationMst2 = new StationMst();
			StationMst stationMst3 = new StationMst();
			// 建物最寄り駅情報
			List<JoinResult> buildingStationInfoList = null;
			if(null != building) {
				buildingStationInfoList = building.getBuildingStationInfoList();
				for(int i = 0; i < buildingStationInfoList.size(); i++) {
					if(i == 0) {
						buildingStationInfo1 = (BuildingStationInfo)buildingStationInfoList.get(i).getItems().get("buildingStationInfo");
						routeMst1 = (RouteMst)buildingStationInfoList.get(i).getItems().get("routeMst");
						stationMst1 = (StationMst)buildingStationInfoList.get(i).getItems().get("stationMst");
					}
					if(i == 1) {
						buildingStationInfo2 = (BuildingStationInfo)buildingStationInfoList.get(i).getItems().get("buildingStationInfo");
						routeMst2 = (RouteMst)buildingStationInfoList.get(i).getItems().get("routeMst");
						stationMst2 = (StationMst)buildingStationInfoList.get(i).getItems().get("stationMst");
					}
					if(i == 2) {
						buildingStationInfo3 = (BuildingStationInfo)buildingStationInfoList.get(i).getItems().get("buildingStationInfo");
						routeMst3 = (RouteMst)buildingStationInfoList.get(i).getItems().get("routeMst");
						stationMst3 = (StationMst)buildingStationInfoList.get(i).getItems().get("stationMst");
					}
				}
			}

			// 物件問合せ情報
			InquiryHeaderInfo inquiryHeaderInfo = housingInquiry.getInquiryHeaderInfo();
			// お問合せヘッダ
			InquiryHeader inquiryHeader = (InquiryHeader)inquiryHeaderInfo.getInquiryHeader();
			// 問合せ内容種別情報
			InquiryDtlInfo[] inquiryDtlInfoArr = inquiryHeaderInfo.getInquiryDtlInfos();
			InquiryDtlInfo inquiryDtlInfo = new InquiryDtlInfo();

			if(null != inquiryDtlInfoArr) {
				if(inquiryDtlInfoArr.length > 0) {
					inquiryDtlInfo = inquiryDtlInfoArr[0];
				}
			}

			// 査定問合せ情報
			InquiryAssessment inquiryAssessment = new InquiryAssessment();
			PrefMst assessmenHeadertPrefMst = new PrefMst();
			PrefMst assessmenPrefMst = new PrefMst();
			if(PanaCommonConstant.INQUIRY_TYPE_ASSESSMENT.equals(rs.getString("inquiryHeader_inquiryType"))) {
				List<JoinResult> jrAssessmentArr = assessmentinquiryInfo.getAssessmentInquiry();
				JoinResult jrAssessment = jrAssessmentArr.get(0);
				inquiryAssessment = (InquiryAssessment)jrAssessment.getItems().get("inquiryAssessment");
				// お問合せヘッダの都道府県
				assessmenHeadertPrefMst = (PrefMst)jrAssessment.getItems().get("PrefMst");
				// 査定問合せ情報の都道府県
				assessmenPrefMst = assessmentinquiryInfo.getPrefMst();

			}

			// 汎用問合せ情報
			InquiryGeneral inquiryGeneral = new InquiryGeneral();
			if(PanaCommonConstant.INQUIRY_TYPE_GENERAL.equals(rs.getString("inquiryHeader_inquiryType"))) {
				JoinResult jrGeneral = generalinquiryInfo.getGeneralInquiry().get(0);
				inquiryGeneral = (InquiryGeneral)jrGeneral.getItems().get("inquiryGeneral");
			}

			// CSV出力の項目を設定する。
			// 問合番号
			pw.write("\"" + PanaStringUtils.toString(inquiryHeader.getInquiryId()) + "\",");
			// 問い合わせ種別
			pw.write("\"" + PanaStringUtils.toString(lookupValueWithSingle("inquiry_type", inquiryHeader.getInquiryType())) + "\",");
			// 会員番号
			pw.write("\"" + PanaStringUtils.toString(inquiryHeader.getUserId()) + "\",");
			// お名前(姓)
			pw.write("\"" + PanaStringUtils.toString(inquiryHeader.getLname()) + "\",");
			// お名前（名）
			pw.write("\"" + PanaStringUtils.toString(inquiryHeader.getFname()) + "\",");
			// フリガナ(セイ)
			pw.write("\"" + PanaStringUtils.toString(inquiryHeader.getLnameKana()) + "\",");
			// フリガナ（メイ）
			pw.write("\"" + PanaStringUtils.toString(inquiryHeader.getFnameKana()) + "\",");
			// メールアドレス
			pw.write("\"" + PanaStringUtils.toString(inquiryHeader.getEmail()) + "\",");
			// 電話番号
			pw.write("\"" + PanaStringUtils.toString(inquiryHeader.getTel()) + "\",");
			// FAX番号
			pw.write("\"" + PanaStringUtils.toString(inquiryHeader.getFax()) + "\",");
			// お問合せ日時
			pw.write("\"" + PanaStringUtils.toString(formatDate(inquiryHeader.getInquiryDate(), "yyyy/MM/dd HH:mm:ss")) + "\",");
			// お問合せ種別
			//汎用問合せ
			if (PanaCommonConstant.INQUIRY_TYPE_GENERAL.equals(inquiryHeader
					.getInquiryType())) {
				pw.write("\""
						+ PanaStringUtils.toString(lookupValueWithSingle(
								"inquiry_dtl_type",
								inquiryDtlInfo.getInquiryDtlType())) + "\",");
			} else if (PanaCommonConstant.INQUIRY_TYPE_HOUSING
					.equals(inquiryHeader.getInquiryType())) {
				//物件問合せ
				pw.write("\""
						+ PanaStringUtils.toString(lookupValueWithSingle(
								"inquiry_housing_dtl_type",
								inquiryDtlInfo.getInquiryDtlType())) + "\",");
			}
			else {
				pw.write("\"\",");
			}
			// お問合せ内容
			String inquiryText = PanaStringUtils.toString(inquiryHeader.getInquiryText());
			//if(PanaCommonConstant.INQUIRY_TYPE_ASSESSMENT.equals(rs.getString("inquiryHeader_inquiryType"))) {
			//	inquiryText = "";
			//}
			pw.write("\"" + inquiryText + "\",");
			// セミナー名
			pw.write("\"" + PanaStringUtils.toString(inquiryGeneral.getEventName()) + "\",");
			// 日時
			pw.write("\"" + PanaStringUtils.toString(formatDate(inquiryGeneral.getEventDatetime(), "M月d日 H:mm")) + "\",");
			// ステータス
			pw.write("\"" + PanaStringUtils.toString(lookupValueWithSingle("inquiry_answerStatus", inquiryHeader.getAnswerStatus())) + "\",");

			if(PanaCommonConstant.INQUIRY_TYPE_GENERAL.equals(rs.getString("inquiryHeader_inquiryType"))) {
				pw.write("\"" + "\",");
				pw.write("\"" + "\",");
				pw.write("\"" + "\",");
			} else {
				// 郵便番号
				pw.write("\"" + PanaStringUtils.toString(inquiryHeader.getZip()) + "\",");
				// 住所1
				if(PanaCommonConstant.INQUIRY_TYPE_ASSESSMENT.equals(rs.getString("inquiryHeader_inquiryType")))
				{
					pw.write("\"" + PanaStringUtils.toString(assessmenHeadertPrefMst.getPrefName()) + "\",");
				}
				else {
					pw.write("\"" + PanaStringUtils.toString(inquiryInfoPrefMst.getPrefName()) + "\",");
				}
				// 住所2
				pw.write("\"" + PanaStringUtils.toString(inquiryHeader.getAddress()) + PanaStringUtils.toString(inquiryHeader.getAddressOther()) + "\",");
			}
			// ご希望の連絡方法
			if(PanaCommonConstant.INQUIRY_TYPE_GENERAL.equals(rs.getString("inquiryHeader_inquiryType"))) {
				pw.write("\"" + PanaStringUtils.toString(lookupValueWithMulti("inquiry_contact_type", inquiryGeneral.getContactType())) + "\",");
			} else if(PanaCommonConstant.INQUIRY_TYPE_ASSESSMENT.equals(rs.getString("inquiryHeader_inquiryType"))) {
				pw.write("\"" + PanaStringUtils.toString(lookupValueWithMulti("inquiry_contact_type", inquiryAssessment.getContactType())) + "\",");
			} else {
				pw.write("\"" + PanaStringUtils.toString(lookupValueWithMulti("inquiry_contact_type", inquiryHousing.getContactType())) + "\",");
			}

			// 連絡可能な時間帯
			if(PanaCommonConstant.INQUIRY_TYPE_GENERAL.equals(rs.getString("inquiryHeader_inquiryType"))) {
				pw.write("\"" + PanaStringUtils.toString(lookupValueWithMulti("inquiry_contact_time", inquiryGeneral.getContactTime())) + "\",");
			} else if(PanaCommonConstant.INQUIRY_TYPE_ASSESSMENT.equals(rs.getString("inquiryHeader_inquiryType"))) {
				pw.write("\"" + PanaStringUtils.toString(lookupValueWithMulti("inquiry_contact_time", inquiryAssessment.getContactTime())) + "\",");
			} else {
				pw.write("\"" + PanaStringUtils.toString(lookupValueWithMulti("inquiry_contact_time", inquiryHousing.getContactTime())) + "\",");
			}

			// 物件番号
			pw.write("\"" + PanaStringUtils.toString(housingInfo.getHousingCd()) + "\",");
			// 物件名
			pw.write("\"" + PanaStringUtils.toString(housingInfo.getDisplayHousingName()) + "\",");
			// 物件種別
			pw.write("\"" + PanaStringUtils.toString(lookupValueWithSingle("buildingInfo_housingKindCd", buildingInfo.getHousingKindCd())) + "\",");
			// 物件価格
			DecimalFormat df = new DecimalFormat("###,###");
			String price = "";
			if(!StringUtils.isEmpty(housingInfo.getPrice())) {
				price = df.format(housingInfo.getPrice());
			}
			pw.write("\"" + price + "\",");
			// 築年月
			pw.write("\"" + PanaStringUtils.toString(formatDate(buildingInfo.getCompDate(), "yyyy年MM月")) + "\",");
			// 間取
			pw.write("\"" + PanaStringUtils.toString(lookupValueWithSingle("layoutCd", housingInfo.getLayoutCd())) + "\",");
			// 郵便番号（物件）
			pw.write("\"" + PanaStringUtils.toString(buildingInfo.getZip()) + "\",");
			// 住所1(物件)
			pw.write("\"" + PanaStringUtils.toString(prefMst.getPrefName()) + "\",");
			// 住所2（物件）
			String addressName = PanaStringUtils.toString(buildingInfo.getAddressName()) + PanaStringUtils.toString(buildingInfo.getAddressOther1())
					+ PanaStringUtils.toString(buildingInfo.getAddressOther2());
			pw.write("\"" + PanaStringUtils.toString(addressName) + "\",");
			// 沿線1
			if(StringValidateUtil.isEmpty(routeMst1.getRouteName())) {
				pw.write("\"" + PanaStringUtils.toString(buildingStationInfo1.getDefaultRouteName()) + "\",");
			} else {
				pw.write("\"" + PanaStringUtils.toString(routeMst1.getRouteName()) + "\",");
			}
			// 駅1
			if(StringValidateUtil.isEmpty(stationMst1.getStationName())) {
				pw.write("\"" + PanaStringUtils.toString(buildingStationInfo1.getStationName()) + "\",");
			} else {
				pw.write("\"" + PanaStringUtils.toString(stationMst1.getStationName()) + "\",");
			}
			// バス1
			pw.write("\"" + PanaStringUtils.toString(buildingStationInfo1.getBusCompany()) + "\",");
			// 徒歩1
			pw.write("\"" + PanaStringUtils.toString(buildingStationInfo1.getTimeFromBusStop()) + "\",");
			// 沿線2
			if(StringValidateUtil.isEmpty(routeMst2.getRouteName())) {
				pw.write("\"" + PanaStringUtils.toString(buildingStationInfo2.getDefaultRouteName()) + "\",");
			} else {
				pw.write("\"" + PanaStringUtils.toString(routeMst2.getRouteName()) + "\",");
			}
			// 駅2
			if(StringValidateUtil.isEmpty(stationMst2.getStationName())) {
				pw.write("\"" + PanaStringUtils.toString(buildingStationInfo2.getStationName()) + "\",");
			} else {
				pw.write("\"" + PanaStringUtils.toString(stationMst2.getStationName()) + "\",");
			}
			// バス2
			pw.write("\"" + PanaStringUtils.toString(buildingStationInfo2.getBusCompany()) + "\",");
			// 徒歩2
			pw.write("\"" + PanaStringUtils.toString(buildingStationInfo2.getTimeFromBusStop()) + "\",");
			// 沿線3
			if(StringValidateUtil.isEmpty(routeMst3.getRouteName())) {
				pw.write("\"" + PanaStringUtils.toString(buildingStationInfo3.getDefaultRouteName()) + "\",");
			} else {
				pw.write("\"" + PanaStringUtils.toString(routeMst3.getRouteName()) + "\",");
			}
			// 駅3
			if(StringValidateUtil.isEmpty(stationMst3.getStationName())) {
				pw.write("\"" + PanaStringUtils.toString(buildingStationInfo3.getStationName()) + "\",");
			} else {
				pw.write("\"" + PanaStringUtils.toString(stationMst3.getStationName()) + "\",");
			}
			// バス3
			pw.write("\"" + PanaStringUtils.toString(buildingStationInfo3.getBusCompany()) + "\",");
			// 徒歩3
			pw.write("\"" + PanaStringUtils.toString(buildingStationInfo3.getTimeFromBusStop()) + "\",");
			// 建物面積（物件）
			String buildingArea = PanaStringUtils.toString(buildingDtlInfo.getBuildingArea());
			if(!StringUtils.isEmpty(buildingArea)) {
				buildingArea = buildingArea + "㎡";
			}
			pw.write("\"" + buildingArea + "\",");
			// 土地面積（物件）
			String landArea = PanaStringUtils.toString(housingInfo.getLandArea());
			if(!StringUtils.isEmpty(landArea)) {
				landArea = landArea + "㎡";
			}
			pw.write("\"" + landArea + "\",");
			// 専有面積（物件）
			String persoalArea = PanaStringUtils.toString(housingInfo.getPersonalArea());
			if(!StringUtils.isEmpty(persoalArea)) {
				persoalArea = persoalArea + "㎡";
			}
			pw.write("\"" + persoalArea + "\",");
			// 売却物件の種別
			pw.write("\"" + PanaStringUtils.toString(lookupValueWithSingle("buildingInfo_housingKindCd", inquiryAssessment.getBuyHousingType())) + "\",");
			// 間取
			pw.write("\"" + PanaStringUtils.toString(lookupValueWithSingle("layoutCd", inquiryAssessment.getLayoutCd())) + "\",");
			// 売却物件住所1
			pw.write("\"" + PanaStringUtils.toString(assessmenPrefMst.getPrefName()) + "\",");
			// 売却物件住所2
			String addrName = PanaStringUtils.toString(inquiryAssessment.getAddress());
			addrName = addrName + PanaStringUtils.toString(inquiryAssessment.getAddressOther());
			pw.write("\"" + PanaStringUtils.toString(addrName) + "\",");
			// 建物面積（売却）
			pw.write("\"" + calcTsubo(inquiryAssessment.getBuildingArea(),inquiryAssessment.getBuildingAreaCrs()) + "\",");
			// 土地面積（売却）
			pw.write("\"" + calcTsubo(inquiryAssessment.getLandArea(), inquiryAssessment.getLandAreaCrs()) + "\",");
			// 専有面積（売却）
			String persoalArea2 = PanaStringUtils.toString(inquiryAssessment.getPersonalArea());
			if(!StringUtils.isEmpty(persoalArea2)) {
				persoalArea2 = persoalArea2 + "㎡";
			}
			pw.write("\"" + persoalArea2 + "\",");
			// 築年数
			String buildAge = "";
			if(null != inquiryAssessment.getBuildAge()) {
				buildAge  = inquiryAssessment.getBuildAge().toString()+ "年";
			}
			pw.write("\"" +  buildAge + "\",");
			// 現況
			pw.write("\"" + PanaStringUtils.toString(lookupValueWithSingle("inquiry_present_cd", inquiryAssessment.getPresentCd())) + "\",");
			// 売却予定時期
			pw.write("\"" + PanaStringUtils.toString(lookupValueWithSingle("inquiry_buy_time_cd", inquiryAssessment.getBuyTimeCd())) + "\",");
			// 買い替え有無
			pw.write("\"" + PanaStringUtils.toString(lookupValueWithSingle("replacement_flg", inquiryAssessment.getReplacementFlg())) + "\",");
			// 備考
			pw.write("\"" + PanaStringUtils.toString(inquiryHeader.getAnswerText()) + "\",");
			// 最終更新日
			pw.write("\"" + PanaStringUtils.toString(formatDate(inquiryHeader.getUpdDate(), "yyyy/MM/dd HH:mm:ss")) + "\",");
			// 最終更新者
			if (inquiryHeader.getUserId() == null) {
				pw.write("\"\"");
			} else {
				if (inquiryHeader.getUserId().equals(inquiryHeader.getUpdUserId())) {
					pw.write("\"本人\"");
				} else {
					AdminLoginInfo adminLoginInfo = this.adminLoginInfoDAO.selectByPK(inquiryHeader.getUpdUserId());
					if(null == adminLoginInfo) {
						adminLoginInfo = new AdminLoginInfo();
					}
					pw.write("\"" + PanaStringUtils.toString(adminLoginInfo.getUserName()) + "\"");
				}
			}
			pw.write("\r\n");
		}

		/**
		 * calcTsubo
		 *
		 * @param value
		 * @param crs
		 * @return Object
		 */
		private Object calcTsubo(BigDecimal value, String crs) {
			String returnValue = "";

			if (null == value) {
				return "";
			}

			if("00".equals(crs)) {
				returnValue = value.toString() + "坪";
			} else if("01".equals(crs)) {
				returnValue = value.toString() + "㎡";
			}

			return returnValue;
		}

		/**
		 * lookupValueWithMulti
		 *
		 * @param lookupName
		 * @param key
		 * @return
		 */
		private String lookupValueWithMulti(String lookupName, String key) {
			if (StringValidateUtil.isEmpty(key)) {
				return "";
			}
			String[] keys = key.split(",");
			StringBuffer resValue = new StringBuffer();

			for(int i = 0; i < keys.length; i++) {
				String value = this.codeLookupManager.lookupValue(lookupName, keys[i]);
				resValue.append(value);
				if(i < keys.length - 1 ) {
					resValue.append(",");
				}
			}

			String value = resValue.toString();

			if (value == null) {
				return "";
			}
			return value;
		}

		/**
		 * lookupValueWithSingle
		 *
		 * @param lookupName
		 * @param key
		 * @return
		 */
		private String lookupValueWithSingle(String lookupName, String key) {
			if (StringValidateUtil.isEmpty(key)) {
				return "";
			}
			String value = this.codeLookupManager.lookupValue(lookupName, key);
			if (value == null) {
				return "";
			}
			return value;
		}

		/**
		 * formatDate
		 *
		 * @param date
		 * @param formatCode
		 * @return
		 */
		private String formatDate(Date date, String formatCode) {
			String str = "";
			if (date != null) {
				SimpleDateFormat sdf = new SimpleDateFormat(formatCode);
				str = sdf.format(date);
			}
			return str;
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
	 * @param searchForm 検索条件の格納オブジェクト
	 */
	protected void createSql(PanaInquirySearchForm searchForm, StringBuffer sql, List<Object> params) {
			sql.append(" SELECT ");
			sql.append(" inquiry_id AS inquiryHeader_inquiryId, ");
			sql.append(" inquiry_type AS inquiryHeader_inquiryType, ");
			sql.append(" display_housing_name AS housingInfo_displayHousingName, ");
			sql.append(" inquiry_date AS inquiryHeader_inquiryDate, ");
			sql.append(" answer_status AS inquiryHeader_answerStatus ");
			sql.append(" FROM ( ");



		// 問合種別の設定
		String[] inquiryType = new String[3];
		if(!StringValidateUtil.isEmpty(searchForm.getKeyHousingCd()) ||
			!StringValidateUtil.isEmpty(searchForm.getKeyDisplayHousingName())) {
			inquiryType[0] = "00";
		} else {
			if(null == searchForm.getKeyInquiryType()) {
				inquiryType[0] = "00";
				inquiryType[1] = "02";
				inquiryType[2] = "01";
			} else {
				for(int i = 0; i < searchForm.getKeyInquiryType().length; i++) {
					inquiryType[i] = searchForm.getKeyInquiryType()[i];
				}
			}
		}

		for(int i = 0; i < inquiryType.length; i++) {
			if(null != inquiryType[i]) {
				if(i > 0) {
					sql.append(" UNION ALL ");
				}
				createSelect(sql, inquiryType[i]);
				createFrom(sql, inquiryType[i]);
				createWhere(searchForm, sql, inquiryType[i], params);
			}
		}

		sql.append(" ) AS inquiry ");
		sql.append(" ORDER BY inquiryHeader_inquiryId  DESC ");
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
	 * @param sql 生成したSQL文を追加するバッファ
	 * @param inquiryType 検索条件の問合種別
	 */
	protected void createSelect(StringBuffer sql, String inquiryType) {
		sql.append(" SELECT ");
		sql.append(" inquiryHeader.inquiry_id, ");
		sql.append(" inquiryHeader.inquiry_type, ");
		// 物件問合せを選択した場合
		if(PanaCommonConstant.INQUIRY_TYPE_HOUSING.equals(inquiryType)) {
			sql.append(" housingInfo.display_housing_name, ");
		}

		// 査定問合せを選択した場合
		if(PanaCommonConstant.INQUIRY_TYPE_ASSESSMENT.equals(inquiryType)) {
			sql.append(" NULL AS display_housing_name, ");
		}

		// 汎用問合せを選択した場合
		if(PanaCommonConstant.INQUIRY_TYPE_GENERAL.equals(inquiryType)) {
			sql.append(" NULL AS display_housing_name, ");
		}

		sql.append(" inquiryHeader.inquiry_date, ");
		sql.append(" inquiryHeader.answer_status ");
	}

	/**
	 * From句の文字列を生成する<br/>
	 * 使用するテーブル名、別名は定数を定義して記述すること<br/>
	 * （コーディング簡素化、タイプミス防止）<br/>
	 * <br/>
	 *
	 * @param sql 生成したSQL文を追加するバッファ
	 * @param inquiryType 検索条件の問合種別
	 */
	protected void createFrom(StringBuffer sql, String inquiryType) {
		sql.append(" FROM ");

		// 物件問合せを選択した場合
		if(PanaCommonConstant.INQUIRY_TYPE_HOUSING.equals(inquiryType)) {
			sql.append(" inquiry_header inquiryHeader ");
			sql.append(" LEFT JOIN inquiry_housing inquiryHousing ");
			sql.append(" ON inquiryHeader.inquiry_id = inquiryHousing.inquiry_id ");
			sql.append(" LEFT JOIN housing_info housingInfo ");
			sql.append(" ON housingInfo.sys_housing_cd = inquiryHousing.sys_housing_cd ");
		}

		// 査定問合せを選択した場合
		if(PanaCommonConstant.INQUIRY_TYPE_ASSESSMENT.equals(inquiryType)) {
			sql.append(" inquiry_header inquiryHeader ");
		}

		// 汎用問合せを選択した場合
		if(PanaCommonConstant.INQUIRY_TYPE_GENERAL.equals(inquiryType)) {
			sql.append(" inquiry_header inquiryHeader ");
			sql.append(" LEFT JOIN inquiry_dtl_info  inquiryDtlInfo ");
			sql.append(" ON inquiryDtlInfo.inquiry_id = inquiryHeader.inquiry_id ");
		}

	}


	/**
	 * Where句の文字列を生成する<br/>
	 * 検索条件ごとに、「AND～」となる文字列を生成するメソッドを呼び出している<br/>
	 * <br/>
	 *
	 * @param searchForm 検索条件の格納オブジェクト
	 * @param sql 生成したSQL文を追加するバッファ
	 * @param inquiryType 検索条件の問合種別
	 * @param params
	 *            SQLで使用するバインドパラメータのリストオブジェクト
	 */
	protected void createWhere(PanaInquirySearchForm searchForm, StringBuffer sql, String inquiryType, List<Object> params) {
		sql.append(" WHERE ");
		sql.append(" 1 =1 ");

		// 物件問合せを選択した場合
		if(PanaCommonConstant.INQUIRY_TYPE_HOUSING.equals(inquiryType)) {
			// 物件番号又は物件名を入力した場合
			if(!StringValidateUtil.isEmpty(searchForm.getKeyHousingCd()) ||
					!StringValidateUtil.isEmpty(searchForm.getKeyDisplayHousingName())) {
				// 物件番号
				if(!StringValidateUtil.isEmpty(searchForm.getKeyHousingCd())) {
					sql.append(" AND housingInfo.housing_cd = ? ");
					params.add(searchForm.getKeyHousingCd());
				}

				// 物件名
				if(!StringValidateUtil.isEmpty(searchForm.getKeyDisplayHousingName())) {
					sql.append(" AND housingInfo.display_housing_name LIKE ? ");
					params.add("%" + searchForm.getKeyDisplayHousingName() + "%");
				}
			}
			sql.append(" AND inquiryHeader.inquiry_type = '00' ");
		}

		// 査定問合せを選択した場合
		if(PanaCommonConstant.INQUIRY_TYPE_ASSESSMENT.equals(inquiryType)) {
			sql.append(" AND inquiryHeader.inquiry_type = '02' ");
		}

		// 汎用問合せを選択した場合
		if(PanaCommonConstant.INQUIRY_TYPE_GENERAL.equals(inquiryType)) {
			sql.append(" AND inquiryHeader.inquiry_type = '01' ");

			if(null != searchForm.getKeyInquiryDtlType()) {
				StringBuffer sqlIn = new StringBuffer();
				String[] inquiryDtlType = searchForm.getKeyInquiryDtlType();

				for (int i = 0; i < inquiryDtlType.length; i++) {
					if (inquiryDtlType[i].trim().length() > 0) {
						sqlIn.append(",? ");
						params.add(inquiryDtlType[i]);
					}
				}

				if (sqlIn.length() > 0) {
					// 最初のカンマを削除する
					String strComma = ",";
					sqlIn.delete(sqlIn.indexOf(strComma), sqlIn.indexOf(strComma) + strComma.length());
					sql.append(" AND inquiryDtlInfo.inquiry_dtl_type IN  (" + sqlIn.toString() + ")");
				}
			}
		}

		// 会員番号
		if(!StringValidateUtil.isEmpty(searchForm.getKeyUserId())) {
			sql.append(" AND inquiryHeader.user_id LIKE ? ");
			params.add("%" + searchForm.getKeyUserId() + "%");
		}

		// メールアドレス
		if(!StringValidateUtil.isEmpty(searchForm.getKeyEmail())) {
			sql.append(" AND inquiryHeader.email LIKE ? ");
			params.add("%" + searchForm.getKeyEmail() + "%");
		}

		// 問合日時開始日
		if(!StringValidateUtil.isEmpty(searchForm.getKeyInquiryDateStart())) {
			sql.append(" AND inquiryHeader.inquiry_date >= ? ");
			params.add(searchForm.getKeyInquiryDateStart() + " 00:00:00");
		}

		// 問合日時終了日
		if(!StringValidateUtil.isEmpty(searchForm.getKeyInquiryDateEnd())) {
			sql.append(" AND inquiryHeader.inquiry_date <= ? ");
			params.add(searchForm.getKeyInquiryDateEnd() + " 23:59:59");
		}

		// ステータス
		if(!StringValidateUtil.isEmpty(searchForm.getKeyAnswerStatus())) {
			sql.append(" AND inquiryHeader.answer_status = ? ");
			params.add(searchForm.getKeyAnswerStatus());
		}

		// 物件問合番号
		if(!StringValidateUtil.isEmpty(searchForm.getKeyInquiryId())) {
			sql.append(" AND inquiryHeader.inquiry_id = ? ");
			params.add(searchForm.getKeyInquiryId());
		}

	}
}
