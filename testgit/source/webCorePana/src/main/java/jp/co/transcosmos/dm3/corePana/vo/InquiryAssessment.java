package jp.co.transcosmos.dm3.corePana.vo;

import java.math.BigDecimal;

/**
 * ������.
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
public class InquiryAssessment {

    /** ���⍇��ID */
    private String inquiryId;
    /** ���p������� */
    private String buyHousingType;
    /** �Ԏ�CD */
    private String layoutCd;
    /** ��L�ʐ� */
    private BigDecimal personalArea;
    /** �z�N�� */
    private Integer buildAge;
    /** �y�n�ʐ� */
    private BigDecimal landArea;
    /** �y�n�ʐϒP�� */
    private String landAreaCrs;
    /** �����ʐ� */
    private BigDecimal buildingArea;
    /** �����ʐϒP�� */
    private String buildingAreaCrs;
    /** ���p�����E�X�֔ԍ� */
    private String zip;
    /** ���p�����E�s���{��CD */
    private String prefCd;
    /** ���p�����E�s�撬���Ԓn */
    private String address;
    /** ���p�����E������ */
    private String addressOther;
    /** ���� */
    private String presentCd;
    /** ���p�\�莞�� */
    private String buyTimeCd;
    /** �����ւ��̗L�� */
    private String replacementFlg;
    /** �v�]�E���� */
    private String requestText;
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
     * ���p������� ���擾����B<br/>
     * <br/>
     * @return ���p�������
     */
    public String getBuyHousingType() {
        return buyHousingType;
    }

    /**
     * ���p������� ��ݒ肷��B<br/>
     * <br/>
     * @param buyHousingType
     */
    public void setBuyHousingType(String buyHousingType) {
        this.buyHousingType = buyHousingType;
    }

    /**
     * �Ԏ�CD ���擾����B<br/>
     * <br/>
     * @return �Ԏ�CD
     */
    public String getLayoutCd() {
        return layoutCd;
    }

    /**
     * �Ԏ�CD ��ݒ肷��B<br/>
     * <br/>
     * @param layoutCd
     */
    public void setLayoutCd(String layoutCd) {
        this.layoutCd = layoutCd;
    }

    /**
     * ��L�ʐ� ���擾����B<br/>
     * <br/>
     * @return ��L�ʐ�
     */
    public BigDecimal getPersonalArea() {
        return personalArea;
    }

    /**
     * ��L�ʐ� ��ݒ肷��B<br/>
     * <br/>
     * @param personalArea
     */
    public void setPersonalArea(BigDecimal personalArea) {
        this.personalArea = personalArea;
    }

    /**
     * �z�N�� ���擾����B<br/>
     * <br/>
     * @return �z�N��
     */
    public Integer getBuildAge() {
        return buildAge;
    }

    /**
     * �z�N�� ��ݒ肷��B<br/>
     * <br/>
     * @param buildAge
     */
    public void setBuildAge(Integer buildAge) {
        this.buildAge = buildAge;
    }

    /**
     * �y�n�ʐ� ���擾����B<br/>
     * <br/>
     * @return �y�n�ʐ�
     */
    public BigDecimal getLandArea() {
        return landArea;
    }

    /**
     * �y�n�ʐ� ��ݒ肷��B<br/>
     * <br/>
     * @param landArea
     */
    public void setLandArea(BigDecimal landArea) {
        this.landArea = landArea;
    }

    /**
     * �y�n�ʐϒP�� ���擾����B<br/>
     * <br/>
     * @return �y�n�ʐϒP��
     */
    public String getLandAreaCrs() {
        return landAreaCrs;
    }

    /**
     * �y�n�ʐϒP�� ��ݒ肷��B<br/>
     * <br/>
     * @param landAreaCrs
     */
    public void setLandAreaCrs(String landAreaCrs) {
        this.landAreaCrs = landAreaCrs;
    }

    /**
     * �����ʐ� ���擾����B<br/>
     * <br/>
     * @return �����ʐ�
     */
    public BigDecimal getBuildingArea() {
        return buildingArea;
    }

    /**
     * �����ʐ� ��ݒ肷��B<br/>
     * <br/>
     * @param buildingArea
     */
    public void setBuildingArea(BigDecimal buildingArea) {
        this.buildingArea = buildingArea;
    }

    /**
     * �����ʐϒP�� ���擾����B<br/>
     * <br/>
     * @return �����ʐϒP��
     */
    public String getBuildingAreaCrs() {
        return buildingAreaCrs;
    }

    /**
     * �����ʐϒP�� ��ݒ肷��B<br/>
     * <br/>
     * @param buildingAreaCrs
     */
    public void setBuildingAreaCrs(String buildingAreaCrs) {
        this.buildingAreaCrs = buildingAreaCrs;
    }
    
    /**
     * ���p�����E�X�֔ԍ� ���擾����B<br/>
     * <br/>
     * @return ���p�����E�X�֔ԍ�
     */
    public String getZip() {
        return zip;
    }

    /**
     * ���p�����E�X�֔ԍ� ��ݒ肷��B<br/>
     * <br/>
     * @param zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * ���p�����E�s���{��CD ���擾����B<br/>
     * <br/>
     * @return ���p�����E�s���{��CD
     */
    public String getPrefCd() {
        return prefCd;
    }

    /**
     * ���p�����E�s���{��CD ��ݒ肷��B<br/>
     * <br/>
     * @param prefCd
     */
    public void setPrefCd(String prefCd) {
        this.prefCd = prefCd;
    }

    /**
     * ���p�����E�s�撬���Ԓn ���擾����B<br/>
     * <br/>
     * @return ���p�����E�s�撬���Ԓn
     */
    public String getAddress() {
        return address;
    }

    /**
     * ���p�����E�s�撬���Ԓn ��ݒ肷��B<br/>
     * <br/>
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * ���p�����E������ ���擾����B<br/>
     * <br/>
     * @return ���p�����E������
     */
    public String getAddressOther() {
        return addressOther;
    }

    /**
     * ���p�����E������ ��ݒ肷��B<br/>
     * <br/>
     * @param addressOther
     */
    public void setAddressOther(String addressOther) {
        this.addressOther = addressOther;
    }

    /**
     * ���� ���擾����B<br/>
     * <br/>
     * @return ����
     */
    public String getPresentCd() {
        return presentCd;
    }

    /**
     * ���� ��ݒ肷��B<br/>
     * <br/>
     * @param presentCd
     */
    public void setPresentCd(String presentCd) {
        this.presentCd = presentCd;
    }

    /**
     * ���p�\�莞�� ���擾����B<br/>
     * <br/>
     * @return ���p�\�莞��
     */
    public String getBuyTimeCd() {
        return buyTimeCd;
    }

    /**
     * ���p�\�莞�� ��ݒ肷��B<br/>
     * <br/>
     * @param buyTimeCd
     */
    public void setBuyTimeCd(String buyTimeCd) {
        this.buyTimeCd = buyTimeCd;
    }

    /**
     * �����ւ��̗L�� ���擾����B<br/>
     * <br/>
     * @return �����ւ��̗L��
     */
    public String getReplacementFlg() {
        return replacementFlg;
    }

    /**
     * �����ւ��̗L�� ��ݒ肷��B<br/>
     * <br/>
     * @param replacementFlg
     */
    public void setReplacementFlg(String replacementFlg) {
        this.replacementFlg = replacementFlg;
    }

    /**
     * �v�]�E���� ���擾����B<br/>
     * <br/>
     * @return �v�]�E����
     */
    public String getRequestText() {
        return requestText;
    }

    /**
     * �v�]�E���� ��ݒ肷��B<br/>
     * <br/>
     * @param requestText
     */
    public void setRequestText(String requestText) {
        this.requestText = requestText;
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
