package jp.co.transcosmos.dm3.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.transcosmos.mock.vo.GoupByJoinTestVo;
import jp.co.transcosmos.mock.vo.GoupByRefTestVo;
import jp.co.transcosmos.mock.vo.TestLongNameVo;

import org.junit.Assert;
import org.junit.Test;

/**
 * GroupByDAOU �̃e�X�g�P�[�X<br/>
 */
public class GroupByDAOTest {

	/**
	 * �Ϗ��悪 ReflectionDAO �̏ꍇ�ɂ�����A�Ϗ���̏����ɂ���ʖ��o�̓e�X�g<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�ʖ��w�薳���łR�O�����𒴂��Ă���ꍇ�A�؂�l�߂��Ă��鎖</li>
	 *     <li>�ʖ��w��L��łR�O�����𒴂��Ă���ꍇ�A�؂�l�߂��Ă��鎖</li>
	 *     <li>�ʖ��w�薳���łR�O�����𒴂��ďd�����Ă���ꍇ�A�i���o�����O����Ă��鎖</li>
	 *     <li>�ʖ��w�肠��łR�O�����𒴂��ďd�����Ă���ꍇ�A�i���o�����O����Ă��鎖</li>
	 *     <li>�ʖ��w�薳���łR�O�����𒴂��Ă��Ȃ��ꍇ�A�e�[�u���ʖ������ŕʖ����o�͂���鎖</li>
	 *     <li>�ʖ��w�肠��łR�O�����𒴂��Ă��Ȃ��ꍇ�A�e�[�u���ʖ��L��ŕʖ����o�͂���鎖</li>
	 * </ul>
	 */
	@Test
	public void getFieldAliasesByRefDAOTest() throws Exception{

		// �Ϗ���� ReflectingDAO ���쐬
		ReflectingDAO<TestLongNameVo> dao = new ReflectingDAO<>();
		dao.setValueObjectClassName("jp.co.transcosmos.mock.vo.TestLongNameVo");

		// �e�X�g�Ώ� GroupByDAO ���쐬
		GroupByDAO<?> groupDao = new GroupByDAO<>();
		groupDao.setTargetDAO(dao);

		String[] thisAlias = new String[]{null, "a1", null, "a1", "a2", "a1", null};
		String[] forFieldNames = new String[]{"xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA",
											  "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxB",
											  "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxC",
											  "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD",
											  "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxE",
											  "col1Name",
											  "col2Name"};
		String[] thisFunctionAlias = new String[]{null, null, null, null, null, null, null};

		// ���t���N�V�����ŁA�e�X�g���\�b�h�����s
		String[] aliases = executeGetFieldAliases(groupDao, thisAlias, forFieldNames, thisFunctionAlias);

		Assert.assertEquals("�߂�l�̔z�񐔂���������", aliases.length, forFieldNames.length);
		Assert.assertEquals("�ʖ��w�薳���łR�O�����𒴂��Ă���ꍇ�A�؂�l�߂��Ă��鎖", aliases[0], "xxxxxxxxxx_xxxxxxxxxx_xxxxxxxx");
		Assert.assertEquals("�ʖ��w��L��łR�O�����𒴂��Ă���ꍇ�A�؂�l�߂��Ă��鎖", aliases[1], "a1_xxxxxxxxxx_xxxxxxxxxx_xxxxx");
		Assert.assertEquals("�ʖ��w�薳���łR�O�����𒴂��ďd�����Ă���ꍇ�A�i���o�����O����Ă��鎖", aliases[2], "xxxxxxxxxx_xxxxxxxxxx_xxxxx000");
		Assert.assertEquals("�ʖ��w��L��łR�O�����𒴂��ďd�����Ă���ꍇ�A�i���o�����O����Ă��鎖", aliases[3], "a1_xxxxxxxxxx_xxxxxxxxxx_xx000");
		Assert.assertEquals("�ʖ����قȂ�̂ŏd�����Ȃ��ꍇ�A�������o�͂���Ă��鎖", aliases[4], "a2_xxxxxxxxxx_xxxxxxxxxx_xxxxx");
		Assert.assertEquals("�ʖ��w��L��łR�O�����𒴂��Ă��Ȃ��ꍇ�A�������o�͂���Ă��鎖", aliases[5], "a1_col1_name");
		Assert.assertEquals("�ʖ��w�薳���łR�O�����𒴂��Ă��Ȃ��ꍇ�A�������o�͂���Ă��鎖", aliases[6], "col2_name");

	}

	
	
	/**
	 * �Ϗ��悪 JoinDAO �̏ꍇ�ɂ�����A�Ϗ���̏����ɂ���ʖ��o�̓e�X�g<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�ʖ��w�薳���łR�O�����𒴂��Ă���ꍇ�A�؂�l�߂��Ă��鎖</li>
	 *     <li>�ʖ��w��L��łR�O�����𒴂��Ă���ꍇ�A�؂�l�߂��Ă��鎖</li>
	 *     <li>�ʖ��w�薳���łR�O�����𒴂��ďd�����Ă���ꍇ�A�i���o�����O����Ă��鎖</li>
	 *     <li>�ʖ��w�肠��łR�O�����𒴂��ďd�����Ă���ꍇ�A�i���o�����O����Ă��鎖</li>
	 *     <li>�ʖ��w�薳���łR�O�����𒴂��Ă��Ȃ��ꍇ�A�e�[�u���ʖ������ŕʖ����o�͂���鎖</li>
	 *     <li>�ʖ��w�肠��łR�O�����𒴂��Ă��Ȃ��ꍇ�A�e�[�u���ʖ��L��ŕʖ����o�͂���鎖</li>
	 *     <li>JoinDAO ���Őݒ肵���e�[�u���ʖ����g�p����鎖</li>
	 *     <li>�e�[�u���ʖ����w�肵���ꍇ�AJoinDAO ���Őݒ肵���e�[�u���ʖ������D�悳��鎖</li>
	 *     <li>�����̗񖼂������� DAO �ɑ��݂���ꍇ�A�ŏ��Ɍ������� DAO �̃e�[�u���ʖ����g�p����鎖�B</li>
	 * </ul>
	 */
	@Test
	public void getFieldAliasesByJoinDAOTest() throws Exception{

		// �Ϗ���� JonDAO ���쐬
		ReflectingDAO<TestLongNameVo> mainDao = new ReflectingDAO<>();
		mainDao.setValueObjectClassName("jp.co.transcosmos.mock.vo.TestLongNameVo");

		ReflectingDAO<TestLongNameVo> subDao = new ReflectingDAO<>();
		subDao.setValueObjectClassName("jp.co.transcosmos.mock.vo.TestLongNameSubVo");
		
		JoinDAO joinDao = new JoinDAO();
		joinDao.setJoinable1(mainDao);
		joinDao.setJoinable2(subDao);

		JoinCondition condition = new JoinCondition();
		condition.setField1("col1Name");
		condition.setField2("subCol1Name");
		joinDao.setCondition(condition);
		
		
		// �e�X�g�Ώ� GroupByDAO ���쐬
		GroupByDAO<?> groupDao = new GroupByDAO<>();
		groupDao.setTargetDAO(joinDao);

		String[] thisAlias = new String[]{null, "dao1", null, "dao1", "dao2", "dao1", null,
				                          null, "dao2", null, "dao2", null, "dao2", null};
		String[] forFieldNames = new String[]{"xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA",	// mainDAO
											  "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxB",
											  "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxC",
											  "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD",
											  "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxE",
											  "col1Name",
											  "col2Name",
											  "subXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA",	// subDAO
											  "subXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxB",
											  "subXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxC",
											  "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD",
											  "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxE",
											  "subCol1Name",
											  "subCol2Name"};

		String[] thisFunctionAlias = new String[]{null, null, null, null, null, null, null,
												  null, null, null, null, null, null, null};

		// ���t���N�V�����ŁA�e�X�g���\�b�h�����s
		String[] aliases = executeGetFieldAliases(groupDao, thisAlias, forFieldNames, thisFunctionAlias);

		Assert.assertEquals("�߂�l�̔z�񐔂���������", aliases.length, forFieldNames.length);
		Assert.assertEquals("�ʖ��w�薳���łR�O�����𒴂��Ă���ꍇ�A�f�t�H���g�̕ʖ����g�p����Đ؂�l�߂��Ă��鎖", aliases[0], "dao1_xxxxxxxxxx_xxxxxxxxxx_xxx");
		Assert.assertEquals("�ʖ��w��L��łR�O�����𒴂��Ă���ꍇ�A�w�肵���ʖ����g�p����Đ؂�l�߂��Ă��鎖", aliases[1], "dao1_xxxxxxxxxx_xxxxxxxxxx_000");
		Assert.assertEquals("�ʖ��w�薳���łR�O�����𒴂��ďd�����Ă���ꍇ�A�i���o�����O����Ă��鎖", aliases[2], "dao1_xxxxxxxxxx_xxxxxxxxxx_001");
		Assert.assertEquals("�ʖ��w��L��łR�O�����𒴂��ďd�����Ă���ꍇ�A�i���o�����O����Ă��鎖", aliases[3], "dao1_xxxxxxxxxx_xxxxxxxxxx_002");
		Assert.assertEquals("�ʖ��w��L��łR�O�����𒴂��ďd�����Ȃ��ꍇ�A�w�肵���ʖ����g�p����Đ؂�l�߂��Ă��鎖", aliases[4], "dao2_xxxxxxxxxx_xxxxxxxxxx_xxx");
		Assert.assertEquals("�ʖ��w��L��łR�O�����𒴂��Ă��Ȃ��ꍇ�A�������o�͂���Ă��鎖", aliases[5], "dao1_col1_name");
		Assert.assertEquals("�ʖ��w�薳���łR�O�����𒴂��Ă��Ȃ��ꍇ�A�������o�͂���Ă��鎖", aliases[6], "dao1_col2_name");

		Assert.assertEquals("�ʖ��w�薳���łR�O�����𒴂��Ă���ꍇ�A�f�t�H���g�̕ʖ����g�p����Đ؂�l�߂��Ă��鎖", aliases[7], "dao2_sub_xxxxxxxxxx_xxxxxxxxxx");
		Assert.assertEquals("�ʖ��w��L��łR�O�����𒴂��Ă���ꍇ�A�w�肵���ʖ����g�p����Đ؂�l�߂��Ă��鎖", aliases[8], "dao2_sub_xxxxxxxxxx_xxxxxxx000");
		Assert.assertEquals("�ʖ��w�薳���łR�O�����𒴂��ďd�����Ă���ꍇ�A�i���o�����O����Ă��鎖", aliases[9], "dao2_sub_xxxxxxxxxx_xxxxxxx001");
		Assert.assertEquals("�ʖ��w��L��łR�O�����𒴂��ďd�����Ă���ꍇ�A�i���o�����O����Ă��鎖", aliases[10], "dao2_xxxxxxxxxx_xxxxxxxxxx_000");
		Assert.assertEquals("�ʖ��w�Ȃ��ŁA�����̃t�B�[���h�������e�[�u���ɑ��݂���ꍇ�A�ŏ��Ɍ������� DAO �̃e�[�u���ʖ����g�p����鎖", aliases[11], "dao1_xxxxxxxxxx_xxxxxxxxxx_003");
		Assert.assertEquals("�ʖ��w��L��łR�O�����𒴂��Ă��Ȃ��ꍇ�A�������o�͂���Ă��鎖", aliases[12], "dao2_sub_col1_name");
		Assert.assertEquals("�ʖ��w�薳���łR�O�����𒴂��Ă��Ȃ��ꍇ�A�������o�͂���Ă��鎖", aliases[13], "dao2_sub_col2_name");

	}



