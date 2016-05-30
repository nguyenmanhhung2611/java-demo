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
 * ���t�H�[���摜�̓o�^�ƍX�V���A����p�t�H�[��.
 * <p>
 *
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * fan			2015.04.10	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���� Form �̃o���f�[�V�����́A�g�p���郂�[�h�i�ǉ����� or �X�V�����j�ňقȂ�̂ŁA �t���[�����[�N���񋟂��� Validateable
 * �C���^�[�t�F�[�X�͎������Ă��Ȃ��B<br/>
 * �o���f�[�V�������s���̃p�����[�^���ʏ�ƈقȂ�̂Œ��ӂ��鎖�B<br/>
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

    /** �V�X�e������CD */
    private String sysHousingCd;

    /** �V�X�e�����t�H�[��CD */
    private String sysReformCd;

    /** �\���� �p�����[�^ */
    private String[] uploadSortOrder;

    /** before �摜�p�X�� �p�����[�^ */
    private FileItem[] uploadBeforePathName;

    /** �t�@�C�������p�X(�ǉ��p�j */
    private String[] uploadBeforeHidPath;
    private String[] uploadBeforeHidPathMin;

    /** before �摜�p�X�� �p�����[�^ */
    private String[] uploadBeforeFileName;

    /** before �摜�R�����g �p�����[�^ */
    private String[] uploadBeforeComment;

    /** after �摜�p�X�� �p�����[�^ */
    private FileItem[] uploadAfterPathName;

    /** �t�@�C�������p�X(�ǉ��p�j */
    private String[] uploadAfterHidPath;
    private String[] uploadAfterHidPathMin;

    /** after �摜�p�X�� �p�����[�^ */
    private String[] uploadAfterFileName;

    /** after �摜�R�����g �p�����[�^ */
    private String[] uploadAfterComment;

    /** �A�b�v���[�h�̉{������ */
    private String[] uploadRoleId;


    /** �\���� �p�����[�^ */
    private String[] editSortOrder;

    /** before �摜�p�X�� �p�����[�^ */
    private String[] editBeforePathName;

    /** before �摜�R�����g �p�����[�^ */
    private String[] editBeforeComment;

    /** after �摜�p�X�� �p�����[�^ */
    private String[] editAfterPathName;

    /** after �摜�R�����g �p�����[�^ */
    private String[] editAfterComment;

    /** �{������ �p�����[�^ */
    private String[] editRoleId;

    /** �摜�� */
    private String[] editBeforeFileName;

    /** after �摜�� �p�����[�^ */
    private String[] editAfterFileName;

	/** �}�� */
    private String[] divNo;

    /** �폜�t���O */
    private String[] delFlg;

    /** �������� �p�����[�^ */
    private String displayHousingName;

    /** �����ԍ� �p�����[�^ */
    private String housingCd;

    /** �������CD */
    private String housingKindCd;

    /** �R�}���h */
    private String command;

    /** after �摜�t�@�C���� */
    private String afterFileName;

    /** before �摜�t�@�C���� */
    private String beforeFileName;


    /** �t�@�C�������p�X(�ǉ��p�j */
    private String[] beforeHidPathMax;

    /** �t�@�C�������p�X(�ǉ��p�j */
    private String[] afterHidPathMax;

    /** �t�@�C�������p�X(�ǉ��p�j */
    private String[] beforeHidPathMin;

    /** �t�@�C�������p�X(�ǉ��p�j */
    private String[] afterHidPathMin;


    /** �A�b�v���[�h�̉{�������i�X�V�ێ��p�j */
    private String[] editOldRoleId;

    /**
     * �t�@�C�������p�X(�ǉ��p�j �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �t�@�C�������p�X(�ǉ��p�j �p�����[�^
     */
    public String[] getUploadBeforeHidPathMin() {
		return uploadBeforeHidPathMin;
	}
    /**
     * �t�@�C�������p�X(�ǉ��p�j �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM uploadBeforeHidPathMin �t�@�C�������p�X(�ǉ��p�j �p�����[�^
     */
	public void setUploadBeforeHidPathMin(String[] uploadBeforeHidPathMin) {
		this.uploadBeforeHidPathMin = uploadBeforeHidPathMin;
	}
	/**
     * �t�@�C�������p�X(�ǉ��p�j �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �t�@�C�������p�X(�ǉ��p�j �p�����[�^
     */
	public String[] getUploadAfterHidPathMin() {
		return uploadAfterHidPathMin;
	}
	/**
     * �t�@�C�������p�X(�ǉ��p�j �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM uploadAfterHidPathMin �t�@�C�������p�X(�ǉ��p�j �p�����[�^
     */
	public void setUploadAfterHidPathMin(String[] uploadAfterHidPathMin) {
		this.uploadAfterHidPathMin = uploadAfterHidPathMin;
	}

	/**
     * �t�@�C�������p�X(�ǉ��p�j �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �t�@�C�������p�X(�ǉ��p�j �p�����[�^
     */
    public String[] getBeforeHidPathMax() {
		return beforeHidPathMax;
	}
    /**
     * �t�@�C�������p�X(�ǉ��p�j �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM beforeHidPathMax �t�@�C�������p�X(�ǉ��p�j �p�����[�^
     */
	public void setBeforeHidPathMax(String[] beforeHidPathMax) {
		this.beforeHidPathMax = beforeHidPathMax;
	}
	/**
     * �t�@�C�������p�X(�ǉ��p�j �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �t�@�C�������p�X(�ǉ��p�j �p�����[�^
     */
	public String[] getAfterHidPathMax() {
		return afterHidPathMax;
	}
	/**
     * �t�@�C�������p�X(�ǉ��p�j �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM afterHidPathMax �t�@�C�������p�X(�ǉ��p�j �p�����[�^
     */
	public void setAfterHidPathMax(String[] afterHidPathMax) {
		this.afterHidPathMax = afterHidPathMax;
	}
	/**
     * �t�@�C�������p�X(�ǉ��p�j �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �t�@�C�������p�X(�ǉ��p�j �p�����[�^
     */
	public String[] getBeforeHidPathMin() {
		return beforeHidPathMin;
	}
	/**
     * �t�@�C�������p�X(�ǉ��p�j �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM beforeHidPathMin �t�@�C�������p�X(�ǉ��p�j �p�����[�^
     */
	public void setBeforeHidPathMin(String[] beforeHidPathMin) {
		this.beforeHidPathMin = beforeHidPathMin;
	}
	/**
     * �t�@�C�������p�X(�ǉ��p�j �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �t�@�C�������p�X(�ǉ��p�j �p�����[�^
     */
	public String[] getAfterHidPathMin() {
		return afterHidPathMin;
	}
	/**
     * �t�@�C�������p�X(�ǉ��p�j �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM afterHidPathMin �t�@�C�������p�X(�ǉ��p�j �p�����[�^
     */
	public void setAfterHidPathMin(String[] afterHidPathMin) {
		this.afterHidPathMin = afterHidPathMin;
	}
	/**
     * �摜�� �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �摜�� �p�����[�^
     */
	public String[] getEditAfterFileName() {
		return editAfterFileName;
	}
	/**
     * �摜�� �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM editAfterFileName �摜�� �p�����[�^
     */
	public void setEditAfterFileName(String[] editAfterFileName) {
		this.editAfterFileName = editAfterFileName;
	}
	/**
     * �t�@�C�������p�X(�ǉ��p�j �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �t�@�C�������p�X(�ǉ��p�j �p�����[�^
     */
    public String[] getUploadBeforeHidPath() {
		return uploadBeforeHidPath;
	}
    /**
     * �t�@�C�������p�X(�ǉ��p�j �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM uploadBeforeHidPath �t�@�C�������p�X(�ǉ��p�j �p�����[�^
     */
	public void setUploadBeforeHidPath(String[] uploadBeforeHidPath) {
		this.uploadBeforeHidPath = uploadBeforeHidPath;
	}
	/**
     * �t�@�C�������p�X(�ǉ��p�j �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �t�@�C�������p�X(�ǉ��p�j �p�����[�^
     */
	public String[] getUploadAfterHidPath() {
		return uploadAfterHidPath;
	}
	 /**
     * �t�@�C�������p�X(�ǉ��p�j �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM uploadAfterHidPath �t�@�C�������p�X(�ǉ��p�j �p�����[�^
     */
	public void setUploadAfterHidPath(String[] uploadAfterHidPath) {
		this.uploadAfterHidPath = uploadAfterHidPath;
	}
	/**
     * �摜�� �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �摜�� �p�����[�^
     */
    public String[] getEditBeforeFileName() {
        return editBeforeFileName;
    }
    /**
     * �摜�� �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM editBeforeFileName �摜�� �p�����[�^
     */
    public void setEditBeforeFileName(String[] editBeforeFileName) {
        this.editBeforeFileName = editBeforeFileName;
    }
    /**
     * �A�b�v���[�h�̉{�������i�X�V�ێ��p�j �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �A�b�v���[�h�̉{�������i�X�V�ێ��p�j �p�����[�^
     */
    public String[] getEditOldRoleId() {
        return editOldRoleId;
    }
    /**
     * �A�b�v���[�h�̉{�������i�X�V�ێ��p�j �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM editOldRoleId �A�b�v���[�h�̉{�������i�X�V�ێ��p�j �p�����[�^
     */
    public void setEditOldRoleId(String[] editOldRoleId) {
        this.editOldRoleId = editOldRoleId;
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
     * @PARAM roleId �V�X�e������CD �p�����[�^
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
     * @PARAM roleId �V�X�e�����t�H�[��CD �p�����[�^
     */
    public void setSysReformCd(String sysReformCd) {
        this.sysReformCd = sysReformCd;
    }

    /**
     * �\���� �p�����[�^���擾����B<br/>
     * �\���� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �\���� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �\���� �p�����[�^
     */
    public String[] getUploadSortOrder() {
        return uploadSortOrder;
    }

    /**
     * �\���� �p�����[�^��ݒ肷��B<br/>
     * �\���� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �\���� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param sortOrder
     *            �\���� �p�����[�^
     */
    public void setUploadSortOrder(String[] uploadSortOrder) {
        this.uploadSortOrder = uploadSortOrder;
    }

    /**
     * before �摜�p�X�� �p�����[�^���擾����B<br/>
     * before �摜�p�X�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� before �摜�p�X��
     * �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return before �摜�p�X�� �p�����[�^
     */
    public FileItem[] getUploadBeforePathName() {
        return uploadBeforePathName;
    }

    /**
     * before �摜�p�X�� �p�����[�^��ݒ肷��B<br/>
     * before �摜�p�X�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� before �摜�p�X��
     * �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param beforePathName
     *            before �摜�p�X�� �p�����[�^
     */
    public void setUploadBeforePathName(FileItem[] uploadBeforePathName) {
        this.uploadBeforePathName = uploadBeforePathName;
    }

    /**
     * before �摜�R�����g �p�����[�^���擾����B<br/>
     * before �摜�R�����g �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� before �摜�R�����g
     * �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return before �摜�R�����g �p�����[�^
     */
    public String[] getUploadBeforeComment() {
        return uploadBeforeComment;
    }

    /**
     * before �摜�R�����g �p�����[�^��ݒ肷��B<br/>
     * before �摜�R�����g �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� before �摜�R�����g
     * �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param beforeComment
     *            before �摜�R�����g �p�����[�^
     */
    public void setUploadBeforeComment(String[] uploadBeforeComment) {
        this.uploadBeforeComment = uploadBeforeComment;
    }

    /**
     * after �摜�p�X�� �p�����[�^���擾����B<br/>
     * after �摜�p�X�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� after �摜�p�X�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return after �摜�p�X�� �p�����[�^
     */
    public FileItem[] getUploadAfterPathName() {
        return uploadAfterPathName;
    }

    /**
     * after �摜�p�X�� �p�����[�^��ݒ肷��B<br/>
     * after �摜�p�X�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� after �摜�p�X�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param afterPathName
     *            after �摜�p�X�� �p�����[�^
     */
    public void setUploadAfterPathName(FileItem[] uploadAfterPathName) {
        this.uploadAfterPathName = uploadAfterPathName;
    }

    /**
     * after �摜�R�����g �p�����[�^���擾����B<br/>
     * after �摜�R�����g �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� after �摜�R�����g
     * �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return after �摜�R�����g �p�����[�^
     */
    public String[] getUploadAfterComment() {
        return uploadAfterComment;
    }

    /**
     * after �摜�R�����g �p�����[�^��ݒ肷��B<br/>
     * after �摜�R�����g �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� after �摜�R�����g
     * �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param afterComment
     *            after �摜�R�����g �p�����[�^
     */
    public void setUploadAfterComment(String[] uploadAfterComment) {
        this.uploadAfterComment = uploadAfterComment;
    }

    /**
     * �\���� �p�����[�^���擾����B<br/>
     * �\���� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �\���� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �\���� �p�����[�^
     */
    public String[] getEditSortOrder() {
        return editSortOrder;
    }

    /**
     * �\���� �p�����[�^��ݒ肷��B<br/>
     * �\���� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �\���� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param sortOrder
     *            �\���� �p�����[�^
     */
    public void setEditSortOrder(String[] editSortOrder) {
        this.editSortOrder = editSortOrder;
    }

    /**
     * before �摜�p�X�� �p�����[�^���擾����B<br/>
     * before �摜�p�X�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� before �摜�p�X��
     * �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return before �摜�p�X�� �p�����[�^
     */
    public String[] getEditBeforePathName() {
        return editBeforePathName;
    }

    /**
     * before �摜�p�X�� �p�����[�^��ݒ肷��B<br/>
     * before �摜�p�X�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� before �摜�p�X��
     * �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param beforePathName
     *            before �摜�p�X�� �p�����[�^
     */
    public void setEditBeforePathName(String[] editBeforePathName) {
        this.editBeforePathName = editBeforePathName;
    }

    /**
     * before �摜�R�����g �p�����[�^���擾����B<br/>
     * before �摜�R�����g �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� before �摜�R�����g
     * �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return before �摜�R�����g �p�����[�^
     */
    public String[] getEditBeforeComment() {
        return editBeforeComment;
    }

    /**
     * �}�� �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �}�� �p�����[�^
     */
    public String[] getDivNo() {
        return divNo;
    }

    /**
     * �}�� �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM roleId �}�� �p�����[�^
     */
    public void setDivNo(String[] divNo) {
        this.divNo = divNo;
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
     * before �摜�R�����g �p�����[�^��ݒ肷��B<br/>
     * before �摜�R�����g �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� before �摜�R�����g
     * �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param beforeComment
     *            before �摜�R�����g �p�����[�^
     */
    public void setEditBeforeComment(String[] editBeforeComment) {
        this.editBeforeComment = editBeforeComment;
    }

    /**
     * after �摜�p�X�� �p�����[�^���擾����B<br/>
     * after �摜�p�X�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� after �摜�p�X�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return after �摜�p�X�� �p�����[�^
     */
    public String[] getEditAfterPathName() {
        return editAfterPathName;
    }

    /**
     * after �摜�p�X�� �p�����[�^��ݒ肷��B<br/>
     * after �摜�p�X�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� after �摜�p�X�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param afterPathName
     *            after �摜�p�X�� �p�����[�^
     */
    public void setEditAfterPathName(String[] editAfterPathName) {
        this.editAfterPathName = editAfterPathName;
    }

    /**
     * after �摜�R�����g �p�����[�^���擾����B<br/>
     * after �摜�R�����g �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� after �摜�R�����g
     * �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return after �摜�R�����g �p�����[�^
     */
    public String[] getEditAfterComment() {
        return editAfterComment;
    }

    /**
     * after �摜�R�����g �p�����[�^��ݒ肷��B<br/>
     * after �摜�R�����g �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� after �摜�R�����g
     * �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param afterComment
     *            after �摜�R�����g �p�����[�^
     */
    public void setEditAfterComment(String[] editAfterComment) {
        this.editAfterComment = editAfterComment;
    }

    /**
     * �{������ �p�����[�^���擾����B<br/>
     * �{������ �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �{������ �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �{������ �p�����[�^
     */
    public String[] getEditRoleId() {
        return editRoleId;
    }

    /**
     * �{������ �p�����[�^��ݒ肷��B<br/>
     * �{������ �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �{������ �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param roleId
     *            �{������ �p�����[�^
     */
    public void setEditRoleId(String[] editRoleId) {
        this.editRoleId = editRoleId;
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
     * before �摜�p�X�� �p�����[�^���擾����B<br/>
     * before �摜�p�X�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� before �摜�p�X��
     * �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return before �摜�p�X�� �p�����[�^
     */
    public String[] getUploadBeforeFileName() {
        return uploadBeforeFileName;
    }

    /**
     * before �摜�p�X�� �p�����[�^��ݒ肷��B<br/>
     * before �摜�p�X�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� before �摜�p�X��
     * �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param uploadBeforeFileName
     *            before �摜�p�X�� �p�����[�^
     */
    public void setUploadBeforeFileName(String[] uploadBeforeFileName) {
        this.uploadBeforeFileName = uploadBeforeFileName;
    }

    /**
     * after �摜�p�X�� �p�����[�^���擾����B<br/>
     * after �摜�p�X�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� after �摜�p�X�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return after �摜�p�X�� �p�����[�^
     */
    public String[] getUploadAfterFileName() {
        return uploadAfterFileName;
    }

    /**
     * after �摜�p�X�� �p�����[�^��ݒ肷��B<br/>
     * after �摜�p�X�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� after �摜�p�X�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param uploadAfterFileName
     *            after �摜�p�X�� �p�����[�^
     */
    public void setUploadAfterFileName(String[] uploadAfterFileName) {
        this.uploadAfterFileName = uploadAfterFileName;
    }


    /**
     * �R�����g���擾����B<br/>
     * �R�����g�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �R�����g �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �R�����g
     */
    public String getCommand() {
        return command;
    }

    /**
     * �R�����g��ݒ肷��B<br/>
     * �R�����g�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �R�����g �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param command
     *            �R�����g
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * CodeLookup���擾����B<br/>
     * CodeLookup�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� CodeLookup �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return CodeLookup
     */
    public CodeLookupManager getCodeLookupManager() {
        return codeLookupManager;
    }

    /**
     * CodeLookup��ݒ肷��B<br/>
     * CodeLookup�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� CodeLookup �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param codeLookupManager
     *            �R�����g
     */
    public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
        this.codeLookupManager = codeLookupManager;
    }

    /**
     * �{���������擾����B<br/>
     * �{�������̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �{������ �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �{������
     */
    public String[] getUploadRoleId() {
        return uploadRoleId;
    }

    /**
     * �{��������ݒ肷��B<br/>
     * �{�������̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �{������ �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param �{������
     *            uploadRoleId
     */
    public void setUploadRoleId(String[] uploadRoleId) {
        this.uploadRoleId = uploadRoleId;
    }
    /**
     * �摜�t�@�C���� �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �摜�t�@�C���� �p�����[�^
     */
    public String getAfterFileName() {
        return afterFileName;
    }
    /**
     * �摜�t�@�C���� �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM afterFileName �摜�t�@�C���� �p�����[�^
     */
    public void setAfterFileName(String afterFileName) {
        this.afterFileName = afterFileName;
    }
    /**
     * �摜�t�@�C���� �p�����[�^���擾����B<br/>
     * <br/>
     *
     * @return �摜�t�@�C���� �p�����[�^
     */
    public String getBeforeFileName() {
        return beforeFileName;
    }
    /**
     * �摜�t�@�C���� �p�����[�^��ݒ肷��B<BR/>
     * <BR/>
     *
     * @PARAM afterFileName �摜�t�@�C���� �p�����[�^
     */
    public void setBeforeFileName(String beforeFileName) {
        this.beforeFileName = beforeFileName;
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
     *
     * @return ���펞 true�A�G���[�� false
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


            // Validate sort_order �\����
            ValidationChain valUploadSortOrder = new ValidationChain(
                    "field.reformImg.SortOrder", getUploadSortOrder()[0]);

            // �K�{���̓`�F�b�N
            valUploadSortOrder.addValidation(new NullOrEmptyCheckValidation());

            // �ő包�� 3��
            valUploadSortOrder.addValidation(new MaxLengthValidation(3));

            // ���p����
            valUploadSortOrder.addValidation(new NumericValidation());

            valUploadSortOrder.validate(errors);

            // Validate before_comment before �摜�R�����g
            ValidationChain valUploadBeforeComment = new ValidationChain(
                    "field.reformImg.BeformComment",
                    getUploadBeforeComment()[0]);

            // �ő包�� 50��
            valUploadBeforeComment.addValidation(new MaxLengthValidation(50));

            valUploadBeforeComment.validate(errors);

            // Validate before_path �摜�p�X��
            ValidationChain valUploadBeforePath = new ValidationChain(
                    "field.reformImg.BeforePathName",wkBeforePathName[0]);

            // �A�b�v���[�h�摜_Before��I������ꍇ�A�R�����g_Before����͂��Ă��Ȃ��ꍇ
            valUploadBeforePath
                    .addValidation(new NullOrEmptyCheckValidation());

            valUploadBeforePath.validate(errors);

            if(this.getUploadBeforePathName()!=null){
       		 	// Validate path_name �摜
               ValidationChain valPathName = new ValidationChain(
                       "field.reformImg.BeforePathName", getUploadBeforePathName()[0].getSize());
               // JPG�`�F�b�N
               valPathName.addValidation(new ReformImgPathJpgValidation(getUploadBeforePathName()[0].getName(),"jpg"));
               // �t�@�C���T�C�Y�`�F�b�N
               valPathName.addValidation(new ReformImgSizeValidation(PanaCommonConstant.updFileSize));
               valPathName.validate(errors);
            }

            // Validate after_comment after �摜�R�����g
            ValidationChain valUploadAfterComment = new ValidationChain(
                    "field.reformImg.AfterComment", getUploadAfterComment()[0]);

            // �ő包�� 50��
            valUploadAfterComment.addValidation(new MaxLengthValidation(50));

            valUploadAfterComment.validate(errors);

            // Validate after_path �摜�p�X��
            ValidationChain valUploadAfterPath = new ValidationChain(
                    "field.reformImg.AfterPathName",wkAfterPathName[0]);

            // �K�{���̓`�F�b�N
            valUploadAfterPath
                    .addValidation(new NullOrEmptyCheckValidation());

            valUploadAfterPath.validate(errors);

            if(this.getUploadAfterPathName()!=null){
      		  // Validate path_name �摜
              ValidationChain valPathName = new ValidationChain(
                      "field.reformImg.AfterPathName", getUploadAfterPathName()[0].getSize());
              // JPG�`�F�b�N
              valPathName.addValidation(new ReformImgPathJpgValidation(getUploadAfterPathName()[0].getName(),"jpg"));
              // �t�@�C���T�C�Y�`�F�b�N
              valPathName.addValidation(new ReformImgSizeValidation(PanaCommonConstant.updFileSize));
              valPathName.validate(errors);
           }

            // Validate role_no �{������
            ValidationChain valUploadRoleNo = new ValidationChain(
                    "field.reformImg.RoleId", getUploadRoleId()[0]);

            // �K�{���̓`�F�b�N
            valUploadRoleNo.addValidation(new NullOrEmptyCheckValidation());

            // �p�^�[���`�F�b�N
            valUploadRoleNo.addValidation(new CodeLookupValidation(
                    this.codeLookupManager, "ImageInfoRoleId"));

            valUploadRoleNo.validate(errors);

        } else {
            if (this.getDivNo() != null) {
                for (int i = 0; i < this.getDivNo().length; i++) {

                    // Validate sort_order �\����
                    ValidationChain valEditSortOrder = new ValidationChain(
                            "field.reformImg.SortOrder", getEditSortOrder()[i]);

                    // �K�{���̓`�F�b�N
                    valEditSortOrder.addValidation(new LineAdapter(
                            new NullOrEmptyCheckValidation(), i + 1));

                    // �ő包�� 3��
                    valEditSortOrder.addValidation(new LineAdapter(
                            new MaxLengthValidation(3), i + 1));

                    // ���p����
                    valEditSortOrder.addValidation(new LineAdapter(
                            new NumericValidation(), i + 1));

                    valEditSortOrder.validate(errors);

                    // Validate before_comment before �摜�R�����g
                    ValidationChain valEditBeforeComment = new ValidationChain(
                            "field.reformImg.BeformComment",
                            getEditBeforeComment()[i]);

                    // �ő包�� 50��
                    valEditBeforeComment.addValidation(new LineAdapter(
                            new MaxLengthValidation(50), i + 1));

                    valEditBeforeComment.validate(errors);

                    // Validate before_comment before �摜�R�����g
                    ValidationChain valEditAfterComment = new ValidationChain(
                            "field.reformImg.AfterComment",
                            getEditAfterComment()[i]);

                    // �ő包�� 50��
                    valEditAfterComment.addValidation(new LineAdapter(
                            new MaxLengthValidation(50), i + 1));

                    valEditAfterComment.validate(errors);

                    // Validate role_no �{������
                    ValidationChain valEditRoleNo = new ValidationChain(
                            "field.reformImg.RoleId", getEditRoleId()[i]);

                    // �K�{���̓`�F�b�N
                    valEditRoleNo.addValidation(new LineAdapter(
                            new NullOrEmptyCheckValidation(), i + 1));

                    // �p�^�[���`�F�b�N
                    valEditRoleNo.addValidation(new LineAdapter(
                            new CodeLookupValidation(this.codeLookupManager,
                                    "ImageInfoRoleId"), i + 1));

                    valEditRoleNo.validate(errors);

                    ValidationChain valOldRole_id = new ValidationChain(
                            "field.reformImg.RoleId", getEditOldRoleId()[i]);

                    // �p�^�[���`�F�b�N
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
     * �o�^�����I�u�W�F�N�g���쐬����B<br/>
     * �o�͗p�Ȃ̂Ńy�[�W���������O�����o�^�����𐶐�����B<br/>
     * <br/>
     *
     * @return �o�^�����I�u�W�F�N�g
     */
    public ReformImg newToReformImg() {
        ReformImg reformImg = new ReformImg();

        // �V�X�e�����t�H�[��CD
        reformImg.setSysReformCd(this.getSysReformCd());

        //  before �摜�t�@�C����
        reformImg.setBeforeFileName(this.getUploadBeforeFileName()[0]);
        // �R�����g_Before
        reformImg.setBeforeComment(this.getUploadBeforeComment()[0]);

        //  after �摜�t�@�C����
        reformImg.setAfterFileName(this.getUploadAfterFileName()[0]);
        // �R�����g_After
        reformImg.setAfterComment(this.getUploadAfterComment()[0]);

        // �\����
        reformImg.setSortOrder(Integer.valueOf(this.getUploadSortOrder()[0]));
        // �{������
        reformImg.setRoleId(this.getUploadRoleId()[0]);

        return reformImg;
    }

    /**
     * �����X�V�����I�u�W�F�N�g���쐬����B<br/>
     * �o�͗p�Ȃ̂Ńy�[�W���������O���������X�V�����𐶐�����B<br/>
     * <br/>
     *
     * @return �����X�V�����I�u�W�F�N�g
     */
    public void copyToReformImg(ReformImg reformImg, int idx) {

        // �\����
        reformImg.setSortOrder(Integer.valueOf(this.getEditSortOrder()[idx]));

        // �R�����g_After
        reformImg.setAfterComment(this.getEditAfterComment()[idx]);

        // �R�����g_Before
        reformImg.setBeforeComment(this.getEditBeforeComment()[idx]);

        // �{������
        reformImg.setRoleId(this.getEditRoleId()[idx]);
    }
    /**
     * �n���ꂽ�o���[�I�u�W�F�N�g���� Form �֏����l��ݒ肷��B<br/>
     * <br/>
     * @param adminUser AdminUserInterface�@�����������A���[�U�[���Ǘ��p�o���[�I�u�W�F�N�g
     * @param adminRole AdminUserRoleInterface �������������[�U�[���[���Ǘ��p�o���[�I�u�W�F�N�g
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
            // �}��
            divNo[i] = PanaStringUtils.toString(reformImgresults.get(i).getDivNo());
            // �\����
            sortOrder[i] = PanaStringUtils.toString(reformImgresults.get(i).getSortOrder());
            // before_�摜
            beforePathName[i] = reformImgresults.get(i).getBeforePathName();
            // before_�R�����g
            beforeComment[i] = reformImgresults.get(i).getBeforeComment();
            // after_�摜
            afterPathName[i] = reformImgresults.get(i).getAfterPathName();
            // after_�R�����g
            afterComment[i] = reformImgresults.get(i).getAfterComment();
            // �{������
            roleId[i] = reformImgresults.get(i).getRoleId();
            // before_�摜
            beforeFileName[i] = reformImgresults.get(i).getBeforeFileName();
            // after_�摜
            afterFileName[i] = reformImgresults.get(i).getAfterFileName();
        }
        // �}��
        this.setDivNo(divNo);
        // �\����
        this.setEditSortOrder(sortOrder);
        // before_�R�����g
        this.setEditBeforeComment(beforeComment);
        // after_�R�����g
        this.setEditAfterComment(afterComment);
        // �{������
        this.setEditRoleId(roleId);
        // before_�摜
        this.setEditBeforePathName(beforePathName);
        this.setEditBeforeFileName(beforeFileName);
        // after_�摜
        this.setEditAfterPathName(afterPathName);
        this.setEditAfterFileName(afterFileName);
        this.setEditOldRoleId(roleId);

    }
}
