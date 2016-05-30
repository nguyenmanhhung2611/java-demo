package jp.co.transcosmos.dm3.frontCore.auth;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.UserRoleSet;
import jp.co.transcosmos.dm3.login.form.v3.DefaultLoginForm;
import jp.co.transcosmos.dm3.login.form.v3.LoginForm;
import jp.co.transcosmos.dm3.login.v3.Authentication;
import jp.co.transcosmos.dm3.login.v3.DefaultAuth;
import jp.co.transcosmos.dm3.utils.CipherUtil;
import jp.co.transcosmos.dm3.utils.ServletUtils;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;


/**
 * �t�����g�T�C�g�̓������O�C������.
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.04.17	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class AnonymousAuth extends DefaultAuth {

	private static final Log log = LogFactory.getLog(AnonymousAuth.class);
	
	/** �}�C�y�[�W�p�F�؃N���X */
	protected Authentication mypageAuth;
	/** ���ʃp�����[�^�I�u�W�F�N�g */
	protected CommonParameters commonParameters;
	/** �}�C�y�[�W��� Model */
	protected MypageUserManage mypageUserManage;
	/** �������O�C���̃��[�U�[ID���i�[�����Ă��� Cookie �� */
	protected String anonymousUidCookieName = "anonUserId";
	/** Cookie �L������ �i�f�t�H���g�P�O�N�j*/
	protected Integer cookieExpires = 60 * 60 * 24 * 365 * 10 ;
	/** Cookie �Z�L���A���� �i�f�t�H���g false�j */
	protected boolean cookieSequre = false;

	/** �Í����N���X */
	private static final CipherUtil userIdCookieCipher = new CipherUtil(";tj1t89483jhzffH"); 



	/**
	 * �}�C�y�[�W�p�F�؃N���X��ݒ肷��B<br/>
	 * <br/>
	 * @param mypageAuth �}�C�y�[�W�p�F�؃N���X
	 */
	public void setMypageAuth(Authentication mypageAuth) {
		this.mypageAuth = mypageAuth;
	}

	/**
	 * ���ʃp�����[�^�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param commonParameters
	 */
	public void setCommonParameters(CommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * �}�C�y�[�W����p Model�@��ݒ肷��B<br/>
	 * <br/>
	 * @param mypageUserManage �}�C�y�[�W����p Model
	 */
	public void setMypageUserManage(MypageUserManage mypageUserManage) {
		this.mypageUserManage = mypageUserManage;
	}

	/**
	 * �������O�C���̃��[�U�[ID���i�[�����Ă��� Cookie ��<br/>
	 * <br/>
	 * @param anonymousUidCookieName �������O�C���̃��[�U�[ID���i�[�����Ă��� Cookie ��
	 */
	public void setAnonymousUidCookieName(String anonymousUidCookieName) {
		this.anonymousUidCookieName = anonymousUidCookieName;
	}

	/**
	 * Cookie �L��������ݒ肷��B<br/>
	 * <br/>
	 * @param cookieExpires Cookie �L������
	 */
	public void setCookieExpires(Integer cookieExpires) {
		this.cookieExpires = cookieExpires;
	}

	/**
	 * Cookie �̃Z�L���A������ݒ肷��B�i�f�t�H���g false�j<br/>
	 * <br/>
	 * @param cookieSequre Cookie �̃Z�L���A����
	 */
	public void setCookieSequre(boolean cookieSequre) {
		this.cookieSequre = cookieSequre;
	}



	/**
	 * �������O�C������<br/>
	 * Cookie ���瓽�����O�C���p�� ID ���擾���A�擾�ł����ꍇ�͂��� ID �œ����F�؂����݂�B<br/>
	 * Cookie �������ꍇ��A�F�؂Ɏ��s�����ꍇ�͐V���Ƀ��[�U�[ID ��o�^���Ă��瓽���F�؂��s���B<br/>
	 * ���O�C�������������ꍇ�͓������O�C���p�� ID �� Cookie �֏������ށB<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	 * 
	 * @return �F�؏��B�@�F�؍ςłȂ��ꍇ�� null
	 */
	@Override
	public LoginUser login(HttpServletRequest request, HttpServletResponse response, LoginForm form) {

		// �������O�C���̏ꍇ�ALoginForm ���n����鎖�͂Ȃ��B
		// Cookie ���烆�[�U�[ID ���擾���ē����F�؂��s���B
		String userId = readUserIdFromCookie(request, response);

		if (userId != null){
			DefaultLoginForm inputForm = new DefaultLoginForm();
			inputForm.setLoginID(userId);

			// �t���[�����[�N�̋@�\���g�p���ă��O�C������
			LoginUser loginUser = super.login(request, response, inputForm);

			// Cookie ����擾�������[�U�[ID �ŔF�؂����������ꍇ�A���[�U�[���𕜋A����B
			// �܂��ACookie �̗L����������������ׁA�������O�C������ Cookie �ɒl����������
			if (loginUser != null) {
				writeUserIdToCookie(request, response, loginUser);
				return loginUser;
			}
		}


		// Cookie ����l���擾�o���Ȃ��ꍇ�A�������́ACookie ����擾�����l�ŔF�؂Ɏ��s�����ꍇ�́A
		// �V���� UserId ���̔Ԃ��� DB �ɓo�^����B�@���̌�A���O�C�����������s���ăZ�b�V���������쐬����B
		try {
			LoginUser loginUser = this.mypageUserManage.addLoginID(null);
			DefaultLoginForm inputForm = new DefaultLoginForm();
			inputForm.setLoginID(loginUser.getLoginId());

			// �t���[�����[�N�̋@�\���g�p���ă��O�C������
			loginUser = super.login(request, response, inputForm);

			// Cookie �� UID ��ݒ肷��B
			writeUserIdToCookie(request, response, loginUser);

			return loginUser;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		}

	}


	
	/**
	 * �������O�C���̔F�؃`�F�b�N����<br/>
	 * �}�C�y�[�W�Ƀ��O�C���ς̏ꍇ�̓}�C�y�[�W�̔F�؏������O�C�����Ƃ��ĕ��A����B<br/>
	 * ���̏ꍇ�A�߂�l�� MypageUserInterface �̎����N���X�i�ʏ�́AMemberInfo�j�ɂȂ�B<br/>
	 * <br/>
	 * �}�C�y�[�W�Ƀ��O�C�����Ă��Ȃ��ꍇ�A�������O�C���̔F�؃`�F�b�N���s���B �����A�������O�C���ςłȂ��ꍇ�A
	 * �������O�C�����s���Ă���F�؏��𕜋A����B�@���̏ꍇ�A�߂�l�� LoginUser �̎����N���X
	 * �i�ʏ�́AUserInfo�j�ɂȂ�B<br/>
	 * <br/>
	 * ���̃��\�b�h�̏ꍇ�A�}�C�y�[�W�̎������O�C�����ݒ肳��Ă�����ꍇ�A�������O�C������������B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	 * 
	 * @return �F�؏��B�@�F�؍ςłȂ��ꍇ�� null
	 */
	@Override
	public LoginUser checkLoggedIn(HttpServletRequest request, HttpServletResponse response) {

		// �}�C�y�[�W�̔F�؃`�F�b�N�����s���A���O�C���ς����`�F�b�N����B
		// �F�؏�񂪎擾�ł����ꍇ�͂��̒l�𕜋A����B
		LoginUser loginUser = this.mypageAuth.checkLoggedIn(request, response);
		if (loginUser != null){
			// Cookie �̓������O�C���̒l���擾����B
			// �擾�o���Ȃ��ꍇ��AID ���قȂ�ꍇ�� Cookie �̒l��ύX����B
			// �i���񗈖K���ɁA�}�C�y�[�W�Ƃ͕ʂ� ID �𓽖����O�C���� ID �Ƃ��Ă��܂��ׁB�j
			String userId = readUserIdFromCookie(request, response);
			if (userId == null || !userId.equals(loginUser.getUserId())){
				writeUserIdToCookie(request, response, loginUser);
			}
			return loginUser;
		}


		// �����F�؂��������Ă��邩���`�F�b�N����B
		loginUser = super.checkLoggedIn(request, response);
		if (loginUser != null) return loginUser;


		// �������O�C�������s����B
		return login(request, response, null);
	}



	/**
	 * ���O�C�����̃��[�U�[���𕜋A����B<br/>
	 * �}�C�y�[�W�̃��O�C�������擾���ĕ��A����B<br/>
	 * �}�C�y�[�W�̃��O�C����񂪎擾�o���Ȃ��ꍇ�A�������O�C���̃��O�C�������擾����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	 * 
	 * @return �F�؏��B�@�F�؍ςłȂ��ꍇ�� null
	 */
	@Override
	public LoginUser getLoggedInUser(HttpServletRequest request, HttpServletResponse response) {

		// �}�C�y�[�W�̔F�؏����擾����B
		// �F�؏�񂪎擾�ł����ꍇ�̓}�C�y�[�W�̔F�؏��𕜋A����B
		LoginUser loginUser = this.mypageAuth.getLoggedInUser(request, response);
		if (loginUser != null) return loginUser;

		// �����F�؂̃��O�C�������擾����B
		return super.getLoggedInUser(request, response);
	}


	
    /**
     * ���O�C�����[�U�[�̃��[�������擾<br/>
     * �}�C�y�[�W�Ƀ��O�C���ςł���΁A�}�C�y�[�W�̃��[�����𕜋A����B<br/>
     * �}�C�y�[�W�Ƀ��O�C���ςłȂ��ꍇ�A�������O�C���̃��[�����𕜋A����B
     * <br/>
     * @param request HTTP���N�G�X�g
     * @param�@response HTTP���X�|���X
     * @return ���[�����̃��X�g�𕜋A����B
     */
	@Override
	public UserRoleSet getLoggedInUserRole(HttpServletRequest request, HttpServletResponse response) {

		// �}�C�y�[�W�̃��[�����擾���������s����B
		// �擾�ł����ꍇ�͂��̒l�𕜋A����B
		UserRoleSet roleSet = this.mypageAuth.getLoggedInUserRole(request, response);
		if (roleSet != null) return roleSet;

		// �������O�C���̃��[�������擾����B
		return super.getLoggedInUserRole(request, response);
	}

	

	/**
	 * ���O�C�����̃Z�b�V�����i�[����<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param user ���O�C�����[�U�[���
	 */
	@Override
	protected void setLoggedInUser(HttpServletRequest request, LoginUser user) {
		// �p�����̏����𗘗p���ăZ�b�V�������Ƀ��O�C��������������
		super.setLoggedInUser(request, user);
		
		// �t���[�����[�N�ł́A���[���p�� DAO ���ݒ肳��Ȃ��ꍇ�AROLE �����Z�b�V�����ɐݒ肷�鏈����
		// �s���Ȃ��B
		// �}�C�y�[�W�̃��O�C�������ł́A�����I�Ƀ��[������ݒ肷��B
		HttpSession session = request.getSession();
        Set<String> myRoles = getMyRolenamesFromDB(user.getUserId());
        session.setAttribute(this.rolesetSessionParameter, new UserRoleSet(user.getUserId(), myRoles));
	}


	
    /**
     * ���[�������擾<br/>
     * �������O�C�������̏ꍇ�A��� Set �I�u�W�F�N�g�𕜋A����B<br/>
     * <br/>
     * @param userId �L�[�ƂȂ郆�[�U�[ID
     * @return ��� Set �I�u�W�F�N�g
     */
	@Override
    protected Set<String> getMyRolenamesFromDB(Object userId) {

        return new HashSet<String>();
    }



	/**
	 * Cookie ���瓽�����O�C���p�̃��[�U�[ID ���擾����B<br/>
	 * Cookie �̒l�͈Í�������Ă���̂ŕ��������Ă��畜�A����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	 * 
	 * @return ���������ꂽ���[�U�[ID
	 */
	protected String readUserIdFromCookie(HttpServletRequest request, HttpServletResponse response){
		
		// Cookie ����Í������ꂽ UserId ���擾����B�@�l���擾�o���Ȃ��ꍇ�� null �𕜋A����B
        String hashedId = ServletUtils.findFirstCookieValueByName(request, this.anonymousUidCookieName);
        if (StringValidateUtil.isEmpty(hashedId)) return null;

        // �l���擾�ł����ꍇ�͕��������ĕ��A
        return userIdCookieCipher.decode(hashedId);
	}

	/**
	 * �����F�؂̃��[�U�[���� Cookie �֏������ށB<br\>
	 * Cookie �֏������ރ��[�U�[ID�͈Í������ď������ށB<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	 * @param loginUser�@�F�؍σ��[�U�[��� 
	 */
	protected void writeUserIdToCookie(HttpServletRequest request, HttpServletResponse response, LoginUser loginUser){

		// ���[�U�[��񂪎擾�o���Ă���ꍇ�A�Í��������l�𐶐�����B
		if (loginUser != null) {
			// Cookie �I�u�W�F�N�g���쐬���A�Í��������������ݒ肷��B
	    	ServletUtils.addCookie(response, request.getContextPath(), this.anonymousUidCookieName, 
	    			userIdCookieCipher.encode((String)loginUser.getUserId()), 
	    			cookieExpires.intValue(), this.commonParameters.getCookieDomain(), this.cookieSequre);
	
		} else {
			// ���[�U�[��񂪎擾�ł��Ȃ������ꍇ�� Cookie ���폜����B
	    	ServletUtils.delCookie(response, request.getContextPath(), this.anonymousUidCookieName, 
	    			this.commonParameters.getCookieDomain(), this.cookieSequre);
	    	
		}

	}

}
