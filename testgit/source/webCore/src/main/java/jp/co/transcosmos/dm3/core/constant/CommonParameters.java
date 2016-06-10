package jp.co.transcosmos.dm3.core.constant;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * ���ʃp�����[�^���N���X.
 * <p>
 * �A�v���P�[�V�������g�p���鋤�ʃp���[���[�^���Ǘ�����B<br/>
 * <p>
 * <pre>
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.29	Shamaison ���Q�l�ɐV�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X����l���擾����ɂ́AcommonParameters �̃I�u�W�F�N�g�� DI �R���e�i����擾���邩�A
 * getInstance() �Ŏ擾�����C���X�^���X���g�p���鎖�B<br/>
 * �܂��A�A�v���P�[�V���������� setter ���g�p���Ēl��ύX���Ȃ����B<br/>
 * <br/>
 * �W���ݒ�ł� JSP �p�ɁAglobal.xml ���烊�N�G�X�g�X�R�[�v�ɂ� commonParameters �� key �Ŋi�[
 * ����Ă���B�@���p�\���́Aglobal.xml �̐ݒ���m�F���鎖�B<br/>
 * 
 */
public class CommonParameters {

	/** CommonParameters �� Bean ID */
	protected static final String COMMON_PARAMETERS_BEAN_ID = "commonParameters";

	/** �摜�t�@�C���ACSS �Ȃǂ́A�ÓI�t�@�C���̃��[�g�p�X */
	private String resourceRootUrl = "/admin_rsc/";

	/**�@�Ǘ����[�U�[�p�X���[�h�œ��͋�����锼�p�L������ */
	private String adminPwdUseMarks = "-=+_*";

	/**�@�Ǘ����[�U�[�p�X���[�h�łǂꂩ�ЂƂ͕K�{�ƂȂ锼�p�L������ */
	private String adminPwdMastMarks = "";

	/** �Ǘ����[�U�[�̃��O�C���h�c�A�p�X���[�h�̍ŏ����� */
	private int minAdminPwdLength = 8;
	
	/**�@�}�C�y�[�W���[�U�[�p�X���[�h�œ��͋�����锼�p�L������ */
	private String mypagePwdUseMarks = "-=+_*";

	/**�@�}�C�y�[�W���[�U�[�p�X���[�h�łǂꂩ�ЂƂ͕K�{�ƂȂ锼�p�L������ */
	private String mypagePwdMastMarks = "";

	/** �}�C�y�[�W���[�U�[�p�X���[�h�̍ŏ����� */
	private int minMypagePwdLength = 8;
	
	/** �Ǘ��y�[�W�^�C�g�� **/
	private String adminPageTitle = "";

	/** �Ǘ��y�[�W���O�C�� URL�@�i���[���{�����Ŏg�p�j **/
	private String adminLoginUrl = "";

	/** �}�C�y�[�W���O�C�� URL�@�i���[���{�����Ŏg�p�j **/
	private String mypageLoginUrl = "";

	/** �������N�G�X�g�o�^������� */
	private int maxHousingRequestCnt = 5;

	// note
	// �����A�����摜�p�̃v���p�e�B���ǉ������\��B

	/** �����摜�̉� root �t�H���_�i�����p�X�j */
	private String housImgTempPhysicalPath = "/data/img/temp/";

	/** �����摜�̌��J root �t�H���_�i�����p�X�j */
	private String housImgOpenPhysicalPath = "/data/img/open/";

	/** �����摜�̉� root URL */
	private String housImgTempUrl = "/img/temp/";

	/** �����摜�̌��J root URL */
	private String housImgOpenUrl = "/img/open/";

	/** Commons Upload ���g�p���郏�[�N�t�H���_ */
	private String uploadWorkPath = "/data/work/";

	/** Cookie Domain�@*/
	private String cookieDomain;

	/** �T���l�C���摜�t�@�C���̃T�C�Y���X�g */
	private List<Integer> thumbnailSizes; 

	/**
	 * RequestScopeDataSource �� Bean ID ��<br/>
	 * �蓮�Ńg�����U�N�V�����𐧌䂷��ꍇ�A���̃v���p�e�B�ɐݒ肳��Ă��� Bean ID ��
	 * �������Ă���B<br/>
	 */
	protected String requestScopeDataSourceId = "requestScopeDS";

	/**
	 *  �Ǘ����[�U�[�e�[�u���� Aliase ��<br/>
	 *  �ʏ�A���̃v���p�e�B�l��ύX���鎖�͂Ȃ��B<br/>
	 *  �F�؂Ɏg�p���� DAO ��ύX�����ꍇ�A�e�[�u���� Alias ����ύX����K�v������������
	 *  �ꍇ�A�K�v�ɉ����Đݒ�l��ύX����B<br/>
	 *  �i�e�����o�Ȃ��悤�� JoinDAO ���C������̂��]�܂����B�j
	 */
	private String adminUserDbAlias = "adminLoginInfo";

	/**
	 * �Ǘ����[�U�[�����e�[�u���� Role ��<br/>
	 *  �ʏ�A���̃v���p�e�B�l��ύX���鎖�͂Ȃ��B<br/>
	 *  �F�؂Ɏg�p���� DAO ��ύX�����ꍇ�A�e�[�u���� Alias ����ύX����K�v������������
	 *  �ꍇ�A�K�v�ɉ����Đݒ�l��ύX����B<br/>
	 *  �i�e�����o�Ȃ��悤�� JoinDAO ���C������̂��]�܂����B�j
	 */
	private String adminRoleDbAlias = "adminRoleInfo";

	/**
	 *  �}�C�y�[�W����e�[�u���� Aliase ��<br/>
	 *  �ʏ�A���̃v���p�e�B�l��ύX���鎖�͂Ȃ��B<br/>
	 *  �F�؂Ɏg�p���� DAO ��ύX�����ꍇ�A�e�[�u���� Alias ����ύX����K�v������������
	 *  �ꍇ�A�K�v�ɉ����Đݒ�l��ύX����B<br/>
	 *  �i�e�����o�Ȃ��悤�� JoinDAO ���C������̂��]�܂����B�j
	 */
	private String memberDbAlias = "memberInfo";

	
	
	/**
	 * CommonParameters �̃C���X�^���X���擾����B<br/>
	 * Spring �̃R���e�L�X�g����A commonParameters �Œ�`���ꂽ CommonParameters �̃C���X�^���X���擾����B<br/>
	 * �擾�����C���X�^���X�́ACommonParameters ���p�������g���N���X�����A�����ꍇ������B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return CommonParameters�A�܂��͌p�����Ċg�������N���X�̃C���X�^���X
	 */
	public static CommonParameters getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (CommonParameters)springContext.getBean(COMMON_PARAMETERS_BEAN_ID);
	}

	/**
	 * �摜�t�@�C���ACSS �Ȃǂ́A�ÓI�t�@�C���̃��[�g�p�X���擾����B<br/>
	 * <br/>
	 * @return �ÓI�t�@�C���̃��[�g�p�X
	 */
	public String getResourceRootUrl() {
		return resourceRootUrl;
	}

	/**
	 * �摜�t�@�C���ACSS �Ȃǂ́A�ÓI�t�@�C���̃��[�g�p�X��ݒ肷��B<br/>
	 * <br/>
	 * @param resourceRootUrl �ÓI�t�@�C���̃��[�g�p�X
	 */
	public void setResourceRootUrl(String resourceRootUrl) {
		this.resourceRootUrl = resourceRootUrl;
	}

	/**
	 * �Ǘ��҃p�X���[�h�ŗ��p�\�ȋL���������擾����B<br/>
	 * <br/>
	 * @return �Ǘ��҃p�X���[�h�ŗ��p�\�ȋL������
	 */
	public String getAdminPwdUseMarks() {
		return adminPwdUseMarks;
	}

	/**
	 * �Ǘ��҃p�X���[�h�ŗ��p�\�ȋL��������ݒ肷��B<br/>
	 * <br/>
	 * @param adminPwdUseMarks �Ǘ��҃p�X���[�h�ŗ��p�\�ȋL������
	 */
	public void setAdminPwdUseMarks(String adminPwdUseMarks) {
		this.adminPwdUseMarks = adminPwdUseMarks;
	}

	/**
	 * �Ǘ��҃p�X���[�h�łǂꂩ�ЂƂ͎g�p���Ȃ���΂Ȃ�Ȃ��L���������擾����B<br/>
	 * <br/>
	 * @return adminPwdUseMarks �Ǘ��҃p�X���[�h�ŗ��p�\�ȋL������
	 */
	public String getAdminPwdMastMarks() {
		return adminPwdMastMarks;
	}

	/**
	 * �Ǘ��҃p�X���[�h�łǂꂩ�ЂƂ͎g�p���Ȃ���΂Ȃ�Ȃ��L��������ݒ肷��B<br/>
	 * <br/>
	 * @param adminPwdMastMarks adminPwdUseMarks �Ǘ��҃p�X���[�h�ŗ��p�\�ȋL������
	 */
	public void setAdminPwdMastMarks(String adminPwdMastMarks) {
		this.adminPwdMastMarks = adminPwdMastMarks;
	}

	/**
	 * �Ǘ��҃��O�C���h�c�A�p�X���[�h�̍ŏ��������擾����B<br/>
	 * <br/>
	 * @return �Ǘ��҃��O�C���h�c�A�p�X���[�h�̍ŏ�����
	 */
	public int getMinAdminPwdLength() {
		return minAdminPwdLength;
	}

	/**
	 * �Ǘ��҃��O�C���h�c�A�p�X���[�h�̍ő包����ݒ肷��B<br/>
	 * <br/>
	 * @param minAdminPwdLength �Ǘ��҃��O�C���h�c�C�p�X���[�h�̍ŏ������@�i�f�t�H���g 8�j
	 */
	public void setMinAdminPwdLength(int minAdminPwdLength) {
		this.minAdminPwdLength = minAdminPwdLength;
	}

	/**
	 * �}�C�y�[�W�p�X���[�h�Ŏg�p�\�ȋL���������擾����B<br/>
	 * <br/>
	 * @return �}�C�y�[�W�p�X���[�h�Ŏg�p�\�ȋL������
	 */
	public String getMypagePwdUseMarks() {
		return mypagePwdUseMarks;
	}

	/**
	 * �}�C�y�[�W�p�X���[�h�Ŏg�p�\�ȋL��������ݒ肷��B<br/>
	 * <br/>
	 * @param mypagePwdUseMarks �}�C�y�[�W�p�X���[�h�Ŏg�p�\�ȋL������
	 */
	public void setMypagePwdUseMarks(String mypagePwdUseMarks) {
		this.mypagePwdUseMarks = mypagePwdUseMarks;
	}

	/**
	 * �}�C�y�[�W�p�X���[�h�łǂꂩ�ЂƂ͎g�p���Ȃ���΂Ȃ�Ȃ��L���������擾����B<br/>
	 * <br/>
	 * @return �}�C�y�[�W�p�X���[�h�łǂꂩ�ЂƂ͎g�p���Ȃ���΂Ȃ�Ȃ��L������
	 */
	public String getMypagePwdMastMarks() {
		return mypagePwdMastMarks;
	}

	/**
	 * �}�C�y�[�W�p�X���[�h�łǂꂩ�ЂƂ͎g�p���Ȃ���΂Ȃ�Ȃ��L��������ݒ肷��B<br/>
	 * <br/>
	 * @param mypagePwdMastMarks �}�C�y�[�W�p�X���[�h�łǂꂩ�ЂƂ͎g�p���Ȃ���΂Ȃ�Ȃ��L������
	 */
	public void setMypagePwdMastMarks(String mypagePwdMastMarks) {
		this.mypagePwdMastMarks = mypagePwdMastMarks;
	}

	/**
	 * �}�C�y�[�W�p�X���[�h�̍ŏ��������擾����B<br/>
	 * <br/>
	 * @return �}�C�y�[�W�p�X���[�h�̍ŏ�����
	 */
	public int getMinMypagePwdLength() {
		return minMypagePwdLength;
	}

	/**
	 * �}�C�y�[�W�p�X���[�h�̍ŏ�������ݒ肷��B<br/>
	 * <br/>
	 * @param minMypagePwdLength �}�C�y�[�W�p�X���[�h�̍ŏ����� �i�f�t�H���g 8 �j
	 */
	public void setMinMypagePwdLength(int minMypagePwdLength) {
		this.minMypagePwdLength = minMypagePwdLength;
	}

	/**
	 * �Ǘ��y�[�W�^�C�g�����擾����B<br/>
	 * <br/>
	 * @return �Ǘ��y�[�W�^�C�g��
	 */
	public String getAdminPageTitle() {
		return adminPageTitle;
	}

	/**
	 * �Ǘ��y�[�W�^�C�g����ݒ肷��B<br/>
	 * <br/>
	 * @param adminPageTitle �Ǘ��y�[�W�^�C�g��
	 */
	public void setAdminPageTitle(String adminPageTitle) {
		this.adminPageTitle = adminPageTitle;
	}

	/**
	 * �Ǘ��y�[�W���O�C�� URL ���擾����B<br/>
	 * <br/>
	 * @return �Ǘ��y�[�W���O�C�� URL
	 */
	public String getAdminLoginUrl() {
		return adminLoginUrl;
	}

	/**
	 * �Ǘ��y�[�W���O�C�� URL ��ݒ肷��B<br/>
	 * <br/>
	 * @param adminLoginUrl �Ǘ��y�[�W���O�C�� URL
	 */
	public void setAdminLoginUrl(String adminLoginUrl) {
		this.adminLoginUrl = adminLoginUrl;
	}

	/**
	 * �}�C�y�[�W���O�C�� URL ���擾����B<br/>
	 * <br/>
	 * @return �}�C�y�[�W���O�C�� URL
	 */
	public String getMypageLoginUrl() {
		return mypageLoginUrl;
	}

	/**
	 * �}�C�y�[�W���O�C�� URL ��ݒ肷��B<br/>
	 * <br/>
	 * @param adminLoginUrl�}�C�y�[�W���O�C�� URL
	 */
	public void setMypageLoginUrl(String mypageLoginUrl) {
		this.mypageLoginUrl = mypageLoginUrl;
	}

	/**
	 * �������N�G�X�g�o�^������� ���擾����B<br/>
	 * <br/>
	 * @return �������N�G�X�g�o�^�������
	 */
	public int getMaxHousingRequestCnt() {
		return maxHousingRequestCnt;
	}

	/**
	 * �������N�G�X�g�o�^������� ��ݒ肷��B<br/>
	 * <br/>
	 * @param maxHousingRequestCnt �������N�G�X�g�o�^�������
	 */
	public void setMaxHousingRequestCnt(int maxHousingRequestCnt) {
		this.maxHousingRequestCnt = maxHousingRequestCnt;
	}

	/**
	 * �����摜�̉� root �t�H���_�i�����p�X�j���擾����B<br/>
	 * <br/>
	 * @return �����摜�̉� root �t�H���_�i�����p�X�j
	 */
	public String getHousImgTempPhysicalPath() {
		return housImgTempPhysicalPath;
	}

	/**
	 * �����摜�̉� root �t�H���_�i�����p�X�j��ݒ肷��B<br/>
	 * <br/>
	 * @param housImgTempPhysicalPath �����摜�̉� root �t�H���_�i�����p�X�j
	 */
	public void setHousImgTempPhysicalPath(String housImgTempPhysicalPath) {
		this.housImgTempPhysicalPath = housImgTempPhysicalPath;
	}

	/**
	 * �����摜�̌��J root �t�H���_�i�����p�X�j���擾����B<br/>
	 * <br/>
	 * @return �����摜�̌��J root �t�H���_�i�����p�X�j
	 */
	public String getHousImgOpenPhysicalPath() {
		return housImgOpenPhysicalPath;
	}

	/**
	 * �����摜�̌��J root �t�H���_�i�����p�X�j��ݒ肷��B<br/>
	 * <br/>
	 * @param housImgOpenPhysicalPath �����摜�̌��J root �t�H���_�i�����p�X�j
	 */
	public void setHousImgOpenPhysicalPath(String housImgOpenPhysicalPath) {
		this.housImgOpenPhysicalPath = housImgOpenPhysicalPath;
	}

	/**
	 * �����摜�̉� root URL ���擾����B<br/>
	 * <br/>
	 * @return �����摜�̉� root URL
	 */
	public String getHousImgTempUrl() {
		return housImgTempUrl;
	}

	/**
	 * �����摜�̉� root URL ��ݒ肷��B<br/>
	 * <br/>
	 * @param housImgTempUrl�@�����摜�̉� root URL
	 */
	public void setHousImgTempUrl(String housImgTempUrl) {
		this.housImgTempUrl = housImgTempUrl;
	}

	/**
	 * �����摜�̌��J root URL ���擾����B<br/>
	 * <br/>
	 * @return �����摜�̌��J root URL
	 */
	public String getHousImgOpenUrl() {
		return housImgOpenUrl;
	}

	/**
	 * �����摜�̌��J root URL ��ݒ肷��B<br/>
	 * <br/>
	 * @param housImgOpenUrl �����摜�̌��J root URL
	 */
	public void setHousImgOpenUrl(String housImgOpenUrl) {
		this.housImgOpenUrl = housImgOpenUrl;
	}

	/**
	 * Commons Upload ���g�p���郏�[�N�t�H���_���擾����B<br/>
	 * <br/>
	 * @return Commons Upload ���g�p���郏�[�N�t�H���_
	 */
	public String getUploadWorkPath() {
		return uploadWorkPath;
	}

	/**
	 * Commons Upload ���g�p���郏�[�N�t�H���_��ݒ肷��B<br/>
	 * <br/>
	 * @param uploadWorkPath Commons Upload ���g�p���郏�[�N�t�H���_
	 */
	public void setUploadWorkPath(String uploadWorkPath) {
		this.uploadWorkPath = uploadWorkPath;
	}

	/**
	 * Cookie Domain ��ݒ肷��B<br/>
	 * <br/>
	 * @return Cookie Domain
	 */
	public String getCookieDomain() {
		return cookieDomain;
	}

	/**
	 * Cookie Domain ��ݒ肷��B<br/>
	 * <br/>
	 * @param cookieDomain Cookie Domain
	 */
	public void setCookieDomain(String cookieDomain) {
		this.cookieDomain = cookieDomain;
	}

	/**
	 * �T���l�C���摜�t�@�C���̃T�C�Y���X�g���擾����B<br/>
	 * <br/>
	 * @return �T���l�C���摜�t�@�C���̃T�C�Y���X�g
	 */
	public List<Integer> getThumbnailSizes() {
		return thumbnailSizes;
	}

	/**
	 * �T���l�C���摜�t�@�C���̃T�C�Y���X�g��ݒ肷��B<br/>
	 * <br/>
	 * @param thumbnailSizes�@�T���l�C���摜�t�@�C���̃T�C�Y���X�g
	 */
	public void setThumbnailSizes(List<Integer> thumbnailSizes) {
		this.thumbnailSizes = thumbnailSizes;
	}

	/**
	 * RequestScopeDataSource �� Bean ID ���擾����B<br/>
	 * ���̒l�́A�g�����U�N�V�������蓮���䂷��ꍇ�Ɏg�p�����B<br/>
	 * <br/>
	 * @return RequestScopeDataSource �� Bean ID
	 */
	public String getRequestScopeDataSourceId() {
		return requestScopeDataSourceId;
	}

	/**
	 * RequestScopeDataSource �� Bean ID ��ݒ肷��B<br/>
	 * <br/>
	 * @param requestScopeDataSourceId RequestScopeDataSource �� Bean ID
	 */
	public void setRequestScopeDataSourceId(String requestScopeDataSourceId) {
		this.requestScopeDataSourceId = requestScopeDataSourceId;
	}

	/**
	 * �Ǘ����[�U�[�e�[�u���� Alias �����擾����B<br/>
	 * <br/>
	 * @return �Ǘ����[�U�[�e�[�u���� Alias ��
	 */
	public String getAdminUserDbAlias() {
		return adminUserDbAlias;
	}

	/**
	 * �Ǘ����[�U�[�e�[�u���� Alias ����ݒ肷��B<br/>
	 * �F�ؗp DAO ���J�X�^�}�C�Y�ŕύX�����ہA����� Alias �����ێ��ł��Ȃ��ꍇ�A
	 * ���̃v���p�e�B�l��ύX����B �ʏ�A���̐ݒu�l���C�����鎖�͂Ȃ��B<br/>
	 * <br/>
	 * @param adminUserDbAlias�@�Ǘ����[�U�[�e�[�u���� Alias ��
	 */
	public void setAdminUserDbAlias(String adminUserDbAlias) {
		this.adminUserDbAlias = adminUserDbAlias;
	}

	/**
	 * �Ǘ����[�U�[�����e�[�u���� Alias �����擾����B<br/>
	 * <br/>
	 * @return �Ǘ����[�U�[�����e�[�u���� Alias ��
	 */
	public String getAdminRoleDbAlias() {
		return adminRoleDbAlias;
	}

	/**
	 * �Ǘ����[�U�[�����e�[�u���� Alias ����ݒ肷��B<br/>
	 * �F�ؗp DAO ���J�X�^�}�C�Y�ŕύX�����ہA����� Alias �����ێ��ł��Ȃ��ꍇ�A
	 * ���̃v���p�e�B�l��ύX����B �ʏ�A���̐ݒu�l���C�����鎖�͂Ȃ��B<br/>
	 * <br/>
	 * @param adminRoleDbAlias�@�Ǘ����[�U�[�����e�[�u���� Alias ��
	 */
	public void setAdminRoleDbAlias(String adminRoleDbAlias) {
		this.adminRoleDbAlias = adminRoleDbAlias;
	}

	/**
	 * �}�C�y�[�W������e�[�u���� Alias �����擾����B<br/>
	 * <br/>
	 * @return �}�C�y�[�W��������e�[�u���� Alias ��
	 */
	public String getMemberDbAlias() {
		return memberDbAlias;
	}

	/**
	 * �}�C�y�[�W������e�[�u���� Alias ����ݒ肷��B<br/>
	 * �F�ؗp DAO ���J�X�^�}�C�Y�ŕύX�����ہA����� Alias �����ێ��ł��Ȃ��ꍇ�A
	 * ���̃v���p�e�B�l��ύX����B �ʏ�A���̐ݒu�l���C�����鎖�͂Ȃ��B<br/>
	 * <br/>
	 * @param memberDbAlias�@�}�C�y�[�W������e�[�u���� Alias ��
	 */
	public void setMemberDbAlias(String memberDbAlias) {
		this.memberDbAlias = memberDbAlias;
	}

}
