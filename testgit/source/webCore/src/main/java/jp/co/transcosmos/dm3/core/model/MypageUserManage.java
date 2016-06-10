package jp.co.transcosmos.dm3.core.model;

import jp.co.transcosmos.dm3.core.model.exception.DuplicateException;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserForm;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserSearchForm;
import jp.co.transcosmos.dm3.core.model.mypage.form.PwdChangeForm;
import jp.co.transcosmos.dm3.core.model.mypage.form.RemindForm;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.login.LoginUser;


/**
 * マイページユーザーの情報を管理する Model クラス用インターフェース.
 * <p>
 * マイページユーザーの情報を操作する model クラスはこのインターフェースを実装する事。<br/>
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
public interface MypageUserManage {

	/**
	 * ユーザーID を採番し、ユーザーID 情報へ追加する。<br/>
	 * フロントサイトの場合、この機能は匿名認証のフィルターから使用される。<br/>
	 * 管理機能からマイページ会員を登録する場合、addMyPageUser() を使用する前にこのメソッドを
	 * 実行してユーザーID を採番する必要がある。<br/>
	 * <br/>
	 * @param editUserId 更新時のタイムスタンプとなるユーザーID　（フロント側の場合、null）
	 * 
	 * @return 追加されたログインユーザーオブジェクト
	 * 
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public LoginUser addLoginID(String editUserId)	throws Exception;



	/**
	 * パラメータで渡された Form の情報と指定されたユーザーIDでマイページユーザーを新規登録する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * フロント側からマイページ登録する場合、ログインID は既に採番されているので、addUserId にはその値を指定する。<br/>
	 * 管理機能から使用する場合は、addLoginID() を使用してログインID を採番しておく事。<br/>
	 * また、フロント側からマイページ登録する場合、editUserId は、addUserId と同じ値を設定する。<br/>
	 * <br/>
	 * @param inputForm マイページユーザーの入力値を格納した Form オブジェクト
	 * @param addUserId マイページ追加対象となるユーザーID
	 * @param editUserId 更新時のタイムスタンプとなるユーザーID
	 * 
	 * @return addUserId と同じ値
	 * 
 	 * @exception Exception 実装クラスによりスローされる任意の例外
 	 * @exception DuplicateException メールアドレス（ログインID） の重複
	 */
	public String addMyPageUser(MypageUserForm inputForm, String addUserId, String editUserId)
			throws Exception, DuplicateException;



	/**
	 * パラメータで渡された Form の情報でマイページユーザーを更新する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * MyPageUserForm の userId プロパティに設定された値を主キー値として更新する。<br/>
	 * フロント側からマイページ情報を更新する場合、editUserId は、addUserId と同じ値を設定する。<br/>
	 * <br/>
	 * @param inputForm マイページユーザーの入力値を格納した Form オブジェクト
	 * @param addUserId マイページ更新対象となるユーザーID
	 * @param editUserId 更新時のタイムスタンプとなるユーザーID
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception DuplicateException ログインID の重複
	 * @exception NotFoundException更新対象なし
	 */
	public void updateMyPageUser(MypageUserForm inputForm, String updUserId, String editUserId)
			throws Exception, DuplicateException, NotFoundException;



	/**
	 * 引数で渡された Form の情報でマイページユーザーを退会処理する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * 退会処理時に、ユーザーID 情報は削除しない事。　また、セキュリティ的問題から、userId の値は、リクエ
	 * ストパラメータから取得しない事。<br/>
	 * なお、削除対象レコードが存在しない場合でも正常終了として扱う事。<br/>
	 * <br/>
	 * @param userId 退会対象となるマイページユーザーのユーザーID
	 * 
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public void delMyPageUser(String userId) throws Exception;


	
	/**
	 * メールアドレス（ログインID） が未使用かをチェックする。<br/>
	 * フォームに設定されているメールアドレス（ログインID） が未使用かをチェックする。<br/>
	 * もし、ユーザーＩＤ が引数で渡された場合、そのユーザーID を除外してチェックする。<br/>
	 * <br/>
	 * @param inputForm チェック対象となる入力値
	 * @param userId チェック対象となるマイページユーザーのユーザーID （新規登録時は null）
	 *  
	 * @return true = 利用可、false = 利用不可
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public boolean isFreeLoginId(MypageUserForm inputForm, String userId)
			throws Exception;



	/**
	 * マイページユーザーを検索し、結果リストを復帰する。<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、マイページユーザーを検索する。<br/>
	 * 検索結果は Form オブジェクトに格納され、取得した該当件数を戻り値として復帰する。<br/>
	 * <br/>
	 * @param searchForm 検索条件、および、検索結果の格納オブジェクト
	 * 
	 * @return 該当件数
	 * 
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public int searchMyPageUser(MypageUserSearchForm searchForm) throws Exception;



	/**
	 * 引数で渡されたユーザーID （主キー値）に該当するマイページユーザー情報を復帰する。<br/>
	 * フロント側のマイページから使用する場合、userId の値は、リクエストパラメータで取得しない事。<br/>
	 * もし該当データが見つからない場合、null を復帰する。<br/>
	 * <br/>
	 * @param userId 取得対象となるマイページユーザーのユーザーID
	 * 
	 * @return　DB から取得したマイページユーザーのバリーオブジェクト
	 * 
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public JoinResult searchMyPageUserPk(String userId) throws Exception;



	/**
	 * パスワード変更の登録処理を行う。<br/>
	 * 指定されたメールアドレスの妥当性を確認し、問題がなければパスワード問合せ情報を DB に登録
	 * する。　正常に処理が行えた場合、採番したお問い合わせID （UUID）を復帰する。<br/>
	 * ※トランザクションの管理は呼出元で制御する事。<br/>
	 *   （その為、メール送信はこのメソッド内で実装しない。）<br/>
	 * <br/>
	 * @param inputForm パスワード問合せの対象となる入力情報
	 * @param entryUserId 匿名認証時に払い出されているユーザーID
	 * 
	 * @return 正常終了時 UUID
	 * 
 	 * @exception Exception 実装クラスによりスローされる任意の例外
 	 * @exception NotFoundException 登録対象なし
	 */
	public String addPasswordChangeRequest(RemindForm inputForm, String entryUserId)
			throws Exception, NotFoundException;



	/**
	 * パスワードの変更処理を行う。<br/>
	 * パスワードの入力確認など、一般的なバリデーションは呼出し元で実施しておく事。<br/>
	 * リクエストパラメータで渡された UUID が期限切れの場合、もしくは、該当レコードがパスワード問合せ
	 * 情報に存在しない場合は例外をスローする。<br/>
	 * パスワードの更新時に使用する主キーの値は、パスワード問合せ情報から取得する事。<br/>
	 * <br/>
	 * @param inputForm 新しいパスワードの入力値が格納された Form
	 * @param entryUserId 匿名認証時に払い出されているユーザーID
	 * 
 	 * @exception Exception 実装クラスによりスローされる任意の例外
 	 * @exception NotFoundException 更新対象なし
	 */
	public void changePassword(PwdChangeForm inputForm, String entryUserId)
			throws Exception, NotFoundException;


}
