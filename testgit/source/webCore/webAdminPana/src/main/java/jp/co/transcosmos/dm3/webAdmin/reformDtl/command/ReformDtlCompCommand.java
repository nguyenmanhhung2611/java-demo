package jp.co.transcosmos.dm3.webAdmin.reformDtl.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.reform.ReformPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformDtlForm;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformFormFactory;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * ���t�H�[���ڍ׏��̒ǉ��A�ύX�A�폜����.
 * <p>
 * �y�V�K�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B</li>
 * <li>�o���f�[�V����������I�������ꍇ�A���t�H�[���ڍ׏���V�K�o�^����B</li>
 * <li>�܂��A���J�悪����l�̏ꍇ�A���t�H�[�����J������V�K�o�^����B</li>
 * </ul>
 * <br/>
 * �y�X�V�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B</li>
 * <li>�o���f�[�V����������I�������ꍇ�A���t�H�[���ڍ׏����X�V����B</li>
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
 * fan			2015.03.12	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class ReformDtlCompCommand implements Command {

    /** ���t�H�[����񃁃��e�i���X���s�� Model �I�u�W�F�N�g */
    private ReformPartThumbnailProxy reformManager;

    /**
     * ���t�H�[����񃁃��e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
     * <br/>
     *
     * @param reformManage
     *            ���t�H�[����񃁃��e�i���X�� model �I�u�W�F�N�g
     */
    public void setReformManager(ReformPartThumbnailProxy reformManager) {
        this.reformManager = reformManager;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        // ���N�G�X�g�p�����[�^���A�t�H�[���I�u�W�F�N�g�֐ݒ肷��B
        Map<String, Object> model = new HashMap<String, Object>();
        ReformFormFactory factory = ReformFormFactory.getInstance(request);

        // ���N�G�X�g�p�����[�^���A�t�H�[���I�u�W�F�N�g�֐ݒ肷��B
        ReformDtlForm form = factory.createReformDtlForm(request);

        PanaHousingFormFactory housingFactory = PanaHousingFormFactory.getInstance(request);
        PanaHousingSearchForm searchForm = housingFactory.createPanaHousingSearchForm(request);
        model.put("searchForm", searchForm);

        String command = form.getCommand();
        String sysHousingCd = form.getSysHousingCd();
        String sysReformCd = form.getSysReformCd();
        String housingCd = form.getHousingCd();
        String housingKindCd = form.getHousingKindCd();

		if (command != null && command.equals("redirect")) {
			model.put("sysHousingCd", sysHousingCd);
			model.put("sysReformCd", sysReformCd);
			model.put("housingCd", housingCd);
			model.put("housingKindCd", housingKindCd);
			return new ModelAndView("comp", model);
		}

        // ���O�`�F�b�N:������{���e�[�u���̓Ǎ�
        HousingInfo housingInfo = this.reformManager.searchHousingInfo(form.getSysHousingCd());
        if (housingInfo.getSysHousingCd() == null) {
            // �f�[�^�̑��݂��Ȃ��ꍇ,���b�Z�[�W�F"�o0�p������񂪑��݂��Ȃ�"�\��
            throw new NotFoundException();
        }
        model.put("housingInfo", housingInfo);

     // ���t�H�[���֘A�����ꊇ�Ǎ�
        Map<String, Object> reform = this.reformManager.searchReform(form.getSysReformCd());

        // ���t�H�[���v�������̓Ǎ�
        ReformPlan reformPlan = (ReformPlan) reform.get("reformPlan");

        // ���O�`�F�b�N
        if (reformPlan == null) {
            // �f�[�^�̑��݂��Ȃ��ꍇ,���b�Z�[�W�F"�o0�p�V�X�e�����t�H�[����񂪑��݂��Ȃ�"�\��
            throw new NotFoundException();
        }

        // ���t�H�[���ڍׂ̌���
        List<ReformDtl> reformDtlList = (List<ReformDtl>) reform.get("dtlList");
        // �{������
        String[] roleId = new String[reformDtlList.size()];
        for (int i = 0; i < reformDtlList.size(); i++) {
            roleId[i] = reformDtlList.get(i).getRoleId();
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
            // �{������
            form.setOldRoleId(roleId);
            model.put("reformDtlForm", form);
            return new ModelAndView("validationError", model);
        }

        // ���폈��
        // ���t�H�[���ڍ׏��̐V�K�ǉ�
        if ("insert".equals(form.getCommand())) {
            this.reformManager.addReformDtl(form);
        } else if ("update".equals(form.getCommand())) {

            this.reformManager.updateReformDtl(form);
        }

        // ���O�C�����[�U�[�̏����擾
        AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);
        // ���[�UID���擾
        String userId = String.valueOf(loginUser.getUserId());

        // ������{���̍ŏI�X�V���ƍŏI�X�V�҂��X�V�̏���
        this.reformManager.updateEditTimestamp(form.getSysHousingCd(), form.getSysReformCd(), userId);

        // �t�H�[���I�u�W�F�N�g�݂̂�ModelAndView �ɓn���Ă���
        model.put("reformDtlForm", form);
        return new ModelAndView("success", model);
    }
}
