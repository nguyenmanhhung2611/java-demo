package jp.co.transcosmos.dm3.validation;

import org.junit.Assert;
import org.junit.Test;

/**
 * ���p�p�����A�܂��͋����ꂽ�L�������̃o���f�[�V�����e�X�g<br/>
 * <br/>
 */
public class AlphanumericMarkOnlyValidationTest {

	/** �������L�������� */
	private static final String OK_MARKS = "[@;:";
	
	/** �e�X�g�ΏۃN���X */
	private Validation validation = new AlphanumericMarkOnlyValidation(OK_MARKS);
	private Validation nullValidation = new AlphanumericMarkOnlyValidation(null);
	
	
	
	/**
	 * ����n�̃e�X�g�i���p�p�������j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>null �����A����鎖�i����I���j</li>
	 * </ul>
	 */
	@Test
	public void validateOKTest1() {
		
		String targetField = "test";
		String targetValue = "abcdefghijklmnopqrstuvwxyz";
		
		// �o���f�[�V�������s
		ValidationFailure failure = this.validation.validate(targetField, targetValue);

		Assert.assertEquals("���p�p�������̏ꍇ�A�߂�l�� null �ł��鎖", null, failure);

	}

	
	/**
	 * ����n�̃e�X�g�i���p�p�啶���j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>null �����A����鎖�i����I���j</li>
	 * </ul>
	 */
	@Test
	public void validateOKTest2() {
		
		String targetField = "test";
		String targetValue = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		// �o���f�[�V�������s
		ValidationFailure failure = this.validation.validate(targetField, targetValue);

		Assert.assertEquals("���p�p�啶���̏ꍇ�A�߂�l�� null �ł��鎖", null, failure);

	}

	
	/**
	 * ����n�̃e�X�g�i���p�����j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>null �����A����鎖�i����I���j</li>
	 * </ul>
	 */
	@Test
	public void validateOKTest3() {
		
		String targetField = "test";
		String targetValue = "0123456789";
		
		// �o���f�[�V�������s
		ValidationFailure failure = this.validation.validate(targetField, targetValue);

		Assert.assertEquals("���p�����̏ꍇ�A�߂�l�� null �ł��鎖", null, failure);

	}

	
	/**
	 * ����n�̃e�X�g�i�����ꂽ�L�������̏ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>null �����A����鎖�i����I���j</li>
	 * </ul>
	 */
	@Test
	public void validateOKTest4() {
		
		String targetField = "test";
		String targetValue = OK_MARKS + OK_MARKS;

		// �o���f�[�V�������s
		ValidationFailure failure = this.validation.validate(targetField, targetValue);

		Assert.assertEquals("�����ꂽ�L�������̏ꍇ�A�߂�l�� null �ł��鎖", null, failure);

	}


	/**
	 * ����n�̃e�X�g�inull�̏ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>null �����A����鎖�i����I���j</li>
	 * </ul>
	 */
	@Test
	public void validateOKTest5() {
		
		String targetField = "test";
		String targetValue = null;

		// �o���f�[�V�������s
		ValidationFailure failure = this.validation.validate(targetField, targetValue);

		Assert.assertEquals("null�̏ꍇ�A�߂�l�� null �ł��鎖", null, failure);

	}


	/**
	 * ����n�̃e�X�g�i�󕶎���̏ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>null �����A����鎖�i����I���j</li>
	 * </ul>
	 */
	@Test
	public void validateOKTest6() {
		
		String targetField = "test";
		String targetValue = "";

		// �o���f�[�V�������s
		ValidationFailure failure = this.validation.validate(targetField, targetValue);

		Assert.assertEquals("�󕶎���̏ꍇ�A�߂�l�� null �ł��鎖", null, failure);

	}

	
	/**
	 * ����n�̃e�X�g�i�������� null �Ŕ��p�p�������̏ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>null �����A����鎖�i����I���j</li>
	 * </ul>
	 */
	@Test
	public void nullParamValidateOKTest1() {
		
		String targetField = "test";
		String targetValue = "abcdefghijklmnopqrstuvwxyz";

		// �o���f�[�V�������s
		ValidationFailure failure = this.nullValidation.validate(targetField, targetValue);

		Assert.assertEquals("�������� null �Ń`�F�b�N�Ώۂ����p�p�������̏ꍇ�A�߂�l�� null �ł��鎖", null, failure);

	}

	
	/**
	 * ����n�̃e�X�g�i�������� null �Ŕ��p�p�啶���̏ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>null �����A����鎖�i����I���j</li>
	 * </ul>
	 */
	@Test
	public void nullParamValidateOKTest2() {
		
		String targetField = "test";
		String targetValue = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		// �o���f�[�V�������s
		ValidationFailure failure = this.nullValidation.validate(targetField, targetValue);

		Assert.assertEquals("�������� null �Ń`�F�b�N�Ώۂ����p�p�啶���̏ꍇ�A�߂�l�� null �ł��鎖", null, failure);

	}


