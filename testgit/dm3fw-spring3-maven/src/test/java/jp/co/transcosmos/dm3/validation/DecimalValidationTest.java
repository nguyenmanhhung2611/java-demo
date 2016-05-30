package jp.co.transcosmos.dm3.validation;

import org.junit.Assert;
import org.junit.Test;

/**
 * ���p�����`�F�b�N�A����сA�������A�������̌������܂ޏꍇ�̃e�X�g�P�[�X<br/>
 * <br/>
 */
public class DecimalValidationTest {

	// �e�X�g�ΏۃN���X
	private Validation validation = new DecimalValidation(8, 3);
	
	
	
	/**
	 * ����n�e�X�g�i�������T���A�������R���j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>null �����A����鎖�i����I���j</li>
	 * </ul>
	 */
	@Test
	public void validationOkTest1(){
		
		String targetField = "test";
		String targetValue = "12345.123";

		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("�������A�������̌����𖞂������p����������̏ꍇ�A�߂�l�� null �ł��鎖", null, failure);
	}

	
	
	/**
	 * ����n�e�X�g�i�������T���̂݁j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>null �����A����鎖�i����I���j</li>
	 * </ul>
	 */
	@Test
	public void validationOkTest2(){
		
		String targetField = "test";
		String targetValue = "12345";

		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("�������݂̂̔��p����������̏ꍇ�A�߂�l�� null �ł��鎖", null, failure);
	}



	/**
	 * ����n�e�X�g�i�s���I�h����n�܂鏬�����R���j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>null �����A����鎖�i����I���j</li>
	 * </ul>
	 */
	@Test
	public void validationOkTest3(){
		
		String targetField = "test";
		String targetValue = ".345";

		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("�������݂̂̔��p����������̏ꍇ�A�߂�l�� null �ł��鎖", null, failure);
	}


	
	/**
	 * ����n�e�X�g�inull �̏ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>null �����A����鎖�i����I���j</li>
	 * </ul>
	 */
	@Test
	public void validationOkTest4(){
		
		String targetField = "test";
		String targetValue = null;

		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("null �̏ꍇ�A�߂�l�� null �ł��鎖", null, failure);
	}

	
	
	/**
	 * ����n�e�X�g�i�󕶎���̏ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>null �����A����鎖�i����I���j</li>
	 * </ul>
	 */
	@Test
	public void validationOkTest5(){
		
		String targetField = "test";
		String targetValue = "";

		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("��̏ꍇ�A�߂�l�� null �ł��鎖", null, failure);
	}



	/**
	 * �ُ�n�e�X�g�i�s���I�h�̂݁j<br/>
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
		String targetValue = ".";

		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("�s���I�h�݂̂̏ꍇ�A�G���[�I�u�W�F�N�g�����A����鎖", null, failure);
		Assert.assertEquals("�s���I�h�݂̂̏ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j", targetField, failure.getName());
		Assert.assertEquals("�s���I�h�݂̂̏ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j", "decimal", failure.getType());
		Assert.assertEquals("�s���I�h�݂̂̏ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j", targetValue, failure.getValue());
	}


	
	/**
	 * �ُ�n�e�X�g�i�s���I�h�ƃX�y�[�X�j<br/>
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
		String targetValue = "  .  ";

		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("�s���I�h�Ƃ��̑O��ɃX�y�[�X�݂̂̏ꍇ�A�G���[�I�u�W�F�N�g�����A����鎖", null, failure);
		Assert.assertEquals("�s���I�h�Ƃ��̑O��ɃX�y�[�X�݂̂̏ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j", targetField, failure.getName());
		Assert.assertEquals("�s���I�h�Ƃ��̑O��ɃX�y�[�X�݂̂̏ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j", "decimal", failure.getType());
		Assert.assertEquals("�s���I�h�Ƃ��̑O��ɃX�y�[�X�݂̂̏ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j", targetValue, failure.getValue());

		
		targetValue = "  .";

		// �o���f�[�V�������s
		failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("�s���I�h�Ƃ��̑O�ɃX�y�[�X�݂̂̏ꍇ�A�G���[�I�u�W�F�N�g�����A����鎖", null, failure);
		Assert.assertEquals("�s���I�h�Ƃ��̑O�ɃX�y�[�X�݂̂̏ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j", targetField, failure.getName());
		Assert.assertEquals("�s���I�h�Ƃ��̑O�ɃX�y�[�X�݂̂̏ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j", "decimal", failure.getType());
		Assert.assertEquals("�s���I�h�Ƃ��̑O�ɃX�y�[�X�݂̂̏ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j", targetValue, failure.getValue());
		
		
		targetValue = ".   ";

		// �o���f�[�V�������s
		failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("�s���I�h�Ƃ��̌��ɃX�y�[�X�݂̂̏ꍇ�A�G���[�I�u�W�F�N�g�����A����鎖", null, failure);
		Assert.assertEquals("�s���I�h�Ƃ��̌��ɃX�y�[�X�݂̂̏ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j", targetField, failure.getName());
		Assert.assertEquals("�s���I�h�Ƃ��̌��ɃX�y�[�X�݂̂̏ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j", "decimal", failure.getType());
		Assert.assertEquals("�s���I�h�Ƃ��̌��ɃX�y�[�X�݂̂̏ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j", targetValue, failure.getValue());
		
	}



	/**
	 * �ُ�n�e�X�g�i�������݂̂Ō����I�[�o�[�j<br/>
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
		String targetValue = "123456";

		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("�������݂̂Ō����I�[�o�[�̏ꍇ�A�G���[�I�u�W�F�N�g�����A����鎖", null, failure);
		Assert.assertEquals("�������݂̂Ō����I�[�o�[�̏ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j", targetField, failure.getName());
		Assert.assertEquals("�������݂̂Ō����I�[�o�[�̏ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j", "decimal", failure.getType());
		Assert.assertEquals("�������݂̂Ō����I�[�o�[�̏ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j", targetValue, failure.getValue());
	}


	
	/**
	 * �ُ�n�e�X�g�i�s���I�h����n�܂鏬�����Ō����I�[�o�[�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j</li>
	 * </ul>
	 */
	@Test
	public void validationNgTest4(){
		
		String targetField = "test";
		String targetValue = ".1234";

		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("�s���I�h����n�܂鏬�����Ō����I�[�o�[�̏ꍇ�A�G���[�I�u�W�F�N�g�����A����鎖", null, failure);
		Assert.assertEquals("�s���I�h����n�܂鏬�����Ō����I�[�o�[�̏ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j", targetField, failure.getName());
		Assert.assertEquals("�s���I�h����n�܂鏬�����Ō����I�[�o�[�̏ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j", "decimal", failure.getType());
		Assert.assertEquals("�s���I�h����n�܂鏬�����Ō����I�[�o�[�̏ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j", targetValue, failure.getValue());
	}



