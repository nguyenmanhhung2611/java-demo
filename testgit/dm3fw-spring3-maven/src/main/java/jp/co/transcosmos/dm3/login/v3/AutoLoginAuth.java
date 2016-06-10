package jp.co.transcosmos.dm3.login.v3;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.login.CookieLoginUser;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.LoginUtils;
import jp.co.transcosmos.dm3.login.form.v3.AutoLoginForm;
import jp.co.transcosmos.dm3.login.form.v3.LoginForm;


/**
 * <pre>
 * V3 �F�ؑΉ�
 * �������O�C���@�\��ǉ������ADB�F�؏����N���X
 * ���O�C���ς݃`�F�b�N�Ɏ��s���Ă��ACookie �̒l���g���ă`�F�b�N���p������B
 * �`�F�b�N�ɐ��������ꍇ�A���O�C�����𐶐����ĕ��A����B Cookie �̑����A�Í����́AV1�AV2 ��
 * ���ʂ̃N���X���g�p���Ă���B
 *
 * �S����            �C����               �C�����e
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.05  �V�K�쐬
 * H.Mizuno  2013.04.11  �F�؃`�F�b�N�ƁA���[�U�[���擾�𕪗�
 *                       ���O�A�E�g���ɁA�F�ؗp Cookie ���폜���鏈����ǉ�
 * H.Mizuno  2015.06.24  �������O�C���p�g�[�N�����A�F�ؐ������ɍč쐬����@�\��ǉ�
 *
 * </pre>
*/
public class AutoLoginAuth extends DefaultAuth {
	private static final Log log = LogFactory.getLog(AutoLoginAuth.class);

	// �������O�C���Ŏg�p���� Cookie �̗L�������i�b�j
    protected Integer autoLoginCookieExpirySeconds;

    // �������O�C���Ŏg�p���� Cookie �́@Domain ��
    protected String cookieDomain = null;

	// �蓮���O�C���̃`�F�b�N�p�g�[�N����
    protected String manualLoginTokenName = "manualLoginToken";

    // true �̏ꍇ�A���O�A�E�g���ɔF�؏����p Cookie ���폜����
    protected boolean removeCookieWithLogout;

// 2015.04.20 H.Mizuno cookie �� sequre �����Ή� start
    // true �̏ꍇ�A�������O�C���Ŏg�p���� cookie �� sequre ������ݒ肷��
    protected boolean cookieSequre;
// 2015.04.20 H.Mizuno cookie �� sequre �����Ή� end

// 2015.06.24 H.Mizuno �F�ؐ������ɁA�������O�C���p�g�[�N�����č쐬����@�\��ǉ� start
    // Cookie �Ɋi�[���鎩�����O�C���p�g�[�N����F�ؐ������Ƀ��Z�b�g����ꍇ�A���̃v���p�e�B�� true �ɐݒ肷��B
    // �i�f�t�H���g false�j
    // ���̋@�\��L���ɂ����ꍇ�A�F�؂���������s�x�A�������O�C���p�g�[�N�����Đݒ肳���̂ŁA
    // �����u���E�U���g�p���Ă���ꍇ�A�ʂ̃u���E�U�̎������O�C�����L�����Z�������B
    // �f�t�H���g�ł̓��Z�b�g���鏈���͖���������Ă��邪�Acookie �����o����Ǝ������O�C�����\
    // �Ȃ̂ŁA��L�������������e�͈͂ł���ΗL�������Ă���������S�ƌ�����B
    protected boolean useAutoLoginTokenReset;
// 2015.06.24 H.Mizuno �F�ؐ������ɁA�������O�C���p�g�[�N�����č쐬����@�\��ǉ� end

    

// 2015.06.24 H.Mizuno �f�t�H���g�̐U�镑�������� start
    public AutoLoginAuth(){
    	this.removeCookieWithLogout = true;
    	// Ver 1.5.x �n�܂ł́Acookie �̃Z�L���A�����t�^�̃f�t�H���g�͖������������A
    	// �Z�L�����e�B�I�Ɋ댯�Ȃ̂� Ver 1.6.x �n�ł́A�f�t�H���g��L���ɕύX�B
    	this.cookieSequre = true;

    	// �������O�C���p�g�[�N�����A�F�ؐ������Ƀ��Z�b�g����@�\�̃f�t�H���g�͖����B
    	this.useAutoLoginTokenReset = false;
    }
// 2015.06.24 H.Mizuno �f�t�H���g�̐U�镑�������� end



