package jp.co.transcosmos.dm3.core.model.housing.form;

import java.math.BigDecimal;

import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.form.PagingListForm;

/**
 * 物件検索条件の入力パラメータ受取り用フォーム.
 * <p>
 * 
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.11	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * この Form のバリデーションは、使用するモード（追加処理 or 更新処理）で異なるので、<br/>
 * フレームワークが提供する Validateable インターフェースは実装していない。<br/>
 * バリデーション実行時のパラメータが通常と異なるので注意する事。<br/>
 * <br/>
 * 特集ページ情報のクエリー文字列から検索条件を生成する処理の為、プロパティは以下の通りの定義とすること。<br/>
 * ・名称は HousingFrom、buildingForm のフィールド名をキャピタライズし、先頭に「key」を付与したものとすること。<br/>
 * ・プリミティブ型は使用しない。対応したラッパークラスで定義すること。
 * 
 */
public class HousingSearchForm extends PagingListForm<Housing> {

	// 特集ページ情報のクエリー文字列から検索条件を生成する処理の為、
	// プロパティは以下の通りの定義とする。
	// ・名称は HousingFrom、buildingForm のフィールド名を
	// 　キャピタライズし、先頭に「key」を付与したものとすること。
	// ・プリミティブ型は使用しない。対応したラッパークラスで定義すること。

	/** 物件番号（検索条件） */
	private String keyHousingCd;
	/** 表示用物件名（検索条件） */
	private String keyDisplayHousingName;
	/** 都道府県CD（検索条件） */
	private String keyPrefCd;
	/** 市区町村CD（検索条件） */
	private String keyAddressCd;

	/** 沿線CD（検索条件） */
	private String keyRouteCd;
	/** 駅CD（検索条件） */
	private String keyStationCd;

	/** 賃料/価格・下限（検索条件） */
	private Long keyPriceLower;
	/** 賃料/価格・上限（検索条件） */
	private Long keyPriceUpper;

	/** 土地面積・下限（検索条件） */
	private BigDecimal keyLandAreaLower;
	/** 土地面積・上限（検索条件） */
	private BigDecimal keyLandAreaUpper;
	/** 専有面積・下限（検索条件） */
	private BigDecimal keyPersonalAreaLower;
	/** 専有面積・上限（検索条件） */
	private BigDecimal keyPersonalAreaUpper;

	/** 物件種類CD（検索条件） */
	private String keyHousingKindCd;
	/** 間取りCD（検索条件） */
	private String keyLayoutCd;

	/** 建物面積・下限（検索条件） */
	private BigDecimal keyBuildingAreaLower;
	/** 建物面積・上限（検索条件） */
	private BigDecimal keyBuildingAreaUpper;

	/** こだわり条件CD （検索条件） */
	private String keyPartSrchCd;

	/** ソート条件 */
	private String keyOrderType;

