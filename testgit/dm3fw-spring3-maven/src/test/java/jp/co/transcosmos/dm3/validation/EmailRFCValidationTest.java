package jp.co.transcosmos.dm3.validation;

import org.junit.Assert;
import org.junit.Test;

/**
 * RFC 対応メールアドレスバリデーションのテスト<br/>
 * <br/>
 */
public class EmailRFCValidationTest {

	/** テスト対象クラス */
	Validation validation = new EmailRFCValidation();


	
	
	/**
	 * 正常メールアドレスのテスト<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>null が復帰される事（正常終了）</li>
	 * </ul>
	 */
	@Test
	public void mailValidateOKTest1() {
		
		String targetField = "test";
		String targetValue = "Mizuno.Hiroyuki@trans-cosmos.co.jp";
		
		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("正常なメールアドレスの場合、戻り値が null である事", null, failure);

	}
	

	/**
	 * ユーザー部の先頭がピリオドの場合<br/>
	 * ※フレームワークの設定で、ダブルコートでエスケープ可能な為、正常値として扱う<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>null が復帰される事（正常終了）</li>
	 * </ul>
	 */
	@Test
	public void mailValidateOKTest2() {

		String targetField = "test";
		String targetValue = ".mizuno.hiroyuki@trans.cosmos.co.jp";
		
		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("ユーザー部の先頭がピリオドの場合、戻り値が null である事", null, failure);

	}

	
	
	/**
	 * ユーザー部の末尾がピリオドの場合<br/>
	 * ※フレームワークの設定で、ダブルコートでエスケープ可能な為、正常値として扱う<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>null が復帰される事（正常終了）</li>
	 * </ul>
	 */
	@Test
	public void mailValidateOKTest3() {

		String targetField = "test";
		String targetValue = "mizuno.hiroyuki.@trans.cosmos.co.jp";
		
		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("ユーザー部の先頭がピリオドの場合、戻り値が null である事", null, failure);

	}

	
	
	/**
	 * ユーザー部のピリオドが連続している場合<br/>
	 * ※フレームワークの設定で、ダブルコートでエスケープ可能な為、正常値として扱う<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>null が復帰される事（正常終了）</li>
	 * </ul>
	 */
	@Test
	public void mailValidateOKTest4() {

		String targetField = "test";
		String targetValue = "mizuno..hiroyuki.@trans.cosmos.co.jp";
		
		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertEquals("ユーザー部の先頭がピリオドの場合、戻り値が null である事", null, failure);

	}

	
	
	/**
	 * ユーザー部で、禁止されていない記号文字が使用された場合<br/>
	 * ※禁止対象は、[、]、(、)、;、,、:、\\、\"、<、> <br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>null が復帰される事（正常終了）</li>
	 * </ul>
	 */
	@Test
	public void mailValidateOKTest5() {

		// ハイフン、ピリオドを除く記号文字
		String okChars[]
				= {"!",  "#", "$",  "%", "&",  "'",
				   "=",  "~",  "^", "|", "`",  "{",
				   "}", "*", "+", "?", "/", ".", "-"};
		
		String targetField = "test";
		
		for (String okChar : okChars) {
	
			String targetValue = "mizuno" + okChar + "hiroyuki@trans.cosmos.co.jp";
			
			// バリデーション実行
			ValidationFailure failure = validation.validate(targetField, targetValue);

			Assert.assertEquals("ユーザー部で、禁止されていない記号文字が使用された場合、戻り値が null である事(" + okChar + ")", null, failure);
		}
	}



