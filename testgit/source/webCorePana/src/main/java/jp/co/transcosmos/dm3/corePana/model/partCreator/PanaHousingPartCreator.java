package jp.co.transcosmos.dm3.corePana.model.partCreator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jp.co.transcosmos.dm3.core.model.HousingPartCreator;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingPartInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;

/**
 * ������{��񂩂炱���������𐶐�����.
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * �`����		2015.04.14	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B<br/>
 *
 */
public class PanaHousingPartCreator implements HousingPartCreator {

	/** �����������������p DAO */
	private DAO<HousingPartInfo> housingPartInfoDAO;

	/**
	 * �k�����ԁA����������CD �ϊ� map<br/>
	 * Key = �k�����ԁAValue = ����������CD<br/>
	 */
	private Map<String, String> timeToPart;

	/**
	 * �����K���A����������CD �ϊ� map<br/>
	 * Key = �����K���AValue = ����������CD<br/>
	 */
	private Map<String, String> floorToPart;

	/**
	 * �����K��(N�K�ȏ�)�A����������CD �ϊ� map<br/>
	 * Key = �����K��(N�K�ȏ�)�AValue = ����������CD<br/>
	 */
	private Map<String, String> floorUpToPart;

	/**
	 * �A�C�R�����i�������߃|�C���g�j�A����������CD �ϊ� map<br/>
	 * Key = �������߃|�C���g�AValue = ����������CD<br/>
	 */
	private Map<String, String> iconToPart;

	/** ValueObject �� Factory �N���X */
	private ValueObjectFactory valueObjectFactory;

	/** ���̃N���X�̐����ΏۂƂȂ�A����������CD(�k������) �� Set �I�u�W�F�N�g */
	private String myTimePartCds[];

	/** ���̃N���X�̐����ΏۂƂȂ�A����������CD(�����K��) �� Set �I�u�W�F�N�g */
	private String myFloorPartCds[];

	/** ���̃N���X�̐����ΏۂƂȂ�A����������CD(�����K��(N�K�ȏ�)) �� Set �I�u�W�F�N�g */
	private String myFloorUpPartCds[];

	/** ����������CD(�ŏ�K) */
	private String topFloorPartCd = "F99";

	/** ���̃N���X�̐����ΏۂƂȂ�A����������CD(�������߃|�C���g) �� Set �I�u�W�F�N�g */
	private String myIconPartCds[];



	/**
	 * ���̃N���X�̂����������𐶐����郁�\�b�h�𔻒肷��B<br/>
	 * <br/>
	 * @param methodName ���ꂩ����s���郁�\�b�h��
	 * @return ���\�b�h���� addHousing �̏ꍇ�Atrue �𕜋A
	 * @return ���\�b�h���� updateHousing �̏ꍇ�Atrue �𕜋A
	 */
	@Override
	public boolean isExecuteMethod(String methodName) {

		// ������{���V�݁^�X�V�������Ɏ��s����B
		// �����A�J�X�^�}�C�Y���� ���L�֐� ��ʂ̃��\�b�h������s���Ă���
		// �ꍇ�́A�����̃��\�b�h����ł����s�����l�ɕύX���鎖�B
		if ("addHousing".equals(methodName)){
			return true;
		}
		if ("updateHousing".equals(methodName)){
			return true;
		}

		return false;
	}



	/**
	 * �o�^�����������I�u�W�F�N�g���炱���������쐬����B<br/>
	 * <br/>
	 */
	@Override
	public void createPart(Housing housing) throws Exception {

		// �k�����Ԃ����������쐬����B
		createPartTime(housing);

		// �����K�������������쐬����B
		createPartFloor(housing);

		// �������߃|�C���g�����������쐬����B
		createPartIcon(housing);

	}



