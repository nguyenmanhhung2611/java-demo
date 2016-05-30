package jp.co.transcosmos.dm3.corePana.model.housing.form;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.validation.LengthValueValidation;
import jp.co.transcosmos.dm3.corePana.validation.PonitNumberValidation;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.NumberValidation;
import jp.co.transcosmos.dm3.validation.NumericValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * ������{��񃁃��e�i���X�̓��̓p�����[�^����p�t�H�[��.
 * <p>
 *
 * <pre>
 * �S����      �C����     �C�����e
 * ------------ ----------- -----------------------------------------------------
 * fan         2015.04.11  �V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */

public class PanaHousingInfoForm implements Validateable {

    /** ���ʃR�[�h�ϊ����� */
    private CodeLookupManager codeLookupManager;

    /**
     * �f�t�H���g�R���X�g���N�^�[<br/>
     * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
     * <br/>
     */
    PanaHousingInfoForm() {
        super();
    }

    /**
     * �R���X�g���N�^�[<br/>
     * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
     * <br/>
     * @param�@codeLookupManager�@���ʃR�[�h�ϊ�����
     */
    PanaHousingInfoForm(CodeLookupManager codeLookupManager) {
        super();
        this.codeLookupManager = codeLookupManager;
    }

    /** �V�X�e������CD */
    private String sysHousingCd;
    /** �V�X�e������CD */
    private String sysBuildingCd;
    /** �����ԍ� */
    private String housingCd;
    /** �������� */
    private String displayHousingName;
    /** ������� */
    private String housingKindCd;
    /** ���i */
    private String price;
    /** �z�N */
    private String compDate;
    /** �Ԏ�� */
    private String layoutCd;
    /** �Z��_�X�֔ԍ� */
    private String zip;

    /** �s���{��CD */
    private String prefCd;
    /** �s���{���� */
    private String prefName;
    /** �s�撬��CD */
    private String addressCd;
    /** �s�撬���� */
    private String addressName;

    /** �Z���P */
    private String addressOther1;
    /** �Z���Q */
    private String addressOther2;

    /** ����CD */
    private String[] defaultRouteCd;
    /** �H�����E�S����Еt(�v���r���[�p) */
    private String[] routeNameRr;
    /** ������ */
    private String[] routeName;
    /** �wCD */
    private String[] stationCd;
    /** �w�� */
    private String[] stationName;

    /** ������ */
    private String[] oldRouteName;
    /** �w�� */
    private String[] oldStationName;

    /** ����CD */
    private String[] oldDefaultRouteCd;
    /** �wCD */
    private String[] oldStationCd;

    /** �o�X��Ж� */
    private String[] busCompany;
    /** �o�X�₩��̓k������ */
    private String[] timeFromBusStop;

    /** �y�n�ʐ� */
    private String landArea;
    /** �y�n�ʐ�_�ؐ� */
    private String landAreaCon;
    /** �y�n�ʐ�_�R�����g */
    private String landAreaMemo;
    /** ��L�ʐ� */
    private String personalArea;
    /** ��L�ʐ�_�ؐ� */
    private String personalAreaCon;
    /** ��L�ʐ�_�R�����g */
    private String personalAreaMemo;
    /** �����ʐ� */
    private String buildingArea;
    /** �����ʐ�_�ؐ� */
    private String buildingAreaCon;
    /** �����ʐ�_�R�����g */
    private String buildingAreaMemo;
    private String command;
    private String comflg;
	/** ���؂���_�⑫ */
	private String coverageMemo;
	/** �e�ϗ�_�⑫ */
	private String buildingRateMemo;


	public String[] getRouteNameRr() {
		return routeNameRr;
	}

	public void setRouteNameRr(String[] routeNameRr) {
		this.routeNameRr = routeNameRr;
	}

	/**
     * ������ �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return ������ �p�����[�^
     */
	public String[] getOldRouteName() {
		return oldRouteName;
	}
	/**
     * ������ �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM oldRouteName ������ �p�����[�^
     */
	public void setOldRouteName(String[] oldRouteName) {
		this.oldRouteName = oldRouteName;
	}
	/**
     * �w�� �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �w�� �p�����[�^
     */
	public String[] getOldStationName() {
		return oldStationName;
	}
	/**
     * �w�� �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM oldStationName �w�� �p�����[�^
     */
	public void setOldStationName(String[] oldStationName) {
		this.oldStationName = oldStationName;
	}
	/**
     * ������CD �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return ������CD �p�����[�^
     */
	public String[] getOldDefaultRouteCd() {
		return oldDefaultRouteCd;
	}
	/**
     * ������CD �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM oldDefaultRouteCd ������CD �p�����[�^
     */
	public void setOldDefaultRouteCd(String[] oldDefaultRouteCd) {
		this.oldDefaultRouteCd = oldDefaultRouteCd;
	}
	/**
     * �w��CD �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �w��CD �p�����[�^
     */
	public String[] getOldStationCd() {
		return oldStationCd;
	}
	/**
     * �w��CD �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM oldStationCd �w��CD �p�����[�^
     */
	public void setOldStationCd(String[] oldStationCd) {
		this.oldStationCd = oldStationCd;
	}

	/**
     * �V�X�e������CD �p�����[�^���擾����B<br/>
     * �V�X�e������CD �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �V�X�e������CD �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �V�X�e������CD �p�����[�^
     */
    public String getSysBuildingCd() {
        return sysBuildingCd;
    }

    /**
     * �V�X�e������CD �p�����[�^��ݒ肷��B<BR/>
     * �V�X�e������CD �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �V�X�e������CD �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM sysBuildingCd �V�X�e������CD �p�����[�^
     */
    public void setSysBuildingCd(String sysBuildingCd) {
        this.sysBuildingCd = sysBuildingCd;
    }

    /**
     * �V�X�e������CD �p�����[�^���擾����B<br/>
     * �V�X�e������CD �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �V�X�e������CD �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �V�X�e������CD �p�����[�^
     */
    public String getSysHousingCd() {
        return sysHousingCd;
    }

    /**
     * �V�X�e������CD �p�����[�^��ݒ肷��B<BR/>
     * �V�X�e������CD �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �V�X�e������CD �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM sysHousingCd �V�X�e������CD �p�����[�^
     */
    public void setSysHousingCd(String sysHousingCd) {
        this.sysHousingCd = sysHousingCd;
    }

    /**
     * �����ԍ� �p�����[�^���擾����B<br/>
     * �����ԍ� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �����ԍ� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �����ԍ� �p�����[�^
     */
    public String getHousingCd() {
        return housingCd;
    }

    /**
     * �����ԍ� �p�����[�^��ݒ肷��B<BR/>
     * �����ԍ� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �����ԍ� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM housingCd �����ԍ� �p�����[�^
     */
    public void setHousingCd(String housingCd) {
        this.housingCd = housingCd;
    }

    /**
     * �������� �p�����[�^���擾����B<br/>
     * �������� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �������� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �������� �p�����[�^
     */
    public String getDisplayHousingName() {
        return displayHousingName;
    }

    /**
     * �������� �p�����[�^��ݒ肷��B<BR/>
     * �������� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �������� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM displayHousingName �������� �p�����[�^
     */
    public void setDisplayHousingName(String displayHousingName) {
        this.displayHousingName = displayHousingName;
    }

    /**
     * ������� �p�����[�^���擾����B<br/>
     * ������� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ������� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return ������� �p�����[�^
     */
    public String getHousingKindCd() {
        return housingKindCd;
    }

    /**
     * ������� �p�����[�^��ݒ肷��B<BR/>
     * ������� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ������� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM housingKindCd ������� �p�����[�^
     */
    public void setHousingKindCd(String housingKindCd) {
        this.housingKindCd = housingKindCd;
    }

    /**
     * ���i �p�����[�^���擾����B<br/>
     * ���i �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���i �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return ���i �p�����[�^
     */
    public String getPrice() {
        return price;
    }

    /**
     * ���i �p�����[�^��ݒ肷��B<BR/>
     * ���i �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���i �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM price ���i �p�����[�^
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * �z�N �p�����[�^���擾����B<br/>
     * �z�N �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �z�N �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �V�X�e�����t�H�[��CD �p�����[�^
     */
    public String getCompDate() {
        return compDate;
    }

    /**
     * �z�N �p�����[�^��ݒ肷��B<BR/>
     * �z�N �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �z�N �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM compDate �z�N �p�����[�^
     */
    public void setCompDate(String compDate) {
        this.compDate = compDate;
    }

    /**
     * �Ԏ�� �p�����[�^���擾����B<br/>
     * �Ԏ�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �Ԏ�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �Ԏ�� �p�����[�^
     */
    public String getLayoutCd() {
        return layoutCd;
    }

    /**
     * �Ԏ�� �p�����[�^��ݒ肷��B<BR/>
     * �Ԏ�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �Ԏ�� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM layoutCd �Ԏ�� �p�����[�^
     */
    public void setLayoutCd(String layoutCd) {
        this.layoutCd = layoutCd;
    }

    /**
     * �Z��_�X�֔ԍ� �p�����[�^���擾����B<br/>
     * �Z��_�X�֔ԍ� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �Z��_�X�֔ԍ� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �Z��_�X�֔ԍ� �p�����[�^
     */
    public String getZip() {
        return zip;
    }

    /**
     * �Z��_�X�֔ԍ� �p�����[�^��ݒ肷��B<BR/>
     * �Z��_�X�֔ԍ� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �Z��_�X�֔ԍ� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM zip �Z��_�X�֔ԍ� �p�����[�^
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * �s���{��CD �p�����[�^���擾����B<br/>
     * �s���{��CD �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �s���{��CD �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �s���{��CD �p�����[�^
     */
    public String getPrefCd() {
        return prefCd;
    }

    /**
     * �s���{��CD �p�����[�^��ݒ肷��B<BR/>
     * �s���{��CD �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �s���{��CD �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM prefCd �s���{��CD �p�����[�^
     */
    public void setPrefCd(String prefCd) {
        this.prefCd = prefCd;
    }

    /**
     * �s���{���� �p�����[�^���擾����B<br/>
     * �s���{���� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �s���{���� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �V�X�e�����t�H�[��CD �p�����[�^
     */
    public String getPrefName() {
        return prefName;
    }

    /**
     * �s���{���� �p�����[�^��ݒ肷��B<BR/>
     * �s���{���� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �s���{���� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM prefName �s���{���� �p�����[�^
     */
    public void setPrefName(String prefName) {
        this.prefName = prefName;
    }

    /**
     * �s�撬��CD �p�����[�^���擾����B<br/>
     * �s�撬��CD �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �s�撬��CD �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �V�X�e�����t�H�[��CD �p�����[�^
     */
    public String getAddressCd() {
        return addressCd;
    }

    /**
     * �s�撬��CD �p�����[�^��ݒ肷��B<BR/>
     * �s�撬��CD �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �s�撬��CD �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM addressCd �s�撬��CD �p�����[�^
     */
    public void setAddressCd(String addressCd) {
        this.addressCd = addressCd;
    }

    /**
     * �s�撬���� �p�����[�^���擾����B<br/>
     * �s�撬���� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �s�撬���� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �s�撬���� �p�����[�^
     */
    public String getAddressName() {
        return addressName;
    }

    /**
     * �s�撬���� �p�����[�^��ݒ肷��B<BR/>
     * �s�撬���� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �s�撬���� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM addressName �s�撬���� �p�����[�^
     */
    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    /**
     * �Z���P �p�����[�^���擾����B<br/>
     * �Z���P �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �Z���P �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �Z���P �p�����[�^
     */
    public String getAddressOther1() {
        return addressOther1;
    }

    /**
     * �Z���P �p�����[�^��ݒ肷��B<BR/>
     * �Z���P �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �Z���P �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM addressOther1 �Z���P �p�����[�^
     */
    public void setAddressOther1(String addressOther1) {
        this.addressOther1 = addressOther1;
    }

    /**
     * �Z���Q �p�����[�^���擾����B<br/>
     * �Z���Q �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �Z���Q �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �Z���Q �p�����[�^
     */
    public String getAddressOther2() {
        return addressOther2;
    }

    /**
     * �Z���Q �p�����[�^��ݒ肷��B<BR/>
     * �Z���Q �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �Z���Q �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM sysReformCd �Z���Q �p�����[�^
     */
    public void setAddressOther2(String addressOther2) {
        this.addressOther2 = addressOther2;
    }

    /**
     * ����CD �p�����[�^���擾����B<br/>
     * ����CD �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ����CD �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return ����CD �p�����[�^
     */
    public String[] getDefaultRouteCd() {
        return defaultRouteCd;
    }

    /**
     * ����CD �p�����[�^��ݒ肷��B<BR/>
     * ����CD �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ����CD �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM defaultRouteCd ����CD �p�����[�^
     */
    public void setDefaultRouteCd(String[] defaultRouteCd) {
        this.defaultRouteCd = defaultRouteCd;
    }

    /**
     * ������ �p�����[�^���擾����B<br/>
     * ������ �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ������ �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return ������ �p�����[�^
     */
    public String[] getRouteName() {
        return routeName;
    }

    /**
     * ������ �p�����[�^��ݒ肷��B<BR/>
     * ������ �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ������ �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM routeName ������ �p�����[�^
     */
    public void setRouteName(String[] routeName) {
        this.routeName = routeName;
    }

    /**
     * �wCD �p�����[�^���擾����B<br/>
     * �wCD �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �wCD �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �wCD �p�����[�^
     */
    public String[] getStationCd() {
        return stationCd;
    }

    /**
     * �wCD �p�����[�^��ݒ肷��B<BR/>
     * �wCD �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �wCD �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM stationCd �wCD �p�����[�^
     */
    public void setStationCd(String[] stationCd) {
        this.stationCd = stationCd;
    }

    /**
     * �w�� �p�����[�^���擾����B<br/>
     * �w�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �w�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �w�� �p�����[�^
     */
    public String[] getStationName() {
        return stationName;
    }

    /**
     * �w�� �p�����[�^��ݒ肷��B<BR/>
     * �w�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �w�� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM stationName �w�� �p�����[�^
     */
    public void setStationName(String[] stationName) {
        this.stationName = stationName;
    }
    /**
     * �o�X��Ж��o�X�▼ �p�����[�^���擾����B<br/>
     * �o�X��Ж��o�X�▼ �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �o�X��Ж��o�X�▼ �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �o�X��Ж��o�X�▼ �p�����[�^
     */
    public String[] getBusCompany() {
		return busCompany;
	}
    /**
     * �o�X��Ж��o�X�▼ �p�����[�^��ݒ肷��B<BR/>
     * �o�X��Ж��o�X�▼ �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �o�X��Ж��o�X�▼ �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM busCompany �o�X��Ж��o�X�▼ �p�����[�^
     */
	public void setBusCompany(String[] busCompany) {
		this.busCompany = busCompany;
	}

	/**
     * �o�X�₩��̓k������ �p�����[�^���擾����B<br/>
     * �o�X�₩��̓k������ �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �o�X�₩��̓k������ �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �o�X�₩��̓k������ �p�����[�^
     */
    public String[] getTimeFromBusStop() {
        return timeFromBusStop;
    }

    /**
     * �o�X�₩��̓k������ �p�����[�^��ݒ肷��B<BR/>
     * �o�X�₩��̓k������ �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �o�X�₩��̓k������ �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM timeFromBusStop �o�X�₩��̓k������ �p�����[�^
     */
    public void setTimeFromBusStop(String[] timeFromBusStop) {
        this.timeFromBusStop = timeFromBusStop;
    }

    /**
     * �y�n�ʐ� �p�����[�^���擾����B<br/>
     * �y�n�ʐ� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �y�n�ʐ� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �y�n�ʐ� �p�����[�^
     */
    public String getLandArea() {
        return landArea;
    }

    /**
     * �y�n�ʐ� �p�����[�^��ݒ肷��B<BR/>
     * �y�n�ʐ� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �y�n�ʐ� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM landArea �y�n�ʐ� �p�����[�^
     */
    public void setLandArea(String landArea) {
        this.landArea = landArea;
    }

    /**
     * �y�n�ʐ�_�R�����g �p�����[�^���擾����B<br/>
     * �y�n�ʐ�_�R�����g �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �y�n�ʐ�_�R�����g �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �y�n�ʐ�_�R�����g �p�����[�^
     */
    public String getLandAreaMemo() {
        return landAreaMemo;
    }

    /**
     * �y�n�ʐ�_�R�����g �p�����[�^��ݒ肷��B<BR/>
     * �y�n�ʐ�_�R�����g �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �y�n�ʐ�_�R�����g �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM landAreaMemo �y�n�ʐ�_�R�����g �p�����[�^
     */
    public void setLandAreaMemo(String landAreaMemo) {
        this.landAreaMemo = landAreaMemo;
    }

    /**
     * ��L�ʐ� �p�����[�^���擾����B<br/>
     * ��L�ʐ� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p�����L�ʐ� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return ��L�ʐ� �p�����[�^
     */
    public String getPersonalArea() {
        return personalArea;
    }

    /**
     * ��L�ʐ� �p�����[�^��ݒ肷��B<BR/>
     * ��L�ʐ� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ��L�ʐ� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM personalArea ��L�ʐ� �p�����[�^
     */
    public void setPersonalArea(String personalArea) {
        this.personalArea = personalArea;
    }

    /**
     * ��L�ʐ�_�R�����g �p�����[�^���擾����B<br/>
     * ��L�ʐ�_�R�����g �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ��L�ʐ�_�R�����g �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return ��L�ʐ�_�R�����g �p�����[�^
     */
    public String getPersonalAreaMemo() {
        return personalAreaMemo;
    }

    /**
     * ��L�ʐ�_�R�����g �p�����[�^��ݒ肷��B<BR/>
     * ��L�ʐ�_�R�����g �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ��L�ʐ�_�R�����g �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM personalAreaMemo ��L�ʐ�_�R�����g �p�����[�^
     */
    public void setPersonalAreaMemo(String personalAreaMemo) {
        this.personalAreaMemo = personalAreaMemo;
    }

    /**
     * �����ʐ� �p�����[�^���擾����B<br/>
     * �����ʐ� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �����ʐ� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �V�X�e�����t�H�[��CD �p�����[�^
     */
    public String getBuildingArea() {
        return buildingArea;
    }

    /**
     * �����ʐ� �p�����[�^��ݒ肷��B<BR/>
     * �����ʐ� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �����ʐ� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM buildingArea �����ʐ� �p�����[�^
     */
    public void setBuildingArea(String buildingArea) {
        this.buildingArea = buildingArea;
    }

    /**
     * �����ʐ�_�R�����g �p�����[�^���擾����B<br/>
     * �����ʐ�_�R�����g �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �����ʐ�_�R�����g �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �����ʐ�_�R�����g �p�����[�^
     */
    public String getBuildingAreaMemo() {
        return buildingAreaMemo;
    }

    /**
     * �����ʐ�_�R�����g �p�����[�^��ݒ肷��B<BR/>
     * �����ʐ�_�R�����g �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �����ʐ�_�R�����g �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM buildingAreaMemo �����ʐ�_�R�����g �p�����[�^
     */
    public void setBuildingAreaMemo(String buildingAreaMemo) {
        this.buildingAreaMemo = buildingAreaMemo;
    }

    /**
     * Command �p�����[�^���擾����B<br/>
     * Command �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� Command �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return Command �p�����[�^
     */
    public String getCommand() {
        return command;
    }

    /**
     * Command �p�����[�^��ݒ肷��B<BR/>
     * Command �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� Command �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM command Command �p�����[�^
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * Comflg �p�����[�^���擾����B<br/>
     * Comflg �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� Comflg �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �V�X�e�����t�H�[��CD �p�����[�^
     */
    public String getComflg() {
        return comflg;
    }

    /**
     * Comflg �p�����[�^��ݒ肷��B<BR/>
     * Comflg �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� Comflg �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM comflg Comflg �p�����[�^
     */
    public void setComflg(String comflg) {
        this.comflg = comflg;
    }

    /**
     * �y�n�ʐ�_�ؐ� �p�����[�^���擾����B<br/>
     * �y�n�ʐ�_�ؐ� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �y�n�ʐ�_�ؐ� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �y�n�ʐ�_�ؐ� �p�����[�^
     */
    public String getLandAreaCon() {
        return landAreaCon;
    }

    /**
     * �y�n�ʐ�_�ؐ� �p�����[�^��ݒ肷��B<BR/>
     * �y�n�ʐ�_�ؐ� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �y�n�ʐ�_�ؐ� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM landAreaCon �y�n�ʐ�_�ؐ� �p�����[�^
     */
    public void setLandAreaCon(String landAreaCon) {
        this.landAreaCon = landAreaCon;
    }

    /**
     * ��L�ʐ�_�ؐ� �p�����[�^���擾����B<br/>
     * ��L�ʐ�_�ؐ� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ��L�ʐ�_�ؐ� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �V�X�e�����t�H�[��CD �p�����[�^
     */
    public String getPersonalAreaCon() {
        return personalAreaCon;
    }

    /**
     * ��L�ʐ�_�ؐ� �p�����[�^��ݒ肷��B<BR/>
     * ��L�ʐ�_�ؐ� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ��L�ʐ�_�ؐ� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM personalAreaCon ��L�ʐ�_�ؐ� �p�����[�^
     */
    public void setPersonalAreaCon(String personalAreaCon) {
        this.personalAreaCon = personalAreaCon;
    }

    /**
     * �����ʐ�_�ؐ� �p�����[�^���擾����B<br/>
     * �����ʐ�_�ؐ� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �����ʐ�_�ؐ� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �����ʐ�_�ؐ� �p�����[�^
     */
    public String getBuildingAreaCon() {
        return buildingAreaCon;
    }

    /**
     * �V�X�e�����t�H�[��CD �p�����[�^��ݒ肷��B<BR/>
     * �����ʐ�_�ؐ� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �����ʐ�_�ؐ� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM buildingAreaCon �����ʐ�_�ؐ� �p�����[�^
     */
    public void setBuildingAreaCon(String buildingAreaCon) {
        this.buildingAreaCon = buildingAreaCon;
    }

    /**
	 * @return coverageMemo
	 */
	public String getCoverageMemo() {
		return coverageMemo;
	}

	/**
	 * @param coverageMemo �Z�b�g���� coverageMemo
	 */
	public void setCoverageMemo(String coverageMemo) {
		this.coverageMemo = coverageMemo;
	}

	/**
	 * @return buildingRateMemo
	 */
	public String getBuildingRateMemo() {
		return buildingRateMemo;
	}

	/**
	 * @param buildingRateMemo �Z�b�g���� buildingRateMemo
	 */
	public void setBuildingRateMemo(String buildingRateMemo) {
		this.buildingRateMemo = buildingRateMemo;
	}

	@Override
    public boolean validate(List<ValidationFailure> errors) {
        int startSize = errors.size();

        if ("address".equals(this.getCommand())) {
			if(StringValidateUtil.isEmpty(getZip())){
				ValidationFailure vf = new ValidationFailure(
                        "housingInfoZipNotInput", "", "", null);
                errors.add(vf);
			}

            // �Z��_�X�֔ԍ�
            ValidationChain valzip = new ValidationChain("housingInfo.input.zip", getZip());
            // ���p����
            valzip.addValidation(new NumericValidation());
            // �ő包�� 7��
            valzip.addValidation(new LengthValueValidation(7));
            valzip.validate(errors);

            return (startSize == errors.size());
        }
        // ��������
        ValidationChain valdisplayHousingName = new ValidationChain("housingInfo.input.displayHousingName",
                getDisplayHousingName());
        // �K�{�`�F�b�N
        valdisplayHousingName.addValidation(new NullOrEmptyCheckValidation());
        // �ő包�� 25��
        valdisplayHousingName.addValidation(new MaxLengthValidation(25));
        valdisplayHousingName.validate(errors);

        // �������
        ValidationChain valhousingKindCd = new ValidationChain("housingInfo.input.housingKindCd", getHousingKindCd());
        // �p�^�[���`�F�b�N
        valhousingKindCd.addValidation(new CodeLookupValidation(this.codeLookupManager, "buildingInfo_housingKindCd"));
        valhousingKindCd.validate(errors);

        // ���i
        ValidationChain valprice = new ValidationChain("housingInfo.input.price", getPrice());
        // �K�{�`�F�b�N
        valprice.addValidation(new NullOrEmptyCheckValidation());
        // ���p����
        valprice.addValidation(new NumericValidation());
        // �ő包�� 11��
        valprice.addValidation(new MaxLengthValidation(11));
        valprice.validate(errors);

        // �z�N
        ValidationChain valcompDate = new ValidationChain("housingInfo.input.compDate", getCompDate());
        if(PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd()) || PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())){
	        // �K�{�`�F�b�N
	        valcompDate.addValidation(new NullOrEmptyCheckValidation());
        }
        valcompDate.validate(errors);
        // ���t�����`�F�b�N
        if (!StringValidateUtil.isEmpty(this.getCompDate())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			try {
				if (!sdf.format(sdf.parse(getCompDate().toString()+"01")).equals(getCompDate().toString()+"01")) {
					 ValidationFailure vf = new ValidationFailure("housingInfoCompDate", "housingInfo.input.compDate", "yyyyMM", null);
				     errors.add(vf);
				}
			} catch (ParseException e) {
				 ValidationFailure vf = new ValidationFailure("housingInfoCompDate", "housingInfo.input.compDate", "yyyyMM", null);
			     errors.add(vf);
			}
        }

        // �Ԏ��
        ValidationChain vallayoutCd = new ValidationChain("housingInfo.input.layoutCd", getLayoutCd());
        if(PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd()) || PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())){
	        // �K�{�`�F�b�N
	        vallayoutCd.addValidation(new NullOrEmptyCheckValidation());
        }
        // �p�^�[���`�F�b�N
        vallayoutCd.addValidation(new CodeLookupValidation(this.codeLookupManager, "layoutCd"));
        vallayoutCd.validate(errors);

        // �Z��_�X�֔ԍ�
        ValidationChain valzip = new ValidationChain("housingInfo.input.zip", getZip());
        // �K�{�`�F�b�N
        valzip.addValidation(new NullOrEmptyCheckValidation());
        // ���p����
        valzip.addValidation(new NumericValidation());
        // �ő包�� 7��
        valzip.addValidation(new LengthValueValidation(7));
        valzip.validate(errors);

        // ���ݒn�i�s���{���j
        ValidationChain valPrefCd = new ValidationChain("housingInfo.input.prefCd", getPrefCd());
        // �K�{�`�F�b�N
        valPrefCd.addValidation(new NullOrEmptyCheckValidation());
        valPrefCd.validate(errors);

        // ���ݒn�i�s�撬���j
        ValidationChain valAddressCd = new ValidationChain("housingInfo.input.addressCd", getAddressCd());
        // �K�{�`�F�b�N
        valAddressCd.addValidation(new NullOrEmptyCheckValidation());
        valAddressCd.validate(errors);

        // �Z���P
        ValidationChain valaddressOther1 = new ValidationChain("housingInfo.input.addressOther1", getAddressOther1());
        // �K�{�`�F�b�N
        valaddressOther1.addValidation(new NullOrEmptyCheckValidation());
        // �ő包�� 30��
        valaddressOther1.addValidation(new MaxLengthValidation(30));
        valaddressOther1.validate(errors);

        // �Z���Q
        ValidationChain valaddressOther2 = new ValidationChain("housingInfo.input.addressOther2", getAddressOther2());
        // �ő包�� 30��
        valaddressOther2.addValidation(new MaxLengthValidation(30));
        valaddressOther2.validate(errors);

        if(this.getDefaultRouteCd() !=null){
        	for (int i = 0; i < this.getDefaultRouteCd().length; i++) {

                // �o�X�▼
                ValidationChain valbusCompany = new ValidationChain("housingInfo.input.busCompany"+(i+1), getBusCompany()[i]);
                // �ő包�� 40��
                valbusCompany.addValidation(new MaxLengthValidation(40));
                valbusCompany.validate(errors);

                // �o�X�₩��̓k������
                ValidationChain valtimeFromBusStop = new ValidationChain("housingInfo.input.timeFromBusStop"+(i+1),
                        getTimeFromBusStop()[i]);
                // ���p����
                valtimeFromBusStop.addValidation(new NumericValidation());
                // �ő包�� 3��
                valtimeFromBusStop.addValidation(new MaxLengthValidation(3));
                valtimeFromBusStop.validate(errors);

                if ("0".equals(getTimeFromBusStop()[i])) {
                    ValidationFailure vf = new ValidationFailure(
                            "landMarkDistanceError", "�o�X�₩��̓k������"+(i+1), "", null);
                    errors.add(vf);
                }
            }
        }

        // �����ʐ�
        ValidationChain valbuildingArea = new ValidationChain("housingInfo.input.buildingArea", getBuildingArea());
        if(PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())){
            // �K�{�`�F�b�N
            valbuildingArea.addValidation(new NullOrEmptyCheckValidation());
        }
        // ���p����
        valbuildingArea.addValidation(new NumberValidation());
        valbuildingArea.addValidation(new PonitNumberValidation(5,2));
        valbuildingArea.validate(errors);

        // �����ʐ�_�R�����g
        ValidationChain valbuildingAreaMemo = new ValidationChain("housingInfo.input.buildingAreaMemo",
                getBuildingAreaMemo());
        // �ő包�� 10��
        valbuildingAreaMemo.addValidation(new MaxLengthValidation(10));
        valbuildingAreaMemo.validate(errors);

        // �y�n�ʐ�
        ValidationChain vallandArea = new ValidationChain("housingInfo.input.landArea", getLandArea());
        if(PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd()) || PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())){
	        // �K�{�`�F�b�N
	        vallandArea.addValidation(new NullOrEmptyCheckValidation());
        }
        // ���p����
        vallandArea.addValidation(new NumberValidation());
        vallandArea.addValidation(new PonitNumberValidation(5,2));
        vallandArea.validate(errors);

        // �y�n�ʐ�_�R�����g
        ValidationChain vallandAreaMemo = new ValidationChain("housingInfo.input.landAreaMemo", getLandAreaMemo());
        // �ő包�� 10��
        vallandAreaMemo.addValidation(new MaxLengthValidation(10));
        vallandAreaMemo.validate(errors);

        // ��L�ʐ�
        ValidationChain valpersonalArea = new ValidationChain("housingInfo.input.personalArea", getPersonalArea());
        if(PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())){
	        // �K�{�`�F�b�N
	        valpersonalArea.addValidation(new NullOrEmptyCheckValidation());
        }
        // ���p����
        valpersonalArea.addValidation(new NumberValidation());
        valpersonalArea.addValidation(new PonitNumberValidation(5,2));
        valpersonalArea.validate(errors);

        // ��L�ʐ�_�R�����g
        ValidationChain valpersonalAreaMemo = new ValidationChain("housingInfo.input.personalAreaMemo",
                getPersonalAreaMemo());
        // �ő包�� 10��
        valpersonalAreaMemo.addValidation(new MaxLengthValidation(10));
        valpersonalAreaMemo.validate(errors);


        // �i�s���{�����X�g�F�����́@OR�@�s�撬�����X�g�F�����́jAND �Z���P�F���͂̏ꍇ
        if (!StringValidateUtil.isEmpty(this.getAddressOther1())) {
            if (StringValidateUtil.isEmpty(this.getPrefCd()) || StringValidateUtil.isEmpty(this.getAddressCd())) {
                String param[] = new String[] { "�s���{�����X�g�Ǝs�撬�����X�g" };
                ValidationFailure vf = new ValidationFailure(
                        "housingInfoInput", "housingInfo.input.addressOther1", "", param);
                errors.add(vf);
            }
        }
        // �i�s���{�����X�g�F�����́@OR�@�s�撬�����X�g�F������OR �Z���P�F�����́jAND �Z��2�F���͂̏ꍇ
        if (!StringValidateUtil.isEmpty(this.getAddressOther2())) {
            if (StringValidateUtil.isEmpty(this.getPrefCd()) || StringValidateUtil.isEmpty(this.getAddressCd())
                    || StringValidateUtil.isEmpty(this.getAddressOther1())) {
                String param[] = new String[] { "�s���{�����X�g�Ǝs�撬�����X�g�ƏZ���P" };
                ValidationFailure vf = new ValidationFailure(
                        "housingInfoInput", "housingInfo.input.addressOther2", "", param);
                errors.add(vf);
            }
        }
        if(this.getDefaultRouteCd() != null){
        	for (int i = 0; i < this.getDefaultRouteCd().length; i++) {

                // �i�������X�g_1�F�����́@OR�@�w���X�g_1�F�����́@OR�@�o�X�₩��̓k������_1�F�����́jAND �o�X��Ж��o�X�▼_1�F���͂̏ꍇ
                if (!StringValidateUtil.isEmpty(this.getBusCompany()[i])) {
                    if (StringValidateUtil.isEmpty(this.getDefaultRouteCd()[i])
                            || StringValidateUtil.isEmpty(this.getStationCd()[i])
                            || StringValidateUtil.isEmpty(this.getTimeFromBusStop()[i])) {
                        String param[] = new String[] { "�������X�g_"+String.valueOf(i+1)+"�Ɖw���X�g_"+String.valueOf(i+1)+"�Ɠk��_"+String.valueOf(i+1) };
                        ValidationFailure vf = new ValidationFailure(
                                "housingInfoInput", "�o�X"+"_"+ String.valueOf(i+1), "", param);
                        errors.add(vf);
                    }
                }
                //�i�������X�g_1�F�����́@OR�@�w���X�g_1�F�����́@�jAND �o�X�₩��̓k������_1�F���͂̏ꍇ
                if (!StringValidateUtil.isEmpty(this.getTimeFromBusStop()[i])) {
                    if (StringValidateUtil.isEmpty(this.getDefaultRouteCd()[i])
                            || StringValidateUtil.isEmpty(this.getStationCd()[i])) {
                        String param[] = new String[] { "�������X�g_"+String.valueOf(i+1)+"�Ɖw���X�g_"+String.valueOf(i+1) };
                        ValidationFailure vf = new ValidationFailure(
                                "housingInfoInput", "�k��"+"_"+ String.valueOf(i+1), "", param);
                        errors.add(vf);
                    }
                }
            }
        }
        // �����ʐρF������ AND �����ʐ�_�R�����g�F���͂̏ꍇ
        if (!StringValidateUtil.isEmpty(this.getBuildingAreaMemo())) {
            if (StringValidateUtil.isEmpty(this.getBuildingArea())) {
                String param[] = new String[] { "�����ʐ�" };
                ValidationFailure vf = new ValidationFailure(
                        "housingInfoInput", "housingInfo.input.buildingAreaMemo", "", param);
                errors.add(vf);
            }
        }
        // �y�n�ʐρF������ AND �y�n�ʐ�_�R�����g�F���͂̏ꍇ
        if (!StringValidateUtil.isEmpty(this.getLandAreaMemo())) {
            if (StringValidateUtil.isEmpty(this.getLandArea())) {
                String param[] = new String[] { "�y�n�ʐ�" };
                ValidationFailure vf = new ValidationFailure(
                        "housingInfoInput", "housingInfo.input.landAreaMemo", "", param);
                errors.add(vf);
            }
        }
        // ��L�ʐρF������ AND ��L�ʐ�_�R�����g�F���͂̏ꍇ
        if (!StringValidateUtil.isEmpty(this.getPersonalAreaMemo())) {
            if (StringValidateUtil.isEmpty(this.getPersonalArea())) {
                String param[] = new String[] { "��L�ʐ�" };
                ValidationFailure vf = new ValidationFailure(
                        "housingInfoInput", "housingInfo.input.personalAreaMemo", "", param);
                errors.add(vf);
            }
        }

        return (startSize == errors.size());
    }

	/**
     * �o�^�����I�u�W�F�N�g���쐬����B<br/>
     * �o�͗p�Ȃ̂Ńy�[�W���������O�����o�^�����𐶐�����B<br/>
     * <br/>
     *
     * @return �o�^�����I�u�W�F�N�g
     */
    public BuildingDtlInfo newToBuildingDtlInfo() {
    	BuildingDtlInfo buildingDtlInfo = new BuildingDtlInfo();

    	 //�V�X�e������CD
        buildingDtlInfo.setSysBuildingCd(this.sysBuildingCd);
        //�����ʐ�
        if(this.buildingArea != null){
            if(!StringValidateUtil.isEmpty(this.buildingArea)){
                buildingDtlInfo.setBuildingArea(BigDecimal.valueOf(Double.valueOf(this.buildingArea)));
            }
        }
        //�����ʐ�_�⑫
        buildingDtlInfo.setBuildingAreaMemo(formAndVo(this.getBuildingAreaMemo(),buildingDtlInfo.getBuildingAreaMemo()));

        //���؂���_�⑫
        buildingDtlInfo.setCoverageMemo(formAndVo(this.getCoverageMemo(),buildingDtlInfo.getCoverageMemo()));
        //�e�ϗ�_�⑫
        buildingDtlInfo.setBuildingRateMemo(formAndVo(this.getBuildingRateMemo(),buildingDtlInfo.getBuildingRateMemo()));

        return buildingDtlInfo;
    }

    /**
     * �t�H�[������VO�f�[�^���������ޏ����B<br/>
     * <br/>
     *
     * @param reformDtl
     *            �������ޑΏ�VO
     * @param i
     *            �A�b�v���[�h�Ώۉ摜���X�g�̃C���f�b�N�X
     *
     */
    public void copyToBuildingDtlInfo(BuildingDtlInfo buildingDtlInfo) {

        //�V�X�e������CD
        buildingDtlInfo.setSysBuildingCd(this.sysBuildingCd);
        //�����ʐ�
        if(this.buildingArea != null){
            if(!StringValidateUtil.isEmpty(this.buildingArea)){
                buildingDtlInfo.setBuildingArea(BigDecimal.valueOf(Double.valueOf(this.buildingArea)));
            }
        }
        //�����ʐ�_�⑫
        buildingDtlInfo.setBuildingAreaMemo(formAndVo(this.getBuildingAreaMemo(),buildingDtlInfo.getBuildingAreaMemo()));

        //���؂���_�⑫
        buildingDtlInfo.setCoverageMemo(formAndVo(this.getCoverageMemo(),buildingDtlInfo.getCoverageMemo()));
        //�e�ϗ�_�⑫
        buildingDtlInfo.setBuildingRateMemo(formAndVo(this.getBuildingRateMemo(),buildingDtlInfo.getBuildingRateMemo()));
    }
    /**
     * String�^�̒l��ݒ肷��B<br/>
     * <br/>
     * @return String
     */
	public String formAndVo(String form,String vo){
		String val;
		if(form == null && !StringValidateUtil.isEmpty(vo)){
			val=vo;
		}else{
			val=form;
		}
		return val;
	}
    /**
     * �n���ꂽ�o���[�I�u�W�F�N�g���� Form �֏����l��ݒ肷��B<br/>
     * <br/>
     * @param adminUser AdminUserInterface�@�����������A���[�U�[���Ǘ��p�o���[�I�u�W�F�N�g
     * @param adminRole AdminUserRoleInterface �������������[�U�[���[���Ǘ��p�o���[�I�u�W�F�N�g
     */
    public void setDefaultData(JoinResult housingInfo,JoinResult buildingInfo,List<JoinResult> buildingStationInfoList){

    	if(housingInfo != null){

			//�V�X�e������CD
			this.sysBuildingCd = ((HousingInfo)housingInfo.getItems().get("housingInfo")).getSysBuildingCd();
			//�����ԍ�
			this.housingCd=((HousingInfo)housingInfo.getItems().get("housingInfo")).getHousingCd();

			//��������
			this.displayHousingName=((HousingInfo)housingInfo.getItems().get("housingInfo")).getDisplayHousingName();
			//���i
			if(((HousingInfo)housingInfo.getItems().get("housingInfo")).getPrice() != null){
				this.price = String.valueOf(((HousingInfo)housingInfo.getItems().get("housingInfo")).getPrice());
			}
			//�Ԏ��
			this.layoutCd = ((HousingInfo)housingInfo.getItems().get("housingInfo")).getLayoutCd();
			//�y�n�ʐ�
			if(((HousingInfo)housingInfo.getItems().get("housingInfo")).getLandArea() !=null){
				this.landArea = String.valueOf(((HousingInfo)housingInfo.getItems().get("housingInfo")).getLandArea());
			}
			//�y�n�ʐ�_�R�����g
			this.landAreaMemo = ((HousingInfo)housingInfo.getItems().get("housingInfo")).getLandAreaMemo();
			//��L�ʐ�
			if(((HousingInfo)housingInfo.getItems().get("housingInfo")).getPersonalArea() !=null ){
				this.personalArea = String.valueOf(((HousingInfo)housingInfo.getItems().get("housingInfo")).getPersonalArea());
			}
			//��L�ʐ�_�R�����g
			this.personalAreaMemo = ((HousingInfo)housingInfo.getItems().get("housingInfo")).getPersonalAreaMemo();
		}
		if(buildingInfo !=null){
			//�������
			this.housingKindCd = ((BuildingInfo)buildingInfo.getItems().get("buildingInfo")).getHousingKindCd();
			//�z�N
			if(((BuildingInfo)buildingInfo.getItems().get("buildingInfo")).getCompDate()!=null){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
				this.compDate = String.valueOf(sdf.format(((BuildingInfo)buildingInfo.getItems().get("buildingInfo")).getCompDate())).substring(0, 6);
			}
			//�Z��_�X�֔ԍ�
			if(!StringValidateUtil.isEmpty(((BuildingInfo)buildingInfo.getItems().get("buildingInfo")).getZip())){
				this.zip =((BuildingInfo)buildingInfo.getItems().get("buildingInfo")).getZip();
			}
			//�s���{��CD
			this.prefCd = ((BuildingInfo)buildingInfo.getItems().get("buildingInfo")).getPrefCd();
			//�s���{����
			//form.setPrefName();
			//�s�撬��CD
			this.addressCd = ((BuildingInfo)buildingInfo.getItems().get("buildingInfo")).getAddressCd();
			//�s�撬����
			this.addressName = ((BuildingInfo)buildingInfo.getItems().get("buildingInfo")).getAddressName();
			//�Z���P
			this.addressOther1 = ((BuildingInfo)buildingInfo.getItems().get("buildingInfo")).getAddressOther1();
			//�Z���Q
			this.addressOther2 = ((BuildingInfo)buildingInfo.getItems().get("buildingInfo")).getAddressOther2();
			//�����ʐ�
			if(((BuildingDtlInfo)buildingInfo.getItems().get("buildingDtlInfo")).getBuildingArea() !=null ){
				this.buildingArea = String.valueOf(((BuildingDtlInfo)buildingInfo.getItems().get("buildingDtlInfo")).getBuildingArea());
			}
			//�����ʐ�_�R�����g
			this.buildingAreaMemo = ((BuildingDtlInfo)buildingInfo.getItems().get("buildingDtlInfo")).getBuildingAreaMemo();
		}
		if(buildingStationInfoList !=null && buildingStationInfoList.size()>0){
			//����CD
			this.defaultRouteCd = new String[buildingStationInfoList.size()];
			//������
			this.routeName = new String[buildingStationInfoList.size()];
			//�wCD
			this.stationCd = new String[buildingStationInfoList.size()];
			//�w��
			this.stationName = new String[buildingStationInfoList.size()];

			//�o�X��Ж�
			this.busCompany = new String[buildingStationInfoList.size()];
			//�o�X�₩��̓k������
			this.timeFromBusStop = new String[buildingStationInfoList.size()];
			for(int i=0;i<buildingStationInfoList.size();i++){
				this.defaultRouteCd[i]=((BuildingStationInfo)buildingStationInfoList.get(i).getItems().get("buildingStationInfo")).getDefaultRouteCd();
				this.routeName[i]=((BuildingStationInfo)buildingStationInfoList.get(i).getItems().get("buildingStationInfo")).getDefaultRouteName();
				this.stationCd[i]=((BuildingStationInfo)buildingStationInfoList.get(i).getItems().get("buildingStationInfo")).getStationCd();
				this.stationName[i]=((BuildingStationInfo)buildingStationInfoList.get(i).getItems().get("buildingStationInfo")).getStationName();
				this.busCompany[i]=((BuildingStationInfo)buildingStationInfoList.get(i).getItems().get("buildingStationInfo")).getBusCompany();
				if(((BuildingStationInfo)buildingStationInfoList.get(i).getItems().get("buildingStationInfo")).getTimeFromBusStop()!=null){
					this.timeFromBusStop[i]=String.valueOf(((BuildingStationInfo)buildingStationInfoList.get(i).getItems().get("buildingStationInfo")).getTimeFromBusStop());
				}
			}

			this.setOldRouteName(routeName);
			this.setOldStationName(stationName);
			this.setOldDefaultRouteCd(defaultRouteCd);
			this.setOldStationCd(stationCd);
		}
    }
}
