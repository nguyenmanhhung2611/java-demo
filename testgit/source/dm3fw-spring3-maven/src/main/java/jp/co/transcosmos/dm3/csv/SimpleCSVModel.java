package jp.co.transcosmos.dm3.csv;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.utils.ReflectionUtils;
import jp.co.transcosmos.dm3.view.SimpleSpreadsheetModel;


/**
 * <pre>
 * ヘッダ行の出力をカスタマイズした CSV 出力処理
 * URL マッピング時、「csvh:CSV設定情報」で使用すると、指定された CSV設定情報の内容で CSV
 * 出力する。　また、ファイル名に日付書式を使用する事も可能。
 *
 * 担当者            修正日               修正内容
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.22  新規作成
 * H.Mizuno  2013.12.20  文字コードを設定可能に変更
 * H.Mizuno  2015.03.04  JoinResult のレスポンスに対応、CSV 出力項目設定に対応
 *
 * </pre>
*/
public class SimpleCSVModel extends SimpleSpreadsheetModel {

	// CSV 出力のプロパティ情報
	private CsvConfig csvConfig;
	

	/**
     * コンストラクター<br/>
     * <br/>
     * @param fileName CSV 出力ファイル名
     */
	public SimpleCSVModel(CsvConfig pConfig) {
		super(pConfig.getFileName());
		this.csvConfig = pConfig;
    }

    

	/**
     * CSV ヘッダ情報出力処理<br/>
     * 標準の処理は、モデルのプロパティ名から CSV の出力を行っている。<br/>
     * このクラスでは、指定された CSV 情報からヘッダ行の情報を取得して出力する。<br/>
     * また、ヘッダ行の設定が無い場合、ヘッダ行を出力しない。<br/>
     * <br/>
     * @param rows 出力データ
     * @param model マップ情報 （未使用）
     * @param request HTTP リクエスト
     * @param response HTTP レスポンス
     */
	@Override
	public Object[] getHeaders(Object[] rows, Map<?, ?> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// 該当データが存在しない場合、ヘッダを出力しない。
        if ((rows == null) || (rows.length == 0)) {
        	return null;
        }

       	// CSV の設定情報が無い場合、ヘッダを出力しない。
       	// 但し、ModelAndViewRewriteSupport を経由する場合、例外が発生するので、
       	// このパターンは通常、存在しない。
       	if (this.csvConfig == null){
       		return null;
       	}

   		// ヘッダ用のリストが設定されていない場合、ヘッダを出力しない。
       	if (this.csvConfig.getHeaderColumns() == null) {
       		return null;
       	}

  		// ヘッダ用のリストが設定されている場合、その情報を使用して Header を出力する。
       	return this.csvConfig.headerColumns.toArray(new String[this.csvConfig.headerColumns.size()]);
	}



// 2015.03.04 H.Mizuno JoinResult でも出力できる様に機能を拡張 start
	/**
	 * CSV １行毎のデータを編集して取得する。<br/>
	 * csvConfig　の dbColumns プロパティが設定されている場合、そこに指定されたフィールド名の値を CSV 出力
	 * 項目とする。<br/>
	 * また、csvConfig　の csvValueConverter プロパティが設定されている場合、そのクラスを使用して CSV 出
	 * 力値の変換処理を行う。<br/>
	 * <br/>
	 */
	@Override
    public Object[] getOneRow(Object rows[], int index, Map<?,?> model, 
            HttpServletRequest request, HttpServletResponse response) throws Exception {

    	Object thisOne = rows[index];
        if (thisOne == null) {
            return null;
        }


    	// 出力カラムの設定が設定されていない場合、元の処理を実行して復帰する。
        if (this.csvConfig == null ||
        		this.csvConfig.getDbColumns() == null || this.csvConfig.getDbColumns().size() == 0){
        	return super.getOneRow(rows, index, model, request, response);
        }


        // 以下、フィールドを指定した CSV 出力処理


    	// 戻り値用のリストオブジェクトを作成
    	List<Object> result = new ArrayList<>();

        
    	// DB 出力項目の設定値を取得する。
    	for (String columnName : this.csvConfig.getDbColumns()){

    		// フィールド名は、aliasName.fieldName または、fieldName で設定される。
    		// もし、ピリオドで区切られていた場合、JoinResult と判断して処理する。
    		String colInfo[] = columnName.split("\\.");

    		Object value = null;
    		if (colInfo.length == 2) {
    			// alias の指定がある場合、JoinResult から item を取得してから処理する。
    			Object valueObject = ((JoinResult)thisOne).getItems().get(colInfo[0]);

                try {
                	value = ReflectionUtils.getFieldValueByGetter(valueObject, colInfo[1]);
                } catch (Throwable err) {
                    throw new RuntimeException("Error getting field: " + columnName);
                }

    		} else {
    			// alias の指定が無い場合、直接値を取得する。
                try {
                	value = ReflectionUtils.getFieldValueByGetter(thisOne, columnName);
                } catch (Throwable err) {
                    throw new RuntimeException("Error getting field: " + columnName);
                }
    		}

    		result.add(convertValue(columnName, value, thisOne));
    	}
        
    	return result.toArray();
	}


    
	/**
	 * CSV 出力値の変換処理<br/>
	 * csvConfig の csvValueConverter プロパティに変換処理が設定されている場合、CSV 出力値
	 * の変換処理を行う。<br/>
	 * <br/>
	 * @param columnName 出力フィールド名
	 * @param value そのカラムの出力値
	 * @param thisOne CSV 行データ （Value オブジェクト、または、JoinResult）
	 * @return
	 */
    private Object convertValue(String columnName, Object value, Object thisOne){

        if (this.csvConfig == null) return value;
        
        CsvValueConverter csvConv = this.csvConfig.getCsvValueConverter(); 
        if (csvConv == null) return value;

        return csvConv.convert(columnName, value, thisOne);
    }
// 2015.03.04 H.Mizuno JoinResult でも出力できる様に機能を拡張 end



