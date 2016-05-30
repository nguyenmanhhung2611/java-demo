package jp.co.transcosmos.dm3.core.vo;


/**
 * ���������ۑ��������������.
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
public class SaveSrchPartData {

	/** ��������ID */
	private String srchId;
	/** ����������CD */
	private String partSrchCd;

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
	 * ����������CD ���擾����B<br/>
	 * <br/>
	 *
	 * @return ����������CD
	 */
	public String getPartSrchCd() {
		return partSrchCd;
	}

	/**
	 * ����������CD ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param partSrchCd
	 */
	public void setPartSrchCd(String partSrchCd) {
		this.partSrchCd = partSrchCd;
	}
}
