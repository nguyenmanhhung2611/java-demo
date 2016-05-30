package jp.co.transcosmos.dm3.corePana.model.equipDtl.form;

import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.validation.LineAdapter;
import jp.co.transcosmos.dm3.core.validation.LineValidationFailure;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousing;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.util.StringUtils;

/**
 * �ݔ����p�t�H�[��.
 * <p>
 *
 * <pre>
 * �S����       �C����     �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �s�����C		2015.4.14	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class EquipDtlInfoForm implements Validateable {

	/** ���ڐ��� */
	private final static int _CONT = 30;
	/** �V�X�e������CD */
	private String sysHousingCd;
	/** �������CD */
	private String housingKindCd;
	/** �����ԍ� */
	private String housingCd;
	/** �������� */
	private String displayHousingName;
	/** �ݔ����� */
	private String[] equipCategory = new String[_CONT];
	/** �ݔ� */
	private String[] equip = new String[_CONT];
	/** ���t�H�[�� */
	private String[] reform = new String[_CONT];
	/** �L�[�ԍ� */
	private String[] keyCd = new String[_CONT];
	/** ���߃t���O */
	private String command;

	/** �ݔ����� */
	private final static String _EQUIP_CATEGORY = "adminEquipName";
	/** �ݔ� */
	private final static String _EQUIP = "adminEquipInfo";
	/** ���t�H�[�� */
	private final static String _REFORM = "adminEquipReform";

	EquipDtlInfoForm() {
		super();
	}

	/**
	 * @return sysHousingCd
	 */
	public String getSysHousingCd() {
		return sysHousingCd;
	}

	/**
	 * @param sysHousingCd �Z�b�g���� sysHousingCd
	 */
	public void setSysHousingCd(String sysHousingCd) {
		this.sysHousingCd = sysHousingCd;
	}

	/**
	 * @return housingKindCd
	 */
	public String getHousingKindCd() {
		return housingKindCd;
	}

	/**
	 * @param housingKindCd �Z�b�g���� housingKindCd
	 */
	public void setHousingKindCd(String housingKindCd) {
		this.housingKindCd = housingKindCd;
	}

	/**
	 * @return housingCd
	 */
	public String getHousingCd() {
		return housingCd;
	}

	/**
	 * @param housingCd �Z�b�g���� housingCd
	 */
	public void setHousingCd(String housingCd) {
		this.housingCd = housingCd;
	}

	/**
	 * @return displayHousingName
	 */
	public String getDisplayHousingName() {
		return displayHousingName;
	}

	/**
	 * @param displayHousingName �Z�b�g���� displayHousingName
	 */
	public void setDisplayHousingName(String displayHousingName) {
		this.displayHousingName = displayHousingName;
	}

	/**
	 * @return equipCategory
	 */
	public String[] getEquipCategory() {
		return equipCategory;
	}

	/**
	 * @param equipCategory �Z�b�g���� equipCategory
	 */
	public void setEquipCategory(String[] equipCategory) {
		this.equipCategory = equipCategory;
	}

	/**
	 * @return equip
	 */
	public String[] getEquip() {
		return equip;
	}

	/**
	 * @param equip �Z�b�g���� equip
	 */
	public void setEquip(String[] equip) {
		this.equip = equip;
	}

	/**
	 * @return reform
	 */
	public String[] getReform() {
		return reform;
	}

	/**
	 * @param reform �Z�b�g���� reform
	 */
	public void setReform(String[] reform) {
		this.reform = reform;
	}

	/**
	 * @return keyCd
	 */
	public String[] getKeyCd() {
		return keyCd;
	}

	/**
	 * @param keyCd �Z�b�g���� keyCd
	 */
	public void setKeyCd(String[] keyCd) {
		this.keyCd = keyCd;
	}

	/**
	 * @return command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @param command �Z�b�g���� command
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * �L�[�ԍ��ݒ肷��<br/>
	 * <br/>
	 *
	 */
	public void init() {
		for (int i = 0; i < _CONT; i++) {
			this.keyCd[i] = String.valueOf(i + 1);
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

		for (int i = 0; i < _CONT; i++) {
			// �ݔ����ړ��̓`�F�b�N
			ValidationChain equipCategory = new ValidationChain("equipDtl.input.equipCategory", this.equipCategory[i]);
			// �����`�F�b�N
			equipCategory.addValidation(new LineAdapter(new MaxLengthValidation(30), i + 1));
			equipCategory.validate(errors);

			// �ݔ����̓`�F�b�N
			ValidationChain equip = new ValidationChain("equipDtl.input.equip", this.equip[i]);
			// �����`�F�b�N
			equip.addValidation(new LineAdapter(new MaxLengthValidation(30), i + 1));
			equip.validate(errors);

			// �ݔ����ڂƐݔ��̊֘A�`�F�b�N
			if (!StringUtils.isEmpty(this.equip[i]) && StringUtils.isEmpty(this.equipCategory[i])) {
				ValidationFailure equipVf = new LineValidationFailure(new ValidationFailure("equipCategoryNotNull",
						"�ݔ�", "�ݔ�����", new String[] { "����" }), Integer.valueOf(keyCd[i]));
				errors.add(equipVf);
			}

			// �ݔ����ڂƃ��t�H�[���̊֘A�`�F�b�N
			if ("1".equals(this.reform[i]) && StringUtils.isEmpty(this.equipCategory[i])) {
				ValidationFailure reformVf = new LineValidationFailure(new ValidationFailure("equipCategoryNotNull",
						"���t�H�[��", "�ݔ�����", new String[] { "�`�F�b�N" }), Integer.valueOf(keyCd[i]));
				errors.add(reformVf);
			}

		}

		return (startSize == errors.size());
	}

	/**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� Form �֏����l��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param housing �������I�u�W�F�N�g
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void setDefaultData(PanaHousing housing) throws Exception {
		// ������{�����擾����B
		HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");

		// �����ԍ����Z�b�g����B
		this.housingCd = housingInfo.getHousingCd();

		// �\���p���������Z�b�g����B
		this.displayHousingName = housingInfo.getDisplayHousingName();

		// �����g�����������擾����B
		Map<String, Map<String, String>> extMap = housing.getHousingExtInfos();

		// �J�e�S�����ɊY������ Map ���擾����B
		Map<String, String> cateMap = extMap.get("adminEquip");

		if (cateMap == null) {
			return;
		}

		// �ݔ�����
		String[] equipCategory = new String[_CONT];
		// �ݔ�
		String[] equip = new String[_CONT];
		// ���t�H�[��
		String[] reform = new String[_CONT];

		// �Ǘ��җp�ݔ����̎擾
		for (String key : cateMap.keySet()) {
			// Key��
			String keyName = "";

			// Key���ԍ�
			int keyNameNm = 0;

			try {
				// Key��
				keyName = key.substring(0, key.length() - 2);

				// Key���ԍ�
				keyNameNm = Integer.parseInt(key.substring(key.length() - 2));
			} catch (Exception e) {
				throw new Exception("Key���t�H�[�}�b�g���s���ł��B");
			}

			// Key�����ݔ����ڂ̏ꍇ
			if (_EQUIP_CATEGORY.equals(keyName)) {
				equipCategory[keyNameNm - 1] = (cateMap.get(key));

			} else if (_EQUIP.equals(keyName)) {
				// Key�����ݔ��̏ꍇ
				equip[keyNameNm - 1] = (cateMap.get(key));

			} else if (_REFORM.equals(keyName)) {
				// Key�������t�H�[���̏ꍇ
				reform[keyNameNm - 1] = (cateMap.get(key));
			}
		}

		// �ݔ����ڂ��Z�b�g����B
		this.equipCategory = equipCategory;
		// �ݔ����Z�b�g����B
		this.equip = equip;
		// ���t�H�[�����Z�b�g����B
		this.reform = reform;
	}

}
