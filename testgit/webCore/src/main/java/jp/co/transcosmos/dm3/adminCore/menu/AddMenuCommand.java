package jp.co.transcosmos.dm3.adminCore.menu;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

/**
 * <pre>
 * ���j���[���𐶐����A���N�G�X�g�X�R�[�v�֊i�[���� Command Filter �N���X
 * 
 * ���j���[�K�w�́AURL �̃t�H���_�K�w�ƈˑ��֌W�ɂ���B�@���L�͂��̃V�X�e���ɂ������\�I�� URL �K�w
 * �ɂȂ�B
 *      /�A�v���P�[�V������/��1�K�w/��2�K�w/�ŗL�̃t�H���_��...
 * 
 * ���j���[�y�[�W�̏��́AMenuPageInfo �N���X�̃C���X�^���X�ŊǗ�����A���̃I�u�W�F�N�g���Q�Ƃ��� Map
 * �I�u�W�F�N�g�����j���[�\�����v���p�e�B�imenuInfo�j�Ɋi�[����Ă���B �܂��AMap �� Key �l�́AURL
 * �̑�1�K�w�̒l�Ƀ}�b�s���O����Ă���B
 * 
 * MenuPageInfo �N���X�́A���̃��j���[�y�[�W�̎q�v�f�ƂȂ郁�j���[�J�e�S���[���iMenuCategoryInfo�j
 * ���i�[���� Map �I�u�W�F�N�g��ێ�����B ���j���[�J�e�S���[�����Ǘ����Ă��� Key �l�� URL �Ƃ̓}�b�s���O
 * ����Ă��Ȃ����A���j���[�\���S�̂�ʂ��Ĉ�ӂł���K�v������B
 * 
 * ���j���[�J�e�S���[���iMenuCategoryInfo�j�́A���̃J�e�S���[�Ɋ܂܂��A���j���[Item �iMenuItemInfo
 * �N���X�B�@�e�@�\�ւ̃����N����Ȃǂ����B�j���i�[���� Map �I�u�W�F�N�g��ێ�����B
 * 
 * ���j���[Item ���� Key �l�́AURL �̑�2�K�w�̒l�Ƀ}�b�s���O����Ă���B
 * ���j���[�����\�����Ă���N���X����ʂ��珇�ɕ��ׂ�ƁA���L�̗l�Ȉˑ��֌W�ɂȂ�B
 * 
 *      MenuPageInfo -> MenuCategoryInfo -> MenuItemInfo
 * 
 * ���� Command Filter ���A�ǂ̂悤�Ƀ��N�G�X�g�X�R�[�v�ɏ����i�[���Ă��邩�� handleRequest ���\�b�h
 * �̐������Q�Ƃ��鎖�B
 * 
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.29	Shamaison ���Q�l�ɐV�K�쐬
 *
 * ���ӎ���
 * 
 * </pre>
*/
public class AddMenuCommand implements Command {

	private static final Log log = LogFactory.getLog(AddMenuCommand.class);

	/** ���N�G�X�g�X�R�[�v�Ɋi�[����ۂ� ID (���j���[���) */
	protected String requestScopeMenuInfoId = "gcMenuInfo";
	
	/** ���N�G�X�g�X�R�[�v�Ɋi�[����ۂ� ID (���݂̃��j���[���) */
	protected String requestScopeCurrentId = "gcCurrentMenu";

	/** ���N�G�X�g�X�R�[�v�Ɋi�[����ۂ� ID (���݂̃��j���[Item ��) */
	protected String requestScopeCurrentItemId = "gcCurrentMenuItem";

	/** ���N�G�X�g�X�R�[�v�Ɋi�[����ۂ� ID (�f�t�H���g���[����) */
	protected String requestScopeDefaultRoleId = "gcDefaultRole";

	/** ���j���[�\����� */
	protected Map<String, MenuPageInfo> menuInfo;
	
	/**
	 * �f�t�H���g�A�N�Z�X��<br/>
	 * �e�I�u�W�F�N�g�� roles �v���p�e�B�ɒl���ݒ肳��Ă��Ȃ��ꍇ�A���̃v���p�e�B�ɐݒ肵�����[������
	 * �K�p�����B<br/>
	 * �v���p�e�B�ȗ����ɑS���[�U�[�ɕ\��������ꍇ�A���̃v���p�e�B�ɑS���[�������J���}��؂�Őݒ�
	 * ���Ă����K�v������B<br/> 
	 */
	private String defaultRoles;



	public void setRequestScopeMenuInfoId(String requestScopeMenuInfoId) {
		this.requestScopeMenuInfoId = requestScopeMenuInfoId;
	}

	public void setRequestScopeCurrentId(String requestScopeCurrentId) {
		this.requestScopeCurrentId = requestScopeCurrentId;
	}

	public void setMenuInfo(Map<String, MenuPageInfo> menuInfo) {
		this.menuInfo = menuInfo;
	}

	public void setDefaultRoles(String defaultRoles) {
		this.defaultRoles = defaultRoles;
	}

	
	
