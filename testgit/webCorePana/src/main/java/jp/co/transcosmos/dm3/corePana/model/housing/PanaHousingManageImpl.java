package jp.co.transcosmos.dm3.corePana.model.housing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.housing.HousingManageImpl;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingDtlForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingImgForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingSearchForm;
import jp.co.transcosmos.dm3.core.vo.AdminLoginInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.ReformManage;
import jp.co.transcosmos.dm3.corePana.model.housing.dao.PanaSearchHousingDAO;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingInfoForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingInspectionForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingStatusForm;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.HousingInspection;
import jp.co.transcosmos.dm3.corePana.vo.HousingStatusInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.dao.NotEnoughRowsException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.util.StringUtils;

/**
 * 物件情報メンテナンス用 Model クラス.
 * <p>
 * 物件情報を操作する model クラスはこのインターフェースを実装する事。<br/>
 * <p>
 *
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * TRANS		2015.03.16	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスはシングルトンで DI コンテナに定義されるので、スレッドセーフである事。<br/>
 *
 */
public class PanaHousingManageImpl extends HousingManageImpl {

	/** リフォーム情報を管理用 Model */
	private ReformManage reformManager;

	/** 物件ステータス情報 **/
	private DAO<jp.co.transcosmos.dm3.core.vo.HousingStatusInfo> housingStatusInfoDAO;

	/** 物件インスペクション情報 **/
	private DAO<HousingInspection> housingInspectionDAO;

	/** 建物詳細情報 **/
	private DAO<BuildingDtlInfo> buildingDtlInfoDAO;

	/** 設備マスタ情報 **/
	private DAO<JoinResult> equipListDAO;

	/** 建物基本情報用 DAO */
	private DAO<jp.co.transcosmos.dm3.core.vo.BuildingInfo> buildingInfoDAO;

	/** 物件一覧検索用 DAO */
	private PanaSearchHousingDAO panaSearchHousingDAO;

	/** 物件画像情報DAO */
	private DAO<jp.co.transcosmos.dm3.core.vo.HousingImageInfo> HousingImageInfoDAO;

	/** 管理ユーザー情報DAO */
	private DAO<AdminLoginInfo> adminLoginInfoDAO;

	/**
	 * リフォーム情報を管理する Model を設定する。<br/>
	 * <br/>
	 *
	 * @param reformManager
	 *            リフォーム情報を管理する Model
	 */
	public void setReformManager(ReformManage reformManager) {
		this.reformManager = reformManager;
	}

	/**
	 * 物件ステータス情報 DAO を設定する。<br/>
	 * <br/>
	 *
	 * @param housingStatusInfoDAO
	 *            物件ステータス情報 DAO
	 */
	public void setHousingStatusInfoDAO(
			DAO<jp.co.transcosmos.dm3.core.vo.HousingStatusInfo> housingStatusInfoDAO) {
		this.housingStatusInfoDAO = housingStatusInfoDAO;
	}

	/**
	 * 物件インスペクション DAO を設定する。<br/>
	 * <br/>
	 *
	 * @param housingInspectionDAO
	 *            物件インスペクション DAO
	 */
	public void setHousingInspectionDAO(
			DAO<HousingInspection> housingInspectionDAO) {
		this.housingInspectionDAO = housingInspectionDAO;
	}

	/**
	 * 建物詳細情報 DAO を設定する。<br/>
	 * <br/>
	 *
	 * @param buildingDtlInfoDAO
	 *            建物詳細情報 DAO
	 */
	public void setBuildingDtlInfoDAO(DAO<BuildingDtlInfo> buildingDtlInfoDAO) {
		this.buildingDtlInfoDAO = buildingDtlInfoDAO;
	}

	/**
	 * 設備マスタ情報 DAO を設定する。<br/>
	 * <br/>
	 *
	 * @param equipMstDAO
	 *            設備マスタ情報 DAO
	 */
	public void setEquipListDAO(DAO<JoinResult> equipListDAO) {
		this.equipListDAO = equipListDAO;
	}

	/**
	 * 建物情報 DAO を設定する。<br/>
	 * <br/>
	 *
	 * @param buildingInfoDAO
	 *            建物情報 DAO
	 */
	public void setBuildingInfoDAO(
			DAO<jp.co.transcosmos.dm3.core.vo.BuildingInfo> buildingInfoDAO) {
		this.buildingInfoDAO = buildingInfoDAO;
	}

	/**
	 * 物件一覧検索用 DAO（管理サイトとフロントサイト両方） を設定する。<br/>
	 * <br/>
	 *
	 * @param panaSearchHousingDAO
	 *            物件一覧検索用 DAO
	 */
	public void setPanaSearchHousingDAO(
			PanaSearchHousingDAO panaSearchHousingDAO) {
		this.panaSearchHousingDAO = panaSearchHousingDAO;
	}

