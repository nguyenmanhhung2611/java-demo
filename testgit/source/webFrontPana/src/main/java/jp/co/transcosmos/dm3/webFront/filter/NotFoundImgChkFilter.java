package jp.co.transcosmos.dm3.webFront.filter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.servlet.BaseFilter;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;


/**
 * �����摜�̑��݃`�F�b�N�t�B���^�[.
 * rsync �ɂ�铯���^�C�����O�ɂ��A�����摜�t�@�C�������݂��Ȃ��ꍇ�� No Image �摜�𕜋A����B<br/>
 * ���� Filter ���^�[�Q�b�g�Ƃ���̂́ATomcat �Ǘ��ƂȂ�������t�@�C���ƂȂ�B<br/>
 * ��ʌ��J�����摜�t�@�C���̏ꍇ�� Apache ���̊��ݒ�őΉ����鎖�B<br/>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.05.14	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class NotFoundImgChkFilter extends BaseFilter {

	private static final Log log = LogFactory.getLog(NotFoundImgChkFilter.class);

	/** �A�v���P�[�V�����̃R���e�L�X�g�p�X */
	private String contextPath = "";

	/**
	 * �`�F�b�N�ΏۂƂ���g���q<br/>
	 *�@���ݒ�̏ꍇ�A.jpeg�A.jpg ���ݒ肳���B<br/>
	 */
	private Set<String> targetExtensions = new HashSet<>();
	
	/**
	 * No Image �摜�t�@�C�������i�[���� Map �I�u�W�F�N�g<br/>
	 * �Y������t�@�C������������Ȃ��ꍇ�A���̂܂܃��N�G�X�g��� Chain ���� 404 �𕜋A����B<br/>
	 * Key = �摜�T�C�Y�A Value = �摜�t�@�C����<br/>
	 */
	private Map<String, String> noImageFileNames = new HashMap<>();

	/** ���ʃp�����[�^�I�u�W�F�N�g */
	private PanaCommonParameters commonParameters;



	/**
	 * Filter �̏���������<br/>
	 * web.xml ����p�����[�^���ݒ肳�ꂽ�ꍇ�A���̒l�� Filter ������������B<br/>
	 * <br/>
	 * @param config �p�����[�^�I�u�W�F�N�g
	 * @exception ServletException
	 * 
	 */
    @Override
	public void init(FilterConfig config) throws ServletException {

    	// Context �p�X���擾
    	this.contextPath = config.getServletContext().getContextPath();


    	// �������p�����[�^����`�F�b�N�ΏۂƂ���g���q���擾����B
    	String extensions = config.getInitParameter("targetExtensions");
    	if (!StringValidateUtil.isEmpty(extensions)){
    		String[] extensionList = extensions.split(",");

    		for (String extension : extensionList){
        		this.targetExtensions.add(extension.toLowerCase());
    			log.info("setting targetExtensions:" + extension);
    		}
    	} else {
    		// ���ݒ�̏ꍇ�A���L�̊g���q���f�t�H���g�ݒ肷��B
    		this.targetExtensions.add(".jpeg");
    		this.targetExtensions.add(".jpg");
    	}


    	// �������p�����[�^���� No Image �摜�t�@�C�����̏����擾����B
    	// �摜�t�@�C�����́A�ȉ��̏����ŋL�q����A�J���}��؂�ŕ����L�q���鎖���ł���B
    	// �u�摜�T�C�Y�v:�u�t�@�C�����v
    	String fileNames = config.getInitParameter("noImageFileNames");
    	if (!StringValidateUtil.isEmpty(fileNames)){
    		String[] fileNameList = fileNames.split(",");

    		for (String fileName : fileNameList){
        		String[] fileNamePats = fileName.split(":");
        		this.noImageFileNames.put(fileNamePats[0], fileNamePats[1]);
    			log.info("setting noImageFileNames:" + fileNamePats[0] + "=" + fileNamePats[1]);
    		}
    	}


		// Spring �R���e�L�X�g���狤�ʃp�����[�^�I�u�W�F�N�g���擾
		try {
			WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
			this.commonParameters = (PanaCommonParameters)springContext.getBean("commonParameters");
		} catch (NoSuchBeanDefinitionException e){
			log.warn("CommonParameters bean not found !!!");
		}
    	
    }



    /**
     * Filter �̎����I�ȏ���<br/>
     * ���N�G�X�g���ꂽ�摜�t�@�C�������݂��Ȃ��ꍇ�ANo Image �摜�𕜋A����B<br/>
     * ���A����摜�t�@�C���̃T�C�Y�� URL ����擾����B<br/>
     * <br/>
     * @param request HTTP ���N�G�X�g
     * @param response HTTP ���X�|���X
     * @param chain Chain �I�u�W�F�N�g
     * 
     * @exception IOException
     * @exception ServletException
     */
	@Override
	protected void filterAction(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException,	ServletException {

		// ���N�G�X�g���ꂽ URL ���畨���p�X���擾����B
		String targetFile = getRealPath(request);

		log.debug("call NotFoundImgChkFilter physical path =" + targetFile);

		// ���N�G�X�g��̊g���q���`�F�b�N����B
		// �����`�F�b�N�ΏۊO�̃��N�G�X�g�̏ꍇ�A���̂܂� Chain ������s����B
		if (!isTargetExtensions(targetFile)){
			chain.doFilter(request, response);
			return;
		}
		

		// ���̏����� Chain ����O�Ƀt�@�C�������݂���̂����`�F�b�N����B
		File file = new File(targetFile);
		if (!file.exists()){

			log.warn(request.getRequestURI() + " is not found.");

			// �t�@�C�����̒��O�̃t�H���_���t�@�C���T�C�Y�Ȃ̂ŁA��������t�@�C���T�C�Y�ɊY������
			// NoImage �摜�t�@�C�������擾����B
			// �����擾�ł��Ȃ��ꍇ�͒ʏ�� 404 �Ƃ��ď�������̂ł��̂܂� Chain ��֗U������B
			String noImgFileName = this.noImageFileNames.get(getImgSize(targetFile));
			if (StringValidateUtil.isEmpty(noImgFileName)){
				chain.doFilter(request, response);
				return;
			}


			// �摜�t�@�C���Ǎ��p�A���X�|���X�����p�� Stream ��p�ӂ���B
			String fileName = this.commonParameters.getHousImgOpenPhysicalPath() + "nophoto/" + noImgFileName;
			try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileName));
				 ServletOutputStream out = response.getOutputStream();){

				// �ǂݍ��񂾃f�[�^�����X�|���X�֏�����
				int data;
				int fileSize = 0;
				while ((data = in.read()) != -1) {
					out.write(data);
					++fileSize;
				}

				// ���X�|���X�w�b�_��ݒ肷��B
				response.addHeader("Content-Type", "image/jpeg");
				response.addHeader("Content-Length", String.valueOf(fileSize));
				// �ꉞ�A404 �̃X�^�[�^�X�R�[�h��ݒ肵�Ă������A���ۂɂ� 200 �����A����Ă��܂�...�B
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			}
			return;
		}

		// �t�@�C�������݂���ꍇ�́A���̂܂ܐ�̏����� Chain ����B
		chain.doFilter(request, response);

	}



	/**
	 * ���N�G�X�g���ꂽ URL ���畨���p�X�ɕϊ�����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return�@�����t�@�C�����i�t���p�X�j
	 */
	protected String getRealPath(HttpServletRequest request) {

		// ���N�G�X�g URL ���擾
		String targetURL = request.getRequestURI();

		// �摜�t�@�C���� ROOT URL ���擾
		String rootURL = this.contextPath + this.commonParameters.getHousImgMemberUrl();


		// �摜 URL �� root ��������菜��
		if (targetURL.startsWith(rootURL)){
			targetURL = targetURL.substring(rootURL.length());
		}

		// �����p�X�𕜋A
		return this.commonParameters.getHousImgOpenPhysicalMemberPath() + targetURL;

	}
	
	
	
	/**
	 * �`�F�b�N�ΏۂƂȂ�g���q�����`�F�b�N����B<br/>
	 * �`�F�b�N���A�啶���E�������͖������ă`�F�b�N����B<br/>
	 * <br/>
	 * @param fileName �����t�@�C�����i�t���p�X�j
	 * @return �`�F�b�N�Ώۊg���q�̏ꍇ�Atrue �𕜋A����B
	 */
	protected boolean isTargetExtensions(String fileName) {

		// �t�@�C�����̌�납��s���I�h���擾����B
		// ����������Ȃ��ꍇ�� false �i�`�F�b�N�ΏۊO�j�𕜋A�B
		int point = fileName.lastIndexOf(".");
		if (point < 0) return false;

		// �������ɕϊ������g���q���擾����B
		// �擾�����g���q���^�[�Q�b�g�Ƃ��Đݒ肳��Ă���ꍇ�� true �𕜋A����B
		String extension = fileName.substring(point).toLowerCase();
		if (this.targetExtensions.contains(extension)) return true;

		return false;
	}
	
	
	
	/**
	 * No Image �摜�̃T�C�Y���t�@�C���p�X����擾����B<br/>
	 * �t�@�C�����̈�K�w�オ�摜�T�C�Y�Ȃ̂ŁA���̒l���擾����B�@�����P�K�w�オ���݂��Ȃ��ꍇ�͋󕶎���𕜋A����B
	 * @param fileName �����t�@�C�����i�t���p�X�j
	 * @return �摜�T�C�Y�i��K�w��̃t�H���_���j
	 */
	protected String getImgSize(String fileName){

		// �X���b�V���Ńt�@�C�����i�t���p�X�j�𕪊�
		String parts[] = fileName.split("\\/");

		// �t�@�C�����̂P�K�w�����t�@�C���T�C�Y�Ƃ݂Ȃ����A�K�w���̂����݂��Ȃ��ꍇ�͋󕶎���𕜋A����B
		if (parts.length-2 < 0) return "";

		return parts[parts.length-2];
	}
}
