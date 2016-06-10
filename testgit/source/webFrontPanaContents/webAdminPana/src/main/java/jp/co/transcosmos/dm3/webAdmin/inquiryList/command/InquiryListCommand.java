package jp.co.transcosmos.dm3.webAdmin.inquiryList.command;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.AdminLoginInfo;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.model.inquiry.PanaHousingInquiryManageImpl;
import jp.co.transcosmos.dm3.corePana.model.inquiry.dao.HousingInquiryDAO;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquirySearchForm;
import jp.co.transcosmos.dm3.utils.CommonLogging;
import jp.co.transcosmos.dm3.validation.ValidationFailure;
import jp.co.transcosmos.dm3.webAdmin.utils.AdminLogging;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * お問合せの検索・一覧
 * 入力された検索条件を元にお問合せ情報を検索し、一覧表示する。
 * 検索条件の入力に問題がある場合、検索処理は行わない。
 *
 * 【復帰する View 名】
 *    ・"success" : 検索処理正常終了
 *    ・"validFail" : バリデーションエラー
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * 郭中レイ		2015.04.07	新規作成
 * Vinh.Ly		2015.08.20  Change the log output csv
 *
 * 注意事項
 *
 * </pre>
 */
public class InquiryListCommand extends jp.co.transcosmos.dm3.adminCore.inquiry.command.InquiryListCommand {
	/** 処理モード (search = 検索処理、 csv = CSV出力処理、 delete = 削除処理) */
	private String mode;

	/** お問合せメンテナンスを行う Model オブジェクト */
	private PanaHousingInquiryManageImpl panaInquiryManager;

	/** 問合情報メンテナンスの model */
	protected HousingInquiryDAO housingInquiryDAO;

    /** 認証用ロギングクラス */
    private CommonLogging authLogging;

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
	 * 認証用ロギングクラス Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param authLogging 認証用ロギングクラス Model オブジェクト
	 */
    public void setAuthLogging(CommonLogging authLogging) {
        this.authLogging = authLogging;
    }

	/**
	 * 物件問合メンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 * @param inquiryManager 物件問合メンテナンスの model オブジェクト
	 */
	public void setPanaInquiryManager(PanaHousingInquiryManageImpl inquiryManager) {
		super.setInquiryManager(inquiryManager);
		this.panaInquiryManager = (PanaHousingInquiryManageImpl)inquiryManager;
	}

	/**
	 * 問合情報メンテナンスの modelを設定する。<br/>
	 * <br/>
	 * @param housingInquiryDAO セットする housingInquiryDAO
	 */
	public void setHousingInquiryDAO(HousingInquiryDAO housingInquiryDAO) {
		this.housingInquiryDAO = housingInquiryDAO;
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
	 * 物件問合せ情報リクエスト処理<br>
	 * 物件問合せ情報のリクエストがあったときに呼び出される。 <br>
	 *
	 * @param request
	 *            クライアントからのHttpリクエスト。
	 * @param response
	 *            クライアントに返すHttpレスポンス。
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 検索パラメータ受信
        Map<String, Object> model = createModel(request);
        PanaInquirySearchForm searchForm = (PanaInquirySearchForm) model.get("searchForm");
        ModelAndView modelAndView = new ModelAndView();

		searchForm.setVisibleNavigationPageCount(this.visibleNavigationPageCount);

        // バリデーションを実行
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();

        if (!searchForm.validate(errors)) {
        	// バリデーションエラーあり
			model.put("errors", errors);
			return new ModelAndView("validFail", model);
        }

		if ("search".equals(this.mode)) {
	        // 検索実行
	        // searchInquiry() は、パラメータで渡された form の内容で物件問合せ情報を検索し、
	        // 検索した結果を form に格納する。
	        // このメソッドの戻り値は該当件数なので、その値を view 層へ渡すパラメータとして設定している。
			modelAndView = super.handleRequest(request, response);


			// バリデーションを通過し、検索処理が行われた場合、Form の command パラメータに list を設定
			// する。　このパラメータ値は、詳細画面や変更画面で searchCommand パラメータとして引き継がれ、
			// 再び検索画面へ復帰する際、command パラメータとして渡される。
			model.put("command", "list");
		} else if ("csv".equals(this.mode)) {
            // Log出力実行
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String dt = sdf.format(new Date());
            String csvName = "toiawase_" + dt + ".csv";
            AdminUserInterface adminLoginInfo = (AdminLoginInfo) AdminLoginUserUtils.getInstance(request)
                    .getLoginUserInfo(request, response);
            String adminUserId = adminLoginInfo.getUserId().toString();
            String loginId = adminLoginInfo.getLoginId().toString();

            try {
                // 検索実行
                // 検索した結果を form に格納する。
                // このメソッドの戻り値は該当件数なので、その値を view 層へ渡すパラメータとして設定している。
                // CSV出力実行
                this.panaInquiryManager.searchCsvHousing(searchForm, response);
                // Log出力成功
                ((AdminLogging) this.authLogging).write("[" + loginId + "]　（" + csvName + "）出力成功", adminUserId,
                        PanaCommonConstant.ADMIN_LOG_FC_INQUIRY_LIST);
            } catch (Exception e) {
                // Log出力失敗
                ((AdminLogging) this.authLogging).write("[" + loginId + "]　（" + csvName + "）出力失敗", adminUserId,
                        PanaCommonConstant.ADMIN_LOG_FC_INQUIRY_LIST);
                throw e;
            }

            return null;
		} else if ("delete".equals(this.mode)) {

			// 物件情報データを削除処理する
			this.panaInquiryManager.delInquiryAll(searchForm.getInquiryId());

			// 検索実行
			// 検索した結果を form に格納する。
			// このメソッドの戻り値は該当件数なので、その値を view 層へ渡すパラメータとして設定している。
			model.put("hitcont",
					this.panaInquiryManager.searchInquiry(searchForm));
			modelAndView = new ModelAndView("success", model);
		}

		return modelAndView;
	}

}
