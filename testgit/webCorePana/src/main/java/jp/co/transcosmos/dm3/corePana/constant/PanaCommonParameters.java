package jp.co.transcosmos.dm3.corePana.constant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;

/**
 * ���ʃp�����[�^���N���X�@�iPanasonic �g���Łj.
 * <p>
 * �A�v���P�[�V�������g�p���鋤�ʃp���[���[�^���Ǘ�����B<br/>
 * <p>
 *
 * <pre>
 * �S����         �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.03.18  �V�K�쐬
 * Thi Tran     2015.10.16  Add new parameters for affiliate
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X����l���擾����ɂ́AcommonParameters �̃I�u�W�F�N�g�� DI �R���e�i����擾���邩�A getInstance()
 * �Ŏ擾�����C���X�^���X���g�p���鎖�B<br/>
 * �܂��A�A�v���P�[�V���������� setter ���g�p���Ēl��ύX���Ȃ����B<br/>
 * <br/>
 * �W���ݒ�ł� JSP �p�ɁAglobal.xml ���烊�N�G�X�g�X�R�[�v�ɂ� commonParameters �� key �Ŋi�[
 * ����Ă���B�@���p�\���́Aglobal.xml �̐ݒ���m�F���鎖�B<br/>
 *
 */
public class PanaCommonParameters extends CommonParameters {

    /** Yahoo Code for your Conversion Page **/
    private String yahooCodeSrc="//b91.yahoo.co.jp/pagead/conversion/1000277438/imp.gif";

	/** Janet URL */
	private String janetUrl = "https://action.j-a-net.jp//";
	
	/** Janet Member registration Id */
	private String janetMemberSid = "404221";
	
	/** Janet Assessment request id */
	private String janetAssessmentSid = "404222";
	
	/** Prefix of inquiry header Id */
	private String inquiryIdPrefix = "A";
	
	/** �����摜�̌��J�t�H���_�i�����p�X�j �������p */
	private String housImgOpenPhysicalMemberPath = "/data/housing/open_member/";

	/** �����摜�̌��J root URL �������p */
	private String housImgMemberUrl = "/img/open_member/";

	/** �o�C�g�������Ɏg�p���镶���R�[�h */
	public static final String BYTE_CHECK_ENCODE = "UTF-8";

	/** ���I�ȉ��URL */
	private String siteURL = "http://localhost:8080/";

	/** ���[���e���v���[�g��fromEmail */
	private String fromEmail = "";

	/** ���[���e���v���[�g��fromEmailName */
	private String fromEmailName = "";

	/** ���[���e���v���[�g��ccEmail */
	private String ccEmail = "";

	/** ���[���e���v���[�g��bccEmail */
	private String bccEmail = "";

	/** ���[���e���v���[�g��replyToEmail */
	private String replyToEmail = "";

	/** ���[���e���v���[�g��errorToEmail */
	private String errorToEmail = "";

	/** Panasonic ReSMAIL������ �Z�� �i���[���{�����Ŏg�p�j */
	private String reSmailZip = "";

	/** �⍇���惁�[���A�h���X�i���[���{�����Ŏg�p�j */
	private String inquiryToEmail = "";

	/** Panasonic ReSMAIL������ �i�r�_�C���� */
	private String reSmailTel = "";

	/** Panasonic ReSMAIL URL */
	private String reSmailUrl = "";

	/** Panasonic ReSMAIL ��t���� */
	private String reSmailUktskJkn = "";

	/** �yPanasonic ReaRie�z */
	private String panasonicReSmail = "�yPanasonic ReaRie�z";

	/** �yPanasonic ReaRie�����ǁz */
	private String panasonicReSmailAffairs = "�yPanasonic ReaRie�����ǁz";

	/** Panasonic �t�@�C���T�[�o�� URL */
	private String fileSiteURL = "";

	/** �ŋߌ��������o�^������� */
	private int maxRecentlyInfoCnt;

	/** ���C�ɓ�����o�^������� */
	private int maxFavoriteInfoCnt;

	/** �t�����g���摜�t�@�C���ACSS �Ȃǂ́A�ÓI�t�@�C���̃��[�g�p�X */
	private String frtResourceRootUrl = "/front_rsc/";

	/** �t�����g���f�t�H���gkeyword */
	private String defaultKeyword;

	/** �t�����g���f�f�t�H���gdescription */
	private String defaultDescription;

