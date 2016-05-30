package jp.co.transcosmos.dm3.corePana.model.housing.form;

import java.util.List;

import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousing;
import jp.co.transcosmos.dm3.corePana.vo.HousingStatusInfo;
import jp.co.transcosmos.dm3.corePana.vo.MemberInfo;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.login.UserRoleSet;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.MinLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * ���t�H�[����񃁃��e�i���X�̓��̓p�����[�^����p�t�H�[��.
 * <p>
 *
 * <pre>
 * �S����      �C����     �C�����e
 * ------------ ----------- -----------------------------------------------------
 * TRANS     2015.03.10  �V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class PanaHousingStatusForm {

	/** ���ʃR�[�h�ϊ����� */
	private CodeLookupManager codeLookupManager;

	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	PanaHousingStatusForm(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * �f�t���g�R���X�g���N�^�[�B<br/>
	 * <br/>
	 *
	 */
	PanaHousingStatusForm() {
		super();
	}

	/** ���J�敪 */
	private String hiddenFlg;

	/** �X�e�[�^�X */
	private String statusCd;

	/** ����ԍ� */
	private String userId;

	/** ���� */
	private String memberName;

	/** �d�b�ԍ� */
	private String tel;

	/** ���[���A�h���X */
	private String email;

	/** ���l */
	private String note;

	/** ���l(�\���p�j */
	private String showNote;

	/** �V�X�e������CD */
	private String sysHousingCd;

	/** �V�X�e�����t�H�[��CD */
	private String sysReformCd;

	/** �C�x���g�t���O */
	private String command;

	/** �V�X�e�������ԍ� */
	private String sysBuildingCd;

	/** ����CD */
	private String housingCd;

	/** �������� */
	private String displayHousingName;

	/** Readonly�t���O */
	private String readonlyFlg;

	/** ������ʃR�[�h */
	private String housingKindCd;

	/**
	 * ���J�敪 ���擾����B<br/>
	 * <br/>
	 *
	 * @return ���J�敪
	 */
	public String getHiddenFlg() {
		return hiddenFlg;
	}

	/**
	 * ���J�敪 ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param hiddenFlg
	 */
	public void setHiddenFlg(String hiddenFlg) {
		this.hiddenFlg = hiddenFlg;
	}

	/**
	 * �X�e�[�^�X ���擾����B<br/>
	 * <br/>
	 *
	 * @return �X�e�[�^�X
	 */
	public String getStatusCd() {
		return statusCd;
	}

	/**
	 * �X�e�[�^�X ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param statusCd
	 */
	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	/**
	 * ����ԍ� ���擾����B<br/>
	 * <br/>
	 *
	 * @return ����ԍ�
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * ����ԍ� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * ���� ���擾����B<br/>
	 * <br/>
	 *
	 * @return ����
	 */
	public String getMemberName() {
		return memberName;
	}

	/**
	 * ���� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param memberName
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	/**
	 * �d�b�ԍ� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �d�b�ԍ�
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * �d�b�ԍ� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param tel
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * ���[���A�h���X ���擾����B<br/>
	 * <br/>
	 *
	 * @return ���[���A�h���X
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * ���[���A�h���X ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * ���l ���擾����B<br/>
	 * <br/>
	 *
	 * @return ���l
	 */
	public String getNote() {
		return note;
	}

	/**
	 * ���l ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param note
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * ���l�i�\���p�j ���擾����B<br/>
	 * <br/>
	 *
	 * @return ���l
	 */
	public String getShowNote() {
		return showNote;
	}

	/**
	 * ���l�i�\���p�j ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param note
	 */
	public void setShowNote(String showNote) {
		this.showNote = showNote;
	}

	/**
	 * �V�X�e�����t�H�[��CD ���擾����B<br/>
	 * <br/>
	 *
	 * @return �V�X�e�����t�H�[��CD
	 */
	public String getSysReformCd() {
		return sysReformCd;
	}

	/**
	 * �V�X�e�����t�H�[��CD ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param sysReformCd
	 */
	public void setSysReformCd(String sysReformCd) {
		this.sysReformCd = sysReformCd;
	}

	/**
	 * �C�x���g�t���O ���擾����B<br/>
	 * <br/>
	 *
	 * @return �C�x���g�t���O
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * �C�x���g�t���O ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param command
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * �V�X�e�������ԍ� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �V�X�e�������ԍ�
	 */
	public String getSysBuildingCd() {
		return sysBuildingCd;
	}

	/**
	 * �V�X�e�������ԍ� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param sysBuildingCd
	 */
	public void setSysBuildingCd(String sysBuildingCd) {
		this.sysBuildingCd = sysBuildingCd;
	}

	/**
	 * �V�X�e������CD ���擾����B<br/>
	 * <br/>
	 *
	 * @return �V�X�e������CD
	 */
	public String getSysHousingCd() {
		return sysHousingCd;
	}

	/**
	 * �V�X�e������CD ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 */
	public void setSysHousingCd(String sysHousingCd) {
		this.sysHousingCd = sysHousingCd;
	}

	/**
	 * �������� ���擾����B<br/>
	 * <br/>
	 *
	 * @return ��������
	 */
	public String getDisplayHousingName() {
		return displayHousingName;
	}

	/**
	 * �������� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param displayHousingName
	 */
	public void setDisplayHousingName(String displayHousingName) {
		this.displayHousingName = displayHousingName;
	}

	/**
	 * �����ԍ� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �����ԍ�
	 */
	public String getHousingCd() {
		return housingCd;
	}

	/**
	 * �����ԍ� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param housingCd
	 */
	public void setHousingCd(String housingCd) {
		this.housingCd = housingCd;
	}

	public String getReadonlyFlg() {
		return readonlyFlg;
	}

	public void setReadonlyFlg(String readonlyFlg) {
		this.readonlyFlg = readonlyFlg;
	}

	public String getHousingKindCd() {
		return housingKindCd;
	}

	public void setHousingKindCd(String housingKindCd) {
		this.housingKindCd = housingKindCd;
	}

	/**
	 * �o���f�[�V��������<br/>
	 * ���N�G�X�g�p�����[�^�̃o���f�[�V�������s��<br/>
	 * <br/>
	 * ���� Form �N���X�̃o���f�[�V�������\�b�h�́AValidateable �C���^�[�t�F�[�X�̎����ł͖����̂ŁA
	 * �o���f�[�V�������s���̈������قȂ�̂ŁA���� Form ���܂Ƃ߂ăo���f�[�V��������ꍇ�Ȃǂ͒��� ���鎖�B<br/>
	 * <br/>
	 *
	 * @param errors
	 *            �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @param isAdmin
	 *            ���O�C�����[�U������admin�����茋��
	 * @return ���펞 true�A�G���[�� false
	 */
	public boolean validateUpd(List<ValidationFailure> errors, Boolean isAdmin, HousingStatusInfo housingStatusInfo) {
		int startSize = errors.size();


		// ���J�敪���̓`�F�b�N
		ValidationChain hiddenFlg = new ValidationChain(
				"housingBrowse.input.hiddenFlg", getHiddenFlg());
		// �K�{�`�F�b�N
		hiddenFlg.addValidation(new NullOrEmptyCheckValidation());
		// �p�^�[���`�F�b�N
		hiddenFlg.addValidation(new CodeLookupValidation(
				this.codeLookupManager, "hiddenFlg"));
		hiddenFlg.validate(errors);

		// �X�e�[�^�X
		ValidationChain statusCd = new ValidationChain(
				"housingBrowse.input.statusCd", getStatusCd());
		// �K�{�`�F�b�N
		statusCd.addValidation(new NullOrEmptyCheckValidation());
		// �p�^�[���`�F�b�N
		statusCd.addValidation(new CodeLookupValidation(this.codeLookupManager,
				"statusCd"));
		statusCd.validate(errors);

		// ���l
		ValidationChain note = new ValidationChain("housingBrowse.input.note",
				getNote());
		// �����`�F�b�N
		note.addValidation(new MinLengthValidation(0));
		note.addValidation(new MaxLengthValidation(200));
		note.validate(errors);

		// �����`�F�b�N
		if (housingStatusInfo.getHiddenFlg() != null) {
			if (!isAdmin
					&& !housingStatusInfo.getHiddenFlg().equals(getHiddenFlg())) {
				ValidationFailure vf = new ValidationFailure("role", "���J�敪",
						null, null);
				errors.add(vf);
			}
		}

		return (startSize == errors.size());
	}

	// �o���[�I�u�W�F�N�g��ݒ�
	public void copyToHousingStatusInfo(HousingStatusInfo housingstatusinfo) {
		// �V�X�e������CD
		housingstatusinfo.setSysHousingCd(setString(getSysHousingCd(),
				housingstatusinfo.getSysHousingCd()));
		// ����J�t���O
		housingstatusinfo.setHiddenFlg(setString(getHiddenFlg(),
				housingstatusinfo.getHiddenFlg()));
		// �X�e�[�^�XCD
		housingstatusinfo.setStatusCd(setString(getStatusCd(),
				housingstatusinfo.getStatusCd()));
		// ���[�U�[ID
		housingstatusinfo.setUserId(setString(getUserId(),
				housingstatusinfo.getUserId()));
		if ("".equals(getUserId())) {
			housingstatusinfo.setUserId(getUserId());
		}
		// ���l
		housingstatusinfo.setNote(getNote());
	}

	// String�^�̒l�ݒ�
	private String setString(String input, String nullValue) {
		if (input == null || "".equals(input)) {
			return nullValue;
		}

		return input;
	}

	/**
	 * �o���f�[�V��������<br/>
	 * ���N�G�X�g�p�����[�^�̃o���f�[�V�������s��<br/>
	 * <br/>
	 * ���� Form �N���X�̃o���f�[�V�������\�b�h�́AValidateable �C���^�[�t�F�[�X�̎����ł͖����̂ŁA
	 * �o���f�[�V�������s���̈������قȂ�̂ŁA���� Form ���܂Ƃ߂ăo���f�[�V��������ꍇ�Ȃǂ͒��� ���鎖�B<br/>
	 * <br/>
	 *
	 * @param errors
	 *            �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @param isAdmin
	 *            ���O�C�����[�U������admin�����茋��
	 * @return ���펞 true�A�G���[�� false
	 */
	public boolean validateIns(List<ValidationFailure> errors, Boolean isAdmin) {
		int startSize = errors.size();

		// �������̓��̓`�F�b�N
		ValidationChain displayHousingName = new ValidationChain(
				"housingStatus.input.displayHousingName", getDisplayHousingName());
		// �K�{�`�F�b�N
		displayHousingName.addValidation(new NullOrEmptyCheckValidation());
		// �����`�F�b�N
		displayHousingName.addValidation(new MinLengthValidation(0));
		displayHousingName.addValidation(new MaxLengthValidation(25));
		displayHousingName.validate(errors);

		// ������ʓ��̓`�F�b�N
		ValidationChain housingKindCd = new ValidationChain(
				"housingStatus.input.housingKindCd", getHousingKindCd());
		// �K�{�`�F�b�N
		housingKindCd.addValidation(new NullOrEmptyCheckValidation());
		// �p�^�[���`�F�b�N
		housingKindCd.addValidation(new CodeLookupValidation(
				this.codeLookupManager, "buildingInfo_housingKindCd"));
		housingKindCd.validate(errors);

		// ���J�敪���̓`�F�b�N
		ValidationChain hiddenFlg = new ValidationChain(
				"housingStatus.input.hiddenFlg", getHiddenFlg());
		// �K�{�`�F�b�N
		hiddenFlg.addValidation(new NullOrEmptyCheckValidation());
		// �p�^�[���`�F�b�N
		hiddenFlg.addValidation(new CodeLookupValidation(
				this.codeLookupManager, "hiddenFlg"));
		hiddenFlg.validate(errors);

		// �X�e�[�^�X
		ValidationChain statusCd = new ValidationChain(
				"housingStatus.input.statusCd", getStatusCd());
		// �K�{�`�F�b�N
		statusCd.addValidation(new NullOrEmptyCheckValidation());
		// �p�^�[���`�F�b�N
		statusCd.addValidation(new CodeLookupValidation(this.codeLookupManager,
				"statusCd"));
		statusCd.validate(errors);

		// ���l
		ValidationChain note = new ValidationChain("housingStatus.input.note",
				getNote());
		// �����`�F�b�N
		note.addValidation(new MinLengthValidation(0));
		note.addValidation(new MaxLengthValidation(200));
		note.validate(errors);

		// �����`�F�b�N
		if (!isAdmin && PanaCommonConstant.HIDDEN_FLG_PUBLIC.equals(getHiddenFlg())) {
			ValidationFailure vf = new ValidationFailure("role", "���J�敪", null,
					null);
			errors.add(vf);
		}

		return (startSize == errors.size());
	}

	/**
	 * �����X�e�[�^�X�i�V�K�j��ʊi�[<br/>
	 * <br/>
	 *
	 * @param userRole
	 *            ���͒l���i�[���ꂽ ���[�U�[��� �I�u�W�F�N�g
	 */
	public void setDefaultData(UserRoleSet userRole) {
		// �Ǘ��������[�U�̏ꍇ
		if (userRole.hasRole("admin")) {
			this.readonlyFlg = "0";
		} else {
			// �Ǘ��������[�U�ȊO�̏ꍇ
			// Readonly�ŕ\������
			this.readonlyFlg = "1";
		}

		if (StringValidateUtil.isEmpty(this.getCommand())) {
			// ���J�敪
			this.setHiddenFlg(PanaCommonConstant.HIDDEN_FLG_PRIVATE);
			// �������
			this.setHousingKindCd(PanaCommonConstant.HOUSING_KIND_CD_MANSION);
		}

		// command
		this.setCommand("insert");
	}

	/**
	 * �X�e�[�^�X�ҏW�̊i�[<br/>
	 * <br/>
	 *
	 * @param housing
	 *            ���͒l���i�[���ꂽ Housing �I�u�W�F�N�g
	 * @param userRole
	 *            ���͒l���i�[���ꂽ ���[�U�[��� �I�u�W�F�N�g
	 * @param memberInfo
	 *            ���͒l���i�[���ꂽ �}�C�y�[�W��� �I�u�W�F�N�g
	 */
	public void setDefaultData(PanaHousing housing, UserRoleSet userRole,
			MemberInfo memberInfo) {

		// �����X�e�[�^�X���̎擾
		JoinResult housingResult = housing.getHousingInfo();
		HousingStatusInfo housingStatusInfo = (HousingStatusInfo) housingResult
				.getItems().get("housingStatusInfo");

		// ���J�敪
		this.setHiddenFlg(housingStatusInfo.getHiddenFlg());

		// �X�e�[�^�X
		this.setStatusCd(housingStatusInfo.getStatusCd());
		// ����ԍ�
		this.setUserId(housingStatusInfo.getUserId());

		// �Ǘ��������[�U�̏ꍇ
		if (userRole.hasRole("admin")) {
			this.readonlyFlg = "0";
		} else {
			// �Ǘ��������[�U�ȊO�̏ꍇ
			// Readonly�ŕ\������
			this.readonlyFlg = "1";
		}
		// ����
		StringBuffer sbr = new StringBuffer();
		if (!StringValidateUtil.isEmpty(memberInfo.getMemberLname())) {
			sbr.append(memberInfo.getMemberLname());
		}
		if (!StringValidateUtil.isEmpty(memberInfo.getMemberFname())) {
			sbr.append(memberInfo.getMemberFname());
		}
		this.setMemberName(sbr.toString());

		// �d�b�ԍ�
		this.setTel(memberInfo.getTel());
		// ���[���A�h���X
		this.setEmail(memberInfo.getEmail());
		// ���l�i�Ǘ��җp�j
		this.setNote(housingStatusInfo.getNote());
	}
}
