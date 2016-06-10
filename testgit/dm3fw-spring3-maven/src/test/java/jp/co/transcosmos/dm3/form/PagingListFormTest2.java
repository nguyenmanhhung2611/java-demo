package jp.co.transcosmos.dm3.form;

import jp.co.transcosmos.mock.form.ArrayForm1;
import jp.co.transcosmos.mock.form.ArrayForm2;
import jp.co.transcosmos.mock.form.DupForm2;
import jp.co.transcosmos.mock.form.SingleForm1;
import jp.co.transcosmos.mock.form.SingleForm2;

import org.junit.Assert;
import org.junit.Test;


/**
 * QueryString 用検索条件文字列の生成結果テスト
 *
 */
public class PagingListFormTest2 {

	
	/**
	 * Form に親クラスが存在しない、String フィールドの場合<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>アノテーションが設定されたフィールド名の設定値が結果文字列に含まれている事</li>
	 *     <li>アノテーションが設定されていないフィールド名が結果文字列に含まれていない事</li>
	 * </ul>
	 */
	@Test
	public void oyaFormTest() throws Exception {
		
		// 使用する Form で直接アノテーションが使用された場合
		SingleForm1 form = new SingleForm1();
		form.setField1("FLD1");
		form.setField2("FLD2");
		form.setField3("FLD3");

		
		// テストメソッドを実行
		String paramString = form.getOptionParams();
		System.out.println(paramString);

		
		// 期待値は 「&field1=FLD1&field3=FLD3」 
		// ただし、順番が入れ替わる可能性がある。

		// チェック用に末尾に & を付加
		paramString += "&";

		// 出力結果をチェック
		if (paramString.indexOf("&field1=FLD1&") < 0) Assert.fail("field1 パラメータが正しく出力されていない");
		if (paramString.indexOf("&field3=FLD3&") < 0) Assert.fail("field3 パラメータが正しく出力されていない");
		Assert.assertEquals("パラメータ長が一致しない", 25, paramString.length());

	}
	

	
	/**
	 * Form に親クラスが存在する、String フィールドの場合<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>親クラスのアノテーションが設定されたフィールド名の設定値が結果文字列に含まれている事</li>
	 *     <li>子クラスのアノテーションが設定されたフィールド名の設定値が結果文字列に含まれている事</li>
	 *     <li>アノテーションが設定されていないフィールド名が結果文字列に含まれていない事</li>
	 * </ul>
	 */
	@Test
	public void koFormTest() throws Exception {

		// 継承元の Form でアノテーションが使用された場合
		SingleForm2 form = new SingleForm2();
		form.setField1("FLD1");
		form.setField2("FLD2");
		form.setField3("FLD3");
		form.setField4("FLD4");
		form.setField5("FLD5");
		form.setField6("FLD6");


		// テストメソッドを実行
		String paramString = form.getOptionParams();


		// 期待値は 「&field1=FLD1&field3=FLD3&field4=FLD4&field6=FLD6」 
		// ただし、順番が入れ替わる可能性がある。

		// チェック用に末尾に & を付加
		paramString += "&";


		if (paramString.indexOf("&field1=FLD1&") < 0) Assert.fail("field1 パラメータが正しく出力されていない");
		if (paramString.indexOf("&field3=FLD3&") < 0) Assert.fail("field3 パラメータが正しく出力されていない");
		if (paramString.indexOf("&field4=FLD4&") < 0) Assert.fail("field4 パラメータが正しく出力されていない");
		if (paramString.indexOf("&field6=FLD6&") < 0) Assert.fail("field6 パラメータが正しく出力されていない");
		Assert.assertEquals("パラメータ長が一致しない", 49, paramString.length());

		System.out.println(paramString);
	}



