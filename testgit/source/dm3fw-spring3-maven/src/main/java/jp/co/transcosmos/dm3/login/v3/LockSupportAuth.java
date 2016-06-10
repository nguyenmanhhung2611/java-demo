package jp.co.transcosmos.dm3.login.v3;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.FormulaUpdateExpression;
import jp.co.transcosmos.dm3.dao.UpdateExpression;
import jp.co.transcosmos.dm3.dao.UpdateValue;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.LockSupportLoginUser;
import jp.co.transcosmos.dm3.login.UserRoleSet;
import jp.co.transcosmos.dm3.login.form.v3.LoginForm;


/**
 * ���O�C�����s�񐔂ɉ������A�A�J�E���g���b�N�Ή��F�؃N���X.
 * <p>
 * DefaultAuth �̔h���n�F�؃N���X�ɑ΂��ăA�J�E���g���b�N�@�\��ǉ����� Adaptor �N���X�B<br>
 * ���O�C���������ALockSupportChecker ���g�p���ăA�J�E���g�̃��b�N��Ԃ��`�F�b�N����B<br>
 * �������b�N���������ꍇ�͗�O���X���[����B<br>
 * �܂��A���O�C���`�F�b�N�����������l�̑Ή����s���B�@����́A�������O�C�������̏ꍇ�A���O�C���`�F�b�N
 * �����Ŏ����I�Ƀ��O�C�����s����ꍇ������ׂ̑Ή��ł���B<br>
 * �F�؎��s���͎��s�񐔂��J�E���g�A�b�v����B<br>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.24	�V�K�쐬
 * H.Mizuno		2015.05.19	�ԍۂ炵�����\�b�h�����g�p����Ă����̂ŕύX
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * V3 �F�؂̂ݑΉ�<br/>
 * <br/>
 * AutoLoginAuth �iCookie �ɂ�鎩�����O�C�������j���g�p����ꍇ�A�ʏ�̃��O�C���������o�C�p�X
 * �����̂ŁA���̃`�F�b�N�t�B���^�[�ł͑Ή��ł��Ȃ��B<br/>
 * AutoLoginAuth ���g�p����ꍇ�́ACookieLoginUser�@�C���^�[�t�F�[�X�Œ�`����Ă���A
 * matchCookieLoginPassword() �̎����N���X����A���̃t�B���^�[�� lockChecker �ɐݒ肵�Ă���
 * �N���X���g�p���āA���b�N��Ԃ��`�F�b�N����K�v������B<br/>
 * 
 */
public class LockSupportAuth implements LockAuthentication {

	private static final Log log = LogFactory.getLog(LockSupportAuth.class);


	/**
	 * �Ϗ���ƂȂ�F�؃N���X<br/>
	 * ���� Adaptor �N���X�́ADefaultAuth �̔h���n�N���X���^�[�Q�b�g�Ƃ��Ă���B<br/>
	 * ����ȊO�̃N���X�̓T�|�[�g���Ă��Ȃ��B<br/>
	 */
	private DefaultAuth targetAuth;

	/** �F�ؗp DAO */
	protected DAO<LockSupportLoginUser> userDAO;

	/** �F�؂Ŏg�p���郆�[�U�[���̃��O�C�����s�� ���i�[����Ă���DB�̗� */
	protected String userDAOFailCntField = "failCnt";    

	/** �F�؂Ŏg�p���郆�[�U�[���̍ŏI���O�C�����s�����i�[����Ă���DB �̗� */
	protected String userDAOLastFailDateField = "lastFailDate";    

	/** �A�J�E���g���b�N�̃`�F�b�N�N���X */
	protected LockSupportChecker lockChecker; 



