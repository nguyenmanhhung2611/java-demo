package jp.co.transcosmos.dm3.core.model.inquiry.form;

import jp.co.transcosmos.dm3.core.vo.InquiryHousing;

/**
 * �����⍇���̓��̓p�����[�^����p�t�H�[��.
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
public class HousingInquiryForm implements InquiryForm {

	/** �⍇���w�b�_�̓��̓t�H�[�� */
	private InquiryHeaderForm commonInquiryForm;



	// TODO
	// �P��s�̃��N�G�X�g�ł���薳�����]�����鎖�B
	/**
	 * �V�X�e������CD<br/>
	 * ����������Ή����Ȃ����A�C���^�[�t�F�[�X�Ƃ��Ă͔z��Ƃ��Ă����B<br/>
	 */
	private String[] sysHousingCd;



	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	protected HousingInquiryForm(){
		super();
	}



	/**
	 * ���ʕ��i�w�b�_���j�̓��͏����擾����B<br/>
	 * <br/>
	 * @return commonInquiryForm ���ʕ��i�w�b�_���j
	 */
	public String[] getSysHousingCd() {
		return sysHousingCd;
	}

	/**
	 * ���ʕ��i�w�b�_���j�̓��͏���ݒ肷��B<br/>
	 * <br/>
	 * @param sysHousingCd ���ʕ��i�w�b�_���j�̓��͏��
	 */
	public void setSysHousingCd(String[] sysHousingCd) {
		this.sysHousingCd = sysHousingCd;
	}

	/**
	 * �⍇���w�b�_�̓��̓t�H�[�����擾����B<br/>
	 * <br/>
	 * @return  �⍇���w�b�_�̓��̓t�H�[��
	 */
	@Override
	public InquiryHeaderForm getInquiryHeaderForm() {
		return this.commonInquiryForm;
	}

	/**
	 * �⍇���w�b�_�̓��̓t�H�[����ݒ肷��B<br/>
	 * <br/>
	 * @param commonInquiryForm �⍇���w�b�_�̓��̓t�H�[��
	 */
	public void setCommonInquiryForm(InquiryHeaderForm commonInquiryForm) {
		this.commonInquiryForm = commonInquiryForm;
	}

	/**
	 * �����œn���ꂽ�����⍇�����̃o���[�I�u�W�F�N�g�Ƀt�H�[���̒l��ݒ肷��B<br/>
	 * �����œn���ꂽ idx �ɊY������s�� Form �l��ݒ肷��B<br/>
	 * �����⍇�����̍X�V�́A��L�[�l�̍X�V���K�v�ȈׁA�ʏ�� update() �ł͍X�V�ł��Ȃ��B<br/>
	 * ���̃��\�b�h�́A��{�I�ɐV�K�o�^���̎g�p��z�肵�Ă���B<br/>
	 * <br/>
	 * @param inquiryHousing �����⍇�����
	 */
	public void copyToInquiryHousing(InquiryHousing inquiryHousing) {

		// �V�X�e������CD
		inquiryHousing.setSysHousingCd(this.sysHousingCd[0]);

	}

}
