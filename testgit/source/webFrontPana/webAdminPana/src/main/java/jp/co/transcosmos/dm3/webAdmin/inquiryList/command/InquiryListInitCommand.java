package jp.co.transcosmos.dm3.webAdmin.inquiryList.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryFormFactory;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquirySearchForm;

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
public class InquiryListInitCommand implements Command {

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
    	Map<String, Object> model = new HashMap<String, Object>();
    	// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		PanaInquiryFormFactory factory = PanaInquiryFormFactory.getInstance(request);
		PanaInquirySearchForm searchForm = factory.createPanaInquirySearchForm(request);

        // �s���{��List�̒l�� view �w�֓n���p�����[�^�Ƃ��Đݒ肵�Ă���B
        model.put("searchForm", searchForm);

        return new ModelAndView("success", model);
	}

}
