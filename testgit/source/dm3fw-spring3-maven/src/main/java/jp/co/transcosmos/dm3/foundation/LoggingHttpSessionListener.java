/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.foundation;

import java.util.Enumeration;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * セッションの開始、終了をロギングするリスナ。デバッグ用。
 * web.xmlに設定を記述して使用する。
 * 実際の運用時には使用しないこと。
 */
public class LoggingHttpSessionListener implements HttpSessionListener, 
        HttpSessionAttributeListener, ServletContextAttributeListener, 
        ServletRequestAttributeListener {

	/** ログ出力 */
	private static Log log = LogFactory.getLog(LoggingHttpSessionListener.class);
	/** カウント数 */
	private static volatile int mCount = 0;
		
	/**
	 * Sessionの属性を文字列にする。
	 * @param session セッション
	 * @return 属性のデバッグ出力
	 */
	protected String getAttributes(HttpSession session){
		StringBuilder buf = new StringBuilder();
		
		Enumeration<String> enume = session.getAttributeNames();
		
		while( enume.hasMoreElements() ){
			String name = (String) enume.nextElement();
			Object value = session.getAttribute(name);
			buf.append(name).append("=");
			buf.append(value).append(";");
		}
		
		return buf.toString();
	}
    
    /**
     * セッションの属性が追加された時に呼び出される。
     * @param pHttpSessionBindingEvent
     * @see javax.servlet.http.HttpSessionAttributeListener#attributeAdded(javax.servlet.http.HttpSessionBindingEvent)
     */
    public void attributeAdded(HttpSessionBindingEvent pHttpSessionBindingEvent) {
        HttpSession session = pHttpSessionBindingEvent.getSession();
        String name = pHttpSessionBindingEvent.getName();
        Object value = pHttpSessionBindingEvent.getValue();
        log.debug("sessionAttributeAdded::" + session.getId() + " [" + name + "=" + value + "]");
    }

    /**
     * セッションの属性が削除された時に呼び出される。
     * @param pHttpSessionBindingEvent
     * @see javax.servlet.http.HttpSessionAttributeListener#attributeRemoved(javax.servlet.http.HttpSessionBindingEvent)
     */
    public void attributeRemoved(HttpSessionBindingEvent pHttpSessionBindingEvent) {
        HttpSession session = pHttpSessionBindingEvent.getSession();
        String name = pHttpSessionBindingEvent.getName();
        Object value = pHttpSessionBindingEvent.getValue();
        log.debug("sessionAttributeRemoved::" + session.getId() + " [" + name + "=" + value + "]");
    }

    /**
     * セッションの属性が変更された時に呼び出される。
     * @param pHttpSessionBindingEvent
     * @see javax.servlet.http.HttpSessionAttributeListener#attributeReplaced(javax.servlet.http.HttpSessionBindingEvent)
     */
    public void attributeReplaced(HttpSessionBindingEvent pHttpSessionBindingEvent) {
        HttpSession session = pHttpSessionBindingEvent.getSession();
        String name = pHttpSessionBindingEvent.getName();
        Object value = pHttpSessionBindingEvent.getValue();
        log.debug("sessionAttributeReplaced::" + session.getId() + " [" + name + "=" + value + "]");
    }

    public void sessionCreated(HttpSessionEvent pHttpSessionEvent) {
        mCount++;
        HttpSession session = pHttpSessionEvent.getSession();
        log.debug("SessionCreated::" + session.getId() + "[" + getAttributes(session) + "]");
        log.debug("Session count=" + mCount );
    }

    public void sessionDestroyed(HttpSessionEvent pHttpSessionEvent) {
        if( mCount > 0 ){
            mCount--;
        }
        
        HttpSession session = pHttpSessionEvent.getSession();
        log.debug("SessionDestroyed::" + session.getId() + "[" + getAttributes(session) + "]");
        log.debug("Session count=" + mCount );        
    }

    public void attributeAdded(ServletContextAttributeEvent event) {
        String name = event.getName();
        Object value = event.getValue();
        log.debug("contextAttributeAdded [" + name + "=" + value + "]");
    }

    public void attributeRemoved(ServletContextAttributeEvent event) {
        String name = event.getName();
        Object value = event.getValue();
        log.debug("contextAttributeRemoved [" + name + "=" + value + "]");
        
    }

    public void attributeReplaced(ServletContextAttributeEvent event) {
        String name = event.getName();
        Object value = event.getValue();
        log.debug("contextAttributeReplaced [" + name + "=" + value + "]");
    }

    public void attributeAdded(ServletRequestAttributeEvent event) {
        String name = event.getName();
        Object value = event.getValue();
        log.debug("requestAttributeAdded [" + name + "=" + value + "]");
    }

    public void attributeRemoved(ServletRequestAttributeEvent event) {
        String name = event.getName();
        Object value = event.getValue();
        log.debug("requestAttributeAdded [" + name + "=" + value + "]");
    }

    public void attributeReplaced(ServletRequestAttributeEvent event) {
        String name = event.getName();
        Object value = event.getValue();
        log.debug("requestAttributeReplaced [" + name + "=" + value + "]");
    }
    
    
}