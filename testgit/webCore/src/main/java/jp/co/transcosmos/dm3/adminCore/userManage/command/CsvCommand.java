package jp.co.transcosmos.dm3.adminCore.userManage.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserFormFactory;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserSearchCsvForm;


/**
 * �Ǘ����[�U�[ CSV �o�͏���.
 * <p>
 * ���͂��ꂽ�������������ɊǗ��҃��[�U�[���������ACSV �o�͂���B<br/>
 * �Ǘ����[�U�[�̈ꗗ�o�͂Ɗ�{�I�ɓ��ꏈ���ł���A�g�p���� Form �I�u�W�F�N�g�� CSV �p��
 * �ύX���Ă���݂̂̈Ⴂ�ƂȂ�B<br/>
 * ���� CSV �p Form �I�u�W�F�N�g�́A�y�[�W�������s�킸�A�S�f�[�^���擾�ΏۂƂ���B<br/>
 * <br/>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.05	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class CsvCommand extends UserListCommand {

	
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
		AdminUserFormFactory factory = AdminUserFormFactory.getInstance(request);
		AdminUserSearchCsvForm searchForm = factory.createUserSearchCsvForm(request); 

		model.put("searchForm", searchForm);
		
		return model;
	}

}
