package jp.co.transcosmos.dm3.corePana.model.passwordRemind.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.mypage.form.PwdChangeForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.validation.PasswordValidation;
import jp.co.transcosmos.dm3.corePana.validation.SameValueValidation;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.MinLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;


/**
 * マイページ会員情報メンテナンスの入力パラメータ受取り用フォーム.
 * <p>
 *
 * <pre>
 * 担当者         修正日     修正内容
 * -------------- ----------- -----------------------------------------------------
 * tang.tianyun   2015.04.17  新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class PasswordChangeForm extends PwdChangeForm {


	/** 共通コードオブジェクト */
	protected CodeLookupManager codeLookupManager;

	/** 共通パラメータオブジェクト */
	protected CommonParameters commonParameters;

	/** レングスバリデーションで使用する文字列長を取得するユーティリティ */
	protected LengthValidationUtils lengthUtils;

	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * デフォルトコンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 *
	 */
	protected PasswordChangeForm(LengthValidationUtils lengthUtils,
			CommonParameters commonParameters,
			CodeLookupManager codeLookupManager){
		super();
		this.lengthUtils = lengthUtils;
		this.commonParameters = commonParameters;
		this.codeLookupManager = codeLookupManager;
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

		ValidationChain valNewPassword = new ValidationChain("新しいパスワード",
				this.getNewPassword());
		// 必須チェック
		valNewPassword.addValidation(new NullOrEmptyCheckValidation());

		// 最小桁数チェック
		valNewPassword.addValidation(new MinLengthValidation(this.commonParameters.getMinAdminPwdLength()));

		// 最大桁数チェック
		valNewPassword.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("user.pwd.newPassword", 16)));

		String newPasswordChk = this.getNewPasswordChk();
		valNewPassword.addValidation(new SameValueValidation("新しいパスワードの確認", newPasswordChk));

		// パスワード強度チェックのバリデーションオブジェクトが取得できた場合、パスワード強度のバリデーションを実行する。
		Validation pwdValidation = createPwdValidation();
		if (pwdValidation != null){
			valNewPassword.addValidation(pwdValidation);
		}

		valNewPassword.validate(errors);

		ValidationChain valNewPasswordChk = new ValidationChain("新しいパスワードの確認",
				this.getNewPasswordChk());
		// 必須チェック
		valNewPasswordChk.addValidation(new NullOrEmptyCheckValidation());

		valNewPasswordChk.validate(errors);

		return (startSize == errors.size());
	}

	/**
	 * パスワード強度チェックバリデーションのインスタンスを生成する。<br/>
	 * パスワード強度チェックを行わない場合、このメソッドの戻り値が null になる様にオーバーライドする事。<br/>
	 * <br/>
	 * @return
	 */
	protected Validation createPwdValidation(){
		return new PasswordValidation(this.commonParameters.getMypagePwdMastMarks());
	}

}
