package jp.co.transcosmos.dm3.corePana.model.housing.form;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.validation.LengthValueValidation;
import jp.co.transcosmos.dm3.corePana.validation.PonitNumberValidation;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.NumberValidation;
import jp.co.transcosmos.dm3.validation.NumericValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * 物件基本情報メンテナンスの入力パラメータ受取り用フォーム.
 * <p>
 *
 * <pre>
 * 担当者      修正日     修正内容
 * ------------ ----------- -----------------------------------------------------
 * fan         2015.04.11  新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */

public class PanaHousingInfoForm implements Validateable {

    /** 共通コード変換処理 */
    private CodeLookupManager codeLookupManager;

    /**
     * デフォルトコンストラクター<br/>
     * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
     * <br/>
     */
    PanaHousingInfoForm() {
        super();
    }

    /**
     * コンストラクター<br/>
     * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
     * <br/>
     * @param　codeLookupManager　共通コード変換処理
     */
    PanaHousingInfoForm(CodeLookupManager codeLookupManager) {
        super();
        this.codeLookupManager = codeLookupManager;
    }

    /** システム物件CD */
    private String sysHousingCd;
    /** システム建物CD */
    private String sysBuildingCd;
    /** 物件番号 */
    private String housingCd;
    /** 物件名称 */
    private String displayHousingName;
    /** 物件種別 */
    private String housingKindCd;
    /** 価格 */
    private String price;
    /** 築年 */
    private String compDate;
    /** 間取り */
    private String layoutCd;
    /** 住所_郵便番号 */
    private String zip;

    /** 都道府県CD */
    private String prefCd;
    /** 都道府県名 */
    private String prefName;
    /** 市区町村CD */
    private String addressCd;
    /** 市区町村名 */
    private String addressName;

    /** 住所１ */
    private String addressOther1;
    /** 住所２ */
    private String addressOther2;

    /** 沿線CD */
    private String[] defaultRouteCd;
    /** 路線名・鉄道会社付(プレビュー用) */
    private String[] routeNameRr;
    /** 沿線名 */
    private String[] routeName;
    /** 駅CD */
    private String[] stationCd;
    /** 駅名 */
    private String[] stationName;

    /** 沿線名 */
    private String[] oldRouteName;
    /** 駅名 */
    private String[] oldStationName;

    /** 沿線CD */
    private String[] oldDefaultRouteCd;
    /** 駅CD */
    private String[] oldStationCd;

    /** バス会社名 */
    private String[] busCompany;
    /** バス停からの徒歩時間 */
    private String[] timeFromBusStop;

    /** 土地面積 */
    private String landArea;
    /** 土地面積_坪数 */
    private String landAreaCon;
    /** 土地面積_コメント */
    private String landAreaMemo;
    /** 専有面積 */
    private String personalArea;
    /** 専有面積_坪数 */
    private String personalAreaCon;
    /** 専有面積_コメント */
    private String personalAreaMemo;
    /** 建物面積 */
    private String buildingArea;
    /** 建物面積_坪数 */
    private String buildingAreaCon;
    /** 建物面積_コメント */
    private String buildingAreaMemo;
    private String command;
    private String comflg;
	/** 建ぺい率_補足 */
	private String coverageMemo;
	/** 容積率_補足 */
	private String buildingRateMemo;


	public String[] getRouteNameRr() {
		return routeNameRr;
	}

	public void setRouteNameRr(String[] routeNameRr) {
		this.routeNameRr = routeNameRr;
	}

	/**
     * 沿線名 パラメータを取得する。<br/>
     * <br/>
     *
     * @return 沿線名 パラメータ
     */
	public String[] getOldRouteName() {
		return oldRouteName;
	}
	/**
     * 沿線名 パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM oldRouteName 沿線名 パラメータ
     */
	public void setOldRouteName(String[] oldRouteName) {
		this.oldRouteName = oldRouteName;
	}
	/**
     * 駅名 パラメータを取得する。<br/>
     * <br/>
     *
     * @return 駅名 パラメータ
     */
	public String[] getOldStationName() {
		return oldStationName;
	}
	/**
     * 駅名 パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM oldStationName 駅名 パラメータ
     */
	public void setOldStationName(String[] oldStationName) {
		this.oldStationName = oldStationName;
	}
	/**
     * 沿線名CD パラメータを取得する。<br/>
     * <br/>
     *
     * @return 沿線名CD パラメータ
     */
	public String[] getOldDefaultRouteCd() {
		return oldDefaultRouteCd;
	}
	/**
     * 沿線名CD パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM oldDefaultRouteCd 沿線名CD パラメータ
     */
	public void setOldDefaultRouteCd(String[] oldDefaultRouteCd) {
		this.oldDefaultRouteCd = oldDefaultRouteCd;
	}
	/**
     * 駅名CD パラメータを取得する。<br/>
     * <br/>
     *
     * @return 駅名CD パラメータ
     */
	public String[] getOldStationCd() {
		return oldStationCd;
	}
	/**
     * 駅名CD パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM oldStationCd 駅名CD パラメータ
     */
	public void setOldStationCd(String[] oldStationCd) {
		this.oldStationCd = oldStationCd;
	}

	/**
     * システム物件CD パラメータを取得する。<br/>
     * システム物件CD パラメータの値は、フレームワークの URL マッピングで使用する システム物件CD クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return システム物件CD パラメータ
     */
    public String getSysBuildingCd() {
        return sysBuildingCd;
    }

    /**
     * システム物件CD パラメータを設定する。<BR/>
     * システム物件CD パラメータの値は、フレームワークの URL マッピングで使用する システム物件CD クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM sysBuildingCd システム物件CD パラメータ
     */
    public void setSysBuildingCd(String sysBuildingCd) {
        this.sysBuildingCd = sysBuildingCd;
    }

