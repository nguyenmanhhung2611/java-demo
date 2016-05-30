package jp.co.transcosmos.dm3.webAdmin.adminLog;

import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.transcosmos.dm3.csv.CsvValueConverter;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

/**
 * A CSV converter that convert values of admin log records into csv format
 * <p>
 * 
 * <pre>
 * 担当者        修正日       修正内容
 * ------------ ----------- -----------------------------------------------------
 * Duong.Nguyen 2015.08.28  Create this file
 * </pre>
 * <p>
 */
public class CsvConverter implements CsvValueConverter {

    /** 共通コード変換処理 */
    private CodeLookupManager codeLookupManager;

    /**
     * 共通コード変換オブジェクトを設定する。<br/>
     * <br/>
     * 
     * @param codeLookupManager
     */
    public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
        this.codeLookupManager = codeLookupManager;
    }

    /**
     * CSV 出力値の変換処理<br/>
     * <br/>
     * 
     * @param targetValue
     *            CSV 出力値
     * @param fieldName
     *            CSV 出力値のフィールド名（CsvConfig#dbColumns の値）
     * @return　変換した値
     */
    @Override
    public Object convert(String columnName, Object value, Object thisOne) {
        // translate function code of admin log in to function name
        if ("adminLog.functionCd".equals(columnName)) {
            String code = String.valueOf(value);
            return this.codeLookupManager.lookupValueWithDefault("functionCd", code, code);
        }
        // translate insert date into yyyy/MM/dd HH:mm:ss format
        if (columnName.equals("adminLog.insDate") && value != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format((Date) value);
        }
        // return translated values
        return value;
    }

}