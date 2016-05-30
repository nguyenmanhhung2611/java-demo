package jp.co.transcosmos.dm3.test.core.displayAdapter;

import jp.co.transcosmos.dm3.core.displayAdapter.DisplayAdapter;
import jp.co.transcosmos.dm3.testUtil.mock.displayAdapter.BaseDisplayAdapter;
import jp.co.transcosmos.dm3.testUtil.mock.displayAdapter.BaseVo;
import jp.co.transcosmos.dm3.testUtil.mock.displayAdapter.ChiBaseVo;
import jp.co.transcosmos.dm3.testUtil.mock.displayAdapter.ChiDisplayAdapter;
import jp.co.transcosmos.dm3.testUtil.mock.displayAdapter.ChiDisplayAdapter2;
import jp.co.transcosmos.dm3.testUtil.mock.displayAdapter.ChiOtherVo;
import jp.co.transcosmos.dm3.testUtil.mock.displayAdapter.OtherVo;

import org.junit.Assert;
import org.junit.Test;

public class DisplayAdapterTest {

	// Display Adapter の親クラス + VO の親クラスのケース
	@Test
	public void parAdapterAndParVotest() throws Exception{
		
		BaseVo vo = new BaseVo();
		vo.setFieldName1("ABC");
		vo.setFieldName2(100);
		vo.setFieldName3(200);
		vo.setFieldName11("EFG");
		vo.setFieldName12(1000);
		vo.setFieldName13(2000);


		DisplayAdapter adapter = new BaseDisplayAdapter();
		
		Assert.assertEquals("String プロパティの委譲先が正常に動作する事", "ABC", adapter.getDisplayValue(vo, "fieldName1"));
		Assert.assertEquals("Integer プロパティの委譲先が正常に動作する事", Integer.valueOf(100), adapter.getDisplayValue(vo, "fieldName2"));
		Assert.assertEquals("int プロパティの委譲先が正常に動作する事", 200, adapter.getDisplayValue(vo, "fieldName3"));
		Assert.assertEquals("String プロパティのオーバーライドが正常に動作する事", "EFGXYZ", adapter.getDisplayValue(vo, "fieldName11"));
		Assert.assertEquals("Integer プロパティのオーバーライドが正常に動作する事", Integer.valueOf(1050), adapter.getDisplayValue(vo, "fieldName12"));
		Assert.assertEquals("int プロパティのオーバーライドが正常に動作する事", 2055, adapter.getDisplayValue(vo, "fieldName13"));

		
		// キャッシュの確認
		vo = new BaseVo();
		vo.setFieldName1("ABCC");
		vo.setFieldName2(1000);
		vo.setFieldName3(2000);
		vo.setFieldName11("EFGG");
		vo.setFieldName12(10000);
		vo.setFieldName13(20000);


		adapter = new BaseDisplayAdapter();
		Assert.assertEquals("String プロパティの委譲先が正常に動作する事", "ABCC", adapter.getDisplayValue(vo, "fieldName1"));
		Assert.assertEquals("Integer プロパティの委譲先が正常に動作する事", Integer.valueOf(1000), adapter.getDisplayValue(vo, "fieldName2"));
		Assert.assertEquals("int プロパティの委譲先が正常に動作する事", 2000, adapter.getDisplayValue(vo, "fieldName3"));
		Assert.assertEquals("String プロパティのオーバーライドが正常に動作する事", "EFGGXYZ", adapter.getDisplayValue(vo, "fieldName11"));
		Assert.assertEquals("Integer プロパティのオーバーライドが正常に動作する事", Integer.valueOf(10050), adapter.getDisplayValue(vo, "fieldName12"));
		Assert.assertEquals("int プロパティのオーバーライドが正常に動作する事", 20055, adapter.getDisplayValue(vo, "fieldName13"));
		
	}
	

	
	// Display Adapter の親クラス + VO の子クラスのケース
	@Test
	public void parAdapterAndChiVotest() throws Exception{
		
		ChiBaseVo vo = new ChiBaseVo();
		vo.setFieldName1("ABC");
		vo.setFieldName2(100);
		vo.setFieldName3(200);
		vo.setFieldName11("EFG");
		vo.setFieldName12(1000);
		vo.setFieldName13(2000);
		vo.setFieldName21("HIJ");
		vo.setFieldName22(500);
		vo.setFieldName23(600);


		DisplayAdapter adapter = new BaseDisplayAdapter();
		
		Assert.assertEquals("String プロパティの委譲先が正常に動作する事", "ABC", adapter.getDisplayValue(vo, "fieldName1"));
		Assert.assertEquals("Integer プロパティの委譲先が正常に動作する事", Integer.valueOf(100), adapter.getDisplayValue(vo, "fieldName2"));
		Assert.assertEquals("int プロパティの委譲先が正常に動作する事", 200, adapter.getDisplayValue(vo, "fieldName3"));
		Assert.assertEquals("String プロパティのオーバーライドが正常に動作する事", "EFGXYZ", adapter.getDisplayValue(vo, "fieldName11"));
		Assert.assertEquals("Integer プロパティのオーバーライドが正常に動作する事", Integer.valueOf(1050), adapter.getDisplayValue(vo, "fieldName12"));
		Assert.assertEquals("int プロパティのオーバーライドが正常に動作する事", 2055, adapter.getDisplayValue(vo, "fieldName13"));
		Assert.assertEquals("String 追加プロパティの委譲先が正常に動作する事", "HIJ", adapter.getDisplayValue(vo, "fieldName21"));
		Assert.assertEquals("Integer 追加プロパティの委譲先が正常に動作する事", Integer.valueOf(500), adapter.getDisplayValue(vo, "fieldName22"));
		Assert.assertEquals("int 追加プロパティの委譲先が正常に動作する事", 600, adapter.getDisplayValue(vo, "fieldName23"));


		// キャッシュの確認
		vo = new ChiBaseVo();
		vo.setFieldName1("ABCC");
		vo.setFieldName2(1000);
		vo.setFieldName3(2000);
		vo.setFieldName11("EFGG");
		vo.setFieldName12(10000);
		vo.setFieldName13(20000);
		vo.setFieldName21("HIJJ");
		vo.setFieldName22(5000);
		vo.setFieldName23(6000);

		adapter = new BaseDisplayAdapter();

		Assert.assertEquals("String プロパティの委譲先が正常に動作する事", "ABCC", adapter.getDisplayValue(vo, "fieldName1"));
		Assert.assertEquals("Integer プロパティの委譲先が正常に動作する事", Integer.valueOf(1000), adapter.getDisplayValue(vo, "fieldName2"));
		Assert.assertEquals("int プロパティの委譲先が正常に動作する事", 2000, adapter.getDisplayValue(vo, "fieldName3"));
		Assert.assertEquals("String プロパティのオーバーライドが正常に動作する事", "EFGGXYZ", adapter.getDisplayValue(vo, "fieldName11"));
		Assert.assertEquals("Integer プロパティのオーバーライドが正常に動作する事", Integer.valueOf(10050), adapter.getDisplayValue(vo, "fieldName12"));
		Assert.assertEquals("int プロパティのオーバーライドが正常に動作する事", 20055, adapter.getDisplayValue(vo, "fieldName13"));
		Assert.assertEquals("String 追加プロパティの委譲先が正常に動作する事", "HIJJ", adapter.getDisplayValue(vo, "fieldName21"));
		Assert.assertEquals("Integer 追加プロパティの委譲先が正常に動作する事", Integer.valueOf(5000), adapter.getDisplayValue(vo, "fieldName22"));
		Assert.assertEquals("int 追加プロパティの委譲先が正常に動作する事", 6000, adapter.getDisplayValue(vo, "fieldName23"));

		
	}

	
	
