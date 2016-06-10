package jp.co.transcosmos.dm3.core.model;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.inquiry.InquiryInterface;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryForm;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquirySearchForm;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryStatusForm;


/**
 * �⍇�������Ǘ����� Model �N���X�p�C���^�[�t�F�[�X.
 * <p>
 * �⍇�����𑀍삷�� model �N���X�͂��̃C���^�[�t�F�[�X���������鎖�B<br/>
 * <p>
 * <pre>
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.16	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B<br/>
 * 
 */
public interface InquiryManage {

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
	public String addInquiry(InquiryForm inputForm, String mypageUserId, String editUserId)
			throws Exception;

	

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
	public int searchInquiry(InquirySearchForm searchForm) throws Exception;
	
	
	
	/**
	 * ���N�G�X�g�p�����[�^�œn���ꂽ�⍇��ID �i��L�[�l�j�ɊY�����镨���⍇�����𕜋A����B<br/>
	 * InquirySearchForm �� inquiryId �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��ď����擾����B
	 * �����Y���f�[�^��������Ȃ��ꍇ�Anull �𕜋A����B<br/>
	 * <br/>
	 * @param searchForm�@�������ʂƂȂ� JoinResult
	 * 
	 * @return�@DB ����擾���������⍇���̃o���[�I�u�W�F�N�g
	 * 
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public InquiryInterface searchInquiryPk(String inquiryId) throws Exception;

	
	
	/**
	 * ���N�G�X�g�p�����[�^�œn���ꂽ�⍇��ID �i��L�[�l�j�ɊY������⍇�����̑Ή��X�e�[�^�X���X�V����B<br/>
	 * InquirySearchForm �� inquiryId �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��đΉ��X�e�[�^�X���X�V����B<br/>
	 * �X�V���A�X�V�ҁA�Ή��X�e�[�^�X�ȊO�͍X�V���Ȃ��̂ŁAForm �ɐݒ肵�Ȃ����B�i�ݒ肵�Ă��l�͖��������B�j<br/>
	 * <br/>
	 * @param inputForm �Ή��X�e�[�^�X�̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * 
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
 	 * @exception NotFoundException �X�V�ΏۂȂ�
	 */
	public void updateInquiryStatus(InquiryStatusForm inputForm, String editUserId)
			throws Exception, NotFoundException;

	
	public InquiryForm createForm();
	
	public InquiryForm createForm(HttpServletRequest request);
	
	
}
