package jp.co.transcosmos.dm3.corePana.model.housing.form;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.model.housing.form.HousingFormFactory;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.form.FormPopulator;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * <pre>
 * ������񌟍��E�ꗗ�p Form �� Factory �N���X
 * getInstance() ���ASpring ����C���X�^���X���擾���Ďg�p���鎖�B
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * guo.zhonglei		2015.04.08	�V�K�쐬
 *
 * ���ӎ���
 * Factory �̃C���X�^���X�𒼐ڐ������Ȃ����B�@�K��getInstance() �Ŏ擾���鎖�B
 *
 * </pre>
 */
public class PanaHousingFormFactory extends HousingFormFactory {


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


	/**
	 * HousingFormFactory �̃C���X�^���X���擾����B<br/>
	 * Spring �̃R���e�L�X�g����A housingFormFactory �Œ�`���ꂽ HousingFormFactory ��
	 * �C���X�^���X���擾����B<br/>
	 * �擾�����C���X�^���X�́AHousingFormFactory ���p�������g���N���X�����A�����ꍇ������B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return HousingFormFactory�A�܂��͌p�����Ċg�������N���X�̃C���X�^���X
	 */
	public static PanaHousingFormFactory getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (PanaHousingFormFactory)springContext.getBean(HousingFormFactory.FACTORY_BEAN_ID);
	}

	/**
	 * �������̌������ʁA����ь����������i�[������ HousingListForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� HousingListForm �C���X�^���X
	 */
	public PanaHousingStatusForm createPanaHousingStatusForm() {
		return new PanaHousingStatusForm(this.codeLookupManager);
	}

	/**
	 * �������̌������ʁA����ь����������i�[���� InformationSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * HousingListForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� HousingListForm �C���X�^���X
	 */
	public PanaHousingStatusForm createPanaHousingStatusForm(HttpServletRequest request) {
		PanaHousingStatusForm form = createPanaHousingStatusForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * �����ڍ׏��̌������ʂ��i�[������ PanaHousingDtlInfoForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� PanaHousingDtlInfoForm �C���X�^���X
	 */
	public PanaHousingDtlInfoForm createPanaHousingDtlInfoForm() {
		return new PanaHousingDtlInfoForm(this.lengthUtils, this.codeLookupManager);
	}

	/**
	 * �����ڍ׏��̌������ʂ��i�[���� PanaHousingDtlInfoForm �̃C���X�^���X�𐶐�����B<br/>
	 * PanaHousingDtlInfoForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� PanaHousingDtlInfoForm �C���X�^���X
	 */
	public PanaHousingDtlInfoForm createPanaHousingDtlInfoForm(HttpServletRequest request) {
		PanaHousingDtlInfoForm form = createPanaHousingDtlInfoForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * �����ڍ׏��̌������ʁA����ь����������i�[����t�H�[���̃C���X�^���X�z��𐶐�����B<br/>
	 * forms �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵���t�H�[���C���X�^���X�z��
	 */
	public Object[] createPanaHousingDtlInfoFormAndSearchForm(HttpServletRequest request) {
		Object[] forms = new Object[2];
		forms[0] = createPanaHousingDtlInfoForm();
		forms[1] = createPanaHousingSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, forms, PanaCommonConstant.maxFileSize, new File(commonParameters.getUploadWorkPath()));
		return forms;
	}

	/**
	 * �����摜���̌������ʁA����ь����������i�[����t�H�[���̃C���X�^���X�z��𐶐�����B<br/>
	 * forms �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵���t�H�[���C���X�^���X�z��
	 */
	public Object[] createPanaHousingImgInfoFormAndSearchForm(HttpServletRequest request) {
		Object[] forms = new Object[2];
		forms[0] = createPanaHousingImageInfoForm();
		forms[1] = createPanaHousingSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, forms, PanaCommonConstant.maxFileSize, new File(commonParameters.getUploadWorkPath()));
		return forms;
	}

	/**
	 * �����摜���̌������ʁA����ь����������i�[����t�H�[���̃C���X�^���X�z��𐶐�����B<br/>
	 * forms �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵���t�H�[���C���X�^���X�z��
	 */
	public Object[] createPanaHousingInspectionFormAndSearchForm(HttpServletRequest request) {
		Object[] forms = new Object[2];
		forms[0] = createPanaHousingInspectionForm();
		forms[1] = createPanaHousingSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, forms, PanaCommonConstant.maxFileSize, new File(commonParameters.getUploadWorkPath()));
		return forms;
	}

	/**
	 * �������̌������ʁA����ь����������i�[������ HousingListForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� HousingListForm �C���X�^���X
	 */
	public PanaHousingForm createPanaHousingForm() {
		return new PanaHousingForm(this.lengthUtils, this.codeLookupManager);
	}

	/**
	 * �������̌������ʁA����ь����������i�[���� InformationSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * HousingListForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� HousingListForm �C���X�^���X
	 */
	public PanaHousingForm createPanaHousingForm(HttpServletRequest request) {
		PanaHousingForm form = createPanaHousingForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * �������̌������ʁA����ь����������i�[������ HousingListForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� HousingListForm �C���X�^���X
	 */
	public PanaHousingInfoForm createPanaHousingInfoForm() {
		return new PanaHousingInfoForm(this.codeLookupManager);
	}

	/**
	 * �������̌������ʁA����ь����������i�[���� InformationSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * HousingListForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� HousingListForm �C���X�^���X
	 */
	public PanaHousingInfoForm createPanaHousingInfoForm(HttpServletRequest request) {
		PanaHousingInfoForm form = createPanaHousingInfoForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * �����摜���̌������ʁA����ь����������i�[������ PanaHousingImageInfoForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� PanaHousingImageInfoForm �C���X�^���X
	 */
	public PanaHousingImageInfoForm createPanaHousingImageInfoForm() {
		return new PanaHousingImageInfoForm(this.codeLookupManager);
	}

	/**
	 * �����摜���̌������ʁA����ь����������i�[���� PanaHousingImageInfoForm �̃C���X�^���X�𐶐�����B<br/>
	 * PanaHousingImageInfoForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� PanaHousingImageInfoForm �C���X�^���X
	 */
	public PanaHousingImageInfoForm createPanaHousingImageInfoForm(HttpServletRequest request) {
		PanaHousingImageInfoForm form = createPanaHousingImageInfoForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * �������̌������ʁA����ь����������i�[������ HousingListForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� HousingListForm �C���X�^���X
	 */
	public PanaHousingInspectionForm createPanaHousingInspectionForm() {
		return new PanaHousingInspectionForm(this.codeLookupManager);
	}

	/**
	 * �������̌������ʁA����ь����������i�[���� InformationSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * HousingListForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� HousingListForm �C���X�^���X
	 */
	public PanaHousingInspectionForm createPanaHousingInspectionForm(HttpServletRequest request) {
		PanaHousingInspectionForm form = createPanaHousingInspectionForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * �������̌������ʁA����ь����������i�[������ HousingListForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� HousingListForm �C���X�^���X
	 */
	public PanaHousingSearchForm createPanaHousingSearchForm() {
		return new PanaHousingSearchForm(this.codeLookupManager, this.lengthUtils);
	}

	/**
	 * �������̌������ʁA����ь����������i�[���� InformationSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * HousingListForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� HousingListForm �C���X�^���X
	 */
	public PanaHousingSearchForm createPanaHousingSearchForm(HttpServletRequest request) {
		PanaHousingSearchForm form = createPanaHousingSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * �������̌������ʁA����ь����������i�[������ HousingListForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� HousingListForm �C���X�^���X
	 */
	public PanaHousingSpecialtyForm createPanaHousingSpecialtyForm() {
		return new PanaHousingSpecialtyForm();
	}

	/**
	 * �������̌������ʁA����ь����������i�[���� InformationSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * HousingListForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� HousingListForm �C���X�^���X
	 */
	public PanaHousingSpecialtyForm createPanaHousingSpecialtyForm(HttpServletRequest request) {
		PanaHousingSpecialtyForm form = createPanaHousingSpecialtyForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * �������̌������ʁA����ь����������i�[������ HousingListForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� HousingListForm �C���X�^���X
	 */
	public RecommendPointForm createRecommendPointForm() {
		return new RecommendPointForm(this.lengthUtils, this.codeLookupManager);
	}

	/**
	 * �������̌������ʁA����ь����������i�[���� InformationSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * HousingListForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� HousingListForm �C���X�^���X
	 */
	public RecommendPointForm createRecommendPointForm(HttpServletRequest request) {
		RecommendPointForm form = createRecommendPointForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * �������N�G�X�g���́A�m�F�A������ʂ� PanaHousingRequestForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� PanaHousingRequestForm �C���X�^���X
	 */
	public PanaHousingRequestForm createPanaHousingRequestForm() {
		return new PanaHousingRequestForm(this.codeLookupManager);
	}

	/**
	 * �������N�G�X�g���́A�m�F�A������ʂ̃C���X�^���X�𐶐�����B<br/>
	 * PanaHousingRequestForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� PanaHousingRequestForm �C���X�^���X
	 */
	public PanaHousingRequestForm createPanaHousingRequestForm(HttpServletRequest request) {
		PanaHousingRequestForm form = createPanaHousingRequestForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * �������������̓��͒l���i�[������ HousingSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� HousingSearchForm �C���X�^���X
	 */
	public PanaHousingSearchForm createHousingSearchForm(){
		return new PanaHousingSearchForm(codeLookupManager, lengthUtils);

	}



	/**
	 * �����������̓��͒l���i�[���� HousingSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * HousingSearchForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� HousingSearchForm �C���X�^���X
	 */
	public PanaHousingSearchForm createHousingSearchForm(HttpServletRequest request){
		PanaHousingSearchForm form = createHousingSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * ���W�ɊY�����镨���̌����������i�[������ FeatureSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� FeatureSearchForm �C���X�^���X
	 */
	public PanaFeatureSearchForm createFeatureSearchForm(){
		return new PanaFeatureSearchForm(codeLookupManager, lengthUtils);
	}



	/**
	 * ���W�ɊY�����镨���̌����������i�[���� FeatureSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * FeatureSearchForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� FeatureSearchForm �C���X�^���X
	 */
	public PanaFeatureSearchForm createFeatureSearchForm(HttpServletRequest request){
		PanaFeatureSearchForm form = createFeatureSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

}
