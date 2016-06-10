package jp.co.transcosmos.dm3.token;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <pre>
 * �Œ�g�[�N���o�͗p�J�X�^���^�O
 * 
 * �S����            �C����               �C�����e
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.05.17  �V�K�쐬
 * H.Mizuno  2013.06.14  �f�t�H���g�̃Z�b�V�����L�[��萔��
 * 
 * </pre>
*/
public class FixedTokenTag implements Tag {
    private static final Log log = LogFactory.getLog(FixedTokenTag.class);

    // �y�[�W�R���e�L�X�g
    private PageContext pageContext;
    // �e�^�O�I�u�W�F�N�g
    private Tag parentTag;
    // �g�[�N����(�f�t�H���g�l�́@dm3token�@)
    private String tokenName;
    // ID �݂̂̏o�͂��s���ꍇ�Atrue ��ݒ肷��i�f�t�H���g�l�� false�j
    // false ���ݒ肳��Ă���ꍇ�Ahidden �^�O���o�͂����B
    private Boolean idOnly;


	
    /**
     * �^�O�o�͂̊J�n����<br/>
     * �Z�b�V�����X�R�[�v�ɐ������ꂽ ID �����݂���ꍇ�́A���̒l���ė��p����B<br/>
     * �i����Z�b�V�������ł���΁A���� ID ���o�͂���ׁB�j<br/>
     * ���݂��Ȃ��ꍇ�͐V�K�� ID ���̔Ԃ��ăZ�b�V�����Ɋi�[����B<br/>
     * ID �̔Ԍ�AidOnly�AtokenName �̐ݒ�ɉ����ă^�O�A�܂��� ID ���o�͂���B<br/>
     * <br/>
     * @return SKIP_BODY �Œ�
     * @throws JspException
     */
	@Override  
    public int doStartTag() throws JspException {

		// �g�[�N�����̑������ݒ肳��Ă��Ȃ��ꍇ�A�����l��ݒ肷��B
    	if (this.tokenName == null || this.tokenName.length() == 0)
    		this.tokenName = GenerateTokenId.DEFAULT_TOKEN_KEYNAME;

		// �f�t�H���g�́AHTML �^�O�o��
    	if (this.idOnly == null)
    		this.idOnly = false;
    	
    	
    	// �Z�b�V�����X�R�[�v���ɁA���Ƀg�[�N������������Ă��邩���`�F�b�N����B
    	String tokenId = (String)pageContext.getSession().getAttribute(this.tokenName);

    	// ��������Ă��Ȃ��ꍇ�A�V���ȃg�[�N�����쐬����B
    	if (tokenId == null || tokenId.length() == 0){
        	HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
    		tokenId = GenerateTokenId.getId(request);
    		
    		// �V���ȃg�[�N���𐶐������ꍇ�A�Z�b�V�����Ɋi�[����B
    		pageContext.getSession().setAttribute(this.tokenName, tokenId);
    		
    		log.info("Generate Fixed Token : name=" + this.tokenName + "," +
    				"id=" + tokenId + "," + "session id=" +pageContext.getSession().getId()); 
    	}

    	// �g�[�N���̏o��
		try {
			if (this.idOnly){
				this.pageContext.getOut().write(tokenId);
			} else {
				this.pageContext.getOut().write("<input type=\"hidden\" name=\"" +
						this.tokenName + "\" value=\"" + tokenId + "\" />");
			}
        } catch (IOException err) {
            throw new JspTagException("Error writing Fixed Toekn");
        }
        return SKIP_BODY;
    }
    
	

    /**
     * �^�O�o�͂̏I������<br/>
     * Body ���������݂��Ȃ��̂ŁA�v���p�e�B�̃��Z�b�g�ȊO�͉������Ȃ��B<br/>
     * doStartTag() �` doEndTag() �ԈȊO�̓X���b�h�Z�[�t�łȂ��̂ŁA�v���p�e�B��<br/>
     * �c�[�͎c���Ȃ��l�ɂ��Ă���B<br/>
     * <br/>
     * @return EVAL_PAGE �Œ�
     * @throws JspException
     */
	@Override
    public int doEndTag() throws JspException {
        this.pageContext = null;
        this.parentTag = null;
        this.tokenName = null;
        this.idOnly = null;

        return EVAL_PAGE;
    }


	
    /**
     * �I�u�W�F�N�g�̔p������<br/>
     * �I�u�W�F�N�g���s�v�Ɣ��f���ꂽ���Ɏ��s�����B�@�������Ȃ��B<br/>
     * <br/>
     * @return EVAL_PAGE �Œ�
     * @throws JspException
     */
	@Override
	public void release() {
	}
    

	// setter�Agetter
    @Override
	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}

	@Override
	public void setParent(Tag parentTag) {
		this.parentTag = parentTag;
	}

	@Override
	public Tag getParent() {
		return this.parentTag;
	}

	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}

	public void setIdOnly(Boolean idOnly) {
		this.idOnly = idOnly;
	}

}
