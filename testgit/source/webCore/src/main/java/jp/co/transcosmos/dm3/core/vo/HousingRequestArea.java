package jp.co.transcosmos.dm3.core.vo;


/**
 * �������N�G�X�g�G���A���.
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
public class HousingRequestArea {

	/** �������N�G�X�gID */
	private String housingRequestId;
	/** �s���{��CD */
	private String prefCd;
	/** �s�撬��CD */
	private String addressCd;

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
	 * �s���{��CD ���擾����B<br/>
	 * <br/>
	 *
	 * @return �s���{��CD
	 */
	public String getPrefCd() {
		return prefCd;
	}

	/**
	 * �s���{��CD ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param prefCd
	 */
	public void setPrefCd(String prefCd) {
		this.prefCd = prefCd;
	}

	/**
	 * �s�撬��CD ���擾����B<br/>
	 * <br/>
	 *
	 * @return �s�撬��CD
	 */
	public String getAddressCd() {
		return addressCd;
	}

	/**
	 * �s�撬��CD ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param addressCd
	 */
	public void setAddressCd(String addressCd) {
		this.addressCd = addressCd;
	}
}
