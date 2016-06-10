package jp.co.transcosmos.dm3.corePana.vo;

/**
 * ���t�H�[���ڍ׏��.
 * <p>
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 * <p>
 * <pre>
 * �S����        �C����        �C�����e
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.03.10  �V�K�쐬
 * H.Mizuno		2015.03.16	�}�Ԃ̃f�[�^�^�� String ���� Integer �֕ύX
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �ʃJ�X�^�}�C�Y�Ōp�������\��������̂ŁA���ڃC���X�^���X�𐶐����Ȃ����B<br/>
 * �C���X�^���X���擾����ꍇ�́AValueObjectFactory ����擾���鎖�B<br/>
 *
 */
public class ReformDtl {

    /** �V�X�e�����t�H�[��CD */
    private String sysReformCd;
    /** �}�� */
    private Integer divNo;
    /** �p�X�� */
    private String pathName;
    /** �t�@�C���� */
    private String fileName;
    /** �\���� */
    private Integer sortOrder;
    /** �摜���� */
    private String imgName;
    /** ���t�H�[�����i */
    private Long reformPrice;
    /** �摜�{������ */
    private String roleId;

    /**
     * �V�X�e�����t�H�[��CD ���擾����B<br/>
     * <br/>
     * @return �V�X�e�����t�H�[��CD
     */
    public String getSysReformCd() {
        return sysReformCd;
    }

    /**
     * �V�X�e�����t�H�[��CD ��ݒ肷��B<br/>
     * <br/>
     * @param sysReformCd
     */
    public void setSysReformCd(String sysReformCd) {
        this.sysReformCd = sysReformCd;
    }

    /**
     * �}�� ���擾����B<br/>
     * <br/>
     * @return �}��
     */
    public Integer getDivNo() {
        return divNo;
    }

    /**
     * �}�� ��ݒ肷��B<br/>
     * <br/>
     * @param divNo
     */
    public void setDivNo(Integer divNo) {
        this.divNo = divNo;
    }

    /**
     * �p�X�� ���擾����B<br/>
     * <br/>
     * @return �p�X��
     */
    public String getPathName() {
        return pathName;
    }

    /**
     * �p�X�� ��ݒ肷��B<br/>
     * <br/>
     * @param pathName
     */
    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    /**
     * �t�@�C���� ���擾����B<br/>
     * <br/>
     * @return �t�@�C����
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * �t�@�C���� ��ݒ肷��B<br/>
     * <br/>
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * �\���� ���擾����B<br/>
     * <br/>
     * @return �\����
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * �\���� ��ݒ肷��B<br/>
     * <br/>
     * @param sortOrder
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * �摜���� ���擾����B<br/>
     * <br/>
     * @return �摜����
     */
    public String getImgName() {
        return imgName;
    }

    /**
     * �摜���� ��ݒ肷��B<br/>
     * <br/>
     * @param imgName
     */
    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    /**
     * ���t�H�[�����i ���擾����B<br/>
     * <br/>
     * @return ���t�H�[�����i
     */
    public Long getReformPrice() {
        return reformPrice;
    }

    /**
     * ���t�H�[�����i ��ݒ肷��B<br/>
     * <br/>
     * @param reformPrice
     */
    public void setReformPrice(Long reformPrice) {
        this.reformPrice = reformPrice;
    }

    /**
     * �摜�{������ ���擾����B<br/>
     * <br/>
     * @return �摜�{������
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * �摜�{������ ��ݒ肷��B<br/>
     * <br/>
     * @param roleId
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
