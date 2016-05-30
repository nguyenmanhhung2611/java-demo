package jp.co.transcosmos.dm3.login.command.v3;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.v3.Authentication;
import jp.co.transcosmos.dm3.retry.RequestRetryParams;
import jp.co.transcosmos.dm3.servlet.BaseFilter;
import jp.co.transcosmos.dm3.servlet.FilterCommandChain;
import jp.co.transcosmos.dm3.utils.ServletUtils;
import jp.co.transcosmos.dm3.utils.SimpleExpressionLanguage;

/**
 * <pre>
 * V3 �F�ؑΉ�
 * ���O�C���F�؃`�F�b�N�t�B���^�[
 * authentication �v���p�e�B�Ŏw�肳�ꂽ�N���X���g�p���ĔF�؍ς̃`�F�b�N���s��
 * 
 * �S����            �C����               �C�����e
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.05  �V�K�쐬
 * H.Mizuno  2013.04.11  �F�؃`�F�b�N�ƁA���[�U�[���擾�𕪗�
 * 
 * ���ӎ��� 
 *     �����I�ȏ������e�́AV2 �ƑS�������ł��B
 * </pre>
*/
public class LoginCheckFilter extends BaseFilter {
    private static final Log log = LogFactory.getLog(LoginCheckFilter.class);

	// �F�؃��W�b�N�I�u�W�F�N�g
    private Authentication authentication;

    // ���N�G�X�g�p�����[�^���Z�b�V�����Ɋi�[����ۂ̃L�[��
    private String savedRequestSessionParameterPrefix = "savedRequest:";

    // ���_�C���N�gURL�ɖ��ߍ��ށA���_�C���N�g�L�[�̃p�����[�^��
    // ���̒l�ƁA�Z�b�V�����̒l���ƍ����A��₂���Ă���ꍇ�̓G���[�ɂ���
    private String savedRequestRequestParameter = "redirectKey";

    // �L���b�V���̖������iture=����������j
    private boolean addNoCacheHeaders = true;

