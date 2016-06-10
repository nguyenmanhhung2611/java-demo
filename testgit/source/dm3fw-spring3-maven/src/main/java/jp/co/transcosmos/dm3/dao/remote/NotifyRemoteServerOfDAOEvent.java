package jp.co.transcosmos.dm3.dao.remote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.DAODeleteListener;
import jp.co.transcosmos.dm3.dao.DAOInsertListener;
import jp.co.transcosmos.dm3.dao.DAOUpdateListener;
import jp.co.transcosmos.dm3.dao.HasPkFields;
import jp.co.transcosmos.dm3.dao.HasValueObjectClassName;
import jp.co.transcosmos.dm3.dao.UpdateExpression;

public class NotifyRemoteServerOfDAOEvent<E> implements DAOInsertListener<E>,
        DAOUpdateListener<E>, DAODeleteListener<E>, HasValueObjectClassName, HasPkFields {

    private String pkFields[];
    private Class<? extends E> valueObjectClass;
    
    public List<String> getPkFields() {
        return this.pkFields == null ? null : Arrays.asList(this.pkFields);
    }

    public void setPkFields(List<String> fieldNames) {
        if (fieldNames == null) {
            this.pkFields = null;
        } else {
            List<String> out = new ArrayList<String>(); 
            for (Iterator<String> i = fieldNames.iterator(); i.hasNext(); ) {
                StringTokenizer st = new StringTokenizer(i.next(), ",");
                while (st.hasMoreTokens()) {
                    out.add(st.nextToken());
                }
            }
            this.pkFields = (String []) out.toArray(new String[out.size()]);
        }
    }

    public String getValueObjectClassName() {
        return this.valueObjectClass == null ? null : this.valueObjectClass.getName();
    }

    @SuppressWarnings("unchecked")
    public void setValueObjectClassName(String valueObjectClassName) {
        try {
            this.valueObjectClass = (Class<? extends E>) Class.forName(valueObjectClassName);
        } catch (ClassNotFoundException err) {
            throw new RuntimeException("Error loading DAO class: " + valueObjectClassName, err);
        }
    }

    public void postInsert(E[] item) {
    }

    public void postUpdate(E[] item) {
    }

    public void postDelete(E item) {
    }

    public void postDeleteByFilter(DAOCriteria criteria) {
    }

    public void postDeleteByPK(Object[] pks) {        
    }
    
    public void postUpdateByCriteria(DAOCriteria criteria, UpdateExpression[] setClauses) {
    }

    //////////// Below is pre operation methods, unused
    
    public boolean preInsert(E[] item) {        
        return true;
    }
    public boolean preUpdate(E[] item) {
        return true;
    }
    public boolean preDelete(E item) {        
        return true;
    }
    public boolean preDeleteByFilter(DAOCriteria criteria) {        
        return true;
    }
    public boolean preDeleteByPK(Object[] pks) {        
        return true;
    }

    public boolean preUpdateByCriteria(DAOCriteria criteria, UpdateExpression[] setClauses) {
        return true;
    }
}
