package jp.co.transcosmos.dm3.form;

import jp.co.transcosmos.mock.form.ArrayForm1;
import jp.co.transcosmos.mock.form.ArrayForm2;
import jp.co.transcosmos.mock.form.DupForm2;
import jp.co.transcosmos.mock.form.SingleForm1;
import jp.co.transcosmos.mock.form.SingleForm2;

import org.junit.Assert;
import org.junit.Test;


/**
 * QueryString �p��������������̐������ʃe�X�g
 *
 */
public class PagingListFormTest2 {

	
	/**
	 * Form �ɐe�N���X�����݂��Ȃ��AString �t�B�[���h�̏ꍇ<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�A�m�e�[�V�������ݒ肳�ꂽ�t�B�[���h���̐ݒ�l�����ʕ�����Ɋ܂܂�Ă��鎖</li>
	 *     <li>�A�m�e�[�V�������ݒ肳��Ă��Ȃ��t�B�[���h�������ʕ�����Ɋ܂܂�Ă��Ȃ���</li>
	 * </ul>
	 */
	@Test
	public void oyaFormTest() throws Exception {
		
		// �g�p���� Form �Œ��ڃA�m�e�[�V�������g�p���ꂽ�ꍇ
		SingleForm1 form = new SingleForm1();
		form.setField1("FLD1");
		form.setField2("FLD2");
		form.setField3("FLD3");

		
		// �e�X�g���\�b�h�����s
		String paramString = form.getOptionParams();
		System.out.println(paramString);

		
		// ���Ғl�� �u&field1=FLD1&field3=FLD3�v 
		// �������A���Ԃ�����ւ��\��������B

		// �`�F�b�N�p�ɖ����� & ��t��
		paramString += "&";

		// �o�͌��ʂ��`�F�b�N
		if (paramString.indexOf("&field1=FLD1&") < 0) Assert.fail("field1 �p�����[�^���������o�͂���Ă��Ȃ�");
		if (paramString.indexOf("&field3=FLD3&") < 0) Assert.fail("field3 �p�����[�^���������o�͂���Ă��Ȃ�");
		Assert.assertEquals("�p�����[�^������v���Ȃ�", 25, paramString.length());

	}
	

	
	/**
	 * Form �ɐe�N���X�����݂���AString �t�B�[���h�̏ꍇ<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�e�N���X�̃A�m�e�[�V�������ݒ肳�ꂽ�t�B�[���h���̐ݒ�l�����ʕ�����Ɋ܂܂�Ă��鎖</li>
	 *     <li>�q�N���X�̃A�m�e�[�V�������ݒ肳�ꂽ�t�B�[���h���̐ݒ�l�����ʕ�����Ɋ܂܂�Ă��鎖</li>
	 *     <li>�A�m�e�[�V�������ݒ肳��Ă��Ȃ��t�B�[���h�������ʕ�����Ɋ܂܂�Ă��Ȃ���</li>
	 * </ul>
	 */
	@Test
	public void koFormTest() throws Exception {

		// �p������ Form �ŃA�m�e�[�V�������g�p���ꂽ�ꍇ
		SingleForm2 form = new SingleForm2();
		form.setField1("FLD1");
		form.setField2("FLD2");
		form.setField3("FLD3");
		form.setField4("FLD4");
		form.setField5("FLD5");
		form.setField6("FLD6");


		// �e�X�g���\�b�h�����s
		String paramString = form.getOptionParams();


		// ���Ғl�� �u&field1=FLD1&field3=FLD3&field4=FLD4&field6=FLD6�v 
		// �������A���Ԃ�����ւ��\��������B

		// �`�F�b�N�p�ɖ����� & ��t��
		paramString += "&";


		if (paramString.indexOf("&field1=FLD1&") < 0) Assert.fail("field1 �p�����[�^���������o�͂���Ă��Ȃ�");
		if (paramString.indexOf("&field3=FLD3&") < 0) Assert.fail("field3 �p�����[�^���������o�͂���Ă��Ȃ�");
		if (paramString.indexOf("&field4=FLD4&") < 0) Assert.fail("field4 �p�����[�^���������o�͂���Ă��Ȃ�");
		if (paramString.indexOf("&field6=FLD6&") < 0) Assert.fail("field6 �p�����[�^���������o�͂���Ă��Ȃ�");
		Assert.assertEquals("�p�����[�^������v���Ȃ�", 49, paramString.length());

		System.out.println(paramString);
	}



