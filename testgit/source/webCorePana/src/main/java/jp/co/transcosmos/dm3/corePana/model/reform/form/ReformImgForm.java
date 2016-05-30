package jp.co.transcosmos.dm3.corePana.model.reform.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.validation.LineAdapter;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.validation.ReformImgPathJpgValidation;
import jp.co.transcosmos.dm3.corePana.validation.ReformImgSizeValidation;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.NumericValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.fileupload.FileItem;

/**
 * リフォーム画像の登録と更新時、受取り用フォーム.
 * <p>
 *
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * fan			2015.04.10	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * この Form のバリデーションは、使用するモード（追加処理 or 更新処理）で異なるので、 フレームワークが提供する Validateable
 * インターフェースは実装していない。<br/>
 * バリデーション実行時のパラメータが通常と異なるので注意する事。<br/>
 *
 */
public class ReformImgForm implements Validateable {

    private CodeLookupManager codeLookupManager;

    ReformImgForm() {
        super();
    }

    ReformImgForm(CodeLookupManager codeLookupManager) {
        super();
        this.codeLookupManager = codeLookupManager;
    }

    /** システム物件CD */
    private String sysHousingCd;

    /** システムリフォームCD */
    private String sysReformCd;

    /** 表示順 パラメータ */
    private String[] uploadSortOrder;

    /** before 画像パス名 パラメータ */
    private FileItem[] uploadBeforePathName;

    /** ファイル物理パス(追加用） */
    private String[] uploadBeforeHidPath;
    private String[] uploadBeforeHidPathMin;

    /** before 画像パス名 パラメータ */
    private String[] uploadBeforeFileName;

    /** before 画像コメント パラメータ */
    private String[] uploadBeforeComment;

    /** after 画像パス名 パラメータ */
    private FileItem[] uploadAfterPathName;

    /** ファイル物理パス(追加用） */
    private String[] uploadAfterHidPath;
    private String[] uploadAfterHidPathMin;

    /** after 画像パス名 パラメータ */
    private String[] uploadAfterFileName;

    /** after 画像コメント パラメータ */
    private String[] uploadAfterComment;

    /** アップロードの閲覧権限 */
    private String[] uploadRoleId;


    /** 表示順 パラメータ */
    private String[] editSortOrder;

    /** before 画像パス名 パラメータ */
    private String[] editBeforePathName;

    /** before 画像コメント パラメータ */
    private String[] editBeforeComment;

    /** after 画像パス名 パラメータ */
    private String[] editAfterPathName;

    /** after 画像コメント パラメータ */
    private String[] editAfterComment;

    /** 閲覧権限 パラメータ */
    private String[] editRoleId;

    /** 画像名 */
    private String[] editBeforeFileName;

    /** after 画像名 パラメータ */
    private String[] editAfterFileName;

	/** 枝番 */
    private String[] divNo;

    /** 削除フラグ */
    private String[] delFlg;

    /** 物件名称 パラメータ */
    private String displayHousingName;

    /** 物件番号 パラメータ */
    private String housingCd;

    /** 物件種類CD */
    private String housingKindCd;

    /** コマンド */
    private String command;

    /** after 画像ファイル名 */
    private String afterFileName;

    /** before 画像ファイル名 */
    private String beforeFileName;


    /** ファイル物理パス(追加用） */
    private String[] beforeHidPathMax;

    /** ファイル物理パス(追加用） */
    private String[] afterHidPathMax;

    /** ファイル物理パス(追加用） */
    private String[] beforeHidPathMin;

    /** ファイル物理パス(追加用） */
    private String[] afterHidPathMin;


    /** アップロードの閲覧権限（更新保持用） */
    private String[] editOldRoleId;

    /**
     * ファイル物理パス(追加用） パラメータを取得する。<br/>
     * <br/>
     *
     * @return ファイル物理パス(追加用） パラメータ
     */
    public String[] getUploadBeforeHidPathMin() {
		return uploadBeforeHidPathMin;
	}
    /**
     * ファイル物理パス(追加用） パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM uploadBeforeHidPathMin ファイル物理パス(追加用） パラメータ
     */
	public void setUploadBeforeHidPathMin(String[] uploadBeforeHidPathMin) {
		this.uploadBeforeHidPathMin = uploadBeforeHidPathMin;
	}
	/**
     * ファイル物理パス(追加用） パラメータを取得する。<br/>
     * <br/>
     *
     * @return ファイル物理パス(追加用） パラメータ
     */
	public String[] getUploadAfterHidPathMin() {
		return uploadAfterHidPathMin;
	}
	/**
     * ファイル物理パス(追加用） パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM uploadAfterHidPathMin ファイル物理パス(追加用） パラメータ
     */
	public void setUploadAfterHidPathMin(String[] uploadAfterHidPathMin) {
		this.uploadAfterHidPathMin = uploadAfterHidPathMin;
	}