    /**
     * システム建物CD パラメータを取得する。<br/>
     * システム建物CD パラメータの値は、フレームワークの URL マッピングで使用する システム建物CD クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return システム建物CD パラメータ
     */
    public String getSysHousingCd() {
        return sysHousingCd;
    }

    /**
     * システム建物CD パラメータを設定する。<BR/>
     * システム建物CD パラメータの値は、フレームワークの URL マッピングで使用する システム建物CD クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM sysHousingCd システム建物CD パラメータ
     */
    public void setSysHousingCd(String sysHousingCd) {
        this.sysHousingCd = sysHousingCd;
    }

    /**
     * 物件番号 パラメータを取得する。<br/>
     * 物件番号 パラメータの値は、フレームワークの URL マッピングで使用する 物件番号 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 物件番号 パラメータ
     */
    public String getHousingCd() {
        return housingCd;
    }

    /**
     * 物件番号 パラメータを設定する。<BR/>
     * 物件番号 パラメータの値は、フレームワークの URL マッピングで使用する 物件番号 クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM housingCd 物件番号 パラメータ
     */
    public void setHousingCd(String housingCd) {
        this.housingCd = housingCd;
    }

    /**
     * 物件名称 パラメータを取得する。<br/>
     * 物件名称 パラメータの値は、フレームワークの URL マッピングで使用する 物件名称 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 物件名称 パラメータ
     */
    public String getDisplayHousingName() {
        return displayHousingName;
    }

    /**
     * 物件名称 パラメータを設定する。<BR/>
     * 物件名称 パラメータの値は、フレームワークの URL マッピングで使用する 物件名称 クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM displayHousingName 物件名称 パラメータ
     */
    public void setDisplayHousingName(String displayHousingName) {
        this.displayHousingName = displayHousingName;
    }

    /**
     * 物件種別 パラメータを取得する。<br/>
     * 物件種別 パラメータの値は、フレームワークの URL マッピングで使用する 物件種別 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 物件種別 パラメータ
     */
    public String getHousingKindCd() {
        return housingKindCd;
    }

    /**
     * 物件種別 パラメータを設定する。<BR/>
     * 物件種別 パラメータの値は、フレームワークの URL マッピングで使用する 物件種別 クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM housingKindCd 物件種別 パラメータ
     */
    public void setHousingKindCd(String housingKindCd) {
        this.housingKindCd = housingKindCd;
    }

    /**
     * 価格 パラメータを取得する。<br/>
     * 価格 パラメータの値は、フレームワークの URL マッピングで使用する 価格 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 価格 パラメータ
     */
    public String getPrice() {
        return price;
    }

    /**
     * 価格 パラメータを設定する。<BR/>
     * 価格 パラメータの値は、フレームワークの URL マッピングで使用する 価格 クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM price 価格 パラメータ
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * 築年 パラメータを取得する。<br/>
     * 築年 パラメータの値は、フレームワークの URL マッピングで使用する 築年 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return システムリフォームCD パラメータ
     */
    public String getCompDate() {
        return compDate;
    }

    /**
     * 築年 パラメータを設定する。<BR/>
     * 築年 パラメータの値は、フレームワークの URL マッピングで使用する 築年 クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM compDate 築年 パラメータ
     */
    public void setCompDate(String compDate) {
        this.compDate = compDate;
    }

    /**
     * 間取り パラメータを取得する。<br/>
     * 間取り パラメータの値は、フレームワークの URL マッピングで使用する 間取り クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 間取り パラメータ
     */
    public String getLayoutCd() {
        return layoutCd;
    }

    /**
     * 間取り パラメータを設定する。<BR/>
     * 間取り パラメータの値は、フレームワークの URL マッピングで使用する 間取り クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM layoutCd 間取り パラメータ
     */
    public void setLayoutCd(String layoutCd) {
        this.layoutCd = layoutCd;
    }

    /**
     * 住所_郵便番号 パラメータを取得する。<br/>
     * 住所_郵便番号 パラメータの値は、フレームワークの URL マッピングで使用する 住所_郵便番号 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 住所_郵便番号 パラメータ
     */
    public String getZip() {
        return zip;
    }

    /**
     * 住所_郵便番号 パラメータを設定する。<BR/>
     * 住所_郵便番号 パラメータの値は、フレームワークの URL マッピングで使用する 住所_郵便番号 クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM zip 住所_郵便番号 パラメータ
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * 都道府県CD パラメータを取得する。<br/>
     * 都道府県CD パラメータの値は、フレームワークの URL マッピングで使用する 都道府県CD クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 都道府県CD パラメータ
     */
    public String getPrefCd() {
        return prefCd;
    }

    /**
     * 都道府県CD パラメータを設定する。<BR/>
     * 都道府県CD パラメータの値は、フレームワークの URL マッピングで使用する 都道府県CD クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM prefCd 都道府県CD パラメータ
     */
    public void setPrefCd(String prefCd) {
        this.prefCd = prefCd;
    }

    /**
     * 都道府県名 パラメータを取得する。<br/>
     * 都道府県名 パラメータの値は、フレームワークの URL マッピングで使用する 都道府県名 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return システムリフォームCD パラメータ
     */
    public String getPrefName() {
        return prefName;
    }

    /**
     * 都道府県名 パラメータを設定する。<BR/>
     * 都道府県名 パラメータの値は、フレームワークの URL マッピングで使用する 都道府県名 クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM prefName 都道府県名 パラメータ
     */
    public void setPrefName(String prefName) {
        this.prefName = prefName;
    }

    /**
     * 市区町村CD パラメータを取得する。<br/>
     * 市区町村CD パラメータの値は、フレームワークの URL マッピングで使用する 市区町村CD クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return システムリフォームCD パラメータ
     */
    public String getAddressCd() {
        return addressCd;
    }

