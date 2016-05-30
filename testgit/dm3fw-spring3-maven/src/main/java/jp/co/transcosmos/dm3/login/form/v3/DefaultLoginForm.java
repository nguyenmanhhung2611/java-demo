package jp.co.transcosmos.dm3.login.form.v3;

import java.util.List;

import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;


/**
 * <pre>
 * Ver 3 認証対応用ログインフォームクラス
 * DefaultAuth を使用する際に使用するフォームクラス
 *
 * 担当者            修正日               修正内容
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.05  新規作成
 *
 * </pre>
*/
public class DefaultLoginForm implements LoginForm {

	// ログインユーザーID
	private String loginID;
	
	// ログインパスワード
	private String password;


	
    /**
     * バリデーション<br/>
     * <br/>
     * @param errors エラーオブジェクト
     * @return エラーが無かった場合 true、エラーが有った場合 false を復帰
     */
	@Override
	public boolean validate(List<ValidationFailure> errors) {
        int startSize = errors.size();

        ValidationChain valLoginId = new ValidationChain("loginID", this.loginID);
        valLoginId.addValidation(new NullOrEmptyCheckValidation());
        valLoginId.validate(errors);

        ValidationChain valPassword = new ValidationChain("password", this.password);
        valPassword.addValidation(new NullOrEmptyCheckValidation());
        valPassword.validate(errors);
        
        return (startSize == errors.size());
	}

	
	
	// setter、getter
	@Override
	public String getLoginID() {
		return loginID;
	}

	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
