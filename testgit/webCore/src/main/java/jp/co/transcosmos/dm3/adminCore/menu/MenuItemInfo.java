package jp.co.transcosmos.dm3.adminCore.menu;


/**
 * <pre>
 * ���j���[�A�C�e�����
 * ���j���[�J�e�S���[���ɕ\������ Link �����Ǘ�����B
 * ���j���[�J�e�S�����̎q�v�f�ƂȂ�N���X�B
 * 
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.29	Shamaison ���Q�l�ɐV�K�쐬
 *
 * ���ӎ���
 * 
 * </pre>
*/
public class MenuItemInfo {

	/** ���j���[���ڕ\������ */
	private String menuItemName;
	
	/** ���j���[���ڂ̃����N�� */
	private String menuItemUrl;

	/**
	 * ���j���[ Item �\���������<br/>
	 * �ȗ������ꍇ�AAddMenuCommand �N���X�� defaultRoles �v���p�e�B�ɐݒ肵�����[�����K�p�����B<br/>
	 *�@���[������ݒ肵���ꍇ�A�ݒ肵�� Role �������[�U�[�̂ݕ\�������B<br/>
	 *�@�����̃��[������ݒ肷��ꍇ�́A�J���}��؂�ŋL�q����B<br\>
	*/
	private String roles;



	public String getMenuItemName() {
		return menuItemName;
	}

	public void setMenuItemName(String menuItemName) {
		this.menuItemName = menuItemName;
	}

	public String getMenuItemUrl() {
		return menuItemUrl;
	}

	public void setMenuItemUrl(String menuItemUrl) {
		this.menuItemUrl = menuItemUrl;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

}
