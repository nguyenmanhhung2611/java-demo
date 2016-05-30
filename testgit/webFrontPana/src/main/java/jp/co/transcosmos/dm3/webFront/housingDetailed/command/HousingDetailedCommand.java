package jp.co.transcosmos.dm3.webFront.housingDetailed.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.core.util.RecentlyCookieUtils;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.RecentlyInfoManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousing;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingDetailed;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingInspection;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 物件詳細画面
 * リクエストパラメータで渡された物件詳細のバリデーションを行い、物件詳細画面を表示する。
 *
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *
 * 担当者       修正日     修正内容
 * ------------ ----------- -----------------------------------------------------
 * gao.long     2015.04.13  新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class HousingDetailedCommand implements Command {

	/** 処理モード (detail = 物件単体、reform = リフォームプランあり) */
	private String mode;

	/** 物件情報用 Model オブジェクト */
	private PanaHousingPartThumbnailProxy panaHousingManage;

	/** 最近見た物件情報を管理する Model オブジェクト */
	private RecentlyInfoManage recentlyInfoManage;

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
	 * 最近見た物件情報用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param RecentlyInfoManage 最近見た物件情報用 Model オブジェクト
	 */
	public void setRecentlyInfoManage(RecentlyInfoManage recentlyInfoManage) {
		this.recentlyInfoManage = recentlyInfoManage;
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
	 * 物件詳細画面表示処理<br>
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
		PanaHousingDetailed outPutForm = (PanaHousingDetailed) model.get("outPutForm");

		// 処理モードを設定する。
		outPutForm.setMode(this.mode);

		// システム物件CD
		String sysHousingCd = request.getParameter("sysHousingCd");

		// リフォームCD
		String sysReformCd = request.getParameter("sysReformCd");

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

				// リフォームCDがnull
				throw new RuntimeException("システムリフォームCDが指定されていません.");

			}
		}

		// 本ページURL
		outPutForm.setCurrentUrl(request.getRequestURI());

		// 処理実行を行う。
		try {
			execute(outPutForm, request, response);

		} catch (NotFoundException e) {
			return new ModelAndView("404");
		}

		model.put("outPutForm", outPutForm);

		return new ModelAndView("success", model);
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

		PanaHousingDetailed requestForm = new PanaHousingDetailed(this.codeLookupManager, this.commonParameters, this.panaFileUtil);

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
		outPutForm.setPreviewFlg(false);

		// ログインユーザーの情報を取得
		MypageUserInterface mypageUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);

		String userId = null;

		// ログインユーザーの情報取得した場合
		if (mypageUser != null) {
			// ユーザIDを取得
			userId = (String) mypageUser.getUserId();

			// 会員フラグ
			outPutForm.setMemberFlg(true);

		} else {

			// 匿名ログイン情報を取得
			LoginUser loginUser = FrontLoginUserUtils.getInstance(request).getAnonLoginUserInfo(request, response);

			if (loginUser != null) {
				// ユーザIDを取得
				userId = (String) loginUser.getUserId();

				// 会員フラグ
				outPutForm.setMemberFlg(false);
			}
		}

		// 物件詳細Map
		Map<String, Object> detailedMap = new HashMap<String, Object>();

		// 物件情報を取得する。
		PanaHousing housing = (PanaHousing) this.panaHousingManage.searchHousingPk(outPutForm.getSysHousingCd());

		if (housing == null) {

			// 物件情報が無い
			throw new NotFoundException();
		}

		// 最近見た物件情報結果リストを取得する。
		List<PanaHousing> recentlyInfoList = this.recentlyInfoManage.searchRecentlyInfo(userId);

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

		if ("reform".equals(this.mode) && reformPlan == null) {

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

		// 最近見た物件情報結果リストをプットする。
		detailedMap.put("recentlyInfoList", recentlyInfoList);

		// Formをセットする。
		outPutForm.setDefaultData(detailedMap);

		// 画面の表示フラグを設定する。
		outPutForm.setDisplayFlg();

		// 最近見た物件テーブルにデータを登録
		Map<String, Object> paramMap = new HashMap<String, Object>();

		// 最近見た物件情報のシステム物件CD
		paramMap.put("sysHousingCd", outPutForm.getSysHousingCd());

		// ユーザーID
		paramMap.put("userId", userId);

		// 最近見た物件テーブルから件数を取得する。
		int recentlyInfoCnt = this.recentlyInfoManage.getRecentlyInfoCnt(userId);

		// 最近見た物件テーブルにデータを登録
		int ret = this.recentlyInfoManage.addRecentlyInfo(paramMap, userId);

		// 追加した場合
		if (ret == 1) {

			// 登録成功、最近見た物件の件数を保持するCookieの値 + 1
			RecentlyCookieUtils.getInstance(request).setRecentlyCount(request, response, recentlyInfoCnt + 1);
		}
	}
}
