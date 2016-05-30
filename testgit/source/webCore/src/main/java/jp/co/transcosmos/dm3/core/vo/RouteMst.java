package jp.co.transcosmos.dm3.core.vo;

import java.util.Date;

/**
 * �H���}�X�^�N���X.
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
public class RouteMst {

	/** �H��CD */
	private String routeCd;
	/** �H���� */
	private String routeName;
	/** �H�����E�J�b�R�t */
	private String routeNameFull;
	/** �H�����E�S����Еt */
	private String routeNameRr;
	/** �S�����CD */
	private String rrCd;
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
	 * �H��CD ���擾����B<br/>
	 * <br/>
	 * @return �H��CD
	 */
	public String getRouteCd() {
		return routeCd;
	}
	
	/**
	 * �H��CD ��ݒ肷��B<br/>
	 * @param routeCd �H��CD
	 */
	public void setRouteCd(String routeCd) {
		this.routeCd = routeCd;
	}

	/**
	 * �H���� ���擾����B<br/>
	 * <br/>
	 * @return �H����
	 */
	public String getRouteName() {
		return routeName;
	}
	
	/**
	 * �H���� ��ݒ肷��B<br/>
	 * <br/>
	 * @param routeName �H����
	 */
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	/**
	 * �H�����E�J�b�R�t��ݒ肷��B<br/>
	 * <br/>
	 * @return �H�����E�J�b�R�t
	 */
	public String getRouteNameFull() {
		return routeNameFull;
	}
	
	/**
	 * �H�����E�J�b�R�t���擾����B<br/>
	 * <br/>
	 * @param routeNameFull �H�����E�J�b�R�t
	 */
	public void setRouteNameFull(String routeNameFull) {
		this.routeNameFull = routeNameFull;
	}
	
	/**
	 * �H�����E�S����Еt���擾����B<br/>
	 * <br/>
	 * @return �H�����E�S����Еt
	 */
	public String getRouteNameRr() {
		return routeNameRr;
	}
	
	/**
	 * �H�����E�S����Еt��ݒ肷��B<br/>
	 * <br/>
	 * @param routeNameRr �H�����E�S����Еt
	 */
	public void setRouteNameRr(String routeNameRr) {
		this.routeNameRr = routeNameRr;
	}

	/**
	 * �S�����CD ���擾����B<br/>
	 * <br/>
	 * @return �S�����CD
	 */
	public String getRrCd() {
		return rrCd;
	}
	
	/**
	 * �S�����CD ��ݒ肷��B<br/>
	 * <br/>
	 * @param rrCd �S�����CD
	 */
	public void setRrCd(String rrCd) {
		this.rrCd = rrCd;
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
