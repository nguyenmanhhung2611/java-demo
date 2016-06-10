package jp.co.transcosmos.dm3.corePana.model.reform.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.vo.Information;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.validation.ReformImgPathJpgValidation;
import jp.co.transcosmos.dm3.corePana.validation.ReformImgSizeValidation;
import jp.co.transcosmos.dm3.corePana.validation.UrlValidation;
import jp.co.transcosmos.dm3.corePana.vo.ReformChart;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * ���t�H�[����񃁃��e�i���X�̓��̓p�����[�^����p�t�H�[��.
 * <p>
 * <pre>
 * �S����      �C����     �C�����e
 * ------------ ----------- -----------------------------------------------------
 * TRANS     2015.03.10  �V�K�쐬
 * Thi Tran     2015.12.18    Update to add categories to reform plan
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class ReformInfoForm implements Validateable {
	/** �摜�t�@�C���L���t���O�i���j */
	public static String IMGFLG_0 = "0";

	/** �摜�t�@�C���L���t���O�i�L�j */
	public static String IMGFLG_1 = "1";

	/** ���t�H�[��(�ڍ�/�摜)�L���t���O�i���j */
	public static String DIVFLG_0 = "0";

	/** ���t�H�[��(�ڍ�/�摜)�L���t���O�i�L�j */
	public static String DIVFLG_1 = "1";

    /** ���ʃR�[�h�ϊ����� */
    private CodeLookupManager codeLookupManager;

    /** �V�X�e������CD */
    private String sysHousingCd;

    /** �\���p������ */
    private String displayHousingName;

    /** �V�X�e�����t�H�[��CD */
    private String sysReformCd;

    /** ����J�t���O */
    private String hiddenFlg;

    /** ���t�H�[���v������ */
    private String planName;

    /** ���t�H�[�����i */
    private String planPrice;

    /** �H�� */
    private String constructionPeriod;

    /** �Z�[���X�|�C���g */
    private String salesPoint;

    /** �Z�[���X�|�C���g�i�\���p�j */
    private String salesPoint1;

    /** ���l */
    private String note;

    /** ���l�i�\���p�j */
    private String note1;

    /** �����ԍ� */
    private String housingCd;

    /** ������� */
    private String housingKindCd;

    /** �`���[�g�ݒ�L�[ */
    private String chartKey[];

    /** �`���[�g�ݒ�l */
    private String chartValue[];

    /** before ����URL */
    private String beforeMovieUrl;

    /** after ����URL */
    private String afterMovieUrl;

    /** ����{������ */
    private String roleId;

    /** �摜�� */
    private String imgName;

    /** �摜TempPath */
    private String temPath;

    /** �摜�t�@�C���P */
    private String imgFile1;

    /** �摜�t�@�C���Q */
    private String imgFile2;

    /** reform�摜�p�X�� */
    private FileItem reformImgFile;

    /** wk�摜�t�@�C���L���t���O */
    private String wkImgFlg;

    /** �摜�t�@�C���I���t���O */
    private String imgSelFlg;

    /** ���t�H�[���ڍ׏��t���O */
    private String dtlFlg;

    /** ���t�H�[���摜���t���O */
    private String imgFlg;

    /** �폜�̃t���O */
    private String reformImgDel;

    /** �ŏI�X�V�� */
    private String updDate;

    /** �C�x���g�t���O */
    private String command;

    /** Category 1 of reform plan */
    private String planCategory1;
    /** Category 2 of reform plan */
    private String planCategory2;

    /** All reform categories which is configurated in code lookup **/
    private LinkedHashMap<String, ReformPlanCategory> reformPlanCategoryMap;

    // log
    private static final Log log = LogFactory.getLog(ReformInfoForm.class);

    /**
     * �f�t���g�R���X�g���N�^�[�B<br/>
     * <br/>
     *
     * @param codeLookupManager ���ʃR�[�h�ϊ�����
     */
    public ReformInfoForm() {
        super();
    }

    /**
     * �R���X�g���N�^�[�B<br/>
     * <br/>
     *
     * @param codeLookupManager ���ʃR�[�h�ϊ�����
     */
    public ReformInfoForm(CodeLookupManager codeLookupManager) {
        super();
        this.codeLookupManager = codeLookupManager;
    }

    /**
     * �ŏI�X�V�� �p�����[�^���擾����B<br/>
     * �ŏI�X�V�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �ŏI�X�V�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �ŏI�X�V�� �p�����[�^
     */
    public String getUpdDate() {
        return updDate;
    }

    /**
     * �ŏI�X�V�� �p�����[�^��ݒ肷��B<br/>
     * �ŏI�X�V�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �ŏI�X�V�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param updDate
     *            �ŏI�X�V�� �p�����[�^
     */
    public void setUpdDate(String updDate) {
        this.updDate = updDate;
    }

    /**
     * before ����URL �p�����[�^���擾����B<br/>
     * before ����URL �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� before ����URL �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return before ����URL �p�����[�^
     */
    public String getBeforeMovieUrl() {
        return beforeMovieUrl;
    }

    /**
     * before ����URL��ݒ肷��B<br/>
     * before ����URL�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� before ����URL�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param beforeMovieUrl
     *            before ����URL
     */
    public void setBeforeMovieUrl(String beforeMovieUrl) {
        this.beforeMovieUrl = beforeMovieUrl;
    }

    /**
     * after ����URL �p�����[�^���擾����B<br/>
     * after ����URL �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� after ����URL �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return after ����URL �p�����[�^
     */
    public String getAfterMovieUrl() {
        return afterMovieUrl;
    }

    /**
     * after ����URL��ݒ肷��B<br/>
     * after ����URL�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� after ����URL�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param afterMovieUrl
     *            after ����URL
     */
    public void setAfterMovieUrl(String afterMovieUrl) {
        this.afterMovieUrl = afterMovieUrl;
    }

    /**
     * ���[��ID �p�����[�^���擾����B<br/>
     * ���[��ID �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���[��ID �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return ���[��ID �p�����[�^
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * ���[��ID��ݒ肷��B<br/>
     * ���[��ID�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���[��ID�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param roleId
     *            ���[��ID
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
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
     * �V�X�e������CD��ݒ肷��B<br/>
     * �V�X�e������CD�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �V�X�e������CD�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param sysHousingCd
     *            �V�X�e������CD
     */
    public void setSysHousingCd(String sysHousingCd) {
        this.sysHousingCd = sysHousingCd;
    }

    /**
     * �\���p������ �p�����[�^���擾����B<br/>
     * �\���p������ �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �\���p������ �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �\���p������ �p�����[�^
     */
    public String getDisplayHousingName() {
        return displayHousingName;
    }

    /**
     * �\���p��������ݒ肷��B<br/>
     * �\���p�������̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �\���p�������̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param displayHousingName
     *            �\���p������
     */
    public void setDisplayHousingName(String displayHousingName) {
        this.displayHousingName = displayHousingName;
    }

    /**
     * ����J�t���O �p�����[�^���擾����B<br/>
     * ����J�t���O �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ����J�t���O �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return ����J�t���O �p�����[�^
     */
    public String getHiddenFlg() {
        return hiddenFlg;
    }

    /**
     * ����J�t���O��ݒ肷��B<br/>
     * ����J�t���O�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ����J�t���O�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param hiddenFlg
     *            ����J�t���O
     */
    public void setHiddenFlg(String hiddenFlg) {
        this.hiddenFlg = hiddenFlg;
    }

    /**
     * ���t�H�[���v������ �p�����[�^���擾����B<br/>
     * ���t�H�[���v������ �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���t�H�[���v������ �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return ���t�H�[���v������ �p�����[�^
     */
    public String getPlanName() {
        return planName;
    }

    /**
     * ���t�H�[���v��������ݒ肷��B<br/>
     * ���t�H�[���v�������̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���t�H�[���v�������̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param planName
     *            ���t�H�[���v������
     */
    public void setPlanName(String planName) {
        this.planName = planName;
    }

    /**
     * ���t�H�[�����i �p�����[�^���擾����B<br/>
     * ���t�H�[�����i �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���t�H�[�����i �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return ���t�H�[�����i �p�����[�^
     */
    public String getPlanPrice() {
        return planPrice;
    }

    /**
     * ���t�H�[�����i��ݒ肷��B<br/>
     * ���t�H�[�����i�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���t�H�[�����i�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param planPrice
     *            ���t�H�[�����i
     */
    public void setPlanPrice(String planPrice) {
        this.planPrice = planPrice;
    }

    /**
     * �H�� �p�����[�^���擾����B<br/>
     * �H�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �H�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �H�� �p�����[�^
     */
    public String getConstructionPeriod() {
        return constructionPeriod;
    }

    /**
     * �H����ݒ肷��B<br/>
     * �H���̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �H���̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param constructionPeriod
     *            �H��
     */
    public void setConstructionPeriod(String constructionPeriod) {
        this.constructionPeriod = constructionPeriod;
    }

    /**
     * �Z�[���X�|�C���g �p�����[�^���擾����B<br/>
     * �Z�[���X�|�C���g�p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �Z�[���X�|�C���g �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �Z�[���X�|�C���g �p�����[�^
     */
    public String getSalesPoint() {
        return salesPoint;
    }

    /**
     * �Z�[���X�|�C���g��ݒ肷��B<br/>
     * �Z�[���X�|�C���g�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �Z�[���X�|�C���g�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param salesPoint
     *            �Z�[���X�|�C���g
     */
    public void setSalesPoint(String salesPoint) {
        this.salesPoint = salesPoint;
    }

    /**
     * �Z�[���X�|�C���g�i�\���p�j �p�����[�^���擾����B<br/>
     * �Z�[���X�|�C���g�i�\���p�j�p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �Z�[���X�|�C���g �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �Z�[���X�|�C���g �p�����[�^
     */
    public String getSalesPoint1() {
        return salesPoint1;
    }

    /**
     * �Z�[���X�|�C���g�i�\���p�j��ݒ肷��B<br/>
     * �Z�[���X�|�C���g�i�\���p�j�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �Z�[���X�|�C���g�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param salesPoint
     *            �Z�[���X�|�C���g
     */
    public void setSalesPoint1(String salesPoint1) {
        this.salesPoint1 = salesPoint1;
    }
    /**
     * ���l �p�����[�^���擾����B<br/>
     * ���l�p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���l �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return ���l �p�����[�^
     */
    public String getNote() {
        return note;
    }

    /**
     * ���l��ݒ肷��B<br/>
     * ���l�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���l�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param note
     *            ���l
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * ���l�i�\���p�j �p�����[�^���擾����B<br/>
     * ���l�i�\���p�j�p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���l �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return ���l �p�����[�^
     */
    public String getNote1() {
        return note1;
    }

    /**
     * ���l�i�\���p�j��ݒ肷��B<br/>
     * ���l�i�\���p�j�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���l�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param note
     *            ���l
     */
    public void setNote1(String note1) {
        this.note1 = note1;
    }
    /**
     * �V�X�e�����t�H�[��CD �p�����[�^���擾����B<br/>
     * �V�X�e�����t�H�[��CD�p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �V�X�e�����t�H�[��CD �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �V�X�e�����t�H�[��CD �p�����[�^
     */
    public String getSysReformCd() {
        return sysReformCd;
    }

    /**
     * �V�X�e�����t�H�[��CD��ݒ肷��B<br/>
     * �V�X�e�����t�H�[��CD�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �V�X�e�����t�H�[��CD�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param sysReformCd
     *            �V�X�e�����t�H�[��CD
     */
    public void setSysReformCd(String sysReformCd) {
        this.sysReformCd = sysReformCd;
    }

    /**
     * dtlFlg �p�����[�^���擾����B<br/>
     * dtlFlg�p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� dtlFlg �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return dtlFlg �p�����[�^
     */
    public String getDtlFlg() {
        return dtlFlg;
    }

    /**
     * dtlFlg��ݒ肷��B<br/>
     * dtlFlg�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� dtlFlg�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param dtlFlg
     *            dtlFlg
     */
    public void setDtlFlg(String dtlFlg) {
        this.dtlFlg = dtlFlg;
    }

    /**
     * imgFlg �p�����[�^���擾����B<br/>
     * imgFlg�p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� imgFlg �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return imgFlg �p�����[�^
     */
    public String getImgFlg() {
        return imgFlg;
    }

    /**
     * imgFlg��ݒ肷��B<br/>
     * imgFlg�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� imgFlg�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param imgFlg
     *            imgFlg
     */
    public void setImgFlg(String imgFlg) {
        this.imgFlg = imgFlg;
    }

    /**
     * �C�x���g�敪 �p�����[�^���擾����B<br/>
     * �C�x���g�敪�p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �C�x���g�敪 �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �C�x���g�敪 �p�����[�^
     */
    public String getCommand() {
        return command;
    }

    /**
     * �C�x���g�敪��ݒ肷��B<br/>
     * �C�x���g�敪�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �C�x���g�敪�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param command
     *            �C�x���g�敪
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * �����ԍ� �p�����[�^���擾����B<br/>
     * �����ԍ��p�����[�^�̒l�́A�t���[�����[�N�� URL
     * �}�b�s���O�Ŏg�p���� �����ԍ� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return housingCd �����ԍ�
     */
    public String getHousingCd() {
        return housingCd;
    }

    /**
     * �����ԍ���ݒ肷��B<br/>
     * �����ԍ��̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �����ԍ��̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param housingCd
     *            �����ԍ�
     */
    public void setHousingCd(String housingCd) {
        this.housingCd = housingCd;
    }

    /**
     * �������CD �p�����[�^���擾����B<br/>
     * �������CD�p�����[�^�̒l�́A�t���[�����[�N�� URL
     * �}�b�s���O�Ŏg�p���� �������CD �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return housingKindCd �������CD
     */
    public String getHousingKindCd() {
        return housingKindCd;
    }

    /**
     * �������CD��ݒ肷��B<br/>
     * �������CD�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �������CD�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param housingKindCd
     *            �������CD
     */
    public void setHousingKindCd(String housingKindCd) {
        this.housingKindCd = housingKindCd;
    }

    /**
     * chartKey �p�����[�^���擾����B<br/>
     * chartKey�p�����[�^�̒l�́A�t���[�����[�N�� URL
     * �}�b�s���O�Ŏg�p���� chartKey �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return chartKey �p�����[�^
     */
    public String[] getChartKey() {
        return chartKey;
    }

    /**
     * chartKey��ݒ肷��B<br/>
     * chartKey�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� chartKey�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param chartKey
     *            chartKey
     */
    public void setChartKey(String[] chartKey) {
        this.chartKey = chartKey;
    }

    /**
     * chartValue �p�����[�^���擾����B<br/>
     * chartValue�p�����[�^�̒l�́A�t���[�����[�N�� URL
     * �}�b�s���O�Ŏg�p���� chartValue �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return chartValue �p�����[�^
     */
    public String[] getChartValue() {
        return chartValue;
    }

    /**
     * chartValue��ݒ肷��B<br/>
     * chartValue�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� chartValue�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param chartValue
     *            chartValue
     */
    public void setChartValue(String[] chartValue) {
        this.chartValue = chartValue;
    }

    /**
     * wk�摜�t�@�C���L���t���O ���擾����B<br/>
     * <br/>
     * @return wk�摜�t�@�C���L���t���O
     */
    public String getWkImgFlg() {
        return wkImgFlg;
    }

    /**
     * wk�摜�t�@�C���L���t���O ��ݒ肷��B<br/>
     * <br/>
     * @param wkImgFlg
     */
    public void setWkImgFlg(String wkImgFlg) {
        this.wkImgFlg = wkImgFlg;
    }

    /**
     * �摜�t�@�C���P ���擾����B<br/>
     * <br/>
     * @return �摜�t�@�C���P
     */
    public String getImgFile1() {
        return imgFile1;
    }

    /**
     * �摜�t�@�C���P ��ݒ肷��B<br/>
     * <br/>
     * @param imgFile1
     */
    public void setImgFile1(String imgFile1) {
        this.imgFile1 = imgFile1;
    }

    /**
     * �摜�t�@�C���Q ���擾����B<br/>
     * <br/>
     * @return �摜�t�@�C���Q
     */
	public String getImgFile2() {
		return imgFile2;
	}

    /**
     * �摜�t�@�C���Q ��ݒ肷��B<br/>
     * <br/>
     * @param imgFile2
     */
	public void setImgFile2(String imgFile2) {
		this.imgFile2 = imgFile2;
	}
	   /**
     * �摜�� ���擾����B<br/>
     * <br/>
     * @return �摜��
     */
    public String getImgName() {
        return imgName;
    }

    /**
     * �摜�� ��ݒ肷��B<br/>
     * <br/>
     * @param imgName
     */
    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    /**
     * �摜TempPath ���擾����B<br/>
     * <br/>
     * @return �摜TempPath
     */
    public String getTemPath() {
        return temPath;
    }

    /**
     * �摜TempPath ��ݒ肷��B<br/>
     * <br/>
     * @param temPath
     */
    public void setTemPath(String temPath) {
        this.temPath = temPath;
    }

    /**
     * reform�摜�p�X�� ���擾����B<br/>
     * <br/>
     * @return reform�摜�p�X��
     */
    public FileItem getReformImgFile() {
        return reformImgFile;
    }

    /**
     * reform�摜�p�X�� ��ݒ肷��B<br/>
     * <br/>
     * @param reformImgFile
     */
    public void setReformImgFile(FileItem reformImgFile) {
        this.reformImgFile = reformImgFile;
    }

    /**
     * �폜�̃t���O ���擾����B<br/>
     * <br/>
     * @return �폜�̃t���O
     */
    public String getReformImgDel() {
        return reformImgDel;
    }

    /**
     * �폜�̃t���O ��ݒ肷��B<br/>
     * <br/>
     * @param reformImgDel
     */
    public void setReformImgDel(String reformImgDel) {
        this.reformImgDel = reformImgDel;
    }

    /**
     * �摜�t�@�C���I���t���O �p�����[�^���擾����B<br/>
     * �摜�t�@�C���I���t���O �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �摜�t�@�C���I���t���O �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �摜�t�@�C���I���t���O �p�����[�^
     */
    public String getImgSelFlg() {
		return imgSelFlg;
	}

    /**
     * �摜�t�@�C���I���t���O �p�����[�^��ݒ肷��B<br/>
     * �摜�t�@�C���I���t���O �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �摜�t�@�C���I���t���O �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param imgSelFlg
     *            �摜�t�@�C���I���t���O �p�����[�^
     */
	public void setImgSelFlg(String imgSelFlg) {
		this.imgSelFlg = imgSelFlg;
	}

    /**
     * @return the planCategory1
     */
    public String getPlanCategory1() {
        return planCategory1;
    }

    /**
     * @param planCategory1
     *            the planCategory1 to set
     */
    public void setPlanCategory1(String planCategory1) {
        this.planCategory1 = planCategory1;
    }

    /**
     * @return the planCategory2
     */
    public String getPlanCategory2() {
        return planCategory2;
    }

    /**
     * @param planCategory2
     *            the planCategory2 to set
     */
    public void setPlanCategory2(String planCategory2) {
        this.planCategory2 = planCategory2;
    }

    // �t�H�[������vo�ɒl��ݒ�
    public void transferToValueObject(Information entry) {
    }

    /**
     * �o���f�[�V��������<br/>
     * ���N�G�X�g�p�����[�^�̃o���f�[�V�������s��<br/>
     * <br/>
     * ���� Form �N���X�̃o���f�[�V�������\�b�h�́AValidateable �C���^�[�t�F�[�X�̎����ł͖����̂ŁA
     * �o���f�[�V�������s���̈������قȂ�̂ŁA���� Form ���܂Ƃ߂ăo���f�[�V��������ꍇ�Ȃǂ͒���
     * ���鎖�B<br/>
     * <br/>
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     * @param mode �������[�h ("insert" or "update")
     * @return ���펞 true�A�G���[�� false
     */
    public boolean validate(List<ValidationFailure> errors) {

        int startSize = errors.size();

        // ���J�敪���̓`�F�b�N
        ValidationChain valHiddenFlg = new ValidationChain("reform.input.hiddenFlg", this.hiddenFlg);
        // �K�{�`�F�b�N
        valHiddenFlg.addValidation(new NullOrEmptyCheckValidation());
        // codeLookUp�`�F�b�N
        valHiddenFlg.addValidation(new CodeLookupValidation(this.codeLookupManager, "hiddenFlg"));
        valHiddenFlg.validate(errors);

        // ���t�H�[���v���������̓`�F�b�N
        ValidationChain valPlanName = new ValidationChain("reform.input.planName", this.planName);
        // �K�{�`�F�b�N
        valPlanName.addValidation(new NullOrEmptyCheckValidation());
        // �����`�F�b�N
        valPlanName.addValidation(new MaxLengthValidation(50));
        valPlanName.validate(errors);

        // ���i���̓`�F�b�N
        ValidationChain valPlanPrice = new ValidationChain("reform.input.planPrice", this.planPrice);
        // �K�{�`�F�b�N
        valPlanPrice.addValidation(new NullOrEmptyCheckValidation());
        // �����`�F�b�N
        valPlanPrice.addValidation(new MaxLengthValidation(11));
        // ���p�����`�F�b�N
        valPlanPrice.addValidation(new NumericValidation());
        valPlanPrice.validate(errors);

        // �H�����̓`�F�b�N
        ValidationChain valConstructionPeriod =
                new ValidationChain("reform.input.constructionPeriod", this.constructionPeriod);
        // �����`�F�b�N
        valConstructionPeriod.addValidation(new MaxLengthValidation(20));
        valConstructionPeriod.validate(errors);

        // �Z�[���X�|�C���g���̓`�F�b�N
        ValidationChain valSalesPoint =
                new ValidationChain("reform.input.salesPoint", this.salesPoint);
        // �����`�F�b�N
        valSalesPoint.addValidation(new MaxLengthValidation(200));
        valSalesPoint.validate(errors);

        // ���l���̓`�F�b�N
        ValidationChain valNote = new ValidationChain("reform.input.note", this.note);
        // �����`�F�b�N
        valNote.addValidation(new MaxLengthValidation(100));
        valNote.validate(errors);

        if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.housingKindCd) ||
        		PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.housingKindCd)) {

            // �]����L�[���̓`�F�b�N
            for (int i = 0; i < getHousingKindSize("key"); i++) {
                // codeLookUp�`�F�b�N
                ValidationChain valChartKey = new ValidationChain("reform.input.chartKey", this.chartKey[i]);
                if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.housingKindCd)) {
                    valChartKey.addValidation(new CodeLookupValidation(this.codeLookupManager, "inspectionTrustMansion"));
                } else if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.housingKindCd)) {
                    valChartKey.addValidation(new CodeLookupValidation(this.codeLookupManager, "inspectionTrustHouse"));
                }
                valChartKey.validate(errors);
            }

            // �]���Value���̓`�F�b�N
            for (int j = 0; j < getHousingKindSize("value"); j++) {
                // codeLookUp�`�F�b�N
                ValidationChain valChartValue = new ValidationChain("reform.input.chartValue", this.chartValue[j]);
                valChartValue.addValidation(new CodeLookupValidation(this.codeLookupManager, "inspectionResult"));
                valChartValue.validate(errors);
        	}
        }

        // �摜
        if(this.getReformImgFile()!=null){
   		 	// Validate path_name �摜
           ValidationChain valPathName = new ValidationChain(
                   "reform.input.pathName", getReformImgFile().getSize());
           // JPG�`�F�b�N
           valPathName.addValidation(new ReformImgPathJpgValidation(getReformImgFile().getName(),"jpg"));
           // �t�@�C���T�C�Y�`�F�b�N
           valPathName.addValidation(new ReformImgSizeValidation(PanaCommonConstant.updFileSize));
           valPathName.validate(errors);
        }

        // ����_Before�`�F�b�N
        ValidationChain valBeforeMovieUrl = new ValidationChain("reform.input.beforeMovieUrl", this.beforeMovieUrl);
        // �����`�F�b�N
        valBeforeMovieUrl.addValidation(new MaxLengthValidation(255));
        valBeforeMovieUrl.validate(errors);

        // ����__After�`�F�b�N
        ValidationChain valAfterMovieUrl = new ValidationChain("reform.input.afterMovieUrl", this.afterMovieUrl);
        // �����`�F�b�N
        valAfterMovieUrl.addValidation(new MaxLengthValidation(255));
        valAfterMovieUrl.validate(errors);

        // �{������
        ValidationChain valRoleId = new ValidationChain("reform.input.roleId", this.roleId);
        // codeLookUp�`�F�b�N
        valRoleId.addValidation(new CodeLookupValidation(this.codeLookupManager, "ImageInfoRoleId"));
        valRoleId.validate(errors);

        // ����_BeforeURL�`�F�b�N
        if (!StringValidateUtil.isEmpty(this.beforeMovieUrl)) {
            ValidationChain beforeMovieUrl = new ValidationChain("reform.input.beforeMovieUrl", this.beforeMovieUrl);
            beforeMovieUrl.addValidation(new UrlValidation());
            beforeMovieUrl.validate(errors);
        }

        // ����_AfterURL�`�F�b�N
        if (!StringValidateUtil.isEmpty(this.afterMovieUrl)) {
            ValidationChain afterMovieUrl = new ValidationChain("reform.input.afterMovieUrl", this.afterMovieUrl);
            afterMovieUrl.addValidation(new UrlValidation());
            afterMovieUrl.validate(errors);
        }

        // �֘A�`�F�b�N
        // ����_Before�{������
        if (!StringValidateUtil.isEmpty(this.beforeMovieUrl) &&
                StringValidateUtil.isEmpty(this.roleId)) {
            ValidationFailure vf = new ValidationFailure(
                    "mustInput1", "����_Before", "�{������", null);
            errors.add(vf);
        }

        // ����_After�{������
        if (!StringValidateUtil.isEmpty(this.afterMovieUrl) &&
                StringValidateUtil.isEmpty(this.roleId)) {
            ValidationFailure vf = new ValidationFailure(
                    "mustInput1", "����_After", "�{������", null);
            errors.add(vf);
        }

        // ����_Before����_After�{������
        String param[] = new String[] { "����_After" };
        if (!StringValidateUtil.isEmpty(this.roleId) &&
                StringValidateUtil.isEmpty(this.beforeMovieUrl) &&
                StringValidateUtil.isEmpty(this.afterMovieUrl)) {
            ValidationFailure vf = new ValidationFailure(
                    "mustInput2", "�{������", "����_Before", param);
            errors.add(vf);
        }

        // �摜�t�@�C���t�@�C�����[�h�`�F�b�N
        FileItem fi = this.getReformImgFile();
        if ("on".equals(this.reformImgDel) &&
        		(fi != null && !StringUtils.isEmpty(fi.getName()))) {

            ValidationFailure vf = new ValidationFailure(
                    "reformImgFileNotNull", "���[�_�[�`���[�g�摜", null, null);
            errors.add(vf);
        }

        // validate for categories
        if (!StringValidateUtil.isEmpty(this.planCategory1)) {

            LinkedHashMap<String, ReformPlanCategory> categoryMap = getReformPlanCategoryMap();
            ReformPlanCategory category1 = categoryMap.get(this.planCategory1);
            if (null == category1 || !category1.isSuperCategory()) {
                ValidationFailure vf = new ValidationFailure("category1Error",
                        "��1�J�e�S��", null, null);
                errors.add(vf);
            }

            if (!StringValidateUtil.isEmpty(this.planCategory2)) {
                ReformPlanCategory category2 = categoryMap
                        .get(this.planCategory2);
                if (null == category2 || category2.isSuperCategory()) {
                    ValidationFailure vf = new ValidationFailure(
                            "category2Error", "��2�J�e�S��", null, null);
                    errors.add(vf);
                }
            }
        }else if(!StringValidateUtil.isEmpty(this.planCategory2)){
            ValidationFailure vf = new ValidationFailure(
                    "category2Error", "��2�J�e�S��", null, null);
            errors.add(vf);
            
        }

        return (startSize == errors.size());
    }

    /**
     * �����œn���ꂽ���t�H�[���v�������̃o���[�I�u�W�F�N�g�Ƀt�H�[���̒l��ݒ肷��B<br/>
     * �X�V�����ł��g�p���鎖���l�����A�^�C���X�^���v���Ɋւ��ẮA�X�V���A�X�V�҂̂ݐݒ肷��B<br/>
     * <br/>
     * @param reformPlan �l��ݒ肷�郊�t�H�[���v�������̃o���[�I�u�W�F�N�g
     *
     */
    public void copyToReformPlan(ReformPlan reformPlan, String editUserId) {

        if ("update".equals(this.command)) {
            // �V�X�e�����t�H�[��CD ��ݒ�
            reformPlan.setSysReformCd(this.sysReformCd);
        }

        // �V�X�e������CD ��ݒ�
        reformPlan.setSysHousingCd(this.sysHousingCd);

        // ���t�H�[���v��������ݒ�
        reformPlan.setPlanName(this.planName);

        // ���t�H�[�����i��ݒ�
        if(!StringValidateUtil.isEmpty(this.planPrice)){
            reformPlan.setPlanPrice(Long.valueOf(this.planPrice));
        }

        // �Z�[���X�|�C���g��ݒ�
        reformPlan.setSalesPoint(this.salesPoint);

        // �H����ݒ�
        reformPlan.setConstructionPeriod(this.constructionPeriod);

        // ���l��ݒ�
        reformPlan.setNote(this.note);

        // before ����URL��ݒ�
        reformPlan.setBeforeMovieUrl(this.beforeMovieUrl);

        // after ����URL��ݒ�
        reformPlan.setAfterMovieUrl(this.afterMovieUrl);

        // ����{��������ݒ�
        reformPlan.setRoleId(this.roleId);

        // ����J�t���O��ݒ�
        reformPlan.setHiddenFlg(this.hiddenFlg);

        // ���[�_�[�`���[�g�摜�t�@�C����
        reformPlan.setReformChartImageFileName(this.imgName);

        // �o�^����ݒ�
        reformPlan.setInsDate(new Date());

        // �o�^�҂�ݒ�
        reformPlan.setInsUserId(editUserId);

        // �X�V���t��ݒ�
        reformPlan.setUpdDate(new Date());

        // �X�V�S���҂�ݒ�
        reformPlan.setUpdUserId(editUserId);

        // reformPlan.setType(this.reformType);
        reformPlan.setPlanCategory1(planCategory1);
        reformPlan.setPlanCategory2(planCategory2);
    }

    /**
     * �����œn���ꂽ���t�H�[���E���[�_�[�`���[�g�̃o���[�I�u�W�F�N�g�Ƀt�H�[���̒l��ݒ肷��B<br/>
     * �X�V�����ł��g�p���鎖���l�����A�^�C���X�^���v���Ɋւ��ẮA�X�V���A�X�V�҂̂ݐݒ肷��B<br/>
     * <br/>
     * @param reformChart �l��ݒ肷�郊�t�H�[���E���[�_�[�`���[�g���̃o���[�I�u�W�F�N�g
     * @param index
     *
     */
    public void copyToReformChart(ReformChart reformChart, String index) {

        int i = Integer.parseInt(index);

        // �V�X�e�����t�H�[��CD
        reformChart.setSysReformCd(this.sysReformCd);

        if (!StringValidateUtil.isEmpty(this.chartValue[i])) {
            // �`���[�g�L�[�l
            reformChart.setChartKey(this.chartKey[i]);
            // �`���[�g�ݒ�l
            reformChart.setChartValue(Integer.valueOf(this.chartValue[i]));
        }

    }

    /**
     * ���t�H�[�����i�V�K�j���͉�ʊi�[<br/>
     * <br/>
     * @param form ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
     * @param displayHousingName ��������
     *
     * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
     */
    public void insertFormat(String displayHousingName) {

        // ��������
        this.setDisplayHousingName(displayHousingName);
        // ���J�敪
        this.setHiddenFlg(PanaCommonConstant.HIDDEN_FLG_PUBLIC);
        // ���t�H�[���v������
        this.setPlanName("");
        // ���i
        this.setPlanPrice("");
        // �H��
        this.setConstructionPeriod("");
        // �Z�[���X�|�C���g
        this.setSalesPoint("");
        // ���l
        this.setNote("");
        // before ����URL
        this.setBeforeMovieUrl("");
        // after ����URL
        this.setAfterMovieUrl("");
        // �{������
        this.setRoleId("");
        // ���t�H�[���ڍ׏��Div
        this.setDtlFlg(DIVFLG_0);
        // ���t�H�[���摜���Div
        this.setImgFlg(DIVFLG_0);
        // �C�x���g�t���O
        this.setCommand("insert");
        // wk�摜�t�@�C���L���t���O
        this.setWkImgFlg(IMGFLG_0);
        // �摜�t�@�C���I���t���O
        this.setImgSelFlg(IMGFLG_0);

        // this.setReformType("");
        this.setPlanCategory1("");
        this.setPlanCategory1("");
        // ���t�H�[���E���[�_�[�`���[�g���X�g
        getInitChart();
    }

    /**
     * ���t�H�[�����i�X�V�j���͉�ʊi�[<br/>
     * <br/>
     * @param result ���t�H�[���v�������
     * @param displayHousingName ��������
     * @param housing �������
	 * @param commonParameters ���ʃp�����[�^�I�u�W�F�N�g
     * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
     */
    @SuppressWarnings("unchecked")
    public void updateFormat(Map<String, Object> result, String displayHousingName, Housing housing,
    		PanaCommonParameters commonParameters, PanaFileUtil fileUtil) {

        if (!"iBack".equals(this.getCommand()) && !"uBack".equals(this.getCommand())) {

        	// ���t�H�[���v�������̎擾
            ReformPlan plan = (ReformPlan) result.get("reformPlan");

            if (plan != null) {
                // ���J�敪
                this.setHiddenFlg(plan.getHiddenFlg());
                // ���t�H�[���v������
                this.setPlanName(plan.getPlanName());
                // ���i
                this.setPlanPrice(PanaStringUtils.toString(plan.getPlanPrice()));
                // �H��
                this.setConstructionPeriod(plan.getConstructionPeriod());
                // �Z�[���X�|�C���g
                this.setSalesPoint(plan.getSalesPoint());
                // ���l
                this.setNote(plan.getNote());
                // before ����URL
                this.setBeforeMovieUrl(plan.getBeforeMovieUrl());
                // after ����URL
                this.setAfterMovieUrl(plan.getAfterMovieUrl());
                // �{������
                this.setRoleId(plan.getRoleId());
                // this.setReformType(plan.getType());
                this.setPlanCategory1(plan.getPlanCategory1());
                this.setPlanCategory2(plan.getPlanCategory2());

                // �摜�t�@�C��
   			 	String rootPath = plan.getReformChartImagePathName();
   			 	String fileName = plan.getReformChartImageFileName();

   			 	if (StringValidateUtil.isEmpty(rootPath) || StringValidateUtil.isEmpty(fileName) ) {

   			 		// wk�摜�t�@�C���L���t���O
   	            	this.setWkImgFlg(IMGFLG_0);

   			 	} else {

   			 		// �摜�t�@�C���p�X
   	   			 	String urlPath = fileUtil.getHousFileMemberUrl(
   	   			 			rootPath, fileName, commonParameters.getAdminSiteChartFolder());

   	  		    	this.setImgName(fileName);
   		         	this.setImgFile1(urlPath);
   		         	this.setImgFile2(urlPath);

   		         	// wk�摜�t�@�C���L���t���O
   	            	this.setWkImgFlg(IMGFLG_1);
   			 	}
            }

            // ��������
            this.setDisplayHousingName(displayHousingName);

            // ���t�H�[���E���[�_�[�`���[�g���X�g
            getUpdateChart((List<ReformChart>) result.get("chartList"));
        }else{
        	// ���t�H�[���v�������̎擾
            ReformPlan plan = (ReformPlan) result.get("reformPlan");

            if (plan != null) {

                // �摜�t�@�C��
    		 	String rootPath = plan.getReformChartImagePathName();
    		 	String fileName = plan.getReformChartImageFileName();

    		 	if (StringValidateUtil.isEmpty(rootPath) || StringValidateUtil.isEmpty(fileName) ) {

    		 		// wk�摜�t�@�C���L���t���O
                	this.setWkImgFlg(IMGFLG_0);

    		 	} else {

    		 		// �摜�t�@�C���p�X
       			 	String urlPath = fileUtil.getHousFileMemberUrl(
       			 			rootPath, fileName, commonParameters.getAdminSiteChartFolder());

      		    	this.setImgName(fileName);
    	         	this.setImgFile1(urlPath);
    	         	this.setImgFile2(urlPath);

    	         	// wk�摜�t�@�C���L���t���O
                	this.setWkImgFlg(IMGFLG_1);
    		 	}
            }
        }

        // ���t�H�[���ڍ׏��Div
        this.setDtlFlg(DIVFLG_1);
        // ���t�H�[���摜���Div
        this.setImgFlg(DIVFLG_1);
        // �C�x���g�t���O
        this.setCommand("update");
        // �摜URL
        this.setImgFile1(this.getImgFile2());
    }

    /**
     * �V�K�������̏ꍇ�]������X�g���쐬����B<br/>
     * <br/>
     * @param form ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
     */
    private void getInitChart() {

        String index = "";
        int size = getHousingKindSize("key");
        String[] chartKey = new String[size];
        String[] chartValue = new String[size];

        for (int i = 1; i <= size; i++) {
            index = String.valueOf(i);
            chartKey[i - 1] = (index);
            chartValue[i - 1] = "1";
        }

        this.setChartKey(chartKey);
        this.setChartValue(chartValue);
    }

    /**
     * �X�V�������̏ꍇ�]������X�g���쐬����B<br>
     * <br/>
     * @param form ReformInfoForm  ���t�H�[���v�������̓��͒l���i�[���� Form �I�u�W�F�N�g
     *
     * @param chartList List �p�����[�^���ݒ肳�ꂽ�t�H�[���I�u�W�F�N�g���i�[���� List �I�u�W�F�N�g
     */
    protected void getUpdateChart(List<ReformChart> chartList) {

        if (chartList != null && (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.housingKindCd) ||
        		PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.housingKindCd))) {

            // codeLookupName�̎擾
            String lookUpType = "";
            if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.housingKindCd)) {
                lookUpType = "inspectionTrustMansion";
            } else if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.housingKindCd)) {
                lookUpType = "inspectionTrustHouse";
            }

            // �]����L�[�l�̎擾
            Iterator<String> iterator = this.codeLookupManager.getKeysByLookup(lookUpType);
            List<String> keyList = new ArrayList<String>();
            if (iterator!=null) {
                while (iterator.hasNext()) {
                    keyList.add(iterator.next());
                }
            }

            // ��ʍ��ڂ̏�����
            String[] chartKey = new String[keyList.size()];
            String[] chartValue = new String[keyList.size()];
            ReformChart chart = new ReformChart();
            // ��ʍ��ڂ̊i�[
            for (int i = 0; i < keyList.size(); i++) {

                // codeLookup���]����L�[�l�̎擾
                chartKey[i] = keyList.get(i);

                chart = new ReformChart();
                for (int j = 0; j < chartList.size(); j++) {

                    // DB���]����L�[�l�̎擾
                    chart = (ReformChart) chartList.get(j);

                    // �]���Value�l�̐ݒ�
                    chartValue[i] = "";
                    if (chartKey[i].equals(chart.getChartKey())) {
                        chartValue[i] = PanaStringUtils.toString(chart.getChartValue());
                        break;
                    }
                }
            }

            this.setChartKey(chartKey);
            this.setChartValue(chartValue);
        }
    }

	/**
	 * ���t�H�[���ڍ׃t�@�C���ݒ�Ǝ��s���s���B<br/>
	 * <br/>
	 * @param inputForm ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
	 * @param commonParameters ���ʃp�����[�^�I�u�W�F�N�g
	 *
	 */
    public List<ReformDtl> setDtlList(
    		List<ReformDtl> dtlList, PanaCommonParameters commonParameters, PanaFileUtil fileUtil) {

    	List<ReformDtl> resultList = new ArrayList<ReformDtl>();

        if (dtlList != null) {
        	for(ReformDtl dtl:dtlList) {

				 // PDF�t�@�C���p�X�̎擾
	   	         String pdfPath = "";
		         if (PanaCommonConstant.ROLE_ID_PUBLIC.equals(dtl.getRoleId())) {
		        	 pdfPath = fileUtil.getHousFileOpenUrl(
		        			 dtl.getPathName(), dtl.getFileName(), commonParameters.getAdminSitePdfFolder());
		         } else if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(dtl.getRoleId())) {
		        	 pdfPath = fileUtil.getHousFileMemberUrl(
		        			 dtl.getPathName(), dtl.getFileName(), commonParameters.getAdminSitePdfFolder());
		         }

		         // PDF�t�@�C���p�X�̍Đݒ�
		         dtl.setPathName(pdfPath);
		         resultList.add(dtl);
            }
        }

        return resultList;
	}

	/**
	 * ���t�H�[���摜�t�@�C���ݒ�Ǝ��s���s���B<br/>
	 * <br/>
	 * @param imgList ���t�H�[���摜���I�u�W�F�N�g
	 * @param commonParameters ���ʃp�����[�^�I�u�W�F�N�g
	 */
    public List<ReformImg> setImgList (List<ReformImg> imgList,
    		PanaCommonParameters commonParameters, PanaFileUtil fileUtil) {

    	List<ReformImg> resultList = new ArrayList<ReformImg>();
    	int size = commonParameters.getThumbnailSizes().size()-1;

        if (imgList != null) {
        	for(ReformImg img :imgList) {

          		 //  before �摜�p�X�̐ݒ�
        		 String sBeforePath = "";
        		 String lBeforePath = "";
        		 String sAfterPath = "";
        		 String lAfterPath = "";

				 if (PanaCommonConstant.ROLE_ID_PUBLIC.equals(img.getRoleId())) {

					 // before �摜86px
			         sBeforePath = fileUtil.getHousFileOpenUrl(img.getBeforePathName(),
			        		 img.getBeforeFileName(), String.valueOf(commonParameters.getThumbnailSizes().get(0)));
			         // before �摜800px
			         lBeforePath = fileUtil.getHousFileOpenUrl(img.getBeforePathName(),
			        		 img.getBeforeFileName(), String.valueOf(commonParameters.getThumbnailSizes().get(size)));
			         // after �摜86px
			         sAfterPath = fileUtil.getHousFileOpenUrl(img.getAfterPathName(),
			        		 img.getAfterFileName(), String.valueOf(commonParameters.getThumbnailSizes().get(0)));
			         // after �摜800px
			         lAfterPath = fileUtil.getHousFileOpenUrl(img.getAfterPathName(),
			        		 img.getAfterFileName(), String.valueOf(commonParameters.getThumbnailSizes().get(size)));


				 } else if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(img.getRoleId())) {

					 // before �摜86px
			         sBeforePath = fileUtil.getHousFileMemberUrl(img.getBeforePathName(),
			        		 img.getBeforeFileName(), String.valueOf(commonParameters.getThumbnailSizes().get(0)));
			         // before �摜800px
			         lBeforePath = fileUtil.getHousFileMemberUrl(img.getBeforePathName(),
			        		 img.getBeforeFileName(), String.valueOf(commonParameters.getThumbnailSizes().get(size)));
			         // after �摜86px
			         sAfterPath = fileUtil.getHousFileMemberUrl(img.getAfterPathName(),
			        		 img.getAfterFileName(), String.valueOf(commonParameters.getThumbnailSizes().get(0)));
			         // after �摜800px
			         lAfterPath = fileUtil.getHousFileMemberUrl(img.getAfterPathName(),
			        		 img.getAfterFileName(), String.valueOf(commonParameters.getThumbnailSizes().get(size)));
				 }

		         img.setBeforePathName(sBeforePath);
		         img.setBeforeFileName(lBeforePath);
		         img.setAfterPathName(sAfterPath);
		         img.setAfterFileName(lAfterPath);

		         resultList.add(img);
            }
        }

        return resultList;
	}

    /**
     * �]���SIZE���擾����B<br>
     * <br/>
     */
    private int getHousingKindSize (String type) {
        // codeLookupName�̎擾
        String lookUpType = "";
        if ("key".equals(type)) {
        	if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.housingKindCd)) {
                lookUpType = "inspectionTrustMansion";
            } else if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.housingKindCd)) {
                lookUpType = "inspectionTrustHouse";
            }
        } else if ("value".equals(type)) {
        	lookUpType = "inspectionResult";
        }

        // �l�̎擾
        Iterator<String> iterator = this.codeLookupManager.getKeysByLookup(lookUpType);
        List<String> keyList = new ArrayList<String>();
        if (iterator!=null) {
            while (iterator.hasNext()) {
                keyList.add(iterator.next());
            }
        }

        return keyList.size();
    }

    /**
     * Convert reform categories from Map<String, String> in code lookup to
     * LinkedHashMap<String, ReformPlanCategory>
     * 
     * @return
     */
    protected LinkedHashMap<String, ReformPlanCategory> getReformPlanCategoryMap() {
        if (reformPlanCategoryMap == null) {
            String code = "reformPlanCategories";
            // get code lookup "reformPlanCategories"
            Iterator<String> keys = codeLookupManager.getKeysByLookup(code);
            LinkedHashMap<String, String> categories = new LinkedHashMap<>();
            while (keys.hasNext()) {
                String key = keys.next();
                categories.put(key, codeLookupManager.lookupValue(code, key));
            }

            // build category tree from this code lookup
            reformPlanCategoryMap = buildCategoryTree(categories, "_", null,
                    null);
        }
        return reformPlanCategoryMap;
    }

    /**
     * Get all parent categories and their children
     * 
     * @return a list of reform category
     */
    public List<ReformPlanCategory> getSupperCategories() {
        return getSupperCategories(getReformPlanCategoryMap());
    }

    /**
     * Get parent categories from category tree
     * 
     * @param tree
     *            LinkedHashMap of ReformPlanCategory
     * @return list of parent ReformPlanCategory
     */
    protected List<ReformPlanCategory> getSupperCategories(
            LinkedHashMap<String, ReformPlanCategory> tree) {
        List<ReformPlanCategory> categories = new ArrayList<>();
        for (ReformPlanCategory category : tree.values()) {
            // check category is parent
            if (category.isSuperCategory()) {
                categories.add(category);
            }
        }
        return categories;
    }

    /**
     * Build category tree from map of category
     * 
     * @param categoryMap
     * @param delimeter
     * @param prefix
     * @param subfix
     * @return
     */
    protected LinkedHashMap<String, ReformPlanCategory> buildCategoryTree(
            LinkedHashMap<String, String> categoryMap, String delimeter,
            String prefix, String subfix) {
        LinkedHashMap<String, ReformPlanCategory> tree = new LinkedHashMap<>();
        for (Entry<String, String> category : categoryMap.entrySet()) {
            String idPath = category.getKey();
            String idLabel = category.getValue();
            String[] ids = StringUtils.split(idPath, delimeter);
            if (ids == null || ids.length == 0) {
                ids = new String[] { idPath };
            }
            String id = ids[ids.length - 1];
            id = removePrefix(id, prefix);
            id = removeSubfix(id, subfix);
            ReformPlanCategory me;
            if (tree.containsKey(id) && (me = tree.get(id)).isNew()) {
                me.setName(idLabel);
            } else {
                me = new ReformPlanCategory(id, idLabel);
                tree.put(id, me);
            }
            if (ids.length == 1) {
                continue;
            }
            String parentId = ids[ids.length - 2];
            parentId = removePrefix(parentId, prefix);
            parentId = removeSubfix(parentId, subfix);
            ReformPlanCategory parent;
            if (tree.containsKey(parentId)) {
                parent = tree.get(parentId);
            } else {
                parent = new ReformPlanCategory(parentId);
                tree.put(parentId, parent);
            }

            me.setParent(parent);
            parent.addChild(me);
        }
        return tree;
    }

    /**
     * Remove prefix from a given string
     * 
     * @param value
     * @param prefix
     * @return
     */
    private String removePrefix(String value, String prefix) {
        if (StringUtils.hasLength(prefix) && value.startsWith(prefix)) {
            return value.substring(prefix.length());
        } else {
            return value;
        }
    }

    /**
     * Remove subfix from a given string
     * 
     * @param value
     * @param subfix
     * @return
     */
    private String removeSubfix(String value, String subfix) {
        if (StringUtils.hasLength(subfix) && value.endsWith(subfix)) {
            return value.substring(value.length() - subfix.length());
        } else {
            return value;
        }
    }

    /**
     * Get all categories as json string
     * 
     * @return null if can not parse to json string
     */
    public String getSuperCategoriesAsJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(getSupperCategories());
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
