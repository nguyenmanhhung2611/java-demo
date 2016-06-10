package jp.co.transcosmos.dm3.core.model.building.form;

import java.math.BigDecimal;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.building.BuildingManageImpl;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.validation.LineAdapter;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.dao.JoinResult;
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
 * ������񃁃��e�i���X�̓��̓p�����[�^����p�t�H�[��
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.03.09	�V�K�쐬
 *
 * ���ӎ���
 * �o���f�[�V�������s���̃p�����[�^���ʏ�ƈقȂ�̂Œ��ӂ��鎖�B
 *
 * </pre>
 */
public class BuildingStationInfoForm implements Validateable {
	
	/** command �p�����[�^ */
	private String command;
	
	/** �V�X�e������CD */
	private String sysBuildingCd;
	/** ���ݒn�E�s���{��CD */
	private String prefCd;
	/** �}�� */
	private String divNo[];
	/** �\���� */
	private String sortOrder[];
	/** ��\�H��CD */
	private String defaultRouteCd[];
	/** �wCD */
	private String stationCd[];
	/** �Ŋ��w����̎�i */
	private String wayFromStation[];
	/** �w����̋��� */
	private String distanceFromStation[];
	/** �w����̓k������ */
	private String timeFromStation[];
	/** �o�X��Ж� */
	private String busCompany[];
	/** �o�X�̏��v���� */
	private String busRequiredTime[];
	/** �o�X�▼ */
	private String busStopName[];
	/** �o�X�₩��̓k������ */
	private String timeFromBusStop[];
	/** �H���� */
	private String routeName[];
	/** �w�� */
	private String stationName[];
	
	
	/** �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B */
	protected LengthValidationUtils lengthUtils;

	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;
	
	/**
	 * �p���p<br/>
	 * <br/>
	 */
	protected BuildingStationInfoForm(){
		super();
	}
	
	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
	 */
	protected BuildingStationInfoForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager){
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
	 * ��\�H��CD���擾����B<br/>
	 * <br/>
	 * @return ��\�H��CD
	 */
	public String[] getDefaultRouteCd() {
		return defaultRouteCd;
	}
	/**
	 * ��\�H��CD��ݒ肷��B<br/>
	 * <br/>
	 * @param defaultRouteCd ��\�H��CD
	 */
	public void setDefaultRouteCd(String[] defaultRouteCd) {
		this.defaultRouteCd = defaultRouteCd;
	}
	/**
	 * �wCD���擾����B<br/>
	 * <br/>
	 * @return �wCD
	 */
	public String[] getStationCd() {
		return stationCd;
	}
	/**
	 * �wCD��ݒ肷��B<br/>
	 * <br/>
	 * @param stationCd �wCD
	 */
	public void setStationCd(String[] stationCd) {
		this.stationCd = stationCd;
	}


	/**
	 * �Ŋ��w����̎�i���擾����B<br/>
	 * <br/>
	 * @return �Ŋ��w����̎�i
	 */
	public String[] getWayFromStation() {
		return wayFromStation;
	}


	/**
	 * �Ŋ��w����̎�i��ݒ肷��B<br/>
	 * <br/>
	 * @param wayFromStation �Ŋ��w����̎�i
	 */
	public void setWayFromStation(String[] wayFromStation) {
		this.wayFromStation = wayFromStation;
	}


	/**
	 * �w����̋������擾����B<br/>
	 * <br/>
	 * @return �w����̋���
	 */
	public String[] getDistanceFromStation() {
		return distanceFromStation;
	}


	/**
	 * �w����̋�����ݒ肷��B<br/>
	 * <br/>
	 * @param distanceFromStation �w����̋���
	 */
	public void setDistanceFromStation(String[] distanceFromStation) {
		this.distanceFromStation = distanceFromStation;
	}


	/**
	 * �w����̓k�����Ԃ��擾����B<br/>
	 * <br/>
	 * @return �w����̓k������
	 */
	public String[] getTimeFromStation() {
		return timeFromStation;
	}


	/**
	 * �w����̓k�����Ԃ�ݒ肷��B<br/>
	 * <br/>
	 * @param timeFromStation �w����̓k������
	 */
	public void setTimeFromStation(String[] timeFromStation) {
		this.timeFromStation = timeFromStation;
	}


	/**
	 * �o�X��Ж����擾����B<br/>
	 * <br/>
	 * @return �o�X��Ж�
	 */
	public String[] getBusCompany() {
		return busCompany;
	}


	/**
	 * �o�X��Ж���ݒ肷��B<br/>
	 * <br/>
	 * @param busCompany �o�X��Ж�
	 */
	public void setBusCompany(String[] busCompany) {
		this.busCompany = busCompany;
	}


	/**
	 * �o�X�̏��v���Ԃ��擾����B<br/>
	 * <br/>
	 * @return �o�X�̏��v����
	 */
	public String[] getBusRequiredTime() {
		return busRequiredTime;
	}


	/**
	 * �o�X�̏��v���Ԃ�ݒ肷��B<br/>
	 * <br/>
	 * @param busRequiredTime �o�X�̏��v����
	 */
	public void setBusRequiredTime(String[] busRequiredTime) {
		this.busRequiredTime = busRequiredTime;
	}


	/**
	 * �o�X�▼���擾����B<br/>
	 * <br/>
	 * @return �o�X�▼
	 */
	public String[] getBusStopName() {
		return busStopName;
	}


	/**
	 * �o�X�▼��ݒ肷��B<br/>
	 * <br/>
	 * @param busStopName �o�X�▼
	 */
	public void setBusStopName(String[] busStopName) {
		this.busStopName = busStopName;
	}


	/**
	 * �o�X�₩��̓k�����Ԃ��擾����B<br/>
	 * <br/>
	 * @return �o�X�₩��̓k������
	 */
	public String[] getTimeFromBusStop() {
		return timeFromBusStop;
	}


	/**
	 * �o�X�₩��̓k�����Ԃ�ݒ肷��B<br/>
	 * <br/>
	 * @param timeFromBusStop �o�X�₩��̓k������
	 */
	public void setTimeFromBusStop(String[] timeFromBusStop) {
		this.timeFromBusStop = timeFromBusStop;
	}


	/**
	 * �H�������擾����B<br/>
	 * <br/>
	 * @return �H����
	 */
	public String[] getRouteName() {
		return routeName;
	}


	/**
	 * �H������ݒ肷��B<br/>
	 * <br/>
	 * @param routeName �H����
	 */
	public void setRouteName(String[] routeName) {
		this.routeName = routeName;
	}


	/**
	 * �w�����擾����B<br/>
	 * <br/>
	 * @return �w��
	 */
	public String[] getStationName() {
		return stationName;
	}


	/**
	 * �w����ݒ肷��B<br/>
	 * <br/>
	 * @param stationName �w��
	 */
	public void setStationName(String[] stationName) {
		this.stationName = stationName;
	}

	/**
	 * �����œn���ꂽ�Ŋ��w���̃o���[�I�u�W�F�N�g�Ƀt�H�[���̒l��ݒ肷��B<br/>
	 * �X�V�����ł��g�p���鎖���l�����A�^�C���X�^���v���Ɋւ��ẮA�X�V���A�X�V�҂̂ݐݒ肷��B<br/>
	 * <br/>
	 * @param information �l��ݒ肷�邨�m�点���̃o���[�I�u�W�F�N�g
	 * @param length �Ŋ��w�̃��R�[�h��
	 * 
	 */
	public void copyToBuildingStationInfo(
			BuildingStationInfo[] buildingStationInfos, int length) {

		// �������Ă���vo�̔ԍ�
		int voIndex = 0;
		// ��ʍs���Ń��[������
		for (int i = 0;i < this.defaultRouteCd.length; i++) {
			// �H������͂��Ă��Ȃ��ꍇ�A�o�^���Ȃ�
			if(StringValidateUtil.isEmpty(this.defaultRouteCd[i])) {
				continue;
			}
			buildingStationInfos[voIndex].setSysBuildingCd(this.sysBuildingCd);
			buildingStationInfos[voIndex].setDivNo(voIndex + 1);
			if (!StringValidateUtil.isEmpty(this.sortOrder[i])) {
				buildingStationInfos[voIndex].setSortOrder(Integer.valueOf(this.sortOrder[i]));
			}
			buildingStationInfos[voIndex].setDefaultRouteCd(this.defaultRouteCd[i]);
			buildingStationInfos[voIndex].setDefaultRouteName(this.routeName[i]);
			buildingStationInfos[voIndex].setStationCd(this.stationCd[i]);
			buildingStationInfos[voIndex].setStationName(this.stationName[i]);
			buildingStationInfos[voIndex].setWayFromStation(this.wayFromStation[i]);
			if (!StringValidateUtil.isEmpty(this.distanceFromStation[i])) {
				buildingStationInfos[voIndex].setDistanceFromStation(new BigDecimal(this.distanceFromStation[i]));
			}
			if (!StringValidateUtil.isEmpty(this.timeFromStation[i])) {
				buildingStationInfos[voIndex].setTimeFromStation(Integer.valueOf(this.timeFromStation[i]));
			}
			buildingStationInfos[voIndex].setBusCompany(this.busCompany[i]);
			if (!StringValidateUtil.isEmpty(this.busRequiredTime[i])) {
				buildingStationInfos[voIndex].setBusRequiredTime(Integer.valueOf(this.busRequiredTime[i]));
			}
			buildingStationInfos[voIndex].setBusStopName(this.busStopName[i]);
			if (!StringValidateUtil.isEmpty(this.timeFromBusStop[i])) {
				buildingStationInfos[voIndex].setTimeFromBusStop(Integer.valueOf(this.timeFromBusStop[i]));
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
	 * @param buildingStationInfoList List<JoinResult>�@�擾�����Ŋ��w��񃊃X�g
	 * 
	 */
	public void setDefaultData(List<JoinResult> buildingStationInfoList) {

		// �z�񏉊���
		creatBuildingStationInfoForm(buildingStationInfoList.size());
		for (int i = 0; i < buildingStationInfoList.size(); i++) {
			BuildingStationInfo buildingStationInfo = (BuildingStationInfo)buildingStationInfoList.get(i).getItems().get(BuildingManageImpl.BUILDING_STATION_INFO_ALIA);
			RouteMst routeMst = (RouteMst)buildingStationInfoList.get(i).getItems().get(BuildingManageImpl.ROUTE_MST);
			StationMst stationMst = (StationMst)buildingStationInfoList.get(i).getItems().get(BuildingManageImpl.STATION_MST);

			// �}��
			if (buildingStationInfo.getDivNo() != null) {
				this.divNo[i] = String.valueOf(buildingStationInfo.getDivNo());
			}
			// �\����
			if (buildingStationInfo.getSortOrder() != null) {
				this.sortOrder[i] = String.valueOf(buildingStationInfo.getSortOrder());
			}
			
			// ��\�H��CD
			this.defaultRouteCd[i] = buildingStationInfo.getDefaultRouteCd();
			// �wCD
			this.stationCd[i] = buildingStationInfo.getStationCd();
			// �Ŋ��w����̎�i
			this.wayFromStation[i] = buildingStationInfo.getWayFromStation();
			// �w����̋���
			if (buildingStationInfo.getDistanceFromStation() != null) {
				this.distanceFromStation[i] = buildingStationInfo.getDistanceFromStation().toString();
			}
			// �w����̓k������
			if (buildingStationInfo.getTimeFromStation() != null) {
				this.timeFromStation[i] = String.valueOf(buildingStationInfo.getTimeFromStation());
			}
			// �o�X��Ж�
			this.busCompany[i] = buildingStationInfo.getBusCompany();
			// �w����̓k������
			if (buildingStationInfo.getBusRequiredTime() != null) {
				this.busRequiredTime[i] = String.valueOf(buildingStationInfo.getBusRequiredTime());
			}
			// �o�X�▼
			this.busStopName[i] = buildingStationInfo.getBusStopName();
			// �w����̓k������
			if (buildingStationInfo.getTimeFromBusStop() != null) {
				this.timeFromBusStop[i] = String.valueOf(buildingStationInfo.getTimeFromBusStop());
			}
			// �H����
			this.routeName[i] = routeMst.getRouteName();
			
			// �w��
			this.stationName[i] = stationMst.getStationName();
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
		this.defaultRouteCd = new String[size];
		this.stationCd = new String[size];
		this.wayFromStation = new String[size];
		this.distanceFromStation = new String[size];
		this.timeFromStation = new String[size];
		this.busCompany = new String[size];
		this.busRequiredTime = new String[size];
		this.busStopName = new String[size];
		this.timeFromBusStop = new String[size];
		this.routeName = new String[size];
		this.stationName = new String[size];
		this.sortOrder = new String[size];
		
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
	 * �o���f�[�V��������<br/>
	 * ���N�G�X�g�p�����[�^�̃o���f�[�V�������s��<br/>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @return ���펞 true�A�G���[�� false
	 */
	@Override
	public boolean validate(List<ValidationFailure> errors) {
        int startSize = errors.size();
        
		for (int i = 0; i < sortOrder.length; i++) {
	        // �H�����̓`�F�b�N
			validDefaultRouteCd(errors, i);
	        // �w���̓`�F�b�N
			validStationCd(errors, i);
	        // �Ŋ��w����̎�i
			validWayFromStation(errors, i);
	        // �w����̋������̓`�F�b�N
			validDistanceFromStation(errors, i);
	        // �w����̓k�����ԓ��̓`�F�b�N
			validTimeFromStation(errors, i);
	        // �o�X��Ж����̓`�F�b�N
			validBusCompany(errors, i);
	        // �o�X�̏��v���ԓ��̓`�F�b�N
			validBusRequiredTime(errors, i);
	        // �o�X�▼���̓`�F�b�N
			validBusStopName(errors, i);
	        // �o�X�₩��̓k�����ԓ��̓`�F�b�N
			validTimeFromBusStop(errors, i);
	        // �\�������̓`�F�b�N
			validSortOrder(errors, i);
		}
        
        return (startSize == errors.size());
	}	
	
	/**
	 * �H�� �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validDefaultRouteCd(List<ValidationFailure> errors, int i) {
        // �H�� ���̓`�F�b�N
        ValidationChain valid = new ValidationChain("buildingStationInfo.input.defaultRouteCd", this.defaultRouteCd[i]);
		// �K�{�`�F�b�N
		if (!StringValidateUtil.isEmpty(this.stationCd[i])
				|| !StringValidateUtil.isEmpty(this.wayFromStation[i])
				|| !StringValidateUtil.isEmpty(this.distanceFromStation[i])
				|| !StringValidateUtil.isEmpty(this.timeFromStation[i])
				|| !StringValidateUtil.isEmpty(this.busCompany[i])
				|| !StringValidateUtil.isEmpty(this.busRequiredTime[i])
				|| !StringValidateUtil.isEmpty(this.busStopName[i])
				|| !StringValidateUtil.isEmpty(this.timeFromBusStop[i])
				|| !StringValidateUtil.isEmpty(this.sortOrder[i])) {
			valid.addValidation(new LineAdapter(new NullOrEmptyCheckValidation(), i + 1));
		}
        valid.validate(errors);
	}
	
	/**
	 * �w �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validStationCd(List<ValidationFailure> errors, int i) {
        // �w ���̓`�F�b�N
        ValidationChain valid = new ValidationChain("buildingStationInfo.input.stationCd", this.stationCd[i]);
		// �K�{�`�F�b�N
		if (!StringValidateUtil.isEmpty(this.defaultRouteCd[i])
				|| !StringValidateUtil.isEmpty(this.wayFromStation[i])
				|| !StringValidateUtil.isEmpty(this.distanceFromStation[i])
				|| !StringValidateUtil.isEmpty(this.timeFromStation[i])
				|| !StringValidateUtil.isEmpty(this.busCompany[i])
				|| !StringValidateUtil.isEmpty(this.busRequiredTime[i])
				|| !StringValidateUtil.isEmpty(this.busStopName[i])
				|| !StringValidateUtil.isEmpty(this.timeFromBusStop[i])
				|| !StringValidateUtil.isEmpty(this.sortOrder[i])) {
			valid.addValidation(new LineAdapter(new NullOrEmptyCheckValidation(), i + 1));
		}
        valid.validate(errors);
	}

	/**
	 * �w����̋��� �o���f�[�V����<br/>
	 * �EDECIMAL�`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validDistanceFromStation(List<ValidationFailure> errors, int i) {
        // �����ԍ����̓`�F�b�N
        ValidationChain valid = new ValidationChain("buildingStationInfo.input.distanceFromStation", this.distanceFromStation[i]);
        // DECIMAL�`�F�b�N
        int num = this.lengthUtils.getLength("buildingStationInfo.input.distanceFromStationNum", 7);
        int dec = this.lengthUtils.getLength("buildingStationInfo.input.distanceFromStationDec", 2);
        valid.addValidation(new LineAdapter(new DecimalValidation(num, dec), i + 1));
        valid.validate(errors);
	}
	
	
	/**
	 * �Ŋ��w����̎�i �o���f�[�V����<br/>
	 * �E�p�^�[���`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validWayFromStation(List<ValidationFailure> errors, int i) {
        // �Ŋ��w����̎�i�`�F�b�N
        ValidationChain valid = new ValidationChain("buildingStationInfo.input.wayFromStation", this.wayFromStation[i]);
        // �p�^�[���`�F�b�N
        valid.addValidation(new CodeLookupValidation(this.codeLookupManager,"buildingStationInfo_wayFromStation"));
        valid.validate(errors);
	}
	
	/**
	 * �w����̓k������ �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validTimeFromStation(List<ValidationFailure> errors, int i) {
        // �w����̓k�����ԓ��̓`�F�b�N
        ValidationChain valid = new ValidationChain("buildingStationInfo.input.timeFromStation",this.timeFromStation[i]);
        // �����`�F�b�N
        int len = this.lengthUtils.getLength("buildingStationInfo.input.timeFromStation", 3);
        valid.addValidation(new LineAdapter(new MaxLengthValidation(len), i + 1));
        // �����`�F�b�N
        valid.addValidation(new LineAdapter(new NumericValidation(), i + 1));
        valid.validate(errors);
	}
	
	/**
	 * �o�X��Ж� �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validBusCompany(List<ValidationFailure> errors, int i) {
        // �o�X��Ж����̓`�F�b�N
        ValidationChain valid = new ValidationChain("buildingStationInfo.input.busCompany",this.busCompany[i]);
        // �����`�F�b�N
        int len = this.lengthUtils.getLength("buildingStationInfo.input.busCompany", 40);
        valid.addValidation(new LineAdapter(new MaxLengthValidation(len), i + 1));
        valid.validate(errors);
	}
	
	/**
	 * �o�X�̏��v���� �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validBusRequiredTime(List<ValidationFailure> errors, int i) {
        // �o�X�̏��v���ԓ��̓`�F�b�N
        ValidationChain valid = new ValidationChain("buildingStationInfo.input.busRequiredTime",this.busRequiredTime[i]);
        // �����`�F�b�N
        int len = this.lengthUtils.getLength("buildingStationInfo.input.busRequiredTime", 3);
        valid.addValidation(new LineAdapter(new MaxLengthValidation(len), i + 1));
        // �����`�F�b�N
        valid.addValidation(new LineAdapter(new NumericValidation(), i + 1));
        valid.validate(errors);
	}
	
	/**
	 * �o�X�▼ �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validBusStopName(List<ValidationFailure> errors, int i) {
        // �o�X�▼���̓`�F�b�N
        ValidationChain valid = new ValidationChain("buildingStationInfo.input.busStopName",this.busStopName[i]);
        // �����`�F�b�N
        int len = this.lengthUtils.getLength("buildingStationInfo.input.busStopName", 40);
        valid.addValidation(new LineAdapter(new MaxLengthValidation(len), i + 1));
        valid.validate(errors);
	}
	
	/**
	 * �o�X�₩��̓k������ �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validTimeFromBusStop(List<ValidationFailure> errors, int i) {
        // �o�X�₩��̓k�����ԓ��̓`�F�b�N
        ValidationChain valid = new ValidationChain("buildingStationInfo.input.timeFromBusStop",this.timeFromBusStop[i]);
        // �����`�F�b�N
        int len = this.lengthUtils.getLength("buildingStationInfo.input.timeFromBusStop", 3);
        valid.addValidation(new LineAdapter(new MaxLengthValidation(len), i + 1));
        // �����`�F�b�N
        valid.addValidation(new LineAdapter(new NumericValidation(), i + 1));
        valid.validate(errors);
	}
	
	/**
	 * �\���� �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validSortOrder(List<ValidationFailure> errors, int i) {
        // �\�������̓`�F�b�N
        ValidationChain valid = new ValidationChain("buildingStationInfo.input.sortOrder",this.sortOrder[i]);
        // �����`�F�b�N
        int len = this.lengthUtils.getLength("buildingStationInfo.input.sortOrder", 3);
        valid.addValidation(new LineAdapter(new MaxLengthValidation(len), i + 1));
        // �����`�F�b�N
        valid.addValidation(new LineAdapter(new NumericValidation(), i + 1));
        valid.validate(errors);
	}
}
