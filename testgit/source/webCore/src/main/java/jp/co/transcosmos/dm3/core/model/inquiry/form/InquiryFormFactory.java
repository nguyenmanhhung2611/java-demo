package jp.co.transcosmos.dm3.core.model.inquiry.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * ���⍇���@�\�p Form �� Factory �N���X.
 * <p>
 * getInstance() ���ASpring ����C���X�^���X���擾���Ďg�p���鎖�B
 * <p>
 * <pre>
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.04.02	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * Factory �̃C���X�^���X�𒼐ڐ������Ȃ����B�@�K��getInstance() �Ŏ擾���鎖�B
 * 
 */
public class InquiryFormFactory {

	/** Form �𐶐����� Factory �� Bean ID */
	protected static String FACTORY_BEAN_ID = "inquiryFormFactory";
	
	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;

	/** �����O�X�o���f�[�V�����p���[�e�B���e�B */
    protected LengthValidationUtils lengthUtils;
    
    
	/**
	 * ���ʃR�[�h�ϊ��I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param codeLookupManager
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}
    
    
	/**
	 * �����O�X�o���f�[�V�����p���[�e�B���e�B��ݒ肷��B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����p���[�e�B���e�B
	 */
	public void setLengthUtils(LengthValidationUtils lengthUtils) {
		this.lengthUtils = lengthUtils;
	}

	/**
	 * InquiryFormFactory �̃C���X�^���X���擾����B<br/>
	 * Spring �̃R���e�L�X�g����A inquiryFormFactory �Œ�`���ꂽ InquiryFormFactory ��
	 * �C���X�^���X���擾����B<br/>
	 * �擾�����C���X�^���X�́AInquiryFormFactory ���p�������g���N���X�����A�����ꍇ������B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return InquiryFormFactory�A�܂��͌p�����Ċg�������N���X�̃C���X�^���X
	 */
	public static InquiryFormFactory getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (InquiryFormFactory)springContext.getBean(InquiryFormFactory.FACTORY_BEAN_ID);
	}



	/**
	 * �⍇���w�b�_�̓��͒l���i�[������ InquiryHeaderForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� InquiryHeaderForm �C���X�^���X 
	 */
	public InquiryHeaderForm createInquiryHeaderForm(){
		return new InquiryHeaderForm();
	}

	/**
	 * �⍇���w�b�_�̓��͒l���i�[���� InquiryHeaderForm �̃C���X�^���X�𐶐�����B<br/>
	 * InquiryHeaderForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� InquiryHeaderForm �C���X�^���X 
	 */
	public InquiryHeaderForm createInquiryHeaderForm(HttpServletRequest request){
		InquiryHeaderForm form = createInquiryHeaderForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}

	
	
	/**
	 * �����⍇���̓��͒l���i�[������ HousingInquiryForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� HousingInquiryForm �C���X�^���X 
	 */
	public HousingInquiryForm createHousingInquiryForm(){
		return new HousingInquiryForm();
	}

	/**
	 * �����⍇���w�b�_�̓��͒l���i�[���� HousingInquiryForm �̃C���X�^���X�𐶐�����B<br/>
	 * HousingInquiryForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� HousingInquiryForm �C���X�^���X 
	 */
	public HousingInquiryForm createHousingInquiryForm(HttpServletRequest request){
		HousingInquiryForm form = createHousingInquiryForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}
	
	/**
	 * �⍇���X�e�[�^�X�̓��͒l���i�[������ InquiryStatusForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� InquiryStatusForm �C���X�^���X 
	 */
	public InquiryStatusForm createInquiryStatusForm(){
		return new InquiryStatusForm(this.lengthUtils, this.codeLookupManager);
	}
	
	/**
	 * �⍇���X�e�[�^�X�̓��͒l���i�[���� InquiryStatusForm �̃C���X�^���X�𐶐�����B<br/>
	 * InquiryStatusForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� InquiryStatusForm �C���X�^���X 
	 */
	public InquiryStatusForm createInquiryStatusForm(HttpServletRequest request){
		InquiryStatusForm form = createInquiryStatusForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}
	
	/**
	 * ���⍇�����i�w�b�_�j�̌����̓��͒l���i�[������ InquirySearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� InquirySearchForm �C���X�^���X 
	 */
	public InquirySearchForm createInquirySearchForm(){
		return new InquirySearchForm(this.lengthUtils, this.codeLookupManager);
	}
	
	/**
	 * ���⍇�����i�w�b�_�j�̌����̓��͒l���i�[���� InquiryStatusForm �̃C���X�^���X�𐶐�����B<br/>
	 * InquirySearchForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� InquirySearchForm �C���X�^���X 
	 */
	public InquirySearchForm createInquirySearchForm(HttpServletRequest request){
		InquirySearchForm form = createInquirySearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}

}
