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
 * GroupByDAOU のテストケース<br/>
 */
public class GroupByDAOTest {

	/**
	 * 委譲先が ReflectionDAO の場合における、委譲先の処理による列別名出力テスト<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>別名指定無しで３０文字を超えている場合、切り詰められている事</li>
	 *     <li>別名指定有りで３０文字を超えている場合、切り詰められている事</li>
	 *     <li>別名指定無しで３０文字を超えて重複している場合、ナンバリングされている事</li>
	 *     <li>別名指定ありで３０文字を超えて重複している場合、ナンバリングされている事</li>
	 *     <li>別名指定無しで３０文字を超えていない場合、テーブル別名無しで別名が出力される事</li>
	 *     <li>別名指定ありで３０文字を超えていない場合、テーブル別名有りで別名が出力される事</li>
	 * </ul>
	 */
	@Test
	public void getFieldAliasesByRefDAOTest() throws Exception{

		// 委譲先の ReflectingDAO を作成
		ReflectingDAO<TestLongNameVo> dao = new ReflectingDAO<>();
		dao.setValueObjectClassName("jp.co.transcosmos.mock.vo.TestLongNameVo");

		// テスト対象 GroupByDAO を作成
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

		// リフレクションで、テストメソッドを実行
		String[] aliases = executeGetFieldAliases(groupDao, thisAlias, forFieldNames, thisFunctionAlias);

		Assert.assertEquals("戻り値の配列数が正しい事", aliases.length, forFieldNames.length);
		Assert.assertEquals("別名指定無しで３０文字を超えている場合、切り詰められている事", aliases[0], "xxxxxxxxxx_xxxxxxxxxx_xxxxxxxx");
		Assert.assertEquals("別名指定有りで３０文字を超えている場合、切り詰められている事", aliases[1], "a1_xxxxxxxxxx_xxxxxxxxxx_xxxxx");
		Assert.assertEquals("別名指定無しで３０文字を超えて重複している場合、ナンバリングされている事", aliases[2], "xxxxxxxxxx_xxxxxxxxxx_xxxxx000");
		Assert.assertEquals("別名指定有りで３０文字を超えて重複している場合、ナンバリングされている事", aliases[3], "a1_xxxxxxxxxx_xxxxxxxxxx_xx000");
		Assert.assertEquals("別名が異なるので重複しない場合、正しく出力されている事", aliases[4], "a2_xxxxxxxxxx_xxxxxxxxxx_xxxxx");
		Assert.assertEquals("別名指定有りで３０文字を超えていない場合、正しく出力されている事", aliases[5], "a1_col1_name");
		Assert.assertEquals("別名指定無しで３０文字を超えていない場合、正しく出力されている事", aliases[6], "col2_name");

	}

	
	
	/**
	 * 委譲先が JoinDAO の場合における、委譲先の処理による列別名出力テスト<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>別名指定無しで３０文字を超えている場合、切り詰められている事</li>
	 *     <li>別名指定有りで３０文字を超えている場合、切り詰められている事</li>
	 *     <li>別名指定無しで３０文字を超えて重複している場合、ナンバリングされている事</li>
	 *     <li>別名指定ありで３０文字を超えて重複している場合、ナンバリングされている事</li>
	 *     <li>別名指定無しで３０文字を超えていない場合、テーブル別名無しで別名が出力される事</li>
	 *     <li>別名指定ありで３０文字を超えていない場合、テーブル別名有りで別名が出力される事</li>
	 *     <li>JoinDAO 側で設定したテーブル別名が使用される事</li>
	 *     <li>テーブル別名を指定した場合、JoinDAO 側で設定したテーブル別名よりも優先される事</li>
	 *     <li>同名の列名が複数の DAO に存在する場合、最初に見つかった DAO のテーブル別名が使用される事。</li>
	 * </ul>
	 */
	@Test
	public void getFieldAliasesByJoinDAOTest() throws Exception{

		// 委譲先の JonDAO を作成
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
		
		
		// テスト対象 GroupByDAO を作成
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

		// リフレクションで、テストメソッドを実行
		String[] aliases = executeGetFieldAliases(groupDao, thisAlias, forFieldNames, thisFunctionAlias);

		Assert.assertEquals("戻り値の配列数が正しい事", aliases.length, forFieldNames.length);
		Assert.assertEquals("別名指定無しで３０文字を超えている場合、デフォルトの別名が使用されて切り詰められている事", aliases[0], "dao1_xxxxxxxxxx_xxxxxxxxxx_xxx");
		Assert.assertEquals("別名指定有りで３０文字を超えている場合、指定した別名が使用されて切り詰められている事", aliases[1], "dao1_xxxxxxxxxx_xxxxxxxxxx_000");
		Assert.assertEquals("別名指定無しで３０文字を超えて重複している場合、ナンバリングされている事", aliases[2], "dao1_xxxxxxxxxx_xxxxxxxxxx_001");
		Assert.assertEquals("別名指定有りで３０文字を超えて重複している場合、ナンバリングされている事", aliases[3], "dao1_xxxxxxxxxx_xxxxxxxxxx_002");
		Assert.assertEquals("別名指定有りで３０文字を超えて重複しない場合、指定した別名が使用されて切り詰められている事", aliases[4], "dao2_xxxxxxxxxx_xxxxxxxxxx_xxx");
		Assert.assertEquals("別名指定有りで３０文字を超えていない場合、正しく出力されている事", aliases[5], "dao1_col1_name");
		Assert.assertEquals("別名指定無しで３０文字を超えていない場合、正しく出力されている事", aliases[6], "dao1_col2_name");

		Assert.assertEquals("別名指定無しで３０文字を超えている場合、デフォルトの別名が使用されて切り詰められている事", aliases[7], "dao2_sub_xxxxxxxxxx_xxxxxxxxxx");
		Assert.assertEquals("別名指定有りで３０文字を超えている場合、指定した別名が使用されて切り詰められている事", aliases[8], "dao2_sub_xxxxxxxxxx_xxxxxxx000");
		Assert.assertEquals("別名指定無しで３０文字を超えて重複している場合、ナンバリングされている事", aliases[9], "dao2_sub_xxxxxxxxxx_xxxxxxx001");
		Assert.assertEquals("別名指定有りで３０文字を超えて重複している場合、ナンバリングされている事", aliases[10], "dao2_xxxxxxxxxx_xxxxxxxxxx_000");
		Assert.assertEquals("別名指なしで、同名のフィールドが複数テーブルに存在する場合、最初に見つかった DAO のテーブル別名が使用される事", aliases[11], "dao1_xxxxxxxxxx_xxxxxxxxxx_003");
		Assert.assertEquals("別名指定有りで３０文字を超えていない場合、正しく出力されている事", aliases[12], "dao2_sub_col1_name");
		Assert.assertEquals("別名指定無しで３０文字を超えていない場合、正しく出力されている事", aliases[13], "dao2_sub_col2_name");

	}



