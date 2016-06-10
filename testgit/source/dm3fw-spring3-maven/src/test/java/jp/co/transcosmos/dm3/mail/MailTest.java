package jp.co.transcosmos.dm3.mail;

import java.util.Arrays;
import java.util.Properties;

//import junit.framework.TestCase;

import org.apache.log4j.BasicConfigurator;
//import org.junit.Test;

/**
 * <pre>
 * TODO �N���X����.<BR>
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * k.suehiro      2007/02/13  �V�K�쐬
 *
 * ���ӎ���
 * �E
 *</pre>
 */
public class MailTest {

// TCI �Г����ł̓��[�����M�e�X�g�͍s���Ȃ��̂Ńe�X�g�͍s��Ȃ��B
// �����e�X�g����ꍇ�� @Test �̃A�m�e�[�V������L���ɂ��鎖�B
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
