package jp.co.transcosmos.dm3.webAdmin.reformImg.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.reform.ReformPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformFormFactory;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformImgForm;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * ���t�H�[���摜�ҏW�o�^�m�F���
 *
 * <pre>
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 *   ��		  2015.03.10    �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class ReformImgCompCommand implements Command {

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

    /**
     * ���t�H�[���摜�ҏW��ʓo�^�A�X�V�A�폜����<br>
     * <br>
     *
     * @param request
     *            HTTP ���N�G�X�g
     * @param response
     *            HTTP ���X�|���X
     */
    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();

        ReformFormFactory factory = ReformFormFactory.getInstance(request);

        // ���N�G�X�g�p�����[�^���A�t�H�[���I�u�W�F�N�g�֐ݒ肷��B
        ReformImgForm form = factory.createRefromImgForm(request);

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
        model.put("housingInfo", housingInfo);

        // ���t�H�[���֘A�����ꊇ�Ǎ�
        Map<String, Object> reform = this.reformManager.searchReform(form.getSysReformCd());

        // ���t�H�[���ڍׂ̌���
        List<ReformImg> reformImgresults = (List<ReformImg>) reform.get("imgList");
        // �{������
        String[] roleId = new String[reformImgresults.size()];
        for (int i = 0; i < reformImgresults.size(); i++) {
        	 // �{������
            roleId[i] = reformImgresults.get(i).getRoleId();
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
            form.setEditOldRoleId(roleId);
            model.put("ReformImgForm", form);
            return new ModelAndView("validationError", model);
        }

        // �o�^�̏ꏊ
        if ("insert".equals(form.getCommand())) {
            // ���t�H�[���摜�̒ǉ�����
            this.reformManager.addReformImg(form);
        } else {
            if (form.getDivNo() != null) {
                // ���t�H�[���摜�̍X�V�����i�X�V�E�폜�j
                this.reformManager.updateReformImg(form);
            }
        }

        // ���O�C�����[�U�[�̏����擾
        AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);
        // ���[�UID���擾
        String userId = String.valueOf(loginUser.getUserId());

        // ������{���̍ŏI�X�V���ƍŏI�X�V�҂��X�V�̏���
        this.reformManager.updateEditTimestamp(form.getSysHousingCd(), form.getSysReformCd(), userId);

        // �t�H�[���I�u�W�F�N�g�݂̂�ModelAndView �ɓn���Ă���
        model.put("ReformImgForm", form);
        return new ModelAndView("success", model);

    }
}