	/**
	 * @ が無いメールアドレスの場合<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>正しいエラーオブジェクトが復帰される事（フィールド名）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（エラータイプ）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（チェック対象値）</li>
	 * </ul>
	 */
	@Test
	public void mailValidateNGTest1() {
		
		String targetField = "test";
		String targetValue = "trans-cosmos.co.jp";
		
		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("@ が無い場合、エラーオブジェクトが復帰される事", null, failure);
		Assert.assertEquals("@ が無い場合、正しいエラーオブジェクトが復帰される事（フィールド名）", targetField, failure.getName());
		Assert.assertEquals("@ が無い場合、正しいエラーオブジェクトが復帰される事（エラータイプ）", "email", failure.getType());
		Assert.assertEquals("@ が無い場合、正しいエラーオブジェクトが復帰される事（チェック対象値）", targetValue, failure.getValue());

	}


	
	/**
	 * @ が２個あるメールアドレスの場合<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>正しいエラーオブジェクトが復帰される事（フィールド名）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（エラータイプ）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（チェック対象値）</li>
	 * </ul>
	 */
	@Test
	public void mailValidateNGTest2() {

		String targetField = "test";
		String targetValue = "mizuno@hiroyuki@trans-cosmos.co.jp";
		
		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("@ が複数ある場合、エラーオブジェクトが復帰される事", null, failure);
		Assert.assertEquals("@ が複数ある場合、正しいエラーオブジェクトが復帰される事（フィールド名）", targetField, failure.getName());
		Assert.assertEquals("@ が複数ある場合、正しいエラーオブジェクトが復帰される事（エラータイプ）", "email", failure.getType());
		Assert.assertEquals("@ が複数ある場合、正しいエラーオブジェクトが復帰される事（チェック対象値）", targetValue, failure.getValue());

	}

	
	
	/**
	 * ドメイン部にピリオド、ハイフン以外の記号文字、およびブランクがある場合<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>正しいエラーオブジェクトが復帰される事（フィールド名）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（エラータイプ）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（チェック対象値）</li>
	 * </ul>
	 */
	@Test
	public void mailValidateNGTest3() {

		String targetField = "test";

		// ハイフン、ピリオドを除く記号文字
		String badChars[]
				= {"!",  "\"", "#", "$",  "%", "&",  "'",  "(", ")",
				   "=",  "~",  "^", "\\", "|", "`",  "[",  "]", "{",
				   "}", ";", ":", "*", "+", ",", "?", "/", "<", ">",
				   " "};

		for (String badChar : badChars){
			String targetValue = "mizuno.hiroyuki@trans" + badChar + "cosmos.co.jp";

			// バリデーション実行
			ValidationFailure failure = validation.validate(targetField, targetValue);

			Assert.assertNotEquals("ドメイン部にピリオド、ハイフン以外の記号文字、およびブランクがある場合、エラーオブジェクトが復帰される事(" + badChar +")", null, failure);
			Assert.assertEquals("ドメイン部にピリオド、ハイフン以外の記号文字、およびブランクがある場合、正しいエラーオブジェクトが復帰される事（フィールド名）", targetField, failure.getName());
			Assert.assertEquals("ドメイン部にピリオド、ハイフン以外の記号文字、およびブランクがある場合、正しいエラーオブジェクトが復帰される事（エラータイプ）", "email", failure.getType());
			Assert.assertEquals("ドメイン部にピリオド、ハイフン以外の記号文字、およびブランクがある場合、正しいエラーオブジェクトが復帰される事（チェック対象値）", targetValue, failure.getValue());

		}
	}

	

	/**
	 * ドメイン部に全角文字がある場合<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>正しいエラーオブジェクトが復帰される事（フィールド名）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（エラータイプ）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（チェック対象値）</li>
	 * </ul>
	 */
	@Test
	public void mailValidateNGTest4() {

		String targetField = "test";
		String targetValue = "mizuno@hiroyuki@transあcosmos.co.jp";
		
		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("ドメイン部に全角文字がある場合、エラーオブジェクトが復帰される事", null, failure);
		Assert.assertEquals("ドメイン部に全角文字がある場合、正しいエラーオブジェクトが復帰される事（フィールド名）", targetField, failure.getName());
		Assert.assertEquals("ドメイン部に全角文字がある場合、正しいエラーオブジェクトが復帰される事（エラータイプ）", "email", failure.getType());
		Assert.assertEquals("ドメイン部に全角文字がある場合、正しいエラーオブジェクトが復帰される事（チェック対象値）", targetValue, failure.getValue());

	}

	
	
