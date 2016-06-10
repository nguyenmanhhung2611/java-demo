package jp.co.transcosmos.dm3.corePana.constant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;

/**
 * 共通パラメータ情報クラス　（Panasonic 拡張版）.
 * <p>
 * アプリケーションが使用する共通パラーメータを管理する。<br/>
 * <p>
 *
 * <pre>
 * 担当者         修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.03.18  新規作成
 * Thi Tran     2015.10.16  Add new parameters for affiliate
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスから値を取得するには、commonParameters のオブジェクトを DI コンテナから取得するか、 getInstance()
 * で取得したインスタンスを使用する事。<br/>
 * また、アプリケーション側から setter を使用して値を変更しない事。<br/>
 * <br/>
 * 標準設定では JSP 用に、global.xml からリクエストスコープにも commonParameters の key で格納
 * されている。　利用可能かは、global.xml の設定を確認する事。<br/>
 *
 */
public class PanaCommonParameters extends CommonParameters {

    /** Yahoo Code for your Conversion Page **/
    private String yahooCodeSrc="//b91.yahoo.co.jp/pagead/conversion/1000277438/imp.gif";

	/** Janet URL */
	private String janetUrl = "https://action.j-a-net.jp//";
	
	/** Janet Member registration Id */
	private String janetMemberSid = "404221";
	
	/** Janet Assessment request id */
	private String janetAssessmentSid = "404222";
	
	/** Prefix of inquiry header Id */
	private String inquiryIdPrefix = "A";
	
	/** 物件画像の公開フォルダ（物理パス） 会員限定用 */
	private String housImgOpenPhysicalMemberPath = "/data/housing/open_member/";

	/** 物件画像の公開 root URL 会員限定用 */
	private String housImgMemberUrl = "/img/open_member/";

	/** バイト処理時に使用する文字コード */
	public static final String BYTE_CHECK_ENCODE = "UTF-8";

	/** 動的な画面URL */
	private String siteURL = "http://localhost:8080/";

	/** メールテンプレートのfromEmail */
	private String fromEmail = "";

	/** メールテンプレートのfromEmailName */
	private String fromEmailName = "";

	/** メールテンプレートのccEmail */
	private String ccEmail = "";

	/** メールテンプレートのbccEmail */
	private String bccEmail = "";

	/** メールテンプレートのreplyToEmail */
	private String replyToEmail = "";

	/** メールテンプレートのerrorToEmail */
	private String errorToEmail = "";

	/** Panasonic ReSMAIL事務局 住所 （メール本文等で使用） */
	private String reSmailZip = "";

	/** 問合せ先メールアドレス（メール本文等で使用） */
	private String inquiryToEmail = "";

	/** Panasonic ReSMAIL事務局 ナビダイヤル */
	private String reSmailTel = "";

	/** Panasonic ReSMAIL URL */
	private String reSmailUrl = "";

	/** Panasonic ReSMAIL 受付時間 */
	private String reSmailUktskJkn = "";

	/** 【Panasonic ReaRie】 */
	private String panasonicReSmail = "【Panasonic ReaRie】";

	/** 【Panasonic ReaRie事務局】 */
	private String panasonicReSmailAffairs = "【Panasonic ReaRie事務局】";

	/** Panasonic ファイルサーバの URL */
	private String fileSiteURL = "";

	/** 最近見た物件登録上限件数 */
	private int maxRecentlyInfoCnt;

	/** お気に入り情報登録上限件数 */
	private int maxFavoriteInfoCnt;

	/** フロント側画像ファイル、CSS などの、静的ファイルのルートパス */
	private String frtResourceRootUrl = "/front_rsc/";

	/** フロント側デフォルトkeyword */
	private String defaultKeyword;

	/** フロント側デデフォルトdescription */
	private String defaultDescription;

	/** パナソニックリスマイル：タイトル用 */
	private String panaReSmail = "パナソニック ReaRie(リアリエ)";

	/** 物件一覧、お気に入り物件一覧、最近見た物件一覧の画像サイズ */
	private String housingListImageSize = "200";

