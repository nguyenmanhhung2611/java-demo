package jp.co.transcosmos.dm3.corePana.model.housing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingLandmark;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.EquipMst;
import jp.co.transcosmos.dm3.core.vo.HousingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.RrMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.util.PanaCalcUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.HousingInspection;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

/**
 * �����ڍ׉�ʗp�t�H�[��.
 * <p>
 * <pre>
 * �S����        �C����        �C�����e
 * ------------ ----------- -----------------------------------------------------
 * gao.long        2015.04.13     �V�K�쐬
 * </pre>
 * <p>
 *
 */
public class PanaHousingDetailed {

	/** �����P�̃��[�h */
	private static final String DETAIL_MODE = "detail";
	/** ���t�H�[���v�������胂�[�h */
	private static final String REFORM_MODE = "reform";
	/** �����摜���[�h */
	private static final String HOUSING_IMG_MODE = "housingImg";
	/** ���t�H�[���摜���[�h */
	private static final String REFORM_IMG_MODE = "reformImg";

	/** ���ʃR�[�h�ϊ����� */
	private CodeLookupManager codeLookupManager;
	/** ���ʃp�����[�^��� */
	private PanaCommonParameters commonParameters;
	/** �t�@�C�������֘A����Util */
	private PanaFileUtil panaFileUtil;
	/** �������[�h */
	private String mode;
	/** �v���r���[�t���O */
	private boolean previewFlg;
	/** ����t���O */
	private boolean memberFlg;
	/** �������CD */
	private String housingKindCd;
	/** ���t�H�[��CD */
	private String reformCd;
	/** �^�C�g���̕\���t���O */
	private boolean titleDisplayFlg;
	/** �V���t���O */
	private boolean freshFlg;
	/** �ڍ׃R�����g */
	private String dtlComment;
	/** �V�X�e������CD */
	private String sysHousingCd;
	/** �����ԍ� */
	private String housingCd;
	/** ������ */
	private String displayHousingName;
	/** ���̕����͂������߃��t�H�[���v����������܂��t���O */
	private boolean reformPlanExists;
	/** �������̕\���t���O */
	private boolean housingInfoDisplayFlg;
	/** �A�C�R�����yhidden�z�u�z��v */
	private String[] iconCd;
	/** �������i */
	private String price;
	/** �������i�yhidden�z */
	private String priceHidden;
	/** �s���{���� */
	private String prefName;
	/** ���ݒn */
	private String address;
	/** �A�N�Z�X�u�z��v */
	private String[] access;
	/** �}���V�����ʐσt���O */
	private boolean personalAreaFlg;
	/** �Ԏ�CD */
	private String layoutCd;
	/** ��L�ʐ� */
	private String personalArea;
	/** ��L�ʐ� �� */
	private String personalAreaSquare;
	/** ��L�ʐ�_�⑫ */
	private String personalAreaMemo;
	/** �ˌ� �y�n�ʐσt���O */
	private boolean buildingAreaFlg;
	/** �����ʐ� */
	private String buildingArea;
	/** �����ʐ� �� */
	private String buildingAreaSquare;
	/** �����ʐ�_�⑫ */
	private String buildingAreaMemo;
	/** �y�n�ʐ� */
	private String landArea;
	/** �y�n�ʐ� �� */
	private String landAreaSquare;
	/** �y�n�ʐ�_�⑫ */
	private String landAreaMemo;
	/** �z�N�� */
	private String compDate;
	/** �K���^���݊K */
	private String floor;
	/** �K���^���݊K�t���O */
	private boolean floorFlg;
	/** ���Ӓn�} */
	private String mapUrl;
	/** �����̃��t�H�[���v�����̕\���t���O */
	private boolean reformPlanDisplayFlg;
	/** ���t�H�[���v���������������̕\���t���O */
	private boolean reformPlanReadyDisplayFlg;
	/** ���t�H�[���v�������������� */
	private String reformPlanReadyComment;
	/** �v�����ԍ��yhidden�z�u�z��v */
	private String[] planNoHidden;
	/** �v�����^�C�v�u�z��v */
	private String[] planType;
	/** ���t�H�[�����i�u�z��v */
	private String[] planPrice;
	/** ���z�P�u�z��v */
	private String[] totalPrice1;
	/** ���z�Q�u�z��v */
	private String[] totalPrice2;
	/** �V�X�e�����t�H�[��CD�yhidden�z�u�z��v */
	private String[] reformCdHidden;
	/** �V�X�e�����t�H�[��URL�yhidden�z�u�z��v */
	private String[] reformUrl;
	/** List of reform categories*/
	private String[] reformCategory;
	/** �摜�̕\���t���O */
	private boolean imgDisplayFlg;
	/** �摜�ԍ��yhidden�z�u�z��v */
	private String[] imgNoHidden;
	/** �p�X���P�yhidden�z�u�z��v */
	private String[] housingImgPath1Hidden;
	/** �p�X���Q�yhidden�z�u�z��v */
	private String[] housingImgPath2Hidden;
	/** ����p�X�yhidden�z */
	private String movieUrl;
	/** �R�����g�yhidden�z�u�z��v */
	private String[] housingImgCommentHidden;
	/** ���C�ɓ���o�^�t���O�̕\���t���O */
	private boolean favoriteDisplayFlg;
	/** ����̃R�����g�̕\���t���O */
	private boolean salesCommentDisplayFlg;
	/** ����̃R�����g */
	private String salesComment;
	/** �S���҂���̂������߂̕\���t���O */
	private boolean recommendDisplayFlg;
	/** �������߉摜�p�X�yhidden�z */
	private String staffImagePathName;
	/** �������ߓ��e */
	private String recommendComment;
	/** �S�� */
	private String staffName;
	/** ��Ж� */
	private String companyName;
	/** �x�X�� */
	private String branchName;
	/** �Ƌ��ԍ� */
	private String licenseNo;
	/** ���ω摜�t���O */
	private boolean introspectImgFlg;
	/** ����o�^�̕\���t���O */
	private boolean loginDisplayFlg;
	/** �����ڍ׏��̕\���t���O */
	private boolean housingDtlInfoDisplayFlg;
	/** �C���t�� */
	private String infrastructure;
	/** ���؂��� */
	private String coverage;
	/** �o���R�j�[�ʐ� */
	private String balconyArea;
	/** �����ڍ׏�� ���l */
	private String upkeepCorp;
	/** ���n�� */
	private String moveinTiming;
	/** ���n�����R�����g */
	private String moveinNote;
	/** �Ǘ��`�� */
	private String upkeepType;
	/** �Ǘ�� */
	private String upkeep;
	/** �K�� */
	private String scale;
	/** ���� */
	private String status;
	/** �X�V�� */
	private String updDate;
	/** ����X�V�\�� */
	private String nextUpdDate;
	/** �\�� */
	private String struct;
	/** �������S */
	private String privateRoad;
	/** ��v�̌� */
	private String direction;
	/** ����`�� */
	private String transactTypeDiv;
	/** �C�U�ϗ��� */
	private String menteFee;
	/** �ړ� */
	private String contactRoad;
	/** �ړ�����/���� */
	private String contactRoadDir;
	/** ���ː� */
	private String totalHouseCnt;
	/** ���ԏ� */
	private String parkingSituation;
	/** �y�n���� */
	private String landRight;
	/** ���L���� */
	private String specialInstruction;
	/** �e�ϗ� */
	private String buildingRate;
	/** �p�r�n�� */
	private String usedAreaCd;
	/** ���r�ی� */
	private String insurExist;
	/** ���������̕\���t���O */
	private boolean housingPropertyDisplayFlg;
	/** �������� */
	private String equipName;
	/** �n����̕\���t���O */
	private boolean landmarkDisplayFlg;
	/** �n����ԍ��yhidden�z�u�z��v */
	private String[] landmarkNoHidden;
	/** �����h�}�[�N�̎�ށu�z��v */
	private String[] landmarkType;
	/** �n����i���́j�u�z��v */
	private String[] landmarkName;
	/** �n����i���v����/�����j�u�z��v */
	private String[] distanceFromLandmark;
	/** �������߃��t�H�[���v������̕\���t���O */
	private boolean recommendReformPlanDisplayFlg;
	/** ���t�H�[���v������ */
	private String planName;
	/** �Z�[���X�|�C���g */
	private String salesPoint;
	/** ���z�P */
	private String totalDtlPrice1;
	/** ���z�Q */
	private String totalDtlPrice2;
	/** ���t�H�[���ڍ�_�ԍ��yhidden�z�u�z��v */
	private String[] reformNoHidden;
	/** ���t�H�[���ڍ�_���ږ��́u�z��v */
	private String[] reformImgName;
	/** ���t�H�[���ڍ�_�摜�p�X���yhidden�z�u�z��v */
	private String[] reformPathName;
	/** ���t�H�[���ڍ�_���ڃ��t�H�[�����i�u�z��v */
	private String[] reformPrice;
	/** �H�� */
	private String constructionPeriod;
	/** ���t�H�[�� ���l */
	private String reformNote;
	/** ���t�H�[���C���[�W�̕\���t���O */
	private boolean reformImgDisplayFlg;
	/** ���t�H�[����_�O�ρE���ӎʐ^�ԍ��yhidden�z�u�z��v */
	private String[] afterPathNoHidden;
	/** ���t�H�[����_�O�ρE���ӎʐ^�p�X�P�yhidden�z�u�z��v */
	private String[] afterPath1;
	/** ���t�H�[����_�O�ρE���ӎʐ^�p�X�Q�yhidden�z�u�z��v */
	private String[] afterPath2;
	/** ���t�H�[����_�O�ρE���ӎʐ^ �R�����g�yhidden�z�u�z��v */
	private String[] afterPathComment;
	/** ���t�H�[����_����p�T���l�C���yhidden�z */
	private String afterMovieUrl;
	/** ���t�H�[���O_�O�ρE���ӎʐ^�ԍ��yhidden�z�u�z��v */
	private String[] beforePathNoHidden;
	/** ���t�H�[���O_�O�ρE���ӎʐ^�p�X�P�yhidden�z�u�z��v */
	private String[] beforePath1;
	/** ���t�H�[���O_�O�ρE���ӎʐ^�p�X�Q�yhidden�z�u�z��v */
	private String[] beforePath2;
	/** ���t�H�[���O_�O�ρE���ӎʐ^ �R�����g�yhidden�z�u�z��v */
	private String[] beforePathComment;
	/** ���t�H�[���O_����p�T���l�C���yhidden�z */
	private String beforeMovieUrl;
	/** �Z��f�f���̕\���t���O */
	private boolean housingInspectionDisplayFlg;
	/** �Z��f�f���{�� */
	private String inspectionExist;
	/** �m�F���x���ԍ��yhidden�z�u�z��v */
	private String[] inspectionNoHidden;
	/** �m�F���x���i���́j�u�z��v */
	private String[] inspectionKey;
	/** �m�F���x���u�z��v */
	private String[] inspectionTrust;
	/** �Z��f�f���}�yhidden�z */
	private String inspectionImagePathName;
	/** �Z��f�f�t�@�C���yhidden�z */
	private String inspectionPathName;
	/** ���̑��̃��t�H�[���v�����̕\���t���O */
	private boolean otherReformPlanDisplayFlg;
	/** ���̑��̃v�����ԍ��yhidden�z�u�z��v */
	private String[] otherPlanNoHidden;
	/** ���̑��̃v�����^�C�v�u�z��v */
	private String[] otherPlanType;
	/** ���̑��̑��z�P�u�z��v */
	private String[] otherTotalPrice1;
	/** ���̑��̑��z�Q�u�z��v */
	private String[] otherTotalPrice2;
	/** ���̑��̃V�X�e�����t�H�[��CD�yhidden�z�u�z��v */
	private String[] otherReformCdHidden;
	/** ���̑��̃V�X�e�����t�H�[��URL�yhidden�z�u�z��v */
	private String[] otherReformUrl;
	/** �ŋߌ��������̕\���t���O */
	private boolean recentlyDisplayFlg;
	/** �ŋ� �����ԍ��yhidden�z�u�z��v */
	private String[] recentlyNoHidden;
	/** �ŋ� �V�X�e������CD�yhidden�z�u�z��v */
	private String[] recentlySysHousingCdHidden;
	/** �ŋ� �����ԍ��ydata-number�z�u�z��v */
	private String[] recentlyHousingCdHidden;
	/** �ŋ� �����摜�u�z��v */
	private String[] recentlyPathName;
	/** �ŋ� �������CD�u�z��v */
	private String[] recentlyHousingKindCd;
	/** �ŋ� �������u�z��v */
	private String[] recentlyDisplayHousingName;
	/** �ŋ� ������FULL�u�z��v */
	private String[] recentlyDisplayHousingNameFull;
	/** �ŋ� �����ڍׁu�z��v */
	private String[] recentlyDtl;
	/** �ŋ� �����ڍ�FULL�u�z��v */
	private String[] recentlyDtlFull;
	/** �ŋ� ����URL�u�z��v */
	private String[] recentlyUrl;
	/** �L�[���[�h */
	private String keywords;
	/** ���� */
	private String description;
	/** �{�y�[�WURL */
	private String currentUrl;
	/** �Č���URL�u�z��v */
	private String[] researchUrl;
	/** �Č����s���{�����u�z��v */
	private String[] researchPrefName;

	/**
	 * ���ʃR�[�h�ϊ����� ���擾����B<br/>
	 * <br/>
	 * @return ���ʃR�[�h�ϊ�����
	 */
	public CodeLookupManager getCodeLookupManager() {
		return codeLookupManager;
	}

	/**
	 * ���ʃR�[�h�ϊ����� ��ݒ肷��B<br/>
	 * <br/>
	 * @param codeLookupManager
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * ���ʃp�����[�^��� ���擾����B<br/>
	 * <br/>
	 * @return ���ʃp�����[�^���
	 */
	public PanaCommonParameters getCommonParameters() {
		return commonParameters;
	}

