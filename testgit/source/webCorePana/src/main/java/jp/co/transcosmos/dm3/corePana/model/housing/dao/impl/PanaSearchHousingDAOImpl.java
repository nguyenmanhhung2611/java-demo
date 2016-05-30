package jp.co.transcosmos.dm3.corePana.model.housing.dao.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.vo.AdminLoginInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingLandmark;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.EquipMst;
import jp.co.transcosmos.dm3.core.vo.HousingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.RrMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousing;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.dao.PanaSearchHousingDAO;
import jp.co.transcosmos.dm3.corePana.model.housing.dao.PanaSearchHousingRowMapper;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.util.PanaCalcUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.HousingInspection;
import jp.co.transcosmos.dm3.corePana.vo.HousingStatusInfo;
import jp.co.transcosmos.dm3.corePana.vo.MemberInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformChart;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.FragmentResultSetExtractor;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.util.StringUtils;

public class PanaSearchHousingDAOImpl implements PanaSearchHousingDAO {

	// �e�[�u��������
	/** ������{���e�[�u���� */
	protected static final String TABLE_HOUSING_NAME = "housing";
	/** ���t�H�[���v�����e�[�u���� */
	protected static final String TABLE_REFORM_NAME = "reform";
	/** ������{���e�[�u���� */
	protected static final String TABLE_HOUSING_INFO_NAME = "housing_info";
	/** �����X�e�[�^�X���e�[�u���� */
	protected static final String TABLE_HOUSING_STATUS_INFO_NAME = "housing_status_info";
	/** ������{���e�[�u���� */
	protected static final String TABLE_BUILDING_INFO_NAME = "building_info";
	/** �����ڍ׏��e�[�u���� */
	protected static final String TABLE_BUILDING_DTL_INFO_NAME = "building_dtl_info";
	/** �����Ŋ�w���e�[�u���� */
	protected static final String TABLE_BUILDING_STATION_NAME = "building_station_info";
	/** �s���{���}�X�^�e�[�u���� */
	protected static final String TABLE_PREF_MST_NAME = "pref_mst";
	/** �����ڍ׏��e�[�u���� */
	protected static final String TABLE_HOUSING_DTL_INFO_NAME = "housing_dtl_info";
	/** �}�C�y�[�W������e�[�u���� */
	protected static final String TABLE_MEMBER_INFO_NAME = "member_info";
	/** �Ǘ��҃��O�C���h�c���e�[�u���� */
	protected static final String TABLE_ADMIN_LOGIN_INFO_NAME = "admin_login_info";
	/** �����g���������e�[�u���� */
	protected static final String TABLE_HOUSING_EXT_INFO_NAME = "housing_ext_info";
	/** �����ݔ����e�[�u���� */
	protected static final String TABLE_HOUSING_EQUIP_INFO_NAME = "housing_equip_info";
	/** �����C���X�y�N�V�����e�[�u���� */
	protected static final String TABLE_HOUSING_INSPECTION_NAME = "housing_inspection";
	/** ���������h�}�[�N���e�[�u���� */
	protected static final String TABLE_BUILDING_LANDMARK_NAME = "building_landmark";
	/** ���t�H�[���v�����e�[�u���� */
	protected static final String TABLE_REFORM_PLAN_NAME = "reform_plan";
	/** ���t�H�[���ڍ׏��e�[�u���� */
	protected static final String TABLE_REFORM_DTL_NAME = "reform_dtl";
	/** ���t�H�[���E���[�_�[�`���[�g�e�[�u���� */
	protected static final String TABLE_REFORM_CHART_NAME = "reform_chart";

	// �G�C���A�X
	/** ������{���G�C���A�X */
	protected static final String TABLE_HOUSING_INFO_ALIAS = "housingInfo";
	/** �����X�e�[�^�X���G�C���A�X */
	protected static final String TABLE_HOUSING_STATUS_INFO_ALIAS = "housingStatusInfo";
	/** ������{���G�C���A�X */
	protected static final String TABLE_BUILDING_INFO_ALIAS = "buildingInfo";
	/** �����ڍ׏��G�C���A�X */
	protected static final String TABLE_BUILDING_DTL_INFO_ALIAS = "buildingDtlInfo";
	/** �����Ŋ�w���G�C���A�X */
	protected static final String TABLE_BUILDING_STATION_ALIAS = "buildingStationInfo";
	/** �s���{���}�X�^�G�C���A�X */
	protected static final String TABLE_PREF_MST_ALIAS = "prefMst";
	/** �����ڍ׏��G�C���A�X */
	protected static final String TABLE_HOUSING_DTL_INFO_ALIAS = "housingDtlInfo";
	/** �}�C�y�[�W������G�C���A�X */
	protected static final String TABLE_MEMBER_INFO_ALIAS = "memberInfo";
	/** �Ǘ��҃��O�C���h�c���G�C���A�X */
	protected static final String TABLE_ADMIN_LOGIN_INFO1_ALIAS = "adminLoginInfo1";
	/** �Ǘ��҃��O�C���h�c���G�C���A�X */
	protected static final String TABLE_ADMIN_LOGIN_INFO2_ALIAS = "adminLoginInfo2";
	
	protected static final String TABLE_REFORM_PLAN_ALIAS = "reformPlan";

	/** CSV�o�̖͂� */
	protected static final String[] CSV_OUT_NAME = {"housing","reform"};

	// �\�[�g����
	/** �����ԍ��i�\�[�g�����j */
	public static final String SORT_SYS_HOUSING_CD_ASC = "0";

	/** ����/���i���������i�\�[�g�����j */
	public static final String SORT_PRICE_ASC = "1";
	/** ����/���i���������i�\�[�g�����j */
	public static final String SORT_PRICE_DESC = "2";
	/** �����o�^���i�V�����j�i�\�[�g�����j */
	public static final String SORT_UPD_DATA_ASC = "3";
	/** �����o�^���i�V�����j�i�\�[�g�����j */
	public static final String SORT_UPD_DATA_DESC = "4";
	/** �z�N���Â����i�\�[�g�����j */
	public static final String SORT_BUILD_DATE_DESC = "5";
	/** �z�N���V�������i�\�[�g�����j */
	public static final String SORT_BUILD_DATE_ASC = "6";
	/** �w����̋����i�\�[�g�����j */
	public static final String SORT_WALK_TIME_ASC = "7";
	/** �w����̋����i�\�[�g�����j */
	public static final String SORT_WALK_TIME_DESC = "8";

	private static final String _CHARACTER_ENCODING = "UTF-8";

	private static final String _CONTENT_TYPE = "application/octet-stream";

	private static final String _HEADER1 = "Content-Disposition";
	private static final String _HEADER2 = "attachment ;filename=";
	private static final int _BYTE_SIZE = 1024;
	// log
	private static final Log log = LogFactory
			.getLog(PanaSearchHousingDAOImpl.class);

	/** �f�[�^�\�[�X */
	private DataSource dataSource;
	/** �����������ʂ��i�[����ׂ�Mapper */
	private PanaSearchHousingRowMapper panaSearchHousingRowMapper;
	/** ���ʃR�[�h�ϊ����� */
	private CodeLookupManager codeLookupManager;

	private DAO<AdminLoginInfo> adminLoginInfoDAO;

	/**
	 * @param adminLoginInfoDAO �Z�b�g���� adminLoginInfoDAO
	 */
	public void setAdminLoginInfoDAO(DAO<AdminLoginInfo> adminLoginInfoDAO) {
		this.adminLoginInfoDAO = adminLoginInfoDAO;
	}

	// ���w�����ɂĎg�p����ׂ�OR_PART_SRCH_LIST�ɒ��ڐ錾���Ȃ�
	/** �w�k��5�� or �w�k��10�� or �w�k��15�� or �w�k��20�� or �o�X�k��5�� */
	private Map<String, String> orPartSrchWarktime = new HashMap<String, String>();

	/**
	 * �w�k��5�� or �w�k��10�� or �w�k��15�� or �w�k��20�� or �o�X�k��5����ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param orPartSrchWarktime
	 *            �w�k��5�� or �w�k��10�� or �w�k��15�� or �w�k��20�� or �o�X�k��5��
	 */
	public void setOrPartSrchWarktime(Map<String, String> orPartSrchWarktime) {
		this.orPartSrchWarktime = orPartSrchWarktime;
	}

	/**
	 * �f�[�^�\�[�X��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param dataSource
	 *            �f�[�^�\�[�X
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * �����������ʂ��i�[����ׂ�Mapper��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param panaSearchHousingRowMapper
	 *            �����������ʂ��i�[����ׂ�Mapper
	 */
	public void setPanaSearchHousingRowMapper(
			PanaSearchHousingRowMapper panaSearchHousingRowMapper) {
		this.panaSearchHousingRowMapper = panaSearchHousingRowMapper;
	}

	/**
	 * ���ʃR�[�h�ϊ�������ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param codeLookupManager
	 *            ���ʃR�[�h�ϊ�����
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * �������i�ꕔ�A�������j���������A���ʃ��X�g�𕜋A����B�i�ꗗ�p�j<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A����������������B<br/>
	 * �������ʂ� List<Housing> �I�u�W�F�N�g�Ɋi�[����A�擾����List<Housing>��߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 *
	 * @param searchForm
	 *            ���������̊i�[�I�u�W�F�N�g
	 * @return List<Housing>
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Housing> panaSearchHousing(PanaHousingSearchForm searchForm) {

		JdbcTemplate template = new JdbcTemplate(dataSource);

		StringBuffer sbSql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		// ����SQL�𐶐�
		createSql(searchForm, sbSql, params);

		log.debug("SQL : " + sbSql.toString());
		List<Housing> housings = (List<Housing>) template.query(sbSql.toString(), (Object[]) params.toArray(),
				new FragmentResultSetExtractor<Object>(this.panaSearchHousingRowMapper, searchForm.getStartIndex(),
				searchForm.getEndIndex()));
		return housings;
	}

	/**
	 * �������iCSV�o�͏��j���������A���ʃ��X�g�𕜋A����B�iCSV�o�͗p�j<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A����������������B<br/>
	 * CSV�o�̓f�[�^�𐶐����ACSV�t�@�C�����o�͂���B<br/>
	 * <br/>
	 *
	 * @param searchForm
	 *            ���������̊i�[�I�u�W�F�N�g
	 * @throws IOException
	 *
	 */
	@Override
	public void panaSearchHousing(PanaHousingSearchForm searchForm, HttpServletResponse response,
			PanaHousingPartThumbnailProxy panaHousingManager, PanaCommonManage panamCommonManager) throws IOException {

		JdbcTemplate template = new JdbcTemplate(dataSource);

		StringBuffer sbSql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		// ����SQL�𐶐�
		createSql(searchForm, sbSql, params);

		log.debug("SQL : " + sbSql.toString());

		List<PanaHousing> panaHousings = new ArrayList<PanaHousing>();
		List<AdminLoginInfo> adminLoginInfo_InUsers = new ArrayList<AdminLoginInfo>();
		List<MemberInfo> memberInfos = new ArrayList<MemberInfo>();
		List<EquipMst> equipMstList = new ArrayList<EquipMst>();
		template.query(sbSql.toString(), params.toArray(), new HousingListRowCallbackHandler(panaHousingManager,
				memberInfos, adminLoginInfo_InUsers,panaHousings));
		try {
			equipMstList = panamCommonManager.getEquipMstList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		response.setCharacterEncoding(_CHARACTER_ENCODING);
		response.setContentType(_CONTENT_TYPE);
		response.setHeader(_HEADER1, _HEADER2 + getZipFileName());
		ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
		byte[] bom = {(byte)0xef, (byte)0xbb, (byte)0xbf};
		for (String str : CSV_OUT_NAME) {
			String csvFileName = str + ".csv";
			zos.putNextEntry(new ZipEntry(csvFileName));
			zos.write(bom);
			writeCSVHeader(str, zos, equipMstList);
			for (int i = 0; i< panaHousings.size(); i++) {
				writeCSVContent(str,panaHousings.get(i),zos,memberInfos.get(i),adminLoginInfo_InUsers.get(i),equipMstList);
			}
		}
		zos.flush();
		zos.close();
	}

	private void zipWriter(String str, ZipOutputStream zos) throws IOException {
		byte[] bs= new byte[_BYTE_SIZE];
		int value = -1;
		ByteArrayInputStream bais = new ByteArrayInputStream(str.getBytes(_CHARACTER_ENCODING));
		BufferedInputStream bis = new BufferedInputStream(bais,_BYTE_SIZE);
		while ((value = bis.read(bs, 0, bs.length)) != -1) {
			zos.write(bs, 0, value);
		}
		bis.close();
		bais.close();
	}

	/**
	 * CSV�t�@�C�������擾����B<br/>
	 * <br/>
	 *
	 * @return String CSV�t�@�C����
	 */
	private String getZipFileName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dt = sdf.format(new Date());

		return "bukken_" + dt + ".zip";
	}

