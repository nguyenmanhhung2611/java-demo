package jp.co.transcosmos.dm3.corePana.model.member;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.exception.DuplicateException;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.HousingManageImpl;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserManageImpl;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserForm;
import jp.co.transcosmos.dm3.core.vo.PasswordRemind;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.model.member.form.MemberInfoForm;
import jp.co.transcosmos.dm3.corePana.model.mypage.PanaMypageUserInterface;
import jp.co.transcosmos.dm3.corePana.vo.MemberQuestion;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

/**
 * ������p Model �N���X.
 * <p>
 * <pre>
 * �S����         �C����      �C�����e
 * -------------- ----------- -----------------------------------------------------
 * tang.tianyun	  2015.04.15	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B<br/>
 *
 */
public class PanaMypageUserManageImpl extends MypageUserManageImpl {

	private static final Log log = LogFactory.getLog(HousingManageImpl.class);

	/** �}�C�y�[�W�A���P�[�g���p DAO */
	private DAO<MemberQuestion> memberQuestionDAO;

	/** �p�X���[�h�⍇�����p DAO */
	private DAO<PasswordRemind> passwordRemindDAOChild;

	/**
	 * �p�X���[�h�⍇�����p DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param passwordRemindDAO �p�X���[�h�⍇�����p DAO
	 */
	public void setPasswordRemindDAOChild(DAO<PasswordRemind> passwordRemindDAOChild) {
		this.passwordRemindDAOChild = passwordRemindDAOChild;
	}

	/**
	 * �}�C�y�[�W�A���P�[�g���̌����E�X�V�Ɏg�p���� DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param memberQuestionDao �}�C�y�[�W�A���P�[�g���̌����E�X�VDAO
	 */
	public void setMemberQuestionDAO(DAO<MemberQuestion> memberQuestionDAO) {
		this.memberQuestionDAO = memberQuestionDAO;
	}

	/**
	 * �p�����[�^�œn���ꂽ Form �̏��Ń}�C�y�[�W�A���P�[�g��V�K�o�^����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * <br/>
	 * @param inputForm �}�C�y�[�W���[�U�[�̓��͒l���i�[���� Form �I�u�W�F�N�g
	 *
	 * @return addUserId �Ɠ����l
	 *
 	 * @exception DuplicateException ���[�U�[ID�A�A���P�[�g�ԍ��A�I������CD �̏d��
	 */
	public String addMemberQuestion(MemberInfoForm inputForm, String addUserId)
			throws DuplicateException {

		List<MemberQuestion> list = new ArrayList<>();			// �}�C�y�[�W�A���P�[�g���o�^�p

		if (inputForm.getQuestionId() != null ) {
			for (String questionId : inputForm.getQuestionId()){

				if (!StringValidateUtil.isEmpty(questionId)) {

					// �o���[�I�u�W�F�N�g�𐶐����Ēl��ݒ肷��B
					MemberQuestion memberQuestion = new MemberQuestion();
					memberQuestion.setUserId(addUserId);
					memberQuestion.setCategoryNo(PanaCommonConstant.COMMON_CATEGORY_NO);
					memberQuestion.setQuestionId(questionId);
					if ("008".equals(questionId)) {
						memberQuestion.setEtcAnswer(inputForm.getEtcAnswer1());
					}
					if ("009".equals(questionId)) {
						memberQuestion.setEtcAnswer(inputForm.getEtcAnswer2());
					}
					if ("010".equals(questionId)) {
						memberQuestion.setEtcAnswer(inputForm.getEtcAnswer3());
					}

					list.add(memberQuestion);
				}
			}

			if (list.size() > 0) {
				try {
					// �I������CD ���ݒ肳��Ă���ꍇ�̓��R�[�h��ǉ�����B
					this.memberQuestionDAO.insert(list.toArray(new MemberQuestion[list.size()]));
				} catch (DataIntegrityViolationException e) {
					// �ǉ����ɐe���R�[�h���폜���ꂽ�ꍇ�ANotFoundException �̗�O���X���[����B
					// �Ⴆ�΁A�����̍폜�����Ƌ��������ꍇ�Ȃ�..�B
					log.warn(e.getMessage(), e);
					throw new NotFoundException();
				}
			}
		}

		return addUserId;
	}

	/**
	 * �p�����[�^�œn���ꂽ Form �̏��Ń}�C�y�[�W�A���P�[�g�����X�V����B<br/>
	 * �}�C�y�[�W�A���P�[�g���� DELETE & INSERT �ňꊇ�X�V����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * MemberInfoForm �� userId �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��čX�V����B<br/>
	 * <br/>
	 * ����{�d�l�ł́A�}�C�y�[�W�A���P�[�g����\���p�A�C�R���Ƃ��ēo�^����B
	 * <br/>
	 * @param inputForm �}�C�y�[�W�A���P�[�g���̓��͒l���i�[���� Form �I�u�W�F�N�g
	 *
	 */