	/**
     * CSV ファイル名取得処理<br/>
     * ファイル名に、###yyyyMMdd### の様に、日付フォーマット書式が含まれる場合、指定された<br/>
     * 書式で置換した文字列をファイル名とする。<br/>
     * <br/>
     * @param rows 出力データ
     * @param model マップ情報 （未使用）
     * @param request HTTP リクエスト
     * @param response HTTP レスポンス
     */
	@Override
    public String getFilename(Map<?,?> model, HttpServletRequest request, HttpServletResponse response)
    		throws Exception {

		// ファイルが指定されていない場合、null を復帰
		if (this.filename == null || this.filename.length() == 0) return null;

		// フォーマット指定の開始が無い場合、そのままファイル名を復帰
		int start = this.filename.indexOf("###");
		if (start == -1) return this.filename;

		// フォーマット指定の開始が見付かった場合、終了位置を取得
		// 終了位置が見付からない場合、そのままファイル名として復帰
		int end = this.filename.indexOf("###", start + 3);
		if (end == -1) return this.filename;
		
		// 指定された書式で日付文字列を生成する。
		String fmtStr = this.filename.substring(start + 3, end);
		SimpleDateFormat dateFmt = new SimpleDateFormat(fmtStr);
		String fmtDate = dateFmt.format(new Date());

		// 生成した日付で置換した結果をファイル名として返す。
		return this.filename.replaceAll("###.*###", fmtDate);
    }

// 2013.12.20 H.Mizuno CSV 出力文字コードの設定を可能に変更 start
	@Override
	public String getEncoding() {
		return csvConfig.getEncode();
	}
// 2013.12.20 H.Mizuno CSV 出力文字コードの設定を可能に変更 end


// 2015.05.19 H.Mizuno CSV 出力時の BOM 対応 start
	public byte[] getBOM(String encoding) {

		if (this.csvConfig.isUseBOM()) {
			if ("UTF-8".equals(encoding.toUpperCase())){
				return new byte[]{(byte)0xef, (byte)0xbb, (byte)0xbf};
			}
		}

		return null;
	}
// 2015.05.19 H.Mizuno CSV 出力時の BOM 対応 end

}
