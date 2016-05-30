package jp.co.transcosmos.dm3.core.model;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.information.form.InformationForm;
import jp.co.transcosmos.dm3.core.model.information.form.InformationSearchForm;
import jp.co.transcosmos.dm3.core.vo.Information;
import jp.co.transcosmos.dm3.dao.JoinResult;

/**
 * ���m�点�����Ǘ����� Model �N���X�p�C���^�[�t�F�[�X.
 * <p>
 * ���m�点�������𑀍삷�� model �N���X�͂��̃C���^�[�t�F�[�X���������鎖�B<br/>
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
public interface InformationManage {

	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ł��m�点����V�K�ǉ�����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * ���m�点�ԍ� �͎����̔Ԃ����̂ŁAInformationForm �� informationNo �v���p�e�B�ɂ͒l��ݒ肵�Ȃ����B<br/>
	 * <br/>
	 * @param inputForm ���m�点���̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @return ���m�点�ԍ�
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public String addInformation(InformationForm inputForm, String editUserId)
			throws Exception;



	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ł��m�点�����X�V����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * InformationForm �� informationNo �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��čX�V����B<br/>
	 * <br/>
	 * @param inputForm ���m�点���̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @return 0 = ����I���A-2 = �X�V�ΏۂȂ�
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException �X�V�Ώۂ����݂��Ȃ��ꍇ
	 */
	public void updateInformation(InformationForm inputForm, String editUserId)
			throws Exception, NotFoundException;


	
	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ł��m�点�����폜����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * InformationSearchForm �� informationNo �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��č폜����B
	 * �܂��A�폜�Ώۃ��R�[�h�����݂��Ȃ��ꍇ�ł�����I���Ƃ��Ĉ������B<br/>
	 * <br/>
	 * @param inputForm ���m�点���̌����l�i�폜�ΏۂƂȂ� informationNo�j���i�[���� Form �I�u�W�F�N�g
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void delInformation(InformationForm inputForm) throws Exception;



	/**
	 * �T�C�g TOP �ɕ\�����邨�m�点�����擾����B<br/>
	 * ���J�Ώۋ敪 = �u�����܂ޑS����v���擾�ΏۂɂȂ�B<br/>
	 * �܂��A�V�X�e�����t�����J���Ԓ��ł��邨�m�点��񂪎擾�ΏۂƂȂ�B<br/>
	 * <br/>
	 * @return �T�C�g TOP �ɕ\������A���m�点���̃��X�g
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public List<Information> searchTopInformation() throws Exception;
	
	
	
	/**
	 * �}�C�y�[�W TOP �ɕ\�����邨�m�点�����擾����B<br/>
	 * ���J�Ώۋ敪 = �u�S�{����v�A�܂��́A�u�l�v���擾�ΏۂɂȂ邪�A�u�l�v�̏ꍇ�A���m�点���J����
	 * �̃��[�U�[ID �������ƈ�v����K�v������B<br/>
	 * �܂��A�V�X�e�����t�����J���Ԓ��ł��邨�m�点��񂪎擾�ΏۂƂȂ�B<br/>
	 * <br/>
	 * @return �}�C�y�[�W TOP �ɕ\������A���m�点���̃��X�g
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public List<Information> searchMyPageInformation(String userId) throws Exception;



	/**
	 * ���m�点�����������A���ʃ��X�g�𕜋A����B�i�Ǘ���ʗp�j<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A���m�点������������B<br/>
	 * �������ʂ� Form �I�u�W�F�N�g�Ɋi�[����A�擾�����Y��������߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 * ���������Ƃ��āA���J�Ώۋ敪���n���ꂽ�ꍇ�A�ȉ��̃f�[�^�������ΏۂƂ���B<br/>
	 * 
	 * @param searchForm ���������A����сA�������ʂ̊i�[�I�u�W�F�N�g
	 * 
	 * @return �Y������
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public int searchAdminInformation(InformationSearchForm searchForm) throws Exception;
	
	
	
	/**
	 * ���N�G�X�g�p�����[�^�œn���ꂽ�u���m�点�ԍ��v �i��L�[�l�j�ɊY�����邨�m�点���𕜋A����B�i�T�C�g TOP �p�j<br/>
	 * ���J�Ώۋ敪 = �u�����܂ޑS����v���擾�ΏۂɂȂ�B<br/>
	 * �܂��A�V�X�e�����t�����J���Ԓ��ł��邨�m�点��񂪎擾�ΏۂƂȂ�B<br/>
	 * �����Y���f�[�^��������Ȃ��ꍇ�Anull �𕜋A����B<br/>
	 * <br/>
	 * @param informationNo �擾�ΏۂƂȂ邨�m�点�ԍ�
	 * 
	 * @return�@���m�点���
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public Information searchTopInformationPk(String informationNo)
			throws Exception;


	
	/**
	 * ���N�G�X�g�p�����[�^�œn���ꂽ�u���m�点�ԍ��v �i��L�[�l�j�ɊY�����邨�m�点���𕜋A����B�i�}�C�y�[�W TOP �p�j<br/>
	 * ���J�Ώۋ敪 = �u�S�{����v�A�܂��́A�u�l�v���擾�ΏۂɂȂ邪�A�u�l�v�̏ꍇ�A���m�点���J����
	 * �̃��[�U�[ID �������ƈ�v����K�v������B<br/>
	 * �܂��A�V�X�e�����t�����J���Ԓ��ł��邨�m�点��񂪎擾�ΏۂƂȂ�B<br/>
	 * �����Y���f�[�^��������Ȃ��ꍇ�Anull �𕜋A����B<br/>
	 * <br/>
	 * @param informationNo �擾�ΏۂƂȂ邨�m�点�ԍ�
	 * @param userId �}�C�y�[�W���[�U�[ID
	 * 
	 * @return�@���m�点���
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public Information searchMyPageInformationPk(String informationNo, String userId)
			throws Exception;



	/**
	 * ���N�G�X�g�p�����[�^�œn���ꂽ�u���m�点�ԍ��v �i��L�[�l�j�ɊY�����邨�m�点���𕜋A����B�i�Ǘ��y�[�W�p�j<br/>
	 * �����Y���f�[�^��������Ȃ��ꍇ�Anull �𕜋A����B<br/>
	 * <br/>
	 * @param informationNo �擾�ΏۂƂȂ邨�m�点�ԍ�
	 * 
	 * @return�@���m�点���A���m�点���J����
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public JoinResult searchAdminInformationPk(String informationNo)
			throws Exception;
	
}
