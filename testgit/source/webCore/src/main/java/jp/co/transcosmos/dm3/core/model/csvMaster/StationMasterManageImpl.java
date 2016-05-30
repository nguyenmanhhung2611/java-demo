package jp.co.transcosmos.dm3.core.model.csvMaster;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.transcosmos.dm3.core.model.CsvMasterManage;
import jp.co.transcosmos.dm3.core.model.csvMaster.dao.StationSortOrderUpdateDAO;
import jp.co.transcosmos.dm3.core.model.csvMaster.form.CsvUploadFormFactory;
import jp.co.transcosmos.dm3.core.model.csvMaster.form.StationCsvForm;
import jp.co.transcosmos.dm3.core.util.CsvStreamReader;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.RrMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.core.vo.StationRouteInfo;
import jp.co.transcosmos.dm3.core.vo.UnregistRouteMst;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.validation.NotExistsInCriteriaValidation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * 駅マスタメンテナンス用 Model クラス.
 * <p>
 * ヴァル研が提供する駅情報CSV を取り込み、鉄道会社マスタ、路線マスタ、駅マスタ、
 * 駅・路線対応表を更新する。<br/>
 * <ul>
 * <li>登録除外路線マスタに設定された路線CD は、駅マスタCSV の取り込み対象外とする。</li>
 * <li>駅情報CSV の会社名、路線名、駅名は、stationAndRouteReplace.xml　に置換ルールを設定
 *     する事により、固定値に変換して取込む事ができる。<br/>
 *     （詳細は、各置換メソッドの説明を参照。）</li>
 * <li>CSV に存在しない鉄道会社、路線、駅の情報は、鉄道会社マスタ、路線マスタ、駅マスタ、駅・路線対応表
 *     に既に存在する場合は削除する事。</li>
 * <li>存在しない鉄道会社名が CSV に存在する場合、鉄道会社CD を採番して鉄道会社マスタに登録する。</li>
 * <li>CSV のデータに該当する路線マスタ、駅マスタ、駅・路線対応表のデータが既に存在する場合は更新し、
 *     存在しない場合は追加する。</li>
 * <li>CSV から取得できない鉄道会社マスタ、路線マスタの表示順データ、既存データの表示順 + 主キー値で
 *     再度ソートしてから値を連番で再設定する。</li>
 * </ul>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.05	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスはシングルトンで DI コンテナに定義されるので、スレッドセーフである事。<br/>
 * 
 */
public class StationMasterManageImpl implements CsvMasterManage {

	private static final Log log = LogFactory.getLog(StationMasterManageImpl.class);
	
	/** VO のインスタンスを生成する場合のファクトリー */
	protected ValueObjectFactory valueObjectFactory;

	/** 登録除外路線マスタ DAO */
	protected DAO<UnregistRouteMst> unregistRouteMstDAO;

	/** 鉄道会社マスタ DAO */
	protected DAO<RrMst> rrMstDAO;
	
	/** 路線マスタ DAO */
	protected DAO<RouteMst> routeMstDAO;

	/** 駅名マスタ DAO */
	protected DAO<StationMst> stationMstDAO;
	
	/** 駅・路線対応表 DAO */
	protected DAO<StationRouteInfo> stationRouteInfoDAO;
	
	/** 都道府県マスタ DAO */
	protected DAO<PrefMst> prefMstDAO;

	/** 路線マスタ、鉄道会社マスタの表示順更新用 DAO */
	protected StationSortOrderUpdateDAO stationSortOrderUpdateDAO;
	
	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;

	/** 駅情報CSV の加工した値を格納する Form を生成する Factory */
	protected CsvUploadFormFactory formFactory;
	
	/** CSV エンコード文字列 */
	protected String csvEncode = "ms932";

	/** タイトル行をスキップするか （true の場合、１行目をスキップ） */
	private boolean titleSkip = true;



	/**
	 * バリーオブジェクトのインスタンスを生成するファクトリーを設定する。<br/>
	 * <br/>
	 * @param valueObjectFactory バリーオブジェクトのファクトリー
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}
	
	/**
	 * 登録除外路線マスタ DAO を設定する。<br/>
	 * <br/>
	 * @param unregistRouteMstDAO 登録除外路線マスタ DAO
	 */
	public void setUnregistRouteMstDAO(DAO<UnregistRouteMst> unregistRouteMstDAO) {
		this.unregistRouteMstDAO = unregistRouteMstDAO;
	}

