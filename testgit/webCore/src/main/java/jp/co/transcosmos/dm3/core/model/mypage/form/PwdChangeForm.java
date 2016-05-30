package jp.co.transcosmos.dm3.core.model.mypage.form;

import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.UpdateExpression;
import jp.co.transcosmos.dm3.dao.UpdateValue;
import jp.co.transcosmos.dm3.utils.EncodingUtils;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * マイページ会員のパスワード変更用フォーム.
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.31	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class PwdChangeForm implements Validateable {

	/** 問合せID */
	private String remindId;
	/** 新パスワード */
	private String newPassword;
	/** 新パスワード（確認） */
	private String newPasswordChk;


	
	/**
	 * デフォルトコンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 * 
	 */
	protected PwdChangeForm(){
		super();
	}

	
	
	/**
	 * マイページ会員の主キー値となる検索条件を生成する。<br/>
	 * <br/>
	 * このメソッド内では、DB の物理フィールド名を指定しているコードが存在する。<br/>
	 * その為、、管理ユーザー情報のテーブルとして、MemberInfo 以外を使用した場合、このメソッドを
	 * オーバーライドする必要がある。<br/>
	 * <br/>
	 * @return 主キーとなる検索条件オブジェクト
	 */
	public DAOCriteria buildPkCriteria(String userId){
		// 検索条件オブジェクトを取得
		DAOCriteria criteria = new DAOCriteria();

		// ユーザー管理をしているテーブルの主キーの検索条件を生成する。
		criteria.addWhereClause("userId", userId);

		return criteria;
	}



	/**
	 * マイページ会員のパスワード更新 UpdateExpression を生成する。<br/>
	 * <br/>
	 * マイページ会員情報のテーブルとして、MemberInfo 以外を使用した場合、このメソッドを
	 * オーバーライドする必要がある。<br/>
	 * <br/>
	 * @param userId 更新者ID
	 * 
	 * @return 更新タイムスタンプ UPDATE 用　UpdateExpression
	 */
	public UpdateExpression[] buildPwdUpdateExpression(String userId){

		// パスワードを暗号化する。
        String hashPassword = EncodingUtils.md5Encode(this.newPassword);

        // システム日付
        Date sysDate = new Date();

        // このメソッドの場合、バリーオブジェクトを受け取っていないので、MemberInfo が使用されて
        // いるのか判断ができない。　別テーブルを使用する場合は必ずオーバーライドする事。
        return new UpdateExpression[] {new UpdateValue("password", hashPassword),
        							   new UpdateValue("updDate", sysDate),
        							   new UpdateValue("updUserId", userId)};
	}



	/**
	 * バリデーション処理<br/>
	 * リクエストパラメータのバリデーションを行う<br/>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 * @return 正常時 true、エラー時 false
	 */

	@Override
	public boolean validate(List<ValidationFailure> errors) {

		int startSize = errors.size();
		
		// TODO 入力ミスの問題もあるので、メールアドレスの整合性チェックのみは実装する事。

		return (startSize == errors.size());
	}



	/**
	 * 問合せID を取得する。<br/>
	 * <br/>
	 * @return 問合せID （パスワード変更用）
	 */
	public String getRemindId() {
		return remindId;
	}

	/**
	 * 問合せID を設定する。<br/>
	 * <br/>
	 * @param remindId 問合せID （パスワード変更用）
	 */
	public void setRemindId(String remindId) {
		this.remindId = remindId;
	}

	/**
	 * 新パスワードを取得する。<br/>
	 * <br/>
	 * @return 新パスワード （パスワード変更用）
	 */
	public String getNewPassword() {
		return newPassword;
	}
	
	/**
	 * 新パスワードを設定する。<br/>
	 * <br/>
	 * @param newPassword 新パスワード （パスワード変更用）
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * 新パスワード（確認）を取得する。<br/>
	 * <br/>
	 * @return　新パスワード （パスワード変更用）（確認）
	 */
	public String getNewPasswordChk() {
		return newPasswordChk;
	}

	/**
	 * 新パスワード（確認）を設定する。<br/>
	 * <br/>
	 * @return　新パスワード （パスワード変更用）（確認）
	 */
	public void setNewPasswordChk(String newPasswordChk) {
		this.newPasswordChk = newPasswordChk;
	}
}
