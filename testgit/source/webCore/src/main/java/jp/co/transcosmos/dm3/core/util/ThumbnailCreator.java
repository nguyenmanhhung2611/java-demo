package jp.co.transcosmos.dm3.core.util;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.housing.dao.HousingFileNameDAO;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * サムネイル画像作成処理.
 * <p>
 * 指定されたサムネイル作成クラスと、指定された画像サイズでサムネイルを作成する。<br/>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.04.10	新規作成
 * H.Mizuno		2015.06.26	削除パスが公開フォルダと同一だった場合、削除しない様に変更
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class ThumbnailCreator {

	private static final Log log = LogFactory.getLog(ThumbnailCreator.class);

	/** 共通パラメータオブジェクト */
	protected CommonParameters commonParameters;

	/** サムネイル画像作成クラス */
	protected ImgUtils imgUtils;

	/** 画像ファイル名採番用 DAO */
	protected HousingFileNameDAO housingFileNameDAO;
	
	
	/** フルサイズ画像のサブフォルダ名 */
	protected static String FULL_SIZE_DIR = "full";
	/** 空フォルダの削除を行う場合、true を設定する。 */
	protected boolean useEmptyDirDelete = false;


	/**
	 * 物件画像ファイル名を取得する。<br/>
	 * <br/>
	 * @return シーケンスから取得した値を１０桁にゼロ詰した文字列。　拡張子は自身で付ける事。
	 * @throws Exception
	 */
	public String getFIleName() throws Exception {
		return this.housingFileNameDAO.createFileName();
	}



	/**
	 * サムネイル画像を作成する。<br/>
	 * また、オリジナルサイズの画像を、画像サイズの階層が full のフォルダへ配置する。<br/>
	 * thumbnailMap の構造は下記の通り。　ファイル名は元のファイル名が使用される。<br/>
	 * <ul>
	 * <li>Key : サムネイル作成元のファイル名（フルパス）</li>
	 * <li>value : サムネイルの出力先パス（ルート～システム物件番号までのパス。　サイズや、ファイル名は含まない。）</li>
	 * </ul>
	 * @param thumbnailMap 作成するファイルの情報
	 * 
	 * @throws IOException 
	 * @throws Exception 委譲先がスローする任意の例外
	 */
	public void create(Map<String, String> thumbnailMap)
			throws IOException, Exception {

		// 作成するファイル分繰り返す
		for (Entry<String, String> e : thumbnailMap.entrySet()){

			// サムネイル作成元のファイルオブジェクトを作成する。
			File srcFile = new File(e.getKey());

			// サムネイル出力先のルートパス （画像サイズの直前までのフォルダ階層）
			String destRootPath = e.getValue();
			if (!destRootPath.endsWith("/")) destRootPath += "/";

			// オリジナル画像をフルサイズ画像として copy する。
			FileUtils.copyFileToDirectory(srcFile, new File(destRootPath + FULL_SIZE_DIR));

						
			// サイズリストが未設定の場合はサムネイル画像を作成しない。
			if (this.commonParameters.getThumbnailSizes() == null) return;

			// 作成するサムネイルサイズ分繰り返す
			for (Integer size : this.commonParameters.getThumbnailSizes()){

				// 出力先サブフォルダが存在しない場合、フォルダを作成する。
				// createImgFile() は、サブフォルダを作成しないので..。
				File subDir = new File(destRootPath + size.toString());
				if (!subDir.exists()){
					FileUtils.forceMkdir(subDir);
				}

				// サムネイルの出力先はファイルサイズ毎に異なるので、サイズ毎に生成する。
				File destFile = new File(destRootPath + size.toString() + "/" + srcFile.getName());
				// サムネイル画像を作成
				this.imgUtils.createImgFile(srcFile, destFile, size.intValue());
			}
		}
	}



	/**
	 * 画像ファイルをディレクトリ単位で削除する。<br/>
	 * targetRootPath + delImgPath のディレクトリを削除する。<br/>
	 * <br/>
	 * @param targetRootPath 削除対象 Root パス
	 * @param delImgFile 削除対象ディレクトリを格納した Set オブジェクト
	 * 
	 * @throws IOException 
	 */
	public void deleteImageDir(List<String> targetRootPath, Set<String> delImgPath) throws IOException {

		for (String rootPath : targetRootPath){

			if (StringValidateUtil.isEmpty(rootPath)) rootPath = "/";
			if (!rootPath.endsWith("/")) rootPath += "/";

			for (String path : delImgPath){

				// スペースを取り除いておく。
				path = rootPath + path.trim();

				log.info("delete housing image file : " + path);

				// ディレクトリ単位の削除の場合、設定値やデータに不備があると予想外の場所が削除されるリスクがある。
				// リスクを回避する為、加工さえれた削除対象がルートパスで無い事と、空でない事をチェックしておく。
				if (StringValidateUtil.isEmpty(path) || path.equals("/")) continue;

				// ルートパス + 画像パスの結果が、ルートパスと一致した場合、削除を行わない様にする。
				// （万が一のバグに備えた安全装置）
				if (path.equals(rootPath)) continue;

				// 指定されたフォルダ配下を削除
				FileUtils.deleteDirectory(new File(path));
			}

		}
		
	}



	/**
	 * 物件画像ファイルを個別に削除する。<br/>
	 * filePath で指定したフォルダ内のファイルが空の場合、フォルダごと削除する。
	 * <br/>
	 * @param filePath ルート～システム物件CD までのパス（画像サイズの下までのパス）
	 * @param fileName　画像ファイル名
	 * 
	 * @throws IOException 
	 */
	public void deleteImgFile(String filePath, String fileName) throws IOException{
		
		// オリジナル画像の削除
		deleteFile(new File(filePath + FULL_SIZE_DIR + "/" + fileName)); 

		// サムネイルサイズが設定されている場合、サムネイルファイルを削除する。
		if (this.commonParameters.getThumbnailSizes() != null) {
			
			for (Integer size : this.commonParameters.getThumbnailSizes()){
				// サムネイル画像を削除
				deleteFile(new File(filePath + size.toString() + "/" + fileName));
			}
		}


		// note
		// 空フォルダの削除機能を有効にする場合、呼び出し元で同期化する仕組みを提供する必要がある。
		// 同じフォルダ配下にファイルを配置する処理が存在する場合、同期化していないと競合発生時にフォルダ
		// ごと削除されてしまう可能性がある。

		if (useEmptyDirDelete){
			// 指定された filePath 配下が空の場合、フォルダ毎削除する。
			try {
				Collection<File> fileList = FileUtils.listFiles(new File(filePath),null,true);
				if (fileList.size()==0){
					FileUtils.deleteDirectory(new File(filePath));
				}
			
			} catch (IllegalArgumentException e){
				// 削除対象物理フォルダが存在しない場合、IllegalArgumentException が発生する。
				// その場合の例外は無視する。
				log.warn(e.getMessage(), e);
			}
		}
	}

	

	/**
	 * 指定されたファイルを削除する。 <br/>
	 * もし物理ファイルが存在するのに削除できなかった場合、警告をログ出力する。<br/>
	 * <br/>
	 * @param targetFile 削除対象ファイルオブジェクト
	 */
	private void deleteFile(File targetFile){
		boolean ret = targetFile.delete();
		if (!ret && targetFile.exists()) {
			// 実ファイルが存在し、ファイルの削除が失敗した場合、例外をスローする。
			throw new RuntimeException(targetFile.getPath() + " couldn't be deleted.");
		}
	}



	/**
	 * 共通パラメータオブジェクトを設定する。<br/>
	 * <br/>
	 * @param commonParameters 共通パラメータオブジェクト
	 */
	public void setCommonParameters(CommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * サムネイル画像作成クラスを設定する。<br/>
	 * <br/>
	 * @param imgUtils サムネイル画像作成クラス
	 */
	public void setImgUtils(ImgUtils imgUtils) {
		this.imgUtils = imgUtils;
	}

	/**
	 * 画像ファイル名採番用 DAO を設定する。<br/>
	 * <br/>
	 * @param housingFileNameDAO 画像ファイル名採番用 DAO
	 */
	public void setHousingFileNameDAO(HousingFileNameDAO housingFileNameDAO) {
		this.housingFileNameDAO = housingFileNameDAO;
	}

	/**
	 * 削除後、空フォルダになった場合、フォルダごと削除する場合は true を設定する。<br/>
	 * デフォルト は false で無効<br/>
	 * <br/>
	 * @param useEmptyDirDelete 有効にする場合は true 
	 */
	public void setUseEmptyDirDelete(boolean useEmptyDirDelete) {
		this.useEmptyDirDelete = useEmptyDirDelete;
	}

}