	/** �p�i�\�j�b�N���X�}�C���F�^�C�g���p */
	private String panaReSmail = "�p�i�\�j�b�N ReaRie(���A���G)";

	/** �����ꗗ�A���C�ɓ��蕨���ꗗ�A�ŋߌ��������ꗗ�̉摜�T�C�Y */
	private String housingListImageSize = "200";

	/** �����ڍׂ̕�����񍶑��̑傫���摜(���t�H�[����A���t�H�[���L��̉ӏ������l)�摜�T�C�Y */
	private String housingDtlImageLargeSize = "800";
	/** �������E���̏������摜(���t�H�[����A���t�H�[���L��̉ӏ������l)�摜�T�C�Y */
	private String housingDtlImageSmallSize = "200";
	/** �����ڍׂ̒S���҂̉摜�摜�T�C�Y */
	private String housingDtlStaffImageSize = "120";
	/** �����ڍׂ̍ŋߌ��������̊O�ω摜�摜�T�C�Y */
	private String housingDtlHistoryImageSize = "86";

	/** �������₢���킹�̊O�ω摜�摜�T�C�Y */
	private String housingInquiryImageSize = "200";

	/** �}�C�y�[�W(���O�C����)�̂��C�ɓ��蕨���O�ω摜�T�C�Y */
	private String mypageFavoriteImageSize = "86";
	/** �}�C�y�[�W(���O�C����)�̍ŋߌ��������̊O�ω摜�T�C�Y */
	private String mypageHistoryImageSize = "86";

	/** �Ǘ��T�C�g��ʕ\���摜�T�C�Y */
	private String adminSiteSmallImageSize = "86";
	/** �Ǘ��T�C�g�|�b�v�A�b�v��ʕ\���摜�T�C�Y */
	private String adminSiteLargeImageSize = "800";
	/** �Ǘ��T�C�g��ʕ\���p�S���Ҏʐ^�T�C�Y */
	private String adminSiteStaffImageSize = "120";
	/** �Ǘ��T�C�gPDF�T�u�t�H���_�[�� */
	private String adminSitePdfFolder = "pdf";
	/** �Ǘ��T�C�g�S���Ҏʐ^�T�u�t�H���_�[�� */
	private String adminSiteStaffFolder = "staff";
	/** �Ǘ��T�C�g���[�_�`���b�g�T�u�t�H���_�[�� */
	private String adminSiteChartFolder = "chart";
	/** �Ǘ��T�C�g�C���[�W���t�@�C���̃T�u�t�H���_�[�� */
	private String adminSiteFullFolder = "full";

	/** �摜�t�@�C���ACSS �Ȃǂ́A�ÓI�t�@�C���̃��[�g�p�X admin����front�����ʗp */
	private String commonResourceRootUrl = "";

	/**�p�i�\�j�b�N�T�C�g���p�� */
	private String panasonicSiteEnglish = "ReaRie";

	/**�p�i�\�j�b�N�T�C�g�J�^�J�i */
	private String panasonicSiteJapan = "���A���G";

	/** �}�C�y�[�W���O�C�� URL�@�i���[���{�����Ŏg�p�j **/
	private String mypageTopUrl = "";

	/** �摜�Ȃ��̏ꍇ�̕\���摜86�T�C�Y **/
	private String noPhoto86 = "buy/img/nophoto/nophoto_86.jpg";

	/** �摜�Ȃ��̏ꍇ�̕\���摜110�T�C�Y **/
	private String noPhoto110 = "buy/img/nophoto/nophoto_110.jpg";

	/** �摜�Ȃ��̏ꍇ�̕\���摜200�T�C�Y **/
	private String noPhoto200 = "buy/img/nophoto/nophoto_200.jpg";

	/** �摜�Ȃ��̏ꍇ�̕\���摜800�T�C�Y **/
	private String noPhoto800 = "buy/img/nophoto/nophoto_800.jpg";


	public String getNoPhoto86() {
		return noPhoto86;
	}
	public void setNoPhoto86(String noPhoto86) {
		this.noPhoto86 = noPhoto86;
	}
	public String getNoPhoto110() {
		return noPhoto110;
	}
	public void setNoPhoto110(String noPhoto110) {
		this.noPhoto110 = noPhoto110;
	}
	public String getNoPhoto200() {
		return noPhoto200;
	}
	public void setNoPhoto200(String noPhoto200) {
		this.noPhoto200 = noPhoto200;
	}
	public String getNoPhoto800() {
		return noPhoto800;
	}
	public void setNoPhoto800(String noPhoto800) {
		this.noPhoto800 = noPhoto800;
	}
	/**
	 * �}�C�y�[�W���O�C�� URL ���擾����B<br/>
	 * <br/>
	 * @return �}�C�y�[�W���O�C�� URL
	 */
	public String getMypageTopUrl() {
		return mypageTopUrl;
	}

