package jp.co.transcosmos.dm3.login.v3;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.FormulaUpdateExpression;
import jp.co.transcosmos.dm3.dao.UpdateExpression;
import jp.co.transcosmos.dm3.dao.UpdateValue;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.LockSupportLoginUser;
import jp.co.transcosmos.dm3.login.UserRoleSet;
import jp.co.transcosmos.dm3.login.form.v3.LoginForm;


/**
 * ログイン失敗回数に応じた、アカウントロック対応認証クラス.
 * <p>
 * DefaultAuth の派生系認証クラスに対してアカウントロック機能を追加する Adaptor クラス。<br>
 * ログイン処理時、LockSupportChecker を使用してアカウントのロック状態をチェックする。<br>
 * もしロック中だった場合は例外をスローする。<br>
 * また、ログインチェック処理時も同様の対応を行う。　これは、自動ログイン処理の場合、ログインチェック
 * 処理で自動的にログインが行われる場合がある為の対応である。<br>
 * 認証失敗時は失敗回数をカウントアップする。<br>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.24	新規作成
 * H.Mizuno		2015.05.19	間際らしいメソッド名が使用されていたので変更
 * </pre>
 * <p>
 * 注意事項<br/>
 * V3 認証のみ対応<br/>
 * <br/>
 * AutoLoginAuth （Cookie による自動ログイン処理）を使用する場合、通常のログイン処理をバイパス
 * されるので、このチェックフィルターでは対応できない。<br/>
 * AutoLoginAuth を使用する場合は、CookieLoginUser　インターフェースで定義されている、
 * matchCookieLoginPassword() の実装クラスから、このフィルターの lockChecker に設定している
 * クラスを使用して、ロック状態をチェックする必要がある。<br/>
 * 
 */
public class LockSupportAuth implements LockAuthentication {

	private static final Log log = LogFactory.getLog(LockSupportAuth.class);


	/**
	 * 委譲先となる認証クラス<br/>
	 * この Adaptor クラスは、DefaultAuth の派生系クラスをターゲットとしている。<br/>
	 * それ以外のクラスはサポートしていない。<br/>
	 */
	private DefaultAuth targetAuth;

	/** 認証用 DAO */
	protected DAO<LockSupportLoginUser> userDAO;

	/** 認証で使用するユーザー情報のログイン失敗回数 が格納されているDBの列名 */
	protected String userDAOFailCntField = "failCnt";    

	/** 認証で使用するユーザー情報の最終ログイン失敗日が格納されているDB の列名 */
	protected String userDAOLastFailDateField = "lastFailDate";    

	/** アカウントロックのチェッククラス */
	protected LockSupportChecker lockChecker; 



