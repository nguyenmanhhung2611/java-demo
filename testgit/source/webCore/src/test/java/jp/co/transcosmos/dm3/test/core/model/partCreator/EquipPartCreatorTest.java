package jp.co.transcosmos.dm3.test.core.model.partCreator;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.HousingManage;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingEquipForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingFormFactory;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingDtlInfo;
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
public class EquipPartCreatorTest {

	@Autowired
	private DAO<HousingInfo> housingInfoDAO;
	@Autowired
	private DAO<HousingDtlInfo> housingDtlInfoDAO;
	@Autowired
	private DAO<HousingStatusInfo> housingStatusInfoDAO;
	@Autowired
	private DAO<HousingPartInfo> housingPartInfoDAO;

	@Autowired
	private DAO<BuildingInfo> buildingInfoDAO;
	@Autowired
	private HousingFormFactory formFactory;

	@Qualifier("housingManager")
	@Autowired
	private HousingManage housingManage;


	
	// 前処理
	@Before
	public void init(){

		HousingInfo housingInfo;


		// 物件情報全件削除
		DAOCriteria criteria = new DAOCriteria();
		this.housingInfoDAO.deleteByFilter(criteria);
		this.housingStatusInfoDAO.deleteByFilter(criteria);
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
		
		// こだわり条件CD
		HousingPartInfo housingPartInfo = new HousingPartInfo();
		housingPartInfo.setSysHousingCd("SYSHOU00001");
		housingPartInfo.setPartSrchCd("S15");		// このテストで再構築される対象
		
		this.housingPartInfoDAO.insert(new HousingPartInfo[]{housingPartInfo});

		housingPartInfo = new HousingPartInfo();
		housingPartInfo.setSysHousingCd("SYSHOU00001");
		housingPartInfo.setPartSrchCd("S20");		// 再構築対象外
		
		this.housingPartInfoDAO.insert(new HousingPartInfo[]{housingPartInfo});



		// 建物基本情報２
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SYSBLD0002");
		buildingInfo.setDisplayBuildingName("建物名２");
		buildingInfo.setPrefCd("14");

		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// 物件情報２
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SYSHOU00002");
		housingInfo.setSysBuildingCd("SYSBLD0002");
		housingInfo.setDisplayHousingName("物件名２");

		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// こだわり条件CD
		housingPartInfo = new HousingPartInfo();
		housingPartInfo.setSysHousingCd("SYSHOU00002");
		housingPartInfo.setPartSrchCd("S15");

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

		HousingEquipForm inputForm = formFactory.createHousingEquipForm();
		inputForm.setSysHousingCd("SYSHOU00001");
		inputForm.setEquipCd(new String[] {"000001", "000002", "000004", "000005"});		

		// テストメソッド実行
		housingManage.updateHousingEquip(inputForm, "editUserId1");


		// テスト結果確認
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00001");
		criteria.addOrderByClause("partSrchCd");
		List<HousingPartInfo> list = this.housingPartInfoDAO.selectByFilter(criteria);

		Assert.assertEquals("こだわり条件の登録件数が正しい事", 3, list.size());
		
		Assert.assertEquals("重複するこだわり条件CD が１行に集約されている事", "B05", list.get(0).getPartSrchCd());
		Assert.assertEquals("指定されたこだわり条件が登録されている事", "S15", list.get(1).getPartSrchCd());
		Assert.assertEquals("別の creator のコードが削除されていない事", "S20", list.get(2).getPartSrchCd());
		
		
		// 別のシステム物件CD のこだわり条件がリセットされていない事
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00002");
		list = this.housingPartInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("別のシステム物件CD のこだわり条件がリセットされていない事", 1, list.size());
		Assert.assertEquals("別のシステム物件CD のこだわり条件がリセットされていない事", "S15", list.get(0).getPartSrchCd());
		
	}

}