	/**
	 * �}�C�y�[�W���O�C�� URL ��ݒ肷��B<br/>
	 * <br/>
	 * @param adminLoginUrl�}�C�y�[�W���O�C�� URL
	 */
	public void setMypageTopUrl(String mypageTopUrl) {
		this.mypageTopUrl = mypageTopUrl;
	}

	/**
	 * �����摜�̌��J�t�H���_�i�����p�X�j ���擾����B�@�i�������p�j<br/>
	 * <br/>
	 *
	 * @return �����摜�̌��J�t�H���_�i�����p�X�j �E�������p
	 */
	public String getHousImgOpenPhysicalMemberPath() {
		return housImgOpenPhysicalMemberPath;
	}

	/**
	 * �����摜�̌��J�t�H���_�i�����p�X�j ��ݒ肷��B�@�i�������p�j<br/>
	 * <br/>
	 *
	 * @param housImgOpenPhysicalMemberPath
	 *            �����摜�̌��J�t�H���_�i�����p�X�j�E�������p
	 */
	public void setHousImgOpenPhysicalMemberPath(
			String housImgOpenPhysicalMemberPath) {
		this.housImgOpenPhysicalMemberPath = housImgOpenPhysicalMemberPath;
	}

	/**
	 * �t�����g���摜�t�@�C���ACSS �Ȃǂ́A�ÓI�t�@�C���̃��[�g�p�X���擾����B<br/>
	 * <br/>
	 *
	 * @return �t�����g���摜�t�@�C���ACSS �Ȃǂ́A�ÓI�t�@�C���̃��[�g�p�X
	 */
	public String getFrtResourceRootUrl() {
		return frtResourceRootUrl;
	}

	/**
	 * �t�����g���摜�t�@�C���ACSS �Ȃǂ́A�ÓI�t�@�C���̃��[�g�p�X��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param frtResourceRootUrl
	 *            �t�����g���摜�t�@�C���ACSS �Ȃǂ́A�ÓI�t�@�C���̃��[�g�p�X
	 */
	public void setFrtResourceRootUrl(String frtResourceRootUrl) {
		this.frtResourceRootUrl = frtResourceRootUrl;
	}


	public void setSiteURL(String siteURL) {
		this.siteURL = siteURL;
	}

	public String getSiteURL() {
		return siteURL;
	}

	/**
	 * fromEmail���擾����B<br/>
	 *
	 * @return fromEmail
	 */
	public String getFromEmail() {
		return fromEmail;
	}

	/**
	 * @param fromEmail
	 *            �Z�b�g���� fromEmail
	 */
	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	/**
	 * fromEmailName���擾����B<br/>
	 *
	 * @return errorToEmail
	 */
	public String getFromEmailName() {
		return fromEmailName;
	}

	/**
	 * @param fromEmailName
	 *            �Z�b�g���� fromEmailName
	 */
	public void setFromEmailName(String fromEmailName) {
		this.fromEmailName = fromEmailName;
	}

	/**
	 * ccEmail���擾����B<br/>
	 *
	 * @return errorToEmail
	 */
	public String getCcEmail() {
		return ccEmail;
	}

	/**
	 * @param ccEmail
	 *            �Z�b�g���� ccEmail
	 */
	public void setCcEmail(String ccEmail) {
		this.ccEmail = ccEmail;
	}

	/**
	 * replyToEmail���擾����B<br/>
	 *
	 * @return errorToEmail
	 */
	public String getReplyToEmail() {
		return replyToEmail;
	}

	/**
	 * @param replyToEmail
	 *            �Z�b�g���� replyToEmail
	 */
	public void setReplyToEmail(String replyToEmail) {
		this.replyToEmail = replyToEmail;
	}

	/**
	 * errorToEmail���擾����B<br/>
	 *
	 * @return errorToEmail
	 */
	public String getErrorToEmail() {
		return errorToEmail;
	}

	/**
	 * @param errorToEmail
	 *            �Z�b�g���� errorToEmail
	 */
	public void setErrorToEmail(String errorToEmail) {
		this.errorToEmail = errorToEmail;
	}

