package jp.co.transcosmos.dm3.core.model.building;


import java.util.List;

import jp.co.transcosmos.dm3.core.model.BuildingManage;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingImageInfoForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingLandmarkForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingSearchForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingStationInfoForm;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingLandmark;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.dao.NotEnoughRowsException;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * <pre>
 * 建物メンテナンス用 Model クラス
 * 
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.27	新規作成
 * 
 * 注意事項
 * 
 * </pre>
 */
public class BuildingManageImpl implements BuildingManage {

	private static final Log log = LogFactory.getLog(BuildingManageImpl.class);

	/** VO のインスタンスを生成する場合のファクトリー */
	protected ValueObjectFactory valueObjectFactory;

	/** 建物基本情報用 DAO */
	protected DAO<BuildingInfo> buildingInfoDAO;
	
	/** 建物情報一覧取得用 DAO */
	protected DAO<JoinResult> buildingListDAO;
	
	/** 建物基本情報取得用 DAO（建物閲覧） */
	protected DAO<JoinResult> buildingInfoDetailDAO;
	
	/** 最寄り駅情報取得用 DAO（建物閲覧） */
	protected DAO<JoinResult> buildingStationInfoListDAO;
	
	/** 建物ランドマーク情報取得用 DAO（建物閲覧） */
	protected DAO<BuildingLandmark> buildingLandmarkDAO;
	
	/** 最寄り駅情報用 DAO */
	protected DAO<BuildingStationInfo> buildingStationInfoDAO;
	
	/** 都道府県マスタテーブルの別名 */
	public static final String PREF_MST_ALIA = "prefMst";
	
	/** 建物基本情報テーブルの別名 */
	public static final String BUILDING_INFO_ALIA = "buildingInfo";
	
	/** 最寄り駅情報テーブルの別名 */
	public static final String BUILDING_STATION_INFO_ALIA = "buildingStationInfo";
	
	/** 路線マスタテーブルの別名 */
	public static final String ROUTE_MST = "routeMst";
	
	/** 駅マスタテーブルの別名 */
	public static final String STATION_MST = "stationMst";
	
	/**
	 * バリーオブジェクトのインスタンスを生成するファクトリーを設定する。<br/>
	 * <br/>
	 * @param valueObjectFactory バリーオブジェクトのファクトリー
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}
	
	/**
	 * パラメータで渡された Form の情報で建物基本情報を新規登録する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * <br/>
	 * @param inputForm 建物基本情報の入力値を格納した Form オブジェクト
	 * @param editUserId ログインユーザーID （更新情報用）
	 * @return 採番されたシステム建物CD
	 */
	@Override
	public String addBuilding(BuildingForm inputForm, String editUserId) {
    	// 新規登録処理の場合、入力フォームの値を設定するバリーオブジェクトを生成する。
		// バリーオブジェクトは、ファクトリーメソッド以外では生成しない事。
		// （継承されたバリーオブジェクトが使用されなくなる為。）
		BuildingInfo buildingInfo = (BuildingInfo) this.valueObjectFactory.getValueObject("BuildingInfo");


    	// フォームの入力値をバリーオブジェクトに設定する。
    	inputForm.copyToBuildingInfo(buildingInfo, editUserId);
		
    	// 新規登用のタイムスタンプ情報を設定する。　（更新日の設定情報を転記）
    	buildingInfo.setInsDate(buildingInfo.getUpdDate());
    	buildingInfo.setInsUserId(editUserId);


		// 同じ主キー値で登録する依存表が存在する為、予め主キーの値を取得しておく。
		String sysBuildingCd = (String) this.buildingInfoDAO
				.allocatePrimaryKeyIds(1)[0].toString();


		// 取得した主キー値で建物基本情報を登録
		buildingInfo.setSysBuildingCd(sysBuildingCd);
		this.buildingInfoDAO.insert(new BuildingInfo[] { buildingInfo });

		return sysBuildingCd;
	}

