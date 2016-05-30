package jp.co.transcosmos.dm3.core.model.adminUser.form;

import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.validation.CompareValidation;
import jp.co.transcosmos.dm3.core.validation.PasswordValidation;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.UpdateExpression;
import jp.co.transcosmos.dm3.dao.UpdateValue;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.EncodingUtils;
import jp.co.transcosmos.dm3.validation.AlphanumericMarkOnlyValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.MinLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * 管理ユーザーパスワード変更のパラメータ受取り用フォーム.
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.22	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class PwdChangeForm implements Validateable {

	/** 共通パラメータオブジェクト */
	protected CommonParameters commonParameters;
	/** レングスバリデーションで使用する文字列長を取得するユーティリティ */
	protected LengthValidationUtils lengthUtils;
	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;

	/** 旧パスワード */
	private String oldPassword;
	/** 新パスワード */
	private String newPassword;
	/** 新パスワード（確認） */
	private String newRePassword;



	/**
	 * デフォルトコンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	protected PwdChangeForm(){
		super();
	}




	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
	 * @param commonParameters 共通パラメータオブジェクト
	 * @param codeLookupManager 共通コード変換処理
	 */
	protected PwdChangeForm(LengthValidationUtils lengthUtils,
							CommonParameters commonParameters,
							CodeLookupManager codeLookupManager){
		super();
		this.lengthUtils = lengthUtils;
		this.commonParameters = commonParameters;
		this.codeLookupManager = codeLookupManager;
	}


	
	/**
	 * 管理ユーザー情報の主キー値となる検索条件を生成する。<br/>
	 * <br/>
	 * このメソッド内では、DB の物理フィールド名を指定しているコードが存在する。<br/>
	 * その為、、管理ユーザー情報のテーブルとして、AdminLoginInfo 以外を使用した場合、このメソッドを
	 * オーバーライドする必要がある。<br/>
	 * <br/>
	 * @param userId 更新対象ユーザーID
	 * 
	 * @return 主キーとなる検索条件オブジェクト
	 */
	public DAOCriteria buildPkCriteria(String userId){
		// 検索条件オブジェクトを取得
		DAOCriteria criteria = new DAOCriteria();

		// ユーザー管理をしているテーブルの主キーの検索条件を生成する。
		criteria.addWhereClause("adminUserId", userId);

		return criteria;
	}



	/**
	 * 管理ユーザー情報のパスワード更新用 UpdateExpression を生成する。<br/>
	 * <br/>
	 * 管理ユーザー情報のテーブルとして、AdminLoginInfo 以外を使用した場合、このメソッドを
	 * オーバーライドする必要がある。<br/>
	 * <br/>
	 * @return パスワード更新用　UpdateExpression
	 */
	public UpdateExpression[] buildPwdUpdateExpression(String editUserId){

		// パスワードを暗号化する。
        String hashPassword = EncodingUtils.md5Encode(this.newPassword);

        // システム日付
        Date sysDate = new Date();

		// そのまま UPDATE すると、更新対象フィールド以外が null になってしまうので、
		// UpdateExpression　を使用して更新フィールドを限定する。
        // このメソッドの場合、バリーオブジェクトを受け取っていないので、AdminLoginInfo が使用されて
        // いるのか判断ができない。　別テーブルを使用する場合は必ずオーバーライドする事。
        return new UpdateExpression[] {new UpdateValue("password", hashPassword),
        							   new UpdateValue("lastPwdChangeDate", sysDate),
        							   new UpdateValue("updUserId", editUserId),
        							   new UpdateValue("updDate", sysDate)};
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

		// 現在のパスワード入力チェック
		validOldPassword(errors);
		// 新パスワード入力チェック
		validNewPassword(errors);
		// 新パスワード確認入力チェック
		validComparePassword(errors);

        return (startSize == errors.size());
	}

	/**
	 * 現在のパスワード バリデーション<br/>
	 * <ul>
	 *   <li>必須チェック</li>
	 *   <li>桁数チェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validOldPassword(List<ValidationFailure> errors) {
		 // 現在のパスワード入力チェック
		ValidationChain valid = new ValidationChain("user.pwd.oldPassword",this.oldPassword);
		valid.addValidation(new NullOrEmptyCheckValidation());		// 必須チェック
		// 桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("user.pwd.oldPassword", 16)));
		valid.validate(errors);
	}

	/**
	 * 新パスワード バリデーション<br/>
	 * <ul>
	 *   <li>必須チェック</li>
	 *   <li>最小桁数チェック</li>
	 *   <li>最大桁数チェック</li>
	 *   <li>新パスワードの半角英数記号文字チェック</li>
	 *   <li>旧パスワードと同じでない事</li>
	 *   <li>パスワード強度チェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validNewPassword(List<ValidationFailure> errors) {
		// 新パスワード入力チェック
		ValidationChain valid = new ValidationChain("user.pwd.newPassword", this.newPassword);

		// 必須チェック
		valid.addValidation(new NullOrEmptyCheckValidation());

		// 最小桁数チェック
		valid.addValidation(new MinLengthValidation(this.commonParameters.getMinAdminPwdLength()));

		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("user.pwd.newPassword", 16)));

		// 新パスワードの半角英数記号文字チェック
		// 利用可能な記号文字は、共通パラメータオブジェクトから取得する。
		valid.addValidation(new AlphanumericMarkOnlyValidation(this.commonParameters.getAdminPwdUseMarks()));

        // 新パスワードと旧パスワードが一致している場合、エラーとする。
        // 新パスワードの比較対象フィールド名を取得
        String label = this.codeLookupManager.lookupValue("errorLabels", "user.pwd.oldPassword");
        valid.addValidation(new CompareValidation(this.oldPassword, label, true));

		// パスワード強度チェックのバリデーションオブジェクトが取得できた場合、パスワード強度のバリデーションを実行する。
		Validation pwdValidation = createPwdValidation();
		if (pwdValidation != null){
			valid.addValidation(pwdValidation);
		}
		valid.validate(errors);
	}

	/**
	 * 新パスワード確認 バリデーション<br/>
	 * <ul>
	 *   <li>必須チェック</li>
	 *   <li>新パスワードの照合チェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validComparePassword(List<ValidationFailure> errors) {
		// 新パスワード確認入力チェック
		ValidationChain valid = new ValidationChain("user.pwd.newRePassword", this.newRePassword);
		// 必須チェック
		valid.addValidation(new NullOrEmptyCheckValidation());

        // 新パスワードの照合チェック
        // 再入力パスワードの比較対象フィールド名を取得
        String label = this.codeLookupManager.lookupValue("errorLabels", "user.pwd.newPassword");
        valid.addValidation(new CompareValidation(this.newPassword, label));

        valid.validate(errors);
	}

	/**
	 * パスワード強度チェックバリデーションのインスタンスを生成する。<br/>
	 * パスワード強度チェックを行わない場合、このメソッドの戻り値が null になる様にオーバーライドする事。<br/>
	 * <br/>
	 * @return
	 */
	protected Validation createPwdValidation(){
		return new PasswordValidation(this.commonParameters.getAdminPwdMastMarks());
	}



	/**
	 * 旧パスワードを取得する。<br/>
	 * <br/>
	 * @return 旧パスワード
	 */
	public String getOldPassword() {
		return oldPassword;
	}

	/**
	 * 旧パスワードを設定する。<br/>
	 * <br/>
	 * @param oldPassword 旧パスワード
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	/**
	 * 新パスワードを取得する。<br/>
	 * <br/>
	 * @return 新パスワード
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * 新パスワードを設定する。<br/>
	 * <br/>
	 * @param newPassword 新パスワード
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * 新パスワード（確認）を取得する。<br/>
	 * <br/>
	 * @return 新パスワード（確認）
	 */
	public String getNewRePassword() {
		return newRePassword;
	}

	/**
	 * 新パスワード（確認）を設定する。<br/>
	 * <br/>
	 * @param newRePassword 新パスワード（確認）
	 */
	public void setNewRePassword(String newRePassword) {
		this.newRePassword = newRePassword;
	}

}
