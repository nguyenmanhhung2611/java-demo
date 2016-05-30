package jp.co.transcosmos.dm3.webFront.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.v3.Authentication;
import jp.co.transcosmos.dm3.servlet.BaseFilter;



/**
 * �������摜�̃`�F�b�N�t�B���^�[.
 * �}�C�y�[�W�ւ̔F�؂��������Ă��邩���`�F�b�N���A�F�؍ς̏ꍇ�AChain ������s����B<br/>
 * �����F�؂��������Ă��Ȃ��ꍇ�A�X�e�[�^�X 403 �𕜋A����B<br/>
 * �摜�t�H���_�̔F�؃`�F�b�N�́A�ʏ�̃}�C�y�[�W�F�؃`�F�b�N�t�B���^�[�Ƃ͈قȂ�A�������O�C���͍s��Ȃ��B<br/>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.05.19	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class ImgLoginChkFilter extends BaseFilter {

	// �F�؃��W�b�N�I�u�W�F�N�g
    private Authentication authentication;

	
	
	/**
	 * Filter �̏���������<br/>
	 * web.xml ����p�����[�^���ݒ肳�ꂽ�ꍇ�A���̒l�� Filter ������������B<br/>
	 * <br/>
	 * @param config �p�����[�^�I�u�W�F�N�g
	 * @exception ServletException
	 * 
	 */
    @Override
	public void init(FilterConfig config) throws ServletException {

		// �F�؏����N���X�� bean ID �̎擾
        String beanName = config.getInitParameter("authentication");
        if ((beanName == null) || beanName.equals("")) {
        	beanName = "authentication";
        }

		// Spring �� Application Context ���擾
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());

		// Spring �R���e�L�X�g���� �F�؃N���X���擾
		this.authentication = (Authentication)springContext.getBean(beanName);
        
    }
	


    /**
     * Filter �̎����I�ȏ���<br/>
     * ���O�C���F�؂���Ă��Ȃ��ꍇ�A�X�e�[�^�X 403 �𕜋A����B<br/>
     * �F�؍σ`�F�b�N�̍ہA�������O�C���͍s��Ȃ��B<br/>
     * <br/>
     * @param request HTTP ���N�G�X�g
     * @param response HTTP ���X�|���X
     * @param chain Chain �I�u�W�F�N�g
     * 
     * @exception IOException
     * @exception ServletException
     */
	@Override
	protected void filterAction(HttpServletRequest request,	HttpServletResponse response, FilterChain chain)
			throws IOException,	ServletException {

		// ���ʂȃL���b�V���R���g���[���͂��̃t�B���^�[������͍s��Ȃ��B
		// �L���b�V���R���g���[���́AApache ���̊��ݒ�ɂčs������O��Ƃ���B

        // ���O�C���ς݂̏ꍇ�́A���̃R�}���h�փ`�F�[������B
		// �Ȃ��A�摜�̔F�؃t�B���^�[�ł́AcheckLoggedIn() �ł͂Ȃ��AgetLoggedInUser() ���g�p����B
		// checkLoggedIn() �́A�������O�C�������s�����\��������Aweb.xml ���� Filter �Ƃ��Ďg�p
		// ���鎖�͂ł��Ȃ��B
        LoginUser user = authentication.getLoggedInUser(request, response);

		if (user != null){
			// �F�؏�񂪎擾�ł����ꍇ�AChain ������s����B
			chain.doFilter(request, response);
		} else {
			// �F�ؐ悪�擾�ł��Ȃ��ꍇ�Arespose �����Z�b�g���ăX�e�[�^�X 403 �𕜋A����B
			response.reset();
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}

	}

}
