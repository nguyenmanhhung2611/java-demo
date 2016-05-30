package jp.co.transcosmos.dm3.webAdmin.inquiryList.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryFormFactory;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquirySearchForm;


/**
 * �����ꗗ��� CSV �o�͏���.
 * <p>
 * ���͂��ꂽ�������������ɕ��������������ACSV �o�͂���B<br/>
 * �����̈ꗗ�o�͂Ɗ�{�I�ɓ��ꏈ���ł���A�g�p���� Form �I�u�W�F�N�g�� CSV �p��
 * �ύX���Ă���݂̂̈Ⴂ�ƂȂ�B<br/>
 * ���� CSV �p Form �I�u�W�F�N�g�́A�y�[�W�������s�킸�A�S�f�[�^���擾�ΏۂƂ���B<br/>
 * <br/>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * �s�����C		2015.04.05	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class CsvCommand extends InquiryListCommand {

	/**
     * �f�t�H���g�R���X�g���N�^�[<br/>
     * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
     * <br/>
     */
    public CsvCommand() {
        super();
    }

	/**
	 * ���N�G�X�g�p�����[�^���� Form �I�u�W�F�N�g���쐬����B<br/>
	 * ��CSV �p�� Form �𐶐�����B
	 * <br/>
	 * @param request HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^���ݒ肳�ꂽ�t�H�[���I�u�W�F�N�g
	 */
	@Override
	protected Map<String, Object> createModel(HttpServletRequest request) {

        Map<String, Object> model = new HashMap<String, Object>();

        // ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		PanaInquiryFormFactory factory = PanaInquiryFormFactory.getInstance(request);
		PanaInquirySearchForm searchForm = factory.createPanaInquirySearchForm(request);

        model.put("searchForm", searchForm);

        return model;
	}

}
