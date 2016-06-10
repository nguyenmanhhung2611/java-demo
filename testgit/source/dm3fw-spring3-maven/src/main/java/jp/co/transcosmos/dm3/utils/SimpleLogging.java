package jp.co.transcosmos.dm3.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.transcosmos.dm3.mail.ReplacingMail;


/**
 * 簡易ログ出力クラス 
 * ログファイルへの出力を対応する。
 *　使用する場合は、Spring の DI コンテナでシングルトンで定義する事。
 *　（直接インスタンス化した場合や、scope がプロトタイプの場合は、マルチスレッド環境で動作保証しない。）
 *
 * 複数の bean 定義をする場合、ログファイル名が重複しない様に注意する事。　（bean 単位で排他制御を行っている為。）
 * 
 */
public class SimpleLogging implements CommonLogging {

    private static final Log log = LogFactory.getLog(SimpleLogging.class);

	// タイムアウト時間 （ミリ秒）　省略した場合は無制限にロックを待機する。
	private Integer timeout = null;

	// ログファイル名（必須）
	private String logFileName = null;

	// メール送信オブジェクト　オブジェクトを設定している場合、例外発生時にエラーメールを送信する。
	// 少なくとも、送信先、送信元、タイトルのプロパティが設定されている事。
	private ReplacingMail mail = null;

	// ファイルローテート指定
	//　"year"、"month"、"day" が指定されると、ファイル名に、「_YYYY」、「_YYYYMM」、「_YYYYMMDD」 が付加される。
	// 未設定の場合、ファイル名に何も付加しない。　（ローテートしない。）
	private String rotate = null;

// 2015.03.31 H.Mizuno 文字コードを指定する機能を追加 start
	/** 出力文字コード。　省略時はプラットホーム毎のデフォルト値が適用される。 */
	private String encode = null;

	/**
	 * 文字コード設定<br/>
	 * 設定を省略した場合の初期値は UTF-8。<br/>
	 * <br/>
	 * @param encode String 文字コード文字列
	 */
	public void setEncode(String encode) {
		this.encode = encode;
	}
// 2015.03.31 H.Mizuno 文字コードを指定する機能を追加 end



	// ロックオブジェクト
	// 複数の SimpleLogging を bean 定義する事を前提としている為、ロックオブジェクトは static で宣言していない。
	// （他のログ出力をロックしない為。）
	private final Lock lock = new ReentrantLock();

	// ログ出力時の日付書式
	private final SimpleDateFormat logDateFmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss S");



	/**
	 * ログ出力処理<br>
	 * <br>
	 * @param msg 出力メッセージ
	 * @return true=正常終了、false=エラー
	*/
	@Override
	public boolean write(String msg) {

		if (this.logFileName == null) {
			// ログファイル名が未設定の場合例外をスロー
			// ※この場合は、明らかなな設定ミス（バグ）なのでランタイムエラーをスローする。
			RuntimeException e = new RuntimeException("SimpleLogging logFileName properties is null"); 
			sendErrorMailAndLogging(e);
			throw e;
		}
		
	
		// このメソッドは、ログファイル名の設定ミス以外は例外をスローしない。
		// (ログ出力時の問題を、業務アプリケーションに影響させない為。）

		
		if (this.timeout == null || this.timeout == 0){
			// タイムアウトの設定がされていない場合はロックが取得できるまで待機する。
			return lockWrite(msg);
		} else {
			// タイムアウトが設定されている場合はタイムアウト時間を設定してロックを取得する。
			return tryLockWrite(msg);
		}
	}



	/**
	 * 無制限待機の排他制御によるログ出力処理<br>
	 * <br>
	 * @param msg 出力メッセージ
	 * @return true=正常終了、false=エラー
	*/
	private boolean lockWrite(String msg) {

		try {
			// ロックが取得できるまで無制限に待機する。
			this.lock.lock();
			
			// ログの書き込みを実施する
			return logWriter(msg);

		} finally {
			// ロックの開放
			this.lock.unlock();

		}
	}