	/** 物件詳細の物件情報左側の大きい画像(リフォーム後、リフォーム有りの箇所も同様)画像サイズ */
	private String housingDtlImageLargeSize = "800";
	/** 物件情報右側の小さい画像(リフォーム後、リフォーム有りの箇所も同様)画像サイズ */
	private String housingDtlImageSmallSize = "200";
	/** 物件詳細の担当者の画像画像サイズ */
	private String housingDtlStaffImageSize = "120";
	/** 物件詳細の最近見た物件の外観画像画像サイズ */
	private String housingDtlHistoryImageSize = "86";

	/** 物件お問い合わせの外観画像画像サイズ */
	private String housingInquiryImageSize = "200";

	/** マイページ(ログイン後)のお気に入り物件外観画像サイズ */
	private String mypageFavoriteImageSize = "86";
	/** マイページ(ログイン後)の最近見た物件の外観画像サイズ */
	private String mypageHistoryImageSize = "86";

	/** 管理サイト画面表示画像サイズ */
	private String adminSiteSmallImageSize = "86";
	/** 管理サイトポップアップ画面表示画像サイズ */
	private String adminSiteLargeImageSize = "800";
	/** 管理サイト画面表示用担当者写真サイズ */
	private String adminSiteStaffImageSize = "120";
	/** 管理サイトPDFサブフォルダー名 */
	private String adminSitePdfFolder = "pdf";
	/** 管理サイト担当者写真サブフォルダー名 */
	private String adminSiteStaffFolder = "staff";
	/** 管理サイトレーダチャットサブフォルダー名 */
	private String adminSiteChartFolder = "chart";
	/** 管理サイトイメージ源ファイルのサブフォルダー名 */
	private String adminSiteFullFolder = "full";

	/** 画像ファイル、CSS などの、静的ファイルのルートパス admin側とfront側共通用 */
	private String commonResourceRootUrl = "";

	/**パナソニックサイト名英字 */
	private String panasonicSiteEnglish = "ReaRie";

	/**パナソニックサイトカタカナ */
	private String panasonicSiteJapan = "リアリエ";

	/** マイページログイン URL　（メール本文等で使用） **/
	private String mypageTopUrl = "";

	/** 画像なしの場合の表示画像86サイズ **/
	private String noPhoto86 = "buy/img/nophoto/nophoto_86.jpg";

	/** 画像なしの場合の表示画像110サイズ **/
	private String noPhoto110 = "buy/img/nophoto/nophoto_110.jpg";

	/** 画像なしの場合の表示画像200サイズ **/
	private String noPhoto200 = "buy/img/nophoto/nophoto_200.jpg";

	/** 画像なしの場合の表示画像800サイズ **/
	private String noPhoto800 = "buy/img/nophoto/nophoto_800.jpg";


	public String getNoPhoto86() {
		return noPhoto86;
	}
	public void setNoPhoto86(String noPhoto86) {
		this.noPhoto86 = noPhoto86;
	}
	public String getNoPhoto110() {
		return noPhoto110;
	}
	public void setNoPhoto110(String noPhoto110) {
		this.noPhoto110 = noPhoto110;
	}
	public String getNoPhoto200() {
		return noPhoto200;
	}
	public void setNoPhoto200(String noPhoto200) {
		this.noPhoto200 = noPhoto200;
	}
	public String getNoPhoto800() {
		return noPhoto800;
	}
	public void setNoPhoto800(String noPhoto800) {
		this.noPhoto800 = noPhoto800;
	}
	/**
	 * マイページログイン URL を取得する。<br/>
	 * <br/>
	 * @return マイページログイン URL
	 */
	public String getMypageTopUrl() {
		return mypageTopUrl;
	}

	/**
	 * マイページログイン URL を設定する。<br/>
	 * <br/>
	 * @param adminLoginUrlマイページログイン URL
	 */
	public void setMypageTopUrl(String mypageTopUrl) {
		this.mypageTopUrl = mypageTopUrl;
	}

