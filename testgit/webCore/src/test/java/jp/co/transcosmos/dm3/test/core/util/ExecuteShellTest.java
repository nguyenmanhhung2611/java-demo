package jp.co.transcosmos.dm3.test.core.util;

import java.util.ArrayList;
import java.util.List;

import jp.co.transcosmos.dm3.core.util.ExecuteShell;

import org.junit.Assert;
import org.junit.Test;


/**
 * �O�� Shell �R�}���h���s�����̃e�X�g�P�[�X<br/>
 * <br/>
 * 
 */
public class ExecuteShellTest {

	
	/**
	 * ���݂���R�}���h�����s�i����n�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�߂�l�� 0 �ł��鎖</li>
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

		Assert.assertEquals("�R�}���h�̖߂�l�� 0 �ł��鎖", 0, ret);

	}
	

	
	/**
	 * ���݂��Ȃ��R�}���h�����s�i�ُ�n�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�߂�l�� 0 �łȂ���</li>
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

		Assert.assertNotEquals("�R�}���h�̖߂�l�� 0 �łȂ���", 0, ret);

	}

}
