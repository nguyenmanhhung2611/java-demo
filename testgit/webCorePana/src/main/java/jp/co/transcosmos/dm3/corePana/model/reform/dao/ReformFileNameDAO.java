package jp.co.transcosmos.dm3.corePana.model.reform.dao;

/**
 * ���t�H�[���摜�t�@�C���������p DAO �̃C���^�[�t�F�[�X.
 * ���t�H�[���摜�t�@�C���𐶐�����N���X��DB�g�p�̗L���ɂ�����炸�A���̃C���^�[�Q�[�X����������K�v������B<br/>
 * ���A����摜�t�@�C�����ɂ͊g���q�A����уt�@�C���p�X�̏��͊܂܂Ȃ��B<br/>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * TRANS		2015.04.06	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public interface ReformFileNameDAO {

    /**
     * ���t�H�[���摜�t�@�C�������V�[�P���X����擾���ĕ��A����B<br/>
     * �g���q�A����уt�@�C���p�X�̏��͕��A����f�[�^�Ɋ܂܂Ȃ��B<br/>
     * <br/>
     * @return �摜�t�@�C�����@�i�g���q�A�p�X�Ȃ��j
     *
     * @exception Exception �C���^�[�t�F�[�X�̎����N���X�����A����C�ӂ̗�O�I�u�W�F�N�g
     */
    public String createFileName() throws Exception;
}
