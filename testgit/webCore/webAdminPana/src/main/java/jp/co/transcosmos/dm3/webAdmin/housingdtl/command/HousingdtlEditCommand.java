package jp.co.transcosmos.dm3.webAdmin.housingdtl.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingDtlInfoForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * �����ڍ׏��ҏW���
 * ���N�G�X�g�p�����[�^�œn���ꂽ�����ڍ׏��̃o���f�[�V�������s���A�m�F��ʂ�\������B
 * �����o���f�[�V�����G���[�����������ꍇ�͓��͉�ʂƂ��ĕ\������B
 *
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *    �E"input" : �o���f�[�V�����G���[�ɂ��ē���
 *
 * �S����       �C����     �C�����e
 * ------------ ----------- -----------------------------------------------------
 * liu.yandong     2015.03.17  �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class HousingdtlEditCommand implements Command {


	/** �������[�h (edit = �ҏW�AeditBack = �ĕҏW) */
	private String mode;

	/** �������p Model �I�u�W�F�N�g */
	private PanaHousingPartThumbnailProxy panaHousingManage;

	/** ���ʃp�����[�^�I�u�W�F�N�g */
	protected PanaCommonParameters commonParameters;

	/** Panasonic�p�t�@�C�������֘A����Util */
	private PanaFileUtil panaFileUtil;

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
	 * @param PanaHousingManage �������p Model �I�u�W�F�N�g
	 */
	public void setPanaHousingManage(PanaHousingPartThumbnailProxy panaHousingManage) {
		this.panaHousingManage = panaHousingManage;
	}

	/**
	 * @param commonParameters �Z�b�g���� commonParameters
	 */
	public void setCommonParameters(PanaCommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * @param panaFileUtil �Z�b�g���� panaFileUtil
	 */
	public void setPanaFileUtil(PanaFileUtil panaFileUtil) {
		this.panaFileUtil = panaFileUtil;
	}

	/**
	 * �����ڍ׏��ҏW��ʕ\������<br>
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
		PanaHousingDtlInfoForm inputForm = (PanaHousingDtlInfoForm) model.get("inputForm");

		// �������[�h�𔻒f����B
		if ("edit".equals(this.mode)) {
			inputForm = (PanaHousingDtlInfoForm) model.get("inputForm");

			// �������s���s���B
			execute(inputForm);
			// �v���r���[�摜�p�p�X�ݒ�
			if (!StringValidateUtil.isEmpty(inputForm.getPictureDataPath()) &&
					!StringValidateUtil.isEmpty(inputForm.getPictureDataFileName())) {
				String previewImgPath = this.panaFileUtil.getHousFileOpenUrl(inputForm.getPictureDataPath(), inputForm.getPictureDataFileName(), this.commonParameters.getAdminSiteStaffFolder());
				inputForm.setPreviewImgPath(previewImgPath);
			}
		} else if ("editBack".equals(this.mode)) {
			// ��ʒl�𕜋A�\������B
			inputForm = (PanaHousingDtlInfoForm) model.get("inputForm");
			// �S���Ҏʐ^�̍Đݒ�
			setStaffImgInfo(inputForm);
			// �v���r���[�摜�p�p�X�ݒ�
			if (!StringValidateUtil.isEmpty(inputForm.getPictureDataPath()) &&
					!StringValidateUtil.isEmpty(inputForm.getPictureDataFileName())) {
				String previewImgPath = this.panaFileUtil.getHousFileOpenUrl(inputForm.getPictureDataPath(), inputForm.getPictureDataFileName(), this.commonParameters.getAdminSiteStaffFolder());
				inputForm.setPreviewImgPath(previewImgPath);
			} else {
				inputForm.setPreviewImgPath(null);
			}
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
		PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);
		PanaHousingDtlInfoForm requestForm = factory.createPanaHousingDtlInfoForm(request);
		PanaHousingSearchForm searchForm = factory.createPanaHousingSearchForm(request);

		model.put("inputForm", requestForm);
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
	private void execute(PanaHousingDtlInfoForm inputForm) throws Exception, NotFoundException {

		// ���������擾����B
		Housing housing = this.panaHousingManage.searchHousingPk(inputForm.getSysHousingCd(), true);

		// �f�[�^�̑��݂��Ȃ��ꍇ�B
		if (housing == null) {
			throw new NotFoundException();
		}

		// Form���Z�b�g����B
		inputForm.setDefaultData(housing);
	}
	/**
	 * �S���Ҏʐ^�̍Đݒ�<br/>
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
	private void setStaffImgInfo(PanaHousingDtlInfoForm inputForm) throws Exception, NotFoundException  {
		// ���������擾����B
		Housing housing = this.panaHousingManage.searchHousingPk(inputForm.getSysHousingCd(), true);

		// �f�[�^�̑��݂��Ȃ��ꍇ�B
		if (housing == null) {
			throw new NotFoundException();
		}
        // �����g�����������擾����B
        Map<String, Map<String, String>> extMap = housing.getHousingExtInfos();
        // �J�e�S�����ɊY������ Map ���擾����B
        Map<String, String> cateMap = extMap.get("housingDetail");
        if (cateMap != null) {
            inputForm.setPictureDataPath(cateMap.get("staffImagePathName"));
            inputForm.setPictureDataFileName(cateMap.get("staffImageFileName"));
            inputForm.setPictureUpFlg(null);
        } else {
            inputForm.setPictureDataPath(null);
            inputForm.setPictureDataFileName(null);
            inputForm.setPictureUpFlg(null);
        }

	}
}
