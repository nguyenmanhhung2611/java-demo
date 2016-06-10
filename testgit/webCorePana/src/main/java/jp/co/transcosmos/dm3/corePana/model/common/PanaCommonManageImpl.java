package jp.co.transcosmos.dm3.corePana.model.common;

import java.util.ArrayList;
import java.util.List;

import jp.co.transcosmos.dm3.core.vo.AddressMst;
import jp.co.transcosmos.dm3.core.vo.EquipMst;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.RrMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.core.vo.StationRouteInfo;
import jp.co.transcosmos.dm3.core.vo.ZipMst;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.building.dao.PanaRouteMstListDAO;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 共通情報取得する Model クラス.
 * <p>
 * 共通情報取得する model クラスはこのインターフェースを実装する事。<br/>
 * <p>
 *
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * TRANS		2015.03.30	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスはシングルトンで DI コンテナに定義されるので、スレッドセーフである事。<br/>
 *
 */
public class PanaCommonManageImpl implements PanaCommonManage {
	private static final Log log = LogFactory
			.getLog(PanaCommonManageImpl.class);

	private static String _MESSAGE_ZIP_TO_ADDRESS_1 = "郵便番号を入力してください。";
	private static String _MESSAGE_ZIP_TO_ADDRESS_2 = "郵便番号を確認してください。";

	/** 都道府県マスタ情報 **/
	private DAO<PrefMst> prefMstDAO;
	
	/** 市区町村マスタ情報 **/
	private DAO<AddressMst> addressMstDAO;

	/** 路線マスタ情報 **/
	private DAO<RouteMst> routeMstDAO;

	/** 鉄道会社マスタ情報 **/
	private DAO<RrMst> rrMstDAO;

	/** 駅名マスタ情報 **/
	private DAO<StationMst> stationMstDAO;

	/** 郵便番号マスタ **/
	private DAO<ZipMst> zipMstDAO;

	/** 駅・路線対応情報 */
	private DAO<StationRouteInfo> stationRouteInfoDAO;

	/** 路線マスタ取得 */
	private PanaRouteMstListDAO routeMstListDAO;

	/** 設備情報マスタ情報 **/
	private DAO<EquipMst> equipMstDAO;

	/**
	 * @param stationRouteInfoDAO
	 *            セットする stationRouteInfoDAO
	 */
	public void setStationRouteInfoDAO(DAO<StationRouteInfo> stationRouteInfoDAO) {
		this.stationRouteInfoDAO = stationRouteInfoDAO;
	}

	/**
	 * @param routeMstListDAO
	 *            セットする routeMstListDAO
	 */
	public void setPanaRouteMstListDAO(PanaRouteMstListDAO routeMstListDAO) {
		this.routeMstListDAO = routeMstListDAO;
	}

	/**
	 * @param prefMstDAO
	 *            セットする prefMstDAO
	 */
	public void setPrefMstDAO(DAO<PrefMst> prefMstDAO) {
		this.prefMstDAO = prefMstDAO;
	}

	/**
	 * @param addressMstDAO
	 *            セットする addressMstDAO
	 */
	public void setAddressMstDAO(DAO<AddressMst> addressMstDAO) {
		this.addressMstDAO = addressMstDAO;
	}

	/**
	 * @param routeMstDAO
	 *            セットする routeMstDAO
	 */
	public void setRouteMstDAO(DAO<RouteMst> routeMstDAO) {
		this.routeMstDAO = routeMstDAO;
	}

	/**
	 * @param rrMstDAO
	 *            セットする rrMstDAO
	 */
	public void setRrMstDAO(DAO<RrMst> rrMstDAO) {
		this.rrMstDAO = rrMstDAO;
	}

	/**
	 * @param stationMstDAO
	 *            セットする stationMstDAO
	 */
	public void setStationMstDAO(DAO<StationMst> stationMstDAO) {
		this.stationMstDAO = stationMstDAO;
	}

	/**
	 * @param zipMstDAO
	 *            セットする zipMstDAO
	 */
	public void setZipMstDAO(DAO<ZipMst> zipMstDAO) {
		this.zipMstDAO = zipMstDAO;
	}

	/**
	 * @param equipMstDAO
	 *            セットする equipMstDAO
	 */
	public void setEquipMstDAO(DAO<EquipMst> equipMstDAO) {
		this.equipMstDAO = equipMstDAO;
	}

