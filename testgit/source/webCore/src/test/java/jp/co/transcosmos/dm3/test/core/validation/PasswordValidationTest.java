package jp.co.transcosmos.dm3.test.core.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import jp.co.transcosmos.dm3.core.validation.PasswordValidation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.junit.Test;


/**
 * <pre>
 * �p�X���[�h���x�`�F�b�N�����e�X�g�p�N���X
 * 
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.04	�V�K�쐬
 * 
 * ���ӎ���
 *
 * </pre>
 */
public class PasswordValidationTest {

	/**
	 * ���p�������Ȃ��B<br/>
	 * <br/>
	 * 
	 */
	@Test
	public void test01() {
		String signList = "!,#,$,%";
		PasswordValidation pv = new PasswordValidation(signList);
		ValidationFailure vd = null;
		vd = pv.validate("testPassword",
				"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!#$%");
		assertEquals("passwordMarkNG", vd.getType());
	}

	/**
	 * ���p�p�����������Ȃ��B<br/>
	 * <br/>
	 * 
	 */
	@Test
	public void test02() {
		String signList = "!,#,$,%";
		PasswordValidation pv = new PasswordValidation(signList);
		ValidationFailure vd = null;
		vd = pv.validate("testPassword",
				"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ!#$%");
		assertEquals("passwordMarkNG", vd.getType());
	}

	/**
	 * ���p�p���啶�����Ȃ��B<br/>
	 * <br/>
	 * 
	 */
	@Test
	public void test03() {
		String signList = "!,#,$,%";
		PasswordValidation pv = new PasswordValidation(signList);
		ValidationFailure vd = null;
		vd = pv.validate("testPassword",
				"0123456789abcdefghijklmnopqrstuvwxyz!#$%");
		assertEquals("passwordMarkNG", vd.getType());
	}

	/**
	 * �L�����Ȃ��B<br/>
	 * <br/>
	 * 
	 */
	@Test
	public void test04() {
		String signList = "!,#,$,%";
		PasswordValidation pv = new PasswordValidation(signList);
		ValidationFailure vd = null;
		vd = pv.validate("testPassword", "0123456789abcdefghijklmnopqrstuvwxyz");
		assertEquals("passwordMarkNG", vd.getType());
	}

	/**
	 * �������Ȃ��L������B<br/>
	 * <br/>
	 * 
	 */
	@Test
	public void test05() {
		String signList = "!,#,$,%";
		PasswordValidation pv = new PasswordValidation(signList);
		ValidationFailure vd = null;
		vd = pv.validate("testPassword",
				"0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!#$%(");
		assertNull(vd);
	}

	/**
	 * ����B<br/>
	 * <br/>
	 * 
	 */
	@Test
	public void test06() {
		String signList = "!,#,$,%";
		PasswordValidation pv = new PasswordValidation(signList);
		ValidationFailure vd = null;
		vd = pv.validate("testPassword",
				"0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!#$%");
		assertNull(vd);
	}

	/**
	 * �L����ݒ肵�Ȃ��ꍇ<br/>
	 * <br/>
	 * 
	 */
	@Test
	public void test07() {
		PasswordValidation pv = new PasswordValidation();
		ValidationFailure vd = null;
		vd = pv.validate("testPassword",
				"0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!#$%");
		assertNull(vd);
	}

	/**
	 * �L����ݒ肵�Ȃ��ꍇ�A����̃P�[�X�B<br/>
	 * <br/>
	 * 
	 */
	@Test
	public void test08() {
		PasswordValidation pv = new PasswordValidation();
		ValidationFailure vd = null;
		vd = pv.validate("testPassword",
				"0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
		assertNull(vd);
	}
}
