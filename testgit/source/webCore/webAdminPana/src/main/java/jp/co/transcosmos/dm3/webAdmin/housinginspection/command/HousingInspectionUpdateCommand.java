package jp.co.transcosmos.dm3.webAdmin.housinginspection.command;

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
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingInspectionForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;
/**
 * �Z��f�f���ύX�������
 * <p>
 * �y�V�K�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B</li>
 * <li>�o���f�[�V����������I�������ꍇ�A�X�e�[�^�X��V�K�o�^����B</li>
 * </ul>
 * <br/>
 * �y�X�V�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B</li>
 * <li>�o���f�[�V����������I�������ꍇ�A�X�e�[�^�X�����X�V����B</li>
 * <li>�����A�X�V�Ώۃf�[�^�����݂��Ȃ��ꍇ�A�X�V�������p���ł��Ȃ��̂ŊY��������ʂ�\������B</li>
 * </ul>
 * <br/>
 * �y���A���� View ���z<br/>
 * <ul>
 * <li>success</li>:����I���i���_�C���N�g�y�[�W�j
 * <li>notFound</li>:�Y���f�[�^�����݂��Ȃ��ꍇ�i�X�V�����̏ꍇ�j
 * <li>comp</li>:������ʕ\��
 * </ul>
 * <p>
 *
 * <pre>
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * fan			2015.04.21	�V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class HousingInspectionUpdateCommand implements Command {

	/** �Z��f�f��񃁃��e�i���X���s�� Model �I�u�W�F�N�g */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/**
	 * �Z��f�f��񃁃��e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param housingManager
	 *            �Z��f�f��񃁃��e�i���X�� model �I�u�W�F�N�g
	 */
	public void setPanaHousingManager(PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
	}
	/** ���ʃp�����[�^�I�u�W�F�N�g */
    private PanaCommonParameters commonParameters;

    /**
     * ���ʃp�����[�^�I�u�W�F�N�g��ݒ肷��B<br/>
     * <br/>
     * @param commonParameters ���ʃp�����[�^�I�u�W�F�N�g
     */
    public void setCommonParameters(PanaCommonParameters commonParameters) {
        this.commonParameters = commonParameters;
    }

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);
		// ���N�G�X�g�p�����[�^���i�[���� form �I�u�W�F�N�g�𐶐�����B
		PanaHousingInspectionForm form = factory.createPanaHousingInspectionForm(request);
		PanaHousingSearchForm searchForm = factory.createPanaHousingSearchForm(request);
		// ���O�C�����[�U�[�̏����擾����B�@�i�^�C���X�^���v�̍X�V�p�j
		AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);

		// ���[�UID���擾
        String userId = String.valueOf(loginUser.getUserId().toString());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("searchForm", searchForm);

		String command = form.getCommand();
		String sysHousingCd = form.getSysHousingCd();
		if (command != null && command.equals("redirect")) {
			model.put("sysHousingCd", sysHousingCd);
			return new ModelAndView("comp", model);
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
			model.put("HousingInspectionForm", form);
			return new ModelAndView("validationError", model);
		}

		// ���O�`�F�b�N:������{���e�[�u���̓Ǎ�
		Housing housing = this.panaHousingManager.searchHousingPk(form
				.getSysHousingCd(),true);
		if (housing == null) {
			// ���b�Z�[�W�FFORM.�����ԍ�������񂪑��݂��Ȃ�"�\��
			throw new NotFoundException();
		}
		// �����C���X�y�N�V��������DB����
		// �����C���X�y�N�V��������update����
		this.panaHousingManager.delHousingInspection(form.getSysHousingCd());

		// ���t�H�[�����̒ǉ�����
		if (form.getInspectionKey() != null) {

			for (int i = 0; i < form.getInspectionKey().length; i++) {
				if(!StringValidateUtil.isEmpty(form.getInspectionTrust_result()[i])){
					this.panaHousingManager.addHousingInspection(form, i);
				}
			}
		}
		// �Z��f�f���{�̕����g����������DB����
		this.panaHousingManager.updExtInfo(form.getSysHousingCd(), "housingInspection","inspectionExist", form.getHousingInspection(), userId);

		// �摜�t�@�C���̃p�X���擾�p
		HousingImageInfo imgInfo = new HousingImageInfo();
		imgInfo.setRoleId(PanaCommonConstant.ROLE_ID_PRIVATE);

		// �Z��f�f�t�@�C�����̏���
		if ("on".equals(form.getHousingDel()) && !"1".equals(form.getLoadFlg())) {
			this.panaHousingManager.delExtInfo(form.getSysHousingCd(),"housingInspection", "inspectionPathName", userId);
			this.panaHousingManager.delExtInfo(form.getSysHousingCd(),"housingInspection", "inspectionFileName", userId);

			// �Z��f�f�t�@�C���̍폜���������s����B
			this.panaHousingManager.deleteImgFile(form.getLoadFilePath(), form.getLoadFile() ,imgInfo,this.commonParameters.getAdminSitePdfFolder());
		}else if("1".equals(form.getLoadFlg())){

			// �p�X����ݒ肷��B
			String filePath = this.panaHousingManager.createImagePath(housing);

			// �Z��f�f�t�@�C�����̏���
			this.panaHousingManager.updExtInfo(form.getSysHousingCd(),"housingInspection", "inspectionPathName", filePath, userId);
			this.panaHousingManager.updExtInfo(form.getSysHousingCd(),"housingInspection", "inspectionFileName", form.getAddHidFileName(), userId);

			// ���[�h�ς݂̃t�@�C�����폜���A�ŐV�̃t�@�C�������[�h����B
			this.panaHousingManager.deleteImgFile(form.getLoadFilePath(), form.getLoadFile() ,imgInfo,this.commonParameters.getAdminSitePdfFolder());
			this.panaHousingManager.addImgFile(form.getSysHousingCd(), form.getAddHidFileName() ,imgInfo,this.commonParameters.getAdminSitePdfFolder());
		}

		// �摜�t�@�C�����̏���
		if ("on".equals(form.getHousingImgDel()) && !"1".equals(form.getImgFlg())) {
			this.panaHousingManager.delExtInfo(form.getSysHousingCd(),"housingInspection", "inspectionImagePathName", userId);
			this.panaHousingManager.delExtInfo(form.getSysHousingCd(),"housingInspection", "inspectionImageFileName", userId);

			// �摜�t�@�C���̍폜���������s����B
			this.panaHousingManager.deleteImgFile(form.getImgFilePath(), form.getImgFile() ,imgInfo,this.commonParameters.getAdminSiteChartFolder());
		}else if("1".equals(form.getImgFlg())){
			// �p�X����ݒ肷��B
			String filePath = this.panaHousingManager.createImagePath(housing);

			// �摜�t�@�C�����̏���
			this.panaHousingManager.updExtInfo(form.getSysHousingCd(),"housingInspection", "inspectionImagePathName", filePath, userId);
			this.panaHousingManager.updExtInfo(form.getSysHousingCd(),"housingInspection", "inspectionImageFileName", form.getAddHidImgName(), userId);

			// ���[�h�ς݂̃t�@�C�����폜���A�ŐV�̃t�@�C�������[�h����B
			this.panaHousingManager.deleteImgFile(form.getImgFilePath(), form.getImgFile() ,imgInfo,this.commonParameters.getAdminSiteChartFolder());
			this.panaHousingManager.addImgFile(form.getSysHousingCd(), form.getAddHidImgName() ,imgInfo,this.commonParameters.getAdminSiteChartFolder());
		}



        // ������{���̍ŏI�X�V���ƍŏI�X�V�҂��X�V�̏���
		this.panaHousingManager.updateEditTimestamp(form.getSysHousingCd(),userId);

		model.put("HousingInspectionForm", form);
		return new ModelAndView("success", model);

	}

}