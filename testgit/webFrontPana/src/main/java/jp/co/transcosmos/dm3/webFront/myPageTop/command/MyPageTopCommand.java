package jp.co.transcosmos.dm3.webFront.myPageTop.command;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.InformationManage;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.model.housingRequest.HousingRequest;
import jp.co.transcosmos.dm3.core.model.housingRequest.HousingRequestManageImpl;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingReqKind;
import jp.co.transcosmos.dm3.core.vo.HousingReqLayout;
import jp.co.transcosmos.dm3.core.vo.HousingReqPart;
import jp.co.transcosmos.dm3.core.vo.HousingRequestArea;
import jp.co.transcosmos.dm3.core.vo.Information;
import jp.co.transcosmos.dm3.core.vo.PartSrchMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.RecentlyInfoManage;
import jp.co.transcosmos.dm3.corePana.model.favorite.PanaFavoriteManageImpl;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousing;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingRequestInfo;
import jp.co.transcosmos.dm3.corePana.vo.MemberInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * �}�C�y�[�W���
 * ���N�G�X�g�p�����[�^�œn���ꂽ�����ڍׂ̃o���f�[�V�������s���A�����ڍ׉�ʂ�\������B
 *
 * �y���A���� View ���z
 *    �E"success" : ����I���i���O�C���ς݁j
 *    �E"nologin" : ����I���i���O�C���O�}�C�y�[�W�ɑJ�ځj
 *
 * �S����       �C����     �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �s�����C     2015.04.13  �V�K�쐬
 * Duong.Nguyen     2015.08.19  Put housing_kind_cd into housing request model
 *
 * ���ӎ���
 *
 * </pre>
 */
public class MyPageTopCommand implements Command {

	/** ���m�点�����e�i���X���s�� Model �I�u�W�F�N�g */
	private InformationManage informationManager;

	/** ���C�ɓ������p Model �I�u�W�F�N�g */
	private PanaFavoriteManageImpl panaFavoriteManager;

	/** �ŋߌ������������Ǘ����� Model �I�u�W�F�N�g */
	private RecentlyInfoManage recentlyInfoManage;

	/** �������N�G�X�g���p Model �I�u�W�F�N�g */
	private HousingRequestManageImpl housingRequestManage;

	/** ���ʃp�����[�^�I�u�W�F�N�g */
	private PanaCommonParameters commonParameters;

	/** �}�X�^��񃁃��e�i���X���s�� Model �I�u�W�F�N�g */
	private PanaCommonManage panamCommonManager;

	/** ���ʃR�[�h�ϊ����� */
	private CodeLookupManager codeLookupManager;

	/** �����������}�X�^ */
	private DAO<PartSrchMst> partSrchMstDAO;

	/** �}�C�y�[�W���[�U�[�̏��Ǘ��p Model */
	private MypageUserManage mypageUserManager;

	/** �����摜���DAO */
	private DAO<HousingImageInfo> housingImageInfoDAO;

	/** Panasonic�p�t�@�C�������֘A����Util */
	private PanaFileUtil fileUtil;

	/**
	 * �}�X�^��񃁃��e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param panaCommonManager
	 *            �}�X�^��񃁃��e�i���X�� model �I�u�W�F�N�g
	 */
	public void setPanamCommonManager(PanaCommonManage panamCommonManager) {
		this.panamCommonManager = panamCommonManager;
	}

	/**
	 * ���m�点�����e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param informationManager ���m�点�����e�i���X�� model �I�u�W�F�N�g
	 */
	public void setInformationManager(InformationManage informationManager) {
		this.informationManager = informationManager;
	}

	/**
	 * ���C�ɓ������p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param memberManager ���ʏ���p Model �I�u�W�F�N�g
	 */
	public void setPanaFavoriteManager(PanaFavoriteManageImpl panaFavoriteManager) {
		this.panaFavoriteManager = panaFavoriteManager;
	}

	/**
	 * �ŋߌ����������p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param RecentlyInfoManage �ŋߌ����������p Model �I�u�W�F�N�g
	 */
	public void setRecentlyInfoManage(RecentlyInfoManage recentlyInfoManage) {
		this.recentlyInfoManage = recentlyInfoManage;
	}

	/**
	 * �������N�G�X�g���p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param HousingRequestManage �������N�G�X�g�����p Model �I�u�W�F�N�g
	 */
	public void setHousingRequestManage(
			HousingRequestManageImpl housingRequestManage) {
		this.housingRequestManage = housingRequestManage;
	}

	/**
	 * ���ʃp�����[�^�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param commonParameters ���ʃp�����[�^�I�u�W�F�N�g
	 */
	public void setCommonParameters(PanaCommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * @param addressMstDAO
	 *            �Z�b�g���� addressMstDAO
	 */
	public void setPartSrchMstDAO(DAO<PartSrchMst> partSrchMstDAO) {
		this.partSrchMstDAO = partSrchMstDAO;
	}

	/**
	 * �}�C�y�[�W���[�U�[�̏��Ǘ��p Model�icore�j ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param mypageUserManager
	 *            �}�C�y�[�W���[�U�[�̏��Ǘ��p Model�icore�j
	 */
	public void setMypageUserManager(MypageUserManage mypageUserManager) {
		this.mypageUserManager = mypageUserManager;
	}

	/**
	 * @param housingImageInfoDAO �Z�b�g���� housingImageInfoDAO
	 */
	public void setHousingImageInfoDAO(DAO<HousingImageInfo> housingImageInfoDAO) {
		this.housingImageInfoDAO = housingImageInfoDAO;
	}

	/**
	 * Panasonic�p�t�@�C�������֘A����Util�B<br/>
	 * <br/>
	 *
	 * @param fileUtil �Z�b�g���� fileUtil
	 */
	public void setFileUtil(PanaFileUtil fileUtil) {
		this.fileUtil = fileUtil;
	}

	/**
	 * �}�C�y�[�W��ʕ\������<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g
	 * @param response
	 *            HTTP ���X�|���X
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
		Map<String, Object> model = createModel(request);
		String command = "success";

		// ���ʃp�����[�^���
		model.put("commonParameters", this.commonParameters);

		// �t�����g���̃��[�U���
		LoginUser loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);
		if (loginUser == null) {
			loginUser = FrontLoginUserUtils.getInstance(request).getAnonLoginUserInfo(request, response);
			command = "nologin";
		}
		String loginUserId = (String)loginUser.getUserId();

		// ���O�C���ς݁@���́@�����O�C���ł��K�v�ȏ����擾����
		getCommon(loginUserId, model, command);
		if ("success".equals(command)) {
			// ���O�C���ς݁@�K�v�ȏ����擾����
			getLogin(loginUserId, model);
		}

		return new ModelAndView(command, model);
	}



	/**
	 * ���O�C���ς݁@���́@�����O�C���ł��K�v�ȏ����擾����
	 * @param loginUserId
	 * @param model
	 * @param command
	 * @throws Exception
	 */
	private void getCommon(String loginUserId, Map<String, Object> model, String command) throws Exception {

		// �ŋߌ���������񌋉ʃ��X�g���擾����B
		List<PanaHousing> recentlyInfoList = this.recentlyInfoManage.searchRecentlyInfo(loginUserId);
		// �ŋߌ��������摜��񃊃X�g
		List<String> recentlyInfoImgList = new ArrayList<String>();
		HousingInfo housingInfo = new HousingInfo();
		for(PanaHousing housing: recentlyInfoList) {
			housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
			String sysHousingCd = housingInfo.getSysHousingCd();
			// �{������������A���A�\�����A�摜�^�C�v�A�}�Ԃŏ����\�[�g���1�Ԗڂ̉摜��\��
			DAOCriteria criteria = new DAOCriteria();
			criteria.addWhereClause("sysHousingCd", sysHousingCd);
			if ("success".equals(command)) {
				criteria.addWhereClause("roleId", null, DAOCriteria.IS_NULL, true);
			} else {
				criteria.addWhereClause("roleId", PanaCommonConstant.ROLE_ID_PUBLIC);
			}
			criteria.addOrderByClause("sortOrder");
			criteria.addOrderByClause("imageType");
			criteria.addOrderByClause("divNo");
			List<HousingImageInfo> housingImageInfoList = this.housingImageInfoDAO.selectByFilter(criteria);
			/** �����摜���ݒ� */
			String pathName = "";
			if (housingImageInfoList.size() > 0) {
				jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo housingImageInfo = (jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo)housingImageInfoList.get(0);
				if (PanaCommonConstant.ROLE_ID_PUBLIC.equals(housingImageInfo.getRoleId())) {
					// �{���������S���̏ꍇ
					pathName = this.fileUtil.getHousFileOpenUrl(housingImageInfoList.get(0).getPathName(),
							housingImageInfoList.get(0).getFileName(),
							this.commonParameters.getMypageHistoryImageSize());
				} else if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(housingImageInfo.getRoleId())) {
					// �{������������݂̂̏ꍇ
					pathName = this.fileUtil.getHousFileMemberUrl(housingImageInfoList.get(0).getPathName(),
							housingImageInfoList.get(0).getFileName(),
							this.commonParameters.getMypageHistoryImageSize());
				}
			}
			recentlyInfoImgList.add(pathName);
		}

		model.put("recentlyInfoList", recentlyInfoList);
		model.put("recentlyInfoImgList", recentlyInfoImgList);

	}

	/**
	 * ���O�C���ς݁@�K�v�ȏ����擾����
	 * @param loginUserId
	 * @param model
	 * @throws Exception
	 */
	private void getLogin(String loginUserId, Map<String, Object> model) throws Exception {

		// ���q�l�ւ̂��m�点�̎擾
		List<Information> infoList= this.informationManager.searchMyPageInformation(loginUserId);
		// ���C�ɓ��蕨���ꗗ���̎擾
		List<PanaHousing> favoriteInfoList = this.panaFavoriteManager.searchFavoriteInfo(loginUserId);
		// ���C�ɓ��蕨���摜��񃊃X�g
		List<String> favoriteInfoImgList = new ArrayList<String>();
		List<jp.co.transcosmos.dm3.core.vo.HousingImageInfo> housingImageInfoList = new  ArrayList<jp.co.transcosmos.dm3.core.vo.HousingImageInfo>();
		for(PanaHousing housing : favoriteInfoList) {
			housingImageInfoList = housing.getHousingImageInfos();
			if(null != housingImageInfoList) {
				/** �����摜���ݒ� */
				String pathName = "";
				if (housingImageInfoList.size() > 0) {
					jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo housingImageInfo = (jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo)housingImageInfoList.get(0);
					if (PanaCommonConstant.ROLE_ID_PUBLIC.equals(housingImageInfo.getRoleId())) {
						// �{���������S���̏ꍇ
						pathName = this.fileUtil.getHousFileOpenUrl(housingImageInfoList.get(0).getPathName(),
								housingImageInfoList.get(0).getFileName(),
								this.commonParameters.getMypageHistoryImageSize());
					} else if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(housingImageInfo.getRoleId())) {
						// �{������������݂̂̏ꍇ
						pathName = this.fileUtil.getHousFileMemberUrl(housingImageInfoList.get(0).getPathName(),
								housingImageInfoList.get(0).getFileName(),
								this.commonParameters.getMypageHistoryImageSize());
					}
				}
				favoriteInfoImgList.add(pathName);
			}
		}
		// �������N�G�X�g�̈ꗗ���̎擾
		List<HousingRequest> housingRequestList = this.housingRequestManage.searchRequest(loginUserId);
		// �}�C�y�[�W���[�U�[���̎擾
		JoinResult joinResult = this.mypageUserManager.searchMyPageUserPk(loginUserId);
		MemberInfo memberInfo = (MemberInfo)joinResult.getItems().get("memberInfo");

		// �������N�G�X�g���
		HousingRequestInfo housingRequestInfo = new HousingRequestInfo();
		// �������N�G�X�g�G���A���̃��X�g
		List<HousingRequestArea> housingRequestAreas = new ArrayList<HousingRequestArea>();
		// �������N�G�X�g�Ԏ����̃��X�g
		List<HousingReqLayout> housingReqLayouts = new ArrayList<HousingReqLayout>();
		// �������N�G�X�g������ޏ��̃��X�g
		List<HousingReqKind> housingReqKinds = new ArrayList<HousingReqKind>();
		// �������N�G�X�g�������������̃��X�g
		List<HousingReqPart> housingReqParts = new ArrayList<HousingReqPart>();
		// ��ʗp�������N�G�X�g���
		List<JoinResult> housingRequestLst = new ArrayList<JoinResult>();
		HousingReqLayout housingReqLayout = new HousingReqLayout();

		for(HousingRequest housingRequest : housingRequestList) {
			Map<String, Object> hr = new HashMap<>();
			Map<String, Object> requestList = new HashMap<>();
			housingRequestInfo = (HousingRequestInfo)housingRequest.getHousingRequestInfo();
			housingRequestAreas = housingRequest.getHousingRequestAreas();
			housingReqLayouts = housingRequest.getHousingReqLayouts();

			housingReqKinds = housingRequest.getHousingReqKinds();
			HousingReqKind housingReqKind = new HousingReqKind();
			if(0 < housingReqKinds.size()) {
				housingReqKind = housingReqKinds.get(0);
			}
			housingReqParts = housingRequest.getHousingReqParts();
			// �s���{����
			String prefName = "";
			// �s�撬����
			StringBuffer addressCd = new StringBuffer();
			// ��������
			StringBuffer requests = new StringBuffer();
			for(int i = 0; i < housingRequestAreas.size(); i++) {
				HousingRequestArea arer = housingRequestAreas.get(i);
				prefName = this.panamCommonManager.getPrefName(arer.getPrefCd());
				addressCd.append(this.panamCommonManager.getAddressName(arer.getAddressCd()));
				if(i < housingRequestAreas.size()) {
					addressCd.append("�@");
				}
			}

			// �\�Z
			if(null != housingRequestInfo.getPriceLower() || null != housingRequestInfo.getPriceUpper()) {
				requests.append(castPrice(housingRequestInfo.getPriceLower(), housingRequestInfo.getPriceUpper()));
				if(!StringUtils.isEmpty(housingRequestInfo.getUseReform())) {
					if(!PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(housingReqKind.getHousingKindCd())) {
						requests.append("�@���t�H�[�����i����");
					}
				}
				requests.append("�b");
			}

			// ������ʂb�c:�}���V����
			if(PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(housingReqKind.getHousingKindCd())) {
				// ��L�ʐ�
				if(null != housingRequestInfo.getPersonalAreaLower() || null != housingRequestInfo.getPersonalAreaUpper()) {
					requests.append(bigDecimalFormat(housingRequestInfo.getPersonalAreaLower(), housingRequestInfo.getPersonalAreaUpper(), "1"));
					requests.append("�b");
				}

				// �Ԏ��
				if(0 < housingReqLayouts.size()) {
					housingReqLayout = new HousingReqLayout();
					for(int i = 0; i < housingReqLayouts.size(); i++) {
						housingReqLayout = housingReqLayouts.get(i);
						if(!StringUtils.isEmpty(housingReqLayout.getLayoutCd())) {
							requests.append(this.codeLookupManager.lookupValue("layoutCd", housingReqLayout.getLayoutCd()));
							if(i < housingReqLayouts.size() - 1) {
								requests.append("�@");
							}
						}
					}
					requests.append("�b");
				}

				// �z�N��
				if(null != housingRequestInfo.getBuiltMonth()) {
					if(housingRequestInfo.getBuiltMonth().compareTo(new Integer(0))  > 0) {
						requests.append("�z�N��");
						requests.append(this.codeLookupManager.lookupValue("compDate", housingRequestInfo.getBuiltMonth().toString()));
						requests.append("�b");
					}
				}

				// ��������
				if(0 < housingReqParts.size()) {
					for(HousingReqPart part : housingReqParts) {
						// ��������������
						PartSrchMst partSrchMst =  partSrchMstDAO.selectByPK(part.getPartSrchCd());
						requests.append(partSrchMst.getPartSrchName());
						requests.append("�@");
					}
				}

			}

			// ������ʂb�c:�ˌ� ������ʂb�c:�y�n
			if(PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(housingReqKind.getHousingKindCd()) ||
					PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(housingReqKind.getHousingKindCd())) {

				// �����ʐ�
				if(null != housingRequestInfo.getBuildingAreaLower() || null != housingRequestInfo.getBuildingAreaUpper()) {
					requests.append(bigDecimalFormat(housingRequestInfo.getBuildingAreaLower(), housingRequestInfo.getBuildingAreaUpper(), "2"));
					requests.append("�b");
				}

				// �y�n�ʐ�
				if(null != housingRequestInfo.getLandAreaLower() || null != housingRequestInfo.getLandAreaUpper()) {
					requests.append(bigDecimalFormat(housingRequestInfo.getLandAreaLower(), housingRequestInfo.getLandAreaUpper(), "3"));
					requests.append("�b");
				}
			}

			// ������ʂb�c:�ˌ�
			if(PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(housingReqKind.getHousingKindCd())) {
				// �Ԏ��
				if(0 < housingReqLayouts.size()) {
					housingReqLayout = new HousingReqLayout();
					for(int i = 0; i < housingReqLayouts.size(); i++) {
						housingReqLayout = housingReqLayouts.get(i);
						if(!StringUtils.isEmpty(housingReqLayout.getLayoutCd())) {
							requests.append(this.codeLookupManager.lookupValue("layoutCd", housingReqLayout.getLayoutCd()));
							if(i < housingReqLayouts.size() - 1) {
								requests.append("�@");
							}
						}
					}
					requests.append("�b");
				}

				// �z�N��
				if(null != housingRequestInfo.getBuiltMonth()) {
					if(housingRequestInfo.getBuiltMonth().compareTo(new Integer(0))  > 0) {
						requests.append("�z�N��");
						requests.append(this.codeLookupManager.lookupValue("compDate", housingRequestInfo.getBuiltMonth().toString()));
						requests.append("�b");
					}
				}

				// ��������
				if(0 < housingReqParts.size()) {
					for(HousingReqPart part : housingReqParts) {
						// ��������������
						PartSrchMst partSrchMst =  partSrchMstDAO.selectByPK(part.getPartSrchCd());
						requests.append(partSrchMst.getPartSrchName());
						requests.append("�@");
					}
				}
			}

			// 2015.08.19 Duong.Nguyen Put housing_kind_cd into housing request model start
			hr.put("housingKindCd", housingReqKind.getHousingKindCd());
			// 2015.08.19 Duong.Nguyen Put housing_kind_cd into housing request model end
			hr.put("housingRequestId", housingRequestInfo.getHousingRequestId());
			hr.put("prefName", prefName);
			hr.put("addressCd", addressCd.toString());
			String requestStr = requests.toString();
			if(!StringUtils.isEmpty(requestStr)) {
				int len = requestStr.length();
				String latStr = requestStr.substring(len - 1, len);
				if(latStr.equals("�@") || latStr.equals("�b")) {
					requestStr = requestStr.substring(0, len - 1);
				}
			}
			hr.put("requests", requestStr);

			requestList.put("housingRequest", hr);
			JoinResult requestLst = new JoinResult(requestList);
			housingRequestLst.add(requestLst);
		}

		// �������s���s���B
		model.put("infoList", infoList);
		model.put("favoriteInfoList", favoriteInfoList);
		model.put("favoriteInfoImgList", favoriteInfoImgList);
		model.put("housingRequestLst", housingRequestLst);
		model.put("memberInfo", memberInfo);
	}

	/**
	 * ���N�G�X�g�p�����[�^���� outPutForm �I�u�W�F�N�g���쐬����B<br/>
	 * �������� outPutForm �I�u�W�F�N�g�� Map �Ɋi�[���ĕ��A����B<br/>
	 * key = �t�H�[���N���X���i�p�b�P�[�W�Ȃ��j�AValue = �t�H�[���I�u�W�F�N�g <br/>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^���ݒ肳�ꂽ�t�H�[���I�u�W�F�N�g���i�[���� Map �I�u�W�F�N�g
	 */
	private Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<>();

		return model;
	}

	/**
	 * �e�ʐς̕ҏW���s���B
	 *
	 * @param areaLower �ʐρE�����l
	 * @param areaUpper �ʐρE����l
	 * @param areaFlg �ʐσt���O(1:��L�ʐρA2:�����ʐρA3:�y�n�ʐ�)
	 * @return �p�����[�^���ݒ肳�ꂽ�t�H�[���I�u�W�F�N�g���i�[���� Map �I�u�W�F�N�g
	 */
	private String bigDecimalFormat(BigDecimal areaLower, BigDecimal areaUpper, String areaFlg) {
		StringBuffer res = new StringBuffer();

		if("1".equals(areaFlg)) {
			res.append("��L�ʐ�");
		}

		if("2".equals(areaFlg)) {
			res.append("�����ʐ�");
		}

		if("3".equals(areaFlg)) {
			res.append("�y�n�ʐ�");
		}

		if(null != areaLower) {
			if(areaLower.compareTo(BigDecimal.ZERO) > 0) {
				res.append(areaLower);
				res.append("�u");
				res.append("�ȏ�");
			}
		}

		if(res.length() > 4) {
			res.append("�@");
		}

		if(null != areaUpper) {
			if(areaUpper.compareTo(BigDecimal.ZERO) > 0) {
				res.append(areaUpper);
				res.append("�u");
				res.append("�ȉ�");
			}
		}

		return res.toString();
	}

	/**
	 * �\�Z�̕ҏW���s���B
	 *
	 * @param priceLower ����/���i�E�����l
	 * @param priceUpper ����/���i�E����l
	 * @return �p�����[�^���ݒ肳�ꂽ�t�H�[���I�u�W�F�N�g���i�[���� Map �I�u�W�F�N�g
	 */
	private String castPrice(Long priceLower, Long priceUpper) {
		// �Ԃ�����
		StringBuffer res = new StringBuffer();
		Long longLower= 0L;
		Long longUpper= 0L;

		res.append("�\�Z");

		if(priceLower != null) {
			if(priceLower.compareTo(longLower) > 0) {
				longLower = priceLower / 10000L;

				if(longLower.compareTo(10000L) == 0) {
					longLower = longLower / 10000L;
					res.append(longLower);
					res.append("��");
				} else {
					res.append(longLower);
					res.append("���~");
				}

				res.append("�ȏ�");
			}
		}

		if(res.length() > 2) {
			res.append("�@");
		}

		if(priceUpper != null) {
			if(priceUpper.compareTo(longUpper) > 0) {
				longUpper = priceUpper / 10000L;

				if(longUpper.compareTo(10000L) == 0) {
					longUpper = longUpper / 10000L;
					res.append(longUpper);
					res.append("��");
				} else {
					res.append(longUpper);
					res.append("���~");
				}

				res.append("�ȉ�");
			}
		}
		return res.toString();
	}
}
