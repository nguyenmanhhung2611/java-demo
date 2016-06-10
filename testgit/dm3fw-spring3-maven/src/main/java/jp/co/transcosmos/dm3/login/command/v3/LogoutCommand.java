package jp.co.transcosmos.dm3.login.command.v3;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.login.support.SessionIdResetter;
import jp.co.transcosmos.dm3.login.v3.Authentication;


/**
 * <pre>
 * Ver 3 �F�ؑΉ��p���O�A�E�g�R�}���h�N���X
 * Authentication �v���p�e�B�̃N���X���g�p ���ă��O�A�E�g�������s���B
 *
 * �S����            �C����               �C�����e
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.05  �V�K�쐬
 * H.Mizuno  2015.06.22  ���O�A�E�g���ɃZ�b�V����ID ��؂�ւ���@�\��ǉ�
 *
 * </pre>
*/
public class LogoutCommand implements Command {

	// �F�؏����N���X
	private Authentication authentication;

// 2015.06.22 H.Mizuno ���O�A�E�g���̃Z�b�V�����j���Ή� start
 	// ���O�A�E�g���ɐV���ȃZ�b�V����ID �𕥂��o���Ȃ��ꍇ�� false ��ݒ肷��B�@�i�f�t�H���g true�j
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
 	public LogoutCommand(){
 		this.useSessionIdReset = true;
 	}
// 2015.06.22 H.Mizuno ���O�A�E�g���̃Z�b�V�����j���Ή� start



    /**
     * ���C�����W�b�N<br/>
     * �v���p�e�B�Őݒ肳�ꂽ�F�ؗp�N���X�Ń��O�A�E�g�������s���B<br/>
     * <br/>
     * @param request HTTP���N�G�X�g
     * @param�@response HTTP���X�|���X
     * @return �����_�����O�Ŏg�p���� ModelAndView �I�u�W�F�N�g
     * @throws Exception
     */
    @Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// ���O�A�E�g����
		this.authentication.logout(request, response);


        // �Z�b�V����ID �̔j�����L���ȏꍇ�A�Z�b�V���� ID ��j������B
    	if (this.useSessionIdReset && this.sessionIdResetter != null) {
           	this.sessionIdResetter.resetSession(request);
    	}

		return new ModelAndView("success");

	}



	// setter�Agetter
    public void setAuthentication(Authentication pAuthentication) {
        this.authentication = pAuthentication;
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
