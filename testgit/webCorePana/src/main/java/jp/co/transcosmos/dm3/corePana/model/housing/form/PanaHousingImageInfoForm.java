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
public class PanaHousingImageInfoForm extends HousingImgForm implements Validateable {

    /** �V�X�e������CD */
    private String sysHousingCd;
    /** �����ԍ� */
    private String housingCd;
    /** �������� */
    private String displayHousingName;
    /** �������CD */
    private String housingKindCd;


    /** �A�b�v���[�h�t�@�C��(�ǉ��p�j */
    private FileItem[] addFilePath;
    /** �t�@�C�������p�X(�ǉ��p�j */
    private String[] addHidPath;
    /** �t�@�C�������p�X(�ǉ��p�jMin */
    private String[] addHidPathMin;
    /** �t�@�C����(�ǉ��p�j */
    private String[] addHidFileName;
    /** �{������(�ǉ��p�j */
    private String[] addRoleId;
    /** �\����(�ǉ��p�j */
    private String[] addSortOrder;
    /** ���(�ǉ��p�j */
    private String[] addImageType;
    /** �R�����g(�ǉ��p�j*/
    private String[] addImgComment;

    /** �{������ */
    private String[] roleId;
    /** �摜 */
    private String[] pathName;
    /** �摜 */
    private String[] hidPathMax;
    /** �摜 */
    private String[] hidPathMin;

    /** �{�������i�X�V�ێ��p�j */
    private String[] oldRoleId;

    /** �R�}���h */
    private String command;
    private String confirm;

    /** ���ʃR�[�h�ϊ����� */
    private CodeLookupManager codeLookupManager;

    /**
     * �t�@�C�������p�X �p�����[�^���擾����B<br/>
     * �t�@�C�������p�X �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �t�@�C�������p�X �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �t�@�C�������p�X �p�����[�^
     */
    public String[] getHidPathMax() {
		return hidPathMax;
	}

    /**
     * �t�@�C�������p�X �p�����[�^��ݒ肷��B<BR/>
     * �t�@�C�������p�X �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �t�@�C�������p�X �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM hidPathMax �t�@�C�������p�X �p�����[�^
     */
	public void setHidPathMax(String[] hidPathMax) {
		this.hidPathMax = hidPathMax;
	}

	/**
     * �t�@�C�������p�X �p�����[�^���擾����B<br/>
     * �t�@�C�������p�X �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �t�@�C�������p�X �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �t�@�C�������p�X �p�����[�^
     */
	public String[] getHidPathMin() {
		return hidPathMin;
	}

    /**
     * �t�@�C�������p�X �p�����[�^��ݒ肷��B<BR/>
     * �t�@�C�������p�X �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �t�@�C�������p�X �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM hidPathMin �t�@�C�������p�X �p�����[�^
     */
	public void setHidPathMin(String[] hidPathMin) {
		this.hidPathMin = hidPathMin;
	}

	/**
     * �A�b�v���[�h�t�@�C��(�ǉ��p�j �p�����[�^���擾����B<br/>
     * �A�b�v���[�h�t�@�C��(�ǉ��p�j �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �A�b�v���[�h�t�@�C��(�ǉ��p�j �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �A�b�v���[�h�t�@�C��(�ǉ��p�j �p�����[�^
     */
	public FileItem[] getAddFilePath() {
		return addFilePath;
	}

	/**
     * �A�b�v���[�h�t�@�C��(�ǉ��p�j �p�����[�^��ݒ肷��B<BR/>
     * �A�b�v���[�h�t�@�C��(�ǉ��p�j �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �A�b�v���[�h�t�@�C��(�ǉ��p�j �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM addFilePath �A�b�v���[�h�t�@�C��(�ǉ��p�j �p�����[�^
     */
	public void setAddFilePath(FileItem[] addFilePath) {
		this.addFilePath = addFilePath;
	}

