package jp.co.transcosmos.dm3.frontCore.auth;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.UserRoleSet;
import jp.co.transcosmos.dm3.login.v3.AutoLoginAuth;



public class MypageAuth extends AutoLoginAuth {

	/** マイページログイン済時のロール名 */
	public static final String MYPAGE_ROLE_NAME = "mypage";



	/**
	 * ログイン情報のセッション格納処理<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param user ログインユーザー情報
	 */
	@Override
	protected void setLoggedInUser(HttpServletRequest request, LoginUser user) {
		// 継承元の処理を利用してセッション情報にログイン情報を書き込む
		super.setLoggedInUser(request, user);
		
		// フレームワークでは、ロール用の DAO が設定されない場合、ROLE 情報をセッションに設定する処理は
		// 行われない。
		// マイページのログイン処理では、強制的にロール情報を設定する。
		HttpSession session = request.getSession();
        Set<String> myRoles = getMyRolenamesFromDB(user.getUserId());
        session.setAttribute(this.rolesetSessionParameter, new UserRoleSet(user.getUserId(), myRoles));
	}



    /**
     * ロール情報を取得<br/>
     * マイページのログイン処理の場合、固定値をロール名として復帰する。<br/>
     * <br/>
     * @param userId キーとなるユーザーID
     * @return 取得したロール名の Set オブジェクト
     */
	@Override
    protected Set<String> getMyRolenamesFromDB(Object userId) {

        Set<String> myRoles = new HashSet<String>();
        myRoles.add(MYPAGE_ROLE_NAME);
        return myRoles; 
    }

}
