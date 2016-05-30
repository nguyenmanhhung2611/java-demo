package jp.co.transcosmos.dm3.corePana.model.information.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.information.form.InformationSearchForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.corePana.validation.DateFromToValidation;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * <pre>
 * ���m�点���̌����p�����[�^�A����сA��ʃR���g���[���p�����[�^����p�t�H�[��
 * ���������ƂȂ郊�N�G�X�g�p�����[�^�̎擾��A�c�a�����I�u�W�F�N�g�̐������s���B
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * zhang		2015.04.21	�V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class PanaInformationSearchForm extends InformationSearchForm implements Validateable {

	/**
	 * �o���f�[�V��������<br/>
	 * ���N�G�X�g�p�����[�^�̃o���f�[�V�������s��<br/>
	 * <br/>
	 * ���� Form �N���X�̃o���f�[�V�������\�b�h�́AValidateable �C���^�[�t�F�[�X�̎����ł͖����̂ŁA
	 * �o���f�[�V�������s���̈������قȂ�̂ŁA���� Form ���܂Ƃ߂ăo���f�[�V��������ꍇ�Ȃǂ͒���
	 * ���鎖�B<br/>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @param mode �������[�h ("insert" or "update")
	 * @return ���펞 true�A�G���[�� false
	 */
	@Override
	public boolean validate(List<ValidationFailure> errors) {
		int startSize = errors.size();
		super.validate(errors);

		// �o�^���E�J�n �o�^���E�I���̓��t��r�`�F�b�N
		ValidationChain valMemberFnameKana = new ValidationChain("information.search.keyInsDateFrom",this.getKeyInsDateFrom());
        valMemberFnameKana.addValidation(new DateFromToValidation("yyyy/MM/dd", "�o�^���E�I��", this.getKeyInsDateTo()));
        valMemberFnameKana.validate(errors);

		return (startSize == errors.size());

	}

	/**
	 * ���������I�u�W�F�N�g���쐬����B<br/>
	 * PagingListForm �Ɏ�������Ă���A�y�[�W�����p�̌������������������g�����A�󂯎�������N�G�X�g
	 * �p�����[�^�ɂ�錟�������𐶐�����B<br/>
	 * <br/>
	 * ���̃��\�b�h���ł́ADB �̕����t�B�[���h�����w�肵�Ă���R�[�h�����݂���B<br/>
	 * ���ׁ̈A�A�Ǘ����[�U�[���̃e�[�u���Ƃ��āAInformation �ȊO���g�p�����ꍇ�A���̃��\�b�h��
	 * �I�[�o�[���C�h����K�v������B<br/>
	 * <br/>
	 * @return ���������I�u�W�F�N�g
	 */
	@Override
	public DAOCriteria buildCriteria(){

		// �����p�I�u�W�F�N�g�̐���
		// �y�[�W�������s���ꍇ�APagingListForm�@�C���^�[�t�F�[�X���������� Form ����
		// DAOCriteria �𐶐�����K�v������B ����āA���g�� buildCriteria() ��
		// �g�p���Č��������𐶐����Ă���B
		DAOCriteria criteria = super.buildCriteria();
		criteria.addOrderByClause("informationNo", false);

        return criteria;

	}

	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 *
	 * @param lengthUtils
	 *            �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
	 */
	PanaInformationSearchForm(LengthValidationUtils lengthUtils) {
		super(lengthUtils);

	}
}
