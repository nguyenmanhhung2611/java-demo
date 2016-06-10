package jp.co.transcosmos.dm3.corePana.model;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformDtlForm;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformImgForm;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformInfoForm;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;

/**
 * ���t�H�[�������Ǘ����� Model �N���X�p�C���^�[�t�F�[�X.
 * <p>
 * ���t�H�[�����𑀍삷�� model �N���X�͂��̃C���^�[�t�F�[�X���������鎖�B<br/>
 * <p>
 * <pre>
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * TRANS		2015.03.10	�V�K�쐬
 * Thi Tran     2015.12.18  Update the interface to support searching housing by cd
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B<br/>
 *
 */
public interface ReformManage {

    /**
     * �p�����[�^�œn���ꂽ ���t�H�[���v��������V�K�ǉ�����B<br/>
     * �V�X�e�����t�H�[��CD �͎����̔Ԃ����̂ŁAReformPlan �� sysReformCd �v���p�e�B�ɂ͒l��ݒ肵�Ȃ����B<br/>
     * <br/>
     * @param reformPlan ���t�H�[���v�������
     * @param inputForm ���t�H�[��Form �I�u�W�F�N�g
     * @param userId ���O�C�����[�U�[ID
     *
     * @return �V�X�e�����t�H�[��CD
     */
    public String addReformPlan(ReformPlan reformPlan, ReformInfoForm inputForm, String userId) throws Exception;

    /**
     * ���t�H�[���v�������̍X�V���s��<br/>
     * <br/>
     * @param reformPlan ���t�H�[���v�������
     *
     * @exception NotFoundException �X�V�Ώۂ����݂��Ȃ��ꍇ
     */
    public void updateReformPlan(ReformInfoForm inputForm, String userId) throws Exception;

    /**
     * �p�����[�^�œn���ꂽ sysReformCd���L�[�Ƀ��t�H�[���v���������폜����B<br/>
     * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
     * <br/>
     * @param sysReformCd �V�X�e�����t�H�[��CD
     * @throws IOException
     * @throws Exception
     *
     */
    public void delReformPlan(String sysHousingCd, String sysReformCd, String userId) throws IOException, Exception;

    /**
     * �p�����[�^�œn���ꂽ ���t�H�[���ڍ׏���V�K�ǉ�����B<br/>
     * �}�� �͎����̔Ԃ����̂ŁAReformDtl �� divNo �v���p�e�B�ɂ͒l��ݒ肵�Ȃ����B<br/>
     * <br/>
     * @param reformDtl ���t�H�[���ڍ׏��
     *
     * @return �}��
     */
    public List<ReformDtl> addReformDtl(ReformDtlForm inputForm) throws Exception;

    /**
     * ���t�H�[���ڍ׏��̍X�V���s��<br/>
     * <br/>
     * @param form ���t�H�[���ڍ׏��̓��͒l���i�[���� Form �I�u�W�F�N�g
     *
     * @exception NotFoundException �X�V�Ώۂ����݂��Ȃ��ꍇ
     */
    public List<ReformDtl> updateReformDtl(ReformDtlForm inputForm) throws Exception;

    /**
     * �p�����[�^�œn���ꂽ Form �̏��Ń��t�H�[���ڍ׏����폜����B<br/>
     * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
     * <br/>
     * @param form ���t�H�[���ڍ׏��̍폜�������i�[����
     *
     */
    public ReformDtl delReformDtl(String sysReformCd, int divNo);

    /**
     * �p�����[�^�œn���ꂽ ���t�H�[���摜����V�K�ǉ�����B<br/>
     * �}�� �͎����̔Ԃ����̂ŁAReformImg �� divNo �v���p�e�B�ɂ͒l��ݒ肵�Ȃ����B<br/>
     * <br/>
     * @param reformImg ���t�H�[���摜���
     *
     * @return �}��
     */
    public ReformImg addReformImg(ReformImgForm inputForm) throws Exception;

    /**
     * ���t�H�[���摜���̍X�V���s��<br/>
     * <br/>
     * @param form ���t�H�[���摜���̓��͒l���i�[���� Form �I�u�W�F�N�g
     *
     * @exception NotFoundException �X�V�Ώۂ����݂��Ȃ��ꍇ
     */
    public List<ReformImg> updateReformImg(ReformImgForm inputForm) throws Exception;

    /**
    /**
     * �p�����[�^�œn���ꂽ Form �̏��Ń��t�H�[���摜�����폜����B<br/>
     * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
     * <br/>
     * @param form ���t�H�[���摜���̍폜�������i�[����
     * @throws IOException
     *
     */
    public ReformImg delReformImg(String sysReformCd, int divNo) throws IOException;

