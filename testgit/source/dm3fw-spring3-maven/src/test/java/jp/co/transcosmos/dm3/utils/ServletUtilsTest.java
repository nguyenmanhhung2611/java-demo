package jp.co.transcosmos.dm3.utils;

import org.junit.Assert;
import org.junit.Test;

public class ServletUtilsTest {

	@Test
	public void crToHtmlTagTest() {

		String testValue;
		String ans;

		
		// null の場合
		Assert.assertEquals("null の場合、空文字列が復帰される事", "", ServletUtils.crToHtmlTag(null));
		
		// 空文字列の場合
		Assert.assertEquals("空文字列の場合、空文字列が復帰される事", "", ServletUtils.crToHtmlTag(""));

		// 置換対象文字列、改行コードが含まれない場合
		testValue = "あいうえお かきくけこ";
		Assert.assertEquals("置換対象文字列、改行コードが含まれない場合、元の値と一致する事", testValue, ServletUtils.crToHtmlTag(testValue));

		// 置換対象文字列が含まれる場合
		testValue = "<p>あいうえお かきくけこ</p>";
		ans = "&lt;p&gt;あいうえお かきくけこ&lt;/p&gt;";
		Assert.assertEquals("置換対象文字列、改行コードが含まれない場合、元の値と一致する事", ans, ServletUtils.crToHtmlTag(testValue));

		// 置換対象文字列、\r\nが含まれる場合
		testValue = "<p>あいうえお\r\nかきくけこ\r\n</p>";
		ans = "&lt;p&gt;あいうえお<br/>かきくけこ<br/>&lt;/p&gt;";
		Assert.assertEquals("置換対象文字列、改行コード（\\r\\n）が含まれない場合、元の値と一致する事", ans, ServletUtils.crToHtmlTag(testValue));

		// 置換対象文字列、\rが含まれる場合
		testValue = "<p>あいうえお\rかきくけこ\r</p>";
		ans = "&lt;p&gt;あいうえお<br/>かきくけこ<br/>&lt;/p&gt;";
		Assert.assertEquals("置換対象文字列、改行コード（\\r）が含まれない場合、元の値と一致する事", ans, ServletUtils.crToHtmlTag(testValue));

		// 置換対象文字列、\nが含まれる場合
		testValue = "<p>あいうえお\nかきくけこ\n</p>";
		ans = "&lt;p&gt;あいうえお<br/>かきくけこ<br/>&lt;/p&gt;";
		Assert.assertEquals("置換対象文字列、改行コード（\\n）が含まれない場合、元の値と一致する事", ans, ServletUtils.crToHtmlTag(testValue));

		
	}



	@Test
	public void replaceTest() {

		String targetString;			// 置換対象文字列
		String replaceFrom;				// 置換元文字列
		String replaceTo;				// 置換先文字列


		// 置換対象文字列が null の場合
		targetString = null;
		replaceFrom = "hoge";
		replaceTo = "hogehoge";
		Assert.assertEquals("置換対象文字列が null の場合、空文字列が復帰する事。", "", ServletUtils.replace(targetString, replaceFrom, replaceTo));
		
		
		// 置換対象文字列が空文字列の場合
		targetString = "";
		replaceFrom = "hoge";
		replaceTo = "hogehoge";
		Assert.assertEquals("置換対象文字列が空文字列の場合、空文字列が復帰する事。", "", ServletUtils.replace(targetString, replaceFrom, replaceTo));


		// 複数の置換文字列が含まれている場合
		targetString = "abcdhogehogeefg";
		replaceFrom = "hoge";
		replaceTo = "hogehoge";
		Assert.assertEquals("複数の置換文字列が含まれている場合、正しく置換できる事", "abcdhogehogehogehogeefg", ServletUtils.replace(targetString, replaceFrom, replaceTo));


		// タグ文字列に置換する場合
		targetString = "あいうえお㎡かきくけこ";
		replaceFrom = "㎡";
		replaceTo = "m<sup>2</sup>";
		Assert.assertEquals("置換先にタグが含まれている場合、エスケープされていないタグが復帰する事。", "あいうえおm<sup>2</sup>かきくけこ", ServletUtils.replace(targetString, replaceFrom, replaceTo));

		
		// 置換元にエスケープが必要な文字列が含まれている場合
		targetString = "あい<br>うえお㎡かきく<br>けこ";
		replaceFrom = "㎡";
		replaceTo = "m<sup>2</sup>";
		Assert.assertEquals("置換先にタグが含まれている場合、エスケープされていないタグが復帰する事。", "あい&lt;br&gt;うえおm<sup>2</sup>かきく&lt;br&gt;けこ", ServletUtils.replace(targetString, replaceFrom, replaceTo));

	}

}
