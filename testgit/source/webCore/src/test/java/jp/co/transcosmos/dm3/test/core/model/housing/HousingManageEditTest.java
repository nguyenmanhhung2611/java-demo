package jp.co.transcosmos.dm3.test.core.model.housing;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.HousingManage;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingDtlForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingEquipForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingFormFactory;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingImgForm;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.HousingEquipInfo;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingStatusInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.utils.DateUtils;
import jp.co.transcosmos.dm3.utils.StringUtils;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ������� model �X�V�n�����̃e�X�g
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class HousingManageEditTest {

	@Autowired
	private DAO<BuildingInfo> buildingInfoDAO;
	@Autowired
	private DAO<HousingInfo> housingInfoDAO;
	@Autowired
	private DAO<HousingDtlInfo> housingDtlInfoDAO;
	@Autowired
	private DAO<HousingStatusInfo> housingStatusInfoDAO;
	@Autowired
	private DAO<HousingEquipInfo> housingEquipInfoDAO;
	@Autowired
	private DAO<HousingImageInfo> housingImageInfoDAO;

	@Autowired
	private HousingFormFactory formFactory;
	@Qualifier("housingManager")
	@Autowired
	private HousingManage housingManage;



	// �O����
	@Before
	public void init(){
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

		
		// ������{���Q
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SYSBLD0002");
		buildingInfo.setDisplayBuildingName("�������Q");
		buildingInfo.setPrefCd("14");

		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

	}



	/**
	 * ������{���̐V�K�o�^�i�S�t�B�[���h���͎��j
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>���\�b�h�̖߂�l���̔Ԃ��ꂽ�V�X�e������CD �ł��鎖</li>
	 *     <li>�o�^�l�����͒l�ƈ�v���鎖</li>
	 * </ul>
	 */
	@Test
	public void addHousingFullTest() throws Exception {

		// ���̓t�H�[���𐶐����ď����l��ݒ�
		HousingForm inputForm = this.formFactory.createHousingForm();

		inputForm.setHousingCd("HOU0001");								// �����ԍ�
		inputForm.setDisplayHousingName("HNAME0001");					// �\���p������
		inputForm.setDisplayHousingNameKana("HNAME0001K");				// �\���p�������i�J�i�j
		inputForm.setRoomNo("R001");									// �����ԍ�
		inputForm.setSysBuildingCd("SYSBLD0001");						// �V�X�e������CD
		inputForm.setPrice("100000");									// �����E���i
		inputForm.setUpkeep("10000");									// �Ǘ���
		inputForm.setCommonAreaFee("20000");							// ���v��
		inputForm.setMenteFee("30000");									// �C�U�ϗ���
		inputForm.setSecDeposit("40000.01");							// �~��
		inputForm.setSecDepositCrs("S");								// �~���P��
		inputForm.setBondChrg("60000.01");								// �ۏ؋�
		inputForm.setBondChrgCrs("B");									// �ۏ؋��P��
		inputForm.setDepositDiv("ZZ");									// �~������敪
		inputForm.setDeposit("90000.01");								// �~������z
		inputForm.setDepositCrs("D");									// �~������P��
		inputForm.setLayoutCd("L01");									// �Ԏ�CD
		inputForm.setLayoutComment("LAYOUT_NOTE1");						// �Ԏ�ڍ׃R�����g
		inputForm.setFloorNo("110");									// �����̊K��
		inputForm.setFloorNoNote("FLOOR0001");							// �����̊K���R�����g
		inputForm.setLandArea("111.01"); 								// �y�n�ʐ�
		inputForm.setLandAreaMemo("LAND_NOTE1");						// �y�n�ʐ�_�⑫
		inputForm.setPersonalArea("112.01");							// ��L�ʐ�
		inputForm.setPersonalAreaMemo("PER_NOTE1");						// ��L�ʐ�_�⑫
		inputForm.setMoveinFlg("9");									// ������ԃt���O
		inputForm.setParkingSituation("YY"); 							// ���ԏ�̏�
		inputForm.setParkingEmpExist("N");								// ���ԏ��̗L��
		inputForm.setDisplayParkingInfo("PARKING_001");					// �\���p���ԏ���
		inputForm.setWindowDirection("S");								// ���̌���
		inputForm.setBasicComment("BASIC001");							// ��{���R�����g

		// �e�X�g�N���X���s
		String id = this.housingManage.addHousing(inputForm, "addUserId1");
		
		// �f�[�^�̃`�F�b�N
		Assert.assertNotNull("�V�X�e������CD ���̔Ԃ���Ă��鎖", id);

		// ������{���̃`�F�b�N
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK(id);

		// �V�X�e������CD
		Assert.assertEquals("�V�X�e������CD ���̔Ԃ��ꂽ�l�ƈ�v���鎖�B", id, housingInfo.getSysHousingCd());
		// �����ԍ�
		Assert.assertEquals("�����ԍ������͂��ꂽ�l�ƈ�v���鎖�B", "HOU0001", housingInfo.getHousingCd());
		// �\���p������
		Assert.assertEquals("�\���p�����������͂��ꂽ�l�ƈ�v���鎖�B", "HNAME0001", housingInfo.getDisplayHousingName());
		// �\���p�������i�J�i�j
		Assert.assertEquals("�\���p�������i�J�i�j�����͂��ꂽ�l�ƈ�v���鎖�B", "HNAME0001K", housingInfo.getDisplayHousingNameKana());
		// �����ԍ�
		Assert.assertEquals("�����ԍ������͂��ꂽ�l�ƈ�v���鎖�B", "R001", housingInfo.getRoomNo());
		// �V�X�e������CD
		Assert.assertEquals("�V�X�e������CD�����͂��ꂽ�l�ƈ�v���鎖�B", "SYSBLD0001", housingInfo.getSysBuildingCd());
		// �����E���i
		Assert.assertEquals("�����E���i�����͂��ꂽ�l�ƈ�v���鎖�B", Long.valueOf("100000"), housingInfo.getPrice());
		// �Ǘ���
		Assert.assertEquals("�Ǘ�����͂��ꂽ�l�ƈ�v���鎖�B", Long.valueOf("10000"), housingInfo.getUpkeep());
		// ���v��
		Assert.assertEquals("���v����͂��ꂽ�l�ƈ�v���鎖�B", Long.valueOf("20000"), housingInfo.getCommonAreaFee());
		// �C�U�ϗ���
		Assert.assertEquals("�C�U�ϗ�����͂��ꂽ�l�ƈ�v���鎖�B", Long.valueOf("30000"), housingInfo.getMenteFee());
		// �~��
		Assert.assertEquals("�~�������͂��ꂽ�l�ƈ�v���鎖�B", new BigDecimal("40000.01"), housingInfo.getSecDeposit());
		// �~���P��
		Assert.assertEquals("�~���P�ʂ����͂��ꂽ�l�ƈ�v���鎖�B", "S", housingInfo.getSecDepositCrs());
		// �ۏ؋�
		Assert.assertEquals("�ۏ؋������͂��ꂽ�l�ƈ�v���鎖�B", new BigDecimal("60000.01"), housingInfo.getBondChrg());
		// �ۏ؋��P��
		Assert.assertEquals("�ۏ؋��P�ʂ����͂��ꂽ�l�ƈ�v���鎖�B", "B", housingInfo.getBondChrgCrs());
		// �~������敪
		Assert.assertEquals("�~������敪�����͂��ꂽ�l�ƈ�v���鎖�B", "ZZ", housingInfo.getDepositDiv());
		// �~������z
		Assert.assertEquals("�~������z�����͂��ꂽ�l�ƈ�v���鎖�B", new BigDecimal("90000.01"), housingInfo.getDeposit());
		// �~������P��
		Assert.assertEquals("�~������P�ʂ����͂��ꂽ�l�ƈ�v���鎖�B", "D", housingInfo.getDepositCrs());
		// �Ԏ�CD
		Assert.assertEquals("�Ԏ�CD�����͂��ꂽ�l�ƈ�v���鎖�B", "L01", housingInfo.getLayoutCd());
		// �Ԏ�ڍ׃R�����g
		Assert.assertEquals("�Ԏ�ڍ׃R�����g�����͂��ꂽ�l�ƈ�v���鎖�B", "LAYOUT_NOTE1", housingInfo.getLayoutComment());
		// �����̊K��
		Assert.assertEquals("�����̊K���R�����g�����͂��ꂽ�l�ƈ�v���鎖�B", Integer.valueOf("110"), housingInfo.getFloorNo());
		// �����̊K���R�����g
		Assert.assertEquals("�����̊K���R�����g�����͂��ꂽ�l�ƈ�v���鎖�B", "FLOOR0001", housingInfo.getFloorNoNote());
		// �y�n�ʐ�
		Assert.assertEquals("�y�n�ʐς����͂��ꂽ�l�ƈ�v���鎖�B", new BigDecimal("111.01"), housingInfo.getLandArea());
		// �y�n�ʐ�_�⑫
		Assert.assertEquals("�y�n�ʐ�_�⑫�����͂��ꂽ�l�ƈ�v���鎖�B", "LAND_NOTE1", housingInfo.getLandAreaMemo());
		// ��L�ʐ�
		Assert.assertEquals("��L�ʐς����͂��ꂽ�l�ƈ�v���鎖�B", new BigDecimal("112.01"), housingInfo.getPersonalArea());
		// ��L�ʐ�_�⑫
		Assert.assertEquals("��L�ʐ�_�⑫�����͂��ꂽ�l�ƈ�v���鎖�B", "PER_NOTE1", housingInfo.getPersonalAreaMemo());
		// ������ԃt���O
		Assert.assertEquals("������ԃt���O�����͂��ꂽ�l�ƈ�v���鎖�B", "9", housingInfo.getMoveinFlg());
		// ���ԏ�̏�
		Assert.assertEquals("���ԏ�̏󋵂����͂��ꂽ�l�ƈ�v���鎖�B", "YY", housingInfo.getParkingSituation());
		// ���ԏ��̗L��
		Assert.assertEquals("���ԏ��̗L�������͂��ꂽ�l�ƈ�v���鎖�B", "N", housingInfo.getParkingEmpExist());
		// �\���p���ԏ���
		Assert.assertEquals("�\���p���ԏ��񂪓��͂��ꂽ�l�ƈ�v���鎖�B", "PARKING_001", housingInfo.getDisplayParkingInfo());
		// ���̌���
		Assert.assertEquals("���̌��������͂��ꂽ�l�ƈ�v���鎖�B", "S", housingInfo.getWindowDirection());
		// ��{���R�����g
		Assert.assertEquals("��{���R�����g�����͂��ꂽ�l�ƈ�v���鎖�B", "BASIC001", housingInfo.getBasicComment());
		
		// �A�C�R�����@�́A�ݔ��o�^���ōX�V�����̂ŁA���̃^�C�~���O�ł� null ���o�^�����B
		Assert.assertNull("�A�C�R����� null �ł��鎖", housingInfo.getIconCd());

		// �o�^���i���t���̂݁j
		String now = StringUtils.dateToString(new Date());
		String date = StringUtils.dateToString(housingInfo.getInsDate());
		Assert.assertEquals("�o�^�����V�X�e�����t�ƈ�v���鎖�B", now, date);

		// �o�^��
		Assert.assertEquals("�o�^�҂������œn�����l�ƈ�v���鎖�B", "addUserId1", housingInfo.getInsUserId());

		// �ŏI�X�V���i���t���̂݁j
		date = StringUtils.dateToString(housingInfo.getUpdDate());
		Assert.assertEquals("�ŏI�X�V�����V�X�e�����t�ƈ�v���鎖�B", now, date);

		// �ŏI�X�V��
		Assert.assertEquals("�ŏI�X�V�҂������œn�����l�ƈ�v���鎖�B", "addUserId1", housingInfo.getUpdUserId());

	}
	
	
	
	/**
	 * ������{���̐V�K�o�^�i�V�X�e������CD���w�肵���ꍇ�j
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�V�X�e������CD ���t�H�[���ɐݒ肵�Ă��l�������̔Ԃ���鎖</li>
	 * </ul>
	 */
	@Test
	public void addHousingBadCdTest() throws Exception{
		
		// ���̓t�H�[���𐶐����ď����l��ݒ�
		HousingForm inputForm = this.formFactory.createHousingForm();

		// �o�^���ɃV�X�e������CD �̒l�͎g�p����Ȃ��B
		// �e�X�g�p�ɂ킴�ƒl��ݒ肵�Ă����B
		inputForm.setSysHousingCd("DUMMY_SYS_CD");
		inputForm.setDisplayHousingName("HNAME0001");					// �\���p������
		inputForm.setSysBuildingCd("SYSBLD0001");						// �V�X�e������CD


		// �e�X�g�N���X���s
		String id = this.housingManage.addHousing(inputForm, "addUserId1");
		
		// �f�[�^�̃`�F�b�N
		Assert.assertNotNull("�V�X�e������CD ���̔Ԃ���Ă��鎖", id);

		// ������{���̃`�F�b�N
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK(id);

		// �V�X�e������CD
		Assert.assertEquals("�V�X�e������CD ���̔Ԃ��ꂽ�l�ƈ�v���鎖�B", id, housingInfo.getSysHousingCd());
		Assert.assertNotEquals("�_�~�[�Őݒ肵���V�X�e������CD �Ƃ͒l���قȂ鎖", inputForm.getSysHousingCd(), housingInfo.getSysHousingCd());

	}
	
	
	
	/**
	 * ������{���̐V�K�o�^�i�e�ƂȂ錚��CD �����݂��Ȃ��ꍇ�j
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>NotFoundException ���X���[����鎖</li>
	 * </ul>
	 */
	@Test(expected=NotFoundException.class)
	public void addHousingFailTest() throws Exception {

		// ���̓t�H�[���𐶐����ď����l��ݒ�
		HousingForm inputForm = this.formFactory.createHousingForm();

		// ���݂��Ȃ��V�X�e������CD ��ݒ�
		inputForm.setSysBuildingCd("SYSBLD2001");

		inputForm.setDisplayHousingName("HNAME0001");					// �\���p������

		// �e�X�g�N���X���s
		this.housingManage.addHousing(inputForm, "addUserId1");

	}
	
	
	/**
	 * ������{���̐V�K�o�^�i�Œ���̓��͒l�j
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>���\�b�h�̖߂�l���̔Ԃ��ꂽ�V�X�e������CD �ł��鎖</li>
	 *     <li>�o�^�l�����͒l�ƈ�v���鎖</li>
	 * </ul>
	 */
	@Test
	public void addHousingMinTest() throws Exception {

		// ���̓t�H�[���𐶐����ď����l��ݒ�
		HousingForm inputForm = this.formFactory.createHousingForm();

		// �o�^���ɃV�X�e������CD �̒l�͎g�p����Ȃ��B
		// �e�X�g�p�ɂ킴�ƒl��ݒ肵�Ă����B
		inputForm.setSysHousingCd("DUMMY_SYS_CD");

		inputForm.setSysBuildingCd("SYSBLD0001");						// �V�X�e������CD
		inputForm.setDisplayHousingName("HNAME0001");					// �\���p������

		// �e�X�g�N���X���s
		String id = this.housingManage.addHousing(inputForm, "addUserId1");
		
		// �f�[�^�̃`�F�b�N
		Assert.assertNotNull("�V�X�e������CD ���̔Ԃ���Ă��鎖", id);

		// ������{���̃`�F�b�N
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK(id);

		// �V�X�e������CD
		Assert.assertEquals("�V�X�e������CD ���̔Ԃ��ꂽ�l�ƈ�v���鎖�B", id, housingInfo.getSysHousingCd());
		// �����ԍ�
		Assert.assertNull("�����ԍ��� Null �ł��鎖�B", housingInfo.getHousingCd());
		// �\���p������
		Assert.assertEquals("�\���p�����������͂��ꂽ�l�ƈ�v���鎖�B", "HNAME0001", housingInfo.getDisplayHousingName());
		// �\���p�������i�J�i�j
		Assert.assertNull("�\���p�������i�J�i�j�� Null �ł��鎖�B", housingInfo.getDisplayHousingNameKana());
		// �����ԍ�
		Assert.assertNull("�����ԍ��� Null �ł��鎖�B", housingInfo.getRoomNo());
		// �V�X�e������CD
		Assert.assertEquals("�V�X�e������CD�����͂��ꂽ�l�ƈ�v���鎖�B", "SYSBLD0001", housingInfo.getSysBuildingCd());
		// �����E���i
		Assert.assertNull("�����E���i�� Null �ł��鎖�B", housingInfo.getPrice());
		// �Ǘ���
		Assert.assertNull("�Ǘ�� Null �ł��鎖�B", housingInfo.getUpkeep());
		// ���v��
		Assert.assertNull("���v� Null �ł��鎖�B", housingInfo.getCommonAreaFee());
		// �C�U�ϗ���
		Assert.assertNull("�C�U�ϗ�� Null �ł��鎖�B", housingInfo.getMenteFee());
		// �~��
		Assert.assertNull("�~���� Null �ł��鎖�B", housingInfo.getSecDeposit());
		// �~���P��
		Assert.assertNull("�~���P�ʂ� Null �ł��鎖�B", housingInfo.getSecDepositCrs());
		// �ۏ؋�
		Assert.assertNull("�ۏ؋��� Null �ł��鎖�B", housingInfo.getBondChrg());
		// �ۏ؋��P��
		Assert.assertNull("�ۏ؋��P�ʂ� Null �ł��鎖�B", housingInfo.getBondChrgCrs());
		// �~������敪
		Assert.assertNull("�~������敪�� Null �ł��鎖�B", housingInfo.getDepositDiv());
		// �~������z
		Assert.assertNull("�~������z�� Null �ł��鎖�B", housingInfo.getDeposit());
		// �~������P��
		Assert.assertNull("�~������P�ʂ� Null �ł��鎖�B", housingInfo.getDepositCrs());
		// �Ԏ�CD
		Assert.assertNull("�Ԏ�CD �� Null �ł��鎖�B", housingInfo.getLayoutCd());
		// �Ԏ�ڍ׃R�����g
		Assert.assertNull("�Ԏ�ڍ׃R�����g�� Null �ł��鎖�B", housingInfo.getLayoutComment());
		// �����̊K��
		Assert.assertNull("�����̊K���R�����g�� Null �ł��鎖�B", housingInfo.getFloorNo());
		// �����̊K���R�����g
		Assert.assertNull("�����̊K���R�����g�� Null �ł��鎖�B", housingInfo.getFloorNoNote());
		// �y�n�ʐ�
		Assert.assertNull("�y�n�ʐς� Null �ł��鎖�B", housingInfo.getLandArea());
		// �y�n�ʐ�_�⑫
		Assert.assertNull("�y�n�ʐ�_�⑫�� Null �ł��鎖�B", housingInfo.getLandAreaMemo());
		// ��L�ʐ�
		Assert.assertNull("��L�ʐς� Null �ł��鎖�B", housingInfo.getPersonalArea());
		// ��L�ʐ�_�⑫
		Assert.assertNull("��L�ʐ�_�⑫�� Null �ł��鎖�B", housingInfo.getPersonalAreaMemo());
		// ������ԃt���O
		Assert.assertNull("������ԃt���O�� Null �ł��鎖�B", housingInfo.getMoveinFlg());
		// ���ԏ�̏�
		Assert.assertNull("���ԏ�̏󋵂� Null �ł��鎖�B", housingInfo.getParkingSituation());
		// ���ԏ��̗L��
		Assert.assertNull("���ԏ��̗L���� Null �ł��鎖�B", housingInfo.getParkingEmpExist());
		// �\���p���ԏ���
		Assert.assertNull("�\���p���ԏ��� Null �ł��鎖�B", housingInfo.getDisplayParkingInfo());
		// ���̌���
		Assert.assertNull("���̌����� Null �ł��鎖�B", housingInfo.getWindowDirection());
		// ��{���R�����g
		Assert.assertNull("��{���R�����g�� Null �ł��鎖�B", housingInfo.getBasicComment());
		
		// �A�C�R�����@�́A�ݔ��o�^���ōX�V�����̂ŁA���̃^�C�~���O�ł� null ���o�^�����B
		Assert.assertNull("�A�C�R����� null �ł��鎖", housingInfo.getIconCd());

		// �o�^���i���t���̂݁j
		String now = StringUtils.dateToString(new Date());
		String date = StringUtils.dateToString(housingInfo.getInsDate());
		Assert.assertEquals("�o�^�����V�X�e�����t�ƈ�v���鎖�B", now, date);

		// �o�^��
		Assert.assertEquals("�o�^�҂������œn�����l�ƈ�v���鎖�B", "addUserId1", housingInfo.getInsUserId());

		// �ŏI�X�V���i���t���̂݁j
		date = StringUtils.dateToString(housingInfo.getUpdDate());
		Assert.assertEquals("�ŏI�X�V�����V�X�e�����t�ƈ�v���鎖�B", now, date);

		// �ŏI�X�V��
		Assert.assertEquals("�ŏI�X�V�҂������œn�����l�ƈ�v���鎖�B", "addUserId1", housingInfo.getUpdUserId());

	}



	/**
	 * ������{���̍X�V
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�w�肵��������{��񂪓��͂����l�ōX�V����鎖</li>
	 *     <li>�A�C�R�����A�o�^���A�o�^�҂��X�V����Ȃ���</li>
	 *     <li>�w�肵�Ă��Ȃ�������{��񂪍X�V����Ă��Ȃ���</li>
	 * </ul>
	 */
	@Test
	public void updateHousingTest() throws Exception {
		
		// �e�X�g�f�[�^�������o�^����B
		initData();


		// ���̓t�H�[���𐶐����ď����l��ݒ�
		HousingForm inputForm = this.formFactory.createHousingForm();

		inputForm.setSysHousingCd("SYSHOU00001");						// �X�V�ΏۃV�X�e������CD

		inputForm.setHousingCd("HOU0001");								// �����ԍ�
		inputForm.setDisplayHousingName("HNAME0001");					// �\���p������
		inputForm.setDisplayHousingNameKana("HNAME0001K");				// �\���p�������i�J�i�j
		inputForm.setRoomNo("R001");									// �����ԍ�
		inputForm.setSysBuildingCd("SYSBLD0002");						// �V�X�e������CD
		inputForm.setPrice("100000");									// �����E���i
		inputForm.setUpkeep("10000");									// �Ǘ���
		inputForm.setCommonAreaFee("20000");							// ���v��
		inputForm.setMenteFee("30000");									// �C�U�ϗ���
		inputForm.setSecDeposit("40000.01");							// �~��
		inputForm.setSecDepositCrs("S");								// �~���P��
		inputForm.setBondChrg("60000.01");								// �ۏ؋�
		inputForm.setBondChrgCrs("B");									// �ۏ؋��P��
		inputForm.setDepositDiv("ZZ");									// �~������敪
		inputForm.setDeposit("90000.01");								// �~������z
		inputForm.setDepositCrs("D");									// �~������P��
		inputForm.setLayoutCd("L01");									// �Ԏ�CD
		inputForm.setLayoutComment("LAYOUT_NOTE1");						// �Ԏ�ڍ׃R�����g
		inputForm.setFloorNo("110");									// �����̊K��
		inputForm.setFloorNoNote("FLOOR0001");							// �����̊K���R�����g
		inputForm.setLandArea("111.01"); 								// �y�n�ʐ�
		inputForm.setLandAreaMemo("LAND_NOTE1");						// �y�n�ʐ�_�⑫
		inputForm.setPersonalArea("112.01");							// ��L�ʐ�
		inputForm.setPersonalAreaMemo("PER_NOTE1");						// ��L�ʐ�_�⑫
		inputForm.setMoveinFlg("9");									// ������ԃt���O
		inputForm.setParkingSituation("YY"); 							// ���ԏ�̏�
		inputForm.setParkingEmpExist("N");								// ���ԏ��̗L��
		inputForm.setDisplayParkingInfo("PARKING_001");					// �\���p���ԏ���
		inputForm.setWindowDirection("S");								// ���̌���
		inputForm.setBasicComment("BASIC001");							// ��{���R�����g

		// �e�X�g�N���X���s
		this.housingManage.updateHousing(inputForm, "updUserId1");

		
		// ������{���̃`�F�b�N
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00001");

		// �V�X�e������CD
		Assert.assertEquals("�V�X�e������CD ���̔Ԃ��ꂽ�l�ƈ�v���鎖�B", "SYSHOU00001", housingInfo.getSysHousingCd());
		// �����ԍ�
		Assert.assertEquals("�����ԍ������͂��ꂽ�l�ƈ�v���鎖�B", "HOU0001", housingInfo.getHousingCd());
		// �\���p������
		Assert.assertEquals("�\���p�����������͂��ꂽ�l�ƈ�v���鎖�B", "HNAME0001", housingInfo.getDisplayHousingName());
		// �\���p�������i�J�i�j
		Assert.assertEquals("�\���p�������i�J�i�j�����͂��ꂽ�l�ƈ�v���鎖�B", "HNAME0001K", housingInfo.getDisplayHousingNameKana());
		// �����ԍ�
		Assert.assertEquals("�����ԍ������͂��ꂽ�l�ƈ�v���鎖�B", "R001", housingInfo.getRoomNo());
		// �V�X�e������CD
		Assert.assertEquals("�V�X�e������CD�����͂��ꂽ�l�ƈ�v���鎖�B", "SYSBLD0002", housingInfo.getSysBuildingCd());
		// �����E���i
		Assert.assertEquals("�����E���i�����͂��ꂽ�l�ƈ�v���鎖�B", Long.valueOf("100000"), housingInfo.getPrice());
		// �Ǘ���
		Assert.assertEquals("�Ǘ�����͂��ꂽ�l�ƈ�v���鎖�B", Long.valueOf("10000"), housingInfo.getUpkeep());
		// ���v��
		Assert.assertEquals("���v����͂��ꂽ�l�ƈ�v���鎖�B", Long.valueOf("20000"), housingInfo.getCommonAreaFee());
		// �C�U�ϗ���
		Assert.assertEquals("�C�U�ϗ�����͂��ꂽ�l�ƈ�v���鎖�B", Long.valueOf("30000"), housingInfo.getMenteFee());
		// �~��
		Assert.assertEquals("�~�������͂��ꂽ�l�ƈ�v���鎖�B", new BigDecimal("40000.01"), housingInfo.getSecDeposit());
		// �~���P��
		Assert.assertEquals("�~���P�ʂ����͂��ꂽ�l�ƈ�v���鎖�B", "S", housingInfo.getSecDepositCrs());
		// �ۏ؋�
		Assert.assertEquals("�ۏ؋������͂��ꂽ�l�ƈ�v���鎖�B", new BigDecimal("60000.01"), housingInfo.getBondChrg());
		// �ۏ؋��P��
		Assert.assertEquals("�ۏ؋��P�ʂ����͂��ꂽ�l�ƈ�v���鎖�B", "B", housingInfo.getBondChrgCrs());
		// �~������敪
		Assert.assertEquals("�~������敪�����͂��ꂽ�l�ƈ�v���鎖�B", "ZZ", housingInfo.getDepositDiv());
		// �~������z
		Assert.assertEquals("�~������z�����͂��ꂽ�l�ƈ�v���鎖�B", new BigDecimal("90000.01"), housingInfo.getDeposit());
		// �~������P��
		Assert.assertEquals("�~������P�ʂ����͂��ꂽ�l�ƈ�v���鎖�B", "D", housingInfo.getDepositCrs());
		// �Ԏ�CD
		Assert.assertEquals("�Ԏ�CD�����͂��ꂽ�l�ƈ�v���鎖�B", "L01", housingInfo.getLayoutCd());
		// �Ԏ�ڍ׃R�����g
		Assert.assertEquals("�Ԏ�ڍ׃R�����g�����͂��ꂽ�l�ƈ�v���鎖�B", "LAYOUT_NOTE1", housingInfo.getLayoutComment());
		// �����̊K��
		Assert.assertEquals("�����̊K���R�����g�����͂��ꂽ�l�ƈ�v���鎖�B", Integer.valueOf("110"), housingInfo.getFloorNo());
		// �����̊K���R�����g
		Assert.assertEquals("�����̊K���R�����g�����͂��ꂽ�l�ƈ�v���鎖�B", "FLOOR0001", housingInfo.getFloorNoNote());
		// �y�n�ʐ�
		Assert.assertEquals("�y�n�ʐς����͂��ꂽ�l�ƈ�v���鎖�B", new BigDecimal("111.01"), housingInfo.getLandArea());
		// �y�n�ʐ�_�⑫
		Assert.assertEquals("�y�n�ʐ�_�⑫�����͂��ꂽ�l�ƈ�v���鎖�B", "LAND_NOTE1", housingInfo.getLandAreaMemo());
		// ��L�ʐ�
		Assert.assertEquals("��L�ʐς����͂��ꂽ�l�ƈ�v���鎖�B", new BigDecimal("112.01"), housingInfo.getPersonalArea());
		// ��L�ʐ�_�⑫
		Assert.assertEquals("��L�ʐ�_�⑫�����͂��ꂽ�l�ƈ�v���鎖�B", "PER_NOTE1", housingInfo.getPersonalAreaMemo());
		// ������ԃt���O
		Assert.assertEquals("������ԃt���O�����͂��ꂽ�l�ƈ�v���鎖�B", "9", housingInfo.getMoveinFlg());
		// ���ԏ�̏�
		Assert.assertEquals("���ԏ�̏󋵂����͂��ꂽ�l�ƈ�v���鎖�B", "YY", housingInfo.getParkingSituation());
		// ���ԏ��̗L��
		Assert.assertEquals("���ԏ��̗L�������͂��ꂽ�l�ƈ�v���鎖�B", "N", housingInfo.getParkingEmpExist());
		// �\���p���ԏ���
		Assert.assertEquals("�\���p���ԏ��񂪓��͂��ꂽ�l�ƈ�v���鎖�B", "PARKING_001", housingInfo.getDisplayParkingInfo());
		// ���̌���
		Assert.assertEquals("���̌��������͂��ꂽ�l�ƈ�v���鎖�B", "S", housingInfo.getWindowDirection());
		// ��{���R�����g
		Assert.assertEquals("��{���R�����g�����͂��ꂽ�l�ƈ�v���鎖�B", "BASIC001", housingInfo.getBasicComment());
		
		// �A�C�R�����@�́A������{���X�V�ł͏����ΏۊO�Ȃ̂ŁA���̒l�ł��鎖���`�F�b�N����B
		Assert.assertEquals("�A�C�R����񂪌��̒l�ł��鎖", "dummyIcon1", housingInfo.getIconCd());

		// �o�^���i���t���̂݁j
		String now10 = StringUtils.dateToString(DateUtils.addDays(new Date(), -10));
		String date = StringUtils.dateToString(housingInfo.getInsDate());
		Assert.assertEquals("�o�^�����X�V����Ă��Ȃ����B", now10, date);

		// �o�^��
		Assert.assertEquals("�o�^�҂��X�V����Ă��Ȃ���", "dummInsID1", housingInfo.getInsUserId());

		// �ŏI�X�V���i���t���̂݁j
		String now = StringUtils.dateToString(new Date());
		date = StringUtils.dateToString(housingInfo.getUpdDate());
		Assert.assertEquals("�ŏI�X�V�����V�X�e�����t�ƈ�v���鎖�B", now, date);

		// �ŏI�X�V��
		Assert.assertEquals("�ŏI�X�V�҂������œn�����l�ƈ�v���鎖�B", "updUserId1", housingInfo.getUpdUserId());

		
		// �X�V�ΏۊO���R�[�h���X�V����Ă��Ȃ������`�F�b�N����B
		housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00002");
		Assert.assertEquals("�X�V�ΏۊO���R�[�h���X�V����Ă��Ȃ���", "OLD�������Q", housingInfo.getDisplayHousingName());
		
	}

	
	
	/**
	 * ������{���̍X�V�inull �l�֍X�V�j
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>null �l�� UPDATE �ł��鎖</li>
	 * </ul>
	 */
	@Test
	public void updateHousingToNullTest() throws Exception {
		
		// updateHousingTest�@���g�p���āA�S�t�B�[���h�ɒl��ݒ肷��B
		updateHousingTest();
		
		// ���̓t�H�[���𐶐����čŒ���̒l�̂ݐݒ肷��B
		HousingForm inputForm = this.formFactory.createHousingForm();

		inputForm.setSysHousingCd("SYSHOU00001");						// �X�V�ΏۃV�X�e������CD
		inputForm.setSysBuildingCd("SYSBLD0002");						// �V�X�e������CD
		inputForm.setDisplayHousingName("HNAME0001");					// �\���p������

		
		// �e�X�g�N���X���s
		this.housingManage.updateHousing(inputForm, "updUserId1");

		
		// ������{���̃`�F�b�N
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00001");

		
		// �����ԍ�
		Assert.assertNull("�����ԍ��� null �ł��鎖�B", housingInfo.getHousingCd());
		// �\���p�������i�J�i�j
		Assert.assertNull("�\���p�������i�J�i�j�� null �ł��鎖�B", housingInfo.getDisplayHousingNameKana());
		// �����ԍ�
		Assert.assertNull("�����ԍ��� null �ł��鎖�B", housingInfo.getRoomNo());
		// �����E���i
		Assert.assertNull("�����E���i�� null �ł��鎖�B", housingInfo.getPrice());
		// �Ǘ���
		Assert.assertNull("�Ǘ�� null �ł��鎖�B", housingInfo.getUpkeep());
		// ���v��
		Assert.assertNull("���v� null �ł��鎖�B", housingInfo.getCommonAreaFee());
		// �C�U�ϗ���
		Assert.assertNull("�C�U�ϗ�� null �ł��鎖�B", housingInfo.getMenteFee());
		// �~��
		Assert.assertNull("�~���� null �ł��鎖�B", housingInfo.getSecDeposit());
		// �~���P��
		Assert.assertNull("�~���P�ʂ� null �ł��鎖�B", housingInfo.getSecDepositCrs());
		// �ۏ؋�
		Assert.assertNull("�ۏ؋��� null �ł��鎖�B", housingInfo.getBondChrg());
		// �ۏ؋��P��
		Assert.assertNull("�ۏ؋��P�ʂ� null �ł��鎖�B", housingInfo.getBondChrgCrs());
		// �~������敪
		Assert.assertNull("�~������敪�� null �ł��鎖�B", housingInfo.getDepositDiv());
		// �~������z
		Assert.assertNull("�~������z�� null �ł��鎖�B", housingInfo.getDeposit());
		// �~������P��
		Assert.assertNull("�~������P�ʂ� null �ł��鎖�B", housingInfo.getDepositCrs());
		// �Ԏ�CD
		Assert.assertNull("�Ԏ�CD�� null �ł��鎖�B", housingInfo.getLayoutCd());
		// �Ԏ�ڍ׃R�����g
		Assert.assertNull("�Ԏ�ڍ׃R�����g�� null �ł��鎖�B", housingInfo.getLayoutComment());
		// �����̊K��
		Assert.assertNull("�����̊K���R�����g�� null �ł��鎖�B", housingInfo.getFloorNo());
		// �����̊K���R�����g
		Assert.assertNull("�����̊K���R�����g�� null �ł��鎖�B", housingInfo.getFloorNoNote());
		// �y�n�ʐ�
		Assert.assertNull("�y�n�ʐς� null �ł��鎖�B", housingInfo.getLandArea());
		// �y�n�ʐ�_�⑫
		Assert.assertNull("�y�n�ʐ�_�⑫�� null �ł��鎖�B", housingInfo.getLandAreaMemo());
		// ��L�ʐ�
		Assert.assertNull("��L�ʐς� null �ł��鎖�B", housingInfo.getPersonalArea());
		// ��L�ʐ�_�⑫
		Assert.assertNull("��L�ʐ�_�⑫�� null �ł��鎖�B", housingInfo.getPersonalAreaMemo());
		// ������ԃt���O
		Assert.assertNull("������ԃt���O�� null �ł��鎖�B", housingInfo.getMoveinFlg());
		// ���ԏ�̏�
		Assert.assertNull("���ԏ�̏󋵂� null �ł��鎖�B", housingInfo.getParkingSituation());
		// ���ԏ��̗L��
		Assert.assertNull("���ԏ��̗L���� null �ł��鎖�B", housingInfo.getParkingEmpExist());
		// �\���p���ԏ���
		Assert.assertNull("�\���p���ԏ��� null �ł��鎖�B", housingInfo.getDisplayParkingInfo());
		// ���̌���
		Assert.assertNull("���̌����� null �ł��鎖�B", housingInfo.getWindowDirection());
		// ��{���R�����g
		Assert.assertNull("��{���R�����g�� null �ł��鎖�B", housingInfo.getBasicComment());
		
		// �A�C�R�����@�́A������{���X�V�ł͏����ΏۊO�Ȃ̂ŁA���̒l�ł��鎖���`�F�b�N����B
		Assert.assertEquals("�A�C�R����񂪌��̒l�ł��鎖", "dummyIcon1", housingInfo.getIconCd());

		
		
	}
	
	
	/**
	 * ������{���̍X�V �i�X�V�Ώۂ����݂��Ȃ��ꍇ�j
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>NotFoundException ���X���[����鎖</li>
	 * </ul>
	 */
	@Test(expected=NotFoundException.class)
	public void updateHousingFailTest1() throws Exception {

		// �e�X�g�f�[�^�������o�^����B
		initData();


		// ���̓t�H�[���𐶐����ď����l��ݒ�
		HousingForm inputForm = this.formFactory.createHousingForm();

		// ���݂��Ȃ��V�X�e������CD ��ݒ�
		inputForm.setSysHousingCd("SYSHOU10001");
		
		// �e�X�g�N���X���s
		this.housingManage.updateHousing(inputForm, "updUserId1");
	}
	

	
	/**
	 * ������{���̍X�V �i�V�X�e������CD �����݂��Ȃ��ꍇ�j
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>NotFoundException ���X���[����鎖</li>
	 * </ul>
	 */
	@Test(expected=NotFoundException.class)
	public void updateHousingFailTest2() throws Exception {

		// �e�X�g�f�[�^�������o�^����B
		initData();


		// ���̓t�H�[���𐶐����ď����l��ݒ�
		HousingForm inputForm = this.formFactory.createHousingForm();

		// �V�X�e������CD ��ݒ�
		inputForm.setSysHousingCd("SYSHOU00001");

		// ���݂��Ȃ��V�X�e������CD ��ݒ�
		inputForm.setSysBuildingCd("SYSBLD2002");
		
		// �e�X�g�N���X���s
		this.housingManage.updateHousing(inputForm, "updUserId1");
	}
	
	
	
	
	/**
	 * �����ڏ��̍X�V �i�X�V�ΏۂƂȂ镨���ڍ׏�񂪑��݂��Ȃ��ꍇ�ŁA�t�����́j
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�����ڍ׏�񂪐V���ȃ��R�[�h�Ƃ��Ēǉ�����鎖</li>
	 *     <li>������{���̍X�V���A�X�V�҂݂̂��X�V����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void updateHousingDtlNonFullDataTest() throws Exception {

		// �e�X�g�f�[�^�������o�^����B
		initData();

		// ���̓t�H�[���̍쐬
		HousingDtlForm inputForm = initInputDtlForm("SYSHOU00001");

		// �e�X�g�N���X���s
		this.housingManage.updateHousingDtl(inputForm, "updUserId1");

		// �����ڍ׏��̃`�F�b�N
		chkHousingDtl("SYSHOU00001");
		

		// ������{���̍X�V���t�`�F�b�N
		chkHousingTimestamp("SYSHOU00001", "updUserId1");
		
	}

	

	/**
	 * �����ڏ��̍X�V �i�X�V�ΏۂƂȂ镨���ڍ׏�񂪑��݂��Ȃ��ꍇ�ŁA�ŏ����́j
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�����ڍ׏�񂪐V���ȃ��R�[�h�Ƃ��Ēǉ�����鎖</li>
	 *     <li>������{���̍X�V���A�X�V�҂݂̂��X�V����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void updateHousingDtlNonMinDataTest() throws Exception {

		// �e�X�g�f�[�^�������o�^����B
		initData();

		// ���̓t�H�[���̍쐬
		HousingDtlForm inputForm = this.formFactory.createHousingDtlForm();
		
		// �Œ���̒l�̂ݓ���
		inputForm.setSysHousingCd("SYSHOU00001");

		// �e�X�g�N���X���s
		this.housingManage.updateHousingDtl(inputForm, "updUserId1");

		// �����ڍ׏��̃`�F�b�N
		HousingDtlInfo housingDtlInfo = this.housingDtlInfoDAO.selectByPK("SYSHOU00001");

		Assert.assertNull("�p�r�n��CD�̓o�^�l�� null �ł��鎖", housingDtlInfo.getUsedAreaCd());
		Assert.assertNull("����`�ԋ敪�� null �ł��鎖", housingDtlInfo.getTransactTypeDiv());
		Assert.assertNull("�\���p�_����Ԃ� null �ł��鎖", housingDtlInfo.getDisplayContractTerm());
		Assert.assertNull("�y�n������ null �ł��鎖", housingDtlInfo.getLandRight());
		Assert.assertNull("�����\�����t���O�� null �ł��鎖", housingDtlInfo.getMoveinTiming());
		Assert.assertNull("�����\������ null �ł��鎖", housingDtlInfo.getMoveinTimingDay());
		Assert.assertNull("�����\�����R�����g�� null �ł��鎖", housingDtlInfo.getMoveinNote());
		Assert.assertNull("�\���p�����\������ null �ł��鎖", housingDtlInfo.getDisplayMoveinTiming());
		Assert.assertNull("�\���p������������ null �ł��鎖", housingDtlInfo.getDisplayMoveinProviso());
		Assert.assertNull("�Ǘ��`�ԁE������ null �ł��鎖", housingDtlInfo.getUpkeepType());
		Assert.assertNull("�Ǘ���Ђ� null �ł��鎖", housingDtlInfo.getUpkeepCorp());
		Assert.assertNull("�X�V���� null �ł��鎖", housingDtlInfo.getRenewChrg());
		Assert.assertNull("�X�V���P�ʂ� null �ł��鎖", housingDtlInfo.getRenewChrgCrs());
		Assert.assertNull("�X�V������ null �ł��鎖", housingDtlInfo.getRenewChrgName());
		Assert.assertNull("�X�V�萔���� null �ł��鎖", housingDtlInfo.getRenewDue());
		Assert.assertNull("�X�V�萔���P�ʂ� null �ł��鎖", housingDtlInfo.getRenewDueCrs());
		Assert.assertNull("����萔���� null �ł��鎖", housingDtlInfo.getBrokerageChrg());
		Assert.assertNull("����萔���P�ʂ� null �ł��鎖", housingDtlInfo.getBrokerageChrgCrs());
		Assert.assertNull("���������� null �ł��鎖", housingDtlInfo.getChangeKeyChrg());
		Assert.assertNull("���ۗL���� null �ł��鎖", housingDtlInfo.getInsurExist());
		Assert.assertNull("���ۗ����� null �ł��鎖", housingDtlInfo.getInsurChrg());
		Assert.assertNull("���۔N���� null �ł��鎖", housingDtlInfo.getInsurTerm());
		Assert.assertNull("���۔F�胉���N�� null �ł��鎖", housingDtlInfo.getInsurLank());
		Assert.assertNull("����p�� null �ł��鎖", housingDtlInfo.getOtherChrg());
		Assert.assertNull("�ړ��󋵂� null �ł��鎖", housingDtlInfo.getContactRoad());
		Assert.assertNull("�ړ�����/������ null �ł��鎖", housingDtlInfo.getContactRoadDir());
		Assert.assertNull("�������S�� null �ł��鎖", housingDtlInfo.getPrivateRoad());
		Assert.assertNull("�o���R�j�[�ʐς� null �ł��鎖", housingDtlInfo.getBalconyArea());
		Assert.assertNull("���L������ null �ł��鎖", housingDtlInfo.getSpecialInstruction());
		Assert.assertNull("�ڍ׃R�����g�� null �ł��鎖", housingDtlInfo.getDtlComment());

		// ������{���̍X�V���t�`�F�b�N
		chkHousingTimestamp("SYSHOU00001", "updUserId1");

	}



	/**
	 * �����ڏ��̍X�V �i�X�V�ΏۂƂȂ镨���ڍ׏�񂪑��݂���ꍇ�j
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�����ڍ׏�񂪓��͒l�ōX�V����Ă��鎖</li>
	 *     <li>������{���̍X�V���A�X�V�҂݂̂��X�V����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void updateHousingDtlFullDataTest() throws Exception {

		// �e�X�g�f�[�^�������o�^����B
		initData();

		// ���̓t�H�[���̍쐬
		HousingDtlForm inputForm = this.formFactory.createHousingDtlForm();
		
		// �Œ���̒l�̂ݓ���
		inputForm.setSysHousingCd("SYSHOU00001");

		// �e�X�g�N���X���s
		this.housingManage.updateHousingDtl(inputForm, "updUserId1");

		// �e�X�g�f�[�^�̑��݃`�F�b�N
		HousingDtlInfo housingDtlInfo = this.housingDtlInfoDAO.selectByPK("SYSHOU00001");
		Assert.assertNotNull("�o�^�����e�X�g�f�[�^�����݂��鎖", housingDtlInfo);
		
		
		// ���̓t�H�[���̍쐬
		inputForm = initInputDtlForm("SYSHOU00001");

		// �e�X�g�N���X���s
		this.housingManage.updateHousingDtl(inputForm, "updUserId2");

		// �����ڍ׏��̃`�F�b�N
		chkHousingDtl("SYSHOU00001");

		// ������{���̍X�V���t�`�F�b�N
		chkHousingTimestamp("SYSHOU00001", "updUserId2");

	}


	
	/**
	 * �����ڏ��̍X�V �i�X�V�ΏۂƂȂ镨���ڍ׏���null�ɍX�V�j
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�����ڍ׏�񂪓��͒l�ōX�V����Ă��鎖</li>
	 *     <li>������{���̍X�V���A�X�V�҂݂̂��X�V����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void updateHousingDtlToNullTest() throws Exception {

		// updateHousingDtlNonFullDataTest ���g�p���āA�S�t�B�[���h�ɒl��ݒ肷��B
		updateHousingDtlNonFullDataTest();

		
		// ���̓t�H�[���̍쐬
		HousingDtlForm inputForm = this.formFactory.createHousingDtlForm();
		
		// �Œ���̒l�̂ݓ���
		inputForm.setSysHousingCd("SYSHOU00001");


		// �e�X�g�N���X���s
		this.housingManage.updateHousingDtl(inputForm, "updUserId2");
		
		
		// �����ڍ׏��̃`�F�b�N
		HousingDtlInfo housingDtlInfo = this.housingDtlInfoDAO.selectByPK("SYSHOU00001");

		Assert.assertNull("�p�r�n��CD�̓o�^�l�� null �ł��鎖", housingDtlInfo.getUsedAreaCd());
		Assert.assertNull("����`�ԋ敪�� null �ł��鎖", housingDtlInfo.getTransactTypeDiv());
		Assert.assertNull("�\���p�_����Ԃ� null �ł��鎖", housingDtlInfo.getDisplayContractTerm());
		Assert.assertNull("�y�n������ null �ł��鎖", housingDtlInfo.getLandRight());
		Assert.assertNull("�����\�����t���O�� null �ł��鎖", housingDtlInfo.getMoveinTiming());
		Assert.assertNull("�����\������ null �ł��鎖", housingDtlInfo.getMoveinTimingDay());
		Assert.assertNull("�����\�����R�����g�� null �ł��鎖", housingDtlInfo.getMoveinNote());
		Assert.assertNull("�\���p�����\������ null �ł��鎖", housingDtlInfo.getDisplayMoveinTiming());
		Assert.assertNull("�\���p������������ null �ł��鎖", housingDtlInfo.getDisplayMoveinProviso());
		Assert.assertNull("�Ǘ��`�ԁE������ null �ł��鎖", housingDtlInfo.getUpkeepType());
		Assert.assertNull("�Ǘ���Ђ� null �ł��鎖", housingDtlInfo.getUpkeepCorp());
		Assert.assertNull("�X�V���� null �ł��鎖", housingDtlInfo.getRenewChrg());
		Assert.assertNull("�X�V���P�ʂ� null �ł��鎖", housingDtlInfo.getRenewChrgCrs());
		Assert.assertNull("�X�V������ null �ł��鎖", housingDtlInfo.getRenewChrgName());
		Assert.assertNull("�X�V�萔���� null �ł��鎖", housingDtlInfo.getRenewDue());
		Assert.assertNull("�X�V�萔���P�ʂ� null �ł��鎖", housingDtlInfo.getRenewDueCrs());
		Assert.assertNull("����萔���� null �ł��鎖", housingDtlInfo.getBrokerageChrg());
		Assert.assertNull("����萔���P�ʂ� null �ł��鎖", housingDtlInfo.getBrokerageChrgCrs());
		Assert.assertNull("���������� null �ł��鎖", housingDtlInfo.getChangeKeyChrg());
		Assert.assertNull("���ۗL���� null �ł��鎖", housingDtlInfo.getInsurExist());
		Assert.assertNull("���ۗ����� null �ł��鎖", housingDtlInfo.getInsurChrg());
		Assert.assertNull("���۔N���� null �ł��鎖", housingDtlInfo.getInsurTerm());
		Assert.assertNull("���۔F�胉���N�� null �ł��鎖", housingDtlInfo.getInsurLank());
		Assert.assertNull("����p�� null �ł��鎖", housingDtlInfo.getOtherChrg());
		Assert.assertNull("�ړ��󋵂� null �ł��鎖", housingDtlInfo.getContactRoad());
		Assert.assertNull("�ړ�����/������ null �ł��鎖", housingDtlInfo.getContactRoadDir());
		Assert.assertNull("�������S�� null �ł��鎖", housingDtlInfo.getPrivateRoad());
		Assert.assertNull("�o���R�j�[�ʐς� null �ł��鎖", housingDtlInfo.getBalconyArea());
		Assert.assertNull("���L������ null �ł��鎖", housingDtlInfo.getSpecialInstruction());
		Assert.assertNull("�ڍ׃R�����g�� null �ł��鎖", housingDtlInfo.getDtlComment());

	}
	
	
	
	
	
	
	
	
	
	

	/**
	 * �����ڏ��̍X�V �i�e�ƂȂ镨����{��񂪑��݂��Ȃ��ꍇ�j
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>NotFoundException ���X���[����鎖</li>
	 * </ul>
	 */
	@Test(expected=NotFoundException.class)
	public void updateHousingDtlFailTest() throws Exception {

		// �e�X�g�f�[�^�̍쐬

		// ���̓t�H�[���̍쐬
		HousingDtlForm inputForm = this.formFactory.createHousingDtlForm();
		
		// �Œ���̒l�̂ݓ���
		inputForm.setSysHousingCd("SYSHOU00001");

		// �e�X�g�N���X���s
		this.housingManage.updateHousingDtl(inputForm, "updUserId1");

	}

	
	/**
	 * �ݔ�����o�^����B�@�i�T���̔z����A�����f�[�^�Ȃ��j
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�o�^���ꂽ�ݔ�CD �̌�������������</li>
	 *     <li>�o�^���ꂽ�ݔ�CD ����������</li>
	 *     <li>�w��ΏۊO�̐ݔ�CD ���폜����Ă��Ȃ���</li>
	 *     <li>�X�V�Ώە�����{���̃A�C�R����񂪓��͒l�ōX�V����Ă��鎖</li>
	 *     <li>�X�V�ΏۈȊO�̕�����{���̃A�C�R����񂪍X�V����Ă��Ȃ���</li>
	 *     <li>�X�V�Ώە�����{���̍X�V���A�X�V�҂݂̂��X�V����Ă��鎖</li>
	 *     <li>�X�V�ΏۈȊO�̕�����{���̍X�V���A�X�V�҂݂̂��X�V����Ă��Ȃ���</li>
	 * </ul>
	 */
	@Test
	public void updateHousingEquipTest1() throws Exception {
		
		// �e�X�g�f�[�^�������o�^����B
		initData();

		// ������f�[�^�폜�̊m�F�p�ɕʂ̃V�X�e������CD �Őݔ�����o�^���Ă���
		HousingEquipInfo equipInfo = new HousingEquipInfo();
		equipInfo.setSysHousingCd("SYSHOU00002");
		equipInfo.setEquipCd("000001");
		this.housingEquipInfoDAO.insert(new HousingEquipInfo[] {equipInfo});
		
		
		
		// �t�H�[�����쐬���ē��͒l��ݒ肷��B
		HousingEquipForm inputForm = this.formFactory.createHousingEquipForm();
		inputForm.setSysHousingCd("SYSHOU00001");

		String equipCd[] = new String[5];
		equipCd[0] = "000001";
		equipCd[1] = "000002";
		equipCd[2] = "000003";
		equipCd[3] = "000004";
		equipCd[4] = "000005";
		inputForm.setEquipCd(equipCd);

		// �e�X�g���\�b�h���s
		this.housingManage.updateHousingEquip(inputForm, "editUserId2");


		// ���s���ʂ̊m�F�i�X�V�Ώۂ̐ݔ����j
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00001");

		List<HousingEquipInfo> list = this.housingEquipInfoDAO.selectByFilter(criteria);
		
		Assert.assertEquals("�o�^��������������", 5, list.size());
		
		for (HousingEquipInfo equip : list) {
			if (!equip.getEquipCd().equals("000001") && !equip.getEquipCd().equals("000002") && 
				!equip.getEquipCd().equals("000003") && !equip.getEquipCd().equals("000004") &&
				!equip.getEquipCd().equals("000005")) {
				
				Assert.fail("�o�^���ꂽ�ݔ�CD ����������:" + equip.getEquipCd());
			}
		}

		
		// ���s���ʂ̊m�F�i�X�V�ΏۊO�̐ݔ����j
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00002");
		
		list = this.housingEquipInfoDAO.selectByFilter(criteria);

		Assert.assertEquals("�o�^��������������", 1, list.size());
		
		for (HousingEquipInfo equip : list) {
			if (!equip.getEquipCd().equals("000001")) {
				Assert.fail("�o�^���ꂽ�ݔ�CD ����������:" + equip.getEquipCd());
			}
		}

		
		// �X�V�Ώە�����{���̃A�C�R�������m�F����B
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00001");
		Assert.assertEquals("�X�V�Ώۂ̃A�C�R����񂪍X�V����Ă��鎖", "000001,000002,000003,000004,000005",  housingInfo.getIconCd());
		

		// �X�V�ΏۊO������{���̃A�C�R�������m�F����B
		housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00002");
		Assert.assertEquals("�X�V�ΏۊO�̃A�C�R����񂪍X�V����Ă��Ȃ���", "dummyIcon2", housingInfo.getIconCd());

		
		// ������{���̍X�V���t�`�F�b�N
		chkHousingTimestamp("SYSHOU00001", "editUserId2");

	}
	

	
	/**
	 * �ݔ�����o�^����B�@�i�T���̔z����A�����f�[�^����j
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�o�^���ꂽ�ݔ�CD �̌�������������</li>
	 *     <li>�o�^���ꂽ�ݔ�CD ����������</li>
	 *     <li>�o�^�O�̐ݔ�CD ���폜����Ă��鎖</li>
	 *     <li>�w��ΏۊO�̐ݔ�CD ���폜����Ă��Ȃ���</li>
	 *     <li>�X�V�Ώە�����{���̃A�C�R����񂪓��͒l�ōX�V����Ă��鎖</li>
	 *     <li>�X�V�ΏۈȊO�̕�����{���̃A�C�R����񂪍X�V����Ă��Ȃ���</li>
	 *     <li>�X�V�Ώە�����{���̍X�V���A�X�V�҂݂̂��X�V����Ă��鎖</li>
	 *     <li>�X�V�ΏۈȊO�̕�����{���̍X�V���A�X�V�҂݂̂��X�V����Ă��Ȃ���</li>
	 * </ul>
	 */
	@Test
	public void updateHousingEquipTest2() throws Exception {
		
		// �e�X�g�f�[�^�������o�^����B
		initData();

		// �X�V�O�ƂȂ�ݔ����̓o�^
		HousingEquipInfo equipInfo = new HousingEquipInfo();
		equipInfo.setSysHousingCd("SYSHOU00001");
		equipInfo.setEquipCd("000006");
		this.housingEquipInfoDAO.insert(new HousingEquipInfo[] {equipInfo});


		// ������f�[�^�폜�̊m�F�p�ɕʂ̃V�X�e������CD �Őݔ�����o�^���Ă���
		equipInfo = new HousingEquipInfo();
		equipInfo.setSysHousingCd("SYSHOU00002");
		equipInfo.setEquipCd("000001");
		this.housingEquipInfoDAO.insert(new HousingEquipInfo[] {equipInfo});
		
		
		
		// �t�H�[�����쐬���ē��͒l��ݒ肷��B
		HousingEquipForm inputForm = this.formFactory.createHousingEquipForm();
		inputForm.setSysHousingCd("SYSHOU00001");

		String equipCd[] = new String[5];
		equipCd[0] = "000001";
		equipCd[1] = "000002";
		equipCd[2] = "000003";
		equipCd[3] = "000004";
		equipCd[4] = "000005";
		inputForm.setEquipCd(equipCd);

		// �e�X�g���\�b�h���s
		this.housingManage.updateHousingEquip(inputForm, "editUserId2");


		// ���s���ʂ̊m�F�i�X�V�Ώۂ̐ݔ����j
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00001");

		List<HousingEquipInfo> list = this.housingEquipInfoDAO.selectByFilter(criteria);
		
		Assert.assertEquals("�o�^��������������", 5, list.size());
		
		for (HousingEquipInfo equip : list) {
			if (!equip.getEquipCd().equals("000001") && !equip.getEquipCd().equals("000002") && 
				!equip.getEquipCd().equals("000003") && !equip.getEquipCd().equals("000004") &&
				!equip.getEquipCd().equals("000005")) {
				
				Assert.fail("�o�^���ꂽ�ݔ�CD ����������:" + equip.getEquipCd());
			}
		}

		
		// ���s���ʂ̊m�F�i�X�V�ΏۊO�̐ݔ����j
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00002");
		
		list = this.housingEquipInfoDAO.selectByFilter(criteria);

		Assert.assertEquals("�o�^��������������", 1, list.size());
		
		for (HousingEquipInfo equip : list) {
			if (!equip.getEquipCd().equals("000001")) {
				Assert.fail("�o�^���ꂽ�ݔ�CD ����������:" + equip.getEquipCd());
			}
		}

		
		// �X�V�Ώە�����{���̃A�C�R�������m�F����B
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00001");
		Assert.assertEquals("�X�V�Ώۂ̃A�C�R����񂪍X�V����Ă��鎖", "000001,000002,000003,000004,000005",  housingInfo.getIconCd());
		

		// �X�V�ΏۊO������{���̃A�C�R�������m�F����B
		housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00002");
		Assert.assertEquals("�X�V�ΏۊO�̃A�C�R����񂪍X�V����Ă��Ȃ���", "dummyIcon2", housingInfo.getIconCd());
		
		
		// ������{���̍X�V���t�`�F�b�N
		chkHousingTimestamp("SYSHOU00001", "editUserId2");

	}


	
	/**
	 * �ݔ�����o�^����B�@�i�R���̔z����A�u�����N�Anull ���܂ށB�@�����f�[�^�Ȃ��j
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�o�^���ꂽ�ݔ�CD �̌�������������</li>
	 *     <li>�o�^���ꂽ�ݔ�CD ����������</li>
	 *     <li>�w��ΏۊO�̐ݔ�CD ���폜����Ă��Ȃ���</li>
	 *     <li>�X�V�Ώە�����{���̃A�C�R����񂪓��͒l�ōX�V����Ă��鎖</li>
	 *     <li>�X�V�ΏۈȊO�̕�����{���̃A�C�R����񂪍X�V����Ă��Ȃ���</li>
	 *     <li>�X�V�Ώە�����{���̍X�V���A�X�V�҂݂̂��X�V����Ă��鎖</li>
	 *     <li>�X�V�ΏۈȊO�̕�����{���̍X�V���A�X�V�҂݂̂��X�V����Ă��Ȃ���</li>
	 * </ul>
	 */
	@Test
	public void updateHousingEquipTest3() throws Exception {
		
		// �e�X�g�f�[�^�������o�^����B
		initData();

		// �t�H�[�����쐬���ē��͒l��ݒ肷��B
		HousingEquipForm inputForm = this.formFactory.createHousingEquipForm();
		inputForm.setSysHousingCd("SYSHOU00001");

		String equipCd[] = new String[5];
		equipCd[0] = "000001";
		equipCd[1] = "000002";
		equipCd[2] = null;
		equipCd[3] = "000004";
		equipCd[4] = "";
		inputForm.setEquipCd(equipCd);

		// �e�X�g���\�b�h���s
		this.housingManage.updateHousingEquip(inputForm, "editUserId2");


		// ���s���ʂ̊m�F�i�X�V�Ώۂ̐ݔ����j
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00001");

		List<HousingEquipInfo> list = this.housingEquipInfoDAO.selectByFilter(criteria);
		
		Assert.assertEquals("�o�^��������������", 3, list.size());
		
		for (HousingEquipInfo equip : list) {
			if (!equip.getEquipCd().equals("000001") && !equip.getEquipCd().equals("000002") && 
				!equip.getEquipCd().equals("000004")) {

				Assert.fail("�o�^���ꂽ�ݔ�CD ����������:" + equip.getEquipCd());
			}
		}
		
		
		// �X�V�Ώە�����{���̃A�C�R�������m�F����B
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00001");
		Assert.assertEquals("�X�V�Ώۂ̃A�C�R����񂪍X�V����Ă��鎖", "000001,000002,000004",  housingInfo.getIconCd());
		

		// �X�V�ΏۊO������{���̃A�C�R�������m�F����B
		housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00002");
		Assert.assertEquals("�X�V�ΏۊO�̃A�C�R����񂪍X�V����Ă��Ȃ���", "dummyIcon2", housingInfo.getIconCd());

		
		// ������{���̍X�V���t�`�F�b�N
		chkHousingTimestamp("SYSHOU00001", "editUserId2");

	}


	
	/**
	 * �ݔ�����o�^����B�@�i5���̋󕶎���z����j
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�����̐ݔ�CD ���폜����Ă��鎖</li>
	 *     <li>�w��ΏۊO�̐ݔ�CD ���폜����Ă��Ȃ���</li>
	 *     <li>�X�V�Ώە�����{���̃A�C�R����� null �ōX�V����Ă��鎖</li>
	 *     <li>�X�V�ΏۈȊO�̕�����{���̃A�C�R����񂪍X�V����Ă��Ȃ���</li>
	 *     <li>�X�V�Ώە�����{���̍X�V���A�X�V�҂݂̂��X�V����Ă��鎖</li>
	 *     <li>�X�V�ΏۈȊO�̕�����{���̍X�V���A�X�V�҂݂̂��X�V����Ă��Ȃ���</li>
	 * </ul>
	 */
	@Test
	public void updateHousingEquipTest4() throws Exception {
		
		// �e�X�g�f�[�^�������o�^����B
		initData();

		// �X�V�O�ƂȂ�ݔ����̓o�^
		HousingEquipInfo equipInfo = new HousingEquipInfo();
		equipInfo.setSysHousingCd("SYSHOU00001");
		equipInfo.setEquipCd("000006");
		this.housingEquipInfoDAO.insert(new HousingEquipInfo[] {equipInfo});

		
		// �t�H�[�����쐬���ē��͒l��ݒ肷��B
		HousingEquipForm inputForm = this.formFactory.createHousingEquipForm();
		inputForm.setSysHousingCd("SYSHOU00001");

		String equipCd[] = new String[5];
		equipCd[0] = "";
		equipCd[1] = "";
		equipCd[2] = "";
		equipCd[3] = "";
		equipCd[4] = "";
		inputForm.setEquipCd(equipCd);

		// �e�X�g���\�b�h���s
		this.housingManage.updateHousingEquip(inputForm, "editUserId2");


		// ���s���ʂ̊m�F�i�X�V�Ώۂ̐ݔ����j
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00001");

		List<HousingEquipInfo> list = this.housingEquipInfoDAO.selectByFilter(criteria);
		
		Assert.assertEquals("�o�^��������������", 0, list.size());
		
		
		// �X�V�Ώە�����{���̃A�C�R�������m�F����B
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00001");
		Assert.assertNull("�X�V�Ώۂ̃A�C�R����� null �ł��鎖", housingInfo.getIconCd());
		

		// �X�V�ΏۊO������{���̃A�C�R�������m�F����B
		housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00002");
		Assert.assertEquals("�X�V�ΏۊO�̃A�C�R����񂪍X�V����Ă��Ȃ���", "dummyIcon2", housingInfo.getIconCd());

		
		// ������{���̍X�V���t�`�F�b�N
		chkHousingTimestamp("SYSHOU00001", "editUserId2");

	}


	
	/**
	 * �ݔ�����o�^����B�@�i�z�񎩑̂� null�j
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�����̐ݔ�CD ���폜����Ă��鎖</li>
	 *     <li>�w��ΏۊO�̐ݔ�CD ���폜����Ă��Ȃ���</li>
	 *     <li>�X�V�Ώە�����{���̃A�C�R����� null �ōX�V����Ă��鎖</li>
	 *     <li>�X�V�ΏۈȊO�̕�����{���̃A�C�R����񂪍X�V����Ă��Ȃ���</li>
	 *     <li>�X�V�Ώە�����{���̍X�V���A�X�V�҂݂̂��X�V����Ă��鎖</li>
	 *     <li>�X�V�ΏۈȊO�̕�����{���̍X�V���A�X�V�҂݂̂��X�V����Ă��Ȃ���</li>
	 * </ul>
	 */
	@Test
	public void updateHousingEquipTest5() throws Exception {
		
		// �e�X�g�f�[�^�������o�^����B
		initData();

		// �X�V�O�ƂȂ�ݔ����̓o�^
		HousingEquipInfo equipInfo = new HousingEquipInfo();
		equipInfo.setSysHousingCd("SYSHOU00001");
		equipInfo.setEquipCd("000006");
		this.housingEquipInfoDAO.insert(new HousingEquipInfo[] {equipInfo});

		
		// �t�H�[�����쐬���ē��͒l��ݒ肷��B
		HousingEquipForm inputForm = this.formFactory.createHousingEquipForm();
		inputForm.setSysHousingCd("SYSHOU00001");
		inputForm.setEquipCd(null);

		// �e�X�g���\�b�h���s
		this.housingManage.updateHousingEquip(inputForm, "editUserId2");


		// ���s���ʂ̊m�F�i�X�V�Ώۂ̐ݔ����j
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00001");

		List<HousingEquipInfo> list = this.housingEquipInfoDAO.selectByFilter(criteria);
		
		Assert.assertEquals("�o�^��������������", 0, list.size());
		
		
		// �X�V�Ώە�����{���̃A�C�R�������m�F����B
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00001");
		Assert.assertNull("�X�V�Ώۂ̃A�C�R����� null �ł��鎖", housingInfo.getIconCd());
		

		// �X�V�ΏۊO������{���̃A�C�R�������m�F����B
		housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00002");
		Assert.assertEquals("�X�V�ΏۊO�̃A�C�R����񂪍X�V����Ă��Ȃ���", "dummyIcon2", housingInfo.getIconCd());

		
		// ������{���̍X�V���t�`�F�b�N
		chkHousingTimestamp("SYSHOU00001", "editUserId2");

	}


	
	/**
	 * �ݔ�����o�^����B�@�i�e���R�[�h�Ȃ��j
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>NotFoundException ������</li>
	 * </ul>
	 */
	@Test(expected=NotFoundException.class)
	public void updateHousingEquipFailTest() throws Exception {
		
		// �t�H�[�����쐬���ē��͒l��ݒ肷��B
		HousingEquipForm inputForm = this.formFactory.createHousingEquipForm();
		inputForm.setSysHousingCd("SYSHOU00001");
		inputForm.setEquipCd(new String[]{"000001"});

		// �e�X�g���\�b�h���s
		this.housingManage.updateHousingEquip(inputForm, "editUserId2");

	}


	
	/**
	 * �����摜���ǉ������i�t�����̓P�[�X�A�R�����A�P���͋󔒍s�A�����f�[�^�Ȃ��A�������Ƀp�X�֘A�f�[�^�Ȃ��B�j
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�o�^���������������B�@�i�󔒍s�������ēo�^����Ă��鎖�B�j</li>
	 *     <li>�p�X�ɕK�v�Ȍ�����񂪖����ꍇ�A�I�[�� 9 ���g�p����Ă��鎖</li>
	 *     <li>���͂����l�œo�^����Ă��鎖</li>
	 *     <li>�}�Ԃ��������̔Ԃ���Ă��鎖</li>
	 *     <li>���C���摜�t���O���������ݒ肳��Ă��鎖</li>
	 *     <li>�c���E�����t���O���������ݒ肳��Ă��鎖</li>
	 *     <li>������{���̍X�V�ҁA�X�V�����X�V����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void addHousingImgFullTest() throws Exception {

		// �e�X�g�f�[�^�쐬
		initData();
		
		// �e�X�g�摜�t�@�C���z�u
		initImgFile();
		
		// �t�H�[���̍쐬
		HousingImgForm inputForm = this.formFactory.createHousingImgForm();

		inputForm.setSysHousingCd("SYSHOU00001");				// �V�X�e������CD
		inputForm.setTempDate("20150101");						// ���t�H���_���@�i���t�����j
		
		String[] imageType = new String[3];						// �摜�^�C�v
		imageType[0] = "00";
		imageType[1] = "";
		imageType[2] = "01";
		inputForm.setImageType(imageType);

		String[] sortOrder = new String[3];						// �\���� 
		sortOrder[0] = "1";
		sortOrder[1] = "";
		sortOrder[2] = "2";
		inputForm.setSortOrder(sortOrder);

		String[] fileName = new String[3];						// �t�@�C����
		fileName[0] = "0000000001.jpg";
		fileName[1] = "";
		fileName[2] = "0000000002.jpg";
		inputForm.setFileName(fileName);

		String[] caption = new String[3];						// �L���v�V����
		caption[0] = "�L���v�V�����P";
		caption[1] = "";
		caption[2] = "�L���v�V�����Q";
		inputForm.setCaption(caption);

		String[] imgComment = new String[3];						// �R�����g
		imgComment[0] = "���߂�ƂP";
		imgComment[1] = "";
		imgComment[2] = "���߂�ƂQ";
		inputForm.setImgComment(imgComment);
		
		
		// �e�X�g���\�b�h���s
		this.housingManage.addHousingImg(inputForm, "editUserId2");
		

		// ���s���ʊm�F
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00001");
		criteria.addOrderByClause("imageType");
		criteria.addOrderByClause("divNo");
		List<HousingImageInfo> imgList = this.housingImageInfoDAO.selectByFilter(criteria);
		
		Assert.assertEquals("�o�^��������������", 2, imgList.size());
		
		Assert.assertEquals("�摜�^�C�v����������", imageType[0], imgList.get(0).getImageType());
		Assert.assertEquals("�摜�^�C�v����������", imageType[2], imgList.get(1).getImageType());
		
		Assert.assertEquals("�}�Ԃ���������", Integer.valueOf(1), imgList.get(0).getDivNo());
		Assert.assertEquals("�}�Ԃ���������", Integer.valueOf(1), imgList.get(1).getDivNo());

		Assert.assertEquals("�\��������������", Integer.valueOf(sortOrder[0]), imgList.get(0).getSortOrder());
		Assert.assertEquals("�\��������������", Integer.valueOf(sortOrder[2]), imgList.get(1).getSortOrder());

		Assert.assertEquals("�p�X����������", "999/13/99999/SYSHOU00001/", imgList.get(0).getPathName());
		Assert.assertEquals("�p�X����������", "999/13/99999/SYSHOU00001/", imgList.get(1).getPathName());

		Assert.assertEquals("�t�@�C��������������", fileName[0], imgList.get(0).getFileName());
		Assert.assertEquals("�t�@�C��������������", fileName[2], imgList.get(1).getFileName());

		Assert.assertEquals("���C���摜�t���O����������", "1", imgList.get(0).getMainImageFlg());
		Assert.assertEquals("���C���摜�t���O����������", "1", imgList.get(1).getMainImageFlg());

		Assert.assertEquals("�L���v�V��������������", caption[0], imgList.get(0).getCaption());
		Assert.assertEquals("�L���v�V��������������", caption[2], imgList.get(1).getCaption());

		Assert.assertEquals("�R�����g����������", imgComment[0], imgList.get(0).getImgComment());
		Assert.assertEquals("�R�����g����������", imgComment[2], imgList.get(1).getImgComment());

		Assert.assertEquals("�c���E�����t���O����������", "1", imgList.get(0).getHwFlg());
		Assert.assertEquals("�c���E�����t���O����������", "0", imgList.get(1).getHwFlg());

		
		// ������{���̃^�C���^���v�m�F
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00001");
		
		// �o�^���i���t���̂݁j
		String now10 = StringUtils.dateToString(DateUtils.addDays(new Date(), -10));
		String date = StringUtils.dateToString(housingInfo.getInsDate());
		Assert.assertEquals("�o�^�����X�V����Ă��Ȃ����B", now10, date);

		// �o�^��
		Assert.assertEquals("�o�^�҂��X�V����Ă��Ȃ���", "dummInsID1", housingInfo.getInsUserId());

		// �ŏI�X�V���i���t���̂݁j
		String now = StringUtils.dateToString(new Date());
		date = StringUtils.dateToString(housingInfo.getUpdDate());
		Assert.assertEquals("�ŏI�X�V�����V�X�e�����t�ƈ�v���鎖�B", now, date);

		// �ŏI�X�V��
		Assert.assertEquals("�ŏI�X�V�҂������œn�����l�ƈ�v���鎖�B", "editUserId2", housingInfo.getUpdUserId());

	}
	
	
	
	/**
	 * �����摜���ǉ������i�t�����̓P�[�X�A�R�����A�P���͋󔒍s�A�����f�[�^����A�p�X�ɕK�v�ȃf�[�^����j
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�o�^���������������B�@�i�󔒍s�������ēo�^����Ă��鎖�B�j</li>
	 *     <li>�p�X�ɕK�v�Ȍ�����񂪎g�p����Ă��鎖</li>
	 *     <li>�����f�[�^�����݂���ꍇ�ł��}�ԁA���C���摜�t���O���������ݒ肳��鎖</li>
	 * </ul>
	 */
	@Test
	public void addHousingImgFullTest2() throws Exception {

		// �e�X�g�f�[�^�쐬
		initData();
		
		// �e�X�g�摜�t�@�C���z�u
		initImgFile();
		
		// ������ʁA�s�撬��CD �ݒ�
		BuildingInfo buildingInfo = this.buildingInfoDAO.selectByPK("SYSBLD0001");
		buildingInfo.setHousingKindCd("123");
		buildingInfo.setAddressCd("12345");
		this.buildingInfoDAO.update(new BuildingInfo[]{buildingInfo});


		// �t�H�[���̍쐬
		HousingImgForm inputForm = this.formFactory.createHousingImgForm();

		inputForm.setSysHousingCd("SYSHOU00001");				// �V�X�e������CD
		inputForm.setTempDate("20150101");						// ���t�H���_���@�i���t�����j
		
		String[] imageType = new String[3];						// �摜�^�C�v
		imageType[0] = "00";
		imageType[1] = "";
		imageType[2] = "01";
		inputForm.setImageType(imageType);

		String[] sortOrder = new String[3];						// �\���� 
		sortOrder[0] = "1";
		sortOrder[1] = "";
		sortOrder[2] = "2";
		inputForm.setSortOrder(sortOrder);

		String[] fileName = new String[3];						// �t�@�C����
		fileName[0] = "0000000001.jpg";
		fileName[1] = "";
		fileName[2] = "0000000002.jpg";
		inputForm.setFileName(fileName);

		String[] caption = new String[3];						// �L���v�V����
		caption[0] = "�L���v�V�����P";
		caption[1] = "";
		caption[2] = "�L���v�V�����Q";
		inputForm.setCaption(caption);

		String[] imgComment = new String[3];						// �R�����g
		imgComment[0] = "���߂�ƂP";
		imgComment[1] = "";
		imgComment[2] = "���߂�ƂQ";
		inputForm.setImgComment(imgComment);
		
		
		// �e�X�g���\�b�h���s
		this.housingManage.addHousingImg(inputForm, "editUserId1");
		

		// ���s���ʊm�F
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00001");
		criteria.addOrderByClause("imageType");
		criteria.addOrderByClause("divNo");
		List<HousingImageInfo> imgList = this.housingImageInfoDAO.selectByFilter(criteria);
		
		Assert.assertEquals("�o�^��������������", 2, imgList.size());
		
		Assert.assertEquals("�p�X����������", "123/13/12345/SYSHOU00001/", imgList.get(0).getPathName());
		Assert.assertEquals("�p�X����������", "123/13/12345/SYSHOU00001/", imgList.get(1).getPathName());

		
		// �Q��ڂ̃f�[�^����
		String[] imageType2 = new String[3];						// �摜�^�C�v
		imageType2[0] = "01";
		imageType2[1] = "";
		imageType2[2] = "01";
		inputForm.setImageType(imageType2);

		String[] sortOrder2 = new String[3];						// �\���� 
		sortOrder2[0] = "3";
		sortOrder2[1] = "";
		sortOrder2[2] = "4";
		inputForm.setSortOrder(sortOrder2);

		String[] fileName2 = new String[3];						// �t�@�C����
		fileName2[0] = "0000000003.jpg";
		fileName2[1] = "";
		fileName2[2] = "0000000004.jpg";
		inputForm.setFileName(fileName2);

		String[] caption2 = new String[3];						// �L���v�V����
		caption2[0] = "�L���v�V�����R";
		caption2[1] = "";
		caption2[2] = "�L���v�V�����S";
		inputForm.setCaption(caption2);

		String[] imgComment2 = new String[3];						// �R�����g
		imgComment2[0] = "���߂�ƂR";
		imgComment2[1] = "";
		imgComment2[2] = "���߂�ƂS";
		inputForm.setImgComment(imgComment2);

		// �e�X�g���\�b�h���s
		this.housingManage.addHousingImg(inputForm, "editUserId2");
		
		
		// ���s���ʊm�F
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00001");
		criteria.addOrderByClause("imageType");
		criteria.addOrderByClause("divNo");
		imgList = this.housingImageInfoDAO.selectByFilter(criteria);
		
		Assert.assertEquals("�o�^��������������", 4, imgList.size());
		
		Assert.assertEquals("�摜�^�C�v����������", imageType[0], imgList.get(0).getImageType());
		Assert.assertEquals("�摜�^�C�v����������", imageType[2], imgList.get(1).getImageType());
		Assert.assertEquals("�摜�^�C�v����������", imageType2[0], imgList.get(2).getImageType());
		Assert.assertEquals("�摜�^�C�v����������", imageType2[2], imgList.get(3).getImageType());
		
		Assert.assertEquals("�}�Ԃ���������", Integer.valueOf(1), imgList.get(0).getDivNo());
		Assert.assertEquals("�}�Ԃ���������", Integer.valueOf(1), imgList.get(1).getDivNo());
		Assert.assertEquals("�}�Ԃ���������", Integer.valueOf(2), imgList.get(2).getDivNo());
		Assert.assertEquals("�}�Ԃ���������", Integer.valueOf(3), imgList.get(3).getDivNo());

		Assert.assertEquals("�\��������������", Integer.valueOf(sortOrder[0]), imgList.get(0).getSortOrder());
		Assert.assertEquals("�\��������������", Integer.valueOf(sortOrder[2]), imgList.get(1).getSortOrder());
		Assert.assertEquals("�\��������������", Integer.valueOf(sortOrder2[0]), imgList.get(2).getSortOrder());
		Assert.assertEquals("�\��������������", Integer.valueOf(sortOrder2[2]), imgList.get(3).getSortOrder());

		Assert.assertEquals("�p�X����������", "123/13/12345/SYSHOU00001/", imgList.get(0).getPathName());
		Assert.assertEquals("�p�X����������", "123/13/12345/SYSHOU00001/", imgList.get(1).getPathName());
		Assert.assertEquals("�p�X����������", "123/13/12345/SYSHOU00001/", imgList.get(2).getPathName());
		Assert.assertEquals("�p�X����������", "123/13/12345/SYSHOU00001/", imgList.get(3).getPathName());

		Assert.assertEquals("�t�@�C��������������", fileName[0], imgList.get(0).getFileName());
		Assert.assertEquals("�t�@�C��������������", fileName[2], imgList.get(1).getFileName());
		Assert.assertEquals("�t�@�C��������������", fileName2[0], imgList.get(2).getFileName());
		Assert.assertEquals("�t�@�C��������������", fileName2[2], imgList.get(3).getFileName());

		Assert.assertEquals("���C���摜�t���O����������", "1", imgList.get(0).getMainImageFlg());
		Assert.assertEquals("���C���摜�t���O����������", "1", imgList.get(1).getMainImageFlg());
		Assert.assertEquals("���C���摜�t���O����������", "0", imgList.get(2).getMainImageFlg());
		Assert.assertEquals("���C���摜�t���O����������", "0", imgList.get(3).getMainImageFlg());

		Assert.assertEquals("�L���v�V��������������", caption[0], imgList.get(0).getCaption());
		Assert.assertEquals("�L���v�V��������������", caption[2], imgList.get(1).getCaption());
		Assert.assertEquals("�L���v�V��������������", caption2[0], imgList.get(2).getCaption());
		Assert.assertEquals("�L���v�V��������������", caption2[2], imgList.get(3).getCaption());

		Assert.assertEquals("�R�����g����������", imgComment[0], imgList.get(0).getImgComment());
		Assert.assertEquals("�R�����g����������", imgComment[2], imgList.get(1).getImgComment());
		Assert.assertEquals("�R�����g����������", imgComment2[0], imgList.get(2).getImgComment());
		Assert.assertEquals("�R�����g����������", imgComment2[2], imgList.get(3).getImgComment());

		Assert.assertEquals("�c���E�����t���O����������", "1", imgList.get(0).getHwFlg());
		Assert.assertEquals("�c���E�����t���O����������", "0", imgList.get(1).getHwFlg());
		Assert.assertEquals("�c���E�����t���O����������", "1", imgList.get(2).getHwFlg());
		Assert.assertEquals("�c���E�����t���O����������", "0", imgList.get(3).getHwFlg());
		
	}
	
	
	
	/**
	 * �����摜���ǉ������i�ŏ����̓��̓e�X�g�j
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�o�^���������������B�@�i�󔒍s�������ēo�^����Ă��鎖�B�j</li>
	 *     <li>���͒l�Ő������o�^����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void addHousingImgMinTest() throws Exception {

		// �e�X�g�f�[�^�쐬
		initData();
		
		// �e�X�g�摜�t�@�C���z�u
		initImgFile();

		// �t�H�[���̍쐬
		HousingImgForm inputForm = this.formFactory.createHousingImgForm();

		inputForm.setSysHousingCd("SYSHOU00001");				// �V�X�e������CD
		inputForm.setTempDate("20150101");						// ���t�H���_���@�i���t�����j
		
		String[] imageType = new String[3];						// �摜�^�C�v
		imageType[0] = "00";
		imageType[1] = "";
		imageType[2] = "";
		inputForm.setImageType(imageType);

		String[] sortOrder = new String[3];						// �\���� 
		sortOrder[0] = "";
		sortOrder[1] = "";
		sortOrder[2] = "";
		inputForm.setSortOrder(sortOrder);

		String[] fileName = new String[3];						// �t�@�C����
		fileName[0] = "0000000001.jpg";
		fileName[1] = "";
		fileName[2] = "";
		inputForm.setFileName(fileName);

		String[] caption = new String[3];						// �L���v�V����
		caption[0] = "";
		caption[1] = "";
		caption[2] = "";
		inputForm.setCaption(caption);

		String[] imgComment = new String[3];						// �R�����g
		imgComment[0] = "";
		imgComment[1] = "";
		imgComment[2] = "";
		inputForm.setImgComment(imgComment);
		
		
		// �e�X�g���\�b�h���s
		this.housingManage.addHousingImg(inputForm, "editUserId1");

		// ���s���ʊm�F
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00001");
		criteria.addOrderByClause("imageType");
		criteria.addOrderByClause("divNo");
		List<HousingImageInfo> imgList = this.housingImageInfoDAO.selectByFilter(criteria);
		
		Assert.assertEquals("�o�^��������������", 1, imgList.size());
		
		Assert.assertEquals("�摜�^�C�v����������", imageType[0], imgList.get(0).getImageType());
		Assert.assertEquals("�}�Ԃ���������", Integer.valueOf(1), imgList.get(0).getDivNo());
		Assert.assertNull("�\������ null �ł��鎖", imgList.get(0).getSortOrder());
		Assert.assertEquals("�p�X����������", "999/13/99999/SYSHOU00001/", imgList.get(0).getPathName());
		Assert.assertEquals("�t�@�C��������������", fileName[0], imgList.get(0).getFileName());
		Assert.assertEquals("���C���摜�t���O����������", "1", imgList.get(0).getMainImageFlg());
		Assert.assertNull("�L���v�V������ null �ł��鎖", imgList.get(0).getCaption());
		Assert.assertNull("�R�����g�� null �ł��鎖", imgList.get(0).getImgComment());
		Assert.assertEquals("�c���E�����t���O����������", "1", imgList.get(0).getHwFlg());

	}


	
	/**
	 * �����摜���ǉ������i�e�f�[�^�Ȃ��j
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>NotFoundException ���X���[����鎖</li>
	 * </ul>
	 */
	@Test(expected=NotFoundException.class)
	public void addHousingImgFailTest() throws Exception {

		// �e�X�g�摜�t�@�C���z�u
		initImgFile();

		// �t�H�[���̍쐬
		HousingImgForm inputForm = this.formFactory.createHousingImgForm();

		inputForm.setSysHousingCd("SYSHOU00001");				// �V�X�e������CD
		inputForm.setTempDate("20150101");						// ���t�H���_���@�i���t�����j
		
		String[] imageType = new String[3];						// �摜�^�C�v
		imageType[0] = "00";
		imageType[1] = "";
		imageType[2] = "";
		inputForm.setImageType(imageType);

		String[] sortOrder = new String[3];						// �\���� 
		sortOrder[0] = "";
		sortOrder[1] = "";
		sortOrder[2] = "";
		inputForm.setSortOrder(sortOrder);

		String[] fileName = new String[3];						// �t�@�C����
		fileName[0] = "0000000001.jpg";
		fileName[1] = "";
		fileName[2] = "";
		inputForm.setFileName(fileName);

		String[] caption = new String[3];						// �L���v�V����
		caption[0] = "";
		caption[1] = "";
		caption[2] = "";
		inputForm.setCaption(caption);

		String[] imgComment = new String[3];						// �R�����g
		imgComment[0] = "";
		imgComment[1] = "";
		imgComment[2] = "";
		inputForm.setImgComment(imgComment);
		
		
		// �e�X�g���\�b�h���s
		this.housingManage.addHousingImg(inputForm, "editUserId1");

	}

	
	/**
	 * �����摜���X�V����
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�폜�t���O���w�肳��Ă���摜��񂪍폜����Ă��鎖</li>
	 *     <li>��L�[�l�i�摜�^�C�v�j���ύX�\�ł��鎖</li>
	 *     <li>�}�Ԃ��č\�z����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void updHousingImgTest() throws Exception {

		// �e�X�g�p�����摜���̓o�^
		initImgData();

		// ���̓t�H�[�����쐬
		HousingImgForm form = this.formFactory.createHousingImgForm();

		// �摜�P���폜
		// �摜�R��ʂ̉摜�^�C�v�֕ύX
		// �摜�S�̊e�t�B�[���h���X�V �i�t�@�C�������A�ꕔ�̒l�͍X�V�ΏۊO�j

		form.setSysHousingCd("SYSHOU00001");								// �V�X�e������CD

		form.setImageType(new String[]{"00","00","02","01","01"});			// �摜�^�C�v
		form.setDivNo(new String[]{"1","2","1","2","3"});					// �}��
		form.setSortOrder(new String[]{"1","2","1","3","4"});				// �\����
		// �t�@�C����
		form.setFileName(new String[]{"FNAME0001","FNAME0002","FNAME0003","FNAME0004","FNAME0005"});
		// �L���v�V����
		form.setCaption(new String[]{"CAP00001","CAP00002","CAP00003","CAP10004","CAP00005"});
		// �R�����g
		form.setImgComment(new String[]{"COM00001","COM00002","COM00003","COM10004","COM00005"});
		// �폜�t���O
		form.setDelFlg(new String[]{"1","0","0","0","0"});
		// ���摜�^�C�v
		form.setOldImageType(new String[]{"00","00","01","01","01"});		// �摜�^�C�v
		
		
		// �e�X�g���\�b�h�����s
		this.housingManage.updHousingImg(form, "editUserId2");


		// ���s���ʂ��`�F�b�N
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00001");
		
		List<HousingImageInfo> imgList = this.housingImageInfoDAO.selectByFilter(criteria);
		
		Assert.assertEquals("�擾��������������", 4, imgList.size());
		
		
		for (HousingImageInfo imgInfo : imgList){

			if (imgInfo.getFileName().equals("FNAME0002")){
				Assert.assertEquals("�摜�^�C�v���������l�ł��鎖", "00", imgInfo.getImageType());
				Assert.assertEquals("�}�Ԃ��������l�ɍ̔Ԃ��Ȃ�����Ă��鎖", Integer.valueOf(1), imgInfo.getDivNo());
				Assert.assertEquals("�\�������������l�ł��鎖", Integer.valueOf(2), imgInfo.getSortOrder());
				Assert.assertEquals("�p�X�����������l�ł��鎖", "PATH00002", imgInfo.getPathName());
				Assert.assertEquals("���C���摜�t���O���������l�ł��鎖", "1", imgInfo.getMainImageFlg());
				Assert.assertEquals("�L���v�V�������������l�ł��鎖", "CAP00002", imgInfo.getCaption());
				Assert.assertEquals("�R�����g���������l�ł��鎖", "COM00002", imgInfo.getImgComment());
				Assert.assertEquals("�c���E�����t���O���������l�ł��鎖", "B", imgInfo.getHwFlg());

			} else if (imgInfo.getFileName().equals("FNAME0003")) {
				Assert.assertEquals("�摜�^�C�v���������l�ł��鎖", "02", imgInfo.getImageType());
				Assert.assertEquals("�}�Ԃ��������l�ɍ̔Ԃ��Ȃ�����Ă��鎖", Integer.valueOf(1), imgInfo.getDivNo());
				Assert.assertEquals("�\�������������l�ł��鎖", Integer.valueOf(1), imgInfo.getSortOrder());
				Assert.assertEquals("�p�X�����������l�ł��鎖", "PATH00003", imgInfo.getPathName());
				Assert.assertEquals("���C���摜�t���O���������l�ł��鎖", "1", imgInfo.getMainImageFlg());
				Assert.assertEquals("�L���v�V�������������l�ł��鎖", "CAP00003", imgInfo.getCaption());
				Assert.assertEquals("�R�����g���������l�ł��鎖", "COM00003", imgInfo.getImgComment());
				Assert.assertEquals("�c���E�����t���O���������l�ł��鎖", "C", imgInfo.getHwFlg());

			} else if (imgInfo.getFileName().equals("FNAME0004")) {
				Assert.assertEquals("�摜�^�C�v���������l�ł��鎖", "01", imgInfo.getImageType());
				Assert.assertEquals("�}�Ԃ��������l�ɍ̔Ԃ��Ȃ�����Ă��鎖", Integer.valueOf(1), imgInfo.getDivNo());
				Assert.assertEquals("�\�������������l�ł��鎖", Integer.valueOf(3), imgInfo.getSortOrder());
				Assert.assertEquals("�p�X�����������l�ł��鎖", "PATH00004", imgInfo.getPathName());
				Assert.assertEquals("���C���摜�t���O���������l�ł��鎖", "1", imgInfo.getMainImageFlg());
				Assert.assertEquals("�L���v�V�������������l�ł��鎖", "CAP10004", imgInfo.getCaption());
				Assert.assertEquals("�R�����g���������l�ł��鎖", "COM10004", imgInfo.getImgComment());
				Assert.assertEquals("�c���E�����t���O���������l�ł��鎖", "D", imgInfo.getHwFlg());

			} else if (imgInfo.getFileName().equals("FNAME0005")) {
				Assert.assertEquals("�摜�^�C�v���������l�ł��鎖", "01", imgInfo.getImageType());
				Assert.assertEquals("�}�Ԃ��������l�ɍ̔Ԃ��Ȃ�����Ă��鎖", Integer.valueOf(2), imgInfo.getDivNo());
				Assert.assertEquals("�\�������������l�ł��鎖", Integer.valueOf(4), imgInfo.getSortOrder());
				Assert.assertEquals("�p�X�����������l�ł��鎖", "PATH00005", imgInfo.getPathName());
				Assert.assertEquals("���C���摜�t���O���������l�ł��鎖", "0", imgInfo.getMainImageFlg());
				Assert.assertEquals("�L���v�V�������������l�ł��鎖", "CAP00005", imgInfo.getCaption());
				Assert.assertEquals("�R�����g���������l�ł��鎖", "COM00005", imgInfo.getImgComment());
				Assert.assertEquals("�c���E�����t���O���������l�ł��鎖", "E", imgInfo.getHwFlg());

			} else {
				Assert.fail("��������R�[�h���폜����Ă��� : " + imgInfo.getFileName());
			}
		}
		
	}

	
	
	/**
	 * �����摜���X�V�����i�\��������ւ��ɂ���L�[�d���Ή��̊m�F�j
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>����V�X�e�������ԍ��A�摜�^�C�v�ŕ\���������ւ��Ă��}�Ԃ̏d���ŃG���[�ɂȂ�Ȃ���</li>
	 *     <li>���̃V�X�e�������ԍ��̃f�[�^���e�����󂯂Ă��Ȃ���</li>
	 * </ul>
	 */
	@Test
	public void updHousingImgTest2() throws Exception {

		// �e�X�g�p�����摜���̓o�^
		initImgData();

		// �X�V�Ώۃf�[�^�̃p�X���̊��Ғl�@�i�ύX����Ă��Ȃ����B�@�������A�R�ԖڂƂS�Ԗڂ̏��Ԃ�����ւ��B�j
		String[] updTargetPath = {"PATH00001", "PATH00002", "PATH00004", "PATH00003", "PATH00005"};
		// �X�V�Ώۃf�[�^�̃t�@�C�����̊��Ғl�@�i�ύX����Ă��Ȃ����B�@�������A�R�ԖڂƂS�Ԗڂ̏��Ԃ�����ւ��B�j
		String[] updTargetFile = {"FNAME0001", "FNAME0002", "FNAME0004", "FNAME0003", "FNAME0005"};
		// �X�V�Ώۃf�[�^�̃L���v�V�����̊��Ғl�@�i���͒l�ŕύX����Ă��鎖�B�@�������A�R�ԖڂƂS�Ԗڂ̏��Ԃ�����ւ��B�j
		String[] updTargetCap = {"CAP00001", "CAP00002", "CAP10004", "CAP10003", "CAP00005"};
		// �X�V�Ώۃf�[�^�̃R�����g�̊��Ғl�@�i���͒l�ŕύX����Ă��鎖�B�@�������A�R�ԖڂƂS�Ԗڂ̏��Ԃ�����ւ��B�j
		String[] updTargetCom = {"COM00001", "COM00002", "COM10004", "COM10003", "COM00005"};
		// �X�V�Ώۃf�[�^�̏c���E�����t���O�̊��Ғl�@�i�ύX����Ă��Ȃ����B�@�������A�R�ԖڂƂS�Ԗڂ̏��Ԃ�����ւ��B�j
		String[] updHwFlg = {"A", "B", "D", "C", "E"};
		// �X�V�Ώۃf�[�^�̃��C���摜�t���O�̊��Ғl
		String[] updMainFlg = {"1", "0", "1", "0", "0"};
		
		
		// ���̓t�H�[�����쐬
		HousingImgForm form = this.formFactory.createHousingImgForm();

		// �摜�R�ƂS�̕\���������ւ�
		// ���̃t�B�[���h�������ƍX�V����Ă��鎖���m�F����ׁA�L���v�V�����A�R�����g�̒l��ύX

		form.setSysHousingCd("SYSHOU00001");								// �V�X�e������CD

		form.setImageType(new String[]{"00","00","01","01","01"});			// �摜�^�C�v
		form.setDivNo(new String[]{"1","2","1","2","3"});					// �}��
		form.setSortOrder(new String[]{"1","2","2","1","3"});				// �\����
		// �t�@�C����
		form.setFileName(new String[]{"FNAME0001","FNAME0002","FNAME0003","FNAME0004","FNAME0005"});
		// �L���v�V����
		form.setCaption(new String[]{"CAP00001","CAP00002","CAP10003","CAP10004","CAP00005"});
		// �R�����g
		form.setImgComment(new String[]{"COM00001","COM00002","COM10003","COM10004","COM00005"});
		// �폜�t���O
		form.setDelFlg(new String[]{"0","0","0","0","0"});
		// ���摜�^�C�v
		form.setOldImageType(new String[]{"00","00","01","01","01"});		// �摜�^�C�v

	
		// �e�X�g���\�b�h�����s
		this.housingManage.updHousingImg(form, "editUserId2");

		
		// �X�V���ʃf�[�^���擾
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00001");
		criteria.addOrderByClause("imageType");
		criteria.addOrderByClause("divNo");

		// �X�V�Ώۃf�[�^�̊m�F
		List<HousingImageInfo> list = this.housingImageInfoDAO.selectByFilter(criteria);
		int idx = 0;
		for (HousingImageInfo info : list){

			Assert.assertEquals("�摜�^�C�v���o�^�����l�ƈ�v���鎖(" + idx + ")", form.getImageType()[idx], info.getImageType());
			Assert.assertEquals("�}�Ԃ��z��l�ł��鎖(" + idx + ")", Integer.valueOf(form.getDivNo()[idx]), info.getDivNo());

			// �}�ԏ��Ń\�[�g���Ēl���擾���Ă���̂ŁA���Ԃ��t�]���Ă���B�@���̃P�[�X�̏ꍇ�A�\�����͎}�ԂƓ����l�ɂȂ�̂ŁA�}�ԂƔ�r����B
			Assert.assertEquals("�\�������o�^�����l�ƈ�v���鎖(" + idx + ")", Integer.valueOf(form.getDivNo()[idx]), info.getSortOrder());
			Assert.assertEquals("���C���摜�t���O���z��l�ł��鎖(" + idx + ")", updMainFlg[idx], info.getMainImageFlg());

			Assert.assertEquals("�p�X�����ω����Ă��Ȃ���(" + idx + ")", updTargetPath[idx], info.getPathName());
			Assert.assertEquals("�t�@�C�������ω����Ă��Ȃ���(" + idx + ")", updTargetFile[idx], info.getFileName());
			Assert.assertEquals("�L���v�V�������o�^�����l�ƈ�v���鎖(" + idx + ")", updTargetCap[idx], info.getCaption());
			Assert.assertEquals("�R�����g���o�^�����l�ƈ�v���鎖(" + idx + ")", updTargetCom[idx], info.getImgComment());
			Assert.assertEquals("�c���E�����t���O���ω����Ă��Ȃ���(" + idx + ")", updHwFlg[idx], info.getHwFlg());

			++idx;
		}

		
		// �X�V�ΏۊO�f�[�^�̊m�F
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00002");

		list = this.housingImageInfoDAO.selectByFilter(criteria);

		Assert.assertEquals("�X�V�ΏۊO�f�[�^�̉摜�^�C�v���X�V����Ă��Ȃ���", "00", list.get(0).getImageType());
		Assert.assertEquals("�X�V�ΏۊO�f�[�^�̎}�Ԃ��X�V����Ă��Ȃ���", Integer.valueOf(1), list.get(0).getDivNo());
		Assert.assertEquals("�X�V�ΏۊO�f�[�^�̕\�������X�V����Ă��Ȃ���", Integer.valueOf(1), list.get(0).getSortOrder());
		Assert.assertEquals("�X�V�ΏۊO�f�[�^�̃��C���摜�t���O���X�V����Ă��Ȃ���", "1", list.get(0).getMainImageFlg());
		Assert.assertEquals("�X�V�ΏۊO�f�[�^�̃p�X�����X�V����Ă��Ȃ���", "PATH00006", list.get(0).getPathName());
		Assert.assertEquals("�X�V�ΏۊO�f�[�^�̃t�@�C�������X�V����Ă��Ȃ���", "FNAME0006", list.get(0).getFileName());
		Assert.assertEquals("�X�V�ΏۊO�f�[�^�̃L���v�V�������X�V����Ă��Ȃ���", "CAP00006", list.get(0).getCaption());
		Assert.assertEquals("�X�V�ΏۊO�f�[�^�̃R�����g���X�V����Ă��Ȃ���", "COM00006", list.get(0).getImgComment());
		Assert.assertEquals("�X�V�ΏۊO�f�[�^�̏c���E�����t���O���X�V����Ă��Ȃ���", "F", list.get(0).getHwFlg());

	}
	
	
	


	// �e�X�g�f�[�^�o�^
	private void initData() {
		
		HousingInfo housingInfo;

		//�@������{���i�X�V�Ώہj
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SYSHOU00001");
		housingInfo.setSysBuildingCd("SYSBLD0001");
		housingInfo.setDisplayHousingName("OLD�������P");
		housingInfo.setIconCd("dummyIcon1");			// �X�V�ΏۊO�t�B�[���h�Ȃ̂ŁA�l��ݒ�
		Date date = new Date();
		housingInfo.setInsDate(DateUtils.addDays(date, -10));
		housingInfo.setInsUserId("dummInsID1");

		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});


		//�@������{���i�X�V�ΏۊO�j
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SYSHOU00002");
		housingInfo.setSysBuildingCd("SYSBLD0001");
		housingInfo.setDisplayHousingName("OLD�������Q");
		housingInfo.setIconCd("dummyIcon2");
		housingInfo.setInsDate(DateUtils.addDays(date, -10));
		housingInfo.setInsUserId("dummInsID2");

		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

	}

	
	// �����ڍׂ̃e�X�g�p���̓t�H�[���쐬
	private HousingDtlForm initInputDtlForm(String sysHousingCd){
		
		// ���̓t�H�[���𐶐����ď����l��ݒ�
		HousingDtlForm inputForm = this.formFactory.createHousingDtlForm();

		inputForm.setSysHousingCd(sysHousingCd);					// �V�X�e������CD
		inputForm.setUsedAreaCd("Y01");								// �p�r�n��CD
		inputForm.setTransactTypeDiv("T01");						// ����`�ԋ敪
		inputForm.setDisplayContractTerm("�\���p�_����ԂP");			// �\���p�_�����
		inputForm.setLandRight("�y�n�����P");							// �y�n����
		inputForm.setMoveinTiming("01");							// �����\�����t���O

		String date = StringUtils.dateToString(DateUtils.addDays(new Date(), 10));
		inputForm.setMoveinTimingDay(date);							// �����\����

		inputForm.setMoveinNote("�����\�����R�����g�P");				// �����\�����R�����g
		inputForm.setDisplayMoveinTiming("�\���p�����\�����P");		// �\���p�����\����
		inputForm.setDisplayMoveinProviso("�\���p�����������P");		// �\���p����������
		inputForm.setUpkeepType("�Ǘ��`�ԁE�����P");					// �Ǘ��`�ԁE����
		inputForm.setUpkeepCorp("�Ǘ���ЂP");						// �Ǘ����
		inputForm.setRenewChrg("1000.01");							// �X�V��
		inputForm.setRenewChrgCrs("R1");							// �X�V���P��
		inputForm.setRenewChrgName("�X�V�����P");						// �X�V����
		inputForm.setRenewDue("2000.01");							// �X�V�萔��
		inputForm.setRenewDueCrs("D1");								// �X�V�萔���P��
		inputForm.setBrokerageChrg("3000.01");						// ����萔��
		inputForm.setBrokerageChrgCrs("B1");						// ����萔���P��
		inputForm.setChangeKeyChrg("4000.01");						// ��������
		inputForm.setInsurExist("B");								// ���ۗL��
		inputForm.setInsurChrg("5000");								// ���ۗ���
		inputForm.setInsurTerm("200");								// ���۔N��
		inputForm.setInsurLank("L01");								// ���۔F�胉���N
		inputForm.setOtherChrg("6000");								// ����p
		inputForm.setContactRoad("�ړ��󋵂P");						// �ړ���
		inputForm.setContactRoadDir("�ړ�����/�����P");				// �ړ�����/����
		inputForm.setPrivateRoad("�������S�P");						// �������S
		inputForm.setBalconyArea("7000.01");						// �o���R�j�[�ʐ�
		inputForm.setSpecialInstruction("���L�����P");				// ���L����
		inputForm.setDtlComment("�ڍ׃R�����g�P");						// �ڍ׃R�����g 

		return inputForm;
	}



	// �����ڍ׏��̓o�^���`�F�b�N
	private void chkHousingDtl(String sysHousingCd){

		HousingDtlInfo housingDtlInfo = this.housingDtlInfoDAO.selectByPK(sysHousingCd);

		Assert.assertEquals("�p�r�n��CD�̓o�^�l����������", "Y01", housingDtlInfo.getUsedAreaCd());
		Assert.assertEquals("����`�ԋ敪�̓o�^�l����������", "T01", housingDtlInfo.getTransactTypeDiv());
		Assert.assertEquals("�\���p�_����Ԃ̓o�^�l����������", "�\���p�_����ԂP", housingDtlInfo.getDisplayContractTerm());
		Assert.assertEquals("�y�n�����̓o�^�l����������", "�y�n�����P", housingDtlInfo.getLandRight());
		Assert.assertEquals("�����\�����t���O����������", "01", housingDtlInfo.getMoveinTiming());

		String date10 = StringUtils.dateToString(DateUtils.addDays(new Date(), 10));
		String date = StringUtils.dateToString(housingDtlInfo.getMoveinTimingDay());
		Assert.assertEquals("�����\��������������", date10, date);
		
		Assert.assertEquals("�����\�����R�����g����������", "�����\�����R�����g�P", housingDtlInfo.getMoveinNote());
		Assert.assertEquals("�\���p�����\��������������", "�\���p�����\�����P", housingDtlInfo.getDisplayMoveinTiming());
		Assert.assertEquals("�\���p��������������������", "�\���p�����������P", housingDtlInfo.getDisplayMoveinProviso());
		Assert.assertEquals("�Ǘ��`�ԁE��������������", "�Ǘ��`�ԁE�����P", housingDtlInfo.getUpkeepType());
		Assert.assertEquals("�Ǘ���Ђ���������", "�Ǘ���ЂP", housingDtlInfo.getUpkeepCorp());
		Assert.assertEquals("�X�V������������", new BigDecimal("1000.01"), housingDtlInfo.getRenewChrg());
		Assert.assertEquals("�X�V���P�ʂ���������", "R1", housingDtlInfo.getRenewChrgCrs());
		Assert.assertEquals("�X�V��������������", "�X�V�����P", housingDtlInfo.getRenewChrgName());
		Assert.assertEquals("�X�V�萔������������", new BigDecimal("2000.01"), housingDtlInfo.getRenewDue());
		Assert.assertEquals("�X�V�萔���P�ʂ���������", "D1", housingDtlInfo.getRenewDueCrs());
		Assert.assertEquals("����萔������������", new BigDecimal("3000.01"), housingDtlInfo.getBrokerageChrg());
		Assert.assertEquals("����萔���P�ʂ���������", "B1", housingDtlInfo.getBrokerageChrgCrs());
		Assert.assertEquals("������������������", new BigDecimal("4000.01"), housingDtlInfo.getChangeKeyChrg());
		Assert.assertEquals("���ۗL������������", "B", housingDtlInfo.getInsurExist());
		Assert.assertEquals("���ۗ�������������", Long.valueOf("5000"), housingDtlInfo.getInsurChrg());
		Assert.assertEquals("���۔N������������", Integer.valueOf(200), housingDtlInfo.getInsurTerm());
		Assert.assertEquals("���۔F�胉���N����������", "L01", housingDtlInfo.getInsurLank());
		Assert.assertEquals("����p����������", Long.valueOf(6000), housingDtlInfo.getOtherChrg());
		Assert.assertEquals("�ړ��󋵂���������", "�ړ��󋵂P", housingDtlInfo.getContactRoad());
		Assert.assertEquals("�ړ�����/��������������", "�ړ�����/�����P", housingDtlInfo.getContactRoadDir());
		Assert.assertEquals("�������S����������", "�������S�P", housingDtlInfo.getPrivateRoad());
		Assert.assertEquals("�o���R�j�[�ʐς���������", new BigDecimal("7000.01"), housingDtlInfo.getBalconyArea());
		Assert.assertEquals("���L��������������", "���L�����P", housingDtlInfo.getSpecialInstruction());
		Assert.assertEquals("�ڍ׃R�����g����������", "�ڍ׃R�����g�P", housingDtlInfo.getDtlComment());

	}

	// ������{���̍X�V���t�`�F�b�N
	private void chkHousingTimestamp(String sysHousingCd, String editUserId){
		
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK(sysHousingCd);

		// �o�^�����ύX����Ă��Ȃ�����
		String date10 = StringUtils.dateToString(DateUtils.addDays(new Date(), -10));

		String date = StringUtils.dateToString(housingInfo.getInsDate());
		Assert.assertEquals("�o�^�����X�V����Ă��Ȃ���", date10, date);
		Assert.assertEquals("�o�^�҂��X�V����Ă��Ȃ���", "dummInsID1", housingInfo.getInsUserId());

		String now = StringUtils.dateToString(new Date());
		date = StringUtils.dateToString(housingInfo.getUpdDate());
		Assert.assertEquals("�X�V�����X�V����Ă��鎖", now, date);
		Assert.assertEquals("�X�V�҂��X�V����Ă��鎖", editUserId, housingInfo.getUpdUserId());
	}



	private void initImgData(){

		// �e�X�g�ΏۂƂȂ镨����{�����쐬����B
		initData();

		HousingImageInfo housingImageInfo = new HousingImageInfo();

		// �����摜�P
		housingImageInfo.setSysHousingCd("SYSHOU00001");				// �V�X�e������CD
		housingImageInfo.setImageType("00");							// �摜�^�C�v
		housingImageInfo.setDivNo(1);									// �}��
		housingImageInfo.setSortOrder(1);								// �\����
		housingImageInfo.setPathName("PATH00001");						// �p�X��
		housingImageInfo.setFileName("FNAME0001");						// �t�@�C����
		housingImageInfo.setMainImageFlg("1");							// ���C���摜�t���O
		housingImageInfo.setCaption("CAP00001");						// �L���v�V����
		housingImageInfo.setImgComment("COM00001");						// �R�����g
		housingImageInfo.setHwFlg("A");									// �c���E�����t���O

		this.housingImageInfoDAO.insert(new HousingImageInfo[]{housingImageInfo});


		// �����摜�Q
		housingImageInfo.setSysHousingCd("SYSHOU00001");				// �V�X�e������CD
		housingImageInfo.setImageType("00");							// �摜�^�C�v
		housingImageInfo.setDivNo(2);									// �}��
		housingImageInfo.setSortOrder(2);								// �\����
		housingImageInfo.setPathName("PATH00002");						// �p�X��
		housingImageInfo.setFileName("FNAME0002");						// �t�@�C����
		housingImageInfo.setMainImageFlg("0");							// ���C���摜�t���O
		housingImageInfo.setCaption("CAP00002");						// �L���v�V����
		housingImageInfo.setImgComment("COM00002");						// �R�����g
		housingImageInfo.setHwFlg("B");									// �c���E�����t���O

		this.housingImageInfoDAO.insert(new HousingImageInfo[]{housingImageInfo});
		

		// �����摜�R
		housingImageInfo.setSysHousingCd("SYSHOU00001");				// �V�X�e������CD
		housingImageInfo.setImageType("01");							// �摜�^�C�v
		housingImageInfo.setDivNo(1);									// �}��
		housingImageInfo.setSortOrder(1);								// �\����
		housingImageInfo.setPathName("PATH00003");						// �p�X��
		housingImageInfo.setFileName("FNAME0003");						// �t�@�C����
		housingImageInfo.setMainImageFlg("1");							// ���C���摜�t���O
		housingImageInfo.setCaption("CAP00003");						// �L���v�V����
		housingImageInfo.setImgComment("COM00003");						// �R�����g
		housingImageInfo.setHwFlg("C");									// �c���E�����t���O

		this.housingImageInfoDAO.insert(new HousingImageInfo[]{housingImageInfo});

		
		// �����摜�S
		housingImageInfo.setSysHousingCd("SYSHOU00001");				// �V�X�e������CD
		housingImageInfo.setImageType("01");							// �摜�^�C�v
		housingImageInfo.setDivNo(2);									// �}��
		housingImageInfo.setSortOrder(2);								// �\����
		housingImageInfo.setPathName("PATH00004");						// �p�X��
		housingImageInfo.setFileName("FNAME0004");						// �t�@�C����
		housingImageInfo.setMainImageFlg("0");							// ���C���摜�t���O
		housingImageInfo.setCaption("CAP00004");						// �L���v�V����
		housingImageInfo.setImgComment("COM00004");						// �R�����g
		housingImageInfo.setHwFlg("D");									// �c���E�����t���O

		this.housingImageInfoDAO.insert(new HousingImageInfo[]{housingImageInfo});

		
		// �����摜�T
		housingImageInfo.setSysHousingCd("SYSHOU00001");				// �V�X�e������CD
		housingImageInfo.setImageType("01");							// �摜�^�C�v
		housingImageInfo.setDivNo(3);									// �}��
		housingImageInfo.setSortOrder(3);								// �\����
		housingImageInfo.setPathName("PATH00005");						// �p�X��
		housingImageInfo.setFileName("FNAME0005");						// �t�@�C����
		housingImageInfo.setMainImageFlg("0");							// ���C���摜�t���O
		housingImageInfo.setCaption("CAP00005");						// �L���v�V����
		housingImageInfo.setImgComment("COM00005");						// �R�����g
		housingImageInfo.setHwFlg("E");									// �c���E�����t���O

		this.housingImageInfoDAO.insert(new HousingImageInfo[]{housingImageInfo});


		// �����摜�U�i�X�V�ΏۊO�����j
		housingImageInfo.setSysHousingCd("SYSHOU00002");				// �V�X�e������CD
		housingImageInfo.setImageType("00");							// �摜�^�C�v
		housingImageInfo.setDivNo(1);									// �}��
		housingImageInfo.setSortOrder(1);								// �\����
		housingImageInfo.setPathName("PATH00006");						// �p�X��
		housingImageInfo.setFileName("FNAME0006");						// �t�@�C����
		housingImageInfo.setMainImageFlg("1");							// ���C���摜�t���O
		housingImageInfo.setCaption("CAP00006");						// �L���v�V����
		housingImageInfo.setImgComment("COM00006");						// �R�����g
		housingImageInfo.setHwFlg("F");									// �c���E�����t���O

		this.housingImageInfoDAO.insert(new HousingImageInfo[]{housingImageInfo});

	}
	
	
	
	private void initImgFile() throws Exception{
		// ���s���̃��[�g�f�B���N�g��
		String root = (String) System.getProperties().get("user.dir");
		
		
		String tempRootDir = root + "/junitTest/upload/temp/";
		String testDataDir = root + "/junitTest/upload/testData/";
		
		// ���t�H���_�����폜����B
		FileUtils.cleanDirectory(new File(tempRootDir));

		// ���t���̉��t�H���_���쐬����
		FileUtils.forceMkdir(new File(tempRootDir + "20150101"));

		// ���t�H���_���Ƀe�X�g�f�[�^��z�u����B
		FileUtils.copyDirectory(new File(testDataDir), new File(tempRootDir + "20150101"));

	}
}
