package jp.co.transcosmos.dm3.corePana.model.housing.form;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.validation.ReformImgPathJpgValidation;
import jp.co.transcosmos.dm3.corePana.validation.ReformImgSizeValidation;
import jp.co.transcosmos.dm3.corePana.vo.HousingInspection;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
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
 * fan			2015.04.23	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * この Form のバリデーションは、使用するモード（追加処理 or 更新処理）で異なるので、 フレームワークが提供する Validateable
 * インターフェースは実装していない。<br/>
 * バリデーション実行時のパラメータが通常と異なるので注意する事。<br/>
 *
 */
public class PanaHousingInspectionForm implements Validateable {

    /** 共通コード変換処理 */
    private CodeLookupManager codeLookupManager;

    /**
     * デフォルトコンストラクター<br/>
     * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
     * <br/>
     */
    PanaHousingInspectionForm() {
        super();
    }

    /**
     * コンストラクター<br/>
     * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
     * <br/>
     * @param　codeLookupManager　共通コード変換処理
     */
    PanaHousingInspectionForm(CodeLookupManager codeLookupManager) {
        super();
        this.codeLookupManager = codeLookupManager;
    }

    /**
     * システム物件CD
     */
    private String sysHousingCd;

    /**
     * コメント
     */
    private String command;

    /**
     * 物件番号
     */
    private String housingCd;

    /**
     * 削除のフラグ
     */
    private String housingDel;
    /**
     * 削除のフラグ
     */
    private String housingImgDel;

    /**
     * 表示用物件名
     */
    private String displayHousingName;

    /**
     * 住宅診断実施有無
     */
    private String housingInspection;

    /**
     * 住宅診断ファイル
     */
    private FileItem housingFile;

    /**
     * 画像ファイル
     */
    private FileItem housingImgFile;

    /**
     * wk住宅診断ファイル有無フラグ
     */
    private String loadFlg;

    /**
     * wk画像ファイル有無フラグ
     */
    private String imgFlg;

    /**
     * 物件種類CD
     */
    private String housingKindCd;

    /**
     * ロード済みファイル
     */
    private String loadFile;

    /**
     * 画像ファイル
     */
    private String imgFile;

    /**
     * ロード済みファイル
     */
    private String loadFilePath;

    /**
     * 画像ファイル
     */
    private String imgFilePath;

    /** ファイル名(追加用） */
    private String addHidFileName;

    /** ファイル名(追加用） */
    private String addHidImgName;

    /**
     * 評価基準の定義
     */
    private String[] inspectionKey;

    /**
     * 評価基準の選択項目
     */
    private String[] inspectionTrust_result;

    /**
     * 確認範囲の選択項目
     */
    private String[] inspectionValue_label;

    /** ファイル物理パス(追加用） */
    private String hidPath;
    /** ファイル名(追加用） */
    private String hidImgPath;

    /** ファイル物理パス(追加用） */
    private String hidNewPath;
    /** ファイル名(追加用） */
    private String hidNewImgPath;

	/**
     * ファイル名(追加用） パラメータを取得する。<br/>
     * ファイル名(追加用） パラメータの値は、フレームワークの URL マッピングで使用する ファイル名(追加用）クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return ファイル名(追加用）パラメータ
     */
	public String getAddHidFileName() {
		return addHidFileName;
	}

	/**
     * ファイル名(追加用）パラメータを設定する。<br/>
     * ファイル名(追加用）パラメータの値は、フレームワークの URL マッピングで使用する ファイル名(追加用）クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param addHidFileName
     *            ファイル名(追加用）パラメータ
     */
	public void setAddHidFileName(String addHidFileName) {
		this.addHidFileName = addHidFileName;
	}

	/**
     * ファイル名(追加用） パラメータを取得する。<br/>
     * ファイル名(追加用） パラメータの値は、フレームワークの URL マッピングで使用する ファイル名(追加用）クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return ファイル名(追加用）パラメータ
     */
	public String getAddHidImgName() {
		return addHidImgName;
	}

	/**
     * ファイル名(追加用）パラメータを設定する。<br/>
     * ファイル名(追加用）パラメータの値は、フレームワークの URL マッピングで使用する ファイル名(追加用）クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param addHidImgName
     *            ファイル名(追加用）パラメータ
     */
	public void setAddHidImgName(String addHidImgName) {
		this.addHidImgName = addHidImgName;
	}

