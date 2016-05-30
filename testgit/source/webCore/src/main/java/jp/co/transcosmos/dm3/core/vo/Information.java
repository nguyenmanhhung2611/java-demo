package jp.co.transcosmos.dm3.core.vo;

import java.util.Date;

/**
 * お知らせ情報クラス.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.06	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 * 
 */
public class Information {

	/** お知らせ番号 */
	private String informationNo;
	/** お知らせ種別 */
	private String informationType;
	/** タイトル */
	private String title;
	/** 表示開始日 */
	private Date startDate;
	/** 表示終了日 */
	private Date endDate;
	/** リンク先URL */
	private String url;
	/** 公開対象区分 */
	private String dspFlg;
	/** お知らせ内容 */
	private String informationMsg;
	/** 登録日 */
	private Date insDate;
	/** 登録者 */
	private String insUserId;
	/** 最終更新日 */
	private Date updDate;
	/** 最終更新者 */
	private String updUserId;
	
	/**
	 * お知らせ番号を設定する。<br/>
	 * <br/>
	 * @param informationNo お知らせ番号
	 */
	public String getInformationNo() {
		return informationNo;
	}
	
	/**
	 * お知らせ番号を取得する。<br/>
	 * <br/>
	 * @return お知らせ番号
	 */
	public void setInformationNo(String informationNo) {
		this.informationNo = informationNo;
	}
	
	/**
	 * お知らせ種別を設定する。<br/>
	 * <br/>
	 * @param informationType お知らせ種別
	 */
	public String getInformationType() {
		return informationType;
	}
	
	/**
	 * お知らせ種別を取得する。<br/>
	 * <br/>
	 * @return お知らせ種別
	 */
	public void setInformationType(String informationType) {
		this.informationType = informationType;
	}
	
	/**
	 * タイトルを設定する。<br/>
	 * <br/>
	 * @param title タイトル
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * タイトルを取得する。<br/>
	 * <br/>
	 * @return タイトル
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * 表示開始日を設定する。<br/>
	 * <br/>
	 * @param startDate 表示開始日
	 */
	public Date getStartDate() {
		return startDate;
	}
	
	/**
	 * 表示開始日を取得する。<br/>
	 * <br/>
	 * @return 表示開始日
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * 表示終了日を設定する。<br/>
	 * <br/>
	 * @param endDate 表示終了日
	 */
	public Date getEndDate() {
		return endDate;
	}
	
	/**
	 * 表示終了日を取得する。<br/>
	 * <br/>
	 * @return 表示終了日
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * リンク先URLを設定する。<br/>
	 * <br/>
	 * @param url リンク先URL
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * リンク先URLを取得する。<br/>
	 * <br/>
	 * @return リンク先URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * 公開対象区分を設定する。<br/>
	 * <br/>
	 * @param dspFlg 公開対象区分
	 */
	public String getDspFlg() {
		return dspFlg;
	}
	
	/**
	 * 公開対象区分を取得する。<br/>
	 * <br/>
	 * @return 公開対象区分
	 */
	public void setDspFlg(String dspFlg) {
		this.dspFlg = dspFlg;
	}
	
	/**
	 * お知らせ内容を設定する。<br/>
	 * <br/>
	 * @param informationMsg お知らせ内容
	 */
	public String getInformationMsg() {
		return informationMsg;
	}
	
	/**
	 * お知らせ内容を取得する。<br/>
	 * <br/>
	 * @return お知らせ内容
	 */
	public void setInformationMsg(String informationMsg) {
		this.informationMsg = informationMsg;
	}
	
	/**
	 * 登録日を設定する。<br/>
	 * <br/>
	 * @param insDate 登録日
	 */
	public Date getInsDate() {
		return insDate;
	}
	
	/**
	 * 登録日を取得する。<br/>
	 * <br/>
	 * @return 登録日
	 */
	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}
	
	/**
	 * 登録者を設定する。<br/>
	 * <br/>
	 * @param insUserId 登録者
	 */
	public String getInsUserId() {
		return insUserId;
	}
	
	/**
	 * 登録者を取得する。<br/>
	 * <br/>
	 * @return 登録者
	 */
	public void setInsUserId(String insUserId) {
		this.insUserId = insUserId;
	}
	
	/**
	 * 最終更新日を設定する。<br/>
	 * <br/>
	 * @param updDate 最終更新日
	 */
	public Date getUpdDate() {
		return updDate;
	}
	
	/**
	 * 最終更新日を取得する。<br/>
	 * <br/>
	 * @return 最終更新日
	 */
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}
	
	/**
	 * 最終更新者を設定する。<br/>
	 * <br/>
	 * @param updUserId 最終更新者
	 */
	public String getUpdUserId() {
		return updUserId;
	}
	
	/**
	 * 最終更新者を取得する。<br/>
	 * <br/>
	 * @return 最終更新者
	 */
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}

}