	/**
	 * �A�J�E���g���b�N�Ή��A���O�C������<br/>
	 * �Ϗ���̔F�؃N���X���g�p���ă��O�C���������s���B<br/>
	 * �F�؏������I�������ꍇ�A�擾�������O�C�����[�U�[���̃��b�N�X�e�[�^�X���`�F�b�N����B<br/>
	 * �����A�A�J�E���g�����b�N��Ԃ̏ꍇ�̓��O�A�E�g�������s���A LockException ���X���[����B<br/>
	 * �A�J�E���g�̃��b�N��Ԃ��ʏ�̏ꍇ�A���̂܂܃��O�C�����𕜋A����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	 * @param form ���̓t�H�[��
	 */
	@Override
	public LoginUser login(HttpServletRequest request, HttpServletResponse response, LoginForm form) {

		// �F�؂���������ƁA�F�؏������c�a��̒l�i�ŏI���O�C�����Ȃǁj���X�V���Ă��܂��B
		// ���ׁ̈A�F�؏����̑O�Ɏw�肳�ꂽ���O�C�� ID �����݂���ꍇ�A���b�N�󋵂��`�F�b�N����B
		LockSupportLoginUser loginUser = getUserInfo(form.getLoginID());

		if (loginUser != null && isLocked(loginUser)){
			// �������b�N��Ԃ������ꍇ�ALockException �̗�O���X���[����B
			throw new LockException();
		}


		// �Ϗ���̃��O�C�����������s���A�F�؏������s���B
		loginUser = (LockSupportLoginUser)this.targetAuth.login(request, response, form);
		
		// �F�؂����s�����ꍇ
		if (loginUser == null) {

			// ���O�C��ID �ɊY�����郆�[�U�[�����݂���ꍇ�A���s�񐔂��J�E���g�A�b�v����B
			failCountUp(form);

			// �F�؂����s���Ă���ꍇ�� null �𕜋A���ďI���B
			return null;
		}


		// ���O�C�����������A���b�N��ԂłȂ��ꍇ�A���O�C���̎��s��񂪂���ꍇ�̓��Z�b�g����B
		changeToUnlock(loginUser);

		return loginUser;
	}


	
	/**
	 * ���O�C���ς��̃`�F�b�N���s���B<br/>
	 * Cookie �ɂ�鎩�����O�C���@�\���g�p���Ă���ꍇ�A���̃��\�b�h�̎��s���Ɏ������O�C������������
	 * �ꍇ������B<br/>
	 * ���̃��\�b�h�́A�������O�C���̐�����ɃA�J�E���g�����b�N��Ԃł���΋����I�Ƀ��O�A�E�g����B<br/>
	 * �����A�ꎞ�I�ł��ꎩ�����O�C���𐬗����������Ȃ��ꍇ�́ACookieLoginUser#matchCookieLoginPassword()
	 * ����������b�N��Ԃ̃`�F�b�N���s���K�v������B<br/>
	 * <br/>
	 */
	@Override
	public LoginUser checkLoggedIn(HttpServletRequest request, HttpServletResponse response) {
		LoginUser loginUser = this.targetAuth.checkLoggedIn(request, response);
		
		if (loginUser != null){
			// �������O�C���������ɁA�A�J�E���g�����b�N��Ԃ̏ꍇ�͋����I�Ƀ��O�A�E�g���s���B
			if (this.lockChecker.isLocked((LockSupportLoginUser)loginUser)){
				logout(request, response);
				return null;
			}
		}

		return loginUser;
	}


	
	/**
	 * ���O�C�����s�񐔁A�ŏI���O�C�����s�����X�V����B<br/>
	 * <ul>
	 * <li>���O�C��ID �ɊY�����郆�[�U�[�����݂��Ȃ��ꍇ�͉����X�V�����ɕ��A����B</li>
	 * <li>���Ƀ��O�C�����s�񐔂�����ɒB���Ă���ꍇ�A�����X�V�����ɕ��A����B</li>
	 * <li>�ŏI���O�C�����s���� null �̏ꍇ�A����̃��O�C�����s�Ȃ̂ŁA���O�C�����s�̃C���^�[�o���Ɋ֌W�Ȃ�
	 * ���O�C�����s�񐔂����Z����B</li>
	 * <li>�ŏI���O�C�����s�����烍�O�C�����s�̃C���^�[�o���Ŏw�肵�����Ԃ��o�߂��Ă��邩�`�F�b�N����B
	 * �����o�߂��Ă���ꍇ�A���O�C�����s�񐔂� 1 �Ƀ��Z�b�g����B�@����ȊO�̏ꍇ�͉��Z����B</li>
	 * <li>���O�C�����s�̃C���^�[�o�� �� 0 �̏ꍇ�̓��Z�b�g�@�\�������Ȃ̂ŁA���̏ꍇ�����s�񐔂����Z����B</li>
	 * </ul>
	 * <br/>
	 * @param form ���̓t�H�[��
	 * 
	 */
	protected void failCountUp(LoginForm form){

		// ���O�C��ID ���L�[�Ƃ��āA���[�U�[�����擾����B
		LockSupportLoginUser loginUser = getUserInfo(form.getLoginID());

		// �Y�����[�U�[�������ꍇ�A���b�N�����͍s��Ȃ��̂ŉ����X�V���Ȃ��B
		if (loginUser == null){
    		log.info("login id is not found.");
			return;
		}


		// �ȉ��A�Y�����[�U�[�����������ꍇ

       	// ���ׂ�A�G���[�񐔂̃I�[�o�[�t���[�̖�肪����̂ŁA���Ƀ��b�N�񐔂�臒l�𒴂��Ă���ꍇ
		// �͉����X�V���Ȃ��B�@�i�{���́A�ŏI���O�C�����s���͍X�V������������...�j
		if (isLocked(loginUser)) return;


   		// UPDATE �p�p�����[�^�I�u�W�F�N�g
   		UpdateExpression[] expression;

		// �J�E���g�A�b�v�̔���t���O
		boolean isCountUp = true;

		// �V�X�e�����t
		Date sysDate = new Date();

		
		// �ŏI���O�C�����s���� null �i���߂ă��O�C�������s�����j�̏ꍇ�A���O�C�����s�̃C���^�[�o���ɂ��
		// ���Z�b�g�̑ΏۊO�ɂȂ�̂ŁA�������ɃJ�E���g�A�b�v�ΏۂɂȂ�B
   		if (loginUser.getLastFailDate() != null) {

   	       	// �ŏI���O�C�����s���{���O�C�����s�̃C���^�[�o�����V�X�e�����t��菬���������`�F�b�N����B
   	       	// �������ꍇ�A���O�C�����s�񐔂͉��Z�����ɂP�Ƀ��Z�b�g����B
   	       	long sysDateTime = sysDate.getTime();

   	       	Calendar calendar = Calendar.getInstance();
   	       	calendar.setTime(loginUser.getLastFailDate());
   	       	calendar.add(Calendar.MINUTE, this.lockChecker.getFailInterval());
   	       	long failDateTime = calendar.getTimeInMillis();
   	   		if (failDateTime <= sysDateTime && this.lockChecker.getFailInterval() != 0){
   	   			isCountUp = false;
   	   		}
   		}

   		
   		if (isCountUp){
   			log.info("failed count is count�@up.");

   			// ���O�C�����s�񐔂��J�E���g�A�b�v����B
   			String failCntStr = "###" + this.userDAOFailCntField + "### = ###"	+ this.userDAOFailCntField + "### + 1";
   			expression = new UpdateExpression[] {new FormulaUpdateExpression(failCntStr),
   												 new UpdateValue(this.userDAOLastFailDateField, sysDate)};
   		} else {
   			log.info("failed count is reset.");

       		// ���O�C�����s�̃C���^�[�o�����o�߂��Ă���̂ŁA���O�C�����s�񐔂� 1 �Ƃ��čX�V����B
   			expression = new UpdateExpression[] {new UpdateValue(this.userDAOFailCntField, 1),
   	        							   		 new UpdateValue(this.userDAOLastFailDateField, sysDate)};
   		}

   		// ���s�񐔂��X�V
   		updateFailStatus(loginUser, expression);

	}