	/**
     * 削除のフラグ パラメータを取得する。<br/>
     * 削除のフラグ パラメータの値は、フレームワークの URL マッピングで使用する 削除のフラグ クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 削除のフラグ パラメータ
     */
	public String getHousingImgDel() {
		return housingImgDel;
	}

	/**
     * 削除のフラグ パラメータを設定する。<br/>
     * 削除のフラグ パラメータの値は、フレームワークの URL マッピングで使用する 削除のフラグ クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param housingImgDel
     *            削除のフラグ パラメータ
     */
	public void setHousingImgDel(String housingImgDel) {
		this.housingImgDel = housingImgDel;
	}

	/**
     * ロード済みファイル パラメータを取得する。<br/>
     * ロード済みファイル パラメータの値は、フレームワークの URL マッピングで使用するロード済みファイル クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return ロード済みファイル パラメータ
     */
	public String getLoadFilePath() {
		return loadFilePath;
	}

	/**
     * ロード済みファイル パラメータを設定する。<br/>
     * ロード済みファイル パラメータの値は、フレームワークの URL マッピングで使用する ロード済みファイル クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param loadFilePath
     *           ロード済みファイル パラメータ
     */
	public void setLoadFilePath(String loadFilePath) {
		this.loadFilePath = loadFilePath;
	}

	/**
     * ロード済みファイル パラメータを取得する。<br/>
     * ロード済みファイル パラメータの値は、フレームワークの URL マッピングで使用するロード済みファイル クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return ロード済みファイル パラメータ
     */
	public String getImgFilePath() {
		return imgFilePath;
	}

	/**
     * ロード済みファイル パラメータを設定する。<br/>
     * ロード済みファイル パラメータの値は、フレームワークの URL マッピングで使用する ロード済みファイル クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param imgFilePath
     *           ロード済みファイル パラメータ
     */
	public void setImgFilePath(String imgFilePath) {
		this.imgFilePath = imgFilePath;
	}

	/**
     * ファイル物理パス(追加用）パラメータを取得する。<br/>
     * ファイル物理パス(追加用）パラメータの値は、フレームワークの URL マッピングで使用する ファイル物理パス(追加用）クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return ファイル名(追加用）パラメータ
     */
	public String getHidPath() {
		return hidPath;
	}

	/**
     * ファイル物理パス(追加用）パラメータを設定する。<br/>
     * ファイル物理パス(追加用）パラメータの値は、フレームワークの URL マッピングで使用する ファイル物理パス(追加用）クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param hidImgPath
     *            ファイル物理パス(追加用）パラメータ
     */
	public void setHidPath(String hidPath) {
		this.hidPath = hidPath;
	}
	/**
     * ファイル物理パス(追加用） パラメータを取得する。<br/>
     * ファイル物理パス(追加用）パラメータの値は、フレームワークの URL マッピングで使用する ファイル物理パス(追加用）クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return ファイル物理パス(追加用）パラメータ
     */
	public String getHidImgPath() {
		return hidImgPath;
	}

	/**
     * ファイル物理パス(追加用）パラメータを設定する。<br/>
     * ファイル物理パス(追加用）パラメータの値は、フレームワークの URL マッピングで使用する ファイル物理パス(追加用）クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param hidImgPath
     *            ファイル名(追加用）パラメータ
     */
	public void setHidImgPath(String hidImgPath) {
		this.hidImgPath = hidImgPath;
	}



	/**
     * ファイル物理パス(追加用）パラメータを取得する。<br/>
     * ファイル物理パス(追加用）パラメータの値は、フレームワークの URL マッピングで使用する ファイル物理パス(追加用）クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return ファイル名(追加用）パラメータ
     */
	public String getHidNewPath() {
		return hidNewPath;
	}

	/**
     * ファイル物理パス(追加用）パラメータを設定する。<br/>
     * ファイル物理パス(追加用）パラメータの値は、フレームワークの URL マッピングで使用する ファイル物理パス(追加用）クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param hidImgPath
     *            ファイル物理パス(追加用）パラメータ
     */
	public void setHidNewPath(String hidNewPath) {
		this.hidNewPath = hidNewPath;
	}
	/**
     * ファイル物理パス(追加用） パラメータを取得する。<br/>
     * ファイル物理パス(追加用）パラメータの値は、フレームワークの URL マッピングで使用する ファイル物理パス(追加用）クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return ファイル物理パス(追加用）パラメータ
     */
	public String getHidNewImgPath() {
		return hidNewImgPath;
	}

