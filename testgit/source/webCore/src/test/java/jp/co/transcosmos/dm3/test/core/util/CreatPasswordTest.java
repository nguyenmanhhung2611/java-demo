package jp.co.transcosmos.dm3.test.core.util;

import static org.junit.Assert.assertNull;

import java.util.HashSet;
import java.util.Set;

import jp.co.transcosmos.dm3.core.util.CreatePassword;
import jp.co.transcosmos.dm3.core.validation.PasswordValidation;

import org.junit.Assert;
import org.junit.Test;

/**
 * �����_���p�X���[�h�����e�X�g
 * 
 */
public class CreatPasswordTest {

	/**
	 * �L���������܂߂Ȃ��ꍇ�̃e�X�g�P�[�X<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������W���ł��鎖</li>
	 *     <li>�������ꂽ�p�X���[�h�ɁA�p�啶���A�p�������A�������܂܂�Ă��鎖�j</li>
	 *     <li>100 ��J��Ԃ��ē����p�X���[�h����������Ȃ���</li>
	 * </ul>
	 */
	@Test
	public void nonSigntest() {

		System.out.println("�L���Ȃ��e�X�g");
		
		Set<String> pwds = new HashSet<>();
		for (int i=0; i<100; ++i){
			CreatePassword cp = new CreatePassword();
			String pwd = cp.getPassword();
			System.out.println(pwd);
			pwds.add(pwd);

			PasswordValidation vd = new PasswordValidation();
			Assert.assertEquals("�p�X���[�h�̌������W���ł��鎖", 8, pwd.length());
			assertNull("�������ꂽ�p�X���[�h���v�������𖞂�����", vd.validate("testPassword", pwd));
		}

		Assert.assertEquals("100��J��Ԃ��Ă��d�����Ă��Ȃ���", 100, pwds.size());
	}


	
	/**
	 * �L�������Ƃ��� null ��ݒ肵���ꍇ�̃e�X�g�P�[�X<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������W���ł��鎖</li>
	 *     <li>�������ꂽ�p�X���[�h�ɁA�p�啶���A�p�������A�������܂܂�Ă��鎖�j</li>
	 *     <li>100 ��J��Ԃ��ē����p�X���[�h����������Ȃ���</li>
	 * </ul>
	 */
	@Test
	public void nullSigntest() {

		System.out.println("�L����null�w��");

		Set<String> pwds = new HashSet<>();
		for (int i=0; i<100; ++i){
			CreatePassword cp = new CreatePassword();
			cp.setSignList(null);
			String pwd = cp.getPassword();
			System.out.println(pwd);
			pwds.add(pwd);

			PasswordValidation vd = new PasswordValidation();
			Assert.assertEquals("�p�X���[�h�̌������W���ł��鎖", 8, pwd.length());
			assertNull("�������ꂽ�p�X���[�h���v�������𖞂�����", vd.validate("testPassword", pwd));
		}

		Assert.assertEquals("100��J��Ԃ��Ă��d�����Ă��Ȃ���", 100, pwds.size());
	}


	
	/**
	 * �L���������܂߂�ꍇ�̃e�X�g�P�[�X<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������W���ł��鎖</li>
	 *     <li>�������ꂽ�p�X���[�h�ɁA�p�啶���A�p�������A�����A�L�����܂܂�Ă��鎖�j</li>
	 *     <li>100 ��J��Ԃ��ē����p�X���[�h����������Ȃ���</li>
	 * </ul>
	 */
	@Test
	public void useSigntest() {

		System.out.println("�L������e�X�g");

		String sign="!#$%";
		
		Set<String> pwds = new HashSet<>();
		for (int i=0; i<100; ++i){
			CreatePassword cp = new CreatePassword();
			cp.setSignList(sign);
			String pwd = cp.getPassword();
			pwds.add(pwd);
			System.out.println(pwd);

			PasswordValidation vd = new PasswordValidation(sign);
			Assert.assertEquals("�p�X���[�h�̌������W���ł��鎖", 8, pwd.length());
			assertNull("�������ꂽ�p�X���[�h���v�������𖞂�����", vd.validate("testPassword", pwd));	
		}

		Assert.assertEquals("100��J��Ԃ��Ă��d�����Ă��Ȃ���", 100, pwds.size());
	}

}
