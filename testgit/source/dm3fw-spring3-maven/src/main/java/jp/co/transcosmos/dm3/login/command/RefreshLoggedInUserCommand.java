/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.login.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.dao.ReadOnlyDAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.support.LoginModificationSupport;
import jp.co.transcosmos.dm3.utils.ServletUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

public class RefreshLoggedInUserCommand extends LoginModificationSupport implements Command {
    private static final Log log = LogFactory.getLog(RefreshLoggedInUserCommand.class);

    private ReadOnlyDAO<LoginUser> userDAO;
    private String userIdParameter = "userId";
    private String logoutURI = "/login/logout.do";
    private long refreshIntervalSeconds = 0;
    
    public void setUserDAO(ReadOnlyDAO<LoginUser> pUserDAO) {
        this.userDAO = pUserDAO;
    }

    public void setRefreshIntervalSeconds(long pPeriod) {
        this.refreshIntervalSeconds = pPeriod;
    }

    public void setUserIdParameter(String pUserIdParameter) {
        this.userIdParameter = pUserIdParameter;
    }

    public void setLogoutURI(String logoutURI) {
        this.logoutURI = logoutURI;
    }

    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        LoginUser user = getLoggedInUser(request);
        if ((user != null) && (session != null)) {
            // Check refresh interval
            long now = System.currentTimeMillis();
            Long lastRefreshedTimestamp = (Long) session.getAttribute("lastRefreshedTimestamp");
            if ((lastRefreshedTimestamp == null) ||
                ((now - lastRefreshedTimestamp.longValue()) > 
                    this.refreshIntervalSeconds * 1000L)) {
                
                // Refresh the logged in user by user id                
                DAOCriteria criteria = new DAOCriteria();
                criteria.addWhereClause(this.userIdParameter, user.getUserId());
                List<LoginUser> users = this.userDAO.selectByFilter(criteria);
                if (users.isEmpty()) {
                    log.warn("Logging out session user not found in the DB: " + user.getUserId());
                    logoutCurrentUser(request.getSession(true));
                    response.sendRedirect(ServletUtils.fixPartialURL(request.getContextPath() + 
                            this.logoutURI, request));
                } else {
                    log.info("Refreshing session user " + user.getUserId() + " from the DB");
                    user = users.iterator().next();
                    setLoggedInUser(request.getSession(true), user);
                    addRolesetToSession(request.getSession(true), user); // trigger reload of the roleset
                }
            }
            session.setAttribute("lastRefreshedTimestamp", new Long(now));
        }
		return null;
	}
}
