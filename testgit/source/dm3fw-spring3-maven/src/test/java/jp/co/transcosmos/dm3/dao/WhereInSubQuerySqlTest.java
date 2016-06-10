package jp.co.transcosmos.dm3.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.transcosmos.mock.vo.TestJoinSubVo;
import jp.co.transcosmos.mock.vo.TestMainVo;
import jp.co.transcosmos.mock.vo.TestSubVo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * WHERE IN �T�u�N�G����� SQL �����e�X�g<br/>
 * <br/>
 */
public class WhereInSubQuerySqlTest {

	private ReflectingDAO<TestMainVo> mainDAO;
	private ReflectingDAO<TestJoinSubVo> joinSubDAO;
	private ReflectingDAO<TestSubVo> subDAO;

	private JoinDAO joinDAO;
	private StarJoinDAO starJoinDAO;

	
	@Before
	public void init(){

		// �e�X�g�p�e�\ DAO
		this.mainDAO = new ReflectingDAO<>();
		this.mainDAO.setValueObjectClassName("jp.co.transcosmos.mock.vo.TestMainVo");

		// �e�X�g�p�q�\ DAO
		this.joinSubDAO = new ReflectingDAO<>();
		this.joinSubDAO.setValueObjectClassName("jp.co.transcosmos.mock.vo.TestJoinSubVo");

		// �e�X�g�p�T�u�N�G���\ DAO
		this.subDAO = new ReflectingDAO<>();
		this.subDAO.setValueObjectClassName("jp.co.transcosmos.mock.vo.TestSubVo");


		// ��������
		JoinCondition condition = new JoinCondition();
		condition.setField1("mainColumn1");
		condition.setField2("joinColumn1");

		
		// �e�X�g�p JoinDAO
		this.joinDAO = new JoinDAO();
		this.joinDAO.setJoinable1(this.mainDAO);
		this.joinDAO.setAlias1("m");
		this.joinDAO.setJoinable2(this.joinSubDAO);
		this.joinDAO.setAlias2("j");
		this.joinDAO.setCondition(condition);


		// �e�X�g�p StarJoinDAO
		this.starJoinDAO = new StarJoinDAO();
		this.starJoinDAO.setMainDAO(this.mainDAO);
		this.starJoinDAO.setMainAlias("m");

		// StarJoin �̎q�\ DAO
		StarJoinDAODescriptor descriptor = new StarJoinDAODescriptor();
		descriptor.setDao(this.joinSubDAO);
		descriptor.setAlias("j");
		descriptor.setCondition(condition);

		List<StarJoinDAODescriptor> childDAOs = Arrays.asList(descriptor);

		this.starJoinDAO.setChildDAOs(childDAOs);

	}


	
	/**
	 * �P��\�ɂ�����AWHERE IN �T�u�N�G����� SQL ���o�̓e�X�g<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>��������� SQL �������������B</li>
	 *     <li>�o�C���h�ϐ����X�g�̊i�[�l�A��������������</li>
	 * </ul>
	 */
	@Test
	public void existsWhereTest(){

		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();


		// ���C�� DAO �̌�������
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("mainColumn3", 10);



		// IN �Ŏg�p����T�u�N�G�� �I�u�W�F�N�g
		InSubQuery subQuery = new InSubQuery(this.subDAO, null, "mainColumn2", "test_main_vo", "subColumn2", "s", false);
		criteria.addSubQuery(subQuery);
		subQuery.setSubField("subColumn2");

		// �T�u�N�G���̌�������
		DAOCriteria subCriteria = new DAOCriteria();
		subCriteria.addWhereClause("subColumn2", 20);
		subCriteria.addWhereClause("subColumn1", new FieldExpression("test_main_vo", "mainColumn1"));
		subQuery.setSubCriteria(subCriteria);
		


		// �������炪�A�e�X�g�Ώۏ���
        sql.append("SELECT ");
        this.mainDAO.addSelectClause(sql, "", null);

        sql.append(" FROM ");
        this.mainDAO.addFromClause(sql, "", null);

		DAOUtils.addWhereClauses(this.mainDAO, criteria, "", sql, params, false);


		// ���s���ʂ̊m�F
		System.out.println("�ysingle�z");
		System.out.println(sql.toString());
		
		String chk = "SELECT main_pk_field, main_column1, main_column2, main_column3" +
				" FROM test_main_vo" +
				" WHERE main_column3 = ? AND" +
				" test_main_vo.main_column2 IN" +
				" (SELECT s.sub_column2 AS s_sub_column2" +
				" FROM test_sub_vo s"+
				" WHERE s.sub_column2 = ? AND s.sub_column1 = test_main_vo.main_column1)";

		Assert.assertEquals("SQL ������������", chk, sql.toString());


		params.stream().forEach(obj -> System.out.println(obj));

		Assert.assertEquals("�o�C���h�ϐ�����������(1)", params.get(0), 10);
		Assert.assertEquals("�o�C���h�ϐ�����������(2)", params.get(1), 20);
		Assert.assertEquals("�o�C���h�ϐ�����������(����)", 2, params.size());

	}



