package jp.co.transcosmos.dm3.corePana.model.housing.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <pre>
 * �������߃|�C���g�ҏW�̓��̓p�����[�^����p�t�H�[��
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �s�����C		2015.4.10	�V�K�쐬
 *
 * ���ӎ���
 * ���� Form �̃o���f�[�V�����́A�g�p���郂�[�h�i�ǉ����� or �X�V�����j�ňقȂ�̂ŁA
 * �t���[�����[�N���񋟂��� Validateable �C���^�[�t�F�[�X�͎������Ă��Ȃ��B
 * �o���f�[�V�������s���̃p�����[�^���ʏ�ƈقȂ�̂Œ��ӂ��鎖�B
 *
 * </pre>
 */
public class RecommendPointForm extends HousingForm {
    /**
     * �f�t�H���g�R���X�g���N�^�[<br/>
     * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
     * <br/>
     */
    protected RecommendPointForm() {
        super();
    }

    /**
     * �R���X�g���N�^�[<br/>
     * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
     * <br/>
     * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
     * @param�@codeLookupManager�@���ʃR�[�h�ϊ�����
     */
    protected RecommendPointForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager) {
        super();
        this.lengthUtils = lengthUtils;
        this.codeLookupManager = codeLookupManager;
    }

	private static final Log log = LogFactory.getLog(RecommendPointForm.class);

	/** command �p�����[�^ */
	private String command;

	/** �A�C�R����� */
	private String[] icon;

	/** �������CD */
	private String housingKindCd;

	/**
	 * command �p�����[�^���擾����B<br/>
	 * <br/>
	 * @return command �p�����[�^
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * command �p�����[�^��ݒ肷��B<br/>
	 * <br/>
	 * @param command command �p�����[�^
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * �A�C�R�������擾����B<br/>
	 * <br/>
	 * @return �A�C�R�����
	 */
	public String[] getIcon() {
		return icon;
	}

	/**
	 * �A�C�R������ݒ肷��B<br/>
	 * <br/>
	 * @param icon �A�C�R�����
	 */
	public void setIcon(String[] icon) {
		this.icon = icon;
	}

	/**
	 * �������CD���擾����B<br/>
	 * <br/>
	 * @return �������CD
	 */
	public String getHousingKindCd() {
		return housingKindCd;
	}

	/**
	 * �������CD��ݒ肷��B<br/>
	 * <br/>
	 * @param housingKindCd �������CD
	 */
	public void setHousingKindCd(String housingKindCd) {
		this.housingKindCd = housingKindCd;
	}

	/**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� Form �֏����l��ݒ肷��B<br/>
	 * <br/>
	 * @param housing Housing�@�����������⍇�����Ǘ��p�o���[�I�u�W�F�N�g
	 */
	public void setDefaultData(Housing housing, RecommendPointForm inputForm) {

		// �����ԍ���ݒ肷��
        inputForm.setHousingCd(
        		((HousingInfo)housing.getHousingInfo().getItems().get("housingInfo")).getHousingCd());

        // �\���p��������ݒ肷��
        inputForm.setDisplayHousingName(
        		((HousingInfo)housing.getHousingInfo().getItems().get("housingInfo")).getDisplayHousingName());

        // �A�C�R������ݒ肷��
        String iconCd = ((HousingInfo)housing.getHousingInfo().getItems().get("housingInfo")).getIconCd();
        if(iconCd != null){
        	String[] icon = iconCd.split(",");
	        inputForm.setIcon(icon);
        }

        inputForm.setCommand("input");

	}

	/**
	 * �����œn���ꂽ���m�点���̃o���[�I�u�W�F�N�g�Ƀt�H�[���̒l��ݒ肷��B<br/>
	 * �X�V�����ł��g�p���鎖���l�����A�^�C���X�^���v���Ɋւ��ẮA�X�V���A�X�V�҂̂ݐݒ肷��B<br/>
	 * <br/>
	 * @param housingInfo �l��ݒ肷�镨����{���̃o���[�I�u�W�F�N�g
	 *
	 */
	@Override
	public void copyToHousingInfo(HousingInfo housingInfo) {

		StringBuffer iconCd = new StringBuffer();

		if(this.icon != null){
			for (int i = 0; i < this.icon.length; i++) {
				if (!"".equals(this.icon[i])) {
					iconCd.append(this.icon[i]).append(",");
				}
			}
			// �A�C�R������ݒ�
			if (iconCd.length() > 0) {
				housingInfo.setIconCd(iconCd.toString().substring(0, iconCd.toString().length() - 1));
			}
		} else {
			housingInfo.setIconCd(iconCd.toString());
		}
	}

	/**
	 * �o���f�[�V��������<br/>
	 * ���N�G�X�g�p�����[�^�̃o���f�[�V�������s��<br/>
	 * <br/>
	 * ���� Form �N���X�̃o���f�[�V�������\�b�h�́AValidateable �C���^�[�t�F�[�X�̎����ł͖����̂ŁA
	 * �o���f�[�V�������s���̈������قȂ�̂ŁA���� Form ���܂Ƃ߂ăo���f�[�V��������ꍇ�Ȃǂ͒��ӂ��鎖�B<br/>
	 * <br/>
	 *
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @return ���펞 true�A�G���[�� false
	 */
	@Override
	public boolean validate(List<ValidationFailure> errors) {

		int startSize = errors.size();

		String[] icons = getIcon();

		if (icons != null) {
			for (int i = 0; i < icons.length; i++) {

				String icon = icons[i];
				// �������߃|�C���g
				ValidationChain iconVal = new ValidationChain("recommendPoint.input.icon", icon);

				// �p�^�[���`�F�b�N
				iconVal.addValidation(new CodeLookupValidation(this.codeLookupManager,"recommend_point_icon"));
				iconVal.validate(errors);
			}
		}

		return (startSize == errors.size());
	}
}
