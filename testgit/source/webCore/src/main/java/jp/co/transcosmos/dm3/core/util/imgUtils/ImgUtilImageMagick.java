package jp.co.transcosmos.dm3.core.util.imgUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.transcosmos.dm3.core.util.ExecuteShell;
import jp.co.transcosmos.dm3.core.util.ImgUtils;


/**
 * ImageMagick によるサムネイル画像の作成クラス.
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.23	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスの実行環境には、ImageMagick をインストールしておく事。
 * 
 */
public class ImgUtilImageMagick implements ImgUtils {

	private static final Log log = LogFactory.getLog(ImgUtilImageMagick.class);
	
	/** ImageMagick 画像変換コマンド名 */
	private String convertCommand = "convert";

	

	/**
	 * ImageMagick の画像変換コマンド名を設定する。<br/>
	 * 通常、デフォルト設定で変更する必要は無いが、path の問題でフルパス定義する場合はこのプロパティを設定する。<br/>
	 * <br/>
	 * @param convertCommand　ImageMagick の画像変換コマンド名
	 */
	public void setConvertCommand(String convertCommand) {
		this.convertCommand = convertCommand;
	}



	/**
	 * 画像情報を取得する。<br/>
	 * <br/>
	 * @param srcFile オリジナル画像のフルパスファイル名
	 * @return 画像情報が格納された ImgInfo オブジェクト
	 * 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@Override
	public ImgInfo getImgInfo(String srcFile) throws FileNotFoundException, IOException {

		return getImgInfo(new File(srcFile));
	}
	
	/**
	 * 画像情報を取得する。<br/>
	 * <br/>
	 * @param srcFile オリジナル画像の File オブジェクト
	 * @return 画像情報が格納された ImgInfo オブジェクト
	 * 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@Override
	public ImgInfo getImgInfo(File srcFile)	throws FileNotFoundException, IOException {

		ImgInfo imgInfo = new ImgInfo();
		
		try (FileInputStream srcFis = new FileInputStream(srcFile)){
			BufferedImage srcImgBuff = ImageIO.read(srcFis);

			imgInfo.setWidth(srcImgBuff.getWidth());		// 元画像ファイルサイズ（幅）
			imgInfo.setHeight(srcImgBuff.getHeight());		// 元画像ファイルサイズ（高）

			// 縦長・横長判定
			if (imgInfo.getWidth() > imgInfo.getHeight()){
				imgInfo.setHwFlg(0);						// 横長

			} else if (imgInfo.getWidth() < imgInfo.getHeight()) {
				imgInfo.setHwFlg(1);						// 縦長

			} else {
				imgInfo.setHwFlg(2);						// 縦横同一

			}
		}

		return imgInfo;
	}


	
	/**
	 * サムネイル画像を作成する。<br/>
	 * <ul>
	 *   <li>オリジナル画像とサムネイル画像が同じサイズの場合、そのままコピーする。</li>
	 *   <li>オリジナル画像がサムネイル画像よりも小さい場合、オリジナル画像をそのままコピーする。</li>
	 *   <li>横長画像の場合、指定されたサムネイルサイズを横幅として使用する。</li>
	 *   <li>縦長画像の場合、指定されたサムネイルサイズを縦幅として使用する。</li>
	 * </ul>
	 * <br/>
	 * @param srcFile オリジナル画像のフルパスファイル名
	 * @param destFile サムネイルの出力先フルパスファイル名
	 * @param size 作成するサムネイルのサイズ
	 * 
	 * @return 作成したサムネイルのサイズ情報
	 * @throws Exception 
	 */
	@Override
	public ImgInfo createImgFile(String srcFile, String destFile, int size)
			throws Exception {

		return createImgFile(new File(srcFile), new File(destFile), size);
	}

