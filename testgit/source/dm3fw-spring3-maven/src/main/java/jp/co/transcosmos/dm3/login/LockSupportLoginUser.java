package jp.co.transcosmos.dm3.login;

import java.util.Date;

/**
 * ログインユーザーのロックチェック用インターフェース.
 * <p>
 * パスワードの入力ミスを繰り返した場合、アカウントロックする機能を使用する場合、アカウントロックに関する
 * 情報を管理するテーブルのバリーオブジェクトはこのインターフェースを実装する必要がある。<br/>
 * LockCheckFilter は、セッションに格納されたログインユーザー情報からこのインターフェースを経由して
 * アカウントロックの状態をチェックする。<br/>
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
public interface LockSupportLoginUser extends LoginUser {

	/**
	 * アカウントのログイン失敗回数を取得する。<br/>
	 * <br/>
	 * @return アカウントのログイン失敗回数
	 */
	public Integer getFailCnt();

	/**
	 * アカウントの最終ログイン失敗日を取得する。<br/>
	 * <br/>
	 * @return アカウントの最終ログイン失敗日
	 */
	public Date getLastFailDate();
	
}
