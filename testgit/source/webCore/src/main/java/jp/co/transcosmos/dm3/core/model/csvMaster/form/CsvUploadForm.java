package jp.co.transcosmos.dm3.core.model.csvMaster.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.validation.FileUploadedValidator;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.fileupload.FileItem;

/**
 * 駅マスタCSV、住所マスタCSV 取り込み処理用フォーム.
 * <p>
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.28	Shamaison を参考に新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class CsvUploadForm implements Validateable {

	/** アップロードファイル */
	private FileItem csvFile;


	
	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	protected CsvUploadForm(){
		super();
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

		// ファイルアップロードのチェック
		validCsvFile(errors);
		// 拡張子チェック
		validNotCsv(errors, startSize);

		return startSize == errors.size();

	}

	/**
	 * ファイルアップロード バリデーション<br/>
	 * ・ファイルアップロードのチェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validCsvFile(List<ValidationFailure> errors) {
		// ファイルアップロードのチェック
		ValidationChain valid = new ValidationChain("master.csvFile.file", this.csvFile);
		valid.addValidation(new FileUploadedValidator());
		valid.validate(errors);
	}

	/**
	 * 拡張子 バリデーション<br/>
	 * ・ファイル名が未設定のチェック
	 * ・大文字、小文字を無視して拡張子が .csvのチェック
	 * ・ファイル名桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validNotCsv(List<ValidationFailure> errors, int startSize) {
		// 拡張子チェック
		if (startSize == errors.size()) {
			String fileName = this.csvFile.getName();
			ValidationFailure notCsvFailure = new ValidationFailure("notCsv", "", "", null);

			if (fileName == null) {
				// ファイル名が未設定の場合はエラー
				errors.add(notCsvFailure);

			} else {

				// 大文字、小文字を無視して拡張子が .csv でなければエラー
				if (!fileName.toLowerCase().endsWith(".csv")){
					errors.add(notCsvFailure);
				}

				// 拡張子が .csv の場合、ファイル名長は 4文字以上のなるので、そのチェックを行う。
				if (fileName.length() <= 4){
					errors.add(notCsvFailure);
				}
			}
		}
	}


	
	/**
	 * アップロードファイルの情報を取得する。<br/>
	 * <br/>
	 * @return アップロードファイル情報
	 */
	public FileItem getCsvFile() {
		return this.csvFile;
	}

	/**
	 * アップロードファイルの情報を設定する。<br/>
	 * <br/>
	 * @param csvFile アップロードファイル情報
	 */
	public void setCsvFile(FileItem csvFile) {
		this.csvFile = csvFile;
	}

}
