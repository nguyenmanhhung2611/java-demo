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
 * �S����        �C����       �C�����e
 * ------------ ----------- -----------------------------------------------------
 * Duong.Nguyen 2015.08.28  Create this file
 * </pre>
 * <p>
 */
public class CsvConverter implements CsvValueConverter {

    /** ���ʃR�[�h�ϊ����� */
    private CodeLookupManager codeLookupManager;

    /**
     * ���ʃR�[�h�ϊ��I�u�W�F�N�g��ݒ肷��B<br/>
     * <br/>
     * 
     * @param codeLookupManager
     */
    public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
        this.codeLookupManager = codeLookupManager;
    }

    /**
     * CSV �o�͒l�̕ϊ�����<br/>
     * <br/>
     * 
     * @param targetValue
     *            CSV �o�͒l
     * @param fieldName
     *            CSV �o�͒l�̃t�B�[���h���iCsvConfig#dbColumns �̒l�j
     * @return�@�ϊ������l
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