	/**
	 * 委譲先が StarJoinDAO の場合における、委譲先の処理による列別名出力テスト<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>別名指定無しで３０文字を超えている場合、切り詰められている事</li>
	 *     <li>別名指定有りで３０文字を超えている場合、切り詰められている事</li>
	 *     <li>別名指定無しで３０文字を超えて重複している場合、ナンバリングされている事</li>
	 *     <li>別名指定ありで３０文字を超えて重複している場合、ナンバリングされている事</li>
	 *     <li>別名指定無しで３０文字を超えていない場合、テーブル別名無しで別名が出力される事</li>
	 *     <li>別名指定ありで３０文字を超えていない場合、テーブル別名有りで別名が出力される事</li>
	 *     <li>JoinDAO 側で設定したテーブル別名が使用される事</li>
	 *     <li>テーブル別名を指定した場合、JoinDAO 側で設定したテーブル別名よりも優先される事</li>
	 *     <li>同名の列名が複数の DAO に存在する場合、最初に見つかった DAO のテーブル別名が使用される事。</li>
	 * </ul>
	 */
	@Test
	public void getFieldAliasesByStarJoinDAOTest() throws Exception{

		// 委譲先の JonDAO を作成
		ReflectingDAO<TestLongNameVo> mainDao = new ReflectingDAO<>();
		mainDao.setValueObjectClassName("jp.co.transcosmos.mock.vo.TestLongNameVo");

		ReflectingDAO<TestLongNameVo> subDao = new ReflectingDAO<>();
		subDao.setValueObjectClassName("jp.co.transcosmos.mock.vo.TestLongNameSubVo");
		
		// start join DAO
		StarJoinDAO joinDao = new StarJoinDAO();
		joinDao.setMainDAO(mainDao);
		joinDao.setMainAlias("dao1");

		// 結合条件
		JoinCondition condition = new JoinCondition();
		condition.setField1("col1Name");
		condition.setField2("subCol1Name");

		// StarJoin の子表 DAO1
		StarJoinDAODescriptor descriptor = new StarJoinDAODescriptor();
		descriptor.setDao(subDao);
		descriptor.setAlias("dao2");
		descriptor.setCondition(condition);

		List<StarJoinDAODescriptor> childDAOs = Arrays.asList(descriptor);
		joinDao.setChildDAOs(childDAOs);


		
		// テスト対象 GroupByDAO を作成
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

		// リフレクションで、テストメソッドを実行
		String[] aliases = executeGetFieldAliases(groupDao, thisAlias, forFieldNames, thisFunctionAlias);

		Assert.assertEquals("戻り値の配列数が正しい事", aliases.length, forFieldNames.length);
		Assert.assertEquals("別名指定無しで３０文字を超えている場合、デフォルトの別名が使用されて切り詰められている事", aliases[0], "dao1_xxxxxxxxxx_xxxxxxxxxx_xxx");
		Assert.assertEquals("別名指定有りで３０文字を超えている場合、指定した別名が使用されて切り詰められている事", aliases[1], "dao1_xxxxxxxxxx_xxxxxxxxxx_000");
		Assert.assertEquals("別名指定無しで３０文字を超えて重複している場合、ナンバリングされている事", aliases[2], "dao1_xxxxxxxxxx_xxxxxxxxxx_001");
		Assert.assertEquals("別名指定有りで３０文字を超えて重複している場合、ナンバリングされている事", aliases[3], "dao1_xxxxxxxxxx_xxxxxxxxxx_002");
		Assert.assertEquals("別名指定有りで３０文字を超えて重複しない場合、指定した別名が使用されて切り詰められている事", aliases[4], "dao2_xxxxxxxxxx_xxxxxxxxxx_xxx");
		Assert.assertEquals("別名指定有りで３０文字を超えていない場合、正しく出力されている事", aliases[5], "dao1_col1_name");
		Assert.assertEquals("別名指定無しで３０文字を超えていない場合、正しく出力されている事", aliases[6], "dao1_col2_name");

		Assert.assertEquals("別名指定無しで３０文字を超えている場合、デフォルトの別名が使用されて切り詰められている事", aliases[7], "dao2_sub_xxxxxxxxxx_xxxxxxxxxx");
		Assert.assertEquals("別名指定有りで３０文字を超えている場合、指定した別名が使用されて切り詰められている事", aliases[8], "dao2_sub_xxxxxxxxxx_xxxxxxx000");
		Assert.assertEquals("別名指定無しで３０文字を超えて重複している場合、ナンバリングされている事", aliases[9], "dao2_sub_xxxxxxxxxx_xxxxxxx001");
		Assert.assertEquals("別名指定有りで３０文字を超えて重複している場合、ナンバリングされている事", aliases[10], "dao2_xxxxxxxxxx_xxxxxxxxxx_000");
		Assert.assertEquals("別名指なしで、同名のフィールドが複数テーブルに存在する場合、最初に見つかった DAO のテーブル別名が使用される事", aliases[11], "dao1_xxxxxxxxxx_xxxxxxxxxx_003");
		Assert.assertEquals("別名指定有りで３０文字を超えていない場合、正しく出力されている事", aliases[12], "dao2_sub_col1_name");
		Assert.assertEquals("別名指定無しで３０文字を超えていない場合、正しく出力されている事", aliases[13], "dao2_sub_col2_name");

	}
	
	
	
