package jp.co.transcosmos.dm3.core.model.favorite;

import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.FavoriteManage;
import jp.co.transcosmos.dm3.core.model.exception.MaxEntryOverException;
import jp.co.transcosmos.dm3.core.model.favorite.form.FavoriteSearchForm;
import jp.co.transcosmos.dm3.core.model.information.InformationManageImpl;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.FavoriteInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.UserInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.dao.NotEnoughRowsException;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * お気に入り情報用 Model クラス.
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * T.Nakamura	2015.03.16	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスはシングルトンで DI コンテナに定義されるので、スレッドセーフである事。<br/>
 * 
 */
public class FavoriteManageImpl implements FavoriteManage {

	private static final Log log = LogFactory.getLog(InformationManageImpl.class);

	/** VO のインスタンスを生成する場合のファクトリー */
	protected ValueObjectFactory valueObjectFactory;

	/** お気に入り情報更新用 DAO */
	protected DAO<FavoriteInfo> favoriteInfoDAO;

	/** 物件基本情報検索用 DAO */
	protected DAO<HousingInfo> housingInfoDAO;

	/** お気に入り情報検索用（物件基本情報含む） DAO */
	protected DAO<JoinResult> favoriteListDAO;

	/** ユーザ情報検索用 DAO */
	protected DAO<UserInfo> userInfoDAO;

	/**
	 * バリーオブジェクトのインスタンスを生成するファクトリーを設定する。<br/>
	 * <br/>
	 * @param valueObjectFactory バリーオブジェクトのファクトリー
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}

	/**
	 * お気に入り情報更新用 DAOを取得する。<br/>
	 * <br/>
	 * 
	 * @return お気に入り情報更新用 DAO
	 */
	public void setFavoriteInfoDAO(DAO<FavoriteInfo> favoriteInfoDAO) {
		this.favoriteInfoDAO = favoriteInfoDAO;
	}

	/**
	 * 物件基本情報検索用 DAOを取得する。<br/>
	 * <br/>
	 * 
	 * @return 物件基本情報検索用 DAO
	 */
	public void setHousingInfoDAO(DAO<HousingInfo> housingInfoDAO) {
		this.housingInfoDAO = housingInfoDAO;
	}

	/**
	 * お気に入り情報検索用 DAOを取得する。<br/>
	 * <br/>
	 * 
	 * @return お気に入り情報検索用 DAO
	 */
	public void setFavoriteListDAO(DAO<JoinResult> favoriteListDAO) {
		this.favoriteListDAO = favoriteListDAO;
	}

	/**
	 * ユーザ情報検索用 DAOを取得する。<br/>
	 * <br/>
	 * 
	 * @param ユーザ情報検索用 DAO
	 */
	public void setUserInfoDAO(DAO<UserInfo> userInfoDAO) {
		this.userInfoDAO = userInfoDAO;
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
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception MaxEntryOverException　最大登録数オーバー
	 */
	@Override
	public void addFavorite(String userId, String sysHousingCd) throws Exception {

		// パラメタチェック
		if (StringValidateUtil.isEmpty(userId)) {
			log.warn("userId Is Null or Empty [userId = " + userId + "]");
			return;
		}
		if (StringValidateUtil.isEmpty(sysHousingCd)) {
			log.warn("sysHousingCd Is Null or Empty [sysHousingCd = " + sysHousingCd + "]");
			return;
		}

		// 登録対象情報の存在チェック
		UserInfo existUserInfo = this.userInfoDAO.selectByPK(userId);
		if (existUserInfo == null) {
			log.warn("Target UserInfo Is Null [userId = " + userId + "]");
			return;
		}
		HousingInfo existHousingInfo = this.housingInfoDAO.selectByPK(sysHousingCd);
		if (existHousingInfo == null) {
			log.warn("Target HousingInfo Is Null [sysHousingCd = " + sysHousingCd + "]");
			return;
		}

		// 既存のお気に入りレコードの検索
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);
		criteria.addWhereClause("userId", userId);
		List<FavoriteInfo> favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);

		// 複数レコードが存在する場合は処理中断
		if (favoriteInfos != null && favoriteInfos.size() > 1) {
			log.warn("Selected FavoriteInfo Is Not Unique [userId = " + userId + ", sysHousingCd = " + sysHousingCd + "]");
			return;
		}

		// 該当する物件情報
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK(sysHousingCd);

		// 該当する物件情報が取得できない場合は処理中断
		if (housingInfo == null) {
			log.warn("Selected HousingInfo Is Null [sysHousingCd = " + sysHousingCd + "]");
			return;
		}

		FavoriteInfo favoriteInfo = null;
		// 登録日、最終更新日の値
		Date sysDate = new Date();

		if (favoriteInfos == null || favoriteInfos.isEmpty()) {
			// 新規登録

			favoriteInfo = buildFavoriteInfo();
			favoriteInfo.setSysHousingCd(sysHousingCd);
			favoriteInfo.setUserId(userId);
			favoriteInfo.setDisplayHousingName(housingInfo.getDisplayHousingName());
			favoriteInfo.setInsDate(sysDate);
			favoriteInfo.setInsUserId(userId);
			favoriteInfo.setUpdDate(sysDate);
			favoriteInfo.setUpdUserId(userId);

			this.favoriteInfoDAO.insert(new FavoriteInfo[] { favoriteInfo });

			log.debug("Add FavoriteInfo [userId = " + userId + ", sysHousingCd = " + sysHousingCd + "]");

		} else if (favoriteInfos.size() == 1) {
			// 更新

			favoriteInfo = favoriteInfos.get(0);
			// 更新時点の表示用物件名を再設定
			favoriteInfo.setDisplayHousingName(housingInfo.getDisplayHousingName());
			favoriteInfo.setUpdDate(sysDate);
			favoriteInfo.setUpdUserId(userId);

			this.favoriteInfoDAO.update(new FavoriteInfo[] { favoriteInfo });

			log.debug("Updata FavoriteInfo [userId = " + userId + ", sysHousingCd = " + sysHousingCd + "]");

		}
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

