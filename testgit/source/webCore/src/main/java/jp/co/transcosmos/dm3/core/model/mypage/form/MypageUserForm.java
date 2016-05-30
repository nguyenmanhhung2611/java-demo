package jp.co.transcosmos.dm3.core.model.mypage.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.validation.CompareValidation;
import jp.co.transcosmos.dm3.core.validation.PasswordValidation;
import jp.co.transcosmos.dm3.core.validation.ZenkakuKanaValidator;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.EncodingUtils;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.AlphanumericMarkOnlyValidation;
import jp.co.transcosmos.dm3.validation.EmailRFCValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.MinLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;
import jp.co.transcosmos.dm3.validation.ZenkakuOnlyValidation;


/**
 * マイページ会員メンテナンスの入力パラメータ受取り用フォーム.
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.05	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * この Form のバリデーションは、使用するモード（追加処理 or 更新処理）で異なるので、
 * フレームワークが提供する Validateable インターフェースは実装していない。<br/>
 * バリデーション実行時のパラメータが通常と異なるので注意する事。<br/>
 * 
 */
public class MypageUserForm {

	// command リクエストパラメータは、URL マッピングで定義されている、namedCommands の制御にも使用される。
	// 管理ユーザーのメンテナンス機能では、以下の値が設定される場合がある。
	//
	// list : 管理ユーザー検索画面で実際に検索を行う場合に指定する。　（未指定の場合、検索画面を初期表示。）
	// back : 入力確認画面から入力画面へ復帰した場合。　（指定された場合、パラメータの値を初期値として入力画面に表示する。）

	/** command パラメータ */
	private String command;
	
	// note
	// userId は Form では持たない事。
	// これは、フロント側の場合から使用した場合、リクエストパラメータとして userId が混入するリスクを回避する為の配慮。
	
	/** メールアドレス (LoginUser インターフェースの getLoginId() に対応) */
	private String email;
	/** 会員名(姓） */
	private String memberLname;
	/** 会員名(名） */
	private String memberFname;
	/** 会員名・カナ(姓） */
	private String memberLnameKana;
	/** 会員名・カナ(名） */
	private String memberFnameKana;
	/** パスワード  */
	private String password;
	/** パスワード確認  */
	private String passwordChk;

	/** 共通パラメータオブジェクト */
	protected CommonParameters commonParameters;
	/** レングスバリデーションで使用する文字列長を取得するユーティリティ */
	protected LengthValidationUtils lengthUtils;
	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;


	
	/**
	 * デフォルトコンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 * 
	 */
	protected MypageUserForm(){
		super();
	}

	
	
	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
	 * @param commonParameters 共通パラメータオブジェクト
	 * @param codeLookupManager 共通コード変換処理
	 * 
	 */
	protected MypageUserForm(LengthValidationUtils lengthUtils,
							 CommonParameters commonParameters,
							 CodeLookupManager codeLookupManager){
		super();
		this.lengthUtils = lengthUtils;
		this.commonParameters = commonParameters;
		this.codeLookupManager = codeLookupManager;
	}

	
	
