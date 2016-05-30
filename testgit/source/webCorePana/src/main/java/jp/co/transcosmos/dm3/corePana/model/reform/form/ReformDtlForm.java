package jp.co.transcosmos.dm3.corePana.model.reform.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.validation.LineAdapter;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.validation.ReformImgPathJpgValidation;
import jp.co.transcosmos.dm3.corePana.validation.ReformImgSizeValidation;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
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
public class ReformDtlForm implements Validateable {
	/** システム物件CD */
	private String sysHousingCd;

    /** システムリフォームCD */
	private String sysReformCd;

    /** 物件名称 パラメータ */
    private String displayHousingName;

    /** 物件番号 パラメータ */
    private String housingCd;

    /** 物件種類CD */
    private String housingKindCd;

    /** アップロードファイル(追加用） */
    private FileItem[] addFilePath;
    /** ファイル物理パス(追加用） */
    private String[] addHidPath;
    /** 表示順(追加用） */
    private String[] addSortOrder;
    /** ファイル名(追加用） */
    private String[] addHidFileName;
    /** 閲覧権限(追加用） */
    private String[] addRoleId;
    /** 表示名称(追加用） */
    private String[] addImgName;
    /** 価格(追加用） */
    private String[] addReformPrice;

    /** 枝番（更新用） */
    private String[] divNo;
    /** 閲覧権限（更新用） */
    private String[] roleId;
    /** 閲覧権限（更新保持用） */
    private String[] oldRoleId;
    /** ファイルパス（更新用） */
    private String[] pathName;
    /** 表示順（更新用） */
    private String[] sortOrder;
    /** ファイル名(更新用） */
    private String[] updHidFileName;
    /** 名称（更新用） */
    private String[] imgName;
    /** 価格（更新用） */
    private String[] reformPrice;
    /** 削除フラグ（更新用） */
    private String[] delFlg;

    /** 画像 */
    private String[] hidPath;

    /** コマンド */
    private String command;

    /** 共通コード変換処理 */
    private CodeLookupManager codeLookupManager;

