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
		
		// ログメッセージ
		if (log.isInfoEnabled()){
			String cmdLine = "";
			for (String arg : args){
				cmdLine = cmdLine + arg + " ";
			}
			log.info(cmdLine);
		}


		// Shell の戻り値。　正常終了時は 0 なので、それ以外の値を初期値として設定。
		int ret = 1;

		// Shell のプロセス情報
		Process process = null;
		// 標準出力 Buffer Reader
		BufferedReader bufferReader = null;

		try {

			// コマンド実行
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

		// 結果をログ出力
		log.info(args.get(0) + " exit code(" + args.get(1) +"):" + ret);
		return ret;

	}
}