	/**
	 * 物件画像の公開フォルダ（物理パス） を取得する。　（会員限定用）<br/>
	 * <br/>
	 *
	 * @return 物件画像の公開フォルダ（物理パス） ・会員限定用
	 */
	public String getHousImgOpenPhysicalMemberPath() {
		return housImgOpenPhysicalMemberPath;
	}

	/**
	 * 物件画像の公開フォルダ（物理パス） を設定する。　（会員限定用）<br/>
	 * <br/>
	 *
	 * @param housImgOpenPhysicalMemberPath
	 *            物件画像の公開フォルダ（物理パス）・会員限定用
	 */
	public void setHousImgOpenPhysicalMemberPath(
			String housImgOpenPhysicalMemberPath) {
		this.housImgOpenPhysicalMemberPath = housImgOpenPhysicalMemberPath;
	}

	/**
	 * フロント側画像ファイル、CSS などの、静的ファイルのルートパスを取得する。<br/>
	 * <br/>
	 *
	 * @return フロント側画像ファイル、CSS などの、静的ファイルのルートパス
	 */
	public String getFrtResourceRootUrl() {
		return frtResourceRootUrl;
	}

	/**
	 * フロント側画像ファイル、CSS などの、静的ファイルのルートパスを設定する。<br/>
	 * <br/>
	 *
	 * @param frtResourceRootUrl
	 *            フロント側画像ファイル、CSS などの、静的ファイルのルートパス
	 */
	public void setFrtResourceRootUrl(String frtResourceRootUrl) {
		this.frtResourceRootUrl = frtResourceRootUrl;
	}


	public void setSiteURL(String siteURL) {
		this.siteURL = siteURL;
	}

	public String getSiteURL() {
		return siteURL;
	}

	/**
	 * fromEmailを取得する。<br/>
	 *
	 * @return fromEmail
	 */
	public String getFromEmail() {
		return fromEmail;
	}

	/**
	 * @param fromEmail
	 *            セットする fromEmail
	 */
	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	/**
	 * fromEmailNameを取得する。<br/>
	 *
	 * @return errorToEmail
	 */
	public String getFromEmailName() {
		return fromEmailName;
	}

	/**
	 * @param fromEmailName
	 *            セットする fromEmailName
	 */
	public void setFromEmailName(String fromEmailName) {
		this.fromEmailName = fromEmailName;
	}

	/**
	 * ccEmailを取得する。<br/>
	 *
	 * @return errorToEmail
	 */
	public String getCcEmail() {
		return ccEmail;
	}

	/**
	 * @param ccEmail
	 *            セットする ccEmail
	 */
	public void setCcEmail(String ccEmail) {
		this.ccEmail = ccEmail;
	}

	/**
	 * replyToEmailを取得する。<br/>
	 *
	 * @return errorToEmail
	 */
	public String getReplyToEmail() {
		return replyToEmail;
	}

	/**
	 * @param replyToEmail
	 *            セットする replyToEmail
	 */
	public void setReplyToEmail(String replyToEmail) {
		this.replyToEmail = replyToEmail;
	}

	/**
	 * errorToEmailを取得する。<br/>
	 *
	 * @return errorToEmail
	 */
	public String getErrorToEmail() {
		return errorToEmail;
	}

	/**
	 * @param errorToEmail
	 *            セットする errorToEmail
	 */
	public void setErrorToEmail(String errorToEmail) {
		this.errorToEmail = errorToEmail;
	}

	/**
	 * ReSMAIL事務局 住所を取得する。<br/>
	 * <br/>
	 *
	 * @return ReSMAIL事務局 住所
	 */
	public String getReSmailZip() {
		return reSmailZip;
	}

	/**
	 * ReSMAIL事務局 住所を設定する。<br/>
	 * <br/>
	 */
	public void setReSmailZip(String reSmailZip) {
		this.reSmailZip = reSmailZip;
	}

	/**
	 * 問合せ先メールアドレスを取得する。<br/>
	 * <br/>
	 *
	 * @return 問合せ先メールアドレス
	 */
	public String getInquiryToEmail() {
		return inquiryToEmail;
	}