	/**
	 * �w�肳�ꂽ�A�J�E���g�����b�N�����`�F�b�N����B<br/>
	 * <br/>
	 * @param loginUser �`�F�b�N�Ώۃ��[�U�[���
	 * @return ���b�N���̏ꍇ�Atrue �𕜋A����B
	 */
	@Override
	public boolean isLocked(LockSupportLoginUser loginUser){
		return this.lockChecker.isLocked(loginUser);
	}



	/**
	 * ���b�N��ԂɕύX����<br/>
	 * <br/>
	 * ���O�C�����s�񐔂ɏ���l��ݒ肵�A�ŏI���O�C�����s���ɃV�X�e�����t��ݒ肷��B<br/>
	 * <br/>
	 * @param loginUser �X�e�[�^�X�X�V�Ώۃ��[�U�[���
	 */
	@Override
	public void changeToLock(LockSupportLoginUser loginUser){

		// �X�e�[�^�X�������I�Ƀ��b�N��ԂɕύX����B
		UpdateExpression[] expression
			= new UpdateExpression[]
					{new UpdateValue(this.userDAOFailCntField, this.lockChecker.getMaxFailCount()),
					 new UpdateValue(this.userDAOLastFailDateField, new Date())};

  		updateFailStatus(loginUser, expression);

   		log.info("change status to locked. ");

	}
	
	
	/**
	 * �ʏ��ԂɕύX����<br/>
	 * ���O�C�����s�񐔂� 0�A�ŏI���O�C�����s���� null �ɍX�V����B
	 * <br/>
	 * @param loginUser �X�e�[�^�X�X�V�Ώۃ��[�U�[���
	 */
	@Override
	public void changeToUnlock(LockSupportLoginUser loginUser) {

		// ���O�C�����s�񐔂ɁA0 �ȊO�̒l���ݒ肳��Ă��邩�A�ŏI���O�C�����s���ɒl���ݒ肳��Ă���ꍇ�A
		// ���O�C�����s�������Z�b�g����B
		if ((loginUser.getFailCnt() != null && loginUser.getFailCnt() > 0) ||
				(loginUser.getLastFailDate() != null)){

			UpdateExpression[] expression
				= new UpdateExpression[] {new UpdateValue(this.userDAOFailCntField, 0),
										  new UpdateValue(this.userDAOLastFailDateField, null)};

	  		updateFailStatus(loginUser, expression);

       		log.info("Lock status is reset.");

		}
		
	}
	
	
	
