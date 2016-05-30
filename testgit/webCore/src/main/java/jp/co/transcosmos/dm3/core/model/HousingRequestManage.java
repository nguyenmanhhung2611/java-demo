package jp.co.transcosmos.dm3.core.model;

import java.util.List;

import jp.co.transcosmos.dm3.adminCore.request.form.HousingRequestForm;
import jp.co.transcosmos.dm3.core.model.exception.MaxEntryOverException;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housingRequest.HousingRequest;
import jp.co.transcosmos.dm3.core.model.housingRequest.form.RequestSearchForm;


/**
 * �������N�G�X�g�����Ǘ����� Model �N���X�p�C���^�[�t�F�[�X.
 * <p>
 * �������N�G�X�g���𑀍삷�� model �N���X�͂��̃C���^�[�t�F�[�X���������鎖�B<br/>
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
public interface HousingRequestManage {


	/**
	 * �w�肳�ꂽ Form �̒l�ŕ������N�G�X�g����o�^����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * �������N�G�X�gID �͎����̔Ԃ����̂ŁAHousingRequestForm �� housingRequestId
	 * �v���p�e�B�ɂ͒l��ݒ肵�Ȃ����B<br/>
	 * �o�^����������l�ɒB����ꍇ�͓o�^�����ɃG���[�Ƃ��ĕ��A���鎖�B<br/>
	 * <br/>
	 * @param userId �}�C�y�[�W���[�U�[ID
	 * @param inputForm ���͂��ꂽ�������N�G�X�g���
	 * 
	 * @return �̔Ԃ��ꂽ�������N�G�X�gID
	 * 
  	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
  	 * @exception MaxEntryOverException �ő�o�^���I�[�o�[
	 */
	public String addRequest(String userId, HousingRequestForm inputForm)
			throws Exception, MaxEntryOverException;



	/**
	 * �w�肳�ꂽ Form �̒l�ŕ������N�G�X�g�����X�V����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * �X�V�Ɏg�p����L�[�́AHousingRequestForm�@�� housingRequestId �ƁA�����œn���ꂽ
	 * userId ���g�p���鎖�B  �iuserId �̓��N�G�X�g�p�����[�^�Ŏ擾���Ȃ����B�j<br/>
	 * <br/>
	 * @param userId �}�C�y�[�W���[�U�[ID
	 * @param inputForm ���͂��ꂽ�������N�G�X�g���i�X�V�Ώۂ̎�L�[�l���܂ށj
	 * 
  	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
  	 * @exception NotFoundException �X�V�ΏۂȂ�
	 */
	public void updateRequest(String userId, HousingRequestForm inputForm)
			throws Exception, NotFoundException;



	/**
	 * �w�肳�ꂽ Form �̒l�ŕ������N�G�X�g�����폜����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * �폜�Ɏg�p����L�[�́AHousingRequestForm�@�� housingRequestId �ƁA�����œn���ꂽ
	 * userId ���g�p���鎖�B  �iuserId �̓��N�G�X�g�p�����[�^�Ŏ擾���Ȃ����B�j<br/>
	 * <br/>
	 * @param userId �}�C�y�[�W���[�U�[ID
	 * @param inputForm ���͂��ꂽ�������N�G�X�g���i�폜�Ώۂ̎�L�[�l���܂ށj
	 * 
  	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void delRequest(String userId, HousingRequestForm inputForm)
			throws Exception;



	/**
	 * �w�肳�ꂽ���[�U�[ID �ɊY������A�������N�G�X�g�i���������j�̈ꗗ���擾����B<br/>
	 * <br/>
	 * @param userId �}�C�y�[�W���[�U�[ID
	 * 
	 * @return �擾����
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public List<HousingRequest> searchRequest(String userId)
			throws Exception;
	
	
	
	/**
	 * �w�肳�ꂽ���[�U�[ID ����������A�������N�G�X�g�i���������j�̐����擾����B<br/>
	 * <br/>
	 * @param userId �}�C�y�[�W���[�U�[ID
	 * 
	 * @return �Y������
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public int searchRequestCnt(String userId)
			throws Exception;



	/**
	 * �w�肳�ꂽ���������i���N�G�X�gID�j�ɊY�����镨���̏����擾����B<br/>
	 * <br/>
	 * @param userId �}�C�y�[�W���[�U�[ID
	 * @param requestId ���N�G�X�gID
	 * 
	 * @return �Y������
	 * 
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public int searchHousing(String userId, RequestSearchForm searchForm) throws Exception;



}
