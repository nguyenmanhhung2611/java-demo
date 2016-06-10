package jp.co.transcosmos.dm3.token;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.transcosmos.dm3.utils.EncodingUtils;


/**
 * <pre>
 * �g�[�N���h�c�o�͏���
 * 
 * �S����            �C����               �C�����e
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.05.17  �V�K�쐬
 * H.Mizuno  2013.06.14  �g�[�N�����폜����@�\��ǉ�
 *                       �f�t�H���g�̃Z�b�V�����L�[�̒萔�����̃N���X�ɐ錾
 * 
 * </pre>
*/
public class GenerateTokenId {
    private static final Log log = LogFactory.getLog(GenerateTokenId.class);

	// ���������I�u�W�F�N�g�i�r������ɂ��g�p�j
    private static final Random rnd = new Random();
    
    // �g�[�N���̃Z�b�V�����L�[���@�i�f�t�H���g�l�j
    public static final String DEFAULT_TOKEN_KEYNAME = "dm3token";
    


    /**
     * �g�[�N���Ɏg�p����ID ���̔Ԃ���B<br/>
     * ���[�J���A�h���X�A���[�J���|�[�g�A�T�[�o�[�|�[�g�A�V�X�e�����ԁA�����̒l�� DM5 �Ńn�b�V������<br/>
     * ��������B<br/>
     * <br/>
     * @param request HTTP ���N�G�X�g
     * @return �������ꂽ ID
     */
	public static String getId(HttpServletRequest request){
        synchronized (rnd) {
            return EncodingUtils.md5Encode(request.getLocalAddr() + " " + 
                                           request.getLocalPort() + " " +
                                           System.currentTimeMillis() + " " + 
                                           request.getServerPort() + " " +
                                           rnd.nextLong());
        }
	}



    /**
     * �Z�b�V��������g�[�N�����폜����@�i�f�t�H���g�g�[�N�����p�j<br/>
     * <br/>
     * @param request HTTP ���N�G�X�g
     */
	public static void removeId(HttpServletRequest request){
		removeId(request, GenerateTokenId.DEFAULT_TOKEN_KEYNAME);
	}

	
	
    /**
     * �Z�b�V��������g�[�N�����폜����<br/>
     * <br/>
     * @param request HTTP ���N�G�X�g
     * @param tokenName �g�[�N����
     */
	public static void removeId(HttpServletRequest request, String tokenName){
		request.getSession().removeAttribute(tokenName);
		log.info("remove token id : "  + tokenName);
	}

	

	/**
	 * ���N�G�X�g���ꂽ�g�[�N���ƁA�Z�b�V�����̃g�[�N�����ƍ�����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param tokenName�@�g�[�N����
	 * 
	 * @return �G���[������ꍇ�Atrue �𕜋A����B
	 */
	public static boolean hasError(HttpServletRequest request, String tokenName) {

		boolean isError = false;


		// �g�[�N�����擾
		String reqTokenId = request.getParameter(tokenName);
		String sessTokenId = (String)request.getSession().getAttribute(tokenName);

		if (reqTokenId == null || reqTokenId.length() ==0) {
			// ���N�G�X�g�p�����[�^�Ƀg�[�N���������ꍇ�G���[
			isError = true;

		} else if (sessTokenId == null || sessTokenId.length() ==0) { 
			// �Z�b�V�����Ƀg�[�N���������ꍇ���G���[
			isError = true;

		} else if (!reqTokenId.equals(sessTokenId)) { 
			// �g�[�N������v���Ȃ��ꍇ���G���[
			isError = true;
		}

		return isError;
	}

}