	/**
	 * アカウントロック対応、ログイン処理<br/>
	 * 委譲先の認証クラスを使用してログイン処理を行う。<br/>
	 * 認証処理が終了した場合、取得したログインユーザー情報のロックステータスをチェックする。<br/>
	 * もし、アカウントがロック状態の場合はログアウト処理を行い、 LockException をスローする。<br/>
	 * アカウントのロック状態が通常の場合、そのままログイン情報を復帰する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	 * @param form 入力フォーム
	 */
	@Override
	public LoginUser login(HttpServletRequest request, HttpServletResponse response, LoginForm form) {

		// 認証が成功すると、認証処理がＤＢ上の値（最終ログイン日など）を更新してしまう。
		// その為、認証処理の前に指定されたログイン ID が存在する場合、ロック状況をチェックする。
		LockSupportLoginUser loginUser = getUserInfo(form.getLoginID());

		if (loginUser != null && isLocked(loginUser)){
			// もしロック状態だった場合、LockException の例外をスローする。
			throw new LockException();
		}


		// 委譲先のログイン処理を実行し、認証処理を行う。
		loginUser = (LockSupportLoginUser)this.targetAuth.login(request, response, form);
		
		// 認証が失敗した場合
		if (loginUser == null) {

			// ログインID に該当するユーザーが存在する場合、失敗回数をカウントアップする。
			failCountUp(form);

			// 認証が失敗している場合は null を復帰して終了。
			return null;
		}


		// ログインが成功し、ロック状態でない場合、ログインの失敗情報がある場合はリセットする。
		changeToUnlock(loginUser);

		return loginUser;
	}


	
	/**
	 * ログイン済かのチェックを行う。<br/>
	 * Cookie による自動ログイン機能を使用している場合、このメソッドの実行時に自動ログインが成立する
	 * 場合がある。<br/>
	 * このメソッドは、自動ログインの成立後にアカウントがロック状態であれば強制的にログアウトする。<br/>
	 * もし、一時的であれ自動ログインを成立させたくない場合は、CookieLoginUser#matchCookieLoginPassword()
	 * 内からもロック状態のチェックを行う必要がある。<br/>
	 * <br/>
	 */
	@Override
	public LoginUser checkLoggedIn(HttpServletRequest request, HttpServletResponse response) {
		LoginUser loginUser = this.targetAuth.checkLoggedIn(request, response);
		
		if (loginUser != null){
			// 自動ログイン成立時に、アカウントがロック状態の場合は強制的にログアウトを行う。
			if (this.lockChecker.isLocked((LockSupportLoginUser)loginUser)){
				logout(request, response);
				return null;
			}
		}

		return loginUser;
	}


	
	/**
	 * ログイン失敗回数、最終ログイン失敗日を更新する。<br/>
	 * <ul>
	 * <li>ログインID に該当するユーザーが存在しない場合は何も更新せずに復帰する。</li>
	 * <li>既にログイン失敗回数が上限に達している場合、何も更新せずに復帰する。</li>
	 * <li>最終ログイン失敗日が null の場合、初回のログイン失敗なので、ログイン失敗のインターバルに関係なく
	 * ログイン失敗回数を加算する。</li>
	 * <li>最終ログイン失敗日からログイン失敗のインターバルで指定した時間が経過しているかチェックする。
	 * もし経過している場合、ログイン失敗回数は 1 にリセットする。　それ以外の場合は加算する。</li>
	 * <li>ログイン失敗のインターバル が 0 の場合はリセット機能が無効なので、その場合も失敗回数を加算する。</li>
	 * </ul>
	 * <br/>
	 * @param form 入力フォーム
	 * 
	 */
	protected void failCountUp(LoginForm form){

		// ログインID をキーとして、ユーザー情報を取得する。
		LockSupportLoginUser loginUser = getUserInfo(form.getLoginID());

		// 該当ユーザーが無い場合、ロック処理は行わないので何も更新しない。
		if (loginUser == null){
    		log.info("login id is not found.");
			return;
		}


		// 以下、該当ユーザーが見つかった場合

       	// 負荷や、エラー回数のオーバーフローの問題があるので、既にロック回数が閾値を超えている場合
		// は何も更新しない。　（本来は、最終ログイン失敗日は更新したい所だが...）
		if (isLocked(loginUser)) return;


   		// UPDATE 用パラメータオブジェクト
   		UpdateExpression[] expression;

		// カウントアップの判定フラグ
		boolean isCountUp = true;

		// システム日付
		Date sysDate = new Date();

		
		// 最終ログイン失敗日が null （初めてログインを失敗した）の場合、ログイン失敗のインターバルによる
		// リセットの対象外になるので、無条件にカウントアップ対象になる。
   		if (loginUser.getLastFailDate() != null) {

   	       	// 最終ログイン失敗日＋ログイン失敗のインターバルがシステム日付より小さいかをチェックする。
   	       	// 小さい場合、ログイン失敗回数は加算せずに１にリセットする。
   	       	long sysDateTime = sysDate.getTime();

   	       	Calendar calendar = Calendar.getInstance();
   	       	calendar.setTime(loginUser.getLastFailDate());
   	       	calendar.add(Calendar.MINUTE, this.lockChecker.getFailInterval());
   	       	long failDateTime = calendar.getTimeInMillis();
   	   		if (failDateTime <= sysDateTime && this.lockChecker.getFailInterval() != 0){
   	   			isCountUp = false;
   	   		}
   		}

   		
   		if (isCountUp){
   			log.info("failed count is count　up.");

   			// ログイン失敗回数をカウントアップする。
   			String failCntStr = "###" + this.userDAOFailCntField + "### = ###"	+ this.userDAOFailCntField + "### + 1";
   			expression = new UpdateExpression[] {new FormulaUpdateExpression(failCntStr),
   												 new UpdateValue(this.userDAOLastFailDateField, sysDate)};
   		} else {
   			log.info("failed count is reset.");

       		// ログイン失敗のインターバルを経過しているので、ログイン失敗回数を 1 として更新する。
   			expression = new UpdateExpression[] {new UpdateValue(this.userDAOFailCntField, 1),
   	        							   		 new UpdateValue(this.userDAOLastFailDateField, sysDate)};
   		}

   		// 失敗回数を更新
   		updateFailStatus(loginUser, expression);

	}



