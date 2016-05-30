package jp.co.transcosmos.dm3.corePana.vo;

/**
 * ���⍇���w�b�_.
 * <p>
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 * <p>
 * <pre>
 * �S����        �C����        �C�����e
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.03.10  �V�K�쐬
 * H.Mizuno		2015.03.11	�Z�����A�A����t�B�[���h�ǉ�
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �ʃJ�X�^�}�C�Y�Ōp�������\��������̂ŁA���ڃC���X�^���X�𐶐����Ȃ����B<br/>
 * �C���X�^���X���擾����ꍇ�́AValueObjectFactory ����擾���鎖�B<br/>
 *
 */
public class InquiryHeader extends jp.co.transcosmos.dm3.core.vo.InquiryHeader {

    // 2015.03.11 H.Mizuno ���ڒǉ� start
    /** �⍇�ҏZ���E�X�֔ԍ� */
    private String zip;
    /** �⍇�ҏZ���E�s���{��CD */
    private String prefCd;
    /** �⍇�ҏZ���E�s�撬���Ԓn */
    private String address;
    /** �⍇�ҏZ���E������ */
    private String addressOther;
    // 2015.03.11 H.Mizuno ���ڒǉ� end

    /** FAX */
    private String fax;
    /** ������CD */
    private String refCd;

    // 2015.03.11 H.Mizuno ���ڒǉ� start
    /**
     * �⍇�ҏZ���E�X�֔ԍ����擾����B<br/>
     * <br/>
     * @return �⍇�ҏZ���E�X�֔ԍ�
     */
    public String getZip() {
        return zip;
    }

    /**
     * �⍇�ҏZ���E�X�֔ԍ� ��ݒ肷��B<br/>
     * <br/>
     * @param zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * �⍇�ҏZ���E�s���{��CD ���擾����B<br/>
     * <br/>
     * @return �⍇�ҏZ���E�s���{��CD
     */
    public String getPrefCd() {
        return prefCd;
    }

    /**
     * �⍇�ҏZ���E�s���{��CD ��ݒ肷��B<br/>
     * <br/>
     * @param prefCd
     */
    public void setPrefCd(String prefCd) {
        this.prefCd = prefCd;
    }

    /**
     * �⍇�ҏZ���E�s�撬���Ԓn ���擾����B<br/>
     * <br/>
     * @return �⍇�ҏZ���E�s�撬���Ԓn
     */
    public String getAddress() {
        return address;
    }

    /**
     * �⍇�ҏZ���E�s�撬���Ԓn ��ݒ肷��B<br/>
     * <br/>
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * �⍇�ҏZ���E������ ���擾����B<br/>
     * <br/>
     * @return �⍇�ҏZ���E������
     */
    public String getAddressOther() {
        return addressOther;
    }

    /**
     * �⍇�ҏZ���E������ ��ݒ肷��B<br/>
     * <br/>
     * @param addressOther
     */
    public void setAddressOther(String addressOther) {
        this.addressOther = addressOther;
    }

    // 2015.03.11 H.Mizuno ���ڒǉ� end

    /**
     * FAX ���擾����B<br/>
     * <br/>
     * @return FAX
     */
    public String getFax() {
        return fax;
    }

    /**
     * FAX ��ݒ肷��B<br/>
     * <br/>
     * @param fax
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * ������CD ���擾����B<br/>
     * <br/>
     * @return ������CD
     */
    public String getRefCd() {
        return refCd;
    }

    /**
     * ������CD ��ݒ肷��B<br/>
     * <br/>
     * @param refCd
     */
    public void setRefCd(String refCd) {
        this.refCd = refCd;
    }
}
