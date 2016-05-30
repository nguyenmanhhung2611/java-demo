package jp.co.transcosmos.dm3.webFront.information.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.InformationManage;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.Information;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.vo.MemberInfo;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.login.LoginUser;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * �}�C�y�[�W���
 * ���N�G�X�g�p�����[�^�œn���ꂽ�����ڍׂ̃o���f�[�V�������s���A�����ڍ׉�ʂ�\������B
 *
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *
 * �S����       �C����     �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �s�����C     2015.04.13  �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class InformationDetailedCommand implements Command {

	/** ���m�点�����e�i���X���s�� Model �I�u�W�F�N�g */
	private InformationManage informationManager;

	/** ���ʃp�����[�^�I�u�W�F�N�g */
	private PanaCommonParameters commonParameters;

	/** �}�C�y�[�W���[�U�[�̏��Ǘ��p Model */
	private MypageUserManage mypageUserManager;

	/**
	 * ���m�点�����e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param informationManager ���m�点�����e�i���X�� model �I�u�W�F�N�g
	 */
	public void setInformationManager(InformationManage informationManager) {
		this.informationManager = informationManager;
	}
	/**
	 * ���ʃp�����[�^�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param commonParameters ���ʃp�����[�^�I�u�W�F�N�g
	 */
	public void setCommonParameters(PanaCommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * �}�C�y�[�W���[�U�[�̏��Ǘ��p Model�icore�j ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param mypageUserManager
	 *            �}�C�y�[�W���[�U�[�̏��Ǘ��p Model�icore�j
	 */
	public void setMypageUserManager(MypageUserManage mypageUserManager) {
		this.mypageUserManager = mypageUserManager;
	}

	/**
	 * �}�C�y�[�W��ʕ\������<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g
	 * @param response
	 *            HTTP ���X�|���X
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
		Map<String, Object> model = createModel(request);

		// ���ʃp�����[�^���
		model.put("commonParameters", this.commonParameters);

		// �t�����g���̃��[�U���
		LoginUser loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);
		String loginUserId = (String)loginUser.getUserId();
		// ���m�点�ԍ�
		String informationNo = request.getParameter("informationNo");
		if(StringUtils.isEmpty(informationNo)) {
			throw new RuntimeException("���m�点�ԍ����w�肳��Ă��܂���.");
		}

		// ���q�l�ւ̂��m�点�̎擾
		Information information = this.informationManager.searchMyPageInformationPk(informationNo, loginUserId);

		if (information == null) {
			return new ModelAndView("404");
		}
		// �}�C�y�[�W���[�U�[���̎擾
		JoinResult joinResult = this.mypageUserManager.searchMyPageUserPk(loginUserId);
		MemberInfo memberInfo = (MemberInfo)joinResult.getItems().get("memberInfo");

		// �������s���s���B
		model.put("information", information);
		model.put("memberInfo", memberInfo);

		return new ModelAndView("success", model);
	}

	/**
	 * ���N�G�X�g�p�����[�^���� outPutForm �I�u�W�F�N�g���쐬����B<br/>
	 * �������� outPutForm �I�u�W�F�N�g�� Map �Ɋi�[���ĕ��A����B<br/>
	 * key = �t�H�[���N���X���i�p�b�P�[�W�Ȃ��j�AValue = �t�H�[���I�u�W�F�N�g <br/>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^���ݒ肳�ꂽ�t�H�[���I�u�W�F�N�g���i�[���� Map �I�u�W�F�N�g
	 */
	private Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<>();
		return model;
	}
}
