package jp.co.transcosmos.dm3.utils;

/**
 * アプリ固有のログ出力を行うクラス用インターフェース.
 * <br>
 * @author H.Mizuno
 *
 */
public interface CommonLogging {

	/**
	 * ログ出力処理<br>
	 * <br>
	 * @param msg 出力メッセージ
	 * @return true=正常終了、false=エラー
	*/
	public boolean write(String msg);

}