	/**
	 * �Ϗ��悪 StarJoinDAO �̏ꍇ�ɂ�����A�Ϗ���̏����ɂ���ʖ��o�̓e�X�g<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�ʖ��w�薳���łR�O�����𒴂��Ă���ꍇ�A�؂�l�߂��Ă��鎖</li>
	 *     <li>�ʖ��w��L��łR�O�����𒴂��Ă���ꍇ�A�؂�l�߂��Ă��鎖</li>
	 *     <li>�ʖ��w�薳���łR�O�����𒴂��ďd�����Ă���ꍇ�A�i���o�����O����Ă��鎖</li>
	 *     <li>�ʖ��w�肠��łR�O�����𒴂��ďd�����Ă���ꍇ�A�i���o�����O����Ă��鎖</li>
	 *     <li>�ʖ��w�薳���łR�O�����𒴂��Ă��Ȃ��ꍇ�A�e�[�u���ʖ������ŕʖ����o�͂���鎖</li>
	 *     <li>�ʖ��w�肠��łR�O�����𒴂��Ă��Ȃ��ꍇ�A�e�[�u���ʖ��L��ŕʖ����o�͂���鎖</li>
	 *     <li>JoinDAO ���Őݒ肵���e�[�u���ʖ����g�p����鎖</li>
	 *     <li>�e�[�u���ʖ����w�肵���ꍇ�AJoinDAO ���Őݒ肵���e�[�u���ʖ������D�悳��鎖</li>
	 *     <li>�����̗񖼂������� DAO �ɑ��݂���ꍇ�A�ŏ��Ɍ������� DAO �̃e�[�u���ʖ����g�p����鎖�B</li>
	 * </ul>
	 */
	@Test
	public void getFieldAliasesByStarJoinDAOTest() throws Exception{

		// �Ϗ���� JonDAO ���쐬
		ReflectingDAO<TestLongNameVo> mainDao = new ReflectingDAO<>();
		mainDao.setValueObjectClassName("jp.co.transcosmos.mock.vo.TestLongNameVo");

		ReflectingDAO<TestLongNameVo> subDao = new ReflectingDAO<>();
		subDao.setValueObjectClassName("jp.co.transcosmos.mock.vo.TestLongNameSubVo");
		
		// start join DAO
		StarJoinDAO joinDao = new StarJoinDAO();
		joinDao.setMainDAO(mainDao);
		joinDao.setMainAlias("dao1");

		// ��������
		JoinCondition condition = new JoinCondition();
		condition.setField1("col1Name");
		condition.setField2("subCol1Name");

		// StarJoin �̎q�\ DAO1
		StarJoinDAODescriptor descriptor = new StarJoinDAODescriptor();
		descriptor.setDao(subDao);
		descriptor.setAlias("dao2");
		descriptor.setCondition(condition);

		List<StarJoinDAODescriptor> childDAOs = Arrays.asList(descriptor);
		joinDao.setChildDAOs(childDAOs);


		
		// �e�X�g�Ώ� GroupByDAO ���쐬
		GroupByDAO<?> groupDao = new GroupByDAO<>();
		groupDao.setTargetDAO(joinDao);

		String[] thisAlias = new String[]{null, "dao1", null, "dao1", "dao2", "dao1", null,
				                          null, "dao2", null, "dao2", null, "dao2", null};
		String[] forFieldNames = new String[]{"xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA",	// mainDAO
											  "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxB",
											  "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxC",
											  "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD",
											  "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxE",
											  "col1Name",
											  "col2Name",
											  "subXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA",	// subDAO
											  "subXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxB",
											  "subXxxxxxxxxxXxxxxxxxxxXxxxxxxxxxC",
											  "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD",
											  "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxE",
											  "subCol1Name",
											  "subCol2Name"};

		String[] thisFunctionAlias = new String[]{null, null, null, null, null, null, null,
												  null, null, null, null, null, null, null};

		// ���t���N�V�����ŁA�e�X�g���\�b�h�����s
		String[] aliases = executeGetFieldAliases(groupDao, thisAlias, forFieldNames, thisFunctionAlias);

		Assert.assertEquals("�߂�l�̔z�񐔂���������", aliases.length, forFieldNames.length);
		Assert.assertEquals("�ʖ��w�薳���łR�O�����𒴂��Ă���ꍇ�A�f�t�H���g�̕ʖ����g�p����Đ؂�l�߂��Ă��鎖", aliases[0], "dao1_xxxxxxxxxx_xxxxxxxxxx_xxx");
		Assert.assertEquals("�ʖ��w��L��łR�O�����𒴂��Ă���ꍇ�A�w�肵���ʖ����g�p����Đ؂�l�߂��Ă��鎖", aliases[1], "dao1_xxxxxxxxxx_xxxxxxxxxx_000");
		Assert.assertEquals("�ʖ��w�薳���łR�O�����𒴂��ďd�����Ă���ꍇ�A�i���o�����O����Ă��鎖", aliases[2], "dao1_xxxxxxxxxx_xxxxxxxxxx_001");
		Assert.assertEquals("�ʖ��w��L��łR�O�����𒴂��ďd�����Ă���ꍇ�A�i���o�����O����Ă��鎖", aliases[3], "dao1_xxxxxxxxxx_xxxxxxxxxx_002");
		Assert.assertEquals("�ʖ��w��L��łR�O�����𒴂��ďd�����Ȃ��ꍇ�A�w�肵���ʖ����g�p����Đ؂�l�߂��Ă��鎖", aliases[4], "dao2_xxxxxxxxxx_xxxxxxxxxx_xxx");
		Assert.assertEquals("�ʖ��w��L��łR�O�����𒴂��Ă��Ȃ��ꍇ�A�������o�͂���Ă��鎖", aliases[5], "dao1_col1_name");
		Assert.assertEquals("�ʖ��w�薳���łR�O�����𒴂��Ă��Ȃ��ꍇ�A�������o�͂���Ă��鎖", aliases[6], "dao1_col2_name");

		Assert.assertEquals("�ʖ��w�薳���łR�O�����𒴂��Ă���ꍇ�A�f�t�H���g�̕ʖ����g�p����Đ؂�l�߂��Ă��鎖", aliases[7], "dao2_sub_xxxxxxxxxx_xxxxxxxxxx");
		Assert.assertEquals("�ʖ��w��L��łR�O�����𒴂��Ă���ꍇ�A�w�肵���ʖ����g�p����Đ؂�l�߂��Ă��鎖", aliases[8], "dao2_sub_xxxxxxxxxx_xxxxxxx000");
		Assert.assertEquals("�ʖ��w�薳���łR�O�����𒴂��ďd�����Ă���ꍇ�A�i���o�����O����Ă��鎖", aliases[9], "dao2_sub_xxxxxxxxxx_xxxxxxx001");
		Assert.assertEquals("�ʖ��w��L��łR�O�����𒴂��ďd�����Ă���ꍇ�A�i���o�����O����Ă��鎖", aliases[10], "dao2_xxxxxxxxxx_xxxxxxxxxx_000");
		Assert.assertEquals("�ʖ��w�Ȃ��ŁA�����̃t�B�[���h�������e�[�u���ɑ��݂���ꍇ�A�ŏ��Ɍ������� DAO �̃e�[�u���ʖ����g�p����鎖", aliases[11], "dao1_xxxxxxxxxx_xxxxxxxxxx_003");
		Assert.assertEquals("�ʖ��w��L��łR�O�����𒴂��Ă��Ȃ��ꍇ�A�������o�͂���Ă��鎖", aliases[12], "dao2_sub_col1_name");
		Assert.assertEquals("�ʖ��w�薳���łR�O�����𒴂��Ă��Ȃ��ꍇ�A�������o�͂���Ă��鎖", aliases[13], "dao2_sub_col2_name");

	}
	
	
	
