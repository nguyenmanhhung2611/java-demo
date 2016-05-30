package jp.co.transcosmos.dm3.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.transcosmos.loader.reader.CsvDataReader;

/**
 * CSV　データの Reader クラス.
 * <p>
 * 実施的な処理は、バッチフレームワークである、ExecuteLoader の CsvDataReader
 * クラスへ処理を委譲する。<br/>
 * CsvDataReader　クラスをファイル名の代わりに InputStreamReader で使用できる
 * 様に拡張した、継承型 Adapter クラスになる。<br/>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.06	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class CsvStreamReader extends CsvDataReader {

	private static Log log = LogFactory.getLog(CsvStreamReader.class); 

	/** CSV 読込み対象 InputStreamReader */
	protected InputStreamReader inputStreamReader;



	/** コンストラクター<br/>
	 * <br/>
	 * @param inputStreamReader CSV 読込み対象となる InputStreamReader
	*/
	public CsvStreamReader(InputStreamReader inputStreamReader) {
		// InputStreamReader の場合、ファイル名は存在しない。
		// ダミー値として、"input stream reader" を設定する。
		super("input stream reader");
		this.inputStreamReader = inputStreamReader;
	}

	/** コンストラクター<br/>
	 * <br/>
	 * @param inputStreamReader CSV 読込み対象となる InputStreamReader
	 * @param encode 文字コード文字列
	 * @param terminator char 区切り文字コード
	*/
	public CsvStreamReader(InputStreamReader inputStreamReader, char terminator) {
		// InputStreamReader の場合、エンコード設定は、既に確定しているので
		// コンストラクターへは null を渡す。
		super("input stream reader", null, terminator);
		this.inputStreamReader = inputStreamReader;
	}



	/**
	 * 初期処理<br/>
	 * 継承元クラスでは、ファイル名を指定して BufferReader を生成するが、このクラスでは
	 * コンストラクタで指定された InputStreamReader を直接指定する。<br/>
	 * <br/>
	 * 親クラスのメソッドを呼び出していないので、親クラスの拡張には注意する事。<br/>
	 * <br/>
	 * @exception IOException
	 */
	@Override
	public void open() throws IOException {
		log.debug("open() start.　(InputStreamReder Version)"); 

		// 既に使用中の場合、例外をスローする。
		if (this.bufferReader != null) {
			throw new RuntimeException("CsvDataReader is already opened.");
		}

		// リーダーを生成する。
		this.bufferReader = new BufferedReader(this.inputStreamReader);

		// タイトルのスキップフラグをリセット
		this.skipped = false;

		log.info("open() success. (InputStreamReder Version)"); 

	}

	

	/**
	 * 後処理<br/>
	 * 渡された InputStreamReader をクローズした後、親クラスのメソッドを実行する。<br/>
	 * <br/>
	 * @exception IOException
	 */
	@Override
	public void close() throws IOException {
		try {
			this.inputStreamReader.close();
		} finally {
			super.close();
		}
	}
	
}
