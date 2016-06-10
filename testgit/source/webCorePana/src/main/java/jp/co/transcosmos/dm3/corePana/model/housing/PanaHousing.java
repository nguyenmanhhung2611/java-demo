package jp.co.transcosmos.dm3.corePana.model.housing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.vo.AdminLoginInfo;
import jp.co.transcosmos.dm3.corePana.vo.HousingInspection;

/**
 * 物件情報クラス.
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.04.16     新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスは個別カスタマイズで拡張される可能性があるので直接インスタンスを生成しない事。
 * 必ず model クラスから取得する事。
 *
 */
public class PanaHousing extends Housing {

	/** 物件インスペクションのリスト */
	private List<HousingInspection> housingInspections = new ArrayList<>();

	/** リフォーム情報のMap */
	private  List<Map<String, Object>> reforms =new ArrayList<Map<String,Object>>();

	/** 物件基本情報最終更新者 */
	private AdminLoginInfo housingInfoUpdUser;

	/**
	 * コンストラクター<br/>
	 * 物件リクエストのモデル以外からインスタンスを生成出来ない様にコンストラクタを制限する。<br/>
	 * <br/>
	 */
	protected PanaHousing() {
		super();
	}



	/**
	 * 物件インスペクションのリストを取得する。<br/>
	 * <br/>
	 * @return 物件インスペクションのリスト
	 */
	public List<HousingInspection> getHousingInspections() {
		return housingInspections;
	}

	/**
	 * 物件インスペクションのリストを設定する。<br/>
	 * <br/>
	 * @param housingInspections 物件インスペクションのリスト
	 */
	public void setHousingInspections(List<HousingInspection> housingInspections) {
		this.housingInspections = housingInspections;
	}


	/**
	 * リフォーム情報のMapを取得する。<br/>
	 * <br/>
	 * @return リフォーム情報のMap
	 */
	public List<Map<String, Object>> getReforms() {
		return reforms;
	}

	/**
	 * リフォーム情報のMapを設定する。<br/>
	 * <br/>
	 * @param reforms リフォーム情報のMap
	 */
	public void setReforms(List<Map<String, Object>> reforms) {
		this.reforms = reforms;
	}

	/**
	 * 物件基本情報最終更新者名を取得する。<br/>
	 * <br/>
	 * @return 物件基本情報最終更新者名
	 */
	public AdminLoginInfo getHousingInfoUpdUser() {
		return housingInfoUpdUser;
	}

	/**
	 * 物件基本情報最終更新者名を設定する。<br/>
	 * <br/>
	 * @param housingInfoUpdUser 物件基本情報最終更新者名
	 */
	public void setHousingInfoUpdUser(AdminLoginInfo housingInfoUpdUser) {
		this.housingInfoUpdUser = housingInfoUpdUser;
	}
}