	/**
	 * JoinDAO �ɂ�錋���\�ɑ΂���AWHERE IN �T�u�N�G����� SQL ���o�̓e�X�g<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>��������� SQL �������������B</li>
	 *     <li>�o�C���h�ϐ����X�g�̊i�[�l�A��������������</li>
	 * </ul>
	 */
	@Test
	public void existsJoinWhereTest(){

		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();


		// ���C�� DAO �̌�������
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("mainColumn3", 10);



		// IN �Ŏg�p����T�u�N�G�� �I�u�W�F�N�g
		InSubQuery subQuery = new InSubQuery(this.subDAO, null, "mainColumn2", "m", "subColumn2", "s", false);
		criteria.addSubQuery(subQuery);
		subQuery.setSubField("subColumn2");

		// �T�u�N�G���̌�������
		DAOCriteria subCriteria = new DAOCriteria();
		subCriteria.addWhereClause("subColumn2", 20);
		subCriteria.addWhereClause("subColumn1", new FieldExpression("m", "mainColumn1"));
		subQuery.setSubCriteria(subCriteria);
		


		// �������炪�A�e�X�g�Ώۏ���
        sql.append("SELECT ");
        this.joinDAO.addSelectClause(sql, "", null);

        sql.append(" FROM ");
        this.joinDAO.addFromClause(sql, "", null);

		DAOUtils.addWhereClauses(this.joinDAO, criteria, "", sql, params, false);


		// ���s���ʂ̊m�F
		System.out.println("�yjoinDAO�z");
		System.out.println(sql.toString());

		String chk = "SELECT" +
				" m.main_pk_field AS m_main_pk_field, m.main_column1 AS m_main_column1, m.main_column2 AS m_main_column2, m.main_column3 AS m_main_column3," +
				" j.join_pk_field AS j_join_pk_field, j.join_column1 AS j_join_column1, j.join_column2 AS j_join_column2, j.join_column3 AS j_join_column3" +
				" FROM test_main_vo m" +
				" INNER JOIN test_join_sub_vo j ON m.main_column1 = j.join_column1" +
				" WHERE m.main_column3 = ? AND" +
				" m.main_column2 IN" +
				" (SELECT s.sub_column2 AS s_sub_column2" +
				" FROM test_sub_vo s" +
				" WHERE s.sub_column2 = ? AND s.sub_column1 = m.main_column1)";

		Assert.assertEquals("SQL ������������", chk, sql.toString());


		params.stream().forEach(obj -> System.out.println(obj));

		Assert.assertEquals("�o�C���h�ϐ�����������(1)", params.get(0), 10);
		Assert.assertEquals("�o�C���h�ϐ�����������(2)", params.get(1), 20);
		Assert.assertEquals("�o�C���h�ϐ�����������(����)", 2, params.size());
	}



	/**
	 * StarJoinDAO �ɂ�錋���\�ɑ΂���AWHERE IN �T�u�N�G����� SQL ���o�̓e�X�g<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>��������� SQL �������������B</li>
	 *     <li>�o�C���h�ϐ����X�g�̊i�[�l�A��������������</li>
	 * </ul>
	 */
	@Test
	public void existsStarJoinWhereTest(){

		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();


		// ���C�� DAO �̌�������
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("mainColumn3", 10);



		// IN �Ŏg�p����T�u�N�G�� �I�u�W�F�N�g
		InSubQuery subQuery = new InSubQuery(this.subDAO, null, "mainColumn2", "m", "subColumn2", "s", false);
		criteria.addSubQuery(subQuery);
		subQuery.setSubField("subColumn2");

		// �T�u�N�G���̌�������
		DAOCriteria subCriteria = new DAOCriteria();
		subCriteria.addWhereClause("subColumn2", 20);
		subCriteria.addWhereClause("subColumn1", new FieldExpression("m", "mainColumn1"));
		subQuery.setSubCriteria(subCriteria);
		


		// �������炪�A�e�X�g�Ώۏ���
        sql.append("SELECT ");
        this.starJoinDAO.addSelectClause(sql, "", null);

        sql.append(" FROM ");
        this.starJoinDAO.addFromClause(sql, "", null);

		DAOUtils.addWhereClauses(this.starJoinDAO, criteria, "", sql, params, false);


		// ���s���ʂ̊m�F
		System.out.println("�ystarJoinDAO�z");
		System.out.println(sql.toString());
		
		String chk = "SELECT" +
				" m.main_pk_field AS m_main_pk_field, m.main_column1 AS m_main_column1, m.main_column2 AS m_main_column2, m.main_column3 AS m_main_column3," +
				" j.join_pk_field AS j_join_pk_field, j.join_column1 AS j_join_column1, j.join_column2 AS j_join_column2, j.join_column3 AS j_join_column3" +
				" FROM test_main_vo m" +
				" INNER JOIN test_join_sub_vo j ON m.main_column1 = j.join_column1" +
				" WHERE m.main_column3 = ? AND" +
				" m.main_column2 IN" +
				" (SELECT s.sub_column2 AS s_sub_column2" +
				" FROM test_sub_vo s" +
				" WHERE s.sub_column2 = ? AND s.sub_column1 = m.main_column1)";

		Assert.assertEquals("SQL ������������", chk, sql.toString());

		
		params.stream().forEach(obj -> System.out.println(obj));

		Assert.assertEquals("�o�C���h�ϐ�����������(1)", params.get(0), 10);
		Assert.assertEquals("�o�C���h�ϐ�����������(2)", params.get(1), 20);
		Assert.assertEquals("�o�C���h�ϐ�����������(����)", 2, params.size());

	}

	
	
}
