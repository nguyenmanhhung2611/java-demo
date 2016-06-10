package jp.co.transcosmos.dm3.webAdmin.reformDtl.command;

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
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformDtlForm;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformFormFactory;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;

import org.springframework.web.servlet.ModelAndView;

/**
 * ���t�H�[���ڍ׏�񌟍����.
 * <p>
 * ���͂��ꂽ�������������Ƀ��t�H�[�������������A�ꗗ�\������B<br/>
 * ���������̓��͂ɖ�肪����ꍇ�A���������͍s��Ȃ��B<br/>
 * <br/>
 * �y���A���� View ���z<br/>
 * <ul>
 * <li>success
 * <li>:������������I��
 * <li>validFail
 * <li>:�o���f�[�V�����G���[
 * </ul>
 * <p>
 *
 * <pre>
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * fan	        2015.03.10  �V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class ReformDtlInputCommand implements Command {

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
     * @param reformManage
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
     * ���t�H�[���ڍ׏�񌟍����N�G�X�g����<br>
     * ���t�H�[���ڍ׏�񌟍��̃��N�G�X�g���������Ƃ��ɌĂяo�����B <br>
     *
     * @param request
     *            �N���C�A���g�����Http���N�G�X�g�B
     * @param response
     *            �N���C�A���g�ɕԂ�Http���X�|���X�B
     */
    @SuppressWarnings("unchecked")
    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // ���N�G�X�g�p�����[�^���A�t�H�[���I�u�W�F�N�g�֐ݒ肷��B
        Map<String, Object> model = new HashMap<String, Object>();
        ReformFormFactory factory = ReformFormFactory.getInstance(request);
        ReformDtlForm form = factory.createReformDtlForm(request);

        PanaHousingFormFactory housingFactory = PanaHousingFormFactory.getInstance(request);
        PanaHousingSearchForm searchForm = housingFactory.createPanaHousingSearchForm(request);
        model.put("searchForm", searchForm);


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

        if (reformPlan == null) {
            throw new NotFoundException();
        }

        // ���t�H�[���ڍ׏����͊m�F��ʂ́u�߂�v�{�^������Ԃ��ꍇ
        if ("back".equals(form.getCommand())) {
            // ��ʕ\���p�A�b�v���[�h�摜�ҏW���̍Đݒ�

            model.put("reformDtlForm", form);

            // �擾�����f�[�^�������_�����O�w�֓n��
            return new ModelAndView("success", model);
        }

        // ���t�H�[���ڍׂ̌���
        List<ReformDtl> reformDtlList = (List<ReformDtl>) reform.get("dtlList");

        // ��ʍ��ڂ�ݒ肷�鏈��
        if (reformDtlList != null && reformDtlList.size() > 0) {

            form.setDefaultData(reformDtlList);
            setUrlList(reformDtlList, model, form);
        }

        model.put("reformDtlForm", form);

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
	private void setUrlList(List<ReformDtl> reformDtlList,Map<String, Object> model, ReformDtlForm form) {

		String[] hidPath;

		if (null != form.getDivNo()) {
			hidPath = new String[form.getDivNo().length];
		} else {
			hidPath = new String[0];
		}

		int count = 0;
        for (ReformDtl rd : reformDtlList) {
            String urlPath = "";
            // �{������������݂̂̏ꍇ
            if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(rd.getRoleId())) {
                urlPath = fileUtil.getHousFileMemberUrl(rd.getPathName(),rd.getFileName(),this.commonParameters.getAdminSitePdfFolder());
            } else {
                // �{���������S���̏ꍇ
                urlPath = fileUtil.getHousFileOpenUrl(rd.getPathName(),rd.getFileName(),this.commonParameters.getAdminSitePdfFolder());
            }

            hidPath[count] = urlPath;
            count++;
        }
        form.setHidPath(hidPath);
	}
}
