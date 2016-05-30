package jp.co.transcosmos.dm3.core.model.inquiry;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.model.HousingManage;
import jp.co.transcosmos.dm3.core.model.InquiryManage;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.inquiry.form.HousingInquiryForm;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryForm;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryFormFactory;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquirySearchForm;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryStatusForm;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.InquiryHousing;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;

/**
 * �����⍇�� model �̎����N���X.
 * <p>
 * <pre>
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.04.02	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class HousingInquiryManageImpl implements InquiryManage {

	/** �����⍇�����p DAO */
	protected DAO<InquiryHousing> inquiryHousingDAO;

	/** �⍇���w�b�_�p���ʏ��� */
	protected InquiryManageUtils inquiryManageUtils;

	/** �⍇���t�H�[���� Factory �N���X */
	protected InquiryFormFactory formFactory;

	/** ������񃁃��e�i���X�� model */
	protected HousingManage housingManager;

	/** Value �I�u�W�F�N�g�� Factory */
	protected ValueObjectFactory valueObjectFactory;


	/**
	 * �����⍇�����pDAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param inquiryHousingDAO �����⍇�����p DAO
	 */
	public void setInquiryHousingDAO(DAO<InquiryHousing> inquiryHousingDAO) {
		this.inquiryHousingDAO = inquiryHousingDAO;
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
	 * �⍇���t�H�[���� Factory �N���X��ݒ肷��B<br/>
	 * <br/>
	 * @param formFactory �⍇���t�H�[���� Factory �N���X
	 */
	public void setFormFactory(InquiryFormFactory formFactory) {
		this.formFactory = formFactory;
	}

	/**
	 * ������񃁃��e�i���X�� model��ݒ肷��B<br/>
	 * <br/>
	 * @param housingManager �⍇���t�H�[���� Factory �N���X
	 */
	public void setHousingManager(HousingManage housingManager) {
		this.housingManager = housingManager;
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

		// �⍇���w�b�_�̓o�^���������s
		String inquiryId = this.inquiryManageUtils.addInquiry(
				inputForm.getInquiryHeaderForm(), "00", mypageUserId, editUserId);

		// �����⍇������Value �I�u�W�F�N�g�𐶐�����B
		InquiryHousing inquiryHousing = (InquiryHousing) this.valueObjectFactory.getValueObject("InquiryHousing");

		// ���₢���킹ID��ݒ肷��B
		inquiryHousing.setInquiryId(inquiryId);

		// �t�H�[���̒l�� Value �I�u�W�F�N�g�� copy ����B
		((HousingInquiryForm) inputForm).copyToInquiryHousing(inquiryHousing);

		// �����⍇�����̓o�^���������s
		this.inquiryHousingDAO.insert(new InquiryHousing[]{inquiryHousing});

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

		// �⍇���w�b�_�̌������������s���Č��ʂ𕜋A����B
		// �⍇���ꗗ�́A�⍇���w�b�_�Ŋ�������͈͂ł��鎖���O��ƂȂ�B
		return this.inquiryManageUtils.searchInquiry(searchForm);
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
	public HousingInquiry searchInquiryPk(String inquiryId) throws Exception {

		// �⍇���w�b�_�̏����擾���ĕ����⍇���I�u�W�F�N�g�֐ݒ肷��B
		HousingInquiry inquiry = createHousingInquiry(this.inquiryManageUtils.searchInquiryPk(inquiryId));

		// �����⍇���w�b�_�̏�񂪖����ꍇ�A�߂�l��null�ݒ肵�ď����𒆒f����B
		if (inquiry.getInquiryHeaderInfo() == null) return null;

		// �⍇���w�b�_�̏�񂪎擾�ł����ꍇ�́A
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId, DAOCriteria.EQUALS, false);

		// �����⍇�������擾
		List<InquiryHousing> inquiryHousings = this.inquiryHousingDAO.selectByFilter(criteria);

		// �������� model ���g�p���ĕ��������擾����B
		List<Housing> housings = new ArrayList<Housing>();
		Housing housing = null;

		for (InquiryHousing inquiryHousing : inquiryHousings){
			housing = this.housingManager.searchHousingPk(inquiryHousing.getSysHousingCd());

			// ������񂪎擾�ł����ꍇ�́A�����⍇���I�u�W�F�N�g�֐ݒ肷��B
			if (housing != null) {
				housings.add(housing);
			}
		}

		// �����⍇������ݒ�
		inquiry.setInquiryHousings(inquiryHousings);

		// ��������ݒ�
		inquiry.setHousings(housings);

		return inquiry;
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
	@Override
	public void updateInquiryStatus(InquiryStatusForm inputForm, String editUserId)
			throws Exception, NotFoundException {

		// �⍇���X�e�[�^�X�X�V���������s����B
		this.inquiryManageUtils.updateInquiryStatus(inputForm, editUserId);
	}



	/**
	 * ��̕����⍇���t�H�[���̃C���X�^���X�𐶐�����B<br/>
	 * �I�u�W�F�N�g�������́A��̖⍇���w�b�_�I�u�W�F�N�g���������Đݒ肷��B<br/>
	 * <bvr/>
	 * @return ��̕����⍇���t�H�[���̃C���X�^���X
	 */
	@Override
	public InquiryForm createForm() {
		HousingInquiryForm form = this.formFactory.createHousingInquiryForm();
		form.setCommonInquiryForm(this.formFactory.createInquiryHeaderForm());

		return form;
	}

	/**
	 * ���͂����l��ݒ肵�������₢���킹�t�H�[���̃C���X�^���X�𐶐�����B<br/>
	 * �I�u�W�F�N�g�������́A���͒l��ݒ肵���⍇���w�b�_�I�u�W�F�N�g���������Đݒ肷��B<br/>
	 */
	@Override
	public InquiryForm createForm(HttpServletRequest request) {
		HousingInquiryForm form = this.formFactory.createHousingInquiryForm(request);
		form.setCommonInquiryForm(this.formFactory.createInquiryHeaderForm(request));

		return form;
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
