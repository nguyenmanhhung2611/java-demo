package jp.co.transcosmos.dm3.validation;

import org.junit.Assert;
import org.junit.Test;


/**
 * 半角数字チェックバリデーションのテスト<br/>
 * <br/>
 */
public class NumericValidationTest {

	
	/** テスト対象クラス */
	Validation validation = new NumericValidation();



	/**
	 * 正常系テスト（0 ～ 9 の文字列）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>null が復帰される事（正常終了）</li>
	 * </ul>
	 */
	@Test
	public void validationOkTest1(){
		
		String targetField = "test";
		String targetValue = "0123456789";
		
		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("0 ～ 9 で構成される文字列の場合、戻り値が null である事", null, failure);
	}

	
	
	/**
	 * 正常系テスト（null）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>null が復帰される事（正常終了）</li>
	 * </ul>
	 */
	@Test
	public void validationOkTest2(){
		
		String targetField = "test";
		String targetValue = null;
		
		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("null の場合、戻り値が null である事", null, failure);
	}


	
	/**
	 * 正常系テスト（空文字列）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>null が復帰される事（正常終了）</li>
	 * </ul>
	 */
	@Test
	public void validationOkTest3(){
		
		String targetField = "test";
		String targetValue = "";
		
		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("空文字列の場合、戻り値が null である事", null, failure);
	}


	
	/**
	 * 異常系テスト（全角数字の場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>正しいエラーオブジェクトが復帰される事（フィールド名）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（エラータイプ）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（チェック対象値）</li>
	 * </ul>
	 */
	@Test
	public void validationNgTest1(){
		
		String targetField = "test";
		String targetValue = "012345０6789";
		
		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("全角数字が含まれる場合、エラーオブジェクトが復帰される事", null, failure);
		Assert.assertEquals("全角数字が含まれる場合、正しいエラーオブジェクトが復帰される事（フィールド名）", targetField, failure.getName());
		Assert.assertEquals("全角数字が含まれる場合、正しいエラーオブジェクトが復帰される事（エラータイプ）", "positNumberOnly", failure.getType());
		Assert.assertEquals("全角数字が含まれる場合、正しいエラーオブジェクトが復帰される事（チェック対象値）", targetValue, failure.getValue());

	}



	/**
	 * 異常系テスト（マイナスを含む場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>正しいエラーオブジェクトが復帰される事（フィールド名）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（エラータイプ）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（チェック対象値）</li>
	 * </ul>
	 */
	@Test
	public void validationNgTest2(){
		
		String targetField = "test";
		String targetValue = "-1234567890";
		
		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("マイナスを含む場合、エラーオブジェクトが復帰される事", null, failure);
		Assert.assertEquals("マイナスを含む場合、正しいエラーオブジェクトが復帰される事（フィールド名）", targetField, failure.getName());
		Assert.assertEquals("マイナスを含む場合、正しいエラーオブジェクトが復帰される事（エラータイプ）", "positNumberOnly", failure.getType());
		Assert.assertEquals("マイナスを含む場合、正しいエラーオブジェクトが復帰される事（チェック対象値）", targetValue, failure.getValue());

	}

	
	
	/**
	 * 異常系テスト（ピリオドを含む場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>正しいエラーオブジェクトが復帰される事（フィールド名）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（エラータイプ）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（チェック対象値）</li>
	 * </ul>
	 */
	@Test
	public void validationNgTest3(){
		
		String targetField = "test";
		String targetValue = "123456.7890";
		
		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("ピリオドを含む場合、エラーオブジェクトが復帰される事", null, failure);
		Assert.assertEquals("ピリオドを含む場合、正しいエラーオブジェクトが復帰される事（フィールド名）", targetField, failure.getName());
		Assert.assertEquals("ピリオドを含む場合、正しいエラーオブジェクトが復帰される事（エラータイプ）", "positNumberOnly", failure.getType());
		Assert.assertEquals("ピリオドを含む場合、正しいエラーオブジェクトが復帰される事（チェック対象値）", targetValue, failure.getValue());

	}

}
