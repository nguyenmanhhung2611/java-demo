package jp.co.transcosmos.dm3.core.model.feature.form;

import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.form.PagingListForm;



/**
 * ���WID �ɊY�����镨������������p�����[�^�̎���p�t�H�[��.
 * <p>
 * ���������ƂȂ���WID�A�y�[�W�ʒu�̏����擾���A�������ʂ��i�[����B
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.10	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class FeatureSearchForm extends PagingListForm<Housing> {

	/** ���W�y�[�WID */
	private String featurePageId;



	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	protected FeatureSearchForm(){
		super();
	}



	/**
	 * ���W�y�[�WID ���擾����<br/>
	 * <br/>
	 * @return ���W�y�[�WID
	 */
	public String getFeaturePageId() {
		return featurePageId;
	}

	/**
	 * ���W�y�[�WID ��ݒ肷��<br/>
	 * <br/>
	 * @param featurePageId ���W�y�[�WID
	 */
	public void setFeaturePageId(String featurePageId) {
		this.featurePageId = featurePageId;
	}

}
