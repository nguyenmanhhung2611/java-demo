package jp.co.transcosmos.dm3.core.model.housingRequest;

import java.util.ArrayList;
import java.util.List;

import jp.co.transcosmos.dm3.core.vo.HousingReqKind;
import jp.co.transcosmos.dm3.core.vo.HousingReqLayout;
import jp.co.transcosmos.dm3.core.vo.HousingReqPart;
import jp.co.transcosmos.dm3.core.vo.HousingReqRoute;
import jp.co.transcosmos.dm3.core.vo.HousingReqStation;
import jp.co.transcosmos.dm3.core.vo.HousingRequestArea;
import jp.co.transcosmos.dm3.core.vo.HousingRequestInfo;


/**
 * <pre>
 * �������N�G�X�g���N���X
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.11	�V�K�쐬
 *
 * ���ӎ���
 * Model �ȊO���璼�ڃC���X�^���X�𐶐����Ȃ����B
 *
 * </pre>
 */
public class HousingRequest {

	/** �������N�G�X�g��� */
	private HousingRequestInfo housingRequestInfo;
	/** �������N�G�X�g�G���A���̃��X�g */
	private List<HousingRequestArea> housingRequestAreas = new ArrayList<>();
	/** �������N�G�X�g�Ԏ����̃��X�g */
	private List<HousingReqLayout> housingReqLayouts = new ArrayList<>();
	/** �������N�G�X�g�H�����̃��X�g */
	private List<HousingReqRoute> housingReqRoutes = new ArrayList<>();
	/** �������N�G�X�g�Ŋ��w���̃��X�g*/
	private List<HousingReqStation> housingReqStations = new ArrayList<>();
	/** �������N�G�X�g������ޏ��̃��X�g */
	private List<HousingReqKind> housingReqKinds = new ArrayList<>();
	/** �������N�G�X�g�������������̃��X�g */
	private List<HousingReqPart> housingReqParts = new ArrayList<>();

	
	
	/**
	 * �R���X�g���N�^�[<br/>
	 * �������N�G�X�g�̃��f���ȊO����C���X�^���X�𐶐��o���Ȃ��l�ɃR���X�g���N�^�𐧌�����B<br/>
	 * <br/>
	 */
	protected HousingRequest() {
		super();
	}



	/**
	 * �������N�G�X�g�����擾����B<br/>
	 * <br/>
	 * @return �������N�G�X�g���
	 */
	public HousingRequestInfo getHousingRequestInfo() {
		return housingRequestInfo;
	}

	/**
	 * �������N�G�X�g����ݒ肷��B<br/>
	 * <br/>
	 * @param housingRequestInfo �������N�G�X�g���
	 */
	public void setHousingRequestInfo(HousingRequestInfo housingRequestInfo) {
		this.housingRequestInfo = housingRequestInfo;
	}

	/**
	 * �������N�G�X�g�G���A���̃��X�g���擾����B<br/>
	 * <br/>
	 * @return �������N�G�X�g�G���A���̃��X�g
	 */
	public List<HousingRequestArea> getHousingRequestAreas() {
		return housingRequestAreas;
	}

	/**
	 * �������N�G�X�g�G���A���̃��X�g��ݒ肷��B<br/>
	 * <br/>
	 * @param housingRequestAreas �������N�G�X�g�G���A���
	 */
	public void setHousingRequestAreas(List<HousingRequestArea> housingRequestAreas) {
		this.housingRequestAreas = housingRequestAreas;
	}
	
	/**
	 * �������N�G�X�g�Ԏ����̃��X�g���擾����B<br/>
	 * <br/>
	 * @return �������N�G�X�g�Ԏ����̃��X�g
	 */
	public List<HousingReqLayout> getHousingReqLayouts() {
		return housingReqLayouts;
	}
	
	/**
	 * �������N�G�X�g�Ԏ����̃��X�g��ݒ肷��B<br/>
	 * <br/>
	 * @param housingReqLayouts �������N�G�X�g�Ԏ����̃��X�g
	 */
	public void setHousingReqLayouts(List<HousingReqLayout> housingReqLayouts) {
		this.housingReqLayouts = housingReqLayouts;
	}

	/**
	 * �������N�G�X�g�H�����̃��X�g���擾����B<br/>
	 * <br/>
	 * @return �������N�G�X�g�H�����̃��X�g
	 */
	public List<HousingReqRoute> getHousingReqRoutes() {
		return housingReqRoutes;
	}

	/**
	 * �������N�G�X�g�H�����̃��X�g��ݒ肷��B<br/>
	 * <br/>
	 * @param housingReqRoutes �������N�G�X�g�H�����̃��X�g
	 */
	public void setHousingReqRoutes(List<HousingReqRoute> housingReqRoutes) {
		this.housingReqRoutes = housingReqRoutes;
	}

	/**
	 * �������N�G�X�g�Ŋ��w���̃��X�g���擾����B<br/>
	 * <br/>
	 * @return �������N�G�X�g�Ŋ��w���̃��X�g
	 */
	public List<HousingReqStation> getHousingReqStations() {
		return housingReqStations;
	}
	
	/**
	 * �������N�G�X�g�Ŋ��w���̃��X�g��ݒ肷��B<br/>
	 * <br/>
	 * @param housingReqStations �������N�G�X�g�Ŋ��w���̃��X�g
	 */
	public void setHousingReqStations(List<HousingReqStation> housingReqStations) {
		this.housingReqStations = housingReqStations;
	}

	/**
	 * �������N�G�X�g������ޏ��̃��X�g���擾����B<br/>
	 * <br/>
	 * @return �������N�G�X�g������ޏ��̃��X�g
	 */
	public List<HousingReqKind> getHousingReqKinds() {
		return housingReqKinds;
	}
	
	/**
	 * �������N�G�X�g������ޏ��̃��X�g��ݒ肷��B<br/>
	 * <br/>
	 * @param housingReqKinds �������N�G�X�g������ޏ��̃��X�g
	 */
	public void setHousingReqKind(List<HousingReqKind> housingReqKinds) {
		this.housingReqKinds = housingReqKinds;
	}
	
	/**
	 * �������N�G�X�g�������������̃��X�g���擾����B<br/>
	 * <br/>
	 * @return �������N�G�X�g�������������̃��X�g
	 */
	public List<HousingReqPart> getHousingReqParts() {
		return housingReqParts;
	}

	/**
	 * �������N�G�X�g�������������̃��X�g��ݒ肷��B<br/>
	 * <br/>
	 * @param housingReqParts �������N�G�X�g�������������̃��X�g
	 */
	public void setHousingReqPart(List<HousingReqPart> housingReqParts) {
		this.housingReqParts = housingReqParts;
	}

}
