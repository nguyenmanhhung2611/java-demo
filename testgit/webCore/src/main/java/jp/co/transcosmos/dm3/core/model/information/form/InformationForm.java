package jp.co.transcosmos.dm3.core.model.information.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.Information;
import jp.co.transcosmos.dm3.core.vo.InformationTarget;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.AsciiOnlyValidation;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.ValidDateValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

/**
 * <pre>
 * お知らせメンテナンスの入力パラメータ受取り用フォーム
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.06	新規作成
 * H.Mizuno		2015.02.26	パッケージ移動、コンストラクターの隠蔽
 *
 * 注意事項
 * この Form のバリデーションは、使用するモード（追加処理 or 更新処理）で異なるので、
 * フレームワークが提供する Validateable インターフェースは実装していない。
 * バリデーション実行時のパラメータが通常と異なるので注意する事。
 *
 * </pre>
 */
public class InformationForm implements Validateable {

	private static final Log log = LogFactory.getLog(InformationForm.class);
	
	
	/** command パラメータ */
	private String command;
	
	/** お知らせ番号 */
	private String informationNo;
	/** お知らせ種別 （01 = お知らせ、 02 = その他）*/
	private String informationType;
	/** タイトル */
	private String title;
	/** 表示開始日 */
	private String startDate;
	/** 表示終了日 */
	private String endDate;
	/** リンク先URL */
	private String url;
	/** 公開対象区分 (0:仮を含む全会員、1:全本会員、2:個人)*/
	private String dspFlg;
	/** 会員名 */
	private String memberName;
	/** メール送信フラグ  (0:送信しない、1:送信する) */
	private String mailFlg;
	/** お知らせ内容 */
	private String informationMsg;
	/** 対象会員 */
	private String userId;
	
	/** レングスバリデーションで使用する文字列長を取得するユーティリティ */
	protected LengthValidationUtils lengthUtils;

	/** 共通コード変換処理 */
	private CodeLookupManager codeLookupManager;

	
	
