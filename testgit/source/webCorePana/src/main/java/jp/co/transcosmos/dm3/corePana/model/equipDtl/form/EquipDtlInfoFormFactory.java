package jp.co.transcosmos.dm3.corePana.model.equipDtl.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.form.FormPopulator;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * <pre>
 * �ݔ����p Form �� Factory �N���X
 * getInstance() ���ASpring ����C���X�^���X���擾���Ďg�p���鎖�B
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �s�����C		2015.4.14	�V�K�쐬
 *
 * ���ӎ���
 * Factory �̃C���X�^���X�𒼐ڐ������Ȃ����B�@�K��getInstance() �Ŏ擾���鎖�B
 *
 * </pre>
 */
public class EquipDtlInfoFormFactory {
	/** Form �𐶐����� Factory �� Bean ID */
	protected static String FACTORY_BEAN_ID = "equipDtlInfoFormFactory";

	/**
	 * EquipDtlInfoFormFactory �̃C���X�^���X���擾����B<br/>
	 * Spring �̃R���e�L�X�g����A equipDtlInfoFormFactory �Œ�`���ꂽ EquipDtlInfoFormFactory ��
	 * �C���X�^���X���擾����B<br/>
	 * �擾�����C���X�^���X�́AEquipDtlInfoFormFactory ���p�������g���N���X�����A�����ꍇ������B<br/>
	 * <br/>
	 *
	 * @param request HTTP ���N�G�X�g
	 * @return EquipDtlInfoFormFactory�A�܂��͌p�����Ċg�������N���X�̃C���X�^���X
	 */
	public static EquipDtlInfoFormFactory getInstance(HttpServletRequest request) {
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request
				.getServletContext());
		return (EquipDtlInfoFormFactory) springContext.getBean(EquipDtlInfoFormFactory.FACTORY_BEAN_ID);
	}

	/**
	 * �ݔ������i�[������ EquipDtlInfoForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 *
	 * @return ��� EquipDtlInfoForm �C���X�^���X
	 */
	public EquipDtlInfoForm createEquipDtlInfoForm() {
		return new EquipDtlInfoForm();
	}

	/**
	 * �ݔ������i�[���� EquipDtlInfoForm �̃C���X�^���X�𐶐�����B<br/>
	 * EquipDtlInfoFormFactory �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� EquipDtlInfoForm �C���X�^���X
	 */
	public EquipDtlInfoForm createEquipDtlInfoForm(HttpServletRequest request) {
		EquipDtlInfoForm form = createEquipDtlInfoForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}
}
