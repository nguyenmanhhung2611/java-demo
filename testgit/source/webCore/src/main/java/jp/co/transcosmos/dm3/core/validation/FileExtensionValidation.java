package jp.co.transcosmos.dm3.core.validation;

import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * ファイル名の拡張子が指定された拡張子かをチェックする.
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.04.07	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class FileExtensionValidation implements Validation {

	/** 入力を許可する拡張子（ピリオド付き）　 */
	private String[] extList = null;


	
	/**
	 * デフォルトコンストラクタ<br/>
	 * <br/>
	 * 
	 */
	public FileExtensionValidation() {

	}

	/**
	 * コンストラクタ<br/>
	 * 入力を許可する拡張子（ピリオド付き）を指定する。<br/>
	 * <br/>
	 * @param extList　入力を許可する拡張子（ピリオド付き）
	 */
	public FileExtensionValidation(String[] extList) {
		this.extList = extList;
	}


	/**
	 * バリデーションを実行<br/>
	 * <br/>
	 * @param name フィールド名
	 * @param 入力値
	 * 
	 * @return エラー発生時は　ValidationFailure　のインスタンスを復帰
	 */
	@Override
	public ValidationFailure validate(String name, Object value) {

		// 空或いはnullの場合、正常終了
		if (value == null || "".equals(value.toString())) {
			return null;
		}

		// 許可された拡張子分繰り返しチェックを行う。
		// その際、大文字、小文字の違いは無視する。
		for (String ext : extList){
			if (value.toString().toUpperCase().endsWith(ext.toUpperCase())){
				return null;
			}
		}

		// extraInfo は、配列の先頭しか見てくれないので、許可する拡張子をカンマ区切りで加工する。
		StringBuffer buff = new StringBuffer(256);
		for (String ext : extList){
			buff.append("," + ext);
		}

		return new ValidationFailure("fileExtNG", name, value, new String[]{buff.toString().substring(1)});
	}
}
