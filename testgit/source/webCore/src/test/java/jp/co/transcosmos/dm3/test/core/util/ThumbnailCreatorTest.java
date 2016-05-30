package jp.co.transcosmos.dm3.test.core.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.util.ImgUtils;
import jp.co.transcosmos.dm3.core.util.ThumbnailCreator;
import jp.co.transcosmos.dm3.core.util.imgUtils.ImgInfo;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



/**
 * VO �C���X�^���X�����N���X�̃e�X�g�P�[�X<br/>
 * <br/>
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class ThumbnailCreatorTest {

	@Autowired
	private ThumbnailCreator thumbnailCreator;
	@Autowired
	private CommonParameters commonParameters;
	@Autowired
	private ImgUtils imgUtils;



	@Before
	public void init() throws IOException {
		// �e�X�g�t�H���_�̃��Z�b�g
		File srcDir = new File(this.commonParameters.getHousImgTempPhysicalPath());
		File destDir = new File(this.commonParameters.getHousImgOpenPhysicalPath());

		FileUtils.cleanDirectory(srcDir);
		FileUtils.cleanDirectory(destDir);

		// temp �t�H���_�ɓ��t�t�H���_���쐬
		FileUtils.forceMkdir(new File(this.commonParameters.getHousImgTempPhysicalPath() + "20150101"));

	}



	/**
	 * �摜�t�@�C�����̎擾�e�X�g<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�[���l���ꂽ�P�O���̐��������񂪕��A����鎖�B</li>
	 *     <li>�Q����s���āA�O��ƈႤ�l���擾�ł��鎖�B</li>
	 * </ul>
	 */
	@Test
	public void getFIleNameTest() throws Exception{
	
		String str = this.thumbnailCreator.getFIleName();
		System.out.println(str);

		Assert.assertEquals("10���̕�����ł��鎖", 10, str.length());
		
		// ���l�ϊ��\�Ȏ����`�F�b�N
		Long.valueOf(str);

		
		String str2 = this.thumbnailCreator.getFIleName();
		Assert.assertNotEquals("�P��ڂƂQ��ڂŒl���قȂ鎖", str, str2);

	}



	/**
	 * �T���l�C���摜�̍쐬�e�X�g<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>CommonsParameters �ɐݒ肳��Ă���T���l�C���T�C�Y���A�t�@�C�����쐬����Ă��鎖�B</li>
	 *     <li>�쐬���ꂽ�T���l�C���T�C�Y����������</li>
	 *     <li>�I���W�i���摜�����̃T�C�Y�̂܂܃R�s�[����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void createTest() throws Exception{

		// ���t�H���_���Ƀe�X�g�f�[�^��z�u����B
		String root = (String) System.getProperties().get("user.dir");
		String testDataDir = root + "/junitTest/upload/testData/";

		FileUtils.copyDirectory(new File(testDataDir), new File(this.commonParameters.getHousImgTempPhysicalPath() + "20150101"));
		
		
		String tempRoot = this.commonParameters.getHousImgTempPhysicalPath() + "20150101/";
		String openRoot = this.commonParameters.getHousImgOpenPhysicalPath() + "test/";

		// �e�X�g�p MAP �̍쐬
		Map<String, String> thumbnailMap = new HashMap<>();
		thumbnailMap.put(tempRoot + "0000000001.jpg", openRoot);
		thumbnailMap.put(tempRoot + "0000000002.jpg", openRoot);
		
		
		// �e�X�g���\�b�h���s
		this.thumbnailCreator.create(thumbnailMap);
		
		
		// ���s���ʂ̊m�F�@�i�c���摜�̏ꍇ�͏c�̃T�C�Y�A�����摜�̏ꍇ�͉��̃T�C�Y�Ń`�F�b�N
		ImgInfo info = this.imgUtils.getImgInfo(openRoot + "85/0000000001.jpg");
		Assert.assertEquals("�摜�T�C�Y����������", Integer.valueOf(85), info.getHeight());
		
		info = this.imgUtils.getImgInfo(openRoot + "85/0000000002.jpg");
		Assert.assertEquals("�摜�T�C�Y����������", Integer.valueOf(85), info.getWidth());

		info = this.imgUtils.getImgInfo(openRoot + "115/0000000001.jpg");
		Assert.assertEquals("�摜�T�C�Y����������", Integer.valueOf(115), info.getHeight());
		
		info = this.imgUtils.getImgInfo(openRoot + "115/0000000002.jpg");
		Assert.assertEquals("�摜�T�C�Y����������", Integer.valueOf(115), info.getWidth());

		info = this.imgUtils.getImgInfo(openRoot + "300/0000000001.jpg");
		Assert.assertEquals("�摜�T�C�Y����������", Integer.valueOf(300), info.getHeight());
		
		info = this.imgUtils.getImgInfo(openRoot + "300/0000000002.jpg");
		Assert.assertEquals("�摜�T�C�Y����������", Integer.valueOf(300), info.getWidth());

		info = this.imgUtils.getImgInfo(openRoot + "500/0000000001.jpg");
		Assert.assertEquals("�摜�T�C�Y����������", Integer.valueOf(500), info.getHeight());
		
		info = this.imgUtils.getImgInfo(openRoot + "500/0000000002.jpg");
		Assert.assertEquals("�摜�T�C�Y����������", Integer.valueOf(500), info.getWidth());

		
		ImgInfo orgInfo = this.imgUtils.getImgInfo(tempRoot + "0000000001.jpg");
		info = this.imgUtils.getImgInfo(openRoot + "full/0000000001.jpg");
		Assert.assertEquals("�摜�T�C�Y����������", orgInfo.getHeight(), info.getHeight());
		Assert.assertEquals("�摜�T�C�Y����������", orgInfo.getWidth(), info.getWidth());

		orgInfo = this.imgUtils.getImgInfo(tempRoot + "0000000002.jpg");
		info = this.imgUtils.getImgInfo(openRoot + "full/0000000002.jpg");
		Assert.assertEquals("�摜�T�C�Y����������", orgInfo.getHeight(), info.getHeight());
		Assert.assertEquals("�摜�T�C�Y����������", orgInfo.getWidth(), info.getWidth());

	}
	

	
	/**
	 * �t�H���_�P�ʂ̉摜�t�@�C���폜�e�X�g<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�폜�Ώۃt�H���_�����݂��Ȃ���</li>
	 *     <li>�폜�ΏۊO�t�H���_�����݂��鎖</li>
	 * </ul>
	 * @throws IOException 
	 */
	@Test
	public void deleteImageDirTest() throws IOException{

		// ���J�t�H���_���Ƀe�X�g�f�[�^��z�u����B
		String root = (String) System.getProperties().get("user.dir");
		String testDataDir = root + "/junitTest/upload/testDataDelDir/";

		FileUtils.copyDirectory(new File(testDataDir), new File(this.commonParameters.getHousImgOpenPhysicalPath()));
		
		
		// �e�X�g�p�����[�^�쐬
		Set<String> dir = new HashSet<>();
		dir.add("111/12/11111/HOUS0001/");
		dir.add("112/12/11111/HOUS0001/");

		List<String> rootPathList = new ArrayList<>(); 
		rootPathList.add(this.commonParameters.getHousImgOpenPhysicalPath());
		rootPathList.add(this.commonParameters.getHousImgOpenPhysicalPath()+"2/");		// ���݂��Ȃ��_�~�[�t�H���_
		this.thumbnailCreator.deleteImageDir(rootPathList, dir);


		// ���ʊm�F
		
		// �폜�Ώۃt�H���_��������Ă��鎖
		try {
			FileUtils.listFiles(new File(this.commonParameters.getHousImgOpenPhysicalPath() + "111/12/11111/HOUS0001"), null, true);
			Assert.fail("�폜�Ώۃt�H���_�����݂��Ȃ���");
		} catch (IllegalArgumentException e) { }

		try {
			FileUtils.listFiles(new File(this.commonParameters.getHousImgOpenPhysicalPath() + "112/12/11111/HOUS0001"), null, true);
			Assert.fail("�폜�Ώۃt�H���_�����݂��Ȃ���");
		} catch (IllegalArgumentException e) { }


		// �폜�ΏۊO�t�H���_�����݂��鎖
		Collection<File> lst = FileUtils.listFiles(new File(this.commonParameters.getHousImgOpenPhysicalPath() + "111/12/11111/HOUS0002"), null, true);
		Assert.assertNotEquals("�폜�ΏۊO�t�H���_��������Ă��Ȃ���", 0, lst.size());
		
		lst = FileUtils.listFiles(new File(this.commonParameters.getHousImgOpenPhysicalPath() + "111/12/11111/HOUS0003"), null, true);
		Assert.assertNotEquals("�폜�ΏۊO�t�H���_��������Ă��Ȃ���", 0, lst.size());

		lst = FileUtils.listFiles(new File(this.commonParameters.getHousImgOpenPhysicalPath() + "112/12/11111/HOUS0002"), null, true);
		Assert.assertNotEquals("�폜�ΏۊO�t�H���_��������Ă��Ȃ���", 0, lst.size());

		lst = FileUtils.listFiles(new File(this.commonParameters.getHousImgOpenPhysicalPath() + "112/12/11111/HOUS0003"), null, true);
		Assert.assertNotEquals("�폜�ΏۊO�t�H���_��������Ă��Ȃ���", 0, lst.size());

	}



	/**
	 * �t�H���_�P�ʂ̉摜�t�@�C���폜�e�X�g�i�����t�@�C���A�t�H���_�����݂��Ȃ��ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>��O���������Ȃ���</li>
	 * </ul>
	 * @throws IOException 
	 */
	@Test
	public void deleteImageNotDirTest() throws IOException{

		// ���J�t�H���_���ɉ����z�u�����Ɏ��s����
		
		// �e�X�g�p�����[�^�쐬
		Set<String> dir = new HashSet<>();
		dir.add("111/12/11111/HOUS0001/");
		dir.add("112/12/11111/HOUS0001/");

		List<String> rootPathList = new ArrayList<>();
		rootPathList.add(this.commonParameters.getHousImgOpenPhysicalPath());
		rootPathList.add(this.commonParameters.getHousImgOpenPhysicalPath() +"2/");		// ���݂��Ȃ��_�~�[�t�H���_
		this.thumbnailCreator.deleteImageDir(rootPathList, dir);
		
	}


	
	/**
	 * �t�H���_�P�ʂ̉摜�t�@�C���폜�̈��S���u�����m�F<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�ʃp�X�t�H���_����̏ꍇ�A���[�g�t�H���_���폜����Ȃ������m�F</li>
	 * </ul>
	 * @throws IOException 
	 */
	@Test
	public void deleteImageSaftyTest() throws IOException{

		// ���J�t�H���_���Ƀe�X�g�f�[�^��z�u����B
		String root = (String) System.getProperties().get("user.dir");
		String testDataDir = root + "/junitTest/upload/testDataDelDir/";

		FileUtils.copyDirectory(new File(testDataDir), new File(this.commonParameters.getHousImgOpenPhysicalPath()));


		// �e�X�g�p�����[�^�쐬
		// �^�[�Q�b�g�p�X���󕶎���ɐݒ肵�ARootPathList �̃t�H���_���폜����Ȃ������m�F�B
		List<String> rootPathList = new ArrayList<>();
		String wk = this.commonParameters.getHousImgOpenPhysicalPath();
		if (!wk.endsWith("/")) wk += "/";
		rootPathList.add( wk + "111/12/11111/HOUS0001/");

		Set<String> dir = new HashSet<>();
		dir.add("");

		
		// �e�X�g�Ώێ��s
		this.thumbnailCreator.deleteImageDir(rootPathList, dir);

		
		// ���ʊm�F
		Assert.assertTrue("���[�g�t�H���_���폜����Ă��Ȃ���", (new File(wk + "111/12/11111/HOUS0001/")).exists());
	}

	

	/**
	 * �t�@�C���P�ʂ̉摜�t�@�C���폜�e�X�g<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�w�肳�ꂽ�摜�̑S�T���l�C���t�@�C�����폜����Ă��鎖</li>
	 *     <li>�w�肳��Ă��Ȃ��摜�t�@�C�����폜����Ă��Ȃ���</li>
	 * </ul>
	 * @throws IOException 
	 */
	@Test
	public void deleteImageFileTest() throws IOException{

		// ���J�t�H���_���Ƀe�X�g�f�[�^��z�u����B
		String root = (String) System.getProperties().get("user.dir");
		String testDataDir = root + "/junitTest/upload/testDataDelDir/";

		FileUtils.copyDirectory(new File(testDataDir), new File(this.commonParameters.getHousImgOpenPhysicalPath()));


		// �e�X�g�p�����[�^�쐬
		String path = this.commonParameters.getHousImgOpenPhysicalPath() + "111/12/11111/HOUS0001/";

		this.thumbnailCreator.deleteImgFile(path, "0000000001.jpg");


		// ���s���ʊm�F
		Assert.assertFalse("�폜�Ώۃt�@�C�������݂��Ȃ���", (new File (path + "85/0000000001.jpg")).exists());
		Assert.assertFalse("�폜�Ώۃt�@�C�������݂��Ȃ���", (new File (path + "115/0000000001.jpg")).exists());
		Assert.assertFalse("�폜�Ώۃt�@�C�������݂��Ȃ���", (new File (path + "300/0000000001.jpg")).exists());
		Assert.assertFalse("�폜�Ώۃt�@�C�������݂��Ȃ���", (new File (path + "500/0000000001.jpg")).exists());
		
		Assert.assertTrue("�폜�ΏۊO�t�@�C�������݂��鎖", (new File (path + "85/0000000002.jpg")).exists());
		Assert.assertTrue("�폜�ΏۊO�t�@�C�������݂��鎖", (new File (path + "115/0000000002.jpg")).exists());
		Assert.assertTrue("�폜�ΏۊO�t�@�C�������݂��鎖", (new File (path + "300/0000000002.jpg")).exists());
		Assert.assertTrue("�폜�ΏۊO�t�@�C�������݂��鎖", (new File (path + "500/0000000002.jpg")).exists());

	}


	
	/**
	 * �t�@�C���P�ʂ̉摜�t�@�C���폜�e�X�g�i�폜��A�t�H���_������ƂȂ�A��t�H���_�폜�@�\�������ȏꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>��t�H���_���폜����Ă��Ȃ���</li>
	 * </ul>
	 * @throws IOException 
	 */
	@Test
	public void deleteImageFileEmptyTest1() throws IOException{

		// ���J�t�H���_���Ƀe�X�g�f�[�^��z�u����B
		String root = (String) System.getProperties().get("user.dir");
		String testDataDir = root + "/junitTest/upload/testDataDelDir/";

		FileUtils.copyDirectory(new File(testDataDir), new File(this.commonParameters.getHousImgOpenPhysicalPath()));


		// �e�X�g�p�����[�^�쐬
		String path = this.commonParameters.getHousImgOpenPhysicalPath() + "111/12/11111/HOUS0001/";

		this.thumbnailCreator.deleteImgFile(path, "0000000001.jpg");


		// ���s���ʊm�F
		Assert.assertFalse("�폜�Ώۃt�@�C�������݂��Ȃ���", (new File (path + "85//0000000001.jpg")).exists());
		Assert.assertFalse("�폜�Ώۃt�@�C�������݂��Ȃ���", (new File (path + "115/0000000001.jpg")).exists());
		Assert.assertFalse("�폜�Ώۃt�@�C�������݂��Ȃ���", (new File (path + "300/0000000001.jpg")).exists());
		Assert.assertFalse("�폜�Ώۃt�@�C�������݂��Ȃ���", (new File (path + "500/0000000001.jpg")).exists());
		
		Assert.assertTrue("�폜�ΏۊO�t�@�C�������݂��鎖", (new File (path + "85/0000000002.jpg")).exists());
		Assert.assertTrue("�폜�ΏۊO�t�@�C�������݂��鎖", (new File (path + "115/0000000002.jpg")).exists());
		Assert.assertTrue("�폜�ΏۊO�t�@�C�������݂��鎖", (new File (path + "300/0000000002.jpg")).exists());
		Assert.assertTrue("�폜�ΏۊO�t�@�C�������݂��鎖", (new File (path + "500/0000000002.jpg")).exists());

		
		// �Q�ڂ̃t�H���_���폜
		this.thumbnailCreator.deleteImgFile(path, "0000000002.jpg");
		
		// �T�u�t�H���_�����݂��鎖
		Assert.assertTrue("�T�u�t�H���_�����݂��鎖", (new File(path)).exists());

	}


	
	/**
	 * �t�@�C���P�ʂ̉摜�t�@�C���폜�e�X�g�i�폜��A�t�H���_������ƂȂ�A��t�H���_�폜�@�\���L���ȏꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>��t�H���_���폜����Ă��鎖</li>
	 * </ul>
	 * @throws IOException 
	 */
	@Test
	public void deleteImageFileEmptyTest2() throws IOException{

		// ���J�t�H���_���Ƀe�X�g�f�[�^��z�u����B
		String root = (String) System.getProperties().get("user.dir");
		String testDataDir = root + "/junitTest/upload/testDataDelDir/";

		FileUtils.copyDirectory(new File(testDataDir), new File(this.commonParameters.getHousImgOpenPhysicalPath()));


		// �e�X�g�p�����[�^�쐬
		String path = this.commonParameters.getHousImgOpenPhysicalPath() + "111/12/11111/HOUS0001/";

		this.thumbnailCreator.setUseEmptyDirDelete(true);
		this.thumbnailCreator.deleteImgFile(path, "0000000001.jpg");


		// ���s���ʊm�F
		Assert.assertFalse("�폜�Ώۃt�@�C�������݂��Ȃ���", (new File (path + "85//0000000001.jpg")).exists());
		Assert.assertFalse("�폜�Ώۃt�@�C�������݂��Ȃ���", (new File (path + "115/0000000001.jpg")).exists());
		Assert.assertFalse("�폜�Ώۃt�@�C�������݂��Ȃ���", (new File (path + "300/0000000001.jpg")).exists());
		Assert.assertFalse("�폜�Ώۃt�@�C�������݂��Ȃ���", (new File (path + "500/0000000001.jpg")).exists());
		
		Assert.assertTrue("�폜�ΏۊO�t�@�C�������݂��鎖", (new File (path + "85/0000000002.jpg")).exists());
		Assert.assertTrue("�폜�ΏۊO�t�@�C�������݂��鎖", (new File (path + "115/0000000002.jpg")).exists());
		Assert.assertTrue("�폜�ΏۊO�t�@�C�������݂��鎖", (new File (path + "300/0000000002.jpg")).exists());
		Assert.assertTrue("�폜�ΏۊO�t�@�C�������݂��鎖", (new File (path + "500/0000000002.jpg")).exists());

		
		// �Q�ڂ̃t�H���_���폜
		this.thumbnailCreator.deleteImgFile(path, "0000000002.jpg");
		
		// �T�u�t�H���_�����݂��鎖
		Assert.assertFalse("�T�u�t�H���_�����݂��Ȃ���", (new File(path)).exists());

	}

	
	
	/**
	 * �t�@�C���P�ʂ̉摜�t�@�C���폜�e�X�g<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�����t�@�C�������݂��Ȃ��ꍇ�A��O���������Ȃ���</li>
	 *     <li>�����p�X�����݂��Ȃ��ꍇ�A��O���������Ȃ���</li>
	 * </ul>
	 * @throws IOException 
	 */
	@Test
	public void deleteImageNonFileTest() throws IOException{

		// ���J�t�H���_���Ƀe�X�g�f�[�^��z�u����B
		String root = (String) System.getProperties().get("user.dir");
		String testDataDir = root + "/junitTest/upload/testDataDelDir/";

		FileUtils.copyDirectory(new File(testDataDir), new File(this.commonParameters.getHousImgOpenPhysicalPath()));


		// ���݂��Ȃ��t�@�C�����Ŏ��s
		String path = this.commonParameters.getHousImgOpenPhysicalPath() + "111/12/11111/HOUS0001/";
		this.thumbnailCreator.deleteImgFile(path, "1000000005.jpg");

		
		// ���݂��Ȃ��p�X���Ŏ��s
		path = this.commonParameters.getHousImgOpenPhysicalPath() + "511/12/11111/HOUS0001/";
		this.thumbnailCreator.deleteImgFile(path, "0000000001.jpg");
		
	}

}
