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
		
        // �s������Chain1 �iline no ����j
        ValidationChain chain1 = new ValidationChain("label1", "ABC������");
        // �K�{�`�F�b�N
        chain1.addValidation(new LineAdapter(new NullOrEmptyCheckValidation(), lineNo));
		// ���p�p���L���`�F�b�N
        chain1.addValidation(new LineAdapter(new AsciiOnlyValidation(), lineNo));
        chain1.validate(errors);


        // TestChain2 �iline no ����j
        ValidationChain chain2 = new ValidationChain("label2", "aaaaaaaaaaaaaaaaaaaa");
		// �K�{�`�F�b�N
        chain2.addValidation(new LineAdapter(new NullOrEmptyCheckValidation(),lineNo));
		// �����`�F�b�N
        chain2.addValidation(new LineAdapter(new MaxLengthValidation(10),lineNo));
		// ���p�p���L���`�F�b�N
        chain2.addValidation(new LineAdapter(new AsciiOnlyValidation(),lineNo));
        chain2.validate(errors);

        
        // TestChain3 �iline no �Ȃ��j
        ValidationChain chain3 = new ValidationChain("label2", "����������������������������������������");
		// �K�{�`�F�b�N
        chain3.addValidation(new NullOrEmptyCheckValidation());
		// �����`�F�b�N
        chain3.addValidation(new MaxLengthValidation(10));
		// ���p�p���L���`�F�b�N
        chain3.addValidation(new AsciiOnlyValidation());
        chain3.validate(errors);


        System.out.println(errors);
        
	}
	
	
}
