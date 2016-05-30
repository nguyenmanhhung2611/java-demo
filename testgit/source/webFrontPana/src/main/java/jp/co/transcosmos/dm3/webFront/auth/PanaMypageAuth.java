package jp.co.transcosmos.dm3.webFront.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.form.v3.LoginForm;
import jp.co.transcosmos.dm3.login.v3.LockSupportAuth;
import jp.co.transcosmos.dm3.utils.ServletUtils;


/**
 * �}�C�y�[�W�F�؃N���X.
 * <p>
 * �t���[�����[�N���񋟂���@�\���g�p���ă��O�C���������s�����A�ȉ��̏�����ǉ����Ă���B<br/>
 * ���̃N���X�̓p�X���[�h���̓~�X���̃��b�N�@�\�ɑΉ������F�؃N���X���g�����Ă���B<br/>
 * <ul>
 * <li>�}�C�y�[�W�Ƀ��O�C�������������ꍇ�AGA �v���p�� Cookie ���o�͂���B</li>
 * </ul>
 * <br/>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.05	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class PanaMypageAuth extends LockSupportAuth {

	/**
	 * Google Analytics �̌v���p Cookie �� Key ��<br/>
	 * Mypage �Ƀ��O�C���ς̏ꍇ�A���� Key ���� Cookie �ɏ������ށB�@�i���̍ۂ̒l�� 1 �Œ�B�j<br/>
	 * Mypage ���烍�O�A�E�g�����ꍇ�A���� Key ���� Cookie ����폜����B<br/>
	 */
	public static final String GA_MYPAGE_CHK_COOKIE_KEY = "my_coogle_analytics";

	/** ���ʃp�����[�^�I�u�W�F�N�g */
	private CommonParameters commonParameters;



	/**
	 * ���ʃp�����[�^�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param commonParameters
	 */
	public void setCommonParameters(CommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}



	/**
	 * �}�C�y�[�W���O�C������<br/>
	 * �Ϗ���N���X�ɂă��O�C���F�؂�����ɁAGA �v���p Cookie ���o�͂���B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	 * @param form ���̓t�H�[��
	 * 
	 */
	@Override
	public LoginUser login(HttpServletRequest request, HttpServletResponse response, LoginForm form) {

		// ���O�C������
		LoginUser loginUser = super.login(request, response, form);
		
		// GA �p�� Cookie ��ǉ��@�i�������͍폜�j
		writeGACookie(request, response, loginUser);

		// �ŋߌ��������A���C�ɓ���ۑ��̌����ȂǁAMyPage �֘A Cookie �̃��Z�b�g
		resetMypageCookie(request, response, loginUser);
		
		return loginUser;
	}


	
    /**
     * ���O�A�E�g����<br/>
	 * �Ϗ���N���X�ɂă��O�A�E�g����������ɁAGA �v���p Cookie ���폜����B<br/>
     * <br/>
     * @param request HTTP���N�G�X�g
     * @param�@response HTTP���X�|���X
     */
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		// ���O�A�E�g����
		super.logout(request, response);

		// GA �p�� Key �� Cookie ����폜����
		writeGACookie(request, response, null);
	}

	
	
	/**
	 * �F�؃`�F�b�N����<br/>
	 * �F�؃`�F�b�N���ɁA�F�؂��Ă��Ȃ��Ɣ��f���ꂽ�ꍇ�ACookie ���폜����B<br/>
	 * �iJava �̃Z�b�V�����^�C���A�E�g�΍�j<br/>
	 * <br/>
     * @param request HTTP���N�G�X�g
     * @param�@response HTTP���X�|���X
	 * 
	 * @return ���O�C�����[�U�[���
	 */
	@Override
	public LoginUser checkLoggedIn(HttpServletRequest request, HttpServletResponse response){

		// �F�؃`�F�b�N����
		// Cookie �ɂ�鎩�����O�C���́A���̃^�C�~���O�Ŏ��s�����B
		LoginUser loginUser = super.checkLoggedIn(request, response);

		// GA �p�� Cookie ��ǉ��@�i�������͍폜�j
		writeGACookie(request, response, loginUser);

		return loginUser;
	}
	

	
	/**
	 * GA �v���p�� Cookie �������ށ@�i�������́A��������B�j<br/>
	 * �����̃��[�U�[��� null �ȊO�̏ꍇ�AGA �v���p�� Cookie ���o�͂���B<br/>
	 * null �̏ꍇ�́AGA �v���p�� Cookie ���폜����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param response�@HTTP ���X�|���X
	 * @param loginUser �F�؏��
	 */
	private void writeGACookie(HttpServletRequest request, HttpServletResponse response, LoginUser loginUser){
		
		if (loginUser != null) {
			// ���O�C�������������ꍇ�AGA �p�� Key �� Cookie �ɏ������� �i�l�͉��ł��ǂ��̂Łu�P�v��ݒ�j
			// Cookie �́A�u���E�U�[��������_�ō폜�����l�ɐݒ肷��B
	    	ServletUtils.addCookie(response, request.getContextPath(), GA_MYPAGE_CHK_COOKIE_KEY, 
	    			"1", this.commonParameters.getCookieDomain());

		} else {
			// ���O�C�������s�����ꍇ�AGA �p�� Key �� Cookie ����폜����
	    	ServletUtils.delCookie(response, request.getContextPath(), GA_MYPAGE_CHK_COOKIE_KEY, 
	    			this.commonParameters.getCookieDomain());

		}
	}
	


	/**
	 * ���C�ɓ��茏���A�ŋߌ��������Ȃǂ́A Mypage �֘A Cookie �̍폜<br/>
	 * ���폜����̂́A���O�C�������������ꍇ�̂݁B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param response�@HTTP ���X�|���X
	 * @param loginUser �F�؏��
	 */
	private void resetMypageCookie(HttpServletRequest request, HttpServletResponse response, LoginUser loginUser){

		if (loginUser != null) {
	    	ServletUtils.delCookie(response, request.getContextPath(), "favoriteCount", 
	    			this.commonParameters.getCookieDomain());
	    	ServletUtils.delCookie(response, request.getContextPath(), "historyCount", 
	    			this.commonParameters.getCookieDomain());

		}
	}
	
}
