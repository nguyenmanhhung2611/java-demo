package jp.co.transcosmos.dm3.webAdmin.housingStatus.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.BuildingManage;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.ReformManage;
import jp.co.transcosmos.dm3.corePana.model.building.form.PanaBuildingForm;
import jp.co.transcosmos.dm3.corePana.model.building.form.PanaBuildingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousing;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingBrowse;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingStatusForm;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingStatusInfo;
import jp.co.transcosmos.dm3.corePana.vo.MemberInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.login.UserRoleSet;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * ステータス変更完了画面
 * <p>
 * 【新規登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。</li>
 * <li>バリデーションが正常終了した場合、ステータスを新規登録する。</li>
 * <li>また、公開区分が特定個人の場合、ステータス公開区分情報も新規登録する。</li>
 * </ul>
 * <br/>
 * 【更新登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。</li>
 * <li>バリデーションが正常終了した場合、ステータス情報を更新する。</li>
 * <li>もし、更新対象データが存在しない場合、更新処理が継続できないので該当無し画面を表示する。</li>
 * </ul>
 * <br/>
 * 【復帰する View 名】<br/>
 * <ul>
 * <li>success</li>:正常終了（リダイレクトページ）
 * <li>input</li>:バリデーションエラーによる再入力
 * <li>notFound</li>:該当データが存在しない場合（更新処理の場合）
 * <li>comp</li>:完了画面表示
 * </ul>
 * <p>
 *
 * <pre>
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * Liu.yandong 2015.03.11	新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class HousingStatusCompCommand implements Command {

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

	private BuildingManage buildingManager;
	/** 処理モード (insert = 新規登録処理、 update=更新処理) */
	private String mode;

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
	 * ステータスメンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param buildingManager
	 *            ステータスメンテナンスの model オブジェクト
	 */
	public void setBuildingManager(BuildingManage buildingManage) {
		this.buildingManager = buildingManage;
	}

	/**
	 * 処理モードを設定する<br/>
	 * <br/>
	 *
	 * @param mode
	 *            "insert" = 新規登録処理、"update" = 更新処理
	 */
	public void setMode(String mode) {
		this.mode = mode;
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
	 * 物件情報の追加、変更処理<br>
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

		// ログインユーザーの情報を取得する。　（タイムスタンプの更新用）
		AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request)
				.getLoginUserInfo(request, response);

		// ログインユーザーのロール権限を取得する。　（タイムスタンプの更新用）
		UserRoleSet userRoleSet = AdminLoginUserUtils.getInstance(request)
				.getLoginUserRole(request, response);


		// 完了画面でリロードした場合、更新処理が意図せず実行される問題が発生する。
		// その問題を解消する為、view 名で "success"　を指定すると自動リダイレクト画面が表示される。
		// このリダイレクト画面は、command パラメータを "redirect"　に設定して完了画面へリクエストを
		// 送信する。
		// よって、command = "redirect" の場合は、ＤＢ更新は行わず、完了画面を表示する。
		String command = form.getCommand();
		if (command != null
				&& ("iRedirect".equals(command) || "uRedirect".equals(command))) {

			if ("iRedirect".equals(command)) {
				form.setCommand("insert");
			} else if ("uRedirect".equals(command)) {
				form.setCommand("update");
			}
			form.setSysHousingCd(form.getHousingCd());
			return new ModelAndView("comp", model);
		}

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

		// 各種処理を実行
		execute(request, response, form, loginUser);

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
	 * 処理の振り分けと実行を行う。<br/>
	 * <br/>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @param inputForm
	 *            入力値が格納された Form オブジェクト
	 * @param loginUser
	 *            タイムスタンプ更新時に使用するログインユーザー情報
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 * @exception NotFoundException
	 *                更新対象が存在しない場合
	 */
	protected void execute(HttpServletRequest request,
			HttpServletResponse response, PanaHousingStatusForm inputForm,
			AdminUserInterface loginUser) throws Exception, NotFoundException {

		if ("insert".equals(this.mode)) {
			insert(request, inputForm, loginUser);
		} else if ("update".equals(this.mode)) {
			panaHousingManager.updateHousingStatus(inputForm,
					(String) loginUser.getUserId());

		} else {
			// 想定していない処理モードの場合、例外をスローする。
			throw new RuntimeException("想定外の処理モードです.");
		}

	}

	/**
	 * 新規登録処理<br/>
	 * 引数で渡された内容で物件情報を下記のテーブルに追加する。<br/>
	 * ・物件ステータス情報<br/>
	 * ・物件基本情報<br/>
	 * ・建物基本情報<br/>
	 * <br/>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @param inputForm
	 *            入力値が格納された Form オブジェクト
	 * @param loginUser
	 *            タイムスタンプ更新時に使用するログインユーザー情報
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	protected void insert(HttpServletRequest request,
			PanaHousingStatusForm inputForm, AdminUserInterface loginUser)
			throws Exception {

		// 建物基本情報の登録
		String sysBuildingCd = addPanaBuildingInfo(request, inputForm,
				loginUser);

		// 物件基本情報の登録
		addPanaHousingInfo(request, inputForm, loginUser, sysBuildingCd);

		// 物件ステータス情報の登録
		addPanaHousingStatus(request, inputForm, loginUser);

	}

	/**
	 * 建物基本情報登録処理<br/>
	 * <br/>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @param inputForm
	 *            入力値が格納された Form オブジェクト
	 * @param loginUser
	 *            タイムスタンプ更新時に使用するログインユーザー情報
	 * @return sysBuildingCd システム物件CD
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	private String addPanaBuildingInfo(HttpServletRequest request,
			PanaHousingStatusForm inputForm, AdminUserInterface loginUser)
			throws Exception {

		// リクエストパラメータを取得して Form オブジェクトを作成する。
		PanaBuildingFormFactory factory = PanaBuildingFormFactory
				.getInstance(request);
		// リクエストパラメータを格納した form オブジェクトを生成する。
		PanaBuildingForm form = factory.createPanaBuildingForm(request);

		// 物件種類CD
		form.setHousingKindCd(inputForm.getHousingKindCd());

		String sysBuildingCd = this.buildingManager.addBuilding(form,
				(String) loginUser.getUserId());

		return sysBuildingCd;
	}

	/**
	 * 物件基本情報登録処理<br/>
	 * <br/>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @param inputForm
	 *            入力値が格納された Form オブジェクト
	 * @param loginUser
	 *            タイムスタンプ更新時に使用するログインユーザー情報
	 * @param sysBuildingCd
	 *            システム建物CD
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	private void addPanaHousingInfo(HttpServletRequest request,
			PanaHousingStatusForm inputForm, AdminUserInterface loginUser,
			String sysBuildingCd) throws Exception {

		// リクエストパラメータを取得して Form オブジェクトを作成する。
		PanaHousingFormFactory factory = PanaHousingFormFactory
				.getInstance(request);
		// リクエストパラメータを格納した form オブジェクトを生成する。
		PanaHousingForm housingForm = factory.createPanaHousingForm(request);

		// システム建物CD
		housingForm.setSysBuildingCd(sysBuildingCd);
		// 表示用物件名
		housingForm.setDisplayHousingName(inputForm.getDisplayHousingName());
		// 物件リクエスト処理対象フラグ
		housingForm.setRequestFlg("1");

		// データを追加
		String sysHousingCd = this.panaHousingManager.addHousing(housingForm,
				(String) loginUser.getUserId());

		// データを修正
		housingForm.setSysHousingCd(sysHousingCd);
		housingForm.setHousingCd(sysHousingCd);
		this.panaHousingManager.updateHousing(housingForm,
				(String) loginUser.getUserId());

		inputForm.setSysHousingCd(sysHousingCd);
	}

	/**
	 * 物件ステータス情報登録処理<br/>
	 * <br/>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @param inputForm
	 *            入力値が格納された Form オブジェクト
	 * @param loginUser
	 *            タイムスタンプ更新時に使用するログインユーザー情報
	 * @return sysBuildingCd システム物件CD
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	private void addPanaHousingStatus(HttpServletRequest request,
			PanaHousingStatusForm inputForm, AdminUserInterface loginUser)
			throws Exception {

		// 物件ステータス情報の登録
		this.panaHousingManager.addHousingStatus(inputForm);
	}

	/**
	 * model オブジェクトを作成し、リクエストパラメータを格納した form オブジェクトを格納する。<br/>
	 * <br/>
	 *
	 * @param request
	 *            HTTP リクエストパラメータ
	 * @return パラメータを設定した Form を格納した model オブジェクトを復帰する。
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<>();

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
