package jp.co.transcosmos.dm3.webAdmin.news.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.adminCore.mypage.command.MypageCompCommand;
import jp.co.transcosmos.dm3.adminCore.news.command.NewsCompCommand;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

/**
 * ���m�点���̒ǉ��A�ύX�A�폜����.
 * <p>
 * �y�V�K�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B</li>
 * <li>�o���f�[�V����������I�������ꍇ�A���m�点����V�K�o�^����B</li>
 * <li>�܂��A���J�悪����l�̏ꍇ�A���m�点���J������V�K�o�^����B</li>
 * </ul>
 * <br/>
 * �y�X�V�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B</li>
 * <li>�o���f�[�V����������I�������ꍇ�A���m�点�����X�V����B</li>
 * <li>���m�点���J����͈�x�폜���A�ύX��̌��J�悪����l�ł���΁A���m�点���J������V�K�o�^����B</li>
 * <li>�����A�X�V�Ώۃf�[�^�����݂��Ȃ��ꍇ�A�X�V�������p���ł��Ȃ��̂ŊY��������ʂ�\������B</li>
 * </ul>
 * <br/>
 * �y�폜�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B�i��L�[�l�̂݁j</li>
 * <li>�o���f�[�V����������I�������ꍇ�A���m�点���A���m�点���J������폜����B</li>
 * </ul>
 * <br/>
 * �y���A���� View ���z<br/>
 * <ul>
 * <li>success</li>:����I���i���_�C���N�g�y�[�W�j
 * <li>input</li>:�o���f�[�V�����G���[�ɂ��ē���
 * <li>notFound</li>:�Y���f�[�^�����݂��Ȃ��ꍇ�i�X�V�����̏ꍇ�j
 * <li>comp</li>:������ʕ\��
 * </ul>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.10	�V�K�쐬
 * H.Mizuno		2015.02.27	�C���^�[�t�F�[�X�̉���ɂƂ��Ȃ��S�̍\����ύX
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class PanaNewsCompCommand extends NewsCompCommand  {

	private static final Log log = LogFactory.getLog(MypageCompCommand.class);

	/**
	 * ���m�点���̒ǉ��A�ύX�A�폜����<br>
	 * <br>
	 * @param request �N���C�A���g�����Http���N�G�X�g�B
	 * @param response �N���C�A���g�ɕԂ�Http���X�|���X�B
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView modelAndView = super.handleRequest(request, response);
		return modelAndView;
	}
}
