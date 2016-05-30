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
public class NewsTarget {

	/** ���m�点�ԍ� */
	private String newsId;
	
	/** ���[�U�[ID */
	private String userId;

	

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
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
