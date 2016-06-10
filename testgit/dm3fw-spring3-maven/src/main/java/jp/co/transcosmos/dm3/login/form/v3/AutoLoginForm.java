package jp.co.transcosmos.dm3.login.form.v3;


/**
 * <pre>
 * Ver 3 �F�ؑΉ��p���O�C���t�H�[���N���X
 * AutoLoginAuth ���g�p����ۂɎg�p����t�H�[���N���X�@�i�������O�C���Ή��Łj
 *
 * �S����            �C����               �C�����e
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.05  �V�K�쐬
 *
 * </pre>
*/
public class AutoLoginForm extends DefaultLoginForm {
	
	// �������O�C���̃`�F�b�N�{�b�N�X
    private String autoLogin;


    
    /**
     * �������O�C���̃`�F�b�N����<br/>
     * <br/>
     * @return �������O�C������ꍇ�́Atrue�A���Ȃ��ꍇ�� false �𕜋A
     * @throws Exception
     */
    public boolean isAutoLoginSelected() {
        String autoLogin = getAutoLogin();
        return (autoLogin != null) && autoLogin.equals("1");
    }


    
    // setter�Agetter
    public String getAutoLogin() {
		return autoLogin;
	}

	public void setAutoLogin(String autoLogin) {
		this.autoLogin = autoLogin;
	}
}
