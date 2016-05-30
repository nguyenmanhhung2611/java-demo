package jp.co.transcosmos.dm3.core.model.feature;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.FeatureManage;
import jp.co.transcosmos.dm3.core.model.HousingManage;
import jp.co.transcosmos.dm3.core.model.feature.form.FeatureSearchForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingFormFactory;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingSearchForm;
import jp.co.transcosmos.dm3.core.model.information.InformationManageImpl;
import jp.co.transcosmos.dm3.core.vo.FeaturePageInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.utils.ReflectionUtils;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 特集情報用 Model クラス.
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * T.Nakamura	2015.04.13	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスはシングルトンで DI コンテナに定義されるので、スレッドセーフである事。<br/>
 *
 */
public class FeatureManageImpl implements FeatureManage {

	private static final Log log = LogFactory.getLog(InformationManageImpl.class);

	/** 特集ページ情報 */
	protected DAO<FeaturePageInfo> featurePageInfoDAO;
	/** 特集一覧取得DAO */
	protected DAO<JoinResult> featureListDAO;

	/** 物件情報Model（物件リクエストに該当する物件情報取得に使用） */
	protected HousingManage housingManager;
	/** 物件情報FormFactory（物件リクエストに該当する物件情報取得に使用） */
	protected HousingFormFactory housingFormFactory;

	// DI seeter START
	public void setFeaturePageInfoDAO(DAO<FeaturePageInfo> featurePageInfoDAO) {
		this.featurePageInfoDAO = featurePageInfoDAO;
	}

	public void setFeatureListDAO(DAO<JoinResult> featureListDAO) {
		this.featureListDAO = featureListDAO;
	}

	public void setHousingManager(HousingManage housingManager) {
		this.housingManager = housingManager;
	}

	public void setHousingFormFactory(HousingFormFactory housingFormFactory) {
		this.housingFormFactory = housingFormFactory;
	}
	// DI seeter END

