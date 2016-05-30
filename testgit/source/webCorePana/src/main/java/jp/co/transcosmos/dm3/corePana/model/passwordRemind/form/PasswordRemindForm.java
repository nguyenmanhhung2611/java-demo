package jp.co.transcosmos.dm3.corePana.model.passwordRemind.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.validation.EmailRFCValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * �p�X���[�h���}�C���_�[����p�t�H�[��.
 * <p>
 *
 * <pre>
 * �S����         �C����     �C�����e
 * -------------- ----------- -----------------------------------------------------
 * ��		   2015.04.17  �V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class PasswordRemindForm extends MypageUserForm {

    /** �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B */
    protected LengthValidationUtils lengthUtils;

	/** ���[�U�[���[���A�h���X */
	private String mailAddress;


	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	PasswordRemindForm(LengthValidationUtils lengthUtils) {
		this.lengthUtils = lengthUtils;
	}

	/**
	 * �o���f�[�V��������<br/>
	 * ���N�G�X�g�̃o���f�[�V�������s��<br/>
	 * <br/>
	 *
	 * @param errors
	 *            �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @return ���펞 true�A�G���[�� false
	 */
	public boolean validate(List<ValidationFailure> errors) {
		int startSize = errors.size();

		ValidationChain valMailAddress = new ValidationChain("passwordRemind.input.valMail", this.getMailAddress());

		// �K�{
		valMailAddress.addValidation(new NullOrEmptyCheckValidation());
		// ����
		valMailAddress.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("passwordRemind.input.valMail", 255)));
		// ���[���`��
        valMailAddress.addValidation(new EmailRFCValidation());
        valMailAddress.validate(errors);

		return (startSize == errors.size());
	}
}