    /**
     * ���O�C�������i�蓮���O�C�������j<br/>
     * �v���p�e�B�Őݒ肳�ꂽ�F�ؗp�N���X���g�p���ă��O�C�����������s����B<br/>
     * ���O�C���Ɏ��s�����ꍇ�Acookie �̔F�؏����폜����B<br/>
     * ���O�C�����������āA�������O�C�����`�F�b�N����Ă���ꍇ�Acookie �ɔF�؏���ǉ�����B<br/>
     * �܂��A�������O�C�����`�F�b�N����Ă��Ȃ��ꍇ�Acookie �̔F�؏����폜����B<br/>
     * <br/>
     * ���������O�C���̔���p�ɁA���O�C������������ƁA�t���O���Z�b�V�����Ɋi�[���Ă���B<br/>
     *   ���O�C�����s���́A���O�A�E�g���������s�����̂ŁA���̃��\�b�h���ł͍폜���Ă��Ȃ��B<br/>
     * <br/>
     * @param request HTTP���N�G�X�g
     * @param�@response HTTP���X�|���X
     * @param�@form ���O�C����ʂ̃t�H�[��
     * @return ���O�C�����[�U�[�̃I�u�W�F�N�g
     */
	@Override
	public LoginUser login(HttpServletRequest request, HttpServletResponse response, LoginForm form) {

		// LoginComannd �Ŏg���Ă��� Form �N���X���Ԉ���Ă���ꍇ�B�@�ݒ�~�X�Ȃ̂ŁA��O���グ�ďI���B
		if (!(form instanceof AutoLoginForm))
			throw new RuntimeException("form type is miss match");

		// �ʏ�̃��O�C�����������s
		LoginUser user = super.login(request, response, form);
		if (user == null) {
			// ���O�C���Ɏ��s�����ꍇ�A Cookie �̃��O�C�������폜����B
// 2015.05.20 H.Mizuno cookie �� sequre �����Ή� start
//			LoginUtils.setAutoLoginCookies(response, request.getContextPath(), null, null,
//					this.cookieDomain);
			LoginUtils.setAutoLoginCookies(response, request.getContextPath(), null, null,
					this.cookieDomain, this.cookieSequre);
// 2015.05.20 H.Mizuno cookie �� sequre �����Ή� end
			return null;
		}
		
		// �蓮���O�C���`�F�b�N�p�̃g�[�N�������ݒ肳��Ă���ꍇ�A�ʏ�̃��O�C������������ƁA�Z�b�V����
		// �Ƀ`�F�b�N�p�g�[�N����ݒ肷��B
		// ���̒l�́AManualLoginCheckFilter �ŁA�蓮���O�C���̗L�����`�F�b�N����ۂɎg�p�����B
        if (this.manualLoginTokenName != null) {
        	HttpSession session = request.getSession();
        	session.setAttribute(this.manualLoginTokenName, Boolean.TRUE);
        }

		// ���O�C�������������ꍇ�̂݁ACookie �Ɏ������O�C���p�̃g�[�N����ݒ肷��B
		// ���R�A�������O�C���̃`�F�b�N���O����Ă���ꍇ�� Cookie ����폜����B
        if (((AutoLoginForm)form).isAutoLoginSelected() && 
                (user instanceof CookieLoginUser)) {
// 2015.05.20 H.Mizuno cookie �� sequre �����Ή� start
//            LoginUtils.setAutoLoginCookies(response, request.getContextPath(), user.getUserId(), 
//                    retrieveCookieLoginPassword(request, response), this.autoLoginCookieExpirySeconds,
//                    this.cookieDomain);
            LoginUtils.setAutoLoginCookies(response, request.getContextPath(), user.getUserId(), 
                    retrieveCookieLoginPassword(request, response), this.autoLoginCookieExpirySeconds,
                    this.cookieDomain, this.cookieSequre);
// 2015.05.20 H.Mizuno cookie �� sequre �����Ή� end
        } else {
// 2015.05.20 H.Mizuno cookie �� sequre �����Ή� start
//            LoginUtils.setAutoLoginCookies(response, request.getContextPath(), null, null,
//            		this.cookieDomain);
            LoginUtils.setAutoLoginCookies(response, request.getContextPath(), null, null,
            		this.cookieDomain, this.cookieSequre);
// 2015.05.20 H.Mizuno cookie �� sequre �����Ή� end
        }

		return user;
	}



