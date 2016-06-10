package jp.co.transcosmos.dm3.validation;

import org.junit.Assert;
import org.junit.Test;

/**
 * 半角数字チェック、および、整数部、小数部の桁数を含む場合のテストケース<br/>
 * <br/>
 */
public class DecimalValidationTest {

	// テスト対象クラス
	private Validation validation = new DecimalValidation(8, 3);
	
	
	
	/**
	 * 正常系テスト（整数部５桁、小数部３桁）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>null が復帰される事（正常終了）</li>
	 * </ul>
	 */
	@Test
	public void validationOkTest1(){
		
		String targetField = "test";
		String targetValue = "12345.123";

		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("整数部、少数部の桁数を満たす半角小数文字列の場合、戻り値が null である事", null, failure);
	}

	
	
	/**
	 * 正常系テスト（整数部５桁のみ）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>null が復帰される事（正常終了）</li>
	 * </ul>
	 */
	@Test
	public void validationOkTest2(){
		
		String targetField = "test";
		String targetValue = "12345";

		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("整数部のみの半角小数文字列の場合、戻り値が null である事", null, failure);
	}



	/**
	 * 正常系テスト（ピリオドから始まる小数部３桁）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>null が復帰される事（正常終了）</li>
	 * </ul>
	 */
	@Test
	public void validationOkTest3(){
		
		String targetField = "test";
		String targetValue = ".345";

		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("小数部のみの半角小数文字列の場合、戻り値が null である事", null, failure);
	}


	
	/**
	 * 正常系テスト（null の場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>null が復帰される事（正常終了）</li>
	 * </ul>
	 */
	@Test
	public void validationOkTest4(){
		
		String targetField = "test";
		String targetValue = null;

		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("null の場合、戻り値が null である事", null, failure);
	}

	
	
	/**
	 * 正常系テスト（空文字列の場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>null が復帰される事（正常終了）</li>
	 * </ul>
	 */
	@Test
	public void validationOkTest5(){
		
		String targetField = "test";
		String targetValue = "";

		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("空の場合、戻り値が null である事", null, failure);
	}



	/**
	 * 異常系テスト（ピリオドのみ）<br/>
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
		String targetValue = ".";

		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("ピリオドのみの場合、エラーオブジェクトが復帰される事", null, failure);
		Assert.assertEquals("ピリオドのみの場合、正しいエラーオブジェクトが復帰される事（フィールド名）", targetField, failure.getName());
		Assert.assertEquals("ピリオドのみの場合、正しいエラーオブジェクトが復帰される事（エラータイプ）", "decimal", failure.getType());
		Assert.assertEquals("ピリオドのみの場合、正しいエラーオブジェクトが復帰される事（チェック対象値）", targetValue, failure.getValue());
	}


	
	/**
	 * 異常系テスト（ピリオドとスペース）<br/>
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
		String targetValue = "  .  ";

		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("ピリオドとその前後にスペースのみの場合、エラーオブジェクトが復帰される事", null, failure);
		Assert.assertEquals("ピリオドとその前後にスペースのみの場合、正しいエラーオブジェクトが復帰される事（フィールド名）", targetField, failure.getName());
		Assert.assertEquals("ピリオドとその前後にスペースのみの場合、正しいエラーオブジェクトが復帰される事（エラータイプ）", "decimal", failure.getType());
		Assert.assertEquals("ピリオドとその前後にスペースのみの場合、正しいエラーオブジェクトが復帰される事（チェック対象値）", targetValue, failure.getValue());

		
		targetValue = "  .";

		// バリデーション実行
		failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("ピリオドとその前にスペースのみの場合、エラーオブジェクトが復帰される事", null, failure);
		Assert.assertEquals("ピリオドとその前にスペースのみの場合、正しいエラーオブジェクトが復帰される事（フィールド名）", targetField, failure.getName());
		Assert.assertEquals("ピリオドとその前にスペースのみの場合、正しいエラーオブジェクトが復帰される事（エラータイプ）", "decimal", failure.getType());
		Assert.assertEquals("ピリオドとその前にスペースのみの場合、正しいエラーオブジェクトが復帰される事（チェック対象値）", targetValue, failure.getValue());
		
		
		targetValue = ".   ";

		// バリデーション実行
		failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("ピリオドとその後ろにスペースのみの場合、エラーオブジェクトが復帰される事", null, failure);
		Assert.assertEquals("ピリオドとその後ろにスペースのみの場合、正しいエラーオブジェクトが復帰される事（フィールド名）", targetField, failure.getName());
		Assert.assertEquals("ピリオドとその後ろにスペースのみの場合、正しいエラーオブジェクトが復帰される事（エラータイプ）", "decimal", failure.getType());
		Assert.assertEquals("ピリオドとその後ろにスペースのみの場合、正しいエラーオブジェクトが復帰される事（チェック対象値）", targetValue, failure.getValue());
		
	}



	/**
	 * 異常系テスト（整数部のみで桁数オーバー）<br/>
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
		String targetValue = "123456";

		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("整数部のみで桁数オーバーの場合、エラーオブジェクトが復帰される事", null, failure);
		Assert.assertEquals("整数部のみで桁数オーバーの場合、正しいエラーオブジェクトが復帰される事（フィールド名）", targetField, failure.getName());
		Assert.assertEquals("整数部のみで桁数オーバーの場合、正しいエラーオブジェクトが復帰される事（エラータイプ）", "decimal", failure.getType());
		Assert.assertEquals("整数部のみで桁数オーバーの場合、正しいエラーオブジェクトが復帰される事（チェック対象値）", targetValue, failure.getValue());
	}


	
	/**
	 * 異常系テスト（ピリオドから始まる小数部で桁数オーバー）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>正しいエラーオブジェクトが復帰される事（フィールド名）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（エラータイプ）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（チェック対象値）</li>
	 * </ul>
	 */
	@Test
	public void validationNgTest4(){
		
		String targetField = "test";
		String targetValue = ".1234";

		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("ピリオドから始まる小数部で桁数オーバーの場合、エラーオブジェクトが復帰される事", null, failure);
		Assert.assertEquals("ピリオドから始まる小数部で桁数オーバーの場合、正しいエラーオブジェクトが復帰される事（フィールド名）", targetField, failure.getName());
		Assert.assertEquals("ピリオドから始まる小数部で桁数オーバーの場合、正しいエラーオブジェクトが復帰される事（エラータイプ）", "decimal", failure.getType());
		Assert.assertEquals("ピリオドから始まる小数部で桁数オーバーの場合、正しいエラーオブジェクトが復帰される事（チェック対象値）", targetValue, failure.getValue());
	}