	/**
	 * ReSMAIL������ �Z�����擾����B<br/>
	 * <br/>
	 *
	 * @return ReSMAIL������ �Z��
	 */
	public String getReSmailZip() {
		return reSmailZip;
	}

	/**
	 * ReSMAIL������ �Z����ݒ肷��B<br/>
	 * <br/>
	 */
	public void setReSmailZip(String reSmailZip) {
		this.reSmailZip = reSmailZip;
	}

	/**
	 * �⍇���惁�[���A�h���X���擾����B<br/>
	 * <br/>
	 *
	 * @return �⍇���惁�[���A�h���X
	 */
	public String getInquiryToEmail() {
		return inquiryToEmail;
	}

	/**
	 * �⍇���惁�[���A�h���X��ݒ肷��B<br/>
	 * <br/>
	 */
	public void setInquiryToEmail(String inquiryToEmail) {
		this.inquiryToEmail = inquiryToEmail;
	}

	/**
	 * Panasonic ReSMAIL �i�r�_�C�������擾����B<br/>
	 * <br/>
	 *
	 * @return Panasonic ReSMAIL �i�r�_�C����
	 */
	public String getReSmailTel() {
		return reSmailTel;
	}

	/**
	 * Panasonic ReSMAIL �i�r�_�C������ݒ肷��B<br/>
	 * <br/>
	 */
	public void setReSmailTel(String reSmailTel) {
		this.reSmailTel = reSmailTel;
	}

	/**
	 * Panasonic ReSMAIL URL���擾����B<br/>
	 * <br/>
	 *
	 * @return Panasonic ReSMAIL URL
	 */
	public String getReSmailUrl() {
		return reSmailUrl;
	}

	/**
	 * Panasonic ReSMAIL URL��ݒ肷��B<br/>
	 * <br/>
	 */
	public void setReSmailUrl(String reSmailUrl) {
		this.reSmailUrl = reSmailUrl;
	}

	/**
	 * @return reSmailUktskJkn
	 */
	public String getReSmailUktskJkn() {
		return reSmailUktskJkn;
	}

	/**
	 * @param reSmailUktskJkn
	 *            �Z�b�g���� reSmailUktskJkn
	 */
	public void setReSmailUktskJkn(String reSmailUktskJkn) {
		this.reSmailUktskJkn = reSmailUktskJkn;
	}

	public String getPanasonicReSmail() {
		return panasonicReSmail;
	}

	public void setPanasonicReSmail(String panasonicReSmail) {
		this.panasonicReSmail = panasonicReSmail;
	}

	public String getPanasonicSiteEnglish() {
		return panasonicSiteEnglish;
	}

	public void setPanasonicSiteEnglish(String panasonicSiteEnglish) {
		this.panasonicSiteEnglish = panasonicSiteEnglish;
	}

	public String getPanasonicSiteJapan() {
		return panasonicSiteJapan;
	}

	public void setPanasonicSiteJapan(String panasonicSiteJapan) {
		this.panasonicSiteJapan = panasonicSiteJapan;
	}

	public String getPanasonicReSmailAffairs() {
		return panasonicReSmailAffairs;
	}

	public void setPanasonicReSmailAffairs(String panasonicReSmailAffairs) {
		this.panasonicReSmailAffairs = panasonicReSmailAffairs;
	}

	/**
	 * @return bccEmail
	 */
	public String getBccEmail() {
		return bccEmail;
	}

	/**
	 * @param bccEmail
	 *            �Z�b�g���� bccEmail
	 */
	public void setBccEmail(String bccEmail) {
		this.bccEmail = bccEmail;
	}

	/**
	 * @return �t�@�C���T�[�o�� URL
	 */
	public String getFileSiteURL() {
		return fileSiteURL;
	}

	/**
	 * @param �t�@�C���T�[�o��
	 *            URL �Z�b�g����
	 */
	public void setFileSiteURL(String fileSiteURL) {
		this.fileSiteURL = fileSiteURL;
	}

	/**
	 * �ŋߌ��������o�^����������擾����B<br/>
	 * <br/>
	 *
	 * @return �ŋߌ��������o�^�������
	 */
	public int getMaxRecentlyInfoCnt() {
		return maxRecentlyInfoCnt;
	}

	/**
	 * �ŋߌ��������o�^���������ݒ肷��B<br/>
	 * <br/>
	 */
	public void setMaxRecentlyInfoCnt(int recentlyInfoMax) {
		this.maxRecentlyInfoCnt = recentlyInfoMax;
	}

