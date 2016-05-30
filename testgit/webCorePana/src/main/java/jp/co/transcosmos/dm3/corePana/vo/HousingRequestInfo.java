package jp.co.transcosmos.dm3.corePana.vo;

import java.math.BigDecimal;

/**
 * �������N�G�X�g���.
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
public class HousingRequestInfo extends jp.co.transcosmos.dm3.core.vo.HousingRequestInfo {

    /** �����ʐρE�����l */
    private BigDecimal buildingAreaLower;
    /** �����ʐρE����l */
    private BigDecimal buildingAreaUpper;
    /** �y�n�ʐρE�����l */
    private BigDecimal landAreaLower;
    /** �y�n�ʐρE����l */
    private BigDecimal landAreaUpper;
    /** ���t�H�[�����i�� */
    private String useReform;
    /** �z�N�� */
    private Integer builtMonth;

    /**
     * �����ʐρE�����l ���擾����B<br/>
     * <br/>
     * @return �����ʐρE�����l
     */
    public BigDecimal getBuildingAreaLower() {
        return buildingAreaLower;
    }

    /**
     * �����ʐρE�����l ��ݒ肷��B<br/>
     * <br/>
     * @param buildingAreaLower
     */
    public void setBuildingAreaLower(BigDecimal buildingAreaLower) {
        this.buildingAreaLower = buildingAreaLower;
    }

    /**
     * �����ʐρE����l ���擾����B<br/>
     * <br/>
     * @return �����ʐρE����l
     */
    public BigDecimal getBuildingAreaUpper() {
        return buildingAreaUpper;
    }

    /**
     * �����ʐρE����l ��ݒ肷��B<br/>
     * <br/>
     * @param buildingAreaUpper
     */
    public void setBuildingAreaUpper(BigDecimal buildingAreaUpper) {
        this.buildingAreaUpper = buildingAreaUpper;
    }

    /**
     * �y�n�ʐρE�����l ���擾����B<br/>
     * <br/>
     * @return �y�n�ʐρE�����l
     */
    public BigDecimal getLandAreaLower() {
        return landAreaLower;
    }

    /**
     * �y�n�ʐρE�����l ��ݒ肷��B<br/>
     * <br/>
     * @param landAreaLower
     */
    public void setLandAreaLower(BigDecimal landAreaLower) {
        this.landAreaLower = landAreaLower;
    }

    /**
     * �y�n�ʐρE����l ���擾����B<br/>
     * <br/>
     * @return �y�n�ʐρE����l
     */
    public BigDecimal getLandAreaUpper() {
        return landAreaUpper;
    }

    /**
     * �y�n�ʐρE����l ��ݒ肷��B<br/>
     * <br/>
     * @param landAreaUpper
     */
    public void setLandAreaUpper(BigDecimal landAreaUpper) {
        this.landAreaUpper = landAreaUpper;
    }

    /**
     * ���t�H�[�����i�� ���擾����B<br/>
     * <br/>
     * @return ���t�H�[�����i��
     */
    public String getUseReform() {
        return useReform;
    }

    /**
     * ���t�H�[�����i�� ��ݒ肷��B<br/>
     * <br/>
     * @param useReform
     */
    public void setUseReform(String useReform) {
        this.useReform = useReform;
    }

    /**
     * �z�N�� ���擾����B<br/>
     * <br/>
     * @return �z�N��
     */
    public Integer getBuiltMonth() {
        return builtMonth;
    }

    /**
     * �z�N�� ��ݒ肷��B<br/>
     * <br/>
     * @param builtMonth
     */
    public void setBuiltMonth(Integer builtMonth) {
        this.builtMonth = builtMonth;
    }
}
