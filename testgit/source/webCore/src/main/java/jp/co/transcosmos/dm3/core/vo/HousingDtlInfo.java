package jp.co.transcosmos.dm3.core.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 物件詳細情報.
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
public class HousingDtlInfo {

	/** システム物件CD */
	private String sysHousingCd;
	/** 用途地域CD */
	private String usedAreaCd;
	/** 取引形態区分 */
	private String transactTypeDiv;
	/** 表示用契約期間 */
	private String displayContractTerm;
	/** 土地権利 */
	private String landRight;
	/** 入居可能時期フラグ */
	private String moveinTiming;
	/** 入居可能時期 */
	private Date moveinTimingDay;
	/** 入居可能時期コメント */
	private String moveinNote;
	/** 表示用入居可能時期 */
	private String displayMoveinTiming;
	/** 表示用入居諸条件 */
	private String displayMoveinProviso;
	/** 管理形態・方式 */
	private String upkeepType;
	/** 管理会社 */
	private String upkeepCorp;
	/** 更新料 */
	private BigDecimal renewChrg;
	/** 更新料単位 */
	private String renewChrgCrs;
	/** 更新料名 */
	private String renewChrgName;
	/** 更新手数料 */
	private BigDecimal renewDue;
	/** 更新手数料単位 */
	private String renewDueCrs;
	/** 仲介手数料 */
	private BigDecimal brokerageChrg;
	/** 仲介手数料単位 */
	private String brokerageChrgCrs;
	/** 鍵交換料 */
	private BigDecimal changeKeyChrg;
	/** 損保有無 */
	private String insurExist;
	/** 損保料金 */
	private Long insurChrg;
	/** 損保年数 */
	private Integer insurTerm;
	/** 損保認定ランク */
	private String insurLank;
	/** 諸費用 */
	private Long otherChrg;
	/** 接道状況 */
	private String contactRoad;
	/** 接道方向/幅員 */
	private String contactRoadDir;
	/** 私道負担 */
	private String privateRoad;
	/** バルコニー面積 */
	private BigDecimal balconyArea;
	/** 特記事項 */
	private String specialInstruction;
	/** 詳細コメント */
	private String dtlComment;

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
	 * 用途地域CD を取得する。<br/>
	 * <br/>
	 *
	 * @return 用途地域CD
	 */
	public String getUsedAreaCd() {
		return usedAreaCd;
	}

	/**
	 * 用途地域CD を設定する。<br/>
	 * <br/>
	 *
	 * @param usedAreaCd
	 */
	public void setUsedAreaCd(String usedAreaCd) {
		this.usedAreaCd = usedAreaCd;
	}

	/**
	 * 取引形態区分 を取得する。<br/>
	 * <br/>
	 *
	 * @return 取引形態区分
	 */
	public String getTransactTypeDiv() {
		return transactTypeDiv;
	}

	/**
	 * 取引形態区分 を設定する。<br/>
	 * <br/>
	 *
	 * @param transactTypeDiv
	 */
	public void setTransactTypeDiv(String transactTypeDiv) {
		this.transactTypeDiv = transactTypeDiv;
	}

	/**
	 * 表示用契約期間 を取得する。<br/>
	 * <br/>
	 *
	 * @return 表示用契約期間
	 */
	public String getDisplayContractTerm() {
		return displayContractTerm;
	}

	/**
	 * 表示用契約期間 を設定する。<br/>
	 * <br/>
	 *
	 * @param displayContractTerm
	 */
	public void setDisplayContractTerm(String displayContractTerm) {
		this.displayContractTerm = displayContractTerm;
	}

	/**
	 * 土地権利 を取得する。<br/>
	 * <br/>
	 *
	 * @return 土地権利
	 */
	public String getLandRight() {
		return landRight;
	}

	/**
	 * 土地権利 を設定する。<br/>
	 * <br/>
	 *
	 * @param landRight
	 */
	public void setLandRight(String landRight) {
		this.landRight = landRight;
	}

