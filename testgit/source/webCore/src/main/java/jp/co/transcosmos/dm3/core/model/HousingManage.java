package jp.co.transcosmos.dm3.core.model;

import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingDtlForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingEquipForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingImgForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingSearchForm;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;

/**
 * 物件情報を管理する Model クラス用インターフェース.
 * <p>
 * 物件情報を操作する model クラスはこのインターフェースを実装する事。<br/>
 * <p>
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.16	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスはシングルトンで DI コンテナに定義されるので、スレッドセーフである事。<br/>
 * 削除以外の更新系処理は、必ずこだわり条件の再構築を行う事。<br/>
 * 
 */
public interface HousingManage {

	/**
	 * パラメータで渡された Form の情報で物件基本情報を新規登録する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * <br/>
	 * @param inputForm 物件基本情報の入力値を格納した Form オブジェクト
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 * @return 採番されたシステム物件CD
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public String addHousing(HousingForm inputForm, String editUserId)
			throws Exception;



	/**
	 * パラメータで渡された Form の情報で物件基本情報を更新する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * HousingForm の sysHousingCd プロパティに設定された値を主キー値として更新する。<br/>
	 * <br/>
	 * @param inputForm 物件基本情報の入力値を格納した Form オブジェクト
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception NotFoundException 更新対象が存在しない場合
	 */
	public void updateHousing(HousingForm inputForm, String editUserId)
			throws Exception, NotFoundException;



	/**
	 * パラメータで渡された Form の情報で物件基本情報を削除する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * HousingForm の sysHousingCd プロパティに設定された値を主キー値として削除する。
	 * また、削除対象レコードが存在しない場合でも正常終了として扱う事。<br/>
	 * 削除時は物件基本情報の従属表も削除対象とする。<br/>
	 * 関連する画像ファイルの削除は Proxy クラス側で対応するので、このクラス内には実装しない。<br/>
	 * <br/>
	 * @param inputForm 削除対象となる sysHousingCd を格納した Form オブジェクト
	 * @param editUserId 削除担当者
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public void delHousingInfo(HousingForm inputForm, String editUserId)
			throws Exception;



	/**
	 * パラメータで渡された Form の情報で物件詳細情報を更新する。<br/>
	 * 該当する物件詳細情報が存在しなくても、該当する物件基本情報が存在する場合、レコードを新たに
	 * 追加して物件詳細情報を登録する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * HousingDtlForm の sysHousingCd プロパティに設定された値を主キー値として更新する。<br/>
	 * <br/>
	 * @param inputForm 物件詳細情報の入力値を格納した Form オブジェクト
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception NotFoundException 親となる物件基本情報が存在しない場合
	 */
	public void updateHousingDtl(HousingDtlForm inputForm, String editUserId)
			throws Exception, NotFoundException;



	/**
	 * パラメータで渡された Form の情報で物件設備情報を更新する。<br/>
	 * 設備情報は DELETE & INSERT で一括更新する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * HousingDtlForm の sysHousingCd プロパティに設定された値を主キー値として更新する。<br/>
	 * <br/>
	 * @param inputForm 物件詳細情報の入力値を格納した Form オブジェクト
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception NotFoundException 親となる物件基本情報が存在しない場合
	 */
	public void updateHousingEquip(HousingEquipForm inputForm, String editUserId)
			throws Exception, NotFoundException;



	/**
	 * パラメータで渡された Form の情報で物件画像情報を新規登録する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * <br/>
	 * 関連する画像ファイルの公開処理やサムネイルの作成は Proxy クラス側で対応するので、
	 * このクラス内では対応しない。<br/>
	 * <br/>
	 * @param inputForm 物件画像情報の入力値を格納した Form オブジェクト
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 * @return 新たに追加した画像情報のリスト
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception NotFoundException 親となる物件基本情報が存在しない場合
	 */
	public List<HousingImageInfo> addHousingImg(HousingImgForm inputForm, String editUserId)
			throws Exception, NotFoundException;