	/**
	 * GroupByDAO �� VO Funtion �A�m�e�[�V�������w�肳�ꂽ�ꍇ�̃e�X�g<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�e�[�u���ʖ��w�薳���A�֐��ʖ������̏ꍇ�A�t�B�[���h������ʖ�����������鎖</li>
	 *     <li>�e�[�u���ʖ��w�薳���A�֐��ʖ��L��ꍇ�A�J�����ʖ����g�p����鎖</li>
	 *     <li>�e�[�u���ʖ��w��L��A�֐��ʖ������ꍇ�A�t�B�[���h������ʖ�����������鎖</li>
	 *     <li>�e�[�u���ʖ��w��L��A�֐��ʖ��L��ꍇ�A�J�����ʖ����g�p����鎖</li>
	 * </ul>
	 */
	@Test
	public void getFieldAliasesByColAliasTest() throws Exception{

		// �Ϗ���� ReflectingDAO ���쐬
		ReflectingDAO<TestLongNameVo> dao = new ReflectingDAO<>();
		dao.setValueObjectClassName("jp.co.transcosmos.mock.vo.TestLongNameVo");

		// �e�X�g�Ώ� GroupByDAO ���쐬
		GroupByDAO<?> groupDao = new GroupByDAO<>();
		groupDao.setTargetDAO(dao);
		groupDao.setValueObjectClassName("jp.co.transcosmos.mock.vo.GoupByRefTestVo");

		String[] thisAlias = new String[]{null, "a1", null, "a1"};
		String[] forFieldNames = new String[]{"xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA",
											  "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxB",
											  "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxC",
											  "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD"};
		String[] thisFunctionAlias = new String[]{null, "alias1", "alias2", null};

		// ���t���N�V�����ŁA�e�X�g���\�b�h�����s
		String[] aliases = executeGetFieldAliases(groupDao, thisAlias, forFieldNames, thisFunctionAlias);

		Assert.assertEquals("�߂�l�̔z�񐔂���������", aliases.length, forFieldNames.length);
		Assert.assertEquals("�e�[�u���ʖ��w�薳���A�֐��ʖ������̏ꍇ�A�t�B�[���h������ʖ�����������鎖", aliases[0], "xxxxxxxxxx_xxxxxxxxxx_xxxxxxxx");
		Assert.assertEquals("�e�[�u���ʖ��w��L��A�֐��ʖ��L��ꍇ�A�֐��ʖ����g�p����鎖", aliases[1], "alias1");
		Assert.assertEquals("�e�[�u���ʖ��w�薳���A�֐��ʖ��L��ꍇ�A�֐��ʖ����g�p����鎖", aliases[2], "alias2");
		Assert.assertEquals("�e�[�u���ʖ��w��L��A�֐��ʖ������ꍇ�A�t�B�[���h������ʖ�����������鎖", aliases[3], "a1_xxxxxxxxxx_xxxxxxxxxx_xxxxx");

	}
	
	
	
