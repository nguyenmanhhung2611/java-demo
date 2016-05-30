package jp.co.transcosmos.dm3.corePana.model.recentlyInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.RecentlyInfoManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousing;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.recentlyInfo.form.RecentlyInfoForm;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.HousingStatusInfo;
import jp.co.transcosmos.dm3.corePana.vo.RecentlyInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.util.StringUtils;

/**
 * 最近見た物件情報を管理する Model クラス.
 * <p>
 * 最近見た物件情報を操作する model クラス。<br/>
 * <p>
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * gao.long		2015.04.27	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスはシングルトンで DI コンテナに定義されるので、スレッドセーフである事。<br/>
 *
 */
public class RecentlyInfoManageImpl implements RecentlyInfoManage {

	/** 最近見た物件情報取得用 DAO */
	private DAO<JoinResult> recentlyInfoHousingListDAO;

	/** 最近見た物件 **/
	private DAO<RecentlyInfo> recentlyInfoDAO;

	/** 物件基本情報 **/
	private DAO<HousingInfo> housingInfoDAO;

	/** 物件ステータス情報用DAO */
	private DAO<HousingStatusInfo> housingStatusInfoDAO;

	/** 物件情報用 Model オブジェクト */
	private PanaHousingPartThumbnailProxy panaHousingManage;

	/** VO のインスタンスを生成する場合のファクトリー */
	private ValueObjectFactory valueObjectFactory;

	/** 共通パラメータオブジェクト */
	private PanaCommonParameters commonParameters;

	/**
	 * 最近見た物件情報取得用 DAO を設定する。<br/>
	 * <br/>
	 * @param recentlyInfoHousingListDAO 最近見た物件情報取得用 DAO
	 */
	public void setRecentlyInfoHousingListDAO(DAO<JoinResult> recentlyInfoHousingListDAO) {
		this.recentlyInfoHousingListDAO = recentlyInfoHousingListDAO;
	}

	/**
	 * 最近見た物件用情報 DAO を設定する。<br/>
	 * <br/>
	 * @param recentlyInfoDAO 最近見た物件用情報 DAO
	 */
	public void setRecentlyInfoDAO(DAO<RecentlyInfo> recentlyInfoDAO) {
		this.recentlyInfoDAO = recentlyInfoDAO;
	}

	/**
	 * 物件基本情報用　DAOを設定する。<br/>
	 * <br/>
	 * @param housingInfoDAO 物件基本情報用　DAO
	 */
	public void setHousingInfoDAO(DAO<HousingInfo> housingInfoDAO) {
		this.housingInfoDAO = housingInfoDAO;
	}

	/**
	 * 物件ステータス情報用DAO を設定する。<br/>
	 * <br/>
	 * @param housingStatusInfoDAO 物件ステータス情報用DAO
	 */
	public void setHousingStatusInfoDAO(DAO<HousingStatusInfo> housingStatusInfoDAO) {
		this.housingStatusInfoDAO = housingStatusInfoDAO;
	}

	/**
	 * 物件情報用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param PanaHousingManage 物件情報用 Model オブジェクト
	 */
	public void setPanaHousingManage(PanaHousingPartThumbnailProxy panaHousingManage) {
		this.panaHousingManage = panaHousingManage;
	}

	/**
	 * バリーオブジェクトのインスタンスを生成するファクトリーを設定する。<br/>
	 * <br/>
	 * @param valueObjectFactory バリーオブジェクトのファクトリー
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}

	/**
	 * 共通パラメータオブジェクトを設定する。<br/>
	 * <br/>
	 * @param commonParameters 共通パラメータオブジェクト
	 */
	public void setCommonParameters(PanaCommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * パラメータで渡された Form の情報で最近見た物件情報を新規追加する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * <br/>
	 * @param paramMap 最近見た物件情報のシステム物件CD、ユーザーIDを格納した Map オブジェクト
	 * @param editUserId ログインユーザーID （更新情報用）
	 * @return 0⇒更新した、または最大件数に達した、
	 *         1⇒追加した
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public int addRecentlyInfo(Map<String, Object> paramMap, String editUserId) throws Exception {

		int ret = 0;

		DAOCriteria criteria;

		// 最近見た物件を取得する。
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String) paramMap.get("sysHousingCd"));
		criteria.addWhereClause("userId", (String) paramMap.get("userId"));
		List<RecentlyInfo> recentlyInfo = this.recentlyInfoDAO.selectByFilter(criteria);