    // �ʏ퓮��̏ꍇ�A�F�؃G���[����������ƁAView ���� failure �ɂ��ă����_�����O�w�ɕ��A����
    // ���܂��A�ړI�� Command �N���X�͎��s����Ȃ��B 
    // ���̃t���O�� true �ɂ���ƁA���O�C���G���[���������Ă��A���O�C�����̓Z�b�V�����ɍ���Ȃ����A
    // �ړI�� Command �N���X�����s���Ă����B
    // ���O�C�����Ă��Ȃ��Ă���ʂ̉{���͉\�����A���O�C�����Ă��Ȃ��Ƌ@�\�����������ꍇ�ȂǂɎg�p
    // ����B
    private boolean softLogin = false;


    
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
	}



	/**
     * �t�B���^�[�̃��C�����W�b�N<br/>
     * �񋟂��ꂽ�F�ؗp�N���X���g�p���ă��O�C���ς��̃`�F�b�N���s���B<br/>
     * ���O�C���ς̏ꍇ�A���̃t�B���^�[�փ`�F�[������B<br/>
     * ���O�C���ς̃`�F�b�N���G���[�̏ꍇ�A���O�C���σ`�F�b�N�G���[���������s����B<br/>
     * �Ȃ��A���O�C���ς݃`�F�b�N�����s�����ꍇ�ł��AsoftLogin ���L���ȏꍇ�A���̃t�B���^�[�փ`�F�[������B<br/>
     * <br/>
     * @param request HTTP���N�G�X�g
     * @param�@response HTTP���X�|���X
     * @param�@chain �`�F�[���E�I�u�W�F�N�g
     * @throws Exception,�@ServletException
     */
	@Override
	protected void filterAction(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.debug("Executing the LoginCheckFilter");
		
        // �F�ؗp�I�u�W�F�N�g���ݒ肳��Ă��Ȃ��ꍇ�A��O���グ�ďI���i�V�X�e���G���[�j
		if (this.authentication == null) {
        	throw new RuntimeException("authentication property not setting!!"); 
        }

		// �L���b�V���𖳌�������w�b�_���̐���
        if (this.addNoCacheHeaders) {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "No-cache");
            response.setDateHeader("Expires", 1000);
        }

        // ���O�C���ς݂̏ꍇ�́A���̃R�}���h�փ`�F�[������B
        LoginUser user = authentication.checkLoggedIn(request, response);
        if (user != null) {
            log.info("User: " + user + "(userId=" + user.getUserId() +
                    ") already logged in. URI=" + request.getRequestURI());
       		chain.doFilter(reassociateSavedRequestIfAvailable(request), response);
            return;
    	}

        // ���O�C�����Ă��Ȃ��ꍇ�̏���
        if (isSoftLogin()) {
            // �\�t�g���O�C�����L���ȏꍇ�i�F�؂��Ă��Ȃ��Ă��A��ʂ�\������ꍇ�j�̏���
            doSoftLogin(authentication, request, response, chain);
        } else {
            // �ʏ�̔F�؃G���[����
        	failedLogin(authentication, request, response, chain);
        }
	}


	
    /**
     * �\�t�g���O�C������<br/>
     * ���O�C���σ`�F�b�N�͎��s���Ă��邪�A���̂܂܃`�F�[�����p������B<br/>
     * ���̏����́A���O�C�������s���Ă��Ă��A�p�[�\�i���C�Y����Ă��Ȃ���ʂ�\������ꍇ�Ɏg�p����B<br/>
     * <br/>
     * @param request HTTP���N�G�X�g
     * @param�@response HTTP���X�|���X
     * @param�@chain �`�F�[���E�I�u�W�F�N�g
     * @throws IOException,�@ServletException
     */
    protected void doSoftLogin(Authentication authentication, HttpServletRequest req, 
            HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("Soft-login enabled: forwarding request with no logged-in user");
        chain.doFilter(reassociateSavedRequestIfAvailable(req), response);
    }

	

    /**
     * ���O�C���σ`�F�b�N�G���[����<br/>
     * ���O�A�E�g�������s���A���N�G�X�g�E�p�����[�^���Z�b�V�����Ɋi�[����B<br/>
     * �`�F�[���悪�t���[�����[�N�̃R�}���h�N���X�̏ꍇ�AView �����@failure �ɐݒ肵�ă`�F�[���𒆒f����B<br/>
     * �`�F�[���悪�ʏ�̃t�B���^�[�̏ꍇ�A�R���e�L�X�g�E�p�����[�^�Ŏw�肳��Ă��� URL �փ��_�C���N�g����B<br/>
     * <br/>
     * @param request HTTP���N�G�X�g
     * @param�@response HTTP���X�|���X
     * @param�@chain �`�F�[���E�I�u�W�F�N�g
     * @throws IOException,�@ServletException
     */
    protected void failedLogin(Authentication authentication, HttpServletRequest request, 
            HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

    	// ���O�A�E�g���������s
        authentication.logout(request, response);
        log.info("Login failed: saving request in session and forwarding to the login-form");

        
        // ���N�G�X�g�����Z�b�V�����Ɋi�[���A�ς��� Key �𖄂ߍ��񂾃��_�C���N�g�� URL ���쐬����B
        // ���O�C����Ɍ��̃��N�G�X�g��ɑJ�ڂ������ꍇ�A�ȉ��̐ݒ�����킹�čs�����B
        //  �E���_�C���N�g��URL���A���O�C����ʂ� hidden �p�����[�^ redirectURL �ɐݒ肷��B
        //  �EURL �}�b�s���O�ŁAsuccess ���̃r���[�����Aredirect:###!redirectURL### �ɐݒ肷��B
        String redirectURL = saveRequest(request);
        Map<String,Object> model = new HashMap<String,Object>(); 
        model.put("redirectURL", redirectURL);


        // �����ŁACookie ���� userType�@���擾���Amodel �ɒǉ����Ă���B
        // ��ŁA���̃p�����[�^���ǂ̗l�Ɏg�p���Ă���̂��l������B
        
        
        if (chain instanceof FilterCommandChain) {
        	// �ʏ�́A������̏����B�@���ʂ��Z�b�g���āA�`�F�[���𒆒f����B
        	// �������AuserType�@�ɂ���ẮAfailureWithUserType �� View ���ɂ��Ă���B
        	((FilterCommandChain) chain).setResult(request, new ModelAndView("failure", model));
        } else {
        	// web.xml ����A�ʏ�̃t�B���^�[�Ƃ��Ďg�p���ꂽ�ꍇ�̏���
            String loginFormURL = this.getLoginFormURL();
            if (loginFormURL != null) {
                String replaced = SimpleExpressionLanguage.evaluateWebExpressionPatternString(
                        loginFormURL, request, response, getContext(), model, true, 
                        response.getCharacterEncoding(), "");
                response.sendRedirect(ServletUtils.fixPartialURL(replaced, request));
            } else {
                log.warn("Failed login, not inside spring, and no loginFormURL defined - sending 403");
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        }
    }

    
    
    /**
     * �R���e�L�X�g�E�p�����[�^����A���O�C�� URL ���擾<br/>
     * <br/>
     * @return ���O�C����ʂ� URL
     */
    protected String getLoginFormURL() {
        return getInitParameter("loginFormURL", null);
    }


    
    /**
     * ���N�G�X�g�E�p�����[�^���Z�b�V�����Ɋi�[����B<br/>
     * ���N�G�X�g�E�p�����[�^���Z�b�V�����Ɋi�[���A���̍ہA���j�[�N�ȃZ�b�V�����L�[�𐶐�����B<br/>
     * <br/>
     * @param�@request HTTP���N�G�X�g
     * @return �Z�b�V�����L�[��g�ݍ��񂾃��_�C���N�g�� URL�B�@���_�C���N�g��́A���̃��N�G�X�g��URL�B
     * @throws IOException, ServletException
     */
    protected String saveRequest(HttpServletRequest request) throws IOException, ServletException {
        return RequestRetryParams.saveRequest(request, this.savedRequestSessionParameterPrefix, 
                this.savedRequestRequestParameter);
    }

    
    /**
     * �Z�b�V�������烊�N�G�X�g�p�����[�^�𕜌�����B<br/>
     * �Z�b�V�����Ɋi�[���ꂽ���N�G�X�g�p�����[�^�𕜌�����B�@���̍ہA�Z�b�V�����L�[�̑Ó������`�F�b�N����B<br/>
     * �i�p�����[�^��ₑ΍�j<br/>
     * <br/>
     * @param�@request HTTP���N�G�X�g
     * @return �Z�b�V��������擾�����AHTTP ���N�G�X�g�I�u�W�F�N�g
     */
    protected HttpServletRequest reassociateSavedRequestIfAvailable(HttpServletRequest req) {
        return RequestRetryParams.reassociateSavedRequestIfAvailable(req, 
                this.savedRequestSessionParameterPrefix,
                this.savedRequestRequestParameter);
    }

    
    
	// setter�Agetter
    public void setSavedRequestRequestParameter(String pSavedRequestRequestParameter) {
        this.savedRequestRequestParameter = pSavedRequestRequestParameter;
    }

    public void setSavedRequestSessionParameterPrefix(String pSavedRequestSessionParameterPrefix) {
        this.savedRequestSessionParameterPrefix = pSavedRequestSessionParameterPrefix;
    }

    public void setAddNoCacheHeaders(boolean pAddNoCacheHeaders) {
        this.addNoCacheHeaders = pAddNoCacheHeaders;
    }

    public void setAuthentication(Authentication pAuthentication) {
        this.authentication = pAuthentication;
    }

    public void setSoftLogin(boolean pSoftLogin) {
        this.softLogin = pSoftLogin;
    }

    public boolean isSoftLogin() throws IOException, ServletException {
        return this.softLogin;
    }
}