	/**
	 * Form に親クラスが存在しない、String 配列フィールドの場合<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>アノテーションが設定されたフィールド名の設定値が結果文字列に含まれている事</li>
	 *     <li>アノテーションが設定されていないフィールド名が結果文字列に含まれていない事</li>
	 * </ul>
	 */
	@Test
	public void arrayOyaFormTest() throws Exception {
		
		// 使用する Form で直接アノテーションが使用された場合
		ArrayForm1 form = new ArrayForm1();
		form.setField1(new String[]{"FLD1-1","FLD1-2","FLD1-3"});
		form.setField2(new String[]{"FLD2-1","FLD2-2","FLD2-3"});
		form.setField3(new String[]{"FLD3-1","FLD3-2","FLD3-3"});

		
		// テストメソッドを実行
		String paramString = form.getOptionParams();
		System.out.println(paramString);


		// 期待値は 「&field1=FLD1-1&field1=FLD1-2&field1=FLD1-3&field3=FLD3-1&field3=FLD3-2&field3=FLD3-3」 
		// ただし、順番が入れ替わる可能性がある。

		// チェック用に末尾に & を付加
		paramString += "&";


		// 出力結果をチェック
		if (paramString.indexOf("&field1=FLD1-1&") < 0) Assert.fail("field1 パラメータが正しく出力されていない");
		if (paramString.indexOf("&field1=FLD1-2&") < 0) Assert.fail("field1 パラメータが正しく出力されていない");
		if (paramString.indexOf("&field1=FLD1-3&") < 0) Assert.fail("field1 パラメータが正しく出力されていない");
		if (paramString.indexOf("&field3=FLD3-1&") < 0) Assert.fail("field3 パラメータが正しく出力されていない");
		if (paramString.indexOf("&field3=FLD3-2&") < 0) Assert.fail("field3 パラメータが正しく出力されていない");
		if (paramString.indexOf("&field3=FLD3-3&") < 0) Assert.fail("field3 パラメータが正しく出力されていない");
		Assert.assertEquals("パラメータ長が一致しない", 85, paramString.length());
		
	}
	
	
	
	/**
	 * Form に親クラスが存在する、String 配列フィールドの場合<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>親クラスのアノテーションが設定されたフィールド名の設定値が結果文字列に含まれている事</li>
	 *     <li>子クラスのアノテーションが設定されたフィールド名の設定値が結果文字列に含まれている事</li>
	 *     <li>アノテーションが設定されていないフィールド名が結果文字列に含まれていない事</li>
	 * </ul>
	 */
	@Test
	public void arrayKoFormTest() throws Exception {

		// 使用する Form で直接アノテーションが使用された場合
		ArrayForm2 form = new ArrayForm2();
		form.setField1(new String[]{"FLD1-1","FLD1-2","FLD1-3"});
		form.setField2(new String[]{"FLD2-1","FLD2-2","FLD2-3"});
		form.setField3(new String[]{"FLD3-1","FLD3-2","FLD3-3"});
		form.setField4(new String[]{"FLD4-1","FLD4-2","FLD4-3"});
		form.setField5(new String[]{"FLD5-1","FLD5-2","FLD5-3"});
		form.setField6(new String[]{"FLD6-1","FLD6-2","FLD6-3"});

		
		// テストメソッドを実行
		String paramString = form.getOptionParams();
		System.out.println(paramString);


		// 期待値は 「&field1=FLD1-1&field1=FLD1-2&field1=FLD1-3&field3=FLD3-1&field3=FLD3-2&field3=FLD3-3
		// &field4=FLD4-1&field4=FLD4-2&field4=FLD4-3&field6=FLD6-1&field6=FLD6-2&field6=FLD6-3」 
		// ただし、順番が入れ替わる可能性がある。

		// チェック用に末尾に & を付加
		paramString += "&";


		// 出力結果をチェック
		if (paramString.indexOf("&field1=FLD1-1&") < 0) Assert.fail("field1 パラメータが正しく出力されていない");
		if (paramString.indexOf("&field1=FLD1-2&") < 0) Assert.fail("field1 パラメータが正しく出力されていない");
		if (paramString.indexOf("&field1=FLD1-3&") < 0) Assert.fail("field1 パラメータが正しく出力されていない");
		if (paramString.indexOf("&field3=FLD3-1&") < 0) Assert.fail("field3 パラメータが正しく出力されていない");
		if (paramString.indexOf("&field3=FLD3-2&") < 0) Assert.fail("field3 パラメータが正しく出力されていない");
		if (paramString.indexOf("&field3=FLD3-3&") < 0) Assert.fail("field3 パラメータが正しく出力されていない");
		if (paramString.indexOf("&field4=FLD4-1&") < 0) Assert.fail("field4 パラメータが正しく出力されていない");
		if (paramString.indexOf("&field4=FLD4-2&") < 0) Assert.fail("field4 パラメータが正しく出力されていない");
		if (paramString.indexOf("&field4=FLD4-3&") < 0) Assert.fail("field4 パラメータが正しく出力されていない");
		if (paramString.indexOf("&field6=FLD6-1&") < 0) Assert.fail("field6 パラメータが正しく出力されていない");
		if (paramString.indexOf("&field6=FLD6-2&") < 0) Assert.fail("field6 パラメータが正しく出力されていない");
		if (paramString.indexOf("&field6=FLD6-3&") < 0) Assert.fail("field6 パラメータが正しく出力されていない");
		Assert.assertEquals("パラメータ長が一致しない", 169, paramString.length());
		
	}

	
	
