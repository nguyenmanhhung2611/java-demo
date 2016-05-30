package jp.co.transcosmos.dm3.core.vo;


/**
 * �����X�e�[�^�X���.
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
public class HousingStatusInfo {

	/** �V�X�e������CD */
	private String sysHousingCd;
	/** ����J�t���O */
	private String hiddenFlg;

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
	 * ����J�t���O ���擾����B<br/>
	 * <br/>
	 *
	 * @return ����J�t���O
	 */
	public String getHiddenFlg() {
		return hiddenFlg;
	}

	/**
	 * ����J�t���O ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param hiddenFlg
	 */
	public void setHiddenFlg(String hiddenFlg) {
		this.hiddenFlg = hiddenFlg;
	}
}