	/**
	 * ���C�ɓ�����o�^����������擾����B<br/>
	 * <br/>
	 *
	 * @return ���C�ɓ�����o�^�������
	 */
	public int getMaxFavoriteInfoCnt() {
		return maxFavoriteInfoCnt;
	}

	/**
	 * ���C�ɓ�����o�^���������ݒ肷��B<br/>
	 * <br/>
	 */
	public void setMaxFavoriteInfoCnt(int favoriteInfoMax) {
		this.maxFavoriteInfoCnt = favoriteInfoMax;
	}

	/** �t�����g���f�t�H���gkeyword */
	public String getDefaultKeyword() {
		return defaultKeyword;
	}

	/** �t�����g���f�t�H���gkeyword */
	public void setDefaultKeyword(String defaultKeyword) {
		this.defaultKeyword = defaultKeyword;
	}

	/** �t�����g���f�f�t�H���gdescription */
	public String getDefaultDescription() {
		return defaultDescription;
	}

	/** �t�����g���f�f�t�H���gdescription */
	public void setDefaultDescription(String defaultDescription) {
		this.defaultDescription = defaultDescription;
	}

	/** �p�i�\�j�b�N���X�}�C�� */
	public String getPanaReSmail() {
		return panaReSmail;
	}

	/** �p�i�\�j�b�N���X�}�C�� */
	public void setPanaReSmail(String panaReSmail) {
		this.panaReSmail = panaReSmail;
	}

	public String getHousingListImageSize() {
		return housingListImageSize;
	}

	public void setHousingListImageSize(String housingListImageSize) {
		this.housingListImageSize = housingListImageSize;
	}

	public String getHousingDtlImageLargeSize() {
		return housingDtlImageLargeSize;
	}

	public void setHousingDtlImageLargeSize(String housingDtlImageLargeSize) {
		this.housingDtlImageLargeSize = housingDtlImageLargeSize;
	}

	public String getHousingDtlImageSmallSize() {
		return housingDtlImageSmallSize;
	}

	public void setHousingDtlImageSmallSize(String housingDtlImageSmallSize) {
		this.housingDtlImageSmallSize = housingDtlImageSmallSize;
	}

	public String getHousingDtlStaffImageSize() {
		return housingDtlStaffImageSize;
	}

	public void setHousingDtlStaffImageSize(String housingDtlStaffImageSize) {
		this.housingDtlStaffImageSize = housingDtlStaffImageSize;
	}

	public String getHousingDtlHistoryImageSize() {
		return housingDtlHistoryImageSize;
	}

	public void setHousingDtlHistoryImageSize(String housingDtlHistoryImageSize) {
		this.housingDtlHistoryImageSize = housingDtlHistoryImageSize;
	}

	public String getHousingInquiryImageSize() {
		return housingInquiryImageSize;
	}

	public void setHousingInquiryImageSize(String housingInquiryImageSize) {
		this.housingInquiryImageSize = housingInquiryImageSize;
	}

	public String getMypageFavoriteImageSize() {
		return mypageFavoriteImageSize;
	}

	public void setMypageFavoriteImageSize(String mypageFavoriteImageSize) {
		this.mypageFavoriteImageSize = mypageFavoriteImageSize;
	}

	public String getMypageHistoryImageSize() {
		return mypageHistoryImageSize;
	}

	public void setMypageHistoryImageSize(String mypageHistoryImageSize) {
		this.mypageHistoryImageSize = mypageHistoryImageSize;
	}

	public String getHousImgMemberUrl() {
		return housImgMemberUrl;
	}

	public void setHousImgMemberUrl(String housImgMemberUrl) {
		this.housImgMemberUrl = housImgMemberUrl;
	}

	public String getAdminSiteSmallImageSize() {
		return adminSiteSmallImageSize;
	}

	public void setAdminSiteSmallImageSize(String adminSiteSmallImageSize) {
		this.adminSiteSmallImageSize = adminSiteSmallImageSize;
	}

	public String getAdminSiteLargeImageSize() {
		return adminSiteLargeImageSize;
	}

	public void setAdminSiteLargeImageSize(String adminSiteLargeImageSize) {
		this.adminSiteLargeImageSize = adminSiteLargeImageSize;
	}

	public String getAdminSiteStaffImageSize() {
		return adminSiteStaffImageSize;
	}

	public void setAdminSiteStaffImageSize(String adminSiteStaffImageSize) {
		this.adminSiteStaffImageSize = adminSiteStaffImageSize;
	}

