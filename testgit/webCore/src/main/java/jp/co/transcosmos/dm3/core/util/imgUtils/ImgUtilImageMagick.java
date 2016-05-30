package jp.co.transcosmos.dm3.core.util.imgUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.transcosmos.dm3.core.util.ExecuteShell;
import jp.co.transcosmos.dm3.core.util.ImgUtils;


/**
 * ImageMagick �ɂ��T���l�C���摜�̍쐬�N���X.
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.23	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�̎��s���ɂ́AImageMagick ���C���X�g�[�����Ă������B
 * 
 */
public class ImgUtilImageMagick implements ImgUtils {

	private static final Log log = LogFactory.getLog(ImgUtilImageMagick.class);
	
	/** ImageMagick �摜�ϊ��R�}���h�� */
	private String convertCommand = "convert";

	

	/**
	 * ImageMagick �̉摜�ϊ��R�}���h����ݒ肷��B<br/>
	 * �ʏ�A�f�t�H���g�ݒ�ŕύX����K�v�͖������Apath �̖��Ńt���p�X��`����ꍇ�͂��̃v���p�e�B��ݒ肷��B<br/>
	 * <br/>
	 * @param convertCommand�@ImageMagick �̉摜�ϊ��R�}���h��
	 */
	public void setConvertCommand(String convertCommand) {
		this.convertCommand = convertCommand;
	}



	/**
	 * �摜�����擾����B<br/>
	 * <br/>
	 * @param srcFile �I���W�i���摜�̃t���p�X�t�@�C����
	 * @return �摜��񂪊i�[���ꂽ ImgInfo �I�u�W�F�N�g
	 * 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@Override
	public ImgInfo getImgInfo(String srcFile) throws FileNotFoundException, IOException {

		return getImgInfo(new File(srcFile));
	}
	
	/**
	 * �摜�����擾����B<br/>
	 * <br/>
	 * @param srcFile �I���W�i���摜�� File �I�u�W�F�N�g
	 * @return �摜��񂪊i�[���ꂽ ImgInfo �I�u�W�F�N�g
	 * 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@Override
	public ImgInfo getImgInfo(File srcFile)	throws FileNotFoundException, IOException {

		ImgInfo imgInfo = new ImgInfo();
		
		try (FileInputStream srcFis = new FileInputStream(srcFile)){
			BufferedImage srcImgBuff = ImageIO.read(srcFis);

			imgInfo.setWidth(srcImgBuff.getWidth());		// ���摜�t�@�C���T�C�Y�i���j
			imgInfo.setHeight(srcImgBuff.getHeight());		// ���摜�t�@�C���T�C�Y�i���j

			// �c���E��������
			if (imgInfo.getWidth() > imgInfo.getHeight()){
				imgInfo.setHwFlg(0);						// ����

			} else if (imgInfo.getWidth() < imgInfo.getHeight()) {
				imgInfo.setHwFlg(1);						// �c��

			} else {
				imgInfo.setHwFlg(2);						// �c������

			}
		}

		return imgInfo;
	}


	
	/**
	 * �T���l�C���摜���쐬����B<br/>
	 * <ul>
	 *   <li>�I���W�i���摜�ƃT���l�C���摜�������T�C�Y�̏ꍇ�A���̂܂܃R�s�[����B</li>
	 *   <li>�I���W�i���摜���T���l�C���摜�����������ꍇ�A�I���W�i���摜�����̂܂܃R�s�[����B</li>
	 *   <li>�����摜�̏ꍇ�A�w�肳�ꂽ�T���l�C���T�C�Y�������Ƃ��Ďg�p����B</li>
	 *   <li>�c���摜�̏ꍇ�A�w�肳�ꂽ�T���l�C���T�C�Y���c���Ƃ��Ďg�p����B</li>
	 * </ul>
	 * <br/>
	 * @param srcFile �I���W�i���摜�̃t���p�X�t�@�C����
	 * @param destFile �T���l�C���̏o�͐�t���p�X�t�@�C����
	 * @param size �쐬����T���l�C���̃T�C�Y
	 * 
	 * @return �쐬�����T���l�C���̃T�C�Y���
	 * @throws Exception 
	 */
	@Override
	public ImgInfo createImgFile(String srcFile, String destFile, int size)
			throws Exception {

		return createImgFile(new File(srcFile), new File(destFile), size);
	}

