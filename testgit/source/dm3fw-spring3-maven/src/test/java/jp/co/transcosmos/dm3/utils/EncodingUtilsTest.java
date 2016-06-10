package jp.co.transcosmos.dm3.utils;

import org.junit.Assert;
import org.junit.Test;

public class EncodingUtilsTest {

	
	@Test
	public void htmlEncodeTest(){
		
		// null �̏ꍇ
		Assert.assertNull("null �̏ꍇ�Anull �����A���鎖�B", EncodingUtils.htmlEncode(null));

		// �󕶎���̏ꍇ
		Assert.assertEquals("�󕶎���̏ꍇ�A�󕶎��񂪕��A���鎖�B", "", EncodingUtils.htmlEncode(""));

		// �u���Ώە����񂪑��݂��Ȃ��ꍇ
		String testValue = "abc ���������� 123";
		Assert.assertEquals("�u���Ώە����񂪑��݂��Ȃ��ꍇ�A���̒l�����A���鎖�B", testValue, EncodingUtils.htmlEncode(testValue));

		// �u�������񂪑��݂���ꍇ
		testValue = "<div>\"abc\" \"����&&������\" \"123\"</div>";
		String ans = "&lt;div&gt;&quot;abc&quot; &quot;����&amp;&amp;������&quot; &quot;123&quot;&lt;/div&gt;";
		Assert.assertEquals("�u���Ώە����񂪑��݂���ꍇ�A�u������Ă��鎖�B", ans, EncodingUtils.htmlEncode(testValue));

	}
	
	
	
	
}
