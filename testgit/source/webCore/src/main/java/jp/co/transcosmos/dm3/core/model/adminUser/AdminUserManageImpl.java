package jp.co.transcosmos.dm3.core.model.adminUser;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DuplicateKeyException;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.AdminUserManage;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserForm;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserSearchForm;
import jp.co.transcosmos.dm3.core.model.adminUser.form.PwdChangeForm;
import jp.co.transcosmos.dm3.core.model.exception.DuplicateException;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.vo.AdminRoleInfo;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.dao.NotEnoughRowsException;
import jp.co.transcosmos.dm3.dao.UpdateExpression;
import jp.co.transcosmos.dm3.login.v3.LockSupportAuth;
import jp.co.transcosmos.dm3.transaction.RequestScopeDataSource;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

/**
 * �Ǘ����[�U�[�����e�i���X�p Model �N���X.
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.03	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B<br/>
 * 
 */
public class AdminUserManageImpl implements AdminUserManage {

	private static final Log log = LogFactory.getLog(AdminUserManageImpl.class);

	/** VO �̃C���X�^���X�𐶐�����ꍇ�̃t�@�N�g���[ */
	protected ValueObjectFactory valueObjectFactory;
	
	/** �Ǘ����[�U�[�����p DAO */
	protected DAO<JoinResult> adminUserListDAO;

	/** �Ǘ����[�U�[���X�V�p DAO */
	protected DAO<AdminUserInterface> adminLoginInfoDAO;
	
	/** �Ǘ����[�U�[���[�����X�V�p DAO */
	protected DAO<AdminUserRoleInterface> adminRoleInfoDAO;
	
	/** ���ʃp�����[�^�[�I�u�W�F�N�g */
	protected CommonParameters commonParameters;

	/** �F�؏����N���X */
	protected LockSupportAuth authentication;

	/**
	 * RequestScopeDataSource �� Bean ID ��<br/>
	 * �蓮�Ńg�����U�N�V�����𐧌䂷��ꍇ�A���̃v���p�e�B�ɐݒ肳��Ă��� Bean ID �ŏ�������B<br/>
	 * �ʏ�́ACommonsParameter �ɐݒ肳��Ă���l���g�p���邪�A���̃v���p�e�B�ɒl���ݒ�
	 * ����Ă���ꍇ�A������̒l��D�悷��B<br/>
	 */
	protected String requestScopeDataSourceId = null;



	/**
	 * �o���[�I�u�W�F�N�g�̃C���X�^���X�𐶐�����t�@�N�g���[��ݒ肷��B<br/>
	 * <br/>
	 * @param valueObjectFactory �o���[�I�u�W�F�N�g�̃t�@�N�g���[
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}

	/**
	 * �Ǘ����[�U�[�����p DAO �̐ݒ�<br>
	 * <br>
	 * @param adminUserListDAO �Ǘ����[�U�[�����p DAO
	 */
	public void setAdminUserListDAO(DAO<JoinResult> adminUserListDAO) {
		this.adminUserListDAO = adminUserListDAO;
	}

	/**
	 * �Ǘ����[�U�[���X�V�p DAO �̐ݒ�<br/>
	 * <br/>
	 * @param adminLoginInfoDAO �Ǘ����[�U�[�X�V�p DAO
	 */
	public void setAdminLoginInfoDAO(DAO<AdminUserInterface> adminLoginInfoDAO) {
		this.adminLoginInfoDAO = adminLoginInfoDAO;
	}

	/**
	 * �Ǘ����[�U�[���[�����X�V�p DAO �̐ݒ�<br/>
	 * ���[���������[�U�[���ŊǗ�����ꍇ�A���� DAO �͐ݒ肵�Ȃ��B
	 * <br/>
	 * @param adminRoleInfoDAO �Ǘ����[�U�[�X�V�p DAO
	 */
	public void setAdminRoleInfoDAO(DAO<AdminUserRoleInterface> adminRoleInfoDAO) {
		this.adminRoleInfoDAO = adminRoleInfoDAO;
	}

