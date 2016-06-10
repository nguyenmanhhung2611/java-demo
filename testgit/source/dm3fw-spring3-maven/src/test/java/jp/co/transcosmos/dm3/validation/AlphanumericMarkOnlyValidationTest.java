package jp.co.transcosmos.dm3.validation;

import org.junit.Assert;
import org.junit.Test;

/**
 * 半角英数字、または許可された記号文字のバリデーションテスト<br/>
 * <br/>
 */
public class AlphanumericMarkOnlyValidationTest {

	/** 許可される記号文字列 */
	private static final String OK_MARKS = "[@;:";
	
	/** テスト対象クラス */
	private Validation validation = new AlphanumericMarkOnlyValidation(OK_MARKS);
	private Validation nullValidation = new AlphanumericMarkOnlyValidation(null);
	
	
	
	/**
	 * 正常系のテスト（半角英小文字）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>null が復帰される事（正常終了）</li>
	 * </ul>
	 */
	@Test
	public void validateOKTest1() {
		
		String targetField = "test";
		String targetValue = "abcdefghijklmnopqrstuvwxyz";
		
		// バリデーション実行
		ValidationFailure failure = this.validation.validate(targetField, targetValue);

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
	public void validateOKTest2() {
		
		String targetField = "test";
		String targetValue = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		// バリデーション実行
		ValidationFailure failure = this.validation.validate(targetField, targetValue);

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
	public void validateOKTest3() {
		
		String targetField = "test";
		String targetValue = "0123456789";
		
		// バリデーション実行
		ValidationFailure failure = this.validation.validate(targetField, targetValue);

		Assert.assertEquals("半角数字の場合、戻り値が null である事", null, failure);

	}

	
	/**
	 * 正常系のテスト（許可された記号文字の場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>null が復帰される事（正常終了）</li>
	 * </ul>
	 */
	@Test
	public void validateOKTest4() {
		
		String targetField = "test";
		String targetValue = OK_MARKS + OK_MARKS;

		// バリデーション実行
		ValidationFailure failure = this.validation.validate(targetField, targetValue);

		Assert.assertEquals("許可された記号文字の場合、戻り値が null である事", null, failure);

	}


	/**
	 * 正常系のテスト（nullの場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>null が復帰される事（正常終了）</li>
	 * </ul>
	 */
	@Test
	public void validateOKTest5() {
		
		String targetField = "test";
		String targetValue = null;

		// バリデーション実行
		ValidationFailure failure = this.validation.validate(targetField, targetValue);

		Assert.assertEquals("nullの場合、戻り値が null である事", null, failure);

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
	public void validateOKTest6() {
		
		String targetField = "test";
		String targetValue = "";

		// バリデーション実行
		ValidationFailure failure = this.validation.validate(targetField, targetValue);

		Assert.assertEquals("空文字列の場合、戻り値が null である事", null, failure);

	}

	
	/**
	 * 正常系のテスト（許可文字列が null で半角英小文字の場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>null が復帰される事（正常終了）</li>
	 * </ul>
	 */
	@Test
	public void nullParamValidateOKTest1() {
		
		String targetField = "test";
		String targetValue = "abcdefghijklmnopqrstuvwxyz";

		// バリデーション実行
		ValidationFailure failure = this.nullValidation.validate(targetField, targetValue);

		Assert.assertEquals("許可文字列が null でチェック対象が半角英小文字の場合、戻り値が null である事", null, failure);

	}

	
	/**
	 * 正常系のテスト（許可文字列が null で半角英大文字の場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>null が復帰される事（正常終了）</li>
	 * </ul>
	 */
	@Test
	public void nullParamValidateOKTest2() {
		
		String targetField = "test";
		String targetValue = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		// バリデーション実行
		ValidationFailure failure = this.nullValidation.validate(targetField, targetValue);

		Assert.assertEquals("許可文字列が null でチェック対象が半角英大文字の場合、戻り値が null である事", null, failure);

	}


	/**
	 * 正常系のテスト（許可文字列が null で半角数字の場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>null が復帰される事（正常終了）</li>
	 * </ul>
	 */
	@Test
	public void nullParamValidateOKTest3() {
		
		String targetField = "test";
		String targetValue = "0123456789";

		// バリデーション実行
		ValidationFailure failure = this.nullValidation.validate(targetField, targetValue);

		Assert.assertEquals("許可文字列が null でチェック対象が半角数字の場合、戻り値が null である事", null, failure);

	}

	
	/**
	 * 異常系のテスト（許可されていない半角記号）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>正しいエラーオブジェクトが復帰される事</li>
	 * </ul>
	 */
	@Test
	public void validateNGTest1() {
		
		String targetField = "test";
		String targetValue = "!\"#$%&'()=-~^|\\`{+*}]<>,.?/_";
		
		// バリデーション実行
		ValidationFailure failure = this.validation.validate(targetField, targetValue);

		Assert.assertNotEquals("許可されていない半角記号がある場合、エラーオブジェクトが復帰される事", null, failure);
		Assert.assertEquals("許可されていない半角記号がある場合、正しいエラーオブジェクトが復帰される事（フィールド名）", targetField, failure.getName());
		Assert.assertEquals("許可されていない半角記号がある場合、正しいエラーオブジェクトが復帰される事（エラータイプ）", "aplhaNumMarkOnly", failure.getType());
		Assert.assertEquals("許可されていない半角記号がある場合、正しいエラーオブジェクトが復帰される事（チェック対象値）", targetValue, failure.getValue());
		Assert.assertEquals("許可されていない半角記号がある場合、正しいエラーオブジェクトが復帰される事（許可文字列）", OK_MARKS, failure.getExtraInfo()[0]);
		
	}

	
	/**
	 * 異常系のテスト（全角記号）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>正しいエラーオブジェクトが復帰される事</li>
	 * </ul>
	 */
	@Test
	public void validateNGTest2() {
		
		String targetField = "test";
		String targetValue = "[＠;:";
		
		// バリデーション実行
		ValidationFailure failure = this.validation.validate(targetField, targetValue);

		Assert.assertNotEquals("全角記号がある場合、エラーオブジェクトが復帰される事", null, failure);
		Assert.assertEquals("全角記号がある場合、正しいエラーオブジェクトが復帰される事（フィールド名）", targetField, failure.getName());
		Assert.assertEquals("全角記号がある場合、正しいエラーオブジェクトが復帰される事（エラータイプ）", "aplhaNumMarkOnly", failure.getType());
		Assert.assertEquals("全角記号がある場合、正しいエラーオブジェクトが復帰される事（チェック対象値）", targetValue, failure.getValue());
		Assert.assertEquals("全角記号がある場合、正しいエラーオブジェクトが復帰される事（許可文字列）", OK_MARKS, failure.getExtraInfo()[0]);
		
	}

	
	/**
	 * 正常系のテスト（許可文字列が null で半角記号文字の場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>正しいエラーオブジェクトが復帰される事</li>
	 * </ul>
	 */
	@Test
	public void nullParamValidateNGTest1() {
		
		String targetField = "test";
		String targetValue = "!\"#$%&'()=-~^|\\`@{[+;*:}]<>,.?/_";

		// バリデーション実行
		ValidationFailure failure = this.nullValidation.validate(targetField, targetValue);

		Assert.assertNotEquals("半角記号がある場合、エラーオブジェクトが復帰される事", null, failure);
		Assert.assertEquals("半角記号がある場合、正しいエラーオブジェクトが復帰される事（フィールド名）", targetField, failure.getName());
		Assert.assertEquals("半角記号がある場合、正しいエラーオブジェクトが復帰される事（エラータイプ）", "alphanumericOnly", failure.getType());
		Assert.assertEquals("半角記号がある場合、正しいエラーオブジェクトが復帰される事（チェック対象値）", targetValue, failure.getValue());
		Assert.assertNull("半角記号がある場合、正しいエラーオブジェクトが復帰される事（許可文字列）", failure.getExtraInfo());

	}

	

}
