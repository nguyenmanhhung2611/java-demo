package jp.co.transcosmos.dm3.webAdmin.housingImageInfo.command;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingImageInfoForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * �����摜���̒ǉ��A�ύX�A�폜����.
 * <p>
 * �y�V�K�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B</li>
 * <li>�o���f�[�V����������I�������ꍇ�A�����摜����V�K�o�^����B</li>
 * <li>�܂��A���J�悪����l�̏ꍇ�A���t�H�[�����J������V�K�o�^����B</li>
 * </ul>
 * <br/>
 * �y�X�V�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B</li>
 * <li>�o���f�[�V����������I�������ꍇ�A�����摜�����X�V����B</li>
 * <li>���t�H�[�����J����͈�x�폜���A�ύX��̌��J�悪����l�ł���΁A���t�H�[�����J������V�K�o�^����B</li>
 * <li>�����A�X�V�Ώۃf�[�^�����݂��Ȃ��ꍇ�A�X�V�������p���ł��Ȃ��̂ŊY��������ʂ�\������B</li>
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
 *
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * fan			2015.04.12	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class HousingImageInfoCompCommand implements Command {

    /** Housing�����e�i���X���s�� Model �I�u�W�F�N�g */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/**
	 * Housing�����e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param panaHousingManager
	 *            Housing�����e�i���X�� model �I�u�W�F�N�g
	 */
	public void setPanaHousingManager(PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
	}

    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

    	Map<String, Object> model = new HashMap<String, Object>();

        // ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
 		PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);

 		// ���N�G�X�g�p�����[�^���i�[���� form �I�u�W�F�N�g�𐶐�����B
 		PanaHousingImageInfoForm form = factory.createPanaHousingImageInfoForm(request);
		PanaHousingSearchForm searchForm = factory.createPanaHousingSearchForm(request);
		model.put("searchForm", searchForm);

        // ���O�C�����[�U�[�̏����擾
        AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);

        // ���[�UID���擾
        String userId = String.valueOf(loginUser.getUserId());

        String command = form.getCommand();
        String sysHousingCd = form.getSysHousingCd();
		if (command != null && command.equals("redirect")) {
			model.put("sysHousingCd", sysHousingCd);
			return new ModelAndView("comp", model);
		}

		// ���O�`�F�b�N:������{���e�[�u���̓Ǎ�
		Housing housingresults = this.panaHousingManager.searchHousingPk(
				form.getSysHousingCd(), true);

		if (housingresults == null) {
			// �f�[�^�̑��݂��Ȃ��ꍇ,���b�Z�[�W�F"�o0�p������񂪑��݂��Ȃ�"�\��
			throw new NotFoundException();
		}

		List<jp.co.transcosmos.dm3.core.vo.HousingImageInfo> housingImageInfoList = housingresults
				.getHousingImageInfos();

		// ��ʍ��ڂ�ݒ肷�鏈��
    	// �摜�^�C�v
    	String[] imageType = new String[housingImageInfoList.size()];
        // �{������
    	String[] roleId = new String[housingImageInfoList.size()];

        for (int i = 0; i < housingImageInfoList.size(); i++) {
            imageType[i] = String.valueOf(((jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo)housingImageInfoList.get(i)).getImageType());
            roleId[i] = String.valueOf(((jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo)housingImageInfoList.get(i)).getRoleId());
        }

        // �o���f�[�V��������
        Validateable validateableForm = (Validateable) form;
        // �G���[���b�Z�[�W�p�̃��X�g���쐬
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();

        // �o���f�[�V���������s
        if (!validateableForm.validate(errors)) {
            // �G���[����
            // �G���[�I�u�W�F�N�g�ƁA�t�H�[���I�u�W�F�N�g��ModelAndView �ɓn���Ă���
            model.put("errors", errors);
            form.setOldRoleId(roleId);
            form.setOldImageType(imageType);
            model.put("housingImageInfoForm", form);
            return new ModelAndView("validationError", model);
        }

        // ���폈��
 		 //�u�N�����v�t�H���_�쐬�p
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        // ���t�H���_���@�i���t�����j
        form.setTempDate(dateFormat.format(new Date()));

        if ("insert".equals(form.getCommand())) {
        	form.setRoleId(form.getAddRoleId());
        	form.setImageType(form.getAddImageType());
        	form.setSortOrder(form.getAddSortOrder());
        	form.setFileName(form.getAddHidFileName());
        	form.setImgComment(form.getAddImgComment());
        	// ���t�H�[���ڍ׏��̐V�K�ǉ�
            this.panaHousingManager.addHousingImg(form, userId);
        } else if ("update".equals(form.getCommand())) {
            // ���t�H�[���ڍ׏��̍X�V����
            this.panaHousingManager.updHousingImg(form, userId);
        }

        // �t�H�[���I�u�W�F�N�g�݂̂�ModelAndView �ɓn���Ă���
        model.put("housingImageInfoForm", form);
        return new ModelAndView("success", model);

    }
}
