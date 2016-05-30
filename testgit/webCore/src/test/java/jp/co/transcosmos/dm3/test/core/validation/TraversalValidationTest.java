package jp.co.transcosmos.dm3.test.core.validation;


import jp.co.transcosmos.dm3.core.validation.TraversalValidation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.junit.Assert;
import org.junit.Test;

/**
 * �f�B���N�g���g���o�[�T���`�F�b�N�̃e�X�g
 * 
 */
public class TraversalValidationTest {

	
	/**
	 * �X���b�V���A�R�����A�o�b�N�X���b�V���A�A�����Ȃ��R�����̏ꍇ
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li> null �����A����鎖</li>
	 * </ul>
	 */
	@Test
	public void okTest1(){

		TraversalValidation validation = new TraversalValidation();

		Assert.assertNull("���p�p�����̏ꍇ null �ł��鎖", validation.validate("test", "abcdefghijklmnopqrstuvwxyz"));
		Assert.assertNull("���p�p�厚�̏ꍇ null �ł��鎖", validation.validate("test", "ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
		Assert.assertNull("���p�����̏ꍇ null �ł��鎖", validation.validate("test", "1234567890"));
		Assert.assertNull("����Ă��锼�p�L���̏ꍇ null �ł��鎖", validation.validate("test", "!\"#$%&'()=-~^|`{}[]+;*?_."));
		Assert.assertNull("���p�p�����̏ꍇ null �ł��鎖", validation.validate("test", "ahc.jpg"));
		Assert.assertNull("�S�p�����̏ꍇ null �ł��鎖", validation.validate("test", "�����������E���^"));
	}
	
	/**
	 * �󕶎���̏ꍇ
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li> null �����A����鎖</li>
	 * </ul>
	 */
	@Test
	public void okTest2(){

		TraversalValidation validation = new TraversalValidation();

		Assert.assertNull("�󕶎���̏ꍇ null �ł��鎖", validation.validate("test", ""));
	}

	/**
	 * null �̏ꍇ
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li> null �����A����鎖</li>
	 * </ul>
	 */
	@Test
	public void okTest3(){

		TraversalValidation validation = new TraversalValidation();

		Assert.assertNull("null �̏ꍇ null �ł��鎖", validation.validate("test", null));
	}

	
	/**
	 * �g�p�֎~�����񂪊܂܂��ꍇ
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������G���[�I�u�W�F�N�g�����A����鎖</li>
	 * </ul>
	 */
	@Test
	public void ngTest1(){

		TraversalValidation validation = new TraversalValidation();

		ValidationFailure fail = validation.validate("test", "aaaa/a");
		
		Assert.assertEquals("�������G���[�I�u�W�F�N�g�����A����鎖�i�^�C�v�j", "traversal", fail.getType());
		Assert.assertEquals("�������G���[�I�u�W�F�N�g�����A����鎖�i�l�j", "aaaa/a", fail.getValue());
		Assert.assertEquals("�������G���[�I�u�W�F�N�g�����A����鎖�i���x���j", "test", fail.getName());

		
		fail = validation.validate("test", "aaaa/../a");
		
		Assert.assertEquals("�������G���[�I�u�W�F�N�g�����A����鎖�i�^�C�v�j", "traversal", fail.getType());
		Assert.assertEquals("�������G���[�I�u�W�F�N�g�����A����鎖�i�l�j", "aaaa/../a", fail.getValue());
		Assert.assertEquals("�������G���[�I�u�W�F�N�g�����A����鎖�i���x���j", "test", fail.getName());

		fail = validation.validate("test", "aaaa:a");
		
		Assert.assertEquals("�������G���[�I�u�W�F�N�g�����A����鎖�i�^�C�v�j", "traversal", fail.getType());
		Assert.assertEquals("�������G���[�I�u�W�F�N�g�����A����鎖�i�l�j", "aaaa:a", fail.getValue());
		Assert.assertEquals("�������G���[�I�u�W�F�N�g�����A����鎖�i���x���j", "test", fail.getName());

	}

}
