package jp.co.transcosmos.dm3.adminCore.inquiry;

import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.transcosmos.dm3.csv.CsvValueConverter;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

/**
 * お問合せCSV 出力時の変換処理.
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * Y.Cho		2015.04.10	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class CsvConverter implements CsvValueConverter {

	protected CodeLookupManager codeLookupManager;
	
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}
	
	/**
	 * CSV 出力値の変換処理<br/>
	 * <br/>
	 * @param columnName CSV 出力値のフィールド名（CsvConfig#dbColumns の値）
	 * @param value そのカラムの出力値
	 * @param thisOne CSV 行データ （Value オブジェクト、または、JoinResult）
	 * 
	 * @return　変換した値
	 */
	@Override
	public Object convert(String columnName, Object value, Object thisOne) {
		
		// 問い合わせ種別 の場合、codelookup で定義されている文字列に変換する。
		if (columnName.equals("inquiryHeader.inquiryType")) {
			return this.codeLookupManager.lookupValueWithDefault("inquiry_type", (String)value, (String)value);
		}

		// お問合せ日時 の場合、日付をyyyy/MM/ddに変換する。
		if (columnName.equals("inquiryHeader.inquiryDate") && value != null) {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
			return sdf.format((Date)value);
		}
		
		// お問合せ種別 の場合、codelookup で定義されている文字列に変換する。
		if (columnName.equals("inquiryDtlInfo.inquiryDtlType")) {
			return this.codeLookupManager.lookupValueWithDefault("inquiry_dtlType", (String)value, (String)value);
		}
		
		// 最終更新日 の場合、日付をyyyy/MM/dd HH:mm:ssに変換する。
		if (columnName.equals("inquiryHeader.updDate") && value != null) {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			return sdf.format((Date)value);
		}
		
		// 変換不要な場合は元の値を復帰
		return value;
	}

}
