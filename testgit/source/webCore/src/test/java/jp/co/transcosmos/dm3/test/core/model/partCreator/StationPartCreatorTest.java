package jp.co.transcosmos.dm3.test.core.model.partCreator;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.BuildingManage;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingFormFactory;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingStationInfoForm;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingPartInfo;
import jp.co.transcosmos.dm3.core.vo.HousingStatusInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ���������������N���X�̃e�X�g
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class StationPartCreatorTest {
	
	@Autowired
	private DAO<HousingInfo> housingInfoDAO;
	@Autowired
	private DAO<HousingStatusInfo> housingStatusInfoDAO;

	@Autowired
	private DAO<BuildingInfo> buildingInfoDAO;
	
	@Autowired
	private DAO<HousingPartInfo> housingPartInfoDAO;
	
	@Autowired
	private BuildingFormFactory formFactory;

	@Qualifier("buildingManager")
	@Autowired
	private BuildingManage buildingManage;
	
	// �O����
	@Before
	public void init(){

		HousingInfo housingInfo;

		// �������S���폜
		DAOCriteria criteria = new DAOCriteria();
		this.buildingInfoDAO.deleteByFilter(criteria);
		
		// ������{���P
		BuildingInfo buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SYSBLD0001");
		buildingInfo.setDisplayBuildingName("�������P");
		buildingInfo.setPrefCd("13");

		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});
		
		// �������P
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SYSHOU00001");
		housingInfo.setSysBuildingCd("SYSBLD0001");
		housingInfo.setDisplayHousingName("�������P");

		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});
		
		// �������2
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SYSHOU00002");
		housingInfo.setSysBuildingCd("SYSBLD0001");
		housingInfo.setDisplayHousingName("�������Q");

		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});
		
		// ����������CD
		HousingPartInfo housingPartInfo = new HousingPartInfo();
		housingPartInfo.setSysHousingCd("SYSHOU00001");
		housingPartInfo.setPartSrchCd("S05");		// ���̃e�X�g�ōč\�z�����Ώ�
		
		this.housingPartInfoDAO.insert(new HousingPartInfo[]{housingPartInfo});
		
	}
	/**
	 * �����������̍č\�z
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������������d������ꍇ�ł��A�P���ɏW�񂵂ēo�^����Ă��鎖</li>
	 *     <li>�ϊ��\�ɂȂ��ݔ����͖�������鎖</li>
	 *     <li>�s�v�Ȃ������������폜����Ă��鎖</li>
	 *     <li>�ʂ� Creator ���S�����邱��������CD ���폜����Ăꂢ�Ȃ���</li>
	 * </ul>
	 */
	@Test
	public void createTest() throws Exception{
		BuildingStationInfoForm inputForm = formFactory.createBuildingStationInfoForm();
		inputForm.setSysBuildingCd("SYSBLD0001");
		inputForm.setDivNo(new String[]{"1","2"});
		inputForm.setDefaultRouteCd(new String[]{"J003001","J003001"});
		inputForm.setStationCd(new String[]{"22828","22828"});
		inputForm.setTimeFromStation(new String[]{"6","4"});
		inputForm.setBusCompany(new String[]{"",""});
		inputForm.setBusRequiredTime(new String[]{"",""});
		inputForm.setDistanceFromStation(new String[]{"",""});
		inputForm.setBusStopName(new String[]{"",""});
		inputForm.setSortOrder(new String[]{"",""});
		inputForm.setBusStopName(new String[]{"",""});
		inputForm.setStationName(new String[]{"",""});
		inputForm.setWayFromStation(new String[]{"",""});
		inputForm.setRouteName(new String[]{"",""});
		inputForm.setTimeFromBusStop(new String[]{"",""});
		this.buildingManage.updateBuildingStationInfo(inputForm);
		
		// �e�X�g���ʊm�F
		DAOCriteria criteria1 = new DAOCriteria();
		criteria1.addWhereClause("sysHousingCd", "SYSHOU00001");
		criteria1.addOrderByClause("partSrchCd");
		List<HousingPartInfo> list1 = this.housingPartInfoDAO.selectByFilter(criteria1);
		
		Assert.assertEquals("�����������̓o�^��������������", 1, list1.size());
		
		Assert.assertEquals("�������������R�[�h���o�^����Ă��鎖", "S05", list1.get(0).getPartSrchCd());
		
		// �e�X�g���ʊm�F
		DAOCriteria criteria2 = new DAOCriteria();
		criteria2.addWhereClause("sysHousingCd", "SYSHOU00002");
		criteria2.addOrderByClause("partSrchCd");
		List<HousingPartInfo> list2 = this.housingPartInfoDAO.selectByFilter(criteria2);
		
		Assert.assertEquals("�����������̓o�^��������������", 1, list2.size());
		
		Assert.assertEquals("�������������R�[�h���o�^����Ă��鎖", "S05", list2.get(0).getPartSrchCd());
	}
	
	
}