    /**
     * ���O�A�E�g����<br/>
     * �v���p�e�B�ɐݒ肳�ꂽ�F�ؗp�N���X���g�p���ă��O�A�E�g�������s���B<br/>
     * �܂��A�������O�C������p�̃t���O���Z�b�V��������폜����B<br/>
     * <br/>
     * @param request HTTP���N�G�X�g
     * @param�@response HTTP���X�|���X
     */
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response) {

		// �ʏ�̃��O�A�E�g���������s
		super.logout(request, response);
		
		// �蓮���O�C���`�F�b�N�p�̃g�[�N�������ݒ肳��Ă���ꍇ�A���O�A�E�g��Ƀ`�F�b�N�p�g�[�N���� �폜����B
		if (this.manualLoginTokenName != null) {
        	HttpSession session = request.getSession(false);
        	if (session != null) {
        		session.removeAttribute(this.manualLoginTokenName);
        	}
        }
		
		// ���O�A�E�g��ɁACookie �̔F�؏����폜����B
		// �A���A�v���p�e�B�̐ݒ�Ŗ������\�B�@�f�t�H���g�͍폜����B
        if (this.removeCookieWithLogout) {
// 2015.05.20 H.Mizuno cookie �� sequre �����Ή� start
//        	LoginUtils.setAutoLoginCookies(response, request.getContextPath(), null, null,
//            		this.cookieDomain);
        	LoginUtils.setAutoLoginCookies(response, request.getContextPath(), null, null,
            		this.cookieDomain, this.cookieSequre);
// 2015.05.20 H.Mizuno cookie �� sequre �����Ή� end
        }
	}

	
	
    /**
     * ���O�C����Ԃ̔��菈��<br/>
     * �ʏ�̃Z�b�V�������ɂ��`�F�b�N�����Ɏ��s�����ꍇ�A Cookie �ɁA�Í������ꂽ�����O�C��<br/>
     * ��񂪑��݂��邩�`�F�b�N����B<br/>
     * ���݂���ꍇ�A�l�𕡍�������DB�̏��Əƍ�����B<br/>
     * �ƍ��ɐ��������ꍇ�A���O�C�������Z�b�V�����ɍ쐬���ă��O�C���ςƂ��ĕ��A����B<br/>
     * <br/>
     * @param request HTTP���N�G�X�g
     * @param�@response HTTP���X�|���X
     * @return ���O�C�����[�U�[�̃I�u�W�F�N�g�B�@���O�C���ςłȂ��ꍇ�Anull �𕜋A����B
     */
	@Override
	public LoginUser checkLoggedIn(HttpServletRequest request, HttpServletResponse response) {

		// �ʏ�̃��O�C���`�F�b�N�����s�A���O�C���ς̏ꍇ�́A���̂܂ܒl��Ԃ��B
		LoginUser user = super.checkLoggedIn(request, response);
		if (user != null) return user;


		// �������O�C���̏ꍇ�Acookie ����A�Í������ꂽ���[�U�[ID�i��L�[�j���擾����B
		// �擾�o���Ȃ��ꍇ�A�F�؃G���[�Ƃ��āAnull �𕜋A����B
		String autoLoginSecret = LoginUtils.getAutoLoginPasswordFromCookie(request);
		if (autoLoginSecret == null) {
            log.info("No auto-login secret found");
            return null;
		}
		
		// �擾�����Í������ꂽ���[�U�[ID�i��L�[�j�𕜍�������B
		Object userID = LoginUtils.getAutoLoginUserIdFromCookie(request);
		if( userID == null ){
            log.info("No auto-login user id found");
            return null;
		}

		// ���[�U�[ID�i��L�[�j���擾�ł����ꍇ�ACookie �ɂ�鎩�����O�C�������s����B
// 2015.06.24 H.Mizuno �F�ؐ������ɁA�������O�C���p�g�[�N�����č쐬����@�\��ǉ� start
//        CookieLoginUser cookieLoginUser = cookieAutoLogin(request, userID, autoLoginSecret);
        CookieLoginUser cookieLoginUser = cookieAutoLogin(request, response, userID, autoLoginSecret);
// 2015.06.24 H.Mizuno �F�ؐ������ɁA�������O�C���p�g�[�N�����č쐬����@�\��ǉ� end
		if( cookieLoginUser == null ){
            log.info("Cookie login failed");
            return null;
		}

		return cookieLoginUser;
	}


    /**
     * ���O�C�����[�U�[�̏��擾<br/>
     * �Z�b�V�������烍�O�C����񂪎擾�\�ȏꍇ�A���O�C�����[�U�[�̏��𕜋A����B<br/>
     * �擾�ł��Ȃ��ꍇ�Anull �𕜋A����B�@�v����ɁA�e�N���X��checkLoggedIn()<br/>
     * �Ɠ��������B
     * <br/>
     * @param request HTTP���N�G�X�g
     * @param�@response HTTP���X�|���X
     * @return ���O�C�����[�U�[�̃I�u�W�F�N�g�B�@���O�C���ςłȂ��ꍇ�Anull �𕜋A����B
     */
	public LoginUser getLoggedInUser(HttpServletRequest request, HttpServletResponse response){
		return super.checkLoggedIn(request, response);
	}


	/**
     * Cookie �ɂ�鎩�����O�C������<br/>
     * ��{�I�ɁA�ʏ�̃��O�C���Ɠ��������A�p�X���[�h�̏ƍ��́A<br/>
     * CookieLoginUser#matchCookieLoginPassword() �ɂčs���B<br/>
     * ����́A�ʏ�̃��O�C���Ɏg�p����p�X���[�h�� Cookie �Ɋi�[����Ɗ댯�Ȃ̂ŁA<br/>
     * �ʂ̃n�b�V�������l�ŏƍ����鎖���\�ɂ���ׂ̏��u�ł���B<br/>
     * <br/>
     * @param request HTTP���N�G�X�g
     * @param�@userId Cookie ���畜���������I�u�W�F�N�g
     * @param�@loginSecretCookie Cookie �Ɋi�[����Ă����Í�������Ă���I�u�W�F�N�g
     * @return ���O�C�����[�U�[�̃I�u�W�F�N�g�i�������O�C���p�j�B�@���O�C���ςłȂ��ꍇ�Anull �𕜋A����B
     */
