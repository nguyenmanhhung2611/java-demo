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

    // 2013.10.02 H.Mizuno ページ処理時にパラメータを引き渡す機能を追加 start
    // 別フォームの値も引き渡す必要がある場合、このプロパティに Form オブジェクトを格納する。
    // フレームワークは、このプロパティにオブジェクトが格納されている場合、UsePageingParam
    // アノテーションが付与されているフィールドを取得してパラメータ文字列を生成する。
    private Object childForm;

    public Object getChildForm() {
		return childForm;
	}

	public void setChildForm(Object childForm) {
		this.childForm = childForm;
	}
    // 2013.10.02 H.Mizuno ページ処理時にパラメータを引き渡す機能を追加 end

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
 // 2014.12.04 H.Mizuno ページ表示位置算出の誤りを修正 start
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
    	// 表示ページ位置を中心にする為、何ページ前から表示するかを取得
    	int narrowerWidth = (int) Math.floor(this.visibleNavigationPageCount / 2);

    	//　上記結果から、表示開始位置となるページ位置を取得
    	int leftPage = this.selectedPage - narrowerWidth;
    	
        int maxPages = getMaxPages();
        return new Integer(Math.max(1, Math.min(leftPage, 
                maxPages - this.visibleNavigationPageCount)));
// 2014.12.04 H.Mizuno ページ表示位置算出の誤りを修正 end
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


// 2013.09.20 H.Mizuno ページ処理時にパラメータを引き渡す機能を追加 start
	/**
	 * ページ遷移する際のパラメータ文字列を生成<br>
	 * フォームクラスから、UsePageingParam　アノテーションが付与されたフィールドを検索し、<br>
	 * URLパラメータ文字列を作成する。<br>
	 * childForm プロパティに Form が設定されている場合はそのオブジェクト内も抽出対象とする。<br>
	 * この文字列はページ遷移時のパラメータとして使用される。　パラメータ値は、UTF-8 で<br>
	 * URL エンコードされる。<br>
	 * <br>
	 * @param encode パラメータ値を URL エンコードする時の文字コード文字列
	 * @param userNullValue true の場合、値が空でも Map の Key を生成する。
	 * @return URL パラメータ文字列
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

    	// パラメータ長は用途次第であり、短いのも良くないので、とりあえず 200 バイト分確保。
    	StringBuffer buff = new StringBuffer(200);
    	
    	// パラメータを格納した Map オブジェクトを作成
    	Map<String, String[]> paramMap = new HashMap<String, String[]>();

    	// フォームからフィールドを取得（自身のフォーム）
    	createParamMap(paramMap, this, useNullValue);
    	
    	// フォームからフィールドを取得（子供のフォーム）
    	if (childForm != null){
    		createParamMap(paramMap, childForm, useNullValue);
    	}

    	// Map を読み込み、QueryString を出力する。
    	for (Map.Entry<String, String[]> entry : paramMap.entrySet()){
    		
    		// 通常、ページ番号のパラメータが必ず付くので、& から始まる QueryString を復帰する。
    		for (String value : entry.getValue()){
    			buff.append("&" + entry.getKey() + "=" + URLEncoder.encode(value,encode));

    		}
    	}

    	return buff.toString();
    }


    
	/**
	 * ページ遷移する際のパラメータ Map オブジェクトを取得する。<br>
	 * フォームクラスから、UsePageingParam　アノテーションが付与されたフィールドを検索し、<br>
	 * URLパラメータ文字列を作成する。<br>
	 * childForm プロパティに Form が設定されている場合はそのオブジェクト内も抽出対象とする。<br>
	 * <br>
	 * @param encode パラメータ値を URL エンコードする時の文字コード文字列
	 * @param userNullValue true の場合、値が空でも Map の Key を生成する。
	 * @return URL パラメータ文字列
	 */
    public Map<String, String[]> getParameterMap() throws IllegalArgumentException, IllegalAccessException {
    	return getParameterMap(false);
    }


    public Map<String, String[]> getParameterMap(boolean useNullValue) throws IllegalArgumentException, IllegalAccessException {
    	// パラメータを格納した Map オブジェクトを作成
    	Map<String, String[]> paramMap = new HashMap<String, String[]>();

    	// フォームからフィールドを取得（自身のフォーム）
    	createParamMap(paramMap, this, useNullValue);
    	
    	// フォームからフィールドを取得（子供のフォーム）
    	if (childForm != null){
    		createParamMap(paramMap, childForm, useNullValue);
    	}

    	return paramMap;
    }

