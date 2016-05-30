package jp.co.transcosmos.dm3.core.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

/**
 * �����O�X�o���f�[�V�����̃��[�e�B���e�B�N���X.
 * <p>
 * �J�X�^�}�C�Y���ꂽ�����O�X���̒l���擾����B<br/>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.27	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class LengthValidationUtils {

	/** ���̃��[�e�B���e �� Bean ID */
	protected static String FACTORY_BEAN_ID = "lengthValidationUtils";

	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;

	/** �J�X�^�}�C�Y���ꂽ�����񒷂��Ǘ����� codelookup �� */
	protected String lookupName = "inputLength";
	
	
	
	/**
	 * ���ʃR�[�h�ϊ�������ݒ肷��B<br/>
	 * <br/>
	 * @param codeLookupManager ���ʃR�[�h�ϊ�����
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * �J�X�^�}�C�Y���ꂽ�����񒷂��Ǘ����� codelookup ����ύX����ꍇ�A���̃v���p�e�B�Őݒ肷��B<br/>
	 * �i�f�t�H���g�l�� inputLength�j<br/>
	 * <br/>
	 * @param lookupName �J�X�^�}�C�Y���ꂽ�����񒷂��Ǘ����� codelookup ��
	 */
	public void setLookupName(String lookupName) {
		this.lookupName = lookupName;
	}



	/**
	 * LengthValidationUtils �̃C���X�^���X���擾����B<br/>
	 * Spring �̃R���e�L�X�g����A lengthValidationUtils �Œ�`���ꂽ LengthValidationUtils ��
	 * �C���X�^���X���擾����B<br/>
	 * �擾�����C���X�^���X�́ALengthValidationUtils ���p�������g���N���X�����A�����ꍇ������B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return LengthValidationUtils�A�܂��͌p�����Ċg�������N���X�̃C���X�^���X
	 */
	public static LengthValidationUtils getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (LengthValidationUtils)springContext.getBean(LengthValidationUtils.FACTORY_BEAN_ID);
	}


	
	/**
	 * �J�X�^�}�C�Y���ꂽ�����O�X�����擾����B<br/>
	 * label �ɂ́AerrorMessage.xml �� errorLabels�AerrorCustom �Ŏg�p����Ă���e���͍���
	 * ���� Key �l��ݒ肷��B<br/>
	 * �����AlookupName�@�v���p�e�B�Ŏw�肳��Ă��� codelookup ��`�ɁAlabel�@�ɊY������l�����݂���
	 * �ꍇ�A������̒l�𕜋A����B<br/>
	 * �Y������l�����݂��Ȃ��ꍇ�� defaultValue �̒l�𕜋A����B<br/>
	 * lookupName�@�v���p�e�B�̏����l�́A�uinputLength�v���ݒ肳��Ă���A���͕����񒷂̃J�X�^�}�C�Y��
	 * �s���ꍇ�́AcustomeLengthValidation.xml�@���C������B<br/>
	 * <br/>
	 * 
	 * @param label ���͗��̃��x����
	 * @param defaultValue �f�t�H���g�ƂȂ镶����
	 * @return
	 */
	public Integer getLength(String label, Integer defaultValue) {

		String value = this.codeLookupManager.lookupValue(this.lookupName, label);
		if (value == null) return defaultValue;

		return Integer.valueOf(value);
	}
	
	
	
	
}
