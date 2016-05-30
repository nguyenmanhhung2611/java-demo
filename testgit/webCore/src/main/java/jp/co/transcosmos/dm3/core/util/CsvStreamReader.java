package jp.co.transcosmos.dm3.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.transcosmos.loader.reader.CsvDataReader;

/**
 * CSV�@�f�[�^�� Reader �N���X.
 * <p>
 * ���{�I�ȏ����́A�o�b�`�t���[�����[�N�ł���AExecuteLoader �� CsvDataReader
 * �N���X�֏������Ϗ�����B<br/>
 * CsvDataReader�@�N���X���t�@�C�����̑���� InputStreamReader �Ŏg�p�ł���
 * �l�Ɋg�������A�p���^ Adapter �N���X�ɂȂ�B<br/>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.06	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class CsvStreamReader extends CsvDataReader {

	private static Log log = LogFactory.getLog(CsvStreamReader.class); 

	/** CSV �Ǎ��ݑΏ� InputStreamReader */
	protected InputStreamReader inputStreamReader;



	/** �R���X�g���N�^�[<br/>
	 * <br/>
	 * @param inputStreamReader CSV �Ǎ��ݑΏۂƂȂ� InputStreamReader
	*/
	public CsvStreamReader(InputStreamReader inputStreamReader) {
		// InputStreamReader �̏ꍇ�A�t�@�C�����͑��݂��Ȃ��B
		// �_�~�[�l�Ƃ��āA"input stream reader" ��ݒ肷��B
		super("input stream reader");
		this.inputStreamReader = inputStreamReader;
	}

	/** �R���X�g���N�^�[<br/>
	 * <br/>
	 * @param inputStreamReader CSV �Ǎ��ݑΏۂƂȂ� InputStreamReader
	 * @param encode �����R�[�h������
	 * @param terminator char ��؂蕶���R�[�h
	*/
	public CsvStreamReader(InputStreamReader inputStreamReader, char terminator) {
		// InputStreamReader �̏ꍇ�A�G���R�[�h�ݒ�́A���Ɋm�肵�Ă���̂�
		// �R���X�g���N�^�[�ւ� null ��n���B
		super("input stream reader", null, terminator);
		this.inputStreamReader = inputStreamReader;
	}



	/**
	 * ��������<br/>
	 * �p�����N���X�ł́A�t�@�C�������w�肵�� BufferReader �𐶐����邪�A���̃N���X�ł�
	 * �R���X�g���N�^�Ŏw�肳�ꂽ InputStreamReader �𒼐ڎw�肷��B<br/>
	 * <br/>
	 * �e�N���X�̃��\�b�h���Ăяo���Ă��Ȃ��̂ŁA�e�N���X�̊g���ɂ͒��ӂ��鎖�B<br/>
	 * <br/>
	 * @exception IOException
	 */
	@Override
	public void open() throws IOException {
		log.debug("open() start.�@(InputStreamReder Version)"); 

		// ���Ɏg�p���̏ꍇ�A��O���X���[����B
		if (this.bufferReader != null) {
			throw new RuntimeException("CsvDataReader is already opened.");
		}

		// ���[�_�[�𐶐�����B
		this.bufferReader = new BufferedReader(this.inputStreamReader);

		// �^�C�g���̃X�L�b�v�t���O�����Z�b�g
		this.skipped = false;

		log.info("open() success. (InputStreamReder Version)"); 

	}

	

	/**
	 * �㏈��<br/>
	 * �n���ꂽ InputStreamReader ���N���[�Y������A�e�N���X�̃��\�b�h�����s����B<br/>
	 * <br/>
	 * @exception IOException
	 */
	@Override
	public void close() throws IOException {
		try {
			this.inputStreamReader.close();
		} finally {
			super.close();
		}
	}
	
}
