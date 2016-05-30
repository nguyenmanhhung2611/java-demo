package jp.co.transcosmos.dm3.corePana.model.favorite;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jp.co.transcosmos.dm3.core.model.favorite.FavoriteManageImpl;
import jp.co.transcosmos.dm3.core.model.favorite.form.FavoriteSearchForm;
import jp.co.transcosmos.dm3.core.model.information.InformationManageImpl;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.FavoriteInfo;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousing;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.vo.HousingStatusInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.dao.NotEnoughRowsException;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

/**
 * お気に入り情報用 Model クラス.
 * <p>
 * <pre>
 * 担当者         修正日      修正内容
 * -------------- ----------- -----------------------------------------------------
 * tang.tianyun	  2015.04.20	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスはシングルトンで DI コンテナに定義されるので、スレッドセーフである事。<br/>
 *
 */
public class PanaFavoriteManageImpl extends FavoriteManageImpl {

	private static final Log log = LogFactory.getLog(InformationManageImpl.class);

	/** お気に入り情報更新用 DAO */
	private DAO<FavoriteInfo> favoriteInfoDAO;

	/** 物件基本情報 **/
	private DAO<HousingInfo> housingInfoDAO;

	/** 物件ステータス情報 **/
	private DAO<HousingStatusInfo> housingStatusInfoDAO;

	/** VO のインスタンスを生成する場合のファクトリー */
	private ValueObjectFactory valueObjectFactory;

	/** 共通パラメータオブジェクト */
	private PanaCommonParameters commonParameters;

	/** 物件情報用 Model オブジェクト */
	private PanaHousingPartThumbnailProxy panaHousingManage;

	/** お気に入り物件一覧情報に該当する物件基本情報の取得用 */
	private DAO<JoinResult> favoritePublicHousingListDAO;

	/** 物件画像情報DAO */
	private DAO<HousingImageInfo> housingImageInfoDAO;

	/**
	 * お気に入り情報更新用  DAO を設定する。<br/>
	 * <br/>
	 *
	 * @param お気に入り情報更新用 DAO
	 */
	public void setFavoriteInfoDAO(DAO<FavoriteInfo> favoriteInfoDAO) {
		this.favoriteInfoDAO = favoriteInfoDAO;
	}

	/**
	 * 物件基本情報検索用 DAO を設定する。<br/>
	 * <br/>
	 *
	 * @param 物件基本情報検索用 DAO
	 */
	public void setHousingInfoDAO(DAO<HousingInfo> housingInfoDAO) {
		this.housingInfoDAO = housingInfoDAO;
	}