	@SuppressWarnings("unchecked")
	private void writeCSVContent(String str, PanaHousing panaHousing, ZipOutputStream zos,
			MemberInfo memberInfo, AdminLoginInfo adminLoginInfo_InUser, List<EquipMst> equipMsts) throws IOException {
		StringBuilder sb = new StringBuilder();
		HousingInfo housingInfo = (HousingInfo)panaHousing.getHousingInfo().getItems().get("housingInfo");
		switch (str) {
			case TABLE_HOUSING_NAME:
				AdminLoginInfo adminLoginInfo_UpUser = panaHousing.getHousingInfoUpdUser();
				BuildingInfo buildingInfo = (BuildingInfo)panaHousing.getBuilding().getBuildingInfo().getItems().get("buildingInfo");
				PrefMst prefMst = (PrefMst)panaHousing.getBuilding().getBuildingInfo().getItems().get("prefMst");
				HousingDtlInfo housingDtlInfo = (HousingDtlInfo)panaHousing.getHousingInfo().getItems().get("housingDtlInfo");
				// �����g���������̎擾
				Map<String, Map<String, String>> housingExtInfos = panaHousing.getHousingExtInfos();
				// �Ŋ��w���̎擾
				List<JoinResult> joinResultList = panaHousing.getBuilding().getBuildingStationInfoList();
				BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo)panaHousing.getBuilding().getBuildingInfo().getItems().get("buildingDtlInfo");
				Map<String, EquipMst> equipMstMap = panaHousing.getHousingEquipInfos();
				// ���������h�}�[�N���̎擾
				List<BuildingLandmark> buildingLandmarkList = panaHousing.getBuilding().getBuildingLandmarkList();
				// �����C���X�y�N�V�����̎擾
				List<HousingInspection> housingInspectionList = panaHousing.getHousingInspections();
				HousingStatusInfo housingStatusInfo = (HousingStatusInfo)panaHousing.getHousingInfo().getItems().get("housingStatusInfo");
				AdminLoginInfo adminLoginInfo_UpUser_Id = panaHousing.getHousingInfoUpdUser();
				if(housingInfo != null){
					sb.append("\"" + PanaStringUtils.toString(housingInfo.getHousingCd()) + "\",");//�����ԍ�
					sb.append("\"" + PanaStringUtils.toString(housingInfo.getDisplayHousingName()) + "\",");//������
					if (buildingInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(lookupValueWithDefault("buildingInfo_housingKindCd", buildingInfo.getHousingKindCd())) + "\",");//�������
					}else{
						sb.append("\"\",");
					}
					if (housingDtlInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(housingDtlInfo.getDtlComment()) + "\",");//�����R�����g
					}else{
						sb.append("\"\",");
					}
					if (!housingExtInfos.isEmpty()) {
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "housingDetail","vendorComment")) + "\",");//����R�����g
					}else{
						sb.append("\"\",");
					}
					if (buildingInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(buildingInfo.getZip()) + "\",");//�X�֔ԍ��i�����j
					}else{
						sb.append("\"\",");
					}
					if (prefMst != null) {
						sb.append("\"" + PanaStringUtils.toString(prefMst.getPrefName()) + "\",");//�Z��1(����)
					}else {
						sb.append("\"\",");
					}
					if (buildingInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(buildingInfo.getAddressName()) + PanaStringUtils.toString(buildingInfo.getAddressOther1()) + PanaStringUtils.toString(buildingInfo.getAddressOther2()) + "\",");//�Z��2�i�����j
					}else {
						sb.append("\"\",");
					}
					if (!joinResultList.isEmpty()) {
						// �����P�`�R�A�w�P�`�R�A�o�X�P�`�R�A�k���P�`�R
						List<String[]> strList = getRouteStationBusName(joinResultList);
						for (int i = 0; i < strList.size(); i++) {
							sb.append("\"" + PanaStringUtils.toString(strList.get(i)[0]) + "\",");// ����
							sb.append("\"" + PanaStringUtils.toString(strList.get(i)[1]) + "\",");// �w
							sb.append("\"" + PanaStringUtils.toString(strList.get(i)[2]) + "\",");// �o�X
							sb.append("\"" + PanaStringUtils.toString(strList.get(i)[3]) + "\",");// �k��
						}
					}else {
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
					}
					sb.append("\"" + PanaStringUtils.toString(format(housingInfo.getPrice(),"�~")) + "\",");//�������i
					sb.append("\"" + PanaStringUtils.toString(lookupValueWithDefault("layoutCd",housingInfo.getLayoutCd())) + "\",");//�Ԏ�
					if(buildingDtlInfo != null){
						sb.append("\"" + PanaStringUtils.toString(format(buildingDtlInfo.getBuildingArea(), "�u")) + "\",");//�����ʐ�
					}else {
						sb.append("\"\",");
					}
					if(buildingDtlInfo != null){
						sb.append("\"" + PanaStringUtils.toString(buildingDtlInfo.getBuildingAreaMemo()) + "\",");//�����ʐ�_�⑫
					}else {
						sb.append("\"\",");
					}
					sb.append("\"" + PanaStringUtils.toString(format(housingInfo.getLandArea(), "�u")) + "\",");//�y�n�ʐ�
					sb.append("\"" + PanaStringUtils.toString(housingInfo.getLandAreaMemo()) + "\",");//�y�n�ʐ�_�⑫
					sb.append("\"" + PanaStringUtils.toString(format(housingInfo.getPersonalArea(),"�u")) + "\",");//��L�ʐ�
					sb.append("\"" + PanaStringUtils.toString(housingInfo.getPersonalAreaMemo()) + "\",");//��L�ʐ�_�⑫
					if (buildingInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(format(buildingInfo.getCompDate(),"YYYY�NMM��")) + "\",");//�z�N��
					}else {
						sb.append("\"\",");
					}
					if (housingDtlInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(lookupValueWithDefault("landRight",housingDtlInfo.getLandRight())) + "\",");//�y�n����
					}else {
						sb.append("\"\",");
					}
					if (!housingExtInfos.isEmpty()) {
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "housingDetail","struct")) + "\",");//�����\��
					}else {
						sb.append("\"\",");
					}
					if (housingDtlInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(housingDtlInfo.getContactRoad()) + "\",");//�ړ���
					}else {
						sb.append("\"\",");
					}
					if (housingDtlInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(housingDtlInfo.getContactRoadDir()) + "\",");//�ړ�����/����
					}else {
						sb.append("\"\",");
					}
					if (housingDtlInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(housingDtlInfo.getPrivateRoad()) + "\",");//�������S
					}else {
						sb.append("\"\",");
					}
					sb.append("\"" + PanaStringUtils.toString(housingInfo.getDisplayParkingInfo()) + "\",");//���ԏ�
					if (housingDtlInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(lookupValueWithDefault("usedAreaCd",housingDtlInfo.getUsedAreaCd())) + "\",");//�p�r�n��
					}else {
						sb.append("\"\",");
					}
					if(buildingDtlInfo != null){
						sb.append("\"" + PanaStringUtils.toString(buildingDtlInfo.getCoverageMemo()) + "\",");//���؂���
					}else {
						sb.append("\"\",");
					}
					if(buildingDtlInfo != null){
						sb.append("\"" + PanaStringUtils.toString(buildingDtlInfo.getBuildingRateMemo()) + "\",");//�e�ϗ�
					}else {
						sb.append("\"\",");
					}
					if (!housingExtInfos.isEmpty()) {
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "housingDetail","status")) + "\",");//����
					}else {
						sb.append("\"\",");
					}
					if (housingDtlInfo != null) {
						String moveinTiming = "";
						if (PanaCommonConstant.MOVEIN_TIMING.equals(housingDtlInfo.getMoveinTiming())) {
							moveinTiming = "����";
						}
						sb.append("\"" + PanaStringUtils.toString(moveinTiming) + "\",");//���n����
					}else {
						sb.append("\"\",");
					}
					if (housingDtlInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(housingDtlInfo.getMoveinNote()) + "\",");//���n�����R�����g
					}else {
						sb.append("\"\",");
					}
					if (housingDtlInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(lookupValueWithDefault("transactTypeDiv", housingDtlInfo.getTransactTypeDiv())) + "\",");//����`��
					}else {
						sb.append("\"\",");
					}
					if (housingDtlInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(housingDtlInfo.getSpecialInstruction()) + "\",");//���L����
					}else {
						sb.append("\"\",");
					}