	/**
	 * GroupByDAO �� ���^�f�[�^���W�����̃e�X�g�i�Ϗ��悪 ReflectionDAO �̏ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>Group By �p�̃o���[�I�u�W�F�N�g�̃t�B�[���h�����AfieldNames�@�v���p�e�B�Ɋi�[����Ă��鎖</li>
	 *     <li>�֐��w�肪�����ꍇ�ADB �񖼂��AcolumnNames�@�v���p�e�B�Ɋi�[����Ă��鎖</li>
	 *     <li>�֐��w�肪����ꍇ�A�֐������񂪁AcolumnNames�@�v���p�e�B�Ɋi�[����Ă��鎖</li>
	 *     <li>Function �A�m�e�[�V�������t�^����Ă���ꍇ�A�w�肳�ꂽ�ʖ��� functionAlias �v���p�e�B�Ɋi�[����Ă��鎖</li>
	 *     <li>Function �A�m�e�[�V�������t�^����Ă��Ȃ��ꍇ�AfunctionAlias �v���p�e�B�� null ���i�[����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void ensureInitializationByRefDAOTest() throws Exception {
		
		// �e�X�g�Ώ� GroupByDAO ���쐬
		GroupByDAO<?> groupDao = buildRefGroupByDAO();
		

		// ���t���N�V�����ŁA�e�X�g���\�b�h�����s
		executeEnsureInitialization(groupDao);

		// ���t���N�V�����ŁA���s���ʂ��擾����B
		Map<String, String[]> props = getGroupByDAOProps(groupDao);

		System.out.println("�yReflectionDAO�z");
		debugPropPrint(props, "fieldNames");
		debugPropPrint(props, "columnNames");
		debugPropPrint(props, "daoAliases");
		debugPropPrint(props, "functionAlias");

		Assert.assertEquals("�t�B�[���h�����������擾�ł��Ă��鎖�i�z�񌏐��j", 5, props.get("fieldNames").length);
		Assert.assertEquals("DB �񖼂��������擾�ł��Ă��鎖�i�z�񌏐��j", 5, props.get("columnNames").length);
		Assert.assertEquals("��̃e�[�u���ʖ����������擾�ł��Ă��鎖�i�z�񌏐��j", 5, props.get("daoAliases").length);
		Assert.assertEquals("�֐��ʖ����������擾�ł��Ă��鎖�i�z�񌏐��j", 5, props.get("functionAlias").length);

		int idx = findTargetIndex(props.get("fieldNames"), "col1Name");
		Assert.assertEquals("�t�B�[���h�����������擾�ł��Ă��鎖�i�P�t�B�[���h�ځj", "col1Name", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB �񖼂��������擾�ł��Ă��鎖�i�P�t�B�[���h�ځj", "col1_name", props.get("columnNames")[idx]);
		Assert.assertNull("��̃e�[�u���ʖ����������擾�ł��Ă��鎖�i�P�t�B�[���h�ځj", props.get("daoAliases")[idx]);
		Assert.assertNull("�֐������񂪐������擾�ł��Ă��鎖�i�P�t�B�[���h�ځj", props.get("functionAlias")[idx]);
		
		idx = findTargetIndex(props.get("fieldNames"), "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA");
		Assert.assertEquals("�t�B�[���h�����������擾�ł��Ă��鎖�i�Q�t�B�[���h�ځj", "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB �񖼂��������擾�ł��Ă��鎖�i�Q�t�B�[���h�ځj", "xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_A", props.get("columnNames")[idx]);
		Assert.assertNull("��̃e�[�u���ʖ����������擾�ł��Ă��鎖�i�Q�t�B�[���h�ځj", props.get("daoAliases")[idx]);
		Assert.assertNull("�֐������񂪐������擾�ł��Ă��鎖�i�Q�t�B�[���h�ځj", props.get("functionAlias")[idx]);
		
		idx = findTargetIndex(props.get("fieldNames"), "cnt");
		Assert.assertEquals("�t�B�[���h�����������擾�ł��Ă��鎖�i�R�t�B�[���h�ځj", "cnt", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB �񖼂��������擾�ł��Ă��鎖�i�R�t�B�[���h�ځj", "max(col1_name)", props.get("columnNames")[idx]);
		Assert.assertNull("��̃e�[�u���ʖ����������擾�ł��Ă��鎖�i�R�t�B�[���h�ځj", props.get("daoAliases")[idx]);
		Assert.assertEquals("�֐������񂪐������擾�ł��Ă��鎖�i�R�t�B�[���h�ځj", "cnt", props.get("functionAlias")[idx]);
		
		idx = findTargetIndex(props.get("fieldNames"), "cnt2");
		Assert.assertEquals("�t�B�[���h�����������擾�ł��Ă��鎖�i�S�t�B�[���h�ځj", "cnt2", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB �񖼂��������擾�ł��Ă��鎖�i�S�t�B�[���h�ځj", "min(col2_name)", props.get("columnNames")[idx]);
		Assert.assertNull("��̃e�[�u���ʖ����������擾�ł��Ă��鎖�i�S�t�B�[���h�ځj", props.get("daoAliases")[idx]);
		Assert.assertEquals("�֐������񂪐������擾�ł��Ă��鎖�i�S�t�B�[���h�ځj", "cnt2", props.get("functionAlias")[idx]);

		idx = findTargetIndex(props.get("fieldNames"), "cnt3");
		Assert.assertEquals("�t�B�[���h�����������擾�ł��Ă��鎖�i�T�t�B�[���h�ځj", "cnt3", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB �񖼂��������擾�ł��Ă��鎖�i�T�t�B�[���h�ځj", "count(*)", props.get("columnNames")[idx]);
		Assert.assertNull("��̃e�[�u���ʖ����������擾�ł��Ă��鎖�i�T�t�B�[���h�ځj", props.get("daoAliases")[idx]);
		Assert.assertEquals("�֐������񂪐������擾�ł��Ă��鎖�i�T�t�B�[���h�ځj", "cnt3", props.get("functionAlias")[idx]);

	}


	
	/**
	 * GroupByDAO �� ���^�f�[�^���W�����̃e�X�g�i�Ϗ��悪 JoinDAO �̏ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>Group By �p�̃o���[�I�u�W�F�N�g�̃t�B�[���h�����AfieldNames�@�v���p�e�B�Ɋi�[����Ă��鎖</li>
	 *     <li>�֐��w�肪�����ꍇ�ADB �񖼂��AcolumnNames�@�v���p�e�B�Ɋi�[����Ă��鎖</li>
	 *     <li>�֐��w�肪����ꍇ�A�֐������񂪁AcolumnNames�@�v���p�e�B�Ɋi�[����Ă��鎖</li>
	 *     <li>DaoAlias �A�m�e�[�V�������t�^����Ă���ꍇ�A�w�肳�ꂽ�l�� daoAliases �v���p�e�B�Ɋi�[����Ă��鎖</li>
	 *     <li>DaoAlias �A�m�e�[�V�������t�^����Ă��Ȃ��ꍇ�AdaoAliases �v���p�e�B�� null ���i�[����Ă��鎖</li>
	 *     <li>Function �A�m�e�[�V�������t�^����Ă���ꍇ�A�w�肳�ꂽ�ʖ��� functionAlias �v���p�e�B�Ɋi�[����Ă��鎖</li>
	 *     <li>Function �A�m�e�[�V�������t�^����Ă��Ȃ��ꍇ�AfunctionAlias �v���p�e�B�� null ���i�[����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void ensureInitializationByJoinDAOTest() throws Exception{

		// �e�X�g�Ώ� DAO �쐬
		GroupByDAO<GoupByJoinTestVo> groupDao = buildJoinGroupByDAO();


		// ���t���N�V�����ŁA�e�X�g���\�b�h�����s
		executeEnsureInitialization(groupDao);

		
		// ���t���N�V�����ŁA���s���ʂ��擾����B
		Map<String, String[]> props = getGroupByDAOProps(groupDao);

		System.out.println("�yJoinDAO�z");
		debugPropPrint(props, "fieldNames");
		debugPropPrint(props, "columnNames");
		debugPropPrint(props, "daoAliases");
		debugPropPrint(props, "functionAlias");
		
		Assert.assertEquals("�t�B�[���h�����������擾�ł��Ă��鎖�i�z�񌏐��j", 7, props.get("fieldNames").length);
		Assert.assertEquals("DB �񖼂��������擾�ł��Ă��鎖�i�z�񌏐��j", 7, props.get("columnNames").length);
		Assert.assertEquals("��̃e�[�u���ʖ����������擾�ł��Ă��鎖�i�z�񌏐��j", 7, props.get("daoAliases").length);
		Assert.assertEquals("�֐��ʖ����������擾�ł��Ă��鎖�i�z�񌏐��j", 7, props.get("functionAlias").length);

		int idx = findTargetIndex(props.get("fieldNames"), "col1Name");
		Assert.assertEquals("�t�B�[���h�����������擾�ł��Ă��鎖�i�P�t�B�[���h�ځj", "col1Name", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB �񖼂��������擾�ł��Ă��鎖�i�P�t�B�[���h�ځj", "col1_name", props.get("columnNames")[idx]);
		Assert.assertNull("��̃e�[�u���ʖ����������擾�ł��Ă��鎖�i�P�t�B�[���h�ځj", props.get("daoAliases")[idx]);
		Assert.assertNull("�֐������񂪐������擾�ł��Ă��鎖�i�P�t�B�[���h�ځj", props.get("functionAlias")[idx]);

		idx = findTargetIndex(props.get("fieldNames"), "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA");
		Assert.assertEquals("�t�B�[���h�����������擾�ł��Ă��鎖�i�Q�t�B�[���h�ځj", "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB �񖼂��������擾�ł��Ă��鎖�i�Q�t�B�[���h�ځj", "xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_A", props.get("columnNames")[idx]);
		Assert.assertNull("��̃e�[�u���ʖ����������擾�ł��Ă��鎖�i�Q�t�B�[���h�ځj", props.get("daoAliases")[idx]);
		Assert.assertNull("�֐������񂪐������擾�ł��Ă��鎖�i�Q�t�B�[���h�ځj", props.get("functionAlias")[idx]);

		idx = findTargetIndex(props.get("fieldNames"), "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD");
		Assert.assertEquals("�t�B�[���h�����������擾�ł��Ă��鎖�i�R�t�B�[���h�ځj", "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB �񖼂��������擾�ł��Ă��鎖�i�R�t�B�[���h�ځj", "xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_D", props.get("columnNames")[idx]);
		Assert.assertEquals("��̃e�[�u���ʖ����������擾�ł��Ă��鎖�i�R�t�B�[���h�ځj", "dao2", props.get("daoAliases")[idx]);
		Assert.assertNull("�֐������񂪐������擾�ł��Ă��鎖�i�R�t�B�[���h�ځj", props.get("functionAlias")[idx]);

		idx = findTargetIndex(props.get("fieldNames"), "cnt");
		Assert.assertEquals("�t�B�[���h�����������擾�ł��Ă��鎖�i�S�t�B�[���h�ځj", "cnt", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB �񖼂��������擾�ł��Ă��鎖�i�S�t�B�[���h�ځj", "max(dao1.col1_name)", props.get("columnNames")[idx]);
		Assert.assertNull("��̃e�[�u���ʖ����������擾�ł��Ă��鎖�i�S�t�B�[���h�ځj", props.get("daoAliases")[idx]);
		Assert.assertEquals("�֐������񂪐������擾�ł��Ă��鎖�i�S�t�B�[���h�ځj", "cnt", props.get("functionAlias")[idx]);

		idx = findTargetIndex(props.get("fieldNames"), "cnt2");
		Assert.assertEquals("�t�B�[���h�����������擾�ł��Ă��鎖�i�T�t�B�[���h�ځj", "cnt2", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB �񖼂��������擾�ł��Ă��鎖�i�T�t�B�[���h�ځj", "min(dao1.col2_name)", props.get("columnNames")[idx]);
		Assert.assertNull("��̃e�[�u���ʖ����������擾�ł��Ă��鎖�i�T�t�B�[���h�ځj", props.get("daoAliases")[idx]);
		Assert.assertEquals("�֐������񂪐������擾�ł��Ă��鎖�i�T�t�B�[���h�ځj", "cnt2", props.get("functionAlias")[idx]);

		idx = findTargetIndex(props.get("fieldNames"), "mx");
		Assert.assertEquals("�t�B�[���h�����������擾�ł��Ă��鎖�i�T�t�B�[���h�ځj", "mx", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB �񖼂��������擾�ł��Ă��鎖�i�T�t�B�[���h�ځj", "sum(dao2.xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_D)", props.get("columnNames")[idx]);
		Assert.assertEquals("��̃e�[�u���ʖ����������擾�ł��Ă��鎖�i�T�t�B�[���h�ځj", "dao2", props.get("daoAliases")[idx]);
		Assert.assertEquals("�֐������񂪐������擾�ł��Ă��鎖�i�T�t�B�[���h�ځj", "mx", props.get("functionAlias")[idx]);

		idx = findTargetIndex(props.get("fieldNames"), "cnt3");
		Assert.assertEquals("�t�B�[���h�����������擾�ł��Ă��鎖�i�U�t�B�[���h�ځj", "cnt3", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB �񖼂��������擾�ł��Ă��鎖�i�U�t�B�[���h�ځj", "count(*)", props.get("columnNames")[idx]);
		Assert.assertNull("��̃e�[�u���ʖ����������擾�ł��Ă��鎖�i�U�t�B�[���h�ځj", props.get("daoAliases")[idx]);
		Assert.assertEquals("�֐������񂪐������擾�ł��Ă��鎖�i�U�t�B�[���h�ځj", "cnt3", props.get("functionAlias")[idx]);

	}


	
	/**
	 * GroupByDAO �� ���^�f�[�^���W�����̃e�X�g�i�Ϗ��悪 StarJoinDAO �̏ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>Group By �p�̃o���[�I�u�W�F�N�g�̃t�B�[���h�����AfieldNames�@�v���p�e�B�Ɋi�[����Ă��鎖</li>
	 *     <li>�֐��w�肪�����ꍇ�ADB �񖼂��AcolumnNames�@�v���p�e�B�Ɋi�[����Ă��鎖</li>
	 *     <li>�֐��w�肪����ꍇ�A�֐������񂪁AcolumnNames�@�v���p�e�B�Ɋi�[����Ă��鎖</li>
	 *     <li>DaoAlias �A�m�e�[�V�������t�^����Ă���ꍇ�A�w�肳�ꂽ�l�� daoAliases �v���p�e�B�Ɋi�[����Ă��鎖</li>
	 *     <li>DaoAlias �A�m�e�[�V�������t�^����Ă��Ȃ��ꍇ�AdaoAliases �v���p�e�B�� null ���i�[����Ă��鎖</li>
	 *     <li>Function �A�m�e�[�V�������t�^����Ă���ꍇ�A�֐��ʖ������w��̏ꍇ�AfunctionAlias �v���p�e�B�Ƀt�B�[���h�����i�[����鎖</li>
	 *     <li>Function �A�m�e�[�V�������t�^����Ă��Ȃ��ꍇ�AfunctionAlias �v���p�e�B�� null ���i�[����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void ensureInitializationByStarJoinDAOTest() throws Exception{

		// �e�X�g�Ώ� StarJoinDAO �쐬
		GroupByDAO<GoupByJoinTestVo> groupDao = buildStarJoinGroupByDAO();


		// ���t���N�V�����ŁA�e�X�g���\�b�h�����s
		executeEnsureInitialization(groupDao);

		
		// ���t���N�V�����ŁA���s���ʂ��擾����B
		Map<String, String[]> props = getGroupByDAOProps(groupDao);

		System.out.println("�yStarJoinDAO�z");
		debugPropPrint(props, "fieldNames");
		debugPropPrint(props, "columnNames");
		debugPropPrint(props, "daoAliases");
		debugPropPrint(props, "functionAlias");

		Assert.assertEquals("�t�B�[���h�����������擾�ł��Ă��鎖�i�z�񌏐��j", 7, props.get("fieldNames").length);
		Assert.assertEquals("DB �񖼂��������擾�ł��Ă��鎖�i�z�񌏐��j", 7, props.get("columnNames").length);
		Assert.assertEquals("��̃e�[�u���ʖ����������擾�ł��Ă��鎖�i�z�񌏐��j", 7, props.get("daoAliases").length);
		Assert.assertEquals("�֐��ʖ����������擾�ł��Ă��鎖�i�z�񌏐��j", 7, props.get("functionAlias").length);

		int idx = findTargetIndex(props.get("fieldNames"), "col1Name");
		Assert.assertEquals("�t�B�[���h�����������擾�ł��Ă��鎖�i�P�t�B�[���h�ځj", "col1Name", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB �񖼂��������擾�ł��Ă��鎖�i�P�t�B�[���h�ځj", "col1_name", props.get("columnNames")[idx]);
		Assert.assertNull("��̃e�[�u���ʖ����������擾�ł��Ă��鎖�i�P�t�B�[���h�ځj", props.get("daoAliases")[idx]);
		Assert.assertNull("�֐������񂪐������擾�ł��Ă��鎖�i�P�t�B�[���h�ځj", props.get("functionAlias")[idx]);

		idx = findTargetIndex(props.get("fieldNames"), "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA");
		Assert.assertEquals("�t�B�[���h�����������擾�ł��Ă��鎖�i�Q�t�B�[���h�ځj", "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB �񖼂��������擾�ł��Ă��鎖�i�Q�t�B�[���h�ځj", "xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_A", props.get("columnNames")[idx]);
		Assert.assertNull("��̃e�[�u���ʖ����������擾�ł��Ă��鎖�i�Q�t�B�[���h�ځj", props.get("daoAliases")[idx]);
		Assert.assertNull("�֐������񂪐������擾�ł��Ă��鎖�i�Q�t�B�[���h�ځj", props.get("functionAlias")[idx]);

		idx = findTargetIndex(props.get("fieldNames"), "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD");
		Assert.assertEquals("�t�B�[���h�����������擾�ł��Ă��鎖�i�R�t�B�[���h�ځj", "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB �񖼂��������擾�ł��Ă��鎖�i�R�t�B�[���h�ځj", "xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_D", props.get("columnNames")[idx]);
		Assert.assertEquals("��̃e�[�u���ʖ����������擾�ł��Ă��鎖�i�R�t�B�[���h�ځj", "dao2", props.get("daoAliases")[idx]);
		Assert.assertNull("�֐������񂪐������擾�ł��Ă��鎖�i�R�t�B�[���h�ځj", props.get("functionAlias")[idx]);

		idx = findTargetIndex(props.get("fieldNames"), "cnt");
		Assert.assertEquals("�t�B�[���h�����������擾�ł��Ă��鎖�i�S�t�B�[���h�ځj", "cnt", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB �񖼂��������擾�ł��Ă��鎖�i�S�t�B�[���h�ځj", "max(dao1.col1_name)", props.get("columnNames")[idx]);
		Assert.assertNull("��̃e�[�u���ʖ����������擾�ł��Ă��鎖�i�S�t�B�[���h�ځj", props.get("daoAliases")[idx]);
		Assert.assertEquals("�֐������񂪐������擾�ł��Ă��鎖�i�S�t�B�[���h�ځj", "cnt", props.get("functionAlias")[idx]);

		idx = findTargetIndex(props.get("fieldNames"), "cnt2");
		Assert.assertEquals("�t�B�[���h�����������擾�ł��Ă��鎖�i�T�t�B�[���h�ځj", "cnt2", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB �񖼂��������擾�ł��Ă��鎖�i�T�t�B�[���h�ځj", "min(dao1.col2_name)", props.get("columnNames")[idx]);
		Assert.assertNull("��̃e�[�u���ʖ����������擾�ł��Ă��鎖�i�T�t�B�[���h�ځj", props.get("daoAliases")[idx]);
		Assert.assertEquals("�֐������񂪐������擾�ł��Ă��鎖�i�T�t�B�[���h�ځj", "cnt2", props.get("functionAlias")[idx]);

		idx = findTargetIndex(props.get("fieldNames"), "mx");
		Assert.assertEquals("�t�B�[���h�����������擾�ł��Ă��鎖�i�T�t�B�[���h�ځj", "mx", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB �񖼂��������擾�ł��Ă��鎖�i�T�t�B�[���h�ځj", "sum(dao2.xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_D)", props.get("columnNames")[idx]);
		Assert.assertEquals("��̃e�[�u���ʖ����������擾�ł��Ă��鎖�i�T�t�B�[���h�ځj", "dao2", props.get("daoAliases")[idx]);
		Assert.assertEquals("�֐������񂪐������擾�ł��Ă��鎖�i�T�t�B�[���h�ځj", "mx", props.get("functionAlias")[idx]);

		idx = findTargetIndex(props.get("fieldNames"), "cnt3");
		Assert.assertEquals("�t�B�[���h�����������擾�ł��Ă��鎖�i�U�t�B�[���h�ځj", "cnt3", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB �񖼂��������擾�ł��Ă��鎖�i�U�t�B�[���h�ځj", "count(*)", props.get("columnNames")[idx]);
		Assert.assertNull("��̃e�[�u���ʖ����������擾�ł��Ă��鎖�i�U�t�B�[���h�ځj", props.get("daoAliases")[idx]);
		Assert.assertEquals("�֐������񂪐������擾�ł��Ă��鎖�i�U�t�B�[���h�ځj", "cnt3", props.get("functionAlias")[idx]);

	}

	
	
	/**
	 * SELECT�@��̏o�̓e�X�g�i�Ϗ��悪 ReflectionDAO �̏ꍇ�j<br/>
	 * ����ʖ��ҏW���ʂ��R�O�����𒴂���ꍇ�A�R�O�����ɐ؂�l�߂���̂ŁA���̏ꍇ�AAS �傪�t�^����Ă��܂��B<br/>
	 * ���p�I�ɂ͖��Ȃ��̂ŁA���ʂȑΏ��͍s��Ȃ��B<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>SELECT LIST �̏o�͍��ڐ�����������</li>
	 *     <li>�ʏ�t�B�[���h�� AS ��A�e�[�u���ʖ������ŏo�͂���鎖</li>
	 *     <li>Function �A�m�e�[�V�������g�p�����ꍇ�A�w�肳�ꂽ�֐��̊֐������񂪏o�͂���鎖�i�e�[�u�ʖ������j</li>
	 * </ul>
	 */
	@Test
	public void addSelectClauseByRefDAOTest() throws Exception{

		// �e�X�g�Ώ� GroupByDAO ���쐬
		GroupByDAO<?> groupDao = buildRefGroupByDAO();


		// �e�X�g���\�b�h�����s
		StringBuilder sql = new StringBuilder();
		groupDao.addSelectClause(sql, "", null);

		System.out.println("�yReflectionDAO�z" + sql.toString());

		// SELECT LIST �̊��Ғl
		String[] ans = new String[]{"col1_name",
									"count(*) AS cnt3",
									"max(col1_name) AS cnt",
									"min(col2_name) AS cnt2",
									"xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_A AS xxxxxxxxxx_xxxxxxxxxx_xxxxxxxx"};

		// ���[�v�J�E���^�B�@�����_����Q�Ƃ����ϐ��́A�����I�� final �Ƃ��Ĉ�����̂ŁA�z��Ɋi�[���Ă���B
		Integer[] cnt = new Integer[]{0};

		Arrays.asList(sql.toString().split(","))
					.stream()
					.map(str-> str.trim())
					.sorted()
					.forEach(str->{
						Assert.assertEquals("SELECT LIST ����������", ans[cnt[0]], str);
						cnt[0]++;
					});

		Assert.assertEquals("SELECT LIST �̏o�͍��ڐ�������������", Integer.valueOf(5), cnt[0]);

	}
	

	
	/**
	 * SELECT�@��̏o�̓e�X�g�i�Ϗ��悪 JoinDAO �̏ꍇ�j<br/>
	 * ����ʖ��ҏW���ʂ��R�O�����𒴂���ꍇ�A�R�O�����ɐ؂�l�߂���̂ŁA���̏ꍇ�AAS �傪�t�^����Ă��܂��B<br/>
	 * ���p�I�ɂ͖��Ȃ��̂ŁA���ʂȑΏ��͍s��Ȃ��B<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>SELECT LIST �̏o�͍��ڐ�����������</li>
	 *     <li>�ʏ�t�B�[���h�� AS ��A�e�[�u���ʂ��t������Ă��鎖</li>
	 *     <li>Function �A�m�e�[�V�������g�p�����ꍇ�A�w�肳�ꂽ�֐��̊֐������񂪏o�͂���鎖�i�e�[�u�ʖ������j</li>
	 * </ul>
	 */
	@Test
	public void addSelectClauseByJoinDAOTest() throws Exception{

		// �e�X�g�Ώ� DAO �쐬
		GroupByDAO<GoupByJoinTestVo> groupDao = buildJoinGroupByDAO();

		// �e�X�g���\�b�h�����s
		StringBuilder sql = new StringBuilder();
		groupDao.addSelectClause(sql, "", null);

		System.out.println("�yJoinDAO�z" + sql.toString());
		
		// SELECT LIST �̊��Ғl
		String[] ans = new String[]{"count(*) AS cnt3",
									"dao1.col1_name AS dao1_col1_name",
									"dao1.xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_A AS dao1_xxxxxxxxxx_xxxxxxxxxx_xxx",
									"dao2.xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_D AS dao2_xxxxxxxxxx_xxxxxxxxxx_xxx",
									"max(dao1.col1_name) AS cnt",
									"min(dao1.col2_name) AS cnt2",
									"sum(dao2.xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_D) AS mx"};

		// ���[�v�J�E���^�B�@�����_����Q�Ƃ����ϐ��́A�����I�� final �Ƃ��Ĉ�����̂ŁA�z��Ɋi�[���Ă���B
		Integer[] cnt = new Integer[]{0};

		Arrays.asList(sql.toString().split(","))
					.stream()
					.map(str-> str.trim())
					.sorted()
					.forEach(str->{
						Assert.assertEquals("SELECT LIST ����������", ans[cnt[0]], str);
						cnt[0]++;
					});

		Assert.assertEquals("SELECT LIST �̏o�͍��ڐ�������������", Integer.valueOf(7), cnt[0]);
		
	}


	
	/**
	 * SELECT�@��̏o�̓e�X�g�i�Ϗ��悪 StarJoinDAO �̏ꍇ�j<br/>
	 * ����ʖ��ҏW���ʂ��R�O�����𒴂���ꍇ�A�R�O�����ɐ؂�l�߂���̂ŁA���̏ꍇ�AAS �傪�t�^����Ă��܂��B<br/>
	 * ���p�I�ɂ͖��Ȃ��̂ŁA���ʂȑΏ��͍s��Ȃ��B<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>SELECT LIST �̏o�͍��ڐ�����������</li>
	 *     <li>�ʏ�t�B�[���h�� AS ��A�e�[�u���ʂ��t������Ă��鎖</li>
	 *     <li>Function �A�m�e�[�V�������g�p�����ꍇ�A�w�肳�ꂽ�֐��̊֐������񂪏o�͂���鎖�i�e�[�u�ʖ������j</li>
	 * </ul>
	 */
	@Test
	public void addSelectClauseByStarJoinDAOTest() throws Exception{

		// �e�X�g�Ώ� StarJoinDAO �쐬
		GroupByDAO<GoupByJoinTestVo> groupDao = buildStarJoinGroupByDAO();

		
		StringBuilder sql = new StringBuilder();
		groupDao.addSelectClause(sql, "", null);

		System.out.println("�yStarJoinDAO�z" + sql.toString());

		// SELECT LIST �̊��Ғl
		String[] ans = new String[]{"count(*) AS cnt3",
									"dao1.col1_name AS dao1_col1_name",
									"dao1.xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_A AS dao1_xxxxxxxxxx_xxxxxxxxxx_xxx",
									"dao2.xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_D AS dao2_xxxxxxxxxx_xxxxxxxxxx_xxx",
									"max(dao1.col1_name) AS cnt",
									"min(dao1.col2_name) AS cnt2",
									"sum(dao2.xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_D) AS mx"};

		// ���[�v�J�E���^�B�@�����_����Q�Ƃ����ϐ��́A�����I�� final �Ƃ��Ĉ�����̂ŁA�z��Ɋi�[���Ă���B
		Integer[] cnt = new Integer[]{0};

		Arrays.asList(sql.toString().split(","))
					.stream()
					.map(str-> str.trim())
					.sorted()
					.forEach(str->{
						Assert.assertEquals("SELECT LIST ����������", ans[cnt[0]], str);
						cnt[0]++;
					});

		Assert.assertEquals("SELECT LIST �̏o�͍��ڐ�������������", Integer.valueOf(7), cnt[0]);

	}
	
	
	
