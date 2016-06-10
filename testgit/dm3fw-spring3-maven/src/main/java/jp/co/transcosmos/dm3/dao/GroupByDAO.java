package jp.co.transcosmos.dm3.dao;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import jp.co.transcosmos.dm3.dao.annotation.DaoAlias;
import jp.co.transcosmos.dm3.dao.annotation.Function;
import jp.co.transcosmos.dm3.utils.ReflectionUtils;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class GroupByDAO<E> implements SQLClauseBuilder {
	
    private static final Log log = LogFactory.getLog(GroupByDAO.class);

    private DataSource dataSource;

    /** マッピングするバリーオブジェクトのフィールド名 */
    private String fieldNames[]; // private because we want to force override of assignFieldNameToColumnNameMappings
    /** マッピングするテーブルのカラム名、または、集計関数文字列 */
    private String columnNames[];
    /** DaoAlias アノテーションで指定された、列に該当するテーブルの別名 */
    private String daoAliases[];
    /** 集計関数の別名 */
    private String functionAlias[];

    private List<DAOCriteriaGroupByClause> groupByClauses;

    private RowMapper<E> resultRowMapper;

    private Map<String,String> fieldToColumnMappingCache;

    /** もし、フィールド名を強制的の大文字にする場合、このプロパティを true に設定する */
    private boolean capitalizeFieldNames;

	/** GROUP BY を実行するターゲットとする DAO  */
	private SQLClauseBuilder targetDAO; 
	/** SQL 実行時の結果受け取り用バリーーオブジェクト */
	private Class<? extends E> valueObjectClass;

    // note
    // SELECT 文実行時の該当件数を getRow() で取得する場合、Driver によっては last() を実行しないと
    // 正確な行数を返さない場合がある。　しかし、FORWARD ONLY の ResultSet の場合、この方法は使用できない。
    // （例外が発生する。）　その様な Driver を使用する場合、このプロパティ値を true に設定する。
    // true に設定した場合、getRow() を使用せずに COUNT(*) の SELECT 文で該当件数を取得して処理する。
    /** 該当件数を別途取得する場合、true を設定する。 （デフォルト false）*/
    private boolean useCountQueryForMaxRows = false;
    private boolean nullResultIfAllFieldsAreNull = false;
    private boolean emptyStringsToNull = false;
    private boolean nullStringsToEmpty = false;

    

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * もし、フィールド名を強制的の大文字にする場合、このプロパティを true に設定する<br/>
     * <br/>
     * @param true の場合、フィールド名を大文字固定にする。
     */
    public void setCapitalizeFieldNames(boolean capitalizeFieldNames) {
        this.capitalizeFieldNames = capitalizeFieldNames;
    }

	public void setTargetDAO(SQLClauseBuilder targetDAO) {
		this.targetDAO = targetDAO;
	}

    @SuppressWarnings("unchecked")
	public void setValueObjectClassName(String valueObjectClassName) {
        try {
            this.valueObjectClass = (Class<? extends E>) Class.forName(valueObjectClassName);
        } catch (ClassNotFoundException err) {
            throw new RuntimeException("Error loading DAO class: " + valueObjectClassName, err);
        }
    }

    public void setUseCountQueryForMaxRows(boolean useCountQueryForMaxRows) {
		this.useCountQueryForMaxRows = useCountQueryForMaxRows;
	}

	public void setNullResultIfAllFieldsAreNull(boolean nullResultIfAllFieldsAreNull) {
		this.nullResultIfAllFieldsAreNull = nullResultIfAllFieldsAreNull;
	}

	public void setEmptyStringsToNull(boolean emptyStringsToNull) {
		this.emptyStringsToNull = emptyStringsToNull;
	}

	public void setNullStringsToEmpty(boolean nullStringsToEmpty) {
		this.nullStringsToEmpty = nullStringsToEmpty;
	}

	public void setGroupByClauses(List<DAOCriteriaGroupByClause> groupByClauses) {
		this.groupByClauses = groupByClauses;
	}

    public List<E> selectByFilter(DAOCriteria criteria) {
        long start = System.currentTimeMillis();

        criteria = prepareCriteria(criteria, null);
        List<Object> params = new ArrayList<Object>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        addSelectClause(sql, "", null);
        sql.append(" FROM ");
        addFromClause(sql, "", params);

        // Add restrictions as sql
        // GROUP BY の場合、SELECT LIST に存在しないフィールドを WHERE 句、ORDER BY 句で使用する場合
        // があるのでフィールドチェックを無効化して SQL 文を生成する。
        DAOUtils.addWhereClauses(this, criteria, "", sql, params, true);
        addGroypByClauses(this.groupByClauses, sql);
        DAOUtils.addOrderByClauses(this, criteria, "", sql, true);
        
        DAOUtils.dumpSQL(sql.toString(), params);
        JdbcTemplate template = new JdbcTemplate(getDataSource());
        if ((criteria != null) && (criteria.getMaxRows() >= 0)) {
            template.setMaxRows(criteria.getMaxRows());
        }
        
        // Set up the query builder and result extractor
        FragmentPreparedStatementCreator psc = new FragmentPreparedStatementCreator(
                sql.toString(), params);
        FragmentResultSetExtractor<E> rse = new FragmentResultSetExtractor<E>(
                this.resultRowMapper, criteria);
        if (rse.isFragmentMode() && this.useCountQueryForMaxRows) {
            rse.setRowCountHint(getRowCountMatchingFilterInternal(criteria));
        }

        // Evaluate the query
        @SuppressWarnings("unchecked")
		List<E> results = (List<E>) template.query(psc, psc, rse);
        log.info("SelectByFilter query found " + results.size() + " rows in " + 
                (System.currentTimeMillis() - start) + "ms");
        return results;

    }

	
    /**
     * SELECT 句を生成する。<br/>
     * 関数が使用されている場合は、columnNames の値をそのまま使用する。<br/>
     * 関数が使用されていない場合は、委譲先の DAO のメソッドを使用して、必要に応じてテーブル別名を付加する。<br/>
     * カラム名と、フィールド別名の値が異なる場合、fieldAliases　の値を使用して列別名を付加する。<br/>
     * 生成された SQL 文は、引数 sql の StringBuilder に追記する。<br/>
     * <br/>
     * @param sql SQL 文用 StringBulder
     * @param alias Dao 別名　（実質的に未使用）
     * @param filterFields フィールド情報のフィールター情報　（実質的に未使用）　
     *
     */
    @Override
    public void addSelectClause(StringBuilder sql, String alias, String[] filterFields) {
        ensureInitialization();
        if (filterFields == null) {
        	filterFields = this.fieldNames;
        }

        // フィールドの別名を取得する。
        String fieldAliases[] = getFieldAliases(this.daoAliases, filterFields, this.functionAlias);
        boolean addedColumn = false;
        for (int n = 0; n < filterFields.length; n++) {

        	// 集計関数を使用する場合は、必ず関数別名が設定される。
        	// 関数別名が設定されている場合、columnNames プロパティに格納されている関数文字列をそのまま
        	// 使用する。　関数別名が指定されていない場合は、委譲先の DAO より、DB 列名に、列のテーブル別
        	// 名を付加する。
        	String aliasDotColumn = null;
        	if (StringValidateUtil.isEmpty(this.functionAlias[n])) {
            	aliasDotColumn = lookupAliasDotColumnName(filterFields[n], this.daoAliases[n], alias);
        	} else {
            	aliasDotColumn = this.columnNames[n];
        	}

        	// Function アノテーションが設定されていない通常フィールドの場合、委譲先の DAO で未定義のフィールドは
        	// SELECT LIST の出力対象にならない。
        	// そのような場合、aliasDotColumn 変数に null が格納されている。
            if (aliasDotColumn != null) {
                if (addedColumn) {
                    sql.append(", ");
                }
                addedColumn = true;

                // DB 列名と、列別名が一致しない場合、AS 句を使用して列別名を設定する。
                sql.append(aliasDotColumn);            
                if (!fieldAliases[n].equals(findColumnName(filterFields[n]))) {
                    sql.append(" AS ");
                    sql.append(fieldAliases[n]);
                }
            	
            }
        }
    }

    
    
    /**
     * Builds the from clause for the joined tables
     */
    @Override
    public void addFromClause(StringBuilder sql, String alias, List<Object> params) {
        this.targetDAO.addFromClause(sql, alias, params);
    }
    
    
    /**
     * No implicit criteria - returns null
     */
    @Override
    public DAOCriteria buildImplicitCriteria() {
		return null;
	}

    
    /**
     * Builds the where clause for the joined tables
     */
    @Override
    public boolean addOneWhereClause(StringBuilder sql, String alias, DAOCriteriaWhereClause where, List<Object> params) {
        if (this.targetDAO.addOneWhereClause(sql, alias, where, params)) {
            return true;
        }
        return false;
    }

    
    /**
     * Builds the order-by clause for the joined tables
     */
    @Override
    public boolean addOneOrderByClause(StringBuilder sql, String alias, DAOCriteriaOrderByClause orderBy) {
        if (this.targetDAO.addOneOrderByClause(sql, alias, orderBy)) {
            return true;
        }
        return false;
    }


    /**
     * Returns the row mapper for this DAO's valueobject using reflection to map
     * the two and populate the output.
     */
    @Override
    public RowMapper<E> getRowMapper(String fieldPrefix, String filterFields[], boolean isJoinedTable) {
        ensureInitialization();
        if (filterFields == null) {
        	filterFields = this.fieldNames;
        }
        return new ReflectingRowMapper<E>(this.valueObjectClass, 
        		filterFields, 
                getFieldAliases(this.daoAliases, filterFields, this.functionAlias),
                this.nullResultIfAllFieldsAreNull, 
                this.nullStringsToEmpty,
                this.emptyStringsToNull, 
                null);
    }


    /**
     * Returns a mapping of a fieldname to the 
     */
    @Override
    public String lookupAliasDotColumnName(String fieldName, String daoAlias, String thisAlias) {
        return this.targetDAO.lookupAliasDotColumnName(fieldName, daoAlias, thisAlias);
    }


    @Override
    public String lookupLabelledName(String fieldName, String daoAlias, String thisAlias) {
        return this.targetDAO.lookupLabelledName(fieldName, daoAlias, thisAlias);
    }

    
	@Override
    public DAOCriteria prepareCriteria(DAOCriteria criteria, String thisAlias) {
        return this.targetDAO.prepareCriteria(criteria, thisAlias);
    }


    /**
     * Internal method that confirms a valid initialized state before doing anything.
     */
    protected void ensureInitialization() {

    	synchronized (this) {
            if (this.fieldNames == null) {

            	List<Map<String,String>> fieldNameToColumnNameMappings
            		= assignFieldNameToColumnNameMappings();

                this.fieldNames = new String[fieldNameToColumnNameMappings.size()];		// VO のフィールド名
                this.columnNames = new String[fieldNameToColumnNameMappings.size()];	// テーブルの列名
                this.daoAliases = new String[fieldNameToColumnNameMappings.size()];		// 列のテーブル別名
                this.functionAlias = new String[fieldNameToColumnNameMappings.size()];	// 集計関数の別名

                Map<String,String> mapping = new HashMap<String,String>();
                for (int n = 0; n < this.fieldNames.length; n++) {
                	this.fieldNames[n] = fieldNameToColumnNameMappings.get(n).get("fieldNames");
                	this.columnNames[n] = fieldNameToColumnNameMappings.get(n).get("columnNames");
                	this.daoAliases[n] = fieldNameToColumnNameMappings.get(n).get("daoAliases");
                	this.functionAlias[n] = fieldNameToColumnNameMappings.get(n).get("functionAlias");

                	mapping.put(this.fieldNames[n], this.columnNames[n]);
                }
                this.fieldToColumnMappingCache = Collections.unmodifiableMap(mapping);
                
                this.resultRowMapper = new ReflectingRowMapper<E>(
                		this.valueObjectClass, 
                        this.fieldNames,
                        getFieldAliases(this.daoAliases, this.fieldNames, this.functionAlias), 
                        false, 
                        this.nullStringsToEmpty, 
                        this.emptyStringsToNull,
                        null);
            
            }
        }
    }



    /**
     * フィールドとカラム名のマッピング情報を取得する。<br/>
     * <br/>
     * 戻り値のリストオブジェクトには、バリーオブジェクトに定義されているフィールド毎の情報が Map 形式で格納されている。<br/>
     * key = fieldNames : value = バリーオブジェクトのフィールド名<br/>
     * key = columnNames : value = DBの列名<br/>
     * key = daoAliases : value = annotation で指定された、DBの列のテーブル別名<br/>
     * key = functionAlias : value = annotation で指定された、集計関数の別名<br/>
     * <br/>
     * @return フィールド毎の情報が格納されたリストオブジェクト
     */
    protected List<Map<String,String>> assignFieldNameToColumnNameMappings() {
        List<Map<String, String>> outputMappings = new ArrayList<>();

        // バリーオブジェクトに格納されているフィールド情報と、アノテーションを取得する。
        // static フィールドや、getter の存在しないフィールドは取得対象外となる。
        LinkedHashMap<String, Annotation[]> allFields = ReflectionUtils.getAllFieldNamesAndAnnotationsByGetters(this.valueObjectClass);

        for (Map.Entry<String, Annotation[]> entry : allFields.entrySet()){
        	Map<String, String> columnInfo = new HashMap<>(); 

        	// バリーオブジェクトのフィールド名を設定する。
        	columnInfo.put("fieldNames", entry.getKey());


        	// DB フィールドに該当するテーブルの別名を格納
        	// この値は、バリーオブジェクトのフィールドに DaoAlias アノテーションが設定されている場合に格納される。
        	// 列のテーブル名 alias は、委譲先の DAO が復帰する値よりも、この値が最優先で使用される。

        	// note
        	// Funtion アノテーションで、集計関数の対象フィールド名を指定した場合、このアノテーションの設定値
        	// が列のテーブル別名として使用される。

        	// note
        	// JoinDAO、StarJoinDAO を使用している場合、同名の DB フィールドが存在する場合がある。
        	// 未指定の場合、最初に見つかった DAO の別名が適用されるが、取得したいテーブルのフィールドでは無い
        	// 場合がある。　よって、同名のフィールドが存在する場合、DaoAlias アノテーションで明示的に参照テー
        	// ブルを特定する使い方が望ましい。
        	String daoAlias = getDaoAliasFromAnnotation(entry.getValue());
        	columnInfo.put("daoAliases", daoAlias);


        	// 集計関数文字列を取得
        	String functionInfo = getFunctionFromAnnotation(entry.getValue(), daoAlias);


        	// DB のフィールド名を格納
        	if (StringValidateUtil.isEmpty(functionInfo)){
            	// 集計関数が使用されていない場合、DB のフィールド名は、バリーオブジェクトのフィールド名をスネーク書式に変換して
        		// 生成し、DB フィールド名として設定する。
        		columnInfo.put("columnNames", mappingPolicyFieldNameToColumnName(entry.getKey()));
        	} else {
            	// 集計関数が使用されている場合、DB のフィールド名は、集計関数文字列を設定する。
        		columnInfo.put("columnNames", functionInfo);
        		
            	// 集計関数の別名を格納する。
              	// Function アノテーションが使用されている場合、フィールド名が集計関数の別名として使用される。
               	columnInfo.put("functionAlias", entry.getKey());
        	}

            outputMappings.add(columnInfo);
            log.debug("Mapping " + entry.getKey() + " --> " + columnInfo.get("columnNames"));
        }

        return outputMappings;
    }



    /**
     * バリーオブジェクトのフィールド名から、DB 列名（または、関数文字列）を取得する。<br/>
     * <br/>
     * @param fieldName バリーオブジェクトのフィールド名
     * @return　DB 列名、または、関数文字列
     */
    protected String findColumnName(String fieldName) {
    	return this.fieldToColumnMappingCache.get(fieldName);
    }

    
    
    protected void addGroypByClauses(List<DAOCriteriaGroupByClause> groupByClauses, StringBuilder sql) {
    	// GROUP BY 設定が無い場合、何も処理しない。
    	if (groupByClauses == null || groupByClauses.size() == 0) {
    		return;
    	}

   		// GROUP BY 句生成
    	boolean isFirst = true;
       	for (DAOCriteriaGroupByClause groupBy :  groupByClauses){
       		if (isFirst){
       			sql.append(" GROUP BY ");
       			isFirst = false;
       		} else {
       			sql.append(", ");
       		}
       		sql.append(lookupAliasDotColumnName(groupBy.getFieldName(), groupBy.getDaoAlias(), ""));
       	}
    }



	/**
     * This is where the default naming policy is defined. Override this to provide 
     * custom naming policies.
     */
    protected String mappingPolicyFieldNameToColumnName(String fieldName) {
    	String mappedTo = ReflectionUtils.lmcToUnderscore(fieldName);
        if (this.capitalizeFieldNames) {
        	mappedTo = mappedTo.toUpperCase();
        }
        return mappedTo;
    }



    /**
     * アノテーション情報から、DB 列のテーブル別名を取得する。<br/>
     * もし、該当するアノテーションが存在しない場合は null を復帰する。<br/>
     * <br/>
     * @param annotations フィールドに設定されているアノテーション
     * @return アノテーションで設定された DB 列のﾃｰﾌﾞﾙ別名
     */
    protected String getDaoAliasFromAnnotation(Annotation[] annotations){
    	
    	for (Annotation annotation : annotations){
    		if (annotation instanceof DaoAlias){
    			return ((DaoAlias) annotation).value();
    		}
    	}
    	return null;
    }

    
    
    /**
     * アノテーション情報から、DB 列の集計関数情報を取得する。<br/>
     * <br/>
     * 集計関数文字列は、以下のルールで生成される。
     * <ul>
     * <li>集計関数が対象とする列のテーブル別名は、DaoAlias アノテーションが設定されていれば、その値を優先する。</li>
     * <li>DaoAlias アノテーションが未設定の場合、列のテーブル別名は委譲先の DAO に依存する。</li>
     * </ul>
     * <br/>
     * @param annotations フィールドに設定されているアノテーション
     * @param daoAlias DaoAlias アノテーションで設定された、列のテーブル別名
     * @return 集計関数に関する配列。　関数文字列 （ Function アノテーション未設定時は null）
     */
    protected String getFunctionFromAnnotation(Annotation[] annotations, String daoAlias){

    	for (Annotation annotation : annotations){
    		if (annotation instanceof Function){

    			// アノテーションから、関数の対象列名を取得する。
    			String columnName = ((Function) annotation).columnName();

    			if (!"*".equals(columnName)){
    				// アノテーションで指定された列名が、"*" 以外の場合、委譲先 DAO の機能を使用して
    				// 列名を生成する。
    				// 列のテーブル別名は、DaoAlias アノテーションが使用されていれば、その値を使用する。
    				// 未使用の場合は、委譲先の DAO に依存する。
    				columnName = lookupAliasDotColumnName(columnName, daoAlias, "");
    			}
    			// アノテーション設定されている関数名と、取得した列名を使用して、関数文字列を生成する。
    			return ((Function) annotation).functionName().toFunctionString(columnName);
    		}
    	}
    	return null;
    }

    
    
    @SuppressWarnings("deprecation")
	protected int getRowCountMatchingFilterInternal(DAOCriteria criteria) {
        ensureInitialization();
        JdbcTemplate template = new JdbcTemplate(getDataSource());

        // 集計関数の場合、インラインビューとして実行して該当件数を取得する。
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(1) AS rowCount FROM ");
        sql.append("(SELECT ");
        addSelectClause(sql, "", null);
        sql.append(" FROM ");
        addFromClause(sql, "", null);

        // Add restrictions as sql
        long start = System.currentTimeMillis();
        int rowCount = 0;
        List<Object> params = new ArrayList<Object>();
        DAOUtils.addWhereClauses(this, criteria, "", sql, params, true);
        addGroypByClauses(this.groupByClauses, sql);
        sql.append(") target_query");

        DAOUtils.dumpSQL(sql.toString(), params);
        rowCount = template.queryForInt(sql.toString(), params.toArray());
        log.info("Row count query found " + rowCount + " rows in " + 
                (System.currentTimeMillis() - start) + "ms");
        return rowCount;
    }

    
    /**
     * フィールド毎の別名を取得する。<br/>
     * 委譲先が ReflectingDAO の場合、フィールド名をスネーク書式に変換した文字列が設定される。<br/>
     * もし、thisAlias の該当箇所でテーブル別名が設定されている場合、それを付加した文字列を設定する。<br/>
     * 委譲先が JoinDAO、StarJoinDAO の場合、DAO 側のテーブル別名 + フィールド名をスネーク書式に変換
     * した文字列が設定される。　（同名のフィールドが存在する場合、）<br/>
     * もし、thisAlias の該当箇所でテーブル別名が設定されている場合、DAO 側のテーブル別名よりも優先される。<br/>
     * さらに、thisColumnAliases　に、列別名が設定されている場合、その値を最優先にする。<br/>
     * <br/>
     * @param thisAlias フィールドに DaoAlias アノテーションで設定されたテーブル別名の配列
     * @param forFieldNames　フィールド名の配列
     * @param thisFunctionAlias 集計関数使用時の別名
     * @return　フィールド毎の別名
     */
    private String[] getFieldAliases(String[] thisAlias, String[] forFieldNames, String[] thisFunctionAlias) {

    	String out[] = new String[forFieldNames.length];

        for (int n = 0; n < forFieldNames.length; n++) {

        	if (StringValidateUtil.isEmpty(thisFunctionAlias[n])) {
            	// アノテーションで集計関数の別名が未指定の場合、委譲先の DAO を使用して列別名を組み立てる。
        		// （関数を使用しない、通常参照とみなす。）

        		// 委譲先の DAO を使用してフィールド毎の別名を生成する。
        		// もし、フィールドに DaoAlias アノテーションでテーブル別名が設定されている場合、
        		// その値をテーブル別名としてフィールド別名を加工する。
        		out[n] = lookupLabelledName(forFieldNames[n], thisAlias[n], "");
        		if (out[n].length() > 30) {
        			out[n] = out[n].substring(0, 30);
        		}
        	} else {
        		// 関数使用時は、指定された別名を設定する。
        		out[n] = thisFunctionAlias[n];
        	}

            // 既に同名の alias が登録されている場合、重複しない名称に変更する。
            // 通常、重複は発生しないが、テーブル名 + フィールド名をスネーク書式に変換した際、
            // 30 文字を超える場合がある。　その場合、30　文字に切り詰められるので、重複する
            // 場合がある。
            DAOUtils.dupliateAliasRename(out, n);
        }
        return out;
    }

}
