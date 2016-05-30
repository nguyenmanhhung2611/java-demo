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
 * 物件詳細情報用フォーム.
 * <p>
 *
 * <pre>
 * 担当者       修正日     修正内容
 * ------------ ----------- -----------------------------------------------------
 * liu.yandong   2015.04.03  新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class PanaHousingDtlInfoForm extends HousingDtlForm implements Validateable {

    private LengthValidationUtils lengthUtils;

	/**
     * デフォルトコンストラクター<br/>
     * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
     * <br/>
     */
    PanaHousingDtlInfoForm() {
        super();
    }

    /**
     * コンストラクター<br/>
     * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
     * <br/>
     * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
     * @param　codeLookupManager　共通コード変換処理
     */
    PanaHousingDtlInfoForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager) {
        super();
        this.lengthUtils = lengthUtils;
        this.codeLookupManager = codeLookupManager;
    }

	/** システム建物CD */
    private String sysBuildingCd;
	/** 物件番号 */
	private String housingCd;
	/** 表示用物件名 */
	private String displayHousingName;
	/** 表示用駐車場情報 */
	private String displayParkingInfo;
	/** 物件の階数コメント */
	private String floorNoNote;
	/** 管理費 */
	private String upkeep;
	/** 修繕積立費 */
	private String menteFee;
	/** 基本情報コメント */
	private String basicComment;
    /** 建物構造 */
    private String buildingDataValue;
    /** 現況 */
    private String preDataValue;
    /** 建ぺい率 */
    private String coverageMemo;
    /** 容積率 */
    private String buildingRateMemo;
    /** 総戸数 */
    private String totalHouseCntDataValue;
    /** 建物階数 */
    private String totalFloors;
    /** 規模 */
    private String scaleDataValue;
    /** 所在階数 */
    private String floorNo;
    /** 向き */
    private String orientedDataValue;
    /** 担当者名 */
    private String workerDataValue;
    /** 担当者写真ファイル */
    private FileItem pictureDataValue;
    /** 担当者写真パス */
    private String pictureDataPath;
    /** 担当者写真ファイル名 */
    private String pictureDataFileName;
    /** 担当者写真削除フラグ */
    private String pictureDataDelete;
    /** 担当者写真アップロードフラグ */
    private String pictureUpFlg;
    /** 会社名 */
    private String companyDataValue;
    /** 支店名 */
    private String branchDataValue;
    /** 免許番号 */
    private String freeCdDataValue;
    /** インフラ */
    private String infDataValue;
    /** リフォーム準備中コメント */
    private String reformComment;
    /** 売主コメント */
    private String vendorComment;
    /** 動画リンクURL */
    private String urlDataValue;
    /** 物件種類CD */
    private String housingKindCd;
    /** 共通コード変換処理 */
    private CodeLookupManager codeLookupManager;
	/** 命令フラグ */
	private String command;
	/** 物件コメント（画面表示用） */
	private String dtlComment1;
	/** 担当者のコメント（画面表示用） */
	private String basicComment1;
	/** 売主コメント（画面表示用） */
	private String vendorComment1;
	/** リフォーム準備中コメント（画面表示用） */
	private String reformComment1;
	/** プレビュー用画像パス */
	private String previewImgPath;


    /**
	 * @return sysBuildingCd
	 */
	public String getSysBuildingCd() {
		return sysBuildingCd;
	}

	/**
	 * @param sysBuildingCd セットする sysBuildingCd
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
	 * @param housingCd セットする housingCd
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
	 * @param displayHousingName セットする displayHousingName
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
	 * @param displayParkingInfo セットする displayParkingInfo
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
	 * @param floorNoNote セットする floorNoNote
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
	 * @param upkeep セットする upkeep
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
	 * @param menteFee セットする menteFee
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
	 * @param basicComment セットする basicComment
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
     * @param buildingDataValue セットする buildingDataValue
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
     * @param preDataValue セットする preDataValue
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
     * @param coverage セットする coverage
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
     * @param buildingRate セットする buildingRate
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
     * @param totalHouseCntDataValue セットする totalHouseCntDataValue
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
     * @param scaleDataValue セットする scaleDataValue
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
	 * @param totalFloors セットする totalFloors
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
	 * @param floorNo セットする floorNo
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
     * @param orientedDataValue セットする orientedDataValue
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
     * @param workerDataValue セットする workerDataValue
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
     * @param pictureDataValue セットする pictureDataValue
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
	 * @param pictureDataPath セットする pictureDataPath
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
	 * @param pictureDataFileName セットする pictureDataFileName
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
	 * @param pictureDataDelete セットする pictureDataDelete
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
	 * @param pictureUpFlg セットする pictureUpFlg
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
     * @param companyDataValue セットする companyDataValue
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
     * @param branchDataValue セットする branchDataValue
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
     * @param freeCdDataValue セットする freeCdDataValue
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
     * @param infDataValue セットする infDataValue
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
     * @param reformComment セットする reformComment
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
     * @param urlDataValue セットする urlDataValue
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
     * @param housingKindCd セットする housingKindCd
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
	 * @param lengthUtils セットする lengthUtils
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
	 * @param codeLookupManager セットする codeLookupManager
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * @param command セットする command
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
	 * @param vendorComment セットする vendorComment
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
	 * @param dtlComment1 セットする dtlComment1
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
	 * @param basicComment1 セットする basicComment1
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
	 * @param vendorComment1 セットする vendorComment1
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
	 * @param reformComment1 セットする reformComment1
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
	 * @param previewImgPath セットする previewImgPath
	 */
	public void setPreviewImgPath(String previewImgPath) {
		this.previewImgPath = previewImgPath;
	}

	/**
     * バリデーション処理<br/>
     * リクエストパラメータのバリデーションを行う<br/>
     * <br/>
     * この Form クラスのバリデーションメソッドは、Validateable インターフェースの実装では無いので、
     * バリデーション実行時の引数が異なるので、複数 Form をまとめてバリデーションする場合などは注意する事。<br/>
     * <br/>
     *
     * @param errors エラー情報を格納するリストオブジェクト
     * @return 正常時 true、エラー時 false
     */
    @Override
    public boolean validate(List<ValidationFailure> errors) {

        int startSize = errors.size();

        // 土地権利入力チェック
        ValidationChain landRight = new ValidationChain("housingDtl.input.landRight", this.getLandRight());
        // パターンチェック
        landRight.addValidation(new CodeLookupValidation(this.codeLookupManager, "landRight"));
        landRight.validate(errors);

        // 建物構造入力チェック
        ValidationChain buildingDataValue = new ValidationChain("housingDtl.input.buildingDataValue",
                this.getBuildingDataValue());
        if (!PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())) {
	        // 必須チェック
	        buildingDataValue.addValidation(new NullOrEmptyCheckValidation());
        }
        // 桁数チェック
        buildingDataValue.addValidation(new MaxLengthValidation(30));
        buildingDataValue.validate(errors);

        // 取引形態入力チェック
        ValidationChain transactTypeDiv = new ValidationChain("housingDtl.input.transactTypeDiv",
                this.getTransactTypeDiv());
        // パターンチェック
        transactTypeDiv.addValidation(new CodeLookupValidation(this.codeLookupManager, "transactTypeDiv"));
        transactTypeDiv.validate(errors);

        // 駐車場入力チェック
        ValidationChain displayParkingInfo = new ValidationChain("housing.input.displayParkingInfo",
                this.getDisplayParkingInfo());
        // 桁数チェック
        displayParkingInfo.addValidation(new MaxLengthValidation(30));
        displayParkingInfo.validate(errors);

        // 現況入力チェック
        ValidationChain preDataValue = new ValidationChain("housingDtl.input.preDataValue", this.getPreDataValue());
        // 必須チェック
        preDataValue.addValidation(new NullOrEmptyCheckValidation());
        // 桁数チェック
        preDataValue.addValidation(new MaxLengthValidation(10));
        preDataValue.validate(errors);

        // 用途地域入力チェック
        ValidationChain usedAreaCd = new ValidationChain("housingDtl.input.usedAreaCd", this.getUsedAreaCd());
        // パターンチェック
        usedAreaCd.addValidation(new CodeLookupValidation(this.codeLookupManager, "usedAreaCd"));
        usedAreaCd.validate(errors);

        // 引渡時期コメント入力チェック
        ValidationChain moveinNote = new ValidationChain("housingDtl.input.moveinNote", this.getMoveinNote());
        // 桁数チェック
        moveinNote.addValidation(new MaxLengthValidation(20));
        moveinNote.validate(errors);

        if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd()) ||
        		PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())) {
            // 接道状況入力チェック
            ValidationChain contactRoad = new ValidationChain("housingDtl.input.contactRoad", this.getContactRoad());
            // 必須チェック
            contactRoad.addValidation(new NullOrEmptyCheckValidation());
            // 桁数チェック
            contactRoad.addValidation(new MaxLengthValidation(30));
            contactRoad.validate(errors);

            // 接道方向/幅員入力チェック
            ValidationChain contactRoadDir = new ValidationChain("housingDtl.input.contactRoadDir",
                    this.getContactRoadDir());
            // 必須チェック
            contactRoadDir.addValidation(new NullOrEmptyCheckValidation());
            // 桁数チェック
            contactRoadDir.addValidation(new MaxLengthValidation(30));
            contactRoadDir.validate(errors);

            // 私道負担入力チェック
            ValidationChain privateRoad = new ValidationChain("housingDtl.input.privateRoad", this.getPrivateRoad());
            // 必須チェック
            privateRoad.addValidation(new NullOrEmptyCheckValidation());
            // 桁数チェック
            privateRoad.addValidation(new MaxLengthValidation(30));
            privateRoad.validate(errors);

            // 建ぺい率入力チェック
            ValidationChain coverage = new ValidationChain("housingDtl.input.coverageMemo", this.getCoverageMemo());
            // 必須チェック
            coverage.addValidation(new NullOrEmptyCheckValidation());
            // 桁数チェック
            coverage.addValidation(new MaxLengthValidation(10));
            // 半角文字チェック
            coverage.addValidation(new AsciiOnlyValidation());
            coverage.validate(errors);

            // 容積率入力チェック
            ValidationChain buildingRate = new ValidationChain("housingDtl.input.buildingRateMemo", this.getBuildingRateMemo());
            // 必須チェック
            buildingRate.addValidation(new NullOrEmptyCheckValidation());
            // 桁数チェック
            buildingRate.addValidation(new MaxLengthValidation(10));
            // 半角文字チェック
            buildingRate.addValidation(new AsciiOnlyValidation());
            buildingRate.validate(errors);
        }

        if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {
            // 総戸数入力チェック
            ValidationChain totalHouseCntDataValue = new ValidationChain("housingDtl.input.totalHouseCntDataValue",
                    this.getTotalHouseCntDataValue());
            // 桁数チェック
            totalHouseCntDataValue.addValidation(new MaxLengthValidation(10));
            totalHouseCntDataValue.validate(errors);

            // 建物階数入力チェック
            ValidationChain totalFloors = new ValidationChain("buildingInfo.input.totalFloors",
                    this.getTotalFloors());
            // 必須チェック
            totalFloors.addValidation(new NullOrEmptyCheckValidation());
            // 桁数チェック
            totalFloors.addValidation(new MaxLengthValidation(3));
            // 半角数字チェック
            totalFloors.addValidation(new NumericValidation());
            totalFloors.validate(errors);

            // 規模入力チェック
            ValidationChain scaleDataValue = new ValidationChain("housingDtl.input.scaleDataValue",
                    this.getScaleDataValue());
            // パターンチェック
            scaleDataValue.addValidation(new CodeLookupValidation(this.codeLookupManager, "scaleDataValue"));
            scaleDataValue.validate(errors);

            // 所在階数入力チェック
            ValidationChain floorNo = new ValidationChain("housingDtl.input.floorNo", this.getFloorNo());
            // 桁数チェック
            floorNo.addValidation(new MaxLengthValidation(3));
            // 半角数字チェック
            floorNo.addValidation(new NumericValidation());
            floorNo.validate(errors);

            // 所在階数コメント入力チェック
            ValidationChain floorNoNote = new ValidationChain("housingDtl.input.floorNoNote", this.getFloorNoNote());
            // 桁数チェック
            floorNoNote.addValidation(new MaxLengthValidation(10));
            floorNoNote.validate(errors);

            // 向き入力チェック
            ValidationChain orientedDataValue = new ValidationChain("housingDtl.input.orientedDataValue",
                    this.getOrientedDataValue());
            // 桁数チェック
            orientedDataValue.addValidation(new MaxLengthValidation(20));
            orientedDataValue.validate(errors);

            // バルコニー面積入力チェック
            ValidationChain balconyArea = new ValidationChain("housingDtl.input.balconyArea", this.getBalconyArea());
            // 桁数チェック
            balconyArea.addValidation(new PonitNumberValidation(5, 2));
            balconyArea.validate(errors);

            // 管理費入力チェック
            ValidationChain upkeep = new ValidationChain("housing.input.upkeep", this.getUpkeep());
            // 桁数チェック
            upkeep.addValidation(new MaxLengthValidation(11));
            // 半角数字チェック
            upkeep.addValidation(new NumericValidation());
            upkeep.validate(errors);

            // 修繕積立費入力チェック
            ValidationChain menteFee = new ValidationChain("housing.input.menteFee", this.getMenteFee());
            // 桁数チェック
            menteFee.addValidation(new MaxLengthValidation(11));
            // 半角数字チェック
            menteFee.addValidation(new NumericValidation());
            menteFee.validate(errors);

            // 管理形態・方式入力チェック
            ValidationChain upkeepType = new ValidationChain("housingDtl.input.upkeepType", this.getUpkeepType());
            // 桁数チェック
            upkeepType.addValidation(new MaxLengthValidation(20));
            upkeepType.validate(errors);
        }

        // 管理会社入力チェック
        ValidationChain upkeepCorp = new ValidationChain("housingDtl.input.upkeepCorp", this.getUpkeepCorp());
        // 桁数チェック
        upkeepCorp.addValidation(new MaxLengthValidation(20));
        upkeepCorp.validate(errors);

        // 瑕疵保険入力チェック
        ValidationChain insurExist = new ValidationChain("housingDtl.input.insurExist", this.getInsurExist());
        // パターンチェック
        insurExist.addValidation(new CodeLookupValidation(this.codeLookupManager, "insurExist"));
        insurExist.validate(errors);

        // 担当者名入力チェック
        ValidationChain workerDataValue = new ValidationChain("housingDtl.input.workerDataValue",
                this.getWorkerDataValue());
        // 桁数チェック
        workerDataValue.addValidation(new MaxLengthValidation(20));
        workerDataValue.validate(errors);

        // 担当者写真入力チェック
        if ("confirm".equals(this.getCommand()) && this.getPictureDataValue().getSize() > 0) {
            ValidationChain pictureDataValue = new ValidationChain("housingDtl.input.pictureDataValue",
                    this.getPictureDataValue().getSize());
            // 画像の有効性チェック
            pictureDataValue.addValidation(new ReformImgPathJpgValidation(this.getPictureDataValue().getName(), "jpg"));
            // 画像のサイズチェック
            pictureDataValue.addValidation(new ReformImgSizeValidation(PanaCommonConstant.updFileSize));
            pictureDataValue.validate(errors);
        }

        // 会社名入力チェック
        ValidationChain companyDataValue = new ValidationChain("housingDtl.input.companyDataValue",
                this.getCompanyDataValue());
        // 必須チェック
        companyDataValue.addValidation(new NullOrEmptyCheckValidation());
        // 桁数チェック
        companyDataValue.addValidation(new MaxLengthValidation(20));
        companyDataValue.validate(errors);

        // 支店名入力チェック
        ValidationChain branchDataValue = new ValidationChain("housingDtl.input.branchDataValue",
                this.getBranchDataValue());
        // 桁数チェック
        branchDataValue.addValidation(new MaxLengthValidation(20));
        branchDataValue.validate(errors);

        // 免許番号入力チェック
        ValidationChain freeCdDataValue = new ValidationChain("housingDtl.input.freeCdDataValue",
                this.getFreeCdDataValue());
        // 必須チェック
        freeCdDataValue.addValidation(new NullOrEmptyCheckValidation());
        // 桁数チェック
        freeCdDataValue.addValidation(new MaxLengthValidation(20));
        freeCdDataValue.validate(errors);

        // インフラ入力チェック
        ValidationChain infDataValue = new ValidationChain("housingDtl.input.infDataValue", this.getInfDataValue());
        // 桁数チェック
        infDataValue.addValidation(new MaxLengthValidation(50));
        infDataValue.validate(errors);

        // 特記事項入力チェック
        ValidationChain specialInstruction = new ValidationChain("housingDtl.input.specialInstruction",
                this.getSpecialInstruction());
        // 桁数チェック
        specialInstruction.addValidation(new MaxLengthValidation(100));
        specialInstruction.validate(errors);

        // 物件コメント入力チェック
        ValidationChain dtlComment = new ValidationChain("housingDtl.input.dtlComment", this.getDtlComment());
        // 桁数チェック
        dtlComment.addValidation(new MaxLengthValidation(40));
        dtlComment.validate(errors);

        // 担当者のコメント入力チェック
        ValidationChain basicComment = new ValidationChain("housingDtl.input.basicComment", this.getBasicComment());
        // 桁数チェック
        basicComment.addValidation(new MaxLengthValidation(500));
        basicComment.validate(errors);

        // 売主コメント入力チェック
        ValidationChain vendorComment = new ValidationChain("housingDtl.input.vendorComment", this.getVendorComment());
        // 桁数チェック
        vendorComment.addValidation(new MaxLengthValidation(500));
        vendorComment.validate(errors);

        // リフォーム準備中コメント入力チェック
        ValidationChain reformComment = new ValidationChain("housingDtl.input.reformComment", this.getReformComment());
        // 桁数チェック
        reformComment.addValidation(new MaxLengthValidation(50));
        reformComment.validate(errors);

        if (!StringValidateUtil.isEmpty(this.getUrlDataValue())) {
            // 動画リンクURL入力チェック
            ValidationChain urlDataValue = new ValidationChain("housingDtl.input.urlDataValue", this.getUrlDataValue());
            // 桁数チェック
            urlDataValue.addValidation(new MaxLengthValidation(255));
            // 半角文字チェック
            urlDataValue.addValidation(new AsciiOnlyValidation());
            // URLチェック
            urlDataValue.addValidation(new UrlValidation());
            urlDataValue.validate(errors);
        }

        return (startSize == errors.size());
    }

    /**
     * 渡されたバリーオブジェクトから Form へ初期値を設定する。<br/>
     * <br/>
     *
     * @param housing 物件情報オブジェクト
     *
     * @exception Exception 実装クラスによりスローされる任意の例外
     */
    public void setDefaultData(Housing housing) throws Exception {

        // 物件基本情報を取得する。
        HousingInfo housingInfo = ((HousingInfo) housing.getHousingInfo().getItems().get("housingInfo"));

		// 建物基本情報を取得する。
		BuildingInfo buildingInfo = (BuildingInfo)housing.getBuilding().getBuildingInfo().getItems().get("buildingInfo");

		// 建物詳細情報を取得する。
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo)housing.getBuilding().getBuildingInfo().getItems().get("buildingDtlInfo");

		// 物件詳細情報を取得する。
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

        // 物件拡張属性情報を取得する。
        Map<String, Map<String, String>> extMap = housing.getHousingExtInfos();
        // カテゴリ名に該当する Map を取得する。
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
	 * 引数で渡された物件詳細情報のバリーオブジェクトにフォームの値を設定する。<br/>
	 * 更新処理でも使用する事を考慮し、タイムスタンプ情報に関しては、更新日、更新者のみ設定する。<br/>
	 * <br/>
	 * @return housingDtlInfo 物件詳細情報
	 * @throws ParseException
	 */
	public void copyToHousingDtlInfo(HousingDtlInfo housingDtlInfo) {

		housingDtlInfo.setSysHousingCd(this.getSysHousingCd());					// システム物件CD
		housingDtlInfo.setUsedAreaCd(this.getUsedAreaCd());							// 用途地域CD
		housingDtlInfo.setTransactTypeDiv(this.getTransactTypeDiv());				// 取引形態区分
		housingDtlInfo.setLandRight(this.getLandRight());							// 土地権利
		housingDtlInfo.setMoveinTiming(this.getMoveinTiming());						// 入居可能時期フラグ
		housingDtlInfo.setMoveinNote(this.getMoveinNote());							// 入居可能時期コメント
		housingDtlInfo.setUpkeepType(this.getUpkeepType());							// 管理形態・方式
		housingDtlInfo.setUpkeepCorp(this.getUpkeepCorp());							// 管理会社
		housingDtlInfo.setInsurExist(this.getInsurExist());							// 損保有無
		housingDtlInfo.setContactRoad(this.getContactRoad());						// 接道状況
		housingDtlInfo.setContactRoadDir(this.getContactRoadDir());					// 接道方向/幅員
		housingDtlInfo.setPrivateRoad(this.getPrivateRoad());						// 私道負担

		if (!StringValidateUtil.isEmpty(this.getBalconyArea())){						// バルコニー面積
			housingDtlInfo.setBalconyArea(new BigDecimal(this.getBalconyArea()));
		} else {
			housingDtlInfo.setBalconyArea(null);
		}

		housingDtlInfo.setSpecialInstruction(this.getSpecialInstruction());			// 特記事項
		housingDtlInfo.setDtlComment(this.getDtlComment());							// 詳細コメント

	}
}
