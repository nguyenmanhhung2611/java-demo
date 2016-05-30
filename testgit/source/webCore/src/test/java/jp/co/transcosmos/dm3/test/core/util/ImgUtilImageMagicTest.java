package jp.co.transcosmos.dm3.test.core.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jp.co.transcosmos.dm3.core.util.ImgUtils;
import jp.co.transcosmos.dm3.core.util.imgUtils.ImgInfo;


/**
 * ImageMagick �ɂ��T���l�C���摜�쐬�N���X�̃e�X�g<br/>
 * <br/>
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class ImgUtilImageMagicTest {

	
	@Autowired
	ImgUtils imgUtils;

	

	@Before
	public void init() throws IOException {
		// �T���l�C���̍쐬��f�B���N�g��������
		String rootPath = System.getProperty("user.dir");
		File destDir = new File(rootPath + "/junitTest/thumDstData");
		FileUtils.deleteDirectory(destDir);
		FileUtils.forceMkdir(destDir);
	}
	
	
	

	/**
	 * �����`�摜�̃T���l�C���쐬�i�T���l�C���̕����������ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�T���l�C���摜���w�肳�ꂽ�f�B���N�g���ɍ쐬����Ă��鎖�B</li>
	 *     <li>�T���l�C���摜�̏c�T�C�Y���������l�ɏk������Ă��鎖</li>
	 *     <li>�T���l�C���摜�̉��T�C�Y���������l�ɏk������Ă��鎖</li>
	 *     <li>�T���l�C���摜�̏c���E�����t���O�� 2�i����j�ł��鎖</li>
	 * </ul>
	 */
	@Test
	public void sqSmallTest() throws Exception{
		
		String rootPath = System.getProperty("user.dir");
		System.out.print(rootPath);

		// �R�}���h���s
		this.imgUtils.createImgFile(rootPath + "/junitTest/thumSrcData/test600.jpg",
									rootPath + "/junitTest/thumDstData/test100.jpg",
									100);

		// �摜�t�@�C���̃`�F�b�N
		ImgInfo imgInfo = this.imgUtils.getImgInfo(rootPath + "/junitTest/thumDstData/test100.jpg");
		Assert.assertEquals("�c�̃T�C�Y����������", 100, (int)imgInfo.getHeight());
		Assert.assertEquals("���̃T�C�Y����������", 100, (int)imgInfo.getWidth());
		Assert.assertEquals("�c���E�����t���O�� 2 �i����j�ł��鎖", 2, (int)imgInfo.getHwFlg());

	}
	

	
	/**
	 * �����`�摜�̃T���l�C���쐬�i�T���l�C���̕����傫���ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�T���l�C���摜���w�肳�ꂽ�f�B���N�g���ɍ쐬����Ă��鎖�B</li>
	 *     <li>�T���l�C���摜�̏c�T�C�Y�����̃T�C�Y�Ɠ�����</li>
	 *     <li>�T���l�C���摜�̉��T�C�Y�����̃T�C�Y�Ɠ�����</li>
	 *     <li>�T���l�C���摜�̏c���E�����t���O�� 2�i����j�ł��鎖</li>
	 * </ul>
	 */
	@Test
	public void sqBigTest() throws Exception{
		
		String rootPath = System.getProperty("user.dir");
		System.out.print(rootPath);

		// �R�}���h���s
		this.imgUtils.createImgFile(rootPath + "/junitTest/thumSrcData/test600.jpg",
									rootPath + "/junitTest/thumDstData/test700.jpg",
									700);

		// �摜�t�@�C���̃`�F�b�N
		ImgInfo imgInfo = this.imgUtils.getImgInfo(rootPath + "/junitTest/thumDstData/test700.jpg");
		Assert.assertEquals("�c�̃T�C�Y�����̃T�C�Y�ł��鎖", 600, (int)imgInfo.getHeight());
		Assert.assertEquals("���̃T�C�Y�����̃T�C�Y�ł��鎖", 600, (int)imgInfo.getWidth());
		Assert.assertEquals("�c���E�����t���O�� 2 �i����j�ł��鎖", 2, (int)imgInfo.getHwFlg());

	}

	
	
	/**
	 * �����摜�̃T���l�C���쐬�i�T���l�C���̕����������ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�T���l�C���摜���w�肳�ꂽ�f�B���N�g���ɍ쐬����Ă��鎖�B</li>
	 *     <li>�T���l�C���摜�̏c�T�C�Y���������l�ɏk������Ă��鎖</li>
	 *     <li>�T���l�C���摜�̉��T�C�Y���������l�ɏk������Ă��鎖</li>
	 *     <li>�T���l�C���摜�̏c���E�����t���O�� 0�i�����j�ł��鎖</li>
	 * </ul>
	 */
	@Test
	public void wiSmallTest() throws Exception{
		
		String rootPath = System.getProperty("user.dir");
		System.out.print(rootPath);

		// �R�}���h���s �i�c 400�A�� 600�j
		this.imgUtils.createImgFile(rootPath + "/junitTest/thumSrcData/wtest600.jpg",
									rootPath + "/junitTest/thumDstData/wtest100.jpg",
									100);

		// �摜�t�@�C���̃`�F�b�N
		ImgInfo imgInfo = this.imgUtils.getImgInfo(rootPath + "/junitTest/thumDstData/wtest100.jpg");
		Assert.assertEquals("�c�̃T�C�Y����������", 66, (int)imgInfo.getHeight());
		Assert.assertEquals("���̃T�C�Y����������", 100, (int)imgInfo.getWidth());
		Assert.assertEquals("�c���E�����t���O�� 0 �i�����j�ł��鎖", 0, (int)imgInfo.getHwFlg());

	}

	
	
	/**
	 * �����摜�̃T���l�C���쐬�i�T���l�C���̕����傫���ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�T���l�C���摜���w�肳�ꂽ�f�B���N�g���ɍ쐬����Ă��鎖�B</li>
	 *     <li>�T���l�C���摜�̏c�T�C�Y�����̃T�C�Y�Ɠ�����</li>
	 *     <li>�T���l�C���摜�̉��T�C�Y�����̃T�C�Y�Ɠ�����</li>
	 *     <li>�T���l�C���摜�̏c���E�����t���O�� 0�i�����j�ł��鎖</li>
	 * </ul>
	 */
	@Test
	public void wiBigTest() throws Exception{
		
		String rootPath = System.getProperty("user.dir");
		System.out.print(rootPath);

		// �R�}���h���s �i�c 400�A�� 600�j
		this.imgUtils.createImgFile(rootPath + "/junitTest/thumSrcData/wtest600.jpg",
									rootPath + "/junitTest/thumDstData/wtest700.jpg",
									700);

		// �摜�t�@�C���̃`�F�b�N
		ImgInfo imgInfo = this.imgUtils.getImgInfo(rootPath + "/junitTest/thumDstData/wtest700.jpg");
		Assert.assertEquals("�c�̃T�C�Y�����̃T�C�Y�ł��鎖", 400, (int)imgInfo.getHeight());
		Assert.assertEquals("���̃T�C�Y�����̃T�C�Y�ł��鎖", 600, (int)imgInfo.getWidth());
		Assert.assertEquals("�c���E�����t���O�� 0 �i�����j�ł��鎖", 0, (int)imgInfo.getHwFlg());

	}

	
	
	/**
	 * �c���摜�̃T���l�C���쐬�i�T���l�C���̕����������ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�T���l�C���摜���w�肳�ꂽ�f�B���N�g���ɍ쐬����Ă��鎖�B</li>
	 *     <li>�T���l�C���摜�̏c�T�C�Y���������l�ɏk������Ă��鎖</li>
	 *     <li>�T���l�C���摜�̉��T�C�Y���������l�ɏk������Ă��鎖</li>
	 *     <li>�T���l�C���摜�̏c���E�����t���O�� 1�i�c���j�ł��鎖</li>
	 * </ul>
	 */
	@Test
	public void heSmallTest() throws Exception{
		
		String rootPath = System.getProperty("user.dir");
		System.out.print(rootPath);

		// �R�}���h���s �i�c 600�A�� 400�j
		this.imgUtils.createImgFile(rootPath + "/junitTest/thumSrcData/htest600.jpg",
									rootPath + "/junitTest/thumDstData/htest100.jpg",
									100);

		// �摜�t�@�C���̃`�F�b�N
		ImgInfo imgInfo = this.imgUtils.getImgInfo(rootPath + "/junitTest/thumDstData/htest100.jpg");
		Assert.assertEquals("�c�̃T�C�Y����������", 100, (int)imgInfo.getHeight());
		Assert.assertEquals("���̃T�C�Y����������", 66, (int)imgInfo.getWidth());
		Assert.assertEquals("�c���E�����t���O�� 1 �i�c���j�ł��鎖", 1, (int)imgInfo.getHwFlg());

	}

	
	
	/**
	 * �c���摜�̃T���l�C���쐬�i�T���l�C���̕����傫���ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�T���l�C���摜���w�肳�ꂽ�f�B���N�g���ɍ쐬����Ă��鎖�B</li>
	 *     <li>�T���l�C���摜�̏c�T�C�Y�����̃T�C�Y�Ɠ�����</li>
	 *     <li>�T���l�C���摜�̉��T�C�Y�����̃T�C�Y�Ɠ�����</li>
	 *     <li>�T���l�C���摜�̏c���E�����t���O�� 1�i�c���j�ł��鎖</li>
	 * </ul>
	 */
	@Test
	public void heBigTest() throws Exception{
		
		String rootPath = System.getProperty("user.dir");
		System.out.print(rootPath);

		// �R�}���h���s �i�c 600�A�� 400�j
		this.imgUtils.createImgFile(rootPath + "/junitTest/thumSrcData/htest600.jpg",
									rootPath + "/junitTest/thumDstData/htest700.jpg",
									700);

		// �摜�t�@�C���̃`�F�b�N
		ImgInfo imgInfo = this.imgUtils.getImgInfo(rootPath + "/junitTest/thumDstData/htest700.jpg");
		Assert.assertEquals("�c�̃T�C�Y�����̃T�C�Y�ł��鎖", 600, (int)imgInfo.getHeight());
		Assert.assertEquals("���̃T�C�Y�����̃T�C�Y�ł��鎖", 400, (int)imgInfo.getWidth());
		Assert.assertEquals("�c���E�����t���O�� 1 �i�c���j�ł��鎖", 1, (int)imgInfo.getHwFlg());

	}

	
	
	/**
	 * �����摜�̃T���l�C���쐬�i�����T���l�C���T�C�Y�ƈ�v����ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�T���l�C���摜���w�肳�ꂽ�f�B���N�g���ɍ쐬����Ă��鎖�B</li>
	 *     <li>�T���l�C���摜�̏c�T�C�Y�����̃T�C�Y�Ɠ�����</li>
	 *     <li>�T���l�C���摜�̉��T�C�Y�����̃T�C�Y�Ɠ�����</li>
	 *     <li>�T���l�C���摜�̏c���E�����t���O�� 0�i�����j�ł��鎖</li>
	 * </ul>
	 */
	@Test
	public void wiEqTest() throws Exception{
		
		String rootPath = System.getProperty("user.dir");
		System.out.print(rootPath);

		// �R�}���h���s �i�c 400�A�� 600�j
		this.imgUtils.createImgFile(rootPath + "/junitTest/thumSrcData/wtest600.jpg",
									rootPath + "/junitTest/thumDstData/wtest700.jpg",
									600);

		// �摜�t�@�C���̃`�F�b�N
		ImgInfo imgInfo = this.imgUtils.getImgInfo(rootPath + "/junitTest/thumDstData/wtest700.jpg");
		Assert.assertEquals("�c�̃T�C�Y�����̃T�C�Y�ł��鎖", 400, (int)imgInfo.getHeight());
		Assert.assertEquals("���̃T�C�Y�����̃T�C�Y�ł��鎖", 600, (int)imgInfo.getWidth());
		Assert.assertEquals("�c���E�����t���O�� 0 �i�����j�ł��鎖", 0, (int)imgInfo.getHwFlg());

	}

	
	/**
	 * �c���摜�̃T���l�C���쐬�i�������T���l�C���T�C�Y�ƈ�v����ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�T���l�C���摜���w�肳�ꂽ�f�B���N�g���ɍ쐬����Ă��鎖�B</li>
	 *     <li>�T���l�C���摜�̏c�T�C�Y�����̃T�C�Y�Ɠ�����</li>
	 *     <li>�T���l�C���摜�̉��T�C�Y�����̃T�C�Y�Ɠ�����</li>
	 *     <li>�T���l�C���摜�̏c���E�����t���O�� 1�i�c���j�ł��鎖</li>
	 * </ul>
	 */
	@Test
	public void heEqTest() throws Exception{
		
		String rootPath = System.getProperty("user.dir");
		System.out.print(rootPath);

		// �R�}���h���s �i�c 600�A�� 400�j
		this.imgUtils.createImgFile(rootPath + "/junitTest/thumSrcData/htest600.jpg",
									rootPath + "/junitTest/thumDstData/htest700.jpg",
									600);

		// �摜�t�@�C���̃`�F�b�N
		ImgInfo imgInfo = this.imgUtils.getImgInfo(rootPath + "/junitTest/thumDstData/htest700.jpg");
		Assert.assertEquals("�c�̃T�C�Y�����̃T�C�Y�ł��鎖", 600, (int)imgInfo.getHeight());
		Assert.assertEquals("���̃T�C�Y�����̃T�C�Y�ł��鎖", 400, (int)imgInfo.getWidth());
		Assert.assertEquals("�c���E�����t���O�� 1 �i�c���j�ł��鎖", 1, (int)imgInfo.getHwFlg());

	}

}