	/**
	 * パラメータで渡された Form の情報で建物基本情報を更新する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * BuildingForm の sysBuildingCd プロパティに設定された値を主キー値として更新する。<br/>
	 * <br/>
	 * @param inputForm 建物情報の入力値を格納した Form オブジェクト
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 */
	@Override
	public void updateBuildingInfo(BuildingForm inputForm, String editUserId)
			throws NotFoundException {

    	// 更新処理の場合、更新対象データを取得する。
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysBuildingCd", inputForm.getSysBuildingCd());

		List<BuildingInfo> buildingInfos = this.buildingInfoDAO.selectByFilter(criteria);

        // 該当するデータが存在しない場合は、例外をスローする。
		if (buildingInfos == null || buildingInfos.size() == 0) {
			throw new NotFoundException();
		}    	

		
        // 建物情報を取得し、入力した値で上書きする。
		BuildingInfo buildingInfo = buildingInfos.get(0);

    	inputForm.copyToBuildingInfo(buildingInfo, editUserId);

		// 建物情報の更新
		this.buildingInfoDAO.update(new BuildingInfo[]{buildingInfo});

	}

	/**
	 * パラメータで渡された Form の情報で建物情報を削除する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * BuildingForm の sysBuildingCd プロパティに設定された値を主キー値として削除する。
	 * また、削除対象レコードが存在しない場合でも正常終了として扱う事。<br/>
	 * <br/>
	 * @param sysBuildingCd 削除対象となる sysBuildingCd
	 * 
	 */
	@Override
	public void delBuildingInfo(String sysBuildingCd) {
		// 建物情報を削除する条件を生成する。
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysBuildingCd", sysBuildingCd);
		
		// 建物基本情報を削除する
		this.buildingInfoDAO.deleteByFilter(criteria);
	}

	/**
	 * 建物情報を検索し、結果リストを復帰する。（一覧用）<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、建物情報を検索する。<br/>
	 * 検索結果は Form オブジェクトに格納され、取得した該当件数を戻り値として復帰する。<br/>
	 * <br/>
	 * @param searchForm 検索条件、および、検索結果の格納オブジェクト
	 * @return 該当件数
	 */
	@Override
	public int searchBuilding(BuildingSearchForm searchForm) {

		// 建物情報を検索する条件を生成する。
		DAOCriteria criteria = searchForm.buildCriteria();

		// 建物の検索
		List<JoinResult> buildingList;
		try {
			buildingList = this.buildingListDAO.selectByFilter(criteria);

		} catch (NotEnoughRowsException err) {

			int pageNo = (err.getMaxRowCount() - 1)
					/ searchForm.getRowsPerPage() + 1;
			log.warn("resetting page to " + pageNo);
			searchForm.setSelectedPage(pageNo);
			criteria = searchForm.buildCriteria();
			buildingList = this.buildingListDAO.selectByFilter(criteria);
		}

		searchForm.setRows(buildingList);

		return buildingList.size();
	}

	/**
	 * リクエストパラメータで渡されたシステム建物CD （主キー値）に該当する建物情報を復帰する。<br/>
	 * BuildingSearchForm の searchForm プロパティに設定された値を主キー値として情報を取得する。
	 * もし該当データが見つからない場合、null を復帰する。<br/>
	 * <br/>
	 * @param searchForm　検索結果となる JoinResult
	 * @return　DB から取得した建物情報のバリーオブジェクト
	 */
	@Override
	public Building searchBuildingPk(String sysBuildingCd) {
		
		// 建物情報のインスタンスを生成する。
		Building building = createBuildingInstace();
		

		// 建物基本情報を取得
		// confMainData() は、１対１の関係にある関連テーブルの情報を取得し、building へ設定する。
		// もし該当するデータが無い場合、building へ何も設定せずに null を復帰してくるので、戻り値が
		// null の場合は処理を中断する。
		if (confMainData(building, sysBuildingCd) == null) return null; 
		
		// 建物最寄り駅情報を取得する。
		// この情報は、駅マスタと結合して表示順でソートした JoinResult が格納される。
		confBuldingStation(building, sysBuildingCd);

		// 建物ランドマーク情報を取得する。
		confBuildingLandmark(building, sysBuildingCd);
		
		return building;

	}

