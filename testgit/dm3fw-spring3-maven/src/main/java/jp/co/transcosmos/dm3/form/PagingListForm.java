/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.form;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.form.annotation.UsePagingParam;

/**
 * A simple form that holds paging details for a list of results to be displayed. Forms 
 * that want to show paging navigation and query parameters should subclass this class and 
 * just add their own parameters. 
 * <p>
 * See the advanced search tutorial for further explanations of this class.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: PagingListForm.java,v 1.2 2007/05/31 10:18:26 rick Exp $
 */
public class PagingListForm<E> {
	
    private static final int DEFAULT_ROWS_PER_PAGE = 20;
    private static final int DEFAULT_MOVE_RANGE = 5;
    
    private int rowsPerPage = DEFAULT_ROWS_PER_PAGE;
    private int visibleNavigationPageCount = DEFAULT_MOVE_RANGE;
    private int selectedPage = 1;


    private List<E> rows;

    // 2013.10.02 H.Mizuno �y�[�W�������Ƀp�����[�^�������n���@�\��ǉ� start
    // �ʃt�H�[���̒l�������n���K�v������ꍇ�A���̃v���p�e�B�� Form �I�u�W�F�N�g���i�[����B
    // �t���[�����[�N�́A���̃v���p�e�B�ɃI�u�W�F�N�g���i�[����Ă���ꍇ�AUsePageingParam
    // �A�m�e�[�V�������t�^����Ă���t�B�[���h���擾���ăp�����[�^������𐶐�����B
    private Object childForm;

    public Object getChildForm() {
		return childForm;
	}

	public void setChildForm(Object childForm) {
		this.childForm = childForm;
	}
    // 2013.10.02 H.Mizuno �y�[�W�������Ƀp�����[�^�������n���@�\��ǉ� end

	public int getRowsPerPage() {
        return rowsPerPage;
    }

    public int getVisibleNavigationPageCount() {
        return visibleNavigationPageCount;
    }

    public int getSelectedPage() {
        return selectedPage;
    }

    public List<E> getRows() {
        return rows;
    }

    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public void setVisibleNavigationPageCount(int visibleNavigationPageCount) {
        this.visibleNavigationPageCount = visibleNavigationPageCount;
    }

    public void setSelectedPage(int selectedPage) {
        this.selectedPage = selectedPage;
    }

    public void setSelectedPage(String selectedPage) {
        if (selectedPage != null) try {
            this.selectedPage = Integer.parseInt(selectedPage);
        } catch (NumberFormatException err) {}
    }

    public void setRows(List<E> rows) {
        this.rows = rows;
        if (this.rows == null) {
            this.selectedPage = 1;
        } else {
            this.selectedPage = Math.min(this.selectedPage, getMaxPages());
        }
    }

    public int getStartIndex() {
        int start = Math.max(0, this.rowsPerPage * (this.selectedPage - 1));
        if (this.rows != null) {
            start = Math.min(start, this.rows.size());
        }
        return start;
    }

    public int getEndIndex() {
        int end = Math.max(0, this.rowsPerPage * this.selectedPage);
        if (this.rows != null) {
            end = Math.min(end, this.rows.size());
        }
        return end;
    }

    public List<E> getVisibleRows() {
        if (this.rows == null) {
            return null;
        } else {
            return this.rows.subList(getStartIndex(), getEndIndex());
        }
    }
    
    public int getMaxPages() {
        return (int) Math.floor((this.rows.size() - 1) / this.rowsPerPage) + 1;
    }
    
    public int getMaxRows() {
        return this.rows.size();
    }
    
    public int getLeftNavigationPageNo() {
 // 2014.12.04 H.Mizuno �y�[�W�\���ʒu�Z�o�̌����C�� start
 /*
    	int narrowerWidth = (int) Math.floor((this.visibleNavigationPageCount - 1) / 2);
        int leftPage = this.selectedPage - narrowerWidth;
        if (this.visibleNavigationPageCount % 2 == 0) { // odd case
            leftPage = leftPage - (1 - (this.selectedPage % 2));
        }
        int maxPages = getMaxPages();
        return new Integer(Math.max(1, Math.min(leftPage, 
                maxPages - this.visibleNavigationPageCount)));
*/
    	// �\���y�[�W�ʒu�𒆐S�ɂ���ׁA���y�[�W�O����\�����邩���擾
    	int narrowerWidth = (int) Math.floor(this.visibleNavigationPageCount / 2);

    	//�@��L���ʂ���A�\���J�n�ʒu�ƂȂ�y�[�W�ʒu���擾
    	int leftPage = this.selectedPage - narrowerWidth;
    	
        int maxPages = getMaxPages();
        return new Integer(Math.max(1, Math.min(leftPage, 
                maxPages - this.visibleNavigationPageCount)));
// 2014.12.04 H.Mizuno �y�[�W�\���ʒu�Z�o�̌����C�� end
    }
    
