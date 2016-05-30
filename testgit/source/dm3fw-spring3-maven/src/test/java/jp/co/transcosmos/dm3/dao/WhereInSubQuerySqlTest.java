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
 * WHERE IN サブクエリ句の SQL 生成テスト<br/>
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

		// テスト用親表 DAO
		this.mainDAO = new ReflectingDAO<>();
		this.mainDAO.setValueObjectClassName("jp.co.transcosmos.mock.vo.TestMainVo");

		// テスト用子表 DAO
		this.joinSubDAO = new ReflectingDAO<>();
		this.joinSubDAO.setValueObjectClassName("jp.co.transcosmos.mock.vo.TestJoinSubVo");

		// テスト用サブクエリ表 DAO
		this.subDAO = new ReflectingDAO<>();
		this.subDAO.setValueObjectClassName("jp.co.transcosmos.mock.vo.TestSubVo");


		// 結合条件
		JoinCondition condition = new JoinCondition();
		condition.setField1("mainColumn1");
		condition.setField2("joinColumn1");

		
		// テスト用 JoinDAO
		this.joinDAO = new JoinDAO();
		this.joinDAO.setJoinable1(this.mainDAO);
		this.joinDAO.setAlias1("m");
		this.joinDAO.setJoinable2(this.joinSubDAO);
		this.joinDAO.setAlias2("j");
		this.joinDAO.setCondition(condition);


		// テスト用 StarJoinDAO
		this.starJoinDAO = new StarJoinDAO();
		this.starJoinDAO.setMainDAO(this.mainDAO);
		this.starJoinDAO.setMainAlias("m");

		// StarJoin の子表 DAO
		StarJoinDAODescriptor descriptor = new StarJoinDAODescriptor();
		descriptor.setDao(this.joinSubDAO);
		descriptor.setAlias("j");
		descriptor.setCondition(condition);

		List<StarJoinDAODescriptor> childDAOs = Arrays.asList(descriptor);

		this.starJoinDAO.setChildDAOs(childDAOs);

	}


	
	/**
	 * 単一表における、WHERE IN サブクエリ句の SQL 文出力テスト<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>生成される SQL 文が正しい事。</li>
	 *     <li>バインド変数リストの格納値、件数が正しい事</li>
	 * </ul>
	 */
	@Test
	public void existsWhereTest(){

		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();


		// メイン DAO の検索条件
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("mainColumn3", 10);



		// IN で使用するサブクエリ オブジェクト
		InSubQuery subQuery = new InSubQuery(this.subDAO, null, "mainColumn2", "test_main_vo", "subColumn2", "s", false);
		criteria.addSubQuery(subQuery);
		subQuery.setSubField("subColumn2");

		// サブクエリの検索条件
		DAOCriteria subCriteria = new DAOCriteria();
		subCriteria.addWhereClause("subColumn2", 20);
		subCriteria.addWhereClause("subColumn1", new FieldExpression("test_main_vo", "mainColumn1"));
		subQuery.setSubCriteria(subCriteria);
		


		// ここからが、テスト対象処理
        sql.append("SELECT ");
        this.mainDAO.addSelectClause(sql, "", null);

        sql.append(" FROM ");
        this.mainDAO.addFromClause(sql, "", null);

		DAOUtils.addWhereClauses(this.mainDAO, criteria, "", sql, params, false);


		// 実行結果の確認
		System.out.println("【single】");
		System.out.println(sql.toString());
		
		String chk = "SELECT main_pk_field, main_column1, main_column2, main_column3" +
				" FROM test_main_vo" +
				" WHERE main_column3 = ? AND" +
				" test_main_vo.main_column2 IN" +
				" (SELECT s.sub_column2 AS s_sub_column2" +
				" FROM test_sub_vo s"+
				" WHERE s.sub_column2 = ? AND s.sub_column1 = test_main_vo.main_column1)";

		Assert.assertEquals("SQL 文が正しい事", chk, sql.toString());


		params.stream().forEach(obj -> System.out.println(obj));

		Assert.assertEquals("バインド変数が正しい事(1)", params.get(0), 10);
		Assert.assertEquals("バインド変数が正しい事(2)", params.get(1), 20);
		Assert.assertEquals("バインド変数が正しい事(件数)", 2, params.size());

	}



	/**
	 * JoinDAO による結合表に対する、WHERE IN サブクエリ句の SQL 文出力テスト<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>生成される SQL 文が正しい事。</li>
	 *     <li>バインド変数リストの格納値、件数が正しい事</li>
	 * </ul>
	 */
	@Test
	public void existsJoinWhereTest(){

		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();


		// メイン DAO の検索条件
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("mainColumn3", 10);



		// IN で使用するサブクエリ オブジェクト
		InSubQuery subQuery = new InSubQuery(this.subDAO, null, "mainColumn2", "m", "subColumn2", "s", false);
		criteria.addSubQuery(subQuery);
		subQuery.setSubField("subColumn2");

		// サブクエリの検索条件
		DAOCriteria subCriteria = new DAOCriteria();
		subCriteria.addWhereClause("subColumn2", 20);
		subCriteria.addWhereClause("subColumn1", new FieldExpression("m", "mainColumn1"));
		subQuery.setSubCriteria(subCriteria);
		


		// ここからが、テスト対象処理
        sql.append("SELECT ");
        this.joinDAO.addSelectClause(sql, "", null);

        sql.append(" FROM ");
        this.joinDAO.addFromClause(sql, "", null);

		DAOUtils.addWhereClauses(this.joinDAO, criteria, "", sql, params, false);


		// 実行結果の確認
		System.out.println("【joinDAO】");
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

		Assert.assertEquals("SQL 文が正しい事", chk, sql.toString());


		params.stream().forEach(obj -> System.out.println(obj));

		Assert.assertEquals("バインド変数が正しい事(1)", params.get(0), 10);
		Assert.assertEquals("バインド変数が正しい事(2)", params.get(1), 20);
		Assert.assertEquals("バインド変数が正しい事(件数)", 2, params.size());
	}



	/**
	 * StarJoinDAO による結合表に対する、WHERE IN サブクエリ句の SQL 文出力テスト<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>生成される SQL 文が正しい事。</li>
	 *     <li>バインド変数リストの格納値、件数が正しい事</li>
	 * </ul>
	 */
	@Test
	public void existsStarJoinWhereTest(){

		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();


		// メイン DAO の検索条件
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("mainColumn3", 10);



		// IN で使用するサブクエリ オブジェクト
		InSubQuery subQuery = new InSubQuery(this.subDAO, null, "mainColumn2", "m", "subColumn2", "s", false);
		criteria.addSubQuery(subQuery);
		subQuery.setSubField("subColumn2");

		// サブクエリの検索条件
		DAOCriteria subCriteria = new DAOCriteria();
		subCriteria.addWhereClause("subColumn2", 20);
		subCriteria.addWhereClause("subColumn1", new FieldExpression("m", "mainColumn1"));
		subQuery.setSubCriteria(subCriteria);
		


		// ここからが、テスト対象処理
        sql.append("SELECT ");
        this.starJoinDAO.addSelectClause(sql, "", null);

        sql.append(" FROM ");
        this.starJoinDAO.addFromClause(sql, "", null);

		DAOUtils.addWhereClauses(this.starJoinDAO, criteria, "", sql, params, false);


		// 実行結果の確認
		System.out.println("【starJoinDAO】");
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

		Assert.assertEquals("SQL 文が正しい事", chk, sql.toString());

		
		params.stream().forEach(obj -> System.out.println(obj));

		Assert.assertEquals("バインド変数が正しい事(1)", params.get(0), 10);
		Assert.assertEquals("バインド変数が正しい事(2)", params.get(1), 20);
		Assert.assertEquals("バインド変数が正しい事(件数)", 2, params.size());

	}

	
	
}
