package jp.co.transcosmos.dm3.webAdmin.housingStatus.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingStatusForm;
import jp.co.transcosmos.dm3.login.UserRoleSet;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * �����{�����
 *
 * �y�V�K�o�^�̏ꍇ�z
 *  ���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B
 *  �o���f�[�V����������I�������ꍇ�A�����{������V�K�o�^����B
 *  ���J�敪������l�̏ꍇ�A�����{�������V�K�o�^����B
 *
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * Liu.Yandong		2015.03.11	�V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class HousingStatusAddCommand implements Command {
	/**
	 * �V�K�o�^��ʕ\������<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g
	 * @param response
	 *            HTTP ���X�|���X
	 */
	// �p�^�[���`�F�b�N
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
		Map<String, Object> model = createModel(request);
		PanaHousingStatusForm form = (PanaHousingStatusForm) model
				.get("inputForm");

		// ���[�U�[���擾
		UserRoleSet userRole = AdminLoginUserUtils.getInstance(request)
				.getLoginUserRole(request, response);

		// ��ʊi�[
		form.setDefaultData(userRole);

		return new ModelAndView("success", model);
	}

	/**
	 * model �I�u�W�F�N�g���쐬����B<br/>
	 * <br/>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g
	 * @return �p�����[�^��ݒ肵�� Form ���i�[���� model �I�u�W�F�N�g�𕜋A����B
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		PanaHousingFormFactory factory = PanaHousingFormFactory
				.getInstance(request);
		PanaHousingStatusForm form = factory
				.createPanaHousingStatusForm(request);
		model.put("inputForm", form);

		// ���������A����сA��ʃR���g���[���p�����[�^���擾����B
		PanaHousingSearchForm searchForm = factory.createPanaHousingSearchForm(request);
		model.put("searchForm", searchForm);

		return model;
	}
}
