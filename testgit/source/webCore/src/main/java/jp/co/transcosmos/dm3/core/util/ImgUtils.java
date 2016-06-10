package jp.co.transcosmos.dm3.core.util;

import java.io.File;

import jp.co.transcosmos.dm3.core.util.imgUtils.ImgInfo;


/**
 * サムネイル画像作成クラスのインターフェース.
 * <p>
 * サムネイル画像を作成するクラスはこのインタフェースを実装する事。<br/>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.23	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public interface ImgUtils {

	/**
	 * 指定された画像ファイルの画像情報を取得する。<br/>
	 * <br/>
	 * @param srcFile 情報取得対象画像のフルパス
	 * @return　画像情報
	 * 
	 * @throws Exception 実装クラスがスローする例外
	 */
	public ImgInfo getImgInfo(String srcFile) throws Exception;
	
	/**
	 * 指定された画像ファイルの画像情報を取得する。<br/>
	 * <br/>
	 * @param srcFile 情報取得対象画像の File オブジェクト
	 * @return　画像情報
	 * 
	 * @throws Exception 実装クラスがスローする例外
	 */
	public ImgInfo getImgInfo(File srcFile) throws Exception;

	
	
	/**
	 * サムネイル画像を作成する。<br/>
	 * <br/>
	 * @param srcFile　オリジナル画像のフルパス
	 * @param destFile 出力するサムネイル画像の出力先フルパス
	 * @param size サムネイル画像のサイズ
	 * @return 作成したサムネイル画像の情報
	 * 
	 * @throws Exception 実装クラスがスローする例外
	 */
	public ImgInfo createImgFile(String srcFile, String destFile, int size) throws Exception;

	/**
	 * サムネイル画像を作成する。<br/>
	 * <br/>
	 * @param srcFile　オリジナル画像の File オブジェクト
	 * @param destFile 出力するサムネイル画像の出力先 File オブジェクト
	 * @param size サムネイル画像のサイズ
	 * @return 作成したサムネイル画像の情報
	 * 
	 * @throws Exception 実装クラスがスローする例外
	 */
	public ImgInfo createImgFile(File srcFile, File destFile, int size) throws Exception;

}
