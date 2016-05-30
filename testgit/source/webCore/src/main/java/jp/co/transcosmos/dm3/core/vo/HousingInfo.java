package jp.co.transcosmos.dm3.core.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 物件基本情報.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 *
 * <pre>
 * 担当者        修正日        修正内容
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.03.10     新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 *
 */
public class HousingInfo {

	/** システム物件CD */
	private String sysHousingCd;
	/** 物件番号 */
	private String housingCd;
	/** 表示用物件名 */
	private String displayHousingName;
	/** 表示用物件名ふりがな */
	private String displayHousingNameKana;
	/** 部屋番号 */
	private String roomNo;
	/** システム建物CD */
	private String sysBuildingCd;
	/** 賃料/価格 */
	private Long price;
	/** 管理費 */
	private Long upkeep;
	/** 共益費 */
	private Long commonAreaFee;
	/** 修繕積立費 */
	private Long menteFee;
	/** 敷金 */
	private BigDecimal secDeposit;
	/** 敷金単位 */
	private String secDepositCrs;
	/** 保証金 */
	private BigDecimal bondChrg;
	/** 保証金単位 */
	private String bondChrgCrs;
	/** 敷引礼金区分 */
	private String depositDiv;
	/** 敷引礼金額 */
	private BigDecimal deposit;
	/** 敷引礼金単位 */
	private String depositCrs;
	/** 間取CD */
	private String layoutCd;
	/** 間取詳細コメント */
	private String layoutComment;
	/** 物件の階数 */
	private Integer floorNo;
	/** 物件の階数コメント */
	private String floorNoNote;
	/** 土地面積 */
	private BigDecimal landArea;
	/** 土地面積_補足 */
	private String landAreaMemo;
	/** 専有面積 */
	private BigDecimal personalArea;
	/** 専有面積_補足 */
	private String personalAreaMemo;
	/** 入居状態フラグ */
	private String moveinFlg;
	/** 駐車場の状況 */
	private String parkingSituation;
	/** 駐車場空の有無 */
	private String parkingEmpExist;
	/** 表示用駐車場情報 */
	private String displayParkingInfo;
	/** 窓の向き */
	private String windowDirection;
	/** アイコン情報 */
	private String iconCd;
	/** 基本情報コメント */
	private String basicComment;
	/** 登録日 */
	private Date insDate;
	/** 登録者 */
	private String insUserId;
	/** 最終更新日 */
	private Date updDate;
	/** 最終更新者 */
	private String updUserId;

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
	 * 物件番号 を取得する。<br/>
	 * <br/>
	 *
	 * @return 物件番号
	 */
	public String getHousingCd() {
		return housingCd;
	}

	/**
	 * 物件番号 を設定する。<br/>
	 * <br/>
	 *
	 * @param housingCd
	 */
	public void setHousingCd(String housingCd) {
		this.housingCd = housingCd;
	}

	/**
	 * 表示用物件名 を取得する。<br/>
	 * <br/>
	 *
	 * @return 表示用物件名
	 */
	public String getDisplayHousingName() {
		return displayHousingName;
	}

	/**
	 * 表示用物件名 を設定する。<br/>
	 * <br/>
	 *
	 * @param displayHousingName
	 */
	public void setDisplayHousingName(String displayHousingName) {
		this.displayHousingName = displayHousingName;
	}

	/**
	 * 表示用物件名ふりがな を取得する。<br/>
	 * <br/>
	 *
	 * @return 表示用物件名ふりがな
	 */
	public String getDisplayHousingNameKana() {
		return displayHousingNameKana;
	}

	/**
	 * 表示用物件名ふりがな を設定する。<br/>
	 * <br/>
	 *
	 * @param displayHousingNameKana
	 */
	public void setDisplayHousingNameKana(String displayHousingNameKana) {
		this.displayHousingNameKana = displayHousingNameKana;
	}

	/**
	 * 部屋番号 を取得する。<br/>
	 * <br/>
	 *
	 * @return 部屋番号
	 */
	public String getRoomNo() {
		return roomNo;
	}

	/**
	 * 部屋番号 を設定する。<br/>
	 * <br/>
	 *
	 * @param roomNo
	 */
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	/**
	 * システム建物CD を取得する。<br/>
	 * <br/>
	 *
	 * @return システム建物CD
	 */
	public String getSysBuildingCd() {
		return sysBuildingCd;
	}

