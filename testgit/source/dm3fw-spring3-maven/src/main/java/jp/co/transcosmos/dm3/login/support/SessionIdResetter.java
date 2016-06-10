package jp.co.transcosmos.dm3.login.support;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * <pre>
 * セッション ID リセットクラス
 * ログイン時、もしくは、ログアウト時にセッション ID を新たな ID に変更する。
 *
 * 担当者            修正日               修正内容
 * ----------------------------------------------------------------------------
 * H.Mizuno  2015.06.22  新規作成
 * H.Mizuno  2015.06.23  SessionResetter から、SessionIdResetter に変更
 *
 * </pre>
*/
public class SessionIdResetter {

	/**
	 * セッションID をリセットする。<br/>
	 * 当初、セッションオブジェクト自体を破棄していたが、現行バージョンでは、旧セッションを破棄し、
	 * 新たなセッションを作成後、旧セッションに格納されていたオブジェクトを新セッションに移動して
	 * いる。<br/>
	 * @param request HTTP リクエスト
	 * @return 新セッションオブジェクト
	 */
	public HttpSession resetSession(HttpServletRequest request) {

		// 現在のセッションを取得
    	HttpSession session = request.getSession();

		// 現在のセッションオブジェクトを取得
		Map<String, Object> sessionObjs = getSessionObjects(session);

		// セッションID の破棄
		session = request.getSession();
		session.invalidate();
		session = request.getSession(true);

		// 旧セッションに格納されていたセッションオブジェクトを復元する。
		setSessionObjects(session, sessionObjs);

		return session;
	}


	/**
	 * セッションからセッションオブジェクトを格納した Map オブジェクトを取得する。<br/>
	 * Map の Key がセッションオブジェクトの Key、Value が格納されていたオブジェクトになる。<br/>
	 * <br/>
	 * @param session HTTP セッション
	 * @return セッションオブジェクトを格納した Map オブジェクト
	 */
	protected Map<String, Object> getSessionObjects(HttpSession session) {
		
		Map<String, Object> objs = new HashMap<>();
		
		Enumeration<String> enu = session.getAttributeNames();
		while (enu.hasMoreElements()){
			String key = enu.nextElement();
			objs.put(key, session.getAttribute(key));
		}

		return objs;
		
	}


	/**
	 * Map に格納されたオブジェクトをセッションオブジェクトとしてセッションに格納する。<br/>
	 * Map の Key がセッションオブジェクトの Key として使用される。<br/>
	 * <br/>
	 * @param session HTTP セッションオブジェクト
	 * @param sessionObjs 格納するオブジェクトを格納した Map
	 */
	protected void setSessionObjects(HttpSession session, Map<String, Object> sessionObjs){
		for (Entry<String, Object> e : sessionObjs.entrySet()) {
			session.setAttribute(e.getKey(), e.getValue());
		}
	}
	
}
