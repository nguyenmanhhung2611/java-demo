package jp.co.transcosmos.dm3.validation;

import org.junit.Assert;
import org.junit.Test;

/**
 * RFC �Ή����[���A�h���X�o���f�[�V�����̃e�X�g<br/>
 * <br/>
 */
public class EmailRFCValidationTest {

	/** �e�X�g�ΏۃN���X */
	Validation validation = new EmailRFCValidation();


	
	
	/**
	 * ���탁�[���A�h���X�̃e�X�g<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>null �����A����鎖�i����I���j</li>
	 * </ul>
	 */
	@Test
	public void mailValidateOKTest1() {
		
		String targetField = "test";
		String targetValue = "Mizuno.Hiroyuki@trans-cosmos.co.jp";
		
		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("����ȃ��[���A�h���X�̏ꍇ�A�߂�l�� null �ł��鎖", null, failure);

	}
	

	/**
	 * ���[�U�[���̐擪���s���I�h�̏ꍇ<br/>
	 * ���t���[�����[�N�̐ݒ�ŁA�_�u���R�[�g�ŃG�X�P�[�v�\�ȈׁA����l�Ƃ��Ĉ���<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>null �����A����鎖�i����I���j</li>
	 * </ul>
	 */
	@Test
	public void mailValidateOKTest2() {

		String targetField = "test";
		String targetValue = ".mizuno.hiroyuki@trans.cosmos.co.jp";
		
		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("���[�U�[���̐擪���s���I�h�̏ꍇ�A�߂�l�� null �ł��鎖", null, failure);

	}

	
	
	/**
	 * ���[�U�[���̖������s���I�h�̏ꍇ<br/>
	 * ���t���[�����[�N�̐ݒ�ŁA�_�u���R�[�g�ŃG�X�P�[�v�\�ȈׁA����l�Ƃ��Ĉ���<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>null �����A����鎖�i����I���j</li>
	 * </ul>
	 */
	@Test
	public void mailValidateOKTest3() {

		String targetField = "test";
		String targetValue = "mizuno.hiroyuki.@trans.cosmos.co.jp";
		
		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("���[�U�[���̐擪���s���I�h�̏ꍇ�A�߂�l�� null �ł��鎖", null, failure);

	}

	
	
	/**
	 * ���[�U�[���̃s���I�h���A�����Ă���ꍇ<br/>
	 * ���t���[�����[�N�̐ݒ�ŁA�_�u���R�[�g�ŃG�X�P�[�v�\�ȈׁA����l�Ƃ��Ĉ���<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>null �����A����鎖�i����I���j</li>
	 * </ul>
	 */
	@Test
	public void mailValidateOKTest4() {

		String targetField = "test";
		String targetValue = "mizuno..hiroyuki.@trans.cosmos.co.jp";
		
		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("���[�U�[���̐擪���s���I�h�̏ꍇ�A�߂�l�� null �ł��鎖", null, failure);

	}

	
	
	/**
	 * ���[�U�[���ŁA�֎~����Ă��Ȃ��L���������g�p���ꂽ�ꍇ<br/>
	 * ���֎~�Ώۂ́A[�A]�A(�A)�A;�A,�A:�A\\�A\"�A<�A> <br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>null �����A����鎖�i����I���j</li>
	 * </ul>
	 */
	@Test
	public void mailValidateOKTest5() {

		// �n�C�t���A�s���I�h�������L������
		String okChars[]
				= {"!",  "#", "$",  "%", "&",  "'",
				   "=",  "~",  "^", "|", "`",  "{",
				   "}", "*", "+", "?", "/", ".", "-"};
		
		String targetField = "test";
		
		for (String okChar : okChars) {
	
			String targetValue = "mizuno" + okChar + "hiroyuki@trans.cosmos.co.jp";
			
			// �o���f�[�V�������s
			ValidationFailure failure = validation.validate(targetField, targetValue);

			Assert.assertEquals("���[�U�[���ŁA�֎~����Ă��Ȃ��L���������g�p���ꂽ�ꍇ�A�߂�l�� null �ł��鎖(" + okChar + ")", null, failure);
		}
	}