		// パラメタチェック
		if (StringValidateUtil.isEmpty(userId)) {
			log.warn("userId Is Null or Empty [userId = " + userId + "]");
			return;
		}
		if (StringValidateUtil.isEmpty(sysHousingCd)) {
			log.warn("sysHousingCd Is Null or Empty [sysHousingCd = " + sysHousingCd + "]");
			return;
		}

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);
		criteria.addWhereClause("userId", userId);
		this.favoriteInfoDAO.deleteByFilter(criteria);

		log.debug("Deleted FavoriteInfo [userId = " + userId + ", sysHousingCd = " + sysHousingCd + "]");

	}

	/**
	 * お気に入り情報を検索し、結果リストを復帰する。<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、お気に入り情報を検索する。<br/>
	 * 検索結果は Form オブジェクトに格納され、取得した該当件数を戻り値として復帰する。<br/>
	 * 物件情報に存在しないお気に入り情報は削除した上で、検索結果を取得する<br/>
	 * <br/>
	 * @param userId マイページのユーザーID
	 * @param searchForm 検索条件、および、検索結果の格納オブジェクト
	 * 
	 * @return 取得件数
	 * 
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	@Override
	public int searchFavorite(String userId, FavoriteSearchForm searchForm) throws Exception {

		// パラメタチェック
		if (StringValidateUtil.isEmpty(userId)) {
			log.warn("userId Is Null or Empty [userId = " + userId + "]");
			return 0;
		}
		if (searchForm == null) {
			log.warn("FavoriteSearchForm Is Null");
			return 0;
		}

		// 紐付く物件情報が存在しない場合、該当のお気に入り情報を削除する
		DAOCriteria criteria = new DAOCriteria();
		if (!StringValidateUtil.isEmpty(searchForm.getSysHousingCd())) {
			criteria.addWhereClause("sysHousingCd", searchForm.getSysHousingCd());
		}
		criteria.addWhereClause("userId", userId);

		List<FavoriteInfo> favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);

		for (FavoriteInfo favoriteInfo : favoriteInfos) {
			HousingInfo housingInfo = this.housingInfoDAO.selectByPK(favoriteInfo.getSysHousingCd());
			if (housingInfo == null) {
				delFavorite(userId, favoriteInfo.getSysHousingCd());
			}
		}

		// 検索条件の生成
		DAOCriteria pagingCriteria = searchForm.buildCriteria();
		pagingCriteria.addWhereClause("userId", userId);

		// 物件基本情報を結合した状態で、改めてお気に入り情報の検索
		// ユーザIDの昇順、最終更新日の降順でソート
		pagingCriteria.addOrderByClause("favoriteInfo", "userId", true);
		pagingCriteria.addOrderByClause("favoriteInfo", "updDate", false);

		List<JoinResult> results;
		try {
			results = this.favoriteListDAO.selectByFilter(pagingCriteria);
		} catch (NotEnoughRowsException err) {
			int pageNo = (err.getMaxRowCount() - 1) / searchForm.getRowsPerPage() + 1;
			log.warn("resetting page to " + pageNo);
			searchForm.setSelectedPage(pageNo);

			// criteriaを作り直し
			pagingCriteria = searchForm.buildCriteria();
			pagingCriteria.addWhereClause("userId", userId);
			pagingCriteria.addOrderByClause("favoriteInfo", "userId", true);
			pagingCriteria.addOrderByClause("favoriteInfo", "updDate", false);

			results = this.favoriteListDAO.selectByFilter(pagingCriteria);
		}

		// 検索結果の格納、件数の返却
		searchForm.setRows(results);
		return results.size();

		
	}

	/**
	 * 引数で渡された userId に該当するお気に入り情報の件数を取得する。<br/>
	 * <br/>
	 * 
	 * @param userId マイページのユーザーID
	 * 
	 * @return お気に入り登録した物件の件数
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	@Override
	public int getFavoriteCnt(String userId) throws Exception {

		// パラメタチェック
		if (StringValidateUtil.isEmpty(userId)) {
			log.warn("userId Is Null or Empty [userId = " + userId + "]");
			return 0;
		}

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("userId", userId);
		List<FavoriteInfo> favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);

		if (favoriteInfos == null) {
			return 0;
		} else {
			return favoriteInfos.size();
		}

	}

	/**
	 * お気に入り情報用のバリーオブジェクトを作成するファクトリーメソッド<br/>
	 * <br/>
	 * @return FavoriteInfo を継承したお気に入り情報オブジェクト
	 */
	protected FavoriteInfo buildFavoriteInfo() {

		// 重要
		// もしお気に入り情報テーブルを FavoriteInfo 以外のオブジェクトに変更した場合、
		// このメソッドを適切なバリーオブジェクトを生成する様にオーバーライドする事。

		return (FavoriteInfo) this.valueObjectFactory.getValueObject("FavoriteInfo"); 
	}

}
