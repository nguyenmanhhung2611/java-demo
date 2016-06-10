package jp.co.transcosmos.dm3.core.model.csvMaster.form;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.form.FormPopulator;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * CSV �t�@�C���A�b�v���[�h�p Form �� Factory �N���X.
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.05	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * Factory �̃C���X�^���X�𒼐ڐ������Ȃ����B�@�K��getInstance() �Ŏ擾���鎖�B
 * 
 */
public class CsvUploadFormFactory {

	/** Form �𐶐����� Factory �� Bean ID */
	protected static String FACTORY_BEAN_ID = "csvUploadFormFactory";

	/** �����O�X�o���f�[�V�����p���[�e�B���e�B */
    protected LengthValidationUtils lengthUtils;

    /** ���ʃp�����[�^�I�u�W�F�N�g */
    protected CommonParameters commonParameters;
    
    /**
     * �A�b�v���[�h�t�@�C���̍ő�T�C�Y�@�i�f�t�H���g 4M�j<br/>
     * ���̃T�C�Y�𒴂����ꍇ�A��ƃt�H���_�ւ̏������݂���������B<br/>
     */
    protected int maxFileSize = 4 * 1024 * 1024;
    
    
    
	/**
	 * �����O�X�o���f�[�V�����p���[�e�B���e�B��ݒ肷��B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����p���[�e�B���e�B
	 */
	public void setLengthUtils(LengthValidationUtils lengthUtils) {
		this.lengthUtils = lengthUtils;
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
	 * �A�b�v���[�h�t�@�C���̍ő�T�C�Y��ݒ肷��B<br/>
	 * �f�t�H���g�ł� 4M ���ݒ肳��Ă���A���̃T�C�Y�𒴂���ƍ�ƃt�H���_�ւ̏������݂���������B<br/>
	 * <br/>
	 * @param maxFileSize �A�b�v���[�h�t�@�C���̍ő�T�C�Y
	 */
	public void setMaxFileSize(int maxFileSize) {
		this.maxFileSize = maxFileSize;
	}

	
	
	/**
	 * CsvUploadFormFactory �̃C���X�^���X���擾����B<br/>
	 * Spring �̃R���e�L�X�g����A csvUploadFormFactory �Œ�`���ꂽ CsvUploadFormFactory ��
	 * �C���X�^���X���擾����B<br/>
	 * �擾�����C���X�^���X�́AcsvUploadFormFactory ���p�������g���N���X�����A�����ꍇ������B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return CsvUploadFormFactory�A�܂��͌p�����Ċg�������N���X�̃C���X�^���X
	 */
	public static CsvUploadFormFactory getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (CsvUploadFormFactory)springContext.getBean(CsvUploadFormFactory.FACTORY_BEAN_ID);
	}

	
	
	/**
	 * �A�b�v���[�h���ꂽ CSV �����i�[������ CsvUploadForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� CsvUploadForm �C���X�^���X 
	 */
	public CsvUploadForm createCsvUploadForm(){
		return new CsvUploadForm();
	}

	
	
	/**
	 * �A�b�v���[�h���ꂽ CSV �����i�[���� CsvUploadForm �̃C���X�^���X�𐶐�����B<br/>
	 * CsvUploadFormFactory �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� CsvUploadForm �C���X�^���X 
	 */
	public CsvUploadForm createCsvUploadForm(HttpServletRequest request){
		CsvUploadForm form = createCsvUploadForm();
		FormPopulator.populateFormBeanFromRequest(request, form, this.maxFileSize, new File(this.commonParameters.getUploadWorkPath()));	
		return form; 
	}



	/**
	 * �A�b�v���[�h���ꂽ�w���CSV ������H�����l���i�[������ StationCsvForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� StationCsvForm �C���X�^���X 
	 */
	public StationCsvForm createStationCsvForm(){
		return new StationCsvForm(this.lengthUtils);
	}


	
	/**
	 * �A�b�v���[�h���ꂽ�X�֔ԍ����CSV ������H�����l���i�[������ AddressCsvForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� AddressCsvForm �C���X�^���X 
	 */
	public AddressCsvForm createAddressCsvForm(){
		return new AddressCsvForm(this.lengthUtils);
	}
	
}
