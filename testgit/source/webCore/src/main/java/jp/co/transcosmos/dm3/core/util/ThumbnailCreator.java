package jp.co.transcosmos.dm3.core.util;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.housing.dao.HousingFileNameDAO;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * �T���l�C���摜�쐬����.
 * <p>
 * �w�肳�ꂽ�T���l�C���쐬�N���X�ƁA�w�肳�ꂽ�摜�T�C�Y�ŃT���l�C�����쐬����B<br/>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.04.10	�V�K�쐬
 * H.Mizuno		2015.06.26	�폜�p�X�����J�t�H���_�Ɠ��ꂾ�����ꍇ�A�폜���Ȃ��l�ɕύX
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class ThumbnailCreator {

	private static final Log log = LogFactory.getLog(ThumbnailCreator.class);

	/** ���ʃp�����[�^�I�u�W�F�N�g */
	protected CommonParameters commonParameters;

	/** �T���l�C���摜�쐬�N���X */
	protected ImgUtils imgUtils;

	/** �摜�t�@�C�����̔ԗp DAO */
	protected HousingFileNameDAO housingFileNameDAO;
	
	
	/** �t���T�C�Y�摜�̃T�u�t�H���_�� */
	protected static String FULL_SIZE_DIR = "full";
	/** ��t�H���_�̍폜���s���ꍇ�Atrue ��ݒ肷��B */
	protected boolean useEmptyDirDelete = false;


	/**
	 * �����摜�t�@�C�������擾����B<br/>
	 * <br/>
	 * @return �V�[�P���X����擾�����l���P�O���Ƀ[���l����������B�@�g���q�͎��g�ŕt���鎖�B
	 * @throws Exception
	 */
	public String getFIleName() throws Exception {
		return this.housingFileNameDAO.createFileName();
	}



	/**
	 * �T���l�C���摜���쐬����B<br/>
	 * �܂��A�I���W�i���T�C�Y�̉摜���A�摜�T�C�Y�̊K�w�� full �̃t�H���_�֔z�u����B<br/>
	 * thumbnailMap �̍\���͉��L�̒ʂ�B�@�t�@�C�����͌��̃t�@�C�������g�p�����B<br/>
	 * <ul>
	 * <li>Key : �T���l�C���쐬���̃t�@�C�����i�t���p�X�j</li>
	 * <li>value : �T���l�C���̏o�͐�p�X�i���[�g�`�V�X�e�������ԍ��܂ł̃p�X�B�@�T�C�Y��A�t�@�C�����͊܂܂Ȃ��B�j</li>
	 * </ul>
	 * @param thumbnailMap �쐬����t�@�C���̏��
	 * 
	 * @throws IOException 
	 * @throws Exception �Ϗ��悪�X���[����C�ӂ̗�O
	 */
	public void create(Map<String, String> thumbnailMap)
			throws IOException, Exception {

		// �쐬����t�@�C�����J��Ԃ�
		for (Entry<String, String> e : thumbnailMap.entrySet()){

			// �T���l�C���쐬���̃t�@�C���I�u�W�F�N�g���쐬����B
			File srcFile = new File(e.getKey());

			// �T���l�C���o�͐�̃��[�g�p�X �i�摜�T�C�Y�̒��O�܂ł̃t�H���_�K�w�j
			String destRootPath = e.getValue();
			if (!destRootPath.endsWith("/")) destRootPath += "/";

			// �I���W�i���摜���t���T�C�Y�摜�Ƃ��� copy ����B
			FileUtils.copyFileToDirectory(srcFile, new File(destRootPath + FULL_SIZE_DIR));

						
			// �T�C�Y���X�g�����ݒ�̏ꍇ�̓T���l�C���摜���쐬���Ȃ��B
			if (this.commonParameters.getThumbnailSizes() == null) return;

			// �쐬����T���l�C���T�C�Y���J��Ԃ�
			for (Integer size : this.commonParameters.getThumbnailSizes()){

				// �o�͐�T�u�t�H���_�����݂��Ȃ��ꍇ�A�t�H���_���쐬����B
				// createImgFile() �́A�T�u�t�H���_���쐬���Ȃ��̂�..�B
				File subDir = new File(destRootPath + size.toString());
				if (!subDir.exists()){
					FileUtils.forceMkdir(subDir);
				}

				// �T���l�C���̏o�͐�̓t�@�C���T�C�Y���ɈقȂ�̂ŁA�T�C�Y���ɐ�������B
				File destFile = new File(destRootPath + size.toString() + "/" + srcFile.getName());
				// �T���l�C���摜���쐬
				this.imgUtils.createImgFile(srcFile, destFile, size.intValue());
			}
		}
	}



	/**
	 * �摜�t�@�C�����f�B���N�g���P�ʂō폜����B<br/>
	 * targetRootPath + delImgPath �̃f�B���N�g�����폜����B<br/>
	 * <br/>
	 * @param targetRootPath �폜�Ώ� Root �p�X
	 * @param delImgFile �폜�Ώۃf�B���N�g�����i�[���� Set �I�u�W�F�N�g
	 * 
	 * @throws IOException 
	 */
	public void deleteImageDir(List<String> targetRootPath, Set<String> delImgPath) throws IOException {

		for (String rootPath : targetRootPath){

			if (StringValidateUtil.isEmpty(rootPath)) rootPath = "/";
			if (!rootPath.endsWith("/")) rootPath += "/";

			for (String path : delImgPath){

				// �X�y�[�X����菜���Ă����B
				path = rootPath + path.trim();

				log.info("delete housing image file : " + path);

				// �f�B���N�g���P�ʂ̍폜�̏ꍇ�A�ݒ�l��f�[�^�ɕs��������Ɨ\�z�O�̏ꏊ���폜����郊�X�N������B
				// ���X�N���������ׁA���H�����ꂽ�폜�Ώۂ����[�g�p�X�Ŗ������ƁA��łȂ������`�F�b�N���Ă����B
				if (StringValidateUtil.isEmpty(path) || path.equals("/")) continue;

				// ���[�g�p�X + �摜�p�X�̌��ʂ��A���[�g�p�X�ƈ�v�����ꍇ�A�폜���s��Ȃ��l�ɂ���B
				// �i������̃o�O�ɔ��������S���u�j
				if (path.equals(rootPath)) continue;

				// �w�肳�ꂽ�t�H���_�z�����폜
				FileUtils.deleteDirectory(new File(path));
			}

		}
		
	}



	/**
	 * �����摜�t�@�C�����ʂɍ폜����B<br/>
	 * filePath �Ŏw�肵���t�H���_���̃t�@�C������̏ꍇ�A�t�H���_���ƍ폜����B
	 * <br/>
	 * @param filePath ���[�g�`�V�X�e������CD �܂ł̃p�X�i�摜�T�C�Y�̉��܂ł̃p�X�j
	 * @param fileName�@�摜�t�@�C����
	 * 
	 * @throws IOException 
	 */
	public void deleteImgFile(String filePath, String fileName) throws IOException{
		
		// �I���W�i���摜�̍폜
		deleteFile(new File(filePath + FULL_SIZE_DIR + "/" + fileName)); 

		// �T���l�C���T�C�Y���ݒ肳��Ă���ꍇ�A�T���l�C���t�@�C�����폜����B
		if (this.commonParameters.getThumbnailSizes() != null) {
			
			for (Integer size : this.commonParameters.getThumbnailSizes()){
				// �T���l�C���摜���폜
				deleteFile(new File(filePath + size.toString() + "/" + fileName));
			}
		}


		// note
		// ��t�H���_�̍폜�@�\��L���ɂ���ꍇ�A�Ăяo�����œ���������d�g�݂�񋟂���K�v������B
		// �����t�H���_�z���Ƀt�@�C����z�u���鏈�������݂���ꍇ�A���������Ă��Ȃ��Ƌ����������Ƀt�H���_
		// ���ƍ폜����Ă��܂��\��������B

		if (useEmptyDirDelete){
			// �w�肳�ꂽ filePath �z������̏ꍇ�A�t�H���_���폜����B
			try {
				Collection<File> fileList = FileUtils.listFiles(new File(filePath),null,true);
				if (fileList.size()==0){
					FileUtils.deleteDirectory(new File(filePath));
				}
			
			} catch (IllegalArgumentException e){
				// �폜�Ώە����t�H���_�����݂��Ȃ��ꍇ�AIllegalArgumentException ����������B
				// ���̏ꍇ�̗�O�͖�������B
				log.warn(e.getMessage(), e);
			}
		}
	}

	

	/**
	 * �w�肳�ꂽ�t�@�C�����폜����B <br/>
	 * ���������t�@�C�������݂���̂ɍ폜�ł��Ȃ������ꍇ�A�x�������O�o�͂���B<br/>
	 * <br/>
	 * @param targetFile �폜�Ώۃt�@�C���I�u�W�F�N�g
	 */
	private void deleteFile(File targetFile){
		boolean ret = targetFile.delete();
		if (!ret && targetFile.exists()) {
			// ���t�@�C�������݂��A�t�@�C���̍폜�����s�����ꍇ�A��O���X���[����B
			throw new RuntimeException(targetFile.getPath() + " couldn't be deleted.");
		}
	}



	/**
	 * ���ʃp�����[�^�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param commonParameters ���ʃp�����[�^�I�u�W�F�N�g
	 */
	public void setCommonParameters(CommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * �T���l�C���摜�쐬�N���X��ݒ肷��B<br/>
	 * <br/>
	 * @param imgUtils �T���l�C���摜�쐬�N���X
	 */
	public void setImgUtils(ImgUtils imgUtils) {
		this.imgUtils = imgUtils;
	}

	/**
	 * �摜�t�@�C�����̔ԗp DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param housingFileNameDAO �摜�t�@�C�����̔ԗp DAO
	 */
	public void setHousingFileNameDAO(HousingFileNameDAO housingFileNameDAO) {
		this.housingFileNameDAO = housingFileNameDAO;
	}

	/**
	 * �폜��A��t�H���_�ɂȂ����ꍇ�A�t�H���_���ƍ폜����ꍇ�� true ��ݒ肷��B<br/>
	 * �f�t�H���g �� false �Ŗ���<br/>
	 * <br/>
	 * @param useEmptyDirDelete �L���ɂ���ꍇ�� true 
	 */
	public void setUseEmptyDirDelete(boolean useEmptyDirDelete) {
		this.useEmptyDirDelete = useEmptyDirDelete;
	}

}
