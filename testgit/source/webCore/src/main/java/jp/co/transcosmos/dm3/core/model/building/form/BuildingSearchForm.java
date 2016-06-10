package jp.co.transcosmos.dm3.core.model.building.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.form.PagingListForm;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.AlphanumericOnlyValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * <pre>
 * 建物情報の検索パラメータ、および、画面コントロールパラメータ受取り用フォーム
 * 検索条件となるリクエストパラメータの取得や、ＤＢ検索オブジェクトの生成を行う。
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.27	新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class BuildingSearchForm extends PagingListForm<JoinResult> implements Validateable {

	// command リクエストパラメータは、URL マッピングで定義されている、namedCommands の制御にも使用される。
	// 建物情報の検索機能では、以下の値が設定される場合がある。
	//
	// list : 建物情報検索・一覧画面で実際に検索を行う場合に指定する。　（未指定の場合、検索画面を初期表示。）
	// back : 入力確認画面から入力画面へ復帰した場合。　（指定された場合、パラメータの値を初期値として入力画面に表示する。）
	
	/** 検索画面の command パラメータ */
	private String searchCommand;
	/** 建物番号 （検索条件） */
	private String keyBuildingCd;
	/** 表示用建物名 （検索条件） */
	private String keyDisplayBuildingName;
	/** 所在地・都道府県CD */
	private String keyPrefCd;
	/** システム建物CD */
	private String sysBuildingCd;
	
	/** レングスバリデーションで使用する文字列長を取得するユーティリティ */
	protected LengthValidationUtils lengthUtils;


	
	/**
	 * デフォルトコンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	protected BuildingSearchForm(){
		super();
	}

	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
	 */
	protected BuildingSearchForm(LengthValidationUtils lengthUtils){
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
        // 建物番号入力チェック
        validKeyBuildingCd(errors);
        // 表示用建物名入力チェック
        validKeyDisplayBuildingName(errors);
        
        return (startSize == errors.size());
	}

	/**
	 * 建物番号 バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validKeyBuildingCd(List<ValidationFailure> errors) {
        // 建物番号入力チェック
        ValidationChain valid = new ValidationChain("building.search.keyBuildingCd",this.keyBuildingCd);
        // 桁数チェック
        int len = this.lengthUtils.getLength("building.search.keyBuildingCd", 20);
        valid.addValidation(new MaxLengthValidation(len));
        // 半角英数チェック
        valid.addValidation(new AlphanumericOnlyValidation());
        valid.validate(errors);

	}
	
	/**
	 * 表示用建物名 バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validKeyDisplayBuildingName(List<ValidationFailure> errors) {
		// 表示用建物名入力チェック
		ValidationChain valid = new ValidationChain(
				"building.search.keyDisplayBuildingName",
				this.keyDisplayBuildingName);
		// 桁数チェック
		int len = this.lengthUtils.getLength(
				"building.search.keyDisplayBuildingName", 40);
		valid.addValidation(new MaxLengthValidation(len));
		valid.validate(errors);

	}
	
	/**
	 * 検索画面の command パラメータを取得する。<br/>
	 * <br/>
	 * @return 検索画面の command パラメータ
	 */
	public String getSearchCommand() {
		return searchCommand;
	}

	/**
	 * 検索画面の command パラメータを設定する。<br/>
	 * <br/>
	 * @param searchCommand 検索画面の command パラメータ
	 */
	public void setSearchCommand(String searchCommand) {
		this.searchCommand = searchCommand;
	}

	/**
	 * 建物番号 （検索条件）を取得する。<br/>
	 * <br/>
	 * @return 建物番号 （検索条件）
	 */
	public String getKeyBuildingCd() {
		return keyBuildingCd;
	}

	/**
	 * 建物番号 （検索条件）を設定する。<br/>
	 * <br/>
	 * @param keyBuildingCd 建物番号 （検索条件）
	 */
	public void setKeyBuildingCd(String keyBuildingCd) {
		this.keyBuildingCd = keyBuildingCd;
	}

	/**
	 * を取得する。<br/>
	 * <br/>
	 * @return 
	 */
	public String getKeyDisplayBuildingName() {
		return keyDisplayBuildingName;
	}

	/**
	 * 表示用建物名 （検索条件）を設定する。<br/>
	 * <br/>
	 * @param keyDisplayBuildingName 表示用建物名 （検索条件）
	 */
	public void setKeyDisplayBuildingName(String keyDisplayBuildingName) {
		this.keyDisplayBuildingName = keyDisplayBuildingName;
	}

	/**
	 * システム建物CDを取得する。<br/>
	 * <br/>
	 * @return システム建物CD
	 */
	public String getSysBuildingCd() {
		return sysBuildingCd;
	}

	/**
	 * システム建物CDを設定する。<br/>
	 * <br/>
	 * @param sysBuildingCd システム建物CD
	 */
	public void setSysBuildingCd(String sysBuildingCd) {
		this.sysBuildingCd = sysBuildingCd;
	}

	/**
	 * 所在地・都道府県CDを取得する。<br/>
	 * <br/>
	 * @return 所在地・都道府県CD
	 */
	public String getKeyPrefCd() {
		return keyPrefCd;
	}

	/**
	 * 所在地・都道府県CDを設定する。<br/>
	 * <br/>
	 * @param keyPrefCd 所在地・都道府県CD
	 */
	public void setKeyPrefCd(String keyPrefCd) {
		this.keyPrefCd = keyPrefCd;
	}
	
	/**
	 * 検索条件オブジェクトを作成する。<br/>
	 * PagingListForm に実装されている、ページ処理用の検索条件生成処理を拡張し、受け取ったリクエスト
	 * パラメータによる検索条件を生成する。<br/>
	 * <br/>
	 * このメソッド内では、DB の物理フィールド名を指定しているコードが存在する。<br/>
	 * その為、、建物情報のテーブルとして、BuildingInfo 以外を使用した場合、このメソッドを
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


		// 建物番号の検索条件生成
		if (!StringValidateUtil.isEmpty(this.keyBuildingCd)) {
			criteria.addWhereClause("buildingCd", this.keyBuildingCd);
		}

		
		// 表示用建物名の検索条件文生成
		if (!StringValidateUtil.isEmpty(this.keyDisplayBuildingName)) {
			criteria.addWhereClause("displayBuildingName", "%" + this.keyDisplayBuildingName + "%",
					DAOCriteria.LIKE_CASE_INSENSITIVE);
		}

		// 所在地・都道府県の検索条件文生成
		if (!StringValidateUtil.isEmpty(this.keyPrefCd)) {
			criteria.addWhereClause("prefCd", this.keyPrefCd);
		}
		
        return criteria;

	}
	

}