	/**
	 * 都道府県リストの取得<br/>
	 *
	 * @return 都道府県リスト
	 * @throws Exception
	 */
	public List<PrefMst> getPrefMstList() throws Exception {
		log.debug(">>>PanaCommonManageImpl.getPrefMstList()<<<");
		DAOCriteria paramDAOCriteria = new DAOCriteria();
		paramDAOCriteria.addOrderByClause("sortOrder");
		return this.prefMstDAO.selectByFilter(paramDAOCriteria);
	}

	/**
	 * 都道府県CDにより、市区町村情報リストの取得<br/>
	 *
	 * @param PrefCd
	 *            都道府県CD
	 * @return 市区町村情報リスト
	 * @throws Exception
	 */
	public List<AddressMst> getPrefCdToAddressMstList(String prefCd)
			throws Exception {
		log.debug(">>>PanaCommonManageImpl.getPrefCdToAddressMstList(String prefCd)<<<");
		log.debug(">>>prefCd is : " + prefCd + "<<<");

		DAOCriteria paramDAOCriteria = new DAOCriteria();
		List<AddressMst> addressMstList = new ArrayList<AddressMst>();

		if (null != prefCd) {
			paramDAOCriteria.addWhereClause("prefCd", prefCd);
			paramDAOCriteria.addOrderByClause("prefCd");
			addressMstList = this.addressMstDAO
					.selectByFilter(paramDAOCriteria);
		}

		return addressMstList;
	}

	/**
	 * 都道府県CDにより、沿線情報リストの取得<br/>
	 *
	 * @param PrefCd
	 *            都道府県CD
	 * @return 沿線情報リスト
	 * @throws Exception
	 */
	public List<RouteMst> getPrefCdToRouteMstList(String prefCd)
			throws Exception {
		log.debug(">>>PanaCommonManageImpl.getPrefCdToRouteMstList(String prefCd)<<<");
		log.debug(">>>prefCd is : " + prefCd + "<<<");

		List<RouteMst> routeMstList = new ArrayList<RouteMst>();

		Object[] params = new String[] { prefCd };
		routeMstList = routeMstListDAO.listRouteMst(params);

		return routeMstList;
	}
	public List<RouteMst> getPrefCdToRouteMstList(String prefCd,String rrCd)
			throws Exception {
		log.debug(">>>PanaCommonManageImpl.getPrefCdToRouteMstList(String prefCd)<<<");
		log.debug(">>>prefCd is : " + prefCd + "<<<");

		List<RouteMst> routeMstList = new ArrayList<RouteMst>();

		Object[] params = new String[] { prefCd,rrCd };
		routeMstList = routeMstListDAO.listRouteMst2(params);

		return routeMstList;
	}

	/**
	 * 鉄道会社リストの取得
	 *
	 * @param PrefCd
	 *            都道府県CD
	 * @return 鉄道会社名
	 * @throws Exception
	 */
	public List<RrMst> getPrefCdToRrMstList(String prefCd)
			throws Exception {
		log.debug(">>>PanaCommonManageImpl.getPrefCdToRouteMstList(String prefCd)<<<");
		log.debug(">>>prefCd is : " + prefCd + "<<<");

		List<RrMst> rrMstList = new ArrayList<RrMst>();

		Object[] params = new String[] { prefCd };
		rrMstList = routeMstListDAO.listRrMst(params);

		return rrMstList;
	}

	/**
	 * 沿線CDにより、駅情報リスト取得<br/>
	 *
	 * @param RouteCd
	 *            沿線CD
	 * @return 駅情報リスト
	 * @throws Exception
	 */
	public List<StationMst> getRouteCdToStationMstList(String routeCd)
			throws Exception {
		log.debug(">>>PanaCommonManageImpl.getRouteCdToStationMstList(String routeCd)<<<");
		log.debug(">>>routeCd is : " + routeCd + "<<<");

		DAOCriteria paramDAOCriteria = new DAOCriteria();
		List<StationMst> stationMstList = new ArrayList<StationMst>();// 駅情報リスト
		List<StationRouteInfo> stationRouteInfoList;
		StationMst stationMst;
		StationRouteInfo stationRouteInfo;

		if (null != routeCd) {
			paramDAOCriteria.addWhereClause("routeCd", routeCd);
			paramDAOCriteria.addOrderByClause("sortOrder");

			stationRouteInfoList = this.stationRouteInfoDAO
					.selectByFilter(paramDAOCriteria);

			if (null != stationRouteInfoList && stationRouteInfoList.size() > 0) {
				for (int i = 0; i < stationRouteInfoList.size(); i++) {
					stationRouteInfo = stationRouteInfoList.get(i);

					if (null != stationRouteInfo) {
						stationMst = stationMstDAO.selectByPK(stationRouteInfo
								.getStationCd());

						if (null != stationMst) {
							stationMstList.add(stationMst);
						}
					}
				}
			}

		}

		return stationMstList;
	}

