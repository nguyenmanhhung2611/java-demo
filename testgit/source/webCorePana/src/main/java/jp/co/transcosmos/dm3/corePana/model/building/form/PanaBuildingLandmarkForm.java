package jp.co.transcosmos.dm3.corePana.model.building.form;

import java.math.BigDecimal;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.building.Building;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingLandmarkForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.BuildingLandmark;
import jp.co.transcosmos.dm3.corePana.util.PanaCalcUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NumericValidation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * <pre>
 * ���������h�}�[�N���̓��̓p�����[�^����p�t�H�[��
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * ������		2015.03.31	�V�K�쐬
 *
 * ���ӎ���
 * �o���f�[�V�������s���̃p�����[�^���ʏ�ƈقȂ�̂Œ��ӂ��鎖�B
 *
 * </pre>
 */
public class PanaBuildingLandmarkForm extends BuildingLandmarkForm {
    /**
     * �p���p<br/>
     * <br/>
     */
    PanaBuildingLandmarkForm() {
        super();
    }

    /**
     * �R���X�g���N�^�[<br/>
     * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
     * <br/>
     * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
     */
    PanaBuildingLandmarkForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager) {
        super();
        this.lengthUtils = lengthUtils;
        this.codeLookupManager = codeLookupManager;
    }


    /** �V�X�e������CD */
    private String sysHousingCd;
    /** �����ԍ� */
    private String housingCd;
    /** �������� */
    private String displayHousingName;

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
	 * �o���f�[�V��������<br/>
	 * ���N�G�X�g�p�����[�^�̃o���f�[�V�������s��<br/>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @return ���펞 true�A�G���[�� false
	 */
	@Override
	public boolean validate(List<ValidationFailure> errors) {
        int startSize = errors.size();

		for (int i = 0; i < this.getLandmarkType().length; i++) {
	        // �����h�}�[�N�̎�ޓ��̓`�F�b�N
			validLandmarkType(errors, i);
	        // �����h�}�[�N�����̓`�F�b�N
			validLandmarkName(errors, i);
	        // �����h�}�[�N����̋������̓`�F�b�N
			validDistanceFromLandmark(errors, i);
			// �����h�}�[�N���ƃ����h�}�[�N����̋����̊֘A�`�F�b�N
			validLandmarkNameAndDistance(errors, i);
		}

        return (startSize == errors.size());
	}

	/**
	 * �����h�}�[�N�̎�ޓ��̓`�F�b�N<br/>
	 * �E �����h�}�[�N�̎�ނ̃p�^�[���`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	private void validLandmarkType(List<ValidationFailure> errors, int i) {
        // �K�͓��̓`�F�b�N
        ValidationChain valid = new ValidationChain("buildingLandmark.input.landmarkType",
        		this.getLandmarkType()[i]);
        // �p�^�[���`�F�b�N
        valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "buildingLandmark_landmarkType"));
        valid.validate(errors);

	}

	/**
	 * �����h�}�[�N���ƃ����h�}�[�N����̋����̊֘A�`�F�b�N<br/>
	 * �E�����h�}�[�N����̋�������͂����ꍇ�A�����h�}�[�N������͂��Ă��Ȃ��ꍇ
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	private void validLandmarkNameAndDistance(List<ValidationFailure> errors,
			int i) {
		String label1 = this.codeLookupManager.lookupValue("buildingLandmark_landmarkType", this.getLandmarkType()[i])  + "�܂ł̋���";
		String label2 =  this.codeLookupManager.lookupValue("buildingLandmark_landmarkType", this.getLandmarkType()[i])  + "��";

		if (!StringValidateUtil.isEmpty(this.getDistanceFromLandmark()[i]) &&
				StringValidateUtil.isEmpty(this.getLandmarkName()[i])) {
        	errors.add(new ValidationFailure("landMarkError", label1, label2, null));
		}

	}

	/**
	 * �����h�}�[�N�� �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	@Override
	protected void validLandmarkName(List<ValidationFailure> errors, int i) {
        // �����h�}�[�N�� ���̓`�F�b�N
		String label = this.codeLookupManager.lookupValue("buildingLandmark_landmarkType", this.getLandmarkType()[i])  + "��_�l";
        ValidationChain valid = new ValidationChain(label, this.getLandmarkName()[i]);
        // �����`�F�b�N
        valid.addValidation(new MaxLengthValidation(50));
        valid.validate(errors);
	}


	/**
	 * �����h�}�[�N����̋��� �o���f�[�V����<br/>
	 * �EDECIMAL�`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	@Override
	protected void validDistanceFromLandmark(List<ValidationFailure> errors, int i) {
		int errorSize = errors.size();
        // �����h�}�[�N����̋��� ���̓`�F�b�N
		String label = this.codeLookupManager.lookupValue("buildingLandmark_landmarkType", this.getLandmarkType()[i]) + "_����";
        ValidationChain valid = new ValidationChain(label, this.getDistanceFromLandmark()[i]);
        // ���p�����`�F�b�N
        valid.addValidation(new NumericValidation());
        // �����`�F�b�N
        valid.addValidation(new MaxLengthValidation(5));
        valid.validate(errors);
        if (errorSize == errors.size()) {
        	if (PanaStringUtils.toInteger(this.getDistanceFromLandmark()[i]) != null &&
        			PanaStringUtils.toInteger(this.getDistanceFromLandmark()[i]) <= 0) {
            	errors.add(new ValidationFailure("landMarkDistanceError", label,  null, null));
        	}
        }

	}



	/**
	 * �����œn���ꂽ���������h�}�[�N���̃o���[�I�u�W�F�N�g�Ƀt�H�[���̒l��ݒ肷��B<br/>
	 * �X�V�����ł��g�p���鎖���l�����A�^�C���X�^���v���Ɋւ��ẮA�X�V���A�X�V�҂̂ݐݒ肷��B<br/>
	 * <br/>
	 * @param buildingLandmark[] �l��ݒ肷�錚�������h�}�[�N���̃o���[�I�u�W�F�N�g
	 * @param length ���������h�}�[�N�̃��R�[�h��
	 *
	 */
	@Override
	public void copyToBuildingLandmark(BuildingLandmark[] buildingLandmarks, int length) {

		// �������Ă���vo�̔ԍ�
		int voIndex = 0;
		for (int i = 0; i < this.getLandmarkType().length; i++) {
			// �n�於����͂��Ă��Ȃ��ꍇ�A�o�^���Ȃ�
			if(StringValidateUtil.isEmpty(this.getLandmarkName()[i])) {
				continue;
			}
			buildingLandmarks[voIndex].setSysBuildingCd(this.getSysBuildingCd());
			buildingLandmarks[voIndex].setDivNo(voIndex + 1);
			buildingLandmarks[voIndex].setLandmarkType(this.getLandmarkType()[i]);
			buildingLandmarks[voIndex].setWayFromLandmark("3");
			buildingLandmarks[voIndex].setSortOrder(null);
			buildingLandmarks[voIndex].setLandmarkName(this.getLandmarkName()[i]);
			buildingLandmarks[voIndex].setTimeFromLandmark(null);
			if (!StringValidateUtil.isEmpty(this.getDistanceFromLandmark()[i])) {
				buildingLandmarks[voIndex].setDistanceFromLandmark(new BigDecimal(this.getDistanceFromLandmark()[i]));
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
	 * �����h�}�[�N����̓k�����Ԃ͍Čv�Z�Ƃ���B
	 * <br/>
	 * @param building Building�@�擾����������񃊃X�g
	 *
	 */
	@Override
	public void setDefaultData(Building building) {
		super.setDefaultData(building);
		// �n���񃊃X�g���擾����
		List<BuildingLandmark> buildingLandmarkList = building.getBuildingLandmarkList();

		for(int i = 0;i<buildingLandmarkList.size();i ++) {
			BuildingLandmark buildingLandmark = buildingLandmarkList.get(i);
			// �����h�}�[�N����̓k������
			if (buildingLandmark.getDistanceFromLandmark() != null) {
				this.getDistanceFromLandmark()[i] = String.valueOf(buildingLandmark.getDistanceFromLandmark().intValue());
				BigDecimal time = PanaCalcUtil.calcLandMarkTime(buildingLandmark.getDistanceFromLandmark());
				if (time != null) {
					this.getTimeFromLandmark()[i] = String.valueOf(time);
				}
			} else {
				this.getTimeFromLandmark()[i] = null;
			}
		}

	}
}
