package jp.co.transcosmos.dm3.webAdmin.information.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.model.information.form.InformationFormFactory;
import jp.co.transcosmos.dm3.core.model.information.form.InformationSearchForm;

/**
 * ���m�点 CSV �o�͏���.
 * <p>
 * ���͂��ꂽ�������������ɂ��m�点�����������ACSV �o�͂���B<br/>
 * ���m�点�̈ꗗ�o�͂Ɗ�{�I�ɓ��ꏈ���ł���A�g�p���� Form �I�u�W�F�N�g�� CSV �p��
 * �ύX���Ă���݂̂̈Ⴂ�ƂȂ�B<br/>
 * ���� CSV �p Form �I�u�W�F�N�g�́A�y�[�W�������s�킸�A�S�f�[�^���擾�ΏۂƂ���B<br/>
 * <br/>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.03.31	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class PanaCsvCommand extends PanaInformationListCommand {

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
		InformationFormFactory factory = InformationFormFactory.getInstance(request);
		InformationSearchForm searchForm = factory.createInformationSearchForm(request);

        // �y�[�W���̕\�������� From �ɐݒ肷��B
		searchForm.setRowsPerPage(Integer.MAX_VALUE);
        // ���̒l�́A�t���[�����[�N�̃y�[�W�������g�p����B
        model.put("searchForm", searchForm);

		return model;
	}

}
