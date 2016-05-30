package jp.co.transcosmos.dm3.core.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.login.UserRoleSet;
import jp.co.transcosmos.dm3.login.v3.Authentication;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * �Ǘ��T�C�g�փ��O�C�����Ă��郆�[�U�[�����擾����.
 * <p>
 * ���j���[����w�b�_�[���ȂǁA�ꕔ�̋��� JSP �ł͒��ڃZ�b�V�������烍�O�C�����[�U�[�����Q�Ƃ��Ă���
 * �ꍇ�����邪�A�t���[�����[�N�̔F�؏�����ύX�����ꍇ�ɑΉ��ł��Ȃ��̂ŁA���ʂȗ��R����������A
 * ���̃N���X���g�p���Ēl���擾���鎖�B<br/>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.20	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class AdminLoginUserUtils {

	/** ���̃��[�e�B���e �� Bean ID */
	protected static String FACTORY_BEAN_ID = "loginUserUtils";

	/** �t���[�����[�N�̔F�؏��� */
	protected Authentication authentication;
	
	
	
	/**
	 * �t���[�����[�N�̔F�؏�����ݒ肷��B<br/>
	 * <br/>
	 * @param authentication �t���[�����[�N�̔F�؏���
	 */
	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}



	/**
	 * AdminLoginUserUtils �̃C���X�^���X���擾����B<br/>
	 * Spring �̃R���e�L�X�g����A loginUserUtils �Œ�`���ꂽ AdminLoginUserUtils ��
	 * �C���X�^���X���擾����B<br/>
	 * �擾�����C���X�^���X�́AAdminLoginUserUtils ���p�������g���N���X�����A�����ꍇ������B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return AdminLoginUserUtils�A�܂��͌p�����Ċg�������N���X�̃C���X�^���X
	 */
	public static AdminLoginUserUtils getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (AdminLoginUserUtils)springContext.getBean(AdminLoginUserUtils.FACTORY_BEAN_ID);
	}

	
	
	/**
	 * ���݃��O�C�����Ă��郆�[�U�[�����擾����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	 * @return ���O�C�����[�U�[���
	 */
	public AdminUserInterface getLoginUserInfo(HttpServletRequest request, HttpServletResponse response){
		return (AdminUserInterface)this.authentication.getLoggedInUser(request, response);
	}
	
	
	
	/**
	 * ���݃��O�C�����Ă��郆�[�U�[�̌��������擾����B<br/>
	 * ���������邩���`�F�b�N����ɂ́A�߂�l�� UserRoleSet �I�u�W�F�N�g�� hasRole(���[����)�����s����B<br/>
	 * ����������ꍇ�� true �𕜋A����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	 * 
	 * @return ���O�C�����[�U�[���[�����
	 */
	public UserRoleSet getLoginUserRole(HttpServletRequest request, HttpServletResponse response){
		return this.authentication.getLoggedInUserRole(request, response);
	}

}
