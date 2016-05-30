package jp.co.transcosmos.dm3.corePana.model.adminLog.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.corePana.validation.DateFromToValidation;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.form.PagingListForm;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.AsciiOnlyValidation;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.ValidDateValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;
import jp.co.transcosmos.dm3.validation.ZenkakuOnlyValidation;

/**
 * Application of entry form for the receipt of the AdminLog.
 * 
 * <pre>
 * 担当者        修正日       修正内容
 * ------------ ----------- -----------------------------------------------------
 * Vinh.Ly      2015.08.24  Create New
 * </pre>
 */
public class AdminLogSearchForm extends PagingListForm<JoinResult> implements Validateable {

    /** 検索画面の command パラメータ */
    private String command;

    /** ログインID （検索条件） */
    private String keyLoginId;
    /** ユーザー名 （検索条件） */
    private String keyUserName;

    /** 登録開始日 （検索条件） */
    private String keyInsDateStart;
    /** 登録終了日 （検索条件） */
    private String keyInsDateEnd;

    /** 検索画面の command パラメータ */
    private String keySearchCommand;

    /** 共通コード変換処理 */
    private CodeLookupManager codeLookupManager;
    /** レングスバリデーションで使用する文字列長を取得するユーティリティ */
    protected LengthValidationUtils lengthUtils;

    /** 共通パラメータオブジェクト */
    protected CommonParameters commonParameters;

    /** ステータスFC */
    private String keyAdminLogFC;

    /**
     * Constructor <br/>
     * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
     * <br/>
     * 
     * @param lengthUtils
     *            レングスバリデーションで使用する文字列長を取得するユーティリティ
     * @param commonParameters
     *            共通パラメータオブジェクト
     * @param codeLookupManager
     *            共通コード変換処理
     *
     */
    protected AdminLogSearchForm(LengthValidationUtils lengthUtils, CommonParameters commonParameters,
            CodeLookupManager codeLookupManager) {

        this.lengthUtils = lengthUtils;
        this.commonParameters = commonParameters;
        this.codeLookupManager = codeLookupManager;
    }

    /**
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * @param command
     *            the command to set
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * @return the keyLoginId
     */
    public String getKeyLoginId() {
        return keyLoginId;
    }

    /**
     * @param keyLoginId
     *            the keyLoginId to set
     */
    public void setKeyLoginId(String keyLoginId) {
        this.keyLoginId = keyLoginId;
    }

    /**
     * @return the keyUserName
     */
    public String getKeyUserName() {
        return keyUserName;
    }

    /**
     * @param keyUserName
     *            the keyUserName to set
     */
    public void setKeyUserName(String keyUserName) {
        this.keyUserName = keyUserName;
    }

    /**
     * @return the keyInsDateStart
     */
    public String getKeyInsDateStart() {
        return keyInsDateStart;
    }

    /**
     * @param keyInsDateStart
     *            the keyInsDateStart to set
     */
    public void setKeyInsDateStart(String keyInsDateStart) {
        this.keyInsDateStart = keyInsDateStart;
    }

    /**
     * @return the keyInsDateEnd
     */
    public String getKeyInsDateEnd() {
        return keyInsDateEnd;
    }

    /**
     * @param keyInsDateEnd
     *            the keyInsDateEnd to set
     */
    public void setKeyInsDateEnd(String keyInsDateEnd) {
        this.keyInsDateEnd = keyInsDateEnd;
    }

    /**
     * @return the keySearchCommand
     */
    public String getKeySearchCommand() {
        return keySearchCommand;
    }

    /**
     * @param keySearchCommand
     *            the keySearchCommand to set
     */
    public void setKeySearchCommand(String keySearchCommand) {
        this.keySearchCommand = keySearchCommand;
    }

    /**
     * @return the codeLookupManager
     */
    public CodeLookupManager getCodeLookupManager() {
        return codeLookupManager;
    }

