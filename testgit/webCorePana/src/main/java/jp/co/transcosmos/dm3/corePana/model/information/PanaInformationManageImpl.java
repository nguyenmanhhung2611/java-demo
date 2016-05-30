package jp.co.transcosmos.dm3.corePana.model.information;

import jp.co.transcosmos.dm3.core.model.information.InformationManageImpl;
import jp.co.transcosmos.dm3.dao.DAOCriteria;

/**
 * <pre>
 * ���m�点�����e�i���X�p Model �N���X
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.05	�V�K�쐬
 *
 * ���ӎ���
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B
 *
 * </pre>
 */
public class PanaInformationManageImpl extends InformationManageImpl {

	/**
	 * �}�C�y�[�W TOP �ɕ\�����邨�m�点���擾�Ɏg�p���錟�������𐶐�����B<br/>
	 * ���J�Ώۋ敪 = �u�S�{����v�A�܂��́A�u�l�v���擾�ΏۂɂȂ邪�A�u�l�v�̏ꍇ�A���m�点���J����
	 * �̃��[�U�[ID �������ƈ�v����K�v������B<br/>
	 * �܂��A�V�X�e�����t�����J���Ԓ��ł��邨�m�点��񂪎擾�ΏۂƂȂ�B<br/>
	 * <br/>
	 * @return �}�C�y�[�W TOP �ɕ\������A���m�点���̃��X�g
	 */
	@Override
	protected DAOCriteria buildMyPageInformationCriteria(String userId) {
		DAOCriteria mainCriteria = super.buildMyPageInformationCriteria(userId);
		mainCriteria.addOrderByClause("insDate",false);
		return mainCriteria;
	}
}
