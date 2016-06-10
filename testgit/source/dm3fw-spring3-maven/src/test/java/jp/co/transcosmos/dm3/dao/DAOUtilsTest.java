package jp.co.transcosmos.dm3.dao;

import org.junit.Assert;
import org.junit.Test;

/**
 * DAOUtils のテストケース<br/>
 */
public class DAOUtilsTest {

	
	/**
	 * フィールド毎の別名が重複している場合にリネームする処理のテスト<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>追加した別名が重複している場合、000 ～ の連番にリネームされる事</li>
	 *     <li>追加した別名が重複していない場合、リネームされない事</li>
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
