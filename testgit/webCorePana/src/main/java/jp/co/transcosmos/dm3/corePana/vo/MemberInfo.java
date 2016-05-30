package jp.co.transcosmos.dm3.corePana.vo;

import java.util.Date;

import jp.co.transcosmos.dm3.corePana.model.mypage.PanaMypageUserInterface;
import jp.co.transcosmos.dm3.login.v3.DefaultLockChecker;
import jp.co.transcosmos.dm3.login.v3.LockSupportChecker;

/**
 * �}�C�y�[�W������.
 * <p>
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 * <p>
 * <pre>
 * �S����        �C����        �C�����e
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.03.10  �V�K�쐬
 * H.Mizuno		2015.04.16	�A�J�E���g���b�N�@�\�p�C���^�[�t�F�[�X�ǉ�
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �ʃJ�X�^�}�C�Y�Ōp�������\��������̂ŁA���ڃC���X�^���X�𐶐����Ȃ����B<br/>
 * �C���X�^���X���擾����ꍇ�́AValueObjectFactory ����擾���鎖�B<br/>
 *
 */
public class MemberInfo extends jp.co.transcosmos.dm3.core.vo.MemberInfo implements PanaMypageUserInterface {

	private static final long serialVersionUID = 1L;

	/** �Z���E�X�֔ԍ� */
    private String zip;
    /** �Z���E�s���{��CD */
    private String prefCd;
    /** �Z���E�撬���Ԓn */
    private String address;
    /** �Z���E������ */
    private String addressOther;
    /** �d�b�ԍ� */
    private String tel;
    /** FAX */
    private String fax;
    /** ���[���z�M��] */
    private String mailSendFlg;
    /** ���Z��� */
    private String residentFlg;
    /** ��]�n��E�s���{�� */
    private String hopePrefCd;
    /** ��]�n��E�s�撬�� */
    private String hopeAddress;
    /** �o�^�o�H */
    private String entryRoute;
    /** �v����CD */
    private String promoCd;
    /** ������CD */
    private String refCd;
    /** ���O�C�����s�� */
    private Integer failCnt;
    /** �ŏI���O�C�����s�� */
    private Date lastFailDate;
    /** ���b�N�t���O */
    private String lockFlg;


    
    /**
     * �}�C�y�[�W���[�U�[�̃p�X���[�h�ƍ�����<br/>
     * <br/>
     * @param pPassword ���͂��ꂽ�p�X���[�h
     * @return �p�X���[�h�̏ƍ��ɐ��������ꍇ�� true �𕜋A
     */
    @Override
    public boolean matchPassword(String pPassword){

    	// �Ǘ���ʂ���A���b�N�t���O�� "1" ���ݒ肳��Ă���ꍇ�A���b�N���Ȃ̂� false �𕜋A����B
    	if ("1".equals(this.lockFlg)) return false;

    	// ���b�N�t���O���ݒ肳��Ă��Ȃ��ꍇ�͒ʏ�̔F�؂��s���B
    	return super.matchPassword(pPassword);
    }



	/**
	 * Cookie �ɂ�鎩�����O�C���̑Ó������菈��<br/>
	 * Cookie �Ɋi�[����Ă���g�[�N�����`�F�b�N���A���Ȃ���� true �𕜋A����B<br\>
	 * <br/>
	 * @param pPassword �������O�C���Ŏg�p����g�[�N��
	 * @return �p�����[�^�̏ƍ������Ȃ������ꍇ�Atrue �𕜋A����B
	 */
    @Override
    public boolean matchCookieLoginPassword(String pPassword){

    	// Cookie �ɂ�鎩�����O�C�������A�Ǘ���ʂ���A���b�N�t���O�� "1" ���ݒ肳��Ă���ꍇ�A
    	// ���b�N���Ȃ̂� false �𕜋A����B
    	if ("1".equals(this.lockFlg)) return false;

    	// �A�J�E���g���b�N�̃`�F�b�J�[���g�p���ă��O�C�����s�񐔂ɂ�郍�b�N��Ԃ��`�F�b�N����B
    	// �������b�N���̏ꍇ�� false �𕜋A����B
    	// ������`�F�b�N���Ȃ��Ă��A���O�C�����������鎖�͖������A�ŏI���O�C�������X�V����Ă��܂��B
    	LockSupportChecker checker = new DefaultLockChecker();
    	if (checker.isLocked(this)) return false;

    	return super.matchCookieLoginPassword(pPassword);
    }



    /**
     * �Z���E�X�֔ԍ� ���擾����B<br/>
     * <br/>
     * @return �Z���E�X�֔ԍ�
     */
    public String getZip() {
        return zip;
    }

    /**
     * �Z���E�X�֔ԍ� ��ݒ肷��B<br/>
     * <br/>
     * @param zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * �Z���E�s���{��CD ���擾����B<br/>
     * <br/>
     * @return �Z���E�s���{��CD
     */
    public String getPrefCd() {
        return prefCd;
    }