	/**
	 * サムネイル画像を作成する。<br/>
	 * <ul>
	 *   <li>オリジナル画像とサムネイル画像が同じサイズの場合、そのままコピーする。</li>
	 *   <li>オリジナル画像がサムネイル画像よりも小さい場合、オリジナル画像をそのままコピーする。</li>
	 *   <li>横長画像の場合、指定されたサムネイルサイズを横幅として使用する。</li>
	 *   <li>縦長画像の場合、指定されたサムネイルサイズを縦幅として使用する。</li>
	 * </ul>
	 * <br/>
	 * @param srcFile オリジナル画像の File オブジェクト
	 * @param destFile サムネイルの出力先 File オブジェクト
	 * @param size 作成するサムネイルのサイズ
	 * 
	 * @return 作成したサムネイルのサイズ情報
	 * @throws Exception 
	 */
	@Override
	public ImgInfo createImgFile(File srcFile, File destFile, int size)
			throws Exception {

		// オリジナル画像サイズを取得
		ImgInfo srcImgInfo = getImgInfo(srcFile);		

		// サムネイル画像サイズを取得
		ImgInfo destImgInfo = createThumImgInfo(srcImgInfo, size);


		// オリジナル画像サイズと作成するサムネイル画像サイズが同じ場合、ファイルをコピーする。
		if(srcImgInfo.getWidth().equals(destImgInfo.getWidth())
				&& srcImgInfo.getHeight().equals(destImgInfo.getHeight())){

			FileUtils.copyFile(srcFile, destFile);
			log.info(srcFile + " is copy only.");
			return srcImgInfo;
		}


		// オリジナル画像サイズが指定したサムネイル画像サイズより小さい場合、ファイルをそのままコピーする。
		if(destImgInfo.getWidth() > srcImgInfo.getWidth() &&
				destImgInfo.getHeight() > srcImgInfo.getHeight()) {

			FileUtils.copyFile(srcFile, destFile);
			log.info(srcFile + " is copy only.");
			return srcImgInfo;
		}

		
		// ImageMagic の機能を使用してサムネイル画像を作成する。
		execImageMagicConvert(srcFile.getPath(), destFile.getPath(), destImgInfo);
		
		return destImgInfo;

	
	}

	
	
	
	/**
	 * サムネイル画像のファイルサイズ情報を取得取得する。<br/>
	 * <br/>
	 * @param srcFile オリジナル画像のフルパスファイル名
	 * @param size 作成するサムネイルのサイズ
	 * 
	 * @return サムネイル画像のサイズ情報
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	protected ImgInfo createThumImgInfo(ImgInfo srcImgInfo, int size)
			throws FileNotFoundException, IOException {

		// サムネイル画像サイズ情報のオブジェクトを作成する。
		ImgInfo thumImgInfo = new ImgInfo();
		thumImgInfo.setHwFlg(srcImgInfo.getHwFlg());


		// サムネイル画像サイズの算出
		// 縦と横の長さで長い方を基準として縮尺率を算出する。
		if (srcImgInfo.getHwFlg() == 0){
			// 横長画像の場合
			double rate = (double)size / (double)srcImgInfo.getWidth();
			thumImgInfo.setHeight((int)(srcImgInfo.getHeight() * rate));
			thumImgInfo.setWidth(size);

		} else if (srcImgInfo.getHwFlg() == 1){
			// 縦長画像の場合
			double rate = (double)size / (double)srcImgInfo.getHeight();
			thumImgInfo.setHeight(size);
			thumImgInfo.setWidth((int)(srcImgInfo.getWidth() * rate));

		} else {
			// 縦・横の長さが同一の場合
			thumImgInfo.setHeight(size);
			thumImgInfo.setWidth(size);
		}

		return thumImgInfo;
	}



	/**
	 * 実質的なサムネイル作成処理。<br/>
	 * ImageMagick の convert コマンドを使用してサムネイル画像を作成する。<br/>
	 * <br/>
	 * @param srcFile オリジナル画像のフルパスファイル名
	 * @param destFile サムネイルの出力先フルパスファイル名
	 * @param destImgInfo　作成するサムネイルファイルのサイズ情報
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	protected void execImageMagicConvert(String srcFile, String destFile, ImgInfo destImgInfo) throws IOException, InterruptedException {
		
		// ImageMagic 変換コマンドのパラメータ作成
		List<String> cmdList = new ArrayList<>();
		cmdList.add(this.convertCommand);
		cmdList.add("-resize");
		cmdList.add(destImgInfo.getWidth() + "x" + destImgInfo.getHeight() + "!");
		cmdList.add(new File(srcFile).getAbsolutePath());
		cmdList.add(new File(destFile).getAbsolutePath());

        int ret = ExecuteShell.exec(cmdList);
        if (ret != 0){
        	throw new RuntimeException("ImageMagic convert result is " + ret);
        }

	}
}
