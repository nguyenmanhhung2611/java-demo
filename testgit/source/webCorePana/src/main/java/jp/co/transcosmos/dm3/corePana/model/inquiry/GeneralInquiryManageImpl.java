package jp.co.transcosmos.dm3.corePana.model.inquiry;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.model.InquiryManage;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.inquiry.InquiryManageUtils;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryForm;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquirySearchForm;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryStatusForm;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryGeneralForm;
import jp.co.transcosmos.dm3.corePana.vo.InquiryGeneral;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHousingQuestion;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;

/**
 * �ėp�⍇�� model �̎����N���X.
 * <p>
 * <pre>
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �s�����C		2015.04.02	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class GeneralInquiryManageImpl implements InquiryManage {

	/** Value �I�u�W�F�N�g�� Factory */
	private ValueObjectFactory valueObjectFactory;

	/** �ėp�⍇�����擾�p DAO */
	private DAO<JoinResult> generalInquiryDAO;

	/** �ėp�⍇�����p DAO */
	private DAO<InquiryGeneral> inquiryGeneralDAO;

	/** �⍇���A���P�[�g���p DAO */
	private DAO<InquiryHousingQuestion> inquiryHousingQuestionDAO;

	/** �⍇���w�b�_�p���ʏ��� */
	private InquiryManageUtils inquiryManageUtils;

	/**
	 * �ėp�⍇�����擾�p DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param generalInquiryDAO �ėp�⍇�����擾�p DAO
	 */
	public void setGeneralInquiryDAO(DAO<JoinResult> generalInquiryDAO) {
		this.generalInquiryDAO = generalInquiryDAO;
	}

	/**
	 * @param inquiryGeneralDAO �Z�b�g���� inquiryGeneralDAO
	 */
	public void setInquiryGeneralDAO(DAO<InquiryGeneral> inquiryGeneralDAO) {
		this.inquiryGeneralDAO = inquiryGeneralDAO;
	}


	/**
	 * @param inquiryManageUtils �Z�b�g���� inquiryManageUtils
	 */
	public void setInquiryManageUtils(InquiryManageUtils inquiryManageUtils) {
		this.inquiryManageUtils = inquiryManageUtils;
	}

	/**
	 * @param inquiryHousingQuestionDAO �Z�b�g���� inquiryHousingQuestionDAO
	 */
	public void setInquiryHousingQuestionDAO(
			DAO<InquiryHousingQuestion> inquiryHousingQuestionDAO) {
		this.inquiryHousingQuestionDAO = inquiryHousingQuestionDAO;
	}

	@Override
	public String addInquiry(InquiryForm inputForm, String mypageUserId,
			String editUserId) throws Exception {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return null;
	}

	@Override
	public int searchInquiry(InquirySearchForm searchForm) throws Exception {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 0;
	}

	/**
	 * Value �I�u�W�F�N�g�� Factory ��ݒ肷��B<br/>
	 * <br/>
	 * @param valueObjectFactory Value �I�u�W�F�N�g�� Factory
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}
	/**
	 * ���N�G�X�g�p�����[�^�œn���ꂽ�⍇��ID �i��L�[�l�j�ɊY�����镨���⍇�����𕜋A����B<br/>
	 * InquirySearchForm �� inquiryId �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��ď����擾����B
	 * �����Y���f�[�^��������Ȃ��ꍇ�Anull �𕜋A����B<br/>
	 * <br/>
	 * @param inquiryId�@�擾�Ώۂ��⍇��ID
	 *
	 * @return�@DB ����擾���������⍇���̃o���[�I�u�W�F�N�g
	 *
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public InquiryInfo searchInquiryPk(String inquiryId) throws Exception {

		// ����⍇�����擾���Ė⍇���I�u�W�F�N�g�֐ݒ肷��B
		InquiryInfo inquiryInfo = new InquiryInfo();

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId, DAOCriteria.EQUALS, false);

		List<JoinResult> generalInquiry = this.generalInquiryDAO.selectByFilter(criteria);
		if (generalInquiry.size() > 0) {
			inquiryInfo.setGeneralInquiry(generalInquiry);
	}

		return inquiryInfo;
	}

	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ŕ����⍇��������V�K�o�^����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * <br/>
	 * @param inputForm �����⍇���̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param mypageUserId �}�C�y�[�W�̃��[�U�[ID �i�}�C�y�[�W���O�C�����ɐݒ�B�@����ȊO�� null�j
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 *
	 * @return �̔Ԃ��ꂽ���₢���킹ID
	 *
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */

	public String addInquiry(PanaInquiryGeneralForm inputForm, String mypageUserId, String editUserId)
			throws Exception {

		String inquiryId = null;

		// �⍇���w�b�_�̓o�^���������s
		inquiryId = this.inquiryManageUtils.addInquiry(
				inputForm.getInquiryHeaderForm(), PanaCommonConstant.INQUIRY_TYPE_GENERAL, mypageUserId, editUserId);
		inputForm.setInquiryId(inquiryId);

		// �V�K�o�^�����̏ꍇ�A���̓t�H�[���̒l��ݒ肷��o���[�I�u�W�F�N�g�𐶐�����B
		// �o���[�I�u�W�F�N�g�́A�t�@�N�g���[���\�b�h�ȊO�ł͐������Ȃ����B
		// �i�p�����ꂽ�o���[�I�u�W�F�N�g���g�p����Ȃ��Ȃ�ׁB�j
		InquiryGeneral[] inquiryGeneral = new InquiryGeneral[]{ (InquiryGeneral) this.valueObjectFactory.getValueObject("InquiryGeneral")};

		// �t�H�[���̓��͒l���o���[�I�u�W�F�N�g�ɐݒ肷��B
		inputForm.copyToInquiryGeneral(inquiryGeneral, inquiryId);
		// �ėp�⍇������o�^
		this.inquiryGeneralDAO.insert(inquiryGeneral);

		// �t�H�[���̓��͒l���o���[�I�u�W�F�N�g�ɐݒ肷��B
		if (inputForm.getAnsCd() != null) {
			InquiryHousingQuestion[] inquiryHousingQuestions = new InquiryHousingQuestion[inputForm.getAnsCd().length];
			inputForm.copyToInquiryHousingQuestion(inquiryHousingQuestions, inquiryId);
			// �⍇���A���P�[�g��o�^
			this.inquiryHousingQuestionDAO.insert(inquiryHousingQuestions);
		}

		return inquiryId;
	}
	@Override
	public void updateInquiryStatus(InquiryStatusForm inputForm,
			String editUserId) throws Exception, NotFoundException {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

	@Override
	public InquiryForm createForm() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return null;
	}

	@Override
	public InquiryForm createForm(HttpServletRequest request) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return null;
	}
}