	/**
	 * 建物詳基本情報情報を取得し、Building オブジェクトへ設定する。<br/>
	 * <br/>
	 * @param building 値の設定先となる building オブジェクト
	 * @param sysBuildingCd 取得対象システム建物CD
	 * 
	 * @return 取得結果 （該当なしの場合、null）
	 */
	protected JoinResult confMainData(Building building, String sysBuildingCd){

		// 建物情報を取得する為の主キーを対象とした検索条件を生成する。
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysBuildingCd", sysBuildingCd);

		// 建物基本情報を取得
		List<JoinResult> buildingInfoDetail = this.buildingInfoDetailDAO
				.selectByFilter(criteria);
		if (buildingInfoDetail.size() == 0) return null;

		building.setBuildingInfo(buildingInfoDetail.get(0));

		return buildingInfoDetail.get(0);
	}
	
	
	/**
	 * 建物情報用 DAOを設定する。<br/>
	 * <br/>
	 * 
	 * @return 建物情報用 DAO
	 */
	public void setBuildingInfoDAO(DAO<BuildingInfo> buildingInfoDAO) {
		this.buildingInfoDAO = buildingInfoDAO;
	}
	
	/**
	 * 建物情報一覧用 DAOを設定する。<br/>
	 * <br/>
	 * 
	 * @return 建物情報用 DAO
	 */
	public void setBuildingListDAO(DAO<JoinResult> buildingListDAO) {
		this.buildingListDAO = buildingListDAO;
	}

	/**
	 * 最寄り駅情報用DAOを設定する。<br/>
	 * <br/>
	 * @param buildingStationInfoDao 最寄り駅情報用DAO
	 */
	public void setBuildingStationInfoDao(DAO<BuildingStationInfo> buildingStationInfoDao) {
		this.buildingStationInfoDAO = buildingStationInfoDao;
	}
	
	/**
	 * パラメータで渡された Form の情報で最寄り駅情報を更新する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * BuildingStationInfoForm の sysBuildingCd プロパティに設定された値を主キー値として更新する。<br/>
	 * <br/>
	 * @param inputForm 最寄り駅情報の入力値を格納した Form オブジェクト
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	@Override
	public void updateBuildingStationInfo(BuildingStationInfoForm inputForm) throws Exception {
		// 一度削除
		// 削除条件
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysBuildingCd", inputForm.getSysBuildingCd());
		this.buildingStationInfoDAO.deleteByFilter(criteria);
		// 変更後の最寄り駅情報を新規登録する
		// レコード数を取得する
		int length = 0;
		for (String routeCd : inputForm.getDefaultRouteCd()) {
			if (StringValidateUtil.isEmpty(routeCd)) {
				continue;
			}
			length++;
		}
		// 最寄り駅情報配列の作成
		BuildingStationInfo[] buildingStationInfos =(BuildingStationInfo[]) this.valueObjectFactory.getValueObject(
				"BuildingStationInfo", length); 
		
		inputForm.copyToBuildingStationInfo(buildingStationInfos, length);
		try {
			this.buildingStationInfoDAO.insert(buildingStationInfos);
		} catch (DataIntegrityViolationException e) {
			throw new NotFoundException();
		}

	}

	/**
	 * パラメータで渡された Form の情報で地域情報を更新する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * BuildingLandmarkForm の sysBuildingCd プロパティに設定された値を主キー値として更新する。<br/>
	 * <br/>
	 * @param inputForm 地域情報の入力値を格納した Form オブジェクト
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	@Override
	public void updateBuildingLandmark(BuildingLandmarkForm inputForm)
			throws Exception {
		// 一度削除
		// 削除条件
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysBuildingCd", inputForm.getSysBuildingCd());
		this.buildingLandmarkDAO.deleteByFilter(criteria);
		// 変更後の最寄り駅情報を新規登録する
		// レコード数を取得する
		int length = 0;
		for (String landmarkName : inputForm.getLandmarkName()) {
			if (StringValidateUtil.isEmpty(landmarkName)) {
				continue;
			}
			length++;
		}
		// 地域情報配列の作成
		BuildingLandmark[] buildingLandmarks =(BuildingLandmark[]) this.valueObjectFactory.getValueObject(
				"BuildingLandmark", length); 
		
		// 地域情報配列の初期化
		inputForm.copyToBuildingLandmark(buildingLandmarks, length);
		try{
			this.buildingLandmarkDAO.insert(buildingLandmarks);
		} catch (DataIntegrityViolationException e){
			throw new NotFoundException();
		}
		
		
	}

	/**
	 * 建物基本情報取得用 DAO（建物閲覧）を設定する。<br/>
	 * <br/>
	 * @param buildingInfoDetailDAO 建物基本情報取得用 DAO（建物閲覧）
	 */
	public void setBuildingInfoDetailDAO(DAO<JoinResult> buildingInfoDetailDAO) {
		this.buildingInfoDetailDAO = buildingInfoDetailDAO;
	}