	/**
	 * パラメータで渡されたシステム物件CD、画像タイプ、枝番で物件画像情報を削除する。<br/>
	 * もし削除フラグに 1 が設定されている場合は更新せずに削除処理する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * <br/>
	 * 関連する画像ファイルの削除は、Proxy クラス側で対応するので、このクラス内では対応しない。<br/>
	 * また、更新処理は、画像パス、画像ファイル名の更新はサポートしていない。<br/>
	 * <br/>
	 * @param sysHousingCd システム物件CD
	 * @param imageType 物件画像タイプ
	 * @param divNo 枝番
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 * @return 削除が発生した画像情報のリスト
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public List<HousingImageInfo> updHousingImg(HousingImgForm inputForm, String editUserId)
			throws Exception, NotFoundException;


	
	/**
	 * パラメータで渡されたシステム物件CD、画像タイプ、枝番で物件画像情報を削除する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * 削除後、物件基本情報のタイムスタンプ情報を更新する。<br/>
	 * <br/>
	 * 関連する画像ファイルの削除は、Proxy クラス側で対応するので、このクラス内では対応しない。<br/>
	 * <br/>
	 * @param sysHousingCd システム物件CD
	 * @param imageType 物件画像タイプ
	 * @param divNo 枝番
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 * @return 削除が発生した画像情報
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public HousingImageInfo delHousingImg(String sysHousingCd, String imageType, int divNo, String editUserId)
			throws Exception;



	/**
	 * 物件情報（一部、建物情報）を検索し、結果リストを復帰する。（一覧用）<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、物件情報を検索する。<br/>
	 * 検索結果は Form オブジェクトに格納され、取得した該当件数を戻り値として復帰する。<br/>
	 * <br/>
	 * このメソッドを使用した場合、暗黙の抽出条件（例えば、非公開物件の除外など）が適用される。<br/>
	 * よって、フロント側は基本的にこのメソッドを使用する事。<br/>
	 * <br/>
	 * @param searchForm 検索条件、および、検索結果の格納オブジェクト
	 * @param full false の場合、公開対象外を除外する。　true の場合は除外しない
	 * 
	 * @return 該当件数
	 * 
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public int searchHousing(HousingSearchForm searchForm) throws Exception;

	
	
	/**
	 * 物件情報（一部、建物情報）を検索し、結果リストを復帰する。（一覧用）<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、物件情報を検索する。<br/>
	 * 検索結果は Form オブジェクトに格納され、取得した該当件数を戻り値として復帰する。<br/>
	 * <br/>
	 * @param searchForm 検索条件、および、検索結果の格納オブジェクト
	 * @param full false の場合、公開対象外を除外する。　true の場合は除外しない
	 * 
	 * @return 該当件数
	 * 
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public int searchHousing(HousingSearchForm searchForm, boolean full) throws Exception;


	
	/**
	 * リクエストパラメータで渡されたシステム物件CD （主キー値）に該当する物件情報を復帰する。<br/>
	 * もし該当データが見つからない場合、null を復帰する。<br/>
	 * <br/>
	 * このメソッドを使用した場合、暗黙の抽出条件（例えば、非公開物件の除外など）が適用される。<br/>
	 * よって、フロント側は基本的にこのメソッドを使用する事。<br/>
	 * <br/>
	 * @param sysHousingCd システム物件CD
	 * @param full false の場合、公開対象外を除外する。　true の場合は除外しない
	 * 
	 * @return　DB から取得したバリーオブジェクトを格納したコンポジットクラス
	 * 
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public Housing searchHousingPk(String sysHousingCd) throws Exception;



	/**
	 * リクエストパラメータで渡されたシステム物件CD （主キー値）に該当する物件情報を復帰する。<br/>
	 * もし該当データが見つからない場合、null を復帰する。<br/>
	 * <br/>
	 * @param sysHousingCd システム物件CD
	 * @param full false の場合、公開対象外を除外する。　true の場合は除外しない
	 * 
	 * @return　DB から取得したバリーオブジェクトを格納したコンポジットクラス
	 * 
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public Housing searchHousingPk(String sysHousingCd, boolean full) throws Exception;



	/**
	 * パラメータで渡された Map の情報で物件拡張属性情報を更新する。（システム物件CD 単位）<br/>
	 * 更新は、Delete & Insert で処理する。<br/>
	 * <br/>
	 * inputData の Map の構成は以下の通り。<br/>
	 *   ・key = カテゴリ名 （拡張属性情報の、category に格納する値）
	 *   ・value = 値が格納された Map オブジェクト
	 * inputData の value に格納される Map の構成は以下の通り。<br/>
	 *   ・key = Key名 （拡張属性情報の、key_name に格納する値）
	 *   ・value = 入力値 （拡張属性情報の、data_value に格納する値）
	 * <br/>
	 * @param sysHousingCd 更新対象システム物件CD
	 * @param inputData 登録情報となる Map オブジェクト
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception NotFoundException 親となる物件基本情報が存在しない場合
	 */
	public void updExtInfo(String sysHousingCd, Map<String, Map<String, String>> inputData, String editUserId)
			throws Exception, NotFoundException;