	/**
	 * 問合せ先メールアドレスを設定する。<br/>
	 * <br/>
	 */
	public void setInquiryToEmail(String inquiryToEmail) {
		this.inquiryToEmail = inquiryToEmail;
	}

	/**
	 * Panasonic ReSMAIL ナビダイヤルを取得する。<br/>
	 * <br/>
	 *
	 * @return Panasonic ReSMAIL ナビダイヤル
	 */
	public String getReSmailTel() {
		return reSmailTel;
	}

	/**
	 * Panasonic ReSMAIL ナビダイヤルを設定する。<br/>
	 * <br/>
	 */
	public void setReSmailTel(String reSmailTel) {
		this.reSmailTel = reSmailTel;
	}

	/**
	 * Panasonic ReSMAIL URLを取得する。<br/>
	 * <br/>
	 *
	 * @return Panasonic ReSMAIL URL
	 */
	public String getReSmailUrl() {
		return reSmailUrl;
	}

	/**
	 * Panasonic ReSMAIL URLを設定する。<br/>
	 * <br/>
	 */
	public void setReSmailUrl(String reSmailUrl) {
		this.reSmailUrl = reSmailUrl;
	}

	/**
	 * @return reSmailUktskJkn
	 */
	public String getReSmailUktskJkn() {
		return reSmailUktskJkn;
	}

	/**
	 * @param reSmailUktskJkn
	 *            セットする reSmailUktskJkn
	 */
	public void setReSmailUktskJkn(String reSmailUktskJkn) {
		this.reSmailUktskJkn = reSmailUktskJkn;
	}

	public String getPanasonicReSmail() {
		return panasonicReSmail;
	}

	public void setPanasonicReSmail(String panasonicReSmail) {
		this.panasonicReSmail = panasonicReSmail;
	}

	public String getPanasonicSiteEnglish() {
		return panasonicSiteEnglish;
	}

	public void setPanasonicSiteEnglish(String panasonicSiteEnglish) {
		this.panasonicSiteEnglish = panasonicSiteEnglish;
	}

	public String getPanasonicSiteJapan() {
		return panasonicSiteJapan;
	}

	public void setPanasonicSiteJapan(String panasonicSiteJapan) {
		this.panasonicSiteJapan = panasonicSiteJapan;
	}

	public String getPanasonicReSmailAffairs() {
		return panasonicReSmailAffairs;
	}

	public void setPanasonicReSmailAffairs(String panasonicReSmailAffairs) {
		this.panasonicReSmailAffairs = panasonicReSmailAffairs;
	}

	/**
	 * @return bccEmail
	 */
	public String getBccEmail() {
		return bccEmail;
	}

	/**
	 * @param bccEmail
	 *            セットする bccEmail
	 */
	public void setBccEmail(String bccEmail) {
		this.bccEmail = bccEmail;
	}

	/**
	 * @return ファイルサーバの URL
	 */
	public String getFileSiteURL() {
		return fileSiteURL;
	}

	/**
	 * @param ファイルサーバの
	 *            URL セットする
	 */
	public void setFileSiteURL(String fileSiteURL) {
		this.fileSiteURL = fileSiteURL;
	}

	/**
	 * 最近見た物件登録上限件数を取得する。<br/>
	 * <br/>
	 *
	 * @return 最近見た物件登録上限件数
	 */
	public int getMaxRecentlyInfoCnt() {
		return maxRecentlyInfoCnt;
	}

	/**
	 * 最近見た物件登録上限件数を設定する。<br/>
	 * <br/>
	 */
	public void setMaxRecentlyInfoCnt(int recentlyInfoMax) {
		this.maxRecentlyInfoCnt = recentlyInfoMax;
	}

	/**
	 * お気に入り情報登録上限件数を取得する。<br/>
	 * <br/>
	 *
	 * @return お気に入り情報登録上限件数
	 */
	public int getMaxFavoriteInfoCnt() {
		return maxFavoriteInfoCnt;
	}

	/**
	 * お気に入り情報登録上限件数を設定する。<br/>
	 * <br/>
	 */
	public void setMaxFavoriteInfoCnt(int favoriteInfoMax) {
		this.maxFavoriteInfoCnt = favoriteInfoMax;
	}