	/**
	 * @ ���������[���A�h���X�̏ꍇ<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j</li>
	 * </ul>
	 */
	@Test
	public void mailValidateNGTest1() {
		
		String targetField = "test";
		String targetValue = "trans-cosmos.co.jp";
		
		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("@ �������ꍇ�A�G���[�I�u�W�F�N�g�����A����鎖", null, failure);
		Assert.assertEquals("@ �������ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j", targetField, failure.getName());
		Assert.assertEquals("@ �������ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j", "email", failure.getType());
		Assert.assertEquals("@ �������ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j", targetValue, failure.getValue());

	}


	
	/**
	 * @ ���Q���郁�[���A�h���X�̏ꍇ<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j</li>
	 * </ul>
	 */
	@Test
	public void mailValidateNGTest2() {

		String targetField = "test";
		String targetValue = "mizuno@hiroyuki@trans-cosmos.co.jp";
		
		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("@ ����������ꍇ�A�G���[�I�u�W�F�N�g�����A����鎖", null, failure);
		Assert.assertEquals("@ ����������ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j", targetField, failure.getName());
		Assert.assertEquals("@ ����������ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j", "email", failure.getType());
		Assert.assertEquals("@ ����������ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j", targetValue, failure.getValue());

	}

	
	
	/**
	 * �h���C�����Ƀs���I�h�A�n�C�t���ȊO�̋L�������A����уu�����N������ꍇ<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j</li>
	 * </ul>
	 */
	@Test
	public void mailValidateNGTest3() {

		String targetField = "test";

		// �n�C�t���A�s���I�h�������L������
		String badChars[]
				= {"!",  "\"", "#", "$",  "%", "&",  "'",  "(", ")",
				   "=",  "~",  "^", "\\", "|", "`",  "[",  "]", "{",
				   "}", ";", ":", "*", "+", ",", "?", "/", "<", ">",
				   " "};

		for (String badChar : badChars){
			String targetValue = "mizuno.hiroyuki@trans" + badChar + "cosmos.co.jp";

			// �o���f�[�V�������s
			ValidationFailure failure = validation.validate(targetField, targetValue);

			Assert.assertNotEquals("�h���C�����Ƀs���I�h�A�n�C�t���ȊO�̋L�������A����уu�����N������ꍇ�A�G���[�I�u�W�F�N�g�����A����鎖(" + badChar +")", null, failure);
			Assert.assertEquals("�h���C�����Ƀs���I�h�A�n�C�t���ȊO�̋L�������A����уu�����N������ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j", targetField, failure.getName());
			Assert.assertEquals("�h���C�����Ƀs���I�h�A�n�C�t���ȊO�̋L�������A����уu�����N������ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j", "email", failure.getType());
			Assert.assertEquals("�h���C�����Ƀs���I�h�A�n�C�t���ȊO�̋L�������A����уu�����N������ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j", targetValue, failure.getValue());

		}
	}

	

	/**
	 * �h���C�����ɑS�p����������ꍇ<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j</li>
	 * </ul>
	 */
	@Test
	public void mailValidateNGTest4() {

		String targetField = "test";
		String targetValue = "mizuno@hiroyuki@trans��cosmos.co.jp";
		
		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("�h���C�����ɑS�p����������ꍇ�A�G���[�I�u�W�F�N�g�����A����鎖", null, failure);
		Assert.assertEquals("�h���C�����ɑS�p����������ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j", targetField, failure.getName());
		Assert.assertEquals("�h���C�����ɑS�p����������ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j", "email", failure.getType());
		Assert.assertEquals("�h���C�����ɑS�p����������ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j", targetValue, failure.getValue());

	}

	
	
