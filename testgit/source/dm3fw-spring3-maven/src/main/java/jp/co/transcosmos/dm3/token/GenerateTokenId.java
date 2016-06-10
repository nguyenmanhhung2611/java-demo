package jp.co.transcosmos.dm3.token;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.transcosmos.dm3.utils.EncodingUtils;


/**
 * <pre>
 * トークンＩＤ出力処理
 * 
 * 担当者            修正日               修正内容
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.05.17  新規作成
 * H.Mizuno  2013.06.14  トークンを削除する機能を追加
 *                       デフォルトのセッションキーの定数をこのクラスに宣言
 * 
 * </pre>
*/
public class GenerateTokenId {
    private static final Log log = LogFactory.getLog(GenerateTokenId.class);

	// 乱数生成オブジェクト（排他制御にも使用）
    private static final Random rnd = new Random();
    
    // トークンのセッションキー名　（デフォルト値）
    public static final String DEFAULT_TOKEN_KEYNAME = "dm3token";
    


    /**
     * トークンに使用するID を採番する。<br/>
     * ローカルアドレス、ローカルポート、サーバーポート、システム時間、乱数の値を DM5 でハッシュして<br/>
     * 生成する。<br/>
     * <br/>
     * @param request HTTP リクエスト
     * @return 生成された ID
     */
	public static String getId(HttpServletRequest request){
        synchronized (rnd) {
            return EncodingUtils.md5Encode(request.getLocalAddr() + " " + 
                                           request.getLocalPort() + " " +
                                           System.currentTimeMillis() + " " + 
                                           request.getServerPort() + " " +
                                           rnd.nextLong());
        }
	}



    /**
     * セッションからトークンを削除する　（デフォルトトークン名用）<br/>
     * <br/>
     * @param request HTTP リクエスト
     */
	public static void removeId(HttpServletRequest request){
		removeId(request, GenerateTokenId.DEFAULT_TOKEN_KEYNAME);
	}

	
	
    /**
     * セッションからトークンを削除する<br/>
     * <br/>
     * @param request HTTP リクエスト
     * @param tokenName トークン名
     */
	public static void removeId(HttpServletRequest request, String tokenName){
		request.getSession().removeAttribute(tokenName);
		log.info("remove token id : "  + tokenName);
	}

	

	/**
	 * リクエストされたトークンと、セッションのトークンを照合する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param tokenName　トークン名
	 * 
	 * @return エラーがある場合、true を復帰する。
	 */
	public static boolean hasError(HttpServletRequest request, String tokenName) {

		boolean isError = false;


		// トークンを取得
		String reqTokenId = request.getParameter(tokenName);
		String sessTokenId = (String)request.getSession().getAttribute(tokenName);

		if (reqTokenId == null || reqTokenId.length() ==0) {
			// リクエストパラメータにトークンが無い場合エラー
			isError = true;

		} else if (sessTokenId == null || sessTokenId.length() ==0) { 
			// セッションにトークンが無い場合もエラー
			isError = true;

		} else if (!reqTokenId.equals(sessTokenId)) { 
			// トークンが一致しない場合もエラー
			isError = true;
		}

		return isError;
	}

}