	/**
	 * GroupByDAO の VO Funtion アノテーションが指定された場合のテスト<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>テーブル別名指定無し、関数別名無しの場合、フィールド名から別名が生成される事</li>
	 *     <li>テーブル別名指定無し、関数別名有り場合、カラム別名が使用される事</li>
	 *     <li>テーブル別名指定有り、関数別名無し場合、フィールド名から別名が生成される事</li>
	 *     <li>テーブル別名指定有り、関数別名有り場合、カラム別名が使用される事</li>
	 * </ul>
	 */
	@Test
	public void getFieldAliasesByColAliasTest() throws Exception{

		// 委譲先の ReflectingDAO を作成
		ReflectingDAO<TestLongNameVo> dao = new ReflectingDAO<>();
		dao.setValueObjectClassName("jp.co.transcosmos.mock.vo.TestLongNameVo");

		// テスト対象 GroupByDAO を作成
		GroupByDAO<?> groupDao = new GroupByDAO<>();
		groupDao.setTargetDAO(dao);
		groupDao.setValueObjectClassName("jp.co.transcosmos.mock.vo.GoupByRefTestVo");

		String[] thisAlias = new String[]{null, "a1", null, "a1"};
		String[] forFieldNames = new String[]{"xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA",
											  "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxB",
											  "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxC",
											  "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD"};
		String[] thisFunctionAlias = new String[]{null, "alias1", "alias2", null};

		// リフレクションで、テストメソッドを実行
		String[] aliases = executeGetFieldAliases(groupDao, thisAlias, forFieldNames, thisFunctionAlias);

		Assert.assertEquals("戻り値の配列数が正しい事", aliases.length, forFieldNames.length);
		Assert.assertEquals("テーブル別名指定無し、関数別名無しの場合、フィールド名から別名が生成される事", aliases[0], "xxxxxxxxxx_xxxxxxxxxx_xxxxxxxx");
		Assert.assertEquals("テーブル別名指定有り、関数別名有り場合、関数別名が使用される事", aliases[1], "alias1");
		Assert.assertEquals("テーブル別名指定無し、関数別名有り場合、関数別名が使用される事", aliases[2], "alias2");
		Assert.assertEquals("テーブル別名指定有り、関数別名無し場合、フィールド名から別名が生成される事", aliases[3], "a1_xxxxxxxxxx_xxxxxxxxxx_xxxxx");

	}
	
	
	
