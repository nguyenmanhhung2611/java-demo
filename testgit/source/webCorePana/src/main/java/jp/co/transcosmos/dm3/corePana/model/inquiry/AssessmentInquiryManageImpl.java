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
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.model.assessment.form.AssessmentInputForm;
import jp.co.transcosmos.dm3.corePana.vo.InquiryAssessment;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHousingQuestion;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

/**
 * ����⍇�� model �̎����N���X.
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
public class AssessmentInquiryManageImpl implements InquiryManage{

	/** Value �I�u�W�F�N�g�� Factory */
	private ValueObjectFactory valueObjectFactory;

	/** �⍇���w�b�_�p���ʏ��� */
	private InquiryManageUtils inquiryManageUtils;

	/** ������p DAO */
	private DAO<InquiryAssessment> inquiryAssessmentDAO;

	/** �⍇���A���P�[�g���p DAO */
	private DAO<InquiryHousingQuestion> inquiryHousingQuestionDAO;

	/** ����⍇���擾�p DAO */
	private DAO<JoinResult> assessmentInquiryDAO;

	/** �s���{���}�X�^�p DAO */
	private DAO<PrefMst> prefMstDAO;

	/**
	 * Value �I�u�W�F�N�g�� Factory ��ݒ肷��B<br/>
	 * <br/>
	 * @param valueObjectFactory Value �I�u�W�F�N�g�� Factory
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}

	/**
	 * �⍇���w�b�_�p���ʏ�����ݒ肷��B<br/>
	 * <br/>
	 * @param inquiryManageUtils �⍇���w�b�_�p���ʏ���
	 */
	public void setInquiryManageUtils(InquiryManageUtils inquiryManageUtils) {
		this.inquiryManageUtils = inquiryManageUtils;
	}

	/**
	 * ����⍇���擾�p DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param assessmentInquiryDAO ����⍇���擾�p DAO
	 */
	public void setInquiryAssessmentDAO(DAO<InquiryAssessment> inquiryAssessmentDAO) {
		this.inquiryAssessmentDAO = inquiryAssessmentDAO;
	}

	/**
	 * @param inquiryHousingQuestionDAO �Z�b�g���� inquiryHousingQuestionDAO
	 */
	public void setInquiryHousingQuestionDAO(DAO<InquiryHousingQuestion> inquiryHousingQuestionDAO) {
		this.inquiryHousingQuestionDAO = inquiryHousingQuestionDAO;
	}

	/**
	 * ����⍇���擾�p DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param assessmentInquiryDAO ����⍇���擾�p DAO
	 */
	public void setAssessmentInquiryDAO(DAO<JoinResult> assessmentInquiryDAO) {
		this.assessmentInquiryDAO = assessmentInquiryDAO;
	}

	/**
	 *  �s���{���}�X�^�p DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param prefMstDAO �s���{���}�X�^�p DAO
	 */
	public void setPrefMstDAO(DAO<PrefMst> prefMstDAO) {
		this.prefMstDAO = prefMstDAO;
	}

	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ō��������V�K�o�^����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * <br/>
	 * @param inputForm ������̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param mypageUserId �}�C�y�[�W�̃��[�U�[ID �i�}�C�y�[�W���O�C�����ɐݒ�B�@����ȊO�� null�j
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 *
	 * @return �̔Ԃ��ꂽ���₢���킹ID
	 *
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public String addInquiry(InquiryForm inputForm, String mypageUserId,
			String editUserId) throws Exception {

		// �⍇���w�b�_�̓o�^���������s
		String inquiryId = this.inquiryManageUtils.addInquiry(
				inputForm.getInquiryHeaderForm(), PanaCommonConstant.INQUIRY_TYPE_ASSESSMENT, mypageUserId, editUserId);

		// �V�K�o�^�����̏ꍇ�A���̓t�H�[���̒l��ݒ肷��o���[�I�u�W�F�N�g�𐶐�����B
		// �o���[�I�u�W�F�N�g�́A�t�@�N�g���[���\�b�h�ȊO�ł͐������Ȃ����B
		// �i�p�����ꂽ�o���[�I�u�W�F�N�g���g�p����Ȃ��Ȃ�ׁB�j

		// ������쐬
		InquiryAssessment inquiryAssessment = (InquiryAssessment) this.valueObjectFactory.getValueObject("InquiryAssessment");

		// �t�H�[���̓��͒l���o���[�I�u�W�F�N�g�ɐݒ肷��B
		((AssessmentInputForm)inputForm).copyToInquiryAssessment(inquiryAssessment);

		// �擾�������⍇��ID����L�[�l�ɐݒ肷��B
    	inquiryAssessment.setInquiryId(inquiryId);

    	// ������o�^
    	this.inquiryAssessmentDAO.insert(new InquiryAssessment[] { inquiryAssessment });


    	// �⍇���A���P�[�g���쐬
		InquiryHousingQuestion[] inquiryHousingQuestions = null;
		int ansCnt = 0;
		if (((AssessmentInputForm)inputForm).getAnsCd() != null && ((AssessmentInputForm)inputForm).getAnsCd().length > 0) {

			ansCnt += ((AssessmentInputForm)inputForm).getAnsCd().length;
		}
		if (ansCnt > 0)
		{
			inquiryHousingQuestions = (InquiryHousingQuestion[]) this.valueObjectFactory.getValueObject("InquiryHousingQuestion", ansCnt);

			// �t�H�[���̓��͒l���o���[�I�u�W�F�N�g�ɐݒ肷��B
			((AssessmentInputForm)inputForm).copyToInquiryHousingQuestion(inquiryHousingQuestions);

			// �擾�������⍇��ID����L�[�l�ɐݒ肷��B
	    	for (InquiryHousingQuestion inquiryHousingQuestion : inquiryHousingQuestions) {
	    		inquiryHousingQuestion.setInquiryId(inquiryId);
			}

	    	// �⍇���A���P�[�g���o�^
			this.inquiryHousingQuestionDAO.insert(inquiryHousingQuestions);
		}

		return inquiryId;
	}

	@Override
	public int searchInquiry(InquirySearchForm searchForm) throws Exception {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 0;
	}

	/**
	 * ���N�G�X�g�p�����[�^�œn���ꂽ�⍇��ID �i��L�[�l�j�ɊY�����鍸��⍇���𕜋A����B<br/>
	 * InquirySearchForm �� inquiryId �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��ď����擾����B
	 * �����Y���f�[�^��������Ȃ��ꍇ�Anull �𕜋A����B<br/>
	 * <br/>
	 * @param inquiryId�@�擾�Ώۂ��⍇��ID
	 *
	 * @return�@DB ����擾��������⍇���̃o���[�I�u�W�F�N�g
	 *
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public InquiryInfo searchInquiryPk(String inquiryId) throws Exception {

		InquiryInfo inquiryInfo = new InquiryInfo();

		// ����⍇�����擾���Ė⍇���I�u�W�F�N�g�֐ݒ肷��B
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId, DAOCriteria.EQUALS, false);

		List<JoinResult> assessmentInquiry = this.assessmentInquiryDAO.selectByFilter(criteria);
		if (assessmentInquiry.size() > 0) {
			inquiryInfo.setAssessmentInquiry(assessmentInquiry);
		}

		//�⍇�ҏZ���E�s���{��CD���s���{���}�X�^����s���{�������擾���Ė⍇���I�u�W�F�N�g�֐ݒ肷��B
		InquiryAssessment inquiryAssessment =  (InquiryAssessment)assessmentInquiry.get(0).getItems().get("inquiryAssessment");
		if (!StringValidateUtil.isEmpty(inquiryAssessment.getPrefCd())) {
			DAOCriteria criteriaPrefMst = new DAOCriteria();
			criteriaPrefMst.addWhereClause("prefCd",inquiryAssessment.getPrefCd(), DAOCriteria.EQUALS, false);

			List<PrefMst> prefMst = this.prefMstDAO.selectByFilter(criteriaPrefMst);
			if (prefMst.size() > 0) {
				inquiryInfo.setPrefMst(prefMst.get(0));
			}
		}

		return inquiryInfo;
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
