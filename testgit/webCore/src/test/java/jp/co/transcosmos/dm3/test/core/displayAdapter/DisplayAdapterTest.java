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

	// Display Adapter �̐e�N���X + VO �̐e�N���X�̃P�[�X
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
		
		Assert.assertEquals("String �v���p�e�B�̈Ϗ��悪����ɓ��삷�鎖", "ABC", adapter.getDisplayValue(vo, "fieldName1"));
		Assert.assertEquals("Integer �v���p�e�B�̈Ϗ��悪����ɓ��삷�鎖", Integer.valueOf(100), adapter.getDisplayValue(vo, "fieldName2"));
		Assert.assertEquals("int �v���p�e�B�̈Ϗ��悪����ɓ��삷�鎖", 200, adapter.getDisplayValue(vo, "fieldName3"));
		Assert.assertEquals("String �v���p�e�B�̃I�[�o�[���C�h������ɓ��삷�鎖", "EFGXYZ", adapter.getDisplayValue(vo, "fieldName11"));
		Assert.assertEquals("Integer �v���p�e�B�̃I�[�o�[���C�h������ɓ��삷�鎖", Integer.valueOf(1050), adapter.getDisplayValue(vo, "fieldName12"));
		Assert.assertEquals("int �v���p�e�B�̃I�[�o�[���C�h������ɓ��삷�鎖", 2055, adapter.getDisplayValue(vo, "fieldName13"));

		
		// �L���b�V���̊m�F
		vo = new BaseVo();
		vo.setFieldName1("ABCC");
		vo.setFieldName2(1000);
		vo.setFieldName3(2000);
		vo.setFieldName11("EFGG");
		vo.setFieldName12(10000);
		vo.setFieldName13(20000);


		adapter = new BaseDisplayAdapter();
		Assert.assertEquals("String �v���p�e�B�̈Ϗ��悪����ɓ��삷�鎖", "ABCC", adapter.getDisplayValue(vo, "fieldName1"));
		Assert.assertEquals("Integer �v���p�e�B�̈Ϗ��悪����ɓ��삷�鎖", Integer.valueOf(1000), adapter.getDisplayValue(vo, "fieldName2"));
		Assert.assertEquals("int �v���p�e�B�̈Ϗ��悪����ɓ��삷�鎖", 2000, adapter.getDisplayValue(vo, "fieldName3"));
		Assert.assertEquals("String �v���p�e�B�̃I�[�o�[���C�h������ɓ��삷�鎖", "EFGGXYZ", adapter.getDisplayValue(vo, "fieldName11"));
		Assert.assertEquals("Integer �v���p�e�B�̃I�[�o�[���C�h������ɓ��삷�鎖", Integer.valueOf(10050), adapter.getDisplayValue(vo, "fieldName12"));
		Assert.assertEquals("int �v���p�e�B�̃I�[�o�[���C�h������ɓ��삷�鎖", 20055, adapter.getDisplayValue(vo, "fieldName13"));
		
	}
	

	
	// Display Adapter �̐e�N���X + VO �̎q�N���X�̃P�[�X
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
		
		Assert.assertEquals("String �v���p�e�B�̈Ϗ��悪����ɓ��삷�鎖", "ABC", adapter.getDisplayValue(vo, "fieldName1"));
		Assert.assertEquals("Integer �v���p�e�B�̈Ϗ��悪����ɓ��삷�鎖", Integer.valueOf(100), adapter.getDisplayValue(vo, "fieldName2"));
		Assert.assertEquals("int �v���p�e�B�̈Ϗ��悪����ɓ��삷�鎖", 200, adapter.getDisplayValue(vo, "fieldName3"));
		Assert.assertEquals("String �v���p�e�B�̃I�[�o�[���C�h������ɓ��삷�鎖", "EFGXYZ", adapter.getDisplayValue(vo, "fieldName11"));
		Assert.assertEquals("Integer �v���p�e�B�̃I�[�o�[���C�h������ɓ��삷�鎖", Integer.valueOf(1050), adapter.getDisplayValue(vo, "fieldName12"));
		Assert.assertEquals("int �v���p�e�B�̃I�[�o�[���C�h������ɓ��삷�鎖", 2055, adapter.getDisplayValue(vo, "fieldName13"));
		Assert.assertEquals("String �ǉ��v���p�e�B�̈Ϗ��悪����ɓ��삷�鎖", "HIJ", adapter.getDisplayValue(vo, "fieldName21"));
		Assert.assertEquals("Integer �ǉ��v���p�e�B�̈Ϗ��悪����ɓ��삷�鎖", Integer.valueOf(500), adapter.getDisplayValue(vo, "fieldName22"));
		Assert.assertEquals("int �ǉ��v���p�e�B�̈Ϗ��悪����ɓ��삷�鎖", 600, adapter.getDisplayValue(vo, "fieldName23"));


		// �L���b�V���̊m�F
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

		Assert.assertEquals("String �v���p�e�B�̈Ϗ��悪����ɓ��삷�鎖", "ABCC", adapter.getDisplayValue(vo, "fieldName1"));
		Assert.assertEquals("Integer �v���p�e�B�̈Ϗ��悪����ɓ��삷�鎖", Integer.valueOf(1000), adapter.getDisplayValue(vo, "fieldName2"));
		Assert.assertEquals("int �v���p�e�B�̈Ϗ��悪����ɓ��삷�鎖", 2000, adapter.getDisplayValue(vo, "fieldName3"));
		Assert.assertEquals("String �v���p�e�B�̃I�[�o�[���C�h������ɓ��삷�鎖", "EFGGXYZ", adapter.getDisplayValue(vo, "fieldName11"));
		Assert.assertEquals("Integer �v���p�e�B�̃I�[�o�[���C�h������ɓ��삷�鎖", Integer.valueOf(10050), adapter.getDisplayValue(vo, "fieldName12"));
		Assert.assertEquals("int �v���p�e�B�̃I�[�o�[���C�h������ɓ��삷�鎖", 20055, adapter.getDisplayValue(vo, "fieldName13"));
		Assert.assertEquals("String �ǉ��v���p�e�B�̈Ϗ��悪����ɓ��삷�鎖", "HIJJ", adapter.getDisplayValue(vo, "fieldName21"));
		Assert.assertEquals("Integer �ǉ��v���p�e�B�̈Ϗ��悪����ɓ��삷�鎖", Integer.valueOf(5000), adapter.getDisplayValue(vo, "fieldName22"));
		Assert.assertEquals("int �ǉ��v���p�e�B�̈Ϗ��悪����ɓ��삷�鎖", 6000, adapter.getDisplayValue(vo, "fieldName23"));

		
	}

	
	
	// Display Adapter �̎q�N���X + VO �̐e�N���X�̃P�[�X
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
		
		Assert.assertEquals("String �v���p�e�B�̈Ϗ��悪����ɓ��삷�鎖", "ABC", adapter.getDisplayValue(vo, "fieldName1"));
		Assert.assertEquals("Integer �v���p�e�B�̈Ϗ��悪����ɓ��삷�鎖", Integer.valueOf(100), adapter.getDisplayValue(vo, "fieldName2"));
		Assert.assertEquals("int �v���p�e�B�̈Ϗ��悪����ɓ��삷�鎖", 200, adapter.getDisplayValue(vo, "fieldName3"));
		Assert.assertEquals("String �v���p�e�B��Adapter�I�[�o�[���C�h������ɓ��삷�鎖", "EFGSTU", adapter.getDisplayValue(vo, "fieldName11"));
		Assert.assertEquals("Integer �v���p�e�B��Adapter�I�[�o�[���C�h������ɓ��삷�鎖", Integer.valueOf(1300), adapter.getDisplayValue(vo, "fieldName12"));
		Assert.assertEquals("int �v���p�e�B��Adapter�I�[�o�[���C�h������ɓ��삷�鎖", 2355, adapter.getDisplayValue(vo, "fieldName13"));


		// �L���b�V���̊m�F
		vo = new BaseVo();
		vo.setFieldName1("ABCC");
		vo.setFieldName2(1000);
		vo.setFieldName3(2000);
		vo.setFieldName11("EFGG");
		vo.setFieldName12(10000);
		vo.setFieldName13(20000);


		adapter = new ChiDisplayAdapter();
		
		Assert.assertEquals("String �v���p�e�B�̈Ϗ��悪����ɓ��삷�鎖", "ABCC", adapter.getDisplayValue(vo, "fieldName1"));
		Assert.assertEquals("Integer �v���p�e�B�̈Ϗ��悪����ɓ��삷�鎖", Integer.valueOf(1000), adapter.getDisplayValue(vo, "fieldName2"));
		Assert.assertEquals("int �v���p�e�B�̈Ϗ��悪����ɓ��삷�鎖", 2000, adapter.getDisplayValue(vo, "fieldName3"));
		Assert.assertEquals("String �v���p�e�B��Adapter�I�[�o�[���C�h������ɓ��삷�鎖", "EFGGSTU", adapter.getDisplayValue(vo, "fieldName11"));
		Assert.assertEquals("Integer �v���p�e�B��Adapter�I�[�o�[���C�h������ɓ��삷�鎖", Integer.valueOf(10300), adapter.getDisplayValue(vo, "fieldName12"));
		Assert.assertEquals("int �v���p�e�B��Adapter�I�[�o�[���C�h������ɓ��삷�鎖", 20355, adapter.getDisplayValue(vo, "fieldName13"));
	}

	
	// Display Adapter �̎q�N���X + VO �̎q�N���X�̃P�[�X
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
		
		Assert.assertEquals("String �v���p�e�B�̈Ϗ��悪����ɓ��삷�鎖", "ABC", adapter.getDisplayValue(vo, "fieldName1"));
		Assert.assertEquals("Integer �v���p�e�B�̈Ϗ��悪����ɓ��삷�鎖", Integer.valueOf(100), adapter.getDisplayValue(vo, "fieldName2"));
		Assert.assertEquals("int �v���p�e�B�̈Ϗ��悪����ɓ��삷�鎖", 200, adapter.getDisplayValue(vo, "fieldName3"));
		Assert.assertEquals("String �v���p�e�B�̃I�[�o�[���C�h������ɓ��삷�鎖", "EFGXYZ", adapter.getDisplayValue(vo, "fieldName11"));
		Assert.assertEquals("Integer �v���p�e�B�̃I�[�o�[���C�h������ɓ��삷�鎖", Integer.valueOf(1050), adapter.getDisplayValue(vo, "fieldName12"));
		Assert.assertEquals("int �v���p�e�B�̃I�[�o�[���C�h������ɓ��삷�鎖", 2055, adapter.getDisplayValue(vo, "fieldName13"));
		Assert.assertEquals("String �ǉ��v���p�e�B��Adapter�I�[�o�[���C�h������ɓ��삷�鎖", "HIJUUU", adapter.getDisplayValue(vo, "fieldName21"));
		Assert.assertEquals("Integer �ǉ��v���p�e�B��Adapter�I�[�o�[���C�h������ɓ��삷�鎖", Integer.valueOf(900), adapter.getDisplayValue(vo, "fieldName22"));
		Assert.assertEquals("int �ǉ��v���p�e�B��Adapter�I�[�o�[���C�h������ɓ��삷�鎖", 1040, adapter.getDisplayValue(vo, "fieldName23"));


		// �L���b�V���̊m�F
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
		
		Assert.assertEquals("String �v���p�e�B�̈Ϗ��悪����ɓ��삷�鎖", "ABCC", adapter.getDisplayValue(vo, "fieldName1"));
		Assert.assertEquals("Integer �v���p�e�B�̈Ϗ��悪����ɓ��삷�鎖", Integer.valueOf(1000), adapter.getDisplayValue(vo, "fieldName2"));
		Assert.assertEquals("int �v���p�e�B�̈Ϗ��悪����ɓ��삷�鎖", 2000, adapter.getDisplayValue(vo, "fieldName3"));
		Assert.assertEquals("String �v���p�e�B�̃I�[�o�[���C�h������ɓ��삷�鎖", "EFGGXYZ", adapter.getDisplayValue(vo, "fieldName11"));
		Assert.assertEquals("Integer �v���p�e�B�̃I�[�o�[���C�h������ɓ��삷�鎖", Integer.valueOf(10050), adapter.getDisplayValue(vo, "fieldName12"));
		Assert.assertEquals("int �v���p�e�B�̃I�[�o�[���C�h������ɓ��삷�鎖", 20055, adapter.getDisplayValue(vo, "fieldName13"));
		Assert.assertEquals("String �ǉ��v���p�e�B��Adapter�I�[�o�[���C�h������ɓ��삷�鎖", "HIJJUUU", adapter.getDisplayValue(vo, "fieldName21"));
		Assert.assertEquals("Integer �ǉ��v���p�e�B��Adapter�I�[�o�[���C�h������ɓ��삷�鎖", Integer.valueOf(5400), adapter.getDisplayValue(vo, "fieldName22"));
		Assert.assertEquals("int �ǉ��v���p�e�B��Adapter�I�[�o�[���C�h������ɓ��삷�鎖", 6440, adapter.getDisplayValue(vo, "fieldName23"));

	}

	
	// vo �� mix ���Ďg�p
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

		
		// �e�X�g���\�b�h�����s
		DisplayAdapter adapter = new ChiDisplayAdapter();
		

		// �O�̂��߁A�Q����s����B
		for (int i=0; i<2; ++i){
			// �e Adapter �ɕ�VO �̓������\�b�h�����݂���
			String ret =  (String) adapter.getDisplayValue(vo, "fieldName1");
			Assert.assertEquals("�Ϗ���̃��\�b�h�����s����鎖", "ABC", ret);

			// �e Adapter �ɕ�VO �̓������\�b�h�����݂���
			Integer retInt =  (Integer) adapter.getDisplayValue(vo, "fieldName2");
			Assert.assertEquals("�Ϗ���̃��\�b�h�����s����鎖", Integer.valueOf(100), retInt);

			// ���g�� Adapter �ɕ�VO �� �����Ǝ��g�� VO �̃��\�b�h���������݂���
			ret =  (String) adapter.getDisplayValue(vo, "fieldName11");
			Assert.assertEquals("���g�� Adapter �̃��\�b�h�����s����鎖", "EFGSTU", ret);

			//�@���g�� Apater �ɎqVO �̃��\�b�h�����邪�A�e Adapter �� ���g�� VO ���\�b�h������
			ret =  (String) adapter.getDisplayValue(otherVo1, "fieldName1");
			Assert.assertEquals("�e Adapter �̃��\�b�h���g�p����鎖", "NNNTTT", ret);
			
			// ���g�� Apater �ɎqVO �̃��\�b�h������
			ret =  (String) adapter.getDisplayValue(otherVo1, "fieldName2");
			Assert.assertEquals("�Ϗ��� �̃��\�b�h���g�p����鎖", "nnn", ret);

			// ��VO �p�^�[��
			ret =  (String) adapter.getDisplayValue(otherVo2, "fieldName1");
			Assert.assertEquals("�e Adapter �̃��\�b�h���g�p����鎖", "MMMTTT", ret);
			
			// ��VO �p�^�[��
			ret =  (String) adapter.getDisplayValue(otherVo2, "fieldName2");
			Assert.assertEquals("�Ϗ��� �̃��\�b�h���g�p����鎖", "mmm", ret);

			// ���g�� Adapter �Ɏ��g�� VO �̃��\�b�h�����݂��A�e Adapter �ɁA�e VO �̃��\�b�h�����݂���B
			ret =  (String) adapter.getDisplayValue(chiOtherVo1, "fieldName1");
			Assert.assertEquals("���g�� Adapter �̃��\�b�h���g�p����鎖", "OOORRR", ret);
			
			// �Y�����\�b�h�Ȃ�
			ret =  (String) adapter.getDisplayValue(chiOtherVo1, "fieldName2");
			Assert.assertEquals("�Ϗ��� �̃��\�b�h���g�p����鎖", "ooo", ret);
			
			// ���g�� Adapter �Ɏ��g�� VO �̃��\�b�h�����݂��A�e Adapter �Ɉˑ��֌W�̖���VO�̓������\�b�h����
			ret =  (String) adapter.getDisplayValue(chiOtherVo1, "fieldName11");
			Assert.assertEquals("���g�� Adapter �̃��\�b�h���g�p����鎖", "OOOOOGGG", ret);

			// ��VO �p�^�[��
			ret =  (String) adapter.getDisplayValue(chiOtherVo2, "fieldName1");
			Assert.assertEquals("���g�� Adapter �̃��\�b�h���g�p����鎖", "PPPRRR", ret);
			
			ret =  (String) adapter.getDisplayValue(chiOtherVo2, "fieldName2");
			Assert.assertEquals("�Ϗ��� �̃��\�b�h���g�p����鎖", "ppp", ret);

			ret =  (String) adapter.getDisplayValue(chiOtherVo2, "fieldName11");
			Assert.assertEquals("���g�� Adapter �̃��\�b�h���g�p����鎖", "PPPPPGGG", ret);
		}

	}
}
