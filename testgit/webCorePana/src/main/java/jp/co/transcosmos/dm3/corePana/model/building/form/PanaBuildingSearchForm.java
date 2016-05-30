package jp.co.transcosmos.dm3.corePana.model.building.form;

import jp.co.transcosmos.dm3.core.model.building.form.BuildingSearchForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;

/**
 * <pre>
 * 建物ランドマーク情報用の物件一覧検索条件保持用フォーム
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * 劉艶東		2015.03.31	新規作成
 *
 *
 * </pre>
 */
public class PanaBuildingSearchForm extends BuildingSearchForm {
    /**
     * 継承用<br/>
     * <br/>
     */
    PanaBuildingSearchForm() {
        super();
    }

    /**
     * コンストラクター<br/>
     * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
     * <br/>
     * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
     */
    PanaBuildingSearchForm(LengthValidationUtils lengthUtils) {
        super();
        this.lengthUtils = lengthUtils;
    }

	/** 物件番号（検索条件） */
	private String keyHousingCd;
	/** 表示用物件名（検索条件） */
	private String keyDisplayHousingName;
	/** 都道府県CD（検索条件） */
	private String keyPrefCd;
	/** 市区町村CD（検索条件） */
	private String keyAddressCd;

    /** 検索画面の command パラメータ */
    private String keySearchCommand;

    /** 登録開始日 （検索条件） */
    private String keyInsDateStart;
    /** 登録終了日 （検索条件） */
    private String keyInsDateEnd;
    /** 更新日時 （検索条件） */
    private String keyUpdDate;
    /** 会員番号 （検索条件） */
    private String keyUserId;
    /** 公開区分 （検索条件） */
    private String keyHiddenFlg;
    /** ステータス （検索条件） */
    private String keyStatusCd;
	/**
	 * @return keyHousingCd
	 */
	public String getKeyHousingCd() {
		return keyHousingCd;
	}

	/**
	 * @param keyHousingCd セットする keyHousingCd
	 */
	public void setKeyHousingCd(String keyHousingCd) {
		this.keyHousingCd = keyHousingCd;
	}

	/**
	 * @return keyDisplayHousingName
	 */
	public String getKeyDisplayHousingName() {
		return keyDisplayHousingName;
	}

	/**
	 * @param keyDisplayHousingName セットする keyDisplayHousingName
	 */
	public void setKeyDisplayHousingName(String keyDisplayHousingName) {
		this.keyDisplayHousingName = keyDisplayHousingName;
	}

	/**
	 * @return keyPrefCd
	 */
	public String getKeyPrefCd() {
		return keyPrefCd;
	}

	/**
	 * @param keyPrefCd セットする keyPrefCd
	 */
	public void setKeyPrefCd(String keyPrefCd) {
		this.keyPrefCd = keyPrefCd;
	}

	/**
	 * @return keyAddressCd
	 */
	public String getKeyAddressCd() {
		return keyAddressCd;
	}

	/**
	 * @param keyAddressCd セットする keyAddressCd
	 */
	public void setKeyAddressCd(String keyAddressCd) {
		this.keyAddressCd = keyAddressCd;
	}

	/**
	 * @return keySearchCommand
	 */
	public String getKeySearchCommand() {
		return keySearchCommand;
	}

	/**
	 * @param keySearchCommand セットする keySearchCommand
	 */
	public void setKeySearchCommand(String keySearchCommand) {
		this.keySearchCommand = keySearchCommand;
	}

	/**
	 * @return keyInsDateStart
	 */
	public String getKeyInsDateStart() {
		return keyInsDateStart;
	}

	/**
	 * @param keyInsDateStart セットする keyInsDateStart
	 */
	public void setKeyInsDateStart(String keyInsDateStart) {
		this.keyInsDateStart = keyInsDateStart;
	}

	/**
	 * @return keyInsDateEnd
	 */
	public String getKeyInsDateEnd() {
		return keyInsDateEnd;
	}

	/**
	 * @param keyInsDateEnd セットする keyInsDateEnd
	 */
	public void setKeyInsDateEnd(String keyInsDateEnd) {
		this.keyInsDateEnd = keyInsDateEnd;
	}

	/**
	 * @return keyUpdDate
	 */
	public String getKeyUpdDate() {
		return keyUpdDate;
	}

	/**
	 * @param keyUpdDate セットする keyUpdDate
	 */
	public void setKeyUpdDate(String keyUpdDate) {
		this.keyUpdDate = keyUpdDate;
	}

	/**
	 * @return keyUserId
	 */
	public String getKeyUserId() {
		return keyUserId;
	}

	/**
	 * @param keyUserId セットする keyUserId
	 */
	public void setKeyUserId(String keyUserId) {
		this.keyUserId = keyUserId;
	}

	/**
	 * @return keyHiddenFlg
	 */
	public String getKeyHiddenFlg() {
		return keyHiddenFlg;
	}

	/**
	 * @param keyHiddenFlg セットする keyHiddenFlg
	 */
	public void setKeyHiddenFlg(String keyHiddenFlg) {
		this.keyHiddenFlg = keyHiddenFlg;
	}

	/**
	 * @return keyStatusCd
	 */
	public String getKeyStatusCd() {
		return keyStatusCd;
	}

	/**
	 * @param keyStatusCd セットする keyStatusCd
	 */
	public void setKeyStatusCd(String keyStatusCd) {
		this.keyStatusCd = keyStatusCd;
	}

}
