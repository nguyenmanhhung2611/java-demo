package jp.co.transcosmos.dm3.core.login.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.login.command.v3.LoginCommand;
import jp.co.transcosmos.dm3.utils.CommonLogging;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * ���O�o�͋@�\�t���F�؏����N���X.
 * <p>
 * V3 �F�ؗp�̃R�}���h�N���X�𒼐ڌp�����Ă���̂ŁA���̃o�[�W�������g�p����ꍇ�͕ʓr�쐬���鎖�B<br/>
 * ���O�o�͂ɂ� Frame work �� SimpleLogging ���g�p�B<br/>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.31	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class LoggingLoginCommand extends LoginCommand {

	private static final Log log = LogFactory.getLog(LoggingLoginCommand.class);

	/** ���O�o�͏��� */
	protected CommonLogging logging;
	
	/**
	 * ���O�o�͏�����ݒ肷��B<br/>
	 * <br/>
	 * @param logging ���O�o�͏���
	 */
	public void setLogging(CommonLogging logging) {
		this.logging = logging;
	}



	/**
	 * ���O�C������<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	 * 
	 * @return �p���������A���� ModelAndView
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// �{���̃��O�C�����������s
		ModelAndView modelAndView = super.handleRequest(request, response); 

		// View �����擾����B
		// ���O�C�������������ꍇ�́A"success" ���ݒ肳���B�@����ȊO�̏ꍇ�͑S�ăG���[�Ƃ��Ĉ����B
		String viewName = modelAndView.getViewName();

		// ���O�C��ID ���擾����B
		// �{���́AV3 �F�ؗp�t�H�[������擾���ׂ������A���O�C�����s���Ɛ������Ŏ擾���@���قȂ�B
		// �~�����̂̓��O�C��ID �݂̂Ȃ̂� HTTP Request ���璼�ڎ擾����B
		String loginId = request.getParameter("loginID");

		//���O�o��
		@SuppressWarnings("unchecked")
		String msg = createMsg(request, viewName, loginId, (List<ValidationFailure>)modelAndView.getModel().get("errors"));
		log.info(msg);
		this.logging.write(msg);

		return modelAndView;
	}



	/**
	 * ���O�o�͕�����̐���<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param viewName �r���[��
	 * @param loginId ���͂��ꂽ���O�C��ID
	 * @param errors �G���[�I�u�W�F�N�g
	 * 
	 * @return�@���O�o�̓��b�Z�[�W
	 */
	protected String createMsg(HttpServletRequest request, String viewName, String loginId, List<ValidationFailure> errors){
		
		// �F�؂��������Ă���ꍇ�́A�������b�Z�[�W�𕜋A
		if ("success".equals(viewName)){
			return "login is success (id=" + loginId + ")";

		}
		

		// �G���[���b�Z�[�W���ݒ肳��Ă���ꍇ�A�G���[���b�Z�[�W�̃^�C�v����A�J�E���g���b�N���������Ă���̂���
		// ���肷��B
		if (errors != null) {
			boolean locked = false;
			for (ValidationFailure failuer : errors){
				if ("userlock".equals(failuer.getType())) {
					locked = true;
					break;
				}
			}
			
			if (locked){
				return "login user is locked (id=" + loginId + ")";
			}
		}


		// �ʏ�̃��O�C���G���[�Ƃ��ĕ��A�B
		return "login is failed (id=" + loginId + ")";
	}

}
