package jp.co.transcosmos.dm3.webAdmin.information.command;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.InformationManage;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.information.InformationManageImpl;
import jp.co.transcosmos.dm3.core.model.information.form.InformationForm;
import jp.co.transcosmos.dm3.core.model.information.form.InformationFormFactory;
import jp.co.transcosmos.dm3.core.model.information.form.InformationSearchForm;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.utils.CommonLogging;
import jp.co.transcosmos.dm3.validation.ValidationFailure;
import jp.co.transcosmos.dm3.webAdmin.utils.AdminLogging;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * お知らせの検索・一覧
 * 入力された検索条件を元にお知らせ情報を検索し、一覧表示する。
 * 検索条件の入力に問題がある場合、検索処理は行わない。
 *
 * 【復帰する View 名】
 *    ・"success" : 検索処理正常終了
 *    ・"validFail" : バリデーションエラー
 *
 * 担当者        修正日       修正内容
 * ------------ ----------- -----------------------------------------------------
 * zh.xiaoting  2015.04.24  新規作成
 * Duong.Nguyen 2015.08.26  Change simple log by admin log for exporting csv file
 *
 * 注意事項
 *
 * </pre>
 */
public class PanaInformationListCommand implements Command {

	/** 処理モード (search = 検索処理、 csv = CSV出力処理、 delete = 削除処理) */
	private String mode;

	/** お知らせメンテナンスを行う Model オブジェクト */
	private InformationManage informationManager;

	/** １ページの表示件数 */
	private int rowsPerPage = 50;

    /** 認証用ロギングクラス */
    private CommonLogging authLogging;

	/** ページの表示数 */
    private int visibleNavigationPageCount = 10;

	/**
	 * 処理モードを設定する<br/>
	 * <br/>
	 * @param mode
	 *            search = 検索処理、 csv = CSV出力処理、 delete = 削除処理
	 */
	public void setMode(String mode) {
		this.mode = mode;
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
	 * 認証用ロギングクラス Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param authLogging 認証用ロギングクラス Model オブジェクト
	 */
    public void setAuthLogging(CommonLogging authLogging) {
        this.authLogging = authLogging;
    }

    /**
     * ページの表示数を設定する。<br>
     * @param visibleNavigationPageCount ページ数
     */
    public void setVisibleNavigationPageCount(int visibleNavigationPageCount) {
        this.visibleNavigationPageCount = visibleNavigationPageCount;
    }

	/**
	 * お知らせ情報リクエスト処理<br>
	 * お知らせ情報のリクエストがあったときに呼び出される。 <br>
	 *
	 * @param request
	 *            クライアントからのHttpリクエスト。
	 * @param response
	 *            クライアントに返すHttpレスポンス。
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 検索パラメータ受信
        Map<String, Object> model = createModel(request);
        InformationSearchForm form = (InformationSearchForm) model.get("searchForm");
        InformationForm inputForm = (InformationForm) model.get("inputForm");

        // バリデーションを実行
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();

        if (!form.validate(errors)) {
        	// バリデーションエラーあり
			model.put("errors", errors);
			return new ModelAndView("validFail", model);
        }

        if ("delete".equals(this.mode)) {
        	// お知らせ情報データを削除処理する
        	this.informationManager.delInformation(inputForm);
        }

        // CSVログ出力
        if ("csv".equals(this.mode)) {
            // Prepare data for admin log
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String dt = sdf.format(new Date());
            String csvName = "oshirase_" + dt + ".csv";
            AdminUserInterface adminUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);
            String loginID = adminUser.getLoginId();
            String adminUserId = String.valueOf(adminUser.getUserId());

            try {
                informationManager.searchAdminInformation(form);
                ((AdminLogging) this.authLogging).write("[" + loginID + "]　（" + csvName + "）出力成功", adminUserId,
                        PanaCommonConstant.ADMIN_LOG_FC_INFO_LIST);
            } catch (Exception ex) {
                ((AdminLogging) this.authLogging).write("[" + loginID + "]　（" + csvName + "）出力失敗", adminUserId,
                        PanaCommonConstant.ADMIN_LOG_FC_INFO_LIST);
                throw ex;
            }
        } else {
            // 検索実行
            // searchInformation() は、パラメータで渡された form の内容でお知らせ情報を検索し、
            // 検索した結果を form に格納する。
            // このメソッドの戻り値は該当件数なので、その値を view 層へ渡すパラメータとして設定している。
            model.put("hitcont", this.informationManager.searchAdminInformation(form));
        }


		// バリデーションを通過し、検索処理が行われた場合、Form の command パラメータに list を設定
		// する。　このパラメータ値は、詳細画面や変更画面で searchCommand パラメータとして引き継がれ、
		// 再び検索画面へ復帰する際、command パラメータとして渡される。
		model.put("command", "list");

		return new ModelAndView("success", model);
	}

	/**
	 * リクエストパラメータから Form オブジェクトを作成する。<br/>
	 * <br/>
	 * @param request HTTP リクエストパラメータ
	 * @return パラメータが設定されたフォームオブジェクト
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		// リクエストパラメータを取得して Form オブジェクトを作成する。
		InformationFormFactory factory = InformationFormFactory.getInstance(request);
		InformationSearchForm searchForm = factory.createInformationSearchForm(request);
		InformationForm inputForm = factory.createInformationForm(request);

        // ページ内の表示件数を From に設定する。
        // この値は、フレームワークのページ処理が使用する。
		searchForm.setRowsPerPage(this.rowsPerPage);
		// ページ数を設定する。
        searchForm.setVisibleNavigationPageCount(this.visibleNavigationPageCount);
        model.put("searchForm", searchForm);
        model.put("inputForm", inputForm);

		return model;

	}

	/**
	 * お知らせメンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 * @param informationManager お知らせメンテナンスの model オブジェクト
	 */
	public void setInformationManager(InformationManageImpl informationManager) {
		this.informationManager = informationManager;
	}

}