//					if (housingDtlInfo != null) {
//						sb.append("\"" + (housingDtlInfo.getUpkeepCorp()) + "\",");//���l
//					}else {
//						sb.append("\"\",");
//					}
					if (buildingInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(format(buildingInfo.getTotalFloors(), "�K����")) + "\",");//�����K��
					}else {
						sb.append("\"\",");
					}
					sb.append("\"" + PanaStringUtils.toString(format(housingInfo.getFloorNo(),"�K")) + "\",");//���݊K��
					sb.append("\"" + PanaStringUtils.toString(housingInfo.getFloorNoNote()) + "\",");// ���݊K���R�����g
					if (!housingExtInfos.isEmpty()) {
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "housingDetail","totalHouseCnt")) + "\",");//���ː�
					}else {
						sb.append("\"\",");
					}
					if (!housingExtInfos.isEmpty()) {
						sb.append("\"" + PanaStringUtils.toString(lookupValueWithDefault("scaleDataValue",getHousingExtInfos(housingExtInfos, "housingDetail","scale"))) + "\",");//�K��
					}else {
						sb.append("\"\",");
					}
					if (!housingExtInfos.isEmpty()) {
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "housingDetail","direction")) + "\",");//����(��������E�̌��j
					}else {
						sb.append("\"\",");
					}
					sb.append("\"" + PanaStringUtils.toString(format(housingInfo.getUpkeep(),"���~")) + "\",");//�Ǘ���
					sb.append("\"" + PanaStringUtils.toString(format(housingInfo.getMenteFee(),"���~")) + "\",");//�C�U�ϗ���
					if (housingDtlInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(housingDtlInfo.getUpkeepCorp()) + "\",");//�Ǘ����
					}else {
						sb.append("\"\",");
					}
					if (housingDtlInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(housingDtlInfo.getUpkeepType()) + "\",");//�Ǘ��`�ԁE����
					}else {
						sb.append("\"\",");
					}
					if (housingDtlInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(format(housingDtlInfo.getBalconyArea(),"�u")) + "\",");//�o���R�j�[�ʐ�
					}else {
						sb.append("\"\",");
					}
					if (housingDtlInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(lookupValueWithDefault("insurExist",housingDtlInfo.getInsurExist())) + "\",");//���r�ی�
					}else {
						sb.append("\"\",");
					}
					if (!housingExtInfos.isEmpty()) {
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "housingDetail","staffName")) + "\",");//�S���Җ�
					}else {
						sb.append("\"\",");
					}
					if (!housingExtInfos.isEmpty()) {
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "housingDetail","companyName")) + "\",");//��Ж�
					}else {
						sb.append("\"\",");
					}
					if (!housingExtInfos.isEmpty()) {
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "housingDetail","branchName")) + "\",");//�x�X��
					}else {
						sb.append("\"\",");
					}
					if (!housingExtInfos.isEmpty()) {
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "housingDetail","licenseNo")) + "\",");//�Ƌ��ԍ�
					}else {
						sb.append("\"\",");
					}
					sb.append("\"" + PanaStringUtils.toString(housingInfo.getBasicComment()) + "\",");//�S���҃R�����g
					if (!housingExtInfos.isEmpty()) {
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "housingDetail","infrastructure")) + "\",");//�C���t��
					}else {
						sb.append("\"\",");
					}
					// �ݔ�����(�񐢑ь����A���g�[�A���r���O�_�C�j���O15��ȏ�A�t���[�����O�A���A�E�H�[�N�C���N���[�[�b�g�A���t�g�A
					// �������C�����@�ATV���j�^�t���C���^�[�t�H���A�E�b�h�f�b�L�A�y�b�g�A��z�{�b�N�X�A�p�����A�p�n)
					if(!equipMstMap.isEmpty()){
						for (int i = 0; i<equipMsts.size(); i++) {
							if(equipMstMap.containsKey(equipMsts.get(i).getEquipCd())){
								if (i == equipMsts.size() - 1) {
									sb.append("\"" +"����"+"\",");
								} else {
									sb.append("\"" +"����"+"\",");
								}
							} else {
								if (i == equipMsts.size() - 1) {
									sb.append("\"" +"�Ȃ�"+"\",");
								} else {
									sb.append("\"" +"�Ȃ�"+"\",");
								}
							}
						}
					}else{
						for (int i=0; i<equipMsts.size(); i++) {
							if (i == equipMsts.size() - 1) {
								sb.append("\"" +"�Ȃ�"+"\",");
							} else {
								sb.append("\"" +"�Ȃ�"+"\",");
							}
						}
					}
					if (!housingExtInfos.isEmpty()) {
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo01")) + "\",");//�Ǘ��җp�ݔ����01
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo02")) + "\",");//�Ǘ��җp�ݔ����02
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo03")) + "\",");//�Ǘ��җp�ݔ����03
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo04")) + "\",");//�Ǘ��җp�ݔ����04
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo05")) + "\",");//�Ǘ��җp�ݔ����05
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo06")) + "\",");//�Ǘ��җp�ݔ����06
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo07")) + "\",");//�Ǘ��җp�ݔ����07
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo08")) + "\",");//�Ǘ��җp�ݔ����08
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo09")) + "\",");//�Ǘ��җp�ݔ����09
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo10")) + "\",");//�Ǘ��җp�ݔ����10
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo11")) + "\",");//�Ǘ��җp�ݔ����11
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo12")) + "\",");//�Ǘ��җp�ݔ����12
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo13")) + "\",");//�Ǘ��җp�ݔ����13
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo14")) + "\",");//�Ǘ��җp�ݔ����14
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo15")) + "\",");//�Ǘ��җp�ݔ����15
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo16")) + "\",");//�Ǘ��җp�ݔ����16
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo17")) + "\",");//�Ǘ��җp�ݔ����17
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo18")) + "\",");//�Ǘ��җp�ݔ����18
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo19")) + "\",");//�Ǘ��җp�ݔ����19
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo20")) + "\",");//�Ǘ��җp�ݔ����20
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo21")) + "\",");//�Ǘ��җp�ݔ����21
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo22")) + "\",");//�Ǘ��җp�ݔ����22
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo23")) + "\",");//�Ǘ��җp�ݔ����23
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo24")) + "\",");//�Ǘ��җp�ݔ����24
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo25")) + "\",");//�Ǘ��җp�ݔ����25
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo26")) + "\",");//�Ǘ��җp�ݔ����26
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo27")) + "\",");//�Ǘ��җp�ݔ����27
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo28")) + "\",");//�Ǘ��җp�ݔ����28
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo29")) + "\",");//�Ǘ��җp�ݔ����29
						sb.append("\"" + PanaStringUtils.toString(getHousingExtInfos(housingExtInfos, "adminEquip","adminEquipInfo30")) + "\",");//�Ǘ��җp�ݔ����30
					}else {
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
					}
					// ����	�a�@	�ۈ牀/�c�t��	���w�Z	���w�Z	�X�[�p�[	�R���r�j	���̑��{��
					if (!buildingLandmarkList.isEmpty()) {
						// ���������h�}�[�N���̎擾
						Iterator<String> codeLookup_Keys_Type = this.codeLookupManager.getKeysByLookup("buildingLandmark_landmarkType");
						while (codeLookup_Keys_Type.hasNext()) {
							String codeLookup_Key_Type = (String) codeLookup_Keys_Type.next();
							for (int i = 0; i < buildingLandmarkList.size(); i++) {
								BuildingLandmark bl = buildingLandmarkList.get(i);
								BigDecimal distanceFromLandmark = null;
								if (bl.getDistanceFromLandmark() != null) {
									distanceFromLandmark = PanaCalcUtil.calcLandMarkTime(bl.getDistanceFromLandmark());
								}
								if (codeLookup_Key_Type.matches(bl.getLandmarkType())) {
									if(codeLookup_Keys_Type.hasNext()){
										if (bl.getLandmarkName() != null) {
											if (bl.getDistanceFromLandmark() != null) {
												sb.append("\"" + PanaStringUtils.toString(bl.getLandmarkName() + "�F�k��" + distanceFromLandmark + "��(" + bl.getDistanceFromLandmark() + "m)") + "\",");
											} else {
												sb.append("\"" + PanaStringUtils.toString(bl.getLandmarkName()) + "\",");
											}
										} else {
											sb.append("\"\",");
										}
									} else {
										if (bl.getLandmarkName() != null) {
											if (bl.getDistanceFromLandmark() != null) {
												sb.append("\"" + PanaStringUtils.toString(bl.getLandmarkName() + "�F�k��" + distanceFromLandmark + "��(" + bl.getDistanceFromLandmark() + "m)") + "\",");
											} else {
												sb.append("\"" + PanaStringUtils.toString(bl.getLandmarkName()) + "\",");
											}
										} else {
											sb.append("\"\",");
										}
									}
									break;
								}
								if (i == buildingLandmarkList.size() - 1) {
									if(codeLookup_Keys_Type.hasNext()){
										sb.append("\"\",");
									} else {
										sb.append("\"\",");
									}
								}
							}
						}
					}else{
						// ����	�a�@	�ۈ牀/�c�t��	���w�Z	���w�Z	�X�[�p�[	�R���r�j	���̑��{��
						Iterator<String> codeLookup_Keys_Type = this.codeLookupManager.getKeysByLookup("buildingLandmark_landmarkType");
						while (codeLookup_Keys_Type.hasNext()) {
							String codeLookup_Key_Type = (String) codeLookup_Keys_Type.next();
							if (codeLookup_Keys_Type.hasNext()) {
								sb.append("\"\",");
							} else {
								sb.append("\"\",");
							}
						}
					}
					if (!housingExtInfos.isEmpty()) {
						sb.append("\"" + PanaStringUtils.toString(lookupValueWithDefault("housingInspection",getHousingExtInfos(housingExtInfos, "housingInspection","inspectionExist"))) + "\",");//�Z��f�f���{�L��
					}else {
						sb.append("\"\",");
					}
					if(!housingInspectionList.isEmpty()){
						List<String[]> iList = getHousingInspection(housingInspectionList);
						for (int i = 0; i < iList.size(); i++) {
							if (i == 7) {
								sb.append("\"" + PanaStringUtils.toString(iList.get(i)[0]) + "\",");// ���[�_�[�`���[�g���l�i�]����j
								sb.append("\"" + PanaStringUtils.toString(iList.get(i)[1]) + "\",");// ���[�_�[�`���[�g���l�i�m�F�͈́j
							} else {
								sb.append("\"" + PanaStringUtils.toString(iList.get(i)[0]) + "\",");// ���[�_�[�`���[�g���l�i�]����j
								sb.append("\"" + PanaStringUtils.toString(iList.get(i)[1]) + "\",");// ���[�_�[�`���[�g���l�i�m�F�͈́j
							}
						}
					}else{
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
						sb.append("\"\",");
					}
					sb.append("\"" + PanaStringUtils.toString(housingInfo.getReformComment()) + "\",");//���t�H�[���v��������������
					// �A�C�R�����̏���
					// �Z��f�f��,�V�z�ۏ؏��p��,���r�ی���,���t�H�[���ی���,SumStock,��̌^���[����
					Iterator<String> codeLookup_Keys = this.codeLookupManager.getKeysByLookup("recommend_point_icon");
					String[] iconCdArr = null;
					if (!StringValidateUtil.isEmpty(housingInfo.getIconCd())) {
						iconCdArr = housingInfo.getIconCd().split(",");
					}
					while (codeLookup_Keys.hasNext()) {
						String codeLookup_Key = (String) codeLookup_Keys.next();
						if (iconCdArr != null) {
							for (int i = 0; i < iconCdArr.length; i++) {
								if(codeLookup_Key.matches(iconCdArr[i])){
									sb.append("\"" + PanaStringUtils.toString("����") + "\",");
									break;
								}
								if(i == iconCdArr.length - 1){
									sb.append("\"" + PanaStringUtils.toString("�Ȃ�") + "\",");
								}
							}
						} else {
							sb.append("\"" + PanaStringUtils.toString("�Ȃ�") + "\",");
						}
					}
					if (housingStatusInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(lookupValueWithDefault("hiddenFlg",housingStatusInfo.getHiddenFlg())) + "\",");//���J�敪
					}else {
						sb.append("\"\",");
					}
					if (housingStatusInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(lookupValueWithDefault("statusCd",housingStatusInfo.getStatusCd())) + "\",");//�X�e�[�^�X
					}else {
						sb.append("\"\",");
					}
					if (housingStatusInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(housingStatusInfo.getUserId()) + "\",");//����ԍ�
					}else {
						sb.append("\"\",");
					}
					if (memberInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(memberInfo.getMemberLname())+"�@"+ PanaStringUtils.toString(memberInfo.getMemberFname()) + "\",");//����
					}else {
						sb.append("\"\",");
					}
					if (memberInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(memberInfo.getTel()) + "\",");// �d�b�ԍ�
					}else {
						sb.append("\"\",");
					}
					if (memberInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(memberInfo.getEmail()) + "\",");// ���[���A�h���X
					}else {
						sb.append("\"\",");
					}
					if (housingStatusInfo != null) {
						sb.append("\"" + PanaStringUtils.toString(housingStatusInfo.getNote()) + "\",");//���l�i�Ǘ��җp�j
					}else {
						sb.append("\"\",");
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					if(housingInfo.getInsDate() != null){
						sb.append("\"" + PanaStringUtils.toString(sdf.format(housingInfo.getInsDate())) + "\",");//�V�K�o�^����
					}else {
						sb.append("\"\",");//�V�K�o�^����
					}
					if (adminLoginInfo_InUser != null) {
						sb.append("\"" + PanaStringUtils.toString(adminLoginInfo_InUser.getUserName()) + "\",");//�V�K�o�^��
					} else {
						sb.append("\"\",");//�V�K�o�^��
					}
					if(housingInfo.getUpdDate() != null){
						sb.append("\"" + PanaStringUtils.toString(sdf.format(housingInfo.getUpdDate())) + "\",");//�ŏI�X�V����
					}else {
						sb.append("\"\",");//�ŏI�X�V����
					}
					if (adminLoginInfo_UpUser_Id != null) {
						sb.append("\"" + PanaStringUtils.toString(adminLoginInfo_UpUser_Id.getAdminUserId()) + "\",");// �ŏI�X�V�ҁiID�j
					}
					if (adminLoginInfo_UpUser != null) {
						sb.append("\"" + PanaStringUtils.toString(adminLoginInfo_UpUser.getUserName()) + "\",");//�ŏI�X�V��
					} else {
						sb.append("\"\"");//�ŏI�X�V��
					}
				}
				sb.append("\r\n");
				break;
			case TABLE_REFORM_NAME:
				// ���t�H�[���v�������̎擾
				List<Map<String, Object>> reformList0 = new ArrayList<Map<String, Object>>();
				reformList0 = panaHousing.getReforms();
				// ���t�H�[���v����
				List<ReformPlan>  reformPlanResult = new ArrayList<ReformPlan>();
				// ���t�H�[���ڍ׏��
				List<List<ReformDtl>> reformDtlResult = new ArrayList<List<ReformDtl>>();
				// ���t�H�[���E���[�_�[�`���[�g
				List<List<ReformChart>> chartResult = new ArrayList<List<ReformChart>>();

				for (Map<String, Object> reformMap : reformList0) {
					// ���t�H�[���v����
					ReformPlan reformPlan = (ReformPlan)reformMap.get("reformPlan");
					reformPlanResult.add(reformPlan);
					// ���t�H�[���ڍ׏��
					List<ReformDtl> reformDtlList = (List<ReformDtl>) reformMap.get("dtlList");
					reformDtlResult.add(reformDtlList);
					// ���t�H�[���E���[�_�[�`���[�g
					List<ReformChart> reformChartList = (List<ReformChart>) reformMap.get("chartList");
					chartResult.add(reformChartList);
				}
				if (!reformPlanResult.isEmpty()) {
					// ���t�H�[���v�������ɏo�͂̍��ڂ�ݒ肷��B
					String planPrice = "";// ���i
					String housingPrice = format(housingInfo.getPrice(),"���~");
					for (ReformPlan rp : reformPlanResult) {
						sb.append("\"" + PanaStringUtils.toString(housingInfo.getHousingCd()) + "\",");//�����ԍ�
						sb.append("\"" + PanaStringUtils.toString(housingInfo.getDisplayHousingName()) + "\",");//������
						sb.append("\"" + PanaStringUtils.toString(rp.getPlanName()) + "\",");//���t�H�[���v������
						if (housingInfo.getPrice() == null && rp.getPlanPrice() != null) {
							planPrice = "�������i"+ "0���~" + " + ���t�H�[�����i" + format(rp.getPlanPrice(), "���~") + " = " + format(rp.getPlanPrice(),"���~");// ���i
						} else if (housingInfo.getPrice() != null && rp.getPlanPrice() == null) {
							planPrice = "�������i"+ housingPrice + " + ���t�H�[�����i" + "0���~" + " = " + housingPrice;// ���i
						} else if (housingInfo.getPrice() != null && rp.getPlanPrice() != null) {
							Long[] LArrPrice = new Long[]{housingInfo.getPrice(),rp.getPlanPrice()};
							planPrice = "�������i"+ housingPrice + " + ���t�H�[�����i" + format(rp.getPlanPrice(), "���~") + " = "
									+ format(LArrPrice, "���~+");// ���i
						}
						sb.append("\"" + PanaStringUtils.toString(planPrice) + "\",");//���i
						sb.append("\"" + PanaStringUtils.toString(lookupValueWithDefault("hiddenFlg", rp.getHiddenFlg())) + "\",");//���J�敪
						if (!reformDtlResult.isEmpty()) {
							sb.append("\"");
							for (List<ReformDtl> rdList : reformDtlResult) {
								for (ReformDtl rd : rdList) {
									if (rd.getSysReformCd().equals(rp.getSysReformCd())) {
										sb.append(PanaStringUtils.toString("�u"+rd.getImgName() +":"+ format(rd.getReformPrice(), "���~")+"�v"));// ���t�H�[���ڍׁi���e�j�F�t�H�[���ڍׁi���i�j
									}
								}
							}
							sb.append("\",");
						}
						sb.append("\"" + PanaStringUtils.toString(rp.getSalesPoint()) + "\",");//�Z�[���X�|�C���g�i�R���Z�v�g�j
						sb.append("\"" + PanaStringUtils.toString(rp.getConstructionPeriod()) + "\",");//�H��
						sb.append("\"" + PanaStringUtils.toString(rp.getNote()) + "\",");//���l

						if (!chartResult.isEmpty()) {
							String[] chartResultArr = new String[]{"","","","","","","",""};
							for (List<ReformChart> rcList : chartResult) {
								if (!rcList.isEmpty()) {
									for (ReformChart rc : rcList) {
 										if (rc.getSysReformCd().equals(rp.getSysReformCd())) {
											if (rc.getChartValue() != null) {
												chartResultArr[Integer.valueOf(rc.getChartKey())-1] = lookupValueWithDefault("inspectionLabel",String.valueOf(rc.getChartValue()));
											}
										}
									}
								}
							}
							sb.append("\"" + PanaStringUtils.toString(chartResultArr[0]) + "\",");// ���[�_�[�`���[�g���l�P�i���t�H�[�����{��j
							sb.append("\"" + PanaStringUtils.toString(chartResultArr[1]) + "\",");// ���[�_�[�`���[�g���l�Q�i���t�H�[�����{��j
							sb.append("\"" + PanaStringUtils.toString(chartResultArr[2]) + "\",");// ���[�_�[�`���[�g���l�R�i���t�H�[�����{��j
							sb.append("\"" + PanaStringUtils.toString(chartResultArr[3]) + "\",");// ���[�_�[�`���[�g���l�S�i���t�H�[�����{��j
							sb.append("\"" + PanaStringUtils.toString(chartResultArr[4]) + "\",");// ���[�_�[�`���[�g���l�T�i���t�H�[�����{��j
							sb.append("\"" + PanaStringUtils.toString(chartResultArr[5]) + "\",");// ���[�_�[�`���[�g���l�U�i���t�H�[�����{��j
							sb.append("\"" + PanaStringUtils.toString(chartResultArr[6]) + "\",");// ���[�_�[�`���[�g���l�V�i���t�H�[�����{��j
							sb.append("\"" + PanaStringUtils.toString(chartResultArr[7]) + "\",");// ���[�_�[�`���[�g���l�W�i���t�H�[�����{��j
						}
						// ���ڒǉ�
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						if(rp.getInsDate() != null){
							sb.append("\"" + PanaStringUtils.toString(sdf.format(rp.getInsDate())) + "\",");//�V�K�o�^����
						}else {
							sb.append("\"\",");//�V�K�o�^����
						}
						if (this.adminLoginInfoDAO.selectByPK(rp.getInsUserId()) != null) {
							sb.append("\"" + PanaStringUtils.toString(this.adminLoginInfoDAO.selectByPK(rp.getInsUserId()).getUserName()) + "\",");//�V�K�o�^��
						}else{
							sb.append("\"\",");//�V�K�o�^��
						}
						if(rp.getUpdDate() != null){
							sb.append("\"" + PanaStringUtils.toString(sdf.format(rp.getUpdDate())) + "\",");//�ŏI�X�V����
						}else {
							sb.append("\"\",");//�ŏI�X�V����
						}
						sb.append("\"" + PanaStringUtils.toString(rp.getUpdUserId()) + "\",");// �ŏI�X�V�ҁiID�j
						if (this.adminLoginInfoDAO.selectByPK(rp.getUpdUserId()) != null) {
							sb.append("\"" + PanaStringUtils.toString(this.adminLoginInfoDAO.selectByPK(rp.getUpdUserId()).getUserName()) + "\"");//�ŏI�X�V��
						}else{
							sb.append("\"\"");//�ŏI�X�V��
						}
						sb.append("\r\n");
					}
				};
				break;

		default:
			break;
		}
		zipWriter(sb.toString(), zos);
	}

	private String lookupValueWithDefault(String lookupName, String key) {
		if (StringValidateUtil.isEmpty(key)) {
			return "";
		}
		String value = this.codeLookupManager.lookupValue(lookupName, key);
		if (value == null) {
			return "";
		}
		return value;
	}

	private String format(Object ob, String addCode) {
		String str = "";
		if (ob != null) {
			switch (addCode) {
				case "�~":
					NumberFormat numberFormat = NumberFormat.getIntegerInstance(Locale.JAPANESE);
					str = numberFormat.format(ob) + addCode;
					break;
				case "���~":
					long LRemainder = ((Long)ob).longValue() % 10000;
					long tempValue = 0L;
					if(LRemainder != 0){
						tempValue = ((Long)ob).longValue() / 10000 + 1;
					} else {
						tempValue = ((Long)ob).longValue() / 10000;
					}
					NumberFormat numberFormat2 = NumberFormat.getIntegerInstance(Locale.JAPANESE);
					str = numberFormat2.format(tempValue) + addCode;
					break;
				case "���~+":
					Long[] lArr = ((Long[])ob);
					long LastValue = 0L;
					for (long lArrValue: lArr) {
						long LRemainderVal = lArrValue % 10000;
						long tempVal = 0L;
						if(LRemainderVal != 0){
							tempVal = lArrValue / 10000 + 1;
						} else {
							tempVal = lArrValue / 10000;
						}
						LastValue = LastValue + tempVal;
					}
					NumberFormat numberFormat3 = NumberFormat.getIntegerInstance(Locale.JAPANESE);
					str = numberFormat3.format(LastValue) + "���~";
					break;
				case "�u":
					str = ob.toString() + addCode;
					break;
				case "�K����":
					str = ob.toString() + addCode;
					break;
				case "�K":
					str = ob.toString() + addCode;
					break;
				case "���~":
					NumberFormat numberFormat1 = NumberFormat.getIntegerInstance(Locale.JAPANESE);
					str = "��" + numberFormat1.format(ob) + "�~";
					break;
				case "YYYY/MM/dd/HH/MM/SS":
					SimpleDateFormat sdf = new SimpleDateFormat(addCode);
					str = sdf.format(ob);
					break;
				case "YYYY�NMM��":
					SimpleDateFormat sdf1 = new SimpleDateFormat(addCode);
					str = sdf1.format(ob);
					break;
				default:
					break;
			}
		}
		return str;
	}

	private String getHousingExtInfos(
			Map<String, Map<String, String>> housingExtInfos,
			String keyName, String key) {
		String str = "";
		if (housingExtInfos.size() != 0) {
			Map<String, String> extInfokeyAddValue = housingExtInfos
					.get(keyName);
			if (extInfokeyAddValue != null) {
				if (extInfokeyAddValue.get(key) != null) {
					str = extInfokeyAddValue.get(key);
				}
			}
		}
		return str;
	}

	private List<String[]> getRouteStationBusName(List<JoinResult> jrList) {
		List<String[]> strList = new ArrayList<String[]>();
		for (int i = 0; i < jrList.size(); i++) {
			JoinResult jr = jrList.get(i);
			BuildingStationInfo buildingStationInfo = (BuildingStationInfo) jr
					.getItems().get("buildingStationInfo");
			RouteMst routeMst = (RouteMst) jr.getItems().get("routeMst");
			StationMst stationMst = (StationMst) jr.getItems().get(
					"stationMst");
			// �S����Ѓ}�X�g�̎擾
			RrMst rrMst = (RrMst) jr.getItems().get("rrMst");

			String[] strArr = new String[] { "-", "-", "-", "-" };
			if (StringValidateUtil.isEmpty(routeMst.getRouteName())) {
				if (!StringValidateUtil.isEmpty(buildingStationInfo
						.getDefaultRouteName())) {
					strArr[0] = buildingStationInfo.getDefaultRouteName();// ����
				}
			} else {
				strArr[0] = rrMst.getRrName()+routeMst.getRouteNameFull();// ����
			}
			if (StringValidateUtil.isEmpty(stationMst.getStationName())) {
				if (!StringValidateUtil.isEmpty(buildingStationInfo.getStationName())) {
					strArr[1] = buildingStationInfo.getStationName()+"�w";// �w
				}
			} else {
				if(!StringValidateUtil.isEmpty(stationMst.getStationName())){
					strArr[1] = stationMst.getStationName()+"�w";// �w
				}
			}
			if (!StringValidateUtil.isEmpty(buildingStationInfo.getBusCompany())) {
				strArr[2] = buildingStationInfo.getBusCompany();// �o�X
			}
			if (buildingStationInfo.getTimeFromBusStop() != null) {
				strArr[3] = String.valueOf(buildingStationInfo.getTimeFromBusStop())+"��";// �k��
			}
			strList.add(strArr);
		}
		int size = strList.size();
		if (size < 3) {
			String[] strArr1 = new String[] { "-", "-", "-", "-" };
			for (int j = 0; j < 3 - size; j++) {
				strList.add(strArr1);
			}
		}
		return strList;
	}

	private List<String[]> getHousingInspection(List<HousingInspection> housingInspectionList) {
		List<String[]> strList = new ArrayList<String[]>();
		for (int i = 0; i < housingInspectionList.size(); i++) {
			HousingInspection housingInspection = housingInspectionList.get(i);
			String[] strArr = new String[2];
			strArr[0] = lookupValueWithDefault("inspectionResult",String.valueOf(housingInspection.getInspectionValue()));
			strArr[1] = lookupValueWithDefault("inspectionLabel",String.valueOf(housingInspection.getInspectionTrust()));
			strList.add(strArr);
		}
		int size = strList.size();
		if (size < 8) {
			String[] strArr1 = new String[]{"",""};
			for (int j = 0; j < 8 - size; j++) {
				strList.add(strArr1);
			}
		}
		return strList;
	}

	/**
	 * CSV��header�f�[�^�𐶐�����B<br/>
	 *
	 * @param tableName
	 * @throws IOException
	 */
	private void writeCSVHeader(String tableName, ZipOutputStream zos, List<EquipMst> equipMsts) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("�����ԍ�" + ",");
		sb.append("������" + ",");
		switch (tableName) {
			case TABLE_HOUSING_NAME:
				sb.append("�������" + ",");
				sb.append("�����R�����g" + ",");
				sb.append("����R�����g" + ",");
				sb.append("�X�֔ԍ��i�����j" + ",");
				sb.append("�Z��1(����) " + ",");
				sb.append("�Z��2�i�����j" + ",");
				sb.append("����1" + ",");
				sb.append("�w1" + ",");
				sb.append("�o�X1" + ",");
				sb.append("�k��1" + ",");
				sb.append("����2" + ",");
				sb.append("�w2" + ",");
				sb.append("�o�X2" + ",");
				sb.append("�k��2" + ",");
				sb.append("����3" + ",");
				sb.append("�w3" + ",");
				sb.append("�o�X3" + ",");
				sb.append("�k��3" + ",");
				sb.append("�������i" + ",");
				sb.append("�Ԏ�" + ",");
				sb.append("�����ʐ�" + ",");
				sb.append("�����ʐ�_�⑫" + ",");
				sb.append("�y�n�ʐ�" + ",");
				sb.append("�y�n�ʐ�_�⑫" + ",");
				sb.append("��L�ʐ�" + ",");
				sb.append("��L�ʐ�_�⑫" + ",");
				sb.append("�z�N��" + ",");
				sb.append("�y�n����" + ",");
				sb.append("�����\��" + ",");
				sb.append("�ړ���" + ",");
				sb.append("�ړ�����/����" + ",");
				sb.append("�������S" + ",");
				sb.append("���ԏ�" + ",");
				sb.append("�p�r�n��" + ",");
				sb.append("���؂���" + ",");
				sb.append("�e�ϗ�" + ",");
				sb.append("����" + ",");
				sb.append("���n����" + ",");
				sb.append("���n�����R�����g" + ",");
				sb.append("����`��" + ",");
				sb.append("���L����" + ",");
//				sb.append("���l" + ",");
				sb.append("�����K��" + ",");
				sb.append("���݊K��" + ",");
				sb.append("���݊K���R�����g" + ",");
				sb.append("���ː�" + ",");
				sb.append("�K��" + ",");
				sb.append("����(��������E�̌��j" + ",");
				sb.append("�Ǘ���" + ",");
				sb.append("�C�U�ϗ���" + ",");
				sb.append("�Ǘ����" + ",");
				sb.append("�Ǘ��`�ԁE����" + ",");
				sb.append("�o���R�j�[�ʐ�" + ",");
				sb.append("���r�ی�" + ",");
				sb.append("�S���Җ�" + ",");
				sb.append("��Ж�" + ",");
				sb.append("�x�X��" + ",");
				sb.append("�Ƌ��ԍ�" + ",");
				sb.append("�S���҃R�����g" + ",");
				sb.append("�C���t��" + ",");
				// �񐢑ь���	���g�[	���r���O�_�C�j���O15��ȏ�	�t���[�����O	�a��	�E�H�[�N�C���N���[�[�b�g	���t�g	�������C�����@	TV���j�^�t���C���^�[�t�H��	�E�b�h�f�b�L	�y�b�g��	��z�{�b�N�X	�p�����A�p�n
				for (int i=0; i<equipMsts.size(); i++) {
					if (i == equipMsts.size() - 1) {
						sb.append(equipMsts.get(i).getEquipName() + ",");
					} else {
						sb.append(equipMsts.get(i).getEquipName() + ",");
					}
				}
				sb.append("�Ǘ��җp�ݔ����01" + ",");
				sb.append("�Ǘ��җp�ݔ����02" + ",");
				sb.append("�Ǘ��җp�ݔ����03" + ",");
				sb.append("�Ǘ��җp�ݔ����04" + ",");
				sb.append("�Ǘ��җp�ݔ����05" + ",");
				sb.append("�Ǘ��җp�ݔ����06" + ",");
				sb.append("�Ǘ��җp�ݔ����07" + ",");
				sb.append("�Ǘ��җp�ݔ����08" + ",");
				sb.append("�Ǘ��җp�ݔ����09" + ",");
				sb.append("�Ǘ��җp�ݔ����10" + ",");
				sb.append("�Ǘ��җp�ݔ����11" + ",");
				sb.append("�Ǘ��җp�ݔ����12" + ",");
				sb.append("�Ǘ��җp�ݔ����13" + ",");
				sb.append("�Ǘ��җp�ݔ����14" + ",");
				sb.append("�Ǘ��җp�ݔ����15" + ",");
				sb.append("�Ǘ��җp�ݔ����16" + ",");
				sb.append("�Ǘ��җp�ݔ����17" + ",");
				sb.append("�Ǘ��җp�ݔ����18" + ",");
				sb.append("�Ǘ��җp�ݔ����19" + ",");
				sb.append("�Ǘ��җp�ݔ����20" + ",");
				sb.append("�Ǘ��җp�ݔ����21" + ",");
				sb.append("�Ǘ��җp�ݔ����22" + ",");
				sb.append("�Ǘ��җp�ݔ����23" + ",");
				sb.append("�Ǘ��җp�ݔ����24" + ",");
				sb.append("�Ǘ��җp�ݔ����25" + ",");
				sb.append("�Ǘ��җp�ݔ����26" + ",");
				sb.append("�Ǘ��җp�ݔ����27" + ",");
				sb.append("�Ǘ��җp�ݔ����28" + ",");
				sb.append("�Ǘ��җp�ݔ����29" + ",");
				sb.append("�Ǘ��җp�ݔ����30" + ",");
				// ����	�a�@	�ۈ牀/�c�t��	���w�Z	���w�Z	�X�[�p�[	�R���r�j	���̑��{��
				Iterator<String> codeLookup_Keys_Type = this.codeLookupManager.getKeysByLookup("buildingLandmark_landmarkType");
				while (codeLookup_Keys_Type.hasNext()) {
					String codeLookup_Key_Type = (String) codeLookup_Keys_Type.next();
					if (codeLookup_Keys_Type.hasNext()) {
						sb.append(lookupValueWithDefault("buildingLandmark_landmarkType", codeLookup_Key_Type) + ",");
					} else {
						sb.append(lookupValueWithDefault("buildingLandmark_landmarkType", codeLookup_Key_Type) + ",");
					}
				}
				sb.append("�Z��f�f���{�L��" + ",");
				sb.append("���[�_�[�`���[�g���l�P�i�]����j" + ",");
				sb.append("���[�_�[�`���[�g���l�P�i�m�F�͈́j" + ",");
				sb.append("���[�_�[�`���[�g���l�Q�i�]����j" + ",");
				sb.append("���[�_�[�`���[�g���l�Q�i�m�F�͈́j" + ",");
				sb.append("���[�_�[�`���[�g���l�R�i�]����j" + ",");
				sb.append("���[�_�[�`���[�g���l�R�i�m�F�͈́j" + ",");
				sb.append("���[�_�[�`���[�g���l�S�i�]����j" + ",");
				sb.append("���[�_�[�`���[�g���l�S�i�m�F�͈́j" + ",");
				sb.append("���[�_�[�`���[�g���l�T�i�]����j" + ",");
				sb.append("���[�_�[�`���[�g���l�T�i�m�F�͈́j" + ",");
				sb.append("���[�_�[�`���[�g���l�U�i�]����j" + ",");
				sb.append("���[�_�[�`���[�g���l�U�i�m�F�͈́j" + ",");
				sb.append("���[�_�[�`���[�g���l�V�i�]����j" + ",");
				sb.append("���[�_�[�`���[�g���l�V�i�m�F�͈́j" + ",");
				sb.append("���[�_�[�`���[�g���l�W�i�]����j" + ",");
				sb.append("���[�_�[�`���[�g���l�W�i�m�F�͈́j" + ",");
				sb.append("���t�H�[���v��������������" + ",");
				// �Z��f�f��	���r�ی��\	���t�H�[���ی���	�ۏ�p���t 	SumStock	��̌^���[����
				Iterator<String> codeLookup_Keys = this.codeLookupManager.getKeysByLookup("recommend_point_icon");
				while (codeLookup_Keys.hasNext()) {
					String codeLookup_Key = (String) codeLookup_Keys.next();
					sb.append(lookupValueWithDefault("recommend_point_icon", codeLookup_Key) + ",");
				}
				sb.append("���J�敪" + ",");
				sb.append("�X�e�[�^�X" + ",");
				sb.append("����ԍ�" + ",");
				sb.append("����" + ",");
				sb.append("�d�b�ԍ�" + ",");
				sb.append("���[���A�h���X" + ",");
				sb.append("���l�i�Ǘ��җp�j" + ",");
				sb.append("�V�K�o�^����" + ",");
				sb.append("�V�K�o�^��" + ",");
				sb.append("�ŏI�X�V����" + ",");
				sb.append("�ŏI�X�V�ҁiID�j" + ",");
				sb.append("�ŏI�X�V��");

				break;
			case TABLE_REFORM_NAME:
				sb.append("���t�H�[���v������" + ",");
				sb.append("���i" + ",");
				sb.append("���J�敪" + ",");
				sb.append("���t�H�[���ڍׁu���e:���i�v1�`N" + ",");
				sb.append("�Z�[���X�|�C���g�i�R���Z�v�g�j" + ",");
				sb.append("�H��" + ",");
				sb.append("���l" + ",");
				sb.append("���[�_�[�`���[�g���l�P�i���t�H�[�����{��j" + ",");
				sb.append("���[�_�[�`���[�g���l�Q�i���t�H�[�����{��j" + ",");
				sb.append("���[�_�[�`���[�g���l�R�i���t�H�[�����{��j" + ",");
				sb.append("���[�_�[�`���[�g���l�S�i���t�H�[�����{��j" + ",");
				sb.append("���[�_�[�`���[�g���l�T�i���t�H�[�����{��j" + ",");
				sb.append("���[�_�[�`���[�g���l�U�i���t�H�[�����{��j" + ",");
				sb.append("���[�_�[�`���[�g���l�V�i���t�H�[�����{��j" + ",");
				sb.append("���[�_�[�`���[�g���l�W�i���t�H�[�����{��j" + ",");
				// ���ڒǉ�
				sb.append("�V�K�o�^����" + ",");
				sb.append("�V�K�o�^��" + ",");
				sb.append("�ŏI�X�V����" + ",");
				sb.append("�ŏI�X�V�ҁiID�j" + ",");
				sb.append("�ŏI�X�V��");
				break;
			default :
				break;
		}
		sb.append("\r\n");
		zipWriter(sb.toString(), zos);
	}

	class HousingListRowCallbackHandler implements RowCallbackHandler {
		PanaHousingPartThumbnailProxy panaHousingManager;
		List<PanaHousing> panaHousings;
		List<AdminLoginInfo> adminLoginInfo_InUsers;
		List<MemberInfo> memberInfos;

		public HousingListRowCallbackHandler(PanaHousingPartThumbnailProxy panaHousingManager, List<MemberInfo> memberInfos,
				List<AdminLoginInfo> adminLoginInfo_InUsers, List<PanaHousing> panaHousings) {
			this.panaHousingManager = panaHousingManager;
			this.panaHousings = panaHousings;
			this.adminLoginInfo_InUsers = adminLoginInfo_InUsers;
			this.memberInfos = memberInfos;
		}

		@Override
		public void processRow(ResultSet rs) throws SQLException {
			try {
				PanaHousing panaHousing = (PanaHousing) this.panaHousingManager.searchHousingPk(rs.getString("housingInfo_sysHousingCd"), true);

				this.panaHousings.add(panaHousing);

				// �Ǘ��҃��O�C���h�c���
				AdminLoginInfo adminLoginInfo_InUser = new AdminLoginInfo();
				// �Ǘ��҃��[�U�[ID
				adminLoginInfo_InUser.setAdminUserId(rs.getString("adminLoginInfo1_adminUserId"));
				// ���[�U�[����
				adminLoginInfo_InUser.setUserName(rs.getString("adminLoginInfo1_userName"));

				this.adminLoginInfo_InUsers.add(adminLoginInfo_InUser);

				// �}�C�y�[�W������
				MemberInfo memberInfo = new MemberInfo();
				// ������i���j
				memberInfo.setMemberLname(rs.getString("memberInfo_memberLname"));
				// ������i���j
				memberInfo.setMemberFname(rs.getString("memberInfo_memberFname"));
				// ���[���A�h���X
				memberInfo.setEmail(rs.getString("memberInfo_email"));
				// �d�b�ԍ�
				memberInfo.setTel(rs.getString("memberInfo_TEL"));

				this.memberInfos.add(memberInfo);

			} catch (Exception e) {
				e.printStackTrace();
			}
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
	 * @param searchForm
	 *            ���������̊i�[�I�u�W�F�N�g
	 */
	protected void createSql(PanaHousingSearchForm searchForm, StringBuffer sbSql, List<Object> params) {
		createSelect(sbSql, params);
		createFrom(sbSql, params);
		createWhere(searchForm, sbSql, params);
		createOrderBy(sbSql, searchForm.getKeyOrderType());
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
	 * @param sbSql
	 *            ��������SQL����ǉ�����o�b�t�@
	 * @param params
	 *            SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 */
	protected void createSelect(StringBuffer sbSql, List<Object> params) {

		StringBuffer sbSelect = new StringBuffer();

		sbSelect.append(" SELECT distinct");
		// �������
		// �V�X�e������CD
		sbSelect.append(" " + TABLE_HOUSING_INFO_ALIAS + ".sys_housing_cd AS " + TABLE_HOUSING_INFO_ALIAS + "_sysHousingCd");
		// �����ԍ�
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".housing_cd AS " + TABLE_HOUSING_INFO_ALIAS + "_housingCd");
		// �\���p������
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".display_housing_name AS " + TABLE_HOUSING_INFO_ALIAS + "_displayHousingName");
		// �\���p�������ӂ肪��
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".display_housing_name_kana AS " + TABLE_HOUSING_INFO_ALIAS + "_displayHousingNameKana");
		// �����ԍ�
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".room_no AS " + TABLE_HOUSING_INFO_ALIAS + "_roomNo");
		// �V�X�e������CD
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".sys_building_cd AS " + TABLE_HOUSING_INFO_ALIAS + "_sysBuildingCd");
		// ����/���i
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".price AS " + TABLE_HOUSING_INFO_ALIAS + "_price");
		// ���t�H�[�������i�i�ŏ��j
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".price_full_min AS " + TABLE_HOUSING_INFO_ALIAS + "_priceFullMin");
		// ���t�H�[�������i�i�ő�j
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".price_full_max AS " + TABLE_HOUSING_INFO_ALIAS + "_priceFullMax");
		// �Ǘ���
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".upkeep AS " + TABLE_HOUSING_INFO_ALIAS + "_upkeep");
		// ���v��
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".common_area_fee AS " + TABLE_HOUSING_INFO_ALIAS + "_commonAreaFee");
		// �C�U�ϗ���
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".mente_fee AS " + TABLE_HOUSING_INFO_ALIAS + "_menteFee");
		// �~��
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".sec_deposit AS " + TABLE_HOUSING_INFO_ALIAS + "_secDeposit");
		// �~���P��
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".sec_deposit_crs AS " + TABLE_HOUSING_INFO_ALIAS + "_secDepositCrs");
		// �ۏ؋�
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".bond_chrg AS " + TABLE_HOUSING_INFO_ALIAS + "_bondChrg");
		// �ۏ؋��P��
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".bond_chrg_crs AS " + TABLE_HOUSING_INFO_ALIAS + "_bondChrgCrs");
		// �~������敪
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".deposit_div AS " + TABLE_HOUSING_INFO_ALIAS + "_depositDiv");
		// �~������z
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".deposit AS " + TABLE_HOUSING_INFO_ALIAS + "_deposit");
		// �~������P��
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".deposit_crs AS " + TABLE_HOUSING_INFO_ALIAS + "_depositCrs");
		// �Ԏ�CD
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".layout_cd AS " + TABLE_HOUSING_INFO_ALIAS + "_layoutCd");
		// �Ԏ�ڍ׃R�����g
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".layout_comment AS " + TABLE_HOUSING_INFO_ALIAS + "_layoutComment");
		// �����̊K��
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".floor_no AS " + TABLE_HOUSING_INFO_ALIAS + "_floorNo");
		// �����̊K���R�����g
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".floor_no_note AS " + TABLE_HOUSING_INFO_ALIAS + "_floorNoNote");
		// �y�n�ʐ�
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".land_area AS " + TABLE_HOUSING_INFO_ALIAS + "_landArea");
		// �y�n�ʐ�_�⑫
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".land_area_memo AS " + TABLE_HOUSING_INFO_ALIAS + "_landAreaMemo");
		// ��L�ʐ�
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".personal_area AS " + TABLE_HOUSING_INFO_ALIAS + "_personalArea");
		// ��L�ʐ�_�⑫
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".personal_area_memo AS " + TABLE_HOUSING_INFO_ALIAS + "_personalAreaMemo");
		// ������ԃt���O
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".movein_flg AS " + TABLE_HOUSING_INFO_ALIAS + "_moveinFlg");
		// ���ԏ�̏�
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".parking_situation AS " + TABLE_HOUSING_INFO_ALIAS + "_parkingSituation");
		// ���ԏ��̗L��
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".parking_emp_exist AS " + TABLE_HOUSING_INFO_ALIAS + "_parkingEmpExist");
		// �\���p���ԏ���
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".display_parking_info AS " + TABLE_HOUSING_INFO_ALIAS + "_displayParkingInfo");
		// ���̌���
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".window_direction AS " + TABLE_HOUSING_INFO_ALIAS + "_windowDirection");
		// �A�C�R�����
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".icon_cd AS " + TABLE_HOUSING_INFO_ALIAS + "_iconCd");
		// ��{���R�����g
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".basic_comment AS " + TABLE_HOUSING_INFO_ALIAS + "_basicComment");
		// ���t�H�[���������R�����g
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".reform_comment AS " + TABLE_HOUSING_INFO_ALIAS + "_reformComment");
		// �ŏ��k������
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".min_walking_time AS " + TABLE_HOUSING_INFO_ALIAS + "_minWalkingTime");
		// �o�^��
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".ins_date AS " + TABLE_HOUSING_INFO_ALIAS + "_insDate");
		// �o�^��
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".ins_user_id AS " + TABLE_HOUSING_INFO_ALIAS + "_insUserId");
		// �ŏI�X�V��
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".upd_date AS " + TABLE_HOUSING_INFO_ALIAS + "_updDate");
		// �ŏI�X�V��
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".upd_user_id AS " + TABLE_HOUSING_INFO_ALIAS + "_updUserId");

		// �����ڍ׏��
		// �V�X�e������CD
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".sys_housing_cd AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_sysHousingCd");
		// �p�r�n��CD
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".used_area_cd AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_usedAreaCd");
		// ����`�ԋ敪
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".transact_type_div AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_transactTypeDiv");
		// �\���p�_�����
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".display_contract_term AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_displayContractTerm");
		// �y�n����
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".land_right AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_landRight");
		// �����\�����t���O
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".movein_timing AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_moveinTiming");
		// �����\����
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".movein_timing_day AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_moveinTimingDay");
		// �����\�����R�����g
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".movein_note AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_moveinNote");
		// �\���p�����\����
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".display_movein_timing AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_displayMoveinTiming");
		// �\���p����������
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".display_movein_proviso AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_displayMoveinProviso");
		// �Ǘ��`�ԁE����
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".upkeep_type AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_upkeepType");
		// �Ǘ����
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".upkeep_corp AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_upkeepCorp");
		// �X�V��
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".renew_chrg AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_renewChrg");
		// �X�V���P��
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".renew_chrg_crs AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_renewChrgCrs");
		// �X�V����
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".renew_chrg_name AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_renewChrgName");
		// �X�V�萔��
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".renew_due AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_renewDue");
		// �X�V�萔���P��
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".renew_due_crs AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_renewDueCrs");
		// ����萔��
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".brokerage_chrg AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_brokerageChrg");
		// ����萔���P��
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".brokerage_chrg_crs AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_brokerageChrgCrs");
		// ��������
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".change_key_chrg AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_changeKeyChrg");
		// ���ۗL��
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".insur_exist AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_insurExist");
		// ���ۗ���
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".insur_chrg AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_insurChrg");
		// ���۔N��
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".insur_term AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_insurTerm");
		// ���۔F�胉���N
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".insur_lank AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_insurLank");
		// ����p
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".other_chrg AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_otherChrg");
		// �ړ���
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".contact_road AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_contactRoad");
		// �ړ�����/����
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".contact_road_dir AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_contactRoadDir");
		// �������S
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".private_road AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_privateRoad");
		// �o���R�j�[�ʐ�
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".balcony_area AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_balconyArea");
		// ���L����
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".special_instruction AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_specialInstruction");
		// �ڍ׃R�����g
		sbSelect.append(", " + TABLE_HOUSING_DTL_INFO_ALIAS + ".dtl_comment AS " + TABLE_HOUSING_DTL_INFO_ALIAS + "_dtlComment");

		// �����X�e�[�^�X���
		// �V�X�e������CD
		sbSelect.append(", " + TABLE_HOUSING_STATUS_INFO_ALIAS + ".sys_housing_cd AS " + TABLE_HOUSING_STATUS_INFO_ALIAS + "_sysHousingCd");
		// ����J�t���O
		sbSelect.append(", " + TABLE_HOUSING_STATUS_INFO_ALIAS + ".hidden_flg AS " + TABLE_HOUSING_STATUS_INFO_ALIAS + "_hiddenFlg");
		// �X�e�[�^�XCD
		sbSelect.append(", " + TABLE_HOUSING_STATUS_INFO_ALIAS + ".status_cd AS " + TABLE_HOUSING_STATUS_INFO_ALIAS + "_statusCd");
		// ���[�U�[ID
		sbSelect.append(", " + TABLE_HOUSING_STATUS_INFO_ALIAS + ".user_id AS " + TABLE_HOUSING_STATUS_INFO_ALIAS + "_userId");
		// ���l
		sbSelect.append(", " + TABLE_HOUSING_STATUS_INFO_ALIAS + ".note AS " + TABLE_HOUSING_STATUS_INFO_ALIAS + "_note");

		// �}�C�y�[�W������
		// ���[�U�[ID
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".user_id AS " + TABLE_MEMBER_INFO_ALIAS + "_userId");
		// ������i���j
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".member_lname AS " + TABLE_MEMBER_INFO_ALIAS + "_memberLname");
		// ������i���j
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".member_fname AS " + TABLE_MEMBER_INFO_ALIAS + "_memberFname");
		// ������E�J�i�i���j
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".member_lname_kana AS " + TABLE_MEMBER_INFO_ALIAS + "_memberLnameKana");
		// ������E�J�i�i���j
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".member_fname_kana AS " + TABLE_MEMBER_INFO_ALIAS + "_memberFnameKana");
		// ���[���A�h���X
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".email AS " + TABLE_MEMBER_INFO_ALIAS + "_email");
		// �������O�C���p�g�[�N��
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".cookie_login_token AS " + TABLE_MEMBER_INFO_ALIAS + "_cookieLoginToken");
		// �Z���E�X�֔ԍ�
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".zip AS " + TABLE_MEMBER_INFO_ALIAS + "_zip");
		// �Z���E�s���{��CD
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".pref_cd AS " + TABLE_MEMBER_INFO_ALIAS + "_pref_cd");
		// �Z���E�撬���Ԓn
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".address AS " + TABLE_MEMBER_INFO_ALIAS + "_address");
		// �Z���E������
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".address_other AS " + TABLE_MEMBER_INFO_ALIAS + "_address_other");
		// �d�b�ԍ�
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".TEL AS " + TABLE_MEMBER_INFO_ALIAS + "_TEL");
		// FAX
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".FAX AS " + TABLE_MEMBER_INFO_ALIAS + "_FAX");
		// �p�X���[�h
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".password AS " + TABLE_MEMBER_INFO_ALIAS + "_password");
		// ���[���z�M��]
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".mail_send_flg AS " + TABLE_MEMBER_INFO_ALIAS + "_mailSendFlg");
		// ���Z���
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".resident_flg AS " + TABLE_MEMBER_INFO_ALIAS + "_residentFlg");
		// ��]�n��E�s���{��
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".hope_pref_cd AS " + TABLE_MEMBER_INFO_ALIAS + "_hopePrefCd");
		// ��]�n��E�s�撬��
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".hope_address AS " + TABLE_MEMBER_INFO_ALIAS + "_hopeAddress");
		// �o�^�o�H
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".entry_route AS " + TABLE_MEMBER_INFO_ALIAS + "_entryRoute");
		// �v����CD
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".promo_cd AS " + TABLE_MEMBER_INFO_ALIAS + "_promoCd");
		// ������CD
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".ref_cd AS " + TABLE_MEMBER_INFO_ALIAS + "_refCd");
		// ���O�C�����s��
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".fail_cnt AS " + TABLE_MEMBER_INFO_ALIAS + "_failCnt");
		// �ŏI���O�C�����s��
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".last_fail_date AS " + TABLE_MEMBER_INFO_ALIAS + "_lastFailDate");
		// ���b�N�t���O
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".lock_flg AS " + TABLE_MEMBER_INFO_ALIAS + "_lockFlg");
		// �O�񃍃O�C����
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".pre_login AS " + TABLE_MEMBER_INFO_ALIAS + "_preLogin");
		// �ŏI���O�C����
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".last_login AS " + TABLE_MEMBER_INFO_ALIAS + "_lastLogin");
		// �o�^��
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".ins_date AS " + TABLE_MEMBER_INFO_ALIAS + "_insDate");
		// �o�^��
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".ins_user_id AS " + TABLE_MEMBER_INFO_ALIAS + "_insUserId");
		// �ŏI�X�V��
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".upd_date AS " + TABLE_MEMBER_INFO_ALIAS + "_updDate");
		// �ŏI�X�V��
		sbSelect.append(", " + TABLE_MEMBER_INFO_ALIAS + ".upd_user_id AS " + TABLE_MEMBER_INFO_ALIAS + "_updUserId");

		// ������{���
		// �V�X�e������CD
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".sys_building_cd AS " + TABLE_BUILDING_INFO_ALIAS + "_sysBuildingCd");
		// �����ԍ�
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".building_cd AS " + TABLE_BUILDING_INFO_ALIAS + "_buildingCd");
		// �������CD
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".housing_kind_cd AS " + TABLE_BUILDING_INFO_ALIAS + "_housingKindCd");
		// �����\��CD
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".struct_cd AS " + TABLE_BUILDING_INFO_ALIAS + "_structCd");
		// �\���p������
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".display_building_name AS " + TABLE_BUILDING_INFO_ALIAS + "_displayBuildingName");
		// �\���p�������ӂ肪��
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".display_building_name_kana AS " + TABLE_BUILDING_INFO_ALIAS + "_displayBuildingNameKana");
		// ���ݒn�E�X�֔ԍ�
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".zip AS " + TABLE_BUILDING_INFO_ALIAS + "_zip");
		// ���ݒn�E�s���{��CD
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".pref_cd AS " + TABLE_BUILDING_INFO_ALIAS + "_prefCd");
		// ���ݒn�E�s�撬��CD
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".address_cd AS " + TABLE_BUILDING_INFO_ALIAS + "_addressCd");
		// ���ݒn�E�s�撬����
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".address_name AS " + TABLE_BUILDING_INFO_ALIAS + "_addressName");
		// ���ݒn�E�����Ԓn
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".address_other1 AS " + TABLE_BUILDING_INFO_ALIAS + "_addressOther1");
		// ���ݒn�E���������̑�
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".address_other2 AS " + TABLE_BUILDING_INFO_ALIAS + "_addressOther2");
		// �v�H�N��
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".comp_date AS " + TABLE_BUILDING_INFO_ALIAS + "_compDate");
		// �v�H�{
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".comp_ten_days AS " + TABLE_BUILDING_INFO_ALIAS + "_compTenDays");
		// ���K��
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".total_floors AS " + TABLE_BUILDING_INFO_ALIAS + "_totalFloors");
		// �o�^��
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".ins_date AS " + TABLE_BUILDING_INFO_ALIAS + "_insDate");
		// �o�^��
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".ins_user_id AS " + TABLE_BUILDING_INFO_ALIAS + "_insUserId");
		// �ŏI�X�V��
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".upd_date AS " + TABLE_BUILDING_INFO_ALIAS + "_updDate");
		// �ŏI�X�V��
		sbSelect.append(", " + TABLE_BUILDING_INFO_ALIAS + ".upd_user_id AS " + TABLE_BUILDING_INFO_ALIAS + "_updUserId");

		// �����ڍ׏��
		// �V�X�e������CD
		sbSelect.append(", " + TABLE_BUILDING_DTL_INFO_ALIAS + ".sys_building_cd AS " + TABLE_BUILDING_DTL_INFO_ALIAS + "_sysBuildingCd");
		// �����ʐ�
		sbSelect.append(", " + TABLE_BUILDING_DTL_INFO_ALIAS + ".building_area AS " + TABLE_BUILDING_DTL_INFO_ALIAS + "_buildingArea");
		// �����ʐ�_�⑫
		sbSelect.append(", " + TABLE_BUILDING_DTL_INFO_ALIAS + ".building_area_memo AS " + TABLE_BUILDING_DTL_INFO_ALIAS + "_buildingAreaMemo");
		// ���؂���
		sbSelect.append(", " + TABLE_BUILDING_DTL_INFO_ALIAS + ".coverage AS " + TABLE_BUILDING_DTL_INFO_ALIAS + "_coverage");
		// ���؂���_�⑫
		sbSelect.append(", " + TABLE_BUILDING_DTL_INFO_ALIAS + ".coverage_memo AS " + TABLE_BUILDING_DTL_INFO_ALIAS + "_coverageMemo");
		// �e�ϗ�
		sbSelect.append(", " + TABLE_BUILDING_DTL_INFO_ALIAS + ".building_rate AS " + TABLE_BUILDING_DTL_INFO_ALIAS + "_buildingRate");
		// �e�ϗ�_�⑫
		sbSelect.append(", " + TABLE_BUILDING_DTL_INFO_ALIAS + ".building_rate_memo AS " + TABLE_BUILDING_DTL_INFO_ALIAS + "_buildingRateMemo");
		// ���ː�
		sbSelect.append(", " + TABLE_BUILDING_DTL_INFO_ALIAS + ".total_house_cnt AS " + TABLE_BUILDING_DTL_INFO_ALIAS + "_totalHouseCnt");
		// ���݌ː�
		sbSelect.append(", " + TABLE_BUILDING_DTL_INFO_ALIAS + ".lease_house_cnt AS " + TABLE_BUILDING_DTL_INFO_ALIAS + "_leaseHouseCnt");
		// �����ܓx
		sbSelect.append(", " + TABLE_BUILDING_DTL_INFO_ALIAS + ".building_latitude AS " + TABLE_BUILDING_DTL_INFO_ALIAS + "_buildingLatitude");
		// �����o�x
		sbSelect.append(", " + TABLE_BUILDING_DTL_INFO_ALIAS + ".building_longitude AS " + TABLE_BUILDING_DTL_INFO_ALIAS + "_buildingLongitude");

		// �s���{���}�X�^
		// �s���{��CD
		sbSelect.append(", " + TABLE_PREF_MST_ALIAS + ".pref_cd AS " + TABLE_PREF_MST_ALIAS + "_prefCd");
		// �s���{����
		sbSelect.append(", " + TABLE_PREF_MST_ALIAS + ".pref_name AS " + TABLE_PREF_MST_ALIAS + "_prefName");
		// �s���{�������[�}��
		sbSelect.append(", " + TABLE_PREF_MST_ALIAS + ".r_pref_name AS " + TABLE_PREF_MST_ALIAS + "_rPrefName");
		// �n��CD
		sbSelect.append(", " + TABLE_PREF_MST_ALIAS + ".area_cd AS " + TABLE_PREF_MST_ALIAS + "_areaCd");
		// �\����
		sbSelect.append(", " + TABLE_PREF_MST_ALIAS + ".sort_order AS " + TABLE_PREF_MST_ALIAS + "_sortOrder");
		// �o�^��
		sbSelect.append(", " + TABLE_PREF_MST_ALIAS + ".ins_date AS " + TABLE_PREF_MST_ALIAS + "_insDate");
		// �o�^��
		sbSelect.append(", " + TABLE_PREF_MST_ALIAS + ".ins_user_id AS " + TABLE_PREF_MST_ALIAS + "_insUserId");
		// �ŏI�X�V��
		sbSelect.append(", " + TABLE_PREF_MST_ALIAS + ".upd_date AS " + TABLE_PREF_MST_ALIAS + "_updDate");
		// �ŏI�X�V��
		sbSelect.append(", " + TABLE_PREF_MST_ALIAS + ".upd_user_id AS " + TABLE_PREF_MST_ALIAS + "_updUserId");

		// �Ǘ��҃��O�C���h�c���
		// �Ǘ��҃��[�U�[ID
		sbSelect.append(", " + TABLE_ADMIN_LOGIN_INFO1_ALIAS+ ".admin_user_id AS " + TABLE_ADMIN_LOGIN_INFO1_ALIAS + "_adminUserId");
		// ���[�U�[����
		sbSelect.append(", " + TABLE_ADMIN_LOGIN_INFO1_ALIAS + ".user_name AS " + TABLE_ADMIN_LOGIN_INFO1_ALIAS + "_userName");

		// �Ǘ��҃��O�C���h�c���
		// �Ǘ��҃��[�U�[ID
		sbSelect.append(", " + TABLE_ADMIN_LOGIN_INFO2_ALIAS + ".admin_user_id AS " + TABLE_ADMIN_LOGIN_INFO2_ALIAS + "_adminUserId");
		// ���[�U�[����
		sbSelect.append(", " + TABLE_ADMIN_LOGIN_INFO2_ALIAS + ".user_name AS " + TABLE_ADMIN_LOGIN_INFO2_ALIAS + "_userName");

		sbSql.append(sbSelect.toString());

	}

	/**
	 * From��̕�����𐶐�����<br/>
	 * �g�p����e�[�u�����A�ʖ��͒萔���`���ċL�q���邱��<br/>
	 * �i�R�[�f�B���O�ȑf���A�^�C�v�~�X�h�~�j<br/>
	 * <br/>
	 *
	 * @param sbSql
	 *            ��������SQL����ǉ�����o�b�t�@
	 * @param params
	 *            SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 */
	protected void createFrom(StringBuffer sbSql, List<Object> params) {

		StringBuffer sbFrom = new StringBuffer();

		sbFrom.append(" FROM ");
		sbFrom.append(" " + TABLE_HOUSING_INFO_NAME + " " + TABLE_HOUSING_INFO_ALIAS);
		sbFrom.append(" LEFT OUTER JOIN " + TABLE_HOUSING_STATUS_INFO_NAME + " " + TABLE_HOUSING_STATUS_INFO_ALIAS + " ON " + TABLE_HOUSING_INFO_ALIAS + ".sys_housing_cd = " + TABLE_HOUSING_STATUS_INFO_ALIAS + ".sys_housing_cd");
		sbFrom.append(" INNER JOIN " + TABLE_BUILDING_INFO_NAME + " " + TABLE_BUILDING_INFO_ALIAS + " ON " + TABLE_HOUSING_INFO_ALIAS + ".sys_building_cd = " + TABLE_BUILDING_INFO_ALIAS + ".sys_building_cd");
		sbFrom.append(" LEFT OUTER JOIN " + TABLE_BUILDING_DTL_INFO_NAME + " " + TABLE_BUILDING_DTL_INFO_ALIAS + " ON " + TABLE_HOUSING_INFO_ALIAS + ".sys_building_cd = " + TABLE_BUILDING_DTL_INFO_ALIAS + ".sys_building_cd");
		sbFrom.append(" LEFT OUTER JOIN " + TABLE_PREF_MST_NAME + " " + TABLE_PREF_MST_ALIAS + " ON " + TABLE_BUILDING_INFO_ALIAS + ".pref_cd = " + TABLE_PREF_MST_ALIAS + ".pref_cd");
		sbFrom.append(" LEFT OUTER JOIN " + TABLE_HOUSING_DTL_INFO_NAME + " " + TABLE_HOUSING_DTL_INFO_ALIAS + " ON " + TABLE_HOUSING_INFO_ALIAS + ".sys_housing_cd = " + TABLE_HOUSING_DTL_INFO_ALIAS + ".sys_housing_cd");
		sbFrom.append(" LEFT OUTER JOIN " + TABLE_MEMBER_INFO_NAME + " " + TABLE_MEMBER_INFO_ALIAS + " ON " + TABLE_HOUSING_STATUS_INFO_ALIAS + ".user_id = " + TABLE_MEMBER_INFO_ALIAS + ".user_id");
		sbFrom.append(" LEFT OUTER JOIN " + TABLE_ADMIN_LOGIN_INFO_NAME + " " + TABLE_ADMIN_LOGIN_INFO1_ALIAS + " ON " + TABLE_HOUSING_INFO_ALIAS + ".ins_user_id = " + TABLE_ADMIN_LOGIN_INFO1_ALIAS + ".admin_user_id");
		sbFrom.append(" LEFT OUTER JOIN " + TABLE_ADMIN_LOGIN_INFO_NAME + " " + TABLE_ADMIN_LOGIN_INFO2_ALIAS + " ON " + TABLE_HOUSING_INFO_ALIAS + ".upd_user_id = " + TABLE_ADMIN_LOGIN_INFO2_ALIAS + ".admin_user_id");
//		sbFrom.append(" LEFT OUTER JOIN " + TABLE_REFORM_PLAN_NAME + " " + TABLE_REFORM_PLAN_ALIAS + " ON " + TABLE_HOUSING_INFO_ALIAS + ".sys_housing_cd = " + TABLE_REFORM_PLAN_ALIAS + ".sys_housing_cd");

		sbSql.append(sbFrom.toString());

	}

	/**
	 * Where��̕�����𐶐�����<br/>
	 * �����������ƂɁA�uAND�`�v�ƂȂ镶����𐶐����郁�\�b�h���Ăяo���Ă���<br/>
	 * <br/>
	 *
	 * @param sbSql
	 *            ��������SQL����ǉ�����o�b�t�@
	 * @param params
	 *            SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param searchForm
	 *            ���������̊i�[�I�u�W�F�N�g
	 */
	protected void createWhere(PanaHousingSearchForm searchForm,
			StringBuffer sbSql, List<Object> params) {

		StringBuffer sbWhere = new StringBuffer();

		// �����ԍ�
		createWhereAndHousingCd(sbWhere, params, searchForm.getKeyHousingCd());
		// ������
		createWhereAndDisplayHousingName(sbWhere, params,
				searchForm.getKeyDisplayHousingName());
		// �s���{��CD
		createWhereAndPrefCd(sbWhere, params, searchForm.getKeyPrefCd());

		// �o�^�J�n���̌��������������ǉ�����
		createWhereAndInsDateStart(sbWhere, params,
				((PanaHousingSearchForm) searchForm).getKeyInsDateStart());
		// �o�^�I�����̌��������������ǉ�����
		createWhereAndInsDateEnd(sbWhere, params,
				((PanaHousingSearchForm) searchForm).getKeyInsDateEnd());
		// �X�V�����̌��������������ǉ�����
		createWhereAndUpdDate(sbWhere, params,
				((PanaHousingSearchForm) searchForm).getKeyUpdDate());
		// ����ԍ��̌��������������ǉ�����
		createWhereAndUserId(sbWhere, params,
				((PanaHousingSearchForm) searchForm).getKeyUserId());
		// ���J�敪�̌��������������ǉ�����
		createWhereAndHiddenFlg(sbWhere, params,
				((PanaHousingSearchForm) searchForm).getKeyHiddenFlg());
		// �X�e�[�^�X�̌��������������ǉ�����
		createWhereAndStatusCd(sbWhere, params,
				((PanaHousingSearchForm) searchForm).getKeyStatusCd());

		// �������
		createWhereAndHousingKindCd(sbWhere, params,
				searchForm.getKeyHousingKindCd());
		// �s�撬��CD
		createWhereAndAddressCd(sbWhere, params, searchForm.getKeyAddressCd());
		// ����CD�A�wCD
		createWhereAndRouteCdStationCd(sbWhere, params,
				searchForm.getKeyRouteCd(), searchForm.getKeyStationCd(),
				searchForm.getKeyPartSrchCd());
		// �y�n�ʐ�
		createWhereAndLandAreaLower(sbWhere, params,
				searchForm.getKeyLandAreaLower());
		createWhereAndLandAreaUpper(sbWhere, params,
				searchForm.getKeyLandAreaUpper());
		// ��L�ʐ�
		createWhereAndPersonalAreaLower(sbWhere, params,
				searchForm.getKeyPersonalAreaLower());
		createWhereAndPersonalAreaUpper(sbWhere, params,
				searchForm.getKeyPersonalAreaUpper());
		// �Ԏ��CD
		createWhereAndLayoutCd(sbWhere, params, searchForm.getKeyLayoutCd());
		// �����ʐ�
		createWhereAndBuildingAreaLower(sbWhere, params,
				searchForm.getKeyBuildingAreaLower());
		createWhereAndBuildingAreaUpper(sbWhere, params,
				searchForm.getKeyBuildingAreaUpper());

		PanaHousingSearchForm searchFormChild = (PanaHousingSearchForm) searchForm;

		// �u���t�H�[�����i���݂Ō�������v�̏�Ԃ̔��f�t���O
		boolean priceFlg = false;

		if ("on".equals(searchFormChild.getReformPriceCheck())) {
			priceFlg = true;
		}

		// �\�Z
		createWhereAndPrice(sbWhere, params, searchForm.getKeyPriceLower(),
				searchForm.getKeyPriceUpper(), priceFlg);

		// �z�N��
		createWhereAndCompDate(sbWhere, params,
				searchFormChild.getKeyCompDate());

		// �������߂̃|�C���g
		createWhereAndIconCd(sbWhere, params, searchFormChild.getKeyIconCd());

		// ���n����
		createWhereAndMovein(sbWhere, params, searchFormChild.getMoveinTiming());

		// ����������
		createWhereAndPartSrchCd(sbWhere, params, searchForm);
//		createWhereAndReformType(sbWhere, params, searchForm.getKeyReformType());

		// �ŏ��́uAND�v���폜���āu WHERE �v��t����
		if (sbWhere.length() > 0) {
			String strAnd = "AND";
			sbWhere.delete(sbWhere.indexOf(strAnd), sbWhere.indexOf(strAnd)
					+ strAnd.length());
			sbWhere.insert(0, " WHERE ");
		}

		sbSql.append(sbWhere.toString());

	}

