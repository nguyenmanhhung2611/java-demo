package jp.co.transcosmos.dm3.login.v3;

import jp.co.transcosmos.dm3.login.LockSupportLoginUser;

/**
 * アカウントロック対応認証インターフェース.
 * <p>
 * アカウントロックに対応する認証処理を作成する場合、このインターフェースを実装する事。<br>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.24	新規作成
 * </pre>
 * <p>
 * 注意事項<br>
 * V3 認証のみ対応
 * 
 */
public interface LockAuthentication extends Authentication {

	/**
	 * ロック状態かをチェックする。<br/>
	 * <br/>
	 * @param loginUser ログインユーザー情報
	 * @return ロック状態の場合、true を復帰する。
	 */
	public boolean isLocked(LockSupportLoginUser loginUser);

	/**
	 * ロック状態に変更する。<br/>
	 * <br/>
	 * @param loginUser ステータス更新対象ユーザー情報
	 */
	public void changeToLock(LockSupportLoginUser loginUser);

	/**
	 * 通常状態に変更する。<br/>
	 * <br/>
	 * @param loginUser ステータス更新対象ユーザー情報
	 */
	public void changeToUnlock(LockSupportLoginUser loginUser);

}