		// 取得した場合
		if (recentlyInfo != null && recentlyInfo.size() > 0) {

			// 最近見た物件
			RecentlyInfo recentlyInfoUpdate = recentlyInfo.get(0);

			// 最近見た物件を更新する。
			recentlyInfoUpdate.setUpdDate(new Date());
			recentlyInfoUpdate.setUpdUserId(editUserId);
			this.recentlyInfoDAO.update(new RecentlyInfo[] { recentlyInfoUpdate });

			// 更新した場合
			ret = 0;

		} else {

			// 最近見た物件の登録件数を取得する。
			int cnt = getRecentlyInfoCnt((String) paramMap.get("userId"));

			// 取得した件数が指定した件数を超える場合
			if (cnt >= commonParameters.getMaxRecentlyInfoCnt()) {

				// 最古い最近見た物件を取得する。
				criteria = new DAOCriteria();
				criteria.addWhereClause("userId", (String) paramMap.get("userId"));
				criteria.addOrderByClause("updDate");
				List<RecentlyInfo> recentlyInfoByUserIdList = this.recentlyInfoDAO.selectByFilter(criteria);

				if (recentlyInfoByUserIdList != null & recentlyInfoByUserIdList.size() > 0) {

					// 最古い最近見た物件を削除する。
					RecentlyInfo recentlyInfoDelete = recentlyInfoByUserIdList.get(0);
					this.recentlyInfoDAO.delete(new RecentlyInfo[] { recentlyInfoDelete });

					// 削除した場合
					ret--;
				}
			}

			// 最近見た物件を登録する。
			RecentlyInfo recentlyInfoInsert = (RecentlyInfo) this.valueObjectFactory.getValueObject("RecentlyInfo");
			recentlyInfoInsert.setSysHousingCd((String) paramMap.get("sysHousingCd"));
			recentlyInfoInsert.setUserId((String) paramMap.get("userId"));
			recentlyInfoInsert.setInsDate(new Date());
			recentlyInfoInsert.setInsUserId(editUserId);
			recentlyInfoInsert.setUpdDate(new Date());
			recentlyInfoInsert.setUpdUserId(editUserId);
			this.recentlyInfoDAO.insert(new RecentlyInfo[] { recentlyInfoInsert });

			// 登録した場合
			ret++;
		}

