package jp.co.transcosmos.dm3.core.model.mypage;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DuplicateKeyException;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.model.exception.DuplicateException;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserForm;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserFormFactory;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserSearchForm;
import jp.co.transcosmos.dm3.core.model.mypage.form.PwdChangeForm;
import jp.co.transcosmos.dm3.core.model.mypage.form.RemindForm;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.PasswordRemind;
import jp.co.transcosmos.dm3.core.vo.UserInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.dao.NotEnoughRowsException;
import jp.co.transcosmos.dm3.dao.UpdateExpression;
import jp.co.transcosmos.dm3.dao.UpdateValue;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.transaction.RequestScopeDataSource;
import jp.co.transcosmos.dm3.utils.DateUtils;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

public class MypageUserManageImpl implements MypageUserManage {

	private static final Log log = LogFactory.getLog(MypageUserManageImpl.class);
	
	/** VO �̃C���X�^���X�𐶐�����ꍇ�̃t�@�N�g���[ */
	protected ValueObjectFactory valueObjectFactory;

	/** �}�C�y�[�W��������p DAO */
	protected DAO<JoinResult> memberListDAO;

	/** ���[�U�[ID ���p DAO*/
	protected DAO<UserInfo> userInfoDAO;

	/** �}�C�y�[�W������p DAO */
	protected DAO<MypageUserInterface> memberInfoDAO;

	/** �p�X���[�h�⍇�����p DAO */
	protected DAO<PasswordRemind> passwordRemindDAO;
	
	/** UUID ���d�������ꍇ�̃��g���C�� */
	protected int maxRetry = 100;

	/** �p�X���[�h�⍇���̗L������ */
	protected int maxEntryDay = 10;

	/** �}�C�y�[�W������p �� Form �t�@�N�g���[ */
	protected MypageUserFormFactory mypageFormFactory;