	/**
	 * From�@��̏o�̓e�X�g�i�Ϗ��悪 ReflectionDAO �̏ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�Ϗ��� DAO �̃e�[�u�������o�͂���鎖</li>
	 * </ul>
	 */
	@Test
	public void addFromClauseByRefDAOTest() {
		
		// �e�X�g�Ώ� GroupByDAO ���쐬
		GroupByDAO<?> groupDao = buildRefGroupByDAO();


		// �e�X�g���\�b�h�����s
		StringBuilder sql = new StringBuilder();
		groupDao.addFromClause(sql, "", null);
	
		
		// ���ʂ��`�F�b�N
		Assert.assertEquals("From �̐�������������", "test_long_name_vo", sql.toString());
	}
	

	
	/**
	 * From�@��̏o�̓e�X�g�i�Ϗ��悪 JoinDAO �̏ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�Ϗ��� DAO �̃e�[�u�����ƌ����������o�͂���鎖</li>
	 * </ul>
	 */
	@Test
	public void addFromClauseByJoinDAOTest() {
		
		// �e�X�g�Ώ� DAO �쐬
		GroupByDAO<GoupByJoinTestVo> groupDao = buildJoinGroupByDAO();

		// �e�X�g���\�b�h�����s
		StringBuilder sql = new StringBuilder();
		groupDao.addFromClause(sql, "", null);
	
		
		// ���ʂ��`�F�b�N
		Assert.assertEquals("From �̐�������������", "test_long_name_vo dao1 INNER JOIN test_long_name_sub_vo dao2 ON dao1.col1_name = dao2.sub_col1_name", sql.toString());
	}


	
	/**
	 * From�@��̏o�̓e�X�g�i�Ϗ��悪 StarJoinDAO �̏ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�Ϗ��� DAO �̃e�[�u�����ƌ����������o�͂���鎖</li>
	 * </ul>
	 */
	@Test
	public void addFromClauseByStarJoinDAOTest() {
		
		// �e�X�g�Ώ� DAO �쐬
		GroupByDAO<GoupByJoinTestVo> groupDao = buildStarJoinGroupByDAO();

		// �e�X�g���\�b�h�����s
		StringBuilder sql = new StringBuilder();
		groupDao.addFromClause(sql, "", null);
	
		
		// ���ʂ��`�F�b�N
		Assert.assertEquals("From �̐�������������", "test_long_name_vo dao1 INNER JOIN test_long_name_sub_vo dao2 ON dao1.col1_name = dao2.sub_col1_name", sql.toString());
	}



