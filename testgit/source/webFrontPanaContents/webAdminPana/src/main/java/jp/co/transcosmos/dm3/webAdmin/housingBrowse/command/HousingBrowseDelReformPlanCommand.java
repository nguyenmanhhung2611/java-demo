package jp.co.transcosmos.dm3.webAdmin.housingBrowse.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.model.ReformManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingStatusForm;

import org.springframework.web.servlet.ModelAndView;

/**
 * リフォーム情報削除
 * <p>
 * 【削除の場合】<br/>
 * <ul>
 * <li>リフォーム情報データを削除処理する。</li>
 * </ul>
 *
 * 担当者        修正日       修正内容
 * ------------ ----------- -----------------------------------------------------
 * Zhang.       2015.03.11	新規作成
 * Duong.Nguyen 2015.08.18  Use form submission to delete reform plan instead of using ajax. So remove json result such as map of reform plans, user information, etc... in MAV, put request models to MAV.  
 *
 * 注意事項
 *
 * </pre>
 */
public class HousingBrowseDelReformPlanCommand implements Command {

	/** 物件情報メンテナンス用 Model */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/** リフォーム情報を管理用 Model */
	private ReformManage reformManager;

	/**
	 * 物件情報メンテナンス用 Model（core） を設定する。<br/>
	 * <br/>
	 *
	 * @param panaHousingManager
	 *            物件情報メンテナンス用 Model（core）
	 */
	public void setPanaHousingManager(
			PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
	}

	/**
	 * リフォーム情報を管理用 Model（core） を設定する。<br/>
	 * <br/>
	 *
	 * @param reformManager
	 *            リフォーム情報を管理用 Model（core）
	 */
	public void setReformManager(ReformManage reformManager) {
		this.reformManager = reformManager;
	}

    /**
     * リフォーム情報入力画面処理<br>
     * <br>
     *
     * @param request
     *            HTTP リクエスト
     * @param response
     *            HTTP レスポンス
     */
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // リクエストパラメータを格納した model オブジェクトを生成する。
        // このオブジェクトは View 層への値引渡しに使用される。
        Map<String, Object> model = createModel(request);
        PanaHousingStatusForm form = (PanaHousingStatusForm) model.get("inputForm");

        // ユーザー情報取得
        AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);

        // リフォーム情報データを削除
        reformManager.delReformPlan(form.getSysHousingCd(), form.getSysReformCd(), (String) loginUser.getUserId());

        // 物件情報のタイムスタンプを更新
        panaHousingManager.updateEditTimestamp(form.getSysHousingCd(), (String) loginUser.getUserId());

        return new ModelAndView("success", model);
    }

	/**
	 * model オブジェクトを作成する。<br/>
	 * <br/>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @return パラメータを設定した Form を格納した model オブジェクトを復帰する。
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		// リクエストパラメータを取得して Form オブジェクトを作成する。
		PanaHousingFormFactory factory = PanaHousingFormFactory
				.getInstance(request);
		PanaHousingStatusForm requestForm = factory
				.createPanaHousingStatusForm(request);
		model.put("inputForm", requestForm);

		// 検索条件、および、画面コントロールパラメータを取得する。
		PanaHousingSearchForm searchForm = factory
				.createPanaHousingSearchForm(request);
		model.put("searchForm", searchForm);

		return model;
	}
}
