package jp.co.transcosmos.dm3.corePana.model.adminUser.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserSearchForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.validation.AsciiOnlyValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;
import jp.co.transcosmos.dm3.validation.ZenkakuOnlyValidation;

/**
 * �Ǘ����[�U�[�����e�i���X�̌����p�����[�^�A����сA��ʃR���g���[���p�����[�^����p�t�H�[��.
 * <p>
 * ���������ƂȂ郊�N�G�X�g�p�����[�^�̎擾��A�c�a�����I�u�W�F�N�g�̐������s���B
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * �`����		2015.04.28	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class PanaAdminUserSearchForm extends AdminUserSearchForm {

	/**
	 * �f�t�H���g�R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	protected PanaAdminUserSearchForm(){
		super();
	}



	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
	 */
	protected PanaAdminUserSearchForm(LengthValidationUtils lengthUtils){
		super();
		this.lengthUtils = lengthUtils;
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

		// ���O�C��ID���̓`�F�b�N
		validKeyLoginId(errors);
		// ���[�U�����̓`�F�b�N
		validKeyUserName(errors);

		return (startSize == errors.size());
	}

	/**
	 * ���O�C��ID �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validKeyLoginId(List<ValidationFailure> errors) {
		String label = "user.search.keyLoginId";
		ValidationChain valid = new ValidationChain(label,this.getKeyLoginId());

		// �����`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 16)));
		// ���p�p���L���`�F�b�N
		valid.addValidation(new AsciiOnlyValidation());

		valid.validate(errors);
	}

	/**
	 * ���[�U�� �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validKeyUserName(List<ValidationFailure> errors){
		String label = "user.search.keyUserName";
		ValidationChain valid = new ValidationChain(label, this.getKeyUserName());

		// �����`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 40)));
		// �S�p�����`�F�b�N
		valid.addValidation(new ZenkakuOnlyValidation());

		valid.validate(errors);
	}
}
