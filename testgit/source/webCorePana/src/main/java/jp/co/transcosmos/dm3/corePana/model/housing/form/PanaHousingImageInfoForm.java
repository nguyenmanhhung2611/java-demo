package jp.co.transcosmos.dm3.corePana.model.housing.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.housing.form.HousingImgForm;
import jp.co.transcosmos.dm3.core.validation.LineAdapter;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.validation.ReformImgPathJpgValidation;
import jp.co.transcosmos.dm3.corePana.validation.ReformImgSizeValidation;
import jp.co.transcosmos.dm3.dao.UpdateExpression;
import jp.co.transcosmos.dm3.dao.UpdateValue;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.NumericValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.fileupload.FileItem;

/**
 * リフォーム情報メンテナンスの入力パラメータ受取り用フォーム.
 * <p>
 *
 * <pre>
 * 担当者      修正日     修正内容
 * ------------ ----------- -----------------------------------------------------
 * 		       2015.03.10  新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class PanaHousingImageInfoForm extends HousingImgForm implements Validateable {

    /** システム物件CD */
    private String sysHousingCd;
    /** 物件番号 */
    private String housingCd;
    /** 物件名称 */
    private String displayHousingName;
    /** 物件種類CD */
    private String housingKindCd;


    /** アップロードファイル(追加用） */
    private FileItem[] addFilePath;
    /** ファイル物理パス(追加用） */
    private String[] addHidPath;
    /** ファイル物理パス(追加用）Min */
    private String[] addHidPathMin;
    /** ファイル名(追加用） */
    private String[] addHidFileName;
    /** 閲覧権限(追加用） */
    private String[] addRoleId;
    /** 表示順(追加用） */
    private String[] addSortOrder;
    /** 種別(追加用） */
    private String[] addImageType;
    /** コメント(追加用）*/
    private String[] addImgComment;

    /** 閲覧権限 */
    private String[] roleId;
    /** 画像 */
    private String[] pathName;
    /** 画像 */
    private String[] hidPathMax;
    /** 画像 */
    private String[] hidPathMin;

    /** 閲覧権限（更新保持用） */
    private String[] oldRoleId;

    /** コマンド */
    private String command;
    private String confirm;

    /** 共通コード変換処理 */
    private CodeLookupManager codeLookupManager;

    /**
     * ファイル物理パス パラメータを取得する。<br/>
     * ファイル物理パス パラメータの値は、フレームワークの URL マッピングで使用する ファイル物理パス クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return ファイル物理パス パラメータ
     */
    public String[] getHidPathMax() {
		return hidPathMax;
	}

    /**
     * ファイル物理パス パラメータを設定する。<BR/>
     * ファイル物理パス パラメータの値は、フレームワークの URL マッピングで使用する ファイル物理パス クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM hidPathMax ファイル物理パス パラメータ
     */
	public void setHidPathMax(String[] hidPathMax) {
		this.hidPathMax = hidPathMax;
	}

	/**
     * ファイル物理パス パラメータを取得する。<br/>
     * ファイル物理パス パラメータの値は、フレームワークの URL マッピングで使用する ファイル物理パス クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return ファイル物理パス パラメータ
     */
	public String[] getHidPathMin() {
		return hidPathMin;
	}

    /**
     * ファイル物理パス パラメータを設定する。<BR/>
     * ファイル物理パス パラメータの値は、フレームワークの URL マッピングで使用する ファイル物理パス クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM hidPathMin ファイル物理パス パラメータ
     */
	public void setHidPathMin(String[] hidPathMin) {
		this.hidPathMin = hidPathMin;
	}

	/**
     * アップロードファイル(追加用） パラメータを取得する。<br/>
     * アップロードファイル(追加用） パラメータの値は、フレームワークの URL マッピングで使用する アップロードファイル(追加用） クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return アップロードファイル(追加用） パラメータ
     */
	public FileItem[] getAddFilePath() {
		return addFilePath;
	}

	/**
     * アップロードファイル(追加用） パラメータを設定する。<BR/>
     * アップロードファイル(追加用） パラメータの値は、フレームワークの URL マッピングで使用する アップロードファイル(追加用） クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM addFilePath アップロードファイル(追加用） パラメータ
     */
	public void setAddFilePath(FileItem[] addFilePath) {
		this.addFilePath = addFilePath;
	}

	/**
     * ファイル物理パス(追加用） パラメータを取得する。<br/>
     * ファイル物理パス(追加用） パラメータの値は、フレームワークの URL マッピングで使用する ファイル物理パス(追加用） クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return ファイル物理パス(追加用） パラメータ
     */
	public String[] getAddHidPath() {
		return addHidPath;
	}

	/**
     * ファイル物理パス(追加用） パラメータを設定する。<BR/>
     * ファイル物理パス(追加用） パラメータの値は、フレームワークの URL マッピングで使用する ファイル物理パス(追加用） クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM addHidPath ファイル物理パス(追加用） パラメータ
     */
	public void setAddHidPath(String[] addHidPath) {
		this.addHidPath = addHidPath;
	}

	/**
     * ファイル物理パス(追加用）Min パラメータを取得する。<br/>
     * ファイル物理パス(追加用）Min パラメータの値は、フレームワークの URL マッピングで使用する ファイル物理パス(追加用） クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return ファイル物理パス(追加用）Min パラメータ
     */
	public String[] getAddHidPathMin() {
		return addHidPathMin;
	}

	/**
     * ファイル物理パス(追加用）Min パラメータを設定する。<BR/>
     * ファイル物理パス(追加用）Min パラメータの値は、フレームワークの URL マッピングで使用する ファイル物理パス(追加用） クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM addHidPathMin ファイル物理パス(追加用）Min パラメータ
     */
	public void setAddHidPathMin(String[] addHidPathMin) {
		this.addHidPathMin = addHidPathMin;
	}

	/**
     * ファイル名(追加用） パラメータを取得する。<br/>
     * ファイル名(追加用） パラメータの値は、フレームワークの URL マッピングで使用する ファイル名(追加用）クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return ファイル物理パス(追加用） パラメータ
     */
	public String[] getAddHidFileName() {
		return addHidFileName;
	}

	/**
     * ファイル名(追加用）パラメータを設定する。<BR/>
     * ファイル名(追加用）パラメータの値は、フレームワークの URL マッピングで使用する ファイル名(追加用）クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM addHidFileName ファイル名(追加用） パラメータ
     */
	public void setAddHidFileName(String[] addHidFileName) {
		this.addHidFileName = addHidFileName;
	}

	/**
     * 閲覧権限(追加用）パラメータを取得する。<br/>
     * 閲覧権限(追加用）パラメータの値は、フレームワークの URL マッピングで使用する 閲覧権限(追加用）クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 閲覧権限(追加用）パラメータ
     */
	public String[] getAddRoleId() {
		return addRoleId;
	}

	/**
     * 閲覧権限(追加用）パラメータを設定する。<BR/>
     * 閲覧権限(追加用）パラメータの値は、フレームワークの URL マッピングで使用する 閲覧権限(追加用）クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM addRoleId 閲覧権限(追加用）パラメータ
     */
	public void setAddRoleId(String[] addRoleId) {
		this.addRoleId = addRoleId;
	}

	/**
     * 表示順(追加用）パラメータを取得する。<br/>
     * 表示順(追加用）パラメータの値は、フレームワークの URL マッピングで使用する表示順(追加用）クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 表示順(追加用）パラメータ
     */
	public String[] getAddSortOrder() {
		return addSortOrder;
	}

	/**
     * 表示順(追加用）パラメータを設定する。<BR/>
     * 表示順(追加用）パラメータの値は、フレームワークの URL マッピングで使用する 表示順(追加用）クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM addSortOrder 表示順(追加用）パラメータ
     */
	public void setAddSortOrder(String[] addSortOrder) {
		this.addSortOrder = addSortOrder;
	}

	/**
     * 種別(追加用） パラメータを取得する。<br/>
     * 種別(追加用） パラメータの値は、フレームワークの URL マッピングで使用する 種別(追加用） クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 表示順(追加用）パラメータ
     */
	public String[] getAddImageType() {
		return addImageType;
	}

	/**
     * 種別(追加用） パラメータを設定する。<BR/>
     * 種別(追加用） パラメータの値は、フレームワークの URL マッピングで使用する 種別(追加用） クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM addImageType 種別(追加用） パラメータ
     */
	public void setAddImageType(String[] addImageType) {
		this.addImageType = addImageType;
	}

	/**
     * コメント(追加用）パラメータを取得する。<br/>
     * コメント(追加用）パラメータの値は、フレームワークの URL マッピングで使用する コメント(追加用）クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 表示順(追加用）パラメータ
     */
	public String[] getAddImgComment() {
		return addImgComment;
	}

	/**
     * コメント(追加用）パラメータを取得する。<br/>
     * コメント(追加用）パラメータの値は、フレームワークの URL マッピングで使用する コメント(追加用）クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return addImgComment コメント(追加用）パラメータ
     */
	public void setAddImgComment(String[] addImgComment) {
		this.addImgComment = addImgComment;
	}

	/**
     * 閲覧権限（更新保持用）パラメータを取得する。<br/>
     * 閲覧権限（更新保持用）パラメータの値は、フレームワークの URL マッピングで使用する 閲覧権限（更新保持用）クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 表示順(追加用）パラメータ
     */
	public String[] getOldRoleId() {
		return oldRoleId;
	}

	/**
     * 閲覧権限（更新保持用）パラメータを取得する。<br/>
     * 閲覧権限（更新保持用）パラメータの値は、フレームワークの URL マッピングで使用する 閲覧権限（更新保持用）クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return oldRoleId 閲覧権限（更新保持用）パラメータ
     */
	public void setOldRoleId(String[] oldRoleId) {
		this.oldRoleId = oldRoleId;
	}

    PanaHousingImageInfoForm(CodeLookupManager codeLookupManager) {
        super();
        this.codeLookupManager = codeLookupManager;
    }

    /**
     * システム物件CDパラメータを取得する。<br/>
     * システム物件CDパラメータの値は、フレームワークの URL マッピングで使用する システム物件CDクラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 表示順(追加用）パラメータ
     */
    public String getSysHousingCd() {
		return sysHousingCd;
	}

    /**
     * システム物件CDパラメータを取得する。<br/>
     * システム物件CDパラメータの値は、フレームワークの URL マッピングで使用する システム物件CDクラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return sysHousingCd システム物件CDパラメータ
     */
	public void setSysHousingCd(String sysHousingCd) {
		this.sysHousingCd = sysHousingCd;
	}


    /**
     * 物件番号 パラメータを取得する。<br/>
     * 物件番号 パラメータの値は、フレームワークの URL マッピングで使用する 物件番号 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 物件番号 パラメータ
     */
    public String getHousingCd() {
        return housingCd;
    }

    /**
     * 物件番号 パラメータを設定する。<BR/>
     * 物件番号 パラメータの値は、フレームワークの URL マッピングで使用する 物件番号 クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM HOUSINGCD 物件番号 パラメータ
     */
    public void setHousingCd(String housingCd) {
        this.housingCd = housingCd;
    }

    /**
     * 物件名称 パラメータを取得する。<br/>
     * 物件名称 パラメータの値は、フレームワークの URL マッピングで使用する 物件名称 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 物件名称 パラメータ
     */
    public String getDisplayHousingName() {
        return displayHousingName;
    }

    /**
     * 物件名称 パラメータを設定する。<BR/>
     * 物件名称 パラメータの値は、フレームワークの URL マッピングで使用する 物件名称 クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM displayHousingName 物件名称 パラメータ
     */
    public void setDisplayHousingName(String displayHousingName) {
        this.displayHousingName = displayHousingName;
    }

    /**
     * 物件種類CD パラメータを取得する。<br/>
     * 物件種類CD パラメータの値は、フレームワークの URL マッピングで使用する 物件種類CD クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return システムリフォームCD パラメータ
     */
    public String getHousingKindCd() {
        return housingKindCd;
    }

    /**
     * 物件種類CD パラメータを設定する。<BR/>
     * 物件種類CD パラメータの値は、フレームワークの URL マッピングで使用する 物件種類CD クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM housingKindCd 物件種類CD パラメータ
     */
    public void setHousingKindCd(String housingKindCd) {
        this.housingKindCd = housingKindCd;
    }


    /**
     * 閲覧権限 パラメータを取得する。<br/>
     * <br/>
     *
     * @return 閲覧権限 パラメータ
     */
    public String[] getRoleId() {
        return roleId;
    }

    /**
     * 閲覧権限 パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM roleId 閲覧権限 パラメータ
     */
    public void setRoleId(String[] roleId) {
        this.roleId = roleId;
    }

    /**
     * 画像 パラメータを取得する。<br/>
     * 画像 パラメータの値は、フレームワークの URL マッピングで使用する 画像 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 画像 パラメータ
     */
    public String[] getPathName() {
        return pathName;
    }

    /**
     * 画像 パラメータを設定する。<BR/>
     * 画像 パラメータの値は、フレームワークの URL マッピングで使用する 画像 クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM pathName 画像 パラメータ
     */
    public void setPathName(String[] pathName) {
        this.pathName = pathName;
    }

    /**
     * コマンドパラメータを取得する。<br/>
     * コマンド パラメータの値は、フレームワークの URL マッピングで使用する コマンド クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return コマンド パラメータ
     */
    public String getCommand() {
        return command;
    }

    /**
     * コマンド パラメータを設定する。<BR/>
     * コマンド パラメータの値は、フレームワークの URL マッピングで使用するコマンド クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM command コマンド パラメータ
     */
    public void setCommand(String command) {
        this.command = command;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    /**
     * バリデーション処理<br/>
     * リクエストパラメータのバリデーションを行う<br/>
     * <br/>
     * この Form クラスのバリデーションメソッドは、Validateable インターフェースの実装では無いので、
     * バリデーション実行時の引数が異なるので、複数 Form をまとめてバリデーションする場合などは注意 する事。<br/>
     * <br/>
     *
     * @param errors
     *            エラー情報を格納するリストオブジェクト
     * @param command
     *            処理モード ("insert" or "update")
     * @return 正常時 true、エラー時 false
     */
    @Override
    public boolean validate(List<ValidationFailure> errors) {
        int startSize = errors.size();            // リフォーム詳細情報入力の画面チェックを行う
            if ("insert".equals(this.getCommand())) {

            	String[] wkPathName = null;

            	if(this.getAddFilePath()!=null){
            		wkPathName = new String[this.getAddFilePath().length];
            		for (int i = 0; i < this.getAddFilePath().length; i++) {
            			wkPathName[i]=this.getAddFilePath()[i].getName();
            		}
            		if (StringValidateUtil.isEmpty(wkPathName[0])
	                        && StringValidateUtil.isEmpty(this.getAddSortOrder()[0])
	                        && StringValidateUtil.isEmpty(this.getAddImageType()[0])
	                        && StringValidateUtil.isEmpty(this.getAddRoleId()[0])
	                        && StringValidateUtil.isEmpty(wkPathName[1])
	                        && StringValidateUtil.isEmpty(this.getAddSortOrder()[1])
	                        && StringValidateUtil.isEmpty(this.getAddImageType()[1])
	                        && StringValidateUtil.isEmpty(this.getAddRoleId()[1])
	                        && StringValidateUtil.isEmpty(wkPathName[2])
	                        && StringValidateUtil.isEmpty(this.getAddSortOrder()[2])
	                        && StringValidateUtil.isEmpty(this.getAddImageType()[2])
	                        && StringValidateUtil.isEmpty(this.getAddRoleId()[2])) {

	                    // Validate path_name 画像
	                    ValidationFailure vf = new ValidationFailure(
                                "reformImgFileAllNull", "アップロード画像", "", null);
                        errors.add(vf);
	                }
            	}else{
            		wkPathName = new String[this.getAddHidFileName().length];
            		wkPathName = this.getAddHidFileName();
            	}

            	for (int i = 0; i < wkPathName.length; i++) {

	                // バリデーションチェックを行う
	                if (!StringValidateUtil.isEmpty(wkPathName[i])
	                        && !StringValidateUtil.isEmpty(this.getAddSortOrder()[i])
	                        && !StringValidateUtil.isEmpty(this.getAddImageType()[i])
	                        && !StringValidateUtil.isEmpty(this.getAddRoleId()[i])) {

	                	if(this.getAddFilePath()!=null){
	                		 // Validate path_name 画像
	                        ValidationChain valPathName = new ValidationChain(
	                                "housingImageInfo.input.pathName", getAddFilePath()[i].getSize());
	                        // JPGチェック
	                        valPathName.addValidation(new LineAdapter(
	                                new ReformImgPathJpgValidation(getAddFilePath()[i].getName(),"jpg"), i+1));
	                        // ファイルサイズチェック
	                        valPathName.addValidation(new LineAdapter(
	                                new ReformImgSizeValidation(PanaCommonConstant.updFileSize), i+1));
	                        valPathName.validate(errors);
	                	}

	                    // Validate sort_order 表示順
	                    ValidationChain valSortOrder = new ValidationChain(
	                            "housingImageInfo.input.sortOrder", getAddSortOrder()[i]);
	                    // 最大桁数 3桁
	                    valSortOrder.addValidation(new LineAdapter(
	                            new MaxLengthValidation(3), i+1));
	                    // 半角数字
	                    valSortOrder.addValidation(new LineAdapter(
	                            new NumericValidation(), i+1));
	                    valSortOrder.validate(errors);

	                    // Validate Role_id 閲覧権限
	                    ValidationChain valImageType = new ValidationChain(
	                            "housingImageInfo.input.imageType",getAddImageType()[i]);

	                    // パターンチェック
	                    valImageType.addValidation(new LineAdapter(
	                            new CodeLookupValidation(this.codeLookupManager,
	                                    "ImageType"), i+1));
	                    valImageType.validate(errors);

	                    // Validate Role_id 閲覧権限
	                    ValidationChain valRoleId = new ValidationChain(
	                            "housingImageInfo.input.roleId",getAddRoleId()[i]);

	                    // パターンチェック
	                    valRoleId.addValidation(new LineAdapter(
	                            new CodeLookupValidation(this.codeLookupManager,
	                                    "ImageInfoRoleId"), i+1));
	                    valRoleId.validate(errors);

	                    // 種別が'内観'、閲覧権限が'全員可'の場合
	                    if (PanaCommonConstant.IMAGE_TYPE_03.equals(this.getAddImageType()[i])) {
	                        if (PanaCommonConstant.ROLE_ID_PUBLIC.equals(this.getAddRoleId()[i])) {

	                            ValidationFailure vf = new ValidationFailure(
	                                    "housingImageInfoError", "", "", null);
	                            errors.add(vf);
	                        }
	                    }
	                }

	                // コメント
                    ValidationChain valImgComment = new ValidationChain(
                            "housingImageInfo.input.imgComment", getAddImgComment()[i]);
                    // 最大桁数 50桁
                    valImgComment.addValidation(new LineAdapter(
                            new MaxLengthValidation(50), i+1));
                    valImgComment.validate(errors);

	                // 項目は全て入力ではなく、全て空白ではない場合
	                if (!StringValidateUtil.isEmpty(wkPathName[i])
	                        || !StringValidateUtil.isEmpty(this.getAddSortOrder()[i])
	                        || !StringValidateUtil.isEmpty(this.getAddImageType()[i])
	                        || !StringValidateUtil.isEmpty(this.getAddRoleId()[i])) {
	                    if (StringValidateUtil.isEmpty(wkPathName[i])
		                        || StringValidateUtil.isEmpty(this.getAddSortOrder()[i])
		                        || StringValidateUtil.isEmpty(this.getAddImageType()[i])
		                        || StringValidateUtil.isEmpty(this.getAddRoleId()[i])) {

	                        String param[] = new String[] { "アップロード画像、表示順、種別、閲覧権限" };
	                        ValidationFailure vf = new ValidationFailure(
	                                "oneInputOtherMustInput", "", "", param);
	                        errors.add(vf);
	                    }
	                }
            	}
            } else {
                // バリデーションチェックを行う
                if (this.getPathName() != null) {
                    for (int i = 0; i < this.getPathName().length; i++) {
                        // Validate path_name 画像
                        ValidationChain valPathName = new ValidationChain(
                                "housingImageInfo.input.pathName", getPathName()[i]);
                        // 必須チェック
                        valPathName.addValidation(new LineAdapter(
                                new NullOrEmptyCheckValidation(), i + 1));
                        valPathName.validate(errors);

                        // Validate sort_order 表示順
                        ValidationChain valSortOrder = new ValidationChain(
                                "housingImageInfo.input.sortOrder", getSortOrder()[i]);
                        // 必須チェック
                        valSortOrder.addValidation(new LineAdapter(
                                new NullOrEmptyCheckValidation(), i + 1));
                        // 最大桁数 3桁
                        valSortOrder.addValidation(new LineAdapter(
                                new MaxLengthValidation(3), i + 1));
                        // 半角数字
                        valSortOrder.addValidation(new LineAdapter(
                                new NumericValidation(), i + 1));
                        valSortOrder.validate(errors);

                        // コメント
                        ValidationChain valImgComment = new ValidationChain(
                                "housingImageInfo.input.imgComment", getImgComment()[i]);
                        // 最大桁数 50桁
                        valImgComment.addValidation(new LineAdapter(
                                new MaxLengthValidation(50), i+1));
                        valImgComment.validate(errors);

                        // Validate Role_id 閲覧権限
                        ValidationChain valRoleId = new ValidationChain(
                                "housingImageInfo.input.roleId", getRoleId()[i]);
                        // パターンチェック
                        valRoleId.addValidation(new LineAdapter(
                                new CodeLookupValidation(this.codeLookupManager,
                                        "ImageInfoRoleId"), i + 1));
                        // 必須チェック
                        valRoleId.addValidation(new LineAdapter(
                                new NullOrEmptyCheckValidation(), i + 1));
                        valRoleId.validate(errors);


                        // 種別
                        ValidationChain valImageType = new ValidationChain(
                                "housingImageInfo.input.imageType", getImageType()[i]);
                        // 必須チェック
                        valImageType.addValidation(new LineAdapter(
                                new NullOrEmptyCheckValidation(), i + 1));
                        // パターンチェック
                        valImageType.addValidation(new LineAdapter(
                                new CodeLookupValidation(this.codeLookupManager,
                                        "ImageType"), i + 1));
                        valImageType.validate(errors);


                        // Validate Role_id 閲覧権限
                        ValidationChain valoOldRoleId = new ValidationChain(
                                "housingImageInfo.input.roleId", getOldRoleId()[i]);
                        // パターンチェック
                        valoOldRoleId.addValidation(new LineAdapter(
                                new CodeLookupValidation(this.codeLookupManager,
                                        "ImageInfoRoleId"), i + 1));
                        valoOldRoleId.validate(errors);


                        // 種別
                        ValidationChain valOldImageType = new ValidationChain(
                                "housingImageInfo.input.imageType", getOldImageType()[i]);

                        // パターンチェック
                        valOldImageType.addValidation(new LineAdapter(
                                new CodeLookupValidation(this.codeLookupManager,
                                        "ImageType"), i + 1));
                        valOldImageType.validate(errors);

                        // 種別が'内観'、閲覧権限が'全員可'の場合
                        if (PanaCommonConstant.IMAGE_TYPE_03.equals(this.getImageType()[i])) {
                            if (PanaCommonConstant.ROLE_ID_PUBLIC.equals(this.getRoleId()[i])) {

                                ValidationFailure vf = new ValidationFailure(
                                        "housingImageInfoError", "", "", null);
                                errors.add(vf);
                            }
                        }
                    }
                }
            }
        return (startSize == errors.size());
    }

    /**
     * フォームからVOデータを書き込む処理。<br/>
     * <br/>
     *
     * @param housingImageInfo
     *            書き込む対象VO
     * @param i
     *            アップロード対象画像リストのインデックス
     *
     */
    @Override
    public void copyToHousingImageInfo(HousingImageInfo housingImageInfo, int idx) {
    	// システム物件CD
    	((jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo) housingImageInfo).setSysHousingCd(this.getSysHousingCd());

		// 画像タイプ
    	((jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo) housingImageInfo).setImageType(this.getImageType()[idx]);

		// 表示順
		if (!StringValidateUtil.isEmpty(this.getSortOrder()[idx])){
			((jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo) housingImageInfo).setSortOrder(Integer.valueOf(this.getSortOrder()[idx]));
		}
		// ファイル名
		// 仮フォルダの段階で、シーケンス１０桁のファイル名にリネームされている事。
		((jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo) housingImageInfo).setFileName(this.getFileName()[idx]);

		// コメント
		((jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo) housingImageInfo).setImgComment(this.getImgComment()[idx]);

		// roleId
		if (!StringValidateUtil.isEmpty(this.roleId[idx])) {
            ((jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo) housingImageInfo).setRoleId(this.getRoleId()[idx]);
        }
    }

	/**
	 * 物件画像情報の更新 UpdateExpression を生成する。<br/>
	 * <br/>
	 * 物件画像情報は、主キー値を更新する為、通常の update() メソッドでは更新できない。<br/>
	 * このメソッドが復帰する　UpdateExpression　を使用して更新する。<br/>
	 * <br/>
	 * @param idx 行位置
	 *
	 * @return 更新タイムスタンプ UPDATE 用　UpdateExpression
	 */
    @Override
	public UpdateExpression[] buildUpdateExpression(int idx){

       // システム物件コード、パス名、ファイル名は更新対象にはならないので設定しない。
       // メイン画像フラグも、別の処理でバッチ的に更新するので個別には更新しない。
       // 縦長・横長フラグも、画像ファイル自体が更新されないので変更は発生しない。

       return new UpdateExpression[] {new UpdateValue("imageType", this.getImageType()[idx]),
       							      new UpdateValue("divNo", this.getDivNo()[idx]),
        							  new UpdateValue("sortOrder", this.getSortOrder()[idx]),
        							  new UpdateValue("imgComment", this.getImgComment()[idx]),
       								  new UpdateValue("roleId", this.getRoleId()[idx])};

	}
    /**
     * 渡されたバリーオブジェクトから Form へ初期値を設定する。<br/>
     * <br/>
     * @param adminUser AdminUserInterface　を実装した、ユーザー情報管理用バリーオブジェクト
     * @param adminRole AdminUserRoleInterface を実装したユーザーロール管理用バリーオブジェクト
     */
    public void setDefaultData(List<HousingImageInfo> housingImageInfoList) {
    	// 画面項目を設定する処理
        if (housingImageInfoList != null && housingImageInfoList.size() > 0) {
        	// 画像タイプ
        	String[] imageType = new String[housingImageInfoList.size()];
        	// コメント
        	String[] imgComment = new String[housingImageInfoList.size()];
            // 枝番
        	String[] divNo = new String[housingImageInfoList.size()];
            // 画像
        	String[] pathName = new String[housingImageInfoList.size()];
        	// ファイル名（非表示）
        	String[] fileName = new String[housingImageInfoList.size()];
            // 表示順
        	String[] sortOrder = new String[housingImageInfoList.size()];;
            // 閲覧権限
        	String[] roleId = new String[housingImageInfoList.size()];

            for (int i = 0; i < housingImageInfoList.size(); i++) {
            	jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo housingImageInfo = (jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo)housingImageInfoList.get(i);
                divNo[i] = String.valueOf(housingImageInfo.getDivNo());
                pathName[i] = String.valueOf(housingImageInfo.getPathName());
                fileName[i] = String.valueOf(housingImageInfo.getFileName());
                sortOrder[i] = String.valueOf(housingImageInfo.getSortOrder());
                imageType[i] = String.valueOf(housingImageInfo.getImageType());
                roleId[i] = String.valueOf(housingImageInfo.getRoleId());
                imgComment[i] = String.valueOf(housingImageInfo.getImgComment()==null?"":housingImageInfo.getImgComment());
            }
            this.setImageType(imageType);
            this.setImgComment(imgComment);
            this.setDivNo(divNo);
            this.setPathName(pathName);
            this.setFileName(fileName);
            this.setSortOrder(sortOrder);
            this.setRoleId(roleId);
            this.setOldRoleId(roleId);
            this.setOldImageType(imageType);
        }
    }
}
