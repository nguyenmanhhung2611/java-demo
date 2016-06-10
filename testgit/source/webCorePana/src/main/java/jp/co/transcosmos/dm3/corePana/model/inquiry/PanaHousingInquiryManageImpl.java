package jp.co.transcosmos.dm3.corePana.model.inquiry;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.core.model.inquiry.HousingInquiry;
import jp.co.transcosmos.dm3.core.model.inquiry.HousingInquiryManageImpl;
import jp.co.transcosmos.dm3.core.model.inquiry.InquiryHeaderInfo;
import jp.co.transcosmos.dm3.core.model.inquiry.InquiryInterface;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryForm;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquirySearchForm;
import jp.co.transcosmos.dm3.core.vo.InquiryHousing;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.model.inquiry.dao.HousingInquiryDAO;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryHousingForm;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquirySearchForm;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHeader;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHousingQuestion;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.dao.NotEnoughRowsException;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

/**
 * �����⍇�� model �̎����N���X.
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
public class PanaHousingInquiryManageImpl extends HousingInquiryManageImpl {

	/** �⍇���w�b�_���pDAO */
	private DAO<InquiryHeader> inquiryHeaderDAO;

	/** �⍇���A���P�[�g���p DAO */
	private DAO<InquiryHousingQuestion> inquiryHousingQuestionDAO;

	/** �����⍇�� model �̎����N���X. */
	protected GeneralInquiryManageImpl generalInquiryManager;

	/** �����⍇�� model �̎����N���X. */
	protected AssessmentInquiryManageImpl assessmentInquiryManager;

	/** �s���{���}�X�^�p DAO */
	private DAO<PrefMst> prefMstDAO;

	/** �⍇��񃁃��e�i���X�� model */
	protected HousingInquiryDAO housingInquiryDAO;

	/**
	 *  �⍇���w�b�_���p DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param inquiryHeaderDAO �⍇���w�b�_���p DAO
	 */
	public void setInquiryHeaderDAO(DAO<InquiryHeader> inquiryHeaderDAO) {
		this.inquiryHeaderDAO = inquiryHeaderDAO;
	}

	/**
	 * @param generalInquiryManage �Z�b�g���� generalInquiryManage
	 */
	public void setGeneralInquiryManager(GeneralInquiryManageImpl generalInquiryManager) {
		this.generalInquiryManager = generalInquiryManager;
	}

	/**
	 * @param assessmentInquiryManage �Z�b�g���� assessmentInquiryManage
	 */
	public void setAssessmentInquiryManager(AssessmentInquiryManageImpl assessmentInquiryManager) {
		this.assessmentInquiryManager = assessmentInquiryManager;
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
	 *  �⍇���A���P�[�g���pDAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param inquiryHousingQuestionDAO �⍇���A���P�[�g���p DAO
	 */
	public void setInquiryHousingQuestionDAO(DAO<InquiryHousingQuestion> inquiryHousingQuestionDAO) {
		this.inquiryHousingQuestionDAO = inquiryHousingQuestionDAO;
	}

	/**
	 * �⍇��񃁃��e�i���X�� model��ݒ肷��B<br/>
	 * <br/>
	 * @param housingInquiryDAO �Z�b�g���� housingInquiryDAO
	 */
	public void setHousingInquiryDAO(HousingInquiryDAO housingInquiryDAO) {
		this.housingInquiryDAO = housingInquiryDAO;
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
	@Override
	public String addInquiry(InquiryForm inputForm, String mypageUserId, String editUserId)
			throws Exception {

		String inquiryId = null;

		// �⍇���w�b�_�̓o�^���������s
		inquiryId = super.addInquiry(inputForm, mypageUserId, editUserId);

		// �⍇���A���P�[�g���쐬
		InquiryHousingQuestion[] inquiryHousingQuestions = null;
		int ansCnt = 0;
		if (((PanaInquiryHousingForm)inputForm).getAnsCd() != null && ((PanaInquiryHousingForm)inputForm).getAnsCd().length > 0) {

			ansCnt += ((PanaInquiryHousingForm)inputForm).getAnsCd().length;
		}
		if (ansCnt > 0){
			inquiryHousingQuestions = (InquiryHousingQuestion[]) this.valueObjectFactory.getValueObject("InquiryHousingQuestion", ansCnt);

			// �t�H�[���̓��͒l���o���[�I�u�W�F�N�g�ɐݒ肷��B
			((PanaInquiryHousingForm)inputForm).copyToInquiryHousingQuestion(inquiryHousingQuestions);

			// �擾�������⍇��ID����L�[�l�ɐݒ肷��B
	    	for (InquiryHousingQuestion inquiryHousingQuestion : inquiryHousingQuestions) {
	    		inquiryHousingQuestion.setInquiryId(inquiryId);
			}

	    	// �⍇���A���P�[�g���o�^
			this.inquiryHousingQuestionDAO.insert(inquiryHousingQuestions);
		}

		return inquiryId;
	}

	/**
	 * �⍇�������������A���ʃ��X�g�𕜋A����B<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A�⍇���w�b�_������������B<br/>
	 * �������ʂ� Form �I�u�W�F�N�g�Ɋi�[����A�擾�����Y��������߂�l�Ƃ��ĕ��A����B<br/>
	 * ���Ǘ���ʂ���̗��p��O��Ƃ��Ă���̂ŁA�t�����g����g�p����ꍇ�̓Z�L�����e�B�ɒ��ӂ��鎖�B<br/>
	 * <br/>
	 * @param searchForm ���������A����сA�������ʂ̊i�[�I�u�W�F�N�g
	 *
	 * @return �Y������
	 *
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public int searchInquiry(InquirySearchForm searchForm) throws Exception {
		// ��������
		List<JoinResult> inquiryList = null;
		try {
			inquiryList = this.housingInquiryDAO.housingInquirySearch((PanaInquirySearchForm)searchForm);
		} catch (NotEnoughRowsException err) {
			// �͈͊O�̃y�[�W���ݒ肳�ꂽ�ꍇ�A�Č���
			int pageNo = (err.getMaxRowCount() - 1) / searchForm.getRowsPerPage() + 1;
			searchForm.setSelectedPage(pageNo);
			inquiryList = this.housingInquiryDAO.housingInquirySearch((PanaInquirySearchForm)searchForm);
		}

		searchForm.setRows(inquiryList);

		return inquiryList.size();
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
	public InquiryInfo searchHousingInquiryPk(String inquiryId) throws Exception {

		InquiryInfo inquiryInfo = new InquiryInfo();

		InquiryInterface inquiryHousing = super.searchInquiryPk(inquiryId);

		// �����⍇���w�b�_�̏�񂪖����ꍇ�A�߂�l��null�ݒ肵�ď����𒆒f����B
		if (inquiryHousing.getInquiryHeaderInfo() == null) return null;

		// �⍇���I�u�W�F�N�g�֐ݒ肷��B
		inquiryInfo.setInquiryHeaderInfo(inquiryHousing.getInquiryHeaderInfo());
		inquiryInfo.setHousings(((HousingInquiry)inquiryHousing).getHousings());

		//�⍇�ҏZ���E�s���{��CD���s���{���}�X�^����s���{�������擾���Ė⍇���I�u�W�F�N�g�֐ݒ肷��B
		InquiryHeader inquiryHeader =  (InquiryHeader)inquiryHousing.getInquiryHeaderInfo().getInquiryHeader();
		if(!StringValidateUtil.isEmpty(inquiryHeader.getPrefCd())){
			DAOCriteria criteria = new DAOCriteria();
			criteria.addWhereClause("prefCd",inquiryHeader.getPrefCd(), DAOCriteria.EQUALS, false);

			List<PrefMst> prefMst = this.prefMstDAO.selectByFilter(criteria);
			if (prefMst.size() > 0) {
				inquiryInfo.setPrefMst(prefMst.get(0));
			}
		}

		// �⍇���w�b�_�̏�񂪎擾�ł����ꍇ�́A�⍇���A���P�[�g�����擾���Ė⍇���I�u�W�F�N�g�֐ݒ肷��B
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId, DAOCriteria.EQUALS, false);

		List<InquiryHousingQuestion> inquiryHousingQuestion = this.inquiryHousingQuestionDAO.selectByFilter(criteria);
		if (inquiryHousingQuestion.size() > 0) {
			inquiryInfo.setInquiryHousingQuestion(inquiryHousingQuestion.toArray(
					new InquiryHousingQuestion[inquiryHousingQuestion.size()]));
		}

		// �⍇���w�b�_�̏�񂪎擾�ł����ꍇ�́A�����⍇�������擾���Ė⍇���I�u�W�F�N�g�֐ݒ肷��B
		DAOCriteria criteriaHousing = new DAOCriteria();
		criteriaHousing.addWhereClause("inquiryId", inquiryId, DAOCriteria.EQUALS, false);

		List<InquiryHousing> inquiryHousings = this.inquiryHousingDAO.selectByFilter(criteriaHousing);
		if (inquiryHousings.size() > 0) {
			inquiryInfo.setInquiryHousing((jp.co.transcosmos.dm3.corePana.vo.InquiryHousing)inquiryHousings.get(0));
		}

		return inquiryInfo;
	}

	/**
	 * ���N�G�X�g�p�����[�^�œn���ꂽ�⍇��ID �i��L�[�l�j�ɊY������⍇���w�b�_���𕜋A����B<br/>
	 * InquirySearchForm �� inquiryId �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��ď����擾����B
	 * �����Y���f�[�^��������Ȃ��ꍇ�Anull �𕜋A����B<br/>
	 * <br/>
	 * @param inquiryId�@�擾�Ώۂ��⍇��ID
	 *
	 * @return�@DB ����擾�����⍇���w�b�_�̃o���[�I�u�W�F�N�g
	 *
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public InquiryHeader searchInquiryHeaderPk(String inquiryId) throws Exception {

		// �⍇���w�b�_���̃C���X�^���X�𐶐�����B
		InquiryHeader inquiryHeader = new InquiryHeader();

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId, DAOCriteria.EQUALS, false);

		// �⍇���w�b�_�����擾
		List<InquiryHeader> inquiryHeaders = this.inquiryHeaderDAO.selectByFilter(criteria);

		//�⍇���w�b�_��� ��ݒ�
		if (inquiryHeaders.size() > 0) {
			inquiryHeader = inquiryHeaders.get(0);
		}

		return inquiryHeader;
	}



	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ŕ����ꗗ�����폜����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * PanaHousingSearchForm �� sysBuildingCd �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��č폜����B
	 * �܂��A�폜�Ώۃ��R�[�h�����݂��Ȃ��ꍇ�ł�����I���Ƃ��Ĉ������B<br/>
	 * <br/>
	 *
	 * @param searchForm
	 *            �폜�����̊i�[�I�u�W�F�N�g
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void delInquiryAll(String inquiryId) throws Exception {
		// ���⍇���w�b�_�����폜��������𐶐�����B
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId);

		// ���⍇���w�b�_�����폜����
		this.inquiryHeaderDAO.deleteByFilter(criteria);
	}

	/**
	 * CSV�o�͏����������A���ʃ��X�g�𕜋A����B<br/>
	 * �����œn���ꂽ searchForm �p�����[�^�̒l�Ō��������𐶐����ACSV�o�͏�����������B<br/>
	 * �������ʂ� searchForm �I�u�W�F�N�g�Ɋi�[����A�擾�����Y��������߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 *
	 * @param searchForm
	 *            ���������A����сA�������ʂ̊i�[�I�u�W�F�N�g
	 *
	 * @return �Y������
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void searchCsvHousing(PanaInquirySearchForm searchForm,HttpServletResponse response)throws Exception {

		this.housingInquiryDAO.housingInquirySearch(searchForm,response, this, this.generalInquiryManager, this.assessmentInquiryManager);
	}


	/**
	 * �����⍇�����̃C���X�^���X�𐶐�����B<br/>
	 * ���������C���X�^���X�ɂ́A�⍇���w�b�_�̏���ݒ肷��B<br/>
	 * �����AHousingInquiry ���g�������ꍇ�͂��̃��\�b�h���I�[�o�[���C�h���鎖�B<br/>
	 * <br/>
	 * @param header ���⍇���w�b�_
	 * @return�@�����⍇�����̃C���X�^���X
	 */
	protected HousingInquiry createHousingInquiry(InquiryHeaderInfo header) {
		HousingInquiry inquiry = new HousingInquiry();
		inquiry.setInquiryHeaderInfo(header);
		return inquiry;
	}

}
