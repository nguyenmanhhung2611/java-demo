package jp.co.transcosmos.dm3.corePana.model.adminUser.form;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserFormFactory;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserSearchCsvForm;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserSearchForm;

public class PanaAdminUserFormFactory extends AdminUserFormFactory {

	/**
	 * PanaAdminUserFormFactory �̃C���X�^���X���擾����B<br/>
	 * Spring �̃R���e�L�X�g����A adminUserFormFactory �Œ�`���ꂽ PanaAdminUserFormFactory ��
	 * �C���X�^���X���擾����B<br/>
	 * �擾�����C���X�^���X�́AadminUserFormFactory ���p�������g���N���X�����A�����ꍇ������B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return PanaAdminUserFormFactory�A�܂��͌p�����Ċg�������N���X�̃C���X�^���X
	 */
	public static PanaAdminUserFormFactory getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (PanaAdminUserFormFactory)springContext.getBean(PanaAdminUserFormFactory.FACTORY_BEAN_ID);
	}



	/**
	 * �Ǘ����[�U�[�̌������ʁA����ь����������i�[������ PanaAdminUserSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� PanaAdminUserSearchForm �C���X�^���X
	 */
	@Override
	public AdminUserSearchForm createUserSearchForm(){
		return new PanaAdminUserSearchForm(this.lengthUtils);
	}



	/**
	 * CSV �o�͗p�̊Ǘ����[�U�[�̌������ʁA����ь����������i�[������ AdminUserSearchCsvForm
	 * �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� AdminUserSearchCsvForm �C���X�^���X
	 */
	@Override
	public AdminUserSearchCsvForm createUserSearchCsvForm(){
		AdminUserSearchCsvForm adminUserSearchCsvForm = super.createUserSearchCsvForm();
		adminUserSearchCsvForm.setRowsPerPage(Integer.MAX_VALUE);
		return adminUserSearchCsvForm;
	}

}
