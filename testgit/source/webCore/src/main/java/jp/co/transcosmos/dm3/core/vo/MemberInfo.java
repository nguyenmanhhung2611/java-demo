package jp.co.transcosmos.dm3.core.vo;

import java.io.Serializable;
import java.util.Date;

import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.utils.EncodingUtils;

/**
 * マイページ会員情報クラス.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.18	新規作成
 * H.Mizuno		2015.03.02	認証用にインターフェースを実装
 * H.Mizuno		2015.04.14	Cookie 自動ログインに対応
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 * 
 */
public class MemberInfo implements MypageUserInterface, Serializable {

	private static final long serialVersionUID = 1L;
	
	/** ユーザーID */
	private String userId;
	/** 会員名（姓） */
	private String memberLname;
	/** 会員名（名） */
	private String memberFname;
	/** 会員名・カナ（姓） */
	private String memberLnameKana;
	/** 会員名・カナ（名） */
	private String memberFnameKana;
	/** メールアドレス */
	private String email;
	/** パスワード */
	private String password;
	/** 自動ログイン用トークン */
	private String cookieLoginToken;
	/** 前回ログイン日 */
	private Date preLogin;
	/** 最終ログイン日 */
	private Date lastLogin;
	/** 登録日 */
	private Date insDate;
	/** 登録者 */
	private String insUserId;
	/** 最終更新日 */
	private Date updDate;
	/** 最終更新者 */
	private String updUserId;
	


	/**
	 * MypageUserInterfaceインターフェース(LoginUser　インターフェース)の実装メソッド<br/>
	 * フレームワークや、マイページ会員メンテナンス機能はこのメソッドの戻り値をマイページ会員の主キー
	 * として扱う。<br/>
	 * <br/>
	 * @param userId ユーザーＩＤ（主キー値）
	 */
	@Override
	public String getUserId() {
		return userId;
	}

	/**
	 * MypageUserInterfaceインターフェースの実装メソッド<br/>
	 * 基本機能はこのフィールドをユーザーIDとして設定する。<br/>
	 * <br/>
	 * @param userId ユーザーID
	 */
	@Override
	public void setUserId(Object userId) {
		this.userId = (String)userId;
	}

	/**
	 * ユーザーIDを設定する。<br/>
	 * <br/>
	 * @param userId ユーザーID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * MypageUserInterfaceインターフェースの実装メソッド<br/>
	 * 基本機能はこのフィールドを会員名（姓）として取得する。<br/>
	 * <br/>
	 * @return 会員名（姓）
	 */
	@Override
	public String getMemberLname() {
		return memberLname;
	}

	/**
	 * MypageUserInterfaceインターフェースの実装メソッド<br/>
	 * 基本機能はこのフィールドを会員名（姓）として設定する。<br/>
	 * <br/>
	 * @param memberLname 会員名（姓）
	 */
	@Override
	public void setMemberLname(String memberLname) {
		this.memberLname = memberLname;
	}

	/**
	 * MypageUserInterfaceインターフェースの実装メソッド<br/>
	 * 基本機能はこのフィールドを会員名（名）として取得する。<br/>
	 * <br/>
	 * @return 会員名（名）
	 */
	@Override
	public String getMemberFname() {
		return memberFname;
	}

	/**
	 * MypageUserInterfaceインターフェースの実装メソッド<br/>
	 * 基本機能はこのフィールドを会員名（名）として設定する。<br/>
	 * <br/>
	 * @param memberFname 会員名（名）
	 */
	@Override
	public void setMemberFname(String memberFname) {
		this.memberFname = memberFname;
	}

	/**
	 * MypageUserInterfaceインターフェースの実装メソッド<br/>
	 * 基本機能はこのフィールドを会員名・カナ（姓）として取得する。<br/>
	 * <br/>
	 * @return 会員名・カナ（姓）
	 */
	@Override
	public String getMemberLnameKana() {
		return memberLnameKana;
	}

	/**
	 * MypageUserInterfaceインターフェースの実装メソッド<br/>
	 * 基本機能はこのフィールドを会員名・カナ（姓）として設定する。<br/>
	 * <br/>
	 * @param memberLnameKana 会員名・カナ（姓）
	 */
	@Override
	public void setMemberLnameKana(String memberLnameKana) {
		this.memberLnameKana = memberLnameKana;
	}