	/**
	 * 指定されたグループＩＤで特集情報のリストを取得する。<br/>
	 * <br/>
	 * @param featureGroupId 特集グループID
	 *
	 * @return 特集ページ情報のリストオブジェクト
	 *
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	@Override
	public List<FeaturePageInfo> searchFeature(String featureGroupId) throws Exception {

		if (StringValidateUtil.isEmpty(featureGroupId)) {
			log.warn("featureGroupId Is Null or Empty [featureGroupId = " + featureGroupId + "]");
			return new ArrayList<FeaturePageInfo>();
		}

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("featureGroupId", featureGroupId);
		// 特集ページ情報の表示可否に関わる条件を追加
		criteria.addWhereClause("featurePageInfo", "displayFlg", "1", DAOCriteria.EQUALS, false);
		Date now = new Date();
		criteria.addWhereClause("featurePageInfo", "displayStartDate", now, DAOCriteria.LESS_THAN_EQUALS, false);
		criteria.addWhereClause("featurePageInfo", "displayEndDate", now, DAOCriteria.GREATER_THAN_EQUALS, false);
		// 特集グループ対応表の表示順でソート
		criteria.addOrderByClause("featureGroupPage", "sortOrder", true);
		List<JoinResult> results = featureListDAO.selectByFilter(criteria);

		List<FeaturePageInfo> featurePageInfos = new ArrayList<FeaturePageInfo>();

		for (JoinResult result : results) {
			featurePageInfos.add((FeaturePageInfo) result.getItems().get("featurePageInfo"));
		}

		return featurePageInfos;

	}

	/**
	 * 指定された検索条件（特集ID、およびページ位置）で特集に該当する物件の情報を取得する。<br/>
	 * <br/>
	 * @param searchForm 特集の検索条件（特集ID、ページ位置）
	 *
	 * @return 該当件数
	 *
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	@Override
	public int searchHousing(FeatureSearchForm searchForm) throws Exception {

		if (searchForm == null) {
			log.warn("searchForm Is Null");
			return 0;
		}

		if (StringValidateUtil.isEmpty(searchForm.getFeaturePageId())) {
			log.warn("featurePageId Is Null or Empty [featurePageId = " + searchForm.getFeaturePageId() + "]");
			return 0;
		}

		FeaturePageInfo featurePageInfo = featurePageInfoDAO.selectByPK(searchForm.getFeaturePageId());

		if (featurePageInfo == null) {
			log.warn("Selected FeaturePageInfo Is Null [featurePageId = " + searchForm.getFeaturePageId() + "]");
			return 0;
		}

		// 物件検索Formの生成
		HousingSearchForm housingSearchForm = createHousingSearchForm(featurePageInfo);

		// 特集情報検索Formが保持するPagingListFormのプロパティ値を物件情報検索Formにコピーする
		housingSearchForm.setRowsPerPage(searchForm.getRowsPerPage());
		housingSearchForm.setVisibleNavigationPageCount(searchForm.getVisibleNavigationPageCount());
		housingSearchForm.setSelectedPage(searchForm.getSelectedPage());

		// 物件情報検索処理（HousingManageに処理を委譲）
		int cnt = this.housingManager.searchHousing(housingSearchForm);

		// 物件情報検索Formが受け取った物件一覧を物件リクエスト検索Formにコピーする
		searchForm.setRows(housingSearchForm.getRows());

		return cnt;

	}

	/**
	 * 特集ページ情報のクエリー文字列か物件検索Formを生成する<br/>
	 * <br/>
	 *
	 * @param featurePageInfo 物件ページ情報
	 * @return 物件検索Form
	 */
	protected HousingSearchForm createHousingSearchForm(FeaturePageInfo featurePageInfo) {

		HousingSearchForm housingSearchForm = housingFormFactory.createHousingSearchForm();

		// DBから取得した特集情報の検索条件文字列を取得する。
		String queryString = featurePageInfo.getQueryStrings();

		// パラメータを「&」で分割する。
		String[] params = queryString.split("&");

		// パラメタ文字列から検索条件を生成
		param : for (String param : params){

			// 分割したパラメータを、さらに「=」で分割して変数部と値部に分割する。
			String[] variableAndValue = param.split("=");

			// 「変数名」 = 「値」の構造になっている場合のみ検索条件として使用する。
			if (variableAndValue.length == 2){

				// Setter のメソッド名称
				String prefix = "Key";
				String name = prefix + variableAndValue[0].substring(0, 1).toUpperCase() + variableAndValue[0].substring(1);
				String methodName = ReflectionUtils.buildSetterName(name);

				// HousingSearchForm のメソッドのうち、パラメタ名から求めた Setter メソッド名称に一致するものを処理する
				Method[] methods = housingSearchForm.getClass().getMethods();

				for (Method method : methods) {
					if (methodName.equals(method.getName())) {

						// Setter の引数のクラス（ ＝プロパティのクラス）
						Class<?>[] parameterTypes = method.getParameterTypes();
						// 求めるのは Setter なので引数はひとつ
						if (parameterTypes == null || parameterTypes.length != 1) {
							continue param;
						}

						// 引数を取得、及び Setter の呼び出し
						Class<?> parameterType = parameterTypes[0];
						if (parameterType.isPrimitive()) {
							// 引数がプリミティブのメソッドは処理しない。
							// これは HousingSearchForm の注意事項として記載している。

							log.warn("parameter of method is primitive, is not set to property");
							continue param;

						} else {
							// ラッパークラスの場合
							// Stringを引数としてインスタンス生成できるクラスのみ対応する
							// 最低でも、String, BigDecimal, BigInteger, Byte, Double, Float, Integer, Long, Short に対応できる。

							// 引数となるクラスのコンストラクタを取得
							Constructor<?> constructor;
							try {
								constructor = parameterType.getConstructor(String.class);
							} catch (NoSuchMethodException | SecurityException e1) {

								log.warn("matching method is not found, Or can't access method.");
								log.warn("this method fails. [methodName : " + method.getName() + ", parameter : " + variableAndValue[1] + "]");
								e1.printStackTrace();
								continue param;
							}
							// 引数のインスタンスを取得
							Object[] args = { variableAndValue[1] };
							Object obj;
							try {
								obj = constructor.newInstance(args);
							} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
									| InvocationTargetException e1) {

								log.warn("initialization provoked by this method fails.");
								log.warn("this method fails. [methodName : " + method.getName() + ", parameter : " + variableAndValue[1] + "]");
								e1.printStackTrace();
								continue param;
							}

							// プロパティ値の設定
							try {
								method.invoke(housingSearchForm, obj);
							} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {

								log.warn("this method fails. [methodName : " + method.getName() + ", parameter : " + variableAndValue[1] + "]");
								e1.printStackTrace();
								continue param;
							}
						}
					}
				}

			} else {
				// パラメータに値が設定されていない場合、警告を出力する。（検索条件としては無視）

				log.warn("feature page parameter " + variableAndValue[0] + " dose not have value.");
			}
		}

		// デバッグ用ログ出力 検索用フォームの全ての getter の値を出力する。
		if (log.isDebugEnabled()) {

			String getterPrefix = "getKey";

			Method[] methods = housingSearchForm.getClass().getMethods();
			for (Method method : methods) {
				if (method.getName().startsWith(getterPrefix)) {
					// String 以外の戻り値は無視する。
					Object ret;
					try {
						ret = method.invoke(housingSearchForm, (Object[]) null);
						if (ret instanceof String) {
							log.debug("form parameter " + method.getName() + "=" + ret);
						}
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						log.debug("this method fails. [methodName : " + method.getName() + "]");
						e.printStackTrace();
						continue;
					}
				}
			}
		}

		return housingSearchForm;

	}

}
