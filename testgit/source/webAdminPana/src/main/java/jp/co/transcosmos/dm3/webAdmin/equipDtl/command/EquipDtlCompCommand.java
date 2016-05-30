package jp.co.transcosmos.dm3.webAdmin.equipDtl.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.model.equipDtl.form.EquipDtlInfoForm;
import jp.co.transcosmos.dm3.corePana.model.equipDtl.form.EquipDtlInfoFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * �Ǘ��җp�ݔ����̒ǉ��A�폜����.
 * <p>
 * �y�V�K�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B</li>
 * <li>�o���f�[�V����������I�������ꍇ�A�Ǘ��җp�ݔ�����V�K�o�^����B</li>
 * </ul>
 * <br/>
 * �y���A���� View ���z<br/>
 * <ul>
 * <li>success</li>:����I���i���_�C���N�g�y�[�W�j
 * <li>input</li>:�o���f�[�V�����G���[�ɂ��ē���
 * <li>redirect</li>:redirect��ʕ\��
 * <li>comp</li>:������ʕ\��
 * </ul>
 * <p>
 *
 * <pre>
 * �S����       �C����     �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �s�����C     2015.4.14  �V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class EquipDtlCompCommand implements Command {

	/** ���ڐ��� */
	private final static int _CONT = 30;

	/** �ݔ����� */
	private final static String _EQUIP_CATEGORY = "adminEquipName";

	/** �ݔ� */
	private final static String _EQUIP = "adminEquipInfo";

	/** ���t�H�[�� */
	private final static String _REFORM = "adminEquipReform";

	/** �������[�h (insert = �V�K�o�^�����A update=�X�V����) */
	private String mode;

	/** �������p Model �I�u�W�F�N�g */
	private PanaHousingPartThumbnailProxy panaHousingManage;

	/**
	 * �������[�h��ݒ肷��<br/>
	 * <br/>
	 *
	 * @param mode
	 *            "insert" = �V�K�o�^�����A"update" = �X�V����
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
	 * �ݔ����̒ǉ��A�폜����<br>
	 * <br>
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
		EquipDtlInfoForm inputForm = (EquipDtlInfoForm) model.get("inputForm");

		// ���O�C�����[�U�[�̏����擾����B�@�i�^�C���X�^���v�̍X�V�p�j
		AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);

		// view ���̏����l��ݒ�
		String viewName = "success";

		String command = inputForm.getCommand();

		if (command != null && command.equals("redirect")) {
			return new ModelAndView("comp", model);
		}

		// �o���f�[�V���������s
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
		if (!inputForm.validate(errors)) {
			// �o���f�[�V�����G���[���́A�G���[���� model �ɐݒ肵�A���͉�ʂ�\������B
			model.put("inputForm", inputForm);
			model.put("errors", errors);
			viewName = "input";
			return new ModelAndView(viewName, model);
		}

		// ���O�`�F�b�N
		if (!targetExist(inputForm)) {
			throw new NotFoundException();
		}

		// �e�폈�������s
		execute(inputForm, loginUser);

		model.put("inputForm", inputForm);

		return new ModelAndView(viewName, model);
	}

	/**
	 * model �I�u�W�F�N�g���쐬���A���N�G�X�g�p�����[�^���i�[���� form �I�u�W�F�N�g���i�[����B<br/>
	 * <br/>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^��ݒ肵�� Form ���i�[���� model �I�u�W�F�N�g�𕜋A����B
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
	 * ���O�`�F�b�N���s���B<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
	 * @return true �f�[�^�����݂̏ꍇ�A false �f�[�^�����݂��Ȃ��ꍇ
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException
	 *                �X�V�Ώۂ����݂��Ȃ��ꍇ
	 */
	private boolean targetExist(EquipDtlInfoForm inputForm) throws Exception,
			NotFoundException {

		// ���������擾����B
		Housing housing = this.panaHousingManage.searchHousingPk(inputForm.getSysHousingCd(), true);

		// �f�[�^�̑��݂��Ȃ��ꍇ�B
		if (housing == null) {
			return false;
		}
		return true;
	}

	/**
	 * �����̐U�蕪���Ǝ��s���s���B<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
	 * @param loginUser
	 *            �^�C���X�^���v�X�V���Ɏg�p���郍�O�C�����[�U�[���
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException
	 *                �X�V�Ώۂ����݂��Ȃ��ꍇ
	 */
	private void execute(EquipDtlInfoForm inputForm, AdminUserInterface loginUser)
			throws Exception, NotFoundException {

		if ("update".equals(this.mode)) {
			// �o�^����
			update(inputForm, loginUser);
		}
	}

	/**
	 * �V�K�o�^����<br/>
	 * �����œn���ꂽ���e�Ń��t�H�[������ǉ�����B<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
	 * @param loginUser
	 *            �^�C���X�^���v�X�V���Ɏg�p���郍�O�C�����[�U�[���
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	private void update(EquipDtlInfoForm inputForm, AdminUserInterface loginUser)
			throws Exception {

		// �o�^���ƂȂ� Map �I�u�W�F�N�g
		Map<String, String> inputData = new HashMap<String, String>();

		int idx = 0;

		for (int i = 0; i < _CONT; i++) {
			if (!StringUtils.isEmpty(inputForm.getEquipCategory()[i])) {

				idx++;

				// �ݔ�����
				inputData.put(getkeyNameNm(_EQUIP_CATEGORY, idx), inputForm.getEquipCategory()[i]);

				// �ݔ�
				inputData.put((getkeyNameNm(_EQUIP, idx)), inputForm.getEquip()[i]);

				// ���t�H�[��
				inputData.put((getkeyNameNm(_REFORM, idx)), (StringUtils.isEmpty(inputForm.getReform()[i])) ? "0" : inputForm.getReform()[i]);
			}
		}

		// Map �� null �̏ꍇ�A�폜�݂̂����s���ĕ��A����B
		if (inputData.isEmpty()) {
			inputData = null;
		}

		// �o�^����
		this.panaHousingManage.updExtInfo(inputForm.getSysHousingCd(), "adminEquip", inputData, (String) loginUser.getUserId());

	}

	/**
	 * Key���𐶐�����<br/>
	 * <br/>
	 *
	 * @param str Key��
	 * @param i Key���ԍ�
	 * @return Key��
	 */
	private String getkeyNameNm(String str, int i) {
		StringBuffer keyName = new StringBuffer();
		keyName.append(str);
		keyName.append(String.format("%02d", i));
		return keyName.toString();
	}
}