	/**
	 * 渡されたバリーオブジェクトから Form へ初期値を設定する。<br/>
	 * <br/>
	 * @param mypageUser MypageUserInterface　を実装した、マイページ会員情報用バリーオブジェクト
	 */
	public void setDefaultData(MypageUserInterface mypageUser){

		// バリーオブジェクトに格納されているパスワードはハッシュ値なので Form には設定しない。
		// パスワードの入力は新規登録時のみ必須で、更新処理時は任意の入力となる。
		// 更新処理でパスワードの入力が行われない場合、パスワードの更新は行わない。

		// メールアドレス
		this.email = mypageUser.getEmail();
		// 会員名(姓）
		this.memberLname = mypageUser.getMemberLname();
		// 会員名(名）
		this.memberFname = mypageUser.getMemberFname();
		// 会員名・カナ(姓）
		this.memberLnameKana = mypageUser.getMemberLnameKana();
		// 会員名・カナ(名）
		this.memberFnameKana = mypageUser.getMemberFnameKana();

	}


	
	/**
	 * 引数で渡されたマイページ会員のバリーオブジェクトにフォームの値を設定する。<br/>
	 * 更新処理でも使用する事を考慮し、タイムスタンプ情報に関しては、更新日、更新者のみ設定する。<br/>
	 * <br/>
	 * @return MypageUserInterface を実装したマイページ会員オブジェクト
	 */
	public void copyToMemberInfo(MypageUserInterface mypageUser){

		// 会員名（姓） を設定
		mypageUser.setMemberLname(this.memberLname);
		// 会員名（名）
		mypageUser.setMemberFname(this.memberFname);
		// 会員名・カナ（姓）
		mypageUser.setMemberLnameKana(this.memberLnameKana);
		// 会員名・カナ（名）
		mypageUser.setMemberFnameKana(this.memberFnameKana);
		// メールアドレス
		mypageUser.setEmail(this.email);

		// パスワード を設定
		// 更新処理の場合、パスワードは変更する場合のみの入力となる。　よって、パスワードの場合は入力があった場合のみ
		// 値を設定する。
		// （パスワードの入力が無い場合は、引数に設定されている値をそのまま使用すると言う事。）
		if (!StringValidateUtil.isEmpty(this.password)){
			mypageUser.setPassword(EncodingUtils.md5Encode(this.password));
		}
	}


	
	/**
	 * マイページ会員情報の主キー値となる検索条件を生成する。<br/>
	 * <br/>
	 * このメソッド内では、DB の物理フィールド名を指定しているコードが存在する。<br/>
	 * その為、、マイページ会員情報のテーブルとして、MemberInfo 以外を使用した場合、このメソッドを
	 * オーバーライドする必要がある。<br/>
	 * <br/>
	 * @param userId 更新対象となる主キー値
	 * 
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
	 * 指定したメールアドレス（ログインID）が存在するかをチェックする為の検索条件を生成する。<br/>
	 * <br/>
	 * ・引数でユーザーID が未指定の時は、新規登録時を想定し、メールアドレスが存在するかを検索する条件を生成する。<br/>
	 * ・引数でユーザーID が指定時された時は、変更時を想定し、自分以外でメールアドレスが存在するかを検索する条件を生成する。<br/>
	 * <br/>
	 * @return メールアドレス（ログインID）の存在をチェックする検索条件オブジェクト
	 */
	public DAOCriteria buildFreeLoginIdCriteria(String userId){
		// 検索条件オブジェクトを取得
		DAOCriteria criteria = new DAOCriteria();

		criteria.addWhereClause("email", this.email);
		
		// ユーザーID が指定されている場合、自分以外で使用されているかをチェックする。
		if (!StringValidateUtil.isEmpty(userId)){
			criteria.addWhereClause("userId", userId, DAOCriteria.EQUALS, true);
		}

		return criteria;
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
	public boolean validate(List<ValidationFailure> errors, String mode) {

        int startSize = errors.size();

        // メールアドレス入力チェック
        validEmail(errors);
        // パスワードの入力チェック
        validPassword(errors, mode);
        // パスワード確認の入力チェック
        validRePassword(errors);
        // 会員名（姓）入力チェック
        validMemberLname(errors);
        // 会員名（名）入力チェック
        validMemberFname(errors);
        // 会員名・カナ（姓）入力チェック
        validMemberLnameKana(errors);
        // 会員名・カナ（名）入力チェック
        validMemberFnameKana(errors);

        return (startSize == errors.size());

	}

	/**
	 * メールアドレス バリデーション<br/>
	 * <ul>
	 * <li>必須チェック</li>
	 * <li>桁数チェック</li>
	 * <li>メールアドレスの書式チェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validEmail(List<ValidationFailure> errors) {
		String label = "mypage.input.email";
        ValidationChain valUpdateMail = new ValidationChain(label, this.email);

        // 必須チェック
        valUpdateMail.addValidation(new NullOrEmptyCheckValidation());
        // 桁数チェック
        valUpdateMail.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 255)));
        // メールアドレスの書式チェック
        valUpdateMail.addValidation(new EmailRFCValidation());

        valUpdateMail.validate(errors);
	}

	/**
	 * パスワード バリデーション<br/>
	 * <ul>
	 * <li>必須チェック</li>
	 * <li>最小桁数チェック</li>
	 * <li>最大桁数チェック</li>
	 * <li>新パスワードの半角英数記号文字チェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 * @param mode 処理モード ("insert" or "update")
	 */
	protected void validPassword(List<ValidationFailure> errors, String mode) {
		String label = "mypage.input.password";
        ValidationChain valPassword = new ValidationChain(label, this.password);

        if (mode.equals("insert")) {
        	// 新規登録時は必須チェックを行う。
        	// 更新登録時のパスワード入力は任意となり、入力された場合のみパスワードを更新する。
        	valPassword.addValidation(new NullOrEmptyCheckValidation());						// 必須チェック
        }

        // パスワードの入力があった場合、パスワードのバリデーションを行う
        if (!StringValidateUtil.isEmpty(this.password)){

        	// 最小桁数チェック
        	valPassword.addValidation(new MinLengthValidation(this.commonParameters.getMinMypagePwdLength()));

        	// 最大桁数チェック
        	valPassword.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 16)));

