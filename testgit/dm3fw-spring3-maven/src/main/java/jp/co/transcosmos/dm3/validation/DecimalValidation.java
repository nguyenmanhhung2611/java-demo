package jp.co.transcosmos.dm3.validation;

/**
 * 十進少数のチェック<br/>
 * 整数部、小数部の桁数が指定された精度内かをチェックする。<br/>
 * 使用可能な文字は、0 ～ 9、ピリオド１個となる。
 * 
 * ※積水のカスタムバリデーションをカスタマイズして実装
 * 
 * @author H.Mizuno
 *
 */
public class DecimalValidation implements Validation {

	/** 整数部桁数 */
	private int num;
	/** 小数部桁数 */
	private int dec;


	/**
	 * コンストラクタ
	 * @param num ピリオドの抜きにした、数値全体の桁数
	 * @param dec 小数部の桁数
	 */
	public DecimalValidation(int num, int dec) {
	    this.num = num - dec;
	    this.dec = dec;
	}

	
	
	@Override
	public ValidationFailure validate(String name, Object value) {

		// null、空文字の場合は通過
		if ((value == null) || value.equals("")) {
			return null;
		}

		String target = value.toString();
		String array[] = target.split("\\.");

		// ピリオドのみの場合、配列のサイズが 0 になる。
		// その場合はエラーを復帰する。
		if (array.length == 0) {
			return failure(name, value);
		}


		// 構成文字列が数値かのチェック
		for (String str : array){

	        for (int n = 0; n < str.length(); n++) {
	            char thisChar = str.charAt(n);
	            if ((thisChar >= '0') && (thisChar <= '9')) {
	                continue;
	            } else {
	            	return failure(name, value);
	            } 
	        }
		}

		// ピリオドの数をチェック （2個以上はエラー）
		if (array.length >= 3) {
        	return failure(name, value);
		}

		// 整数部の桁数チェック
		if(array[0].length() > this.num) {
        	return failure(name, value);
		}

		// ピリオドの入力がある場合、小数部の桁数チェック
		if (array.length == 2 && array[1].length() > this.dec) {
			return failure(name, value);
		}

		return null;
	}

	

	/**
	 * エラーオブジェクトを復帰する。<br/>
	 * <br/>
	 * @param name　入力フィールドのラベル名
	 * @param value 入力値
	 * 
	 * @return エラーオブジェクト
	 */
	protected ValidationFailure failure(String name, Object value){

		return new ValidationFailure("decimal", name, value.toString(),
									 new String[] {String.valueOf(this.num), String.valueOf(this.dec)});
		
	}
}
