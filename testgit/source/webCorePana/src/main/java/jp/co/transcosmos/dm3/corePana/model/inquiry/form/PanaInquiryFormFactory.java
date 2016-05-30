package jp.co.transcosmos.dm3.corePana.model.inquiry.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryFormFactory;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryHeaderForm;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquirySearchForm;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryStatusForm;
import jp.co.transcosmos.dm3.form.FormPopulator;

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
 * �s�����C		2015.04.02	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * Factory �̃C���X�^���X�𒼐ڐ������Ȃ����B�@�K��getInstance() �Ŏ擾���鎖�B
 *
 */
public class PanaInquiryFormFactory extends InquiryFormFactory {

	/**
	 * InquiryFormFactory �̃C���X�^���X���擾����B<br/>
	 * Spring �̃R���e�L�X�g����A inquiryFormFactory �Œ�`���ꂽ InquiryFormFactory ��
	 * �C���X�^���X���擾����B<br/>
	 * �擾�����C���X�^���X�́AInquiryFormFactory ���p�������g���N���X�����A�����ꍇ������B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return InquiryFormFactory�A�܂��͌p�����Ċg�������N���X�̃C���X�^���X
	 */
	public static PanaInquiryFormFactory getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (PanaInquiryFormFactory)springContext.getBean(PanaInquiryFormFactory.FACTORY_BEAN_ID);
	}

	/**
	 * �⍇���X�e�[�^�X�̓��͒l���i�[������ InquiryStatusForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� InquiryStatusForm �C���X�^���X
	 */
	@Override
	public InquiryStatusForm createInquiryStatusForm(){
		return new PanaInquiryStatusForm(this.lengthUtils, this.codeLookupManager);
	}

	/**
	 * �⍇���X�e�[�^�X�̓��͒l���i�[������ PanaInquiryStatusForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� PanaInquiryStatusForm �C���X�^���X
	 */
	public PanaInquiryStatusForm createPanaInquiryStatusForm(){
		return new PanaInquiryStatusForm(this.lengthUtils, this.codeLookupManager);
	}

	/**
	 * �⍇���X�e�[�^�X�̓��͒l���i�[���� PanaInquiryStatusForm �̃C���X�^���X�𐶐�����B<br/>
	 * PanaInquiryStatusForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� PanaInquiryStatusForm �C���X�^���X
	 */
	public PanaInquiryStatusForm createPanaInquiryStatusForm(HttpServletRequest request){
		PanaInquiryStatusForm form = createPanaInquiryStatusForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * �ėp�⍇���̓��͒l���i�[������ PanaInquirySearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� PanaInquirySearchForm �C���X�^���X
	 */
	public PanaInquirySearchForm createPanaInquirySearchForm(){
		return new PanaInquirySearchForm(this.lengthUtils, this.codeLookupManager);
	}

	/**
	 * �ėp�⍇���w�b�_�̓��͒l���i�[���� PanaInquirySearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * PanaInquirySearchForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� PanaInquirySearchForm �C���X�^���X
	 */
	public PanaInquirySearchForm createPanaInquirySearchForm(HttpServletRequest request){
		PanaInquirySearchForm form = createPanaInquirySearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * �����̂��₢���킹���͒l���i�[������ PanaInquiryHousingForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� PanaInquiryHousingForm �C���X�^���X
	 */
	public PanaInquiryHousingForm createPanaInquiryHousingForm(){
		return new PanaInquiryHousingForm(this.lengthUtils, this.codeLookupManager);
	}

	/**
	 * �����̂��₢���킹���͒l���i�[���� PanaInquiryHousingForm �̃C���X�^���X�𐶐�����B<br/>
	 * PanaInquiryHousingForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� PanaInquiryHousingForm �C���X�^���X
	 */
	public PanaInquiryHousingForm createPanaInquiryHousingForm(HttpServletRequest request){
		PanaInquiryHousingForm form = createPanaInquiryHousingForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * �ėp���₢���킹���͒l���i�[������ PanaInquiryGeneralForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� PanaInquiryGeneralForm �C���X�^���X
	 */
	public PanaInquiryGeneralForm createPanaInquiryGeneralForm(){
		return new PanaInquiryGeneralForm(this.lengthUtils, this.codeLookupManager);
	}

	/**
	 * �ėp���₢���킹���͒l���i�[���� PanaInquiryGeneralForm �̃C���X�^���X�𐶐�����B<br/>
	 * PanaInquiryGeneralForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� PanaInquiryGeneralForm �C���X�^���X
	 */
	public PanaInquiryGeneralForm createPanaInquiryGeneralForm(HttpServletRequest request){
		PanaInquiryGeneralForm form = createPanaInquiryGeneralForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * ���⍇�����i�w�b�_�j�̌����̓��͒l���i�[������ InquirySearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� InquirySearchForm �C���X�^���X
	 */
	@Override
	public InquirySearchForm createInquirySearchForm(){
		return new PanaInquirySearchForm(this.lengthUtils, this.codeLookupManager);
	}

	/**
	 * �⍇���w�b�_�̓��͒l���i�[������ InquiryHeaderForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� PanaInquiryHeaderForm �C���X�^���X
	 */
	@Override
	public InquiryHeaderForm createInquiryHeaderForm(){
		return new PanaInquiryHeaderForm();
	}

}