	/**
     * �t�@�C�������p�X(�ǉ��p�j �p�����[�^���擾����B<br/>
     * �t�@�C�������p�X(�ǉ��p�j �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �t�@�C�������p�X(�ǉ��p�j �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �t�@�C�������p�X(�ǉ��p�j �p�����[�^
     */
	public String[] getAddHidPath() {
		return addHidPath;
	}

	/**
     * �t�@�C�������p�X(�ǉ��p�j �p�����[�^��ݒ肷��B<BR/>
     * �t�@�C�������p�X(�ǉ��p�j �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �t�@�C�������p�X(�ǉ��p�j �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM addHidPath �t�@�C�������p�X(�ǉ��p�j �p�����[�^
     */
	public void setAddHidPath(String[] addHidPath) {
		this.addHidPath = addHidPath;
	}

	/**
     * �t�@�C�������p�X(�ǉ��p�jMin �p�����[�^���擾����B<br/>
     * �t�@�C�������p�X(�ǉ��p�jMin �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �t�@�C�������p�X(�ǉ��p�j �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �t�@�C�������p�X(�ǉ��p�jMin �p�����[�^
     */
	public String[] getAddHidPathMin() {
		return addHidPathMin;
	}

	/**
     * �t�@�C�������p�X(�ǉ��p�jMin �p�����[�^��ݒ肷��B<BR/>
     * �t�@�C�������p�X(�ǉ��p�jMin �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �t�@�C�������p�X(�ǉ��p�j �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM addHidPathMin �t�@�C�������p�X(�ǉ��p�jMin �p�����[�^
     */
	public void setAddHidPathMin(String[] addHidPathMin) {
		this.addHidPathMin = addHidPathMin;
	}

	/**
     * �t�@�C����(�ǉ��p�j �p�����[�^���擾����B<br/>
     * �t�@�C����(�ǉ��p�j �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �t�@�C����(�ǉ��p�j�N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �t�@�C�������p�X(�ǉ��p�j �p�����[�^
     */
	public String[] getAddHidFileName() {
		return addHidFileName;
	}

	/**
     * �t�@�C����(�ǉ��p�j�p�����[�^��ݒ肷��B<BR/>
     * �t�@�C����(�ǉ��p�j�p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �t�@�C����(�ǉ��p�j�N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM addHidFileName �t�@�C����(�ǉ��p�j �p�����[�^
     */
	public void setAddHidFileName(String[] addHidFileName) {
		this.addHidFileName = addHidFileName;
	}

	/**
     * �{������(�ǉ��p�j�p�����[�^���擾����B<br/>
     * �{������(�ǉ��p�j�p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �{������(�ǉ��p�j�N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �{������(�ǉ��p�j�p�����[�^
     */
	public String[] getAddRoleId() {
		return addRoleId;
	}

	/**
     * �{������(�ǉ��p�j�p�����[�^��ݒ肷��B<BR/>
     * �{������(�ǉ��p�j�p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �{������(�ǉ��p�j�N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM addRoleId �{������(�ǉ��p�j�p�����[�^
     */
	public void setAddRoleId(String[] addRoleId) {
		this.addRoleId = addRoleId;
	}

	/**
     * �\����(�ǉ��p�j�p�����[�^���擾����B<br/>
     * �\����(�ǉ��p�j�p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p����\����(�ǉ��p�j�N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �\����(�ǉ��p�j�p�����[�^
     */
	public String[] getAddSortOrder() {
		return addSortOrder;
	}

	/**
     * �\����(�ǉ��p�j�p�����[�^��ݒ肷��B<BR/>
     * �\����(�ǉ��p�j�p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �\����(�ǉ��p�j�N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM addSortOrder �\����(�ǉ��p�j�p�����[�^
     */
	public void setAddSortOrder(String[] addSortOrder) {
		this.addSortOrder = addSortOrder;
	}

