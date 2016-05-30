package jp.co.transcosmos.dm3.corePana.vo;

import java.util.Date;

/**
 * �ŋߌ�������.
 * <p>
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 * <p>
 * <pre>
 * �S����        �C����        �C�����e
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.03.10     �V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �ʃJ�X�^�}�C�Y�Ōp�������\��������̂ŁA���ڃC���X�^���X�𐶐����Ȃ����B<br/>
 * �C���X�^���X���擾����ꍇ�́AValueObjectFactory ����擾���鎖�B<br/>
 *
 */
public class RecentlyInfo {

    /** �V�X�e������CD */
    private String sysHousingCd;
    /** ���[�U�[ID */
    private String userId;
    /** �o�^�� */
    private Date insDate;
    /** �o�^�� */
    private String insUserId;
    /** �ŏI�X�V�� */
    private Date updDate;
    /** �ŏI�X�V�� */
    private String updUserId;

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
     * ���[�U�[ID ���擾����B<br/>
     * <br/>
     * @return ���[�U�[ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * ���[�U�[ID ��ݒ肷��B<br/>
     * <br/>
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
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
}
