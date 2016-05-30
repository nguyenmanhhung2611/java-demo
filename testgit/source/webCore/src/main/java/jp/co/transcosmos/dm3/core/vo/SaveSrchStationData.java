package jp.co.transcosmos.dm3.core.vo;


/**
 * ���������ۑ��Ŋ��w���.
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
public class SaveSrchStationData {

	/** ��������ID */
	private String srchId;
	/** �wCD */
	private String stationCd;

	/**
	 * ��������ID ���擾����B<br/>
	 * <br/>
	 *
	 * @return ��������ID
	 */
	public String getSrchId() {
		return srchId;
	}

	/**
	 * ��������ID ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param srchId
	 */
	public void setSrchId(String srchId) {
		this.srchId = srchId;
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
