package jp.co.transcosmos.dm3.webAdmin.recommendPoint.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.RecommendPointForm;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * �������߃|�C���g�ҏW��ʂ̏����\��
 * �V�X�e������CD��肨�����߃|�C���g���f�[�^�𒊏o���A��ʂɕ\�������B
 *
 *
 * �y���A���� View ���z
 *    �E"success" : ������������I��
 *    �E"validFail" : �o���f�[�V�����G���[
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �s�����C     2015.4.10  �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class RecommendPointInputCommand implements Command {

	/** ���������s�� Model �I�u�W�F�N�g */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/**
	 * ������񃊃N�G�X�g����<br>
	 * �������̃��N�G�X�g���������Ƃ��ɌĂяo�����B <br>
	 *
	 * @param request
	 *            �N���C�A���g�����Http���N�G�X�g�B
	 * @param response
	 *            �N���C�A���g�ɕԂ�Http���X�|���X�B
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
        Map<String, Object> model = createModel(request);

		return new ModelAndView("success", model);
	}

	/**
	 * ���������s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param panaHousingManager �������� model �I�u�W�F�N�g
	 */
	public void setPanaHousingManager(PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
	}

	/**
	 * model �I�u�W�F�N�g���쐬���A���N�G�X�g�p�����[�^���i�[���� form �I�u�W�F�N�g���i�[����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^��ݒ肵�� Form ���i�[���� model �I�u�W�F�N�g�𕜋A����B
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
        PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);
        RecommendPointForm inputForm = factory.createRecommendPointForm(request);
        PanaHousingSearchForm searchForm = factory.createPanaHousingSearchForm(request);


		// ���̓t�H�[���̎󂯎��́Acommand �p�����[�^�̒l�ɂ���ĈقȂ�B
		// command �p�����[�^���n����Ȃ��ꍇ�A���͉�ʂ̏����\���Ƃ݂Ȃ��A��̃t�H�[���𕜋A����B
		// command �p�����[�^�� "back" ���n���ꂽ�ꍇ�A���͊m�F��ʂ���̕��A���Ӗ�����B
		// ���̏ꍇ�̓��N�G�X�g�p�����[�^���󂯎��A���͉�ʂ֏����\������B
		// �����A"back" �ȊO�̒l���n���ꂽ�ꍇ�Acommand �p�����[�^���n����Ȃ��ꍇ�Ɠ����Ɉ����B
		String command = inputForm.getCommand();

		if ("back".equals(command)){

			// �߂�{�^���̏ꍇ�́A���N�G�X�g�p�����[�^�̒l��ݒ肵�� Form �𕜋A����B
			model.put("inputForm", inputForm);
			model.put("searchForm", searchForm);
		} else {

		       // �V�X�e������CD
	        String sysHousingCd = request.getParameter("sysHousingCd");

	        // �V�X�e������CD��ݒ肷��
	        inputForm.setSysHousingCd(sysHousingCd);

	        // �V�X�e������CD �i��L�[�l�j�ɊY�����镨�������擾����
	        Housing housing = this.panaHousingManager.searchHousingPk(sysHousingCd, true);

			// �f�[�^�̑��݂��Ȃ��ꍇ�B
			if (housing == null) {
				throw new NotFoundException();
			}

	        // �擾�����l����͗p Form �֊i�[����B �i���͒l�̏����l�p�j
			inputForm.setDefaultData(housing, inputForm);

	        model.put("inputForm", inputForm);
	        model.put("searchForm", searchForm);
		}

		return model;

	}

}