	/**
	 * Form �ɐe�N���X�����݂��Ȃ��AString �z��t�B�[���h�̏ꍇ<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�A�m�e�[�V�������ݒ肳�ꂽ�t�B�[���h���̐ݒ�l�����ʕ�����Ɋ܂܂�Ă��鎖</li>
	 *     <li>�A�m�e�[�V�������ݒ肳��Ă��Ȃ��t�B�[���h�������ʕ�����Ɋ܂܂�Ă��Ȃ���</li>
	 * </ul>
	 */
	@Test
	public void arrayOyaFormTest() throws Exception {
		
		// �g�p���� Form �Œ��ڃA�m�e�[�V�������g�p���ꂽ�ꍇ
		ArrayForm1 form = new ArrayForm1();
		form.setField1(new String[]{"FLD1-1","FLD1-2","FLD1-3"});
		form.setField2(new String[]{"FLD2-1","FLD2-2","FLD2-3"});
		form.setField3(new String[]{"FLD3-1","FLD3-2","FLD3-3"});

		
		// �e�X�g���\�b�h�����s
		String paramString = form.getOptionParams();
		System.out.println(paramString);


		// ���Ғl�� �u&field1=FLD1-1&field1=FLD1-2&field1=FLD1-3&field3=FLD3-1&field3=FLD3-2&field3=FLD3-3�v 
		// �������A���Ԃ�����ւ��\��������B

		// �`�F�b�N�p�ɖ����� & ��t��
		paramString += "&";


		// �o�͌��ʂ��`�F�b�N
		if (paramString.indexOf("&field1=FLD1-1&") < 0) Assert.fail("field1 �p�����[�^���������o�͂���Ă��Ȃ�");
		if (paramString.indexOf("&field1=FLD1-2&") < 0) Assert.fail("field1 �p�����[�^���������o�͂���Ă��Ȃ�");
		if (paramString.indexOf("&field1=FLD1-3&") < 0) Assert.fail("field1 �p�����[�^���������o�͂���Ă��Ȃ�");
		if (paramString.indexOf("&field3=FLD3-1&") < 0) Assert.fail("field3 �p�����[�^���������o�͂���Ă��Ȃ�");
		if (paramString.indexOf("&field3=FLD3-2&") < 0) Assert.fail("field3 �p�����[�^���������o�͂���Ă��Ȃ�");
		if (paramString.indexOf("&field3=FLD3-3&") < 0) Assert.fail("field3 �p�����[�^���������o�͂���Ă��Ȃ�");
		Assert.assertEquals("�p�����[�^������v���Ȃ�", 85, paramString.length());
		
	}
	
	
	
