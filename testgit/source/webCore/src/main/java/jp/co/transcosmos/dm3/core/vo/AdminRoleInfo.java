package jp.co.transcosmos.dm3.core.vo;

import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserRoleInterface;

/**
 * �Ǘ��Ҍ������N���X.
 * <p>
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.28	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �ʃJ�X�^�}�C�Y�Ōp�������\��������̂ŁA���ڃC���X�^���X�𐶐����Ȃ����B<br/>
 * �C���X�^���X���擾����ꍇ�́AValueObjectFactory ����擾���鎖�B<br/>
 * 
 */
public class AdminRoleInfo implements AdminUserRoleInterface {

	/** �Ǘ��҃��[�U�[ID */
	private String adminUserId;
	/** ���[��ID */
	private String roleId;
	
	
	
	/**
	 * �Ǘ��҃��[�U�[ID ���擾����B<br/>
	 * <br/>
	 * @return �Ǘ��҃��[�U�[ID
	 */
	public String getAdminUserId() {
		return adminUserId;
	}
	
	/**
	 * �Ǘ��҃��[�U�[ID ��ݒ肷��B<br/>
	 * <br/>
	 * @param adminUserId �Ǘ��҃��[�U�[ID
	 */
	public void setAdminUserId(String adminUserId) {
		this.adminUserId = adminUserId;
	}
	
	/**
	 * AdminUserRoleInterface �̎������\�b�h<br/>
	 * ���[�U�[�����e�i���X�@�\�́A���̃��\�b�h���o�R���ă��[��ID �ɑΉ�����Ǘ����[�U�[ID ��ݒ肷��B<br/>
	 * <br/>
	 * @param �Ǘ����[�U�[ID
	 */
	@Override
	public void setUserId(String userId) {
		this.adminUserId = userId;
	}

	/**
	 * AdminUserRoleInterface �̎������\�b�h<br/>
	 * ���[�U�[�����e�i���X�@�\�́A���̃��\�b�h���o�R���ă��[��ID ���擾����B<br/>
	 * <br/>
	 * @return ���[��ID
	 */
	@Override
	public String getRoleId() {
		return roleId;
	}
	
	/**
	 * AdminUserRoleInterface �̎������\�b�h<br/>
	 * ���[�U�[�����e�i���X�@�\�́A���̃��\�b�h���o�R���ă��[��ID ��ݒ肷��B<br/>
	 * <br/>
	 * @param roleId ���[��ID
	 */
	@Override
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