	/**
	 * 鉄道会社マスタ DAO を設定する。<br/>
	 * <br/>
	 * @param rrMstDAO 鉄道会社マスタ DAO
	 */
	public void setRrMstDAO(DAO<RrMst> rrMstDAO) {
		this.rrMstDAO = rrMstDAO;
	}

	/**
	 * 路線マスタ DAO を設定する。<br/>
	 * <br/>
	 * @param routeMstDAO　路線マスタ　DAO
	 */
	public void setRouteMstDAO(DAO<RouteMst> routeMstDAO) {
		this.routeMstDAO = routeMstDAO;
	}

	/**
	 * 駅名マスタ DAO を設定する。<br/>
	 * <br/>
	 * @param stationMstDAO 駅名マスタ DAO
	 */
	public void setStationMstDAO(DAO<StationMst> stationMstDAO) {
		this.stationMstDAO = stationMstDAO;
	}
	
	/**
	 * 駅・路線対応表 DAO を設定する。<br/>
	 * <br/>
	 * @param stationRouteInfo　駅・路線対応表　DAO
	 */
	public void setStationRouteInfoDAO(DAO<StationRouteInfo> stationRouteInfo) {
		this.stationRouteInfoDAO = stationRouteInfo;
	}
	
	/**
	 * 都道府県マスタ DAO を設定する。<br/>
	 * <br/>
	 * @param prefMstDAO　都道府県マスタ　DAO
	 */
	public void setPrefMstDAO(DAO<PrefMst> prefMstDAO) {
		this.prefMstDAO = prefMstDAO;
	}

