package jp.co.transcosmos.dm3.webAdmin.housingBrowse.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.model.ReformManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingStatusForm;

import org.springframework.web.servlet.ModelAndView;

/**
 * ���t�H�[�����폜
 * <p>
 * �y�폜�̏ꍇ�z<br/>
 * <ul>
 * <li>���t�H�[�����f�[�^���폜��������B</li>
 * </ul>
 *
 * �S����        �C����       �C�����e
 * ------------ ----------- -----------------------------------------------------
 * Zhang.       2015.03.11	�V�K�쐬
 * Duong.Nguyen 2015.08.18  Use form submission to delete reform plan instead of using ajax. So remove json result such as map of reform plans, user information, etc... in MAV, put request models to MAV.  
 *
 * ���ӎ���
 *
 * </pre>
 */
public class HousingBrowseDelReformPlanCommand implements Command {

	/** ������񃁃��e�i���X�p Model */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/** ���t�H�[�������Ǘ��p Model */
	private ReformManage reformManager;

	/**
	 * ������񃁃��e�i���X�p Model�icore�j ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param panaHousingManager
	 *            ������񃁃��e�i���X�p Model�icore�j
	 */
	public void setPanaHousingManager(
			PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
	}

	/**
	 * ���t�H�[�������Ǘ��p Model�icore�j ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param reformManager
	 *            ���t�H�[�������Ǘ��p Model�icore�j
	 */
	public void setReformManager(ReformManage reformManager) {
		this.reformManager = reformManager;
	}

    /**
     * ���t�H�[�������͉�ʏ���<br>
     * <br>
     *
     * @param request
     *            HTTP ���N�G�X�g
     * @param response
     *            HTTP ���X�|���X
     */
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
        // ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
        Map<String, Object> model = createModel(request);
        PanaHousingStatusForm form = (PanaHousingStatusForm) model.get("inputForm");

        // ���[�U�[���擾
        AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);

        // ���t�H�[�����f�[�^���폜
        reformManager.delReformPlan(form.getSysHousingCd(), form.getSysReformCd(), (String) loginUser.getUserId());

        // �������̃^�C���X�^���v���X�V
        panaHousingManager.updateEditTimestamp(form.getSysHousingCd(), (String) loginUser.getUserId());

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
		PanaHousingStatusForm requestForm = factory
				.createPanaHousingStatusForm(request);
		model.put("inputForm", requestForm);

		// ���������A����сA��ʃR���g���[���p�����[�^���擾����B
		PanaHousingSearchForm searchForm = factory
				.createPanaHousingSearchForm(request);
		model.put("searchForm", searchForm);

		return model;
	}
}