	/**
	 * Form �ɐe�N���X�����݂���AString �z��t�B�[���h�̏ꍇ<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�e�N���X�̃A�m�e�[�V�������ݒ肳�ꂽ�t�B�[���h���̐ݒ�l�����ʕ�����Ɋ܂܂�Ă��鎖</li>
	 *     <li>�q�N���X�̃A�m�e�[�V�������ݒ肳�ꂽ�t�B�[���h���̐ݒ�l�����ʕ�����Ɋ܂܂�Ă��鎖</li>
	 *     <li>�A�m�e�[�V�������ݒ肳��Ă��Ȃ��t�B�[���h�������ʕ�����Ɋ܂܂�Ă��Ȃ���</li>
	 * </ul>
	 */
	@Test
	public void arrayKoFormTest() throws Exception {

		// �g�p���� Form �Œ��ڃA�m�e�[�V�������g�p���ꂽ�ꍇ
		ArrayForm2 form = new ArrayForm2();
		form.setField1(new String[]{"FLD1-1","FLD1-2","FLD1-3"});
		form.setField2(new String[]{"FLD2-1","FLD2-2","FLD2-3"});
		form.setField3(new String[]{"FLD3-1","FLD3-2","FLD3-3"});
		form.setField4(new String[]{"FLD4-1","FLD4-2","FLD4-3"});
		form.setField5(new String[]{"FLD5-1","FLD5-2","FLD5-3"});
		form.setField6(new String[]{"FLD6-1","FLD6-2","FLD6-3"});

		
		// �e�X�g���\�b�h�����s
		String paramString = form.getOptionParams();
		System.out.println(paramString);


		// ���Ғl�� �u&field1=FLD1-1&field1=FLD1-2&field1=FLD1-3&field3=FLD3-1&field3=FLD3-2&field3=FLD3-3
		// &field4=FLD4-1&field4=FLD4-2&field4=FLD4-3&field6=FLD6-1&field6=FLD6-2&field6=FLD6-3�v 
		// �������A���Ԃ�����ւ��\��������B

		// �`�F�b�N�p�ɖ����� & ��t��
		paramString += "&";


		// �o�͌��ʂ��`�F�b�N
		if (paramString.indexOf("&field1=FLD1-1&") < 0) Assert.fail("field1 �p�����[�^���������o�͂���Ă��Ȃ�");
		if (paramString.indexOf("&field1=FLD1-2&") < 0) Assert.fail("field1 �p�����[�^���������o�͂���Ă��Ȃ�");
		if (paramString.indexOf("&field1=FLD1-3&") < 0) Assert.fail("field1 �p�����[�^���������o�͂���Ă��Ȃ�");
		if (paramString.indexOf("&field3=FLD3-1&") < 0) Assert.fail("field3 �p�����[�^���������o�͂���Ă��Ȃ�");
		if (paramString.indexOf("&field3=FLD3-2&") < 0) Assert.fail("field3 �p�����[�^���������o�͂���Ă��Ȃ�");
		if (paramString.indexOf("&field3=FLD3-3&") < 0) Assert.fail("field3 �p�����[�^���������o�͂���Ă��Ȃ�");
		if (paramString.indexOf("&field4=FLD4-1&") < 0) Assert.fail("field4 �p�����[�^���������o�͂���Ă��Ȃ�");
		if (paramString.indexOf("&field4=FLD4-2&") < 0) Assert.fail("field4 �p�����[�^���������o�͂���Ă��Ȃ�");
		if (paramString.indexOf("&field4=FLD4-3&") < 0) Assert.fail("field4 �p�����[�^���������o�͂���Ă��Ȃ�");
		if (paramString.indexOf("&field6=FLD6-1&") < 0) Assert.fail("field6 �p�����[�^���������o�͂���Ă��Ȃ�");
		if (paramString.indexOf("&field6=FLD6-2&") < 0) Assert.fail("field6 �p�����[�^���������o�͂���Ă��Ȃ�");
		if (paramString.indexOf("&field6=FLD6-3&") < 0) Assert.fail("field6 �p�����[�^���������o�͂���Ă��Ȃ�");
		Assert.assertEquals("�p�����[�^������v���Ȃ�", 169, paramString.length());
		
	}

	
	
	/**
	 * Form �̃v���p�e�B�� String �^�� �l�� null �̏ꍇ<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�󕶎��񂪕��A����鎖</li>
	 * </ul>
	 */
	@Test
	public void formNullTest() throws Exception {

		// �g�p���� Form �Œ��ڃA�m�e�[�V�������g�p���ꂽ�ꍇ
		SingleForm2 form = new SingleForm2();


		// �e�X�g���\�b�h�����s
		String paramString = form.getOptionParams();
		System.out.println(paramString);


		Assert.assertEquals("�󕶎��񂪕��A����鎖", "", paramString);
	}
	
	
	
