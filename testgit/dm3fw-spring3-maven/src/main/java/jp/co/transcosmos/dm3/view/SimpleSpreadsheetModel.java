/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.form.PagingListForm;
import jp.co.transcosmos.dm3.utils.ReflectionUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Implementation of the SpreadsheetModel interface. This class will use simple
 * java reflection on the items in the Model, and attempt to create columns from the
 * member variables in the valueobjects inside the Model's list.
 * <p>
 * It will attempt to locate the rowset within the model using the first Object array,
 * Collection or PagingListForm it finds in the Model when the keys are alphabetically 
 * sorted. 
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: SimpleSpreadsheetModel.java,v 1.2 2007/05/31 03:46:47 rick Exp $
 */
public class SimpleSpreadsheetModel implements SpreadsheetModel {
    private static final Log log = LogFactory.getLog(SimpleSpreadsheetModel.class);

// 2013.04.19 H.Mizuno �@�\�g���ׁ̈A�X�R�[�v��ύX�@Start
//    private String filename;
    protected String filename;
// 2013.04.19 H.Mizuno �@�\�g���ׁ̈A�X�R�[�v��ύX End

    public SimpleSpreadsheetModel() {}
    
    public SimpleSpreadsheetModel(String filename) {
        this();
        this.filename = filename;
    }

    public String getFilename(Map<?,?> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return (this.filename != null) && !this.filename.equals("") ? this.filename : null;
    }

    public Object[] getHeaders(Object rows[], Map<?,?> model, 
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        if ((rows != null) && (rows.length > 0)) {
// 2015.03.05 H.Mizuno JoinResult �̏ꍇ�A����ɏo�͂ł��Ȃ����ɑΉ� start
        	if (rows[0] instanceof JoinResult){
            	return getHeadersFromJoinResult((JoinResult)rows[0]);
        	}
// 2015.03.05 H.Mizuno JoinResult �̏ꍇ�A����ɏo�͂ł��Ȃ����ɑΉ� end
            return ReflectionUtils.getAllFieldNamesByGetters(rows[0].getClass());
        } else {
            return null;
        }
    }

// 2015.03.05 H.Mizuno JoinResult �̏ꍇ�A����ɏo�͂ł��Ȃ����ɑΉ� start
    /**
     * JoinResult �p�̃w�b�_�擾����<br/>
     * objectName + "." + fieldName ���w�b�_���Ƃ��ĕ��A����B<br/>
     * <br/>
     * @param oneRow �w�b�_�����擾���� JoinResult
     * @return
     */
    protected Object[] getHeadersFromJoinResult(JoinResult oneRow){
    	
        // �߂�l�p�̃��X�g�I�u�W�F�N�g���쐬
        List<Object> result = new ArrayList<>();
    	
        // JoinResult �� items �Ɋi�[����Ă���I�u�W�F�N�g���A���[�v���� Value �I�u�W�F�N�g���擾����B
        for (Object oneValueObject : oneRow.getItems().values()){

        	// JoinResultMap �� 10 �� Value �I�u�W�F�N�g������I�ɕێ����Ă���Avalues() ��
        	// �����S�ẴI�u�W�F�N�g�𕜋A����B�@���g�p�ȏꍇ������̂ŁAnull ���肵�Ă��珈������B
        	if (oneValueObject != null) {

        		// Value �I�u�W�F�N�g�ɑ��݂���t�B�[���h�����擾����B
        		String[] fieldNames = ReflectionUtils.getAllFieldNamesByGetters(oneValueObject.getClass());
        
        		// �擾�����t�B�[���h���ɁAValue �I�u�W�F�N�g�̃I�u�W�F�N�g����t�����āA�߂�l�p���X�g�ɒǉ�����B
        		for (int i=0; i<fieldNames.length ;++i){
        			result.add(oneValueObject.getClass().getSimpleName() + "." + fieldNames[i]);
        		}
        	}
        }

        return result.toArray();
    }
// 2015.03.05 H.Mizuno JoinResult �̏ꍇ�A����ɏo�͂ł��Ȃ����ɑΉ� end
    
