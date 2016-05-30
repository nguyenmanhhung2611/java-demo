package jp.co.transcosmos.dm3.core.model.information.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.form.PagingListForm;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.AlphanumericOnlyValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.ValidDateValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * <pre>
 * お知らせ情報の検索パラメータ、および、画面コントロールパラメータ受取り用フォーム
 * 検索条件となるリクエストパラメータの取得や、ＤＢ検索オブジェクトの生成を行う。
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.05	新規作成
 * H.Mizuno		2015.02.26	パッケージ移動、コンストラクターの隠蔽
 *
 * 注意事項
 *
 * </pre>
 */
public class InformationSearchForm extends PagingListForm<JoinResult> implements Validateable {
	// command リクエストパラメータは、URL マッピングで定義されている、namedCommands の制御にも使用される。
	// お知らせ情報の検索機能では、以下の値が設定される場合がある。
	//
	// list : お知らせ検索・一覧画面で実際に検索を行う場合に指定する。　（未指定の場合、検索画面を初期表示。）
	// back : 入力確認画面から入力画面へ復帰した場合。　（指定された場合、パラメータの値を初期値として入力画面に表示する。）

	/** 検索画面の command パラメータ */
	private String searchCommand;
	/** お知らせ番号 （検索条件） */
	private String keyInformationNo;
	/** タイトル （検索条件）*/
	private String keyTitle;
	/** 登録日from （検索条件）*/
	private String keyInsDateFrom;
	/** 登録日to （検索条件） */
	private String keyInsDateTo;
	/** 対象会員（検索条件） */
	private String keyUserId;
	/** お知らせ番号 */
	private String informationNo;

	/** レングスバリデーションで使用する文字列長を取得するユーティリティ */
	protected LengthValidationUtils lengthUtils;

	
	
	/**
	 * デフォルトコンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	protected InformationSearchForm(){
		super();
	}

	

	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
	 */
	protected InformationSearchForm(LengthValidationUtils lengthUtils){
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

        // お知らせ番号入力チェック
        validKeyInformationNo(errors);

        // タイトル入力チェック
        validKeyTitle(errors);

        // 登録日From入力チェック
        validKeyInsDateFrom(errors);

        // 登録日To入力チェック
        validKeyInsDateTo(errors);

        // 対象会員入力チェック
        validKeyUserId(errors);

        return (startSize == errors.size());
	}

	/**
	 * お知らせ番号 バリデーション<br/>
	 * ・英数字チェック
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validKeyInformationNo(List<ValidationFailure> errors) {
        // お知らせ番号入力チェック
        ValidationChain valInformationNo = new ValidationChain("information.search.keyInformationNo",this.keyInformationNo);
		// 英数字チェック
        valInformationNo.addValidation(new AlphanumericOnlyValidation());
        // 桁数チェック
        valInformationNo.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("information.search.keyInformationNo", 13)));
        valInformationNo.validate(errors);
	}

	/**
	 * タイトル バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validKeyTitle(List<ValidationFailure> errors) {
        // タイトル入力チェック
        ValidationChain valTitle = new ValidationChain("information.search.keyTitle", this.keyTitle);
        // 桁数チェック
        valTitle.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("information.search.keyTitle", 200)));
        valTitle.validate(errors);
	}

	/**
	 * 登録日From バリデーション<br/>
	 * ・日付書式チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validKeyInsDateFrom(List<ValidationFailure> errors) {
        // 登録日From入力チェック
        ValidationChain valInsDateFrom = new ValidationChain("information.search.keyInsDateFrom", this.keyInsDateFrom);
        // 日付書式チェック
        valInsDateFrom.addValidation(new ValidDateValidation("yyyy/MM/dd"));
        valInsDateFrom.validate(errors);
	}

	/**
	 * 登録日To バリデーション<br/>
	 * ・日付書式チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validKeyInsDateTo(List<ValidationFailure> errors) {
        // 登録日To入力チェック
        ValidationChain valInsDateTo = new ValidationChain("information.search.keyInsDateTo", this.keyInsDateTo);
		// 日付書式チェック
        valInsDateTo.addValidation(new ValidDateValidation("yyyy/MM/dd"));
        valInsDateTo.validate(errors);
	}

	/**
	 * 対象会員 バリデーション<br/>
	 * ・英数字チェック
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validKeyUserId(List<ValidationFailure> errors) {
	    // 対象会員入力チェック
        ValidationChain valUserId = new ValidationChain("information.search.keyUserId", this.keyUserId);
		// 英数字チェック
        valUserId.addValidation(new AlphanumericOnlyValidation());
        // 桁数チェック
        valUserId.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("information.search.keyUserId", 20)));
        valUserId.validate(errors);
	}


	/**
	 * 検索画面の commandを取得する。<br/>
	 * <br/>
	 * @param searchCommand 検索画面の command
	 */
	public String getSearchCommand() {
		return searchCommand;
	}

	/**
	 * 検索画面の commandを設定する。<br/>
	 * <br/>
	 * @return 検索画面の command
	 */
	public void setSearchCommand(String searchCommand) {
		this.searchCommand = searchCommand;
	}

