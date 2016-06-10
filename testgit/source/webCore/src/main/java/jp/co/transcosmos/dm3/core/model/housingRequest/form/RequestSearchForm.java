package jp.co.transcosmos.dm3.core.model.housingRequest.form;

import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.form.PagingListForm;


/**
 * �������N�G�X�gID �ɊY�����镨������������p�����[�^�̎���p�t�H�[��.
 * <p>
 * ���������ƂȂ镨�����N�G�X�gID�A�y�[�W�ʒu�̏����擾���A�������ʂ��i�[����B
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
public class RequestSearchForm extends PagingListForm<Housing> {

	/** �������N�G�X�gID */
	private String housingRequestId;
	
	
	
	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	protected RequestSearchForm(){
		super();
	}



	/**
	 * �������N�G�X�gID ���擾����B<br/>
	 * <br/>
	 * @return �������N�G�X�gID
	 */
	public String getHousingRequestId() {
		return housingRequestId;
	}

	/**
	 * �������N�G�X�gID ��ݒ肷��B<br/>
	 * <br/>
	 * @param housingRequestId �������N�G�X�gID
	 */
	public void setHousingRequestId(String housingRequestId) {
		this.housingRequestId = housingRequestId;
	}

}
