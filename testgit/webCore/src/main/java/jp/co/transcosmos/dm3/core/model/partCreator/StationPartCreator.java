package jp.co.transcosmos.dm3.core.model.partCreator;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.HousingPartCreator;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingPartInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;

/**
 * �Ŋ��w��񂩂炱���������𐶐�����.
 * <p>
 * <pre>
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.04.15	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B<br/>
 * 
 */
public class StationPartCreator implements HousingPartCreator {

	/** �����������������p DAO */
	protected DAO<HousingPartInfo> housingPartInfoDAO;
	
	/** ���̃N���X�̐����ΏۂƂȂ�A����������CD�B�i����������CD�̉�����2���͓k�����Ԃ̒l�Ɠ����ɑz�肷��j */
	protected String myPartCds = "S05,S10,S15";
	
	/** �����R�[�h */
	protected String regex = ",";
	
	/** ValueObject �� Factory �N���X */
	protected ValueObjectFactory valueObjectFactory;

	/**
	 * Value �I�u�W�F�N�g�� Factory �N���X��ݒ肷��B<br/>
	 * <br/>
	 * @param valueObjectFactory Value �I�u�W�F�N�g�� Factory
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}
	
	/**
	 * ���̃N���X�̐����ΏۂƂȂ�A����������CD��ݒ肷��B<br/>
	 * <br/>
	 * @param myPartCds 
	 */
	public void setMyPartCds(String myPartCds) {
		this.myPartCds = myPartCds;
	}

	/**
	 * �����R�[�h��ݒ肷��B<br/>
	 * <br/>
	 * @param regex �����R�[�h
	 */
	public void setRegex(String regex) {
		this.regex = regex;
	}

	/**
	 * �����������������DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param housingPartInfoDAO �����������������DAO
	 */
	public void setHousingPartInfoDAO(DAO<HousingPartInfo> housingPartInfoDAO) {
		this.housingPartInfoDAO = housingPartInfoDAO;
	}
	
	/**
	 * ���̃N���X�̂����������𐶐����郁�\�b�h�𔻒肷��B<br/>
	 * <br/>
	 * @param methodName ���ꂩ����s���郁�\�b�h��
	 * @return ���\�b�h���� updateHousingEquip �̏ꍇ�Atrue �𕜋A
	 */
	@Override
	public boolean isExecuteMethod(String methodName) {
		// �Ŋ��w���X�V�������Ɏ��s����B
		// �����A�J�X�^�}�C�Y���� updateBuildingStationInfo() ��ʂ̃��\�b�h������s���Ă���
		// �ꍇ�́A�����̃��\�b�h����ł����s�����l�ɕύX���鎖�B
		if ("updateBuildingStationInfo".equals(methodName)){
			return true;
		}
		return false;
	}

	/**
	 * �o�^�����Ŋ��w���I�u�W�F�N�g���畨���������������쐬����B<br/>
	 * <br/>
	 */
	@Override
	public void createPart(Housing housing) throws Exception {
		// �o�^�����Ŋ��w�����擾����B
		List<JoinResult> stationInfoList = housing.getBuilding().getBuildingStationInfoList();
		// �X�V�ΏۂƂȂ镨����{�����擾����B
		HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");
		
		// ���݂̂���������CD ���폜����B
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", housingInfo.getSysHousingCd());
		String[] myPartCdString = this.myPartCds.split(this.regex);
		criteria.addInSubQuery("partSrchCd", myPartCdString);
		this.housingPartInfoDAO.deleteByFilter(criteria);
		// �Ŋ��w��񂪂���ꍇ�A���������쐬����
		if (stationInfoList != null && stationInfoList.size() != 0) {
			// �ŒZ����
			int time = ((BuildingStationInfo) stationInfoList.get(0).getItems().get("buildingStationInfo")).getTimeFromStation();
			// �Ŋ��w���ɍŒZ���Ԃ��擾����
			for (int i = 1;i < stationInfoList.size();i++) {
				// �k���̏��v����
				int walkTime = ((BuildingStationInfo) stationInfoList.get(i).getItems().get("buildingStationInfo")).getTimeFromStation();
				if (time > walkTime) {
					time = walkTime;
				}
			}
			for (int i = 0;i < myPartCdString.length;i++) {
				// �������Cd��ݒ肷��
				if (time < Integer.valueOf(myPartCdString[i].substring(1))) {
					HousingPartInfo stationPartInfo = createHousingPartInfo(housingInfo, myPartCdString[i]);
					// ������������ǉ�����B
					this.housingPartInfoDAO.insert(new HousingPartInfo[] { stationPartInfo });
					break;
				}
			}
		}
	}

	/**
	 * �V�K�ǉ�����A������������������ Value �I�u�W�F�N�g�𐶐�����B<br/>
	 * �{���́A�V�X�e������CD �ƁA����������CD �����ő����͂������A�g�������l�����āA���̑������n���Ă����B<br/>
	 * <br/>
	 * @param housingInfo �������
	 * @param partCd�@����������CD
	 * @return
	 */
	protected HousingPartInfo createHousingPartInfo(HousingInfo housingInfo, String partCd) {
		// Value �I�u�W�F�N�g�𐶐����ăv���p�e�B�l��ݒ肷��B
		HousingPartInfo housingPartInfo = (HousingPartInfo) this.valueObjectFactory.getValueObject("HousingPartInfo");
		housingPartInfo.setSysHousingCd(housingInfo.getSysHousingCd());
		housingPartInfo.setPartSrchCd(partCd);
		return housingPartInfo;
	}

}