// 2015.06.24 H.Mizuno �F�ؐ������ɁA�������O�C���p�g�[�N�����č쐬����@�\��ǉ� start
//	protected CookieLoginUser cookieAutoLogin(HttpServletRequest request,
//			Object userId, String loginSecretCookie){

	/**
	 * �������O�C������<br/>
	 * ���o�[�W�����ւ̌݊��p���\�b�h�B�@�񐄏��B<br/>
	 * <br/>
	 * @deprecated
	 * @param request HTTP ���N�G�X�g
	 * @param userId�@���[�U�[ID
	 * @param loginSecretCookie �������O�C���p�g�[�N��
	 * @return
	 */
	protected CookieLoginUser cookieAutoLogin(HttpServletRequest request,
			Object userId, String loginSecretCookie){

		return cookieAutoLogin(request, null, userId, loginSecretCookie);
	}

	/**
	 * �������O�C������<br/>
	 * �w�肳�ꂽ���[�U�[ID ���L�[�Ƃ��āA���[�U�[����DB����擾����B<br/>
	 * �擾�������[�U�[���̎������O�C���g�[�N������v����ꍇ�A���O�C���������X�V�A�Z�b�V�����Ƀ��[�U�[
	 * �����i�[���Ă��烆�[�U�[���𕜋A����B<br/>
	 * �����AuseAutoLoginTokenReset�@���L���ȏꍇ�A�������O�C���̔F�،�Ɏ������O�C���p�̃g�[�N��
	 * ���Ĕ��s���Acookie �� DB �̒l���X�V����B
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	 * @param userId�@���[�U�[ID
	 * @param loginSecretCookie �������O�C���p�g�[�N��
	 * @return
	 */
	protected CookieLoginUser cookieAutoLogin(HttpServletRequest request,
			HttpServletResponse response, Object userId, String loginSecretCookie){
// 2015.06.24 H.Mizuno �F�ؐ������ɁA�������O�C���p�g�[�N�����č쐬����@�\��ǉ� end

		// ���[�U�[ID�i��L�[�j���g�p���āA���[�U�[�����擾����B
        DAOCriteria criteria = new DAOCriteria();
        criteria.addWhereClause(this.userDAOUserIdField, userId);
        List<LoginUser> users = this.userDAO.selectByFilter(criteria);

        for (Iterator<LoginUser> i = users.iterator(); i.hasNext(); ) {
            LoginUser user = i.next();
            // Cookie ����擾�����Í������ꂽ���ƁADB�̏����ƍ����A��v�����ꍇ�A
            // ���O�C���������s���B
            if ((user instanceof CookieLoginUser) && 
                    ((CookieLoginUser) user).matchCookieLoginPassword(loginSecretCookie)) {

            	updateLastLoginTimestamp(user);
                setLoggedInUser(request, user);

// 2015.06.24 H.Mizuno �F�ؐ������ɁA�������O�C���p�g�[�N�����č쐬����@�\��ǉ� start
                if (this.useAutoLoginTokenReset) {

                	// �V���Ȏ������O�C���p�g�[�N�����̔Ԃ��ADB �Ɋi�[����B
                	String newLoginSecretCookie = retrieveCookieLoginPassword(request, response);
                	
                	// �̔Ԃ����������O�C���p�g�[�N���� Cookie �ɏ�����
                    LoginUtils.setAutoLoginCookies(response, request.getContextPath(), user.getUserId(), 
                    		newLoginSecretCookie, this.autoLoginCookieExpirySeconds,
                            this.cookieDomain, this.cookieSequre);
                    
                    // ���A���郆�[�U�[���̎������O�C���p�g�[�N�����A�V���ɍ̔Ԃ����l�ɕύX����B
                    ((CookieLoginUser) user).setCookieLoginPassword(newLoginSecretCookie);
                }
// 2015.06.24 H.Mizuno �F�ؐ������ɁA�������O�C���p�g�[�N�����č쐬����@�\��ǉ� end
                
                return (CookieLoginUser) user;
            }                
        }
        log.info("No user found with ID=" + userId + 
                " and matching cookie login password - Failing auto-login");
        return null;
	}


	// ���j
	// ���̎������ƁA�P�x���O�C������ƁA�ȍ~�ACookie �ɏ������܂��L�[�̒l���Œ艻����Ă��܂�
	// �l�Ɏv����B�@�ʏ탍�O�C���̏ꍇ�ʂ́AKey �𐶐��������������ǂ��̂ł́H

	/**
     * Cookie �ɏ������ރ��O�C���p�̃g�[�N���𐶐�<br/>
     * Cookie �ɏ������ރ��O�C���p�̃g�[�N���́A�t���[�����[�N���������_���ȕ�������Í������č쐬����B<br/>
     * ���̈Í�������������́AcookieLoginUser#setCookieLoginPassword() �Ƀ}�b�v�����
     * DB��̃t�B�[���h�Ɋi�[�����B�@�ȍ~�ADB��ɈÍ������ꂽ�g�[�N�������݂���ꍇ�A���̃g�[�N�����g��
     * �܂킷�B<br/>
     * <br/>
     * @param request HTTP���N�G�X�g
     * @param�@userId Cookie ���畜���������I�u�W�F�N�g
     * @param�@loginSecretCookie Cookie �Ɋi�[����Ă����Í�������Ă���I�u�W�F�N�g
     * @return �������O�C���p�̃g�[�N��������B�@���O�C���ςłȂ��ꍇ�Anull �𕜋A����B
     */
	protected String retrieveCookieLoginPassword(HttpServletRequest request, HttpServletResponse response){
		
		// ���[�U�[�����擾
		// checkLoggedIn() ���̂́A�������O�C���̏��������݂��邪�AretrieveCookieLoginPassword()
		// �́A���O�C�������������ꍇ�Ɏ��������̂ŁA�Z�b�V�������烍�O�C�����������o�����݂̂��s����B
        LoginUser user = checkLoggedIn(request, response);


        // �������O�C���@�\���L���ȏꍇ�̂ݎ������O�C���p�̃g�[�N���𐶐�����B
        if (user instanceof CookieLoginUser) {
            CookieLoginUser cookieLoginUser = (CookieLoginUser) user;

// 2015.06.24 H.Mizuno �F�ؐ������ɁA�������O�C���p�g�[�N�����č쐬����@�\��ǉ� start
//            if (cookieLoginUser.getCookieLoginPassword() == null) {
            
            // DB �ɕۊǂ���Ă���A�������O�C���p�̃g�[�N�����ݒ肳��Ă��Ȃ��ꍇ�i���񃍃O�C�����Ȃǁj�A
            // �g�[�N���𐶐����ADB�Ɋi�[����B
            // �܂��AuseAutoLoginTokenReset�@�v���p�e�B�� true �̏ꍇ�A��Ɏ������O�C���p�̃g�[�N��
            // ���Đݒ肷��B
            if (cookieLoginUser.getCookieLoginPassword() == null || this.useAutoLoginTokenReset) {
// 2015.06.24 H.Mizuno �F�ؐ������ɁA�������O�C���p�g�[�N�����č쐬����@�\��ǉ� end
                cookieLoginUser.setCookieLoginPassword(LoginUtils.generateAutoLoginPassword());

                if (writeableUserDAO != null) {
                	this.writeableUserDAO.update(new LoginUser[] {cookieLoginUser});
                } else {
                	this.userDAO.update(new LoginUser[] {cookieLoginUser});
                }
            }
            return cookieLoginUser.getCookieLoginPassword();
        } else {
            return null;
        }
	}


	
    // setter�Agetter
    public void setAutoLoginCookieExpirySeconds(int pAutoLoginCookieExpirySeconds) {
        this.autoLoginCookieExpirySeconds = new Integer(pAutoLoginCookieExpirySeconds);
    }
	
	public void setCookieDomain(String cookieDomain) {
		this.cookieDomain = cookieDomain;
	}

	public void setManualLoginTokenName(String manualLoginTokenName) {
		this.manualLoginTokenName = manualLoginTokenName;
	}

	public void setRemoveCookieWithLogout(boolean removeCookieWithLogout) {
		this.removeCookieWithLogout = removeCookieWithLogout;
	}

