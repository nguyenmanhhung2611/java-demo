package jp.co.transcosmos.dm3.test.core.displayAdapter;

import org.junit.Assert;
import org.junit.Test;

import jp.co.transcosmos.dm3.core.displayAdapter.DisplayUtils;

public class DisplayUtilsTest {

	// ���~�P�ʕ\���e�X�g�i�f�t�H���g�ݒ�j
	@Test
	public void toTenThousandUnitsTest(){
		
		DisplayUtils utils = new DisplayUtils();
		

		// null �̏ꍇ
		String ret = utils.toTenThousandUnits(null);
		Assert.assertEquals("�󕶎��񂪕\������鎖", "", ret);

		// 0 �̏ꍇ
		ret = utils.toTenThousandUnits(0L);
		Assert.assertEquals("0�~���\������鎖", "0�~", ret);

		
		// �P�������̒l�̏ꍇ�i�����_�ȉ��؂�グ�j
		ret = utils.toTenThousandUnits(100L);
		Assert.assertEquals("�ꖜ�����̏ꍇ�ł������_�ȉ����؂�グ���Ă��鎖", "1���~", ret);
		
		// �P���̏ꍇ�i�����_�ȉ��؂�グ�j
		ret = utils.toTenThousandUnits(10000L);
		Assert.assertEquals("�ꖜ�̏ꍇ�ł����������H����鎖", "1���~", ret);

		// �P���P��̏ꍇ�i�����_�ȉ��؂�グ�j
		ret = utils.toTenThousandUnits(11000L);
		Assert.assertEquals("�ꖜ�𒴂���ꍇ�ł��A�����_�ȉ����؏グ���鎖", "2���~", ret);

		// �P�P���̏ꍇ�i�����_�ȉ��؂�グ�j
		ret = utils.toTenThousandUnits(110000L);
		Assert.assertEquals("�ꖜ�����̏ꍇ�ł������_�ȉ����؂�グ���Ă��鎖", "11���~", ret);
		
		// ���H��A�S�����z����ꍇ�A�J���}�ҏW����鎖�i�����_�ȉ��؂�グ�j
		ret = utils.toTenThousandUnits(11000000000L);
		Assert.assertEquals("�ꖜ�����̏ꍇ�ł������_�ȉ����؂�グ���Ă��鎖", "1,100,000���~", ret);
		
		
		// �����_�ȉ��P�����w��
		utils.setPriceRoundScale(1);

		// �P�������̒l�̏ꍇ�i�����_�ȉ��؂�グ�j
		ret = utils.toTenThousandUnits(100L);
		Assert.assertEquals("100 �̏ꍇ�A�w�肵�������_�ȉ��ɐ؂�グ���鎖", "0.1���~", ret);

		// �P�������̒l�̏ꍇ�i�����_�ȉ��؂�グ�j
		ret = utils.toTenThousandUnits(1000L);
		Assert.assertEquals("�ꖜ�����̏ꍇ�ł��w�肵�������_�ȉ��ɐ؂�グ���Ă��鎖", "0.1���~", ret);
		
		// �P���̏ꍇ�i�����_�ȉ��؂�グ�j
		ret = utils.toTenThousandUnits(10000L);
		Assert.assertEquals("�ꖜ�̏ꍇ�ł����������H����鎖", "1���~", ret);

		// �P���P��̏ꍇ�i�����_�ȉ��؂�グ�j
		ret = utils.toTenThousandUnits(11000L);
		Assert.assertEquals("�ꖜ�𒴂���ꍇ�ł��A�����_�ȉ����؏グ���鎖", "1.1���~", ret);

		// �P�P���̏ꍇ�i�����_�ȉ��؂�グ�j
		ret = utils.toTenThousandUnits(110000L);
		Assert.assertEquals("�ꖜ�����̏ꍇ�ł������_�ȉ����؂�グ���Ă��鎖", "11���~", ret);
		
		// ���H��A�S�����z����ꍇ�A�J���}�ҏW����鎖�i�����_�ȉ��؂�グ�j
		ret = utils.toTenThousandUnits(11000000000L);
		Assert.assertEquals("�ꖜ�����̏ꍇ�ł������_�ȉ����؂�グ���Ă��鎖", "1,100,000���~", ret);
		
		
		
		
	}
	
	
	
}
