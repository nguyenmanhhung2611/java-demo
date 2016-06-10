package jp.co.transcosmos.dm3.token;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <pre>
 * 固定トークン出力用カスタムタグ
 * 
 * 担当者            修正日               修正内容
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.05.17  新規作成
 * H.Mizuno  2013.06.14  デフォルトのセッションキーを定数化
 * 
 * </pre>
*/
public class FixedTokenTag implements Tag {
    private static final Log log = LogFactory.getLog(FixedTokenTag.class);

    // ページコンテキスト
    private PageContext pageContext;
    // 親タグオブジェクト
    private Tag parentTag;
    // トークン名(デフォルト値は　dm3token　)
    private String tokenName;
    // ID のみの出力を行う場合、true を設定する（デフォルト値は false）
    // false が設定されている場合、hidden タグが出力される。
    private Boolean idOnly;


	
    /**
     * タグ出力の開始処理<br/>
     * セッションスコープに生成された ID が存在する場合は、その値を再利用する。<br/>
     * （同一セッション内であれば、同じ ID を出力する為。）<br/>
     * 存在しない場合は新規に ID を採番してセッションに格納する。<br/>
     * ID 採番後、idOnly、tokenName の設定に応じてタグ、または ID を出力する。<br/>
     * <br/>
     * @return SKIP_BODY 固定
     * @throws JspException
     */
	@Override  
    public int doStartTag() throws JspException {

		// トークン名の属性が設定されていない場合、初期値を設定する。
    	if (this.tokenName == null || this.tokenName.length() == 0)
    		this.tokenName = GenerateTokenId.DEFAULT_TOKEN_KEYNAME;

		// デフォルトは、HTML タグ出力
    	if (this.idOnly == null)
    		this.idOnly = false;
    	
    	
    	// セッションスコープ内に、既にトークンが生成されているかをチェックする。
    	String tokenId = (String)pageContext.getSession().getAttribute(this.tokenName);

    	// 生成されていない場合、新たなトークンを作成する。
    	if (tokenId == null || tokenId.length() == 0){
        	HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
    		tokenId = GenerateTokenId.getId(request);
    		
    		// 新たなトークンを生成した場合、セッションに格納する。
    		pageContext.getSession().setAttribute(this.tokenName, tokenId);
    		
    		log.info("Generate Fixed Token : name=" + this.tokenName + "," +
    				"id=" + tokenId + "," + "session id=" +pageContext.getSession().getId()); 
    	}

    	// トークンの出力
		try {
			if (this.idOnly){
				this.pageContext.getOut().write(tokenId);
			} else {
				this.pageContext.getOut().write("<input type=\"hidden\" name=\"" +
						this.tokenName + "\" value=\"" + tokenId + "\" />");
			}
        } catch (IOException err) {
            throw new JspTagException("Error writing Fixed Toekn");
        }
        return SKIP_BODY;
    }
    
	

    /**
     * タグ出力の終了処理<br/>
     * Body 部分が存在しないので、プロパティのリセット以外は何もしない。<br/>
     * doStartTag() ～ doEndTag() 間以外はスレッドセーフでないので、プロパティの<br/>
     * 残骸は残さない様にしている。<br/>
     * <br/>
     * @return EVAL_PAGE 固定
     * @throws JspException
     */
	@Override
    public int doEndTag() throws JspException {
        this.pageContext = null;
        this.parentTag = null;
        this.tokenName = null;
        this.idOnly = null;

        return EVAL_PAGE;
    }


	
    /**
     * オブジェクトの廃棄処理<br/>
     * オブジェクトが不要と判断された時に実行される。　何もしない。<br/>
     * <br/>
     * @return EVAL_PAGE 固定
     * @throws JspException
     */
	@Override
	public void release() {
	}
    

	// setter、getter
    @Override
	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}

	@Override
	public void setParent(Tag parentTag) {
		this.parentTag = parentTag;
	}

	@Override
	public Tag getParent() {
		return this.parentTag;
	}

	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}

	public void setIdOnly(Boolean idOnly) {
		this.idOnly = idOnly;
	}

}
