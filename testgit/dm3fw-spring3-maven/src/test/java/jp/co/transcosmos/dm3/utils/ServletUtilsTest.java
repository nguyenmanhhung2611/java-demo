package jp.co.transcosmos.dm3.utils;

import org.junit.Assert;
import org.junit.Test;

public class ServletUtilsTest {

	@Test
	public void crToHtmlTagTest() {

		String testValue;
		String ans;

		
		// null �̏ꍇ
		Assert.assertEquals("null �̏ꍇ�A�󕶎��񂪕��A����鎖", "", ServletUtils.crToHtmlTag(null));
		
		// �󕶎���̏ꍇ
		Assert.assertEquals("�󕶎���̏ꍇ�A�󕶎��񂪕��A����鎖", "", ServletUtils.crToHtmlTag(""));

		// �u���Ώە�����A���s�R�[�h���܂܂�Ȃ��ꍇ
		testValue = "���������� ����������";
		Assert.assertEquals("�u���Ώە�����A���s�R�[�h���܂܂�Ȃ��ꍇ�A���̒l�ƈ�v���鎖", testValue, ServletUtils.crToHtmlTag(testValue));

		// �u���Ώە����񂪊܂܂��ꍇ
		testValue = "<p>���������� ����������</p>";
		ans = "&lt;p&gt;���������� ����������&lt;/p&gt;";
		Assert.assertEquals("�u���Ώە�����A���s�R�[�h���܂܂�Ȃ��ꍇ�A���̒l�ƈ�v���鎖", ans, ServletUtils.crToHtmlTag(testValue));

		// �u���Ώە�����A\r\n���܂܂��ꍇ
		testValue = "<p>����������\r\n����������\r\n</p>";
		ans = "&lt;p&gt;����������<br/>����������<br/>&lt;/p&gt;";
		Assert.assertEquals("�u���Ώە�����A���s�R�[�h�i\\r\\n�j���܂܂�Ȃ��ꍇ�A���̒l�ƈ�v���鎖", ans, ServletUtils.crToHtmlTag(testValue));

		// �u���Ώە�����A\r���܂܂��ꍇ
		testValue = "<p>����������\r����������\r</p>";
		ans = "&lt;p&gt;����������<br/>����������<br/>&lt;/p&gt;";
		Assert.assertEquals("�u���Ώە�����A���s�R�[�h�i\\r�j���܂܂�Ȃ��ꍇ�A���̒l�ƈ�v���鎖", ans, ServletUtils.crToHtmlTag(testValue));

		// �u���Ώە�����A\n���܂܂��ꍇ
		testValue = "<p>����������\n����������\n</p>";
		ans = "&lt;p&gt;����������<br/>����������<br/>&lt;/p&gt;";
		Assert.assertEquals("�u���Ώە�����A���s�R�[�h�i\\n�j���܂܂�Ȃ��ꍇ�A���̒l�ƈ�v���鎖", ans, ServletUtils.crToHtmlTag(testValue));

		
	}



	@Test
	public void replaceTest() {

		String targetString;			// �u���Ώە�����
		String replaceFrom;				// �u����������
		String replaceTo;				// �u���敶����


		// �u���Ώە����� null �̏ꍇ
		targetString = null;
		replaceFrom = "hoge";
		replaceTo = "hogehoge";
		Assert.assertEquals("�u���Ώە����� null �̏ꍇ�A�󕶎��񂪕��A���鎖�B", "", ServletUtils.replace(targetString, replaceFrom, replaceTo));
		
		
		// �u���Ώە����񂪋󕶎���̏ꍇ
		targetString = "";
		replaceFrom = "hoge";
		replaceTo = "hogehoge";
		Assert.assertEquals("�u���Ώە����񂪋󕶎���̏ꍇ�A�󕶎��񂪕��A���鎖�B", "", ServletUtils.replace(targetString, replaceFrom, replaceTo));


		// �����̒u�������񂪊܂܂�Ă���ꍇ
		targetString = "abcdhogehogeefg";
		replaceFrom = "hoge";
		replaceTo = "hogehoge";
		Assert.assertEquals("�����̒u�������񂪊܂܂�Ă���ꍇ�A�������u���ł��鎖", "abcdhogehogehogehogeefg", ServletUtils.replace(targetString, replaceFrom, replaceTo));


		// �^�O������ɒu������ꍇ
		targetString = "�����������u����������";
		replaceFrom = "�u";
		replaceTo = "m<sup>2</sup>";
		Assert.assertEquals("�u����Ƀ^�O���܂܂�Ă���ꍇ�A�G�X�P�[�v����Ă��Ȃ��^�O�����A���鎖�B", "����������m<sup>2</sup>����������", ServletUtils.replace(targetString, replaceFrom, replaceTo));

		
		// �u�����ɃG�X�P�[�v���K�v�ȕ����񂪊܂܂�Ă���ꍇ
		targetString = "����<br>�������u������<br>����";
		replaceFrom = "�u";
		replaceTo = "m<sup>2</sup>";
		Assert.assertEquals("�u����Ƀ^�O���܂܂�Ă���ꍇ�A�G�X�P�[�v����Ă��Ȃ��^�O�����A���鎖�B", "����&lt;br&gt;������m<sup>2</sup>������&lt;br&gt;����", ServletUtils.replace(targetString, replaceFrom, replaceTo));

	}

}
