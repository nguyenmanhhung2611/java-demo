package jp.co.transcosmos.dm3.core.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.transcosmos.dm3.mail.ReplacingMail;

/**
 * �e�X�g���p���[�����M�N���X.
 * <p>
 * ���[�����M���̗�O����ʂɃX���[����Ȃ��l�ɕ⑫����B<br/>
 * ���[���𑗐M����@�\������������Ă����ł͖����̂ň����ɂ͒��ӂ��鎖�B<br/>
 * �܂��A���[�����M���̗�O���⑫�ł��Ȃ��̂ŁA���^�p�ł͎g�p���Ȃ����B�@�i�f�o�b�O�p�̃N���X�B�j<br/>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.20	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class TestReplacingMail extends ReplacingMail {

	private static final Log log = LogFactory.getLog(TestReplacingMail.class);

	/**
	 * ���[�����M�����������[�J���f�o�b�O�̏ꍇ�A���[�����M���ɗ�O���X���[����A�e�X�g������ȏꍇ��
	 * ����B�@���̃N���X�̓��[�����M���̗�O��⑫���A��ʂɃX���[���Ȃ��B<br>
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