    /**
     * ���t�H�[���֘A���iReformPlan, ReformChart, ReformDtl, ReformImg)���������A����Map�𕜋A����B<br/>
     * �����œn���ꂽ sysReformCd �p�����[�^�̒l�Ō��������𐶐����A���t�H�[��������������B<br/>
     * <br/>
     * ���������Ƃ��āA�ȉ��̃f�[�^�������ΏۂƂ���B<br/>
     *
     * @param sysReformCd �V�X�e�����t�H�[��CD
     *
     * @return ���t�H�[���v��������Map
     *
     * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
     */
    public Map<String, Object> searchReform(String sysReformCd);

    /**
     * �p�����[�^�œn���ꂽ ���t�H�[���E���[�_�[�`���[�g�ڍ׏����X�V�iupdate��insert)����B<br/>
     * <br/>
     * @param reformChart ���t�H�[���E���[�_�[�`���[�g�ڍ׏��
     *
     */
    public void updReformChart(ReformInfoForm form, int count);

    /**
     * �p�����[�^�œn���ꂽ ���t�H�[���E���[�_�[�`���[�g�ڍ׏���V�K�ǉ�����B<br/>
     * <br/>
     * @param reformChart ���t�H�[���E���[�_�[�`���[�g�ڍ׏��
     *
     */
    public void addReformChart(ReformInfoForm form, int count);

    /**
     * �p�����[�^�œn���ꂽ �V�X�e�����t�H�[��CD�Ń��t�H�[���E���[�_�[�`���[�g�����폜����B<br/>
     * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
     * <br/>
     * @param sysReformCd �V�X�e�����t�H�[��CD
     *
     */
    public void delReformChart(String sysReformCd);

    /**
     * ���t�H�[���摜���̎}�Ԃ��̔Ԃ��鏈���B<br/>
     * <br/>
     * @param sysReformCd �V�X�e�����t�H�[��CD
     *
     */
    public int getReformImgDivNo(String sysReformCd);

    /**
     * ���t�H�[���ڍ׏��̎}�Ԃ��̔Ԃ��鏈���B<br/>
     * <br/>
     * @param sysReformCd �V�X�e�����t�H�[��CD
     *
     */
    public int getReformDtlDivNo(String sysReformCd);

    /**
     * �p�����[�^ �V�X�e������CD ���L�[�ɁA������{������������B<br/>
     * <br/>
     * @param form ���������A����сA�������ʂ̊i�[�I�u�W�F�N�g
     *
     * @return ������{���
     *
     * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
     */
    public HousingInfo searchHousingInfo(String sysHousingCd) throws Exception;

    /**
     * �p�����[�^ �V�X�e������CD ���L�[�ɁA������{������������B<br/>
     * <br/>
     * @param sysHousingCd �V�X�e������CD
     *
     * @return ������{���
     *
     * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
     */
    public BuildingInfo searchBuildingInfo(String sysHousingCd) throws Exception;

    /**
     * ���t�H�[���ڍ׏����������A���ʂ𕜋A����B<br/>
     * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A���t�H�[���ڍ׏�����������B<br/>
     * �������ʂ� Form �I�u�W�F�N�g�Ɋi�[����A�擾�����Y�����R�[�h��߂�l�Ƃ��ĕ��A����B<br/>
     * <br/>
     * @param sysReformCd �V�X�e�����t�H�[��CD
     * @param div_no �}��
     *
     * @return ���������ɊY�����郊�t�H�[���ڍ׏��
     *
     * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
     */
    public ReformDtl searchReformDtlByPk(String sysReformCd, String div_no)
            throws Exception;

    /**
     * ���t�H�[���v���������������A���ʂ𕜋A����B<br/>
     * <br/>
     * @param sysHousingCd �V�X�e������CD
     *
     * ���̃��\�b�h���g�p�����ꍇ�A�Öق̒��o�����i�Ⴆ�΁A����J�����̏��O�Ȃǁj���K�p�����B<br/>
	 * ����āA�t�����g���͊�{�I�ɂ��̃��\�b�h���g�p���鎖�B<br/>
     *
     * @return ���������ɊY�����郊�t�H�[���v�������
     *
     * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
     */
    public List<ReformPlan> searchReformPlan(String sysHousingCd)
            throws Exception;


    /**
     * ���t�H�[���v���������������A���ʂ𕜋A����B<br/>
     * <br/>
     * @param sysHousingCd �V�X�e������CD
     *
     * @return ���������ɊY�����郊�t�H�[���v�������
     *
     * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
     */
    public List<ReformPlan> searchReformPlan(String sysHousingCd, boolean full)
            throws Exception;

    /**
     * ������{���̃^�C���X�^���v�����X�V����B<br/>
     * <br/>
     * @param sysHousingCd �X�V�ΏۃV�X�e������CD
     * @param editUserId �X�V��ID
     */
    public void updateEditTimestamp(String sysHousingCd, String sysReformCd, String editUserId) throws Exception;

    /**
     * Search housing by cd<br/>
     * <br/>
     * @param sysHousingCd housing cd
     * @param full Return public housing if false, return all if true
     * @return Housing information corresponding to the given housing cd
     * @throws Exception Exception is thrown while implementing
     */
    public Housing searchHousingByPk(String sysHousingCd, boolean full) throws Exception;
}
