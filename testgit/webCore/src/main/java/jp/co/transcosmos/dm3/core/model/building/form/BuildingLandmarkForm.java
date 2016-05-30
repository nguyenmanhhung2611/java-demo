package jp.co.transcosmos.dm3.core.model.building.form;

import java.math.BigDecimal;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.building.Building;
import jp.co.transcosmos.dm3.core.model.building.BuildingManageImpl;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.validation.LineAdapter;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingLandmark;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.DecimalValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.NumericValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * <pre>
 * ���������h�}�[�N���̓��̓p�����[�^����p�t�H�[��
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.03.17	�V�K�쐬
 *
 * ���ӎ���
 * �o���f�[�V�������s���̃p�����[�^���ʏ�ƈقȂ�̂Œ��ӂ��鎖�B
 *
 * </pre>
 */
public class BuildingLandmarkForm implements Validateable {
	
	/** command �p�����[�^ */
	private String command;
	
	/** �V�X�e������CD */
	private String sysBuildingCd;
	/** �����ԍ� */
	private String buildingCd;
	/** �������� */
	private String displayBuildingName;
	/** �}�� */
	private String divNo[];
	/** �����h�}�[�N�̎�� */
	private String landmarkType[];
	/** �����h�}�[�N����̎�i */
	private String wayFromLandmark[];
	/** �\���� */
	private String sortOrder[];
	/** �����h�}�[�N�� */
	private String landmarkName[];
	/** �����h�}�[�N����̓k������ */
	private String timeFromLandmark[];
	/** �����h�}�[�N����̋��� */
	private String distanceFromLandmark[];
	
	/** �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B */
	protected LengthValidationUtils lengthUtils;
	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;
	
	/**
	 * �p���p<br/>
	 * <br/>
	 */
	protected BuildingLandmarkForm() {
		super();
	}
	
	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
	 */
	protected BuildingLandmarkForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager){
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
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
	 * �}�Ԃ��擾����B<br/>
	 * <br/>
	 * @return �}��
	 */
	public String[] getDivNo() {
		return divNo;
	}

	/**
	 * �}�Ԃ�ݒ肷��B<br/>
	 * <br/>
	 * @param divNo �}��
	 */
	public void setDivNo(String[] divNo) {
		this.divNo = divNo;
	}

	/**
	 * �����h�}�[�N�̎�ނ��擾����B<br/>
	 * <br/>
	 * @return �����h�}�[�N�̎��
	 */
	public String[] getLandmarkType() {
		return landmarkType;
	}

	/**
	 * �����h�}�[�N�̎�ނ�ݒ肷��B<br/>
	 * <br/>
	 * @param landmarkType �����h�}�[�N�̎��
	 */
	public void setLandmarkType(String[] landmarkType) {
		this.landmarkType = landmarkType;
	}

	/**
	 * �����h�}�[�N����̎�i���擾����B<br/>
	 * <br/>
	 * @return �����h�}�[�N����̎�i
	 */
	public String[] getWayFromLandmark() {
		return wayFromLandmark;
	}

	/**
	 * �����h�}�[�N����̎�i��ݒ肷��B<br/>
	 * <br/>
	 * @param wayFromLandmark �����h�}�[�N����̎�i
	 */
	public void setWayFromLandmark(String[] wayFromLandmark) {
		this.wayFromLandmark = wayFromLandmark;
	}

	/**
	 * �\�������擾����B<br/>
	 * <br/>
	 * @return �\����
	 */
	public String[] getSortOrder() {
		return sortOrder;
	}

	/**
	 * �\������ݒ肷��B<br/>
	 * <br/>
	 * @param sortOrder �\����
	 */
	public void setSortOrder(String[] sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * �����h�}�[�N�����擾����B<br/>
	 * <br/>
	 * @return �����h�}�[�N��
	 */
	public String[] getLandmarkName() {
		return landmarkName;
	}

	/**
	 * �����h�}�[�N����ݒ肷��B<br/>
	 * <br/>
	 * @param landmarkName �����h�}�[�N��
	 */
	public void setLandmarkName(String[] landmarkName) {
		this.landmarkName = landmarkName;
	}

	/**
	 * �����h�}�[�N����̓k�����Ԃ��擾����B<br/>
	 * <br/>
	 * @return �����h�}�[�N����̓k������
	 */
	public String[] getTimeFromLandmark() {
		return timeFromLandmark;
	}

	/**
	 * �����h�}�[�N����̓k�����Ԃ�ݒ肷��B<br/>
	 * <br/>
	 * @param timeFromLandmark �����h�}�[�N����̓k������
	 */
	public void setTimeFromLandmark(String[] timeFromLandmark) {
		this.timeFromLandmark = timeFromLandmark;
	}

	/**
	 * �����h�}�[�N����̋������擾����B<br/>
	 * <br/>
	 * @return �����h�}�[�N����̋���
	 */
	public String[] getDistanceFromLandmark() {
		return distanceFromLandmark;
	}

	/**
	 * �����h�}�[�N����̋�����ݒ肷��B<br/>
	 * <br/>
	 * @param distanceFromLandmark �����h�}�[�N����̋���
	 */
	public void setDistanceFromLandmark(String[] distanceFromLandmark) {
		this.distanceFromLandmark = distanceFromLandmark;
	}
	
	/**
	 * �����œn���ꂽ���������h�}�[�N���̃o���[�I�u�W�F�N�g�Ƀt�H�[���̒l��ݒ肷��B<br/>
	 * �X�V�����ł��g�p���鎖���l�����A�^�C���X�^���v���Ɋւ��ẮA�X�V���A�X�V�҂̂ݐݒ肷��B<br/>
	 * <br/>
	 * @param buildingLandmark[] �l��ݒ肷�錚�������h�}�[�N���̃o���[�I�u�W�F�N�g
	 * @param length ���������h�}�[�N�̃��R�[�h��
	 * 
	 */
	public void copyToBuildingLandmark(BuildingLandmark[] buildingLandmarks, int length) {

		// �������Ă���vo�̔ԍ�
		int voIndex = 0;
		for (int i = 0; i < this.landmarkType.length; i++) {
			// �n�於����͂��Ă��Ȃ��ꍇ�A�o�^���Ȃ�
			if(StringValidateUtil.isEmpty(this.landmarkName[i])) {
				continue;
			}
			buildingLandmarks[voIndex].setSysBuildingCd(this.sysBuildingCd);
			buildingLandmarks[voIndex].setDivNo(voIndex + 1);
			buildingLandmarks[voIndex].setLandmarkType(this.landmarkType[i]);
			buildingLandmarks[voIndex].setWayFromLandmark(this.wayFromLandmark[i]);
			if (!StringValidateUtil.isEmpty(this.sortOrder[i])) {
				buildingLandmarks[voIndex].setSortOrder(Integer.valueOf(this.sortOrder[i]));
			}
			buildingLandmarks[voIndex].setLandmarkName(this.landmarkName[i]);
			if (!StringValidateUtil.isEmpty(this.timeFromLandmark[i])) {
				buildingLandmarks[voIndex].setTimeFromLandmark(Integer.valueOf(this.timeFromLandmark[i]));
			}
			if (!StringValidateUtil.isEmpty(this.distanceFromLandmark[i])) {
				buildingLandmarks[voIndex].setDistanceFromLandmark(new BigDecimal(this.distanceFromLandmark[i]));
			}
			
			// �S�ē��͂����s�͏����I��
			if (voIndex == length) {
				break;
			}
			voIndex++;
			
		}
		
	}

	/**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� Form �֏����l��ݒ肷��B<br/>
	 * <br/>
	 * @param building Building�@�擾����������񃊃X�g
	 * 
	 */
	public void setDefaultData(Building building) {
		
		// �n���񃊃X�g���擾����
		List<BuildingLandmark> buildingLandmarkList = building.getBuildingLandmarkList();
		
        // �����ԍ���ݒ肷��
		this.buildingCd = ((BuildingInfo) building.getBuildingInfo()
				.getItems().get(BuildingManageImpl.BUILDING_INFO_ALIA))
				.getBuildingCd();
		// �������̂�ݒ肷��
		this.displayBuildingName = ((BuildingInfo) building.getBuildingInfo()
				.getItems().get(BuildingManageImpl.BUILDING_INFO_ALIA))
				.getDisplayBuildingName();
		// �z�񏉊���
		creatBuildingStationInfoForm(buildingLandmarkList.size());
		for(int i = 0;i<buildingLandmarkList.size();i ++) {
			BuildingLandmark buildingLandmark = buildingLandmarkList.get(i);
			// �}��
			if (buildingLandmark.getDivNo() != null) {
				this.divNo[i] = String.valueOf(buildingLandmark.getDivNo());
			}
			// �����h�}�[�N�̎��
			this.landmarkType[i] = buildingLandmark.getLandmarkType();
			// �����h�}�[�N����̎�i
			this.wayFromLandmark[i] = buildingLandmark.getWayFromLandmark();
			// �\����
			if (buildingLandmark.getSortOrder() != null) {
				this.sortOrder[i] = String.valueOf(buildingLandmark.getSortOrder());
			}
			// �����h�}�[�N��
			this.landmarkName[i] = buildingLandmark.getLandmarkName();
			// �����h�}�[�N����̓k������
			if (buildingLandmark.getTimeFromLandmark() != null) {
				this.timeFromLandmark[i] = String.valueOf(buildingLandmark.getTimeFromLandmark());
			}
			// �����h�}�[�N����̋���
			if (buildingLandmark.getDistanceFromLandmark() != null) {
				this.distanceFromLandmark[i] = buildingLandmark.getDistanceFromLandmark().toString();
			}
		}

	}
	
	/**
	 * �n���ꂽ�T�C�Y�� Form�̔z���ݒ肷��B<br/>
	 * <br/>
	 * @param size int�@�z��̃T�C�Y
	 * 
	 */
	protected void creatBuildingStationInfoForm(int size) {
		this.divNo = new String[size];
		this.landmarkType = new String[size];
		this.wayFromLandmark = new String[size];
		this.sortOrder = new String[size];
		this.landmarkName = new String[size];
		this.timeFromLandmark = new String[size];
		this.distanceFromLandmark = new String[size];
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
	 * �������̂��擾����B<br/>
	 * <br/>
	 * @return ��������
	 */
	public String getDisplayBuildingName() {
		return displayBuildingName;
	}

	/**
	 * �������̂�ݒ肷��B<br/>
	 * <br/>
	 * @param displayBuildingName ��������
	 */
	public void setDisplayBuildingName(String displayBuildingName) {
		this.displayBuildingName = displayBuildingName;
	}

	/**
	 * �o���f�[�V��������<br/>
	 * ���N�G�X�g�p�����[�^�̃o���f�[�V�������s��<br/>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @return ���펞 true�A�G���[�� false
	 */
	@Override
	public boolean validate(List<ValidationFailure> errors) {
        int startSize = errors.size();
        
		for (int i = 0; i < landmarkType.length; i++) {
	        // �����h�}�[�N�����̓`�F�b�N
			validLandmarkName(errors, i);
	        // �����h�}�[�N����̎�i���̓`�F�b�N
			validWayFromLandmark(errors, i);
	        // �����h�}�[�N����̓k������
			validTimeFromLandmark(errors, i);
	        // �����h�}�[�N����̋������̓`�F�b�N
			validDistanceFromLandmark(errors, i);
	        // �\�������̓`�F�b�N
			validSortOrder(errors, i);
		}
        
        return (startSize == errors.size());
	}
	
	/**
	 * �����h�}�[�N�� �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validLandmarkName(List<ValidationFailure> errors, int i) {
        // �����h�}�[�N�� ���̓`�F�b�N
        ValidationChain valid = new ValidationChain("buildingLandmark.input.landmarkName", this.landmarkName[i]);
		// �K�{�`�F�b�N
		if (!StringValidateUtil.isEmpty(this.wayFromLandmark[i])
				|| !StringValidateUtil.isEmpty(this.timeFromLandmark[i])
				|| !StringValidateUtil.isEmpty(this.distanceFromLandmark[i])
				|| !StringValidateUtil.isEmpty(this.sortOrder[i])) {
			valid.addValidation(new LineAdapter(new NullOrEmptyCheckValidation(), i + 1));
		}
        // �����`�F�b�N
        int len = this.lengthUtils.getLength("buildingLandmark.input.landmarkName", 80);
        valid.addValidation(new LineAdapter(new MaxLengthValidation(len), i + 1));
        valid.validate(errors);
	}

	/**
	 * �����h�}�[�N����̎�i �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * �E�p�^�[���`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validWayFromLandmark(List<ValidationFailure> errors, int i) {
        // �����h�}�[�N����̎�i ���̓`�F�b�N
        ValidationChain valid = new ValidationChain("buildingLandmark.input.wayFromLandmark", this.wayFromLandmark[i]);
		// �K�{�`�F�b�N
		if (!StringValidateUtil.isEmpty(this.landmarkName[i])
				|| !StringValidateUtil.isEmpty(this.timeFromLandmark[i])
				|| !StringValidateUtil.isEmpty(this.distanceFromLandmark[i])
				|| !StringValidateUtil.isEmpty(this.sortOrder[i])) {
			valid.addValidation(new LineAdapter(new NullOrEmptyCheckValidation(), i + 1));
	        // �p�^�[���`�F�b�N
	        valid.addValidation(new CodeLookupValidation(this.codeLookupManager,"buildingStationInfo_wayFromStation"));
		}
        valid.validate(errors);
	}
	
	/**
	 * �����h�}�[�N����̓k������ �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validTimeFromLandmark(List<ValidationFailure> errors, int i) {
        // �����h�}�[�N����̓k������ ���̓`�F�b�N
        ValidationChain valid = new ValidationChain("buildingLandmark.input.timeFromLandmark", this.timeFromLandmark[i]);
        // �����`�F�b�N
        int len = this.lengthUtils.getLength("buildingLandmark.input.timeFromLandmark", 3);
        valid.addValidation(new LineAdapter(new MaxLengthValidation(len), i + 1));
        // �����`�F�b�N
        valid.addValidation(new LineAdapter(new NumericValidation(), i + 1));
        valid.validate(errors);
       
	}
	
	/**
	 * �����h�}�[�N����̋��� �o���f�[�V����<br/>
	 * �EDECIMAL�`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validDistanceFromLandmark(List<ValidationFailure> errors, int i) {
        // �����h�}�[�N����̋��� ���̓`�F�b�N
        ValidationChain valid = new ValidationChain("buildingLandmark.input.distanceFromLandmark", this.distanceFromLandmark[i]);
        // DECIMAL�`�F�b�N
        int num = this.lengthUtils.getLength("buildingLandmark.input.distanceFromLandmarkNum", 7);
        int dec = this.lengthUtils.getLength("buildingLandmark.input.distanceFromLandmarkDec", 2);
        valid.addValidation(new LineAdapter(new DecimalValidation(num, dec), i + 1));
        valid.validate(errors);
        
	}
	
	/**
	 * �\�������� �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validSortOrder(List<ValidationFailure> errors, int i) {
        // �\�������� ���̓`�F�b�N
        ValidationChain valid = new ValidationChain("buildingLandmark.input.sortOrder", this.sortOrder[i]);
        // �����`�F�b�N
        int len = this.lengthUtils.getLength("buildingLandmark.input.sortOrder", 3);
        valid.addValidation(new LineAdapter(new MaxLengthValidation(len), i + 1));
        // �����`�F�b�N
        valid.addValidation(new LineAdapter(new NumericValidation(), i + 1));
        valid.validate(errors);
       
	}
	
}
