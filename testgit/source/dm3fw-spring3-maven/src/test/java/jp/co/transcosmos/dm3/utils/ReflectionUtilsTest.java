package jp.co.transcosmos.dm3.utils;

import jp.co.transcosmos.mock.vo.ReflectionUtilsTestVo2;

import org.junit.Assert;
import org.junit.Test;

public class ReflectionUtilsTest {

	@Test
	public void getFieldTypeByGetterTest() throws NoSuchMethodException {
		
		ReflectionUtilsTestVo2 vo = new ReflectionUtilsTestVo2();
		
		Class<?> type1 = ReflectionUtils.getFieldTypeByGetter(vo.getClass(), "fieldName1");
		Assert.assertEquals("継承元クラスの String 型プロパティが取得できる事", type1.getName(), "java.lang.String");
		
		Class<?> type2 = ReflectionUtils.getFieldTypeByGetter(vo.getClass(), "fieldName2");
		Assert.assertEquals("継承元クラスの Integer 型プロパティが取得できる事", type2.getName(), "java.lang.Integer");

		Class<?> type3 = ReflectionUtils.getFieldTypeByGetter(vo.getClass(), "fieldName3");
		Assert.assertEquals("継承元クラスの Boolean 型プロパティが取得できる事", type3.getName(), "java.lang.Boolean");

		Class<?> type4 = ReflectionUtils.getFieldTypeByGetter(vo.getClass(), "fieldName4");
		Assert.assertEquals("継承元クラスの Double 型プロパティが取得できる事", type4.getName(), "java.lang.Double");

		Class<?> type5 = ReflectionUtils.getFieldTypeByGetter(vo.getClass(), "fieldName5");
		Assert.assertEquals("継承元クラスの boolean 型プロパティが取得できる事", type5.getName(), "boolean");

		Class<?> type11 = ReflectionUtils.getFieldTypeByGetter(vo.getClass(), "fieldName11");
		Assert.assertEquals("継承クラスの String 型プロパティが取得できる事", type11.getName(), "java.lang.String");

		Class<?> type12 = ReflectionUtils.getFieldTypeByGetter(vo.getClass(), "fieldName12");
		Assert.assertEquals("継承クラスの Integer 型プロパティが取得できる事", type12.getName(), "java.lang.Integer");

		Class<?> type13 = ReflectionUtils.getFieldTypeByGetter(vo.getClass(), "fieldName13");
		Assert.assertEquals("継承クラスの Boolean 型プロパティが取得できる事", type13.getName(), "java.lang.Boolean");

		Class<?> type14 = ReflectionUtils.getFieldTypeByGetter(vo.getClass(), "fieldName14");
		Assert.assertEquals("継承クラスの Double 型プロパティが取得できる事", type14.getName(), "java.lang.Double");

		Class<?> type15 = ReflectionUtils.getFieldTypeByGetter(vo.getClass(), "fieldName15");
		Assert.assertEquals("継承クラスの boolean 型プロパティが取得できる事", type15.getName(), "boolean");

	}
	

	
	@Test(expected = NoSuchMethodException.class)
	public void getFieldTypeByGetterExceptionTest1() throws NoSuchMethodException {
		
		ReflectionUtilsTestVo2 vo = new ReflectionUtilsTestVo2();
		
		Class<?> type1 = ReflectionUtils.getFieldTypeByGetter(vo.getClass(), "fieldName6");
		Assert.assertEquals("継承元クラスの非 getter メソッドで例外が発生する事", type1.getName(), "java.lang.String");
		
	}

	
	@Test(expected = NoSuchMethodException.class)
	public void getFieldTypeByGetterExceptionTest2() throws NoSuchMethodException {
		
		ReflectionUtilsTestVo2 vo = new ReflectionUtilsTestVo2();
		
		Class<?> type1 = ReflectionUtils.getFieldTypeByGetter(vo.getClass(), "fieldName16");
		Assert.assertEquals("継承クラスの非 getter メソッドで例外が発生する事", type1.getName(), "java.lang.String");

	}

	
	@Test(expected = NoSuchMethodException.class)
	public void getFieldTypeByGetterExceptionTest3() throws NoSuchMethodException {
		
		ReflectionUtilsTestVo2 vo = new ReflectionUtilsTestVo2();
		
		Class<?> type1 = ReflectionUtils.getFieldTypeByGetter(vo.getClass(), "fieldName16");
		Assert.assertEquals("存在しないメソッドで例外が発生する事", type1.getName(), "java.lang.String");

	}

	
	@Test
	public void getFieldValueByGetterTest() throws Exception {

		ReflectionUtilsTestVo2 vo = new ReflectionUtilsTestVo2();
		vo.setFieldName1("data1");
		vo.setFieldName2(100);
		vo.setFieldName3(true);
		vo.setFieldName4(1000d);
		vo.setFieldName5(true);
		vo.setFieldName11("data11");
		vo.setFieldName12(200);
		vo.setFieldName13(true);
		vo.setFieldName14(2000d);
		vo.setFieldName15(true);
		
		
		String fieldName1 = (String) ReflectionUtils.getFieldValueByGetter(vo, "fieldName1");
		Assert.assertEquals("継承元クラスの String 型プロパティが取得できる事", "data1", fieldName1);
		
		Integer fieldName2 = (Integer)ReflectionUtils.getFieldValueByGetter(vo, "fieldName2");
		Assert.assertEquals("継承元クラスの Integer 型プロパティが取得できる事", Integer.valueOf(100), fieldName2);

		Boolean fieldName3 = (Boolean) ReflectionUtils.getFieldValueByGetter(vo, "fieldName3");
		Assert.assertTrue("継承元クラスの Boolean 型プロパティが取得できる事", fieldName3);

		Double fieldName4 = (Double) ReflectionUtils.getFieldValueByGetter(vo, "fieldName4");
		Assert.assertEquals("継承元クラスの Double 型プロパティが取得できる事", Double.valueOf(1000d), fieldName4);

		boolean fieldName5 = (boolean) ReflectionUtils.getFieldValueByGetter(vo, "fieldName5");
		Assert.assertTrue("継承元クラスの boolean 型プロパティが取得できる事", fieldName5);

		String fieldName11 = (String) ReflectionUtils.getFieldValueByGetter(vo, "fieldName11");
		Assert.assertEquals("継承クラスの String 型プロパティが取得できる事", "data11", fieldName11);

		Integer fieldName12 = (Integer) ReflectionUtils.getFieldValueByGetter(vo, "fieldName12");
		Assert.assertEquals("継承クラスの Integer 型プロパティが取得できる事", Integer.valueOf(200), fieldName12);

		Boolean fieldName13 = (Boolean) ReflectionUtils.getFieldValueByGetter(vo, "fieldName13");
		Assert.assertTrue("継承クラスの Boolean 型プロパティが取得できる事", fieldName13);

		Double fieldName14 = (Double) ReflectionUtils.getFieldValueByGetter(vo, "fieldName14");
		Assert.assertEquals("継承クラスの Double 型プロパティが取得できる事", Double.valueOf(2000d), fieldName14);

		boolean fieldName15 = (boolean)ReflectionUtils.getFieldValueByGetter(vo, "fieldName15");
		Assert.assertTrue("継承クラスの boolean 型プロパティが取得できる事", fieldName15);

	}
	
	
}