	/**
	 * �ُ�n�e�X�g�i�������A�������ō\������A�������̌����I�[�o�[�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j</li>
	 * </ul>
	 */
	@Test
	public void validationNgTest5(){
		
		String targetField = "test";
		String targetValue = "123456.123";

		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("�������A�������ō\������A�������̌����I�[�o�[�̏ꍇ�A�G���[�I�u�W�F�N�g�����A����鎖", null, failure);
		Assert.assertEquals("�������A�������ō\������A�������̌����I�[�o�[�̏ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j", targetField, failure.getName());
		Assert.assertEquals("�������A�������ō\������A�������̌����I�[�o�[�̏ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j", "decimal", failure.getType());
		Assert.assertEquals("�������A�������ō\������A�������̌����I�[�o�[�̏ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j", targetValue, failure.getValue());
	}

	
	
	/**
	 * �ُ�n�e�X�g�i�������A�������ō\������A�����̌����I�[�o�[�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j</li>
	 * </ul>
	 */
	@Test
	public void validationNgTest6(){
		
		String targetField = "test";
		String targetValue = "12345.1234";

		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("�������A�������ō\������A�������̌����I�[�o�[�̏ꍇ�A�G���[�I�u�W�F�N�g�����A����鎖", null, failure);
		Assert.assertEquals("�������A�������ō\������A�������̌����I�[�o�[�̏ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j", targetField, failure.getName());
		Assert.assertEquals("�������A�������ō\������A�������̌����I�[�o�[�̏ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j", "decimal", failure.getType());
		Assert.assertEquals("�������A�������ō\������A�������̌����I�[�o�[�̏ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j", targetValue, failure.getValue());
	}

	
	
	/**
	 * �ُ�n�e�X�g�i���p�p�����܂ށj<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j</li>
	 * </ul>
	 */
	@Test
	public void validationNgTest7(){
		
		String targetField = "test";
		String targetValue = "1a25.14";

		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("���p�p�����܂ޏꍇ�A�G���[�I�u�W�F�N�g�����A����鎖", null, failure);
		Assert.assertEquals("���p�p�����܂ޏꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j", targetField, failure.getName());
		Assert.assertEquals("���p�p�����܂ޏꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j", "decimal", failure.getType());
		Assert.assertEquals("���p�p�����܂ޏꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j", targetValue, failure.getValue());
	}


	
	/**
	 * �ُ�n�e�X�g�i�S�p�������܂ށj<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j</li>
	 * </ul>
	 */
	@Test
	public void validationNgTest8(){
		
		String targetField = "test";
		String targetValue = "125.�X1";

		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("�S�p�������܂ޏꍇ�A�G���[�I�u�W�F�N�g�����A����鎖", null, failure);
		Assert.assertEquals("�S�p�������܂ޏꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j", targetField, failure.getName());
		Assert.assertEquals("�S�p�������܂ޏꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j", "decimal", failure.getType());
		Assert.assertEquals("�S�p�������܂ޏꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j", targetValue, failure.getValue());
	}

}
