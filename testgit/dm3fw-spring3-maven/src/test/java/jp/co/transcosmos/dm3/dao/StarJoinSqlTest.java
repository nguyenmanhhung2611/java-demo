package jp.co.transcosmos.dm3.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.transcosmos.mock.vo.TestJoinSubVo;
import jp.co.transcosmos.mock.vo.TestMainVo;
import jp.co.transcosmos.mock.vo.TestSubSubVo;
import jp.co.transcosmos.mock.vo.TestSubVo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * StarJoinDAO �ɂ�錋������ SQL �̃e�X�g<br/>
 * <br/>
 */
public class StarJoinSqlTest {

	private ReflectingDAO<TestMainVo> mainDAO;
	private ReflectingDAO<TestJoinSubVo> joinSubDAO;
	private ReflectingDAO<TestSubVo> subDAO;
	private ReflectingDAO<TestSubSubVo> subSubDAO;

	
	
	@Before
	public void init() {

		// �e�X�g�p�e�\ DAO
		this.mainDAO = new ReflectingDAO<>();
		this.mainDAO.setValueObjectClassName("jp.co.transcosmos.mock.vo.TestMainVo");

		// �e�X�g�p�q�\1 DAO
		this.joinSubDAO = new ReflectingDAO<>();
		this.joinSubDAO.setValueObjectClassName("jp.co.transcosmos.mock.vo.TestJoinSubVo");

		// �e�X�g�p�q�\2 DAO
		this.subDAO = new ReflectingDAO<>();
		this.subDAO.setValueObjectClassName("jp.co.transcosmos.mock.vo.TestSubVo");

		// �e�X�g�p�q�\3 DAO
		this.subSubDAO = new ReflectingDAO<>();
		this.subSubDAO.setValueObjectClassName("jp.co.transcosmos.mock.vo.TestSubSubVo");


	}
	
	
	
	/**
	 * DAO + DAO �ɂ����� StarJoinDAO �������� SQL ���o�̓e�X�g�i�t�B�[���h�d���Ȃ��j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>��������� SQL �������������B</li>
	 *     <li>�o�C���h�ϐ����X�g�̊i�[�l�A��������������</li>
	 * </ul>
	 */
	@Test
	public void twoJoinNonDupTest(){

		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();


		// �e�X�g�p StarJoinDAO�@�imain -- join_sub�j
		StarJoinDAO joinDAO = new StarJoinDAO();
		joinDAO.setMainDAO(this.mainDAO);
		joinDAO.setMainAlias("m");

		// ��������
		JoinCondition condition = new JoinCondition();
		condition.setField1("mainColumn1");
		condition.setField2("joinColumn1");

		// StarJoin �̎q�\ DAO
		StarJoinDAODescriptor descriptor = new StarJoinDAODescriptor();
		descriptor.setDao(this.joinSubDAO);
		descriptor.setAlias("j");
		descriptor.setCondition(condition);

		List<StarJoinDAODescriptor> childDAOs = Arrays.asList(descriptor);
		joinDAO.setChildDAOs(childDAOs);

		
		// ��������
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("m", "mainColumn2", 10, DAOCriteria.EQUALS, false);
		criteria.addWhereClause("j", "joinColumn2", 20, DAOCriteria.EQUALS, false);
		

		// �������炪�A�e�X�g�Ώۏ���
        sql.append("SELECT ");
        joinDAO.addSelectClause(sql, "", null);

        sql.append(" FROM ");
        joinDAO.addFromClause(sql, "", null);

		DAOUtils.addWhereClauses(joinDAO, criteria, "", sql, params, false);

		
		// ���s���ʂ̊m�F
		System.out.println("�ytwoJoinNonDupTest�z");
		System.out.println(sql.toString());
		
		String chk = "SELECT" +
				" m.main_pk_field AS m_main_pk_field, m.main_column1 AS m_main_column1, m.main_column2 AS m_main_column2, m.main_column3 AS m_main_column3," +
				" j.join_pk_field AS j_join_pk_field, j.join_column1 AS j_join_column1, j.join_column2 AS j_join_column2, j.join_column3 AS j_join_column3" +
				" FROM test_main_vo m" +
				" INNER JOIN test_join_sub_vo j ON m.main_column1 = j.join_column1" +
				" WHERE m.main_column2 = ? AND j.join_column2 = ?";

		Assert.assertEquals("SQL ������������", chk, sql.toString());


		params.stream().forEach(obj -> System.out.println(obj));

		Assert.assertEquals("�o�C���h�ϐ�����������(1)", params.get(0), 10);
		Assert.assertEquals("�o�C���h�ϐ�����������(2)", params.get(1), 20);
		Assert.assertEquals("�o�C���h�ϐ�����������(����)", 2, params.size());

	}

	
	
