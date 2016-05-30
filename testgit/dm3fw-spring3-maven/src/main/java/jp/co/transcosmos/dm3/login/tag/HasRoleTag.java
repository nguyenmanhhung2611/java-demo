/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.login.tag;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import jp.co.transcosmos.dm3.login.UserRoleSet;
import jp.co.transcosmos.dm3.login.v3.Authentication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * JSP tag to check the logged in user's role, so that we can do partial page 
 * display based on permissions. The roleName attribute specifies the role
 * to check for, and the negate attribute defines whether to show or hide the 
 * tag body if the user has the role. 
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: HasRoleTag.java,v 1.2 2007/05/31 09:44:52 rick Exp $
 */
public class HasRoleTag extends BodyTagSupport {
    private static final Log log = LogFactory.getLog(HasRoleTag.class);
    
    private static final long serialVersionUID = 1;

    private String roleName;
    private String rolesetSessionParameter = "loggedInUserRoleset";
    private boolean negate;
// 2013.04.17 H.Mizuno V3 認証対応 Start
    // V3 認証の場合、認証用クラスの bean ID を指定する。
    private String authId = null;
// 2013.04.17 H.Mizuno V3 認証対応 End
    
    public void setRoleName(String pRoleName) {
        this.roleName = pRoleName;
    }

    public void setRolesetSessionParameter(String rolesetSessionParameter) {
        this.rolesetSessionParameter = rolesetSessionParameter;
    }

    public boolean isNegative() {
        return this.negate;
    }

    public void setNegative(boolean pNegative) {
        this.negate = pNegative;
    }

    public boolean isNegate() {
        return this.negate;
    }

    public void setNegate(boolean pNegate) {
        this.negate = pNegate;
    }

// 2013.04.17 H.Mizuno V3 認証対応 Start
	public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}
// 2013.04.17 H.Mizuno V3 認証対応 End

	
	public int doStartTag() throws JspException {
// 2013.04.17 H.Mizuno V3 認証対応 Start
//		UserRoleSet roleset = getRoleSetFromRequest(this.pageContext);
		UserRoleSet roleset;
		if (this.authId != null && this.authId.length() != 0){
			// Ver 3  認証
			roleset = getRoleSetFromAuthenticate(this.pageContext);
		} else {
			// Ver 1、2 認証
			roleset = getRoleSetFromRequest(this.pageContext);
		}
// 2013.04.17 H.Mizuno V3 認証対応 End
		
		
		if (!isNegative() && (roleset != null) && roleset.hasRole(getRoles())) {
            return EVAL_BODY_INCLUDE;
// 2013.04.16 H.Mizuno 複数ロール名を指定して、Negative を使用した時、正常に判定できない問題を修正 start
//        } else if (isNegative() && ((roleset == null) || !roleset.hasRole(this.roleName))) {
          } else if (isNegative() && ((roleset == null) || !roleset.hasRole(getRoles()))) {
// 2013.04.16 H.Mizuno 複数ロール名を指定して、Negative を使用した時、正常に判定できない問題を修正 end
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }
    
	protected Set<String> getRoles() {
        // Tokenize the role names
        StringTokenizer st = new StringTokenizer(this.roleName, ",");
        Set<String> roles = new HashSet<String>();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (token != null) {
                roles.add(token);
            }
        }
        return roles;
    }

    public int doEndTag() throws JspException {
        this.roleName = null;
        this.rolesetSessionParameter = "loggedInUserRoleset";
        this.negate = false;
        return super.doEndTag();
    }

    protected UserRoleSet getRoleSetFromRequest(PageContext pageContext) {
        HttpSession session = pageContext.getSession();
        if (session != null) {
            return (UserRoleSet) session.getAttribute(this.rolesetSessionParameter);
        } else {
            log.warn("WARNING: Roleset not found in session scope - returning NULL");
            return null;
        }
    }
    

// 2013.04.17 H.Mizuno V3 認証対応 Start    
    // V3 認証用ロール名所得処理
    // 認証用オブジェクトを経由して、ログインユーザーのロール情報を取得する。
	protected UserRoleSet getRoleSetFromAuthenticate(PageContext pageContext){

		// Spring の Application Context を取得
		ServletContext ctx = pageContext.getServletContext();
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(ctx);
		
		// Spring コンテキストから 認証クラスを取得
		try {
			Authentication authentication = (Authentication)springContext.getBean(this.authId);
			if (authentication == null) return null;

			return authentication.getLoggedInUserRole(
					(HttpServletRequest)pageContext.getRequest(),
					(HttpServletResponse)pageContext.getResponse());
			
		} catch (NoSuchBeanDefinitionException e){
			log.warn("Authentication bean not found !!!");
		}
		return null;
    }

// 2013.04.17 H.Mizuno V3 認証対応 End    
    
}
