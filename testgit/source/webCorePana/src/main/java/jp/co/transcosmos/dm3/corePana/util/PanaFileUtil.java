package jp.co.transcosmos.dm3.corePana.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;

import org.apache.commons.fileupload.FileItem;
import org.springframework.util.StringUtils;

/**
 * Panasonic用ファイル処理関連共通Util.
 *
 * <pre>
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 *   Trans	  2015.03.10    新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class PanaFileUtil {
	/** 共通パラメータオブジェクト */
	private PanaCommonParameters commonParameters;

	/**
	 * 共通パラメータオブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param commonParameters
	 *            共通パラメータオブジェクト
	 */
	public void setCommonParameters(PanaCommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * Tempパスを復帰する。<br/>
	 * <br/>
	 *
	 * @param rootPath
	 *            ルートパス「定数値」
	 * @return Tempパス（/「定数値」/「年月日」/）を返す
	 */
	public static String getUploadTempPath() {
		// 「年月日」
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

		// 「年月日」を返す
		return dateFormat.format(new Date());
	}

	/**
	 * 物理パス パラメータの区切りをシステム区切りに置き換えるメソッド.<br>
	 * <br>
	 *
	 * @param path
	 *            物理パス
	 * @return 置き換えた物理パス
	 */
	public static String replaceToFileSeparator(String path) {
		if (StringUtils.isEmpty(path)) {
			return "";
		}

		// パス区切りを置き換える
		return path.trim().replace("/", File.separator)
				.replace("\\", File.separator);
	}

	/**
	 * 物理パス パラメータをURLパス（画面表示用）に置き換えるメソッド.<br>
	 * <br>
	 *
	 * @param path
	 *            物理パス
	 * @return URLパス
	 */
	public static String replaceToURLSeparator(String path) {
		if (StringUtils.isEmpty(path)) {
			return "";
		}

		// パス区切りを置き換える
		return path.replace("\\", "/");
	}

	/**
	 * パスを連結するメソッド.<br>
	 * 連結後後物理パスは存在しない場合、作成する. <br>
	 *
	 * @param rootPath
	 *            連結元パス
	 * @param path
	 *            連結先パス
	 * @return 連結後パス
	 */
	public static String mkPhysicalPath(String rootPath, String path) {
		return conPhysicalPath(rootPath, path, 0);
	}

	/**
	 * パスを連結するメソッド.<br>
	 * 連結後後物理パスは存在しない場合、作成もしない. <br>
	 *
	 * @param rootPath
	 *            連結元パス
	 * @param path
	 *            連結先パス
	 * @return 連結後パス
	 */
	public static String conPhysicalPath(String rootPath, String path) {
		return conPhysicalPath(rootPath, path, 1);
	}

	/**
	 * 連結元パスに連結先パスを追加するメソッド.<br>
	 * <br>
	 *
	 * @param rootPath
	 *            連結元パス
	 * @param path
	 *            連結先パス
	 * @param type
	 *            0: 連結後パスは存在しない場合、作成する 1:
	 *            連結後パスは存在しない場合、作成しない（普段は連結先パスがファイルの場合利用）
	 * @return 連結後パス
	 */
	public static String conPhysicalPath(String rootPath, String path, int type) {
		// 連結元パスがNullの場合、初期化する
		if (rootPath == null) {
			rootPath = "";
		}

		if (path == null) {
			path = "";
		}
		path = path.trim();

		rootPath = replaceToFileSeparator(rootPath);

		StringBuffer strB = new StringBuffer(rootPath);

		// 連結元パスの最後が区切り文字でない場合、区切り文字を追加
		if (!rootPath.endsWith(File.separator)) {
			strB.append(File.separator);
		}

		// 連結先パスの先頭が区切り文字である場合、区切り文字を削除
		if (path.startsWith(File.separator)) {
			path = path.substring(1, path.length());
		}

		// 連結元パス + 連結先パス
		strB.append(path);

		// 連結後パスは存在しない場合、作成する
		if (type != 1) {
			File file = new File(strB.toString());

			if (!file.exists()) {
				// 存在しない場合、作成する
				file.mkdirs();
			}
		}

		return strB.toString();
	}

	/**
	 * ファイルをアップロードする用の共通処理.<br>
	 * <br>
	 *
	 * @param item
	 *            アップロード対象ファイルアイテム.
	 * @param uploadPath
	 *            アップロードパス. 例）/「定数値」/[物件種別CD]/[都道府県CD]/[市区町村CD]/システム物件番号/
	 * @param delFlg
	 *            アップロード先削除フラグ<br>
	 *            （1:アップロード先に同名ファイルがある場合該当ファイルを削除する）
	 * @return アップロードファイル名
	 */
	public static String uploadFile(FileItem item, String uploadPath,
			String fileName) throws Exception {
		return uploadFile(item, uploadPath, fileName, 1);
	}

	/**
	 * ファイルをアップロードする用の共通処理.<br>
	 * <br>
	 *
	 * @param item
	 *            アップロード対象ファイルアイテム.
	 * @param uploadPath
	 *            アップロードパス. 例）/「定数値」/[物件種別CD]/[都道府県CD]/[市区町村CD]/システム物件番号/
	 * @param delFlg
	 *            アップロード先削除フラグ<br>
	 *            （1:アップロード先に同名ファイルがある場合該当ファイルを削除する）
	 * @return アップロードファイル名
	 */
	public static String uploadFile(FileItem item, String uploadPath,
			String fileName, int delFlg) throws Exception {
		// オブジェクトが存在しない場合、何もしない.
		if (item == null) {
			return "";
		}

		// ファイル名が無い場合も何もしない.
		if (StringUtils.isEmpty(item.getName())) {
			return "";
		}

		File file = new File(uploadPath);
		if (!file.exists()) {
			// 物理パスが存在しない場合、作成する
			file.mkdirs();
		}

		// ファイル出力
		File uploadFile = new File(uploadPath, fileName);
		if (delFlg == 1 && uploadFile.exists()) {
			uploadFile.delete();
		}

		item.write(uploadFile);

		return uploadFile.getName();
	}

	/**
	 * ファイルを削除するメソッド.<br>
	 * サムネイルも削除するため、同フォルダーの同じファイル名のファイルをすべて削除する.<br>
	 * <br>
	 *
	 * @param path
	 *            削除対象ファイルのパス.
	 *            例）/「定数値」/[物件種別CD]/[都道府県CD]/[市区町村CD]/システム物件番号/シーケンス(10桁).jpg
	 * @return アップロードファイル名
	 */
	public static void delPhysicalPathFile(String path, String fileName) {
		File pPath = new File(path);
		List<String> fileList = new ArrayList<String>();

		if (pPath.exists()) {
			// ファイル格納パス下のファイルをすべて取得
			getFileList(pPath.getAbsolutePath(), pPath.list(), fileList);

			// 削除対象ファイルと同じファイル名は、すべて削除（サムネイルも削除するため）
			for (String fName : fileList) {
				if (fName.indexOf(fileName) > 0) {
					File delFile = new File(fName);

					if (delFile.exists()) {
						delFile.delete();
					}
				}
			}
		}
	}

	/**
	 * パス パラメータのフォルダー下のファイル名をすべて取得するメソッド.<br>
	 * <br>
	 *
	 * @param path
	 *            対象パス.
	 * @param files
	 *            pash下のファイル名とフォルダー名リスト.
	 * @param fileList
	 *            結果のファイル名リスト.
	 */
	public static void getFileList(String path, String[] files,
			List<String> fileList) {
		for (int i = 0; i < files.length; i++) {
			File readfile = new File(path + File.separator + files[i]);
			if (readfile.isDirectory()) {
				getFileList(readfile.getAbsolutePath(), readfile.list(),
						fileList);
			} else {
				fileList.add(readfile.getAbsolutePath());
			}
		}
	}

	/**
	 * パス パラメータとファイル名 パラメータにより、TempファイルのURLパスを取得するメソッド.<br>
	 * リフォーム関連ファイル用.<br>
	 *
	 * @param temPathName
	 *            パス.
	 * @param temFileName
	 *            ファイル名.
	 * @return URLパス
	 */
	public String getHousFileTempUrl(String temPathName, String temFileName) {
		// "http://fileserver:8080"
		StringBuffer path = new StringBuffer(
				this.commonParameters.getFileSiteURL());

		// "http://fileserver:8080/reform/temp/"
		addUrlParam(path, this.commonParameters.getHousImgTempUrl());

		// "http://fileserver:8080/reform/temp/yyyymmdd/"
		addUrlParam(path, temPathName);

		// "http://fileserver:8080/reform/temp/yyyymmdd/abc.jpg"
		addUrlParam(path, temFileName);

		return path.toString();
	}

	/**
	 * パス パラメータとファイル名 パラメータにより、全員閲覧可のURLパスを取得するメソッド.<br>
	 * リフォーム関連ファイル用.<br>
	 *
	 * @param pathName
	 *            パス.
	 * @param fileName
	 *            ファイル名.
	 * @param size
	 *            イメージサイズのサブフォルダー名（PDFも渡せる）.
	 * @return URLパス
	 */
	public String getHousFileOpenUrl(String pathName, String fileName,
			String size) {
		// "http://fileserver:8080"
		StringBuffer path = new StringBuffer(
				this.commonParameters.getFileSiteURL());

		// "http://fileserver:8080/reform/open/"
		addUrlParam(path, this.commonParameters.getHousImgOpenUrl());

		// "http://fileserver:8080/reform/open/123/12/12345/H1234/"
		addUrlParam(path, pathName);

		// "http://fileserver:8080/reform/open/123/12/12345/H1234/full/"
		addUrlParam(path, size);

		// "http://fileserver:8080/reform/open/123/12/12345/H1234/full/abc.jpg"
		addUrlParam(path, fileName);

		return path.toString();
	}

	/**
	 * パス パラメータとファイル名 パラメータにより、会員のみ閲覧可のURLパスを取得するメソッド.<br>
	 * リフォーム関連ファイル用.<br>
	 *
	 * @param pathName
	 *            パス.
	 * @param fileName
	 *            ファイル名.
	 * @param size
	 *            イメージサイズのサブフォルダー名（PDFも渡せる）.
	 * @return URLパス
	 */
	public String getHousFileMemberUrl(String pathName, String fileName,
			String size) {
		// "http://fileserver:8080"
		StringBuffer path = new StringBuffer(
				this.commonParameters.getFileSiteURL());

		// "http://fileserver:8080/reform/open_member/"
		addUrlParam(path, this.commonParameters.getHousImgMemberUrl());

		// "http://fileserver:8080/reform/open/123/12/12345/H1234/"
		addUrlParam(path, pathName);

		// "http://fileserver:8080/reform/open/123/12/12345/H1234/full/"
		addUrlParam(path, size);

		// "http://fileserver:8080/reform/open/123/12/12345/H1234/full/abc.jpg"
		addUrlParam(path, fileName);

		return path.toString();
	}

	public static void addUrlParam(StringBuffer path, String param) {
		if (path == null) {
			return;
		}

		if (param == null || param.length() == 0) {
			return;
		}

		if (path.length() == 0) {
			if ("/".equals(param.substring(0, 1))) {
				path.append(param);
			} else {
				path.append("/").append(param);
			}

			return;
		}

		if (!"/".equals(path.substring(path.length() - 1))) {
			path.append("/");
		}

		if ("/".equals(param.substring(0, 1))) {
			path.append(param.substring(1));
		} else {
			path.append(param);
		}
	}
}
