package jp.co.transcosmos.dm3.corePana.model.building.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.model.building.form.BuildingFormFactory;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingLandmarkForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingSearchForm;
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
public class PanaBuildingFormFactory extends BuildingFormFactory {

    /**
     * InformationFormFactory �̃C���X�^���X���擾����B<br/>
     * Spring �̃R���e�L�X�g����A HousingListFormFactory �Œ�`���ꂽ InformationFormFactory ��
     * �C���X�^���X���擾����B<br/>
     * �擾�����C���X�^���X�́AHousingListFormFactory ���p�������g���N���X�����A�����ꍇ������B<br/>
     * <br/>
     * @param request HTTP ���N�G�X�g
     * @return HousingListFormFactory�A�܂��͌p�����Ċg�������N���X�̃C���X�^���X
     */
    public static PanaBuildingFormFactory getInstance(HttpServletRequest request) {
        WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request
                .getServletContext());
        return (PanaBuildingFormFactory) springContext.getBean(PanaBuildingFormFactory.FACTORY_BEAN_ID);
    }

    /**
     * �������̌������ʁA����ь����������i�[������ HousingListForm �̃C���X�^���X�𐶐�����B<br/>
     * <br/>
     * @return ��� HousingListForm �C���X�^���X
     */
    public PanaBuildingForm createPanaBuildingForm() {
        return new PanaBuildingForm(this.lengthUtils);
    }

    /**
     * �������̌������ʁA����ь����������i�[���� InformationSearchForm �̃C���X�^���X�𐶐�����B<br/>
     * HousingListForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
     * <br/>
     * @param request HTTP ���N�G�X�g
     * @return ���N�G�X�g�p�����[�^��ݒ肵�� HousingListForm �C���X�^���X
     */
    public PanaBuildingForm createPanaBuildingForm(HttpServletRequest request) {
        PanaBuildingForm form = createPanaBuildingForm();
        FormPopulator.populateFormBeanFromRequest(request, form);
        return form;
    }

    /**
     * �������̌������ʁA����ь����������i�[������ HousingListForm �̃C���X�^���X�𐶐�����B<br/>
     * <br/>
     * @return ��� HousingListForm �C���X�^���X
     */
    @Override
    public BuildingLandmarkForm createBuildingLandmarkForm() {
        return new PanaBuildingLandmarkForm(this.lengthUtils, this.codeLookupManager);
    }

    /**
     * �������̌������ʁA����ь����������i�[���� InformationSearchForm �̃C���X�^���X�𐶐�����B<br/>
     * HousingListForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
     * <br/>
     * @param request HTTP ���N�G�X�g
     * @return ���N�G�X�g�p�����[�^��ݒ肵�� HousingListForm �C���X�^���X
     */
    @Override
    public BuildingLandmarkForm createBuildingLandmarkForm(HttpServletRequest request) {
        BuildingLandmarkForm form = createBuildingLandmarkForm();
        FormPopulator.populateFormBeanFromRequest(request, form);
        return form;
    }

    /**
     * �������̌������ʁA����ь����������i�[������ HousingListForm �̃C���X�^���X�𐶐�����B<br/>
     * <br/>
     * @return ��� HousingListForm �C���X�^���X
     */
    public PanaBuildingStationInfoForm createPanaBuildingStationInfoForm() {
        return new PanaBuildingStationInfoForm(this.lengthUtils, this.codeLookupManager);
    }

    /**
     * �������̌������ʁA����ь����������i�[���� InformationSearchForm �̃C���X�^���X�𐶐�����B<br/>
     * HousingListForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
     * <br/>
     * @param request HTTP ���N�G�X�g
     * @return ���N�G�X�g�p�����[�^��ݒ肵�� HousingListForm �C���X�^���X
     */
    public PanaBuildingStationInfoForm createPanaBuildingStationInfoForm(HttpServletRequest request) {
        PanaBuildingStationInfoForm form = createPanaBuildingStationInfoForm();
        FormPopulator.populateFormBeanFromRequest(request, form);
        return form;
    }

	/**
	 * �����ꗗ�̌����������i�[������ BuildingSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� BuildingSearchForm �C���X�^���X
	 */
    @Override
	public BuildingSearchForm createBuildingSearchForm(){
		return new PanaBuildingSearchForm(this.lengthUtils);
	}

	/**
	 * �����ꗗ�̌����������i�[���� BuildingSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * BuildingSearchForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� BuildingSearchForm �C���X�^���X
	 */
    @Override
	public BuildingSearchForm createBuildingSearchForm(HttpServletRequest request){
    	BuildingSearchForm form = createBuildingSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}
}
