package jp.co.transcosmos.dm3.corePana.model.housing.form;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingDtlForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingDtlInfo;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.util.PanaCommonUtil;
import jp.co.transcosmos.dm3.corePana.validation.PonitNumberValidation;
import jp.co.transcosmos.dm3.corePana.validation.ReformImgPathJpgValidation;
import jp.co.transcosmos.dm3.corePana.validation.ReformImgSizeValidation;
import jp.co.transcosmos.dm3.corePana.validation.UrlValidation;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.AsciiOnlyValidation;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.NumericValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.fileupload.FileItem;

/**
 * �����ڍ׏��p�t�H�[��.
 * <p>
 *
 * <pre>
 * �S����       �C����     �C�����e
 * ------------ ----------- -----------------------------------------------------
 * liu.yandong   2015.04.03  �V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class PanaHousingDtlInfoForm extends HousingDtlForm implements Validateable {

    private LengthValidationUtils lengthUtils;

	/**
     * �f�t�H���g�R���X�g���N�^�[<br/>
     * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
     * <br/>
     */
    PanaHousingDtlInfoForm() {
        super();
    }

    /**
     * �R���X�g���N�^�[<br/>
     * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
     * <br/>
     * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
     * @param�@codeLookupManager�@���ʃR�[�h�ϊ�����
     */
    PanaHousingDtlInfoForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager) {
        super();
        this.lengthUtils = lengthUtils;
        this.codeLookupManager = codeLookupManager;
    }

	/** �V�X�e������CD */
    private String sysBuildingCd;
	/** �����ԍ� */
	private String housingCd;
	/** �\���p������ */
	private String displayHousingName;
	/** �\���p���ԏ��� */
	private String displayParkingInfo;
	/** �����̊K���R�����g */
	private String floorNoNote;
	/** �Ǘ��� */
	private String upkeep;
	/** �C�U�ϗ��� */
	private String menteFee;
	/** ��{���R�����g */
	private String basicComment;
    /** �����\�� */
    private String buildingDataValue;
    /** ���� */
    private String preDataValue;
    /** ���؂��� */
    private String coverageMemo;
    /** �e�ϗ� */
    private String buildingRateMemo;
    /** ���ː� */
    private String totalHouseCntDataValue;
    /** �����K�� */
    private String totalFloors;
    /** �K�� */
    private String scaleDataValue;
    /** ���݊K�� */
    private String floorNo;
    /** ���� */
    private String orientedDataValue;
    /** �S���Җ� */
    private String workerDataValue;
    /** �S���Ҏʐ^�t�@�C�� */
    private FileItem pictureDataValue;
    /** �S���Ҏʐ^�p�X */
    private String pictureDataPath;
    /** �S���Ҏʐ^�t�@�C���� */
    private String pictureDataFileName;
    /** �S���Ҏʐ^�폜�t���O */
    private String pictureDataDelete;
    /** �S���Ҏʐ^�A�b�v���[�h�t���O */
    private String pictureUpFlg;
    /** ��Ж� */
    private String companyDataValue;
    /** �x�X�� */
    private String branchDataValue;
    /** �Ƌ��ԍ� */
    private String freeCdDataValue;
    /** �C���t�� */
    private String infDataValue;
    /** ���t�H�[���������R�����g */
    private String reformComment;
    /** ����R�����g */
    private String vendorComment;
    /** ���惊���NURL */
    private String urlDataValue;
    /** �������CD */
    private String housingKindCd;
    /** ���ʃR�[�h�ϊ����� */
    private CodeLookupManager codeLookupManager;
	/** ���߃t���O */
	private String command;
	/** �����R�����g�i��ʕ\���p�j */
	private String dtlComment1;
	/** �S���҂̃R�����g�i��ʕ\���p�j */
	private String basicComment1;
	/** ����R�����g�i��ʕ\���p�j */
	private String vendorComment1;
	/** ���t�H�[���������R�����g�i��ʕ\���p�j */
	private String reformComment1;
	/** �v���r���[�p�摜�p�X */
	private String previewImgPath;


    /**
	 * @return sysBuildingCd
	 */
	public String getSysBuildingCd() {
		return sysBuildingCd;
	}

	/**
	 * @param sysBuildingCd �Z�b�g���� sysBuildingCd
	 */
	public void setSysBuildingCd(String sysBuildingCd) {
		this.sysBuildingCd = sysBuildingCd;
	}

	/**
	 * @return housingCd
	 */
	public String getHousingCd() {
		return housingCd;
	}

	/**
	 * @param housingCd �Z�b�g���� housingCd
	 */
	public void setHousingCd(String housingCd) {
		this.housingCd = housingCd;
	}

	/**
	 * @return displayHousingName
	 */
	public String getDisplayHousingName() {
		return displayHousingName;
	}

	/**
	 * @param displayHousingName �Z�b�g���� displayHousingName
	 */
	public void setDisplayHousingName(String displayHousingName) {
		this.displayHousingName = displayHousingName;
	}

	/**
	 * @return displayParkingInfo
	 */
	public String getDisplayParkingInfo() {
		return displayParkingInfo;
	}

	/**
	 * @param displayParkingInfo �Z�b�g���� displayParkingInfo
	 */
	public void setDisplayParkingInfo(String displayParkingInfo) {
		this.displayParkingInfo = displayParkingInfo;
	}

	/**
	 * @return floorNoNote
	 */
	public String getFloorNoNote() {
		return floorNoNote;
	}

	/**
	 * @param floorNoNote �Z�b�g���� floorNoNote
	 */
	public void setFloorNoNote(String floorNoNote) {
		this.floorNoNote = floorNoNote;
	}

	/**
	 * @return upkeep
	 */
	public String getUpkeep() {
		return upkeep;
	}

	/**
	 * @param upkeep �Z�b�g���� upkeep
	 */
	public void setUpkeep(String upkeep) {
		this.upkeep = upkeep;
	}

	/**
	 * @return menteFee
	 */
	public String getMenteFee() {
		return menteFee;
	}

	/**
	 * @param menteFee �Z�b�g���� menteFee
	 */
	public void setMenteFee(String menteFee) {
		this.menteFee = menteFee;
	}

	/**
	 * @return basicComment
	 */
	public String getBasicComment() {
		return basicComment;
	}

	/**
	 * @param basicComment �Z�b�g���� basicComment
	 */
	public void setBasicComment(String basicComment) {
		this.basicComment = basicComment;
	}

	/**
     * @return buildingDataValue
     */
    public String getBuildingDataValue() {
        return buildingDataValue;
    }

    /**
     * @param buildingDataValue �Z�b�g���� buildingDataValue
     */
    public void setBuildingDataValue(String buildingDataValue) {
        this.buildingDataValue = buildingDataValue;
    }


    /**
     * @return preDataValue
     */
    public String getPreDataValue() {
        return preDataValue;
    }

    /**
     * @param preDataValue �Z�b�g���� preDataValue
     */
    public void setPreDataValue(String preDataValue) {
        this.preDataValue = preDataValue;
    }

    /**
     * @return coverage
     */
    public String getCoverageMemo() {
        return coverageMemo;
    }

    /**
     * @param coverage �Z�b�g���� coverage
     */
    public void setCoverageMemo(String coverageMemo) {
        this.coverageMemo = coverageMemo;
    }

    /**
     * @return buildingRate
     */
    public String getBuildingRateMemo() {
        return buildingRateMemo;
    }

    /**
     * @param buildingRate �Z�b�g���� buildingRate
     */
    public void setBuildingRateMemo(String buildingRateMemo) {
        this.buildingRateMemo = buildingRateMemo;
    }

    /**
     * @return totalHouseCntDataValue
     */
    public String getTotalHouseCntDataValue() {
        return totalHouseCntDataValue;
    }

    /**
     * @param totalHouseCntDataValue �Z�b�g���� totalHouseCntDataValue
     */
    public void setTotalHouseCntDataValue(String totalHouseCntDataValue) {
        this.totalHouseCntDataValue = totalHouseCntDataValue;
    }


    /**
     * @return scaleDataValue
     */
    public String getScaleDataValue() {
        return scaleDataValue;
    }

    /**
     * @param scaleDataValue �Z�b�g���� scaleDataValue
     */
    public void setScaleDataValue(String scaleDataValue) {
        this.scaleDataValue = scaleDataValue;
    }


    /**
	 * @return totalFloors
	 */
	public String getTotalFloors() {
		return totalFloors;
	}

	/**
	 * @param totalFloors �Z�b�g���� totalFloors
	 */
	public void setTotalFloors(String totalFloors) {
		this.totalFloors = totalFloors;
	}

	/**
	 * @return floorNo
	 */
	public String getFloorNo() {
		return floorNo;
	}

	/**
	 * @param floorNo �Z�b�g���� floorNo
	 */
	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}

	/**
     * @return orientedDataValue
     */
    public String getOrientedDataValue() {
        return orientedDataValue;
    }

    /**
     * @param orientedDataValue �Z�b�g���� orientedDataValue
     */
    public void setOrientedDataValue(String orientedDataValue) {
        this.orientedDataValue = orientedDataValue;
    }


    /**
     * @return workerDataValue
     */
    public String getWorkerDataValue() {
        return workerDataValue;
    }

    /**
     * @param workerDataValue �Z�b�g���� workerDataValue
     */
    public void setWorkerDataValue(String workerDataValue) {
        this.workerDataValue = workerDataValue;
    }

    /**
     * @return pictureDataValue
     */
    public FileItem getPictureDataValue() {
        return pictureDataValue;
    }

    /**
     * @param pictureDataValue �Z�b�g���� pictureDataValue
     */
    public void setPictureDataValue(FileItem pictureDataValue) {
        this.pictureDataValue = pictureDataValue;
    }

    /**
	 * @return pictureDataPath
	 */
	public String getPictureDataPath() {
		return pictureDataPath;
	}

	/**
	 * @param pictureDataPath �Z�b�g���� pictureDataPath
	 */
	public void setPictureDataPath(String pictureDataPath) {
		this.pictureDataPath = pictureDataPath;
	}

	/**
	 * @return pictureDataFileName
	 */
	public String getPictureDataFileName() {
		return pictureDataFileName;
	}

	/**
	 * @param pictureDataFileName �Z�b�g���� pictureDataFileName
	 */
	public void setPictureDataFileName(String pictureDataFileName) {
		this.pictureDataFileName = pictureDataFileName;
	}

	/**
	 * @return pictureDataDelete
	 */
	public String getPictureDataDelete() {
		return pictureDataDelete;
	}

	/**
	 * @param pictureDataDelete �Z�b�g���� pictureDataDelete
	 */
	public void setPictureDataDelete(String pictureDataDelete) {
		this.pictureDataDelete = pictureDataDelete;
	}

	/**
	 * @return pictureUpFlg
	 */
	public String getPictureUpFlg() {
		return pictureUpFlg;
	}

	/**
	 * @param pictureUpFlg �Z�b�g���� pictureUpFlg
	 */
	public void setPictureUpFlg(String pictureUpFlg) {
		this.pictureUpFlg = pictureUpFlg;
	}

	/**
     * @return companyDataValue
     */
    public String getCompanyDataValue() {
        return companyDataValue;
    }

    /**
     * @param companyDataValue �Z�b�g���� companyDataValue
     */
    public void setCompanyDataValue(String companyDataValue) {
        this.companyDataValue = companyDataValue;
    }

    /**
     * @return branchDataValue
     */
    public String getBranchDataValue() {
        return branchDataValue;
    }

    /**
     * @param branchDataValue �Z�b�g���� branchDataValue
     */
    public void setBranchDataValue(String branchDataValue) {
        this.branchDataValue = branchDataValue;
    }

    /**
     * @return freeCdDataValue
     */
    public String getFreeCdDataValue() {
        return freeCdDataValue;
    }

    /**
     * @param freeCdDataValue �Z�b�g���� freeCdDataValue
     */
    public void setFreeCdDataValue(String freeCdDataValue) {
        this.freeCdDataValue = freeCdDataValue;
    }

    /**
     * @return infDataValue
     */
    public String getInfDataValue() {
        return infDataValue;
    }

    /**
     * @param infDataValue �Z�b�g���� infDataValue
     */
    public void setInfDataValue(String infDataValue) {
        this.infDataValue = infDataValue;
    }


    /**
     * @return reformComment
     */
    public String getReformComment() {
        return reformComment;
    }

    /**
     * @param reformComment �Z�b�g���� reformComment
     */
    public void setReformComment(String reformComment) {
        this.reformComment = reformComment;
    }

    /**
     * @return urlDataValue
     */
    public String getUrlDataValue() {
        return urlDataValue;
    }

    /**
     * @param urlDataValue �Z�b�g���� urlDataValue
     */
    public void setUrlDataValue(String urlDataValue) {
        this.urlDataValue = urlDataValue;
    }

    /**
     * @return housingKindCd
     */
    public String getHousingKindCd() {
        return housingKindCd;
    }

    /**
     * @param housingKindCd �Z�b�g���� housingKindCd
     */
    public void setHousingKindCd(String housingKindCd) {
        this.housingKindCd = housingKindCd;
    }

    /**
	 * @return lengthUtils
	 */
	public LengthValidationUtils getLengthUtils() {
		return lengthUtils;
	}

	/**
	 * @param lengthUtils �Z�b�g���� lengthUtils
	 */
	public void setLengthUtils(LengthValidationUtils lengthUtils) {
		this.lengthUtils = lengthUtils;
	}

	/**
	 * @return command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @return codeLookupManager
	 */
	public CodeLookupManager getCodeLookupManager() {
		return codeLookupManager;
	}

	/**
	 * @param codeLookupManager �Z�b�g���� codeLookupManager
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * @param command �Z�b�g���� command
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * @return vendorComment
	 */
	public String getVendorComment() {
		return vendorComment;
	}

	/**
	 * @param vendorComment �Z�b�g���� vendorComment
	 */
	public void setVendorComment(String vendorComment) {
		this.vendorComment = vendorComment;
	}

	/**
	 * @return dtlComment1
	 */
	public String getDtlComment1() {
		return dtlComment1;
	}

	/**
	 * @param dtlComment1 �Z�b�g���� dtlComment1
	 */
	public void setDtlComment1(String dtlComment1) {
		this.dtlComment1 = dtlComment1;
	}

	/**
	 * @return basicComment1
	 */
	public String getBasicComment1() {
		return basicComment1;
	}

	/**
	 * @param basicComment1 �Z�b�g���� basicComment1
	 */
	public void setBasicComment1(String basicComment1) {
		this.basicComment1 = basicComment1;
	}

	/**
	 * @return vendorComment1
	 */
	public String getVendorComment1() {
		return vendorComment1;
	}

	/**
	 * @param vendorComment1 �Z�b�g���� vendorComment1
	 */
	public void setVendorComment1(String vendorComment1) {
		this.vendorComment1 = vendorComment1;
	}

	/**
	 * @return reformComment1
	 */
	public String getReformComment1() {
		return reformComment1;
	}

	/**
	 * @param reformComment1 �Z�b�g���� reformComment1
	 */
	public void setReformComment1(String reformComment1) {
		this.reformComment1 = reformComment1;
	}

	/**
	 * @return previewImgPath
	 */
	public String getPreviewImgPath() {
		return previewImgPath;
	}

	/**
	 * @param previewImgPath �Z�b�g���� previewImgPath
	 */
	public void setPreviewImgPath(String previewImgPath) {
		this.previewImgPath = previewImgPath;
	}

	/**
     * �o���f�[�V��������<br/>
     * ���N�G�X�g�p�����[�^�̃o���f�[�V�������s��<br/>
     * <br/>
     * ���� Form �N���X�̃o���f�[�V�������\�b�h�́AValidateable �C���^�[�t�F�[�X�̎����ł͖����̂ŁA
     * �o���f�[�V�������s���̈������قȂ�̂ŁA���� Form ���܂Ƃ߂ăo���f�[�V��������ꍇ�Ȃǂ͒��ӂ��鎖�B<br/>
     * <br/>
     *
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     * @return ���펞 true�A�G���[�� false
     */
    @Override
    public boolean validate(List<ValidationFailure> errors) {

        int startSize = errors.size();

        // �y�n�������̓`�F�b�N
        ValidationChain landRight = new ValidationChain("housingDtl.input.landRight", this.getLandRight());
        // �p�^�[���`�F�b�N
        landRight.addValidation(new CodeLookupValidation(this.codeLookupManager, "landRight"));
        landRight.validate(errors);

        // �����\�����̓`�F�b�N
        ValidationChain buildingDataValue = new ValidationChain("housingDtl.input.buildingDataValue",
                this.getBuildingDataValue());
        if (!PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())) {
	        // �K�{�`�F�b�N
	        buildingDataValue.addValidation(new NullOrEmptyCheckValidation());
        }
        // �����`�F�b�N
        buildingDataValue.addValidation(new MaxLengthValidation(30));
        buildingDataValue.validate(errors);

        // ����`�ԓ��̓`�F�b�N
        ValidationChain transactTypeDiv = new ValidationChain("housingDtl.input.transactTypeDiv",
                this.getTransactTypeDiv());
        // �p�^�[���`�F�b�N
        transactTypeDiv.addValidation(new CodeLookupValidation(this.codeLookupManager, "transactTypeDiv"));
        transactTypeDiv.validate(errors);

        // ���ԏ���̓`�F�b�N
        ValidationChain displayParkingInfo = new ValidationChain("housing.input.displayParkingInfo",
                this.getDisplayParkingInfo());
        // �����`�F�b�N
        displayParkingInfo.addValidation(new MaxLengthValidation(30));
        displayParkingInfo.validate(errors);

        // �������̓`�F�b�N
        ValidationChain preDataValue = new ValidationChain("housingDtl.input.preDataValue", this.getPreDataValue());
        // �K�{�`�F�b�N
        preDataValue.addValidation(new NullOrEmptyCheckValidation());
        // �����`�F�b�N
        preDataValue.addValidation(new MaxLengthValidation(10));
        preDataValue.validate(errors);

        // �p�r�n����̓`�F�b�N
        ValidationChain usedAreaCd = new ValidationChain("housingDtl.input.usedAreaCd", this.getUsedAreaCd());
        // �p�^�[���`�F�b�N
        usedAreaCd.addValidation(new CodeLookupValidation(this.codeLookupManager, "usedAreaCd"));
        usedAreaCd.validate(errors);

        // ���n�����R�����g���̓`�F�b�N
        ValidationChain moveinNote = new ValidationChain("housingDtl.input.moveinNote", this.getMoveinNote());
        // �����`�F�b�N
        moveinNote.addValidation(new MaxLengthValidation(20));
        moveinNote.validate(errors);

        if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd()) ||
        		PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())) {
            // �ړ��󋵓��̓`�F�b�N
            ValidationChain contactRoad = new ValidationChain("housingDtl.input.contactRoad", this.getContactRoad());
            // �K�{�`�F�b�N
            contactRoad.addValidation(new NullOrEmptyCheckValidation());
            // �����`�F�b�N
            contactRoad.addValidation(new MaxLengthValidation(30));
            contactRoad.validate(errors);

            // �ړ�����/�������̓`�F�b�N
            ValidationChain contactRoadDir = new ValidationChain("housingDtl.input.contactRoadDir",
                    this.getContactRoadDir());
            // �K�{�`�F�b�N
            contactRoadDir.addValidation(new NullOrEmptyCheckValidation());
            // �����`�F�b�N
            contactRoadDir.addValidation(new MaxLengthValidation(30));
            contactRoadDir.validate(errors);

            // �������S���̓`�F�b�N
            ValidationChain privateRoad = new ValidationChain("housingDtl.input.privateRoad", this.getPrivateRoad());
            // �K�{�`�F�b�N
            privateRoad.addValidation(new NullOrEmptyCheckValidation());
            // �����`�F�b�N
            privateRoad.addValidation(new MaxLengthValidation(30));
            privateRoad.validate(errors);

            // ���؂������̓`�F�b�N
            ValidationChain coverage = new ValidationChain("housingDtl.input.coverageMemo", this.getCoverageMemo());
            // �K�{�`�F�b�N
            coverage.addValidation(new NullOrEmptyCheckValidation());
            // �����`�F�b�N
            coverage.addValidation(new MaxLengthValidation(10));
            // ���p�����`�F�b�N
            coverage.addValidation(new AsciiOnlyValidation());
            coverage.validate(errors);

            // �e�ϗ����̓`�F�b�N
            ValidationChain buildingRate = new ValidationChain("housingDtl.input.buildingRateMemo", this.getBuildingRateMemo());
            // �K�{�`�F�b�N
            buildingRate.addValidation(new NullOrEmptyCheckValidation());
            // �����`�F�b�N
            buildingRate.addValidation(new MaxLengthValidation(10));
            // ���p�����`�F�b�N
            buildingRate.addValidation(new AsciiOnlyValidation());
            buildingRate.validate(errors);
        }

        if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {
            // ���ː����̓`�F�b�N
            ValidationChain totalHouseCntDataValue = new ValidationChain("housingDtl.input.totalHouseCntDataValue",
                    this.getTotalHouseCntDataValue());
            // �����`�F�b�N
            totalHouseCntDataValue.addValidation(new MaxLengthValidation(10));
            totalHouseCntDataValue.validate(errors);

            // �����K�����̓`�F�b�N
            ValidationChain totalFloors = new ValidationChain("buildingInfo.input.totalFloors",
                    this.getTotalFloors());
            // �K�{�`�F�b�N
            totalFloors.addValidation(new NullOrEmptyCheckValidation());
            // �����`�F�b�N
            totalFloors.addValidation(new MaxLengthValidation(3));
            // ���p�����`�F�b�N
            totalFloors.addValidation(new NumericValidation());
            totalFloors.validate(errors);

            // �K�͓��̓`�F�b�N
            ValidationChain scaleDataValue = new ValidationChain("housingDtl.input.scaleDataValue",
                    this.getScaleDataValue());
            // �p�^�[���`�F�b�N
            scaleDataValue.addValidation(new CodeLookupValidation(this.codeLookupManager, "scaleDataValue"));
            scaleDataValue.validate(errors);

            // ���݊K�����̓`�F�b�N
            ValidationChain floorNo = new ValidationChain("housingDtl.input.floorNo", this.getFloorNo());
            // �����`�F�b�N
            floorNo.addValidation(new MaxLengthValidation(3));
            // ���p�����`�F�b�N
            floorNo.addValidation(new NumericValidation());
            floorNo.validate(errors);

            // ���݊K���R�����g���̓`�F�b�N
            ValidationChain floorNoNote = new ValidationChain("housingDtl.input.floorNoNote", this.getFloorNoNote());
            // �����`�F�b�N
            floorNoNote.addValidation(new MaxLengthValidation(10));
            floorNoNote.validate(errors);

            // �������̓`�F�b�N
            ValidationChain orientedDataValue = new ValidationChain("housingDtl.input.orientedDataValue",
                    this.getOrientedDataValue());
            // �����`�F�b�N
            orientedDataValue.addValidation(new MaxLengthValidation(20));
            orientedDataValue.validate(errors);

            // �o���R�j�[�ʐϓ��̓`�F�b�N
            ValidationChain balconyArea = new ValidationChain("housingDtl.input.balconyArea", this.getBalconyArea());
            // �����`�F�b�N
            balconyArea.addValidation(new PonitNumberValidation(5, 2));
            balconyArea.validate(errors);

            // �Ǘ�����̓`�F�b�N
            ValidationChain upkeep = new ValidationChain("housing.input.upkeep", this.getUpkeep());
            // �����`�F�b�N
            upkeep.addValidation(new MaxLengthValidation(11));
            // ���p�����`�F�b�N
            upkeep.addValidation(new NumericValidation());
            upkeep.validate(errors);

            // �C�U�ϗ�����̓`�F�b�N
            ValidationChain menteFee = new ValidationChain("housing.input.menteFee", this.getMenteFee());
            // �����`�F�b�N
            menteFee.addValidation(new MaxLengthValidation(11));
            // ���p�����`�F�b�N
            menteFee.addValidation(new NumericValidation());
            menteFee.validate(errors);

            // �Ǘ��`�ԁE�������̓`�F�b�N
            ValidationChain upkeepType = new ValidationChain("housingDtl.input.upkeepType", this.getUpkeepType());
            // �����`�F�b�N
            upkeepType.addValidation(new MaxLengthValidation(20));
            upkeepType.validate(errors);
        }

        // �Ǘ���Г��̓`�F�b�N
        ValidationChain upkeepCorp = new ValidationChain("housingDtl.input.upkeepCorp", this.getUpkeepCorp());
        // �����`�F�b�N
        upkeepCorp.addValidation(new MaxLengthValidation(20));
        upkeepCorp.validate(errors);

        // ���r�ی����̓`�F�b�N
        ValidationChain insurExist = new ValidationChain("housingDtl.input.insurExist", this.getInsurExist());
        // �p�^�[���`�F�b�N
        insurExist.addValidation(new CodeLookupValidation(this.codeLookupManager, "insurExist"));
        insurExist.validate(errors);

        // �S���Җ����̓`�F�b�N
        ValidationChain workerDataValue = new ValidationChain("housingDtl.input.workerDataValue",
                this.getWorkerDataValue());
        // �����`�F�b�N
        workerDataValue.addValidation(new MaxLengthValidation(20));
        workerDataValue.validate(errors);

        // �S���Ҏʐ^���̓`�F�b�N
        if ("confirm".equals(this.getCommand()) && this.getPictureDataValue().getSize() > 0) {
            ValidationChain pictureDataValue = new ValidationChain("housingDtl.input.pictureDataValue",
                    this.getPictureDataValue().getSize());
            // �摜�̗L�����`�F�b�N
            pictureDataValue.addValidation(new ReformImgPathJpgValidation(this.getPictureDataValue().getName(), "jpg"));
            // �摜�̃T�C�Y�`�F�b�N
            pictureDataValue.addValidation(new ReformImgSizeValidation(PanaCommonConstant.updFileSize));
            pictureDataValue.validate(errors);
        }

        // ��Ж����̓`�F�b�N
        ValidationChain companyDataValue = new ValidationChain("housingDtl.input.companyDataValue",
                this.getCompanyDataValue());
        // �K�{�`�F�b�N
        companyDataValue.addValidation(new NullOrEmptyCheckValidation());
        // �����`�F�b�N
        companyDataValue.addValidation(new MaxLengthValidation(20));
        companyDataValue.validate(errors);

        // �x�X�����̓`�F�b�N
        ValidationChain branchDataValue = new ValidationChain("housingDtl.input.branchDataValue",
                this.getBranchDataValue());
        // �����`�F�b�N
        branchDataValue.addValidation(new MaxLengthValidation(20));
        branchDataValue.validate(errors);

        // �Ƌ��ԍ����̓`�F�b�N
        ValidationChain freeCdDataValue = new ValidationChain("housingDtl.input.freeCdDataValue",
                this.getFreeCdDataValue());
        // �K�{�`�F�b�N
        freeCdDataValue.addValidation(new NullOrEmptyCheckValidation());
        // �����`�F�b�N
        freeCdDataValue.addValidation(new MaxLengthValidation(20));
        freeCdDataValue.validate(errors);

        // �C���t�����̓`�F�b�N
        ValidationChain infDataValue = new ValidationChain("housingDtl.input.infDataValue", this.getInfDataValue());
        // �����`�F�b�N
        infDataValue.addValidation(new MaxLengthValidation(50));
        infDataValue.validate(errors);

        // ���L�������̓`�F�b�N
        ValidationChain specialInstruction = new ValidationChain("housingDtl.input.specialInstruction",
                this.getSpecialInstruction());
        // �����`�F�b�N
        specialInstruction.addValidation(new MaxLengthValidation(100));
        specialInstruction.validate(errors);

        // �����R�����g���̓`�F�b�N
        ValidationChain dtlComment = new ValidationChain("housingDtl.input.dtlComment", this.getDtlComment());
        // �����`�F�b�N
        dtlComment.addValidation(new MaxLengthValidation(40));
        dtlComment.validate(errors);

        // �S���҂̃R�����g���̓`�F�b�N
        ValidationChain basicComment = new ValidationChain("housingDtl.input.basicComment", this.getBasicComment());
        // �����`�F�b�N
        basicComment.addValidation(new MaxLengthValidation(500));
        basicComment.validate(errors);

        // ����R�����g���̓`�F�b�N
        ValidationChain vendorComment = new ValidationChain("housingDtl.input.vendorComment", this.getVendorComment());
        // �����`�F�b�N
        vendorComment.addValidation(new MaxLengthValidation(500));
        vendorComment.validate(errors);

        // ���t�H�[���������R�����g���̓`�F�b�N
        ValidationChain reformComment = new ValidationChain("housingDtl.input.reformComment", this.getReformComment());
        // �����`�F�b�N
        reformComment.addValidation(new MaxLengthValidation(50));
        reformComment.validate(errors);

        if (!StringValidateUtil.isEmpty(this.getUrlDataValue())) {
            // ���惊���NURL���̓`�F�b�N
            ValidationChain urlDataValue = new ValidationChain("housingDtl.input.urlDataValue", this.getUrlDataValue());
            // �����`�F�b�N
            urlDataValue.addValidation(new MaxLengthValidation(255));
            // ���p�����`�F�b�N
            urlDataValue.addValidation(new AsciiOnlyValidation());
            // URL�`�F�b�N
            urlDataValue.addValidation(new UrlValidation());
            urlDataValue.validate(errors);
        }

        return (startSize == errors.size());
    }

    /**
     * �n���ꂽ�o���[�I�u�W�F�N�g���� Form �֏����l��ݒ肷��B<br/>
     * <br/>
     *
     * @param housing �������I�u�W�F�N�g
     *
     * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
     */
    public void setDefaultData(Housing housing) throws Exception {

        // ������{�����擾����B
        HousingInfo housingInfo = ((HousingInfo) housing.getHousingInfo().getItems().get("housingInfo"));

		// ������{�����擾����B
		BuildingInfo buildingInfo = (BuildingInfo)housing.getBuilding().getBuildingInfo().getItems().get("buildingInfo");

		// �����ڍ׏����擾����B
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo)housing.getBuilding().getBuildingInfo().getItems().get("buildingDtlInfo");

		// �����ڍ׏����擾����B
		HousingDtlInfo housingDtlInfo = (HousingDtlInfo)housing.getHousingInfo().getItems().get("housingDtlInfo");

        if (housingInfo != null && housingInfo.getSysHousingCd() != null) {
            PanaCommonUtil.copyProperties(this, housingInfo);
        }
        if (buildingInfo != null && buildingInfo.getSysBuildingCd() != null) {
            PanaCommonUtil.copyProperties(this, buildingInfo);
        }
        if (housingDtlInfo !=null && housingDtlInfo.getSysHousingCd() != null) {
            PanaCommonUtil.copyProperties(this, housingDtlInfo);
        }
        if (buildingDtlInfo !=null && buildingDtlInfo.getSysBuildingCd() != null) {
            PanaCommonUtil.copyProperties(this, buildingDtlInfo);
        }

        // �����g�����������擾����B
        Map<String, Map<String, String>> extMap = housing.getHousingExtInfos();
        // �J�e�S�����ɊY������ Map ���擾����B
        Map<String, String> cateMap = extMap.get("housingDetail");
        if (cateMap != null) {
            this.setBuildingDataValue(cateMap.get("struct"));
            this.setPreDataValue(cateMap.get("status"));
            this.setTotalHouseCntDataValue(cateMap.get("totalHouseCnt"));
            this.setScaleDataValue(cateMap.get("scale"));
            this.setOrientedDataValue(cateMap.get("direction"));
            this.setWorkerDataValue(cateMap.get("staffName"));
            this.setPictureDataPath(cateMap.get("staffImagePathName"));
            this.setPictureDataFileName(cateMap.get("staffImageFileName"));
            this.setCompanyDataValue(cateMap.get("companyName"));
            this.setBranchDataValue(cateMap.get("branchName"));
            this.setFreeCdDataValue(cateMap.get("licenseNo"));
            this.setInfDataValue(cateMap.get("infrastructure"));
            this.setUrlDataValue(cateMap.get("movieUrl"));
            this.setVendorComment(cateMap.get("vendorComment"));
        }
    }

	/**
	 * �����œn���ꂽ�����ڍ׏��̃o���[�I�u�W�F�N�g�Ƀt�H�[���̒l��ݒ肷��B<br/>
	 * �X�V�����ł��g�p���鎖���l�����A�^�C���X�^���v���Ɋւ��ẮA�X�V���A�X�V�҂̂ݐݒ肷��B<br/>
	 * <br/>
	 * @return housingDtlInfo �����ڍ׏��
	 * @throws ParseException
	 */
	public void copyToHousingDtlInfo(HousingDtlInfo housingDtlInfo) {

		housingDtlInfo.setSysHousingCd(this.getSysHousingCd());					// �V�X�e������CD
		housingDtlInfo.setUsedAreaCd(this.getUsedAreaCd());							// �p�r�n��CD
		housingDtlInfo.setTransactTypeDiv(this.getTransactTypeDiv());				// ����`�ԋ敪
		housingDtlInfo.setLandRight(this.getLandRight());							// �y�n����
		housingDtlInfo.setMoveinTiming(this.getMoveinTiming());						// �����\�����t���O
		housingDtlInfo.setMoveinNote(this.getMoveinNote());							// �����\�����R�����g
		housingDtlInfo.setUpkeepType(this.getUpkeepType());							// �Ǘ��`�ԁE����
		housingDtlInfo.setUpkeepCorp(this.getUpkeepCorp());							// �Ǘ����
		housingDtlInfo.setInsurExist(this.getInsurExist());							// ���ۗL��
		housingDtlInfo.setContactRoad(this.getContactRoad());						// �ړ���
		housingDtlInfo.setContactRoadDir(this.getContactRoadDir());					// �ړ�����/����
		housingDtlInfo.setPrivateRoad(this.getPrivateRoad());						// �������S

		if (!StringValidateUtil.isEmpty(this.getBalconyArea())){						// �o���R�j�[�ʐ�
			housingDtlInfo.setBalconyArea(new BigDecimal(this.getBalconyArea()));
		} else {
			housingDtlInfo.setBalconyArea(null);
		}

		housingDtlInfo.setSpecialInstruction(this.getSpecialInstruction());			// ���L����
		housingDtlInfo.setDtlComment(this.getDtlComment());							// �ڍ׃R�����g

	}
}
