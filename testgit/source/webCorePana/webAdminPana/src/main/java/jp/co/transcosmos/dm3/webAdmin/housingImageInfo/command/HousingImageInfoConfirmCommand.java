package jp.co.transcosmos.dm3.webAdmin.housingImageInfo.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingImageInfoForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.fileupload.FileItem;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * �����摜�����͊m�F���
 * ���N�G�X�g�p�����[�^�œn���ꂽ�����摜���̃o���f�[�V�������s���A�m�F��ʂ�\������B
 * �����o���f�[�V�����G���[�����������ꍇ�͓��͉�ʂƂ��ĕ\������B
 *
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *    �E"input" : �o���f�[�V�����G���[�ɂ��ē���
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * fan			2015.04.11	�V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class HousingImageInfoConfirmCommand implements Command {

	/** Panasonic�p�t�@�C�������֘A����Util */
	private PanaFileUtil fileUtil;

	/**
	 * Panasonic�p�t�@�C�������֘A����Util��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param fileUtil
	 *            Panasonic�p�t�@�C�������֘A����Util
	 */
	public void setFileUtil(PanaFileUtil fileUtil) {
		this.fileUtil = fileUtil;
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

    /**
     * ���͊m�F��ʕ\������<br>
     * <br>
     *
     * @param request
     *            HTTP ���N�G�X�g
     * @param response
     *            HTTP ���X�|���X
     */
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

    	Map<String, Object> model = new HashMap<String, Object>();

    	// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);

		// ���N�G�X�g�p�����[�^���i�[���� form �I�u�W�F�N�g�𐶐�����B
		Object[] forms = factory.createPanaHousingImgInfoFormAndSearchForm(request);

		// ���N�G�X�g�p�����[�^���i�[���� form �I�u�W�F�N�g�𐶐�����B
		PanaHousingImageInfoForm form = (PanaHousingImageInfoForm)forms[0];
		PanaHousingSearchForm searchForm = (PanaHousingSearchForm)forms[1];

		model.put("searchForm", searchForm);

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
            setResultLists(form);
            // �G���[�I�u�W�F�N�g�ƁA�t�H�[���I�u�W�F�N�g��ModelAndView �ɓn���Ă���
            model.put("errors", errors);

            form.setOldRoleId(roleId);
            form.setOldImageType(imageType);
            model.put("housingImageInfoForm", form);

            return new ModelAndView("validationError", model);
        }

        // �X�V�����̏ꍇ
        if ("update".equals(form.getCommand())) {
            // ���폈��
            setResultLists(form);
            // �t�H�[���I�u�W�F�N�g�݂̂�ModelAndView �ɓn���Ă���
            model.put("housingImageInfoForm", form);
            return new ModelAndView("success", model);
        }

        // path = "\data\reform\temp\"
        String temPath = PanaFileUtil.getUploadTempPath();

        // �V�K�o�^�����̏ꍇ
        String[] addHidPath = new String[form.getAddFilePath().length];
        String[] addHidFileName = new String[form.getAddFilePath().length];
        String[] addHidPathMin = new String[form.getAddFilePath().length];
        for (int idx = 0; idx < form.getAddFilePath().length; ++idx) {
            FileItem fi = form.getAddFilePath()[idx];
            if (fi != null && !StringUtils.isEmpty(fi.getName())) {

                // �C���[�W�P���A�b�v���[�h���A�߂�l�F�t�@�C�������擾
                String fileName = panaHousingManager.getReformJpgFileName();
                // �A�b�v���[�h
                this.panaHousingManager.addTempFile(fi, temPath, fileName);

                String urlPathMax = fileUtil.getHousFileTempUrl(temPath+"/"+this.commonParameters.getThumbnailSizes().get(this.commonParameters.getThumbnailSizes().size() - 1).toString(),fileName);
                String urlPathMin = fileUtil.getHousFileTempUrl(temPath+"/"+this.commonParameters.getThumbnailSizes().get(0).toString(),fileName);
                // �A�b�v���[�h�����T�[�o�[����URL�p�X��ݒ�
                addHidPath[idx] =  urlPathMax;
                addHidFileName[idx] = fileName;
                addHidPathMin[idx] = urlPathMin;
            }
        }
        form.setAddHidPathMin(addHidPathMin);
        form.setAddHidPath(addHidPath);
        form.setAddHidFileName(addHidFileName);
        setResultLists(form);

        // �t�H�[���I�u�W�F�N�g�݂̂�ModelAndView �ɓn���Ă���
        model.put("housingImageInfoForm", form);

        return new ModelAndView("success", model);
    }

    /**
     * �m�F��ʕ\���pform��ݒ肷�鏈���B <br>
     *
     * @param form
     *            �t�H�[���ڍ׏����form�B
     * @return �m�F��ʕ\���pform
     */
    private void setResultLists(PanaHousingImageInfoForm form) {
        if (form.getDivNo() != null) {
            String[] delLength = new String[form.getDivNo().length];
            for (int i = 0; i < form.getDivNo().length; i++) {
                if (form.getDivNo()[i] != null && form.getDivNo()[i] != "") {

                    if (form.getDelFlg() != null) {
                        for (int j = 0; j < form.getDelFlg().length; j++) {
                            if (i == Integer.valueOf(form.getDelFlg()[j])) {
                                delLength[i] = "1";
                                break;
                            } else {
                                delLength[i] = "0";
                            }
                        }
                    } else {
                        delLength[i] = "0";
                    }
                }
            }
            form.setDelFlg(delLength);
        }
    }
}
