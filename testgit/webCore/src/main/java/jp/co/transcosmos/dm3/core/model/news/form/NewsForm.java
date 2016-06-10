package jp.co.transcosmos.dm3.core.model.news.form;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.News;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <pre>
 * お知らせメンテナンスの入力パラメータ受取り用フォーム
 * 
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * @author hiennt
 */
public class NewsForm implements Validateable {

	private static final Log log = LogFactory.getLog(NewsForm.class);

	/** command パラメータ */
	private String command;
	private String newsId;
	private String newsTitle;
	private String newsContent;
	private String insDate;
	private String insUserId;
	private String updDate;
	private String updUserId;
	private boolean delFlg;

	private String userId;

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public String getNewsContent() {
		return newsContent;
	}

	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}

	public String getInsDate() {
		return insDate;
	}

	public void setInsDate(String insDate) {
		this.insDate = insDate;
	}

	public String getInsUserId() {
		return insUserId;
	}

	public void setInsUserId(String insUserId) {
		this.insUserId = insUserId;
	}

	public String getUpdDate() {
		return updDate;
	}

	public void setUpdDate(String updDate) {
		this.updDate = updDate;
	}

	public String getUpdUserId() {
		return updUserId;
	}

	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}

	public boolean isDelFlg() {
		return delFlg;
	}

	public void setDelFlg(boolean delFlg) {
		this.delFlg = delFlg;
	}

	/** レングスバリデーションで使用する文字列長を取得するユーティリティ */
	protected LengthValidationUtils lengthUtils;

	/** 共通コード変換処理 */
	private CodeLookupManager codeLookupManager;

	/**
	 * デフォルトコンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	protected NewsForm() {
		super();
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
	protected NewsForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager) {
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * バリデーション処理<br/>
	 * リクエストパラメータのバリデーションを行う<br/>
	 * <br/>
	 * この Form クラスのバリデーションメソッドは、Validateable インターフェースの実装では無いので、 バリデーション実行時の引数が異なるので、複数 Form をまとめてバリデーションする場合などは注意 する事。<br/>
	 * <br/>
	 * 
	 * @param errors
	 *            エラー情報を格納するリストオブジェクト
	 * @param mode
	 *            処理モード ("insert" or "update")
	 * @return 正常時 true、エラー時 false
	 */
	public boolean validate(List<ValidationFailure> errors) {

		int startSize = errors.size();

		validKeyNewsTitle(errors);
		validKeyNewsContent(errors);

		return (startSize == errors.size());
	}

	protected void validKeyNewsTitle(List<ValidationFailure> errors) {
		// タイトル入力チェック
		ValidationChain valTitle = new ValidationChain("information.search.keyTitle", this.newsTitle);
		// 桁数チェック
		valTitle.addValidation(new NullOrEmptyCheckValidation());
		valTitle.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("information.search.keyTitle", 50)));
		valTitle.validate(errors);
	}

	protected void validKeyNewsContent(List<ValidationFailure> errors) {
		// タイトル入力チェック
		ValidationChain valContent = new ValidationChain("information.search.keyTitle", this.newsContent);
		// 桁数チェック
		valContent.addValidation(new NullOrEmptyCheckValidation());
		valContent.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("information.search.keyTitle", 200)));
		valContent.validate(errors);
	}

	/**
	 * command パラメータを設定する。<br/>
	 * <br/>
	 * 
	 * @param command
	 *            command パラメータ
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * command パラメータを取得する。<br/>
	 * <br/>
	 * 
	 * @return command パラメータ
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * 対象会員を設定する。<br/>
	 * <br/>
	 * 
	 * @param userId
	 *            対象会員
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 対象会員を取得する。<br/>
	 * <br/>
	 * 
	 * @return 対象会員
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setDefaultData(News news) {

		this.newsId = (String) news.getNewsId();
		this.newsTitle = news.getNewsTitle();
		this.newsContent = news.getNewsContent();

		SimpleDateFormat formatSEDate = new SimpleDateFormat("yyyy/MM/dd");
		if (news.getInsDate() != null) {
			this.insDate = formatSEDate.format(news.getInsDate());
		}

		if (news.getUpdDate() != null) {
			this.updDate = formatSEDate.format(news.getUpdDate());
		}
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
	public void copyToNews(News news, String editUserId) {

		if (!StringValidateUtil.isEmpty(this.newsId)) {
			news.setNewsId(this.newsId);
		}
		
		news.setNewsTitle(this.newsTitle);
		news.setNewsContent(this.newsContent);

		Date date = new Date();
		news.setUpdDate(date);

		news.setUpdUserId(editUserId);
		news.setDelFlg(true);
	}

	/**
	 * お知らせ情報の主キー値となる検索条件を生成する。<br/>
	 * <br/>
	 * このメソッド内では、DB の物理フィールド名を指定しているコードが存在する。<br/>
	 * その為、、管理ユーザー情報のテーブルとして、Information 以外を使用した場合、このメソッドを オーバーライドする必要がある。<br/>
	 * <br/>
	 * 
	 * @return 主キーとなる検索条件オブジェクト
	 */
	public DAOCriteria buildPkCriteria() {
		// 検索条件オブジェクトを取得
		DAOCriteria criteria = new DAOCriteria();

		// お知らせ番号をしているテーブルの主キーの検索条件を生成する。
		criteria.addWhereClause("newsId", this.newsId);
		return criteria;
	}

}
