package jp.co.transcosmos.dm3.adminCore.menu;

import java.util.LinkedHashMap;


/**
 * <pre>
 * ���j���[�J�e�S���[���
 * ���j���[�y�[�W�ɕ\�����郁�j���[�J�e�S���[���Ǘ�����B
 * ���j���[�y�[�W���̎q�v�f�ƂȂ�N���X�B
 * 
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.29	Shamaison ���Q�l�ɐV�K�쐬
 *
 * ���ӎ���
 * 
 * </pre>
*/
public class MenuCategoryInfo {

	/** ���j���[�J�e�S���[���� */
	private String menuItemCategoryName;

	/**
	 *  ���j���[ Item<br/>
	 *  ���j���[�J�e�S���[���ɕ\�����郁�j���[���ڏ��� Map �I�u�W�F�N�g<br/>
	*/
	private LinkedHashMap<String, MenuItemInfo> menuItems;

	/**
	 * ���j���[�J�e�S���[�\���������<br/>
	 * �ȗ������ꍇ�AAddMenuCommand �N���X�� defaultRoles �v���p�e�B�ɐݒ肵�����[�����K�p�����B<br/>
	 *�@���[������ݒ肵���ꍇ�A�ݒ肵�� Role �������[�U�[�̂ݕ\�������B<br/>
	 *�@�����̃��[������ݒ肷��ꍇ�́A�J���}��؂�ŋL�q����B<br\>
	*/
	private String roles;

	
	
	public String getMenuItemCategoryName() {
		return menuItemCategoryName;
	}

	public void setMenuItemCategoryName(String menuItemCategoryName) {
		this.menuItemCategoryName = menuItemCategoryName;
	}

	public LinkedHashMap<String, MenuItemInfo> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(LinkedHashMap<String, MenuItemInfo> menuItems) {
		this.menuItems = menuItems;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

}
