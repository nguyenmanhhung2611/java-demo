package jp.co.transcosmos.dm3.token;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.config.ValidScope;


/**
 * <pre>
 * �Œ�g�[�N���o�͗p�J�X�^���^�O�iVelocity �p�j
 * 
 * �S����            �C����               �C�����e
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.05.17  �V�K�쐬
 * H.Mizuno  2013.06.14  �f�t�H���g�̃Z�b�V�����L�[�𑼂̃N���X�Ƌ��ʉ�
 * 
 * </pre>
*/
@DefaultKey("dm3token")
@ValidScope(Scope.REQUEST)
public class TokenVelocity {
    private static final Log log = LogFactory.getLog(TokenVelocity.class);

    // HTTP ���N�G�X�g�I�u�W�F�N�g
    protected HttpServletRequest request;

    
	
    /**
     * ���ʊ֐�����������<br/>
     * ���̃��\�b�h���`����ƁAVelocity ���� ToolContext �����鎖���ł���B<br />
     * ToolContext �ɂ́AHttpRequest ��AServletContext ���i�[���Ă���̂ŁA�K�v��<br />
     * ����΁A���̃��\�b�h���`����B<br />
     * <br/>
     * @param values Velocity ����n�����v���p�e�B���
     */
    protected void configure(Map<?,?> values)
    {
		log.debug("velocity dm3login init");

		// ToolContext ���擾���A���[�J���R���e�L�X�g����AWeb �֘A�̃I�u�W�F�N�g���擾�B
		ToolContext toolContext = (ToolContext)values.get("velocityContext");
    	this.request = (HttpServletRequest)toolContext.get("request");
    }


    /**
     * �����^�C���g�[�N���̐�������<br/>
     * <br/>
     * @param tokenName �g�[�N����
     * @param idOnly true �ɐݒ肷��ƁAID �̂ݏo�́Afalse �̏ꍇ�Ahidden �^�O���o��
     * @return �������ꂽID�A�܂��� hidden �^�O
     */
    public String oneTimeToken(){
    	return oneTimeToken(GenerateTokenId.DEFAULT_TOKEN_KEYNAME, false);
    }

    public String oneTimeToken(String tokenName){
    	return oneTimeToken(tokenName, false);
    }

    public String oneTimeToken(Boolean idOnly){
    	return oneTimeToken(GenerateTokenId.DEFAULT_TOKEN_KEYNAME, idOnly);
    }

    
    /**
     * �����^�C���g�[�N���̐�������<br/>
     * ���N�G�X�g�X�R�[�v�ɐ������ꂽ ID �����݂���ꍇ�́A���̒l���ė��p����B<br/>
     * �i����y�[�W���ł���΁A���� ID ���o�͂���ׁB�j<br/>
     * ���݂��Ȃ��ꍇ�͐V�K�� ID ���̔Ԃ��ă��N�G�X�g�X�R�[�v�A�Z�b�V�����Ɋi�[����B<br/>
     * ID �̔Ԍ�AidOnly�AtokenName �̐ݒ�ɉ����ă^�O�A�܂��� ID ���o�͂���B<br/>
     * <br/>
     * @param tokenName �g�[�N����
     * @param idOnly true �ɐݒ肷��ƁAID �̂ݏo�́Afalse �̏ꍇ�Ahidden �^�O���o��
     * @return �������ꂽID�A�܂��� hidden �^�O
     */
    public String oneTimeToken(String tokenName, Boolean idOnly){
    	// ���N�G�X�g�R�[�v���ɁA���Ƀg�[�N������������Ă��邩���`�F�b�N����B
    	String tokenId = (String)request.getAttribute(tokenName);

    	// ��������Ă��Ȃ��ꍇ�A�V���ȃg�[�N�����쐬����B
    	if (tokenId == null || tokenId.length() == 0){
    		tokenId = GenerateTokenId.getId(request);
    		
    		// �V���ȃg�[�N���𐶐������ꍇ�A���N�G�X�g�R�[�v�A�Z�b�V�����Ɋi�[����B
    		request.setAttribute(tokenName, tokenId);
    		request.getSession().setAttribute(tokenName, tokenId);
    		
    		log.info("Generate One Time Token : name=" + tokenName + "," +
    				"id=" + tokenId + "," + "session id=" + request.getSession().getId()); 
    	}

    	return writeToken(tokenId, tokenName, idOnly);
    }


    
    /**
     * �Œ�g�[�N���̐�������<br/>
     * <br/>
     * @param tokenName �g�[�N����
     * @param idOnly true �ɐݒ肷��ƁAID �̂ݏo�́Afalse �̏ꍇ�Ahidden �^�O���o��
     * @return �������ꂽID�A�܂��� hidden �^�O
     */
    public String fixedToken(){
    	return fixedToken(GenerateTokenId.DEFAULT_TOKEN_KEYNAME, false);
    }

    public String fixedToken(String tokenName){
    	return fixedToken(tokenName, false);
    }

    public String fixedToken(Boolean idOnly){
    	return fixedToken(GenerateTokenId.DEFAULT_TOKEN_KEYNAME, idOnly);
    }
    


    /**
     * �Œ�g�[�N���̐�������<br/>
     * �Z�b�V�����X�R�[�v�ɐ������ꂽ ID �����݂���ꍇ�́A���̒l���ė��p����B<br/>
     * �i����Z�b�V�������ł���΁A���� ID ���o�͂���ׁB�j<br/>
     * ���݂��Ȃ��ꍇ�͐V�K�� ID ���̔Ԃ��ăZ�b�V�����Ɋi�[����B<br/>
     * ID �̔Ԍ�AidOnly�AtokenName �̐ݒ�ɉ����ă^�O�A�܂��� ID ���o�͂���B<br/>
     * <br/>
     * @param tokenName �g�[�N����
     * @param idOnly true �ɐݒ肷��ƁAID �̂ݏo�́Afalse �̏ꍇ�Ahidden �^�O���o��
     * @return �������ꂽID�A�܂��� hidden �^�O
     */
    public String fixedToken(String tokenName, Boolean idOnly){
    	// �Z�b�V�������ɁA���Ƀg�[�N������������Ă��邩���`�F�b�N����B
    	String tokenId = (String)request.getSession().getAttribute(tokenName);
    	
    	// ��������Ă��Ȃ��ꍇ�A�V���ȃg�[�N�����쐬����B
    	if (tokenId == null || tokenId.length() == 0){
    		tokenId = GenerateTokenId.getId(request);
    		
    		// �V���ȃg�[�N���𐶐������ꍇ�A�Z�b�V�����Ɋi�[����B
    		request.getSession().setAttribute(tokenName, tokenId);
    		
    		log.info("Generate Fixed Token : name=" + tokenName + "," +
    				"id=" + tokenId + "," + "session id=" + request.getSession().getId()); 
    	}

    	return writeToken(tokenId, tokenName, idOnly);
    }



    /**
     * �h�c�A�܂��́Ahidden �^�O�̏o�͏���<br/>
     * <br/>
     * @param tokenId �g�[�N��ID
     * @param tokenName �g�[�N����
     * @param idOnly true �ɐݒ肷��ƁAID �̂ݏo�́Afalse �̏ꍇ�Ahidden �^�O���o��
     * @return �������ꂽID�A�܂��� hidden �^�O
     */
    private String writeToken(String tokenId, String tokenName, Boolean idOnly){
		if (idOnly){
			return tokenId;
		} else {
			return "<input type=\"hidden\" name=\"" +
					tokenName + "\" value=\"" + tokenId + "\" />";
		}

    }
    
}
