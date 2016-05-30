package jp.co.transcosmos.dm3.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.BasicConfigurator;

/**
 * Simple tests on the external interface of a joinable DAO
 */
public class JoinableTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
        BasicConfigurator.configure();
    }

    public static void testSQL() throws Exception {
        
        ReflectingDAO<TestVO> one = new ReflectingDAO<TestVO>();
        one.setValueObjectClassName(TestVO.class.getName());
        
        ReflectingDAO<TestVOJoined> two = new ReflectingDAO<TestVOJoined>();
        two.setValueObjectClassName(TestVOJoined.class.getName());
        
        StarJoinDAO join = new StarJoinDAO();
        join.setMainAlias("one");
        join.setMainDAO(one);
        StarJoinDAODescriptor jd = new StarJoinDAODescriptor();
        jd.setAlias("two");
        jd.setDao(two);
        JoinCondition cond = new JoinCondition();
        cond.setField1("one");
        cond.setField2("one");
        jd.setCondition(cond);
        join.setChildDAOs(Arrays.asList(new StarJoinDAODescriptor[] {jd}));

        assertEquals("one.one", join.lookupAliasDotColumnName("one", null, null));
        assertEquals("one.two", join.lookupAliasDotColumnName("two", null, null));
        assertEquals("two.three", join.lookupAliasDotColumnName("three", null, null));
        
        StringBuilder sql = new StringBuilder();
        join.buildSubQuerySQL(sql, null, null, new String[] {"two", "three"}, 
                new String[] {"one", "two"}, null);
        assertEquals("SELECT one.two AS one_two, two.three AS two_three " +
        		"FROM test_vo one INNER JOIN test_vo_joined two ON one.one = two.one", 
        		sql.toString());
    }
    
    public static void testNesting() throws Exception {
        // Nest a star join inside a star join, then query for field names        
        ReflectingDAO<TestVO> one = new ReflectingDAO<TestVO>();
        one.setValueObjectClassName(TestVO.class.getName());
        
        ReflectingDAO<TestVOJoined> two = new ReflectingDAO<TestVOJoined>();
        two.setValueObjectClassName(TestVOJoined.class.getName());

        // inside star join
        StarJoinDAO insideJoin = new StarJoinDAO();
        insideJoin.setMainAlias("insideOne");
        insideJoin.setMainDAO(one);
        StarJoinDAODescriptor insideJD = new StarJoinDAODescriptor();
        insideJD.setAlias("insideTwo");
        insideJD.setDao(two);
        JoinCondition insideCond = new JoinCondition();
        insideCond.setField1("one");
        insideCond.setField2("one");
        insideJD.setCondition(insideCond);
        insideJoin.setChildDAOs(Arrays.asList(new StarJoinDAODescriptor[] {insideJD}));
        
        // outside star join
        StarJoinDAO outsideJoin = new StarJoinDAO();
        outsideJoin.setMainAlias("outsideNested");
        outsideJoin.setMainDAO(insideJoin);
        StarJoinDAODescriptor outsideJD1 = new StarJoinDAODescriptor();
        outsideJD1.setAlias("outsideOne");
        outsideJD1.setDao(two);
        JoinCondition outsideCond1 = new JoinCondition();
        outsideCond1.setField1("one");
        outsideCond1.setAlias1("insideOne");
        outsideCond1.setField2("one");
        outsideJD1.setCondition(outsideCond1);
        
        StarJoinDAODescriptor outsideJD2 = new StarJoinDAODescriptor();
        outsideJD2.setAlias("outsideTwo");
        outsideJD2.setDao(two);
        JoinCondition outsideCond2 = new JoinCondition();
        outsideCond2.setField1("one");
        outsideCond2.setAlias1("insideTwo");
        outsideCond2.setField2("one");
        outsideJD2.setCondition(outsideCond2);
        
        outsideJoin.setChildDAOs(Arrays.asList(new StarJoinDAODescriptor[] {outsideJD1, outsideJD2}));

        StringBuilder sql = new StringBuilder();
        outsideJoin.buildSubQuerySQL(sql, null, null, new String[] {"two", "three"}, 
                new String[] {"insideOne", "outsideTwo"}, null);
        assertEquals("SELECT insideOne.two AS insideOne_two, outsideTwo.three AS outsideTwo_three " +
        		"FROM test_vo insideOne " +
        		"INNER JOIN test_vo_joined insideTwo ON insideOne.one = insideTwo.one " +
        		"INNER JOIN test_vo_joined outsideOne ON insideOne.one = outsideOne.one " +
        		"INNER JOIN test_vo_joined outsideTwo ON insideTwo.one = outsideTwo.one", 
        		sql.toString());
    }
    

    public static void testSimple() throws Exception {
        
        ReflectingDAO<TestVO> one = new ReflectingDAO<TestVO>();
        one.setValueObjectClassName(TestVO.class.getName());

        assertEquals("one", one.lookupAliasDotColumnName("one", null, null));
        assertEquals("two", one.lookupAliasDotColumnName("two", null, null));
        
        StringBuilder sql = new StringBuilder();
        one.buildSubQuerySQL(sql, null, null, new String[] {"one", "two"}, 
                null, null);
        assertEquals("SELECT one, two FROM test_vo", 
        		sql.toString());
    }
    
    public static void testStarJoin() throws Exception {
        
        ReflectingDAO<TestVO> one = new ReflectingDAO<TestVO>();
        one.setValueObjectClassName(TestVO.class.getName());
        
        ReflectingDAO<TestVOJoined> two = new ReflectingDAO<TestVOJoined>();
        two.setValueObjectClassName(TestVOJoined.class.getName());

        // inside star join
        StarJoinDAO insideJoin = new StarJoinDAO();
        insideJoin.setMainAlias("insideOne");
        insideJoin.setMainDAO(one);
        StarJoinDAODescriptor insideJD = new StarJoinDAODescriptor();
        insideJD.setAlias("insideTwo");
        insideJD.setDao(two);
        JoinCondition insideCond = new JoinCondition();
        insideCond.setField1("one");
        insideCond.setField2("one");
        insideJD.setCondition(insideCond);
        insideJoin.setChildDAOs(Arrays.asList(new StarJoinDAODescriptor[] {insideJD}));
        
        // outside star join
        StarJoinDAO outsideJoin = new StarJoinDAO();
        outsideJoin.setMainAlias("outsideNested");
        outsideJoin.setMainDAO(insideJoin);
        StarJoinDAODescriptor outsideJD1 = new StarJoinDAODescriptor();
        outsideJD1.setAlias("outsideOne");
        outsideJD1.setDao(two);
        JoinCondition outsideCond1 = new JoinCondition();
        outsideCond1.setField1("one");
        outsideCond1.setAlias1("insideOne");
        outsideCond1.setField2("one");
        outsideJD1.setCondition(outsideCond1);
        
        StarJoinDAODescriptor outsideJD2 = new StarJoinDAODescriptor();
        outsideJD2.setAlias("outsideTwo");
        outsideJD2.setDao(two);
        JoinCondition outsideCond2 = new JoinCondition();
        outsideCond2.setField1("one");
        outsideCond2.setAlias1("insideTwo");
        outsideCond2.setField2("one");
        outsideJD2.setCondition(outsideCond2);
        
        outsideJoin.setChildDAOs(Arrays.asList(new StarJoinDAODescriptor[] {outsideJD1, outsideJD2}));

        DAOCriteria criteria = new DAOCriteria();
        criteria.addWhereClause("insideTwo", "three", 1, DAOCriteria.EQUALS, false);
        
    	assertEquals("SELECT insideOne.one AS insideOne_one, insideOne.two AS insideOne_two, " +
        					"insideTwo.one AS insideTwo_one, insideTwo.three AS insideTwo_three, " +
        					"outsideOne.one AS outsideOne_one, outsideOne.three AS outsideOne_three, " +
        					"outsideTwo.one AS outsideTwo_one, outsideTwo.three AS outsideTwo_three " +
        		"FROM test_vo insideOne " +
        		"INNER JOIN test_vo_joined insideTwo ON insideOne.one = insideTwo.one " +
        		"INNER JOIN test_vo_joined outsideOne ON insideOne.one = outsideOne.one " +
        		"INNER JOIN test_vo_joined outsideTwo ON insideTwo.one = outsideTwo.one " +
        		"WHERE insideTwo.three = ?", 
        		buildSqlSelect(outsideJoin, "", criteria));
    }
    
    private static String buildSqlSelect(SQLClauseBuilder dao, String alias, DAOCriteria criteria) {
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        
        sql.append("SELECT ");
        dao.addSelectClause(sql, alias, null);
        sql.append(" FROM ");
        dao.addFromClause(sql, alias, params);
        
        // Add restrictions as sql
        DAOUtils.addWhereClauses(dao, criteria, alias, sql, params, false);
        DAOUtils.addOrderByClauses(dao, criteria, alias, sql, false);
        return sql.toString();
    }
    
    @SuppressWarnings("unchecked")
    public static void testSimpleUnion() throws Exception {

        ReflectingDAO<TestVO> main = new ReflectingDAO<TestVO>();
        main.setValueObjectClassName(TestVO.class.getName());
        
        DAOCriteria critOne = new DAOCriteria();
        critOne.addWhereClause("two", 2);
        
        FilterDAO<TestVO> filterOne = new FilterDAO<TestVO>();
        filterOne.setFilteredDAO(main);
        filterOne.setFilterCriteria(critOne);
        
        DAOCriteria critTwo = new DAOCriteria();
        critTwo.addWhereClause("two", 4);
        
        FilterDAO<TestVO> filterTwo = new FilterDAO<TestVO>();
        filterTwo.setFilteredDAO(main);
        filterTwo.setFilterCriteria(critTwo);
        
    	assertEquals("SELECT one, two FROM test_vo WHERE two = ?", 
        		buildSqlSelect(filterOne, "", null));
        
    	assertEquals("SELECT one, two FROM test_vo WHERE two = ?", 
        		buildSqlSelect(filterTwo, "", null));
        
    	// Union the two filtered daos
    	UnionDAO<TestVO> unionDAO = new UnionDAO<TestVO>();
    	unionDAO.setChildElements(new UnionDAOElementDescriptor[] {
    			new UnionDAOElementDescriptor<TestVO>(filterOne, null),
    			new UnionDAOElementDescriptor<TestVO>(filterTwo, null)
    	});
    	unionDAO.setUnionAll(true);

    	assertEquals("SELECT abc.one AS abc_one, abc.two AS abc_two FROM " +
		    			"(SELECT one, two FROM test_vo WHERE two = ? " + 
		    			 "UNION ALL " +
		    			 "SELECT one, two FROM test_vo WHERE two = ?) abc", 
        		buildSqlSelect(unionDAO, "abc", null));

        DAOCriteria criteria = new DAOCriteria();
        criteria.addWhereClause("abc", "one", 1, DAOCriteria.EQUALS, false);

    	assertEquals("SELECT abc.one AS abc_one, abc.two AS abc_two FROM " +
		    			"(SELECT one, two FROM test_vo WHERE two = ? " + 
		    			 "UNION ALL " +
		    			 "SELECT one, two FROM test_vo WHERE two = ?) abc " + 
		    		 "WHERE abc.one = ?", 
        		buildSqlSelect(unionDAO, "abc", criteria));

        criteria.addOrderByClause("abc", "one", false);

    	assertEquals("SELECT abc.one AS abc_one, abc.two AS abc_two FROM " +
		    			"(SELECT one, two FROM test_vo WHERE two = ? " + 
		    			 "UNION ALL " +
		    			 "SELECT one, two FROM test_vo WHERE two = ?) abc " + 
		    		 "WHERE abc.one = ? ORDER BY abc.one DESC", 
        		buildSqlSelect(unionDAO, "abc", criteria));
    }
    
    public static void testJoinOnClause() throws Exception {
        ReflectingDAO<TestVO> one = new ReflectingDAO<TestVO>();
        one.setValueObjectClassName(TestVO.class.getName());
        
        ReflectingDAO<TestVOJoined> two = new ReflectingDAO<TestVOJoined>();
        two.setValueObjectClassName(TestVOJoined.class.getName());
        
        StringBuilder sql = new StringBuilder();
        
    	// Test that the FROM clause (specifically the ON) is rendered correctly

        // Simple two table
        JoinDAO join1 = new JoinDAO();
        join1.setAlias1("one");
        join1.setJoinable1(one);
        join1.setAlias2("two");
        join1.setJoinable2(two);
        JoinCondition c1 = new JoinCondition();
        c1.setAlias1("one");
        c1.setField1("one");
        c1.setAlias2("two");
        c1.setField2("one");
        join1.setCondition(c1);

        join1.addFromClause(sql, "", null);
    	assertEquals("test_vo one INNER JOIN test_vo_joined two " +
    			"ON one.one = two.one", sql.toString());

        // three table
        JoinDAO join2 = new JoinDAO();
        join2.setAlias1("three");
        join2.setJoinable1(one);
        join2.setJoinable2(join1);
        JoinCondition c2 = new JoinCondition();
        c2.setAlias1("three");
        c2.setField1("one");
        c2.setAlias2("two");
        c2.setField2("one");
        join2.setCondition(c2);
        
        sql.setLength(0);
        join2.addFromClause(sql, "", null);
    	assertEquals("test_vo three INNER JOIN test_vo one INNER JOIN test_vo_joined two " +
    			"ON one.one = two.one ON three.one = two.one", sql.toString());

        // two table join with hard filter on each table
        JoinDAO join3 = new JoinDAO();
        join3.setAlias1("one");
        join3.setJoinable1(one);
        join3.setAlias2("two");
        join3.setJoinable2(two);
        JoinCondition c3 = new JoinCondition();
        c3.setAlias1("one");
        c3.setField1("one");
        c3.setAlias2("two");
        c3.setField2("one");
        DAOCriteria c4 = new DAOCriteria();
        c4.addWhereClause("one", "two", "abc", DAOCriteria.EQUALS, false);
        c4.addWhereClause("one", "two", "def", DAOCriteria.EQUALS, false);
        join3.setConditions(new OnClause[] {c3, c4});
        
        sql.setLength(0);
        join3.addFromClause(sql, "", null);
        
        // c4 には、one.two に対する２個の検索条件が設定されているが、テストコード上では１個しか記述が
        // 無かったのでエラーが発生していた。　期待値の誤りなので、判定ロジックを修正した。
    	assertEquals("test_vo one INNER JOIN test_vo_joined two ON one.one = two.one " +
    			"AND one.two = ? AND one.two = ?", sql.toString());
    }
    
    public class TestVO {
        private int one;
        private String two;
        
        public int getOne() {
            return this.one;
        }
        public String getTwo() {
            return this.two;
        }
        public void setOne(int one) {
            this.one = one;
        }
        public void setTwo(String two) {
            this.two = two;
        }
    }
    
    public class TestVOJoined {
        private int one;
        private String three;
        
        public int getOne() {
            return this.one;
        }
        public String getThree() {
            return this.three;
        }
        public void setOne(int one) {
            this.one = one;
        }
        public void setThree(String three) {
            this.three = three;
        }
    }
}