    public int getRightNavigationPageNo() {
        return new Integer(Math.min(getLeftNavigationPageNo() + 
                this.visibleNavigationPageCount, getMaxPages()));
    }
    
    public DAOCriteria buildCriteria() {
        DAOCriteria criteria = new DAOCriteria();
        criteria.setFragmentOffset(getStartIndex());
        criteria.setFragmentLimit(getEndIndex());
        return criteria;
    }


// 2013.09.20 H.Mizuno �y�[�W�������Ƀp�����[�^�������n���@�\��ǉ� start
	/**
	 * �y�[�W�J�ڂ���ۂ̃p�����[�^������𐶐�<br>
	 * �t�H�[���N���X����AUsePageingParam�@�A�m�e�[�V�������t�^���ꂽ�t�B�[���h���������A<br>
	 * URL�p�����[�^��������쐬����B<br>
	 * childForm �v���p�e�B�� Form ���ݒ肳��Ă���ꍇ�͂��̃I�u�W�F�N�g�������o�ΏۂƂ���B<br>
	 * ���̕�����̓y�[�W�J�ڎ��̃p�����[�^�Ƃ��Ďg�p�����B�@�p�����[�^�l�́AUTF-8 ��<br>
	 * URL �G���R�[�h�����B<br>
	 * <br>
	 * @param encode �p�����[�^�l�� URL �G���R�[�h���鎞�̕����R�[�h������
	 * @param userNullValue true �̏ꍇ�A�l����ł� Map �� Key �𐶐�����B
	 * @return URL �p�����[�^������
	 */
    public String getOptionParams() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, UnsupportedEncodingException {
    	return getOptionParams("utf-8", false);
    }

    public String getOptionParams(boolean useNullValue) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, UnsupportedEncodingException {
    	return getOptionParams("utf-8", useNullValue);
    }

    public String getOptionParams(String encode) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, UnsupportedEncodingException {
    	return getOptionParams(encode, false);
    }


    public String getOptionParams(String encode, boolean useNullValue) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, UnsupportedEncodingException {

    	// �p�����[�^���͗p�r����ł���A�Z���̂��ǂ��Ȃ��̂ŁA�Ƃ肠���� 200 �o�C�g���m�ہB
    	StringBuffer buff = new StringBuffer(200);
    	
    	// �p�����[�^���i�[���� Map �I�u�W�F�N�g���쐬
    	Map<String, String[]> paramMap = new HashMap<String, String[]>();

    	// �t�H�[������t�B�[���h���擾�i���g�̃t�H�[���j
    	createParamMap(paramMap, this, useNullValue);
    	
    	// �t�H�[������t�B�[���h���擾�i�q���̃t�H�[���j
    	if (childForm != null){
    		createParamMap(paramMap, childForm, useNullValue);
    	}

    	// Map ��ǂݍ��݁AQueryString ���o�͂���B
    	for (Map.Entry<String, String[]> entry : paramMap.entrySet()){
    		
    		// �ʏ�A�y�[�W�ԍ��̃p�����[�^���K���t���̂ŁA& ����n�܂� QueryString �𕜋A����B
    		for (String value : entry.getValue()){
    			buff.append("&" + entry.getKey() + "=" + URLEncoder.encode(value,encode));

    		}
    	}

    	return buff.toString();
    }


    
	/**
	 * �y�[�W�J�ڂ���ۂ̃p�����[�^ Map �I�u�W�F�N�g���擾����B<br>
	 * �t�H�[���N���X����AUsePageingParam�@�A�m�e�[�V�������t�^���ꂽ�t�B�[���h���������A<br>
	 * URL�p�����[�^��������쐬����B<br>
	 * childForm �v���p�e�B�� Form ���ݒ肳��Ă���ꍇ�͂��̃I�u�W�F�N�g�������o�ΏۂƂ���B<br>
	 * <br>
	 * @param encode �p�����[�^�l�� URL �G���R�[�h���鎞�̕����R�[�h������
	 * @param userNullValue true �̏ꍇ�A�l����ł� Map �� Key �𐶐�����B
	 * @return URL �p�����[�^������
	 */
    public Map<String, String[]> getParameterMap() throws IllegalArgumentException, IllegalAccessException {
    	return getParameterMap(false);
    }


