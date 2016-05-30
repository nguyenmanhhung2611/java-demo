package jp.co.transcosmos.dm3.corePana.model.passwordRemind.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.mypage.form.PwdChangeForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.validation.PasswordValidation;
import jp.co.transcosmos.dm3.corePana.validation.SameValueValidation;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.MinLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;


/**
 * �}�C�y�[�W�����񃁃��e�i���X�̓��̓p�����[�^����p�t�H�[��.
 * <p>
 *
 * <pre>
 * �S����         �C����     �C�����e
 * -------------- ----------- -----------------------------------------------------
 * tang.tianyun   2015.04.17  �V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class PasswordChangeForm extends PwdChangeForm {


	/** ���ʃR�[�h�I�u�W�F�N�g */
	protected CodeLookupManager codeLookupManager;

	/** ���ʃp�����[�^�I�u�W�F�N�g */
	protected CommonParameters commonParameters;

	/** �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B */
	protected LengthValidationUtils lengthUtils;

	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * �f�t�H���g�R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 *
	 */
	protected PasswordChangeForm(LengthValidationUtils lengthUtils,
			CommonParameters commonParameters,
			CodeLookupManager codeLookupManager){
		super();
		this.lengthUtils = lengthUtils;
		this.commonParameters = commonParameters;
		this.codeLookupManager = codeLookupManager;
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

		ValidationChain valNewPassword = new ValidationChain("�V�����p�X���[�h",
				this.getNewPassword());
		// �K�{�`�F�b�N
		valNewPassword.addValidation(new NullOrEmptyCheckValidation());

		// �ŏ������`�F�b�N
		valNewPassword.addValidation(new MinLengthValidation(this.commonParameters.getMinAdminPwdLength()));

		// �ő包���`�F�b�N
		valNewPassword.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("user.pwd.newPassword", 16)));

		String newPasswordChk = this.getNewPasswordChk();
		valNewPassword.addValidation(new SameValueValidation("�V�����p�X���[�h�̊m�F", newPasswordChk));

		// �p�X���[�h���x�`�F�b�N�̃o���f�[�V�����I�u�W�F�N�g���擾�ł����ꍇ�A�p�X���[�h���x�̃o���f�[�V���������s����B
		Validation pwdValidation = createPwdValidation();
		if (pwdValidation != null){
			valNewPassword.addValidation(pwdValidation);
		}

		valNewPassword.validate(errors);

		ValidationChain valNewPasswordChk = new ValidationChain("�V�����p�X���[�h�̊m�F",
				this.getNewPasswordChk());
		// �K�{�`�F�b�N
		valNewPasswordChk.addValidation(new NullOrEmptyCheckValidation());

		valNewPasswordChk.validate(errors);

		return (startSize == errors.size());
	}

	/**
	 * �p�X���[�h���x�`�F�b�N�o���f�[�V�����̃C���X�^���X�𐶐�����B<br/>
	 * �p�X���[�h���x�`�F�b�N���s��Ȃ��ꍇ�A���̃��\�b�h�̖߂�l�� null �ɂȂ�l�ɃI�[�o�[���C�h���鎖�B<br/>
	 * <br/>
	 * @return
	 */
	protected Validation createPwdValidation(){
		return new PasswordValidation(this.commonParameters.getMypagePwdMastMarks());
	}

}