	/**
	 * システム建物CD を設定する。<br/>
	 * <br/>
	 *
	 * @param sysBuildingCd
	 */
	public void setSysBuildingCd(String sysBuildingCd) {
		this.sysBuildingCd = sysBuildingCd;
	}

	/**
	 * 賃料/価格 を取得する。<br/>
	 * <br/>
	 *
	 * @return 賃料/価格
	 */
	public Long getPrice() {
		return price;
	}

	/**
	 * 賃料/価格 を設定する。<br/>
	 * <br/>
	 *
	 * @param price
	 */
	public void setPrice(Long price) {
		this.price = price;
	}

	/**
	 * 管理費 を取得する。<br/>
	 * <br/>
	 *
	 * @return 管理費
	 */
	public Long getUpkeep() {
		return upkeep;
	}

	/**
	 * 管理費 を設定する。<br/>
	 * <br/>
	 *
	 * @param upkeep
	 */
	public void setUpkeep(Long upkeep) {
		this.upkeep = upkeep;
	}

	/**
	 * 共益費 を取得する。<br/>
	 * <br/>
	 *
	 * @return 共益費
	 */
	public Long getCommonAreaFee() {
		return commonAreaFee;
	}

	/**
	 * 共益費 を設定する。<br/>
	 * <br/>
	 *
	 * @param commonAreaFee
	 */
	public void setCommonAreaFee(Long commonAreaFee) {
		this.commonAreaFee = commonAreaFee;
	}

	/**
	 * 修繕積立費 を取得する。<br/>
	 * <br/>
	 *
	 * @return 修繕積立費
	 */
	public Long getMenteFee() {
		return menteFee;
	}

	/**
	 * 修繕積立費 を設定する。<br/>
	 * <br/>
	 *
	 * @param menteFee
	 */
	public void setMenteFee(Long menteFee) {
		this.menteFee = menteFee;
	}

	/**
	 * 敷金 を取得する。<br/>
	 * <br/>
	 *
	 * @return 敷金
	 */
	public BigDecimal getSecDeposit() {
		return secDeposit;
	}

	/**
	 * 敷金 を設定する。<br/>
	 * <br/>
	 *
	 * @param secDeposit
	 */
	public void setSecDeposit(BigDecimal secDeposit) {
		this.secDeposit = secDeposit;
	}

	/**
	 * 敷金単位 を取得する。<br/>
	 * <br/>
	 *
	 * @return 敷金単位
	 */
	public String getSecDepositCrs() {
		return secDepositCrs;
	}

	/**
	 * 敷金単位 を設定する。<br/>
	 * <br/>
	 *
	 * @param secDepositCrs
	 */
	public void setSecDepositCrs(String secDepositCrs) {
		this.secDepositCrs = secDepositCrs;
	}

	/**
	 * 保証金 を取得する。<br/>
	 * <br/>
	 *
	 * @return 保証金
	 */
	public BigDecimal getBondChrg() {
		return bondChrg;
	}

	/**
	 * 保証金 を設定する。<br/>
	 * <br/>
	 *
	 * @param bondChrg
	 */
	public void setBondChrg(BigDecimal bondChrg) {
		this.bondChrg = bondChrg;
	}

	/**
	 * 保証金単位 を取得する。<br/>
	 * <br/>
	 *
	 * @return 保証金単位
	 */
	public String getBondChrgCrs() {
		return bondChrgCrs;
	}

	/**
	 * 保証金単位 を設定する。<br/>
	 * <br/>
	 *
	 * @param bondChrgCrs
	 */
	public void setBondChrgCrs(String bondChrgCrs) {
		this.bondChrgCrs = bondChrgCrs;
	}

	/**
	 * 敷引礼金区分 を取得する。<br/>
	 * <br/>
	 *
	 * @return 敷引礼金区分
	 */
	public String getDepositDiv() {
		return depositDiv;
	}

	/**
	 * 敷引礼金区分 を設定する。<br/>
	 * <br/>
	 *
	 * @param depositDiv
	 */
	public void setDepositDiv(String depositDiv) {
		this.depositDiv = depositDiv;
	}

	/**
	 * 敷引礼金額 を取得する。<br/>
	 * <br/>
	 *
	 * @return 敷引礼金額
	 */
	public BigDecimal getDeposit() {
		return deposit;
	}