    /**
     * 市区町村CD パラメータを設定する。<BR/>
     * 市区町村CD パラメータの値は、フレームワークの URL マッピングで使用する 市区町村CD クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM addressCd 市区町村CD パラメータ
     */
    public void setAddressCd(String addressCd) {
        this.addressCd = addressCd;
    }

    /**
     * 市区町村名 パラメータを取得する。<br/>
     * 市区町村名 パラメータの値は、フレームワークの URL マッピングで使用する 市区町村名 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 市区町村名 パラメータ
     */
    public String getAddressName() {
        return addressName;
    }

    /**
     * 市区町村名 パラメータを設定する。<BR/>
     * 市区町村名 パラメータの値は、フレームワークの URL マッピングで使用する 市区町村名 クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM addressName 市区町村名 パラメータ
     */
    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    /**
     * 住所１ パラメータを取得する。<br/>
     * 住所１ パラメータの値は、フレームワークの URL マッピングで使用する 住所１ クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 住所１ パラメータ
     */
    public String getAddressOther1() {
        return addressOther1;
    }

    /**
     * 住所１ パラメータを設定する。<BR/>
     * 住所１ パラメータの値は、フレームワークの URL マッピングで使用する 住所１ クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM addressOther1 住所１ パラメータ
     */
    public void setAddressOther1(String addressOther1) {
        this.addressOther1 = addressOther1;
    }

    /**
     * 住所２ パラメータを取得する。<br/>
     * 住所２ パラメータの値は、フレームワークの URL マッピングで使用する 住所２ クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 住所２ パラメータ
     */
    public String getAddressOther2() {
        return addressOther2;
    }

    /**
     * 住所２ パラメータを設定する。<BR/>
     * 住所２ パラメータの値は、フレームワークの URL マッピングで使用する 住所２ クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM sysReformCd 住所２ パラメータ
     */
    public void setAddressOther2(String addressOther2) {
        this.addressOther2 = addressOther2;
    }

    /**
     * 沿線CD パラメータを取得する。<br/>
     * 沿線CD パラメータの値は、フレームワークの URL マッピングで使用する 沿線CD クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 沿線CD パラメータ
     */
    public String[] getDefaultRouteCd() {
        return defaultRouteCd;
    }

    /**
     * 沿線CD パラメータを設定する。<BR/>
     * 沿線CD パラメータの値は、フレームワークの URL マッピングで使用する 沿線CD クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM defaultRouteCd 沿線CD パラメータ
     */
    public void setDefaultRouteCd(String[] defaultRouteCd) {
        this.defaultRouteCd = defaultRouteCd;
    }

    /**
     * 沿線名 パラメータを取得する。<br/>
     * 沿線名 パラメータの値は、フレームワークの URL マッピングで使用する 沿線名 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 沿線名 パラメータ
     */
    public String[] getRouteName() {
        return routeName;
    }

    /**
     * 沿線名 パラメータを設定する。<BR/>
     * 沿線名 パラメータの値は、フレームワークの URL マッピングで使用する 沿線名 クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM routeName 沿線名 パラメータ
     */
    public void setRouteName(String[] routeName) {
        this.routeName = routeName;
    }

    /**
     * 駅CD パラメータを取得する。<br/>
     * 駅CD パラメータの値は、フレームワークの URL マッピングで使用する 駅CD クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 駅CD パラメータ
     */
    public String[] getStationCd() {
        return stationCd;
    }

    /**
     * 駅CD パラメータを設定する。<BR/>
     * 駅CD パラメータの値は、フレームワークの URL マッピングで使用する 駅CD クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM stationCd 駅CD パラメータ
     */
    public void setStationCd(String[] stationCd) {
        this.stationCd = stationCd;
    }

    /**
     * 駅名 パラメータを取得する。<br/>
     * 駅名 パラメータの値は、フレームワークの URL マッピングで使用する 駅名 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 駅名 パラメータ
     */
    public String[] getStationName() {
        return stationName;
    }

    /**
     * 駅名 パラメータを設定する。<BR/>
     * 駅名 パラメータの値は、フレームワークの URL マッピングで使用する 駅名 クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM stationName 駅名 パラメータ
     */
    public void setStationName(String[] stationName) {
        this.stationName = stationName;
    }
    /**
     * バス会社名バス停名 パラメータを取得する。<br/>
     * バス会社名バス停名 パラメータの値は、フレームワークの URL マッピングで使用する バス会社名バス停名 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return バス会社名バス停名 パラメータ
     */
    public String[] getBusCompany() {
		return busCompany;
	}
    /**
     * バス会社名バス停名 パラメータを設定する。<BR/>
     * バス会社名バス停名 パラメータの値は、フレームワークの URL マッピングで使用する バス会社名バス停名 クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM busCompany バス会社名バス停名 パラメータ
     */
	public void setBusCompany(String[] busCompany) {
		this.busCompany = busCompany;
	}

	/**
     * バス停からの徒歩時間 パラメータを取得する。<br/>
     * バス停からの徒歩時間 パラメータの値は、フレームワークの URL マッピングで使用する バス停からの徒歩時間 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return バス停からの徒歩時間 パラメータ
     */
    public String[] getTimeFromBusStop() {
        return timeFromBusStop;
    }

    /**
     * バス停からの徒歩時間 パラメータを設定する。<BR/>
     * バス停からの徒歩時間 パラメータの値は、フレームワークの URL マッピングで使用する バス停からの徒歩時間 クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM timeFromBusStop バス停からの徒歩時間 パラメータ
     */
    public void setTimeFromBusStop(String[] timeFromBusStop) {
        this.timeFromBusStop = timeFromBusStop;
    }

