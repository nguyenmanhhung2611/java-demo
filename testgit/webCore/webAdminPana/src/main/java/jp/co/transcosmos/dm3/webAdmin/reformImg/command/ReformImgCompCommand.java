package jp.co.transcosmos.dm3.webAdmin.reformImg.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.reform.ReformPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformFormFactory;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformImgForm;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * リフォーム画像編集登録確認画面
 *
 * <pre>
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 *   焦		  2015.03.10    新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class ReformImgCompCommand implements Command {

    /** リフォーム情報メンテナンスを行う Model オブジェクト */
    private ReformPartThumbnailProxy reformManager;

    /**
     * リフォーム情報メンテナンスを行う Model　オブジェクトを設定する。<br/>
     * <br/>
     *
     * @param reformManage
     *            リフォーム情報メンテナンスの model オブジェクト
     */
    public void setReformManager(ReformPartThumbnailProxy reformManager) {
        this.reformManager = reformManager;
    }

    /**
     * リフォーム画像編集画面登録、更新、削除処理<br>
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

        Map<String, Object> model = new HashMap<String, Object>();

        ReformFormFactory factory = ReformFormFactory.getInstance(request);

        // リクエストパラメータを、フォームオブジェクトへ設定する。
        ReformImgForm form = factory.createRefromImgForm(request);

        PanaHousingFormFactory housingFactory = PanaHousingFormFactory.getInstance(request);
        PanaHousingSearchForm searchForm = housingFactory.createPanaHousingSearchForm(request);
        model.put("searchForm", searchForm);

        String command = form.getCommand();
        String sysHousingCd = form.getSysHousingCd();
        String sysReformCd = form.getSysReformCd();
        String housingCd = form.getHousingCd();
        String housingKindCd = form.getHousingKindCd();

		if (command != null && command.equals("redirect")) {
			model.put("sysHousingCd", sysHousingCd);
			model.put("sysReformCd", sysReformCd);
			model.put("housingCd", housingCd);
			model.put("housingKindCd", housingKindCd);
			return new ModelAndView("comp", model);
		}

		// 事前チェック:物件基本情報テーブルの読込
        HousingInfo housingInfo = this.reformManager.searchHousingInfo(form.getSysHousingCd());
        model.put("housingInfo", housingInfo);

        // リフォーム関連情報を一括読込
        Map<String, Object> reform = this.reformManager.searchReform(form.getSysReformCd());

        // リフォーム詳細の検索
        List<ReformImg> reformImgresults = (List<ReformImg>) reform.get("imgList");
        // 閲覧権限
        String[] roleId = new String[reformImgresults.size()];
        for (int i = 0; i < reformImgresults.size(); i++) {
        	 // 閲覧権限
            roleId[i] = reformImgresults.get(i).getRoleId();
        }

        // バリデーション処理
        Validateable validateableForm = (Validateable) form;
        // エラーメッセージ用のリストを作成
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();

        // バリデーションを実行
        if (!validateableForm.validate(errors)) {
            // エラー処理
            // エラーオブジェクトと、フォームオブジェクトをModelAndView に渡している
            model.put("errors", errors);
         // 閲覧権限
            form.setEditOldRoleId(roleId);
            model.put("ReformImgForm", form);
            return new ModelAndView("validationError", model);
        }

        // 登録の場所
        if ("insert".equals(form.getCommand())) {
            // リフォーム画像の追加処理
            this.reformManager.addReformImg(form);
        } else {
            if (form.getDivNo() != null) {
                // リフォーム画像の更新処理（更新・削除）
                this.reformManager.updateReformImg(form);
            }
        }

        // ログインユーザーの情報を取得
        AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);
        // ユーザIDを取得
        String userId = String.valueOf(loginUser.getUserId());

        // 物件基本情報の最終更新日と最終更新者を更新の処理
        this.reformManager.updateEditTimestamp(form.getSysHousingCd(), form.getSysReformCd(), userId);

        // フォームオブジェクトのみをModelAndView に渡している
        model.put("ReformImgForm", form);
        return new ModelAndView("success", model);

    }
}
