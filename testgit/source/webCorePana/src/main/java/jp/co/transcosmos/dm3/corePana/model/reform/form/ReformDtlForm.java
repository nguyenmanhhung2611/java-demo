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
 * ���t�H�[����񃁃��e�i���X�̓��̓p�����[�^����p�t�H�[��.
 * <p>
 *
 * <pre>
 * �S����      �C����     �C�����e
 * ------------ ----------- -----------------------------------------------------
 * 		       2015.03.10  �V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class ReformDtlForm implements Validateable {
	/** �V�X�e������CD */
	private String sysHousingCd;

    /** �V�X�e�����t�H�[��CD */
	private String sysReformCd;

    /** �������� �p�����[�^ */
    private String displayHousingName;

    /** �����ԍ� �p�����[�^ */
    private String housingCd;

    /** �������CD */
    private String housingKindCd;

    /** �A�b�v���[�h�t�@�C��(�ǉ��p�j */
    private FileItem[] addFilePath;
    /** �t�@�C�������p�X(�ǉ��p�j */
    private String[] addHidPath;
    /** �\����(�ǉ��p�j */
    private String[] addSortOrder;
    /** �t�@�C����(�ǉ��p�j */
    private String[] addHidFileName;
    /** �{������(�ǉ��p�j */
    private String[] addRoleId;
    /** �\������(�ǉ��p�j */
    private String[] addImgName;
    /** ���i(�ǉ��p�j */
    private String[] addReformPrice;

    /** �}�ԁi�X�V�p�j */
    private String[] divNo;
    /** �{�������i�X�V�p�j */
    private String[] roleId;
    /** �{�������i�X�V�ێ��p�j */
    private String[] oldRoleId;
    /** �t�@�C���p�X�i�X�V�p�j */
    private String[] pathName;
    /** �\�����i�X�V�p�j */
    private String[] sortOrder;
    /** �t�@�C����(�X�V�p�j */
    private String[] updHidFileName;
    /** ���́i�X�V�p�j */
    private String[] imgName;
    /** ���i�i�X�V�p�j */
    private String[] reformPrice;
    /** �폜�t���O�i�X�V�p�j */
    private String[] delFlg;

    /** �摜 */
    private String[] hidPath;

    /** �R�}���h */
    private String command;

    /** ���ʃR�[�h�ϊ����� */
    private CodeLookupManager codeLookupManager;

    ReformDtlForm(CodeLookupManager codeLookupManager) {
        super();
        this.codeLookupManager = codeLookupManager;
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
     * �������� �p�����[�^��ݒ肷��B<br/>
     * �������� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �������� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param displayHousingName
     *            �������� �p�����[�^
     */
    public void setDisplayHousingName(String displayHousingName) {
        this.displayHousingName = displayHousingName;
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
     * �����ԍ� �p�����[�^��ݒ肷��B<br/>
     * �����ԍ� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �����ԍ� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param housingCd
     *            �����ԍ� �p�����[�^
     */
    public void setHousingCd(String housingCd) {
        this.housingCd = housingCd;
    }

    /**
     * �������CD���擾����B<br/>
     * �������CD�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �������CD �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return before �������CD
     */
    public String getHousingKindCd() {
        return housingKindCd;
    }

    /**
     * �������CD��ݒ肷��B<br/>
     * �������CD�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �������CD �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param housingKindCd
     *            �������CD
     */
    public void setHousingKindCd(String housingKindCd) {
        this.housingKindCd = housingKindCd;
    }

    /**
     * �摜 �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �摜 �p�����[�^
     */
    public String[] getHidPath() {
		return hidPath;
	}

    /**
     * �摜 �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM hidPath �摜 �p�����[�^
     */
	public void setHidPath(String[] hidPath) {
		this.hidPath = hidPath;
	}

	/**
     * �V�X�e������CD �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �V�X�e������CD �p�����[�^
     */
    public String getSysHousingCd() {
        return sysHousingCd;
    }

	/**
     * �V�X�e������CD �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM sysHousingCd �V�X�e������CD �p�����[�^
     */
    public void setSysHousingCd(String sysHousingCd) {
        this.sysHousingCd = sysHousingCd;
    }

    /**
     * �V�X�e�����t�H�[��CD �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �V�X�e�����t�H�[��CD �p�����[�^
     */
    public String getSysReformCd() {
        return sysReformCd;
    }

    /**
     * �V�X�e�����t�H�[��CD �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM sysReformCd �V�X�e�����t�H�[��CD �p�����[�^
     */
    public void setSysReformCd(String sysReformCd) {
        this.sysReformCd = sysReformCd;
    }

    /**
     * �A�b�v���[�h�t�@�C�� �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �A�b�v���[�h�t�@�C�� �p�����[�^
     */
    public FileItem[] getAddFilePath() {
        return addFilePath;
    }

    /**
     * �A�b�v���[�h�t�@�C�� �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM addFilePath �A�b�v���[�h�t�@�C�� �p�����[�^
     */
    public void setAddFilePath(FileItem[] addFilePath) {
        this.addFilePath = addFilePath;
    }

    /**
     * �t�@�C�������p�X �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �t�@�C�������p�X �p�����[�^
     */
    public String[] getAddHidPath() {
        return addHidPath;
    }

    /**
     * �t�@�C�������p�X �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM addHidPath �t�@�C�������p�X �p�����[�^
     */
    public void setAddHidPath(String[] addHidPath) {
        this.addHidPath = addHidPath;
    }

    /**
     * �\���� �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �\���� �p�����[�^
     */
    public String[] getAddSortOrder() {
        return addSortOrder;
    }

    /**
     * �\���� �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM addSortOrder �\���� �p�����[�^
     */
    public void setAddSortOrder(String[] addSortOrder) {
        this.addSortOrder = addSortOrder;
    }

    /**
     * �t�@�C���� �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �t�@�C���� �p�����[�^
     */
    public String[] getAddHidFileName() {
        return addHidFileName;
    }

    /**
     * �t�@�C���� �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM addHidFileName �t�@�C���� �p�����[�^
     */
    public void setAddHidFileName(String[] addHidFileName) {
        this.addHidFileName = addHidFileName;
    }

    /**
     * �{������ �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �{������ �p�����[�^
     */
    public String[] getAddRoleId() {
        return addRoleId;
    }

    /**
     * �{������ �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM addRoleId �{������ �p�����[�^
     */
    public void setAddRoleId(String[] addRoleId) {
        this.addRoleId = addRoleId;
    }

    /**
     * �\������ �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �\������ �p�����[�^
     */
    public String[] getAddImgName() {
        return addImgName;
    }

    /**
     * �\������ �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM addImgName �\������ �p�����[�^
     */
    public void setAddImgName(String[] addImgName) {
        this.addImgName = addImgName;
    }

    /**
     * ���i �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return ���i �p�����[�^
     */
    public String[] getAddReformPrice() {
        return addReformPrice;
    }

    /**
     * ���i �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM addReformPrice ���i �p�����[�^
     */
    public void setAddReformPrice(String[] addReformPrice) {
        this.addReformPrice = addReformPrice;
    }

    /**
     * �{������ �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �{������ �p�����[�^
     */
    public String[] getOldRoleId() {
        return oldRoleId;
    }

    /**
     * �{������ �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM oldRoleId �{������ �p�����[�^
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
     * �}�� �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �V�X�e�����t�H�[��CD �p�����[�^
     */
    public String[] getDivNo() {
        return divNo;
    }

    /**
     * �}�� �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM divNo �}�� �p�����[�^
     */
    public void setDivNo(String[] divNo) {
        this.divNo = divNo;
    }

    /**
     * �{������ �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �{������ �p�����[�^
     */
    public String[] getRoleId() {
        return roleId;
    }

    /**
     * �{������ �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM roleId �{������ �p�����[�^
     */
    public void setRoleId(String[] roleId) {
        this.roleId = roleId;
    }

    /**
     * �摜 �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �摜 �p�����[�^
     */
    public String[] getPathName() {
        return pathName;
    }

    /**
     * �摜 �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM pathName �摜 �p�����[�^
     */
    public void setPathName(String[] pathName) {
        this.pathName = pathName;
    }

    /**
     * �\���� �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �\���� �p�����[�^
     */
    public String[] getSortOrder() {
        return sortOrder;
    }

    /**
     * �\���� �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM sortOrder �\���� �p�����[�^
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
     * ���� �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return ���� �p�����[�^
     */
    public String[] getImgName() {
        return imgName;
    }

    /**
     * ���� �p�����[�^��ݒ肷��B<BR/>>
     * <BR/>
     *
     * @PARAM imgName ���� �p�����[�^
     */
    public void setImgName(String[] imgName) {
        this.imgName = imgName;
    }

    /**
     * ���i �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return ���i �p�����[�^
     */
    public String[] getReformPrice() {
        return reformPrice;
    }

    /**
     * ���i �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM reformPrice ���i �p�����[�^
     */
    public void setReformPrice(String[] reformPrice) {
        this.reformPrice = reformPrice;
    }

    /**
     * �폜�t���O �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �폜�t���O �p�����[�^
     */
    public String[] getDelFlg() {
        return delFlg;
    }

    /**
     * �폜�t���O �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM roleId �폜�t���O �p�����[�^
     */
    public void setDelFlg(String[] delFlg) {
        this.delFlg = delFlg;
    }

    /**
     * �R�}���h�p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �R�}���h �p�����[�^
     */
    public String getCommand() {
        return command;
    }

    /**
     * �R�}���h �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM command �R�}���h �p�����[�^
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * ���������I�u�W�F�N�g���쐬����B<br/>
     * �o�͗p�Ȃ̂Ńy�[�W���������O�������������𐶐�����B<br/>
     * <br/>
     *
     * @return ���������I�u�W�F�N�g
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
     * �o���f�[�V��������<br/>
     * ���N�G�X�g�p�����[�^�̃o���f�[�V�������s��<br/>
     * <br/>
     * ���� Form �N���X�̃o���f�[�V�������\�b�h�́AValidateable �C���^�[�t�F�[�X�̎����ł͖����̂ŁA
     * �o���f�[�V�������s���̈������قȂ�̂ŁA���� Form ���܂Ƃ߂ăo���f�[�V��������ꍇ�Ȃǂ͒��� ���鎖�B<br/>
     * <br/>
     *
     * @param errors
     *            �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     * @param command
     *            �������[�h ("insert" or "update")
     * @return ���펞 true�A�G���[�� false
     */
    @Override
    public boolean validate(List<ValidationFailure> errors) {
        int startSize = errors.size();

            // ���t�H�[���ڍ׏����͂̉�ʃ`�F�b�N���s��
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
                                "reformImgFileAllNull", "�A�b�v���[�h�摜", "", null);
                        errors.add(vf);
	                }
            	}else{
            		wkPathName = new String[this.getAddHidFileName().length];
            		wkPathName = this.getAddHidFileName();
            	}

            	for (int i = 0; i < wkPathName.length; i++) {


	                // �o���f�[�V�����`�F�b�N���s��
	                if (!StringValidateUtil.isEmpty(wkPathName[i])
	                        && !StringValidateUtil.isEmpty(this.getAddSortOrder()[i])
	                        && !StringValidateUtil.isEmpty(this.getAddImgName()[i])
	                        && !StringValidateUtil.isEmpty(this.getAddReformPrice()[i])
	                        && !StringValidateUtil.isEmpty(this.getAddRoleId()[i])) {

	                	if(this.getAddFilePath()!=null){

	                		 // Validate path_name �摜
	                        ValidationChain valPathName = new ValidationChain(
	                                "field.reformDtl.PathName", getAddFilePath()[i].getSize());
	                        // PDF�`�F�b�N
	                        valPathName.addValidation(new LineAdapter(new ReformImgPathJpgValidation(getAddFilePath()[0].getName(),"pdf"), i+1));
	                        // �t�@�C���T�C�Y�`�F�b�N
	                        valPathName.addValidation(new LineAdapter(
	                                new ReformImgSizeValidation(PanaCommonConstant.updFileSize), i+1));
	                        valPathName.validate(errors);
	                	}

	                    // Validate sort_order �\����
	                    ValidationChain valSortOrder = new ValidationChain(
	                            "field.reformDtl.SortOrder", getAddSortOrder()[i]);
	                    // �ő包�� 3��
	                    valSortOrder.addValidation(new LineAdapter(
	                            new MaxLengthValidation(3), i+1));
	                    // ���p����
	                    valSortOrder.addValidation(new LineAdapter(
	                            new NumericValidation(), i+1));
	                    valSortOrder.validate(errors);

	                    // ����
	                    ValidationChain valImgComment = new ValidationChain(
	                            "field.reformDtl.ImgName", getAddImgName()[i]);
	                    // �ő包�� 20��
	                    valImgComment.addValidation(new LineAdapter(
	                            new MaxLengthValidation(20), i+1));
	                    valImgComment.validate(errors);

	                    // ���i
	                    ValidationChain valReforPrice = new ValidationChain(
	                            "field.reformDtl.ReformPrice",getAddReformPrice()[i]);

                        // �ő包�� 7��
	                    valReforPrice.addValidation(new LineAdapter(
                                new MaxLengthValidation(7), i + 1));
                        // ���p�����`�F�b�N
	                    valReforPrice.addValidation(new LineAdapter(
                                new NumericValidation(), i + 1));

	                    valReforPrice.validate(errors);

	                    // Validate Role_id �{������
	                    ValidationChain valRoleId = new ValidationChain(
	                            "field.reformImg.RoleId",getAddRoleId()[i]);

	                    // �p�^�[���`�F�b�N
	                    valRoleId.addValidation(new LineAdapter(
	                            new CodeLookupValidation(this.codeLookupManager,
	                                    "ImageInfoRoleId"), i+1));
	                    valRoleId.validate(errors);
	                }

	                // ���ڂ͑S�ē��͂ł͂Ȃ��A�S�ċ󔒂ł͂Ȃ��ꍇ
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

	                        String param[] = new String[] { "�A�b�v���[�h�摜�A�\�����A���́A�{�������A���i" };
	                        ValidationFailure vf = new ValidationFailure(
	                                "oneInputOtherMustInput", "", "", param);
	                        errors.add(vf);
	                    }
	                }
            	}
            } else {
                // �o���f�[�V�����`�F�b�N���s��
                if (this.getPathName() != null) {
                    for (int i = 0; i < this.getPathName().length; i++) {
                        // Validate path_name �摜
                        ValidationChain valPathName = new ValidationChain(
                                "field.reformDtl.PathName", getPathName()[i]);
                        // �K�{�`�F�b�N
                        valPathName.addValidation(new LineAdapter(
                                new NullOrEmptyCheckValidation(), i + 1));
                        valPathName.validate(errors);

                        // Validate sort_order �\����
                        ValidationChain valSortOrder = new ValidationChain(
                                "field.reformDtl.SortOrder", getSortOrder()[i]);
                        // �K�{�`�F�b�N
                        valSortOrder.addValidation(new LineAdapter(
                                new NullOrEmptyCheckValidation(), i + 1));
                        // �ő包�� 3��
                        valSortOrder.addValidation(new LineAdapter(
                                new MaxLengthValidation(3), i + 1));
                        // ���p����
                        valSortOrder.addValidation(new LineAdapter(
                                new NumericValidation(), i + 1));
                        valSortOrder.validate(errors);

                        // Validate Img_name ����
                        ValidationChain valImg_name = new ValidationChain(
                                "field.reformDtl.ImgName", getImgName()[i]);
                        // �K�{�`�F�b�N
                        valImg_name.addValidation(new LineAdapter(
                                new NullOrEmptyCheckValidation(), i + 1));
                        // �ő包�� 20��
                        valImg_name.addValidation(new LineAdapter(
                                new MaxLengthValidation(20), i + 1));
                        valImg_name.validate(errors);

                        // Validate Reform_price ���i
                        ValidationChain valReform_price = new ValidationChain(
                                "field.reformDtl.ReformPrice",
                                getReformPrice()[i]);
                        // �K�{�`�F�b�N
                        valReform_price.addValidation(new LineAdapter(
                                new NullOrEmptyCheckValidation(), i + 1));
                        // �ő包�� 7��
                        valReform_price.addValidation(new LineAdapter(
                                new MaxLengthValidation(7), i + 1));
                        // ���p�����`�F�b�N
                        valReform_price.addValidation(new LineAdapter(
                                new NumericValidation(), i + 1));
                        valReform_price.validate(errors);

                        // Validate Role_id �{������
                        ValidationChain valRole_id = new ValidationChain(
                                "field.reformImg.RoleId", getRoleId()[i]);
                        // �K�{�`�F�b�N
                        valRole_id.addValidation(new LineAdapter(
                                new NullOrEmptyCheckValidation(), i + 1));
                        // �p�^�[���`�F�b�N
                        valRole_id.addValidation(new LineAdapter(
                                new CodeLookupValidation(
                                        this.codeLookupManager,
                                        "ImageInfoRoleId"), i + 1));
                        valRole_id.validate(errors);

                        ValidationChain valOldRole_id = new ValidationChain(
                                "field.reformImg.RoleId", getOldRoleId()[i]);

                        // �p�^�[���`�F�b�N
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
     * �t�H�[������VO�f�[�^���������ޏ����B<br/>
     * <br/>
     *
     * @param reformDtl
     *            �������ޑΏ�VO
     * @param i
     *            �A�b�v���[�h�Ώۉ摜���X�g�̃C���f�b�N�X
     *
     */
    public void copyToReformDtl(ReformDtl reformDtl, int idx) {

        if ("insert".equals(this.getCommand())) {
            // �V�X�e�����t�H�[��CD
            reformDtl.setSysReformCd(this.getSysReformCd());
            // �t�@�C����
            reformDtl.setFileName(this.getAddHidFileName()[idx]);
            // �\����
            reformDtl.setSortOrder(PanaStringUtils.toInteger(this.getAddSortOrder()[idx]));
            // �摜����
            reformDtl.setImgName(this.getAddImgName()[idx]);
            // ���t�H�[�����i
            reformDtl.setReformPrice(PanaStringUtils.toLong(this.getAddReformPrice()[idx]));
            // �摜�{������
            reformDtl.setRoleId(this.getAddRoleId()[idx]);
        } else {
            // �\����
            reformDtl
                    .setSortOrder(PanaStringUtils.toInteger(this.getSortOrder()[idx]));
            // �摜����
            reformDtl.setImgName(this.getImgName()[idx]);
            // ���t�H�[�����i
            reformDtl.setReformPrice(PanaStringUtils.toLong(this
                    .getReformPrice()[idx]));
            // �摜�{������
            reformDtl.setRoleId(this.getRoleId()[idx]);
        }
    }

    /**
     * �n���ꂽ�o���[�I�u�W�F�N�g���� Form �֏����l��ݒ肷��B<br/>
     * <br/>
     * @param adminUser AdminUserInterface�@�����������A���[�U�[���Ǘ��p�o���[�I�u�W�F�N�g
     * @param adminRole AdminUserRoleInterface �������������[�U�[���[���Ǘ��p�o���[�I�u�W�F�N�g
     */
    public void setDefaultData(List<ReformDtl> reformDtlList) {
        // �}��
        String[] divNo = new String[reformDtlList.size()];
        // �\����
        String[] sortOrder = new String[reformDtlList.size()];
        // ����
        String[] imgName = new String[reformDtlList.size()];
        // �t�@�C�����i��\���j
        String[] updHidFileName = new String[reformDtlList.size()];
        // �p�X��
        String[] pathName = new String[reformDtlList.size()];
        // ���i
        String[] reformPrice = new String[reformDtlList.size()];
        // �{������
        String[] roleId = new String[reformDtlList.size()];
        // �폜�t���O
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
        // �}��
        this.divNo = divNo;
        // �\����
        this.sortOrder = sortOrder;
        // ����
        this.imgName = imgName;
        // �t�@�C�����i��\���j
        this.updHidFileName = updHidFileName;
        // �p�X��
        this.pathName = pathName;
        // ���i
        this.reformPrice = reformPrice;
        // �{������
        this.roleId = roleId;
        // �{������
        this.oldRoleId = roleId;
        // �폜�t���O
        this.delFlg = delFlg;
    }
}