	/**
	 * お知らせ番号 （検索条件）を取得する。<br/>
	 * <br/>
	 * @param keyInformationNo お知らせ番号 （検索条件）
	 */
	public String getKeyInformationNo() {
		return keyInformationNo;
	}

	/**
	 * お知らせ番号 （検索条件）を設定する。<br/>
	 * <br/>
	 * @return お知らせ番号 （検索条件）
	 */
	public void setKeyInformationNo(String informationNo) {
		this.keyInformationNo = informationNo;
	}

	/**
	 * タイトル （検索条件）を取得する。<br/>
	 * <br/>
	 * @param keyTitle タイトル （検索条件）
	 */
	public String getKeyTitle() {
		return keyTitle;
	}

	/**
	 * タイトル （検索条件）を設定する。<br/>
	 * <br/>
	 * @return タイトル （検索条件）
	 */
	public void setKeyTitle(String title) {
		this.keyTitle = title;
	}

	/**
	 * 登録日from （検索条件）を取得する。<br/>
	 * <br/>
	 * @param keyInsDateFrom 登録日from （検索条件）
	 */
	public String getKeyInsDateFrom() {
		return keyInsDateFrom;
	}

	/**
	 * 登録日from （検索条件）を設定する。<br/>
	 * <br/>
	 * @return 登録日from （検索条件）
	 */
	public void setKeyInsDateFrom(String insDateFrom) {
		this.keyInsDateFrom = insDateFrom;
	}

	/**
	 * 登録日to （検索条件）を取得する。<br/>
	 * <br/>
	 * @param keyInsDateTo 登録日to （検索条件）
	 */
	public String getKeyInsDateTo() {
		return keyInsDateTo;
	}

	/**
	 * 登録日to （検索条件）を設定する。<br/>
	 * <br/>
	 * @return 登録日to （検索条件）
	 */
	public void setKeyInsDateTo(String insDateTo) {
		this.keyInsDateTo = insDateTo;
	}

	/**
	 * 対象会員（検索条件）を取得する。<br/>
	 * <br/>
	 * @param keyUserId 対象会員（検索条件）
	 */
	public String getKeyUserId() {
		return keyUserId;
	}

	/**
	 * 対象会員（検索条件）を設定する。<br/>
	 * <br/>
	 * @return 対象会員（検索条件）
	 */
	public void setKeyUserId(String userId) {
		this.keyUserId = userId;
	}
	
	/**
	 * 検索条件オブジェクトを作成する。<br/>
	 * PagingListForm に実装されている、ページ処理用の検索条件生成処理を拡張し、受け取ったリクエスト
	 * パラメータによる検索条件を生成する。<br/>
	 * <br/>
	 * このメソッド内では、DB の物理フィールド名を指定しているコードが存在する。<br/>
	 * その為、、管理ユーザー情報のテーブルとして、Information 以外を使用した場合、このメソッドを
	 * オーバーライドする必要がある。<br/>
	 * <br/>
	 * @return 検索条件オブジェクト
	 */
	@Override
	public DAOCriteria buildCriteria(){

		// 検索用オブジェクトの生成
		// ページ処理を行う場合、PagingListForm　インターフェースを実装した Form から
		// DAOCriteria を生成する必要がある。 よって、自身の buildCriteria() を
		// 使用して検索条件を生成している。
		DAOCriteria criteria = super.buildCriteria();


		// お知らせ番号の検索条件生成
		if (!StringValidateUtil.isEmpty(this.keyInformationNo)) {
			criteria.addWhereClause("informationNo", this.keyInformationNo);
		}

		
		// タイトルの検索条件文生成
		if (!StringValidateUtil.isEmpty(this.keyTitle)) {
			criteria.addWhereClause("title", "%" + this.keyTitle + "%",
					DAOCriteria.LIKE_CASE_INSENSITIVE);
		}
		
		// 登録日の検索条件生成
		if (!StringValidateUtil.isEmpty(this.keyInsDateFrom)) {
			criteria.addWhereClause("insDate", this.keyInsDateFrom + " 00:00:00",
					DAOCriteria.GREATER_THAN_EQUALS);
		}
		if (!StringValidateUtil.isEmpty(this.keyInsDateTo)) {
			criteria.addWhereClause("insDate", this.keyInsDateTo + " 23:59:59",
					DAOCriteria.LESS_THAN_EQUALS);
		}

		// 対象会員の検索条件文生成
		if (!StringValidateUtil.isEmpty(this.keyUserId)) {
			criteria.addWhereClause("userId", "%" + this.keyUserId + "%",
					DAOCriteria.LIKE_CASE_INSENSITIVE);
		}
		
        return criteria;

	}
	

	
	/**
	 * お知らせ番号を取得する。<br/>
	 * <br/>
	 * @param informationNo お知らせ番号
	 */
	public String getInformationNo() {
		return informationNo;
	}

	/**
	 * お知らせ番号を設定する。<br/>
	 * <br/>
	 * @return お知らせ番号
	 */
	public void setInformationNo(String informationNo) {
		this.informationNo = informationNo;
	}
}
