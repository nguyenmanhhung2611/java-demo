package jp.co.transcosmos.dm3.corePana.vo;

/**
 * �ݔ��E������ʑΏە\.
 * <p>
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 * <p>
 * <pre>
 * �S����        �C����        �C�����e
 * ------------ ----------- -----------------------------------------------------
 * liu.yandong   2015.04.17  �V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �ʃJ�X�^�}�C�Y�Ōp�������\��������̂ŁA���ڃC���X�^���X�𐶐����Ȃ����B<br/>
 * �C���X�^���X���擾����ꍇ�́AValueObjectFactory ����擾���鎖�B<br/>
 *
 */
public class EquipKindInfo {

    /** �ݔ�CD */
    private String equipCd;
    /** �������CD */
    private String housingKindCd;

    /**
     * �ݔ�CD ���擾����B<br/>
     * <br/>
     * @return �ݔ�CD
     */
    public String getEquipCd() {
        return equipCd;
    }

    /**
     * �ݔ�CD ��ݒ肷��B<br/>
     * <br/>
     * @param equipCd
     */
    public void setEquipCd(String equipCd) {
        this.equipCd = equipCd;
    }

    /**
     * �������CD ���擾����B<br/>
     * <br/>
     * @return �������CD
     */
    public String getHousingKindCd() {
        return housingKindCd;
    }

    /**
     * �������CD ��ݒ肷��B<br/>
     * <br/>
     * @param housingKindCd
     */
    public void setHousingKindCd(String housingKindCd) {
        this.housingKindCd = housingKindCd;
    }
}
