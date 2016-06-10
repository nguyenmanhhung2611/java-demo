package jp.co.transcosmos.dm3.login.v3;

/**
 * アカウントロック例外クラス.
 * <p>
 * 認証チェックしたアカウントがロック状態の場合、この例外がスローされる。
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.24	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * V3 認証のみ対応
 * 
 */
public class LockException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
