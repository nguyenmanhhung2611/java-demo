package jp.co.transcosmos.dm3.core.vo;


/**
 * �������N�G�X�g�H�����.
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
public class HousingReqRoute {

	/** �������N�G�X�gID */
	private String housingRequestId;
	/** �H��CD */
	private String routeCd;

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
	 * �H��CD ���擾����B<br/>
	 * <br/>
	 *
	 * @return �H��CD
	 */
	public String getRouteCd() {
		return routeCd;
	}

	/**
	 * �H��CD ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param routeCd
	 */
	public void setRouteCd(String routeCd) {
		this.routeCd = routeCd;
	}
}
