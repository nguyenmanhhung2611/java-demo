package jp.co.transcosmos.dm3.corePana.model.mypage;

import java.util.Date;

import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.login.LockSupportLoginUser;

/**
 * Panasonic�p �}�C�y�[�W���[�U�[�p�C���^�[�t�F�[�X.
 * �A�J�E���g���b�N�@�\�p�C���^�[�t�F�[�X��ǉ��B<br/>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * tang.tianyun		2015.04.16	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public interface PanaMypageUserInterface extends MypageUserInterface, LockSupportLoginUser {

    /**
     * �Z���E�X�֔ԍ� ���擾����B<br/>
     * <br/>
     * @return �Z���E�X�֔ԍ�
     */
    public String getZip();

    /**
     * �Z���E�X�֔ԍ� ��ݒ肷��B<br/>
     * <br/>
     * @param zip
     */
    public void setZip(String zip);

    /**
     * �Z���E�s���{��CD ���擾����B<br/>
     * <br/>
     * @return �Z���E�s���{��CD
     */
    public String getPrefCd();

    /**
     * �Z���E�s���{��CD ��ݒ肷��B<br/>
     * <br/>
     * @param prefCd
     */
    public void setPrefCd(String prefCd);

    /**
     * �Z���E�s�撬���� ���擾����B<br/>
     * <br/>
     * @return �Z���E�s�撬����
     */
    public String getAddress();

    /**
     * �Z���E�s�撬���� ��ݒ肷��B<br/>
     * <br/>
     * @param address
     */
    public void setAddress(String address);

    /**
     * �Z���E�����Ԓn���̑� ���擾����B<br/>
     * <br/>
     * @return �Z���E�����Ԓn���̑�
     */
    public String getAddressOther();

    /**
     * �Z���E�����Ԓn���̑� ��ݒ肷��B<br/>
     * <br/>
     * @param addressOther
     */
    public void setAddressOther(String addressOther);

    /**
     * �d�b�ԍ� ���擾����B<br/>
     * <br/>
     * @return �d�b�ԍ�
     */
    public String getTel();

    /**
     * �d�b�ԍ� ��ݒ肷��B<br/>
     * <br/>
     * @param TEL
     */
    public void setTel(String tel);

    /**
     * FAX ���擾����B<br/>
     * <br/>
     * @return FAX
     */
    public String getFax();

    /**
     * FAX ��ݒ肷��B<br/>
     * <br/>
     * @param FAX
     */
    public void setFax(String fax);

    /**
     * ���[���z�M��] ���擾����B<br/>
     * <br/>
     * @return ���[���z�M��]
     */
    public String getMailSendFlg();

    /**
     * ���[���z�M��] ��ݒ肷��B<br/>
     * <br/>
     * @param mailSendFlg
     */
    public void setMailSendFlg(String mailSendFlg);

    /**
     * ���Z��� ���擾����B<br/>
     * <br/>
     * @return ���Z���
     */
    public String getResidentFlg();

    /**
     * ���Z��� ��ݒ肷��B<br/>
     * <br/>
     * @param residentFlg
     */
    public void setResidentFlg(String residentFlg);

    /**
     * ��]�n��E�s���{�� ���擾����B<br/>
     * <br/>
     * @return ��]�n��E�s���{��
     */
    public String getHopePrefCd();

    /**
     * ��]�n��E�s���{�� ��ݒ肷��B<br/>
     * <br/>
     * @param hopePrefCd
     */
    public void setHopePrefCd(String hopePrefCd);

    /**
     * ��]�n��E�s�撬�� ���擾����B<br/>
     * <br/>
     * @return ��]�n��E�s�撬��
     */
    public String getHopeAddress();

    /**
     * ��]�n��E�s�撬�� ��ݒ肷��B<br/>
     * <br/>
     * @param hopeAddress
     */
    public void setHopeAddress(String hopeAddress);

    /**
     * �o�^�o�H ���擾����B<br/>
     * <br/>
     * @return �o�^�o�H
     */
    public String getEntryRoute();

    /**
     * �o�^�o�H ��ݒ肷��B<br/>
     * <br/>
     * @param entryRoute
     */
    public void setEntryRoute(String entryRoute);

    /**
     * ���O�C�����s�� ���擾����B<br/>
     * <br/>
     * @return ���O�C�����s��
     */
    public Integer getFailCnt();

    /**
     * ���O�C�����s�� ��ݒ肷��B<br/>
     * <br/>
     * @param failCnt
     */
    public void setFailCnt(Integer failCnt);

    /**
     * �ŏI���O�C�����s�� ���擾����B<br/>
     * <br/>
     * @return �ŏI���O�C�����s��
     */
    public Date getLastFailDate();

    /**
     * �ŏI���O�C�����s�� ��ݒ肷��B<br/>
     * <br/>
     * @param lastFailDate
     */
    public void setLastFailDate(Date lastFailDate);

    /**
     * ���b�N�t���O ���擾����B<br/>
     * <br/>
     * @return ���b�N�t���O
     */
    public String getLockFlg();

    /**
     * ���b�N�t���O ��ݒ肷��B<br/>
     * <br/>
     * @param lockFlg
     */
    public void setLockFlg(String lockFlg);

	/**
	 * @return promoCd
	 */
	public String getPromoCd();

	/**
	 * @param promoCd �Z�b�g���� promoCd
	 */
	public void setPromoCd(String promoCd);

	/**
	 * @return refCd
	 */
	public String getRefCd();

	/**
	 * @param refCd �Z�b�g���� refCd
	 */
	public void setRefCd(String refCd);


	/**
	 * �Ǘ����[�U�[�̍ŏI���O�C�������擾����B<br/>
	 * <br/>
	 * @return �Ǘ����[�U�[�̍ŏI���O�C����
	 */
	public Date getLastLogin();

	/**
	 * �Ǘ����[�U�[�̍ŏI���O�C������ݒ肷��B<br/>
	 * <br/>
	 * @param insDate �Ǘ����[�U�[�̍ŏI���O�C����
	 */
	public void setLastLogin(Date lastLogin);
}
