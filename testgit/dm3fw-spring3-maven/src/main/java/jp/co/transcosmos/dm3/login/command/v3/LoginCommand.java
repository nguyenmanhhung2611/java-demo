package jp.co.transcosmos.dm3.login.command.v3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.form.v3.DefaultLoginForm;
import jp.co.transcosmos.dm3.login.form.v3.LoginForm;
import jp.co.transcosmos.dm3.login.support.SessionIdResetter;
import jp.co.transcosmos.dm3.login.v3.Authentication;
import jp.co.transcosmos.dm3.login.v3.LockException;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * <pre>
 * Ver 3 �F�ؑΉ��p���O�C���R�}���h�N���X
 * ���N�G�X�g�p�����[�^�̃o���f�[�V�����`�F�b�N���s���AAuthentication �v���p�e�B�̃N���X���g�p
 * ���ă��O�C���������s���B
 *
 * �S����            �C����               �C�����e
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.05  �V�K�쐬
 * H.Mizuno  2013.04.19  �݊����ׁ̈Avalidationerros.jsh �����̎d�l�ɖ߂����ׂ̏C��
 * H.Mizuno  2015.02.24  �A�J�E���g���b�N�Ή�
 * H.Mizuno  2015.06.22  ���O�C���������ɃZ�b�V����ID ��؂�ւ���@�\��ǉ�
 *
 * </pre>
*/
public class LoginCommand implements Command {

	private static final Log log = LogFactory.getLog(LoginCommand.class);

	// �F�؏����N���X
	protected Authentication authentication;
	
	// ���_�C���N�g�� URL �p�����[�^��
	protected String redirectURLParameter = "redirectURL";

	// ���O�C���t�H�[���̃I�u�W�F�N�g
	// �o���f�[�V�������W�b�N�̊g������LoginForm ��ς���ꍇ�ASpring ���� DI ����B
	// AutoLoginAuth ���g�p����ꍇ�A���̃v���p�e�B���A AutoLoginForm�@�ɐݒ肷��B
	protected LoginForm loginForm = new DefaultLoginForm();

// 2015.06.22 H.Mizuno ���O�A�E�g���̃Z�b�V�����j���Ή� start
 	// ���O�C����ɃZ�b�V����ID ��؂�ւ��Ȃ��ꍇ�� false ��ݒ肷��B �i�f�t�H���g true�j
	// �Z�b�V�����I�u�W�F�N�g�̒l�͐V�����Z�b�V�����Ɉ����p�����̂ŁA�ʏ�͕ύX����K�v�������B
	// ��������̖�肪���������ꍇ�ɖ���������B
    private boolean useSessionIdReset;

    // �Z�b�V���� ID �����Z�b�g����I�u�W�F�N�g
    // SessionIdResetter �N���X�́A���Z�b�V�����̒l��V�Z�b�V�����Ɉ����p�����A�����A���p��
    // ���I�u�W�F�N�g�𐧌��������ꍇ�Ȃǂ́A���̃v���p�e�B�ɐݒ肷��I�u�W�F�N�g��ύX����B
 	protected SessionIdResetter sessionIdResetter = new SessionIdResetter();

 	/**
 	 * �f�t�H���g�R���X�g���N�^
 	 */
 	public LoginCommand (){
 		this.useSessionIdReset = true;
 	}
// 2015.06.22 H.Mizuno ���O�A�E�g���̃Z�b�V�����j���Ή� start
 	
 	
	
    /**
     * ���C�����W�b�N<br/>
     * ���N�G�X�g�E�p�����[�^������A�o���f�[�V�������s���B<br/>
     * �o���f�[�V����������I�������ꍇ�A�w�肳�ꂽ�F�ؗp�N���X�ɂ�胍�O�C���������s���B<br/>
     * ���O�C�������̐����E���s�ɉ����āA�㑱���������s����B<br/>
     * <br/>
     * @param request HTTP���N�G�X�g
     * @param�@response HTTP���X�|���X
     * @return �����_�����O�Ŏg�p���� ModelAndView �I�u�W�F�N�g
     * @throws Exception
     */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

        FormPopulator.populateFormBeanFromRequest(request, loginForm);

        // �o���f�[�V�����G���[������ꍇ�AView ���� failure �ŕ��A
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        if (!loginForm.validate(errors)) {
            return loginFailure(request, response, null, loginForm, errors);
        }

        // ���O�C�����������s
// 2015.02.24 H.Mizuno �A�J�E���g���b�N�@�\�ɑΉ� start
//        LoginUser user = this.authentication.login(request, response, loginForm);

        LoginUser user = null;
        try {
            user = this.authentication.login(request, response, loginForm);
        } catch (LockException e){
        	// �A�J�E���g���b�N�̗�O���X���[���ꂽ�ꍇ�B
        	errors.add(new ValidationFailure("userlock","logincheck",null,null));
        	return loginFailure(request, response, user, loginForm, errors);
        }
// 2015.02.24 H.Mizuno �A�J�E���g���b�N�@�\�ɑΉ� start

        if (user == null) {
        	// ���O�C�����s
        	errors.add(new ValidationFailure("notfound","logincheck",null,null));
        	return loginFailure(request, response, user, loginForm, errors);
		} else {
        	// ���O�C������
            return loginSuccess(request, response, user, loginForm);
        }
	}

	
	
    /**
     * ���O�C����������<br/>
     * View ���� success �ɐݒ肵�ĕ��A����B<br/>
     * <br/>
     * @param request HTTP���N�G�X�g
     * @param�@response HTTP���X�|���X
     * @param�@user�@���O�C�����[�U�[�̃I�u�W�F�N�g
     * @param�@loginForm ���O�C���t�H�[��
     * @return �r���[�� success �� ModelAndView �I�u�W�F�N�g
     */
    protected ModelAndView loginSuccess(HttpServletRequest request, 
            HttpServletResponse response, LoginUser user, LoginForm loginForm) {
        log.info("set user: " + user);

        // �Z�b�V����ID �̐؂�ւ����L���ȏꍇ�A�Z�b�V���� ��V�Z�b�V�����֐؂�ւ���
    	if (this.useSessionIdReset && this.sessionIdResetter != null) {
           	this.sessionIdResetter.resetSession(request);
    	}

        // ���_�C���N�g��URL�̏����A���N�G�X�g�E�p�����[�^����擾����B
        return new ModelAndView("success", "redirectURL", getRedirectURL(request));        
    }
	

    
    /**
     * ���O�C�����s����<br/>
     * View ���� failure �ɐݒ肵�ĕ��A����B<br/>
     * ���O�A�E�g���������s���AModelAndView �Ƀ��O�C���t�H�[���ƁA�G���[���b�Z�[�W���i�[����B
     * <br/>
     * @param request HTTP���N�G�X�g
     * @param�@response HTTP���X�|���X
     * @param�@user�@���O�C�����[�U�[�̃I�u�W�F�N�g
     * @param�@loginForm ���O�C���t�H�[��
     * @return �r���[�� failure �� ModelAndView �I�u�W�F�N�g
     */
	protected ModelAndView loginFailure(HttpServletRequest request, 
            HttpServletResponse response, LoginUser user, LoginForm loginForm,
            List<ValidationFailure> errors) {

    	// ���O�A�E�g����
    	this.authentication.logout(request, response);

        Map<String,Object> model = new HashMap<String,Object>();

        // ���_�C���N�g����A���͒l�A�G���[���b�Z�[�W�� ModelAndView �֓n���B
        model.put("redirectURL", getRedirectURL(request));
        model.put("loginForm", loginForm);
        model.put("errors", errors);

        // �����ł��AuserType �̏������s���Ă��邪�A�Ƃ肠�����폜
        
        return new ModelAndView("failure", model);
	}

	
	
    /**
     * ���_�C���N�g��URL�̎擾<br/>
     * ���N�G�X�g�E�p�����[�^����A���_�C���N�g�� URL ���擾����B
     * <br/>
     * @param request HTTP���N�G�X�g
     * @return ���_�C���N�g�� URL
     */
    protected String getRedirectURL(HttpServletRequest request) {
        return request.getParameter(this.redirectURLParameter);
    }

	
	
	// setter�Agetter
	public void setAuthentication(Authentication pAuthentication) {
        this.authentication = pAuthentication;
    }

	public void setLoginForm(LoginForm loginForm) {
		this.loginForm = loginForm;
	}



// 2015.06.22 H.Mizuno ���O�A�E�g���̃Z�b�V�����j���Ή� start
	/**
	 * ���O�C�����̃Z�b�V����ID �؂�ւ��𖳌�������ꍇ�� false ��ݒ肷��B<br/>
	 * Ver 1.5.x �n�܂ł́A�f�t�H���g�ł� false �i�����j���������A�Z�L�����e�B�I�ȍl���ɂ��A
	 * Ver 1.6.x �n����́A�f�t�H���g�� true �i�L���j�ɕύX����Ă���B<br/>
	 * �܂��A���\�b�h�����AuseSessionReset ����ύX����Ă���B
	 * <br/>
	 * @param useSessionIdReset ����������ꍇ false ��ݒ�@�i�f�t�H���g true�j
	 */
	public void setUseSessionIdReset(boolean useSessionIdReset) {
		this.useSessionIdReset = useSessionIdReset;
	}

	/**
	 * �Z�b�V����ID �̃��Z�b�g�N���X��ݒ肷��B<br/>
	 * �f�t�H���g�ł́ASessionIdResetter �̃C���X�^���X���g�p����邪�A�N���X���g�������ꍇ
	 * �́A���̃��\�b�h�ŃI�u�W�F�N�g��ݒ肷��B<br/>
	 * Ver 1.5.x ���烁�\�b�h�����AsetSessionResetter ����ύX����Ă���B<br/>
	 * <br/>
	 * @param sessionIdResetter �Z�b�V����ID ���Z�b�g�I�u�W�F�N�g
	 */
	public void setSessionIdResetter(SessionIdResetter sessionIdResetter) {
		this.sessionIdResetter = sessionIdResetter;
	}
// 2015.06.22 H.Mizuno ���O�A�E�g���̃Z�b�V�����j���Ή� end

}
