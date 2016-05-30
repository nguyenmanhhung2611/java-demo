package jp.co.transcosmos.dm3.core.vo;

import java.io.Serializable;
import java.util.Date;

import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.utils.EncodingUtils;

/**
 * 管理者ログインＩＤ情報クラス.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.28	Shamaison サイトをベースに新規作成
 * H.Mizuno		2015.02.20	PasswordExpire　（パスワード有効期限チェック） へ対応
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 * 
 */
public class AdminLoginInfo implements AdminUserInterface, Serializable {

	private static final long serialVersionUID = 1L;

	/** 管理者ユーザーID */
	private String adminUserId;
	/** ログインID */
	private String loginId;
	/** パスワード（ハッシュ値） */
	private String password;
	/** ユーザー名称 */
	private String userName;
	/** メールアドレス */
	private String email;
	/** ログイン失敗回数 */
	private Integer failCnt;
	/** 最終ログイン失敗日 */
	private Date lastFailDate;
	/** 前回ログイン日 */
	private Date preLogin;
	/** 最終ログイン日 */
	private Date lastLogin;
	/** 最終パスワード変更日 */
	private Date lastPwdChangeDate;
	/** 備考 */
	private String note;
	/** 登録日 */
	private Date insDate;
	/** 登録者 */
	private String insUserId;
	/** 最終更新日 */
	private Date updDate;
	/** 最終更新者 */
	private String updUserId;

	
	
	/**
	 * AdminUserInterfaceインターフェース(LoginUser　インターフェース)の実装メソッド<br/>
	 * フレームワークや、管理ユーザーメンテナンス機能はこのメソッドの戻り値を管理ユーザーの主キー
	 * として扱う。<br/>
	 * <br/>
	 * @param userId ユーザーＩＤ（主キー値）
	 */
	@Override
	public void setUserId(Object userId) {
		this.adminUserId = (String) userId;
	}

	/**
	 * AdminUserInterfaceインターフェース(LoginUser　インターフェース)の実装メソッド<br/>
	 * フレームワークや、管理ユーザーメンテナンス機能はこのメソッドの戻り値を管理ユーザーの主キー
	 * として扱う。<br/>
	 * <br/>
	 * @return ユーザーID（主キー値）
	 */
	@Override
	public Object getUserId() {
		return adminUserId;
	}

	/**
	 * 管理者ユーザーID を取得する。<br/>
	 * <br/>
	 * @return  管理者ユーザーID
	 */
	public String getAdminUserId() {
		return this.adminUserId;
	}

	/**
	 * 管理者ユーザーID を設定する。<br/>
	 * <br/>
	 * @param adminUserId　管理者ユーザーID
	 */
	public void setAdminUserId(String adminUserId) {
		this.adminUserId = adminUserId;
	}
	
	/**
	 * AdminUserInterfaceインターフェース(LoginUser　インターフェース)の実装メソッド<br/>
	 * フレームワークや、管理ユーザーメンテナンス機能はこのメソッドの戻り値を管理ユーザーのログインＩＤ
	 * として扱う。<br/>
	 * <br/>
	 * @return ログインID
	 */
	@Override
	public String getLoginId() {
		return this.loginId;
	}

	/**
	 * AdminUserInterfaceインターフェースの実装メソッド<br/>
	 * 管理ユーザーメンテナンス機能はこのメソッドを使用してログインID　を設定する。<br/>
	 * <br/>
	 * @param loginId ログインID
	 */
	@Override
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	/**
	 * AdminUserInterfaceインターフェースの実装メソッド<br/>
	 * 管理ユーザーメンテナンス機能はこのメソッドを使用してパスワードを取得する。<br/>
	 * <br/>
	 * @return パスワード（ハッシュ値）
	 */
	@Override
	public String getPassword() {
		return password;
	}
	
