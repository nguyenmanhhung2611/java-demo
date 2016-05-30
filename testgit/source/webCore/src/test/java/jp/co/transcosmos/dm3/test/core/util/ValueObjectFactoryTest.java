package jp.co.transcosmos.dm3.test.core.util;

import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.AddressMst;
import jp.co.transcosmos.dm3.testUtil.mock.MockFactory;
import jp.co.transcosmos.dm3.testUtil.mock.MockPrefMst;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * VO �C���X�^���X�����N���X�̃e�X�g�P�[�X<br/>
 * <br/>
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class ValueObjectFactoryTest {

	// �e�X�g�p�� MockFacotry �Ńe�X�g����B
	// ���̃N���X�́APrefMst �̏ꍇ�A MockPrefMst �𕜋A����l�Ɋg������Ă���B 
	private ValueObjectFactory factory = new MockFactory();

	
	/**
	 * VO �C���X�^���X�̎擾�e�X�g�i�P��p�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�w�肳�ꂽ�N���X���̃C���X�^���X���擾�ł��鎖</li>
	 * </ul>
	 */
	@Test
	public void getValueObjectSingleTest() {

		// �e�X�g���\�b�h���s
		Object obj = this.factory.getValueObject("AddressMst");
		
		if (!(obj instanceof AddressMst)){
			Assert.fail("�w�肵���N���X�̃C���X�^���X���擾�ł��Ă��鎖�B");
		}
		
		
	}



	/**
	 * VO �C���X�^���X�̎擾�e�X�g�i�z��p�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�w�肳��N���X���̃C���X�^���X���擾�ł��鎖</li>
	 *     <li>�w�肳�ꂽ���̃C���X�^���X����������Ă��鎖</li>
	 *     <li>�z��ɂ͈قȂ�I�u�W�F�N�g���i�[����Ă��鎖�i�P�ڂƂQ�ڂŔ�r�j</li>
	 * </ul>
	 */
	@Test
	public void getValueObjectArrayTest() {

		// �e�X�g���\�b�h���s
		Object[] objs = this.factory.getValueObject("AddressMst", 3);

		Assert.assertEquals("�z��̌�������������", 3, objs.length);
		
		if (!(objs instanceof AddressMst[])){
			Assert.fail("�w�肵���N���X�̔z�񂪎擾�ł��Ă��鎖�B");
		}
		
		if (!(objs[0] instanceof AddressMst)){
			Assert.fail("�w�肵���N���X�̃C���X�^���X���擾�ł��Ă��鎖�B");
		}
		
		Object old = null;
		for (Object obj : objs){
			Assert.assertNotNull("�I�u�W�F�N�g���z��ɐݒ肳��Ă��鎖", obj);
			
			if (old != null){
				Assert.assertNotEquals("�I�u�W�F�N�gID ���قȂ鎖", old.toString(), obj.toString());
			}
			
			old = obj;
		}
		
	}

	
	
	/**
	 * VO �C���X�^���X�̎擾�e�X�g�i�P��p�A�p�����j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�w�肳�ꂽ�N���X���̌p�����ꂽ�C���X�^���X���擾�ł��鎖</li>
	 * </ul>
	 */
	@Test
	public void getValueObjectExSingleTest() {

		// �e�X�g���\�b�h���s
		Object obj = this.factory.getValueObject("PrefMst");
		
		if (!(obj instanceof MockPrefMst)){
			Assert.fail("�w�肵���N���X�̌p�����ꂽ�N���X�̃C���X�^���X���擾�ł��Ă��鎖�B");
		}
		
		
	}


	
	/**
	 * VO �C���X�^���X�̎擾�e�X�g�i�z��p�A�p�����j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�w�肳�ꂽ�N���X���̌p�����ꂽ�C���X�^���X�̔z�񂪎擾�ł��鎖</li>
	 * </ul>
	 */
	@Test
	public void getValueObjectExArrayTest() {

		// �e�X�g���\�b�h���s
		Object[] obj = this.factory.getValueObject("PrefMst", 3);

		if (!(obj instanceof MockPrefMst[])){
			Assert.fail("�w�肵���N���X�̌p�����ꂽ�N���X�̔z�񂪎擾�ł��Ă��鎖�B");
		}

		if (!(obj[0] instanceof MockPrefMst)){
			Assert.fail("�w�肵���N���X�̌p�����ꂽ�N���X�̃C���X�^���X���擾�ł��Ă��鎖�B");
		}

	}

}
