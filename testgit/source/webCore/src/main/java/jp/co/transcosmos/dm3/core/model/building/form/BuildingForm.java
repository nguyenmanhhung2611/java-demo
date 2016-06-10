package jp.co.transcosmos.dm3.core.model.building.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.AlphanumericOnlyValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.NumberValidation;
import jp.co.transcosmos.dm3.validation.ValidDateValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <pre>
 * ������񃁃��e�i���X�̓��̓p�����[�^����p�t�H�[��
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.27	�V�K�쐬
 *
 * ���ӎ���
 * �t���[�����[�N���񋟂��� Validateable �C���^�[�t�F�[�X�͎������Ă��Ȃ��B
 * �o���f�[�V�������s���̃p�����[�^���ʏ�ƈقȂ�̂Œ��ӂ��鎖�B
 *
 * </pre>
 */
public class BuildingForm implements Validateable {

	private static final Log log = LogFactory.getLog(BuildingForm.class);
	
	/** command �p�����[�^ */
	private String command;
	
	/** �V�X�e������CD */
	private String sysBuildingCd;
	/** �����ԍ� */
	private String buildingCd;
	/** �������CD */
	private String housingKindCd;
	/** �����\��CD */
	private String structCd;
	/** �\���p������ */
	private String displayBuildingName;
	/** �\���p�������ӂ肪�� */
	private String displayBuildingNameKana;
	/** ���ݒn�E�X�֔ԍ� */
	private String zip;
	/** ���ݒn�E�s���{��CD */
	private String prefCd;
	/** ���ݒn�E�s���{���� */
	private String prefName;
	/** ���ݒn�E�s�撬��CD */
	private String addressCd;
	/** ���ݒn�E�s�撬���� */
	private String addressName;
	/** ���ݒn�E�����Ԓn */
	private String addressOther1;
	/** ���ݒn�E���������̑�  */
	private String addressOther2;
	/** �v�H�N�� */
	private String compDate;
	/** �v�H�{ */
	private String compTenDays;
	/** ���K�� */
	private String totalFloors;
	
	/** �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B */
	protected LengthValidationUtils lengthUtils;

	/**
	 * �p���p<br/>
	 * <br/>
	 */
	protected BuildingForm() {
		super();
	}
	
	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
	 */
	protected BuildingForm(LengthValidationUtils lengthUtils) {
		super();
		this.lengthUtils = lengthUtils;
	}
	
	
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
	 * �V�X�e������CD���擾����B<br/>
	 * <br/>
	 * @return �V�X�e������CD
	 */
	public String getSysBuildingCd() {
		return sysBuildingCd;
	}
	/**
	 * �V�X�e������CD��ݒ肷��B<br/>
	 * <br/>
	 * @param sysBuildingCd �V�X�e������CD
	 */
	public void setSysBuildingCd(String sysBuildingCd) {
		this.sysBuildingCd = sysBuildingCd;
	}
	/**
	 * �����ԍ����擾����B<br/>
	 * <br/>
	 * @return �����ԍ�
	 */
	public String getBuildingCd() {
		return buildingCd;
	}
	/**
	 * �����ԍ���ݒ肷��B<br/>
	 * <br/>
	 * @param buildingCd �����ԍ�
	 */
	public void setBuildingCd(String buildingCd) {
		this.buildingCd = buildingCd;
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
	 * �\���p���������擾����B<br/>
	 * <br/>
	 * @return �\���p������
	 */
	public String getDisplayBuildingName() {
		return displayBuildingName;
	}
	/**
	 * �\���p��������ݒ肷��B<br/>
	 * <br/>
	 * @param displayBuildingName �\���p������
	 */
	public void setDisplayBuildingName(String displayBuildingName) {
		this.displayBuildingName = displayBuildingName;
	}
	/**
	 * �\���p�������ӂ肪�Ȃ��擾����B<br/>
	 * <br/>
	 * @return �\���p�������ӂ肪��
	 */
	public String getDisplayBuildingNameKana() {
		return displayBuildingNameKana;
	}
	/**
	 * �\���p�������ӂ肪�Ȃ�ݒ肷��B<br/>
	 * <br/>
	 * @param displayBuildingNameKana �\���p�������ӂ肪��
	 */
	public void setDisplayBuildingNameKana(String displayBuildingNameKana) {
		this.displayBuildingNameKana = displayBuildingNameKana;
	}
	/**
	 * ���ݒn�E�X�֔ԍ����擾����B<br/>
	 * <br/>
	 * @return ���ݒn�E�X�֔ԍ�
	 */
	public String getZip() {
		return zip;
	}
	/**
	 * ���ݒn�E�X�֔ԍ���ݒ肷��B<br/>
	 * <br/>
	 * @param zip ���ݒn�E�X�֔ԍ�
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	/**
	 * ���ݒn�E�s���{��CD���擾����B<br/>
	 * <br/>
	 * @return ���ݒn�E�s���{��CD
	 */
	public String getPrefCd() {
		return prefCd;
	}
	/**
	 * ���ݒn�E�s���{��CD��ݒ肷��B<br/>
	 * <br/>
	 * @param prefCd ���ݒn�E�s���{��CD
	 */
	public void setPrefCd(String prefCd) {
		this.prefCd = prefCd;
	}
	/**
	 * ���ݒn�E�s���{�������擾����B<br/>
	 * <br/>
	 * @return ���ݒn�E�s���{����
	 */
	public String getPrefName() {
		return prefName;
	}
	/**
	 * ���ݒn�E�s���{������ݒ肷��B<br/>
	 * <br/>
	 * @param prefCd ���ݒn�E�s���{����
	 */
	public void setPrefName(String prefName) {
		this.prefName = prefName;
	}
	/**
	 * ���ݒn�E�s�撬��CD���擾����B<br/>
	 * <br/>
	 * @return ���ݒn�E�s�撬��CD
	 */
	public String getAddressCd() {
		return addressCd;
	}
	/**
	 * ���ݒn�E�s�撬��CD��ݒ肷��B<br/>
	 * <br/>
	 * @param addressCd ���ݒn�E�s�撬��CD
	 */
	public void setAddressCd(String addressCd) {
		this.addressCd = addressCd;
	}
	/**
	 * ���ݒn�E�s�撬�������擾����B<br/>
	 * <br/>
	 * @return ���ݒn�E�s�撬����
	 */
	public String getAddressName() {
		return addressName;
	}
	/**
	 * ���ݒn�E�s�撬������ݒ肷��B<br/>
	 * <br/>
	 * @param addressName ���ݒn�E�s�撬����
	 */
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	/**
	 * ���ݒn�E�����Ԓn���擾����B<br/>
	 * <br/>
	 * @return ���ݒn�E�����Ԓn
	 */
	public String getAddressOther1() {
		return addressOther1;
	}
	/**
	 * ���ݒn�E�����Ԓn��ݒ肷��B<br/>
	 * <br/>
	 * @param addressOther1 ���ݒn�E�����Ԓn
	 */
	public void setAddressOther1(String addressOther1) {
		this.addressOther1 = addressOther1;
	}
	/**
	 * ���ݒn�E���������̑����擾����B<br/>
	 * <br/>
	 * @return ���ݒn�E���ݒn�E���������̑�
	 */
	public String getAddressOther2() {
		return addressOther2;
	}
	/**
	 * ���ݒn�E���������̑���ݒ肷��B<br/>
	 * <br/>
	 * @param addressOther2 ���ݒn�E���������̑�
	 */
	public void setAddressOther2(String addressOther2) {
		this.addressOther2 = addressOther2;
	}
	/**
	 * �����\��CD���擾����B<br/>
	 * <br/>
	 * @return �����\��CD
	 */
	public String getStructCd() {
		return structCd;
	}
	/**
	 * �����\��CD��ݒ肷��B<br/>
	 * <br/>
	 * @param structCd �����\��CD
	 */
	public void setStructCd(String structCd) {
		this.structCd = structCd;
	}
	/**
	 * �v�H�N�����擾����B<br/>
	 * <br/>
	 * @return �v�H�N��
	 */
	public String getCompDate() {
		return compDate;
	}
	/**
	 * �v�H�N����ݒ肷��B<br/>
	 * <br/>
	 * @param compDate �v�H�N��
	 */
	public void setCompDate(String compDate) {
		this.compDate = compDate;
	}
	/**
	 * �v�H�{���擾����B<br/>
	 * <br/>
	 * @return �v�H�{
	 */
	public String getCompTenDays() {
		return compTenDays;
	}
	/**
	 * �v�H�{��ݒ肷��B<br/>
	 * <br/>
	 * @param compTenDays �v�H�{
	 */
	public void setCompTenDays(String compTenDays) {
		this.compTenDays = compTenDays;
	}
	/**
	 * ���K�����擾����B<br/>
	 * <br/>
	 * @return ���K��
	 */
	public String getTotalFloors() {
		return totalFloors;
	}
	/**
	 * ���K����ݒ肷��B<br/>
	 * <br/>
	 * @param totalFloors ���K��
	 */
	public void setTotalFloors(String totalFloors) {
		this.totalFloors = totalFloors;
	}
	
	/**
	 * �o���f�[�V��������<br/>
	 * ���N�G�X�g�p�����[�^�̃o���f�[�V�������s��<br/>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @return ���펞 true�A�G���[�� false
	 */
	public boolean validate(List<ValidationFailure> errors) {

        int startSize = errors.size();
        
        // �����ԍ����̓`�F�b�N
        validBuildingCd(errors);
        // �\���p���������̓`�F�b�N
        validDisplayBuildingName(errors);
        // �\���p�������ӂ肪�ȓ��̓`�F�b�N
        validDisplayBuildingNameKana(errors);
        // ���ݒn�E�X�֔ԍ����̓`�F�b�N
        validZip(errors);
        // ���ݒn�E�s���{�����̓`�F�b�N
        validPrefCd(errors);
        // ���ݒn�E�����Ԓn���̓`�F�b�N
        validAddressOther1(errors);
        // ���ݒn�E���������̑����̓`�F�b�N
        validAddressOther2(errors);
        // �v�H�N�����̓`�F�b�N
        validCompDate(errors);
        // �v�H�{���̓`�F�b�N
        validCompTenDays(errors);
        // ���K�����̓`�F�b�N
        validTotalFloors(errors);
        return (startSize == errors.size());
	}
	
	/**
	 * �����ԍ� �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * �E���p�p���`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validBuildingCd(List<ValidationFailure> errors) {
        // �����ԍ����̓`�F�b�N
        ValidationChain valid = new ValidationChain("buildingInfo.input.buildingCd",this.buildingCd);
        // �����`�F�b�N
        int len = this.lengthUtils.getLength("buildingInfo.input.buildingCd", 20);
        valid.addValidation(new MaxLengthValidation(len));
        // ���p�p���`�F�b�N
        valid.addValidation(new AlphanumericOnlyValidation());
        valid.validate(errors);

	}
	
	/**
	 * �\���p������ �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validDisplayBuildingName(List<ValidationFailure> errors) {
		// �\���p���������̓`�F�b�N
		ValidationChain valid = new ValidationChain(
				"buildingInfo.input.displayBuildingName",
				this.displayBuildingName);
		// �K�{�`�F�b�N
		valid.addValidation(new NullOrEmptyCheckValidation());
		// �����`�F�b�N
		int len = this.lengthUtils.getLength(
				"buildingInfo.input.displayBuildingName", 40);
		valid.addValidation(new MaxLengthValidation(len));
		valid.validate(errors);
	}
	
	/**
	 * �\���p�������ӂ肪�� �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validDisplayBuildingNameKana(List<ValidationFailure> errors) {
		// �\���p�������ӂ肪�ȓ��̓`�F�b�N
		ValidationChain valid = new ValidationChain(
				"buildingInfo.input.displayBuildingNameKana",
				this.displayBuildingName);
		// �����`�F�b�N
		int len = this.lengthUtils.getLength(
				"buildingInfo.input.displayBuildingNameKana", 80);
		valid.addValidation(new MaxLengthValidation(len));
		valid.validate(errors);
	}

	/**
	 * ���ݒn�E�X�֔ԍ� �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * �E���l�`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validZip(List<ValidationFailure> errors) {
		// ���ݒn�E�X�֔ԍ����̓`�F�b�N
		ValidationChain valid = new ValidationChain("buildingInfo.input.zip", this.zip);
		// �����`�F�b�N
		int len = this.lengthUtils.getLength("buildingInfo.input.zip", 7);
		
		valid.addValidation(new MaxLengthValidation(len));
		// ���l�`�F�b�N
		valid.addValidation(new NumberValidation());
		valid.validate(errors);
	}

	/**
	 * ���ݒn�E�s���{�� �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validPrefCd(List<ValidationFailure> errors) {
		// ���ݒn�E�X�֔ԍ����̓`�F�b�N
		ValidationChain valid = new ValidationChain(
				"buildingInfo.input.prefCd",
				this.prefCd);
		// �K�{�`�F�b�N
		valid.addValidation(new NullOrEmptyCheckValidation());
		valid.validate(errors);
	}
	
	/**
	 * ���ݒn�E�����Ԓn �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validAddressOther1(List<ValidationFailure> errors) {
		// ���ݒn�E�����Ԓn���̓`�F�b�N
		ValidationChain valid = new ValidationChain("buildingInfo.input.addressOther1", this.addressOther1);
		// �����`�F�b�N
		int len = this.lengthUtils.getLength("buildingInfo.input.addressOther1", 50);
		valid.addValidation(new MaxLengthValidation(len));
		valid.validate(errors);
	}
	
	/**
	 * ���ݒn�E���������̑� �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validAddressOther2(List<ValidationFailure> errors) {
		// ���ݒn�E���������̑����̓`�F�b�N
		ValidationChain valid = new ValidationChain("buildingInfo.input.addressOther2", this.addressOther2);
		// �����`�F�b�N
		int len = this.lengthUtils.getLength("buildingInfo.input.addressOther2", 50);
		valid.addValidation(new MaxLengthValidation(len));
		valid.validate(errors);
	}
	
	/**
	 * ���ݒn�E�v�H�N�� �o���f�[�V����<br/>
	 * �E�v�H�{�����͂��ꂽ�ꍇ�͕K�{
	 * �E���t�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validCompDate(List<ValidationFailure> errors) {

		// ���ݒn�E�v�H�N�����̓`�F�b�N
		ValidationChain valid = new ValidationChain("buildingInfo.input.compDate", this.compDate);

		// �v�H�{�����͂��ꂽ�ꍇ�͕K�{
		if (!StringValidateUtil.isEmpty(this.compTenDays)){
			// �K�{�`�F�b�N
			valid.addValidation(new NullOrEmptyCheckValidation());
		}

		// ���t�����`�F�b�N
		valid.addValidation(new ValidDateValidation("yyyy/MM"));
		valid.validate(errors);
	}

	/**
	 * �v�H�{ �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validCompTenDays(List<ValidationFailure> errors) {
		// �v�H�{���̓`�F�b�N
		ValidationChain valid = new ValidationChain("buildingInfo.input.compTenDays", this.compTenDays);
		// �����`�F�b�N
		int len = this.lengthUtils.getLength("buildingInfo.input.compTenDays", 10);
		valid.addValidation(new MaxLengthValidation(len));
		valid.validate(errors);
	}
	
	/**
	 * ���K�� �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * �E���l�`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validTotalFloors(List<ValidationFailure> errors) {
		// ���K�����̓`�F�b�N
		ValidationChain valid = new ValidationChain("buildingInfo.input.totalFloors", this.totalFloors);
		// �����`�F�b�N
		int len = this.lengthUtils.getLength("buildingInfo.input.totalFloors", 3);
		valid.addValidation(new MaxLengthValidation(len));
		// ���l�`�F�b�N
		valid.addValidation(new NumberValidation());
		valid.validate(errors);
	}

	/**
	 * �����œn���ꂽ������{���̃o���[�I�u�W�F�N�g�Ƀt�H�[���̒l��ݒ肷��B<br/>
	 * �X�V�����ł��g�p���鎖���l�����A�^�C���X�^���v���Ɋւ��ẮA�X�V���A�X�V�҂̂ݐݒ肷��B<br/>
	 * <br/>
	 * @param building �l��ݒ肷�錚����{���̃o���[�I�u�W�F�N�g
	 * 
	 */
	public void copyToBuildingInfo(BuildingInfo building, String editUserId) {

		// �V�X�e������CD��ݒ�
		if (!StringValidateUtil.isEmpty(this.sysBuildingCd)) {
			building.setSysBuildingCd(this.sysBuildingCd);
		}
		
		// �����ԍ���ݒ�	
		building.setBuildingCd(this.buildingCd);

		// �������CD��ݒ�
		building.setHousingKindCd(this.housingKindCd);
		
		// �����\��CD��ݒ�
		building.setStructCd(this.structCd);
		
		// �\���p��������ݒ�
		building.setDisplayBuildingName(this.displayBuildingName);
		
		// �\���p�������ӂ肪�Ȃ�ݒ�
		building.setDisplayBuildingNameKana(this.displayBuildingNameKana);
		
		// ���ݒn�E�X�֔ԍ���ݒ�
		building.setZip(this.zip);
		
		// ���ݒn�E�s���{��CD��ݒ�
		building.setPrefCd(this.prefCd);
		
		// ���ݒn�E�s�撬��CD��ݒ�
		building.setAddressCd(this.addressCd);
		
		// ���ݒn�E�s�撬������ݒ�
		building.setAddressName(this.addressName);
		
		// ���ݒn�E�����Ԓn��ݒ�
		building.setAddressOther1(this.addressOther1);
		
		// ���ݒn�E���������̑���ݒ�
		building.setAddressOther2(this.addressOther2);
		
		// �v�H�N����ݒ� �i���� 01�Œ�j
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		if (!StringValidateUtil.isEmpty(this.compDate)) {
			try {
				building.setCompDate(sdf.parse(this.compDate + "/01"));
			} catch (ParseException e) {
				// �o���f�[�V�����ň��S����S�ۂ��Ă���̂ŁA���̃^�C�~���O�ŃG���[���������鎖�͖������A
				// �ꉞ�A�x�������O�o�͂���B
				log.warn("date format error. (" + this.compDate + ")");
				building.setCompDate(null);
			}
		} else {
			building.setCompDate(null);
		}

		
		// �v�H�{��ݒ�
		building.setCompTenDays(this.compTenDays);
		
		// ���K����ݒ�
		if (!StringValidateUtil.isEmpty(this.totalFloors)) {
			building.setTotalFloors(Integer.valueOf(this.totalFloors));
		} else {
			building.setTotalFloors(null);
		}

		// �X�V���t��ݒ�
		building.setUpdDate(new Date());;

		// �X�V�S���҂�ݒ�
		building.setUpdUserId(editUserId);

	}
	
	/**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� Form �֏����l��ݒ肷��B<br/>
	 * <br/>
	 * @param building Building�@�����������A�������Ǘ��p�o���[�I�u�W�F�N�g
	 * @param prefMst PrefMst�@�����������A�s���{���}�X�^�Ǘ��p�o���[�I�u�W�F�N�g
	 * 
	 */
	public void setDefaultData(BuildingInfo building, PrefMst prefMst) {

		// �V�X�e������CD
		this.sysBuildingCd = building.getSysBuildingCd();
		// �����ԍ� ��ݒ�
		this.buildingCd = building.getBuildingCd();
		// �������CD
		this.housingKindCd = building.getHousingKindCd();
		// �����\��CD
		this.structCd = building.getStructCd();
		// �\���p������
		this.displayBuildingName = building.getDisplayBuildingName();
		// �\���p�������ӂ肪�� 
		this.displayBuildingNameKana = building.getDisplayBuildingNameKana();
		// ���ݒn�E�X�֔ԍ�
		this.zip = building.getZip();
		// ���ݒn�E�s���{��CD
		this.prefCd = building.getPrefCd();
		// ���ݒn�E�s���{����
		this.prefName = prefMst.getPrefName();
		// ���ݒn�E�s�撬��CD
		this.addressCd = building.getAddressCd();
		// ���ݒn�E�s�撬����
		this.addressName = building.getAddressName();
		// ���ݒn�E�����Ԓn
		this.addressOther1 = building.getAddressOther1();
		// ���ݒn�E���������̑�
		this.addressOther2 = building.getAddressOther2();
		// �v�H�N��
		if (building.getCompDate() != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM"); 
			this.compDate = formatter.format(building.getCompDate());
		}
		// �v�H�{
		this.compTenDays = building.getCompTenDays();
		// ���K��
		if (building.getTotalFloors() != null) {
			this.totalFloors = String.valueOf(building.getTotalFloors());
		}
		
	}
}