	/**
	 * 都道府県名の取得<br/>
	 *
	 * @param prefCd
	 *            都道府県CD
	 * @return 都道府県名
	 * @throws Exception
	 */
	public String getPrefName(String prefCd) throws Exception {
		log.debug(">>>PanaCommonManageImpl.getPrefName(String prefCd)<<<");
		log.debug(">>>prefCd is : " + prefCd + "<<<");

		String prefName = "";

		if (null != prefCd) {
			PrefMst prefMst = this.prefMstDAO.selectByPK(prefCd);

			if (null != prefMst) {
				prefName = prefMst.getPrefName();
			}
		}

		return prefName;
	}

	/**
	 * 市区町村名の取得<br/>
	 *
	 * @param addressCd
	 *            市区町村CD
	 * @return 市区町村名
	 * @throws Exception
	 */
	public String getAddressName(String addressCd) throws Exception {
		log.debug(">>>getAddressName(String addressCd)<<<");
		log.debug(">>>addressCd is : " + addressCd + "<<<");

		String addressName = "";

		if (null != addressCd) {
			AddressMst addressMst = this.addressMstDAO.selectByPK(addressCd);

			if (null != addressMst) {
				addressName = addressMst.getAddressName();
			}
		}

		return addressName;
	}

	/**
	 * 路線名の取得
	 *
	 * @param routeCd
	 *            路線CD
	 * @return 路線名
	 * @throws Exception
	 */
	public String getRouteName(String routeCd) throws Exception {
		log.debug(">>>getRouteName(String routeCd)<<<");
		log.debug(">>>routeCd is : " + routeCd + "<<<");

		String routeName = "";

		if (null != routeCd) {
			RouteMst routeMst = this.routeMstDAO.selectByPK(routeCd);

			if (null != routeMst) {
				routeName = routeMst.getRouteName();
			}
		}

		return routeName;
	}

	/**
	 * 路線名・カッコ付の取得
	 *
	 * @param routeCd
	 *            路線CD
	 * @return 路線名
	 * @throws Exception
	 */
	public String getRouteNameFull(String routeCd) throws Exception {
		log.debug(">>>getRouteNameFull(String routeCd)<<<");
		log.debug(">>>routeCd is : " + routeCd + "<<<");

		String routeName = "";

		if (null != routeCd) {
			RouteMst routeMst = this.routeMstDAO.selectByPK(routeCd);

			if (null != routeMst) {
				routeName = routeMst.getRouteNameFull();
			}
		}

		return routeName;
	}

	/**
	 * 路線名・鉄道会社付の取得
	 *
	 * @param routeCd
	 *            路線CD
	 * @return 路線名
	 * @throws Exception
	 */
	public String getRouteNameRr(String routeCd) throws Exception {
		log.debug(">>>getRouteNameRr(String routeCd)<<<");
		log.debug(">>>routeCd is : " + routeCd + "<<<");

		String routeName = "";

		if (null != routeCd) {
			RouteMst routeMst = this.routeMstDAO.selectByPK(routeCd);

			if (null != routeMst) {
				routeName = routeMst.getRouteNameRr();
			}
		}

		return routeName;
	}

	/**
	 * 鉄道会社名+路線名・カッコ付取得
	 *
	 * @param routeCd
	 *            路線CD
	 * @return 路線名
	 * @throws Exception
	 */
	public String getRrNameRouteFull(String routeCd) throws Exception {
		log.debug(">>>getRrNameRouteFull(String routeCd)<<<");
		log.debug(">>>routeCd is : " + routeCd + "<<<");

		String rrCd = "";
		String rrName="";
		String routeName = "";
		if (null != routeCd) {
			RouteMst routeMst = this.routeMstDAO.selectByPK(routeCd);

			if (null != routeMst) {
				rrCd = routeMst.getRrCd();
				routeName = routeMst.getRouteNameFull();

				RrMst rrMst = this.rrMstDAO.selectByPK(rrCd);
				if (null != rrMst) {
					rrName = rrMst.getRrName();
				}
			}
		}

		return rrName+routeName;
	}

