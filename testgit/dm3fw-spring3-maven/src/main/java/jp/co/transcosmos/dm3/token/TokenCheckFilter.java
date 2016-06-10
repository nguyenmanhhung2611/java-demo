package jp.co.transcosmos.dm3.token;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.transcosmos.dm3.servlet.BaseFilter;
import jp.co.transcosmos.dm3.utils.ServletUtils;

public class TokenCheckFilter extends BaseFilter  {

	private static final Log log = LogFactory.getLog(TokenCheckFilter.class);

	/** トークン名 */
	private String tokenName = GenerateTokenId.DEFAULT_TOKEN_KEYNAME;

	/** エラー発生時のリダイレクト先 URL */
	private String redirectUrl = "";

	/** true の場合、トークンの照合チェック後にセッションから token を削除する。 （デフォルト true ） */
	private boolean removeAfterToken = true;



	/**
	 * トークン名を設定する。<br/>
	 * デフォルトは dm3token。　変更した場合はこのプロパティに値を設定する。<br/>
	 * <br/>
	 * @param tokenName トークン名
	 */
	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}

	/**
	 * リダイレクト先 URL を設定する。<br/>
	 * <br/>
	 * @param redirectUrl リダイレクト先 URL
	 */
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	/**
	 * トークンの照合後、セッションから削除しない場合、このプロパティに false を設定する。<br/>
	 * （デフォルト true ）<br/>
	 * <br/>
	 * @param removeAfterToken true の場合、セッションからトークンを削除する。
	 */
	public void setRemoveAfterToken(boolean removeAfterToken) {
		this.removeAfterToken = removeAfterToken;
	}

	@Override
	protected void filterAction(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// トークンの照合を行う
		boolean isError = GenerateTokenId.hasError(request, this.tokenName);

		if (this.removeAfterToken){
			// チェック後にトークンをセッションから削除する。
			GenerateTokenId.removeId(request, this.tokenName);
		}

		if (isError) {
			log.warn("token error(filter check). token is " + request.getParameter(this.tokenName));

			// エラーが発生した場合、指定された URL へリダイレクトする。
            response.sendRedirect(ServletUtils.fixPartialURL(
                    request.getContextPath() + this.redirectUrl, request));
		} else {
			// 問題なければ次の Filter へ Chain する。
			chain.doFilter(request, response);
		}

	}

}
