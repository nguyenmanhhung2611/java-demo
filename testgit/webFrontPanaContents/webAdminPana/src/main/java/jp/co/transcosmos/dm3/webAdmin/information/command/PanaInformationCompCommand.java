package jp.co.transcosmos.dm3.webAdmin.information.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.adminCore.information.command.InformationCompCommand;
import jp.co.transcosmos.dm3.adminCore.mypage.command.MypageCompCommand;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.information.form.InformationForm;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;

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
public class PanaInformationCompCommand extends InformationCompCommand  {

	private static final Log log = LogFactory.getLog(MypageCompCommand.class);

	//�Ǘ����[�U���[��
	private String loginUserEmail;

	/**
	 * ���m�点���̒ǉ��A�ύX�A�폜����<br>
	 * <br>
	 * @param request �N���C�A���g�����Http���N�G�X�g�B
	 * @param response �N���C�A���g�ɕԂ�Http���X�|���X�B
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        // ���O�C�����[�U�[�̏����擾����B�@�i�Ǘ����[�U���[���擾�p�j
        AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);
        this.loginUserEmail = loginUser.getEmail();
		ModelAndView modelAndView = super.handleRequest(request, response);
		return modelAndView;
	}

	/**
	 * ���m�点�̒ʒm���[���𑗐M����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param inputForm ���͒l
	 */
	@Override
	protected void sendInformationMail(HttpServletRequest request,
			InformationForm inputForm) {

		// �폜����ꍇ ���A���M���Ȃ�
		if (!"delete".equals(inputForm.getCommand())
				&& PanaCommonConstant.SEND_FLG_1.equals(inputForm.getMailFlg())
				&& PanaCommonConstant.DSP_FLG_PRIVATE.equals(inputForm.getDspFlg())) {
			// ���[���e���v���[�g���ݒ肳��Ă��Ȃ��ꍇ�̓��[���̒ʒm���s��Ȃ��B
			if (this.sendInformationTemplate == null) {
				log.warn("sendInformationTemplate is null.");
				return;
			}

			// ���[���e���v���[�g�Ŏg�p����p�����[�^��ݒ肷��B
			this.sendInformationTemplate.setParameter("loginUserEmail", this.loginUserEmail);
			this.sendInformationTemplate.setParameter("inputForm", inputForm);
			this.sendInformationTemplate.setParameter("commonParameters", CommonParameters.getInstance(request));

			// ���[�����M
			this.sendInformationTemplate.send();
		}
	}
}
