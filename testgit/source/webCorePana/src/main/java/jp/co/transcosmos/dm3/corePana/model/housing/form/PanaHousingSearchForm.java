package jp.co.transcosmos.dm3.corePana.model.housing.form;

import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.model.housing.form.HousingSearchForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.validation.DateFromToValidation;
import jp.co.transcosmos.dm3.form.annotation.UsePagingParam;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.validation.AlphanumericOnlyValidation;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.ValidDateValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * �����ꗗ���̌����p�����[�^�A����сA��ʃR���g���[���p�����[�^����p�t�H�[��.
 * <p>
 * ���������ƂȂ郊�N�G�X�g�p�����[�^�̎擾��A�c�a�����I�u�W�F�N�g�̐������s���B
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * guo.zhonglei		2015.04.09	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class PanaHousingSearchForm extends HousingSearchForm implements Validateable {

    // command ���N�G�X�g�p�����[�^�́AURL �}�b�s���O�Œ�`����Ă���AnamedCommands �̐���ɂ��g�p�����B
    // �����ꗗ���̃����e�i���X�@�\�ł́A�ȉ��̒l���ݒ肳���ꍇ������B
    //
    // list : �����ꗗ��񌟍���ʂŎ��ۂɌ������s���ꍇ�Ɏw�肷��B�@�i���w��̏ꍇ�A������ʂ������\���B�j
    // back : ���͊m�F��ʂ�����͉�ʂ֕��A�����ꍇ�B�@�i�w�肳�ꂽ�ꍇ�A�p�����[�^�̒l�������l�Ƃ��ē��͉�ʂɕ\������B�j

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

    /** ���߃t���O */
	private String command;

	/** �s���{��CD */
	private String prefCd;

	/** �������CD */
	private String housingKindCd;

	/** �s�撬��CD */
	private String addressCd;

	/** �wCD */
	private String stationCd;

	/** �H��CD */
	private String routeCd;

	/** ���t�H�[�����i���݂Ō������� */
	@UsePagingParam
	private String reformPriceCheck;

	/** �z�N�� */
	@UsePagingParam
	private String keyCompDate;

	/** �������߂̃|�C���g */
	private String[] iconCd;

	/** ���i(�\�[�g����) */
	@UsePagingParam
	private String sortPriceValue;

	/** �����o�^���i�V�����j�i�\�[�g�����j */
	@UsePagingParam
	private String sortUpdDateValue;

	/** �z�N���Â����i�\�[�g�����j */
	@UsePagingParam
	private String sortBuildDateValue;

	/** �w����̋����i�\�[�g�����j */
	@UsePagingParam
	private String sortWalkTimeValue;

	/** ����/���i�E�����i���������j */
	@UsePagingParam
	private String priceLower;
	/** ����/���i�E����i���������j */
	@UsePagingParam
	private String priceUpper;

	/** �y�n�ʐρE�����i���������j */
	@UsePagingParam
	private String landAreaLower;
	/** �y�n�ʐρE����i���������j */
	@UsePagingParam
	private String landAreaUpper;
	/** ��L�ʐρE�����i���������j */
	@UsePagingParam
	private String personalAreaLower;
	/** ��L�ʐρE����i���������j */
	@UsePagingParam
	private String personalAreaUpper;

	/** �����ʐρE�����i���������j */
	@UsePagingParam
	private String buildingAreaLower;
	/** �����ʐρE����i���������j */
	@UsePagingParam
	private String buildingAreaUpper;
	/** �������߂̃|�C���g */
	@UsePagingParam
	private String keyIconCd;

	/** �Ԏ��CD�i���������j */
	private String[] layoutCd;

	/** ���n�����i���������j */
	@UsePagingParam
	private String moveinTiming;

	/** ���r�ی��i���������j */
	private String insurExist;

	/** ���݊K���i���������j */
	@UsePagingParam
	private String partSrchCdFloorArray;

	/** �w�k���i���������j */
	@UsePagingParam
	private String partSrchCdWalkArray;

	/** �����摜���A�������� �i���������j */
	@UsePagingParam
	private String partSrchCdArray;

	/** �s�撬���� */
	private String hidAddressName;

	/** �N���A�t���O */
	private String clearFlg;

	/** �L�[���[�h */
	private String keywords;

	/** ���� */
	private String description;

	/** ���ʃR�[�h�ϊ����� */
    private CodeLookupManager codeLookupManager;
    /** �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B */
    protected LengthValidationUtils lengthUtils;

    /** �\�[�g���� */
    @UsePagingParam
	private String keyOrderType;

    /** �Ԏ��CD�i���������j */
    @UsePagingParam
	private String keyLayoutCd;

	/** �����ԍ��i���������j */
	private String keyHousingCd;
	
//	private String keyReformType;

	/**
	 * �w���擾����B<br/>
	 * <br/>
	 *
	 * @return �w
	 */
	public String getStationCd() {
		return stationCd;
	}

	/**
	 * �w��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param stationCd �w
	 */
	public void setStationCd(String stationCd) {
		this.stationCd = stationCd;
	}

	/**
	 * �����ԍ��i���������j���擾����B<br/>
	 * <br/>
	 *
	 * @return �����ԍ��i���������j
	 */
	public String getKeyHousingCd() {
		return keyHousingCd;
	}

	/**
	 * �����ԍ��i���������j��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param keyHousingCd �����ԍ��i���������j
	 */
	public void setKeyHousingCd(String keyHousingCd) {
		this.keyHousingCd = keyHousingCd;
	}

	/**
	 * �Ԏ��CD�i���������j���擾����B<br/>
	 * <br/>
	 *
	 * @return �Ԏ��CD�i���������j
	 */
	public String getKeyLayoutCd() {
		return keyLayoutCd;
	}

	/**
	 * �Ԏ��CD�i���������j��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param keyLayoutCd �Ԏ��CD�i���������j
	 */
	public void setKeyLayoutCd(String keyLayoutCd) {
		this.keyLayoutCd = keyLayoutCd;
	}

	/**
	 * �\�[�g�������擾����B<br/>
	 * <br/>
	 *
	 * @return �\�[�g����
	 */
	public String getKeyOrderType() {
		return keyOrderType;
	}

	/**
	 * �\�[�g������ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param keyOrder �\�[�g����
	 */
	public void setKeyOrderType(String keyOrderType) {
		this.keyOrderType = keyOrderType;
	}

    /**
     * �f�t�H���g�R���X�g���N�^�[<br/>
     * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
     * <br/>
     */
    PanaHousingSearchForm(CodeLookupManager codeLookupManager, LengthValidationUtils lengthUtils) {
        super();
        this.codeLookupManager = codeLookupManager;
        this.lengthUtils = lengthUtils;
    }


	/**
     * ������ʂ� command �p�����[�^���擾����B<br/>
     * <br/>
     * @return ������ʂ� command �p�����[�^
     */
    public String getKeySearchCommand() {
        return keySearchCommand;
    }

    /**
     * ������ʂ� command �p�����[�^��ݒ肷��B<br/>
     * <br/>
     * @param searchCommand ������ʂ� command �p�����[�^
     */
    public void setKeySearchCommand(String keySearchCommand) {
        this.keySearchCommand = keySearchCommand;
    }

    /**
     * �o�^�J�n�� �i���������j���擾����B<br/>
     * <br/>
     * @return �o�^�J�n�� �i���������j
     */
    public String getKeyInsDateStart() {
        return keyInsDateStart;
    }

    /**
     * �o�^�J�n�� �i���������j��ݒ肷��B<br/>
     * <br/>
     * @param insDateStart �o�^�J�n�� �i���������j
     */
    public void setKeyInsDateStart(String keyInsDateStart) {
        this.keyInsDateStart = keyInsDateStart;
    }

    /**
     * �o�^�I���� �i���������j���擾����B<br/>
     * <br/>
     * @return �o�^�I���� �i���������j
     */
    public String getKeyInsDateEnd() {
        return keyInsDateEnd;
    }

    /**
     * �o�^�I���� �i���������j��ݒ肷��B<br/>
     * <br/>
     * @param insDateEnd �o�^�I���� �i���������j
     */
    public void setKeyInsDateEnd(String keyInsDateEnd) {
        this.keyInsDateEnd = keyInsDateEnd;
    }

    /**
     * �X�V���� �i���������j���擾����B<br/>
     * <br/>
     * @return �X�V���� �i���������j
     */
    public String getKeyUpdDate() {
        return keyUpdDate;
    }

    /**
     * �X�V���� �i���������j��ݒ肷��B<br/>
     * <br/>
     * @param updDate �X�V���� �i���������j
     */
    public void setKeyUpdDate(String keyUpdDate) {
        this.keyUpdDate = keyUpdDate;
    }

    /**
     * ����ԍ� �i���������j���擾����B<br/>
     * <br/>
     * @return ����ԍ� �i���������j
     */
    public String getKeyUserId() {
        return keyUserId;
    }

    /**
     * ����ԍ� �i���������j��ݒ肷��B<br/>
     * <br/>
     * @param userId ����ԍ� �i���������j
     */
    public void setKeyUserId(String keyUserId) {
        this.keyUserId = keyUserId;
    }

    /**
     * ���J�敪 �i���������j���擾����B<br/>
     * <br/>
     * @return ���J�敪 �i���������j
     */
    public String getKeyHiddenFlg() {
        return keyHiddenFlg;
    }

    /**
     * ���J�敪 �i���������j��ݒ肷��B<br/>
     * <br/>
     * @param hiddenFlg ���J�敪 �i���������j
     */
    public void setKeyHiddenFlg(String keyHiddenFlg) {
        this.keyHiddenFlg = keyHiddenFlg;
    }

    /**
     * �X�e�[�^�X �i���������j���擾����B<br/>
     * <br/>
     * @return �X�e�[�^�X �i���������j
     */
    public String getKeyStatusCd() {
        return keyStatusCd;
    }

    /**
     * �X�e�[�^�X �i���������j��ݒ肷��B<br/>
     * <br/>
     * @param statusCd �X�e�[�^�X �i���������j
     */
    public void setKeyStatusCd(String keyStatusCd) {
        this.keyStatusCd = keyStatusCd;
    }
    /**
     * ���߃t���O���擾����B<br/>
     * <br/>
     * @return ���߃t���O
     */
	public String getCommand() {
		return command;
	}

	 /**
     * ���߃t���O��ݒ肷��B<br/>
     * <br/>
     * @param command ���߃t���O
     */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
     * �s���{��CD���擾����B<br/>
     * <br/>
     * @return prefCd �s���{��CD
     */
	public String getPrefCd() {
		return prefCd;
	}

	/**
     * �s���{��CD��ݒ肷��B<br/>
     * <br/>
     * @param prefCd �s���{��CD
     */
	public void setPrefCd(String prefCd) {
		this.prefCd = prefCd;
	}

	/**
     * �������CD���擾����B<br/>
     * <br/>
     * @return housingKindCd �������CD
     */
	public String getHousingKindCd() {
		return housingKindCd;
	}

	/**
     * �������CD��ݒ肷��B<br/>
     * <br/>
     * @param housingKindCd �������CD
     */
	public void setHousingKindCd(String housingKindCd) {
		this.housingKindCd = housingKindCd;
	}

	/**
     * �s�撬��CD���擾����B<br/>
     * <br/>
     * @return addressCd �s�撬��CD
     */
	public String getAddressCd() {
		return addressCd;
	}

	/**
     * �s�撬��CD��ݒ肷��B<br/>
     * <br/>
     * @param addressCd �s�撬��CD
     */
	public void setAddressCd(String addressCd) {
		this.addressCd = addressCd;
	}

	/**
     * �H��CD���擾����B<br/>
     * <br/>
     * @return routeCd �H��CD
     */
	public String getRouteCd() {
		return routeCd;
	}

	/**
     * �H��CD��ݒ肷��B<br/>
     * <br/>
     * @param routeCd �H��CD
     */
	public void setRouteCd(String routeCd) {
		this.routeCd = routeCd;
	}

	/**
     * ���t�H�[�����i���݂Ō���������擾����B<br/>
     * <br/>
     * @return reformPriceCheck ���t�H�[�����i���݂Ō�������
     */
	public String getReformPriceCheck() {
		return reformPriceCheck;
	}

	/**
     * ���t�H�[�����i���݂Ō��������ݒ肷��B<br/>
     * <br/>
     * @param reformPriceCheck ���t�H�[�����i���݂Ō�������
     */
	public void setReformPriceCheck(String reformPriceCheck) {
		this.reformPriceCheck = reformPriceCheck;
	}

	/**
     * �z�N�����擾����B<br/>
     * <br/>
     * @return keyCompDate �z�N��
     */
	public String getKeyCompDate() {
		return keyCompDate;
	}

	/**
     * �z�N����ݒ肷��B<br/>
     * <br/>
     * @param keyCompDate �z�N��
     */
	public void setKeyCompDate(String keyCompDate) {
		this.keyCompDate = keyCompDate;
	}

	/**
     * �������߂̃|�C���g���擾����B<br/>
     * <br/>
     * @return keyIconCd �������߂̃|�C���g
     */
	public String getKeyIconCd() {
		return keyIconCd;
	}

	/**
     * �������߂̃|�C���g��ݒ肷��B<br/>
     * <br/>
     * @param keyIconCd �������߂̃|�C���g
     */
	public void setKeyIconCd(String keyIconCd) {
		this.keyIconCd = keyIconCd;
	}

	/**
     * �������߂̃|�C���g���擾����B<br/>
     * <br/>
     * @return keyIconCd �������߂̃|�C���g
     */
	public String[] getIconCd() {
		return iconCd;
	}

	/**
     * �������߂̃|�C���g��ݒ肷��B<br/>
     * <br/>
     * @param keyIconCd �������߂̃|�C���g
     */
	public void setIconCd(String[] iconCd) {
		this.iconCd = iconCd;
	}

	/**
     * ���i(�\�[�g����)���擾����B<br/>
     * <br/>
     * @return sortPriceValue ���i(�\�[�g����)
     */
	public String getSortPriceValue() {
		return sortPriceValue;
	}

	/**
     * ���i(�\�[�g����)��ݒ肷��B<br/>
     * <br/>
     * @param sortPriceValue ���i(�\�[�g����)
     */
	public void setSortPriceValue(String sortPriceValue) {
		this.sortPriceValue = sortPriceValue;
	}

	/**
     * �z�N���Â����i�\�[�g�����j���擾����B<br/>
     * <br/>
     * @return sortUpdDateValue �z�N���Â����i�\�[�g�����j
     */
	public String getSortUpdDateValue() {
		return sortUpdDateValue;
	}

	/**
     * �z�N���Â����i�\�[�g�����j��ݒ肷��B<br/>
     * <br/>
     * @param sortUpdDateValue �z�N���Â����i�\�[�g�����j
     */
	public void setSortUpdDateValue(String sortUpdDateValue) {
		this.sortUpdDateValue = sortUpdDateValue;
	}

	/**
     * �z�N���Â����i�\�[�g�����j���擾����B<br/>
     * <br/>
     * @return sortBuildDateValue �z�N���Â����i�\�[�g�����j
     */
	public String getSortBuildDateValue() {
		return sortBuildDateValue;
	}

	/**
     * �z�N���Â����i�\�[�g�����j��ݒ肷��B<br/>
     * <br/>
     * @param sortBuildDateValue �z�N���Â����i�\�[�g�����j
     */
	public void setSortBuildDateValue(String sortBuildDateValue) {
		this.sortBuildDateValue = sortBuildDateValue;
	}

	/**
     * �w����̋����i�\�[�g�����j���擾����B<br/>
     * <br/>
     * @return sortWalkTimeValue �w����̋����i�\�[�g�����j
     */
	public String getSortWalkTimeValue() {
		return sortWalkTimeValue;
	}

	/**
     * �w����̋����i�\�[�g�����j��ݒ肷��B<br/>
     * <br/>
     * @param sortWalkTimeValue �w����̋����i�\�[�g�����j
     */
	public void setSortWalkTimeValue(String sortWalkTimeValue) {
		this.sortWalkTimeValue = sortWalkTimeValue;
	}

	/**
     * ����/���i�E�����i���������j���擾����B<br/>
     * <br/>
     * @return priceLower ����/���i�E�����i���������j
     */
	public String getPriceLower() {
		return priceLower;
	}

	/**
     * ����/���i�E�����i���������j��ݒ肷��B<br/>
     * <br/>
     * @param priceLower ����/���i�E�����i���������j
     */
	public void setPriceLower(String priceLower) {
		this.priceLower = priceLower;
	}

	/**
     * ����/���i�E����i���������j���擾����B<br/>
     * <br/>
     * @return priceUpper ����/���i�E����i���������j
     */
	public String getPriceUpper() {
		return priceUpper;
	}

	/**
     * ����/���i�E����i���������j��ݒ肷��B<br/>
     * <br/>
     * @param priceUpper ����/���i�E����i���������j
     */
	public void setPriceUpper(String priceUpper) {
		this.priceUpper = priceUpper;
	}

	/**
     * �y�n�ʐρE�����i���������j���擾����B<br/>
     * <br/>
     * @return landAreaLower �y�n�ʐρE�����i���������j
     */
	public String getLandAreaLower() {
		return landAreaLower;
	}

	/**
     * �y�n�ʐρE�����i���������j��ݒ肷��B<br/>
     * <br/>
     * @param landAreaLower �y�n�ʐρE�����i���������j
     */
	public void setLandAreaLower(String landAreaLower) {
		this.landAreaLower = landAreaLower;
	}

	/**
     * �y�n�ʐρE����i���������j���擾����B<br/>
     * <br/>
     * @return landAreaUpper �y�n�ʐρE����i���������j
     */
	public String getLandAreaUpper() {
		return landAreaUpper;
	}

	/**
     * �y�n�ʐρE����i���������j��ݒ肷��B<br/>
     * <br/>
     * @param landAreaUpper �y�n�ʐρE����i���������j
     */
	public void setLandAreaUpper(String landAreaUpper) {
		this.landAreaUpper = landAreaUpper;
	}

	/**
     * ��L�ʐρE�����i���������j���擾����B<br/>
     * <br/>
     * @return personalAreaLower ��L�ʐρE�����i���������j
     */
	public String getPersonalAreaLower() {
		return personalAreaLower;
	}

	/**
     * ��L�ʐρE�����i���������j��ݒ肷��B<br/>
     * <br/>
     * @param personalAreaLower ��L�ʐρE�����i���������j
     */
	public void setPersonalAreaLower(String personalAreaLower) {
		this.personalAreaLower = personalAreaLower;
	}

	/**
     * ��L�ʐρE����i���������j���擾����B<br/>
     * <br/>
     * @return personalAreaUpper ��L�ʐρE����i���������j
     */
	public String getPersonalAreaUpper() {
		return personalAreaUpper;
	}

	/**
     * ��L�ʐρE����i���������j��ݒ肷��B<br/>
     * <br/>
     * @param personalAreaUpper ��L�ʐρE����i���������j
     */
	public void setPersonalAreaUpper(String personalAreaUpper) {
		this.personalAreaUpper = personalAreaUpper;
	}

	/**
     * �����ʐρE�����i���������j���擾����B<br/>
     * <br/>
     * @return buildingAreaLower �����ʐρE�����i���������j
     */
	public String getBuildingAreaLower() {
		return buildingAreaLower;
	}

	/**
     * �����ʐρE�����i���������j��ݒ肷��B<br/>
     * <br/>
     * @param buildingAreaLower �����ʐρE�����i���������j
     */
	public void setBuildingAreaLower(String buildingAreaLower) {
		this.buildingAreaLower = buildingAreaLower;
	}

	/**
     * �����ʐρE����i���������j���擾����B<br/>
     * <br/>
     * @return buildingAreaUpper �����ʐρE����i���������j
     */
	public String getBuildingAreaUpper() {
		return buildingAreaUpper;
	}

	/**
     * �����ʐρE����i���������j��ݒ肷��B<br/>
     * <br/>
     * @param buildingAreaUpper �����ʐρE����i���������j
     */
	public void setBuildingAreaUpper(String buildingAreaUpper) {
		this.buildingAreaUpper = buildingAreaUpper;
	}

	/**
     * �Ԏ��CD�i���������j���擾����B<br/>
     * <br/>
     * @return layoutCd �Ԏ��CD�i���������j
     */
	public String[] getLayoutCd() {
		return layoutCd;
	}

	/**
     * �Ԏ��CD�i���������j��ݒ肷��B<br/>
     * <br/>
     * @param layoutCd �Ԏ��CD�i���������j
     */
	public void setLayoutCd(String[] layoutCd) {
		this.layoutCd = layoutCd;
	}

	/**
     * ���n�����i���������j���擾����B<br/>
     * <br/>
     * @return moveinTiming ���n�����i���������j
     */
	public String getMoveinTiming() {
		return moveinTiming;
	}

	/**
     * ���n�����i���������j��ݒ肷��B<br/>
     * <br/>
     * @param moveinTiming ���n�����i���������j
     */
	public void setMoveinTiming(String moveinTiming) {
		this.moveinTiming = moveinTiming;
	}

	/**
     * ���r�ی��i���������j���擾����B<br/>
     * <br/>
     * @return insurExist ���r�ی��i���������j
     */
	public String getInsurExist() {
		return insurExist;
	}

	/**
     * ���r�ی��i���������j��ݒ肷��B<br/>
     * <br/>
     * @param insurExist ���r�ی��i���������j
     */
	public void setInsurExist(String insurExist) {
		this.insurExist = insurExist;
	}

	/**
     * ���݊K���i���������j���擾����B<br/>
     * <br/>
     * @return partSrchCdFloor ���݊K���i���������j
     */
	public String getPartSrchCdFloorArray() {
		return partSrchCdFloorArray;
	}

	/**
     * ���݊K���i���������j��ݒ肷��B<br/>
     * <br/>
     * @param partSrchCdFloor ���݊K���i���������j
     */
	public void setPartSrchCdFloorArray(String partSrchCdFloorArray) {
		this.partSrchCdFloorArray = partSrchCdFloorArray;
	}

	/**
     * �w�k���i���������j���擾����B<br/>
     * <br/>
     * @return partSrchCdWalk �w�k���i���������j
     */
	public String getPartSrchCdWalkArray() {
		return partSrchCdWalkArray;
	}

	/**
     * �w�k���i���������j��ݒ肷��B<br/>
     * <br/>
     * @param partSrchCdWalk �w�k���i���������j
     */
	public void setPartSrchCdWalkArray(String partSrchCdWalkArray) {
		this.partSrchCdWalkArray = partSrchCdWalkArray;
	}

	/**
     * �����摜���A�������� �i���������j���擾����B<br/>
     * <br/>
     * @return partSrchCd �����摜���A�������� �i���������j
     */
	public String getPartSrchCdArray() {
		return partSrchCdArray;
	}

	/**
     * �����摜���A�������� �i���������j��ݒ肷��B<br/>
     * <br/>
     * @param partSrchCd �����摜���A�������� �i���������j
     */
	public void setPartSrchCdArray(String partSrchCdArray) {
		this.partSrchCdArray = partSrchCdArray;
	}

	/**
     * �s�撬�������擾����B<br/>
     * <br/>
     * @return hidAddressName �s�撬����
     */
	public String getHidAddressName() {
		return hidAddressName;
	}

	/**
     * �s�撬������ݒ肷��B<br/>
     * <br/>
     * @param hidAddressName �s�撬����
     */
	public void setHidAddressName(String hidAddressName) {
		this.hidAddressName = hidAddressName;
	}

	/**
     * �N���A�t���O���擾����B<br/>
     * <br/>
     * @return clearFlg �N���A�t���O
     */
	public String getClearFlg() {
		return clearFlg;
	}

	/**
     * �N���A�t���O��ݒ肷��B<br/>
     * <br/>
     * @param clearFlg �N���A�t���O
     */
	public void setClearFlg(String clearFlg) {
		this.clearFlg = clearFlg;
	}

	/**
	 * �L�[���[�h ���擾����B<br/>
	 * <br/>
	 * @return �L�[���[�h
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * �L�[���[�h ��ݒ肷��B<br/>
	 * <br/>
	 * @param keywords
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * ���� ���擾����B<br/>
	 * <br/>
	 * @return ����
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * ���� ��ݒ肷��B<br/>
	 * <br/>
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
//	/**
//     * @return the reformType
//     */
//    public String getKeyReformType() {
//        return keyReformType;
//    }
//
//    /**
//     * @param reformType the reformType to set
//     */
//    public void setKeyReformType(String reformType) {
//        this.keyReformType = reformType;
//    }

    /**
     * ���ʃR�[�h�ϊ��������擾����B<br/>
     * <br/>
     * @return codeLookupManager ���ʃR�[�h�ϊ�����
     */
	public CodeLookupManager getCodeLookupManager() {
		return codeLookupManager;
	}

	/**
     * ���ʃR�[�h�ϊ�������ݒ肷��B<br/>
     * <br/>
     * @param codeLookupManager ���ʃR�[�h�ϊ�����
     */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}
	/**
     * �o���f�[�V��������<br/>
     * ���N�G�X�g�p�����[�^�̃o���f�[�V�������s��<br/>
     * <br/>
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     * @return ���펞 true�A�G���[�� false
     */
    @Override
    public boolean validate(List<ValidationFailure> errors) {

        int startSize = errors.size();

        // �����ԍ����̓`�F�b�N
        validKeyHousingCd(errors);

        // ���������̓`�F�b�N
        validKeyDisplayHousingName(errors);

        // �o�^�J�n�����̓`�F�b�N
        validInsDateStart(errors);

        // �o�^�I�������̓`�F�b�N
        validInsDateEnd(errors);

        // �o�^�J�n�� < �o�^�I�����̃`�F�b�N
        validInsDateCom(errors);

        // �X�V�������̓`�F�b�N
        validUpdDate(errors);

        // ����ԍ����̓`�F�b�N
        validUserId(errors);

        // ���J�敪���̓`�F�b�N
        validHiddenFlg(errors);

        // �X�e�[�^�X���̓`�F�b�N
        validStatusCd(errors);

        return (startSize == errors.size());
    }

    /**
     * �����ԍ� �o���f�[�V����<br/>
     * �E�����`�F�b�N
     * �E�����`�F�b�N
     * <br/>
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
    protected void validKeyHousingCd(List<ValidationFailure> errors) {
        // �����ԍ����̓`�F�b�N
        ValidationChain valid = new ValidationChain("housingList.input.keyHousingCd", this.getKeyHousingCd());
        // �����`�F�b�N
        valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("housing.input.housingCd", 10)));
        // �����`�F�b�N
        valid.addValidation(new AlphanumericOnlyValidation());
        valid.validate(errors);
    }

    /**
     * ������ �o���f�[�V����<br/>
     * �E�����`�F�b�N
     * <br/>
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
    protected void validKeyDisplayHousingName(List<ValidationFailure> errors) {
        // ���������̓`�F�b�N
        ValidationChain valid = new ValidationChain("housingList.input.keyDisplayHousingName",
                this.getKeyDisplayHousingName());
        // �����`�F�b�N
        valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("housing.input.displayHousingName", 25)));
        valid.validate(errors);
    }

    /**
     * �o�^�J�n�� �o���f�[�V����<br/>
     * �E���t�����`�F�b�N
     * <br/>
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
    protected void validInsDateStart(List<ValidationFailure> errors) {
        // �o�^�J�n�����̓`�F�b�N
        ValidationChain valid = new ValidationChain("housingList.input.insDateStart", this.keyInsDateStart);
        // ���t�����`�F�b�N
        valid.addValidation(new ValidDateValidation("yyyy/MM/dd"));
        valid.validate(errors);
    }

    /**
     * �o�^�I���� �o���f�[�V����<br/>
     * �E���t�����`�F�b�N
     * <br/>
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
    protected void validInsDateEnd(List<ValidationFailure> errors) {
        // �o�^�I�������̓`�F�b�N
        ValidationChain valid = new ValidationChain("housingList.input.insDateEnd", this.keyInsDateEnd);
        // ���t�����`�F�b�N
        valid.addValidation(new ValidDateValidation("yyyy/MM/dd"));
        valid.validate(errors);
    }

    /**
     * �o�^�J�n�� < �o�^�I���� �o���f�[�V����<br/>
     * �E���t��r�`�F�b�N
     * <br/>
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
    protected void validInsDateCom(List<ValidationFailure> errors) {
        // ���t��r�`�F�b�N
        ValidationChain valid = new ValidationChain("housingList.input.insDateStart", this.keyInsDateStart);
        // ���t�����`�F�b�N
        valid.addValidation(new DateFromToValidation("yyyy/MM/dd", "�o�^�I����", this.keyInsDateEnd));
        valid.validate(errors);
    }

    /**
     * �X�V���� �o���f�[�V����<br/>
     * �E�p�^�[���`�F�b�N
     * <br/>
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
    protected void validUpdDate(List<ValidationFailure> errors) {
        // �X�V�������̓`�F�b�N
        ValidationChain valid = new ValidationChain("housingList.input.updDate", this.keyUpdDate);
        // �p�^�[���`�F�b�N
        valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "housingListUpdDate"));
        valid.validate(errors);
    }

    /**
     * ����ԍ� �o���f�[�V����<br/>
     * �E�p�����`�F�b�N
     * <br/>
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
    protected void validUserId(List<ValidationFailure> errors) {
        // �X�V�������̓`�F�b�N
        ValidationChain valid = new ValidationChain("housingList.input.userId", this.keyUserId);
        // �p�����`�F�b�N
        valid.addValidation(new AlphanumericOnlyValidation());
        valid.validate(errors);
    }

    /**
     * ���J�敪 �o���f�[�V����<br/>
     * �E�p�^�[���`�F�b�N
     * <br/>
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
    protected void validHiddenFlg(List<ValidationFailure> errors) {
        // �X�V�������̓`�F�b�N
        ValidationChain valid = new ValidationChain("housingList.input.hiddenFlg", this.keyHiddenFlg);
        // �p�^�[���`�F�b�N
        valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "hiddenFlg"));
        valid.validate(errors);
    }

    /**
     * �X�e�[�^�X �o���f�[�V����<br/>
     * �E�p�^�[���`�F�b�N
     * <br/>
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
    protected void validStatusCd(List<ValidationFailure> errors) {
        // �X�V�������̓`�F�b�N
        ValidationChain valid = new ValidationChain("housingList.input.statusCd", this.keyStatusCd);
        // �p�^�[���`�F�b�N
        valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "statusCd"));
        valid.validate(errors);
    }

    /**
     * �n���ꂽ�o���[�I�u�W�F�N�g���� Form �֏����l��ݒ肷��B<br/>
     * <br/>
     * @param prefMstList List<PrefMst>�@�����������A�s���{���}�X�^�Ǘ��p�o���[�I�u�W�F�N�g
     * @param full false �̏ꍇ�A�s���{���}�X�^���X�g���̂ݐݒ肷��B�@true �̏ꍇ�͂��ׂĐݒ肵�܂��B
     *
     */
    public void setDefaultData(List<PrefMst> prefMstList, Map<String, Object> model, boolean full) {

        if (full) {
            // �X�V������ݒ�
            this.keyUpdDate = "01";
            // ���J�敪��ݒ�
            this.keyHiddenFlg = "1";
        } else {
        	// �\�[�g����
        	this.setKeyOrderType("0");
		}
        model.put("prefMst", prefMstList);
    }
}