package jp.co.transcosmos.dm3.corePana.model;

import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousing;
import jp.co.transcosmos.dm3.corePana.model.recentlyInfo.form.RecentlyInfoForm;

/**
 * �ŋߌ������������Ǘ����� Model �N���X�p�C���^�[�t�F�[�X.
 * <p>
 * �ŋߌ����������𑀍삷�� model �N���X�͂��̃C���^�[�t�F�[�X���������鎖�B<br/>
 * <p>
 * <pre>
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * TRANS		2015.03.12	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B<br/>
 *
 */
public interface RecentlyInfoManage {

	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ōŋߌ�����������V�K�ǉ�����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * <br/>
	 * @param paramMap �ŋߌ����������̃V�X�e������CD�A���[�U�[ID���i�[���� Map �I�u�W�F�N�g
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * @return 0�ˍX�V�����A�܂��͍ő匏���ɒB�����A
	 *         1�˒ǉ�����
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public int addRecentlyInfo(Map<String, Object> paramMap, String editUserId) throws Exception;

	/**
	 * �ŋߌ������������������A���ʃ��X�g�𕜋A����B<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A�ŋߌ�������������������B<br/>
	 * �������ʂ� Form �I�u�W�F�N�g�Ɋi�[����A�擾�����Y��������߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 * @param searchForm ���������A����сA�������ʂ̊i�[�I�u�W�F�N�g
	 *
	 * @return ���ʃ��X�g
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public List<Map<String, PanaHousing>> searchRecentlyInfo(RecentlyInfoForm searchForm) throws Exception;

	/**
	 * �ŋߌ������������������A���ʃ��X�g�𕜋A����B<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A�ŋߌ�������������������B<br/>
	 * �������ʂ� Form �I�u�W�F�N�g�Ɋi�[����A�擾�����Y��������߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 * @param userId ���[�U�[ID
	 *
	 * @return ���ʃ��X�g
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public List<PanaHousing> searchRecentlyInfo(String userId) throws Exception;

	/**
	 * �ŋߌ��������̓o�^�������������A�o�^�����𕜋A����B<br/>
	 * �����œn���ꂽ Map �p�����[�^�̒l�Ō��������𐶐����A�ŋߌ��������̓o�^��������������B<br/>
	 * �擾�����Y��������߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 * @param paramMap ���������̊i�[�I�u�W�F�N�g
	 *
	 * @return �o�^����
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public int searchRecentlyInfo(Map<String, Object> paramMap) throws Exception;

	/**
	 * ���[�U�[ID���ŋߌ����������擾<br/>
	 * ����W�I�����������i������񂪖����ꍇ�A����сA����J�ɐݒ肳��Ă���ꍇ�j���܂�<br/>
	 *
	 * @param userId
	 *            ���[�U�[ID
	 * @return �ŋߌ����������
	 * @throws Exception
	 */
	public List<PanaHousing> getRecentlyInfoListMap(String userId, String orderBy, boolean ascending) throws Exception;

	/**
	 * ���[�U�[ID���ŋߌ��������e�[�u�����猏���擾
	 *
	 * @param userId
	 *            ���[�U�[ID
	 * @return �ŋߌ�����������
	 * @throws Exception
	 */
	public int getRecentlyInfoCnt(String userId) throws Exception;

	/**
	 * �����œn���ꂽ�l�ōŋߌ������������폜����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * �w�肳�ꂽ�ŋߌ���������񂪑��݂��Ȃ��ꍇ�́A���̂܂ܐ���I���Ƃ��Ĉ����B<br/>
	 * <br/>
	 *
	 * @param userId ���[�U�[ID
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void delRecentlyInfo(String userId) throws Exception;
}