	/**
	 * パラメータで渡されたシステム物件CD に該当する物件拡張属性情報を削除する。（システム物件CD 単位）<br/>
	 * <br/>
	 * @param sysHousingCd 削除対象システム物件CD
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public void delExtInfo(String sysHousingCd, String editUserId)
			throws Exception;



	/**
	 * パラメータで渡された Map の情報で物件拡張属性情報を更新する。（カテゴリー 単位）<br/>
	 * 更新は、Delete & Insert で処理する。<br/>
	 * <br/>
	 * inputData の Map の構成は以下の通り。<br/>
	 *   ・key = Key名 （拡張属性情報の、key_name に格納する値）
	 *   ・value = 入力値 （拡張属性情報の、data_value に格納する値）
	 * <br/>
	 * <br/>
	 * @param sysHousingCd 更新対象システム物件CD
	 * @param category 更新対象カテゴリ名
	 * @param inputData 登録情報となる Map オブジェクト
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception NotFoundException 親となる物件基本情報が存在しない場合
	 */
	public void updExtInfo(String sysHousingCd, String category, Map<String, String> inputData, String editUserId)
			throws Exception, NotFoundException;



	/**
	 * パラメータで渡されたシステム物件CD に該当する物件拡張属性情報を削除する。（カテゴリー 単位）<br/>
	 * <br/>
	 * @param sysHousingCd 削除対象システム物件CD
	 * @param category 削除対象カテゴリ名
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public void delExtInfo(String sysHousingCd, String category, String editUserId)
			throws Exception;
	


	/**
	 * パラメータで渡された Map の情報で物件拡張属性情報を更新する。（Key 単位）<br/>
	 * 更新は、Delete & Insert で処理する。<br/>
	 * <br/>
	 * @param sysHousingCd 更新対象システム物件CD
	 * @param category 更新対象カテゴリ名
	 * @param key 更新対象Key
	 * @param value 更新する値
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception NotFoundException 親となる物件基本情報が存在しない場合
	 */
	public void updExtInfo(String sysHousingCd, String category, String key, String value, String editUserId)
			throws Exception, NotFoundException;

	
	
	/**
	 * パラメータで渡されたシステム物件CD に該当する物件拡張属性情報を削除する。（キー 単位）<br/>
	 * <br/>
	 * @param sysHousingCd 削除対象システム物件CD
	 * @param category 削除対象カテゴリ名
	 * @param category 削除対象 Key
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public void delExtInfo(String sysHousingCd, String category, String key, String editUserId)
			throws Exception;

	/**
	 * 物件情報オブジェクトのインスタンスを生成する。<br/>
	 * もし、カスタマイズで物件情報を構成するテーブルを追加した場合、このメソッドをオーバーライドする事。<br/>
	 * <br/>
	 * @return Housing のインスタンス
	 */
	public Housing createHousingInstace();

}