	/**
     * ���(�ǉ��p�j �p�����[�^���擾����B<br/>
     * ���(�ǉ��p�j �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���(�ǉ��p�j �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �\����(�ǉ��p�j�p�����[�^
     */
	public String[] getAddImageType() {
		return addImageType;
	}

	/**
     * ���(�ǉ��p�j �p�����[�^��ݒ肷��B<BR/>
     * ���(�ǉ��p�j �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���(�ǉ��p�j �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM addImageType ���(�ǉ��p�j �p�����[�^
     */
	public void setAddImageType(String[] addImageType) {
		this.addImageType = addImageType;
	}

	/**
     * �R�����g(�ǉ��p�j�p�����[�^���擾����B<br/>
     * �R�����g(�ǉ��p�j�p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �R�����g(�ǉ��p�j�N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �\����(�ǉ��p�j�p�����[�^
     */
	public String[] getAddImgComment() {
		return addImgComment;
	}

	/**
     * �R�����g(�ǉ��p�j�p�����[�^���擾����B<br/>
     * �R�����g(�ǉ��p�j�p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �R�����g(�ǉ��p�j�N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return addImgComment �R�����g(�ǉ��p�j�p�����[�^
     */
	public void setAddImgComment(String[] addImgComment) {
		this.addImgComment = addImgComment;
	}

	/**
     * �{�������i�X�V�ێ��p�j�p�����[�^���擾����B<br/>
     * �{�������i�X�V�ێ��p�j�p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �{�������i�X�V�ێ��p�j�N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �\����(�ǉ��p�j�p�����[�^
     */
	public String[] getOldRoleId() {
		return oldRoleId;
	}

	/**
     * �{�������i�X�V�ێ��p�j�p�����[�^���擾����B<br/>
     * �{�������i�X�V�ێ��p�j�p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �{�������i�X�V�ێ��p�j�N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return oldRoleId �{�������i�X�V�ێ��p�j�p�����[�^
     */
	public void setOldRoleId(String[] oldRoleId) {
		this.oldRoleId = oldRoleId;
	}

    PanaHousingImageInfoForm(CodeLookupManager codeLookupManager) {
        super();
        this.codeLookupManager = codeLookupManager;
    }

    /**
     * �V�X�e������CD�p�����[�^���擾����B<br/>
     * �V�X�e������CD�p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �V�X�e������CD�N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �\����(�ǉ��p�j�p�����[�^
     */
    public String getSysHousingCd() {
		return sysHousingCd;
	}

