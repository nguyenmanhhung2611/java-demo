package jp.co.transcosmos.dm3.corePana.model.passwordRemind.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class PasswordFormFactory {

	/** Form �𐶐����� Factory �� Bean ID */
	protected static String FACTORY_BEAN_ID = "passwordFormFactory";

	/** �����O�X�o���f�[�V�����p���[�e�B���e�B */
    protected LengthValidationUtils lengthUtils;

	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;

	/** ���ʃp�����[�^�I�u�W�F�N�g */
    protected CommonParameters commonParameters;

	/**
	 * �����O�X�o���f�[�V�����p���[�e�B���e�B��ݒ肷��B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����p���[�e�B���e�B
	 */
	public void setLengthUtils(LengthValidationUtils lengthUtils) {
		this.lengthUtils = lengthUtils;
	}

	/**
	 * ���ʃR�[�h�ϊ��I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param codeLookupManager
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	public void setCommonParameters(CommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
     * PanaMemberFormFactory �̃C���X�^���X���擾����B<br/>
     * Spring �̃R���e�L�X�g����A PanaMemberFormFactory �Œ�`���ꂽ PanaMemberFormFactory ��
     * �C���X�^���X���擾����B<br/>
     * �擾�����C���X�^���X�́APanaMemberFormFactory ���p�������g���N���X�����A�����ꍇ������B<br/>
     * <br/>
     * @param request HTTP ���N�G�X�g
     * @return PanaMemberFormFactory�A�܂��͌p�����Ċg�������N���X�̃C���X�^���X
     */
    public static PasswordFormFactory getInstance(HttpServletRequest request) {
    	WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (PasswordFormFactory)springContext.getBean(PasswordFormFactory.FACTORY_BEAN_ID);
    }

    /**
     * ������� MemberInfoForm �̃C���X�^���X�𐶐�����B<br/>
     * <br/>
     * @return ��� MemberInfoForm �C���X�^���X
     */
    public PasswordRemindForm createPasswordRemindForm() {
        return new PasswordRemindForm(this.lengthUtils);
    }

    /**
     * ������̌������ʁA����ь����������i�[���� MemberSearchForm �̃C���X�^���X�𐶐�����B<br/>
     * MemberSearchForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
     * <br/>
     * @param request HTTP ���N�G�X�g
     * @return ���N�G�X�g�p�����[�^��ݒ肵�� MemberSearchForm �C���X�^���X
     */
    public PasswordRemindForm createPasswordRemindForm(HttpServletRequest request) {
    	PasswordRemindForm form = createPasswordRemindForm();
        FormPopulator.populateFormBeanFromRequest(request, form);
        return form;
    }

    /**
     * ������� MemberInfoForm �̃C���X�^���X�𐶐�����B<br/>
     * <br/>
     * @return ��� MemberInfoForm �C���X�^���X
     */
    public PasswordChangeForm createPasswordChangeForm() {
        return new PasswordChangeForm(lengthUtils, commonParameters, codeLookupManager);
    }

    /**
     * ������̌������ʁA����ь����������i�[���� MemberSearchForm �̃C���X�^���X�𐶐�����B<br/>
     * MemberSearchForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
     * <br/>
     * @param request HTTP ���N�G�X�g
     * @return ���N�G�X�g�p�����[�^��ݒ肵�� MemberSearchForm �C���X�^���X
     */
    public PasswordChangeForm createPasswordChangeForm(HttpServletRequest request) {
    	PasswordChangeForm form = createPasswordChangeForm();
        FormPopulator.populateFormBeanFromRequest(request, form);
        return form;
    }
}
