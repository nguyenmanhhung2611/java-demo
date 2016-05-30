package jp.co.transcosmos.dm3.login.command.v3;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.PasswordExpire;
import jp.co.transcosmos.dm3.login.v3.Authentication;
import jp.co.transcosmos.dm3.servlet.BaseFilter;
import jp.co.transcosmos.dm3.servlet.FilterCommandChain;


/**
 * パスワード変更の期限チェックフィルター.
 * <p>
 * パスワード変更の期限のチェックを行う。<br/>
 * このフィルターは、ログイン認証がパスしている事を前提としているので、必ず LoginCheckFilter
 * の後に Filter 設定する事。<br/>
 * もし、期限切れの場合、　failure　の view 名で ModelAndView を復帰する。<br/>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.20	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * web.xml からの通常 Filter としての使用は非対応。
 * 
 */
public class PasswordExpireFilter extends BaseFilter {

	private static final Log log = LogFactory.getLog(PasswordExpireFilter.class);
	
	/** 認証ロジックオブジェクト */
    private Authentication authentication;
	
	/** パスワード有効日数 （デフォルト 30日）*/
	private Integer passwordExpireDays = 30;

	/**
	 * 期限切れ判定用キー値<br/>
	 * 期限切れが発生した場合、遷移先の処理でも判断できるように、この変数で定義した Key 名でセッションに
	 * true の boolean 値が格納される。<br/>
	 * 期限が切れていない場合、何も設定なれない。<br/>
	 */
	private String passwordExpireFailedKey = "passwordExpireFailed";

	
	
	


	/**
	 * パスワード有効期限のチェックを行う<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	 * @param chain チェーンオブジェクト
	 */
	@Override
	protected void filterAction(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException,	ServletException {

		// パスワード期限切れ判定用セッションオブジェクトを消去する。
		request.getSession().removeAttribute(this.passwordExpireFailedKey);


        // 認証用オブジェクトが設定されていない場合、例外を上げて終了（システムエラー）
		if (this.authentication == null) {
        	throw new RuntimeException("authentication property not setting!!"); 
        }

		
		// ログインユーザーの情報を取得する。
		LoginUser loginUser = this.authentication.getLoggedInUser(request, response);

		// ログイン済である事が前提なので、ログインしていない場合は例外をスローする。
		if (loginUser == null) {
			throw new RuntimeException("not login.");
		}


		// パスワード有効期限の Filter がインプリメントされていない場合、チェック不能なので、例外をスローする。
		if (!(loginUser instanceof PasswordExpire)){
			throw new RuntimeException("not PasswordExpire　interface.");
		}


		// 最後にパスワード変更した日付を取得する。
		Date lastChangeDate = ((PasswordExpire)loginUser).getLastPasswdChange();

		// パスワードの変更日が未設定の場合は期限切れとみなす。
		if (lastChangeDate == null){
			log.info("password change expire check failed. (lastChangeDate is null)");
			fail(request, (FilterCommandChain)chain);
			return;
		}


		// 最終変更日が閾値を超えている場合は期限切れとする。
		long sysDate = (new Date()).getTime();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(lastChangeDate);
		calendar.add(Calendar.DAY_OF_MONTH, this.passwordExpireDays);
		long chageDate = calendar.getTimeInMillis();

		if (chageDate < sysDate){
			log.info("password change expire check failed. (lastChangeDate is old)");
			fail(request, (FilterCommandChain)chain);
			return;
		}


		// 期限内であれば次のフィルターへ Chain する。
        log.info("password change expire OK");
      	chain.doFilter(request, response);

	}


	
	/**
	 * 期限切れが発生した場合の処理<br/>
	 * 期限切れが発生した場合、セッションにフラグを設定しておく。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param chain FilterChain
	 */
	private void fail(HttpServletRequest request, FilterCommandChain chain){
		chain.setResult(request, new ModelAndView("failure"));
		request.getSession().setAttribute(this.passwordExpireFailedKey, true);
	}
	

	/**
	 * 認証ロジックオブジェクトを設定する。<br/>
	 * <br/>
	 * @param authentication 認証ロジックオブジェクト
	 */
	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

	/**
	 * パスワード有効日数を設定する。<br/>
	 * <br/>
	 * @param passwordExpireDays パスワード有効日数（デフォルト 30日）
	 */
    public void setPasswordExpireDays(Integer passwordExpireDays) {
		this.passwordExpireDays = passwordExpireDays;
	}


    /**
     * パスワードの期限切れが発生した場合にセッションにフラグを設定する際の Key 名を変更する場合に設定する。<br/>
     * デフォルト値は passwordExpireFailed　で、通常、変更する事はない。<br/>
     * <br/>
     * @param passwordExpireFailedKey セッションにフラグを設定する際の Key 名
     */
	public void setPasswordExpireFailedKey(String passwordExpireFailedKey) {
		this.passwordExpireFailedKey = passwordExpireFailedKey;
	}

}
