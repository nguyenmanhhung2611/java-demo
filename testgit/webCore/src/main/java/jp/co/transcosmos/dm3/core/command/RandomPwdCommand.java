package jp.co.transcosmos.dm3.core.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.core.util.CreatePassword;
import jp.co.transcosmos.dm3.command.Command;


/**
 * ランダムパスワードの生成する.
 * <p>
 * 【復帰する View 名】<br/>
 * <ul>
 * <li>success</li>:正常終了（生成したパスワードの JSON レスポンス）
 * </ul>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.27	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class RandomPwdCommand implements Command {

	/** パスワード生成クラス */
	protected CreatePassword createPassword;



	/**
	 * パスワード生成クラスを設定する。<br/>
	 * <br/>
	 * @param createPassword パスワード生成クラス
	 */
	public void setCreatePassword(CreatePassword createPassword) {
		this.createPassword = createPassword;
	}



	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// パスワードを生成する。
		return new ModelAndView("success", "password", this.createPassword.getPassword());
	}

}
