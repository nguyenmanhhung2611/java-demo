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
 * JoinDAO、StarJoinDAO の複合テスト<br/>
 * <br/>
 */
public class MixJoinSqlTest {

	private ReflectingDAO<TestMainVo> mainDAO;
	private ReflectingDAO<TestJoinSubVo> joinSubDAO;
	private ReflectingDAO<TestSubVo> subDAO;

	
	
	@Before
	public void init() {

		// テスト用親表 DAO
		this.mainDAO = new ReflectingDAO<>();
		this.mainDAO.setValueObjectClassName("jp.co.transcosmos.mock.vo.TestMainVo");

		// テスト用子表1 DAO
		this.joinSubDAO = new ReflectingDAO<>();
		this.joinSubDAO.setValueObjectClassName("jp.co.transcosmos.mock.vo.TestJoinSubVo");

		// テスト用子表2 DAO
		this.subDAO = new ReflectingDAO<>();
		this.subDAO.setValueObjectClassName("jp.co.transcosmos.mock.vo.TestSubVo");

	}

	
	
	/**
	 * JoinDAO + DAO における StarJoinDAO 結合時の SQL 文出力テスト（フィールド重複なし）<br/>
	 * （表１ -- 表２　-- 表３）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>生成される SQL 文が正しい事。</li>
	 *     <li>バインド変数リストの格納値、件数が正しい事</li>
	 * </ul>
	 */
	@Test
	public void joinAndStarNonDupTest1(){

		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();


		// テスト用1 JoinDAO　（main -- join_sub）
		JoinDAO joinDAO1 = new JoinDAO();
		joinDAO1.setJoinable1(this.mainDAO);
		joinDAO1.setAlias1("m");
		joinDAO1.setJoinable2(this.joinSubDAO);
		joinDAO1.setAlias2("j");

		// 結合条件（main -- join_sub）
		JoinCondition condition1 = new JoinCondition();
		condition1.setField1("mainColumn1");
		condition1.setField2("joinColumn1");
		joinDAO1.setCondition(condition1);

		// JoinDAO に対して、starJoinDAO を使用する。
		StarJoinDAO joinDAO2 = new StarJoinDAO();
		joinDAO2.setMainDAO(joinDAO1);
		
		// 結合条件2
		JoinCondition condition2 = new JoinCondition();
		condition2.setField1("joinColumn1");
		condition2.setField2("subColumn1");
		
		// StarJoin の子表 DAO
		StarJoinDAODescriptor descriptor1 = new StarJoinDAODescriptor();
		descriptor1.setDao(this.subDAO);
		descriptor1.setAlias("s");
		descriptor1.setCondition(condition2);

		List<StarJoinDAODescriptor> childDAOs = Arrays.asList(descriptor1);
		joinDAO2.setChildDAOs(childDAOs);


		// 検索条件
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("m", "mainColumn2", 10, DAOCriteria.EQUALS, false);
		criteria.addWhereClause("j", "joinColumn2", 20, DAOCriteria.EQUALS, false);
		criteria.addWhereClause("s", "subColumn2", 30, DAOCriteria.EQUALS, false);
		

		// ここからが、テスト対象処理
        sql.append("SELECT ");
        joinDAO2.addSelectClause(sql, "", null);

        sql.append(" FROM ");
        joinDAO2.addFromClause(sql, "", null);

		DAOUtils.addWhereClauses(joinDAO2, criteria, "", sql, params, false);

		
		// 実行結果の確認
		System.out.println("【joinAndStarNonDupTest1】");
		System.out.println(sql.toString());
		
		String chk = "SELECT" +
				" m.main_pk_field AS m_main_pk_field, m.main_column1 AS m_main_column1, m.main_column2 AS m_main_column2, m.main_column3 AS m_main_column3," +
				" j.join_pk_field AS j_join_pk_field, j.join_column1 AS j_join_column1, j.join_column2 AS j_join_column2, j.join_column3 AS j_join_column3," +
				" s.sub_pk_field AS s_sub_pk_field, s.sub_column1 AS s_sub_column1, s.sub_column2 AS s_sub_column2, s.sub_column3 AS s_sub_column3" +
				" FROM test_main_vo m" +
				" INNER JOIN test_join_sub_vo j ON m.main_column1 = j.join_column1" +
				" INNER JOIN test_sub_vo s ON j.join_column1 = s.sub_column1" +
				" WHERE m.main_column2 = ? AND j.join_column2 = ? AND s.sub_column2 = ?";

		Assert.assertEquals("SQL 文が正しい事", chk, sql.toString());


		params.stream().forEach(obj -> System.out.println(obj));

		Assert.assertEquals("バインド変数が正しい事(1)", params.get(0), 10);
		Assert.assertEquals("バインド変数が正しい事(2)", params.get(1), 20);
		Assert.assertEquals("バインド変数が正しい事(3)", params.get(2), 30);
		Assert.assertEquals("バインド変数が正しい事(件数)", 3, params.size());

	}

	
	
