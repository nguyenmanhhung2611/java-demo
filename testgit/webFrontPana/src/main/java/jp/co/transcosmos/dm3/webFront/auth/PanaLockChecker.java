package jp.co.transcosmos.dm3.webFront.auth;

import jp.co.transcosmos.dm3.login.LockSupportLoginUser;
import jp.co.transcosmos.dm3.login.v3.DefaultLockChecker;
import jp.co.transcosmos.dm3.corePana.vo.MemberInfo;


public class PanaLockChecker extends DefaultLockChecker {

	
	/**
	 * マイページロック状態チェック処理.
	 * <p>
	 * フレームワークのロック状態チェックを拡張し、会員情報のロックフラグが 1 の場合もロック状態とする。<br/>
	 * </ul>
	 * <br/>
	 * <pre>
	 * 担当者		修正日		修正内容
	 * ------------ ----------- -----------------------------------------------------
	 * H.Mizuno		2015.03.05	新規作成
	 * </pre>
	 * <p>
	 * 注意事項<br/>
	 * 
	 */
	@Override
	public boolean isLocked(LockSupportLoginUser loginUser) {
		// オリジナルの処理を使用して、ログイン失敗によるロック状態をチェックする。
		boolean ret = super.isLocked(loginUser);

		// ログイン失敗回数によりロックされていない場合、ユーザー情報をロックフラグをチェックする。
		// もしロック状態の場合、true を復帰する。
		if (!ret) {
			if ("1".equals(((MemberInfo)loginUser).getLockFlg())) return true;
		}

		return ret;
	}
}