    /**
     * �Z���E�s���{��CD ��ݒ肷��B<br/>
     * <br/>
     * @param prefCd
     */
    public void setPrefCd(String prefCd) {
        this.prefCd = prefCd;
    }

    /**
     * �Z���E�撬���Ԓn ���擾����B<br/>
     * <br/>
     * @return �Z���E�撬���Ԓn
     */
    public String getAddress() {
        return address;
    }

    /**
     * �Z���E�撬���Ԓn ��ݒ肷��B<br/>
     * <br/>
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * �Z���E������ ���擾����B<br/>
     * <br/>
     * @return �Z���E������
     */
    public String getAddressOther() {
        return addressOther;
    }

    /**
     * �Z���E������ ��ݒ肷��B<br/>
     * <br/>
     * @param addressOther
     */
    public void setAddressOther(String addressOther) {
        this.addressOther = addressOther;
    }

    /**
     * �d�b�ԍ� ���擾����B<br/>
     * <br/>
     * @return �d�b�ԍ�
     */
    public String getTel() {
        return tel;
    }

    /**
     * �d�b�ԍ� ��ݒ肷��B<br/>
     * <br/>
     * @param TEL
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

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
     * @param FAX
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * ���[���z�M��] ���擾����B<br/>
     * <br/>
     * @return ���[���z�M��]
     */
    public String getMailSendFlg() {
        return mailSendFlg;
    }

    /**
     * ���[���z�M��] ��ݒ肷��B<br/>
     * <br/>
     * @param mailSendFlg
     */
    public void setMailSendFlg(String mailSendFlg) {
        this.mailSendFlg = mailSendFlg;
    }

    /**
     * ���Z��� ���擾����B<br/>
     * <br/>
     * @return ���Z���
     */
    public String getResidentFlg() {
        return residentFlg;
    }

    /**
     * ���Z��� ��ݒ肷��B<br/>
     * <br/>
     * @param residentFlg
     */
    public void setResidentFlg(String residentFlg) {
        this.residentFlg = residentFlg;
    }

    /**
     * ��]�n��E�s���{�� ���擾����B<br/>
     * <br/>
     * @return ��]�n��E�s���{��
     */
    public String getHopePrefCd() {
        return hopePrefCd;
    }

    /**
     * ��]�n��E�s���{�� ��ݒ肷��B<br/>
     * <br/>
     * @param hopePrefCd
     */
    public void setHopePrefCd(String hopePrefCd) {
        this.hopePrefCd = hopePrefCd;
    }

    /**
     * ��]�n��E�s�撬�� ���擾����B<br/>
     * <br/>
     * @return ��]�n��E�s�撬��
     */
    public String getHopeAddress() {
        return hopeAddress;
    }

    /**
     * ��]�n��E�s�撬�� ��ݒ肷��B<br/>
     * <br/>
     * @param hopeAddress
     */
    public void setHopeAddress(String hopeAddress) {
        this.hopeAddress = hopeAddress;
    }

    /**
     * �o�^�o�H ���擾����B<br/>
     * <br/>
     * @return �o�^�o�H
     */
    public String getEntryRoute() {
        return entryRoute;
    }

    /**
     * �o�^�o�H ��ݒ肷��B<br/>
     * <br/>
     * @param entryRoute
     */
    public void setEntryRoute(String entryRoute) {
        this.entryRoute = entryRoute;
    }

    /**
     * �v����CD ���擾����B<br/>
     * <br/>
     * @return �v����CD
     */
    public String getPromoCd() {
        return promoCd;
    }

    /**
     * �v����CD ��ݒ肷��B<br/>
     * <br/>
     * @param promoCd
     */
    public void setPromoCd(String promoCd) {
        this.promoCd = promoCd;
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

    /**
     * ���O�C�����s�� ���擾����B<br/>
     * <br/>
     * @return ���O�C�����s��
     */
    @Override
    public Integer getFailCnt() {
        return failCnt;
    }

    /**
     * ���O�C�����s�� ��ݒ肷��B<br/>
     * <br/>
     * @param failCnt
     */
    public void setFailCnt(Integer failCnt) {
        this.failCnt = failCnt;
    }

    /**
     * �ŏI���O�C�����s�� ���擾����B<br/>
     * <br/>
     * @return �ŏI���O�C�����s��
     */
    @Override
    public Date getLastFailDate() {
        return lastFailDate;
    }

    /**
     * �ŏI���O�C�����s�� ��ݒ肷��B<br/>
     * <br/>
     * @param lastFailDate
     */
    public void setLastFailDate(Date lastFailDate) {
        this.lastFailDate = lastFailDate;
    }

    /**
     * ���b�N�t���O ���擾����B<br/>
     * <br/>
     * @return ���b�N�t���O
     */
    public String getLockFlg() {
        return lockFlg;
    }

    /**
     * ���b�N�t���O ��ݒ肷��B<br/>
     * <br/>
     * @param lockFlg
     */
    public void setLockFlg(String lockFlg) {
        this.lockFlg = lockFlg;
    }

}
