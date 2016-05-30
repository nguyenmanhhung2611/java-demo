/**
 * 
 */
package jp.co.transcosmos.dm3.core.model.housingRequest;

import java.util.ArrayList;
import java.util.List;

import jp.co.transcosmos.dm3.adminCore.request.form.HousingRequestForm;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.HousingManage;
import jp.co.transcosmos.dm3.core.model.HousingRequestManage;
import jp.co.transcosmos.dm3.core.model.exception.MaxEntryOverException;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingFormFactory;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingSearchForm;
import jp.co.transcosmos.dm3.core.model.housingRequest.form.RequestSearchForm;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.AddressMst;
import jp.co.transcosmos.dm3.core.vo.HousingReqKind;
import jp.co.transcosmos.dm3.core.vo.HousingReqLayout;
import jp.co.transcosmos.dm3.core.vo.HousingReqPart;
import jp.co.transcosmos.dm3.core.vo.HousingReqRoute;
import jp.co.transcosmos.dm3.core.vo.HousingReqStation;
import jp.co.transcosmos.dm3.core.vo.HousingRequestArea;
import jp.co.transcosmos.dm3.core.vo.HousingRequestInfo;
import jp.co.transcosmos.dm3.core.vo.MemberInfo;
import jp.co.transcosmos.dm3.core.vo.PartSrchMst;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.dao.OrCriteria;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 物件リクエスト情報用 Model クラス.
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * T.Nakamura	2015.03.30	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスはシングルトンで DI コンテナに定義されるので、スレッドセーフである事。<br/>
 * 
 */
public class HousingRequestManageImpl implements HousingRequestManage {

	private static final Log log = LogFactory.getLog(HousingRequestManageImpl.class);

	/** VO のインスタンスを生成する場合のファクトリー */
	protected ValueObjectFactory valueObjectFactory;
	/** 共通設定情報管理クラス */
	protected CommonParameters commonParameters;

	/** 物件情報Model（物件リクエストに該当する物件情報取得に使用） */
	protected HousingManage housingManager;
	/** 物件情報FormFactory（物件リクエストに該当する物件情報取得に使用） */
	protected HousingFormFactory housingFormFactory;

	/** マイページ会員情報DAO */
	protected DAO<MemberInfo> memberInfoDAO;

	/** 物件リクエスト情報DAO */
	protected DAO<HousingRequestInfo> housingRequestInfoDAO;
	/** 物件リクエストエリア情報DAO */
	protected DAO<HousingRequestArea> housingRequestAreaDAO;
	/** 物件リクエスト路線情報DAO */
	protected DAO<HousingReqRoute> housingReqRouteDAO;
	/** 物件リクエスト最寄り駅情報DAO */
	protected DAO<HousingReqStation> housingReqStationDAO;
	/** 物件リクエスト物件種類情報DAO */
	protected DAO<HousingReqKind> housingReqKindDAO;
	/** 物件リクエスト間取情報DAO */
	protected DAO<HousingReqLayout> housingReqLayoutDAO;
	/** 物件リクエストこだわり条件情報DAO */
	protected DAO<HousingReqPart> housingReqPartDAO;

	/** 都道府県マスタDAO */
	protected DAO<PrefMst> prefMstDAO;
	/** 市区町村マスタDAO */
	protected DAO<AddressMst> addressMstDAO;
	/** 路線マスタDAO */
	protected DAO<RouteMst> routeMstDAO;
	/** 駅名マスタDAO */
	protected DAO<StationMst> stationMstDAO;
	/** 駅・路線対応のマスタ取得用DAO */
	protected DAO<JoinResult> stationRouteRelationDAO;
	/** こだわり条件マスタDAO */
	protected DAO<PartSrchMst> partSrchMstDAO;

