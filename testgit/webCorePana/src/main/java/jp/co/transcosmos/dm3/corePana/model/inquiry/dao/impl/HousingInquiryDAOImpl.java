package jp.co.transcosmos.dm3.corePana.model.inquiry.dao.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import jp.co.transcosmos.dm3.core.model.InquiryManage;
import jp.co.transcosmos.dm3.core.model.building.Building;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.inquiry.HousingInquiry;
import jp.co.transcosmos.dm3.core.model.inquiry.InquiryHeaderInfo;
import jp.co.transcosmos.dm3.core.vo.AdminLoginInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.InquiryDtlInfo;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.model.housing.dao.impl.PanaSearchHousingDAOImpl;
import jp.co.transcosmos.dm3.corePana.model.inquiry.AssessmentInquiryManageImpl;
import jp.co.transcosmos.dm3.corePana.model.inquiry.GeneralInquiryManageImpl;
import jp.co.transcosmos.dm3.corePana.model.inquiry.InquiryInfo;
import jp.co.transcosmos.dm3.corePana.model.inquiry.PanaHousingInquiryManageImpl;
import jp.co.transcosmos.dm3.corePana.model.inquiry.dao.HousingInquiryDAO;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquirySearchForm;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.InquiryAssessment;
import jp.co.transcosmos.dm3.corePana.vo.InquiryGeneral;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHeader;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHousing;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.FragmentResultSetExtractor;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