	/**
	 * 鉄道会社マスタ、路線マスタの表示順更新用 DAO を設定する。<br/>
	 * <br/>
	 * @param stationSortOrderUpdateDAO 鉄道会社マスタ、路線マスタの表示順更新用 DAO
	 */
	public void setStationSortOrderUpdateDAO(StationSortOrderUpdateDAO stationSortOrderUpdateDAO) {
		this.stationSortOrderUpdateDAO = stationSortOrderUpdateDAO;
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
	 * 駅情報CSV の加工した値を格納する Form を生成する Factory を設定する。<br/>
	 * <br/>
	 * @param formFactory Form の Factory
	 */
	public void setFormFactory(CsvUploadFormFactory formFactory) {
		this.formFactory = formFactory;
	}

	/**
	 * CSV の文字コードを変更する場合、このプロパティに設定する。<br/>
	 * 未設定の場合、初期値として ms932 が使用される。<br/>
	 * <br/>
	 * @param csvEncode CSV ファイルの文字コード
	 */
	public void setCsvEncode(String csvEncode) {
		this.csvEncode = csvEncode;
	}

	/**
	 * デフォルトでは、1行目をタイトル行としてスキップする。<br/>
	 * １行目からデータが始まる場合、このプロパティを false に設定する。<br/>
	 * <br/>
	 * @param titleSkip true の場合、１行目をスキップ（デフォルト）、false の場合、１行目からロード
	 */
	public void setTitleSkip(boolean titleSkip) {
		this.titleSkip = titleSkip;
	}

	
	
	/**
	 * 駅マスタCSV の取り込み処理<br/>
	 * 正常終了した場合、戻り値のエラーオブジェクトのリスト件数は 0 件になる。<br/>
	 * <br/>
	 * @param inputStream リクエストで送信された File の InputStream オブジェクト
	 * @param editUserId ログインユーザーＩＤ（更新情報用）
	 * @param errors エラーオブジェクトのリスト
	 * 
	 * @return 正常時 true、CSV のバリデーションでエラーがある場合 false
	 * 
	 * @throws IOException
	 */
	@Override
	public boolean csvLoad(InputStream inputStream, String editUserId, List<ValidationFailure> errors)
			throws IOException {
		
		// データ登録用 Map オブジェクトの初期化

		// 鉄道会社情報に追加する情報を格納する Map オブジェクトを生成する。
		// （Key = 鉄道会社名、Value = 鉄道会社オブジェクト）
		Map<String, RrMst> addRrMstMap = new HashMap<>();

		// 路線マスタに追加する情報を格納する Map オブジェクトを生成する。
		// （Key = 路線CD、Value = 路線マスタオブジェクト）
		Map<String, RouteMst> addRouteMstMap = new HashMap<>();

		// 駅マスタに追加する情報を格納する Map オブジェクトを生成する。
		// （Key = 駅CD、Value = 駅マスタオブジェクト）
		Map<String, StationMst> addStationMstMap = new HashMap<>();

		// 駅・路線対応表に追加する情報を格納する Map オブジェクトを生成する。
		// （Key = 路線CD + 駅CD、Value = 駅・路線対応表オブジェクト）
		Map<String, StationRouteInfo> addStationRouteInfoMap = new HashMap<>();



		// 鉄道会社の既存情報を取得
		
		// 既存の鉄道会社マスタ情報の Map と、最大の鉄道会社CD の最大値を取得する。
		// 鉄道会社CD の最大値は、新たな鉄道会社を登録する際のキー値生成に使用する。
		// （Key = 鉄道会社名、 Value = 鉄道会社マスタオブジェクト）
		Map<String, RrMst> rrMstMap = new HashMap<>();
		String maxRrCd = getRrMst(rrMstMap);

		// 既存の鉄道会社情報から、鉄道会社CD の Set オブジェクトを作成する。
		// この値は、新たな鉄道会社CD を採番する時に、一意な値をチェックするのに使用する。
		Set<String> allRrCdSet = new HashSet<>();
		for (Entry<String, RrMst> e : rrMstMap.entrySet()){
			allRrCdSet.add(e.getValue().getRrCd());
		}


		// 路線情報の既存情報を取得

		// 既存の路線マスタから、路線CD の表示順を格納した Map オブジェクトを取得する。
		// （Key = 路線CD、 Value = 表示順）
		Map<String, Integer> routeOrderMap = getRouteOrder();

		// 取り込み対象外とする路線CD の Set を取得する。
		Set<String> unregistRouteCdSet = getUnregistRouteCd();
		


		// 現在のシステム日付を取得（ＤＢ更新時のタイムスタンプとして使用。）
		Date currentDate = new Date();



		// アップロードされた CSV ファイルを読込む
		InputStreamReader stream = new InputStreamReader(inputStream, this.csvEncode);

		try (CsvStreamReader reader = new CsvStreamReader(stream)){

			int listCnt = 0;				// 行番号

			// 1行目はタイトルなので読み飛ばす設定をして読込みを開始する。
			if (this.titleSkip){
				reader.setFirstSkip(true);
				listCnt = 1;				// タイトル行を読み飛ばす場合、１からスタートする。
			}
			reader.open();


			Map<Integer, String> csvData;	// 取得した CSV DATA
			while ((csvData = reader.getRowData()) != null){

				++listCnt;

				// 項目数が正しくない場合
				if(csvData.size() != 17){
					errors.add(new ValidationFailure("csvListCnt", String.valueOf(listCnt), null,null));
					errors.add(new ValidationFailure("csvFormat", null, null,null));
					return false;
				}


				// CSV の値を設定した Form を生成する。
				StationCsvForm csvForm = createForm(csvData);

				// 鉄道会社名を特定のルールで変換処理する。
				replaceRrName(csvForm);

				// 路線名を特定のルールで変換処理する。
				replaceRouteName(csvForm);

				// 「路線名カッコ付」を特定のルールで変換処理する。
				replaceRouteNameFull(csvForm);
				
				// 「路線名鉄道会社付き」を特定のルールで変換処理する。
				replaceRouteNameRr(csvForm);


				// 既存データ、または追加予定データに該当する鉄道会社情報が存在する場合、鉄道会社CD、
				// 鉄道会社の表示順を Form に設定する。
				// また、既存データが存在しない場合は鉄道会社CD を新たに採番して Form に設定する。
				// （その場合、表示順は null が設定される。）
				maxRrCd = confRrCdAndRrSortOrder(csvForm, rrMstMap, addRrMstMap, allRrCdSet, maxRrCd);

				// 最大値となる鉄道会社CD が復帰される。
				// もし採番できない場合は null が復帰される。
				if (maxRrCd == null){
					// 鉄道会社コード オーバーフロー
					errors.add(new ValidationFailure("csvListCnt", null, null,null));
					return false;
				}


				// 既存路線マスタが存在する場合、路線マスタの表示順を Form へ設定する。
				confRouteSortOrder(routeOrderMap, csvForm);
				
				
				// フォーマットチェック
				List<ValidationFailure> subErrors = new ArrayList<ValidationFailure>();
				csvForm.validate(subErrors);


				// 都道府県CD が存在するかのバリデーションを実行
				DAOCriteria prefCri = new DAOCriteria();
				prefCri.addWhereClause("prefCd", csvForm.getPrefCd(), DAOCriteria.EQUALS, false);

				ValidationChain valPrefCd = new ValidationChain("stationCsv.prefCd", csvForm.getPrefCd());
				valPrefCd.addValidation(new NotExistsInCriteriaValidation(this.prefMstDAO, prefCri));
				valPrefCd.validate(subErrors);
				
				if (subErrors.size() > 0) {
					errors.add(new ValidationFailure("csvListCnt", String.valueOf(listCnt), null,null));
					errors.addAll(subErrors);
					return false;
				}			


				// 登録除外路線マスタテーブルに存在する場合は除外する
				if (!unregistRouteCdSet.contains(csvForm.getRouteCd())) {

					// 鉄道会社マスタ
					// CSV には鉄道会社CD が存在しないので、鉄道会社名で存在するかをチェックする。
					// 追加予定 Map に鉄道会社名が存在しない場合、鉄道会社情報を追加する。
					addRrMstData(addRrMstMap, csvForm, editUserId, currentDate);

					// 駅・路線対応表
					// 路線CD + 駅CD をキーとして、駅・路線対応表の追加対象 Map に存在するかをチェックする。
					// 存在しない場合は Map に追加する。
					addStationRouteData(addStationRouteInfoMap, csvForm, editUserId, currentDate);

					// 路線マスタ
					// 路線CD をキーとして、路線マスタの追加 Map に存在するかをチェックする。
					// 存在しない場合は Map に追加する。
					addRouteMstData(addRouteMstMap, csvForm, editUserId, currentDate);

					
					// 駅名マスタ
					// 駅CD をキーとして、駅マスタの追加 Map に存在するかをチェックする。
					// 存在しない場合は Map に追加する。
					addStationMstData(addStationMstMap, csvForm, editUserId, currentDate);

				}

				if (subErrors.size() > 0) {
					errors.add(new ValidationFailure("csvListCnt", String.valueOf(listCnt), null,null));
					errors.addAll(subErrors);
					return false;
				}			
			}
		}

		// 登録データが1件もない場合
		if(addStationMstMap.size()==0){
			errors.add(new ValidationFailure("csvNoData", null, null,null));
			return false;
		}


		// 全データ削除 ※子テーブルから削除!
		DAOCriteria dummyCri = new DAOCriteria();
		this.stationRouteInfoDAO.deleteByFilter(dummyCri);
		this.stationMstDAO.deleteByFilter(dummyCri);
		this.routeMstDAO.deleteByFilter(dummyCri);
		this.rrMstDAO.deleteByFilter(dummyCri);

		// 登録　※親テーブルから登録!
		this.rrMstDAO.insert((RrMst[])addRrMstMap.values().toArray(new RrMst[addRrMstMap.size()]));
		this.routeMstDAO.insert((RouteMst[])addRouteMstMap.values().toArray(new RouteMst[addRouteMstMap.size()]));
		this.stationMstDAO.insert((StationMst[])addStationMstMap.values().toArray(new StationMst[addStationMstMap.size()]));
		this.stationRouteInfoDAO.insert((StationRouteInfo[])addStationRouteInfoMap.values().toArray(new StationRouteInfo[addStationRouteInfoMap.size()]));

		// 並び順を更新(既存の並び順＋コードでソートして振りなおす)
		Object[] updData = new Object[]{currentDate, editUserId};
		this.stationSortOrderUpdateDAO.updateRrMstSortOrder(updData);
		this.stationSortOrderUpdateDAO.updateRouteMstSortOrder(updData);

		return true;
	}



	/**
	 * 取り込み対象外とする路線CD を設定した Set オブジェクトを取得する。<br/>
	 * 登録除外路線マスタに登録されている路線CD を取得し、Set オブジェクトに格納して復帰する。<br/>
	 * <br/>
	 * @return　取り込み対象外とする路線CD が格納された Map オブジェクト
	 */
	protected Set<String> getUnregistRouteCd(){

		// 登録除外路線マスタから全件取得
		List<UnregistRouteMst> unregistRouteMstList = this.unregistRouteMstDAO.selectByFilter(null);

		Set<String> unregistRouteCdSet = new HashSet<>();

		// 取得した路線CD を Set に格納して復帰。
		for (UnregistRouteMst unregistRoute : unregistRouteMstList){
			unregistRouteCdSet.add(unregistRoute.getRouteCd());
		}

		return unregistRouteCdSet;
	}



	/**
	 * 既に登録されている鉄道会社情報の Map オブジェクトを取得する。<br/>
	 * <br/>
	 * Key = 鉄道会社名、 Value = 鉄道会社マスタオブジェクト
	 * <br/>
	 * @param rrMstMap 鉄道会社情報の Map オブジェクト
	 * @return 最大鉄道会社CD
	 */
	protected String getRrMst(Map<String, RrMst> rrMstMap) {

		// 最大鉄道会社CD
		String maxRrCd = "000";

		// 鉄道会社マスタを読み込み、Mapに配置
		// 新たな鉄道会社を登録する際の鉄道会社CD は、登録済鉄道会社CD の最大値 + 1 で
		// 算出する。
		// その為、既存の鉄道会社マスタからデータを取得する際には鉄道会社CD でソートした結果
		// を取得する。　（
		DAOCriteria criteria = new DAOCriteria();
		criteria.addOrderByClause("rrCd");
		List<RrMst> rrMstList = this.rrMstDAO.selectByFilter(null);

		for (RrMst rrMst : rrMstList){
			maxRrCd = rrMst.getRrCd();
			rrMstMap.put(rrMst.getRrName(), rrMst);
		}
		
		return maxRrCd;
	}
	
	
	
	/**
	 * 既存路線CD （路線マスタ）の表示順の Map を取得する。<br/>
	 * <br/>
	 * Key = 路線CD、 Value = 表示順
	 * <br/>
	 * @return 路線CD の表示順を格納した Map オブジェクト
	 */
	protected Map<String, Integer> getRouteOrder(){

		Map<String, Integer> routeOrderMap = new HashMap<String, Integer>();

		// 路線マスタを取得する。
		List<RouteMst> routeMstList = this.routeMstDAO.selectByFilter(null);

		for (RouteMst routeMst : routeMstList){
			
			// note
			// 既存コードでは、路線CD ではなく、鉄道会社CD を Key として設定していた。
			// この Map を実際に使用している箇所では路線CD で参照していたので、既存バグに思える。
			routeOrderMap.put(routeMst.getRouteCd(), routeMst.getSortOrder());
		}

		return routeOrderMap;
	}
	
	
	
	/**
	 * 駅情報CSV の値を設定した Form オブジェクト生成する。<br/>
	 * <br/>
	 * @param csvData CSV データー
	 * @return　Form オブジェクト
	 */
	protected StationCsvForm createForm(Map<Integer, String> csvData){
		
		StationCsvForm form = this.formFactory.createStationCsvForm();

		form.setRouteCd(csvData.get(1));						// 路線コード
		form.setRrName(csvData.get(2));							// 鉄道会社名
		form.setRouteNameFull(csvData.get(4));					// 路線名括弧付き
		form.setRouteNameRr(csvData.get(7));					// 路線名括弧付き、鉄道会社付き
		form.setStationCd(csvData.get(10));						// 駅コード
		form.setStationNameFull(csvData.get(11));				// 駅名括弧あり
		form.setPrefCd(csvData.get(15));						// 都道府県コード
		form.setStationRouteDispOrder(csvData.get(13));			// 停車順1
		form.setRouteName(splitKakko(form.getRouteNameFull()));	// 路線名

		return form;
	}



	/**
	 * 特定の条件に該当する鉄道会社名を置換する。<br/>
	 * ・路線CD が "J" から始まる場合は鉄道会社名を「ＪＲ」にする。<br/>
	 * ・鉄道会社名が変換表（stationAndRouteReplace.xml）に設定されている場合、
	 *  取得した値で置換する。<br/>
	 * <br/>
	 * @param csvForm 加工した CSV の値を設定する Form
	 */
	protected void replaceRrName(StationCsvForm csvForm){

		// 路線CD「J」からはじまるものは鉄道会社名を「ＪＲ」とする
		if (csvForm.getRouteCd().startsWith("J")) {
			csvForm.setRrName("ＪＲ");
		}

		// 変換表に存在する場合は 鉄道会社名を変換する。			
		String repRrName = this.codeLookupManager.lookupValue("rrName", csvForm.getRrName());
		if(repRrName != null){
			csvForm.setRrName(repRrName);
		}
	}


	
	/**
	 * 特定の条件に該当する路線名を置換する。<br/>
	 * 路線CD が変換表（stationAndRouteReplace.xml）に設定されている場合、
	 * 取得した値で置換する。<br/>
	 * <br/>
	 * @param csvForm 加工した CSV の値を設定する Form
	 */
	protected void replaceRouteName(StationCsvForm csvForm) {

		// 変換表に存在する場合は 路線名を変換する。			
		String repRouteName = this.codeLookupManager.lookupValue("routeName", csvForm.getRouteCd());
		if(repRouteName != null){
			csvForm.setRouteName(repRouteName);
		}
	}


	
	/**
	 * 特定の条件に該当する「路線名カッコ付」を置換する。<br/>
	 * 路線CD が変換表（stationAndRouteReplace.xml）に設定されている場合、
	 * 取得した値で置換する。<br/>
	 * <br/>
	 * @param csvForm 加工した CSV の値を設定する Form
	 */
	protected void replaceRouteNameFull(StationCsvForm csvForm){

		// 変換表に存在する場合は 「路線名カッコ付」を変換する。			
		String repRouteNameFull = this.codeLookupManager.lookupValue("routeNameFull", csvForm.getRouteCd());
		if(repRouteNameFull != null){
			csvForm.setRouteNameFull(repRouteNameFull);
		}
	}

	

	/**
	 * 特定の条件に該当する「路線名鉄道会社付き」を置換する。<br/>
	 * ・「路線名鉄道会社付き」の値に、カッコで囲まれた値がある場合、その部分を取り除く。
	 * 路線CD が変換表（stationAndRouteReplace.xml）に設定されている場合、
	 * 取得した値で置換する。<br/>
	 * <br/>
	 * @param csvForm 加工した CSV の値を設定する Form
	 */
	protected void replaceRouteNameRr(StationCsvForm csvForm){

		// データにカッコが含まれている場合、その部分を消去する。
		csvForm.setRouteNameRr(splitKakko(csvForm.getRouteNameRr()));

		// 変換表に存在する場合は 「路線名鉄道会社付き」を変換する。			
		String repRouteNameRr = this.codeLookupManager.lookupValue("routeNameRr", csvForm.getRouteCd());
		if(repRouteNameRr != null){
			csvForm.setRouteNameRr(repRouteNameRr);
		}
	}

	
	
	/**
	 * 鉄道会社CD、鉄道会社の表示順を設定する。<br/>
	 * 鉄道会社名に該当するデータが鉄道会社マスタに存在する場合、そのマスターに設定されている鉄道会社CD、
	 * 鉄道会社表示順を csvForm へ設定する。<br/>
	 * <br/>
	 * 見つからない場合、新たに追加する鉄道会社情報の Map に該当する鉄道会社情報 が存在するのかをチェッ
	 * クする。　存在する場合、その鉄道会社CD csvForm へ設定する。<br/>
	 * <br/>
	 * それでも見つからない場合、新たな鉄道会社CD を採番して csvForm へ設定する。
	 * <br/>
	 * @param csvForm 加工した CSV の値を設定する Form
	 * @param rrMstMap 既存の鉄道会社情報の Map
	 * @param addRrMstMap 新たに追加する鉄道会社情報の Map
	 * @param allRrCdSet 全鉄道会社CD （これから追加する予定のコードを含む）
	 * @param maxRrCd 最大鉄道会社CD
	 * @param 最大鉄道会社CD
	 */
	protected String confRrCdAndRrSortOrder(StationCsvForm csvForm,
										  Map<String, RrMst> rrMstMap,
										  Map<String, RrMst> addRrMstMap,
										  Set<String> allRrCdSet,
										  String maxRrCd){

		// 鉄道会社情報の鉄道会社CD、表示順を設定
		if (rrMstMap.containsKey(csvForm.getRrName())) {
			// 既存の鉄道会社マスタに同一名称があれば、既存の値を使用する。
			RrMst rrMst = rrMstMap.get(csvForm.getRrName());
			csvForm.setRrCd(rrMst.getRrCd());
			csvForm.setRrSortOrder(rrMst.getSortOrder());
			return maxRrCd;
		}

		if (addRrMstMap.containsKey(csvForm.getRrName())) {
			// 追加予定データに追加済みであればそちらを優先する
			RrMst rrMst = addRrMstMap.get(csvForm.getRrName());
			csvForm.setRrCd(rrMst.getRrCd());
			csvForm.setRrSortOrder(rrMst.getSortOrder());
			return maxRrCd;
		}

		// 新規の場合
		String rrCd = "";								// 鉄道会社CD
		Integer intMaxrrCd = Integer.valueOf(maxRrCd);	// 数値化した最大鉄道会社CD


		// 重複しないコードを作成
		intMaxrrCd++;
		if(intMaxrrCd <= 9999){
			// 鉄道会社CD の最大値に１加算した値が最大値を超えていなければ、その値をゼロ詰
			// 編集して鉄道会社CD とする。
			rrCd = makeDigit(intMaxrrCd, 4, '0');		// 左0詰め4桁
			if(allRrCdSet.contains(rrCd)){				// もし存在していたらリセット
				rrCd = "";
			}
		}

		// 採番できなかった場合（最大値に達していた場合、または、既に使用されていた場合。）
		if(rrCd.equals("")){

			// 0001～9999まで空いているCDを探す
			for(intMaxrrCd=1; intMaxrrCd<=9999; intMaxrrCd++){
				rrCd = makeDigit(intMaxrrCd, 4, '0');	// 左0詰め4桁
				if(!allRrCdSet.contains(rrCd)){
					break;
				}
				rrCd = "";
			}
		}

		// それでも採番できない場合、最大鉄道会社CD として null を復帰する。
		// （鉄道会社コード オーバーフロー）
		if(rrCd.equals("")) return null;

		// 採番したコードを Form に設定する。
		// 新規登録時はソート順は null
		csvForm.setRrCd(rrCd);
		csvForm.setRrSortOrder(null);
		
		// 全鉄道会社CD の set オブジェクトに、追加対象となる鉄道会社CD を追加する。
		allRrCdSet.add(rrCd);

		return rrCd;

	}
	

	
	/**
	 * 既存路線マスタが存在する場合、既存の表示順を Form へ設定する。<br/>
	 * <br/>
	 * @param routeOrderMap 既存路線マスタに登録されている表示順
	 * @param csvForm CSV データを格納した Form 
	 */
	protected void confRouteSortOrder(Map<String, Integer> routeOrderMap, StationCsvForm csvForm){

		// 既存路線マスタが存在する場合、路線マスタの表示順を Form へ設定する。
		if(routeOrderMap.containsKey(csvForm.getRouteCd())){
			csvForm.setRouteSortOrder(routeOrderMap.get(csvForm.getRouteCd()));
		}

	}

	
	
	/**
	 * 鉄道会社情報に追加すべきデータかをチェックし、追加対象の場合は addRrMstMap へ
	 * Form の値を設定する。<br/>
	 * <br/>
	 * @param addRrMstMap 追加対象となる鉄道会社情報を設定する Map
	 * @param csvForm　加工された CSV の情報
	 * @param editUserId ログインユーザーID（更新情報用）
	 * @param currentDate 現在の時間
	 */
	protected void addRrMstData(Map<String, RrMst> addRrMstMap,	StationCsvForm csvForm,	String editUserId, Date currentDate){

		// CSV には鉄道会社CD が存在しないので、鉄道会社名で存在するかをチェックする。
		// 追加予定 Map に鉄道会社名が存在しない場合、鉄道会社情報を追加する。
		if (!addRrMstMap.containsKey(csvForm.getRrName())) {
		
			RrMst rrMst = (RrMst) this.valueObjectFactory.getValueObject("RrMst");
			
			rrMst.setRrCd(csvForm.getRrCd());
			rrMst.setRrName(csvForm.getRrName());
			rrMst.setInsDate(currentDate);
			rrMst.setInsUserId(editUserId);
			rrMst.setUpdDate(currentDate);
			rrMst.setUpdUserId(editUserId);
			rrMst.setSortOrder(csvForm.getRrSortOrder());
			addRrMstMap.put(csvForm.getRrName(), rrMst);
		}
	}
	
	
	
	/**
	 * 駅・路線対応表に追加すべきデータかをチェックし、追加対象の場合は addStationRouteInfoMap へ
	 * Form の値を設定する。<br/>
	 * <br/>
	 * @param addStationRouteInfoMap 追加対象となる駅・路線対応表を設定する Map
	 * @param csvForm　加工された CSV の情報
	 * @param editUserId ログインユーザーID（更新情報用）
	 * @param currentDate 現在の時間
	 */
	protected void addStationRouteData(Map<String, StationRouteInfo> addStationRouteInfoMap,
									   StationCsvForm csvForm,
									   String editUserId,
									   Date currentDate){

		// 駅・路線対応表
		// 路線CD + 駅CD をキーとして、駅・路線対応表の追加対象 Map に存在するかをチェックする。
		// 存在しない場合は Map に追加する。
		if(!addStationRouteInfoMap.containsKey(csvForm.getRouteCd() + csvForm.getStationCd())){
			StationRouteInfo stationRouteInfo = (StationRouteInfo) this.valueObjectFactory.getValueObject("StationRouteInfo");
			stationRouteInfo.setRouteCd(csvForm.getRouteCd());
			stationRouteInfo.setStationCd(csvForm.getStationCd());
			try {
				stationRouteInfo.setSortOrder(new Integer(csvForm.getStationRouteDispOrder()));
			} catch (Exception e) {
				// フォーマットチェック済みなのでここを通ることはないはず
				log.warn("station route order is not numeric.");
			}

			// 駅・路線対応表の追加対象 Map に追加する。
			addStationRouteInfoMap.put(csvForm.getRouteCd() + csvForm.getStationCd(), stationRouteInfo);
		}
	}



	/**
	 * 路線マスタに追加すべきデータかをチェックし、追加対象の場合は addRouteMstMap へ
	 * Form の値を設定する。<br/>
	 * <br/>
	 * @param addRouteMstMap 追加対象となる路線マスタを設定する Map
	 * @param csvForm　加工された CSV の情報
	 * @param editUserId ログインユーザーID（更新情報用）
	 * @param currentDate 現在の時間
	 */
	protected void addRouteMstData(Map<String, RouteMst> addRouteMstMap,
			   					   StationCsvForm csvForm,
			   					   String editUserId,
			   					   Date currentDate){

		// 路線マスタ
		// すでに登録予定となっているコード以外を追加する(先勝ち)
		if (!addRouteMstMap.containsKey(csvForm.getRouteCd())) {

			RouteMst routeMst = (RouteMst) this.valueObjectFactory.getValueObject("RouteMst");
			routeMst.setRouteCd(csvForm.getRouteCd());
			routeMst.setRouteName(csvForm.getRouteName());
			routeMst.setRouteNameFull(csvForm.getRouteNameFull());
			routeMst.setRouteNameRr(csvForm.getRouteNameRr());
			routeMst.setRrCd(csvForm.getRrCd());
			routeMst.setInsDate(currentDate);
			routeMst.setInsUserId(editUserId);
			routeMst.setUpdDate(currentDate);
			routeMst.setUpdUserId(editUserId);
			routeMst.setSortOrder(csvForm.getRrSortOrder());
			addRouteMstMap.put(csvForm.getRouteCd(), routeMst);
		}
	}

	

	/**
	 * 駅マスタに追加すべきデータかをチェックし、追加対象の場合は addStationMstMap へ
	 * Form の値を設定する。<br/>
	 * <br/>
	 * @param addStationMstMap 追加対象となる駅マスタを設定する Map
	 * @param csvForm　加工された CSV の情報
	 * @param editUserId ログインユーザーID（更新情報用）
	 * @param currentDate 現在の時間
	 */
	protected void addStationMstData(Map<String, StationMst> addStationMstMap,
			   						 StationCsvForm csvForm,
			   						 String editUserId,
			   						 Date currentDate){

		// 駅名マスタ
		// すでに登録予定となっているコード以外を追加する(先勝ち)
		if (!addStationMstMap.containsKey(csvForm.getStationCd())) {
			StationMst stationMst = (StationMst) this.valueObjectFactory.getValueObject("StationMst");
			stationMst.setStationCd(csvForm.getStationCd());
			stationMst.setStationName(splitKakko(csvForm.getStationNameFull()));
			stationMst.setStationNameFull(csvForm.getStationNameFull());
			stationMst.setPrefCd(csvForm.getPrefCd());
			stationMst.setInsDate(currentDate);
			stationMst.setInsUserId(editUserId);
			stationMst.setUpdDate(currentDate);
			stationMst.setUpdUserId(editUserId);
			addStationMstMap.put(csvForm.getStationCd(), stationMst);
		}
	}
	

	
	/**
	 * 文字列内から、カッコで囲まれた部分を取り除いた結果を復帰する。<br/>
	 * <br/>
	 * @param str 編集対象文字列 
	 * @return 編集された文字列
	 */
	private String splitKakko(String str) {
		int startIndex, endIndex;
		
		startIndex = str.indexOf("(");
		endIndex = str.indexOf(")");
		if (startIndex == -1) {
			return str;
		}
		
		if (endIndex == -1) {
			return str.substring(0, startIndex);
		}
		
		StringBuffer sb = new StringBuffer(str);
		sb.delete(startIndex, endIndex + 1);
		str = new String(sb);
		sb.setLength(0);
		return str;
	}


	
	/**
	 * 指定された数値を、digit の桁数になる様に、appendix　に指定された値で左詰した値を復帰する。<br/>
	 * <br/>
	 * @param number 編集対象の数値
	 * @param digit 編集後の桁数
	 * @param appendix 左詰する文字
	 * @return 加工された文字列
	 */
	private String makeDigit(int number, int digit, char appendix) {
		String result = Integer.toString(number);
		
		for (int i = result.length(); i < digit; i++) {
			result = appendix + result;
		}
		return result;
	}
	
}