	/**
	 * 入居可能時期フラグ を取得する。<br/>
	 * <br/>
	 *
	 * @return 入居可能時期フラグ
	 */
	public String getMoveinTiming() {
		return moveinTiming;
	}

	/**
	 * 入居可能時期フラグ を設定する。<br/>
	 * <br/>
	 *
	 * @param moveinTiming
	 */
	public void setMoveinTiming(String moveinTiming) {
		this.moveinTiming = moveinTiming;
	}

	/**
	 * 入居可能時期 を取得する。<br/>
	 * <br/>
	 *
	 * @return 入居可能時期
	 */
	public Date getMoveinTimingDay() {
		return moveinTimingDay;
	}

	/**
	 * 入居可能時期 を設定する。<br/>
	 * <br/>
	 *
	 * @param moveinTimingDay
	 */
	public void setMoveinTimingDay(Date moveinTimingDay) {
		this.moveinTimingDay = moveinTimingDay;
	}

	/**
	 * 入居可能時期コメント を取得する。<br/>
	 * <br/>
	 *
	 * @return 入居可能時期コメント
	 */
	public String getMoveinNote() {
		return moveinNote;
	}

	/**
	 * 入居可能時期コメント を設定する。<br/>
	 * <br/>
	 *
	 * @param moveinNote
	 */
	public void setMoveinNote(String moveinNote) {
		this.moveinNote = moveinNote;
	}

	/**
	 * 表示用入居可能時期 を取得する。<br/>
	 * <br/>
	 *
	 * @return 表示用入居可能時期
	 */
	public String getDisplayMoveinTiming() {
		return displayMoveinTiming;
	}

	/**
	 * 表示用入居可能時期 を設定する。<br/>
	 * <br/>
	 *
	 * @param displayMoveinTiming
	 */
	public void setDisplayMoveinTiming(String displayMoveinTiming) {
		this.displayMoveinTiming = displayMoveinTiming;
	}

	/**
	 * 表示用入居諸条件 を取得する。<br/>
	 * <br/>
	 *
	 * @return 表示用入居諸条件
	 */
	public String getDisplayMoveinProviso() {
		return displayMoveinProviso;
	}

	/**
	 * 表示用入居諸条件 を設定する。<br/>
	 * <br/>
	 *
	 * @param displayMoveinProviso
	 */
	public void setDisplayMoveinProviso(String displayMoveinProviso) {
		this.displayMoveinProviso = displayMoveinProviso;
	}

	/**
	 * 管理形態・方式 を取得する。<br/>
	 * <br/>
	 *
	 * @return 管理形態・方式
	 */
	public String getUpkeepType() {
		return upkeepType;
	}

	/**
	 * 管理形態・方式 を設定する。<br/>
	 * <br/>
	 *
	 * @param upkeepType
	 */
	public void setUpkeepType(String upkeepType) {
		this.upkeepType = upkeepType;
	}

	/**
	 * 管理会社 を取得する。<br/>
	 * <br/>
	 *
	 * @return 管理会社
	 */
	public String getUpkeepCorp() {
		return upkeepCorp;
	}

	/**
	 * 管理会社 を設定する。<br/>
	 * <br/>
	 *
	 * @param upkeepCorp
	 */
	public void setUpkeepCorp(String upkeepCorp) {
		this.upkeepCorp = upkeepCorp;
	}

	/**
	 * 更新料 を取得する。<br/>
	 * <br/>
	 *
	 * @return 更新料
	 */
	public BigDecimal getRenewChrg() {
		return renewChrg;
	}

	/**
	 * 更新料 を設定する。<br/>
	 * <br/>
	 *
	 * @param renewChrg
	 */
	public void setRenewChrg(BigDecimal renewChrg) {
		this.renewChrg = renewChrg;
	}

	/**
	 * 更新料単位 を取得する。<br/>
	 * <br/>
	 *
	 * @return 更新料単位
	 */
	public String getRenewChrgCrs() {
		return renewChrgCrs;
	}