	public String getAdminSitePdfFolder() {
		return adminSitePdfFolder;
	}

	public void setAdminSitePdfFolder(String adminSitePdfFolder) {
		this.adminSitePdfFolder = adminSitePdfFolder;
	}

	public String getAdminSiteStaffFolder() {
		return adminSiteStaffFolder;
	}

	public void setAdminSiteStaffFolder(String adminSiteStaffFolder) {
		this.adminSiteStaffFolder = adminSiteStaffFolder;
	}

	public String getAdminSiteChartFolder() {
		return adminSiteChartFolder;
	}

	public void setAdminSiteChartFolder(String adminSiteChartFolder) {
		this.adminSiteChartFolder = adminSiteChartFolder;
	}

	public String getAdminSiteFullFolder() {
		return adminSiteFullFolder;
	}

	public void setAdminSiteFullFolder(String adminSiteFullFolder) {
		this.adminSiteFullFolder = adminSiteFullFolder;
	}

	/**
	 * �摜�t�@�C���ACSS �Ȃǂ́A�ÓI�t�@�C���̃��[�g�p�X admin����front�����ʗp��ݒ肷��B<br/>
	 * <br/>
	 */
	public String getCommonResourceRootUrl() {
		return commonResourceRootUrl;
	}

	/**
	 * �摜�t�@�C���ACSS �Ȃǂ́A�ÓI�t�@�C���̃��[�g�p�X admin����front�����ʗp���擾����B<br/>
	 * <br/>
	 *
	 * @return �摜�t�@�C���ACSS �Ȃǂ́A�ÓI�t�@�C���̃��[�g�p�X admin����front�����ʗp
	 */
	public void setCommonResourceRootUrl(String commonResourceRootUrl) {
		this.commonResourceRootUrl = commonResourceRootUrl;
	}
	/**
     * Get yahoo code source
     * @return
     */
    public String getYahooCodeSrc() {
        return yahooCodeSrc;
    }
    /**
     * Set yahoo code source
     * @param yahooCodeSrc
     */
    public void setYahooCodeSrc(String yahooCodeSrc) {
        this.yahooCodeSrc = yahooCodeSrc;
    }
    /**
	 * Get janet URL
     * @return the janetUrl
     */
    public String getJanetUrl() {
        return janetUrl;
    }
    /**
     * Set janet URL
     * @param janetUrl the janetUrl to set
     */
    public void setJanetUrl(String janetUrl) {
        this.janetUrl = janetUrl;
    }
    /**
     * Get janet ID for registration member
     * @return the janetMemberSid
     */
    public String getJanetMemberSid() {
        return janetMemberSid;
    }
    /**
     * Set janet ID for registration member
     * @param janetMemberSid the janetMemberSid to set
     */
    public void setJanetMemberSid(String janetMemberSid) {
        this.janetMemberSid = janetMemberSid;
    }
    /**
     * Get janet ID for creating assessment inquiry
     * @return the janetAssessmentSid
     */
    public String getJanetAssessmentSid() {
        return janetAssessmentSid;
    }
    /**
     * Set janet ID for creating assessment inquiry
     * @param janetAssessmentSid the janetAssessmentSid to set
     */
    public void setJanetAssessmentSid(String janetAssessmentSid) {
        this.janetAssessmentSid = janetAssessmentSid;
    }
    /**
     * Get prefix of assessment inquiry ID
     * @return the inquiryIdPrefix
     */
    public String getInquiryIdPrefix() {
        return inquiryIdPrefix;
    }
    /**
     * Set prefix of assessment inquiry ID
     * @param inquiryIdPrefix the inquiryIdPrefix to set
     */
    public void setInquiryIdPrefix(String inquiryIdPrefix) {
        this.inquiryIdPrefix = inquiryIdPrefix;
    }
    /**
	 * PanaCommonParameters �̃C���X�^���X���擾����B<br/>
	 * Spring �̃R���e�L�X�g����A PanaCommonParameters �Œ�`���ꂽ PanaCommonParameters �̃C���X�^���X���擾����B<br/>
	 * �擾�����C���X�^���X�́APanaCommonParameters ���p�������g���N���X�����A�����ꍇ������B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return PanaCommonParameters�A�܂��͌p�����Ċg�������N���X�̃C���X�^���X
	 */
	public static PanaCommonParameters getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (PanaCommonParameters)springContext.getBean(COMMON_PARAMETERS_BEAN_ID);
	}
}
