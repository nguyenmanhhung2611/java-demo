package jp.co.transcosmos.dm3.core.model.building;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.BuildingManage;
import jp.co.transcosmos.dm3.core.model.HousingManage;
import jp.co.transcosmos.dm3.core.model.HousingPartCreator;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingImageInfoForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingLandmarkForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingSearchForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingStationInfoForm;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.util.ThumbnailCreator;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;

/**
 * 建物情報用 Model の proxy クラス.
 * この proxy クラスでは、建物画像ファイルのサムネイル作成や、こだわり条件情報の再構築などを行う。<br/>
 * 建物情報用 Model は個別カスタマイズで継承・委譲により拡張される可能性がある。<br/>
 * 画像ファイルの操作はロールバックできないので、全てのDB更新処理完了後に実行する必要がある。<br/>
 * また、こだわり条件情報の再構築も全てのDB更新が完了している必要がある。<br/>
 * その為、これらの処理は proxy 内から実行して Model 本体から分離する。<br/>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.04.14	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスはシングルトンで DI コンテナに定義されるので、スレッドセーフである事。<br/>
 * 
 */
public class BuildingPartThumbnailProxy implements BuildingManage {
	
	/** 委譲先となる建物 Model クラス */
	protected BuildingManage buildingManage;
	
	/** こだわり条件生成クラスのリスト */
	protected List<HousingPartCreator> partCreateors;
	
	/** 物件基本情報用　DAO */
	private DAO<HousingInfo> housingInfoDAO;
	
	/** 物件 Model クラス */
	protected HousingManage housingManage;

	/** サムネイル画像作成クラス */
	protected ThumbnailCreator thumbnailCreator;

	/** 共通パラメータオブジェクト */
	protected CommonParameters commonParameters;
	
	/** 物件画像情報用　DAO */
	protected DAO<HousingImageInfo> housingImageInfoDAO;
	
	/**
	 * 物件基本情報用　DAOを設定する。<br/>
	 * <br/>
	 * @param housingInfoDAO 物件基本情報用　DAO
	 */
	public void setHousingInfoDAO(DAO<HousingInfo> housingInfoDAO) {
		this.housingInfoDAO = housingInfoDAO;
	}

	/**
	 * 物件画像情報用　DAOを設定する。<br/>
	 * <br/>
	 * @param housingImageInfoDAO 物件画像情報用　DAO
	 */
	public void setHousingImageInfoDAO(DAO<HousingImageInfo> housingImageInfoDAO) {
		this.housingImageInfoDAO = housingImageInfoDAO;
	}

