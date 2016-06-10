package jp.co.transcosmos.dm3.login.v3;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.transcosmos.dm3.login.LockSupportLoginUser;


/**
 * 標準実装となる、アカウントロックの判定処理.
 * ログイン失敗回数の閾値と比較し、失敗回数が閾値を超えている場合はロック状態と判定する。<br>
 * 
 * <br>
 * @author H.Mizuno
 *
 */
public class DefaultLockChecker implements LockSupportChecker {

	private static final Log log = LogFactory.getLog(DefaultLockChecker.class);

	// note
	// このクラスのプロパティは、全インスタンスで共有されているので、DI コンテナ以外からは変更しない事。
	// 変則的な構造だが、LockSupportLoginUser　と、CookieLoginUser　を組み合わせて使用した場合、
	// CookieLoginUser#matchCookieLoginPassword() 内からこのクラスの isLocked() を使用して、
	// アカウントのロック状態をチェックする必要がある。
	// もし、CookieLoginUser 側でチェックしなかった場合、一度、自動ログインが成立してしまうので、最終ログイ
	// ン日が更新されてしまう。

	/** ログイン失敗回数の閾値 （デフォルト 5）*/
//	private static Integer maxFailCount = 5;
	private Integer maxFailCount = 5;

	/**
	 * ログイン失敗のインターバル （単位分）　デフォルト 10 分<br/>
	 * ここで指定した時間を経過してからログインに失敗した場合、失敗回数は１にリセットされる。<br/>
	 * 0 の場合、ログインが成功するまでリセットは行わない。<br/>
	 */
//	private static Integer failInterval = 10;
	private Integer failInterval = 10;

	/** ロック期間　（単位分）  デフォルト　0 の場合、永続的にロックする。 */
//	private static Integer lockInterval = 0;
	private Integer lockInterval = 0;



	/**
	 * ログイン失敗回数が閾値を超えているかをチェックする。<br/>
	 * <ul>
	 * <li>ログイン失敗回数が閾値より小さい場合、通常状態として false を復帰する。</li>
	 * <li>ログイン失敗回数が閾値を超えていて、ロック期間が 0 の場合、無制限にロックするので true を復帰する。</li> 
	 * <li>ログイン失敗回数が閾値を超えていて、ロック期間が指定されている場合、最終ログイン失敗日とロック期間
	 * を比較し、ロック期間を経過している場合は通常状態として false を復帰する。</li>
	 * <li>ログイン失敗回数が閾値を超えていて、最終ログイン失敗日がロック期間内の場合は true を復帰する。</li>
	 * </ul>
	 * <br/>
	 * @param loginUser　ログインユーザー情報
	 * 
	 * @return 閾値を超えている場合（ロック状態） true を復帰
	 */
	@Override
	public boolean isLocked(LockSupportLoginUser loginUser) {

		// ログイン失敗回数が閾値を超えていない場合、通常状態として復帰する。
//		if (DefaultLockChecker.maxFailCount > convFailCount(loginUser)) return false;
		if (this.maxFailCount > convFailCount(loginUser)) return false;
		
		
		// 以降、敷地値に達している場合の処理

		// ロック期間が 0 の場合、永続的にロックするので、ロック状態として復帰する。
//		if (DefaultLockChecker.lockInterval == 0) return true;
		if (this.lockInterval == 0) return true;
		
		// ログイン失敗回数が閾値を超えていて、最終ログイン失敗日が空のケースは通常は存在しない。
		// その様な場合は、ロック開放期間の判断が出来ないので、ロック中として復帰する。
		if (loginUser.getLastFailDate() == null) return true;


        // ロック回数が閾値を超えている場合、ロック期間と照合する。
       	// もし最終ログイン失敗日＋ロック期間 がシステム日付より小さい場合、通常状態として復帰する。
       	long sysDateTime = (new Date()).getTime();

       	Calendar calendar = Calendar.getInstance();
       	calendar.setTime(loginUser.getLastFailDate());
//       	calendar.add(Calendar.MINUTE, DefaultLockChecker.lockInterval);
       	calendar.add(Calendar.MINUTE, this.lockInterval);
       	long lockDateTime = calendar.getTimeInMillis();

//       	if (lockDateTime < sysDateTime && DefaultLockChecker.lockInterval != 0){
       	if (lockDateTime < sysDateTime && this.lockInterval != 0){
       		log.info("Lock duration has been passed.");
       		return false;
       	}

       	log.info("failed count is upper limit already.");
       	return true;
	}

	
	
	/**
	 * ログインユーザー情報からログイン失敗回数を取得する。<br/>
	 * ログイン失敗回数が null の場合、0 として復帰する。<br/>
	 * @param loginUser
	 * @return
	 */
	private int convFailCount(LockSupportLoginUser loginUser) {
		Integer failCnt = ((LockSupportLoginUser)loginUser).getFailCnt();
		if (failCnt == null) return 0;
		return failCnt;
	}

	
	
	/**
	 * ログイン失敗回数の閾値を取得する。<br/>
	 * <br/>
	 * @return ログイン失敗回数の閾値
	 */
	@Override
	public Integer getMaxFailCount() {
//		return DefaultLockChecker.maxFailCount;
		return this.maxFailCount;
	}

	/**
	 * ログイン失敗回数の閾値を設定する。<br/>
	 * 初期値として 5　回が設定されている。　もしログインの失敗回数が閾値に達した場合、アカウントがロック状態になる。<br/>
	 * <br/>
	 * @param maxFailCount ログイン失敗回数
	 */
	public void setMaxFailCount(Integer maxFailCount) {
//		DefaultLockChecker.maxFailCount = maxFailCount;
		this.maxFailCount = maxFailCount;
	}

	/**
	 * ログイン失敗のインターバル（単位分）を取得する。<br/>
	 * <br/>
	 * @return ログイン失敗のインターバル（単位分）
	 */
	@Override
	public Integer getFailInterval() {
//		return DefaultLockChecker.failInterval;
		return this.failInterval;
	}

	/**
	 * ログイン失敗のインターバル（単位分）を設定する。<br/>
	 * 初期値として 10 分が設定されている。　最後にログインを失敗してから指定値を超えた場合、失敗回数が１にリセットされる。<br/>
	 * null または、0 を指定した場合、ログインが成功するまでリセットは行わない。
	 * <br/>
	 * @param maxFailCount ログイン失敗回数
	 */
	public void setFailInterval(Integer failInterval) {
		if (failInterval == null) {
//			DefaultLockChecker.failInterval = 0;
			this.failInterval = 0;
		} else {
//			DefaultLockChecker.failInterval = failInterval;			
			this.failInterval = failInterval;			
		}
	}

	/**
	 * ロック期間（単位分）を設定する。<br/>
	 * 初期値として 0 が設定されている。　null または 0 の場合、永続的にロック状態になる。<br/>
	 * <br/>
	 * @param lockInterval ロック期間（単位分）
	 */
	public void setLockInterval(Integer lockInterval) {
		if (lockInterval == null) {
//			DefaultLockChecker.lockInterval = 0;
			this.lockInterval = 0;
		} else {
//			DefaultLockChecker.lockInterval = lockInterval;
			this.lockInterval = lockInterval;
		}
	}


}