	/**
	 * 指定されたアカウントがロック中かチェックする。<br/>
	 * <br/>
	 * @param loginUser チェック対象ユーザー情報
	 * @return ロック中の場合、true を復帰する。
	 */
	@Override
	public boolean isLocked(LockSupportLoginUser loginUser){
		return this.lockChecker.isLocked(loginUser);
	}



	/**
	 * ロック状態に変更する<br/>
	 * <br/>
	 * ログイン失敗回数に上限値を設定し、最終ログイン失敗日にシステム日付を設定する。<br/>
	 * <br/>
	 * @param loginUser ステータス更新対象ユーザー情報
	 */
	@Override
	public void changeToLock(LockSupportLoginUser loginUser){

		// ステータスを強制的にロック状態に変更する。
		UpdateExpression[] expression
			= new UpdateExpression[]
					{new UpdateValue(this.userDAOFailCntField, this.lockChecker.getMaxFailCount()),
					 new UpdateValue(this.userDAOLastFailDateField, new Date())};

  		updateFailStatus(loginUser, expression);

   		log.info("change status to locked. ");

	}
	
	
	/**
	 * 通常状態に変更する<br/>
	 * ログイン失敗回数を 0、最終ログイン失敗日を null に更新する。
	 * <br/>
	 * @param loginUser ステータス更新対象ユーザー情報
	 */
	@Override
	public void changeToUnlock(LockSupportLoginUser loginUser) {

		// ログイン失敗回数に、0 以外の値が設定されているか、最終ログイン失敗日に値が設定されている場合、
		// ログイン失敗情報をリセットする。
		if ((loginUser.getFailCnt() != null && loginUser.getFailCnt() > 0) ||
				(loginUser.getLastFailDate() != null)){

			UpdateExpression[] expression
				= new UpdateExpression[] {new UpdateValue(this.userDAOFailCntField, 0),
										  new UpdateValue(this.userDAOLastFailDateField, null)};

	  		updateFailStatus(loginUser, expression);

       		log.info("Lock status is reset.");

		}
		
	}
	
	
	
