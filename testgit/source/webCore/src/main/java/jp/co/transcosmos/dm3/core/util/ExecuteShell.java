package jp.co.transcosmos.dm3.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExecuteShell {

	private static final Log log = LogFactory.getLog(ExecuteShell.class);

	
	public static int exec(List<String> args) throws IOException, InterruptedException {
		
		// ���O���b�Z�[�W
		if (log.isInfoEnabled()){
			String cmdLine = "";
			for (String arg : args){
				cmdLine = cmdLine + arg + " ";
			}
			log.info(cmdLine);
		}


		// Shell �̖߂�l�B�@����I������ 0 �Ȃ̂ŁA����ȊO�̒l�������l�Ƃ��Đݒ�B
		int ret = 1;

		// Shell �̃v���Z�X���
		Process process = null;
		// �W���o�� Buffer Reader
		BufferedReader bufferReader = null;

		try {

			// �R�}���h���s
			ProcessBuilder processBuilder = new ProcessBuilder(args); 
			processBuilder.redirectErrorStream(true);
			process = processBuilder.start();

			bufferReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuffer buff = new StringBuffer();
			String str = bufferReader.readLine();
			while (str != null){
				buff.append(str + "\n");
				str = bufferReader.readLine();
			}
			log.info(buff.toString());

			bufferReader.close();
			bufferReader = null;

			ret = process.waitFor();

		} finally {
			if (bufferReader != null) {
				try {
					bufferReader.close();

				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}

			if (process != null) {
				process.destroy();
			}
		}

		// ���ʂ����O�o��
		log.info(args.get(0) + " exit code(" + args.get(1) +"):" + ret);
		return ret;

	}
}
