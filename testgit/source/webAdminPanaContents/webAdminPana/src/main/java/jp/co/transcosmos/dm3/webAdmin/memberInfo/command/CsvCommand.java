package jp.co.transcosmos.dm3.webAdmin.memberInfo.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.corePana.model.member.form.MemberFormFactory;
import jp.co.transcosmos.dm3.corePana.model.member.form.MemberSearchForm;

/**
 * ��� CSV �o�͏���.
 * <p>
 * ���͂��ꂽ�������������ɉ�����������ACSV �o�͂���B<br/>
 * ����̈ꗗ�o�͂Ɗ�{�I�ɓ��ꏈ���ł���A�g�p���� Form �I�u�W�F�N�g�� CSV �p��
 * �ύX���Ă���݂̂̈Ⴂ�ƂȂ�B<br/>
 * ���� CSV �p Form �I�u�W�F�N�g�́A�y�[�W�������s�킸�A�S�f�[�^���擾�ΏۂƂ���B<br/>
 * <br/>
 * <pre>
 * �S����		  �C����		�C�����e
 * -------------- ----------- -----------------------------------------------------
 * tang.tianyun	  2015.04.20	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class CsvCommand extends MemberInfoListCommand {

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
		MemberFormFactory factory = MemberFormFactory.getInstance(request);
		MemberSearchForm searchForm = factory.createMypageUserSearchForm(request);

		// �y�[�W���̕\�������� From �ɐݒ肷��B
		searchForm.setRowsPerPage(Integer.MAX_VALUE);
		model.put("searchForm", searchForm);

		return model;
	}

}