    /**
     * 土地面積 パラメータを取得する。<br/>
     * 土地面積 パラメータの値は、フレームワークの URL マッピングで使用する 土地面積 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 土地面積 パラメータ
     */
    public String getLandArea() {
        return landArea;
    }

    /**
     * 土地面積 パラメータを設定する。<BR/>
     * 土地面積 パラメータの値は、フレームワークの URL マッピングで使用する 土地面積 クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM landArea 土地面積 パラメータ
     */
    public void setLandArea(String landArea) {
        this.landArea = landArea;
    }

    /**
     * 土地面積_コメント パラメータを取得する。<br/>
     * 土地面積_コメント パラメータの値は、フレームワークの URL マッピングで使用する 土地面積_コメント クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 土地面積_コメント パラメータ
     */
    public String getLandAreaMemo() {
        return landAreaMemo;
    }

    /**
     * 土地面積_コメント パラメータを設定する。<BR/>
     * 土地面積_コメント パラメータの値は、フレームワークの URL マッピングで使用する 土地面積_コメント クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM landAreaMemo 土地面積_コメント パラメータ
     */
    public void setLandAreaMemo(String landAreaMemo) {
        this.landAreaMemo = landAreaMemo;
    }

    /**
     * 専有面積 パラメータを取得する。<br/>
     * 専有面積 パラメータの値は、フレームワークの URL マッピングで使用する専有面積 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 専有面積 パラメータ
     */
    public String getPersonalArea() {
        return personalArea;
    }

    /**
     * 専有面積 パラメータを設定する。<BR/>
     * 専有面積 パラメータの値は、フレームワークの URL マッピングで使用する 専有面積 クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM personalArea 専有面積 パラメータ
     */
    public void setPersonalArea(String personalArea) {
        this.personalArea = personalArea;
    }

    /**
     * 専有面積_コメント パラメータを取得する。<br/>
     * 専有面積_コメント パラメータの値は、フレームワークの URL マッピングで使用する 専有面積_コメント クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 専有面積_コメント パラメータ
     */
    public String getPersonalAreaMemo() {
        return personalAreaMemo;
    }

    /**
     * 専有面積_コメント パラメータを設定する。<BR/>
     * 専有面積_コメント パラメータの値は、フレームワークの URL マッピングで使用する 専有面積_コメント クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM personalAreaMemo 専有面積_コメント パラメータ
     */
    public void setPersonalAreaMemo(String personalAreaMemo) {
        this.personalAreaMemo = personalAreaMemo;
    }

    /**
     * 建物面積 パラメータを取得する。<br/>
     * 建物面積 パラメータの値は、フレームワークの URL マッピングで使用する 建物面積 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return システムリフォームCD パラメータ
     */
    public String getBuildingArea() {
        return buildingArea;
    }

    /**
     * 建物面積 パラメータを設定する。<BR/>
     * 建物面積 パラメータの値は、フレームワークの URL マッピングで使用する 建物面積 クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM buildingArea 建物面積 パラメータ
     */
    public void setBuildingArea(String buildingArea) {
        this.buildingArea = buildingArea;
    }

    /**
     * 建物面積_コメント パラメータを取得する。<br/>
     * 建物面積_コメント パラメータの値は、フレームワークの URL マッピングで使用する 建物面積_コメント クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 建物面積_コメント パラメータ
     */
    public String getBuildingAreaMemo() {
        return buildingAreaMemo;
    }

    /**
     * 建物面積_コメント パラメータを設定する。<BR/>
     * 建物面積_コメント パラメータの値は、フレームワークの URL マッピングで使用する 建物面積_コメント クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM buildingAreaMemo 建物面積_コメント パラメータ
     */
    public void setBuildingAreaMemo(String buildingAreaMemo) {
        this.buildingAreaMemo = buildingAreaMemo;
    }

    /**
     * Command パラメータを取得する。<br/>
     * Command パラメータの値は、フレームワークの URL マッピングで使用する Command クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return Command パラメータ
     */
    public String getCommand() {
        return command;
    }

    /**
     * Command パラメータを設定する。<BR/>
     * Command パラメータの値は、フレームワークの URL マッピングで使用する Command クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM command Command パラメータ
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * Comflg パラメータを取得する。<br/>
     * Comflg パラメータの値は、フレームワークの URL マッピングで使用する Comflg クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return システムリフォームCD パラメータ
     */
    public String getComflg() {
        return comflg;
    }

    /**
     * Comflg パラメータを設定する。<BR/>
     * Comflg パラメータの値は、フレームワークの URL マッピングで使用する Comflg クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM comflg Comflg パラメータ
     */
    public void setComflg(String comflg) {
        this.comflg = comflg;
    }

    /**
     * 土地面積_坪数 パラメータを取得する。<br/>
     * 土地面積_坪数 パラメータの値は、フレームワークの URL マッピングで使用する 土地面積_坪数 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 土地面積_坪数 パラメータ
     */
    public String getLandAreaCon() {
        return landAreaCon;
    }

    /**
     * 土地面積_坪数 パラメータを設定する。<BR/>
     * 土地面積_坪数 パラメータの値は、フレームワークの URL マッピングで使用する 土地面積_坪数 クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM landAreaCon 土地面積_坪数 パラメータ
     */
    public void setLandAreaCon(String landAreaCon) {
        this.landAreaCon = landAreaCon;
    }

    /**
     * 専有面積_坪数 パラメータを取得する。<br/>
     * 専有面積_坪数 パラメータの値は、フレームワークの URL マッピングで使用する 専有面積_坪数 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return システムリフォームCD パラメータ
     */
    public String getPersonalAreaCon() {
        return personalAreaCon;
    }

    /**
     * 専有面積_坪数 パラメータを設定する。<BR/>
     * 専有面積_坪数 パラメータの値は、フレームワークの URL マッピングで使用する 専有面積_坪数 クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM personalAreaCon 専有面積_坪数 パラメータ
     */
    public void setPersonalAreaCon(String personalAreaCon) {
        this.personalAreaCon = personalAreaCon;
    }

