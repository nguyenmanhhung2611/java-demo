package jp.co.transcosmos.dm3.corePana.vo;

/**
 * ���t�H�[���E���[�_�[�`���[�g.
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
public class ReformChart {

    /** �V�X�e�����t�H�[��CD */
    private String sysReformCd;
    /** �`���[�g�L�[�l */
    private String chartKey;
    /** �`���[�g�ݒ�l */
    private Integer chartValue;

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
     * �`���[�g�L�[�l ���擾����B<br/>
     * <br/>
     * @return �`���[�g�L�[�l
     */
    public String getChartKey() {
        return chartKey;
    }

    /**
     * �`���[�g�L�[�l ��ݒ肷��B<br/>
     * <br/>
     * @param chartKey
     */
    public void setChartKey(String chartKey) {
        this.chartKey = chartKey;
    }

    /**
     * �`���[�g�ݒ�l ���擾����B<br/>
     * <br/>
     * @return �`���[�g�ݒ�l
     */
    public Integer getChartValue() {
        return chartValue;
    }

    /**
     * �`���[�g�ݒ�l ��ݒ肷��B<br/>
     * <br/>
     * @param chartValue
     */
    public void setChartValue(Integer chartValue) {
        this.chartValue = chartValue;
    }
}
