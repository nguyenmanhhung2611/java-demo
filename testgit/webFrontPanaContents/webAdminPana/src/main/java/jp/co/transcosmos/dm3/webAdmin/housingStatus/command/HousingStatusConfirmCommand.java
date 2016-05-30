package jp.co.transcosmos.dm3.webAdmin.housingStatus.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.ReformManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousing;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingBrowse;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingStatusForm;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.vo.HousingStatusInfo;
import jp.co.transcosmos.dm3.corePana.vo.MemberInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.login.UserRoleSet;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * ステータス編集確認画面
 * リクエストパラメータで渡された管理ユーザー情報のバリデーションを行い、確認画面を表示する。
 * もしバリデーションエラーが発生した場合は入力画面として表示する。
 *
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *    ・"input" : バリデーションエラーによる再入力
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * Liu.yandong 2015.03.11	新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class HousingStatusConfirmCommand implements Command {
	/** 物件情報メンテナンス用 Model */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/** マイページユーザーの情報管理用 Model */
	private MypageUserManage mypageUserManager;

	/** リフォーム情報を管理用 Model */
	private ReformManage reformManager;

	/** 共通パラメータオブジェクト */
	private PanaCommonParameters commonParameters;

	/** 共通コード変換処理 */
	private CodeLookupManager codeLookupManager;

    /** Panasonic用ファイル処理関連共通 */
    private PanaFileUtil fileUtil;

	/**
	 * 物件情報メンテナンス用 Model（core） を設定する。<br/>
	 * <br/>
	 *
	 * @param panaHousingManager
	 *            物件情報メンテナンス用 Model（core）
	 */
	public void setPanaHousingManager(
			PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
	}

	/**
	 * マイページユーザーの情報管理用 Model（core） を設定する。<br/>
	 * <br/>
	 *
	 * @param mypageUserManager
	 *            マイページユーザーの情報管理用 Model（core）
	 */
	public void setMypageUserManager(MypageUserManage mypageUserManager) {
		this.mypageUserManager = mypageUserManager;
	}

	/**
	 * リフォーム情報を管理用 Model（core） を設定する。<br/>
	 * <br/>
	 *
	 * @param reformManager
	 *            リフォーム情報を管理用 Model（core）
	 */
	public void setReformManager(ReformManage reformManager) {
		this.reformManager = reformManager;
	}

	/**
	 * 共通パラメータオブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param commonParameters
	 *            共通パラメータオブジェクト
	 */
	public void setCommonParameters(PanaCommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
     * Panasonic用ファイル処理関連共通を設定する。<br/>
     * <br/>
     * @param fileUtil Panasonic用ファイル処理関連共通
     */
    public void setFileUtil(PanaFileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

	/**
	 * 入力確認画面表示処理<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @param response
	 *            HTTP レスポンス
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// リクエストパラメータを格納した model オブジェクトを生成する。
		// このオブジェクトは View 層への値引渡しに使用される。
		Map<String, Object> model = createModel(request);
		PanaHousingStatusForm form = (PanaHousingStatusForm) model
				.get("inputForm");
		form.setShowNote(PanaStringUtils.encodeHtml(form.getNote()));
		// ログインユーザーのロール権限を取得する。（タイムスタンプの更新用）
		UserRoleSet userRoleSet = AdminLoginUserUtils.getInstance(request)
				.getLoginUserRole(request, response);

		// バリデーションチェック
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
		if ("insert".equals(form.getCommand())) {
			if (!form.validateIns(errors, userRoleSet.hasRole("admin"))) {
				model.put("errors", errors);
				model.put("inputForm", form);
				return new ModelAndView("insErr", model);
			}
		} else {
			// 事前チェック
			PanaHousing housing = (PanaHousing) this.panaHousingManager
					.searchHousingPk(form.getSysHousingCd(), true);
			if (housing == null) {
				throw new NotFoundException();
			}

			JoinResult housingInfo = housing.getHousingInfo();
			HousingStatusInfo housingStatusInfo = (HousingStatusInfo) housingInfo
					.getItems().get("housingStatusInfo");

			if (!form.validateUpd(errors, userRoleSet.hasRole("admin"),
					housingStatusInfo)) {

				// ユーザー情報取得
				UserRoleSet userRole = AdminLoginUserUtils.getInstance(request)
						.getLoginUserRole(request, response);

				// マイページ会員情報の取得
				MemberInfo memberInfo = getMemberInfo(housing);

				// リフォームプランの取得
				List<ReformPlan> reformPlanReslutList = this.reformManager
						.searchReformPlan(form.getSysHousingCd(), true);
				List<ReformPlan> reformPlanList = new ArrayList<ReformPlan>();

				for (int i = 0; i < 5; i++) {
					if (i >= reformPlanReslutList.size()
							|| reformPlanReslutList.get(i) == null) {
						reformPlanList.add(new ReformPlan());
					} else {
						reformPlanList.add(reformPlanReslutList.get(i));
					}
				}

				// 画面項目の格納
				model.put("housing", housing);
				model.put("memberInfo", memberInfo);
				model.put("reformPlanList", reformPlanList);
				PanaHousingBrowse housingBrowse = new PanaHousingBrowse(
						this.codeLookupManager);
				housingBrowse.setPanaHousingBrowse(model, userRole,
						this.commonParameters, fileUtil);

				model.put("housingBrowse", housingBrowse);
				model.put("errors", errors);
				model.put("inputForm", form);

				return new ModelAndView("updErr", model);
			}
		}

		return new ModelAndView("success", model);
	}

	/**
	 * マイページ会員情報の取得<br>
	 * <br>
	 *
	 * @param housing
	 *            値の設定先となる Housing オブジェクト
	 *
	 * @return 取得したマイページ会員情報
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	private MemberInfo getMemberInfo(Housing housing) throws Exception {

		MemberInfo memberInfo = new MemberInfo();

		// 物件ステータス情報のユーザーIDの取得
		JoinResult housingResult = housing.getHousingInfo();
		HousingStatusInfo housingStatusInfo = (HousingStatusInfo) housingResult
				.getItems().get("housingStatusInfo");
		String userId = housingStatusInfo.getUserId();

		// マイページ会員情報の取得
		JoinResult mypageResult = this.mypageUserManager
				.searchMyPageUserPk(userId);
		if (mypageResult != null) {
			memberInfo = (MemberInfo) mypageResult.getItems().get("memberInfo");
		}

		return memberInfo;
	}

	/**
	 * model オブジェクトを作成する。<br/>
	 * <br/>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @return パラメータを設定した Form を格納した model オブジェクトを復帰する。
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		// リクエストパラメータを取得して Form オブジェクトを作成する。
		PanaHousingFormFactory factory = PanaHousingFormFactory
				.getInstance(request);
		PanaHousingStatusForm form = factory
				.createPanaHousingStatusForm(request);
		model.put("inputForm", form);

		// 検索条件、および、画面コントロールパラメータを取得する。
		PanaHousingSearchForm searchForm = factory
				.createPanaHousingSearchForm(request);
		model.put("searchForm", searchForm);

		return model;
	}
}
