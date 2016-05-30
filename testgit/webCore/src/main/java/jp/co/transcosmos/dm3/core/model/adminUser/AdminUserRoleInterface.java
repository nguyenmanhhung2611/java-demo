package jp.co.transcosmos.dm3.core.model.adminUser;


/**
 * <pre>
 * �Ǘ����[�U�[���Ǘ��o���[�I�u�W�F�N�g�p�C���^�[�t�F�[�X
 * �Ǘ����[�U�[�̃��[�����Ǘ����� DAO �̃o���[�I�u�W�F�N�g�́A���̃C���^�[�t�F�[�X���������鎖�B
 * �Ǘ����[�U�[�����e�i���X�@�\�́A���̃C���^�[�t�F�[�X�z���Ƀ��[��������s���B
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.28	�V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public interface AdminUserRoleInterface {

	/** �Ǘ����[�U�[ID ��ݒ肷��B<br/>
	 * ���̃��\�b�h�̐ݒ�悪�A���[�����ɑΉ�����Ǘ����[�U�[�h�c�̃t�B�[���h�i�L�[��j�ƂȂ�B<br/>
	 * <br/>
	 * param �Ǘ����[�U�[ID
	 */
	public void setUserId(String userId);
	
	
	/**
	 * �Ǘ����[�U�[�̃��[��ID ���擾����B<br/>
	 * ���̃��\�b�h�̐ݒ�悪�A�V�X�e���������Ǘ��Ŏg�p���郍�[��ID �̃t�B�[���h�ƂȂ�B<br/>
	 * <br/>
	 * @return ���[��ID
	 */
	public String getRoleId();
	
	/**
	 * �Ǘ����[�U�[�̃��[��ID ��ݒ肷��B<br/>
	 * ���̃��\�b�h�̐ݒ�悪�A�V�X�e���������Ǘ��Ŏg�p���郍�[��ID �̃t�B�[���h�ƂȂ�B<br/>
	 * <br/>
	 * @param ���[��ID
	 */
	public void setRoleId(String roleId);
}
