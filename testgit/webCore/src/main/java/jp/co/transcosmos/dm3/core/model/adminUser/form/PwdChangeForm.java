package jp.co.transcosmos.dm3.core.model.adminUser.form;

import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.validation.CompareValidation;
import jp.co.transcosmos.dm3.core.validation.PasswordValidation;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.UpdateExpression;
import jp.co.transcosmos.dm3.dao.UpdateValue;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.EncodingUtils;
import jp.co.transcosmos.dm3.validation.AlphanumericMarkOnlyValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.MinLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * �Ǘ����[�U�[�p�X���[�h�ύX�̃p�����[�^����p�t�H�[��.
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.22	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class PwdChangeForm implements Validateable {

	/** ���ʃp�����[�^�I�u�W�F�N�g */
	protected CommonParameters commonParameters;
	/** �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B */
	protected LengthValidationUtils lengthUtils;
	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;

	/** ���p�X���[�h */
	private String oldPassword;
	/** �V�p�X���[�h */
	private String newPassword;
	/** �V�p�X���[�h�i�m�F�j */
	private String newRePassword;



	/**
	 * �f�t�H���g�R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	protected PwdChangeForm(){
		super();
	}




	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
	 * @param commonParameters ���ʃp�����[�^�I�u�W�F�N�g
	 * @param codeLookupManager ���ʃR�[�h�ϊ�����
	 */
	protected PwdChangeForm(LengthValidationUtils lengthUtils,
							CommonParameters commonParameters,
							CodeLookupManager codeLookupManager){
		super();
		this.lengthUtils = lengthUtils;
		this.commonParameters = commonParameters;
		this.codeLookupManager = codeLookupManager;
	}


	
	/**
	 * �Ǘ����[�U�[���̎�L�[�l�ƂȂ錟�������𐶐�����B<br/>
	 * <br/>
	 * ���̃��\�b�h���ł́ADB �̕����t�B�[���h�����w�肵�Ă���R�[�h�����݂���B<br/>
	 * ���ׁ̈A�A�Ǘ����[�U�[���̃e�[�u���Ƃ��āAAdminLoginInfo �ȊO���g�p�����ꍇ�A���̃��\�b�h��
	 * �I�[�o�[���C�h����K�v������B<br/>
	 * <br/>
	 * @param userId �X�V�Ώۃ��[�U�[ID
	 * 
	 * @return ��L�[�ƂȂ錟�������I�u�W�F�N�g
	 */
	public DAOCriteria buildPkCriteria(String userId){
		// ���������I�u�W�F�N�g���擾
		DAOCriteria criteria = new DAOCriteria();

		// ���[�U�[�Ǘ������Ă���e�[�u���̎�L�[�̌��������𐶐�����B
		criteria.addWhereClause("adminUserId", userId);

		return criteria;
	}



	/**
	 * �Ǘ����[�U�[���̃p�X���[�h�X�V�p UpdateExpression �𐶐�����B<br/>
	 * <br/>
	 * �Ǘ����[�U�[���̃e�[�u���Ƃ��āAAdminLoginInfo �ȊO���g�p�����ꍇ�A���̃��\�b�h��
	 * �I�[�o�[���C�h����K�v������B<br/>
	 * <br/>
	 * @return �p�X���[�h�X�V�p�@UpdateExpression
	 */
	public UpdateExpression[] buildPwdUpdateExpression(String editUserId){

		// �p�X���[�h���Í�������B
        String hashPassword = EncodingUtils.md5Encode(this.newPassword);

        // �V�X�e�����t
        Date sysDate = new Date();

		// ���̂܂� UPDATE ����ƁA�X�V�Ώۃt�B�[���h�ȊO�� null �ɂȂ��Ă��܂��̂ŁA
		// UpdateExpression�@���g�p���čX�V�t�B�[���h�����肷��B
        // ���̃��\�b�h�̏ꍇ�A�o���[�I�u�W�F�N�g���󂯎���Ă��Ȃ��̂ŁAAdminLoginInfo ���g�p�����
        // ����̂����f���ł��Ȃ��B�@�ʃe�[�u�����g�p����ꍇ�͕K���I�[�o�[���C�h���鎖�B
        return new UpdateExpression[] {new UpdateValue("password", hashPassword),
        							   new UpdateValue("lastPwdChangeDate", sysDate),
        							   new UpdateValue("updUserId", editUserId),
        							   new UpdateValue("updDate", sysDate)};
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

		// ���݂̃p�X���[�h���̓`�F�b�N
		validOldPassword(errors);
		// �V�p�X���[�h���̓`�F�b�N
		validNewPassword(errors);
		// �V�p�X���[�h�m�F���̓`�F�b�N
		validComparePassword(errors);

        return (startSize == errors.size());
	}

	/**
	 * ���݂̃p�X���[�h �o���f�[�V����<br/>
	 * <ul>
	 *   <li>�K�{�`�F�b�N</li>
	 *   <li>�����`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validOldPassword(List<ValidationFailure> errors) {
		 // ���݂̃p�X���[�h���̓`�F�b�N
		ValidationChain valid = new ValidationChain("user.pwd.oldPassword",this.oldPassword);
		valid.addValidation(new NullOrEmptyCheckValidation());		// �K�{�`�F�b�N
		// �����`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("user.pwd.oldPassword", 16)));
		valid.validate(errors);
	}

	/**
	 * �V�p�X���[�h �o���f�[�V����<br/>
	 * <ul>
	 *   <li>�K�{�`�F�b�N</li>
	 *   <li>�ŏ������`�F�b�N</li>
	 *   <li>�ő包���`�F�b�N</li>
	 *   <li>�V�p�X���[�h�̔��p�p���L�������`�F�b�N</li>
	 *   <li>���p�X���[�h�Ɠ����łȂ���</li>
	 *   <li>�p�X���[�h���x�`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validNewPassword(List<ValidationFailure> errors) {
		// �V�p�X���[�h���̓`�F�b�N
		ValidationChain valid = new ValidationChain("user.pwd.newPassword", this.newPassword);

		// �K�{�`�F�b�N
		valid.addValidation(new NullOrEmptyCheckValidation());

		// �ŏ������`�F�b�N
		valid.addValidation(new MinLengthValidation(this.commonParameters.getMinAdminPwdLength()));

		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("user.pwd.newPassword", 16)));

		// �V�p�X���[�h�̔��p�p���L�������`�F�b�N
		// ���p�\�ȋL�������́A���ʃp�����[�^�I�u�W�F�N�g����擾����B
		valid.addValidation(new AlphanumericMarkOnlyValidation(this.commonParameters.getAdminPwdUseMarks()));

        // �V�p�X���[�h�Ƌ��p�X���[�h����v���Ă���ꍇ�A�G���[�Ƃ���B
        // �V�p�X���[�h�̔�r�Ώۃt�B�[���h�����擾
        String label = this.codeLookupManager.lookupValue("errorLabels", "user.pwd.oldPassword");
        valid.addValidation(new CompareValidation(this.oldPassword, label, true));

		// �p�X���[�h���x�`�F�b�N�̃o���f�[�V�����I�u�W�F�N�g���擾�ł����ꍇ�A�p�X���[�h���x�̃o���f�[�V���������s����B
		Validation pwdValidation = createPwdValidation();
		if (pwdValidation != null){
			valid.addValidation(pwdValidation);
		}
		valid.validate(errors);
	}

	/**
	 * �V�p�X���[�h�m�F �o���f�[�V����<br/>
	 * <ul>
	 *   <li>�K�{�`�F�b�N</li>
	 *   <li>�V�p�X���[�h�̏ƍ��`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validComparePassword(List<ValidationFailure> errors) {
		// �V�p�X���[�h�m�F���̓`�F�b�N
		ValidationChain valid = new ValidationChain("user.pwd.newRePassword", this.newRePassword);
		// �K�{�`�F�b�N
		valid.addValidation(new NullOrEmptyCheckValidation());

        // �V�p�X���[�h�̏ƍ��`�F�b�N
        // �ē��̓p�X���[�h�̔�r�Ώۃt�B�[���h�����擾
        String label = this.codeLookupManager.lookupValue("errorLabels", "user.pwd.newPassword");
        valid.addValidation(new CompareValidation(this.newPassword, label));

        valid.validate(errors);
	}

	/**
	 * �p�X���[�h���x�`�F�b�N�o���f�[�V�����̃C���X�^���X�𐶐�����B<br/>
	 * �p�X���[�h���x�`�F�b�N���s��Ȃ��ꍇ�A���̃��\�b�h�̖߂�l�� null �ɂȂ�l�ɃI�[�o�[���C�h���鎖�B<br/>
	 * <br/>
	 * @return
	 */
	protected Validation createPwdValidation(){
		return new PasswordValidation(this.commonParameters.getAdminPwdMastMarks());
	}



	/**
	 * ���p�X���[�h���擾����B<br/>
	 * <br/>
	 * @return ���p�X���[�h
	 */
	public String getOldPassword() {
		return oldPassword;
	}

	/**
	 * ���p�X���[�h��ݒ肷��B<br/>
	 * <br/>
	 * @param oldPassword ���p�X���[�h
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	/**
	 * �V�p�X���[�h���擾����B<br/>
	 * <br/>
	 * @return �V�p�X���[�h
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * �V�p�X���[�h��ݒ肷��B<br/>
	 * <br/>
	 * @param newPassword �V�p�X���[�h
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * �V�p�X���[�h�i�m�F�j���擾����B<br/>
	 * <br/>
	 * @return �V�p�X���[�h�i�m�F�j
	 */
	public String getNewRePassword() {
		return newRePassword;
	}

	/**
	 * �V�p�X���[�h�i�m�F�j��ݒ肷��B<br/>
	 * <br/>
	 * @param newRePassword �V�p�X���[�h�i�m�F�j
	 */
	public void setNewRePassword(String newRePassword) {
		this.newRePassword = newRePassword;
	}

}