/**
 * �����⍇�� model �̎����N���X.
 * <p>
 * <pre>
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �s�����C		2015.04.02	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class HousingInquiryDAOImpl implements HousingInquiryDAO {

	/** �⍇���w�b�_���pDAO */
	private DAO<AdminLoginInfo> adminLoginInfoDAO;

	// �e�[�u��������
	/** ������{���e�[�u���� */
	protected static final String TABLE_HOUSING_INFO_NAME = "housing_info";

	// �G�C���A�X
	/** ������{���G�C���A�X */
	protected static final String TABLE_HOUSING_INFO_ALIAS = "housingInfo";

	private static final String _CHARACTER_ENCODING = "UTF-8";

	private static final String _CONTENT_TYPE = "application/octet-stream";

	private static final String _HEADER1 = "Content-Disposition";

	private static final String _HEADER2 = "attachment ;filename=";

	// log
	private static final Log log = LogFactory.getLog(HousingInquiryDAOImpl.class);


	/** �f�[�^�\�[�X */
	private DataSource dataSource;

	/** ���ʃR�[�h�ϊ����� */
	private CodeLookupManager codeLookupManager;

	// �ȉ��AapplicationContext�ɒ�`
	//
	// OR�����Ō���������������������Map�ŕێ�����
	// �@key�F����������CD�A�����L�[�̑��ݗL�����������������ƂȂ�
	// �@value�F���������Ɏg�p����l��ێ��ł���
	// ������OR�����Ώۂ�List�ŕێ�����

	/**
	 * @param adminLoginInfoDAO �Z�b�g���� adminLoginInfoDAO
	 */
	public void setAdminLoginInfoDAO(DAO<AdminLoginInfo> adminLoginInfoDAO) {
		this.adminLoginInfoDAO = adminLoginInfoDAO;
	}


	/** OR�����Ō������邱��������CD�̃O���[�s���O��` */
	private List<Map<String, String>> orPartSrchList =  new ArrayList<Map<String, String>>();

	/**
     * �f�[�^�\�[�X��ݒ肷��B<br/>
     * <br/>
     * @param dataSource �f�[�^�\�[�X
     */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
     * OR�����Ō������邱��������CD�̃O���[�s���O��`��ݒ肷��B<br/>
     * <br/>
     * @param orPartSrchList OR�����Ō������邱��������CD�̃O���[�s���O��`
     */
	public void setOrPartSrchList(List<Map<String, String>> orPartSrchList) {
		this.orPartSrchList = orPartSrchList;
	}

	/**
     * ���ʃR�[�h�ϊ�������ݒ肷��B<br/>
     * <br/>
     * @param codeLookupManager ���ʃR�[�h�ϊ�����
     */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * �⍇�ꗗ�����������A���ʃ��X�g�𕜋A����B�i�ꗗ�p�j<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A����������������B<br/>
	 * �������ʂ� List<Inquiry> �I�u�W�F�N�g�Ɋi�[����A�擾����List<Inquiry>��߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 *
	 * @param searchForm
	 *            ���������̊i�[�I�u�W�F�N�g
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JoinResult> housingInquirySearch(PanaInquirySearchForm searchForm) {
		JdbcTemplate template = new JdbcTemplate(dataSource);

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		// ����SQL�𐶐�
		createSql(searchForm, sql, params);

		log.debug("SQL : " + sql.toString());
		System.out.println("SQL : " + sql.toString());
		List<JoinResult> housingInquiry = (List<JoinResult>) template.query(sql.toString(), (Object[]) params.toArray(),
				new FragmentResultSetExtractor<Object>(new InquiryRowMapper(), searchForm
								.getStartIndex(), searchForm.getEndIndex()));
		return housingInquiry;
	}

	class InquiryRowMapper implements RowMapper<JoinResult> {
		@Override
		public JoinResult mapRow(ResultSet result, int rowNum)
				throws SQLException {
			Map<String, Object> inquiry = new HashMap<String, Object>();
			inquiry.put("inquiryId",result.getString("inquiryHeader_inquiryId"));
			inquiry.put("inquiryType",result.getString("inquiryHeader_inquiryType"));
			inquiry.put("displayHousingName",result.getString("housingInfo_displayHousingName"));
			inquiry.put("inquiryDate",result.getTimestamp("inquiryHeader_inquiryDate"));
			inquiry.put("answerStatus",result.getString("inquiryHeader_answerStatus"));

			Map<String, Object> items = new HashMap<String, Object>();
			items.put("inquiry",inquiry);

			JoinResult joinResult = new JoinResult(items);
			return joinResult;
		}
	}

	/**
	 * �⍇�ꗗ�����������A���ʃ��X�g�𕜋A����B�i�ꗗ�p�j<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A����������������B<br/>
	 * �������ʂ� List<Housing> �I�u�W�F�N�g�Ɋi�[����A�擾����List<Housing>��߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 *
	 * @param searchForm
	 *            ���������̊i�[�I�u�W�F�N�g
	 * @param response
	 *            �N���C�A���g�ɕԂ�Http���X�|���X�B
	 * @param inquiryManage
	 *            �����⍇�� model
	 * @param generalInquiryManage
	 *            �ėp�⍇�� model
	 * @param assessmentInquiryManage
	 *            ����⍇�� model
	 * @throws IOException
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public void housingInquirySearch(PanaInquirySearchForm searchForm,
			HttpServletResponse response,InquiryManage inquiryManage,InquiryManage generalInquiryManage,
			InquiryManage assessmentInquiryManage) throws IOException {
		JdbcTemplate template = new JdbcTemplate(dataSource);

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		// ����SQL�𐶐�
		createSql(searchForm, sql, params);

		log.debug("SQL : " + sql.toString());

		// �t�@�C�����̐ݒ�
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dataTime = sdf.format(new Date());
		String fileName = "toiawase_" + dataTime + ".csv";

		response.setCharacterEncoding(_CHARACTER_ENCODING);
		response.setContentType(_CONTENT_TYPE);
		response.setHeader(_HEADER1, _HEADER2 + fileName);

		PrintWriter pw = response.getWriter();
		byte[] bom = {(byte)0xef, (byte)0xbb, (byte)0xbf};
		pw.write(new String(bom));

		this.writeCSVHeader(pw);

		template.query(sql.toString(), params.toArray(), new InquiryListRowCallbackHandler(response, inquiryManage, generalInquiryManage, assessmentInquiryManage, this.codeLookupManager, this.adminLoginInfoDAO, pw));
	}


	/**
	 * CSV��header�f�[�^�𐶐�����B<br/>
	 *
	 * @param pw
	 */
	private void writeCSVHeader(PrintWriter pw) {
		pw.write("�⍇�ԍ�" + ",");
		pw.write("�₢���킹���" + ",");
		pw.write("����ԍ�" + ",");
		pw.write("�����O(��)" + ",");
		pw.write("�����O�i���j" + ",");
		pw.write("�t���K�i(�Z�C)" + ",");
		pw.write("�t���K�i�i���C�j" + ",");
		pw.write("���[���A�h���X" + ",");
		pw.write("�d�b�ԍ�" + ",");
		pw.write("FAX�ԍ�" + ",");
		pw.write("���⍇������" + ",");
		pw.write("���⍇�����" + ",");
		pw.write("���⍇�����e" + ",");
		pw.write("�Z�~�i�[��" + ",");
		pw.write("����" + ",");
		pw.write("�X�e�[�^�X" + ",");
		pw.write("�X�֔ԍ�" + ",");
		pw.write("�Z��1" + ",");
		pw.write("�Z��2" + ",");
		pw.write("����]�̘A�����@" + ",");
		pw.write("�A���\�Ȏ��ԑ�" + ",");
		pw.write("�����ԍ�" + ",");
		pw.write("������" + ",");
		pw.write("�������" + ",");
		pw.write("�������i" + ",");
		pw.write("�z�N��" + ",");
		pw.write("�Ԏ�" + ",");
		pw.write("�X�֔ԍ��i�����j" + ",");
		pw.write("�Z��1(����) " + ",");
		pw.write("�Z��2�i�����j" + ",");
		pw.write("����1" + ",");
		pw.write("�w1" + ",");
		pw.write("�o�X1" + ",");
		pw.write("�k��1" + ",");
		pw.write("����2" + ",");
		pw.write("�w2" + ",");
		pw.write("�o�X2" + ",");
		pw.write("�k��2" + ",");
		pw.write("����3" + ",");
		pw.write("�w3" + ",");
		pw.write("�o�X3" + ",");
		pw.write("�k��3" + ",");
		pw.write("�����ʐρi�����j" + ",");
		pw.write("�y�n�ʐρi�����j" + ",");
		pw.write("��L�ʐρi�����j" + ",");
		pw.write("���p�����̎��" + ",");
		pw.write("�Ԏ�" + ",");
		pw.write("���p�����Z��1 " + ",");
		pw.write("���p�����Z��2" + ",");
		pw.write("�����ʐρi���p�j" + ",");
		pw.write("�y�n�ʐρi���p�j" + ",");
		pw.write("��L�ʐρi���p�j" + ",");
		pw.write("�z�N��" + ",");
		pw.write("����" + ",");
		pw.write("���p�\�莞��" + ",");
		pw.write("�����ւ��L��" + ",");
		pw.write("���l" + ",");
		pw.write("�ŏI�X�V��" + ",");
		pw.write("�ŏI�X�V��");
		pw.write("\r\n");
	}

	class InquiryListRowCallbackHandler implements RowCallbackHandler {
		HttpServletResponse response;
		PanaHousingInquiryManageImpl housingInquiryManage;
		GeneralInquiryManageImpl generalInquiryManage;
		AssessmentInquiryManageImpl assessmentInquiryManage;
		CodeLookupManager codeLookupManager;
		DAO<AdminLoginInfo> adminLoginInfoDAO;
		PrintWriter pw;

		public InquiryListRowCallbackHandler(HttpServletResponse response,InquiryManage housingInquiryManage,
				InquiryManage generalInquiryManage, InquiryManage assessmentInquiryManage,
				CodeLookupManager codeLookupManager, DAO<AdminLoginInfo> adminLoginInfoDAO,
				PrintWriter pw) {
			this.response = response;
			this.housingInquiryManage = (PanaHousingInquiryManageImpl)housingInquiryManage;
			this.generalInquiryManage = (GeneralInquiryManageImpl)generalInquiryManage;
			this.assessmentInquiryManage = (AssessmentInquiryManageImpl)assessmentInquiryManage;
			this.codeLookupManager = codeLookupManager;
			this.adminLoginInfoDAO = adminLoginInfoDAO;
			this.pw = pw;
		}

		@Override
		public void processRow(ResultSet rs) throws SQLException {

			try {
				PrintWriter pw = response.getWriter();
				pw = this.pw;

				this.writeCSVContent(pw, rs);
				pw.flush();

			} catch (IOException e) {
				// TODO �����������ꂽ catch �u���b�N
				e.printStackTrace();
			}

		}

		private void writeCSVContent(PrintWriter pw, ResultSet rs)
				throws SQLException {
			// �⍇�����
			InquiryInfo inquiryInfo= new InquiryInfo();
			// �����⍇�����擾
			HousingInquiry housingInquiry = new HousingInquiry();
			// �����⍇�����
			InquiryHousing inquiryHousing = new InquiryHousing();
			// �ėp�⍇�����
			InquiryInfo generalinquiryInfo = new InquiryInfo();
			// ����⍇�����
			InquiryInfo assessmentinquiryInfo = new InquiryInfo();
			PrefMst inquiryInfoPrefMst = new PrefMst();

			try {
				//if(PanaCommonConstant.INQUIRY_TYPE_HOUSING.equals(rs.getString("inquiryHeader_inquiryType"))){
				inquiryInfo = this.housingInquiryManage.searchHousingInquiryPk(rs.getString("inquiryHeader_inquiryId"));
				//}

				if(PanaCommonConstant.INQUIRY_TYPE_GENERAL.equals(rs.getString("inquiryHeader_inquiryType"))) {
					generalinquiryInfo = this.generalInquiryManage.searchInquiryPk(rs.getString("inquiryHeader_inquiryId"));
				}

				if(PanaCommonConstant.INQUIRY_TYPE_ASSESSMENT.equals(rs.getString("inquiryHeader_inquiryType"))) {
					assessmentinquiryInfo = this.assessmentInquiryManage.searchInquiryPk(rs.getString("inquiryHeader_inquiryId"));
				}
			} catch (Exception e) {
				// TODO �����������ꂽ catch �u���b�N
				e.printStackTrace();
			}


			housingInquiry = (HousingInquiry)inquiryInfo;

			// �����⍇�����
			if(null != inquiryInfo.getInquiryHousing()) {
				inquiryHousing = inquiryInfo.getInquiryHousing();
			}
			if(null != inquiryInfo.getPrefMst()) {
				inquiryInfoPrefMst = inquiryInfo.getPrefMst();
			}

			// ������{��� �i������{���{�����ڍ׏��{�����X�e�[�^�X���j
			Housing housings = null;
			if (housingInquiry.getHousings().size() > 0) {
				housings = housingInquiry.getHousings().get(0);
			}

			JoinResult housing = null;
			if(null != housings) {
				housing = housings.getHousingInfo();
			}

			// ������{���
			HousingInfo housingInfo = new HousingInfo();
			if(null != housing) {
				housingInfo = (HousingInfo)housing.getItems().get("housingInfo");
			}

			// ������{���
			Building building = null;
			JoinResult builds = null;
			BuildingInfo buildingInfo = new BuildingInfo();
			if(null != housings) {
				building =  housings.getBuilding();
				builds = building.getBuildingInfo();
				buildingInfo = (BuildingInfo)builds.getItems().get("buildingInfo");
			}

			// �����ڍ׏��
			BuildingDtlInfo buildingDtlInfo = new BuildingDtlInfo();
			if(null != builds) {
				buildingDtlInfo = (BuildingDtlInfo)builds.getItems().get("buildingDtlInfo");
			}

			// �s���{���}�X�^�N���X
			PrefMst prefMst = new PrefMst();
			if(null != builds) {
				prefMst = (PrefMst)builds.getItems().get("prefMst");
			}

			// �����Ŋ��w���N���X
			BuildingStationInfo buildingStationInfo1 = new BuildingStationInfo();
			BuildingStationInfo buildingStationInfo2 = new BuildingStationInfo();
			BuildingStationInfo buildingStationInfo3 = new BuildingStationInfo();
			// �H���}�X�^�N���X
			RouteMst routeMst1 = new RouteMst();
			RouteMst routeMst2 = new RouteMst();
			RouteMst routeMst3 = new RouteMst();
			// �w���}�X�^�N���X
			StationMst stationMst1 = new StationMst();
			StationMst stationMst2 = new StationMst();
			StationMst stationMst3 = new StationMst();
			// �����Ŋ��w���
			List<JoinResult> buildingStationInfoList = null;
			if(null != building) {
				buildingStationInfoList = building.getBuildingStationInfoList();
				for(int i = 0; i < buildingStationInfoList.size(); i++) {
					if(i == 0) {
						buildingStationInfo1 = (BuildingStationInfo)buildingStationInfoList.get(i).getItems().get("buildingStationInfo");
						routeMst1 = (RouteMst)buildingStationInfoList.get(i).getItems().get("routeMst");
						stationMst1 = (StationMst)buildingStationInfoList.get(i).getItems().get("stationMst");
					}
					if(i == 1) {
						buildingStationInfo2 = (BuildingStationInfo)buildingStationInfoList.get(i).getItems().get("buildingStationInfo");
						routeMst2 = (RouteMst)buildingStationInfoList.get(i).getItems().get("routeMst");
						stationMst2 = (StationMst)buildingStationInfoList.get(i).getItems().get("stationMst");
					}
					if(i == 2) {
						buildingStationInfo3 = (BuildingStationInfo)buildingStationInfoList.get(i).getItems().get("buildingStationInfo");
						routeMst3 = (RouteMst)buildingStationInfoList.get(i).getItems().get("routeMst");
						stationMst3 = (StationMst)buildingStationInfoList.get(i).getItems().get("stationMst");
					}
				}
			}

			// �����⍇�����
			InquiryHeaderInfo inquiryHeaderInfo = housingInquiry.getInquiryHeaderInfo();
			// ���⍇���w�b�_
			InquiryHeader inquiryHeader = (InquiryHeader)inquiryHeaderInfo.getInquiryHeader();
			// �⍇�����e��ʏ��
			InquiryDtlInfo[] inquiryDtlInfoArr = inquiryHeaderInfo.getInquiryDtlInfos();
			InquiryDtlInfo inquiryDtlInfo = new InquiryDtlInfo();

			if(null != inquiryDtlInfoArr) {
				if(inquiryDtlInfoArr.length > 0) {
					inquiryDtlInfo = inquiryDtlInfoArr[0];
				}
			}

			// ����⍇�����
			InquiryAssessment inquiryAssessment = new InquiryAssessment();
			PrefMst assessmenHeadertPrefMst = new PrefMst();
			PrefMst assessmenPrefMst = new PrefMst();
			if(PanaCommonConstant.INQUIRY_TYPE_ASSESSMENT.equals(rs.getString("inquiryHeader_inquiryType"))) {
				List<JoinResult> jrAssessmentArr = assessmentinquiryInfo.getAssessmentInquiry();
				JoinResult jrAssessment = jrAssessmentArr.get(0);
				inquiryAssessment = (InquiryAssessment)jrAssessment.getItems().get("inquiryAssessment");
				// ���⍇���w�b�_�̓s���{��
				assessmenHeadertPrefMst = (PrefMst)jrAssessment.getItems().get("PrefMst");
				// ����⍇�����̓s���{��
				assessmenPrefMst = assessmentinquiryInfo.getPrefMst();

			}

			// �ėp�⍇�����
			InquiryGeneral inquiryGeneral = new InquiryGeneral();
			if(PanaCommonConstant.INQUIRY_TYPE_GENERAL.equals(rs.getString("inquiryHeader_inquiryType"))) {
				JoinResult jrGeneral = generalinquiryInfo.getGeneralInquiry().get(0);
				inquiryGeneral = (InquiryGeneral)jrGeneral.getItems().get("inquiryGeneral");
			}

			// CSV�o�͂̍��ڂ�ݒ肷��B
			// �⍇�ԍ�
			pw.write("\"" + PanaStringUtils.toString(inquiryHeader.getInquiryId()) + "\",");
			// �₢���킹���
			pw.write("\"" + PanaStringUtils.toString(lookupValueWithSingle("inquiry_type", inquiryHeader.getInquiryType())) + "\",");
			// ����ԍ�
			pw.write("\"" + PanaStringUtils.toString(inquiryHeader.getUserId()) + "\",");
			// �����O(��)
			pw.write("\"" + PanaStringUtils.toString(inquiryHeader.getLname()) + "\",");
			// �����O�i���j
			pw.write("\"" + PanaStringUtils.toString(inquiryHeader.getFname()) + "\",");
			// �t���K�i(�Z�C)
			pw.write("\"" + PanaStringUtils.toString(inquiryHeader.getLnameKana()) + "\",");
			// �t���K�i�i���C�j
			pw.write("\"" + PanaStringUtils.toString(inquiryHeader.getFnameKana()) + "\",");
			// ���[���A�h���X
			pw.write("\"" + PanaStringUtils.toString(inquiryHeader.getEmail()) + "\",");
			// �d�b�ԍ�
			pw.write("\"" + PanaStringUtils.toString(inquiryHeader.getTel()) + "\",");
			// FAX�ԍ�
			pw.write("\"" + PanaStringUtils.toString(inquiryHeader.getFax()) + "\",");
			// ���⍇������
			pw.write("\"" + PanaStringUtils.toString(formatDate(inquiryHeader.getInquiryDate(), "yyyy/MM/dd HH:mm:ss")) + "\",");
			// ���⍇�����
			//�ėp�⍇��
			if (PanaCommonConstant.INQUIRY_TYPE_GENERAL.equals(inquiryHeader
					.getInquiryType())) {
				pw.write("\""
						+ PanaStringUtils.toString(lookupValueWithSingle(
								"inquiry_dtl_type",
								inquiryDtlInfo.getInquiryDtlType())) + "\",");
			} else if (PanaCommonConstant.INQUIRY_TYPE_HOUSING
					.equals(inquiryHeader.getInquiryType())) {
				//�����⍇��
				pw.write("\""
						+ PanaStringUtils.toString(lookupValueWithSingle(
								"inquiry_housing_dtl_type",
								inquiryDtlInfo.getInquiryDtlType())) + "\",");
			}
			else {
				pw.write("\"\",");
			}
			// ���⍇�����e
			String inquiryText = PanaStringUtils.toString(inquiryHeader.getInquiryText());
			//if(PanaCommonConstant.INQUIRY_TYPE_ASSESSMENT.equals(rs.getString("inquiryHeader_inquiryType"))) {
			//	inquiryText = "";
			//}
			pw.write("\"" + inquiryText + "\",");
			// �Z�~�i�[��
			pw.write("\"" + PanaStringUtils.toString(inquiryGeneral.getEventName()) + "\",");
			// ����
			pw.write("\"" + PanaStringUtils.toString(formatDate(inquiryGeneral.getEventDatetime(), "M��d�� H:mm")) + "\",");
			// �X�e�[�^�X
			pw.write("\"" + PanaStringUtils.toString(lookupValueWithSingle("inquiry_answerStatus", inquiryHeader.getAnswerStatus())) + "\",");

			if(PanaCommonConstant.INQUIRY_TYPE_GENERAL.equals(rs.getString("inquiryHeader_inquiryType"))) {
				pw.write("\"" + "\",");
				pw.write("\"" + "\",");
				pw.write("\"" + "\",");
			} else {
				// �X�֔ԍ�
				pw.write("\"" + PanaStringUtils.toString(inquiryHeader.getZip()) + "\",");
				// �Z��1
				if(PanaCommonConstant.INQUIRY_TYPE_ASSESSMENT.equals(rs.getString("inquiryHeader_inquiryType")))
				{
					pw.write("\"" + PanaStringUtils.toString(assessmenHeadertPrefMst.getPrefName()) + "\",");
				}
				else {
					pw.write("\"" + PanaStringUtils.toString(inquiryInfoPrefMst.getPrefName()) + "\",");
				}
				// �Z��2
				pw.write("\"" + PanaStringUtils.toString(inquiryHeader.getAddress()) + PanaStringUtils.toString(inquiryHeader.getAddressOther()) + "\",");
			}
			// ����]�̘A�����@
			if(PanaCommonConstant.INQUIRY_TYPE_GENERAL.equals(rs.getString("inquiryHeader_inquiryType"))) {
				pw.write("\"" + PanaStringUtils.toString(lookupValueWithMulti("inquiry_contact_type", inquiryGeneral.getContactType())) + "\",");
			} else if(PanaCommonConstant.INQUIRY_TYPE_ASSESSMENT.equals(rs.getString("inquiryHeader_inquiryType"))) {
				pw.write("\"" + PanaStringUtils.toString(lookupValueWithMulti("inquiry_contact_type", inquiryAssessment.getContactType())) + "\",");
			} else {
				pw.write("\"" + PanaStringUtils.toString(lookupValueWithMulti("inquiry_contact_type", inquiryHousing.getContactType())) + "\",");
			}

			// �A���\�Ȏ��ԑ�
			if(PanaCommonConstant.INQUIRY_TYPE_GENERAL.equals(rs.getString("inquiryHeader_inquiryType"))) {
				pw.write("\"" + PanaStringUtils.toString(lookupValueWithMulti("inquiry_contact_time", inquiryGeneral.getContactTime())) + "\",");
			} else if(PanaCommonConstant.INQUIRY_TYPE_ASSESSMENT.equals(rs.getString("inquiryHeader_inquiryType"))) {
				pw.write("\"" + PanaStringUtils.toString(lookupValueWithMulti("inquiry_contact_time", inquiryAssessment.getContactTime())) + "\",");
			} else {
				pw.write("\"" + PanaStringUtils.toString(lookupValueWithMulti("inquiry_contact_time", inquiryHousing.getContactTime())) + "\",");
			}

			// �����ԍ�
			pw.write("\"" + PanaStringUtils.toString(housingInfo.getHousingCd()) + "\",");
			// ������
			pw.write("\"" + PanaStringUtils.toString(housingInfo.getDisplayHousingName()) + "\",");
			// �������
			pw.write("\"" + PanaStringUtils.toString(lookupValueWithSingle("buildingInfo_housingKindCd", buildingInfo.getHousingKindCd())) + "\",");
			// �������i
			DecimalFormat df = new DecimalFormat("###,###");
			String price = "";
			if(!StringUtils.isEmpty(housingInfo.getPrice())) {
				price = df.format(housingInfo.getPrice());
			}
			pw.write("\"" + price + "\",");
			// �z�N��
			pw.write("\"" + PanaStringUtils.toString(formatDate(buildingInfo.getCompDate(), "yyyy�NMM��")) + "\",");
			// �Ԏ�
			pw.write("\"" + PanaStringUtils.toString(lookupValueWithSingle("layoutCd", housingInfo.getLayoutCd())) + "\",");
			// �X�֔ԍ��i�����j
			pw.write("\"" + PanaStringUtils.toString(buildingInfo.getZip()) + "\",");
			// �Z��1(����)
			pw.write("\"" + PanaStringUtils.toString(prefMst.getPrefName()) + "\",");
			// �Z��2�i�����j
			String addressName = PanaStringUtils.toString(buildingInfo.getAddressName()) + PanaStringUtils.toString(buildingInfo.getAddressOther1())
					+ PanaStringUtils.toString(buildingInfo.getAddressOther2());
			pw.write("\"" + PanaStringUtils.toString(addressName) + "\",");
			// ����1
			if(StringValidateUtil.isEmpty(routeMst1.getRouteName())) {
				pw.write("\"" + PanaStringUtils.toString(buildingStationInfo1.getDefaultRouteName()) + "\",");
			} else {
				pw.write("\"" + PanaStringUtils.toString(routeMst1.getRouteName()) + "\",");
			}
			// �w1
			if(StringValidateUtil.isEmpty(stationMst1.getStationName())) {
				pw.write("\"" + PanaStringUtils.toString(buildingStationInfo1.getStationName()) + "\",");
			} else {
				pw.write("\"" + PanaStringUtils.toString(stationMst1.getStationName()) + "\",");
			}
			// �o�X1
			pw.write("\"" + PanaStringUtils.toString(buildingStationInfo1.getBusCompany()) + "\",");
			// �k��1
			pw.write("\"" + PanaStringUtils.toString(buildingStationInfo1.getTimeFromBusStop()) + "\",");
			// ����2
			if(StringValidateUtil.isEmpty(routeMst2.getRouteName())) {
				pw.write("\"" + PanaStringUtils.toString(buildingStationInfo2.getDefaultRouteName()) + "\",");
			} else {
				pw.write("\"" + PanaStringUtils.toString(routeMst2.getRouteName()) + "\",");
			}
			// �w2
			if(StringValidateUtil.isEmpty(stationMst2.getStationName())) {
				pw.write("\"" + PanaStringUtils.toString(buildingStationInfo2.getStationName()) + "\",");
			} else {
				pw.write("\"" + PanaStringUtils.toString(stationMst2.getStationName()) + "\",");
			}
			// �o�X2
			pw.write("\"" + PanaStringUtils.toString(buildingStationInfo2.getBusCompany()) + "\",");
			// �k��2
			pw.write("\"" + PanaStringUtils.toString(buildingStationInfo2.getTimeFromBusStop()) + "\",");
			// ����3
			if(StringValidateUtil.isEmpty(routeMst3.getRouteName())) {
				pw.write("\"" + PanaStringUtils.toString(buildingStationInfo3.getDefaultRouteName()) + "\",");
			} else {
				pw.write("\"" + PanaStringUtils.toString(routeMst3.getRouteName()) + "\",");
			}
			// �w3
			if(StringValidateUtil.isEmpty(stationMst3.getStationName())) {
				pw.write("\"" + PanaStringUtils.toString(buildingStationInfo3.getStationName()) + "\",");
			} else {
				pw.write("\"" + PanaStringUtils.toString(stationMst3.getStationName()) + "\",");
			}
			// �o�X3
			pw.write("\"" + PanaStringUtils.toString(buildingStationInfo3.getBusCompany()) + "\",");
			// �k��3
			pw.write("\"" + PanaStringUtils.toString(buildingStationInfo3.getTimeFromBusStop()) + "\",");
			// �����ʐρi�����j
			String buildingArea = PanaStringUtils.toString(buildingDtlInfo.getBuildingArea());
			if(!StringUtils.isEmpty(buildingArea)) {
				buildingArea = buildingArea + "�u";
			}
			pw.write("\"" + buildingArea + "\",");
			// �y�n�ʐρi�����j
			String landArea = PanaStringUtils.toString(housingInfo.getLandArea());
			if(!StringUtils.isEmpty(landArea)) {
				landArea = landArea + "�u";
			}
			pw.write("\"" + landArea + "\",");
			// ��L�ʐρi�����j
			String persoalArea = PanaStringUtils.toString(housingInfo.getPersonalArea());
			if(!StringUtils.isEmpty(persoalArea)) {
				persoalArea = persoalArea + "�u";
			}
			pw.write("\"" + persoalArea + "\",");
			// ���p�����̎��
			pw.write("\"" + PanaStringUtils.toString(lookupValueWithSingle("buildingInfo_housingKindCd", inquiryAssessment.getBuyHousingType())) + "\",");
			// �Ԏ�
			pw.write("\"" + PanaStringUtils.toString(lookupValueWithSingle("layoutCd", inquiryAssessment.getLayoutCd())) + "\",");
			// ���p�����Z��1
			pw.write("\"" + PanaStringUtils.toString(assessmenPrefMst.getPrefName()) + "\",");
			// ���p�����Z��2
			String addrName = PanaStringUtils.toString(inquiryAssessment.getAddress());
			addrName = addrName + PanaStringUtils.toString(inquiryAssessment.getAddressOther());
			pw.write("\"" + PanaStringUtils.toString(addrName) + "\",");
			// �����ʐρi���p�j
			pw.write("\"" + calcTsubo(inquiryAssessment.getBuildingArea(),inquiryAssessment.getBuildingAreaCrs()) + "\",");
			// �y�n�ʐρi���p�j
			pw.write("\"" + calcTsubo(inquiryAssessment.getLandArea(), inquiryAssessment.getLandAreaCrs()) + "\",");
			// ��L�ʐρi���p�j
			String persoalArea2 = PanaStringUtils.toString(inquiryAssessment.getPersonalArea());
			if(!StringUtils.isEmpty(persoalArea2)) {
				persoalArea2 = persoalArea2 + "�u";
			}
			pw.write("\"" + persoalArea2 + "\",");
			// �z�N��
			String buildAge = "";
			if(null != inquiryAssessment.getBuildAge()) {
				buildAge  = inquiryAssessment.getBuildAge().toString()+ "�N";
			}
			pw.write("\"" +  buildAge + "\",");
			// ����
			pw.write("\"" + PanaStringUtils.toString(lookupValueWithSingle("inquiry_present_cd", inquiryAssessment.getPresentCd())) + "\",");
			// ���p�\�莞��
			pw.write("\"" + PanaStringUtils.toString(lookupValueWithSingle("inquiry_buy_time_cd", inquiryAssessment.getBuyTimeCd())) + "\",");
			// �����ւ��L��
			pw.write("\"" + PanaStringUtils.toString(lookupValueWithSingle("replacement_flg", inquiryAssessment.getReplacementFlg())) + "\",");
			// ���l
			pw.write("\"" + PanaStringUtils.toString(inquiryHeader.getAnswerText()) + "\",");
			// �ŏI�X�V��
			pw.write("\"" + PanaStringUtils.toString(formatDate(inquiryHeader.getUpdDate(), "yyyy/MM/dd HH:mm:ss")) + "\",");
			// �ŏI�X�V��
			if (inquiryHeader.getUserId() == null) {
				pw.write("\"\"");
			} else {
				if (inquiryHeader.getUserId().equals(inquiryHeader.getUpdUserId())) {
					pw.write("\"�{�l\"");
				} else {
					AdminLoginInfo adminLoginInfo = this.adminLoginInfoDAO.selectByPK(inquiryHeader.getUpdUserId());
					if(null == adminLoginInfo) {
						adminLoginInfo = new AdminLoginInfo();
					}
					pw.write("\"" + PanaStringUtils.toString(adminLoginInfo.getUserName()) + "\"");
				}
			}
			pw.write("\r\n");
		}

		/**
		 * calcTsubo
		 *
		 * @param value
		 * @param crs
		 * @return Object
		 */
		private Object calcTsubo(BigDecimal value, String crs) {
			String returnValue = "";

			if (null == value) {
				return "";
			}

			if("00".equals(crs)) {
				returnValue = value.toString() + "��";
			} else if("01".equals(crs)) {
				returnValue = value.toString() + "�u";
			}

			return returnValue;
		}

		/**
		 * lookupValueWithMulti
		 *
		 * @param lookupName
		 * @param key
		 * @return
		 */
		private String lookupValueWithMulti(String lookupName, String key) {
			if (StringValidateUtil.isEmpty(key)) {
				return "";
			}
			String[] keys = key.split(",");
			StringBuffer resValue = new StringBuffer();

			for(int i = 0; i < keys.length; i++) {
				String value = this.codeLookupManager.lookupValue(lookupName, keys[i]);
				resValue.append(value);
				if(i < keys.length - 1 ) {
					resValue.append(",");
				}
			}

			String value = resValue.toString();

			if (value == null) {
				return "";
			}
			return value;
		}

		/**
		 * lookupValueWithSingle
		 *
		 * @param lookupName
		 * @param key
		 * @return
		 */
		private String lookupValueWithSingle(String lookupName, String key) {
			if (StringValidateUtil.isEmpty(key)) {
				return "";
			}
			String value = this.codeLookupManager.lookupValue(lookupName, key);
			if (value == null) {
				return "";
			}
			return value;
		}

		/**
		 * formatDate
		 *
		 * @param date
		 * @param formatCode
		 * @return
		 */
		private String formatDate(Date date, String formatCode) {
			String str = "";
			if (date != null) {
				SimpleDateFormat sdf = new SimpleDateFormat(formatCode);
				str = sdf.format(date);
			}
			return str;
		}

	}

	/**
	 * ����������SQL���𐶐�����B<br/>
	 * <br/>
	 *
	 * @param sbSql
	 *            ��������SQL����ǉ�����o�b�t�@
	 * @param params
	 *            SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param searchForm ���������̊i�[�I�u�W�F�N�g
	 */
	protected void createSql(PanaInquirySearchForm searchForm, StringBuffer sql, List<Object> params) {
			sql.append(" SELECT ");
			sql.append(" inquiry_id AS inquiryHeader_inquiryId, ");
			sql.append(" inquiry_type AS inquiryHeader_inquiryType, ");
			sql.append(" display_housing_name AS housingInfo_displayHousingName, ");
			sql.append(" inquiry_date AS inquiryHeader_inquiryDate, ");
			sql.append(" answer_status AS inquiryHeader_answerStatus ");
			sql.append(" FROM ( ");



		// �⍇��ʂ̐ݒ�
		String[] inquiryType = new String[3];
		if(!StringValidateUtil.isEmpty(searchForm.getKeyHousingCd()) ||
			!StringValidateUtil.isEmpty(searchForm.getKeyDisplayHousingName())) {
			inquiryType[0] = "00";
		} else {
			if(null == searchForm.getKeyInquiryType()) {
				inquiryType[0] = "00";
				inquiryType[1] = "02";
				inquiryType[2] = "01";
			} else {
				for(int i = 0; i < searchForm.getKeyInquiryType().length; i++) {
					inquiryType[i] = searchForm.getKeyInquiryType()[i];
				}
			}
		}

		for(int i = 0; i < inquiryType.length; i++) {
			if(null != inquiryType[i]) {
				if(i > 0) {
					sql.append(" UNION ALL ");
				}
				createSelect(sql, inquiryType[i]);
				createFrom(sql, inquiryType[i]);
				createWhere(searchForm, sql, inquiryType[i], params);
			}
		}

		sql.append(" ) AS inquiry ");
		sql.append(" ORDER BY inquiryHeader_inquiryId  DESC ");
	}

	/**
	 * Select�啶����𐶐�����<br/>
	 * �������ʂƂ��ĕK�v�ȍ��ڂ�������ꍇ�́A�C�����s������<br/>
	 * �i������RowMapper�N���X�̏C�����K�v�j<b/>
	 * �����RowMapper�N���X�ŎQ�Ƃ���L�[�ƂȂ邱�Ƃ���A�ʖ���`���ăA�N�Z�X���₷�����邱�ƁB
	 * �E�e�[�u�����ƃJ�������̃L�������P�[�X���u_�v�i�A���_�[�o�[�j�Ō���<br/>
	 * �E�W���֐��ɂ��Ă͊֐��̖��̂��܂߂Ė����i��FmaxImageCnt�j<br/>
	 * <br/>
	 *
	 * @param sql ��������SQL����ǉ�����o�b�t�@
	 * @param inquiryType ���������̖⍇���
	 */
	protected void createSelect(StringBuffer sql, String inquiryType) {
		sql.append(" SELECT ");
		sql.append(" inquiryHeader.inquiry_id, ");
		sql.append(" inquiryHeader.inquiry_type, ");
		// �����⍇����I�������ꍇ
		if(PanaCommonConstant.INQUIRY_TYPE_HOUSING.equals(inquiryType)) {
			sql.append(" housingInfo.display_housing_name, ");
		}

		// ����⍇����I�������ꍇ
		if(PanaCommonConstant.INQUIRY_TYPE_ASSESSMENT.equals(inquiryType)) {
			sql.append(" NULL AS display_housing_name, ");
		}

		// �ėp�⍇����I�������ꍇ
		if(PanaCommonConstant.INQUIRY_TYPE_GENERAL.equals(inquiryType)) {
			sql.append(" NULL AS display_housing_name, ");
		}

		sql.append(" inquiryHeader.inquiry_date, ");
		sql.append(" inquiryHeader.answer_status ");
	}

	/**
	 * From��̕�����𐶐�����<br/>
	 * �g�p����e�[�u�����A�ʖ��͒萔���`���ċL�q���邱��<br/>
	 * �i�R�[�f�B���O�ȑf���A�^�C�v�~�X�h�~�j<br/>
	 * <br/>
	 *
	 * @param sql ��������SQL����ǉ�����o�b�t�@
	 * @param inquiryType ���������̖⍇���
	 */
	protected void createFrom(StringBuffer sql, String inquiryType) {
		sql.append(" FROM ");

		// �����⍇����I�������ꍇ
		if(PanaCommonConstant.INQUIRY_TYPE_HOUSING.equals(inquiryType)) {
			sql.append(" inquiry_header inquiryHeader ");
			sql.append(" LEFT JOIN inquiry_housing inquiryHousing ");
			sql.append(" ON inquiryHeader.inquiry_id = inquiryHousing.inquiry_id ");
			sql.append(" LEFT JOIN housing_info housingInfo ");
			sql.append(" ON housingInfo.sys_housing_cd = inquiryHousing.sys_housing_cd ");
		}

		// ����⍇����I�������ꍇ
		if(PanaCommonConstant.INQUIRY_TYPE_ASSESSMENT.equals(inquiryType)) {
			sql.append(" inquiry_header inquiryHeader ");
		}

		// �ėp�⍇����I�������ꍇ
		if(PanaCommonConstant.INQUIRY_TYPE_GENERAL.equals(inquiryType)) {
			sql.append(" inquiry_header inquiryHeader ");
			sql.append(" LEFT JOIN inquiry_dtl_info  inquiryDtlInfo ");
			sql.append(" ON inquiryDtlInfo.inquiry_id = inquiryHeader.inquiry_id ");
		}

	}


	/**
	 * Where��̕�����𐶐�����<br/>
	 * �����������ƂɁA�uAND�`�v�ƂȂ镶����𐶐����郁�\�b�h���Ăяo���Ă���<br/>
	 * <br/>
	 *
	 * @param searchForm ���������̊i�[�I�u�W�F�N�g
	 * @param sql ��������SQL����ǉ�����o�b�t�@
	 * @param inquiryType ���������̖⍇���
	 * @param params
	 *            SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 */
	protected void createWhere(PanaInquirySearchForm searchForm, StringBuffer sql, String inquiryType, List<Object> params) {
		sql.append(" WHERE ");
		sql.append(" 1 =1 ");

		// �����⍇����I�������ꍇ
		if(PanaCommonConstant.INQUIRY_TYPE_HOUSING.equals(inquiryType)) {
			// �����ԍ����͕���������͂����ꍇ
			if(!StringValidateUtil.isEmpty(searchForm.getKeyHousingCd()) ||
					!StringValidateUtil.isEmpty(searchForm.getKeyDisplayHousingName())) {
				// �����ԍ�
				if(!StringValidateUtil.isEmpty(searchForm.getKeyHousingCd())) {
					sql.append(" AND housingInfo.housing_cd = ? ");
					params.add(searchForm.getKeyHousingCd());
				}

				// ������
				if(!StringValidateUtil.isEmpty(searchForm.getKeyDisplayHousingName())) {
					sql.append(" AND housingInfo.display_housing_name LIKE ? ");
					params.add("%" + searchForm.getKeyDisplayHousingName() + "%");
				}
			}
			sql.append(" AND inquiryHeader.inquiry_type = '00' ");
		}

		// ����⍇����I�������ꍇ
		if(PanaCommonConstant.INQUIRY_TYPE_ASSESSMENT.equals(inquiryType)) {
			sql.append(" AND inquiryHeader.inquiry_type = '02' ");
		}

		// �ėp�⍇����I�������ꍇ
		if(PanaCommonConstant.INQUIRY_TYPE_GENERAL.equals(inquiryType)) {
			sql.append(" AND inquiryHeader.inquiry_type = '01' ");

			if(null != searchForm.getKeyInquiryDtlType()) {
				StringBuffer sqlIn = new StringBuffer();
				String[] inquiryDtlType = searchForm.getKeyInquiryDtlType();

				for (int i = 0; i < inquiryDtlType.length; i++) {
					if (inquiryDtlType[i].trim().length() > 0) {
						sqlIn.append(",? ");
						params.add(inquiryDtlType[i]);
					}
				}

				if (sqlIn.length() > 0) {
					// �ŏ��̃J���}���폜����
					String strComma = ",";
					sqlIn.delete(sqlIn.indexOf(strComma), sqlIn.indexOf(strComma) + strComma.length());
					sql.append(" AND inquiryDtlInfo.inquiry_dtl_type IN  (" + sqlIn.toString() + ")");
				}
			}
		}

		// ����ԍ�
		if(!StringValidateUtil.isEmpty(searchForm.getKeyUserId())) {
			sql.append(" AND inquiryHeader.user_id LIKE ? ");
			params.add("%" + searchForm.getKeyUserId() + "%");
		}

		// ���[���A�h���X
		if(!StringValidateUtil.isEmpty(searchForm.getKeyEmail())) {
			sql.append(" AND inquiryHeader.email LIKE ? ");
			params.add("%" + searchForm.getKeyEmail() + "%");
		}

		// �⍇�����J�n��
		if(!StringValidateUtil.isEmpty(searchForm.getKeyInquiryDateStart())) {
			sql.append(" AND inquiryHeader.inquiry_date >= ? ");
			params.add(searchForm.getKeyInquiryDateStart() + " 00:00:00");
		}

		// �⍇�����I����
		if(!StringValidateUtil.isEmpty(searchForm.getKeyInquiryDateEnd())) {
			sql.append(" AND inquiryHeader.inquiry_date <= ? ");
			params.add(searchForm.getKeyInquiryDateEnd() + " 23:59:59");
		}

		// �X�e�[�^�X
		if(!StringValidateUtil.isEmpty(searchForm.getKeyAnswerStatus())) {
			sql.append(" AND inquiryHeader.answer_status = ? ");
			params.add(searchForm.getKeyAnswerStatus());
		}

		// �����⍇�ԍ�
		if(!StringValidateUtil.isEmpty(searchForm.getKeyInquiryId())) {
			sql.append(" AND inquiryHeader.inquiry_id = ? ");
			params.add(searchForm.getKeyInquiryId());
		}

	}
}
