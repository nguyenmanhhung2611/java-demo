package jp.co.transcosmos.dm3.core.vo;

/**
 * ���W�O���[�v�Ή��\.
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
public class FeatureGroupPage {

	/** ���W�O���[�vID */
	private String featureGroupId;
	/** ���W�y�[�WID */
	private String featurePageId;
	/** �\���� */
	private Integer sortOrder;

	/**
	 * ���W�O���[�vID ���擾����B<br/>
	 * <br/>
	 *
	 * @return ���W�O���[�vID
	 */
	public String getFeatureGroupId() {
		return featureGroupId;
	}

	/**
	 * ���W�O���[�vID ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param featureGroupId
	 */
	public void setFeatureGroupId(String featureGroupId) {
		this.featureGroupId = featureGroupId;
	}

	/**
	 * ���W�y�[�WID ���擾����B<br/>
	 * <br/>
	 *
	 * @return ���W�y�[�WID
	 */
	public String getFeaturePageId() {
		return featurePageId;
	}

	/**
	 * ���W�y�[�WID ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param featurePageId
	 */
	public void setFeaturePageId(String featurePageId) {
		this.featurePageId = featurePageId;
	}

	/**
	 * �\���� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �\����
	 */
	public Integer getSortOrder() {
		return sortOrder;
	}

	/**
	 * �\���� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param sortOrder
	 */
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
}
