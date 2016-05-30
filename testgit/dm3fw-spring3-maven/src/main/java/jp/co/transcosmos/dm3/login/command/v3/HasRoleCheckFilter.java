package jp.co.transcosmos.dm3.login.command.v3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.login.UserRoleSet;
import jp.co.transcosmos.dm3.login.v3.Authentication;
import jp.co.transcosmos.dm3.servlet.BaseFilter;
import jp.co.transcosmos.dm3.servlet.FilterCommandChain;


/**
 * <pre>
 * V3 �F�ؑΉ�
 * ���[���ɂ��A�N�Z�X���`�F�b�N�R�}���h�i�t�B���^�[�j
 * ���O�C�����[�U�[�̃��[�������擾���A���̃t�B���^�[�� roles �v���p�e�B�ɐݒ肳�ꂽ���[����
 * �ƈ�v���邩���ƍ�����B
 * 
 * �S����            �C����               �C�����e
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.05.07  �V�K�쐬
 * 
 * ���ӎ��� 
 *     �����I�ȏ������e�́AV2 �ƑS�������ł��B
 * </pre>
*/
public class HasRoleCheckFilter extends BaseFilter {
    private static final Log log = LogFactory.getLog(HasRoleCheckFilter.class);

    // ���̃t�B���^�������郍�[�����̃R���N�V����
    private Collection<String> roles;

    // �F�؃��W�b�N�I�u�W�F�N�g
    private Authentication authentication;

	

    /**
     * �ʏ�̃t�B���^�[�Ƃ��Ďg�p����ꍇ�̏���������<br/>
     * Web.xml ����ʏ�̃t�B���^�[�Ƃ��Ďg�p���ꂽ�ꍇ�A�F�ؗp�I�u�W�F�N�g�������I�� DI ����Ȃ�
     * �̂ŁA�t�B���^�[�̏��������\�b�h���� Spring �̃R���e�L�X�g���玩�g�Ŏ擾����B<br/>
     * <br/>
     * @param config �t�B���^�[�̐ݒ���
     * @throws ServletException
     */
    @Override
	public void init(FilterConfig config) throws ServletException {
		super.init(config);

		// �F�؏����N���X�� bean ID �̎擾
        String beanName = config.getInitParameter("authentication");
        if ((beanName == null) || beanName.equals("")) {
        	beanName = "authentication";
        }

		// Spring �� Application Context ���擾
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());

		// Spring �R���e�L�X�g���� �F�؃N���X���擾
		try {
			this.authentication = (Authentication)springContext.getBean(beanName);
		} catch (NoSuchBeanDefinitionException e){
			this.authentication = null;
			log.warn("Authentication bean not found !!!");
		}
    
    
		// �A�N�Z�X�������郍�[�������擾����
		// ���[�����́A�J���}��؂�ŕ����w�肷�鎖���\�B
        String wkRoles = config.getInitParameter("roles");
        this.roles = Arrays.asList(wkRoles.split(",")); 

    }
	


    /**
     * �t�B���^�[�̃��C�����W�b�N<br/>
     * �񋟂��ꂽ�F�ؗp�N���X���g�p���ă��O�C�����[�U�[�̃��[�������擾����B<br/>
     * �擾�������[����񂪁A���̃t�B���^�[�ɐݒ肳�ꂽ���[�����Ƀ}�b�`����ꍇ�A���̃t�B���^<br/>
     * �փ`�F�[������B<br/>
     * �擾�������[����񂪑��݂��Ȃ��ꍇ�A�r���[���� failure �ɂ��ď����𒆒f����B<br/>
     * �i�t�B���^�[�Ƃ��Ďg�p���Ă���ꍇ�́A403 �̃��X�|���X�𕜋A����B�j
     * <br/>
     * @param request HTTP���N�G�X�g
     * @param�@response HTTP���X�|���X
     * @param�@chain �`�F�[���E�I�u�W�F�N�g
     * @return �����_�����O�Ŏg�p���� ModelAndView �I�u�W�F�N�g
     * @throws Exception,�@ServletException
     */
    @Override
	protected void filterAction(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		// ���O�C�����[�U�[�̃��[�������擾
        UserRoleSet roleset = this.authentication.getLoggedInUserRole(request, response);


		// ���[�����擾�ł����ꍇ�͏ƍ�����B
        // ���t����΁A�Ȃɂ������ɏI�����A���t����Ȃ��ꍇ�A���X�|���X��ݒ肵�ă`�F�[���𒆒f ����B
        if (roleset == null) {
        	// �F�؃N���X�̖߂�l�� null �̏ꍇ�A���[���`�F�b�N�ΏۊO�Ƃ��āA�F��OK�Ƃ���B
        	// ����āA���[�����P���������Ă��Ȃ��ꍇ�ł��A��� UserRoleSet ��Ԃ��K�v������B
        	// �����A���̎d�l�͕ύX����\��������܂��B
        	log.warn("Roleset not found - not logged in ? Allowing, because we only" +
                    " want to apply this rolecheck to logged in users - wrap an extra " +
                    "CookieLoginCheckFilter around this when you need a login-only check");
        	
        	chain.doFilter(request, response);
        	return;

        } else if (roleset.hasRole(this.roles)) {
        	// ���[��������̂ŁA��̃t�B���^�[�փ`�F�[������B
            log.info("User has defined role - allowing");
        	chain.doFilter(request, response);
            return;

        } else {
            log.info("User does not have defined role - rejecting");
            
            if (chain instanceof FilterCommandChain) {
            	// �ʏ�́A������̏����B�@���ʂ��Z�b�g���āA�`�F�[���𒆒f����B
            	((FilterCommandChain) chain).setResult(request, new ModelAndView("failure", "neededRole", this.roles));
            } else {
            	// web.xml ����A�ʏ�̃t�B���^�[�Ƃ��Ďg�p���ꂽ�ꍇ�̏���
            	((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        }
	}

    

    // setter�Agetter
    public void setRoles(Collection<String> pRoles) {
        this.roles = pRoles;
    }

    public void setRole(String pRole) {
        this.roles = new ArrayList<String>();
        this.roles.add(pRole);
    }

    public void setAuthentication(Authentication pAuthentication) {
        this.authentication = pAuthentication;
    }

}
