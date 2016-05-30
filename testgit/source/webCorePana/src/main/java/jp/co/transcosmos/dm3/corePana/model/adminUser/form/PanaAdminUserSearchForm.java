package jp.co.transcosmos.dm3.corePana.model.adminUser.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserSearchForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.validation.AsciiOnlyValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;
import jp.co.transcosmos.dm3.validation.ZenkakuOnlyValidation;

/**
 * 管理ユーザーメンテナンスの検索パラメータ、および、画面コントロールパラメータ受取り用フォーム.
 * <p>
 * 検索条件となるリクエストパラメータの取得や、ＤＢ検索オブジェクトの生成を行う。
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * チョ夢		2015.04.28	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class PanaAdminUserSearchForm extends AdminUserSearchForm {

	/**
	 * デフォルトコンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	protected PanaAdminUserSearchForm(){
		super();
	}



	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
	 */
	protected PanaAdminUserSearchForm(LengthValidationUtils lengthUtils){
		super();
		this.lengthUtils = lengthUtils;
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

		// ログインID入力チェック
		validKeyLoginId(errors);
		// ユーザ名入力チェック
		validKeyUserName(errors);

		return (startSize == errors.size());
	}

	/**
	 * ログインID バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validKeyLoginId(List<ValidationFailure> errors) {
		String label = "user.search.keyLoginId";
		ValidationChain valid = new ValidationChain(label,this.getKeyLoginId());

		// 桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 16)));
		// 半角英数記号チェック
		valid.addValidation(new AsciiOnlyValidation());

		valid.validate(errors);
	}

	/**
	 * ユーザ名 バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validKeyUserName(List<ValidationFailure> errors){
		String label = "user.search.keyUserName";
		ValidationChain valid = new ValidationChain(label, this.getKeyUserName());

		// 桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 40)));
		// 全角文字チェック
		valid.addValidation(new ZenkakuOnlyValidation());

		valid.validate(errors);
	}
}