	/**
     * ファイル物理パス(追加用） パラメータを取得する。<br/>
     * <br/>
     *
     * @return ファイル物理パス(追加用） パラメータ
     */
    public String[] getBeforeHidPathMax() {
		return beforeHidPathMax;
	}
    /**
     * ファイル物理パス(追加用） パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM beforeHidPathMax ファイル物理パス(追加用） パラメータ
     */
	public void setBeforeHidPathMax(String[] beforeHidPathMax) {
		this.beforeHidPathMax = beforeHidPathMax;
	}
	/**
     * ファイル物理パス(追加用） パラメータを取得する。<br/>
     * <br/>
     *
     * @return ファイル物理パス(追加用） パラメータ
     */
	public String[] getAfterHidPathMax() {
		return afterHidPathMax;
	}
	/**
     * ファイル物理パス(追加用） パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM afterHidPathMax ファイル物理パス(追加用） パラメータ
     */
	public void setAfterHidPathMax(String[] afterHidPathMax) {
		this.afterHidPathMax = afterHidPathMax;
	}
	/**
     * ファイル物理パス(追加用） パラメータを取得する。<br/>
     * <br/>
     *
     * @return ファイル物理パス(追加用） パラメータ
     */
	public String[] getBeforeHidPathMin() {
		return beforeHidPathMin;
	}
	/**
     * ファイル物理パス(追加用） パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM beforeHidPathMin ファイル物理パス(追加用） パラメータ
     */
	public void setBeforeHidPathMin(String[] beforeHidPathMin) {
		this.beforeHidPathMin = beforeHidPathMin;
	}
	/**
     * ファイル物理パス(追加用） パラメータを取得する。<br/>
     * <br/>
     *
     * @return ファイル物理パス(追加用） パラメータ
     */
	public String[] getAfterHidPathMin() {
		return afterHidPathMin;
	}
	/**
     * ファイル物理パス(追加用） パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM afterHidPathMin ファイル物理パス(追加用） パラメータ
     */
	public void setAfterHidPathMin(String[] afterHidPathMin) {
		this.afterHidPathMin = afterHidPathMin;
	}
	/**
     * 画像名 パラメータを取得する。<br/>
     * <br/>
     *
     * @return 画像名 パラメータ
     */
	public String[] getEditAfterFileName() {
		return editAfterFileName;
	}
	/**
     * 画像名 パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM editAfterFileName 画像名 パラメータ
     */
	public void setEditAfterFileName(String[] editAfterFileName) {
		this.editAfterFileName = editAfterFileName;
	}
	/**
     * ファイル物理パス(追加用） パラメータを取得する。<br/>
     * <br/>
     *
     * @return ファイル物理パス(追加用） パラメータ
     */
    public String[] getUploadBeforeHidPath() {
		return uploadBeforeHidPath;
	}
    /**
     * ファイル物理パス(追加用） パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM uploadBeforeHidPath ファイル物理パス(追加用） パラメータ
     */
	public void setUploadBeforeHidPath(String[] uploadBeforeHidPath) {
		this.uploadBeforeHidPath = uploadBeforeHidPath;
	}
	/**
     * ファイル物理パス(追加用） パラメータを取得する。<br/>
     * <br/>
     *
     * @return ファイル物理パス(追加用） パラメータ
     */
	public String[] getUploadAfterHidPath() {
		return uploadAfterHidPath;
	}
	 /**
     * ファイル物理パス(追加用） パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM uploadAfterHidPath ファイル物理パス(追加用） パラメータ
     */
	public void setUploadAfterHidPath(String[] uploadAfterHidPath) {
		this.uploadAfterHidPath = uploadAfterHidPath;
	}
	/**
     * 画像名 パラメータを取得する。<br/>
     * <br/>
     *
     * @return 画像名 パラメータ
     */
    public String[] getEditBeforeFileName() {
        return editBeforeFileName;
    }
    /**
     * 画像名 パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM editBeforeFileName 画像名 パラメータ
     */
    public void setEditBeforeFileName(String[] editBeforeFileName) {
        this.editBeforeFileName = editBeforeFileName;
    }
    /**
     * アップロードの閲覧権限（更新保持用） パラメータを取得する。<br/>
     * <br/>
     *
     * @return アップロードの閲覧権限（更新保持用） パラメータ
     */
    public String[] getEditOldRoleId() {
        return editOldRoleId;
    }
    /**
     * アップロードの閲覧権限（更新保持用） パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM editOldRoleId アップロードの閲覧権限（更新保持用） パラメータ
     */
    public void setEditOldRoleId(String[] editOldRoleId) {
        this.editOldRoleId = editOldRoleId;
    }


