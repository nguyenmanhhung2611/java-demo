package jp.co.transcosmos.dm3.test.core.validation;

import java.util.ArrayList;
import java.util.List;

import jp.co.transcosmos.dm3.core.validation.LineAdapter;
import jp.co.transcosmos.dm3.validation.AsciiOnlyValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.junit.Test;

public class LineValidationAdapterTest {

	@Test
	public void test(){
		
		List<ValidationFailure> errors = new ArrayList<>();

		int lineNo = 5;
		
        // ＴｅｓｔChain1 （line no あり）
        ValidationChain chain1 = new ValidationChain("label1", "ABCあいう");
        // 必須チェック
        chain1.addValidation(new LineAdapter(new NullOrEmptyCheckValidation(), lineNo));
		// 半角英数記号チェック
        chain1.addValidation(new LineAdapter(new AsciiOnlyValidation(), lineNo));
        chain1.validate(errors);


        // TestChain2 （line no あり）
        ValidationChain chain2 = new ValidationChain("label2", "aaaaaaaaaaaaaaaaaaaa");
		// 必須チェック
        chain2.addValidation(new LineAdapter(new NullOrEmptyCheckValidation(),lineNo));
		// 桁数チェック
        chain2.addValidation(new LineAdapter(new MaxLengthValidation(10),lineNo));
		// 半角英数記号チェック
        chain2.addValidation(new LineAdapter(new AsciiOnlyValidation(),lineNo));
        chain2.validate(errors);

        
        // TestChain3 （line no なし）
        ValidationChain chain3 = new ValidationChain("label2", "ああああああああああああああああああああ");
		// 必須チェック
        chain3.addValidation(new NullOrEmptyCheckValidation());
		// 桁数チェック
        chain3.addValidation(new MaxLengthValidation(10));
		// 半角英数記号チェック
        chain3.addValidation(new AsciiOnlyValidation());
        chain3.validate(errors);


        System.out.println(errors);
        
	}
	
	
}
