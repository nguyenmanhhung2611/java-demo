package jp.co.transcosmos.dm3.corePana.vo;

/**
 * �����������E������ʑΏە\.
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
public class PartKindInfo {

    /** ����������CD */
    private String partSrchCd;
    /** �������CD */
    private String housingKindCd;

    /**
     * ����������CD ���擾����B<br/>
     * <br/>
     * @return ����������CD
     */
    public String getPartSrchCd() {
        return partSrchCd;
    }

    /**
     * ����������CD ��ݒ肷��B<br/>
     * <br/>
     * @param partSrchCd
     */
    public void setPartSrchCd(String partSrchCd) {
        this.partSrchCd = partSrchCd;
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
