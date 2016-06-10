package jp.co.transcosmos.dm3.corePana.vo;

import java.util.Date;

import jp.co.transcosmos.dm3.corePana.model.mypage.PanaMypageUserInterface;
import jp.co.transcosmos.dm3.login.v3.DefaultLockChecker;
import jp.co.transcosmos.dm3.login.v3.LockSupportChecker;

/**
 * マイページ会員情報.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 * <pre>
 * 担当者        修正日        修正内容
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.03.10  新規作成
 * H.Mizuno		2015.04.16	アカウントロック機能用インターフェース追加
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 *
 */
public class MemberInfo extends jp.co.transcosmos.dm3.core.vo.MemberInfo implements PanaMypageUserInterface {

	private static final long serialVersionUID = 1L;

	/** 住所・郵便番号 */
    private String zip;
    /** 住所・都道府県CD */
    private String prefCd;
    /** 住所・区町村番地 */
    private String address;
    /** 住所・建物名 */
    private String addressOther;
    /** 電話番号 */
    private String tel;
    /** FAX */
    private String fax;
    /** メール配信希望 */
    private String mailSendFlg;
    /** 居住状態 */
    private String residentFlg;
    /** 希望地域・都道府県 */
    private String hopePrefCd;
    /** 希望地域・市区町村 */
    private String hopeAddress;
    /** 登録経路 */
    private String entryRoute;
    /** プロモCD */
    private String promoCd;
    /** 流入元CD */
    private String refCd;
    /** ログイン失敗回数 */
    private Integer failCnt;
    /** 最終ログイン失敗日 */
    private Date lastFailDate;
    /** ロックフラグ */
    private String lockFlg;


    
    /**
     * マイページユーザーのパスワード照合処理<br/>
     * <br/>
     * @param pPassword 入力されたパスワード
     * @return パスワードの照合に成功した場合は true を復帰
     */
    @Override
    public boolean matchPassword(String pPassword){

    	// 管理画面から、ロックフラグに "1" が設定されている場合、ロック中なので false を復帰する。
    	if ("1".equals(this.lockFlg)) return false;

    	// ロックフラグが設定されていない場合は通常の認証を行う。
    	return super.matchPassword(pPassword);
    }



	/**
	 * Cookie による自動ログインの妥当性判定処理<br/>
	 * Cookie に格納されているトークンをチェックし、問題なければ true を復帰する。<br\>
	 * <br/>
	 * @param pPassword 自動ログインで使用するトークン
	 * @return パラメータの照合が問題なかった場合、true を復帰する。
	 */
    @Override
    public boolean matchCookieLoginPassword(String pPassword){

    	// Cookie による自動ログイン時も、管理画面から、ロックフラグに "1" が設定されている場合、
    	// ロック中なので false を復帰する。
    	if ("1".equals(this.lockFlg)) return false;

    	// アカウントロックのチェッカーを使用してログイン失敗回数によるロック状態をチェックする。
    	// もしロック中の場合は false を復帰する。
    	// これをチェックしなくても、ログインが成立する事は無いが、最終ログイン日が更新されてしまう。
    	LockSupportChecker checker = new DefaultLockChecker();
    	if (checker.isLocked(this)) return false;

    	return super.matchCookieLoginPassword(pPassword);
    }



    /**
     * 住所・郵便番号 を取得する。<br/>
     * <br/>
     * @return 住所・郵便番号
     */
    public String getZip() {
        return zip;
    }

    /**
     * 住所・郵便番号 を設定する。<br/>
     * <br/>
     * @param zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * 住所・都道府県CD を取得する。<br/>
     * <br/>
     * @return 住所・都道府県CD
     */
    public String getPrefCd() {
        return prefCd;
    }

    /**
     * 住所・都道府県CD を設定する。<br/>
     * <br/>
     * @param prefCd
     */
    public void setPrefCd(String prefCd) {
        this.prefCd = prefCd;
    }

    /**
     * 住所・区町村番地 を取得する。<br/>
     * <br/>
     * @return 住所・区町村番地
     */
    public String getAddress() {
        return address;
    }

    /**
     * 住所・区町村番地 を設定する。<br/>
     * <br/>
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 住所・建物名 を取得する。<br/>
     * <br/>
     * @return 住所・建物名
     */
    public String getAddressOther() {
        return addressOther;
    }

    /**
     * 住所・建物名 を設定する。<br/>
     * <br/>
     * @param addressOther
     */
    public void setAddressOther(String addressOther) {
        this.addressOther = addressOther;
    }

