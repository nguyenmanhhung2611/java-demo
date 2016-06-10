package jp.co.transcosmos.dm3.corePana.vo;

/**
 * ���t�H�[���摜���.
 * <p>
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 * <p>
 * <pre>
 * �S����        �C����        �C�����e
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.03.10  �V�K�쐬
 * H.Mizuno		2015.03.16	�}�Ԃ� String �^���� Integer �֕ύX
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �ʃJ�X�^�}�C�Y�Ōp�������\��������̂ŁA���ڃC���X�^���X�𐶐����Ȃ����B<br/>
 * �C���X�^���X���擾����ꍇ�́AValueObjectFactory ����擾���鎖�B<br/>
 *
 */
public class ReformImg {

    /** �V�X�e�����t�H�[��CD */
    private String sysReformCd;
    /** �}�� */
    private Integer divNo;
    /** before �摜�p�X�� */
    private String beforePathName;
    /** before �摜�t�@�C���� */
    private String beforeFileName;
    /** before �摜�R�����g */
    private String beforeComment;
    /** after �摜�p�X�� */
    private String afterPathName;
    /** after �摜�t�@�C���� */
    private String afterFileName;
    /** after �摜�R�����g */
    private String afterComment;
    /** �\���� */
    private Integer sortOrder;
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
     * before �摜�p�X�� ���擾����B<br/>
     * <br/>
     * @return before �摜�p�X��
     */
    public String getBeforePathName() {
        return beforePathName;
    }

    /**
     * before �摜�p�X�� ��ݒ肷��B<br/>
     * <br/>
     * @param beforePathName
     */
    public void setBeforePathName(String beforePathName) {
        this.beforePathName = beforePathName;
    }

    /**
     * before �摜�t�@�C���� ���擾����B<br/>
     * <br/>
     * @return before �摜�t�@�C����
     */
    public String getBeforeFileName() {
        return beforeFileName;
    }

    /**
     * before �摜�t�@�C���� ��ݒ肷��B<br/>
     * <br/>
     * @param beforeFileName
     */
    public void setBeforeFileName(String beforeFileName) {
        this.beforeFileName = beforeFileName;
    }

    /**
     * before �摜�R�����g ���擾����B<br/>
     * <br/>
     * @return before �摜�R�����g
     */
    public String getBeforeComment() {
        return beforeComment;
    }

    /**
     * before �摜�R�����g ��ݒ肷��B<br/>
     * <br/>
     * @param beforeComment
     */
    public void setBeforeComment(String beforeComment) {
        this.beforeComment = beforeComment;
    }

    /**
     * after �摜�p�X�� ���擾����B<br/>
     * <br/>
     * @return after �摜�p�X��
     */
    public String getAfterPathName() {
        return afterPathName;
    }

    /**
     * after �摜�p�X�� ��ݒ肷��B<br/>
     * <br/>
     * @param afterPathName
     */
    public void setAfterPathName(String afterPathName) {
        this.afterPathName = afterPathName;
    }

    /**
     * after �摜�t�@�C���� ���擾����B<br/>
     * <br/>
     * @return after �摜�t�@�C����
     */
    public String getAfterFileName() {
        return afterFileName;
    }

    /**
     * after �摜�t�@�C���� ��ݒ肷��B<br/>
     * <br/>
     * @param afterFileName
     */
    public void setAfterFileName(String afterFileName) {
        this.afterFileName = afterFileName;
    }

    /**
     * after �摜�R�����g ���擾����B<br/>
     * <br/>
     * @return after �摜�R�����g
     */
    public String getAfterComment() {
        return afterComment;
    }

    /**
     * after �摜�R�����g ��ݒ肷��B<br/>
     * <br/>
     * @param afterComment
     */
    public void setAfterComment(String afterComment) {
        this.afterComment = afterComment;
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
