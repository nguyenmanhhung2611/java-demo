package jp.co.transcosmos.dm3.core.vo;


/**
 * 物件画像情報.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 *
 * <pre>
 * 担当者        修正日        修正内容
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.03.10  新規作成
 * H.Mizuno		2015.03.16	枝番のデータ型を String から Integer へ変更
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 *
 */
public class HousingImageInfo {

	/** システム物件CD */
	private String sysHousingCd;
	/** 画像タイプ */
	private String imageType;
	/** 枝番 */
	private Integer divNo;
	/** 表示順 */
	private Integer sortOrder;
	/** パス名 */
	private String pathName;
	/** ファイル名 */
	private String fileName;
	/** メイン画像フラグ */
	private String mainImageFlg;
	/** キャプション */
	private String caption;
	/** コメント */
	private String imgComment;
	/** 縦長・横長フラグ */
	private String hwFlg;

	/**
	 * システム物件CD を取得する。<br/>
	 * <br/>
	 *
	 * @return システム物件CD
	 */
	public String getSysHousingCd() {
		return sysHousingCd;
	}

	/**
	 * システム物件CD を設定する。<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 */
	public void setSysHousingCd(String sysHousingCd) {
		this.sysHousingCd = sysHousingCd;
	}

	/**
	 * 画像タイプ を取得する。<br/>
	 * <br/>
	 *
	 * @return 画像タイプ
	 */
	public String getImageType() {
		return imageType;
	}

	/**
	 * 画像タイプ を設定する。<br/>
	 * <br/>
	 *
	 * @param imageType
	 */
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	/**
	 * 枝番 を取得する。<br/>
	 * <br/>
	 *
	 * @return 枝番
	 */
	public Integer getDivNo() {
		return divNo;
	}

	/**
	 * 枝番 を設定する。<br/>
	 * <br/>
	 *
	 * @param divNo
	 */
	public void setDivNo(Integer divNo) {
		this.divNo = divNo;
	}

	/**
	 * 表示順 を取得する。<br/>
	 * <br/>
	 *
	 * @return 表示順
	 */
	public Integer getSortOrder() {
		return sortOrder;
	}

	/**
	 * 表示順 を設定する。<br/>
	 * <br/>
	 *
	 * @param sortOrder
	 */
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * パス名 を取得する。<br/>
	 * <br/>
	 *
	 * @return パス名
	 */
	public String getPathName() {
		return pathName;
	}

	/**
	 * パス名 を設定する。<br/>
	 * <br/>
	 *
	 * @param pathName
	 */
	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	/**
	 * ファイル名を取得する。<br/>
	 * <br/>
	 * @return ファイル名
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * ファイル名を設定する。<br/>
	 * <br/>
	 * @param fileName　ファイル名
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * メイン画像フラグ を取得する。<br/>
	 * <br/>
	 *
	 * @return メイン画像フラグ
	 */
	public String getMainImageFlg() {
		return mainImageFlg;
	}

	/**
	 * メイン画像フラグ を設定する。<br/>
	 * <br/>
	 *
	 * @param mainImageFlg
	 */
	public void setMainImageFlg(String mainImageFlg) {
		this.mainImageFlg = mainImageFlg;
	}

	/**
	 * キャプション を取得する。<br/>
	 * <br/>
	 *
	 * @return キャプション
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * キャプション を設定する。<br/>
	 * <br/>
	 *
	 * @param caption
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * コメント を取得する。<br/>
	 * <br/>
	 *
	 * @return コメント
	 */
	public String getImgComment() {
		return imgComment;
	}

	/**
	 * コメント を設定する。<br/>
	 * <br/>
	 *
	 * @param imgComment
	 */
	public void setImgComment(String imgComment) {
		this.imgComment = imgComment;
	}

	/**
	 * 縦長・横長フラグ を取得する。<br/>
	 * <br/>
	 *
	 * @return 縦長・横長フラグ
	 */
	public String getHwFlg() {
		return hwFlg;
	}

	/**
	 * 縦長・横長フラグ を設定する。<br/>
	 * <br/>
	 *
	 * @param hwFlg
	 */
	public void setHwFlg(String hwFlg) {
		this.hwFlg = hwFlg;
	}
}
