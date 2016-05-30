package jp.co.transcosmos.dm3.core.model.news.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.News;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.form.PagingListForm;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.AlphanumericOnlyValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
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
public class NewsSearchForm extends PagingListForm<News> implements Validateable {
	// command リクエストパラメータは、URL マッピングで定義されている、namedCommands の制御にも使用される。
	// お知らせ情報の検索機能では、以下の値が設定される場合がある。
	//
	// list : お知らせ検索・一覧画面で実際に検索を行う場合に指定する。　（未指定の場合、検索画面を初期表示。）
	// back : 入力確認画面から入力画面へ復帰した場合。　（指定された場合、パラメータの値を初期値として入力画面に表示する。）

	private String keyNewsId;
	private String keyNewsTitle;
	private String keyNewsContent;

	public String getKeyNewsId() {
		return keyNewsId;
	}

	public void setKeyNewsId(String keyNewsId) {
		this.keyNewsId = keyNewsId;
	}

	public String getKeyNewsTitle() {
		return keyNewsTitle;
	}

	public void setKeyNewsTitle(String keyNewsTitle) {
		this.keyNewsTitle = keyNewsTitle;
	}

	public String getKeyNewsContent() {
		return keyNewsContent;
	}

	public void setKeyNewsContent(String keyNewsContent) {
		this.keyNewsContent = keyNewsContent;
	}

	private String newsId;

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	/** 検索画面の command パラメータ */
	private String searchCommand;
	/** お知らせ番号 （検索条件） */

	/** レングスバリデーションで使用する文字列長を取得するユーティリティ */
	protected LengthValidationUtils lengthUtils;

	/**
	 * デフォルトコンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	protected NewsSearchForm() {
		super();
	}

	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 * 
	 * @param lengthUtils
	 *            レングスバリデーションで使用する文字列長を取得するユーティリティ
	 */
	protected NewsSearchForm(LengthValidationUtils lengthUtils) {
		super();
		this.lengthUtils = lengthUtils;
	}

	/**
	 * バリデーション処理<br/>
	 * リクエストパラメータのバリデーションを行う<br/>
	 * <br/>
	 * 
	 * @param errors
	 *            エラー情報を格納するリストオブジェクト
	 * @return 正常時 true、エラー時 false
	 */
	@Override
	public boolean validate(List<ValidationFailure> errors) {
		int startSize = errors.size();

		validKeyNewsId(errors);
		validKeyNewsTitle(errors);
		validKeyNewsContent(errors);

		return (startSize == errors.size());
	}

	protected void validKeyNewsId(List<ValidationFailure> errors) {
		// お知らせ番号入力チェック
		ValidationChain validKeyNewsId = new ValidationChain("information.search.keyInformationNo", this.keyNewsId);
		// 英数字チェック
		validKeyNewsId.addValidation(new AlphanumericOnlyValidation());
		// 桁数チェック
		validKeyNewsId.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(
				"information.search.keyInformationNo", 20)));
		validKeyNewsId.validate(errors);
	}

	protected void validKeyNewsTitle(List<ValidationFailure> errors) {
		// タイトル入力チェック
		ValidationChain valTitle = new ValidationChain("information.search.keyTitle", this.keyNewsTitle);
		// 桁数チェック
		valTitle.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("information.search.keyTitle", 50)));
		valTitle.validate(errors);
	}

	protected void validKeyNewsContent(List<ValidationFailure> errors) {
		// タイトル入力チェック
		ValidationChain valContent = new ValidationChain("information.search.keyTitle", this.keyNewsContent);
		// 桁数チェック
		valContent.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("information.search.keyTitle", 200)));
		valContent.validate(errors);
	}

	/**
	 * 検索画面の commandを取得する。<br/>
	 * <br/>
	 * 
	 * @param searchCommand
	 *            検索画面の command
	 */
	public String getSearchCommand() {
		return searchCommand;
	}

	/**
	 * 検索画面の commandを設定する。<br/>
	 * <br/>
	 * 
	 * @return 検索画面の command
	 */
	public void setSearchCommand(String searchCommand) {
		this.searchCommand = searchCommand;
	}

	/**
	 * 検索条件オブジェクトを作成する。<br/>
	 * PagingListForm に実装されている、ページ処理用の検索条件生成処理を拡張し、受け取ったリクエスト パラメータによる検索条件を生成する。<br/>
	 * <br/>
	 * このメソッド内では、DB の物理フィールド名を指定しているコードが存在する。<br/>
	 * その為、、管理ユーザー情報のテーブルとして、Information 以外を使用した場合、このメソッドを オーバーライドする必要がある。<br/>
	 * <br/>
	 * 
	 * @return 検索条件オブジェクト
	 */
	@Override
	public DAOCriteria buildCriteria() {
		DAOCriteria criteria = super.buildCriteria();

		// お知らせ番号の検索条件生成
		if (!StringValidateUtil.isEmpty(this.keyNewsId)) {
			criteria.addWhereClause("newsId", "%" + this.keyNewsId + "%", DAOCriteria.LIKE_CASE_INSENSITIVE);
		}

		// タイトルの検索条件文生成
		if (!StringValidateUtil.isEmpty(this.keyNewsTitle)) {
			criteria.addWhereClause("newsTitle", "%" + this.keyNewsTitle + "%", DAOCriteria.LIKE_CASE_INSENSITIVE);
		}

		if (!StringValidateUtil.isEmpty(this.keyNewsContent)) {
			criteria.addWhereClause("newsContent", "%" + this.keyNewsContent + "%", DAOCriteria.LIKE_CASE_INSENSITIVE);
		}

		return criteria;

	}

}
