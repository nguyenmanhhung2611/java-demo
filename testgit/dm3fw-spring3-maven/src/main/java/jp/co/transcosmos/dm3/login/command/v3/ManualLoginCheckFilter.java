package jp.co.transcosmos.dm3.login.command.v3;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.login.v3.Authentication;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.retry.RequestRetryParams;
import jp.co.transcosmos.dm3.servlet.BaseFilter;
import jp.co.transcosmos.dm3.servlet.FilterCommandChain;
import jp.co.transcosmos.dm3.utils.ServletUtils;
import jp.co.transcosmos.dm3.utils.SimpleExpressionLanguage;



/**
 * <pre>
 * V3 �F�ؑΉ�
 * �蓮���O�C���̃`�F�b�N�t�B���^�[
 * �����I�ȃ��O�C���������s���������`�F�b�N����B
 * ���̃t�B���^�[�́A��{�I�� AutoLoginAuth �Ƃ̑g�ݍ��킹�Ŏg�p���A�������O�C���̏ꍇ�ɁA
 * �A�N�Z�X�𐧌��������ꍇ�Ɏg�p����B
 * �i�Ⴆ�΁A�l���̓��͉�ʂ́A���m�ȃ��O�C�����K�v�ȏꍇ�Ȃǁj
 * 
 * �S����            �C����               �C�����e
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.05  �V�K�쐬
 * H.Mizuno  2013.04.11  �F�؃`�F�b�N�ƁA���[�U�[���擾�𕪗�
 * H.Mizuno  2013.05.10  Servlet Filter �Ŏg�p�����ꍇ�A���O�C�������Ƀ��_�C���N�g�悪�n��Ȃ�
 *                       �����C��
 * 
 * ���ӎ��� 
 *     �����I�ȏ������e�́AV2 �Ɠ����ł����AServlet Filter �Ń��_�C���N�g���̏����������ύX�B
 * </pre>
*/
public class ManualLoginCheckFilter extends BaseFilter {
    private static final Log log = LogFactory.getLog(ManualLoginCheckFilter.class);

    // �F�؃��W�b�N�I�u�W�F�N�g�B�@�ʏ�́AAUtoLoginAuth �i�������O�C���j���g�p����B
    // �^�́AAutoLoginAuth �̕������S�����A���삵���F�؃N���X�ɂ��g�����������Ȃ�̂ŁA
    // Authentication �C���^�[�t�F�[�X���g�p���Ă���B
    protected Authentication authentication;

    // true �̏ꍇ�A���X�|���X�̃L���b�V���𖳌��ɂ���
    protected boolean addNoCacheHeaders = true;

	// �蓮���O�C���̃`�F�b�N�p�g�[�N����
    protected String manualLoginTokenName = "manualLoginToken";

    // ���_�C���N�g�p�̃L�[
    // �p�r����Ń`�F�b�N����B�@�i�K�v�����c���ł��Ă��Ȃ��ׁB�j
    private String savedRequestSessionParameterPrefix = "savedRequest:";
    private String savedRequestRequestParameter = "redirectKey";

    // web.xml �� Filter �Ŏg�p���ꂽ�ꍇ�̃��_�C���N�g�� URL
    private String loginFormURL;

    

    /**
     * �ʏ�̃t�B���^�[�Ƃ��Ďg�p����ꍇ�̏���������<br/>
     * Web.xml ����ʏ�̃t�B���^�[�Ƃ��Ďg�p���ꂽ�ꍇ�A�F�ؗp�I�u�W�F�N�g�������I�� DI ����Ȃ�
     * �̂ŁA�t�B���^�[�̏��������\�b�h���� Spring �̃R���e�L�X�g���玩�g�Ŏ擾����B<br/>
     * �܂��A�R���e�L�X�g�E�p�����[�^����A���O�C����ʂ� URL ���擾����B<br/>
     * <br/>
     * @param config �t�B���^�[�̐ݒ���
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig config) throws ServletException {
    	super.init(config);

    	// web.xml �̃R���e�L�X�g�E�p�����[�^�Őݒ肳��Ă���ꍇ�ɃG���[���̃��_�C���N�g���ݒ肷��B
    	String loginFormURL = config.getInitParameter("loginFormURL");
        if (loginFormURL != null) {
            this.loginFormURL = loginFormURL;
        }

		// �F�؏����N���X�� bean ID �̎擾
        String beanName = config.getInitParameter("authentication");
        if ((beanName == null) || beanName.equals("")) {
        	beanName = "authentication";
        }

		// Spring �� Application Context ���擾
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());

		// �R���e�L�X�g�E�p�����[�^����A�F�؃N���X�� Bean�@ID�@���擾
		try {
			this.authentication = (Authentication)springContext.getBean(beanName);
		} catch (NoSuchBeanDefinitionException e){
			this.authentication = null;
			log.warn("Authentication bean not found !!!");
		}
    }

    
    
    /**
     * �㏈��<br/>
     * �v���p�e�B�̃��Z�b�g<br/>
     */
    public void destroy() {
        this.loginFormURL = null;
    }

    
    
