package jp.co.transcosmos.dm3.validation;

import org.junit.Assert;
import org.junit.Test;

/**
 * ���p�p���L�������̃o���f�[�V�����e�X�g<br/>
 * <br/>
 */
public class AsciiOnlyValidationTest {

	/** �e�X�g�ΏۃN���X */
	Validation validation = new AsciiOnlyValidation();

	
	/**
	 * ����n�̃e�X�g�i���p�p�������j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>null �����A����鎖�i����I���j</li>
	 * </ul>
	 */
	@Test
	public void mailValidateOKTest1() {
		
		String targetField = "test";
		String targetValue = "abcdefghijklmnopqrstuvwxyz";
		
		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

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
	public void mailValidateOKTest2() {
		
		String targetField = "test";
		String targetValue = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

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
	public void mailValidateOKTest3() {
		
		String targetField = "test";
		String targetValue = "0123456789";
		
		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("���p�����̏ꍇ�A�߂�l�� null �ł��鎖", null, failure);

	}

	
	/**
	 * ����n�̃e�X�g�i���p�L���j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>null �����A����鎖�i����I���j</li>
	 * </ul>
	 */
	@Test
	public void mailValidateOKTest4() {
		
		String targetField = "test";
		String targetValue = "!\"#$%&'()=-~^|\\`@{[+;*:}]<>,.?/_";
		
		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("���p�L���̏ꍇ�A�߂�l�� null �ł��鎖", null, failure);

	}

	
	
	/**
	 * ����n�̃e�X�g�inull �̏ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>null �����A����鎖�i����I���j</li>
	 * </ul>
	 */
	@Test
	public void mailValidateOKTest5() {
		
		String targetField = "test";
		String targetValue = null;
		
		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("null �̏ꍇ�A�߂�l�� null �ł��鎖", null, failure);

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
	public void mailValidateOKTest6() {
		
		String targetField = "test";
		String targetValue = "";
		
		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("�󕶎���̏ꍇ�A�߂�l�� null �ł��鎖", null, failure);

	}


	
	/**
	 * �ُ�n�̃e�X�g�i�S�p�������܂ޏꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖</li>
	 * </ul>
	 */
	@Test
	public void mailValidateNGTest1() {
		
		String targetField = "test";
		String targetValue = "agc�Pdedg";
		
		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("�S�p��������������ꍇ�A�G���[�I�u�W�F�N�g�����A����鎖", null, failure);
		Assert.assertEquals("�S�p��������������ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j", targetField, failure.getName());
		Assert.assertEquals("�S�p��������������ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j", "asciiOnly", failure.getType());
		Assert.assertEquals("�S�p��������������ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j", targetValue, failure.getValue());

	}

	
	
	/**
	 * �ُ�n�̃e�X�g�i�S�p�������܂ޏꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖</li>
	 * </ul>
	 */
	@Test
	public void mailValidateNGTest2() {
		
		String targetField = "test";
		String targetValue = "agc����dedg";
		
		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("�S�p��������������ꍇ�A�G���[�I�u�W�F�N�g�����A����鎖", null, failure);
		Assert.assertEquals("�S�p��������������ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j", targetField, failure.getName());
		Assert.assertEquals("�S�p��������������ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j", "asciiOnly", failure.getType());
		Assert.assertEquals("�S�p��������������ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j", targetValue, failure.getValue());

	}

}
