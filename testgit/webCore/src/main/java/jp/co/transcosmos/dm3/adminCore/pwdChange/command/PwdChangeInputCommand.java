package jp.co.transcosmos.dm3.adminCore.pwdChange.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;

/**
 * 管理ユーザーパスワード変更入力画面.
 * <p>
 * セッションからログイン情報を取得し、パスワードの最終更新日を画面出力する。<br/>
 * <br/>
 * 【復帰する View 名】<br/>
 * <ul>
 * <li>success<li>:入力画面表示
 * </ul>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.23	Shamaison を参考に新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class PwdChangeInputCommand implements Command {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<>();


		// ログイン情報をセッションから取得
		AdminUserInterface userInfo = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);

		// パスワードの最終更新日を取得
		model.put("pwdChangeDate", userInfo.getLastPasswdChange());

		return new ModelAndView("success", model);
	}

}