	/**
	 * ���j���[�����擾���A���N�G�X�g�X�R�[�v�֊i�[����B<br/>
	 * <br/>
	 * <ul>
	 *   <li>���N�G�X�g URL ����A�v���P�[�V����������菜���B</li>
	 *   <li>���� URL ����̏ꍇ�iURL �� �u/�A�v�����v �̏ꍇ�j�A���j���[���͊i�[���Ȃ��B</li>
	 *   <li>URL �̑�1�K�w����̏ꍇ�iURL �� �u/�A�v����/�v �̏ꍇ�j�A���j���[���͊i�[���Ȃ��B</li>
	 *   <li>URL �̑�1�K�w�����݂���ꍇ�iURL �� �u/�A�v����/��1�K�w�v �̏ꍇ�j�A��1�K�w�̃t�H���_���� key
	 *   �Ƃ��āA���j���[�\�����v���p�e�B�imenuInfo�j�� Map ���� MenuPageInfo �̏����擾����B<br/>
	 *   ���̒l�́A���݂̃��j���[��ʏ��Ƃ��ă��N�G�X�g�X�R�[�v�֊i�[����B</li>
	 *   <li>URL �̑�2�K�w�����݂���ꍇ�iURL �� �u/�A�v����/��1�K�w/��2�K�w�v �̏ꍇ�j�A���̃��j���[�y�[
	 *   �W�Ɋ܂܂�郁�j���[Item �ɁAURL ��2�K�w�Ɏw�肵�� Key�l�ƈ�v���郁�j���[Item �����݂��邩�`�F�b�N����B</li>
	 *   <li>���j���[Item ���擾�ł����ꍇ�A���݂̃��j���[ Item �Ƃ��� Key �l�����N�G�X�g�X�R�[�v�֊i�[����B</li>
	 * </ul>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	 * @return ModelAndView �̃C���X�^���X
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// ���N�G�X�g���ꂽ URL ���擾�@�i�R���e�L�X�g�����������l���擾�j
		String url = request.getRequestURI();

		String contextName = request.getServletContext().getContextPath();
		if (!StringValidateUtil.isEmpty(contextName)){
			url = url.substring(contextName.length());
		}


		// ���������l���ݒ肳��Ă��Ȃ��ꍇ�A���̂܂ܕ��A����B�@�i���j���[�\���ΏۊO�j
		if (StringValidateUtil.isEmpty(url)) return null;


		// �t�H���_�𕪊����A�ŏ��̊K�w�̒l�� menu ID �Ƃ��Ďg�p����B
		// URL �� / ����n�܂�̂ŁA�Q�ڂ̔z��̒l�Ń`�F�b�N����B
		String[] dirs = url.split("/");
		if (dirs.length < 2 || StringValidateUtil.isEmpty(dirs[1])) return null;
		
		log.debug("menu Id is " + dirs[1]);

		// menu ID ���L�[�Ƀ��j���[�y�[�W�����擾����B
		MenuPageInfo menuPageInfo = this.menuInfo.get(dirs[1]);

		
		// ���j���[�y�[�W���� ���j���[ Item �� Map Key �� URL ��2�K�w�ƈ�v����ꍇ�A���݂̃��j���[ Item �Ƃ��Ď擾����B
		String menuItemId = null;

		
		// URL �̑�2�K�w�����݂���ꍇ
		if (dirs.length >= 3 && !StringValidateUtil.isEmpty(dirs[2])) {

			// ��2�K�w�� Key �l�Ƃ��Ď擾
			String itemKey = dirs[2];
			
			// �ꉞ�A���j���[�y�[�W�Ƀ��j���[�J�e�S���[�����݂��鎖���`�F�b�N����B
			if (menuPageInfo != null && menuPageInfo.getMenuCategorys() != null) {

				// ���j���[�y�[�W�Ɋ܂܂�郁�j���[�J�e�S���[�����擾����B
				for (Entry<String, MenuCategoryInfo> ce : menuPageInfo.getMenuCategorys().entrySet()){
					MenuCategoryInfo category = ce.getValue();

					// ���j���[�J�e�S���[�Ƀ��j���[ Item ���܂܂��ꍇ�AURL ��2�K�w�̒l�� Key �Ƃ��Č��݂̃��j���[
					// Item �����擾����B
					if (category.getMenuItems() != null) {
						for (Entry<String, MenuItemInfo> ie : category.getMenuItems().entrySet()){

							// ���j���[Item �́@Key �l�� URL ��2�K�w�ƈ�v����ꍇ
							if (ie.getKey().equals(itemKey)){
								menuItemId = ie.getKey();
								break;
							}
						}
					}
					if (menuItemId != null) break;
				}
			}
		}
		
		// ���̉�ʂŎg�p���郁�j���[�������N�G�X�g�X�R�[�v�֊i�[����B
		request.setAttribute(this.requestScopeMenuInfoId, this.menuInfo);			// ���j���[�\��
		request.setAttribute(this.requestScopeCurrentId, menuPageInfo);				// ���݂̃��j���[�y�[�W
		request.setAttribute(this.requestScopeCurrentItemId, menuItemId);			// ���݂̃��j���[ Item �y�[�W
		request.setAttribute(this.requestScopeDefaultRoleId, this.defaultRoles);	// �f�t�H���g���[��

		return null;
	}

}
