package jp.co.transcosmos.dm3.corePana.model.housing.form;

import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.model.housing.form.HousingSearchForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.validation.DateFromToValidation;
import jp.co.transcosmos.dm3.form.annotation.UsePagingParam;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.validation.AlphanumericOnlyValidation;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.ValidDateValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * 物件一覧情報の検索パラメータ、および、画面コントロールパラメータ受取り用フォーム.
 * <p>
 * 検索条件となるリクエストパラメータの取得や、ＤＢ検索オブジェクトの生成を行う。
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * guo.zhonglei		2015.04.09	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class PanaHousingSearchForm extends HousingSearchForm implements Validateable {

    // command リクエストパラメータは、URL マッピングで定義されている、namedCommands の制御にも使用される。
    // 物件一覧情報のメンテナンス機能では、以下の値が設定される場合がある。
    //
    // list : 物件一覧情報検索画面で実際に検索を行う場合に指定する。　（未指定の場合、検索画面を初期表示。）
    // back : 入力確認画面から入力画面へ復帰した場合。　（指定された場合、パラメータの値を初期値として入力画面に表示する。）

    /** 検索画面の command パラメータ */
    private String keySearchCommand;

    /** 登録開始日 （検索条件） */
    private String keyInsDateStart;
    /** 登録終了日 （検索条件） */
    private String keyInsDateEnd;
    /** 更新日時 （検索条件） */
    private String keyUpdDate;
    /** 会員番号 （検索条件） */
    private String keyUserId;
    /** 公開区分 （検索条件） */
    private String keyHiddenFlg;
    /** ステータス （検索条件） */
    private String keyStatusCd;

    /** 命令フラグ */
	private String command;

	/** 都道府県CD */
	private String prefCd;

	/** 物件種類CD */
	private String housingKindCd;

	/** 市区町村CD */
	private String addressCd;

	/** 駅CD */
	private String stationCd;

	/** 路線CD */
	private String routeCd;

	/** リフォーム価格込みで検索する */
	@UsePagingParam
	private String reformPriceCheck;

	/** 築年月 */
	@UsePagingParam
	private String keyCompDate;

	/** おすすめのポイント */
	private String[] iconCd;

	/** 価格(ソート条件) */
	@UsePagingParam
	private String sortPriceValue;

	/** 物件登録日（新着順）（ソート条件） */
	@UsePagingParam
	private String sortUpdDateValue;

	/** 築年が古い順（ソート条件） */
	@UsePagingParam
	private String sortBuildDateValue;

	/** 駅からの距離（ソート条件） */
	@UsePagingParam
	private String sortWalkTimeValue;

	/** 賃料/価格・下限（検索条件） */
	@UsePagingParam
	private String priceLower;
	/** 賃料/価格・上限（検索条件） */
	@UsePagingParam
	private String priceUpper;

	/** 土地面積・下限（検索条件） */
	@UsePagingParam
	private String landAreaLower;
	/** 土地面積・上限（検索条件） */
	@UsePagingParam
	private String landAreaUpper;
	/** 専有面積・下限（検索条件） */
	@UsePagingParam
	private String personalAreaLower;
	/** 専有面積・上限（検索条件） */
	@UsePagingParam
	private String personalAreaUpper;

	/** 建物面積・下限（検索条件） */
	@UsePagingParam
	private String buildingAreaLower;
	/** 建物面積・上限（検索条件） */
	@UsePagingParam
	private String buildingAreaUpper;
	/** おすすめのポイント */
	@UsePagingParam
	private String keyIconCd;

	/** 間取りCD（検索条件） */
	private String[] layoutCd;

	/** 引渡時期（検索条件） */
	@UsePagingParam
	private String moveinTiming;

	/** 瑕疵保険（検索条件） */
	private String insurExist;

	/** 所在階数（検索条件） */
	@UsePagingParam
	private String partSrchCdFloorArray;

	/** 駅徒歩（検索条件） */
	@UsePagingParam
	private String partSrchCdWalkArray;

	/** 物件画像情報、物件特長 （検索条件） */
	@UsePagingParam
	private String partSrchCdArray;

	/** 市区町村名 */
	private String hidAddressName;

	/** クリアフラグ */
	private String clearFlg;

	/** キーワード */
	private String keywords;

	/** 説明 */
	private String description;

	/** 共通コード変換処理 */
    private CodeLookupManager codeLookupManager;
    /** レングスバリデーションで使用する文字列長を取得するユーティリティ */
    protected LengthValidationUtils lengthUtils;

    /** ソート条件 */
    @UsePagingParam
	private String keyOrderType;

    /** 間取りCD（検索条件） */
    @UsePagingParam
	private String keyLayoutCd;

	/** 物件番号（検索条件） */
	private String keyHousingCd;
	
//	private String keyReformType;

	/**
	 * 駅を取得する。<br/>
	 * <br/>
	 *
	 * @return 駅
	 */
	public String getStationCd() {
		return stationCd;
	}

	/**
	 * 駅を設定する。<br/>
	 * <br/>
	 *
	 * @param stationCd 駅
	 */
	public void setStationCd(String stationCd) {
		this.stationCd = stationCd;
	}

	/**
	 * 物件番号（検索条件）を取得する。<br/>
	 * <br/>
	 *
	 * @return 物件番号（検索条件）
	 */
	public String getKeyHousingCd() {
		return keyHousingCd;
	}

	/**
	 * 物件番号（検索条件）を設定する。<br/>
	 * <br/>
	 *
	 * @param keyHousingCd 物件番号（検索条件）
	 */
	public void setKeyHousingCd(String keyHousingCd) {
		this.keyHousingCd = keyHousingCd;
	}

	/**
	 * 間取りCD（検索条件）を取得する。<br/>
	 * <br/>
	 *
	 * @return 間取りCD（検索条件）
	 */
	public String getKeyLayoutCd() {
		return keyLayoutCd;
	}

	/**
	 * 間取りCD（検索条件）を設定する。<br/>
	 * <br/>
	 *
	 * @param keyLayoutCd 間取りCD（検索条件）
	 */
	public void setKeyLayoutCd(String keyLayoutCd) {
		this.keyLayoutCd = keyLayoutCd;
	}

	/**
	 * ソート条件を取得する。<br/>
	 * <br/>
	 *
	 * @return ソート条件
	 */
	public String getKeyOrderType() {
		return keyOrderType;
	}

	/**
	 * ソート条件を設定する。<br/>
	 * <br/>
	 *
	 * @param keyOrder ソート条件
	 */
	public void setKeyOrderType(String keyOrderType) {
		this.keyOrderType = keyOrderType;
	}

    /**
     * デフォルトコンストラクター<br/>
     * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
     * <br/>
     */
    PanaHousingSearchForm(CodeLookupManager codeLookupManager, LengthValidationUtils lengthUtils) {
        super();
        this.codeLookupManager = codeLookupManager;
        this.lengthUtils = lengthUtils;
    }


	/**
     * 検索画面の command パラメータを取得する。<br/>
     * <br/>
     * @return 検索画面の command パラメータ
     */
    public String getKeySearchCommand() {
        return keySearchCommand;
    }

    /**
     * 検索画面の command パラメータを設定する。<br/>
     * <br/>
     * @param searchCommand 検索画面の command パラメータ
     */
    public void setKeySearchCommand(String keySearchCommand) {
        this.keySearchCommand = keySearchCommand;
    }

    /**
     * 登録開始日 （検索条件）を取得する。<br/>
     * <br/>
     * @return 登録開始日 （検索条件）
     */
    public String getKeyInsDateStart() {
        return keyInsDateStart;
    }

    /**
     * 登録開始日 （検索条件）を設定する。<br/>
     * <br/>
     * @param insDateStart 登録開始日 （検索条件）
     */
    public void setKeyInsDateStart(String keyInsDateStart) {
        this.keyInsDateStart = keyInsDateStart;
    }

    /**
     * 登録終了日 （検索条件）を取得する。<br/>
     * <br/>
     * @return 登録終了日 （検索条件）
     */
    public String getKeyInsDateEnd() {
        return keyInsDateEnd;
    }

    /**
     * 登録終了日 （検索条件）を設定する。<br/>
     * <br/>
     * @param insDateEnd 登録終了日 （検索条件）
     */
    public void setKeyInsDateEnd(String keyInsDateEnd) {
        this.keyInsDateEnd = keyInsDateEnd;
    }

    /**
     * 更新日時 （検索条件）を取得する。<br/>
     * <br/>
     * @return 更新日時 （検索条件）
     */
    public String getKeyUpdDate() {
        return keyUpdDate;
    }

    /**
     * 更新日時 （検索条件）を設定する。<br/>
     * <br/>
     * @param updDate 更新日時 （検索条件）
     */
    public void setKeyUpdDate(String keyUpdDate) {
        this.keyUpdDate = keyUpdDate;
    }

    /**
     * 会員番号 （検索条件）を取得する。<br/>
     * <br/>
     * @return 会員番号 （検索条件）
     */
    public String getKeyUserId() {
        return keyUserId;
    }

    /**
     * 会員番号 （検索条件）を設定する。<br/>
     * <br/>
     * @param userId 会員番号 （検索条件）
     */
    public void setKeyUserId(String keyUserId) {
        this.keyUserId = keyUserId;
    }

    /**
     * 公開区分 （検索条件）を取得する。<br/>
     * <br/>
     * @return 公開区分 （検索条件）
     */
    public String getKeyHiddenFlg() {
        return keyHiddenFlg;
    }

    /**
     * 公開区分 （検索条件）を設定する。<br/>
     * <br/>
     * @param hiddenFlg 公開区分 （検索条件）
     */
    public void setKeyHiddenFlg(String keyHiddenFlg) {
        this.keyHiddenFlg = keyHiddenFlg;
    }

    /**
     * ステータス （検索条件）を取得する。<br/>
     * <br/>
     * @return ステータス （検索条件）
     */
    public String getKeyStatusCd() {
        return keyStatusCd;
    }

    /**
     * ステータス （検索条件）を設定する。<br/>
     * <br/>
     * @param statusCd ステータス （検索条件）
     */
    public void setKeyStatusCd(String keyStatusCd) {
        this.keyStatusCd = keyStatusCd;
    }
    /**
     * 命令フラグを取得する。<br/>
     * <br/>
     * @return 命令フラグ
     */
	public String getCommand() {
		return command;
	}

	 /**
     * 命令フラグを設定する。<br/>
     * <br/>
     * @param command 命令フラグ
     */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
     * 都道府県CDを取得する。<br/>
     * <br/>
     * @return prefCd 都道府県CD
     */
	public String getPrefCd() {
		return prefCd;
	}

	/**
     * 都道府県CDを設定する。<br/>
     * <br/>
     * @param prefCd 都道府県CD
     */
	public void setPrefCd(String prefCd) {
		this.prefCd = prefCd;
	}

	/**
     * 物件種類CDを取得する。<br/>
     * <br/>
     * @return housingKindCd 物件種類CD
     */
	public String getHousingKindCd() {
		return housingKindCd;
	}

	/**
     * 物件種類CDを設定する。<br/>
     * <br/>
     * @param housingKindCd 物件種類CD
     */
	public void setHousingKindCd(String housingKindCd) {
		this.housingKindCd = housingKindCd;
	}

	/**
     * 市区町村CDを取得する。<br/>
     * <br/>
     * @return addressCd 市区町村CD
     */
	public String getAddressCd() {
		return addressCd;
	}

	/**
     * 市区町村CDを設定する。<br/>
     * <br/>
     * @param addressCd 市区町村CD
     */
	public void setAddressCd(String addressCd) {
		this.addressCd = addressCd;
	}

	/**
     * 路線CDを取得する。<br/>
     * <br/>
     * @return routeCd 路線CD
     */
	public String getRouteCd() {
		return routeCd;
	}

	/**
     * 路線CDを設定する。<br/>
     * <br/>
     * @param routeCd 路線CD
     */
	public void setRouteCd(String routeCd) {
		this.routeCd = routeCd;
	}

	/**
     * リフォーム価格込みで検索するを取得する。<br/>
     * <br/>
     * @return reformPriceCheck リフォーム価格込みで検索する
     */
	public String getReformPriceCheck() {
		return reformPriceCheck;
	}

	/**
     * リフォーム価格込みで検索するを設定する。<br/>
     * <br/>
     * @param reformPriceCheck リフォーム価格込みで検索する
     */
	public void setReformPriceCheck(String reformPriceCheck) {
		this.reformPriceCheck = reformPriceCheck;
	}

	/**
     * 築年月を取得する。<br/>
     * <br/>
     * @return keyCompDate 築年月
     */
	public String getKeyCompDate() {
		return keyCompDate;
	}

	/**
     * 築年月を設定する。<br/>
     * <br/>
     * @param keyCompDate 築年月
     */
	public void setKeyCompDate(String keyCompDate) {
		this.keyCompDate = keyCompDate;
	}

	/**
     * おすすめのポイントを取得する。<br/>
     * <br/>
     * @return keyIconCd おすすめのポイント
     */
	public String getKeyIconCd() {
		return keyIconCd;
	}

	/**
     * おすすめのポイントを設定する。<br/>
     * <br/>
     * @param keyIconCd おすすめのポイント
     */
	public void setKeyIconCd(String keyIconCd) {
		this.keyIconCd = keyIconCd;
	}

	/**
     * おすすめのポイントを取得する。<br/>
     * <br/>
     * @return keyIconCd おすすめのポイント
     */
	public String[] getIconCd() {
		return iconCd;
	}

	/**
     * おすすめのポイントを設定する。<br/>
     * <br/>
     * @param keyIconCd おすすめのポイント
     */
	public void setIconCd(String[] iconCd) {
		this.iconCd = iconCd;
	}

	/**
     * 価格(ソート条件)を取得する。<br/>
     * <br/>
     * @return sortPriceValue 価格(ソート条件)
     */
	public String getSortPriceValue() {
		return sortPriceValue;
	}

	/**
     * 価格(ソート条件)を設定する。<br/>
     * <br/>
     * @param sortPriceValue 価格(ソート条件)
     */
	public void setSortPriceValue(String sortPriceValue) {
		this.sortPriceValue = sortPriceValue;
	}

	/**
     * 築年が古い順（ソート条件）を取得する。<br/>
     * <br/>
     * @return sortUpdDateValue 築年が古い順（ソート条件）
     */
	public String getSortUpdDateValue() {
		return sortUpdDateValue;
	}

	/**
     * 築年が古い順（ソート条件）を設定する。<br/>
     * <br/>
     * @param sortUpdDateValue 築年が古い順（ソート条件）
     */
	public void setSortUpdDateValue(String sortUpdDateValue) {
		this.sortUpdDateValue = sortUpdDateValue;
	}

	/**
     * 築年が古い順（ソート条件）を取得する。<br/>
     * <br/>
     * @return sortBuildDateValue 築年が古い順（ソート条件）
     */
	public String getSortBuildDateValue() {
		return sortBuildDateValue;
	}

	/**
     * 築年が古い順（ソート条件）を設定する。<br/>
     * <br/>
     * @param sortBuildDateValue 築年が古い順（ソート条件）
     */
	public void setSortBuildDateValue(String sortBuildDateValue) {
		this.sortBuildDateValue = sortBuildDateValue;
	}

	/**
     * 駅からの距離（ソート条件）を取得する。<br/>
     * <br/>
     * @return sortWalkTimeValue 駅からの距離（ソート条件）
     */
	public String getSortWalkTimeValue() {
		return sortWalkTimeValue;
	}

	/**
     * 駅からの距離（ソート条件）を設定する。<br/>
     * <br/>
     * @param sortWalkTimeValue 駅からの距離（ソート条件）
     */
	public void setSortWalkTimeValue(String sortWalkTimeValue) {
		this.sortWalkTimeValue = sortWalkTimeValue;
	}

	/**
     * 賃料/価格・下限（検索条件）を取得する。<br/>
     * <br/>
     * @return priceLower 賃料/価格・下限（検索条件）
     */
	public String getPriceLower() {
		return priceLower;
	}

	/**
     * 賃料/価格・下限（検索条件）を設定する。<br/>
     * <br/>
     * @param priceLower 賃料/価格・下限（検索条件）
     */
	public void setPriceLower(String priceLower) {
		this.priceLower = priceLower;
	}

	/**
     * 賃料/価格・上限（検索条件）を取得する。<br/>
     * <br/>
     * @return priceUpper 賃料/価格・上限（検索条件）
     */
	public String getPriceUpper() {
		return priceUpper;
	}

	/**
     * 賃料/価格・上限（検索条件）を設定する。<br/>
     * <br/>
     * @param priceUpper 賃料/価格・上限（検索条件）
     */
	public void setPriceUpper(String priceUpper) {
		this.priceUpper = priceUpper;
	}

	/**
     * 土地面積・下限（検索条件）を取得する。<br/>
     * <br/>
     * @return landAreaLower 土地面積・下限（検索条件）
     */
	public String getLandAreaLower() {
		return landAreaLower;
	}

	/**
     * 土地面積・下限（検索条件）を設定する。<br/>
     * <br/>
     * @param landAreaLower 土地面積・下限（検索条件）
     */
	public void setLandAreaLower(String landAreaLower) {
		this.landAreaLower = landAreaLower;
	}

	/**
     * 土地面積・上限（検索条件）を取得する。<br/>
     * <br/>
     * @return landAreaUpper 土地面積・上限（検索条件）
     */
	public String getLandAreaUpper() {
		return landAreaUpper;
	}

	/**
     * 土地面積・上限（検索条件）を設定する。<br/>
     * <br/>
     * @param landAreaUpper 土地面積・上限（検索条件）
     */
	public void setLandAreaUpper(String landAreaUpper) {
		this.landAreaUpper = landAreaUpper;
	}

	/**
     * 専有面積・下限（検索条件）を取得する。<br/>
     * <br/>
     * @return personalAreaLower 専有面積・下限（検索条件）
     */
	public String getPersonalAreaLower() {
		return personalAreaLower;
	}

	/**
     * 専有面積・下限（検索条件）を設定する。<br/>
     * <br/>
     * @param personalAreaLower 専有面積・下限（検索条件）
     */
	public void setPersonalAreaLower(String personalAreaLower) {
		this.personalAreaLower = personalAreaLower;
	}

	/**
     * 専有面積・上限（検索条件）を取得する。<br/>
     * <br/>
     * @return personalAreaUpper 専有面積・上限（検索条件）
     */
	public String getPersonalAreaUpper() {
		return personalAreaUpper;
	}

	/**
     * 専有面積・上限（検索条件）を設定する。<br/>
     * <br/>
     * @param personalAreaUpper 専有面積・上限（検索条件）
     */
	public void setPersonalAreaUpper(String personalAreaUpper) {
		this.personalAreaUpper = personalAreaUpper;
	}

	/**
     * 建物面積・下限（検索条件）を取得する。<br/>
     * <br/>
     * @return buildingAreaLower 建物面積・下限（検索条件）
     */
	public String getBuildingAreaLower() {
		return buildingAreaLower;
	}

	/**
     * 建物面積・下限（検索条件）を設定する。<br/>
     * <br/>
     * @param buildingAreaLower 建物面積・下限（検索条件）
     */
	public void setBuildingAreaLower(String buildingAreaLower) {
		this.buildingAreaLower = buildingAreaLower;
	}

	/**
     * 建物面積・上限（検索条件）を取得する。<br/>
     * <br/>
     * @return buildingAreaUpper 建物面積・上限（検索条件）
     */
	public String getBuildingAreaUpper() {
		return buildingAreaUpper;
	}

	/**
     * 建物面積・上限（検索条件）を設定する。<br/>
     * <br/>
     * @param buildingAreaUpper 建物面積・上限（検索条件）
     */
	public void setBuildingAreaUpper(String buildingAreaUpper) {
		this.buildingAreaUpper = buildingAreaUpper;
	}

	/**
     * 間取りCD（検索条件）を取得する。<br/>
     * <br/>
     * @return layoutCd 間取りCD（検索条件）
     */
	public String[] getLayoutCd() {
		return layoutCd;
	}

	/**
     * 間取りCD（検索条件）を設定する。<br/>
     * <br/>
     * @param layoutCd 間取りCD（検索条件）
     */
	public void setLayoutCd(String[] layoutCd) {
		this.layoutCd = layoutCd;
	}

	/**
     * 引渡時期（検索条件）を取得する。<br/>
     * <br/>
     * @return moveinTiming 引渡時期（検索条件）
     */
	public String getMoveinTiming() {
		return moveinTiming;
	}

	/**
     * 引渡時期（検索条件）を設定する。<br/>
     * <br/>
     * @param moveinTiming 引渡時期（検索条件）
     */
	public void setMoveinTiming(String moveinTiming) {
		this.moveinTiming = moveinTiming;
	}

	/**
     * 瑕疵保険（検索条件）を取得する。<br/>
     * <br/>
     * @return insurExist 瑕疵保険（検索条件）
     */
	public String getInsurExist() {
		return insurExist;
	}

	/**
     * 瑕疵保険（検索条件）を設定する。<br/>
     * <br/>
     * @param insurExist 瑕疵保険（検索条件）
     */
	public void setInsurExist(String insurExist) {
		this.insurExist = insurExist;
	}

	/**
     * 所在階数（検索条件）を取得する。<br/>
     * <br/>
     * @return partSrchCdFloor 所在階数（検索条件）
     */
	public String getPartSrchCdFloorArray() {
		return partSrchCdFloorArray;
	}

	/**
     * 所在階数（検索条件）を設定する。<br/>
     * <br/>
     * @param partSrchCdFloor 所在階数（検索条件）
     */
	public void setPartSrchCdFloorArray(String partSrchCdFloorArray) {
		this.partSrchCdFloorArray = partSrchCdFloorArray;
	}

	/**
     * 駅徒歩（検索条件）を取得する。<br/>
     * <br/>
     * @return partSrchCdWalk 駅徒歩（検索条件）
     */
	public String getPartSrchCdWalkArray() {
		return partSrchCdWalkArray;
	}

	/**
     * 駅徒歩（検索条件）を設定する。<br/>
     * <br/>
     * @param partSrchCdWalk 駅徒歩（検索条件）
     */
	public void setPartSrchCdWalkArray(String partSrchCdWalkArray) {
		this.partSrchCdWalkArray = partSrchCdWalkArray;
	}

	/**
     * 物件画像情報、物件特長 （検索条件）を取得する。<br/>
     * <br/>
     * @return partSrchCd 物件画像情報、物件特長 （検索条件）
     */
	public String getPartSrchCdArray() {
		return partSrchCdArray;
	}

	/**
     * 物件画像情報、物件特長 （検索条件）を設定する。<br/>
     * <br/>
     * @param partSrchCd 物件画像情報、物件特長 （検索条件）
     */
	public void setPartSrchCdArray(String partSrchCdArray) {
		this.partSrchCdArray = partSrchCdArray;
	}

	/**
     * 市区町村名を取得する。<br/>
     * <br/>
     * @return hidAddressName 市区町村名
     */
	public String getHidAddressName() {
		return hidAddressName;
	}

	/**
     * 市区町村名を設定する。<br/>
     * <br/>
     * @param hidAddressName 市区町村名
     */
	public void setHidAddressName(String hidAddressName) {
		this.hidAddressName = hidAddressName;
	}

	/**
     * クリアフラグを取得する。<br/>
     * <br/>
     * @return clearFlg クリアフラグ
     */
	public String getClearFlg() {
		return clearFlg;
	}

	/**
     * クリアフラグを設定する。<br/>
     * <br/>
     * @param clearFlg クリアフラグ
     */
	public void setClearFlg(String clearFlg) {
		this.clearFlg = clearFlg;
	}

	/**
	 * キーワード を取得する。<br/>
	 * <br/>
	 * @return キーワード
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * キーワード を設定する。<br/>
	 * <br/>
	 * @param keywords
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * 説明 を取得する。<br/>
	 * <br/>
	 * @return 説明
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 説明 を設定する。<br/>
	 * <br/>
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
//	/**
//     * @return the reformType
//     */
//    public String getKeyReformType() {
//        return keyReformType;
//    }
//
//    /**
//     * @param reformType the reformType to set
//     */
//    public void setKeyReformType(String reformType) {
//        this.keyReformType = reformType;
//    }

    /**
     * 共通コード変換処理を取得する。<br/>
     * <br/>
     * @return codeLookupManager 共通コード変換処理
     */
	public CodeLookupManager getCodeLookupManager() {
		return codeLookupManager;
	}

	/**
     * 共通コード変換処理を設定する。<br/>
     * <br/>
     * @param codeLookupManager 共通コード変換処理
     */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}
	/**
     * バリデーション処理<br/>
     * リクエストパラメータのバリデーションを行う<br/>
     * <br/>
     * @param errors エラー情報を格納するリストオブジェクト
     * @return 正常時 true、エラー時 false
     */
    @Override
    public boolean validate(List<ValidationFailure> errors) {

        int startSize = errors.size();

        // 物件番号入力チェック
        validKeyHousingCd(errors);

        // 物件名入力チェック
        validKeyDisplayHousingName(errors);

        // 登録開始日入力チェック
        validInsDateStart(errors);

        // 登録終了日入力チェック
        validInsDateEnd(errors);

        // 登録開始日 < 登録終了日のチェック
        validInsDateCom(errors);

        // 更新日時入力チェック
        validUpdDate(errors);

        // 会員番号入力チェック
        validUserId(errors);

        // 公開区分入力チェック
        validHiddenFlg(errors);

        // ステータス入力チェック
        validStatusCd(errors);

        return (startSize == errors.size());
    }

    /**
     * 物件番号 バリデーション<br/>
     * ・桁数チェック
     * ・文字チェック
     * <br/>
     * @param errors エラー情報を格納するリストオブジェクト
     */
    protected void validKeyHousingCd(List<ValidationFailure> errors) {
        // 物件番号入力チェック
        ValidationChain valid = new ValidationChain("housingList.input.keyHousingCd", this.getKeyHousingCd());
        // 桁数チェック
        valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("housing.input.housingCd", 10)));
        // 文字チェック
        valid.addValidation(new AlphanumericOnlyValidation());
        valid.validate(errors);
    }

    /**
     * 物件名 バリデーション<br/>
     * ・桁数チェック
     * <br/>
     * @param errors エラー情報を格納するリストオブジェクト
     */
    protected void validKeyDisplayHousingName(List<ValidationFailure> errors) {
        // 物件名入力チェック
        ValidationChain valid = new ValidationChain("housingList.input.keyDisplayHousingName",
                this.getKeyDisplayHousingName());
        // 桁数チェック
        valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("housing.input.displayHousingName", 25)));
        valid.validate(errors);
    }

    /**
     * 登録開始日 バリデーション<br/>
     * ・日付書式チェック
     * <br/>
     * @param errors エラー情報を格納するリストオブジェクト
     */
    protected void validInsDateStart(List<ValidationFailure> errors) {
        // 登録開始日入力チェック
        ValidationChain valid = new ValidationChain("housingList.input.insDateStart", this.keyInsDateStart);
        // 日付書式チェック
        valid.addValidation(new ValidDateValidation("yyyy/MM/dd"));
        valid.validate(errors);
    }

    /**
     * 登録終了日 バリデーション<br/>
     * ・日付書式チェック
     * <br/>
     * @param errors エラー情報を格納するリストオブジェクト
     */
    protected void validInsDateEnd(List<ValidationFailure> errors) {
        // 登録終了日入力チェック
        ValidationChain valid = new ValidationChain("housingList.input.insDateEnd", this.keyInsDateEnd);
        // 日付書式チェック
        valid.addValidation(new ValidDateValidation("yyyy/MM/dd"));
        valid.validate(errors);
    }

    /**
     * 登録開始日 < 登録終了日 バリデーション<br/>
     * ・日付比較チェック
     * <br/>
     * @param errors エラー情報を格納するリストオブジェクト
     */
    protected void validInsDateCom(List<ValidationFailure> errors) {
        // 日付比較チェック
        ValidationChain valid = new ValidationChain("housingList.input.insDateStart", this.keyInsDateStart);
        // 日付書式チェック
        valid.addValidation(new DateFromToValidation("yyyy/MM/dd", "登録終了日", this.keyInsDateEnd));
        valid.validate(errors);
    }

    /**
     * 更新日時 バリデーション<br/>
     * ・パターンチェック
     * <br/>
     * @param errors エラー情報を格納するリストオブジェクト
     */
    protected void validUpdDate(List<ValidationFailure> errors) {
        // 更新日時入力チェック
        ValidationChain valid = new ValidationChain("housingList.input.updDate", this.keyUpdDate);
        // パターンチェック
        valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "housingListUpdDate"));
        valid.validate(errors);
    }

    /**
     * 会員番号 バリデーション<br/>
     * ・英数字チェック
     * <br/>
     * @param errors エラー情報を格納するリストオブジェクト
     */
    protected void validUserId(List<ValidationFailure> errors) {
        // 更新日時入力チェック
        ValidationChain valid = new ValidationChain("housingList.input.userId", this.keyUserId);
        // 英数字チェック
        valid.addValidation(new AlphanumericOnlyValidation());
        valid.validate(errors);
    }

    /**
     * 公開区分 バリデーション<br/>
     * ・パターンチェック
     * <br/>
     * @param errors エラー情報を格納するリストオブジェクト
     */
    protected void validHiddenFlg(List<ValidationFailure> errors) {
        // 更新日時入力チェック
        ValidationChain valid = new ValidationChain("housingList.input.hiddenFlg", this.keyHiddenFlg);
        // パターンチェック
        valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "hiddenFlg"));
        valid.validate(errors);
    }

    /**
     * ステータス バリデーション<br/>
     * ・パターンチェック
     * <br/>
     * @param errors エラー情報を格納するリストオブジェクト
     */
    protected void validStatusCd(List<ValidationFailure> errors) {
        // 更新日時入力チェック
        ValidationChain valid = new ValidationChain("housingList.input.statusCd", this.keyStatusCd);
        // パターンチェック
        valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "statusCd"));
        valid.validate(errors);
    }

    /**
     * 渡されたバリーオブジェクトから Form へ初期値を設定する。<br/>
     * <br/>
     * @param prefMstList List<PrefMst>　を実装した、都道府県マスタ管理用バリーオブジェクト
     * @param full false の場合、都道府県マスタレストをのみ設定する。　true の場合はすべて設定します。
     *
     */
    public void setDefaultData(List<PrefMst> prefMstList, Map<String, Object> model, boolean full) {

        if (full) {
            // 更新日時を設定
            this.keyUpdDate = "01";
            // 公開区分を設定
            this.keyHiddenFlg = "1";
        } else {
        	// ソート条件
        	this.setKeyOrderType("0");
		}
        model.put("prefMst", prefMstList);
    }
}