	/**
	 * MypageUserInterfaceインターフェースの実装メソッド<br/>
	 * 基本機能はこのフィールドを会員名・カナ（名）として取得する。<br/>
	 * <br/>
	 * @return 会員名・カナ（名）
	 */
	@Override
	public String getMemberFnameKana() {
		return memberFnameKana;
	}

	/**
	 * MypageUserInterfaceインターフェースの実装メソッド<br/>
	 * 基本機能はこのフィールドを会員名・カナ（名）として設定する。<br/>
	 * <br/>
	 * @param memberFnameKana 会員名・カナ（名）
	 */
	@Override
	public void setMemberFnameKana(String memberFnameKana) {
		this.memberFnameKana = memberFnameKana;
	}

	/**
	 * MypageUserInterfaceインターフェースの実装メソッド<br/>
	 * 基本機能はこのフィールドをメールアドレスとして取得する。<br/>
	 * <br/>
	 * @return メールアドレス
	 */
	@Override
	public String getEmail() {
		return email;
	}

	/**
	 * MypageUserInterfaceインターフェースの実装メソッド<br/>
	 * 基本機能はこのフィールドをメールアドレスして設定する。<br/>
	 * <br/>
	 * @param email メールアドレス
	 */
	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * MypageUserInterfaceインターフェース(LoginUser　インターフェース)の実装メソッド<br/>
	 * フレームワークや、マイページ会員メンテナンス機能はこのメソッドの戻り値をマイページ会員のログインＩＤ
	 * として扱う。<br/>
	 * <br/>
	 * @return ログインID
	 */
	@Override
	public String getLoginId() {
		return email;
	}

	/**
	 * ログインIDを設定する。<br/>
	 * <br/>
	 * @param loginId ログインID
	 */
	public void setLoginId(String loginId) {
		this.email = loginId;
	}

	/**
	 * MypageUserInterfaceインターフェースの実装メソッド<br/>
	 * 基本機能はこのフィールドをパスワードとして取得する。<br/>
	 * <br/>
	 * @return パスワード （ハッシュ値）
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/**
	 * MypageUserInterfaceインターフェースの実装メソッド<br/>
	 * 基本機能はこのフィールドをパスワードとして設定する。<br/>
	 * <br/>
	 * @param password パスワード （ハッシュ値）
	 */
	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 自動ログイン用トークンを取得する。<br/>
	 * <br/>
	 * @return 自動ログイン用トークン
	 */
	public String getCookieLoginToken() {
		return cookieLoginToken;
	}

	/**
	 * 自動ログイン用トークンを設定する。<br/>
	 * <br/>
	 * @param cookieLoginToken 自動ログイン用トークン
	 */
	public void setCookieLoginToken(String cookieLoginToken) {
		this.cookieLoginToken = cookieLoginToken;
	}

	/**
	 * MypageUserInterfaceインターフェースの実装メソッド<br/>
	 * 基本機能はこのフィールドを前回ログイン日として取得する。<br/>
	 * <br/>
	 * @return 前回ログイン日
	 */
	@Override
	public Date getPreLoginDate() {
		return preLogin;
	}