	/**
	 * Form �̃v���p�e�B�� String �^�� �l���󕶎���̏ꍇ<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�󕶎��񂪕��A����鎖</li>
	 * </ul>
	 */
	@Test
	public void formBlankTest() throws Exception {

		// �g�p���� Form �Œ��ڃA�m�e�[�V�������g�p���ꂽ�ꍇ
		SingleForm2 form = new SingleForm2();
		form.setField1("");
		form.setField2("");
		form.setField3("");
		form.setField4("");
		form.setField5("");
		form.setField6("");


		// �e�X�g���\�b�h�����s
		String paramString = form.getOptionParams();
		System.out.println(paramString);


		Assert.assertEquals("�󕶎��񂪕��A����鎖", "", paramString);
	}
	
	
	
	
	/**
	 * Form �̃v���p�e�B�� String �z��^�� �l�� null �̏ꍇ<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�󕶎��񂪕��A����鎖</li>
	 * </ul>
	 */
	@Test
	public void arrayFormNullTest() throws Exception {

		// �g�p���� Form �Œ��ڃA�m�e�[�V�������g�p���ꂽ�ꍇ
		ArrayForm2 form = new ArrayForm2();


		// �e�X�g���\�b�h�����s
		String paramString = form.getOptionParams();
		System.out.println(paramString);


		Assert.assertEquals("�󕶎��񂪕��A����鎖", "", paramString);
		
	}

	
	
	/**
	 * Form �̃v���p�e�B�� String �z��^�� �l����̔z��̏ꍇ<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�󕶎��񂪕��A����鎖</li>
	 * </ul>
	 */
	@Test
	public void arrayFormZeroArrayTest() throws Exception {

		// �g�p���� Form �Œ��ڃA�m�e�[�V�������g�p���ꂽ�ꍇ
		ArrayForm2 form = new ArrayForm2();
		form.setField1(new String[0]);
		form.setField2(new String[0]);
		form.setField3(new String[0]);
		form.setField4(new String[0]);
		form.setField5(new String[0]);
		form.setField6(new String[0]);


		// �e�X�g���\�b�h�����s
		String paramString = form.getOptionParams();
		System.out.println(paramString);


		Assert.assertEquals("�󕶎��񂪕��A����鎖", "", paramString);
		
	}

	
	
	/**
	 * Form �̃v���p�e�B�� String �z��^�� �z��̕����񂪋󕶎���Anull �̏ꍇ<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�󕶎��񂪕��A����鎖</li>
	 * </ul>
	 */
	@Test
	public void arrayFormNullArrayTest() throws Exception {

		// �g�p���� Form �Œ��ڃA�m�e�[�V�������g�p���ꂽ�ꍇ
		ArrayForm2 form = new ArrayForm2();
		form.setField1(new String[]{""});
		form.setField2(new String[]{""});
		form.setField3(new String[]{""});
		form.setField4(new String[]{null});
		form.setField5(new String[]{null});
		form.setField6(new String[]{null});


		// �e�X�g���\�b�h�����s
		String paramString = form.getOptionParams();
		System.out.println(paramString);


		Assert.assertEquals("�󕶎��񂪕��A����鎖", "", paramString);
		
	}

	

	/**
	 * Form �̃v���p�e�B�� String �^�̏ꍇ�A������ URL �G���R�[�h����Ă��鎖<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�G�X�P�[�v���ꂽ�����񂪕��A����鎖</li>
	 * </ul>
	 */
	@Test
	public void formEscapeTest() throws Exception {
	
		// �g�p���� Form �Œ��ڃA�m�e�[�V�������g�p���ꂽ�ꍇ
		SingleForm2 form = new SingleForm2();
		form.setField1("����������");
		form.setField6("abc=efg&hij");


		// �e�X�g���\�b�h�����s
		String paramString = form.getOptionParams();
		System.out.println(paramString);

		
		// �`�F�b�N�p�ɖ����� & ��t��
		paramString += "&";

		
		// ���Ғl�� �u&field1=%E3%81%82%E3%81%84%E3%81%86%E3%81%88%E3%81%8A&field6=abc%3Defg%26hij
		if (paramString.indexOf("&field1=%E3%81%82%E3%81%84%E3%81%86%E3%81%88%E3%81%8A&") < 0) Assert.fail("field1 �p�����[�^���������G�X�P�[�v����Ă��Ȃ�");
		if (paramString.indexOf("&field6=abc%3Defg%26hij&") < 0) Assert.fail("field6 �p�����[�^���������G�X�P�[�v����Ă��Ȃ�");
		Assert.assertEquals("�p�����[�^������v���Ȃ�", 77, paramString.length());

	}



