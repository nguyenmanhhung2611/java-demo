package jp.co.transcosmos.dm3.core.model.housing.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.validation.FileExtensionValidation;
import jp.co.transcosmos.dm3.core.validation.LineAdapter;
import jp.co.transcosmos.dm3.core.validation.TraversalValidation;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.dao.UpdateExpression;
import jp.co.transcosmos.dm3.dao.UpdateValue;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ArrayMemberValidation;
import jp.co.transcosmos.dm3.validation.AsciiOnlyValidation;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.NumericValidation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;


/**
 * 物件画像情報メンテナンスの入力パラメータ受取り用フォーム.
 * このフォームは、アップロード後の確認画面、および、ファイル以外の属性更新用。<br/>
 * ファイルアップロードは別のフォームで行う事。（model 側のパッケージでは管理しない。）<br/>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.11	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * この Form のバリデーションは、使用するモード（追加処理 or 更新処理）で異なるので、 フレームワークが提供する Validateable
 * インターフェースは実装していない。<br/>
 * バリデーション実行時のパラメータが通常と異なるので注意する事。<br/>
 * 
 */
public class HousingImgForm {

	/** システム物件CD （更新用） */
	private String sysHousingCd;
	/** 仮フォルダ名　（日付部分） */
	private String tempDate;

	/** 画像タイプ （00:間取図／01:外観／99:その他）*/
	private String imageType[];
	/** 枝番  （更新用）*/
	private String divNo[];
	/** 表示順 */
	private String sortOrder[];
	/** ファイル名  （新規登録用） */
	private String fileName[];
	/** キャプション */
	private String caption[];
	/** コメント */
	private String imgComment[];
	/** 削除フラグ （更新用） 削除時は 1 を設定する。 */
	private String delFlg[];

	/** 旧画像タイプ （画像タイプは主キー構成要素の一つなので、変更前の値が無いと更新できない。 ）*/
	private String oldImageType[];
	
	// 「メイン画像フラグ」、「縦長・横長フラグ」は自動更新項目なので Form では未定義
	
	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;
	/** レングスバリデーションで使用する文字列長を取得するユーティリティ */
	protected LengthValidationUtils lengthUtils;



