package jp.co.transcosmos.dm3.login.v3;

import jp.co.transcosmos.dm3.login.LockSupportLoginUser;

/**
 * LockSupportAuth が使用するアカウントロック処理のインターフェース.
 * LockSupportAuth　は、認証の失敗回数に応じてアカウントがロックされたのと同様の働きを行う。<br>
 * このインターフェースは、LockSupportAuth クラスから使用され、ロックの判定処理や、失敗回数
 * の閾値を取得するインターフェースを提供する。<br>
 * ロックの判定ロジックをカスタマイズする場合はこのインターフェースを実装する必要がある。<br>
 * <br>
 * @author H.Mizuno
 *
 */
public interface LockSupportChecker {

	/**
	 * 指定されたアカウントがロック中かチェックする。<br/>
	 * <br/>
	 * @param loginUser チェック対象ユーザー情報
	 * 
	 * @return ロック中の場合、true を復帰する。
	 */
	public boolean isLocked(LockSupportLoginUser loginUser);



	/**
	 * ログイン失敗回数の閾値を取得する。<br/>
	 * この設定値を超えた回数ログインを失敗するとアカウントがロック状態になる。<br/>
	 * <br/>
	 * @return ログイン失敗回数の閾値
	 */
	public Integer getMaxFailCount();



	/**
	 * ログイン失敗のインターバル（単位分）を取得する。<br/>
	 * この設置値以内にログインを失敗した場合、ログイン失敗としてカウントアップする。<br/>
	 * この設定値を超えている場合は失敗回数を１にリセットする。<br/>
	 * <br/>
	 * @return ログイン失敗のインターバル（単位分）
	 */
	public Integer getFailInterval();
	
}