	/**
	 * �h���C�����Ńs���I�h���A�����Ă���ꍇ<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j</li>
	 * </ul>
	 */
	@Test
	public void mailValidateNGTest5() {

		String targetField = "test";
		String targetValue = "mizuno@hiroyuki@trans..cosmos.co.jp";
		
		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("�h���C�����Ńs���I�h���A�����Ă���ꍇ�A�G���[�I�u�W�F�N�g�����A����鎖", null, failure);
		Assert.assertEquals("�h���C�����Ńs���I�h���A�����Ă���ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j", targetField, failure.getName());
		Assert.assertEquals("�h���C�����Ńs���I�h���A�����Ă���ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j", "email", failure.getType());
		Assert.assertEquals("�h���C�����Ńs���I�h���A�����Ă���ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j", targetValue, failure.getValue());

	}
	

	
	/**
	 * �h���C�����̐擪���s���I�h�̏ꍇ<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j</li>
	 * </ul>
	 */
	@Test
	public void mailValidateNGTest6() {

		String targetField = "test";
		String targetValue = "mizuno@.hiroyuki@trans.cosmos.co.jp";
		
		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("�h���C�����̐擪���s���I�h�̏ꍇ�A�G���[�I�u�W�F�N�g�����A����鎖", null, failure);
		Assert.assertEquals("�h���C�����̐擪���s���I�h�̏ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j", targetField, failure.getName());
		Assert.assertEquals("�h���C�����̐擪���s���I�h�̏ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j", "email", failure.getType());
		Assert.assertEquals("�h���C�����̐擪���s���I�h�̏ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j", targetValue, failure.getValue());

	}


	
	/**
	 * �h���C�����̖������s���I�h�̏ꍇ<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j</li>
	 * </ul>
	 */
	@Test
	public void mailValidateNGTest7() {

		String targetField = "test";
		String targetValue = "mizuno@hiroyuki@trans.cosmos.co.jp.";
		
		// �o���f�[�V�������s
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("�h���C�����̐擪���s���I�h�̏ꍇ�A�G���[�I�u�W�F�N�g�����A����鎖", null, failure);
		Assert.assertEquals("�h���C�����̐擪���s���I�h�̏ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j", targetField, failure.getName());
		Assert.assertEquals("�h���C�����̐擪���s���I�h�̏ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j", "email", failure.getType());
		Assert.assertEquals("�h���C�����̐擪���s���I�h�̏ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j", targetValue, failure.getValue());

	}

	

	/**
	 * ���[�U�[���Ŏg�p�֎~�L�������A����сA�u�����N���g�p���ꂽ�ꍇ<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j</li>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j</li>
	 * </ul>
	 */
	@Test
	public void mailValidateNGTest8() {

		// ���[�U�[���̎g�p�֎~����
		String badChars[]
				= {"(", ")", "<", ">", "[", "]", ":", ";", ",", "\\", "\"", " "};

		
		String targetField = "test";
		
		for (String badChar : badChars) {

			String targetValue = "mizuno" + badChar + "hiroyuki@trans.cosmos.co.jp";			

			// �o���f�[�V�������s
			ValidationFailure failure = validation.validate(targetField, targetValue);

			Assert.assertNotEquals("���[�U�[���Ŏg�p�֎~�L�������A����сA�u�����N���g�p���ꂽ�ꍇ�A�G���[�I�u�W�F�N�g�����A����鎖(" + badChar + ")", null, failure);
			Assert.assertEquals("���[�U�[���Ŏg�p�֎~�L�������A����сA�u�����N���g�p���ꂽ�ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�t�B�[���h���j", targetField, failure.getName());
			Assert.assertEquals("���[�U�[���Ŏg�p�֎~�L�������A����сA�u�����N���g�p���ꂽ�ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�G���[�^�C�v�j", "email", failure.getType());
			Assert.assertEquals("���[�U�[���Ŏg�p�֎~�L�������A����сA�u�����N���g�p���ꂽ�ꍇ�A�������G���[�I�u�W�F�N�g�����A����鎖�i�`�F�b�N�Ώےl�j", targetValue, failure.getValue());
		}
	}
}
