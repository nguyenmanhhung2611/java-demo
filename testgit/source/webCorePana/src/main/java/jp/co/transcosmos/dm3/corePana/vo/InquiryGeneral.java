package jp.co.transcosmos.dm3.corePana.vo;

import java.util.Date;

/**
 * �ėp�⍇��.
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
public class InquiryGeneral {

    /** ���⍇��ID */
    private String inquiryId;
    /** �C�x���g�E�Z�~�i�[�� */
    private String eventName;
    /** �C�x���g�E�Z�~�i�[���� */
    private Date eventDatetime;
    /** �A�����@ */
    private String contactType;
    /** �A���\�Ȏ��ԑ� */
    private String contactTime;

    /**
     * ���⍇��ID ���擾����B<br/>
     * <br/>
     * @return ���⍇��ID
     */
    public String getInquiryId() {
        return inquiryId;
    }

    /**
     * ���⍇��ID ��ݒ肷��B<br/>
     * <br/>
     * @param inquiryId
     */
    public void setInquiryId(String inquiryId) {
        this.inquiryId = inquiryId;
    }

    /**
     * �C�x���g�E�Z�~�i�[�� ���擾����B<br/>
     * <br/>
     * @return �C�x���g�E�Z�~�i�[��
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * �C�x���g�E�Z�~�i�[�� ��ݒ肷��B<br/>
     * <br/>
     * @param eventName
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * �C�x���g�E�Z�~�i�[���� ���擾����B<br/>
     * <br/>
     * @return �C�x���g�E�Z�~�i�[����
     */
    public Date getEventDatetime() {
        return eventDatetime;
    }

    /**
     * �C�x���g�E�Z�~�i�[���� ��ݒ肷��B<br/>
     * <br/>
     * @param eventDatetime
     */
    public void setEventDatetime(Date eventDatetime) {
        this.eventDatetime = eventDatetime;
    }

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
