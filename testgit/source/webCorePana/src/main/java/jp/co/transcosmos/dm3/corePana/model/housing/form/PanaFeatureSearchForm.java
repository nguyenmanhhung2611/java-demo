package jp.co.transcosmos.dm3.corePana.model.housing.form;

import jp.co.transcosmos.dm3.core.model.feature.form.FeatureSearchForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.form.annotation.UsePagingParam;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.util.StringUtils;
/**
 * 特集テンプレート情報の検索パラメータ、および、画面コントロールパラメータ受取り用フォーム.
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
public class PanaFeatureSearchForm extends FeatureSearchForm{

	/** ディフォルト順（ソート条件） */
	public static final String SORT_DEFAULT = "4";

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

	/** ソート条件 */
	@UsePagingParam
	private String keyOrderType;

	/** 共通コード変換処理 */
    private CodeLookupManager codeLookupManager;
    /** レングスバリデーションで使用する文字列長を取得するユーティリティ */
    protected LengthValidationUtils lengthUtils;

    /**
     * デフォルトコンストラクター<br/>
     * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
     * <br/>
     */
    PanaFeatureSearchForm(CodeLookupManager codeLookupManager, LengthValidationUtils lengthUtils) {
        super();
        this.codeLookupManager = codeLookupManager;
        this.lengthUtils = lengthUtils;
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
     * 渡されたバリーオブジェクトから Form へ初期値を設定する。<br/>
     * <br/>
     * @param prefMstList List<PrefMst>　を実装した、都道府県マスタ管理用バリーオブジェクト
     * @param full false の場合、都道府県マスタレストをのみ設定する。　true の場合はすべて設定します。
     *
     */
    public void setDefaultData() {

    	// ディフォルトのソート順を設定する。
		if (StringUtils.isEmpty(this.getKeyOrderType())) {
			this.setSortUpdDateValue("3");
			this.setSortPriceValue("1");
			this.setSortBuildDateValue("6");
			this.setSortWalkTimeValue("7");

			this.setKeyOrderType(SORT_DEFAULT);

		}
    }
}