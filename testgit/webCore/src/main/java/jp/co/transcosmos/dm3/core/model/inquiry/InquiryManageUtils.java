package jp.co.transcosmos.dm3.core.model.inquiry;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryHeaderForm;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquirySearchForm;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryStatusForm;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.InquiryDtlInfo;
import jp.co.transcosmos.dm3.core.vo.InquiryHeader;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.dao.NotEnoughRowsException;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * �⍇���w�b�_���[�e�B���e�B.
 * �Ǎ����w�b�_�ɑ΂���e�폈�����s���B<br/>
 * <p>
 * <pre>
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.04.02	�V�K�쐬
 * H.Mizuno		2015.04.30	�������⍇����ʂ̑Ή��ɖ�肪����̂ŏC��
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class InquiryManageUtils {
	
	private static final Log log = LogFactory.getLog(InquiryManageUtils.class);
	
	/** Value �I�u�W�F�N�g�� Factory */
	protected ValueObjectFactory valueObjectFactory;
	
	/** �⍇���w�b�_���pDAO */
	protected DAO<InquiryHeader> inquiryHeaderDAO;
	
	/** ���⍇�����e��ʏ��pDAO */
	protected DAO<InquiryDtlInfo> inquiryDtlInfoDAO;
	
	/** ���⍇�����ꗗ�擾�p DAO */
	protected DAO<JoinResult> inquiryListDAO;
	
	/** �}�C�y�[�W������p DAO */
	protected DAO<MypageUserInterface> memberInfoDAO;
	
	/**
	 * Value �I�u�W�F�N�g�� Factory ��ݒ肷��B<br/>
	 * <br/>
	 * @param valueObjectFactory Value �I�u�W�F�N�g�� Factory
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}
	
	/**
	 * �⍇���w�b�_���pDAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param inquiryHeaderDAO �⍇���w�b�_���p DAO
	 */
	public void setInquiryHeaderDAO(DAO<InquiryHeader> inquiryHeaderDAO) {
		this.inquiryHeaderDAO = inquiryHeaderDAO;
	}
	
	/**
	 * ���⍇�����e��ʏ��pDAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param inquiryDtlInfoDAO ���⍇�����e��ʏ��p DAO
	 */
	public void setInquiryDtlInfoDAO(DAO<InquiryDtlInfo> inquiryDtlInfoDAO) {
		this.inquiryDtlInfoDAO = inquiryDtlInfoDAO;
	}
	
	/**
	 *  ���⍇�����ꗗ�擾�pDAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param inquiryListDAO  ���⍇�����ꗗ�擾�p DAO
	 */
	public void setInquiryListDAO(DAO<JoinResult> inquiryListDAO) {
		this.inquiryListDAO = inquiryListDAO;
	}
	
	/**
	 * �}�C�y�[�W������̌����E�X�V�Ɏg�p���� DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param memberInfoDAO �}�C�y�[�W������̌����E�X�VDAO
	 */
	public void setMemberInfoDAO(DAO<MypageUserInterface> memberInfoDAO) {
		this.memberInfoDAO = memberInfoDAO;
	}

	/**
	 * �p�����[�^�œn���ꂽ Form �̏��Ŗ⍇��������V�K�o�^����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * <br/>
	 * @param inputForm �����⍇���̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param inquiryType ���⍇���敪 
	 * @param mypageUserId �}�C�y�[�W�̃��[�U�[ID �i�}�C�y�[�W���O�C�����ɐݒ�B�@����ȊO�� null�j
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @return �̔Ԃ��ꂽ���₢���킹ID
	 * 
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public String addInquiry(InquiryHeaderForm inputForm,
			String inquiryType,
			String mypageUserId,
			String editUserId)
			throws Exception {

		// �V�K�o�^�����̏ꍇ�A���̓t�H�[���̒l��ݒ肷��o���[�I�u�W�F�N�g�𐶐�����B
		// �o���[�I�u�W�F�N�g�́A�t�@�N�g���[���\�b�h�ȊO�ł͐������Ȃ����B
		// �i�p�����ꂽ�o���[�I�u�W�F�N�g���g�p����Ȃ��Ȃ�ׁB�j
		InquiryHeader inquiryHeader = (InquiryHeader) this.valueObjectFactory.getValueObject("InquiryHeader");

// 2015.04.30 H.Mizuno �������⍇�����e��ʂւ̖����C�� start
//		InquiryDtlInfo[] inquiryDtlInfos
//				= new InquiryDtlInfo[] {(InquiryDtlInfo) this.valueObjectFactory.getValueObject("InquiryDtlInfo")};
		InquiryDtlInfo[] inquiryDtlInfos = null;
		if (inputForm.getInquiryDtlType() != null && inputForm.getInquiryDtlType().length > 0){
			inquiryDtlInfos = (InquiryDtlInfo[]) this.valueObjectFactory.getValueObject("InquiryDtlInfo", inputForm.getInquiryDtlType().length);
		}
// 2015.04.30 H.Mizuno �������⍇�����e��ʂւ̖����C�� end

		// �X�V�S���҂�ݒ�
		String strUserId = null;
		if (!StringValidateUtil.isEmpty(mypageUserId)) {
			strUserId = mypageUserId;
// 2015.05.11 H.Mizuno �}�C�y�[�W���[�U�[�̏ꍇ�A���[�U�[ID ��ݒ肷�鏈����ǉ� start
			inquiryHeader.setUserId(mypageUserId);
// 2015.05.11 H.Mizuno �}�C�y�[�W���[�U�[�̏ꍇ�A���[�U�[ID ��ݒ肷�鏈����ǉ� end
		} else {
			strUserId = editUserId;
		}

    	// �t�H�[���̓��͒l���o���[�I�u�W�F�N�g�ɐݒ肷��B
    	inputForm.copyToInquiryHeader(inquiryHeader, strUserId);
		
    	// ���⍇���敪�A���₢���킹�����A�Ή��X�e�[�^�X��ݒ肷��
    	inquiryHeader.setInquiryType(inquiryType);
    	inquiryHeader.setInquiryDate(inquiryHeader.getUpdDate());
    	inquiryHeader.setAnswerStatus("0");
    	
    	// �V�K�o�p�̃^�C���X�^���v����ݒ肷��B �i�X�V���̐ݒ����]�L�j
    	inquiryHeader.setInsDate(inquiryHeader.getUpdDate());
    	inquiryHeader.setInsUserId(strUserId);

		// �擾������L�[�l�Ŗ⍇���w�b�_����o�^
		this.inquiryHeaderDAO.insert(new InquiryHeader[] { inquiryHeader });


		// ���͒l��ݒ�
// 2015.04.30 H.Mizuno ���⍇�����e���CD �̍X�V���@��ύX start
//
//		inputForm.copyToInquiryDtlInfo(inquiryDtlInfos);
//
//		// �⍇�����e��ʏ��̎�L�[�l���擾������L�[�l�ɐݒ肷��B
//		for (InquiryDtlInfo inquiryDtlInfo : inquiryDtlInfos) {
//			inquiryDtlInfo.setInquiryId(inquiryHeader.getInquiryId());
//		}
//
//		try {
//			this.inquiryDtlInfoDAO.insert(inquiryDtlInfos);
//		} catch (DataIntegrityViolationException e) {
//			throw new NotFoundException();
//		}
//
		// ���⍇���e���CD ���n���ꂽ�ꍇ�̂ݍX�V�������s���B
		if (inquiryDtlInfos != null){
			// ���⍇���e���CD �� Form ����ݒ肷��B
			inputForm.copyToInquiryDtlInfo(inquiryDtlInfos);

			// �������e���CD �����M�����ƃL�[�d���G���[����������̂ŁA�o�^�������CD �͑ޔ����Ă����B
			// ���� Set �I�u�W�F�N�g�ɓo�^�������⍇�����CD �̏ꍇ�͒ǉ��������L�����Z������B
			Set<String> cdChkSet = new HashSet<>();

			// �n���ꂽ���⍇�����e���CD ���A�o�^���J��Ԃ��B
			for (InquiryDtlInfo inquiryDtlInfo : inquiryDtlInfos) {
				inquiryDtlInfo.setInquiryId(inquiryHeader.getInquiryId());
				
				// ���ɓo�^�ς̂��⍇�����e���CD �̏ꍇ�A�o�^���X�L�b�v����B
				// �܂��A�R�[�h���̂��󕶎���̏ꍇ���o�^���X�L�b�v����B
				if (!StringValidateUtil.isEmpty(inquiryDtlInfo.getInquiryDtlType()) &&
					!cdChkSet.contains(inquiryDtlInfo.getInquiryDtlType())){

					this.inquiryDtlInfoDAO.insert(new InquiryDtlInfo[]{inquiryDtlInfo});
					cdChkSet.add(inquiryDtlInfo.getInquiryDtlType());
				}
			}

		}
// 2015.04.30 H.Mizuno ���⍇�����e���CD �̍X�V���@��ύX end
		
		return inquiryHeader.getInquiryId();
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
	public int searchInquiry(InquirySearchForm searchForm) throws Exception {
		// �⍇������������������𐶐�����B
		DAOCriteria criteria = searchForm.buildCriteria();

		// �⍇���̌���
		List<JoinResult> inquiryList;
		try {
			inquiryList = this.inquiryListDAO.selectByFilter(criteria);

		} catch (NotEnoughRowsException err) {

			int pageNo = (err.getMaxRowCount() - 1)
					/ searchForm.getRowsPerPage() + 1;
			log.warn("resetting page to " + pageNo);
			searchForm.setSelectedPage(pageNo);
			criteria = searchForm.buildCriteria();
			inquiryList = this.inquiryListDAO.selectByFilter(criteria);
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
	 * @return�@DB ����擾�����⍇���w�b�_���̃o���[�I�u�W�F�N�g
	 * 
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public InquiryHeaderInfo searchInquiryPk(String inquiryId) throws Exception {
		// �⍇���w�b�_���̃C���X�^���X�𐶐�����B
		InquiryHeaderInfo inquiryHeaderInfo = new InquiryHeaderInfo();
		
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId, DAOCriteria.EQUALS, false);
		
		// �⍇���w�b�_�����擾
		List<InquiryHeader> inquiryHeaders = this.inquiryHeaderDAO.selectByFilter(criteria);
		
		if (inquiryHeaders.size() == 0) return null;
		
		// �⍇���w�b�_����ݒ�
		inquiryHeaderInfo.setInquiryHeader(inquiryHeaders.get(0));
		
		// ���⍇�����e��ʏ����擾
		List<InquiryDtlInfo> inquiryDtlInfoList = this.inquiryDtlInfoDAO.selectByFilter(criteria);
		
		// ���⍇�����e��ʏ���ݒ�
		if (inquiryDtlInfoList.size() > 0) {
			inquiryHeaderInfo.setInquiryDtlInfos(inquiryDtlInfoList.toArray(
					new InquiryDtlInfo[inquiryDtlInfoList.size()]));
		}
		
		// �}�C�y�[�W������̎�L�[�l�����������Ƃ����I�u�W�F�N�g�𐶐�
		String userId = inquiryHeaderInfo.getInquiryHeader().getUserId();
		DAOCriteria criteriaMember = new DAOCriteria();
		
		// ���[�U�[ID ���w�肳��Ă���ꍇ�A�}�C�y�[�W������擾����B
		if (!StringValidateUtil.isEmpty(userId)){
			criteriaMember.addWhereClause("userId", userId, DAOCriteria.EQUALS, true);
			
			// �}�C�y�[�W������擾����
			List<MypageUserInterface> member = this.memberInfoDAO.selectByFilter(criteriaMember);
			
			if (member.size() > 0) {
				inquiryHeaderInfo.setMypageUser(member.get(0));
			}			
		}
		
		return inquiryHeaderInfo;
	}

	/**
	 * ���N�G�X�g�p�����[�^�œn���ꂽ�⍇��ID �i��L�[�l�j�ɊY������⍇�����̑Ή��X�e�[�^�X���X�V����B<br/>
	 * InquirySearchForm �� inquiryId �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��đΉ��X�e�[�^�X���X�V����B<br/>
	 * �X�V���A�X�V�ҁA�Ή��X�e�[�^�X�ȊO�͍X�V���Ȃ��̂ŁAForm �ɐݒ肵�Ȃ����B�i�ݒ肵�Ă��l�͖��������B�j<br/>
	 * <br/>
	 * @param inputForm �Ή��X�e�[�^�X�̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
 	 * @exception NotFoundException �X�V�ΏۂȂ�
	 */
	public void updateInquiryStatus(InquiryStatusForm inputForm, String editUserId)
			throws Exception, NotFoundException {
		
    	// �X�V�����̏ꍇ�A�X�V�Ώۃf�[�^���擾����B
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inputForm.getInquiryId());

		List<InquiryHeader> inquiryHeaders = this.inquiryHeaderDAO.selectByFilter(criteria);

        // �Y������f�[�^�����݂��Ȃ��ꍇ�́A��O���X���[����B
		if (inquiryHeaders == null || inquiryHeaders.size() == 0) {
			throw new NotFoundException();
		}    	

		
        // ���⍇���w�b�_�����擾���A���͂����l�ŏ㏑������B
		InquiryHeader inquiryHeader = inquiryHeaders.get(0);

    	inputForm.copyToInquiryHeader(inquiryHeader, editUserId);

		// ���⍇���w�b�_���̍X�V
		this.inquiryHeaderDAO.update(new InquiryHeader[]{inquiryHeader});
		
	}

}
