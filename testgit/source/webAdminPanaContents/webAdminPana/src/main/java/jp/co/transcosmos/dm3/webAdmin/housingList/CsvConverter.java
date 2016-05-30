package jp.co.transcosmos.dm3.webAdmin.housingList;

import jp.co.transcosmos.dm3.csv.CsvValueConverter;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;


/**
 * 物件一覧CSV 出力時の変換処理.
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.05	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class CsvConverter implements CsvValueConverter {

	/** 共通コード変換処理 */
	private CodeLookupManager codeLookupManager;

	/**
     * 共通コード変換オブジェクトを設定する。<br/>
     * <br/>
     * @param codeLookupManager
     */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}



	/**
	 * CSV 出力値の変換処理<br/>
	 * <br/>
	 * @param targetValue CSV 出力値
	 * @param fieldName CSV 出力値のフィールド名（CsvConfig#dbColumns の値）
	 * @return　変換した値
	 */
    @Override
    public Object convert(String columnName, Object value, Object thisOne) {
        // カラム名（CsvConfig の、dbColumns で指定した値）がロールID の場合
        if (columnName.equals("adminRoleInfo.roleId")) {
            // ロールID の場合、codelookup で定義されている文字列に変換する。
            return this.codeLookupManager.lookupValueWithDefault("roleId", (String)value, (String)value);
        }

        // 変換不要な場合は元の値を復帰
        return value;
    }

}