    /**
     * システム物件CD パラメータを取得する。<br/>
     * <br/>
     *
     * @return システム物件CD パラメータ
     */
    public String getSysHousingCd() {
        return sysHousingCd;
    }

    /**
     * システム物件CD パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM roleId システム物件CD パラメータ
     */
    public void setSysHousingCd(String sysHousingCd) {
        this.sysHousingCd = sysHousingCd;
    }

    /**
     * システムリフォームCD パラメータを取得する。<br/>
     * <br/>
     *
     * @return システムリフォームCD パラメータ
     */
    public String getSysReformCd() {
        return sysReformCd;
    }

    /**
     * システムリフォームCD パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM roleId システムリフォームCD パラメータ
     */
    public void setSysReformCd(String sysReformCd) {
        this.sysReformCd = sysReformCd;
    }

    /**
     * 表示順 パラメータを取得する。<br/>
     * 表示順 パラメータの値は、フレームワークの URL マッピングで使用する 表示順 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 表示順 パラメータ
     */
    public String[] getUploadSortOrder() {
        return uploadSortOrder;
    }

    /**
     * 表示順 パラメータを設定する。<br/>
     * 表示順 パラメータの値は、フレームワークの URL マッピングで使用する 表示順 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param sortOrder
     *            表示順 パラメータ
     */
    public void setUploadSortOrder(String[] uploadSortOrder) {
        this.uploadSortOrder = uploadSortOrder;
    }

    /**
     * before 画像パス名 パラメータを取得する。<br/>
     * before 画像パス名 パラメータの値は、フレームワークの URL マッピングで使用する before 画像パス名
     * クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return before 画像パス名 パラメータ
     */
    public FileItem[] getUploadBeforePathName() {
        return uploadBeforePathName;
    }

    /**
     * before 画像パス名 パラメータを設定する。<br/>
     * before 画像パス名 パラメータの値は、フレームワークの URL マッピングで使用する before 画像パス名
     * クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param beforePathName
     *            before 画像パス名 パラメータ
     */
    public void setUploadBeforePathName(FileItem[] uploadBeforePathName) {
        this.uploadBeforePathName = uploadBeforePathName;
    }

    /**
     * before 画像コメント パラメータを取得する。<br/>
     * before 画像コメント パラメータの値は、フレームワークの URL マッピングで使用する before 画像コメント
     * クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return before 画像コメント パラメータ
     */
    public String[] getUploadBeforeComment() {
        return uploadBeforeComment;
    }

    /**
     * before 画像コメント パラメータを設定する。<br/>
     * before 画像コメント パラメータの値は、フレームワークの URL マッピングで使用する before 画像コメント
     * クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param beforeComment
     *            before 画像コメント パラメータ
     */
    public void setUploadBeforeComment(String[] uploadBeforeComment) {
        this.uploadBeforeComment = uploadBeforeComment;
    }

    /**
     * after 画像パス名 パラメータを取得する。<br/>
     * after 画像パス名 パラメータの値は、フレームワークの URL マッピングで使用する after 画像パス名 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return after 画像パス名 パラメータ
     */
    public FileItem[] getUploadAfterPathName() {
        return uploadAfterPathName;
    }

    /**
     * after 画像パス名 パラメータを設定する。<br/>
     * after 画像パス名 パラメータの値は、フレームワークの URL マッピングで使用する after 画像パス名 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param afterPathName
     *            after 画像パス名 パラメータ
     */
    public void setUploadAfterPathName(FileItem[] uploadAfterPathName) {
        this.uploadAfterPathName = uploadAfterPathName;
    }

