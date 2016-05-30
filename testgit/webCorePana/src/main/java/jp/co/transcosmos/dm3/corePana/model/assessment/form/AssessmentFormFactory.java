package jp.co.transcosmos.dm3.corePana.model.assessment.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * ���胁���e�i���X�p Form �� Factory �N���X.
 * <p>
 * getInstance() ���ASpring ����C���X�^���X���擾���Ďg�p���鎖�B
 * <p>
 * <pre>
 * �S����	   �C����	  �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �`����		2015.04.28	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * Factory �̃C���X�^���X�𒼐ڐ������Ȃ����B�@�K��getInstance() �Ŏ擾���鎖�B
 *
 */
public class AssessmentFormFactory {

	/** Form �𐶐����� Factory �� Bean ID */
	protected static String FACTORY_BEAN_ID = "assessmentFormFactory";

	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;

	/** ���ʃp�����[�^�I�u�W�F�N�g */
	protected CommonParameters commonParameters;

	/** �����O�X�o���f�[�V�����p���[�e�B���e�B */
	protected LengthValidationUtils lengthUtils;



	/**
	 * ���ʃR�[�h�ϊ�������ݒ肷��B<br/>
	 * <br/>
	 * @param codeLookupManager ���ʃR�[�h�ϊ�����
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
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
	 * �����O�X�o���f�[�V�����p���[�e�B���e�B��ݒ肷��B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����p���[�e�B���e�B
	 */
	public void setLengthUtils(LengthValidationUtils lengthUtils) {
		this.lengthUtils = lengthUtils;
	}


	/**
	 * AssessmentFormFactory �̃C���X�^���X���擾����B<br/>
	 * Spring �̃R���e�L�X�g����A assessmentFormFactory �Œ�`���ꂽ AssessmentFormFactory ��
	 * �C���X�^���X���擾����B<br/>
	 * �擾�����C���X�^���X�́AassessmentFormFactory ���p�������g���N���X�����A�����ꍇ������B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return AssessmentFormFactory�A�܂��͌p�����Ċg�������N���X�̃C���X�^���X
	 */
	public static AssessmentFormFactory getInstance(HttpServletRequest request) {
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request
				.getServletContext());
		return (AssessmentFormFactory) springContext.getBean(AssessmentFormFactory.FACTORY_BEAN_ID);
	}

	/**
	 *����̂��\�����݂̓��͓��e����p��� AssessmentInputForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� AssessmentInputForm �C���X�^���X
	 */
	public AssessmentInputForm createAssessmentInputForm() {
		return new AssessmentInputForm(this.lengthUtils, this.commonParameters, this.codeLookupManager);
	}

	public AssessmentInputForm createAssessmentInputForm(HttpServletRequest request) {
		AssessmentInputForm form = createAssessmentInputForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}
}
