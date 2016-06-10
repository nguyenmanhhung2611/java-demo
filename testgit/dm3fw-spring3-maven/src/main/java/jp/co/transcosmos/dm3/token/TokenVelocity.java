package jp.co.transcosmos.dm3.token;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.config.ValidScope;


/**
 * <pre>
 * 固定トークン出力用カスタムタグ（Velocity 用）
 * 
 * 担当者            修正日               修正内容
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.05.17  新規作成
 * H.Mizuno  2013.06.14  デフォルトのセッションキーを他のクラスと共通化
 * 
 * </pre>
*/
@DefaultKey("dm3token")
@ValidScope(Scope.REQUEST)
public class TokenVelocity {
    private static final Log log = LogFactory.getLog(TokenVelocity.class);

    // HTTP リクエストオブジェクト
    protected HttpServletRequest request;

    
	
    /**
     * 共通関数初期化処理<br/>
     * このメソッドを定義すると、Velocity から ToolContext を受取る事ができる。<br />
     * ToolContext には、HttpRequest や、ServletContext を格納しているので、必要で<br />
     * あれば、このメソッドを定義する。<br />
     * <br/>
     * @param values Velocity から渡されるプロパティ情報
     */
    protected void configure(Map<?,?> values)
    {
		log.debug("velocity dm3login init");

		// ToolContext を取得し、ローカルコンテキストから、Web 関連のオブジェクトを取得。
		ToolContext toolContext = (ToolContext)values.get("velocityContext");
    	this.request = (HttpServletRequest)toolContext.get("request");
    }


    /**
     * ワンタイムトークンの生成処理<br/>
     * <br/>
     * @param tokenName トークン名
     * @param idOnly true に設定すると、ID のみ出力、false の場合、hidden タグを出力
     * @return 生成されたID、または hidden タグ
     */
    public String oneTimeToken(){
    	return oneTimeToken(GenerateTokenId.DEFAULT_TOKEN_KEYNAME, false);
    }

    public String oneTimeToken(String tokenName){
    	return oneTimeToken(tokenName, false);
    }

    public String oneTimeToken(Boolean idOnly){
    	return oneTimeToken(GenerateTokenId.DEFAULT_TOKEN_KEYNAME, idOnly);
    }

    
    /**
     * ワンタイムトークンの生成処理<br/>
     * リクエストスコープに生成された ID が存在する場合は、その値を再利用する。<br/>
     * （同一ページ内であれば、同じ ID を出力する為。）<br/>
     * 存在しない場合は新規に ID を採番してリクエストスコープ、セッションに格納する。<br/>
     * ID 採番後、idOnly、tokenName の設定に応じてタグ、または ID を出力する。<br/>
     * <br/>
     * @param tokenName トークン名
     * @param idOnly true に設定すると、ID のみ出力、false の場合、hidden タグを出力
     * @return 生成されたID、または hidden タグ
     */
    public String oneTimeToken(String tokenName, Boolean idOnly){
    	// リクエストコープ内に、既にトークンが生成されているかをチェックする。
    	String tokenId = (String)request.getAttribute(tokenName);

    	// 生成されていない場合、新たなトークンを作成する。
    	if (tokenId == null || tokenId.length() == 0){
    		tokenId = GenerateTokenId.getId(request);
    		
    		// 新たなトークンを生成した場合、リクエストコープ、セッションに格納する。
    		request.setAttribute(tokenName, tokenId);
    		request.getSession().setAttribute(tokenName, tokenId);
    		
    		log.info("Generate One Time Token : name=" + tokenName + "," +
    				"id=" + tokenId + "," + "session id=" + request.getSession().getId()); 
    	}

    	return writeToken(tokenId, tokenName, idOnly);
    }


    
    /**
     * 固定トークンの生成処理<br/>
     * <br/>
     * @param tokenName トークン名
     * @param idOnly true に設定すると、ID のみ出力、false の場合、hidden タグを出力
     * @return 生成されたID、または hidden タグ
     */
    public String fixedToken(){
    	return fixedToken(GenerateTokenId.DEFAULT_TOKEN_KEYNAME, false);
    }

    public String fixedToken(String tokenName){
    	return fixedToken(tokenName, false);
    }

    public String fixedToken(Boolean idOnly){
    	return fixedToken(GenerateTokenId.DEFAULT_TOKEN_KEYNAME, idOnly);
    }
    


    /**
     * 固定トークンの生成処理<br/>
     * セッションスコープに生成された ID が存在する場合は、その値を再利用する。<br/>
     * （同一セッション内であれば、同じ ID を出力する為。）<br/>
     * 存在しない場合は新規に ID を採番してセッションに格納する。<br/>
     * ID 採番後、idOnly、tokenName の設定に応じてタグ、または ID を出力する。<br/>
     * <br/>
     * @param tokenName トークン名
     * @param idOnly true に設定すると、ID のみ出力、false の場合、hidden タグを出力
     * @return 生成されたID、または hidden タグ
     */
    public String fixedToken(String tokenName, Boolean idOnly){
    	// セッション内に、既にトークンが生成されているかをチェックする。
    	String tokenId = (String)request.getSession().getAttribute(tokenName);
    	
    	// 生成されていない場合、新たなトークンを作成する。
    	if (tokenId == null || tokenId.length() == 0){
    		tokenId = GenerateTokenId.getId(request);
    		
    		// 新たなトークンを生成した場合、セッションに格納する。
    		request.getSession().setAttribute(tokenName, tokenId);
    		
    		log.info("Generate Fixed Token : name=" + tokenName + "," +
    				"id=" + tokenId + "," + "session id=" + request.getSession().getId()); 
    	}

    	return writeToken(tokenId, tokenName, idOnly);
    }



    /**
     * ＩＤ、または、hidden タグの出力処理<br/>
     * <br/>
     * @param tokenId トークンID
     * @param tokenName トークン名
     * @param idOnly true に設定すると、ID のみ出力、false の場合、hidden タグを出力
     * @return 生成されたID、または hidden タグ
     */
    private String writeToken(String tokenId, String tokenName, Boolean idOnly){
		if (idOnly){
			return tokenId;
		} else {
			return "<input type=\"hidden\" name=\"" +
					tokenName + "\" value=\"" + tokenId + "\" />";
		}

    }
    
}
