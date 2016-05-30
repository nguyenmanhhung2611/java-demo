package jp.co.transcosmos.dm3.core.model.housingRequest;

import java.util.ArrayList;
import java.util.List;

import jp.co.transcosmos.dm3.core.vo.HousingReqKind;
import jp.co.transcosmos.dm3.core.vo.HousingReqLayout;
import jp.co.transcosmos.dm3.core.vo.HousingReqPart;
import jp.co.transcosmos.dm3.core.vo.HousingReqRoute;
import jp.co.transcosmos.dm3.core.vo.HousingReqStation;
import jp.co.transcosmos.dm3.core.vo.HousingRequestArea;
import jp.co.transcosmos.dm3.core.vo.HousingRequestInfo;


/**
 * <pre>
 * 物件リクエスト情報クラス
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.11	新規作成
 *
 * 注意事項
 * Model 以外から直接インスタンスを生成しない事。
 *
 * </pre>
 */
public class HousingRequest {

	/** 物件リクエスト情報 */
	private HousingRequestInfo housingRequestInfo;
	/** 物件リクエストエリア情報のリスト */
	private List<HousingRequestArea> housingRequestAreas = new ArrayList<>();
	/** 物件リクエスト間取り情報のリスト */
	private List<HousingReqLayout> housingReqLayouts = new ArrayList<>();
	/** 物件リクエスト路線情報のリスト */
	private List<HousingReqRoute> housingReqRoutes = new ArrayList<>();
	/** 物件リクエスト最寄り駅情報のリスト*/
	private List<HousingReqStation> housingReqStations = new ArrayList<>();
	/** 物件リクエスト物件種類情報のリスト */
	private List<HousingReqKind> housingReqKinds = new ArrayList<>();
	/** 物件リクエストこだわり条件情報のリスト */
	private List<HousingReqPart> housingReqParts = new ArrayList<>();

	
	
	/**
	 * コンストラクター<br/>
	 * 物件リクエストのモデル以外からインスタンスを生成出来ない様にコンストラクタを制限する。<br/>
	 * <br/>
	 */
	protected HousingRequest() {
		super();
	}



	/**
	 * 物件リクエスト情報を取得する。<br/>
	 * <br/>
	 * @return 物件リクエスト情報
	 */
	public HousingRequestInfo getHousingRequestInfo() {
		return housingRequestInfo;
	}

	/**
	 * 物件リクエスト情報を設定する。<br/>
	 * <br/>
	 * @param housingRequestInfo 物件リクエスト情報
	 */
	public void setHousingRequestInfo(HousingRequestInfo housingRequestInfo) {
		this.housingRequestInfo = housingRequestInfo;
	}

	/**
	 * 物件リクエストエリア情報のリストを取得する。<br/>
	 * <br/>
	 * @return 物件リクエストエリア情報のリスト
	 */
	public List<HousingRequestArea> getHousingRequestAreas() {
		return housingRequestAreas;
	}

	/**
	 * 物件リクエストエリア情報のリストを設定する。<br/>
	 * <br/>
	 * @param housingRequestAreas 物件リクエストエリア情報
	 */
	public void setHousingRequestAreas(List<HousingRequestArea> housingRequestAreas) {
		this.housingRequestAreas = housingRequestAreas;
	}
	
	/**
	 * 物件リクエスト間取り情報のリストを取得する。<br/>
	 * <br/>
	 * @return 物件リクエスト間取り情報のリスト
	 */
	public List<HousingReqLayout> getHousingReqLayouts() {
		return housingReqLayouts;
	}
	
	/**
	 * 物件リクエスト間取り情報のリストを設定する。<br/>
	 * <br/>
	 * @param housingReqLayouts 物件リクエスト間取り情報のリスト
	 */
	public void setHousingReqLayouts(List<HousingReqLayout> housingReqLayouts) {
		this.housingReqLayouts = housingReqLayouts;
	}

	/**
	 * 物件リクエスト路線情報のリストを取得する。<br/>
	 * <br/>
	 * @return 物件リクエスト路線情報のリスト
	 */
	public List<HousingReqRoute> getHousingReqRoutes() {
		return housingReqRoutes;
	}

	/**
	 * 物件リクエスト路線情報のリストを設定する。<br/>
	 * <br/>
	 * @param housingReqRoutes 物件リクエスト路線情報のリスト
	 */
	public void setHousingReqRoutes(List<HousingReqRoute> housingReqRoutes) {
		this.housingReqRoutes = housingReqRoutes;
	}

	/**
	 * 物件リクエスト最寄り駅情報のリストを取得する。<br/>
	 * <br/>
	 * @return 物件リクエスト最寄り駅情報のリスト
	 */
	public List<HousingReqStation> getHousingReqStations() {
		return housingReqStations;
	}
	
	/**
	 * 物件リクエスト最寄り駅情報のリストを設定する。<br/>
	 * <br/>
	 * @param housingReqStations 物件リクエスト最寄り駅情報のリスト
	 */
	public void setHousingReqStations(List<HousingReqStation> housingReqStations) {
		this.housingReqStations = housingReqStations;
	}

	/**
	 * 物件リクエスト物件種類情報のリストを取得する。<br/>
	 * <br/>
	 * @return 物件リクエスト物件種類情報のリスト
	 */
	public List<HousingReqKind> getHousingReqKinds() {
		return housingReqKinds;
	}
	
	/**
	 * 物件リクエスト物件種類情報のリストを設定する。<br/>
	 * <br/>
	 * @param housingReqKinds 物件リクエスト物件種類情報のリスト
	 */
	public void setHousingReqKind(List<HousingReqKind> housingReqKinds) {
		this.housingReqKinds = housingReqKinds;
	}
	
	/**
	 * 物件リクエストこだわり条件情報のリストを取得する。<br/>
	 * <br/>
	 * @return 物件リクエストこだわり条件情報のリスト
	 */
	public List<HousingReqPart> getHousingReqParts() {
		return housingReqParts;
	}

	/**
	 * 物件リクエストこだわり条件情報のリストを設定する。<br/>
	 * <br/>
	 * @param housingReqParts 物件リクエストこだわり条件情報のリスト
	 */
	public void setHousingReqPart(List<HousingReqPart> housingReqParts) {
		this.housingReqParts = housingReqParts;
	}

}
