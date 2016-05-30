package jp.co.transcosmos.dm3.core.model.mypage;

import java.util.Date;

import jp.co.transcosmos.dm3.login.CookieLoginUser;
import jp.co.transcosmos.dm3.login.LastLoginTimestamped;
import jp.co.transcosmos.dm3.login.LoginUser;

public interface MypageUserInterface extends LoginUser, CookieLoginUser, LastLoginTimestamped {

	/**
	 * マイページ会員のユーザーＩＤ（主キーの値）を設定する。<br/>
	 * このメソッドで設定するフィールドが、マイページ会員の主キー値として使用される。<br/>
	 * <br/>
	 * @param userId ユーザーＩＤ（主キー値）
	 */
	public void setUserId(Object userId);

	/**
	 * 会員名（姓）を取得する。<br/>
	 * <br/>
	 * @return 会員名（姓）
	 */
	public String getMemberLname();

	/**
	 * 会員名（姓）を設定する。<br/>
	 * <br/>
	 * @param memberLname 会員名（姓）
	 */
	public void setMemberLname(String memberLname);
	
	/**
	 * 会員名（名）を取得する。<br/>
	 * <br/>
	 * @return 会員名（名）
	 */
	public String getMemberFname();
	
	/**
	 * 会員名（名）を設定する。<br/>
	 * <br/>
	 * @param memberFname 会員名（名）
	 */
	public void setMemberFname(String memberFname);
	
	/**
	 * 会員名・カナ（姓）を取得する。<br/>
	 * <br/>
	 * @return 会員名・カナ（姓）
	 */
	public String getMemberLnameKana();

	/**
	 * 会員名・カナ（姓）を設定する。<br/>
	 * <br/>
	 * @param memberLnameKana 会員名・カナ（姓）
	 */
	public void setMemberLnameKana(String memberLnameKana);

	/**
	 * 会員名・カナ（名）を取得する。<br/>
	 * <br/>
	 * @return 会員名・カナ（名）
	 */
	public String getMemberFnameKana();
	
	/**
	 * 会員名・カナ（名）を設定する。<br/>
	 * <br/>
	 * @param memberFnameKana 会員名・カナ（名）
	 */
	public void setMemberFnameKana(String memberFnameKana);
	
	/**
	 * メールアドレスを取得する。 （マイページのログインID として使用する。）<br/>
	 * <br/>
	 * @return メールアドレス
	 */
	public String getEmail();
	
	/**
	 * メールアドレスを設定する。 （マイページのログインID として使用する。）<br/>
	 * <br/>
	 * @param email メールアドレス
	 */
	public void setEmail(String email);

	/**
	 * パスワードを取得する。<br/>
	 * <br/>
	 * @return パスワード （ハッシュ値）
	 */
	public String getPassword();

	/**
	 * パスワードを設定する。<br/>
	 * <br/>
	 * @param password パスワード （ハッシュ値）
	 */
	public void setPassword(String password);

	/** 前回ログイン日を取得する。<br/>
	 * <br/>
	 * @return 前回ログイン日
	 */
	public Date getPreLoginDate();
	
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