// 2015.06.02 H.Mizuno 親クラスのアノテーションも参照する様に拡張 start
    private void createParamMap(Map<String, String[]> paramMap, Object formObj, boolean useNullValue) throws IllegalArgumentException, IllegalAccessException {

    	Class<?> curCls = formObj.getClass();
    	
    	while (curCls != null){

    		// 指定されたクラスのパラメータマップを作成
    		createCurParamMap(paramMap, curCls, formObj, useNullValue);

    		// 親クラスを取得し、存在する場合はその親クラスからもパラメータを取得する。
    		// 親クラス側にアノテーションが定義された同名のフィールドが存在する事は想定していない。
    		// もし存在する場合は親クラス側の値が優先される。
    		curCls = curCls.getSuperclass();
    	}

    }
// 2015.06.02 H.Mizuno 親クラスのアノテーションも参照する様に拡張 end
    

// 2015.06.02 H.Mizuno 親クラスのアノテーションも参照する様に拡張 start
//    private void createParamMap(Map<String, String[]> paramMap, Object formObj, boolean useNullValue) throws IllegalArgumentException, IllegalAccessException {
//    	// 指定されたフォームクラスのフィールドを取得する。
//    	Field[] fields = formObj.getClass().getDeclaredFields();
    private void createCurParamMap(Map<String, String[]> paramMap, Class<?> targetCls, Object formObj, boolean useNullValue) throws IllegalArgumentException, IllegalAccessException {

    	// 指定されたフォームクラスのフィールドを取得する。
    	Field[] fields = targetCls.getDeclaredFields();
// 2015.06.02 H.Mizuno 親クラスのアノテーションも参照する様に拡張 end

    	// UsePageingParam アノテーションが付与されているフィールドを取得して Map に格納する。
    	for (Field field : fields){
    		field.setAccessible(true);
    		if (field.getAnnotation(UsePagingParam.class) != null){

    			Object obj = field.get(formObj);
    			if (obj != null){
        			if (obj instanceof Object[]){

    					// 作業用 ArrayList
    					List<String> wkParamList = new ArrayList<String>();

        				// パラメータが配列の場合
        				for (Object objOne : (Object[])obj){

        					if (objOne instanceof String){
        						if (((String) objOne).length()>0) {
        							// 実際に値が格納されている場合、ArrayList に追加する。
        							wkParamList.add((String)objOne);
        						}
        					}
        				}

        				if (!wkParamList.isEmpty()){
            				// 値が格納されていれば、Map に格納する。
// 2015.06.02 H.Mizuno Form で配列を使用している時に例外が発生する問題を修正 start
//    						paramMap.put(field.getName(), (String[])wkParamList.toArray());
    						paramMap.put(field.getName(), (String[])wkParamList.toArray(new String[wkParamList.size()]));
// 2015.06.02 H.Mizuno Form で配列を使用している時に例外が発生する問題を修正 end

        				} else if (useNullValue){
							// useNullValue が設定されている場合、値が null でも空文字列で Map に格納する。
    						paramMap.put(field.getName(), new String[]{""});

        				}

        			} else {
            			// パラメータが配列以外の場合
    					if (obj instanceof String){
    						if (((String) obj).length()>0) {
    							// 値が設定されているフィールドの場合、 Map に格納する。
        						paramMap.put(field.getName(), new String[]{(String) obj});

    						} else if (useNullValue) {
    							// useNullValue が設定されている場合、値が null でも空文字列で Map に格納する。
        						paramMap.put(field.getName(), new String[]{""});

    						}
    					}
        			}
    			}
    			
    		}
    		field.setAccessible(false);
    	}
    }
// 2013.09.20 H.Mizuno ページ処理時にパラメータを引き渡す機能を追加 end

}