	/**
	 * �w�肳�ꂽ���O�C��ID �ɊY�����郍�O�C�����[�U�[�����擾����B<br/>
	 * �Y�����郆�[�U�[��񂪎擾�ł����ꍇ�A���̃I�u�W�F�N�g�𕜋A����B<br/>
	 * <br/>
	 * @param LoginId �擾�ΏۂƂȂ郍�O�C��ID
	 * @return�@�擾�������O�C�����
	 */
	protected LockSupportLoginUser getUserInfo(String LoginId){

		// ���͂��ꂽ���O�C�� ID �ŊY���f�[�^���擾����B
		// ���O�C��ID �̃t�B�[���h���́A�Ϗ���̃N���X����擾����B
		DAOCriteria criteria = new DAOCriteria();
        criteria.addWhereClause(this.targetAuth.getUserDAOLoginIdField(), LoginId);

        List<LockSupportLoginUser> matchingUsers = this.userDAO.selectByFilter(criteria);


        if (matchingUsers.size() == 1){
        	// ���͂������O�C��ID �ɊY������f�[�^���P�����݂���ꍇ

    		// �A�J�E���g���b�N�p�C���^�[�t�F�[�X����������Ă��邩���`�F�b�N����B
    		// �����C���^�[�t�F�[�X����������Ă��Ȃ��ꍇ�A��O���X���[����B
    		if (!(matchingUsers.get(0) instanceof LockSupportLoginUser)){
    			throw new RuntimeException("UserLock Interface not used.");
    		}

        	// �擾�������[�U�[���𕜋A
        	return  matchingUsers.get(0);

        } else if (matchingUsers.size() > 1) {
            // �ʏ�A���O�C��ID �ɂ͈�Ӑ��񂪒����Ă���̂ŁA���̃P�[�X�ɗ���鎖�͂Ȃ��B
        	// �������ꂽ�ꍇ�͗�O���X���[����B
        	throw new RuntimeException("duplicated login id.");
        	
        } else {
        	// �Y���f�[�^�������ꍇ�� null �𕜋A
        	return null;
        }
	}
	
	

