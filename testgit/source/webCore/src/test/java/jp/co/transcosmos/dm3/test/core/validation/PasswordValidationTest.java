package jp.co.transcosmos.dm3.test.core.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import jp.co.transcosmos.dm3.core.validation.PasswordValidation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.junit.Test;


/**
 * <pre>
 * パスワード強度チェック処理テスト用クラス
 * 
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.04	新規作成
 * 
 * 注意事項
 *
 * </pre>
 */
public class PasswordValidationTest {

	/**
	 * 半角数字がない。<br/>
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
	 * 半角英字小文字がない。<br/>
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
	 * 半角英字大文字がない。<br/>
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
	 * 記号がない。<br/>
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
	 * 強制しない記号あり。<br/>
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
	 * 正常。<br/>
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
	 * 記号を設定しない場合<br/>
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
	 * 記号を設定しない場合、正常のケース。<br/>
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
