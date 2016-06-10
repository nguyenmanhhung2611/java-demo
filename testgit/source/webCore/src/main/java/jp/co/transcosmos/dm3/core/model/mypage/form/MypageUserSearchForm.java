package jp.co.transcosmos.dm3.core.model.mypage.form;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.form.PagingListForm;
import jp.co.transcosmos.dm3.utils.DateUtils;
import jp.co.transcosmos.dm3.utils.StringUtils;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.ValidDateValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;


/**
 * マイページ会員の検索条件パラメータ受取り用フォーム.
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
 * <br/>
 * また、この Form はフロント側から使用しない事。<br/>
 * （UserId をリクエストパラメータで受け取ってしまうリスクがある為。）<br/>
 * 
 */
public class MypageUserSearchForm  extends PagingListForm<JoinResult> implements Validateable {

	private static final Log log = LogFactory.getLog(MypageUserSearchForm.class);
	


	// command リクエストパラメータは、URL マッピングで定義されている、namedCommands の制御にも使用される。
	// 管理ユーザーのメンテナンス機能では、以下の値が設定される場合がある。
	//
	// list : 管理ユーザー検索画面で実際に検索を行う場合に指定する。　（未指定の場合、検索画面を初期表示。）
	// back : 入力確認画面から入力画面へ復帰した場合。　（指定された場合、パラメータの値を初期値として入力画面に表示する。）

	/** 検索画面の command パラメータ */
	private String searchCommand;

	// note
	// フロント側からの使用を考慮した場合、userId をリクエストパラメータで受取る訳には行かないので input 用
	// form には userId を配置しない。
	// 検索用フォームはフロント側から使用する事は無いので、検索用フォームで処理対象ユーザーID を受け取る。

	/** 処理対象となるユーザーID */
	private String userId;

	/** メールアドレス （検索条件） */
	private String keyEmail;
	/** 氏名・姓 （検索条件） */
	private String keyMemberLname;
	/** 氏名・名（検索条件） */
	private String keyMemberFname;
	/** 氏名・カナ・セイ （検索条件） */
	private String keyMemberLnameKana;
	/** 氏名・カナ・メイ （検索条件） */
	private String keyMemberFnameKana;
	/** 登録日・開始 （検索条件） */
	private String keyInsDateFrom;
	/** 登録日・終了 （検索条件） */
	private String keyInsDateTo;

	/** レングスバリデーションで使用する文字列長を取得するユーティリティ */
	protected LengthValidationUtils lengthUtils;


	
	/**
	 * デフォルトコンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	protected MypageUserSearchForm(){
		super();
	}

	

	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
	 */
	protected MypageUserSearchForm(LengthValidationUtils lengthUtils){
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

        // メールアドレス （検索条件） 入力チェック
        valiKeyEmail(errors);

        // 氏名・姓 （検索条件） 入力チェック
        valiKeyMemberLname(errors);

        // 氏名・名 （検索条件） 入力チェック
        valiKeyMemberFname(errors);

        // 氏名・カナ・セイ （検索条件）入力チェック
        valiKeyMemberLnameKana(errors);

        // 氏名・カナ・メイ （検索条件） 入力チェック
        valiKeyMemberFnameKana(errors);

        // 登録日・開始 （検索条件） 入力チェック
        valiKeyInsDateFrom(errors);

        // 登録日・終了 （検索条件） 入力チェック
        valiKeyInsDateTo(errors);

        return (startSize == errors.size());
	}