	/**
	 * 物件画像情報用 DAO を設定する。<br/>
	 * <br/>
	 *
	 * @param housingImageInfoDAO
	 *            物件画像情報用 DAO
	 */
	public void setHousingImageInfoDAO(
			DAO<jp.co.transcosmos.dm3.core.vo.HousingImageInfo> housingImageInfoDAO) {
		super.setHousingImageInfoDAO(housingImageInfoDAO);
		this.HousingImageInfoDAO = housingImageInfoDAO;
	}

	/**
	 * 管理ユーザー情報DAO を設定する。<br/>
	 * <br/>
	 *
	 * @param adminLoginInfoDAO
	 *            管理ユーザー情報DAO
	 */
	public void setAdminLoginInfoDAO(DAO<AdminLoginInfo> adminLoginInfoDAO) {
		this.adminLoginInfoDAO = adminLoginInfoDAO;
	}

	/**
	 * パラメータで渡された Form の情報で物件基本情報を新規登録する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * ※表示用アイコンは設備情報で更新するので、このメソッドでは更新されない。<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            物件基本情報の入力値を格納した Form オブジェクト
	 * @param editUserId
	 *            ログインユーザーID （更新情報用）
	 *
	 * @return 採番されたシステム物件CD
	 */
	@Override
	public String addHousing(HousingForm inputForm, String editUserId)
			throws Exception {

		return super.addHousing(inputForm, editUserId);

	}

	/**
	 * パラメータで渡された Form の情報で物件基本情報を更新する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * HousingForm の sysHousingCd プロパティに設定された値を主キー値として更新する。<br/>
	 * ※表示用アイコンは設備情報で更新するので、このメソッドでは更新されない。<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            物件基本情報の入力値を格納した Form オブジェクト
	 * @param editUserId
	 *            ログインユーザーID （更新情報用）
	 *
	 * @exception NotFoundException
	 *                更新対象が存在しない場合
	 */
	@Override
	public void updateHousing(HousingForm inputForm, String editUserId)
			throws Exception, NotFoundException {

		super.updateHousing(inputForm, editUserId);
	}

	/**
	 * パラメータで渡された Form の情報で物件基本情報を削除する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * HousingForm の sysHousingCd プロパティに設定された値を主キー値として削除する。
	 * また、削除対象レコードが存在しない場合でも正常終了として扱う事。<br/>
	 * 削除時は物件基本情報の従属表も削除対象とする。<br/>
	 * 関連する画像ファイルの削除は Proxy クラス側で対応するので、このクラス内には実装しない。<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            削除対象となる sysHousingCd を格納した Form オブジェクト
	 * @param editUserId
	 *            削除担当者
	 */
	@Override
	public void delHousingInfo(HousingForm inputForm, String editUserId) {

		// super.delHousingInfo(inputForm, editUserId);

		// 建物情報を削除する条件を生成する。
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysBuildingCd", inputForm.getSysBuildingCd());
		// 建物基本情報を削除する
		this.buildingInfoDAO.deleteByFilter(criteria);

	}

	/**
	 * パラメータで渡された Form の情報で物件詳細情報を更新する。<br/>
	 * 該当する物件詳細情報が存在しなくても、該当する物件基本情報が存在する場合、レコードを新たに 追加して物件詳細情報を登録する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * HousingDtlForm の sysHousingCd プロパティに設定された値を主キー値として更新する。<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            物件詳細情報の入力値を格納した Form オブジェクト
	 * @param editUserId
	 *            ログインユーザーID （更新情報用）
	 *
	 */
	@Override
	public void updateHousingDtl(HousingDtlForm inputForm, String editUserId) {

		super.updateHousingDtl(inputForm, editUserId);

	}

	/**
	 * 設備CD から表示用アイコン情報を更新する。<br/>
	 * このメソッドは設備情報の更新処理内から実行され、入力した設備CD を元に表示用アイコン情報を 生成する。<br/>
	 * 表示用アイコン情報を別の源泉から作成する場合、何も処理しない様にこのメソッドをオーバーライド する必要がある。<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 * @param equipCds
	 */
	@Override
	protected void equipToiconData(String sysHousingCd, String[] equipCds,
			String editUserId) {

	}