	/** フロント側デフォルトkeyword */
	public String getDefaultKeyword() {
		return defaultKeyword;
	}

	/** フロント側デフォルトkeyword */
	public void setDefaultKeyword(String defaultKeyword) {
		this.defaultKeyword = defaultKeyword;
	}

	/** フロント側デデフォルトdescription */
	public String getDefaultDescription() {
		return defaultDescription;
	}

	/** フロント側デデフォルトdescription */
	public void setDefaultDescription(String defaultDescription) {
		this.defaultDescription = defaultDescription;
	}

	/** パナソニックリスマイル */
	public String getPanaReSmail() {
		return panaReSmail;
	}

	/** パナソニックリスマイル */
	public void setPanaReSmail(String panaReSmail) {
		this.panaReSmail = panaReSmail;
	}

	public String getHousingListImageSize() {
		return housingListImageSize;
	}

	public void setHousingListImageSize(String housingListImageSize) {
		this.housingListImageSize = housingListImageSize;
	}

	public String getHousingDtlImageLargeSize() {
		return housingDtlImageLargeSize;
	}

	public void setHousingDtlImageLargeSize(String housingDtlImageLargeSize) {
		this.housingDtlImageLargeSize = housingDtlImageLargeSize;
	}

	public String getHousingDtlImageSmallSize() {
		return housingDtlImageSmallSize;
	}

	public void setHousingDtlImageSmallSize(String housingDtlImageSmallSize) {
		this.housingDtlImageSmallSize = housingDtlImageSmallSize;
	}

	public String getHousingDtlStaffImageSize() {
		return housingDtlStaffImageSize;
	}

	public void setHousingDtlStaffImageSize(String housingDtlStaffImageSize) {
		this.housingDtlStaffImageSize = housingDtlStaffImageSize;
	}

	public String getHousingDtlHistoryImageSize() {
		return housingDtlHistoryImageSize;
	}

	public void setHousingDtlHistoryImageSize(String housingDtlHistoryImageSize) {
		this.housingDtlHistoryImageSize = housingDtlHistoryImageSize;
	}

	public String getHousingInquiryImageSize() {
		return housingInquiryImageSize;
	}

	public void setHousingInquiryImageSize(String housingInquiryImageSize) {
		this.housingInquiryImageSize = housingInquiryImageSize;
	}

	public String getMypageFavoriteImageSize() {
		return mypageFavoriteImageSize;
	}

	public void setMypageFavoriteImageSize(String mypageFavoriteImageSize) {
		this.mypageFavoriteImageSize = mypageFavoriteImageSize;
	}

	public String getMypageHistoryImageSize() {
		return mypageHistoryImageSize;
	}

	public void setMypageHistoryImageSize(String mypageHistoryImageSize) {
		this.mypageHistoryImageSize = mypageHistoryImageSize;
	}

	public String getHousImgMemberUrl() {
		return housImgMemberUrl;
	}

	public void setHousImgMemberUrl(String housImgMemberUrl) {
		this.housImgMemberUrl = housImgMemberUrl;
	}

	public String getAdminSiteSmallImageSize() {
		return adminSiteSmallImageSize;
	}

	public void setAdminSiteSmallImageSize(String adminSiteSmallImageSize) {
		this.adminSiteSmallImageSize = adminSiteSmallImageSize;
	}

	public String getAdminSiteLargeImageSize() {
		return adminSiteLargeImageSize;
	}

	public void setAdminSiteLargeImageSize(String adminSiteLargeImageSize) {
		this.adminSiteLargeImageSize = adminSiteLargeImageSize;
	}

	public String getAdminSiteStaffImageSize() {
		return adminSiteStaffImageSize;
	}

	public void setAdminSiteStaffImageSize(String adminSiteStaffImageSize) {
		this.adminSiteStaffImageSize = adminSiteStaffImageSize;
	}

	public String getAdminSitePdfFolder() {
		return adminSitePdfFolder;
	}

