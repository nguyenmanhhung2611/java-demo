package jp.co.transcosmos.dm3.login.support;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * <pre>
 * �Z�b�V���� ID ���Z�b�g�N���X
 * ���O�C�����A�������́A���O�A�E�g���ɃZ�b�V���� ID ��V���� ID �ɕύX����B
 *
 * �S����            �C����               �C�����e
 * ----------------------------------------------------------------------------
 * H.Mizuno  2015.06.22  �V�K�쐬
 * H.Mizuno  2015.06.23  SessionResetter ����ASessionIdResetter �ɕύX
 *
 * </pre>
*/
public class SessionIdResetter {

	/**
	 * �Z�b�V����ID �����Z�b�g����B<br/>
	 * �����A�Z�b�V�����I�u�W�F�N�g���̂�j�����Ă������A���s�o�[�W�����ł́A���Z�b�V������j�����A
	 * �V���ȃZ�b�V�������쐬��A���Z�b�V�����Ɋi�[����Ă����I�u�W�F�N�g��V�Z�b�V�����Ɉړ�����
	 * ����B<br/>
	 * @param request HTTP ���N�G�X�g
	 * @return �V�Z�b�V�����I�u�W�F�N�g
	 */
	public HttpSession resetSession(HttpServletRequest request) {

		// ���݂̃Z�b�V�������擾
    	HttpSession session = request.getSession();

		// ���݂̃Z�b�V�����I�u�W�F�N�g���擾
		Map<String, Object> sessionObjs = getSessionObjects(session);

		// �Z�b�V����ID �̔j��
		session = request.getSession();
		session.invalidate();
		session = request.getSession(true);

		// ���Z�b�V�����Ɋi�[����Ă����Z�b�V�����I�u�W�F�N�g�𕜌�����B
		setSessionObjects(session, sessionObjs);

		return session;
	}


	/**
	 * �Z�b�V��������Z�b�V�����I�u�W�F�N�g���i�[���� Map �I�u�W�F�N�g���擾����B<br/>
	 * Map �� Key ���Z�b�V�����I�u�W�F�N�g�� Key�AValue ���i�[����Ă����I�u�W�F�N�g�ɂȂ�B<br/>
	 * <br/>
	 * @param session HTTP �Z�b�V����
	 * @return �Z�b�V�����I�u�W�F�N�g���i�[���� Map �I�u�W�F�N�g
	 */
	protected Map<String, Object> getSessionObjects(HttpSession session) {
		
		Map<String, Object> objs = new HashMap<>();
		
		Enumeration<String> enu = session.getAttributeNames();
		while (enu.hasMoreElements()){
			String key = enu.nextElement();
			objs.put(key, session.getAttribute(key));
		}

		return objs;
		
	}


	/**
	 * Map �Ɋi�[���ꂽ�I�u�W�F�N�g���Z�b�V�����I�u�W�F�N�g�Ƃ��ăZ�b�V�����Ɋi�[����B<br/>
	 * Map �� Key ���Z�b�V�����I�u�W�F�N�g�� Key �Ƃ��Ďg�p�����B<br/>
	 * <br/>
	 * @param session HTTP �Z�b�V�����I�u�W�F�N�g
	 * @param sessionObjs �i�[����I�u�W�F�N�g���i�[���� Map
	 */
	protected void setSessionObjects(HttpSession session, Map<String, Object> sessionObjs){
		for (Entry<String, Object> e : sessionObjs.entrySet()) {
			session.setAttribute(e.getKey(), e.getValue());
		}
	}
	
}
