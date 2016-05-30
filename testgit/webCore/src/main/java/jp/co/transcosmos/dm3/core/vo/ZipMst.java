package jp.co.transcosmos.dm3.core.vo;


/**
 * �X�֔ԍ��}�X�^.
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
public class ZipMst {

	/** �X�֔ԍ� */
	private String zip;
	/** �s���{��CD */
	private String prefCd;
	/** �s�撬��CD */
	private String addressCd;

	/**
	 * �X�֔ԍ� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �X�֔ԍ�
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * �X�֔ԍ� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param zip
	 */
	public void setZip(String zip) {
		this.zip = zip;
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
