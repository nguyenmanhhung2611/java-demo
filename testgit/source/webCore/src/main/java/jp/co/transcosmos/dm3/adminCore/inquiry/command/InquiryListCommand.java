package jp.co.transcosmos.dm3.adminCore.inquiry.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.InquiryManage;
import jp.co.transcosmos.dm3.core.model.inquiry.HousingInquiryManageImpl;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryFormFactory;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquirySearchForm;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * ���⍇���̌����E�ꗗ
 * ���͂��ꂽ�������������ɂ��⍇�������������A�ꗗ�\������B
 * ���������̓��͂ɖ�肪����ꍇ�A���������͍s��Ȃ��B
 * 
 * �y���A���� View ���z
 *    �E"success" : ������������I��
 *    �E"validFail" : �o���f�[�V�����G���[ 
 * 
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * Y.Cho		2015.04.07	�V�K�쐬
 * 
 * ���ӎ���
 * 
 * </pre>
 */
public class InquiryListCommand implements Command {
	
	/** ���⍇�������e�i���X���s�� Model �I�u�W�F�N�g */
	protected InquiryManage inquiryManager;
	
	/** �P�y�[�W�̕\������ */
	protected int rowsPerPage = 50;
	
	/**
	 * 1�y�[�W������\������ݒ肷��B<br>
	 * 
	 * @param rowsPerPage
	 *            1�y�[�W������\����
	 */
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}
	
	/**
	 * �����⍇�����e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param inquiryManager �����⍇�����e�i���X�� model �I�u�W�F�N�g
	 */
	public void setInquiryManager(HousingInquiryManageImpl inquiryManager) {
		this.inquiryManager = inquiryManager;
	}
	
	/**
	 * �����⍇����񃊃N�G�X�g����<br>
	 * �����⍇�����̃��N�G�X�g���������Ƃ��ɌĂяo�����B <br>
	 * 
	 * @param request
	 *            �N���C�A���g�����Http���N�G�X�g�B
	 * @param response
	 *            �N���C�A���g�ɕԂ�Http���X�|���X�B
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// �����p�����[�^��M
        Map<String, Object> model = createModel(request);
        InquirySearchForm form = (InquirySearchForm) model.get("searchForm");
        		
        // �o���f�[�V���������s
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();

        if (!form.validate(errors)) {
        	// �o���f�[�V�����G���[����
			model.put("errors", errors);
			return new ModelAndView("validFail", model);
        }
        	

        // �������s
        // searchInquiry() �́A�p�����[�^�œn���ꂽ form �̓��e�ŕ����⍇�������������A
        // �����������ʂ� form �Ɋi�[����B
        // ���̃��\�b�h�̖߂�l�͊Y�������Ȃ̂ŁA���̒l�� view �w�֓n���p�����[�^�Ƃ��Đݒ肵�Ă���B
		model.put("hitcont", this.inquiryManager.searchInquiry(form));


		// �o���f�[�V������ʉ߂��A�����������s��ꂽ�ꍇ�AForm �� command �p�����[�^�� list ��ݒ�
		// ����B�@���̃p�����[�^�l�́A�ڍ׉�ʂ�ύX��ʂ� searchCommand �p�����[�^�Ƃ��Ĉ����p����A
		// �Ăь�����ʂ֕��A����ہAcommand �p�����[�^�Ƃ��ēn�����B
		model.put("command", "list");

		return new ModelAndView("success", model);
	}
	
	/**
	 * ���N�G�X�g�p�����[�^���� Form �I�u�W�F�N�g���쐬����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^���ݒ肳�ꂽ�t�H�[���I�u�W�F�N�g
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		
		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		InquiryFormFactory factory = InquiryFormFactory.getInstance(request);
		InquirySearchForm searchForm = factory.createInquirySearchForm(request);
		
        // �y�[�W���̕\�������� From �ɐݒ肷��B
        // ���̒l�́A�t���[�����[�N�̃y�[�W�������g�p����B
		searchForm.setRowsPerPage(this.rowsPerPage);
        model.put("searchForm", searchForm);

		return model;

	}

}
