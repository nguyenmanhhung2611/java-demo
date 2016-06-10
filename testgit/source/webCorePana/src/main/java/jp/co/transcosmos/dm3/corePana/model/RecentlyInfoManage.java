package jp.co.transcosmos.dm3.corePana.model;

import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousing;
import jp.co.transcosmos.dm3.corePana.model.recentlyInfo.form.RecentlyInfoForm;

/**
 * 最近見た物件情報を管理する Model クラス用インターフェース.
 * <p>
 * 最近見た物件情報を操作する model クラスはこのインターフェースを実装する事。<br/>
 * <p>
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * TRANS		2015.03.12	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスはシングルトンで DI コンテナに定義されるので、スレッドセーフである事。<br/>
 *
 */
public interface RecentlyInfoManage {

	/**
	 * パラメータで渡された Form の情報で最近見た物件情報を新規追加する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * <br/>
	 * @param paramMap 最近見た物件情報のシステム物件CD、ユーザーIDを格納した Map オブジェクト
	 * @param editUserId ログインユーザーID （更新情報用）
	 * @return 0⇒更新した、または最大件数に達した、
	 *         1⇒追加した
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public int addRecentlyInfo(Map<String, Object> paramMap, String editUserId) throws Exception;

	/**
	 * 最近見た物件情報を検索し、結果リストを復帰する。<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、最近見た物件情報を検索する。<br/>
	 * 検索結果は Form オブジェクトに格納され、取得した該当件数を戻り値として復帰する。<br/>
	 * <br/>
	 * @param searchForm 検索条件、および、検索結果の格納オブジェクト
	 *
	 * @return 結果リスト
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public List<Map<String, PanaHousing>> searchRecentlyInfo(RecentlyInfoForm searchForm) throws Exception;

	/**
	 * 最近見た物件情報を検索し、結果リストを復帰する。<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、最近見た物件情報を検索する。<br/>
	 * 検索結果は Form オブジェクトに格納され、取得した該当件数を戻り値として復帰する。<br/>
	 * <br/>
	 * @param userId ユーザーID
	 *
	 * @return 結果リスト
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public List<PanaHousing> searchRecentlyInfo(String userId) throws Exception;

	/**
	 * 最近見た物件の登録件数を検索し、登録件数を復帰する。<br/>
	 * 引数で渡された Map パラメータの値で検索条件を生成し、最近見た物件の登録件数を検索する。<br/>
	 * 取得した該当件数を戻り値として復帰する。<br/>
	 * <br/>
	 * @param paramMap 検索条件の格納オブジェクト
	 *
	 * @return 登録件数
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public int searchRecentlyInfo(Map<String, Object> paramMap) throws Exception;

	/**
	 * ユーザーIDより最近見た物件情報取得<br/>
	 * ※募集終了した物件（物件情報が無い場合、および、非公開に設定されている場合）も含む<br/>
	 *
	 * @param userId
	 *            ユーザーID
	 * @return 最近見た物件情報
	 * @throws Exception
	 */
	public List<PanaHousing> getRecentlyInfoListMap(String userId, String orderBy, boolean ascending) throws Exception;

	/**
	 * ユーザーIDより最近見た物件テーブルから件数取得
	 *
	 * @param userId
	 *            ユーザーID
	 * @return 最近見た物件件数
	 * @throws Exception
	 */
	public int getRecentlyInfoCnt(String userId) throws Exception;

	/**
	 * 引数で渡された値で最近見た物件情報を削除する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * 指定された最近見た物件情報が存在しない場合は、そのまま正常終了として扱う。<br/>
	 * <br/>
	 *
	 * @param userId ユーザーID
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public void delRecentlyInfo(String userId) throws Exception;
}
