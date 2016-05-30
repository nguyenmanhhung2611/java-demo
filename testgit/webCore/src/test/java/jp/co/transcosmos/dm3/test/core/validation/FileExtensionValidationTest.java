package jp.co.transcosmos.dm3.test.core.validation;

import jp.co.transcosmos.dm3.core.validation.FileExtensionValidation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.junit.Assert;
import org.junit.Test;

/**
 * ������� model �X�V�n�����̃e�X�g
 * 
 */
public class FileExtensionValidationTest {

	/**
	 * �w�肳�ꂽ�g���q�ɊY������ꍇ
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�啶���A���������قȂ�ꍇ�ł� null �����A����鎖</li>
	 * </ul>
	 */
	@Test
	public void oKtest1(){
		
		FileExtensionValidation validation = new FileExtensionValidation(new String[]{".jpg", ".jpeg"});
		
		Assert.assertNull("�g���q����v����ꍇ null �����A���鎖", validation.validate("test", "ABC.jpg"));
		Assert.assertNull("�g���q����v����ꍇ null �����A���鎖", validation.validate("test", "ABC.JPG"));
		Assert.assertNull("�g���q����v����ꍇ null �����A���鎖", validation.validate("test", "ABC.jpeg"));
		Assert.assertNull("�g���q����v����ꍇ null �����A���鎖", validation.validate("test", "ABC.JPEG"));
		
	}
	

	/**
	 * �w�肳�ꂽ�l���󕶎���̏ꍇ
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�`������̏ꍇ�ł� null �����A����鎖</li>
	 * </ul>
	 */
	@Test
	public void okTest2(){
		
		FileExtensionValidation validation = new FileExtensionValidation(new String[]{".jpg", ".jpeg"});
		
		Assert.assertNull("�󕶎���̏ꍇ null �����A���鎖", validation.validate("test", ""));
		
	}


	
	/**
	 * �w�肳�ꂽ�l�� null �̏ꍇ
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�`������̏ꍇ�ł� null �����A����鎖</li>
	 * </ul>
	 */
	@Test
	public void okTest3(){
		
		FileExtensionValidation validation = new FileExtensionValidation(new String[]{".jpg", ".jpeg"});
		
		Assert.assertNull("null �̏ꍇ null �����A���鎖", validation.validate("test", null));
		
	}

	
	
	/**
	 * �w�肳�ꂽ�g���ȊO���g�p���ꂽ�ꍇ
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖</li>
	 * </ul>
	 */
	@Test
	public void ngTest1(){
		
		FileExtensionValidation validation = new FileExtensionValidation(new String[]{".jpg", ".jpeg"});
		
		ValidationFailure fail = validation.validate("test", "ABC.PG");
		Assert.assertEquals("�������G���[�I�u�W�F�N�g�����A����鎖�i�^�C�v�j", "fileExtNG", fail.getType());
		Assert.assertEquals("�������G���[�I�u�W�F�N�g�����A����鎖�i�l�j", "ABC.PG", fail.getValue());
		Assert.assertEquals("�������G���[�I�u�W�F�N�g�����A����鎖�i���x���j", "test", fail.getName());

		fail = validation.validate("test", "ABC.");
		Assert.assertEquals("�������G���[�I�u�W�F�N�g�����A����鎖�i�^�C�v�j", "fileExtNG", fail.getType());
		Assert.assertEquals("�������G���[�I�u�W�F�N�g�����A����鎖�i�l�j", "ABC.", fail.getValue());
		Assert.assertEquals("�������G���[�I�u�W�F�N�g�����A����鎖�i���x���j", "test", fail.getName());

		fail = validation.validate("test", "ABC.gif");
		Assert.assertEquals("�������G���[�I�u�W�F�N�g�����A����鎖�i�^�C�v�j", "fileExtNG", fail.getType());
		Assert.assertEquals("�������G���[�I�u�W�F�N�g�����A����鎖�i�l�j", "ABC.gif", fail.getValue());
		Assert.assertEquals("�������G���[�I�u�W�F�N�g�����A����鎖�i���x���j", "test", fail.getName());
		
		fail = validation.validate("test", "jpg");
		Assert.assertEquals("�������G���[�I�u�W�F�N�g�����A����鎖�i�^�C�v�j", "fileExtNG", fail.getType());
		Assert.assertEquals("�������G���[�I�u�W�F�N�g�����A����鎖�i�l�j", "jpg", fail.getValue());
		Assert.assertEquals("�������G���[�I�u�W�F�N�g�����A����鎖�i���x���j", "test", fail.getName());
		
	}

}