	/**
	 * 異常系テスト（整数部、小数部で構成され、整数部の桁数オーバー）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>正しいエラーオブジェクトが復帰される事（フィールド名）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（エラータイプ）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（チェック対象値）</li>
	 * </ul>
	 */
	@Test
	public void validationNgTest5(){
		
		String targetField = "test";
		String targetValue = "123456.123";

		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("整数部、小数部で構成され、整数部の桁数オーバーの場合、エラーオブジェクトが復帰される事", null, failure);
		Assert.assertEquals("整数部、小数部で構成され、整数部の桁数オーバーの場合、正しいエラーオブジェクトが復帰される事（フィールド名）", targetField, failure.getName());
		Assert.assertEquals("整数部、小数部で構成され、整数部の桁数オーバーの場合、正しいエラーオブジェクトが復帰される事（エラータイプ）", "decimal", failure.getType());
		Assert.assertEquals("整数部、小数部で構成され、整数部の桁数オーバーの場合、正しいエラーオブジェクトが復帰される事（チェック対象値）", targetValue, failure.getValue());
	}

	
	
	/**
	 * 異常系テスト（整数部、小数部で構成され、小数の桁数オーバー）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>正しいエラーオブジェクトが復帰される事（フィールド名）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（エラータイプ）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（チェック対象値）</li>
	 * </ul>
	 */
	@Test
	public void validationNgTest6(){
		
		String targetField = "test";
		String targetValue = "12345.1234";

		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("整数部、小数部で構成され、小数部の桁数オーバーの場合、エラーオブジェクトが復帰される事", null, failure);
		Assert.assertEquals("整数部、小数部で構成され、小数部の桁数オーバーの場合、正しいエラーオブジェクトが復帰される事（フィールド名）", targetField, failure.getName());
		Assert.assertEquals("整数部、小数部で構成され、小数部の桁数オーバーの場合、正しいエラーオブジェクトが復帰される事（エラータイプ）", "decimal", failure.getType());
		Assert.assertEquals("整数部、小数部で構成され、小数部の桁数オーバーの場合、正しいエラーオブジェクトが復帰される事（チェック対象値）", targetValue, failure.getValue());
	}

	
	
	/**
	 * 異常系テスト（半角英字を含む）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>正しいエラーオブジェクトが復帰される事（フィールド名）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（エラータイプ）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（チェック対象値）</li>
	 * </ul>
	 */
	@Test
	public void validationNgTest7(){
		
		String targetField = "test";
		String targetValue = "1a25.14";

		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("半角英字を含む場合、エラーオブジェクトが復帰される事", null, failure);
		Assert.assertEquals("半角英字を含む場合、正しいエラーオブジェクトが復帰される事（フィールド名）", targetField, failure.getName());
		Assert.assertEquals("半角英字を含む場合、正しいエラーオブジェクトが復帰される事（エラータイプ）", "decimal", failure.getType());
		Assert.assertEquals("半角英字を含む場合、正しいエラーオブジェクトが復帰される事（チェック対象値）", targetValue, failure.getValue());
	}


	
	/**
	 * 異常系テスト（全角数字を含む）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>正しいエラーオブジェクトが復帰される事（フィールド名）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（エラータイプ）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（チェック対象値）</li>
	 * </ul>
	 */
	@Test
	public void validationNgTest8(){
		
		String targetField = "test";
		String targetValue = "125.９1";

		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("全角数字を含む場合、エラーオブジェクトが復帰される事", null, failure);
		Assert.assertEquals("全角数字を含む場合、正しいエラーオブジェクトが復帰される事（フィールド名）", targetField, failure.getName());
		Assert.assertEquals("全角数字を含む場合、正しいエラーオブジェクトが復帰される事（エラータイプ）", "decimal", failure.getType());
		Assert.assertEquals("全角数字を含む場合、正しいエラーオブジェクトが復帰される事（チェック対象値）", targetValue, failure.getValue());
	}

}