	/**
	 * 更新料単位 を設定する。<br/>
	 * <br/>
	 *
	 * @param renewChrgCrs
	 */
	public void setRenewChrgCrs(String renewChrgCrs) {
		this.renewChrgCrs = renewChrgCrs;
	}

	/**
	 * 更新料名 を取得する。<br/>
	 * <br/>
	 *
	 * @return 更新料名
	 */
	public String getRenewChrgName() {
		return renewChrgName;
	}

	/**
	 * 更新料名 を設定する。<br/>
	 * <br/>
	 *
	 * @param renewChrgName
	 */
	public void setRenewChrgName(String renewChrgName) {
		this.renewChrgName = renewChrgName;
	}

	/**
	 * 更新手数料 を取得する。<br/>
	 * <br/>
	 *
	 * @return 更新手数料
	 */
	public BigDecimal getRenewDue() {
		return renewDue;
	}

	/**
	 * 更新手数料 を設定する。<br/>
	 * <br/>
	 *
	 * @param renewDue
	 */
	public void setRenewDue(BigDecimal renewDue) {
		this.renewDue = renewDue;
	}

	/**
	 * 更新手数料単位 を取得する。<br/>
	 * <br/>
	 *
	 * @return 更新手数料単位
	 */
	public String getRenewDueCrs() {
		return renewDueCrs;
	}

	/**
	 * 更新手数料単位 を設定する。<br/>
	 * <br/>
	 *
	 * @param renewDueCrs
	 */
	public void setRenewDueCrs(String renewDueCrs) {
		this.renewDueCrs = renewDueCrs;
	}

	/**
	 * 仲介手数料 を取得する。<br/>
	 * <br/>
	 *
	 * @return 仲介手数料
	 */
	public BigDecimal getBrokerageChrg() {
		return brokerageChrg;
	}

	/**
	 * 仲介手数料 を設定する。<br/>
	 * <br/>
	 *
	 * @param brokerageChrg
	 */
	public void setBrokerageChrg(BigDecimal brokerageChrg) {
		this.brokerageChrg = brokerageChrg;
	}

	/**
	 * 仲介手数料単位 を取得する。<br/>
	 * <br/>
	 *
	 * @return 仲介手数料単位
	 */
	public String getBrokerageChrgCrs() {
		return brokerageChrgCrs;
	}

	/**
	 * 仲介手数料単位 を設定する。<br/>
	 * <br/>
	 *
	 * @param brokerageChrgCrs
	 */
	public void setBrokerageChrgCrs(String brokerageChrgCrs) {
		this.brokerageChrgCrs = brokerageChrgCrs;
	}

	/**
	 * 鍵交換料 を取得する。<br/>
	 * <br/>
	 *
	 * @return 鍵交換料
	 */
	public BigDecimal getChangeKeyChrg() {
		return changeKeyChrg;
	}

	/**
	 * 鍵交換料 を設定する。<br/>
	 * <br/>
	 *
	 * @param changeKeyChrg
	 */
	public void setChangeKeyChrg(BigDecimal changeKeyChrg) {
		this.changeKeyChrg = changeKeyChrg;
	}

	/**
	 * 損保有無 を取得する。<br/>
	 * <br/>
	 *
	 * @return 損保有無
	 */
	public String getInsurExist() {
		return insurExist;
	}

	/**
	 * 損保有無 を設定する。<br/>
	 * <br/>
	 *
	 * @param insurExist
	 */
	public void setInsurExist(String insurExist) {
		this.insurExist = insurExist;
	}

	/**
	 * 損保料金 を取得する。<br/>
	 * <br/>
	 *
	 * @return 損保料金
	 */
	public Long getInsurChrg() {
		return insurChrg;
	}

	/**
	 * 損保料金 を設定する。<br/>
	 * <br/>
	 *
	 * @param insurChrg
	 */
	public void setInsurChrg(Long insurChrg) {
		this.insurChrg = insurChrg;
	}

