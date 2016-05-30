package jp.co.transcosmos.dm3.validation;

import org.junit.Assert;
import org.junit.Test;

/**
 * 半角英数記号文字のバリデーションテスト<br/>
 * <br/>
 */
public class AsciiOnlyValidationTest {

	/** テスト対象クラス */
	Validation validation = new AsciiOnlyValidation();

	
	/**
	 * 正常系のテスト（半角英小文字）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>null が復帰される事（正常終了）</li>
	 * </ul>
	 */
	@Test
	public void mailValidateOKTest1() {
		
		String targetField = "test";
		String targetValue = "abcdefghijklmnopqrstuvwxyz";
		
		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("半角英小文字の場合、戻り値が null である事", null, failure);

	}


	/**
	 * 正常系のテスト（半角英大文字）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>null が復帰される事（正常終了）</li>
	 * </ul>
	 */
	@Test
	public void mailValidateOKTest2() {
		
		String targetField = "test";
		String targetValue = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("半角英大文字の場合、戻り値が null である事", null, failure);

	}


	/**
	 * 正常系のテスト（半角数字）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>null が復帰される事（正常終了）</li>
	 * </ul>
	 */
	@Test
	public void mailValidateOKTest3() {
		
		String targetField = "test";
		String targetValue = "0123456789";
		
		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("半角数字の場合、戻り値が null である事", null, failure);

	}

	
	/**
	 * 正常系のテスト（半角記号）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>null が復帰される事（正常終了）</li>
	 * </ul>
	 */
	@Test
	public void mailValidateOKTest4() {
		
		String targetField = "test";
		String targetValue = "!\"#$%&'()=-~^|\\`@{[+;*:}]<>,.?/_";
		
		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("半角記号の場合、戻り値が null である事", null, failure);

	}

	
	
	/**
	 * 正常系のテスト（null の場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>null が復帰される事（正常終了）</li>
	 * </ul>
	 */
	@Test
	public void mailValidateOKTest5() {
		
		String targetField = "test";
		String targetValue = null;
		
		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("null の場合、戻り値が null である事", null, failure);

	}

	

	/**
	 * 正常系のテスト（空文字列の場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>null が復帰される事（正常終了）</li>
	 * </ul>
	 */
	@Test
	public void mailValidateOKTest6() {
		
		String targetField = "test";
		String targetValue = "";
		
		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("空文字列の場合、戻り値が null である事", null, failure);

	}


	
	/**
	 * 異常系のテスト（全角数字を含む場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>正しいエラーオブジェクトが復帰される事</li>
	 * </ul>
	 */
	@Test
	public void mailValidateNGTest1() {
		
		String targetField = "test";
		String targetValue = "agc１dedg";
		
		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("全角数字が複数ある場合、エラーオブジェクトが復帰される事", null, failure);
		Assert.assertEquals("全角数字が複数ある場合、正しいエラーオブジェクトが復帰される事（フィールド名）", targetField, failure.getName());
		Assert.assertEquals("全角数字が複数ある場合、正しいエラーオブジェクトが復帰される事（エラータイプ）", "asciiOnly", failure.getType());
		Assert.assertEquals("全角数字が複数ある場合、正しいエラーオブジェクトが復帰される事（チェック対象値）", targetValue, failure.getValue());

	}

	
	
	/**
	 * 異常系のテスト（全角漢字を含む場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>正しいエラーオブジェクトが復帰される事</li>
	 * </ul>
	 */
	@Test
	public void mailValidateNGTest2() {
		
		String targetField = "test";
		String targetValue = "agc漢字dedg";
		
		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("全角漢字が複数ある場合、エラーオブジェクトが復帰される事", null, failure);
		Assert.assertEquals("全角漢字が複数ある場合、正しいエラーオブジェクトが復帰される事（フィールド名）", targetField, failure.getName());
		Assert.assertEquals("全角漢字が複数ある場合、正しいエラーオブジェクトが復帰される事（エラータイプ）", "asciiOnly", failure.getType());
		Assert.assertEquals("全角漢字が複数ある場合、正しいエラーオブジェクトが復帰される事（チェック対象値）", targetValue, failure.getValue());

	}

}
