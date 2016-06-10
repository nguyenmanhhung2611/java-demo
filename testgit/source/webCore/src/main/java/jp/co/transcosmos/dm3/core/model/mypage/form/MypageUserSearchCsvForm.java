package jp.co.transcosmos.dm3.core.model.mypage.form;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.dao.DAOCriteria;

/**
 * �}�C�y�[�W��� CSV �o�͂̌����p�����[�^����p�t�H�[��.
 * <p>
 * �}�C�y�[�W��������p�� Form ���p�����č쐬���� CSV �o�͗p�� Form �N���X�B<br/>
 * buildCriteria() ���s���APagingListForm�@�� buildCriteria() ���������Ȃ��B
 * ����āA�S�f�[�^���擾�ΏۂƂȂ�B<br/>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.30	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �����ΏۂƂȂ�S�f�[�^�� Form �Ɋi�[�����̂ŁACSV �o�͈ȊO�ł͎g�p���Ȃ����B
 * 
 */
public class MypageUserSearchCsvForm extends MypageUserSearchForm {

	/**
	 * �f�t�H���g�R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	protected MypageUserSearchCsvForm() {
		super();
	}

	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
	 */
	protected MypageUserSearchCsvForm(LengthValidationUtils lengthUtils) {
		super(lengthUtils);
	}

	
	
	/**
	 * ���������I�u�W�F�N�g���쐬����B<br/>
	 * CSV �o�͗p�Ȃ̂Ńy�[�W���������O�������������𐶐�����B<br/>
	 * <br/>
	 * @return ���������I�u�W�F�N�g
	 */
	@Override
	public DAOCriteria buildCriteria(){
		return buildCriteria(new DAOCriteria());
	}
	
}
