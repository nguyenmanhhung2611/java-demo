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
 * こだわり条件生成クラスのテスト
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
	
	// 前処理
	@Before
	public void init(){

		HousingInfo housingInfo;

		// 物件情報全件削除
		DAOCriteria criteria = new DAOCriteria();
		this.buildingInfoDAO.deleteByFilter(criteria);
		
		// 建物基本情報１
		BuildingInfo buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SYSBLD0001");
		buildingInfo.setDisplayBuildingName("建物名１");
		buildingInfo.setPrefCd("13");

		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});
		
		// 物件情報１
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SYSHOU00001");
		housingInfo.setSysBuildingCd("SYSBLD0001");
		housingInfo.setDisplayHousingName("物件名１");

		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});
		
		// 物件情報2
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SYSHOU00002");
		housingInfo.setSysBuildingCd("SYSBLD0001");
		housingInfo.setDisplayHousingName("物件名２");

		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});
		
		// こだわり条件CD
		HousingPartInfo housingPartInfo = new HousingPartInfo();
		housingPartInfo.setSysHousingCd("SYSHOU00001");
		housingPartInfo.setPartSrchCd("S05");		// このテストで再構築される対象
		
		this.housingPartInfoDAO.insert(new HousingPartInfo[]{housingPartInfo});
		
	}
	/**
	 * こだわり条件の再構築
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>こだわり条件が重複する場合でも、１件に集約して登録されている事</li>
	 *     <li>変換表にない設備情報は無視される事</li>
	 *     <li>不要なこだわり条件が削除されている事</li>
	 *     <li>別の Creator が担当するこだわり条件CD が削除されてれいない事</li>
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
		
		// テスト結果確認
		DAOCriteria criteria1 = new DAOCriteria();
		criteria1.addWhereClause("sysHousingCd", "SYSHOU00001");
		criteria1.addOrderByClause("partSrchCd");
		List<HousingPartInfo> list1 = this.housingPartInfoDAO.selectByFilter(criteria1);
		
		Assert.assertEquals("こだわり条件の登録件数が正しい事", 1, list1.size());
		
		Assert.assertEquals("正しいこだわりコードが登録されている事", "S05", list1.get(0).getPartSrchCd());
		
		// テスト結果確認
		DAOCriteria criteria2 = new DAOCriteria();
		criteria2.addWhereClause("sysHousingCd", "SYSHOU00002");
		criteria2.addOrderByClause("partSrchCd");
		List<HousingPartInfo> list2 = this.housingPartInfoDAO.selectByFilter(criteria2);
		
		Assert.assertEquals("こだわり条件の登録件数が正しい事", 1, list2.size());
		
		Assert.assertEquals("正しいこだわりコードが登録されている事", "S05", list2.get(0).getPartSrchCd());
	}
	
	
}