	// Display Adapter の子クラス + VO の親クラスのケース
	@Test
	public void chiAdapterAndParVotest() throws Exception{
		
		BaseVo vo = new BaseVo();
		vo.setFieldName1("ABC");
		vo.setFieldName2(100);
		vo.setFieldName3(200);
		vo.setFieldName11("EFG");
		vo.setFieldName12(1000);
		vo.setFieldName13(2000);


		DisplayAdapter adapter = new ChiDisplayAdapter();
		
		Assert.assertEquals("String プロパティの委譲先が正常に動作する事", "ABC", adapter.getDisplayValue(vo, "fieldName1"));
		Assert.assertEquals("Integer プロパティの委譲先が正常に動作する事", Integer.valueOf(100), adapter.getDisplayValue(vo, "fieldName2"));
		Assert.assertEquals("int プロパティの委譲先が正常に動作する事", 200, adapter.getDisplayValue(vo, "fieldName3"));
		Assert.assertEquals("String プロパティのAdapterオーバーライドが正常に動作する事", "EFGSTU", adapter.getDisplayValue(vo, "fieldName11"));
		Assert.assertEquals("Integer プロパティのAdapterオーバーライドが正常に動作する事", Integer.valueOf(1300), adapter.getDisplayValue(vo, "fieldName12"));
		Assert.assertEquals("int プロパティのAdapterオーバーライドが正常に動作する事", 2355, adapter.getDisplayValue(vo, "fieldName13"));


		// キャッシュの確認
		vo = new BaseVo();
		vo.setFieldName1("ABCC");
		vo.setFieldName2(1000);
		vo.setFieldName3(2000);
		vo.setFieldName11("EFGG");
		vo.setFieldName12(10000);
		vo.setFieldName13(20000);


		adapter = new ChiDisplayAdapter();
		
		Assert.assertEquals("String プロパティの委譲先が正常に動作する事", "ABCC", adapter.getDisplayValue(vo, "fieldName1"));
		Assert.assertEquals("Integer プロパティの委譲先が正常に動作する事", Integer.valueOf(1000), adapter.getDisplayValue(vo, "fieldName2"));
		Assert.assertEquals("int プロパティの委譲先が正常に動作する事", 2000, adapter.getDisplayValue(vo, "fieldName3"));
		Assert.assertEquals("String プロパティのAdapterオーバーライドが正常に動作する事", "EFGGSTU", adapter.getDisplayValue(vo, "fieldName11"));
		Assert.assertEquals("Integer プロパティのAdapterオーバーライドが正常に動作する事", Integer.valueOf(10300), adapter.getDisplayValue(vo, "fieldName12"));
		Assert.assertEquals("int プロパティのAdapterオーバーライドが正常に動作する事", 20355, adapter.getDisplayValue(vo, "fieldName13"));
	}

	
	// Display Adapter の子クラス + VO の子クラスのケース
	@Test
	public void chiAdapterAndChiVotest() throws Exception{
		
		ChiBaseVo vo = new ChiBaseVo();
		vo.setFieldName1("ABC");
		vo.setFieldName2(100);
		vo.setFieldName3(200);
		vo.setFieldName11("EFG");
		vo.setFieldName12(1000);
		vo.setFieldName13(2000);
		vo.setFieldName21("HIJ");
		vo.setFieldName22(500);
		vo.setFieldName23(600);


		DisplayAdapter adapter = new ChiDisplayAdapter2();
		
		Assert.assertEquals("String プロパティの委譲先が正常に動作する事", "ABC", adapter.getDisplayValue(vo, "fieldName1"));
		Assert.assertEquals("Integer プロパティの委譲先が正常に動作する事", Integer.valueOf(100), adapter.getDisplayValue(vo, "fieldName2"));
		Assert.assertEquals("int プロパティの委譲先が正常に動作する事", 200, adapter.getDisplayValue(vo, "fieldName3"));
		Assert.assertEquals("String プロパティのオーバーライドが正常に動作する事", "EFGXYZ", adapter.getDisplayValue(vo, "fieldName11"));
		Assert.assertEquals("Integer プロパティのオーバーライドが正常に動作する事", Integer.valueOf(1050), adapter.getDisplayValue(vo, "fieldName12"));
		Assert.assertEquals("int プロパティのオーバーライドが正常に動作する事", 2055, adapter.getDisplayValue(vo, "fieldName13"));
		Assert.assertEquals("String 追加プロパティのAdapterオーバーライドが正常に動作する事", "HIJUUU", adapter.getDisplayValue(vo, "fieldName21"));
		Assert.assertEquals("Integer 追加プロパティのAdapterオーバーライドが正常に動作する事", Integer.valueOf(900), adapter.getDisplayValue(vo, "fieldName22"));
		Assert.assertEquals("int 追加プロパティのAdapterオーバーライドが正常に動作する事", 1040, adapter.getDisplayValue(vo, "fieldName23"));


		// キャッシュの確認
		vo = new ChiBaseVo();
		vo.setFieldName1("ABCC");
		vo.setFieldName2(1000);
		vo.setFieldName3(2000);
		vo.setFieldName11("EFGG");
		vo.setFieldName12(10000);
		vo.setFieldName13(20000);
		vo.setFieldName21("HIJJ");
		vo.setFieldName22(5000);
		vo.setFieldName23(6000);


		adapter = new ChiDisplayAdapter2();
		
		Assert.assertEquals("String プロパティの委譲先が正常に動作する事", "ABCC", adapter.getDisplayValue(vo, "fieldName1"));
		Assert.assertEquals("Integer プロパティの委譲先が正常に動作する事", Integer.valueOf(1000), adapter.getDisplayValue(vo, "fieldName2"));
		Assert.assertEquals("int プロパティの委譲先が正常に動作する事", 2000, adapter.getDisplayValue(vo, "fieldName3"));
		Assert.assertEquals("String プロパティのオーバーライドが正常に動作する事", "EFGGXYZ", adapter.getDisplayValue(vo, "fieldName11"));
		Assert.assertEquals("Integer プロパティのオーバーライドが正常に動作する事", Integer.valueOf(10050), adapter.getDisplayValue(vo, "fieldName12"));
		Assert.assertEquals("int プロパティのオーバーライドが正常に動作する事", 20055, adapter.getDisplayValue(vo, "fieldName13"));
		Assert.assertEquals("String 追加プロパティのAdapterオーバーライドが正常に動作する事", "HIJJUUU", adapter.getDisplayValue(vo, "fieldName21"));
		Assert.assertEquals("Integer 追加プロパティのAdapterオーバーライドが正常に動作する事", Integer.valueOf(5400), adapter.getDisplayValue(vo, "fieldName22"));
		Assert.assertEquals("int 追加プロパティのAdapterオーバーライドが正常に動作する事", 6440, adapter.getDisplayValue(vo, "fieldName23"));

	}

	
	// vo を mix して使用
	@Test
	public void mixVotest() throws Exception{

		ChiBaseVo vo = new ChiBaseVo();
		vo.setFieldName1("ABC");
		vo.setFieldName2(100);
		vo.setFieldName11("EFG");

		OtherVo otherVo1 = new OtherVo();
		otherVo1.setFieldName1("NNN");
		otherVo1.setFieldName2("nnn");
		
		OtherVo otherVo2 = new OtherVo();
		otherVo2.setFieldName1("MMM");
		otherVo2.setFieldName2("mmm");
		
		ChiOtherVo chiOtherVo1 = new ChiOtherVo();
		chiOtherVo1.setFieldName1("OOO");
		chiOtherVo1.setFieldName2("ooo");
		chiOtherVo1.setFieldName11("OOOOO");

		ChiOtherVo chiOtherVo2 = new ChiOtherVo();
		chiOtherVo2.setFieldName1("PPP");
		chiOtherVo2.setFieldName2("ppp");
		chiOtherVo2.setFieldName11("PPPPP");

		
		// テストメソッドを実行
		DisplayAdapter adapter = new ChiDisplayAdapter();
		

		// 念のため、２回実行する。
		for (int i=0; i<2; ++i){
			// 親 Adapter に別VO の同名メソッドが存在する
			String ret =  (String) adapter.getDisplayValue(vo, "fieldName1");
			Assert.assertEquals("委譲先のメソッドが実行される事", "ABC", ret);

			// 親 Adapter に別VO の同名メソッドが存在する
			Integer retInt =  (Integer) adapter.getDisplayValue(vo, "fieldName2");
			Assert.assertEquals("委譲先のメソッドが実行される事", Integer.valueOf(100), retInt);

			// 自身の Adapter に別VO の 同名と自身の VO のメソッド両方が存在する
			ret =  (String) adapter.getDisplayValue(vo, "fieldName11");
			Assert.assertEquals("自身の Adapter のメソッドが実行される事", "EFGSTU", ret);

			//　自身の Apater に子VO のメソッドがあるが、親 Adapter に 自身の VO メソッドがある
			ret =  (String) adapter.getDisplayValue(otherVo1, "fieldName1");
			Assert.assertEquals("親 Adapter のメソッドが使用される事", "NNNTTT", ret);
			
			// 自身の Apater に子VO のメソッドがある
			ret =  (String) adapter.getDisplayValue(otherVo1, "fieldName2");
			Assert.assertEquals("委譲先 のメソッドが使用される事", "nnn", ret);

			// 別VO パターン
			ret =  (String) adapter.getDisplayValue(otherVo2, "fieldName1");
			Assert.assertEquals("親 Adapter のメソッドが使用される事", "MMMTTT", ret);
			
			// 別VO パターン
			ret =  (String) adapter.getDisplayValue(otherVo2, "fieldName2");
			Assert.assertEquals("委譲先 のメソッドが使用される事", "mmm", ret);

			// 自身の Adapter に自身の VO のメソッドが存在し、親 Adapter に、親 VO のメソッドが存在する。
			ret =  (String) adapter.getDisplayValue(chiOtherVo1, "fieldName1");
			Assert.assertEquals("自身の Adapter のメソッドが使用される事", "OOORRR", ret);
			
			// 該当メソッドなし
			ret =  (String) adapter.getDisplayValue(chiOtherVo1, "fieldName2");
			Assert.assertEquals("委譲先 のメソッドが使用される事", "ooo", ret);
			
			// 自身の Adapter に自身の VO のメソッドが存在し、親 Adapter に依存関係の無いVOの同名メソッドあり
			ret =  (String) adapter.getDisplayValue(chiOtherVo1, "fieldName11");
			Assert.assertEquals("自身の Adapter のメソッドが使用される事", "OOOOOGGG", ret);

			// 別VO パターン
			ret =  (String) adapter.getDisplayValue(chiOtherVo2, "fieldName1");
			Assert.assertEquals("自身の Adapter のメソッドが使用される事", "PPPRRR", ret);
			
			ret =  (String) adapter.getDisplayValue(chiOtherVo2, "fieldName2");
			Assert.assertEquals("委譲先 のメソッドが使用される事", "ppp", ret);

			ret =  (String) adapter.getDisplayValue(chiOtherVo2, "fieldName11");
			Assert.assertEquals("自身の Adapter のメソッドが使用される事", "PPPPPGGG", ret);
		}

	}
}
