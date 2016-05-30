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
 * ���t�H�[���摜�̓o�^�ƍX�V���A����p�t�H�[��.
 * <p>
 *
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * fan			2015.04.23	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���� Form �̃o���f�[�V�����́A�g�p���郂�[�h�i�ǉ����� or �X�V�����j�ňقȂ�̂ŁA �t���[�����[�N���񋟂��� Validateable
 * �C���^�[�t�F�[�X�͎������Ă��Ȃ��B<br/>
 * �o���f�[�V�������s���̃p�����[�^���ʏ�ƈقȂ�̂Œ��ӂ��鎖�B<br/>
 *
 */
public class PanaHousingInspectionForm implements Validateable {

    /** ���ʃR�[�h�ϊ����� */
    private CodeLookupManager codeLookupManager;

    /**
     * �f�t�H���g�R���X�g���N�^�[<br/>
     * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
     * <br/>
     */
    PanaHousingInspectionForm() {
        super();
    }

    /**
     * �R���X�g���N�^�[<br/>
     * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
     * <br/>
     * @param�@codeLookupManager�@���ʃR�[�h�ϊ�����
     */
    PanaHousingInspectionForm(CodeLookupManager codeLookupManager) {
        super();
        this.codeLookupManager = codeLookupManager;
    }

    /**
     * �V�X�e������CD
     */
    private String sysHousingCd;

    /**
     * �R�����g
     */
    private String command;

    /**
     * �����ԍ�
     */
    private String housingCd;

    /**
     * �폜�̃t���O
     */
    private String housingDel;
    /**
     * �폜�̃t���O
     */
    private String housingImgDel;

    /**
     * �\���p������
     */
    private String displayHousingName;

    /**
     * �Z��f�f���{�L��
     */
    private String housingInspection;

    /**
     * �Z��f�f�t�@�C��
     */
    private FileItem housingFile;

    /**
     * �摜�t�@�C��
     */
    private FileItem housingImgFile;

    /**
     * wk�Z��f�f�t�@�C���L���t���O
     */
    private String loadFlg;

    /**
     * wk�摜�t�@�C���L���t���O
     */
    private String imgFlg;

    /**
     * �������CD
     */
    private String housingKindCd;

    /**
     * ���[�h�ς݃t�@�C��
     */
    private String loadFile;

    /**
     * �摜�t�@�C��
     */
    private String imgFile;

    /**
     * ���[�h�ς݃t�@�C��
     */
    private String loadFilePath;

    /**
     * �摜�t�@�C��
     */
    private String imgFilePath;

    /** �t�@�C����(�ǉ��p�j */
    private String addHidFileName;

    /** �t�@�C����(�ǉ��p�j */
    private String addHidImgName;

    /**
     * �]����̒�`
     */
    private String[] inspectionKey;

    /**
     * �]����̑I������
     */
    private String[] inspectionTrust_result;

    /**
     * �m�F�͈͂̑I������
     */
    private String[] inspectionValue_label;

    /** �t�@�C�������p�X(�ǉ��p�j */
    private String hidPath;
    /** �t�@�C����(�ǉ��p�j */
    private String hidImgPath;

    /** �t�@�C�������p�X(�ǉ��p�j */
    private String hidNewPath;
    /** �t�@�C����(�ǉ��p�j */
    private String hidNewImgPath;

	/**
     * �t�@�C����(�ǉ��p�j �p�����[�^���擾����B<br/>
     * �t�@�C����(�ǉ��p�j �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �t�@�C����(�ǉ��p�j�N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �t�@�C����(�ǉ��p�j�p�����[�^
     */
	public String getAddHidFileName() {
		return addHidFileName;
	}

	/**
     * �t�@�C����(�ǉ��p�j�p�����[�^��ݒ肷��B<br/>
     * �t�@�C����(�ǉ��p�j�p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �t�@�C����(�ǉ��p�j�N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param addHidFileName
     *            �t�@�C����(�ǉ��p�j�p�����[�^
     */
	public void setAddHidFileName(String addHidFileName) {
		this.addHidFileName = addHidFileName;
	}

	/**
     * �t�@�C����(�ǉ��p�j �p�����[�^���擾����B<br/>
     * �t�@�C����(�ǉ��p�j �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �t�@�C����(�ǉ��p�j�N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �t�@�C����(�ǉ��p�j�p�����[�^
     */
	public String getAddHidImgName() {
		return addHidImgName;
	}

	/**
     * �t�@�C����(�ǉ��p�j�p�����[�^��ݒ肷��B<br/>
     * �t�@�C����(�ǉ��p�j�p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �t�@�C����(�ǉ��p�j�N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param addHidImgName
     *            �t�@�C����(�ǉ��p�j�p�����[�^
     */
	public void setAddHidImgName(String addHidImgName) {
		this.addHidImgName = addHidImgName;
	}

	/**
     * �폜�̃t���O �p�����[�^���擾����B<br/>
     * �폜�̃t���O �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �폜�̃t���O �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �폜�̃t���O �p�����[�^
     */
	public String getHousingImgDel() {
		return housingImgDel;
	}

	/**
     * �폜�̃t���O �p�����[�^��ݒ肷��B<br/>
     * �폜�̃t���O �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �폜�̃t���O �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param housingImgDel
     *            �폜�̃t���O �p�����[�^
     */
	public void setHousingImgDel(String housingImgDel) {
		this.housingImgDel = housingImgDel;
	}

	/**
     * ���[�h�ς݃t�@�C�� �p�����[�^���擾����B<br/>
     * ���[�h�ς݃t�@�C�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���郍�[�h�ς݃t�@�C�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return ���[�h�ς݃t�@�C�� �p�����[�^
     */
	public String getLoadFilePath() {
		return loadFilePath;
	}

	/**
     * ���[�h�ς݃t�@�C�� �p�����[�^��ݒ肷��B<br/>
     * ���[�h�ς݃t�@�C�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���[�h�ς݃t�@�C�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param loadFilePath
     *           ���[�h�ς݃t�@�C�� �p�����[�^
     */
	public void setLoadFilePath(String loadFilePath) {
		this.loadFilePath = loadFilePath;
	}

	/**
     * ���[�h�ς݃t�@�C�� �p�����[�^���擾����B<br/>
     * ���[�h�ς݃t�@�C�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���郍�[�h�ς݃t�@�C�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return ���[�h�ς݃t�@�C�� �p�����[�^
     */
	public String getImgFilePath() {
		return imgFilePath;
	}

	/**
     * ���[�h�ς݃t�@�C�� �p�����[�^��ݒ肷��B<br/>
     * ���[�h�ς݃t�@�C�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���[�h�ς݃t�@�C�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param imgFilePath
     *           ���[�h�ς݃t�@�C�� �p�����[�^
     */
	public void setImgFilePath(String imgFilePath) {
		this.imgFilePath = imgFilePath;
	}

	/**
     * �t�@�C�������p�X(�ǉ��p�j�p�����[�^���擾����B<br/>
     * �t�@�C�������p�X(�ǉ��p�j�p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �t�@�C�������p�X(�ǉ��p�j�N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �t�@�C����(�ǉ��p�j�p�����[�^
     */
	public String getHidPath() {
		return hidPath;
	}

	/**
     * �t�@�C�������p�X(�ǉ��p�j�p�����[�^��ݒ肷��B<br/>
     * �t�@�C�������p�X(�ǉ��p�j�p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �t�@�C�������p�X(�ǉ��p�j�N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param hidImgPath
     *            �t�@�C�������p�X(�ǉ��p�j�p�����[�^
     */
	public void setHidPath(String hidPath) {
		this.hidPath = hidPath;
	}
	/**
     * �t�@�C�������p�X(�ǉ��p�j �p�����[�^���擾����B<br/>
     * �t�@�C�������p�X(�ǉ��p�j�p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �t�@�C�������p�X(�ǉ��p�j�N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �t�@�C�������p�X(�ǉ��p�j�p�����[�^
     */
	public String getHidImgPath() {
		return hidImgPath;
	}

	/**
     * �t�@�C�������p�X(�ǉ��p�j�p�����[�^��ݒ肷��B<br/>
     * �t�@�C�������p�X(�ǉ��p�j�p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �t�@�C�������p�X(�ǉ��p�j�N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param hidImgPath
     *            �t�@�C����(�ǉ��p�j�p�����[�^
     */
	public void setHidImgPath(String hidImgPath) {
		this.hidImgPath = hidImgPath;
	}



	/**
     * �t�@�C�������p�X(�ǉ��p�j�p�����[�^���擾����B<br/>
     * �t�@�C�������p�X(�ǉ��p�j�p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �t�@�C�������p�X(�ǉ��p�j�N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �t�@�C����(�ǉ��p�j�p�����[�^
     */
	public String getHidNewPath() {
		return hidNewPath;
	}

	/**
     * �t�@�C�������p�X(�ǉ��p�j�p�����[�^��ݒ肷��B<br/>
     * �t�@�C�������p�X(�ǉ��p�j�p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �t�@�C�������p�X(�ǉ��p�j�N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param hidImgPath
     *            �t�@�C�������p�X(�ǉ��p�j�p�����[�^
     */
	public void setHidNewPath(String hidNewPath) {
		this.hidNewPath = hidNewPath;
	}
	/**
     * �t�@�C�������p�X(�ǉ��p�j �p�����[�^���擾����B<br/>
     * �t�@�C�������p�X(�ǉ��p�j�p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �t�@�C�������p�X(�ǉ��p�j�N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �t�@�C�������p�X(�ǉ��p�j�p�����[�^
     */
	public String getHidNewImgPath() {
		return hidNewImgPath;
	}

	/**
     * �t�@�C�������p�X(�ǉ��p�j�p�����[�^��ݒ肷��B<br/>
     * �t�@�C�������p�X(�ǉ��p�j�p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �t�@�C�������p�X(�ǉ��p�j�N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param hidImgPath
     *            �t�@�C����(�ǉ��p�j�p�����[�^
     */
	public void setHidNewImgPath(String hidNewImgPath) {
		this.hidNewImgPath = hidNewImgPath;
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
     * �V�X�e������CD �p�����[�^��ݒ肷��B<br/>
     * �V�X�e������CD �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �V�X�e������CD �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param sysHousingCd
     *            �V�X�e������CD �p�����[�^
     */
    public void setSysHousingCd(String sysHousingCd) {
        this.sysHousingCd = sysHousingCd;
    }

    /**
     * �R�����g �p�����[�^���擾����B<br/>
     * �R�����g �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �R�����g �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �R�����g �p�����[�^
     */
    public String getCommand() {
        return command;
    }

    /**
     * �R�����g �p�����[�^��ݒ肷��B<br/>
     * �R�����g �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �R�����g �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param command
     *            �R�����g �p�����[�^
     */
    public void setCommand(String command) {
        this.command = command;
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
     * @param command
     *            �����ԍ� �p�����[�^
     */
    public void setHousingCd(String housingCd) {
        this.housingCd = housingCd;
    }

    /**
     * �폜�̃t���O �p�����[�^���擾����B<br/>
     * �폜�̃t���O �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p�폜�̃t���O�� �폜�̃t���O �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �폜�̃t���O �p�����[�^
     */
    public String getHousingDel() {
        return housingDel;
    }

    /**
     * �폜�̃t���O �p�����[�^��ݒ肷��B<br/>
     * �폜�̃t���O �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �폜�̃t���O �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param command
     *            �폜�̃t���O �p�����[�^
     */
    public void setHousingDel(String housingDel) {
        this.housingDel = housingDel;
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
     * �\���p������ �p�����[�^��ݒ肷��B<br/>
     * �\���p������ �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �\���p������ �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param command
     *            �\���p������ �p�����[�^
     */
    public void setDisplayHousingName(String displayHousingName) {
        this.displayHousingName = displayHousingName;
    }

    /**
     * �Z��f�f���{�L�� �p�����[�^���擾����B<br/>
     * �Z��f�f���{�L�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �Z��f�f���{�L�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �Z��f�f���{�L�� �p�����[�^
     */
    public String getHousingInspection() {
        return housingInspection;
    }

    /**
     * �Z��f�f���{�L�� �p�����[�^��ݒ肷��B<br/>
     * �Z��f�f���{�L�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �Z��f�f���{�L�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param command
     *            �Z��f�f���{�L�� �p�����[�^
     */
    public void setHousingInspection(String housingInspection) {
        this.housingInspection = housingInspection;
    }

    /**
     * �Z��f�f�t�@�C�� �p�����[�^���擾����B<br/>
     * �Z��f�f�t�@�C�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �Z��f�f�t�@�C�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �Z��f�f�t�@�C�� �p�����[�^
     */
    public FileItem getHousingFile() {
        return housingFile;
    }

    /**
     * �Z��f�f�t�@�C�� �p�����[�^��ݒ肷��B<br/>
     * �Z��f�f�t�@�C�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �Z��f�f�t�@�C�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param command
     *            �Z��f�f�t�@�C�� �p�����[�^
     */
    public void setHousingFile(FileItem housingFile) {
        this.housingFile = housingFile;
    }

    /**
     * �摜�t�@�C�� �p�����[�^���擾����B<br/>
     * �摜�t�@�C�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �摜�t�@�C�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �摜�t�@�C�� �p�����[�^
     */
    public FileItem getHousingImgFile() {
        return housingImgFile;
    }

    /**
     * �摜�t�@�C�� �p�����[�^��ݒ肷��B<br/>
     * �摜�t�@�C�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �摜�t�@�C�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param command
     *            �摜�t�@�C�� �p�����[�^
     */
    public void setHousingImgFile(FileItem housingImgFile) {
        this.housingImgFile = housingImgFile;
    }

    /**
     * wk�Z��f�f�t�@�C���L���t���O �p�����[�^���擾����B<br/>
     * wk�Z��f�f�t�@�C���L���t���O �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� wk�Z��f�f�t�@�C���L���t���O
     * �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return wk�Z��f�f�t�@�C���L���t���O �p�����[�^
     */
    public String getLoadFlg() {
        return loadFlg;
    }

    /**
     * wk�Z��f�f�t�@�C���L���t���O �p�����[�^��ݒ肷��B<br/>
     * wk�Z��f�f�t�@�C���L���t���O �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� wk�Z��f�f�t�@�C���L���t���O
     * �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param command
     *            wk�Z��f�f�t�@�C���L���t���O �p�����[�^
     */
    public void setLoadFlg(String loadFlg) {
        this.loadFlg = loadFlg;
    }

    /**
     * wk�摜�t�@�C���L���t���O �p�����[�^���擾����B<br/>
     * wk�摜�t�@�C���L���t���O �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� wk�摜�t�@�C���L���t���O
     * �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return wk�摜�t�@�C���L���t���O �p�����[�^
     */
    public String getImgFlg() {
        return imgFlg;
    }

    /**
     * wk�摜�t�@�C���L���t���O �p�����[�^��ݒ肷��B<br/>
     * wk�摜�t�@�C���L���t���O �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� wk�摜�t�@�C���L���t���O
     * �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param command
     *            wk�摜�t�@�C���L���t���O �p�����[�^
     */
    public void setImgFlg(String imgFlg) {
        this.imgFlg = imgFlg;
    }

    /**
     * �������CD �p�����[�^���擾����B<br/>
     * �������CD �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �������CD �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �������CD �p�����[�^
     */
    public String getHousingKindCd() {
        return housingKindCd;
    }

    /**
     * �������CD �p�����[�^��ݒ肷��B<br/>
     * �������CD �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �������CD �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param command
     *            �������CD �p�����[�^
     */
    public void setHousingKindCd(String housingKindCd) {
        this.housingKindCd = housingKindCd;
    }

    /**
     * ���[�h�ς݃t�@�C�� �p�����[�^���擾����B<br/>
     * ���[�h�ς݃t�@�C�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���[�h�ς݃t�@�C�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return ���[�h�ς݃t�@�C�� �p�����[�^
     */
    public String getLoadFile() {
        return loadFile;
    }

    /**
     * ���[�h�ς݃t�@�C�� �p�����[�^��ݒ肷��B<br/>
     * ���[�h�ς݃t�@�C�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���[�h�ς݃t�@�C�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param command
     *            ���[�h�ς݃t�@�C�� �p�����[�^
     */
    public void setLoadFile(String loadFile) {
        this.loadFile = loadFile;
    }

    /**
     * �摜�t�@�C�� �p�����[�^���擾����B<br/>
     * �摜�t�@�C�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �摜�t�@�C�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �摜�t�@�C�� �p�����[�^
     */
    public String getImgFile() {
        return imgFile;
    }

    /**
     * �摜�t�@�C�� �p�����[�^��ݒ肷��B<br/>
     * �摜�t�@�C�� �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �摜�t�@�C�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param command
     *            �摜�t�@�C�� �p�����[�^
     */
    public void setImgFile(String imgFile) {
        this.imgFile = imgFile;
    }

    /**
     * �]����̒�` �p�����[�^���擾����B<br/>
     * �]����̒�` �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �]����̒�` �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �]����̒�` �p�����[�^
     */
    public String[] getInspectionKey() {
        return inspectionKey;
    }

    /**
     * �]����̒�` �p�����[�^��ݒ肷��B<br/>
     * �]����̒�` �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �]����̒�` �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param command
     *            �]����̒�` �p�����[�^
     */
    public void setInspectionKey(String[] inspectionKey) {
        this.inspectionKey = inspectionKey;
    }

    /**
     * �]����̑I������ �p�����[�^���擾����B<br/>
     * �]����̑I������ �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �]����̑I������ �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �]����̑I������ �p�����[�^
     */
    public String[] getInspectionTrust_result() {
        return inspectionTrust_result;
    }

    /**
     * �]����̑I������ �p�����[�^��ݒ肷��B<br/>
     * �]����̑I������ �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �]����̑I������ �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param command
     *            �]����̑I������ �p�����[�^
     */
    public void setInspectionTrust_result(String[] inspectionTrust_result) {
        this.inspectionTrust_result = inspectionTrust_result;
    }

    /**
     * �]����̑I������ �p�����[�^���擾����B<br/>
     * �]����̑I������ �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �]����̑I������ �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �]����̑I������ �p�����[�^
     */
    public String[] getInspectionValue_label() {
        return inspectionValue_label;
    }

    /**
     * �]����̑I������ �p�����[�^��ݒ肷��B<br/>
     * �]����̑I������ �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �]����̑I������ �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @param command
     *            �]����̑I������ �p�����[�^
     */
    public void setInspectionValue_label(String[] inspectionValue_label) {
        this.inspectionValue_label = inspectionValue_label;
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
    public void copyToHousingInspection(HousingInspection housingInspection,
            int i) {
        // �V�X�e������CD
        housingInspection.setSysHousingCd(setString(getSysHousingCd(),
                housingInspection.getSysHousingCd()));

        // �X�e�[�^�XCD
        housingInspection.setInspectionKey(setString(getInspectionKey()[i],
                housingInspection.getInspectionKey()));
        // ���[�U�[ID
        housingInspection.setInspectionTrust(Integer.valueOf(setString(
                getInspectionTrust_result()[i],
                String.valueOf(housingInspection.getInspectionTrust()))));
        // ���l
        housingInspection.setInspectionValue(Integer.valueOf(setString(
                getInspectionValue_label()[i],
                String.valueOf(housingInspection.getInspectionValue()))));
    }

    // String�^�̒l�ݒ�
    private String setString(String input, String nullValue) {
        if (input == null || "".equals(input)) {
            return nullValue;
        }

        return input;
    }

    /**
     * �o���f�[�V��������<br/>
     * ���N�G�X�g�p�����[�^�̃o���f�[�V�������s��<br/>
     * <br/>
     *
     * @param errors
     *            �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     * @return ���펞 true�A�G���[�� false
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
                    		codeLookupValue+"�@"+"�]���",strInspectionValue_label);
                    // �p�^�[���`�F�b�N
                    valInspectionValue_label
                            .addValidation(new CodeLookupValidation(
                                    this.codeLookupManager, "inspectionResult"));
                    valInspectionValue_label.validate(errors);
                }

                if(!StringValidateUtil.isEmpty(strInspectionTrust_result)){
                	 ValidationChain valInspectionTrust_result = new ValidationChain(
                			 codeLookupValue+"�@"+"�m�F�͈�",strInspectionTrust_result);
                     // �p�^�[���`�F�b�N
                     valInspectionTrust_result
                             .addValidation(new CodeLookupValidation(
                                     this.codeLookupManager,"inspectionLabel"));
                     valInspectionTrust_result.validate(errors);
                }

                if(PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())){
                	if(!StringValidateUtil.isEmpty(this.getInspectionKey()[i])){
                    	ValidationChain valInspectionKey = new ValidationChain(
                                "field.housingInspection.Key",this.getInspectionKey()[i]);
                        // �p�^�[���`�F�b�N
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
                        // �p�^�[���`�F�b�N
                    	valInspectionKey
                                .addValidation(new CodeLookupValidation(
                                        this.codeLookupManager, "inspectionTrustHouse"));
                    	valInspectionKey.validate(errors);
                    }
                }

                // �Z��f�f���{�L�����u�L�v���@�]������͊m�F�͈͂����I��
                if(count == 0){
	                if ("01".equals(this.getHousingInspection())) {
	        			if(StringValidateUtil.isEmpty(strInspectionValue_label) || StringValidateUtil.isEmpty(strInspectionTrust_result)){
	        				ValidationFailure vf = new ValidationFailure(
	                                "inspectionValue", "�Z��f�f���{�L��", "�]������͊m�F�͈�", null);
	                        errors.add(vf);
	                        count++;
	        			}
	                }
                }

                // �]�����I�������A�Ή�����m�F�͈͂𖢑I��
                if ((!StringValidateUtil.isEmpty(strInspectionValue_label) && StringValidateUtil.isEmpty(strInspectionTrust_result))) {
                		String param[] = new String[] { codeLookupValue+"�@" };
                	 ValidationFailure vf = new ValidationFailure(
                             "eachExits", "�]���", "�m�F�͈�", param);
                     errors.add(vf);
        		} else if((StringValidateUtil.isEmpty(strInspectionValue_label) && !StringValidateUtil.isEmpty(strInspectionTrust_result))){
        			String param[] = new String[] { codeLookupValue+"�@" };
        			ValidationFailure vf = new ValidationFailure(
                            "eachExits", "�m�F�͈�", "�]���", param);
                    errors.add(vf);
        		}
            }

            // �Z��f�f���{�L��
            ValidationChain valInspectionTrust_result = new ValidationChain(
                    "field.housingInspection",this.getHousingInspection());
            // �p�^�[���`�F�b�N
            valInspectionTrust_result
                    .addValidation(new CodeLookupValidation(
                            this.codeLookupManager,"housingInspection"));
            valInspectionTrust_result.validate(errors);

            // �֘A�`�F�b�N�F�Z��f�f���{�L�����u�L�v���@���[�_�[�`���[�g�摜���Ȃ��̏ꍇ
            if ("01".equals(this.getHousingInspection())) {
            	if (("on".equals(this.getHousingImgDel()) && !"1".equals(this.getImgFlg())) || (StringValidateUtil.isEmpty(this.getImgFile()) && !"1".equals(this.getImgFlg()))) {
    				ValidationFailure vf = new ValidationFailure(
                            "housingInfoImg", "�Z��f�f���{�L��", "�A�b�v���[�h�摜", null);
                    errors.add(vf);
    			}
            }

            if(this.getHousingFile()!=null){
            	// Validate path_name �摜
            	ValidationChain valPathName = new ValidationChain(
                      "�Z��f�f�t�@�C��", getHousingFile().getSize());
            	// PDF�`�F�b�N
                valPathName.addValidation(new ReformImgPathJpgValidation(getHousingFile().getName(),"pdf"));
            	// �t�@�C���T�C�Y�`�F�b�N
            	valPathName.addValidation(new ReformImgSizeValidation(PanaCommonConstant.updFileSize));
            	valPathName.validate(errors);
            }
            if(this.getHousingImgFile()!=null){
            	// Validate path_name �摜
            	ValidationChain valPathName = new ValidationChain(
                      "���[�_�[�`���[�g�摜", getHousingImgFile().getSize());
            	// jpg�`�F�b�N
                valPathName.addValidation(new ReformImgPathJpgValidation(getHousingImgFile().getName(),"jpg"));
            	// �t�@�C���T�C�Y�`�F�b�N
            	valPathName.addValidation(new ReformImgSizeValidation(PanaCommonConstant.updFileSize));
            	valPathName.validate(errors);
            }
        }

        return startSize == errors.size();
    }

    /**
     * �n���ꂽ�o���[�I�u�W�F�N�g���� Form �֏����l��ݒ肷��B<br/>
     * <br/>
     * @param adminUser AdminUserInterface�@�����������A���[�U�[���Ǘ��p�o���[�I�u�W�F�N�g
     * @param adminRole AdminUserRoleInterface �������������[�U�[���[���Ǘ��p�o���[�I�u�W�F�N�g
     */
    public void setDefaultData(JoinResult housingInfo,JoinResult buildingInfo,List<HousingInspection> HousingInspection, Map<String,String> housingExtInfo) {
    	if(housingInfo != null){
        	//��������
    		this.setDisplayHousingName(((HousingInfo)housingInfo.getItems().get("housingInfo")).getDisplayHousingName());
			//�����ԍ�
    		this.setHousingCd(((HousingInfo)housingInfo.getItems().get("housingInfo")).getHousingCd());
        }
        if(buildingInfo != null){
			//�������
        	this.setHousingKindCd(((BuildingInfo)buildingInfo.getItems().get("buildingInfo")).getHousingKindCd());
        }
    	if(HousingInspection.size()>0){
			String[] inspectionKey = new  String[HousingInspection.size()];
			String[] inspectionTrust_result = new  String[HousingInspection.size()];
			String[] inspectionValue_label = new  String[HousingInspection.size()];
			for (int i = 0; i < HousingInspection.size(); i++) {
				// �]����A�m�F�͈͂̒�`
				inspectionKey[i]=HousingInspection.get(i).getInspectionKey();
				// �]����̑I�����ڂ̒�`
				inspectionTrust_result[i]=String.valueOf(HousingInspection.get(i).getInspectionTrust());
				// �m�F�͈͂̑I�����ڂ̒�`
				inspectionValue_label[i]=String.valueOf(HousingInspection.get(i).getInspectionValue());
			}

			// �]����A�m�F�͈͂̒�`
			this.setInspectionKey(inspectionKey);
			// �]����̑I�����ڂ̒�`
			this.setInspectionTrust_result(inspectionTrust_result);
			// �m�F�͈͂̑I�����ڂ̒�`
			this.setInspectionValue_label(inspectionValue_label);
		}

		// �Z��f�f�t�@�C���̕����g���������̎擾
		// �f�[�^�̑��݂����ꍇ�A
		if (housingExtInfo != null) {

			// �p�����[�^�Fwk���[�h�ς݃t�@�C��(loadFile)�@=�@�����g���������.�l
			String exist =  housingExtInfo.get("inspectionExist");
			String fileName = housingExtInfo.get("inspectionFileName");
			String imageFileName = housingExtInfo.get("inspectionImageFileName");
			String pathName = housingExtInfo.get("inspectionPathName");
			String imagePathName = housingExtInfo.get("inspectionImagePathName");

			// �f�[�^�̑��݂��Ȃ��ꍇ�Awk�Z��f�f���{�L���@=�@"�L"
			if(!StringValidateUtil.isEmpty(exist)){
				this.setHousingInspection(exist);
			}else{
				this.setHousingInspection("02");
			}

			// wk���[�h�ςݏZ��f�f�t�@�C��
			this.setLoadFile(fileName);

			// wk���[�h�ς݉摜�t�@�C��
			this.setImgFile(imageFileName);

			// wk���[�h�ςݏZ��f�f�t�@�C���p�X
			this.setLoadFilePath(pathName);

			// wk���[�h�ς݉摜�t�@�C���p�X
			this.setImgFilePath(imagePathName);
		}
		// �f�[�^�̑��݂��Ȃ��ꍇ�A
		else {
			// �f�[�^�̑��݂����ꍇ�Awk�Z��f�f���{�L���@=�@"��"
			this.setHousingInspection("01");
		}
    }
}