	/**
	 * パラメータで渡された Form の情報で物件画像情報を新規登録する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * <br/>
	 * 関連する画像ファイルの公開処理やサムネイルの作成は Proxy クラス側で対応するので、 このクラス内では対応しない。<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            物件画像情報の入力値を格納した Form オブジェクト
	 * @param editUserId
	 *            ログインユーザーID （更新情報用）
	 *
	 * @return 新たに追加した画像情報のリスト
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	@Override
	public List<jp.co.transcosmos.dm3.core.vo.HousingImageInfo> addHousingImg(
			HousingImgForm inputForm, String editUserId) throws Exception {

		return super.addHousingImg(inputForm, editUserId);
	}

	/**
	 * パラメータで渡されたシステム物件CD、画像タイプ、枝番で物件画像情報を削除する。<br/>
	 * もし削除フラグに 1 が設定されている場合は更新せずに削除処理する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * <br/>
	 * 関連する画像ファイルの削除は、Proxy クラス側で対応するので、このクラス内では対応しない。<br/>
	 * また、更新処理は、画像パス、画像ファイル名の更新はサポートしていない。<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            システム物件CD
	 * @param imageType
	 *            物件画像タイプ
	 * @param divNo
	 *            枝番
	 * @param editUserId
	 *            ログインユーザーID （更新情報用）
	 *
	 * @return 削除が発生した画像情報のリスト
	 */
	@Override
	public List<jp.co.transcosmos.dm3.core.vo.HousingImageInfo> updHousingImg(
			HousingImgForm inputForm, String editUserId) {

		// return super.updHousingImg(inputForm, editUserId);

		// 戻り値となる、削除が発生した物件画像情報のリスト
		List<HousingImageInfo> delImgList = new ArrayList<>();

		// メイン画像フラグをリセットし、システム物件CD 単位で物件画像情報をロックする。
		lockAndRestFlag(inputForm.getSysHousingCd());

		String[] divNo = inputForm.getDivNo();
		// 画像ファイルを基準に件数分ループする。
		for (int idx = 0; idx < inputForm.getFileName().length; ++idx) {

			// 削除フラグが設定されている場合は削除処理へ
			if ("1".equals(inputForm.getDelFlg()[idx])) {

				// 物件画像情報を削除
				HousingImageInfo imgInfo = delHousingImg(
						inputForm.getSysHousingCd(),
						inputForm.getImageType()[idx],
						Integer.valueOf(inputForm.getDivNo()[idx]));
				// 実際に削除する情報が無かった場合、 null が復帰されるので、その場合はリストに追加しない。
				if (imgInfo != null) {
					delImgList.add(imgInfo);
				}

			} else {
				// 条件を生成する。
				DAOCriteria criteria = new DAOCriteria();
				criteria.addWhereClause("sysHousingCd",
						inputForm.getSysHousingCd());
				criteria.addWhereClause("imageType",
						inputForm.getOldImageType()[idx]);
				criteria.addWhereClause("divNo",
						Integer.valueOf(inputForm.getDivNo()[idx]));

				List<HousingImageInfo> imgInfoList = this.HousingImageInfoDAO
						.selectByFilter(criteria);
				HousingImageInfo imgInfo = imgInfoList.get(0);


				// 条件を生成する。
				DAOCriteria criteriaNew = new DAOCriteria();
				criteriaNew.addWhereClause("sysHousingCd",
						inputForm.getSysHousingCd());
				criteriaNew.addWhereClause("imageType",
						inputForm.getImageType()[idx]);
				criteriaNew.addOrderByClause("divNo", false);
				List<HousingImageInfo> newImgInfoList = this.HousingImageInfoDAO
						.selectByFilter(criteriaNew);

				if(newImgInfoList ==null || newImgInfoList.size()==0){
					divNo[idx]="1";
				}else{
					HousingImageInfo newImgInfo = newImgInfoList.get(0);
					divNo[idx] =String.valueOf(newImgInfo.getDivNo()+1);
				}
				inputForm.setDivNo(divNo);
				// Form から UpdateExpression を生成してデータを更新する。
				this.HousingImageInfoDAO.updateByCriteria(criteria,
						inputForm.buildUpdateExpression(idx));
				if (!((jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingImageInfoForm) inputForm)
						.getOldRoleId()[idx]
						.equals(((jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingImageInfoForm) inputForm)
								.getRoleId()[idx])) {
					delImgList.add(imgInfo);
				}

			}
		}

		// メイン画像フラグ、枝番を更新する。
		updateMainFlgAndDivNo(inputForm.getSysHousingCd());

		// 物件基本情報のタイムスタンプを更新
		updateEditTimestamp(inputForm.getSysHousingCd(), editUserId);

		return delImgList;
	}

	/**
	 * パラメータで渡されたシステム物件CD、画像タイプ、枝番で物件画像情報を削除する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * 更新者ID を省略したこのメソッド場合、メイン画像フラグ、枝番、物件基本情報のタイムスタンプは更新されない。 <br/>
	 * 関連する画像ファイルの削除は、Proxy クラス側で対応するので、このクラス内では対応しない。<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            システム物件CD
	 * @param imageType
	 *            物件画像タイプ
	 * @param divNo
	 *            枝番
	 *
	 * @return 削除が発生した画像情報
	 *
	 */
	protected jp.co.transcosmos.dm3.core.vo.HousingImageInfo delHousingImg(
			String sysHousingCd, String imageType, int divNo) {

		return super.delHousingImg(sysHousingCd, imageType, divNo);
	}

