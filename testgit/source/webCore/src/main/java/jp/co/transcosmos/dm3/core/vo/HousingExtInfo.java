package jp.co.transcosmos.dm3.core.vo;


/**
 * �����g���������.
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
public class HousingExtInfo {

	/** �V�X�e������CD */
	private String sysHousingCd;
	/** �J�e�S���[�� */
	private String category;
	/** Key �� */
	private String keyName;
	/** �l */
	private String dataValue;

	/**
	 * �V�X�e������CD ���擾����B<br/>
	 * <br/>
	 *
	 * @return �V�X�e������CD
	 */
	public String getSysHousingCd() {
		return sysHousingCd;
	}

	/**
	 * �V�X�e������CD ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 */
	public void setSysHousingCd(String sysHousingCd) {
		this.sysHousingCd = sysHousingCd;
	}

	/**
	 * �J�e�S���[�� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �J�e�S���[��
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * �J�e�S���[�� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param category
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Key �� ���擾����B<br/>
	 * <br/>
	 *
	 * @return Key ��
	 */
	public String getKeyName() {
		return keyName;
	}

	/**
	 * Key �� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param keyName
	 */
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	/**
	 * �l ���擾����B<br/>
	 * <br/>
	 *
	 * @return �l
	 */
	public String getDataValue() {
		return dataValue;
	}

	/**
	 * �l ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param dataValue
	 */
	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}
}