	/**
	 * JoinDAO + DAO における StarJoinDAO 結合時の SQL 文出力テスト（フィールド重複なし）<br/>
	 * （（表１ -- 表２）　-- 表３） ... 表３は、表１、表２の列に依存
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>生成される SQL 文が正しい事。</li>
	 *     <li>バインド変数リストの格納値、件数が正しい事</li>
	 * </ul>
	 */
	@Test
	public void joinAndStarNonDupTest2(){

		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();


		// テスト用1 JoinDAO　（main -- join_sub）
		JoinDAO joinDAO1 = new JoinDAO();
		joinDAO1.setJoinable1(this.mainDAO);
		joinDAO1.setAlias1("m");
		joinDAO1.setJoinable2(this.joinSubDAO);
		joinDAO1.setAlias2("j");

		// 結合条件（main -- join_sub）
		JoinCondition condition1 = new JoinCondition();
		condition1.setField1("mainColumn1");
		condition1.setField2("joinColumn1");
		joinDAO1.setCondition(condition1);
		
		
		// テスト用2 JoinDAO　（（main -- join_sub） -- sub）
		StarJoinDAO joinDAO2 = new StarJoinDAO();
		joinDAO2.setMainDAO(joinDAO1);

		// 結合条件2（main -- sub）
		JoinCondition condition2_1 = new JoinCondition();
		condition2_1.setField1("mainColumn1");
		condition2_1.setField2("subColumn1");

		// 結合条件2（join_sub -- sub）
		JoinCondition condition2_2 = new JoinCondition();
		condition2_2.setField1("joinColumn1");
		condition2_2.setField2("subColumn1");


		// StarJoin の子表 DAO
		StarJoinDAODescriptor descriptor1 = new StarJoinDAODescriptor();
		descriptor1.setDao(this.subDAO);
		descriptor1.setAlias("s");
		descriptor1.setConditionList(Arrays.asList(condition2_1, condition2_2));

		List<StarJoinDAODescriptor> childDAOs = Arrays.asList(descriptor1);
		joinDAO2.setChildDAOs(childDAOs);

		
		// 検索条件
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("m", "mainColumn2", 10, DAOCriteria.EQUALS, false);
		criteria.addWhereClause("j", "joinColumn2", 20, DAOCriteria.EQUALS, false);
		criteria.addWhereClause("s", "subColumn2", 30, DAOCriteria.EQUALS, false);
		

		// ここからが、テスト対象処理
        sql.append("SELECT ");
        joinDAO2.addSelectClause(sql, "", null);

        sql.append(" FROM ");
        joinDAO2.addFromClause(sql, "", null);

		DAOUtils.addWhereClauses(joinDAO2, criteria, "", sql, params, false);

		
		// 実行結果の確認
		System.out.println("【joinAndStarNonDupTest2】");
		System.out.println(sql.toString());
		
		String chk = "SELECT" +
				" m.main_pk_field AS m_main_pk_field, m.main_column1 AS m_main_column1, m.main_column2 AS m_main_column2, m.main_column3 AS m_main_column3," +
				" j.join_pk_field AS j_join_pk_field, j.join_column1 AS j_join_column1, j.join_column2 AS j_join_column2, j.join_column3 AS j_join_column3," +
				" s.sub_pk_field AS s_sub_pk_field, s.sub_column1 AS s_sub_column1, s.sub_column2 AS s_sub_column2, s.sub_column3 AS s_sub_column3" +
				" FROM test_main_vo m" +
				" INNER JOIN test_join_sub_vo j ON m.main_column1 = j.join_column1" +
				" INNER JOIN test_sub_vo s ON m.main_column1 = s.sub_column1 AND j.join_column1 = s.sub_column1" +
				" WHERE m.main_column2 = ? AND j.join_column2 = ? AND s.sub_column2 = ?";

		Assert.assertEquals("SQL 文が正しい事", chk, sql.toString());


		params.stream().forEach(obj -> System.out.println(obj));

		Assert.assertEquals("バインド変数が正しい事(1)", params.get(0), 10);
		Assert.assertEquals("バインド変数が正しい事(2)", params.get(1), 20);
		Assert.assertEquals("バインド変数が正しい事(3)", params.get(2), 30);
		Assert.assertEquals("バインド変数が正しい事(件数)", 3, params.size());

	}

	
	