    public Object[] getOneRow(Object rows[], int index, Map<?,?> model, 
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object thisOne = rows[index];
        if (thisOne == null) {
            return null;
        }

// 2015.03.05 H.Mizuno JoinResult �̏ꍇ�A����ɏo�͂ł��Ȃ����ɑΉ� start
        if (thisOne instanceof JoinResult){
        	return getOneRowFromJoinResult((JoinResult)thisOne);
        }
// 2015.03.05 H.Mizuno JoinResult �̏ꍇ�A����ɏo�͂ł��Ȃ����ɑΉ� end

        String fieldNames[] = ReflectionUtils.getAllFieldNamesByGetters(
                thisOne.getClass());
        Object result[] = new Object[fieldNames.length];
        for (int n = 0; n < result.length; n++) {
            try {
                result[n] = ReflectionUtils.getFieldValueByGetter(
                        thisOne, fieldNames[n]);
            } catch (Throwable err) {
                throw new RuntimeException("Error getting field: " + fieldNames[n] + 
                        " on row " + index);
            }
        }
        return result;
    }

    
// 2015.03.05 H.Mizuno JoinResult �̏ꍇ�A����ɏo�͂ł��Ȃ����ɑΉ� start
    /**
     * JoinResult �̏ꍇ�̂P�s�f�[�^�擾����<br/>
     * <br/>
     * @param thisOne CSV �P�s�ɊY������ JoinResult
     * @return�@�擾�����t�B�[���h�l���i�[���ꂽ�I�u�W�F�N�g�̔z��
     * @exception Exception
     */
    protected Object[] getOneRowFromJoinResult(JoinResult thisOne) throws Exception {

        // �߂�l�p�̃��X�g�I�u�W�F�N�g���쐬
        List<Object> result = new ArrayList<>();

        // JoinResult �� items �Ɋi�[����Ă���I�u�W�F�N�g���A���[�v���� Value �I�u�W�F�N�g���擾����B
        for (Object oneValueObject : thisOne.getItems().values()){

        	// JoinResultMap �� 10 �� Value �I�u�W�F�N�g������I�ɕێ����Ă���Avalues() ��
        	// �����S�ẴI�u�W�F�N�g�𕜋A����B�@���g�p�ȏꍇ������̂ŁAnull ���肵�Ă��珈������B
        	if (oneValueObject != null) {

            	// Value �I�u�W�F�N�g����t�B�[���h�����擾����B
            	String fieldNames[]
            			= ReflectionUtils.getAllFieldNamesByGetters(oneValueObject.getClass());

            	// �t�B�[���h�����Agetter ���o�R����CSV �o�͒l���擾����B
       			for (String fieldName : fieldNames){
       				try {
       					result.add(ReflectionUtils.getFieldValueByGetter(oneValueObject, fieldName));
       				} catch (Throwable err) {
       					// getter ����������Ă��Ȃ��ꍇ�A��O���X���[����B
       					// getter �������ꍇ�A�x���ł��ǂ������m��Ȃ����A����Ƃ̌݊���D�悷��B
       					log.error("Error getting field: " + fieldName);
       					throw err;
       				}
       			}
        	}
        }

        return result.toArray();
    }
// 2015.03.05 H.Mizuno JoinResult �̏ꍇ�A����ɏo�͂ł��Ȃ����ɑΉ� end
    
    
    
    public Object[] getRows(Map<?,?> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Object keys[] = model.keySet().toArray();
        Arrays.sort(keys);
        for (int n = 0; n < keys.length; n++) {
            Object value = model.get(keys[n]);
            if (value instanceof Object[]) {
                log.info("Using model item " + keys[n]);
                return (Object[]) value;
            } else if (value instanceof Collection) {
                log.info("Using model item " + keys[n]);
                return ((Collection<?>) value).toArray();
            } else if (value instanceof PagingListForm) {
                log.info("Using model item " + keys[n]);
                return ((PagingListForm<?>) value).getVisibleRows().toArray();
            }
        }
        return null;
    }

// 2013.12.20 H.Mizuno�@CSV �o�͎��̕����R�[�h��ݒ�\�ɂ���׃��\�b�h��ǉ�  start
	@Override
	public String getEncoding() {
		// CSVView �́A���Ɏw�肪������� HttpServletResponse �̕����R�[�h���g�p����B
		// ����������擾�o���Ȃ��ꍇ�AWindows-31J�@���g�p���Ă���B
		// ���̗���͊����V�X�e���̌݊����̖�肩��ύX���鎖�͂ł��Ȃ��B
		return null;
	}
// 2013.12.20 H.Mizuno�@CSV �o�͎��̕����R�[�h��ݒ�\�ɂ���׃��\�b�h��ǉ�  end

}
