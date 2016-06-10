package jp.co.transcosmos.dm3.test.core.util;

import static org.junit.Assert.assertNull;

import java.util.HashSet;
import java.util.Set;

import jp.co.transcosmos.dm3.core.util.CreatePassword;
import jp.co.transcosmos.dm3.core.validation.PasswordValidation;

import org.junit.Assert;
import org.junit.Test;

/**
 * ランダムパスワード生成テスト
 * 
 */
public class CreatPasswordTest {

	/**
	 * 記号文字を含めない場合のテストケース<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>桁数が８桁である事</li>
	 *     <li>生成されたパスワードに、英大文字、英小文字、数字が含まれている事）</li>
	 *     <li>100 回繰り返して同じパスワードが生成されない事</li>
	 * </ul>
	 */
	@Test
	public void nonSigntest() {

		System.out.println("記号なしテスト");
		
		Set<String> pwds = new HashSet<>();
		for (int i=0; i<100; ++i){
			CreatePassword cp = new CreatePassword();
			String pwd = cp.getPassword();
			System.out.println(pwd);
			pwds.add(pwd);

			PasswordValidation vd = new PasswordValidation();
			Assert.assertEquals("パスワードの桁数が８桁である事", 8, pwd.length());
			assertNull("生成されたパスワードが要求事項を満たす事", vd.validate("testPassword", pwd));
		}

		Assert.assertEquals("100回繰り返しても重複していない事", 100, pwds.size());
	}


	
	/**
	 * 記号文字として null を設定した場合のテストケース<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>桁数が８桁である事</li>
	 *     <li>生成されたパスワードに、英大文字、英小文字、数字が含まれている事）</li>
	 *     <li>100 回繰り返して同じパスワードが生成されない事</li>
	 * </ul>
	 */
	@Test
	public void nullSigntest() {

		System.out.println("記号にnull指定");

		Set<String> pwds = new HashSet<>();
		for (int i=0; i<100; ++i){
			CreatePassword cp = new CreatePassword();
			cp.setSignList(null);
			String pwd = cp.getPassword();
			System.out.println(pwd);
			pwds.add(pwd);

			PasswordValidation vd = new PasswordValidation();
			Assert.assertEquals("パスワードの桁数が８桁である事", 8, pwd.length());
			assertNull("生成されたパスワードが要求事項を満たす事", vd.validate("testPassword", pwd));
		}

		Assert.assertEquals("100回繰り返しても重複していない事", 100, pwds.size());
	}


	
	/**
	 * 記号文字を含める場合のテストケース<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>桁数が８桁である事</li>
	 *     <li>生成されたパスワードに、英大文字、英小文字、数字、記号が含まれている事）</li>
	 *     <li>100 回繰り返して同じパスワードが生成されない事</li>
	 * </ul>
	 */
	@Test
	public void useSigntest() {

		System.out.println("記号ありテスト");

		String sign="!#$%";
		
		Set<String> pwds = new HashSet<>();
		for (int i=0; i<100; ++i){
			CreatePassword cp = new CreatePassword();
			cp.setSignList(sign);
			String pwd = cp.getPassword();
			pwds.add(pwd);
			System.out.println(pwd);

			PasswordValidation vd = new PasswordValidation(sign);
			Assert.assertEquals("パスワードの桁数が８桁である事", 8, pwd.length());
			assertNull("生成されたパスワードが要求事項を満たす事", vd.validate("testPassword", pwd));	
		}

		Assert.assertEquals("100回繰り返しても重複していない事", 100, pwds.size());
	}

}
