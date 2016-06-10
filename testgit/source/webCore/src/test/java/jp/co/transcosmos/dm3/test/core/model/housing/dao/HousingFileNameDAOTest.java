package jp.co.transcosmos.dm3.test.core.model.housing.dao;

import jp.co.transcosmos.dm3.core.model.housing.dao.HousingFileNameDAO;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * �����摜�t�@�C���������N���X�̃e�X�g
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class HousingFileNameDAOTest {

	@Autowired
	private HousingFileNameDAO housingFileNameDAO;

	
	/**
	 * �����摜�t�@�C�����̐����e�X�g
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�P�O���̕�����𕜋A���鎖�B</li>
	 *     <li>�P��ڂƂQ��ڂŒl���قȂ鎖�B</li>
	 * </ul>
	 */
	@Test
	public void getFileNameTest() throws Exception{

		String fileName1 = this.housingFileNameDAO.createFileName();
		Assert.assertEquals("10���̕����񂪕��A���鎖", 10, fileName1.length());
		System.out.println("fileName1 : " + fileName1);

		String fileName2 = this.housingFileNameDAO.createFileName();
		Assert.assertEquals("10���̕����񂪕��A���鎖", 10, fileName2.length());
		Assert.assertNotEquals("�P��ڂƂQ��ڂňقȂ�l�����A���鎖", fileName1, fileName2);
		System.out.println("fileName2 : " + fileName2);
		
		
	}
	
	
}
