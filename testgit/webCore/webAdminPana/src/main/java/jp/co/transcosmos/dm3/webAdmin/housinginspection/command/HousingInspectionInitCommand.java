package jp.co.transcosmos.dm3.webAdmin.housinginspection.command;

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
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingInspectionForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingInspection;
import jp.co.transcosmos.dm3.dao.JoinResult;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * �Z��f�f�����͊m�F���
 * ���N�G�X�g�p�����[�^�œn���ꂽ���t�H�[�����̃o���f�[�V�������s���A�m�F��ʂ�\������B
 * �����o���f�[�V�����G���[�����������ꍇ�͓��͉�ʂƂ��ĕ\������B
 *
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *    �E"input" : �o���f�[�V�����G���[�ɂ��ē���
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * fan			2015.04.21	�V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class HousingInspectionInitCommand implements Command {

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
	/** �Z��f�f��񃁃��e�i���X���s�� Model �I�u�W�F�N�g */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/**
	 * �Z��f�f��񃁃��e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param panaHousingManager
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

	/**
	 * �Z��f�f����ʕ\������<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g
	 * @param response
	 *            HTTP ���X�|���X
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);
		// ���N�G�X�g�p�����[�^���i�[���� form �I�u�W�F�N�g�𐶐�����B
		PanaHousingInspectionForm form = factory.createPanaHousingInspectionForm(request);
		PanaHousingSearchForm searchForm = factory.createPanaHousingSearchForm(request);

		// ���ʗp��model
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("searchForm", searchForm);

		// ���O�`�F�b�N
		Housing housingresults = this.panaHousingManager.searchHousingPk(form.getSysHousingCd(),true);
		if (housingresults == null) {
			throw new NotFoundException();
		}
		JoinResult housingInfo = housingresults.getHousingInfo();
        JoinResult buildingInfo = housingresults.getBuilding().getBuildingInfo();

		// �Z��f�f���{�L���̕����g���������̎擾
		Map<String,String> housingExtInfo = housingresults.getHousingExtInfos().get("housingInspection");

		// �߂�̃R�����g�̔��f
		if ("back".equals(form.getCommand())) {
			model.put("HousingInspectionForm", form);
			return new ModelAndView("success", model);
		}

		// �f�f���ʂ̑I�����ڂ̒�`hashtable���擾
		List<HousingInspection> HousingInspectionList = this.panaHousingManager.searchHousingInspection(form.getSysHousingCd());
		// �����C���X�y�N�V�������̎擾

		form.setDefaultData(housingInfo,buildingInfo,HousingInspectionList,housingExtInfo);

		setUrlList(model, form);
		model.put("HousingInspectionForm", form);

		return new ModelAndView("success", model);

	}

	 /**
     * �摜�\���p��ݒ肷�鏈���B <br>
     *
     * @param housingImageInfoList
     * @param model
     *
     * @return
     */
    private void setUrlList(Map<String, Object> model,PanaHousingInspectionForm form) {

		 String urlPathImg = "";
         String urlPathPdf = "";
         // �{������������݂̂̏ꍇ

         urlPathImg = fileUtil.getHousFileMemberUrl(form.getImgFilePath(),form.getImgFile(),this.commonParameters.getAdminSiteChartFolder());
         urlPathPdf = fileUtil.getHousFileMemberUrl(form.getLoadFilePath(),form.getLoadFile(),this.commonParameters.getAdminSitePdfFolder());

         form.setHidImgPath(urlPathImg);
         form.setHidPath(urlPathPdf);

    }
}