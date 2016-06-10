package jp.co.transcosmos.dm3.login.form.v3;


/**
 * <pre>
 * Ver 3 認証対応用ログインフォームクラス
 * AutoLoginAuth を使用する際に使用するフォームクラス　（自動ログイン対応版）
 *
 * 担当者            修正日               修正内容
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.05  新規作成
 *
 * </pre>
*/
public class AutoLoginForm extends DefaultLoginForm {
	
	// 自動ログインのチェックボックス
    private String autoLogin;


    
    /**
     * 自動ログインのチェック判定<br/>
     * <br/>
     * @return 自動ログインする場合は、true、しない場合は false を復帰
     * @throws Exception
     */
    public boolean isAutoLoginSelected() {
        String autoLogin = getAutoLogin();
        return (autoLogin != null) && autoLogin.equals("1");
    }


    
    // setter、getter
    public String getAutoLogin() {
		return autoLogin;
	}

	public void setAutoLogin(String autoLogin) {
		this.autoLogin = autoLogin;
	}
}