	/**
	 * デフォルトコンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	protected InformationForm(){
		super();
	}



	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
	 * @param codeLookupManager 共通コード変換処理
	 */
	protected InformationForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager){
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
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
	public boolean validate(List<ValidationFailure> errors) {

        int startSize = errors.size();

        // お知らせ種別入力チェック
        validInformationType(errors);


        // 公開対象区分入力チェック
        validDspFlg(errors);


        // 特定会員の場合
        if (!StringUtils.isEmpty(this.dspFlg) && "2".equals(this.dspFlg)) {
            // 会員入力チェック
            validUserId(errors);

            // コードの妥当性は、DB 照合が必要なので Command クラス側で行う
        }

        // タイトル入力チェック
        validTitle(errors);

        // お知らせ内容入力チェック
        validInformationMsg(errors);

        // 開始日 入力チェック
        validStartDate(errors);
        
        // 終了日 入力チェック
        validEndDate(errors);
        
        // リンク先 URL 入力チェック
        validInputUrl(errors);

        return (startSize == errors.size());
	}
	
	/**
	 * 開始日 バリデーション<br/>
	 * ・日付書式チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validStartDate(List<ValidationFailure> errors) {
        // 開始日入力チェック
        ValidationChain valStartDate = new ValidationChain("information.input.startDate", this.startDate);
		// 日付書式チェック
        valStartDate.addValidation(new ValidDateValidation("yyyy/MM/dd"));
        valStartDate.validate(errors);
	}
	
	/**
	 * 終了日 バリデーション<br/>
	 * ・日付書式チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validEndDate(List<ValidationFailure> errors) {
        // 終了日入力チェック
        ValidationChain valEndDate = new ValidationChain("information.input.endDate", this.endDate);
		// 日付書式チェック
        valEndDate.addValidation(new ValidDateValidation("yyyy/MM/dd"));
        valEndDate.validate(errors);
	}
	
	/**
	 * お知らせ種別 バリデーション<br/>
	 * ・必須チェック
	 * ・パターンチェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validInformationType(List<ValidationFailure> errors) {
        // お知らせ種別入力チェック
        ValidationChain valType = new ValidationChain("information.input.informationType", this.informationType);
		// 必須チェック
        valType.addValidation(new NullOrEmptyCheckValidation());
        // パターンチェック
        valType.addValidation(new CodeLookupValidation(this.codeLookupManager,"information_type"));
        valType.validate(errors);
	}

	/**
	 * 公開対象区分 バリデーション<br/>
	 * ・必須チェック
	 * ・パターンチェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validDspFlg(List<ValidationFailure> errors) {
        // 公開対象区分入力チェック
        ValidationChain valDspFlg = new ValidationChain("information.input.dspFlg", this.dspFlg);
		// 必須チェック
        valDspFlg.addValidation(new NullOrEmptyCheckValidation());
        // パターンチェック
        valDspFlg.addValidation(new CodeLookupValidation(this.codeLookupManager,"information_dspFlg"));
        valDspFlg.validate(errors);
	}

	/**
	 * 会員入力 バリデーション<br/>
	 * ・必須チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validUserId(List<ValidationFailure> errors) {
        // 会員入力チェック
        ValidationChain valUserId = new ValidationChain("information.input.userId", this.userId);
		// 必須チェック
        valUserId.addValidation(new NullOrEmptyCheckValidation());
        valUserId.validate(errors);
	}

	/**
	 * タイトル バリデーション<br/>
	 * ・必須チェック
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validTitle(List<ValidationFailure> errors) {
        // タイトル入力チェック
        ValidationChain valTitle = new ValidationChain("information.input.title", this.title);
		// 必須チェック
        valTitle.addValidation(new NullOrEmptyCheckValidation());
        // 桁数チェック
        valTitle.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("information.input.title", 200)));
        valTitle.validate(errors);
	}

	/**
	 * ログインID バリデーション<br/>
	 * ・必須チェック
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validInformationMsg(List<ValidationFailure> errors) {
        // お知らせ内容入力チェック
        ValidationChain valInformationMsg = new ValidationChain("information.input.informationMsg", this.informationMsg);
		// 必須チェック
        valInformationMsg.addValidation(new NullOrEmptyCheckValidation());
        // 桁数チェック
        valInformationMsg.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("information.input.informationMsg", 1000)));
        valInformationMsg.validate(errors);
	}

	/**
	 * リンク先 URL バリデーション<br/>
	 * ・桁数チェック
	 * ・半角英数記号チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validInputUrl(List<ValidationFailure> errors) {
        // リンク先 URL 入力チェック
        ValidationChain valUrl = new ValidationChain("information.input.url", this.url);
        // 桁数チェック
        valUrl.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("information.input.url", 255)));
        // 半角英数記号チェック
        valUrl.addValidation(new AsciiOnlyValidation());
        valUrl.validate(errors);
	}



	/**
	 * command パラメータを設定する。<br/>
	 * <br/>
	 * @param command command パラメータ
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * command パラメータを取得する。<br/>
	 * <br/>
	 * @return command パラメータ
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * お知らせ番号を設定する。<br/>
	 * <br/>
	 * @param informationNo お知らせ番号
	 */
	public String getInformationNo() {
		return informationNo;
	}

	/**
	 * お知らせ番号を取得する。<br/>
	 * <br/>
	 * @return お知らせ番号
	 */
	public void setInformationNo(String informationNo) {
		this.informationNo = informationNo;
	}

	/**
	 * お知らせ種別を設定する。<br/>
	 * <br/>
	 * @param informationType お知らせ種別
	 */
	public String getInformationType() {
		return informationType;
	}

	/**
	 * お知らせ種別を取得する。<br/>
	 * <br/>
	 * @return お知らせ種別
	 */
	public void setInformationType(String informationType) {
		this.informationType = informationType;
	}

	/**
	 * タイトルを設定する。<br/>
	 * <br/>
	 * @param title タイトル
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * タイトルを取得する。<br/>
	 * <br/>
	 * @return タイトル
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 表示開始日を設定する。<br/>
	 * <br/>
	 * @param startDate 表示開始日
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * 表示開始日を取得する。<br/>
	 * <br/>
	 * @return 表示開始日
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * 表示終了日を設定する。<br/>
	 * <br/>
	 * @param endDate 表示終了日
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * 表示終了日を取得する。<br/>
	 * <br/>
	 * @return 表示終了日
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * リンク先URLを設定する。<br/>
	 * <br/>
	 * @param url リンク先URL
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * リンク先URLを取得する。<br/>
	 * <br/>
	 * @return リンク先URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 公開対象区分を設定する。<br/>
	 * <br/>
	 * @param dspFlg 公開対象区分
	 */
	public String getDspFlg() {
		return dspFlg;
	}

	/**
	 * 公開対象区分を取得する。<br/>
	 * <br/>
	 * @return 公開対象区分
	 */
	public void setDspFlg(String dspFlg) {
		this.dspFlg = dspFlg;
	}

	/**
	 * お知らせ内容を設定する。<br/>
	 * <br/>
	 * @param informationMsg お知らせ内容
	 */
	public String getInformationMsg() {
		return informationMsg;
	}

	/**
	 * お知らせ内容を取得する。<br/>
	 * <br/>
	 * @return お知らせ内容
	 */
	public void setInformationMsg(String informationMsg) {
		this.informationMsg = informationMsg;
	}

	/**
	 * 会員名を設定する。<br/>
	 * <br/>
	 * @param memberName 会員名
	 */
	public String getMemberName() {
		return memberName;
	}

	/**
	 * 会員名を取得する。<br/>
	 * <br/>
	 * @return 会員名
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	/**
	 * メール送信フラグ を設定する。<br/>
	 * <br/>
	 * @param mailFlg メール送信フラグ 
	 */
	public String getMailFlg() {
		return mailFlg;
	}

	/**
	 * メール送信フラグ を取得する。<br/>
	 * <br/>
	 * @return メール送信フラグ 
	 */
	public void setMailFlg(String mailFlg) {
		this.mailFlg = mailFlg;
	}

	/**
	 * 対象会員を設定する。<br/>
	 * <br/>
	 * @param userId 対象会員
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 対象会員を取得する。<br/>
	 * <br/>
	 * @return 対象会員
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**
	 * 渡されたバリーオブジェクトから Form へ初期値を設定する。<br/>
	 * <br/>
	 * @param information Information　を実装した、お知らせ情報管理用バリーオブジェクト
	 * @param informationTarget InformationTarget を実装したお知らせ公開先情報管理用バリーオブジェクト
	 * @param mypageUserInterface MypageUserInterface を実装したマイページ会員情報管理用バリーオブジェクト
	 */
	public void setDefaultData(Information information, InformationTarget informationTarget, MypageUserInterface mypageUserInterface){

		// バリーオブジェクトに格納されているパスワードはハッシュ値なので Form には設定しない。
		// パスワードの入力は新規登録時のみ必須で、更新処理時は任意の入力となる。
		// 更新処理でパスワードの入力が行われない場合、パスワードの更新は行わない。

		// お知らせ番号 を設定
		this.informationNo = (String) information.getInformationNo();
		// お知らせ種別 を設定
		this.informationType = information.getInformationType();
		// タイトルを設定
		this.title = information.getTitle();
		SimpleDateFormat formatSEDate = new SimpleDateFormat("yyyy/MM/dd"); 
		// 表示開始日を設定
		if (information.getStartDate() != null) {
			this.startDate = formatSEDate.format(information.getStartDate());
		}
		
		// 表示終了日を設定
		if (information.getEndDate() != null) {
			this.endDate = formatSEDate.format(information.getEndDate());
		}
		
		// リンク先URLを設定
		this.url = information.getUrl();
		// 公開対象区分 を設定
		this.dspFlg = information.getDspFlg();
		// 会員名 を設定
		if (!StringUtils.isEmpty(mypageUserInterface.getMemberLname())) {
			this.memberName = mypageUserInterface.getMemberLname() + " ";
		}
		if (!StringUtils.isEmpty(mypageUserInterface.getMemberFname())) {
			this.memberName += mypageUserInterface.getMemberFname();
		}
		// お知らせ内容を設定
		this.informationMsg = information.getInformationMsg();
		// 対象会員IDを設定
		this.userId = informationTarget.getUserId();
		
	}
	


	/**
	 * 引数で渡されたお知らせ情報のバリーオブジェクトにフォームの値を設定する。<br/>
	 * 更新処理でも使用する事を考慮し、タイムスタンプ情報に関しては、更新日、更新者のみ設定する。<br/>
	 * <br/>
	 * @param information 値を設定するお知らせ情報のバリーオブジェクト
	 * 
	 */
	public void copyToInformation(Information information, String editUserId) {
		
		// お知らせ番号 を設定
		if (!StringValidateUtil.isEmpty(this.informationNo)) {
			information.setInformationNo(this.informationNo);
		}
		
		// お知らせ種別 を設定
		information.setInformationType(this.informationType);

		// タイトルを設定
		information.setTitle(this.title);

		// 表示開始日を設定
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		if (!StringValidateUtil.isEmpty(this.startDate)) {
			try {
				information.setStartDate(sdf.parse(this.startDate));
			} catch (ParseException e) {
				// バリデーションで安全性を担保しているので、このタイミングでエラーが発生する事は無いが、
				// 一応、警告をログ出力する。
				log.warn("date format error. (" + this.startDate + ")");
				information.setStartDate(null);
			}
		} else {
			information.setStartDate(null);
		}

		// 表示終了日を設定
		if (!StringValidateUtil.isEmpty(this.endDate)) {
			SimpleDateFormat sdfEnd = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			try {
				information.setEndDate(sdfEnd.parse(this.endDate + " 23:59:59"));
			} catch (ParseException e) {
				// バリデーションで安全性を担保しているので、このタイミングでエラーが発生する事は無いが、
				// 一応、警告をログ出力する。
				log.warn("date format error. (" + this.endDate + ")");
				information.setEndDate(null);;
			}
		} else {
			information.setEndDate(null);
		}

		// リンク先URLを設定
		information.setUrl(this.url);
		
		// 公開対象区分を設定
		information.setDspFlg(this.dspFlg);
		
		// お知らせ内容を設定
		information.setInformationMsg(this.informationMsg);

		// 更新日付を設定
		information.setUpdDate(new Date());;

		// 更新担当者を設定
		information.setUpdUserId(editUserId);

	}

	
	
	/**
	 * お知らせ公開先情報のバリーオブジェクトを作成する。<br/>
	 * <br/>
	 * @param informationTargets　
	 * @return お知らせ公開先情報バリーオブジェクトの配列
	 */
	public void copyToInformationTarget(InformationTarget[] informationTargets){
		
		informationTargets[0].setInformationNo(this.informationNo);
		informationTargets[0].setUserId(this.userId);

	}
	
	/**
	 * お知らせ情報の主キー値となる検索条件を生成する。<br/>
	 * <br/>
	 * このメソッド内では、DB の物理フィールド名を指定しているコードが存在する。<br/>
	 * その為、、管理ユーザー情報のテーブルとして、Information 以外を使用した場合、このメソッドを
	 * オーバーライドする必要がある。<br/>
	 * <br/>
	 * @return 主キーとなる検索条件オブジェクト
	 */
	public DAOCriteria buildPkCriteria(){
		// 検索条件オブジェクトを取得
		DAOCriteria criteria = new DAOCriteria();

		// お知らせ番号をしているテーブルの主キーの検索条件を生成する。 
		criteria.addWhereClause("informationNo", this.informationNo);

		return criteria;
	}


}