	/**
	 * Form のプロパティが String 型で 値が null の場合<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>空文字列が復帰される事</li>
	 * </ul>
	 */
	@Test
	public void formNullTest() throws Exception {

		// 使用する Form で直接アノテーションが使用された場合
		SingleForm2 form = new SingleForm2();


		// テストメソッドを実行
		String paramString = form.getOptionParams();
		System.out.println(paramString);


		Assert.assertEquals("空文字列が復帰される事", "", paramString);
	}
	
	
	
	/**
	 * Form のプロパティが String 型で 値が空文字列の場合<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>空文字列が復帰される事</li>
	 * </ul>
	 */
	@Test
	public void formBlankTest() throws Exception {

		// 使用する Form で直接アノテーションが使用された場合
		SingleForm2 form = new SingleForm2();
		form.setField1("");
		form.setField2("");
		form.setField3("");
		form.setField4("");
		form.setField5("");
		form.setField6("");


		// テストメソッドを実行
		String paramString = form.getOptionParams();
		System.out.println(paramString);


		Assert.assertEquals("空文字列が復帰される事", "", paramString);
	}
	
	
	
	
	/**
	 * Form のプロパティが String 配列型で 値が null の場合<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>空文字列が復帰される事</li>
	 * </ul>
	 */
	@Test
	public void arrayFormNullTest() throws Exception {

		// 使用する Form で直接アノテーションが使用された場合
		ArrayForm2 form = new ArrayForm2();


		// テストメソッドを実行
		String paramString = form.getOptionParams();
		System.out.println(paramString);


		Assert.assertEquals("空文字列が復帰される事", "", paramString);
		
	}

	
	
	/**
	 * Form のプロパティが String 配列型で 値が空の配列の場合<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>空文字列が復帰される事</li>
	 * </ul>
	 */
	@Test
	public void arrayFormZeroArrayTest() throws Exception {

		// 使用する Form で直接アノテーションが使用された場合
		ArrayForm2 form = new ArrayForm2();
		form.setField1(new String[0]);
		form.setField2(new String[0]);
		form.setField3(new String[0]);
		form.setField4(new String[0]);
		form.setField5(new String[0]);
		form.setField6(new String[0]);


		// テストメソッドを実行
		String paramString = form.getOptionParams();
		System.out.println(paramString);


		Assert.assertEquals("空文字列が復帰される事", "", paramString);
		
	}

	
	
	/**
	 * Form のプロパティが String 配列型で 配列の文字列が空文字列、null の場合<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>空文字列が復帰される事</li>
	 * </ul>
	 */
	@Test
	public void arrayFormNullArrayTest() throws Exception {

		// 使用する Form で直接アノテーションが使用された場合
		ArrayForm2 form = new ArrayForm2();
		form.setField1(new String[]{""});
		form.setField2(new String[]{""});
		form.setField3(new String[]{""});
		form.setField4(new String[]{null});
		form.setField5(new String[]{null});
		form.setField6(new String[]{null});


		// テストメソッドを実行
		String paramString = form.getOptionParams();
		System.out.println(paramString);


		Assert.assertEquals("空文字列が復帰される事", "", paramString);
		
	}

	

