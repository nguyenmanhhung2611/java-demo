package jp.co.transcosmos.dm3.core.model.csvMaster.dao;

public interface StationSortOrderUpdateDAO {

	/**
	 * �S����Ѓ}�X�^�̕��я��S���X�V
	 * �@���я��{�S�����CD���ɕ��ׂ����̂ɑ΂���
	 * �@rownum�𗘗p����1�`���ɂ������я��ōX�V����
	 * �@���������ɂȂ��Ă������я��͋l�܂�
	 */
	public void updateRrMstSortOrder(Object[] objUpdData);

	
	
	/**
	 * �H���}�X�^�̕��я��S���X�V
	 * �@���я��{�H��CD���ɕ��ׂ����̂ɑ΂���
	 * �@rownum�𗘗p����1�`���ɂ������я��ōX�V����
	 * �@���������ɂȂ��Ă������я��͋l�܂�
	*/
	public void updateRouteMstSortOrder(Object[] objUpdData);

}
