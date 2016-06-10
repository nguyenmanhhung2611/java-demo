package jp.co.transcosmos.dm3.corePana.model;

import java.util.List;

import jp.co.transcosmos.dm3.core.vo.AddressMst;
import jp.co.transcosmos.dm3.core.vo.EquipMst;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.RrMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;

/**
 * ���ʏ��擾���� Model �N���X�p�C���^�[�t�F�[�X.
 * <p>
 * ���ʏ��擾���� model �N���X�͂��̃C���^�[�t�F�[�X���������鎖�B<br/>
 * <p>
 *
 * <pre>
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * TRANS		2015.03.30	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B<br/>
 *
 */
public interface PanaCommonManage {

	/**
	 * �s���{�����X�g�̎擾<br/>
	 *
	 * @return �s���{�����X�g
	 * @throws Exception
	 */
	public List<PrefMst> getPrefMstList() throws Exception;

	/**
	 * �s���{��CD�ɂ��A�s�撬����񃊃X�g�̎擾<br/>
	 *
	 * @param PrefCd
	 *            �s���{��CD
	 * @return �s�撬����񃊃X�g
	 * @throws Exception
	 */
	public List<AddressMst> getPrefCdToAddressMstList(String prefCd)
			throws Exception;

	/**
	 * �s���{��CD�ɂ��A������񃊃X�g�̎擾<br/>
	 *
	 * @param PrefCd
	 *            �s���{��CD
	 * @return ������񃊃X�g
	 * @throws Exception
	 */
	public List<RouteMst> getPrefCdToRouteMstList(String prefCd)
			throws Exception;

	/**
	 * ����CD�ɂ��A�w��񃊃X�g�擾<br/>
	 *
	 * @param RouteCd
	 *            ����CD
	 * @return �w��񃊃X�g
	 * @throws Exception
	 */
	public List<StationMst> getRouteCdToStationMstList(String routeCd)
			throws Exception;

	public List<RouteMst> getPrefCdToRouteMstList(String prefCd,String rrCd)
			throws Exception;

	/**
	 * �S����Ѓ��X�g�̎擾
	 *
	 * @param routeCd
	 *            �H��CD
	 * @return �H����
	 * @throws Exception
	 */
	public List<RrMst> getPrefCdToRrMstList(String prefCd)
			throws Exception;
	/**
	 * �s���{�����̎擾<br/>
	 *
	 * @param prefCd
	 *            �s���{��CD
	 * @return �s���{����
	 * @throws Exception
	 */
	public String getPrefName(String prefCd) throws Exception;

	/**
	 * �s�撬�����̎擾<br/>
	 *
	 * @param addressCd
	 *            �s�撬��CD
	 * @return �s�撬����
	 * @throws Exception
	 */
	public String getAddressName(String addressCd) throws Exception;

	/**
	 * �H�����̎擾
	 *
	 * @param routeCd
	 *            �H��CD
	 * @return �H����
	 * @throws Exception
	 */
	public String getRouteName(String routeCd) throws Exception;

	/**
	 * �H�����E�J�b�R�t�̎擾
	 *
	 * @param routeCd
	 *            �H��CD
	 * @return �H����
	 * @throws Exception
	 */
	public String getRouteNameFull(String routeCd) throws Exception;

	/**
	 * �S����Ж�+�H�����E�J�b�R�t�擾
	 *
	 * @param routeCd
	 *            �H��CD
	 * @return �H����
	 * @throws Exception
	 */
	public String getRrNameRouteFull(String routeCd) throws Exception;

	/**
	 * �w���̎擾
	 *
	 * @param stationCd
	 *            �wCD
	 * @return �w��
	 * @throws Exception
	 */
	public String getStationName(String stationCd) throws Exception;

	/**
	 * �X�֔ԍ��ɂ��A�s���{���A�s�撬���擾
	 *
	 * @param zip
	 *            �X�֔ԍ�
	 * @return 0:�s���{��CD+�s�撬��CD<br/>
	 *         1:�X�֔ԍ��͓��͂��Ă��������B<br/>
	 *         2:�Y���X�֔ԍ��̏Z���͂���܂���B
	 * @throws Exception
	 */
	public String[] getZipToAddress(String zip) throws Exception;

	/**
	 * �ݔ���񃊃X�g�̎擾<br/>
	 *
	 * @return �ݔ���񃊃X�g
	 * @throws Exception
	 */
	public List<EquipMst> getEquipMstList() throws Exception;

	/**
	 * �H�����E�S����Еt�̎擾
	 *
	 * @param routeCd
	 *            �H��CD
	 * @return �H����
	 * @throws Exception
	 */
	public String getRouteNameRr(String routeCd) throws Exception;
	
}
