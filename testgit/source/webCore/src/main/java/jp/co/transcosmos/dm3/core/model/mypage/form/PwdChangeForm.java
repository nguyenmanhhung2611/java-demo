package jp.co.transcosmos.dm3.core.model.mypage.form;

import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.UpdateExpression;
import jp.co.transcosmos.dm3.dao.UpdateValue;
import jp.co.transcosmos.dm3.utils.EncodingUtils;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * �}�C�y�[�W����̃p�X���[�h�ύX�p�t�H�[��.
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.31	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class PwdChangeForm implements Validateable {

	/** �⍇��ID */
	private String remindId;
	/** �V�p�X���[�h */
	private String newPassword;
	/** �V�p�X���[�h�i�m�F�j */
	private String newPasswordChk;


	
	/**
	 * �f�t�H���g�R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 * 
	 */
	protected PwdChangeForm(){
		super();
	}

	
	
	/**
	 * �}�C�y�[�W����̎�L�[�l�ƂȂ錟�������𐶐�����B<br/>
	 * <br/>
	 * ���̃��\�b�h���ł́ADB �̕����t�B�[���h�����w�肵�Ă���R�[�h�����݂���B<br/>
	 * ���ׁ̈A�A�Ǘ����[�U�[���̃e�[�u���Ƃ��āAMemberInfo �ȊO���g�p�����ꍇ�A���̃��\�b�h��
	 * �I�[�o�[���C�h����K�v������B<br/>
	 * <br/>
	 * @return ��L�[�ƂȂ錟�������I�u�W�F�N�g
	 */
	public DAOCriteria buildPkCriteria(String userId){
		// ���������I�u�W�F�N�g���擾
		DAOCriteria criteria = new DAOCriteria();

		// ���[�U�[�Ǘ������Ă���e�[�u���̎�L�[�̌��������𐶐�����B
		criteria.addWhereClause("userId", userId);

		return criteria;
	}



	/**
	 * �}�C�y�[�W����̃p�X���[�h�X�V UpdateExpression �𐶐�����B<br/>
	 * <br/>
	 * �}�C�y�[�W������̃e�[�u���Ƃ��āAMemberInfo �ȊO���g�p�����ꍇ�A���̃��\�b�h��
	 * �I�[�o�[���C�h����K�v������B<br/>
	 * <br/>
	 * @param userId �X�V��ID
	 * 
	 * @return �X�V�^�C���X�^���v UPDATE �p�@UpdateExpression
	 */
	public UpdateExpression[] buildPwdUpdateExpression(String userId){

		// �p�X���[�h���Í�������B
        String hashPassword = EncodingUtils.md5Encode(this.newPassword);

        // �V�X�e�����t
        Date sysDate = new Date();

        // ���̃��\�b�h�̏ꍇ�A�o���[�I�u�W�F�N�g���󂯎���Ă��Ȃ��̂ŁAMemberInfo ���g�p�����
        // ����̂����f���ł��Ȃ��B�@�ʃe�[�u�����g�p����ꍇ�͕K���I�[�o�[���C�h���鎖�B
        return new UpdateExpression[] {new UpdateValue("password", hashPassword),
        							   new UpdateValue("updDate", sysDate),
        							   new UpdateValue("updUserId", userId)};
	}



	/**
	 * �o���f�[�V��������<br/>
	 * ���N�G�X�g�p�����[�^�̃o���f�[�V�������s��<br/>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @return ���펞 true�A�G���[�� false
	 */

	@Override
	public boolean validate(List<ValidationFailure> errors) {

		int startSize = errors.size();
		
		// TODO ���̓~�X�̖�������̂ŁA���[���A�h���X�̐������`�F�b�N�݂͎̂������鎖�B

		return (startSize == errors.size());
	}



	/**
	 * �⍇��ID ���擾����B<br/>
	 * <br/>
	 * @return �⍇��ID �i�p�X���[�h�ύX�p�j
	 */
	public String getRemindId() {
		return remindId;
	}

	/**
	 * �⍇��ID ��ݒ肷��B<br/>
	 * <br/>
	 * @param remindId �⍇��ID �i�p�X���[�h�ύX�p�j
	 */
	public void setRemindId(String remindId) {
		this.remindId = remindId;
	}

	/**
	 * �V�p�X���[�h���擾����B<br/>
	 * <br/>
	 * @return �V�p�X���[�h �i�p�X���[�h�ύX�p�j
	 */
	public String getNewPassword() {
		return newPassword;
	}
	
	/**
	 * �V�p�X���[�h��ݒ肷��B<br/>
	 * <br/>
	 * @param newPassword �V�p�X���[�h �i�p�X���[�h�ύX�p�j
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * �V�p�X���[�h�i�m�F�j���擾����B<br/>
	 * <br/>
	 * @return�@�V�p�X���[�h �i�p�X���[�h�ύX�p�j�i�m�F�j
	 */
	public String getNewPasswordChk() {
		return newPasswordChk;
	}

	/**
	 * �V�p�X���[�h�i�m�F�j��ݒ肷��B<br/>
	 * <br/>
	 * @return�@�V�p�X���[�h �i�p�X���[�h�ύX�p�j�i�m�F�j
	 */
	public void setNewPasswordChk(String newPasswordChk) {
		this.newPasswordChk = newPasswordChk;
	}
}
