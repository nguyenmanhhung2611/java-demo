package jp.co.transcosmos.dm3.corePana.model.mypage;

import java.util.Date;

import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.login.LockSupportLoginUser;

/**
 * Panasonic用 マイページユーザー用インターフェース.
 * アカウントロック機能用インターフェースを追加。<br/>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * tang.tianyun		2015.04.16	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public interface PanaMypageUserInterface extends MypageUserInterface, LockSupportLoginUser {

    /**
     * 住所・郵便番号 を取得する。<br/>
     * <br/>
     * @return 住所・郵便番号
     */
    public String getZip();

    /**
     * 住所・郵便番号 を設定する。<br/>
     * <br/>
     * @param zip
     */
    public void setZip(String zip);

    /**
     * 住所・都道府県CD を取得する。<br/>
     * <br/>
     * @return 住所・都道府県CD
     */
    public String getPrefCd();

    /**
     * 住所・都道府県CD を設定する。<br/>
     * <br/>
     * @param prefCd
     */
    public void setPrefCd(String prefCd);

    /**
     * 住所・市区町村名 を取得する。<br/>
     * <br/>
     * @return 住所・市区町村名
     */
    public String getAddress();

    /**
     * 住所・市区町村名 を設定する。<br/>
     * <br/>
     * @param address
     */
    public void setAddress(String address);

    /**
     * 住所・町名番地その他 を取得する。<br/>
     * <br/>
     * @return 住所・町名番地その他
     */
    public String getAddressOther();

    /**
     * 住所・町名番地その他 を設定する。<br/>
     * <br/>
     * @param addressOther
     */
    public void setAddressOther(String addressOther);

    /**
     * 電話番号 を取得する。<br/>
     * <br/>
     * @return 電話番号
     */
    public String getTel();

    /**
     * 電話番号 を設定する。<br/>
     * <br/>
     * @param TEL
     */
    public void setTel(String tel);

    /**
     * FAX を取得する。<br/>
     * <br/>
     * @return FAX
     */
    public String getFax();

    /**
     * FAX を設定する。<br/>
     * <br/>
     * @param FAX
     */
    public void setFax(String fax);

    /**
     * メール配信希望 を取得する。<br/>
     * <br/>
     * @return メール配信希望
     */
    public String getMailSendFlg();

    /**
     * メール配信希望 を設定する。<br/>
     * <br/>
     * @param mailSendFlg
     */
    public void setMailSendFlg(String mailSendFlg);

    /**
     * 居住状態 を取得する。<br/>
     * <br/>
     * @return 居住状態
     */
    public String getResidentFlg();

    /**
     * 居住状態 を設定する。<br/>
     * <br/>
     * @param residentFlg
     */
    public void setResidentFlg(String residentFlg);

    /**
     * 希望地域・都道府県 を取得する。<br/>
     * <br/>
     * @return 希望地域・都道府県
     */
    public String getHopePrefCd();

    /**
     * 希望地域・都道府県 を設定する。<br/>
     * <br/>
     * @param hopePrefCd
     */
    public void setHopePrefCd(String hopePrefCd);

    /**
     * 希望地域・市区町村 を取得する。<br/>
     * <br/>
     * @return 希望地域・市区町村
     */
    public String getHopeAddress();

    /**
     * 希望地域・市区町村 を設定する。<br/>
     * <br/>
     * @param hopeAddress
     */
    public void setHopeAddress(String hopeAddress);

    /**
     * 登録経路 を取得する。<br/>
     * <br/>
     * @return 登録経路
     */
    public String getEntryRoute();

    /**
     * 登録経路 を設定する。<br/>
     * <br/>
     * @param entryRoute
     */
    public void setEntryRoute(String entryRoute);

    /**
     * ログイン失敗回数 を取得する。<br/>
     * <br/>
     * @return ログイン失敗回数
     */
    public Integer getFailCnt();

    /**
     * ログイン失敗回数 を設定する。<br/>
     * <br/>
     * @param failCnt
     */
    public void setFailCnt(Integer failCnt);

    /**
     * 最終ログイン失敗日 を取得する。<br/>
     * <br/>
     * @return 最終ログイン失敗日
     */
    public Date getLastFailDate();

    /**
     * 最終ログイン失敗日 を設定する。<br/>
     * <br/>
     * @param lastFailDate
     */
    public void setLastFailDate(Date lastFailDate);

    /**
     * ロックフラグ を取得する。<br/>
     * <br/>
     * @return ロックフラグ
     */
    public String getLockFlg();

    /**
     * ロックフラグ を設定する。<br/>
     * <br/>
     * @param lockFlg
     */
    public void setLockFlg(String lockFlg);

	/**
	 * @return promoCd
	 */
	public String getPromoCd();

	/**
	 * @param promoCd セットする promoCd
	 */
	public void setPromoCd(String promoCd);

	/**
	 * @return refCd
	 */
	public String getRefCd();

	/**
	 * @param refCd セットする refCd
	 */
	public void setRefCd(String refCd);


	/**
	 * 管理ユーザーの最終ログイン日を取得する。<br/>
	 * <br/>
	 * @return 管理ユーザーの最終ログイン日
	 */
	public Date getLastLogin();

	/**
	 * 管理ユーザーの最終ログイン日を設定する。<br/>
	 * <br/>
	 * @param insDate 管理ユーザーの最終ログイン日
	 */
	public void setLastLogin(Date lastLogin);
}