	/**
     * ファイル物理パス(追加用）パラメータを設定する。<br/>
     * ファイル物理パス(追加用）パラメータの値は、フレームワークの URL マッピングで使用する ファイル物理パス(追加用）クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param hidImgPath
     *            ファイル名(追加用）パラメータ
     */
	public void setHidNewImgPath(String hidNewImgPath) {
		this.hidNewImgPath = hidNewImgPath;
	}


	/**
     * システム物件CD パラメータを取得する。<br/>
     * システム物件CD パラメータの値は、フレームワークの URL マッピングで使用する システム物件CD クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return システム物件CD パラメータ
     */
    public String getSysHousingCd() {
        return sysHousingCd;
    }

    /**
     * システム物件CD パラメータを設定する。<br/>
     * システム物件CD パラメータの値は、フレームワークの URL マッピングで使用する システム物件CD クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param sysHousingCd
     *            システム物件CD パラメータ
     */
    public void setSysHousingCd(String sysHousingCd) {
        this.sysHousingCd = sysHousingCd;
    }

    /**
     * コメント パラメータを取得する。<br/>
     * コメント パラメータの値は、フレームワークの URL マッピングで使用する コメント クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return コメント パラメータ
     */
    public String getCommand() {
        return command;
    }

    /**
     * コメント パラメータを設定する。<br/>
     * コメント パラメータの値は、フレームワークの URL マッピングで使用する コメント クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param command
     *            コメント パラメータ
     */
    public void setCommand(String command) {
        this.command = command;
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
     * @param command
     *            物件番号 パラメータ
     */
    public void setHousingCd(String housingCd) {
        this.housingCd = housingCd;
    }

    /**
     * 削除のフラグ パラメータを取得する。<br/>
     * 削除のフラグ パラメータの値は、フレームワークの URL マッピングで使用削除のフラグる 削除のフラグ クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 削除のフラグ パラメータ
     */
    public String getHousingDel() {
        return housingDel;
    }

    /**
     * 削除のフラグ パラメータを設定する。<br/>
     * 削除のフラグ パラメータの値は、フレームワークの URL マッピングで使用する 削除のフラグ クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param command
     *            削除のフラグ パラメータ
     */
    public void setHousingDel(String housingDel) {
        this.housingDel = housingDel;
    }

    /**
     * 表示用物件名 パラメータを取得する。<br/>
     * 表示用物件名 パラメータの値は、フレームワークの URL マッピングで使用する 表示用物件名 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 表示用物件名 パラメータ
     */
    public String getDisplayHousingName() {
        return displayHousingName;
    }

    /**
     * 表示用物件名 パラメータを設定する。<br/>
     * 表示用物件名 パラメータの値は、フレームワークの URL マッピングで使用する 表示用物件名 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param command
     *            表示用物件名 パラメータ
     */
    public void setDisplayHousingName(String displayHousingName) {
        this.displayHousingName = displayHousingName;
    }

    /**
     * 住宅診断実施有無 パラメータを取得する。<br/>
     * 住宅診断実施有無 パラメータの値は、フレームワークの URL マッピングで使用する 住宅診断実施有無 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 住宅診断実施有無 パラメータ
     */
    public String getHousingInspection() {
        return housingInspection;
    }

    /**
     * 住宅診断実施有無 パラメータを設定する。<br/>
     * 住宅診断実施有無 パラメータの値は、フレームワークの URL マッピングで使用する 住宅診断実施有無 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param command
     *            住宅診断実施有無 パラメータ
     */
    public void setHousingInspection(String housingInspection) {
        this.housingInspection = housingInspection;
    }

    /**
     * 住宅診断ファイル パラメータを取得する。<br/>
     * 住宅診断ファイル パラメータの値は、フレームワークの URL マッピングで使用する 住宅診断ファイル クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 住宅診断ファイル パラメータ
     */
    public FileItem getHousingFile() {
        return housingFile;
    }

    /**
     * 住宅診断ファイル パラメータを設定する。<br/>
     * 住宅診断ファイル パラメータの値は、フレームワークの URL マッピングで使用する 住宅診断ファイル クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param command
     *            住宅診断ファイル パラメータ
     */
    public void setHousingFile(FileItem housingFile) {
        this.housingFile = housingFile;
    }

    /**
     * 画像ファイル パラメータを取得する。<br/>
     * 画像ファイル パラメータの値は、フレームワークの URL マッピングで使用する 画像ファイル クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 画像ファイル パラメータ
     */
    public FileItem getHousingImgFile() {
        return housingImgFile;
    }

    /**
     * 画像ファイル パラメータを設定する。<br/>
     * 画像ファイル パラメータの値は、フレームワークの URL マッピングで使用する 画像ファイル クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param command
     *            画像ファイル パラメータ
     */
    public void setHousingImgFile(FileItem housingImgFile) {
        this.housingImgFile = housingImgFile;
    }

    /**
     * wk住宅診断ファイル有無フラグ パラメータを取得する。<br/>
     * wk住宅診断ファイル有無フラグ パラメータの値は、フレームワークの URL マッピングで使用する wk住宅診断ファイル有無フラグ
     * クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return wk住宅診断ファイル有無フラグ パラメータ
     */
    public String getLoadFlg() {
        return loadFlg;
    }

    /**
     * wk住宅診断ファイル有無フラグ パラメータを設定する。<br/>
     * wk住宅診断ファイル有無フラグ パラメータの値は、フレームワークの URL マッピングで使用する wk住宅診断ファイル有無フラグ
     * クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param command
     *            wk住宅診断ファイル有無フラグ パラメータ
     */
    public void setLoadFlg(String loadFlg) {
        this.loadFlg = loadFlg;
    }

    /**
     * wk画像ファイル有無フラグ パラメータを取得する。<br/>
     * wk画像ファイル有無フラグ パラメータの値は、フレームワークの URL マッピングで使用する wk画像ファイル有無フラグ
     * クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return wk画像ファイル有無フラグ パラメータ
     */
    public String getImgFlg() {
        return imgFlg;
    }

    /**
     * wk画像ファイル有無フラグ パラメータを設定する。<br/>
     * wk画像ファイル有無フラグ パラメータの値は、フレームワークの URL マッピングで使用する wk画像ファイル有無フラグ
     * クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param command
     *            wk画像ファイル有無フラグ パラメータ
     */
    public void setImgFlg(String imgFlg) {
        this.imgFlg = imgFlg;
    }

    /**
     * 物件種類CD パラメータを取得する。<br/>
     * 物件種類CD パラメータの値は、フレームワークの URL マッピングで使用する 物件種類CD クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 物件種類CD パラメータ
     */
    public String getHousingKindCd() {
        return housingKindCd;
    }

    /**
     * 物件種類CD パラメータを設定する。<br/>
     * 物件種類CD パラメータの値は、フレームワークの URL マッピングで使用する 物件種類CD クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param command
     *            物件種類CD パラメータ
     */
    public void setHousingKindCd(String housingKindCd) {
        this.housingKindCd = housingKindCd;
    }

    /**
     * ロード済みファイル パラメータを取得する。<br/>
     * ロード済みファイル パラメータの値は、フレームワークの URL マッピングで使用する ロード済みファイル クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return ロード済みファイル パラメータ
     */
    public String getLoadFile() {
        return loadFile;
    }

    /**
     * ロード済みファイル パラメータを設定する。<br/>
     * ロード済みファイル パラメータの値は、フレームワークの URL マッピングで使用する ロード済みファイル クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param command
     *            ロード済みファイル パラメータ
     */
    public void setLoadFile(String loadFile) {
        this.loadFile = loadFile;
    }

    /**
     * 画像ファイル パラメータを取得する。<br/>
     * 画像ファイル パラメータの値は、フレームワークの URL マッピングで使用する 画像ファイル クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 画像ファイル パラメータ
     */
    public String getImgFile() {
        return imgFile;
    }

    /**
     * 画像ファイル パラメータを設定する。<br/>
     * 画像ファイル パラメータの値は、フレームワークの URL マッピングで使用する 画像ファイル クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param command
     *            画像ファイル パラメータ
     */
    public void setImgFile(String imgFile) {
        this.imgFile = imgFile;
    }

    /**
     * 評価基準の定義 パラメータを取得する。<br/>
     * 評価基準の定義 パラメータの値は、フレームワークの URL マッピングで使用する 評価基準の定義 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 評価基準の定義 パラメータ
     */
    public String[] getInspectionKey() {
        return inspectionKey;
    }

    /**
     * 評価基準の定義 パラメータを設定する。<br/>
     * 評価基準の定義 パラメータの値は、フレームワークの URL マッピングで使用する 評価基準の定義 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param command
     *            評価基準の定義 パラメータ
     */
    public void setInspectionKey(String[] inspectionKey) {
        this.inspectionKey = inspectionKey;
    }

    /**
     * 評価基準の選択項目 パラメータを取得する。<br/>
     * 評価基準の選択項目 パラメータの値は、フレームワークの URL マッピングで使用する 評価基準の選択項目 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 評価基準の選択項目 パラメータ
     */
    public String[] getInspectionTrust_result() {
        return inspectionTrust_result;
    }

    /**
     * 評価基準の選択項目 パラメータを設定する。<br/>
     * 評価基準の選択項目 パラメータの値は、フレームワークの URL マッピングで使用する 評価基準の選択項目 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param command
     *            評価基準の選択項目 パラメータ
     */
    public void setInspectionTrust_result(String[] inspectionTrust_result) {
        this.inspectionTrust_result = inspectionTrust_result;
    }

    /**
     * 評価基準の選択項目 パラメータを取得する。<br/>
     * 評価基準の選択項目 パラメータの値は、フレームワークの URL マッピングで使用する 評価基準の選択項目 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 評価基準の選択項目 パラメータ
     */
    public String[] getInspectionValue_label() {
        return inspectionValue_label;
    }

    /**
     * 評価基準の選択項目 パラメータを設定する。<br/>
     * 評価基準の選択項目 パラメータの値は、フレームワークの URL マッピングで使用する 評価基準の選択項目 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @param command
     *            評価基準の選択項目 パラメータ
     */
    public void setInspectionValue_label(String[] inspectionValue_label) {
        this.inspectionValue_label = inspectionValue_label;
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
    public void copyToHousingInspection(HousingInspection housingInspection,
            int i) {
        // システム物件CD
        housingInspection.setSysHousingCd(setString(getSysHousingCd(),
                housingInspection.getSysHousingCd()));

        // ステータスCD
        housingInspection.setInspectionKey(setString(getInspectionKey()[i],
                housingInspection.getInspectionKey()));
        // ユーザーID
        housingInspection.setInspectionTrust(Integer.valueOf(setString(
                getInspectionTrust_result()[i],
                String.valueOf(housingInspection.getInspectionTrust()))));
        // 備考
        housingInspection.setInspectionValue(Integer.valueOf(setString(
                getInspectionValue_label()[i],
                String.valueOf(housingInspection.getInspectionValue()))));
    }

    // String型の値設定
    private String setString(String input, String nullValue) {
        if (input == null || "".equals(input)) {
            return nullValue;
        }

        return input;
    }

    /**
     * バリデーション処理<br/>
     * リクエストパラメータのバリデーションを行う<br/>
     * <br/>
     *
     * @param errors
     *            エラー情報を格納するリストオブジェクト
     * @return 正常時 true、エラー時 false
     */
    @Override
    public boolean validate(List<ValidationFailure> errors) {

        int startSize = errors.size();

        if (this.inspectionKey != null) {
        	int count=0;
            for (int i = 0; i < this.getInspectionKey().length; i++) {

                String strInspectionTrust_result = this.getInspectionTrust_result()[i];
                String strInspectionValue_label = this.getInspectionValue_label()[i];

                String codeLookup = "";
                if(PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())){
                	codeLookup ="inspectionTrustMansion";
                }
                if(PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())){
                	codeLookup ="inspectionTrustHouse";
                }

                Iterator<String> priceIte = this.codeLookupManager
        				.getKeysByLookup(codeLookup);

                String codeLookupValue="";
        		while (priceIte.hasNext()) {
        			String price = priceIte.next();
        			if (price.equals(String.valueOf(i+1))) {
        				codeLookupValue = this.codeLookupManager.lookupValue(
        							codeLookup, price);
        			}
        		}

                if(!StringValidateUtil.isEmpty(strInspectionValue_label)){
                    ValidationChain valInspectionValue_label = new ValidationChain(
                    		codeLookupValue+"　"+"評価基準",strInspectionValue_label);
                    // パターンチェック
                    valInspectionValue_label
                            .addValidation(new CodeLookupValidation(
                                    this.codeLookupManager, "inspectionResult"));
                    valInspectionValue_label.validate(errors);
                }

                if(!StringValidateUtil.isEmpty(strInspectionTrust_result)){
                	 ValidationChain valInspectionTrust_result = new ValidationChain(
                			 codeLookupValue+"　"+"確認範囲",strInspectionTrust_result);
                     // パターンチェック
                     valInspectionTrust_result
                             .addValidation(new CodeLookupValidation(
                                     this.codeLookupManager,"inspectionLabel"));
                     valInspectionTrust_result.validate(errors);
                }

                if(PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())){
                	if(!StringValidateUtil.isEmpty(this.getInspectionKey()[i])){
                    	ValidationChain valInspectionKey = new ValidationChain(
                                "field.housingInspection.Key",this.getInspectionKey()[i]);
                        // パターンチェック
                    	valInspectionKey
                                .addValidation(new CodeLookupValidation(
                                        this.codeLookupManager, "inspectionTrustMansion"));
                    	valInspectionKey.validate(errors);
                    }
                }

                if(PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())){
                	if(!StringValidateUtil.isEmpty(this.getInspectionKey()[i])){
                    	ValidationChain valInspectionKey = new ValidationChain(
                                "field.housingInspection.Key",this.getInspectionKey()[i]);
                        // パターンチェック
                    	valInspectionKey
                                .addValidation(new CodeLookupValidation(
                                        this.codeLookupManager, "inspectionTrustHouse"));
                    	valInspectionKey.validate(errors);
                    }
                }

                // 住宅診断実施有無が「有」且つ　評価基準又は確認範囲が未選択
                if(count == 0){
	                if ("01".equals(this.getHousingInspection())) {
	        			if(StringValidateUtil.isEmpty(strInspectionValue_label) || StringValidateUtil.isEmpty(strInspectionTrust_result)){
	        				ValidationFailure vf = new ValidationFailure(
	                                "inspectionValue", "住宅診断実施有無", "評価基準又は確認範囲", null);
	                        errors.add(vf);
	                        count++;
	        			}
	                }
                }

                // 評価基準を選択した、対応する確認範囲を未選択
                if ((!StringValidateUtil.isEmpty(strInspectionValue_label) && StringValidateUtil.isEmpty(strInspectionTrust_result))) {
                		String param[] = new String[] { codeLookupValue+"　" };
                	 ValidationFailure vf = new ValidationFailure(
                             "eachExits", "評価基準", "確認範囲", param);
                     errors.add(vf);
        		} else if((StringValidateUtil.isEmpty(strInspectionValue_label) && !StringValidateUtil.isEmpty(strInspectionTrust_result))){
        			String param[] = new String[] { codeLookupValue+"　" };
        			ValidationFailure vf = new ValidationFailure(
                            "eachExits", "確認範囲", "評価基準", param);
                    errors.add(vf);
        		}
            }