	/**
	 * パラメータで渡されたシステム物件CD、画像タイプ、枝番で物件画像情報を削除する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * 削除後、メイン画像フラグと物件基本情報のタイムスタンプ情報を更新する。<br/>
	 * <br/>
	 * 関連する画像ファイルの削除は、Proxy クラス側で対応するので、このクラス内では対応しない。<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            システム物件CD
	 * @param imageType
	 *            物件画像タイプ
	 * @param divNo
	 *            枝番
	 * @param editUserId
	 *            ログインユーザーID （更新情報用）
	 *
	 * @return 削除が発生した画像情報
	 */
	@Override
	public jp.co.transcosmos.dm3.core.vo.HousingImageInfo delHousingImg(
			String sysHousingCd, String imageType, int divNo, String editUserId) {

		return super.delHousingImg(sysHousingCd, imageType, divNo, editUserId);
	}

	/**
	 * 物件画像情報のパス名に格納する値を取得する。<br/>
	 * <br/>
	 *
	 * @param housing
	 *            更新対象となる物件オブジェクト
	 *
	 * @return 加工されたパス名
	 */
	protected String createImagePath(Housing housing) {

		return super.createImagePath(housing);
	}

	/**
	 * 部件番号、画像タイプ毎に、最大の枝番を取得する。<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            システム物件CD
	 * @param imageType
	 *            画像タイプ
	 */
	protected int getMaxDivNo(String sysHousingCd, String imageType) {

		return super.getMaxDivNo(sysHousingCd, imageType);
	}

	/**
	 * システム物件CD 単位に物件画像情報の行ロックを取得する。<br/>
	 * また、その際、メイン画像フラグのリセットを行う。<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            システム物件CD
	 */
	protected void lockAndRestFlag(String sysHousingCd) {

		super.lockAndRestFlag(sysHousingCd);

	}

	/**
	 * 物件画像情報のメイン画像フラグ、および枝番の更新を行う<br/>
	 * 画像種別毎に一番最初に表示する物件画像情報に対してメイン画像フラグを設定する。<br/>
	 * また、画像タイプ毎に表示順で枝番を更新する。<br/>
	 * <br/>
	 *
	 * @param getSysHousingCd
	 *            システム物件CD
	 */
	protected void updateMainFlgAndDivNo(String sysHousingCd) {
		super.updateMainFlgAndDivNo(sysHousingCd);
	}

