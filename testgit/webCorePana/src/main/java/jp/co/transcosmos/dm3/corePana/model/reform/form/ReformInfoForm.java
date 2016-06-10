package jp.co.transcosmos.dm3.corePana.model.reform.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.vo.Information;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.validation.ReformImgPathJpgValidation;
import jp.co.transcosmos.dm3.corePana.validation.ReformImgSizeValidation;
import jp.co.transcosmos.dm3.corePana.validation.UrlValidation;
import jp.co.transcosmos.dm3.corePana.vo.ReformChart;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.NumericValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * リフォーム情報メンテナンスの入力パラメータ受取り用フォーム.
 * <p>
 * <pre>
 * 担当者      修正日     修正内容
 * ------------ ----------- -----------------------------------------------------
 * TRANS     2015.03.10  新規作成
 * Thi Tran     2015.12.18    Update to add categories to reform plan
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class ReformInfoForm implements Validateable {
	/** 画像ファイル有無フラグ（無） */
	public static String IMGFLG_0 = "0";

	/** 画像ファイル有無フラグ（有） */
	public static String IMGFLG_1 = "1";

	/** リフォーム(詳細/画像)有無フラグ（無） */
	public static String DIVFLG_0 = "0";

	/** リフォーム(詳細/画像)有無フラグ（有） */
	public static String DIVFLG_1 = "1";

    /** 共通コード変換処理 */
    private CodeLookupManager codeLookupManager;

    /** システム物件CD */
    private String sysHousingCd;

    /** 表示用物件名 */
    private String displayHousingName;

    /** システムリフォームCD */
    private String sysReformCd;

    /** 非公開フラグ */
    private String hiddenFlg;

    /** リフォームプラン名 */
    private String planName;

    /** リフォーム価格 */
    private String planPrice;

    /** 工期 */
    private String constructionPeriod;

    /** セールスポイント */
    private String salesPoint;

    /** セールスポイント（表示用） */
    private String salesPoint1;

    /** 備考 */
    private String note;

    /** 備考（表示用） */
    private String note1;

    /** 物件番号 */
    private String housingCd;

    /** 物件種類 */
    private String housingKindCd;

    /** チャート設定キー */
    private String chartKey[];

    /** チャート設定値 */
    private String chartValue[];

    /** before 動画URL */
    private String beforeMovieUrl;

    /** after 動画URL */
    private String afterMovieUrl;

    /** 動画閲覧権限 */
    private String roleId;

    /** 画像名 */
    private String imgName;

    /** 画像TempPath */
    private String temPath;

    /** 画像ファイル１ */
    private String imgFile1;

    /** 画像ファイル２ */
    private String imgFile2;

    /** reform画像パス名 */
    private FileItem reformImgFile;

    /** wk画像ファイル有無フラグ */
    private String wkImgFlg;

    /** 画像ファイル選択フラグ */
    private String imgSelFlg;

    /** リフォーム詳細情報フラグ */
    private String dtlFlg;

    /** リフォーム画像情報フラグ */
    private String imgFlg;

    /** 削除のフラグ */
    private String reformImgDel;

    /** 最終更新日 */
    private String updDate;

    /** イベントフラグ */
    private String command;

    /** Category 1 of reform plan */
    private String planCategory1;
    /** Category 2 of reform plan */
    private String planCategory2;

    /** All reform categories which is configurated in code lookup **/
    private LinkedHashMap<String, ReformPlanCategory> reformPlanCategoryMap;

    // log
    private static final Log log = LogFactory.getLog(ReformInfoForm.class);

    /**
     * デフルトコンストラクター。<br/>
     * <br/>
     *
     * @param codeLookupManager 共通コード変換処理
     */
    public ReformInfoForm() {
        super();
    }

    /**
     * コンストラクター。<br/>
     * <br/>
     *
     * @param codeLookupManager 共通コード変換処理
     */
    public ReformInfoForm(CodeLookupManager codeLookupManager) {
        super();
        this.codeLookupManager = codeLookupManager;
    }

    /**
     * 最終更新日 パラメータを取得する。<br/>
     * 最終更新日 パラメータの値は、フレームワークの URL マッピングで使用する 最終更新日 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 最終更新日 パラメータ
     */
    public String getUpdDate() {
        return updDate;
    }

    /**
     * 最終更新日 パラメータを設定する。<br/>
     * 最終更新日 パラメータの値は、フレームワークの URL マッピングで使用する 最終更新日 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param updDate
     *            最終更新日 パラメータ
     */
    public void setUpdDate(String updDate) {
        this.updDate = updDate;
    }

    /**
     * before 動画URL パラメータを取得する。<br/>
     * before 動画URL パラメータの値は、フレームワークの URL マッピングで使用する before 動画URL クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return before 動画URL パラメータ
     */
    public String getBeforeMovieUrl() {
        return beforeMovieUrl;
    }

    /**
     * before 動画URLを設定する。<br/>
     * before 動画URLの値は、フレームワークの URL マッピングで使用する before 動画URLの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param beforeMovieUrl
     *            before 動画URL
     */
    public void setBeforeMovieUrl(String beforeMovieUrl) {
        this.beforeMovieUrl = beforeMovieUrl;
    }

    /**
     * after 動画URL パラメータを取得する。<br/>
     * after 動画URL パラメータの値は、フレームワークの URL マッピングで使用する after 動画URL クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return after 動画URL パラメータ
     */
    public String getAfterMovieUrl() {
        return afterMovieUrl;
    }

    /**
     * after 動画URLを設定する。<br/>
     * after 動画URLの値は、フレームワークの URL マッピングで使用する after 動画URLの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param afterMovieUrl
     *            after 動画URL
     */
    public void setAfterMovieUrl(String afterMovieUrl) {
        this.afterMovieUrl = afterMovieUrl;
    }

    /**
     * ロールID パラメータを取得する。<br/>
     * ロールID パラメータの値は、フレームワークの URL マッピングで使用する ロールID クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return ロールID パラメータ
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * ロールIDを設定する。<br/>
     * ロールIDの値は、フレームワークの URL マッピングで使用する ロールIDの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param roleId
     *            ロールID
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * システム物件CD パラメータを取得する。<br/>
     * システム物件CD パラメータの値は、フレームワークの URL マッピングで使用する システム物件CD クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return システム物件CD パラメータ
     */
    public String getSysHousingCd() {
        return sysHousingCd;
    }

    /**
     * システム物件CDを設定する。<br/>
     * システム物件CDの値は、フレームワークの URL マッピングで使用する システム物件CDの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param sysHousingCd
     *            システム物件CD
     */
    public void setSysHousingCd(String sysHousingCd) {
        this.sysHousingCd = sysHousingCd;
    }

    /**
     * 表示用物件名 パラメータを取得する。<br/>
     * 表示用物件名 パラメータの値は、フレームワークの URL マッピングで使用する 表示用物件名 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 表示用物件名 パラメータ
     */
    public String getDisplayHousingName() {
        return displayHousingName;
    }

    /**
     * 表示用物件名を設定する。<br/>
     * 表示用物件名の値は、フレームワークの URL マッピングで使用する 表示用物件名の切り替えにも使用される。<br/>
     * <br/>
     *
     * @param displayHousingName
     *            表示用物件名
     */
    public void setDisplayHousingName(String displayHousingName) {
        this.displayHousingName = displayHousingName;
    }

    /**
     * 非公開フラグ パラメータを取得する。<br/>
     * 非公開フラグ パラメータの値は、フレームワークの URL マッピングで使用する 非公開フラグ クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 非公開フラグ パラメータ
     */
    public String getHiddenFlg() {
        return hiddenFlg;
    }

    /**
     * 非公開フラグを設定する。<br/>
     * 非公開フラグの値は、フレームワークの URL マッピングで使用する 非公開フラグの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param hiddenFlg
     *            非公開フラグ
     */
    public void setHiddenFlg(String hiddenFlg) {
        this.hiddenFlg = hiddenFlg;
    }

    /**
     * リフォームプラン名 パラメータを取得する。<br/>
     * リフォームプラン名 パラメータの値は、フレームワークの URL マッピングで使用する リフォームプラン名 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return リフォームプラン名 パラメータ
     */
    public String getPlanName() {
        return planName;
    }

    /**
     * リフォームプラン名を設定する。<br/>
     * リフォームプラン名の値は、フレームワークの URL マッピングで使用する リフォームプラン名の切り替えにも使用される。<br/>
     * <br/>
     *
     * @param planName
     *            リフォームプラン名
     */
    public void setPlanName(String planName) {
        this.planName = planName;
    }

    /**
     * リフォーム価格 パラメータを取得する。<br/>
     * リフォーム価格 パラメータの値は、フレームワークの URL マッピングで使用する リフォーム価格 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return リフォーム価格 パラメータ
     */
    public String getPlanPrice() {
        return planPrice;
    }

    /**
     * リフォーム価格を設定する。<br/>
     * リフォーム価格の値は、フレームワークの URL マッピングで使用する リフォーム価格の切り替えにも使用される。<br/>
     * <br/>
     *
     * @param planPrice
     *            リフォーム価格
     */
    public void setPlanPrice(String planPrice) {
        this.planPrice = planPrice;
    }

    /**
     * 工期 パラメータを取得する。<br/>
     * 工期 パラメータの値は、フレームワークの URL マッピングで使用する 工期 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 工期 パラメータ
     */
    public String getConstructionPeriod() {
        return constructionPeriod;
    }

    /**
     * 工期を設定する。<br/>
     * 工期の値は、フレームワークの URL マッピングで使用する 工期の切り替えにも使用される。<br/>
     * <br/>
     *
     * @param constructionPeriod
     *            工期
     */
    public void setConstructionPeriod(String constructionPeriod) {
        this.constructionPeriod = constructionPeriod;
    }

    /**
     * セールスポイント パラメータを取得する。<br/>
     * セールスポイントパラメータの値は、フレームワークの URL マッピングで使用する セールスポイント クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return セールスポイント パラメータ
     */
    public String getSalesPoint() {
        return salesPoint;
    }

    /**
     * セールスポイントを設定する。<br/>
     * セールスポイントの値は、フレームワークの URL マッピングで使用する セールスポイントの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param salesPoint
     *            セールスポイント
     */
    public void setSalesPoint(String salesPoint) {
        this.salesPoint = salesPoint;
    }

    /**
     * セールスポイント（表示用） パラメータを取得する。<br/>
     * セールスポイント（表示用）パラメータの値は、フレームワークの URL マッピングで使用する セールスポイント クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return セールスポイント パラメータ
     */
    public String getSalesPoint1() {
        return salesPoint1;
    }

    /**
     * セールスポイント（表示用）を設定する。<br/>
     * セールスポイント（表示用）の値は、フレームワークの URL マッピングで使用する セールスポイントの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param salesPoint
     *            セールスポイント
     */
    public void setSalesPoint1(String salesPoint1) {
        this.salesPoint1 = salesPoint1;
    }
    /**
     * 備考 パラメータを取得する。<br/>
     * 備考パラメータの値は、フレームワークの URL マッピングで使用する 備考 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 備考 パラメータ
     */
    public String getNote() {
        return note;
    }

    /**
     * 備考を設定する。<br/>
     * 備考の値は、フレームワークの URL マッピングで使用する 備考の切り替えにも使用される。<br/>
     * <br/>
     *
     * @param note
     *            備考
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * 備考（表示用） パラメータを取得する。<br/>
     * 備考（表示用）パラメータの値は、フレームワークの URL マッピングで使用する 備考 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 備考 パラメータ
     */
    public String getNote1() {
        return note1;
    }

    /**
     * 備考（表示用）を設定する。<br/>
     * 備考（表示用）の値は、フレームワークの URL マッピングで使用する 備考の切り替えにも使用される。<br/>
     * <br/>
     *
     * @param note
     *            備考
     */
    public void setNote1(String note1) {
        this.note1 = note1;
    }
    /**
     * システムリフォームCD パラメータを取得する。<br/>
     * システムリフォームCDパラメータの値は、フレームワークの URL マッピングで使用する システムリフォームCD クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return システムリフォームCD パラメータ
     */
    public String getSysReformCd() {
        return sysReformCd;
    }

    /**
     * システムリフォームCDを設定する。<br/>
     * システムリフォームCDの値は、フレームワークの URL マッピングで使用する システムリフォームCDの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param sysReformCd
     *            システムリフォームCD
     */
    public void setSysReformCd(String sysReformCd) {
        this.sysReformCd = sysReformCd;
    }

    /**
     * dtlFlg パラメータを取得する。<br/>
     * dtlFlgパラメータの値は、フレームワークの URL マッピングで使用する dtlFlg クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return dtlFlg パラメータ
     */
    public String getDtlFlg() {
        return dtlFlg;
    }

    /**
     * dtlFlgを設定する。<br/>
     * dtlFlgの値は、フレームワークの URL マッピングで使用する dtlFlgの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param dtlFlg
     *            dtlFlg
     */
    public void setDtlFlg(String dtlFlg) {
        this.dtlFlg = dtlFlg;
    }

    /**
     * imgFlg パラメータを取得する。<br/>
     * imgFlgパラメータの値は、フレームワークの URL マッピングで使用する imgFlg クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return imgFlg パラメータ
     */
    public String getImgFlg() {
        return imgFlg;
    }

    /**
     * imgFlgを設定する。<br/>
     * imgFlgの値は、フレームワークの URL マッピングで使用する imgFlgの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param imgFlg
     *            imgFlg
     */
    public void setImgFlg(String imgFlg) {
        this.imgFlg = imgFlg;
    }

    /**
     * イベント区分 パラメータを取得する。<br/>
     * イベント区分パラメータの値は、フレームワークの URL マッピングで使用する イベント区分 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return イベント区分 パラメータ
     */
    public String getCommand() {
        return command;
    }

    /**
     * イベント区分を設定する。<br/>
     * イベント区分の値は、フレームワークの URL マッピングで使用する イベント区分の切り替えにも使用される。<br/>
     * <br/>
     *
     * @param command
     *            イベント区分
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * 物件番号 パラメータを取得する。<br/>
     * 物件番号パラメータの値は、フレームワークの URL
     * マッピングで使用する 物件番号 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return housingCd 物件番号
     */
    public String getHousingCd() {
        return housingCd;
    }

    /**
     * 物件番号を設定する。<br/>
     * 物件番号の値は、フレームワークの URL マッピングで使用する 物件番号の切り替えにも使用される。<br/>
     * <br/>
     *
     * @param housingCd
     *            物件番号
     */
    public void setHousingCd(String housingCd) {
        this.housingCd = housingCd;
    }

    /**
     * 物件種類CD パラメータを取得する。<br/>
     * 物件種類CDパラメータの値は、フレームワークの URL
     * マッピングで使用する 物件種類CD クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return housingKindCd 物件種類CD
     */
    public String getHousingKindCd() {
        return housingKindCd;
    }

    /**
     * 物件種類CDを設定する。<br/>
     * 物件種類CDの値は、フレームワークの URL マッピングで使用する 物件種類CDの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param housingKindCd
     *            物件種類CD
     */
    public void setHousingKindCd(String housingKindCd) {
        this.housingKindCd = housingKindCd;
    }

    /**
     * chartKey パラメータを取得する。<br/>
     * chartKeyパラメータの値は、フレームワークの URL
     * マッピングで使用する chartKey クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return chartKey パラメータ
     */
    public String[] getChartKey() {
        return chartKey;
    }

    /**
     * chartKeyを設定する。<br/>
     * chartKeyの値は、フレームワークの URL マッピングで使用する chartKeyの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param chartKey
     *            chartKey
     */
    public void setChartKey(String[] chartKey) {
        this.chartKey = chartKey;
    }

    /**
     * chartValue パラメータを取得する。<br/>
     * chartValueパラメータの値は、フレームワークの URL
     * マッピングで使用する chartValue クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return chartValue パラメータ
     */
    public String[] getChartValue() {
        return chartValue;
    }

    /**
     * chartValueを設定する。<br/>
     * chartValueの値は、フレームワークの URL マッピングで使用する chartValueの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param chartValue
     *            chartValue
     */
    public void setChartValue(String[] chartValue) {
        this.chartValue = chartValue;
    }

    /**
     * wk画像ファイル有無フラグ を取得する。<br/>
     * <br/>
     * @return wk画像ファイル有無フラグ
     */
    public String getWkImgFlg() {
        return wkImgFlg;
    }

    /**
     * wk画像ファイル有無フラグ を設定する。<br/>
     * <br/>
     * @param wkImgFlg
     */
    public void setWkImgFlg(String wkImgFlg) {
        this.wkImgFlg = wkImgFlg;
    }

    /**
     * 画像ファイル１ を取得する。<br/>
     * <br/>
     * @return 画像ファイル１
     */
    public String getImgFile1() {
        return imgFile1;
    }

    /**
     * 画像ファイル１ を設定する。<br/>
     * <br/>
     * @param imgFile1
     */
    public void setImgFile1(String imgFile1) {
        this.imgFile1 = imgFile1;
    }

    /**
     * 画像ファイル２ を取得する。<br/>
     * <br/>
     * @return 画像ファイル２
     */
	public String getImgFile2() {
		return imgFile2;
	}

    /**
     * 画像ファイル２ を設定する。<br/>
     * <br/>
     * @param imgFile2
     */
	public void setImgFile2(String imgFile2) {
		this.imgFile2 = imgFile2;
	}
	   /**
     * 画像名 を取得する。<br/>
     * <br/>
     * @return 画像名
     */
    public String getImgName() {
        return imgName;
    }

    /**
     * 画像名 を設定する。<br/>
     * <br/>
     * @param imgName
     */
    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    /**
     * 画像TempPath を取得する。<br/>
     * <br/>
     * @return 画像TempPath
     */
    public String getTemPath() {
        return temPath;
    }

    /**
     * 画像TempPath を設定する。<br/>
     * <br/>
     * @param temPath
     */
    public void setTemPath(String temPath) {
        this.temPath = temPath;
    }

    /**
     * reform画像パス名 を取得する。<br/>
     * <br/>
     * @return reform画像パス名
     */
    public FileItem getReformImgFile() {
        return reformImgFile;
    }

    /**
     * reform画像パス名 を設定する。<br/>
     * <br/>
     * @param reformImgFile
     */
    public void setReformImgFile(FileItem reformImgFile) {
        this.reformImgFile = reformImgFile;
    }

    /**
     * 削除のフラグ を取得する。<br/>
     * <br/>
     * @return 削除のフラグ
     */
    public String getReformImgDel() {
        return reformImgDel;
    }

    /**
     * 削除のフラグ を設定する。<br/>
     * <br/>
     * @param reformImgDel
     */
    public void setReformImgDel(String reformImgDel) {
        this.reformImgDel = reformImgDel;
    }

    /**
     * 画像ファイル選択フラグ パラメータを取得する。<br/>
     * 画像ファイル選択フラグ パラメータの値は、フレームワークの URL マッピングで使用する 画像ファイル選択フラグ クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 画像ファイル選択フラグ パラメータ
     */
    public String getImgSelFlg() {
		return imgSelFlg;
	}

    /**
     * 画像ファイル選択フラグ パラメータを設定する。<br/>
     * 画像ファイル選択フラグ パラメータの値は、フレームワークの URL マッピングで使用する 画像ファイル選択フラグ クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param imgSelFlg
     *            画像ファイル選択フラグ パラメータ
     */
	public void setImgSelFlg(String imgSelFlg) {
		this.imgSelFlg = imgSelFlg;
	}

    /**
     * @return the planCategory1
     */
    public String getPlanCategory1() {
        return planCategory1;
    }

    /**
     * @param planCategory1
     *            the planCategory1 to set
     */
    public void setPlanCategory1(String planCategory1) {
        this.planCategory1 = planCategory1;
    }

    /**
     * @return the planCategory2
     */
    public String getPlanCategory2() {
        return planCategory2;
    }

    /**
     * @param planCategory2
     *            the planCategory2 to set
     */
    public void setPlanCategory2(String planCategory2) {
        this.planCategory2 = planCategory2;
    }

    // フォームからvoに値を設定
    public void transferToValueObject(Information entry) {
    }

    /**
     * バリデーション処理<br/>
     * リクエストパラメータのバリデーションを行う<br/>
     * <br/>
     * この Form クラスのバリデーションメソッドは、Validateable インターフェースの実装では無いので、
     * バリデーション実行時の引数が異なるので、複数 Form をまとめてバリデーションする場合などは注意
     * する事。<br/>
     * <br/>
     * @param errors エラー情報を格納するリストオブジェクト
     * @param mode 処理モード ("insert" or "update")
     * @return 正常時 true、エラー時 false
     */
    public boolean validate(List<ValidationFailure> errors) {

        int startSize = errors.size();

        // 公開区分入力チェック
        ValidationChain valHiddenFlg = new ValidationChain("reform.input.hiddenFlg", this.hiddenFlg);
        // 必須チェック
        valHiddenFlg.addValidation(new NullOrEmptyCheckValidation());
        // codeLookUpチェック
        valHiddenFlg.addValidation(new CodeLookupValidation(this.codeLookupManager, "hiddenFlg"));
        valHiddenFlg.validate(errors);

        // リフォームプラン名入力チェック
        ValidationChain valPlanName = new ValidationChain("reform.input.planName", this.planName);
        // 必須チェック
        valPlanName.addValidation(new NullOrEmptyCheckValidation());
        // 桁数チェック
        valPlanName.addValidation(new MaxLengthValidation(50));
        valPlanName.validate(errors);

        // 価格入力チェック
        ValidationChain valPlanPrice = new ValidationChain("reform.input.planPrice", this.planPrice);
        // 必須チェック
        valPlanPrice.addValidation(new NullOrEmptyCheckValidation());
        // 桁数チェック
        valPlanPrice.addValidation(new MaxLengthValidation(11));
        // 半角数字チェック
        valPlanPrice.addValidation(new NumericValidation());
        valPlanPrice.validate(errors);

        // 工期入力チェック
        ValidationChain valConstructionPeriod =
                new ValidationChain("reform.input.constructionPeriod", this.constructionPeriod);
        // 桁数チェック
        valConstructionPeriod.addValidation(new MaxLengthValidation(20));
        valConstructionPeriod.validate(errors);

        // セールスポイント入力チェック
        ValidationChain valSalesPoint =
                new ValidationChain("reform.input.salesPoint", this.salesPoint);
        // 桁数チェック
        valSalesPoint.addValidation(new MaxLengthValidation(200));
        valSalesPoint.validate(errors);

        // 備考入力チェック
        ValidationChain valNote = new ValidationChain("reform.input.note", this.note);
        // 桁数チェック
        valNote.addValidation(new MaxLengthValidation(100));
        valNote.validate(errors);

        if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.housingKindCd) ||
        		PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.housingKindCd)) {

            // 評価基準キー入力チェック
            for (int i = 0; i < getHousingKindSize("key"); i++) {
                // codeLookUpチェック
                ValidationChain valChartKey = new ValidationChain("reform.input.chartKey", this.chartKey[i]);
                if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.housingKindCd)) {
                    valChartKey.addValidation(new CodeLookupValidation(this.codeLookupManager, "inspectionTrustMansion"));
                } else if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.housingKindCd)) {
                    valChartKey.addValidation(new CodeLookupValidation(this.codeLookupManager, "inspectionTrustHouse"));
                }
                valChartKey.validate(errors);
            }

            // 評価基準Value入力チェック
            for (int j = 0; j < getHousingKindSize("value"); j++) {
                // codeLookUpチェック
                ValidationChain valChartValue = new ValidationChain("reform.input.chartValue", this.chartValue[j]);
                valChartValue.addValidation(new CodeLookupValidation(this.codeLookupManager, "inspectionResult"));
                valChartValue.validate(errors);
        	}
        }

        // 画像
        if(this.getReformImgFile()!=null){
   		 	// Validate path_name 画像
           ValidationChain valPathName = new ValidationChain(
                   "reform.input.pathName", getReformImgFile().getSize());
           // JPGチェック
           valPathName.addValidation(new ReformImgPathJpgValidation(getReformImgFile().getName(),"jpg"));
           // ファイルサイズチェック
           valPathName.addValidation(new ReformImgSizeValidation(PanaCommonConstant.updFileSize));
           valPathName.validate(errors);
        }

        // 動画_Beforeチェック
        ValidationChain valBeforeMovieUrl = new ValidationChain("reform.input.beforeMovieUrl", this.beforeMovieUrl);
        // 桁数チェック
        valBeforeMovieUrl.addValidation(new MaxLengthValidation(255));
        valBeforeMovieUrl.validate(errors);

        // 動画__Afterチェック
        ValidationChain valAfterMovieUrl = new ValidationChain("reform.input.afterMovieUrl", this.afterMovieUrl);
        // 桁数チェック
        valAfterMovieUrl.addValidation(new MaxLengthValidation(255));
        valAfterMovieUrl.validate(errors);

        // 閲覧権限
        ValidationChain valRoleId = new ValidationChain("reform.input.roleId", this.roleId);
        // codeLookUpチェック
        valRoleId.addValidation(new CodeLookupValidation(this.codeLookupManager, "ImageInfoRoleId"));
        valRoleId.validate(errors);

        // 動画_BeforeURLチェック
        if (!StringValidateUtil.isEmpty(this.beforeMovieUrl)) {
            ValidationChain beforeMovieUrl = new ValidationChain("reform.input.beforeMovieUrl", this.beforeMovieUrl);
            beforeMovieUrl.addValidation(new UrlValidation());
            beforeMovieUrl.validate(errors);
        }

        // 動画_AfterURLチェック
        if (!StringValidateUtil.isEmpty(this.afterMovieUrl)) {
            ValidationChain afterMovieUrl = new ValidationChain("reform.input.afterMovieUrl", this.afterMovieUrl);
            afterMovieUrl.addValidation(new UrlValidation());
            afterMovieUrl.validate(errors);
        }

        // 関連チェック
        // 動画_Before閲覧権限
        if (!StringValidateUtil.isEmpty(this.beforeMovieUrl) &&
                StringValidateUtil.isEmpty(this.roleId)) {
            ValidationFailure vf = new ValidationFailure(
                    "mustInput1", "動画_Before", "閲覧権限", null);
            errors.add(vf);
        }

        // 動画_After閲覧権限
        if (!StringValidateUtil.isEmpty(this.afterMovieUrl) &&
                StringValidateUtil.isEmpty(this.roleId)) {
            ValidationFailure vf = new ValidationFailure(
                    "mustInput1", "動画_After", "閲覧権限", null);
            errors.add(vf);
        }

        // 動画_Before動画_After閲覧権限
        String param[] = new String[] { "動画_After" };
        if (!StringValidateUtil.isEmpty(this.roleId) &&
                StringValidateUtil.isEmpty(this.beforeMovieUrl) &&
                StringValidateUtil.isEmpty(this.afterMovieUrl)) {
            ValidationFailure vf = new ValidationFailure(
                    "mustInput2", "閲覧権限", "動画_Before", param);
            errors.add(vf);
        }

        // 画像ファイルファイルロードチェック
        FileItem fi = this.getReformImgFile();
        if ("on".equals(this.reformImgDel) &&
        		(fi != null && !StringUtils.isEmpty(fi.getName()))) {

            ValidationFailure vf = new ValidationFailure(
                    "reformImgFileNotNull", "レーダーチャート画像", null, null);
            errors.add(vf);
        }

        // validate for categories
        if (!StringValidateUtil.isEmpty(this.planCategory1)) {

            LinkedHashMap<String, ReformPlanCategory> categoryMap = getReformPlanCategoryMap();
            ReformPlanCategory category1 = categoryMap.get(this.planCategory1);
            if (null == category1 || !category1.isSuperCategory()) {
                ValidationFailure vf = new ValidationFailure("category1Error",
                        "第1カテゴリ", null, null);
                errors.add(vf);
            }

            if (!StringValidateUtil.isEmpty(this.planCategory2)) {
                ReformPlanCategory category2 = categoryMap
                        .get(this.planCategory2);
                if (null == category2 || category2.isSuperCategory()) {
                    ValidationFailure vf = new ValidationFailure(
                            "category2Error", "第2カテゴリ", null, null);
                    errors.add(vf);
                }
            }
        }else if(!StringValidateUtil.isEmpty(this.planCategory2)){
            ValidationFailure vf = new ValidationFailure(
                    "category2Error", "第2カテゴリ", null, null);
            errors.add(vf);
            
        }

        return (startSize == errors.size());
    }

    /**
     * 引数で渡されたリフォームプラン情報のバリーオブジェクトにフォームの値を設定する。<br/>
     * 更新処理でも使用する事を考慮し、タイムスタンプ情報に関しては、更新日、更新者のみ設定する。<br/>
     * <br/>
     * @param reformPlan 値を設定するリフォームプラン情報のバリーオブジェクト
     *
     */
    public void copyToReformPlan(ReformPlan reformPlan, String editUserId) {

        if ("update".equals(this.command)) {
            // システムリフォームCD を設定
            reformPlan.setSysReformCd(this.sysReformCd);
        }

        // システム物件CD を設定
        reformPlan.setSysHousingCd(this.sysHousingCd);

        // リフォームプラン名を設定
        reformPlan.setPlanName(this.planName);

        // リフォーム価格を設定
        if(!StringValidateUtil.isEmpty(this.planPrice)){
            reformPlan.setPlanPrice(Long.valueOf(this.planPrice));
        }

        // セールスポイントを設定
        reformPlan.setSalesPoint(this.salesPoint);

        // 工期を設定
        reformPlan.setConstructionPeriod(this.constructionPeriod);

        // 備考を設定
        reformPlan.setNote(this.note);

        // before 動画URLを設定
        reformPlan.setBeforeMovieUrl(this.beforeMovieUrl);

        // after 動画URLを設定
        reformPlan.setAfterMovieUrl(this.afterMovieUrl);

        // 動画閲覧権限を設定
        reformPlan.setRoleId(this.roleId);

        // 非公開フラグを設定
        reformPlan.setHiddenFlg(this.hiddenFlg);

        // レーダーチャート画像ファイル名
        reformPlan.setReformChartImageFileName(this.imgName);

        // 登録日を設定
        reformPlan.setInsDate(new Date());

        // 登録者を設定
        reformPlan.setInsUserId(editUserId);

        // 更新日付を設定
        reformPlan.setUpdDate(new Date());

        // 更新担当者を設定
        reformPlan.setUpdUserId(editUserId);

        // reformPlan.setType(this.reformType);
        reformPlan.setPlanCategory1(planCategory1);
        reformPlan.setPlanCategory2(planCategory2);
    }

    /**
     * 引数で渡されたリフォーム・レーダーチャートのバリーオブジェクトにフォームの値を設定する。<br/>
     * 更新処理でも使用する事を考慮し、タイムスタンプ情報に関しては、更新日、更新者のみ設定する。<br/>
     * <br/>
     * @param reformChart 値を設定するリフォーム・レーダーチャート情報のバリーオブジェクト
     * @param index
     *
     */
    public void copyToReformChart(ReformChart reformChart, String index) {

        int i = Integer.parseInt(index);

        // システムリフォームCD
        reformChart.setSysReformCd(this.sysReformCd);

        if (!StringValidateUtil.isEmpty(this.chartValue[i])) {
            // チャートキー値
            reformChart.setChartKey(this.chartKey[i]);
            // チャート設定値
            reformChart.setChartValue(Integer.valueOf(this.chartValue[i]));
        }

    }

    /**
     * リフォーム情報（新規）入力画面格納<br/>
     * <br/>
     * @param form 入力値が格納された Form オブジェクト
     * @param displayHousingName 物件名称
     *
     * @exception Exception 実装クラスによりスローされる任意の例外
     */
    public void insertFormat(String displayHousingName) {

        // 物件名称
        this.setDisplayHousingName(displayHousingName);
        // 公開区分
        this.setHiddenFlg(PanaCommonConstant.HIDDEN_FLG_PUBLIC);
        // リフォームプラン名
        this.setPlanName("");
        // 価格
        this.setPlanPrice("");
        // 工期
        this.setConstructionPeriod("");
        // セールスポイント
        this.setSalesPoint("");
        // 備考
        this.setNote("");
        // before 動画URL
        this.setBeforeMovieUrl("");
        // after 動画URL
        this.setAfterMovieUrl("");
        // 閲覧権限
        this.setRoleId("");
        // リフォーム詳細情報Div
        this.setDtlFlg(DIVFLG_0);
        // リフォーム画像情報Div
        this.setImgFlg(DIVFLG_0);
        // イベントフラグ
        this.setCommand("insert");
        // wk画像ファイル有無フラグ
        this.setWkImgFlg(IMGFLG_0);
        // 画像ファイル選択フラグ
        this.setImgSelFlg(IMGFLG_0);

        // this.setReformType("");
        this.setPlanCategory1("");
        this.setPlanCategory1("");
        // リフォーム・レーダーチャートリスト
        getInitChart();
    }

    /**
     * リフォーム情報（更新）入力画面格納<br/>
     * <br/>
     * @param result リフォームプラン情報
     * @param displayHousingName 物件名称
     * @param housing 物件情報
	 * @param commonParameters 共通パラメータオブジェクト
     * @exception Exception 実装クラスによりスローされる任意の例外
     */
    @SuppressWarnings("unchecked")
    public void updateFormat(Map<String, Object> result, String displayHousingName, Housing housing,
    		PanaCommonParameters commonParameters, PanaFileUtil fileUtil) {

        if (!"iBack".equals(this.getCommand()) && !"uBack".equals(this.getCommand())) {

        	// リフォームプラン情報の取得
            ReformPlan plan = (ReformPlan) result.get("reformPlan");

            if (plan != null) {
                // 公開区分
                this.setHiddenFlg(plan.getHiddenFlg());
                // リフォームプラン名
                this.setPlanName(plan.getPlanName());
                // 価格
                this.setPlanPrice(PanaStringUtils.toString(plan.getPlanPrice()));
                // 工期
                this.setConstructionPeriod(plan.getConstructionPeriod());
                // セールスポイント
                this.setSalesPoint(plan.getSalesPoint());
                // 備考
                this.setNote(plan.getNote());
                // before 動画URL
                this.setBeforeMovieUrl(plan.getBeforeMovieUrl());
                // after 動画URL
                this.setAfterMovieUrl(plan.getAfterMovieUrl());
                // 閲覧権限
                this.setRoleId(plan.getRoleId());
                // this.setReformType(plan.getType());
                this.setPlanCategory1(plan.getPlanCategory1());
                this.setPlanCategory2(plan.getPlanCategory2());

                // 画像ファイル
   			 	String rootPath = plan.getReformChartImagePathName();
   			 	String fileName = plan.getReformChartImageFileName();

   			 	if (StringValidateUtil.isEmpty(rootPath) || StringValidateUtil.isEmpty(fileName) ) {

   			 		// wk画像ファイル有無フラグ
   	            	this.setWkImgFlg(IMGFLG_0);

   			 	} else {

   			 		// 画像ファイルパス
   	   			 	String urlPath = fileUtil.getHousFileMemberUrl(
   	   			 			rootPath, fileName, commonParameters.getAdminSiteChartFolder());

   	  		    	this.setImgName(fileName);
   		         	this.setImgFile1(urlPath);
   		         	this.setImgFile2(urlPath);

   		         	// wk画像ファイル有無フラグ
   	            	this.setWkImgFlg(IMGFLG_1);
   			 	}
            }

            // 物件名称
            this.setDisplayHousingName(displayHousingName);

            // リフォーム・レーダーチャートリスト
            getUpdateChart((List<ReformChart>) result.get("chartList"));
        }else{
        	// リフォームプラン情報の取得
            ReformPlan plan = (ReformPlan) result.get("reformPlan");

            if (plan != null) {

                // 画像ファイル
    		 	String rootPath = plan.getReformChartImagePathName();
    		 	String fileName = plan.getReformChartImageFileName();

    		 	if (StringValidateUtil.isEmpty(rootPath) || StringValidateUtil.isEmpty(fileName) ) {

    		 		// wk画像ファイル有無フラグ
                	this.setWkImgFlg(IMGFLG_0);

    		 	} else {

    		 		// 画像ファイルパス
       			 	String urlPath = fileUtil.getHousFileMemberUrl(
       			 			rootPath, fileName, commonParameters.getAdminSiteChartFolder());

      		    	this.setImgName(fileName);
    	         	this.setImgFile1(urlPath);
    	         	this.setImgFile2(urlPath);

    	         	// wk画像ファイル有無フラグ
                	this.setWkImgFlg(IMGFLG_1);
    		 	}
            }
        }

        // リフォーム詳細情報Div
        this.setDtlFlg(DIVFLG_1);
        // リフォーム画像情報Div
        this.setImgFlg(DIVFLG_1);
        // イベントフラグ
        this.setCommand("update");
        // 画像URL
        this.setImgFile1(this.getImgFile2());
    }

    /**
     * 新規初期化の場合評価基準リストを作成する。<br/>
     * <br/>
     * @param form 入力値が格納された Form オブジェクト
     */
    private void getInitChart() {

        String index = "";
        int size = getHousingKindSize("key");
        String[] chartKey = new String[size];
        String[] chartValue = new String[size];

        for (int i = 1; i <= size; i++) {
            index = String.valueOf(i);
            chartKey[i - 1] = (index);
            chartValue[i - 1] = "1";
        }

        this.setChartKey(chartKey);
        this.setChartValue(chartValue);
    }

    /**
     * 更新初期化の場合評価基準リストを作成する。<br>
     * <br/>
     * @param form ReformInfoForm  リフォームプラン情報の入力値を格納した Form オブジェクト
     *
     * @param chartList List パラメータが設定されたフォームオブジェクトを格納した List オブジェクト
     */
    protected void getUpdateChart(List<ReformChart> chartList) {

        if (chartList != null && (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.housingKindCd) ||
        		PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.housingKindCd))) {

            // codeLookupNameの取得
            String lookUpType = "";
            if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.housingKindCd)) {
                lookUpType = "inspectionTrustMansion";
            } else if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.housingKindCd)) {
                lookUpType = "inspectionTrustHouse";
            }

            // 評価基準キー値の取得
            Iterator<String> iterator = this.codeLookupManager.getKeysByLookup(lookUpType);
            List<String> keyList = new ArrayList<String>();
            if (iterator!=null) {
                while (iterator.hasNext()) {
                    keyList.add(iterator.next());
                }
            }

            // 画面項目の初期化
            String[] chartKey = new String[keyList.size()];
            String[] chartValue = new String[keyList.size()];
            ReformChart chart = new ReformChart();
            // 画面項目の格納
            for (int i = 0; i < keyList.size(); i++) {

                // codeLookupより評価基準キー値の取得
                chartKey[i] = keyList.get(i);

                chart = new ReformChart();
                for (int j = 0; j < chartList.size(); j++) {

                    // DBより評価基準キー値の取得
                    chart = (ReformChart) chartList.get(j);

                    // 評価基準Value値の設定
                    chartValue[i] = "";
                    if (chartKey[i].equals(chart.getChartKey())) {
                        chartValue[i] = PanaStringUtils.toString(chart.getChartValue());
                        break;
                    }
                }
            }

            this.setChartKey(chartKey);
            this.setChartValue(chartValue);
        }
    }

	/**
	 * リフォーム詳細ファイル設定と実行を行う。<br/>
	 * <br/>
	 * @param inputForm 入力値が格納された Form オブジェクト
	 * @param commonParameters 共通パラメータオブジェクト
	 *
	 */
    public List<ReformDtl> setDtlList(
    		List<ReformDtl> dtlList, PanaCommonParameters commonParameters, PanaFileUtil fileUtil) {

    	List<ReformDtl> resultList = new ArrayList<ReformDtl>();

        if (dtlList != null) {
        	for(ReformDtl dtl:dtlList) {

				 // PDFファイルパスの取得
	   	         String pdfPath = "";
		         if (PanaCommonConstant.ROLE_ID_PUBLIC.equals(dtl.getRoleId())) {
		        	 pdfPath = fileUtil.getHousFileOpenUrl(
		        			 dtl.getPathName(), dtl.getFileName(), commonParameters.getAdminSitePdfFolder());
		         } else if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(dtl.getRoleId())) {
		        	 pdfPath = fileUtil.getHousFileMemberUrl(
		        			 dtl.getPathName(), dtl.getFileName(), commonParameters.getAdminSitePdfFolder());
		         }

		         // PDFファイルパスの再設定
		         dtl.setPathName(pdfPath);
		         resultList.add(dtl);
            }
        }

        return resultList;
	}

	/**
	 * リフォーム画像ファイル設定と実行を行う。<br/>
	 * <br/>
	 * @param imgList リフォーム画像情報オブジェクト
	 * @param commonParameters 共通パラメータオブジェクト
	 */
    public List<ReformImg> setImgList (List<ReformImg> imgList,
    		PanaCommonParameters commonParameters, PanaFileUtil fileUtil) {

    	List<ReformImg> resultList = new ArrayList<ReformImg>();
    	int size = commonParameters.getThumbnailSizes().size()-1;

        if (imgList != null) {
        	for(ReformImg img :imgList) {

          		 //  before 画像パスの設定
        		 String sBeforePath = "";
        		 String lBeforePath = "";
        		 String sAfterPath = "";
        		 String lAfterPath = "";

				 if (PanaCommonConstant.ROLE_ID_PUBLIC.equals(img.getRoleId())) {

					 // before 画像86px
			         sBeforePath = fileUtil.getHousFileOpenUrl(img.getBeforePathName(),
			        		 img.getBeforeFileName(), String.valueOf(commonParameters.getThumbnailSizes().get(0)));
			         // before 画像800px
			         lBeforePath = fileUtil.getHousFileOpenUrl(img.getBeforePathName(),
			        		 img.getBeforeFileName(), String.valueOf(commonParameters.getThumbnailSizes().get(size)));
			         // after 画像86px
			         sAfterPath = fileUtil.getHousFileOpenUrl(img.getAfterPathName(),
			        		 img.getAfterFileName(), String.valueOf(commonParameters.getThumbnailSizes().get(0)));
			         // after 画像800px
			         lAfterPath = fileUtil.getHousFileOpenUrl(img.getAfterPathName(),
			        		 img.getAfterFileName(), String.valueOf(commonParameters.getThumbnailSizes().get(size)));


				 } else if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(img.getRoleId())) {

					 // before 画像86px
			         sBeforePath = fileUtil.getHousFileMemberUrl(img.getBeforePathName(),
			        		 img.getBeforeFileName(), String.valueOf(commonParameters.getThumbnailSizes().get(0)));
			         // before 画像800px
			         lBeforePath = fileUtil.getHousFileMemberUrl(img.getBeforePathName(),
			        		 img.getBeforeFileName(), String.valueOf(commonParameters.getThumbnailSizes().get(size)));
			         // after 画像86px
			         sAfterPath = fileUtil.getHousFileMemberUrl(img.getAfterPathName(),
			        		 img.getAfterFileName(), String.valueOf(commonParameters.getThumbnailSizes().get(0)));
			         // after 画像800px
			         lAfterPath = fileUtil.getHousFileMemberUrl(img.getAfterPathName(),
			        		 img.getAfterFileName(), String.valueOf(commonParameters.getThumbnailSizes().get(size)));
				 }

		         img.setBeforePathName(sBeforePath);
		         img.setBeforeFileName(lBeforePath);
		         img.setAfterPathName(sAfterPath);
		         img.setAfterFileName(lAfterPath);

		         resultList.add(img);
            }
        }

        return resultList;
	}

    /**
     * 評価基準SIZEを取得する。<br>
     * <br/>
     */
    private int getHousingKindSize (String type) {
        // codeLookupNameの取得
        String lookUpType = "";
        if ("key".equals(type)) {
        	if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.housingKindCd)) {
                lookUpType = "inspectionTrustMansion";
            } else if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.housingKindCd)) {
                lookUpType = "inspectionTrustHouse";
            }
        } else if ("value".equals(type)) {
        	lookUpType = "inspectionResult";
        }

        // 値の取得
        Iterator<String> iterator = this.codeLookupManager.getKeysByLookup(lookUpType);
        List<String> keyList = new ArrayList<String>();
        if (iterator!=null) {
            while (iterator.hasNext()) {
                keyList.add(iterator.next());
            }
        }

        return keyList.size();
    }

    /**
     * Convert reform categories from Map<String, String> in code lookup to
     * LinkedHashMap<String, ReformPlanCategory>
     * 
     * @return
     */
    protected LinkedHashMap<String, ReformPlanCategory> getReformPlanCategoryMap() {
        if (reformPlanCategoryMap == null) {
            String code = "reformPlanCategories";
            // get code lookup "reformPlanCategories"
            Iterator<String> keys = codeLookupManager.getKeysByLookup(code);
            LinkedHashMap<String, String> categories = new LinkedHashMap<>();
            while (keys.hasNext()) {
                String key = keys.next();
                categories.put(key, codeLookupManager.lookupValue(code, key));
            }

            // build category tree from this code lookup
            reformPlanCategoryMap = buildCategoryTree(categories, "_", null,
                    null);
        }
        return reformPlanCategoryMap;
    }

    /**
     * Get all parent categories and their children
     * 
     * @return a list of reform category
     */
    public List<ReformPlanCategory> getSupperCategories() {
        return getSupperCategories(getReformPlanCategoryMap());
    }

    /**
     * Get parent categories from category tree
     * 
     * @param tree
     *            LinkedHashMap of ReformPlanCategory
     * @return list of parent ReformPlanCategory
     */
    protected List<ReformPlanCategory> getSupperCategories(
            LinkedHashMap<String, ReformPlanCategory> tree) {
        List<ReformPlanCategory> categories = new ArrayList<>();
        for (ReformPlanCategory category : tree.values()) {
            // check category is parent
            if (category.isSuperCategory()) {
                categories.add(category);
            }
        }
        return categories;
    }

    /**
     * Build category tree from map of category
     * 
     * @param categoryMap
     * @param delimeter
     * @param prefix
     * @param subfix
     * @return
     */
    protected LinkedHashMap<String, ReformPlanCategory> buildCategoryTree(
            LinkedHashMap<String, String> categoryMap, String delimeter,
            String prefix, String subfix) {
        LinkedHashMap<String, ReformPlanCategory> tree = new LinkedHashMap<>();
        for (Entry<String, String> category : categoryMap.entrySet()) {
            String idPath = category.getKey();
            String idLabel = category.getValue();
            String[] ids = StringUtils.split(idPath, delimeter);
            if (ids == null || ids.length == 0) {
                ids = new String[] { idPath };
            }
            String id = ids[ids.length - 1];
            id = removePrefix(id, prefix);
            id = removeSubfix(id, subfix);
            ReformPlanCategory me;
            if (tree.containsKey(id) && (me = tree.get(id)).isNew()) {
                me.setName(idLabel);
            } else {
                me = new ReformPlanCategory(id, idLabel);
                tree.put(id, me);
            }
            if (ids.length == 1) {
                continue;
            }
            String parentId = ids[ids.length - 2];
            parentId = removePrefix(parentId, prefix);
            parentId = removeSubfix(parentId, subfix);
            ReformPlanCategory parent;
            if (tree.containsKey(parentId)) {
                parent = tree.get(parentId);
            } else {
                parent = new ReformPlanCategory(parentId);
                tree.put(parentId, parent);
            }

            me.setParent(parent);
            parent.addChild(me);
        }
        return tree;
    }

    /**
     * Remove prefix from a given string
     * 
     * @param value
     * @param prefix
     * @return
     */
    private String removePrefix(String value, String prefix) {
        if (StringUtils.hasLength(prefix) && value.startsWith(prefix)) {
            return value.substring(prefix.length());
        } else {
            return value;
        }
    }

    /**
     * Remove subfix from a given string
     * 
     * @param value
     * @param subfix
     * @return
     */
    private String removeSubfix(String value, String subfix) {
        if (StringUtils.hasLength(subfix) && value.endsWith(subfix)) {
            return value.substring(value.length() - subfix.length());
        } else {
            return value;
        }
    }

    /**
     * Get all categories as json string
     * 
     * @return null if can not parse to json string
     */
    public String getSuperCategoriesAsJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(getSupperCategories());
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
