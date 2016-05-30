package jp.co.transcosmos.dm3.core.vo;


/**
 * �������N�G�X�g�Ŋ��w���.
 * <p>
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 * <p>
 *
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
public class HousingReqStation {

	/** �������N�G�X�gID */
	private String housingRequestId;
	/** �wCD */
	private String stationCd;

	/**
	 * �������N�G�X�gID ���擾����B<br/>
	 * <br/>
	 *
	 * @return �������N�G�X�gID
	 */
	public String getHousingRequestId() {
		return housingRequestId;
	}

	/**
	 * �������N�G�X�gID ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param housingRequestId
	 */
	public void setHousingRequestId(String housingRequestId) {
		this.housingRequestId = housingRequestId;
	}

	/**
	 * �wCD ���擾����B<br/>
	 * <br/>
	 *
	 * @return �wCD
	 */
	public String getStationCd() {
		return stationCd;
	}

	/**
	 * �wCD ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param stationCd
	 */
	public void setStationCd(String stationCd) {
		this.stationCd = stationCd;
	}
}
