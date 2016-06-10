package jp.co.transcosmos.dm3.login.command.v3;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.login.v3.Authentication;
import jp.co.transcosmos.dm3.login.UserRoleSet;

/**
 * <pre>
 * V3 �F�ؑΉ�
 * ���[���ɂ��A�N�Z�X���`�F�b�N�R�}���h�i�t�B���^�[�j
 * ���O�C�����[�U�[�̃��[�������擾���A���̃t�B���^�[�� roles �v���p�e�B�ɐݒ肳�ꂽ���[����
 * �ƈ�v���邩���ƍ�����B
 * 
 * �S����            �C����               �C�����e
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.05  �V�K�쐬
 * H.Mizuno  2013.04.05  ���[�������R���N�V�����Őݒ肷��ꍇ�A�I�[�o�[���[�h���������@�\���Ȃ�
 *                       �̂ŁArole = �P��Aroles = ���X�g�ɌŒ艻
 * H.Mizuno  2013.05.07  Ver 1.1 ����񐄏��ɂȂ����̂ŃR�����g��ύX
 * 
 * ���ӎ��� 
 *     �����I�ȏ������e�́AV2 �ƑS�������ł��B
 * </pre>
 * @deprecated HasRoleCheckFilter �̎g�p�𐄏����܂��B����A�����e�i���X����Ȃ��\��������܂��B
*/
public class HasRoleCheckCommand implements Command {
    private static final Log log = LogFactory.getLog(HasRoleCheckCommand.class);

    // ���̃t�B���^�������郍�[�����̃R���N�V����
    private Collection<String> roles;

    // �F�؃��W�b�N�I�u�W�F�N�g
    private Authentication authentication;

    
    /**
     * ���C�����W�b�N<br/>
     * �񋟂��ꂽ�F�ؗp�N���X���g�p���ă��O�C�����[�U�[�̃��[�������擾����B<br/>
     * �擾�������[����񂪁A���̃t�B���^�[�ɐݒ肳�ꂽ���[�����Ƀ}�b�`����ꍇ�A���̂܂�<br/>
     * ���A����B�@�i��̏����ɐi�ށj<br/>
     * �擾�������[����񂪑��݂��Ȃ��ꍇ�A�r���[���� failure �ɂ��ď����𒆒f����B<br/>
     * <br/>
     * @param request HTTP���N�G�X�g
     * @param�@response HTTP���X�|���X
     * @return �����_�����O�Ŏg�p���� ModelAndView �I�u�W�F�N�g
     * @throws Exception
     */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

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
            return null;
        } else if (roleset.hasRole(this.roles)) {
            log.info("User has defined role - allowing");
            return null;
        } else {
            log.info("User does not have defined role - rejecting");
            return new ModelAndView("failure", "neededRole", this.roles);
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
