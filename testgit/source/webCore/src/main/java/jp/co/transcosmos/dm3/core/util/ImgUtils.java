package jp.co.transcosmos.dm3.core.util;

import java.io.File;

import jp.co.transcosmos.dm3.core.util.imgUtils.ImgInfo;


/**
 * �T���l�C���摜�쐬�N���X�̃C���^�[�t�F�[�X.
 * <p>
 * �T���l�C���摜���쐬����N���X�͂��̃C���^�t�F�[�X���������鎖�B<br/>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.23	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public interface ImgUtils {

	/**
	 * �w�肳�ꂽ�摜�t�@�C���̉摜�����擾����B<br/>
	 * <br/>
	 * @param srcFile ���擾�Ώۉ摜�̃t���p�X
	 * @return�@�摜���
	 * 
	 * @throws Exception �����N���X���X���[�����O
	 */
	public ImgInfo getImgInfo(String srcFile) throws Exception;
	
	/**
	 * �w�肳�ꂽ�摜�t�@�C���̉摜�����擾����B<br/>
	 * <br/>
	 * @param srcFile ���擾�Ώۉ摜�� File �I�u�W�F�N�g
	 * @return�@�摜���
	 * 
	 * @throws Exception �����N���X���X���[�����O
	 */
	public ImgInfo getImgInfo(File srcFile) throws Exception;

	
	
	/**
	 * �T���l�C���摜���쐬����B<br/>
	 * <br/>
	 * @param srcFile�@�I���W�i���摜�̃t���p�X
	 * @param destFile �o�͂���T���l�C���摜�̏o�͐�t���p�X
	 * @param size �T���l�C���摜�̃T�C�Y
	 * @return �쐬�����T���l�C���摜�̏��
	 * 
	 * @throws Exception �����N���X���X���[�����O
	 */
	public ImgInfo createImgFile(String srcFile, String destFile, int size) throws Exception;

	/**
	 * �T���l�C���摜���쐬����B<br/>
	 * <br/>
	 * @param srcFile�@�I���W�i���摜�� File �I�u�W�F�N�g
	 * @param destFile �o�͂���T���l�C���摜�̏o�͐� File �I�u�W�F�N�g
	 * @param size �T���l�C���摜�̃T�C�Y
	 * @return �쐬�����T���l�C���摜�̏��
	 * 
	 * @throws Exception �����N���X���X���[�����O
	 */
	public ImgInfo createImgFile(File srcFile, File destFile, int size) throws Exception;

}