	// DI Setter START ----------------------------------------
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}

	public void setCommonParameters(CommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	public void setHousingManager(HousingManage housingManager) {
		this.housingManager = housingManager;
	}

	public void setHousingFormFactory(HousingFormFactory housingFormFactory) {
		this.housingFormFactory = housingFormFactory;
	}

	public void setMemberInfoDAO(DAO<MemberInfo> memberInfoDAO) {
		this.memberInfoDAO = memberInfoDAO;
	}

	public void setHousingRequestInfoDAO(DAO<HousingRequestInfo> housingRequestInfoDAO) {
		this.housingRequestInfoDAO = housingRequestInfoDAO;
	}

	public void setHousingRequestAreaDAO(DAO<HousingRequestArea> housingRequestAreaDAO) {
		this.housingRequestAreaDAO = housingRequestAreaDAO;
	}

	public void setHousingReqRouteDAO(DAO<HousingReqRoute> housingReqRouteDAO) {
		this.housingReqRouteDAO = housingReqRouteDAO;
	}

	public void setHousingReqStationDAO(DAO<HousingReqStation> housingReqStationDAO) {
		this.housingReqStationDAO = housingReqStationDAO;
	}

	public void setHousingReqKindDAO(DAO<HousingReqKind> housingReqKindDAO) {
		this.housingReqKindDAO = housingReqKindDAO;
	}

	public void setHousingReqLayoutDAO(DAO<HousingReqLayout> housingReqLayoutDAO) {
		this.housingReqLayoutDAO = housingReqLayoutDAO;
	}

	public void setHousingReqPartDAO(DAO<HousingReqPart> housingReqPartDAO) {
		this.housingReqPartDAO = housingReqPartDAO;
	}

	public void setPrefMstDAO(DAO<PrefMst> prefMstDAO) {
		this.prefMstDAO = prefMstDAO;
	}

	public void setAddressMstDAO(DAO<AddressMst> addressMstDAO) {
		this.addressMstDAO = addressMstDAO;
	}

	public void setRouteMstDAO(DAO<RouteMst> routeMstDAO) {
		this.routeMstDAO = routeMstDAO;
	}

	public void setStationMstDAO(DAO<StationMst> stationMstDAO) {
		this.stationMstDAO = stationMstDAO;
	}

	public void setStationRouteRelationDAO(DAO<JoinResult> stationRouteRelationDAO) {
		this.stationRouteRelationDAO = stationRouteRelationDAO;
	}

	public void setPartSrchMstDAO(DAO<PartSrchMst> partSrchMstDAO) {
		this.partSrchMstDAO = partSrchMstDAO;
	}

	// DI Setter END ----------------------------------------

	/**
	 * 指定された Form の値で物件リクエスト情報を登録する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * 物件リクエストID は自動採番されるので、HousingRequestForm の housingRequestId
	 * プロパティには値を設定しない事。<br/>
	 * 登録件数が上限値に達する場合は登録せずにエラーとして復帰する事。<br/>
	 * <br/>
	 * @param userId マイページユーザーID
	 * @param inputForm 入力された物件リクエスト情報
	 * 
	 * @return 採番された物件リクエストID
	 * 
  	 * @exception Exception 実装クラスによりスローされる任意の例外
  	 * @exception MaxEntryOverException 最大登録数オーバー
	 */
	@Override
	public String addRequest(String userId, HousingRequestForm inputForm) throws Exception, MaxEntryOverException {

		// パラメタチェック
		if (StringValidateUtil.isEmpty(userId)) {
			log.warn("userId Is Null or Empty [userId = " + userId + "]");
			return null;
		}

		// 登録対象情報の存在チェック
		MemberInfo existMenberInfo = this.memberInfoDAO.selectByPK(userId);
		if (existMenberInfo == null) {
			log.warn("Target MemberInfo Is Null [userId = " + userId + "]");
			return null;
		}

		// 登録上限件数チェック
		int nowCnt = searchRequestCnt(userId);
		if (!(nowCnt < commonParameters.getMaxHousingRequestCnt())) {
			log.warn("housingRequest max entry over [maxHousingRequestCnt = " + commonParameters.getMaxHousingRequestCnt() + "]");
			throw new MaxEntryOverException();
		}

		// 物件リクエスト情報の登録
		HousingRequestInfo housingRequestInfo = buildHousingRequestInfo();
		inputForm.copyToHousingRequestInfo(userId, housingRequestInfo);
		housingRequestInfo.setInsDate(housingRequestInfo.getUpdDate());
		housingRequestInfo.setInsUserId(housingRequestInfo.getUpdUserId());
		this.housingRequestInfoDAO.insert(new HousingRequestInfo[] { housingRequestInfo });

		// 各パラメタが存在する場合に対応するテーブルにレコードを登録

		// 物件リクエストエリア情報
		insertHousingRequestArea(housingRequestInfo.getHousingRequestId(), inputForm.getPrefCd(), inputForm.getAddressCd());

		// 物件リクエスト最寄駅情報
		insertHousingReqRouteStation(housingRequestInfo.getHousingRequestId(), inputForm.getRouteCd(), inputForm.getStationCd());

		// 物件リクエスト物件種類情報
		insertHousingReqKind(housingRequestInfo.getHousingRequestId(), inputForm.getHousingKindCd());

		// 物件リクエスト間取情報
		insertHousingReqLayout(housingRequestInfo.getHousingRequestId(), inputForm.getLayoutCd());

		// 物件リクエストこだわり条件情報
		insertHousingReqPartSrch(housingRequestInfo.getHousingRequestId(), inputForm.getPartSrchCd());

		return housingRequestInfo.getHousingRequestId();
	}

	/**
	 * 指定された Form の値で物件リクエスト情報を更新する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * 更新に使用するキーは、HousingRequestForm　の housingRequestId と、引数で渡された
	 * userId を使用する事。  （userId はリクエストパラメータで取得しない事。）<br/>
	 * <br/>
	 * @param userId マイページユーザーID
	 * @param inputForm 入力された物件リクエスト情報（更新対象の主キー値を含む）
	 * 
  	 * @exception Exception 実装クラスによりスローされる任意の例外
  	 * @exception NotFoundException 更新対象なし
	 */
	@Override
	public void updateRequest(String userId, HousingRequestForm inputForm) throws Exception, NotFoundException {

		if (StringValidateUtil.isEmpty(userId)) {
			log.warn("userId Is Null or Empty [userId = " + userId + "]");
			return;
		}
		if (StringValidateUtil.isEmpty(inputForm.getHousingRequestId())) {
			log.warn("housingRequestId Is Null or Empty [housingRequestId = " + inputForm.getHousingRequestId() + "]");
			return;
		}

		List<HousingRequest> requests = searchRequest(userId, inputForm.getHousingRequestId());
		if (requests.size() != 1) {
			log.warn("Selected HousingRequestInfo Is Empty or Not Unique [userId = " + userId + ", housingRequestId = " + inputForm.getHousingRequestId() + "]");
			return ;
		}

		HousingRequest request = requests.get(0);

		// 物件リクエスト情報
		HousingRequestInfo housingRequestInfo = request.getHousingRequestInfo();
		inputForm.copyToHousingRequestInfo(userId, housingRequestInfo);
		this.housingRequestInfoDAO.update(new HousingRequestInfo[] { housingRequestInfo });

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", housingRequestInfo.getHousingRequestId());

		// 物件リクエストエリア情報
		this.housingRequestAreaDAO.deleteByFilter(criteria);
		insertHousingRequestArea(housingRequestInfo.getHousingRequestId(), inputForm.getPrefCd(), inputForm.getAddressCd());

		// 物件リクエスト路線情報
		// 物件リクエスト最寄駅情報
		this.housingReqRouteDAO.deleteByFilter(criteria);
		this.housingReqStationDAO.deleteByFilter(criteria);
		insertHousingReqRouteStation(housingRequestInfo.getHousingRequestId(), inputForm.getRouteCd(), inputForm.getStationCd());

		// 物件リクエスト物件種類情報
		this.housingReqKindDAO.deleteByFilter(criteria);
		insertHousingReqKind(housingRequestInfo.getHousingRequestId(), inputForm.getHousingKindCd());

		// 物件リクエスト間取り情報
		this.housingReqLayoutDAO.deleteByFilter(criteria);
		insertHousingReqLayout(housingRequestInfo.getHousingRequestId(), inputForm.getLayoutCd());

		// 物件リクエストこだわり条件情報
		this.housingReqPartDAO.deleteByFilter(criteria);
		insertHousingReqPartSrch(housingRequestInfo.getHousingRequestId(), inputForm.getPartSrchCd());

	}

	/**
	 * 物件リクエストエリア情報登録処理<br/>
	 * <br/>
	 * 
	 * @param housingRequestId
	 * @param csPrefCd
	 * @param csAddressCd
	 */
	protected void insertHousingRequestArea(String housingRequestId, String csPrefCd, String csAddressCd) {

		// 物件リクエストエリア情報
		if (!StringValidateUtil.isEmpty(csPrefCd)) {

			// 対象とする都道府県CDはマスタに存在すること
			String[] prefCds = csPrefCd.split(",");
			List<PrefMst> prefMsts = this.prefMstDAO.selectByPK(prefCds);

			List<AddressMst> addressMsts;
			if (!StringValidateUtil.isEmpty(csAddressCd)) {

				// 対象とする市区町村CDはマスタに存在すること
				String[] addressCds = csAddressCd.split(",");
				addressMsts = this.addressMstDAO.selectByPK(addressCds);
			} else {
				addressMsts = new ArrayList<AddressMst>();
			}

			if (prefMsts.size() > 0) {
				List<HousingRequestArea> housingRequestAreas = new ArrayList<HousingRequestArea>();

				for (PrefMst prefMst : prefMsts) {
					// 都道府県マスタが取得できている場合

					HousingRequestArea housingRequestArea;
					boolean isExistAddress = false;

					if (addressMsts.size() > 0) {
						// 市区町村マスタが取得できている場合

						for (AddressMst addressMst : addressMsts) {
							if (prefMst.getPrefCd().equals(addressMst.getPrefCd())) {
								housingRequestArea = buildHousingRequestArea();
								housingRequestArea.setHousingRequestId(housingRequestId);
								housingRequestArea.setAddressCd(addressMst.getAddressCd());
								housingRequestArea.setPrefCd(addressMst.getPrefCd());
								housingRequestAreas.add(housingRequestArea);

								// 市区町村CDのレコード登録有無
								isExistAddress = true;
							}
						}
					}

					if (!isExistAddress) {
						// 市区町村CDを含めてた登録が無い場合、都道府県CDのみで登録する
						housingRequestArea = buildHousingRequestArea();
						housingRequestArea.setHousingRequestId(housingRequestId);
						housingRequestArea.setPrefCd(prefMst.getPrefCd());
						housingRequestAreas.add(housingRequestArea);
					}

				}

				this.housingRequestAreaDAO.insert(housingRequestAreas.toArray(new HousingRequestArea[housingRequestAreas.size()]));
			}
		}
	}

	protected void insertHousingReqRouteStation(String housingRequestId, String routeCd, String stationCd) {

		if (!StringValidateUtil.isEmpty(stationCd) && !StringValidateUtil.isEmpty(routeCd)) {

			// 対象とする駅CDはマスタに存在すること
			String[] stationCds = stationCd.split(",");
			List<StationMst> stationMsts = this.stationMstDAO.selectByPK(stationCds);

			if (stationMsts.size() > 0) {
				DAOCriteria criteria = new DAOCriteria();
				OrCriteria orCriteria = new OrCriteria();

				List<HousingReqStation> housingReqStations = new ArrayList<HousingReqStation>();
				for (StationMst stationMst : stationMsts) {
					HousingReqStation housingReqStation = buildHousingReqStation();
					housingReqStation.setHousingRequestId(housingRequestId);
					housingReqStation.setStationCd(stationMst.getStationCd());
					housingReqStations.add(housingReqStation);

					// 路線情報の検索条件となる駅CD
					orCriteria.addWhereClause("stationMst", "stationCd", stationMst.getStationCd(), DAOCriteria.EQUALS, false);
				}

				criteria.addSubCriteria(orCriteria);
				orCriteria = new OrCriteria();

				// 対象とする路線CDはマスタに存在すること
				String[] routeCds = routeCd.split(",");
				List<RouteMst> routeMsts = this.routeMstDAO.selectByPK(routeCds);

				for (RouteMst routeMst : routeMsts) {
					orCriteria.addWhereClause("routeMst", "routeCd", routeMst.getRouteCd(), DAOCriteria.EQUALS, false);
				}

				criteria.addSubCriteria(orCriteria);
				criteria.addOrderByClause("routeMst","routeCd",true);

				// 駅マスタと関連付けて対象を取得
				List<JoinResult> results = this.stationRouteRelationDAO.selectByFilter(criteria);

				if (results.size() > 0) {
					List<HousingReqRoute> housingReqRoutes = new ArrayList<HousingReqRoute>();
					String tempRouteCd = "";
					for (JoinResult result : results) {
						RouteMst routeMst = (RouteMst) result.getItems().get("routeMst");

						// 重複チェック
						if (tempRouteCd.equals(routeMst.getRouteCd())) {
							continue;
						}
						tempRouteCd = routeMst.getRouteCd();

						HousingReqRoute housingReqRoute = buildHousingReqRoute();
						housingReqRoute.setHousingRequestId(housingRequestId);
						housingReqRoute.setRouteCd(routeMst.getRouteCd());
						housingReqRoutes.add(housingReqRoute);
					}

					this.housingReqStationDAO.insert(housingReqStations.toArray(new HousingReqStation[housingReqStations.size()]));
					this.housingReqRouteDAO.insert(housingReqRoutes.toArray(new HousingReqRoute[housingReqRoutes.size()]));
				}
			}

		} else if (!StringValidateUtil.isEmpty(routeCd)) {
			// 物件リクエスト路線情報

			// 対象とする路線CDはマスタに存在すること
			String[] routeCds = routeCd.split(",");
			List<RouteMst> routeMsts = this.routeMstDAO.selectByPK(routeCds);

			if (routeMsts.size() > 0) {
				List<HousingReqRoute> housingReqRoutes = new ArrayList<HousingReqRoute>();
				for(RouteMst routeMst : routeMsts) {
					HousingReqRoute housingReqRoute = buildHousingReqRoute();
					housingReqRoute.setHousingRequestId(housingRequestId);
					housingReqRoute.setRouteCd(routeMst.getRouteCd());
					housingReqRoutes.add(housingReqRoute);
				}

				this.housingReqRouteDAO.insert(housingReqRoutes.toArray(new HousingReqRoute[housingReqRoutes.size()]));
			}
		}
	}

	protected void insertHousingReqKind(String housingRequestId, String csHhousingKindCd) {

		if (!StringValidateUtil.isEmpty(csHhousingKindCd)) {

			String[] kindCds = csHhousingKindCd.split(",");

			List<HousingReqKind> housingReqKinds = new ArrayList<HousingReqKind>();
			for (String kindCd : kindCds) {
				HousingReqKind housingReqKind = buildHousingReqKind();
				housingReqKind.setHousingRequestId(housingRequestId);
				housingReqKind.setHousingKindCd(kindCd);
				housingReqKinds.add(housingReqKind);
			}

			this.housingReqKindDAO.insert(housingReqKinds.toArray(new HousingReqKind[housingReqKinds.size()]));
		}
	}

	protected void insertHousingReqLayout(String housingRequestId, String csLayoutCd) {

		if (!StringValidateUtil.isEmpty(csLayoutCd)) {

			String[] layoutCds = csLayoutCd.split(",");

			List<HousingReqLayout> housingReqLayouts = new ArrayList<HousingReqLayout>();
			for (String layoutCd : layoutCds) {
				HousingReqLayout housingReqLayout = buildHousingReqLayout();
				housingReqLayout.setHousingRequestId(housingRequestId);
				housingReqLayout.setLayoutCd(layoutCd);
				housingReqLayouts.add(housingReqLayout);
			}

			this.housingReqLayoutDAO.insert(housingReqLayouts.toArray(new HousingReqLayout[housingReqLayouts.size()]));
		}
	}

	protected void insertHousingReqPartSrch(String housingRequestId, String csPartSrchCd) {

		if (!StringValidateUtil.isEmpty(csPartSrchCd)) {

			// 対象とするこだわり条件CDはマスタに存在すること
			String[] partSrchCds = csPartSrchCd.split(",");
			List<PartSrchMst> partSrchMsts = this.partSrchMstDAO.selectByPK(partSrchCds);

			if (partSrchMsts.size() > 0) {
				List<HousingReqPart> housingReqParts = new ArrayList<HousingReqPart>();
				for (PartSrchMst partSrchMst : partSrchMsts) {
					HousingReqPart housingReqPart = buildHousingReqPart();
					housingReqPart.setHousingRequestId(housingRequestId);
					housingReqPart.setPartSrchCd(partSrchMst.getPartSrchCd());
					housingReqParts.add(housingReqPart);
				}

				housingReqPartDAO.insert(housingReqParts.toArray(new HousingReqPart[housingReqParts.size()]));
			}
		}
	}

	/**
	 * 指定された Form の値で物件リクエスト情報を削除する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * 削除に使用するキーは、HousingRequestForm　の housingRequestId と、引数で渡された
	 * userId を使用する事。  （userId はリクエストパラメータで取得しない事。）<br/>
	 * <br/>
	 * @param userId マイページユーザーID
	 * @param inputForm 入力された物件リクエスト情報（削除対象の主キー値を含む）
	 * 
	 * 
  	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	@Override
	public void delRequest(String userId, HousingRequestForm inputForm) throws Exception {

		if (StringValidateUtil.isEmpty(userId)) {
			log.warn("userId Is Null or Empty [userId = " + userId + "]");
			return;
		}
		if (StringValidateUtil.isEmpty(inputForm.getHousingRequestId())) {
			log.warn("housingRequestId Is Null or Empty [housingRequestId = " + inputForm.getHousingRequestId() + "]");
			return;
		}

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", inputForm.getHousingRequestId());

		// 物件リクエストエリア情報
		this.housingRequestAreaDAO.deleteByFilter(criteria);
		// 物件リクエスト路線情報
		this.housingReqRouteDAO.deleteByFilter(criteria);
		// 物件リクエスト最寄り駅情報
		this.housingReqStationDAO.deleteByFilter(criteria);
		// 物件リクエスト物件種類情報
		this.housingReqKindDAO.deleteByFilter(criteria);
		// 物件リクエスト間取情報
		this.housingReqLayoutDAO.deleteByFilter(criteria);
		// 物件リクエストこだわり条件情報
		this.housingReqPartDAO.deleteByFilter(criteria);

		criteria.addWhereClause("userId", userId);
		// 物件リクエスト情報
		this.housingRequestInfoDAO.deleteByFilter(criteria);

	}

	/**
	 * 指定されたユーザーID に該当する、物件リクエスト（検索条件）の一覧を取得する。<br/>
	 * <br/>
	 * 
	 * @param userId マイページユーザーID
	 * @return 取得結果
	 * @exception Exception
	 */
	@Override
	public List<HousingRequest> searchRequest(String userId) throws Exception {
		return searchRequest(userId, null);
	}

	/**
	 * 指定されたユーザーID、物件リクエストID に該当する、物件リクエスト（検索条件）の一覧を取得する。<br/>
	 * <br/>
	 * 
	 * @param userId マイページユーザーID
	 * @param housingRequestId 物件リクエストID
	 * @return 取得結果
	 * @throws Exception
	 */
	protected List<HousingRequest> searchRequest(String userId, String housingRequestId) throws Exception {

		List<HousingRequest> results = new ArrayList<HousingRequest>();

		if (StringValidateUtil.isEmpty(userId)) {
			log.warn("userId Is Null or Empty [userId = " + userId + "]");
			return results;
		}

		// 物件リクエスト情報を検索
		DAOCriteria criteria = createSearchRequestCriteria(userId, housingRequestId);
		List<HousingRequestInfo> housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria);

		if (housingRequestInfos == null || housingRequestInfos.isEmpty()) {
			log.warn("Selected HousingRequestInfo Is Null [userId = " + userId + "]");
			return results;
		}

		for (HousingRequestInfo housingRequestInfo : housingRequestInfos) {
			// 取得した物件リクエスト情報の物件リクエストIDから関連するレコードを検索

			HousingRequest housingRequest = createHousingRequest();
			housingRequest.setHousingRequestInfo(housingRequestInfo);

			criteria = new DAOCriteria();
			criteria.addWhereClause("housingRequestId", housingRequestInfo.getHousingRequestId());

			// 物件リクエストエリア情報
			housingRequest.setHousingRequestAreas(housingRequestAreaDAO.selectByFilter(criteria));
			// 物件リクエスト路線情報
			housingRequest.setHousingReqRoutes(housingReqRouteDAO.selectByFilter(criteria));
			// 物件リクエスト最寄り駅情報
			housingRequest.setHousingReqStations(housingReqStationDAO.selectByFilter(criteria));
			// 物件リクエスト物件種類情報
			housingRequest.setHousingReqKind(housingReqKindDAO.selectByFilter(criteria));
			// 物件リクエスト間取情報
			housingRequest.setHousingReqLayouts(housingReqLayoutDAO.selectByFilter(criteria));
			// 物件リクエストこだわり条件情報
			housingRequest.setHousingReqPart(housingReqPartDAO.selectByFilter(criteria));

			results.add(housingRequest);
		}

		return results;
	}

	/**
	 * 物件リクエストを検索するCriteriaを生成する<br/>
	 * 並び順は最終更新日の降順とする<br/>
	 * <br/>
	 * 
	 * @param userId マイページユーザーID
	 * @param housingRequestId 物件リクエストID
	 * @return DAOCriteria
	 */
	protected DAOCriteria createSearchRequestCriteria(String userId, String housingRequestId) {

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("userId", userId);
		if (!StringValidateUtil.isEmpty(housingRequestId)) {
			criteria.addWhereClause("housingRequestId", housingRequestId);
		}
		criteria.addOrderByClause("updDate", false);

		return criteria;

	}

	/**
	 * 指定されたユーザーID が所持する、物件リクエスト（検索条件）の数を取得する。<br/>
	 * <br/>
	 * @param userId マイページユーザーID
	 * 
	 * @return 該当件数
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	@Override
	public int searchRequestCnt(String userId) throws Exception {

		if (StringValidateUtil.isEmpty(userId)) {
			log.warn("userId Is Null or Empty [userId = " + userId + "]");
			return 0;
		}

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("userId", userId);
		List<HousingRequestInfo> housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria);

		return housingRequestInfos.size();
	}

	/**
	 * 指定された検索条件（リクエストID）に該当する物件の情報を取得する。<br/>
	 * <br/>
	 * @param userId マイページユーザーID
	 * @param requestId リクエストID
	 * 
	 * @return 該当件数
	 * 
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	@Override
	public int searchHousing(String userId, RequestSearchForm searchForm) throws Exception {

		if (StringValidateUtil.isEmpty(userId)) {
			log.warn("userId Is Null or Empty [userId = " + userId + "]");
			return 0;
		}
		if (StringValidateUtil.isEmpty(searchForm.getHousingRequestId())) {
			log.warn("housingRequestId Is Null or Empty [housingRequestId = " + searchForm.getHousingRequestId() + "]");
			return 0;
		}

		List<HousingRequest> requests = searchRequest(userId, searchForm.getHousingRequestId());
		if (requests == null || requests.isEmpty()) {
			log.warn("Selected HousingRequestInfo Is Null [userId = " + userId + ", housingRequestId = "+ searchForm.getHousingRequestId() +"]");
		}

		HousingRequest request = requests.get(0);

		HousingSearchForm housingSearchForm = this.housingFormFactory.createHousingSearchForm();

		// 物件リクエスト情報
		HousingRequestInfo requestInfo = request.getHousingRequestInfo();
		// 賃料/価格・下限
		if (requestInfo.getPriceLower() != null) {
			housingSearchForm.setKeyPriceLower(requestInfo.getPriceLower());
		}
		// 賃料/価格・上限
		if (requestInfo.getPriceUpper() != null) {
			housingSearchForm.setKeyPriceUpper(requestInfo.getPriceUpper());
		}
		// 専有面積・下限
		if (requestInfo.getPersonalAreaLower() != null) {
			housingSearchForm.setKeyPersonalAreaLower(requestInfo.getPersonalAreaLower());
		}
		// 専有面積・上限
		if (requestInfo.getPersonalAreaUpper() != null) {
			housingSearchForm.setKeyPersonalAreaUpper(requestInfo.getPersonalAreaUpper());
		}

		// 物件リクエストエリア情報
		StringBuffer sbPref = new StringBuffer();
		StringBuffer sbAddress = new StringBuffer();
		List<HousingRequestArea> requestAreas = request.getHousingRequestAreas();
		for (HousingRequestArea requestArea : requestAreas) {
			// 都道府県CD
			if (!StringValidateUtil.isEmpty(requestArea.getPrefCd())) {
				if (sbPref.length() > 0) {
					sbPref.append(",");
				}
				sbPref.append(requestArea.getPrefCd());
			}
			// 市区町村CD
			if (!StringValidateUtil.isEmpty(requestArea.getAddressCd())) {
				if (sbAddress.length() > 0) {
					sbAddress.append(",");
				}
				sbAddress.append(requestArea.getAddressCd());
			}
		}
		housingSearchForm.setKeyPrefCd(sbPref.toString());
		housingSearchForm.setKeyAddressCd(sbAddress.toString());

		// 物件リクエスト路線情報
		StringBuffer sbRoute = new StringBuffer();
		List<HousingReqRoute> requestRoutes = request.getHousingReqRoutes();
		for (HousingReqRoute requestRoute : requestRoutes) {
			// 路線CD
			if (!StringValidateUtil.isEmpty(requestRoute.getRouteCd())) {
				if (sbRoute.length() > 0) {
					sbRoute.append(",");
				}
				sbRoute.append(requestRoute.getRouteCd());
			}
		}
		housingSearchForm.setKeyRouteCd(sbRoute.toString());

		// 物件リクエスト最寄り駅情報
		StringBuffer sbStation = new StringBuffer();
		List<HousingReqStation> requestStations = request.getHousingReqStations();
		for (HousingReqStation requestStation : requestStations) {
			// 路線CD
			if (!StringValidateUtil.isEmpty(requestStation.getStationCd())) {
				if (sbStation.length() > 0) {
					sbStation.append(",");
				}
				sbStation.append(requestStation.getStationCd());
			}
		}
		housingSearchForm.setKeyStationCd(sbStation.toString());

		// 物件リクエスト物件種類情報
		StringBuffer sbHousingKind = new StringBuffer();
		List<HousingReqKind> requestKinds = request.getHousingReqKinds();
		for (HousingReqKind requestKind : requestKinds) {
			// 路線CD
			if (!StringValidateUtil.isEmpty(requestKind.getHousingKindCd())) {
				if (sbHousingKind.length() > 0) {
					sbHousingKind.append(",");
				}
				sbHousingKind.append(requestKind.getHousingKindCd());
			}
		}
		housingSearchForm.setKeyHousingKindCd(sbHousingKind.toString());

		// 物件リクエスト間取り情報
		StringBuffer sbLayout = new StringBuffer();
		List<HousingReqLayout> requestLayouts = request.getHousingReqLayouts();
		for (HousingReqLayout requestLayout : requestLayouts) {
			// 路線CD
			if (!StringValidateUtil.isEmpty(requestLayout.getLayoutCd())) {
				if (sbLayout.length() > 0) {
					sbLayout.append(",");
				}
				sbLayout.append(requestLayout.getLayoutCd());
			}
		}
		housingSearchForm.setKeyLayoutCd(sbLayout.toString());

		// 物件リクエストこだわり条件情報
		StringBuffer sbPartSrch = new StringBuffer();
		List<HousingReqPart> requestParts = request.getHousingReqParts();
		for (HousingReqPart requestPart : requestParts) {
			// 路線CD
			if (!StringValidateUtil.isEmpty(requestPart.getPartSrchCd())) {
				if (sbPartSrch.length() > 0) {
					sbPartSrch.append(",");
				}
				sbPartSrch.append(requestPart.getPartSrchCd());
			}
		}
		housingSearchForm.setKeyPartSrchCd(sbPartSrch.toString());

		// 物件リクエスト検索Formが保持するPagingListFormのプロパティ値を物件情報検索Formにコピーする
		housingSearchForm.setRowsPerPage(searchForm.getRowsPerPage());
		housingSearchForm.setVisibleNavigationPageCount(searchForm.getVisibleNavigationPageCount());
		housingSearchForm.setSelectedPage(searchForm.getSelectedPage());

		int cnt = this.housingManager.searchHousing(housingSearchForm);

		// 物件情報検索Formが受け取った物件一覧を物件リクエスト検索Formにコピーする
		searchForm.setRows(housingSearchForm.getRows());

		return cnt;
	}

	/**
	 * 物件リクエスト情報用のバリーオブジェクトを作成するファクトリーメソッド<br/>
	 * <br/>
	 * @return HousingRequestInfo を継承した物件リクエスト情報オブジェクト
	 */
	protected HousingRequestInfo buildHousingRequestInfo() {

		// 重要
		// もしお気に入り情報用テーブルを HousingRequestInfo 以外のオブジェクトに変更した場合、
		// このメソッドを適切なバリーオブジェクトを生成する様にオーバーライドする事。

		return (HousingRequestInfo) this.valueObjectFactory.getValueObject("HousingRequestInfo"); 
	}

	/**
	 * 物件リクエストエリア情報用のバリーオブジェクトを作成するファクトリーメソッド<br/>
	 * <br/>
	 * @return HousingRequestArea を継承した物件リクエストエリア情報オブジェクト
	 */
	protected HousingRequestArea buildHousingRequestArea() {

		// 重要
		// もし物件リクエストエリア情報テーブルを HousingRequestArea 以外のオブジェクトに変更した場合、
		// このメソッドを適切なバリーオブジェクトを生成する様にオーバーライドする事。

		return (HousingRequestArea) this.valueObjectFactory.getValueObject("HousingRequestArea"); 
	}

	/**
	 * 物件リクエスト路線情報用のバリーオブジェクトを作成するファクトリーメソッド<br/>
	 * <br/>
	 * @return HousingReqRoute を継承した物件リクエスト路線情報オブジェクト
	 */
	protected HousingReqRoute buildHousingReqRoute() {

		// 重要
		// もし物件リクエスト路線情報テーブルを HousingReqRoute 以外のオブジェクトに変更した場合、
		// このメソッドを適切なバリーオブジェクトを生成する様にオーバーライドする事。

		return (HousingReqRoute) this.valueObjectFactory.getValueObject("HousingReqRoute"); 
	}

	/**
	 * 物件リクエスト最寄駅情報用のバリーオブジェクトを作成するファクトリーメソッド<br/>
	 * <br/>
	 * @return HousingReqStation を継承した物件リクエスト最寄駅情報オブジェクト
	 */
	protected HousingReqStation buildHousingReqStation() {

		// 重要
		// もし物件リクエスト最寄駅情報用を HousingReqStation 以外のオブジェクトに変更した場合、
		// このメソッドを適切なバリーオブジェクトを生成する様にオーバーライドする事。

		return (HousingReqStation) this.valueObjectFactory.getValueObject("HousingReqStation"); 
	}

	/**
	 * 物件リクエスト物件種類情報用のバリーオブジェクトを作成するファクトリーメソッド<br/>
	 * <br/>
	 * @return HousingReqKind を継承した物件リクエスト物件種類情報オブジェクト
	 */
	protected HousingReqKind buildHousingReqKind() {

		// 重要
		// もし物件リクエスト物件種類情報テーブルを HousingReqKind 以外のオブジェクトに変更した場合、
		// このメソッドを適切なバリーオブジェクトを生成する様にオーバーライドする事。

		return (HousingReqKind) this.valueObjectFactory.getValueObject("HousingReqKind"); 
	}

	/**
	 * 物件リクエスト間取情報用のバリーオブジェクトを作成するファクトリーメソッド<br/>
	 * <br/>
	 * @return HousingReqLayout を継承した物件リクエスト間取情報オブジェクト
	 */
	protected HousingReqLayout buildHousingReqLayout() {

		// 重要
		// もし物件リクエスト間取情報テーブルを HousingReqLayout 以外のオブジェクトに変更した場合、
		// このメソッドを適切なバリーオブジェクトを生成する様にオーバーライドする事。

		return (HousingReqLayout) this.valueObjectFactory.getValueObject("HousingReqLayout"); 
	}

	/**
	 * 物件リクエストこだわり条件情報用のバリーオブジェクトを作成するファクトリーメソッド<br/>
	 * <br/>
	 * @return HousingReqPart を継承した物件リクエストこだわり条件情報オブジェクト
	 */
	protected HousingReqPart buildHousingReqPart() {

		// 重要
		// もし物件リクエストこだわり情報情報テーブルを HousingReqPart 以外のオブジェクトに変更した場合、
		// このメソッドを適切なバリーオブジェクトを生成する様にオーバーライドする事。

		return (HousingReqPart) this.valueObjectFactory.getValueObject("HousingReqPart"); 
	}

	/**
	 * 物件リクエスト情報オブジェクトのインスタンスを生成する。<br/>
	 * もし、カスタマイズで構成するテーブルを追加した場合、このメソッドをオーバーライドする事。<br/>
	 * <br/>
	 * @return HousingRequest のインスタンス
	 */
	protected HousingRequest createHousingRequest() {
		return new HousingRequest();
	}
}
