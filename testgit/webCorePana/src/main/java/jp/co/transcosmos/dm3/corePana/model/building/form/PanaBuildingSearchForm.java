package jp.co.transcosmos.dm3.corePana.model.building.form;

import jp.co.transcosmos.dm3.core.model.building.form.BuildingSearchForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;

/**
 * <pre>
 * ���������h�}�[�N���p�̕����ꗗ���������ێ��p�t�H�[��
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * ������		2015.03.31	�V�K�쐬
 *
 *
 * </pre>
 */
public class PanaBuildingSearchForm extends BuildingSearchForm {
    /**
     * �p���p<br/>
     * <br/>
     */
    PanaBuildingSearchForm() {
        super();
    }

    /**
     * �R���X�g���N�^�[<br/>
     * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
     * <br/>
     * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
     */
    PanaBuildingSearchForm(LengthValidationUtils lengthUtils) {
        super();
        this.lengthUtils = lengthUtils;
    }

	/** �����ԍ��i���������j */
	private String keyHousingCd;
	/** �\���p�������i���������j */
	private String keyDisplayHousingName;
	/** �s���{��CD�i���������j */
	private String keyPrefCd;
	/** �s�撬��CD�i���������j */
	private String keyAddressCd;

    /** ������ʂ� command �p�����[�^ */
    private String keySearchCommand;

    /** �o�^�J�n�� �i���������j */
    private String keyInsDateStart;
    /** �o�^�I���� �i���������j */
    private String keyInsDateEnd;
    /** �X�V���� �i���������j */
    private String keyUpdDate;
    /** ����ԍ� �i���������j */
    private String keyUserId;
    /** ���J�敪 �i���������j */
    private String keyHiddenFlg;
    /** �X�e�[�^�X �i���������j */
    private String keyStatusCd;
	/**
	 * @return keyHousingCd
	 */
	public String getKeyHousingCd() {
		return keyHousingCd;
	}

	/**
	 * @param keyHousingCd �Z�b�g���� keyHousingCd
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
	 * @param keyDisplayHousingName �Z�b�g���� keyDisplayHousingName
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
	 * @param keyPrefCd �Z�b�g���� keyPrefCd
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
	 * @param keyAddressCd �Z�b�g���� keyAddressCd
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
	 * @param keySearchCommand �Z�b�g���� keySearchCommand
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
	 * @param keyInsDateStart �Z�b�g���� keyInsDateStart
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
	 * @param keyInsDateEnd �Z�b�g���� keyInsDateEnd
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
	 * @param keyUpdDate �Z�b�g���� keyUpdDate
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
	 * @param keyUserId �Z�b�g���� keyUserId
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
	 * @param keyHiddenFlg �Z�b�g���� keyHiddenFlg
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
	 * @param keyStatusCd �Z�b�g���� keyStatusCd
	 */
	public void setKeyStatusCd(String keyStatusCd) {
		this.keyStatusCd = keyStatusCd;
	}

}
