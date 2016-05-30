package jp.co.transcosmos.dm3.core.model.adminUser.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.form.PagingListForm;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * 管理ユーザーメンテナンスの検索パラメータ、および、画面コントロールパラメータ受取り用フォーム.
 * <p>
 * 検索条件となるリクエストパラメータの取得や、ＤＢ検索オブジェクトの生成を行う。
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.28	Shamaison を参考に新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class AdminUserSearchForm extends PagingListForm<JoinResult> implements Validateable {

	// command リクエストパラメータは、URL マッピングで定義されている、namedCommands の制御にも使用される。
	// 管理ユーザーのメンテナンス機能では、以下の値が設定される場合がある。
	//
	// list : 管理ユーザー検索画面で実際に検索を行う場合に指定する。　（未指定の場合、検索画面を初期表示。）
	// back : 入力確認画面から入力画面へ復帰した場合。　（指定された場合、パラメータの値を初期値として入力画面に表示する。）

	/** 検索画面の command パラメータ */
	private String searchCommand;

	/** ログインID （検索条件） */
	private String keyLoginId;
	/** ユーザー名 （検索条件）*/
	private String keyUserName;
	/** メールアドレス （検索条件）*/
	private String keyEmail;
	/** 権限 （検索条件） */
	private String keyRoleId;
	/** 処理対象ユーザーID (LoginUser インターフェースの getUserId() が復帰する主キーの値) */
	private String userId;

	/** レングスバリデーションで使用する文字列長を取得するユーティリティ */
	protected LengthValidationUtils lengthUtils;
	
	

	/**
	 * デフォルトコンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	protected AdminUserSearchForm(){
		super();
	}



	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
	 */
	protected AdminUserSearchForm(LengthValidationUtils lengthUtils){
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
        // メールアドレス入力チェック
        validKeyEmail(errors);
        // 権限入力チェック
        validKeyRoleId(errors);

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
        ValidationChain valid = new ValidationChain(label,this.keyLoginId);

        // 桁数チェック
        valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 16)));

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
	    ValidationChain valid = new ValidationChain(label, this.keyUserName);

	    // 桁数チェック
	    valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 40)));

	    valid.validate(errors);
	}

	/**
	 * メールアドレス バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validKeyEmail(List<ValidationFailure> errors){
		String label = "user.search.keyEmail";
	    ValidationChain valid = new ValidationChain(label, this.keyEmail);

	    // 桁数チェック
	    valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 255)));

	    valid.validate(errors);
	}

	/**
	 * 権限 バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validKeyRoleId(List<ValidationFailure> errors){
		String label = "user.search.keyRoleId";
	    ValidationChain valid = new ValidationChain(label, this.keyRoleId);

	    // 桁数チェック
	    valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 20)));

	    valid.validate(errors);
	}

	
	
	/**
	 * 検索条件オブジェクトを作成する。<br/>
	 * PagingListForm に実装されている、ページ処理用の検索条件生成処理を拡張し、受け取ったリクエスト
	 * パラメータによる検索条件を生成する。<br/>
	 * <br/>
	 * @return 検索条件オブジェクト
	 */
	@Override
	public DAOCriteria buildCriteria(){

		// 検索用オブジェクトの生成
		// ページ処理を行う場合、PagingListForm　インターフェースを実装した Form から
		// DAOCriteria を生成する必要がある。 よって、自身の buildCriteria() を
		// 使用して検索条件を生成している。
		return buildCriteria(super.buildCriteria());
		
	}

	/**
	 * 検索条件オブジェクトを作成する。<br/>
	 * 引数で受け取った検索オブジェクトに対して、受け取ったリクエストパラメータから検索条件を生成して
	 * 設定する。<br/>
	 * <br/>
	 * このメソッド内では、DB の物理フィールド名を指定しているコードが存在する。<br/>
	 * その為、、管理ユーザー情報のテーブルとして、AdminLoginInfo 以外を使用した場合、このメソッドを
	 * オーバーライドする必要がある。<br/>
	 * <br/>
	 * @return 検索条件オブジェクト
	 */
	protected DAOCriteria buildCriteria(DAOCriteria criteria){
	
		// ログインIDの検索条件生成
		if (!StringValidateUtil.isEmpty(this.keyLoginId)) {
			criteria.addWhereClause("loginId", this.keyLoginId);
		}

		// ユーザー名の検索条件文生成
		if (!StringValidateUtil.isEmpty(this.keyUserName)) {
			criteria.addWhereClause("userName", "%" + this.keyUserName + "%", DAOCriteria.LIKE);
		}

		// メールアドレスの検索条件文生成
		if (!StringValidateUtil.isEmpty(this.keyEmail)) {
			criteria.addWhereClause("email", "%" + this.keyEmail + "%", DAOCriteria.LIKE);
		}

		// 権限の検索条件文生成
		if (!StringValidateUtil.isEmpty(this.keyRoleId)) {
			criteria.addWhereClause("roleId", this.keyRoleId);
		}

        return criteria;
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
	 * 検索画面で使用していた command パラメータの値を取得する。<br/>
	 * このパラメータ値は、検索画面に復帰する際、command パラメータとして渡す必要がある。<br/>
	 * <br/>
	 * @return command 制御用パラメータ
	 */
	public String getSearchCommand() {
		return this.searchCommand;
	}

	/**
	 * 検索画面で使用していた command パラメータの値を設定する。<br/>
	 * このパラメータ値は、検索画面に復帰する際、command パラメータとして渡す必要がある。<br/>
	 * <br/>
	 * @param searchCommand 制御用パラメータ
	 */
	public void setSearchCommand(String searchCommand) {
		this.searchCommand = searchCommand;
	}

	/**
	 * 処理対象となるログインID (LoginUser インターフェースの getUserId() が復帰する主キーの値)を取得する。<br/>
	 * <br/>
	 * @return 処理対象となるログインID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 処理対象となるログインID (LoginUser インターフェースの getUserId() が復帰する主キーの値)を設定する。<br/>
	 * <br/>
	 * @param userId 処理対象となるログインID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * ログインＩＤ（検索条件）を取得する。<br/>
	 * <br/>
	 * @return ログインＩＤ（検索条件）
	 */
	public String getKeyLoginId() {
		return this.keyLoginId;
	}

	/**
	 * ログインＩＤ（検索条件）を設定する。<br/>
	 * <br/>
	 * @param keyLoginId ログインＩＤ（検索条件）
	 */
	public void setKeyLoginId(String keyLoginId) {
		this.keyLoginId = keyLoginId;
	}

	/**
	 * ユーザー名（検索条件）を取得する。<br/>
	 * <br/>
	 * @return ユーザー名（検索条件）
	 */
	public String getKeyUserName() {
		return this.keyUserName;
	}

	/**
	 * ユーザー名（検索条件）を設定する。<br/>
	 * <br/>
	 * @param keyUserName ユーザー名（検索条件）
	 */
	public void setKeyUserName(String keyUserName) {
		this.keyUserName = keyUserName;
	}
	
	/**
	 * メールアドレス（検索条件）を取得する。<br/>
	 * <br/>
	 * @return メールアドレス（検索条件）
	 */
	public String getKeyEmail() {
		return this.keyEmail;
	}

	/**
	 * メールアドレス（検索条件）を設定する。<br/>
	 * <br/>
	 * @param keyEmail メールアドレス（検索条件）
	 */
	public void setKeyEmail(String keyEmail) {
		this.keyEmail = keyEmail;
	}

	/**
	 * ロールＩＤ（検索条件）を取得する。<br/>
	 * <br/>
	 * @return ロールID
	 */
	public String getKeyRoleId() {
		return this.keyRoleId;
	}

	/**
	 * ロールID（検索条件）を設定する。<br/>
	 * <br/>
	 * @param keyRoleId ロールID
	 */
	public void setKeyRoleId(String keyRoleId) {
		this.keyRoleId = keyRoleId;
	}
}