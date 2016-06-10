package jp.co.transcosmos.dm3.core.model.favorite.form;

import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.form.PagingListForm;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

/**
 * ���[�UID �ɊY�����邨�C�ɓ��������������p�����[�^�̎���p�t�H�[��.
 * <p>
 * ���������ƂȂ郆�[�UID�A�V�X�e������CD�A�y�[�W�ʒu�̏����擾���A�������ʂ��i�[����B
 * <p>
 * 
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * T.Nakamura	2015.03.23	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class FavoriteSearchForm extends PagingListForm<JoinResult> {

	/** �V�X�e������CD�i���������j */
	private String sysHousingCd;

	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	protected FavoriteSearchForm(){
		super();
	}

	/**
	 * �V�X�e������CD ���擾����<br/>
	 * <br/>
	 * @return �V�X�e������CD
	 */
	public String getSysHousingCd() {
		return sysHousingCd;
	}

	/**
	 * �V�X�e������CD ��ݒ肷��<br/>
	 * <br/>
	 * @param userId �V�X�e������CD
	 */
	public void setSysHousingCd(String sysHousingCd) {
		this.sysHousingCd = sysHousingCd;
	}

	/* (�� Javadoc)
	 * @see jp.co.transcosmos.dm3.form.PagingListForm#buildCriteria()
	 */
	@Override
	public DAOCriteria buildCriteria() {

		DAOCriteria criteria = super.buildCriteria();

		// �V�X�e������CD�̌�����������
		if (!StringValidateUtil.isEmpty(this.sysHousingCd)) {
			criteria.addWhereClause("sysHousingCd", this.sysHousingCd);
		}

		return criteria;
	}

}
