package jp.co.transcosmos.dm3.core.model;


import jp.co.transcosmos.dm3.core.model.building.Building;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingImageInfoForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingLandmarkForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingSearchForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingStationInfoForm;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;

/**
 * ���������Ǘ����� Model �N���X�p�C���^�[�t�F�[�X.
 * <p>
 * �������𑀍삷�� model �N���X�͂��̃C���^�[�t�F�[�X���������鎖�B<br/>
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
public interface BuildingManage {

	
	/**
	 * �p�����[�^�œn���ꂽ Form �̏��Ō�������V�K�o�^����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * <br/>
	 * @param inputForm �Ǘ����[�U�[�̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @return �̔Ԃ��ꂽ�V�X�e������CD
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public String addBuilding(BuildingForm inputForm, String editUserId)
			throws Exception;



	/**
	 * �p�����[�^�œn���ꂽ Form �̏��Ō�����{�����X�V����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * BuildingForm �� sysBuildingCd �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��čX�V����B<br/>
	 * <br/>
	 * @param inputForm �������̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException �X�V�Ώۂ����݂��Ȃ��ꍇ
	 */
	public void updateBuildingInfo(BuildingForm inputForm, String editUserId)
			throws Exception, NotFoundException;



	/**
	 * �p�����[�^�œn���ꂽ Form �̏��Ō�����{�����폜����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * BuildingForm �� sysBuildingCd �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��č폜����B
	 * �܂��A�폜�Ώۃ��R�[�h�����݂��Ȃ��ꍇ�ł�����I���Ƃ��Ĉ������B<br/>
	 * <br/>
	 * @param sysBuildingCd �폜�ΏۂƂȂ� sysBuildingCd
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void delBuildingInfo(String sysBuildingCd) throws Exception;

	
	
	/**
	 * ���������������A���ʃ��X�g�𕜋A����B�i�ꗗ�p�j<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A����������������B<br/>
	 * �������ʂ� Form �I�u�W�F�N�g�Ɋi�[����A�擾�����Y��������߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 * @param searchForm ���������A����сA�������ʂ̊i�[�I�u�W�F�N�g
	 * 
	 * @return �Y������
	 * 
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public int searchBuilding(BuildingSearchForm searchForm) throws Exception;

	
	
	/**
	 * ���N�G�X�g�p�����[�^�œn���ꂽ�V�X�e������CD �i��L�[�l�j�ɊY�����錚�����𕜋A����B<br/>
	 * BuildingSearchForm �� searchForm �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��ď����擾����B
	 * �����Y���f�[�^��������Ȃ��ꍇ�Anull �𕜋A����B<br/>
	 * <br/>
	 * @param sysBuildingCd�@�V�X�e�������ԍ�
	 * 
	 * @return�@DB ����擾�����������̃o���[�I�u�W�F�N�g
	 * 
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public Building searchBuildingPk(String sysBuildingCd) throws Exception;


	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ōŊ��w�����X�V����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * BuildingStationInfoForm �� sysBuildingCd �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��čX�V����B<br/>
	 * <br/>
	 * @param inputForm �Ŋ��w���̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void updateBuildingStationInfo(BuildingStationInfoForm inputForm)
			throws Exception;

	
	/**
	 * �p�����[�^�œn���ꂽ Form �̏��Œn������X�V����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * BuildingLandmarkForm �� sysBuildingCd �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��čX�V����B<br/>
	 * <br/>
	 * @param inputForm �n����̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void updateBuildingLandmark(BuildingLandmarkForm inputForm)
			throws Exception;

	/**
	 * �p�����[�^�œn���ꂽ Form �̏��Ō����摜����V�K�o�^����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * <br/>
	 * @param inputForm �����摜���̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void addBuildingImageInfo(BuildingImageInfoForm inputForm)
			throws Exception;
	
	/**
	 * �p�����[�^�œn���ꂽ Form �̏��Ō����摜�����X�V����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * <br/>
	 * @param inputForm �����摜���̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void updBuildingImageInfo(BuildingImageInfoForm inputForm)
			throws Exception;
	
	/**
	 * �p�����[�^�œn���ꂽ Form �̏��Ō����摜�����폜����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * <br/>
	 * @param inputForm �����摜���̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void delBuildingImageInfo(BuildingImageInfoForm inputForm)
			throws Exception;


	/**
	 * �������I�u�W�F�N�g�̃C���X�^���X�𐶐�����B<br/>
	 * �����A�J�X�^�}�C�Y�Ō��������\������e�[�u����ǉ������ꍇ�A���̃��\�b�h���I�[�o�[���C�h���鎖�B<br/>
	 * <br/>
	 * @return Building �̃C���X�^���X
	 */
	public Building createBuildingInstace();

}
