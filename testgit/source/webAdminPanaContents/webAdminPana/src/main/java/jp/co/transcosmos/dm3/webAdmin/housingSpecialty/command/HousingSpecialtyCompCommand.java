package jp.co.transcosmos.dm3.webAdmin.housingSpecialty.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSpecialtyForm;
import jp.co.transcosmos.dm3.form.FormPopulator;

import org.springframework.web.servlet.ModelAndView;

/**
 * 物件設備情報の追加、削除処理.
 * <p>
 * 【新規登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。</li>
 * <li>バリデーションが正常終了した場合、物件設備情報を新規登録する。</li>
 * </ul>
 * <br/>
 * 【復帰する View 名】<br/>
 * <ul>
 * <li>success</li>:正常終了（リダイレクトページ）
 * <li>redirect</li>:redirect画面表示
 * <li>comp</li>:完了画面表示
 * </ul>
 * <p>
 *
 * <pre>
 * 担当者       修正日     修正内容
 * ------------ ----------- -----------------------------------------------------
 * liu.yandong  2015.04.10  新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class HousingSpecialtyCompCommand implements Command {

	/** 物件情報用 Model オブジェクト */
    protected PanaHousingPartThumbnailProxy panaHousingManager;

    /**
	 * 物件情報用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param PanaHousingPartThumbnailProxy 物件情報用 Model オブジェクト
	 */
    public void setPanaHousingManager(PanaHousingPartThumbnailProxy panaHousingManager) {
        this.panaHousingManager = panaHousingManager;
    }

	/**
	 * 物件設備情報の追加、変更、削除処理<br>
	 * <br>
	 * @param request クライアントからのHttpリクエスト。
	 * @param response クライアントに返すHttpレスポンス。
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// リクエストパラメータを格納した model オブジェクトを生成する。
		// このオブジェクトは View 層への値引渡しに使用される。
        Map<String, Object> model = createModel(request);
        PanaHousingSpecialtyForm inputForm = (PanaHousingSpecialtyForm) model.get("inputForm");

        // ログインユーザーの情報を取得する。　（タイムスタンプの更新用）
        AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);

        // 完了画面でリロードした場合、更新処理が意図せず実行される問題が発生する。
        // その問題を解消する為、view 名で "success"　を指定すると自動リダイレクト画面が表示される。
        // このリダイレクト画面は、command パラメータを "redirect"　に設定して完了画面へリクエストを
        // 送信する。
        // よって、command = "redirect" の場合は、ＤＢ更新は行わず、完了画面を表示する。
        String command = inputForm.getCommand();
        if (command != null && "redirect".equals(command)){
        	return new ModelAndView("comp" , model);
        }

        // 各種処理を実行
        // 物件基本情報を取得する。
		Housing housing = this.panaHousingManager.searchHousingPk(inputForm.getSysHousingCd(), true);

		// データの存在しない場合。
		if (housing == null) {
			throw new NotFoundException();
		}

        try {
        	this.panaHousingManager.updateHousingEquip(inputForm, (String)loginUser.getUserId());

        } catch (NotFoundException e) {
            // システム物件CD、設備CDが存在しない場合は、該当なし画面へ
        	return new ModelAndView("notFound", model);

        }

		return new ModelAndView("success" , model);
	}

	/**
	 * model オブジェクトを作成し、リクエストパラメータを格納した form オブジェクトを格納する。<br/>
	 * <br/>
	 * @param request HTTP リクエストパラメータ
	 * @return パラメータを設定した Form を格納した model オブジェクトを復帰する。
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<>();

		// リクエストパラメータを取得して Form オブジェクトを作成する。
		PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);
		PanaHousingSpecialtyForm inputForm = factory.createPanaHousingSpecialtyForm();
        FormPopulator.populateFormBeanFromRequest(request, inputForm);
        model.put("inputForm", inputForm);

        // 検索条件、および、画面コントロールパラメータを取得する。
 		PanaHousingSearchForm searchForm = factory.createPanaHousingSearchForm(request);
 		model.put("searchForm", searchForm);

		return model;
	}
}
