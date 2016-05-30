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
 * �S����        �C����       �C�����e
 * ------------ ----------- -----------------------------------------------------
 * Vinh.Ly      2015.08.24  Create New
 * </pre>
 */
public class AdminLogSearchForm extends PagingListForm<JoinResult> implements Validateable {

    /** ������ʂ� command �p�����[�^ */
    private String command;

    /** ���O�C��ID �i���������j */
    private String keyLoginId;
    /** ���[�U�[�� �i���������j */
    private String keyUserName;

    /** �o�^�J�n�� �i���������j */
    private String keyInsDateStart;
    /** �o�^�I���� �i���������j */
    private String keyInsDateEnd;

    /** ������ʂ� command �p�����[�^ */
    private String keySearchCommand;

    /** ���ʃR�[�h�ϊ����� */
    private CodeLookupManager codeLookupManager;
    /** �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B */
    protected LengthValidationUtils lengthUtils;

    /** ���ʃp�����[�^�I�u�W�F�N�g */
    protected CommonParameters commonParameters;

    /** �X�e�[�^�XFC */
    private String keyAdminLogFC;

    /**
     * Constructor <br/>
     * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
     * <br/>
     * 
     * @param lengthUtils
     *            �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
     * @param commonParameters
     *            ���ʃp�����[�^�I�u�W�F�N�g
     * @param codeLookupManager
     *            ���ʃR�[�h�ϊ�����
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

        // ���O�C��ID���̓`�F�b�N
        validKeyLoginId(errors);
        // ���[�U�����̓`�F�b�N
        validKeyUserName(errors);

        // �o�^�J�n�����̓`�F�b�N
        validInsDateStart(errors);

        // �o�^�I�������̓`�F�b�N
        validInsDateEnd(errors);

        // �o�^�J�n�� < �o�^�I�����̃`�F�b�N
        validInsDateCom(errors);

        // check function cd in admin log
        validKeyAdminLogFc(errors);

        return startSize == errors.size();

    }

    /**
     * ���O�C��ID �o���f�[�V����<br/>
     * �E�����`�F�b�N <br/>
     * 
     * @param errors
     *            �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
    protected void validKeyLoginId(List<ValidationFailure> errors) {
        String label = "adminLogList.input.keyLoginId";
        ValidationChain valid = new ValidationChain(label, this.getKeyLoginId());

        // �����`�F�b�N
        valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 16)));
        // ���p�p���L���`�F�b�N
        valid.addValidation(new AsciiOnlyValidation());

        valid.validate(errors);
    }

    /**
     * ���[�U�� �o���f�[�V����<br/>
     * �E�����`�F�b�N <br/>
     * 
     * @param errors
     *            �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
    protected void validKeyUserName(List<ValidationFailure> errors) {
        String label = "adminLogList.input.keyUserName";
        ValidationChain valid = new ValidationChain(label, this.getKeyUserName());

        // �����`�F�b�N
        valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 40)));
        // �S�p�����`�F�b�N
        valid.addValidation(new ZenkakuOnlyValidation());

        valid.validate(errors);
    }

    /**
     * �o�^�J�n�� �o���f�[�V����<br/>
     * �E���t�����`�F�b�N <br/>
     * 
     * @param errors
     *            �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
    protected void validInsDateStart(List<ValidationFailure> errors) {
        // �o�^�J�n�����̓`�F�b�N
        ValidationChain valid = new ValidationChain("adminLogList.input.insDateStart", this.keyInsDateStart);
        // ���t�����`�F�b�N
        valid.addValidation(new ValidDateValidation("yyyy/MM/dd"));
        valid.validate(errors);
    }

    /**
     * �o�^�I���� �o���f�[�V����<br/>
     * �E���t�����`�F�b�N <br/>
     * 
     * @param errors
     *            �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
    protected void validInsDateEnd(List<ValidationFailure> errors) {
        // �o�^�I�������̓`�F�b�N
        ValidationChain valid = new ValidationChain("adminLogList.input.insDateEnd", this.keyInsDateEnd);
        // ���t�����`�F�b�N
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
        // �X�V�������̓`�F�b�N
        ValidationChain valid = new ValidationChain("adminLogList.input.adminLogFC", this.keyAdminLogFC);
        // �p�^�[���`�F�b�N
        valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "functionCd"));
        valid.validate(errors);
    }

    /**
     * Registration Start Date < Registration End Date Validation <br/>
     * �EDate comparison check <br/>
     * 
     * @param errors
     *            List object that contains the error information
     */
    protected void validInsDateCom(List<ValidationFailure> errors) {
        // ���t��r�`�F�b�N
        ValidationChain valid = new ValidationChain("adminLogList.input.insDateStart", this.keyInsDateStart);
        // ���t�����`�F�b�N
        valid.addValidation(new DateFromToValidation("yyyy/MM/dd", "�o�^�I����", this.keyInsDateEnd));
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