    public Map<String, String[]> getParameterMap(boolean useNullValue) throws IllegalArgumentException, IllegalAccessException {
    	// �p�����[�^���i�[���� Map �I�u�W�F�N�g���쐬
    	Map<String, String[]> paramMap = new HashMap<String, String[]>();

    	// �t�H�[������t�B�[���h���擾�i���g�̃t�H�[���j
    	createParamMap(paramMap, this, useNullValue);
    	
    	// �t�H�[������t�B�[���h���擾�i�q���̃t�H�[���j
    	if (childForm != null){
    		createParamMap(paramMap, childForm, useNullValue);
    	}

    	return paramMap;
    }

// 2015.06.02 H.Mizuno �e�N���X�̃A�m�e�[�V�������Q�Ƃ���l�Ɋg�� start
    private void createParamMap(Map<String, String[]> paramMap, Object formObj, boolean useNullValue) throws IllegalArgumentException, IllegalAccessException {

    	Class<?> curCls = formObj.getClass();
    	
    	while (curCls != null){

    		// �w�肳�ꂽ�N���X�̃p�����[�^�}�b�v���쐬
    		createCurParamMap(paramMap, curCls, formObj, useNullValue);

    		// �e�N���X���擾���A���݂���ꍇ�͂��̐e�N���X������p�����[�^���擾����B
    		// �e�N���X���ɃA�m�e�[�V��������`���ꂽ�����̃t�B�[���h�����݂��鎖�͑z�肵�Ă��Ȃ��B
    		// �������݂���ꍇ�͐e�N���X���̒l���D�悳���B
    		curCls = curCls.getSuperclass();
    	}

    }
// 2015.06.02 H.Mizuno �e�N���X�̃A�m�e�[�V�������Q�Ƃ���l�Ɋg�� end
    

// 2015.06.02 H.Mizuno �e�N���X�̃A�m�e�[�V�������Q�Ƃ���l�Ɋg�� start
//    private void createParamMap(Map<String, String[]> paramMap, Object formObj, boolean useNullValue) throws IllegalArgumentException, IllegalAccessException {
//    	// �w�肳�ꂽ�t�H�[���N���X�̃t�B�[���h���擾����B
//    	Field[] fields = formObj.getClass().getDeclaredFields();
    private void createCurParamMap(Map<String, String[]> paramMap, Class<?> targetCls, Object formObj, boolean useNullValue) throws IllegalArgumentException, IllegalAccessException {

    	// �w�肳�ꂽ�t�H�[���N���X�̃t�B�[���h���擾����B
    	Field[] fields = targetCls.getDeclaredFields();
// 2015.06.02 H.Mizuno �e�N���X�̃A�m�e�[�V�������Q�Ƃ���l�Ɋg�� end

    	// UsePageingParam �A�m�e�[�V�������t�^����Ă���t�B�[���h���擾���� Map �Ɋi�[����B
    	for (Field field : fields){
    		field.setAccessible(true);
    		if (field.getAnnotation(UsePagingParam.class) != null){

    			Object obj = field.get(formObj);
    			if (obj != null){
        			if (obj instanceof Object[]){

    					// ��Ɨp ArrayList
    					List<String> wkParamList = new ArrayList<String>();

        				// �p�����[�^���z��̏ꍇ
        				for (Object objOne : (Object[])obj){

        					if (objOne instanceof String){
        						if (((String) objOne).length()>0) {
        							// ���ۂɒl���i�[����Ă���ꍇ�AArrayList �ɒǉ�����B
        							wkParamList.add((String)objOne);
        						}
        					}
        				}

        				if (!wkParamList.isEmpty()){
            				// �l���i�[����Ă���΁AMap �Ɋi�[����B
// 2015.06.02 H.Mizuno Form �Ŕz����g�p���Ă��鎞�ɗ�O��������������C�� start
//    						paramMap.put(field.getName(), (String[])wkParamList.toArray());
    						paramMap.put(field.getName(), (String[])wkParamList.toArray(new String[wkParamList.size()]));
// 2015.06.02 H.Mizuno Form �Ŕz����g�p���Ă��鎞�ɗ�O��������������C�� end

        				} else if (useNullValue){
							// useNullValue ���ݒ肳��Ă���ꍇ�A�l�� null �ł��󕶎���� Map �Ɋi�[����B
    						paramMap.put(field.getName(), new String[]{""});

        				}

        			} else {
            			// �p�����[�^���z��ȊO�̏ꍇ
    					if (obj instanceof String){
    						if (((String) obj).length()>0) {
    							// �l���ݒ肳��Ă���t�B�[���h�̏ꍇ�A Map �Ɋi�[����B
        						paramMap.put(field.getName(), new String[]{(String) obj});

    						} else if (useNullValue) {
    							// useNullValue ���ݒ肳��Ă���ꍇ�A�l�� null �ł��󕶎���� Map �Ɋi�[����B
        						paramMap.put(field.getName(), new String[]{""});

    						}
    					}
        			}
    			}
    			
    		}
    		field.setAccessible(false);
    	}
    }
// 2013.09.20 H.Mizuno �y�[�W�������Ƀp�����[�^�������n���@�\��ǉ� end

}

