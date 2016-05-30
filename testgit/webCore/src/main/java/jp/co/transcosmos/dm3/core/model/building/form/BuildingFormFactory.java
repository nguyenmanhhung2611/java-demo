package jp.co.transcosmos.dm3.core.model.building.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * <pre>
 * ������񌟍��E�ꗗ�p Form �� Factory �N���X
 * getInstance() ���ASpring ����C���X�^���X���擾���Ďg�p���鎖�B
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.27	�V�K�쐬
 *
 * ���ӎ���
 * Factory �̃C���X�^���X�𒼐ڐ������Ȃ����B�@�K��getInstance() �Ŏ擾���鎖�B
 *
 * </pre>
 */
public class BuildingFormFactory {
	/** Form �𐶐����� Factory �� Bean ID */
	protected static String FACTORY_BEAN_ID = "buildingFormFactory";
	
	/** �����O�X�o���f�[�V�����p���[�e�B���e�B */
    protected LengthValidationUtils lengthUtils;
    
	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;
	
	/**
	 * �����O�X�o���f�[�V�����p���[�e�B���e�B��ݒ肷��B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����p���[�e�B���e�B
	 */
	public void setLengthUtils(LengthValidationUtils lengthUtils) {
		this.lengthUtils = lengthUtils;
	}

	/**
	 * ���ʃR�[�h�ϊ��I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param codeLookupManager
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	
	/**
	 * �������̌������ʁA����ь����������i�[������ BuildingSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� BuildingSearchForm �C���X�^���X 
	 */
	public BuildingSearchForm createBuildingSearchForm(){
		return new BuildingSearchForm(this.lengthUtils);
	}

	/**
	 * �������̌������ʁA����ь����������i�[���� BuildingSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * BuildingSearchForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� BuildingSearchForm �C���X�^���X 
	 */
	public BuildingSearchForm createBuildingSearchForm(HttpServletRequest request){
		BuildingSearchForm form = createBuildingSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}
	
	/**
	 * BuildingFormFactory �̃C���X�^���X���擾����B<br/>
	 * Spring �̃R���e�L�X�g����A BuildingFormFactory �Œ�`���ꂽ BuildingFormFactory ��
	 * �C���X�^���X���擾����B<br/>
	 * �擾�����C���X�^���X�́ABuildingFormFactory ���p�������g���N���X�����A�����ꍇ������B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return BuildingFormFactory�A�܂��͌p�����Ċg�������N���X�̃C���X�^���X
	 */
	public static BuildingFormFactory getInstance(HttpServletRequest request) {

		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (BuildingFormFactory)springContext.getBean(BuildingFormFactory.FACTORY_BEAN_ID);
	}
	
	/**
	 * �������X�V���̓��͒l���i�[������ BuildingForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� BuildingForm �̃C���X�^���X
	 */
	public BuildingForm createBuildingForm(){
		return new BuildingForm(this.lengthUtils);
	}
	
	
	
	/**
	 * �������X�V���̓��͒l���i�[������ BuildingForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� BuildingForm �̃C���X�^���X
	 */
	public BuildingForm createBuildingForm(HttpServletRequest request){
		BuildingForm form = createBuildingForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * �Ŋ��w���X�V���̓��͒l���i�[������ BuildingStationInfoForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� BuildingStationInfoForm �̃C���X�^���X
	 */
	public BuildingStationInfoForm createBuildingStationInfoForm(
			HttpServletRequest request) {
		BuildingStationInfoForm form = createBuildingStationInfoForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}
	
	/**
	 * �������X�V���̓��͒l���i�[������ BuildingForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� BuildingForm �̃C���X�^���X
	 */
	public BuildingStationInfoForm createBuildingStationInfoForm(){
		return new BuildingStationInfoForm(this.lengthUtils, this.codeLookupManager);
	}
	
	/**
	 * �n����X�V���̓��͒l���i�[������ BuildingLandmarkForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� BuildingLandmarkForm �̃C���X�^���X
	 */
	public BuildingLandmarkForm createBuildingLandmarkForm(){
		return new BuildingLandmarkForm(this.lengthUtils, this.codeLookupManager);
	}
	
	/**
	 * �n����X�V���̓��͒l���i�[������ BuildingLandmarkForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� BuildingLandmarkForm �̃C���X�^���X
	 */
	public BuildingLandmarkForm createBuildingLandmarkForm(
			HttpServletRequest request) {
		BuildingLandmarkForm form = createBuildingLandmarkForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}
}
