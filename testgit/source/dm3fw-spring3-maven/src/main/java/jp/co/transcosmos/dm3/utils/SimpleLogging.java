package jp.co.transcosmos.dm3.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.transcosmos.dm3.mail.ReplacingMail;


/**
 * �ȈՃ��O�o�̓N���X 
 * ���O�t�@�C���ւ̏o�͂�Ή�����B
 *�@�g�p����ꍇ�́ASpring �� DI �R���e�i�ŃV���O���g���Œ�`���鎖�B
 *�@�i���ڃC���X�^���X�������ꍇ��Ascope ���v���g�^�C�v�̏ꍇ�́A�}���`�X���b�h���œ���ۏ؂��Ȃ��B�j
 *
 * ������ bean ��`������ꍇ�A���O�t�@�C�������d�����Ȃ��l�ɒ��ӂ��鎖�B�@�ibean �P�ʂŔr��������s���Ă���ׁB�j
 * 
 */
public class SimpleLogging implements CommonLogging {

    private static final Log log = LogFactory.getLog(SimpleLogging.class);

	// �^�C���A�E�g���� �i�~���b�j�@�ȗ������ꍇ�͖������Ƀ��b�N��ҋ@����B
	private Integer timeout = null;

	// ���O�t�@�C�����i�K�{�j
	private String logFileName = null;

	// ���[�����M�I�u�W�F�N�g�@�I�u�W�F�N�g��ݒ肵�Ă���ꍇ�A��O�������ɃG���[���[���𑗐M����B
	// ���Ȃ��Ƃ��A���M��A���M���A�^�C�g���̃v���p�e�B���ݒ肳��Ă��鎖�B
	private ReplacingMail mail = null;

	// �t�@�C�����[�e�[�g�w��
	//�@"year"�A"month"�A"day" ���w�肳���ƁA�t�@�C�����ɁA�u_YYYY�v�A�u_YYYYMM�v�A�u_YYYYMMDD�v ���t�������B
	// ���ݒ�̏ꍇ�A�t�@�C�����ɉ����t�����Ȃ��B�@�i���[�e�[�g���Ȃ��B�j
	private String rotate = null;

// 2015.03.31 H.Mizuno �����R�[�h���w�肷��@�\��ǉ� start
	/** �o�͕����R�[�h�B�@�ȗ����̓v���b�g�z�[�����̃f�t�H���g�l���K�p�����B */
	private String encode = null;

	/**
	 * �����R�[�h�ݒ�<br/>
	 * �ݒ���ȗ������ꍇ�̏����l�� UTF-8�B<br/>
	 * <br/>
	 * @param encode String �����R�[�h������
	 */
	public void setEncode(String encode) {
		this.encode = encode;
	}
// 2015.03.31 H.Mizuno �����R�[�h���w�肷��@�\��ǉ� end



	// ���b�N�I�u�W�F�N�g
	// ������ SimpleLogging �� bean ��`���鎖��O��Ƃ��Ă���ׁA���b�N�I�u�W�F�N�g�� static �Ő錾���Ă��Ȃ��B
	// �i���̃��O�o�͂����b�N���Ȃ��ׁB�j
	private final Lock lock = new ReentrantLock();

	// ���O�o�͎��̓��t����
	private final SimpleDateFormat logDateFmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss S");



	/**
	 * ���O�o�͏���<br>
	 * <br>
	 * @param msg �o�̓��b�Z�[�W
	 * @return true=����I���Afalse=�G���[
	*/
	@Override
	public boolean write(String msg) {

		if (this.logFileName == null) {
			// ���O�t�@�C���������ݒ�̏ꍇ��O���X���[
			// �����̏ꍇ�́A���炩�ȂȐݒ�~�X�i�o�O�j�Ȃ̂Ń����^�C���G���[���X���[����B
			RuntimeException e = new RuntimeException("SimpleLogging logFileName properties is null"); 
			sendErrorMailAndLogging(e);
			throw e;
		}
		
	
		// ���̃��\�b�h�́A���O�t�@�C�����̐ݒ�~�X�ȊO�͗�O���X���[���Ȃ��B
		// (���O�o�͎��̖����A�Ɩ��A�v���P�[�V�����ɉe�������Ȃ��ׁB�j

		
		if (this.timeout == null || this.timeout == 0){
			// �^�C���A�E�g�̐ݒ肪����Ă��Ȃ��ꍇ�̓��b�N���擾�ł���܂őҋ@����B
			return lockWrite(msg);
		} else {
			// �^�C���A�E�g���ݒ肳��Ă���ꍇ�̓^�C���A�E�g���Ԃ�ݒ肵�ă��b�N���擾����B
			return tryLockWrite(msg);
		}
	}



	/**
	 * �������ҋ@�̔r������ɂ�郍�O�o�͏���<br>
	 * <br>
	 * @param msg �o�̓��b�Z�[�W
	 * @return true=����I���Afalse=�G���[
	*/
	private boolean lockWrite(String msg) {

		try {
			// ���b�N���擾�ł���܂Ŗ������ɑҋ@����B
			this.lock.lock();
			
			// ���O�̏������݂����{����
			return logWriter(msg);

		} finally {
			// ���b�N�̊J��
			this.lock.unlock();

		}
	}