	/**
	 * �k�����Ԃ����������쐬����B<br/>
	 * <br/>
	 */
	protected void createPartTime(Housing housing) throws Exception {

		// �X�V�ΏۂƂȂ镨����{�����擾����B
		HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");

		// ���݂̂���������CD ���폜����B
		delPartCds(housingInfo.getSysHousingCd(), this.myTimePartCds);

		// �ŏ��k�����Ԗ��o�^�ꍇ�͏����I���B
		if (housingInfo.getMinWalkingTime() == null) return;
		// �ŏ��k�����Ԃ��擾����B
		int minWalkingTime = housingInfo.getMinWalkingTime();

		// �ǉ����邱������������ Map �I�u�W�F�N�g
		// Key = ����������CD�AValue = ���ꂩ��ǉ����镨���������������� Value �I�u�W�F�N�g
		Map<String, HousingPartInfo> addPartMap = new HashMap<>();

		// �k�����ԁA����������CD �ϊ� map�����[�v���A������{���̍ŏ��k�����ԂƔ�r����B
		for (Entry<String, String> e : this.timeToPart.entrySet()){
			if (minWalkingTime > Integer.parseInt(e.getKey())) continue;
			String partCd = e.getValue();
			if (!StringValidateUtil.isEmpty(partCd)){

				// �ŏ��k������ ���ϊ��e�[�u���ɓk�����Ԉȉ�����ꍇ�A�ǉ����邱������������ Map �ɁA
				// ���������������݂��邩���`�F�b�N����B
				// �������݂���ꍇ�͓o�^�ςȂ̂ŁA���̃f�[�^����������B
				if (addPartMap.get(partCd) == null){
					addPartMap.put(partCd, createHousingPartInfo(housingInfo, partCd));
				}
			}
		}

		// ������������ǉ�����B
		this.housingPartInfoDAO.insert(addPartMap.values().toArray(new HousingPartInfo[addPartMap.size()]));
	}



	/**
	 * �����K�������������쐬����B<br/>
	 * <br/>
	 */
	protected void createPartFloor(Housing housing) throws Exception {

		// �X�V�ΏۂƂȂ镨����{�����擾����B
		HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");
		BuildingInfo buildingInfo = (BuildingInfo) housing.getBuilding().getBuildingInfo().getItems().get("buildingInfo");

		// ���݂̂���������CD ���폜����B
		delPartCds(housingInfo.getSysHousingCd(), this.myFloorPartCds);
		delPartCds(housingInfo.getSysHousingCd(), this.myFloorUpPartCds);

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", housingInfo.getSysHousingCd());
		criteria.addWhereClause("partSrchCd", this.topFloorPartCd);
		this.housingPartInfoDAO.deleteByFilter(criteria);

		// �����K�����o�^�ꍇ�͏����I���B
		if (housingInfo.getFloorNo() == null) return;
		// �����K�� ���K�� ���擾����B
		int housingFloor = housingInfo.getFloorNo();
		int buildingFloor = 0;
		if (buildingInfo.getTotalFloors() != null) buildingFloor = buildingInfo.getTotalFloors();

		// �ǉ����邱������������ Map �I�u�W�F�N�g
		// Key = ����������CD�AValue = ���ꂩ��ǉ����镨���������������� Value �I�u�W�F�N�g
		Map<String, HousingPartInfo> addPartMap = new HashMap<>();

		// �����K���A����������CD �ϊ��e�[�u���ɑ��݂��邩���`�F�b�N����B
		String floorPartCd = this.floorToPart.get(String.valueOf(housingFloor));
		if (!StringValidateUtil.isEmpty(floorPartCd)){

			// �ϊ��e�[�u���ɕ����K�� ���o�^����Ă���ꍇ�A�ǉ����邱������������ Map �ɁA
			// ���������������݂��邩���`�F�b�N����B
			if (addPartMap.get(floorPartCd) == null){
				addPartMap.put(floorPartCd, createHousingPartInfo(housingInfo, floorPartCd));
			}

		}

		// �����K��(N�K�ȏ�)�A����������CD �ϊ� map�����[�v���A������{���̕����K���Ɣ�r����B
		for (Entry<String, String> e : this.floorUpToPart.entrySet()){
			if (housingFloor < Integer.parseInt(e.getKey())) continue;
			String partCd = e.getValue();
			if (!StringValidateUtil.isEmpty(partCd)){

				// �����K�� ���ϊ��e�[�u���ɕ����K���ȏ゠��ꍇ�A�ǉ����邱������������ Map �ɁA
				// ���������������݂��邩���`�F�b�N����B
				// �������݂���ꍇ�͓o�^�ςȂ̂ŁA���̃f�[�^����������B
				if (addPartMap.get(partCd) == null){
					addPartMap.put(partCd, createHousingPartInfo(housingInfo, partCd));
				}
			}
		}

		// ����������CD(�ŏ�K) ���`�F�b�N����B
		if (housingFloor > 1 && housingFloor == buildingFloor){
			if (!StringValidateUtil.isEmpty(this.topFloorPartCd)){

				// ���������������݂��邩���`�F�b�N����B
				if (addPartMap.get(this.topFloorPartCd) == null){
					addPartMap.put(this.topFloorPartCd, createHousingPartInfo(housingInfo, this.topFloorPartCd));
				}

			}
		}

		// ������������ǉ�����B
		this.housingPartInfoDAO.insert(addPartMap.values().toArray(new HousingPartInfo[addPartMap.size()]));
	}



