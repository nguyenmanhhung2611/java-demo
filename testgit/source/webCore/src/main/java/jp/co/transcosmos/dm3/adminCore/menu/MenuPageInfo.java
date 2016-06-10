package jp.co.transcosmos.dm3.adminCore.menu;

import java.util.LinkedHashMap;

/**
 * <pre>
 * ���j���[�y�[�W���
 * ���j���[�Ǘ����̍ŏ�ʂɈʒu����I�u�W�F�N�g
 * ���j���[���́A���̃I�u�W�F�N�g�� LinkedHashMap �ŊǗ�����B�@�Ǘ���ʏ�i�̃��j���[�^�u�́A
 * ���� Map ��񂩂�o�͂����B
 * 
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.29	Shamaison ���Q�l�ɐV�K�쐬
 *
 * ���ӎ���
 * 
 * </pre>
*/
public class MenuPageInfo {

	/**
	 *  ���j���[��ʖ���<br/>
	 *  ���j���[��ʖ��́B�@�y�[�W�^�C�g����A���j���[��ʑI���^�u�̕\���Ɏg�p�����B<br/>
	 *  �����AMenuPageTitleImage�AMenuTabImage ���ݒ肳��Ă���ꍇ�A�ݒ肳��Ă���摜�t�@�C����\������B<br/>
	*/
	private String menuPageName;

	/**
	 *  ���j���[��ʃ^�C�g���摜<br/>
	 *  �ȗ��\�B�@�T�C�h���j���[�ɕ\������郁�j���[��ʖ��̉摜��ݒ肷��B<br/>
	 *  �����AMenuTabOnImage ���ݒ肳��Ă���ꍇ�A�ݒ肳��Ă���摜�t�@�C����\������B<br/>
	 *  �摜�p�X�ɂ́ACommonParameters#setResourceRootUrl() �Ɏw�肵���p�X�ȍ~���L�q����B<br/> 
	*/
	private String menuPageTitleIamge;

	/**
	 * ���j���[�^�u�摜�t�@�C�����i�A�N�e�B�u�ȏ�ԗp�j<br/>
	 * �ȗ��\�B�@�ȗ������ꍇ�A �摜�̕ς��� MenuPageName ���\�������B<br/>
	 *  �摜�p�X�ɂ́ACommonParameters#setResourceRootUrl() �Ɏw�肵���p�X�ȍ~���L�q����B<br/> 
	 */
	private String menuTabOnImage;

	/**
	 * ���j���[�^�u�摜�t�@�C�����i��A�N�e�B�u�ȏ�ԗp�j<br/>
	 * �ȗ��\�B�@�ȗ������ꍇ�A �摜�̕ς��� MenuPageName ���\�������B<br/>
	 *  �摜�p�X�ɂ́ACommonParameters#setResourceRootUrl() �Ɏw�肵���p�X�ȍ~���L�q����B<br/> 
	 */
	private String menuTabOffImage;

	/**
	 * ���j���[�J�e�S�����<br/>
	 * ���̃��j���[�^�u�ɑ����郁�j���[�J�e�S���� Map ���B<br/>
	 * Key = ���j���[�J�e�S���[ID�AValue ���j���[�J�e�S���[�I�u�W�F�N�g<br/>
	*/
	private LinkedHashMap<String, MenuCategoryInfo> menuCategorys;

	/**
	 * ���j���[�\���������<br/>
	 * �ȗ������ꍇ�AAddMenuCommand �N���X�� defaultRoles �v���p�e�B�ɐݒ肵�����[�����K�p�����B<br/>
	 *�@���[������ݒ肵���ꍇ�A�ݒ肵�� Role �������[�U�[�̂ݕ\�������B<br/>
	 *�@�����̃��[������ݒ肷��ꍇ�́A�J���}��؂�ŋL�q����B<br\>
	*/
	private String roles;

	

	public String getMenuPageName() {
		return this.menuPageName;
	}

	public void setMenuPageName(String menuPageName) {
		this.menuPageName = menuPageName;
	}

	public String getMenuPageTitleIamge() {
		return this.menuPageTitleIamge;
	}

	public void setMenuPageTitleIamge(String menuPageTitleIamge) {
		this.menuPageTitleIamge = menuPageTitleIamge;
	}

	public String getMenuTabOnImage() {
		return this.menuTabOnImage;
	}

	public void setMenuTabOnImage(String menuTabOnImage) {
		this.menuTabOnImage = menuTabOnImage;
	}

	public String getMenuTabOffImage() {
		return this.menuTabOffImage;
	}

	public void setMenuTabOffImage(String menuTabOffImage) {
		this.menuTabOffImage = menuTabOffImage;
	}

	public LinkedHashMap<String, MenuCategoryInfo> getMenuCategorys() {
		return this.menuCategorys;
	}

	public void setMenuCategorys(LinkedHashMap<String, MenuCategoryInfo> menuCategorys) {
		this.menuCategorys = menuCategorys;
	}

	public String getRoles() {
		return this.roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

}
