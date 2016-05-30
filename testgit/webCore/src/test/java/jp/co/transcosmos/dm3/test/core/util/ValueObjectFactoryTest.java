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
 * VO インスタンス生成クラスのテストケース<br/>
 * <br/>
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class ValueObjectFactoryTest {

	// テスト用の MockFacotry でテストする。
	// このクラスは、PrefMst の場合、 MockPrefMst を復帰する様に拡張されている。 
	private ValueObjectFactory factory = new MockFactory();

	
	/**
	 * VO インスタンスの取得テスト（単一用）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>指定されたクラス名のインスタンスが取得できる事</li>
	 * </ul>
	 */
	@Test
	public void getValueObjectSingleTest() {

		// テストメソッド実行
		Object obj = this.factory.getValueObject("AddressMst");
		
		if (!(obj instanceof AddressMst)){
			Assert.fail("指定したクラスのインスタンスが取得できている事。");
		}
		
		
	}



	/**
	 * VO インスタンスの取得テスト（配列用）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>指定されクラス名のインスタンスが取得できる事</li>
	 *     <li>指定された数のインスタンスが生成されている事</li>
	 *     <li>配列には異なるオブジェクトが格納されている事（１個目と２個目で比較）</li>
	 * </ul>
	 */
	@Test
	public void getValueObjectArrayTest() {

		// テストメソッド実行
		Object[] objs = this.factory.getValueObject("AddressMst", 3);

		Assert.assertEquals("配列の件数が正しい事", 3, objs.length);
		
		if (!(objs instanceof AddressMst[])){
			Assert.fail("指定したクラスの配列が取得できている事。");
		}
		
		if (!(objs[0] instanceof AddressMst)){
			Assert.fail("指定したクラスのインスタンスが取得できている事。");
		}
		
		Object old = null;
		for (Object obj : objs){
			Assert.assertNotNull("オブジェクトが配列に設定されている事", obj);
			
			if (old != null){
				Assert.assertNotEquals("オブジェクトID が異なる事", old.toString(), obj.toString());
			}
			
			old = obj;
		}
		
	}

	
	
	/**
	 * VO インスタンスの取得テスト（単一用、継承時）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>指定されたクラス名の継承されたインスタンスが取得できる事</li>
	 * </ul>
	 */
	@Test
	public void getValueObjectExSingleTest() {

		// テストメソッド実行
		Object obj = this.factory.getValueObject("PrefMst");
		
		if (!(obj instanceof MockPrefMst)){
			Assert.fail("指定したクラスの継承されたクラスのインスタンスが取得できている事。");
		}
		
		
	}


	
	/**
	 * VO インスタンスの取得テスト（配列用、継承時）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>指定されたクラス名の継承されたインスタンスの配列が取得できる事</li>
	 * </ul>
	 */
	@Test
	public void getValueObjectExArrayTest() {

		// テストメソッド実行
		Object[] obj = this.factory.getValueObject("PrefMst", 3);

		if (!(obj instanceof MockPrefMst[])){
			Assert.fail("指定したクラスの継承されたクラスの配列が取得できている事。");
		}

		if (!(obj[0] instanceof MockPrefMst)){
			Assert.fail("指定したクラスの継承されたクラスのインスタンスが取得できている事。");
		}

	}

}