    /**
     * �V�X�e������CD�p�����[�^���擾����B<br/>
     * �V�X�e������CD�p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �V�X�e������CD�N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return sysHousingCd �V�X�e������CD�p�����[�^
     */
	public void setSysHousingCd(String sysHousingCd) {
		this.sysHousingCd = sysHousingCd;
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
     * �����ԍ� �p�����[�^��ݒ肷��B<BR/>
     * �����ԍ� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �����ԍ� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM HOUSINGCD �����ԍ� �p�����[�^
     */
    public void setHousingCd(String housingCd) {
        this.housingCd = housingCd;
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
     * �������� �p�����[�^��ݒ肷��B<BR/>
     * �������� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �������� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM displayHousingName �������� �p�����[�^
     */
    public void setDisplayHousingName(String displayHousingName) {
        this.displayHousingName = displayHousingName;
    }

    /**
     * �������CD �p�����[�^���擾����B<br/>
     * �������CD �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �������CD �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �V�X�e�����t�H�[��CD �p�����[�^
     */
    public String getHousingKindCd() {
        return housingKindCd;
    }

    /**
     * �������CD �p�����[�^��ݒ肷��B<BR/>
     * �������CD �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �������CD �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM housingKindCd �������CD �p�����[�^
     */
    public void setHousingKindCd(String housingKindCd) {
        this.housingKindCd = housingKindCd;
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
     * �摜 �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �摜 �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �摜 �p�����[�^
     */
    public String[] getPathName() {
        return pathName;
    }

    /**
     * �摜 �p�����[�^��ݒ肷��B<BR/>
     * �摜 �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �摜 �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM pathName �摜 �p�����[�^
     */
    public void setPathName(String[] pathName) {
        this.pathName = pathName;
    }

    /**
     * �R�}���h�p�����[�^���擾����B<br/>
     * �R�}���h �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �R�}���h �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �R�}���h �p�����[�^
     */
    public String getCommand() {
        return command;
    }

    /**
     * �R�}���h �p�����[�^��ݒ肷��B<BR/>
     * �R�}���h �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p����R�}���h �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM command �R�}���h �p�����[�^
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
        int startSize = errors.size();            // ���t�H�[���ڍ׏����͂̉�ʃ`�F�b�N���s��
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

	                    // Validate path_name �摜
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
	                        && !StringValidateUtil.isEmpty(this.getAddImageType()[i])
	                        && !StringValidateUtil.isEmpty(this.getAddRoleId()[i])) {

	                	if(this.getAddFilePath()!=null){
	                		 // Validate path_name �摜
	                        ValidationChain valPathName = new ValidationChain(
	                                "housingImageInfo.input.pathName", getAddFilePath()[i].getSize());
	                        // JPG�`�F�b�N
	                        valPathName.addValidation(new LineAdapter(
	                                new ReformImgPathJpgValidation(getAddFilePath()[i].getName(),"jpg"), i+1));
	                        // �t�@�C���T�C�Y�`�F�b�N
	                        valPathName.addValidation(new LineAdapter(
	                                new ReformImgSizeValidation(PanaCommonConstant.updFileSize), i+1));
	                        valPathName.validate(errors);
	                	}

	                    // Validate sort_order �\����
	                    ValidationChain valSortOrder = new ValidationChain(
	                            "housingImageInfo.input.sortOrder", getAddSortOrder()[i]);
	                    // �ő包�� 3��
	                    valSortOrder.addValidation(new LineAdapter(
	                            new MaxLengthValidation(3), i+1));
	                    // ���p����
	                    valSortOrder.addValidation(new LineAdapter(
	                            new NumericValidation(), i+1));
	                    valSortOrder.validate(errors);

	                    // Validate Role_id �{������
	                    ValidationChain valImageType = new ValidationChain(
	                            "housingImageInfo.input.imageType",getAddImageType()[i]);

	                    // �p�^�[���`�F�b�N
	                    valImageType.addValidation(new LineAdapter(
	                            new CodeLookupValidation(this.codeLookupManager,
	                                    "ImageType"), i+1));
	                    valImageType.validate(errors);

	                    // Validate Role_id �{������
	                    ValidationChain valRoleId = new ValidationChain(
	                            "housingImageInfo.input.roleId",getAddRoleId()[i]);

	                    // �p�^�[���`�F�b�N
	                    valRoleId.addValidation(new LineAdapter(
	                            new CodeLookupValidation(this.codeLookupManager,
	                                    "ImageInfoRoleId"), i+1));
	                    valRoleId.validate(errors);

	                    // ��ʂ�'����'�A�{��������'�S����'�̏ꍇ
	                    if (PanaCommonConstant.IMAGE_TYPE_03.equals(this.getAddImageType()[i])) {
	                        if (PanaCommonConstant.ROLE_ID_PUBLIC.equals(this.getAddRoleId()[i])) {

	                            ValidationFailure vf = new ValidationFailure(
	                                    "housingImageInfoError", "", "", null);
	                            errors.add(vf);
	                        }
	                    }
	                }

	                // �R�����g
                    ValidationChain valImgComment = new ValidationChain(
                            "housingImageInfo.input.imgComment", getAddImgComment()[i]);
                    // �ő包�� 50��
                    valImgComment.addValidation(new LineAdapter(
                            new MaxLengthValidation(50), i+1));
                    valImgComment.validate(errors);

	                // ���ڂ͑S�ē��͂ł͂Ȃ��A�S�ċ󔒂ł͂Ȃ��ꍇ
	                if (!StringValidateUtil.isEmpty(wkPathName[i])
	                        || !StringValidateUtil.isEmpty(this.getAddSortOrder()[i])
	                        || !StringValidateUtil.isEmpty(this.getAddImageType()[i])
	                        || !StringValidateUtil.isEmpty(this.getAddRoleId()[i])) {
	                    if (StringValidateUtil.isEmpty(wkPathName[i])
		                        || StringValidateUtil.isEmpty(this.getAddSortOrder()[i])
		                        || StringValidateUtil.isEmpty(this.getAddImageType()[i])
		                        || StringValidateUtil.isEmpty(this.getAddRoleId()[i])) {

	                        String param[] = new String[] { "�A�b�v���[�h�摜�A�\�����A��ʁA�{������" };
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
                                "housingImageInfo.input.pathName", getPathName()[i]);
                        // �K�{�`�F�b�N
                        valPathName.addValidation(new LineAdapter(
                                new NullOrEmptyCheckValidation(), i + 1));
                        valPathName.validate(errors);

                        // Validate sort_order �\����
                        ValidationChain valSortOrder = new ValidationChain(
                                "housingImageInfo.input.sortOrder", getSortOrder()[i]);
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

                        // �R�����g
                        ValidationChain valImgComment = new ValidationChain(
                                "housingImageInfo.input.imgComment", getImgComment()[i]);
                        // �ő包�� 50��
                        valImgComment.addValidation(new LineAdapter(
                                new MaxLengthValidation(50), i+1));
                        valImgComment.validate(errors);

                        // Validate Role_id �{������
                        ValidationChain valRoleId = new ValidationChain(
                                "housingImageInfo.input.roleId", getRoleId()[i]);
                        // �p�^�[���`�F�b�N
                        valRoleId.addValidation(new LineAdapter(
                                new CodeLookupValidation(this.codeLookupManager,
                                        "ImageInfoRoleId"), i + 1));
                        // �K�{�`�F�b�N
                        valRoleId.addValidation(new LineAdapter(
                                new NullOrEmptyCheckValidation(), i + 1));
                        valRoleId.validate(errors);


                        // ���
                        ValidationChain valImageType = new ValidationChain(
                                "housingImageInfo.input.imageType", getImageType()[i]);
                        // �K�{�`�F�b�N
                        valImageType.addValidation(new LineAdapter(
                                new NullOrEmptyCheckValidation(), i + 1));
                        // �p�^�[���`�F�b�N
                        valImageType.addValidation(new LineAdapter(
                                new CodeLookupValidation(this.codeLookupManager,
                                        "ImageType"), i + 1));
                        valImageType.validate(errors);


                        // Validate Role_id �{������
                        ValidationChain valoOldRoleId = new ValidationChain(
                                "housingImageInfo.input.roleId", getOldRoleId()[i]);
                        // �p�^�[���`�F�b�N
                        valoOldRoleId.addValidation(new LineAdapter(
                                new CodeLookupValidation(this.codeLookupManager,
                                        "ImageInfoRoleId"), i + 1));
                        valoOldRoleId.validate(errors);


                        // ���
                        ValidationChain valOldImageType = new ValidationChain(
                                "housingImageInfo.input.imageType", getOldImageType()[i]);

                        // �p�^�[���`�F�b�N
                        valOldImageType.addValidation(new LineAdapter(
                                new CodeLookupValidation(this.codeLookupManager,
                                        "ImageType"), i + 1));
                        valOldImageType.validate(errors);

                        // ��ʂ�'����'�A�{��������'�S����'�̏ꍇ
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
     * �t�H�[������VO�f�[�^���������ޏ����B<br/>
     * <br/>
     *
     * @param housingImageInfo
     *            �������ޑΏ�VO
     * @param i
     *            �A�b�v���[�h�Ώۉ摜���X�g�̃C���f�b�N�X
     *
     */
    @Override
    public void copyToHousingImageInfo(HousingImageInfo housingImageInfo, int idx) {
    	// �V�X�e������CD
    	((jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo) housingImageInfo).setSysHousingCd(this.getSysHousingCd());

		// �摜�^�C�v
    	((jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo) housingImageInfo).setImageType(this.getImageType()[idx]);

		// �\����
		if (!StringValidateUtil.isEmpty(this.getSortOrder()[idx])){
			((jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo) housingImageInfo).setSortOrder(Integer.valueOf(this.getSortOrder()[idx]));
		}
		// �t�@�C����
		// ���t�H���_�̒i�K�ŁA�V�[�P���X�P�O���̃t�@�C�����Ƀ��l�[������Ă��鎖�B
		((jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo) housingImageInfo).setFileName(this.getFileName()[idx]);

		// �R�����g
		((jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo) housingImageInfo).setImgComment(this.getImgComment()[idx]);

		// roleId
		if (!StringValidateUtil.isEmpty(this.roleId[idx])) {
            ((jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo) housingImageInfo).setRoleId(this.getRoleId()[idx]);
        }
    }

	/**
	 * �����摜���̍X�V UpdateExpression �𐶐�����B<br/>
	 * <br/>
	 * �����摜���́A��L�[�l���X�V����ׁA�ʏ�� update() ���\�b�h�ł͍X�V�ł��Ȃ��B<br/>
	 * ���̃��\�b�h�����A����@UpdateExpression�@���g�p���čX�V����B<br/>
	 * <br/>
	 * @param idx �s�ʒu
	 *
	 * @return �X�V�^�C���X�^���v UPDATE �p�@UpdateExpression
	 */
    @Override
	public UpdateExpression[] buildUpdateExpression(int idx){

       // �V�X�e�������R�[�h�A�p�X���A�t�@�C�����͍X�V�Ώۂɂ͂Ȃ�Ȃ��̂Őݒ肵�Ȃ��B
       // ���C���摜�t���O���A�ʂ̏����Ńo�b�`�I�ɍX�V����̂Ōʂɂ͍X�V���Ȃ��B
       // �c���E�����t���O���A�摜�t�@�C�����̂��X�V����Ȃ��̂ŕύX�͔������Ȃ��B

       return new UpdateExpression[] {new UpdateValue("imageType", this.getImageType()[idx]),
       							      new UpdateValue("divNo", this.getDivNo()[idx]),
        							  new UpdateValue("sortOrder", this.getSortOrder()[idx]),
        							  new UpdateValue("imgComment", this.getImgComment()[idx]),
       								  new UpdateValue("roleId", this.getRoleId()[idx])};

	}
    /**
     * �n���ꂽ�o���[�I�u�W�F�N�g���� Form �֏����l��ݒ肷��B<br/>
     * <br/>
     * @param adminUser AdminUserInterface�@�����������A���[�U�[���Ǘ��p�o���[�I�u�W�F�N�g
     * @param adminRole AdminUserRoleInterface �������������[�U�[���[���Ǘ��p�o���[�I�u�W�F�N�g
     */
    public void setDefaultData(List<HousingImageInfo> housingImageInfoList) {
    	// ��ʍ��ڂ�ݒ肷�鏈��
        if (housingImageInfoList != null && housingImageInfoList.size() > 0) {
        	// �摜�^�C�v
        	String[] imageType = new String[housingImageInfoList.size()];
        	// �R�����g
        	String[] imgComment = new String[housingImageInfoList.size()];
            // �}��
        	String[] divNo = new String[housingImageInfoList.size()];
            // �摜
        	String[] pathName = new String[housingImageInfoList.size()];
        	// �t�@�C�����i��\���j
        	String[] fileName = new String[housingImageInfoList.size()];
            // �\����
        	String[] sortOrder = new String[housingImageInfoList.size()];;
            // �{������
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
