package jp.co.transcosmos.dm3.core.vo;


/**
 * ���������ۑ��Ԏ����.
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
public class SaveSrchLayoutData {

	/** ��������ID */
	private String srchId;
	/** �Ԏ�CD */
	private String layoutCd;

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
