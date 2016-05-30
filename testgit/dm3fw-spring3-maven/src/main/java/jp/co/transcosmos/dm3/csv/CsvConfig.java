package jp.co.transcosmos.dm3.csv;

import java.util.List;


/**
 * <pre>
 * CSV 出力設定情報
 * CSV 出力時のヘッダ情報、ファイル名、区切り文字などを指定する。
 *
 * 担当者            修正日               修正内容
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.22  新規作成
 * H.Mizuno  2013.12.20  文字コードを設定可能に変更
 * H.Mizuno  2015.03.04  出力フィールドを設定可能に変更
 *
 * </pre>
*/
public class CsvConfig {

	// 出力ファイル名（拡張子無しで指定）
	protected String fileName;

	// CSV ヘッダ行情報
	// ヘッダ行を出力しない場合、なにも設定しない
	protected List<String> headerColumns;

// 2015.03.04 H.Mizuno 出力フィールドを設定する機能を追加 start
	// 出力対象となる DB フィールド名
	// JoinResult の場合、alias.fieldName の書式で設定する。
	// 全フィールドを出力する場合、何も設定しない。
	protected List<String> dbColumns;
	
	// CSV の値を変換する場合、変換処理を設定する。
	// 特に何も処理しない場合は何も設定しない。
	protected CsvValueConverter csvValueConverter;
// 2015.03.04 H.Mizuno 出力フィールドを設定する機能を追加 end

	// 区切り文字。　省略時は、カンマ記号が使用される。
	protected String delimiter;

// 2013.12.20 H.Mizuno CSV 文字コードを設定可能にする為に設定を追加 start
	// CSV 出力時の文字コード
	protected String encode;
// 2013.12.20 H.Mizuno CSV 文字コードを設定可能にする為に設定を追加 end

// 2015.05.19 H.Mizuno CSV 出力時の BOM 対応 start
	// CSV 出力時の BOM 出力 （UTF-8 で BOM を出力する場合、true を設定する。）
	protected boolean useBOM = false;
// 2015.05.19 H.Mizuno CSV 出力時の BOM 対応 end


	// setter、getter
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public List<String> getHeaderColumns() {
		return headerColumns;
	}

// 2015.03.04 H.Mizuno 出力フィールドを設定する機能を追加 start
	public List<String> getDbColumns() {
		return dbColumns;
	}
	public void setDbColumns(List<String> dbColumns) {
		this.dbColumns = dbColumns;
	}
	public CsvValueConverter getCsvValueConverter() {
		return csvValueConverter;
	}
	public void setCsvValueConverter(CsvValueConverter csvValueConverter) {
		this.csvValueConverter = csvValueConverter;
	}
// 2015.03.04 H.Mizuno 出力フィールドを設定する機能を追加 end

	public void setHeaderColumns(List<String> headerColumns) {
		this.headerColumns = headerColumns;
	}
	public String getDelimiter() {
		return delimiter;
	}
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

// 2013.12.20 H.Mizuno CSV 文字コードを設定可能にする為に設定を追加 start
	public String getEncode() {
		return encode;
	}
	public void setEncode(String encode) {
		this.encode = encode;
	}
// 2013.12.20 H.Mizuno CSV 文字コードを設定可能にする為に設定を追加 end

// 2015.05.19 H.Mizuno CSV 出力時の BOM 対応 start
	public boolean isUseBOM() {
		return useBOM;
	}
	public void setUseBOM(boolean useBOM) {
		this.useBOM = useBOM;
	}
// 2015.05.19 H.Mizuno CSV 出力時の BOM 対応 end

}
