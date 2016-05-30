package jp.co.transcosmos.dm3.corePana.vo;

/**
 * �����C���X�y�N�V����.
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
public class HousingInspection {

    /** �V�X�e������CD */
    private String sysHousingCd;
    /** �C���X�y�N�V�����L�[�l */
    private String inspectionKey;
    /** �ݒ�l�̐M���x */
    private Integer inspectionTrust;
    /** �C���X�y�N�V�����ݒ�l */
    private Integer inspectionValue;

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
     * �C���X�y�N�V�����L�[�l ���擾����B<br/>
     * <br/>
     * @return �C���X�y�N�V�����L�[�l
     */
    public String getInspectionKey() {
        return inspectionKey;
    }

    /**
     * �C���X�y�N�V�����L�[�l ��ݒ肷��B<br/>
     * <br/>
     * @param inspectionKey
     */
    public void setInspectionKey(String inspectionKey) {
        this.inspectionKey = inspectionKey;
    }

    /**
     * �ݒ�l�̐M���x ���擾����B<br/>
     * <br/>
     * @return �ݒ�l�̐M���x
     */
    public Integer getInspectionTrust() {
        return inspectionTrust;
    }

    /**
     * �ݒ�l�̐M���x ��ݒ肷��B<br/>
     * <br/>
     * @param inspectionTrust
     */
    public void setInspectionTrust(Integer inspectionTrust) {
        this.inspectionTrust = inspectionTrust;
    }

    /**
     * �C���X�y�N�V�����ݒ�l ���擾����B<br/>
     * <br/>
     * @return �C���X�y�N�V�����ݒ�l
     */
    public Integer getInspectionValue() {
        return inspectionValue;
    }

    /**
     * �C���X�y�N�V�����ݒ�l ��ݒ肷��B<br/>
     * <br/>
     * @param inspectionValue
     */
    public void setInspectionValue(Integer inspectionValue) {
        this.inspectionValue = inspectionValue;
    }
}