	/**
	 * AdminUserInterfaceインターフェースの実装メソッド<br/>
	 * 管理ユーザーメンテナンス機能はこのメソッドを使用してパスワードを設定する。<br/>
	 * <br/>
	 * @param password　パスワード（ハッシュ値）
	 */
	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * AdminUserInterfaceインターフェースの実装メソッド<br/>
	 * 管理ユーザーメンテナンス機能はこのメソッドの戻り値を管理ユーザー名として扱う。<br/>
	 * <br/>
	 * @return ユーザー名
	 */
	@Override
	public String getUserName() {
		return userName;
	}
	
	/**
	 * AdminUserInterfaceインターフェースの実装メソッド<br/>
	 * 管理ユーザーメンテナンス機能はこのメソッドが設定するフィールドをユーザー名として設定する。<br/>
	 * <br/>
	 * @param userName ユーザー名
	 */
	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * AdminUserInterfaceインターフェースの実装メソッド<br/>
	 * 管理ユーザーメンテナンス機能はこのメソッドの戻り値を管理ユーザーのメールアドレスとして扱う。<br/>
	 * <br/>
	 * @return メールアドレス
	 */
	@Override
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * AdminUserInterfaceインターフェースの実装メソッド<br/>
	 * 管理ユーザーメンテナンス機能はこのメソッドが設定するフィールドを管理ユーザーのメールアドレスとして設定する。<br/>
	 * <br/>
	 * @param email メールアドレス
	 */
	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * AdminUserInterface（UserLock）インターフェースの実装メソッド<br/>
	 * フレームワーク、および、管理ユーザーメンテナンス機能はこのメソッドが参照するフィールドをログイン失敗回数として扱う。<br/>
	 * <br/>
	 * @return ログイン失敗回数
	 */
	@Override
	public Integer getFailCnt() {
		return failCnt;
	}

	/**
	 * ログイン失敗回数を設定する。<br/>
	 * <br/>
	 * @param failCnt ログイン失敗回数
	 */
	public void setFailCnt(Integer failCnt) {
		this.failCnt = failCnt;
	}

	/**
	 * 最終ログイン失敗日を取得する。<br/>
	 * <br/>
	 * @return 最終ログイン失敗日
	 */
	public Date getLastFailDate() {
		return lastFailDate;
	}

	/**
	 * 最終ログイン失敗日を設定する。<br/>
	 * <br/>
	 * @param lastFailDate 最終ログイン失敗日
	 */
	public void setLastFailDate(Date lastFailDate) {
		this.lastFailDate = lastFailDate;
	}

	/**
	 * AdminUserInterfaceインターフェースの実装メソッド<br/>
	 * 管理ユーザーメンテナンス機能はこのメソッドの戻り値を管理ユーザーの最終ログイン日として扱う。<br/>
	 */
	@Override
	public Date getLastLoginDate() {
		return this.lastLogin;
	}

	/**
	 * 最終ログイン日を取得する。<br/>
	 * <br/>
	 * @return 最終ログイン日
	 */
	public Date getLastLogin() {
		return this.lastLogin;
	}
	
	/**
	 * 最終ログイン日を設定する。<br/>
	 * <br/>
	 * @param lastLogin 最終ログイン日
	 */
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 *　dminUserInterfaceインターフェース(LoginUser　インターフェース)の実装メソッド<br/>
	 *　前回ログイン日を取得する。<br/>
	 * <br/>
	 * @return 前回ログイン日 
	 */
	@Override
	public Date getPreLoginDate() {
		return this.preLogin;
	}

	/** 前回ログイン日を取得する。<br/>
	 * <br/>
	 * @return 前回ログイン日
	 */
	public Date getPreLogin() {
		return this.preLogin;
	}

	/**
	 * 前回ログイン日を設定する。<br/>
	 * <br/>
	 * @param preLogin 前回ログイン日
	 */
	public void setPreLogin(Date preLogin) {
		this.preLogin = preLogin;
	}

	/**
	 * 最終パスワード変更日を取得する。<br/>
	 * <br/>
	 * @return 最終パスワード変更日
	 */
	public Date getLastPwdChangeDate() {
		return lastPwdChangeDate;
	}