// 2015.05.20 H.Mizuno cookie �� sequre �����Ή� start
	/**
	 * �������O�C���p�g�[�N���ɃZ�L���A������t�^���Ȃ��ꍇ�� false ��ݒ肷��B<br/>
	 * ���Z�L�����e�B�I�l���ɂ��AVer 1.6 �n����f�t�H���g�� true �ɕύX����Ă���B
	 * <br/>
	 * @param cookieSequre ������t���Ȃ��ꍇ�� false ��ݒ肷��B�i�f�t�H���g true�j
	 */
	public void setCookieSequre(boolean cookieSequre) {
		this.cookieSequre = cookieSequre;
	}
// 2015.05.20 H.Mizuno cookie �� sequre �����Ή� end


// 2015.06.24 H.Mizuno �F�ؐ������ɁA�������O�C���p�g�[�N�����č쐬����@�\��ǉ� start
	/**
	 * �F�ؐ������Ɏ������O�C���p�g�[�N�����Đݒ肷��ꍇ�� true ��ݒ肷��B<br/>
	 * <br/>
	 * @param useAutoLoginTokenReset �@�\��L���ɂ���ꍇ true �i�f�t�H���g false�j
	 */
	public void setUseAutoLoginTokenReset(boolean useAutoLoginTokenReset) {
		this.useAutoLoginTokenReset = useAutoLoginTokenReset;
	}
// 2015.06.24 H.Mizuno �F�ؐ������ɁA�������O�C���p�g�[�N�����č쐬����@�\��ǉ� end
	
}