	/**
	 * DAO + DAO �ɂ����� StarJoinDAO �������� SQL ���o�̓e�X�g�i�t�B�[���h�d������j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>��������� SQL �������������B</li>
	 *     <li>�o�C���h�ϐ����X�g�̊i�[�l�A��������������</li>
	 * </ul>
	 */
	@Test
	public void twoJoinDupTest(){

		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();


		// �e�X�g�p StarJoinDAO�@�imain1 -- main2�j
		StarJoinDAO joinDAO = new StarJoinDAO();
		joinDAO.setMainDAO(this.mainDAO);
		joinDAO.setMainAlias("m1");

		// ��������
		JoinCondition condition = new JoinCondition();
		condition.setField1("mainColumn1");
		condition.setField2("mainColumn1");

		// StarJoin �̎q�\ DAO
		StarJoinDAODescriptor descriptor = new StarJoinDAODescriptor();
		descriptor.setDao(this.mainDAO);
		descriptor.setAlias("m2");
		descriptor.setCondition(condition);

		List<StarJoinDAODescriptor> childDAOs = Arrays.asList(descriptor);
		joinDAO.setChildDAOs(childDAOs);

		
		// ��������
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("m1", "mainColumn2", 10, DAOCriteria.EQUALS, false);
		criteria.addWhereClause("m2", "mainColumn3", 20, DAOCriteria.EQUALS, false);
		

		// �������炪�A�e�X�g�Ώۏ���
        sql.append("SELECT ");
        joinDAO.addSelectClause(sql, "", null);

        sql.append(" FROM ");
        joinDAO.addFromClause(sql, "", null);

		DAOUtils.addWhereClauses(joinDAO, criteria, "", sql, params, false);

		
		// ���s���ʂ̊m�F
		System.out.println("�ytwoJoinDupTest�z");
		System.out.println(sql.toString());
		
		String chk = "SELECT" +
				" m1.main_pk_field AS m1_main_pk_field, m1.main_column1 AS m1_main_column1, m1.main_column2 AS m1_main_column2, m1.main_column3 AS m1_main_column3," +
				" m2.main_pk_field AS m2_main_pk_field, m2.main_column1 AS m2_main_column1, m2.main_column2 AS m2_main_column2, m2.main_column3 AS m2_main_column3" +
				" FROM test_main_vo m1" +
				" INNER JOIN test_main_vo m2 ON m1.main_column1 = m2.main_column1" +
				" WHERE m1.main_column2 = ? AND m2.main_column3 = ?";

		Assert.assertEquals("SQL ������������", chk, sql.toString());


		params.stream().forEach(obj -> System.out.println(obj));

		Assert.assertEquals("�o�C���h�ϐ�����������(1)", params.get(0), 10);
		Assert.assertEquals("�o�C���h�ϐ�����������(2)", params.get(1), 20);
		Assert.assertEquals("�o�C���h�ϐ�����������(����)", 2, params.size());

	}


	
	/**
	 * DAO + DAO �ɂ����� StarJoinDAO �������� SQL ���o�̓e�X�g�i�t�B�[���h�d���Ȃ��j<br/>
	 * �i�\�P -- �\�Q�A �\�P -- �\�R�j
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>��������� SQL �������������B</li>
	 *     <li>�o�C���h�ϐ����X�g�̊i�[�l�A��������������</li>
	 * </ul>
	 */
	@Test
	public void threeJoinNonDupTest1(){

		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();


		// �e�X�g�p1 StarJoinDAO�@�imain -- join_sub�j
		StarJoinDAO joinDAO = new StarJoinDAO();
		joinDAO.setMainDAO(this.mainDAO);
		joinDAO.setMainAlias("m");

		// ��������1
		JoinCondition condition1 = new JoinCondition();
		condition1.setField1("mainColumn1");
		condition1.setField2("joinColumn1");

		// StarJoin �̎q�\ DAO1
		StarJoinDAODescriptor descriptor1 = new StarJoinDAODescriptor();
		descriptor1.setDao(this.joinSubDAO);
		descriptor1.setAlias("j");
		descriptor1.setCondition(condition1);

		
		// ��������2
		JoinCondition condition2 = new JoinCondition();
		condition2.setField1("mainColumn1");
		condition2.setField2("subColumn1");

		// StarJoin �̎q�\ DAO2
		StarJoinDAODescriptor descriptor2 = new StarJoinDAODescriptor();
		descriptor2.setDao(this.subDAO);
		descriptor2.setAlias("s");
		descriptor2.setCondition(condition2);


		List<StarJoinDAODescriptor> childDAOs = Arrays.asList(descriptor1,descriptor2);
		joinDAO.setChildDAOs(childDAOs);


		// ��������
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("m", "mainColumn2", 10, DAOCriteria.EQUALS, false);
		criteria.addWhereClause("j", "joinColumn2", 20, DAOCriteria.EQUALS, false);
		criteria.addWhereClause("s", "subColumn2", 30, DAOCriteria.EQUALS, false);
		

		// �������炪�A�e�X�g�Ώۏ���
        sql.append("SELECT ");
        joinDAO.addSelectClause(sql, "", null);

        sql.append(" FROM ");
        joinDAO.addFromClause(sql, "", null);

		DAOUtils.addWhereClauses(joinDAO, criteria, "", sql, params, false);

		
		// ���s���ʂ̊m�F
		System.out.println("�ythreeJoinNonDupTest1�z");
		System.out.println(sql.toString());
		
		String chk = "SELECT" +
				" m.main_pk_field AS m_main_pk_field, m.main_column1 AS m_main_column1, m.main_column2 AS m_main_column2, m.main_column3 AS m_main_column3," +
				" j.join_pk_field AS j_join_pk_field, j.join_column1 AS j_join_column1, j.join_column2 AS j_join_column2, j.join_column3 AS j_join_column3," +
				" s.sub_pk_field AS s_sub_pk_field, s.sub_column1 AS s_sub_column1, s.sub_column2 AS s_sub_column2, s.sub_column3 AS s_sub_column3" +
				" FROM test_main_vo m" +
				" INNER JOIN test_join_sub_vo j ON m.main_column1 = j.join_column1" +
				" INNER JOIN test_sub_vo s ON m.main_column1 = s.sub_column1" +
				" WHERE m.main_column2 = ? AND j.join_column2 = ? AND s.sub_column2 = ?";

		Assert.assertEquals("SQL ������������", chk, sql.toString());


		params.stream().forEach(obj -> System.out.println(obj));

		Assert.assertEquals("�o�C���h�ϐ�����������(1)", params.get(0), 10);
		Assert.assertEquals("�o�C���h�ϐ�����������(2)", params.get(1), 20);
		Assert.assertEquals("�o�C���h�ϐ�����������(3)", params.get(2), 30);
		Assert.assertEquals("�o�C���h�ϐ�����������(����)", 3, params.size());

	}