	/**
	 * Form �̃v���p�e�B�� String �z��^�̏ꍇ�A������ URL �G���R�[�h����Ă��鎖<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�G�X�P�[�v���ꂽ�����񂪕��A����鎖</li>
	 * </ul>
	 */
	@Test
	public void arrayFormEscapeTest() throws Exception {
	

		// �g�p���� Form �Œ��ڃA�m�e�[�V�������g�p���ꂽ�ꍇ
		ArrayForm2 form = new ArrayForm2();
		form.setField1(new String[]{"����������","����������"});
		form.setField6(new String[]{"abc=efg&hij","zzz=FFF"});


		// �e�X�g���\�b�h�����s
		String paramString = form.getOptionParams();
		System.out.println(paramString);

		
		// �`�F�b�N�p�ɖ����� & ��t��
		paramString += "&";

		
		// ���Ғl�� �u&field1=%E3%81%82%E3%81%84%E3%81%86%E3%81%88%E3%81%8A&
		// &field1=%E3%81%8B%E3%81%8D%E3%81%8F%E3%81%91%E3%81%93&field6=abc%3Defg%26hij&field6=zzz%3DFFF
		if (paramString.indexOf("&field1=%E3%81%82%E3%81%84%E3%81%86%E3%81%88%E3%81%8A&") < 0) Assert.fail("field1 �p�����[�^���������G�X�P�[�v����Ă��Ȃ�");
		if (paramString.indexOf("&field1=%E3%81%8B%E3%81%8D%E3%81%8F%E3%81%91%E3%81%93&") < 0) Assert.fail("field1 �p�����[�^���������G�X�P�[�v����Ă��Ȃ�");
		if (paramString.indexOf("&field6=abc%3Defg%26hij&") < 0) Assert.fail("field6 �p�����[�^���������G�X�P�[�v����Ă��Ȃ�");
		if (paramString.indexOf("&field6=zzz%3DFFF&") < 0) Assert.fail("field6 �p�����[�^���������G�X�P�[�v����Ă��Ȃ�");
		Assert.assertEquals("�p�����[�^������v���Ȃ�", 147, paramString.length());

	}
	
	
	/**
	 * �e�N���X�Ɠ����̃t�B�[���h�����݂���ꍇ<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�e�N���X�̃t�B�[���h���D�悳��Ďg�p����鎖</li>
	 * </ul>
	 */
	@Test
	public void dupFormTest() throws Exception {

		DupForm2 form = new DupForm2();
		
		form.setField1("FLD1");
		form.setField2("FLD2");
		form.setField3(new String[]{"FLD3-1", "FLD3-2"});
		form.setField4(new String[]{"FLD4-1", "FLD4-2"});


		// �e�X�g���\�b�h�����s
		String paramString = form.getOptionParams();
		System.out.println(paramString);

		
		// �`�F�b�N�p�ɖ����� & ��t��
		paramString += "&";

		// ���Ғl�� �u&field1=FLD1_OYA&field3=FLD3-1_OYA&field3=FLD3-2_OYA
		if (paramString.indexOf("&field1=FLD1_OYA&") < 0) Assert.fail("field1 �p�����[�^���e�t�B�[���h�̒l�Ƃ��ďo�͂���Ă��Ȃ�");
		if (paramString.indexOf("&field3=FLD3-1_OYA&") < 0) Assert.fail("field3 �p�����[�^���e�t�B�[���h�̒l�Ƃ��ďo�͂���Ă��Ȃ�");
		if (paramString.indexOf("&field3=FLD3-2_OYA&") < 0) Assert.fail("field3 �p�����[�^���e�t�B�[���h�̒l�Ƃ��ďo�͂���Ă��Ȃ�");
		Assert.assertEquals("�p�����[�^������v���Ȃ�", 53, paramString.length());

	}
}
