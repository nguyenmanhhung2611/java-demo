package jp.co.transcosmos.dm3.core.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.transcosmos.dm3.mail.ReplacingMail;

/**
 * テスト環境用メール送信クラス.
 * <p>
 * メール送信時の例外が上位にスローされない様に補足する。<br/>
 * メールを送信する機能が無効化されている訳では無いので扱いには注意する事。<br/>
 * また、メール送信時の例外が補足できないので、実運用では使用しない事。　（デバッグ用のクラス。）<br/>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.20	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class TestReplacingMail extends ReplacingMail {

	private static final Log log = LogFactory.getLog(TestReplacingMail.class);

	/**
	 * メール送信環境が無いローカルデバッグの場合、メール送信時に例外がスローされ、テストが困難な場合が
	 * ある。　このクラスはメール送信時の例外を補足し、上位にスローしない。<br>
	 */
	@Override
    public void send() {

		try {
			super.send();

		} catch (Throwable e) {
			log.debug(e.getMessage(), e);
		}

    }

}