	/**
	 * 前回ログイン日を取得する。<br/>
	 * <br/>
	 * @return 前回ログイン日
	 */
	public Date getPreLogin() {
		return preLogin;
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
	 * 最終ログイン日を取得する。<br/>
	 * <br/>
	 * @return 最終ログイン日
	 */
	public Date getLastLogin() {
		return lastLogin;
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
	 * MypageUserInterfaceインターフェースの実装メソッド<br/>
	 * 基本機能はこのフィールドを登録日として設定する。<br/>
	 * <br/>
	 * @param insDate 登録日
	 */
	@Override
	public Date getInsDate() {
		return insDate;
	}
	
	/**
	 * MypageUserInterfaceインターフェースの実装メソッド<br/>
	 * 基本機能はこのフィールドを登録日として取得する。<br/>
	 * <br/>
	 * @return 登録日
	 */
	@Override
	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}
	
	/**
	 * MypageUserInterfaceインターフェースの実装メソッド<br/>
	 * 基本機能はこのフィールドを登録者として設定する。<br/>
	 * <br/>
	 * @param insUserId 登録者
	 */
	@Override
	public String getInsUserId() {
		return insUserId;
	}
	
	/**
	 * MypageUserInterfaceインターフェースの実装メソッド<br/>
	 * 基本機能はこのフィールドを登録者として取得する。<br/>
	 * <br/>
	 * @return 登録者
	 */
	@Override
	public void setInsUserId(String insUserId) {
		this.insUserId = insUserId;
	}
	
	/**
	 * MypageUserInterfaceインターフェースの実装メソッド<br/>
	 * 基本機能はこのフィールドを最終更新日として設定する。<br/>
	 * <br/>
	 * @param updDate 最終更新日
	 */
	@Override
	public Date getUpdDate() {
		return updDate;
	}
	
	/**
	 * MypageUserInterfaceインターフェースの実装メソッド<br/>
	 * 基本機能はこのフィールドを最終更新日として取得する。<br/>
	 * <br/>
	 * @return 最終更新日
	 */
	@Override
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}
	
	/**
	 * MypageUserInterfaceインターフェースの実装メソッド<br/>
	 * 基本機能はこのフィールドを最終更新者として設定する。<br/>
	 * <br/>
	 * @param updUserId 最終更新者
	 */
	@Override
	public String getUpdUserId() {
		return updUserId;
	}
	
	/**
	 * MypageUserInterfaceインターフェースの実装メソッド<br/>
	 * 基本機能はこのフィールドを最終更新者として取得する。<br/>
	 * <br/>
	 * @return 最終更新者
	 */
	@Override
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}

	/**
	 * MypageUserInterfaceインターフェース（LoginUser インターフェース）の実装メソッド<br/>
	 * パスワード一致確認用メソッド<br>
	 * パスワードがDBから取得したパスワードと一致しているかをチェックし結果を復帰する。<br>
	 * <br>
	 * @param pPassword 入力されたパスワード
	 * @return true 一致 false 不一致
	 */
	@Override
	public boolean matchPassword(String pPassword) {
		return getPassword().equals(EncodingUtils.md5Encode(pPassword));
	}

	/**
	 * MypageUserInterfaceインターフェース（LastLoginTimestamped インターフェース）の実装メソッド<br/>
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
	 * MypageUserInterfaceインターフェース（LastLoginTimestamped インターフェース）の実装メソッド<br/>
	 * このメソッドに該当するフィールドを使用して最終ログイン日時を更新する。<br/>
	 * <br/>
	 * @param lLastLoginTimestamp 最終ログイン日
	 */
	@Override
	public void setThisLoginTimestamp(Date lLastLoginTimestamp) {
		this.lastLogin = lLastLoginTimestamp;
	}

	/**
	 * MypageUserInterfaceインターフェース（CookieLoginUser インターフェース）の実装メソッド<br/>
	 * フレームワークの認証処理は、このメソッドの戻り値を Cookie による自動ログインのトークンとして使用する。<br/>
	 * <br/>
	 * @return 自動ログインのトークン
	 */
	@Override
	public String getCookieLoginPassword() {
		return this.cookieLoginToken;
	}

	/**
	 * MypageUserInterfaceインターフェース（CookieLoginUser インターフェース）の実装メソッド<br/>
	 * フレームワークの認証処理は、このメソッドで Cookie による自動ログインのトークンを設定する。<br/>
	 * <br/>
	 * @param 自動ログインのトークン
	 */
	@Override
	public void setCookieLoginPassword(String token) {
		this.cookieLoginToken = token;
	}

	/**
	 * MypageUserInterfaceインターフェース（CookieLoginUser インターフェース）の実装メソッド<br/>
	 * フレームワークは、このメソッドの戻り値で Token の照合を行う。<br/>
	 * <br/>
	 * @return token が正しい場合は true
	 */
	@Override
	public boolean matchCookieLoginPassword(String myToken) {
		return (this.cookieLoginToken != null) && this.cookieLoginToken.equals(myToken);
	}

}
