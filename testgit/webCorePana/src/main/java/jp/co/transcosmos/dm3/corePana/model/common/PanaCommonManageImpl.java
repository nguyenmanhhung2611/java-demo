package jp.co.transcosmos.dm3.corePana.model.common;

import java.util.ArrayList;
import java.util.List;

import jp.co.transcosmos.dm3.core.vo.AddressMst;
import jp.co.transcosmos.dm3.core.vo.EquipMst;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.RrMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.core.vo.StationRouteInfo;
import jp.co.transcosmos.dm3.core.vo.ZipMst;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.building.dao.PanaRouteMstListDAO;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ���ʏ��擾���� Model �N���X.
 * <p>
 * ���ʏ��擾���� model �N���X�͂��̃C���^�[�t�F�[�X���������鎖�B<br/>
 * <p>
 *
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * TRANS		2015.03.30	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B<br/>
 *
 */
public class PanaCommonManageImpl implements PanaCommonManage {
	private static final Log log = LogFactory
			.getLog(PanaCommonManageImpl.class);

	private static String _MESSAGE_ZIP_TO_ADDRESS_1 = "�X�֔ԍ�����͂��Ă��������B";
	private static String _MESSAGE_ZIP_TO_ADDRESS_2 = "�X�֔ԍ����m�F���Ă��������B";

	/** �s���{���}�X�^��� **/
	private DAO<PrefMst> prefMstDAO;
	
	/** �s�撬���}�X�^��� **/
	private DAO<AddressMst> addressMstDAO;

	/** �H���}�X�^��� **/
	private DAO<RouteMst> routeMstDAO;

	/** �S����Ѓ}�X�^��� **/
	private DAO<RrMst> rrMstDAO;

	/** �w���}�X�^��� **/
	private DAO<StationMst> stationMstDAO;

	/** �X�֔ԍ��}�X�^ **/
	private DAO<ZipMst> zipMstDAO;

	/** �w�E�H���Ή���� */
	private DAO<StationRouteInfo> stationRouteInfoDAO;

	/** �H���}�X�^�擾 */
	private PanaRouteMstListDAO routeMstListDAO;

	/** �ݔ����}�X�^��� **/
	private DAO<EquipMst> equipMstDAO;

	/**
	 * @param stationRouteInfoDAO
	 *            �Z�b�g���� stationRouteInfoDAO
	 */
	public void setStationRouteInfoDAO(DAO<StationRouteInfo> stationRouteInfoDAO) {
		this.stationRouteInfoDAO = stationRouteInfoDAO;
	}

	/**
	 * @param routeMstListDAO
	 *            �Z�b�g���� routeMstListDAO
	 */
	public void setPanaRouteMstListDAO(PanaRouteMstListDAO routeMstListDAO) {
		this.routeMstListDAO = routeMstListDAO;
	}

	/**
	 * @param prefMstDAO
	 *            �Z�b�g���� prefMstDAO
	 */
	public void setPrefMstDAO(DAO<PrefMst> prefMstDAO) {
		this.prefMstDAO = prefMstDAO;
	}

	/**
	 * @param addressMstDAO
	 *            �Z�b�g���� addressMstDAO
	 */
	public void setAddressMstDAO(DAO<AddressMst> addressMstDAO) {
		this.addressMstDAO = addressMstDAO;
	}

	/**
	 * @param routeMstDAO
	 *            �Z�b�g���� routeMstDAO
	 */
	public void setRouteMstDAO(DAO<RouteMst> routeMstDAO) {
		this.routeMstDAO = routeMstDAO;
	}

	/**
	 * @param rrMstDAO
	 *            �Z�b�g���� rrMstDAO
	 */
	public void setRrMstDAO(DAO<RrMst> rrMstDAO) {
		this.rrMstDAO = rrMstDAO;
	}

	/**
	 * @param stationMstDAO
	 *            �Z�b�g���� stationMstDAO
	 */
	public void setStationMstDAO(DAO<StationMst> stationMstDAO) {
		this.stationMstDAO = stationMstDAO;
	}

	/**
	 * @param zipMstDAO
	 *            �Z�b�g���� zipMstDAO
	 */
	public void setZipMstDAO(DAO<ZipMst> zipMstDAO) {
		this.zipMstDAO = zipMstDAO;
	}

	/**
	 * @param equipMstDAO
	 *            �Z�b�g���� equipMstDAO
	 */
	public void setEquipMstDAO(DAO<EquipMst> equipMstDAO) {
		this.equipMstDAO = equipMstDAO;
	}

	/**
	 * �s���{�����X�g�̎擾<br/>
	 *
	 * @return �s���{�����X�g
	 * @throws Exception
	 */
	public List<PrefMst> getPrefMstList() throws Exception {
		log.debug(">>>PanaCommonManageImpl.getPrefMstList()<<<");
		DAOCriteria paramDAOCriteria = new DAOCriteria();
		paramDAOCriteria.addOrderByClause("sortOrder");
		return this.prefMstDAO.selectByFilter(paramDAOCriteria);
	}

	/**
	 * �s���{��CD�ɂ��A�s�撬����񃊃X�g�̎擾<br/>
	 *
	 * @param PrefCd
	 *            �s���{��CD
	 * @return �s�撬����񃊃X�g
	 * @throws Exception
	 */
	public List<AddressMst> getPrefCdToAddressMstList(String prefCd)
			throws Exception {
		log.debug(">>>PanaCommonManageImpl.getPrefCdToAddressMstList(String prefCd)<<<");
		log.debug(">>>prefCd is : " + prefCd + "<<<");

		DAOCriteria paramDAOCriteria = new DAOCriteria();
		List<AddressMst> addressMstList = new ArrayList<AddressMst>();

		if (null != prefCd) {
			paramDAOCriteria.addWhereClause("prefCd", prefCd);
			paramDAOCriteria.addOrderByClause("prefCd");
			addressMstList = this.addressMstDAO
					.selectByFilter(paramDAOCriteria);
		}

		return addressMstList;
	}

	/**
	 * �s���{��CD�ɂ��A������񃊃X�g�̎擾<br/>
	 *
	 * @param PrefCd
	 *            �s���{��CD
	 * @return ������񃊃X�g
	 * @throws Exception
	 */
	public List<RouteMst> getPrefCdToRouteMstList(String prefCd)
			throws Exception {
		log.debug(">>>PanaCommonManageImpl.getPrefCdToRouteMstList(String prefCd)<<<");
		log.debug(">>>prefCd is : " + prefCd + "<<<");

		List<RouteMst> routeMstList = new ArrayList<RouteMst>();

		Object[] params = new String[] { prefCd };
		routeMstList = routeMstListDAO.listRouteMst(params);

		return routeMstList;
	}
	public List<RouteMst> getPrefCdToRouteMstList(String prefCd,String rrCd)
			throws Exception {
		log.debug(">>>PanaCommonManageImpl.getPrefCdToRouteMstList(String prefCd)<<<");
		log.debug(">>>prefCd is : " + prefCd + "<<<");

		List<RouteMst> routeMstList = new ArrayList<RouteMst>();

		Object[] params = new String[] { prefCd,rrCd };
		routeMstList = routeMstListDAO.listRouteMst2(params);

		return routeMstList;
	}

	/**
	 * �S����Ѓ��X�g�̎擾
	 *
	 * @param PrefCd
	 *            �s���{��CD
	 * @return �S����Ж�
	 * @throws Exception
	 */
	public List<RrMst> getPrefCdToRrMstList(String prefCd)
			throws Exception {
		log.debug(">>>PanaCommonManageImpl.getPrefCdToRouteMstList(String prefCd)<<<");
		log.debug(">>>prefCd is : " + prefCd + "<<<");

		List<RrMst> rrMstList = new ArrayList<RrMst>();

		Object[] params = new String[] { prefCd };
		rrMstList = routeMstListDAO.listRrMst(params);

		return rrMstList;
	}

	/**
	 * ����CD�ɂ��A�w��񃊃X�g�擾<br/>
	 *
	 * @param RouteCd
	 *            ����CD
	 * @return �w��񃊃X�g
	 * @throws Exception
	 */
	public List<StationMst> getRouteCdToStationMstList(String routeCd)
			throws Exception {
		log.debug(">>>PanaCommonManageImpl.getRouteCdToStationMstList(String routeCd)<<<");
		log.debug(">>>routeCd is : " + routeCd + "<<<");

		DAOCriteria paramDAOCriteria = new DAOCriteria();
		List<StationMst> stationMstList = new ArrayList<StationMst>();// �w��񃊃X�g
		List<StationRouteInfo> stationRouteInfoList;
		StationMst stationMst;
		StationRouteInfo stationRouteInfo;

		if (null != routeCd) {
			paramDAOCriteria.addWhereClause("routeCd", routeCd);
			paramDAOCriteria.addOrderByClause("sortOrder");

			stationRouteInfoList = this.stationRouteInfoDAO
					.selectByFilter(paramDAOCriteria);

			if (null != stationRouteInfoList && stationRouteInfoList.size() > 0) {
				for (int i = 0; i < stationRouteInfoList.size(); i++) {
					stationRouteInfo = stationRouteInfoList.get(i);

					if (null != stationRouteInfo) {
						stationMst = stationMstDAO.selectByPK(stationRouteInfo
								.getStationCd());

						if (null != stationMst) {
							stationMstList.add(stationMst);
						}
					}
				}
			}

		}

		return stationMstList;
	}

	/**
	 * �s���{�����̎擾<br/>
	 *
	 * @param prefCd
	 *            �s���{��CD
	 * @return �s���{����
	 * @throws Exception
	 */
	public String getPrefName(String prefCd) throws Exception {
		log.debug(">>>PanaCommonManageImpl.getPrefName(String prefCd)<<<");
		log.debug(">>>prefCd is : " + prefCd + "<<<");

		String prefName = "";

		if (null != prefCd) {
			PrefMst prefMst = this.prefMstDAO.selectByPK(prefCd);

			if (null != prefMst) {
				prefName = prefMst.getPrefName();
			}
		}

		return prefName;
	}

	/**
	 * �s�撬�����̎擾<br/>
	 *
	 * @param addressCd
	 *            �s�撬��CD
	 * @return �s�撬����
	 * @throws Exception
	 */
	public String getAddressName(String addressCd) throws Exception {
		log.debug(">>>getAddressName(String addressCd)<<<");
		log.debug(">>>addressCd is : " + addressCd + "<<<");

		String addressName = "";

		if (null != addressCd) {
			AddressMst addressMst = this.addressMstDAO.selectByPK(addressCd);

			if (null != addressMst) {
				addressName = addressMst.getAddressName();
			}
		}

		return addressName;
	}

	/**
	 * �H�����̎擾
	 *
	 * @param routeCd
	 *            �H��CD
	 * @return �H����
	 * @throws Exception
	 */
	public String getRouteName(String routeCd) throws Exception {
		log.debug(">>>getRouteName(String routeCd)<<<");
		log.debug(">>>routeCd is : " + routeCd + "<<<");

		String routeName = "";

		if (null != routeCd) {
			RouteMst routeMst = this.routeMstDAO.selectByPK(routeCd);

			if (null != routeMst) {
				routeName = routeMst.getRouteName();
			}
		}

		return routeName;
	}

	/**
	 * �H�����E�J�b�R�t�̎擾
	 *
	 * @param routeCd
	 *            �H��CD
	 * @return �H����
	 * @throws Exception
	 */
	public String getRouteNameFull(String routeCd) throws Exception {
		log.debug(">>>getRouteNameFull(String routeCd)<<<");
		log.debug(">>>routeCd is : " + routeCd + "<<<");

		String routeName = "";

		if (null != routeCd) {
			RouteMst routeMst = this.routeMstDAO.selectByPK(routeCd);

			if (null != routeMst) {
				routeName = routeMst.getRouteNameFull();
			}
		}

		return routeName;
	}

	/**
	 * �H�����E�S����Еt�̎擾
	 *
	 * @param routeCd
	 *            �H��CD
	 * @return �H����
	 * @throws Exception
	 */
	public String getRouteNameRr(String routeCd) throws Exception {
		log.debug(">>>getRouteNameRr(String routeCd)<<<");
		log.debug(">>>routeCd is : " + routeCd + "<<<");

		String routeName = "";

		if (null != routeCd) {
			RouteMst routeMst = this.routeMstDAO.selectByPK(routeCd);

			if (null != routeMst) {
				routeName = routeMst.getRouteNameRr();
			}
		}

		return routeName;
	}

	/**
	 * �S����Ж�+�H�����E�J�b�R�t�擾
	 *
	 * @param routeCd
	 *            �H��CD
	 * @return �H����
	 * @throws Exception
	 */
	public String getRrNameRouteFull(String routeCd) throws Exception {
		log.debug(">>>getRrNameRouteFull(String routeCd)<<<");
		log.debug(">>>routeCd is : " + routeCd + "<<<");

		String rrCd = "";
		String rrName="";
		String routeName = "";
		if (null != routeCd) {
			RouteMst routeMst = this.routeMstDAO.selectByPK(routeCd);

			if (null != routeMst) {
				rrCd = routeMst.getRrCd();
				routeName = routeMst.getRouteNameFull();

				RrMst rrMst = this.rrMstDAO.selectByPK(rrCd);
				if (null != rrMst) {
					rrName = rrMst.getRrName();
				}
			}
		}

		return rrName+routeName;
	}

	/**
	 * �w���̎擾
	 *
	 * @param stationCd
	 *            �wCD
	 * @return �w��
	 * @throws Exception
	 */
	public String getStationName(String stationCd) throws Exception {
		log.debug(">>>getStationName(String stationCd)<<<");
		log.debug(">>>stationCd is : " + stationCd + "<<<");

		String stationName = "";

		if (null != stationCd) {
			StationMst stationMst = this.stationMstDAO.selectByPK(stationCd);

			if (null != stationMst) {
				stationName = stationMst.getStationName();
			}
		}

		return stationName;
	}

	/**
	 * �w���E�J�b�R�̎擾
	 *
	 * @param stationCd
	 *            �wCD
	 * @return �w��
	 * @throws Exception
	 */
	public String getStationNameFull(String stationCd) throws Exception {
		log.debug(">>>getStationNameFull(String stationCd)<<<");
		log.debug(">>>stationCd is : " + stationCd + "<<<");

		String stationName = "";

		if (null != stationCd) {
			StationMst stationMst = this.stationMstDAO.selectByPK(stationCd);

			if (null != stationMst) {
				stationName = stationMst.getStationNameFull();
			}
		}

		return stationName;
	}

	/**
	 * �X�֔ԍ��ɂ��A�s���{���A�s�撬���擾
	 *
	 * @param zip
	 *            �X�֔ԍ�
	 * @return 0:�s���{��CD+�s�撬��CD<br/>
	 *         1:�X�֔ԍ�����͂��Ă��������B<br/>
	 *         2:�X�֔ԍ����m�F���Ă��������B<br/>
	 * @throws Exception
	 */
	public String[] getZipToAddress(String zip) throws Exception {
		log.debug(">>>getZipToAddress(String zip)<<<");
		log.debug(">>>zip is : " + zip + "<<<");

		String[] message = new String[3];

		if (!StringValidateUtil.isEmpty(zip)) {
			ZipMst zipMst = this.zipMstDAO.selectByPK(zip);

			if (null != zipMst) {

				if (!StringValidateUtil.isEmpty(zipMst.getPrefCd())
						&& !StringValidateUtil.isEmpty(zipMst.getAddressCd())) {
					PrefMst prefMst = prefMstDAO.selectByPK(zipMst.getPrefCd());
					AddressMst addressMst = addressMstDAO.selectByPK(zipMst
							.getAddressCd());

					if (!StringValidateUtil.isEmpty(prefMst.getPrefName())
							&& !StringValidateUtil.isEmpty(addressMst
									.getAddressName())) {
						message[0] = "0";
						message[1] = zipMst.getPrefCd();
						message[2] = zipMst.getAddressCd();
					} else {
						message[0] = "2";
						message[1] = _MESSAGE_ZIP_TO_ADDRESS_2;
						message[2] = _MESSAGE_ZIP_TO_ADDRESS_2;
					}
				} else {
					message[0] = "2";
					message[1] = _MESSAGE_ZIP_TO_ADDRESS_2;
					message[2] = _MESSAGE_ZIP_TO_ADDRESS_2;
				}
			} else {
				message[0] = "2";
				message[1] = _MESSAGE_ZIP_TO_ADDRESS_2;
				message[2] = _MESSAGE_ZIP_TO_ADDRESS_2;
			}
		} else {
			message[0] = "1";
			message[1] = _MESSAGE_ZIP_TO_ADDRESS_1;
			message[2] = _MESSAGE_ZIP_TO_ADDRESS_1;
		}

		return message;
	}

	/**
	 * �ݔ���񃊃X�g�̎擾<br/>
	 *
	 * @return �ݔ���񃊃X�g
	 * @throws Exception
	 */
	public List<EquipMst> getEquipMstList() throws Exception {
		log.debug(">>>PanaCommonManageImpl.getEquipMstList()<<<");
		DAOCriteria paramDAOCriteria = new DAOCriteria();
		paramDAOCriteria.addOrderByClause("sortOrder");
		return this.equipMstDAO.selectByFilter(paramDAOCriteria);
	}
	
}