    /**
     * after 画像コメント パラメータを取得する。<br/>
     * after 画像コメント パラメータの値は、フレームワークの URL マッピングで使用する after 画像コメント
     * クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return after 画像コメント パラメータ
     */
    public String[] getUploadAfterComment() {
        return uploadAfterComment;
    }

    /**
     * after 画像コメント パラメータを設定する。<br/>
     * after 画像コメント パラメータの値は、フレームワークの URL マッピングで使用する after 画像コメント
     * クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param afterComment
     *            after 画像コメント パラメータ
     */
    public void setUploadAfterComment(String[] uploadAfterComment) {
        this.uploadAfterComment = uploadAfterComment;
    }

    /**
     * 表示順 パラメータを取得する。<br/>
     * 表示順 パラメータの値は、フレームワークの URL マッピングで使用する 表示順 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 表示順 パラメータ
     */
    public String[] getEditSortOrder() {
        return editSortOrder;
    }

    /**
     * 表示順 パラメータを設定する。<br/>
     * 表示順 パラメータの値は、フレームワークの URL マッピングで使用する 表示順 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param sortOrder
     *            表示順 パラメータ
     */
    public void setEditSortOrder(String[] editSortOrder) {
        this.editSortOrder = editSortOrder;
    }

    /**
     * before 画像パス名 パラメータを取得する。<br/>
     * before 画像パス名 パラメータの値は、フレームワークの URL マッピングで使用する before 画像パス名
     * クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return before 画像パス名 パラメータ
     */
    public String[] getEditBeforePathName() {
        return editBeforePathName;
    }

    /**
     * before 画像パス名 パラメータを設定する。<br/>
     * before 画像パス名 パラメータの値は、フレームワークの URL マッピングで使用する before 画像パス名
     * クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param beforePathName
     *            before 画像パス名 パラメータ
     */
    public void setEditBeforePathName(String[] editBeforePathName) {
        this.editBeforePathName = editBeforePathName;
    }

    /**
     * before 画像コメント パラメータを取得する。<br/>
     * before 画像コメント パラメータの値は、フレームワークの URL マッピングで使用する before 画像コメント
     * クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return before 画像コメント パラメータ
     */
    public String[] getEditBeforeComment() {
        return editBeforeComment;
    }

    /**
     * 枝番 パラメータを取得する。<br/>
     * <br/>
     *
     * @return 枝番 パラメータ
     */
    public String[] getDivNo() {
        return divNo;
    }

    /**
     * 枝番 パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM roleId 枝番 パラメータ
     */
    public void setDivNo(String[] divNo) {
        this.divNo = divNo;
    }

    /**
     * 削除フラグ パラメータを取得する。<br/>
     * <br/>
     *
     * @return 削除フラグ パラメータ
     */
    public String[] getDelFlg() {
        return delFlg;
    }

    /**
     * 削除フラグ パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM roleId 削除フラグ パラメータ
     */
    public void setDelFlg(String[] delFlg) {
        this.delFlg = delFlg;
    }

    /**
     * before 画像コメント パラメータを設定する。<br/>
     * before 画像コメント パラメータの値は、フレームワークの URL マッピングで使用する before 画像コメント
     * クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param beforeComment
     *            before 画像コメント パラメータ
     */
    public void setEditBeforeComment(String[] editBeforeComment) {
        this.editBeforeComment = editBeforeComment;
    }

    /**
     * after 画像パス名 パラメータを取得する。<br/>
     * after 画像パス名 パラメータの値は、フレームワークの URL マッピングで使用する after 画像パス名 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return after 画像パス名 パラメータ
     */
    public String[] getEditAfterPathName() {
        return editAfterPathName;
    }

    /**
     * after 画像パス名 パラメータを設定する。<br/>
     * after 画像パス名 パラメータの値は、フレームワークの URL マッピングで使用する after 画像パス名 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param afterPathName
     *            after 画像パス名 パラメータ
     */
    public void setEditAfterPathName(String[] editAfterPathName) {
        this.editAfterPathName = editAfterPathName;
    }

    /**
     * after 画像コメント パラメータを取得する。<br/>
     * after 画像コメント パラメータの値は、フレームワークの URL マッピングで使用する after 画像コメント
     * クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return after 画像コメント パラメータ
     */
    public String[] getEditAfterComment() {
        return editAfterComment;
    }

