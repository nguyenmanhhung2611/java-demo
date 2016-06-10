package jp.co.transcosmos.dm3.token;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.transcosmos.dm3.servlet.BaseFilter;
import jp.co.transcosmos.dm3.utils.ServletUtils;

public class TokenCheckFilter extends BaseFilter  {

	private static final Log log = LogFactory.getLog(TokenCheckFilter.class);

	/** �g�[�N���� */
	private String tokenName = GenerateTokenId.DEFAULT_TOKEN_KEYNAME;

	/** �G���[�������̃��_�C���N�g�� URL */
	private String redirectUrl = "";

	/** true �̏ꍇ�A�g�[�N���̏ƍ��`�F�b�N��ɃZ�b�V�������� token ���폜����B �i�f�t�H���g true �j */
	private boolean removeAfterToken = true;



	/**
	 * �g�[�N������ݒ肷��B<br/>
	 * �f�t�H���g�� dm3token�B�@�ύX�����ꍇ�͂��̃v���p�e�B�ɒl��ݒ肷��B<br/>
	 * <br/>
	 * @param tokenName �g�[�N����
	 */
	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}

	/**
	 * ���_�C���N�g�� URL ��ݒ肷��B<br/>
	 * <br/>
	 * @param redirectUrl ���_�C���N�g�� URL
	 */
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	/**
	 * �g�[�N���̏ƍ���A�Z�b�V��������폜���Ȃ��ꍇ�A���̃v���p�e�B�� false ��ݒ肷��B<br/>
	 * �i�f�t�H���g true �j<br/>
	 * <br/>
	 * @param removeAfterToken true �̏ꍇ�A�Z�b�V��������g�[�N�����폜����B
	 */
	public void setRemoveAfterToken(boolean removeAfterToken) {
		this.removeAfterToken = removeAfterToken;
	}

	@Override
	protected void filterAction(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// �g�[�N���̏ƍ����s��
		boolean isError = GenerateTokenId.hasError(request, this.tokenName);

		if (this.removeAfterToken){
			// �`�F�b�N��Ƀg�[�N�����Z�b�V��������폜����B
			GenerateTokenId.removeId(request, this.tokenName);
		}

		if (isError) {
			log.warn("token error(filter check). token is " + request.getParameter(this.tokenName));

			// �G���[�����������ꍇ�A�w�肳�ꂽ URL �փ��_�C���N�g����B
            response.sendRedirect(ServletUtils.fixPartialURL(
                    request.getContextPath() + this.redirectUrl, request));
		} else {
			// ���Ȃ���Ύ��� Filter �� Chain ����B
			chain.doFilter(request, response);
		}

	}

}
