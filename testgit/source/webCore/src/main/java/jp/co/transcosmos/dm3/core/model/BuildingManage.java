package jp.co.transcosmos.dm3.core.model;


import jp.co.transcosmos.dm3.core.model.building.Building;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingImageInfoForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingLandmarkForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingSearchForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingStationInfoForm;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;

/**
 * 建物情報を管理する Model クラス用インターフェース.
 * <p>
 * 建物情報を操作する model クラスはこのインターフェースを実装する事。<br/>
 * <p>
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.16	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスはシングルトンで DI コンテナに定義されるので、スレッドセーフである事。<br/>
 * 
 */
public interface BuildingManage {

	
	/**
	 * パラメータで渡された Form の情報で建物情報を新規登録する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * <br/>
	 * @param inputForm 管理ユーザーの入力値を格納した Form オブジェクト
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 * @return 採番されたシステム建物CD
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public String addBuilding(BuildingForm inputForm, String editUserId)
			throws Exception;



	/**
	 * パラメータで渡された Form の情報で建物基本情報を更新する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * BuildingForm の sysBuildingCd プロパティに設定された値を主キー値として更新する。<br/>
	 * <br/>
	 * @param inputForm 建物情報の入力値を格納した Form オブジェクト
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception NotFoundException 更新対象が存在しない場合
	 */
	public void updateBuildingInfo(BuildingForm inputForm, String editUserId)
			throws Exception, NotFoundException;



	/**
	 * パラメータで渡された Form の情報で建物基本情報を削除する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * BuildingForm の sysBuildingCd プロパティに設定された値を主キー値として削除する。
	 * また、削除対象レコードが存在しない場合でも正常終了として扱う事。<br/>
	 * <br/>
	 * @param sysBuildingCd 削除対象となる sysBuildingCd
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public void delBuildingInfo(String sysBuildingCd) throws Exception;

	
	
	/**
	 * 建物情報を検索し、結果リストを復帰する。（一覧用）<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、建物情報を検索する。<br/>
	 * 検索結果は Form オブジェクトに格納され、取得した該当件数を戻り値として復帰する。<br/>
	 * <br/>
	 * @param searchForm 検索条件、および、検索結果の格納オブジェクト
	 * 
	 * @return 該当件数
	 * 
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public int searchBuilding(BuildingSearchForm searchForm) throws Exception;

	
	
	/**
	 * リクエストパラメータで渡されたシステム建物CD （主キー値）に該当する建物情報を復帰する。<br/>
	 * BuildingSearchForm の searchForm プロパティに設定された値を主キー値として情報を取得する。
	 * もし該当データが見つからない場合、null を復帰する。<br/>
	 * <br/>
	 * @param sysBuildingCd　システム建物番号
	 * 
	 * @return　DB から取得した建物情報のバリーオブジェクト
	 * 
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public Building searchBuildingPk(String sysBuildingCd) throws Exception;


	/**
	 * パラメータで渡された Form の情報で最寄り駅情報を更新する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * BuildingStationInfoForm の sysBuildingCd プロパティに設定された値を主キー値として更新する。<br/>
	 * <br/>
	 * @param inputForm 最寄り駅情報の入力値を格納した Form オブジェクト
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public void updateBuildingStationInfo(BuildingStationInfoForm inputForm)
			throws Exception;

	
	/**
	 * パラメータで渡された Form の情報で地域情報を更新する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * BuildingLandmarkForm の sysBuildingCd プロパティに設定された値を主キー値として更新する。<br/>
	 * <br/>
	 * @param inputForm 地域情報の入力値を格納した Form オブジェクト
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public void updateBuildingLandmark(BuildingLandmarkForm inputForm)
			throws Exception;

	/**
	 * パラメータで渡された Form の情報で建物画像情報を新規登録する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * <br/>
	 * @param inputForm 建物画像情報の入力値を格納した Form オブジェクト
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public void addBuildingImageInfo(BuildingImageInfoForm inputForm)
			throws Exception;
	
	/**
	 * パラメータで渡された Form の情報で建物画像情報を更新する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * <br/>
	 * @param inputForm 建物画像情報の入力値を格納した Form オブジェクト
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public void updBuildingImageInfo(BuildingImageInfoForm inputForm)
			throws Exception;
	
	/**
	 * パラメータで渡された Form の情報で建物画像情報を削除する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * <br/>
	 * @param inputForm 建物画像情報の入力値を格納した Form オブジェクト
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public void delBuildingImageInfo(BuildingImageInfoForm inputForm)
			throws Exception;


	/**
	 * 建物情報オブジェクトのインスタンスを生成する。<br/>
	 * もし、カスタマイズで建物情報を構成するテーブルを追加した場合、このメソッドをオーバーライドする事。<br/>
	 * <br/>
	 * @return Building のインスタンス
	 */
	public Building createBuildingInstace();

}
