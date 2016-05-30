package jp.co.transcosmos.dm3.utils;

import jp.co.transcosmos.mock.vo.ReflectionUtilsTestVo2;

import org.junit.Assert;
import org.junit.Test;

public class ReflectionUtilsTest {

	@Test
	public void getFieldTypeByGetterTest() throws NoSuchMethodException {
		
		ReflectionUtilsTestVo2 vo = new ReflectionUtilsTestVo2();
		
		Class<?> type1 = ReflectionUtils.getFieldTypeByGetter(vo.getClass(), "fieldName1");
		Assert.assertEquals("�p�����N���X�� String �^�v���p�e�B���擾�ł��鎖", type1.getName(), "java.lang.String");
		
		Class<?> type2 = ReflectionUtils.getFieldTypeByGetter(vo.getClass(), "fieldName2");
		Assert.assertEquals("�p�����N���X�� Integer �^�v���p�e�B���擾�ł��鎖", type2.getName(), "java.lang.Integer");

		Class<?> type3 = ReflectionUtils.getFieldTypeByGetter(vo.getClass(), "fieldName3");
		Assert.assertEquals("�p�����N���X�� Boolean �^�v���p�e�B���擾�ł��鎖", type3.getName(), "java.lang.Boolean");

		Class<?> type4 = ReflectionUtils.getFieldTypeByGetter(vo.getClass(), "fieldName4");
		Assert.assertEquals("�p�����N���X�� Double �^�v���p�e�B���擾�ł��鎖", type4.getName(), "java.lang.Double");

		Class<?> type5 = ReflectionUtils.getFieldTypeByGetter(vo.getClass(), "fieldName5");
		Assert.assertEquals("�p�����N���X�� boolean �^�v���p�e�B���擾�ł��鎖", type5.getName(), "boolean");

		Class<?> type11 = ReflectionUtils.getFieldTypeByGetter(vo.getClass(), "fieldName11");
		Assert.assertEquals("�p���N���X�� String �^�v���p�e�B���擾�ł��鎖", type11.getName(), "java.lang.String");

		Class<?> type12 = ReflectionUtils.getFieldTypeByGetter(vo.getClass(), "fieldName12");
		Assert.assertEquals("�p���N���X�� Integer �^�v���p�e�B���擾�ł��鎖", type12.getName(), "java.lang.Integer");

		Class<?> type13 = ReflectionUtils.getFieldTypeByGetter(vo.getClass(), "fieldName13");
		Assert.assertEquals("�p���N���X�� Boolean �^�v���p�e�B���擾�ł��鎖", type13.getName(), "java.lang.Boolean");

		Class<?> type14 = ReflectionUtils.getFieldTypeByGetter(vo.getClass(), "fieldName14");
		Assert.assertEquals("�p���N���X�� Double �^�v���p�e�B���擾�ł��鎖", type14.getName(), "java.lang.Double");

		Class<?> type15 = ReflectionUtils.getFieldTypeByGetter(vo.getClass(), "fieldName15");
		Assert.assertEquals("�p���N���X�� boolean �^�v���p�e�B���擾�ł��鎖", type15.getName(), "boolean");

	}
	

	
	@Test(expected = NoSuchMethodException.class)
	public void getFieldTypeByGetterExceptionTest1() throws NoSuchMethodException {
		
		ReflectionUtilsTestVo2 vo = new ReflectionUtilsTestVo2();
		
		Class<?> type1 = ReflectionUtils.getFieldTypeByGetter(vo.getClass(), "fieldName6");
		Assert.assertEquals("�p�����N���X�̔� getter ���\�b�h�ŗ�O���������鎖", type1.getName(), "java.lang.String");
		
	}

	
	@Test(expected = NoSuchMethodException.class)
	public void getFieldTypeByGetterExceptionTest2() throws NoSuchMethodException {
		
		ReflectionUtilsTestVo2 vo = new ReflectionUtilsTestVo2();
		
		Class<?> type1 = ReflectionUtils.getFieldTypeByGetter(vo.getClass(), "fieldName16");
		Assert.assertEquals("�p���N���X�̔� getter ���\�b�h�ŗ�O���������鎖", type1.getName(), "java.lang.String");

	}

	
	@Test(expected = NoSuchMethodException.class)
	public void getFieldTypeByGetterExceptionTest3() throws NoSuchMethodException {
		
		ReflectionUtilsTestVo2 vo = new ReflectionUtilsTestVo2();
		
		Class<?> type1 = ReflectionUtils.getFieldTypeByGetter(vo.getClass(), "fieldName16");
		Assert.assertEquals("���݂��Ȃ����\�b�h�ŗ�O���������鎖", type1.getName(), "java.lang.String");

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
		Assert.assertEquals("�p�����N���X�� String �^�v���p�e�B���擾�ł��鎖", "data1", fieldName1);
		
		Integer fieldName2 = (Integer)ReflectionUtils.getFieldValueByGetter(vo, "fieldName2");
		Assert.assertEquals("�p�����N���X�� Integer �^�v���p�e�B���擾�ł��鎖", Integer.valueOf(100), fieldName2);

		Boolean fieldName3 = (Boolean) ReflectionUtils.getFieldValueByGetter(vo, "fieldName3");
		Assert.assertTrue("�p�����N���X�� Boolean �^�v���p�e�B���擾�ł��鎖", fieldName3);

		Double fieldName4 = (Double) ReflectionUtils.getFieldValueByGetter(vo, "fieldName4");
		Assert.assertEquals("�p�����N���X�� Double �^�v���p�e�B���擾�ł��鎖", Double.valueOf(1000d), fieldName4);

		boolean fieldName5 = (boolean) ReflectionUtils.getFieldValueByGetter(vo, "fieldName5");
		Assert.assertTrue("�p�����N���X�� boolean �^�v���p�e�B���擾�ł��鎖", fieldName5);

		String fieldName11 = (String) ReflectionUtils.getFieldValueByGetter(vo, "fieldName11");
		Assert.assertEquals("�p���N���X�� String �^�v���p�e�B���擾�ł��鎖", "data11", fieldName11);

		Integer fieldName12 = (Integer) ReflectionUtils.getFieldValueByGetter(vo, "fieldName12");
		Assert.assertEquals("�p���N���X�� Integer �^�v���p�e�B���擾�ł��鎖", Integer.valueOf(200), fieldName12);

		Boolean fieldName13 = (Boolean) ReflectionUtils.getFieldValueByGetter(vo, "fieldName13");
		Assert.assertTrue("�p���N���X�� Boolean �^�v���p�e�B���擾�ł��鎖", fieldName13);

		Double fieldName14 = (Double) ReflectionUtils.getFieldValueByGetter(vo, "fieldName14");
		Assert.assertEquals("�p���N���X�� Double �^�v���p�e�B���擾�ł��鎖", Double.valueOf(2000d), fieldName14);

		boolean fieldName15 = (boolean)ReflectionUtils.getFieldValueByGetter(vo, "fieldName15");
		Assert.assertTrue("�p���N���X�� boolean �^�v���p�e�B���擾�ł��鎖", fieldName15);

	}
	
	
}