	/**
	 * GroupByDAO の メタデータ収集処理のテスト（委譲先が ReflectionDAO の場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>Group By 用のバリーオブジェクトのフィールド名が、fieldNames　プロパティに格納されている事</li>
	 *     <li>関数指定が無い場合、DB 列名が、columnNames　プロパティに格納されている事</li>
	 *     <li>関数指定がある場合、関数文字列が、columnNames　プロパティに格納されている事</li>
	 *     <li>Function アノテーションが付与されている場合、指定された別名が functionAlias プロパティに格納されている事</li>
	 *     <li>Function アノテーションが付与されていない場合、functionAlias プロパティに null が格納されている事</li>
	 * </ul>
	 */
	@Test
	public void ensureInitializationByRefDAOTest() throws Exception {
		
		// テスト対象 GroupByDAO を作成
		GroupByDAO<?> groupDao = buildRefGroupByDAO();
		

		// リフレクションで、テストメソッドを実行
		executeEnsureInitialization(groupDao);

		// リフレクションで、実行結果を取得する。
		Map<String, String[]> props = getGroupByDAOProps(groupDao);

		System.out.println("【ReflectionDAO】");
		debugPropPrint(props, "fieldNames");
		debugPropPrint(props, "columnNames");
		debugPropPrint(props, "daoAliases");
		debugPropPrint(props, "functionAlias");

		Assert.assertEquals("フィールド名が正しく取得できている事（配列件数）", 5, props.get("fieldNames").length);
		Assert.assertEquals("DB 列名が正しく取得できている事（配列件数）", 5, props.get("columnNames").length);
		Assert.assertEquals("列のテーブル別名が正しく取得できている事（配列件数）", 5, props.get("daoAliases").length);
		Assert.assertEquals("関数別名が正しく取得できている事（配列件数）", 5, props.get("functionAlias").length);

		int idx = findTargetIndex(props.get("fieldNames"), "col1Name");
		Assert.assertEquals("フィールド名が正しく取得できている事（１フィールド目）", "col1Name", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB 列名が正しく取得できている事（１フィールド目）", "col1_name", props.get("columnNames")[idx]);
		Assert.assertNull("列のテーブル別名が正しく取得できている事（１フィールド目）", props.get("daoAliases")[idx]);
		Assert.assertNull("関数文字列が正しく取得できている事（１フィールド目）", props.get("functionAlias")[idx]);
		
		idx = findTargetIndex(props.get("fieldNames"), "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA");
		Assert.assertEquals("フィールド名が正しく取得できている事（２フィールド目）", "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB 列名が正しく取得できている事（２フィールド目）", "xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_A", props.get("columnNames")[idx]);
		Assert.assertNull("列のテーブル別名が正しく取得できている事（２フィールド目）", props.get("daoAliases")[idx]);
		Assert.assertNull("関数文字列が正しく取得できている事（２フィールド目）", props.get("functionAlias")[idx]);
		
		idx = findTargetIndex(props.get("fieldNames"), "cnt");
		Assert.assertEquals("フィールド名が正しく取得できている事（３フィールド目）", "cnt", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB 列名が正しく取得できている事（３フィールド目）", "max(col1_name)", props.get("columnNames")[idx]);
		Assert.assertNull("列のテーブル別名が正しく取得できている事（３フィールド目）", props.get("daoAliases")[idx]);
		Assert.assertEquals("関数文字列が正しく取得できている事（３フィールド目）", "cnt", props.get("functionAlias")[idx]);
		
		idx = findTargetIndex(props.get("fieldNames"), "cnt2");
		Assert.assertEquals("フィールド名が正しく取得できている事（４フィールド目）", "cnt2", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB 列名が正しく取得できている事（４フィールド目）", "min(col2_name)", props.get("columnNames")[idx]);
		Assert.assertNull("列のテーブル別名が正しく取得できている事（４フィールド目）", props.get("daoAliases")[idx]);
		Assert.assertEquals("関数文字列が正しく取得できている事（４フィールド目）", "cnt2", props.get("functionAlias")[idx]);

		idx = findTargetIndex(props.get("fieldNames"), "cnt3");
		Assert.assertEquals("フィールド名が正しく取得できている事（５フィールド目）", "cnt3", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB 列名が正しく取得できている事（５フィールド目）", "count(*)", props.get("columnNames")[idx]);
		Assert.assertNull("列のテーブル別名が正しく取得できている事（５フィールド目）", props.get("daoAliases")[idx]);
		Assert.assertEquals("関数文字列が正しく取得できている事（５フィールド目）", "cnt3", props.get("functionAlias")[idx]);

	}


	
	/**
	 * GroupByDAO の メタデータ収集処理のテスト（委譲先が JoinDAO の場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>Group By 用のバリーオブジェクトのフィールド名が、fieldNames　プロパティに格納されている事</li>
	 *     <li>関数指定が無い場合、DB 列名が、columnNames　プロパティに格納されている事</li>
	 *     <li>関数指定がある場合、関数文字列が、columnNames　プロパティに格納されている事</li>
	 *     <li>DaoAlias アノテーションが付与されている場合、指定された値が daoAliases プロパティに格納されている事</li>
	 *     <li>DaoAlias アノテーションが付与されていない場合、daoAliases プロパティに null が格納されている事</li>
	 *     <li>Function アノテーションが付与されている場合、指定された別名が functionAlias プロパティに格納されている事</li>
	 *     <li>Function アノテーションが付与されていない場合、functionAlias プロパティに null が格納されている事</li>
	 * </ul>
	 */
	@Test
	public void ensureInitializationByJoinDAOTest() throws Exception{

		// テスト対象 DAO 作成
		GroupByDAO<GoupByJoinTestVo> groupDao = buildJoinGroupByDAO();


		// リフレクションで、テストメソッドを実行
		executeEnsureInitialization(groupDao);

		
		// リフレクションで、実行結果を取得する。
		Map<String, String[]> props = getGroupByDAOProps(groupDao);

		System.out.println("【JoinDAO】");
		debugPropPrint(props, "fieldNames");
		debugPropPrint(props, "columnNames");
		debugPropPrint(props, "daoAliases");
		debugPropPrint(props, "functionAlias");
		
		Assert.assertEquals("フィールド名が正しく取得できている事（配列件数）", 7, props.get("fieldNames").length);
		Assert.assertEquals("DB 列名が正しく取得できている事（配列件数）", 7, props.get("columnNames").length);
		Assert.assertEquals("列のテーブル別名が正しく取得できている事（配列件数）", 7, props.get("daoAliases").length);
		Assert.assertEquals("関数別名が正しく取得できている事（配列件数）", 7, props.get("functionAlias").length);

		int idx = findTargetIndex(props.get("fieldNames"), "col1Name");
		Assert.assertEquals("フィールド名が正しく取得できている事（１フィールド目）", "col1Name", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB 列名が正しく取得できている事（１フィールド目）", "col1_name", props.get("columnNames")[idx]);
		Assert.assertNull("列のテーブル別名が正しく取得できている事（１フィールド目）", props.get("daoAliases")[idx]);
		Assert.assertNull("関数文字列が正しく取得できている事（１フィールド目）", props.get("functionAlias")[idx]);

		idx = findTargetIndex(props.get("fieldNames"), "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA");
		Assert.assertEquals("フィールド名が正しく取得できている事（２フィールド目）", "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB 列名が正しく取得できている事（２フィールド目）", "xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_A", props.get("columnNames")[idx]);
		Assert.assertNull("列のテーブル別名が正しく取得できている事（２フィールド目）", props.get("daoAliases")[idx]);
		Assert.assertNull("関数文字列が正しく取得できている事（２フィールド目）", props.get("functionAlias")[idx]);

		idx = findTargetIndex(props.get("fieldNames"), "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD");
		Assert.assertEquals("フィールド名が正しく取得できている事（３フィールド目）", "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB 列名が正しく取得できている事（３フィールド目）", "xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_D", props.get("columnNames")[idx]);
		Assert.assertEquals("列のテーブル別名が正しく取得できている事（３フィールド目）", "dao2", props.get("daoAliases")[idx]);
		Assert.assertNull("関数文字列が正しく取得できている事（３フィールド目）", props.get("functionAlias")[idx]);

		idx = findTargetIndex(props.get("fieldNames"), "cnt");
		Assert.assertEquals("フィールド名が正しく取得できている事（４フィールド目）", "cnt", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB 列名が正しく取得できている事（４フィールド目）", "max(dao1.col1_name)", props.get("columnNames")[idx]);
		Assert.assertNull("列のテーブル別名が正しく取得できている事（４フィールド目）", props.get("daoAliases")[idx]);
		Assert.assertEquals("関数文字列が正しく取得できている事（４フィールド目）", "cnt", props.get("functionAlias")[idx]);

		idx = findTargetIndex(props.get("fieldNames"), "cnt2");
		Assert.assertEquals("フィールド名が正しく取得できている事（５フィールド目）", "cnt2", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB 列名が正しく取得できている事（５フィールド目）", "min(dao1.col2_name)", props.get("columnNames")[idx]);
		Assert.assertNull("列のテーブル別名が正しく取得できている事（５フィールド目）", props.get("daoAliases")[idx]);
		Assert.assertEquals("関数文字列が正しく取得できている事（５フィールド目）", "cnt2", props.get("functionAlias")[idx]);

		idx = findTargetIndex(props.get("fieldNames"), "mx");
		Assert.assertEquals("フィールド名が正しく取得できている事（５フィールド目）", "mx", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB 列名が正しく取得できている事（５フィールド目）", "sum(dao2.xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_D)", props.get("columnNames")[idx]);
		Assert.assertEquals("列のテーブル別名が正しく取得できている事（５フィールド目）", "dao2", props.get("daoAliases")[idx]);
		Assert.assertEquals("関数文字列が正しく取得できている事（５フィールド目）", "mx", props.get("functionAlias")[idx]);

		idx = findTargetIndex(props.get("fieldNames"), "cnt3");
		Assert.assertEquals("フィールド名が正しく取得できている事（６フィールド目）", "cnt3", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB 列名が正しく取得できている事（６フィールド目）", "count(*)", props.get("columnNames")[idx]);
		Assert.assertNull("列のテーブル別名が正しく取得できている事（６フィールド目）", props.get("daoAliases")[idx]);
		Assert.assertEquals("関数文字列が正しく取得できている事（６フィールド目）", "cnt3", props.get("functionAlias")[idx]);

	}


	
	/**
	 * GroupByDAO の メタデータ収集処理のテスト（委譲先が StarJoinDAO の場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>Group By 用のバリーオブジェクトのフィールド名が、fieldNames　プロパティに格納されている事</li>
	 *     <li>関数指定が無い場合、DB 列名が、columnNames　プロパティに格納されている事</li>
	 *     <li>関数指定がある場合、関数文字列が、columnNames　プロパティに格納されている事</li>
	 *     <li>DaoAlias アノテーションが付与されている場合、指定された値が daoAliases プロパティに格納されている事</li>
	 *     <li>DaoAlias アノテーションが付与されていない場合、daoAliases プロパティに null が格納されている事</li>
	 *     <li>Function アノテーションが付与されている場合、関数別名が未指定の場合、functionAlias プロパティにフィールド名が格納される事</li>
	 *     <li>Function アノテーションが付与されていない場合、functionAlias プロパティに null が格納されている事</li>
	 * </ul>
	 */
	@Test
	public void ensureInitializationByStarJoinDAOTest() throws Exception{

		// テスト対象 StarJoinDAO 作成
		GroupByDAO<GoupByJoinTestVo> groupDao = buildStarJoinGroupByDAO();


		// リフレクションで、テストメソッドを実行
		executeEnsureInitialization(groupDao);

		
		// リフレクションで、実行結果を取得する。
		Map<String, String[]> props = getGroupByDAOProps(groupDao);

		System.out.println("【StarJoinDAO】");
		debugPropPrint(props, "fieldNames");
		debugPropPrint(props, "columnNames");
		debugPropPrint(props, "daoAliases");
		debugPropPrint(props, "functionAlias");

		Assert.assertEquals("フィールド名が正しく取得できている事（配列件数）", 7, props.get("fieldNames").length);
		Assert.assertEquals("DB 列名が正しく取得できている事（配列件数）", 7, props.get("columnNames").length);
		Assert.assertEquals("列のテーブル別名が正しく取得できている事（配列件数）", 7, props.get("daoAliases").length);
		Assert.assertEquals("関数別名が正しく取得できている事（配列件数）", 7, props.get("functionAlias").length);

		int idx = findTargetIndex(props.get("fieldNames"), "col1Name");
		Assert.assertEquals("フィールド名が正しく取得できている事（１フィールド目）", "col1Name", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB 列名が正しく取得できている事（１フィールド目）", "col1_name", props.get("columnNames")[idx]);
		Assert.assertNull("列のテーブル別名が正しく取得できている事（１フィールド目）", props.get("daoAliases")[idx]);
		Assert.assertNull("関数文字列が正しく取得できている事（１フィールド目）", props.get("functionAlias")[idx]);

		idx = findTargetIndex(props.get("fieldNames"), "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA");
		Assert.assertEquals("フィールド名が正しく取得できている事（２フィールド目）", "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB 列名が正しく取得できている事（２フィールド目）", "xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_A", props.get("columnNames")[idx]);
		Assert.assertNull("列のテーブル別名が正しく取得できている事（２フィールド目）", props.get("daoAliases")[idx]);
		Assert.assertNull("関数文字列が正しく取得できている事（２フィールド目）", props.get("functionAlias")[idx]);

		idx = findTargetIndex(props.get("fieldNames"), "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD");
		Assert.assertEquals("フィールド名が正しく取得できている事（３フィールド目）", "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB 列名が正しく取得できている事（３フィールド目）", "xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_D", props.get("columnNames")[idx]);
		Assert.assertEquals("列のテーブル別名が正しく取得できている事（３フィールド目）", "dao2", props.get("daoAliases")[idx]);
		Assert.assertNull("関数文字列が正しく取得できている事（３フィールド目）", props.get("functionAlias")[idx]);

		idx = findTargetIndex(props.get("fieldNames"), "cnt");
		Assert.assertEquals("フィールド名が正しく取得できている事（４フィールド目）", "cnt", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB 列名が正しく取得できている事（４フィールド目）", "max(dao1.col1_name)", props.get("columnNames")[idx]);
		Assert.assertNull("列のテーブル別名が正しく取得できている事（４フィールド目）", props.get("daoAliases")[idx]);
		Assert.assertEquals("関数文字列が正しく取得できている事（４フィールド目）", "cnt", props.get("functionAlias")[idx]);

		idx = findTargetIndex(props.get("fieldNames"), "cnt2");
		Assert.assertEquals("フィールド名が正しく取得できている事（５フィールド目）", "cnt2", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB 列名が正しく取得できている事（５フィールド目）", "min(dao1.col2_name)", props.get("columnNames")[idx]);
		Assert.assertNull("列のテーブル別名が正しく取得できている事（５フィールド目）", props.get("daoAliases")[idx]);
		Assert.assertEquals("関数文字列が正しく取得できている事（５フィールド目）", "cnt2", props.get("functionAlias")[idx]);

		idx = findTargetIndex(props.get("fieldNames"), "mx");
		Assert.assertEquals("フィールド名が正しく取得できている事（５フィールド目）", "mx", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB 列名が正しく取得できている事（５フィールド目）", "sum(dao2.xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_D)", props.get("columnNames")[idx]);
		Assert.assertEquals("列のテーブル別名が正しく取得できている事（５フィールド目）", "dao2", props.get("daoAliases")[idx]);
		Assert.assertEquals("関数文字列が正しく取得できている事（５フィールド目）", "mx", props.get("functionAlias")[idx]);

		idx = findTargetIndex(props.get("fieldNames"), "cnt3");
		Assert.assertEquals("フィールド名が正しく取得できている事（６フィールド目）", "cnt3", props.get("fieldNames")[idx]);
		Assert.assertEquals("DB 列名が正しく取得できている事（６フィールド目）", "count(*)", props.get("columnNames")[idx]);
		Assert.assertNull("列のテーブル別名が正しく取得できている事（６フィールド目）", props.get("daoAliases")[idx]);
		Assert.assertEquals("関数文字列が正しく取得できている事（６フィールド目）", "cnt3", props.get("functionAlias")[idx]);

	}

	
	
	/**
	 * SELECT　句の出力テスト（委譲先が ReflectionDAO の場合）<br/>
	 * ※列別名編集結果が３０文字を超える場合、３０文字に切り詰められるので、その場合、AS 句が付与されてしまう。<br/>
	 * 実用的には問題ないので、特別な対処は行わない。<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>SELECT LIST の出力項目数が正しい事</li>
	 *     <li>通常フィールドが AS 句、テーブル別名無しで出力される事</li>
	 *     <li>Function アノテーションを使用した場合、指定された関数の関数文字列が出力される事（テーブ別名無し）</li>
	 * </ul>
	 */
	@Test
	public void addSelectClauseByRefDAOTest() throws Exception{

		// テスト対象 GroupByDAO を作成
		GroupByDAO<?> groupDao = buildRefGroupByDAO();


		// テストメソッドを実行
		StringBuilder sql = new StringBuilder();
		groupDao.addSelectClause(sql, "", null);

		System.out.println("【ReflectionDAO】" + sql.toString());

		// SELECT LIST の期待値
		String[] ans = new String[]{"col1_name",
									"count(*) AS cnt3",
									"max(col1_name) AS cnt",
									"min(col2_name) AS cnt2",
									"xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_A AS xxxxxxxxxx_xxxxxxxxxx_xxxxxxxx"};

		// ループカウンタ。　ラムダから参照される変数は、実質的に final として扱われるので、配列に格納している。
		Integer[] cnt = new Integer[]{0};

		Arrays.asList(sql.toString().split(","))
					.stream()
					.map(str-> str.trim())
					.sorted()
					.forEach(str->{
						Assert.assertEquals("SELECT LIST が正しい事", ans[cnt[0]], str);
						cnt[0]++;
					});

		Assert.assertEquals("SELECT LIST の出力項目数がただしい事", Integer.valueOf(5), cnt[0]);

	}
	

	
	/**
	 * SELECT　句の出力テスト（委譲先が JoinDAO の場合）<br/>
	 * ※列別名編集結果が３０文字を超える場合、３０文字に切り詰められるので、その場合、AS 句が付与されてしまう。<br/>
	 * 実用的には問題ないので、特別な対処は行わない。<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>SELECT LIST の出力項目数が正しい事</li>
	 *     <li>通常フィールドに AS 句、テーブル別が付加されている事</li>
	 *     <li>Function アノテーションを使用した場合、指定された関数の関数文字列が出力される事（テーブ別名無し）</li>
	 * </ul>
	 */
	@Test
	public void addSelectClauseByJoinDAOTest() throws Exception{

		// テスト対象 DAO 作成
		GroupByDAO<GoupByJoinTestVo> groupDao = buildJoinGroupByDAO();

		// テストメソッドを実行
		StringBuilder sql = new StringBuilder();
		groupDao.addSelectClause(sql, "", null);

		System.out.println("【JoinDAO】" + sql.toString());
		
		// SELECT LIST の期待値
		String[] ans = new String[]{"count(*) AS cnt3",
									"dao1.col1_name AS dao1_col1_name",
									"dao1.xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_A AS dao1_xxxxxxxxxx_xxxxxxxxxx_xxx",
									"dao2.xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_D AS dao2_xxxxxxxxxx_xxxxxxxxxx_xxx",
									"max(dao1.col1_name) AS cnt",
									"min(dao1.col2_name) AS cnt2",
									"sum(dao2.xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_D) AS mx"};

		// ループカウンタ。　ラムダから参照される変数は、実質的に final として扱われるので、配列に格納している。
		Integer[] cnt = new Integer[]{0};

		Arrays.asList(sql.toString().split(","))
					.stream()
					.map(str-> str.trim())
					.sorted()
					.forEach(str->{
						Assert.assertEquals("SELECT LIST が正しい事", ans[cnt[0]], str);
						cnt[0]++;
					});

		Assert.assertEquals("SELECT LIST の出力項目数がただしい事", Integer.valueOf(7), cnt[0]);
		
	}


	
	/**
	 * SELECT　句の出力テスト（委譲先が StarJoinDAO の場合）<br/>
	 * ※列別名編集結果が３０文字を超える場合、３０文字に切り詰められるので、その場合、AS 句が付与されてしまう。<br/>
	 * 実用的には問題ないので、特別な対処は行わない。<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>SELECT LIST の出力項目数が正しい事</li>
	 *     <li>通常フィールドに AS 句、テーブル別が付加されている事</li>
	 *     <li>Function アノテーションを使用した場合、指定された関数の関数文字列が出力される事（テーブ別名無し）</li>
	 * </ul>
	 */
	@Test
	public void addSelectClauseByStarJoinDAOTest() throws Exception{

		// テスト対象 StarJoinDAO 作成
		GroupByDAO<GoupByJoinTestVo> groupDao = buildStarJoinGroupByDAO();

		
		StringBuilder sql = new StringBuilder();
		groupDao.addSelectClause(sql, "", null);

		System.out.println("【StarJoinDAO】" + sql.toString());

		// SELECT LIST の期待値
		String[] ans = new String[]{"count(*) AS cnt3",
									"dao1.col1_name AS dao1_col1_name",
									"dao1.xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_A AS dao1_xxxxxxxxxx_xxxxxxxxxx_xxx",
									"dao2.xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_D AS dao2_xxxxxxxxxx_xxxxxxxxxx_xxx",
									"max(dao1.col1_name) AS cnt",
									"min(dao1.col2_name) AS cnt2",
									"sum(dao2.xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_D) AS mx"};

		// ループカウンタ。　ラムダから参照される変数は、実質的に final として扱われるので、配列に格納している。
		Integer[] cnt = new Integer[]{0};

		Arrays.asList(sql.toString().split(","))
					.stream()
					.map(str-> str.trim())
					.sorted()
					.forEach(str->{
						Assert.assertEquals("SELECT LIST が正しい事", ans[cnt[0]], str);
						cnt[0]++;
					});

		Assert.assertEquals("SELECT LIST の出力項目数がただしい事", Integer.valueOf(7), cnt[0]);

	}
	
	
	
	/**
	 * From　句の出力テスト（委譲先が ReflectionDAO の場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>委譲先 DAO のテーブル名が出力される事</li>
	 * </ul>
	 */
	@Test
	public void addFromClauseByRefDAOTest() {
		
		// テスト対象 GroupByDAO を作成
		GroupByDAO<?> groupDao = buildRefGroupByDAO();


		// テストメソッドを実行
		StringBuilder sql = new StringBuilder();
		groupDao.addFromClause(sql, "", null);
	
		
		// 結果をチェック
		Assert.assertEquals("From の生成が正しい事", "test_long_name_vo", sql.toString());
	}
	

	
	/**
	 * From　句の出力テスト（委譲先が JoinDAO の場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>委譲先 DAO のテーブル名と結合条件が出力される事</li>
	 * </ul>
	 */
	@Test
	public void addFromClauseByJoinDAOTest() {
		
		// テスト対象 DAO 作成
		GroupByDAO<GoupByJoinTestVo> groupDao = buildJoinGroupByDAO();

		// テストメソッドを実行
		StringBuilder sql = new StringBuilder();
		groupDao.addFromClause(sql, "", null);
	
		
		// 結果をチェック
		Assert.assertEquals("From の生成が正しい事", "test_long_name_vo dao1 INNER JOIN test_long_name_sub_vo dao2 ON dao1.col1_name = dao2.sub_col1_name", sql.toString());
	}


	
	/**
	 * From　句の出力テスト（委譲先が StarJoinDAO の場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>委譲先 DAO のテーブル名と結合条件が出力される事</li>
	 * </ul>
	 */
	@Test
	public void addFromClauseByStarJoinDAOTest() {
		
		// テスト対象 DAO 作成
		GroupByDAO<GoupByJoinTestVo> groupDao = buildStarJoinGroupByDAO();

		// テストメソッドを実行
		StringBuilder sql = new StringBuilder();
		groupDao.addFromClause(sql, "", null);
	
		
		// 結果をチェック
		Assert.assertEquals("From の生成が正しい事", "test_long_name_vo dao1 INNER JOIN test_long_name_sub_vo dao2 ON dao1.col1_name = dao2.sub_col1_name", sql.toString());
	}



	/**
	 * WHERE　句の出力テスト（委譲先が ReflectionDAO の場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>委譲先 DAO の機能にて WHERE 句が生成されている事</li>
	 *     <li>バインド変数リストが正しい事</li>
	 * </ul>
	 */
	@Test
	public void addWhereClausesByRefGroupTest() throws Exception{
		
		// テスト対象 GroupByDAO 作成
		GroupByDAO<GoupByRefTestVo> groupDao = buildRefGroupByDAO();
		
		// バインド変数値
		Integer[] ansParams = new Integer[]{10, 20};

		// テスト対象検索条件作成
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("col1Name", ansParams[0]);							// 通常フィールド
		criteria.addWhereClause("xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA", ansParams[1]);	// 桁数オーバーフィールド

		
		// テストメソッド実行
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();

		groupDao.prepareCriteria(criteria, null);
		executeEnsureInitialization(groupDao);
		DAOUtils.addWhereClauses(groupDao, criteria, "", sql, params, true);

		Assert.assertEquals("WHERE 句の出力が正しい事", " WHERE col1_name = ? AND xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_A = ?", sql.toString());
		
		Integer[] cnt = new Integer[]{0};
		params.stream().forEach(param -> {
								Assert.assertEquals("バインド変数が正しい事", ansParams[cnt[0]], param);
								cnt[0]++;
							});
	}



	/**
	 * WHERE　句の出力テスト（委譲先が JoinDAO の場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>委譲先 DAO の機能にて WHERE 句が生成されている事</li>
	 *     <li>バインド変数リストが正しい事</li>
	 * </ul>
	 */
	@Test
	public void addWhereClausesByJoinGroupTest() throws Exception{
		
		// テスト対象 GroupByDAO 作成
		GroupByDAO<GoupByJoinTestVo> groupDao = buildJoinGroupByDAO();


		// バインド変数値
		Integer[] ansParams = new Integer[]{10, 20, 30, 40};
		
		// テスト対象検索条件作成
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("col1Name", 10);						// 通常フィールド(dao1)
		criteria.addWhereClause("xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA", 20);	// 桁数オーバーフィールド(dao1)
		criteria.addWhereClause("subCol1Name", 30);						// 通常フィールド(dao2)
		// 重複フィールド(dao2)
		criteria.addWhereClause("dao2", "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD", 40, DAOCriteria.EQUALS, true);


		// テストメソッド実行
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();

		groupDao.prepareCriteria(criteria, null);
		executeEnsureInitialization(groupDao);
		DAOUtils.addWhereClauses(groupDao, criteria, "", sql, params, true);

		String ans = " WHERE dao1.col1_name = ? AND dao1.xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_A = ? " +
					 "AND dao2.sub_col1_name = ? AND dao2.xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_D <> ?";
		Assert.assertEquals("WHERE 句の出力が正しい事", ans, sql.toString());

		Integer[] cnt = new Integer[]{0};
		params.stream().forEach(param -> {
								Assert.assertEquals("バインド変数が正しい事", ansParams[cnt[0]], param);
								cnt[0]++;
							});

	}

	

	/**
	 * WHERE　句の出力テスト（委譲先が StarJoinDAO の場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>委譲先 DAO の機能にて WHERE 句が生成されている事</li>
	 *     <li>バインド変数リストが正しい事</li>
	 * </ul>
	 */
	@Test
	public void addWhereClausesByStarJoinGroupTest() throws Exception{
		
		// テスト対象 GroupByDAO 作成
		GroupByDAO<GoupByJoinTestVo> groupDao = buildStarJoinGroupByDAO();


		// バインド変数値
		Integer[] ansParams = new Integer[]{10, 20, 30, 40};
		
		// テスト対象検索条件作成
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("col1Name", 10);						// 通常フィールド(dao1)
		criteria.addWhereClause("xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxA", 20);	// 桁数オーバーフィールド(dao1)
		criteria.addWhereClause("subCol1Name", 30);						// 通常フィールド(dao2)
		// 重複フィールド(dao2)
		criteria.addWhereClause("dao2", "xxxxxxxxxxXxxxxxxxxxXxxxxxxxxxD", 40, DAOCriteria.EQUALS, true);


		// テストメソッド実行
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();

		groupDao.prepareCriteria(criteria, null);
		executeEnsureInitialization(groupDao);
		DAOUtils.addWhereClauses(groupDao, criteria, "", sql, params, true);

		String ans = " WHERE dao1.col1_name = ? AND dao1.xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_A = ? " +
					 "AND dao2.sub_col1_name = ? AND dao2.xxxxxxxxxx_xxxxxxxxxx_xxxxxxxxxx_D <> ?";
		Assert.assertEquals("WHERE 句の出力が正しい事", ans, sql.toString());

		Integer[] cnt = new Integer[]{0};
		params.stream().forEach(param -> {
								Assert.assertEquals("バインド変数が正しい事", ansParams[cnt[0]], param);
								cnt[0]++;
							});

	}


	
	// ReflectingDAO + GroupByDAO をテスト対象 DAO を生成する。
	private GroupByDAO<GoupByRefTestVo> buildRefGroupByDAO() {
		
		// 委譲先の ReflectingDAO を作成
		ReflectingDAO<TestLongNameVo> dao = new ReflectingDAO<>();
		dao.setValueObjectClassName("jp.co.transcosmos.mock.vo.TestLongNameVo");

		// テスト対象 GroupByDAO を作成
		GroupByDAO<GoupByRefTestVo> groupDao = new GroupByDAO<>();
		groupDao.setTargetDAO(dao);
		groupDao.setValueObjectClassName("jp.co.transcosmos.mock.vo.GoupByRefTestVo");

		return groupDao;
	}

	
	// JoinDAO + GroupByDAO をテスト対象 DAO を生成する。
	private GroupByDAO<GoupByJoinTestVo> buildJoinGroupByDAO() {

		// 委譲先の JonDAO を作成
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
		
		
		// テスト対象 GroupByDAO を作成
		GroupByDAO<GoupByJoinTestVo> groupDao = new GroupByDAO<>();
		groupDao.setTargetDAO(joinDao);
		groupDao.setValueObjectClassName("jp.co.transcosmos.mock.vo.GoupByJoinTestVo");
		
		return groupDao;

	}

	
	// StarJoinDAO + GroupByDAO をテスト対象 DAO を生成する。
	private GroupByDAO<GoupByJoinTestVo> buildStarJoinGroupByDAO() {

		// 結合する DAO を作成
		ReflectingDAO<TestLongNameVo> mainDao = new ReflectingDAO<>();
		mainDao.setValueObjectClassName("jp.co.transcosmos.mock.vo.TestLongNameVo");

		ReflectingDAO<TestLongNameVo> subDao = new ReflectingDAO<>();
		subDao.setValueObjectClassName("jp.co.transcosmos.mock.vo.TestLongNameSubVo");
		
		// start join DAO
		StarJoinDAO joinDao = new StarJoinDAO();
		joinDao.setMainDAO(mainDao);
		joinDao.setMainAlias("dao1");

		// 結合条件
		JoinCondition condition = new JoinCondition();
		condition.setField1("col1Name");
		condition.setField2("subCol1Name");

		// StarJoin の子表 DAO1
		StarJoinDAODescriptor descriptor = new StarJoinDAODescriptor();
		descriptor.setDao(subDao);
		descriptor.setAlias("dao2");
		descriptor.setCondition(condition);

		List<StarJoinDAODescriptor> childDAOs = Arrays.asList(descriptor);
		joinDao.setChildDAOs(childDAOs);

		
		// テスト対象 GroupByDAO を作成
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
	

	// getFieldAliases()　メソッドをリフレクションで実行
	private String[] executeGetFieldAliases(GroupByDAO<?> groupDao, String[] thisAlias, String[] forFieldNames, String[] thisFunctionAlias) throws Exception{

		Method method = groupDao.getClass().getDeclaredMethod("getFieldAliases", String[].class, String[].class, String[].class);
		method.setAccessible(true);

		return (String[]) method.invoke(groupDao, thisAlias, forFieldNames, thisFunctionAlias);
	}

	
	// ensureInitialization()　メソッドをリフレクションで実行
	private void executeEnsureInitialization(GroupByDAO<?> groupDao) throws Exception{
		
		Method method = groupDao.getClass().getDeclaredMethod("ensureInitialization");
		method.setAccessible(true);

		method.invoke(groupDao);
	}

	
	// GroupByDAO の内部プロパティ値を取得する。
	private Map<String, String[]> getGroupByDAOProps(GroupByDAO<?> groupDao) throws Exception {

		Map<String, String[]> result = new HashMap<>();

		// バリーオブジェクトのフィールド名を取得
		Field field = groupDao.getClass().getDeclaredField("fieldNames");
		field.setAccessible(true);
		result.put("fieldNames", (String[])field.get(groupDao));
		
		// 列のﾃｰﾌﾞﾙ別名を取得
		field = groupDao.getClass().getDeclaredField("daoAliases");
		field.setAccessible(true);
		result.put("daoAliases", (String[])field.get(groupDao));
		
		// DB の列名を取得
		field = groupDao.getClass().getDeclaredField("columnNames");
		field.setAccessible(true);
		result.put("columnNames", (String[])field.get(groupDao));
		
		// 集計関数使用時の別名を取得
		field = groupDao.getClass().getDeclaredField("functionAlias");
		field.setAccessible(true);
		result.put("functionAlias", (String[])field.get(groupDao));

	    return result;
	}

	
	
}