	/**
	 * �^�C���A�E�g�t���r������ɂ�郍�O�o�͏���<br>
	 * <br>
	 * @param msg �o�̓��b�Z�[�W
	 * @return true=����I���Afalse=�G���[
	*/
	private boolean tryLockWrite(String msg) {
		
		try {
			if (this.lock.tryLock(this.timeout, TimeUnit.MILLISECONDS)){
				try {
					// ���O�̏������݂����{����
					return logWriter(msg);
					
				} finally {
					// ���b�N���擾�ł��Ă���ꍇ�̓��b�N���J������
					this.lock.unlock();

				}

			} else {
				// ���b�N���擾�o���Ȃ������ꍇ�� Log4J �̃��M���O��G���[���[���ő��M����B
				// �A�v���P�[�V�����ɂ͗�O�̓X���[���Ȃ��B
				RuntimeException e = new RuntimeException("SimpleLogging lock time out"); 
				sendErrorMailAndLogging(e);
				return false;
			}

		} catch (InterruptedException e) {
			// �����ݗ�O�����������ꍇ����O���X���[���Ȃ��B
			// ��O�̏��� Log4J �̃��M���O��G���[���[���ő��M����B
			sendErrorMailAndLogging(e);
			return false;
		}
	}



	/**
	 * ���O�o�͏���<br>
	 * <br>
	 * @param msg �o�̓��b�Z�[�W
	 * @return true=����I���Afalse=�G���[
	*/
	private boolean logWriter(String msg){

		// ���O�ɏo�͂�����t���擾
		String logDate = logDateFmt.format(new Date());


		// rotate �v���p�e�B���ݒ肳��Ă���ꍇ�A�t�@�C�����ɕt�^����B
		String fileName = this.logFileName;

		if (rotate != null) {

			String fileNameDate = "";
			// ���[�e�[�g�����ɍ��킹�ăt�@�C�����̓��t�������擾
			if (rotate.equals("year")) {
				fileNameDate = "_" + logDate.substring(0,4);

			} else if (rotate.equals("month")) {
				fileNameDate = "_" + logDate.substring(0,4) + logDate.substring(5,7);
				
			} else if (rotate.equals("day")) {
				fileNameDate = "_" + logDate.substring(0,4) + logDate.substring(5,7) + logDate.substring(8,10);

			}


			// note
			// �ׂ����Ή�����ƃ��O�o�͂̐��\�ɂ��e������̂ŁA���}���b��I�Ȓu�������B
			// �t�@�C�����Ŋg���q���g�p�����ɁA�t�H���_���Ńs���I�h���g�p�����ꍇ�A�t�H���_�����u�������B

			if (this.logFileName.lastIndexOf(".") == -1) {
				// �g���q�������ꍇ�A�����ɂ��̂܂ܓ��t��ǉ�
				fileName = fileName + fileNameDate;

			} else {
				// �g���q�����t�������ꍇ�A���t��t��
				fileName = fileName.replaceAll("(.*)\\.(.+?)$", "$1" + fileNameDate + ".$2");

			}
		}
		
		
		// ���O�t�@�C���̃I�u�W�F�N�g
		File file = new File(fileName);
		// ���O�t�@�C���̃��C�^�[�I�u�W�F�N�g
		PrintWriter writer = null;

		try {

			// ���O�t�@�C���ւ̏�������

// 2015.03.31 H.Mizuno �����R�[�h���w�肷��@�\��ǉ� start
//			writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
			if (this.encode == null || this.encode.equals("")){
				// �����R�[�h�����w��̏ꍇ�A�v���b�g�z�[�����̃f�t�H���g�l�ŏo��
				writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
			} else {
				// �w�肳�ꂽ�����R�[�h�ŏo��
				writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true),this.encode)));
			}
// 2015.03.31 H.Mizuno �����R�[�h���w�肷��@�\��ǉ� end

			writer.println("[" + logDate + "] " + msg);

			return true;

		} catch (IOException e) {
			// �������݃G���[���������Ă���O���X���[���Ȃ��B�@��O�̏��� Log4J �̃��M���O��G���[���[���ő��M����B
			// �Ⴆ�΁A�o�͐�t�@�C�����r�����b�N����Ă���ꍇ�ł��G���[�ɂ��Ȃ��B
			sendErrorMailAndLogging(e);
			return false;

		} finally {
			if (writer != null) writer.close();

		}
	}

	

	/**
	 * �G���[���O�̏o�́A����сA�G���[���[���̑��M����<br>
	 * <br>
	 * @param e ��O�I�u�W�F�N�g
	*/
	private void sendErrorMailAndLogging(Exception e){

		// �X�^�b�N�g���[�X�̎擾
		StringWriter sw = null;
        PrintWriter pw = null;
        String stacktrace = null;

		try {
			sw = new StringWriter();
	        pw = new PrintWriter(sw);

	        e.printStackTrace(pw);
	        stacktrace = sw.toString();
		} finally {
			if (sw != null) {
				try {
					sw.close();
				} catch (IOException e1) {
					log.error(e1);
				}
			}
			if (pw != null) {
				pw.close();
			}
		}


		// ���ʂ̃��M���O�ɂ̓��[�j���O���x���ŏo�͂���B
		log.warn(stacktrace);

		
		// ���[���I�u�W�F�N�g�������ݒ肳��Ă��Ȃ��ꍇ�A���[�����M�͍s��Ȃ��B
		if (this.mail == null) return;

		// �G���[���[�����M����
		try {
			this.mail.setBodyText(e.getMessage());
	        this.mail.sendThreaded();

		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
	}
	
	

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public void setLogFileName(String logFileName) {
		this.logFileName = logFileName;
	}

	public void setMail(ReplacingMail mail) {
		this.mail = mail;
	}

	public void setRotate(String rotate) {
		this.rotate = rotate;
	}
}
