package jp.co.transcosmos.dm3.utils;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.command.Command;


/**
 * 横断的に使用する 共通オブジェクトを リクエストスコープへ格納する。<br/>
 * このコマンドクラスを scope = protptype で bean 定義し、Filter として URL マッピングして使用する。<br/>
 * URL マッピングに該当したリクエストに対してリクエストスコープへ指定された Bean がロードされる。<br/>
 * <br/>
 * @author H.Mizuno
 *
 */
public class AddBeanToRequestScopeCommand implements Command {

    private static final Log log = LogFactory.getLog(AddBeanToRequestScopeCommand.class);



	/** リクエストスコープに格納する Bean の Map 情報 */
	private Map<String, Object> beanMap;
	
	/**
	 * リクエストスコープに格納する Bean を設定する。<br/>
	 * Map の構成は下記の通り。<br/>
	 * <br/>
	 *   Key = Bean ID<br/>
	 *   Value = 格納するオブジェクト<br/> 
	 * <br/>
	 * @param beanMap リクエストスコープに格納するオブジェクトの Map 情報
	 */
	public void setBeanMap(Map<String, Object> beanMap) {
		this.beanMap = beanMap;
	}


	
	/**
	 * メイン処理<br/>
	 * 指定されたオブジェクトをリクエストスコープへ格納する。<br/>
	 * <br/>
	 * @param req HTTP リクエスト
	 * @param res HTTP レスポンス
	 * @return null 固定
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse res) throws Exception {

		if (this.beanMap != null) {
			for (Entry<String, Object> e : this.beanMap.entrySet()){
	            req.setAttribute(e.getKey(), e.getValue());
	            log.info("Added " + e.getValue().getClass().getSimpleName() + " to request attribute: " + e.getKey());
			}
		}

		return null;
	}
	
}