	/**
	 * WHERE�@��̏o�̓e�X�g�i�Ϗ��悪 ReflectionDAO �̏ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�Ϗ��� DAO �̋@�\�ɂ� WHERE �傪��������Ă��鎖</li>
	 *     <li>�o�C���h�ϐ����X�g����������</li>
	 * </ul>
	 */
	@Test
	public void addWhereClausesByRefGroupTest() throws Exception{
		
		// �e�X�g�Ώ� GroupByDAO �쐬
		GroupByDAO<GoupByRefTestVo> groupDao = buildRefGroupByDAO();
		
		// �o�C���h�ϐ��l
		Integer[] ansParams = new Integer[]{10, 20};

		// �e�X�g�Ώی��������쐬
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("col1Name", ansParams[0]);							// �ʏ�t�B�[���h
		criteria.addWhereClause("xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA", ansParams[1]);	// �����I�[�o�[�t�B�[���h

		
		// �e�X�g���\�b�h���s
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();

		groupDao.prepareCriteria(criteria, null);
		executeEnsureInitialization(groupDao);
		DAOUtils.addWhereClauses(groupDao, criteria, "", sql, params, true);

		Assert.assertEquals("WHERE ��̏o�͂���������", " WHERE col1_name = ? AND xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_A = ?", sql.toString());
		
		Integer[] cnt = new Integer[]{0};
		params.stream().forEach(param -> {
								Assert.assertEquals("�o�C���h�ϐ�����������", ansParams[cnt[0]], param);
								cnt[0]++;
							});
	}



