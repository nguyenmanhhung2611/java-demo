package jp.co.transcosmos.dm3.core.model;

import java.util.List;

import jp.co.transcosmos.dm3.adminCore.request.form.HousingRequestForm;
import jp.co.transcosmos.dm3.core.model.exception.MaxEntryOverException;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housingRequest.HousingRequest;
import jp.co.transcosmos.dm3.core.model.housingRequest.form.RequestSearchForm;


/**
 * 物件リクエスト情報を管理する Model クラス用インターフェース.
 * <p>
 * 物件リクエスト情報を操作する model クラスはこのインターフェースを実装する事。<br/>
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
public interface HousingRequestManage {


	/**
	 * 指定された Form の値で物件リクエスト情報を登録する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * 物件リクエストID は自動採番されるので、HousingRequestForm の housingRequestId
	 * プロパティには値を設定しない事。<br/>
	 * 登録件数が上限値に達する場合は登録せずにエラーとして復帰する事。<br/>
	 * <br/>
	 * @param userId マイページユーザーID
	 * @param inputForm 入力された物件リクエスト情報
	 * 
	 * @return 採番された物件リクエストID
	 * 
  	 * @exception Exception 実装クラスによりスローされる任意の例外
  	 * @exception MaxEntryOverException 最大登録数オーバー
	 */
	public String addRequest(String userId, HousingRequestForm inputForm)
			throws Exception, MaxEntryOverException;



	/**
	 * 指定された Form の値で物件リクエスト情報を更新する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * 更新に使用するキーは、HousingRequestForm　の housingRequestId と、引数で渡された
	 * userId を使用する事。  （userId はリクエストパラメータで取得しない事。）<br/>
	 * <br/>
	 * @param userId マイページユーザーID
	 * @param inputForm 入力された物件リクエスト情報（更新対象の主キー値を含む）
	 * 
  	 * @exception Exception 実装クラスによりスローされる任意の例外
  	 * @exception NotFoundException 更新対象なし
	 */
	public void updateRequest(String userId, HousingRequestForm inputForm)
			throws Exception, NotFoundException;



	/**
	 * 指定された Form の値で物件リクエスト情報を削除する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * 削除に使用するキーは、HousingRequestForm　の housingRequestId と、引数で渡された
	 * userId を使用する事。  （userId はリクエストパラメータで取得しない事。）<br/>
	 * <br/>
	 * @param userId マイページユーザーID
	 * @param inputForm 入力された物件リクエスト情報（削除対象の主キー値を含む）
	 * 
  	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public void delRequest(String userId, HousingRequestForm inputForm)
			throws Exception;



	/**
	 * 指定されたユーザーID に該当する、物件リクエスト（検索条件）の一覧を取得する。<br/>
	 * <br/>
	 * @param userId マイページユーザーID
	 * 
	 * @return 取得結果
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public List<HousingRequest> searchRequest(String userId)
			throws Exception;
	
	
	
	/**
	 * 指定されたユーザーID が所持する、物件リクエスト（検索条件）の数を取得する。<br/>
	 * <br/>
	 * @param userId マイページユーザーID
	 * 
	 * @return 該当件数
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public int searchRequestCnt(String userId)
			throws Exception;



	/**
	 * 指定された検索条件（リクエストID）に該当する物件の情報を取得する。<br/>
	 * <br/>
	 * @param userId マイページユーザーID
	 * @param requestId リクエストID
	 * 
	 * @return 該当件数
	 * 
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public int searchHousing(String userId, RequestSearchForm searchForm) throws Exception;



}
