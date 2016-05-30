package jp.co.transcosmos.dm3.core.vo;

/**
 * ���m�点���J����N���X.
 * <p>
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.06	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �ʃJ�X�^�}�C�Y�Ōp�������\��������̂ŁA���ڃC���X�^���X�𐶐����Ȃ����B<br/>
 * �C���X�^���X���擾����ꍇ�́AValueObjectFactory ����擾���鎖�B<br/>
 * 
 */
public class InformationTarget {

	/** ���m�点�ԍ� */
	private String informationNo;
	
	/** ���[�U�[ID */
	private String userId;

	/**
	 * ���m�点�ԍ���ݒ肷��B<br/>
	 * <br/>
	 * @param informationNo ���m�点�ԍ�
	 */
	public String getInformationNo() {
		return informationNo;
	}

	/**
	 * ���m�点�ԍ����擾����B<br/>
	 * <br/>
	 * @return ���m�点�ԍ�
	 */
	public void setInformationNo(String informationNo) {
		this.informationNo = informationNo;
	}

	/**
	 * ���[�U�[ID��ݒ肷��B<br/>
	 * <br/>
	 * @param userId ���[�U�[ID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * ���[�U�[ID���擾����B<br/>
	 * <br/>
	 * @return ���[�U�[ID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