	public void updateMemberQuestion(MemberInfoForm inputForm, String userId){
		// �����}�C�y�[�W�A���P�[�g�����폜����B
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("userId", userId);
		this.memberQuestionDAO.deleteByFilter(criteria);

		addMemberQuestion(inputForm, userId);
	}

	/**
	 * ���[�U�[ID �i��L�[�l�j�ɊY������}�C�y�[�W�A���P�[�g���𕜋A����B<br/>
	 * �����Y���f�[�^��������Ȃ��ꍇ�Anull �𕜋A����B<br/>
	 * <br/>
	 * @param userId�@���擾�ΏۂƂȂ�}�C�y�[�W�A���P�[�g���
	 * @return �擾�����}�C�y�[�W�A���P�[�g���
	 */
	public List<MemberQuestion> searchMemberQuestionPk(String userId) {

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("userId", userId);
		// �}�C�y�[�W�A���P�[�g�����擾
		List<MemberQuestion> memberQuestionList =  this.memberQuestionDAO.selectByFilter(criteria);

		return memberQuestionList;
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
		PanaMypageUserInterface memberInfo = (PanaMypageUserInterface) buildMemberInfo();


    	// �t�H�[���̓��͒l���o���[�I�u�W�F�N�g�ɐݒ肷��B
    	inputForm.copyToMemberInfo(memberInfo);

    	// ���[�U�[ID ��ݒ�
    	memberInfo.setUserId(addUserId);

    	// �^�C���X�^���v����ݒ肷��B
    	Date sysDate = new Date();
		// ���O�C�����s��
		memberInfo.setFailCnt(0);
        // �t�����g�T�C�g����o�^�����ꍇ�͓o�^�o�H=WEB�A�L���敪=�L��
        if ("front".equals(((MemberInfoForm) inputForm).getProjectFlg())) {
			// �o�^�o�H
			memberInfo.setEntryRoute(PanaCommonConstant.ENTRY_ROUTE_001);
			// ���b�N�t���O
			memberInfo.setLockFlg(PanaCommonConstant.LOCK_FLG_0);
        }
    	memberInfo.setInsDate(sysDate);
    	memberInfo.setInsUserId(editUserId);
    	memberInfo.setUpdDate(sysDate);
    	memberInfo.setUpdUserId(editUserId);


		// �}�C�y�[�W�������o�^
		try {
			this.memberInfoDAO.insert(new MypageUserInterface[]{memberInfo});

			// �}�C�y�[�W�A���P�[�g�����X�V����
			MemberInfoForm form = (MemberInfoForm)inputForm;
			updateMemberQuestion(form, addUserId);

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
		super.updateMyPageUser(inputForm, updUserId, editUserId);

		// �}�C�y�[�W�A���P�[�g�����X�V����
		MemberInfoForm form = (MemberInfoForm)inputForm;
		updateMemberQuestion(form, updUserId);
	}

	/**
	 * �⍇���o�^��<br/>
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
	public boolean dateCheck(String remindId)
			throws DuplicateException, NotFoundException {

		// ���͂��ꂽ���⍇��ID ���擾����B
		// �擾�����́A���⍇��ID �����N�G�X�g�p�����[�^�ƈ�v���A�������A�L���������̕��B
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("remindId", remindId);
		criteria.addWhereClause("commitFlg", "0");


		List<PasswordRemind> remind = this.passwordRemindDAOChild.selectByFilter(criteria);

		// �Y���f�[�^���擾�o���Ȃ��ꍇ�A��O���X���[����B
		if (remind.size() == 0){
			throw new NotFoundException();
		}

		Date insDate = remind.get(0).getInsDate();

		// �V�X�e������
		Date now = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		long nowTime = cal.getTimeInMillis();

		long insTime = 0;

		cal.setTime(insDate);
		insTime = cal.getTimeInMillis();

		// 1���Ԉȓ��i�V�X�e�����t - �ŏI�X�V��<= 1���j�v�Z
		long betweenDays = (nowTime - insTime) / (1000 * 3600 * 24);

		if(betweenDays>0){
			return false;
		}
		return true;
	}

	/**
	 * ���[�U�[�̃��[��<br/>
	 * <br/>
	 *
	 * @throws DuplicateException
	 * @throws NotFoundException
	 */
	public String getResultMail(String remindId)
			throws DuplicateException, NotFoundException {

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("remindId", remindId);

		List<PasswordRemind> remindInfo = this.passwordRemindDAOChild.selectByFilter(criteria);
		JoinResult userInfo = searchMyPageUserPk(remindInfo.get(0).getUserId());

		return ((jp.co.transcosmos.dm3.corePana.vo.MemberInfo)userInfo.getItems().get("memberInfo")).getEmail();
	}
}