	/**
	 * メールアドレス（検索条件） バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void valiKeyEmail(List<ValidationFailure> errors) {
        // メールアドレス （検索条件） 入力チェック
        ValidationChain valMail = new ValidationChain("mypage.search.keyEmail",this.keyEmail);
        // 桁数チェック
        valMail.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("mypage.search.keyEmail", 255)));
        valMail.validate(errors);
	}

	/**
	 * 氏名・名 （検索条件） バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void valiKeyMemberLname(List<ValidationFailure> errors) {
		String label = "mypage.search.keyMemberLname";
        ValidationChain valMemberLname = new ValidationChain(label,this.keyMemberLname);

        // 桁数チェック
        valMemberLname.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 40)));

        valMemberLname.validate(errors);
	}

	/**
	 * 氏名・名 （検索条件） バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void valiKeyMemberFname(List<ValidationFailure> errors) {
		String label = "mypage.search.keyMemberFname";
        ValidationChain valMemberFname = new ValidationChain(label,this.keyMemberFname);

        // 桁数チェック
        valMemberFname.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 40)));

        valMemberFname.validate(errors);
	}

	/**
	 * 氏名・カナ・セイ （検索条件） バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void valiKeyMemberLnameKana(List<ValidationFailure> errors) {
		String label = "mypage.search.keyMemberLnameKana";
        ValidationChain valMemberLnameKana = new ValidationChain(label,this.keyMemberLnameKana);

        // 桁数チェック
        valMemberLnameKana.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 40)));

        valMemberLnameKana.validate(errors);
	}

	/**
	 * 氏名・カナ・メイ （検索条件） バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void valiKeyMemberFnameKana(List<ValidationFailure> errors) {
		String label = "mypage.search.keyMemberFnameKana";
        ValidationChain valMemberFnameKana = new ValidationChain(label,this.keyMemberFnameKana);

        // 桁数チェック
        valMemberFnameKana.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 40)));
        
        valMemberFnameKana.validate(errors);
	}

	/**
	 * 登録日・開始 （検索条件） バリデーション<br/>
	 * ・日付書式チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void valiKeyInsDateFrom(List<ValidationFailure> errors) {
		String label = "mypage.search.keyInsDateFrom";
        ValidationChain valInsDateFrom = new ValidationChain(label,this.keyInsDateFrom);

        // 日付書式チェック
        valInsDateFrom.addValidation(new ValidDateValidation("yyyy/MM/dd"));

        valInsDateFrom.validate(errors);
	}

	/**
	 * 登録日・終了 （検索条件） バリデーション<br/>
	 * ・日付書式チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void valiKeyInsDateTo(List<ValidationFailure> errors) {
		String label = "mypage.search.keyInsDateTo";
        ValidationChain valInsDateTo = new ValidationChain(label,this.keyInsDateTo);

        // 日付書式チェック
        valInsDateTo.addValidation(new ValidDateValidation("yyyy/MM/dd"));

        valInsDateTo.validate(errors);
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
	 * その為、、マイページ会員情報のテーブルとして、MemberInfo 以外を使用した場合、このメソッドを
	 * オーバーライドする必要がある。<br/>
	 * <br/>
	 * @return 検索条件オブジェクト
	 */
	protected DAOCriteria buildCriteria(DAOCriteria criteria){

		// メールアドレスの検索条件文生成
		if (!StringValidateUtil.isEmpty(this.keyEmail)) {
			criteria.addWhereClause("email", "%" + this.keyEmail + "%", DAOCriteria.LIKE);
		}
		
		// お名前（姓）の検索条件文生成
		if (!StringValidateUtil.isEmpty(this.keyMemberLname)) {
			criteria.addWhereClause("memberLname", "%" + this.keyMemberLname + "%", DAOCriteria.LIKE);
		}

		// お名前（名）の検索条件文生成
		if (!StringValidateUtil.isEmpty(this.keyMemberFname)) {
			criteria.addWhereClause("memberFname", "%" + this.keyMemberFname + "%", DAOCriteria.LIKE);
		}

		// フリガナ（セイ）の検索条件文生成
		if (!StringValidateUtil.isEmpty(this.keyMemberLnameKana)) {
			criteria.addWhereClause("memberLnameKana", "%" + this.keyMemberLnameKana + "%", DAOCriteria.LIKE);
		}

		// フリガナ（メイ）の検索条件文生成
		if (!StringValidateUtil.isEmpty(this.keyMemberFnameKana)) {
			criteria.addWhereClause("memberFnameKana", "%" + this.keyMemberFnameKana + "%", DAOCriteria.LIKE);
		}

		// 登録日（開始日）の検索条件文生成
		if (!StringValidateUtil.isEmpty(this.keyInsDateFrom)) {

			try {
				criteria.addWhereClause("insDate", StringUtils.stringToDate(this.keyInsDateFrom, "yyyy/MM/dd"), DAOCriteria.GREATER_THAN_EQUALS);

			} catch (ParseException e) {
				// バリデーションを実施しているので、このケースになる事は通常ありえない。
				// もし例外が発生した場合は警告をログ出力して検索条件としては無視する。
				log.warn("keyInsDateFrom is invalid date string (" + this.keyInsDateFrom + ")", e);
			}
		}

		// 登録日（終了日）の検索条件文生成
		if (!StringValidateUtil.isEmpty(this.keyInsDateTo)) {

			try {
				// タイムスタンプ情報には時間情報があるので、終了日は翌日未満で検索する。
				Date toDate = DateUtils.addDays(this.keyInsDateTo, "yyyy/MM/dd", 1);
				criteria.addWhereClause("insDate", toDate, DAOCriteria.LESS_THAN);

			} catch (ParseException e) {
				// バリデーションを実施しているので、このケースになる事は通常ありえない。
				// もし例外が発生した場合は警告をログ出力して検索条件としては無視する。
				log.warn("keyInsDateTo is invalid date string (" + this.keyInsDateTo + ")", e);
			}
			
		}

		return criteria;
	}
	
