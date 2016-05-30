package jp.co.transcosmos.dm3.webAdmin.housingList.command;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.AdminLoginInfo;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.utils.CommonLogging;
import jp.co.transcosmos.dm3.validation.ValidationFailure;
import jp.co.transcosmos.dm3.webAdmin.utils.AdminLogging;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 物件一覧画面
 * リクエストパラメータで渡された物件一覧のバリデーションを行い、物件一覧を表示する。
 * もしバリデーションエラーが発生した場合は入力画面として表示する。
 *
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *    ・"validFail" : 異常終了
 *
 * 担当者        修正日       修正内容
 * ------------ ----------- -----------------------------------------------------
 * gao.long     2015.03.17  新規作成
 * Duong.Nguyen 2015.08.26  Change simple log by admin log for exporting csv file
 *
 * 注意事項
 *
 * </pre>
 */
public class HousingListCommand implements Command {

	/** 処理モード (search = 検索処理、 csv = CSV出力処理、 delete = 削除処理) */
	private String mode;

	/** 物件情報用 Model オブジェクト */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/** 共通情報取用 Model オブジェクト */
	private PanaCommonManage panamCommonManager;

    /** 認証用ロギングクラス */
    private CommonLogging authLogging;


    /**
     * 認証用ロギングクラス Model オブジェクトを設定する。<br/>
     * <br/>
     * 
     * @param authLogging
     *            認証用ロギングクラス Model オブジェクト
     */
    public void setAuthLogging(CommonLogging authLogging) {
        this.authLogging = authLogging;
    }

	/** １ページの表示件数 */
	private int rowsPerPage = 50;

	/**一覧画面の表示ページ数 */
	private int visibleNavigationPageCount = 10;
	/**
	 * 処理モードを設定する<br/>
	 * <br/>
	 *
	 * @param mode
	 *            search = 検索処理、 csv = CSV出力処理、 delete = 削除処理
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * 物件情報用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param PanaHousingPartThumbnailProxy 物件情報用 Model オブジェクト
	 */
    public void setPanaHousingManager(PanaHousingPartThumbnailProxy panaHousingManager) {
        this.panaHousingManager = panaHousingManager;
    }

	/**
	 * 共通情報取用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param PanaCommonManage
	 *            共通情報取用 Model オブジェクト
	 */
	public void setPanamCommonManager(PanaCommonManage panamCommonManager) {
		this.panamCommonManager = panamCommonManager;
	}

	/**
	 * 1ページあたり表示数を設定する。<br>
	 *
	 * @param rowsPerPage
	 *            1ページあたり表示数
	 */
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}
	/**
	 * 一覧画面の表示ページ数を設定する。<br>
	 *
	 * @param rowsPerPage
	 *            一覧画面の表示ページ数
	 */
	public void setVisibleNavigationPageCount(int visibleNavigationPageCount) {
		this.visibleNavigationPageCount = visibleNavigationPageCount;
	}

	/**
	 * 物件一覧表示処理<br>
	 * <br>
	 *
	 * @param request
	 *            クライアントからのHttpリクエスト。
	 * @param response
	 *            クライアントに返すHttpレスポンス。
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// リクエストパラメータを格納した model オブジェクトを生成する。
		Map<String, Object> model = createModel(request);
		PanaHousingSearchForm searchForm = (PanaHousingSearchForm) model.get("searchForm");

		// 画面項目の初期化
		searchForm.setDefaultData(this.panamCommonManager.getPrefMstList(),model, false);

		// バリデーションを実行
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();

		if (!searchForm.validate(errors)) {
			// バリデーションエラーあり
			model.put("errors", errors);
			return new ModelAndView("validFail", model);
		}

		if ("search".equals(this.mode)) {

			// 検索実行
			// 検索した結果を form に格納する。
			// このメソッドの戻り値は該当件数なので、その値を view 層へ渡すパラメータとして設定している。
			model.put("hitcont", this.panaHousingManager.searchHousing(searchForm));
		} else if ("csv".equals(this.mode)) {
            // Log出力実行
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String dt = sdf.format(new Date());
            String csvName = "bukken_" + dt + ".zip";
            AdminUserInterface adminLoginInfo = (AdminLoginInfo) AdminLoginUserUtils.getInstance(request)
                    .getLoginUserInfo(request, response);
            String adminUserId = adminLoginInfo.getUserId().toString();
            String loginId = adminLoginInfo.getLoginId().toString();

            try {
                // CSV出力実行
                panaHousingManager.searchCsvHousing(searchForm, response, this.panaHousingManager,
                        this.panamCommonManager);
                ((AdminLogging) this.authLogging).write("[" + loginId + "]　（" + csvName + "）出力成功", adminUserId,
                        PanaCommonConstant.ADMIN_LOG_FC_HOUSING_LIST);
            } catch (Exception ex) {
                ((AdminLogging) this.authLogging).write("[" + loginId + "]　（" + csvName + "）出力失敗", adminUserId,
                        PanaCommonConstant.ADMIN_LOG_FC_HOUSING_LIST);
                throw ex;
            }

            return null;
		} else if ("delete".equals(this.mode)) {
			PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);
			PanaHousingForm housingForm = factory.createPanaHousingForm();
			housingForm.setSysHousingCd(searchForm.getSysHousingCd());
			// 物件情報データを削除処理する
			this.panaHousingManager.delHousingInfo(housingForm, AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response).getUserId().toString());

			// 検索実行
			// 検索した結果を form に格納する。
			// このメソッドの戻り値は該当件数なので、その値を view 層へ渡すパラメータとして設定している。
			model.put("hitcont", this.panaHousingManager.searchHousing(searchForm));
		}

		// バリデーションを通過し、検索処理が行われた場合、Form の command パラメータに list を設定
		// する。　このパラメータ値は、詳細画面や変更画面で searchCommand パラメータとして引き継がれ、
		// 再び検索画面へ復帰する際、command パラメータとして渡される。
		// （この設定により、検索済であれば、詳細画面から戻った時に検索済で画面が表示される。）
		model.put("command", "list");

		return new ModelAndView("success", model);
	}

	/**
	 * リクエストパラメータから Form オブジェクトを作成する。<br/>
	 * 生成した Form オブジェクトは Map に格納して復帰する。<br/>
	 * key = フォームクラス名（パッケージなし）、Value = フォームオブジェクト <br/>
	 *
	 * @param request
	 *            HTTP リクエストパラメータ
	 * @return パラメータが設定されたフォームオブジェクトを格納した Map オブジェクト
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		// リクエストパラメータを取得して Form オブジェクトを作成する。
		PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);
		PanaHousingSearchForm searchForm = factory.createPanaHousingSearchForm(request);

		// １ページに表示する行数を設定する。
		searchForm.setRowsPerPage(this.rowsPerPage);
		searchForm.setVisibleNavigationPageCount(this.visibleNavigationPageCount);

		model.put("searchForm", searchForm);

		return model;
	}
}
