package jp.co.transcosmos.dm3.core.model;

import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingDtlForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingEquipForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingImgForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingSearchForm;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;

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
 * �폜�ȊO�̍X�V�n�����́A�K�������������̍č\�z���s�����B<br/>
 * 
 */
public interface HousingManage {

	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ŕ�����{����V�K�o�^����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * <br/>
	 * @param inputForm ������{���̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @return �̔Ԃ��ꂽ�V�X�e������CD
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public String addHousing(HousingForm inputForm, String editUserId)
			throws Exception;



	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ŕ�����{�����X�V����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * HousingForm �� sysHousingCd �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��čX�V����B<br/>
	 * <br/>
	 * @param inputForm ������{���̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException �X�V�Ώۂ����݂��Ȃ��ꍇ
	 */
	public void updateHousing(HousingForm inputForm, String editUserId)
			throws Exception, NotFoundException;



	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ŕ�����{�����폜����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * HousingForm �� sysHousingCd �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��č폜����B
	 * �܂��A�폜�Ώۃ��R�[�h�����݂��Ȃ��ꍇ�ł�����I���Ƃ��Ĉ������B<br/>
	 * �폜���͕�����{���̏]���\���폜�ΏۂƂ���B<br/>
	 * �֘A����摜�t�@�C���̍폜�� Proxy �N���X���őΉ�����̂ŁA���̃N���X���ɂ͎������Ȃ��B<br/>
	 * <br/>
	 * @param inputForm �폜�ΏۂƂȂ� sysHousingCd ���i�[���� Form �I�u�W�F�N�g
	 * @param editUserId �폜�S����
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void delHousingInfo(HousingForm inputForm, String editUserId)
			throws Exception;



	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ŕ����ڍ׏����X�V����B<br/>
	 * �Y�����镨���ڍ׏�񂪑��݂��Ȃ��Ă��A�Y�����镨����{��񂪑��݂���ꍇ�A���R�[�h��V����
	 * �ǉ����ĕ����ڍ׏���o�^����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * HousingDtlForm �� sysHousingCd �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��čX�V����B<br/>
	 * <br/>
	 * @param inputForm �����ڍ׏��̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException �e�ƂȂ镨����{��񂪑��݂��Ȃ��ꍇ
	 */
	public void updateHousingDtl(HousingDtlForm inputForm, String editUserId)
			throws Exception, NotFoundException;



	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ŕ����ݔ������X�V����B<br/>
	 * �ݔ����� DELETE & INSERT �ňꊇ�X�V����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * HousingDtlForm �� sysHousingCd �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��čX�V����B<br/>
	 * <br/>
	 * @param inputForm �����ڍ׏��̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException �e�ƂȂ镨����{��񂪑��݂��Ȃ��ꍇ
	 */
	public void updateHousingEquip(HousingEquipForm inputForm, String editUserId)
			throws Exception, NotFoundException;



	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ŕ����摜����V�K�o�^����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * <br/>
	 * �֘A����摜�t�@�C���̌��J������T���l�C���̍쐬�� Proxy �N���X���őΉ�����̂ŁA
	 * ���̃N���X���ł͑Ή����Ȃ��B<br/>
	 * <br/>
	 * @param inputForm �����摜���̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @return �V���ɒǉ������摜���̃��X�g
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException �e�ƂȂ镨����{��񂪑��݂��Ȃ��ꍇ
	 */
	public List<HousingImageInfo> addHousingImg(HousingImgForm inputForm, String editUserId)
			throws Exception, NotFoundException;



	/**
	 * �p�����[�^�œn���ꂽ�V�X�e������CD�A�摜�^�C�v�A�}�Ԃŕ����摜�����폜����B<br/>
	 * �����폜�t���O�� 1 ���ݒ肳��Ă���ꍇ�͍X�V�����ɍ폜��������B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * <br/>
	 * �֘A����摜�t�@�C���̍폜�́AProxy �N���X���őΉ�����̂ŁA���̃N���X���ł͑Ή����Ȃ��B<br/>
	 * �܂��A�X�V�����́A�摜�p�X�A�摜�t�@�C�����̍X�V�̓T�|�[�g���Ă��Ȃ��B<br/>
	 * <br/>
	 * @param sysHousingCd �V�X�e������CD
	 * @param imageType �����摜�^�C�v
	 * @param divNo �}��
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @return �폜�����������摜���̃��X�g
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public List<HousingImageInfo> updHousingImg(HousingImgForm inputForm, String editUserId)
			throws Exception, NotFoundException;


	
	/**
	 * �p�����[�^�œn���ꂽ�V�X�e������CD�A�摜�^�C�v�A�}�Ԃŕ����摜�����폜����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * �폜��A������{���̃^�C���X�^���v�����X�V����B<br/>
	 * <br/>
	 * �֘A����摜�t�@�C���̍폜�́AProxy �N���X���őΉ�����̂ŁA���̃N���X���ł͑Ή����Ȃ��B<br/>
	 * <br/>
	 * @param sysHousingCd �V�X�e������CD
	 * @param imageType �����摜�^�C�v
	 * @param divNo �}��
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @return �폜�����������摜���
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public HousingImageInfo delHousingImg(String sysHousingCd, String imageType, int divNo, String editUserId)
			throws Exception;



	/**
	 * �������i�ꕔ�A�������j���������A���ʃ��X�g�𕜋A����B�i�ꗗ�p�j<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A����������������B<br/>
	 * �������ʂ� Form �I�u�W�F�N�g�Ɋi�[����A�擾�����Y��������߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 * ���̃��\�b�h���g�p�����ꍇ�A�Öق̒��o�����i�Ⴆ�΁A����J�����̏��O�Ȃǁj���K�p�����B<br/>
	 * ����āA�t�����g���͊�{�I�ɂ��̃��\�b�h���g�p���鎖�B<br/>
	 * <br/>
	 * @param searchForm ���������A����сA�������ʂ̊i�[�I�u�W�F�N�g
	 * @param full false �̏ꍇ�A���J�ΏۊO�����O����B�@true �̏ꍇ�͏��O���Ȃ�
	 * 
	 * @return �Y������
	 * 
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public int searchHousing(HousingSearchForm searchForm) throws Exception;

	
	
	/**
	 * �������i�ꕔ�A�������j���������A���ʃ��X�g�𕜋A����B�i�ꗗ�p�j<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A����������������B<br/>
	 * �������ʂ� Form �I�u�W�F�N�g�Ɋi�[����A�擾�����Y��������߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 * @param searchForm ���������A����сA�������ʂ̊i�[�I�u�W�F�N�g
	 * @param full false �̏ꍇ�A���J�ΏۊO�����O����B�@true �̏ꍇ�͏��O���Ȃ�
	 * 
	 * @return �Y������
	 * 
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public int searchHousing(HousingSearchForm searchForm, boolean full) throws Exception;


	
	/**
	 * ���N�G�X�g�p�����[�^�œn���ꂽ�V�X�e������CD �i��L�[�l�j�ɊY�����镨�����𕜋A����B<br/>
	 * �����Y���f�[�^��������Ȃ��ꍇ�Anull �𕜋A����B<br/>
	 * <br/>
	 * ���̃��\�b�h���g�p�����ꍇ�A�Öق̒��o�����i�Ⴆ�΁A����J�����̏��O�Ȃǁj���K�p�����B<br/>
	 * ����āA�t�����g���͊�{�I�ɂ��̃��\�b�h���g�p���鎖�B<br/>
	 * <br/>
	 * @param sysHousingCd �V�X�e������CD
	 * @param full false �̏ꍇ�A���J�ΏۊO�����O����B�@true �̏ꍇ�͏��O���Ȃ�
	 * 
	 * @return�@DB ����擾�����o���[�I�u�W�F�N�g���i�[�����R���|�W�b�g�N���X
	 * 
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public Housing searchHousingPk(String sysHousingCd) throws Exception;



	/**
	 * ���N�G�X�g�p�����[�^�œn���ꂽ�V�X�e������CD �i��L�[�l�j�ɊY�����镨�����𕜋A����B<br/>
	 * �����Y���f�[�^��������Ȃ��ꍇ�Anull �𕜋A����B<br/>
	 * <br/>
	 * @param sysHousingCd �V�X�e������CD
	 * @param full false �̏ꍇ�A���J�ΏۊO�����O����B�@true �̏ꍇ�͏��O���Ȃ�
	 * 
	 * @return�@DB ����擾�����o���[�I�u�W�F�N�g���i�[�����R���|�W�b�g�N���X
	 * 
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public Housing searchHousingPk(String sysHousingCd, boolean full) throws Exception;



	/**
	 * �p�����[�^�œn���ꂽ Map �̏��ŕ����g�����������X�V����B�i�V�X�e������CD �P�ʁj<br/>
	 * �X�V�́ADelete & Insert �ŏ�������B<br/>
	 * <br/>
	 * inputData �� Map �̍\���͈ȉ��̒ʂ�B<br/>
	 *   �Ekey = �J�e�S���� �i�g���������́Acategory �Ɋi�[����l�j
	 *   �Evalue = �l���i�[���ꂽ Map �I�u�W�F�N�g
	 * inputData �� value �Ɋi�[����� Map �̍\���͈ȉ��̒ʂ�B<br/>
	 *   �Ekey = Key�� �i�g���������́Akey_name �Ɋi�[����l�j
	 *   �Evalue = ���͒l �i�g���������́Adata_value �Ɋi�[����l�j
	 * <br/>
	 * @param sysHousingCd �X�V�ΏۃV�X�e������CD
	 * @param inputData �o�^���ƂȂ� Map �I�u�W�F�N�g
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException �e�ƂȂ镨����{��񂪑��݂��Ȃ��ꍇ
	 */
	public void updExtInfo(String sysHousingCd, Map<String, Map<String, String>> inputData, String editUserId)
			throws Exception, NotFoundException;



	/**
	 * �p�����[�^�œn���ꂽ�V�X�e������CD �ɊY�����镨���g�����������폜����B�i�V�X�e������CD �P�ʁj<br/>
	 * <br/>
	 * @param sysHousingCd �폜�ΏۃV�X�e������CD
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void delExtInfo(String sysHousingCd, String editUserId)
			throws Exception;



	/**
	 * �p�����[�^�œn���ꂽ Map �̏��ŕ����g�����������X�V����B�i�J�e�S���[ �P�ʁj<br/>
	 * �X�V�́ADelete & Insert �ŏ�������B<br/>
	 * <br/>
	 * inputData �� Map �̍\���͈ȉ��̒ʂ�B<br/>
	 *   �Ekey = Key�� �i�g���������́Akey_name �Ɋi�[����l�j
	 *   �Evalue = ���͒l �i�g���������́Adata_value �Ɋi�[����l�j
	 * <br/>
	 * <br/>
	 * @param sysHousingCd �X�V�ΏۃV�X�e������CD
	 * @param category �X�V�ΏۃJ�e�S����
	 * @param inputData �o�^���ƂȂ� Map �I�u�W�F�N�g
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException �e�ƂȂ镨����{��񂪑��݂��Ȃ��ꍇ
	 */
	public void updExtInfo(String sysHousingCd, String category, Map<String, String> inputData, String editUserId)
			throws Exception, NotFoundException;



	/**
	 * �p�����[�^�œn���ꂽ�V�X�e������CD �ɊY�����镨���g�����������폜����B�i�J�e�S���[ �P�ʁj<br/>
	 * <br/>
	 * @param sysHousingCd �폜�ΏۃV�X�e������CD
	 * @param category �폜�ΏۃJ�e�S����
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void delExtInfo(String sysHousingCd, String category, String editUserId)
			throws Exception;
	


	/**
	 * �p�����[�^�œn���ꂽ Map �̏��ŕ����g�����������X�V����B�iKey �P�ʁj<br/>
	 * �X�V�́ADelete & Insert �ŏ�������B<br/>
	 * <br/>
	 * @param sysHousingCd �X�V�ΏۃV�X�e������CD
	 * @param category �X�V�ΏۃJ�e�S����
	 * @param key �X�V�Ώ�Key
	 * @param value �X�V����l
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException �e�ƂȂ镨����{��񂪑��݂��Ȃ��ꍇ
	 */
	public void updExtInfo(String sysHousingCd, String category, String key, String value, String editUserId)
			throws Exception, NotFoundException;

	
	
	/**
	 * �p�����[�^�œn���ꂽ�V�X�e������CD �ɊY�����镨���g�����������폜����B�i�L�[ �P�ʁj<br/>
	 * <br/>
	 * @param sysHousingCd �폜�ΏۃV�X�e������CD
	 * @param category �폜�ΏۃJ�e�S����
	 * @param category �폜�Ώ� Key
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void delExtInfo(String sysHousingCd, String category, String key, String editUserId)
			throws Exception;

	/**
	 * �������I�u�W�F�N�g�̃C���X�^���X�𐶐�����B<br/>
	 * �����A�J�X�^�}�C�Y�ŕ��������\������e�[�u����ǉ������ꍇ�A���̃��\�b�h���I�[�o�[���C�h���鎖�B<br/>
	 * <br/>
	 * @return Housing �̃C���X�^���X
	 */
	public Housing createHousingInstace();

}
