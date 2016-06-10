package jp.co.transcosmos.dm3.validation;

import java.util.Arrays;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


/**
 * RFC 822 対応の Email バリデーション<br/>
 * ただし、完全対応している訳ではなく、システム都合に合わせていくつか仕様を変更している。<br/>
 * <ul>
 *   <li>ドメインリテラルによる IP指定　（例えば、XXXXX@[192.168.1.1]の様な書式）はエラーとする。</li>
 *   <li>() によるコメント指定　（例えば、（hoge）XXXXX@trans-cosmos.co.jp の様な書式）はエラーとする。</li>
 *   <li>上記、特殊な意味を持つ () [] はダブルコートでエスケープすればユーザー部で使用可能だがエラーとする。</li>
 *   <li>@　も、ダブルコートでエスケープすればユーザー部で使用可能だがエラーとする。</li>
 *   <li>ユーザー部の先頭、末尾でのピリオド、連続したピリオドは通常エラーだが、フレームワークのメール送信処理で
 *   自動的にダブルコートでエスケープする事が可能なので正常とする。</li>
 * </ul>
 * <br/>
 * @author H.Mizuno
 *
 */
public class EmailRFCValidation implements Validation {

	// 使用を許可しない特殊制御文字
	// RFC 的には、ユーザー部であればダブルコートで囲めば使用可能だが、誤動作の問題もあるのでエラーとする。
	private static final char NG_CHARS[] = {'"', '(', ')', ',', ':', ';', '<', '>', '[', '\\', ']'};

	// 注
	// Arrays.binarySearch() を使用する場合、NG_CHARS 配列はソート済である必要がある。
	// よって、上記配列の順番は意味があるので変更する場合は注意する事。

	
	
    @Override
    public ValidationFailure validate(String name, Object value) {

    	// データが空の場合、バリデーションＯＫ
        if ((value == null) || value.equals("")) {
            return null;
        }

        // チェック対象を文字列に変換
        String valueStr = value.toString().toLowerCase();


        // note
        // InternetAddress　の、RFC-822 バリデーションは、ドメイン部に対してのみ。
        // 例外がスローされるのは下記パターンのみ。
        //   ・「@」 が複数存在する場合
        //   ・半角英数、「.」、「-」以外の文字がドメイン部に含まれる場合
        //   ・「.」がドメイン部で連続する場合、「.」がドメイン部の先頭、末尾で使用されている場合
        try {
			new InternetAddress(valueStr, true);
		} catch (AddressException e) {
			return new ValidationFailure("email", name, value, null);
		}

        
        // 使用禁止文字列のチェック
        // RFC-822 としては使用可能な文字列の場合でも、誤動作を避ける為、使用禁止にしている場合がある。
        // また、スペース、および、表示出来ない制御コードもエラーとする。
        for (int n = 0; n < valueStr.length(); n++) {
            char thisChar = valueStr.charAt(n);

            if (Arrays.binarySearch(NG_CHARS, thisChar) >= 0) {
            	// 使用禁止文字が存在する場合はエラーとする。
            	return new ValidationFailure("email", name, value, null);

            } else if (thisChar <= 0x20 || thisChar == 0x7f){
            	// 表示不能な半角制御文字もエラーとする。
            	return new ValidationFailure("email", name, value, null);

            }
        }
    	
        return null;
    }

}
