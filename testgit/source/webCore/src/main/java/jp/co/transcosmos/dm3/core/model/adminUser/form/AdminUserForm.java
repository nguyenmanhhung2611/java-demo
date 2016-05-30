package jp.co.transcosmos.dm3.core.model.adminUser.form;

import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserRoleInterface;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.validation.CompareValidation;
import jp.co.transcosmos.dm3.core.validation.PasswordValidation;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.UpdateExpression;
import jp.co.transcosmos.dm3.dao.UpdateValue;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.EncodingUtils;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.AlphanumericMarkOnlyValidation;
import jp.co.transcosmos.dm3.validation.AsciiOnlyValidation;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.EmailRFCValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.MinLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;
import jp.co.transcosmos.dm3.validation.ZenkakuOnlyValidation;

/**
 * 管理ユーザーメンテナンスの入力パラメータ受取り用フォーム.
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.28	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * この Form のバリデーションは、使用するモード（追加処理 or 更新処理）で異なるので、
 * フレームワークが提供する Validateable インターフェースは実装していない。<br/>
 * バリデーション実行時のパラメータが通常と異なるので注意する事。<br/>
 *
 */
public class AdminUserForm {

	// command リクエストパラメータは、URL マッピングで定義されている、namedCommands の制御にも使用される。
	// 管理ユーザーのメンテナンス機能では、以下の値が設定される場合がある。
	//
	// list : 管理ユーザー検索画面で実際に検索を行う場合に指定する。　（未指定の場合、検索画面を初期表示。）
	// back : 入力確認画面から入力画面へ復帰した場合。　（指定された場合、パラメータの値を初期値として入力画面に表示する。）

	/** command パラメータ */
	private String command;

	/** ロールID （AdminUserRoleInterface　インターフェースの getRoleId() に対応） */
	private String roleId;

	/** 処理対象ユーザーID (LoginUser インターフェースの getUserId() に対応) */
	private String userId;

	/** ログインID (LoginUser インターフェースの getLoginId() に対応) */
	private String loginId;
	/** パスワード  （AdminUserRoleInterface　インターフェースの getPassword() に対応）  */
	private String password;
	/** パスワード確認  */
	private String passwordChk;
	/** ユーザー名 （AdminUserInterface インターフェースの getUserName() に対応） */
	private String userName;
	/** メールアドレス （AdminUserInterface インターフェースの getEmail() に対応） */
	private String email;
	/** 備考 （AdminUserInterface インターフェースの getNote() に対応） */
	private String note;

	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;
	/** 共通パラメータオブジェクト */
	protected CommonParameters commonParameters;
	/** レングスバリデーションで使用する文字列長を取得するユーティリティ */
	protected LengthValidationUtils lengthUtils;