	/**
	 * Form のプロパティが String 型の場合、文字列が URL エンコードされている事<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>エスケープされた文字列が復帰される事</li>
	 * </ul>
	 */
	@Test
	public void formEscapeTest() throws Exception {
	
		// 使用する Form で直接アノテーションが使用された場合
		SingleForm2 form = new SingleForm2();
		form.setField1("あいうえお");
		form.setField6("abc=efg&hij");


		// テストメソッドを実行
		String paramString = form.getOptionParams();
		System.out.println(paramString);

		
		// チェック用に末尾に & を付加
		paramString += "&";

		
		// 期待値は 「&field1=%E3%81%82%E3%81%84%E3%81%86%E3%81%88%E3%81%8A&field6=abc%3Defg%26hij
		if (paramString.indexOf("&field1=%E3%81%82%E3%81%84%E3%81%86%E3%81%88%E3%81%8A&") < 0) Assert.fail("field1 パラメータが正しくエスケープされていない");
		if (paramString.indexOf("&field6=abc%3Defg%26hij&") < 0) Assert.fail("field6 パラメータが正しくエスケープされていない");
		Assert.assertEquals("パラメータ長が一致しない", 77, paramString.length());

	}



	/**
	 * Form のプロパティが String 配列型の場合、文字列が URL エンコードされている事<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>エスケープされた文字列が復帰される事</li>
	 * </ul>
	 */
	@Test
	public void arrayFormEscapeTest() throws Exception {
	

		// 使用する Form で直接アノテーションが使用された場合
		ArrayForm2 form = new ArrayForm2();
		form.setField1(new String[]{"あいうえお","かきくけこ"});
		form.setField6(new String[]{"abc=efg&hij","zzz=FFF"});


		// テストメソッドを実行
		String paramString = form.getOptionParams();
		System.out.println(paramString);

		
		// チェック用に末尾に & を付加
		paramString += "&";

		
		// 期待値は 「&field1=%E3%81%82%E3%81%84%E3%81%86%E3%81%88%E3%81%8A&
		// &field1=%E3%81%8B%E3%81%8D%E3%81%8F%E3%81%91%E3%81%93&field6=abc%3Defg%26hij&field6=zzz%3DFFF
		if (paramString.indexOf("&field1=%E3%81%82%E3%81%84%E3%81%86%E3%81%88%E3%81%8A&") < 0) Assert.fail("field1 パラメータが正しくエスケープされていない");
		if (paramString.indexOf("&field1=%E3%81%8B%E3%81%8D%E3%81%8F%E3%81%91%E3%81%93&") < 0) Assert.fail("field1 パラメータが正しくエスケープされていない");
		if (paramString.indexOf("&field6=abc%3Defg%26hij&") < 0) Assert.fail("field6 パラメータが正しくエスケープされていない");
		if (paramString.indexOf("&field6=zzz%3DFFF&") < 0) Assert.fail("field6 パラメータが正しくエスケープされていない");
		Assert.assertEquals("パラメータ長が一致しない", 147, paramString.length());

	}
	
	
	/**
	 * 親クラスと同名のフィールドが存在する場合<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>親クラスのフィールドが優先されて使用される事</li>
	 * </ul>
	 */
	@Test
	public void dupFormTest() throws Exception {

		DupForm2 form = new DupForm2();
		
		form.setField1("FLD1");
		form.setField2("FLD2");
		form.setField3(new String[]{"FLD3-1", "FLD3-2"});
		form.setField4(new String[]{"FLD4-1", "FLD4-2"});


		// テストメソッドを実行
		String paramString = form.getOptionParams();
		System.out.println(paramString);

		
		// チェック用に末尾に & を付加
		paramString += "&";

		// 期待値は 「&field1=FLD1_OYA&field3=FLD3-1_OYA&field3=FLD3-2_OYA
		if (paramString.indexOf("&field1=FLD1_OYA&") < 0) Assert.fail("field1 パラメータが親フィールドの値として出力されていない");
		if (paramString.indexOf("&field3=FLD3-1_OYA&") < 0) Assert.fail("field3 パラメータが親フィールドの値として出力されていない");
		if (paramString.indexOf("&field3=FLD3-2_OYA&") < 0) Assert.fail("field3 パラメータが親フィールドの値として出力されていない");
		Assert.assertEquals("パラメータ長が一致しない", 53, paramString.length());

	}
}
