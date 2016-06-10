package jp.co.transcosmos.dm3.webAdmin.reformImg.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.ReformManage;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformFormFactory;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformImgForm;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;

import org.springframework.web.servlet.ModelAndView;

/**
 * ���t�H�[���摜�ҏW���
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
public class ReformImgInputCommand implements Command {

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
    /** ���t�H�[����񃁃��e�i���X���s�� Model �I�u�W�F�N�g */
    private ReformManage reformManager;

    /**
     * ���t�H�[����񃁃��e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
     * <br/>
     *
     * @param reformManager
     *            ���t�H�[����񃁃��e�i���X�� model �I�u�W�F�N�g
     */
    public void setReformManager(ReformManage reformManager) {
        this.reformManager = reformManager;
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
     * ���t�H�[���摜�ҏW��ʕ\������<br>
     * <br>
     *
     * @param request
     *            HTTP ���N�G�X�g
     * @param response
     *            HTTP ���X�|���X
     */
    @SuppressWarnings("unchecked")
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // ���ʗp��model
        Map<String, Object> model = new HashMap<String, Object>();

    	ReformFormFactory factory = ReformFormFactory.getInstance(request);

        // �y�[�W�����p�̃t�H�[���I�u�W�F�N�g���쐬
        ReformImgForm reformImgform = factory.createRefromImgForm(request);

        PanaHousingFormFactory housingFactory = PanaHousingFormFactory.getInstance(request);
        PanaHousingSearchForm searchForm = housingFactory.createPanaHousingSearchForm(request);
        model.put("searchForm", searchForm);

        // �f�[�^�̎擾
        // ������{���e�[�u���̓Ǎ�
        HousingInfo housingInfo = this.reformManager.searchHousingInfo(reformImgform.getSysHousingCd());
        if (housingInfo.getSysHousingCd() == null) {
            throw new NotFoundException();
        }
        model.put("housingInfo", housingInfo);

        // ���t�H�[���֘A�����ꊇ�Ǎ�
        Map<String, Object> reform = this.reformManager.searchReform(reformImgform.getSysReformCd());

        // ���t�H�[���v�������̓Ǎ�
        ReformPlan reformPlan = (ReformPlan) reform.get("reformPlan");

        if (reformPlan == null) {
            throw new NotFoundException();
        }

        // ���t�H�[���摜���̎擾
        List<ReformImg> reformImgresults = (List<ReformImg>) reform.get("imgList");

        // �߂�{�^������
        if ("back".equals(reformImgform.getCommand())) {
            model.put("ReformImgForm", reformImgform);
            // �擾�����f�[�^�������_�����O�w�֓n��
            return new ModelAndView("success", model);
        }

        // ��ʍ��ڂ�ݒ肷�鏈��
        if (reformImgresults != null && reformImgresults.size() > 0) {

            reformImgform.setDefaultData(reformImgresults);
            setUrlList(reformImgresults,model,reformImgform);
        }

        model.put("ReformImgForm", reformImgform);

        // �擾�����f�[�^�������_�����O�w�֓n��
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
    private void setUrlList(List<ReformImg> reformImgresults,Map<String, Object> model , ReformImgForm form) {

    	if(form.getDivNo()!=null){

    		String[] beforeHidPathMax = new String[form.getDivNo().length];
            String[] beforeHidPathMin = new String[form.getDivNo().length];
            String[] afterHidPathMax = new String[form.getDivNo().length];
            String[] afterHidPathMin = new String[form.getDivNo().length];
            int count =0;
            for (ReformImg rd : reformImgresults) {
                String urlBeforePathMin = "";
                String urlBeforePathMax = "";
                String urlAfterPathMin = "";
                String urlAfterPathMax = "";
                // �{������������݂̂̏ꍇ
                if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(rd.getRoleId())) {
                	urlBeforePathMin = fileUtil.getHousFileMemberUrl(rd.getBeforePathName(),rd.getBeforeFileName(),this.commonParameters.getThumbnailSizes().get(0).toString());
                	urlBeforePathMax = fileUtil.getHousFileMemberUrl(rd.getBeforePathName(),rd.getBeforeFileName(),this.commonParameters.getThumbnailSizes().get(this.commonParameters.getThumbnailSizes().size()-1).toString());
                	urlAfterPathMin = fileUtil.getHousFileMemberUrl(rd.getAfterPathName(),rd.getAfterFileName(),this.commonParameters.getThumbnailSizes().get(0).toString());
                	urlAfterPathMax = fileUtil.getHousFileMemberUrl(rd.getAfterPathName(),rd.getAfterFileName(),this.commonParameters.getThumbnailSizes().get(this.commonParameters.getThumbnailSizes().size()-1).toString());
                } else {
                    // �{���������S���̏ꍇ
                	urlBeforePathMin = fileUtil.getHousFileOpenUrl(rd.getBeforePathName(),rd.getBeforeFileName(),this.commonParameters.getThumbnailSizes().get(0).toString());
                	urlBeforePathMax = fileUtil.getHousFileOpenUrl(rd.getBeforePathName(),rd.getBeforeFileName(),this.commonParameters.getThumbnailSizes().get(this.commonParameters.getThumbnailSizes().size()-1).toString());
                	urlAfterPathMin = fileUtil.getHousFileOpenUrl(rd.getAfterPathName(),rd.getAfterFileName(),this.commonParameters.getThumbnailSizes().get(0).toString());
                	urlAfterPathMax = fileUtil.getHousFileOpenUrl(rd.getAfterPathName(),rd.getAfterFileName(),this.commonParameters.getThumbnailSizes().get(this.commonParameters.getThumbnailSizes().size()-1).toString());
                }

                beforeHidPathMin[count]=urlBeforePathMin;

                beforeHidPathMax[count]=urlBeforePathMax;

                afterHidPathMin[count]=urlAfterPathMin;

                afterHidPathMax[count]=urlAfterPathMax;

                count++;
            }
            form.setBeforeHidPathMin(beforeHidPathMin);
            form.setBeforeHidPathMax(beforeHidPathMax);
            form.setAfterHidPathMin(afterHidPathMin);
            form.setAfterHidPathMax(afterHidPathMax);
    	}

    }
}
