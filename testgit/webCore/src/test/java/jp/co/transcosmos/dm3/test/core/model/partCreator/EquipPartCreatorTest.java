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
 * ���������������N���X�̃e�X�g
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


	
	// �O����
	@Before
	public void init(){

		HousingInfo housingInfo;


		// �������S���폜
		DAOCriteria criteria = new DAOCriteria();
		this.housingInfoDAO.deleteByFilter(criteria);
		this.housingStatusInfoDAO.deleteByFilter(criteria);
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
		
		// ����������CD
		HousingPartInfo housingPartInfo = new HousingPartInfo();
		housingPartInfo.setSysHousingCd("SYSHOU00001");
		housingPartInfo.setPartSrchCd("S15");		// ���̃e�X�g�ōč\�z�����Ώ�
		
		this.housingPartInfoDAO.insert(new HousingPartInfo[]{housingPartInfo});

		housingPartInfo = new HousingPartInfo();
		housingPartInfo.setSysHousingCd("SYSHOU00001");
		housingPartInfo.setPartSrchCd("S20");		// �č\�z�ΏۊO
		
		this.housingPartInfoDAO.insert(new HousingPartInfo[]{housingPartInfo});



		// ������{���Q
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SYSBLD0002");
		buildingInfo.setDisplayBuildingName("�������Q");
		buildingInfo.setPrefCd("14");

		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// �������Q
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SYSHOU00002");
		housingInfo.setSysBuildingCd("SYSBLD0002");
		housingInfo.setDisplayHousingName("�������Q");

		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// ����������CD
		housingPartInfo = new HousingPartInfo();
		housingPartInfo.setSysHousingCd("SYSHOU00002");
		housingPartInfo.setPartSrchCd("S15");

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

		HousingEquipForm inputForm = formFactory.createHousingEquipForm();
		inputForm.setSysHousingCd("SYSHOU00001");
		inputForm.setEquipCd(new String[] {"000001", "000002", "000004", "000005"});		

		// �e�X�g���\�b�h���s
		housingManage.updateHousingEquip(inputForm, "editUserId1");


		// �e�X�g���ʊm�F
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00001");
		criteria.addOrderByClause("partSrchCd");
		List<HousingPartInfo> list = this.housingPartInfoDAO.selectByFilter(criteria);

		Assert.assertEquals("�����������̓o�^��������������", 3, list.size());
		
		Assert.assertEquals("�d�����邱��������CD ���P�s�ɏW�񂳂�Ă��鎖", "B05", list.get(0).getPartSrchCd());
		Assert.assertEquals("�w�肳�ꂽ�������������o�^����Ă��鎖", "S15", list.get(1).getPartSrchCd());
		Assert.assertEquals("�ʂ� creator �̃R�[�h���폜����Ă��Ȃ���", "S20", list.get(2).getPartSrchCd());
		
		
		// �ʂ̃V�X�e������CD �̂��������������Z�b�g����Ă��Ȃ���
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00002");
		list = this.housingPartInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�ʂ̃V�X�e������CD �̂��������������Z�b�g����Ă��Ȃ���", 1, list.size());
		Assert.assertEquals("�ʂ̃V�X�e������CD �̂��������������Z�b�g����Ă��Ȃ���", "S15", list.get(0).getPartSrchCd());
		
	}

}
