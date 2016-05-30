package jp.co.transcosmos.dm3.webAdmin.memberInfo.command;

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
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.favorite.PanaFavoriteManageImpl;
import jp.co.transcosmos.dm3.corePana.model.member.PanaMypageUserManageImpl;
import jp.co.transcosmos.dm3.corePana.model.member.form.MemberFormFactory;
import jp.co.transcosmos.dm3.corePana.model.member.form.MemberSearchForm;
import jp.co.transcosmos.dm3.utils.CommonLogging;
import jp.co.transcosmos.dm3.validation.ValidationFailure;
import jp.co.transcosmos.dm3.webAdmin.utils.AdminLogging;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 会員情報の検索・一覧
 *
 * 【復帰する View 名】
 *    ・"success" : 検索処理正常終了
 *
 * 担当者          修正日       修正内容
 * -------------- ----------- -----------------------------------------------------
 * tang.tianyun   2015.04.13  新規作成
 * Vinh.Ly        2015.08.26  Change the log output csv
 *
 * 注意事項
 *
 * </pre>
 */
public class MemberInfoListCommand implements Command {

	/** 処理モード (search = 検索処理、 csv = CSV出力処理、 delete = 削除処理) */
	private String mode;

	/** 会員情報用 Model オブジェクト */
    protected PanaMypageUserManageImpl memberManager;

	/** 共通情報取用 Model オブジェクト */
	private PanaCommonManage panamCommonManager;

	/** お気に入り情報取用 Model オブジェクト */
	private PanaFavoriteManageImpl panaFavoriteManager;

    /** １ページの表示件数 */
    private int rowsPerPage = 50;

    /** ページの表示数 */
    private int visibleNavigationPageCount = 10;

    /** 認証用ロギングクラス */
    private CommonLogging authLogging;

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
	 * 会員情報取用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param memberManager 共通情報取用 Model オブジェクト
	 */
	public void setMemberManager(PanaMypageUserManageImpl memberManager) {
		this.memberManager = memberManager;
	}

	/**
	 * 共通情報取用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param PanaCommonManage 共通情報取用 Model オブジェクト
	 */
	public void setPanamCommonManager(PanaCommonManage panamCommonManager) {
		this.panamCommonManager = panamCommonManager;
	}

	/**
	 * お気に入り情報取用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param memberManager 共通情報取用 Model オブジェクト
	 */
	public void setPanaFavoriteManager(PanaFavoriteManageImpl panaFavoriteManager) {
		this.panaFavoriteManager = panaFavoriteManager;
	}

    /**
     * 1ページあたり表示数を設定する。<br>
     * @param rowsPerPage 1ページあたり表示数
     */
    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    /**
     * ページの表示数を設定する。<br>
     * @param visibleNavigationPageCount ページ数
     */
    public void setVisibleNavigationPageCount(int visibleNavigationPageCount) {
        this.visibleNavigationPageCount = visibleNavigationPageCount;
    }

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

    /**
	 * 会員一覧表示処理<br>
	 * <br>
	 *
	 * @param request
	 *            クライアントからのHttpリクエスト。
	 * @param response
	 *            クライアントに返すHttpレスポンス。
	 */
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // リクエストパラメータを格納した model オブジェクトを生成する。
        Map<String, Object> model = createModel(request);
        MemberSearchForm searchForm = (MemberSearchForm) model.get("searchForm");

        if (!"csv".equals(this.mode)) {
        	// 都道府県マスタを取得する
     	    List<PrefMst> prefMstList = this.panamCommonManager.getPrefMstList();
     		model.put("prefMstList", prefMstList);
        }

        // バリデーションを実行
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();

        if (!searchForm.validate(errors)) {
            // バリデーションエラーあり
            model.put("errors", errors);
            return new ModelAndView("validFail", model);
        }

        if ("delete".equals(this.mode)) {
        	// 会員情報データを削除処理する
        	this.memberManager.delMyPageUser(searchForm.getUserId());
        	// お気に入り情報を削除する
        	this.panaFavoriteManager.delFavorite(searchForm.getUserId());
        }

        // CSVログ出力
        if ("csv".equals(this.mode)) {
            // Log出力実行
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String dt = sdf.format(new Date());
            String csvName = "kaiin_" + dt + ".csv";
            AdminUserInterface adminLoginInfo = (AdminLoginInfo) AdminLoginUserUtils.getInstance(request)
                    .getLoginUserInfo(request, response);
            String adminUserId = adminLoginInfo.getUserId().toString();
            String loginId = adminLoginInfo.getLoginId().toString();
            try {
                // 検索実行
                // searchMyPageUser() は、パラメータで渡された form の内容で会員を検索し、
                // 検索した結果を form に格納する。
                this.memberManager.searchMyPageUser(searchForm);
                // Log出力成功
                ((AdminLogging) this.authLogging).write("[" + loginId + "]　（" + csvName + "）出力成功", adminUserId,
                        PanaCommonConstant.ADMIN_LOG_FC_MEMBER_LIST);
            } catch (Exception e) {
                // Log出力失敗
                ((AdminLogging) this.authLogging).write("[" + loginId + "]　（" + csvName + "）出力失敗", adminUserId,
                        PanaCommonConstant.ADMIN_LOG_FC_MEMBER_LIST);
                throw e;
            }

        } else {
            // 検索実行
            // searchMyPageUser() は、パラメータで渡された form の内容で会員を検索し、
            // 検索した結果を form に格納する。
            // このメソッドの戻り値は該当件数なので、その値を view 層へ渡すパラメータとして設定している。
            model.put("hitcont", this.memberManager.searchMyPageUser(searchForm));
        }

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
        MemberFormFactory factory = MemberFormFactory.getInstance(request);
        MemberSearchForm searchForm = factory.createMypageUserSearchForm(request);

        // １ページに表示する行数を設定する。
        searchForm.setRowsPerPage(this.rowsPerPage);
        // ページ数を設定する。
        searchForm.setVisibleNavigationPageCount(this.visibleNavigationPageCount);
        // バリデーションを通過し、検索処理が行われた場合、Form の searchCommand パラメータに list を設定
        // する。　このパラメータ値は、詳細画面や変更画面で searchCommand パラメータとして引き継がれ、
        // 再び検索画面へ復帰する際、searchCommand パラメータとして渡される。
        // （この設定により、検索済であれば、編集画面から戻った時に検索済で画面が表示される。）
        searchForm.setSearchCommand("list");

        model.put("searchForm", searchForm);

        return model;
    }

}
