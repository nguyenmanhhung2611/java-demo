package jp.co.transcosmos.dm3.corePana.model.passwordRemind.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.validation.EmailRFCValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * パスワードリマインダー受取り用フォーム.
 * <p>
 *
 * <pre>
 * 担当者         修正日     修正内容
 * -------------- ----------- -----------------------------------------------------
 * 焦		   2015.04.17  新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class PasswordRemindForm extends MypageUserForm {

    /** レングスバリデーションで使用する文字列長を取得するユーティリティ */
    protected LengthValidationUtils lengthUtils;

	/** ユーザーメールアドレス */
	private String mailAddress;


	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	PasswordRemindForm(LengthValidationUtils lengthUtils) {
		this.lengthUtils = lengthUtils;
	}

	/**
	 * バリデーション処理<br/>
	 * リクエストのバリデーションを行う<br/>
	 * <br/>
	 *
	 * @param errors
	 *            エラー情報を格納するリストオブジェクト
	 * @return 正常時 true、エラー時 false
	 */
	public boolean validate(List<ValidationFailure> errors) {
		int startSize = errors.size();

		ValidationChain valMailAddress = new ValidationChain("passwordRemind.input.valMail", this.getMailAddress());

		// 必須
		valMailAddress.addValidation(new NullOrEmptyCheckValidation());
		// 桁数
		valMailAddress.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("passwordRemind.input.valMail", 255)));
		// メール形式
        valMailAddress.addValidation(new EmailRFCValidation());
        valMailAddress.validate(errors);

		return (startSize == errors.size());
	}
}