	/**
	 * 敷引礼金額 を設定する。<br/>
	 * <br/>
	 *
	 * @param deposit
	 */
	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
	}

	/**
	 * 敷引礼金単位 を取得する。<br/>
	 * <br/>
	 *
	 * @return 敷引礼金単位
	 */
	public String getDepositCrs() {
		return depositCrs;
	}

	/**
	 * 敷引礼金単位 を設定する。<br/>
	 * <br/>
	 *
	 * @param depositCrs
	 */
	public void setDepositCrs(String depositCrs) {
		this.depositCrs = depositCrs;
	}

	/**
	 * 間取CD を取得する。<br/>
	 * <br/>
	 *
	 * @return 間取CD
	 */
	public String getLayoutCd() {
		return layoutCd;
	}

	/**
	 * 間取CD を設定する。<br/>
	 * <br/>
	 *
	 * @param layoutCd
	 */
	public void setLayoutCd(String layoutCd) {
		this.layoutCd = layoutCd;
	}

	/**
	 * 間取詳細コメント を取得する。<br/>
	 * <br/>
	 *
	 * @return 間取詳細コメント
	 */
	public String getLayoutComment() {
		return layoutComment;
	}

	/**
	 * 間取詳細コメント を設定する。<br/>
	 * <br/>
	 *
	 * @param layoutComment
	 */
	public void setLayoutComment(String layoutComment) {
		this.layoutComment = layoutComment;
	}

	/**
	 * 物件の階数 を取得する。<br/>
	 * <br/>
	 *
	 * @return 物件の階数
	 */
	public Integer getFloorNo() {
		return floorNo;
	}

	/**
	 * 物件の階数 を設定する。<br/>
	 * <br/>
	 *
	 * @param floorNo
	 */
	public void setFloorNo(Integer floorNo) {
		this.floorNo = floorNo;
	}

	/**
	 * 物件の階数コメント を取得する。<br/>
	 * <br/>
	 *
	 * @return 物件の階数コメント
	 */
	public String getFloorNoNote() {
		return floorNoNote;
	}

	/**
	 * 物件の階数コメント を設定する。<br/>
	 * <br/>
	 *
	 * @param floorNoNote
	 */
	public void setFloorNoNote(String floorNoNote) {
		this.floorNoNote = floorNoNote;
	}

	/**
	 * 土地面積 を取得する。<br/>
	 * <br/>
	 *
	 * @return 土地面積
	 */
	public BigDecimal getLandArea() {
		return landArea;
	}

	/**
	 * 土地面積 を設定する。<br/>
	 * <br/>
	 *
	 * @param landArea
	 */
	public void setLandArea(BigDecimal landArea) {
		this.landArea = landArea;
	}

	/**
	 * 土地面積_補足 を取得する。<br/>
	 * <br/>
	 *
	 * @return 土地面積_補足
	 */
	public String getLandAreaMemo() {
		return landAreaMemo;
	}

	/**
	 * 土地面積_補足 を設定する。<br/>
	 * <br/>
	 *
	 * @param landAreaMemo
	 */
	public void setLandAreaMemo(String landAreaMemo) {
		this.landAreaMemo = landAreaMemo;
	}

	/**
	 * 専有面積 を取得する。<br/>
	 * <br/>
	 *
	 * @return 専有面積
	 */
	public BigDecimal getPersonalArea() {
		return personalArea;
	}

	/**
	 * 専有面積 を設定する。<br/>
	 * <br/>
	 *
	 * @param personalArea
	 */
	public void setPersonalArea(BigDecimal personalArea) {
		this.personalArea = personalArea;
	}

	/**
	 * 専有面積_補足 を取得する。<br/>
	 * <br/>
	 *
	 * @return 専有面積_補足
	 */
	public String getPersonalAreaMemo() {
		return personalAreaMemo;
	}

	/**
	 * 専有面積_補足 を設定する。<br/>
	 * <br/>
	 *
	 * @param personalAreaMemo
	 */
	public void setPersonalAreaMemo(String personalAreaMemo) {
		this.personalAreaMemo = personalAreaMemo;
	}

	/**
	 * 入居状態フラグ を取得する。<br/>
	 * <br/>
	 *
	 * @return 入居状態フラグ
	 */
	public String getMoveinFlg() {
		return moveinFlg;
	}

	/**
	 * 入居状態フラグ を設定する。<br/>
	 * <br/>
	 *
	 * @param moveinFlg
	 */
	public void setMoveinFlg(String moveinFlg) {
		this.moveinFlg = moveinFlg;
	}

	/**
	 * 駐車場の状況 を取得する。<br/>
	 * <br/>
	 *
	 * @return 駐車場の状況
	 */
	public String getParkingSituation() {
		return parkingSituation;
	}

	/**
	 * 駐車場の状況 を設定する。<br/>
	 * <br/>
	 *
	 * @param parkingSituation
	 */
	public void setParkingSituation(String parkingSituation) {
		this.parkingSituation = parkingSituation;
	}

	/**
	 * 駐車場空の有無 を取得する。<br/>
	 * <br/>
	 *
	 * @return 駐車場空の有無
	 */
	public String getParkingEmpExist() {
		return parkingEmpExist;
	}

	/**
	 * 駐車場空の有無 を設定する。<br/>
	 * <br/>
	 *
	 * @param parkingEmpExist
	 */
	public void setParkingEmpExist(String parkingEmpExist) {
		this.parkingEmpExist = parkingEmpExist;
	}

	/**
	 * 表示用駐車場情報 を取得する。<br/>
	 * <br/>
	 *
	 * @return 表示用駐車場情報
	 */
	public String getDisplayParkingInfo() {
		return displayParkingInfo;
	}

	/**
	 * 表示用駐車場情報 を設定する。<br/>
	 * <br/>
	 *
	 * @param displayParkingInfo
	 */
	public void setDisplayParkingInfo(String displayParkingInfo) {
		this.displayParkingInfo = displayParkingInfo;
	}

	/**
	 * 窓の向き を取得する。<br/>
	 * <br/>
	 *
	 * @return 窓の向き
	 */
	public String getWindowDirection() {
		return windowDirection;
	}

	/**
	 * 窓の向き を設定する。<br/>
	 * <br/>
	 *
	 * @param windowDirection
	 */
	public void setWindowDirection(String windowDirection) {
		this.windowDirection = windowDirection;
	}

	/**
	 * アイコン情報 を取得する。<br/>
	 * <br/>
	 *
	 * @return アイコン情報
	 */
	public String getIconCd() {
		return iconCd;
	}

	/**
	 * アイコン情報 を設定する。<br/>
	 * <br/>
	 *
	 * @param iconCd
	 */
	public void setIconCd(String iconCd) {
		this.iconCd = iconCd;
	}

	/**
	 * 基本情報コメント を取得する。<br/>
	 * <br/>
	 *
	 * @return 基本情報コメント
	 */
	public String getBasicComment() {
		return basicComment;
	}

	/**
	 * 基本情報コメント を設定する。<br/>
	 * <br/>
	 *
	 * @param basicComment
	 */
	public void setBasicComment(String basicComment) {
		this.basicComment = basicComment;
	}

	/**
	 * 登録日 を取得する。<br/>
	 * <br/>
	 *
	 * @return 登録日
	 */
	public Date getInsDate() {
		return insDate;
	}

	/**
	 * 登録日 を設定する。<br/>
	 * <br/>
	 *
	 * @param insDate
	 */
	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	/**
	 * 登録者 を取得する。<br/>
	 * <br/>
	 *
	 * @return 登録者
	 */
	public String getInsUserId() {
		return insUserId;
	}

	/**
	 * 登録者 を設定する。<br/>
	 * <br/>
	 *
	 * @param insUserId
	 */
	public void setInsUserId(String insUserId) {
		this.insUserId = insUserId;
	}

	/**
	 * 最終更新日 を取得する。<br/>
	 * <br/>
	 *
	 * @return 最終更新日
	 */
	public Date getUpdDate() {
		return updDate;
	}

	/**
	 * 最終更新日 を設定する。<br/>
	 * <br/>
	 *
	 * @param updDate
	 */
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	/**
	 * 最終更新者 を取得する。<br/>
	 * <br/>
	 *
	 * @return 最終更新者
	 */
	public String getUpdUserId() {
		return updUserId;
	}

	/**
	 * 最終更新者 を設定する。<br/>
	 * <br/>
	 *
	 * @param updUserId
	 */
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}
}
