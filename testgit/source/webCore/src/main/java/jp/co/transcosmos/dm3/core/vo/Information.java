package jp.co.transcosmos.dm3.core.vo;

import java.util.Date;

/**
 * ���m�点���N���X.
 * <p>
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.06	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �ʃJ�X�^�}�C�Y�Ōp�������\��������̂ŁA���ڃC���X�^���X�𐶐����Ȃ����B<br/>
 * �C���X�^���X���擾����ꍇ�́AValueObjectFactory ����擾���鎖�B<br/>
 * 
 */
public class Information {

	/** ���m�点�ԍ� */
	private String informationNo;
	/** ���m�点��� */
	private String informationType;
	/** �^�C�g�� */
	private String title;
	/** �\���J�n�� */
	private Date startDate;
	/** �\���I���� */
	private Date endDate;
	/** �����N��URL */
	private String url;
	/** ���J�Ώۋ敪 */
	private String dspFlg;
	/** ���m�点���e */
	private String informationMsg;
	/** �o�^�� */
	private Date insDate;
	/** �o�^�� */
	private String insUserId;
	/** �ŏI�X�V�� */
	private Date updDate;
	/** �ŏI�X�V�� */
	private String updUserId;
	
	/**
	 * ���m�点�ԍ���ݒ肷��B<br/>
	 * <br/>
	 * @param informationNo ���m�点�ԍ�
	 */
	public String getInformationNo() {
		return informationNo;
	}
	
	/**
	 * ���m�点�ԍ����擾����B<br/>
	 * <br/>
	 * @return ���m�点�ԍ�
	 */
	public void setInformationNo(String informationNo) {
		this.informationNo = informationNo;
	}
	
	/**
	 * ���m�点��ʂ�ݒ肷��B<br/>
	 * <br/>
	 * @param informationType ���m�点���
	 */
	public String getInformationType() {
		return informationType;
	}
	
	/**
	 * ���m�点��ʂ��擾����B<br/>
	 * <br/>
	 * @return ���m�点���
	 */
	public void setInformationType(String informationType) {
		this.informationType = informationType;
	}
	
	/**
	 * �^�C�g����ݒ肷��B<br/>
	 * <br/>
	 * @param title �^�C�g��
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * �^�C�g�����擾����B<br/>
	 * <br/>
	 * @return �^�C�g��
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * �\���J�n����ݒ肷��B<br/>
	 * <br/>
	 * @param startDate �\���J�n��
	 */
	public Date getStartDate() {
		return startDate;
	}
	
	/**
	 * �\���J�n�����擾����B<br/>
	 * <br/>
	 * @return �\���J�n��
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * �\���I������ݒ肷��B<br/>
	 * <br/>
	 * @param endDate �\���I����
	 */
	public Date getEndDate() {
		return endDate;
	}
	
	/**
	 * �\���I�������擾����B<br/>
	 * <br/>
	 * @return �\���I����
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * �����N��URL��ݒ肷��B<br/>
	 * <br/>
	 * @param url �����N��URL
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * �����N��URL���擾����B<br/>
	 * <br/>
	 * @return �����N��URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * ���J�Ώۋ敪��ݒ肷��B<br/>
	 * <br/>
	 * @param dspFlg ���J�Ώۋ敪
	 */
	public String getDspFlg() {
		return dspFlg;
	}
	
	/**
	 * ���J�Ώۋ敪���擾����B<br/>
	 * <br/>
	 * @return ���J�Ώۋ敪
	 */
	public void setDspFlg(String dspFlg) {
		this.dspFlg = dspFlg;
	}
	
	/**
	 * ���m�点���e��ݒ肷��B<br/>
	 * <br/>
	 * @param informationMsg ���m�点���e
	 */
	public String getInformationMsg() {
		return informationMsg;
	}
	
	/**
	 * ���m�点���e���擾����B<br/>
	 * <br/>
	 * @return ���m�点���e
	 */
	public void setInformationMsg(String informationMsg) {
		this.informationMsg = informationMsg;
	}
	
	/**
	 * �o�^����ݒ肷��B<br/>
	 * <br/>
	 * @param insDate �o�^��
	 */
	public Date getInsDate() {
		return insDate;
	}
	
	/**
	 * �o�^�����擾����B<br/>
	 * <br/>
	 * @return �o�^��
	 */
	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}
	
	/**
	 * �o�^�҂�ݒ肷��B<br/>
	 * <br/>
	 * @param insUserId �o�^��
	 */
	public String getInsUserId() {
		return insUserId;
	}
	
	/**
	 * �o�^�҂��擾����B<br/>
	 * <br/>
	 * @return �o�^��
	 */
	public void setInsUserId(String insUserId) {
		this.insUserId = insUserId;
	}
	
	/**
	 * �ŏI�X�V����ݒ肷��B<br/>
	 * <br/>
	 * @param updDate �ŏI�X�V��
	 */
	public Date getUpdDate() {
		return updDate;
	}
	
	/**
	 * �ŏI�X�V�����擾����B<br/>
	 * <br/>
	 * @return �ŏI�X�V��
	 */
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}
	
	/**
	 * �ŏI�X�V�҂�ݒ肷��B<br/>
	 * <br/>
	 * @param updUserId �ŏI�X�V��
	 */
	public String getUpdUserId() {
		return updUserId;
	}
	
	/**
	 * �ŏI�X�V�҂��擾����B<br/>
	 * <br/>
	 * @return �ŏI�X�V��
	 */
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}

}