//	/**
//	 * 
//	 * @param sbWhere
//	 * @param params
//	 * @param reformType
//	 */
//	protected void createWhereAndReformType(StringBuffer sbWhere,
//            List<Object> params, String reformType) {
//	    if (!StringValidateUtil.isEmpty(reformType)) {
//            sbWhere.append(" AND " + TABLE_REFORM_PLAN_ALIAS
//                    + ".type = ? ");
//            params.add(reformType);
//        }
//	}
	/**
	 * �����ԍ��̌��������������ǉ�����<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            ��������SQL����ǉ�����o�b�t�@
	 * @param params
	 *            SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param csHousingCd
	 *            �����ΏۂƂ��镨���ԍ��i�J���}��؂�ŕ����̎󂯎���z��j
	 */
	protected void createWhereAndHousingCd(StringBuffer sbWhere,
			List<Object> params, String csHousingCd) {

		if (!StringValidateUtil.isEmpty(csHousingCd)) {

			StringBuffer sqlIn = new StringBuffer();
			String[] arrHousingCd = csHousingCd.split(",");
			for (int i = 0; i < arrHousingCd.length; i++) {
				if (arrHousingCd[i].trim().length() > 0) {
					sqlIn.append(",? ");
					params.add(arrHousingCd[i]);
				}
			}
			if (sqlIn.length() > 0) {
				// �ŏ��̃J���}���폜����
				String strComma = ",";
				sqlIn.delete(sqlIn.indexOf(strComma), sqlIn.indexOf(strComma)
						+ strComma.length());
				sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
						+ ".housing_cd IN (" + sqlIn.toString() + ")");
			}
		}

	}

	/**
	 * �\���p�������̌��������������ǉ�����<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            ��������SQL����ǉ�����o�b�t�@
	 * @param params
	 *            SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param displayHousingName
	 *            �����ΏۂƂ���\���p�������i������v�j
	 */
	protected void createWhereAndDisplayHousingName(StringBuffer sbWhere,
			List<Object> params, String displayHousingName) {

		if (!StringValidateUtil.isEmpty(displayHousingName)) {
			sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
					+ ".display_housing_name LIKE ? ");
			params.add("%" + displayHousingName + "%");
		}

	}

	/**
	 * �s���{��CD�̌��������������ǉ�����<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            ��������SQL����ǉ�����o�b�t�@
	 * @param params
	 *            SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param csPrefCd
	 *            �����ΏۂƂ���s���{��CD�̃p�����^�i�J���}��؂�ŕ����̎󂯎���z��j
	 */
	protected void createWhereAndPrefCd(StringBuffer sbWhere,
			List<Object> params, String csPrefCd) {

		if (!StringValidateUtil.isEmpty(csPrefCd)) {

			StringBuffer sqlIn = new StringBuffer();
			String[] arrPrefCd = csPrefCd.split(",");
			for (int i = 0; i < arrPrefCd.length; i++) {
				if (arrPrefCd[i].trim().length() > 0) {
					sqlIn.append(",? ");
					params.add(arrPrefCd[i]);
				}
			}
			if (sqlIn.length() > 0) {
				// �ŏ��̃J���}���폜����
				String strComma = ",";
				sqlIn.delete(sqlIn.indexOf(strComma), sqlIn.indexOf(strComma)
						+ strComma.length());
				sbWhere.append(" AND " + TABLE_BUILDING_INFO_ALIAS
						+ ".pref_cd IN (" + sqlIn.toString() + ")");
			}
		}

	}

	/**
	 * �o�^�J�n���̌��������������ǉ�����<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            ��������SQL����ǉ�����o�b�t�@
	 * @param params
	 *            SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param csInsDateStart
	 *            �����ΏۂƂ���o�^�J�n��
	 */
	protected void createWhereAndInsDateStart(StringBuffer sbWhere,
			List<Object> params, String csInsDateStart) {

		if (!StringValidateUtil.isEmpty(csInsDateStart)) {
			sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
					+ ".ins_date >= ? ");
			params.add(csInsDateStart + " 00:00:00");
		}

	}

	/**
	 * �o�^�I�����̌��������������ǉ�����<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            ��������SQL����ǉ�����o�b�t�@
	 * @param params
	 *            SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param csInsDateEnd
	 *            �����ΏۂƂ���o�^�I����
	 */
	protected void createWhereAndInsDateEnd(StringBuffer sbWhere,
			List<Object> params, String csInsDateEnd) {

		if (!StringValidateUtil.isEmpty(csInsDateEnd)) {
			sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
					+ ".ins_date <= ? ");
			params.add(csInsDateEnd + " 23:59:59");
		}
	}

	/**
	 * �X�V�����̌��������������ǉ�����<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            ��������SQL����ǉ�����o�b�t�@
	 * @param params
	 *            SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param csUpdDate
	 *            �����ΏۂƂ���X�V����
	 */
	protected void createWhereAndUpdDate(StringBuffer sbWhere,
			List<Object> params, String csUpdDate) {

		if (!StringValidateUtil.isEmpty(csUpdDate)) {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy/MM/dd HH:mm:ss");
			if ("02".equals(csUpdDate)) {
				sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
						+ ".upd_date <= ? ");
				params.add(format.format(cal.getTime()));
				cal.add(Calendar.DATE, -7);
				sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
						+ ".upd_date >= ? ");
				params.add(format.format(cal.getTime()));
			} else if ("03".equals(csUpdDate)) {
				cal.add(Calendar.DATE, -7);
				sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
						+ ".upd_date <= ? ");
				params.add(format.format(cal.getTime()));
				cal.add(Calendar.DATE, -7);
				sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
						+ ".upd_date >= ? ");
				params.add(format.format(cal.getTime()));
			} else if ("04".equals(csUpdDate)) {
				cal.add(Calendar.DATE, -14);
				sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
						+ ".upd_date <= ? ");
				params.add(format.format(cal.getTime()));
			}
		}
	}

	/**
	 * ����ԍ��̌��������������ǉ�����<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            ��������SQL����ǉ�����o�b�t�@
	 * @param params
	 *            SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param csUserId
	 *            �����ΏۂƂ������ԍ�
	 */
	protected void createWhereAndUserId(StringBuffer sbWhere,
			List<Object> params, String csUserId) {

		if (!StringValidateUtil.isEmpty(csUserId)) {
			sbWhere.append(" AND " + TABLE_HOUSING_STATUS_INFO_ALIAS
					+ ".user_id LIKE ? ");
			params.add("%" + csUserId + "%");
		}
	}

	/**
	 * ���J�敪�̌��������������ǉ�����<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            ��������SQL����ǉ�����o�b�t�@
	 * @param params
	 *            SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param csHiddenFlg
	 *            �����ΏۂƂ�����J�敪
	 */
	protected void createWhereAndHiddenFlg(StringBuffer sbWhere,
			List<Object> params, String csHiddenFlg) {

		if (!StringValidateUtil.isEmpty(csHiddenFlg)) {
			sbWhere.append(" AND " + TABLE_HOUSING_STATUS_INFO_ALIAS
					+ ".hidden_flg = ? ");
			params.add(csHiddenFlg);
		}
	}

	/**
	 * �X�e�[�^�X�̌��������������ǉ�����<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            ��������SQL����ǉ�����o�b�t�@
	 * @param params
	 *            SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param csStatusCd
	 *            �����ΏۂƂ���X�e�[�^�X
	 */
	protected void createWhereAndStatusCd(StringBuffer sbWhere,
			List<Object> params, String csStatusCd) {

		if (!StringValidateUtil.isEmpty(csStatusCd)) {
			sbWhere.append(" AND " + TABLE_HOUSING_STATUS_INFO_ALIAS
					+ ".status_cd = ? ");
			params.add(csStatusCd);
		}
	}

	/**
	 * �s�撬��CD�̌��������������ǉ�����<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            ��������SQL����ǉ�����o�b�t�@
	 * @param params
	 *            SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param csAaddressCd
	 *            �����ΏۂƂ���s�撬��CD�̃p�����^�i�J���}��؂�ŕ����̎󂯎���z��j
	 */
	protected void createWhereAndAddressCd(StringBuffer sbWhere,
			List<Object> params, String csAaddressCd) {

		if (!StringValidateUtil.isEmpty(csAaddressCd)) {

			StringBuffer sqlIn = new StringBuffer();
			String[] addressCds = csAaddressCd.split(",");
			for (int i = 0; i < addressCds.length; i++) {
				if (addressCds[i].trim().length() > 0) {
					sqlIn.append(",? ");
					params.add(addressCds[i]);
				}
			}
			if (sqlIn.length() > 0) {
				// �ŏ��̃J���}���폜����
				String strComma = ",";
				sqlIn.delete(sqlIn.indexOf(strComma), sqlIn.indexOf(strComma)
						+ strComma.length());
				sbWhere.append(" AND " + TABLE_BUILDING_INFO_ALIAS
						+ ".address_cd IN (" + sqlIn.toString() + ")");
			}
		}

	}

	/**
	 * �H��CD�ƉwCD�̌��������������ǉ�����<br/>
	 * �wCD�̌����͘H��CD�������ɐݒ肳��Ă���K�v������B<br/>
	 * �܂��A�����������ɉw�k�����ԁA�o�X�k�����Ԃ��܂܂��ꍇ�A������l������</br> <br/>
	 *
	 * @param sbWhere
	 *            ��������SQL����ǉ�����o�b�t�@
	 * @param params
	 *            SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param csRouteCd
	 *            �����ΏۂƂ���H��CD�̃p�����^�i�J���}��؂�ŕ����̎󂯎���z��j
	 * @param csStationCd
	 *            �����ΏۂƂ���wCD�̃p�����^�i�J���}��؂�ŕ����̎󂯎���z��j
	 * @param csPartSrchCd
	 *            �����ΏۂƂ��邱��������CD�̃p�����^�i�J���}��؂�ŕ����̎󂯎���z��j
	 */
	protected void createWhereAndRouteCdStationCd(StringBuffer sbWhere,
			List<Object> params, String csRouteCd, String csStationCd,
			String csPartSrchCd) {

		if (!StringValidateUtil.isEmpty(csRouteCd)) {

			// �H��CD
			List<Object> pramRoute = new ArrayList<>();
			StringBuffer sqlRouteIn = new StringBuffer();
			String[] arrRouteCd = csRouteCd.split(",");
			for (int i = 0; i < arrRouteCd.length; i++) {
				if (arrRouteCd[i].trim().length() > 0) {
					sqlRouteIn.append(",? ");
					pramRoute.add(arrRouteCd[i]);
				}
			}
			params.addAll(pramRoute);

			if (sqlRouteIn.length() > 0) {

				sqlRouteIn.delete(0, 1);
				sbWhere.append(" AND EXISTS( ");
				sbWhere.append(" SELECT 1 FROM building_station_info bsi");
				sbWhere.append(" INNER JOIN station_route_info sri ON bsi.station_cd = sri.station_cd and bsi.default_route_cd = sri.route_cd ");
				sbWhere.append(" INNER JOIN station_mst sm ON sri.station_cd = sm.station_cd ");
				sbWhere.append(" INNER JOIN route_mst rm ON sri.route_cd = rm.route_cd ");
				sbWhere.append(" WHERE rm.route_cd IN ("
						+ sqlRouteIn.toString() + ") ");

				if (!StringValidateUtil.isEmpty(csStationCd)) {

					// �wCD�i����CD�̏��������邱�Ƃ��O��ƂȂ�j
					List<Object> pramStation = new ArrayList<>();
					StringBuffer sqlStationIn = new StringBuffer();
					String[] arrStationCd = csStationCd.split(",");
					for (int i = 0; i < arrStationCd.length; i++) {
						if (arrStationCd[i].trim().length() > 0) {
							sqlStationIn.append(",? ");
							pramStation.add(arrStationCd[i]);
						}
					}
					params.addAll(pramStation);
					if (sqlStationIn.length() > 0) {
						// �ŏ��̃J���}���폜����
						String strComma = ",";
						sqlStationIn.delete(
								sqlStationIn.indexOf(strComma),
								sqlStationIn.indexOf(strComma)
										+ strComma.length());
						sbWhere.append(" AND sm.station_cd IN ("
								+ sqlStationIn.toString() + ")");
					}

				}

				// ����������(�k������)���w�肳�ꂽ�ꍇ�A�����ōi�荞�݁@---------------------------
				if (!StringValidateUtil.isEmpty(csPartSrchCd)) {

					String[] arrPartSrchCd = csPartSrchCd.split(",");
					StringBuffer sqlPartWhere = new StringBuffer();

					for (int i = 0; i < arrPartSrchCd.length; i++) {
						if (arrPartSrchCd[i].trim().length() > 0) {
							// �k������ OR����
							if (orPartSrchWarktime
									.containsKey(arrPartSrchCd[i])) {
								if (arrPartSrchCd[i].startsWith("B")) {
									// �o�X�k������
									sqlPartWhere
											.append(" OR bsi.time_from_bus_stop <= "
													+ orPartSrchWarktime
															.get(arrPartSrchCd[i]));
								} else if (arrPartSrchCd[i].startsWith("S")) {
									// �w�k������
									sqlPartWhere
											.append(" OR bsi.time_from_station <= "
													+ orPartSrchWarktime
															.get(arrPartSrchCd[i]));
								}
							}
						}
					}

					if (sqlPartWhere.length() > 0) {
						// �ŏ��́uOR�v���폜
						String strOr = "OR";
						sqlPartWhere.delete(sqlPartWhere.indexOf(strOr),
								sqlPartWhere.indexOf(strOr) + strOr.length());
						// AND�Őڑ�
						sbWhere.append(" AND (" + sqlPartWhere.toString() + ")");
					}
				}
				// -----------------------------------------------------------------------------------

				sbWhere.append(" AND bsi.sys_building_cd = "
						+ TABLE_BUILDING_INFO_ALIAS + ".sys_building_cd)");
			}
		}

	}

	/**
	 * �y�n�ʐρE�����̌��������������ǉ�����<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            ��������SQL����ǉ�����o�b�t�@
	 * @param params
	 *            SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param landAreaLower
	 *            �����ΏۂƂ���y�n�ʐρE�����̃p�����^
	 */
	protected void createWhereAndLandAreaLower(StringBuffer sbWhere,
			List<Object> params, BigDecimal landAreaLower) {

		if (landAreaLower != null) {
			sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
					+ ".land_area >= ? ");
			params.add(landAreaLower);
		}

	}

	/**
	 * �y�n�ʐρE����̌��������������ǉ�����<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            ��������SQL����ǉ�����o�b�t�@
	 * @param params
	 *            SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param landAreaUpper
	 *            �����ΏۂƂ���y�n�ʐρE����̃p�����^
	 */
	protected void createWhereAndLandAreaUpper(StringBuffer sbWhere,
			List<Object> params, BigDecimal landAreaUpper) {

		if (landAreaUpper != null) {
			sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
					+ ".land_area <= ? ");
			params.add(landAreaUpper);
		}

	}

	/**
	 * ��L�ʐρE�����̌��������������ǉ�����<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            ��������SQL����ǉ�����o�b�t�@
	 * @param params
	 *            SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param personalAreaLower
	 *            �����ΏۂƂ����L�ʐρE�����̃p�����^
	 */
	protected void createWhereAndPersonalAreaLower(StringBuffer sbWhere,
			List<Object> params, BigDecimal personalAreaLower) {

		if (personalAreaLower != null) {
			sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
					+ ".personal_area >= ? ");
			params.add(personalAreaLower);
		}

	}

	/**
	 * ��L�ʐρE����̌��������������ǉ�����<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            ��������SQL����ǉ�����o�b�t�@
	 * @param params
	 *            SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param personalAreaUpper
	 *            �����ΏۂƂ����L�ʐρE����̃p�����^
	 */
	protected void createWhereAndPersonalAreaUpper(StringBuffer sbWhere,
			List<Object> params, BigDecimal personalAreaUpper) {

		if (personalAreaUpper != null) {
			sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
					+ ".personal_area <= ? ");
			params.add(personalAreaUpper);
		}

	}

	/**
	 * �Ԏ��CD�̌��������������ǉ�����<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            ��������SQL����ǉ�����o�b�t�@
	 * @param params
	 *            SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param csLayoutCd
	 *            �����ΏۂƂ���Ԏ��CD�̃p�����^�i�J���}��؂�ŕ����̎󂯎���z��j
	 */
	protected void createWhereAndLayoutCd(StringBuffer sbWhere,
			List<Object> params, String csLayoutCd) {

		if (!StringValidateUtil.isEmpty(csLayoutCd)) {

			StringBuffer sqlIn = new StringBuffer();
			String[] arrLayoutCd = csLayoutCd.split(",");
			for (int i = 0; i < arrLayoutCd.length; i++) {
				if (arrLayoutCd[i].trim().length() > 0) {
					sqlIn.append(",? ");
					params.add(arrLayoutCd[i]);
				}
			}
			if (sqlIn.length() > 0) {
				// �ŏ��̃J���}���폜����
				String strComma = ",";
				sqlIn.delete(sqlIn.indexOf(strComma), sqlIn.indexOf(strComma)
						+ strComma.length());
				sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
						+ ".layout_cd IN (" + sqlIn.toString() + ")");
			}
		}

	}

	/**
	 * �����ʐρE�����̌��������������ǉ�����<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            ��������SQL����ǉ�����o�b�t�@
	 * @param params
	 *            SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param buildingAreaLower
	 *            �����ΏۂƂ��錚���ʐρE�����̃p�����^
	 */
	protected void createWhereAndBuildingAreaLower(StringBuffer sbWhere,
			List<Object> params, BigDecimal buildingAreaLower) {

		if (buildingAreaLower != null) {
			sbWhere.append(" AND " + TABLE_BUILDING_DTL_INFO_ALIAS
					+ ".building_area >= ? ");
			params.add(buildingAreaLower);
		}

	}

	/**
	 * �����ʐρE����̌��������������ǉ�����<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            ��������SQL����ǉ�����o�b�t�@
	 * @param params
	 *            SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param buildingAreaUpper
	 *            �����ΏۂƂ��錚���ʐρE����̃p�����^
	 */
	protected void createWhereAndBuildingAreaUpper(StringBuffer sbWhere,
			List<Object> params, BigDecimal buildingAreaUpper) {

		if (buildingAreaUpper != null) {
			sbWhere.append(" AND " + TABLE_BUILDING_DTL_INFO_ALIAS
					+ ".building_area <= ? ");
			params.add(buildingAreaUpper);
		}

	}

	/**
	 * ����������CD�̌��������������ǉ�����<br/>
	 * <br/>
	 * orPartSrchList�ŃO���[�s���O���ꂽ����������CD���ƁA<br/>
	 * ���͒�`�Ɋ܂܂�Ȃ�����������CD�ŁAEXISTS�����𐶐�����B<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            ��������SQL����ǉ�����o�b�t�@
	 * @param params
	 *            SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param searchForm
	 *            �����ΏۂƂ�������̃p�����^
	 */
	protected void createWhereAndPartSrchCd(StringBuffer sbWhere,
			List<Object> params, PanaHousingSearchForm searchForm) {

		// �w�k��
		if (!StringValidateUtil.isEmpty(searchForm.getPartSrchCdWalkArray()) && !"999".equals(searchForm.getPartSrchCdWalkArray())) {
			String partSrchCdWalkArray = "";
			if(!"999".equals(searchForm.getPartSrchCdWalkArray())){
				partSrchCdWalkArray = searchForm.getPartSrchCdWalkArray();
			}
			sbWhere.append(" AND EXISTS(SELECT 1 FROM housing_part_info "
					+ " WHERE housingInfo.sys_housing_cd = housing_part_info.sys_housing_cd "
					+ " AND housing_part_info.part_srch_cd = ?) ");

			params.add(partSrchCdWalkArray);

		}
		// �����摜���A��������
		if (!StringValidateUtil.isEmpty(searchForm.getPartSrchCdArray())) {

			String[] partSrchCd = searchForm.getPartSrchCdArray().split(",");

			for (int i = 0; i < partSrchCd.length; i++) {
				sbWhere.append(" AND EXISTS(SELECT 1 FROM housing_part_info "
						+ " WHERE housingInfo.sys_housing_cd = housing_part_info.sys_housing_cd "
						+ " AND housing_part_info.part_srch_cd = ?) ");

				params.add(partSrchCd[i]);
			}
		}
		// ���݊K��
		if (!StringValidateUtil.isEmpty(searchForm.getPartSrchCdFloorArray())) {

			String[] partSrchCdFloor = searchForm.getPartSrchCdFloorArray().split(
					",");

			for (int i = 0; i < partSrchCdFloor.length; i++) {
				if(i==0){
					sbWhere.append(" AND ( ");
				}else{
					sbWhere.append(" OR ");
				}

				sbWhere.append(" EXISTS(SELECT 1 FROM housing_part_info "
						+ " WHERE housingInfo.sys_housing_cd = housing_part_info.sys_housing_cd "
						+ " AND housing_part_info.part_srch_cd = ?) ");

				params.add(partSrchCdFloor[i]);
			}
			sbWhere.append(" ) ");
		}

	}

	/**
	 * ������ނ̌��������������ǉ�����<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            ��������SQL����ǉ�����o�b�t�@
	 * @param params
	 *            SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param csHousingKindCd
	 *            �����ΏۂƂ��镨�����
	 */
	protected void createWhereAndHousingKindCd(StringBuffer sbWhere,
			List<Object> params, String csHousingKindCd) {

		if (!StringValidateUtil.isEmpty(csHousingKindCd)) {
			sbWhere.append(" AND " + TABLE_BUILDING_INFO_ALIAS
					+ ".housing_kind_cd = ? ");
			params.add(csHousingKindCd);

		}
	}

	/**
	 * ����/���i�̌��������������ǉ�����<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            ��������SQL����ǉ�����o�b�t�@
	 * @param params
	 *            SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param priceLower
	 *            �����ΏۂƂ������/���i�E�����̃p�����^
	 * @param priceUpper
	 *            �����ΏۂƂ������/���i�E����̃p�����^
	 */
	protected void createWhereAndPrice(StringBuffer sbWhere,
			List<Object> params, Long priceLower, Long priceUpper,
			boolean priceFlg) {

		// �u���t�H�[�����i���݂Ō�������v���`�F�b�N�I�t�̏ꍇ
		if (!priceFlg) {
			if (priceLower != null) {
				sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
						+ ".price >= ? ");
				params.add(priceLower);
			}

			if (priceUpper != null) {
				sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
						+ ".price <= ? ");
				params.add(priceUpper);
			}

			// �u���t�H�[�����i���݂Ō�������v���`�F�b�N�I���̏ꍇ
		} else {

			sbWhere.append(" AND (EXISTS(SELECT 1 FROM reform_plan  WHERE " + TABLE_HOUSING_INFO_ALIAS + ".sys_housing_cd = reform_plan.sys_housing_cd  AND reform_plan.hidden_flg = '0' ");

			if (priceLower != null) {
				sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
						+ ".price + reform_plan.plan_price  >= ? ");
				params.add(priceLower);
			}

			if (priceUpper != null) {
				sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
						+ ".price + reform_plan.plan_price <= ? ");
				params.add(priceUpper);
			}

			sbWhere.append(") OR (NOT EXISTS(SELECT 1 FROM reform_plan  WHERE " + TABLE_HOUSING_INFO_ALIAS + ".sys_housing_cd = reform_plan.sys_housing_cd  AND reform_plan.hidden_flg = '0') ");

			if (priceLower != null) {
				sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
						+ ".price  >= ? ");
				params.add(priceLower);
			}

			if (priceUpper != null) {
				sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS
						+ ".price <= ? ");
				params.add(priceUpper);
			}

			sbWhere.append(" ))");

		}

	}

	/**
	 * �z�N���̌��������������ǉ�����<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            ��������SQL����ǉ�����o�b�t�@
	 * @param params
	 *            SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param compDate
	 *            �����ΏۂƂ���z�N���̃p�����^
	 */
	protected void createWhereAndCompDate(StringBuffer sbWhere,
			List<Object> params, String compDate) {

		if (!StringUtils.isEmpty(compDate) && !"999".equals(compDate)) {
			sbWhere.append(" AND PERIOD_DIFF(EXTRACT(YEAR_MONTH FROM CURDATE()),EXTRACT(YEAR_MONTH FROM "
					+ TABLE_BUILDING_INFO_ALIAS + ".comp_date)) <= ? * 12");
			params.add(compDate);
		}
	}

	/**
	 * �������߂̃|�C���g�̌��������������ǉ�����<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            ��������SQL����ǉ�����o�b�t�@
	 * @param params
	 *            SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param iconCd
	 *            �����ΏۂƂ��邨�����߂̃|�C���g�̃p�����^
	 */
	protected void createWhereAndIconCd(StringBuffer sbWhere,
			List<Object> params, String iconCd) {

		// �����摜���A��������
		if(!StringUtils.isEmpty(iconCd)){

			String[] arrIconCd = iconCd.split(",");

			if (iconCd != null && arrIconCd.length > 0) {

				for (int i = 0; i < arrIconCd.length; i++) {
					sbWhere.append(" AND EXISTS(SELECT 1 FROM housing_part_info "
							+ " WHERE housingInfo.sys_housing_cd = housing_part_info.sys_housing_cd "
							+ " AND housing_part_info.part_srch_cd = ?) ");

					params.add(arrIconCd[i]);
				}
			}
		}

	}

	/**
	 * ���n�����̌��������������ǉ�����<br/>
	 * <br/>
	 *
	 * @param sbWhere
	 *            ��������SQL����ǉ�����o�b�t�@
	 * @param params
	 *            SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param iconCd
	 *            �����Ώۂƈ��n�����̃p�����^
	 */
	protected void createWhereAndMovein(StringBuffer sbWhere,
			List<Object> params, String movein) {

		if (!StringUtils.isEmpty(movein)) {
			sbWhere.append(" AND housingDtlInfo.movein_timing = ?");
			params.add(movein);
		}
	}

	/**
	 * Order By��𐶐�����<br/>
	 *
	 * @param sbSql
	 *            ��������SQL����ǉ�����o�b�t�@
	 * @param keyOrder
	 *            �����L�[
	 */
	protected void createOrderBy(StringBuffer sbSql, String keyOrder) {

		if (!StringValidateUtil.isEmpty(keyOrder)) {

			StringBuffer sbOrderBy = new StringBuffer();

			if (keyOrder.equals(SORT_SYS_HOUSING_CD_ASC)) {
				// _housingCd
				sbOrderBy.append(", " + TABLE_HOUSING_INFO_ALIAS
						+ "_housingCd DESC");
			} else if (keyOrder.equals(SORT_PRICE_ASC)) {
				// �������i��
				sbOrderBy
						.append(", " + TABLE_HOUSING_INFO_ALIAS + "_price ASC");

			} else if (keyOrder.equals(SORT_PRICE_DESC)) {
				// �������i��
				sbOrderBy.append(", " + TABLE_HOUSING_INFO_ALIAS
						+ "_price DESC");

			} else if (keyOrder.equals(SORT_UPD_DATA_ASC)) {
				// �����o�^���i�V�����j��
				sbOrderBy.append(", " + TABLE_HOUSING_INFO_ALIAS
						+ "_updDate ASC");

			} else if (keyOrder.equals(SORT_UPD_DATA_DESC)) {
				// �����o�^���i�V�����j��
				sbOrderBy.append(", " + TABLE_HOUSING_INFO_ALIAS
						+ "_updDate DESC");

			} else if (keyOrder.equals(SORT_BUILD_DATE_DESC)) {
				// �z�N����
				sbOrderBy.append(", " + TABLE_BUILDING_INFO_ALIAS
						+ "_compDate ASC");

			} else if (keyOrder.equals(SORT_BUILD_DATE_ASC)) {
				// �z�N����
				sbOrderBy.append(", " + TABLE_BUILDING_INFO_ALIAS
						+ "_compDate DESC");

			} else if (keyOrder.equals(SORT_WALK_TIME_ASC)) {
				// �w����̋�����
				sbOrderBy.append(", " + TABLE_HOUSING_INFO_ALIAS
						+ "_minWalkingTime ASC");

			} else if (keyOrder.equals(SORT_WALK_TIME_DESC)) {
				// �w����̋�����
				sbOrderBy.append(", " + TABLE_HOUSING_INFO_ALIAS
						+ "_minWalkingTime DESC");

			}

			// �ŏ��̃J���}���폜���āu ORDER BY �v��t����
			if (sbOrderBy.length() > 0) {
				String strComma = ",";
				sbOrderBy.delete(sbOrderBy.indexOf(strComma),
						sbOrderBy.indexOf(strComma) + strComma.length());
				sbOrderBy.insert(0, " ORDER BY ");

				sbSql.append(sbOrderBy.toString());
			}
		}

	}
}
