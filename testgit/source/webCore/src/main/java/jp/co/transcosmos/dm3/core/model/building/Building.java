package jp.co.transcosmos.dm3.core.model.building;

import java.util.List;

import jp.co.transcosmos.dm3.core.vo.BuildingLandmark;
import jp.co.transcosmos.dm3.dao.JoinResult;

/**
 * �������N���X.
 * �����A�J�X�^�}�C�Y�Ō��������\������e�[�u�������������ꍇ�A���̃N���X���p�����Ċg�����邱�ƁB<br/>
 * �܂��A�p�������ꍇ�͕K�� BuildingManageImpl �� createBuildingInstace() ���I�[�o�[���C�h���鎖�B<br/>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.03.16	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�͌ʃJ�X�^�}�C�Y�Ŋg�������\��������̂Œ��ڃC���X�^���X�𐶐����Ȃ����B
 * �K�� model �N���X����擾���鎖�B
 * 
 */
public class Building {

	/** ������{��� �i������{���{�����ڍׁ{�s���{���}�X�^�A�{�s�撬���}�X�^�j*/
	private JoinResult buildingInfo;

	/** �Ŋ��w��� */
	private List<JoinResult> buildingStationInfoList;

	/** ���������h�}�[�N��� */
	private List<BuildingLandmark> buildingLandmarkList;

	// note
	// ���s�o�[�W�����ł́A�����摜���̓T�|�[�g���Ă��Ȃ��̂ŁA�v���p�e�B�����݂��Ȃ��B
	// �����A���ڂ��ǉ������\��������B



	/**
	 * �R���X�g���N�^�[<br/>
	 * �������N�G�X�g�̃��f���ȊO����C���X�^���X�𐶐��o���Ȃ��l�ɃR���X�g���N�^�𐧌�����B<br/>
	 * <br/>
	 */
	protected Building() {
		super();
	}


	/**
	 * ������{�����擾����B<br/>
	 * <br/>
	 * @return ������{���
	 */
	public JoinResult getBuildingInfo() {
		return buildingInfo;
	}


	/**
	 * ������{����ݒ肷��B<br/>
	 * <br/>
	 * @param buildingInfo ������{���
	 */
	public void setBuildingInfo(JoinResult buildingInfo) {
		this.buildingInfo = buildingInfo;
	}


	/**
	 * �Ŋ��w�����擾����B<br/>
	 * <br/>
	 * @return �Ŋ��w���
	 */
	public List<JoinResult> getBuildingStationInfoList() {
		return buildingStationInfoList;
	}


	/**
	 * �Ŋ��w����ݒ肷��B<br/>
	 * <br/>
	 * @param buildingStationInfoList �Ŋ��w���
	 */
	public void setBuildingStationInfoList(List<JoinResult> buildingStationInfoList) {
		this.buildingStationInfoList = buildingStationInfoList;
	}


	/**
	 * ���������h�}�[�N�����擾����B<br/>
	 * <br/>
	 * @return ���������h�}�[�N���
	 */
	public List<BuildingLandmark> getBuildingLandmarkList() {
		return buildingLandmarkList;
	}


	/**
	 * ���������h�}�[�N����ݒ肷��B<br/>
	 * <br/>
	 * @param buildingLandmarkList ���������h�}�[�N���
	 */
	public void setBuildingLandmarkList(List<BuildingLandmark> buildingLandmarkList) {
		this.buildingLandmarkList = buildingLandmarkList;
	}
	
	
}
