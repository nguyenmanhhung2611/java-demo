package jp.co.transcosmos.dm3.login.v3;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.dao.ReadOnlyDAO;
import jp.co.transcosmos.dm3.login.LastLoginTimestamped;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.UserRoleSet;
import jp.co.transcosmos.dm3.login.form.v3.LoginForm;
import jp.co.transcosmos.dm3.utils.ReflectionUtils;


/**
 * <pre>
 * V3 認証対応
 * 標準のDB認証処理クラス
 * もっともシンプルなDBによる認証処理を実装している。
 *   ・ログインが成功すると、セッションに、ユーザー情報、ユーザーID（主キー）、ロール名を設定する。
 *   ・ログアウト処理を行うと、上記情報をセッションから削除する。
 *   ・getLoggedInUser() を使用して認証チェックを行う。
 *
 * 担当者            修正日               修正内容
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.04  新規作成
 * H.Mizuno  2013.04.11  認証チェックと、ユーザー情報取得を分離
 *
 * </pre>
*/
public class DefaultAuth implements Authentication {
	private static final Log log = LogFactory.getLog(DefaultAuth.class);
	
	// 認証用 DAO
	protected DAO<LoginUser> userDAO;

	// 最終更新日更新 DAO
	// 認証用と、最終更新日を管理するフィールドが別の場合、このプロパティに値を設定する。
	// 未設定の場合、userDAO を更新用としてそのまま使用する。
	protected DAO<LoginUser> writeableUserDAO;

	// ロール情報用 DAO
	protected ReadOnlyDAO<?> userRoleDAO;

	// 認証で使用するユーザー情報のユーザーID（主キー） が格納されているDBの列名
	protected String userDAOUserIdField = "userId";    

	// 認証で使用するユーザー情報のログインID が格納されているDBの列名
	protected String userDAOLoginIdField = "loginId";    

    // 認証で使用するロール名を取得する際、ユーザーID が格納されているDBの列名
	protected String userRoleDAOUserIdField = "userId";

    // 認証で使用するロール名が格納されているDBの列名
	protected String userRoleDAORolenameField = "rolename";

    // 認証で使用するロール名を取得する際、ユーザーID が格納されているテーブルの別名
    // 通常は設定しないが、ロールの取得で JoinDAO を使用する場合に設定する。
	protected String userRoleDAOUserIdAlias = "";

    // 認証で使用するロール名を取得する際、ロール名が格納されているテーブルの別名
    // 通常は設定しないが、ロールの取得で JoinDAO を使用する場合に設定する。
	protected String userRoleDAORolenameAlias = "";
    
    // ログインユーザー情報をセッションに書き込む時のキー名
	protected String loginUserSessionParameter = "loggedInUser";

    // ユーザーID(主キー値)をセッションに書き込む時のキー名
	protected String loginUserIdSessionParameter = "loggedInUserId";

    // ロール情報をセッションに書き込む時のキー名
	protected String rolesetSessionParameter = "loggedInUserRoleset";



    /**
     * ログイン処理（手動ログイン処理）<br/>
     * 正常終了すれば、ログイン情報をセッションに格納し、ログインユーザー情報を復帰する。<br/>
     * ログインに失敗した場合、null を復帰する。<br/>
     * <br/>
     * @param request HTTPリクエスト
     * @param　response HTTPレスポンス
     * @param　form ログイン画面のフォーム
     * @return ログインユーザーのオブジェクト
     */
	@Override
	public LoginUser login(HttpServletRequest request, HttpServletResponse response,
			LoginForm form) {

        DAOCriteria criteria = new DAOCriteria();
        criteria.addWhereClause(this.userDAOLoginIdField, form.getLoginID());

        List<LoginUser> matchingUsers = this.userDAO.selectByFilter(criteria);

        // 取得したデータから、パスワードが一致するレコードが見付からない場合、 null を復帰する。
        for (Iterator<LoginUser> i = matchingUsers.iterator(); i.hasNext(); ) {
            LoginUser user = i.next();
            if (user.matchPassword(form.getPassword())) {

            	// 最終ログイン日の更新処理
            	updateLastLoginTimestamp(user);

                // セッションにユーザー情報を書き込む
                setLoggedInUser(request, user);
                return user;
            }
        }
        return null;
	}

	
	
    /**
     * ログアウト処理<br/>
     * セッションからログイン情報を削除する。<br/>
     * <br/>
     * @param request HTTPリクエスト
     * @param　response HTTPレスポンス
     */
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		
		// ユーザー情報をセッションから削除する
		session.removeAttribute(this.loginUserSessionParameter);
        log.info("removed user from session");