    /**
     * 電話番号 を取得する。<br/>
     * <br/>
     * @return 電話番号
     */
    public String getTel() {
        return tel;
    }

    /**
     * 電話番号 を設定する。<br/>
     * <br/>
     * @param TEL
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * FAX を取得する。<br/>
     * <br/>
     * @return FAX
     */
    public String getFax() {
        return fax;
    }

    /**
     * FAX を設定する。<br/>
     * <br/>
     * @param FAX
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * メール配信希望 を取得する。<br/>
     * <br/>
     * @return メール配信希望
     */
    public String getMailSendFlg() {
        return mailSendFlg;
    }

    /**
     * メール配信希望 を設定する。<br/>
     * <br/>
     * @param mailSendFlg
     */
    public void setMailSendFlg(String mailSendFlg) {
        this.mailSendFlg = mailSendFlg;
    }

    /**
     * 居住状態 を取得する。<br/>
     * <br/>
     * @return 居住状態
     */
    public String getResidentFlg() {
        return residentFlg;
    }

    /**
     * 居住状態 を設定する。<br/>
     * <br/>
     * @param residentFlg
     */
    public void setResidentFlg(String residentFlg) {
        this.residentFlg = residentFlg;
    }

    /**
     * 希望地域・都道府県 を取得する。<br/>
     * <br/>
     * @return 希望地域・都道府県
     */
    public String getHopePrefCd() {
        return hopePrefCd;
    }

    /**
     * 希望地域・都道府県 を設定する。<br/>
     * <br/>
     * @param hopePrefCd
     */
    public void setHopePrefCd(String hopePrefCd) {
        this.hopePrefCd = hopePrefCd;
    }

    /**
     * 希望地域・市区町村 を取得する。<br/>
     * <br/>
     * @return 希望地域・市区町村
     */
    public String getHopeAddress() {
        return hopeAddress;
    }

    /**
     * 希望地域・市区町村 を設定する。<br/>
     * <br/>
     * @param hopeAddress
     */
    public void setHopeAddress(String hopeAddress) {
        this.hopeAddress = hopeAddress;
    }

    /**
     * 登録経路 を取得する。<br/>
     * <br/>
     * @return 登録経路
     */
    public String getEntryRoute() {
        return entryRoute;
    }

    /**
     * 登録経路 を設定する。<br/>
     * <br/>
     * @param entryRoute
     */
    public void setEntryRoute(String entryRoute) {
        this.entryRoute = entryRoute;
    }

    /**
     * プロモCD を取得する。<br/>
     * <br/>
     * @return プロモCD
     */
    public String getPromoCd() {
        return promoCd;
    }

    /**
     * プロモCD を設定する。<br/>
     * <br/>
     * @param promoCd
     */
    public void setPromoCd(String promoCd) {
        this.promoCd = promoCd;
    }

    /**
     * 流入元CD を取得する。<br/>
     * <br/>
     * @return 流入元CD
     */
    public String getRefCd() {
        return refCd;
    }

    /**
     * 流入元CD を設定する。<br/>
     * <br/>
     * @param refCd
     */
    public void setRefCd(String refCd) {
        this.refCd = refCd;
    }

    /**
     * ログイン失敗回数 を取得する。<br/>
     * <br/>
     * @return ログイン失敗回数
     */
    @Override
    public Integer getFailCnt() {
        return failCnt;
    }

    /**
     * ログイン失敗回数 を設定する。<br/>
     * <br/>
     * @param failCnt
     */
    public void setFailCnt(Integer failCnt) {
        this.failCnt = failCnt;
    }

    /**
     * 最終ログイン失敗日 を取得する。<br/>
     * <br/>
     * @return 最終ログイン失敗日
     */
    @Override
    public Date getLastFailDate() {
        return lastFailDate;
    }

    /**
     * 最終ログイン失敗日 を設定する。<br/>
     * <br/>
     * @param lastFailDate
     */
    public void setLastFailDate(Date lastFailDate) {
        this.lastFailDate = lastFailDate;
    }

    /**
     * ロックフラグ を取得する。<br/>
     * <br/>
     * @return ロックフラグ
     */
    public String getLockFlg() {
        return lockFlg;
    }

    /**
     * ロックフラグ を設定する。<br/>
     * <br/>
     * @param lockFlg
     */
    public void setLockFlg(String lockFlg) {
        this.lockFlg = lockFlg;
    }

}