	/**
	 * JoinDAO + DAO における StarJoinDAO 結合時の SQL 文出力テスト（フィールド重複あり）<br/>
	 * （表１ -- 表２ -- 表３）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>生成される SQL 文が正しい事。</li>
	 *     <li>バインド変数リストの格納値、件数が正しい事</li>
	 * </ul>
	 */
	@Test
	public void joinAndStarDupTest1(){

		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();


		// テスト用1 JoinDAO　（main1 -- main2）
		JoinDAO joinDAO1 = new JoinDAO();
		joinDAO1.setJoinable1(this.mainDAO);
		joinDAO1.setAlias1("m1");
		joinDAO1.setJoinable2(this.mainDAO);
		joinDAO1.setAlias2("m2");


		// 結合条件（main1 -- main2）
		JoinCondition condition1 = new JoinCondition();
		condition1.setField1("mainColumn1");
		condition1.setField2("mainColumn2");
		joinDAO1.setCondition(condition1);
		
		
		// テスト用2 JoinDAO　（main1 -- main2 -- main3）
		StarJoinDAO joinDAO2 = new StarJoinDAO();
		joinDAO2.setMainDAO(joinDAO1);

		// 結合条件（main2 -- main3）
		JoinCondition condition2 = new JoinCondition();
		condition2.setAlias1("m2");
		condition2.setField1("mainColumn1");
		condition2.setAlias2("m3");
		condition2.setField2("mainColumn3");

		// StarJoin の子表 DAO
		StarJoinDAODescriptor descriptor1 = new StarJoinDAODescriptor();
		descriptor1.setDao(this.mainDAO);
		descriptor1.setAlias("m3");
		descriptor1.setCondition(condition2);

		List<StarJoinDAODescriptor> childDAOs = Arrays.asList(descriptor1);
		joinDAO2.setChildDAOs(childDAOs);

		
		// 検索条件
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("m1", "mainColumn1", 10, DAOCriteria.EQUALS, false);
		criteria.addWhereClause("m2", "mainColumn2", 20, DAOCriteria.EQUALS, false);
		criteria.addWhereClause("m3", "mainColumn3", 30, DAOCriteria.EQUALS, false);
		

		// ここからが、テスト対象処理
        sql.append("SELECT ");
        joinDAO2.addSelectClause(sql, "", null);

        sql.append(" FROM ");
        joinDAO2.addFromClause(sql, "", null);

		DAOUtils.addWhereClauses(joinDAO2, criteria, "", sql, params, false);

		
		// 実行結果の確認
		System.out.println("【joinAndStarDupTest1】");
		System.out.println(sql.toString());
		
		String chk = "SELECT" +
				" m1.main_pk_field AS m1_main_pk_field, m1.main_column1 AS m1_main_column1, m1.main_column2 AS m1_main_column2, m1.main_column3 AS m1_main_column3," +
				" m2.main_pk_field AS m2_main_pk_field, m2.main_column1 AS m2_main_column1, m2.main_column2 AS m2_main_column2, m2.main_column3 AS m2_main_column3," +
				" m3.main_pk_field AS m3_main_pk_field, m3.main_column1 AS m3_main_column1, m3.main_column2 AS m3_main_column2, m3.main_column3 AS m3_main_column3" +
				" FROM test_main_vo m1" +
				" INNER JOIN test_main_vo m2 ON m1.main_column1 = m2.main_column2" +
				" INNER JOIN test_main_vo m3 ON m2.main_column1 = m3.main_column3" +
				" WHERE m1.main_column1 = ? AND m2.main_column2 = ? AND m3.main_column3 = ?";

		Assert.assertEquals("SQL 文が正しい事", chk, sql.toString());


		params.stream().forEach(obj -> System.out.println(obj));

		Assert.assertEquals("バインド変数が正しい事(1)", params.get(0), 10);
		Assert.assertEquals("バインド変数が正しい事(2)", params.get(1), 20);
		Assert.assertEquals("バインド変数が正しい事(3)", params.get(2), 30);
		Assert.assertEquals("バインド変数が正しい事(件数)", 3, params.size());

	}



