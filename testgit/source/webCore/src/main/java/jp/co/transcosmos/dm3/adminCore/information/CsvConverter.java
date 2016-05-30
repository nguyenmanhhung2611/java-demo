package jp.co.transcosmos.dm3.adminCore.information;

import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.transcosmos.dm3.csv.CsvValueConverter;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

/**
 * お知らせCSV 出力時の変換処理.
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.03.31	新規作成
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
		
		// 登録日時 の場合、日付をyyyy/MM/dd HH:mm:ssに変換する。
		if (columnName.equals("information.insDate") && value != null) {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			return sdf.format((Date)value);
		}
		
		// 種別 の場合、codelookup で定義されている文字列に変換する。
		if (columnName.equals("information.informationType")) {
			return this.codeLookupManager.lookupValueWithDefault("information_type", (String)value, (String)value);
		}
		
		// 公開対象区分 の場合、codelookup で定義されている文字列に変換する。
		if (columnName.equals("information.dspFlg")) {
			return this.codeLookupManager.lookupValueWithDefault("information_dspFlg", (String)value, (String)value);
		}
		
		// 表示期間（FROM) の場合、日付をyyyy/MM/ddに変換する。
		if (columnName.equals("information.startDate") && value != null) {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
			return sdf.format((Date)value);
		}
		
		// 表示期間（TO) の場合、日付をyyyy/MM/ddに変換する。
		if (columnName.equals("information.endDate") && value != null) {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
			return sdf.format((Date)value);
		}
		
		// 最終更新日 の場合、日付をyyyy/MM/dd HH:mm:ssに変換する。
		if (columnName.equals("information.updDate") && value != null) {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			return sdf.format((Date)value);
		}
		
		// 変換不要な場合は元の値を復帰
		return value;
	}

}