        // ユーザーIDをセッションから削除する
        session.removeAttribute(this.loginUserIdSessionParameter);
        log.info("removed userid from session");

		// ロール情報をセッションから削除する
        session.removeAttribute(rolesetSessionParameter);

	}

	
	
    /**
     * ログイン状態の判定処理<br/>
     * セッションからログイン情報が取得可能な場合、ログインユーザーの情報を復帰する。<br/>
     * 取得できない場合、null を復帰する。<br/>
     * <br/>
     * @param request HTTPリクエスト
     * @param　response HTTPレスポンス
     * @return ログインユーザーのオブジェクト。　ログイン済でない場合、null を復帰する。
     */
	@Override
	public LoginUser checkLoggedIn(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		
        LoginUser user = null;

    	log.info("User has a session: id=" + session.getId());
        user = (LoginUser) session.getAttribute(this.loginUserSessionParameter);

        log.info("Checked session variable: " + this.loginUserSessionParameter + 
                " for logged in user: found " + user);
        return user;
	}


    /**
     * ログインユーザーの情報取得<br/>
     * この認証処理では、checkLoggedIn と同じ処理。<br/>
     * <br/>
     * @param request HTTPリクエスト
     * @param　response HTTPレスポンス
     * @return ログインユーザーのオブジェクト。　ログイン済でない場合、null を復帰する。
     */
	public LoginUser getLoggedInUser(HttpServletRequest request, HttpServletResponse response){
		return checkLoggedIn(request, response);
	}

	
    /**
     * ログインユーザーのロール情報を取得<br/>
     * ログイン済の場合、セッションにロール情報が格納されているので、その値を復帰する。<br/>
     * ロール情報が取得できない場合は null を復帰する。<br/>
     * <br/>
     * @param request HTTPリクエスト
     * @param　response HTTPレスポンス
     * @return ロール名のリストを復帰する。
     */
	@Override
	public UserRoleSet getLoggedInUserRole(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();

		UserRoleSet roleset = null;

		roleset = (UserRoleSet) session.getAttribute(this.rolesetSessionParameter);
        if (roleset == null) {
        	log.warn("WARNING: Roleset not found in session scope - returning NULL");
        }
        return roleset;
	}
	
	
	
    /**
     * 最終ログイン日の更新処理<br/>
     * 認証用DAO の VO が、LastLoginTimestamped を実装している場合、最終ログイン日を<br/>
     * 更新する。　インターフェースが実装されていない場合、なにもしない。<br/>
     * <br/>
     * @param user ログインユーザーのオブジェクト
     */
    protected void updateLastLoginTimestamp(LoginUser user) {
    	if (user instanceof LastLoginTimestamped) {
            ((LastLoginTimestamped) user).copyThisLoginTimestampToLastLoginTimestamp();
            ((LastLoginTimestamped) user).setThisLoginTimestamp(new Date());

            if (writeableUserDAO != null) {
            	this.writeableUserDAO.update(new LoginUser[] {user});
            } else {
            	this.userDAO.update(new LoginUser[] {user});
            }
        }        
    }


    
    /**
     * ログイン情報のセッション格納処理<br/>
     * ログインが成功した時に実行され、ユーザー情報、ユーザーID、ロール情報をセッションに書き込む。<br/>
     * <br/>
     * @param request HTTPリクエストオブジェクト
     * @param user ログインユーザーのオブジェクト
     */
    protected void setLoggedInUser(HttpServletRequest request, LoginUser user) {

		HttpSession session = request.getSession();

    	// ユーザー情報を、セッションに格納する。
    	session.setAttribute(this.loginUserSessionParameter, user);
        log.info("add user to session; user=" + user);

        // ユーザーIDを、セッション情報に格納する。
        session.setAttribute(this.loginUserIdSessionParameter, user.getUserId());
        log.info("add userid to session; userid=" + user.getUserId());            

    	// ロール情報用の DAO が設定されている場合、ユーザーのロール情報をセッションに格納する。
        UserRoleSet roleset = null;
        if (this.userRoleDAO != null) {
            Set<String> myRoles = getMyRolenamesFromDB(user.getUserId());
            log.info("Found roles: " + myRoles + " for userId=" + user.getUserId());
            roleset = new UserRoleSet(user.getUserId(), myRoles);
        }
        
        if (roleset != null) {
        	session.setAttribute(this.rolesetSessionParameter, roleset);
        } else {
            session.removeAttribute(this.rolesetSessionParameter);
        }

    	// セッションに、ログイン時間（最終アクセス時間）を設定する。
        session.setAttribute("lastRefreshedTimestamp", new Long(System.currentTimeMillis()));
    }
    
	

    /**
     * ロール情報を取得<br/>
     * データベースからログイン情報を取得し、ロール名の情報を復帰する。<br/>
     * <br/>
     * @param userId キーとなるユーザーID
     * @return 取得したロール名の Set オブジェクト
     */
    protected Set<String> getMyRolenamesFromDB(Object userId) {

    	DAOCriteria criteria;
    	// ユーザーID　の別名が設定されている場合（JoinDAO を使用している場合）、別名を使用して検索条件を作成する
    	if (this.userRoleDAOUserIdAlias != null && this.userRoleDAOUserIdAlias.length() > 0)
    		criteria = new DAOCriteria(this.userRoleDAOUserIdAlias, this.userRoleDAOUserIdField, userId, DAOCriteria.EQUALS, false);
    	else
        	criteria = new DAOCriteria(this.userRoleDAOUserIdField, userId);
        

    	// ロール情報をDBから取得する
    	List<?> results = this.userRoleDAO.selectByFilter(criteria);
        Set<String> myRoles = new HashSet<String>();

        for (Iterator<?> i = results.iterator(); i.hasNext(); ) {
            Object role = i.next();
            if (role == null) {
                continue;
            } else if (role instanceof String) {
                myRoles.add((String) role);
            } else try {

            	// JoinDAO の場合、結果はネストしている為、更に結果セットからロール情報を取得する。
            	if (role instanceof JoinResult) {
            		if (this.userRoleDAORolenameAlias == null || this.userRoleDAORolenameAlias.length() == 0)
            			throw new RuntimeException("Error : userRoleDAORolenameAlias is null"); 
            		role = ((JoinResult)role).getItems().get(this.userRoleDAORolenameAlias);
            	}

            	myRoles.add("" + ReflectionUtils.getFieldValueByGetter(role, 
                       this.userRoleDAORolenameField));
            } catch (Throwable err) {
                throw new RuntimeException("Error trying to get the field " + 
                        this.userRoleDAORolenameField + " from the DAO result " + role, err);
            }
        }
        return myRoles; 
    }


    
    // setter、getter
    public void setUserDAO(DAO<LoginUser> userDAO) {
        this.userDAO = userDAO;
    }
    
    public void setWriteableUserDAO(DAO<LoginUser> writeableUserDAO) {
		this.writeableUserDAO = writeableUserDAO;
	}

	public void setUserRoleDAO(ReadOnlyDAO<?> userRoleDAO) {
		this.userRoleDAO = userRoleDAO;
	}

	public void setUserDAOUserIdField(String userDAOUserIdField) {
		this.userDAOUserIdField = userDAOUserIdField;
	}

	public String getUserDAOUserIdField() {
		return this.userDAOUserIdField;
	}
	
	public void setUserDAOLoginIdField(String userDAOLoginIdField) {
		this.userDAOLoginIdField = userDAOLoginIdField;
	}

	public String getUserDAOLoginIdField() {
		return this.userDAOLoginIdField;
	}
	
	public void setUserRoleDAOUserIdField(String userRoleDAOUserIdField) {
		this.userRoleDAOUserIdField = userRoleDAOUserIdField;
	}

	public void setUserRoleDAORolenameField(String userRoleDAORolenameField) {
		this.userRoleDAORolenameField = userRoleDAORolenameField;
	}

	public void setUserRoleDAOUserIdAlias(String userRoleDAOUserIdAlias) {
		this.userRoleDAOUserIdAlias = userRoleDAOUserIdAlias;
	}

	public void setUserRoleDAORolenameAlias(String userRoleDAORolenameAlias) {
		this.userRoleDAORolenameAlias = userRoleDAORolenameAlias;
	}

	public void setLoginUserSessionParameter(String loginUserSessionParameter) {
		this.loginUserSessionParameter = loginUserSessionParameter;
	}

	public void setLoginUserIdSessionParameter(String loginUserIdSessionParameter) {
		this.loginUserIdSessionParameter = loginUserIdSessionParameter;
	}

	public void setRolesetSessionParameter(String rolesetSessionParameter) {
		this.rolesetSessionParameter = rolesetSessionParameter;
	}
}
