package jp.co.transcosmos.dm3.login.logging.v3;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.FilterConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import jp.co.transcosmos.dm3.login.v3.Authentication;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.servlet.BaseFilter;
import jp.co.transcosmos.dm3.utils.SimpleExpressionLanguage;


/**
 * <pre>
 * V3 �F�ؑΉ�
 * ���X�|���X�w�b�_�[�ǉ��t�B���^�[
 * 
 * �S����            �C����               �C�����e
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.11  �V�K�쐬
 * 
 * ���ӎ��� 
 *     �����I�ȏ������e�́AV2 �ƑS�������ł��B
 * </pre>
*/
public class AddResponseHeadersFilter extends BaseFilter {
    private static final Log log = LogFactory.getLog(AddResponseHeadersFilter.class);

	// �F�؃��W�b�N�I�u�W�F�N�g
    private Authentication authentication;

    // �ǉ�����A�w�b�_�[��
    private String headerName;

    // �ǉ�����w�b�_�[�̏o�̓p�^�[��
    private String headerPattern;



    /**
     * �ʏ�̃t�B���^�[�Ƃ��Ďg�p����ꍇ�̏���������<br/>
     * Spring �� Application Context ���擾���A�F�؃��W�b�N�I�u�W�F�N�g���擾����B<br/>
     * <br/>
     * @param config �t�B���^�[�ݒ���
     * @throws ServletException
     */
	@Override
    public void init(FilterConfig config) throws ServletException {
		super.init(config);

		// �ǉ�����w�b�_�[���̎擾
        this.headerName = config.getInitParameter("headerName");
        if ((this.headerName == null) || this.headerName.equals("")) {
            this.headerName = "X-LoginId";
        }
        
		// �ǉ�����w�b�_�[���̏o�̓p�^�[���̎擾
        this.headerPattern = config.getInitParameter("headerPattern");
        if ((this.headerPattern == null) || this.headerPattern.equals("")) {
            this.headerPattern = "###$user.loginId###";
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
     * �t�B���^�[�̃��C������<br/>
     * �w�b�_�[�����쐬���A���X�|���X�w�b�_�ɒǉ�����B<br/>
     * <br/>
     * @param config �t�B���^�[�ݒ���
     * @throws ServletException
     */
	@Override
	protected void filterAction(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
        // Build the header value and add it to the response
        String headerValue = SimpleExpressionLanguage.evaluateWebExpressionPatternString(
        		this.headerPattern, request, response, getContext(), 
                buildExtraParamsMap((HttpServletRequest) request, (HttpServletResponse) response), 
                false, response.getCharacterEncoding(), "");
        if ((headerValue != null) && !headerValue.equals("")) {
            response.addHeader(this.headerName, headerValue);
        }
        chain.doFilter(request, response);
	}


	
	/**
     * ���O�C�����̎擾����<br/>
     * �F�ؗp�N���X���g�p���ă��O�C�������擾����B<br/>
     * �F�ؗp�N���X���ݒ肳��Ă��Ȃ��ꍇ�A�����O�C�����Ɠ��������Ƃ���B
     * <br/>
     * @param config �t�B���^�[�ݒ���
     * @throws ServletException
     */
	private Map<String,Object> buildExtraParamsMap(HttpServletRequest request, HttpServletResponse response) {

        Map<String,Object> out = new HashMap<String,Object>();

    	if (this.authentication == null){
            out.put("user", null);
            out.put("$user", null);
        } else {
            LoginUser user = this.authentication.getLoggedInUser(request, response);
            out.put("user", user);
            out.put("$user", user);
        }
        
        return out;
    }



	// setter�Agetter
	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	public void setHeaderPattern(String headerPattern) {
		this.headerPattern = headerPattern;
	}

}