	/**
	 * 物件情報（一部、建物情報）を検索し、結果リストを復帰する。（一覧用）<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、物件情報を検索する。<br/>
	 * 検索結果は Form オブジェクトに格納され、取得した該当件数を戻り値として復帰する。<br/>
	 * <br/>
	 * このメソッドを使用した場合、暗黙の抽出条件（例えば、非公開物件の除外など）が適用される。<br/>
	 * よって、フロント側は基本的にこのメソッドを使用する事。<br/>
	 * <br/>
	 *
	 * @param searchForm
	 *            検索条件、および、検索結果の格納オブジェクト
	 * @param full
	 *            false の場合、公開対象外を除外する。　true の場合は除外しない
	 *
	 * @return 該当件数
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	@Override
	public int searchHousing(HousingSearchForm searchForm) throws Exception {
		return super.searchHousing(searchForm);
	}

	/**
	 * 物件情報（一部、建物情報）を検索し、結果リストを復帰する。（一覧用）<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、物件情報を検索する。<br/>
	 * 検索結果は Form オブジェクトに格納され、取得した該当件数を戻り値として復帰する。<br/>
	 * <br/>
	 *
	 * @param searchForm
	 *            検索条件、および、検索結果の格納オブジェクト
	 * @param full
	 *            false の場合、公開対象外を除外する。　true の場合は除外しない
	 *
	 * @return 該当件数
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	@Override
	public int searchHousing(HousingSearchForm searchForm, boolean full)
			throws Exception {
		// return super.searchHousing(searchForm, full);

		PanaHousingSearchForm panaSearchForm = (PanaHousingSearchForm) searchForm;

		// 検索処理
		List<Housing> housingList = null;
		try {
			housingList = this.panaSearchHousingDAO
					.panaSearchHousing(panaSearchForm);
		} catch (NotEnoughRowsException err) {
			// 範囲外のページが設定された場合、再検索
			int pageNo = (err.getMaxRowCount() - 1)
					/ panaSearchForm.getRowsPerPage() + 1;
			panaSearchForm.setSelectedPage(pageNo);
			housingList = this.panaSearchHousingDAO
					.panaSearchHousing(panaSearchForm);
		}
		panaSearchForm.setRows(housingList);

		return housingList.size();
	}

	/**
	 * リクエストパラメータで渡されたシステム物件CD （主キー値）に該当する物件情報を復帰する。<br/>
	 * もし該当データが見つからない場合、null を復帰する。<br/>
	 * <br/>
	 * このメソッドを使用した場合、暗黙の抽出条件（例えば、非公開物件の除外など）が適用される。<br/>
	 * よって、フロント側は基本的にこのメソッドを使用する事。<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            システム物件CD
	 * @param full
	 *            false の場合、公開対象外を除外する。　true の場合は除外しない
	 *
	 * @return　DB から取得したバリーオブジェクトを格納したコンポジットクラス
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	@Override
	public Housing searchHousingPk(String sysHousingCd) throws Exception {
		return searchHousingPk(sysHousingCd, false);
	}

	/**
	 * リクエストパラメータで渡されたシステム物件CD （主キー値）に該当する物件情報を復帰する。<br/>
	 * もし該当データが見つからない場合、null を復帰する。<br/>
	 * <br/>
	 * ※基本的に、パラメータ full を true にして使用するのは管理画面のみ。<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            システム物件CD
	 * @param full
	 *            false の場合、公開対象外を除外する。　true の場合は除外しない
	 *
	 * @return　DB から取得したバリーオブジェクトを格納したコンポジットクラス
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	@Override
	public Housing searchHousingPk(String sysHousingCd, boolean full)
			throws Exception {

		PanaHousing panaHousing = (PanaHousing) super.searchHousingPk(
				sysHousingCd, full);

		if (panaHousing != null) {
			// 物件インスペクションのリストを設定する
			panaHousing.setHousingInspections(this
					.searchHousingInspection(sysHousingCd));

			// リフォームプラン情報を検索
			List<ReformPlan> reformPlanList = this.reformManager
					.searchReformPlan(sysHousingCd, full);

			List<Map<String, Object>> reforms = new ArrayList<Map<String, Object>>();
			// リフォームの情報を設定する
			for (ReformPlan rp : reformPlanList) {
				Map<String, Object> reformMap = this.reformManager
						.searchReform(rp.getSysReformCd());
				reforms.add(reformMap);
			}
			panaHousing.setReforms(reforms);

			JoinResult housingResult = panaHousing.getHousingInfo();
			HousingInfo housingInfo = (HousingInfo) housingResult.getItems().get(
					"housingInfo");
			if (!StringUtils.isEmpty(housingInfo.getUpdUserId())) {
				DAOCriteria criteria = new DAOCriteria();
				criteria.addWhereClause("adminUserId", housingInfo.getUpdUserId());

				List<AdminLoginInfo> adminLoginInfoList = this.adminLoginInfoDAO
						.selectByFilter(criteria);

				if (adminLoginInfoList != null && adminLoginInfoList.size() > 0) {
					panaHousing.setHousingInfoUpdUser(adminLoginInfoList.get(0));
				}
			}
		}

		return panaHousing;
	}

	/**
	 * 物件詳細情報と １対１の関係にあるメインの関連テーブルの情報を取得し、Housing オブジェクトへ設定する。<br/>
	 * <br/>
	 *
	 * @param housing
	 *            値の設定先となる Housing オブジェクト
	 * @param sysHousingCd
	 *            取得対象システム物件CD
	 * @param full
	 *            false の場合、公開対象外を除外する。　true の場合は除外しない
	 *
	 * @return 取得結果 （該当なしの場合、null）
	 * @throws Exception
	 *             委譲先がスローする例外
	 */
	protected JoinResult confMainData(Housing housing, String sysHousingCd,
			boolean full) throws Exception {

		return super.confMainData(housing, sysHousingCd, full);
	}

	/**
	 * 物件画像情報を取得する。<br/>
	 * 取得した結果を housing オブジェクトへ格納する。<br/>
	 * <br/>
	 *
	 * @param housing
	 *            値の設定先となる Housing オブジェクト
	 * @param sysHousingCd
	 *            取得対象システム物件CD
	 *
	 * @return 取得結果
	 */
	@Override
	protected List<HousingImageInfo> confHousingImage(Housing housing,
			String sysHousingCd) {

		// コアと違うのはソート順です！
		// 物件画像情報を取得する。
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);
		criteria.addOrderByClause("sortOrder");
		criteria.addOrderByClause("imageType");
		criteria.addOrderByClause("divNo");
		List<HousingImageInfo> list = this.HousingImageInfoDAO
				.selectByFilter(criteria);

		housing.setHousingImageInfos(list);

