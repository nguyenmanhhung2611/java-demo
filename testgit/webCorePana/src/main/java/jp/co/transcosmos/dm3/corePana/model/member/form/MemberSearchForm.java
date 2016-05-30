package jp.co.transcosmos.dm3.corePana.model.member.form;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserSearchForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.validation.ZenkakuKanaValidator;
import jp.co.transcosmos.dm3.corePana.validation.DateFromToValidation;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.DateUtils;
import jp.co.transcosmos.dm3.utils.StringUtils;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.AlphanumericOnlyValidation;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;
import jp.co.transcosmos.dm3.validation.ZenkakuOnlyValidation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 会員情報一覧用フォーム.
 * <p>
 *
 * <pre>
 * 担当者        修正日     修正内容
 * ------------  ----------- -----------------------------------------------------
 * tang.tianyun  2015.04.15  新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class MemberSearchForm extends MypageUserSearchForm implements Validateable {

	private static final Log log = LogFactory.getLog(MypageUserSearchForm.class);

	/** 共通コード変換処理 */
    private CodeLookupManager codeLookupManager;

	/**
     * コンストラクター<br/>
     * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
     * <br/>
     */
    MemberSearchForm() {
        super();
    }

	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
	 */
	protected MemberSearchForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager) {
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
	}

	/** ユーザーID （検索条件） */
	private String keyUserNo;
	/** プロモコード （検索条件） */
	private String keyPromo;
	/** 会員住所CD （検索条件） */
	private String keyPrefCd;
	/** 会員住所名 （検索条件） */
	private String keyPrefName;
	/** 登録経路 （検索条件） */
	private String keyEntryRoute;
	/** 流入経路 （検索条件） */
	private String[] keyInflowRoute;

	/**
	 * @return keyUserNo
	 */
	public String getKeyUserNo() {
		return keyUserNo;
	}

	/**
	 * @param keyUserNo セットする keyUserNo
	 */
	public void setKeyUserNo(String keyUserNo) {
		this.keyUserNo = keyUserNo;
	}

	/**
	 * @return keyPromo
	 */
	public String getKeyPromo() {
		return keyPromo;
	}

	/**
	 * @param keyPromo セットする keyPromo
	 */
	public void setKeyPromo(String keyPromo) {
		this.keyPromo = keyPromo;
	}

	/**
	 * @return keyPrefCd
	 */
	public String getKeyPrefCd() {
		return keyPrefCd;
	}

	/**
	 * @param keyPrefCd セットする keyPrefCd
	 */
	public void setKeyPrefCd(String keyPrefCd) {
		this.keyPrefCd = keyPrefCd;
	}

	/**
	 * @return keyPrefName
	 */
	public String getKeyPrefName() {
		return keyPrefName;
	}

	/**
	 * @param keyPrefName セットする keyPrefName
	 */
	public void setKeyPrefName(String keyPrefName) {
		this.keyPrefName = keyPrefName;
	}

	/**
	 * @return keyEntryRoute
	 */
	public String getKeyEntryRoute() {
		return keyEntryRoute;
	}

	/**
	 * @param keyEntryRoute セットする keyEntryRoute
	 */
	public void setKeyEntryRoute(String keyEntryRoute) {
		this.keyEntryRoute = keyEntryRoute;
	}

	/**
	 * @return keyInflowRoute
	 */
	public String[] getKeyInflowRoute() {
		return keyInflowRoute;
	}

	/**
	 * @param keyInflowRoute セットする keyInflowRoute
	 */
	public void setKeyInflowRoute(String[] keyInflowRoute) {
		this.keyInflowRoute = keyInflowRoute;
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

        // 会員番号 （検索条件） 入力チェック
        valiUserId(errors);

        // プロモCD （検索条件） 入力チェック
        valiKeyPromoCd(errors);

        // お名前（姓）（検索条件） 入力チェック
        valiKeyMemberLname(errors);

        // お名前（名） （検索条件） 入力チェック
        valiKeyMemberFname(errors);

        // フリガナ(セイ) （検索条件）入力チェック
        valiKeyMemberLnameKana(errors);

        // フリガナ(メイ) （検索条件） 入力チェック
        valiKeyMemberFnameKana(errors);

        // 登録日・開始 （検索条件） 入力チェック
        valiKeyInsDateFrom(errors);

        // 登録日・終了 （検索条件） 入力チェック
        valiKeyInsDateTo(errors);

        // 登録日・開始 登録日・終了 （検索条件） 日付比較チェック
        valiKeyInsDate(errors);

        // 登録経路入力チェック
        validKeyEntryRoute(errors);

        // 流入経路入力チェック
        validKeyInflowRoute(errors);

        return (startSize == errors.size());
	}

	/**
	 * 会員番号（検索条件） バリデーション<br/>
	 * ・桁数チェック
	 * ・半角英数字チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void valiUserId(List<ValidationFailure> errors) {
		String label = "user.search.userId";
        ValidationChain valUserId = new ValidationChain(label,this.getKeyUserNo());

        // 桁数チェック
        valUserId.addValidation(new MaxLengthValidation(20));
        // 半角英数字チェック
        valUserId.addValidation(new AlphanumericOnlyValidation());
        valUserId.validate(errors);
	}

	/**
	 * プロモCD （検索条件） バリデーション<br/>
	 * ・桁数チェック
	 * ・半角英数字チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void valiKeyPromoCd(List<ValidationFailure> errors) {
		String label = "member.search.KeyPromoCd";
        ValidationChain valMemberLname = new ValidationChain(label,this.getKeyPromo());

        // 桁数チェック
        valMemberLname.addValidation(new MaxLengthValidation(20));
        // 半角英数字チェック
        valMemberLname.addValidation(new AlphanumericOnlyValidation());

        valMemberLname.validate(errors);
	}

	/**
	 * お名前（姓） （検索条件） バリデーション<br/>
	 * ・桁数チェック
	 * ・全角文字チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void valiKeyMemberLname(List<ValidationFailure> errors) {
		String label = "member.search.keyMemberLname";
        ValidationChain valMemberLname = new ValidationChain(label,this.getKeyMemberLname());

        // 桁数チェック
        valMemberLname.addValidation(new MaxLengthValidation(30));
        // 全角文字チェック
        valMemberLname.addValidation(new ZenkakuOnlyValidation());

        valMemberLname.validate(errors);
	}

	/**
	 * お名前（名） （検索条件） バリデーション<br/>
	 * ・桁数チェック
	 * ・全角文字チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void valiKeyMemberFname(List<ValidationFailure> errors) {
		String label = "member.search.keyMemberFname";
        ValidationChain valMemberFname = new ValidationChain(label,this.getKeyMemberFname());

        // 桁数チェック
        valMemberFname.addValidation(new MaxLengthValidation(30));
        // 全角文字チェック
        valMemberFname.addValidation(new ZenkakuOnlyValidation());

        valMemberFname.validate(errors);
	}

	/**
	 * フリガナ(セイ) （検索条件） バリデーション<br/>
	 * ・桁数チェック
	 * ・全角カタカナチェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void valiKeyMemberLnameKana(List<ValidationFailure> errors) {
		String label = "member.search.keyMemberLnameKana";
        ValidationChain valMemberLnameKana = new ValidationChain(label,this.getKeyMemberLnameKana());

        // 桁数チェック
        valMemberLnameKana.addValidation(new MaxLengthValidation(30));
        // 全角カタカナチェック
        valMemberLnameKana.addValidation(new ZenkakuKanaValidator());

        valMemberLnameKana.validate(errors);
	}

	/**
	 * フリガナ(メイ) （検索条件） バリデーション<br/>
	 * ・桁数チェック
	 * ・全角カタカナチェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void valiKeyMemberFnameKana(List<ValidationFailure> errors) {
		String label = "member.search.keyMemberFnameKana";
        ValidationChain valMemberFnameKana = new ValidationChain(label,this.getKeyMemberFnameKana());

        // 桁数チェック
        valMemberFnameKana.addValidation(new MaxLengthValidation(30));
        // 全角カタカナチェック
        valMemberFnameKana.addValidation(new ZenkakuKanaValidator());

        valMemberFnameKana.validate(errors);
	}

	/**
	 * 登録日・開始 登録日・終了 （検索条件） バリデーション<br/>
	 * ・日付比較チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void valiKeyInsDate(List<ValidationFailure> errors) {
		String label = "member.search.keyInsDateFrom";
        ValidationChain valMemberFnameKana = new ValidationChain(label,this.getKeyInsDateFrom());

        // 日付比較チェック
        valMemberFnameKana.addValidation(new DateFromToValidation("yyyy/MM/dd", "登録日・終了", this.getKeyInsDateTo()));

        valMemberFnameKana.validate(errors);
	}

	/**
     * 登録経路 バリデーション<br/>
     * ・パターンチェック
     * <br/>
     * @param errors エラー情報を格納するリストオブジェクト
     */
    protected void validKeyEntryRoute(List<ValidationFailure> errors) {
    	String label = "member.search.keyEntryRoute";
        ValidationChain valid = new ValidationChain(label, this.getKeyEntryRoute());
        // パターンチェック
        valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "entryRoute"));
        valid.validate(errors);
    }

    /**
     * 流入経路 バリデーション<br/>
     * ・パターンチェック
     * <br/>
     * @param errors エラー情報を格納するリストオブジェクト
     */
    protected void validKeyInflowRoute(List<ValidationFailure> errors) {
    	String label = "member.search.keyInflowRoute";
    	if (this.getKeyInflowRoute() != null) {
    		for (int i = 0; i < this.getKeyInflowRoute().length; i++) {
        		ValidationChain valid = new ValidationChain(label, this.keyInflowRoute[i]);
                // パターンチェック
                valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "refCd"));
                valid.validate(errors);
        	}
    	}
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

		// 会員番号の検索条件文生成
		if (!StringValidateUtil.isEmpty(this.getKeyUserNo())) {
			criteria.addWhereClause("userId", this.getKeyUserNo(), DAOCriteria.EQUALS);
		}

		// メールアドレスの検索条件文生成
		if (!StringValidateUtil.isEmpty(this.getKeyEmail())) {
			criteria.addWhereClause("email", "%" + this.getKeyEmail() + "%", DAOCriteria.LIKE);
		}

		// プロモCDの検索条件文生成
		if (!StringValidateUtil.isEmpty(this.getKeyPromo())) {
			criteria.addWhereClause("promoCd", this.getKeyPromo(), DAOCriteria.EQUALS);
		}

		// 会員住所の検索条件文生成
		if (!StringValidateUtil.isEmpty(this.getKeyPrefCd())) {
			criteria.addWhereClause("prefCd", this.getKeyPrefCd(), DAOCriteria.EQUALS);
		}

		// お名前（姓）の検索条件文生成
		if (!StringValidateUtil.isEmpty(this.getKeyMemberLname())) {
			criteria.addWhereClause("memberLname", "%" + this.getKeyMemberLname() + "%", DAOCriteria.LIKE);
		}

		// お名前（名）の検索条件文生成
		if (!StringValidateUtil.isEmpty(this.getKeyMemberFname())) {
			criteria.addWhereClause("memberFname", "%" + this.getKeyMemberFname() + "%", DAOCriteria.LIKE);
		}

		// フリガナ（セイ）の検索条件文生成
		if (!StringValidateUtil.isEmpty(this.getKeyMemberLnameKana())) {
			criteria.addWhereClause("memberLnameKana", "%" + this.getKeyMemberLnameKana() + "%", DAOCriteria.LIKE);
		}

		// フリガナ（メイ）の検索条件文生成
		if (!StringValidateUtil.isEmpty(this.getKeyMemberFnameKana())) {
			criteria.addWhereClause("memberFnameKana", "%" + this.getKeyMemberFnameKana() + "%", DAOCriteria.LIKE);
		}

		// 登録経路の検索条件文生成
		if (!StringValidateUtil.isEmpty(this.getKeyEntryRoute()) && !"000".equals(this.getKeyEntryRoute())) {
			criteria.addWhereClause("entryRoute", this.getKeyEntryRoute(), DAOCriteria.EQUALS);
		}

		// 流入経路の検索条件文生成
		if (this.keyInflowRoute != null && this.keyInflowRoute.length > 0) {
			criteria.addInSubQuery("refCd", this.keyInflowRoute);
		}

		// 登録日（開始日）の検索条件文生成
		if (!StringValidateUtil.isEmpty(this.getKeyInsDateFrom())) {

			try {
				criteria.addWhereClause("insDate", StringUtils.stringToDate(this.getKeyInsDateFrom(), "yyyy/MM/dd"), DAOCriteria.GREATER_THAN_EQUALS);

			} catch (ParseException e) {
				// バリデーションを実施しているので、このケースになる事は通常ありえない。
				// もし例外が発生した場合は警告をログ出力して検索条件としては無視する。
				log.warn("keyInsDateFrom is invalid date string (" + this.getKeyInsDateFrom() + ")");
				e.printStackTrace();
			}
		}

		// 登録日（終了日）の検索条件文生成
		if (!StringValidateUtil.isEmpty(this.getKeyInsDateTo())) {

			try {
				// タイムスタンプ情報には時間情報があるので、終了日は翌日未満で検索する。
				Date toDate = DateUtils.addDays(this.getKeyInsDateTo(), "yyyy/MM/dd", 1);
				criteria.addWhereClause("insDate", toDate, DAOCriteria.LESS_THAN);

			} catch (ParseException e) {
				// バリデーションを実施しているので、このケースになる事は通常ありえない。
				// もし例外が発生した場合は警告をログ出力して検索条件としては無視する。
				log.warn("keyInsDateTo is invalid date string (" + this.getKeyInsDateTo() + ")");
				e.printStackTrace();
			}

		}

		criteria.addOrderByClause("userId", false);

		return criteria;
	}
}
