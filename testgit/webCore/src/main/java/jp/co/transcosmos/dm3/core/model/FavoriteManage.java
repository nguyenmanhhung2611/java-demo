package jp.co.transcosmos.dm3.core.model;

import jp.co.transcosmos.dm3.core.model.exception.MaxEntryOverException;
import jp.co.transcosmos.dm3.core.model.favorite.form.FavoriteSearchForm;

/**
 * お気に入り情報を管理する Model クラス用インターフェース.
 * <p>
 * お気に入り情報を操作する model クラスはこのインターフェースを実装する事。<br/>
 * <p>
 * 
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
public interface FavoriteManage {

	/**
	 * 引数で渡された値でお気に入り情報を登録する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * 指定されたユーザーID、システム物件CD が既に存在する場合、上書き保存する。<br/>
	 * 　登録件数※登録件数の上限なし。<br/>
	 * <br/>
	 * 
	 * @param userId マイページのユーザーID
	 * @param sysHousingCd お気に入り登録するシステム物件CD
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception MaxEntryOverException　最大登録数オーバー
	 */
	public void addFavorite(String userId, String sysHousingCd) throws Exception;

	/**
	 * 引数で渡された値でお気に入り情報を削除する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * 指定されたお気に入り情報が存在しない場合は、そのまま正常終了として扱う。<br/>
	 * <br/>
	 * 
	 * @param userId ユーザーID
	 * @param sysHousingCd システム物件CD
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public void delFavorite(String userId, String sysHousingCd) throws Exception;

	/**
	 * お気に入り情報を検索し、結果リストを復帰する。<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、お気に入り情報を検索する。<br/>
	 * 検索結果は Form オブジェクトに格納され、取得した該当件数を戻り値として復帰する。<br/>
	 * 物件情報に存在しないお気に入り情報は削除した上で、検索結果を取得する<br/>
	 * <br/>
	 * @param userId マイページのユーザーID
	 * @param searchForm 検索条件、および、検索結果の格納オブジェクト
	 * 
	 * @return 取得件数
	 * 
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public int searchFavorite(String userId, FavoriteSearchForm searchForm) throws Exception;

	/**
	 * 引数で渡された userId に該当するお気に入り情報の件数を取得する。<br/>
	 * <br/>
	 * 
	 * @param userId マイページのユーザーID
	 * 
	 * @return お気に入り登録した物件の件数
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public int getFavoriteCnt(String userId) throws Exception;

}
