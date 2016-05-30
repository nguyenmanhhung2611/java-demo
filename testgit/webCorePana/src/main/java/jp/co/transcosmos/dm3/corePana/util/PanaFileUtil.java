package jp.co.transcosmos.dm3.corePana.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;

import org.apache.commons.fileupload.FileItem;
import org.springframework.util.StringUtils;

/**
 * Panasonic�p�t�@�C�������֘A����Util.
 *
 * <pre>
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 *   Trans	  2015.03.10    �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class PanaFileUtil {
	/** ���ʃp�����[�^�I�u�W�F�N�g */
	private PanaCommonParameters commonParameters;

	/**
	 * ���ʃp�����[�^�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param commonParameters
	 *            ���ʃp�����[�^�I�u�W�F�N�g
	 */
	public void setCommonParameters(PanaCommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * Temp�p�X�𕜋A����B<br/>
	 * <br/>
	 *
	 * @param rootPath
	 *            ���[�g�p�X�u�萔�l�v
	 * @return Temp�p�X�i/�u�萔�l�v/�u�N�����v/�j��Ԃ�
	 */
	public static String getUploadTempPath() {
		// �u�N�����v
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

		// �u�N�����v��Ԃ�
		return dateFormat.format(new Date());
	}

	/**
	 * �����p�X �p�����[�^�̋�؂���V�X�e����؂�ɒu�������郁�\�b�h.<br>
	 * <br>
	 *
	 * @param path
	 *            �����p�X
	 * @return �u�������������p�X
	 */
	public static String replaceToFileSeparator(String path) {
		if (StringUtils.isEmpty(path)) {
			return "";
		}

		// �p�X��؂��u��������
		return path.trim().replace("/", File.separator)
				.replace("\\", File.separator);
	}

	/**
	 * �����p�X �p�����[�^��URL�p�X�i��ʕ\���p�j�ɒu�������郁�\�b�h.<br>
	 * <br>
	 *
	 * @param path
	 *            �����p�X
	 * @return URL�p�X
	 */
	public static String replaceToURLSeparator(String path) {
		if (StringUtils.isEmpty(path)) {
			return "";
		}

		// �p�X��؂��u��������
		return path.replace("\\", "/");
	}

	/**
	 * �p�X��A�����郁�\�b�h.<br>
	 * �A����㕨���p�X�͑��݂��Ȃ��ꍇ�A�쐬����. <br>
	 *
	 * @param rootPath
	 *            �A�����p�X
	 * @param path
	 *            �A����p�X
	 * @return �A����p�X
	 */
	public static String mkPhysicalPath(String rootPath, String path) {
		return conPhysicalPath(rootPath, path, 0);
	}

	/**
	 * �p�X��A�����郁�\�b�h.<br>
	 * �A����㕨���p�X�͑��݂��Ȃ��ꍇ�A�쐬�����Ȃ�. <br>
	 *
	 * @param rootPath
	 *            �A�����p�X
	 * @param path
	 *            �A����p�X
	 * @return �A����p�X
	 */
	public static String conPhysicalPath(String rootPath, String path) {
		return conPhysicalPath(rootPath, path, 1);
	}

	/**
	 * �A�����p�X�ɘA����p�X��ǉ����郁�\�b�h.<br>
	 * <br>
	 *
	 * @param rootPath
	 *            �A�����p�X
	 * @param path
	 *            �A����p�X
	 * @param type
	 *            0: �A����p�X�͑��݂��Ȃ��ꍇ�A�쐬���� 1:
	 *            �A����p�X�͑��݂��Ȃ��ꍇ�A�쐬���Ȃ��i���i�͘A����p�X���t�@�C���̏ꍇ���p�j
	 * @return �A����p�X
	 */
	public static String conPhysicalPath(String rootPath, String path, int type) {
		// �A�����p�X��Null�̏ꍇ�A����������
		if (rootPath == null) {
			rootPath = "";
		}

		if (path == null) {
			path = "";
		}
		path = path.trim();

		rootPath = replaceToFileSeparator(rootPath);

		StringBuffer strB = new StringBuffer(rootPath);

		// �A�����p�X�̍Ōオ��؂蕶���łȂ��ꍇ�A��؂蕶����ǉ�
		if (!rootPath.endsWith(File.separator)) {
			strB.append(File.separator);
		}

		// �A����p�X�̐擪����؂蕶���ł���ꍇ�A��؂蕶�����폜
		if (path.startsWith(File.separator)) {
			path = path.substring(1, path.length());
		}

		// �A�����p�X + �A����p�X
		strB.append(path);

		// �A����p�X�͑��݂��Ȃ��ꍇ�A�쐬����
		if (type != 1) {
			File file = new File(strB.toString());

			if (!file.exists()) {
				// ���݂��Ȃ��ꍇ�A�쐬����
				file.mkdirs();
			}
		}

		return strB.toString();
	}

	/**
	 * �t�@�C�����A�b�v���[�h����p�̋��ʏ���.<br>
	 * <br>
	 *
	 * @param item
	 *            �A�b�v���[�h�Ώۃt�@�C���A�C�e��.
	 * @param uploadPath
	 *            �A�b�v���[�h�p�X. ��j/�u�萔�l�v/[�������CD]/[�s���{��CD]/[�s�撬��CD]/�V�X�e�������ԍ�/
	 * @param delFlg
	 *            �A�b�v���[�h��폜�t���O<br>
	 *            �i1:�A�b�v���[�h��ɓ����t�@�C��������ꍇ�Y���t�@�C�����폜����j
	 * @return �A�b�v���[�h�t�@�C����
	 */
	public static String uploadFile(FileItem item, String uploadPath,
			String fileName) throws Exception {
		return uploadFile(item, uploadPath, fileName, 1);
	}

	/**
	 * �t�@�C�����A�b�v���[�h����p�̋��ʏ���.<br>
	 * <br>
	 *
	 * @param item
	 *            �A�b�v���[�h�Ώۃt�@�C���A�C�e��.
	 * @param uploadPath
	 *            �A�b�v���[�h�p�X. ��j/�u�萔�l�v/[�������CD]/[�s���{��CD]/[�s�撬��CD]/�V�X�e�������ԍ�/
	 * @param delFlg
	 *            �A�b�v���[�h��폜�t���O<br>
	 *            �i1:�A�b�v���[�h��ɓ����t�@�C��������ꍇ�Y���t�@�C�����폜����j
	 * @return �A�b�v���[�h�t�@�C����
	 */
	public static String uploadFile(FileItem item, String uploadPath,
			String fileName, int delFlg) throws Exception {
		// �I�u�W�F�N�g�����݂��Ȃ��ꍇ�A�������Ȃ�.
		if (item == null) {
			return "";
		}

		// �t�@�C�����������ꍇ���������Ȃ�.
		if (StringUtils.isEmpty(item.getName())) {
			return "";
		}

		File file = new File(uploadPath);
		if (!file.exists()) {
			// �����p�X�����݂��Ȃ��ꍇ�A�쐬����
			file.mkdirs();
		}

		// �t�@�C���o��
		File uploadFile = new File(uploadPath, fileName);
		if (delFlg == 1 && uploadFile.exists()) {
			uploadFile.delete();
		}

		item.write(uploadFile);

		return uploadFile.getName();
	}

	/**
	 * �t�@�C�����폜���郁�\�b�h.<br>
	 * �T���l�C�����폜���邽�߁A���t�H���_�[�̓����t�@�C�����̃t�@�C�������ׂč폜����.<br>
	 * <br>
	 *
	 * @param path
	 *            �폜�Ώۃt�@�C���̃p�X.
	 *            ��j/�u�萔�l�v/[�������CD]/[�s���{��CD]/[�s�撬��CD]/�V�X�e�������ԍ�/�V�[�P���X(10��).jpg
	 * @return �A�b�v���[�h�t�@�C����
	 */
	public static void delPhysicalPathFile(String path, String fileName) {
		File pPath = new File(path);
		List<String> fileList = new ArrayList<String>();

		if (pPath.exists()) {
			// �t�@�C���i�[�p�X���̃t�@�C�������ׂĎ擾
			getFileList(pPath.getAbsolutePath(), pPath.list(), fileList);

			// �폜�Ώۃt�@�C���Ɠ����t�@�C�����́A���ׂč폜�i�T���l�C�����폜���邽�߁j
			for (String fName : fileList) {
				if (fName.indexOf(fileName) > 0) {
					File delFile = new File(fName);

					if (delFile.exists()) {
						delFile.delete();
					}
				}
			}
		}
	}

	/**
	 * �p�X �p�����[�^�̃t�H���_�[���̃t�@�C���������ׂĎ擾���郁�\�b�h.<br>
	 * <br>
	 *
	 * @param path
	 *            �Ώۃp�X.
	 * @param files
	 *            pash���̃t�@�C�����ƃt�H���_�[�����X�g.
	 * @param fileList
	 *            ���ʂ̃t�@�C�������X�g.
	 */
	public static void getFileList(String path, String[] files,
			List<String> fileList) {
		for (int i = 0; i < files.length; i++) {
			File readfile = new File(path + File.separator + files[i]);
			if (readfile.isDirectory()) {
				getFileList(readfile.getAbsolutePath(), readfile.list(),
						fileList);
			} else {
				fileList.add(readfile.getAbsolutePath());
			}
		}
	}

	/**
	 * �p�X �p�����[�^�ƃt�@�C���� �p�����[�^�ɂ��ATemp�t�@�C����URL�p�X���擾���郁�\�b�h.<br>
	 * ���t�H�[���֘A�t�@�C���p.<br>
	 *
	 * @param temPathName
	 *            �p�X.
	 * @param temFileName
	 *            �t�@�C����.
	 * @return URL�p�X
	 */
	public String getHousFileTempUrl(String temPathName, String temFileName) {
		// "http://fileserver:8080"
		StringBuffer path = new StringBuffer(
				this.commonParameters.getFileSiteURL());

		// "http://fileserver:8080/reform/temp/"
		addUrlParam(path, this.commonParameters.getHousImgTempUrl());

		// "http://fileserver:8080/reform/temp/yyyymmdd/"
		addUrlParam(path, temPathName);

		// "http://fileserver:8080/reform/temp/yyyymmdd/abc.jpg"
		addUrlParam(path, temFileName);

		return path.toString();
	}

	/**
	 * �p�X �p�����[�^�ƃt�@�C���� �p�����[�^�ɂ��A�S���{����URL�p�X���擾���郁�\�b�h.<br>
	 * ���t�H�[���֘A�t�@�C���p.<br>
	 *
	 * @param pathName
	 *            �p�X.
	 * @param fileName
	 *            �t�@�C����.
	 * @param size
	 *            �C���[�W�T�C�Y�̃T�u�t�H���_�[���iPDF���n����j.
	 * @return URL�p�X
	 */
	public String getHousFileOpenUrl(String pathName, String fileName,
			String size) {
		// "http://fileserver:8080"
		StringBuffer path = new StringBuffer(
				this.commonParameters.getFileSiteURL());

		// "http://fileserver:8080/reform/open/"
		addUrlParam(path, this.commonParameters.getHousImgOpenUrl());

		// "http://fileserver:8080/reform/open/123/12/12345/H1234/"
		addUrlParam(path, pathName);

		// "http://fileserver:8080/reform/open/123/12/12345/H1234/full/"
		addUrlParam(path, size);

		// "http://fileserver:8080/reform/open/123/12/12345/H1234/full/abc.jpg"
		addUrlParam(path, fileName);

		return path.toString();
	}

	/**
	 * �p�X �p�����[�^�ƃt�@�C���� �p�����[�^�ɂ��A����̂݉{����URL�p�X���擾���郁�\�b�h.<br>
	 * ���t�H�[���֘A�t�@�C���p.<br>
	 *
	 * @param pathName
	 *            �p�X.
	 * @param fileName
	 *            �t�@�C����.
	 * @param size
	 *            �C���[�W�T�C�Y�̃T�u�t�H���_�[���iPDF���n����j.
	 * @return URL�p�X
	 */
	public String getHousFileMemberUrl(String pathName, String fileName,
			String size) {
		// "http://fileserver:8080"
		StringBuffer path = new StringBuffer(
				this.commonParameters.getFileSiteURL());

		// "http://fileserver:8080/reform/open_member/"
		addUrlParam(path, this.commonParameters.getHousImgMemberUrl());

		// "http://fileserver:8080/reform/open/123/12/12345/H1234/"
		addUrlParam(path, pathName);

		// "http://fileserver:8080/reform/open/123/12/12345/H1234/full/"
		addUrlParam(path, size);

		// "http://fileserver:8080/reform/open/123/12/12345/H1234/full/abc.jpg"
		addUrlParam(path, fileName);

		return path.toString();
	}

	public static void addUrlParam(StringBuffer path, String param) {
		if (path == null) {
			return;
		}

		if (param == null || param.length() == 0) {
			return;
		}

		if (path.length() == 0) {
			if ("/".equals(param.substring(0, 1))) {
				path.append(param);
			} else {
				path.append("/").append(param);
			}

			return;
		}

		if (!"/".equals(path.substring(path.length() - 1))) {
			path.append("/");
		}

		if ("/".equals(param.substring(0, 1))) {
			path.append(param.substring(1));
		} else {
			path.append(param);
		}
	}
}