	/**
	 * ���ʃp�����[�^�[�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param commonParameters ���ʃp�����[�^�[�I�u�W�F�N�g
	 */
	public void setCommonParameters(CommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * �F�؏����N���X��ݒ肷��B<br/>
	 * <br/>
	 * @param authentication ���b�N�������T�|�[�g���Ă���F�؏����N���X
	 */
	public void setAuthentication(LockSupportAuth authentication) {
		this.authentication = authentication;
	}

	/**
	 * RequestScopeDataSource �� Bean ID ����ݒ肷��B<br/>
	 * �ʏ�AcommonParameters �Őݒ肳��Ă���l���g�p����̂ŁA���̃v���p�e�B���g�p���鎖�͂Ȃ��B<br/>
	 * <br/>
	 * @param requestScopeDataSourceId Bean ID
	 */
	public void setRequestScopeDataSourceId(String requestScopeDataSourceId) {
		this.requestScopeDataSourceId = requestScopeDataSourceId;
	}

	
	
	/**
	 * �Ǘ����[�U�[�̒ǉ����s��<br/>
	 * �����A���[�U�[���ƃ��[�����𓯈�e�[�u���ŊǗ�����ꍇ�AadminRoleInfoDAO �v���p�e�B�ɂ�
	 * null ��ݒ肷�鎖�B<br/>
	 * �Ǘ����[�U�[ID �͎����̔Ԃ����̂ŁAAdminUserForm �� userId �v���p�e�B�ɂ͒l��ݒ肵�Ȃ����B<br/>
	 * ���ݒ肵�Ă����Ȃ����A�o�^�����ł͎g�p����Ȃ��B<br/>
	 * <br/>
	 * @param inputForm �Ǘ����[�U�[�̓��͒l���i�[���� Form �I�u�W�F�N�g
 	 * @param editUserId ���O�C�����[�U�[�h�c�i�X�V���p�j
	 * @return �̔Ԃ��ꂽ�Ǘ��҃��[�U�[ID
	 * 
	 * @throws DuplicateException �o�^���郍�O�C��ID ���d�������ꍇ
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	@Override
	public String addAdminUser(AdminUserForm inputForm, String editUserId)
			throws DuplicateException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		
    	// �V�K�o�^�����̏ꍇ�A���̓t�H�[���̒l��ݒ肷��o���[�I�u�W�F�N�g�𐶐�����B
		// �o���[�I�u�W�F�N�g�́A�t�@�N�g���[���\�b�h�ȊO�ł͐������Ȃ����B
		// �i�p�����ꂽ�o���[�I�u�W�F�N�g���g�p����Ȃ��Ȃ�ׁB�j
    	AdminUserInterface adminUser = buildAdminUserInfo();

    	// �t�H�[���̓��͒l���o���[�I�u�W�F�N�g�ɐݒ肷��B
    	inputForm.copyToAdminUserInfo(adminUser);

    	// �^�C���X�^���v����ݒ肷��B
    	Date sysDate = new Date();
		adminUser.setInsDate(sysDate);
		adminUser.setInsUserId(editUserId);
    	adminUser.setUpdDate(sysDate);
		adminUser.setUpdUserId(editUserId);


		// �Ǘ����[�U�[����o�^
		try {
			this.adminLoginInfoDAO.insert(new AdminUserInterface[]{adminUser});

		} catch (DuplicateKeyException e){
			// �Ǘ����[�U�[�̓o�^�ł́A���O�C��ID �̓��͂ɂ���ăL�[�d���G���[����������
			// �ꍇ������B�@���̏ꍇ�A���[���o�b�N���ė�O���X���[����B
			// �{���́A���O�C��ID �ȊO�̈�Ӑ���G���[�͗�O�Ƃ��Ĉ����ׂ������A������@��
			// �c�a�x���_�[�Ɉˑ�����̂ŁA�����܂ōׂ������肵�Ȃ��B
			manualRollback();
			throw new DuplicateException();
		}


		// ���[�����p�� DAO �����݂��Ȃ��ꍇ�i���[�U�[�Ǘ����Ń��[��ID ���Ǘ����Ă���ꍇ�j�A
		// ���[���Ǘ��̃e�[�u���͑��݂��Ȃ��̂Ń��R�[�h�̒ǉ��͍s��Ȃ��B
		if (this.adminRoleInfoDAO != null){
	    	AdminUserRoleInterface adminRoles = buildAdminRoleInfo();
			inputForm.copyToAdminRoleInfo(adminRoles);
			// �V�K�o�^���́AForm ���� userId ���ݒ肳��Ă��Ȃ��̂ō̔Ԃ����l�����g�Őݒ肷��K�v������B
			adminRoles.setUserId((String)adminUser.getUserId());
			this.adminRoleInfoDAO.insert(new AdminUserRoleInterface[]{adminRoles});
		}

		return (String)adminUser.getUserId();
	}

	
	
	/**
	 * �Ǘ����[�U�[�̍X�V���s��<br/>
	 * �����A���[�U�[���ƃ��[�����𓯈�e�[�u���ŊǗ�����ꍇ�AadminRoleInfoDAO �v���p�e�B�ɂ�
	 * null ��ݒ肷�鎖�B<br/>
	 * ��L�[�ƂȂ�X�V�����́AAdminUserForm �� buildPkCriteria() �����A���錟���������g
	 * �p����B
	 * <br/>
	 * @param inputForm �Ǘ����[�U�[�̓��͒l���i�[���� Form �I�u�W�F�N�g
 	 * @param editUserId ���O�C�����[�U�[�h�c�i�X�V���p�j
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	@Override
	public void updateAdminUser(AdminUserForm inputForm, String editUserId)
			throws DuplicateException, NotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException{

    	// �X�V�����̏ꍇ�A�X�V�Ώۃf�[�^���擾����B
        DAOCriteria criteria = inputForm.buildPkCriteria();

        // �Ǘ����[�U�[�����擾
        List<JoinResult> userInfo = this.adminUserListDAO.selectByFilter(criteria);

        // �Y������f�[�^�����݂��Ȃ��ꍇ�� UPDATE �����s�ł��Ȃ��̂ŁA��O���X���[����B
        // �i�Ⴆ�΁A�Ǘ����[�U�[�̌�����ɕʂ̃Z�b�V��������擾�Ώۃ��[�U�[���폜�����ꍇ�Ȃǁj
		if (userInfo == null || userInfo.size() == 0 ) {
        	throw new NotFoundException();
		}


        // �Ǘ����[�U�[�����擾���A���͂����l�ŏ㏑������B
    	// �� �p�X���[�h�̏ꍇ�A���͂��ꂽ�ꍇ�̂ݏ㏑�������B
    	AdminUserInterface adminUser
    		= (AdminUserInterface) userInfo.get(0).getItems().get(this.commonParameters.getAdminUserDbAlias()); 
    	inputForm.copyToAdminUserInfo(adminUser);
    	AdminUserRoleInterface adminRoles = buildAdminRoleInfo();
    	inputForm.copyToAdminRoleInfo(adminRoles);

    	// �^�C���X�^���v����ݒ肷��B
		adminUser.setUpdDate(new Date());
		adminUser.setUpdUserId(editUserId);


    	try {
    		// �Ǘ����[�U�[�����X�V
        	// �����_�ł͌��i�ȋ����R���g���[���͍s���Ă��Ȃ��B�@�i�V�X�e���Ƃ��Ă̐��������j
        	// �����A�y�ϓI���b�N�ɂ�鋣���Ǘ�����������\��������B
    		this.adminLoginInfoDAO.update(new AdminUserInterface[]{adminUser});

    	} catch (DuplicateKeyException e) {
			// �Ǘ����[�U�[�̓o�^�ł́A���O�C��ID �̓��͂ɂ���ăL�[�d���G���[����������
			// �ꍇ������B�@���̏ꍇ�A���[���o�b�N���ė�O���X���[����B
			// �{���́A���O�C��ID �ȊO�̈�Ӑ���G���[�͗�O�Ƃ��Ĉ����ׂ������A������@��
			// �c�a�x���_�[�Ɉˑ�����̂ŁA�����܂ōׂ������肵�Ȃ��B
			manualRollback();
			throw new DuplicateException();
    	}


		// ���[�����p�� DAO �����݂��Ȃ��ꍇ�i���[�U�[�Ǘ����Ń��[��ID ���Ǘ����Ă���ꍇ�j�A
		// ���[���Ǘ��̃e�[�u���͑��݂��Ȃ��̂Ń��R�[�h�̒ǉ��͍s��Ȃ��B
		if (this.adminRoleInfoDAO != null){
			// ���[�����́A���������鎖��z�肵�ADelete And Insert �ōX�V����B
			// �Ǘ��҃��O�C��ID ���L�[�Ƃ��č폜������ɁA���[������ǉ�����B
			this.adminRoleInfoDAO.deleteByFilter(inputForm.buildPkCriteria());
			this.adminRoleInfoDAO.insert(new AdminUserRoleInterface[]{adminRoles});
		}

	}

	
	
	/**
	 * �Ǘ����[�U�[�̍폜���s��<br/>
	 * ��L�[�ƂȂ�폜�����́AinputForm �� buildPkCriteria() �����A���錟������
	 * ���g�p����B<br/>
	 * �܂��A�폜�Ώۃ��R�[�h�����݂��Ȃ��ꍇ�ł�����I���Ƃ��Ĉ������B<br/>
	 * <br/>
	 * @param inputForm �폜�ΏۂƂȂ�Ǘ����[�U�[�̎�L�[�l���i�[���� Form �I�u�W�F�N�g
	 */
	@Override
	public void delAdminUser(AdminUserForm inputForm){

		// �Ǘ����[�U�[�̎�L�[�l���폜�����Ƃ����I�u�W�F�N�g�𐶐�
		DAOCriteria criteria = inputForm.buildPkCriteria();

		// �Ǘ����[�U�[���[�������폜����B
		this.adminRoleInfoDAO.deleteByFilter(criteria);

		// �Ǘ����[�U�[�����폜����B
		this.adminLoginInfoDAO.deleteByFilter(criteria);

	}



	/**
	 * �A�J�E���g���b�N�X�e�[�^�X��ύX����B<br/>
	 * <br/>
	 * @param inputForm �ύX�ΏۂƂȂ�Ǘ����[�U�[�̎�L�[�l���i�[���� Form �I�u�W�F�N�g
	 * @param locked false = �ʏ탂�[�h�ɐݒ�Atrue = ���b�N���[�h�ɐݒ�
 	 * @param editUserId ���O�C�����[�U�[�h�c�i�X�V���p�j
	 * @exception NotFoundException �X�V�Ώۂ����݂��Ȃ��ꍇ
	 */
	@Override
	public void changeLockStatus(AdminUserForm inputForm, boolean locked, String editUserId)
			throws NotFoundException {

    	// ���b�N�ΏۂƂȂ�Ǘ����[�U�[�����擾����B
        DAOCriteria criteria = inputForm.buildPkCriteria();
        List<AdminUserInterface> userInfo = this.adminLoginInfoDAO.selectByFilter(criteria);

        // �Y������f�[�^�����݂��Ȃ��ꍇ�͍X�V�������ł��Ȃ��̂ŁA��O���X���[����B
        // �i�Ⴆ�΁A�Ǘ����[�U�[�̌�����ɕʂ̃Z�b�V��������擾�Ώۃ��[�U�[���폜�����ꍇ�Ȃǁj
		if (userInfo == null || userInfo.size() == 0 ) {
        	throw new NotFoundException();
		}

		if (locked){
			// ���b�N���[�h�ɐݒ�
			this.authentication.changeToLock(userInfo.get(0));
		} else {
			// �ʏ탂�[�h�ɐݒ�
			this.authentication.changeToUnlock(userInfo.get(0));
		}

		// �^�C���X�^���v���X�V
		UpdateExpression[] expression = inputForm.buildTimestampUpdateExpression(editUserId);
        this.adminLoginInfoDAO.updateByCriteria(criteria, expression);

	}


	
	/**
	 * ���b�N��Ԃ̔���<br/>
	 * �t���[�����[�N�̃��b�N�X�e�[�^�X�`�F�b�N�֏������Ϗ�����B<br/>
	 * <br/>
	 * @param targetUser �`�F�b�N�Ώۃ��[�U�[���
	 * @return true = ���b�N���Afalse = ����
	 */
	@Override
	public boolean isLocked(AdminUserInterface targetUser) {
		return this.authentication.isLocked(targetUser);
	}


	
	/**
	 * ���O�C��ID �����g�p�����`�F�b�N����B<br/>
	 * �t�H�[���ɐݒ肳��Ă��郍�O�C��ID �����g�p�����`�F�b�N����B<br/>
	 * �����A���[�U�[�h�c ���ݒ肳��Ă���ꍇ�A���̃��[�U�[ID �����O���ă`�F�b�N����B<br/>
	 * <br/>
	 * @param inputForm �`�F�b�N�ΏۂƂȂ���͒l
	 *  
	 * @return true = ���p�Afalse = ���p�s��
	 * 
	 */
	@Override
	public boolean isFreeLoginId(AdminUserForm inputForm) {

		// Form ����`�F�b�N�ɕK�v�Ȍ��������𐶐����Č�������B
		DAOCriteria criteria = inputForm.buildFreeLoginIdCriteria();
		List<AdminUserInterface> list = this.adminLoginInfoDAO.selectByFilter(criteria);

		if (list.size() == 0) {
			return true;
			
		} else {
			return false;
		}
	}

	
	
	/**
	 * �Ǘ����[�U�[���������A���ʃ��X�g�𕜋A����B<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A�Ǘ����[�U�[����������B<br/>
	 * �������ʂ� Form �I�u�W�F�N�g�Ɋi�[����A�擾�����Y��������߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 * @param searchForm ���������A����сA�������ʂ̊i�[�I�u�W�F�N�g
	 * @return �Y������
	 */
	@Override
	public int searchAdminUser(AdminUserSearchForm searchForm){
		
        // �Ǘ����[�U�[��������������𐶐�����B
        DAOCriteria criteria = searchForm.buildCriteria();

        // �Ǘ����[�U�[�̌���
        List<JoinResult> userList;
        try {
        	userList = this.adminUserListDAO.selectByFilter(criteria);

        } catch(NotEnoughRowsException err) {
 
			int pageNo = (err.getMaxRowCount() - 1) / searchForm.getRowsPerPage() + 1;
			log.warn("resetting page to " + pageNo);
			searchForm.setSelectedPage(pageNo);
			criteria = searchForm.buildCriteria();
			userList = this.adminUserListDAO.selectByFilter(criteria);
        }

        searchForm.setRows(userList);

        return userList.size();

	}



	/**
	 * ���N�G�X�g�p�����[�^�œn���ꂽ���[�U�[ID �i��L�[�l�j�ɊY������Ǘ����[�U�[���𕜋A����B<br/>
	 * �����Y���f�[�^��������Ȃ��ꍇ�Anull �𕜋A����B<br/>
	 * <br/>
	 * @param searchForm�@�������ʂƂȂ� JoinResult
	 * @return �擾�����Ǘ����[�U�[���
	 */
	@Override
	public JoinResult searchAdminUserPk(AdminUserSearchForm searchForm) {

        // �Ǘ����[�U�[�����擾����ׂ̎�L�[��ΏۂƂ������������𐶐�����B
        DAOCriteria criteria = searchForm.buildPkCriteria();

        // �Ǘ����[�U�[�����擾
        List<JoinResult> userInfo = this.adminUserListDAO.selectByFilter(criteria);

		if (userInfo == null || userInfo.size() == 0 ) {
			return null;
		}
		
		return userInfo.get(0);
	}



	/**
	 * �p�X���[�h�̕ύX�������s���B<br/>
	 * ��L�[�ƂȂ�X�V�����́AAdminUserForm �� buildPkCriteria() �����A���錟������
	 * ���g�p����B<br/>
	 * <br/>
	 * @param inputForm �p�X���[�h�ύX�̓��͏��
	 * @param updUserId �X�V�Ώۃ��[�U�[ID
 	 * @param editUserId ���O�C�����[�U�[�h�c�i�X�V���p�j
	 * @exception NotFoundException �X�V�Ώۂ����݂��Ȃ��ꍇ
	 */
	@Override
	public void changePassword(PwdChangeForm inputForm, String updUserId, String editUserId)
			throws NotFoundException {

        // �Ǘ����[�U�[�����擾����ׂ̎�L�[��ΏۂƂ������������𐶐�����B
        DAOCriteria criteria = inputForm.buildPkCriteria(updUserId);

        // �p�X���[�h�X�V�p�� UpdateExpression �𐶐�����B
        UpdateExpression[] expression = inputForm.buildPwdUpdateExpression(editUserId);

        if (this.adminLoginInfoDAO.updateByCriteria(criteria, expression) == 0){
        	// �ʏ�A�X�V�Ώۖ����̏ꍇ�͖������Ă��邪�A�p�X���[�h�ύX�̏ꍇ�A�Z�L�����e�B�I��
        	// �e��������̂ōX�V�ł��Ă��鎖���m�F����B
        	throw new NotFoundException();
        }
	}



	/**
	 * �Ǘ����[�U�[�p�̃o���[�I�u�W�F�N�g���쐬����t�@�N�g���[���\�b�h<br/>
	 * <br/>
	 * @return AdminUserInterface �����������Ǘ����[�U�[�I�u�W�F�N�g
	 */
	protected AdminUserInterface buildAdminUserInfo() {

		// �d�v
		// �����Ǘ����[�U�[�e�[�u���� AdminLoginInfo �ȊO�̃I�u�W�F�N�g�ɕύX�����ꍇ�A
		// ���̃��\�b�h��K�؂ȃo���[�I�u�W�F�N�g�𐶐�����l�ɃI�[�o�[���C�h���鎖�B

		return (AdminUserInterface) this.valueObjectFactory.getValueObject("AdminLoginInfo"); 
	}



	/**
	 * �Ǘ����[�U�[�̃��[�����̃o���[�I�u�W�F�N�g���쐬����t�@�N�g���[���\�b�h<br/>
	 * �o���[�I�u�W�F�N�g���g�������ꍇ�͂��̃��\�b�h���I�[�o�[���C�h���鎖�B<br/>
	 * <br/>
	 * @return ���[�����o���[�I�u�W�F�N�g
	 * 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	protected AdminUserRoleInterface buildAdminRoleInfo()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		
		// �d�v
		// �����Ǘ����[�U�[���[���e�[�u���� AdminRoleInfo �ȊO�̃I�u�W�F�N�g�ɕύX�����ꍇ�A
		// ���̃��\�b�h��K�؂ȃo���[�I�u�W�F�N�g�𐶐�����l�ɃI�[�o�[���C�h���鎖�B

		// ���ɁA���[��ID ���Ǘ����[�U�[���ŊǗ�����ꍇ�A���[�����e�[�u�������݂��Ȃ�
		// �̂ŁAnull �𕜋A����l�ɃI�[�o�[���C�h����K�v������B

		return (AdminRoleInfo) this.valueObjectFactory.getValueObject("AdminRoleInfo");
	}

	
	
	/**
	 * �蓮�Ń��[���o�b�N�������s���B<br/>
	 * <br/>
	 */
	protected void manualRollback(){

		// ���ʃp�����[�^�I�u�W�F�N�g����l���擾����B
		String beanId = this.commonParameters.getRequestScopeDataSourceId();
		// �����A���̃N���X�̃v���p�e�B�ɒl���ݒ肳��Ă����ꍇ�A������̒l��D�悷��B
		if (!StringValidateUtil.isEmpty(this.requestScopeDataSourceId)){
			beanId = this.requestScopeDataSourceId;
		}

		// ���[���o�b�N����
		RequestScopeDataSource.closeCurrentTransaction(beanId, true);
	}

}