	/**
	 * 寄り駅情報取得用 DAO（建物閲覧）を設定する。<br/>
	 * <br/>
	 * @param buildingStationInfoListDAO 寄り駅情報取得用 DAO（建物閲覧）
	 */
	public void setBuildingStationInfoListDAO(
			DAO<JoinResult> buildingStationInfoListDAO) {
		this.buildingStationInfoListDAO = buildingStationInfoListDAO;
	}

	/**
	 * 建物ランドマーク情報取得用 DAO（建物閲覧）を設定する。<br/>
	 * <br/>
	 * @param buildingLandmarkDAO 建物ランドマーク情報取得用 DAO（建物閲覧）
	 */
	public void setBuildingLandmarkDAO(DAO<BuildingLandmark> buildingLandmarkDAO) {
		this.buildingLandmarkDAO = buildingLandmarkDAO;
	}

	/**
	 * 建物情報オブジェクトのインスタンスを生成する。<br/>
	 * もし、カスタマイズで建物情報を構成するテーブルを追加した場合、このメソッドをオーバーライドする事。<br/>
	 * <br/>
	 * @return Building のインスタンス
	 */
	public Building createBuildingInstace() {
		return new Building();
	}

	/**
	 * 建物最寄り駅情報を取得する。<br/>
 	 * 建物最寄り駅情報と、駅マスタ、最寄り駅マスタを結合し、建物最寄り駅情報の表示順でソートした結果を building
 	 * へ設定する。<br/>
	 * <br/>
	 * @param building 値の設定先となる Building オブジェクト
	 * @param sysBuildingCd 取得対象システム物件CD に該当する物件基本情報のシステム建物CD
	 * 
	 * @return 取得結果
	 */
	protected List<JoinResult> confBuldingStation(Building building, String sysBuildingCd){

		// 建物最寄り駅情報の取得
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysBuildingCd", sysBuildingCd);
		criteria.addOrderByClause("sortOrder");
		
		// 最寄り駅情報を取得
		List<JoinResult> list = this.buildingStationInfoListDAO
				.selectByFilter(criteria);
		building.setBuildingStationInfoList(list);

		return list;
	}

	/**
	 * 建物ランドマーク情報を取得する。<br/>
 	 * 取得した建物ランドマーク情報を building へ設定する。<br/>
	 * <br/>
	 * @param building 値の設定先となる Building オブジェクト
	 * @param sysBuildingCd 取得対象システム物件CD に該当する建物基本情報のシステム建物CD
	 * 
	 * @return 取得結果
	 */
	protected List<BuildingLandmark> confBuildingLandmark(Building building, String sysBuildingCd){

		// 建物ランドマーク情報の取得条件
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysBuildingCd", sysBuildingCd);
		criteria.addOrderByClause("sortOrder");
		
		// 建物ランドマーク情報の取得
		List<BuildingLandmark> list = this.buildingLandmarkDAO.selectByFilter(criteria);
		building.setBuildingLandmarkList(list);

		return list;
	}

	/**
	 * パラメータで渡された Form の情報で建物画像情報を新規登録する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * <br/>
	 * @param inputForm 建物画像情報の入力値を格納した Form オブジェクト
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	@Override
	public void addBuildingImageInfo(BuildingImageInfoForm inputForm)
			throws Exception {
		// 一旦実装しない

	}

	/**
	 * パラメータで渡された Form の情報で建物画像情報を更新する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * <br/>
	 * @param inputForm 建物画像情報の入力値を格納した Form オブジェクト
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	@Override
	public void updBuildingImageInfo(BuildingImageInfoForm inputForm)
			throws Exception {
		// 一旦実装しない

	}
	
	/**
	 * パラメータで渡された Form の情報で建物画像情報を削除する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * <br/>
	 * @param inputForm 建物画像情報の入力値を格納した Form オブジェクト
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	@Override
	public void delBuildingImageInfo(BuildingImageInfoForm inputForm)
			throws Exception {
		// 一旦実装しない

	}
}