	/**
	 * 物件ステータス情報検索用 DAO を設定する。<br/>
	 * <br/>
	 *
	 * @param 物件ステータス情報検索用 DAO
	 */
	public void setHousingStatusInfoDAO(DAO<HousingStatusInfo> housingStatusInfoDAO) {
		this.housingStatusInfoDAO = housingStatusInfoDAO;
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
	 * 物件情報用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param PanaHousingManage 物件情報用 Model オブジェクト
	 */
	public void setPanaHousingManage(PanaHousingPartThumbnailProxy panaHousingManage) {
		this.panaHousingManage = panaHousingManage;
	}

	/**
	 * @param favoritePublicHousingListDAO セットする favoritePublicHousingListDAO
	 */
	public void setFavoritePublicHousingListDAO(
			DAO<JoinResult> favoritePublicHousingListDAO) {
		this.favoritePublicHousingListDAO = favoritePublicHousingListDAO;
	}

	/**
	 * @param housingImageInfoDAO セットする housingImageInfoDAO
	 */
	public void setHousingImageInfoDAO(DAO<HousingImageInfo> housingImageInfoDAO) {
		this.housingImageInfoDAO = housingImageInfoDAO;
	}

	/**
	 * 引数で渡された値でお気に入り情報を削除する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * 指定されたお気に入り情報が存在しない場合は、そのまま正常終了として扱う。<br/>
	 * <br/>
	 *
	 * @param userId ユーザーID
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */

	public void delFavorite(String userId) throws Exception {

		// パラメタチェック
		if (StringValidateUtil.isEmpty(userId)) {
			log.warn("userId Is Null or Empty [userId = " + userId + "]");
			return;
		}

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("userId", userId);
		this.favoriteInfoDAO.deleteByFilter(criteria);

		log.debug("Deleted FavoriteInfo [userId = " + userId + "]");

	}

	/**
	 * 引数で渡された値でお気に入り情報を削除する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * 指定されたお気に入り情報が存在しない場合は、そのまま正常終了として扱う。<br/>
	 * <br/>
	 *
	 * @param userId ユーザーID
	 * @param sysHousingCd システム物件CD
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	@Override
	public void delFavorite(String userId, String sysHousingCd) throws Exception {
		super.setFavoriteInfoDAO(this.favoriteInfoDAO);
		super.delFavorite(userId, sysHousingCd);

	}

	/**
	 * ユーザーIDよりお気に入り情報取得<br/>
	 * ※isCheckがfalseの場合は、募集終了した物件（物件情報が無い場合、および、非公開に設定されている場合）を含まない<br/>
	 *
	 * @param userId ユーザーID
	 * @param isCheck チェックフラグ
	 *
	 * @return お気に入り情報
	 * @throws Exception
	 */
	private Map<String, FavoriteInfo> getFavoriteInfoMap(String userId,
			boolean isCheck) throws Exception {
		Map<String, FavoriteInfo> favoriteInfoMap = new TreeMap<String, FavoriteInfo>();

		if (null != userId) {
			DAOCriteria paramDAOCriteria = new DAOCriteria();
			paramDAOCriteria.addWhereClause("userId", userId);
			paramDAOCriteria.addOrderByClause("insDate", true);
			List<FavoriteInfo> favoriteInfoList = favoriteInfoDAO
					.selectByFilter(paramDAOCriteria);

			if (null != favoriteInfoList && favoriteInfoList.size() > 0) {
				String sysHousingCd;// システム物件CD
				FavoriteInfo favoriteInfo;

				for (int i = 0; i < favoriteInfoList.size(); i++) {
					favoriteInfo = favoriteInfoList.get(i);

					if (null != favoriteInfo) {
						// システム物件CD取得
						sysHousingCd = favoriteInfo.getSysHousingCd();

						if (isCheck) {
							if (!StringValidateUtil.isEmpty(sysHousingCd)) {
								HousingInfo housingInfo = housingInfoDAO
										.selectByPK(sysHousingCd);

								if (null != housingInfo
										&& !StringValidateUtil
												.isEmpty(housingInfo
														.getSysHousingCd())) {// 物件情報があるかどうか判断
									HousingStatusInfo housingStatusInfo = housingStatusInfoDAO
											.selectByPK(sysHousingCd);

									if (null != housingStatusInfo
											&& !PanaCommonConstant.HIDDEN_FLG_PRIVATE
													.equals(housingStatusInfo
															.getHiddenFlg())) {// 非公開かどうか判断
										favoriteInfoMap.put(sysHousingCd + ":"
												+ userId, favoriteInfo);
									}
								}
							}
						} else {
							favoriteInfoMap.put(sysHousingCd + ":" + userId,
									favoriteInfo);
						}
					}
				}
			}

		}

		return favoriteInfoMap;
	}

	/**
	 * ユーザーIDよりお気に入り情報取得<br/>
	 * ※募集終了した物件（物件情報が無い場合、および、非公開に設定されている場合）も含む<br/>
	 *
	 * @param userId
	 *            ユーザーID
	 * @return お気に入り情報
	 * @throws Exception
	 */
	public Map<String, List<FavoriteInfo>> searchPrivateFavorite(String userId) throws Exception {

		Map<String, List<FavoriteInfo>> map = new HashMap<String, List<FavoriteInfo>>();

		DAOCriteria paramDAOCriteria = new DAOCriteria();
		paramDAOCriteria.addWhereClause("userId", userId);
		paramDAOCriteria.addOrderByClause("insDate", true);

		List<FavoriteInfo> favoriteInfoList = this.favoriteInfoDAO.selectByFilter(paramDAOCriteria);

		//募集終了した物件リスト
		List<FavoriteInfo> listPrivate = new ArrayList<>();

		if (null != favoriteInfoList && favoriteInfoList.size() > 0) {
			String sysHousingCd;// システム物件CD
			FavoriteInfo favoriteInfo;

			for (int i = 0; i < favoriteInfoList.size(); i++) {
				favoriteInfo = (FavoriteInfo) favoriteInfoList.get(i);

				if (null != favoriteInfo) {
					// システム物件CD取得
					sysHousingCd = favoriteInfo.getSysHousingCd();

					if (!StringValidateUtil.isEmpty(sysHousingCd)) {
						HousingInfo housingInfo = housingInfoDAO
								.selectByPK(sysHousingCd);

						if (null != housingInfo
								&& !StringValidateUtil.isEmpty(housingInfo.getSysHousingCd())) {
							// 物件情報があるかどうか判断
							HousingStatusInfo housingStatusInfo = housingStatusInfoDAO
									.selectByPK(sysHousingCd);

							if (housingStatusInfo == null) {
								listPrivate.add(favoriteInfo);
							} else if (null != housingStatusInfo && PanaCommonConstant.HIDDEN_FLG_PRIVATE
									.equals(housingStatusInfo.getHiddenFlg())) {
								// 非公開かどうか判断
								listPrivate.add(favoriteInfo);
							}
						} else {
							listPrivate.add(favoriteInfo);
						}
					}
				}
			}
		}
		map.put("private", listPrivate);

		return map;
	}

	/**
	 * ユーザーIDよりお気に入り情報取得<br/>
	 * ※募集終了した物件（物件情報が無い場合、および、非公開に設定されている場合）を含まない<br/>
	 *
	 * @param FavoriteSearchForm
	 * @param userId ユーザーID
	 * @param orderBy ソート順
	 * @param ascending true：昇順 false：降順
	 *
	 * @return お気に入り情報件数
	 * @throws Exception
	 */
	public int searchPublicFavorite(FavoriteSearchForm searchForm, String userId, String orderBy, boolean ascending) throws Exception {

		DAOCriteria paramDAOCriteria = new DAOCriteria();
		paramDAOCriteria.addWhereClause("favoriteInfo", "userId", userId, DAOCriteria.EQUALS, false);
		paramDAOCriteria.addWhereClause("hiddenFlg", PanaCommonConstant.HIDDEN_FLG_PUBLIC);

		switch (orderBy) {
		case "1":
			// 並べ替え：お気に入り登録日
			paramDAOCriteria.addOrderByClause("favoriteInfo", "insDate", ascending);
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

		List<JoinResult> favoriteInfoList;
		try {
			favoriteInfoList = this.favoritePublicHousingListDAO.selectByFilter(paramDAOCriteria);
		} catch (NotEnoughRowsException err) {
			int pageNo = (err.getMaxRowCount() - 1) / searchForm.getRowsPerPage() + 1;
			searchForm.setSelectedPage(pageNo);
			favoriteInfoList = this.favoritePublicHousingListDAO.selectByFilter(paramDAOCriteria);
		}
		searchForm.setRows(favoriteInfoList);
		return favoriteInfoList.size();

	}

	/**
	 * 引数で渡された値でお気に入り情報を登録する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * 指定されたユーザーID、システム物件CD が既に存在する場合、上書き保存する。<br/>
	 * 　登録件数※登録件数の上限なし。<br/>
	 * <br/>
	 *
	 * @param userId マイページのユーザーID
	 * @param sysHousingCd お気に入り登録するシステム物件CD
	 *
	 * @return messageId 0⇒追加した
	 *                   1⇒最大件数に達した
	 *                   2⇒既に存在した
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public String addPanaFavorite(String userId, String sysHousingCd)
			throws Exception {
		log.debug(">>>addFavoriteInfo(String userId, String sysHousingCd)<<<");
		log.debug(">>>userId is : " + userId + "<<<");
		log.debug(">>>sysHousingCd is : " + sysHousingCd + "<<<");

		String message = "";

		if (!StringValidateUtil.isEmpty(userId)
				&& !StringValidateUtil.isEmpty(sysHousingCd)) {
			Map<String, FavoriteInfo> favoriteInfoMap = this
					.getFavoriteInfoMap(userId, false);

			if (favoriteInfoMap.size() >= commonParameters.getMaxFavoriteInfoCnt()) {// 上限件数を超えた場合
				message = "1";
			} else {
				if (null != favoriteInfoMap.get(sysHousingCd + ":" + userId)) {// 既に存在する場合
					message = "2";
				} else {
					// 該当する物件情報
					HousingInfo housingInfo = this.housingInfoDAO
							.selectByPK(sysHousingCd);

					// 重要
					// もしお気に入り情報テーブルを FavoriteInfo 以外のオブジェクトに変更した場合、
					// このメソッドを適切なバリーオブジェクトを生成する様にオーバーライドする事。
					FavoriteInfo favoriteInfo = (FavoriteInfo) this.valueObjectFactory
							.getValueObject("FavoriteInfo");

					// 登録日、最終更新日の値
					Date sysDate = new Date();

					favoriteInfo.setSysHousingCd(sysHousingCd);
					favoriteInfo.setUserId(userId);
					favoriteInfo.setDisplayHousingName(housingInfo
							.getDisplayHousingName());
					favoriteInfo.setInsDate(sysDate);
					favoriteInfo.setInsUserId(userId);
					favoriteInfo.setUpdDate(sysDate);
					favoriteInfo.setUpdUserId(userId);

					this.favoriteInfoDAO
							.insert(new FavoriteInfo[] { favoriteInfo });

					message = "0";
				}
			}
		}

		return message;
	}

	/**
	 * お気に入り物件情報を検索し、結果リストを復帰する。<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、お気に入り物件情報を検索する。<br/>
	 * 検索結果は Form オブジェクトに格納され、取得した該当件数を戻り値として復帰する。<br/>
	 * <br/>
	 * @param userId ユーザーID
	 *
	 * @return 結果リスト
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public List<PanaHousing> searchFavoriteInfo(String userId) throws Exception {

		// お気に入り物件を取得する。
		Map<String, FavoriteInfo> favoriteInfo = this.getFavoriteInfoMap(userId, true);

		// システム物件CD
		String sysHousingCd = null;

		// 物件情報
		PanaHousing housing = null;

		// お気に入り物件情報結果リスト
		List<PanaHousing> favoriteInfoList = new ArrayList<PanaHousing>();

		// お気に入り物件を繰り返す、物件情報を取得する。
		for (String key : favoriteInfo.keySet()) {

			if (!StringUtils.isEmpty(key) && key.split(":").length == 2) {

				sysHousingCd = key.split(":")[0];

				// 物件情報を取得する。
				housing = (PanaHousing) this.panaHousingManage.searchHousingPk(sysHousingCd);

				// 閲覧権限があり、且つ、表示順、画像タイプ、枝番で昇順ソート後の1番目の画像を表示
				DAOCriteria criteria = new DAOCriteria();
				criteria.addWhereClause("sysHousingCd", sysHousingCd);
				criteria.addWhereClause("roleId", null, DAOCriteria.IS_NULL, true);
				criteria.addOrderByClause("sortOrder");
				criteria.addOrderByClause("imageType");
				criteria.addOrderByClause("divNo");
				List<HousingImageInfo> housingImageInfoList = this.housingImageInfoDAO.selectByFilter(criteria);
				housing.setHousingImageInfos(housingImageInfoList);

				favoriteInfoList.add(housing);
			}
		}
		return favoriteInfoList;
	}

	/**
	 * ユーザーIDよりお気に入り物件テーブルから件数取得
	 *
	 * @param userId
	 *            ユーザーID
	 * @return お気に入り物件件数
	 * @throws Exception
	 */
	public int getFavoriteInfoCnt(String userId) throws Exception {

		return this.getFavoriteInfoMap(userId, false).size();
	}
}