	/**
	 * ���ʃp�����[�^��� ��ݒ肷��B<br/>
	 * <br/>
	 * @param commonParameters
	 */
	public void setCommonParameters(PanaCommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * �t�@�C�������֘A����Util ���擾����B<br/>
	 * <br/>
	 * @return �t�@�C�������֘A����Util
	 */
	public PanaFileUtil getPanaFileUtil() {
		return panaFileUtil;
	}

	/**
	 * �t�@�C�������֘A����Util ��ݒ肷��B<br/>
	 * <br/>
	 * @param panaFileUtil
	 */
	public void setPanaFileUtil(PanaFileUtil panaFileUtil) {
		this.panaFileUtil = panaFileUtil;
	}

	/**
	 * �������[�h ���擾����B<br/>
	 * <br/>
	 * @return �������[�h
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * �������[�h ��ݒ肷��B<br/>
	 * <br/>
	 * @param mode
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * �v���r���[�t���O ���擾����B<br/>
	 * <br/>
	 * @return �v���r���[�t���O
	 */
	public boolean isPreviewFlg() {
		return previewFlg;
	}

	/**
	 * �v���r���[�t���O ��ݒ肷��B<br/>
	 * <br/>
	 * @param previewFlg
	 */
	public void setPreviewFlg(boolean previewFlg) {
		this.previewFlg = previewFlg;
	}

	/**
	 * ����t���O ���擾����B<br/>
	 * <br/>
	 * @return ����t���O
	 */
	public boolean isMemberFlg() {
		return memberFlg;
	}

	/**
	 * ����t���O ��ݒ肷��B<br/>
	 * <br/>
	 * @param memberFlg
	 */
	public void setMemberFlg(boolean memberFlg) {
		this.memberFlg = memberFlg;
	}

	/**
	 * �������CD ���擾����B<br/>
	 * <br/>
	 * @return �������CD
	 */
	public String getHousingKindCd() {
		return housingKindCd;
	}

	/**
	 * �������CD ��ݒ肷��B<br/>
	 * <br/>
	 * @param housingKindCd
	 */
	public void setHousingKindCd(String housingKindCd) {
		this.housingKindCd = housingKindCd;
	}

	/**
	 * ���t�H�[��CD ���擾����B<br/>
	 * <br/>
	 * @return ���t�H�[��CD
	 */
	public String getReformCd() {
		return reformCd;
	}

	/**
	 * ���t�H�[��CD ��ݒ肷��B<br/>
	 * <br/>
	 * @param reformCd
	 */
	public void setReformCd(String reformCd) {
		this.reformCd = reformCd;
	}

	/**
	 * �^�C�g���̕\���t���O ���擾����B<br/>
	 * <br/>
	 * @return �^�C�g���̕\���t���O
	 */
	public boolean isTitleDisplayFlg() {
		return titleDisplayFlg;
	}

	/**
	 * �^�C�g���̕\���t���O ��ݒ肷��B<br/>
	 * <br/>
	 * @param titleDisplayFlg
	 */
	public void setTitleDisplayFlg(boolean titleDisplayFlg) {
		this.titleDisplayFlg = titleDisplayFlg;
	}

	/**
	 * �V���t���O ���擾����B<br/>
	 * <br/>
	 * @return �V���t���O
	 */
	public boolean isFreshFlg() {
		return freshFlg;
	}

	/**
	 * �V���t���O ��ݒ肷��B<br/>
	 * <br/>
	 * @param freshFlg
	 */
	public void setFreshFlg(boolean freshFlg) {
		this.freshFlg = freshFlg;
	}

	/**
	 * �ڍ׃R�����g ���擾����B<br/>
	 * <br/>
	 * @return �ڍ׃R�����g
	 */
	public String getDtlComment() {
		return dtlComment;
	}

	/**
	 * �ڍ׃R�����g ��ݒ肷��B<br/>
	 * <br/>
	 * @param dtlComment
	 */
	public void setDtlComment(String dtlComment) {
		this.dtlComment = dtlComment;
	}

	/**
	 * �V�X�e������CD ���擾����B<br/>
	 * <br/>
	 * @return �V�X�e������CD
	 */
	public String getSysHousingCd() {
		return sysHousingCd;
	}

	/**
	 * �V�X�e������CD ��ݒ肷��B<br/>
	 * <br/>
	 * @param sysHousingCd
	 */
	public void setSysHousingCd(String sysHousingCd) {
		this.sysHousingCd = sysHousingCd;
	}

	/**
	 * �����ԍ� ���擾����B<br/>
	 * <br/>
	 * @return �����ԍ�
	 */
	public String getHousingCd() {
		return housingCd;
	}

	/**
	 * �����ԍ� ��ݒ肷��B<br/>
	 * <br/>
	 * @param housingCd
	 */
	public void setHousingCd(String housingCd) {
		this.housingCd = housingCd;
	}

	/**
	 * ������ ���擾����B<br/>
	 * <br/>
	 * @return ������
	 */
	public String getDisplayHousingName() {
		return displayHousingName;
	}

	/**
	 * ������ ��ݒ肷��B<br/>
	 * <br/>
	 * @param displayHousingName
	 */
	public void setDisplayHousingName(String displayHousingName) {
		this.displayHousingName = displayHousingName;
	}

	/**
	 * ���̕����͂������߃��t�H�[���v����������܂��t���O ���擾����B<br/>
	 * <br/>
	 * @return ���̕����͂������߃��t�H�[���v����������܂��t���O
	 */
	public boolean isReformPlanExists() {
		return reformPlanExists;
	}

	/**
	 * ���̕����͂������߃��t�H�[���v����������܂��t���O ��ݒ肷��B<br/>
	 * <br/>
	 * @param reformPlanExists
	 */
	public void setReformPlanExists(boolean reformPlanExists) {
		this.reformPlanExists = reformPlanExists;
	}

	/**
	 * �������̕\���t���O ���擾����B<br/>
	 * <br/>
	 * @return �������̕\���t���O
	 */
	public boolean isHousingInfoDisplayFlg() {
		return housingInfoDisplayFlg;
	}

	/**
	 * �������̕\���t���O ��ݒ肷��B<br/>
	 * <br/>
	 * @param housingInfoDisplayFlg
	 */
	public void setHousingInfoDisplayFlg(boolean housingInfoDisplayFlg) {
		this.housingInfoDisplayFlg = housingInfoDisplayFlg;
	}

	/**
	 * �A�C�R�����yhidden�z�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return �A�C�R�����yhidden�z�u�z��v
	 */
	public String[] getIconCd() {
		return iconCd;
	}

	/**
	 * �A�C�R�����yhidden�z�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param iconCd
	 */
	public void setIconCd(String[] iconCd) {
		this.iconCd = iconCd;
	}

	/**
	 * �������i ���擾����B<br/>
	 * <br/>
	 * @return �������i
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * �������i ��ݒ肷��B<br/>
	 * <br/>
	 * @param price
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * �������i�yhidden�z ���擾����B<br/>
	 * <br/>
	 * @return �������i�yhidden�z
	 */
	public String getPriceHidden() {
		return priceHidden;
	}

	/**
	 * �������i�yhidden�z ��ݒ肷��B<br/>
	 * <br/>
	 * @param priceHidden
	 */
	public void setPriceHidden(String priceHidden) {
		this.priceHidden = priceHidden;
	}

	/**
	 * �s���{���� ���擾����B<br/>
	 * <br/>
	 * @return �s���{����
	 */
	public String getPrefName() {
		return prefName;
	}

	/**
	 * �s���{���� ��ݒ肷��B<br/>
	 * <br/>
	 * @param prefName
	 */
	public void setPrefName(String prefName) {
		this.prefName = prefName;
	}

	/**
	 * ���ݒn ���擾����B<br/>
	 * <br/>
	 * @return ���ݒn
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * ���ݒn ��ݒ肷��B<br/>
	 * <br/>
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * �A�N�Z�X�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return �A�N�Z�X�u�z��v
	 */
	public String[] getAccess() {
		return access;
	}

	/**
	 * �A�N�Z�X�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param access
	 */
	public void setAccess(String[] access) {
		this.access = access;
	}

	/**
	 * �}���V�����ʐσt���O ���擾����B<br/>
	 * <br/>
	 * @return �}���V�����ʐσt���O
	 */
	public boolean isPersonalAreaFlg() {
		return personalAreaFlg;
	}

	/**
	 * �}���V�����ʐσt���O ��ݒ肷��B<br/>
	 * <br/>
	 * @param personalAreaFlg
	 */
	public void setPersonalAreaFlg(boolean personalAreaFlg) {
		this.personalAreaFlg = personalAreaFlg;
	}

	/**
	 * �Ԏ�CD ���擾����B<br/>
	 * <br/>
	 * @return �Ԏ�CD
	 */
	public String getLayoutCd() {
		return layoutCd;
	}

	/**
	 * �Ԏ�CD ��ݒ肷��B<br/>
	 * <br/>
	 * @param layoutCd
	 */
	public void setLayoutCd(String layoutCd) {
		this.layoutCd = layoutCd;
	}

	/**
	 * ��L�ʐ� ���擾����B<br/>
	 * <br/>
	 * @return ��L�ʐ�
	 */
	public String getPersonalArea() {
		return personalArea;
	}

	/**
	 * ��L�ʐ� ��ݒ肷��B<br/>
	 * <br/>
	 * @param personalArea
	 */
	public void setPersonalArea(String personalArea) {
		this.personalArea = personalArea;
	}

	/**
	 * ��L�ʐ� �� ���擾����B<br/>
	 * <br/>
	 * @return ��L�ʐ� ��
	 */
	public String getPersonalAreaSquare() {
		return personalAreaSquare;
	}

	/**
	 * ��L�ʐ� �� ��ݒ肷��B<br/>
	 * <br/>
	 * @param personalAreaSquare
	 */
	public void setPersonalAreaSquare(String personalAreaSquare) {
		this.personalAreaSquare = personalAreaSquare;
	}

	/**
	 * ��L�ʐ�_�⑫ ���擾����B<br/>
	 * <br/>
	 * @return ��L�ʐ�_�⑫
	 */
	public String getPersonalAreaMemo() {
		return personalAreaMemo;
	}

	/**
	 * ��L�ʐ�_�⑫ ��ݒ肷��B<br/>
	 * <br/>
	 * @param personalAreaMemo
	 */
	public void setPersonalAreaMemo(String personalAreaMemo) {
		this.personalAreaMemo = personalAreaMemo;
	}

	/**
	 * �ˌ� �y�n�ʐσt���O ���擾����B<br/>
	 * <br/>
	 * @return �ˌ� �y�n�ʐσt���O
	 */
	public boolean isBuildingAreaFlg() {
		return buildingAreaFlg;
	}

	/**
	 * �ˌ� �y�n�ʐσt���O ��ݒ肷��B<br/>
	 * <br/>
	 * @param buildingAreaFlg
	 */
	public void setBuildingAreaFlg(boolean buildingAreaFlg) {
		this.buildingAreaFlg = buildingAreaFlg;
	}

	/**
	 * �����ʐ� ���擾����B<br/>
	 * <br/>
	 * @return �����ʐ�
	 */
	public String getBuildingArea() {
		return buildingArea;
	}

	/**
	 * �����ʐ� ��ݒ肷��B<br/>
	 * <br/>
	 * @param buildingArea
	 */
	public void setBuildingArea(String buildingArea) {
		this.buildingArea = buildingArea;
	}

	/**
	 * �����ʐ� �� ���擾����B<br/>
	 * <br/>
	 * @return �����ʐ� ��
	 */
	public String getBuildingAreaSquare() {
		return buildingAreaSquare;
	}

	/**
	 * �����ʐ� �� ��ݒ肷��B<br/>
	 * <br/>
	 * @param buildingAreaSquare
	 */
	public void setBuildingAreaSquare(String buildingAreaSquare) {
		this.buildingAreaSquare = buildingAreaSquare;
	}

	/**
	 * �����ʐ�_�⑫ ���擾����B<br/>
	 * <br/>
	 * @return �����ʐ�_�⑫
	 */
	public String getBuildingAreaMemo() {
		return buildingAreaMemo;
	}

	/**
	 * �����ʐ�_�⑫ ��ݒ肷��B<br/>
	 * <br/>
	 * @param buildingAreaMemo
	 */
	public void setBuildingAreaMemo(String buildingAreaMemo) {
		this.buildingAreaMemo = buildingAreaMemo;
	}

	/**
	 * �y�n�ʐ� ���擾����B<br/>
	 * <br/>
	 * @return �y�n�ʐ�
	 */
	public String getLandArea() {
		return landArea;
	}

	/**
	 * �y�n�ʐ� ��ݒ肷��B<br/>
	 * <br/>
	 * @param landArea
	 */
	public void setLandArea(String landArea) {
		this.landArea = landArea;
	}

	/**
	 * �y�n�ʐ� �� ���擾����B<br/>
	 * <br/>
	 * @return �y�n�ʐ� ��
	 */
	public String getLandAreaSquare() {
		return landAreaSquare;
	}

	/**
	 * �y�n�ʐ� �� ��ݒ肷��B<br/>
	 * <br/>
	 * @param landAreaSquare
	 */
	public void setLandAreaSquare(String landAreaSquare) {
		this.landAreaSquare = landAreaSquare;
	}

	/**
	 * �y�n�ʐ�_�⑫ ���擾����B<br/>
	 * <br/>
	 * @return �y�n�ʐ�_�⑫
	 */
	public String getLandAreaMemo() {
		return landAreaMemo;
	}

	/**
	 * �y�n�ʐ�_�⑫ ��ݒ肷��B<br/>
	 * <br/>
	 * @param landAreaMemo
	 */
	public void setLandAreaMemo(String landAreaMemo) {
		this.landAreaMemo = landAreaMemo;
	}

	/**
	 * �z�N�� ���擾����B<br/>
	 * <br/>
	 * @return �z�N��
	 */
	public String getCompDate() {
		return compDate;
	}

	/**
	 * �z�N�� ��ݒ肷��B<br/>
	 * <br/>
	 * @param compDate
	 */
	public void setCompDate(String compDate) {
		this.compDate = compDate;
	}

	/**
	 * �K���^���݊K ���擾����B<br/>
	 * <br/>
	 * @return �K���^���݊K
	 */
	public String getFloor() {
		return floor;
	}

	/**
	 * �K���^���݊K ��ݒ肷��B<br/>
	 * <br/>
	 * @param floor
	 */
	public void setFloor(String floor) {
		this.floor = floor;
	}

	/**
	 * �K���^���݊K�t���O ���擾����B<br/>
	 * <br/>
	 * @return �K���^���݊K�t���O
	 */
	public boolean isFloorFlg() {
		return floorFlg;
	}

	/**
	 * �K���^���݊K�t���O ��ݒ肷��B<br/>
	 * <br/>
	 * @param floorFlg
	 */
	public void setFloorFlg(boolean floorFlg) {
		this.floorFlg = floorFlg;
	}

	/**
	 * ���Ӓn�} ���擾����B<br/>
	 * <br/>
	 * @return ���Ӓn�}
	 */
	public String getMapUrl() {
		return mapUrl;
	}

	/**
	 * ���Ӓn�} ��ݒ肷��B<br/>
	 * <br/>
	 * @param mapUrl
	 */
	public void setMapUrl(String mapUrl) {
		this.mapUrl = mapUrl;
	}

	/**
	 * �����̃��t�H�[���v�����̕\���t���O ���擾����B<br/>
	 * <br/>
	 * @return �����̃��t�H�[���v�����̕\���t���O
	 */
	public boolean isReformPlanDisplayFlg() {
		return reformPlanDisplayFlg;
	}

	/**
	 * �����̃��t�H�[���v�����̕\���t���O ��ݒ肷��B<br/>
	 * <br/>
	 * @param reformPlanDisplayFlg
	 */
	public void setReformPlanDisplayFlg(boolean reformPlanDisplayFlg) {
		this.reformPlanDisplayFlg = reformPlanDisplayFlg;
	}

	/**
	 * ���t�H�[���v���������������̕\���t���O ���擾����B<br/>
	 * <br/>
	 * @return ���t�H�[���v���������������̕\���t���O
	 */
	public boolean isReformPlanReadyDisplayFlg() {
		return reformPlanReadyDisplayFlg;
	}

	/**
	 * ���t�H�[���v���������������̕\���t���O ��ݒ肷��B<br/>
	 * <br/>
	 * @param reformPlanReadyDisplayFlg
	 */
	public void setReformPlanReadyDisplayFlg(boolean reformPlanReadyDisplayFlg) {
		this.reformPlanReadyDisplayFlg = reformPlanReadyDisplayFlg;
	}

	/**
	 * ���t�H�[���v�������������� ���擾����B<br/>
	 * <br/>
	 * @return ���t�H�[���v��������������
	 */
	public String getReformPlanReadyComment() {
		return reformPlanReadyComment;
	}

	/**
	 * ���t�H�[���v�������������� ��ݒ肷��B<br/>
	 * <br/>
	 * @param reformPlanReadyComment
	 */
	public void setReformPlanReadyComment(String reformPlanReadyComment) {
		this.reformPlanReadyComment = reformPlanReadyComment;
	}

	/**
	 * �v�����ԍ��yhidden�z�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return �v�����ԍ��yhidden�z�u�z��v
	 */
	public String[] getPlanNoHidden() {
		return planNoHidden;
	}

	/**
	 * �v�����ԍ��yhidden�z�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param planNoHidden
	 */
	public void setPlanNoHidden(String[] planNoHidden) {
		this.planNoHidden = planNoHidden;
	}

	/**
	 * �v�����^�C�v�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return �v�����^�C�v�u�z��v
	 */
	public String[] getPlanType() {
		return planType;
	}

	/**
	 * �v�����^�C�v�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param planType
	 */
	public void setPlanType(String[] planType) {
		this.planType = planType;
	}

	/**
	 * ���t�H�[�����i�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return ���t�H�[�����i�u�z��v
	 */
	public String[] getPlanPrice() {
		return planPrice;
	}

	/**
	 * ���t�H�[�����i�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param planPrice
	 */
	public void setPlanPrice(String[] planPrice) {
		this.planPrice = planPrice;
	}

	/**
	 * ���z�P�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return ���z�P�u�z��v
	 */
	public String[] getTotalPrice1() {
		return totalPrice1;
	}

	/**
	 * ���z�P�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param totalPrice1
	 */
	public void setTotalPrice1(String[] totalPrice1) {
		this.totalPrice1 = totalPrice1;
	}

	/**
	 * ���z�Q�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return ���z�Q�u�z��v
	 */
	public String[] getTotalPrice2() {
		return totalPrice2;
	}

	/**
	 * ���z�Q�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param totalPrice2
	 */
	public void setTotalPrice2(String[] totalPrice2) {
		this.totalPrice2 = totalPrice2;
	}

	/**
	 * �V�X�e�����t�H�[��CD�yhidden�z�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return �V�X�e�����t�H�[��CD�yhidden�z�u�z��v
	 */
	public String[] getReformCdHidden() {
		return reformCdHidden;
	}

	/**
	 * �V�X�e�����t�H�[��CD�yhidden�z�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param reformCdHidden
	 */
	public void setReformCdHidden(String[] reformCdHidden) {
		this.reformCdHidden = reformCdHidden;
	}

	/**
	 * �V�X�e�����t�H�[��URL�yhidden�z�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return �V�X�e�����t�H�[��URL�yhidden�z�u�z��v
	 */
	public String[] getReformUrl() {
		return reformUrl;
	}

	/**
	 * �V�X�e�����t�H�[��URL�yhidden�z�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param reformUrl
	 */
	public void setReformUrl(String[] reformUrl) {
		this.reformUrl = reformUrl;
	}

	public void setReformCategory(String[] reformCategory){
	    this.reformCategory = reformCategory;
	}
	/**
     * @return the reformCategory
     */
    public String[] getReformCategory() {
        return reformCategory;
    }

    /**
	 * �摜�̕\���t���O ���擾����B<br/>
	 * <br/>
	 * @return �摜�̕\���t���O
	 */
	public boolean isImgDisplayFlg() {
		return imgDisplayFlg;
	}

	/**
	 * �摜�̕\���t���O ��ݒ肷��B<br/>
	 * <br/>
	 * @param imgDisplayFlg
	 */
	public void setImgDisplayFlg(boolean imgDisplayFlg) {
		this.imgDisplayFlg = imgDisplayFlg;
	}

	/**
	 * �摜�ԍ��yhidden�z�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return �摜�ԍ��yhidden�z�u�z��v
	 */
	public String[] getImgNoHidden() {
		return imgNoHidden;
	}

	/**
	 * �摜�ԍ��yhidden�z�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param imgNoHidden
	 */
	public void setImgNoHidden(String[] imgNoHidden) {
		this.imgNoHidden = imgNoHidden;
	}

	/**
	 * �p�X���P�yhidden�z�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return �p�X���P�yhidden�z�u�z��v
	 */
	public String[] getHousingImgPath1Hidden() {
		return housingImgPath1Hidden;
	}

	/**
	 * �p�X���P�yhidden�z�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param housingImgPath1Hidden
	 */
	public void setHousingImgPath1Hidden(String[] housingImgPath1Hidden) {
		this.housingImgPath1Hidden = housingImgPath1Hidden;
	}

	/**
	 * �p�X���Q�yhidden�z�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return �p�X���Q�yhidden�z�u�z��v
	 */
	public String[] getHousingImgPath2Hidden() {
		return housingImgPath2Hidden;
	}

	/**
	 * �p�X���Q�yhidden�z�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param housingImgPath2Hidden
	 */
	public void setHousingImgPath2Hidden(String[] housingImgPath2Hidden) {
		this.housingImgPath2Hidden = housingImgPath2Hidden;
	}

	/**
	 * ����p�X�yhidden�z ���擾����B<br/>
	 * <br/>
	 * @return ����p�X�yhidden�z
	 */
	public String getMovieUrl() {
		return movieUrl;
	}

	/**
	 * ����p�X�yhidden�z ��ݒ肷��B<br/>
	 * <br/>
	 * @param movieUrl
	 */
	public void setMovieUrl(String movieUrl) {
		this.movieUrl = movieUrl;
	}

	/**
	 * �R�����g�yhidden�z�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return �R�����g�yhidden�z�u�z��v
	 */
	public String[] getHousingImgCommentHidden() {
		return housingImgCommentHidden;
	}

	/**
	 * �R�����g�yhidden�z�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param housingImgCommentHidden
	 */
	public void setHousingImgCommentHidden(String[] housingImgCommentHidden) {
		this.housingImgCommentHidden = housingImgCommentHidden;
	}

	/**
	 * ���C�ɓ���o�^�t���O�̕\���t���O ���擾����B<br/>
	 * <br/>
	 * @return ���C�ɓ���o�^�t���O�̕\���t���O
	 */
	public boolean isFavoriteDisplayFlg() {
		return favoriteDisplayFlg;
	}

	/**
	 * ���C�ɓ���o�^�t���O�̕\���t���O ��ݒ肷��B<br/>
	 * <br/>
	 * @param favoriteDisplayFlg
	 */
	public void setFavoriteDisplayFlg(boolean favoriteDisplayFlg) {
		this.favoriteDisplayFlg = favoriteDisplayFlg;
	}

	/**
	 * ����̃R�����g�̕\���t���O ���擾����B<br/>
	 * <br/>
	 * @return ����̃R�����g�̕\���t���O
	 */
	public boolean isSalesCommentDisplayFlg() {
		return salesCommentDisplayFlg;
	}

	/**
	 * ����̃R�����g�̕\���t���O ��ݒ肷��B<br/>
	 * <br/>
	 * @param salesCommentDisplayFlg
	 */
	public void setSalesCommentDisplayFlg(boolean salesCommentDisplayFlg) {
		this.salesCommentDisplayFlg = salesCommentDisplayFlg;
	}

	/**
	 * ����̃R�����g ���擾����B<br/>
	 * <br/>
	 * @return ����̃R�����g
	 */
	public String getSalesComment() {
		return salesComment;
	}

	/**
	 * ����̃R�����g ��ݒ肷��B<br/>
	 * <br/>
	 * @param salesComment
	 */
	public void setSalesComment(String salesComment) {
		this.salesComment = salesComment;
	}

	/**
	 * �S���҂���̂������߂̕\���t���O ���擾����B<br/>
	 * <br/>
	 * @return �S���҂���̂������߂̕\���t���O
	 */
	public boolean isRecommendDisplayFlg() {
		return recommendDisplayFlg;
	}

	/**
	 * �S���҂���̂������߂̕\���t���O ��ݒ肷��B<br/>
	 * <br/>
	 * @param recommendDisplayFlg
	 */
	public void setRecommendDisplayFlg(boolean recommendDisplayFlg) {
		this.recommendDisplayFlg = recommendDisplayFlg;
	}

	/**
	 * �������߉摜�p�X�yhidden�z ���擾����B<br/>
	 * <br/>
	 * @return �������߉摜�p�X�yhidden�z
	 */
	public String getStaffimagePathName() {
		return staffImagePathName;
	}

	/**
	 * �������߉摜�p�X�yhidden�z ��ݒ肷��B<br/>
	 * <br/>
	 * @param staffImagePathName
	 */
	public void setStaffimagePathName(String staffImagePathName) {
		this.staffImagePathName = staffImagePathName;
	}

	/**
	 * �������ߓ��e ���擾����B<br/>
	 * <br/>
	 * @return �������ߓ��e
	 */
	public String getRecommendComment() {
		return recommendComment;
	}

	/**
	 * �������ߓ��e ��ݒ肷��B<br/>
	 * <br/>
	 * @param recommendComment
	 */
	public void setRecommendComment(String recommendComment) {
		this.recommendComment = recommendComment;
	}

	/**
	 * �S�� ���擾����B<br/>
	 * <br/>
	 * @return �S��
	 */
	public String getStaffName() {
		return staffName;
	}

	/**
	 * �S�� ��ݒ肷��B<br/>
	 * <br/>
	 * @param staffName
	 */
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	/**
	 * ��Ж� ���擾����B<br/>
	 * <br/>
	 * @return ��Ж�
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * ��Ж� ��ݒ肷��B<br/>
	 * <br/>
	 * @param companyName
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * �x�X�� ���擾����B<br/>
	 * <br/>
	 * @return �x�X��
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * �x�X�� ��ݒ肷��B<br/>
	 * <br/>
	 * @param branchName
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * �Ƌ��ԍ� ���擾����B<br/>
	 * <br/>
	 * @return �Ƌ��ԍ�
	 */
	public String getLicenseNo() {
		return licenseNo;
	}

	/**
	 * �Ƌ��ԍ� ��ݒ肷��B<br/>
	 * <br/>
	 * @param licenseNo
	 */
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	/**
	 * ���ω摜�t���O ���擾����B<br/>
	 * <br/>
	 * @return ���ω摜�t���O
	 */
	public boolean isIntrospectImgFlg() {
		return introspectImgFlg;
	}

	/**
	 * ���ω摜�t���O ��ݒ肷��B<br/>
	 * <br/>
	 * @param introspectImgFlg
	 */
	public void setIntrospectImgFlg(boolean introspectImgFlg) {
		this.introspectImgFlg = introspectImgFlg;
	}

	/**
	 * ����o�^�̕\���t���O ���擾����B<br/>
	 * <br/>
	 * @return ����o�^�̕\���t���O
	 */
	public boolean isLoginDisplayFlg() {
		return loginDisplayFlg;
	}

	/**
	 * ����o�^�̕\���t���O ��ݒ肷��B<br/>
	 * <br/>
	 * @param loginDisplayFlg
	 */
	public void setLoginDisplayFlg(boolean loginDisplayFlg) {
		this.loginDisplayFlg = loginDisplayFlg;
	}

	/**
	 * �����ڍ׏��̕\���t���O ���擾����B<br/>
	 * <br/>
	 * @return �����ڍ׏��̕\���t���O
	 */
	public boolean isHousingDtlInfoDisplayFlg() {
		return housingDtlInfoDisplayFlg;
	}

	/**
	 * �����ڍ׏��̕\���t���O ��ݒ肷��B<br/>
	 * <br/>
	 * @param housingDtlInfoDisplayFlg
	 */
	public void setHousingDtlInfoDisplayFlg(boolean housingDtlInfoDisplayFlg) {
		this.housingDtlInfoDisplayFlg = housingDtlInfoDisplayFlg;
	}

	/**
	 * �C���t�� ���擾����B<br/>
	 * <br/>
	 * @return �C���t��
	 */
	public String getInfrastructure() {
		return infrastructure;
	}

	/**
	 * �C���t�� ��ݒ肷��B<br/>
	 * <br/>
	 * @param infrastructure
	 */
	public void setInfrastructure(String infrastructure) {
		this.infrastructure = infrastructure;
	}

	/**
	 * ���؂��� ���擾����B<br/>
	 * <br/>
	 * @return ���؂���
	 */
	public String getCoverage() {
		return coverage;
	}

	/**
	 * ���؂��� ��ݒ肷��B<br/>
	 * <br/>
	 * @param coverage
	 */
	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}