		return ret;
	}

	/**
	 * 最近見た物件情報を検索し、結果リストを復帰する。<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、最近見た物件情報を検索する。<br/>
	 * 検索結果は Form オブジェクトに格納され、取得した該当件数を戻り値として復帰する。<br/>
	 * <br/>
	 * @param searchForm 検索条件、および、検索結果の格納オブジェクト
	 *
	 * @return 該当件数
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public List<Map<String, PanaHousing>> searchRecentlyInfo(RecentlyInfoForm searchForm) throws Exception {
		return null;
	}

	/**
	 * 最近見た物件情報を検索し、結果リストを復帰する。<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、最近見た物件情報を検索する。<br/>
	 * 検索結果は Form オブジェクトに格納され、取得した該当件数を戻り値として復帰する。<br/>
	 * <br/>
	 * @param userId ユーザーID
	 *
	 * @return 結果リスト
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public List<PanaHousing> searchRecentlyInfo(String userId) throws Exception {

		// 最近見た物件を取得する。
		Map<String, RecentlyInfo> recentlyInfo = getRecentlyInfoMap(userId, true);

		// システム物件CD
		String sysHousingCd = null;

		// 物件情報
		PanaHousing housing = null;

		// 最近見た物件情報結果リスト
		List<PanaHousing> recentlyInfoList = new ArrayList<PanaHousing>();

		// 最近見た物件を繰り返す、物件情報を取得する。
		for (String key : recentlyInfo.keySet()) {

			if (!StringUtils.isEmpty(key) && key.split(":").length == 2) {

				sysHousingCd = key.split(":")[0];

				// 物件情報を取得する。
				housing = (PanaHousing) this.panaHousingManage.searchHousingPk(sysHousingCd);

				recentlyInfoList.add(housing);
			}
		}
		return recentlyInfoList;
	}

	/**
	 * ユーザーIDより最近見た物件取得
	 * ※isCheckがfalseの場合は、募集終了した物件（物件情報が無い場合、および、非公開に設定されている場合）を含まない<br/>
	 *
	 * @param userId ユーザーID
	 * @param isCheck チェックフラグ
	 *
	 * @return 最近見た物件
	 * @throws Exception
	 */
	public Map<String, RecentlyInfo> getRecentlyInfoMap(String userId, boolean isCheck)
			throws Exception {
		Map<String, RecentlyInfo> recentlyInfoMap = new LinkedHashMap<String, RecentlyInfo>();

		if (null != userId) {
			DAOCriteria paramDAOCriteria = new DAOCriteria();
			paramDAOCriteria.addWhereClause("userId", userId);
			paramDAOCriteria.addOrderByClause("updDate", false);
			List<RecentlyInfo> recentlyInfoList = recentlyInfoDAO
					.selectByFilter(paramDAOCriteria);

			if (null != recentlyInfoList && recentlyInfoList.size() > 0) {
				String sysHousingCd;// システム物件CD
				RecentlyInfo recentlyInfo;

				for (int i = 0; i < recentlyInfoList.size(); i++) {
					recentlyInfo = recentlyInfoList.get(i);

					if (null != recentlyInfo) {
						// システム物件CD取得
						sysHousingCd = recentlyInfo.getSysHousingCd();

						if (isCheck) {
							if (!StringValidateUtil.isEmpty(sysHousingCd)) {
								HousingInfo housingInfo = housingInfoDAO
										.selectByPK(sysHousingCd);

								if (null != housingInfo
										&& !StringValidateUtil.isEmpty(housingInfo
												.getSysHousingCd())) {// 物件情報があるかどうか判断
									HousingStatusInfo housingStatusInfo = housingStatusInfoDAO
											.selectByPK(sysHousingCd);

									if (null != housingStatusInfo
											&& !PanaCommonConstant.HIDDEN_FLG_PRIVATE
													.equals(housingStatusInfo
															.getHiddenFlg())) {// 非公開かどうか判断
										recentlyInfoMap.put(sysHousingCd + ":"
												+ userId, recentlyInfo);
									}
								}
							}
						} else {
							recentlyInfoMap.put(sysHousingCd + ":" + userId,
									recentlyInfo);
						}
					}
				}
			}

		}

		return recentlyInfoMap;
	}

	/**
	 * ユーザーIDより最近見た物件テーブルから件数取得
	 *
	 * @param userId
	 *            ユーザーID
	 * @return 最近見た物件件数
	 * @throws Exception
	 */
	public int getRecentlyInfoCnt(String userId) throws Exception {

		return this.getRecentlyInfoMap(userId, false).size();
	}

	/**
	 * 最近見た物件の登録件数を検索し、登録件数を復帰する。<br/>
	 * 引数で渡された Map パラメータの値で検索条件を生成し、最近見た物件の登録件数を検索する。<br/>
	 * 取得した該当件数を戻り値として復帰する。<br/>
	 * <br/>
	 * @param paramMap 検索条件の格納オブジェクト
	 *
	 * @return 登録件数
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public int searchRecentlyInfo(Map<String, Object> paramMap) throws Exception {
		return 0;
	}

	/**
	 * ユーザーIDより最近見た物件情報取得<br/>
	 * ※募集終了した物件（物件情報が無い場合、および、非公開に設定されている場合）も含む<br/>
	 *
	 * @param userId
	 *            ユーザーID
	 * @return 最近見た物件情報
	 * @throws Exception
	 */
	public List<PanaHousing> getRecentlyInfoListMap(String userId, String orderBy, boolean ascending) throws Exception {

		DAOCriteria paramDAOCriteria = new DAOCriteria();
		paramDAOCriteria.addWhereClause("recentlyInfo", "userId", userId, DAOCriteria.EQUALS, false);
		paramDAOCriteria.addWhereClause("hiddenFlg", PanaCommonConstant.HIDDEN_FLG_PUBLIC);
		switch (orderBy) {
		case "1":
			// 並べ替え：最終更新日
			paramDAOCriteria.addOrderByClause("recentlyInfo", "updDate", ascending);
			break;
		case "2":
			// 並べ替え：物件価格
			paramDAOCriteria.addOrderByClause("housingInfo", "price", ascending);
			break;
		case "3":
			// 並べ替え：築年数
			paramDAOCriteria.addOrderByClause("buildingInfo", "compDate", ascending);
			break;
		case "4":
			// 並べ替え：駅からの距離
			paramDAOCriteria.addOrderByClause("housingInfo", "minWalkingTime", ascending);
			break;
		}

		List<JoinResult> recentlyInfoList = this.recentlyInfoHousingListDAO.selectByFilter(paramDAOCriteria);

		// 物件情報
		PanaHousing housing = null;

		// 最近見た物件情報結果リスト
		List<PanaHousing> recentlyInfoHousingList = new ArrayList<PanaHousing>();

		if (null != recentlyInfoList && recentlyInfoList.size() > 0) {
			// システム物件CD
			String sysHousingCd;
			RecentlyInfo recentlyInfo;

			for (int i = 0; i < recentlyInfoList.size(); i++) {
				recentlyInfo = (RecentlyInfo) recentlyInfoList.get(i).getItems().get("recentlyInfo");

				if (null != recentlyInfo) {
					// システム物件CD取得
					sysHousingCd = recentlyInfo.getSysHousingCd();

					// 物件情報を取得する。
					housing = (PanaHousing) this.panaHousingManage.searchHousingPk(sysHousingCd);

					recentlyInfoHousingList.add(housing);
				}
			}
		}

		return recentlyInfoHousingList;
	}

	/**
	 * 引数で渡された値で最近見た物件情報を削除する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * 指定された最近見た物件情報が存在しない場合は、そのまま正常終了として扱う。<br/>
	 * <br/>
	 *
	 * @param userId ユーザーID
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public void delRecentlyInfo(String userId) throws Exception {

		DAOCriteria paramDAOCriteria = new DAOCriteria();
		paramDAOCriteria.addWhereClause("userId", userId);

		List<RecentlyInfo> recentlyInfoList = this.recentlyInfoDAO.selectByFilter(paramDAOCriteria);

		//募集終了した物件リスト
		List<RecentlyInfo> listPrivate = new ArrayList<>();

		if (null != recentlyInfoList && recentlyInfoList.size() > 0) {
			String sysHousingCd;// システム物件CD
			RecentlyInfo recentlyInfo;

			for (int i = 0; i < recentlyInfoList.size(); i++) {
				recentlyInfo = (RecentlyInfo) recentlyInfoList.get(i);

				if (null != recentlyInfo) {
					// システム物件CD取得
					sysHousingCd = recentlyInfo.getSysHousingCd();

					if (!StringValidateUtil.isEmpty(sysHousingCd)) {
						HousingInfo housingInfo = housingInfoDAO.selectByPK(sysHousingCd);

						if (null != housingInfo && !StringValidateUtil.isEmpty(housingInfo.getSysHousingCd())) {
							// 物件情報があるかどうか判断
							HousingStatusInfo housingStatusInfo = housingStatusInfoDAO.selectByPK(sysHousingCd);

							if (housingStatusInfo == null) {
								listPrivate.add(recentlyInfo);
							} else if (null != housingStatusInfo && PanaCommonConstant.HIDDEN_FLG_PRIVATE.equals(housingStatusInfo.getHiddenFlg())) {
								// 非公開かどうか判断
								listPrivate.add(recentlyInfo);
							}
						} else {
							listPrivate.add(recentlyInfo);
						}
					}
				}
			}
		}

		for (RecentlyInfo f : listPrivate) {
			DAOCriteria criteria = new DAOCriteria();
			criteria.addWhereClause("sysHousingCd", f.getSysHousingCd());
			criteria.addWhereClause("userId", f.getUserId());
			this.recentlyInfoDAO.deleteByFilter(criteria);
		}
	}
}
