package jp.co.transcosmos.dm3.core.model;

import jp.co.transcosmos.dm3.core.model.exception.MaxEntryOverException;
import jp.co.transcosmos.dm3.core.model.favorite.form.FavoriteSearchForm;

/**
 * ���C�ɓ�������Ǘ����� Model �N���X�p�C���^�[�t�F�[�X.
 * <p>
 * ���C�ɓ�����𑀍삷�� model �N���X�͂��̃C���^�[�t�F�[�X���������鎖�B<br/>
 * <p>
 * 
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
public interface FavoriteManage {

	/**
	 * �����œn���ꂽ�l�ł��C�ɓ������o�^����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * �w�肳�ꂽ���[�U�[ID�A�V�X�e������CD �����ɑ��݂���ꍇ�A�㏑���ۑ�����B<br/>
	 * �@�o�^�������o�^�����̏���Ȃ��B<br/>
	 * <br/>
	 * 
	 * @param userId �}�C�y�[�W�̃��[�U�[ID
	 * @param sysHousingCd ���C�ɓ���o�^����V�X�e������CD
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception MaxEntryOverException�@�ő�o�^���I�[�o�[
	 */
	public void addFavorite(String userId, String sysHousingCd) throws Exception;

	/**
	 * �����œn���ꂽ�l�ł��C�ɓ�������폜����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * �w�肳�ꂽ���C�ɓ����񂪑��݂��Ȃ��ꍇ�́A���̂܂ܐ���I���Ƃ��Ĉ����B<br/>
	 * <br/>
	 * 
	 * @param userId ���[�U�[ID
	 * @param sysHousingCd �V�X�e������CD
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void delFavorite(String userId, String sysHousingCd) throws Exception;

	/**
	 * ���C�ɓ�������������A���ʃ��X�g�𕜋A����B<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A���C�ɓ��������������B<br/>
	 * �������ʂ� Form �I�u�W�F�N�g�Ɋi�[����A�擾�����Y��������߂�l�Ƃ��ĕ��A����B<br/>
	 * �������ɑ��݂��Ȃ����C�ɓ�����͍폜������ŁA�������ʂ��擾����<br/>
	 * <br/>
	 * @param userId �}�C�y�[�W�̃��[�U�[ID
	 * @param searchForm ���������A����сA�������ʂ̊i�[�I�u�W�F�N�g
	 * 
	 * @return �擾����
	 * 
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public int searchFavorite(String userId, FavoriteSearchForm searchForm) throws Exception;

	/**
	 * �����œn���ꂽ userId �ɊY�����邨�C�ɓ�����̌������擾����B<br/>
	 * <br/>
	 * 
	 * @param userId �}�C�y�[�W�̃��[�U�[ID
	 * 
	 * @return ���C�ɓ���o�^���������̌���
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public int getFavoriteCnt(String userId) throws Exception;

}
