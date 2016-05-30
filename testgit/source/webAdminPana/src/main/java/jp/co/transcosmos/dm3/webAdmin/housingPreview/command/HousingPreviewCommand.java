package jp.co.transcosmos.dm3.webAdmin.housingPreview.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.building.form.PanaBuildingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousing;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingDetailed;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPreview;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformFormFactory;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingInspection;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 物件プレビュー画面
 * リクエストパラメータで渡された物件詳細のバリデーションを行い、物件プレビュー画面を表示する。
 *
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *
 * 担当者       修正日     修正内容
 * ------------ ----------- -----------------------------------------------------
 * Y.Zhang     2015.05.06  新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class HousingPreviewCommand implements Command {

	/** 処理モード (detail = 物件単体、reform = リフォームプランあり) */
	private String mode;

	/** 物件情報用 Model オブジェクト */
	private PanaHousingPartThumbnailProxy panaHousingManage;

	/** 共通パラメータオブジェクト */
	private PanaCommonParameters commonParameters;

	/** 共通コード変換処理 */
	private CodeLookupManager codeLookupManager;

	/** Panasonic用ファイル処理関連共通Util */
	private PanaFileUtil panaFileUtil;

	/** Panasonic用共通情報取得 */
	private PanaCommonManage panamCommonManager;

	/**
	 * 処理モードを設定する<br/>
	 * <br/>
	 *
	 * @param mode "detail" = 物件単体 "reform" = リフォームプランあり
	 */
	public void setMode(String mode) {
		this.mode = mode;
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
	 * 共通パラメータオブジェクトを設定する。<br/>
	 * <br/>
	 * @param commonParameters 共通パラメータオブジェクト
	 */
	public void setCommonParameters(PanaCommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * 共通コード変換オブジェクトを設定する。<br/>
	 * <br/>
	 * @param codeLookupManager
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * Panasonic用ファイル処理関連共通Utilオブジェクトを設定する。<br/>
	 * <br/>
	 * @param panaFileUtil
	 */
	public void setPanaFileUtil(PanaFileUtil panaFileUtil) {
		this.panaFileUtil = panaFileUtil;
	}

	/**
	 * Panasonic用共通情報取得オブジェクトを設定する。<br/>
	 * <br/>
	 * @param panamCommonManager
	 */
	public void setPanamCommonManager(PanaCommonManage panamCommonManager) {
		this.panamCommonManager = panamCommonManager;
	}

	/**
	 * 物件プレビュー画面表示処理<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @param response
	 *            HTTP レスポンス
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// リクエストパラメータを格納した model オブジェクトを生成する。
		// このオブジェクトは View 層への値引渡しに使用される。
		Map<String, Object> model = createModel(request);
		PanaHousingPreview outPutForm = (PanaHousingPreview) model.get("outPutForm");

		// システム物件CD
		String sysHousingCd = request.getParameter("sysHousingCd");

		// リフォームCD
		String sysReformCd = request.getParameter("sysReformCd");

		// 遷移元画面Idを取得
		String prePageId = request.getParameter("pageId");

		// 処理モードを設定する。
		if ("reformPlan".equals(prePageId) || "reformDtl".equals(prePageId) || "reformImg".equals(prePageId)) {
			this.mode = "reform";
		}

		// 処理モードを設定する。
		outPutForm.setMode(this.mode);

		if (!StringUtils.isEmpty(sysHousingCd)) {

			outPutForm.setSysHousingCd(sysHousingCd);

		} else {

			// システム物件CDがnull
			throw new RuntimeException("システム物件CDが指定されていません.");
		}

		if ("reform".equals(this.mode)) {

			if (!StringUtils.isEmpty(sysReformCd)) {

				outPutForm.setReformCd(sysReformCd);

			} else {

				if (!"reformPlan".equals(prePageId)) {

					// リフォームCDがnull
					throw new RuntimeException("システムリフォームCDが指定されていません.");
				}

			}
		}

		// 処理実行を行う。
		execute(outPutForm, request, response);

		// プレビュー内容を設定する
		setPreviewData(prePageId, request, outPutForm);

		model.put("outPutForm", outPutForm);

		return new ModelAndView("success", model);
	}

	/**
	 * 渡されたバリーオブジェクトから Form へプレビュー内容を設定する。<br/>
	 * <br/>
	 *
	 * @param prePage 遷移元画面
	 * @param request HTTP リクエスト
	 * @param outPutForm 出力用Form オブジェクト
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	private void setPreviewData(String prePageId,
			HttpServletRequest request,
			PanaHousingPreview outPutForm) throws Exception {

		if (StringUtils.isEmpty(prePageId)) {
			throw new RuntimeException("遷移元画面が指定されていません。");
		}

		// リクエストパラメータを取得して Form オブジェクトを作成する。
		PanaHousingFormFactory housingFormFactory = PanaHousingFormFactory.getInstance(request);

		PanaBuildingFormFactory buildingFormFactory = PanaBuildingFormFactory.getInstance(request);

		ReformFormFactory reformFormFactory = ReformFormFactory.getInstance(request);

		// 物件基本情報の場合
		if ("housingInfo".equals(prePageId)) {
			outPutForm.setHousingInfoPreviewData(housingFormFactory.createPanaHousingInfoForm(request), outPutForm);
		}

		// 物件詳細情報の場合
		if ("housingDtl".equals(prePageId)) {
			outPutForm.setHousingDtlPreviewData(housingFormFactory.createPanaHousingDtlInfoForm(request), outPutForm);
		}

		// 物件特長の場合
		if ("housingSpecialty".equals(prePageId)) {
			outPutForm.setHousingSpecialtyPreviewData(housingFormFactory.createPanaHousingSpecialtyForm(request), outPutForm);
		}

		// おすすめポイント情報の場合
		if ("recommendPoint".equals(prePageId)) {
			outPutForm.setRecommendPointPreviewData(housingFormFactory.createRecommendPointForm(request), outPutForm);
		}

		// 住宅診断情報の場合
		if ("housingInspection".equals(prePageId)) {
			outPutForm.setHousingInspectionPreviewData(housingFormFactory.createPanaHousingInspectionForm(request), outPutForm);
		}

		// リフォーム情報の場合
		if ("reformPlan".equals(prePageId)) {
			outPutForm.setReformPlanPreviewData(reformFormFactory.createRefromInfoForm(request), outPutForm);
		}

		// リフォーム詳細情報の場合
		if ("reformDtl".equals(prePageId)) {
			outPutForm.setReformDtlPreviewData(reformFormFactory.createReformDtlForm(request), outPutForm);
		}

		// リフォーム画像情報の場合
		if ("reformImg".equals(prePageId)) {
			outPutForm.setReformImgPreviewData(reformFormFactory.createRefromImgForm(request), outPutForm);
		}

		// 物件画像情報の場合
		if ("housingImage".equals(prePageId)) {
			outPutForm.setHousingImagePreviewData(housingFormFactory.createPanaHousingImageInfoForm(request), outPutForm);
		}

		// 地域情報の場合
		if ("housingLandmark".equals(prePageId)) {
			outPutForm.setHousingLandmarkPreviewData(buildingFormFactory.createBuildingLandmarkForm(request), outPutForm);
		}

		// プレビューの新着フラグと更新日を設定する。
		outPutForm.setFreshAndUpdDate(outPutForm);

		// 画面の表示フラグを設定する。
		outPutForm.setDisplayFlg();

		// meta部を設定する。
		outPutForm.setMeta();

	}

	/**
	 * リクエストパラメータから outPutForm オブジェクトを作成する。<br/>
	 * 生成した outPutForm オブジェクトは Map に格納して復帰する。<br/>
	 * key = フォームクラス名（パッケージなし）、Value = フォームオブジェクト <br/>
	 *
	 * @param request
	 *            HTTP リクエストパラメータ
	 * @return パラメータが設定されたフォームオブジェクトを格納した Map オブジェクト
	 */
	private Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<>();

		PanaHousingPreview requestForm = new PanaHousingPreview(this.codeLookupManager, this.commonParameters, this.panaFileUtil);

		model.put("outPutForm", requestForm);

		return model;
	}

	/**
	 * 処理実行を行う。<br/>
	 * <br/>
	 *
	 * @param outPutForm
	 *            入力値が格納された outPutForm オブジェクト
	 * @param response
	 * @param request
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 * @exception NotFoundException
	 *                更新対象が存在しない場合
	 */
	@SuppressWarnings("unchecked")
	private void execute(PanaHousingDetailed outPutForm, HttpServletRequest request, HttpServletResponse response) throws Exception, NotFoundException {

		// プレビューフラグ
		outPutForm.setPreviewFlg(true);

		// ログインユーザーの情報を取得
		AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);

		// ログインユーザーの情報取得した場合
		if (loginUser != null) {

			// 会員フラグ
			outPutForm.setMemberFlg(true);

		} else {
			// 会員フラグ
			outPutForm.setMemberFlg(false);
		}

		// 物件詳細Map
		Map<String, Object> detailedMap = new HashMap<String, Object>();

		// 物件情報を取得する。
		PanaHousing housing = (PanaHousing) this.panaHousingManage.searchHousingPk(outPutForm.getSysHousingCd(), true);

		if (housing == null) {

			// 物件情報が無い
			throw new NotFoundException();
		}

		// リフォーム関連情報を取得する。
		List<Map<String, Object>> reforms = housing.getReforms();

		// リフォームプラン情報リスト
		List<ReformPlan> reformPlanList = new ArrayList<ReformPlan>();

		// リフォームプラン情報
		ReformPlan reformPlan = null;

		// リフォーム詳細情報リスト
		List<ReformDtl> reformDtlList = null;

		// リフォーム画像情報リスト
		List<ReformImg> reformImgList = null;

		// リフォーム関連情報を繰り返す、リフォームCDを判断する。
		for (Map<String, Object> reform : reforms) {

			// リフォームプラン情報
			ReformPlan temReformPlan = (ReformPlan) reform.get("reformPlan");

			// リフォームCDが表示用のリフォームCDの場合
			if (outPutForm.getReformCd() != null && outPutForm.getReformCd().equals(temReformPlan.getSysReformCd())) {

				// リフォームプラン情報取得する。
				reformPlan = (ReformPlan) reform.get("reformPlan");

				// リフォーム詳細情報リスト取得する。
				reformDtlList = (List<ReformDtl>) reform.get("dtlList");

				// リフォーム画像情報リスト取得する。
				reformImgList = (List<ReformImg>) reform.get("imgList");
			}

			// リフォームプラン情報を取得する。
			reformPlanList.add(temReformPlan);
		}

		if ("reform".equals(this.mode) && !"reformPlan".equals(request.getParameter("pageId")) && reformPlan == null) {

			// リフォームプラン情報が無い
			throw new NotFoundException();
		}

		// 物件インスペクションリスト取得する。
		List<HousingInspection> housingInspectionList = housing.getHousingInspections();

		// 都道府県リストを取得する。
		List<PrefMst> prefMstList = this.panamCommonManager.getPrefMstList();

		// 物件情報をプットする。
		detailedMap.put("housing", housing);

		// リフォームプラン情報リストをプットする。
		detailedMap.put("reformPlanList", reformPlanList);

		// リフォームプラン情報をプットする。
		detailedMap.put("reformPlan", reformPlan);

		// リフォーム詳細情報リストをプットする。
		detailedMap.put("reformDtlList", reformDtlList);

		// リフォーム画像情報リストをプットする。
		detailedMap.put("reformImgList", reformImgList);

		// 物件インスペクションリストをプットする。
		detailedMap.put("housingInspectionList", housingInspectionList);

		// 都道府県リストをプットする。
		detailedMap.put("prefMstList", prefMstList);

		// Formをセットする。
		outPutForm.setDefaultData(detailedMap);
	}
}
