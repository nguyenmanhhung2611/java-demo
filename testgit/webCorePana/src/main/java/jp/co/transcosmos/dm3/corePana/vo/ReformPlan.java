package jp.co.transcosmos.dm3.corePana.vo;

import java.util.Date;

/**
 * ���t�H�[���v����.
 * <p>
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 * <p>
 * <pre>
 * �S����        �C����        �C�����e
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.03.10     �V�K�쐬
 * Thi Tran     2015.12.18     Update to add categories to reform plan
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �ʃJ�X�^�}�C�Y�Ōp�������\��������̂ŁA���ڃC���X�^���X�𐶐����Ȃ����B<br/>
 * �C���X�^���X���擾����ꍇ�́AValueObjectFactory ����擾���鎖�B<br/>
 *
 */
public class ReformPlan {

    /** �V�X�e�����t�H�[��CD */
    private String sysReformCd;
    /** �V�X�e������CD */
    private String sysHousingCd;
    /** ���t�H�[���v������ */
    private String planName;
    /** ���t�H�[�����i */
    private Long planPrice;
    /** �Z�[���X�|�C���g */
    private String salesPoint;
    /** �H�� */
    private String constructionPeriod;
    /** ���l */
    private String note;
    /** before ����URL */
    private String beforeMovieUrl;
    /** after ����URL */
    private String afterMovieUrl;
    /** ����{������ */
    private String roleId;
    /** ����J�t���O */
    private String hiddenFlg;
    /** ���[�_�[�`���[�g�摜�p�X�� */
    private String reformChartImagePathName;
    /** ���[�_�[�`���[�g�摜�t�@�C���� */
    private String reformChartImageFileName;
    /** �o�^�� */
    private Date insDate;
    /** �o�^�� */
    private String insUserId;
    /** �ŏI�X�V�� */
    private Date updDate;
    /** �ŏI�X�V�� */
    private String updUserId;

    /** Category 1 of reform plan **/
    private String planCategory1;

    /** Category 2 of reform_plan **/
    private String planCategory2;

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
     * �V�X�e������CD ���擾����B<br/>
     * <br/>
     * @return �V�X�e������CD
     */
    public String getSysHousingCd() {
        return sysHousingCd;
    }

    /**
     * �V�X�e������CD ��ݒ肷��B<br/>
     * <br/>
     * @param sysHousingCd
     */
    public void setSysHousingCd(String sysHousingCd) {
        this.sysHousingCd = sysHousingCd;
    }

    /**
     * ���t�H�[���v������ ���擾����B<br/>
     * <br/>
     * @return ���t�H�[���v������
     */
    public String getPlanName() {
        return planName;
    }

    /**
     * ���t�H�[���v������ ��ݒ肷��B<br/>
     * <br/>
     * @param planName
     */
    public void setPlanName(String planName) {
        this.planName = planName;
    }

    /**
     * ���t�H�[�����i ���擾����B<br/>
     * <br/>
     * @return ���t�H�[�����i
     */
    public Long getPlanPrice() {
        return planPrice;
    }

    /**
     * ���t�H�[�����i ��ݒ肷��B<br/>
     * <br/>
     * @param planPrice
     */
    public void setPlanPrice(Long planPrice) {
        this.planPrice = planPrice;
    }

    /**
     * �Z�[���X�|�C���g ���擾����B<br/>
     * <br/>
     * @return �Z�[���X�|�C���g
     */
    public String getSalesPoint() {
        return salesPoint;
    }

    /**
     * �Z�[���X�|�C���g ��ݒ肷��B<br/>
     * <br/>
     * @param salesPoint
     */
    public void setSalesPoint(String salesPoint) {
        this.salesPoint = salesPoint;
    }

    /**
     * �H�� ���擾����B<br/>
     * <br/>
     * @return �H��
     */
    public String getConstructionPeriod() {
        return constructionPeriod;
    }

    /**
     * �H�� ��ݒ肷��B<br/>
     * <br/>
     * @param constructionPeriod
     */
    public void setConstructionPeriod(String constructionPeriod) {
        this.constructionPeriod = constructionPeriod;
    }

    /**
     * ���l ���擾����B<br/>
     * <br/>
     * @return ���l
     */
    public String getNote() {
        return note;
    }

