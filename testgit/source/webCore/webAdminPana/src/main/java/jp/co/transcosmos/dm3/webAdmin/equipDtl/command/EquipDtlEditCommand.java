package jp.co.transcosmos.dm3.webAdmin.equipDtl.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.corePana.model.equipDtl.form.EquipDtlInfoForm;
import jp.co.transcosmos.dm3.corePana.model.equipDtl.form.EquipDtlInfoFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousing;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * �Ǘ��җp�ݔ����ҏW���
 * ���N�G�X�g�p�����[�^�œn���ꂽ�Ǘ��җp�ݔ����̃o���f�[�V�������s���A�m�F��ʂ�\������B
 * �����o���f�[�V�����G���[�����������ꍇ�͓��͉�ʂƂ��ĕ\������B
 *
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *    �E"input" : �o���f�[�V�����G���[�ɂ��ē���
 *
 * �S����       �C����     �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �s�����C     2015.4.14  �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class EquipDtlEditCommand implements Command {

	/** �������[�h (edit = �ҏW�AeditBack = �ĕҏW) */
	private String mode;

	/** �������p Model �I�u�W�F�N�g */
	private PanaHousingPartThumbnailProxy panaHousingManage;

	/**
	 * �������[�h��ݒ肷��<br/>
	 * <br/>
	 *
	 * @param mode "edit" = �ҏW "editBack" = �ĕҏW
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * �������p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param PanaHousingPartThumbnailProxy �������p Model �I�u�W�F�N�g
	 */
	public void setPanaHousingManage(PanaHousingPartThumbnailProxy panaHousingManage) {
		this.panaHousingManage = panaHousingManage;
	}

	/**
	 * �Ǘ��җp�ݔ����ҏW��ʕ\������<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g
	 * @param response
	 *            HTTP ���X�|���X
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
		Map<String, Object> model = createModel(request);
		EquipDtlInfoForm inputForm = null;

		// �������[�h�𔻒f����B
		if ("edit".equals(this.mode)) {
			inputForm = (EquipDtlInfoForm) model.get("inputForm");
			// �L�[�ԍ���ݒ肷��B
			inputForm.init();

			// �������s���s���B
			execute(inputForm);

		} else if ("editBack".equals(this.mode)) {
			// ��ʒl����������B
			inputForm = (EquipDtlInfoForm) model.get("inputForm");
		}

		model.put("inputForm", inputForm);

		return new ModelAndView("success", model);
	}

	/**
	 * ���N�G�X�g�p�����[�^���� Form �I�u�W�F�N�g���쐬����B<br/>
	 * �������� Form �I�u�W�F�N�g�� Map �Ɋi�[���ĕ��A����B<br/>
	 * key = �t�H�[���N���X���i�p�b�P�[�W�Ȃ��j�AValue = �t�H�[���I�u�W�F�N�g <br/>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^���ݒ肳�ꂽ�t�H�[���I�u�W�F�N�g���i�[���� Map �I�u�W�F�N�g
	 */
	private Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<>();

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		EquipDtlInfoFormFactory factory = EquipDtlInfoFormFactory.getInstance(request);
		EquipDtlInfoForm requestForm = factory.createEquipDtlInfoForm(request);

		model.put("inputForm", requestForm);

		PanaHousingFormFactory housingFactory = PanaHousingFormFactory.getInstance(request);
		PanaHousingSearchForm searchForm = housingFactory.createPanaHousingSearchForm(request);
		model.put("searchForm", searchForm);

		return model;
	}

	/**
	 * �������s���s���B<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException
	 *                �X�V�Ώۂ����݂��Ȃ��ꍇ
	 */
	private void execute(EquipDtlInfoForm inputForm) throws Exception, NotFoundException {

		// ���������擾����B
		PanaHousing housing = (PanaHousing) this.panaHousingManage.searchHousingPk(inputForm.getSysHousingCd(), true);

		// �f�[�^�̑��݂��Ȃ��ꍇ�B
		if (housing == null) {
			throw new NotFoundException();
		}

		// Form���Z�b�g����B
		inputForm.setDefaultData(housing);
	}

}
