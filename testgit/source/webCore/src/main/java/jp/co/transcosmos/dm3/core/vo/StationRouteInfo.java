package jp.co.transcosmos.dm3.core.vo;

/**
 * <pre>
 * �w�E�H���Ή��\�N���X.
 * 
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 *
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.18	�V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �ʃJ�X�^�}�C�Y�Ōp�������\��������̂ŁA���ڃC���X�^���X�𐶐����Ȃ����B<br/>
 * �C���X�^���X���擾����ꍇ�́AValueObjectFactory ����擾���鎖�B<br/>
 * 
 */

public class StationRouteInfo {

	/** �H��CD */
	private String routeCd;
	/** �wCD */
	private String stationCd;
	/** �\���� */
	private Integer sortOrder;
	
	
	
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
	 * <br/>
	 * @param routeCd �H��CD
	 */
	public void setRouteCd(String routeCd) {
		this.routeCd = routeCd;
	}

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

}