	/**
	 * �w�肳�ꂽ���[�U�[�����A�w�肳�ꂽ UpdateExpression �̒l�ōX�V����B<br/>
	 * <br/>
	 * @param loginUser ���O�C�����[�U�[���
	 * @param expression �X�V����l
	 */
	protected void updateFailStatus(LockSupportLoginUser loginUser, UpdateExpression[] expression) {

		// �擾�������[�U�[���̃��[�U�[ID �i��L�[�j�ōX�V����B
		// ��L�[�̃t�B�[���h���́A�Ϗ���̃N���X����擾����B
		DAOCriteria criteria = new DAOCriteria();
        criteria.addWhereClause(this.targetAuth.getUserDAOUserIdField(), loginUser.getUserId());

		// �X�V
		this.userDAO.updateByCriteria(criteria, expression);

	}
	
	
	
	// ����ȍ~�̏����́A�������Ϗ�����̂݁B
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		this.targetAuth.logout(request, response);
	}

	@Override
	public LoginUser getLoggedInUser(HttpServletRequest request, HttpServletResponse response) {
		return this.targetAuth.getLoggedInUser(request, response);
	}

	@Override
	public UserRoleSet getLoggedInUserRole(HttpServletRequest request, HttpServletResponse response) {
		return this.targetAuth.getLoggedInUserRole(request, response);
	}

	
	
	/**
	 * �Ϗ���ƂȂ�F�؃N���X��ݒ肷��B<br/>
	 * ���� Adaptor �N���X�́ADefaultAuth �̔h���n�N���X��ΏۂƂ��Ă���̂ŁA����ȊO�̃N���X�ł�
	 * �g�p�ł��Ȃ��̂Œ��ӂ��鎖�B<br/>
	 * <br/>
	 * @param targetAuth �Ϗ���ƂȂ�F�؃N���X�iDefaultAuth �̔h���N���X�j
	 */
	public void setTargetAuth(DefaultAuth targetAuth) {
		this.targetAuth = targetAuth;
	}

	/**
	 * �F�ؗp DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param userDAO �F�ؗp DAO
	 */
	public void setUserDAO(DAO<LockSupportLoginUser> userDAO) {
		this.userDAO = userDAO;
	}

	/**
	 * �F�ؗp�e�[�u���̃��O�C�����s�񐔃t�B�[���h����ݒ肷��B<br/>
	 * <br/>
	 * @param userDAOFailCntField ���O�C�����s�񐔃t�B�[���h��
	 */
	public void setUserDAOFailCntField(String userDAOFailCntField) {
		this.userDAOFailCntField = userDAOFailCntField;
	}

	/**
	 * �F�ؗp�e�[�u���̍ŏI���O�C�����s���t�B�[���h����ݒ肷��B<br/>
	 * <br/>
	 * @param userDAOFailCntField �ŏI���O�C�����s����
	 */
	public void setUserDAOLastFailDateField(String userDAOLastFailDateField) {
		this.userDAOLastFailDateField = userDAOLastFailDateField;
	}

	/**
	 * �A�J�E���g�̃��b�N�`�F�b�J�[��ݒ肷��B<br/>
	 * <br/>
	 * @param lockChecker �A�J�E���g�̃��b�N�`�F�b�N����
	 */
	public void setLockChecker(LockSupportChecker lockChecker) {
		this.lockChecker = lockChecker;
	}

}
