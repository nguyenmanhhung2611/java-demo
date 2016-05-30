package jp.co.transcosmos.dm3.test.core.model.housing.dao;

import jp.co.transcosmos.dm3.core.model.housing.dao.HousingFileNameDAO;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * 物件画像ファイル名生成クラスのテスト
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class HousingFileNameDAOTest {

	@Autowired
	private HousingFileNameDAO housingFileNameDAO;

	
	/**
	 * 物件画像ファイル名の生成テスト
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>１０桁の文字列を復帰する事。</li>
	 *     <li>１回目と２回目で値が異なる事。</li>
	 * </ul>
	 */
	@Test
	public void getFileNameTest() throws Exception{

		String fileName1 = this.housingFileNameDAO.createFileName();
		Assert.assertEquals("10桁の文字列が復帰する事", 10, fileName1.length());
		System.out.println("fileName1 : " + fileName1);

		String fileName2 = this.housingFileNameDAO.createFileName();
		Assert.assertEquals("10桁の文字列が復帰する事", 10, fileName2.length());
		Assert.assertNotEquals("１回目と２回目で異なる値が復帰する事", fileName1, fileName2);
		System.out.println("fileName2 : " + fileName2);
		
		
	}
	
	
}
