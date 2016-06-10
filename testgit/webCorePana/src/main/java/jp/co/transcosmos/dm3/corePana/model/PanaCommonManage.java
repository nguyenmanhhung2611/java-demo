package jp.co.transcosmos.dm3.corePana.model;

import java.util.List;

import jp.co.transcosmos.dm3.core.vo.AddressMst;
import jp.co.transcosmos.dm3.core.vo.EquipMst;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.RrMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;

/**
 * 共通情報取得する Model クラス用インターフェース.
 * <p>
 * 共通情報取得する model クラスはこのインターフェースを実装する事。<br/>
 * <p>
 *
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * TRANS		2015.03.30	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスはシングルトンで DI コンテナに定義されるので、スレッドセーフである事。<br/>
 *
 */
public interface PanaCommonManage {

	/**
	 * 都道府県リストの取得<br/>
	 *
	 * @return 都道府県リスト
	 * @throws Exception
	 */
	public List<PrefMst> getPrefMstList() throws Exception;

	/**
	 * 都道府県CDにより、市区町村情報リストの取得<br/>
	 *
	 * @param PrefCd
	 *            都道府県CD
	 * @return 市区町村情報リスト
	 * @throws Exception
	 */
	public List<AddressMst> getPrefCdToAddressMstList(String prefCd)
			throws Exception;

	/**
	 * 都道府県CDにより、沿線情報リストの取得<br/>
	 *
	 * @param PrefCd
	 *            都道府県CD
	 * @return 沿線情報リスト
	 * @throws Exception
	 */
	public List<RouteMst> getPrefCdToRouteMstList(String prefCd)
			throws Exception;

	/**
	 * 沿線CDにより、駅情報リスト取得<br/>
	 *
	 * @param RouteCd
	 *            沿線CD
	 * @return 駅情報リスト
	 * @throws Exception
	 */
	public List<StationMst> getRouteCdToStationMstList(String routeCd)
			throws Exception;

	public List<RouteMst> getPrefCdToRouteMstList(String prefCd,String rrCd)
			throws Exception;

	/**
	 * 鉄道会社リストの取得
	 *
	 * @param routeCd
	 *            路線CD
	 * @return 路線名
	 * @throws Exception
	 */
	public List<RrMst> getPrefCdToRrMstList(String prefCd)
			throws Exception;
	/**
	 * 都道府県名の取得<br/>
	 *
	 * @param prefCd
	 *            都道府県CD
	 * @return 都道府県名
	 * @throws Exception
	 */
	public String getPrefName(String prefCd) throws Exception;

	/**
	 * 市区町村名の取得<br/>
	 *
	 * @param addressCd
	 *            市区町村CD
	 * @return 市区町村名
	 * @throws Exception
	 */
	public String getAddressName(String addressCd) throws Exception;

	/**
	 * 路線名の取得
	 *
	 * @param routeCd
	 *            路線CD
	 * @return 路線名
	 * @throws Exception
	 */
	public String getRouteName(String routeCd) throws Exception;

	/**
	 * 路線名・カッコ付の取得
	 *
	 * @param routeCd
	 *            路線CD
	 * @return 路線名
	 * @throws Exception
	 */
	public String getRouteNameFull(String routeCd) throws Exception;

	/**
	 * 鉄道会社名+路線名・カッコ付取得
	 *
	 * @param routeCd
	 *            路線CD
	 * @return 路線名
	 * @throws Exception
	 */
	public String getRrNameRouteFull(String routeCd) throws Exception;

	/**
	 * 駅名の取得
	 *
	 * @param stationCd
	 *            駅CD
	 * @return 駅名
	 * @throws Exception
	 */
	public String getStationName(String stationCd) throws Exception;

	/**
	 * 郵便番号により、都道府県、市区町村取得
	 *
	 * @param zip
	 *            郵便番号
	 * @return 0:都道府県CD+市区町村CD<br/>
	 *         1:郵便番号は入力してください。<br/>
	 *         2:該当郵便番号の住所はありません。
	 * @throws Exception
	 */
	public String[] getZipToAddress(String zip) throws Exception;

	/**
	 * 設備情報リストの取得<br/>
	 *
	 * @return 設備情報リスト
	 * @throws Exception
	 */
	public List<EquipMst> getEquipMstList() throws Exception;

	/**
	 * 路線名・鉄道会社付の取得
	 *
	 * @param routeCd
	 *            路線CD
	 * @return 路線名
	 * @throws Exception
	 */
	public String getRouteNameRr(String routeCd) throws Exception;
	
}
