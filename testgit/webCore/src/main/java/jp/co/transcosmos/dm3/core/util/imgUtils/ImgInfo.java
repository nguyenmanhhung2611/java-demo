package jp.co.transcosmos.dm3.core.util.imgUtils;


/**
 * 画像情報クラス.
 * <p>
 * 画像ファイルのサイズ情報を格納する。<br/>
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
public class ImgInfo {

	/** 画像の縦ピクセル数 */
	private Integer height;
	/** 画像の横ピクセル数 */
	private Integer width;
	/** 縦長・横長ステータス （0:横長画像、1:縦長画像、2:縦横同一）*/
	private Integer hwFlg;



	/**
	 * 画像の縦ピクセル数を取得する。<br/>
	 * <br/>
	 * @return 画像の縦ピクセル数
	 */
	public Integer getHeight() {
		return height;
	}

	/**
	 * 画像の縦ピクセル数を設定する。<br/>
	 * <br/>
	 * @param height 画像の縦ピクセル数
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}

	/**
	 * 画像の横ピクセル数を取得する。<br/>
	 * <br/>
	 * @return 画像の横ピクセル数
	 */
	public Integer getWidth() {
		return width;
	}

	/**
	 * 画像の横ピクセル数を取得する。<br/>
	 * <br/>
	 * @param width 画像の横ピクセル数
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}

	/**
	 * 縦長・横長ステータスを取得する。<br/>
	 * <br/>
	 * @return 0:横長画像、1:縦長画像、2:縦横同一
	 */
	public Integer getHwFlg() {
		return hwFlg;
	}

	/**
	 * 縦長・横長ステータスを設定する。<br/>
	 * <br/>
	 * @param hwFlg 0:横長画像、1:縦長画像、2:縦横同一
	 */
	public void setHwFlg(Integer hwFlg) {
		this.hwFlg = hwFlg;
	}

}
