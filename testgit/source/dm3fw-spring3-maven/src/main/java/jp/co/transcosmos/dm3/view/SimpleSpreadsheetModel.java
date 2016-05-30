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

// 2013.04.19 H.Mizuno 機能拡張の為、スコープを変更　Start
//    private String filename;
    protected String filename;
// 2013.04.19 H.Mizuno 機能拡張の為、スコープを変更 End

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
// 2015.03.05 H.Mizuno JoinResult の場合、正常に出力できない問題に対応 start
        	if (rows[0] instanceof JoinResult){
            	return getHeadersFromJoinResult((JoinResult)rows[0]);
        	}
// 2015.03.05 H.Mizuno JoinResult の場合、正常に出力できない問題に対応 end
            return ReflectionUtils.getAllFieldNamesByGetters(rows[0].getClass());
        } else {
            return null;
        }
    }

// 2015.03.05 H.Mizuno JoinResult の場合、正常に出力できない問題に対応 start
    /**
     * JoinResult 用のヘッダ取得処理<br/>
     * objectName + "." + fieldName をヘッダ名として復帰する。<br/>
     * <br/>
     * @param oneRow ヘッダ情報を取得する JoinResult
     * @return
     */
    protected Object[] getHeadersFromJoinResult(JoinResult oneRow){
    	
        // 戻り値用のリストオブジェクトを作成
        List<Object> result = new ArrayList<>();
    	
        // JoinResult の items に格納されているオブジェクト分、ループして Value オブジェクトを取得する。
        for (Object oneValueObject : oneRow.getItems().values()){

        	// JoinResultMap は 10 個の Value オブジェクトを内部的に保持しており、values() は
        	// それら全てのオブジェクトを復帰する。　未使用な場合もあるので、null 判定してから処理する。
        	if (oneValueObject != null) {

        		// Value オブジェクトに存在するフィールド名を取得する。
        		String[] fieldNames = ReflectionUtils.getAllFieldNamesByGetters(oneValueObject.getClass());
        
        		// 取得したフィールド名に、Value オブジェクトのオブジェクト名を付加して、戻り値用リストに追加する。
        		for (int i=0; i<fieldNames.length ;++i){
        			result.add(oneValueObject.getClass().getSimpleName() + "." + fieldNames[i]);
        		}
        	}
        }

        return result.toArray();
    }
// 2015.03.05 H.Mizuno JoinResult の場合、正常に出力できない問題に対応 end
    
    public Object[] getOneRow(Object rows[], int index, Map<?,?> model, 
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object thisOne = rows[index];
        if (thisOne == null) {
            return null;
        }

// 2015.03.05 H.Mizuno JoinResult の場合、正常に出力できない問題に対応 start
        if (thisOne instanceof JoinResult){
        	return getOneRowFromJoinResult((JoinResult)thisOne);
        }
// 2015.03.05 H.Mizuno JoinResult の場合、正常に出力できない問題に対応 end

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

    
// 2015.03.05 H.Mizuno JoinResult の場合、正常に出力できない問題に対応 start
    /**
     * JoinResult の場合の１行データ取得処理<br/>
     * <br/>
     * @param thisOne CSV １行に該当する JoinResult
     * @return　取得したフィールド値が格納されたオブジェクトの配列
     * @exception Exception
     */
    protected Object[] getOneRowFromJoinResult(JoinResult thisOne) throws Exception {

        // 戻り値用のリストオブジェクトを作成
        List<Object> result = new ArrayList<>();

        // JoinResult の items に格納されているオブジェクト分、ループして Value オブジェクトを取得する。
        for (Object oneValueObject : thisOne.getItems().values()){

        	// JoinResultMap は 10 個の Value オブジェクトを内部的に保持しており、values() は
        	// それら全てのオブジェクトを復帰する。　未使用な場合もあるので、null 判定してから処理する。
        	if (oneValueObject != null) {

            	// Value オブジェクトからフィールド名を取得する。
            	String fieldNames[]
            			= ReflectionUtils.getAllFieldNamesByGetters(oneValueObject.getClass());

            	// フィールド名分、getter を経由してCSV 出力値を取得する。
       			for (String fieldName : fieldNames){
       				try {
       					result.add(ReflectionUtils.getFieldValueByGetter(oneValueObject, fieldName));
       				} catch (Throwable err) {
       					// getter が実装されていない場合、例外をスローする。
       					// getter が無い場合、警告でも良いかも知れないが、現状との互換を優先する。
       					log.error("Error getting field: " + fieldName);
       					throw err;
       				}
       			}
        	}
        }

        return result.toArray();
    }
// 2015.03.05 H.Mizuno JoinResult の場合、正常に出力できない問題に対応 end
    
    
    
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

// 2013.12.20 H.Mizuno　CSV 出力時の文字コードを設定可能にする為メソッドを追加  start
	@Override
	public String getEncoding() {
		// CSVView は、特に指定が無ければ HttpServletResponse の文字コードを使用する。
		// そこからも取得出来ない場合、Windows-31J　を使用している。
		// この流れは既存システムの互換性の問題から変更する事はできない。
		return null;
	}
// 2013.12.20 H.Mizuno　CSV 出力時の文字コードを設定可能にする為メソッドを追加  end

}