    /**
     * 建物面積_坪数 パラメータを取得する。<br/>
     * 建物面積_坪数 パラメータの値は、フレームワークの URL マッピングで使用する 建物面積_坪数 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 建物面積_坪数 パラメータ
     */
    public String getBuildingAreaCon() {
        return buildingAreaCon;
    }

    /**
     * システムリフォームCD パラメータを設定する。<BR/>
     * 建物面積_坪数 パラメータの値は、フレームワークの URL マッピングで使用する 建物面積_坪数 クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM buildingAreaCon 建物面積_坪数 パラメータ
     */
    public void setBuildingAreaCon(String buildingAreaCon) {
        this.buildingAreaCon = buildingAreaCon;
    }

    /**
	 * @return coverageMemo
	 */
	public String getCoverageMemo() {
		return coverageMemo;
	}

	/**
	 * @param coverageMemo セットする coverageMemo
	 */
	public void setCoverageMemo(String coverageMemo) {
		this.coverageMemo = coverageMemo;
	}

	/**
	 * @return buildingRateMemo
	 */
	public String getBuildingRateMemo() {
		return buildingRateMemo;
	}

	/**
	 * @param buildingRateMemo セットする buildingRateMemo
	 */
	public void setBuildingRateMemo(String buildingRateMemo) {
		this.buildingRateMemo = buildingRateMemo;
	}

