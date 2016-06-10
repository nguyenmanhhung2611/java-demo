package jp.co.transcosmos.dm3.validation;

import org.junit.Assert;
import org.junit.Test;


/**
 * ���p�����`�F�b�N�o���f�[�V�����̃e�X�g<br/>
 * <br/>
 */
public class NumericValidationTest {

	
	/** �e�X�g�ΏۃN���X */
	Validation validation = new NumericValidation();



	/**
	 * ����n�e�X�g�i0 �` 9 �̕�����j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>null �����A����鎖�i����I���j</li>
	 * </ul>
	 */
	@Test
	public void validationOkTest1(){
		
		String targetField = "test";
		String targetValue = "0123456789";
		
		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("0 �` 9 �ō\������镶����̏ꍇ�A�߂�l�� null �ł��鎖", null, failure);
	}

	
	
	/**
	 * ����n�e�X�g�inull�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>null �����A����鎖�i����I���j</li>
	 * </ul>
	 */
	@Test
	public void validationOkTest2(){
		
		String targetField = "test";
		String targetValue = null;
		
		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("null �̏ꍇ�A�߂�l�� null �ł��鎖", null, failure);
	}


	
	/**
	 * ����n�e�X�g�i�󕶎���j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>null �����A����鎖�i����I���j</li>
	 * </ul>
	 */
	@Test
	public void validationOkTest3(){
		
		String targetField = "test";
		String targetValue = "";
		
		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("�󕶎���̏ꍇ�A�߂�l�� null �ł��鎖", null, failure);
	}


	
	/**
	 * �ُ�n�e�X�g�i�S�p�����̏ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j</li>
	 * </ul>
	 */
	@Test
	public void validationNgTest1(){
		
		String targetField = "test";
		String targetValue = "012345�O6789";
		
		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("�S�p�������܂܂��ꍇ�A�G���[�I�u�W�F�N�g�����A����鎖", null, failure);
		Assert.assertEquals("�S�p�������܂܂��ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j", targetField, failure.getName());
		Assert.assertEquals("�S�p�������܂܂��ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j", "positNumberOnly", failure.getType());
		Assert.assertEquals("�S�p�������܂܂��ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j", targetValue, failure.getValue());

	}



	/**
	 * �ُ�n�e�X�g�i�}�C�i�X���܂ޏꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j</li>
	 * </ul>
	 */
	@Test
	public void validationNgTest2(){
		
		String targetField = "test";
		String targetValue = "-1234567890";
		
		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("�}�C�i�X���܂ޏꍇ�A�G���[�I�u�W�F�N�g�����A����鎖", null, failure);
		Assert.assertEquals("�}�C�i�X���܂ޏꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j", targetField, failure.getName());
		Assert.assertEquals("�}�C�i�X���܂ޏꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j", "positNumberOnly", failure.getType());
		Assert.assertEquals("�}�C�i�X���܂ޏꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j", targetValue, failure.getValue());

	}

	
	
	/**
	 * �ُ�n�e�X�g�i�s���I�h���܂ޏꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j</li>
	 * </ul>
	 */
	@Test
	public void validationNgTest3(){
		
		String targetField = "test";
		String targetValue = "123456.7890";
		
		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("�s���I�h���܂ޏꍇ�A�G���[�I�u�W�F�N�g�����A����鎖", null, failure);
		Assert.assertEquals("�s���I�h���܂ޏꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j", targetField, failure.getName());
		Assert.assertEquals("�s���I�h���܂ޏꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j", "positNumberOnly", failure.getType());
		Assert.assertEquals("�s���I�h���܂ޏꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j", targetValue, failure.getValue());

	}

}
