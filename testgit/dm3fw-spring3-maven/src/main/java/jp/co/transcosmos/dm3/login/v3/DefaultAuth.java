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
 * V3 �F�ؑΉ�
 * �W����DB�F�؏����N���X
 * �����Ƃ��V���v����DB�ɂ��F�؏������������Ă���B
 *   �E���O�C������������ƁA�Z�b�V�����ɁA���[�U�[���A���[�U�[ID�i��L�[�j�A���[������ݒ肷��B
 *   �E���O�A�E�g�������s���ƁA��L�����Z�b�V��������폜����B
 *   �EgetLoggedInUser() ���g�p���ĔF�؃`�F�b�N���s���B
 *
 * �S����            �C����               �C�����e
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.04  �V�K�쐬
 * H.Mizuno  2013.04.11  �F�؃`�F�b�N�ƁA���[�U�[���擾�𕪗�
 *
 * </pre>
*/
public class DefaultAuth implements Authentication {
	private static final Log log = LogFactory.getLog(DefaultAuth.class);
	
	// �F�ؗp DAO
	protected DAO<LoginUser> userDAO;

	// �ŏI�X�V���X�V DAO
	// �F�ؗp�ƁA�ŏI�X�V�����Ǘ�����t�B�[���h���ʂ̏ꍇ�A���̃v���p�e�B�ɒl��ݒ肷��B
	// ���ݒ�̏ꍇ�AuserDAO ���X�V�p�Ƃ��Ă��̂܂܎g�p����B
	protected DAO<LoginUser> writeableUserDAO;

	// ���[�����p DAO
	protected ReadOnlyDAO<?> userRoleDAO;

	// �F�؂Ŏg�p���郆�[�U�[���̃��[�U�[ID�i��L�[�j ���i�[����Ă���DB�̗�
	protected String userDAOUserIdField = "userId";    

	// �F�؂Ŏg�p���郆�[�U�[���̃��O�C��ID ���i�[����Ă���DB�̗�
	protected String userDAOLoginIdField = "loginId";    

    // �F�؂Ŏg�p���郍�[�������擾����ہA���[�U�[ID ���i�[����Ă���DB�̗�
	protected String userRoleDAOUserIdField = "userId";

    // �F�؂Ŏg�p���郍�[�������i�[����Ă���DB�̗�
	protected String userRoleDAORolenameField = "rolename";

    // �F�؂Ŏg�p���郍�[�������擾����ہA���[�U�[ID ���i�[����Ă���e�[�u���̕ʖ�
    // �ʏ�͐ݒ肵�Ȃ����A���[���̎擾�� JoinDAO ���g�p����ꍇ�ɐݒ肷��B
	protected String userRoleDAOUserIdAlias = "";

    // �F�؂Ŏg�p���郍�[�������擾����ہA���[�������i�[����Ă���e�[�u���̕ʖ�
    // �ʏ�͐ݒ肵�Ȃ����A���[���̎擾�� JoinDAO ���g�p����ꍇ�ɐݒ肷��B
	protected String userRoleDAORolenameAlias = "";
    
    // ���O�C�����[�U�[�����Z�b�V�����ɏ������ގ��̃L�[��
	protected String loginUserSessionParameter = "loggedInUser";

    // ���[�U�[ID(��L�[�l)���Z�b�V�����ɏ������ގ��̃L�[��
	protected String loginUserIdSessionParameter = "loggedInUserId";

    // ���[�������Z�b�V�����ɏ������ގ��̃L�[��
	protected String rolesetSessionParameter = "loggedInUserRoleset";



    /**
     * ���O�C�������i�蓮���O�C�������j<br/>
     * ����I������΁A���O�C�������Z�b�V�����Ɋi�[���A���O�C�����[�U�[���𕜋A����B<br/>
     * ���O�C���Ɏ��s�����ꍇ�Anull �𕜋A����B<br/>
     * <br/>
     * @param request HTTP���N�G�X�g
     * @param�@response HTTP���X�|���X
     * @param�@form ���O�C����ʂ̃t�H�[��
     * @return ���O�C�����[�U�[�̃I�u�W�F�N�g
     */
	@Override
	public LoginUser login(HttpServletRequest request, HttpServletResponse response,
			LoginForm form) {

        DAOCriteria criteria = new DAOCriteria();
        criteria.addWhereClause(this.userDAOLoginIdField, form.getLoginID());

        List<LoginUser> matchingUsers = this.userDAO.selectByFilter(criteria);

        // �擾�����f�[�^����A�p�X���[�h����v���郌�R�[�h�����t����Ȃ��ꍇ�A null �𕜋A����B
        for (Iterator<LoginUser> i = matchingUsers.iterator(); i.hasNext(); ) {
            LoginUser user = i.next();
            if (user.matchPassword(form.getPassword())) {

            	// �ŏI���O�C�����̍X�V����
            	updateLastLoginTimestamp(user);

                // �Z�b�V�����Ƀ��[�U�[������������
                setLoggedInUser(request, user);
                return user;
            }
        }
        return null;
	}

	
	
    /**
     * ���O�A�E�g����<br/>
     * �Z�b�V�������烍�O�C�������폜����B<br/>
     * <br/>
     * @param request HTTP���N�G�X�g
     * @param�@response HTTP���X�|���X
     */
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		
		// ���[�U�[�����Z�b�V��������폜����
		session.removeAttribute(this.loginUserSessionParameter);
        log.info("removed user from session");

        // ���[�U�[ID���Z�b�V��������폜����
        session.removeAttribute(this.loginUserIdSessionParameter);
        log.info("removed userid from session");

		// ���[�������Z�b�V��������폜����
        session.removeAttribute(rolesetSessionParameter);

	}

	
	
    /**
     * ���O�C����Ԃ̔��菈��<br/>
     * �Z�b�V�������烍�O�C����񂪎擾�\�ȏꍇ�A���O�C�����[�U�[�̏��𕜋A����B<br/>
     * �擾�ł��Ȃ��ꍇ�Anull �𕜋A����B<br/>
     * <br/>
     * @param request HTTP���N�G�X�g
     * @param�@response HTTP���X�|���X
     * @return ���O�C�����[�U�[�̃I�u�W�F�N�g�B�@���O�C���ςłȂ��ꍇ�Anull �𕜋A����B
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
     * ���O�C�����[�U�[�̏��擾<br/>
     * ���̔F�؏����ł́AcheckLoggedIn �Ɠ��������B<br/>
     * <br/>
     * @param request HTTP���N�G�X�g
     * @param�@response HTTP���X�|���X
     * @return ���O�C�����[�U�[�̃I�u�W�F�N�g�B�@���O�C���ςłȂ��ꍇ�Anull �𕜋A����B
     */
	public LoginUser getLoggedInUser(HttpServletRequest request, HttpServletResponse response){
		return checkLoggedIn(request, response);
	}

	
    /**
     * ���O�C�����[�U�[�̃��[�������擾<br/>
     * ���O�C���ς̏ꍇ�A�Z�b�V�����Ƀ��[����񂪊i�[����Ă���̂ŁA���̒l�𕜋A����B<br/>
     * ���[����񂪎擾�ł��Ȃ��ꍇ�� null �𕜋A����B<br/>
     * <br/>
     * @param request HTTP���N�G�X�g
     * @param�@response HTTP���X�|���X
     * @return ���[�����̃��X�g�𕜋A����B
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
     * �ŏI���O�C�����̍X�V����<br/>
     * �F�ؗpDAO �� VO ���ALastLoginTimestamped ���������Ă���ꍇ�A�ŏI���O�C������<br/>
     * �X�V����B�@�C���^�[�t�F�[�X����������Ă��Ȃ��ꍇ�A�Ȃɂ����Ȃ��B<br/>
     * <br/>
     * @param user ���O�C�����[�U�[�̃I�u�W�F�N�g
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
     * ���O�C�����̃Z�b�V�����i�[����<br/>
     * ���O�C���������������Ɏ��s����A���[�U�[���A���[�U�[ID�A���[�������Z�b�V�����ɏ������ށB<br/>
     * <br/>
     * @param request HTTP���N�G�X�g�I�u�W�F�N�g
     * @param user ���O�C�����[�U�[�̃I�u�W�F�N�g
     */
    protected void setLoggedInUser(HttpServletRequest request, LoginUser user) {

		HttpSession session = request.getSession();

    	// ���[�U�[�����A�Z�b�V�����Ɋi�[����B
    	session.setAttribute(this.loginUserSessionParameter, user);
        log.info("add user to session; user=" + user);

        // ���[�U�[ID���A�Z�b�V�������Ɋi�[����B
        session.setAttribute(this.loginUserIdSessionParameter, user.getUserId());
        log.info("add userid to session; userid=" + user.getUserId());            

    	// ���[�����p�� DAO ���ݒ肳��Ă���ꍇ�A���[�U�[�̃��[�������Z�b�V�����Ɋi�[����B
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

    	// �Z�b�V�����ɁA���O�C�����ԁi�ŏI�A�N�Z�X���ԁj��ݒ肷��B
        session.setAttribute("lastRefreshedTimestamp", new Long(System.currentTimeMillis()));
    }
    
	

    /**
     * ���[�������擾<br/>
     * �f�[�^�x�[�X���烍�O�C�������擾���A���[�����̏��𕜋A����B<br/>
     * <br/>
     * @param userId �L�[�ƂȂ郆�[�U�[ID
     * @return �擾�������[������ Set �I�u�W�F�N�g
     */
    protected Set<String> getMyRolenamesFromDB(Object userId) {

    	DAOCriteria criteria;
    	// ���[�U�[ID�@�̕ʖ����ݒ肳��Ă���ꍇ�iJoinDAO ���g�p���Ă���ꍇ�j�A�ʖ����g�p���Č����������쐬����
    	if (this.userRoleDAOUserIdAlias != null && this.userRoleDAOUserIdAlias.length() > 0)
    		criteria = new DAOCriteria(this.userRoleDAOUserIdAlias, this.userRoleDAOUserIdField, userId, DAOCriteria.EQUALS, false);
    	else
        	criteria = new DAOCriteria(this.userRoleDAOUserIdField, userId);
        

    	// ���[������DB����擾����
    	List<?> results = this.userRoleDAO.selectByFilter(criteria);
        Set<String> myRoles = new HashSet<String>();

        for (Iterator<?> i = results.iterator(); i.hasNext(); ) {
            Object role = i.next();
            if (role == null) {
                continue;
            } else if (role instanceof String) {
                myRoles.add((String) role);
            } else try {

            	// JoinDAO �̏ꍇ�A���ʂ̓l�X�g���Ă���ׁA�X�Ɍ��ʃZ�b�g���烍�[�������擾����B
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


    
    // setter�Agetter
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