	/**
	 * デフォルトコンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	protected HousingImgForm() {
		super();
	}

	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
	 */
	protected HousingImgForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager) {
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
	}


	/**
	 * 引数で渡された物件画像情報のバリーオブジェクトにフォームの値を設定する。<br/>
	 * 引数で渡された idx に該当する行の Form 値を設定する。<br/>
	 * 物件画像情報の更新は、主キー値の更新が必要な為、通常の update() では更新できない。<br/>
	 * このメソッドは、基本的に新規登録時の使用を想定している。<br/>
	 * <br/>
	 * @param housingImageInfo 物件画像情報
	 * @param idx 行位置
	 */
	public void copyToHousingImageInfo(HousingImageInfo housingImageInfo, int idx){

		// 枝番は、呼び出し元側で、システム物件番号、画像タイプ内で連番を設定する。
		// パス名は、画像ファイル名、物件基本情報等を元に、呼び出し元で値を設定する。
		// メイン画像フラグは、バッチ更新的に追加・変更・削除後に纏めて処理する。
		// 縦長・横長フラグは、呼び出し元で実際の画像ファイルから情報を取得して設定する。

		// システム物件CD
		housingImageInfo.setSysHousingCd(this.sysHousingCd);

		// 画像タイプ
		housingImageInfo.setImageType(this.imageType[idx]);

		// 表示順
		if (!StringValidateUtil.isEmpty(this.sortOrder[idx])){
			housingImageInfo.setSortOrder(Integer.valueOf(this.sortOrder[idx]));
		}

		// ファイル名
		// 仮フォルダの段階で、シーケンス１０桁のファイル名にリネームされている事。
		housingImageInfo.setFileName(this.fileName[idx]);

		// キャプション
		housingImageInfo.setCaption(this.caption[idx]);

		// コメント
		housingImageInfo.setImgComment(this.imgComment[idx]);

	}
		

	
	/**
	 * 物件画像情報の更新 UpdateExpression を生成する。<br/>
	 * <br/>
	 * 物件画像情報は、主キー値を更新する為、通常の update() メソッドでは更新できない。<br/>
	 * このメソッドが復帰する　UpdateExpression　を使用して更新する。<br/>
	 * <br/>
	 * @param idx 行位置
	 * 
	 * @return 更新タイムスタンプ UPDATE 用　UpdateExpression
	 */
	public UpdateExpression[] buildUpdateExpression(int idx){

       // システム物件コード、パス名、ファイル名は更新対象にはならないので設定しない。
       // メイン画像フラグも、別の処理でバッチ的に更新するので個別には更新しない。
       // 縦長・横長フラグも、画像ファイル自体が更新されないので変更は発生しない。

       return new UpdateExpression[] {new UpdateValue("imageType", this.imageType[idx]),
       							      new UpdateValue("divNo", this.divNo[idx]),
        							  new UpdateValue("sortOrder", this.sortOrder[idx]),
        							  new UpdateValue("caption", this.caption[idx]),
        							  new UpdateValue("imgComment", this.imgComment[idx])};

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
	 * 
	 * @return 正常時 true、エラー時 false
	 */
	public boolean validate(List<ValidationFailure> errors, String mode) {

		int startSize = errors.size();

		// システム物件CD のバリデーションは、Command 側で対応するので From 側では実装しない。
		// （更新時にパラメータが欠落している場合や、該当データが無い場合は例外をスローする。）

		// tempDate のバリデーションは、Command 側で対応するので From 側では実装しない。
		// （新規登録時にパラメータが欠落している場合は例外をスローする。）


		boolean inputLineChk = false;		// 入力行の存在チェックフラグ

		for (int lineNo = 0; lineNo < imageType.length; ++lineNo){
			// １行単位で何かしらの入力があった場合のみチェックを行う。
			// 全てのデータが未入力の場合はバリデーションを行わない。
			if (isLineInput(lineNo)) {
				validate(errors, mode, lineNo);
				inputLineChk = true; 
			}
		}

		// 入力した行が１件も無い場合、バリデーションエラー
		if (!inputLineChk) {

			// TODO エラーメッセージは別途検討。
			
		}

		return (startSize == errors.size());
	}

	/**
	 * 行毎のバリデーション処理<br/>
	 * リクエストパラメータのバリデーションを行う<br/>
	 * <br/>
	 * この Form クラスのバリデーションメソッドは、Validateable インターフェースの実装では無いので、
	 * バリデーション実行時の引数が異なるので、複数 Form をまとめてバリデーションする場合などは注意
	 * する事。<br/>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 * @param mode 処理モード ("insert" or "update")
	 * @param lineNo 行番号
	 * 
	 * @return 正常時 true、エラー時 false
	 */
	protected boolean validate(List<ValidationFailure> errors, String mode, int lineNo) {

		int startSize = errors.size();

		// ファイル名
		validFileName(errors, mode, lineNo);
		// 表示順
		validSortOrder(errors, mode, lineNo);
		// 画像タイプ
		validImageType(errors, mode, lineNo);
		// キャプション
		validCaption(errors, mode, lineNo);
		// コメント
		validImgComment(errors, mode, lineNo);
		// 削除フラグ
		validDelFlg(errors, mode, lineNo);
		// 枝番
		validDivNo(errors, mode, lineNo);

		return (startSize == errors.size());
	}

	

	/**
	 * ファイル名バリデーション<br/>
	 * <ul>
	 *   <li>行単位で入力があった場合は必須</li>
	 *   <li>半角英数記号文字チェック</li>
	 *   <li>ディレクトリトラバーサルチェック</li>
	 *   <li>拡張子チェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 * @param mode 処理モード ("insert" or "update")
	 * @param lineNo 行番号
	 */
	protected void validFileName(List<ValidationFailure> errors, String mode, int lineNo){
		String label = "housingImg.input.fileName";
		ValidationChain valid = new ValidationChain(label, this.fileName[lineNo]);

		// 行単位で何かしらの入力があった場合のみ実行されるので、普通に必須チェックを行う
		valid.addValidation(new LineAdapter(new NullOrEmptyCheckValidation(),lineNo));

		// 半角英数記号文字チェック
		valid.addValidation(new LineAdapter(new AsciiOnlyValidation(),lineNo));

		// ディレクトリトラバーサルチェック
		valid.addValidation(new LineAdapter(new TraversalValidation(),lineNo));

		// ファイル拡張子のチェック
		valid.addValidation(new LineAdapter(new FileExtensionValidation(new String[]{".jpg",".jpeg"}),lineNo));

		// ファイルサイズ等は、仮フォルダにアップする時点でチェック済なはずなので、このタイミングではチェックしない。
		// ファイル拡張子のチェックは念のため。　（パラメータ改竄時は物理ファイルが存在しないはずなので、本来は
		// このタイミングでのチェックは不要。）

		valid.validate(errors);
	}

	/**
	 * 表示順バリデーション<br/>
	 * <ul>
	 *   <li>半角数字チェック</li>
	 *   <li>最大文字数チェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 * @param mode 処理モード ("insert" or "update")
	 * @param lineNo 行番号
	 */
	protected void validSortOrder(List<ValidationFailure> errors, String mode, int lineNo){
		String label = "housingImg.input.sortOrder";
		ValidationChain valid = new ValidationChain(label, this.sortOrder[lineNo]);

		// 半角数字チェック
		valid.addValidation(new LineAdapter(new NumericValidation(),lineNo));
		// 最大桁数チェック
		valid.addValidation(new LineAdapter(new MaxLengthValidation(this.lengthUtils.getLength(label, 3)),lineNo));

		valid.validate(errors);
	}

	/**
	 * 画像タイプバリデーション<br/>
	 * <ul>
	 *   <li>行単位で入力があった場合は必須</li>
	 *   <li>パターンチェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 * @param mode 処理モード ("insert" or "update")
	 * @param lineNo 行番号
	 */
	protected void validImageType(List<ValidationFailure> errors, String mode, int lineNo){
		String label = "housingImg.input.imageType";
		ValidationChain valid = new ValidationChain(label, this.imageType);

		// 行単位で何かしらの入力があった場合のみ実行されるので、普通に必須チェックを行う
		valid.addValidation(new LineAdapter(new NullOrEmptyCheckValidation(),lineNo));
		// パターンチェック
		valid.addValidation(new LineAdapter(new CodeLookupValidation(this.codeLookupManager, "housingImageType"),lineNo));

		valid.validate(errors);
	}
	
	/**
	 * キャプションバリデーション<br/>
	 * <ul>
	 *   <li>最大文字数チェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 * @param mode 処理モード ("insert" or "update")
	 * @param lineNo 行番号
	 */
	protected void validCaption(List<ValidationFailure> errors, String mode, int lineNo){
		String label = "housingImg.input.caption";
		ValidationChain valid = new ValidationChain(label, this.caption);
		
		// 最大桁数チェック
		valid.addValidation(new LineAdapter(new MaxLengthValidation(this.lengthUtils.getLength(label, 20)),lineNo));

		valid.validate(errors);
	}

	/**
	 * コメントバリデーション<br/>
	 * <ul>
	 *   <li>最大文字数チェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 * @param mode 処理モード ("insert" or "update")
	 * @param lineNo 行番号
	 */
	protected void validImgComment(List<ValidationFailure> errors, String mode, int lineNo){
		String label = "housingImg.input.imgComment";
		ValidationChain valid = new ValidationChain(label, this.imgComment);
		
		// 最大桁数チェック
		valid.addValidation(new LineAdapter(new MaxLengthValidation(this.lengthUtils.getLength(label, 200)),lineNo));

		valid.validate(errors);
	}

	
	/**
	 * 削除フラグバリデーション<br/>
	 * <ul>
	 *   <li>パターンチェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 * @param mode 処理モード ("insert" or "update")
	 * @param lineNo 行番号
	 */
	protected void validDelFlg(List<ValidationFailure> errors, String mode, int lineNo){
		String label = "housingImg.input.delFlg";
		ValidationChain valid = new ValidationChain(label, this.delFlg);

		// パターンチェック
		valid.addValidation(new ArrayMemberValidation(new String[]{"1"}));

		valid.validate(errors);
	}

	/**
	 * 枝番バリデーション<br/>
	 * <ul>
	 *   <li>更新処理の場合、必須チェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 * @param mode 処理モード ("insert" or "update")
	 * @param lineNo 行番号
	 */
	protected void validDivNo(List<ValidationFailure> errors, String mode, int lineNo){
		String label = "housingImg.input.divNo";
		ValidationChain valid = new ValidationChain(label, this.divNo);

		// 行単位で何かしらの入力があった場合のみ実行されるので、更新処理の場合、普通に必須チェックを行う
		if (mode.equals("update")) {
			valid.addValidation(new LineAdapter(new NullOrEmptyCheckValidation(),lineNo));
		}

		// 不正な値に改竄した場合、更新や削除対象が無いだけなので、これ以上のチェックは行わない。

		valid.validate(errors);
	}

	
	
	/**
	 * 行データの全てが未入力かをチェックする。<br/>
	 * <br/>
	 * @param lineNo 行番号
	 * @return true 入力あり、false 入力なし
	 */
	protected boolean isLineInput(int lineNo) {
		
		// 画像タイプの入力があったか？
		if (!StringValidateUtil.isEmpty(this.imageType[lineNo])) return true;

		// 枝番がパラメータで渡されているか？
		if (!StringValidateUtil.isEmpty(this.divNo[lineNo])) return true;

		// 表示順の入力があったか？
		if (!StringValidateUtil.isEmpty(this.sortOrder[lineNo])) return true;
		
		// ファイル名がパラメータで渡されているか？
		if (!StringValidateUtil.isEmpty(this.fileName[lineNo])) return true;

		// キャプションの入力があったか？
		if (!StringValidateUtil.isEmpty(this.caption[lineNo])) return true;
		
		// コメントの入力があったか？
		if (!StringValidateUtil.isEmpty(this.imgComment[lineNo])) return true;
		
		// 削除フラグの入力があったか？
		if (!StringValidateUtil.isEmpty(this.delFlg[lineNo])) return true;

		return false;
	}
	
	

	
	/**
	 * システム物件CD を取得する。<br/>
	 * <br/>
	 * @return システム物件CD
	 */
	public String getSysHousingCd() {
		return sysHousingCd;
	}

	/**
	 * システム物件CD を設定する。<br/>
	 * <br/>
	 * @param sysHousingCd システム物件CD
	 */
	public void setSysHousingCd(String sysHousingCd) {
		this.sysHousingCd = sysHousingCd;
	}

	/**
	 * サブフォルダ名で使用している日付を取得する。<br/>
	 * 日付がまたがる事を想定し、仮フォルダ格納時に作成したサブホルダ名（ YYYYMMDD）を取得する。<br/>
	 * <br/>
	 * @return サブフォルダ名で使用している日付
	 */
	public String getTempDate() {
		return tempDate;
	}

	/**
	 * サブフォルダ名で使用している日付を設定する。<br/>
	 * 日付がまたがる事を想定し、仮フォルダ格納時に作成したサブホルダ名（ YYYYMMDD）を設定する。<br/>
	 * <br/>
	 * @param tempDate サブフォルダ名で使用している日付
	 */
	public void setTempDate(String tempDate) {
		this.tempDate = tempDate;
	}

	/**
	 * 画像タイプ （00:間取図／01:外観／99:その他）を取得する。<br/>
	 * <br/>
	 * @return 画像タイプ
	 */
	public String[] getImageType() {
		return imageType;
	}

	/**
	 * 画像タイプ （00:間取図／01:外観／99:その他）を設定する。<br/>
	 * <br/>
	 * @param imageType 画像タイプ
	 */
	public void setImageType(String[] imageType) {
		this.imageType = imageType;
	}

	/**
	 * 枝番を取得する。<br/>
	 * <br/>
	 * @return 枝番
	 */
	public String[] getDivNo() {
		return divNo;
	}

	/**
	 * 枝番を設定する。<br/>
	 * <br/>
	 * @param divNo 枝番
	 */
	public void setDivNo(String[] divNo) {
		this.divNo = divNo;
	}

	/**
	 * 表示順を取得する。<br/>
	 * <br/>
	 * @return 表示順
	 */
	public String[] getSortOrder() {
		return sortOrder;
	}

	/**
	 * 表示順を設定する。<br/>
	 * <br/>
	 * @param sortOrder 表示順
	 */
	public void setSortOrder(String[] sortOrder) {
		this.sortOrder = sortOrder;
	}


	/**
	 * ファイル名を取得する。<br/>
	 * <br/>
	 * @return ファイル名
	 */
	public String[] getFileName() {
		return fileName;
	}

	/**
	 * ファイル名を設定する。<br/>
	 * <br/>
	 * @param fileName ファイル名
	 */

	public void setFileName(String[] fileName) {
		this.fileName = fileName;
	}



	/**
	 * キャプションを取得する。<br/>
	 * <br/>
	 * @return キャプション
	 */
	public String[] getCaption() {
		return caption;
	}

	/**
	 * キャプションを設定する。<br/>
	 * <br/>
	 * @param caption キャプション
	 */
	public void setCaption(String[] caption) {
		this.caption = caption;
	}

	/**
	 * コメントを取得する。<br/>
	 * <br/>
	 * @return コメント
	 */
	public String[] getImgComment() {
		return imgComment;
	}

	/**
	 * コメントを設定する。<br/>
	 * <br/>
	 * @param imgComment コメント
	 */
	public void setImgComment(String[] imgComment) {
		this.imgComment = imgComment;
	}

	/**
	 * 旧画像タイプを取得する。<br/>
	 * <br/>
	 * @return 旧画像タイプ
	 */
	public String[] getOldImageType() {
		return oldImageType;
	}

	/**
	 * 旧画像タイプを取得する。<br/>
	 * <br/>
	 * @param oldImageType 旧画像タイプ
	 */
	public void setOldImageType(String[] oldImageType) {
		this.oldImageType = oldImageType;
	}

	/**
	 * 削除フラグを取得する。<br/>
	 * <br/>
	 * @return 削除フラグ
	 */
	public String[] getDelFlg() {
		return delFlg;
	}

	/**
	 * 削除フラグを設定する。<br/>
	 * <br/>
	 * @param delFlg 削除フラグ
	 */
	public void setDelFlg(String[] delFlg) {
		this.delFlg = delFlg;
	}

}
