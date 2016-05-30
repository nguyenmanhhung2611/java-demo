package jp.co.transcosmos.dm3.test.core.util;

import java.util.ArrayList;
import java.util.List;

import jp.co.transcosmos.dm3.core.util.ExecuteShell;

import org.junit.Assert;
import org.junit.Test;


/**
 * 外部 Shell コマンド実行処理のテストケース<br/>
 * <br/>
 * 
 */
public class ExecuteShellTest {

	
	/**
	 * 存在するコマンドを実行（正常系）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>戻り値が 0 である事</li>
	 * </ul>
	 */
	@Test
	public void execOKTest() throws Exception{
		
		List<String> list = new ArrayList<>();

		list.add("cmd.exe");
		list.add("/C");
		list.add("echo");
		list.add("ABC");
		
		int ret = ExecuteShell.exec(list);

		Assert.assertEquals("コマンドの戻り値が 0 である事", 0, ret);

	}
	

	
	/**
	 * 存在しないコマンドを実行（異常系）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>戻り値が 0 でない事</li>
	 * </ul>
	 */
	@Test
	public void execNGTest() throws Exception{
		
		List<String> list = new ArrayList<>();

		list.add("cmd.exe");
		list.add("/C");
		list.add("echo2");
		list.add("ABC");
		
		int ret = ExecuteShell.exec(list);

		Assert.assertNotEquals("コマンドの戻り値が 0 でない事", 0, ret);

	}

}
