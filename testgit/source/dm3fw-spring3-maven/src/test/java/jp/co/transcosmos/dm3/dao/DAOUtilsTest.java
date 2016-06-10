package jp.co.transcosmos.dm3.dao;

import org.junit.Assert;
import org.junit.Test;

/**
 * DAOUtils �̃e�X�g�P�[�X<br/>
 */
public class DAOUtilsTest {

	
	/**
	 * �t�B�[���h���̕ʖ����d�����Ă���ꍇ�Ƀ��l�[�����鏈���̃e�X�g<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�ǉ������ʖ����d�����Ă���ꍇ�A000 �` �̘A�ԂɃ��l�[������鎖</li>
	 *     <li>�ǉ������ʖ����d�����Ă��Ȃ��ꍇ�A���l�[������Ȃ���</li>
	 * </ul>
	 */
	@Test
	public void dupliateAliasRenameTest() {
		
		String forFieldNames[] = new String[7];

		forFieldNames[0] = "XXX_xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx";
		DAOUtils.dupliateAliasRename(forFieldNames, 0);
		Assert.assertEquals("XXX_xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx", forFieldNames[0]);


		forFieldNames[1] = "XXX_xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx";
		DAOUtils.dupliateAliasRename(forFieldNames, 1);
		Assert.assertEquals("XXX_xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx", forFieldNames[0]);
		Assert.assertEquals("XXX_xxxxxxxxxx_xxxxxxxxxx_xxxxxxx000", forFieldNames[1]);
		
				
		forFieldNames[2] = "XXX_xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx";
		DAOUtils.dupliateAliasRename(forFieldNames, 2);
		Assert.assertEquals("XXX_xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx", forFieldNames[0]);
		Assert.assertEquals("XXX_xxxxxxxxxx_xxxxxxxxxx_xxxxxxx000", forFieldNames[1]);
		Assert.assertEquals("XXX_xxxxxxxxxx_xxxxxxxxxx_xxxxxxx001", forFieldNames[2]);

		forFieldNames[3] = "YYY_xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx";
		DAOUtils.dupliateAliasRename(forFieldNames, 3);
		Assert.assertEquals("XXX_xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx", forFieldNames[0]);
		Assert.assertEquals("XXX_xxxxxxxxxx_xxxxxxxxxx_xxxxxxx000", forFieldNames[1]);
		Assert.assertEquals("XXX_xxxxxxxxxx_xxxxxxxxxx_xxxxxxx001", forFieldNames[2]);
		Assert.assertEquals("YYY_xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx", forFieldNames[3]);
		
		forFieldNames[4] = "ZZZ_xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx";
		DAOUtils.dupliateAliasRename(forFieldNames, 4);
		Assert.assertEquals("XXX_xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx", forFieldNames[0]);
		Assert.assertEquals("XXX_xxxxxxxxxx_xxxxxxxxxx_xxxxxxx000", forFieldNames[1]);
		Assert.assertEquals("XXX_xxxxxxxxxx_xxxxxxxxxx_xxxxxxx001", forFieldNames[2]);
		Assert.assertEquals("YYY_xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx", forFieldNames[3]);
		Assert.assertEquals("ZZZ_xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx", forFieldNames[4]);
		
		forFieldNames[5] = "ZZZ_xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx";
		DAOUtils.dupliateAliasRename(forFieldNames, 5);
		Assert.assertEquals("XXX_xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx", forFieldNames[0]);
		Assert.assertEquals("XXX_xxxxxxxxxx_xxxxxxxxxx_xxxxxxx000", forFieldNames[1]);
		Assert.assertEquals("XXX_xxxxxxxxxx_xxxxxxxxxx_xxxxxxx001", forFieldNames[2]);
		Assert.assertEquals("YYY_xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx", forFieldNames[3]);
		Assert.assertEquals("ZZZ_xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx", forFieldNames[4]);
		Assert.assertEquals("ZZZ_xxxxxxxxxx_xxxxxxxxxx_xxxxxxx000", forFieldNames[5]);

		forFieldNames[6] = "ZZZ_xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx";
		DAOUtils.dupliateAliasRename(forFieldNames, 6);
		Assert.assertEquals("XXX_xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx", forFieldNames[0]);
		Assert.assertEquals("XXX_xxxxxxxxxx_xxxxxxxxxx_xxxxxxx000", forFieldNames[1]);
		Assert.assertEquals("XXX_xxxxxxxxxx_xxxxxxxxxx_xxxxxxx001", forFieldNames[2]);
		Assert.assertEquals("YYY_xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx", forFieldNames[3]);
		Assert.assertEquals("ZZZ_xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx", forFieldNames[4]);
		Assert.assertEquals("ZZZ_xxxxxxxxxx_xxxxxxxxxx_xxxxxxx000", forFieldNames[5]);
		Assert.assertEquals("ZZZ_xxxxxxxxxx_xxxxxxxxxx_xxxxxxx001", forFieldNames[6]);

	}
	
}
