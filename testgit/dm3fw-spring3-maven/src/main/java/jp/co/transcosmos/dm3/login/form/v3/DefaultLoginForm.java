package jp.co.transcosmos.dm3.login.form.v3;

import java.util.List;

import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;


/**
 * <pre>
 * Ver 3 �F�ؑΉ��p���O�C���t�H�[���N���X
 * DefaultAuth ���g�p����ۂɎg�p����t�H�[���N���X
 *
 * �S����            �C����               �C�����e
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.05  �V�K�쐬
 *
 * </pre>
*/
public class DefaultLoginForm implements LoginForm {

	// ���O�C�����[�U�[ID
	private String loginID;
	
	// ���O�C���p�X���[�h
	private String password;


	
    /**
     * �o���f�[�V����<br/>
     * <br/>
     * @param errors �G���[�I�u�W�F�N�g
     * @return �G���[�����������ꍇ true�A�G���[���L�����ꍇ false �𕜋A
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

	
	
	// setter�Agetter
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
