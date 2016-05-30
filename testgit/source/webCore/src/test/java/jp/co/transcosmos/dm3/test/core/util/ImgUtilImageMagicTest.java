package jp.co.transcosmos.dm3.test.core.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jp.co.transcosmos.dm3.core.util.ImgUtils;
import jp.co.transcosmos.dm3.core.util.imgUtils.ImgInfo;


/**
 * ImageMagick によるサムネイル画像作成クラスのテスト<br/>
 * <br/>
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class ImgUtilImageMagicTest {

	
	@Autowired
	ImgUtils imgUtils;

	

	@Before
	public void init() throws IOException {
		// サムネイルの作成先ディレクトリを消去
		String rootPath = System.getProperty("user.dir");
		File destDir = new File(rootPath + "/junitTest/thumDstData");
		FileUtils.deleteDirectory(destDir);
		FileUtils.forceMkdir(destDir);
	}
	
	
	

	/**
	 * 正方形画像のサムネイル作成（サムネイルの方が小さい場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>サムネイル画像が指定されたディレクトリに作成されている事。</li>
	 *     <li>サムネイル画像の縦サイズが正しい値に縮小されている事</li>
	 *     <li>サムネイル画像の横サイズが正しい値に縮小されている事</li>
	 *     <li>サムネイル画像の縦長・横長フラグが 2（同一）である事</li>
	 * </ul>
	 */
	@Test
	public void sqSmallTest() throws Exception{
		
		String rootPath = System.getProperty("user.dir");
		System.out.print(rootPath);

		// コマンド実行
		this.imgUtils.createImgFile(rootPath + "/junitTest/thumSrcData/test600.jpg",
									rootPath + "/junitTest/thumDstData/test100.jpg",
									100);

		// 画像ファイルのチェック
		ImgInfo imgInfo = this.imgUtils.getImgInfo(rootPath + "/junitTest/thumDstData/test100.jpg");
		Assert.assertEquals("縦のサイズが正しい事", 100, (int)imgInfo.getHeight());
		Assert.assertEquals("横のサイズが正しい事", 100, (int)imgInfo.getWidth());
		Assert.assertEquals("縦長・横長フラグが 2 （同一）である事", 2, (int)imgInfo.getHwFlg());

	}
	

	
	/**
	 * 正方形画像のサムネイル作成（サムネイルの方が大きい場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>サムネイル画像が指定されたディレクトリに作成されている事。</li>
	 *     <li>サムネイル画像の縦サイズが元のサイズと同じ事</li>
	 *     <li>サムネイル画像の横サイズが元のサイズと同じ事</li>
	 *     <li>サムネイル画像の縦長・横長フラグが 2（同一）である事</li>
	 * </ul>
	 */
	@Test
	public void sqBigTest() throws Exception{
		
		String rootPath = System.getProperty("user.dir");
		System.out.print(rootPath);

		// コマンド実行
		this.imgUtils.createImgFile(rootPath + "/junitTest/thumSrcData/test600.jpg",
									rootPath + "/junitTest/thumDstData/test700.jpg",
									700);

		// 画像ファイルのチェック
		ImgInfo imgInfo = this.imgUtils.getImgInfo(rootPath + "/junitTest/thumDstData/test700.jpg");
		Assert.assertEquals("縦のサイズが元のサイズである事", 600, (int)imgInfo.getHeight());
		Assert.assertEquals("横のサイズが元のサイズである事", 600, (int)imgInfo.getWidth());
		Assert.assertEquals("縦長・横長フラグが 2 （同一）である事", 2, (int)imgInfo.getHwFlg());

	}

	
	
	/**
	 * 横長画像のサムネイル作成（サムネイルの方が小さい場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>サムネイル画像が指定されたディレクトリに作成されている事。</li>
	 *     <li>サムネイル画像の縦サイズが正しい値に縮小されている事</li>
	 *     <li>サムネイル画像の横サイズが正しい値に縮小されている事</li>
	 *     <li>サムネイル画像の縦長・横長フラグが 0（横長）である事</li>
	 * </ul>
	 */
	@Test
	public void wiSmallTest() throws Exception{
		
		String rootPath = System.getProperty("user.dir");
		System.out.print(rootPath);

		// コマンド実行 （縦 400、横 600）
		this.imgUtils.createImgFile(rootPath + "/junitTest/thumSrcData/wtest600.jpg",
									rootPath + "/junitTest/thumDstData/wtest100.jpg",
									100);

		// 画像ファイルのチェック
		ImgInfo imgInfo = this.imgUtils.getImgInfo(rootPath + "/junitTest/thumDstData/wtest100.jpg");
		Assert.assertEquals("縦のサイズが正しい事", 66, (int)imgInfo.getHeight());
		Assert.assertEquals("横のサイズが正しい事", 100, (int)imgInfo.getWidth());
		Assert.assertEquals("縦長・横長フラグが 0 （横長）である事", 0, (int)imgInfo.getHwFlg());

	}

	
	
	/**
	 * 横長画像のサムネイル作成（サムネイルの方が大きい場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>サムネイル画像が指定されたディレクトリに作成されている事。</li>
	 *     <li>サムネイル画像の縦サイズが元のサイズと同じ事</li>
	 *     <li>サムネイル画像の横サイズが元のサイズと同じ事</li>
	 *     <li>サムネイル画像の縦長・横長フラグが 0（横長）である事</li>
	 * </ul>
	 */
	@Test
	public void wiBigTest() throws Exception{
		
		String rootPath = System.getProperty("user.dir");
		System.out.print(rootPath);

		// コマンド実行 （縦 400、横 600）
		this.imgUtils.createImgFile(rootPath + "/junitTest/thumSrcData/wtest600.jpg",
									rootPath + "/junitTest/thumDstData/wtest700.jpg",
									700);

		// 画像ファイルのチェック
		ImgInfo imgInfo = this.imgUtils.getImgInfo(rootPath + "/junitTest/thumDstData/wtest700.jpg");
		Assert.assertEquals("縦のサイズが元のサイズである事", 400, (int)imgInfo.getHeight());
		Assert.assertEquals("横のサイズが元のサイズである事", 600, (int)imgInfo.getWidth());
		Assert.assertEquals("縦長・横長フラグが 0 （横長）である事", 0, (int)imgInfo.getHwFlg());

	}

	
	
	/**
	 * 縦長画像のサムネイル作成（サムネイルの方が小さい場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>サムネイル画像が指定されたディレクトリに作成されている事。</li>
	 *     <li>サムネイル画像の縦サイズが正しい値に縮小されている事</li>
	 *     <li>サムネイル画像の横サイズが正しい値に縮小されている事</li>
	 *     <li>サムネイル画像の縦長・横長フラグが 1（縦長）である事</li>
	 * </ul>
	 */
	@Test
	public void heSmallTest() throws Exception{
		
		String rootPath = System.getProperty("user.dir");
		System.out.print(rootPath);

		// コマンド実行 （縦 600、横 400）
		this.imgUtils.createImgFile(rootPath + "/junitTest/thumSrcData/htest600.jpg",
									rootPath + "/junitTest/thumDstData/htest100.jpg",
									100);

		// 画像ファイルのチェック
		ImgInfo imgInfo = this.imgUtils.getImgInfo(rootPath + "/junitTest/thumDstData/htest100.jpg");
		Assert.assertEquals("縦のサイズが正しい事", 100, (int)imgInfo.getHeight());
		Assert.assertEquals("横のサイズが正しい事", 66, (int)imgInfo.getWidth());
		Assert.assertEquals("縦長・横長フラグが 1 （縦長）である事", 1, (int)imgInfo.getHwFlg());

	}

	
	
	/**
	 * 縦長画像のサムネイル作成（サムネイルの方が大きい場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>サムネイル画像が指定されたディレクトリに作成されている事。</li>
	 *     <li>サムネイル画像の縦サイズが元のサイズと同じ事</li>
	 *     <li>サムネイル画像の横サイズが元のサイズと同じ事</li>
	 *     <li>サムネイル画像の縦長・横長フラグが 1（縦長）である事</li>
	 * </ul>
	 */
	@Test
	public void heBigTest() throws Exception{
		
		String rootPath = System.getProperty("user.dir");
		System.out.print(rootPath);

		// コマンド実行 （縦 600、横 400）
		this.imgUtils.createImgFile(rootPath + "/junitTest/thumSrcData/htest600.jpg",
									rootPath + "/junitTest/thumDstData/htest700.jpg",
									700);

		// 画像ファイルのチェック
		ImgInfo imgInfo = this.imgUtils.getImgInfo(rootPath + "/junitTest/thumDstData/htest700.jpg");
		Assert.assertEquals("縦のサイズが元のサイズである事", 600, (int)imgInfo.getHeight());
		Assert.assertEquals("横のサイズが元のサイズである事", 400, (int)imgInfo.getWidth());
		Assert.assertEquals("縦長・横長フラグが 1 （縦長）である事", 1, (int)imgInfo.getHwFlg());

	}

	
	
	/**
	 * 横長画像のサムネイル作成（幅がサムネイルサイズと一致する場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>サムネイル画像が指定されたディレクトリに作成されている事。</li>
	 *     <li>サムネイル画像の縦サイズが元のサイズと同じ事</li>
	 *     <li>サムネイル画像の横サイズが元のサイズと同じ事</li>
	 *     <li>サムネイル画像の縦長・横長フラグが 0（横長）である事</li>
	 * </ul>
	 */
	@Test
	public void wiEqTest() throws Exception{
		
		String rootPath = System.getProperty("user.dir");
		System.out.print(rootPath);

		// コマンド実行 （縦 400、横 600）
		this.imgUtils.createImgFile(rootPath + "/junitTest/thumSrcData/wtest600.jpg",
									rootPath + "/junitTest/thumDstData/wtest700.jpg",
									600);

		// 画像ファイルのチェック
		ImgInfo imgInfo = this.imgUtils.getImgInfo(rootPath + "/junitTest/thumDstData/wtest700.jpg");
		Assert.assertEquals("縦のサイズが元のサイズである事", 400, (int)imgInfo.getHeight());
		Assert.assertEquals("横のサイズが元のサイズである事", 600, (int)imgInfo.getWidth());
		Assert.assertEquals("縦長・横長フラグが 0 （横長）である事", 0, (int)imgInfo.getHwFlg());

	}

	
	/**
	 * 縦長画像のサムネイル作成（高さがサムネイルサイズと一致する場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>サムネイル画像が指定されたディレクトリに作成されている事。</li>
	 *     <li>サムネイル画像の縦サイズが元のサイズと同じ事</li>
	 *     <li>サムネイル画像の横サイズが元のサイズと同じ事</li>
	 *     <li>サムネイル画像の縦長・横長フラグが 1（縦長）である事</li>
	 * </ul>
	 */
	@Test
	public void heEqTest() throws Exception{
		
		String rootPath = System.getProperty("user.dir");
		System.out.print(rootPath);

		// コマンド実行 （縦 600、横 400）
		this.imgUtils.createImgFile(rootPath + "/junitTest/thumSrcData/htest600.jpg",
									rootPath + "/junitTest/thumDstData/htest700.jpg",
									600);

		// 画像ファイルのチェック
		ImgInfo imgInfo = this.imgUtils.getImgInfo(rootPath + "/junitTest/thumDstData/htest700.jpg");
		Assert.assertEquals("縦のサイズが元のサイズである事", 600, (int)imgInfo.getHeight());
		Assert.assertEquals("横のサイズが元のサイズである事", 400, (int)imgInfo.getWidth());
		Assert.assertEquals("縦長・横長フラグが 1 （縦長）である事", 1, (int)imgInfo.getHwFlg());

	}

}