	/**
	 * �T���l�C���摜���쐬����B<br/>
	 * <ul>
	 *   <li>�I���W�i���摜�ƃT���l�C���摜�������T�C�Y�̏ꍇ�A���̂܂܃R�s�[����B</li>
	 *   <li>�I���W�i���摜���T���l�C���摜�����������ꍇ�A�I���W�i���摜�����̂܂܃R�s�[����B</li>
	 *   <li>�����摜�̏ꍇ�A�w�肳�ꂽ�T���l�C���T�C�Y�������Ƃ��Ďg�p����B</li>
	 *   <li>�c���摜�̏ꍇ�A�w�肳�ꂽ�T���l�C���T�C�Y���c���Ƃ��Ďg�p����B</li>
	 * </ul>
	 * <br/>
	 * @param srcFile �I���W�i���摜�� File �I�u�W�F�N�g
	 * @param destFile �T���l�C���̏o�͐� File �I�u�W�F�N�g
	 * @param size �쐬����T���l�C���̃T�C�Y
	 * 
	 * @return �쐬�����T���l�C���̃T�C�Y���
	 * @throws Exception 
	 */
	@Override
	public ImgInfo createImgFile(File srcFile, File destFile, int size)
			throws Exception {

		// �I���W�i���摜�T�C�Y���擾
		ImgInfo srcImgInfo = getImgInfo(srcFile);		

		// �T���l�C���摜�T�C�Y���擾
		ImgInfo destImgInfo = createThumImgInfo(srcImgInfo, size);


		// �I���W�i���摜�T�C�Y�ƍ쐬����T���l�C���摜�T�C�Y�������ꍇ�A�t�@�C�����R�s�[����B
		if(srcImgInfo.getWidth().equals(destImgInfo.getWidth())
				&& srcImgInfo.getHeight().equals(destImgInfo.getHeight())){

			FileUtils.copyFile(srcFile, destFile);
			log.info(srcFile + " is copy only.");
			return srcImgInfo;
		}


		// �I���W�i���摜�T�C�Y���w�肵���T���l�C���摜�T�C�Y��菬�����ꍇ�A�t�@�C�������̂܂܃R�s�[����B
		if(destImgInfo.getWidth() > srcImgInfo.getWidth() &&
				destImgInfo.getHeight() > srcImgInfo.getHeight()) {

			FileUtils.copyFile(srcFile, destFile);
			log.info(srcFile + " is copy only.");
			return srcImgInfo;
		}

		
		// ImageMagic �̋@�\���g�p���ăT���l�C���摜���쐬����B
		execImageMagicConvert(srcFile.getPath(), destFile.getPath(), destImgInfo);
		
		return destImgInfo;

	
	}

	
	
	
	/**
	 * �T���l�C���摜�̃t�@�C���T�C�Y�����擾�擾����B<br/>
	 * <br/>
	 * @param srcFile �I���W�i���摜�̃t���p�X�t�@�C����
	 * @param size �쐬����T���l�C���̃T�C�Y
	 * 
	 * @return �T���l�C���摜�̃T�C�Y���
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	protected ImgInfo createThumImgInfo(ImgInfo srcImgInfo, int size)
			throws FileNotFoundException, IOException {

		// �T���l�C���摜�T�C�Y���̃I�u�W�F�N�g���쐬����B
		ImgInfo thumImgInfo = new ImgInfo();
		thumImgInfo.setHwFlg(srcImgInfo.getHwFlg());


		// �T���l�C���摜�T�C�Y�̎Z�o
		// �c�Ɖ��̒����Œ���������Ƃ��ďk�ڗ����Z�o����B
		if (srcImgInfo.getHwFlg() == 0){
			// �����摜�̏ꍇ
			double rate = (double)size / (double)srcImgInfo.getWidth();
			thumImgInfo.setHeight((int)(srcImgInfo.getHeight() * rate));
			thumImgInfo.setWidth(size);

		} else if (srcImgInfo.getHwFlg() == 1){
			// �c���摜�̏ꍇ
			double rate = (double)size / (double)srcImgInfo.getHeight();
			thumImgInfo.setHeight(size);
			thumImgInfo.setWidth((int)(srcImgInfo.getWidth() * rate));

		} else {
			// �c�E���̒���������̏ꍇ
			thumImgInfo.setHeight(size);
			thumImgInfo.setWidth(size);
		}

		return thumImgInfo;
	}



	/**
	 * �����I�ȃT���l�C���쐬�����B<br/>
	 * ImageMagick �� convert �R�}���h���g�p���ăT���l�C���摜���쐬����B<br/>
	 * <br/>
	 * @param srcFile �I���W�i���摜�̃t���p�X�t�@�C����
	 * @param destFile �T���l�C���̏o�͐�t���p�X�t�@�C����
	 * @param destImgInfo�@�쐬����T���l�C���t�@�C���̃T�C�Y���
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	protected void execImageMagicConvert(String srcFile, String destFile, ImgInfo destImgInfo) throws IOException, InterruptedException {
		
		// ImageMagic �ϊ��R�}���h�̃p�����[�^�쐬
		List<String> cmdList = new ArrayList<>();
		cmdList.add(this.convertCommand);
		cmdList.add("-resize");
		cmdList.add(destImgInfo.getWidth() + "x" + destImgInfo.getHeight() + "!");
		cmdList.add(new File(srcFile).getAbsolutePath());
		cmdList.add(new File(destFile).getAbsolutePath());

        int ret = ExecuteShell.exec(cmdList);
        if (ret != 0){
        	throw new RuntimeException("ImageMagic convert result is " + ret);
        }

	}
}