	/**
	 * マイページ会員情報の主キー値となる検索条件を生成する。<br/>
	 * <br/>
	 * このメソッド内では、DB の物理フィールド名を指定しているコードが存在する。<br/>
	 * その為、、マイページ会員情報のテーブルとして、MemberInfo 以外を使用した場合、このメソッドを
	 * オーバーライドする必要がある。<br/>
	 * <br/>
	 * @return 主キーとなる検索条件オブジェクト
	 */
	public DAOCriteria buildPkCriteria(){
		// 検索条件オブジェクトを取得
		DAOCriteria criteria = new DAOCriteria();

		// をしているテーブルの主キーの検索条件を生成する。 
		criteria.addWhereClause("userId", this.userId);

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
	 * 処理対象となるユーザーID を取得する。<br/>
	 * <br/>
	 * @return ユーザーID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 処理対象となるユーザーID を設定する。<br/>
	 * <br/>
	 * @param userId ユーザーID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * メールアドレス （検索条件）を取得する。<br/>
	 * <br/>
	 * @return メールアドレス （検索条件）
	 */
	public String getKeyEmail() {
		return keyEmail;
	}

	/**
	 * メールアドレス （検索条件）を設定する。<br/>
	 * <br/>
	 * @param keyEmail　メールアドレス （検索条件）
	 */
	public void setKeyEmail(String keyEmail) {
		this.keyEmail = keyEmail;
	}

	/**
	 * 氏名・姓 （検索条件）を取得する。<br/>
	 * <br/>
	 * @return 氏名・姓 （検索条件）
	 */
	public String getKeyMemberLname() {
		return keyMemberLname;
	}

	/**
	 * 氏名・姓 （検索条件）を設定する。<br/>
	 * <br/>
	 * @param keyMemberLname　氏名・姓 （検索条件）
	 */
	public void setKeyMemberLname(String keyMemberLname) {
		this.keyMemberLname = keyMemberLname;
	}
	
	/**
	 * 氏名・名（検索条件）を取得する。<br/>
	 * <br/>
	 * @return 氏名・名（検索条件）
	 */
	public String getKeyMemberFname() {
		return keyMemberFname;
	}

	/**
	 * 氏名・名（検索条件）を設定する。<br/>
	 * <br/>
	 * @param keyMemberFname 氏名・名（検索条件）
	 */
	public void setKeyMemberFname(String keyMemberFname) {
		this.keyMemberFname = keyMemberFname;
	}
	
	/**
	 * 氏名・カナ・セイ （検索条件）を取得する。<br/>
	 * <br/>
	 * @return 氏名・カナ・セイ （検索条件）
	 */
	public String getKeyMemberLnameKana() {
		return keyMemberLnameKana;
	}

	/**
	 * 氏名・カナ・セイ （検索条件）を設定する。<br/>
	 * <br/>
	 * @param keyMemberLnameKana 氏名・カナ・セイ （検索条件）
	 */
	public void setKeyMemberLnameKana(String keyMemberLnameKana) {
		this.keyMemberLnameKana = keyMemberLnameKana;
	}
	
	/**
	 * 氏名・カナ・メイ （検索条件）を取得する。<br/>
	 * <br/>
	 * @return 氏名・カナ・メイ （検索条件）
	 */
	public String getKeyMemberFnameKana() {
		return keyMemberFnameKana;
	}

	/**
	 * 氏名・カナ・メイ （検索条件）を設定する。<br/>
	 * <br/>
	 * @param keyMemberFnameKana 氏名・カナ・メイ （検索条件）
	 */
	public void setKeyMemberFnameKana(String keyMemberFnameKana) {
		this.keyMemberFnameKana = keyMemberFnameKana;
	}
	
	/**
	 * 登録日・開始 （検索条件）を取得する。<br/>
	 * <br/>
	 * @return 登録日・開始 （検索条件）
	 */
	public String getKeyInsDateFrom() {
		return keyInsDateFrom;
	}

	/**
	 * 登録日・開始 （検索条件）を設定する。<br/>
	 * <br/>
	 * @param keyInsDateFrom　登録日・開始 （検索条件）
	 */
	public void setKeyInsDateFrom(String keyInsDateFrom) {
		this.keyInsDateFrom = keyInsDateFrom;
	}

	/**
	 * 登録日・終了 （検索条件） を取得する。<br/>
	 * <br/>
	 * @return 登録日・終了 （検索条件）
	 */
	public String getKeyInsDateTo() {
		return keyInsDateTo;
	}

	/**
	 * 登録日・終了 （検索条件） を設定する。<br/>
	 * <br/>
	 * @param keyInsDateTo　登録日・終了 （検索条件）
	 */
	public void setKeyInsDateTo(String keyInsDateTo) {
		this.keyInsDateTo = keyInsDateTo;
	}

}
