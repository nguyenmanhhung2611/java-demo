package jp.co.transcosmos.dm3.webAdmin.recommendPoint.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import jp.co.transcosmos.dm3.corePana.model.housing.form.RecommendPointForm;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * 物件おすすめポイント情報の更新処理.
 * <p>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。</li>
 * <li>バリデーションが正常終了した場合、物件おすすめポイント情報を更新する。</li>
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
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * 郭中レイ     2015.4.10  新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class RecommendPointCompCommand implements Command {

	/** 物件情報を行う Model オブジェクト */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/**
	 * 物件情報を行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 * @param panaHousingManager 物件情報の model オブジェクト
	 */
	public void setPanaHousingManager(PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
	}

	/**
	 * 物件おすすめポイント情報の更新処理<br>
	 * <br>
	 * @param request クライアントからのHttpリクエスト。
	 * @param response クライアントに返すHttpレスポンス。
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// リクエストパラメータを格納した model オブジェクトを生成する。
		// このオブジェクトは View 層への値引渡しに使用される。
        Map<String, Object> model = createModel(request);
        RecommendPointForm inputForm = (RecommendPointForm) model.get("inputForm");


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

		// バリデーションを実行
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
		if (!inputForm.validate(errors)) {
			// バリデーションエラー時は、エラー情報を model に設定し、入力画面を表示する。
			model.put("errors", errors);

			return new ModelAndView("input" , model);
		}

		// 事前チェック
		if (!targetExist(inputForm)) {
			throw new NotFoundException();
		}

        // 各種処理を実行
        try {
        	this.panaHousingManager.updateHousing(inputForm, (String)loginUser.getUserId());

        } catch (NotFoundException e) {
            // ログインID が存在しない場合は、該当なし画面へ
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
        RecommendPointForm form = factory.createRecommendPointForm(request);
        PanaHousingSearchForm searchForm = factory.createPanaHousingSearchForm(request);

		model.put("inputForm", form);
		model.put("searchForm", searchForm);

		return model;

	}

	/**
	 * 事前チェックを行う。<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            入力値が格納された Form オブジェクト
	 * @return true データが存在の場合、 false データが存在しない場合
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 * @exception NotFoundException
	 *                更新対象が存在しない場合
	 */
	private boolean targetExist(RecommendPointForm inputForm) throws Exception,
			NotFoundException {

		// 物件情報を取得する。
		Housing housing = this.panaHousingManager.searchHousingPk(inputForm.getSysHousingCd(), true);

		// データの存在しない場合。
		if (housing == null) {
			return false;
		}
		return true;
	}

}
