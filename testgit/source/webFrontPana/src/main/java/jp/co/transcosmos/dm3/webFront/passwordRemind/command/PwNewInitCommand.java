package jp.co.transcosmos.dm3.webFront.passwordRemind.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.corePana.model.member.PanaMypageUserManageImpl;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * パスワードリマインダー
 * パスワードリマインダー画面を表示する。
 *
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *
 * 担当者       修正日     修正内容
 * ------------ ----------- -----------------------------------------------------
 * 焦     2015.04.24  新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class PwNewInitCommand implements Command {

	/** 会員情報用 Model オブジェクト */
	private PanaMypageUserManageImpl panaMypageUserManager;

	/**
	 * @param mypageUserManager セットする mypageUserManager
	 */
	public void setPanaMypageUserManager(PanaMypageUserManageImpl panaMypageUserManager) {
		this.panaMypageUserManager = panaMypageUserManager;
	}

	/**
	 * パスワードリマインダー画面表示処理<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @param response
	 *            HTTP レスポンス
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 取得したデータをレンダリング層へ渡す
		Map<String, Object> model = new HashMap<String, Object>();

		String remindId = request.getParameter("remindId");

		// 有効期限チェック
		Boolean dateFlg = false;
		try{
			dateFlg = this.panaMypageUserManager.dateCheck(remindId);
		}catch(NotFoundException e){
			return new ModelAndView("404", model);
		}

		if(!dateFlg){

			return new ModelAndView("error", model);
		}

		model.put("remindId", remindId);
		return new ModelAndView("success", model);
	}

}
