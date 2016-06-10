package jp.co.transcosmos.dm3.dao;


/**
 * GROUP BY �吶���p�I�u�W�F�N�g
 * 
 */
public class DAOCriteriaGroupByClause {

	/** �e�[�u���̕ʖ� */
    private String daoAlias;
    /** �t�B�[���h�� */
    private String fieldName;

    
    public DAOCriteriaGroupByClause() {
    }

    
    public DAOCriteriaGroupByClause(String fieldName) {
    	this();
    	this.fieldName = fieldName;
    }

    
    public DAOCriteriaGroupByClause(String daoAlias, String fieldName) {
    	this(fieldName);
    	this.daoAlias = daoAlias;
    }
    
    
    public String getDaoAlias() {
		return daoAlias;
	}

    public void setDaoAlias(String daoAlias) {
		this.daoAlias = daoAlias;
	}

    public String getFieldName() {
		return fieldName;
	}

    public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}


    public String toString() {
        return "[DAOCriteriaGroupByClause: fieldName=" + this.fieldName +
                (this.daoAlias != null ? " (dao:" + this.daoAlias + ")" : "") + "]";
    }

}
