/**
 * 
 */
package jp.co.transcosmos.dm3.core.model.housingRequest;

import java.util.ArrayList;
import java.util.List;

import jp.co.transcosmos.dm3.adminCore.request.form.HousingRequestForm;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.HousingManage;
import jp.co.transcosmos.dm3.core.model.HousingRequestManage;
import jp.co.transcosmos.dm3.core.model.exception.MaxEntryOverException;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingFormFactory;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingSearchForm;
import jp.co.transcosmos.dm3.core.model.housingRequest.form.RequestSearchForm;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.AddressMst;
import jp.co.transcosmos.dm3.core.vo.HousingReqKind;
import jp.co.transcosmos.dm3.core.vo.HousingReqLayout;
import jp.co.transcosmos.dm3.core.vo.HousingReqPart;
import jp.co.transcosmos.dm3.core.vo.HousingReqRoute;
import jp.co.transcosmos.dm3.core.vo.HousingReqStation;
import jp.co.transcosmos.dm3.core.vo.HousingRequestArea;
import jp.co.transcosmos.dm3.core.vo.HousingRequestInfo;
import jp.co.transcosmos.dm3.core.vo.MemberInfo;
import jp.co.transcosmos.dm3.core.vo.PartSrchMst;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.dao.OrCriteria;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �������N�G�X�g���p Model �N���X.
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * T.Nakamura	2015.03.30	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B<br/>
 * 
 */
public class HousingRequestManageImpl implements HousingRequestManage {

	private static final Log log = LogFactory.getLog(HousingRequestManageImpl.class);

	/** VO �̃C���X�^���X�𐶐�����ꍇ�̃t�@�N�g���[ */
	protected ValueObjectFactory valueObjectFactory;
	/** ���ʐݒ���Ǘ��N���X */
	protected CommonParameters commonParameters;

	/** �������Model�i�������N�G�X�g�ɊY�����镨�����擾�Ɏg�p�j */
	protected HousingManage housingManager;
	/** �������FormFactory�i�������N�G�X�g�ɊY�����镨�����擾�Ɏg�p�j */
	protected HousingFormFactory housingFormFactory;

	/** �}�C�y�[�W������DAO */
	protected DAO<MemberInfo> memberInfoDAO;

	/** �������N�G�X�g���DAO */
	protected DAO<HousingRequestInfo> housingRequestInfoDAO;
	/** �������N�G�X�g�G���A���DAO */
	protected DAO<HousingRequestArea> housingRequestAreaDAO;
	/** �������N�G�X�g�H�����DAO */
	protected DAO<HousingReqRoute> housingReqRouteDAO;
	/** �������N�G�X�g�Ŋ��w���DAO */
	protected DAO<HousingReqStation> housingReqStationDAO;
	/** �������N�G�X�g������ޏ��DAO */
	protected DAO<HousingReqKind> housingReqKindDAO;
	/** �������N�G�X�g�Ԏ���DAO */
	protected DAO<HousingReqLayout> housingReqLayoutDAO;
	/** �������N�G�X�g�������������DAO */
	protected DAO<HousingReqPart> housingReqPartDAO;

	/** �s���{���}�X�^DAO */
	protected DAO<PrefMst> prefMstDAO;
	/** �s�撬���}�X�^DAO */
	protected DAO<AddressMst> addressMstDAO;
	/** �H���}�X�^DAO */
	protected DAO<RouteMst> routeMstDAO;
	/** �w���}�X�^DAO */
	protected DAO<StationMst> stationMstDAO;
	/** �w�E�H���Ή��̃}�X�^�擾�pDAO */
	protected DAO<JoinResult> stationRouteRelationDAO;
	/** �����������}�X�^DAO */
	protected DAO<PartSrchMst> partSrchMstDAO;