	/**
	 * WHERE�@��̏o�̓e�X�g�i�Ϗ��悪 JoinDAO �̏ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�Ϗ��� DAO �̋@�\�ɂ� WHERE �傪��������Ă��鎖</li>
	 *     <li>�o�C���h�ϐ����X�g����������</li>
	 * </ul>
	 */
	@Test
	public void addWhereClausesByJoinGroupTest() throws Exception{
		
		// �e�X�g�Ώ� GroupByDAO �쐬
		GroupByDAO<GoupByJoinTestVo> groupDao = buildJoinGroupByDAO();


		// �o�C���h�ϐ��l
		Integer[] ansParams = new Integer[]{10, 20, 30, 40};
		
		// �e�X�g�Ώی��������쐬
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("col1Name", 10);						// �ʏ�t�B�[���h(dao1)
		criteria.addWhereClause("xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA", 20);	// �����I�[�o�[�t�B�[���h(dao1)
		criteria.addWhereClause("subCol1Name", 30);						// �ʏ�t�B�[���h(dao2)
		// �d���t�B�[���h(dao2)
		criteria.addWhereClause("dao2", "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD", 40, DAOCriteria.EQUALS, true);


		// �e�X�g���\�b�h���s
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();

		groupDao.prepareCriteria(criteria, null);
		executeEnsureInitialization(groupDao);
		DAOUtils.addWhereClauses(groupDao, criteria, "", sql, params, true);

		String ans = " WHERE dao1.col1_name = ? AND dao1.xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_A = ? " +
					 "AND dao2.sub_col1_name = ? AND dao2.xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_D <> ?";
		Assert.assertEquals("WHERE ��̏o�͂���������", ans, sql.toString());

		Integer[] cnt = new Integer[]{0};
		params.stream().forEach(param -> {
								Assert.assertEquals("�o�C���h�ϐ�����������", ansParams[cnt[0]], param);
								cnt[0]++;
							});

	}

	

