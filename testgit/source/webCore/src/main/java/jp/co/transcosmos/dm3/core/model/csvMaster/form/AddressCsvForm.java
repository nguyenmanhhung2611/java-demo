package jp.co.transcosmos.dm3.core.model.csvMaster.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.validation.LengthValidator;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.NumberValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * 日本郵便が提供する郵便番号CSV によるマスターデータ更新時に加工済データを管理するフォーム.
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.19	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class AddressCsvForm implements Validateable {

	/** 都道府県CD */
	private String prefCd;
	
	/** 市区町村CD */
	private String addressCd;

	/** 市区町村名 */
	private String addressName;

	/** 郵便番号 */
	private String zip;

	/** エリア検索非表示フラグ （0:通常、1:エリア検索で非表示） */
	private String areaNotDsp = "0";



	/**
	 * 更新の表示<br/>
	 * 日本郵便提供の CSV ファイルでは、14 項目は「更新の表示」を意味する。<br/>
	 * この値が「2」の場合、「廃止データ」を意味するが、全件データの場合「廃止データ」は存在しないと思える
	 * が、一応チェック対象としておく。<br/>
	 */
	private String updFlg;

	/** レングスバリデーションで使用する文字列長を取得するユーティリティ */
	protected LengthValidationUtils lengthUtils;



	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	protected AddressCsvForm() {
		super();
	}

	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
	 */
	protected AddressCsvForm(LengthValidationUtils lengthUtils){
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

		// 都道府県CD　半角数字２桁固定、必須
		validPrefCd(errors);
		// 市区町村CD　半角数字５桁固定、必須
		validAddressCd(errors);
		// 市区町村名　必須
		validAddressName(errors);
		// 郵便番号　半角数字7桁固定、必須
		validZip(errors);

		return startSize == errors.size();
	}

	/**
	 * 都道府県CD バリデーション<br/>
	 * ・必須チェック
	 * ・２桁固定チェック
	 * ・半角数字チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validPrefCd(List<ValidationFailure> errors) {
		// 都道府県CD　半角数字２桁固定、必須
		ValidationChain valid = new ValidationChain("addressCsv.prefCd", this.prefCd);
		valid.addValidation(new NullOrEmptyCheckValidation());
		valid.addValidation(new LengthValidator(this.lengthUtils.getLength("addressCsv.prefCd", 2)));
		valid.addValidation(new NumberValidation());
		valid.validate(errors);
	}

	/**
	 * 市区町村CD バリデーション<br/>
	 * ・必須チェック
	 * ・５桁固定チェック
	 * ・半角数字チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validAddressCd(List<ValidationFailure> errors) {
		// 市区町村CD　半角数字５桁固定、必須
		ValidationChain valid = new ValidationChain("addressCsv.addressCd", this.addressCd);
		valid.addValidation(new NullOrEmptyCheckValidation());
		valid.addValidation(new LengthValidator(this.lengthUtils.getLength("addressCsv.addressCd", 5)));
		valid.addValidation(new NumberValidation());
		valid.validate(errors);
	}

	/**
	 * 市区町村名 バリデーション<br/>
	 * ・必須チェック
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validAddressName(List<ValidationFailure> errors) {
		// 市区町村名　必須、桁数
		ValidationChain valid = new ValidationChain("addressCsv.addressName", this.addressName);
		valid.addValidation(new NullOrEmptyCheckValidation());
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("addressCsv.addressName", 60)));
		valid.validate(errors);
	}

	/**
	 * 郵便番号 バリデーション<br/>
	 * ・必須チェック
	 * ・7桁固定チェック
	 * ・半角数字チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validZip(List<ValidationFailure> errors) {
		// 郵便番号　半角数字7桁固定、必須
		ValidationChain valid = new ValidationChain("addressCsv.zip", this.zip);
		valid.addValidation(new NullOrEmptyCheckValidation());
		valid.addValidation(new LengthValidator(this.lengthUtils.getLength("addressCsv.zip", 7)));
		valid.addValidation(new NumberValidation());
		valid.validate(errors);
	}

	/**
	 * 都道府県CD を取得する。<br/>
	 * <br/>
	 * @return 都道府県CD
	 */
	public String getPrefCd() {
		return prefCd;
	}

	/**
	 * 都道府県CD を設定する。<br/>
	 * <br/>
	 * @param prefCd　都道府県CD
	 */
	public void setPrefCd(String prefCd) {
		this.prefCd = prefCd;
	}

	/**
	 * 市区町村CD を取得する。<br/>
	 * <br/>
	 * @return 市区町村CD
	 */
	public String getAddressCd() {
		return addressCd;
	}

	/**
	 * 市区町村CD を設定する。<br/>
	 * <br/>
	 * @param addressCd　市区町村CD
	 */
	public void setAddressCd(String addressCd) {
		this.addressCd = addressCd;
	}

	/**
	 * 市区町村名を取得する。<br/>
	 * <br/>
	 * @return 市区町村名
	 */
	public String getAddressName() {
		return addressName;
	}

	/**
	 * 市区町村名を設定する。<br/>
	 * <br/>
	 * @param addressName 市区町村名
	 */
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	/**
	 * 郵便番号を取得する。<br/>
	 * <br/>
	 * @return 郵便番号
	 */
	public String getZip() {
		return zip;
	}
	
	/**
	 * 郵便番号を設定する。<br/>
	 * <br/>
	 * @param zip　郵便番号
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * 「更新の表示」を取得する。<br/>
	 * <br/>
	 * @return 「更新の表示」
	 */
	public String getUpdFlg() {
		return updFlg;
	}

	/**
	 * 「更新の表示」を設定する。<br/>
	 * <br/>
	 * @param updFlg 「更新の表示」
	 */
	public void setUpdFlg(String updFlg) {
		this.updFlg = updFlg;
	}

	/**
	 * エリア検索非表示フラグを取得する。<br/>
	 * <br/>
	 * @return エリア検索非表示フラグ　（0:通常、1:エリア情報で非表示）
	 */
	public String getAreaNotDsp() {
		return areaNotDsp;
	}

	/**
	 * エリア検索非表示フラグを設定する。<br/>
	 * <br/>
	 * @param エリア検索非表示フラグ　（0:通常、1:エリア情報で非表示）
	 */
	public void setAreaNotDsp(String areaNotDsp) {
		this.areaNotDsp = areaNotDsp;
	}
	
	
}