	/**
	 * �������߃|�C���g�����������쐬����B<br/>
	 * <br/>
	 */
	protected void createPartIcon(Housing housing) throws Exception {

		// �X�V�ΏۂƂȂ镨����{�����擾����B
		HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");

		// ���݂̂���������CD ���폜����B
		delPartCds(housingInfo.getSysHousingCd(), this.myIconPartCds);

		// �A�C�R�����i�������߃|�C���g�j���o�^�ꍇ�͏����I���B
		if (housingInfo.getIconCd() == null) return;
		// �A�C�R�����i�������߃|�C���g�j���擾����B
		String[] iconCds = housingInfo.getIconCd().split(",");

		// �ǉ����邱������������ Map �I�u�W�F�N�g
		// Key = ����������CD�AValue = ���ꂩ��ǉ����镨���������������� Value �I�u�W�F�N�g
		Map<String, HousingPartInfo> addPartMap = new HashMap<>();

		// ������{���̃A�C�R���������[�v���A�������߃|�C���g�A����������CD �ϊ� map�ɑ��݂��邩���`�F�b�N����B
		for (String iconCd : iconCds) {
			String partCd = this.iconToPart.get(iconCd);
			if (!StringValidateUtil.isEmpty(partCd)){

				// �ϊ��e�[�u���ɂ������߃|�C���g ���o�^����Ă���ꍇ�A�ǉ����邱������������ Map �ɁA
				// ���������������݂��邩���`�F�b�N����B
				// �������݂���ꍇ�͓o�^�ςȂ̂ŁA���̃f�[�^����������B
				if (addPartMap.get(partCd) == null){
					addPartMap.put(partCd, createHousingPartInfo(housingInfo, partCd));
				}
			}
		}

		// ������������ǉ�����B
		this.housingPartInfoDAO.insert(addPartMap.values().toArray(new HousingPartInfo[addPartMap.size()]));
	}



