package jp.co.transcosmos.dm3.corePana.model.information.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.information.form.InformationForm;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.Information;
import jp.co.transcosmos.dm3.core.vo.InformationTarget;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.validation.DateFromToValidation;
import jp.co.transcosmos.dm3.corePana.validation.UrlValidation;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.AsciiOnlyValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.util.StringUtils;

/**
 * <pre>
 * お知らせメンテナンスの入力パラメータ受取り用フォーム
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * zhang		2015.04.21	新規作成
 *
 * 注意事項
 * この Form のバリデーションは、使用するモード（追加処理 or 更新処理）で異なるので、
 * フレームワークが提供する Validateable インターフェースは実装していない。
 * バリデーション実行時のパラメータが通常と異なるので注意する事。
 *
 * </pre>
 */
public class PanaInformationForm extends InformationForm implements
		Validateable {

	/** メールアドレス */
	private String email;

	/**
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email セットする email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * バリデーション処理<br/>
	 * リクエストパラメータのバリデーションを行う<br/>
	 * <br/>
	 * この Form クラスのバリデーションメソッドは、Validateable インターフェースの実装では無いので、
	 * バリデーション実行時の引数が異なるので、複数 Form をまとめてバリデーションする場合などは注意
	 * する事。<br/>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 * @param mode 処理モード ("insert" or "update")
	 * @return 正常時 true、エラー時 false
	 */
	@Override
	public boolean validate(List<ValidationFailure> errors) {
		int startSize = errors.size();
		// お知らせ種別入力チェック
        validInformationType(errors);

        // 公開対象区分入力チェック
        validDspFlg(errors);

        // 特定会員の場合
        if (!StringUtils.isEmpty(this.getDspFlg()) && PanaCommonConstant.DSP_FLG_PRIVATE.equals(this.getDspFlg())) {
            // 会員入力チェック
        	if (StringValidateUtil.isEmpty(this.getUserId())) {
                ValidationFailure vf = new ValidationFailure(
                        "informationError", "特定会員", "特定会員テキスト", null);
                errors.add(vf);
            }
        }

        // タイトル入力チェック
        validTitle(errors);

        // お知らせ内容入力チェック
        validInformationMsg(errors);

        // 開始日 入力チェック
        validStartDate(errors);

        // 終了日 入力チェック
        validEndDate(errors);

		// 表示期間の日付比較チェック
		ValidationChain valMemberFnameKana = new ValidationChain("information.input.startDate",this.getStartDate());
        valMemberFnameKana.addValidation(new DateFromToValidation("yyyy/MM/dd", "表示期間終了日", this.getEndDate()));
        valMemberFnameKana.validate(errors);

        // リンク先 URL 入力チェック
        validInputUrl(errors);

        //会員とメール送信の関連チェック
        validDspMail(errors);

		return (startSize == errors.size());

	}

	/**
	 * リンク先 URL バリデーション<br/>
	 * ・桁数チェック
	 * ・半角英数記号チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validInputUrl(List<ValidationFailure> errors) {
        // リンク先 URL 入力チェック
        ValidationChain valUrl = new ValidationChain("information.input.url", this.getUrl());
        // 桁数チェック
        valUrl.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("information.input.url", 255)));
        // 半角英数記号チェック
        valUrl.addValidation(new AsciiOnlyValidation());
        // URLのフォーマットチェック
        if (!StringValidateUtil.isEmpty(this.getUrl())) {
        	valUrl.addValidation(new UrlValidation());
        }
        valUrl.validate(errors);
	}

	/**
	 * 会員とメール送信の関連チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validDspMail(List<ValidationFailure> errors) {
        // リンク先 URL 入力チェック
        if(PanaCommonConstant.SEND_FLG_1.equals(this.getMailFlg()) && !PanaCommonConstant.DSP_FLG_PRIVATE.equals(this.getDspFlg()))
        {
        	ValidationFailure vf = new ValidationFailure(
                    "informationMailError", "メール送信", "特定会員", null);
            errors.add(vf);
        }
	}



	/**
	 * 渡されたバリーオブジェクトから Form へ初期値を設定する。<br/>
	 * <br/>
	 *
	 * @param information
	 *            Information　を実装した、お知らせ情報管理用バリーオブジェクト
	 * @param informationTarget
	 *            InformationTarget を実装したお知らせ公開先情報管理用バリーオブジェクト
	 * @param mypageUserInterface
	 *            MypageUserInterface を実装したマイページ会員情報管理用バリーオブジェクト
	 */
	@Override
	public void setDefaultData(Information information,
			InformationTarget informationTarget,
			MypageUserInterface mypageUserInterface) {

		super.setDefaultData(information, informationTarget,
				mypageUserInterface);

		// メール送信FLG
		this.setMailFlg(((jp.co.transcosmos.dm3.corePana.vo.Information) information)
				.getSendFlg());
		// メールアドレス
		this.setEmail(mypageUserInterface.getEmail());

	}

	/**
	 * 引数で渡されたお知らせ情報のバリーオブジェクトにフォームの値を設定する。<br/>
	 * 更新処理でも使用する事を考慮し、タイムスタンプ情報に関しては、更新日、更新者のみ設定する。<br/>
	 * <br/>
	 *
	 * @param information
	 *            値を設定するお知らせ情報のバリーオブジェクト
	 *
	 */
	@Override
	public void copyToInformation(Information information, String editUserId) {

		super.copyToInformation(information, editUserId);

		((jp.co.transcosmos.dm3.corePana.vo.Information) information)
				.setSendFlg(this.getMailFlg());

	}

	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 *
	 * @param lengthUtils
	 *            レングスバリデーションで使用する文字列長を取得するユーティリティ
	 * @param codeLookupManager
	 *            共通コード変換処理
	 */
	PanaInformationForm(LengthValidationUtils lengthUtils,
			CodeLookupManager codeLookupManager) {
		super(lengthUtils, codeLookupManager);
		this.setMailFlg("0");
	}

}