    /**
     * after 画像コメント パラメータを設定する。<br/>
     * after 画像コメント パラメータの値は、フレームワークの URL マッピングで使用する after 画像コメント
     * クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param afterComment
     *            after 画像コメント パラメータ
     */
    public void setEditAfterComment(String[] editAfterComment) {
        this.editAfterComment = editAfterComment;
    }

    /**
     * 閲覧権限 パラメータを取得する。<br/>
     * 閲覧権限 パラメータの値は、フレームワークの URL マッピングで使用する 閲覧権限 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 閲覧権限 パラメータ
     */
    public String[] getEditRoleId() {
        return editRoleId;
    }

    /**
     * 閲覧権限 パラメータを設定する。<br/>
     * 閲覧権限 パラメータの値は、フレームワークの URL マッピングで使用する 閲覧権限 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param roleId
     *            閲覧権限 パラメータ
     */
    public void setEditRoleId(String[] editRoleId) {
        this.editRoleId = editRoleId;
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
     * 物件名称 パラメータを設定する。<br/>
     * 物件名称 パラメータの値は、フレームワークの URL マッピングで使用する 物件名称 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param displayHousingName
     *            物件名称 パラメータ
     */
    public void setDisplayHousingName(String displayHousingName) {
        this.displayHousingName = displayHousingName;
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
     * 物件番号 パラメータを設定する。<br/>
     * 物件番号 パラメータの値は、フレームワークの URL マッピングで使用する 物件番号 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param housingCd
     *            物件番号 パラメータ
     */
    public void setHousingCd(String housingCd) {
        this.housingCd = housingCd;
    }

    /**
     * 物件種類CDを取得する。<br/>
     * 物件種類CDの値は、フレームワークの URL マッピングで使用する 物件種類CD クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return before 物件種類CD
     */
    public String getHousingKindCd() {
        return housingKindCd;
    }

    /**
     * 物件種類CDを設定する。<br/>
     * 物件種類CDの値は、フレームワークの URL マッピングで使用する 物件種類CD クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param housingKindCd
     *            物件種類CD
     */
    public void setHousingKindCd(String housingKindCd) {
        this.housingKindCd = housingKindCd;
    }

    /**
     * before 画像パス名 パラメータを取得する。<br/>
     * before 画像パス名 パラメータの値は、フレームワークの URL マッピングで使用する before 画像パス名
     * クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return before 画像パス名 パラメータ
     */
    public String[] getUploadBeforeFileName() {
        return uploadBeforeFileName;
    }

    /**
     * before 画像パス名 パラメータを設定する。<br/>
     * before 画像パス名 パラメータの値は、フレームワークの URL マッピングで使用する before 画像パス名
     * クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param uploadBeforeFileName
     *            before 画像パス名 パラメータ
     */
    public void setUploadBeforeFileName(String[] uploadBeforeFileName) {
        this.uploadBeforeFileName = uploadBeforeFileName;
    }

    /**
     * after 画像パス名 パラメータを取得する。<br/>
     * after 画像パス名 パラメータの値は、フレームワークの URL マッピングで使用する after 画像パス名 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return after 画像パス名 パラメータ
     */
    public String[] getUploadAfterFileName() {
        return uploadAfterFileName;
    }

    /**
     * after 画像パス名 パラメータを設定する。<br/>
     * after 画像パス名 パラメータの値は、フレームワークの URL マッピングで使用する after 画像パス名 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param uploadAfterFileName
     *            after 画像パス名 パラメータ
     */
    public void setUploadAfterFileName(String[] uploadAfterFileName) {
        this.uploadAfterFileName = uploadAfterFileName;
    }


    /**
     * コメントを取得する。<br/>
     * コメントの値は、フレームワークの URL マッピングで使用する コメント クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return コメント
     */
    public String getCommand() {
        return command;
    }

    /**
     * コメントを設定する。<br/>
     * コメントの値は、フレームワークの URL マッピングで使用する コメント クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param command
     *            コメント
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * CodeLookupを取得する。<br/>
     * CodeLookupの値は、フレームワークの URL マッピングで使用する CodeLookup クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return CodeLookup
     */
    public CodeLookupManager getCodeLookupManager() {
        return codeLookupManager;
    }

    /**
     * CodeLookupを設定する。<br/>
     * CodeLookupの値は、フレームワークの URL マッピングで使用する CodeLookup クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param codeLookupManager
     *            コメント
     */
    public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
        this.codeLookupManager = codeLookupManager;
    }

    /**
     * 閲覧権限を取得する。<br/>
     * 閲覧権限の値は、フレームワークの URL マッピングで使用する 閲覧権限 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 閲覧権限
     */
    public String[] getUploadRoleId() {
        return uploadRoleId;
    }

    /**
     * 閲覧権限を設定する。<br/>
     * 閲覧権限の値は、フレームワークの URL マッピングで使用する 閲覧権限 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param 閲覧権限
     *            uploadRoleId
     */
    public void setUploadRoleId(String[] uploadRoleId) {
        this.uploadRoleId = uploadRoleId;
    }
    /**
     * 画像ファイル名 パラメータを取得する。<br/>
     * <br/>
     *
     * @return 画像ファイル名 パラメータ
     */
    public String getAfterFileName() {
        return afterFileName;
    }
    /**
     * 画像ファイル名 パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM afterFileName 画像ファイル名 パラメータ
     */
    public void setAfterFileName(String afterFileName) {
        this.afterFileName = afterFileName;
    }
    /**
     * 画像ファイル名 パラメータを取得する。<br/>
     * <br/>
     *
     * @return 画像ファイル名 パラメータ
     */
    public String getBeforeFileName() {
        return beforeFileName;
    }
    /**
     * 画像ファイル名 パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM afterFileName 画像ファイル名 パラメータ
     */
    public void setBeforeFileName(String beforeFileName) {
        this.beforeFileName = beforeFileName;
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
     *
     * @return 正常時 true、エラー時 false
     */
    public boolean validate(List<ValidationFailure> errors) {
        int startSize = errors.size();

        if ("insert".equals(this.command)) {

        	String[] wkBeforePathName = null;

        	if(this.getUploadBeforePathName()!=null){
        		wkBeforePathName = new String[this.getUploadBeforePathName().length];
        		for (int i = 0; i < this.getUploadBeforePathName().length; i++) {
        			wkBeforePathName[i]=this.getUploadBeforePathName()[i].getName();
        		}

        	}else{
        		wkBeforePathName = new String[this.getUploadBeforeFileName().length];
        		wkBeforePathName = this.getUploadBeforeFileName();
        	}

        	String[] wkAfterPathName = null;

        	if(this.getUploadAfterPathName()!=null){
        		wkAfterPathName = new String[this.getUploadAfterPathName().length];
        		for (int i = 0; i < this.getUploadAfterPathName().length; i++) {
        			wkAfterPathName[i]=this.getUploadAfterPathName()[i].getName();
        		}

        	}else{
        		wkAfterPathName = new String[this.getUploadAfterFileName().length];
        		wkAfterPathName = this.getUploadAfterFileName();
        	}


            // Validate sort_order 表示順
            ValidationChain valUploadSortOrder = new ValidationChain(
                    "field.reformImg.SortOrder", getUploadSortOrder()[0]);

            // 必須入力チェック
            valUploadSortOrder.addValidation(new NullOrEmptyCheckValidation());

            // 最大桁数 3桁
            valUploadSortOrder.addValidation(new MaxLengthValidation(3));

            // 半角数字
            valUploadSortOrder.addValidation(new NumericValidation());

            valUploadSortOrder.validate(errors);

            // Validate before_comment before 画像コメント
            ValidationChain valUploadBeforeComment = new ValidationChain(
                    "field.reformImg.BeformComment",
                    getUploadBeforeComment()[0]);

            // 最大桁数 50桁
            valUploadBeforeComment.addValidation(new MaxLengthValidation(50));

            valUploadBeforeComment.validate(errors);

            // Validate before_path 画像パス名
            ValidationChain valUploadBeforePath = new ValidationChain(
                    "field.reformImg.BeforePathName",wkBeforePathName[0]);

            // アップロード画像_Beforeを選択する場合、コメント_Beforeを入力していない場合
            valUploadBeforePath
                    .addValidation(new NullOrEmptyCheckValidation());

            valUploadBeforePath.validate(errors);

            if(this.getUploadBeforePathName()!=null){
       		 	// Validate path_name 画像
               ValidationChain valPathName = new ValidationChain(
                       "field.reformImg.BeforePathName", getUploadBeforePathName()[0].getSize());
               // JPGチェック
               valPathName.addValidation(new ReformImgPathJpgValidation(getUploadBeforePathName()[0].getName(),"jpg"));
               // ファイルサイズチェック
               valPathName.addValidation(new ReformImgSizeValidation(PanaCommonConstant.updFileSize));
               valPathName.validate(errors);
            }

            // Validate after_comment after 画像コメント
            ValidationChain valUploadAfterComment = new ValidationChain(
                    "field.reformImg.AfterComment", getUploadAfterComment()[0]);

            // 最大桁数 50桁
            valUploadAfterComment.addValidation(new MaxLengthValidation(50));

            valUploadAfterComment.validate(errors);

            // Validate after_path 画像パス名
            ValidationChain valUploadAfterPath = new ValidationChain(
                    "field.reformImg.AfterPathName",wkAfterPathName[0]);

            // 必須入力チェック
            valUploadAfterPath
                    .addValidation(new NullOrEmptyCheckValidation());

            valUploadAfterPath.validate(errors);

            if(this.getUploadAfterPathName()!=null){
      		  // Validate path_name 画像
              ValidationChain valPathName = new ValidationChain(
                      "field.reformImg.AfterPathName", getUploadAfterPathName()[0].getSize());
              // JPGチェック
              valPathName.addValidation(new ReformImgPathJpgValidation(getUploadAfterPathName()[0].getName(),"jpg"));
              // ファイルサイズチェック
              valPathName.addValidation(new ReformImgSizeValidation(PanaCommonConstant.updFileSize));
              valPathName.validate(errors);
           }

            // Validate role_no 閲覧権限
            ValidationChain valUploadRoleNo = new ValidationChain(
                    "field.reformImg.RoleId", getUploadRoleId()[0]);

            // 必須入力チェック
            valUploadRoleNo.addValidation(new NullOrEmptyCheckValidation());

            // パターンチェック
            valUploadRoleNo.addValidation(new CodeLookupValidation(
                    this.codeLookupManager, "ImageInfoRoleId"));

            valUploadRoleNo.validate(errors);

        } else {
            if (this.getDivNo() != null) {
                for (int i = 0; i < this.getDivNo().length; i++) {

                    // Validate sort_order 表示順
                    ValidationChain valEditSortOrder = new ValidationChain(
                            "field.reformImg.SortOrder", getEditSortOrder()[i]);

                    // 必須入力チェック
                    valEditSortOrder.addValidation(new LineAdapter(
                            new NullOrEmptyCheckValidation(), i + 1));

                    // 最大桁数 3桁
                    valEditSortOrder.addValidation(new LineAdapter(
                            new MaxLengthValidation(3), i + 1));

                    // 半角数字
                    valEditSortOrder.addValidation(new LineAdapter(
                            new NumericValidation(), i + 1));

                    valEditSortOrder.validate(errors);

                    // Validate before_comment before 画像コメント
                    ValidationChain valEditBeforeComment = new ValidationChain(
                            "field.reformImg.BeformComment",
                            getEditBeforeComment()[i]);

                    // 最大桁数 50桁
                    valEditBeforeComment.addValidation(new LineAdapter(
                            new MaxLengthValidation(50), i + 1));

                    valEditBeforeComment.validate(errors);

                    // Validate before_comment before 画像コメント
                    ValidationChain valEditAfterComment = new ValidationChain(
                            "field.reformImg.AfterComment",
                            getEditAfterComment()[i]);

                    // 最大桁数 50桁
                    valEditAfterComment.addValidation(new LineAdapter(
                            new MaxLengthValidation(50), i + 1));

                    valEditAfterComment.validate(errors);

                    // Validate role_no 閲覧権限
                    ValidationChain valEditRoleNo = new ValidationChain(
                            "field.reformImg.RoleId", getEditRoleId()[i]);

                    // 必須入力チェック
                    valEditRoleNo.addValidation(new LineAdapter(
                            new NullOrEmptyCheckValidation(), i + 1));

                    // パターンチェック
                    valEditRoleNo.addValidation(new LineAdapter(
                            new CodeLookupValidation(this.codeLookupManager,
                                    "ImageInfoRoleId"), i + 1));

                    valEditRoleNo.validate(errors);

                    ValidationChain valOldRole_id = new ValidationChain(
                            "field.reformImg.RoleId", getEditOldRoleId()[i]);

                    // パターンチェック
                    valOldRole_id.addValidation(new LineAdapter(
                            new CodeLookupValidation(this.codeLookupManager,
                                    "ImageInfoRoleId"), i + 1));
                    valOldRole_id.validate(errors);
                }
            }
        }

        return (startSize == errors.size());
    }

    /**
     * 登録条件オブジェクトを作成する。<br/>
     * 出力用なのでページ処理を除外した登録条件を生成する。<br/>
     * <br/>
     *
     * @return 登録条件オブジェクト
     */
    public ReformImg newToReformImg() {
        ReformImg reformImg = new ReformImg();

        // システムリフォームCD
        reformImg.setSysReformCd(this.getSysReformCd());

        //  before 画像ファイル名
        reformImg.setBeforeFileName(this.getUploadBeforeFileName()[0]);
        // コメント_Before
        reformImg.setBeforeComment(this.getUploadBeforeComment()[0]);

        //  after 画像ファイル名
        reformImg.setAfterFileName(this.getUploadAfterFileName()[0]);
        // コメント_After
        reformImg.setAfterComment(this.getUploadAfterComment()[0]);

        // 表示順
        reformImg.setSortOrder(Integer.valueOf(this.getUploadSortOrder()[0]));
        // 閲覧権限
        reformImg.setRoleId(this.getUploadRoleId()[0]);

        return reformImg;
    }

    /**
     * 検索更新条件オブジェクトを作成する。<br/>
     * 出力用なのでページ処理を除外した検索更新条件を生成する。<br/>
     * <br/>
     *
     * @return 検索更新条件オブジェクト
     */
    public void copyToReformImg(ReformImg reformImg, int idx) {

        // 表示順
        reformImg.setSortOrder(Integer.valueOf(this.getEditSortOrder()[idx]));

        // コメント_After
        reformImg.setAfterComment(this.getEditAfterComment()[idx]);

        // コメント_Before
        reformImg.setBeforeComment(this.getEditBeforeComment()[idx]);

        // 閲覧権限
        reformImg.setRoleId(this.getEditRoleId()[idx]);
    }
    /**
     * 渡されたバリーオブジェクトから Form へ初期値を設定する。<br/>
     * <br/>
     * @param adminUser AdminUserInterface　を実装した、ユーザー情報管理用バリーオブジェクト
     * @param adminRole AdminUserRoleInterface を実装したユーザーロール管理用バリーオブジェクト
     */
    public void setDefaultData(List<ReformImg> reformImgresults) {
    	String[] divNo = new String[reformImgresults.size()];
        String[] beforePathName = new String[reformImgresults.size()];
        String[] beforeFileName = new String[reformImgresults.size()];
        String[] beforeComment = new String[reformImgresults.size()];
        String[] afterPathName = new String[reformImgresults.size()];
        String[] afterFileName = new String[reformImgresults.size()];
        String[] afterComment = new String[reformImgresults.size()];
        String[] sortOrder = new String[reformImgresults.size()];
        String[] roleId = new String[reformImgresults.size()];

        for (int i = 0; i < reformImgresults.size(); i++) {
            // 枝番
            divNo[i] = PanaStringUtils.toString(reformImgresults.get(i).getDivNo());
            // 表示順
            sortOrder[i] = PanaStringUtils.toString(reformImgresults.get(i).getSortOrder());
            // before_画像
            beforePathName[i] = reformImgresults.get(i).getBeforePathName();
            // before_コメント
            beforeComment[i] = reformImgresults.get(i).getBeforeComment();
            // after_画像
            afterPathName[i] = reformImgresults.get(i).getAfterPathName();
            // after_コメント
            afterComment[i] = reformImgresults.get(i).getAfterComment();
            // 閲覧権限
            roleId[i] = reformImgresults.get(i).getRoleId();
            // before_画像
            beforeFileName[i] = reformImgresults.get(i).getBeforeFileName();
            // after_画像
            afterFileName[i] = reformImgresults.get(i).getAfterFileName();
        }
        // 枝番
        this.setDivNo(divNo);
        // 表示順
        this.setEditSortOrder(sortOrder);
        // before_コメント
        this.setEditBeforeComment(beforeComment);
        // after_コメント
        this.setEditAfterComment(afterComment);
        // 閲覧権限
        this.setEditRoleId(roleId);
        // before_画像
        this.setEditBeforePathName(beforePathName);
        this.setEditBeforeFileName(beforeFileName);
        // after_画像
        this.setEditAfterPathName(afterPathName);
        this.setEditAfterFileName(afterFileName);
        this.setEditOldRoleId(roleId);

    }
}
