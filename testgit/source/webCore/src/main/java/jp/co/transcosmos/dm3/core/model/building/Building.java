package jp.co.transcosmos.dm3.core.model.building;

import java.util.List;

import jp.co.transcosmos.dm3.core.vo.BuildingLandmark;
import jp.co.transcosmos.dm3.dao.JoinResult;

/**
 * 建物情報クラス.
 * もし、カスタマイズで建物情報を構成するテーブルが増加した場合、このクラスを継承して拡張すること。<br/>
 * また、継承した場合は必ず BuildingManageImpl の createBuildingInstace() をオーバーライドする事。<br/>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.03.16	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスは個別カスタマイズで拡張される可能性があるので直接インスタンスを生成しない事。
 * 必ず model クラスから取得する事。
 * 
 */
public class Building {

	/** 建物基本情報 （建物基本情報＋建物詳細＋都道府県マスタ、＋市区町村マスタ）*/
	private JoinResult buildingInfo;

	/** 最寄り駅情報 */
	private List<JoinResult> buildingStationInfoList;

	/** 建物ランドマーク情報 */
	private List<BuildingLandmark> buildingLandmarkList;

	// note
	// 現行バージョンでは、建物画像情報はサポートしていないので、プロパティが存在しない。
	// 将来、項目が追加される可能性がある。



	/**
	 * コンストラクター<br/>
	 * 物件リクエストのモデル以外からインスタンスを生成出来ない様にコンストラクタを制限する。<br/>
	 * <br/>
	 */
	protected Building() {
		super();
	}


	/**
	 * 建物基本情報を取得する。<br/>
	 * <br/>
	 * @return 建物基本情報
	 */
	public JoinResult getBuildingInfo() {
		return buildingInfo;
	}


	/**
	 * 建物基本情報を設定する。<br/>
	 * <br/>
	 * @param buildingInfo 建物基本情報
	 */
	public void setBuildingInfo(JoinResult buildingInfo) {
		this.buildingInfo = buildingInfo;
	}


	/**
	 * 最寄り駅情報を取得する。<br/>
	 * <br/>
	 * @return 最寄り駅情報
	 */
	public List<JoinResult> getBuildingStationInfoList() {
		return buildingStationInfoList;
	}


	/**
	 * 最寄り駅情報を設定する。<br/>
	 * <br/>
	 * @param buildingStationInfoList 最寄り駅情報
	 */
	public void setBuildingStationInfoList(List<JoinResult> buildingStationInfoList) {
		this.buildingStationInfoList = buildingStationInfoList;
	}


	/**
	 * 建物ランドマーク情報を取得する。<br/>
	 * <br/>
	 * @return 建物ランドマーク情報
	 */
	public List<BuildingLandmark> getBuildingLandmarkList() {
		return buildingLandmarkList;
	}


	/**
	 * 建物ランドマーク情報を設定する。<br/>
	 * <br/>
	 * @param buildingLandmarkList 建物ランドマーク情報
	 */
	public void setBuildingLandmarkList(List<BuildingLandmark> buildingLandmarkList) {
		this.buildingLandmarkList = buildingLandmarkList;
	}
	
	
}