	/**
	 * 最終パスワード変更日を設定する。<br/>
	 * <br/>
	 * @param lastPwdChangeDate 最終パスワード変更日
	 */
	public void setLastPwdChangeDate(Date lastPwdChangeDate) {
		this.lastPwdChangeDate = lastPwdChangeDate;
	}

	/**
	 * AdminUserInterface（PasswordExpire） インターフェースの実装メソッド<br/>
	 * 最終パスワード変更日を管理しているプロパティの値を復帰する。<br/>
	 * フレームワークはこの日付でパスワードの有効期限をチェックする。<br/>
	 * <br/>
	 * @return 最終パスワード変更日
	 */
	@Override
	public Date getLastPasswdChange() {
		return lastPwdChangeDate;
	}

	/**
	 * AdminUserInterface インターフェースの実装メソッド<br/>
	 * 最終パスワード変更日を管理しているプロパティに値を設定する。<br/>
	 * 管理機能はこのプロパティで設定するフィールドを最終パスワード変更日のフィールドとして扱う。<br/>
	 * <br/>
	 * @return 最終パスワード変更日
	 */
	@Override
	public void setLastPasswdChange(Date changeDate) {
		this.lastPwdChangeDate = changeDate;
	}

	/**
	 * AdminUserInterfaceインターフェースの実装メソッド<br/>
	 * 管理ユーザーメンテナンス機能はこのメソッドの戻り値を備考として扱う。<br/>
	 * <br/>
	 * @return 備考
	 */
	@Override
	public String getNote() {
		return note;
	}

	/**
	 * 備考を設定する。<br/>
	 * <br/>
	 * @param note 備考
	 */
	@Override
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * 登録日を取得する。<br/>
	 * <br/>
	 * @return 登録日
	 */
	public Date getInsDate() {
		return insDate;
	}
	
	/**
	 * 登録日を設定する。<br/>
	 * <br/>
	 * @return 登録日
	 */
	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	/**
	 * 登録者（管理者ユーザーID）を取得する。<br/>
	 * <br/>
	 * @return 登録者
	 */
	public String getInsUserId() {
		return insUserId;
	}
	
	/**
	 * 登録者（管理者ユーザーID）を設定する。<br/>
	 * <br/>
	 * @param insUserId 登録者
	 */
	public void setInsUserId(String insUserId) {
		this.insUserId = insUserId;
	}

	/**
	 * 最終更新日を取得する。<br/>
	 * <br/>
	 * @return 最終更新日
	 */
	public Date getUpdDate() {
		return updDate;
	}

	/**
	 * 最終更新日を設定する。<br/>
	 * <br/>
	 * @param updDate 最終更新日
	 */
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}
	
	/**
	 * 最終更新者を取得する。<br/>
	 * <br/>
	 * @return 最終更新者
	 */
	public String getUpdUserId() {
		return updUserId;
	}

	/**
	 * 最終更新者を設定する。<br/>
	 * <br/>
	 * @param updUserId 最終更新者
	 */
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}

	/**
	 * AdminUserInterfaceインターフェース（LoginUser インターフェース）の実装メソッド<br/>
	 * パスワード一致確認用メソッド<br>
	 * パスワードがDBから取得したパスワードと一致しているかをチェックし結果を復帰する。<br>
	 * <br>
	 * @param pPassword 入力されたパスワード
	 * @return true 一致 false 不一致
	 */
	public boolean matchPassword(String pPassword) {
		return getPassword().equals(EncodingUtils.md5Encode(pPassword));
	}

	/**
	 * AdminUserInterfaceインターフェース（LastLoginTimestamped インターフェース）の実装メソッド<br/>
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
	 * AdminUserInterfaceインターフェース（LastLoginTimestamped インターフェース）の実装メソッド<br/>
	 * このメソッドに該当するフィールドを使用して最終ログイン日時を更新する。<br/>
	 * <br/>
	 * @param lLastLoginTimestamp 最終ログイン日
	 */
	@Override
	public void setThisLoginTimestamp(Date lLastLoginTimestamp) {
		this.lastLogin = lLastLoginTimestamp;
	}

}