	/** ���ʃp�����[�^�[�I�u�W�F�N�g */
	protected CommonParameters commonParameters;

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
	 * �}�C�y�[�W�����񌟍��p DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param memberListDAO �}�C�y�[�W�����񌟍��p DAO
	 */
	public void setMemberListDAO(DAO<JoinResult> memberListDAO) {
		this.memberListDAO = memberListDAO;
	}

	/**
	 * ���[�U�[ID ���p DAO<br/>
	 * <br/>
	 * @param userInfoDAO ���[�U�[ID ���p DAO
	 */
	public void setUserInfoDAO(DAO<UserInfo> userInfoDAO) {
		this.userInfoDAO = userInfoDAO;
	}

	/**
	 * �}�C�y�[�W������̌����E�X�V�Ɏg�p���� DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param memberInfoDAO �}�C�y�[�W������̌����E�X�VDAO
	 */
	public void setMemberInfoDAO(DAO<MypageUserInterface> memberInfoDAO) {
		this.memberInfoDAO = memberInfoDAO;
	}

	/**
	 * �p�X���[�h�⍇�����p DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param passwordRemindDAO �p�X���[�h�⍇�����p DAO
	 */
	public void setPasswordRemindDAO(DAO<PasswordRemind> passwordRemindDAO) {
		this.passwordRemindDAO = passwordRemindDAO;
	}

	/**
	 * ���⍇�����̎�L�[�Ƃ��Ďg�p���� UUID ���A�d�������ꍇ�̃��g���C�񐔂�ݒ肷��B<br/>
	 * �����l�� 100 ��Ȃ̂ŁA�ʏ�͕ύX����K�v�͂Ȃ��B<br/>
	 * <br/>
	 * @param maxRetry ���g���C��
	 */
	public void setMaxRetry(int maxRetry) {
		this.maxRetry = maxRetry;
	}

	/**
	 * ���⍇�����̗L��������ݒ肷��B<br/>
	 * �����l�� 10���ŁA�l��ς������ꍇ�ɂ��̃v���p�e�B��ݒ肷��B<br/>
	 * <br/>
	 * @param maxEntryDay �L������
	 */
	public void setMaxEntryDay(int maxEntryDay) {
		this.maxEntryDay = maxEntryDay;
	}

	/**
	 * �}�C�y�[�W������p �� Form �t�@�N�g���[��ݒ肷��B<br/>
	 * <br/>
	 * @param mypageFormFactory �}�C�y�[�W������p �� Form �t�@�N�g���[
	 */
	public void setMypageFormFactory(MypageUserFormFactory mypageFormFactory) {
		this.mypageFormFactory = mypageFormFactory;
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
	 * ���ʃp�����[�^�[�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param commonParameters ���ʃp�����[�^�[�I�u�W�F�N�g
	 */
	public void setCommonParameters(CommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	

	
	/**
	 * ���[�U�[ID ���̔Ԃ��A���[�U�[ID ���֒ǉ�����B<br/>
	 * �t�����g�T�C�g�̏ꍇ�A���̋@�\�͓����F�؂̃t�B���^�[����g�p�����B<br/>
	 * �Ǘ��@�\����}�C�y�[�W�����o�^����ꍇ�AaddMyPageUser() ���g�p����O�ɂ��̃��\�b�h��
	 * ���s���ă��[�U�[ID ���̔Ԃ���K�v������B<br/>
	 * <br/>
	 * @param editUserId �X�V���̃^�C���X�^���v�ƂȂ郆�[�U�[ID�@�i�t�����g���̏ꍇ�Anull�j
	 * 
	 * @return �ǉ����ꂽ���O�C�����[�U�[�I�u�W�F�N�g
	 * 
	 */
	@Override
	public LoginUser addLoginID(String editUserId){

    	// ���[�U�[ID �̔ԗp�̃o���[�I�u�W�F�N�g�̃C���X�^���X�𐶐�����B
		// �o���[�I�u�W�F�N�g�́A�t�@�N�g���[���\�b�h�ȊO�ł͐������Ȃ����B
		// �i�p�����ꂽ�o���[�I�u�W�F�N�g���g�p����Ȃ��Ȃ�ׁB�j
    	UserInfo userInfo = buildUserInfo();

    	// UserInfo ���o�C�p�X����l�ȃJ�X�^�}�C�Y��z�肵�A�����@buildUserInfo()
    	// ���C���X�^���X�𕜋A���Ȃ��ꍇ�̓��[�U�[ID ���̔Ԃ����� null �𕜋A����B<br/>
    	if (userInfo == null) return null;


    	// �o���[�I�u�W�F�N�g�ɒl��ݒ肷��B �i�ݒ肪�K�v�Ȃ͓̂o�^���̂݁B�j
    	userInfo.setInsDate(new Date());

    	//�@���[�U�[ID ���̔Ԃ��A���[�U�[ID �𕜋A
		this.userInfoDAO.insert(new UserInfo[]{userInfo});
		return userInfo;
	}



	/**
	 * �p�����[�^�œn���ꂽ Form �̏��Ǝw�肳�ꂽ���[�U�[ID�Ń}�C�y�[�W���[�U�[��V�K�o�^����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * �t�����g������}�C�y�[�W�o�^����ꍇ�A���O�C��ID �͊��ɍ̔Ԃ���Ă���̂ŁAaddUserId �ɂ͂��̒l���w�肷��B<br/>
	 * �Ǘ��@�\����g�p����ꍇ�́AaddLoginID() ���g�p���ă��O�C��ID ���̔Ԃ��Ă������B<br/>
	 * �܂��A�t�����g������}�C�y�[�W�o�^����ꍇ�AeditUserId �́AaddUserId �Ɠ����l��ݒ肷��B<br/>
	 * <br/>
	 * @param inputForm �}�C�y�[�W���[�U�[�̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param addUserId �}�C�y�[�W�ǉ��ΏۂƂȂ郆�[�U�[ID
	 * @param editUserId �X�V���̃^�C���X�^���v�ƂȂ郆�[�U�[ID
	 * 
	 * @return addUserId �Ɠ����l
	 * 
 	 * @exception DuplicateException ���[���A�h���X�i���O�C��ID�j �̏d��
	 */
	@Override
	public String addMyPageUser(MypageUserForm inputForm, String addUserId, String editUserId)
			throws DuplicateException {

    	// �V�K�o�^�����̏ꍇ�A���̓t�H�[���̒l��ݒ肷��o���[�I�u�W�F�N�g�𐶐�����B
		// �o���[�I�u�W�F�N�g�́A�t�@�N�g���[���\�b�h�ȊO�ł͐������Ȃ����B
		// �i�p�����ꂽ�o���[�I�u�W�F�N�g���g�p����Ȃ��Ȃ�ׁB�j
    	MypageUserInterface memberInfo = buildMemberInfo();


    	// �t�H�[���̓��͒l���o���[�I�u�W�F�N�g�ɐݒ肷��B
    	inputForm.copyToMemberInfo(memberInfo);

    	// ���[�U�[ID ��ݒ�
    	memberInfo.setUserId(addUserId);
    	
    	// �^�C���X�^���v����ݒ肷��B
    	Date sysDate = new Date();
    	memberInfo.setInsDate(sysDate);
    	memberInfo.setInsUserId(editUserId);
    	memberInfo.setUpdDate(sysDate);
    	memberInfo.setUpdUserId(editUserId);

    	
		// �}�C�y�[�W�������o�^
		try {
			this.memberInfoDAO.insert(new MypageUserInterface[]{memberInfo});

		} catch (DuplicateKeyException e){
			// �}�C�y�[�W����̓o�^�ł́A���[���A�h���X�i���O�C��ID�j �̓��͂ɂ���ăL�[�d���G���[����������
			// �ꍇ������B�@���̏ꍇ�A���[���o�b�N���ė�O���X���[����B
			// �{���́A���[���A�h���X�i���O�C��ID�j �ȊO�̈�Ӑ���G���[�͗�O�Ƃ��Ĉ����ׂ������A������@��
			// �c�a�x���_�[�Ɉˑ�����̂ŁA�����܂ōׂ������肵�Ȃ��B
			manualRollback();
			throw new DuplicateException();
		} catch (Exception e){
			e.printStackTrace();
		}

		return addUserId;
	}

	
	
	/**
	 * �}�C�y�[�W����̍X�V���s��<br/>
	 * ��L�[�ƂȂ�X�V�����́AMypageUserForm �� buildPkCriteria() �����A���錟���������g
	 * �p����B
	 * <br/>
	 * @param inputForm �}�C�y�[�W���[�U�[�̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param addUserId �}�C�y�[�W�X�V�ΏۂƂȂ郆�[�U�[ID
	 * @param editUserId �X�V���̃^�C���X�^���v�ƂȂ郆�[�U�[ID
	 * 
	 * @throws DuplicateException 
	 * @throws NotFoundException 
	 */
	@Override
	public void updateMyPageUser(MypageUserForm inputForm, String updUserId, String editUserId)
			throws DuplicateException, NotFoundException {

    	// �X�V�����̏ꍇ�A�X�V�Ώۃf�[�^���擾����B
        DAOCriteria criteria = inputForm.buildPkCriteria(updUserId);

        // �}�C�y�[�W��������擾
        List<JoinResult> userInfo = this.memberListDAO.selectByFilter(criteria);

        // �Y������f�[�^�����݂��Ȃ��ꍇ�� UPDATE �����s�ł��Ȃ��̂ŁA��O���X���[����B
        // �i�Ⴆ�΁A�}�C�y�[�W����̌�����ɕʂ̃Z�b�V��������擾�Ώۃ��[�U�[���폜�����ꍇ�Ȃǁj
		if (userInfo == null || userInfo.size() == 0 ) {
        	throw new NotFoundException();
		}


        // �}�C�y�[�W��������擾���A���͂����l�ŏ㏑������B
    	// �� �p�X���[�h�̏ꍇ�A���͂��ꂽ�ꍇ�̂ݏ㏑�������B
    	MypageUserInterface mypageUser
    		= (MypageUserInterface) userInfo.get(0).getItems().get(this.commonParameters.getMemberDbAlias()); 
    	inputForm.copyToMemberInfo(mypageUser);

    	// �^�C���X�^���v����ݒ肷��B
    	mypageUser.setUpdDate(new Date());
    	mypageUser.setUpdUserId(editUserId);


    	try {
    		// �}�C�y�[�W��������X�V
        	// �����_�ł͌��i�ȋ����R���g���[���͍s���Ă��Ȃ��B�@�i�V�X�e���Ƃ��Ă̐��������j
        	// �����A�y�ϓI���b�N�ɂ�鋣���Ǘ�����������\��������B
    		this.memberInfoDAO.update(new MypageUserInterface[]{mypageUser});

    	} catch (DuplicateKeyException e) {
			// �}�C�y�[�W������̓o�^�ł́A���[���A�h���X�i���O�C��ID�j �̓��͂ɂ���ăL�[�d���G���[����������
			// �ꍇ������B�@���̏ꍇ�A���[���o�b�N���ė�O���X���[����B
			// �{���́A���[���A�h���X�ȊO�̈�Ӑ���G���[�͗�O�Ƃ��Ĉ����ׂ������A������@��
			// �c�a�x���_�[�Ɉˑ�����̂ŁA�����܂ōׂ������肵�Ȃ��B
			manualRollback();
			throw new DuplicateException();
    	}
	}

	
	
	/**
	 * �}�C�y�[�W����̍폜���s��<br/>
	 * ��L�[�ƂȂ�폜�����́AinputForm �� buildPkCriteria() �����A���錟������
	 * ���g�p����B<br/>
	 * �܂��A�폜�Ώۃ��R�[�h�����݂��Ȃ��ꍇ�ł�����I���Ƃ��Ĉ������B<br/>
	 * <br/>
	 * @param userId �폜�ΏۂƂȂ�}�C�y�[�W����̎�L�[�l
	 */
	@Override
	public void delMyPageUser(String userId) {

		// note
		// ���̃��\�b�h�̓t�����g��������g�p�����\��������B
		// ���̏ꍇ�AMypageUserSearchForm�@���g�p������ƁAuserId �����N�G�X�g�p�����[�^
		// �ł���肳���\��������A�Z�L�����e�B�I�Ƀ��X�N����������B
		// �܂��A�}�C�y�[�W������̓J�X�^�}�C�Y�ɂ���Ă̓e�[�u�����̂� MemberInfo �łȂ�
		// �\��������A�������� model ���Ŕc���ł��Ȃ��B
		// ����āAmodel ���� Form �̃C���X�^���X���r���h���ď������Ϗ�����B

		MypageUserSearchForm searchForm = this.mypageFormFactory.createMypageUserSearchForm();
		searchForm.setUserId(userId);

		// �}�C�y�[�W������̎�L�[�l���폜�����Ƃ����I�u�W�F�N�g�𐶐�
		DAOCriteria criteria = searchForm.buildPkCriteria();

		// �}�C�y�[�W����������폜����B
		// ���މ����A�}�C�y�[�W����ȊO�̃��[�U�[�ɒ񋟂��Ă���@�\������̂ŁA���O�C��ID ���̍폜��
		// �s��Ȃ��B
		this.memberInfoDAO.deleteByFilter(criteria);

	}



	/**
	 * ���[���A�h���X�i���O�C��ID�j �����g�p�����`�F�b�N����B<br/>
	 * �t�H�[���ɐݒ肳��Ă��郁�[���A�h���X�i���O�C��ID�j �����g�p�����`�F�b�N����B<br/>
	 * �����A���[�U�[�h�c �������œn���ꂽ�ꍇ�A���̃��[�U�[ID �����O���ă`�F�b�N����B<br/>
	 * <br/>
	 * @param inputForm �`�F�b�N�ΏۂƂȂ���͒l
	 * @param userId �`�F�b�N�ΏۂƂȂ�}�C�y�[�W���[�U�[�̃��[�U�[ID �i�V�K�o�^���� null�j
	 *  
	 * @return true = ���p�Afalse = ���p�s��
	 * 
	 */
	@Override
	public boolean isFreeLoginId(MypageUserForm inputForm, String userId) {

		// Form ����`�F�b�N�ɕK�v�Ȍ��������𐶐����Č�������B
		DAOCriteria criteria = inputForm.buildFreeLoginIdCriteria(userId);
		List<MypageUserInterface> list = this.memberInfoDAO.selectByFilter(criteria);

		if (list.size() == 0) {
			return true;

		} else {
			return false;
		}
	}



	/**
	 * �}�C�y�[�W������������A���ʃ��X�g�𕜋A����B<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A�}�C�y�[�W�������������B<br/>
	 * �������ʂ� Form �I�u�W�F�N�g�Ɋi�[����A�擾�����Y��������߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 * @param searchForm ���������A����сA�������ʂ̊i�[�I�u�W�F�N�g
	 * @return �Y������
	 */
	@Override
	public int searchMyPageUser(MypageUserSearchForm searchForm) {

        // �}�C�y�[�W�����������������𐶐�����B
        DAOCriteria criteria = searchForm.buildCriteria();

        // �Ǘ����[�U�[�̌���
        List<JoinResult> userList;
        try {
        	userList = this.memberListDAO.selectByFilter(criteria);

        } catch(NotEnoughRowsException err) {
 
			int pageNo = (err.getMaxRowCount() - 1) / searchForm.getRowsPerPage() + 1;
			log.warn("resetting page to " + pageNo);
			searchForm.setSelectedPage(pageNo);
			criteria = searchForm.buildCriteria();
			userList = this.memberListDAO.selectByFilter(criteria);
        }

        searchForm.setRows(userList);
        
        return userList.size();

	}



	/**
	 * ���[�U�[ID �i��L�[�l�j�ɊY������}�C�y�[�W������𕜋A����B<br/>
	 * �����Y���f�[�^��������Ȃ��ꍇ�Anull �𕜋A����B<br/>
	 * <br/>
	 * @param userId�@���擾�ΏۂƂȂ�}�C�y�[�W������
	 * @return �擾�����}�C�y�[�W������
	 */
	@Override
	public JoinResult searchMyPageUserPk(String userId) {

		// note
		// ���̃��\�b�h�̓t�����g��������g�p�����\��������B
		// ���̏ꍇ�AMypageUserSearchForm�@���g�p������ƁAuserId �����N�G�X�g�p�����[�^
		// �ł���肳���\��������A�Z�L�����e�B�I�Ƀ��X�N����������B
		// �܂��A�}�C�y�[�W������̓J�X�^�}�C�Y�ɂ���Ă̓e�[�u�����̂� MemberInfo �łȂ�
		// �\��������A�������� model ���Ŕc���ł��Ȃ��B
		// ����āAmodel ���� Form �̃C���X�^���X���r���h���ď������Ϗ�����B

		MypageUserSearchForm searchForm = this.mypageFormFactory.createMypageUserSearchForm();
		searchForm.setUserId(userId);

		// Form �ɏ������Ϗ����A��L�[�̌��������𐶐�����B
		DAOCriteria criteria = searchForm.buildPkCriteria();

        // �}�C�y�[�W��������擾
        List<JoinResult> userInfo = this.memberListDAO.selectByFilter(criteria);

		if (userInfo == null || userInfo.size() == 0 ) {
			return null;
		}

		return userInfo.get(0);
	}



	/**
	 * �p�X���[�h�ύX�̓o�^�������s���B<br/>
	 * �w�肳�ꂽ���[���A�h���X�̑Ó������m�F���A��肪�Ȃ���΃p�X���[�h�⍇������ DB �ɓo�^
	 * ����B�@����ɏ������s�����ꍇ�A�̔Ԃ������₢���킹ID �iUUID�j�𕜋A����B<br/>
	 * ���g�����U�N�V�����̊Ǘ��͌ďo���Ő��䂷�鎖�B<br/>
	 *   �i���ׁ̈A���[�����M�͂��̃��\�b�h���Ŏ������Ȃ��B�j<br/>
	 * <br/>
	 * @param inputForm �p�X���[�h�⍇���̑ΏۂƂȂ���͏��
	 * @param entryUserId �����F�؎��ɕ����o����Ă��郆�[�U�[ID
	 * 
	 * @return ����I���� UUID�A���A�h�����݂��Ȃ��ꍇ�ȂǁA�G���[�������� null
	 * 
 	 * @exception NotFoundException �X�V�ΏۂȂ�
	 *
	*/
	@Override
	public String addPasswordChangeRequest(RemindForm inputForm, String entryUserId)
		throws NotFoundException {

		// ���͂��ꂽ���[���A�h���X�����݂��邩���`�F�b�N����B
		DAOCriteria criteria = inputForm.buildMailCriteria();
		List<MypageUserInterface> member = this.memberInfoDAO.selectByFilter(criteria);

		// �Y���f�[�^�����݂��Ȃ������ꍇ�͗�O���X���[����B
		if (member.size() == 0){
			throw new NotFoundException();
		};

		
		// �p�X���[�h�⍇���̃o���[�I�u�W�F�N�g�֒l��ݒ肷��B
		PasswordRemind passwordRemind = buildPasswordRemind(member.get(0), entryUserId);

		// �߂�l�ƂȂ� UUID �i���⍇��ID�j
		// �c�a�ւ̓o�^������I�������ꍇ�̂ݒl���ݒ肳���B
		String returnId = null;

		// ������AUUID ���d�������ꍇ�A���g���C����̂ŁA臒l�񐔕��o�^���J��Ԃ��B
		for (int i=0; i<this.maxRetry; ++i){

			//�@UUID �̍̔�
			// UUID Ver 1 �͎g�p���Ȃ����B�@�i�T�[�o�[�� IP ���������\��������B�j
			passwordRemind.setRemindId(UUID.randomUUID().toString());

			// �o�^����
			try {
				this.passwordRemindDAO.insert(new PasswordRemind[]{passwordRemind});
				returnId = passwordRemind.getRemindId();
				break;

			} catch (DuplicateKeyException e) {
				// �����A�L�[�d�������������ꍇ�͐V���� UUID �Ń��g���C����B
				// ���m���I�ɂ͔��ɒႢ...�B
				continue;
			}
		}


		if (returnId == null) {
			// ����臒l�ɒB���č̔Ԃł��Ȃ��ꍇ�͗�O���X���[����B
			// UUID �͏d�����鎖���̂��H�Ȃ̂ŁA100 ��J��Ԃ��Ă��d������ꍇ�͕ʂ̖�肪
			// �l������B
			throw new RuntimeException ("UUID retry count is max.");
		}

		// �̔Ԃ������⍇��ID �iUUID�j�𕜋A
		return returnId;
	}



	/**
	 * �p�X���[�h�̕ύX�������s���B<br/>
	 * �p�X���[�h�̓��͊m�F�ȂǁA��ʓI�ȃo���f�[�V�����͌ďo�����Ŏ��{���Ă������B<br/>
	 * ���N�G�X�g�p�����[�^�œn���ꂽ UUID �������؂�̏ꍇ�A�������́A�Y�����R�[�h���p�X���[�h�⍇��
	 * ���ɑ��݂��Ȃ��ꍇ�͗�O���X���[����B<br/>
	 * �p�X���[�h�̍X�V���Ɏg�p�����L�[�̒l�́A�p�X���[�h�⍇����񂩂�擾���鎖�B<br/>
	 * <br/>
	 * @param inputForm �V�����p�X���[�h�̓��͒l���i�[���ꂽ Form
	 * @param entryUserId �����F�؎��ɕ����o����Ă��郆�[�U�[ID
	 * 
 	 * @exception NotFoundException �X�V�ΏۂȂ�
	 */
	@Override
	public void changePassword(PwdChangeForm inputForm, String entryUserId)
			throws NotFoundException {

		// ���͂��ꂽ���⍇��ID ���擾����B
		// �擾�����́A���⍇��ID �����N�G�X�g�p�����[�^�ƈ�v���A�������A�L���������̕��B
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("remindId", inputForm.getRemindId());
		criteria.addWhereClause("commitFlg", "0");

		Date limitDate = DateUtils.addDays(new Date(), -1 * this.maxEntryDay);
		criteria.addWhereClause("insDate", limitDate, DAOCriteria.GREATER_THAN_EQUALS);

		List<PasswordRemind> remind = this.passwordRemindDAO.selectByFilter(criteria);
		
		// �Y���f�[�^���擾�o���Ȃ��ꍇ�A��O���X���[����B
		if (remind.size() == 0){
			throw new NotFoundException();
		}

		// note
		// �����́A�Y���f�[�^�����ƁA�����؂��ʂɈ������Ǝv�������A�Z�L�����e�B�I�ɉa��^����
		// ���ɂȂ�̂ŁA�ǂ���������Ɉ������ɂ����B


		// �}�C�y�[�W������擾����ׂ̎�L�[��ΏۂƂ������������𐶐�����B
		String userId = remind.get(0).getUserId();
        criteria = inputForm.buildPkCriteria(userId);

        // �p�X���[�h���X�V����ׂ́@UpdateExpression�@�𐶐����čX�V����B
        UpdateExpression[] expression = inputForm.buildPwdUpdateExpression(entryUserId);
        this.memberInfoDAO.updateByCriteria(criteria, expression);


        // �⍇�����̃X�e�[�^�X���X�V����ׂ̎�L�[�����𐶐�����B
		criteria = new DAOCriteria();
		criteria.addWhereClause("remindId", inputForm.getRemindId());

        // �⍇�����̃X�e�[�^�X�X�V�p�� UpdateExpression�@�𐶐����čX�V����B
		expression = new UpdateExpression[] {new UpdateValue("commitFlg", "1"),
											 new UpdateValue("updDate", new Date()),
											 new UpdateValue("updUserId", entryUserId)};

        this.passwordRemindDAO.updateByCriteria(criteria, expression);

        // ��x�̔Ԃ��ꂽ UUID ���i�v���Ԃɂ���ׁA�p�X���[�h�⍇�����͏������Ȃ��B

	}



	/**
	 * ���[�U�[ID �p�̃o���[�I�u�W�F�N�g���쐬����t�@�N�g���[���\�b�h<br/>
	 * <br/>
	 * @return ���[�U�[ID �I�u�W�F�N�g
	 */
	protected UserInfo buildUserInfo() {

		// �d�v
		// ���̃V�X�e���ł̓}�C�y�[�W���[�U�[�̏��́A���O�C��ID ���ɑ��݂��鎖��O��Ƃ��Ă���B
		// �ʃJ�X�^�}�C�Y���Ń��O�C��ID ����r������ꍇ�́A���̃��\�b�h�̖߂�l�� null �ɂȂ�
		// �l�ɃI�[�o�[���C�h����K�v������B
		// ���̃��\�b�h�̖߂�l�� null �̏ꍇ�A�Ǘ��@�\�̓��O�C��ID���̍X�V���s��Ȃ��B
		// ��DB �̐���폜��A�Q�ƌn�@�\�� DAO ���C������ȂǁA����Ȃ�̑Ή��͕K�v�B

		return (UserInfo) this.valueObjectFactory.getValueObject("UserInfo"); 
	}



	/**
	 * �}�C�y�[�W���[�U�[�p�̃o���[�I�u�W�F�N�g���쐬����t�@�N�g���[���\�b�h<br/>
	 * <br/>
	 * @return MypageUserInterface �����������Ǘ����[�U�[�I�u�W�F�N�g
	 */
	protected MypageUserInterface buildMemberInfo() {

		// �d�v
		// �����Ǘ����[�U�[�e�[�u���� AdminLoginInfo �ȊO�̃I�u�W�F�N�g�ɕύX�����ꍇ�A
		// ���̃��\�b�h��K�؂ȃo���[�I�u�W�F�N�g�𐶐�����l�ɃI�[�o�[���C�h���鎖�B

		return (MypageUserInterface) this.valueObjectFactory.getValueObject("MemberInfo"); 
	}

	

	/**
	 * �p�X���[�h�⍇���p�̃o���[�I�u�W�F�N�g���쐬����t�@�N�g���[���\�b�h<br/>
	 * <br/>
	 * @param member �Y���ƂȂ������
	 * @param editUserId �o�^��ID �i�����F�؎��ɕ����o���ꂽID�j
	 * 
	 * @return PasswordRemind �p�X���[�h�⍇�����
	 */
	protected PasswordRemind buildPasswordRemind(MypageUserInterface member, String editUserId){
		//�@�t�@�N�g���[���g�p���ăo���[�I�u�W�F�N�g�𐶐�����B
		PasswordRemind passwordRemind = (PasswordRemind) valueObjectFactory.getValueObject("PasswordRemind");

		// �p�X���[�h�⍇�����́A���͒l���c�a�ɕۑ����Ȃ��̂ŁAUUID �ȊO�̒l�̐ݒ�������Ȃ��Ă��܂��B

		// ���[�U�[ID
		passwordRemind.setUserId((String)member.getUserId());
		// �ύX�m��t���O �i���m��Œ�j
		passwordRemind.setCommitFlg("0");
		// �⍇���o�^��
		passwordRemind.setInsDate(new Date());

		// �⍇���o�^��
		// �����F�؎��ɉ�������� UserID �͕����o����Ă���̂ŁA���̒l��ݒ肵�Ă���
		passwordRemind.setInsUserId(editUserId);

		return passwordRemind;
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