    ReformDtlForm(CodeLookupManager codeLookupManager) {
        super();
        this.codeLookupManager = codeLookupManager;
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
     * 画像 パラメータを取得する。<br/>
     * <br/>
     *
     * @return 画像 パラメータ
     */
    public String[] getHidPath() {
		return hidPath;
	}

    /**
     * 画像 パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM hidPath 画像 パラメータ
     */
	public void setHidPath(String[] hidPath) {
		this.hidPath = hidPath;
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
     * @PARAM sysHousingCd システム物件CD パラメータ
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
     * @PARAM sysReformCd システムリフォームCD パラメータ
     */
    public void setSysReformCd(String sysReformCd) {
        this.sysReformCd = sysReformCd;
    }

    /**
     * アップロードファイル パラメータを取得する。<br/>
     * <br/>
     *
     * @return アップロードファイル パラメータ
     */
    public FileItem[] getAddFilePath() {
        return addFilePath;
    }

    /**
     * アップロードファイル パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM addFilePath アップロードファイル パラメータ
     */
    public void setAddFilePath(FileItem[] addFilePath) {
        this.addFilePath = addFilePath;
    }

    /**
     * ファイル物理パス パラメータを取得する。<br/>
     * <br/>
     *
     * @return ファイル物理パス パラメータ
     */
    public String[] getAddHidPath() {
        return addHidPath;
    }

    /**
     * ファイル物理パス パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM addHidPath ファイル物理パス パラメータ
     */
    public void setAddHidPath(String[] addHidPath) {
        this.addHidPath = addHidPath;
    }

    /**
     * 表示順 パラメータを取得する。<br/>
     * <br/>
     *
     * @return 表示順 パラメータ
     */
    public String[] getAddSortOrder() {
        return addSortOrder;
    }

    /**
     * 表示順 パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM addSortOrder 表示順 パラメータ
     */
    public void setAddSortOrder(String[] addSortOrder) {
        this.addSortOrder = addSortOrder;
    }

    /**
     * ファイル名 パラメータを取得する。<br/>
     * <br/>
     *
     * @return ファイル名 パラメータ
     */
    public String[] getAddHidFileName() {
        return addHidFileName;
    }

    /**
     * ファイル名 パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM addHidFileName ファイル名 パラメータ
     */
    public void setAddHidFileName(String[] addHidFileName) {
        this.addHidFileName = addHidFileName;
    }

    /**
     * 閲覧権限 パラメータを取得する。<br/>
     * <br/>
     *
     * @return 閲覧権限 パラメータ
     */
    public String[] getAddRoleId() {
        return addRoleId;
    }

    /**
     * 閲覧権限 パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM addRoleId 閲覧権限 パラメータ
     */
    public void setAddRoleId(String[] addRoleId) {
        this.addRoleId = addRoleId;
    }

    /**
     * 表示名称 パラメータを取得する。<br/>
     * <br/>
     *
     * @return 表示名称 パラメータ
     */
    public String[] getAddImgName() {
        return addImgName;
    }

    /**
     * 表示名称 パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM addImgName 表示名称 パラメータ
     */
    public void setAddImgName(String[] addImgName) {
        this.addImgName = addImgName;
    }

    /**
     * 価格 パラメータを取得する。<br/>
     * <br/>
     *
     * @return 価格 パラメータ
     */
    public String[] getAddReformPrice() {
        return addReformPrice;
    }

    /**
     * 価格 パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM addReformPrice 価格 パラメータ
     */
    public void setAddReformPrice(String[] addReformPrice) {
        this.addReformPrice = addReformPrice;
    }

    /**
     * 閲覧権限 パラメータを取得する。<br/>
     * <br/>
     *
     * @return 閲覧権限 パラメータ
     */
    public String[] getOldRoleId() {
        return oldRoleId;
    }

    /**
     * 閲覧権限 パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM oldRoleId 閲覧権限 パラメータ
     */
    public void setOldRoleId(String[] oldRoleId) {
        this.oldRoleId = oldRoleId;
    }

    public CodeLookupManager getCodeLookupManager() {
        return codeLookupManager;
    }

    public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
        this.codeLookupManager = codeLookupManager;
    }

    /**
     * 枝番 パラメータを取得する。<br/>
     * <br/>
     *
     * @return システムリフォームCD パラメータ
     */
    public String[] getDivNo() {
        return divNo;
    }

    /**
     * 枝番 パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM divNo 枝番 パラメータ
     */
    public void setDivNo(String[] divNo) {
        this.divNo = divNo;
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
     * <br/>
     *
     * @return 画像 パラメータ
     */
    public String[] getPathName() {
        return pathName;
    }

    /**
     * 画像 パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM pathName 画像 パラメータ
     */
    public void setPathName(String[] pathName) {
        this.pathName = pathName;
    }

    /**
     * 表示順 パラメータを取得する。<br/>
     * <br/>
     *
     * @return 表示順 パラメータ
     */
    public String[] getSortOrder() {
        return sortOrder;
    }

    /**
     * 表示順 パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM sortOrder 表示順 パラメータ
     */
    public void setSortOrder(String[] sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String[] getUpdHidFileName() {
        return updHidFileName;
    }

    public void setUpdHidFileName(String[] updHidFileName) {
        this.updHidFileName = updHidFileName;
    }

    /**
     * 名称 パラメータを取得する。<br/>
     * <br/>
     *
     * @return 名称 パラメータ
     */
    public String[] getImgName() {
        return imgName;
    }

    /**
     * 名称 パラメータを設定する。<BR/>>
     * <BR/>
     *
     * @PARAM imgName 名称 パラメータ
     */
    public void setImgName(String[] imgName) {
        this.imgName = imgName;
    }

    /**
     * 価格 パラメータを取得する。<br/>
     * <br/>
     *
     * @return 価格 パラメータ
     */
    public String[] getReformPrice() {
        return reformPrice;
    }

    /**
     * 価格 パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM reformPrice 価格 パラメータ
     */
    public void setReformPrice(String[] reformPrice) {
        this.reformPrice = reformPrice;
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
     * コマンドパラメータを取得する。<br/>
     * <br/>
     *
     * @return コマンド パラメータ
     */
    public String getCommand() {
        return command;
    }

    /**
     * コマンド パラメータを設定する。<BR/>
     * <BR/>
     *
     * @PARAM command コマンド パラメータ
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * 検索条件オブジェクトを作成する。<br/>
     * 出力用なのでページ処理を除外した検索条件を生成する。<br/>
     * <br/>
     *
     * @return 検索条件オブジェクト
     */
    public DAOCriteria buildReformDtlCriteria() {
        DAOCriteria criteria = new DAOCriteria();

        criteria.addOrderByClause("sortOrder", true);
        criteria.addOrderByClause("divNo", false);
        if ((this.sysReformCd != null) && !this.sysReformCd.equals("")) {
            criteria.addWhereClause("sysReformCd", this.sysReformCd);
        }

        return criteria;
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
        int startSize = errors.size();

            // リフォーム詳細情報入力の画面チェックを行う
            if ("insert".equals(this.getCommand())) {
            	String[] wkPathName = null;

            	if(this.getAddFilePath()!=null){
            		wkPathName = new String[this.getAddFilePath().length];
            		for (int i = 0; i < this.getAddFilePath().length; i++) {
            			wkPathName[i]=this.getAddFilePath()[i].getName();
            		}

            		if (StringValidateUtil.isEmpty(wkPathName[0])
	                        && StringValidateUtil.isEmpty(this.getAddSortOrder()[0])
	                        && StringValidateUtil.isEmpty(this.getAddImgName()[0])
	                        && StringValidateUtil.isEmpty(this.getAddRoleId()[0])
	                        && StringValidateUtil.isEmpty(this.getAddReformPrice()[0])
	                        && StringValidateUtil.isEmpty(wkPathName[1])
	                        && StringValidateUtil.isEmpty(this.getAddSortOrder()[1])
	                        && StringValidateUtil.isEmpty(this.getAddImgName()[1])
	                        && StringValidateUtil.isEmpty(this.getAddRoleId()[1])
	                        && StringValidateUtil.isEmpty(this.getAddReformPrice()[1])
	                        && StringValidateUtil.isEmpty(wkPathName[2])
	                        && StringValidateUtil.isEmpty(this.getAddSortOrder()[2])
	                        && StringValidateUtil.isEmpty(this.getAddImgName()[2])
	                        && StringValidateUtil.isEmpty(this.getAddRoleId()[2])
	                        && StringValidateUtil.isEmpty(this.getAddReformPrice()[2])) {

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
	                        && !StringValidateUtil.isEmpty(this.getAddImgName()[i])
	                        && !StringValidateUtil.isEmpty(this.getAddReformPrice()[i])
	                        && !StringValidateUtil.isEmpty(this.getAddRoleId()[i])) {

	                	if(this.getAddFilePath()!=null){

	                		 // Validate path_name 画像
	                        ValidationChain valPathName = new ValidationChain(
	                                "field.reformDtl.PathName", getAddFilePath()[i].getSize());
	                        // PDFチェック
	                        valPathName.addValidation(new LineAdapter(new ReformImgPathJpgValidation(getAddFilePath()[0].getName(),"pdf"), i+1));
	                        // ファイルサイズチェック
	                        valPathName.addValidation(new LineAdapter(
	                                new ReformImgSizeValidation(PanaCommonConstant.updFileSize), i+1));
	                        valPathName.validate(errors);
	                	}

	                    // Validate sort_order 表示順
	                    ValidationChain valSortOrder = new ValidationChain(
	                            "field.reformDtl.SortOrder", getAddSortOrder()[i]);
	                    // 最大桁数 3桁
	                    valSortOrder.addValidation(new LineAdapter(
	                            new MaxLengthValidation(3), i+1));
	                    // 半角数字
	                    valSortOrder.addValidation(new LineAdapter(
	                            new NumericValidation(), i+1));
	                    valSortOrder.validate(errors);

	                    // 名称
	                    ValidationChain valImgComment = new ValidationChain(
	                            "field.reformDtl.ImgName", getAddImgName()[i]);
	                    // 最大桁数 20桁
	                    valImgComment.addValidation(new LineAdapter(
	                            new MaxLengthValidation(20), i+1));
	                    valImgComment.validate(errors);

	                    // 価格
	                    ValidationChain valReforPrice = new ValidationChain(
	                            "field.reformDtl.ReformPrice",getAddReformPrice()[i]);

                        // 最大桁数 7桁
	                    valReforPrice.addValidation(new LineAdapter(
                                new MaxLengthValidation(7), i + 1));
                        // 半角数字チェック
	                    valReforPrice.addValidation(new LineAdapter(
                                new NumericValidation(), i + 1));

	                    valReforPrice.validate(errors);

	                    // Validate Role_id 閲覧権限
	                    ValidationChain valRoleId = new ValidationChain(
	                            "field.reformImg.RoleId",getAddRoleId()[i]);

	                    // パターンチェック
	                    valRoleId.addValidation(new LineAdapter(
	                            new CodeLookupValidation(this.codeLookupManager,
	                                    "ImageInfoRoleId"), i+1));
	                    valRoleId.validate(errors);
	                }

	                // 項目は全て入力ではなく、全て空白ではない場合
	                if (!StringValidateUtil.isEmpty(wkPathName[i])
	                        || !StringValidateUtil.isEmpty(this.getAddSortOrder()[i])
	                        || !StringValidateUtil.isEmpty(this.getAddImgName()[i])
	                        || !StringValidateUtil.isEmpty(this.getAddReformPrice()[i])
	                        || !StringValidateUtil.isEmpty(this.getAddRoleId()[i])) {
	                    if (StringValidateUtil.isEmpty(wkPathName[i])
		                        || StringValidateUtil.isEmpty(this.getAddSortOrder()[i])
		                        || StringValidateUtil.isEmpty(this.getAddImgName()[i])
		                        || StringValidateUtil.isEmpty(this.getAddReformPrice()[i])
		                        || StringValidateUtil.isEmpty(this.getAddRoleId()[i])) {

	                        String param[] = new String[] { "アップロード画像、表示順、名称、閲覧権限、価格" };
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
                                "field.reformDtl.PathName", getPathName()[i]);
                        // 必須チェック
                        valPathName.addValidation(new LineAdapter(
                                new NullOrEmptyCheckValidation(), i + 1));
                        valPathName.validate(errors);

                        // Validate sort_order 表示順
                        ValidationChain valSortOrder = new ValidationChain(
                                "field.reformDtl.SortOrder", getSortOrder()[i]);
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

                        // Validate Img_name 名称
                        ValidationChain valImg_name = new ValidationChain(
                                "field.reformDtl.ImgName", getImgName()[i]);
                        // 必須チェック
                        valImg_name.addValidation(new LineAdapter(
                                new NullOrEmptyCheckValidation(), i + 1));
                        // 最大桁数 20桁
                        valImg_name.addValidation(new LineAdapter(
                                new MaxLengthValidation(20), i + 1));
                        valImg_name.validate(errors);

                        // Validate Reform_price 価格
                        ValidationChain valReform_price = new ValidationChain(
                                "field.reformDtl.ReformPrice",
                                getReformPrice()[i]);
                        // 必須チェック
                        valReform_price.addValidation(new LineAdapter(
                                new NullOrEmptyCheckValidation(), i + 1));
                        // 最大桁数 7桁
                        valReform_price.addValidation(new LineAdapter(
                                new MaxLengthValidation(7), i + 1));
                        // 半角数字チェック
                        valReform_price.addValidation(new LineAdapter(
                                new NumericValidation(), i + 1));
                        valReform_price.validate(errors);

                        // Validate Role_id 閲覧権限
                        ValidationChain valRole_id = new ValidationChain(
                                "field.reformImg.RoleId", getRoleId()[i]);
                        // 必須チェック
                        valRole_id.addValidation(new LineAdapter(
                                new NullOrEmptyCheckValidation(), i + 1));
                        // パターンチェック
                        valRole_id.addValidation(new LineAdapter(
                                new CodeLookupValidation(
                                        this.codeLookupManager,
                                        "ImageInfoRoleId"), i + 1));
                        valRole_id.validate(errors);

                        ValidationChain valOldRole_id = new ValidationChain(
                                "field.reformImg.RoleId", getOldRoleId()[i]);

                        // パターンチェック
                        valOldRole_id.addValidation(new LineAdapter(
                                new CodeLookupValidation(
                                        this.codeLookupManager,
                                        "ImageInfoRoleId"), i + 1));
                        valOldRole_id.validate(errors);
                    }
                }
            }
        return (startSize == errors.size());
    }

    /**
     * フォームからVOデータを書き込む処理。<br/>
     * <br/>
     *
     * @param reformDtl
     *            書き込む対象VO
     * @param i
     *            アップロード対象画像リストのインデックス
     *
     */
    public void copyToReformDtl(ReformDtl reformDtl, int idx) {

        if ("insert".equals(this.getCommand())) {
            // システムリフォームCD
            reformDtl.setSysReformCd(this.getSysReformCd());
            // ファイル名
            reformDtl.setFileName(this.getAddHidFileName()[idx]);
            // 表示順
            reformDtl.setSortOrder(PanaStringUtils.toInteger(this.getAddSortOrder()[idx]));
            // 画像名称
            reformDtl.setImgName(this.getAddImgName()[idx]);
            // リフォーム価格
            reformDtl.setReformPrice(PanaStringUtils.toLong(this.getAddReformPrice()[idx]));
            // 画像閲覧権限
            reformDtl.setRoleId(this.getAddRoleId()[idx]);
        } else {
            // 表示順
            reformDtl
                    .setSortOrder(PanaStringUtils.toInteger(this.getSortOrder()[idx]));
            // 画像名称
            reformDtl.setImgName(this.getImgName()[idx]);
            // リフォーム価格
            reformDtl.setReformPrice(PanaStringUtils.toLong(this
                    .getReformPrice()[idx]));
            // 画像閲覧権限
            reformDtl.setRoleId(this.getRoleId()[idx]);
        }
    }

    /**
     * 渡されたバリーオブジェクトから Form へ初期値を設定する。<br/>
     * <br/>
     * @param adminUser AdminUserInterface　を実装した、ユーザー情報管理用バリーオブジェクト
     * @param adminRole AdminUserRoleInterface を実装したユーザーロール管理用バリーオブジェクト
     */
    public void setDefaultData(List<ReformDtl> reformDtlList) {
        // 枝番
        String[] divNo = new String[reformDtlList.size()];
        // 表示順
        String[] sortOrder = new String[reformDtlList.size()];
        // 名称
        String[] imgName = new String[reformDtlList.size()];
        // ファイル名（非表示）
        String[] updHidFileName = new String[reformDtlList.size()];
        // パス名
        String[] pathName = new String[reformDtlList.size()];
        // 価格
        String[] reformPrice = new String[reformDtlList.size()];
        // 閲覧権限
        String[] roleId = new String[reformDtlList.size()];
        // 削除フラグ
        String[] delFlg = new String[reformDtlList.size()];

        for (int i = 0; i < reformDtlList.size(); i++) {
            ReformDtl reformDtl = reformDtlList.get(i);
            divNo[i] = PanaStringUtils.toString(reformDtl.getDivNo());
            sortOrder[i] = PanaStringUtils.toString(reformDtl.getSortOrder());
            imgName[i] = reformDtl.getImgName();
            updHidFileName[i] = reformDtl.getFileName();
            pathName[i] = reformDtl.getPathName();
            reformPrice[i] = PanaStringUtils.toString(reformDtl.getReformPrice());
            roleId[i] = reformDtl.getRoleId();
            delFlg[i] = "0";
        }
        // 枝番
        this.divNo = divNo;
        // 表示順
        this.sortOrder = sortOrder;
        // 名称
        this.imgName = imgName;
        // ファイル名（非表示）
        this.updHidFileName = updHidFileName;
        // パス名
        this.pathName = pathName;
        // 価格
        this.reformPrice = reformPrice;
        // 閲覧権限
        this.roleId = roleId;
        // 閲覧権限
        this.oldRoleId = roleId;
        // 削除フラグ
        this.delFlg = delFlg;
    }
}
