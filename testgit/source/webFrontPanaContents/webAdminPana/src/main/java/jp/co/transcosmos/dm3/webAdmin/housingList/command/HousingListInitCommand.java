package jp.co.transcosmos.dm3.webAdmin.housingList.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 物件一覧の初期化画面
 * 都道府県情報を取得。
 *
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *
 * 担当者       修正日     修正内容
 * ------------ ----------- -----------------------------------------------------
 * guo.zhonglei     2015.04.02  新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class HousingListInitCommand implements Command {

	/** 共通情報取用 Model オブジェクト */
	private PanaCommonManage panamCommonManager;

	/**
	 * 共通情報取用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param PanaCommonManage 共通情報取用 Model オブジェクト
	 */
	public void setPanamCommonManager(PanaCommonManage panamCommonManager) {
		this.panamCommonManager = panamCommonManager;
	}

	/**
	 * 物件一覧の初期化表示処理<br>
	 * <br>
	 *
	 * @param request
	 *            クライアントからのHttpリクエスト。
	 * @param response
	 *            クライアントに返すHttpレスポンス。
	 */
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	// 検索パラメータ受信
    	Map<String, Object> model = new HashMap<String, Object>();
    	// リクエストパラメータを取得して Form オブジェクトを作成する。
    	PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);
    	PanaHousingSearchForm searchForm = factory.createPanaHousingSearchForm(request);
        // 画面項目の初期化
        searchForm.setDefaultData(this.panamCommonManager.getPrefMstList(),model, true);

        // 都道府県Listの値を view 層へ渡すパラメータとして設定している。
        model.put("searchForm", searchForm);

        return new ModelAndView("success", model);
    }
}
