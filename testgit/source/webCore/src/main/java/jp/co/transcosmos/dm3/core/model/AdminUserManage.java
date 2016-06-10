package jp.co.transcosmos.dm3.core.model;

import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserForm;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserSearchForm;
import jp.co.transcosmos.dm3.core.model.adminUser.form.PwdChangeForm;
import jp.co.transcosmos.dm3.core.model.exception.DuplicateException;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.dao.JoinResult;


/**
 * 管理ユーザー情報を管理する Model クラス用インターフェース.
 * <p>
 * 管理ユーザー情報を操作する model クラスはこのインターフェースを実装する事。<br/>
 * <p>
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.03	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスはシングルトンで DI コンテナに定義されるので、スレッドセーフである事。<br/>
 * 
 */
public interface AdminUserManage {

	
	/**
	 * パラメータで渡された Form の情報で管理ユーザーを新規追加する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * 管理ユーザーID は自動採番されるので、AdminUserForm の userId プロパティには値を設定しない事。<br/>
	 * <br/>
	 * @param inputForm 管理ユーザーの入力値を格納した Form オブジェクト
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 * @return 採番された管理者ユーザーID
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception DuplicateException 登録するログインID が重複した場合
	 */
	public String addAdminUser(AdminUserForm inputForm, String editUserId)
			throws Exception, DuplicateException;


	/**
	 * パラメータで渡された Form の情報で管理ユーザーを更新する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * AdminUserForm の userId プロパティに設定された値を主キー値として更新する。<br/>
	 * <br/>
	 * @param inputForm 管理ユーザーの入力値を格納した Form オブジェクト
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception DuplicateException 登録するログインID が重複した場合
	 * @exception NotFoundException 更新対象が存在しない場合
	 */
	public void updateAdminUser(AdminUserForm inputForm, String editUserId)
			throws Exception, DuplicateException, NotFoundException;

	
	
	/**
	 * パラメータで渡された Form の情報で管理ユーザーを削除する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * AdminUserForm の userId プロパティに設定された値を主キー値として削除する。
	 * また、削除対象レコードが存在しない場合でも正常終了として扱う事。<br/>
	 * <br/>
	 * @param inputForm 削除対象となる管理ユーザーの主キー値を格納した Form オブジェクト
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public void delAdminUser(AdminUserForm inputForm)
			throws Exception;

	
	
	/**
	 * アカウントロックステータスを変更する。<br/>
	 * <br/>
	 * @param inputForm 変更対象となる管理ユーザーの主キー値を格納した Form オブジェクト
	 * @param locked false = 通常モードに設定、true = ロックモードに設定
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception NotFoundException 更新対象が存在しない場合
	 */
	public void changeLockStatus (AdminUserForm inputForm, boolean locked, String editUserId)
			throws Exception, NotFoundException;



	/**
	 * アカウントロックステータスをチェックする。<br/>
	 * <br/>
	 * @param targetUser チェック対象ユーザー情報
	 *  
	 * @return true = ロック中、false = 正常
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public boolean isLocked(AdminUserInterface targetUser)
			throws Exception;
	
	
	
	/**
	 * ログインID が未使用かをチェックする。<br/>
	 * フォームに設定されているログインID が未使用かをチェックする。<br/>
	 * もし、ユーザーＩＤ が設定されている場合、そのユーザーID を除外してチェックする。<br/>
	 * <br/>
	 * @param inputForm チェック対象となる入力値
	 *  
	 * @return true = 利用可、false = 利用不可
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public boolean isFreeLoginId(AdminUserForm inputForm)
			throws Exception;



	/**
	 * 管理ユーザーを検索し、結果リストを復帰する。<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、管理ユーザーを検索する。<br/>
	 * 検索結果は Form オブジェクトに格納され、取得した該当件数を戻り値として復帰する。<br/>
	 * <br/>
	 * @param searchForm 検索条件、および、検索結果の格納オブジェクト
	 * 
	 * @return 該当件数
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public int searchAdminUser(AdminUserSearchForm searchForm)
			throws Exception;



	/**
	 * リクエストパラメータで渡されたユーザーID （主キー値）に該当する管理ユーザー情報を復帰する。<br/>
	 * AdminUserSearchForm の userId プロパティに設定された値を主キー値として情報を取得する。
	 * もし該当データが見つからない場合、null を復帰する。<br/>
	 * <br/>
	 * @param searchForm　検索結果となる JoinResult
	 * 
	 * @return　DB から取得した管理ユーザーのバリーオブジェクト
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public JoinResult searchAdminUserPk(AdminUserSearchForm searchForm)
			throws Exception;



	/**
	 * パスワードの変更処理を行う。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * AdminUserForm の userId プロパティに設定された値を主キー値としてパスワードを更新する。<br/>
	 * <br/>
	 * @param inputForm パスワード変更の入力情報
	 * @param updUserId 更新対象ユーザーID
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception NotFoundException 更新対象が存在しない場合
	 */
	public void changePassword(PwdChangeForm inputForm, String updUserId, String editUserId)
			throws Exception, NotFoundException;


	// TODO 管理機能用のリマインダを追加する可能性あり。　（優先度低）

}
