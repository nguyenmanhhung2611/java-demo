package jp.co.transcosmos.dm3.adminCore.inquiry.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.InquiryManage;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.inquiry.HousingInquiry;
import jp.co.transcosmos.dm3.core.model.inquiry.InquiryInterface;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryFormFactory;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquirySearchForm;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryStatusForm;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * ���⍇�������͉��
 * 
 * �E���N�G�X�g�p�����[�^�i������ʂœ��͂������������A�\���y�[�W�ʒu���j�̎����s���B
 * �E���N�G�X�g�p�����[�^�iinquiryId�j�ɊY�����邨�⍇�����A�}�C�y�[�W��������擾���A���
 *      �\������B
 *      
 * �y���N�G�X�g�p�����[�^�icommand�j �� "back"�̏ꍇ�z
 *     �E���N�G�X�g�p�����[�^�œn���ꂽ���͒l����͉�ʂɕ\������B�@�i���͊m�F��ʂ��畜�A�����P�[�X�B�j
 *
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *    �E"notFound" : �Y���f�[�^�����݂��Ȃ��ꍇ
 *     
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * Y.Cho		2015.04.08	�V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class InquiryStatusInputCommand implements Command {
	
	/** ���⍇�������e�i���X���s�� Model �I�u�W�F�N�g */
	protected InquiryManage inquiryManager;
	
	/**
	 * ���⍇�������e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param inquiryManager ���⍇�������e�i���X�� model �I�u�W�F�N�g
	 */
	public void setInquiryManager(InquiryManage inquiryManager) {
		this.inquiryManager = inquiryManager;
	}
	
	
	/**
	 * ���⍇�������͉�ʕ\������<br>
	 * <br>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	*/
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
        Map<String, Object> model = createModel(request);
        InquiryStatusForm inputForm = (InquiryStatusForm) model.get("inputForm"); 
        InquirySearchForm searchForm = (InquirySearchForm) model.get("searchForm"); 

		// �����ΏۂƂȂ��L�[�l���p�����[�^�œn����Ă��Ȃ��ꍇ�A��O���X���[����B
		if (StringValidateUtil.isEmpty(searchForm.getInquiryId())){
			throw new RuntimeException ("pk value is null.");
		}
        
        // ���⍇�������擾����
		InquiryInterface inquiry = searchInquiryPk(searchForm);
        
        // �Y������f�[�^�����݂��Ȃ��ꍇ�A�Y��������ʂ�\������B
        if (inquiry == null) {
        	return new ModelAndView("notFound", model);
        }

        // model�ɂ��⍇�������i�[����
        putModel(inquiry, model);
               
		// command �p�����[�^�� "back" �̏ꍇ�A���͊m�F��ʂ���̕��A�Ȃ̂ŁA�c�a���珉���l���擾���Ȃ��B
		// �i���N�G�X�g�p�����[�^����擾�����l���g�p����B�j
		String command = inputForm.getCommand();
		if (command != null && "back".equals(command)) {
			
			return new ModelAndView("success", model);
		}
		
        // �擾�����l����͗p Form �֊i�[����B �i���͒l�̏����l�p�j
		inputForm.setDefaultData(inquiry);
		
		return new ModelAndView("success", model);
	}
	
	/**
	 * �擾�������⍇������model �I�u�W�F�N�g�Ɋi�[����B<br/>
	 * <br/>
	 * @param model model �I�u�W�F�N�g
	 * @param inquiry ���⍇�����
	 */
	protected void putModel(InquiryInterface inquiry, Map<String, Object> model) {
		 // �⍇���w�b�_�����i�[����
		 model.put("inquiryHeaderInfo", inquiry.getInquiryHeaderInfo());
		 
		 // ���������i�[����
		 List<Housing> housings =  ((HousingInquiry) inquiry).getHousings();
		 
		 if (housings != null && housings.size() > 0) {
			 model.put("housingInfo", housings.get(0).getHousingInfo());
		 }
	}
	
	/**
	 * ���N�G�X�g�p�����[�^�œn���ꂽ�⍇��ID �i��L�[�l�j�ɊY�����镨���⍇�����𕜋A����B<br/>
	 * InquirySearchForm �� inquiryId �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��ď����擾����B
	 * �����Y���f�[�^��������Ȃ��ꍇ�Anull �𕜋A����B<br/>
	 * <br/>
	 * @param inquiryId�@�擾�Ώۂ��⍇��ID
	 * 
	 * @return�@DB ����擾���������⍇���̃o���[�I�u�W�F�N�g
	 * 
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	protected InquiryInterface searchInquiryPk(InquirySearchForm searchForm) throws Exception {
		return this.inquiryManager.searchInquiryPk(searchForm.getInquiryId());
	}

	
	/**
	 * model �I�u�W�F�N�g���쐬���A���N�G�X�g�p�����[�^���i�[���� form �I�u�W�F�N�g���i�[����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^��ݒ肵�� Form ���i�[���� model �I�u�W�F�N�g�𕜋A����B
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

        Map<String, Object> model = new HashMap<String, Object>();

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
        InquiryFormFactory factory = InquiryFormFactory.getInstance(request);

		// ���������A����сA��ʃR���g���[���p�����[�^���擾����B
        InquirySearchForm searchForm = factory.createInquirySearchForm(request);
		model.put("searchForm", searchForm);

		
		// ���̓t�H�[���̎󂯎��́Acommand �p�����[�^�̒l�ɂ���ĈقȂ�B
		// command �p�����[�^���n����Ȃ��ꍇ�A���͉�ʂ̏����\���Ƃ݂Ȃ��A��̃t�H�[���𕜋A����B
		// command �p�����[�^�� "back" ���n���ꂽ�ꍇ�A���͊m�F��ʂ���̕��A���Ӗ�����B
		// ���̏ꍇ�̓��N�G�X�g�p�����[�^���󂯎��A���͉�ʂ֏����\������B
		// �����A"back" �ȊO�̒l���n���ꂽ�ꍇ�Acommand �p�����[�^���n����Ȃ��ꍇ�Ɠ����Ɉ����B

		InquiryStatusForm inputForm = factory.createInquiryStatusForm(request);
		String command = inputForm.getCommand();
		
		if ("back".equals(command)){
			// �߂�{�^���̏ꍇ�́A���N�G�X�g�p�����[�^�̒l��ݒ肵�� Form �𕜋A����B
			model.put("inputForm", inputForm);
		} else {

			// ������ʕ\���̏ꍇ�A��t�H�[���𐶐����A�f�t�H���g�̓��͒l��ݒ肷��B
			inputForm = factory.createInquiryStatusForm(request);

			model.put("inputForm", inputForm);
		}

		return model;

	}

}
