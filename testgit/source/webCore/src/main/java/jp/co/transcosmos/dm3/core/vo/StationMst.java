package jp.co.transcosmos.dm3.core.vo;

import java.util.Date;

/**
 * �w���}�X�^�N���X.
 * <p>
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * �ד��@��		2006.12.26	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �ʃJ�X�^�}�C�Y�Ōp�������\��������̂ŁA���ڃC���X�^���X�𐶐����Ȃ����B<br/>
 * �C���X�^���X���擾����ꍇ�́AValueObjectFactory ����擾���鎖�B<br/>
 * 
 */
public class StationMst {
	/** �wCD */
	private String stationCd;
	/** �w�� */
	private String stationName;
	/** �w���E�J�b�R�t�� */
	private String stationNameFull;
	/** �s���{��CD */
	private String prefCd;
	/** �o�^�� */
	private Date insDate;
	/** �o�^�� */
	private String insUserId;
	/**�@�ŏI�X�V�� */
	private Date updDate;
	/** �ŏI�X�V�� */
	private String updUserId;
	
	

	/**
	 * �wCD ���擾����B<br/>
	 * <br/>
	 * @return �wCD
	 */
	public String getStationCd() {
		return stationCd;
	}
	
	/**
	 * �wCD ��ݒ肷��B<br/>
	 * <br/>
	 * @param stationCd �wCD
	 */
	public void setStationCd(String stationCd) {
		this.stationCd = stationCd;
	}

	/**
	 * �w�����擾����B<br/>
	 * <br/>
	 * @return �w��
	 */
	public String getStationName() {
		return stationName;
	}
	
	/**
	 * �w����ݒ肷��B<br/>
	 * <br/>
	 * @param stationName �w��
	 */
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	/**
	 * �w���E�J�b�R�t�����擾����B<br/>
	 * <br/>
	 * @return �w���E�J�b�R�t��
	 */
	public String getStationNameFull() {
		return stationNameFull;
	}
	
	/**
	 * �w���E�J�b�R�t����ݒ肷��B<br/>
	 * <br/>
	 * @param stationNameFull �w���E�J�b�R�t��
	 */
	public void setStationNameFull(String stationNameFull) {
		this.stationNameFull = stationNameFull;
	}

	/**
	 * �s���{��CD ���擾����B<br/>
	 * <br/>
	 * @return �s���{��CD
	 */
	public String getPrefCd() {
		return prefCd;
	}
	
	/**
	 * �s���{��CD ��ݒ肷��B<br/>
	 * <br/>
	 * @param prefCd �s���{��CD
	 */
	public void setPrefCd(String prefCd) {
		this.prefCd = prefCd;
	}
	
	/**
	 * �o�^�����擾����B<br/>
	 * <br/>
	 * @return �o�^��
	 */
	public Date getInsDate() {
		return insDate;
	}
	
	/**
	 * �o�^����ݒ肷��B<br/>
	 * <br/>
	 * @param insDate �o�^��
	 */
	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	/**
	 * �o�^�҂��擾����B<br/>
	 * <br/>
	 * @return �o�^��
	 */
	public String getInsUserId() {
		return insUserId;
	}

	/**
	 * �o�^�҂�ݒ肷��B<br/>
	 * <br/>
	 * @param insUserId �o�^��
	 */
	public void setInsUserId(String insUserId) {
		this.insUserId = insUserId;
	}

	/**
	 * �ŏI�X�V�����擾����B<br/>
	 * <br/>
	 * @return �ŏI�X�V��
	 */
	public Date getUpdDate() {
		return updDate;
	}
	
	/**
	 * �ŏI�X�V����ݒ肷��B<br/>
	 * <br/>
	 * @param updDate �ŏI�X�V��
	 */
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}


	/**
	 * �ŏI�X�V�҂��擾����B<br/>
	 * <br/>
	 * @return �ŏI�X�V��
	 */
	public String getUpdUserId() {
		return updUserId;
	}
	
	/**
	 * �ŏI�X�V�҂�ݒ肷��B<br/>
	 * <br/>
	 * @param updUserId �ŏI�X�V��
	 */
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}
	
}
