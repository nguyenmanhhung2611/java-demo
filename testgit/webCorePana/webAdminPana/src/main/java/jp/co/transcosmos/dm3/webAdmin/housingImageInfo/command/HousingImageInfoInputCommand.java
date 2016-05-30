package jp.co.transcosmos.dm3.webAdmin.housingImageInfo.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingImageInfoForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.dao.JoinResult;

import org.springframework.web.servlet.ModelAndView;

/**
 * �����摜��񌟍����.
 * <p>
 * ���͂��ꂽ�������������ɕ����摜�����������A�ꗗ�\������B<br/>
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
 * fan	        2015.04.10  �V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class HousingImageInfoInputCommand implements Command {


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
	 *
	 * @param commonParameters
	 *            ���ʃp�����[�^�I�u�W�F�N�g
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
	public void setPanaHousingManager(
			PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
	}

	/**
	 * �����摜��񌟍����N�G�X�g����<br>
	 * �����摜��񌟍��̃��N�G�X�g���������Ƃ��ɌĂяo�����B <br>
	 *
	 * @param request
	 *            �N���C�A���g�����Http���N�G�X�g�B
	 * @param response
	 *            �N���C�A���g�ɕԂ�Http���X�|���X�B
	 */
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

		// ���O�`�F�b�N:������{���e�[�u���̓Ǎ�
		Housing housingresults = this.panaHousingManager.searchHousingPk(
				form.getSysHousingCd(), true);

		if (housingresults == null) {
			// �f�[�^�̑��݂��Ȃ��ꍇ,���b�Z�[�W�F"�o0�p������񂪑��݂��Ȃ�"�\��
			throw new NotFoundException();
		}

		List<jp.co.transcosmos.dm3.core.vo.HousingImageInfo> housingImageInfoList = housingresults
				.getHousingImageInfos();

		// ���t�H�[���ڍ׏����͊m�F��ʂ́u�߂�v�{�^������Ԃ��ꍇ
		if ("back".equals(form.getCommand())) {
			// ��ʕ\���p�A�b�v���[�h�摜�ҏW���̍Đݒ�
			model.put("housingImageInfoForm", form);
			// �擾�����f�[�^�������_�����O�w�֓n��
			return new ModelAndView("success", model);
		}

		JoinResult housingInfo = housingresults.getHousingInfo();
		if (housingInfo != null) {
			// ��������
			form.setDisplayHousingName(((HousingInfo) housingInfo.getItems()
					.get("housingInfo")).getDisplayHousingName());
			// �����ԍ�
			form.setHousingCd(((HousingInfo) housingInfo.getItems().get(
					"housingInfo")).getHousingCd());
		}

		form.setDefaultData(housingImageInfoList);

		setUrlList(housingImageInfoList, model, form);

		model.put("housingImageInfoForm", form);

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
	private void setUrlList(
			List<jp.co.transcosmos.dm3.core.vo.HousingImageInfo> housingImageInfoList,
			Map<String, Object> model, PanaHousingImageInfoForm form) {

		String[] hidPathMax;
		String[] hidPathMin;
		if (null != form.getDivNo()) {
			hidPathMax = new String[form.getDivNo().length];
			hidPathMin = new String[form.getDivNo().length];
		} else {
			hidPathMax = new String[0];
			hidPathMin = new String[0];
		}

		int count = 0;
		for (jp.co.transcosmos.dm3.core.vo.HousingImageInfo coreHi : housingImageInfoList) {
			jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo hi = (jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo) coreHi;
			String urlPathMin = "";
			String urlPathMax = "";
			// �{������������݂̂̏ꍇ
			if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(hi.getRoleId())) {
				urlPathMin = fileUtil.getHousFileMemberUrl(hi.getPathName(),hi.getFileName(),this.commonParameters.getThumbnailSizes().get(0).toString());
				urlPathMax = fileUtil.getHousFileMemberUrl(hi.getPathName(),hi.getFileName(),this.commonParameters.getThumbnailSizes().get(this.commonParameters.getThumbnailSizes().size() - 1).toString());
			} else {
				// �{���������S���̏ꍇ
				urlPathMin = fileUtil.getHousFileOpenUrl(hi.getPathName(),hi.getFileName(),this.commonParameters.getThumbnailSizes().get(0).toString());
				urlPathMax = fileUtil.getHousFileOpenUrl(hi.getPathName(),hi.getFileName(),this.commonParameters.getThumbnailSizes().get(this.commonParameters.getThumbnailSizes().size() - 1).toString());
			}

			hidPathMin[count] = urlPathMin;

			hidPathMax[count] = urlPathMax;

			count++;
		}
		form.setHidPathMax(hidPathMax);
		form.setHidPathMin(hidPathMin);
	}
}