	/**
	 * ���݂̂���������CD ���폜����B<br/>
	 * <br/>
	 */
	protected void delPartCds(String sysHousingCd, String partCds[]) {
		// ���݂̂���������CD ���폜����B
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);
		criteria.addInSubQuery("partSrchCd", partCds);
		this.housingPartInfoDAO.deleteByFilter(criteria);
	}



	/**
	 * �V�K�ǉ�����A������������������ Value �I�u�W�F�N�g�𐶐�����B<br/>
	 * �{���́A�V�X�e������CD �ƁA����������CD �����ő����͂������A�g�������l�����āA���̑������n���Ă����B<br/>
	 * <br/>
	 * @param housingInfo �������
	 * @param partCd�@����������CD
	 * @return
	 */
	protected HousingPartInfo createHousingPartInfo(HousingInfo housingInfo, String partCd) {
		// Value �I�u�W�F�N�g�𐶐����ăv���p�e�B�l��ݒ肷��B
		HousingPartInfo housingPartInfo = (HousingPartInfo) this.valueObjectFactory.getValueObject("HousingPartInfo");
		housingPartInfo.setSysHousingCd(housingInfo.getSysHousingCd());
		housingPartInfo.setPartSrchCd(partCd);
		return housingPartInfo;
	}



	/**
	 * �k�����ԁA����������CD �ϊ� map ��ݒ肷��B<br/>
	 * �܂��A���̃N���X�������ΏۂƂ��邱��������CD �̃��X�g���쐬����B<br/>
	 * Key = �k�����ԁAValue = ����������CD
	 * <br/>
	 * @param �k�����ԁA����������CD �ϊ� map
	 */
	public synchronized void setTimeToPart(Map<String, String> timeToPart) {

		// ���̃��\�b�h�̎��s�ɂ͓��������K�v�Ȃ̂ŁA���̃N���X�̃C���X�^���X�͕K���V���O���g���Œ�`���鎖�B
		// �܂��A���̃��\�b�h�́ADI �R���e�i�� Bean ��`�ȊO�ł͎g�p���Ȃ����B

		// �ϊ� Map ��ݒ肷��B
		this.timeToPart = timeToPart;

		// ����������CD �� Set ���g�p���ďd������菜���AmyPartCds �֐ݒ肷��B
		Set<String> partCdSet = new HashSet<>();
		for (Entry<String, String> e : timeToPart.entrySet()){
			partCdSet.add(e.getValue());
		}
		this.myTimePartCds = partCdSet.toArray(new String[partCdSet.size()]);
	}



	/**
	 * �����K���A����������CD �ϊ� map ��ݒ肷��B<br/>
	 * �܂��A���̃N���X�������ΏۂƂ��邱��������CD �̃��X�g���쐬����B<br/>
	 * Key = �����K���AValue = ����������CD
	 * <br/>
	 * @param �����K���A����������CD �ϊ� map
	 */
	public synchronized void setFloorToPart(Map<String, String> floorToPart) {

		// ���̃��\�b�h�̎��s�ɂ͓��������K�v�Ȃ̂ŁA���̃N���X�̃C���X�^���X�͕K���V���O���g���Œ�`���鎖�B
		// �܂��A���̃��\�b�h�́ADI �R���e�i�� Bean ��`�ȊO�ł͎g�p���Ȃ����B

		// �ϊ� Map ��ݒ肷��B
		this.floorToPart = floorToPart;

		// ����������CD �� Set ���g�p���ďd������菜���AmyPartCds �֐ݒ肷��B
		Set<String> partCdSet = new HashSet<>();
		for (Entry<String, String> e : floorToPart.entrySet()){
			partCdSet.add(e.getValue());
		}
		this.myFloorPartCds = partCdSet.toArray(new String[partCdSet.size()]);
	}



	/**
	 * �����K��(N�K�ȏ�)�A����������CD �ϊ� map ��ݒ肷��B<br/>
	 * �܂��A���̃N���X�������ΏۂƂ��邱��������CD �̃��X�g���쐬����B<br/>
	 * Key = �����K��(N�K�ȏ�)�AValue = ����������CD
	 * <br/>
	 * @param �����K��(N�K�ȏ�)�A����������CD �ϊ� map
	 */
	public synchronized void setFloorUpToPart(Map<String, String> floorUpToPart) {

		// ���̃��\�b�h�̎��s�ɂ͓��������K�v�Ȃ̂ŁA���̃N���X�̃C���X�^���X�͕K���V���O���g���Œ�`���鎖�B
		// �܂��A���̃��\�b�h�́ADI �R���e�i�� Bean ��`�ȊO�ł͎g�p���Ȃ����B

		// �ϊ� Map ��ݒ肷��B
		this.floorUpToPart = floorUpToPart;

		// ����������CD �� Set ���g�p���ďd������菜���AmyPartCds �֐ݒ肷��B
		Set<String> partCdSet = new HashSet<>();
		for (Entry<String, String> e : floorUpToPart.entrySet()){
			partCdSet.add(e.getValue());
		}
		this.myFloorUpPartCds = partCdSet.toArray(new String[partCdSet.size()]);
	}



	/**
	 * ����������CD(�ŏ�K) ��ݒ肷��B<br/>
	 * <br/>
	 * @param ����������CD(�ŏ�K)
	 */
	public synchronized void setTopFloorPartCd(String topFloorPartCd) {

		// ���̃��\�b�h�̎��s�ɂ͓��������K�v�Ȃ̂ŁA���̃N���X�̃C���X�^���X�͕K���V���O���g���Œ�`���鎖�B
		// �܂��A���̃��\�b�h�́ADI �R���e�i�� Bean ��`�ȊO�ł͎g�p���Ȃ����B

		this.topFloorPartCd = topFloorPartCd;
	}



	/**
	 * �A�C�R�����i�������߃|�C���g�j�A����������CD �ϊ� map ��ݒ肷��B<br/>
	 * �܂��A���̃N���X�������ΏۂƂ��邱��������CD �̃��X�g���쐬����B<br/>
	 * Key = �������߃|�C���g�AValue = ����������CD
	 * <br/>
	 * @param �������߃|�C���g�A����������CD �ϊ� map
	 */
	public synchronized void setIconToPart(Map<String, String> iconToPart) {

		// ���̃��\�b�h�̎��s�ɂ͓��������K�v�Ȃ̂ŁA���̃N���X�̃C���X�^���X�͕K���V���O���g���Œ�`���鎖�B
		// �܂��A���̃��\�b�h�́ADI �R���e�i�� Bean ��`�ȊO�ł͎g�p���Ȃ����B

		// �ϊ� Map ��ݒ肷��B
		this.iconToPart = iconToPart;

		// ����������CD �� Set ���g�p���ďd������菜���AmyPartCds �֐ݒ肷��B
		Set<String> partCdSet = new HashSet<>();
		for (Entry<String, String> e : iconToPart.entrySet()){
			partCdSet.add(e.getValue());
		}
		this.myIconPartCds = partCdSet.toArray(new String[partCdSet.size()]);
	}



	/**
	 * �����������������DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param housingPartInfoDAO �����������������DAO
	 */
	public void setHousingPartInfoDAO(DAO<HousingPartInfo> housingPartInfoDAO) {
		this.housingPartInfoDAO = housingPartInfoDAO;
	}

	/**
	 * Value �I�u�W�F�N�g�� Factory �N���X��ݒ肷��B<br/>
	 * <br/>
	 * @param valueObjectFactory Value �I�u�W�F�N�g�� Factory
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}

}