	/**
	 * 損保年数 を取得する。<br/>
	 * <br/>
	 *
	 * @return 損保年数
	 */
	public Integer getInsurTerm() {
		return insurTerm;
	}

	/**
	 * 損保年数 を設定する。<br/>
	 * <br/>
	 *
	 * @param insurTerm
	 */
	public void setInsurTerm(Integer insurTerm) {
		this.insurTerm = insurTerm;
	}

	/**
	 * 損保認定ランク を取得する。<br/>
	 * <br/>
	 *
	 * @return 損保認定ランク
	 */
	public String getInsurLank() {
		return insurLank;
	}

	/**
	 * 損保認定ランク を設定する。<br/>
	 * <br/>
	 *
	 * @param insurLank
	 */
	public void setInsurLank(String insurLank) {
		this.insurLank = insurLank;
	}

	/**
	 * 諸費用 を取得する。<br/>
	 * <br/>
	 *
	 * @return 諸費用
	 */
	public Long getOtherChrg() {
		return otherChrg;
	}

	/**
	 * 諸費用 を設定する。<br/>
	 * <br/>
	 *
	 * @param otherChrg
	 */
	public void setOtherChrg(Long otherChrg) {
		this.otherChrg = otherChrg;
	}

	/**
	 * 接道状況 を取得する。<br/>
	 * <br/>
	 *
	 * @return 接道状況
	 */
	public String getContactRoad() {
		return contactRoad;
	}

	/**
	 * 接道状況 を設定する。<br/>
	 * <br/>
	 *
	 * @param contactRoad
	 */
	public void setContactRoad(String contactRoad) {
		this.contactRoad = contactRoad;
	}

	/**
	 * 接道方向/幅員 を取得する。<br/>
	 * <br/>
	 *
	 * @return 接道方向/幅員
	 */
	public String getContactRoadDir() {
		return contactRoadDir;
	}

	/**
	 * 接道方向/幅員 を設定する。<br/>
	 * <br/>
	 *
	 * @param contactRoadDir
	 */
	public void setContactRoadDir(String contactRoadDir) {
		this.contactRoadDir = contactRoadDir;
	}

	/**
	 * 私道負担 を取得する。<br/>
	 * <br/>
	 *
	 * @return 私道負担
	 */
	public String getPrivateRoad() {
		return privateRoad;
	}

	/**
	 * 私道負担 を設定する。<br/>
	 * <br/>
	 *
	 * @param privateRoad
	 */
	public void setPrivateRoad(String privateRoad) {
		this.privateRoad = privateRoad;
	}

	/**
	 * バルコニー面積 を取得する。<br/>
	 * <br/>
	 *
	 * @return バルコニー面積
	 */
	public BigDecimal getBalconyArea() {
		return balconyArea;
	}

	/**
	 * バルコニー面積 を設定する。<br/>
	 * <br/>
	 *
	 * @param balconyArea
	 */
	public void setBalconyArea(BigDecimal balconyArea) {
		this.balconyArea = balconyArea;
	}

	/**
	 * 特記事項 を取得する。<br/>
	 * <br/>
	 *
	 * @return 特記事項
	 */
	public String getSpecialInstruction() {
		return specialInstruction;
	}

	/**
	 * 特記事項 を設定する。<br/>
	 * <br/>
	 *
	 * @param specialInstruction
	 */
	public void setSpecialInstruction(String specialInstruction) {
		this.specialInstruction = specialInstruction;
	}

	/**
	 * 詳細コメント を取得する。<br/>
	 * <br/>
	 *
	 * @return 詳細コメント
	 */
	public String getDtlComment() {
		return dtlComment;
	}

	/**
	 * 詳細コメント を設定する。<br/>
	 * <br/>
	 *
	 * @param dtlComment
	 */
	public void setDtlComment(String dtlComment) {
		this.dtlComment = dtlComment;
	}
}