	/**
	 * 駅名の取得
	 *
	 * @param stationCd
	 *            駅CD
	 * @return 駅名
	 * @throws Exception
	 */
	public String getStationName(String stationCd) throws Exception {
		log.debug(">>>getStationName(String stationCd)<<<");
		log.debug(">>>stationCd is : " + stationCd + "<<<");

		String stationName = "";

		if (null != stationCd) {
			StationMst stationMst = this.stationMstDAO.selectByPK(stationCd);

			if (null != stationMst) {
				stationName = stationMst.getStationName();
			}
		}

		return stationName;
	}

	/**
	 * 駅名・カッコの取得
	 *
	 * @param stationCd
	 *            駅CD
	 * @return 駅名
	 * @throws Exception
	 */
	public String getStationNameFull(String stationCd) throws Exception {
		log.debug(">>>getStationNameFull(String stationCd)<<<");
		log.debug(">>>stationCd is : " + stationCd + "<<<");

		String stationName = "";

		if (null != stationCd) {
			StationMst stationMst = this.stationMstDAO.selectByPK(stationCd);

			if (null != stationMst) {
				stationName = stationMst.getStationNameFull();
			}
		}

		return stationName;
	}

	/**
	 * 郵便番号により、都道府県、市区町村取得
	 *
	 * @param zip
	 *            郵便番号
	 * @return 0:都道府県CD+市区町村CD<br/>
	 *         1:郵便番号を入力してください。<br/>
	 *         2:郵便番号を確認してください。<br/>
	 * @throws Exception
	 */
	public String[] getZipToAddress(String zip) throws Exception {
		log.debug(">>>getZipToAddress(String zip)<<<");
		log.debug(">>>zip is : " + zip + "<<<");

		String[] message = new String[3];

		if (!StringValidateUtil.isEmpty(zip)) {
			ZipMst zipMst = this.zipMstDAO.selectByPK(zip);

			if (null != zipMst) {

				if (!StringValidateUtil.isEmpty(zipMst.getPrefCd())
						&& !StringValidateUtil.isEmpty(zipMst.getAddressCd())) {
					PrefMst prefMst = prefMstDAO.selectByPK(zipMst.getPrefCd());
					AddressMst addressMst = addressMstDAO.selectByPK(zipMst
							.getAddressCd());

					if (!StringValidateUtil.isEmpty(prefMst.getPrefName())
							&& !StringValidateUtil.isEmpty(addressMst
									.getAddressName())) {
						message[0] = "0";
						message[1] = zipMst.getPrefCd();
						message[2] = zipMst.getAddressCd();
					} else {
						message[0] = "2";
						message[1] = _MESSAGE_ZIP_TO_ADDRESS_2;
						message[2] = _MESSAGE_ZIP_TO_ADDRESS_2;
					}
				} else {
					message[0] = "2";
					message[1] = _MESSAGE_ZIP_TO_ADDRESS_2;
					message[2] = _MESSAGE_ZIP_TO_ADDRESS_2;
				}
			} else {
				message[0] = "2";
				message[1] = _MESSAGE_ZIP_TO_ADDRESS_2;
				message[2] = _MESSAGE_ZIP_TO_ADDRESS_2;
			}
		} else {
			message[0] = "1";
			message[1] = _MESSAGE_ZIP_TO_ADDRESS_1;
			message[2] = _MESSAGE_ZIP_TO_ADDRESS_1;
		}

		return message;
	}

	/**
	 * 設備情報リストの取得<br/>
	 *
	 * @return 設備情報リスト
	 * @throws Exception
	 */
	public List<EquipMst> getEquipMstList() throws Exception {
		log.debug(">>>PanaCommonManageImpl.getEquipMstList()<<<");
		DAOCriteria paramDAOCriteria = new DAOCriteria();
		paramDAOCriteria.addOrderByClause("sortOrder");
		return this.equipMstDAO.selectByFilter(paramDAOCriteria);
	}
	
}
