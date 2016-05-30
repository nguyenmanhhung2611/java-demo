package jp.co.transcosmos.dm3.core.model.adminUser;

import java.util.Date;

import jp.co.transcosmos.dm3.login.LastLoginTimestamped;
import jp.co.transcosmos.dm3.login.LockSupportLoginUser;
import jp.co.transcosmos.dm3.login.PasswordExpire;

/**
 * <pre>
 * 管理ユーザー情報管理バリーオブジェクト用インターフェース
 * 管理ユーザーを管理する DAO のバリーオブジェクトは、このインターフェースを実装する必要がある。
 * 管理ユーザーメンテナンス機能は、このインターフェース越しにデーターの操作を行う。
 * 
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.28	新規作成
 * H.Mizuno		2015.02.24	パスワード有効期限、アカウントロック対応
 *
 * 注意事項
 *
 * </pre>
 */
public interface AdminUserInterface extends LockSupportLoginUser, PasswordExpire, LastLoginTimestamped {

	/**
	 * 管理ユーザーのユーザーＩＤ（主キーの値）を設定する。<br/>
	 * このメソッドで設定するフィールドが、管理ユーザーの主キー値として使用される。<br/>
	 * <br/>
	 * @param userId ユーザーＩＤ（主キー値）
	 */
	public void setUserId(Object userId);
	
	/**
	 * 管理ユーザーのログインID を設定する。<br/>
	 * このメソッドで設定するフィールドが、管理ユーザーのログインID　として使用される。<br/>
	 * <br/>
	 * @param loginId
	 */
	public void setLoginId(String loginId);

	/**
	 * 管理ユーザーのパスワードを設定する。<br/>
	 * このメソッドで設定するフィールドが、管理ユーザーのパスワードとして使用される。<br/>
	 * <br/>
	 * @param password　パスワード（ハッシュされた値）
	 */
	public void setPassword(String password);

	/**
	 * 管理ユーザーのパスワードを取得する。<br/>
	 * このメソッドで設定するフィールドが、管理ユーザーのパスワードとして使用される。<br/>
	 * <br/>
	 * @return password　パスワード（ハッシュされた値）
	 */
	public String getPassword();
	
	/**
	 * 管理ユーザーのユーザー名を設定する。<br/>
	 * このメソッドで設定するフィールドが、管理ユーザーのユーザー名として使用される。<br/>
	 * <br/>
	 * @param userName 管理ユーザー名
	 */
	public void setUserName(String userName);

	/**
	 * 管理ユーザー名を取得する。<br/>
	 * このメソッドが復帰するフィールドを、管理ユーザーのユーザー名として使用する。<br/>
	 * <br/>
	 * @return String 管理ユーザー名
	 */
	public String getUserName();
	
	/**
	 * 管理ユーザーのメールアドレスを設定する。<br/>
	 * このメソッドで設定するフィールドが、管理ユーザーのメールアドレスとして使用される。<br/>
	 * <br/>
	 * @param email メールアドレス
	 */
	public void setEmail(String email);
	
	/**
	 * 管理ユーザーのメールアドレスを取得する。<br/>
	 * このメソッドが復帰するフィールドを、管理ユーザーのメールアドレスとして使用する。<br/>
	 * <br/>
	 * @return String メールアドレス
	 */
	public String getEmail();
	
	/**
	 * 管理ユーザーの備考を設定する。<br/>
	 * このメソッドが設定するフィールドを、管理ユーザーの備考として使用する。<br/>
	 * <br/>
	 * @param note 備考
	 */
	public void setNote(String note);
	
	/**
	 * 管理ユーザーの備考を取得する。<br/>
	 * このメソッドが復帰するフィールドを、管理ユーザーの備考として使用する。<br/>
	 * <br/>
	 * @return 備考
	 */
	public String getNote();

	/**
	 * 最終ログイン日時を取得する。<br/>
	 * このメソッドが復帰するフィールドを、管理ユーザーの最終ログイン日として使用する。<br/>
	 * <br/>
	 * @return Date 最終ログイン日
	 */
	public Date getLastLoginDate();

	/**
	 * 前回ログイン日時を取得する。<br/>
	 * このメソッドが復帰するフィールドを、管理ユーザーの前回ログイン日として使用する。<br/>
	 * <br/>
	 * @return Date 前回ログイン日
	 */
	public Date getPreLoginDate();

	/**
	 * 最終パスワード変更日時を設定する。<br/>
	 * このメソッドを使用して、管理ユーザーの最終パスワード変更日時を設定する。<br/>
	 * <br/>
	 * @param changeDate 最終パスワード変更日時
	 */
	public void setLastPasswdChange(Date changeDate);
	
	/**
	 * 管理ユーザーの登録日を取得する。<br/>
	 * <br/>
	 * @return 管理ユーザーの登録日
	 */
	public Date getInsDate();
	
	/**
	 * 管理ユーザーの登録日を設定する。<br/>
	 * <br/>
	 * @param insDate 管理ユーザーの登録日
	 */
	public void setInsDate(Date insDate);
	
	/**
	 * 管理ユーザーの登録担当者ID を取得する。<br/>
	 * <br/>
	 * @return 管理ユーザーの登録担当者ID
	 */
	public String getInsUserId();
	
	/**
	 * 管理ユーザーの登録担当者ID を設定する。<br/>
	 * <br/>
	 * @param insUserId　管理ユーザーの登録担当者ID
	 */
	public void setInsUserId(String insUserId);
	
	/**
	 * 管理ユーザーの更新日を取得する。<br/>
	 * <br/>
	 * @return 管理ユーザーの更新日
	 */
	public Date getUpdDate();
	
	/**
	 * 管理ユーザーの更新日を設定する。<br/>
	 * <br/>
	 * @param updDate 管理ユーザーの更新日
	 */
	public void setUpdDate(Date updDate);
	
	/**
	 * 管理ユーザーの更新者ID を取得する。<br/>
	 * <br/>
	 * @return 管理ユーザーの更新者ID
	 */
	public String getUpdUserId();
	
	/**
	 * 管理ユーザーの更新者ID を設定する。<br/>
	 * <br/>
	 * @param updUserId
	 */
	public void setUpdUserId(String updUserId);

}