	/**
	 * WHERE�@��̏o�̓e�X�g�i�Ϗ��悪 StarJoinDAO �̏ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�Ϗ��� DAO �̋@�\�ɂ� WHERE �傪��������Ă��鎖</li>
	 *     <li>�o�C���h�ϐ����X�g����������</li>
	 * </ul>
	 */
	@Test
	public void addWhereClausesByStarJoinGroupTest() throws Exception{
		
		// �e�X�g�Ώ� GroupByDAO �쐬
		GroupByDAO<GoupByJoinTestVo> groupDao = buildStarJoinGroupByDAO();


		// �o�C���h�ϐ��l
		Integer[] ansParams = new Integer[]{10, 20, 30, 40};
		
		// �e�X�g�Ώی��������쐬
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("col1Name", 10);						// �ʏ�t�B�[���h(dao1)
		criteria.addWhereClause("xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA", 20);	// �����I�[�o�[�t�B�[���h(dao1)
		criteria.addWhereClause("subCol1Name", 30);						// �ʏ�t�B�[���h(dao2)
		// �d���t�B�[���h(dao2)
		criteria.addWhereClause("dao2", "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD", 40, DAOCriteria.EQUALS, true);


		// �e�X�g���\�b�h���s
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();

		groupDao.prepareCriteria(criteria, null);
		executeEnsureInitialization(groupDao);
		DAOUtils.addWhereClauses(groupDao, criteria, "", sql, params, true);

		String ans = " WHERE dao1.col1_name = ? AND dao1.xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_A = ? " +
					 "AND dao2.sub_col1_name = ? AND dao2.xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_D <> ?";
		Assert.assertEquals("WHERE ��̏o�͂���������", ans, sql.toString());

		Integer[] cnt = new Integer[]{0};
		params.stream().forEach(param -> {
								Assert.assertEquals("�o�C���h�ϐ�����������", ansParams[cnt[0]], param);
								cnt[0]++;
							});

	}


	
	// ReflectingDAO + GroupByDAO ���e�X�g�Ώ� DAO �𐶐�����B
	private GroupByDAO<GoupByRefTestVo> buildRefGroupByDAO() {
		
		// �Ϗ���� ReflectingDAO ���쐬
		ReflectingDAO<TestLongNameVo> dao = new ReflectingDAO<>();
		dao.setValueObjectClassName("jp.co.transcosmos.mock.vo.TestLongNameVo");

		// �e�X�g�Ώ� GroupByDAO ���쐬
		GroupByDAO<GoupByRefTestVo> groupDao = new GroupByDAO<>();
		groupDao.setTargetDAO(dao);
		groupDao.setValueObjectClassName("jp.co.transcosmos.mock.vo.GoupByRefTestVo");

		return groupDao;
	}

	
	// JoinDAO + GroupByDAO ���e�X�g�Ώ� DAO �𐶐�����B
	private GroupByDAO<GoupByJoinTestVo> buildJoinGroupByDAO() {

		// �Ϗ���� JonDAO ���쐬
		ReflectingDAO<TestLongNameVo> mainDao = new ReflectingDAO<>();
		mainDao.setValueObjectClassName("jp.co.transcosmos.mock.vo.TestLongNameVo");

		ReflectingDAO<TestLongNameVo> subDao = new ReflectingDAO<>();
		subDao.setValueObjectClassName("jp.co.transcosmos.mock.vo.TestLongNameSubVo");
		
		JoinDAO joinDao = new JoinDAO();
		joinDao.setJoinable1(mainDao);
		joinDao.setJoinable2(subDao);

		JoinCondition condition = new JoinCondition();
		condition.setField1("col1Name");
		condition.setField2("subCol1Name");
		joinDao.setCondition(condition);
		
		
		// �e�X�g�Ώ� GroupByDAO ���쐬
		GroupByDAO<GoupByJoinTestVo> groupDao = new GroupByDAO<>();
		groupDao.setTargetDAO(joinDao);
		groupDao.setValueObjectClassName("jp.co.transcosmos.mock.vo.GoupByJoinTestVo");
		
		return groupDao;

	}

	
	// StarJoinDAO + GroupByDAO ���e�X�g�Ώ� DAO �𐶐�����B
	private GroupByDAO<GoupByJoinTestVo> buildStarJoinGroupByDAO() {

		// �������� DAO ���쐬
		ReflectingDAO<TestLongNameVo> mainDao = new ReflectingDAO<>();
		mainDao.setValueObjectClassName("jp.co.transcosmos.mock.vo.TestLongNameVo");

		ReflectingDAO<TestLongNameVo> subDao = new ReflectingDAO<>();
		subDao.setValueObjectClassName("jp.co.transcosmos.mock.vo.TestLongNameSubVo");
		
		// start join DAO
		StarJoinDAO joinDao = new StarJoinDAO();
		joinDao.setMainDAO(mainDao);
		joinDao.setMainAlias("dao1");

		// ��������
		JoinCondition condition = new JoinCondition();
		condition.setField1("col1Name");
		condition.setField2("subCol1Name");

		// StarJoin �̎q�\ DAO1
		StarJoinDAODescriptor descriptor = new StarJoinDAODescriptor();
		descriptor.setDao(subDao);
		descriptor.setAlias("dao2");
		descriptor.setCondition(condition);

		List<StarJoinDAODescriptor> childDAOs = Arrays.asList(descriptor);
		joinDao.setChildDAOs(childDAOs);

		
		// �e�X�g�Ώ� GroupByDAO ���쐬
		GroupByDAO<GoupByJoinTestVo> groupDao = new GroupByDAO<>();
		groupDao.setTargetDAO(joinDao);
		groupDao.setValueObjectClassName("jp.co.transcosmos.mock.vo.GoupByJoinTestVo");

		return groupDao;
	}
	
	
	private void debugPropPrint (Map<String, String[]> props, String propName){

		props.entrySet().stream()
		.filter(entry -> entry.getKey().equals(propName))
		.forEach(entry -> {
			System.out.println(entry.getKey() + ":");
			String[] values = entry.getValue();
			for (String oneValue : values){
				System.out.println(oneValue);
			}
		});
		System.out.println("---------------------------");
	}
	

	private int findTargetIndex (String[] values, String target){

		int cnt = 0;
		for (String value : values){
			if (target.equals(value)) return cnt;
			++cnt;
		}
		return -1;
	}
	

	// getFieldAliases()�@���\�b�h�����t���N�V�����Ŏ��s
	private String[] executeGetFieldAliases(GroupByDAO<?> groupDao, String[] thisAlias, String[] forFieldNames, String[] thisFunctionAlias) throws Exception{

		Method method = groupDao.getClass().getDeclaredMethod("getFieldAliases", String[].class, String[].class, String[].class);
		method.setAccessible(true);

		return (String[]) method.invoke(groupDao, thisAlias, forFieldNames, thisFunctionAlias);
	}

	
	// ensureInitialization()�@���\�b�h�����t���N�V�����Ŏ��s
	private void executeEnsureInitialization(GroupByDAO<?> groupDao) throws Exception{
		
		Method method = groupDao.getClass().getDeclaredMethod("ensureInitialization");
		method.setAccessible(true);

		method.invoke(groupDao);
	}

	
	// GroupByDAO �̓����v���p�e�B�l���擾����B
	private Map<String, String[]> getGroupByDAOProps(GroupByDAO<?> groupDao) throws Exception {

		Map<String, String[]> result = new HashMap<>();

		// �o���[�I�u�W�F�N�g�̃t�B�[���h�����擾
		Field field = groupDao.getClass().getDeclaredField("fieldNames");
		field.setAccessible(true);
		result.put("fieldNames", (String[])field.get(groupDao));
		
		// ���ð��ٕʖ����擾
		field = groupDao.getClass().getDeclaredField("daoAliases");
		field.setAccessible(true);
		result.put("daoAliases", (String[])field.get(groupDao));
		
		// DB �̗񖼂��擾
		field = groupDao.getClass().getDeclaredField("columnNames");
		field.setAccessible(true);
		result.put("columnNames", (String[])field.get(groupDao));
		
		// �W�v�֐��g�p���̕ʖ����擾
		field = groupDao.getClass().getDeclaredField("functionAlias");
		field.setAccessible(true);
		result.put("functionAlias", (String[])field.get(groupDao));

	    return result;
	}

	
	
}