    /**
     * �t�B���^�[�̃��C�����W�b�N<br/>
     * �Z�b�V�����ɁAmanualLoginTokenName �Ŏw�肳�ꂽ�l�����݂��邩���`�F�b�N���A���݂����ꍇ�A<br/>
     * �蓮�Ń��O�C���������Ƃ݂Ȃ��B�@�蓮���O�C�����Ă��Ȃ��ꍇ�A���O�C����ʂ֗U������B<br/>
     * ���̃g�[�N���́AAutoLoginAuth �ŁA�����I�ȃ��O�C�����s�������ɒl���ݒ肳��A���O�A�E�g����<br/>
     * �폜�����B<br/>
     * <br/>
     * @param request HTTP���N�G�X�g
     * @param�@response HTTP���X�|���X
     * @param�@chain �`�F�[���E�I�u�W�F�N�g
     * @throws IOException,�@ServletException
     */
	@Override
	protected void filterAction(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		log.debug("Executing the ManualLoginCheckFilter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // �L���b�V���������ȏꍇ�p�̃w�b�_�ݒ�
        if (this.addNoCacheHeaders) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setHeader("Pragma", "No-cache");
            httpResponse.setHeader("Cache-Control", "No-cache");
            httpResponse.setDateHeader("Expires", 1000);
        }

        if (this.manualLoginTokenName != null) {
            HttpSession session = httpRequest.getSession(false);
            if ((session == null) || (session.getAttribute(this.manualLoginTokenName) == null)) {
                // �蓮���O�C���̃g�[�N�����Z�b�V�������猩�t����Ȃ��ꍇ
            	noManualLoginTokenFound(httpRequest, response, chain);
                return;
            }
        }
        // �蓮���O�C���̃g�[�N�����Z�b�V�������猩�t�������ꍇ
        manualLoginTokenFound(httpRequest, response, chain);
	}



    /**
     * �蓮���O�C�������Ă����ꍇ�̏���<br/>
     * ���̃t�B���^�[�փ`�F�[������B<br/>
     * <br/>
     * @param request HTTP���N�G�X�g
     * @param�@response HTTP���X�|���X
     * @param�@chain �`�F�[���E�I�u�W�F�N�g
     * @throws IOException,�@ServletException
     */
    protected void manualLoginTokenFound(HttpServletRequest request, HttpServletResponse response, 
            FilterChain chain) throws IOException, ServletException {

    	// ���̃t�B���^�[�ɏ������p������
    	LoginUser userBean = this.authentication.getLoggedInUser(request, response);
        Object userId = userBean.getUserId();
        log.info("Manual login check succeeded for user: " + userBean + " (userId=" + userId + ")");
        chain.doFilter(reassociateSavedRequestIfAvailable(request), response);

    }



    /**
     * �蓮���O�C�������Ă��Ȃ��ꍇ�̏���<br/>
     * �`�F�[���悪�t���[�����[�N�̃R�}���h�N���X�̏ꍇ�A�r���[���� failure �ɐݒ肵�ď����𒆒f����B<br/>
     * �`�F�[���悪�ʏ�̃t�B���^�[�̏ꍇ�A�R���e�L�X�g�E�p�����[�^�Ŏ擾�����@URL �Ƀ��_�C���N�g����B<br/>
     * <br/>
     * @param request HTTP���N�G�X�g
     * @param�@response HTTP���X�|���X
     * @param�@chain �`�F�[���E�I�u�W�F�N�g
     * @throws IOException,�@ServletException
     */
    protected void noManualLoginTokenFound(HttpServletRequest request, HttpServletResponse response, 
            FilterChain chain) throws IOException, ServletException {

    	LoginUser userBean = this.authentication.getLoggedInUser(request, response);
    	if (userBean != null) {
    		Object userId = userBean.getUserId();
    		log.info("Manual login check failed for user: " + userBean + " (userId=" + userId + 
    				"): saving request in session and forwarding to the login-form");
    	} else {
    		log.info("Manual login check failed for user: not logined");
    	}
    	
        String redirectURL = saveRequest(request);
        Map<String,Object> model = new HashMap<String,Object>(); 
        model.put("redirectURL", redirectURL);
        
        
        if (chain instanceof FilterCommandChain) {
            // �`�F�[���悪�t���[�����[�N�̃R�}���h�̏ꍇ�Afailure �̃��X�|���X�𕜋A���ď����𒆒f����
        	((FilterCommandChain) chain).setResult(request, new ModelAndView("failure", model));

        } else if (this.loginFormURL != null) {
        	// web.xml ����A�ʏ�̃t�B���^�[�Ƃ��Ďg�p���ꂽ�ꍇ�̏���
        	// �w�肳�ꂽ URL �փ��_�C���N�g����
            String replaced = SimpleExpressionLanguage.evaluateWebExpressionPatternString(
                    loginFormURL, request, response, getContext(), model, true, 
                    response.getCharacterEncoding(), "");
            response.sendRedirect(ServletUtils.fixPartialURL(replaced, request));

        } else {
        	// ��ѐ悪�w�肳��Ă��Ȃ��̂ŃV�X�e���G���[��ʂ�
        	log.warn("Failed login, not inside spring, and no loginFormURL defined - sending 403");
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN);
        }
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
    protected HttpServletRequest reassociateSavedRequestIfAvailable(HttpServletRequest request) {
        return RequestRetryParams.reassociateSavedRequestIfAvailable(request, 
                this.savedRequestSessionParameterPrefix,
                this.savedRequestRequestParameter);
    }

    
	
    // setter�Agetter
    public void setManualLoginTokenName(String manualLoginTokenName) {
        this.manualLoginTokenName = manualLoginTokenName;
    }
	
	public void setAuthentication(Authentication pAuthentication) {
        this.authentication = pAuthentication;
    }
	
    public void setSavedRequestRequestParameter(String pSavedRequestRequestParameter) {
        this.savedRequestRequestParameter = pSavedRequestRequestParameter;
    }

    public void setSavedRequestSessionParameterPrefix(String pSavedRequestSessionParameterPrefix) {
        this.savedRequestSessionParameterPrefix = pSavedRequestSessionParameterPrefix;
    }
	
    public void setAddNoCacheHeaders(boolean pAddNoCacheHeaders) {
        this.addNoCacheHeaders = pAddNoCacheHeaders;
    }
}