	/**
	 * JoinDAO + DAO における StarJoinDAO 結合時の SQL 文出力テスト（フィールド重複あり）<br/>
	 * （（表１ -- 表２） -- 表３） ... 表３は、表１、表２の列に依存
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>生成される SQL 文が正しい事。</li>
	 *     <li>バインド変数リストの格納値、件数が正しい事</li>
	 * </ul>
	 */
	@Test
	public void joinAndStarDupTest2(){

		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();


		// テスト用1 JoinDAO　（main1 -- main2）
		JoinDAO joinDAO1 = new JoinDAO();
		joinDAO1.setJoinable1(this.mainDAO);
		joinDAO1.setAlias1("m1");
		joinDAO1.setJoinable2(this.mainDAO);
		joinDAO1.setAlias2("m2");


		// 結合条件（main1 -- main2）
		JoinCondition condition1 = new JoinCondition();
		condition1.setField1("mainColumn1");
		condition1.setField2("mainColumn2");
		joinDAO1.setCondition(condition1);

		
		// テスト用2 JoinDAO　（（main1 -- main2） -- main3）
		StarJoinDAO joinDAO2 = new StarJoinDAO();
		joinDAO2.setMainDAO(joinDAO1);

		
		
		// 結合条件（main1 -- main3）
		JoinCondition condition2_1 = new JoinCondition();
		condition2_1.setAlias1("m1");
		condition2_1.setField1("mainColumn1");
		condition2_1.setAlias2("m3");
		condition2_1.setField2("mainColumn2");

		// 結合条件（main2 -- main3）
		JoinCondition condition2_2 = new JoinCondition();
		condition2_2.setAlias1("m2");
		condition2_2.setField1("mainColumn1");
		condition2_2.setAlias2("m3");
		condition2_2.setField2("mainColumn3");
		
		// StarJoin の子表 DAO
		StarJoinDAODescriptor descriptor1 = new StarJoinDAODescriptor();
		descriptor1.setDao(this.mainDAO);
		descriptor1.setAlias("m3");
		descriptor1.setConditionList(Arrays.asList(condition2_1,condition2_2));

		List<StarJoinDAODescriptor> childDAOs = Arrays.asList(descriptor1);
		joinDAO2.setChildDAOs(childDAOs);

		
		// 検索条件
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("m1", "mainColumn1", 10, DAOCriteria.EQUALS, false);
		criteria.addWhereClause("m2", "mainColumn2", 20, DAOCriteria.EQUALS, false);
		criteria.addWhereClause("m3", "mainColumn3", 30, DAOCriteria.EQUALS, false);
		

		// ここからが、テスト対象処理
        sql.append("SELECT ");
        joinDAO2.addSelectClause(sql, "", null);

        sql.append(" FROM ");
        joinDAO2.addFromClause(sql, "", null);

		DAOUtils.addWhereClauses(joinDAO2, criteria, "", sql, params, false);

		
		// 実行結果の確認
		System.out.println("【joinAndStarDupTest2】");
		System.out.println(sql.toString());
		
		String chk = "SELECT" +
				" m1.main_pk_field AS m1_main_pk_field, m1.main_column1 AS m1_main_column1, m1.main_column2 AS m1_main_column2, m1.main_column3 AS m1_main_column3," +
				" m2.main_pk_field AS m2_main_pk_field, m2.main_column1 AS m2_main_column1, m2.main_column2 AS m2_main_column2, m2.main_column3 AS m2_main_column3," +
				" m3.main_pk_field AS m3_main_pk_field, m3.main_column1 AS m3_main_column1, m3.main_column2 AS m3_main_column2, m3.main_column3 AS m3_main_column3" +
				" FROM test_main_vo m1" +
				" INNER JOIN test_main_vo m2 ON m1.main_column1 = m2.main_column2" +
				" INNER JOIN test_main_vo m3 ON m1.main_column1 = m3.main_column2 AND m2.main_column1 = m3.main_column3" +
				" WHERE m1.main_column1 = ? AND m2.main_column2 = ? AND m3.main_column3 = ?";

		Assert.assertEquals("SQL 文が正しい事", chk, sql.toString());


		params.stream().forEach(obj -> System.out.println(obj));

		Assert.assertEquals("バインド変数が正しい事(1)", params.get(0), 10);
		Assert.assertEquals("バインド変数が正しい事(2)", params.get(1), 20);
		Assert.assertEquals("バインド変数が正しい事(3)", params.get(2), 30);
		Assert.assertEquals("バインド変数が正しい事(件数)", 3, params.size());

	}
	
	
	
