package jp.co.transcosmos.dm3.utils;

import org.junit.Assert;
import org.junit.Test;

public class EncodingUtilsTest {

	
	@Test
	public void htmlEncodeTest(){
		
		// null の場合
		Assert.assertNull("null の場合、null が復帰する事。", EncodingUtils.htmlEncode(null));

		// 空文字列の場合
		Assert.assertEquals("空文字列の場合、空文字列が復帰する事。", "", EncodingUtils.htmlEncode(""));

		// 置換対象文字列が存在しない場合
		String testValue = "abc あいうえお 123";
		Assert.assertEquals("置換対象文字列が存在しない場合、元の値が復帰する事。", testValue, EncodingUtils.htmlEncode(testValue));

		// 置換文字列が存在する場合
		testValue = "<div>\"abc\" \"あい&&うえお\" \"123\"</div>";
		String ans = "&lt;div&gt;&quot;abc&quot; &quot;あい&amp;&amp;うえお&quot; &quot;123&quot;&lt;/div&gt;";
		Assert.assertEquals("置換対象文字列が存在する場合、置換されている事。", ans, EncodingUtils.htmlEncode(testValue));

	}
	
	
	
	
}
