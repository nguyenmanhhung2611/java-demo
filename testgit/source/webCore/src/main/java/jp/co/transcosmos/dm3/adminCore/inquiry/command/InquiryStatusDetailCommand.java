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
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.web.servlet.ModelAndView;

/**
 * ���⍇���ڍ׉��.
 * <p>
 * ���N�G�X�g�p�����[�^�iinquiryId�j�œn���ꂽ�l�ɊY�����邨�m�点�����擾����ʕ\������B
 * <br/>
 * �y���A���� View ���z<br/>
 * <ul>
 * <li>success<li>:������������I��
 * <li>notFound<li>:�Y���f�[�^�����݂��Ȃ��ꍇ
 * </ul>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * Y.Cho		2015.04.10	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class InquiryStatusDetailCommand implements Command  {

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
	 * ���m�点�ڍו\������<br>
	 * <br>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	 * @return ModelAndView�@�̃C���X�^���X
	*/
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
        Map<String, Object> model = createModel(request);
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
        
        // �擾�ł����ꍇ�� model �ɐݒ肷��B
        putModel(inquiry, model);

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

		return model;
	}

}
