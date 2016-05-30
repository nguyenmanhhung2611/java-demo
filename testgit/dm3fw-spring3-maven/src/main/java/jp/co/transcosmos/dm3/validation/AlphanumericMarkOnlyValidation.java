package jp.co.transcosmos.dm3.validation;

public class AlphanumericMarkOnlyValidation implements Validation {

	/** 使用可能記号文字列 */
	private String marks;


    /**
     * コンストラクタ<br/>
     * 引数で指定した記号文字が利用可能になる。<br/>
     * <br/>
     * @param marks 使用可能記号文字
     */
    public AlphanumericMarkOnlyValidation(String marks) {
        this.marks = marks;
    }



	@Override
	public ValidationFailure validate(String name, Object value) {

		// 比較対象が空の場合は正常終了
        if ((value == null) || value.equals("")) {
            return null;
        }

        String valueStr = value.toString().toLowerCase();
        for (int n = 0; n < valueStr.length(); n++) {
            char thisChar = valueStr.charAt(n);
            if ((thisChar >= '0') && (thisChar <= '9')) {
                continue;
            } else if ((thisChar >= 'a') && (thisChar <= 'z')) {
                continue;
            } else {
            	
            	// 許可されてる文字が未指定の場合、エラーとする。
            	if (this.marks == null || this.marks.length() == 0){
            		// この場合のエラーメッセージは、半角英数字チェックと同じエラーメッセージとして扱う
            		return new ValidationFailure("alphanumericOnly", name, value, null);
            	}

            	// 許可される記号文字が設定されている場合、その記号文字であればＯＫとする。
           		if (marks.indexOf(thisChar) >= 0){
           			continue;
           		}
                return new ValidationFailure("aplhaNumMarkOnly", name, value, new String[]{marks});
            } 
        }

        return null;
	}

}