	public void setAdminSitePdfFolder(String adminSitePdfFolder) {
		this.adminSitePdfFolder = adminSitePdfFolder;
	}

	public String getAdminSiteStaffFolder() {
		return adminSiteStaffFolder;
	}

	public void setAdminSiteStaffFolder(String adminSiteStaffFolder) {
		this.adminSiteStaffFolder = adminSiteStaffFolder;
	}

	public String getAdminSiteChartFolder() {
		return adminSiteChartFolder;
	}

	public void setAdminSiteChartFolder(String adminSiteChartFolder) {
		this.adminSiteChartFolder = adminSiteChartFolder;
	}

	public String getAdminSiteFullFolder() {
		return adminSiteFullFolder;
	}

	public void setAdminSiteFullFolder(String adminSiteFullFolder) {
		this.adminSiteFullFolder = adminSiteFullFolder;
	}

	/**
	 * 画像ファイル、CSS などの、静的ファイルのルートパス admin側とfront側共通用を設定する。<br/>
	 * <br/>
	 */
	public String getCommonResourceRootUrl() {
		return commonResourceRootUrl;
	}

	/**
	 * 画像ファイル、CSS などの、静的ファイルのルートパス admin側とfront側共通用を取得する。<br/>
	 * <br/>
	 *
	 * @return 画像ファイル、CSS などの、静的ファイルのルートパス admin側とfront側共通用
	 */
	public void setCommonResourceRootUrl(String commonResourceRootUrl) {
		this.commonResourceRootUrl = commonResourceRootUrl;
	}
	/**
     * Get yahoo code source
     * @return
     */
    public String getYahooCodeSrc() {
        return yahooCodeSrc;
    }
    /**
     * Set yahoo code source
     * @param yahooCodeSrc
     */
    public void setYahooCodeSrc(String yahooCodeSrc) {
        this.yahooCodeSrc = yahooCodeSrc;
    }
    /**
	 * Get janet URL
     * @return the janetUrl
     */
    public String getJanetUrl() {
        return janetUrl;
    }
    /**
     * Set janet URL
     * @param janetUrl the janetUrl to set
     */
    public void setJanetUrl(String janetUrl) {
        this.janetUrl = janetUrl;
    }
    /**
     * Get janet ID for registration member
     * @return the janetMemberSid
     */
    public String getJanetMemberSid() {
        return janetMemberSid;
    }
    /**
     * Set janet ID for registration member
     * @param janetMemberSid the janetMemberSid to set
     */
    public void setJanetMemberSid(String janetMemberSid) {
        this.janetMemberSid = janetMemberSid;
    }
    /**
     * Get janet ID for creating assessment inquiry
     * @return the janetAssessmentSid
     */
    public String getJanetAssessmentSid() {
        return janetAssessmentSid;
    }
    /**
     * Set janet ID for creating assessment inquiry
     * @param janetAssessmentSid the janetAssessmentSid to set
     */
    public void setJanetAssessmentSid(String janetAssessmentSid) {
        this.janetAssessmentSid = janetAssessmentSid;
    }
    /**
     * Get prefix of assessment inquiry ID
     * @return the inquiryIdPrefix
     */
    public String getInquiryIdPrefix() {
        return inquiryIdPrefix;
    }
    /**
     * Set prefix of assessment inquiry ID
     * @param inquiryIdPrefix the inquiryIdPrefix to set
     */
    public void setInquiryIdPrefix(String inquiryIdPrefix) {
        this.inquiryIdPrefix = inquiryIdPrefix;
    }
    /**
	 * PanaCommonParameters のインスタンスを取得する。<br/>
	 * Spring のコンテキストから、 PanaCommonParameters で定義された PanaCommonParameters のインスタンスを取得する。<br/>
	 * 取得されるインスタンスは、PanaCommonParameters を継承した拡張クラスが復帰される場合もある。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return PanaCommonParameters、または継承して拡張したクラスのインスタンス
	 */
	public static PanaCommonParameters getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (PanaCommonParameters)springContext.getBean(COMMON_PARAMETERS_BEAN_ID);
	}
}
