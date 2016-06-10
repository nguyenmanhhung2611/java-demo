package jp.co.transcosmos.dm3.corePana.vo;

/**
 * �����⍇�����.
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
public class InquiryHousing extends jp.co.transcosmos.dm3.core.vo.InquiryHousing {

    /** �A�����@ */
    private String contactType;
    /** �A���\�Ȏ��ԑ� */
    private String contactTime;
    
    /**
     * �A�����@ ���擾����B<br/>
     * <br/>
     * @return �A�����@
     */
    public String getContactType() {
        return contactType;
    }
    
    /**
     * �A�����@ ��ݒ肷��B<br/>
     * <br/>
     * @param contactType
     */
    public void setContactType(String contactType) {
        this.contactType = contactType;
    }
    
    /**
     * �A���\�Ȏ��ԑ� ���擾����B<br/>
     * <br/>
     * @return �A���\�Ȏ��ԑ�
     */
    public String getContactTime() {
        return contactTime;
    }
    
    /**
     * �A���\�Ȏ��ԑ� ��ݒ肷��B<br/>
     * <br/>
     * @param contactTime
     */
    public void setContactTime(String contactTime) {
        this.contactTime = contactTime;
    }
}