    /**
     * ���l ��ݒ肷��B<br/>
     * <br/>
     * @param note
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * before ����URL ���擾����B<br/>
     * <br/>
     * @return before ����URL
     */
    public String getBeforeMovieUrl() {
        return beforeMovieUrl;
    }

    /**
     * before ����URL ��ݒ肷��B<br/>
     * <br/>
     * @param beforeMovieUrl
     */
    public void setBeforeMovieUrl(String beforeMovieUrl) {
        this.beforeMovieUrl = beforeMovieUrl;
    }

    /**
     * after ����URL ���擾����B<br/>
     * <br/>
     * @return after ����URL
     */
    public String getAfterMovieUrl() {
        return afterMovieUrl;
    }

    /**
     * after ����URL ��ݒ肷��B<br/>
     * <br/>
     * @param afterMovieUrl
     */
    public void setAfterMovieUrl(String afterMovieUrl) {
        this.afterMovieUrl = afterMovieUrl;
    }

    /**
     * ����{������ ���擾����B<br/>
     * <br/>
     * @return ����{������
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * ����{������ ��ݒ肷��B<br/>
     * <br/>
     * @param roleId
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * ����J�t���O ���擾����B<br/>
     * <br/>
     * @return ����J�t���O
     */
    public String getHiddenFlg() {
        return hiddenFlg;
    }

    /**
     * ����J�t���O ��ݒ肷��B<br/>
     * <br/>
     * @param hiddenFlg
     */
    public void setHiddenFlg(String hiddenFlg) {
        this.hiddenFlg = hiddenFlg;
    }

    /**
     * ���[�_�[�`���[�g�摜�p�X�� ���擾����B<br/>
     * <br/>
     * @return ���[�_�[�`���[�g�摜�p�X��
     */
    public String getReformChartImagePathName() {
        return reformChartImagePathName;
    }

    /**
     * ���[�_�[�`���[�g�摜�p�X�� ��ݒ肷��B<br/>
     * <br/>
     * @param reformChartImagePathName
     */
    public void setReformChartImagePathName(String reformChartImagePathName) {
        this.reformChartImagePathName = reformChartImagePathName;
    }

    /**
     * ���[�_�[�`���[�g�摜�t�@�C���� ���擾����B<br/>
     * <br/>
     * @return ���[�_�[�`���[�g�摜�t�@�C����
     */
    public String getReformChartImageFileName() {
        return reformChartImageFileName;
    }

    /**
     * ���[�_�[�`���[�g�摜�t�@�C���� ��ݒ肷��B<br/>
     * <br/>
     * @param reformChartImageFileName
     */
    public void setReformChartImageFileName(String reformChartImageFileName) {
        this.reformChartImageFileName = reformChartImageFileName;
    }

    /**
     * �o�^�� ���擾����B<br/>
     * <br/>
     * @return �o�^��
     */
    public Date getInsDate() {
        return insDate;
    }

    /**
     * �o�^�� ��ݒ肷��B<br/>
     * <br/>
     * @param insDate
     */
    public void setInsDate(Date insDate) {
        this.insDate = insDate;
    }

    /**
     * �o�^�� ���擾����B<br/>
     * <br/>
     * @return �o�^��
     */
    public String getInsUserId() {
        return insUserId;
    }

    /**
     * �o�^�� ��ݒ肷��B<br/>
     * <br/>
     * @param insUserId
     */
    public void setInsUserId(String insUserId) {
        this.insUserId = insUserId;
    }

    /**
     * �ŏI�X�V�� ���擾����B<br/>
     * <br/>
     * @return �ŏI�X�V��
     */
    public Date getUpdDate() {
        return updDate;
    }

    /**
     * �ŏI�X�V�� ��ݒ肷��B<br/>
     * <br/>
     * @param updDate
     */
    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }

    /**
     * �ŏI�X�V�� ���擾����B<br/>
     * <br/>
     * @return �ŏI�X�V��
     */
    public String getUpdUserId() {
        return updUserId;
    }

    /**
     * �ŏI�X�V�� ��ݒ肷��B<br/>
     * <br/>
     * @param updUserId
     */
    public void setUpdUserId(String updUserId) {
        this.updUserId = updUserId;
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
}