	/**
	 * �o���R�j�[�ʐ� ���擾����B<br/>
	 * <br/>
	 * @return �o���R�j�[�ʐ�
	 */
	public String getBalconyArea() {
		return balconyArea;
	}

	/**
	 * �o���R�j�[�ʐ� ��ݒ肷��B<br/>
	 * <br/>
	 * @param balconyArea
	 */
	public void setBalconyArea(String balconyArea) {
		this.balconyArea = balconyArea;
	}

	/**
	 * �����ڍ׏�� ���l ���擾����B<br/>
	 * <br/>
	 * @return �����ڍ׏�� ���l
	 */
	public String getUpkeepCorp() {
		return upkeepCorp;
	}

	/**
	 * �����ڍ׏�� ���l ��ݒ肷��B<br/>
	 * <br/>
	 * @param upkeepCorp
	 */
	public void setUpkeepCorp(String upkeepCorp) {
		this.upkeepCorp = upkeepCorp;
	}

	/**
	 * ���n�� ���擾����B<br/>
	 * <br/>
	 * @return ���n��
	 */
	public String getMoveinTiming() {
		return moveinTiming;
	}

	/**
	 * ���n�� ��ݒ肷��B<br/>
	 * <br/>
	 * @param moveinTiming
	 */
	public void setMoveinTiming(String moveinTiming) {
		this.moveinTiming = moveinTiming;
	}

	/**
	 * ���n�����R�����g ���擾����B<br/>
	 * <br/>
	 * @return ���n�����R�����g
	 */
	public String getMoveinNote() {
		return moveinNote;
	}

	/**
	 * ���n�����R�����g ��ݒ肷��B<br/>
	 * <br/>
	 * @param moveinNote
	 */
	public void setMoveinNote(String moveinNote) {
		this.moveinNote = moveinNote;
	}

	/**
	 * �Ǘ��`�� ���擾����B<br/>
	 * <br/>
	 * @return �Ǘ��`��
	 */
	public String getUpkeepType() {
		return upkeepType;
	}

	/**
	 * �Ǘ��`�� ��ݒ肷��B<br/>
	 * <br/>
	 * @param upkeepType
	 */
	public void setUpkeepType(String upkeepType) {
		this.upkeepType = upkeepType;
	}

	/**
	 * �Ǘ�� ���擾����B<br/>
	 * <br/>
	 * @return �Ǘ��
	 */
	public String getUpkeep() {
		return upkeep;
	}

	/**
	 * �Ǘ�� ��ݒ肷��B<br/>
	 * <br/>
	 * @param upkeep
	 */
	public void setUpkeep(String upkeep) {
		this.upkeep = upkeep;
	}

	/**
	 * �K�� ���擾����B<br/>
	 * <br/>
	 * @return �K��
	 */
	public String getScale() {
		return scale;
	}

	/**
	 * �K�� ��ݒ肷��B<br/>
	 * <br/>
	 * @param scale
	 */
	public void setScale(String scale) {
		this.scale = scale;
	}

	/**
	 * ���� ���擾����B<br/>
	 * <br/>
	 * @return ����
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * ���� ��ݒ肷��B<br/>
	 * <br/>
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * �X�V�� ���擾����B<br/>
	 * <br/>
	 * @return �X�V��
	 */
	public String getUpdDate() {
		return updDate;
	}

	/**
	 * �X�V�� ��ݒ肷��B<br/>
	 * <br/>
	 * @param updDate
	 */
	public void setUpdDate(String updDate) {
		this.updDate = updDate;
	}

	/**
	 * ����X�V�\�� ���擾����B<br/>
	 * <br/>
	 * @return ����X�V�\��
	 */
	public String getNextUpdDate() {
		return nextUpdDate;
	}

	/**
	 * ����X�V�\�� ��ݒ肷��B<br/>
	 * <br/>
	 * @param nextUpdDate
	 */
	public void setNextUpdDate(String nextUpdDate) {
		this.nextUpdDate = nextUpdDate;
	}

	/**
	 * �\�� ���擾����B<br/>
	 * <br/>
	 * @return �\��
	 */
	public String getStruct() {
		return struct;
	}

	/**
	 * �\�� ��ݒ肷��B<br/>
	 * <br/>
	 * @param struct
	 */
	public void setStruct(String struct) {
		this.struct = struct;
	}

	/**
	 * �������S ���擾����B<br/>
	 * <br/>
	 * @return �������S
	 */
	public String getPrivateRoad() {
		return privateRoad;
	}

	/**
	 * �������S ��ݒ肷��B<br/>
	 * <br/>
	 * @param privateRoad
	 */
	public void setPrivateRoad(String privateRoad) {
		this.privateRoad = privateRoad;
	}

	/**
	 * ��v�̌� ���擾����B<br/>
	 * <br/>
	 * @return ��v�̌�
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * ��v�̌� ��ݒ肷��B<br/>
	 * <br/>
	 * @param direction
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

	/**
	 * ����`�� ���擾����B<br/>
	 * <br/>
	 * @return ����`��
	 */
	public String getTransactTypeDiv() {
		return transactTypeDiv;
	}

	/**
	 * ����`�� ��ݒ肷��B<br/>
	 * <br/>
	 * @param transactTypeDiv
	 */
	public void setTransactTypeDiv(String transactTypeDiv) {
		this.transactTypeDiv = transactTypeDiv;
	}

	/**
	 * �C�U�ϗ��� ���擾����B<br/>
	 * <br/>
	 * @return �C�U�ϗ���
	 */
	public String getMenteFee() {
		return menteFee;
	}

	/**
	 * �C�U�ϗ��� ��ݒ肷��B<br/>
	 * <br/>
	 * @param menteFee
	 */
	public void setMenteFee(String menteFee) {
		this.menteFee = menteFee;
	}

	/**
	 * �ړ� ���擾����B<br/>
	 * <br/>
	 * @return �ړ�
	 */
	public String getContactRoad() {
		return contactRoad;
	}

	/**
	 * �ړ� ��ݒ肷��B<br/>
	 * <br/>
	 * @param contactRoad
	 */
	public void setContactRoad(String contactRoad) {
		this.contactRoad = contactRoad;
	}

	/**
	 * �ړ�����/���� ���擾����B<br/>
	 * <br/>
	 * @return �ړ�����/����
	 */
	public String getContactRoadDir() {
		return contactRoadDir;
	}

	/**
	 * �ړ�����/���� ��ݒ肷��B<br/>
	 * <br/>
	 * @param contactRoadDir
	 */
	public void setContactRoadDir(String contactRoadDir) {
		this.contactRoadDir = contactRoadDir;
	}

	/**
	 * ���ː� ���擾����B<br/>
	 * <br/>
	 * @return ���ː�
	 */
	public String getTotalHouseCnt() {
		return totalHouseCnt;
	}

	/**
	 * ���ː� ��ݒ肷��B<br/>
	 * <br/>
	 * @param totalHouseCnt
	 */
	public void setTotalHouseCnt(String totalHouseCnt) {
		this.totalHouseCnt = totalHouseCnt;
	}

	/**
	 * ���ԏ� ���擾����B<br/>
	 * <br/>
	 * @return ���ԏ�
	 */
	public String getParkingSituation() {
		return parkingSituation;
	}

	/**
	 * ���ԏ� ��ݒ肷��B<br/>
	 * <br/>
	 * @param parkingSituation
	 */
	public void setParkingSituation(String parkingSituation) {
		this.parkingSituation = parkingSituation;
	}

	/**
	 * �y�n���� ���擾����B<br/>
	 * <br/>
	 * @return �y�n����
	 */
	public String getLandRight() {
		return landRight;
	}

	/**
	 * �y�n���� ��ݒ肷��B<br/>
	 * <br/>
	 * @param landRight
	 */
	public void setLandRight(String landRight) {
		this.landRight = landRight;
	}

	/**
	 * ���L���� ���擾����B<br/>
	 * <br/>
	 * @return ���L����
	 */
	public String getSpecialInstruction() {
		return specialInstruction;
	}

	/**
	 * ���L���� ��ݒ肷��B<br/>
	 * <br/>
	 * @param specialInstruction
	 */
	public void setSpecialInstruction(String specialInstruction) {
		this.specialInstruction = specialInstruction;
	}

	/**
	 * �e�ϗ� ���擾����B<br/>
	 * <br/>
	 * @return �e�ϗ�
	 */
	public String getBuildingRate() {
		return buildingRate;
	}

	/**
	 * �e�ϗ� ��ݒ肷��B<br/>
	 * <br/>
	 * @param buildingRate
	 */
	public void setBuildingRate(String buildingRate) {
		this.buildingRate = buildingRate;
	}

	/**
	 * �p�r�n�� ���擾����B<br/>
	 * <br/>
	 * @return �p�r�n��
	 */
	public String getUsedAreaCd() {
		return usedAreaCd;
	}

	/**
	 * �p�r�n�� ��ݒ肷��B<br/>
	 * <br/>
	 * @param usedAreaCd
	 */
	public void setUsedAreaCd(String usedAreaCd) {
		this.usedAreaCd = usedAreaCd;
	}

	/**
	 * ���r�ی� ���擾����B<br/>
	 * <br/>
	 * @return ���r�ی�
	 */
	public String getInsurExist() {
		return insurExist;
	}

	/**
	 * ���r�ی� ��ݒ肷��B<br/>
	 * <br/>
	 * @param insurExist
	 */
	public void setInsurExist(String insurExist) {
		this.insurExist = insurExist;
	}

	/**
	 * ���������̕\���t���O ���擾����B<br/>
	 * <br/>
	 * @return ���������̕\���t���O
	 */
	public boolean isHousingPropertyDisplayFlg() {
		return housingPropertyDisplayFlg;
	}

	/**
	 * ���������̕\���t���O ��ݒ肷��B<br/>
	 * <br/>
	 * @param housingPropertyDisplayFlg
	 */
	public void setHousingPropertyDisplayFlg(boolean housingPropertyDisplayFlg) {
		this.housingPropertyDisplayFlg = housingPropertyDisplayFlg;
	}

	/**
	 * �������� ���擾����B<br/>
	 * <br/>
	 * @return ��������
	 */
	public String getEquipName() {
		return equipName;
	}