	/**
	 * ドメイン部でピリオドが連続している場合<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>正しいエラーオブジェクトが復帰される事（フィールド名）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（エラータイプ）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（チェック対象値）</li>
	 * </ul>
	 */
	@Test
	public void mailValidateNGTest5() {

		String targetField = "test";
		String targetValue = "mizuno@hiroyuki@trans..cosmos.co.jp";
		
		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("ドメイン部でピリオドが連続している場合、エラーオブジェクトが復帰される事", null, failure);
		Assert.assertEquals("ドメイン部でピリオドが連続している場合、正しいエラーオブジェクトが復帰される事（フィールド名）", targetField, failure.getName());
		Assert.assertEquals("ドメイン部でピリオドが連続している場合、正しいエラーオブジェクトが復帰される事（エラータイプ）", "email", failure.getType());
		Assert.assertEquals("ドメイン部でピリオドが連続している場合、正しいエラーオブジェクトが復帰される事（チェック対象値）", targetValue, failure.getValue());

	}
	

	
	/**
	 * ドメイン部の先頭がピリオドの場合<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>正しいエラーオブジェクトが復帰される事（フィールド名）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（エラータイプ）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（チェック対象値）</li>
	 * </ul>
	 */
	@Test
	public void mailValidateNGTest6() {

		String targetField = "test";
		String targetValue = "mizuno@.hiroyuki@trans.cosmos.co.jp";
		
		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("ドメイン部の先頭がピリオドの場合、エラーオブジェクトが復帰される事", null, failure);
		Assert.assertEquals("ドメイン部の先頭がピリオドの場合、正しいエラーオブジェクトが復帰される事（フィールド名）", targetField, failure.getName());
		Assert.assertEquals("ドメイン部の先頭がピリオドの場合、正しいエラーオブジェクトが復帰される事（エラータイプ）", "email", failure.getType());
		Assert.assertEquals("ドメイン部の先頭がピリオドの場合、正しいエラーオブジェクトが復帰される事（チェック対象値）", targetValue, failure.getValue());

	}


	
	/**
	 * ドメイン部の末尾がピリオドの場合<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>正しいエラーオブジェクトが復帰される事（フィールド名）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（エラータイプ）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（チェック対象値）</li>
	 * </ul>
	 */
	@Test
	public void mailValidateNGTest7() {

		String targetField = "test";
		String targetValue = "mizuno@hiroyuki@trans.cosmos.co.jp.";
		
		// バリデーション実行
		ValidationFailure failure = validation.validate(targetField, targetValue);

		Assert.assertNotEquals("ドメイン部の先頭がピリオドの場合、エラーオブジェクトが復帰される事", null, failure);
		Assert.assertEquals("ドメイン部の先頭がピリオドの場合、正しいエラーオブジェクトが復帰される事（フィールド名）", targetField, failure.getName());
		Assert.assertEquals("ドメイン部の先頭がピリオドの場合、正しいエラーオブジェクトが復帰される事（エラータイプ）", "email", failure.getType());
		Assert.assertEquals("ドメイン部の先頭がピリオドの場合、正しいエラーオブジェクトが復帰される事（チェック対象値）", targetValue, failure.getValue());

	}

	

	/**
	 * ユーザー部で使用禁止記号文字、および、ブランクが使用された場合<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>正しいエラーオブジェクトが復帰される事（フィールド名）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（エラータイプ）</li>
	 *     <li>正しいエラーオブジェクトが復帰される事（チェック対象値）</li>
	 * </ul>
	 */
	@Test
	public void mailValidateNGTest8() {

		// ユーザー部の使用禁止文字
		String badChars[]
				= {"(", ")", "<", ">", "[", "]", ":", ";", ",", "\\", "\"", " "};

		
		String targetField = "test";
		
		for (String badChar : badChars) {

			String targetValue = "mizuno" + badChar + "hiroyuki@trans.cosmos.co.jp";			

			// バリデーション実行
			ValidationFailure failure = validation.validate(targetField, targetValue);

			Assert.assertNotEquals("ユーザー部で使用禁止記号文字、および、ブランクが使用された場合、エラーオブジェクトが復帰される事(" + badChar + ")", null, failure);
			Assert.assertEquals("ユーザー部で使用禁止記号文字、および、ブランクが使用された場合、正しいエラーオブジェクトが復帰される事（フィールド名）", targetField, failure.getName());
			Assert.assertEquals("ユーザー部で使用禁止記号文字、および、ブランクが使用された場合、正しいエラーオブジェクトが復帰される事（エラータイプ）", "email", failure.getType());
			Assert.assertEquals("ユーザー部で使用禁止記号文字、および、ブランクが使用された場合、正しいエラーオブジェクトが復帰される事（チェック対象値）", targetValue, failure.getValue());
		}
	}
}