	/**
	 * デフォルトコンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	protected AdminUserForm(){
		super();
	}



	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
	 * @param　codeLookupManager　共通コード変換処理
	 * @param commonParameters 共通パラメータオブジェクト
	 */
	protected AdminUserForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager, CommonParameters commonParameters){
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
		this.commonParameters = commonParameters;
	}



	/**
	 * 渡されたバリーオブジェクトから Form へ初期値を設定する。<br/>
	 * <br/>
	 * @param adminUser AdminUserInterface　を実装した、ユーザー情報管理用バリーオブジェクト
	 * @param adminRole AdminUserRoleInterface を実装したユーザーロール管理用バリーオブジェクト
	 */
	public void setDefaultData(AdminUserInterface adminUser, AdminUserRoleInterface adminRole){

		// バリーオブジェクトに格納されているパスワードはハッシュ値なので Form には設定しない。
		// パスワードの入力は新規登録時のみ必須で、更新処理時は任意の入力となる。
		// 更新処理でパスワードの入力が行われない場合、パスワードの更新は行わない。

		// ユーザーID を設定
		this.userId = (String) adminUser.getUserId();
		// ログインID を設定
		this.loginId = adminUser.getLoginId();
		// ユーザー名称を設定
		this.userName = adminUser.getUserName();
		// メールアドレスを設定
		this.email = adminUser.getEmail();
		// 備考
		this.note = adminUser.getNote();
		// 権限（ロールID）
		this.roleId = adminRole.getRoleId();

	}



	/**
	 * 引数で渡された管理ユーザーのバリーオブジェクトにフォームの値を設定する。<br/>
	 * 更新処理でも使用する事を考慮し、タイムスタンプ情報に関しては、更新日、更新者のみ設定する。<br/>
	 * <br/>
	 * @return AdminUserInterface を実装した管理ユーザーオブジェクト
	 */
	public void copyToAdminUserInfo(AdminUserInterface adminUser){

		// 新規登録時は DAO 側で自動採番するので、ユーザーID は設定しない。
		
		// ログインID を設定
		adminUser.setLoginId(this.loginId);

		// パスワード を設定
		// 更新処理の場合、パスワードは変更する場合のみの入力となる。　よって、パスワードの場合は入力があった場合のみ
		// 値を設定する。
		// （パスワードの入力が無い場合は、引数に設定されている値をそのまま使用すると言う事。）
		if (!StringValidateUtil.isEmpty(this.password)){
			adminUser.setPassword(EncodingUtils.md5Encode(this.password));

			// パスワード有効期限を設定
			adminUser.setLastPasswdChange(new Date());
		}

		// ユーザー名を設定
		adminUser.setUserName(this.userName);

		// メールアドレスを設定
		adminUser.setEmail(this.email);

		// 備考を設定
		adminUser.setNote(this.note);

	}



	/**
	 * 引数で渡された管理者ロールID の配列に、フォームの情報を設定する。<br/>
	 * <br/>
	 * @return ロール情報バリーオブジェクト
	 */
	public void copyToAdminRoleInfo(AdminUserRoleInterface adminRole){

		adminRole.setUserId(this.userId);
		adminRole.setRoleId(this.roleId);

	}



	/**
	 * 管理ユーザー情報の主キー値となる検索条件を生成する。<br/>
	 * <br/>
	 * このメソッド内では、DB の物理フィールド名を指定しているコードが存在する。<br/>
	 * その為、、管理ユーザー情報のテーブルとして、AdminLoginInfo 以外を使用した場合、このメソッドを
	 * オーバーライドする必要がある。<br/>
	 * <br/>
	 * @return 主キーとなる検索条件オブジェクト
	 */
	public DAOCriteria buildPkCriteria(){
		// 検索条件オブジェクトを取得
		DAOCriteria criteria = new DAOCriteria();

		// ユーザー管理をしているテーブルの主キーの検索条件を生成する。
		criteria.addWhereClause("adminUserId", this.userId);

		return criteria;
	}

	
	
	/**
	 * 指定したログインID が存在するかをチェックする為の検索条件を生成する。<br/>
	 * <br/>
	 * ・Form 内でユーザーID 未指定時は、新規登録時を想定し、ログインID が存在するかを検索する条件を生成する。<br/>
	 * ・Form 内でユーザーID 指定時は、変更時を想定し、自分以外でログインID が存在するかを検索する条件を生成する。<br/>
	 * <br/>
	 * @return ログインID の存在をチェックする検索条件オブジェクト
	 */
	public DAOCriteria buildFreeLoginIdCriteria(){
		// 検索条件オブジェクトを取得
		DAOCriteria criteria = new DAOCriteria();

		criteria.addWhereClause("loginId", this.loginId);
		
		// ユーザーID が指定されている場合、自分以外で使用されているかをチェックする。
		if (!StringValidateUtil.isEmpty(this.userId)){
			criteria.addWhereClause("adminUserId", this.userId, DAOCriteria.EQUALS, true);
		}

		return criteria;
	}



	/**
	 * 管理ユーザー情報の更新タイムスタンプ用 UpdateExpression を生成する。<br/>
	 * <br/>
	 * 管理ユーザー情報のテーブルとして、AdminLoginInfo 以外を使用した場合、このメソッドを
	 * オーバーライドする必要がある。<br/>
	 * <br/>
	 * @return 更新タイムスタンプ UPDATE 用　UpdateExpression
	 */
	public UpdateExpression[] buildTimestampUpdateExpression(String editUserId){

        // システム日付
        Date sysDate = new Date();

        // このメソッドの場合、バリーオブジェクトを受け取っていないので、AdminLoginInfo が使用されて
        // いるのか判断ができない。　別テーブルを使用する場合は必ずオーバーライドする事。
        return new UpdateExpression[] {new UpdateValue("updUserId", editUserId),
        							   new UpdateValue("updDate", sysDate)};
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

		// 権限チェック
		validRoleId(errors);
		// ログインＩＤ入力チェック
		validLoginId(errors);
		// パスワードの入力チェック
		validPassword(errors, mode);
		// パスワード確認チェック
		validComparePassword(errors);
		// ユーザ名入力チェック
		validUserName(errors);
		// メールアドレス入力チェック
		validEmail(errors);
		// 備考チェック
		validNote(errors);

		return (startSize == errors.size());

	}

	/**
	 * 権限 バリデーション<br/>
	 * <ul>
	 *   <li>必須チェック</li>
	 *   <li>パターンチェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validRoleId(List<ValidationFailure> errors){
		String label = "user.input.roleId";
		ValidationChain valid = new ValidationChain(label, this.roleId);

		// 必須チェック
		valid.addValidation(new NullOrEmptyCheckValidation());
		// パターンチェック
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "roleId"));

		valid.validate(errors);
	}

	/**
	 * ログインＩＤ バリデーション<br/>
	 * <ul>
	 *   <li>必須チェック</li>
	 *   <li>最小桁数チェック</li>
	 *   <li>最大桁数チェック</li>
	 *   <li>半角英数記号チェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validLoginId(List<ValidationFailure> errors){
		String label = "user.input.loginId";
		ValidationChain valid = new ValidationChain(label, this.loginId);

		// 必須チェック
		valid.addValidation(new NullOrEmptyCheckValidation());
		// 最小桁数チェック
		valid.addValidation(new MinLengthValidation(this.commonParameters.getMinAdminPwdLength()));
		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 16)));
		// 半角英数記号チェック
		valid.addValidation(new AsciiOnlyValidation());
		
		valid.validate(errors);
	}

	/**
	 * パスワード バリデーション<br/>
	 * <ul>
	 *   <li>必須チェック （新規登録時）</li>
	 *   <li>最小桁数チェック</li>
	 *   <li>最大桁数チェック</li>
	 *   <li>半角英数記号チェック</li>
	 *   <li>パスワード強度チェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 * @param mode 処理モード ("insert" or "update")
	 */
	protected void validPassword(List<ValidationFailure> errors,String mode){
		String label = "user.input.password";
		ValidationChain valid = new ValidationChain(label, this.password);

		if (mode.equals("insert")) {
			// 新規登録時は必須チェックを行う。
			// 更新登録時のパスワード入力は任意となり、入力された場合のみパスワードを更新する。

			// 必須チェック
			valid.addValidation(new NullOrEmptyCheckValidation());
		}

		// パスワードの入力があった場合、パスワードのバリデーションを行う
		if (!StringValidateUtil.isEmpty(this.password)){

			// 最小桁数チェック
			valid.addValidation(new MinLengthValidation(this.commonParameters.getMinAdminPwdLength()));

			// 最大桁数チェック
			valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 16)));

			// 新パスワードの半角英数記号文字チェック
			// 利用可能な記号文字は、共通パラメータオブジェクトから取得する。
			valid.addValidation(new AlphanumericMarkOnlyValidation(this.commonParameters.getAdminPwdUseMarks()));

			// パスワード強度チェックのバリデーションオブジェクトが取得できた場合、パスワード強度のバリデーションを実行する。
			Validation pwdValidation = createPwdValidation();
			if (pwdValidation != null){
				valid.addValidation(pwdValidation);
			}
		}
		valid.validate(errors);
	}

	/**
	 * パスワード確認 バリデーション<br/>
	 * <ul>
	 *   <li>必須チェック</li>
	 *   <li>パスワードと、確認入力の照合チェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validComparePassword(List<ValidationFailure> errors){
		// パスワードの入力があった場合、パスワード確認のバリデーションを行う
		if (!StringValidateUtil.isEmpty(this.password)){

			String label = "user.input.rePassword";
			ValidationChain valid = new ValidationChain(label, this.passwordChk);
			// 必須チェック
			valid.addValidation(new NullOrEmptyCheckValidation());

			// パスワードと、確認入力の照合チェック
	        // 比較対象フィールド名を取得
	        String cmplabel = this.codeLookupManager.lookupValue("errorLabels", "user.input.password");
			valid.addValidation(new CompareValidation(this.password, cmplabel));

			valid.validate(errors);
		}
	}

	/**
	 * ユーザ名 バリデーション<br/>
	 * <ul>
	 *   <li>必須チェック</li>
	 *   <li>全角チェック</li>
	 *   <li>桁数チェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validUserName(List<ValidationFailure> errors){
		String label = "user.input.adminUserName";
		ValidationChain valid = new ValidationChain(label, this.userName);

		// 必須チェック
		valid.addValidation(new NullOrEmptyCheckValidation());
		// 全角チェック
		valid.addValidation(new ZenkakuOnlyValidation());
		// 桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 40)));

		valid.validate(errors);
	}

	/**
	 * メールアドレス バリデーション<br/>
	 * <ul>
	 *   <li>桁数チェック</li>
	 *   <li>メールアドレスの書式チェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validEmail(List<ValidationFailure> errors){
		String label = "user.input.email";
		ValidationChain valid = new ValidationChain(label, this.email);

		// 必須チェック
		valid.addValidation(new NullOrEmptyCheckValidation());
		// 桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 255)));
		// メールアドレスの書式チェック
		valid.addValidation(new EmailRFCValidation());

		valid.validate(errors);
	}

	/**
	 * 備考 バリデーション<br/>
	 * <ul>
	 *   <li>桁数チェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validNote(List<ValidationFailure> errors){
		String label = "user.input.note";
		ValidationChain valid = new ValidationChain(label, this.note);

		// 桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 200)));

		valid.validate(errors);
	}

	

	/**
	 * パスワード強度チェックバリデーションのインスタンスを生成する。<br/>
	 * パスワード強度チェックを行わない場合、このメソッドの戻り値が null になる様にオーバーライドする事。<br/>
	 * <br/>
	 * @return　PasswordValidation　のインスタンス
	 */
	protected Validation createPwdValidation(){
		return new PasswordValidation(this.commonParameters.getAdminPwdMastMarks());
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
	 * ロールID を取得する。<br/>
	 * <br/>
	 * @return ロールID
	 */
	public String getRoleId() {
		return this.roleId;
	}

	/**
	 * ロールID を設定する。<br/>
	 * <br/>
	 * @param roleId ロールID
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * 処理対象となるユーザーID（主キー値）を取得する。<br/>
	 * <br/>
	 * @return 処理対象となるユーザーID
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * 処理対象となるユーザーID（主キー値）を設定する。<br/>
	 * <br/>
	 * @param userId 処理対象となるユーザーID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * ログインIDを取得する。<br/>
	 * <br/>
	 * @return ログインID
	 */
	public String getLoginId() {
		return this.loginId;
	}

	/**
	 * ログインIDを設定する。<br/>
	 * <br/>
	 * @param loginId ログインID
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	/**
	 * パスワードを取得する。<br/>
	 * <br/>
	 * @return パスワード（ハッシュされる前の入力値）
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * パスワードを設定する。<br/>
	 * <br/>
	 * @param password パスワード（ハッシュされる前の入力値）
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
		return this.passwordChk;
	}

	/**
	 * パスワード確認を設定する。<br/>
	 * <br/>
	 * @param passwordChk パスワード確認
	 */
	public void setPasswordChk(String passwordChk) {
		this.passwordChk = passwordChk;
	}

	/**
	 * ユーザー名を取得する。<br/>
	 * <br/>
	 * @return ユーザー名
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * ユーザー名を設定する。<br/>
	 * <br/>
	 * @param adminUserName ユーザー名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * メールアドレスを取得する。<br/>
	 * <br/>
	 * @return メールアドレス
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * メールアドレスを設定する。<br/>
	 * <br/>
	 * @param email メールアドレス
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 備考を取得する。<br/>
	 * <br/>
	 * @return 備考
	 */
	public String getNote() {
		return this.note;
	}

	/**
	 * 備考を設定する。<br/>
	 * <br/>
	 * @param note 備考
	 */
	public void setNote(String note) {
		this.note = note;
	}
}