	/**
	 * StarJoinDAO + DAO における JoinDAO 結合時の SQL 文出力テスト（フィールド重複なし）<br/>
	 * （表１ -- 表２　-- 表３）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>生成される SQL 文が正しい事。</li>
	 *     <li>バインド変数リストの格納値、件数が正しい事</li>
	 * </ul>
	 */
	@Test
	public void starJoinAndJoinNonDupTest1(){

		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();


		// テスト用1 StarJoinDAO　（main -- join_sub）
		StarJoinDAO joinDAO1 = new StarJoinDAO();
		joinDAO1.setMainDAO(this.mainDAO);
		joinDAO1.setMainAlias("m");

		// 結合条件（main -- join_sub）
		JoinCondition condition1 = new JoinCondition();
		condition1.setField1("mainColumn1");
		condition1.setField2("joinColumn2");

		// StarJoin の子表 DAO
		StarJoinDAODescriptor descriptor1 = new StarJoinDAODescriptor();
		descriptor1.setDao(this.joinSubDAO);
		descriptor1.setAlias("j");
		descriptor1.setCondition(condition1);

		List<StarJoinDAODescriptor> childDAOs = Arrays.asList(descriptor1);
		joinDAO1.setChildDAOs(childDAOs);


		// StarJoinDAO に対して、JoinDAO を使用する。　（join_sub -- sub）
		JoinDAO joinDAO2 = new JoinDAO();
		joinDAO2.setJoinable1(joinDAO1);
		joinDAO2.setJoinable2(this.subDAO);
		joinDAO2.setAlias2("s");


		// 結合条件（sub_join -- join）
		JoinCondition condition2 = new JoinCondition();
		condition2.setAlias1("j");
		condition2.setField1("joinColumn1");
		condition2.setField2("subColumn2");
		joinDAO2.setCondition(condition2);


		// 検索条件
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("m", "mainColumn2", 10, DAOCriteria.EQUALS, false);
		criteria.addWhereClause("j", "joinColumn2", 20, DAOCriteria.EQUALS, false);
		criteria.addWhereClause("s", "subColumn2", 30, DAOCriteria.EQUALS, false);


		// ここからが、テスト対象処理
        sql.append("SELECT ");
        joinDAO2.addSelectClause(sql, "", null);

        sql.append(" FROM ");
        joinDAO2.addFromClause(sql, "", null);

		DAOUtils.addWhereClauses(joinDAO2, criteria, "", sql, params, false);

		
		// 実行結果の確認
		System.out.println("【starJoinAndJoinNonDupTest1】");
		System.out.println(sql.toString());
		
		String chk = "SELECT" +
				" m.main_pk_field AS m_main_pk_field, m.main_column1 AS m_main_column1, m.main_column2 AS m_main_column2, m.main_column3 AS m_main_column3," +
				" j.join_pk_field AS j_join_pk_field, j.join_column1 AS j_join_column1, j.join_column2 AS j_join_column2, j.join_column3 AS j_join_column3," +
				" s.sub_pk_field AS s_sub_pk_field, s.sub_column1 AS s_sub_column1, s.sub_column2 AS s_sub_column2, s.sub_column3 AS s_sub_column3" +
				" FROM test_main_vo m" +
				" INNER JOIN test_join_sub_vo j ON m.main_column1 = j.join_column2" +
				" INNER JOIN test_sub_vo s ON j.join_column1 = s.sub_column2" +
				" WHERE m.main_column2 = ? AND j.join_column2 = ? AND s.sub_column2 = ?";

		Assert.assertEquals("SQL 文が正しい事", chk, sql.toString());


		params.stream().forEach(obj -> System.out.println(obj));

		Assert.assertEquals("バインド変数が正しい事(1)", params.get(0), 10);
		Assert.assertEquals("バインド変数が正しい事(2)", params.get(1), 20);
		Assert.assertEquals("バインド変数が正しい事(3)", params.get(2), 30);
		Assert.assertEquals("バインド変数が正しい事(件数)", 3, params.size());

	}


	
	/**
	 * StarJoinDAO + DAO における JoinDAO 結合時の SQL 文出力テスト（フィールド重複なし）<br/>
	 * （（表１ -- 表２）　-- 表３） ... 表３は、表１、表２の列に依存
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>生成される SQL 文が正しい事。</li>
	 *     <li>バインド変数リストの格納値、件数が正しい事</li>
	 * </ul>
	 */
	@Test
	public void starJoinAndJoinNonDupTest2(){

		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();


		// テスト用1 StarJoinDAO　（main -- join_sub）
		StarJoinDAO joinDAO1 = new StarJoinDAO();
		joinDAO1.setMainDAO(this.mainDAO);
		joinDAO1.setMainAlias("m");

		// 結合条件（main -- join_sub）
		JoinCondition condition1 = new JoinCondition();
		condition1.setField1("mainColumn1");
		condition1.setField2("joinColumn2");

		// StarJoin の子表 DAO
		StarJoinDAODescriptor descriptor1 = new StarJoinDAODescriptor();
		descriptor1.setDao(this.joinSubDAO);
		descriptor1.setAlias("j");
		descriptor1.setCondition(condition1);

		List<StarJoinDAODescriptor> childDAOs = Arrays.asList(descriptor1);
		joinDAO1.setChildDAOs(childDAOs);

		
		// StarJoinDAO に対して、JoinDAO を使用する。　（（main -- join_sub） -- sub）
		JoinDAO joinDAO2 = new JoinDAO();
		joinDAO2.setJoinable1(joinDAO1);
		joinDAO2.setJoinable2(this.subDAO);
		joinDAO2.setAlias2("s");

		// 結合条件（main -- join）
		JoinCondition condition2_1 = new JoinCondition();
		condition2_1.setAlias1("m");
		condition2_1.setField1("mainColumn1");
		condition2_1.setField2("subColumn3");

		// 結合条件（sub_join -- join）
		JoinCondition condition2_2 = new JoinCondition();
		condition2_2.setAlias1("j");
		condition2_2.setField1("joinColumn1");
		condition2_2.setField2("subColumn3");

		joinDAO2.setConditions(new OnClause[]{condition2_1, condition2_2});

		
		
		// 検索条件
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("m", "mainColumn2", 10, DAOCriteria.EQUALS, false);
		criteria.addWhereClause("j", "joinColumn2", 20, DAOCriteria.EQUALS, false);
		criteria.addWhereClause("s", "subColumn2", 30, DAOCriteria.EQUALS, false);
		

		// ここからが、テスト対象処理
        sql.append("SELECT ");
        joinDAO2.addSelectClause(sql, "", null);

        sql.append(" FROM ");
        joinDAO2.addFromClause(sql, "", null);

		DAOUtils.addWhereClauses(joinDAO2, criteria, "", sql, params, false);

		
		// 実行結果の確認
		System.out.println("【starJoinAndJoinNonDupTest2】");
		System.out.println(sql.toString());
		
		String chk = "SELECT" +
				" m.main_pk_field AS m_main_pk_field, m.main_column1 AS m_main_column1, m.main_column2 AS m_main_column2, m.main_column3 AS m_main_column3," +
				" j.join_pk_field AS j_join_pk_field, j.join_column1 AS j_join_column1, j.join_column2 AS j_join_column2, j.join_column3 AS j_join_column3," +
				" s.sub_pk_field AS s_sub_pk_field, s.sub_column1 AS s_sub_column1, s.sub_column2 AS s_sub_column2, s.sub_column3 AS s_sub_column3" +
				" FROM test_main_vo m" +
				" INNER JOIN test_join_sub_vo j ON m.main_column1 = j.join_column2" +
				" INNER JOIN test_sub_vo s ON m.main_column1 = s.sub_column3 AND j.join_column1 = s.sub_column3" +
				" WHERE m.main_column2 = ? AND j.join_column2 = ? AND s.sub_column2 = ?";

		Assert.assertEquals("SQL 文が正しい事", chk, sql.toString());


		params.stream().forEach(obj -> System.out.println(obj));

		Assert.assertEquals("バインド変数が正しい事(1)", params.get(0), 10);
		Assert.assertEquals("バインド変数が正しい事(2)", params.get(1), 20);
		Assert.assertEquals("バインド変数が正しい事(3)", params.get(2), 30);
		Assert.assertEquals("バインド変数が正しい事(件数)", 3, params.size());

	}

}
