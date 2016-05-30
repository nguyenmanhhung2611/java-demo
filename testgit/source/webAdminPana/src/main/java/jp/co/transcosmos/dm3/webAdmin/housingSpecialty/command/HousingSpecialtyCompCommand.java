package jp.co.transcosmos.dm3.webAdmin.housingSpecialty.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSpecialtyForm;
import jp.co.transcosmos.dm3.form.FormPopulator;

import org.springframework.web.servlet.ModelAndView;

/**
 * �����ݔ����̒ǉ��A�폜����.
 * <p>
 * �y�V�K�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B</li>
 * <li>�o���f�[�V����������I�������ꍇ�A�����ݔ�����V�K�o�^����B</li>
 * </ul>
 * <br/>
 * �y���A���� View ���z<br/>
 * <ul>
 * <li>success</li>:����I���i���_�C���N�g�y�[�W�j
 * <li>redirect</li>:redirect��ʕ\��
 * <li>comp</li>:������ʕ\��
 * </ul>
 * <p>
 *
 * <pre>
 * �S����       �C����     �C�����e
 * ------------ ----------- -----------------------------------------------------
 * liu.yandong  2015.04.10  �V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class HousingSpecialtyCompCommand implements Command {

	/** �������p Model �I�u�W�F�N�g */
    protected PanaHousingPartThumbnailProxy panaHousingManager;

    /**
	 * �������p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param PanaHousingPartThumbnailProxy �������p Model �I�u�W�F�N�g
	 */
    public void setPanaHousingManager(PanaHousingPartThumbnailProxy panaHousingManager) {
        this.panaHousingManager = panaHousingManager;
    }

	/**
	 * �����ݔ����̒ǉ��A�ύX�A�폜����<br>
	 * <br>
	 * @param request �N���C�A���g�����Http���N�G�X�g�B
	 * @param response �N���C�A���g�ɕԂ�Http���X�|���X�B
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
        Map<String, Object> model = createModel(request);
        PanaHousingSpecialtyForm inputForm = (PanaHousingSpecialtyForm) model.get("inputForm");

        // ���O�C�����[�U�[�̏����擾����B�@�i�^�C���X�^���v�̍X�V�p�j
        AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);

        // ������ʂŃ����[�h�����ꍇ�A�X�V�������Ӑ}�������s������肪��������B
        // ���̖�����������ׁAview ���� "success"�@���w�肷��Ǝ������_�C���N�g��ʂ��\�������B
        // ���̃��_�C���N�g��ʂ́Acommand �p�����[�^�� "redirect"�@�ɐݒ肵�Ċ�����ʂփ��N�G�X�g��
        // ���M����B
        // ����āAcommand = "redirect" �̏ꍇ�́A�c�a�X�V�͍s�킸�A������ʂ�\������B
        String command = inputForm.getCommand();
        if (command != null && "redirect".equals(command)){
        	return new ModelAndView("comp" , model);
        }

        // �e�폈�������s
        // ������{�����擾����B
		Housing housing = this.panaHousingManager.searchHousingPk(inputForm.getSysHousingCd(), true);

		// �f�[�^�̑��݂��Ȃ��ꍇ�B
		if (housing == null) {
			throw new NotFoundException();
		}

        try {
        	this.panaHousingManager.updateHousingEquip(inputForm, (String)loginUser.getUserId());

        } catch (NotFoundException e) {
            // �V�X�e������CD�A�ݔ�CD�����݂��Ȃ��ꍇ�́A�Y���Ȃ���ʂ�
        	return new ModelAndView("notFound", model);

        }

		return new ModelAndView("success" , model);
	}

	/**
	 * model �I�u�W�F�N�g���쐬���A���N�G�X�g�p�����[�^���i�[���� form �I�u�W�F�N�g���i�[����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^��ݒ肵�� Form ���i�[���� model �I�u�W�F�N�g�𕜋A����B
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<>();

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);
		PanaHousingSpecialtyForm inputForm = factory.createPanaHousingSpecialtyForm();
        FormPopulator.populateFormBeanFromRequest(request, inputForm);
        model.put("inputForm", inputForm);

        // ���������A����сA��ʃR���g���[���p�����[�^���擾����B
 		PanaHousingSearchForm searchForm = factory.createPanaHousingSearchForm(request);
 		model.put("searchForm", searchForm);

		return model;
	}
}