	/**
	 * タイムアウト付き排他制御によるログ出力処理<br>
	 * <br>
	 * @param msg 出力メッセージ
	 * @return true=正常終了、false=エラー
	*/
	private boolean tryLockWrite(String msg) {
		
		try {
			if (this.lock.tryLock(this.timeout, TimeUnit.MILLISECONDS)){
				try {
					// ログの書き込みを実施する
					return logWriter(msg);
					
				} finally {
					// ロックが取得できている場合はロックを開放する
					this.lock.unlock();

				}

			} else {
				// ロックが取得出来なかった場合は Log4J のロギングやエラーメールで送信する。
				// アプリケーションには例外はスローしない。
				RuntimeException e = new RuntimeException("SimpleLogging lock time out"); 
				sendErrorMailAndLogging(e);
				return false;
			}

		} catch (InterruptedException e) {
			// 割込み例外が発生した場合も例外をスローしない。
			// 例外の情報は Log4J のロギングやエラーメールで送信する。
			sendErrorMailAndLogging(e);
			return false;
		}
	}



	/**
	 * ログ出力処理<br>
	 * <br>
	 * @param msg 出力メッセージ
	 * @return true=正常終了、false=エラー
	*/
	private boolean logWriter(String msg){

		// ログに出力する日付を取得
		String logDate = logDateFmt.format(new Date());


		// rotate プロパティが設定されている場合、ファイル名に付与する。
		String fileName = this.logFileName;

		if (rotate != null) {

			String fileNameDate = "";
			// ローテート書式に合わせてファイル名の日付部分を取得
			if (rotate.equals("year")) {
				fileNameDate = "_" + logDate.substring(0,4);

			} else if (rotate.equals("month")) {
				fileNameDate = "_" + logDate.substring(0,4) + logDate.substring(5,7);
				
			} else if (rotate.equals("day")) {
				fileNameDate = "_" + logDate.substring(0,4) + logDate.substring(5,7) + logDate.substring(8,10);

			}


			// note
			// 細かく対応するとログ出力の性能にも影響するので、取り急ぎ暫定的な置換処理。
			// ファイル名で拡張子を使用せずに、フォルダ名でピリオドを使用した場合、フォルダ名が置換される。

			if (this.logFileName.lastIndexOf(".") == -1) {
				// 拡張子が無い場合、末尾にそのまま日付を追加
				fileName = fileName + fileNameDate;

			} else {
				// 拡張子が見付かった場合、日付を付加
				fileName = fileName.replaceAll("(.*)\\.(.+?)$", "$1" + fileNameDate + ".$2");

			}
		}
		
		
		// ログファイルのオブジェクト
		File file = new File(fileName);
		// ログファイルのライターオブジェクト
		PrintWriter writer = null;

		try {

			// ログファイルへの書き込み

// 2015.03.31 H.Mizuno 文字コードを指定する機能を追加 start
//			writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
			if (this.encode == null || this.encode.equals("")){
				// 文字コードが未指定の場合、プラットホーム毎のデフォルト値で出力
				writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
			} else {
				// 指定された文字コードで出力
				writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true),this.encode)));
			}
// 2015.03.31 H.Mizuno 文字コードを指定する機能を追加 end

			writer.println("[" + logDate + "] " + msg);

			return true;

		} catch (IOException e) {
			// 書き込みエラーが発生しても例外をスローしない。　例外の情報は Log4J のロギングやエラーメールで送信する。
			// 例えば、出力先ファイルが排他ロックされている場合でもエラーにしない。
			sendErrorMailAndLogging(e);
			return false;

		} finally {
			if (writer != null) writer.close();

		}
	}

	

	/**
	 * エラーログの出力、および、エラーメールの送信処理<br>
	 * <br>
	 * @param e 例外オブジェクト
	*/
	private void sendErrorMailAndLogging(Exception e){

		// スタックトレースの取得
		StringWriter sw = null;
        PrintWriter pw = null;
        String stacktrace = null;

		try {
			sw = new StringWriter();
	        pw = new PrintWriter(sw);

	        e.printStackTrace(pw);
	        stacktrace = sw.toString();
		} finally {
			if (sw != null) {
				try {
					sw.close();
				} catch (IOException e1) {
					log.error(e1);
				}
			}
			if (pw != null) {
				pw.close();
			}
		}


		// 共通のロギングにはワーニングレベルで出力する。
		log.warn(stacktrace);

		
		// メールオブジェクトが何も設定されていない場合、メール送信は行わない。
		if (this.mail == null) return;

		// エラーメール送信処理
		try {
			this.mail.setBodyText(e.getMessage());
	        this.mail.sendThreaded();

		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
	}
	
	

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public void setLogFileName(String logFileName) {
		this.logFileName = logFileName;
	}

	public void setMail(ReplacingMail mail) {
		this.mail = mail;
	}

	public void setRotate(String rotate) {
		this.rotate = rotate;
	}
}