	// �i�\�P -- �\�Q�@-- �\�R�j �̌����́AStarJoinDAO �̃T�|�[�g�ΏۊO
	// StarJoin �P�ƂŁA �i�i�\�P -- �\�Q�j�@-- �\�R�j �̌����̓T�|�[�g�ΏۊO
	
	
	/**
	 * DAO + DAO + DAO �ɂ����� StarJoinDAO �������� SQL ���o�̓e�X�g�i�t�B�[���h�d������j<br/>
	 * �i�\�P -- �\�Q�A �\�P -- �\�R�j
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>��������� SQL �������������B</li>
	 *     <li>�o�C���h�ϐ����X�g�̊i�[�l�A��������������</li>
	 * </ul>
	 */
	@Test
	public void threeJoinDupTest1(){

		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();


		// �e�X�g�p1 JoinDAO�@�imain1 -- main2�j
		StarJoinDAO joinDAO = new StarJoinDAO();
		joinDAO.setMainAlias("m1");
		joinDAO.setMainDAO(this.mainDAO);

		
		// ���������imain1 -- main2�j
		JoinCondition condition1 = new JoinCondition();
		condition1.setField1("mainColumn1");
		condition1.setField2("mainColumn2");
		
		// StarJoin �̎q�\ DAO1
		StarJoinDAODescriptor descriptor1 = new StarJoinDAODescriptor();
		descriptor1.setDao(this.mainDAO);
		descriptor1.setAlias("m2");
		descriptor1.setCondition(condition1);

		// ���������imain1 -- main3�j
		JoinCondition condition2 = new JoinCondition();
		condition2.setField1("mainColumn1");
		condition2.setField2("mainColumn3");

		// StarJoin �̎q�\ DAO2
		StarJoinDAODescriptor descriptor2 = new StarJoinDAODescriptor();
		descriptor2.setDao(this.mainDAO);
		descriptor2.setAlias("m3");
		descriptor2.setCondition(condition2);

		List<StarJoinDAODescriptor> childDAOs = Arrays.asList(descriptor1, descriptor2);
		joinDAO.setChildDAOs(childDAOs);

		
		// ��������
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("m1", "mainColumn1", 10, DAOCriteria.EQUALS, false);
		criteria.addWhereClause("m2", "mainColumn2", 20, DAOCriteria.EQUALS, false);
		criteria.addWhereClause("m3", "mainColumn3", 30, DAOCriteria.EQUALS, false);
		

		// �������炪�A�e�X�g�Ώۏ���
        sql.append("SELECT ");
        joinDAO.addSelectClause(sql, "", null);

        sql.append(" FROM ");
        joinDAO.addFromClause(sql, "", null);

		DAOUtils.addWhereClauses(joinDAO, criteria, "", sql, params, false);

		
		// ���s���ʂ̊m�F
		System.out.println("�ythreeJoinDupTest1�z");
		System.out.println(sql.toString());
		
		String chk = "SELECT" +
				" m1.main_pk_field AS m1_main_pk_field, m1.main_column1 AS m1_main_column1, m1.main_column2 AS m1_main_column2, m1.main_column3 AS m1_main_column3," +
				" m2.main_pk_field AS m2_main_pk_field, m2.main_column1 AS m2_main_column1, m2.main_column2 AS m2_main_column2, m2.main_column3 AS m2_main_column3," +
				" m3.main_pk_field AS m3_main_pk_field, m3.main_column1 AS m3_main_column1, m3.main_column2 AS m3_main_column2, m3.main_column3 AS m3_main_column3" +
				" FROM test_main_vo m1" +
				" INNER JOIN test_main_vo m2 ON m1.main_column1 = m2.main_column2" +
				" INNER JOIN test_main_vo m3 ON m1.main_column1 = m3.main_column3" +
				" WHERE m1.main_column1 = ? AND m2.main_column2 = ? AND m3.main_column3 = ?";

		Assert.assertEquals("SQL ������������", chk, sql.toString());


		params.stream().forEach(obj -> System.out.println(obj));

		Assert.assertEquals("�o�C���h�ϐ�����������(1)", params.get(0), 10);
		Assert.assertEquals("�o�C���h�ϐ�����������(2)", params.get(1), 20);
		Assert.assertEquals("�o�C���h�ϐ�����������(3)", params.get(2), 30);
		Assert.assertEquals("�o�C���h�ϐ�����������(����)", 3, params.size());

	}
	
}