	/**
	 * ����n�̃e�X�g�i�������� null �Ŕ��p�����̏ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>null �����A����鎖�i����I���j</li>
	 * </ul>
	 */
	@Test
	public void nullParamValidateOKTest3() {
		
		String targetField = "test";
		String targetValue = "0123456789";

		// �o���f�[�V�������s
		ValidationFailure failure = this.nullValidation.validate(targetField, targetValue);

		Assert.assertEquals("�������� null �Ń`�F�b�N�Ώۂ����p�����̏ꍇ�A�߂�l�� null �ł��鎖", null, failure);

	}

	
	/**
	 * �ُ�n�̃e�X�g�i������Ă��Ȃ����p�L���j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖</li>
	 * </ul>
	 */
	@Test
	public void validateNGTest1() {
		
		String targetField = "test";
		String targetValue = "!\"#$%&'()=-~^|\\`{+*}]<>,.?/_";
		
		// �o���f�[�V�������s
		ValidationFailure failure = this.validation.validate(targetField, targetValue);

		Assert.assertNotEquals("������Ă��Ȃ����p�L��������ꍇ�A�G���[�I�u�W�F�N�g�����A����鎖", null, failure);
		Assert.assertEquals("������Ă��Ȃ����p�L��������ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j", targetField, failure.getName());
		Assert.assertEquals("������Ă��Ȃ����p�L��������ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j", "aplhaNumMarkOnly", failure.getType());
		Assert.assertEquals("������Ă��Ȃ����p�L��������ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j", targetValue, failure.getValue());
		Assert.assertEquals("������Ă��Ȃ����p�L��������ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i��������j", OK_MARKS, failure.getExtraInfo()[0]);
		
	}

	
	/**
	 * �ُ�n�̃e�X�g�i�S�p�L���j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖</li>
	 * </ul>
	 */
	@Test
	public void validateNGTest2() {
		
		String targetField = "test";
		String targetValue = "[��;:";
		
		// �o���f�[�V�������s
		ValidationFailure failure = this.validation.validate(targetField, targetValue);

		Assert.assertNotEquals("�S�p�L��������ꍇ�A�G���[�I�u�W�F�N�g�����A����鎖", null, failure);
		Assert.assertEquals("�S�p�L��������ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j", targetField, failure.getName());
		Assert.assertEquals("�S�p�L��������ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j", "aplhaNumMarkOnly", failure.getType());
		Assert.assertEquals("�S�p�L��������ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j", targetValue, failure.getValue());
		Assert.assertEquals("�S�p�L��������ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i��������j", OK_MARKS, failure.getExtraInfo()[0]);
		
	}

	
	/**
	 * ����n�̃e�X�g�i�������� null �Ŕ��p�L�������̏ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖</li>
	 * </ul>
	 */
	@Test
	public void nullParamValidateNGTest1() {
		
		String targetField = "test";
		String targetValue = "!\"#$%&'()=-~^|\\`@{[+;*:}]<>,.?/_";

		// �o���f�[�V�������s
		ValidationFailure failure = this.nullValidation.validate(targetField, targetValue);

		Assert.assertNotEquals("���p�L��������ꍇ�A�G���[�I�u�W�F�N�g�����A����鎖", null, failure);
		Assert.assertEquals("���p�L��������ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j", targetField, failure.getName());
		Assert.assertEquals("���p�L��������ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j", "alphanumericOnly", failure.getType());
		Assert.assertEquals("���p�L��������ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j", targetValue, failure.getValue());
		Assert.assertNull("���p�L��������ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i��������j", failure.getExtraInfo());

	}

	

}
