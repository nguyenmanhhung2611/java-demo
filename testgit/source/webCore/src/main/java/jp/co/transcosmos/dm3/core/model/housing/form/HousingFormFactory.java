package jp.co.transcosmos.dm3.core.model.housing.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * <pre>
 * �������p Form �� Factory �N���X
 * getInstance() ���ASpring ����C���X�^���X���擾���Ďg�p���鎖�B
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.11	�V�K�쐬
 *
 * ���ӎ���
 * Factory �̃C���X�^���X�𒼐ڐ������Ȃ����B�@�K��getInstance() �Ŏ擾���鎖�B
 *
 * </pre>
 */
public class HousingFormFactory {

	/** Form �𐶐����� Factory �� Bean ID */
	protected static String FACTORY_BEAN_ID = "housingFormFactory";

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
	 * HousingFormFactory �̃C���X�^���X���擾����B<br/>
	 * Spring �̃R���e�L�X�g����A housingFormFactory �Œ�`���ꂽ HousingFormFactory ��
	 * �C���X�^���X���擾����B<br/>
	 * �擾�����C���X�^���X�́AHousingFormFactory ���p�������g���N���X�����A�����ꍇ������B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return HousingFormFactory�A�܂��͌p�����Ċg�������N���X�̃C���X�^���X
	 */
	public static HousingFormFactory getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (HousingFormFactory)springContext.getBean(HousingFormFactory.FACTORY_BEAN_ID);
	}

	
	
	
	/**
	 * ������{���̓��͒l���i�[������ HousingForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� HousingForm �C���X�^���X 
	 */
	public HousingForm createHousingForm(){
		return new HousingForm(this.lengthUtils, this.codeLookupManager);
	}


	
	/**
	 * ������{���̓��͒l���i�[���� HousingForm �̃C���X�^���X�𐶐�����B<br/>
	 * HousingForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� HousingForm �C���X�^���X 
	 */
	public HousingForm createHousingForm(HttpServletRequest request){
		HousingForm form = createHousingForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}

	
	
	/**
	 * �����ڍ׏��̓��͒l���i�[������ HousingDtlForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� HousingDtlForm �C���X�^���X 
	 */
	public HousingDtlForm createHousingDtlForm(){
		return new HousingDtlForm(this.lengthUtils, this.codeLookupManager);
	}



	/**
	 * ������{���̓��͒l���i�[���� HousingDtlForm �̃C���X�^���X�𐶐�����B<br/>
	 * HousingDtlForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� HousingDtlForm �C���X�^���X 
	 */
	public HousingDtlForm createHousingDtlForm(HttpServletRequest request){
		HousingDtlForm form = createHousingDtlForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}
	
	
	
	/**
	 * �����ݔ����̓��͒l���i�[������ HousingEquipForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� HousingEquipForm �C���X�^���X 
	 */
	public HousingEquipForm createHousingEquipForm(){
		return new HousingEquipForm();
	}
	

	
	/**
	 * �����ݔ����̓��͒l���i�[���� HousingEquipForm �̃C���X�^���X�𐶐�����B<br/>
	 * HousingEquipForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� HousingEquipForm �C���X�^���X 
	 */
	public HousingEquipForm createHousingEquipForm(HttpServletRequest request){
		HousingEquipForm form = createHousingEquipForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}

	
	
	/**
	 * �����摜���̓��͒l���i�[������ HousingImgForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� HousingImgForm �C���X�^���X 
	 */
	public HousingImgForm createHousingImgForm(){
		return new HousingImgForm(this.lengthUtils, this.codeLookupManager);
	}

	
	
	/**
	 * �����摜���̓��͒l���i�[���� HousingImgForm �̃C���X�^���X�𐶐�����B<br/>
	 * HousingImgForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� HousingImgForm �C���X�^���X 
	 */
	public HousingImgForm createHousingImgForm(HttpServletRequest request){
		HousingImgForm form = createHousingImgForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}

	
	
	/**
	 * �������������̓��͒l���i�[������ HousingSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� HousingSearchForm �C���X�^���X 
	 */
	public HousingSearchForm createHousingSearchForm(){
		return new HousingSearchForm();
	}

	
	
	/**
	 * �����������̓��͒l���i�[���� HousingSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * HousingSearchForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� HousingSearchForm �C���X�^���X 
	 */
	public HousingSearchForm createHousingSearchForm(HttpServletRequest request){
		HousingSearchForm form = createHousingSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}

}