	/** 処理対象システム物件CD */
	private String sysHousingCd;

	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	protected HousingSearchForm() {

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
	 * 表示用物件名（検索条件）を取得する。<br/>
	 * <br/>
	 * 
	 * @return 表示用物件名
	 */
	public String getKeyDisplayHousingName() {
		return keyDisplayHousingName;
	}

	/**
	 * 表示用物件名（検索条件）を設定する。<br/>
	 * <br/>
	 * 
	 * @param keyDisplayHousingName 表示用物件名
	 */
	public void setKeyDisplayHousingName(String keyDisplayHousingName) {
		this.keyDisplayHousingName = keyDisplayHousingName;
	}

	/**
	 * 都道府県CD（検索条件）を取得する。<br/>
	 * <br/>
	 * 
	 * @return 都道府県CD（検索条件）
	 */
	public String getKeyPrefCd() {
		return keyPrefCd;
	}

	/**
	 * 都道府県CD（検索条件）を設定する。<br/>
	 * <br/>
	 * 
	 * @param keyPrefCd 都道府県CD（検索条件）
	 */
	public void setKeyPrefCd(String keyPrefCd) {
		this.keyPrefCd = keyPrefCd;
	}

	/**
	 * 市区町村CD（検索条件）を取得する。<br/>
	 * <br/>
	 * 
	 * @return 市区町村CD （検索条件）
	 */
	public String getKeyAddressCd() {
		return keyAddressCd;
	}

	/**
	 * 市区町村CD（検索条件）を設定する。<br/>
	 * <br/>
	 * 
	 * @param keyAddressCd 市区町村CD （検索条件）
	 */
	public void setKeyAddressCd(String keyAddressCd) {
		this.keyAddressCd = keyAddressCd;
	}

	/**
	 * 沿線CD（検索条件）を取得する。<br/>
	 * <br/>
	 * 
	 * @return 沿線CD （検索条件）
	 */
	public String getKeyRouteCd() {
		return keyRouteCd;
	}

	/**
	 * 沿線CD（検索条件）を設定する。<br/>
	 * <br/>
	 * 
	 * @param keyRouteCd 沿線CD （検索条件）
	 */
	public void setKeyRouteCd(String keyRouteCd) {
		this.keyRouteCd = keyRouteCd;
	}

	/**
	 * 駅CD （検索条件）を取得する。<br/>
	 * <br/>
	 * 
	 * @return 駅CD（検索条件）
	 */
	public String getKeyStationCd() {
		return keyStationCd;
	}

	/**
	 * 駅CD （検索条件）を設定する。<br/>
	 * <br/>
	 * 
	 * @param keyStationCd 駅CD （検索条件）
	 */
	public void setKeyStationCd(String keyStationCd) {
		this.keyStationCd = keyStationCd;
	}

	/**
	 * 賃料/価格・下限（検索条件）を取得する。<br/>
	 * <br/>
	 * 
	 * @return 賃料/価格・下限 （検索条件）
	 */
	public Long getKeyPriceLower() {
		return keyPriceLower;
	}

	/**
	 * 賃料/価格・下限（検索条件）を設定する。<br/>
	 * <br/>
	 * 
	 * @param keyPriceLower 賃料/価格・下限 （検索条件）
	 */
	public void setKeyPriceLower(Long keyPriceLower) {
		this.keyPriceLower = keyPriceLower;
	}

	/**
	 * 賃料/価格・上限（検索条件）を取得する。<br/>
	 * <br/>
	 * 
	 * @return 賃料/価格・上限 （検索条件）
	 */
	public Long getKeyPriceUpper() {
		return keyPriceUpper;
	}

	/**
	 * 賃料/価格・上限（検索条件）を設定する。<br/>
	 * <br/>
	 * 
	 * @param keyPriceUpper 賃料/価格・上限 （検索条件）
	 */
	public void setKeyPriceUpper(Long keyPriceUpper) {
		this.keyPriceUpper = keyPriceUpper;
	}

	/**
	 * 土地面積・下限（検索条件）を取得する。<br/>
	 * <br/>
	 * 
	 * @return 土地面積・下限 （検索条件）
	 */
	public BigDecimal getKeyLandAreaLower() {
		return keyLandAreaLower;
	}

	/**
	 * 土地面積・下限（検索条件）を設定する。<br/>
	 * <br/>
	 * 
	 * @param keyLandAreaLower 土地面積・下限（検索条件）
	 */
	public void setKeyLandAreaLower(BigDecimal keyLandAreaLower) {
		this.keyLandAreaLower = keyLandAreaLower;
	}

	/**
	 * 土地面積・上限（検索条件）を取得する。<br/>
	 * <br/>
	 * 
	 * @return 土地面積・上限（検索条件）
	 */
	public BigDecimal getKeyLandAreaUpper() {
		return keyLandAreaUpper;
	}

	/**
	 * 土地面積・上限（検索条件）を設定する。<br/>
	 * <br/>
	 * 
	 * @param keyLandAreaUpper 土地面積・上限（検索条件）
	 */
	public void setKeyLandAreaUpper(BigDecimal keyLandAreaUpper) {
		this.keyLandAreaUpper = keyLandAreaUpper;
	}

	/**
	 * 専有面積・下限（検索条件）を取得する。<br/>
	 * <br/>
	 * 
	 * @return 専有面積・下限（検索条件）
	 */
	public BigDecimal getKeyPersonalAreaLower() {
		return keyPersonalAreaLower;
	}

	/**
	 * 専有面積・下限（検索条件）を設定する。<br/>
	 * <br/>
	 * 
	 * @param keyPersonalAreaLower 専有面積・下限（検索条件）
	 */
	public void setKeyPersonalAreaLower(BigDecimal keyPersonalAreaLower) {
		this.keyPersonalAreaLower = keyPersonalAreaLower;
	}

	/**
	 * 専有面積・上限（検索条件）を取得する。<br/>
	 * <br/>
	 * 
	 * @return 専有面積・上限（検索条件）
	 */
	public BigDecimal getKeyPersonalAreaUpper() {
		return keyPersonalAreaUpper;
	}

	/**
	 * 専有面積・上限（検索条件）を設定する。<br/>
	 * <br/>
	 * 
	 * @param keyPersonalAreaUpper 専有面積・上限（検索条件）
	 */
	public void setKeyPersonalAreaUpper(BigDecimal keyPersonalAreaUpper) {
		this.keyPersonalAreaUpper = keyPersonalAreaUpper;
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
	 * 物件種類CD（検索条件）を取得する。<br/>
	 * <br/>
	 * 
	 * @return 物件種類CD（検索条件）
	 */
	public String getKeyHousingKindCd() {
		return keyHousingKindCd;
	}

	/**
	 * 物件種類CD（検索条件）を設定する。<br/>
	 * <br/>
	 * 
	 * @param keyHousingKindCd 物件種類CD（検索条件）
	 */
	public void setKeyHousingKindCd(String keyHousingKindCd) {
		this.keyHousingKindCd = keyHousingKindCd;
	}

	/**
	 * 建物面積・下限（検索条件）を取得する。<br/>
	 * <br/>
	 * 
	 * @return 建物面積・下限（検索条件）
	 */
	public BigDecimal getKeyBuildingAreaLower() {
		return keyBuildingAreaLower;
	}

	/**
	 * 建物面積・下限（検索条件）を設定する。<br/>
	 * <br/>
	 * 
	 * @param keyPersonalAreaLower 建物面積・下限（検索条件）
	 */
	public void setKeyBuildingAreaLower(BigDecimal keyBuildingAreaLower) {
		this.keyBuildingAreaLower = keyBuildingAreaLower;
	}

	/**
	 * 建物面積・上限（検索条件）を取得する。<br/>
	 * <br/>
	 * 
	 * @return 建物面積・上限（検索条件）
	 */
	public BigDecimal getKeyBuildingAreaUpper() {
		return keyBuildingAreaUpper;
	}

	/**
	 * 建物面積・上限（検索条件）を設定する。<br/>
	 * <br/>
	 * 
	 * @param keyBuildingAreaUpper 建物面積・上限（検索条件）
	 */
	public void setKeyBuildingAreaUpper(BigDecimal keyBuildingAreaUpper) {
		this.keyBuildingAreaUpper = keyBuildingAreaUpper;
	}

	/**
	 * こだわり条件CD（検索条件）を取得する。<br/>
	 * <br/>
	 * 
	 * @return こだわり条件CD（検索条件）
	 */
	public String getKeyPartSrchCd() {
		return keyPartSrchCd;
	}

	/**
	 * こだわり条件CD（検索条件）を設定する。<br/>
	 * <br/>
	 * 
	 * @param keyPartSrchCd こだわり条件CD（検索条件）
	 */
	public void setKeyPartSrchCd(String keyPartSrchCd) {
		this.keyPartSrchCd = keyPartSrchCd;
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
	 * 処理対象システム物件CD を取得する。<br/>
	 * <br/>
	 * 
	 * @return 処理対象システム物件CD
	 */
	public String getSysHousingCd() {
		return sysHousingCd;
	}

	/**
	 * 処理対象システム物件CD を設定する。<br/>
	 * <br/>
	 * 
	 * @param sysHousingCd 処理対象システム物件CD
	 */
	public void setSysHousingCd(String sysHousingCd) {
		this.sysHousingCd = sysHousingCd;
	}

}
