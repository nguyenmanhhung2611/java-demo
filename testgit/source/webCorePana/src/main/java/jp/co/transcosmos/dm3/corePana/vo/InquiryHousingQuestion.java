package jp.co.transcosmos.dm3.corePana.vo;

/**
 * �����⍇���A���P�[�g.
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
public class InquiryHousingQuestion {

    /** ���⍇��ID */
    private String inquiryId;
    /** �A���P�[�g�ԍ� */
    private String categoryNo;
    /** ��CD */
    private String ansCd;
    /** ���̑����� */
    private String note;

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
     * �A���P�[�g�ԍ� ���擾����B<br/>
     * <br/>
     * @return �A���P�[�g�ԍ�
     */
    public String getCategoryNo() {
        return categoryNo;
    }
    
    /**
     * �A���P�[�g�ԍ� ��ݒ肷��B<br/>
     * <br/>
     * @param categoryNo
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }
    
    /**
     * ��CD ���擾����B<br/>
     * <br/>
     * @return ��CD
     */
    public String getAnsCd() {
        return ansCd;
    }

    /**
     * ��CD ��ݒ肷��B<br/>
     * <br/>
     * @param ansCd
     */
    public void setAnsCd(String ansCd) {
        this.ansCd = ansCd;
    }

    /**
     * ���̑����� ���擾����B<br/>
     * <br/>
     * @return ���̑�����
     */
    public String getNote() {
        return note;
    }

    /**
     * ���̑����� ��ݒ肷��B<br/>
     * <br/>
     * @param note
     */
    public void setNote(String note) {
        this.note = note;
    }
}