	@Override
    public boolean validate(List<ValidationFailure> errors) {
        int startSize = errors.size();

        if ("address".equals(this.getCommand())) {
			if(StringValidateUtil.isEmpty(getZip())){
				ValidationFailure vf = new ValidationFailure(
                        "housingInfoZipNotInput", "", "", null);
                errors.add(vf);
			}

            // 住所_郵便番号
            ValidationChain valzip = new ValidationChain("housingInfo.input.zip", getZip());
            // 半角数字
            valzip.addValidation(new NumericValidation());
            // 最大桁数 7桁
            valzip.addValidation(new LengthValueValidation(7));
            valzip.validate(errors);

            return (startSize == errors.size());
        }
        // 物件名称
        ValidationChain valdisplayHousingName = new ValidationChain("housingInfo.input.displayHousingName",
                getDisplayHousingName());
        // 必須チェック
        valdisplayHousingName.addValidation(new NullOrEmptyCheckValidation());
        // 最大桁数 25桁
        valdisplayHousingName.addValidation(new MaxLengthValidation(25));
        valdisplayHousingName.validate(errors);

        // 物件種別
        ValidationChain valhousingKindCd = new ValidationChain("housingInfo.input.housingKindCd", getHousingKindCd());
        // パターンチェック
        valhousingKindCd.addValidation(new CodeLookupValidation(this.codeLookupManager, "buildingInfo_housingKindCd"));
        valhousingKindCd.validate(errors);

        // 価格
        ValidationChain valprice = new ValidationChain("housingInfo.input.price", getPrice());
        // 必須チェック
        valprice.addValidation(new NullOrEmptyCheckValidation());
        // 半角数字
        valprice.addValidation(new NumericValidation());
        // 最大桁数 11桁
        valprice.addValidation(new MaxLengthValidation(11));
        valprice.validate(errors);

        // 築年
        ValidationChain valcompDate = new ValidationChain("housingInfo.input.compDate", getCompDate());
        if(PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd()) || PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())){
	        // 必須チェック
	        valcompDate.addValidation(new NullOrEmptyCheckValidation());
        }
        valcompDate.validate(errors);
        // 日付書式チェック
        if (!StringValidateUtil.isEmpty(this.getCompDate())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			try {
				if (!sdf.format(sdf.parse(getCompDate().toString()+"01")).equals(getCompDate().toString()+"01")) {
					 ValidationFailure vf = new ValidationFailure("housingInfoCompDate", "housingInfo.input.compDate", "yyyyMM", null);
				     errors.add(vf);
				}
			} catch (ParseException e) {
				 ValidationFailure vf = new ValidationFailure("housingInfoCompDate", "housingInfo.input.compDate", "yyyyMM", null);
			     errors.add(vf);
			}
        }

        // 間取り
        ValidationChain vallayoutCd = new ValidationChain("housingInfo.input.layoutCd", getLayoutCd());
        if(PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd()) || PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())){
	        // 必須チェック
	        vallayoutCd.addValidation(new NullOrEmptyCheckValidation());
        }
        // パターンチェック
        vallayoutCd.addValidation(new CodeLookupValidation(this.codeLookupManager, "layoutCd"));
        vallayoutCd.validate(errors);

        // 住所_郵便番号
        ValidationChain valzip = new ValidationChain("housingInfo.input.zip", getZip());
        // 必須チェック
        valzip.addValidation(new NullOrEmptyCheckValidation());
        // 半角数字
        valzip.addValidation(new NumericValidation());
        // 最大桁数 7桁
        valzip.addValidation(new LengthValueValidation(7));
        valzip.validate(errors);

        // 所在地（都道府県）
        ValidationChain valPrefCd = new ValidationChain("housingInfo.input.prefCd", getPrefCd());
        // 必須チェック
        valPrefCd.addValidation(new NullOrEmptyCheckValidation());
        valPrefCd.validate(errors);

        // 所在地（市区町村）
        ValidationChain valAddressCd = new ValidationChain("housingInfo.input.addressCd", getAddressCd());
        // 必須チェック
        valAddressCd.addValidation(new NullOrEmptyCheckValidation());
        valAddressCd.validate(errors);

        // 住所１
        ValidationChain valaddressOther1 = new ValidationChain("housingInfo.input.addressOther1", getAddressOther1());
        // 必須チェック
        valaddressOther1.addValidation(new NullOrEmptyCheckValidation());
        // 最大桁数 30桁
        valaddressOther1.addValidation(new MaxLengthValidation(30));
        valaddressOther1.validate(errors);

        // 住所２
        ValidationChain valaddressOther2 = new ValidationChain("housingInfo.input.addressOther2", getAddressOther2());
        // 最大桁数 30桁
        valaddressOther2.addValidation(new MaxLengthValidation(30));
        valaddressOther2.validate(errors);

        if(this.getDefaultRouteCd() !=null){
        	for (int i = 0; i < this.getDefaultRouteCd().length; i++) {

                // バス停名
                ValidationChain valbusCompany = new ValidationChain("housingInfo.input.busCompany"+(i+1), getBusCompany()[i]);
                // 最大桁数 40桁
                valbusCompany.addValidation(new MaxLengthValidation(40));
                valbusCompany.validate(errors);

                // バス停からの徒歩時間
                ValidationChain valtimeFromBusStop = new ValidationChain("housingInfo.input.timeFromBusStop"+(i+1),
                        getTimeFromBusStop()[i]);
                // 半角数字
                valtimeFromBusStop.addValidation(new NumericValidation());
                // 最大桁数 3桁
                valtimeFromBusStop.addValidation(new MaxLengthValidation(3));
                valtimeFromBusStop.validate(errors);

                if ("0".equals(getTimeFromBusStop()[i])) {
                    ValidationFailure vf = new ValidationFailure(
                            "landMarkDistanceError", "バス停からの徒歩時間"+(i+1), "", null);
                    errors.add(vf);
                }
            }
        }

        // 建物面積
        ValidationChain valbuildingArea = new ValidationChain("housingInfo.input.buildingArea", getBuildingArea());
        if(PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())){
            // 必須チェック
            valbuildingArea.addValidation(new NullOrEmptyCheckValidation());
        }
        // 半角数字
        valbuildingArea.addValidation(new NumberValidation());
        valbuildingArea.addValidation(new PonitNumberValidation(5,2));
        valbuildingArea.validate(errors);

        // 建物面積_コメント
        ValidationChain valbuildingAreaMemo = new ValidationChain("housingInfo.input.buildingAreaMemo",
                getBuildingAreaMemo());
        // 最大桁数 10桁
        valbuildingAreaMemo.addValidation(new MaxLengthValidation(10));
        valbuildingAreaMemo.validate(errors);

        // 土地面積
        ValidationChain vallandArea = new ValidationChain("housingInfo.input.landArea", getLandArea());
        if(PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd()) || PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())){
	        // 必須チェック
	        vallandArea.addValidation(new NullOrEmptyCheckValidation());
        }
        // 半角数字
        vallandArea.addValidation(new NumberValidation());
        vallandArea.addValidation(new PonitNumberValidation(5,2));
        vallandArea.validate(errors);

        // 土地面積_コメント
        ValidationChain vallandAreaMemo = new ValidationChain("housingInfo.input.landAreaMemo", getLandAreaMemo());
        // 最大桁数 10桁
        vallandAreaMemo.addValidation(new MaxLengthValidation(10));
        vallandAreaMemo.validate(errors);

        // 専有面積
        ValidationChain valpersonalArea = new ValidationChain("housingInfo.input.personalArea", getPersonalArea());
        if(PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())){
	        // 必須チェック
	        valpersonalArea.addValidation(new NullOrEmptyCheckValidation());
        }
        // 半角数字
        valpersonalArea.addValidation(new NumberValidation());
        valpersonalArea.addValidation(new PonitNumberValidation(5,2));
        valpersonalArea.validate(errors);

        // 専有面積_コメント
        ValidationChain valpersonalAreaMemo = new ValidationChain("housingInfo.input.personalAreaMemo",
                getPersonalAreaMemo());
        // 最大桁数 10桁
        valpersonalAreaMemo.addValidation(new MaxLengthValidation(10));
        valpersonalAreaMemo.validate(errors);


        // （都道府県リスト：未入力　OR　市区町村リスト：未入力）AND 住所１：入力の場合
        if (!StringValidateUtil.isEmpty(this.getAddressOther1())) {
            if (StringValidateUtil.isEmpty(this.getPrefCd()) || StringValidateUtil.isEmpty(this.getAddressCd())) {
                String param[] = new String[] { "都道府県リストと市区町村リスト" };
                ValidationFailure vf = new ValidationFailure(
                        "housingInfoInput", "housingInfo.input.addressOther1", "", param);
                errors.add(vf);
            }
        }
        // （都道府県リスト：未入力　OR　市区町村リスト：未入力OR 住所１：未入力）AND 住所2：入力の場合
        if (!StringValidateUtil.isEmpty(this.getAddressOther2())) {
            if (StringValidateUtil.isEmpty(this.getPrefCd()) || StringValidateUtil.isEmpty(this.getAddressCd())
                    || StringValidateUtil.isEmpty(this.getAddressOther1())) {
                String param[] = new String[] { "都道府県リストと市区町村リストと住所１" };
                ValidationFailure vf = new ValidationFailure(
                        "housingInfoInput", "housingInfo.input.addressOther2", "", param);
                errors.add(vf);
            }
        }
        if(this.getDefaultRouteCd() != null){
        	for (int i = 0; i < this.getDefaultRouteCd().length; i++) {

                // （沿線リスト_1：未入力　OR　駅リスト_1：未入力　OR　バス停からの徒歩時間_1：未入力）AND バス会社名バス停名_1：入力の場合
                if (!StringValidateUtil.isEmpty(this.getBusCompany()[i])) {
                    if (StringValidateUtil.isEmpty(this.getDefaultRouteCd()[i])
                            || StringValidateUtil.isEmpty(this.getStationCd()[i])
                            || StringValidateUtil.isEmpty(this.getTimeFromBusStop()[i])) {
                        String param[] = new String[] { "沿線リスト_"+String.valueOf(i+1)+"と駅リスト_"+String.valueOf(i+1)+"と徒歩_"+String.valueOf(i+1) };
                        ValidationFailure vf = new ValidationFailure(
                                "housingInfoInput", "バス"+"_"+ String.valueOf(i+1), "", param);
                        errors.add(vf);
                    }
                }
                //（沿線リスト_1：未入力　OR　駅リスト_1：未入力　）AND バス停からの徒歩時間_1：入力の場合
                if (!StringValidateUtil.isEmpty(this.getTimeFromBusStop()[i])) {
                    if (StringValidateUtil.isEmpty(this.getDefaultRouteCd()[i])
                            || StringValidateUtil.isEmpty(this.getStationCd()[i])) {
                        String param[] = new String[] { "沿線リスト_"+String.valueOf(i+1)+"と駅リスト_"+String.valueOf(i+1) };
                        ValidationFailure vf = new ValidationFailure(
                                "housingInfoInput", "徒歩"+"_"+ String.valueOf(i+1), "", param);
                        errors.add(vf);
                    }
                }
            }
        }
        // 建物面積：未入力 AND 建物面積_コメント：入力の場合
        if (!StringValidateUtil.isEmpty(this.getBuildingAreaMemo())) {
            if (StringValidateUtil.isEmpty(this.getBuildingArea())) {
                String param[] = new String[] { "建物面積" };
                ValidationFailure vf = new ValidationFailure(
                        "housingInfoInput", "housingInfo.input.buildingAreaMemo", "", param);
                errors.add(vf);
            }
        }
        // 土地面積：未入力 AND 土地面積_コメント：入力の場合
        if (!StringValidateUtil.isEmpty(this.getLandAreaMemo())) {
            if (StringValidateUtil.isEmpty(this.getLandArea())) {
                String param[] = new String[] { "土地面積" };
                ValidationFailure vf = new ValidationFailure(
                        "housingInfoInput", "housingInfo.input.landAreaMemo", "", param);
                errors.add(vf);
            }
        }
        // 専有面積：未入力 AND 専有面積_コメント：入力の場合
        if (!StringValidateUtil.isEmpty(this.getPersonalAreaMemo())) {
            if (StringValidateUtil.isEmpty(this.getPersonalArea())) {
                String param[] = new String[] { "専有面積" };
                ValidationFailure vf = new ValidationFailure(
                        "housingInfoInput", "housingInfo.input.personalAreaMemo", "", param);
                errors.add(vf);
            }
        }

        return (startSize == errors.size());
    }

	/**
     * 登録条件オブジェクトを作成する。<br/>
     * 出力用なのでページ処理を除外した登録条件を生成する。<br/>
     * <br/>
     *
     * @return 登録条件オブジェクト
     */
    public BuildingDtlInfo newToBuildingDtlInfo() {
    	BuildingDtlInfo buildingDtlInfo = new BuildingDtlInfo();

    	 //システム建物CD
        buildingDtlInfo.setSysBuildingCd(this.sysBuildingCd);
        //建物面積
        if(this.buildingArea != null){
            if(!StringValidateUtil.isEmpty(this.buildingArea)){
                buildingDtlInfo.setBuildingArea(BigDecimal.valueOf(Double.valueOf(this.buildingArea)));
            }
        }
        //建物面積_補足
        buildingDtlInfo.setBuildingAreaMemo(formAndVo(this.getBuildingAreaMemo(),buildingDtlInfo.getBuildingAreaMemo()));

        //建ぺい率_補足
        buildingDtlInfo.setCoverageMemo(formAndVo(this.getCoverageMemo(),buildingDtlInfo.getCoverageMemo()));
        //容積率_補足
        buildingDtlInfo.setBuildingRateMemo(formAndVo(this.getBuildingRateMemo(),buildingDtlInfo.getBuildingRateMemo()));

        return buildingDtlInfo;
    }

    /**
     * フォームからVOデータを書き込む処理。<br/>
     * <br/>
     *
     * @param reformDtl
     *            書き込む対象VO
     * @param i
     *            アップロード対象画像リストのインデックス
     *
     */
    public void copyToBuildingDtlInfo(BuildingDtlInfo buildingDtlInfo) {

        //システム建物CD
        buildingDtlInfo.setSysBuildingCd(this.sysBuildingCd);
        //建物面積
        if(this.buildingArea != null){
            if(!StringValidateUtil.isEmpty(this.buildingArea)){
                buildingDtlInfo.setBuildingArea(BigDecimal.valueOf(Double.valueOf(this.buildingArea)));
            }
        }
        //建物面積_補足
        buildingDtlInfo.setBuildingAreaMemo(formAndVo(this.getBuildingAreaMemo(),buildingDtlInfo.getBuildingAreaMemo()));

        //建ぺい率_補足
        buildingDtlInfo.setCoverageMemo(formAndVo(this.getCoverageMemo(),buildingDtlInfo.getCoverageMemo()));
        //容積率_補足
        buildingDtlInfo.setBuildingRateMemo(formAndVo(this.getBuildingRateMemo(),buildingDtlInfo.getBuildingRateMemo()));
    }
    /**
     * String型の値を設定する。<br/>
     * <br/>
     * @return String
     */
	public String formAndVo(String form,String vo){
		String val;
		if(form == null && !StringValidateUtil.isEmpty(vo)){
			val=vo;
		}else{
			val=form;
		}
		return val;
	}
    /**
     * 渡されたバリーオブジェクトから Form へ初期値を設定する。<br/>
     * <br/>
     * @param adminUser AdminUserInterface　を実装した、ユーザー情報管理用バリーオブジェクト
     * @param adminRole AdminUserRoleInterface を実装したユーザーロール管理用バリーオブジェクト
     */
    public void setDefaultData(JoinResult housingInfo,JoinResult buildingInfo,List<JoinResult> buildingStationInfoList){

    	if(housingInfo != null){

			//システム建物CD
			this.sysBuildingCd = ((HousingInfo)housingInfo.getItems().get("housingInfo")).getSysBuildingCd();
			//物件番号
			this.housingCd=((HousingInfo)housingInfo.getItems().get("housingInfo")).getHousingCd();

			//物件名称
			this.displayHousingName=((HousingInfo)housingInfo.getItems().get("housingInfo")).getDisplayHousingName();
			//価格
			if(((HousingInfo)housingInfo.getItems().get("housingInfo")).getPrice() != null){
				this.price = String.valueOf(((HousingInfo)housingInfo.getItems().get("housingInfo")).getPrice());
			}
			//間取り
			this.layoutCd = ((HousingInfo)housingInfo.getItems().get("housingInfo")).getLayoutCd();
			//土地面積
			if(((HousingInfo)housingInfo.getItems().get("housingInfo")).getLandArea() !=null){
				this.landArea = String.valueOf(((HousingInfo)housingInfo.getItems().get("housingInfo")).getLandArea());
			}
			//土地面積_コメント
			this.landAreaMemo = ((HousingInfo)housingInfo.getItems().get("housingInfo")).getLandAreaMemo();
			//専有面積
			if(((HousingInfo)housingInfo.getItems().get("housingInfo")).getPersonalArea() !=null ){
				this.personalArea = String.valueOf(((HousingInfo)housingInfo.getItems().get("housingInfo")).getPersonalArea());
			}
			//専有面積_コメント
			this.personalAreaMemo = ((HousingInfo)housingInfo.getItems().get("housingInfo")).getPersonalAreaMemo();
		}
		if(buildingInfo !=null){
			//物件種別
			this.housingKindCd = ((BuildingInfo)buildingInfo.getItems().get("buildingInfo")).getHousingKindCd();
			//築年
			if(((BuildingInfo)buildingInfo.getItems().get("buildingInfo")).getCompDate()!=null){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
				this.compDate = String.valueOf(sdf.format(((BuildingInfo)buildingInfo.getItems().get("buildingInfo")).getCompDate())).substring(0, 6);
			}
			//住所_郵便番号
			if(!StringValidateUtil.isEmpty(((BuildingInfo)buildingInfo.getItems().get("buildingInfo")).getZip())){
				this.zip =((BuildingInfo)buildingInfo.getItems().get("buildingInfo")).getZip();
			}
			//都道府県CD
			this.prefCd = ((BuildingInfo)buildingInfo.getItems().get("buildingInfo")).getPrefCd();
			//都道府県名
			//form.setPrefName();
			//市区町村CD
			this.addressCd = ((BuildingInfo)buildingInfo.getItems().get("buildingInfo")).getAddressCd();
			//市区町村名
			this.addressName = ((BuildingInfo)buildingInfo.getItems().get("buildingInfo")).getAddressName();
			//住所１
			this.addressOther1 = ((BuildingInfo)buildingInfo.getItems().get("buildingInfo")).getAddressOther1();
			//住所２
			this.addressOther2 = ((BuildingInfo)buildingInfo.getItems().get("buildingInfo")).getAddressOther2();
			//建物面積
			if(((BuildingDtlInfo)buildingInfo.getItems().get("buildingDtlInfo")).getBuildingArea() !=null ){
				this.buildingArea = String.valueOf(((BuildingDtlInfo)buildingInfo.getItems().get("buildingDtlInfo")).getBuildingArea());
			}
			//建物面積_コメント
			this.buildingAreaMemo = ((BuildingDtlInfo)buildingInfo.getItems().get("buildingDtlInfo")).getBuildingAreaMemo();
		}
		if(buildingStationInfoList !=null && buildingStationInfoList.size()>0){
			//沿線CD
			this.defaultRouteCd = new String[buildingStationInfoList.size()];
			//沿線名
			this.routeName = new String[buildingStationInfoList.size()];
			//駅CD
			this.stationCd = new String[buildingStationInfoList.size()];
			//駅名
			this.stationName = new String[buildingStationInfoList.size()];

			//バス会社名
			this.busCompany = new String[buildingStationInfoList.size()];
			//バス停からの徒歩時間
			this.timeFromBusStop = new String[buildingStationInfoList.size()];
			for(int i=0;i<buildingStationInfoList.size();i++){
				this.defaultRouteCd[i]=((BuildingStationInfo)buildingStationInfoList.get(i).getItems().get("buildingStationInfo")).getDefaultRouteCd();
				this.routeName[i]=((BuildingStationInfo)buildingStationInfoList.get(i).getItems().get("buildingStationInfo")).getDefaultRouteName();
				this.stationCd[i]=((BuildingStationInfo)buildingStationInfoList.get(i).getItems().get("buildingStationInfo")).getStationCd();
				this.stationName[i]=((BuildingStationInfo)buildingStationInfoList.get(i).getItems().get("buildingStationInfo")).getStationName();
				this.busCompany[i]=((BuildingStationInfo)buildingStationInfoList.get(i).getItems().get("buildingStationInfo")).getBusCompany();
				if(((BuildingStationInfo)buildingStationInfoList.get(i).getItems().get("buildingStationInfo")).getTimeFromBusStop()!=null){
					this.timeFromBusStop[i]=String.valueOf(((BuildingStationInfo)buildingStationInfoList.get(i).getItems().get("buildingStationInfo")).getTimeFromBusStop());
				}
			}

			this.setOldRouteName(routeName);
			this.setOldStationName(stationName);
			this.setOldDefaultRouteCd(defaultRouteCd);
			this.setOldStationCd(stationCd);
		}
    }
}