	/**
	 * 共通パラメータオブジェクトを設定する。<br/>
	 * <br/>
	 * @param commonParameters 共通パラメータオブジェクト
	 */
	public void setCommonParameters(CommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * サムネイル画像作成クラスを設定する。<br/>
	 * <br/>
	 * @param thumbnailCreator サムネイル画像作成クラス
	 */
	public void setThumbnailCreator(ThumbnailCreator thumbnailCreator) {
		this.thumbnailCreator = thumbnailCreator;
	}

	/**
	 * 物件 Model クラスを設定する。<br/>
	 * <br/>
	 * @param housingManage 物件 Model クラス
	 */
	public void setHousingManage(HousingManage housingManage) {
		this.housingManage = housingManage;
	}

	/**
	 * こだわり条件生成クラスのリストを設定する。<br/>
	 * <br/>
	 * @param partCreateors  こだわり条件生成クラスのリスト
	 */
	public void setPartCreateors(List<HousingPartCreator> partCreateors) {
		this.partCreateors = partCreateors;
	}

	/**
	 * 委譲先となる建物 Modelを設定する。<br/>
	 * <br/>
	 * @param buildingManage 委譲先となる建物 Model
	 */
	public void setBuildingManage(BuildingManage buildingManage) {
		this.buildingManage = buildingManage;
	}

	/**
	 * 建物基本情報新規登録時のフィルター処理<br/>
	 * そのまま委譲する。<br/>
	 * <br/>
	 * @param inputForm 建物基本情報の入力値を格納した Form オブジェクト
	 * @param editUserId ログインユーザーID （更新情報用）
	 * @return 採番されたシステム建物CD
	 */
	@Override
	public String addBuilding(BuildingForm inputForm, String editUserId)
			throws Exception {
		// 建物基本情報の登録処理へ委譲する。
		String sysBuildingCd = this.buildingManage.addBuilding(inputForm, editUserId);
		
		return sysBuildingCd;
	}

	/**
	 * 建物基本情報更新時のフィルター処理<br/>
	 * 委譲先の建物 model を使用して建物基本情報を更新後、こだわり条件を再構築する。<br/>
	 * <br/>
	 * @param inputForm 建物情報の入力値を格納した Form オブジェクト
	 * @param editUserId ログインユーザーID （更新情報用）
	 */
	@Override
	public void updateBuildingInfo(BuildingForm inputForm, String editUserId)
			throws Exception, NotFoundException {
		// 建物基本情報の更新処理へ委譲する。
		this.buildingManage.updateBuildingInfo(inputForm, editUserId);
		
		// こだわり条件を再構築する。
		createPartInfo(inputForm.getSysBuildingCd(), "updateBuildingInfo");
		
	}

	/**
	 * 建物基本情報削除時のフィルター処理<br/>
	 * 建物情報を削除する前に、削除対象となる物件画像情報を取得し、パス名を集約する。<br/>
	 * 委譲先の物件 model を使用して建物情報の削除を行い、該当する公開物件画像ファイルを削除する。<br/>
	 * <br/>
	 * @param sysBuildingCd 削除対象となる sysBuildingCd
	 */
	@Override
	public void delBuildingInfo(String sysBuildingCd) throws Exception {
		
		//　画像削除パスを格納する Set オブジェクト
		Set<String> delPath = new HashSet<>();
		
		// 条件の作成対象となる物件情報を取得する。
		DAOCriteria criteriaBuilding = new DAOCriteria();
		criteriaBuilding.addWhereClause("sysBuildingCd", sysBuildingCd);
		List<HousingInfo> housingInfoList = this.housingInfoDAO.selectByFilter(criteriaBuilding);
		
		for (HousingInfo housingInfo : housingInfoList) {
			// 削除対象となる物件画像情報を先に取得しておく。
			DAOCriteria criteriaHousing = new DAOCriteria();
			criteriaHousing.addWhereClause("sysHousingCd", housingInfo.getSysHousingCd());
			List<HousingImageInfo> delImgList = this.housingImageInfoDAO.selectByFilter(criteriaHousing);

			// 削除はフォルダ単位で行うので、画像情報の値を Set オブジェクトに格納して重複を取り除く
			for (HousingImageInfo imgInfo : delImgList){
				if (!delPath.contains(imgInfo.getPathName())){
					delPath.add(imgInfo.getPathName());
				}
			}
		}

		// 建物基本情報の削除処理へ委譲する。
		this.buildingManage.delBuildingInfo(sysBuildingCd);
		
		// 建物情報の削除後、公開されている物件画像ファイルを削除する。
		this.thumbnailCreator.deleteImageDir(getImgOpenRootPath(), delPath);
	}

	/**
	 * 公開先となる物件画像の Root パスを取得する。<br/>
	 * Form オブジェクトが省略された場合、全ての公開先 Root フォルダを復帰する。<br/>
	 * <br/>
	 * @return 公開されている物件画像の Root パスリスト
	 */
	protected List<String> getImgOpenRootPath(){
		// 基本機能としては、公開先フォルダは１個のみ。
		List<String> imgOpenRootList = new ArrayList<>();
		imgOpenRootList.add(this.commonParameters.getHousImgOpenPhysicalPath());
		return imgOpenRootList;
	}
	
	/**
	 * 建物検索処理のフィルター処理<br/>
	 * そのまま委譲する。<br/>
	 * <br/>
	 * @param inputForm 検索条件フォーム
	 * @return 該当件数
	 */
	@Override
	public int searchBuilding(BuildingSearchForm searchForm) throws Exception {
		// 建物基本情報の検索処理へ委譲する。
		return this.buildingManage.searchBuilding(searchForm);
	}

	/**
	 * 建物情報取得処理のフィルター処理<br/>
	 * そのまま委譲する。<br/>
	 * <br/>
	 * @param inputForm 検索条件フォーム
	 * @return 該当件数
	 */
	@Override
	public Building searchBuildingPk(String sysBuildingCd) throws Exception {
		// 建物情報の検索処理へ委譲する。
		return this.buildingManage.searchBuildingPk(sysBuildingCd);
	}

	
	/**
	 * 最寄り駅情報更新時のフィルター処理<br/>
	 * 委譲先の建物 model を使用して最寄り駅情報を更新後、こだわり条件を再構築する。<br/>
	 * <br/>
	 * @param inputForm 最寄り駅情報の入力値を格納した Form オブジェクト
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	@Override
	public void updateBuildingStationInfo(BuildingStationInfoForm inputForm)
			throws Exception {
		// 最寄り駅情報の更新処理へ委譲する。
		this.buildingManage.updateBuildingStationInfo(inputForm);
		// こだわり条件を再構築する。
		createPartInfo(inputForm.getSysBuildingCd(), "updateBuildingStationInfo");
	}

	/**
	 * 地域情報更新時のフィルター処理<br/>
	 * 委譲先の建物 model を使用して地域情報を更新後、こだわり条件を再構築する。<br/>
	 * <br/>
	 * @param inputForm 地域情報の入力値を格納した Form オブジェクト
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	@Override
	public void updateBuildingLandmark(BuildingLandmarkForm inputForm)
			throws Exception {
		// 地域情報の更新処理へ委譲する。
		this.buildingManage.updateBuildingLandmark(inputForm);
		// こだわり条件を再構築する。
		createPartInfo(inputForm.getSysBuildingCd(), "updateBuildingLandmark");
		
	}

	/**
	 * 建物画像登録処理のフィルター処理<br/>
	 * そのまま委譲する。<br/>
	 * <br/>
	 * @param inputForm 画像情報フォーム
	 */
	@Override
	public void addBuildingImageInfo(BuildingImageInfoForm inputForm)
			throws Exception {
		// 建物画像の登録処理へ委譲する。
		this.buildingManage.addBuildingImageInfo(inputForm);
		
	}

	/**
	 * 建物画像更新処理のフィルター処理<br/>
	 * そのまま委譲する。<br/>
	 * <br/>
	 * @param inputForm 画像情報フォーム
	 */
	@Override
	public void updBuildingImageInfo(BuildingImageInfoForm inputForm)
			throws Exception {
		// 建物画像の更新処理へ委譲する。
		this.buildingManage.addBuildingImageInfo(inputForm);
		
	}

	/**
	 * 建物画像削除処理のフィルター処理<br/>
	 * そのまま委譲する。<br/>
	 * <br/>
	 * @param inputForm 画像情報フォーム
	 */
	@Override
	public void delBuildingImageInfo(BuildingImageInfoForm inputForm)
			throws Exception {
		// 建物画像の削除処理へ委譲する。
		this.buildingManage.delBuildingImageInfo(inputForm);
		
	}

	/**
	 * 建物情報オブジェクトのインスタンスを生成するフィルター処理<br/>
	 * そのまま委譲する。<br/>
	 * <br/>
	 */
	@Override
	public Building createBuildingInstace() {
		return this.buildingManage.createBuildingInstace();
	}
	
	/**
	 * こだわり条件を再作成する。<br/>
	 * <br/>
	 * @param sysBuildingCd システム建物CD
	 * @param methodName 実行された物件 model のメソッド名
	 * 
	 * @throws Exception 
	 */
	protected void createPartInfo(String sysBuildingCd, String methodName) throws Exception{

		// こだわり条件生成用のクラスが設定されていない場合はなにもしない。
		if (this.partCreateors == null || this.partCreateors.size() == 0) return;

		// 条件の作成対象となる物件情報を取得する。
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysBuildingCd", sysBuildingCd);
		List<HousingInfo> housingInfoList = this.housingInfoDAO.selectByFilter(criteria);
		for (HousingInfo housingInfo : housingInfoList) {
			// 条件の作成対象となる物件情報を取得する。
			Housing housing = this.housingManage.searchHousingPk(housingInfo.getSysHousingCd(), true);
			for (HousingPartCreator creator : this.partCreateors){
				// 実行対象外の場合は次のこだわり条件作成クラスへ
				if (!creator.isExecuteMethod(methodName)) continue;
	
				// こだわり条件を作成する。
				creator.createPart(housing);
			}
		}
	}

}
