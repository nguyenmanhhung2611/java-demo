package jp.co.transcosmos.dm3.core.model.housing.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingSearchForm;
import jp.co.transcosmos.dm3.dao.FragmentResultSetExtractor;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class SearchHousingDAOImpl implements SearchHousingDAO {

	// �e�[�u���������A�G�C���A�X
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

	// �\�[�g����
	/** �����ԍ��i�\�[�g�����j */
	public static final String SORT_SYS_HOUSING_CD_ASC = "0";
	/** ����/���i���������i�\�[�g�����j */
	public static final String SORT_PRICE_ASC = "1";
	/** ����/���i���������i�\�[�g�����j */
	public static final String SORT_PRICE_DESC = "2";
	/** ��L�ʐς��������i�\�[�g�����j */
	public static final String SORT_PERSONAL_AREA_ASC = "3";
	/** ��L�ʐς��L�����i�\�[�g�����j */
	public static final String SORT_PERSONAL_AREA_DESC = "4";
	/** �z�N���Â����i�\�[�g�����j */
	public static final String SORT_BUILD_DATE_DESC = "5";
	/** �z�N���V�������i�\�[�g�����j */
	public static final String SORT_BUILD_DATE_ASC = "6";

	private static final Log log = LogFactory.getLog(SearchHousingDAOImpl.class);

	/** �f�[�^�\�[�X */
	protected DataSource dataSource;

	/** �����������ʂ��i�[����ׂ�Mapper */
	protected SearchHousingRowMapper searchHousingRowMapper;

	// �ȉ��AapplicationContext�ɒ�`
	// 
	// OR�����Ō���������������������Map�ŕێ�����
	// �@key�F����������CD�A�����L�[�̑��ݗL�����������������ƂȂ�
	// �@value�F���������Ɏg�p����l��ێ��ł���
	// ������OR�����Ώۂ�List�ŕێ�����

	/** OR�����Ō������邱��������CD�̃O���[�s���O��` */
	private List<Map<String, String>> orPartSrchList =  new ArrayList<Map<String, String>>();

	// ���w�����ɂĎg�p����ׂ�OR_PART_SRCH_LIST�ɒ��ڐ錾���Ȃ�
	/** �w�k��5�� or �w�k��10�� or �w�k��15�� or �w�k��20�� or �o�X�k��5�� */
	private Map<String, String> orPartSrchWarktime = new HashMap<String, String>();

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setSearchHousingRowMapper(SearchHousingRowMapper searchHousingRowMapper) {
		this.searchHousingRowMapper = searchHousingRowMapper;
	}

	public void setOrPartSrchList(List<Map<String, String>> orPartSrchList) {
		this.orPartSrchList = orPartSrchList;
	}

	public void setOrPartSrchWarktime(Map<String, String> orPartSrchWarktime) {
		this.orPartSrchWarktime = orPartSrchWarktime;
	}

	/**
	 * �������i�ꕔ�A�������j���������A���ʃ��X�g�𕜋A����B�i�ꗗ�p�j<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A����������������B<br/>
	 * �������ʂ� Form �I�u�W�F�N�g�Ɋi�[����A�擾�����Y��������߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 * ���̃��\�b�h���g�p�����ꍇ�A�Öق̒��o�����i�Ⴆ�΁A����J�����̏��O�Ȃǁj���K�p�����B<br/>
	 * ����āA�t�����g���͊�{�I�ɂ��̃��\�b�h���g�p���鎖�B<br/>
	 * <br/>
	 * 
	 * @param searchForm ���������A����сA�������ʂ̊i�[�I�u�W�F�N�g
	 * @param full false �̏ꍇ�A���J�ΏۊO�����O����B�@true �̏ꍇ�͏��O���Ȃ�
	 * 
	 * @return �Y������
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public List<Housing> searchHousing(HousingSearchForm searchForm) {
		return searchHousing(searchForm, false);
	}

	/**
	 * �������i�ꕔ�A�������j���������A���ʃ��X�g�𕜋A����B�i�ꗗ�p�j<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A����������������B<br/>
	 * �������ʂ� Form �I�u�W�F�N�g�Ɋi�[����A�擾�����Y��������߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 * 
	 * @param searchForm ���������A����сA�������ʂ̊i�[�I�u�W�F�N�g
	 * @param full false �̏ꍇ�A���J�ΏۊO�����O����B�@true �̏ꍇ�͏��O���Ȃ�
	 * 
	 * @return �Y������
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Housing> searchHousing(HousingSearchForm searchForm, boolean full) {

		JdbcTemplate template = new JdbcTemplate(dataSource);

		StringBuffer sbSql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		// ����SQL�𐶐�
		createSql(searchForm, sbSql, params, full);

		log.debug("SQL : " + sbSql.toString());

		return (List<Housing>) template.query(sbSql.toString(), (Object[]) params.toArray(),
				new FragmentResultSetExtractor<Object>(this.searchHousingRowMapper, searchForm.getStartIndex(),
						searchForm.getEndIndex()));
	}

	/**
	 * ����������SQL���𐶐�����B<br/>
	 * <br/>
	 * 
	 * @param sbSql ��������SQL����ǉ�����o�b�t�@
	 * @param params SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param full false�̏ꍇ�A���J�ΏۊO�����O����Btrue�̏ꍇ�͏��O���Ȃ�
	 */
	protected void createSql(HousingSearchForm searchForm, StringBuffer sbSql, List<Object> params, boolean full) {

		createSelect(sbSql, params, full);
		createFrom(sbSql, params, full);
		createWhere(searchForm, sbSql, params, full);
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
	 * @param sbSql ��������SQL����ǉ�����o�b�t�@
	 * @param params SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param full false�̏ꍇ�A���J�ΏۊO�����O����Btrue�̏ꍇ�͏��O���Ȃ�
	 */
	protected void createSelect(StringBuffer sbSql, List<Object> params, boolean full) {

		StringBuffer sbSelect = new StringBuffer();

		sbSelect.append(" SELECT ");

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
		// �o�^��
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".ins_date AS " + TABLE_HOUSING_INFO_ALIAS + "_insDate");
		// �o�^��
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".ins_user_id AS " + TABLE_HOUSING_INFO_ALIAS + "_insUserId");
		// �ŏI�X�V��
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".upd_date AS " + TABLE_HOUSING_INFO_ALIAS + "_updDate");
		// �ŏI�X�V��
		sbSelect.append(", " + TABLE_HOUSING_INFO_ALIAS + ".upd_user_id AS " + TABLE_HOUSING_INFO_ALIAS + "_updUserId");

		// �����X�e�[�^�X���
		// �V�X�e������CD
		sbSelect.append(", " + TABLE_HOUSING_STATUS_INFO_ALIAS + ".sys_housing_cd AS " + TABLE_HOUSING_STATUS_INFO_ALIAS + "_sysHousingCd");
		// ����J�t���O
		sbSelect.append(", " + TABLE_HOUSING_STATUS_INFO_ALIAS + ".hidden_flg AS " + TABLE_HOUSING_STATUS_INFO_ALIAS + "_hiddenFlg");

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

		// Select��̊g������
		createSelectExtra(sbSelect, params, full);

		sbSql.append(sbSelect.toString());

	}

	/**
	 * Select�吶���̊g������<br/>
	 * �p�����Select���ڂ�ǉ�����ꍇ�A���̃��\�b�h���I�[�o�[���C�h����<br/>
	 * 
	 * @param sbWhere
	 * @param params
	 * @param searchForm
	 */
	protected void createSelectExtra(StringBuffer sbSelect, List<Object> params, boolean full) {

		// �p�����Select���ڂ�ǉ�����ꍇ�A���̃��\�b�h���I�[�o�[���C�h����

	}

	/**
	 * From��̕�����𐶐�����<br/>
	 * �g�p����e�[�u�����A�ʖ��͒萔���`���ċL�q���邱��<br/>
	 * �i�R�[�f�B���O�ȑf���A�^�C�v�~�X�h�~�j<br/>
	 * <br/>
	 * 
	 * @param sbSql ��������SQL����ǉ�����o�b�t�@
	 * @param params SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param full false�̏ꍇ�A���J�ΏۊO�����O����Btrue�̏ꍇ�͏��O���Ȃ�
	 */
	protected void createFrom(StringBuffer sbSql, List<Object> params, boolean full) {

		StringBuffer sbFrom = new StringBuffer();

		sbFrom.append(" FROM ");
		sbFrom.append(" " + TABLE_HOUSING_INFO_NAME + " " + TABLE_HOUSING_INFO_ALIAS);
		sbFrom.append(" LEFT OUTER JOIN " + TABLE_HOUSING_STATUS_INFO_NAME + " " + TABLE_HOUSING_STATUS_INFO_ALIAS + " ON " + TABLE_HOUSING_INFO_ALIAS + ".sys_housing_cd = " + TABLE_HOUSING_STATUS_INFO_ALIAS + ".sys_housing_cd");
		sbFrom.append(" INNER JOIN " + TABLE_BUILDING_INFO_NAME + " " + TABLE_BUILDING_INFO_ALIAS + " ON " + TABLE_HOUSING_INFO_ALIAS + ".sys_building_cd = " + TABLE_BUILDING_INFO_ALIAS + ".sys_building_cd");
		sbFrom.append(" LEFT OUTER JOIN " + TABLE_BUILDING_DTL_INFO_NAME + " " + TABLE_BUILDING_DTL_INFO_ALIAS + " ON " + TABLE_HOUSING_INFO_ALIAS + ".sys_building_cd = " + TABLE_BUILDING_DTL_INFO_ALIAS + ".sys_building_cd");

		createFromExtra(sbFrom, params, full);

		sbSql.append(sbFrom.toString());

	}

	/**
	 * From�吶���̊g������<br/>
	 * �p����Ō����e�[�u����ǉ�����ꍇ�A���̃��\�b�h���I�[�o�[���C�h����<br/>
	 * �������镶�����JOIN�傩��ƂȂ�悤�ɂ��邱��<br/>
	 * 
	 * @param sbWhere
	 * @param params
	 * @param searchForm
	 */
	protected void createFromExtra(StringBuffer sbFrom, List<Object> params, boolean full) {

		// �p����Ō����e�[�u����ǉ�����ꍇ�A���̃��\�b�h���I�[�o�[���C�h����

	}

	/**
	 * Where��̕�����𐶐�����<br/>
	 * �����������ƂɁA�uAND�`�v�ƂȂ镶����𐶐����郁�\�b�h���Ăяo���Ă���<br/>
	 * <br/>
	 * 
	 * @param sbSql ��������SQL����ǉ�����o�b�t�@
	 * @param params SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param full false�̏ꍇ�A���J�ΏۊO�����O����Btrue�̏ꍇ�͏��O���Ȃ�
	 */
	protected void createWhere(HousingSearchForm searchForm, StringBuffer sbSql, List<Object> params, boolean full) {

		StringBuffer sbWhere = new StringBuffer();

		// ����J�t���O�̃`�F�b�N
		createWhereAndFull(sbWhere, params, full);

		// �����ԍ�
		createWhereAndHousingCd(sbWhere, params, searchForm.getKeyHousingCd());
		// ������
		createWhereAndDisplayHousingName(sbWhere, params, searchForm.getKeyDisplayHousingName());

		// �s���{��CD
		createWhereAndPrefCd(sbWhere, params, searchForm.getKeyPrefCd());
		// �s�撬��CD
		createWhereAndAddressCd(sbWhere, params, searchForm.getKeyAddressCd());
		// ����CD�A�wCD
		createWhereAndRouteCdStationCd(sbWhere, params, searchForm.getKeyRouteCd(), searchForm.getKeyStationCd(), searchForm.getKeyPartSrchCd());

		// �ƒ�
		createWhereAndPriceLower(sbWhere, params, searchForm.getKeyPriceLower());
		createWhereAndPriceUpper(sbWhere, params, searchForm.getKeyPriceUpper());

		// �y�n�ʐ�
		createWhereAndLandAreaLower(sbWhere, params, searchForm.getKeyLandAreaLower());
		createWhereAndLandAreaUpper(sbWhere, params, searchForm.getKeyLandAreaUpper());
		// ��L�ʐ�
		createWhereAndPersonalAreaLower(sbWhere, params, searchForm.getKeyPersonalAreaLower());
		createWhereAndPersonalAreaUpper(sbWhere, params, searchForm.getKeyPersonalAreaUpper());

		// �������CD
		createWhereAndHousingKindCd(sbWhere, params, searchForm.getKeyHousingKindCd());
		// �Ԏ��CD
		createWhereAndLayoutCd(sbWhere, params, searchForm.getKeyLayoutCd());

		// �����ʐ�
		createWhereAndBuildingAreaLower(sbWhere, params, searchForm.getKeyBuildingAreaLower());
		createWhereAndBuildingAreaUpper(sbWhere, params, searchForm.getKeyBuildingAreaUpper());

		// ����������
		createWhereAndPartSrchCd(sbWhere, params, searchForm.getKeyPartSrchCd());

		// ���������̊g��
		createWhereExtra(sbWhere, params, searchForm, full);

		// �ŏ��́uAND�v���폜���āu WHERE �v��t����
		if (sbWhere.length() > 0) {
			String strAnd = "AND";
			sbWhere.delete(sbWhere.indexOf(strAnd), sbWhere.indexOf(strAnd) + strAnd.length());
			sbWhere.insert(0, " WHERE ");
		}
		
		sbSql.append(sbWhere.toString());

	}

	/**
	 * Where�吶���̊g������<br/>
	 * �p����ŌŗL�̌���������ǉ�����ꍇ�A���̃��\�b�h���I�[�o�[���C�h����<br/>
	 * �������镶����́uAND�`�v�ƂȂ�悤�ɂ��邱��<br/>
	 * 
	 * @param sbWhere
	 * @param params
	 * @param searchForm
	 * @param full
	 */
	protected void createWhereExtra(StringBuffer sbWhere, List<Object> params, HousingSearchForm searchForm, boolean full) {

		// �p����ŌŗL�̌���������ǉ�����ꍇ�A���̃��\�b�h���I�[�o�[���C�h����B

	}

	/**
	 * ����J�t���O�̌��������������ǉ�����<br/>
	 * <br/>
	 * 
	 * @param sbWhere ��������SQL����ǉ�����o�b�t�@
	 * @param params SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param full false�̏ꍇ�A���J�ΏۊO�����O����Btrue�̏ꍇ�͏��O���Ȃ�
	 */
	protected void createWhereAndFull(StringBuffer sbWhere, List<Object> params, boolean full) {

		if (!full) {
			sbWhere.append(" AND (" + TABLE_HOUSING_STATUS_INFO_ALIAS + ".hidden_flg IS NULL OR " + TABLE_HOUSING_STATUS_INFO_ALIAS + ".hidden_flg IS FALSE ) ");
		}

	}

	/**
	 * �����ԍ��̌��������������ǉ�����<br/>
	 * <br/>
	 * 
	 * @param sbWhere ��������SQL����ǉ�����o�b�t�@
	 * @param params SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param csHousingCd �����ΏۂƂ��镨���ԍ��i�J���}��؂�ŕ����̎󂯎���z��j
	 */
	protected void createWhereAndHousingCd(StringBuffer sbWhere, List<Object> params, String csHousingCd) {

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
				sqlIn.delete(sqlIn.indexOf(strComma), sqlIn.indexOf(strComma) + strComma.length());
				sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS + ".housing_cd IN (" + sqlIn.toString() + ")");
			}
		}

	}

	/**
	 * �\���p�������̌��������������ǉ�����<br/>
	 * <br/>
	 * 
	 * @param sbWhere ��������SQL����ǉ�����o�b�t�@
	 * @param params SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param displayHousingName �����ΏۂƂ���\���p�������i������v�j
	 */
	protected void createWhereAndDisplayHousingName(StringBuffer sbWhere, List<Object> params, String displayHousingName) {

		if (!StringValidateUtil.isEmpty(displayHousingName)) {
			sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS + ".display_housing_name LIKE ? ");
			params.add("%" + displayHousingName + "%");
		}

	}

	/**
	 * �s���{��CD�̌��������������ǉ�����<br/>
	 * <br/>
	 * 
	 * @param sbWhere ��������SQL����ǉ�����o�b�t�@
	 * @param params SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param csPrefCd �����ΏۂƂ���s���{��CD�̃p�����^�i�J���}��؂�ŕ����̎󂯎���z��j
	 */
	protected void createWhereAndPrefCd(StringBuffer sbWhere, List<Object> params, String csPrefCd) {

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
				sqlIn.delete(sqlIn.indexOf(strComma), sqlIn.indexOf(strComma) + strComma.length());
				sbWhere.append(" AND " + TABLE_BUILDING_INFO_ALIAS + ".pref_cd IN (" + sqlIn.toString() + ")");
			}
		}

	}

	/**
	 * �s�撬��CD�̌��������������ǉ�����<br/>
	 * <br/>
	 * 
	 * @param sbWhere ��������SQL����ǉ�����o�b�t�@
	 * @param params SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param csAaddressCd �����ΏۂƂ���s�撬��CD�̃p�����^�i�J���}��؂�ŕ����̎󂯎���z��j
	 */
	protected void createWhereAndAddressCd(StringBuffer sbWhere, List<Object> params, String csAaddressCd) {

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
				sqlIn.delete(sqlIn.indexOf(strComma), sqlIn.indexOf(strComma) + strComma.length());
				sbWhere.append(" AND " + TABLE_BUILDING_INFO_ALIAS + ".address_cd IN (" + sqlIn.toString() + ")");
			}
		}

	}

	/**
	 * �H��CD�ƉwCD�̌��������������ǉ�����<br/>
	 * �wCD�̌����͘H��CD�������ɐݒ肳��Ă���K�v������B<br/>
	 * �܂��A�����������ɉw�k�����ԁA�o�X�k�����Ԃ��܂܂��ꍇ�A������l������</br> <br/>
	 * 
	 * @param sbWhere ��������SQL����ǉ�����o�b�t�@
	 * @param params SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param csRouteCd �����ΏۂƂ���H��CD�̃p�����^�i�J���}��؂�ŕ����̎󂯎���z��j
	 * @param csStationCd �����ΏۂƂ���wCD�̃p�����^�i�J���}��؂�ŕ����̎󂯎���z��j
	 * @param csPartSrchCd �����ΏۂƂ��邱��������CD�̃p�����^�i�J���}��؂�ŕ����̎󂯎���z��j
	 */
	protected void createWhereAndRouteCdStationCd(StringBuffer sbWhere, List<Object> params, String csRouteCd,
			String csStationCd, String csPartSrchCd) {

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
				sbWhere.append(" INNER JOIN station_route_info sri ON bsi.station_cd = sri.station_cd ");
				sbWhere.append(" INNER JOIN station_mst sm ON sri.station_cd = sm.station_cd ");
				sbWhere.append(" INNER JOIN route_mst rm ON sri.route_cd = rm.route_cd ");
				sbWhere.append(" WHERE rm.route_cd IN (" + sqlRouteIn.toString() + ") ");

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
						sqlStationIn.delete(sqlStationIn.indexOf(strComma), sqlStationIn.indexOf(strComma) + strComma.length());
						sbWhere.append(" AND sm.station_cd IN (" + sqlStationIn.toString() + ")");
					}

				}

				// ����������(�k������)���w�肳�ꂽ�ꍇ�A�����ōi�荞�݁@---------------------------
				if (!StringValidateUtil.isEmpty(csPartSrchCd)) {

					String[] arrPartSrchCd = csPartSrchCd.split(",");
					StringBuffer sqlPartWhere = new StringBuffer();

					for (int i = 0; i < arrPartSrchCd.length; i++) {
						if (arrPartSrchCd[i].trim().length() > 0) {
							// �k������ OR����
							if (orPartSrchWarktime.containsKey(arrPartSrchCd[i])) {
								if (arrPartSrchCd[i].startsWith("B")) {
									// �o�X�k������
									sqlPartWhere.append(" OR bsi.time_from_bus_stop <= " + orPartSrchWarktime.get(arrPartSrchCd[i]));
								} else if (arrPartSrchCd[i].startsWith("S")) {
									// �w�k������
									sqlPartWhere.append(" OR bsi.time_from_station <= " + orPartSrchWarktime.get(arrPartSrchCd[i]));
								}
							}
						}
					}

					if (sqlPartWhere.length() > 0) {
						// �ŏ��́uOR�v���폜
						String strOr = "OR";
						sqlPartWhere.delete(sqlPartWhere.indexOf(strOr), sqlPartWhere.indexOf(strOr) + strOr.length());
						// AND�Őڑ�
						sbWhere.append(" AND (" + sqlPartWhere.toString() + ")");
					}
				}
				// -----------------------------------------------------------------------------------

				sbWhere.append(" AND bsi.sys_building_cd = " + TABLE_BUILDING_INFO_ALIAS + ".sys_building_cd)");
			}
		}

	}

	/**
	 * ����/���i�E�����̌��������������ǉ�����<br/>
	 * <br/>
	 * 
	 * @param sbWhere ��������SQL����ǉ�����o�b�t�@
	 * @param params SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param priceLower �����ΏۂƂ������/���i�E�����̃p�����^
	 */
	protected void createWhereAndPriceLower(StringBuffer sbWhere, List<Object> params, Long priceLower) {

		if (priceLower != null) {
			sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS + ".price >= ? ");
			params.add(priceLower);
		}

	}

	/**
	 * ����/���i�E����̌��������������ǉ�����<br/>
	 * <br/>
	 * 
	 * @param sbWhere ��������SQL����ǉ�����o�b�t�@
	 * @param params SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param priceUpper �����ΏۂƂ������/���i�E����̃p�����^
	 */
	protected void createWhereAndPriceUpper(StringBuffer sbWhere, List<Object> params, Long priceUpper) {

		if (priceUpper != null) {
			sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS + ".price <= ? ");
			params.add(priceUpper);
		}

	}

	/**
	 * �y�n�ʐρE�����̌��������������ǉ�����<br/>
	 * <br/>
	 * 
	 * @param sbWhere ��������SQL����ǉ�����o�b�t�@
	 * @param params SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param landAreaLower �����ΏۂƂ���y�n�ʐρE�����̃p�����^
	 */
	protected void createWhereAndLandAreaLower(StringBuffer sbWhere, List<Object> params, BigDecimal landAreaLower) {

		if (landAreaLower != null) {
			sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS + ".land_area >= ? ");
			params.add(landAreaLower);
		}

	}

	/**
	 * �y�n�ʐρE����̌��������������ǉ�����<br/>
	 * <br/>
	 * 
	 * @param sbWhere ��������SQL����ǉ�����o�b�t�@
	 * @param params SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param landAreaUpper �����ΏۂƂ���y�n�ʐρE����̃p�����^
	 */
	protected void createWhereAndLandAreaUpper(StringBuffer sbWhere, List<Object> params, BigDecimal landAreaUpper) {

		if (landAreaUpper != null) {
			sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS + ".land_area <= ? ");
			params.add(landAreaUpper);
		}

	}

	/**
	 * ��L�ʐρE�����̌��������������ǉ�����<br/>
	 * <br/>
	 * 
	 * @param sbWhere ��������SQL����ǉ�����o�b�t�@
	 * @param params SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param personalAreaLower �����ΏۂƂ����L�ʐρE�����̃p�����^
	 */
	protected void createWhereAndPersonalAreaLower(StringBuffer sbWhere, List<Object> params, BigDecimal personalAreaLower) {

		if (personalAreaLower != null) {
			sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS + ".personal_area >= ? ");
			params.add(personalAreaLower);
		}

	}

	/**
	 * ��L�ʐρE����̌��������������ǉ�����<br/>
	 * <br/>
	 * 
	 * @param sbWhere ��������SQL����ǉ�����o�b�t�@
	 * @param params SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param personalAreaUpper �����ΏۂƂ����L�ʐρE����̃p�����^
	 */
	protected void createWhereAndPersonalAreaUpper(StringBuffer sbWhere, List<Object> params, BigDecimal personalAreaUpper) {

		if (personalAreaUpper != null) {
			sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS + ".personal_area <= ? ");
			params.add(personalAreaUpper);
		}

	}

	/**
	 * �������CD�̌��������������ǉ�����<br/>
	 * <br/>
	 * 
	 * @param sbWhere ��������SQL����ǉ�����o�b�t�@
	 * @param params SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param csHousingKindCd �����ΏۂƂ��镨�����CD�̃p�����^�i�J���}��؂�ŕ����̎󂯎���z��j
	 */
	protected void createWhereAndHousingKindCd(StringBuffer sbWhere, List<Object> params, String csHousingKindCd) {

		if (!StringValidateUtil.isEmpty(csHousingKindCd)) {

			StringBuffer sqlIn = new StringBuffer();
			String[] arrHousingKindCd = csHousingKindCd.split(",");
			for (int i = 0; i < arrHousingKindCd.length; i++) {
				if (arrHousingKindCd[i].trim().length() > 0) {
					sqlIn.append(",? ");
					params.add(arrHousingKindCd[i]);
				}
			}
			if (sqlIn.length() > 0) {
				// �ŏ��̃J���}���폜����
				String strComma = ",";
				sqlIn.delete(sqlIn.indexOf(strComma), sqlIn.indexOf(strComma) + strComma.length());
				sbWhere.append(" AND " + TABLE_BUILDING_INFO_ALIAS + ".housing_kind_cd IN (" + sqlIn.toString() + ")");
			}
		}

	}

	/**
	 * �Ԏ��CD�̌��������������ǉ�����<br/>
	 * <br/>
	 * 
	 * @param sbWhere ��������SQL����ǉ�����o�b�t�@
	 * @param params SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param csLayoutCd �����ΏۂƂ���Ԏ��CD�̃p�����^�i�J���}��؂�ŕ����̎󂯎���z��j
	 */
	protected void createWhereAndLayoutCd(StringBuffer sbWhere, List<Object> params, String csLayoutCd) {

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
				sqlIn.delete(sqlIn.indexOf(strComma), sqlIn.indexOf(strComma) + strComma.length());
				sbWhere.append(" AND " + TABLE_HOUSING_INFO_ALIAS + ".layout_cd IN (" + sqlIn.toString() + ")");
			}
		}

	}

	/**
	 * �����ʐρE�����̌��������������ǉ�����<br/>
	 * <br/>
	 * 
	 * @param sbWhere ��������SQL����ǉ�����o�b�t�@
	 * @param params SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param buildingAreaLower �����ΏۂƂ��錚���ʐρE�����̃p�����^
	 */
	protected void createWhereAndBuildingAreaLower(StringBuffer sbWhere, List<Object> params, BigDecimal buildingAreaLower) {

		if (buildingAreaLower != null) {
			sbWhere.append(" AND " + TABLE_BUILDING_DTL_INFO_ALIAS + ".building_area >= ? ");
			params.add(buildingAreaLower);
		}

	}

	/**
	 * �����ʐρE����̌��������������ǉ�����<br/>
	 * <br/>
	 * 
	 * @param sbWhere ��������SQL����ǉ�����o�b�t�@
	 * @param params SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param buildingAreaUpper �����ΏۂƂ��錚���ʐρE����̃p�����^
	 */
	protected void createWhereAndBuildingAreaUpper(StringBuffer sbWhere, List<Object> params, BigDecimal buildingAreaUpper) {

		if (buildingAreaUpper != null) {
			sbWhere.append(" AND " + TABLE_BUILDING_DTL_INFO_ALIAS + ".building_area <= ? ");
			params.add(buildingAreaUpper);
		}

	}

	/**
	 * ����������CD�̌��������������ǉ�����<br/>
	 * <br/>
	 * orPartSrchList�ŃO���[�s���O���ꂽ����������CD���ƁA<br/>
	 * ���͒�`�Ɋ܂܂�Ȃ�����������CD�ŁAEXISTS�����𐶐�����B<br/>
	 * <br/>
	 * ��j<br/>
	 * �@�@�w�肳�ꂽ����������CD<br/>
	 * �@�@��[S05,S10,NEW,ANT,PAD]<br/>
	 * �@�@orPartSrchList�̒�`<br/>
	 * �@�@��[{S05=5, S10=10, S15=15, S20=20, B05=5, B10=10}, {FRE=FRE, REI=REI}, {NEW=NEW, C05=C05}]<br/>
	 * <br/>
	 * �@�@���������EXISTS�����͈ȉ��̒ʂ�<br/>
	 * <br/>
	 * �@�@  AND EXISTS ( SELECT 1 <br/>
	 * �@�@    FROM housing_part_info hpi<br/> 
	 * �@�@    WHERE<br/>
	 * �@�@      housingInfo.sys_housing_cd = hpi.sys_housing_cd<br/> 
	 * �@�@      AND hpi.part_srch_cd = 'ANT'<br/>
	 * �@�@  )<br/> 
	 * �@�@  AND EXISTS ( SELECT 1<br/> 
	 * �@�@    FROM housing_part_info hpi<br/> 
	 * �@�@    WHERE<br/>
	 * �@�@      housingInfo.sys_housing_cd = hpi.sys_housing_cd<br/> 
	 * �@�@      AND hpi.part_srch_cd = 'PAD'<br/>
	 * �@�@  )<br/> 
	 * �@�@  AND EXISTS ( SELECT 1<br/> 
	 * �@�@    FROM housing_part_info hpi<br/> 
	 * �@�@    WHERE<br/>
	 * �@�@      housingInfo.sys_housing_cd = hpi.sys_housing_cd<br/> 
	 * �@�@      AND (hpi.part_srch_cd = 'S05' OR hpi.part_srch_cd = 'S10' )<br/>
	 * �@�@  )<br/> 
	 * �@�@  AND EXISTS ( SELECT 1<br/> 
	 * �@�@    FROM housing_part_info hpi<br/> 
	 * �@�@    WHERE<br/>
	 * �@�@      housingInfo.sys_housing_cd = hpi.sys_housing_cd<br/> 
	 * �@�@      AND (hpi.part_srch_cd = 'NEW' )<br/>
	 * �@�@  )<br/> 
	 * <br/>
	 * 
	 * @param sbWhere ��������SQL����ǉ�����o�b�t�@
	 * @param params SQL�Ŏg�p����o�C���h�p�����[�^�̃��X�g�I�u�W�F�N�g
	 * @param csPartSrchCd �����ΏۂƂ��邱��������CD�̃p�����^
	 */
	protected void createWhereAndPartSrchCd(StringBuffer sbWhere, List<Object> params, String csPartSrchCd) {

		if (!StringValidateUtil.isEmpty(csPartSrchCd)) {

			String[] arrPartSrchCd = csPartSrchCd.split(",");

			List<StringBuffer> sqlPartWhereList = new ArrayList<StringBuffer>(orPartSrchList.size());
			List<StringBuffer> orPartSrchCdList = new ArrayList<StringBuffer>(orPartSrchList.size());
			for (int i = 0; i < orPartSrchList.size(); i++) {
				sqlPartWhereList.add(new StringBuffer());
				orPartSrchCdList.add(new StringBuffer());
			}

			for (int i = 0; i < arrPartSrchCd.length; i++) {

				boolean add = true;

				if (arrPartSrchCd[i].trim().length() > 0) {

					if (orPartSrchList.size() > 0) {
						for (int j = 0; j < orPartSrchList.size(); j++) {
							Map<String, String> orPartSrhCdMap = orPartSrchList.get(j);
							if (orPartSrhCdMap.containsKey(arrPartSrchCd[i])) {
								StringBuffer sqlPartWhere = sqlPartWhereList.get(j);
								StringBuffer orPartSrchCd = orPartSrchCdList.get(j);

								sqlPartWhere.append(" OR hpi.part_srch_cd = ? ");
								orPartSrchCd.append("," + arrPartSrchCd[i]);

								add = false;
								break;
							}
						}
					}
					if (add) {
						sbWhere.append(" AND EXISTS(");
						sbWhere.append(" SELECT 1 FROM housing_part_info hpi");
						sbWhere.append(" WHERE " + TABLE_HOUSING_INFO_ALIAS + ".sys_housing_cd = hpi.sys_housing_cd");
						sbWhere.append(" AND hpi.part_srch_cd = ? )");
						params.add(arrPartSrchCd[i]);
					}
				}
			}

			for (int i = 0; i < orPartSrchList.size(); i++) {
				StringBuffer sqlPartWhere = sqlPartWhereList.get(i);
				StringBuffer orPartSrchCd = orPartSrchCdList.get(i);

				if (sqlPartWhere.length() > 0) {
					// �ŏ���OR���폜����
					String strOr = "OR";
					sqlPartWhere.delete(sqlPartWhere.indexOf(strOr), sqlPartWhere.indexOf(strOr) + strOr.length());
					// �ŏ��̃J���}���폜����
					String strComma = ",";
					orPartSrchCd.delete(orPartSrchCd.indexOf(strComma), orPartSrchCd.indexOf(strComma) + strComma.length());
					// AND�Őڑ�
					sbWhere.append(" AND EXISTS(");
					sbWhere.append(" SELECT 1 FROM housing_part_info hpi");
					sbWhere.append(" WHERE " + TABLE_HOUSING_INFO_ALIAS + ".sys_housing_cd = hpi.sys_housing_cd");
					sbWhere.append(" AND (" + sqlPartWhere.toString() + "))");

					String[] tmpPartSrchCd = orPartSrchCd.toString().split(",");
					for (int j = 0; j < tmpPartSrchCd.length; j++) {
						params.add(tmpPartSrchCd[j]);
					}
				}

			}

		}
	}

	/**
	 * Order By��𐶐�����<br/>
	 * 
	 * @param sbSql ��������SQL����ǉ�����o�b�t�@
	 * @param keyOrder �����L�[
	 */
	protected void createOrderBy(StringBuffer sbSql, String keyOrder) {

		if (!StringValidateUtil.isEmpty(keyOrder)) {

			StringBuffer sbOrderBy = new StringBuffer();

			if (keyOrder.equals(SORT_SYS_HOUSING_CD_ASC)) {
				// �V�X�e������CD�̏���
				sbOrderBy.append(", " + TABLE_HOUSING_INFO_ALIAS + "_sysHousingCd ASC");

			} else if (keyOrder.equals(SORT_PRICE_ASC)) {
				// ����/���i�̈�����
				sbOrderBy.append(", " + TABLE_HOUSING_INFO_ALIAS + "_price ASC");

			} else if (keyOrder.equals(SORT_PRICE_DESC)) {
				// ����/���i�̍�����
				sbOrderBy.append(", " + TABLE_HOUSING_INFO_ALIAS + "_price DESC");

			} else if (keyOrder.equals(SORT_PERSONAL_AREA_ASC)) {
				// ��L�ʐς̋�����
				sbOrderBy.append(", " + TABLE_HOUSING_INFO_ALIAS + "_personalArea ASC");

			} else if (keyOrder.equals(SORT_PERSONAL_AREA_DESC)) {
				// ��L�ʐς̍L����
				sbOrderBy.append(", " + TABLE_HOUSING_INFO_ALIAS + "_personalArea DESC");

			} else if (keyOrder.equals(SORT_BUILD_DATE_DESC)) {
				// �z�N�̌Â���
				sbOrderBy.append(", " + TABLE_BUILDING_INFO_ALIAS + "_compDate ASC");

			} else if (keyOrder.equals(SORT_BUILD_DATE_ASC)) {
				// �z�N�̎Ⴂ��
				sbOrderBy.append(", " + TABLE_BUILDING_INFO_ALIAS + "_compDate DESC");

			}

			// �\�[�g�����̊g��
			createOrderByExtra(sbOrderBy, keyOrder);

			// �ŏ��̃J���}���폜���āu ORDER BY �v��t����
			if (sbOrderBy.length() > 0) {
				String strComma = ",";
				sbOrderBy.delete(sbOrderBy.indexOf(strComma), sbOrderBy.indexOf(strComma) + strComma.length());
				sbOrderBy.insert(0, " ORDER BY ");

				sbSql.append(sbOrderBy.toString());
			}
		}

	}

	/**
	 * OrderBy�吶���̊g������<br/>
	 * �p����ŌŗL�̌���������ǉ�����ꍇ�A���̃��\�b�h���I�[�o�[���C�h����<br/>
	 * �������镶����́u, �v����n�܂�悤�ɂ��邱��<br/>
	 * 
	 * @param sbOrderBy ��������SQL����ǉ�����o�b�t�@
	 * @param keyOrder �����L�[
	 */
	protected void createOrderByExtra(StringBuffer sbOrderBy, String keyOrder) {

		// �p����ŌŗL�̌���������ǉ�����ꍇ�A���̃��\�b�h���I�[�o�[���C�h����B

	}

}