    /**
     * @param codeLookupManager
     *            the codeLookupManager to set
     */
    public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
        this.codeLookupManager = codeLookupManager;
    }

    /**
     * @return the keyAdminLogFC
     */
    public String getKeyAdminLogFC() {
        return keyAdminLogFC;
    }

    /**
     * @param keyAdminLogFC
     *            the keyAdminLogFC to set
     */
    public void setKeyAdminLogFC(String keyAdminLogFC) {
        this.keyAdminLogFC = keyAdminLogFC;
    }

    /**
     * Validation process<br/>
     * To perform the validation of the request parameters <br/>
     * <br/>
     * 
     * @param errors
     *            List object that contains the error information
     * @return Normal is true , when an error is false
     */
    @Override
    public boolean validate(List<ValidationFailure> errors) {
        int startSize = errors.size();

        // ログインID入力チェック
        validKeyLoginId(errors);
        // ユーザ名入力チェック
        validKeyUserName(errors);

        // 登録開始日入力チェック
        validInsDateStart(errors);

        // 登録終了日入力チェック
        validInsDateEnd(errors);

        // 登録開始日 < 登録終了日のチェック
        validInsDateCom(errors);

        // check function cd in admin log
        validKeyAdminLogFc(errors);

        return startSize == errors.size();

    }

    /**
     * ログインID バリデーション<br/>
     * ・桁数チェック <br/>
     * 
     * @param errors
     *            エラー情報を格納するリストオブジェクト
     */
    protected void validKeyLoginId(List<ValidationFailure> errors) {
        String label = "adminLogList.input.keyLoginId";
        ValidationChain valid = new ValidationChain(label, this.getKeyLoginId());

        // 桁数チェック
        valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 16)));
        // 半角英数記号チェック
        valid.addValidation(new AsciiOnlyValidation());

        valid.validate(errors);
    }

    /**
     * ユーザ名 バリデーション<br/>
     * ・桁数チェック <br/>
     * 
     * @param errors
     *            エラー情報を格納するリストオブジェクト
     */
    protected void validKeyUserName(List<ValidationFailure> errors) {
        String label = "adminLogList.input.keyUserName";
        ValidationChain valid = new ValidationChain(label, this.getKeyUserName());

        // 桁数チェック
        valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 40)));
        // 全角文字チェック
        valid.addValidation(new ZenkakuOnlyValidation());

        valid.validate(errors);
    }

    /**
     * 登録開始日 バリデーション<br/>
     * ・日付書式チェック <br/>
     * 
     * @param errors
     *            エラー情報を格納するリストオブジェクト
     */
    protected void validInsDateStart(List<ValidationFailure> errors) {
        // 登録開始日入力チェック
        ValidationChain valid = new ValidationChain("adminLogList.input.insDateStart", this.keyInsDateStart);
        // 日付書式チェック
        valid.addValidation(new ValidDateValidation("yyyy/MM/dd"));
        valid.validate(errors);
    }

    /**
     * 登録終了日 バリデーション<br/>
     * ・日付書式チェック <br/>
     * 
     * @param errors
     *            エラー情報を格納するリストオブジェクト
     */
    protected void validInsDateEnd(List<ValidationFailure> errors) {
        // 登録終了日入力チェック
        ValidationChain valid = new ValidationChain("adminLogList.input.insDateEnd", this.keyInsDateEnd);
        // 日付書式チェック
        valid.addValidation(new ValidDateValidation("yyyy/MM/dd"));
        valid.validate(errors);
    }

    /**
     * Check function cd in admin log <br/>
     * <br/>
     * 
     * @param errors
     *            List object that contains the error information
     */
    protected void validKeyAdminLogFc(List<ValidationFailure> errors) {
        // 更新日時入力チェック
        ValidationChain valid = new ValidationChain("adminLogList.input.adminLogFC", this.keyAdminLogFC);
        // パターンチェック
        valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "functionCd"));
        valid.validate(errors);
    }

    /**
     * Registration Start Date < Registration End Date Validation <br/>
     * ・Date comparison check <br/>
     * 
     * @param errors
     *            List object that contains the error information
     */
    protected void validInsDateCom(List<ValidationFailure> errors) {
        // 日付比較チェック
        ValidationChain valid = new ValidationChain("adminLogList.input.insDateStart", this.keyInsDateStart);
        // 日付書式チェック
        valid.addValidation(new DateFromToValidation("yyyy/MM/dd", "登録終了日", this.keyInsDateEnd));
        valid.validate(errors);
    }

    /*
     * (non-Javadoc)
     * 
     * @see jp.co.transcosmos.dm3.form.PagingListForm#buildCriteria()
     */
    @Override
    public DAOCriteria buildCriteria() {
        DAOCriteria criteria = super.buildCriteria();
        // filter LoginId of user
        if (!StringValidateUtil.isEmpty(keyLoginId)) {
            criteria.addWhereClause("adminLoginInfo", "loginId", keyLoginId, DAOCriteria.EQUALS, false);
        }
        // filter Username of user
        if (!StringValidateUtil.isEmpty(keyUserName)) {
            criteria.addWhereClause("adminLoginInfo", "userName", "%" + keyUserName + "%",
                    DAOCriteria.LIKE_CASE_INSENSITIVE, false);
        }

        // filter start date
        if (!StringValidateUtil.isEmpty(keyInsDateStart)) {
            criteria.addWhereClause("insDate", this.keyInsDateStart + " 00:00:00", DAOCriteria.GREATER_THAN_EQUALS);
        }

        // filter end date
        if (!StringValidateUtil.isEmpty(keyInsDateEnd)) {
            criteria.addWhereClause("insDate", this.keyInsDateEnd + " 23:59:59", DAOCriteria.LESS_THAN_EQUALS);
        }

        // filter admin log function code
        if (!StringValidateUtil.isEmpty(keyAdminLogFC)) {
            criteria.addWhereClause("functionCd", keyAdminLogFC);
        }

        // sort the result base on adminLogId
        criteria.addOrderByClause("adminLogId", true);
        return criteria;
    }
}