		return list;

	}

	/**
	 * 物件設備情報を取得する。<br/>
	 * 取得結果は、LinkedHashMap に変換して housing へ格納される。<br/>
	 * ・Key = 設備CD<br/>
	 * ・Value = 設備マスタ<br/>
	 * <br/>
	 *
	 * @param housing
	 *            値の設定先となる Housing オブジェクト
	 * @param sysHousingCd
	 *            取得対象システム物件CD
	 *
	 * @return 取得結果
	 */
	protected Map<String, jp.co.transcosmos.dm3.core.vo.EquipMst> confHousingEquip(
			Housing housing, String sysHousingCd) {

		return super.confHousingEquip(housing, sysHousingCd);
	}

	/**
	 * 物件拡張属性情報に格納されている情報を Map オブジェクトに変換して格納する。<br/>
	 * Map オブジェクトの構成は、以下の通り。<br/>
	 * ・Key = 物件拡張情報のカテゴリ名（category） ・Value = カテゴリの該当する、Key値が設定された Map
	 * オブジェクト（Key = keyName列、Value = dataValue列） <br/>
	 *
	 * @param housing
	 *            格納先となる Housing オブジェクト
	 * @param sysHousingCd
	 *            取得対象システム物件CD
	 *
	 */
	protected void confHousingExtInfo(Housing housing, String sysHousingCd) {

		super.confHousingExtInfo(housing, sysHousingCd);

	}

	/**
	 * パラメータで渡された Map の情報で物件拡張属性情報を更新する。（システム物件CD 単位）<br/>
	 * 更新は、Delete & Insert で処理する。<br/>
	 * <br/>
	 * inputData の Map の構成は以下の通り。<br/>
	 * ・key = カテゴリ名 （拡張属性情報の、category に格納する値） ・value = 値が格納された Map オブジェクト
	 * inputData の value に格納される Map の構成は以下の通り。<br/>
	 * ・key = Key名 （拡張属性情報の、key_name に格納する値） ・value = 入力値 （拡張属性情報の、data_value
	 * に格納する値） <br/>
	 *
	 * @param sysHousingCd
	 *            更新対象システム物件CD
	 * @param inputData
	 *            登録情報となる Map オブジェクト
	 * @param editUserId
	 *            ログインユーザーID （更新情報用）
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 * @exception NotFoundException
	 *                親となる物件基本情報が存在しない場合
	 */
	@Override
	public void updExtInfo(String sysHousingCd,
			Map<String, Map<String, String>> inputData, String editUserId)
			throws Exception, NotFoundException {

		super.updExtInfo(sysHousingCd, inputData, editUserId);
	}

	/**
	 * パラメータで渡されたシステム物件CD に該当する物件拡張属性情報を削除する。（システム物件CD 単位）<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            削除対象システム物件CD
	 * @param editUserId
	 *            ログインユーザーID （更新情報用）
	 *
	 */
	@Override
	public void delExtInfo(String sysHousingCd, String editUserId) {

		super.delExtInfo(sysHousingCd, editUserId);

	}

	/**
	 * パラメータで渡された Map の情報で物件拡張属性情報を更新する。（カテゴリー 単位）<br/>
	 * 更新は、Delete & Insert で処理する。<br/>
	 * <br/>
	 * inputData の Map の構成は以下の通り。<br/>
	 * ・key = Key名 （拡張属性情報の、key_name に格納する値） ・value = 入力値 （拡張属性情報の、data_value
	 * に格納する値） <br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            更新対象システム物件CD
	 * @param category
	 *            更新対象カテゴリ名
	 * @param inputData
	 *            登録情報となる Map オブジェクト
	 * @param editUserId
	 *            ログインユーザーID （更新情報用）
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 * @exception NotFoundException
	 *                親となる物件基本情報が存在しない場合
	 */
	@Override
	public void updExtInfo(String sysHousingCd, String category,
			Map<String, String> inputData, String editUserId) throws Exception,
			NotFoundException {

		super.updExtInfo(sysHousingCd, category, inputData, editUserId);

	}

	/**
	 * パラメータで渡されたシステム物件CD に該当する物件拡張属性情報を削除する。（カテゴリー 単位）<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            削除対象システム物件CD
	 * @param category
	 *            削除対象カテゴリ名
	 * @param editUserId
	 *            ログインユーザーID （更新情報用）
	 *
	 */
	@Override
	public void delExtInfo(String sysHousingCd, String category,
			String editUserId) {

		super.delExtInfo(sysHousingCd, category, editUserId);

	}

	/**
	 * パラメータで渡された Map の情報で物件拡張属性情報を更新する。（Key 単位）<br/>
	 * 更新は、Delete & Insert で処理する。<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            更新対象システム物件CD
	 * @param category
	 *            更新対象カテゴリ名
	 * @param key
	 *            更新対象Key
	 * @param value
	 *            更新する値
	 * @param editUserId
	 *            ログインユーザーID （更新情報用）
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 * @exception NotFoundException
	 *                親となる物件基本情報が存在しない場合
	 */
	@Override
	public void updExtInfo(String sysHousingCd, String category, String key,
			String value, String editUserId) throws Exception,
			NotFoundException {

		super.updExtInfo(sysHousingCd, category, key, value, editUserId);

	}

	/**
	 * パラメータで渡されたシステム物件CD に該当する物件拡張属性情報を削除する。（キー 単位）<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            削除対象システム物件CD
	 * @param category
	 *            削除対象カテゴリ名
	 * @param category
	 *            削除対象 Key
	 * @param editUserId
	 *            ログインユーザーID （更新情報用）
	 *
	 */
	@Override
	public void delExtInfo(String sysHousingCd, String category, String key,
			String editUserId) {

		super.delExtInfo(sysHousingCd, category, key, editUserId);

	}

	/**
	 * 物件基本情報のタイムスタンプ情報を更新する。<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            更新対象システム物件CD
	 * @param editUserId
	 *            更新者ID
	 */
	protected void updateEditTimestamp(String sysHousingCd, String editUserId) {

		super.updateEditTimestamp(sysHousingCd, editUserId);

	}

	/**
	 * 非公開となる条件を引数で渡された検索条件オブジェクトに設定する。<br/>
	 * このメソッドは、searchHousingPk()、searchBuilding() の full パラメータが false の場合に
	 * 実行される。<br/>
	 * <br/>
	 * デフォルト仕様として抽出対象になるには以下の条件を満たす必要がある。<br/>
	 * <ul>
	 * <li>公開物件である事。（非公開フラグ hidden_flg = 1 の物件ステータス情報が存在しない事。）</li>
	 * </ul>
	 * <br/>
	 * 条件をカスタマイズする場合は、このメソッドをオーバーライドする事。<br/>
	 * <br/>
	 *
	 * @param criteria
	 *            検索で仕様する検索オブジェクト
	 *
	 */
	protected void addNegativeFilter(DAOCriteria criteria) {

		super.addNegativeFilter(criteria);

	}

	/**
	 * 物件情報オブジェクトのインスタンスを生成する。<br/>
	 * もし、カスタマイズで物件情報を構成するテーブルを追加した場合、このメソッドをオーバーライドする事。<br/>
	 * <br/>
	 *
	 * @return PanaHousing のインスタンス
	 */
	public PanaHousing createHousingInstace() {
		return new PanaHousing();
	}

	/**
	 * ステータスの新規を行う<br/>
	 * <br/>
	 *
	 * @param form
	 *            ステータスの入力値を格納した Form オブジェクト
	 *
	 */
	public String addHousingStatus(PanaHousingStatusForm form)
			throws Exception {

		// 新規登録処理の場合、入力フォームの値を設定するバリーオブジェクトを生成する。
		// バリーオブジェクトは、ファクトリーメソッド以外では生成しない事。
		// （継承されたバリーオブジェクトが使用されなくなる為。）
		HousingStatusInfo housingStatusInfo = new HousingStatusInfo();

		// フォームの入力値をバリーオブジェクトに設定する。
		form.copyToHousingStatusInfo(housingStatusInfo);

		this.housingStatusInfoDAO
				.insert(new HousingStatusInfo[] { housingStatusInfo });

		return housingStatusInfo.getSysHousingCd();
	}

	/**
	 * ステータスの更新を行う<br/>
	 * <br/>
	 *
	 * @param form
	 *            ステータスの入力値を格納した Form オブジェクト
	 * @param editUserId
	 *            ログインユーザーID （更新情報用）
	 * @exception NotFoundException
	 *                更新対象が存在しない場合
	 */
	public void updateHousingStatus(PanaHousingStatusForm form,
			String editUserId) throws Exception, NotFoundException {
		// 更新処理の場合、更新対象データを取得する。
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", form.getSysHousingCd());

		HousingStatusInfo housingStatusInfo = (HousingStatusInfo) this.housingStatusInfoDAO
				.selectByPK(form.getSysHousingCd());

		// 該当するデータが存在しない場合は、例外をスローする。
		if (housingStatusInfo == null) {
			throw new NotFoundException();
		}

		// リフォームプラン情報を取得し、入力した値で上書きする。
		form.copyToHousingStatusInfo(housingStatusInfo);

		// リフォームプラン情報の更新
		this.housingStatusInfoDAO
				.update(new HousingStatusInfo[] { housingStatusInfo });

		// 物件情報のタイムスタンプを更新
		updateEditTimestamp(form.getSysHousingCd(), editUserId);
	}

	/**
	 * パラメータで渡されたシステム物件CD に該当する住宅診断情報を削除する。（システム物件CD 単位）<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            削除対象システム物件CD
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	public void delHousingInspection(String sysHousingCd) throws Exception {

		// 削除条件生成
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);
		this.housingInspectionDAO.deleteByFilter(criteria);

	}

	/**
	 * 住宅診断情報を新規追加する<br/>
	 * <br/>
	 *
	 * @param PanaHousingInspectionForm
	 *            入力値を格納した Form オブジェクト
	 * @param idx
	 *            入力値を格納した Form オブジェクト
	 *
	 */
	public String addHousingInspection(PanaHousingInspectionForm form, int idx)
			throws Exception {

		// フォームの入力値をバリーオブジェクトに設定する。
		HousingInspection housingInspection = new HousingInspection();
		form.copyToHousingInspection(housingInspection, idx);

		this.housingInspectionDAO
				.insert(new HousingInspection[] { housingInspection });

		return housingInspection.getSysHousingCd();
	}

	/**
	 * 住宅診断情報を取得する。<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            システム物件CD
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	public List<HousingInspection> searchHousingInspection(String sysHousingCd)
			throws Exception {

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);

		List<HousingInspection> housingInspection = this.housingInspectionDAO
				.selectByFilter(criteria);

		return housingInspection;
	}

	/**
	 * パラメータで渡された 建物詳細情報を更新する。<br/>
	 * <br/>
	 *
	 * @param PanaHousingInfoForm
	 *            建物詳細情報
	 * @param editUserId
	 *            ユーザID
	 *
	 */
	public void updateBuildingDtlInfo(PanaHousingInfoForm inputForm,
			String editUserId) {
		// 更新処理の場合、更新対象データを取得する。
		BuildingDtlInfo buildingDtlInfo = this.buildingDtlInfoDAO
				.selectByPK(inputForm.getSysBuildingCd());

		// 該当するデータが存在しない場合は、例外をスローする。
		if (buildingDtlInfo == null) {
			BuildingDtlInfo newBuildingDtlInfo = inputForm.newToBuildingDtlInfo();
			// 取得した主キー値でリフォームプラン情報を登録
			try {
				this.buildingDtlInfoDAO
						.insert(new BuildingDtlInfo[] { newBuildingDtlInfo });
			} catch (DataIntegrityViolationException e) {
				// もし、新規登録時に親レコードが存在しない場合は例外をスローする。
				// 物件詳細情報のみ存在するケースはDBの制約上有り得ないので、新規登録時のみ制御する。
				e.printStackTrace();
				throw new NotFoundException();
			}
		} else {
			// 更新対象データが取得出来た場合は更新する。

			// 取得したバリーオブジェクトに Form の値を設定する。
			inputForm.copyToBuildingDtlInfo(buildingDtlInfo);
			//
			this.buildingDtlInfoDAO
					.update(new BuildingDtlInfo[] { buildingDtlInfo });
		}

		// 物件情報のタイムスタンプを更新
		updateEditTimestamp(inputForm.getSysHousingCd(), editUserId);

	}

	/**
	 * リクエストパラメータで渡された物件種類CD （主キー値）に該当する設備マスタ情報を復帰する。<br/>
	 * もし該当データが見つからない場合、null を復帰する。<br/>
	 * <br/>
	 *
	 * @param housingKindCd
	 *            物件種類CD
	 *
	 * @return　DB から取得した設備マスタ情報を格納したリスト
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	public List<JoinResult> searchEquipMst(String housingKindCd)
			throws Exception {

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("housingKindCd", housingKindCd);
		criteria.addOrderByClause("sortOrder");

		List<JoinResult> equipMstList = this.equipListDAO
				.selectByFilter(criteria);

		return equipMstList;
	}

	/**
	 * CSV出力情報を検索し、結果リストを復帰する。<br/>
	 * 引数で渡された searchForm パラメータの値で検索条件を生成し、CSV出力情報を検索する。<br/>
	 * 検索結果は searchForm オブジェクトに格納され、取得した該当件数を戻り値として復帰する。<br/>
	 * <br/>
	 *
	 * @param searchForm
	 *            検索条件、および、検索結果の格納オブジェクト
	 *
	 * @return 該当件数
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	public void searchCsvHousing(PanaHousingSearchForm searchForm,
			HttpServletResponse response,
			PanaHousingPartThumbnailProxy panaHousingManager, PanaCommonManage panamCommonManager) throws Exception {
		this.panaSearchHousingDAO.panaSearchHousing(searchForm, response,
				panaHousingManager, panamCommonManager);
	}

	/**
	 * 市区町村値を取得する。<br/>
	 * <br/>
	 *
	 * @param housing
	 *            更新対象となる物件オブジェクト
	 *
	 * @return 加工されたパス名
	 * @throws Exception
	 */
	public int searchHousingInfo(PanaHousingSearchForm areaForm)
			throws Exception {

		List<Housing> housingCount = this.panaSearchHousingDAO.panaSearchHousing(areaForm);

		return housingCount.size();
	}

}