	// DI Setter START ----------------------------------------
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}

	public void setCommonParameters(CommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	public void setHousingManager(HousingManage housingManager) {
		this.housingManager = housingManager;
	}

	public void setHousingFormFactory(HousingFormFactory housingFormFactory) {
		this.housingFormFactory = housingFormFactory;
	}

	public void setMemberInfoDAO(DAO<MemberInfo> memberInfoDAO) {
		this.memberInfoDAO = memberInfoDAO;
	}

	public void setHousingRequestInfoDAO(DAO<HousingRequestInfo> housingRequestInfoDAO) {
		this.housingRequestInfoDAO = housingRequestInfoDAO;
	}

	public void setHousingRequestAreaDAO(DAO<HousingRequestArea> housingRequestAreaDAO) {
		this.housingRequestAreaDAO = housingRequestAreaDAO;
	}

	public void setHousingReqRouteDAO(DAO<HousingReqRoute> housingReqRouteDAO) {
		this.housingReqRouteDAO = housingReqRouteDAO;
	}

	public void setHousingReqStationDAO(DAO<HousingReqStation> housingReqStationDAO) {
		this.housingReqStationDAO = housingReqStationDAO;
	}

	public void setHousingReqKindDAO(DAO<HousingReqKind> housingReqKindDAO) {
		this.housingReqKindDAO = housingReqKindDAO;
	}

	public void setHousingReqLayoutDAO(DAO<HousingReqLayout> housingReqLayoutDAO) {
		this.housingReqLayoutDAO = housingReqLayoutDAO;
	}

	public void setHousingReqPartDAO(DAO<HousingReqPart> housingReqPartDAO) {
		this.housingReqPartDAO = housingReqPartDAO;
	}

	public void setPrefMstDAO(DAO<PrefMst> prefMstDAO) {
		this.prefMstDAO = prefMstDAO;
	}

	public void setAddressMstDAO(DAO<AddressMst> addressMstDAO) {
		this.addressMstDAO = addressMstDAO;
	}

	public void setRouteMstDAO(DAO<RouteMst> routeMstDAO) {
		this.routeMstDAO = routeMstDAO;
	}

	public void setStationMstDAO(DAO<StationMst> stationMstDAO) {
		this.stationMstDAO = stationMstDAO;
	}

	public void setStationRouteRelationDAO(DAO<JoinResult> stationRouteRelationDAO) {
		this.stationRouteRelationDAO = stationRouteRelationDAO;
	}

	public void setPartSrchMstDAO(DAO<PartSrchMst> partSrchMstDAO) {
		this.partSrchMstDAO = partSrchMstDAO;
	}

	// DI Setter END ----------------------------------------

	/**
	 * �w�肳�ꂽ Form �̒l�ŕ������N�G�X�g����o�^����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * �������N�G�X�gID �͎����̔Ԃ����̂ŁAHousingRequestForm �� housingRequestId
	 * �v���p�e�B�ɂ͒l��ݒ肵�Ȃ����B<br/>
	 * �o�^����������l�ɒB����ꍇ�͓o�^�����ɃG���[�Ƃ��ĕ��A���鎖�B<br/>
	 * <br/>
	 * @param userId �}�C�y�[�W���[�U�[ID
	 * @param inputForm ���͂��ꂽ�������N�G�X�g���
	 * 
	 * @return �̔Ԃ��ꂽ�������N�G�X�gID
	 * 
  	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
  	 * @exception MaxEntryOverException �ő�o�^���I�[�o�[
	 */
	@Override
	public String addRequest(String userId, HousingRequestForm inputForm) throws Exception, MaxEntryOverException {

		// �p�����^�`�F�b�N
		if (StringValidateUtil.isEmpty(userId)) {
			log.warn("userId Is Null or Empty [userId = " + userId + "]");
			return null;
		}

		// �o�^�Ώۏ��̑��݃`�F�b�N
		MemberInfo existMenberInfo = this.memberInfoDAO.selectByPK(userId);
		if (existMenberInfo == null) {
			log.warn("Target MemberInfo Is Null [userId = " + userId + "]");
			return null;
		}

		// �o�^��������`�F�b�N
		int nowCnt = searchRequestCnt(userId);
		if (!(nowCnt < commonParameters.getMaxHousingRequestCnt())) {
			log.warn("housingRequest max entry over [maxHousingRequestCnt = " + commonParameters.getMaxHousingRequestCnt() + "]");
			throw new MaxEntryOverException();
		}

		// �������N�G�X�g���̓o�^
		HousingRequestInfo housingRequestInfo = buildHousingRequestInfo();
		inputForm.copyToHousingRequestInfo(userId, housingRequestInfo);
		housingRequestInfo.setInsDate(housingRequestInfo.getUpdDate());
		housingRequestInfo.setInsUserId(housingRequestInfo.getUpdUserId());
		this.housingRequestInfoDAO.insert(new HousingRequestInfo[] { housingRequestInfo });

		// �e�p�����^�����݂���ꍇ�ɑΉ�����e�[�u���Ƀ��R�[�h��o�^

		// �������N�G�X�g�G���A���
		insertHousingRequestArea(housingRequestInfo.getHousingRequestId(), inputForm.getPrefCd(), inputForm.getAddressCd());

		// �������N�G�X�g�Ŋ�w���
		insertHousingReqRouteStation(housingRequestInfo.getHousingRequestId(), inputForm.getRouteCd(), inputForm.getStationCd());

		// �������N�G�X�g������ޏ��
		insertHousingReqKind(housingRequestInfo.getHousingRequestId(), inputForm.getHousingKindCd());

		// �������N�G�X�g�Ԏ���
		insertHousingReqLayout(housingRequestInfo.getHousingRequestId(), inputForm.getLayoutCd());

		// �������N�G�X�g�������������
		insertHousingReqPartSrch(housingRequestInfo.getHousingRequestId(), inputForm.getPartSrchCd());

		return housingRequestInfo.getHousingRequestId();
	}

	/**
	 * �w�肳�ꂽ Form �̒l�ŕ������N�G�X�g�����X�V����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * �X�V�Ɏg�p����L�[�́AHousingRequestForm�@�� housingRequestId �ƁA�����œn���ꂽ
	 * userId ���g�p���鎖�B  �iuserId �̓��N�G�X�g�p�����[�^�Ŏ擾���Ȃ����B�j<br/>
	 * <br/>
	 * @param userId �}�C�y�[�W���[�U�[ID
	 * @param inputForm ���͂��ꂽ�������N�G�X�g���i�X�V�Ώۂ̎�L�[�l���܂ށj
	 * 
  	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
  	 * @exception NotFoundException �X�V�ΏۂȂ�
	 */
	@Override
	public void updateRequest(String userId, HousingRequestForm inputForm) throws Exception, NotFoundException {

		if (StringValidateUtil.isEmpty(userId)) {
			log.warn("userId Is Null or Empty [userId = " + userId + "]");
			return;
		}
		if (StringValidateUtil.isEmpty(inputForm.getHousingRequestId())) {
			log.warn("housingRequestId Is Null or Empty [housingRequestId = " + inputForm.getHousingRequestId() + "]");
			return;
		}

		List<HousingRequest> requests = searchRequest(userId, inputForm.getHousingRequestId());
		if (requests.size() != 1) {
			log.warn("Selected HousingRequestInfo Is Empty or Not Unique [userId = " + userId + ", housingRequestId = " + inputForm.getHousingRequestId() + "]");
			return ;
		}

		HousingRequest request = requests.get(0);

		// �������N�G�X�g���
		HousingRequestInfo housingRequestInfo = request.getHousingRequestInfo();
		inputForm.copyToHousingRequestInfo(userId, housingRequestInfo);
		this.housingRequestInfoDAO.update(new HousingRequestInfo[] { housingRequestInfo });

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", housingRequestInfo.getHousingRequestId());

		// �������N�G�X�g�G���A���
		this.housingRequestAreaDAO.deleteByFilter(criteria);
		insertHousingRequestArea(housingRequestInfo.getHousingRequestId(), inputForm.getPrefCd(), inputForm.getAddressCd());

		// �������N�G�X�g�H�����
		// �������N�G�X�g�Ŋ�w���
		this.housingReqRouteDAO.deleteByFilter(criteria);
		this.housingReqStationDAO.deleteByFilter(criteria);
		insertHousingReqRouteStation(housingRequestInfo.getHousingRequestId(), inputForm.getRouteCd(), inputForm.getStationCd());

		// �������N�G�X�g������ޏ��
		this.housingReqKindDAO.deleteByFilter(criteria);
		insertHousingReqKind(housingRequestInfo.getHousingRequestId(), inputForm.getHousingKindCd());

		// �������N�G�X�g�Ԏ����
		this.housingReqLayoutDAO.deleteByFilter(criteria);
		insertHousingReqLayout(housingRequestInfo.getHousingRequestId(), inputForm.getLayoutCd());

		// �������N�G�X�g�������������
		this.housingReqPartDAO.deleteByFilter(criteria);
		insertHousingReqPartSrch(housingRequestInfo.getHousingRequestId(), inputForm.getPartSrchCd());

	}

	/**
	 * �������N�G�X�g�G���A���o�^����<br/>
	 * <br/>
	 * 
	 * @param housingRequestId
	 * @param csPrefCd
	 * @param csAddressCd
	 */
	protected void insertHousingRequestArea(String housingRequestId, String csPrefCd, String csAddressCd) {

		// �������N�G�X�g�G���A���
		if (!StringValidateUtil.isEmpty(csPrefCd)) {

			// �ΏۂƂ���s���{��CD�̓}�X�^�ɑ��݂��邱��
			String[] prefCds = csPrefCd.split(",");
			List<PrefMst> prefMsts = this.prefMstDAO.selectByPK(prefCds);

			List<AddressMst> addressMsts;
			if (!StringValidateUtil.isEmpty(csAddressCd)) {

				// �ΏۂƂ���s�撬��CD�̓}�X�^�ɑ��݂��邱��
				String[] addressCds = csAddressCd.split(",");
				addressMsts = this.addressMstDAO.selectByPK(addressCds);
			} else {
				addressMsts = new ArrayList<AddressMst>();
			}

			if (prefMsts.size() > 0) {
				List<HousingRequestArea> housingRequestAreas = new ArrayList<HousingRequestArea>();

				for (PrefMst prefMst : prefMsts) {
					// �s���{���}�X�^���擾�ł��Ă���ꍇ

					HousingRequestArea housingRequestArea;
					boolean isExistAddress = false;

					if (addressMsts.size() > 0) {
						// �s�撬���}�X�^���擾�ł��Ă���ꍇ

						for (AddressMst addressMst : addressMsts) {
							if (prefMst.getPrefCd().equals(addressMst.getPrefCd())) {
								housingRequestArea = buildHousingRequestArea();
								housingRequestArea.setHousingRequestId(housingRequestId);
								housingRequestArea.setAddressCd(addressMst.getAddressCd());
								housingRequestArea.setPrefCd(addressMst.getPrefCd());
								housingRequestAreas.add(housingRequestArea);

								// �s�撬��CD�̃��R�[�h�o�^�L��
								isExistAddress = true;
							}
						}
					}

					if (!isExistAddress) {
						// �s�撬��CD���܂߂Ă��o�^�������ꍇ�A�s���{��CD�݂̂œo�^����
						housingRequestArea = buildHousingRequestArea();
						housingRequestArea.setHousingRequestId(housingRequestId);
						housingRequestArea.setPrefCd(prefMst.getPrefCd());
						housingRequestAreas.add(housingRequestArea);
					}

				}

				this.housingRequestAreaDAO.insert(housingRequestAreas.toArray(new HousingRequestArea[housingRequestAreas.size()]));
			}
		}
	}

	protected void insertHousingReqRouteStation(String housingRequestId, String routeCd, String stationCd) {

		if (!StringValidateUtil.isEmpty(stationCd) && !StringValidateUtil.isEmpty(routeCd)) {

			// �ΏۂƂ���wCD�̓}�X�^�ɑ��݂��邱��
			String[] stationCds = stationCd.split(",");
			List<StationMst> stationMsts = this.stationMstDAO.selectByPK(stationCds);

			if (stationMsts.size() > 0) {
				DAOCriteria criteria = new DAOCriteria();
				OrCriteria orCriteria = new OrCriteria();

				List<HousingReqStation> housingReqStations = new ArrayList<HousingReqStation>();
				for (StationMst stationMst : stationMsts) {
					HousingReqStation housingReqStation = buildHousingReqStation();
					housingReqStation.setHousingRequestId(housingRequestId);
					housingReqStation.setStationCd(stationMst.getStationCd());
					housingReqStations.add(housingReqStation);

					// �H�����̌��������ƂȂ�wCD
					orCriteria.addWhereClause("stationMst", "stationCd", stationMst.getStationCd(), DAOCriteria.EQUALS, false);
				}

				criteria.addSubCriteria(orCriteria);
				orCriteria = new OrCriteria();

				// �ΏۂƂ���H��CD�̓}�X�^�ɑ��݂��邱��
				String[] routeCds = routeCd.split(",");
				List<RouteMst> routeMsts = this.routeMstDAO.selectByPK(routeCds);

				for (RouteMst routeMst : routeMsts) {
					orCriteria.addWhereClause("routeMst", "routeCd", routeMst.getRouteCd(), DAOCriteria.EQUALS, false);
				}

				criteria.addSubCriteria(orCriteria);
				criteria.addOrderByClause("routeMst","routeCd",true);

				// �w�}�X�^�Ɗ֘A�t���đΏۂ��擾
				List<JoinResult> results = this.stationRouteRelationDAO.selectByFilter(criteria);

				if (results.size() > 0) {
					List<HousingReqRoute> housingReqRoutes = new ArrayList<HousingReqRoute>();
					String tempRouteCd = "";
					for (JoinResult result : results) {
						RouteMst routeMst = (RouteMst) result.getItems().get("routeMst");

						// �d���`�F�b�N
						if (tempRouteCd.equals(routeMst.getRouteCd())) {
							continue;
						}
						tempRouteCd = routeMst.getRouteCd();

						HousingReqRoute housingReqRoute = buildHousingReqRoute();
						housingReqRoute.setHousingRequestId(housingRequestId);
						housingReqRoute.setRouteCd(routeMst.getRouteCd());
						housingReqRoutes.add(housingReqRoute);
					}

					this.housingReqStationDAO.insert(housingReqStations.toArray(new HousingReqStation[housingReqStations.size()]));
					this.housingReqRouteDAO.insert(housingReqRoutes.toArray(new HousingReqRoute[housingReqRoutes.size()]));
				}
			}

		} else if (!StringValidateUtil.isEmpty(routeCd)) {
			// �������N�G�X�g�H�����

			// �ΏۂƂ���H��CD�̓}�X�^�ɑ��݂��邱��
			String[] routeCds = routeCd.split(",");
			List<RouteMst> routeMsts = this.routeMstDAO.selectByPK(routeCds);

			if (routeMsts.size() > 0) {
				List<HousingReqRoute> housingReqRoutes = new ArrayList<HousingReqRoute>();
				for(RouteMst routeMst : routeMsts) {
					HousingReqRoute housingReqRoute = buildHousingReqRoute();
					housingReqRoute.setHousingRequestId(housingRequestId);
					housingReqRoute.setRouteCd(routeMst.getRouteCd());
					housingReqRoutes.add(housingReqRoute);
				}

				this.housingReqRouteDAO.insert(housingReqRoutes.toArray(new HousingReqRoute[housingReqRoutes.size()]));
			}
		}
	}

	protected void insertHousingReqKind(String housingRequestId, String csHhousingKindCd) {

		if (!StringValidateUtil.isEmpty(csHhousingKindCd)) {

			String[] kindCds = csHhousingKindCd.split(",");

			List<HousingReqKind> housingReqKinds = new ArrayList<HousingReqKind>();
			for (String kindCd : kindCds) {
				HousingReqKind housingReqKind = buildHousingReqKind();
				housingReqKind.setHousingRequestId(housingRequestId);
				housingReqKind.setHousingKindCd(kindCd);
				housingReqKinds.add(housingReqKind);
			}

			this.housingReqKindDAO.insert(housingReqKinds.toArray(new HousingReqKind[housingReqKinds.size()]));
		}
	}

	protected void insertHousingReqLayout(String housingRequestId, String csLayoutCd) {

		if (!StringValidateUtil.isEmpty(csLayoutCd)) {

			String[] layoutCds = csLayoutCd.split(",");

			List<HousingReqLayout> housingReqLayouts = new ArrayList<HousingReqLayout>();
			for (String layoutCd : layoutCds) {
				HousingReqLayout housingReqLayout = buildHousingReqLayout();
				housingReqLayout.setHousingRequestId(housingRequestId);
				housingReqLayout.setLayoutCd(layoutCd);
				housingReqLayouts.add(housingReqLayout);
			}

			this.housingReqLayoutDAO.insert(housingReqLayouts.toArray(new HousingReqLayout[housingReqLayouts.size()]));
		}
	}

	protected void insertHousingReqPartSrch(String housingRequestId, String csPartSrchCd) {

		if (!StringValidateUtil.isEmpty(csPartSrchCd)) {

			// �ΏۂƂ��邱��������CD�̓}�X�^�ɑ��݂��邱��
			String[] partSrchCds = csPartSrchCd.split(",");
			List<PartSrchMst> partSrchMsts = this.partSrchMstDAO.selectByPK(partSrchCds);

			if (partSrchMsts.size() > 0) {
				List<HousingReqPart> housingReqParts = new ArrayList<HousingReqPart>();
				for (PartSrchMst partSrchMst : partSrchMsts) {
					HousingReqPart housingReqPart = buildHousingReqPart();
					housingReqPart.setHousingRequestId(housingRequestId);
					housingReqPart.setPartSrchCd(partSrchMst.getPartSrchCd());
					housingReqParts.add(housingReqPart);
				}

				housingReqPartDAO.insert(housingReqParts.toArray(new HousingReqPart[housingReqParts.size()]));
			}
		}
	}

	/**
	 * �w�肳�ꂽ Form �̒l�ŕ������N�G�X�g�����폜����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * �폜�Ɏg�p����L�[�́AHousingRequestForm�@�� housingRequestId �ƁA�����œn���ꂽ
	 * userId ���g�p���鎖�B  �iuserId �̓��N�G�X�g�p�����[�^�Ŏ擾���Ȃ����B�j<br/>
	 * <br/>
	 * @param userId �}�C�y�[�W���[�U�[ID
	 * @param inputForm ���͂��ꂽ�������N�G�X�g���i�폜�Ώۂ̎�L�[�l���܂ށj
	 * 
	 * 
  	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public void delRequest(String userId, HousingRequestForm inputForm) throws Exception {

		if (StringValidateUtil.isEmpty(userId)) {
			log.warn("userId Is Null or Empty [userId = " + userId + "]");
			return;
		}
		if (StringValidateUtil.isEmpty(inputForm.getHousingRequestId())) {
			log.warn("housingRequestId Is Null or Empty [housingRequestId = " + inputForm.getHousingRequestId() + "]");
			return;
		}

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("housingRequestId", inputForm.getHousingRequestId());

		// �������N�G�X�g�G���A���
		this.housingRequestAreaDAO.deleteByFilter(criteria);
		// �������N�G�X�g�H�����
		this.housingReqRouteDAO.deleteByFilter(criteria);
		// �������N�G�X�g�Ŋ��w���
		this.housingReqStationDAO.deleteByFilter(criteria);
		// �������N�G�X�g������ޏ��
		this.housingReqKindDAO.deleteByFilter(criteria);
		// �������N�G�X�g�Ԏ���
		this.housingReqLayoutDAO.deleteByFilter(criteria);
		// �������N�G�X�g�������������
		this.housingReqPartDAO.deleteByFilter(criteria);

		criteria.addWhereClause("userId", userId);
		// �������N�G�X�g���
		this.housingRequestInfoDAO.deleteByFilter(criteria);

	}

	/**
	 * �w�肳�ꂽ���[�U�[ID �ɊY������A�������N�G�X�g�i���������j�̈ꗗ���擾����B<br/>
	 * <br/>
	 * 
	 * @param userId �}�C�y�[�W���[�U�[ID
	 * @return �擾����
	 * @exception Exception
	 */
	@Override
	public List<HousingRequest> searchRequest(String userId) throws Exception {
		return searchRequest(userId, null);
	}

	/**
	 * �w�肳�ꂽ���[�U�[ID�A�������N�G�X�gID �ɊY������A�������N�G�X�g�i���������j�̈ꗗ���擾����B<br/>
	 * <br/>
	 * 
	 * @param userId �}�C�y�[�W���[�U�[ID
	 * @param housingRequestId �������N�G�X�gID
	 * @return �擾����
	 * @throws Exception
	 */
	protected List<HousingRequest> searchRequest(String userId, String housingRequestId) throws Exception {

		List<HousingRequest> results = new ArrayList<HousingRequest>();

		if (StringValidateUtil.isEmpty(userId)) {
			log.warn("userId Is Null or Empty [userId = " + userId + "]");
			return results;
		}

		// �������N�G�X�g��������
		DAOCriteria criteria = createSearchRequestCriteria(userId, housingRequestId);
		List<HousingRequestInfo> housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria);

		if (housingRequestInfos == null || housingRequestInfos.isEmpty()) {
			log.warn("Selected HousingRequestInfo Is Null [userId = " + userId + "]");
			return results;
		}

		for (HousingRequestInfo housingRequestInfo : housingRequestInfos) {
			// �擾�����������N�G�X�g���̕������N�G�X�gID����֘A���郌�R�[�h������

			HousingRequest housingRequest = createHousingRequest();
			housingRequest.setHousingRequestInfo(housingRequestInfo);

			criteria = new DAOCriteria();
			criteria.addWhereClause("housingRequestId", housingRequestInfo.getHousingRequestId());

			// �������N�G�X�g�G���A���
			housingRequest.setHousingRequestAreas(housingRequestAreaDAO.selectByFilter(criteria));
			// �������N�G�X�g�H�����
			housingRequest.setHousingReqRoutes(housingReqRouteDAO.selectByFilter(criteria));
			// �������N�G�X�g�Ŋ��w���
			housingRequest.setHousingReqStations(housingReqStationDAO.selectByFilter(criteria));
			// �������N�G�X�g������ޏ��
			housingRequest.setHousingReqKind(housingReqKindDAO.selectByFilter(criteria));
			// �������N�G�X�g�Ԏ���
			housingRequest.setHousingReqLayouts(housingReqLayoutDAO.selectByFilter(criteria));
			// �������N�G�X�g�������������
			housingRequest.setHousingReqPart(housingReqPartDAO.selectByFilter(criteria));

			results.add(housingRequest);
		}

		return results;
	}

	/**
	 * �������N�G�X�g����������Criteria�𐶐�����<br/>
	 * ���я��͍ŏI�X�V���̍~���Ƃ���<br/>
	 * <br/>
	 * 
	 * @param userId �}�C�y�[�W���[�U�[ID
	 * @param housingRequestId �������N�G�X�gID
	 * @return DAOCriteria
	 */
	protected DAOCriteria createSearchRequestCriteria(String userId, String housingRequestId) {

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("userId", userId);
		if (!StringValidateUtil.isEmpty(housingRequestId)) {
			criteria.addWhereClause("housingRequestId", housingRequestId);
		}
		criteria.addOrderByClause("updDate", false);

		return criteria;

	}

	/**
	 * �w�肳�ꂽ���[�U�[ID ����������A�������N�G�X�g�i���������j�̐����擾����B<br/>
	 * <br/>
	 * @param userId �}�C�y�[�W���[�U�[ID
	 * 
	 * @return �Y������
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public int searchRequestCnt(String userId) throws Exception {

		if (StringValidateUtil.isEmpty(userId)) {
			log.warn("userId Is Null or Empty [userId = " + userId + "]");
			return 0;
		}

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("userId", userId);
		List<HousingRequestInfo> housingRequestInfos = this.housingRequestInfoDAO.selectByFilter(criteria);

		return housingRequestInfos.size();
	}

	/**
	 * �w�肳�ꂽ���������i���N�G�X�gID�j�ɊY�����镨���̏����擾����B<br/>
	 * <br/>
	 * @param userId �}�C�y�[�W���[�U�[ID
	 * @param requestId ���N�G�X�gID
	 * 
	 * @return �Y������
	 * 
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public int searchHousing(String userId, RequestSearchForm searchForm) throws Exception {

		if (StringValidateUtil.isEmpty(userId)) {
			log.warn("userId Is Null or Empty [userId = " + userId + "]");
			return 0;
		}
		if (StringValidateUtil.isEmpty(searchForm.getHousingRequestId())) {
			log.warn("housingRequestId Is Null or Empty [housingRequestId = " + searchForm.getHousingRequestId() + "]");
			return 0;
		}

		List<HousingRequest> requests = searchRequest(userId, searchForm.getHousingRequestId());
		if (requests == null || requests.isEmpty()) {
			log.warn("Selected HousingRequestInfo Is Null [userId = " + userId + ", housingRequestId = "+ searchForm.getHousingRequestId() +"]");
		}

		HousingRequest request = requests.get(0);

		HousingSearchForm housingSearchForm = this.housingFormFactory.createHousingSearchForm();

		// �������N�G�X�g���
		HousingRequestInfo requestInfo = request.getHousingRequestInfo();
		// ����/���i�E����
		if (requestInfo.getPriceLower() != null) {
			housingSearchForm.setKeyPriceLower(requestInfo.getPriceLower());
		}
		// ����/���i�E���
		if (requestInfo.getPriceUpper() != null) {
			housingSearchForm.setKeyPriceUpper(requestInfo.getPriceUpper());
		}
		// ��L�ʐρE����
		if (requestInfo.getPersonalAreaLower() != null) {
			housingSearchForm.setKeyPersonalAreaLower(requestInfo.getPersonalAreaLower());
		}
		// ��L�ʐρE���
		if (requestInfo.getPersonalAreaUpper() != null) {
			housingSearchForm.setKeyPersonalAreaUpper(requestInfo.getPersonalAreaUpper());
		}

		// �������N�G�X�g�G���A���
		StringBuffer sbPref = new StringBuffer();
		StringBuffer sbAddress = new StringBuffer();
		List<HousingRequestArea> requestAreas = request.getHousingRequestAreas();
		for (HousingRequestArea requestArea : requestAreas) {
			// �s���{��CD
			if (!StringValidateUtil.isEmpty(requestArea.getPrefCd())) {
				if (sbPref.length() > 0) {
					sbPref.append(",");
				}
				sbPref.append(requestArea.getPrefCd());
			}
			// �s�撬��CD
			if (!StringValidateUtil.isEmpty(requestArea.getAddressCd())) {
				if (sbAddress.length() > 0) {
					sbAddress.append(",");
				}
				sbAddress.append(requestArea.getAddressCd());
			}
		}
		housingSearchForm.setKeyPrefCd(sbPref.toString());
		housingSearchForm.setKeyAddressCd(sbAddress.toString());

		// �������N�G�X�g�H�����
		StringBuffer sbRoute = new StringBuffer();
		List<HousingReqRoute> requestRoutes = request.getHousingReqRoutes();
		for (HousingReqRoute requestRoute : requestRoutes) {
			// �H��CD
			if (!StringValidateUtil.isEmpty(requestRoute.getRouteCd())) {
				if (sbRoute.length() > 0) {
					sbRoute.append(",");
				}
				sbRoute.append(requestRoute.getRouteCd());
			}
		}
		housingSearchForm.setKeyRouteCd(sbRoute.toString());

		// �������N�G�X�g�Ŋ��w���
		StringBuffer sbStation = new StringBuffer();
		List<HousingReqStation> requestStations = request.getHousingReqStations();
		for (HousingReqStation requestStation : requestStations) {
			// �H��CD
			if (!StringValidateUtil.isEmpty(requestStation.getStationCd())) {
				if (sbStation.length() > 0) {
					sbStation.append(",");
				}
				sbStation.append(requestStation.getStationCd());
			}
		}
		housingSearchForm.setKeyStationCd(sbStation.toString());

		// �������N�G�X�g������ޏ��
		StringBuffer sbHousingKind = new StringBuffer();
		List<HousingReqKind> requestKinds = request.getHousingReqKinds();
		for (HousingReqKind requestKind : requestKinds) {
			// �H��CD
			if (!StringValidateUtil.isEmpty(requestKind.getHousingKindCd())) {
				if (sbHousingKind.length() > 0) {
					sbHousingKind.append(",");
				}
				sbHousingKind.append(requestKind.getHousingKindCd());
			}
		}
		housingSearchForm.setKeyHousingKindCd(sbHousingKind.toString());

		// �������N�G�X�g�Ԏ����
		StringBuffer sbLayout = new StringBuffer();
		List<HousingReqLayout> requestLayouts = request.getHousingReqLayouts();
		for (HousingReqLayout requestLayout : requestLayouts) {
			// �H��CD
			if (!StringValidateUtil.isEmpty(requestLayout.getLayoutCd())) {
				if (sbLayout.length() > 0) {
					sbLayout.append(",");
				}
				sbLayout.append(requestLayout.getLayoutCd());
			}
		}
		housingSearchForm.setKeyLayoutCd(sbLayout.toString());

		// �������N�G�X�g�������������
		StringBuffer sbPartSrch = new StringBuffer();
		List<HousingReqPart> requestParts = request.getHousingReqParts();
		for (HousingReqPart requestPart : requestParts) {
			// �H��CD
			if (!StringValidateUtil.isEmpty(requestPart.getPartSrchCd())) {
				if (sbPartSrch.length() > 0) {
					sbPartSrch.append(",");
				}
				sbPartSrch.append(requestPart.getPartSrchCd());
			}
		}
		housingSearchForm.setKeyPartSrchCd(sbPartSrch.toString());

		// �������N�G�X�g����Form���ێ�����PagingListForm�̃v���p�e�B�l�𕨌���񌟍�Form�ɃR�s�[����
		housingSearchForm.setRowsPerPage(searchForm.getRowsPerPage());
		housingSearchForm.setVisibleNavigationPageCount(searchForm.getVisibleNavigationPageCount());
		housingSearchForm.setSelectedPage(searchForm.getSelectedPage());

		int cnt = this.housingManager.searchHousing(housingSearchForm);

		// ������񌟍�Form���󂯎���������ꗗ�𕨌����N�G�X�g����Form�ɃR�s�[����
		searchForm.setRows(housingSearchForm.getRows());

		return cnt;
	}

	/**
	 * �������N�G�X�g���p�̃o���[�I�u�W�F�N�g���쐬����t�@�N�g���[���\�b�h<br/>
	 * <br/>
	 * @return HousingRequestInfo ���p�������������N�G�X�g���I�u�W�F�N�g
	 */
	protected HousingRequestInfo buildHousingRequestInfo() {

		// �d�v
		// �������C�ɓ�����p�e�[�u���� HousingRequestInfo �ȊO�̃I�u�W�F�N�g�ɕύX�����ꍇ�A
		// ���̃��\�b�h��K�؂ȃo���[�I�u�W�F�N�g�𐶐�����l�ɃI�[�o�[���C�h���鎖�B

		return (HousingRequestInfo) this.valueObjectFactory.getValueObject("HousingRequestInfo"); 
	}

	/**
	 * �������N�G�X�g�G���A���p�̃o���[�I�u�W�F�N�g���쐬����t�@�N�g���[���\�b�h<br/>
	 * <br/>
	 * @return HousingRequestArea ���p�������������N�G�X�g�G���A���I�u�W�F�N�g
	 */
	protected HousingRequestArea buildHousingRequestArea() {

		// �d�v
		// �����������N�G�X�g�G���A���e�[�u���� HousingRequestArea �ȊO�̃I�u�W�F�N�g�ɕύX�����ꍇ�A
		// ���̃��\�b�h��K�؂ȃo���[�I�u�W�F�N�g�𐶐�����l�ɃI�[�o�[���C�h���鎖�B

		return (HousingRequestArea) this.valueObjectFactory.getValueObject("HousingRequestArea"); 
	}

	/**
	 * �������N�G�X�g�H�����p�̃o���[�I�u�W�F�N�g���쐬����t�@�N�g���[���\�b�h<br/>
	 * <br/>
	 * @return HousingReqRoute ���p�������������N�G�X�g�H�����I�u�W�F�N�g
	 */
	protected HousingReqRoute buildHousingReqRoute() {

		// �d�v
		// �����������N�G�X�g�H�����e�[�u���� HousingReqRoute �ȊO�̃I�u�W�F�N�g�ɕύX�����ꍇ�A
		// ���̃��\�b�h��K�؂ȃo���[�I�u�W�F�N�g�𐶐�����l�ɃI�[�o�[���C�h���鎖�B

		return (HousingReqRoute) this.valueObjectFactory.getValueObject("HousingReqRoute"); 
	}

	/**
	 * �������N�G�X�g�Ŋ�w���p�̃o���[�I�u�W�F�N�g���쐬����t�@�N�g���[���\�b�h<br/>
	 * <br/>
	 * @return HousingReqStation ���p�������������N�G�X�g�Ŋ�w���I�u�W�F�N�g
	 */
	protected HousingReqStation buildHousingReqStation() {

		// �d�v
		// �����������N�G�X�g�Ŋ�w���p�� HousingReqStation �ȊO�̃I�u�W�F�N�g�ɕύX�����ꍇ�A
		// ���̃��\�b�h��K�؂ȃo���[�I�u�W�F�N�g�𐶐�����l�ɃI�[�o�[���C�h���鎖�B

		return (HousingReqStation) this.valueObjectFactory.getValueObject("HousingReqStation"); 
	}

	/**
	 * �������N�G�X�g������ޏ��p�̃o���[�I�u�W�F�N�g���쐬����t�@�N�g���[���\�b�h<br/>
	 * <br/>
	 * @return HousingReqKind ���p�������������N�G�X�g������ޏ��I�u�W�F�N�g
	 */
	protected HousingReqKind buildHousingReqKind() {

		// �d�v
		// �����������N�G�X�g������ޏ��e�[�u���� HousingReqKind �ȊO�̃I�u�W�F�N�g�ɕύX�����ꍇ�A
		// ���̃��\�b�h��K�؂ȃo���[�I�u�W�F�N�g�𐶐�����l�ɃI�[�o�[���C�h���鎖�B

		return (HousingReqKind) this.valueObjectFactory.getValueObject("HousingReqKind"); 
	}

	/**
	 * �������N�G�X�g�Ԏ���p�̃o���[�I�u�W�F�N�g���쐬����t�@�N�g���[���\�b�h<br/>
	 * <br/>
	 * @return HousingReqLayout ���p�������������N�G�X�g�Ԏ���I�u�W�F�N�g
	 */
	protected HousingReqLayout buildHousingReqLayout() {

		// �d�v
		// �����������N�G�X�g�Ԏ���e�[�u���� HousingReqLayout �ȊO�̃I�u�W�F�N�g�ɕύX�����ꍇ�A
		// ���̃��\�b�h��K�؂ȃo���[�I�u�W�F�N�g�𐶐�����l�ɃI�[�o�[���C�h���鎖�B

		return (HousingReqLayout) this.valueObjectFactory.getValueObject("HousingReqLayout"); 
	}

	/**
	 * �������N�G�X�g�������������p�̃o���[�I�u�W�F�N�g���쐬����t�@�N�g���[���\�b�h<br/>
	 * <br/>
	 * @return HousingReqPart ���p�������������N�G�X�g�������������I�u�W�F�N�g
	 */
	protected HousingReqPart buildHousingReqPart() {

		// �d�v
		// �����������N�G�X�g�����������e�[�u���� HousingReqPart �ȊO�̃I�u�W�F�N�g�ɕύX�����ꍇ�A
		// ���̃��\�b�h��K�؂ȃo���[�I�u�W�F�N�g�𐶐�����l�ɃI�[�o�[���C�h���鎖�B

		return (HousingReqPart) this.valueObjectFactory.getValueObject("HousingReqPart"); 
	}

	/**
	 * �������N�G�X�g���I�u�W�F�N�g�̃C���X�^���X�𐶐�����B<br/>
	 * �����A�J�X�^�}�C�Y�ō\������e�[�u����ǉ������ꍇ�A���̃��\�b�h���I�[�o�[���C�h���鎖�B<br/>
	 * <br/>
	 * @return HousingRequest �̃C���X�^���X
	 */
	protected HousingRequest createHousingRequest() {
		return new HousingRequest();
	}
}
