/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.login;

import java.util.Date;

/**
 * Implement this interface to timestamp the user object after they successfully 
 * login.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: LastLoginTimestamped.java,v 1.2 2007/05/31 09:44:51 rick Exp $
 */
public interface LastLoginTimestamped {
    public void setThisLoginTimestamp(Date lLastLoginTimestamp);
    public void copyThisLoginTimestampToLastLoginTimestamp();
}
