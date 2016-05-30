package jp.co.transcosmos.dm3.login.tag;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.transcosmos.dm3.login.UserRoleSet;
import jp.co.transcosmos.dm3.login.v3.Authentication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.config.ValidScope;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;



/**
 * <pre>
 * Ver 3 �F�ؑΉ��AVelocity �p���[���`�F�b�N����
 * dm3login ���ʃ^�O���C�u������ Velocity �ŁB
 * velocity.tols.xml �́Arequest �X�R�[�v�ɓo�^���Ďg�p���鎖�B
 *
 * �S����            �C����               �C�����e
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.18  �V�K�쐬
 *
 * </pre>
*/
@DefaultKey("dm3login")
@ValidScope(Scope.REQUEST)
public class HasRoleVelocity {
    private static final Log log = LogFactory.getLog(HasRoleVelocity.class);

    // ���[�����Ǘ����Ă���A�Z�b�V������ Key �iV1�AV2 �F�ؗp�j
    private String rolesetSessionParameter = "loggedInUserRoleset";

    // HTTP ���N�G�X�g�I�u�W�F�N�g
    protected HttpServletRequest request;

    // HTTP ���X�|���X�I�u�W�F�N�g
    protected HttpServletResponse response;

    // �T�[�u���b�g�E�R���e�L�X�g
    protected ServletContext servletContext;

    
    /**
     * ���ʊ֐�����������<br/>
     * ���̃��\�b�h���`����ƁAVelocity ���� ToolContext �����鎖���ł���B<br />
     * ToolContext �ɂ́AHttpRequest ��AServletContext ���i�[���Ă���̂ŁA�K�v��<br />
     * ����΁A���̃��\�b�h���`����B<br />
     * <br/>
     * @param values Velocity ����n�����v���p�e�B���
     */
    protected void configure(Map<?,?> values)
    {
		log.debug("velocity dm3login init");

		// ToolContext ���擾���A���[�J���R���e�L�X�g����AWeb �֘A�̃I�u�W�F�N�g���擾�B
		ToolContext toolContext = (ToolContext)values.get("velocityContext");
    	this.servletContext = (ServletContext)toolContext.get("servletContext");
    	this.request = (HttpServletRequest)toolContext.get("request");
    	this.response = (HttpServletResponse)toolContext.get("response");
    }

    
    
    /**
     * ���[���`�F�b�N����<br/>
     * �F�ؗp Bean ID ���ȗ����Ă���̂ŁAV1�AV2 �F�ؐ�p�B<br />
     * <br/>
     * @param pRoleName �`�F�b�N�ΏۂƂȂ郍�[�����i�J���}��؂�ŕ����w��j
     * @return ���[���������t�������ꍇ true �A���t����Ȃ��ꍇ�@false
     */
	public boolean hasRole(String pRoleName){
		return hasRole(pRoleName, null);
	}



    /**
     * ���[���`�F�b�N����<br/>
     * �F�ؗp Bean ID ���ȗ����ꂽ�ꍇ�AV1�AV2 �F�؂Ƃ��ăZ�b�V�������烍�[��������������B<br />
     * �F�ؗp Bean ID ���w�肳��Ă���ꍇ�ASpring ����F�ؗp�I�u�W�F�N�g���擾���A�F�؃`�F�b�N��<br />
     * �s���B<br />
     * <br/>
     * @param pRoleName �`�F�b�N�ΏۂƂȂ郍�[�����i�J���}��؂�ŕ����w��j
     * @param pAuthId �F�ؗp�I�u�W�F�N�g�� Bean ID
     * @return ���[���������t�������ꍇ true �A���t����Ȃ��ꍇ�@false
     */
	public boolean hasRole(String pRoleName, String pAuthId){
		log.debug("Velocity has role check start. roleName:" +  pRoleName + "authId:" + pAuthId);

		UserRoleSet roleset;
		if (pAuthId != null && pAuthId.length() != 0){
			// Ver 3  �F��
			roleset = getRoleSetFromAuthenticate(pAuthId);
		} else {
			// Ver 1�A2 �F��
			roleset = getRoleSetFromRequest();
		}

		// ���̔���́AHasRoleCheckCommand �Ɣ�ׂ�ƁA�኱�A�������قȂ�B
		// �iHasRoleCheckCommand �́Anull �� true ���肷��B�j
		// �����AHasRoleCheckCommand �����A���̔����ɍ��킹��\��������B
		if (roleset != null && roleset.hasRole(getRoles(pRoleName))) {
			log.debug("role name is found");
			return true;
        } else {
			log.debug("role name is not found");
            return false;
        }
	}



    /**
     * ���[���I�u�W�F�N�g�擾�����iV1�AV2 �p�j<br/>
     * �Z�b�V����������A���[���I�u�W�F�N�g���擾���ĕ��A����B<br />
     * <br/>
     * @return ���[���I�u�W�F�N�g
     */
    protected UserRoleSet getRoleSetFromRequest() {
		log.debug("check is v1, v2 authentication");

        HttpSession session = this.request.getSession();
        if (session != null) {
            return (UserRoleSet) session.getAttribute(this.rolesetSessionParameter);
        } else {
            log.warn("WARNING: Roleset not found in session scope - returning NULL");
            return null;
        }
    }

	
    
    /**
     * ���[���I�u�W�F�N�g�擾�����iV3 �p�j<br/>
     * Spring ����擾�����F�ؗp�I�u�W�F�N�g���g�p���ă��[���`�F�b�N���s���B<br />
     * �F�ؗp�I�u�W�F�N�g�����A���郍�[���I�u�W�F�N�g�𕜋A����B<br/>
     * @param pAuthId �F�ؗp�I�u�W�F�N�g�� Bean ID
     * @return ���[���I�u�W�F�N�g
     */
	protected UserRoleSet getRoleSetFromAuthenticate(String pAuthId){
		log.debug("check is v3 authentication");

		// Spring �� Application Context ���擾
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(this.servletContext);
		
		// Spring �R���e�L�X�g���� �F�؃N���X���擾
		try {
			Authentication authentication = (Authentication)springContext.getBean(pAuthId);
			if (authentication == null) return null;

			return authentication.getLoggedInUserRole(this.request, this.response);
			
		} catch (NoSuchBeanDefinitionException e){
			log.warn("Authentication bean not found !!!");
		}
		return null;
    }


	
    /**
     * ���[�����̕�������<br/>
     * �w�肳�ꂽ���[�������A�J���}�i,�j�L���ŕ������A�R���N�V�����E�I�u�W�F�N�g�𕜋A����B<br />
     * @param ���[�����i�e���v���[�g����n���ꂽ���[�����j
     * @return ���X�g�����ꂽ���[����
     */
	protected Set<String> getRoles(String pRoleName) {
        // Tokenize the role names
        StringTokenizer st = new StringTokenizer(pRoleName, ",");
        Set<String> roles = new HashSet<String>();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (token != null) {
                roles.add(token);
            }
        }
        return roles;
    }

}
