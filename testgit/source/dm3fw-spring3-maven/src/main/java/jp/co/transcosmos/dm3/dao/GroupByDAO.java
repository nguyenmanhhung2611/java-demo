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

    /** �}�b�s���O����o���[�I�u�W�F�N�g�̃t�B�[���h�� */
    private String fieldNames[]; // private because we want to force override of assignFieldNameToColumnNameMappings
    /** �}�b�s���O����e�[�u���̃J�������A�܂��́A�W�v�֐������� */
    private String columnNames[];
    /** DaoAlias �A�m�e�[�V�����Ŏw�肳�ꂽ�A��ɊY������e�[�u���̕ʖ� */
    private String daoAliases[];
    /** �W�v�֐��̕ʖ� */
    private String functionAlias[];

    private List<DAOCriteriaGroupByClause> groupByClauses;

    private RowMapper<E> resultRowMapper;

    private Map<String,String> fieldToColumnMappingCache;

    /** �����A�t�B�[���h���������I�̑啶���ɂ���ꍇ�A���̃v���p�e�B�� true �ɐݒ肷�� */
    private boolean capitalizeFieldNames;

	/** GROUP BY �����s����^�[�Q�b�g�Ƃ��� DAO  */
	private SQLClauseBuilder targetDAO; 
	/** SQL ���s���̌��ʎ󂯎��p�o���[�[�I�u�W�F�N�g */
	private Class<? extends E> valueObjectClass;

    // note
    // SELECT �����s���̊Y�������� getRow() �Ŏ擾����ꍇ�ADriver �ɂ���Ă� last() �����s���Ȃ���
    // ���m�ȍs����Ԃ��Ȃ��ꍇ������B�@�������AFORWARD ONLY �� ResultSet �̏ꍇ�A���̕��@�͎g�p�ł��Ȃ��B
    // �i��O����������B�j�@���̗l�� Driver ���g�p����ꍇ�A���̃v���p�e�B�l�� true �ɐݒ肷��B
    // true �ɐݒ肵���ꍇ�AgetRow() ���g�p������ COUNT(*) �� SELECT ���ŊY���������擾���ď�������B
    /** �Y��������ʓr�擾����ꍇ�Atrue ��ݒ肷��B �i�f�t�H���g false�j*/
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
     * �����A�t�B�[���h���������I�̑啶���ɂ���ꍇ�A���̃v���p�e�B�� true �ɐݒ肷��<br/>
     * <br/>
     * @param true �̏ꍇ�A�t�B�[���h����啶���Œ�ɂ���B
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
        // GROUP BY �̏ꍇ�ASELECT LIST �ɑ��݂��Ȃ��t�B�[���h�� WHERE ��AORDER BY ��Ŏg�p����ꍇ
        // ������̂Ńt�B�[���h�`�F�b�N�𖳌������� SQL ���𐶐�����B
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
     * SELECT ��𐶐�����B<br/>
     * �֐����g�p����Ă���ꍇ�́AcolumnNames �̒l�����̂܂܎g�p����B<br/>
     * �֐����g�p����Ă��Ȃ��ꍇ�́A�Ϗ���� DAO �̃��\�b�h���g�p���āA�K�v�ɉ����ăe�[�u���ʖ���t������B<br/>
     * �J�������ƁA�t�B�[���h�ʖ��̒l���قȂ�ꍇ�AfieldAliases�@�̒l���g�p���ė�ʖ���t������B<br/>
     * �������ꂽ SQL ���́A���� sql �� StringBuilder �ɒǋL����B<br/>
     * <br/>
     * @param sql SQL ���p StringBulder
     * @param alias Dao �ʖ��@�i�����I�ɖ��g�p�j
     * @param filterFields �t�B�[���h���̃t�B�[���^�[���@�i�����I�ɖ��g�p�j�@
     *
     */
    @Override
    public void addSelectClause(StringBuilder sql, String alias, String[] filterFields) {
        ensureInitialization();
        if (filterFields == null) {
        	filterFields = this.fieldNames;
        }

        // �t�B�[���h�̕ʖ����擾����B
        String fieldAliases[] = getFieldAliases(this.daoAliases, filterFields, this.functionAlias);
        boolean addedColumn = false;
        for (int n = 0; n < filterFields.length; n++) {

        	// �W�v�֐����g�p����ꍇ�́A�K���֐��ʖ����ݒ肳���B
        	// �֐��ʖ����ݒ肳��Ă���ꍇ�AcolumnNames �v���p�e�B�Ɋi�[����Ă���֐�����������̂܂�
        	// �g�p����B�@�֐��ʖ����w�肳��Ă��Ȃ��ꍇ�́A�Ϗ���� DAO ���ADB �񖼂ɁA��̃e�[�u����
        	// ����t������B
        	String aliasDotColumn = null;
        	if (StringValidateUtil.isEmpty(this.functionAlias[n])) {
            	aliasDotColumn = lookupAliasDotColumnName(filterFields[n], this.daoAliases[n], alias);
        	} else {
            	aliasDotColumn = this.columnNames[n];
        	}

        	// Function �A�m�e�[�V�������ݒ肳��Ă��Ȃ��ʏ�t�B�[���h�̏ꍇ�A�Ϗ���� DAO �Ŗ���`�̃t�B�[���h��
        	// SELECT LIST �̏o�͑ΏۂɂȂ�Ȃ��B
        	// ���̂悤�ȏꍇ�AaliasDotColumn �ϐ��� null ���i�[����Ă���B
            if (aliasDotColumn != null) {
                if (addedColumn) {
                    sql.append(", ");
                }
                addedColumn = true;

                // DB �񖼂ƁA��ʖ�����v���Ȃ��ꍇ�AAS ����g�p���ė�ʖ���ݒ肷��B
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

                this.fieldNames = new String[fieldNameToColumnNameMappings.size()];		// VO �̃t�B�[���h��
                this.columnNames = new String[fieldNameToColumnNameMappings.size()];	// �e�[�u���̗�
                this.daoAliases = new String[fieldNameToColumnNameMappings.size()];		// ��̃e�[�u���ʖ�
                this.functionAlias = new String[fieldNameToColumnNameMappings.size()];	// �W�v�֐��̕ʖ�

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
     * �t�B�[���h�ƃJ�������̃}�b�s���O�����擾����B<br/>
     * <br/>
     * �߂�l�̃��X�g�I�u�W�F�N�g�ɂ́A�o���[�I�u�W�F�N�g�ɒ�`����Ă���t�B�[���h���̏�� Map �`���Ŋi�[����Ă���B<br/>
     * key = fieldNames : value = �o���[�I�u�W�F�N�g�̃t�B�[���h��<br/>
     * key = columnNames : value = DB�̗�<br/>
     * key = daoAliases : value = annotation �Ŏw�肳�ꂽ�ADB�̗�̃e�[�u���ʖ�<br/>
     * key = functionAlias : value = annotation �Ŏw�肳�ꂽ�A�W�v�֐��̕ʖ�<br/>
     * <br/>
     * @return �t�B�[���h���̏�񂪊i�[���ꂽ���X�g�I�u�W�F�N�g
     */
    protected List<Map<String,String>> assignFieldNameToColumnNameMappings() {
        List<Map<String, String>> outputMappings = new ArrayList<>();

        // �o���[�I�u�W�F�N�g�Ɋi�[����Ă���t�B�[���h���ƁA�A�m�e�[�V�������擾����B
        // static �t�B�[���h��Agetter �̑��݂��Ȃ��t�B�[���h�͎擾�ΏۊO�ƂȂ�B
        LinkedHashMap<String, Annotation[]> allFields = ReflectionUtils.getAllFieldNamesAndAnnotationsByGetters(this.valueObjectClass);

        for (Map.Entry<String, Annotation[]> entry : allFields.entrySet()){
        	Map<String, String> columnInfo = new HashMap<>(); 

        	// �o���[�I�u�W�F�N�g�̃t�B�[���h����ݒ肷��B
        	columnInfo.put("fieldNames", entry.getKey());


        	// DB �t�B�[���h�ɊY������e�[�u���̕ʖ����i�[
        	// ���̒l�́A�o���[�I�u�W�F�N�g�̃t�B�[���h�� DaoAlias �A�m�e�[�V�������ݒ肳��Ă���ꍇ�Ɋi�[�����B
        	// ��̃e�[�u���� alias �́A�Ϗ���� DAO �����A����l�����A���̒l���ŗD��Ŏg�p�����B

        	// note
        	// Funtion �A�m�e�[�V�����ŁA�W�v�֐��̑Ώۃt�B�[���h�����w�肵���ꍇ�A���̃A�m�e�[�V�����̐ݒ�l
        	// ����̃e�[�u���ʖ��Ƃ��Ďg�p�����B

        	// note
        	// JoinDAO�AStarJoinDAO ���g�p���Ă���ꍇ�A������ DB �t�B�[���h�����݂���ꍇ������B
        	// ���w��̏ꍇ�A�ŏ��Ɍ������� DAO �̕ʖ����K�p����邪�A�擾�������e�[�u���̃t�B�[���h�ł͖���
        	// �ꍇ������B�@����āA�����̃t�B�[���h�����݂���ꍇ�ADaoAlias �A�m�e�[�V�����Ŗ����I�ɎQ�ƃe�[
        	// �u������肷��g�������]�܂����B
        	String daoAlias = getDaoAliasFromAnnotation(entry.getValue());
        	columnInfo.put("daoAliases", daoAlias);


        	// �W�v�֐���������擾
        	String functionInfo = getFunctionFromAnnotation(entry.getValue(), daoAlias);


        	// DB �̃t�B�[���h�����i�[
        	if (StringValidateUtil.isEmpty(functionInfo)){
            	// �W�v�֐����g�p����Ă��Ȃ��ꍇ�ADB �̃t�B�[���h���́A�o���[�I�u�W�F�N�g�̃t�B�[���h�����X�l�[�N�����ɕϊ�����
        		// �������ADB �t�B�[���h���Ƃ��Đݒ肷��B
        		columnInfo.put("columnNames", mappingPolicyFieldNameToColumnName(entry.getKey()));
        	} else {
            	// �W�v�֐����g�p����Ă���ꍇ�ADB �̃t�B�[���h���́A�W�v�֐��������ݒ肷��B
        		columnInfo.put("columnNames", functionInfo);
        		
            	// �W�v�֐��̕ʖ����i�[����B
              	// Function �A�m�e�[�V�������g�p����Ă���ꍇ�A�t�B�[���h�����W�v�֐��̕ʖ��Ƃ��Ďg�p�����B
               	columnInfo.put("functionAlias", entry.getKey());
        	}

            outputMappings.add(columnInfo);
            log.debug("Mapping " + entry.getKey() + " --> " + columnInfo.get("columnNames"));
        }

        return outputMappings;
    }



    /**
     * �o���[�I�u�W�F�N�g�̃t�B�[���h������ADB �񖼁i�܂��́A�֐�������j���擾����B<br/>
     * <br/>
     * @param fieldName �o���[�I�u�W�F�N�g�̃t�B�[���h��
     * @return�@DB �񖼁A�܂��́A�֐�������
     */
    protected String findColumnName(String fieldName) {
    	return this.fieldToColumnMappingCache.get(fieldName);
    }

    
    
    protected void addGroypByClauses(List<DAOCriteriaGroupByClause> groupByClauses, StringBuilder sql) {
    	// GROUP BY �ݒ肪�����ꍇ�A�����������Ȃ��B
    	if (groupByClauses == null || groupByClauses.size() == 0) {
    		return;
    	}

   		// GROUP BY �吶��
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
     * �A�m�e�[�V������񂩂�ADB ��̃e�[�u���ʖ����擾����B<br/>
     * �����A�Y������A�m�e�[�V���������݂��Ȃ��ꍇ�� null �𕜋A����B<br/>
     * <br/>
     * @param annotations �t�B�[���h�ɐݒ肳��Ă���A�m�e�[�V����
     * @return �A�m�e�[�V�����Őݒ肳�ꂽ DB ���ð��ٕʖ�
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
     * �A�m�e�[�V������񂩂�ADB ��̏W�v�֐������擾����B<br/>
     * <br/>
     * �W�v�֐�������́A�ȉ��̃��[���Ő��������B
     * <ul>
     * <li>�W�v�֐����ΏۂƂ����̃e�[�u���ʖ��́ADaoAlias �A�m�e�[�V�������ݒ肳��Ă���΁A���̒l��D�悷��B</li>
     * <li>DaoAlias �A�m�e�[�V���������ݒ�̏ꍇ�A��̃e�[�u���ʖ��͈Ϗ���� DAO �Ɉˑ�����B</li>
     * </ul>
     * <br/>
     * @param annotations �t�B�[���h�ɐݒ肳��Ă���A�m�e�[�V����
     * @param daoAlias DaoAlias �A�m�e�[�V�����Őݒ肳�ꂽ�A��̃e�[�u���ʖ�
     * @return �W�v�֐��Ɋւ���z��B�@�֐������� �i Function �A�m�e�[�V�������ݒ莞�� null�j
     */
    protected String getFunctionFromAnnotation(Annotation[] annotations, String daoAlias){

    	for (Annotation annotation : annotations){
    		if (annotation instanceof Function){

    			// �A�m�e�[�V��������A�֐��̑Ώۗ񖼂��擾����B
    			String columnName = ((Function) annotation).columnName();

    			if (!"*".equals(columnName)){
    				// �A�m�e�[�V�����Ŏw�肳�ꂽ�񖼂��A"*" �ȊO�̏ꍇ�A�Ϗ��� DAO �̋@�\���g�p����
    				// �񖼂𐶐�����B
    				// ��̃e�[�u���ʖ��́ADaoAlias �A�m�e�[�V�������g�p����Ă���΁A���̒l���g�p����B
    				// ���g�p�̏ꍇ�́A�Ϗ���� DAO �Ɉˑ�����B
    				columnName = lookupAliasDotColumnName(columnName, daoAlias, "");
    			}
    			// �A�m�e�[�V�����ݒ肳��Ă���֐����ƁA�擾�����񖼂��g�p���āA�֐�������𐶐�����B
    			return ((Function) annotation).functionName().toFunctionString(columnName);
    		}
    	}
    	return null;
    }

    
    
    @SuppressWarnings("deprecation")
	protected int getRowCountMatchingFilterInternal(DAOCriteria criteria) {
        ensureInitialization();
        JdbcTemplate template = new JdbcTemplate(getDataSource());

        // �W�v�֐��̏ꍇ�A�C�����C���r���[�Ƃ��Ď��s���ĊY���������擾����B
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
     * �t�B�[���h���̕ʖ����擾����B<br/>
     * �Ϗ��悪 ReflectingDAO �̏ꍇ�A�t�B�[���h�����X�l�[�N�����ɕϊ����������񂪐ݒ肳���B<br/>
     * �����AthisAlias �̊Y���ӏ��Ńe�[�u���ʖ����ݒ肳��Ă���ꍇ�A�����t�������������ݒ肷��B<br/>
     * �Ϗ��悪 JoinDAO�AStarJoinDAO �̏ꍇ�ADAO ���̃e�[�u���ʖ� + �t�B�[���h�����X�l�[�N�����ɕϊ�
     * ���������񂪐ݒ肳���B�@�i�����̃t�B�[���h�����݂���ꍇ�A�j<br/>
     * �����AthisAlias �̊Y���ӏ��Ńe�[�u���ʖ����ݒ肳��Ă���ꍇ�ADAO ���̃e�[�u���ʖ������D�悳���B<br/>
     * ����ɁAthisColumnAliases�@�ɁA��ʖ����ݒ肳��Ă���ꍇ�A���̒l���ŗD��ɂ���B<br/>
     * <br/>
     * @param thisAlias �t�B�[���h�� DaoAlias �A�m�e�[�V�����Őݒ肳�ꂽ�e�[�u���ʖ��̔z��
     * @param forFieldNames�@�t�B�[���h���̔z��
     * @param thisFunctionAlias �W�v�֐��g�p���̕ʖ�
     * @return�@�t�B�[���h���̕ʖ�
     */
    private String[] getFieldAliases(String[] thisAlias, String[] forFieldNames, String[] thisFunctionAlias) {

    	String out[] = new String[forFieldNames.length];

        for (int n = 0; n < forFieldNames.length; n++) {

        	if (StringValidateUtil.isEmpty(thisFunctionAlias[n])) {
            	// �A�m�e�[�V�����ŏW�v�֐��̕ʖ������w��̏ꍇ�A�Ϗ���� DAO ���g�p���ė�ʖ���g�ݗ��Ă�B
        		// �i�֐����g�p���Ȃ��A�ʏ�Q�ƂƂ݂Ȃ��B�j

        		// �Ϗ���� DAO ���g�p���ăt�B�[���h���̕ʖ��𐶐�����B
        		// �����A�t�B�[���h�� DaoAlias �A�m�e�[�V�����Ńe�[�u���ʖ����ݒ肳��Ă���ꍇ�A
        		// ���̒l���e�[�u���ʖ��Ƃ��ăt�B�[���h�ʖ������H����B
        		out[n] = lookupLabelledName(forFieldNames[n], thisAlias[n], "");
        		if (out[n].length() > 30) {
        			out[n] = out[n].substring(0, 30);
        		}
        	} else {
        		// �֐��g�p���́A�w�肳�ꂽ�ʖ���ݒ肷��B
        		out[n] = thisFunctionAlias[n];
        	}

            // ���ɓ����� alias ���o�^����Ă���ꍇ�A�d�����Ȃ����̂ɕύX����B
            // �ʏ�A�d���͔������Ȃ����A�e�[�u���� + �t�B�[���h�����X�l�[�N�����ɕϊ������ہA
            // 30 �����𒴂���ꍇ������B�@���̏ꍇ�A30�@�����ɐ؂�l�߂���̂ŁA�d������
            // �ꍇ������B
            DAOUtils.dupliateAliasRename(out, n);
        }
        return out;
    }

}
