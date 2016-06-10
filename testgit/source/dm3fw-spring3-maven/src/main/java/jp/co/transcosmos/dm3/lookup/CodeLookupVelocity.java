package jp.co.transcosmos.dm3.lookup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.config.ValidScope;



/**
 * <pre>
 * dm3lookup カスタムタグの Velocity 版
 * Code Lookup Manager を使用してコード変換、および、プルダウンリスト用の List を取得する。
 *
 * 担当者            修正日               修正内容
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.18  新規作成
 * H.Mizuno  2013.04.19  lookupForEach 対応
 * 
 * </pre>
*/
@DefaultKey("dm3lookup")
@ValidScope(Scope.REQUEST)
public class CodeLookupVelocity {
    private static final Log log = LogFactory.getLog(CodeLookupVelocity.class);

    // ロールを管理している、セッションの Key （V1、V2 認証用）
    private String codeLookupManagerParameterName = "codeLookupManager";

    // HTTP リクエストオブジェクト
    protected HttpServletRequest request;

    // HTTP レスポンスオブジェクト
    protected HttpServletResponse response;

    // サーブレット・コンテキスト
    protected ServletContext servletContext;

	

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
    	this.servletContext = (ServletContext)toolContext.get("servletContext");
    	this.request = (HttpServletRequest)toolContext.get("request");
    	this.response = (HttpServletResponse)toolContext.get("response");
    }

	
    /**
     * Code Lookup Manager による、コード変換処理<br/>
     * <br/>
     * @param pLookupName 使用する lookup 名
     * @param pLookupKey 選択状態にする値
     * @return コードに該当する文字列
     */
    public String lookup(String pLookupName, String pLookupKey) {
    	return lookup(pLookupName, pLookupKey, null, null);
    }


    
    /**
     * Code Lookup Manager による、コード変換処理<br/>
     * <br/>
     * @param pLookupName 使用する lookup 名
     * @param pLookupKey 選択状態にする値
     * @param pWildcard 置換文字列（{1} などの部分を配列に従い置換する。）
     * @return コードに該当する文字列
     */
    public String lookup(String pLookupName, String pLookupKey, String pWildcard) {
    	return lookup(pLookupName, pLookupKey, new String[] {pWildcard}, null);
    }

    public String lookup(String pLookupName, String pLookupKey, String pWildcards[]) {
    	return lookup(pLookupName, pLookupKey, pWildcards, null);
    }

    public String lookup(String pLookupName, String pLookupKey, List<String> pWildcards) {
    	String wildcards[] = pWildcards.toArray(new String[pWildcards.size()]);
    	return lookup(pLookupName, pLookupKey, wildcards, null);
    }

    
    
    /**
     * Code Lookup Manager による、コード変換処理<br/>
     * <br/>
     * @param pLookupName 使用する lookup 名
     * @param pLookupKey 選択状態にする値
     * @param pWildcard 置換文字列（{1} などの部分を配列に従い置換する。）
     * @param pManageId Code lookup Manager の Bean ID
     * @return コードに該当する文字列
     */
    public String lookup(String pLookupName, String pLookupKey, String pWildcards[], String pManageId) {

    	// まず有り得ないと思うが、同一ページ内で異なる CodeLookup Manager が指定された場合、
    	// このオブジェクトのプロパティを書き換えると問題が発生する可能性がある。
    	// よって、処理にしようする変数は別に確保する。
        String managerParam = this.codeLookupManagerParameterName;
        if (pManageId != null && pManageId.length() > 0) {
        	managerParam = "codeLookupManager";
        }

        // Code lookup マネージャから値を受取り復帰する。
        CodeLookupManager manager = (CodeLookupManager) this.request.getAttribute(managerParam);
        if (manager != null) {
        	return manager.lookupValue(pLookupName, pLookupKey, pWildcards);
        } else {
            log.warn("WARNING: Code lookup manager not found");
            return null;
        }
    }


    /**
     * Code Lookup Manager による、リスト取得処理<br/>
     * 戻り値は、該当データの MAP が格納された List オブジェクトを復帰する。<br/>
     * Map は、下記構造で値が設定される。<br/>
     *     ・Key = "key" : CodeLookupList で定義されている Key 値<br/>
     *     ・key = "value" : CodeLookupList で定義されている Value 値<br/>
     * <br/>
     * @param pLookupName 使用する lookup 名
     * @return 該当データの List 情報
     */
    public List<Map<String, String>> lookupForEach(String pLookupName){
    	return lookupForEach(pLookupName, null, null, null, null);
    }


    
    /**
     * Code Lookup Manager による、リスト取得処理<br/>
     * 戻り値は、該当データの MAP が格納された List オブジェクトを復帰する。<br/>
     * Map は、下記構造で値が設定される。<br/>
     *     ・Key = "key" : CodeLookupList で定義されている Key 値<br/>
     *     ・key = "value" : CodeLookupList で定義されている Value 値<br/>
     * <br/>
     * @param pLookupName 使用する lookup 名
     * @param pWildcard 置換文字列（{1} などの部分を配列に従い置換する。）
     * @return 該当データの List 情報
     */
    public List<Map<String, String>> lookupForEach(String pLookupName, String pWildcard){
    	return lookupForEach(pLookupName, new String[] {pWildcard}, null, null, null);
    }
    
    public List<Map<String, String>> lookupForEach(String pLookupName, String pWildcards[]){
    	return lookupForEach(pLookupName, pWildcards, null, null, null);
    }
    
    public List<Map<String, String>> lookupForEach(String pLookupName, List<String> pWildcards){
    	String wildcards[] = pWildcards.toArray(new String[pWildcards.size()]);
    	return lookupForEach(pLookupName, wildcards, null, null, null);
    }
    

    
    /**
     * Code Lookup Manager による、リスト取得処理<br/>
     * 戻り値は、該当データの MAP が格納された List オブジェクトを復帰する。<br/>
     * Map は、下記構造で値が設定される。<br/>
     *     ・Key = "key" : CodeLookupList で定義されている Key 値<br/>
     *     ・key = "value" : CodeLookupList で定義されている Value 値<br/>
     * <br/>
     * @param pLookupName 使用する lookup 名
     * @param pWildcard 置換文字列（{1} などの部分を配列に従い置換する。）
     * @param pManageId Code lookup Manager の Bean ID
     * @return 該当データの List 情報
     */
    public List<Map<String, String>> lookupForEach(String pLookupName, String pWildcards[], String pManageId){
    	return lookupForEach(pLookupName, pWildcards, pManageId, null, null);
    }



    /**
     * Code Lookup Manager による、リスト取得処理<br/>
     * 戻り値は、該当データの MAP が格納された List オブジェクトを復帰する。<br/>
     * Map は、下記構造で値が設定される。<br/>
     *     ・Key = pKeyParameter : CodeLookupList で定義されている Key 値<br/>
     *     ・key = pValueParameter : CodeLookupList で定義されている Value 値<br/>
     *     ※pKeyParameter、pValueParameter が null 値の場合、"key", "value"　が使用されえる。<br/>
     * <br/>
     * @param pLookupName 使用する lookup 名
     * @param pWildcard 置換文字列（{1} などの部分を配列に従い置換する。）
     * @param pManageId Code lookup Manager の Bean ID
     * @return 該当データの List 情報
     */
    public List<Map<String, String>> lookupForEach(String pLookupName, String pWildcards[], String pManageId, String pKeyParameter, String pValueParameter){

    	// Key 属性名
        String keyParameter = "key";
        if (pKeyParameter != null && pKeyParameter.length() > 0){
        	keyParameter = pKeyParameter; 
        }

        // value 属性名
        String valueParameter = "value";
        if (pValueParameter != null && pValueParameter.length() > 0){
        	valueParameter = pValueParameter;
        }

        // Colde Lookup Manager の Bean ID 名
        String managerParam = this.codeLookupManagerParameterName;
        if (pManageId != null && pManageId.length() > 0) {
        	managerParam = "codeLookupManager";
        }

        // Code lookup マネージャから値を受取り復帰する。
        CodeLookupManager manager = (CodeLookupManager) this.request.getAttribute(managerParam);
        if (manager != null) {
        	Iterator<String> lookupKeys = manager.getKeysByLookup(pLookupName);
        	if (lookupKeys == null) return null;

    		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        	while(lookupKeys.hasNext()){
        		// 戻り値用のリストオブジェクト
        		Map<String, String> map = new HashMap<String, String>();

        		// 取得したデータを Map に設定する。
        		String key = lookupKeys.next();
        		map.put(keyParameter, key);
        		map.put(valueParameter, manager.lookupValue(pLookupName, key, pWildcards));

        		list.add(map);
        	}
        	return list;

        } else {
        	log.warn("WARNING: Code lookup manager not found");
            return null;
        }
    }
}
