package jp.co.transcosmos.dm3.adminCore.mypage.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserFormFactory;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserSearchCsvForm;

/**
 * �}�C�y�[�W��� CSV �o�͏���.
 * <p>
 * ���͂��ꂽ�������������Ƀ}�C�y�[�W������������ACSV �o�͂���B<br/>
 * �}�C�y�[�W����̈ꗗ�o�͂Ɗ�{�I�ɓ��ꏈ���ł���A�g�p���� Form �I�u�W�F�N�g�� CSV �p��
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
public class CsvCommand extends MypageListCommand {

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
		MypageUserFormFactory factory = MypageUserFormFactory.getInstance(request);
		MypageUserSearchCsvForm searchForm = factory.createMypageUserSearchCsvForm(request); 

		model.put("searchForm", searchForm);
		
		return model;
	}

}
