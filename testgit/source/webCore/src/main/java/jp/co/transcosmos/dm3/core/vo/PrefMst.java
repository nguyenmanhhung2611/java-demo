package jp.co.transcosmos.dm3.core.vo;

import java.util.Date;

/**
 * �s���{���}�X�^�N���X.
 * <p>
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * �ד��@��		2006.12.19	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �ʃJ�X�^�}�C�Y�Ōp�������\��������̂ŁA���ڃC���X�^���X�𐶐����Ȃ����B<br/>
 * �C���X�^���X���擾����ꍇ�́AValueObjectFactory ����擾���鎖�B<br/>
 * 
 */
public class PrefMst {

	/** �s���{��CD */
	private String prefCd;
	/** �s���{���� */
	private String prefName;
	/** �s���{�������[�}�� */
	private String rPrefName;
	/** �n��CD */
	private String areaCd;
	/** �\���� */
	private Integer sortOrder;
	/** �o�^�� */
	private Date insDate;
	/** �o�^�� */
	private String insUserId;
	/** �ŏI�X�V�� */
	private Date updDate;
	/** �ŏI�X�V�� */
	private String updUserId;

 

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
	 * @param prefCd
	 */
	public void setPrefCd(String prefCd) {
		this.prefCd = prefCd;
	}

	/**
	 * �s���{�������擾����B<br/>
	 * <br/>
	 * @return �s���{����
	 */
	public String getPrefName() {
		return prefName;
	}

	/**
	 * �s���{������ݒ肷��B<br/>
	 * <br/>
	 * @param prefName �s���{����
	 */
	public void setPrefName(String prefName) {
		this.prefName = prefName;
	}

	/**
	 * �s���{�������[�}�����擾����B<br/>
	 * <br/>
	 * @return �s���{�������[�}
	 */
	public String getRPrefName() {
		return rPrefName;
	}
	
	/**
	 * �s���{�������[�}�����擾����B<br/>
	 * <br/>
	 * @param rPrefName �s���{�������[�}��
	 */
	public void setRPrefName(String rPrefName) {
		this.rPrefName = rPrefName;
	}
	
	/**
	 * �n��CD ���擾����B<br/>
	 * <br/>
	 * @return �n��CD
	 */
	public String getAreaCd() {
		return areaCd;
	}
	
	/**
	 * �n��CD ��ݒ肷��B<br/>
	 * <br/>
	 * @param areaCd �n��CD
	 */
	public void setAreaCd(String areaCd) {
		this.areaCd = areaCd;
	}
	
	/**
	 * �\�������擾����B<br/>
	 * <br/>
	 * @return �\����
	 */
	public Integer getSortOrder() {
		return sortOrder;
	}

	/**
	 * �\������ݒ肷��B<br/>
	 * <br/>
	 * @param sortOrder �\����
	 */
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
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
