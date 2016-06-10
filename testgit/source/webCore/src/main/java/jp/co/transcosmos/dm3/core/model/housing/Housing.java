package jp.co.transcosmos.dm3.core.model.housing;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.model.building.Building;
import jp.co.transcosmos.dm3.core.vo.EquipMst;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.dao.JoinResult;

/**
 * 物件情報クラス.
 * もし、カスタマイズで物件情報を構成するテーブルが増加した場合、このクラスを継承して拡張すること。<br/>
 * また、継承した場合は必ず HousingManageImpl の createHousingInstace() をオーバーライドする事。<br/>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.12	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスは個別カスタマイズで拡張される可能性があるので直接インスタンスを生成しない事。
 * 必ず model クラスから取得する事。
 * 
 */
public class Housing {

	/** 建物情報 */
	private Building building;

	/** 物件基本情報 （物件基本情報＋物件詳細情報＋物件ステータス情報） */
	private JoinResult housingInfo;

	/**
	 * 物件設備情報の Map<br/>
	 * Key = 設備CD、 Value = 設備情報マスタ<br/>
	 */
	private Map<String, EquipMst> housingEquipInfos = new LinkedHashMap<>();

	/** 物件画像情報のリスト */
	private List<HousingImageInfo> housingImageInfos = new ArrayList<>();

	/**
	 *  物件拡張属性情報のマップ<br/>
	 *     ・Key = 物件拡張情報のカテゴリ名（category）
	 *     ・Value = カテゴリの該当する、Key値が設定された Map オブジェクト（Key = keyName列、Value = dataValue列）
	 */
	private Map<String, Map<String,String>> housingExtInfos = new LinkedHashMap<>();



	/**
	 * コンストラクター<br/>
	 * 物件リクエストのモデル以外からインスタンスを生成出来ない様にコンストラクタを制限する。<br/>
	 * <br/>
	 */
	protected Housing() {
		super();
	}



	/**
	 * 建物情報を取得する。<br/>
	 * <br/>
	 * @return 建物情報
	 */
	public Building getBuilding() {
		return building;
	}

	/**
	 * 建物情報を設定する。<br/>
	 * <br/>
	 * @param building 建物情報
	 */
	public void setBuilding(Building building) {
		this.building = building;
	}

	/**
	 * 物件基本情報を取得する。<br/>
	 * <br/>
	 * @return 物件基本情報 （物件基本情報＋都道府県マスタ＋市区町村マスタ）
	 */
	public JoinResult getHousingInfo() {
		return housingInfo;
	}

	/**
	 * 物件基本情報を設定する。<br/>
	 * <br/>
	 * @param housingInfo 物件基本情報 （物件基本情報＋都道府県マスタ＋市区町村マスタ）
	 */
	public void setHousingInfo(JoinResult housingInfo) {
		this.housingInfo = housingInfo;
	}
	
	/**
	 * 物件設備情報の Map を取得する。<br/>
	 * <br/>
	 * @return 物件設備情報の Map
	 */
	public Map<String, EquipMst> getHousingEquipInfos() {
		return housingEquipInfos;
	}

	/**
	 * 物件設備情報の Map を設定する。<br/>
	 * <br/>
	 * @return 物件設備情報の Map
	 */
	public void setHousingEquipInfos(Map<String, EquipMst> housingEquipInfos) {
		this.housingEquipInfos = housingEquipInfos;
	}

	/**
	 * 物件拡張属性情報のマップを取得する。<br/>
	 * <br/>
	 * @return 物件拡張属性情報のマップ
	 */
	public Map<String, Map<String,String>> getHousingExtInfos() {
		return housingExtInfos;
	}
	
	/**
	 * 物件拡張属性情報のマップを設定する。<br/>
	 * <br/>
	 * @param housingExtInfos 物件拡張属性情報のマップ
	 */
	public void setHousingExtInfos(Map<String, Map<String,String>> housingExtInfos) {
		this.housingExtInfos = housingExtInfos;
	}

	/**
	 * 物件画像情報のリストを取得する。<br/>
	 * <br/>
	 * @return 物件画像情報のリスト
	 */
	public List<HousingImageInfo> getHousingImageInfos() {
		return housingImageInfos;
	}

	/**
	 * 物件画像情報のリストを設定する。<br/>
	 * <br/>
	 * @param housingImageInfos 物件画像情報のリスト
	 */
	public void setHousingImageInfos(List<HousingImageInfo> housingImageInfos) {
		this.housingImageInfos = housingImageInfos;
	}

}
