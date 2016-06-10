package jp.co.transcosmos.dm3.mail;

import java.util.Arrays;
import java.util.Properties;

//import junit.framework.TestCase;

import org.apache.log4j.BasicConfigurator;
//import org.junit.Test;

/**
 * <pre>
 * TODO クラス説明.<BR>
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * k.suehiro      2007/02/13  新規作成
 *
 * 注意事項
 * ・
 *</pre>
 */
public class MailTest {

// TCI 社内環境ではメール送信テストは行えないのでテストは行わない。
// もしテストする場合は @Test のアノテーションを有効にする事。
//	@Test
    public void testMailSending() throws Exception {
        BasicConfigurator.configure();
        
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", "mail.ati.co.jp");
        ReplacingMail mail = new ReplacingMail();
        mail.setVelocityReplaceOverrides(true);
        mail.setMailProperties(props);
        mail.setFromAddress("rick@knowleses.org");
        mail.setErrorTo("rick${n}@knowleses.org");
        mail.setXMailer("rick mailer ${n}");
        mail.setToList(Arrays.asList(new String[] {"Knowles.Rick1@trans-cosmos.co.jp"}));
        mail.setSubject("Test: ${n}");
        mail.setBodyText("Body Test: ${n}");
        for (int n = 0; n < 5; n++) {
            mail.setParameter("n", n);
            mail.send();
        }
    }
}
