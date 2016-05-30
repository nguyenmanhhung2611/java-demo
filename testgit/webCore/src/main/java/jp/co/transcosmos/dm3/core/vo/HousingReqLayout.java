package jp.co.transcosmos.dm3.core.vo;


/**
 * �������N�G�X�g�Ԏ���.
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
public class HousingReqLayout {

	/** �������N�G�X�gID */
	private String housingRequestId;
	/** �Ԏ�CD */
	private String layoutCd;

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
	 * �Ԏ�CD ���擾����B<br/>
	 * <br/>
	 *
	 * @return �Ԏ�CD
	 */
	public String getLayoutCd() {
		return layoutCd;
	}

	/**
	 * �Ԏ�CD ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param layoutCd
	 */
	public void setLayoutCd(String layoutCd) {
		this.layoutCd = layoutCd;
	}
}
