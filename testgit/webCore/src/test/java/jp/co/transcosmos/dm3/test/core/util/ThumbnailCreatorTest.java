package jp.co.transcosmos.dm3.test.core.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.util.ImgUtils;
import jp.co.transcosmos.dm3.core.util.ThumbnailCreator;
import jp.co.transcosmos.dm3.core.util.imgUtils.ImgInfo;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



/**
 * VO インスタンス生成クラスのテストケース<br/>
 * <br/>
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class ThumbnailCreatorTest {

	@Autowired
	private ThumbnailCreator thumbnailCreator;
	@Autowired
	private CommonParameters commonParameters;
	@Autowired
	private ImgUtils imgUtils;



	@Before
	public void init() throws IOException {
		// テストフォルダのリセット
		File srcDir = new File(this.commonParameters.getHousImgTempPhysicalPath());
		File destDir = new File(this.commonParameters.getHousImgOpenPhysicalPath());

		FileUtils.cleanDirectory(srcDir);
		FileUtils.cleanDirectory(destDir);

		// temp フォルダに日付フォルダを作成
		FileUtils.forceMkdir(new File(this.commonParameters.getHousImgTempPhysicalPath() + "20150101"));

	}



	/**
	 * 画像ファイル名の取得テスト<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>ゼロ詰された１０桁の数字文字列が復帰される事。</li>
	 *     <li>２回実行して、前回と違う値が取得できる事。</li>
	 * </ul>
	 */
	@Test
	public void getFIleNameTest() throws Exception{
	
		String str = this.thumbnailCreator.getFIleName();
		System.out.println(str);

		Assert.assertEquals("10桁の文字列である事", 10, str.length());
		
		// 数値変換可能な事をチェック
		Long.valueOf(str);

		
		String str2 = this.thumbnailCreator.getFIleName();
		Assert.assertNotEquals("１回目と２回目で値が異なる事", str, str2);

	}



	/**
	 * サムネイル画像の作成テスト<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>CommonsParameters に設定されているサムネイルサイズ分、ファイルが作成されている事。</li>
	 *     <li>作成されたサムネイルサイズが正しい事</li>
	 *     <li>オリジナル画像が元のサイズのままコピーされている事</li>
	 * </ul>
	 */
	@Test
	public void createTest() throws Exception{

		// 仮フォルダ内にテストデータを配置する。
		String root = (String) System.getProperties().get("user.dir");
		String testDataDir = root + "/junitTest/upload/testData/";

		FileUtils.copyDirectory(new File(testDataDir), new File(this.commonParameters.getHousImgTempPhysicalPath() + "20150101"));
		
		
		String tempRoot = this.commonParameters.getHousImgTempPhysicalPath() + "20150101/";
		String openRoot = this.commonParameters.getHousImgOpenPhysicalPath() + "test/";

		// テスト用 MAP の作成
		Map<String, String> thumbnailMap = new HashMap<>();
		thumbnailMap.put(tempRoot + "0000000001.jpg", openRoot);
		thumbnailMap.put(tempRoot + "0000000002.jpg", openRoot);
		
		
		// テストメソッド実行
		this.thumbnailCreator.create(thumbnailMap);
		
		
		// 実行結果の確認　（縦長画像の場合は縦のサイズ、横長画像の場合は横のサイズでチェック
		ImgInfo info = this.imgUtils.getImgInfo(openRoot + "85/0000000001.jpg");
		Assert.assertEquals("画像サイズが正しい事", Integer.valueOf(85), info.getHeight());
		
		info = this.imgUtils.getImgInfo(openRoot + "85/0000000002.jpg");
		Assert.assertEquals("画像サイズが正しい事", Integer.valueOf(85), info.getWidth());

		info = this.imgUtils.getImgInfo(openRoot + "115/0000000001.jpg");
		Assert.assertEquals("画像サイズが正しい事", Integer.valueOf(115), info.getHeight());
		
		info = this.imgUtils.getImgInfo(openRoot + "115/0000000002.jpg");
		Assert.assertEquals("画像サイズが正しい事", Integer.valueOf(115), info.getWidth());

		info = this.imgUtils.getImgInfo(openRoot + "300/0000000001.jpg");
		Assert.assertEquals("画像サイズが正しい事", Integer.valueOf(300), info.getHeight());
		
		info = this.imgUtils.getImgInfo(openRoot + "300/0000000002.jpg");
		Assert.assertEquals("画像サイズが正しい事", Integer.valueOf(300), info.getWidth());

		info = this.imgUtils.getImgInfo(openRoot + "500/0000000001.jpg");
		Assert.assertEquals("画像サイズが正しい事", Integer.valueOf(500), info.getHeight());
		
		info = this.imgUtils.getImgInfo(openRoot + "500/0000000002.jpg");
		Assert.assertEquals("画像サイズが正しい事", Integer.valueOf(500), info.getWidth());

		
		ImgInfo orgInfo = this.imgUtils.getImgInfo(tempRoot + "0000000001.jpg");
		info = this.imgUtils.getImgInfo(openRoot + "full/0000000001.jpg");
		Assert.assertEquals("画像サイズが正しい事", orgInfo.getHeight(), info.getHeight());
		Assert.assertEquals("画像サイズが正しい事", orgInfo.getWidth(), info.getWidth());

		orgInfo = this.imgUtils.getImgInfo(tempRoot + "0000000002.jpg");
		info = this.imgUtils.getImgInfo(openRoot + "full/0000000002.jpg");
		Assert.assertEquals("画像サイズが正しい事", orgInfo.getHeight(), info.getHeight());
		Assert.assertEquals("画像サイズが正しい事", orgInfo.getWidth(), info.getWidth());

	}
	

	
	/**
	 * フォルダ単位の画像ファイル削除テスト<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>削除対象フォルダが存在しない事</li>
	 *     <li>削除対象外フォルダが存在する事</li>
	 * </ul>
	 * @throws IOException 
	 */
	@Test
	public void deleteImageDirTest() throws IOException{

		// 公開フォルダ内にテストデータを配置する。
		String root = (String) System.getProperties().get("user.dir");
		String testDataDir = root + "/junitTest/upload/testDataDelDir/";

		FileUtils.copyDirectory(new File(testDataDir), new File(this.commonParameters.getHousImgOpenPhysicalPath()));
		
		
		// テストパラメータ作成
		Set<String> dir = new HashSet<>();
		dir.add("111/12/11111/HOUS0001/");
		dir.add("112/12/11111/HOUS0001/");

		List<String> rootPathList = new ArrayList<>(); 
		rootPathList.add(this.commonParameters.getHousImgOpenPhysicalPath());
		rootPathList.add(this.commonParameters.getHousImgOpenPhysicalPath()+"2/");		// 存在しないダミーフォルダ
		this.thumbnailCreator.deleteImageDir(rootPathList, dir);


		// 結果確認
		
		// 削除対象フォルダが消されている事
		try {
			FileUtils.listFiles(new File(this.commonParameters.getHousImgOpenPhysicalPath() + "111/12/11111/HOUS0001"), null, true);
			Assert.fail("削除対象フォルダが存在しない事");
		} catch (IllegalArgumentException e) { }

		try {
			FileUtils.listFiles(new File(this.commonParameters.getHousImgOpenPhysicalPath() + "112/12/11111/HOUS0001"), null, true);
			Assert.fail("削除対象フォルダが存在しない事");
		} catch (IllegalArgumentException e) { }


		// 削除対象外フォルダが存在する事
		Collection<File> lst = FileUtils.listFiles(new File(this.commonParameters.getHousImgOpenPhysicalPath() + "111/12/11111/HOUS0002"), null, true);
		Assert.assertNotEquals("削除対象外フォルダが消されていない事", 0, lst.size());
		
		lst = FileUtils.listFiles(new File(this.commonParameters.getHousImgOpenPhysicalPath() + "111/12/11111/HOUS0003"), null, true);
		Assert.assertNotEquals("削除対象外フォルダが消されていない事", 0, lst.size());

		lst = FileUtils.listFiles(new File(this.commonParameters.getHousImgOpenPhysicalPath() + "112/12/11111/HOUS0002"), null, true);
		Assert.assertNotEquals("削除対象外フォルダが消されていない事", 0, lst.size());

		lst = FileUtils.listFiles(new File(this.commonParameters.getHousImgOpenPhysicalPath() + "112/12/11111/HOUS0003"), null, true);
		Assert.assertNotEquals("削除対象外フォルダが消されていない事", 0, lst.size());

	}



	/**
	 * フォルダ単位の画像ファイル削除テスト（物理ファイル、フォルダが存在しない場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>例外が発生しない事</li>
	 * </ul>
	 * @throws IOException 
	 */
	@Test
	public void deleteImageNotDirTest() throws IOException{

		// 公開フォルダ内に何も配置せずに実行する
		
		// テストパラメータ作成
		Set<String> dir = new HashSet<>();
		dir.add("111/12/11111/HOUS0001/");
		dir.add("112/12/11111/HOUS0001/");

		List<String> rootPathList = new ArrayList<>();
		rootPathList.add(this.commonParameters.getHousImgOpenPhysicalPath());
		rootPathList.add(this.commonParameters.getHousImgOpenPhysicalPath() +"2/");		// 存在しないダミーフォルダ
		this.thumbnailCreator.deleteImageDir(rootPathList, dir);
		
	}


	
	/**
	 * フォルダ単位の画像ファイル削除の安全装置挙動確認<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>個別パスフォルダが空の場合、ルートフォルダが削除されない事を確認</li>
	 * </ul>
	 * @throws IOException 
	 */
	@Test
	public void deleteImageSaftyTest() throws IOException{

		// 公開フォルダ内にテストデータを配置する。
		String root = (String) System.getProperties().get("user.dir");
		String testDataDir = root + "/junitTest/upload/testDataDelDir/";

		FileUtils.copyDirectory(new File(testDataDir), new File(this.commonParameters.getHousImgOpenPhysicalPath()));


		// テストパラメータ作成
		// ターゲットパスを空文字列に設定し、RootPathList のフォルダが削除されない事を確認。
		List<String> rootPathList = new ArrayList<>();
		String wk = this.commonParameters.getHousImgOpenPhysicalPath();
		if (!wk.endsWith("/")) wk += "/";
		rootPathList.add( wk + "111/12/11111/HOUS0001/");

		Set<String> dir = new HashSet<>();
		dir.add("");

		
		// テスト対象実行
		this.thumbnailCreator.deleteImageDir(rootPathList, dir);

		
		// 結果確認
		Assert.assertTrue("ルートフォルダが削除されていない事", (new File(wk + "111/12/11111/HOUS0001/")).exists());
	}

	

	/**
	 * ファイル単位の画像ファイル削除テスト<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>指定された画像の全サムネイルファイルが削除されている事</li>
	 *     <li>指定されていない画像ファイルが削除されていない事</li>
	 * </ul>
	 * @throws IOException 
	 */
	@Test
	public void deleteImageFileTest() throws IOException{

		// 公開フォルダ内にテストデータを配置する。
		String root = (String) System.getProperties().get("user.dir");
		String testDataDir = root + "/junitTest/upload/testDataDelDir/";

		FileUtils.copyDirectory(new File(testDataDir), new File(this.commonParameters.getHousImgOpenPhysicalPath()));


		// テストパラメータ作成
		String path = this.commonParameters.getHousImgOpenPhysicalPath() + "111/12/11111/HOUS0001/";

		this.thumbnailCreator.deleteImgFile(path, "0000000001.jpg");


		// 実行結果確認
		Assert.assertFalse("削除対象ファイルが存在しない事", (new File (path + "85/0000000001.jpg")).exists());
		Assert.assertFalse("削除対象ファイルが存在しない事", (new File (path + "115/0000000001.jpg")).exists());
		Assert.assertFalse("削除対象ファイルが存在しない事", (new File (path + "300/0000000001.jpg")).exists());
		Assert.assertFalse("削除対象ファイルが存在しない事", (new File (path + "500/0000000001.jpg")).exists());
		
		Assert.assertTrue("削除対象外ファイルが存在する事", (new File (path + "85/0000000002.jpg")).exists());
		Assert.assertTrue("削除対象外ファイルが存在する事", (new File (path + "115/0000000002.jpg")).exists());
		Assert.assertTrue("削除対象外ファイルが存在する事", (new File (path + "300/0000000002.jpg")).exists());
		Assert.assertTrue("削除対象外ファイルが存在する事", (new File (path + "500/0000000002.jpg")).exists());

	}


	
	/**
	 * ファイル単位の画像ファイル削除テスト（削除後、フォルダ内が空となり、空フォルダ削除機能が無効な場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>空フォルダが削除されていない事</li>
	 * </ul>
	 * @throws IOException 
	 */
	@Test
	public void deleteImageFileEmptyTest1() throws IOException{

		// 公開フォルダ内にテストデータを配置する。
		String root = (String) System.getProperties().get("user.dir");
		String testDataDir = root + "/junitTest/upload/testDataDelDir/";

		FileUtils.copyDirectory(new File(testDataDir), new File(this.commonParameters.getHousImgOpenPhysicalPath()));


		// テストパラメータ作成
		String path = this.commonParameters.getHousImgOpenPhysicalPath() + "111/12/11111/HOUS0001/";

		this.thumbnailCreator.deleteImgFile(path, "0000000001.jpg");


		// 実行結果確認
		Assert.assertFalse("削除対象ファイルが存在しない事", (new File (path + "85//0000000001.jpg")).exists());
		Assert.assertFalse("削除対象ファイルが存在しない事", (new File (path + "115/0000000001.jpg")).exists());
		Assert.assertFalse("削除対象ファイルが存在しない事", (new File (path + "300/0000000001.jpg")).exists());
		Assert.assertFalse("削除対象ファイルが存在しない事", (new File (path + "500/0000000001.jpg")).exists());
		
		Assert.assertTrue("削除対象外ファイルが存在する事", (new File (path + "85/0000000002.jpg")).exists());
		Assert.assertTrue("削除対象外ファイルが存在する事", (new File (path + "115/0000000002.jpg")).exists());
		Assert.assertTrue("削除対象外ファイルが存在する事", (new File (path + "300/0000000002.jpg")).exists());
		Assert.assertTrue("削除対象外ファイルが存在する事", (new File (path + "500/0000000002.jpg")).exists());

		
		// ２個目のフォルダを削除
		this.thumbnailCreator.deleteImgFile(path, "0000000002.jpg");
		
		// サブフォルダが存在する事
		Assert.assertTrue("サブフォルダが存在する事", (new File(path)).exists());

	}


	
	/**
	 * ファイル単位の画像ファイル削除テスト（削除後、フォルダ内が空となり、空フォルダ削除機能が有効な場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>空フォルダが削除されている事</li>
	 * </ul>
	 * @throws IOException 
	 */
	@Test
	public void deleteImageFileEmptyTest2() throws IOException{

		// 公開フォルダ内にテストデータを配置する。
		String root = (String) System.getProperties().get("user.dir");
		String testDataDir = root + "/junitTest/upload/testDataDelDir/";

		FileUtils.copyDirectory(new File(testDataDir), new File(this.commonParameters.getHousImgOpenPhysicalPath()));


		// テストパラメータ作成
		String path = this.commonParameters.getHousImgOpenPhysicalPath() + "111/12/11111/HOUS0001/";

		this.thumbnailCreator.setUseEmptyDirDelete(true);
		this.thumbnailCreator.deleteImgFile(path, "0000000001.jpg");


		// 実行結果確認
		Assert.assertFalse("削除対象ファイルが存在しない事", (new File (path + "85//0000000001.jpg")).exists());
		Assert.assertFalse("削除対象ファイルが存在しない事", (new File (path + "115/0000000001.jpg")).exists());
		Assert.assertFalse("削除対象ファイルが存在しない事", (new File (path + "300/0000000001.jpg")).exists());
		Assert.assertFalse("削除対象ファイルが存在しない事", (new File (path + "500/0000000001.jpg")).exists());
		
		Assert.assertTrue("削除対象外ファイルが存在する事", (new File (path + "85/0000000002.jpg")).exists());
		Assert.assertTrue("削除対象外ファイルが存在する事", (new File (path + "115/0000000002.jpg")).exists());
		Assert.assertTrue("削除対象外ファイルが存在する事", (new File (path + "300/0000000002.jpg")).exists());
		Assert.assertTrue("削除対象外ファイルが存在する事", (new File (path + "500/0000000002.jpg")).exists());

		
		// ２個目のフォルダを削除
		this.thumbnailCreator.deleteImgFile(path, "0000000002.jpg");
		
		// サブフォルダが存在する事
		Assert.assertFalse("サブフォルダが存在しない事", (new File(path)).exists());

	}

	
	
	/**
	 * ファイル単位の画像ファイル削除テスト<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>物理ファイルが存在しない場合、例外が発生しない事</li>
	 *     <li>物理パスが存在しない場合、例外が発生しない事</li>
	 * </ul>
	 * @throws IOException 
	 */
	@Test
	public void deleteImageNonFileTest() throws IOException{

		// 公開フォルダ内にテストデータを配置する。
		String root = (String) System.getProperties().get("user.dir");
		String testDataDir = root + "/junitTest/upload/testDataDelDir/";

		FileUtils.copyDirectory(new File(testDataDir), new File(this.commonParameters.getHousImgOpenPhysicalPath()));


		// 存在しないファイル名で実行
		String path = this.commonParameters.getHousImgOpenPhysicalPath() + "111/12/11111/HOUS0001/";
		this.thumbnailCreator.deleteImgFile(path, "1000000005.jpg");

		
		// 存在しないパス名で実行
		path = this.commonParameters.getHousImgOpenPhysicalPath() + "511/12/11111/HOUS0001/";
		this.thumbnailCreator.deleteImgFile(path, "0000000001.jpg");
		
	}

}
