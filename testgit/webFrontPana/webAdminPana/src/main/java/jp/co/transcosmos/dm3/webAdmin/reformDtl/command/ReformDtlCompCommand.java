package jp.co.transcosmos.dm3.webAdmin.reformDtl.command;

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
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.reform.ReformPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformDtlForm;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformFormFactory;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * リフォーム詳細情報の追加、変更、削除処理.
 * <p>
 * 【新規登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。</li>
 * <li>バリデーションが正常終了した場合、リフォーム詳細情報を新規登録する。</li>
 * <li>また、公開先が特定個人の場合、リフォーム公開先情報も新規登録する。</li>
 * </ul>
 * <br/>
 * 【更新登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。</li>
 * <li>バリデーションが正常終了した場合、リフォーム詳細情報を更新する。</li>
 * <li>リフォーム公開先情報は一度削除し、変更後の公開先が特定個人であれば、リフォーム公開先情報も新規登録する。</li>
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
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * fan			2015.03.12	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class ReformDtlCompCommand implements Command {

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

    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        // リクエストパラメータを、フォームオブジェクトへ設定する。
        Map<String, Object> model = new HashMap<String, Object>();
        ReformFormFactory factory = ReformFormFactory.getInstance(request);

        // リクエストパラメータを、フォームオブジェクトへ設定する。
        ReformDtlForm form = factory.createReformDtlForm(request);

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
        if (housingInfo.getSysHousingCd() == null) {
            // データの存在しない場合,メッセージ："｛0｝物件情報が存在しない"表示
            throw new NotFoundException();
        }
        model.put("housingInfo", housingInfo);

     // リフォーム関連情報を一括読込
        Map<String, Object> reform = this.reformManager.searchReform(form.getSysReformCd());

        // リフォームプラン情報の読込
        ReformPlan reformPlan = (ReformPlan) reform.get("reformPlan");

        // 事前チェック
        if (reformPlan == null) {
            // データの存在しない場合,メッセージ："｛0｝システムリフォーム情報が存在しない"表示
            throw new NotFoundException();
        }

        // リフォーム詳細の検索
        List<ReformDtl> reformDtlList = (List<ReformDtl>) reform.get("dtlList");
        // 閲覧権限
        String[] roleId = new String[reformDtlList.size()];
        for (int i = 0; i < reformDtlList.size(); i++) {
            roleId[i] = reformDtlList.get(i).getRoleId();
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
            form.setOldRoleId(roleId);
            model.put("reformDtlForm", form);
            return new ModelAndView("validationError", model);
        }

        // 正常処理
        // リフォーム詳細情報の新規追加
        if ("insert".equals(form.getCommand())) {
            this.reformManager.addReformDtl(form);
        } else if ("update".equals(form.getCommand())) {

            this.reformManager.updateReformDtl(form);
        }

        // ログインユーザーの情報を取得
        AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);
        // ユーザIDを取得
        String userId = String.valueOf(loginUser.getUserId());

        // 物件基本情報の最終更新日と最終更新者を更新の処理
        this.reformManager.updateEditTimestamp(form.getSysHousingCd(), form.getSysReformCd(), userId);

        // フォームオブジェクトのみをModelAndView に渡している
        model.put("reformDtlForm", form);
        return new ModelAndView("success", model);
    }
}
