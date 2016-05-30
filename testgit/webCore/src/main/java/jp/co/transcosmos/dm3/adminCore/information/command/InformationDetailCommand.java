package jp.co.transcosmos.dm3.adminCore.information.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.InformationManage;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.information.form.InformationFormFactory;
import jp.co.transcosmos.dm3.core.model.information.form.InformationSearchForm;
import jp.co.transcosmos.dm3.dao.JoinResult;

import org.springframework.web.servlet.ModelAndView;

/**
 * お知らせ詳細画面　（削除処理の確認画面兼用）.
 * <p>
 * リクエストパラメータ（informaitonNo）で渡された値に該当するお知らせ情報を取得し画面表示する。
 * <br/>
 * 【復帰する View 名】<br/>
 * <ul>
 * <li>success<li>:検索処理正常終了
 * <li>notFound<li>:該当データが存在しない場合
 * </ul>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.03.30	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class InformationDetailCommand implements Command  {

	/** お知らせメンテナンスを行う Model オブジェクト */
	protected InformationManage informationManager;
	
	/**
	 * お知らせメンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 * @param informationManager お知らせメンテナンスの model オブジェクト
	 */
	public void setInformationManager(InformationManage informationManager) {
		this.informationManager = informationManager;
	}

	/**
	 * お知らせ詳細表示処理<br>
	 * <br>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	 * @return ModelAndView　のインスタンス
	*/
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// リクエストパラメータを格納した model オブジェクトを生成する。
		// このオブジェクトは View 層への値引渡しに使用される。
        Map<String, Object> model = createModel(request);
        InformationSearchForm searchForm = (InformationSearchForm) model.get("searchForm");
        
        // お知らせ情報を取得
        JoinResult informationDetail = this.informationManager.searchAdminInformationPk(searchForm.getInformationNo());
        
        // 該当するデータが存在しない場合
        // 該当無し画面を表示する。
        if (informationDetail == null) {
        	throw new NotFoundException();
        }
        
        // 取得できた場合は model に設定する。
		model.put("informationDetail", informationDetail);

		return new ModelAndView("success", model);
	}
	
	/**
	 * model オブジェクトを作成し、リクエストパラメータを格納した form オブジェクトを格納する。<br/>
	 * <br/>
	 * @param request HTTP リクエストパラメータ
	 * @return パラメータを設定した Form を格納した model オブジェクトを復帰する。
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		// リクエストパラメータを取得して Form オブジェクトを作成する。
		InformationFormFactory factory = InformationFormFactory.getInstance(request);
		model.put("searchForm", factory.createInformationSearchForm(request));

		return model;
	}

}
