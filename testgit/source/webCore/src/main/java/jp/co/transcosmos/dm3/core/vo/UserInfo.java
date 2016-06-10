package jp.co.transcosmos.dm3.core.vo;

import java.io.Serializable;
import java.util.Date;

import jp.co.transcosmos.dm3.login.LastLoginTimestamped;
import jp.co.transcosmos.dm3.login.LoginUser;

/**
 * ユーザーID情報.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 *
 * <pre>
 * 担当者        修正日        修正内容
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.03.10     新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 *
 */
public class UserInfo implements LoginUser, LastLoginTimestamped, Serializable {

	private static final long serialVersionUID = 1L;

	/** ユーザーID */
	private String userId;
	/** 登録日 */
	private Date insDate;
	/** 前回ログイン日 */
	private Date preLogin;
	/** 最終ログイン日 */
	private Date lastLogin;

	/**
	 * ユーザーID を取得する。<br/>
	 * 認証用インターフェース（LoginUser）の実装メソッドで、匿名認証で使用される。<br/>
	 * <br/>
	 *
	 * @return ユーザーID
	 */
	@Override
	public String getUserId() {
		return userId;
	}

	/**
	 * ユーザーID を設定する。<br/>
	 * <br/>
	 *
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 登録日 を取得する。<br/>
	 * <br/>
	 *
	 * @return 登録日
	 */
	public Date getInsDate() {
		return insDate;
	}

	/**
	 * 登録日 を設定する。<br/>
	 * <br/>
	 *
	 * @param insDate
	 */
	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	/**
	 * 前回ログイン日 を取得する。<br/>
	 * <br/>
	 *
	 * @return 前回ログイン日
	 */
	public Date getPreLogin() {
		return preLogin;
	}

	/**
	 * 前回ログイン日 を設定する。<br/>
	 * <br/>
	 *
	 * @param preLogin
	 */
	public void setPreLogin(Date preLogin) {
		this.preLogin = preLogin;
	}

	/**
	 * 最終ログイン日 を取得する。<br/>
	 * <br/>
	 *
	 * @return 最終ログイン日
	 */
	public Date getLastLogin() {
		return lastLogin;
	}

	/**
	 * 最終ログイン日 を設定する。<br/>
	 * <br/>
	 *
	 * @param lastLogin
	 */
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * ログインID を取得する。<br/>
	 * 認証用インターフェース（LoginUser）の実装メソッドで、匿名認証で使用される。<br/>
	 * 匿名ログインでは、ログインID = ユーザーID となる<br/>
	 * <br/>
	 * @return null 固定
	 */
	@Override
	public String getLoginId() {
		return this.userId;
	}

	/**
	 * パスワードの照合を行う。<br/>
	 * 認証用インターフェース（LoginUser）の実装メソッドで、匿名認証で使用される。<br/>
	 * 匿名ログインでは、明示的なログインは行わないのでパスワード照合は常に true を復帰する。<br/>
	 * <br/>
	 * @return true 固定
	 */
	@Override
	public boolean matchPassword(String password) {
		// TODO 後で、DBに保存したトークンに変更する。

		return true;
	}

	/**
	 * LastLoginTimestamped インターフェースの実装メソッド<br/>
	 * このメソッドは、最終ログイン日時を更新する前に実行される。<br/>
	 * 例えば、前回ログイン日を画面表示する場合、最終ログイン日はログインした時に更新されてしまうので、
	 * このメソッドをオーバーライドして値を退避しておく必要がある。<br/>
	 * <br/>
	 */
	@Override
	public void copyThisLoginTimestampToLastLoginTimestamp() {
		setPreLogin(getLastLogin());
	}

	/**
	 * LastLoginTimestamped インターフェースの実装メソッド<br/>
	 * このメソッドに該当するフィールドを使用して最終ログイン日時を更新する。<br/>
	 * <br/>
	 * @param lLastLoginTimestamp 最終ログイン日
	 */
	@Override
	public void setThisLoginTimestamp(Date lLastLoginTimestamp) {
		this.lastLogin = lLastLoginTimestamp;		
	}
}
