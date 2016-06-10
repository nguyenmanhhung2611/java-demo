package jp.co.transcosmos.dm3.core.vo;


/**
 * �����ݔ����.
 * <p>
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 * <p>
 *
 * <pre>
 * �S����        �C����        �C�����e
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.03.10	�V�K�쐬
 * H.Mizuno		2015.03.18	�ݔ��R�����g�폜
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �ʃJ�X�^�}�C�Y�Ōp�������\��������̂ŁA���ڃC���X�^���X�𐶐����Ȃ����B<br/>
 * �C���X�^���X���擾����ꍇ�́AValueObjectFactory ����擾���鎖�B<br/>
 *
 */
public class HousingEquipInfo {

	/** �V�X�e������CD */
	private String sysHousingCd;
	/** �ݔ�CD */
	private String equipCd;

	
	
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
	 * �ݔ�CD ���擾����B<br/>
	 * <br/>
	 *
	 * @return �ݔ�CD
	 */
	public String getEquipCd() {
		return equipCd;
	}

	/**
	 * �ݔ�CD ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param equipCd
	 */
	public void setEquipCd(String equipCd) {
		this.equipCd = equipCd;
	}

}
