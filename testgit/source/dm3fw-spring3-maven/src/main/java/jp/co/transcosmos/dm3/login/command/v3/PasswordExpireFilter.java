package jp.co.transcosmos.dm3.login.command.v3;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.PasswordExpire;
import jp.co.transcosmos.dm3.login.v3.Authentication;
import jp.co.transcosmos.dm3.servlet.BaseFilter;
import jp.co.transcosmos.dm3.servlet.FilterCommandChain;


/**
 * �p�X���[�h�ύX�̊����`�F�b�N�t�B���^�[.
 * <p>
 * �p�X���[�h�ύX�̊����̃`�F�b�N���s���B<br/>
 * ���̃t�B���^�[�́A���O�C���F�؂��p�X���Ă��鎖��O��Ƃ��Ă���̂ŁA�K�� LoginCheckFilter
 * �̌�� Filter �ݒ肷�鎖�B<br/>
 * �����A�����؂�̏ꍇ�A�@failure�@�� view ���� ModelAndView �𕜋A����B<br/>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.20	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * web.xml ����̒ʏ� Filter �Ƃ��Ă̎g�p�͔�Ή��B
 * 
 */
public class PasswordExpireFilter extends BaseFilter {

	private static final Log log = LogFactory.getLog(PasswordExpireFilter.class);
	
	/** �F�؃��W�b�N�I�u�W�F�N�g */
    private Authentication authentication;
	
	/** �p�X���[�h�L������ �i�f�t�H���g 30���j*/
	private Integer passwordExpireDays = 30;

	/**
	 * �����؂ꔻ��p�L�[�l<br/>
	 * �����؂ꂪ���������ꍇ�A�J�ڐ�̏����ł����f�ł���悤�ɁA���̕ϐ��Œ�`���� Key ���ŃZ�b�V������
	 * true �� boolean �l���i�[�����B<br/>
	 * �������؂�Ă��Ȃ��ꍇ�A�����ݒ�Ȃ�Ȃ��B<br/>
	 */
	private String passwordExpireFailedKey = "passwordExpireFailed";

	
	
	


	/**
	 * �p�X���[�h�L�������̃`�F�b�N���s��<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	 * @param chain �`�F�[���I�u�W�F�N�g
	 */
	@Override
	protected void filterAction(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException,	ServletException {

		// �p�X���[�h�����؂ꔻ��p�Z�b�V�����I�u�W�F�N�g����������B
		request.getSession().removeAttribute(this.passwordExpireFailedKey);


        // �F�ؗp�I�u�W�F�N�g���ݒ肳��Ă��Ȃ��ꍇ�A��O���グ�ďI���i�V�X�e���G���[�j
		if (this.authentication == null) {
        	throw new RuntimeException("authentication property not setting!!"); 
        }

		
		// ���O�C�����[�U�[�̏����擾����B
		LoginUser loginUser = this.authentication.getLoggedInUser(request, response);

		// ���O�C���ςł��鎖���O��Ȃ̂ŁA���O�C�����Ă��Ȃ��ꍇ�͗�O���X���[����B
		if (loginUser == null) {
			throw new RuntimeException("not login.");
		}


		// �p�X���[�h�L�������� Filter ���C���v�������g����Ă��Ȃ��ꍇ�A�`�F�b�N�s�\�Ȃ̂ŁA��O���X���[����B
		if (!(loginUser instanceof PasswordExpire)){
			throw new RuntimeException("not PasswordExpire�@interface.");
		}


		// �Ō�Ƀp�X���[�h�ύX�������t���擾����B
		Date lastChangeDate = ((PasswordExpire)loginUser).getLastPasswdChange();

		// �p�X���[�h�̕ύX�������ݒ�̏ꍇ�͊����؂�Ƃ݂Ȃ��B
		if (lastChangeDate == null){
			log.info("password change expire check failed. (lastChangeDate is null)");
			fail(request, (FilterCommandChain)chain);
			return;
		}


		// �ŏI�ύX����臒l�𒴂��Ă���ꍇ�͊����؂�Ƃ���B
		long sysDate = (new Date()).getTime();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(lastChangeDate);
		calendar.add(Calendar.DAY_OF_MONTH, this.passwordExpireDays);
		long chageDate = calendar.getTimeInMillis();

		if (chageDate < sysDate){
			log.info("password change expire check failed. (lastChangeDate is old)");
			fail(request, (FilterCommandChain)chain);
			return;
		}


		// �������ł���Ύ��̃t�B���^�[�� Chain ����B
        log.info("password change expire OK");
      	chain.doFilter(request, response);

	}


	
	/**
	 * �����؂ꂪ���������ꍇ�̏���<br/>
	 * �����؂ꂪ���������ꍇ�A�Z�b�V�����Ƀt���O��ݒ肵�Ă����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param chain FilterChain
	 */
	private void fail(HttpServletRequest request, FilterCommandChain chain){
		chain.setResult(request, new ModelAndView("failure"));
		request.getSession().setAttribute(this.passwordExpireFailedKey, true);
	}
	

	/**
	 * �F�؃��W�b�N�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param authentication �F�؃��W�b�N�I�u�W�F�N�g
	 */
	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

	/**
	 * �p�X���[�h�L��������ݒ肷��B<br/>
	 * <br/>
	 * @param passwordExpireDays �p�X���[�h�L�������i�f�t�H���g 30���j
	 */
    public void setPasswordExpireDays(Integer passwordExpireDays) {
		this.passwordExpireDays = passwordExpireDays;
	}


    /**
     * �p�X���[�h�̊����؂ꂪ���������ꍇ�ɃZ�b�V�����Ƀt���O��ݒ肷��ۂ� Key ����ύX����ꍇ�ɐݒ肷��B<br/>
     * �f�t�H���g�l�� passwordExpireFailed�@�ŁA�ʏ�A�ύX���鎖�͂Ȃ��B<br/>
     * <br/>
     * @param passwordExpireFailedKey �Z�b�V�����Ƀt���O��ݒ肷��ۂ� Key ��
     */
	public void setPasswordExpireFailedKey(String passwordExpireFailedKey) {
		this.passwordExpireFailedKey = passwordExpireFailedKey;
	}

}