	/**
	 * �������� ��ݒ肷��B<br/>
	 * <br/>
	 * @param equipName
	 */
	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}

	/**
	 * �n����̕\���t���O ���擾����B<br/>
	 * <br/>
	 * @return �n����̕\���t���O
	 */
	public boolean isLandmarkDisplayFlg() {
		return landmarkDisplayFlg;
	}

	/**
	 * �n����̕\���t���O ��ݒ肷��B<br/>
	 * <br/>
	 * @param landmarkDisplayFlg
	 */
	public void setLandmarkDisplayFlg(boolean landmarkDisplayFlg) {
		this.landmarkDisplayFlg = landmarkDisplayFlg;
	}

	/**
	 * �n����ԍ��yhidden�z�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return �n����ԍ��yhidden�z�u�z��v
	 */
	public String[] getLandmarkNoHidden() {
		return landmarkNoHidden;
	}

	/**
	 * �n����ԍ��yhidden�z�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param landmarkNoHidden
	 */
	public void setLandmarkNoHidden(String[] landmarkNoHidden) {
		this.landmarkNoHidden = landmarkNoHidden;
	}

	/**
	 * �����h�}�[�N�̎�ށu�z��v ���擾����B<br/>
	 * <br/>
	 * @return �����h�}�[�N�̎�ށu�z��v
	 */
	public String[] getLandmarkType() {
		return landmarkType;
	}

	/**
	 * �����h�}�[�N�̎�ށu�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param landmarkType
	 */
	public void setLandmarkType(String[] landmarkType) {
		this.landmarkType = landmarkType;
	}

	/**
	 * �n����i���́j�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return �n����i���́j�u�z��v
	 */
	public String[] getLandmarkName() {
		return landmarkName;
	}

	/**
	 * �n����i���́j�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param landmarkName
	 */
	public void setLandmarkName(String[] landmarkName) {
		this.landmarkName = landmarkName;
	}

	/**
	 * �n����i���v����/�����j�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return �n����i���v����/�����j�u�z��v
	 */
	public String[] getDistanceFromLandmark() {
		return distanceFromLandmark;
	}

	/**
	 * �n����i���v����/�����j�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param distanceFromLandmark
	 */
	public void setDistanceFromLandmark(String[] distanceFromLandmark) {
		this.distanceFromLandmark = distanceFromLandmark;
	}

	/**
	 * �������߃��t�H�[���v������̕\���t���O ���擾����B<br/>
	 * <br/>
	 * @return �������߃��t�H�[���v������̕\���t���O
	 */
	public boolean isRecommendReformPlanDisplayFlg() {
		return recommendReformPlanDisplayFlg;
	}

	/**
	 * �������߃��t�H�[���v������̕\���t���O ��ݒ肷��B<br/>
	 * <br/>
	 * @param recommendReformPlanDisplayFlg
	 */
	public void setRecommendReformPlanDisplayFlg(boolean recommendReformPlanDisplayFlg) {
		this.recommendReformPlanDisplayFlg = recommendReformPlanDisplayFlg;
	}

	/**
	 * ���t�H�[���v������ ���擾����B<br/>
	 * <br/>
	 * @return ���t�H�[���v������
	 */
	public String getPlanName() {
		return planName;
	}

	/**
	 * ���t�H�[���v������ ��ݒ肷��B<br/>
	 * <br/>
	 * @param planName
	 */
	public void setPlanName(String planName) {
		this.planName = planName;
	}

	/**
	 * �Z�[���X�|�C���g ���擾����B<br/>
	 * <br/>
	 * @return �Z�[���X�|�C���g
	 */
	public String getSalesPoint() {
		return salesPoint;
	}

	/**
	 * �Z�[���X�|�C���g ��ݒ肷��B<br/>
	 * <br/>
	 * @param salesPoint
	 */
	public void setSalesPoint(String salesPoint) {
		this.salesPoint = salesPoint;
	}

	/**
	 * ���z�P ���擾����B<br/>
	 * <br/>
	 * @return ���z�P
	 */
	public String getTotalDtlPrice1() {
		return totalDtlPrice1;
	}

	/**
	 * ���z�P ��ݒ肷��B<br/>
	 * <br/>
	 * @param totalDtlPrice1
	 */
	public void setTotalDtlPrice1(String totalDtlPrice1) {
		this.totalDtlPrice1 = totalDtlPrice1;
	}

	/**
	 * ���z�Q ���擾����B<br/>
	 * <br/>
	 * @return ���z�Q
	 */
	public String getTotalDtlPrice2() {
		return totalDtlPrice2;
	}

	/**
	 * ���z�Q ��ݒ肷��B<br/>
	 * <br/>
	 * @param totalDtlPrice2
	 */
	public void setTotalDtlPrice2(String totalDtlPrice2) {
		this.totalDtlPrice2 = totalDtlPrice2;
	}

	/**
	 * ���t�H�[���ڍ�_�ԍ��yhidden�z�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return ���t�H�[���ڍ�_�ԍ��yhidden�z�u�z��v
	 */
	public String[] getReformNoHidden() {
		return reformNoHidden;
	}

	/**
	 * ���t�H�[���ڍ�_�ԍ��yhidden�z�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param reformNoHidden
	 */
	public void setReformNoHidden(String[] reformNoHidden) {
		this.reformNoHidden = reformNoHidden;
	}

	/**
	 * ���t�H�[���ڍ�_���ږ��́u�z��v ���擾����B<br/>
	 * <br/>
	 * @return ���t�H�[���ڍ�_���ږ��́u�z��v
	 */
	public String[] getReformImgName() {
		return reformImgName;
	}

	/**
	 * ���t�H�[���ڍ�_���ږ��́u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param reformImgName
	 */
	public void setReformImgName(String[] reformImgName) {
		this.reformImgName = reformImgName;
	}

	/**
	 * ���t�H�[���ڍ�_�摜�p�X���yhidden�z�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return ���t�H�[���ڍ�_�摜�p�X���yhidden�z�u�z��v
	 */
	public String[] getReformPathName() {
		return reformPathName;
	}

	/**
	 * ���t�H�[���ڍ�_�摜�p�X���yhidden�z�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param reformPathName
	 */
	public void setReformPathName(String[] reformPathName) {
		this.reformPathName = reformPathName;
	}

	/**
	 * ���t�H�[���ڍ�_���ڃ��t�H�[�����i�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return ���t�H�[���ڍ�_���ڃ��t�H�[�����i�u�z��v
	 */
	public String[] getReformPrice() {
		return reformPrice;
	}

	/**
	 * ���t�H�[���ڍ�_���ڃ��t�H�[�����i�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param reformPrice
	 */
	public void setReformPrice(String[] reformPrice) {
		this.reformPrice = reformPrice;
	}

	/**
	 * �H�� ���擾����B<br/>
	 * <br/>
	 * @return �H��
	 */
	public String getConstructionPeriod() {
		return constructionPeriod;
	}

	/**
	 * �H�� ��ݒ肷��B<br/>
	 * <br/>
	 * @param constructionPeriod
	 */
	public void setConstructionPeriod(String constructionPeriod) {
		this.constructionPeriod = constructionPeriod;
	}

	/**
	 * ���t�H�[�� ���l ���擾����B<br/>
	 * <br/>
	 * @return ���t�H�[�� ���l
	 */
	public String getReformNote() {
		return reformNote;
	}

	/**
	 * ���t�H�[�� ���l ��ݒ肷��B<br/>
	 * <br/>
	 * @param reformNote
	 */
	public void setReformNote(String reformNote) {
		this.reformNote = reformNote;
	}

	/**
	 * ���t�H�[���C���[�W�̕\���t���O ���擾����B<br/>
	 * <br/>
	 * @return ���t�H�[���C���[�W�̕\���t���O
	 */
	public boolean isReformImgDisplayFlg() {
		return reformImgDisplayFlg;
	}

	/**
	 * ���t�H�[���C���[�W�̕\���t���O ��ݒ肷��B<br/>
	 * <br/>
	 * @param reformImgDisplayFlg
	 */
	public void setReformImgDisplayFlg(boolean reformImgDisplayFlg) {
		this.reformImgDisplayFlg = reformImgDisplayFlg;
	}

	/**
	 * ���t�H�[����_�O�ρE���ӎʐ^�ԍ��yhidden�z�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return ���t�H�[����_�O�ρE���ӎʐ^�ԍ��yhidden�z�u�z��v
	 */
	public String[] getAfterPathNoHidden() {
		return afterPathNoHidden;
	}

	/**
	 * ���t�H�[����_�O�ρE���ӎʐ^�ԍ��yhidden�z�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param afterPathNoHidden
	 */
	public void setAfterPathNoHidden(String[] afterPathNoHidden) {
		this.afterPathNoHidden = afterPathNoHidden;
	}

	/**
	 * ���t�H�[����_�O�ρE���ӎʐ^�p�X�P�yhidden�z�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return ���t�H�[����_�O�ρE���ӎʐ^�p�X�P�yhidden�z�u�z��v
	 */
	public String[] getAfterPath1() {
		return afterPath1;
	}

	/**
	 * ���t�H�[����_�O�ρE���ӎʐ^�p�X�P�yhidden�z�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param afterPath1
	 */
	public void setAfterPath1(String[] afterPath1) {
		this.afterPath1 = afterPath1;
	}

	/**
	 * ���t�H�[����_�O�ρE���ӎʐ^�p�X�Q�yhidden�z�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return ���t�H�[����_�O�ρE���ӎʐ^�p�X�Q�yhidden�z�u�z��v
	 */
	public String[] getAfterPath2() {
		return afterPath2;
	}

	/**
	 * ���t�H�[����_�O�ρE���ӎʐ^�p�X�Q�yhidden�z�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param afterPath2
	 */
	public void setAfterPath2(String[] afterPath2) {
		this.afterPath2 = afterPath2;
	}

	/**
	 * ���t�H�[����_�O�ρE���ӎʐ^ �R�����g�yhidden�z�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return ���t�H�[����_�O�ρE���ӎʐ^ �R�����g�yhidden�z�u�z��v
	 */
	public String[] getAfterPathComment() {
		return afterPathComment;
	}

	/**
	 * ���t�H�[����_�O�ρE���ӎʐ^ �R�����g�yhidden�z�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param afterPathComment
	 */
	public void setAfterPathComment(String[] afterPathComment) {
		this.afterPathComment = afterPathComment;
	}

	/**
	 * ���t�H�[����_����p�T���l�C���yhidden�z ���擾����B<br/>
	 * <br/>
	 * @return ���t�H�[����_����p�T���l�C���yhidden�z
	 */
	public String getAfterMovieUrl() {
		return afterMovieUrl;
	}

	/**
	 * ���t�H�[����_����p�T���l�C���yhidden�z ��ݒ肷��B<br/>
	 * <br/>
	 * @param afterMovieUrl
	 */
	public void setAfterMovieUrl(String afterMovieUrl) {
		this.afterMovieUrl = afterMovieUrl;
	}

	/**
	 * ���t�H�[���O_�O�ρE���ӎʐ^�ԍ��yhidden�z�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return ���t�H�[���O_�O�ρE���ӎʐ^�ԍ��yhidden�z�u�z��v
	 */
	public String[] getBeforePathNoHidden() {
		return beforePathNoHidden;
	}

	/**
	 * ���t�H�[���O_�O�ρE���ӎʐ^�ԍ��yhidden�z�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param beforePathNoHidden
	 */
	public void setBeforePathNoHidden(String[] beforePathNoHidden) {
		this.beforePathNoHidden = beforePathNoHidden;
	}

	/**
	 * ���t�H�[���O_�O�ρE���ӎʐ^�p�X�P�yhidden�z�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return ���t�H�[���O_�O�ρE���ӎʐ^�p�X�P�yhidden�z�u�z��v
	 */
	public String[] getBeforePath1() {
		return beforePath1;
	}

	/**
	 * ���t�H�[���O_�O�ρE���ӎʐ^�p�X�P�yhidden�z�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param beforePath1
	 */
	public void setBeforePath1(String[] beforePath1) {
		this.beforePath1 = beforePath1;
	}

	/**
	 * ���t�H�[���O_�O�ρE���ӎʐ^�p�X�Q�yhidden�z�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return ���t�H�[���O_�O�ρE���ӎʐ^�p�X�Q�yhidden�z�u�z��v
	 */
	public String[] getBeforePath2() {
		return beforePath2;
	}

	/**
	 * ���t�H�[���O_�O�ρE���ӎʐ^�p�X�Q�yhidden�z�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param beforePath2
	 */
	public void setBeforePath2(String[] beforePath2) {
		this.beforePath2 = beforePath2;
	}

	/**
	 * ���t�H�[���O_�O�ρE���ӎʐ^ �R�����g�yhidden�z�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return ���t�H�[���O_�O�ρE���ӎʐ^ �R�����g�yhidden�z�u�z��v
	 */
	public String[] getBeforePathComment() {
		return beforePathComment;
	}

	/**
	 * ���t�H�[���O_�O�ρE���ӎʐ^ �R�����g�yhidden�z�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param beforePathComment
	 */
	public void setBeforePathComment(String[] beforePathComment) {
		this.beforePathComment = beforePathComment;
	}

	/**
	 * ���t�H�[���O_����p�T���l�C���yhidden�z ���擾����B<br/>
	 * <br/>
	 * @return ���t�H�[���O_����p�T���l�C���yhidden�z
	 */
	public String getBeforeMovieUrl() {
		return beforeMovieUrl;
	}

	/**
	 * ���t�H�[���O_����p�T���l�C���yhidden�z ��ݒ肷��B<br/>
	 * <br/>
	 * @param beforeMovieUrl
	 */
	public void setBeforeMovieUrl(String beforeMovieUrl) {
		this.beforeMovieUrl = beforeMovieUrl;
	}

	/**
	 * �Z��f�f���̕\���t���O ���擾����B<br/>
	 * <br/>
	 * @return �Z��f�f���̕\���t���O
	 */
	public boolean isHousingInspectionDisplayFlg() {
		return housingInspectionDisplayFlg;
	}

	/**
	 * �Z��f�f���̕\���t���O ��ݒ肷��B<br/>
	 * <br/>
	 * @param housingInspectionDisplayFlg
	 */
	public void setHousingInspectionDisplayFlg(boolean housingInspectionDisplayFlg) {
		this.housingInspectionDisplayFlg = housingInspectionDisplayFlg;
	}

	/**
	 * �Z��f�f���{�� ���擾����B<br/>
	 * <br/>
	 * @return �Z��f�f���{��
	 */
	public String getInspectionExist() {
		return inspectionExist;
	}

	/**
	 * �Z��f�f���{�� ��ݒ肷��B<br/>
	 * <br/>
	 * @param inspectionExist
	 */
	public void setInspectionExist(String inspectionExist) {
		this.inspectionExist = inspectionExist;
	}

	/**
	 * �m�F���x���ԍ��yhidden�z�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return �m�F���x���ԍ��yhidden�z�u�z��v
	 */
	public String[] getInspectionNoHidden() {
		return inspectionNoHidden;
	}

	/**
	 * �m�F���x���ԍ��yhidden�z�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param inspectionNoHidden
	 */
	public void setInspectionNoHidden(String[] inspectionNoHidden) {
		this.inspectionNoHidden = inspectionNoHidden;
	}

	/**
	 * �m�F���x���i���́j�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return �m�F���x���i���́j�u�z��v
	 */
	public String[] getInspectionKey() {
		return inspectionKey;
	}

	/**
	 * �m�F���x���i���́j�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param inspectionKey
	 */
	public void setInspectionKey(String[] inspectionKey) {
		this.inspectionKey = inspectionKey;
	}

	/**
	 * �m�F���x���u�z��v ���擾����B<br/>
	 * <br/>
	 * @return �m�F���x���u�z��v
	 */
	public String[] getInspectionTrust() {
		return inspectionTrust;
	}

	/**
	 * �m�F���x���u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param inspectionTrust
	 */
	public void setInspectionTrust(String[] inspectionTrust) {
		this.inspectionTrust = inspectionTrust;
	}

	/**
	 * �Z��f�f���}�yhidden�z ���擾����B<br/>
	 * <br/>
	 * @return �Z��f�f���}�yhidden�z
	 */
	public String getInspectionImagePathName() {
		return inspectionImagePathName;
	}

	/**
	 * �Z��f�f���}�yhidden�z ��ݒ肷��B<br/>
	 * <br/>
	 * @param inspectionImagePathName
	 */
	public void setInspectionImagePathName(String inspectionImagePathName) {
		this.inspectionImagePathName = inspectionImagePathName;
	}

	/**
	 * �Z��f�f�t�@�C���yhidden�z ���擾����B<br/>
	 * <br/>
	 * @return �Z��f�f�t�@�C���yhidden�z
	 */
	public String getInspectionPathName() {
		return inspectionPathName;
	}

	/**
	 * �Z��f�f�t�@�C���yhidden�z ��ݒ肷��B<br/>
	 * <br/>
	 * @param inspectionPathName
	 */
	public void setInspectionPathName(String inspectionPathName) {
		this.inspectionPathName = inspectionPathName;
	}

	/**
	 * ���̑��̃��t�H�[���v�����̕\���t���O ���擾����B<br/>
	 * <br/>
	 * @return ���̑��̃��t�H�[���v�����̕\���t���O
	 */
	public boolean isOtherReformPlanDisplayFlg() {
		return otherReformPlanDisplayFlg;
	}

	/**
	 * ���̑��̃��t�H�[���v�����̕\���t���O ��ݒ肷��B<br/>
	 * <br/>
	 * @param otherReformPlanDisplayFlg
	 */
	public void setOtherReformPlanDisplayFlg(boolean otherReformPlanDisplayFlg) {
		this.otherReformPlanDisplayFlg = otherReformPlanDisplayFlg;
	}

	/**
	 * ���̑��̃v�����ԍ��yhidden�z�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return ���̑��̃v�����ԍ��yhidden�z�u�z��v
	 */
	public String[] getOtherPlanNoHidden() {
		return otherPlanNoHidden;
	}

	/**
	 * ���̑��̃v�����ԍ��yhidden�z�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param otherPlanNoHidden
	 */
	public void setOtherPlanNoHidden(String[] otherPlanNoHidden) {
		this.otherPlanNoHidden = otherPlanNoHidden;
	}

	/**
	 * ���̑��̃v�����^�C�v�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return ���̑��̃v�����^�C�v�u�z��v
	 */
	public String[] getOtherPlanType() {
		return otherPlanType;
	}

	/**
	 * ���̑��̃v�����^�C�v�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param otherPlanType
	 */
	public void setOtherPlanType(String[] otherPlanType) {
		this.otherPlanType = otherPlanType;
	}

	/**
	 * ���̑��̑��z�P�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return ���̑��̑��z�P�u�z��v
	 */
	public String[] getOtherTotalPrice1() {
		return otherTotalPrice1;
	}

	/**
	 * ���̑��̑��z�P�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param otherTotalPrice1
	 */
	public void setOtherTotalPrice1(String[] otherTotalPrice1) {
		this.otherTotalPrice1 = otherTotalPrice1;
	}

	/**
	 * ���̑��̑��z�Q�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return ���̑��̑��z�Q�u�z��v
	 */
	public String[] getOtherTotalPrice2() {
		return otherTotalPrice2;
	}

	/**
	 * ���̑��̑��z�Q�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param otherTotalPrice2
	 */
	public void setOtherTotalPrice2(String[] otherTotalPrice2) {
		this.otherTotalPrice2 = otherTotalPrice2;
	}

	/**
	 * ���̑��̃V�X�e�����t�H�[��CD�yhidden�z�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return ���̑��̃V�X�e�����t�H�[��CD�yhidden�z�u�z��v
	 */
	public String[] getOtherReformCdHidden() {
		return otherReformCdHidden;
	}

	/**
	 * ���̑��̃V�X�e�����t�H�[��CD�yhidden�z�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param otherReformCdHidden
	 */
	public void setOtherReformCdHidden(String[] otherReformCdHidden) {
		this.otherReformCdHidden = otherReformCdHidden;
	}

	/**
	 * ���̑��̃V�X�e�����t�H�[��URL�yhidden�z�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return ���̑��̃V�X�e�����t�H�[��URL�yhidden�z�u�z��v
	 */
	public String[] getOtherReformUrl() {
		return otherReformUrl;
	}

	/**
	 * ���̑��̃V�X�e�����t�H�[��URL�yhidden�z�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param otherReformUrl
	 */
	public void setOtherReformUrl(String[] otherReformUrl) {
		this.otherReformUrl = otherReformUrl;
	}

	/**
	 * �ŋߌ��������̕\���t���O ���擾����B<br/>
	 * <br/>
	 * @return �ŋߌ��������̕\���t���O
	 */
	public boolean isRecentlyDisplayFlg() {
		return recentlyDisplayFlg;
	}

	/**
	 * �ŋߌ��������̕\���t���O ��ݒ肷��B<br/>
	 * <br/>
	 * @param recentlyDisplayFlg
	 */
	public void setRecentlyDisplayFlg(boolean recentlyDisplayFlg) {
		this.recentlyDisplayFlg = recentlyDisplayFlg;
	}

	/**
	 * �ŋ� �����ԍ��yhidden�z�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return �ŋ� �����ԍ��yhidden�z�u�z��v
	 */
	public String[] getRecentlyNoHidden() {
		return recentlyNoHidden;
	}

	/**
	 * �ŋ� �����ԍ��yhidden�z�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param recentlyNoHidden
	 */
	public void setRecentlyNoHidden(String[] recentlyNoHidden) {
		this.recentlyNoHidden = recentlyNoHidden;
	}

	/**
	 * �ŋ� �V�X�e������CD�yhidden�z�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return �ŋ� �V�X�e������CD�yhidden�z�u�z��v
	 */
	public String[] getRecentlySysHousingCdHidden() {
		return recentlySysHousingCdHidden;
	}

	/**
	 * �ŋ� �V�X�e������CD�yhidden�z�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param recentlySysHousingCdHidden
	 */
	public void setRecentlySysHousingCdHidden(String[] recentlySysHousingCdHidden) {
		this.recentlySysHousingCdHidden = recentlySysHousingCdHidden;
	}

	/**
	 * �ŋ� �����ԍ��ydata-number�z�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return �ŋ� �����ԍ��ydata-number�z�u�z��v
	 */
	public String[] getRecentlyHousingCdHidden() {
		return recentlyHousingCdHidden;
	}

	/**
	 * �ŋ� �����ԍ��ydata-number�z�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param recentlyHousingCdHidden
	 */
	public void setRecentlyHousingCdHidden(String[] recentlyHousingCdHidden) {
		this.recentlyHousingCdHidden = recentlyHousingCdHidden;
	}

	/**
	 * �ŋ� �����摜�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return �ŋ� �����摜�u�z��v
	 */
	public String[] getRecentlyPathName() {
		return recentlyPathName;
	}

	/**
	 * �ŋ� �����摜�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param recentlyPathName
	 */
	public void setRecentlyPathName(String[] recentlyPathName) {
		this.recentlyPathName = recentlyPathName;
	}

	/**
	 * �ŋ� �������CD�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return �ŋ� �������CD�u�z��v
	 */
	public String[] getRecentlyHousingKindCd() {
		return recentlyHousingKindCd;
	}

	/**
	 * �ŋ� �������CD�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param recentlyHousingKindCd
	 */
	public void setRecentlyHousingKindCd(String[] recentlyHousingKindCd) {
		this.recentlyHousingKindCd = recentlyHousingKindCd;
	}

	/**
	 * �ŋ� �������u�z��v ���擾����B<br/>
	 * <br/>
	 * @return �ŋ� �������u�z��v
	 */
	public String[] getRecentlyDisplayHousingName() {
		return recentlyDisplayHousingName;
	}

	/**
	 * �ŋ� �������u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param recentlyDisplayHousingName
	 */
	public void setRecentlyDisplayHousingName(String[] recentlyDisplayHousingName) {
		this.recentlyDisplayHousingName = recentlyDisplayHousingName;
	}

	/**
	 * �ŋ� ������FULL�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return �ŋ� ������FULL�u�z��v
	 */
	public String[] getRecentlyDisplayHousingNameFull() {
		return recentlyDisplayHousingNameFull;
	}

	/**
	 * �ŋ� ������FULL�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param recentlyDisplayHousingNameFull
	 */
	public void setRecentlyDisplayHousingNameFull(String[] recentlyDisplayHousingNameFull) {
		this.recentlyDisplayHousingNameFull = recentlyDisplayHousingNameFull;
	}

	/**
	 * �ŋ� �����ڍׁu�z��v ���擾����B<br/>
	 * <br/>
	 * @return �ŋ� �����ڍׁu�z��v
	 */
	public String[] getRecentlyDtl() {
		return recentlyDtl;
	}

	/**
	 * �ŋ� �����ڍׁu�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param recentlyDtl
	 */
	public void setRecentlyDtl(String[] recentlyDtl) {
		this.recentlyDtl = recentlyDtl;
	}

	/**
	 * �ŋ� �����ڍ�FULL�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return �ŋ� �����ڍ�FULL�u�z��v
	 */
	public String[] getRecentlyDtlFull() {
		return recentlyDtlFull;
	}

	/**
	 * �ŋ� �����ڍ�FULL�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param recentlyDtlFull
	 */
	public void setRecentlyDtlFull(String[] recentlyDtlFull) {
		this.recentlyDtlFull = recentlyDtlFull;
	}

	/**
	 * �ŋ� ����URL�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return �ŋ� ����URL�u�z��v
	 */
	public String[] getRecentlyUrl() {
		return recentlyUrl;
	}

	/**
	 * �ŋ� ����URL�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param recentlyUrl
	 */
	public void setRecentlyUrl(String[] recentlyUrl) {
		this.recentlyUrl = recentlyUrl;
	}

	/**
	 * �L�[���[�h ���擾����B<br/>
	 * <br/>
	 * @return �L�[���[�h
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * �L�[���[�h ��ݒ肷��B<br/>
	 * <br/>
	 * @param keywords
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * ���� ���擾����B<br/>
	 * <br/>
	 * @return ����
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * ���� ��ݒ肷��B<br/>
	 * <br/>
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * �{�y�[�WURL ���擾����B<br/>
	 * <br/>
	 * @return �{�y�[�WURL
	 */
	public String getCurrentUrl() {
		return currentUrl;
	}

	/**
	 * �{�y�[�WURL ��ݒ肷��B<br/>
	 * <br/>
	 * @param currentUrl
	 */
	public void setCurrentUrl(String currentUrl) {
		this.currentUrl = currentUrl;
	}

	/**
	 * �Č���URL�u�z��v ���擾����B<br/>
	 * <br/>
	 * @return �Č���URL�u�z��v
	 */
	public String[] getResearchUrl() {
		return researchUrl;
	}

	/**
	 * �Č���URL�u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param researchUrl
	 */
	public void setResearchUrl(String[] researchUrl) {
		this.researchUrl = researchUrl;
	}

	/**
	 * �Č����s���{�����u�z��v ���擾����B<br/>
	 * <br/>
	 * @return �Č����s���{�����u�z��v
	 */
	public String[] getResearchPrefName() {
		return researchPrefName;
	}

	/**
	 * �Č����s���{�����u�z��v ��ݒ肷��B<br/>
	 * <br/>
	 * @param researchPrefName
	 */
	public void setResearchPrefName(String[] researchPrefName) {
		this.researchPrefName = researchPrefName;
	}

	/**
	 * �R���X�g���N�^�[<br/>
	 * <br/>
	 */
	public PanaHousingDetailed(CodeLookupManager codeLookupManager, PanaCommonParameters commonParameters, PanaFileUtil panaFileUtil) {
		this.codeLookupManager = codeLookupManager;
		this.commonParameters = commonParameters;
		this.panaFileUtil = panaFileUtil;
	}

	/**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� Form �֏����l��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param detailedMap �����ڍ׃I�u�W�F�N�g
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@SuppressWarnings("unchecked")
	public void setDefaultData(Map<String, Object> detailedMap) throws Exception {

		// �������[�h���A�t���O��ݒ肷��B
		if (DETAIL_MODE.equals(getMode())) {
			// �������߃��t�H�[���v������̕\���t���O
			setRecommendReformPlanDisplayFlg(false);

			// ���t�H�[���C���[�W�̕\���t���O
			setReformImgDisplayFlg(false);

			// ���̕����͂������߃��t�H�[���v����������܂��t���O
			setReformPlanExists(false);

		} else if (REFORM_MODE.equals(getMode())) {

			// �������߃��t�H�[���v������̕\���t���O
			setRecommendReformPlanDisplayFlg(true);

			// ���t�H�[���C���[�W�̕\���t���O
			setReformImgDisplayFlg(true);

			// ���̕����͂������߃��t�H�[���v����������܂��t���O
			setReformPlanExists(true);
		}

		// �����擾����B
		// ���������擾����B
		PanaHousing housing = (PanaHousing) detailedMap.get("housing");

		// ������{�����擾����B
		HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");

		// �����ڍ׏����擾����B
		HousingDtlInfo housingDtlInfo = (HousingDtlInfo) housing.getHousingInfo().getItems().get("housingDtlInfo");

		// ������{�����擾����B
		BuildingInfo buildingInfo = (BuildingInfo) housing.getBuilding().getBuildingInfo().getItems().get("buildingInfo");

		// �����ڍ׏����擾����B
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) housing.getBuilding().getBuildingInfo().getItems().get("buildingDtlInfo");

		// �s���{���}�X�^���擾����B
		PrefMst prefMst = (PrefMst) housing.getBuilding().getBuildingInfo().getItems().get("prefMst");

		// �����Ŋ��w�����擾����B
		List<JoinResult> buildingStationInfoList = housing.getBuilding().getBuildingStationInfoList();

		// ���t�H�[���v���������擾����B
		List<ReformPlan> reformPlanList = (List<ReformPlan>) detailedMap.get("reformPlanList");

		// �摜�����擾����B
		List<HousingImageInfo> housingImageInfoList = housing.getHousingImageInfos();

		// �����g�����������擾����B
		Map<String, Map<String, String>> housingExtInfosMap = housing.getHousingExtInfos();

		// �����ݔ������擾����B
		Map<String, EquipMst> housingEquipInfos = housing.getHousingEquipInfos();

		// ���������h�}�[�N�����擾����B
		List<BuildingLandmark> buildingLandmarkList = housing.getBuilding().getBuildingLandmarkList();

		// ���t�H�[���v�������擾����B
		ReformPlan reformPlan = (ReformPlan) detailedMap.get("reformPlan");

		// ���t�H�[���ڍ׏����擾����B
		List<ReformDtl> reformDtlList = (List<ReformDtl>) detailedMap.get("reformDtlList");

		// ���t�H�[���摜�����擾����B
		List<ReformImg> reformImgList = (List<ReformImg>) detailedMap.get("reformImgList");

		// �����C���X�y�N�V�������擾����B
		List<HousingInspection> housingInspectionList = (List<HousingInspection>) detailedMap.get("housingInspectionList");

		// �s���{�����X�g���擾����B
		List<PrefMst> prefMstList = (List<PrefMst>) detailedMap.get("prefMstList");

		// �ŋߌ����������擾����B
		List<PanaHousing> recentlyInfoList = (List<PanaHousing>) detailedMap.get("recentlyInfoList");

		// ����ݒ肷��B
		// �^�C�g������ݒ肷��B
		setTitle(housingInfo, housingDtlInfo, buildingInfo);

		// ��������ݒ肷��B
		setHousingInfo(housingInfo, buildingStationInfoList, buildingInfo, buildingDtlInfo, prefMst);

		// ���t�H�[���v��������ݒ肷��B
		setReformPlan(housingInfo, buildingInfo, reformPlanList);

		// �摜����ݒ肷��B
		setHousingImageInfo(housingImageInfoList, housingExtInfosMap);

		// ����̃R�����g��ݒ肷��B
		setSaleComment(housingExtInfosMap);

		// �S���҂���̂������߂�ݒ肷��B
		setRecommend(housingInfo, housingExtInfosMap);

		// �����ڍ׏���ݒ肷��B
		setHousingDtlInfo(housingInfo, housingDtlInfo, housingExtInfosMap, buildingDtlInfo);

		// ����������ݒ肷��B
		setHousingEquipInfo(housingEquipInfos);

		// �n�����ݒ肷��B
		setBuildingLandmark(buildingLandmarkList);

		// �������߃��t�H�[���v�������ݒ肷��B
		setRecommendReformPlan(housingInfo, reformPlan, reformDtlList);

		// ���t�H�[���C���[�W��ݒ肷��B
		setReformImg(reformPlan, reformImgList);

		// �Z��f�f����ݒ肷��B
		setInspection(housingInspectionList, housingExtInfosMap);

		// ���̑��̃��t�H�[���v������ݒ肷��B
		setOtherReformPlan();

		// �ŋߌ���������ݒ肷��B
		setRecentlyInfo(recentlyInfoList);

		// �Č����̃����N��ݒ肷��B
		setResearchLink(prefMst, prefMstList);

		// ����o�^�̕\���t���O��ݒ肷��B
		setMemberLoginFlg();

		// �L�[���[�h�Ɛ�����ݒ肷��B
		setMeta();
	}

	/**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� �^�C�g������ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param housingInfo ������{���
	 * @param housingDtlInfo �����ڍ׏��
	 * @param buildingInfo ������{���
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	private void setTitle(HousingInfo housingInfo, HousingDtlInfo housingDtlInfo, BuildingInfo buildingInfo) throws Exception {

		if (housingInfo != null) {

			// �V���t���O
			if (housingInfo.getUpdDate() != null) {
				// �V�X�e�����t
				Date nowDate = new Date();
				// �ŏI�X�V��
				Date updDate = housingInfo.getUpdDate();
				// 1�T�Ԉȓ��i�V�X�e�����t - �ŏI�X�V��<= 7���j
				if ((nowDate.getTime() - updDate.getTime()) <= 7 * (24 * 60 * 60 * 1000)) {
					// �V���t���O
					setFreshFlg(true);

				} else {

					// �V���t���O
					setFreshFlg(false);
				}
			}
			if (buildingInfo != null) {
				// �������CD
				setHousingKindCd(buildingInfo.getHousingKindCd());
			}

			// �����ԍ�
			setHousingCd(housingInfo.getHousingCd());

			// ������
			setDisplayHousingName(housingInfo.getDisplayHousingName());
		}

		if (housingDtlInfo != null) {
			// �������߃|�C���g�̑i���G���A1
			setDtlComment(PanaStringUtils.encodeHtml(housingDtlInfo.getDtlComment()));
		}
	}

	/**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� ��������ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param housingInfo ������{���
	 * @param buildingStationInfoList �����Ŋ��w���
	 * @param buildingInfo ������{���
	 * @param buildingDtlInfo �����ڍ׏��
	 * @param prefMst �s���{���}�X�^
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	private void setHousingInfo(HousingInfo housingInfo, List<JoinResult> buildingStationInfoList, BuildingInfo buildingInfo, BuildingDtlInfo buildingDtlInfo, PrefMst prefMst)
			throws Exception {

		if (housingInfo != null) {

			// �A�C�R�����yhidden�z�u�z��v
			if (!isEmpty(housingInfo.getIconCd())) {
				setIconCd(defaultString(housingInfo.getIconCd()).split(","));
			}

			// �������i
			setPrice((housingInfo.getPrice() == null) ? "" : formatPrice(housingInfo.getPrice(), true) + "���~");

			// �������i�yhidden�z
			setPriceHidden((housingInfo.getPrice() == null) ? "" : housingInfo.getPrice().toString());

			// ���ݒn
			StringBuilder sbAddress = new StringBuilder();
			if (prefMst != null) {
				// �s���{����
				sbAddress.append(defaultString(prefMst.getPrefName()) + " ");

				// �s���{����
				setPrefName(prefMst.getPrefName());
			}
			if (buildingInfo != null) {
				// ���ݒn�E�s�撬����
				sbAddress.append(defaultString(buildingInfo.getAddressName()) + " ");
				// ���ݒn�E�����Ԓn
				sbAddress.append(defaultString(buildingInfo.getAddressOther1()) + " ");
				// ���ݒn�E���������̑�
				sbAddress.append(defaultString(buildingInfo.getAddressOther2()));
			}

			// ���ݒn
			setAddress(sbAddress.toString());

			if (buildingStationInfoList != null && buildingStationInfoList.size() > 0) {
				int cnt = buildingStationInfoList.size();
				String[] access = new String[cnt];

				for (int i = 0; i < cnt; i++) {
					StringBuilder sbAccess = new StringBuilder();
					// �����Ŋ��w���
					BuildingStationInfo buildingStationInfo = (BuildingStationInfo) buildingStationInfoList.get(i).getItems().get("buildingStationInfo");
					// �S����Ѓ}�X�^
					RrMst rrMst = (RrMst) buildingStationInfoList.get(i).getItems().get("rrMst");
					// �H���}�X�^
					RouteMst routeMst = (RouteMst) buildingStationInfoList.get(i).getItems().get("routeMst");
					// �w���}�X�^
					StationMst stationMst = (StationMst) buildingStationInfoList.get(i).getItems().get("stationMst");
					// ��\�H����
					sbAccess.append(addString(defaultString(defaultString(rrMst.getRrName()) + defaultString(routeMst.getRouteName()), buildingStationInfo.getDefaultRouteName()),
							" "));
					// �w��
					sbAccess.append(addString(defaultString(stationMst.getStationName(), defaultString(buildingStationInfo.getStationName())), "�w "));
					// �o�X��Ж�
					sbAccess.append(addString(defaultString(buildingStationInfo.getBusCompany()), " "));
					// �o�X�₩��̓k������
					sbAccess.append(defaultString(buildingStationInfo.getTimeFromBusStop(), "�k��" + buildingStationInfo.getTimeFromBusStop() + "��"));
					access[i] = sbAccess.toString();
				}

				// �A�N�Z�X�u�z��v
				setAccess(access);
			}

			// �Ԏ�CD
			setLayoutCd(housingInfo.getLayoutCd());

			if (buildingInfo != null && buildingInfo.getCompDate() != null) {
				// �z�N��
				setCompDate(new SimpleDateFormat("yyyy�NM���z").format(buildingInfo.getCompDate()));
			}

			// �������CD�𔻒f
			if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(getHousingKindCd())) {
				// �}���V�����ʐσt���O
				setPersonalAreaFlg(true);

				// �ˌ� �y�n�ʐσt���O
				setBuildingAreaFlg(false);

				// �K���^���݊K�t���O
				setFloorFlg(true);

				// ��L�ʐ�
				setPersonalArea(defaultString(housingInfo.getPersonalArea()));

				// ��L�ʐ� ��
				setPersonalAreaSquare((housingInfo.getPersonalArea() == null) ? "" : "(��" + PanaCalcUtil.calcTsubo(housingInfo.getPersonalArea()).toString() + "��)");

				// ��L�ʐ�_�⑫
				setPersonalAreaMemo(housingInfo.getPersonalAreaMemo());

				if (buildingInfo != null) {
					// �K���^���݊K
					StringBuilder sbFloor = new StringBuilder();
					// ���K��
					sbFloor.append((buildingInfo.getTotalFloors() == null) ? "" : buildingInfo.getTotalFloors() + "�K��");
					sbFloor.append((isEmpty(buildingInfo.getTotalFloors()) && isEmpty(housingInfo.getFloorNo())) ? "" : "�@&frasl;�@");
					// �����̊K��
					sbFloor.append((housingInfo.getFloorNo() == null) ? "" : housingInfo.getFloorNo() + "�K");
					// �K���^���݊K
					setFloor(sbFloor.toString());
				}

			} else {

				// �}���V�����ʐσt���O
				setPersonalAreaFlg(false);

				// �ˌ� �y�n�ʐσt���O
				setBuildingAreaFlg(true);

				// �K���^���݊K�t���O
				setFloorFlg(false);

				if (buildingDtlInfo != null) {
					// �����ʐ�
					setBuildingArea(defaultString(buildingDtlInfo.getBuildingArea()));

					// �����ʐ� ��
					setBuildingAreaSquare((buildingDtlInfo.getBuildingArea() == null) ? "" : "�i��" + PanaCalcUtil.calcTsubo(buildingDtlInfo.getBuildingArea()).toString() + "�؁j");

					// �����ʐ�_�⑫
					setBuildingAreaMemo(buildingDtlInfo.getBuildingAreaMemo());
				}

				// �y�n�ʐ�
				setLandArea(defaultString(housingInfo.getLandArea()));

				// �y�n�ʐ� ��
				setLandAreaSquare((housingInfo.getLandArea() == null) ? "" : "�i��" + PanaCalcUtil.calcTsubo(housingInfo.getLandArea()).toString() + "�؁j");

				// �y�n�ʐ�_�⑫
				setLandAreaMemo(housingInfo.getLandAreaMemo());
			}

			if (buildingInfo != null) {

				// ���Ӓn�}
				setMapUrl(makeUrl(buildingInfo.getPrefCd(), housingInfo.getSysHousingCd(), getHousingKindCd()));
			}
		}
	}

	/**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� ���t�H�[���v��������ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param housingInfo ������{���
	 * @param buildingInfo ������{���
	 * @param reformPlanList ���t�H�[���v�������
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	private void setReformPlan(HousingInfo housingInfo, BuildingInfo buildingInfo, List<ReformPlan> reformPlanList) throws Exception {

		if (housingInfo != null && buildingInfo != null) {
			// �u�������CD�v �� �u03�F�y�n�v�̏ꍇ
			if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(getHousingKindCd())) {
				// �����̃��t�H�[���v�����̕\���t���O
				setReformPlanDisplayFlg(false);

				// ���t�H�[���v���������������̕\���t���O
				setReformPlanReadyDisplayFlg(false);

			} else {

				// ���t�H�[���v������񂪂���ꍇ
				if (reformPlanList != null && reformPlanList.size() > 0) {
					// �����̃��t�H�[���v�����̕\���t���O
					setReformPlanDisplayFlg(true);

					// ���t�H�[���v���������������̕\���t���O
					setReformPlanReadyDisplayFlg(false);

					int cnt = reformPlanList.size();
					String[] planNo = new String[cnt];
					String[] planName = new String[cnt];
					String[] planPrice = new String[cnt];
					String[] totalPrice1 = new String[cnt];
					String[] totalPrice2 = new String[cnt];
					String[] reformCdHidden = new String[cnt];
					String[] reformUrl = new String[cnt];
					String[] reformCategory = new String[cnt];

					for (int i = 0; i < cnt; i++) {
						StringBuilder sbReformPlan = new StringBuilder();
						StringBuilder sbTotalPrice1 = new StringBuilder();
						StringBuilder sbTotalPrice2 = new StringBuilder();
						StringBuilder sbReformCdHidden = new StringBuilder();

						// ���t�H�[���v�������
						ReformPlan reformPlan = reformPlanList.get(i);

						// �v�����ԍ��yhidden�z�u�z��v
						planNo[i] = String.valueOf(i);

						// �v�����^�C�v�u�z��v
						sbReformPlan.append(defaultString(reformPlan.getPlanName()));
						planName[i] = sbReformPlan.toString();

						// ���t�H�[�����i�u�z��v
						planPrice[i] = defaultString(reformPlan.getPlanPrice());

						// ���z�P�u�z��v
						if (housingInfo.getPrice() != null || reformPlan.getPlanPrice() != null) {
							sbTotalPrice1.append("��");
							sbTotalPrice1.append(formatPrice(sumPrice(housingInfo.getPrice(), reformPlan.getPlanPrice()), true));
							sbTotalPrice1.append("���~");
						}
						totalPrice1[i] = sbTotalPrice1.toString();

						// ���z�Q�u�z��v
						sbTotalPrice2.append((housingInfo.getPrice() == null) ? "" : "�������i�F" + formatPrice(housingInfo.getPrice(), true) + "���~");
						sbTotalPrice2.append((housingInfo.getPrice() == null || reformPlan.getPlanPrice() == null) ? "" : "�{");
						sbTotalPrice2.append((reformPlan.getPlanPrice() == null) ? "" : "���t�H�[�����i�F��" + formatPrice(reformPlan.getPlanPrice(), true) + "���~");
						totalPrice2[i] = sbTotalPrice2.toString();

						// �V�X�e�����t�H�[��CD�yhidden�z�u�z��v
						sbReformCdHidden.append(defaultString(reformPlan.getSysReformCd()));
						reformCdHidden[i] = sbReformCdHidden.toString();

						// �V�X�e�����t�H�[��URL�yhidden�z�u�z��v
						reformUrl[i] = (makeUrl(buildingInfo.getPrefCd(), housingInfo.getSysHousingCd(), getHousingKindCd(), reformPlan.getSysReformCd()));
						reformCategory[i] = reformPlan.getPlanCategory1();
					}

					// �v�����ԍ��yhidden�z�u�z��v
					setPlanNoHidden(planNo);

					// �v�����^�C�v�u�z��v
					setPlanType(planName);

					// ���t�H�[�����i�u�z��v
					setPlanPrice(planPrice);

					// ���z�P�u�z��v
					setTotalPrice1(totalPrice1);

					// ���z�Q�u�z��v
					setTotalPrice2(totalPrice2);

					// �V�X�e�����t�H�[��CD�yhidden�z�u�z��v
					setReformCdHidden(reformCdHidden);

					// �V�X�e�����t�H�[��URL�yhidden�z�u�z��v
					setReformUrl(reformUrl);
					
					setReformCategory(reformCategory);

				} else {

					// ���t�H�[���v���������������擾���Ȃ������ꍇ
					if (isEmpty(housingInfo.getReformComment())) {
						// �����̃��t�H�[���v�����̕\���t���O
						setReformPlanDisplayFlg(false);

						// ���t�H�[���v���������������̕\���t���O
						setReformPlanReadyDisplayFlg(false);

					} else {

						// �����̃��t�H�[���v�����̕\���t���O
						setReformPlanDisplayFlg(false);

						// ���t�H�[���v���������������̕\���t���O
						setReformPlanReadyDisplayFlg(true);

						// ���t�H�[���v��������������
						setReformPlanReadyComment(PanaStringUtils.encodeHtml(housingInfo.getReformComment()));
					}
				}
			}
		}
	}

	/**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� �摜����ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param housingImageInfoList �摜���
	 * @param housingExtInfosMap �����g���������
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	private void setHousingImageInfo(List<HousingImageInfo> housingImageInfoList, Map<String, Map<String, String>> housingExtInfosMap) throws Exception {

		if (housingImageInfoList != null && housingImageInfoList.size() > 0) {
			// �摜�̕\���t���O
			setImgDisplayFlg(true);

			int cnt = housingImageInfoList.size();
			int j = 0;
			int k = 0;
			String[] imageNo = new String[cnt];
			String[] imageType = new String[cnt];
			String[] path1 = new String[cnt];
			String[] path2 = new String[cnt];
			String[] imgComment = new String[cnt];

			for (int i = 0; i < cnt; i++) {

				// �摜���
				jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo housingImageInfo = (jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo) housingImageInfoList.get(i);

				// �����̏ꍇ
				if (!isMemberFlg()) {

					// �S���̉摜���{���Ƃ���B
					if (PanaCommonConstant.ROLE_ID_PUBLIC.equals(housingImageInfo.getRoleId())) {

						// �摜�ԍ��yhidden�z�u�z��v
						imageNo[j] = String.valueOf(j);

						// �p�X���P�yhidden�z�u�z��v
						path1[j] = getImgPath(housingImageInfo.getRoleId(), housingImageInfo.getPathName(), getCommonParameters().getHousingDtlImageSmallSize(),
								housingImageInfo.getFileName());

						// �p�X���Q�yhidden�z�u�z��v
						path2[j] = getImgPath(housingImageInfo.getRoleId(), housingImageInfo.getPathName(), getCommonParameters().getHousingDtlImageLargeSize(),
								housingImageInfo.getFileName());

						// �R�����g�yhidden�z�u�z��v
						imgComment[j] = housingImageInfo.getImgComment();

						j++;
					}

				} else {

					// �摜�ԍ��yhidden�z�u�z��v
					imageNo[k] = String.valueOf(k);

					// �p�X���P�yhidden�z�u�z��v
					path1[k] = getImgPath(housingImageInfo.getRoleId(), housingImageInfo.getPathName(), getCommonParameters().getHousingDtlImageSmallSize(),
							housingImageInfo.getFileName());

					// �p�X���Q�yhidden�z�u�z��v
					path2[k] = getImgPath(housingImageInfo.getRoleId(), housingImageInfo.getPathName(), getCommonParameters().getHousingDtlImageLargeSize(),
							housingImageInfo.getFileName());

					// �R�����g�yhidden�z�u�z��v
					imgComment[k] = housingImageInfo.getImgComment();

					k++;
				}

				// �摜�^�C�v�yhidden�z�u�z��v
				imageType[i] = housingImageInfo.getImageType();
			}

			// ���ω摜�t���O
			setIntrospectImgFlg(false);

			// �����摜�����J��Ԃ��A�摜�^�C�v�𔻒f����B
			for (String type : imageType) {
				if (PanaCommonConstant.IMAGE_TYPE_03.equals(type)) {
					// ���ω摜�t���O
					setIntrospectImgFlg(true);
					break;
				}
			}

			// �摜�ԍ��yhidden�z�u�z��v
			setImgNoHidden(imageNo);

			// �p�X���P�yhidden�z�u�z��v
			setHousingImgPath1Hidden(path1);

			// �p�X���Q�yhidden�z�u�z��v
			setHousingImgPath2Hidden(path2);

			// �R�����g�yhidden�z�u�z��v
			setHousingImgCommentHidden(imgComment);

		} else {

			// �摜�̕\���t���O
			setImgDisplayFlg(false);

			// ���ω摜�t���O
			setIntrospectImgFlg(false);
		}

		if (housingExtInfosMap != null) {

			// �J�e�S�����ɊY������ Map ���擾����B
			Map<String, String> housingDetailMap = housingExtInfosMap.get("housingDetail");

			if (housingDetailMap != null) {

				// ����p�X�yhidden�z
				setMovieUrl(housingDetailMap.get("movieUrl"));
			}
		}
	}

	/**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� ����̃R�����g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param housingExtInfosMap �����g���������
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	private void setSaleComment(Map<String, Map<String, String>> housingExtInfosMap) throws Exception {

		if (housingExtInfosMap != null) {

			// �J�e�S�����ɊY������ Map ���擾����B
			Map<String, String> housingDetailMap = housingExtInfosMap.get("housingDetail");

			if (housingDetailMap != null) {

				// ����̃R�����g
				setSalesComment(PanaStringUtils.encodeHtml(housingDetailMap.get("vendorComment")));
			}
		}
	}

	/**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� �S���҂���̂������߂�ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param housingInfo ������{���
	 * @param housingExtInfosMap �����g���������
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	private void setRecommend(HousingInfo housingInfo, Map<String, Map<String, String>> housingExtInfosMap) throws Exception {

		if (housingInfo != null && housingExtInfosMap != null) {

			// �J�e�S�����ɊY������ Map ���擾����B
			Map<String, String> housingDetailMap = housingExtInfosMap.get("housingDetail");

			if (housingDetailMap != null) {
				// �������߉摜�p�X�yhidden�z
				setStaffimagePathName(getImgPath(PanaCommonConstant.ROLE_ID_PUBLIC, housingDetailMap.get("staffImagePathName"), getCommonParameters().getAdminSiteStaffFolder(),
						housingDetailMap.get("staffImageFileName")));

				// �S��
				setStaffName(housingDetailMap.get("staffName"));

				// ��Ж�
				setCompanyName(housingDetailMap.get("companyName"));

				// �x�X��
				setBranchName(housingDetailMap.get("branchName"));

				// �Ƌ��ԍ�
				setLicenseNo(housingDetailMap.get("licenseNo"));
			}

			// �������ߓ��e
			setRecommendComment(PanaStringUtils.encodeHtml(housingInfo.getBasicComment()));
		}
	}

	/**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� �����ڍ׏���ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param housingInfo ������{���
	 * @param housingDtlInfo �����ڍ׏��
	 * @param housingExtInfosMap �����g���������
	 * @param buildingDtlInfo �����ڍ׏��
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	private void setHousingDtlInfo(HousingInfo housingInfo, HousingDtlInfo housingDtlInfo, Map<String, Map<String, String>> housingExtInfosMap, BuildingDtlInfo buildingDtlInfo)
			throws Exception {

		// �u�������CD�v �� �u01�F�}���V�����v�̏ꍇ
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(getHousingKindCd())) {
			if (housingDtlInfo != null) {
				// �o���R�j�[�ʐ�
				setBalconyArea(defaultString(housingDtlInfo.getBalconyArea()));

				// �Ǘ��`��
				setUpkeepType(housingDtlInfo.getUpkeepType());

				// �~�n����
				setLandRight(housingDtlInfo.getLandRight());

				// �p�r�n��
				setUsedAreaCd(housingDtlInfo.getUsedAreaCd());

				// ���n��
				setMoveinTiming(housingDtlInfo.getMoveinTiming());

				// ���n�����R�����g
				setMoveinNote(housingDtlInfo.getMoveinNote());

				// ����`��
				setTransactTypeDiv(housingDtlInfo.getTransactTypeDiv());

				// ���L����
				setSpecialInstruction(housingDtlInfo.getSpecialInstruction());

				// ���l
				setUpkeepCorp(isEmpty(housingDtlInfo.getUpkeepCorp()) ? "" : "�Ǘ���ЁF" + housingDtlInfo.getUpkeepCorp());
			}

			if (housingInfo != null) {
				// �Ǘ��
				setUpkeep((housingInfo.getUpkeep() == null) ? "" : formatPrice(housingInfo.getUpkeep(), false) + "�~ &frasl; ��");

				// �C�U�ϗ���
				setMenteFee((housingInfo.getMenteFee() == null) ? "" : formatPrice(housingInfo.getMenteFee(), false) + "�~ &frasl; ��");

				// ���ԏ�
				setParkingSituation(housingInfo.getDisplayParkingInfo());

				if (housingInfo.getUpdDate() != null) {
					// �X�V��
					setUpdDate(new SimpleDateFormat("yyyy�NMM��dd��").format(housingInfo.getUpdDate()));

					// ����X�V�\��
					setNextUpdDate("�i����X�V�\�� �F" + new SimpleDateFormat("yyyy�NMM��dd��").format(dateAdd(housingInfo.getUpdDate())) + "�j");
				}
			}

			if (housingExtInfosMap != null) {
				// �J�e�S�����ɊY������ Map ���擾����B
				Map<String, String> housingDetailMap = housingExtInfosMap.get("housingDetail");

				if (housingDetailMap != null) {
					// ��v�̌�
					setDirection(housingDetailMap.get("direction"));

					// �\��
					setStruct(housingDetailMap.get("struct"));

					// ���ː�
					setTotalHouseCnt(housingDetailMap.get("totalHouseCnt"));

					// �K��
					setScale(housingDetailMap.get("scale"));

					// ����
					setStatus(housingDetailMap.get("status"));

					// �C���t��
					setInfrastructure(housingDetailMap.get("infrastructure"));
				}
			}

		} else {

			if (housingDtlInfo != null) {
				// �������S
				setPrivateRoad(housingDtlInfo.getPrivateRoad());

				// �y�n����
				setLandRight(housingDtlInfo.getLandRight());

				// �p�r�n��
				setUsedAreaCd(housingDtlInfo.getUsedAreaCd());

				// ���n��
				setMoveinTiming(housingDtlInfo.getMoveinTiming());

				// ���n�����R�����g
				setMoveinNote(housingDtlInfo.getMoveinNote());

				// ����`��
				setTransactTypeDiv(housingDtlInfo.getTransactTypeDiv());

				// �ړ�
				setContactRoad(housingDtlInfo.getContactRoad());

				// �ړ�����/����
				setContactRoadDir(housingDtlInfo.getContactRoadDir());

				// ���r�ی�
				setInsurExist(housingDtlInfo.getInsurExist());

				// ���L����
				setSpecialInstruction(housingDtlInfo.getSpecialInstruction());

				// ���l
				setUpkeepCorp(isEmpty(housingDtlInfo.getUpkeepCorp()) ? "" : "�Ǘ���ЁF" + housingDtlInfo.getUpkeepCorp());
			}

			if (housingInfo != null) {
				// ���ԏ�
				setParkingSituation(housingInfo.getDisplayParkingInfo());

				if (housingInfo.getUpdDate() != null) {
					// �X�V��
					setUpdDate(new SimpleDateFormat("yyyy�NMM��dd��").format(housingInfo.getUpdDate()));

					// ����X�V�\��
					setNextUpdDate("�i����X�V�\�� �F" + new SimpleDateFormat("yyyy�NMM��dd��").format(dateAdd(housingInfo.getUpdDate())) + "�j");
				}
			}

			if (housingExtInfosMap != null) {
				// �J�e�S�����ɊY������ Map ���擾����B
				Map<String, String> housingDetailMap = housingExtInfosMap.get("housingDetail");

				if (housingDetailMap != null) {
					// �\��
					setStruct(housingDetailMap.get("struct"));

					// ����
					setStatus(housingDetailMap.get("status"));

					// �C���t��
					setInfrastructure(housingDetailMap.get("infrastructure"));
				}
			}

			if (buildingDtlInfo != null) {
				// ���؂���
				setCoverage(buildingDtlInfo.getCoverageMemo());

				// �e�ϗ�
				setBuildingRate(buildingDtlInfo.getBuildingRateMemo());
			}
		}
	}

	/**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� ����������ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param housingEquipInfos �����ݔ����
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	private void setHousingEquipInfo(Map<String, EquipMst> housingEquipInfos) throws Exception {

		// �u�������CD�v �� �u03�F�y�n�v�ȊO�̏ꍇ
		if (!PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(getHousingKindCd())) {

			if (housingEquipInfos != null && housingEquipInfos.size() > 0) {
				StringBuilder sbName = new StringBuilder();

				// �����ݔ������J��Ԃ��A�����������쐬����B
				for (String key : housingEquipInfos.keySet()) {
					EquipMst equip = housingEquipInfos.get(key);
					sbName.append(equip.getEquipName()).append("/");
				}

				// ��������
				setEquipName(sbName.toString().substring(0, sbName.toString().lastIndexOf("/")));
			}

		} else {

			// ���������̕\���t���O
			setHousingPropertyDisplayFlg(false);
		}
	}

	/**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� �n�����ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param buildingLandmarkList ���������h�}�[�N���
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	private void setBuildingLandmark(List<BuildingLandmark> buildingLandmarkList) throws Exception {

		if (buildingLandmarkList != null && buildingLandmarkList.size() > 0) {
			// �n����̕\���t���O
			setLandmarkDisplayFlg(true);

			int cnt = buildingLandmarkList.size();
			String[] landmarkNo = new String[cnt];
			String[] landmarkType = new String[cnt];
			String[] landmarkName = new String[cnt];
			String[] distanceFromLandmark = new String[cnt];
			for (int i = 0; i < cnt; i++) {

				// �n����
				BuildingLandmark buildingLandmark = buildingLandmarkList.get(i);

				StringBuilder sbLandmark = new StringBuilder();

				// �n����ԍ��yhidden�z�u�z��v
				landmarkNo[i] = String.valueOf(i);

				// �����h�}�[�N�̎�ށu�z��v
				landmarkType[i] = buildingLandmark.getLandmarkType();

				// �n����i���́j�u�z��v
				landmarkName[i] = buildingLandmark.getLandmarkName();

				// �n����i���v����/�����j�u�z��v
				if (!isEmpty(buildingLandmark.getDistanceFromLandmark())) {
					sbLandmark.append("�k��");
					sbLandmark.append(PanaCalcUtil.calcLandMarkTime(buildingLandmark.getDistanceFromLandmark()));
					sbLandmark.append("���i");
					sbLandmark.append(buildingLandmark.getDistanceFromLandmark());
					sbLandmark.append("m�j");
				}

				// �����h�}�[�N����̓k�����ԂƋ���
				distanceFromLandmark[i] = sbLandmark.toString();
			}

			// �n����ԍ��yhidden�z�u�z��v
			setLandmarkNoHidden(landmarkNo);

			// �����h�}�[�N�̎�ށu�z��v
			setLandmarkType(landmarkType);

			// �n����i���́j�u�z��v
			setLandmarkName(landmarkName);

			// �n����i���v����/�����j�u�z��v
			setDistanceFromLandmark(distanceFromLandmark);

		} else {

			// �n����̕\���t���O
			setLandmarkDisplayFlg(false);
		}
	}

	/**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� �������߃��t�H�[���v�������ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param housingInfo ������{���
	 * @param reformPlan ���t�H�[���v����
	 * @param reformDtlList ���t�H�[���ڍ׏��
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	private void setRecommendReformPlan(HousingInfo housingInfo, ReformPlan reformPlan, List<ReformDtl> reformDtlList) throws Exception {

		// �������߃��t�H�[���v������̕\���t���O �� false�̏ꍇ
		if (!isRecommendReformPlanDisplayFlg()) {
			return;
		}

		if (reformPlan != null) {
			// ���t�H�[���v������
			setPlanName(reformPlan.getPlanName());

			// �Z�[���X�|�C���g
			setSalesPoint(PanaStringUtils.encodeHtml(reformPlan.getSalesPoint()));

			if (housingInfo != null) {
				// ���z�P
				if (housingInfo.getPrice() != null || reformPlan.getPlanPrice() != null) {
					setTotalDtlPrice1("��" + formatPrice(sumPrice(housingInfo.getPrice(), reformPlan.getPlanPrice()), true) + "���~");
				}

				StringBuilder sbTotalPrice2 = new StringBuilder();
				sbTotalPrice2.append((housingInfo.getPrice() == null) ? "" : "�������i�F" + formatPrice(housingInfo.getPrice(), true) + "���~");
				sbTotalPrice2.append((housingInfo.getPrice() == null || reformPlan.getPlanPrice() == null) ? "" : "�{");
				sbTotalPrice2.append((reformPlan.getPlanPrice() == null) ? "" : "���t�H�[�����i�F��" + formatPrice(reformPlan.getPlanPrice(), true) + "���~");
				// ���z�Q
				setTotalDtlPrice2(sbTotalPrice2.toString());
			}

			// �H��
			setConstructionPeriod(reformPlan.getConstructionPeriod());

			// ���l
			setReformNote(PanaStringUtils.encodeHtml(reformPlan.getNote()));
		}

		if (reformDtlList != null && reformDtlList.size() > 0) {
			int cnt = reformDtlList.size();
			String[] planNo = new String[cnt];
			String[] planName = new String[cnt];
			String[] planPathName = new String[cnt];
			String[] price = new String[cnt];

			int i = 0;
			for (int j = 0; j < cnt; j++) {

				ReformDtl reformDtl = reformDtlList.get(j);

				// �����̏ꍇ
				if (!isMemberFlg()) {

					// �S���̉摜���{���Ƃ���B
					if (PanaCommonConstant.ROLE_ID_PUBLIC.equals(reformDtl.getRoleId())) {

						// ���t�H�[���ڍ�_�ԍ��yhidden�z�u�z��v
						planNo[i] = String.valueOf(i);

						// ���t�H�[���ڍ�_���ږ��́u�z��v
						planName[i] = reformDtl.getImgName();

						// ���t�H�[���ڍ�_�摜�p�X���yhidden�z�u�z��v
						planPathName[i] = getImgPath(reformDtl.getRoleId(), reformDtl.getPathName(), getCommonParameters().getAdminSitePdfFolder(), reformDtl.getFileName(),
								REFORM_IMG_MODE);

						// ���t�H�[���ڍ�_���ڃ��t�H�[�����i�u�z��v
						price[i] = (reformDtl.getReformPrice() == null) ? "" : "��" + formatPrice(reformDtl.getReformPrice(), true) + "���~";

						i++;

					}

				} else {

					// ���t�H�[���ڍ�_�ԍ��yhidden�z�u�z��v
					planNo[j] = String.valueOf(j);

					// ���t�H�[���ڍ�_���ږ��́u�z��v
					planName[j] = reformDtl.getImgName();

					// ���t�H�[���ڍ�_�摜�p�X���yhidden�z�u�z��v
					planPathName[j] = getImgPath(reformDtl.getRoleId(), reformDtl.getPathName(), getCommonParameters().getAdminSitePdfFolder(), reformDtl.getFileName(),
							REFORM_IMG_MODE);

					// ���t�H�[���ڍ�_���ڃ��t�H�[�����i�u�z��v
					price[j] = (reformDtl.getReformPrice() == null) ? "" : "��" + formatPrice(reformDtl.getReformPrice(), true) + "���~";
				}

			}
			// ���t�H�[���ڍ�_�ԍ��yhidden�z�u�z��v
			setReformNoHidden(planNo);

			// ���t�H�[���ڍ�_���ږ��́u�z��v
			setReformImgName(planName);

			// ���t�H�[���ڍ�_�摜�p�X���yhidden�z�u�z��v
			setReformPathName(planPathName);

			// ���t�H�[���ڍ�_���ڃ��t�H�[�����i�u�z��v
			setReformPrice(price);
		}
	}

	/**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� ���t�H�[���C���[�W��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param reformPlan ���t�H�[���v����
	 * @param reformImgList ���t�H�[���摜���
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	private void setReformImg(ReformPlan reformPlan, List<ReformImg> reformImgList) throws Exception {

		// ���t�H�[���C���[�W�̕\���t���O  �� false�̏ꍇ
		if (!isReformImgDisplayFlg()) {
			return;
		}

		if (reformImgList != null && reformImgList.size() > 0) {
			int cnt = reformImgList.size();
			String[] afterPathNo = new String[cnt];
			String[] afterPathName1 = new String[cnt];
			String[] afterPathName2 = new String[cnt];
			String[] afterPathComment = new String[cnt];
			String[] beforePathNo = new String[cnt];
			String[] beforePathName1 = new String[cnt];
			String[] beforePathName2 = new String[cnt];
			String[] beforePathComment = new String[cnt];

			int i = 0;

			for (int j = 0; j < cnt; j++) {

				ReformImg reformImg = reformImgList.get(j);

				// �����̏ꍇ
				if (!isMemberFlg()) {

					// �S���̉摜���{���Ƃ���B
					if (PanaCommonConstant.ROLE_ID_PUBLIC.equals(reformImg.getRoleId())) {

						// ���t�H�[����_�O�ρE���ӎʐ^�ԍ��yhidden�z�u�z��v
						afterPathNo[i] = String.valueOf(i);

						// ���t�H�[����_�O�ρE���ӎʐ^�p�X�P�yhidden�z�u�z��v
						afterPathName1[i] = getImgPath(reformImg.getRoleId(), reformImg.getAfterPathName(), getCommonParameters().getHousingDtlImageSmallSize(),
								reformImg.getAfterFileName(), REFORM_IMG_MODE);

						// ���t�H�[����_�O�ρE���ӎʐ^�p�X�Q�yhidden�z�u�z��v
						afterPathName2[i] = getImgPath(reformImg.getRoleId(), reformImg.getAfterPathName(), getCommonParameters().getHousingDtlImageLargeSize(),
								reformImg.getAfterFileName(), REFORM_IMG_MODE);

						// ���t�H�[����_�O�ρE���ӎʐ^ �R�����g�yhidden�z�u�z��v
						afterPathComment[i] = reformImg.getAfterComment();

						// ���t�H�[���O_�O�ρE���ӎʐ^�ԍ��yhidden�z�u�z��v
						beforePathNo[i] = String.valueOf(i);

						// ���t�H�[���O_�O�ρE���ӎʐ^�p�X�P�yhidden�z�u�z��v
						beforePathName1[i] = getImgPath(reformImg.getRoleId(), reformImg.getBeforePathName(), getCommonParameters().getHousingDtlImageSmallSize(),
								reformImg.getBeforeFileName(), REFORM_IMG_MODE);

						// ���t�H�[���O_�O�ρE���ӎʐ^�p�X�Q�yhidden�z�u�z��v
						beforePathName2[i] = getImgPath(reformImg.getRoleId(), reformImg.getBeforePathName(), getCommonParameters().getHousingDtlImageLargeSize(),
								reformImg.getBeforeFileName(), REFORM_IMG_MODE);

						// ���t�H�[���O_�O�ρE���ӎʐ^ �R�����g�yhidden�z�u�z��v
						beforePathComment[i] = reformImg.getBeforeComment();

						i++;
					}

				} else {

					// ���t�H�[����_�O�ρE���ӎʐ^�ԍ��yhidden�z�u�z��v
					afterPathNo[j] = String.valueOf(j);

					// ���t�H�[����_�O�ρE���ӎʐ^�p�X�P�yhidden�z�u�z��v
					afterPathName1[j] = getImgPath(reformImg.getRoleId(), reformImg.getAfterPathName(), getCommonParameters().getHousingDtlImageSmallSize(),
							reformImg.getAfterFileName(), REFORM_IMG_MODE);

					// ���t�H�[����_�O�ρE���ӎʐ^�p�X�Q�yhidden�z�u�z��v
					afterPathName2[j] = getImgPath(reformImg.getRoleId(), reformImg.getAfterPathName(), getCommonParameters().getHousingDtlImageLargeSize(),
							reformImg.getAfterFileName(), REFORM_IMG_MODE);

					// ���t�H�[����_�O�ρE���ӎʐ^ �R�����g�yhidden�z�u�z��v
					afterPathComment[j] = reformImg.getAfterComment();

					// ���t�H�[���O_�O�ρE���ӎʐ^�ԍ��yhidden�z�u�z��v
					beforePathNo[j] = String.valueOf(j);

					// ���t�H�[���O_�O�ρE���ӎʐ^�p�X�P�yhidden�z�u�z��v
					beforePathName1[j] = getImgPath(reformImg.getRoleId(), reformImg.getBeforePathName(), getCommonParameters().getHousingDtlImageSmallSize(),
							reformImg.getBeforeFileName(), REFORM_IMG_MODE);

					// ���t�H�[���O_�O�ρE���ӎʐ^�p�X�Q�yhidden�z�u�z��v
					beforePathName2[j] = getImgPath(reformImg.getRoleId(), reformImg.getBeforePathName(), getCommonParameters().getHousingDtlImageLargeSize(),
							reformImg.getBeforeFileName(), REFORM_IMG_MODE);

					// ���t�H�[���O_�O�ρE���ӎʐ^ �R�����g�yhidden�z�u�z��v
					beforePathComment[j] = reformImg.getBeforeComment();
				}
			}

			// ���t�H�[����_�O�ρE���ӎʐ^�ԍ��yhidden�z�u�z��v
			setAfterPathNoHidden(afterPathNo);

			// ���t�H�[����_�O�ρE���ӎʐ^�p�X�P�yhidden�z�u�z��v
			setAfterPath1(afterPathName1);

			// ���t�H�[����_�O�ρE���ӎʐ^�p�X�Q�yhidden�z�u�z��v
			setAfterPath2(afterPathName2);

			// ���t�H�[����_�O�ρE���ӎʐ^ �R�����g�yhidden�z�u�z��v
			setAfterPathComment(afterPathComment);

			// ���t�H�[���O_�O�ρE���ӎʐ^�ԍ��yhidden�z�u�z��v
			setBeforePathNoHidden(beforePathNo);

			// ���t�H�[���O_�O�ρE���ӎʐ^�p�X�P�yhidden�z�u�z��v
			setBeforePath1(beforePathName1);

			// ���t�H�[���O_�O�ρE���ӎʐ^�p�X�Q�yhidden�z�u�z��v
			setBeforePath2(beforePathName2);

			// ���t�H�[����_�O�ρE���ӎʐ^ �R�����g�yhidden�z�u�z��v
			setBeforePathComment(beforePathComment);
		}

		if (reformPlan != null) {

			// �����̏ꍇ
			if (!isMemberFlg()) {

				// �S���̓��悪�{���Ƃ���B
				if (PanaCommonConstant.ROLE_ID_PUBLIC.equals(reformPlan.getRoleId())) {

					// ���t�H�[����_����p�T���l�C���yhidden�z�u�z��v
					setAfterMovieUrl(reformPlan.getAfterMovieUrl());

					// ���t�H�[���O_����p�T���l�C���yhidden�z�u�z��v
					setBeforeMovieUrl(reformPlan.getBeforeMovieUrl());
				}

			} else {

				// ���t�H�[����_����p�T���l�C���yhidden�z�u�z��v
				setAfterMovieUrl(reformPlan.getAfterMovieUrl());

				// ���t�H�[���O_����p�T���l�C���yhidden�z�u�z��v
				setBeforeMovieUrl(reformPlan.getBeforeMovieUrl());
			}
		}
	}

	/**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� �Z��f�f����ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param housingInspectionList �����C���X�y�N�V����
	 * @param housingExtInfosMap �����g���������
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	private void setInspection(List<HousingInspection> housingInspectionList, Map<String, Map<String, String>> housingExtInfosMap) throws Exception {

		if (housingExtInfosMap != null) {

			// �J�e�S�����ɊY������ Map ���擾����B
			Map<String, String> housingInspectionMap = housingExtInfosMap.get("housingInspection");

			if (housingInspectionMap != null) {

				// �Z��f�f���{��
				setInspectionExist(housingInspectionMap.get("inspectionExist"));

				// �Z��f�f���}�yhidden�z
				setInspectionImagePathName(getImgPath(PanaCommonConstant.ROLE_ID_PRIVATE, housingInspectionMap.get("inspectionImagePathName"), getCommonParameters()
						.getAdminSiteChartFolder(),
						housingInspectionMap.get("inspectionImageFileName")));

				// �Z��f�f�t�@�C���yhidden�z
				setInspectionPathName(getImgPath(PanaCommonConstant.ROLE_ID_PRIVATE, housingInspectionMap.get("inspectionPathName"), getCommonParameters()
						.getAdminSitePdfFolder(),
						housingInspectionMap.get("inspectionFileName")));
			}
		}

		if (housingInspectionList != null && housingInspectionList.size() > 0) {
			int cnt = housingInspectionList.size();
			String[] inspectionNo = new String[cnt];
			String[] inspectionKey = new String[cnt];
			String[] inspectionTrust = new String[cnt];

			for (int i = 0; i < cnt; i++) {

				HousingInspection housingInspection = housingInspectionList.get(i);

				// �m�F���x���ԍ��yhidden�z�u�z��v
				inspectionNo[i] = String.valueOf(i);

				// �m�F���x���i���́j�u�z��v
				inspectionKey[i] = housingInspection.getInspectionKey();

				// �m�F���x���u�z��v
				inspectionTrust[i] = defaultString(housingInspection.getInspectionTrust());
			}

			// �m�F���x���ԍ��yhidden�z�u�z��v
			setInspectionNoHidden(inspectionNo);

			// �m�F���x���i���́j�u�z��v
			setInspectionKey(inspectionKey);

			// �m�F���x���u�z��v
			setInspectionTrust(inspectionTrust);
		}
	}

	/**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� ����o�^�̕\���t���O��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	private void setMemberLoginFlg() throws Exception {

		// ����t���O �� false���A�i���ω摜�t���O �� true�܂��́A�Z��f�f���{�L�j���A�u�������CD�v �� �u03�F�y�n�v�̏ꍇ
		if (!isMemberFlg() && (isIntrospectImgFlg() || PanaCommonConstant.HOUSING_INSPECTION_EXIST.equals(getInspectionExist()))
				&& !PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(getHousingKindCd())) {
			// ����o�^�̕\���t���O
			setLoginDisplayFlg(true);
		}
	}

	/**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� ���̑��̃��t�H�[���v������ݒ肷��B<br/>
	 * <br/>
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	protected void setOtherReformPlan() throws Exception {

		// �u�������CD�v �� �u03�F�y�n�v�̏ꍇ
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(getHousingKindCd())) {
			// ���̑��̃��t�H�[���v�����̕\���t���O
			setOtherReformPlanDisplayFlg(false);

		} else {

			// ���̑��̃��t�H�[���v�����̕\���t���O
			setOtherReformPlanDisplayFlg(true);

			// ���[�h �� detail�̏ꍇ
			if (DETAIL_MODE.equals(getMode())) {
				// ���̑��̃v�����ԍ��yhidden�z�u�z��v
				setOtherPlanNoHidden(getPlanNoHidden());

				// ���̑��̃v�����^�C�v�u�z��v
				setOtherPlanType(getPlanType());

				// ���̑��̑��z�P�u�z��v
				setOtherTotalPrice1(getTotalPrice1());

				// ���̑��̑��z�Q�u�z��v
				setOtherTotalPrice2(getTotalPrice2());

				// ���̑��̃V�X�e�����t�H�[��CD�yhidden�z�u�z��v
				setOtherReformCdHidden(getReformCdHidden());

				// ���̑��̃V�X�e�����t�H�[��URL�yhidden�z�u�z��v
				setOtherReformUrl(getReformUrl());

			} else if (REFORM_MODE.equals(getMode())) {

				if (getPlanNoHidden() != null && getPlanNoHidden().length > 1) {

					int cnt = getPlanNoHidden().length;
					int j = 0;
					String[] planNo = new String[cnt];
					String[] planName = new String[cnt];
					String[] totalPrice1 = new String[cnt];
					String[] totalPrice2 = new String[cnt];
					String[] reformCdHidden = new String[cnt];
					String[] reformUrl = new String[cnt];
					String[] reformCategory = new String[cnt];
 
					for (int i = 0; i < getPlanNoHidden().length; i++) {
						if (getReformCdHidden()[i] != null && !getReformCdHidden()[i].equals(getReformCd())) {
							// ���̑��̃v�����ԍ��yhidden�z�u�z��v
							planNo[j] = String.valueOf(j);

							// ���̑��̃v�����^�C�v�u�z��v
							planName[j] = getPlanType()[i];

							// ���̑��̑��z�P�u�z��v
							totalPrice1[j] = getTotalPrice1()[i];

							// ���̑��̑��z�Q�u�z��v
							totalPrice2[j] = getTotalPrice2()[i];

							// ���̑��̃V�X�e�����t�H�[��CD�yhidden�z�u�z��v
							reformCdHidden[j] = getReformCdHidden()[i];

							// ���̑��̃V�X�e�����t�H�[��URL�yhidden�z�u�z��v
							reformUrl[j] = getReformUrl()[i];
							
							// set reformCategory
							reformCategory[j] = getReformCategory()[i];

							j++;
						}
					}

					// ���̑��̃v�����ԍ��yhidden�z�u�z��v
					setOtherPlanNoHidden(planNo);

					// ���̑��̃v�����^�C�v�u�z��v
					setOtherPlanType(planName);

					// ���̑��̑��z�P�u�z��v
					setOtherTotalPrice1(totalPrice1);

					// ���̑��̑��z�Q�u�z��v
					setOtherTotalPrice2(totalPrice2);

					// ���̑��̃V�X�e�����t�H�[��CD�yhidden�z�u�z��v
					setOtherReformCdHidden(reformCdHidden);

					// ���̑��̃V�X�e�����t�H�[��URL�yhidden�z�u�z��v
					setOtherReformUrl(reformUrl);
					
					setReformCategory(reformCategory);
					
				}
			}
		}
	}

	/**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� �ŋߌ���������ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param recentlyInfoList �ŋߌ����������
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	private void setRecentlyInfo(List<PanaHousing> recentlyInfoList) throws Exception {

		if (recentlyInfoList != null && recentlyInfoList.size() > 0) {

			// �ŋߌ���������ʕ\������
			int cnt = 4;

			int i = 0;
			String[] recentlyNoHidden = new String[cnt];
			String[] recentlySysHousingCdHidden = new String[cnt];
			String[] recentlyHousingCdHidden = new String[cnt];
			String[] recentlyPathName = new String[cnt];
			String[] recentlyHousingKindCd = new String[cnt];
			String[] recentlyDisplayHousingName = new String[cnt];
			String[] recentlyDisplayHousingNameFull = new String[cnt];
			String[] recentlyTotalPrice = new String[cnt];
			String[] recentlyLayoutCd = new String[cnt];
			String[] recentlyAccess = new String[cnt];
			String[] recentlyDtl = new String[cnt];
			String[] recentlyDtlFull = new String[cnt];
			String[] recentlyUrl = new String[cnt];
			List<HousingImageInfo> housingImageInfoList;

			for (PanaHousing housing : recentlyInfoList) {

				if (housing != null) {

					// �ŋ� �����ԍ��yhidden�z�u�z��v
					recentlyNoHidden[i] = String.valueOf(i);

					// �����摜���̃��X�g���擾����B
					housingImageInfoList = housing.getHousingImageInfos();

					if (housingImageInfoList != null && housingImageInfoList.size() > 0) {

						for (HousingImageInfo housingImageInfoTemp : housingImageInfoList) {

							// �摜���
							jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo housingImageInfo = (jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo) housingImageInfoTemp;

							// �����̏ꍇ
							if (!isMemberFlg()) {

								// �S���̉摜���{���Ƃ���B
								if (PanaCommonConstant.ROLE_ID_PUBLIC.equals(housingImageInfo.getRoleId())) {

									// �ŋ� �����摜�u�z��v
									recentlyPathName[i] = getImgPath(housingImageInfo.getRoleId(), housingImageInfo.getPathName(), getCommonParameters()
											.getHousingDtlHistoryImageSize(), housingImageInfo.getFileName());

									break;
								}

							} else {

								// �ŋ� �����摜�u�z��v
								recentlyPathName[i] = getImgPath(housingImageInfo.getRoleId(), housingImageInfo.getPathName(), getCommonParameters()
										.getHousingDtlHistoryImageSize(), housingImageInfo.getFileName());

								break;
							}
						}
					}

					// ������{�����擾����B
					HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");

					// ������{�����擾����B
					BuildingInfo buildingInfo = (BuildingInfo) housing.getBuilding().getBuildingInfo().getItems().get("buildingInfo");

					// �����Ŋ��w�����擾����B
					List<JoinResult> buildingStationInfoList = housing.getBuilding().getBuildingStationInfoList();

					if (buildingInfo != null) {
						// �������CD
						recentlyHousingKindCd[i] = buildingInfo.getHousingKindCd();
					}

					if (housingInfo != null) {

						// �ŋ� �V�X�e������CD�yhidden�z�u�z��v
						recentlySysHousingCdHidden[i] = housingInfo.getSysHousingCd();

						// �ŋ� �����ԍ��ydata-number�z�u�z��v
						recentlyHousingCdHidden[i] = housingInfo.getHousingCd();

						// �ŋ� ������FULL�u�z��v
						recentlyDisplayHousingNameFull[i] = housingInfo.getDisplayHousingName();

						// �ŋ� �������u�z��v
						recentlyDisplayHousingName[i] = (recentlyDisplayHousingNameFull[i] == null) ? ""
								: (recentlyDisplayHousingNameFull[i].length() > 6 ? recentlyDisplayHousingNameFull[i].substring(0, 6) + "�c" : recentlyDisplayHousingNameFull[i]);

						// �ŋ� �������i�u�z��v
						recentlyTotalPrice[i] = (housingInfo.getPrice() == null) ? "" : formatPrice(housingInfo.getPrice(), true) + "���~";

						// �ŋ� �����Ԏ��u�z��v
						recentlyLayoutCd[i] = getCodeLookupManager().lookupValue("layoutCd", defaultString(housingInfo.getLayoutCd()));

						if (buildingStationInfoList != null && buildingStationInfoList.size() > 0) {

							StringBuilder sbAccess = new StringBuilder();

							for (int j = 0; j < buildingStationInfoList.size(); j++) {

								// �����Ŋ��w���
								BuildingStationInfo buildingStationInfo = (BuildingStationInfo) buildingStationInfoList.get(j).getItems().get("buildingStationInfo");
								// �w���}�X�^
								StationMst stationMst = (StationMst) buildingStationInfoList.get(j).getItems().get("stationMst");
								if (stationMst != null) {
									// �w��
									sbAccess.append(defaultString(stationMst.getStationName(), buildingStationInfo.getStationName()) + " ");
								}
							}

							// �ŋ� �����A�N�Z�X�u�z��v
							recentlyAccess[i] = sbAccess.toString();
						}

						// �ŋ� �����ڍ�FULL�u�z��v
						if (!isEmpty(recentlyTotalPrice[i]) || !isEmpty(recentlyLayoutCd[i]) || !isEmpty(recentlyAccess[i])) {
							recentlyDtlFull[i] = defaultString(recentlyTotalPrice[i]) + " / " + defaultString(recentlyLayoutCd[i]) + " / " + defaultString(recentlyAccess[i]);
						}

						// �ŋ� �����ڍׁu�z��v
						recentlyDtl[i] = (recentlyDtlFull[i] == null) ? "" : (recentlyDtlFull[i].length() > 18 ? recentlyDtlFull[i].substring(0, 18) + "�c" : recentlyDtlFull[i]);

						// �V�X�e�����t�H�[��URL�yhidden�z�u�z��v
						recentlyUrl[i] = (makeUrl(buildingInfo.getPrefCd(), housingInfo.getSysHousingCd(), buildingInfo.getHousingKindCd()));
					}
				}

				i++;

				if (i >= cnt) {
					break;
				}
			}

			// �ŋ� �����ԍ��yhidden�z�u�z��v
			setRecentlyNoHidden(recentlyNoHidden);

			// �ŋ� �V�X�e������CD�yhidden�z�u�z��v
			setRecentlySysHousingCdHidden(recentlySysHousingCdHidden);

			// �ŋ� �����ԍ��ydata-number�z�u�z��v
			setRecentlyHousingCdHidden(recentlyHousingCdHidden);

			// �ŋ� �����摜�u�z��v
			setRecentlyPathName(recentlyPathName);

			// �ŋ� �������CD�u�z��v
			setRecentlyHousingKindCd(recentlyHousingKindCd);

			// �ŋ� �������u�z��v
			setRecentlyDisplayHousingName(recentlyDisplayHousingName);

			// �ŋ� ������FULL�u�z��v
			setRecentlyDisplayHousingNameFull(recentlyDisplayHousingNameFull);

			// �ŋ� �����ڍׁu�z��v
			setRecentlyDtl(recentlyDtl);

			// �ŋ� �����ڍ�FULL�u�z��v
			setRecentlyDtlFull(recentlyDtlFull);

			// �ŋ� ����URL
			setRecentlyUrl(recentlyUrl);
		}
	}

	/**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� �Č����̃����N��ݒ肷��B<br/>
	 * <br/>
	 * @param prefMst �s���{���}�X�^
	 * @param prefMstList �s���{�����X�g
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	private void setResearchLink(PrefMst prefMst, List<PrefMst> prefMstList) throws Exception {

		if (prefMst != null && prefMstList != null) {

			int cnt = prefMstList.size();
			int i = 0;
			String[] researchUrl = new String[cnt];
			String[] researchPrefName = new String[cnt];

			// �s���{�����X�g���J��Ԃ��A�Č���URL�u�z��v�ƍČ����s���{�����u�z��v���쐬����B
			for (PrefMst prefMstTmp : prefMstList) {

				// �n��CD����v���郌�R�[�h���擾
				if (prefMst.getAreaCd() != null && prefMst.getAreaCd().equals(prefMstTmp.getAreaCd())) {

					// �s���{��CD��CodeLookup�Œ�`�����ꍇ
					if (!isEmpty(getCodeLookupManager().lookupValue("researchPrefCd", prefMstTmp.getPrefCd()))) {

						// �Č���URL�u�z��v
						researchUrl[i] = makeUrl(prefMstTmp.getPrefCd(), getHousingKindCd());

						// �Č����s���{�����u�z��v
						researchPrefName[i] = prefMstTmp.getPrefName();

						i++;
					}
				}
			}

			// �Č���URL�u�z��v
			setResearchUrl(researchUrl);

			// �Č����s���{�����u�z��v
			setResearchPrefName(researchPrefName);
		}
	}

	/**
	 * ��ʂ̕\���t���O��ݒ肷��<br/>
	 *
	 */
	public void setDisplayFlg() {

		// �^�C�g���̕\���t���O
		setTitleDisplayFlg(!(isEmpty(getHousingKindCd()) && isEmpty(getSysHousingCd()) && isEmpty(getDisplayHousingName()) && isEmpty(getDtlComment())));

		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(getHousingKindCd())) {
			// �������̕\���t���O
			setHousingInfoDisplayFlg(!(isEmpty(getIconCd()) && isEmpty(getPrice()) && isEmpty(getAddress()) && isEmpty(getAccess()) && isEmpty(getLayoutCd())
					&& isEmpty(getCompDate()) && isEmpty(getPersonalArea()) && isEmpty(getPersonalAreaSquare()) && isEmpty(getPersonalAreaMemo()) && isEmpty(getFloor())));

			// �����ڍ׏��̕\���t���O
			setHousingDtlInfoDisplayFlg(!(isEmpty(getBalconyArea()) && isEmpty(getUpkeepType()) && isEmpty(getLandRight()) && isEmpty(getUsedAreaCd())
					&& isEmpty(getMoveinTiming()) && isEmpty(getMoveinNote()) && isEmpty(getTransactTypeDiv()) && isEmpty(getSpecialInstruction()) && isEmpty(getUpkeepCorp())
					&& isEmpty(getUpkeep()) && isEmpty(getMenteFee()) && isEmpty(getParkingSituation()) && isEmpty(getUpdDate()) && isEmpty(getNextUpdDate())
					&& isEmpty(getDirection()) && isEmpty(getStruct()) && isEmpty(getTotalHouseCnt()) && isEmpty(getScale()) && isEmpty(getStatus()) && isEmpty(getInfrastructure())));
		} else {
			// �������̕\���t���O
			setHousingInfoDisplayFlg(!(isEmpty(getIconCd()) && isEmpty(getPrice()) && isEmpty(getAddress()) && isEmpty(getAccess()) && isEmpty(getLayoutCd())
					&& isEmpty(getCompDate()) && isEmpty(getBuildingArea()) && isEmpty(getBuildingAreaSquare()) && isEmpty(getLandArea()) && isEmpty(getLandAreaSquare())));

			// �����ڍ׏��̕\���t���O
			setHousingDtlInfoDisplayFlg(!(isEmpty(getPrivateRoad()) && isEmpty(getLandRight()) && isEmpty(getUsedAreaCd()) && isEmpty(getMoveinTiming())
					&& isEmpty(getMoveinNote()) && isEmpty(getTransactTypeDiv()) && isEmpty(getContactRoad()) && isEmpty(getContactRoadDir()) && isEmpty(getInsurExist())
					&& isEmpty(getSpecialInstruction()) && isEmpty(getUpkeepCorp()) && isEmpty(getParkingSituation()) && isEmpty(getUpdDate()) && isEmpty(getNextUpdDate())
					&& isEmpty(getStruct()) && isEmpty(getStatus()) && isEmpty(getInfrastructure()) && isEmpty(getCoverage()) && isEmpty(getBuildingRate())));
		}

		// �����̃��t�H�[���v�����̕\���t���O
		setReformPlanDisplayFlg(!(isEmpty(getPlanType()) && isEmpty(getTotalPrice1()) && isEmpty(getTotalPrice2()) && isEmpty(getReformCdHidden()) && isEmpty(getReformUrl())));

		// �摜�̕\���t���O
		setImgDisplayFlg(!(isEmpty(getImgNoHidden()) && isEmpty(getHousingImgPath1Hidden()) && isEmpty(getMovieUrl())));

		// ����̃R�����g�̕\���t���O
		setSalesCommentDisplayFlg(!isEmpty(getSalesComment()));

		// �S���҂���̂������߂̕\���t���O
		setRecommendDisplayFlg(!(isEmpty(getStaffimagePathName()) && isEmpty(getStaffName()) && isEmpty(getCompanyName()) && isEmpty(getBranchName()) && isEmpty(getLicenseNo()) && isEmpty(getRecommendComment())));

		// ���������̕\���t���O
		setHousingPropertyDisplayFlg(!isEmpty(getEquipName()));

		// �n����̕\���t���O
		setLandmarkDisplayFlg(!(isEmpty(getLandmarkType()) && isEmpty(getLandmarkName()) && isEmpty(getDistanceFromLandmark())));

		// �������߃��t�H�[���v������̕\���t���O
		setRecommendReformPlanDisplayFlg(!(isEmpty(getPlanName()) && isEmpty(getSalesPoint()) && isEmpty(getTotalDtlPrice1()) && isEmpty(getTotalDtlPrice2())
				&& isEmpty(getConstructionPeriod()) && isEmpty(getReformNote()) && isEmpty(getReformImgName()) && isEmpty(getReformPathName()) && isEmpty(getReformPrice())));

		// ���t�H�[���C���[�W�̕\���t���O
		setReformImgDisplayFlg(!(isEmpty(getAfterPathNoHidden()) && isEmpty(getAfterPath1()) && isEmpty(getAfterMovieUrl()) && isEmpty(getBeforePathNoHidden())
				&& isEmpty(getBeforePath1()) && isEmpty(getBeforeMovieUrl())));

		// �Z��f�f���̕\���t���O
		setHousingInspectionDisplayFlg(!(isEmpty(getInspectionExist())) && PanaCommonConstant.HOUSING_INSPECTION_EXIST.equals(getInspectionExist()) && isMemberFlg()
				&& !PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(getHousingKindCd()));

		// ���̑��̃��t�H�[���v�����̕\���t���O
		setOtherReformPlanDisplayFlg(!(isEmpty(getOtherPlanType()) && isEmpty(getOtherTotalPrice1()) && isEmpty(getOtherTotalPrice2()) && isEmpty(getOtherReformCdHidden()) && isEmpty(getOtherReformUrl())));

		// �ŋߌ��������̕\���t���O
		setRecentlyDisplayFlg(!(isEmpty(getRecentlyPathName()) && isEmpty(getRecentlyHousingKindCd()) && isEmpty(getRecentlyDisplayHousingName()) && isEmpty(getRecentlyDtl()) && isEmpty(getRecentlyUrl())));
	}

	/**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� meta����ݒ肷��B<br/>
	 *
	 */
	public void setMeta() {

		// �L�[���[�h
		StringBuilder sbKeywords = new StringBuilder();
		sbKeywords.append(defaultString(getDisplayHousingName()));
		sbKeywords.append(",�p�i�\�j�b�N,");
		sbKeywords.append(getCommonParameters().getPanasonicSiteEnglish());
		sbKeywords.append(",");
		sbKeywords.append(getCommonParameters().getPanasonicSiteJapan());
		sbKeywords.append(",Re2,���[�E�X�N�G�A");

		// �L�[���[�h
		setKeywords(sbKeywords.toString());

		// ����
		StringBuilder sbDescription = new StringBuilder();
		sbDescription.append("�p�i�\�j�b�N��");
		sbDescription.append(getCommonParameters().getPanasonicSiteEnglish());
		sbDescription.append("(");
		sbDescription.append(getCommonParameters().getPanasonicSiteJapan());
		sbDescription.append(")�b");
		sbDescription.append(defaultString(getDisplayHousingName()));
		sbDescription.append("�̕������B");
		sbDescription.append(defaultString(getDtlComment()));
		sbDescription.append("�B");
		if (isReformPlanDisplayFlg() || isReformPlanReadyDisplayFlg()) {
			sbDescription.append("�����������Ɗ��������t�H�[���v������̂���Ă��B");
		}

		// ����
		setDescription(sbDescription.toString());
	}

	/**
	 * URL�ݒ���s���B<br/>
	 * <br/>
	 * @param prefCd �s���{��CD
	 * @param housingKindCd �������CD
	 * @return URL
	 */
	protected String makeUrl(String prefCd, String housingKindCd) {
		return makeUrl(prefCd, null, housingKindCd, null, "list");
	}

	/**
	 * URL�ݒ���s���B<br/>
	 * <br/>
	 * @param prefCd �s���{��CD
	 * @param sysHousingCd �V�X�e������CD
	 * @param housingKindCd �������CD
	 * @return URL
	 */
	protected String makeUrl(String prefCd, String sysHousingCd, String housingKindCd) {
		return makeUrl(prefCd, sysHousingCd, housingKindCd, null, "detail");
	}

	/**
	 * URL�ݒ���s���B<br/>
	 * <br/>
	 * @param prefCd �s���{��CD
	 * @param sysHousingCd �V�X�e������CD
	 * @param housingKindCd �������CD
	 * @param sysReformCd �V�X�e�����t�H�[��CD
	 * @return URL
	 */
	protected String makeUrl(String prefCd, String sysHousingCd, String housingKindCd, String sysReformCd) {
		return makeUrl(prefCd, sysHousingCd, housingKindCd, sysReformCd, "detail");
	}

	/**
	 * URL�ݒ���s���B<br/>
	 * <br/>
	 * @param prefCd �s���{��CD
	 * @param sysHousingCd �V�X�e������CD
	 * @param housingKindCd �������CD
	 * @param sysReformCd �V�X�e�����t�H�[��CD
	 * @param mode ���[�h
	 * @return URL
	 */
	protected String makeUrl(String prefCd, String sysHousingCd, String housingKindCd, String sysReformCd, String mode) {
		if (isEmpty(prefCd) || isEmpty(housingKindCd)) {
			return "/";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("/buy/");
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(housingKindCd)) {
			sb.append("mansion/");

		} else if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(housingKindCd)) {
			sb.append("house/");

		} else if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(housingKindCd)) {
			sb.append("ground/");

		}
		sb.append(prefCd);
		sb.append("/");
		sb.append(mode);
		if (!isEmpty(sysHousingCd)) {
			sb.append("/");
			sb.append(sysHousingCd);
		}
		if (!isEmpty(sysReformCd)) {
			sb.append("/");
			sb.append(sysReformCd);
		}
		sb.append("/");
		return sb.toString();
	}

	/**
	 * �摜�p�X�ݒ���s���B<br/>
	 * <br/>
	 * @param permission ����
	 * @param pathName �摜�p�X
	 * @param size �摜�T�C�Y
	 * @param fileName �摜��
	 * @return �摜�p�X
	 */
	protected String getImgPath(String permission, String pathName, String size, String fileName) {
		return getImgPath(permission, pathName, size, fileName, HOUSING_IMG_MODE);
	}

	/**
	 * �摜�p�X�ݒ���s���B<br/>
	 * <br/>
	 * @param permission ����
	 * @param pathName �摜�p�X
	 * @param size �摜�T�C�Y
	 * @param fileName �摜��
	 * @param mode ���[�h
	 * @return �摜�p�X
	 */
	protected String getImgPath(String permission, String pathName, String size, String fileName, String mode) {

		String urlPath = "";

		if (isEmpty(pathName) || isEmpty(fileName)) {
			return urlPath;
		}

		if (HOUSING_IMG_MODE.equals(mode)) {
			if (PanaCommonConstant.ROLE_ID_PUBLIC.equals(permission)) {
				urlPath = getPanaFileUtil().getHousFileOpenUrl(pathName, fileName, size);
			} else if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(permission)) {
				urlPath = getPanaFileUtil().getHousFileMemberUrl(pathName, fileName, size);
			}

		} else if (REFORM_IMG_MODE.equals(mode)) {
			if (PanaCommonConstant.ROLE_ID_PUBLIC.equals(permission)) {
				urlPath = getPanaFileUtil().getHousFileOpenUrl(pathName, fileName, size);
			} else if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(permission)) {
				urlPath = getPanaFileUtil().getHousFileMemberUrl(pathName, fileName, size);
			}
		}

		return urlPath;

	}

	protected boolean isEmpty(Object str) {
		return org.springframework.util.StringUtils.isEmpty(str);
	}

	protected boolean isEmpty(Object[] str) {
		if (str == null) {
			return true;
		}
		long i = 0;
		long j = 0;
		for (Object target : str) {
			i++;
			if (org.springframework.util.StringUtils.isEmpty(target)) {
				j++;
			}
		}
		return i == j;
	}

	protected String defaultString(String str) {
		return str == null ? "" : str;
	}

	protected String defaultString(String str, String defaultStr) {
		return !isEmpty(str) ? str : defaultString(defaultStr);
	}

	protected String defaultString(Integer i) {
		return i == null ? "" : i.toString();
	}

	protected String defaultString(Integer i, String defaultStr) {
		return i == null ? "" : defaultStr;
	}

	protected Integer defaultString(Integer i, Integer defaultI) {
		return i == null ? defaultI : i;
	}

	protected String defaultString(Long l) {
		return l == null ? "" : l.toString();
	}

	protected String defaultString(Long l, String defaultStr) {
		return l == null ? "" : defaultStr;
	}

	protected Long defaultString(Long l, Long defaultL) {
		return l == null ? defaultL : l;
	}

	protected String defaultString(BigDecimal bd) {
		return bd == null ? "" : bd.toString();
	}

	protected String defaultString(BigDecimal bd, String defaultStr) {
		return bd == null ? "" : defaultStr;
	}

	protected BigDecimal defaultString(BigDecimal bd, BigDecimal defaultBd) {
		return bd == null ? defaultBd : bd;
	}

	protected String addString(String str1, String str2) {
		return !isEmpty(str1) && !isEmpty(str2) ? str1 + str2 : "";
	}

	protected BigDecimal divide(BigDecimal bd) {
		return bd == null ? BigDecimal.ZERO : bd.divide(new BigDecimal(10000), RoundingMode.CEILING);
	}

	protected Date dateAdd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 14);
		return calendar.getTime();
	}

	protected String formatPrice(BigDecimal price, boolean divideFlg) {
		if (isEmpty(price)) {
			return "";
		}
		return divideFlg ? new DecimalFormat(",###").format(divide(price)) : new DecimalFormat(",###").format(price);
	}

	protected String formatPrice(Long price, boolean divideFlg) {
		if (isEmpty(price)) {
			return "";
		}
		return divideFlg ? new DecimalFormat(",###").format(divide(new BigDecimal(price))) : new DecimalFormat(",###").format(new BigDecimal(price));
	}

	protected String formatPrice(String price, boolean divideFlg) {
		if (isEmpty(price)) {
			return "";
		}
		return divideFlg ? new DecimalFormat(",###").format(divide(new BigDecimal(price))) : new DecimalFormat(",###").format(new BigDecimal(price));
	}

	protected String sumPrice(Long l1, Long l2) {
		if (l1 == null && l2 == null) {
			return null;
		}
		return new Long((defaultString(l1, 0l) + defaultString(l2, 0l))).toString();
	}

	protected String sumPrice(String s1, String s2) {
		if (isEmpty(s1) && isEmpty(s2)) {
			return null;
		}
		return new Long((new Long(defaultString(s1, "0")) + new Long(defaultString(s2, "0")))).toString();
	}
}