	/**
	 * 指定されたログインID に該当するログインユーザー情報を取得する。<br/>
	 * 該当するユーザー情報が取得できた場合、そのオブジェクトを復帰する。<br/>
	 * <br/>
	 * @param LoginId 取得対象となるログインID
	 * @return　取得したログイン情報
	 */
	protected LockSupportLoginUser getUserInfo(String LoginId){

		// 入力されたログイン ID で該当データを取得する。
		// ログインID のフィールド名は、委譲先のクラスから取得する。
		DAOCriteria criteria = new DAOCriteria();
        criteria.addWhereClause(this.targetAuth.getUserDAOLoginIdField(), LoginId);

        List<LockSupportLoginUser> matchingUsers = this.userDAO.selectByFilter(criteria);


        if (matchingUsers.size() == 1){
        	// 入力したログインID に該当するデータが１件存在する場合

    		// アカウントロック用インターフェースが実装されているかをチェックする。
    		// もしインターフェースが実装されていない場合、例外をスローする。
    		if (!(matchingUsers.get(0) instanceof LockSupportLoginUser)){
    			throw new RuntimeException("UserLock Interface not used.");
    		}

        	// 取得したユーザー情報を復帰
        	return  matchingUsers.get(0);

        } else if (matchingUsers.size() > 1) {
            // 通常、ログインID には一意制約が張られているので、このケースに流れる事はない。
        	// もし流れた場合は例外をスローする。
        	throw new RuntimeException("duplicated login id.");
        	
        } else {
        	// 該当データが無い場合は null を復帰
        	return null;
        }
	}
	
	

	/**
	 * 指定されたユーザー情報を、指定された UpdateExpression の値で更新する。<br/>
	 * <br/>
	 * @param loginUser ログインユーザー情報
	 * @param expression 更新する値
	 */
	protected void updateFailStatus(LockSupportLoginUser loginUser, UpdateExpression[] expression) {

		// 取得したユーザー情報のユーザーID （主キー）で更新する。
		// 主キーのフィールド名は、委譲先のクラスから取得する。
		DAOCriteria criteria = new DAOCriteria();
        criteria.addWhereClause(this.targetAuth.getUserDAOUserIdField(), loginUser.getUserId());

		// 更新
		this.userDAO.updateByCriteria(criteria, expression);

	}
	
	
	
	// これ以降の処理は、処理を委譲するのみ。
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		this.targetAuth.logout(request, response);
	}

	@Override
	public LoginUser getLoggedInUser(HttpServletRequest request, HttpServletResponse response) {
		return this.targetAuth.getLoggedInUser(request, response);
	}

	@Override
	public UserRoleSet getLoggedInUserRole(HttpServletRequest request, HttpServletResponse response) {
		return this.targetAuth.getLoggedInUserRole(request, response);
	}

	
	
	/**
	 * 委譲先となる認証クラスを設定する。<br/>
	 * この Adaptor クラスは、DefaultAuth の派生系クラスを対象としているので、それ以外のクラスでは
	 * 使用できないので注意する事。<br/>
	 * <br/>
	 * @param targetAuth 委譲先となる認証クラス（DefaultAuth の派生クラス）
	 */
	public void setTargetAuth(DefaultAuth targetAuth) {
		this.targetAuth = targetAuth;
	}

	/**
	 * 認証用 DAO を設定する。<br/>
	 * <br/>
	 * @param userDAO 認証用 DAO
	 */
	public void setUserDAO(DAO<LockSupportLoginUser> userDAO) {
		this.userDAO = userDAO;
	}

	/**
	 * 認証用テーブルのログイン失敗回数フィールド名を設定する。<br/>
	 * <br/>
	 * @param userDAOFailCntField ログイン失敗回数フィールド名
	 */
	public void setUserDAOFailCntField(String userDAOFailCntField) {
		this.userDAOFailCntField = userDAOFailCntField;
	}

	/**
	 * 認証用テーブルの最終ログイン失敗日フィールド名を設定する。<br/>
	 * <br/>
	 * @param userDAOFailCntField 最終ログイン失敗日名
	 */
	public void setUserDAOLastFailDateField(String userDAOLastFailDateField) {
		this.userDAOLastFailDateField = userDAOLastFailDateField;
	}

	/**
	 * アカウントのロックチェッカーを設定する。<br/>
	 * <br/>
	 * @param lockChecker アカウントのロックチェック処理
	 */
	public void setLockChecker(LockSupportChecker lockChecker) {
		this.lockChecker = lockChecker;
	}

}
