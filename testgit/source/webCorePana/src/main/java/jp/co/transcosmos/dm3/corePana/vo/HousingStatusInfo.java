package jp.co.transcosmos.dm3.corePana.vo;

/**
 * �����X�e�[�^�X���.
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
public class HousingStatusInfo extends jp.co.transcosmos.dm3.core.vo.HousingStatusInfo {

    /** �X�e�[�^�XCD */
    private String statusCd;
    /** ���[�U�[ID */
    private String userId;
    /** ���l */
    private String note;

    /**
     * �X�e�[�^�XCD ���擾����B<br/>
     * <br/>
     * @return �X�e�[�^�XCD
     */
    public String getStatusCd() {
        return statusCd;
    }

    /**
     * �X�e�[�^�XCD ��ݒ肷��B<br/>
     * <br/>
     * @param statusCd
     */
    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
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
}