        	// 新パスワードの半角英数記号文字チェック
        	// 利用可能な記号文字は、共通パラメータオブジェクトから取得する。
        	valPassword.addValidation(new AlphanumericMarkOnlyValidation(this.commonParameters.getMypagePwdUseMarks()));

        	// パスワード強度チェックのバリデーションオブジェクトが取得できた場合、パスワード強度のバリデーションを実行する。
            Validation pwdValidation = createPwdValidation();
        	if (pwdValidation != null){
        		valPassword.addValidation(pwdValidation);
        	}
        }
        valPassword.validate(errors);
	}

	/**
	 * パスワード確認 バリデーション<br/>
	 * パスワードの入力があった場合のみ、以下のバリデーションを実施する<br/>
	 * <ul>
	 * <li>必須チェック</li>
	 * <li>パスワードと、確認入力の照合チェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validRePassword(List<ValidationFailure> errors) {

		// パスワードの入力があった場合、パスワード確認のバリデーションを行う
        if (!StringValidateUtil.isEmpty(this.password)){

        	String label = "mypage.input.rePassword";
            ValidationChain valNewRePwd = new ValidationChain(label, this.passwordChk);

            // 必須チェック
            valNewRePwd.addValidation(new NullOrEmptyCheckValidation());

            // パスワードと、確認入力の照合チェック
            String cmplabel = this.codeLookupManager.lookupValue("errorLabels", "mypage.input.password");
            valNewRePwd.addValidation(new CompareValidation(this.password, cmplabel));

            valNewRePwd.validate(errors);
        }
	}

	/**
	 * 会員名（姓） バリデーション<br/>
	 * <ul>
	 * <li>必須チェック</li>
	 * <li>全角チェック</li>
	 * <li>桁数チェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validMemberLname(List<ValidationFailure> errors) {
		String label = "mypage.input.memberLname";
        ValidationChain valLName = new ValidationChain(label, this.memberLname);

        // 必須チェック
        valLName.addValidation(new NullOrEmptyCheckValidation());
		// 全角チェック
        valLName.addValidation(new ZenkakuOnlyValidation());
        // 桁数チェック
        valLName.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 40)));

        valLName.validate(errors);
	}

	/**
	 * 会員名（名） バリデーション<br/>
	 * <ul>
	 * <li>必須チェック</li>
	 * <li>全角チェック</li>
	 * <li>桁数チェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validMemberFname(List<ValidationFailure> errors) {
		String label = "mypage.input.memberFname";
        ValidationChain valFName = new ValidationChain(label, this.memberFname);

        // 必須チェック
        valFName.addValidation(new NullOrEmptyCheckValidation());
		// 全角チェック
        valFName.addValidation(new ZenkakuOnlyValidation());
        // 桁数チェック
        valFName.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 40)));

        valFName.validate(errors);
	}

	/**
	 * 会員名・カナ（姓）バリデーション<br/>
	 * <ul>
	 * <li>必須チェック</li>
	 * <li>全角カタカナチェック</li>
	 * <li>桁数チェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validMemberLnameKana(List<ValidationFailure> errors) {
		String label = "mypage.input.memberLnameKana";
        ValidationChain valLNameKana = new ValidationChain(label, this.memberLnameKana);

        // 必須チェック
        valLNameKana.addValidation(new NullOrEmptyCheckValidation());
		// 全角カタカナチェック
        valLNameKana.addValidation(new ZenkakuKanaValidator());
        // 桁数チェック
        valLNameKana.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 40)));

        valLNameKana.validate(errors);
	}

	/**
	 * 会員名・カナ（名） バリデーション<br/>
	 * <ul>
	 * <li>必須チェック</li>
	 * <li>全角カタカナチェック</li>
	 * <li>桁数チェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validMemberFnameKana(List<ValidationFailure> errors) {
		String label = "mypage.input.memberFnameKana";
        ValidationChain valFNameKana = new ValidationChain(label, this.memberFnameKana);

        // 必須チェック
        valFNameKana.addValidation(new NullOrEmptyCheckValidation());
		// 全角カタカナチェック
        valFNameKana.addValidation(new ZenkakuKanaValidator());
        // 桁数チェック
        valFNameKana.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 40)));

        valFNameKana.validate(errors);
	}



	/**
	 * パスワード強度チェックバリデーションのインスタンスを生成する。<br/>
	 * パスワード強度チェックを行わない場合、このメソッドの戻り値が null になる様にオーバーライドする事。<br/>
	 * <br/>
	 * @return PasswordValidation のインスタンス
	 */
	protected Validation createPwdValidation(){
		return new PasswordValidation(this.commonParameters.getMypagePwdMastMarks());
	}

	

	/**
	 * command パラメータを取得する。<br/>
	 * command パラメータの値は、フレームワークの URL マッピングで使用する command クラスの切り替えにも使用される。<br/>
	 * <br/>
	 * @return command パラメータ
	 */
	public String getCommand() {
		return this.command;
	}

	/**
	 * command パラメータを設定する。<br/>
	 * command パラメータの値は、フレームワークの URL マッピングで使用する command クラスの切り替えにも使用される。<br/>
	 * <br/>
	 * @param command command パラメータ
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * メールアドレスを取得する。　メールアドレスはログインID として使用される。<br/>
	 * <br/>
	 * @return メールアドレス
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * メールアドレスを設定する。　メールアドレスはログインID として使用される。<br/>
	 * <br/>
	 * @param email メールアドレス
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 会員名（姓）を取得する。<br/>
	 * <br/>
	 * @return 会員名（姓）
	 */
	public String getMemberLname() {
		return memberLname;
	}

	/**
	 * 会員名（姓）を設定する。<br/>
	 * @param memberLname 会員名（姓）
	 */
	public void setMemberLname(String memberLname) {
		this.memberLname = memberLname;
	}

	/**
	 * 会員名(名）を取得する。<br/>
	 * <br/>
	 * @return 会員名(名）
	 */
	public String getMemberFname() {
		return memberFname;
	}

	/**
	 * 会員名(名）を設定する。<br/>
	 * <br/>
	 * @param memberFname 会員名(名）
	 */
	public void setMemberFname(String memberFname) {
		this.memberFname = memberFname;
	}

	/**
	 * 会員名・カナ(姓）を取得する。<br/>
	 * <br/>
	 * @return 会員名・カナ(姓）
	 */
	public String getMemberLnameKana() {
		return memberLnameKana;
	}

	/**
	 * 会員名・カナ(姓）を設定する。<br/>
	 * <br/>
	 * @param memberLnameKana
	 */
	public void setMemberLnameKana(String memberLnameKana) {
		this.memberLnameKana = memberLnameKana;
	}

	/**
	 * 会員名・カナ(名）を取得する。<br/>
	 * <br/>
	 * @return 会員名・カナ(名）
	 */
	public String getMemberFnameKana() {
		return memberFnameKana;
	}

	/**
	 * 会員名・カナ(名）を設定する。<br/>
	 * <br/>
	 * @param memberFnameKana会員名・カナ(名）
	 */
	public void setMemberFnameKana(String memberFnameKana) {
		this.memberFnameKana = memberFnameKana;
	}

	/**
	 * パスワードを取得する。<br/>
	 * <br/>
	 * @return パスワード
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * パスワードを設定する。<br/>
	 * <br/>
	 * @param password パスワード
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * パスワード確認を取得する。<br/>
	 * <br/>
	 * @return パスワード確認
	 */
	public String getPasswordChk() {
		return passwordChk;
	}

	/**
	 * パスワード確認を設定する。<br/>
	 * <br/>
	 * @param passwordChk パスワード確認
	 */
	public void setPasswordChk(String passwordChk) {
		this.passwordChk = passwordChk;
	}

}