            // 住宅診断実施有無
            ValidationChain valInspectionTrust_result = new ValidationChain(
                    "field.housingInspection",this.getHousingInspection());
            // パターンチェック
            valInspectionTrust_result
                    .addValidation(new CodeLookupValidation(
                            this.codeLookupManager,"housingInspection"));
            valInspectionTrust_result.validate(errors);

            // 関連チェック：住宅診断実施有無が「有」且つ　レーダーチャート画像がないの場合
            if ("01".equals(this.getHousingInspection())) {
            	if (("on".equals(this.getHousingImgDel()) && !"1".equals(this.getImgFlg())) || (StringValidateUtil.isEmpty(this.getImgFile()) && !"1".equals(this.getImgFlg()))) {
    				ValidationFailure vf = new ValidationFailure(
                            "housingInfoImg", "住宅診断実施有無", "アップロード画像", null);
                    errors.add(vf);
    			}
            }

            if(this.getHousingFile()!=null){
            	// Validate path_name 画像
            	ValidationChain valPathName = new ValidationChain(
                      "住宅診断ファイル", getHousingFile().getSize());
            	// PDFチェック
                valPathName.addValidation(new ReformImgPathJpgValidation(getHousingFile().getName(),"pdf"));
            	// ファイルサイズチェック
            	valPathName.addValidation(new ReformImgSizeValidation(PanaCommonConstant.updFileSize));
            	valPathName.validate(errors);
            }
            if(this.getHousingImgFile()!=null){
            	// Validate path_name 画像
            	ValidationChain valPathName = new ValidationChain(
                      "レーダーチャート画像", getHousingImgFile().getSize());
            	// jpgチェック
                valPathName.addValidation(new ReformImgPathJpgValidation(getHousingImgFile().getName(),"jpg"));
            	// ファイルサイズチェック
            	valPathName.addValidation(new ReformImgSizeValidation(PanaCommonConstant.updFileSize));
            	valPathName.validate(errors);
            }
        }

        return startSize == errors.size();
    }

    /**
     * 渡されたバリーオブジェクトから Form へ初期値を設定する。<br/>
     * <br/>
     * @param adminUser AdminUserInterface　を実装した、ユーザー情報管理用バリーオブジェクト
     * @param adminRole AdminUserRoleInterface を実装したユーザーロール管理用バリーオブジェクト
     */
    public void setDefaultData(JoinResult housingInfo,JoinResult buildingInfo,List<HousingInspection> HousingInspection, Map<String,String> housingExtInfo) {
    	if(housingInfo != null){
        	//物件名称
    		this.setDisplayHousingName(((HousingInfo)housingInfo.getItems().get("housingInfo")).getDisplayHousingName());
			//物件番号
    		this.setHousingCd(((HousingInfo)housingInfo.getItems().get("housingInfo")).getHousingCd());
        }
        if(buildingInfo != null){
			//物件種別
        	this.setHousingKindCd(((BuildingInfo)buildingInfo.getItems().get("buildingInfo")).getHousingKindCd());
        }
    	if(HousingInspection.size()>0){
			String[] inspectionKey = new  String[HousingInspection.size()];
			String[] inspectionTrust_result = new  String[HousingInspection.size()];
			String[] inspectionValue_label = new  String[HousingInspection.size()];
			for (int i = 0; i < HousingInspection.size(); i++) {
				// 評価基準、確認範囲の定義
				inspectionKey[i]=HousingInspection.get(i).getInspectionKey();
				// 評価基準の選択項目の定義
				inspectionTrust_result[i]=String.valueOf(HousingInspection.get(i).getInspectionTrust());
				// 確認範囲の選択項目の定義
				inspectionValue_label[i]=String.valueOf(HousingInspection.get(i).getInspectionValue());
			}

			// 評価基準、確認範囲の定義
			this.setInspectionKey(inspectionKey);
			// 評価基準の選択項目の定義
			this.setInspectionTrust_result(inspectionTrust_result);
			// 確認範囲の選択項目の定義
			this.setInspectionValue_label(inspectionValue_label);
		}

		// 住宅診断ファイルの物件拡張属性情報の取得
		// データの存在した場合、
		if (housingExtInfo != null) {

			// パラメータ：wkロード済みファイル(loadFile)　=　物件拡張属性情報.値
			String exist =  housingExtInfo.get("inspectionExist");
			String fileName = housingExtInfo.get("inspectionFileName");
			String imageFileName = housingExtInfo.get("inspectionImageFileName");
			String pathName = housingExtInfo.get("inspectionPathName");
			String imagePathName = housingExtInfo.get("inspectionImagePathName");

			// データの存在しない場合、wk住宅診断実施有無　=　"有"
			if(!StringValidateUtil.isEmpty(exist)){
				this.setHousingInspection(exist);
			}else{
				this.setHousingInspection("02");
			}

			// wkロード済み住宅診断ファイル
			this.setLoadFile(fileName);

			// wkロード済み画像ファイル
			this.setImgFile(imageFileName);

			// wkロード済み住宅診断ファイルパス
			this.setLoadFilePath(pathName);

			// wkロード済み画像ファイルパス
			this.setImgFilePath(imagePathName);
		}
		// データの存在しない場合、
		else {
			// データの存在した場合、wk住宅診断実施有無　=　"無"
			this.setHousingInspection("01");
		}
    }
}
