package jp.co.transcosmos.dm3.test.core.model.housingRequest;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.adminCore.request.form.HousingRequestForm;
import jp.co.transcosmos.dm3.core.model.HousingRequestManage;
import jp.co.transcosmos.dm3.core.model.exception.MaxEntryOverException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.housingRequest.HousingRequest;
import jp.co.transcosmos.dm3.core.model.housingRequest.form.RequestSearchForm;
import jp.co.transcosmos.dm3.core.model.housingRequest.form.RequestSearchFormFactory;
import jp.co.transcosmos.dm3.core.vo.AddressMst;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingPartInfo;
import jp.co.transcosmos.dm3.core.vo.HousingReqKind;
import jp.co.transcosmos.dm3.core.vo.HousingReqLayout;
import jp.co.transcosmos.dm3.core.vo.HousingReqPart;
import jp.co.transcosmos.dm3.core.vo.HousingReqRoute;
import jp.co.transcosmos.dm3.core.vo.HousingReqStation;
import jp.co.transcosmos.dm3.core.vo.HousingRequestArea;
import jp.co.transcosmos.dm3.core.vo.HousingRequestInfo;
import jp.co.transcosmos.dm3.core.vo.MemberInfo;
import jp.co.transcosmos.dm3.core.vo.PartSrchMst;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.core.vo.UserInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * �������N�G�X�g��� model �̃e�X�g�P�[�X
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")

public class HousingRequestManageTest {

	@Autowired
	private HousingRequestManage housingRequestManage;
	@Autowired
	private RequestSearchFormFactory requestSearchFormFactory;

	@Autowired
	private DAO<UserInfo> userInfoDAO;
	@Autowired
	private DAO<MemberInfo> memberInfoDAO;

	@Autowired
	private DAO<HousingRequestInfo> housingRequestInfoDAO;
	@Autowired
	private DAO<HousingRequestArea> housingRequestAreaDAO;
	@Autowired
	private DAO<HousingReqRoute> housingReqRouteDAO;
	@Autowired
	private DAO<HousingReqStation> housingReqStationDAO;
	@Autowired
	private DAO<HousingReqKind> housingReqKindDAO;
	@Autowired
	private DAO<HousingReqLayout> housingReqLayoutDAO;
	@Autowired
	private DAO<HousingReqPart> housingReqPartDAO;

	@Autowired
	private DAO<AddressMst> addressMstDAO;
	@Autowired
	private DAO<RouteMst> routeMstDAO;
	@Autowired
	private DAO<StationMst> stationMstDAO;
	@Autowired
	private DAO<JoinResult> stationRouteRelationDAO;
	@Autowired
	private DAO<PartSrchMst> partSrchMstDAO;

	@Autowired
	private DAO<BuildingInfo> buildingInfoDAO;
	@Autowired
	private DAO<HousingInfo> housingInfoDAO;
	@Autowired
	private DAO<BuildingStationInfo> buildingStationInfoDAO;
	@Autowired
	private DAO<HousingPartInfo> housingPartInfoDAO;

	// �O����
	@Before
	public void init() {
		// �֘A����f�[�^�̑S���폜
		DAOCriteria criteria = new DAOCriteria();
		this.userInfoDAO.deleteByFilter(criteria);
		this.memberInfoDAO.deleteByFilter(criteria);

		this.housingRequestInfoDAO.deleteByFilter(criteria);
		this.housingRequestAreaDAO.deleteByFilter(criteria);
		this.housingReqRouteDAO.deleteByFilter(criteria);
		this.housingReqStationDAO.deleteByFilter(criteria);
		this.housingReqKindDAO.deleteByFilter(criteria);
		this.housingReqLayoutDAO.deleteByFilter(criteria);
		this.housingReqLayoutDAO.deleteByFilter(criteria);
		this.housingReqPartDAO.deleteByFilter(criteria);

		this.housingInfoDAO.deleteByFilter(criteria);
		this.buildingInfoDAO.deleteByFilter(criteria);
		this.buildingStationInfoDAO.deleteByFilter(criteria);
		this.housingPartInfoDAO.deleteByFilter(criteria);

		initTestData();
	}

	// �e�X�g�f�[�^�쐬
	private void initTestData() {

		UserInfo userInfo;
		MemberInfo memberInfo;

		// ------ �o�^�f�[�^------

		// ���[�UID���
		userInfo = new UserInfo();
		userInfo.setUserId("UID00001");
		userInfoDAO.insert(new UserInfo[] { userInfo });

		userInfo = new UserInfo();
		userInfo.setUserId("UID00002");
		userInfoDAO.insert(new UserInfo[] { userInfo });

		userInfo = new UserInfo();
		userInfo.setUserId("UID00003");
		userInfoDAO.insert(new UserInfo[] { userInfo });

		userInfo = new UserInfo();
		userInfo.setUserId("UID00004");
		userInfoDAO.insert(new UserInfo[] { userInfo });

		userInfo = new UserInfo();
		userInfo.setUserId("UID00005");
		userInfoDAO.insert(new UserInfo[] { userInfo });

		userInfo = new UserInfo();
		userInfo.setUserId("UID00006");
		userInfoDAO.insert(new UserInfo[] { userInfo });

		userInfo = new UserInfo();
		userInfo.setUserId("UID00007");
		userInfoDAO.insert(new UserInfo[] { userInfo });

		userInfo = new UserInfo();
		userInfo.setUserId("UID00008");
		userInfoDAO.insert(new UserInfo[] { userInfo });

		userInfo = new UserInfo();
		userInfo.setUserId("UID00009");
		userInfoDAO.insert(new UserInfo[] { userInfo });

		userInfo = new UserInfo();
		userInfo.setUserId("UID00010");
		userInfoDAO.insert(new UserInfo[] { userInfo });

		userInfo = new UserInfo();
		userInfo.setUserId("UID00011");
		userInfoDAO.insert(new UserInfo[] { userInfo });

		// �}�C�y�[�W������
		memberInfo = new MemberInfo();
		memberInfo.setUserId("UID00001");
		memberInfo.setEmail("UID00001@mail.co.jp");
		memberInfoDAO.insert(new MemberInfo[] { memberInfo });

		memberInfo = new MemberInfo();
		memberInfo.setUserId("UID00002");
		memberInfo.setEmail("UID00002@mail.co.jp");
		memberInfoDAO.insert(new MemberInfo[] { memberInfo });

		memberInfo = new MemberInfo();
		memberInfo.setUserId("UID00003");
		memberInfo.setEmail("UID00003@mail.co.jp");
		memberInfoDAO.insert(new MemberInfo[] { memberInfo });

		memberInfo = new MemberInfo();
		memberInfo.setUserId("UID00004");
		memberInfo.setEmail("UID00004@mail.co.jp");
		memberInfoDAO.insert(new MemberInfo[] { memberInfo });

		memberInfo = new MemberInfo();
		memberInfo.setUserId("UID00005");
		memberInfo.setEmail("UID00005@mail.co.jp");
		memberInfoDAO.insert(new MemberInfo[] { memberInfo });

		memberInfo = new MemberInfo();
		memberInfo.setUserId("UID00006");
		memberInfo.setEmail("UID00006@mail.co.jp");
		memberInfoDAO.insert(new MemberInfo[] { memberInfo });

		memberInfo = new MemberInfo();
		memberInfo.setUserId("UID00007");
		memberInfo.setEmail("UID00007@mail.co.jp");
		memberInfoDAO.insert(new MemberInfo[] { memberInfo });

		memberInfo = new MemberInfo();
		memberInfo.setUserId("UID00008");
		memberInfo.setEmail("UID00008@mail.co.jp");
		memberInfoDAO.insert(new MemberInfo[] { memberInfo });

		memberInfo = new MemberInfo();
		memberInfo.setUserId("UID00009");
		memberInfo.setEmail("UID00009@mail.co.jp");
		memberInfoDAO.insert(new MemberInfo[] { memberInfo });

		memberInfo = new MemberInfo();
		memberInfo.setUserId("UID00010");
		memberInfo.setEmail("UID00010@mail.co.jp");
		memberInfoDAO.insert(new MemberInfo[] { memberInfo });

		memberInfo = new MemberInfo();
		memberInfo.setUserId("UID00011");
		memberInfo.setEmail("UID00011@mail.co.jp");
		memberInfoDAO.insert(new MemberInfo[] { memberInfo });

	}

	/**
	 * �������N�G�X�g���̓o�^����<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�p�����^���������ݒ肳��Ă��Ȃ��ꍇ�A�������N�G�X�g���͓o�^�����A����I�����鎖</li>
	 *     <li>�������N�G�X�g���̃��R�[�h������鎖</li>
	 * </ul>
	 */
	@Test
	public void addRequestParamCheckTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingRequestInfo> housingRequestInfos;

		// �e�X�g�P�[�X���{�O�m�F
		criteria = new DAOCriteria();
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z�������N�G�X�g���0���ł��鎖�B", 0, housingRequestInfos.size());

		HousingRequestForm inputForm;

		// ���[�UID����̏ꍇ�A���R�[�h������Ȃ���
		inputForm = new HousingRequestForm();
		housingRequestManage.addRequest("", inputForm);
		criteria = new DAOCriteria();
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("�p�����^���������ݒ肳��Ă��Ȃ��ꍇ�A�������N�G�X�g���͓o�^�����A����I�����鎖", 0, housingRequestInfos.size());

		// ���[�UID��null�̏ꍇ�A���R�[�h������Ȃ���
		inputForm = new HousingRequestForm();
		housingRequestManage.addRequest(null, inputForm);
		criteria = new DAOCriteria();
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("�p�����^���������ݒ肳��Ă��Ȃ��ꍇ�A�������N�G�X�g���͓o�^�����A����I�����鎖", 0, housingRequestInfos.size());

		// ���[�UID�����݂��Ȃ��ꍇ�A���R�[�h������Ȃ���
		inputForm = new HousingRequestForm();
		housingRequestManage.addRequest("UID00000", inputForm);
		criteria = new DAOCriteria();
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("�p�����^���������ݒ肳��Ă��Ȃ��ꍇ�A�������N�G�X�g���͓o�^�����A����I�����鎖", 0, housingRequestInfos.size());

	}

	/**
	 * �������N�G�X�g���̓o�^����<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������N�G�X�g��񂪐������o�^����鎖</li>
	 * </ul>
	 */
	@Test
	public void addRequestTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingRequestInfo> housingRequestInfos;

		HousingRequestForm inputForm;

		inputForm = new HousingRequestForm();
		inputForm.setRequestName("���N�G�X�g����");
		inputForm.setPriceLower(10000L);
		inputForm.setPriceUpper(20000L);
		inputForm.setPersonalAreaLower(new BigDecimal("20.00"));
		inputForm.setPersonalAreaUpper(new BigDecimal("50.00"));
		housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00001");
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 

		Assert.assertEquals("�������N�G�X�g���������o�^����鎖", 1, housingRequestInfos.size());
		HousingRequestInfo housingRequestInfo = housingRequestInfos.get(0);
		Assert.assertEquals("�������N�G�X�g���̃��[�UID���������o�^����Ă��鎖�B", "UID00001", housingRequestInfo.getUserId());
		Assert.assertEquals("�������N�G�X�g���̃��N�G�X�g���̂��������o�^����Ă��鎖�B", "���N�G�X�g����", housingRequestInfo.getRequestName());
		Assert.assertEquals("�������N�G�X�g���̒���/���i�E�������������o�^����Ă��鎖�B", 10000L, housingRequestInfo.getPriceLower().longValue());
		Assert.assertEquals("�������N�G�X�g���̒���/���i�E������������o�^����Ă��鎖�B", 20000L, housingRequestInfo.getPriceUpper().longValue());
		Assert.assertTrue("�������N�G�X�g���̐�L�ʐρE�������������o�^����Ă��鎖�B", new BigDecimal("20.00").compareTo(housingRequestInfo.getPersonalAreaLower()) == 0);
		Assert.assertTrue("�������N�G�X�g���̐�L�ʐρE������������o�^����Ă��鎖�B", new BigDecimal("50.00").compareTo(housingRequestInfo.getPersonalAreaUpper()) == 0);
		Assert.assertEquals("�������N�G�X�g���̓o�^�҂��������o�^����Ă��鎖�B", "UID00001", housingRequestInfo.getInsUserId());
		Assert.assertEquals("�������N�G�X�g���̍ŏI�X�V�҂��������o�^����Ă��鎖�B", "UID00001", housingRequestInfo.getUpdUserId());

	}

	/**
	 * �������N�G�X�g���̓o�^����<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������N�G�X�g���̏�����������������삷�鎖</li>
	 * </ul>
	 */
	@Test
	public void addRequestMaxCntTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingRequestInfo> housingRequestInfos;

		HousingRequestForm inputForm;

		inputForm = new HousingRequestForm();
		inputForm.setRequestName("���N�G�X�g���̂P");
		housingRequestManage.addRequest("UID00001", inputForm);

		inputForm = new HousingRequestForm();
		inputForm.setRequestName("���N�G�X�g���̂Q");
		housingRequestManage.addRequest("UID00001", inputForm);

		inputForm = new HousingRequestForm();
		inputForm.setRequestName("���N�G�X�g���̂R");
		housingRequestManage.addRequest("UID00001", inputForm);

		inputForm = new HousingRequestForm();
		inputForm.setRequestName("���N�G�X�g���̂S");
		housingRequestManage.addRequest("UID00001", inputForm);

		inputForm = new HousingRequestForm();
		inputForm.setRequestName("���N�G�X�g���̂T");
		housingRequestManage.addRequest("UID00001", inputForm);

		try {
			inputForm = new HousingRequestForm();
			inputForm.setRequestName("���N�G�X�g���̂U");
			housingRequestManage.addRequest("UID00001", inputForm);
		} catch (MaxEntryOverException e) {
			Assert.assertTrue("��������ɒB���Ă���ꍇ�AMaxEntryOverException��throw����邱��", e instanceof MaxEntryOverException);
		}

		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00001");
		criteria.addOrderByClause("housingRequestId");
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g���������o�^����鎖", 5, housingRequestInfos.size());
		Assert.assertEquals("�������N�G�X�g���̃��N�G�X�g���̂��������o�^����Ă��鎖�B", "���N�G�X�g���̂P", housingRequestInfos.get(0).getRequestName());
		Assert.assertEquals("�������N�G�X�g���̃��N�G�X�g���̂��������o�^����Ă��鎖�B", "���N�G�X�g���̂Q", housingRequestInfos.get(1).getRequestName());
		Assert.assertEquals("�������N�G�X�g���̃��N�G�X�g���̂��������o�^����Ă��鎖�B", "���N�G�X�g���̂R", housingRequestInfos.get(2).getRequestName());
		Assert.assertEquals("�������N�G�X�g���̃��N�G�X�g���̂��������o�^����Ă��鎖�B", "���N�G�X�g���̂S", housingRequestInfos.get(3).getRequestName());
		Assert.assertEquals("�������N�G�X�g���̃��N�G�X�g���̂��������o�^����Ă��鎖�B", "���N�G�X�g���̂T", housingRequestInfos.get(4).getRequestName());

	}

	/**
	 * �������N�G�X�g�G���A���̓o�^����<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�s���{��CD����ƂȂ��Ă����ꍇ�A���R�[�h������Ȃ���</li>
	 *     <li>�s���{��CD�݂̂̕������N�G�X�g��񂪍���鎖</li>
	 * </ul>
	 */
	@Test
	public void addRequestPrefTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingRequestArea> housingRequestAreas;

		// �e�X�g�P�[�X���{�O�m�F
		criteria = new DAOCriteria();
		housingRequestAreas = this.housingRequestAreaDAO.selectByFilter(criteria); 
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z�������N�G�X�g�G���A���0���ł��鎖�B", 0, housingRequestAreas.size());

		HousingRequestForm inputForm;
		String requestId;

		// �s���{��CD����ƂȂ��Ă����ꍇ�A���R�[�h������Ȃ���
		inputForm = new HousingRequestForm();
		inputForm.setPrefCd("");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		housingRequestAreas = this.housingRequestAreaDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g�G���A���0���ł��鎖�B", 0, housingRequestAreas.size());

		// �s���{��CD�̃��R�[�h������鎖
		inputForm = new HousingRequestForm();
		inputForm.setPrefCd("13");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		criteria.addOrderByClause("prefCd");
		housingRequestAreas = this.housingRequestAreaDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g�G���A���̌��������������B", 1, housingRequestAreas.size());
		Assert.assertEquals("�������N�G�X�g�G���A���̓s���{��CD���������o�^����Ă��鎖�B", "13", housingRequestAreas.get(0).getPrefCd());

		// �s���{��CD�̃��R�[�h����������鎖
		inputForm = new HousingRequestForm();
		inputForm.setPrefCd("13,14");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		criteria.addOrderByClause("prefCd");
		housingRequestAreas = this.housingRequestAreaDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g�G���A���̌������������B", 2, housingRequestAreas.size());
		Assert.assertEquals("�������N�G�X�g�G���A���̓s���{��CD���������o�^����Ă��鎖�B", "13", housingRequestAreas.get(0).getPrefCd());
		Assert.assertEquals("�������N�G�X�g�G���A���̓s���{��CD���������o�^����Ă��鎖�B", "14", housingRequestAreas.get(1).getPrefCd());

	}

	/**
	 * �������N�G�X�g�G���A���̓o�^����<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�s�撬��CD����ƂȂ��Ă����ꍇ���R�[�h������Ȃ���</li>
	 *     <li>�s���{��CD�������A�s�撬��CD�����Ń��R�[�h������Ȃ���</li>
	 *     <li>�s���{��CD�ƁA�s�撬��CD�Ń��R�[�h������鎖</li>
	 * </ul>
	 */
	@Test
	public void addRequestAddressTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingRequestArea> housingRequestAreas;

		// �e�X�g�P�[�X���{�O�m�F
		criteria = new DAOCriteria();
		housingRequestAreas = this.housingRequestAreaDAO.selectByFilter(criteria); 
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z�������N�G�X�g�G���A���0���ł��鎖�B", 0, housingRequestAreas.size());

		HousingRequestForm inputForm;
		String requestId;

		// �s�撬��CD����ƂȂ��Ă����ꍇ���R�[�h������Ȃ���
		inputForm = new HousingRequestForm();
		inputForm.setAddressCd("");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		housingRequestAreas = this.housingRequestAreaDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g�G���A���0���ł��鎖�B", 0, housingRequestAreas.size());

		// �s���{��CD�������A�s�撬��CD�����Ń��R�[�h������Ȃ���
		inputForm = new HousingRequestForm();
		inputForm.setAddressCd("13101");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		housingRequestAreas = this.housingRequestAreaDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g�G���A���0���ł��鎖�B", 0, housingRequestAreas.size());

		// �s���{��CD�ƁA�s�撬��CD�Ń��R�[�h������鎖
		inputForm = new HousingRequestForm();
		inputForm.setPrefCd("13");
		inputForm.setAddressCd("13101");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		housingRequestAreas = this.housingRequestAreaDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g�G���A���̌��������������B", 1, housingRequestAreas.size());
		Assert.assertEquals("�������N�G�X�g�G���A���̓s���{��CD���������o�^����Ă��鎖�B", "13", housingRequestAreas.get(0).getPrefCd());
		Assert.assertEquals("�������N�G�X�g�G���A���̎s�撬��CD���������o�^����Ă��鎖�B", "13101", housingRequestAreas.get(0).getAddressCd());

		// �s���{��CD�ƁA�s�撬��CD�ŕ������R�[�h������鎖
		inputForm = new HousingRequestForm();
		inputForm.setPrefCd("13");
		inputForm.setAddressCd("13101,13102");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		criteria.addOrderByClause("prefCd");
		criteria.addOrderByClause("addressCd");
		housingRequestAreas = this.housingRequestAreaDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g�G���A���̌��������������B", 2, housingRequestAreas.size());
		Assert.assertEquals("�������N�G�X�g�G���A���̓s���{��CD���������o�^����Ă��鎖�B", "13", housingRequestAreas.get(0).getPrefCd());
		Assert.assertEquals("�������N�G�X�g�G���A���̎s�撬��CD���������o�^����Ă��鎖�B", "13101", housingRequestAreas.get(0).getAddressCd());
		Assert.assertEquals("�������N�G�X�g�G���A���̓s���{��CD���������o�^����Ă��鎖�B", "13", housingRequestAreas.get(1).getPrefCd());
		Assert.assertEquals("�������N�G�X�g�G���A���̎s�撬��CD���������o�^����Ă��鎖�B", "13102", housingRequestAreas.get(1).getAddressCd());

		// �s���{��CD�ƁA�s�撬��CD�ŕ������R�[�h������鎖
		inputForm = new HousingRequestForm();
		inputForm.setPrefCd("13,14");
		inputForm.setAddressCd("13101,13102,14101");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		criteria.addOrderByClause("prefCd");
		criteria.addOrderByClause("addressCd");
		housingRequestAreas = this.housingRequestAreaDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g�G���A���̌��������������B", 3, housingRequestAreas.size());
		Assert.assertEquals("�������N�G�X�g�G���A���̓s���{��CD���������o�^����Ă��鎖�B", "13", housingRequestAreas.get(0).getPrefCd());
		Assert.assertEquals("�������N�G�X�g�G���A���̎s�撬��CD���������o�^����Ă��鎖�B", "13101", housingRequestAreas.get(0).getAddressCd());
		Assert.assertEquals("�������N�G�X�g�G���A���̓s���{��CD���������o�^����Ă��鎖�B", "13", housingRequestAreas.get(1).getPrefCd());
		Assert.assertEquals("�������N�G�X�g�G���A���̎s�撬��CD���������o�^����Ă��鎖�B", "13102", housingRequestAreas.get(1).getAddressCd());
		Assert.assertEquals("�������N�G�X�g�G���A���̓s���{��CD���������o�^����Ă��鎖�B", "14", housingRequestAreas.get(2).getPrefCd());
		Assert.assertEquals("�������N�G�X�g�G���A���̎s�撬��CD���������o�^����Ă��鎖�B", "14101", housingRequestAreas.get(2).getAddressCd());

	}

	/**
	 * �������N�G�X�g�������̓o�^����<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�H��CD������ƂȂ��Ă����ꍇ���R�[�h������Ȃ���</li>
	 *     <li>�������N�G�X�g������񂪍���鎖</li>
	 * </ul>
	 */
	@Test
	public void addRequestRouteTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingReqRoute> housingReqRoutes;

		// �e�X�g�P�[�X���{�O�m�F
		criteria = new DAOCriteria();
		housingReqRoutes = this.housingReqRouteDAO.selectByFilter(criteria); 
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z�������N�G�X�g�H�����0���ł��鎖�B", 0, housingReqRoutes.size());

		HousingRequestForm inputForm;
		String requestId;

		// �H��CD����Ń��R�[�h������Ȃ���
		inputForm = new HousingRequestForm();
		inputForm.setRouteCd("");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		housingReqRoutes = this.housingReqRouteDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g�H�����0���ł��鎖�B", 0, housingReqRoutes.size());

		// �H��CD�̃��R�[�h������鎖
		inputForm = new HousingRequestForm();
		inputForm.setRouteCd("P116005");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		housingReqRoutes = this.housingReqRouteDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g�H�����̌��������������B", 1, housingReqRoutes.size());
		Assert.assertEquals("�������N�G�X�g�H�����̘H��CD���������o�^����Ă��鎖�B", "P116005",  housingReqRoutes.get(0).getRouteCd());

		// �H��CD�̕������R�[�h������鎖
		inputForm = new HousingRequestForm();
		inputForm.setRouteCd("P116005,P116006");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		criteria.addOrderByClause("routeCd");
		housingReqRoutes = this.housingReqRouteDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g�H�����̌��������������B", 2, housingReqRoutes.size());
		Assert.assertEquals("�������N�G�X�g�H�����̘H��CD���������o�^����Ă��鎖�B", "P116005",  housingReqRoutes.get(0).getRouteCd());
		Assert.assertEquals("�������N�G�X�g�H�����̘H��CD���������o�^����Ă��鎖�B", "P116006",  housingReqRoutes.get(1).getRouteCd());

	}

	/**
	 * �������N�G�X�g�Ŋ�w���̓o�^����<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�wCD����ƂȂ��Ă����ꍇ���R�[�h������Ȃ���</li>
	 *     <li>�H��CD�������A�wCD�����Ń��R�[�h������Ȃ���</li>
	 *     <li>�������N�G�X�g�Ŋ�w��񂪍���鎖</li>
	 * </ul>
	 */
	@Test
	public void addRequestStationTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingReqStation> housingReqStations;

		// �e�X�g�P�[�X���{�O�m�F
		criteria = new DAOCriteria();
		housingReqStations = this.housingReqStationDAO.selectByFilter(criteria); 
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z�������N�G�X�g�Ŋ�w���0���ł��鎖�B", 0, housingReqStations.size());

		HousingRequestForm inputForm;
		String requestId;

		// �wCD����Ń��R�[�h������Ȃ���
		inputForm = new HousingRequestForm();
		inputForm.setStationCd("");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		housingReqStations = this.housingReqStationDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g�Ŋ�w���0���ł��鎖�B", 0, housingReqStations.size());

		// �H��CD���Ȃ��A�wCD�����Ń��R�[�h������Ȃ���
		inputForm = new HousingRequestForm();
		inputForm.setStationCd("22849");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		housingReqStations = this.housingReqStationDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g�Ŋ�w���0���ł��鎖�B", 0, housingReqStations.size());

		// �H��CD�ƁA�wCD�Ń��R�[�h������鎖
		inputForm = new HousingRequestForm();
		inputForm.setRouteCd("P116005");
		inputForm.setStationCd("22849");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		housingReqStations = this.housingReqStationDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g�Ŋ�w���̌��������������B", 1, housingReqStations.size());
		Assert.assertEquals("�������N�G�X�g�Ŋ�w���̉wCD�����������B", "22849", housingReqStations.get(0).getStationCd());

		// �H��CD�ƁA�wCD�ŕ����̃��R�[�h������鎖
		inputForm = new HousingRequestForm();
		inputForm.setRouteCd("P116005");
		inputForm.setStationCd("22849,22583");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		criteria.addOrderByClause("stationCd");
		housingReqStations = this.housingReqStationDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g�Ŋ�w���̌��������������B", 2, housingReqStations.size());
		Assert.assertEquals("�������N�G�X�g�Ŋ�w���̉wCD�����������B", "22583", housingReqStations.get(0).getStationCd());
		Assert.assertEquals("�������N�G�X�g�Ŋ�w���̉wCD�����������B", "22849", housingReqStations.get(1).getStationCd());

		// �H��CD�ƁA�wCD�ŕ����̃��R�[�h������鎖
		inputForm = new HousingRequestForm();
		inputForm.setRouteCd("P116005,P116006");
		inputForm.setStationCd("22849,22583,22627");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		criteria.addOrderByClause("stationCd");
		housingReqStations = this.housingReqStationDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g�Ŋ�w���̌��������������B", 3, housingReqStations.size());
		Assert.assertEquals("�������N�G�X�g�Ŋ�w���̉wCD�����������B", "22583", housingReqStations.get(0).getStationCd());
		Assert.assertEquals("�������N�G�X�g�Ŋ�w���̉wCD�����������B", "22627", housingReqStations.get(1).getStationCd());
		Assert.assertEquals("�������N�G�X�g�Ŋ�w���̉wCD�����������B", "22849", housingReqStations.get(2).getStationCd());

	}

	/**
	 * �������N�G�X�g������ʏ��̓o�^����<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������CD����ƂȂ��Ă����ꍇ�A���R�[�h������Ȃ���</li>
	 *     <li>�������N�G�X�g������ʏ�񂪍���鎖</li>
	 * </ul>
	 */
	@Test
	public void addRequestHousingKindTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingReqKind> housingReqKinds;

		// �e�X�g�P�[�X���{�O�m�F
		criteria = new DAOCriteria();
		housingReqKinds = this.housingReqKindDAO.selectByFilter(criteria); 
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z�������N�G�X�g������ʏ��0���ł��鎖�B", 0, housingReqKinds.size());

		HousingRequestForm inputForm;
		String requestId;

		// �������CD����Ń��R�[�h������Ȃ���
		inputForm = new HousingRequestForm();
		inputForm.setHousingKindCd("");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		housingReqKinds = this.housingReqKindDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g������ʏ��0���ł��鎖�B", 0, housingReqKinds.size());

		// �������CD�̃��R�[�h������鎖
		inputForm.setHousingKindCd("001");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		housingReqKinds = this.housingReqKindDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g������ʏ��̌��������������B", 1, housingReqKinds.size());
		Assert.assertEquals("�������N�G�X�g������ʏ��̕������CD�����������B", "001", housingReqKinds.get(0).getHousingKindCd());

		// �������CD�̕������R�[�h������鎖
		inputForm.setHousingKindCd("001,002");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		criteria.addOrderByClause("housingKindCd");
		housingReqKinds = this.housingReqKindDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g������ʏ��̌��������������B", 2, housingReqKinds.size());
		Assert.assertEquals("�������N�G�X�g������ʏ��̕������CD�����������B", "001", housingReqKinds.get(0).getHousingKindCd());
		Assert.assertEquals("�������N�G�X�g������ʏ��̕������CD�����������B", "002", housingReqKinds.get(1).getHousingKindCd());

	}

	/**
	 * �������N�G�X�g�Ԏ���̓o�^����<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�Ԏ�CD����ƂȂ��Ă����ꍇ�A���R�[�h������Ȃ���</li>
	 *     <li>�������N�G�X�g�Ԏ��񂪍���鎖</li>
	 * </ul>
	 */
	@Test
	public void addRequestHousingLayoutTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingReqLayout> housingReqLayouts;

		// �e�X�g�P�[�X���{�O�m�F
		criteria = new DAOCriteria();
		housingReqLayouts = this.housingReqLayoutDAO.selectByFilter(criteria); 
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z�������N�G�X�g�Ԏ���0���ł��鎖�B", 0, housingReqLayouts.size());

		HousingRequestForm inputForm;
		String requestId;

		// �Ԏ��CD����Ń��R�[�h������Ȃ���
		inputForm = new HousingRequestForm();
		inputForm.setLayoutCd("");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		housingReqLayouts = this.housingReqLayoutDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g�Ԏ���0���ł��鎖�B", 0, housingReqLayouts.size());

		// �Ԏ��CD�̃��R�[�h������鎖
		inputForm.setLayoutCd("001");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		housingReqLayouts = this.housingReqLayoutDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g�Ԏ���̌��������������B", 1, housingReqLayouts.size());
		Assert.assertEquals("�������N�G�X�g�Ԏ���̊Ԏ�CD�����������B", "001", housingReqLayouts.get(0).getLayoutCd());

		// �Ԏ��CD�̕������R�[�h������鎖
		inputForm.setLayoutCd("001,002");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		criteria.addOrderByClause("layoutCd");
		housingReqLayouts = this.housingReqLayoutDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g�Ԏ���̌��������������B", 2, housingReqLayouts.size());
		Assert.assertEquals("�������N�G�X�g�Ԏ���̊Ԏ�CD�����������B", "001", housingReqLayouts.get(0).getLayoutCd());
		Assert.assertEquals("�������N�G�X�g�Ԏ���̊Ԏ�CD�����������B", "002", housingReqLayouts.get(1).getLayoutCd());

	}

	/**
	 * �������N�G�X�g�������������̓o�^����<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>����������CD����ƂȂ��Ă����ꍇ�A���R�[�h������Ȃ���</li>
	 *     <li>�������N�G�X�g������������񂪍���鎖</li>
	 * </ul>
	 */
	@Test
	public void addRequestHousingPartSrchTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingReqPart> housingReqParts;

		// �e�X�g�P�[�X���{�O�m�F
		criteria = new DAOCriteria();
		housingReqParts = this.housingReqPartDAO.selectByFilter(criteria); 
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z�������N�G�X�g�������������0���ł��鎖�B", 0, housingReqParts.size());

		HousingRequestForm inputForm;
		String requestId;

		// ����������CD����Ń��R�[�h������Ȃ���
		inputForm = new HousingRequestForm();
		inputForm.setPartSrchCd("");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		housingReqParts = this.housingReqPartDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g�������������0���ł��鎖�B", 0, housingReqParts.size());

		// ����������CD�̃��R�[�h������鎖
		inputForm.setPartSrchCd("FRE");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		housingReqParts = this.housingReqPartDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g������������񂪐����������ł��鎖�B", 1, housingReqParts.size());
		Assert.assertEquals("�������N�G�X�g�������������̂������CD�����������B", "FRE", housingReqParts.get(0).getPartSrchCd());

		// ����������CD�̕������R�[�h������鎖
		inputForm.setPartSrchCd("FRE,REI");
		requestId = housingRequestManage.addRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", requestId);
		criteria.addOrderByClause("partSrchCd");
		housingReqParts = this.housingReqPartDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g������������񂪐����������ł��鎖�B", 2, housingReqParts.size());
		Assert.assertEquals("�������N�G�X�g�������������̂������CD�����������B", "FRE", housingReqParts.get(0).getPartSrchCd());
		Assert.assertEquals("�������N�G�X�g�������������̂������CD�����������B", "REI", housingReqParts.get(1).getPartSrchCd());

	}

	/**
	 * �������N�G�X�g���̍X�V����<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�p�����^���������ݒ肳��Ă��Ȃ��ꍇ�A�������N�G�X�g���͍X�V�����A����I�����鎖</li>
	 *     <li>�������N�G�X�g���̃��R�[�h������鎖</li>
	 * </ul>
	 */
	@Test
	public void updateRequestParamCheckTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingRequestInfo> housingRequestInfos;

		HousingRequestForm inputForm;

		Date beforeDate;
		Date afterDate;

		inputForm = new HousingRequestForm();
		inputForm.setRequestName("���N�G�X�g����");
		inputForm.setPriceLower(10000L);
		inputForm.setPriceUpper(20000L);
		inputForm.setPersonalAreaLower(new BigDecimal("20.00"));
		inputForm.setPersonalAreaUpper(new BigDecimal("50.00"));
		inputForm.setPrefCd("01");
		inputForm.setAddressCd("01101");
		inputForm.setRouteCd("P116001");
		inputForm.setStationCd("22495");
		inputForm.setHousingKindCd("001");
		inputForm.setLayoutCd("002");
		inputForm.setPartSrchCd("FRE");
		housingRequestManage.addRequest("UID00001", inputForm);

		// �e�X�g�P�[�X���{�O�m�F
		criteria = new DAOCriteria();
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z�������N�G�X�g���1���ł��鎖�B", 1, housingRequestInfos.size());
		beforeDate = housingRequestInfos.get(0).getUpdDate();

		// ���[�UID����̏ꍇ�A���R�[�h���X�V����Ȃ���
		inputForm = new HousingRequestForm();
		housingRequestManage.updateRequest("", inputForm);
		criteria = new DAOCriteria();
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = housingRequestInfos.get(0).getUpdDate();
		Assert.assertTrue("�p�����^���������ݒ肳��Ă��Ȃ��ꍇ�A�������N�G�X�g���͍X�V�����A����I�����鎖", beforeDate.compareTo(afterDate) == 0);

		// ���[�UID��null�̏ꍇ�A���R�[�h���X�V����Ȃ���
		inputForm = new HousingRequestForm();
		housingRequestManage.updateRequest(null, inputForm);
		criteria = new DAOCriteria();
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = housingRequestInfos.get(0).getUpdDate();
		Assert.assertTrue("�p�����^���������ݒ肳��Ă��Ȃ��ꍇ�A�������N�G�X�g���͍X�V�����A����I�����鎖", beforeDate.compareTo(afterDate) == 0);

		// ���[�UID�����݂��Ȃ��ꍇ�A���R�[�h���X�V����Ȃ���
		inputForm = new HousingRequestForm();
		housingRequestManage.updateRequest("UID00000", inputForm);
		criteria = new DAOCriteria();
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = housingRequestInfos.get(0).getUpdDate();
		Assert.assertTrue("�p�����^���������ݒ肳��Ă��Ȃ��ꍇ�A�������N�G�X�g���͍X�V�����A����I�����鎖", beforeDate.compareTo(afterDate) == 0);

	}

	/**
	 * �������N�G�X�g���̍X�V����<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������N�G�X�g���̃��R�[�h���������X�V����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void updateRequestTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingRequestInfo> requestInfos;

		HousingRequestForm inputForm;

		String housingRequestId;
		Date beforeDate;
		Date afterDate;

		inputForm = new HousingRequestForm();
		inputForm.setRequestName("���N�G�X�g����");
		inputForm.setPriceLower(10000L);
		inputForm.setPriceUpper(20000L);
		inputForm.setPersonalAreaLower(new BigDecimal("20.00"));
		inputForm.setPersonalAreaUpper(new BigDecimal("50.00"));
		inputForm.setPrefCd("01");
		inputForm.setAddressCd("01101");
		inputForm.setRouteCd("P116001");
		inputForm.setStationCd("22495");
		inputForm.setHousingKindCd("001");
		inputForm.setLayoutCd("002");
		inputForm.setPartSrchCd("FRE");
		housingRequestId =  housingRequestManage.addRequest("UID00001", inputForm);

		// �e�X�g�P�[�X���{�O�m�F
		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z�������N�G�X�g���1���ł��鎖�B", 1, requestInfos.size());
		beforeDate = requestInfos.get(0).getUpdDate();

		// ���X�V�����������ɂȂ�Ȃ��悤�Ɉꎞ��~
		Thread.sleep(1000);

		// ���N�G�X�g����
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setRequestName("���N�G�X�g���̂P");
		housingRequestManage.updateRequest("UID00001", inputForm);
		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("�������N�G�X�g��񂪐������X�V����Ă��鎖", beforeDate.compareTo(afterDate) < 0);
		Assert.assertEquals("�������N�G�X�g��񂪐������X�V����Ă��鎖", "���N�G�X�g���̂P", requestInfos.get(0).getRequestName());

		// ���X�V�����������ɂȂ�Ȃ��悤�Ɉꎞ��~
		Thread.sleep(1000);

		// ����/���i�E����
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setPriceLower(100001L);
		housingRequestManage.updateRequest("UID00001", inputForm);
		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("�������N�G�X�g��񂪐������X�V����Ă��鎖", beforeDate.compareTo(afterDate) < 0);
		Assert.assertEquals("�������N�G�X�g��񂪐������X�V����Ă��鎖", 100001L, requestInfos.get(0).getPriceLower().longValue());

		// ���X�V�����������ɂȂ�Ȃ��悤�Ɉꎞ��~
		Thread.sleep(1000);

		// ����/���i�E���
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setPriceUpper(200001L);
		housingRequestManage.updateRequest("UID00001", inputForm);
		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("�������N�G�X�g��񂪐������X�V����Ă��鎖", beforeDate.compareTo(afterDate) < 0);
		Assert.assertEquals("�������N�G�X�g��񂪐������X�V����Ă��鎖", 200001L, requestInfos.get(0).getPriceUpper().longValue());

		// ���X�V�����������ɂȂ�Ȃ��悤�Ɉꎞ��~
		Thread.sleep(1000);

		// ��L�ʐρE����
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setPersonalAreaLower(new BigDecimal("21"));
		housingRequestManage.updateRequest("UID00001", inputForm);
		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("�������N�G�X�g��񂪐������X�V����Ă��鎖", beforeDate.compareTo(afterDate) < 0);
		Assert.assertTrue("�������N�G�X�g��񂪐������X�V����Ă��鎖", new BigDecimal("21.00").compareTo(requestInfos.get(0).getPersonalAreaLower()) == 0);

		// ���X�V�����������ɂȂ�Ȃ��悤�Ɉꎞ��~
		Thread.sleep(1000);

		// ��L�ʐρE���
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setPersonalAreaUpper(new BigDecimal("51"));
		housingRequestManage.updateRequest("UID00001", inputForm);
		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("�������N�G�X�g��񂪐������X�V����Ă��鎖", beforeDate.compareTo(afterDate) < 0);
		Assert.assertTrue("�������N�G�X�g��񂪐������X�V����Ă��鎖", new BigDecimal("51.00").compareTo(requestInfos.get(0).getPersonalAreaUpper()) == 0);

	}

	/**
	 * �������N�G�X�g���̍X�V����<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������N�G�X�g�G���A���̃��R�[�h���������X�V����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void updateRequestAreaTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingRequestInfo> requestInfos;
		List<HousingRequestArea> requestAreas;

		HousingRequestForm inputForm;

		String housingRequestId;
		Date beforeDate;
		Date afterDate;

		inputForm = new HousingRequestForm();
		inputForm.setRequestName("���N�G�X�g����");
		inputForm.setPriceLower(10000L);
		inputForm.setPriceUpper(20000L);
		inputForm.setPersonalAreaLower(new BigDecimal("20.00"));
		inputForm.setPersonalAreaUpper(new BigDecimal("50.00"));
		inputForm.setPrefCd("01");
		inputForm.setAddressCd("01101");
		inputForm.setRouteCd("P116001");
		inputForm.setStationCd("22495");
		inputForm.setHousingKindCd("001");
		inputForm.setLayoutCd("002");
		inputForm.setPartSrchCd("FRE");
		housingRequestId =  housingRequestManage.addRequest("UID00001", inputForm);

		// �e�X�g�P�[�X���{�O�m�F
		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z�������N�G�X�g���1���ł��鎖�B", 1, requestInfos.size());
		beforeDate = requestInfos.get(0).getUpdDate();

		// ���X�V�����������ɂȂ�Ȃ��悤�Ɉꎞ��~
		Thread.sleep(1000);

		// �s���{��CD�i�ǉ��j
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setPrefCd("01,02,03");
		housingRequestManage.updateRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("�������N�G�X�g��񂪐������X�V����Ă��鎖", beforeDate.compareTo(afterDate) < 0);
		criteria.addOrderByClause("prefCd");
		requestAreas = this.housingRequestAreaDAO.selectByFilter(criteria);
		Assert.assertEquals("�������N�G�X�g�G���A��񂪐������X�V����Ă��鎖", 3, requestAreas.size());
		Assert.assertEquals("�������N�G�X�g�G���A��񂪐������X�V����Ă��鎖", "01", requestAreas.get(0).getPrefCd());
		Assert.assertEquals("�������N�G�X�g�G���A��񂪐������X�V����Ă��鎖", "02", requestAreas.get(1).getPrefCd());
		Assert.assertEquals("�������N�G�X�g�G���A��񂪐������X�V����Ă��鎖", "03", requestAreas.get(2).getPrefCd());

		// ���X�V�����������ɂȂ�Ȃ��悤�Ɉꎞ��~
		Thread.sleep(1000);

		// �s���{��CD�i�폜�j
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setPrefCd("02,03");
		housingRequestManage.updateRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("�������N�G�X�g��񂪐������X�V����Ă��鎖", beforeDate.compareTo(afterDate) < 0);
		criteria.addOrderByClause("prefCd");
		requestAreas = this.housingRequestAreaDAO.selectByFilter(criteria);
		Assert.assertEquals("�������N�G�X�g�G���A��񂪐������X�V����Ă��鎖", 2, requestAreas.size());
		Assert.assertEquals("�������N�G�X�g�G���A��񂪐������X�V����Ă��鎖", "02", requestAreas.get(0).getPrefCd());
		Assert.assertEquals("�������N�G�X�g�G���A��񂪐������X�V����Ă��鎖", "03", requestAreas.get(1).getPrefCd());

		// �s�撬��CD�i�ǉ��j
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setAddressCd("02201,03201,03202");
		housingRequestManage.updateRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("�������N�G�X�g��񂪐������X�V����Ă��鎖", beforeDate.compareTo(afterDate) < 0);
		criteria.addOrderByClause("addressCd");
		requestAreas = this.housingRequestAreaDAO.selectByFilter(criteria);
		Assert.assertEquals("�������N�G�X�g�G���A��񂪐������X�V����Ă��鎖", 3, requestAreas.size());
		Assert.assertEquals("�������N�G�X�g�G���A��񂪐������X�V����Ă��鎖", "02201", requestAreas.get(0).getAddressCd());
		Assert.assertEquals("�������N�G�X�g�G���A��񂪐������X�V����Ă��鎖", "03201", requestAreas.get(1).getAddressCd());
		Assert.assertEquals("�������N�G�X�g�G���A��񂪐������X�V����Ă��鎖", "03202", requestAreas.get(2).getAddressCd());

		// ���X�V�����������ɂȂ�Ȃ��悤�Ɉꎞ��~
		Thread.sleep(1000);

		// �s���{��CD�i�폜�j
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setAddressCd("02201,03201");
		housingRequestManage.updateRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("�������N�G�X�g��񂪐������X�V����Ă��鎖", beforeDate.compareTo(afterDate) < 0);
		criteria.addOrderByClause("addressCd");
		requestAreas = this.housingRequestAreaDAO.selectByFilter(criteria);
		Assert.assertEquals("�������N�G�X�g�G���A��񂪐������X�V����Ă��鎖", 2, requestAreas.size());
		Assert.assertEquals("�������N�G�X�g�G���A��񂪐������X�V����Ă��鎖", "02201", requestAreas.get(0).getAddressCd());
		Assert.assertEquals("�������N�G�X�g�G���A��񂪐������X�V����Ă��鎖", "03201", requestAreas.get(1).getAddressCd());

	}

	/**
	 * �������N�G�X�g���̍X�V����<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������N�G�X�g�H�����̃��R�[�h���������X�V����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void updateRequesRouteTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingRequestInfo> requestInfos;
		List<HousingReqRoute> requestRoutes;

		HousingRequestForm inputForm;

		String housingRequestId;
		Date beforeDate;
		Date afterDate;

		inputForm = new HousingRequestForm();
		inputForm.setRequestName("���N�G�X�g����");
		inputForm.setPriceLower(10000L);
		inputForm.setPriceUpper(20000L);
		inputForm.setPersonalAreaLower(new BigDecimal("20.00"));
		inputForm.setPersonalAreaUpper(new BigDecimal("50.00"));
		inputForm.setPrefCd("01");
		inputForm.setAddressCd("01101");
		inputForm.setRouteCd("P116001");
		inputForm.setStationCd("");
		inputForm.setHousingKindCd("001");
		inputForm.setLayoutCd("002");
		inputForm.setPartSrchCd("FRE");
		housingRequestId =  housingRequestManage.addRequest("UID00001", inputForm);

		// �e�X�g�P�[�X���{�O�m�F
		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z�������N�G�X�g���1���ł��鎖�B", 1, requestInfos.size());
		beforeDate = requestInfos.get(0).getUpdDate();

		// ���X�V�����������ɂȂ�Ȃ��悤�Ɉꎞ��~
		Thread.sleep(1000);

		// �H��CD�i�ǉ��j
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setRouteCd("P116001,P116002,P116003");
		housingRequestManage.updateRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("�������N�G�X�g��񂪐������X�V����Ă��鎖", beforeDate.compareTo(afterDate) < 0);
		criteria.addOrderByClause("routeCd");
		requestRoutes = this.housingReqRouteDAO.selectByFilter(criteria);
		Assert.assertEquals("�������N�G�X�g�H����񂪐������X�V����Ă��鎖", 3, requestRoutes.size());
		Assert.assertEquals("�������N�G�X�g�H����񂪐������X�V����Ă��鎖", "P116001", requestRoutes.get(0).getRouteCd());
		Assert.assertEquals("�������N�G�X�g�H����񂪐������X�V����Ă��鎖", "P116002", requestRoutes.get(1).getRouteCd());
		Assert.assertEquals("�������N�G�X�g�H����񂪐������X�V����Ă��鎖", "P116003", requestRoutes.get(2).getRouteCd());

		// ���X�V�����������ɂȂ�Ȃ��悤�Ɉꎞ��~
		Thread.sleep(1000);

		// �H��CD�i�폜�j
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setRouteCd("P116002,P116003");
		housingRequestManage.updateRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("�������N�G�X�g��񂪐������X�V����Ă��鎖", beforeDate.compareTo(afterDate) < 0);
		criteria.addOrderByClause("routeCd");
		requestRoutes = this.housingReqRouteDAO.selectByFilter(criteria);
		Assert.assertEquals("�������N�G�X�g�H����񂪐������X�V����Ă��鎖", 2, requestRoutes.size());
		Assert.assertEquals("�������N�G�X�g�H����񂪐������X�V����Ă��鎖", "P116002", requestRoutes.get(0).getRouteCd());
		Assert.assertEquals("�������N�G�X�g�H����񂪐������X�V����Ă��鎖", "P116003", requestRoutes.get(1).getRouteCd());

	}

	/**
	 * �������N�G�X�g���̍X�V����<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������N�G�X�g�Ŋ�w���̃��R�[�h���������X�V����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void updateRequesStationTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingRequestInfo> requestInfos;
		List<HousingReqStation> requestStations;

		HousingRequestForm inputForm;

		String housingRequestId;
		Date beforeDate;
		Date afterDate;

		inputForm = new HousingRequestForm();
		inputForm.setRequestName("���N�G�X�g����");
		inputForm.setPriceLower(10000L);
		inputForm.setPriceUpper(20000L);
		inputForm.setPersonalAreaLower(new BigDecimal("20.00"));
		inputForm.setPersonalAreaUpper(new BigDecimal("50.00"));
		inputForm.setPrefCd("01");
		inputForm.setAddressCd("01101");
		inputForm.setRouteCd("P116001");
		inputForm.setStationCd("22495");
		inputForm.setHousingKindCd("001");
		inputForm.setLayoutCd("002");
		inputForm.setPartSrchCd("FRE");
		housingRequestId =  housingRequestManage.addRequest("UID00001", inputForm);

		// �e�X�g�P�[�X���{�O�m�F
		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z�������N�G�X�g���1���ł��鎖�B", 1, requestInfos.size());
		beforeDate = requestInfos.get(0).getUpdDate();

		// ���X�V�����������ɂȂ�Ȃ��悤�Ɉꎞ��~
		Thread.sleep(1000);

		// �H��CD�i�ǉ��j
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setStationCd("22495,22811,22528");
		housingRequestManage.updateRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("�������N�G�X�g��񂪐������X�V����Ă��鎖", beforeDate.compareTo(afterDate) < 0);
		criteria.addOrderByClause("stationCd");
		requestStations = this.housingReqStationDAO.selectByFilter(criteria);
		Assert.assertEquals("�������N�G�X�g�Ŋ�w��񂪐������X�V����Ă��鎖", 3, requestStations.size());
		Assert.assertEquals("�������N�G�X�g�Ŋ�w��񂪐������X�V����Ă��鎖", "22495", requestStations.get(0).getStationCd());
		Assert.assertEquals("�������N�G�X�g�Ŋ�w��񂪐������X�V����Ă��鎖", "22528", requestStations.get(1).getStationCd());
		Assert.assertEquals("�������N�G�X�g�Ŋ�w��񂪐������X�V����Ă��鎖", "22811", requestStations.get(2).getStationCd());

		// ���X�V�����������ɂȂ�Ȃ��悤�Ɉꎞ��~
		Thread.sleep(1000);

		// �H��CD�i�폜�j
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setStationCd("22811,22528");
		housingRequestManage.updateRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("�������N�G�X�g��񂪐������X�V����Ă��鎖", beforeDate.compareTo(afterDate) < 0);
		criteria.addOrderByClause("stationCd");
		requestStations = this.housingReqStationDAO.selectByFilter(criteria);
		Assert.assertEquals("�������N�G�X�g�Ŋ�w��񂪐������X�V����Ă��鎖", 2, requestStations.size());
		Assert.assertEquals("�������N�G�X�g�Ŋ�w��񂪐������X�V����Ă��鎖", "22528", requestStations.get(0).getStationCd());
		Assert.assertEquals("�������N�G�X�g�Ŋ�w��񂪐������X�V����Ă��鎖", "22811", requestStations.get(1).getStationCd());

	}

	/**
	 * �������N�G�X�g���̍X�V����<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������N�G�X�g������ޏ��̃��R�[�h���������X�V����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void updateRequesHousingKindTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingRequestInfo> requestInfos;
		List<HousingReqKind> requestKinds;

		HousingRequestForm inputForm;

		String housingRequestId;
		Date beforeDate;
		Date afterDate;

		inputForm = new HousingRequestForm();
		inputForm.setRequestName("���N�G�X�g����");
		inputForm.setPriceLower(10000L);
		inputForm.setPriceUpper(20000L);
		inputForm.setPersonalAreaLower(new BigDecimal("20.00"));
		inputForm.setPersonalAreaUpper(new BigDecimal("50.00"));
		inputForm.setPrefCd("01");
		inputForm.setAddressCd("01101");
		inputForm.setRouteCd("P116001");
		inputForm.setStationCd("22495");
		inputForm.setHousingKindCd("001");
		inputForm.setLayoutCd("002");
		inputForm.setPartSrchCd("FRE");
		housingRequestId =  housingRequestManage.addRequest("UID00001", inputForm);

		// �e�X�g�P�[�X���{�O�m�F
		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z�������N�G�X�g���1���ł��鎖�B", 1, requestInfos.size());
		beforeDate = requestInfos.get(0).getUpdDate();

		// ���X�V�����������ɂȂ�Ȃ��悤�Ɉꎞ��~
		Thread.sleep(1000);

		// �������CD�i�ǉ��j
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setHousingKindCd("001,002,003");
		housingRequestManage.updateRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("�������N�G�X�g��񂪐������X�V����Ă��鎖", beforeDate.compareTo(afterDate) < 0);
		criteria.addOrderByClause("housingKindCd");
		requestKinds = this.housingReqKindDAO.selectByFilter(criteria);
		Assert.assertEquals("�������N�G�X�g������ޏ�񂪐������X�V����Ă��鎖", 3, requestKinds.size());
		Assert.assertEquals("�������N�G�X�g������ޏ�񂪐������X�V����Ă��鎖", "001", requestKinds.get(0).getHousingKindCd());
		Assert.assertEquals("�������N�G�X�g������ޏ�񂪐������X�V����Ă��鎖", "002", requestKinds.get(1).getHousingKindCd());
		Assert.assertEquals("�������N�G�X�g������ޏ�񂪐������X�V����Ă��鎖", "003", requestKinds.get(2).getHousingKindCd());

		// ���X�V�����������ɂȂ�Ȃ��悤�Ɉꎞ��~
		Thread.sleep(1000);

		// �������CD�i�폜�j
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setHousingKindCd("002,003");
		housingRequestManage.updateRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("�������N�G�X�g��񂪐������X�V����Ă��鎖", beforeDate.compareTo(afterDate) < 0);
		criteria.addOrderByClause("housingKindCd");
		requestKinds = this.housingReqKindDAO.selectByFilter(criteria);
		Assert.assertEquals("�������N�G�X�g������ޏ�񂪐������X�V����Ă��鎖", 2, requestKinds.size());
		Assert.assertEquals("�������N�G�X�g������ޏ�񂪐������X�V����Ă��鎖", "002", requestKinds.get(0).getHousingKindCd());
		Assert.assertEquals("�������N�G�X�g������ޏ�񂪐������X�V����Ă��鎖", "003", requestKinds.get(1).getHousingKindCd());

	}

	/**
	 * �������N�G�X�g���̍X�V����<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������N�G�X�g�Ԏ���̃��R�[�h���������X�V����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void updateRequesLayoutTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingRequestInfo> requestInfos;
		List<HousingReqLayout> requestLayouts;

		HousingRequestForm inputForm;

		String housingRequestId;
		Date beforeDate;
		Date afterDate;

		inputForm = new HousingRequestForm();
		inputForm.setRequestName("���N�G�X�g����");
		inputForm.setPriceLower(10000L);
		inputForm.setPriceUpper(20000L);
		inputForm.setPersonalAreaLower(new BigDecimal("20.00"));
		inputForm.setPersonalAreaUpper(new BigDecimal("50.00"));
		inputForm.setPrefCd("01");
		inputForm.setAddressCd("01101");
		inputForm.setRouteCd("P116001");
		inputForm.setStationCd("22495");
		inputForm.setHousingKindCd("001");
		inputForm.setLayoutCd("002");
		inputForm.setPartSrchCd("FRE");
		housingRequestId =  housingRequestManage.addRequest("UID00001", inputForm);

		// �e�X�g�P�[�X���{�O�m�F
		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z�������N�G�X�g���1���ł��鎖�B", 1, requestInfos.size());
		beforeDate = requestInfos.get(0).getUpdDate();

		// ���X�V�����������ɂȂ�Ȃ��悤�Ɉꎞ��~
		Thread.sleep(1000);

		// �Ԏ�CD�i�ǉ��j
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setLayoutCd("002,003,004");
		housingRequestManage.updateRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("�������N�G�X�g��񂪐������X�V����Ă��鎖", beforeDate.compareTo(afterDate) < 0);
		criteria.addOrderByClause("layoutCd");
		requestLayouts = this.housingReqLayoutDAO.selectByFilter(criteria);
		Assert.assertEquals("�������N�G�X�g�Ԏ��񂪐������X�V����Ă��鎖", 3, requestLayouts.size());
		Assert.assertEquals("�������N�G�X�g�Ԏ��񂪐������X�V����Ă��鎖", "002", requestLayouts.get(0).getLayoutCd());
		Assert.assertEquals("�������N�G�X�g�Ԏ��񂪐������X�V����Ă��鎖", "003", requestLayouts.get(1).getLayoutCd());
		Assert.assertEquals("�������N�G�X�g�Ԏ��񂪐������X�V����Ă��鎖", "004", requestLayouts.get(2).getLayoutCd());

		// ���X�V�����������ɂȂ�Ȃ��悤�Ɉꎞ��~
		Thread.sleep(1000);

		// �Ԏ�CD�i�폜�j
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setLayoutCd("003,004");
		housingRequestManage.updateRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("�������N�G�X�g��񂪐������X�V����Ă��鎖", beforeDate.compareTo(afterDate) < 0);
		criteria.addOrderByClause("layoutCd");
		requestLayouts = this.housingReqLayoutDAO.selectByFilter(criteria);
		Assert.assertEquals("�������N�G�X�g�Ԏ��񂪐������X�V����Ă��鎖", 2, requestLayouts.size());
		Assert.assertEquals("�������N�G�X�g�Ԏ��񂪐������X�V����Ă��鎖", "003", requestLayouts.get(0).getLayoutCd());
		Assert.assertEquals("�������N�G�X�g�Ԏ��񂪐������X�V����Ă��鎖", "004", requestLayouts.get(1).getLayoutCd());

	}

	/**
	 * �������N�G�X�g���̍X�V����<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������N�G�X�g�������������̃��R�[�h���������X�V����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void updateRequesPartSrchTest() throws Exception {

		DAOCriteria criteria = null;
		List<HousingRequestInfo> requestInfos;
		List<HousingReqPart> requestPartSrchs;

		HousingRequestForm inputForm;

		String housingRequestId;
		Date beforeDate;
		Date afterDate;

		inputForm = new HousingRequestForm();
		inputForm.setRequestName("���N�G�X�g����");
		inputForm.setPriceLower(10000L);
		inputForm.setPriceUpper(20000L);
		inputForm.setPersonalAreaLower(new BigDecimal("20.00"));
		inputForm.setPersonalAreaUpper(new BigDecimal("50.00"));
		inputForm.setPrefCd("01");
		inputForm.setAddressCd("01101");
		inputForm.setRouteCd("P116001");
		inputForm.setStationCd("22495");
		inputForm.setHousingKindCd("001");
		inputForm.setLayoutCd("002");
		inputForm.setPartSrchCd("FRE");
		housingRequestId =  housingRequestManage.addRequest("UID00001", inputForm);

		// �e�X�g�P�[�X���{�O�m�F
		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z�������N�G�X�g���1���ł��鎖�B", 1, requestInfos.size());
		beforeDate = requestInfos.get(0).getUpdDate();

		// ���X�V�����������ɂȂ�Ȃ��悤�Ɉꎞ��~
		Thread.sleep(1000);

		// ����������CD�i�ǉ��j
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setPartSrchCd("FRE,REI,S10");
		housingRequestManage.updateRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("�������N�G�X�g��񂪐������X�V����Ă��鎖", beforeDate.compareTo(afterDate) < 0);
		criteria.addOrderByClause("partSrchCd");
		requestPartSrchs = this.housingReqPartDAO.selectByFilter(criteria);
		Assert.assertEquals("�������N�G�X�g������������񂪐������X�V����Ă��鎖", 3, requestPartSrchs.size());
		Assert.assertEquals("�������N�G�X�g������������񂪐������X�V����Ă��鎖", "FRE", requestPartSrchs.get(0).getPartSrchCd());
		Assert.assertEquals("�������N�G�X�g������������񂪐������X�V����Ă��鎖", "REI", requestPartSrchs.get(1).getPartSrchCd());
		Assert.assertEquals("�������N�G�X�g������������񂪐������X�V����Ă��鎖", "S10", requestPartSrchs.get(2).getPartSrchCd());

		// ���X�V�����������ɂȂ�Ȃ��悤�Ɉꎞ��~
		Thread.sleep(1000);

		// ����������CD�i�폜�j
		inputForm.setHousingRequestId(housingRequestId);
		inputForm.setPartSrchCd("REI,S10");
		housingRequestManage.updateRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		requestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		afterDate = requestInfos.get(0).getUpdDate();
		Assert.assertTrue("�������N�G�X�g��񂪐������X�V����Ă��鎖", beforeDate.compareTo(afterDate) < 0);
		criteria.addOrderByClause("partSrchCd");
		requestPartSrchs = this.housingReqPartDAO.selectByFilter(criteria);
		Assert.assertEquals("�������N�G�X�g������������񂪐������X�V����Ă��鎖", 2, requestPartSrchs.size());
		Assert.assertEquals("�������N�G�X�g������������񂪐������X�V����Ă��鎖", "REI", requestPartSrchs.get(0).getPartSrchCd());
		Assert.assertEquals("�������N�G�X�g������������񂪐������X�V����Ă��鎖", "S10", requestPartSrchs.get(1).getPartSrchCd());

	}

	/**
	 * �������N�G�X�g�̍폜����<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�p�����^���������ݒ肳��Ă��Ȃ��ꍇ�A�������N�G�X�g���폜���ꂸ�A����I�����鎖</li>
	 * </ul>
	 */
	@Test
	public void delRequestParamCheckTest() throws Exception {

		DAOCriteria criteria;
		List<HousingRequestInfo> housingRequestInfos;

		// �폜�Ώۃf�[�^�̓o�^
		HousingRequestInfo housingRequestInfo = new HousingRequestInfo();
		housingRequestInfo.setUserId("UID00001");
		housingRequestInfo.setRequestName("���N�G�X�g�P");
		housingRequestInfoDAO.insert(new HousingRequestInfo[] { housingRequestInfo });

		// �e�X�g�P�[�X���{�O�m�F
		criteria = new DAOCriteria();
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z�������N�G�X�g���1���ł��鎖�B", 1, housingRequestInfos.size());

		HousingRequestForm inputForm;

		// �������N�G�X�gID��null
		inputForm = new HousingRequestForm();
		inputForm.setHousingRequestId(null);
		housingRequestManage.delRequest("UDI00001", inputForm);
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g���1���ł��鎖�B", 1, housingRequestInfos.size());

		// �������N�G�X�gID����
		inputForm = new HousingRequestForm();
		inputForm.setHousingRequestId("");
		housingRequestManage.delRequest("UDI00001", inputForm);
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g���1���ł��鎖�B", 1, housingRequestInfos.size());

		// ���[�UID��null
		inputForm = new HousingRequestForm();
		inputForm.setHousingRequestId(housingRequestInfo.getHousingRequestId());
		housingRequestManage.delRequest(null, inputForm);
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g���1���ł��鎖�B", 1, housingRequestInfos.size());

		// ���[�UID����
		inputForm = new HousingRequestForm();
		inputForm.setHousingRequestId(housingRequestInfo.getHousingRequestId());
		housingRequestManage.delRequest("", inputForm);
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g���1���ł��鎖�B", 1, housingRequestInfos.size());

	}

	/**
	 * �������N�G�X�g�̍폜����<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>����������CD����ƂȂ��Ă����ꍇ�A���R�[�h������Ȃ���</li>
	 *     <li>�������N�G�X�g������������񂪍���鎖</li>
	 * </ul>
	 */
	@Test
	public void delRequestTest() throws Exception {

		DAOCriteria criteria;
		List<HousingRequestInfo> housingRequestInfos;
		List<HousingRequestArea> housingRequestAreas;
		List<HousingReqRoute> housingReqRoutes;
		List<HousingReqStation> housingReqStations;
		List<HousingReqKind> housingReqKinds;
		List<HousingReqLayout> housingReqLayouts;
		List<HousingReqPart> housingReqParts;

		// �폜�Ώۃf�[�^�̓o�^
		HousingRequestInfo housingRequestInfo = new HousingRequestInfo();
		housingRequestInfo.setUserId("UID00001");
		housingRequestInfo.setRequestName("���N�G�X�g�P");
		housingRequestInfoDAO.insert(new HousingRequestInfo[] { housingRequestInfo });

		HousingRequestArea housingRequestArea = new HousingRequestArea();
		housingRequestArea.setHousingRequestId(housingRequestInfo.getHousingRequestId());
		housingRequestArea.setPrefCd("13");
		housingRequestAreaDAO.insert(new HousingRequestArea[] { housingRequestArea });

		housingRequestArea = new HousingRequestArea();
		housingRequestArea.setHousingRequestId(housingRequestInfo.getHousingRequestId());
		housingRequestArea.setPrefCd("14");
		housingRequestArea.setAddressCd("15");
		housingRequestAreaDAO.insert(new HousingRequestArea[] { housingRequestArea });

		HousingReqRoute housingReqRoute = new HousingReqRoute();
		housingReqRoute.setHousingRequestId(housingRequestInfo.getHousingRequestId());
		housingReqRoute.setRouteCd("P116005");
		housingReqRouteDAO.insert(new HousingReqRoute[] { housingReqRoute });

		housingReqRoute = new HousingReqRoute();
		housingReqRoute.setHousingRequestId(housingRequestInfo.getHousingRequestId());
		housingReqRoute.setRouteCd("P116006");
		housingReqRouteDAO.insert(new HousingReqRoute[] { housingReqRoute });

		HousingReqStation housingReqStation = new HousingReqStation();
		housingReqStation.setHousingRequestId(housingRequestInfo.getHousingRequestId());
		housingReqStation.setStationCd("22849");
		housingReqStationDAO.insert(new HousingReqStation[] { housingReqStation });

		housingReqStation = new HousingReqStation();
		housingReqStation.setHousingRequestId(housingRequestInfo.getHousingRequestId());
		housingReqStation.setStationCd("22583");
		housingReqStationDAO.insert(new HousingReqStation[] { housingReqStation });

		HousingReqKind housingReqKind = new HousingReqKind();
		housingReqKind.setHousingRequestId(housingRequestInfo.getHousingRequestId());
		housingReqKind.setHousingKindCd("001");
		housingReqKindDAO.insert(new HousingReqKind[] { housingReqKind });

		housingReqKind = new HousingReqKind();
		housingReqKind.setHousingRequestId(housingRequestInfo.getHousingRequestId());
		housingReqKind.setHousingKindCd("002");
		housingReqKindDAO.insert(new HousingReqKind[] { housingReqKind });

		HousingReqLayout housingReqLayout = new HousingReqLayout();
		housingReqLayout.setHousingRequestId(housingRequestInfo.getHousingRequestId());
		housingReqLayout.setLayoutCd("001");
		housingReqLayoutDAO.insert(new HousingReqLayout[] { housingReqLayout });

		housingReqLayout = new HousingReqLayout();
		housingReqLayout.setHousingRequestId(housingRequestInfo.getHousingRequestId());
		housingReqLayout.setLayoutCd("002");
		housingReqLayoutDAO.insert(new HousingReqLayout[] { housingReqLayout });

		HousingReqPart housingReqPart = new HousingReqPart();
		housingReqPart.setHousingRequestId(housingRequestInfo.getHousingRequestId());
		housingReqPart.setPartSrchCd("FRE");
		housingReqPartDAO.insert(new HousingReqPart[] { housingReqPart });

		housingReqPart = new HousingReqPart();
		housingReqPart.setHousingRequestId(housingRequestInfo.getHousingRequestId());
		housingReqPart.setPartSrchCd("REI");
		housingReqPartDAO.insert(new HousingReqPart[] { housingReqPart });

		criteria = new DAOCriteria();
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z�������N�G�X�g���1���ł��鎖�B", 1, housingRequestInfos.size());
		housingRequestAreas = this.housingRequestAreaDAO.selectByFilter(criteria); 
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z�������N�G�X�g�G���A���2���ł��鎖�B", 2, housingRequestAreas.size());
		housingReqRoutes = this.housingReqRouteDAO.selectByFilter(criteria); 
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z�������N�G�X�g�H�����2���ł��鎖�B", 2, housingReqRoutes.size());
		housingReqStations = this.housingReqStationDAO.selectByFilter(criteria); 
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z�������N�G�X�g�Ŋ�w���2���ł��鎖�B", 2, housingReqStations.size());
		housingReqKinds = this.housingReqKindDAO.selectByFilter(criteria); 
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z�������N�G�X�g������ʏ��2���ł��鎖�B", 2, housingReqKinds.size());
		housingReqLayouts = this.housingReqLayoutDAO.selectByFilter(criteria); 
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z�������N�G�X�g�Ԏ���2���ł��鎖�B", 2, housingReqLayouts.size());
		housingReqParts = this.housingReqPartDAO.selectByFilter(criteria); 
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z�������N�G�X�g�������������2���ł��鎖�B", 2, housingReqParts.size());

		HousingRequestForm inputForm;

		inputForm = new HousingRequestForm();
		inputForm.setHousingRequestId(housingRequestInfo.getHousingRequestId());
		housingRequestManage.delRequest("UID00001", inputForm);

		criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", housingRequestInfo.getHousingRequestId());
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g���0���ł��鎖�B", 0, housingRequestInfos.size());
		housingRequestAreas = this.housingRequestAreaDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g�G���A���0���ł��鎖�B", 0, housingRequestAreas.size());
		housingReqRoutes = this.housingReqRouteDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g�H�����0���ł��鎖�B", 0, housingReqRoutes.size());
		housingReqStations = this.housingReqStationDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g�Ŋ�w���0���ł��鎖�B", 0, housingReqStations.size());
		housingReqKinds = this.housingReqKindDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g������ʏ��0���ł��鎖�B", 0, housingReqKinds.size());
		housingReqLayouts = this.housingReqLayoutDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g�Ԏ���0���ł��鎖�B", 0, housingReqLayouts.size());
		housingReqParts = this.housingReqPartDAO.selectByFilter(criteria); 
		Assert.assertEquals("�������N�G�X�g�������������0���ł��鎖�B", 0, housingReqParts.size());

	}

	/**
	 * �������N�G�X�g�̌�������<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�p�����^���������ݒ肳��Ă��Ȃ��ꍇ�A�������ʂ�0���Ő���I�����鎖</li>
	 * </ul>
	 */
	@Test
	public void searchRequestParamCheckTest() throws Exception {

		DAOCriteria criteria;
		List<HousingRequestInfo> housingRequestInfos;

		// �����Ώۃf�[�^�̓o�^
		HousingRequestInfo housingRequestInfo = new HousingRequestInfo();
		housingRequestInfo.setUserId("UID00001");
		housingRequestInfo.setRequestName("���N�G�X�g�P");
		housingRequestInfoDAO.insert(new HousingRequestInfo[] { housingRequestInfo });

		// �e�X�g�P�[�X���{�O�m�F
		criteria = new DAOCriteria();
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z�������N�G�X�g���1���ł��鎖�B", 1, housingRequestInfos.size());

		List<HousingRequest> requests;

		// ���[�UID��null
		requests = housingRequestManage.searchRequest(null);
		Assert.assertEquals("�������ʂ�0���ł��鎖�B", 0, requests.size());

		// ���[�UID����
		requests = housingRequestManage.searchRequest("");
		Assert.assertEquals("�������ʂ�0���ł��鎖�B", 0, requests.size());

	}

	/**
	 * �������N�G�X�g�̌�������<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������N�G�X�g����������������鎖</li>
	 * </ul>
	 */
	@Test
	public void searchRequestTest() throws Exception {

		DAOCriteria criteria;
		List<HousingRequestInfo> housingRequestInfos;

		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, -10);
		Date insDate = calendar.getTime();

		// �����Ώۃf�[�^�̓o�^
		HousingRequestInfo housingRequestInfo = new HousingRequestInfo();
		housingRequestInfo.setUserId("UID00001");
		housingRequestInfo.setRequestName("���N�G�X�g�P�P");
		housingRequestInfo.setInsDate(insDate);
		calendar.setTime(now);
		calendar.add(Calendar.DAY_OF_MONTH, -3);
		housingRequestInfo.setUpdDate(calendar.getTime());
		housingRequestInfoDAO.insert(new HousingRequestInfo[] { housingRequestInfo });

		housingRequestInfo = new HousingRequestInfo();
		housingRequestInfo.setUserId("UID00001");
		housingRequestInfo.setRequestName("���N�G�X�g�P�Q");
		housingRequestInfo.setInsDate(insDate);
		calendar.setTime(now);
		calendar.add(Calendar.DAY_OF_MONTH, -2);
		housingRequestInfo.setUpdDate(calendar.getTime());
		housingRequestInfoDAO.insert(new HousingRequestInfo[] { housingRequestInfo });

		housingRequestInfo = new HousingRequestInfo();
		housingRequestInfo.setUserId("UID00002");
		housingRequestInfo.setRequestName("���N�G�X�g�Q�P");
		housingRequestInfo.setInsDate(insDate);
		calendar.setTime(now);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		housingRequestInfo.setUpdDate(calendar.getTime());
		housingRequestInfoDAO.insert(new HousingRequestInfo[] { housingRequestInfo });

		// �e�X�g�P�[�X���{�O�m�F
		criteria = new DAOCriteria();
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z�������N�G�X�g���3���ł��鎖�B", 3, housingRequestInfos.size());

		List<HousingRequest> requests;

		// �������������ʂ��擾����邱��
		requests = housingRequestManage.searchRequest("UID00001");
		Assert.assertEquals("�������ʌ��������������B", 2, requests.size());
		Assert.assertTrue("�������ʂ̕��я������������B", requests.get(0).getHousingRequestInfo().getUpdDate().compareTo(requests.get(1).getHousingRequestInfo().getUpdDate()) > 0);
		Assert.assertEquals("�������ʂ̕������N�G�X�g��񂪐��������B", "���N�G�X�g�P�Q", requests.get(0).getHousingRequestInfo().getRequestName());
		Assert.assertEquals("�������ʂ̕������N�G�X�g��񂪐��������B", "���N�G�X�g�P�P", requests.get(1).getHousingRequestInfo().getRequestName());

	}

	/**
	 * �������N�G�X�g�̌����擾����<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�p�����^���������ݒ肳��Ă��Ȃ��ꍇ�A�������ʂ�0���Ő���I�����鎖</li>
	 * </ul>
	 */
	@Test
	public void searchRequestCntParamCheckTest() throws Exception {

		DAOCriteria criteria;
		List<HousingRequestInfo> housingRequestInfos;
		int cnt = 1;

		// �����Ώۃf�[�^�̓o�^
		HousingRequestInfo housingRequestInfo = new HousingRequestInfo();
		housingRequestInfo.setUserId("UID00001");
		housingRequestInfo.setRequestName("���N�G�X�g�P");
		housingRequestInfoDAO.insert(new HousingRequestInfo[] { housingRequestInfo });

		// �e�X�g�P�[�X���{�O�m�F
		criteria = new DAOCriteria();
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z�������N�G�X�g���1���ł��鎖�B", 1, housingRequestInfos.size());

		// ���[�UID��null
		cnt = housingRequestManage.searchRequestCnt(null);
		Assert.assertEquals("�������ʂ�0���ł��鎖�B", 0, cnt);

		// ���[�UID����
		cnt = housingRequestManage.searchRequestCnt("");
		Assert.assertEquals("�������ʂ�0���ł��鎖�B", 0, cnt);

	}

	/**
	 * �������N�G�X�g�̌����擾����<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������N�G�X�g�̌������������擾����鎖</li>
	 * </ul>
	 */
	@Test
	public void searchRequestCntTest() throws Exception {

		DAOCriteria criteria;
		List<HousingRequestInfo> housingRequestInfos;

		// �����Ώۃf�[�^�̓o�^
		HousingRequestInfo housingRequestInfo = new HousingRequestInfo();
		housingRequestInfo.setUserId("UID00001");
		housingRequestInfo.setRequestName("���N�G�X�g�P�P");
		housingRequestInfoDAO.insert(new HousingRequestInfo[] { housingRequestInfo });

		housingRequestInfo = new HousingRequestInfo();
		housingRequestInfo.setUserId("UID00001");
		housingRequestInfo.setRequestName("���N�G�X�g�P�Q");
		housingRequestInfoDAO.insert(new HousingRequestInfo[] { housingRequestInfo });

		housingRequestInfo = new HousingRequestInfo();
		housingRequestInfo.setUserId("UID00002");
		housingRequestInfo.setRequestName("���N�G�X�g�Q�P");
		housingRequestInfoDAO.insert(new HousingRequestInfo[] { housingRequestInfo });

		// �e�X�g�P�[�X���{�O�m�F
		criteria = new DAOCriteria();
		housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria); 
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z�������N�G�X�g���3���ł��鎖�B", 3, housingRequestInfos.size());

		int cnt = 0;

		// �������������ʂ��擾����邱��
		cnt = housingRequestManage.searchRequestCnt("UID00001");
		Assert.assertEquals("�������ʌ��������������B", 2, cnt);

	}

	/**
	 * �������N�G�X�g����̕�����������<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������N�G�X�g�̏����ɍ��v����������񂪌�������鎖</li>
	 * </ul>
	 */
	@Test
	public void searchHousingTest() throws Exception {

		// �����ΏۂƂ��镨�����𐶐�
		makeTestHousingData();

		HousingRequestForm inputForm;
		String housingRequestId;

		RequestSearchForm searchForm;
		int cnt = 0;
		HousingInfo housingInfo;

		// ����/���i�E����
		inputForm = new HousingRequestForm();
		inputForm.setRequestName("���N�G�X�g����");
		inputForm.setPriceLower(500000L);
		housingRequestId = housingRequestManage.addRequest("UID00001", inputForm);

		searchForm = requestSearchFormFactory.createRequestSearchForm();
		searchForm.setHousingRequestId(housingRequestId);
		cnt = housingRequestManage.searchHousing("UID00001", searchForm);
		Assert.assertEquals("�������N�G�X�g�������ʂƂȂ镨�����������������ƁB", 1, cnt);
		housingInfo = (HousingInfo) searchForm.getRows().get(0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("�������N�G�X�g�������ʂƂȂ镨�������������ƁB", "SHOU00005", housingInfo.getSysHousingCd() );

		// ����/���i�E���
		inputForm = new HousingRequestForm();
		inputForm.setRequestName("���N�G�X�g����");
		inputForm.setPriceUpper(100000L);
		housingRequestId = housingRequestManage.addRequest("UID00002", inputForm);

		searchForm = requestSearchFormFactory.createRequestSearchForm();
		searchForm.setHousingRequestId(housingRequestId);
		cnt = housingRequestManage.searchHousing("UID00002", searchForm);
		Assert.assertEquals("�������N�G�X�g�������ʂƂȂ镨�����������������ƁB", 1, cnt);
		housingInfo = (HousingInfo) searchForm.getRows().get(0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("�������N�G�X�g�������ʂƂȂ镨�������������ƁB", "SHOU00001", housingInfo.getSysHousingCd() );

		// ��L�ʐρE����
		inputForm = new HousingRequestForm();
		inputForm.setRequestName("���N�G�X�g����");
		inputForm.setPersonalAreaLower(new BigDecimal("100"));
		housingRequestId = housingRequestManage.addRequest("UID00003", inputForm);

		searchForm = requestSearchFormFactory.createRequestSearchForm();
		searchForm.setHousingRequestId(housingRequestId);
		cnt = housingRequestManage.searchHousing("UID00003", searchForm);
		Assert.assertEquals("�������N�G�X�g�������ʂƂȂ镨�����������������ƁB", 1, cnt);
		housingInfo = (HousingInfo) searchForm.getRows().get(0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("�������N�G�X�g�������ʂƂȂ镨�������������ƁB", "SHOU00005", housingInfo.getSysHousingCd() );

		// ��L�ʐρE���
		inputForm = new HousingRequestForm();
		inputForm.setRequestName("���N�G�X�g����");
		inputForm.setPersonalAreaUpper(new BigDecimal("60"));
		housingRequestId = housingRequestManage.addRequest("UID00004", inputForm);

		searchForm = requestSearchFormFactory.createRequestSearchForm();
		searchForm.setHousingRequestId(housingRequestId);
		cnt = housingRequestManage.searchHousing("UID00004", searchForm);
		Assert.assertEquals("�������N�G�X�g�������ʂƂȂ镨�����������������ƁB", 1, cnt);
		housingInfo = (HousingInfo) searchForm.getRows().get(0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("�������N�G�X�g�������ʂƂȂ镨�������������ƁB", "SHOU00001", housingInfo.getSysHousingCd() );

		// �s���{��CD
		inputForm = new HousingRequestForm();
		inputForm.setRequestName("���N�G�X�g����");
		inputForm.setPrefCd("01");
		housingRequestId = housingRequestManage.addRequest("UID00005", inputForm);

		searchForm = requestSearchFormFactory.createRequestSearchForm();
		searchForm.setHousingRequestId(housingRequestId);
		cnt = housingRequestManage.searchHousing("UID00005", searchForm);
		Assert.assertEquals("�������N�G�X�g�������ʂƂȂ镨�����������������ƁB", 1, cnt);
		housingInfo = (HousingInfo) searchForm.getRows().get(0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("�������N�G�X�g�������ʂƂȂ镨�������������ƁB", "SHOU00001", housingInfo.getSysHousingCd() );

		// �s�撬��CD
		inputForm = new HousingRequestForm();
		inputForm.setRequestName("���N�G�X�g����");
		inputForm.setPrefCd("02");
		inputForm.setAddressCd("02101");
		housingRequestId = housingRequestManage.addRequest("UID00006", inputForm);

		searchForm = requestSearchFormFactory.createRequestSearchForm();
		searchForm.setHousingRequestId(housingRequestId);
		cnt = housingRequestManage.searchHousing("UID00006", searchForm);
		Assert.assertEquals("�������N�G�X�g�������ʂƂȂ镨�����������������ƁB", 1, cnt);
		housingInfo = (HousingInfo) searchForm.getRows().get(0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("�������N�G�X�g�������ʂƂȂ镨�������������ƁB", "SHOU00002", housingInfo.getSysHousingCd() );

		// �H��CD
		inputForm = new HousingRequestForm();
		inputForm.setRequestName("���N�G�X�g����");
		inputForm.setRouteCd("P116003");
		housingRequestId = housingRequestManage.addRequest("UID00007", inputForm);

		searchForm = requestSearchFormFactory.createRequestSearchForm();
		searchForm.setHousingRequestId(housingRequestId);
		cnt = housingRequestManage.searchHousing("UID00007", searchForm);
		Assert.assertEquals("�������N�G�X�g�������ʂƂȂ镨�����������������ƁB", 1, cnt);
		housingInfo = (HousingInfo) searchForm.getRows().get(0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("�������N�G�X�g�������ʂƂȂ镨�������������ƁB", "SHOU00003", housingInfo.getSysHousingCd() );

		// �wCD
		inputForm = new HousingRequestForm();
		inputForm.setRequestName("���N�G�X�g����");
		inputForm.setRouteCd("P116004");
		inputForm.setStationCd("22630");
		housingRequestId = housingRequestManage.addRequest("UID00008", inputForm);

		searchForm = requestSearchFormFactory.createRequestSearchForm();
		searchForm.setHousingRequestId(housingRequestId);
		cnt = housingRequestManage.searchHousing("UID00008", searchForm);
		Assert.assertEquals("�������N�G�X�g�������ʂƂȂ镨�����������������ƁB", 1, cnt);
		housingInfo = (HousingInfo) searchForm.getRows().get(0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("�������N�G�X�g�������ʂƂȂ镨�������������ƁB", "SHOU00004", housingInfo.getSysHousingCd() );

		// �������CD
		inputForm = new HousingRequestForm();
		inputForm.setRequestName("���N�G�X�g����");
		inputForm.setHousingKindCd("001");
		housingRequestId = housingRequestManage.addRequest("UID00009", inputForm);

		searchForm = requestSearchFormFactory.createRequestSearchForm();
		searchForm.setHousingRequestId(housingRequestId);
		cnt = housingRequestManage.searchHousing("UID00009", searchForm);
		Assert.assertEquals("�������N�G�X�g�������ʂƂȂ镨�����������������ƁB", 1, cnt);
		housingInfo = (HousingInfo) searchForm.getRows().get(0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("�������N�G�X�g�������ʂƂȂ镨�������������ƁB", "SHOU00001", housingInfo.getSysHousingCd() );

		// �Ԏ��CD
		inputForm = new HousingRequestForm();
		inputForm.setRequestName("���N�G�X�g����");
		inputForm.setLayoutCd("001");
		housingRequestId = housingRequestManage.addRequest("UID00010", inputForm);

		searchForm = requestSearchFormFactory.createRequestSearchForm();
		searchForm.setHousingRequestId(housingRequestId);
		cnt = housingRequestManage.searchHousing("UID00010", searchForm);
		Assert.assertEquals("�������N�G�X�g�������ʂƂȂ镨�����������������ƁB", 1, cnt);
		housingInfo = (HousingInfo) searchForm.getRows().get(0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("�������N�G�X�g�������ʂƂȂ镨�������������ƁB", "SHOU00001", housingInfo.getSysHousingCd() );

		// ����������CD
		inputForm = new HousingRequestForm();
		inputForm.setRequestName("���N�G�X�g����");
		inputForm.setPartSrchCd("FRE");
		housingRequestId = housingRequestManage.addRequest("UID00011", inputForm);

		searchForm = requestSearchFormFactory.createRequestSearchForm();
		searchForm.setHousingRequestId(housingRequestId);
		cnt = housingRequestManage.searchHousing("UID00011", searchForm);
		Assert.assertEquals("�������N�G�X�g�������ʂƂȂ镨�����������������ƁB", 1, cnt);
		housingInfo = (HousingInfo) searchForm.getRows().get(0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("�������N�G�X�g�������ʂƂȂ镨�������������ƁB", "SHOU00002", housingInfo.getSysHousingCd() );

	}

	/**
	 * ���������p�̃f�[�^���쐬
	 */
	private void makeTestHousingData() {

		BuildingInfo buildingInfo;
		HousingInfo housingInfo;
		HousingPartInfo housingPartInfo;
		BuildingStationInfo buildingStationInfo;

		Calendar calendar = Calendar.getInstance();
		calendar.clear();

		// ------ �擾�Ώۃf�[�^ ------

		// ------ �y001�z
		// ������{���
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00001");
		buildingInfo.setBuildingCd("BLD00001");
		buildingInfo.setDisplayBuildingName("�������O�P");
		buildingInfo.setHousingKindCd("001");
		buildingInfo.setPrefCd("01");
		buildingInfo.setAddressCd("01101");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// ������{���
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00001");
		housingInfo.setHousingCd("HOU00001");
		housingInfo.setDisplayHousingName("�������O�P");
		housingInfo.setSysBuildingCd("SBLD00001");
		housingInfo.setPrice(100000L);
		housingInfo.setLayoutCd("001");
		housingInfo.setPersonalArea(new BigDecimal("60"));
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// �����Ŋ�w���
		buildingStationInfo = new BuildingStationInfo();
		buildingStationInfo.setSysBuildingCd("SBLD00001");
		buildingStationInfo.setDivNo(000);
		buildingStationInfo.setDefaultRouteCd("P116001");
		buildingStationInfo.setStationCd("22495");
		this.buildingStationInfoDAO.insert(new BuildingStationInfo[]{buildingStationInfo});

		// �����������������
		housingPartInfo = new HousingPartInfo();
		housingPartInfo.setSysHousingCd("SHOU00001");
		housingPartInfo.setPartSrchCd("REI");
		this.housingPartInfoDAO.insert(new HousingPartInfo[] { housingPartInfo });

		// ------ �y002�z

		// ������{���
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00002");
		buildingInfo.setBuildingCd("BLD00002");
		buildingInfo.setDisplayBuildingName("�������O�Q");
		buildingInfo.setHousingKindCd("002");
		buildingInfo.setPrefCd("02");
		buildingInfo.setAddressCd("02101");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// ������{���
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00002");
		housingInfo.setHousingCd("HOU00002");
		housingInfo.setDisplayHousingName("�������O�Q");
		housingInfo.setSysBuildingCd("SBLD00002");
		housingInfo.setPrice(200000L);
		housingInfo.setLayoutCd("002");
		housingInfo.setPersonalArea(new BigDecimal("70"));
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// �����Ŋ�w���
		buildingStationInfo = new BuildingStationInfo();
		buildingStationInfo.setSysBuildingCd("SBLD00002");
		buildingStationInfo.setDivNo(000);
		buildingStationInfo.setDefaultRouteCd("P116002");
		buildingStationInfo.setStationCd("22513");
		this.buildingStationInfoDAO.insert(new BuildingStationInfo[]{buildingStationInfo});

		// �����������������
		housingPartInfo = new HousingPartInfo();
		housingPartInfo.setSysHousingCd("SHOU00002");
		housingPartInfo.setPartSrchCd("FRE");
		this.housingPartInfoDAO.insert(new HousingPartInfo[] { housingPartInfo });

		// ------ �y003�z

		// ������{���
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00003");
		buildingInfo.setBuildingCd("BLD00003");
		buildingInfo.setDisplayBuildingName("�������O�R");
		buildingInfo.setHousingKindCd("003");
		buildingInfo.setPrefCd("03");
		buildingInfo.setAddressCd("03101");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// ������{���
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00003");
		housingInfo.setHousingCd("HOU00003");
		housingInfo.setDisplayHousingName("�������O�R");
		housingInfo.setSysBuildingCd("SBLD00003");
		housingInfo.setPrice(300000L);
		housingInfo.setLayoutCd("003");
		housingInfo.setPersonalArea(new BigDecimal("80"));
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// �����Ŋ�w���
		buildingStationInfo = new BuildingStationInfo();
		buildingStationInfo.setSysBuildingCd("SBLD00003");
		buildingStationInfo.setDivNo(000);
		buildingStationInfo.setDefaultRouteCd("P116003");
		buildingStationInfo.setStationCd("22850");
		this.buildingStationInfoDAO.insert(new BuildingStationInfo[]{buildingStationInfo});

		// �����������������
		housingPartInfo = new HousingPartInfo();
		housingPartInfo.setSysHousingCd("SHOU00003");
		housingPartInfo.setPartSrchCd("B05");
		this.housingPartInfoDAO.insert(new HousingPartInfo[] { housingPartInfo });

		// ------ �y004�z

		// ������{���
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00004");
		buildingInfo.setBuildingCd("BLD00004");
		buildingInfo.setDisplayBuildingName("�������O�S");
		buildingInfo.setHousingKindCd("004");
		buildingInfo.setPrefCd("04");
		buildingInfo.setAddressCd("04101");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// ������{���
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00004");
		housingInfo.setHousingCd("HOU00004");
		housingInfo.setDisplayHousingName("�������O�S");
		housingInfo.setSysBuildingCd("SBLD00004");
		housingInfo.setPrice(400000L);
		housingInfo.setLayoutCd("004");
		housingInfo.setPersonalArea(new BigDecimal("90"));
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// �����Ŋ�w���
		buildingStationInfo = new BuildingStationInfo();
		buildingStationInfo.setSysBuildingCd("SBLD00004");
		buildingStationInfo.setDivNo(000);
		buildingStationInfo.setDefaultRouteCd("P116004");
		buildingStationInfo.setStationCd("22630");
		this.buildingStationInfoDAO.insert(new BuildingStationInfo[]{buildingStationInfo});

		// �����������������
		housingPartInfo = new HousingPartInfo();
		housingPartInfo.setSysHousingCd("SHOU00004");
		housingPartInfo.setPartSrchCd("S05");
		this.housingPartInfoDAO.insert(new HousingPartInfo[] { housingPartInfo });

		// ------ �y005�z

		// ������{���
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00005");
		buildingInfo.setBuildingCd("BLD00005");
		buildingInfo.setDisplayBuildingName("�������O�T");
		buildingInfo.setHousingKindCd("005");
		buildingInfo.setPrefCd("05");
		buildingInfo.setAddressCd("05101");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// ������{���
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00005");
		housingInfo.setHousingCd("HOU00005");
		housingInfo.setDisplayHousingName("�������O�P");
		housingInfo.setSysBuildingCd("SBLD00005");
		housingInfo.setPrice(500000L);
		housingInfo.setLayoutCd("005");
		housingInfo.setPersonalArea(new BigDecimal("100"));
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// �����Ŋ�w���
		buildingStationInfo = new BuildingStationInfo();
		buildingStationInfo.setSysBuildingCd("SBLD00005");
		buildingStationInfo.setDivNo(000);
		buildingStationInfo.setDefaultRouteCd("P116005");
		buildingStationInfo.setStationCd("22849");
		this.buildingStationInfoDAO.insert(new BuildingStationInfo[]{buildingStationInfo});

		// �����������������
		housingPartInfo = new HousingPartInfo();
		housingPartInfo.setSysHousingCd("SHOU00005");
		housingPartInfo.setPartSrchCd("S10");
		this.housingPartInfoDAO.insert(new HousingPartInfo[] { housingPartInfo });

	}

	/**
	 * �������N�G�X�g����̕�����������<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�y�[�W���O�֘A�̃v���p�e�B�l�ɍ��v����������񂪐������擾����鎖</li>
	 * </ul>
	 */
	@Test
	public void searchHousingPagingTest() throws Exception {

		makeTestHousingData();

		HousingRequestForm inputForm;
		String housingRequestId;

		RequestSearchForm searchForm;
		int cnt = 0;
		HousingInfo housingInfo;

		int actualRow =0;

		inputForm = new HousingRequestForm();
		inputForm.setRequestName("���N�G�X�g����");
		housingRequestId = housingRequestManage.addRequest("UID00001", inputForm);

		searchForm = requestSearchFormFactory.createRequestSearchForm();
		searchForm.setRowsPerPage(2);
		searchForm.setSelectedPage(1);
		searchForm.setHousingRequestId(housingRequestId);

		// 1�y�[�W��
		cnt = housingRequestManage.searchHousing("UID00001", searchForm);
		Assert.assertEquals("�������N�G�X�g�������ʂƂȂ镨�����������������ƁB", 5, cnt);
		// ���Ԃ̃T�C�Y���擾����
		actualRow = 0;
		for (Housing housing : searchForm.getRows()) {
			actualRow++;
		}
		int pageRowIndex = searchForm.getRowsPerPage() * searchForm.getSelectedPage() - searchForm.getRowsPerPage();
		Assert.assertEquals("�y�[�W���O�֘A�̃v���p�e�B�l�ɍ��v����������񂪐������擾����鎖", 2, actualRow);
		housingInfo = (HousingInfo) searchForm.getRows().get(pageRowIndex + 0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("�y�[�W���O�֘A�̃v���p�e�B�l�ɍ��v����������񂪐������擾����鎖", "SHOU00001", housingInfo.getSysHousingCd() );

		searchForm = requestSearchFormFactory.createRequestSearchForm();
		searchForm.setRowsPerPage(2);
		searchForm.setSelectedPage(2);
		searchForm.setHousingRequestId(housingRequestId);

		// 2�y�[�W��
		cnt = housingRequestManage.searchHousing("UID00001", searchForm);
		Assert.assertEquals("�������N�G�X�g�������ʂƂȂ镨�����������������ƁB", 5, cnt);
		// ���Ԃ̃T�C�Y���擾����
		actualRow = 0;
		for (Housing housing : searchForm.getRows()) {
			actualRow++;
		}
		pageRowIndex = searchForm.getRowsPerPage() * searchForm.getSelectedPage() - searchForm.getRowsPerPage();
		Assert.assertEquals("�y�[�W���O�֘A�̃v���p�e�B�l�ɍ��v����������񂪐������擾����鎖", 2, actualRow);
		housingInfo = (HousingInfo) searchForm.getRows().get(pageRowIndex + 0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("�y�[�W���O�֘A�̃v���p�e�B�l�ɍ��v����������񂪐������擾����鎖", "SHOU00003", housingInfo.getSysHousingCd() );


	}

}
