package jp.co.transcosmos.dm3.corePana.model.building.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.building.form.BuildingStationInfoForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * ������{��񃁃��e�i���X�̓��̓p�����[�^����p�t�H�[��.
 * <p>
 *
 * <pre>
 * �S����      �C����     �C�����e
 * ------------ ----------- -----------------------------------------------------
 * fan         2015.03.11  �V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */

public class PanaBuildingStationInfoForm extends BuildingStationInfoForm implements Validateable {
    /** ���ʃR�[�h�ϊ����� */
    private CodeLookupManager codeLookupManager;

	/**
     * �p���p<br/>
     * <br/>
     */
    protected PanaBuildingStationInfoForm(){
        super();
    }

    /**
     * �R���X�g���N�^�[<br/>
     * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
     * <br/>
     * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
     */
    protected PanaBuildingStationInfoForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager){
        super();
        this.lengthUtils = lengthUtils;
        this.codeLookupManager = codeLookupManager;
    }

	/**
	 * �����œn���ꂽ�Ŋ��w���̃o���[�I�u�W�F�N�g�Ƀt�H�[���̒l��ݒ肷��B<br/>
	 * �X�V�����ł��g�p���鎖���l�����A�^�C���X�^���v���Ɋւ��ẮA�X�V���A�X�V�҂̂ݐݒ肷��B<br/>
	 * <br/>
	 * @param information �l��ݒ肷�邨�m�点���̃o���[�I�u�W�F�N�g
	 * @param length �Ŋ��w�̃��R�[�h��
	 *
	 */
	@Override
	public void copyToBuildingStationInfo(
			BuildingStationInfo[] buildingStationInfos, int length) {
		// �������Ă���vo�̔ԍ�
		int voIndex = 0;
		// ��ʍs���Ń��[������
		for (int i = 0;i < this.getDefaultRouteCd().length; i++) {
			// �H������͂��Ă��Ȃ��ꍇ�A�o�^���Ȃ�
			if(StringValidateUtil.isEmpty(this.getDefaultRouteCd()[i])) {
				continue;
			}
			buildingStationInfos[voIndex].setSysBuildingCd(this.getSysBuildingCd());
			buildingStationInfos[voIndex].setDivNo(voIndex + 1);
			buildingStationInfos[voIndex].setDefaultRouteCd(this.getDefaultRouteCd()[i]);
			buildingStationInfos[voIndex].setDefaultRouteName(this.getRouteName()[i]);
			buildingStationInfos[voIndex].setStationCd(this.getStationCd()[i]);
			buildingStationInfos[voIndex].setStationName(this.getStationName()[i]);
			buildingStationInfos[voIndex].setBusCompany(this.getBusCompany()[i]);
			buildingStationInfos[voIndex].setSortOrder(i+1);
			if (!StringValidateUtil.isEmpty(this.getTimeFromBusStop()[voIndex])) {
				buildingStationInfos[voIndex].setTimeFromBusStop(Integer.valueOf(this.getTimeFromBusStop()[i]));
			}
			// �S�ē��͂����s�͏����I��
			if (voIndex == length) {
				break;
			}
			voIndex++;
		}
	}
	@Override
	public boolean validate(List<ValidationFailure> errors) {
		int startSize = errors.size();
		return (startSize == errors.size());
	}
}
